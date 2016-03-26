var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdateCODLC';
/** refer drsCommon.js */
formId='codLcDoxDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to Update DRS ?";
/** for Clear screen */
pageLoadUrl= "./updateCodLcDrs.do?submitName=viewUpdateDrsPage";

/** called on Load */
$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
					"sScrollX": "100%",
					"sScrollXInner":"248%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
				load();
			} );

/**
 * load
 */
function load(){
	if(!isNewDrs()){
		buttonDisabled('Print','disabled');
	}

	/**If on page load of Disable the entire header*/
	disableHeaderForUpdate();
	
	disableGrid();
	
	/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
	disableForUser();
	applyButtonEvents();
	setFocusOnFsIntime();
	focusOnDrsForUpdate();
	removeFsInEventsIfDrsNotOpnd();
	enablePaymentModeOnlyOnce();
} 
/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){

	/** On click of the save button*/
	$('#Save').click(function() {
		/** Save function call*/
		saveDrsDetails();
	});


	$('#Cancel').click(function() {
		clearScreen();
	});
}

/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./updateCodLcDrs.do?submitName=findDrsNumberForUpdate";
		globalFormSubmit(url,formId);
	}
}

/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(validateFSInTime() && validateGridForSaveDrs()&& promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./updateCodLcDrs.do?submitName=updateDrs";
		//globalFormSubmit(url,formId);
		ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}
function ajaxResponseAfterSave(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	var drsToObject=null;
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			drsToObject=responseText;
			alert(drsToObject.successMessage);
			disableAfterUpdate();
			if(isConfirmedForAnotherUpdate()){
				clearScreenWithoutConfirmation();
			}
		}
	}
}

/**
 * Check mandatory for save
 */
function validateGridForSaveDrs(){
	if(!isNewDrs()){
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		var modeOfPaymentDom= document.getElementsByName("to.rowModeOfPayment");
		var chequeNODom= document.getElementsByName("to.rowChequeNo");
		var chequeDateDom= document.getElementsByName("to.rowChequeDate");
		var banknameAndBranchDom= document.getElementsByName("to.rowBankNameAndBranch");
		
		for(var i=0;i<deliveryTypeDom.length;i++) {
			if(deliveryTypeDom==null || deliveryTypeDom[i]==null){
				break;
			}
			if(isNull(deliveryTypeDom[i].value)){
				alert("Please provide Delivery Type at line :"+(i+1));
				setFocusOnDom(deliveryTypeDom[i]);
				return false;
			}
			/** Check for validations only when the delivry type is not non-delivry
			 *	
			 */
			if(deliveryTypeDom[i].value!=getNonDlvType()){
				if(isNull(deliveryTimeDomHH[i].value)){
					alert("Please provide Delivery Time in HH at line :"+(i+1));
					setFocusOnDom(deliveryTimeDomHH[i]);
					return false;
				}else if(isNull(deliveryTimeDomMM[i].value)){
					alert("Please provide Delivery Time in MM at line :"+(i+1));
					setFocusOnDom(deliveryTimeDomMM[i]);
					return false;
				}
			}
			
			/**  if  delivery type is Home delivery*/
			if(deliveryTypeDom[i].value!=getNonDlvType() && deliveryTypeDom[i].value!=getOfficeDlvType()){
				recvNameDom[i].value=trimString(recvNameDom[i].value);

				if(isNull(recvNameDom[i].value)){
					alert("Please provide Receiver Name at line :"+(i+1));
					setFocusOnDom(recvNameDom[i]);
					return false;
				}
				contactNumDom[i].value=trimString(contactNumDom[i].value);
				if(isNull(contactNumDom[i].value)){
					alert("Please provide Contact Number at line :"+(i+1));
					setFocusOnDom(contactNumDom[i]);
					return false;
				}
				
			}//close for home-delivry type
			
			/** Check for validations only when the delivry type is not non-delivry
			 *	
			 */
			if(deliveryTypeDom[i].value!=getNonDlvType()){
				/**  if  delivery type is Office delivery*/
				if(deliveryTypeDom[i].value ==getOfficeDlvType()){
					if(isNull(compnySealDom[i].value)){
						alert("Please provide Company Seal/Sign at line :"+(i+1));
						setFocusOnDom(compnySealDom[i]);
						return false;
					}

				}
				if(modeOfPaymentDom[i].disabled){
					continue;
				}
				if(isNull(modeOfPaymentDom[i].value)){
					alert("Please provide Mode Of Payment at line :"+(i+1));
					setFocusOnDom(modeOfPaymentDom[i]);
					return false;
				}

				/**  if  mode of payment type is other than cash*/
				if(modeOfPaymentDom[i].value !=getModeOfPaymntTypeCash()){
					var pmntType = getSelectedDropDownTextByDOM(modeOfPaymentDom[i]);
					if(isNull(chequeNODom[i].value)){
						alert("Please provide "+pmntType+" No at line :"+(i+1));
						setFocusOnDom(chequeNODom[i]);
						return false;
					}
					if(isNull(chequeDateDom[i].value)){
						alert("Please provide "+pmntType+" Date at line :"+(i+1));
						setFocusOnDom(chequeDateDom[i]);
						return false;
					}

					if(isNull(banknameAndBranchDom[i].value)){
						alert("Please provide Bank And Branch Name at line :"+(i+1));
						setFocusOnDom(banknameAndBranchDom[i]);
						return false;
					}

				}//close For mode of payment other than cash
			}//close for validation chk if delivery type other than non-delivry
				
		}//close For Loop
		return true;
	}//close isNewDRS
}


