var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdateCCDox';
/** refer drsCommon.js */
formId='creditCardDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to Update DRS ?";

pageLoadUrl= "./updateCcqDoxDrs.do?submitName=viewUpdateDrsPage";



$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
					"sScrollX": "100%",
					"sScrollXInner":"200%",
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
		var url="./updateCcqDoxDrs.do?submitName=findDrsNumberForUpdate";
		globalFormSubmit(url,formId);
	}
}
/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(validateFSInTime() && validateGridForSaveDrs()&& promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./updateCcqDoxDrs.do?submitName=updateDrs";
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
	/** 	1.	For CC, Contact Number, Receiver Name, ID proof type, ID Number & Relationship will be mandatory. The DRS will not be closed unless this information is captured.
	 *2.	For Q series only Contact number and Receiver name are mandatory. Others fields are not mandatory.
	 */
	if(!isNewDrs()){
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var relationDom= document.getElementsByName("to.rowRelationId");
		var idProofDom= document.getElementsByName("to.rowIdentityProofId");
		var idNumDom= document.getElementsByName("to.rowIdNumber");
		var compnySealDom= document.getElementsByName("to.rowCompanySealSign");


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
			}//close the check for validations for only if it is not non-deliv type
			/**  if  delivery type is not non-delivery*/
			if(deliveryTypeDom[i].value!=getNonDlvType()){
				/**  if  delivery type is Office delivery*/
				if(deliveryTypeDom[i].value ==getOfficeDlvType()){
					if(isNull(compnySealDom[i].value)){
						alert("Please provide Company Seal/Sign at line :"+(i+1));
						setFocusOnDom(compnySealDom[i]);
						return false;
					}

				}

				if(getCnoteProduct(consgDom[i].value) == getCseries()){//Check for C-series check
					/** if it's a C-series then Relation is mandatory*/
					if(isNull(relationDom[i].value)){
						alert("Please select Relation Details at line :"+(i+1));
						setFocusOnDom(relationDom[i]);
						return false;
					}
					if(isNull(idProofDom[i].value)){
						alert("Please provide Proofs number for  at line :"+(i+1));
						setFocusOnDom(idProofDom[i]);
						return false;
					}
					if(isNull(idNumDom[i].value)){
						alert("Please provide Id number for "+getSelectedDropDownTextByDOM(idProofDom[i]) +" at line :"+(i+1));
						setFocusOnDom(idNumDom[i]);
						return false;
					}	
				}//close Check for C-series 

				if(!isNull(idProofDom[i].value)){
					if(isNull(idNumDom[i].value)){
						alert("Please provide Id number for "+getSelectedDropDownTextByDOM(idProofDom[i]) +" at line :"+(i+1));
						setFocusOnDom(idNumDom[i]);
						return false;
					}
				}//close id proof
			}//close For NON-DLV check
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
		var relationIdDom= document.getElementsByName("to.rowRelationId");
		var identityProofDom= document.getElementsByName("to.rowIdentityProofId");
		var idNumDom= document.getElementsByName("to.rowIdNumber");
		var isFocusGained=false;
		//var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		for(var i=0;i<deliveryTypeDom.length;i++) {
			if(deliveryTypeDom==null || deliveryTypeDom[i]==null){
				break;
			}
			/** if it's  NON-delivery (i.e already updated in Pending drs)then 
			 * make that row as NON-Editable*/
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
				identityProofDom[i].disabled=true;
				relationIdDom[i].disabled=true;
				idNumDom[i].setAttribute("readOnly",true);
				idNumDom[i].setAttribute("tabindex","-1");
				
			}else{
				//disable delivered cn
				if(!disableDeliveredRowForDrs(i)){
				/** set company seal & sign as default for Office Dlv*/
				setCompnaySealForDlvType((i+1));
				if(!isFocusGained &&!isDrsClosed()){
					setFocusOnDom(deliveryTypeDom[i]);
					isFocusGained=true;
				}
				}
			}//end of For loop
			
		}
	}
}
function enableForDlvTypeForCcq(rowId){
	var dlvTypeDom = getDomElementById("rowDeliveryType"+rowId);
	var comSealDom = getDomElementById("rowCompanySealSign"+rowId);
	/** 	1.	For CC, Contact Number, Receiver Name, ID proof type, ID Number & Relationship will be mandatory. The DRS will not be closed unless this information is captured.
		*2.	For Q series only Contact number and Receiver name are mandatory. Others fields are not mandatory.
	*
	Business Rules : 1. Seal or Sign of the Company (if Delivery Type is Office Delivery)*/
	/** Business Rules : 2.ContactNumber& ReceiverName :: Enabled only when Delivery Type is ‘Home Delivery’*/
	if(dlvTypeDom!=null){
		if(!isNull(dlvTypeDom.value)){

			/** validate whether user selected Non-Delivery, if selected then restrict it.*/
			if(dlvTypeDom.value == getNonDlvType()){
				alert("Please do not select Non-Delivery");
				dlvTypeDom.value="";
				/** Sets focus back to the Dlv Type Drop-Down*/
				setFocusOnDom(dlvTypeDom);
				return false;
			}

			/** if Delivery type is Office Delivery*/
			if(dlvTypeDom.value == getOfficeDlvType()){
				if(comSealDom!=null){
					comSealDom.value="";
					comSealDom.disabled=false;
					comSealDom.removeAttribute("tabindex");
				}

			}else{
				if(comSealDom!=null){
					comSealDom.disabled=true;
					comSealDom.value="";
					comSealDom.setAttribute("tabindex","-1");
				}
			}
		}
	}
}

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


function enterKeynavForConName(rowId,keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		var relationDom= getDomElementById("rowRelationId"+rowId);
		var compnySealDom= getDomElementById("rowCompanySealSign"+rowId);
		if(compnySealDom!=null && !compnySealDom.disabled){
			setFocusOnDom(compnySealDom);
		}
		if(compnySealDom!=null && compnySealDom.disabled){
			setFocusOnDom(relationDom);
		}
	}
}