/**
 * disableGrid :on load
 */
function disableGrid(){
	if(!isNewDrs()){
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var modeofPaymntDom= document.getElementsByName("to.rowModeOfPayment");
		var chequeNumberDom= document.getElementsByName("to.rowChequeNo");
		var chequeDateDom= document.getElementsByName("to.rowChequeDate");
		var bankNameDom= document.getElementsByName("to.rowBankNameAndBranch");
		var isFocusGained=false;
		//var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		for(var i=0;i<deliveryTypeDom.length;i++) {
			if(deliveryTypeDom==null || deliveryTypeDom[i]==null){
				break;
			}
			/** if it's  NON-delivery (i.e already updated in Pending drs)then 
			 * make that row as NON-Editable*/
			var j=i+1;
			var calImgId= getDomElementById('calImg'+j);
			if(deliveryTypeDom[i].value==getNonDlvType()){
				recvNameDom[i].setAttribute("readOnly",true);
				recvNameDom[i].setAttribute("tabindex","-1");
				
				contactNumDom[i].setAttribute("readOnly",true);
				contactNumDom[i].setAttribute("tabindex","-1");
				
				deliveryTimeDomHH[i].setAttribute("readOnly",true);
				deliveryTimeDomHH[i].setAttribute("tabindex","-1");
				
				deliveryTimeDomMM[i].setAttribute("readOnly",true);
				deliveryTimeDomMM[i].setAttribute("tabindex","-1");
				
				
				deliveryTypeDom[i].disabled=true;
				modeofPaymntDom[i].disabled=true;
				if(calImgId!=null){
					calImgId.style.display="none";
				}
				
			}else{
				if(!disableDeliveredRowForDrs(i)){
					/** set company seal & sign as default for Office Dlv*/
					setCompnaySealForDlvType((i+1));
					if(!isFocusGained && !isDrsClosed()){
						setFocusOnDom(deliveryTypeDom[i]);
						isFocusGained=true;
					}
					if(!isDrsClosed()){
						modeofPaymntDom[i].value=getModeOfPaymntTypeCash();
					}
					if(chequeNumberDom!=null && chequeNumberDom.length>0){
						chequeNumberDom[i].value="";
						chequeNumberDom[i].setAttribute("readOnly",true);
						chequeNumberDom[i].setAttribute("tabindex","-1");
					}
					if(chequeDateDom!=null && chequeDateDom.length>0){
						chequeDateDom[i].value="";
						chequeDateDom[i].setAttribute("readOnly",true);
						chequeDateDom[i].setAttribute("tabindex","-1");
					}
					if(bankNameDom!=null && bankNameDom.length>0){
						bankNameDom[i].value="";
						bankNameDom[i].setAttribute("readOnly",true);
						bankNameDom[i].setAttribute("tabindex","-1");
						bankNameDom[i].removeAttribute("onkeydown");
					}
					if(calImgId!=null){
						calImgId.style.display="none";
					}
				}
			}
			if(isDrsClosed()){
				if(calImgId!=null){
					calImgId.style.display="none";
				}
			}
			
		}
	}
}

/**
 * 
 * @param rowId
 * @returns {Boolean}
 */


function enterKeynavForDlvTime(rowId,keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		var recvNameDom= getDomElementById("rowReceiverName"+rowId);
		var compnySealDom= getDomElementById("rowCompanySealSign"+rowId);
		if(recvNameDom!=null&& !recvNameDom.readOnly){
			setFocusOnDom(recvNameDom);
		}
		if(recvNameDom!=null&& recvNameDom.readOnly){
			setFocusOnDom(compnySealDom);
		}
	}
}




