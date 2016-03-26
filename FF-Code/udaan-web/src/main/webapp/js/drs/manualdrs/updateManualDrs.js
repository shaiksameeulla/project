var rowCount = 1;
/** refer drsCommon.js */
tableId='updateManualDrs';
/** refer drsCommon.js */
formId='manualDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to Update DRS ?";
/** for Clear screen */
pageLoadUrl= "./updateManualDrs.do?submitName=viewUpdateDrsPage";

var DELIVERY_TYPE_DROP_DOWN=null;

var DELIVERY_COMP_SEAL_DROP_DOWN=null;

var DELIVERY_MODE_OF_PAYMENT_DROP_DOWN=null;

var DELIVERY_RELATION_SHIP_DROP_DOWN=null;

var DELIVERY_IDENTITY_PROOF_DROP_DOWN=null;

/** called on Load */
$(document).ready( function () {
	var oTable = $('#'+tableId).dataTable( {
		"sScrollY": "205",
		"sScrollX": "100%",
		"sScrollXInner":"300%",
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
 * fnClickAddRow : for Add row functionality
 */
function addRow(){
	var tempRow=rowCount;
	try{
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount,
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignmentForManualDrs(\''+ tempRow +'\')" class="txtbox width140" onkeydown="return enterKeyNav(\'rowDeliveryType'+ tempRow +'\',event.keyCode);"/>',
	                                           '<select name="to.rowDeliveryType" id="rowDeliveryType'+ rowCount +'" class="selectBox width110"  onkeydown="return enterKeyNav(\'rowDeliveryTimeInHH'+ tempRow +'\',event.keyCode);" onchange="enableForDlvType(\''+ tempRow +'\')"><option value="">--Select--</option></select>',
	                                           
	                                           '<input type="text" id="rowDeliveryTimeInHH'+ rowCount +'" name="to.rowDeliveryTimeInHH" class="txtbox width30" onkeydown="return enterKeyNav(\'rowDeliveryTimeInMM'+tempRow+'\',event.keyCode);" onchange="clearDeliveryTimeMinutes(\''+ tempRow +'\')" maxlength="2" />:\
	                                           <input type="text" id="rowDeliveryTimeInMM'+ rowCount +'" name="to.rowDeliveryTimeInMM" class="txtbox width30" onkeydown="return enterKeyNav(\'rowReceiverName'+tempRow+'\',event.keyCode);" onchange="validateDeliveryTime(\''+ tempRow +'\')" maxlength="2" />',
	                                          
	                                           '<input type="text" id="rowReceiverName'+ rowCount +'" name="to.rowReceiverName" class="txtbox width100"  onkeydown="return enterKeyNav(\'rowContactNumber'+tempRow+'\',event.keyCode);" maxlength="20" />',
	                                           '<input type="text" id="rowContactNumber'+ rowCount +'" name="to.rowContactNumber" class="txtbox width100" onkeydown="return enterKeyNav(\'rowCompanySealSign'+tempRow+'\',event.keyCode);" maxlength="10" onkeypress="return onlyNumeric(event)" onchange="validateContactNumber(this)"/>',
	                                           '<select name="to.rowCompanySealSign" id="rowCompanySealSign'+ rowCount +'" class="selectBox width110" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,\''+ tempRow +'\',\'rowCompanySealSign\');"><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowCodAmount'+ rowCount +'" name="to.rowCodAmount" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowLCAmount'+ rowCount +'" name="to.rowLCAmount" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowToPayAmount'+ rowCount +'" name="to.rowToPayAmount" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowOtherCharges'+ rowCount +'" name="to.rowOtherCharges" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowBaAmount'+ rowCount +'" name="to.rowBaAmount" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
	                                           '<select name="to.rowModeOfPayment" id="rowModeOfPayment'+ rowCount +'" class="selectBox width110" disabled="true" onchange="enableForModeOfPaymntChequeType(\''+ tempRow +'\')"><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowChequeNo'+ rowCount +'" name="to.rowChequeNo"  class="txtbox width100" maxlength="6" readonly="readonly" tabindex="-1" onkeydown="return enterKeyNav(\'rowChequeDate'+ tempRow +'\',event.keyCode);" onkeypress="return onlyNumeric(event)"/> ',
	                                           '<input type="text" id="rowChequeDate'+ rowCount +'" name="to.rowChequeDate"  class="txtbox width100" maxlength="12" readonly="readonly" tabindex="-1" onkeydown="return enterKeyNav(\'rowBankNameAndBranch'+ tempRow +'\',event.keyCode);"/> <a href="javascript:show_calendar(\'rowChequeDate'+rowCount+'\', this.value)" id="calImg'+rowCount+'\" name="calImg"> <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0"  /></a> ',
	                                           '<input type="text" id="rowBankNameAndBranch'+ rowCount +'" name="to.rowBankNameAndBranch"  class="txtbox width100" maxlength="15" readonly="readonly" tabindex="-1"/> ',
	                                           '<select name="to.rowRelationId" id="rowRelationId'+ rowCount +'"  class="selectBox width110" disabled="true" onkeydown="return enterKeyNav(\'rowIdentityProofId'+ tempRow +'\',event.keyCode);"><option value="">--Select--</option></select>',
	                                           '<select name="to.rowIdentityProofId" id="rowIdentityProofId'+ rowCount +'"  class="selectBox width110" disabled="true" onkeydown="return enterKeyNav(\'rowIdNumber'+ tempRow +'\',event.keyCode);"><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowIdNumber'+ rowCount +'" name="to.rowIdNumber"  class="txtbox width100" maxlength="25"  tabindex="-1" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,\''+ tempRow +'\',\'rowIdNumber\');"/> ',
	                                           '<select name="to.rowPendingReasonId" id="rowPendingReasonId'+ rowCount +'"  class="selectBox width110" disabled="true" tabindex="-1" ><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowMissedCardNumber'+ rowCount +'" name="to.rowMissedCardNumber"  class="txtbox width100" maxlength="25" readonly="readonly" tabindex="-1"/> ',
	                                           '<input type="text" id="rowRemarks'+ rowCount +'" name="to.rowRemarks" class="txtbox width100" maxlength="30" readonly="readonly" tabindex="-1"/>\
	                                           <input type="hidden" id="rowDeliveryDetailId'+ rowCount +'" name="to.rowDeliveryDetailId" />\
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowParentChildCnType'+ rowCount +'" name="to.rowParentChildCnType"/> \
	                                           <input type="hidden" id="rowIsPaymentAlreadyCaptured'+ rowCount +'" name="to.rowIsPaymentAlreadyCaptured"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />',
	                                           ] );
	}catch(e){
		//alert(e);
	}
	rowCount++;
	/**inspecting Whether delete functionality 
	 * executed at least once. 
	 * if so start calling updateGrid method for updating serial numbers */
	if(isDeleted){
		updateGrid(tableId);
	}
	focusOnElementWithId('rowConsignmentNumber',tempRow);
	populateManualDrsDropDown(tempRow);
	focusOnElementWithId('rowConsignmentNumber',tempRow);
	focusForManualDrsOnStartup();
}




function addRowForManualDrsUpdate(){
	if(isValidToAddRowsForMDRS()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		addRow();
	}
}

function isValidToAddNewRow(rowId){
	if(isValidToAddRowsForMDRS() && validateMandatoryForAddManualDrs(rowId)){
		addRow();
	}
}

function validateMandatoryForAddManualDrs(rowId){
	var consgNumberDom = getDomElementById("rowConsignmentNumber"+rowId);
	var consgNumberDomList = document.getElementsByName("to.rowConsignmentNumber");
	var atRow=" at Row :"+rowId;
	
	if(isNull(consgNumberDom.value)){
		return false;
	}
	if(isNull(consgNumberDomList) || consgNumberDomList.length<=0){
		return false;
	}
	//check common mandatory fields such as Delivery Time , Delivery type
	var dlvTypeDom = getDomElementById("rowDeliveryType"+rowId);
	var dlvTimeHH = getDomElementById("rowDeliveryTimeInHH"+rowId);
	var dlvTimeMM = getDomElementById("rowDeliveryTimeInMM"+rowId);
	var companySealDom = getDomElementById("rowCompanySealSign"+rowId);
	var receiverDom = getDomElementById("rowReceiverName"+rowId);
	var contactNumDom = getDomElementById("rowContactNumber"+rowId);
	
	if(isNull(dlvTypeDom.value)){
		alert("Please provide Delivery Type "+atRow);
		dlvTypeDom.focus();
		setFocusOnDom(dlvTypeDom);
		return false;
	}
	if(isNull(dlvTimeHH.value)){
		alert("Please provide Delivery Time in HH "+atRow);
		setFocusOnDom(dlvTimeHH);
		return false;
	}
	if(isNull(dlvTimeMM.value)){
		alert("Please provide Delivery Time in MM "+atRow);
		setFocusOnDom(dlvTimeMM);
		return false;
	}
	if(dlvTypeDom.value == getOfficeDlvType()){
		if(isNull(companySealDom.value)){
			alert("Please select  company seal/Sig "+atRow);
			setFocusOnDom(companySealDom);
			return false;
		}
	}else if(dlvTypeDom.value != getOfficeDlvType()){
		if(isNull(receiverDom.value)){
			alert("Please provide  Receiver Name "+atRow);
			receiverDom.focus();
			setFocusOnDom(receiverDom);
			return false;
		}
		if(isNull(contactNumDom.value)){
			alert("Please provide  Contact Name "+atRow);
			contactNumDom.focus();
			setFocusOnDom(contactNumDom);
			return false;
		} 
	}
	
	if(!isNull(contactNumDom.value)){
		if(!isValidContactNumber(contactNumDom.value)){
			contactNumDom.value='';
			setFocusOnDom(contactNumDom);
			return false;
		}
	}
	var size=consgNumberDomList.length;
	
	if(consgNumberDomList[size-1] ==consgNumberDom){
		var product=getCnoteProduct(consgNumberDom.value);
		if(product==getLseries() || product==getTseries() || product==getDseries()){
			//check mandatory values for COD/LC/TO-Pay
			 var modeOfPaymntTypeDom = getDomElementById("rowModeOfPayment"+rowId);
			var chequeNumberDom = getDomElementById("rowChequeNo"+rowId);
			var chequeDateDom = getDomElementById("rowChequeDate"+rowId);
			var bankNameDom= getDomElementById("rowBankNameAndBranch"+rowId);
			if(!modeOfPaymntTypeDom.disabled){
				if(isNull(modeOfPaymntTypeDom.value)){
					alert("Please select payment Mode "+atRow);
					modeOfPaymntTypeDom.focus();
					setFocusOnDom(modeOfPaymntTypeDom);
					return false;
				}else if(modeOfPaymntTypeDom.value != getModeOfPaymntTypeCash()){

					var pmntType = getSelectedDropDownTextByDOM(modeOfPaymntTypeDom);
					if(isNull(chequeNumberDom.value)){
						alert("Please provide "+pmntType+" No "+atRow);
						setFocusOnDom(chequeNumberDom);
						return false;
					}
					if(isNull(chequeDateDom.value)){
						alert("Please provide "+pmntType+" Date "+atRow);
						setFocusOnDom(chequeDateDom);
						return false;
					}

					if(isNull(bankNameDom.value)){
						alert("Please provide Bank And Branch Name "+atRow);
						setFocusOnDom(bankNameDom);
						return false;
					}


				}
			}
			//End Cod/LC/TO Pay series

		}else if(product==getQseries() || product==getCseries()){
			//check mandatory fields Contact number and Reciever Name
			if(isNull(receiverDom.value)){
				alert("Please provide  Receiver Name "+atRow);
				//receiverDom.focus();
				setFocusOnDom(receiverDom);
				return false;
			}
			if(isNull(contactNumDom.value)){
				alert("Please provide  Contact Number "+atRow);
				setFocusOnDom(contactNumDom);
				return false;
			}
			//check mandatory fields(For Credit card) Contact number and mobile number ,idProof, idNumber & relation
			if(product==getCseries()){
				var relationDom= getDomElementById("rowRelationId"+rowId);
				var idProofDom= getDomElementById("rowIdentityProofId"+rowId);
				var idNumDom= getDomElementById("rowIdNumber"+rowId);
				if(isNull(relationDom.value)){
					alert("Please provide Relation details "+atRow);
					setFocusOnDom(relationDom);
					relationDom.focus();
					return false;
				}
				if(isNull(idProofDom.value)){
					alert("Please select  identity proof details "+atRow);
					setFocusOnDom(idProofDom);
					idProofDom.focus();
					return false;
				}
				var idType = getSelectedDropDownTextByDOM(idProofDom);
				if(isNull(idNumDom.value)){
					alert("Please provide "+idType+" Number "+atRow);
					setFocusOnDom(idNumDom);
					//idNumDom.focus();
					return false;
				}
			}//end of C-series validations
		}//end of CC & Q series validations
	}else{
		//if current row and Last row are not same.
		return false;
	}
	
	return true;
}

/**
 * load
 */
function load(){
	if(!isNewDrs()){
		buttonDisabled('Print','disabled');
		
	}
	rowCount= getMaxRowsForDrs();
	if(isNull(rowCount)){
		rowCount=1;
	}
	addRowForManualDrsUpdate();
	/**If on page load of Disable the entire header*/
	disableHeaderForUpdate();
	
	disableGrid();
	
	/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
	disableForUser();
	applyButtonEvents();
	setFocusOnFsIntime();
	disabledForTPmanifest();
	disableGridForTpMf();
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
		var url="./updateManualDrs.do?submitName=findDrsNumberForUpdate";
		globalFormSubmit(url,formId);
	}
}

/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(isValidateForSaveManualDrs() && validateFSInTime('') && validateGridForSaveDrs()&& promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./updateManualDrs.do?submitName=updateDeliveredDrs";
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
		}
	}
}

/**
 * Check mandatory for save
 */
function validateGridForSaveDrs(){
	
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
		
		var relationDom= document.getElementsByName("to.rowRelationId");
		var idProofDom= document.getElementsByName("to.rowIdentityProofId");
		var idNumDom= document.getElementsByName("to.rowIdNumber");
		var lastRowId=null;
		if(deliveryTypeDom!=null && deliveryTypeDom.length>0){
			lastRowId=deliveryTypeDom.length-1;
		for(var i=0;i<deliveryTypeDom.length;i++) {
			var atRow=" at line :"+(i+1);
			if(deliveryTypeDom[i].disabled || deliveryTypeDom[i].value ==getNonDlvType()){
				//since it's readOnly (ie consignment already marked as pending)
				continue;
			}
			if(lastRowId == i && i!=0){
				//check whether last row details is empty
				if(isNull(consgDom[i].value)){
					return true;
				}
			}
			
			if(isNull(consgDom[i].value)){
				alert("Please provide Consignment number "+atRow);
				setFocusOnDom(consgDom[i]);
				return false;
			}
			
			if(isNull(deliveryTypeDom[i].value)){
				alert("Please provide Delivery Type "+atRow);
				setFocusOnDom(deliveryTypeDom[i]);
				return false;
			}
			/*if(isNull(deliveryTimeDom[i].value)){
				alert("Please provide Delivery Time "+atRow);
				setFocusOnDom(deliveryTimeDom[i]);
				return false;
			}*/
			if(isNull(deliveryTimeDomHH[i].value)){
				alert("Please provide Delivery Time in HH "+atRow);
				setFocusOnDom(deliveryTimeDomHH[i]);
				return false;
			}
			if(isNull(deliveryTimeDomMM[i].value)){
				alert("Please provide Delivery Time in MM"+atRow);
				setFocusOnDom(deliveryTimeDomMM[i]);
				return false;
			}
			if(deliveryTypeDom[i].value == getOfficeDlvType()){
				if(isNull(compnySealDom[i].value)){
					alert("Please select  company seal/Sig "+atRow);
					companySealDom.focus();
					setFocusOnDom(compnySealDom[i]);
					return false;
				}
			}
			/**  if  delivery type is Home delivery*/
			if(deliveryTypeDom[i].value!=getNonDlvType() && deliveryTypeDom[i].value!=getOfficeDlvType()){
				recvNameDom[i].value=trimString(recvNameDom[i].value);
				if(isNull(recvNameDom[i].value)){
					alert("Please provide Receiver Name "+atRow);
					setFocusOnDom(recvNameDom[i]);
					return false;
				}
				contactNumDom[i].value=trimString(contactNumDom[i].value);
				if(isNull(contactNumDom[i].value)){
					alert("Please provide Contact Number "+atRow);
					setFocusOnDom(contactNumDom[i]);
					return false;
				}
				
			}//close for home-delivry type
			
			//get consignment product
			var product=getCnoteProduct(consgDom[i].value);
			if(!modeOfPaymentDom[i].disabled || isPaymentRequiredForLTDSeries(i, product)){
				if(isProductValidForPayment(product)){
					if(isNull(modeOfPaymentDom[i].value)){
						alert("Please select Mode of payment details "+atRow);
						setFocusOnDom(modeOfPaymentDom[i]);
						return false;

					}
					/**  if  mode of payment type is other than cash*/
					if(modeOfPaymentDom[i].value !=getModeOfPaymntTypeCash()){
						var pmntType = getSelectedDropDownTextByDOM(modeOfPaymentDom[i]);
						if(isNull(chequeNODom[i].value)){
							alert("Please provide "+pmntType+" No "+atRow);
							setFocusOnDom(chequeNODom[i]);
							return false;
						}
						if(isNull(chequeDateDom[i].value)){
							alert("Please provide "+pmntType+" Date "+atRow);
							setFocusOnDom(chequeDateDom[i]);
							return false;
						}

						if(isNull(banknameAndBranchDom[i].value)){
							alert("Please provide Bank And Branch Name "+atRow);
							setFocusOnDom(banknameAndBranchDom[i]);
							return false;
						}

					}//close For mode of payment other than cash
				}//close for validation For Cod Lc TO Pay
			}
			
			if(product==getQseries() || product==getCseries()){
				if(isNull(recvNameDom[i].value)){
					alert("Please provide Receiver Name "+atRow);
					setFocusOnDom(recvNameDom[i]);
					return false;
				}
				if(isNull(contactNumDom[i].value)){
					alert("Please provide Contact Number "+atRow);
					setFocusOnDom(contactNumDom[i]);
					return false;
				}
				//check mandatory fields(For Credit card) Contact number and mobile number ,idProof, idNumber & relation
				if(product==getCseries()){
					
					if(isNull(relationDom[i].value)){
						alert("Please provide Relation details "+atRow);
						setFocusOnDom(relationDom[i]);
						return false;
					}
					if(isNull(idProofDom[i].value)){
						alert("Please select  identity proof details "+atRow);
						setFocusOnDom(idProofDom[i]);
						return false;
					}
					var idType = getSelectedDropDownTextByDOM(idProofDom[i]);
					if(isNull(idNumDom[i].value)){
						alert("Please provide "+idType+" Number "+atRow);
						setFocusOnDom(idNumDom[i]);
						return false;
					}
				}//end of C-series validations
			}//end of CC & Q series validations
				
		}//close For Loop
		}else{
			alert("In-valid Grid details");
			return false;
		}
		return true;
}
/**
 * getModeOfPaymentType 
 * @returns
 */
function getModeOfPaymntTypeCheque(){
	return getDomElementById("modeOfPaymentCheque").value;
}

/**
 * @returns ModeOfPaymntType Cash
 */
function getModeOfPaymntTypeCash(){
	return getDomElementById("modeOfPaymentCash").value;
}

/**
 * disableGrid :on load
 */
function disableGrid(){
	if(!isNewDrs()){
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var modeofPaymntDom= document.getElementsByName("to.rowModeOfPayment");
		var isFocusGained=false;
		//var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		for(var i=0;i<deliveryTypeDom.length;i++) {
			var chequeDateImg=getDomElementById('calImg'+(i+1));
				chequeDateImg.style.display = "none";
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
				
				/*deliveryTimeDom[i].setAttribute("readOnly",true);
				deliveryTimeDom[i].setAttribute("tabindex","-1");*/
				
				deliveryTimeDomHH[i].setAttribute("readOnly",true);
				deliveryTimeDomHH[i].setAttribute("tabindex","-1");
				
				deliveryTimeDomMM[i].setAttribute("readOnly",true);
				deliveryTimeDomMM[i].setAttribute("tabindex","-1");
				
				deliveryTypeDom[i].disabled=true;
				modeofPaymntDom[i].disabled=true;
				
			}else{
				/** set company seal & sign as default for Office Dlv*/
				setCompnaySealForDlvType((i+1));
				if(!isFocusGained){
					if(isNull(consgDom[i].value)){
						setFocusOnDom(consgDom[i]);
					}else{
					setFocusOnDom(deliveryTypeDom[i]);
					}
					isFocusGained=true;
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

/**
 * 
 * @param rowId
 */
function populateManualDrsDropDown(rowId){
	if(isNull(DELIVERY_TYPE_DROP_DOWN)|| isNull(DELIVERY_COMP_SEAL_DROP_DOWN) ||isNull(DELIVERY_MODE_OF_PAYMENT_DROP_DOWN) ||isNull(DELIVERY_RELATION_SHIP_DROP_DOWN) ||isNull(DELIVERY_IDENTITY_PROOF_DROP_DOWN)){
		getManualDrsGridDropdown(rowId);
		
	}else{
		popultaManualDrsGridPopulation(rowId);
	}
}
function getManualDrsGridDropdown(rowId){
	var url='./updateManualDrs.do?submitName=ajaxGetGridDropdownDetailsForManualDRS';
	ajaxJqueryWithRow(url,formId,ajxRespForManualDrsGridDropdown,rowId);
}
function ajxRespForManualDrsGridDropdown(rspObject,rowId){
	if(!isNull(rspObject)){
		var responseText =jsonJqueryParser(rspObject); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			//case (i):get dropdown values for DElivery Type
			DELIVERY_TYPE_DROP_DOWN =responseText["DRS_TYPE"];
			DELIVERY_COMP_SEAL_DROP_DOWN =responseText["DRS_SEAL"];
			DELIVERY_MODE_OF_PAYMENT_DROP_DOWN=responseText["MODE_OF_PAYMENT"];
			DELIVERY_RELATION_SHIP_DROP_DOWN=responseText["DRS_RELATION_TYPE"];
			DELIVERY_IDENTITY_PROOF_DROP_DOWN=responseText["DRS_ID_PROOF_TYPE"];
			popultaManualDrsGridPopulation(rowId);
		}
	}else{
		alert("Non-Delivery Reason details does not exist");
	}

}
function popultaManualDrsGridPopulation(rowId){
	//DELIVERY_MODE_OF_PAYMENT_DROP_DOWN =responseText["MODE_OF_PAYMENT"];
	if(!isNull(DELIVERY_TYPE_DROP_DOWN)){
		var dlvType='rowDeliveryType'+rowId;
		createDropDownForJquery(dlvType,DELIVERY_TYPE_DROP_DOWN);
	}
	//case (ii):get dropdown values for DElivery Type
	//DELIVERY_COMP_SEAL_DROP_DOWN =responseText["DRS_SEAL"];
	if(!isNull(DELIVERY_COMP_SEAL_DROP_DOWN)){
		var compSeal='rowCompanySealSign'+rowId; 
		createDropDownForJquery(compSeal,DELIVERY_COMP_SEAL_DROP_DOWN);
	}
	//case (iii):get dropdown values for DElivery Type
	//DELIVERY_MODE_OF_PAYMENT_DROP_DOWN =responseText["MODE_OF_PAYMENT"];
	if(!isNull(DELIVERY_MODE_OF_PAYMENT_DROP_DOWN)){
		var modeOfPamnt='rowModeOfPayment'+rowId;
		createDropDownForJquery(modeOfPamnt,DELIVERY_MODE_OF_PAYMENT_DROP_DOWN);
	}
	
	//case (iii):get dropdown values for Relation Ship
	//DELIVERY_RELATION_SHIP_DROP_DOWN =responseText["MODE_OF_PAYMENT"];
	if(!isNull(DELIVERY_RELATION_SHIP_DROP_DOWN)){
		var relationShipDom='rowRelationId'+rowId;
		createDropDownForJquery(relationShipDom,DELIVERY_RELATION_SHIP_DROP_DOWN);
	}
	
	//case (iii):get dropdown values for I proof
	//DELIVERY_IDENTITY_PROOF_DROP_DOWN =responseText["MODE_OF_PAYMENT"];
	if(!isNull(DELIVERY_IDENTITY_PROOF_DROP_DOWN)){
		var idProofDom='rowIdentityProofId'+rowId;
		createDropDownForJquery(idProofDom,DELIVERY_IDENTITY_PROOF_DROP_DOWN);
	}
	focusOnElementWithId('rowConsignmentNumber',rowId);
}

function validateConsignmentForManualDrs(rId){
	var rowId=parseInt(rId);
	removeOnblurEvent(rowId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var drsDom= getDomElementById('drsNumber');
	
	if(isNull(consignmentNumber.value)){
		alert("Please enter consignment number");
		clearGridValuesForManualDrs(rowId);
		setFocusOnDom(consignmentNumber);
		return;
	}
	if(isNull(drsDom.value)){
		alert("Please provide DRS number(please find the data first)");
		drsDom.value="";
		setFocusOnDom(drsDom);
		return;
	}
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkAllowedConsignments(consignmentNumber) && checkDuplicateConsignments(consignmentNumber) && validateMandatoryForManualDrsCNScan()){
		/** Ajax call to validate Consignment number by DRS number*/
		var url="./updateManualDrs.do?submitName=ajaxValidateConsignmentByDrsNumberForManualDrs&consgNumber="+consignmentNumber.value+"&drsNumber="+drsDom.value;
		enableDisableForCnAjaxCall(false);
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
		clearGridValuesForManualDrs(rowId);
		consignmentNumber.value="";
		setFocusOnDom(consignmentNumber);
		return;
	}
}
/**
 * ajaxResponseForConsignment
 * @param ajaxResp
 * @param rowId
 */
function ajaxResponseForConsignment(ajaxResp,rowId){
	enableDisableForCnAjaxCall(true);
	//alert("ajaxResp"+ajaxResp);
	var deliveryDetailsTo=null;
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			clearGridValuesForManualDrs(rowId);
			setFocusOnDom(consignmentNumber);
		}else{
			deliveryDetailsTo=responseText;
			//populate Consignment details
			populateGridForManualDrs(deliveryDetailsTo,rowId);
			//populateGridConsgAmntDts(deliveryDetailsTo, rowId);
			codlcToAmountPopulate(rowId,deliveryDetailsTo);
			enableForCCqSeries(rowId);
			isPaymnetModeRequired(rowId);
			setOnBlurEventForCn(rowId);
			populateDlvTypeAndCompSeal(rowId);
			enableModeOfpaymentForMDrs(rowId);
		}
	}else{
		alert("Invalid response,please Re-try...");
		clearGridValuesForManualDrs(rowId);
		setFocusOnDom(consignmentNumber);
	}

}
function clearGridValuesForManualDrs(rowId){
	removeOnblurEvent(rowId);
	getDomElementById("rowOriginCityId"+rowId).value="";
	getDomElementById("rowOriginCityCode"+rowId).value="";
	
	getDomElementById("rowConsignmentNumber"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
	getDomElementById("rowConsignmentId"+rowId).value="";
	getDomElementById("rowParentChildCnType"+rowId).value="";
	
	getDomElementById("rowDeliveryType"+rowId).value="";
	//getDomElementById("rowDeliveryTime"+rowId).value="";
	getDomElementById("rowDeliveryTimeInHH"+rowId).value="";
	getDomElementById("rowDeliveryTimeInMM"+rowId).value="";
	getDomElementById("rowReceiverName"+rowId).value="";
	getDomElementById("rowContactNumber"+rowId).value="";
	
	var relationDom=getDomElementById("rowRelationId"+rowId);
	relationDom.disabled=true;
	relationDom.value="";
	
	var identityProofDom=getDomElementById("rowIdentityProofId"+rowId);
	identityProofDom.value="";
	identityProofDom.disabled=true;
	
	var rowIdNumber=getDomElementById("rowIdNumber"+rowId);
	rowIdNumber.setAttribute("readOnly",true);
	rowIdNumber.value="";
	
	var companyDom=getDomElementById("rowCompanySealSign"+rowId);
	companyDom.value="";
	
	var modeOfPaymntDom=getDomElementById("rowModeOfPayment"+rowId);
	modeOfPaymntDom.value="";
	modeOfPaymntDom.disabled=true;
	
	var chequeNo=getDomElementById("rowChequeNo"+rowId);
	chequeNo.value="";
	chequeNo.setAttribute("readOnly",true);
	
	var cheqDateDom=getDomElementById("rowChequeDate"+rowId);
	cheqDateDom.value="";
	
	var bankDo=getDomElementById("rowBankNameAndBranch"+rowId);
	bankDo.value="";
	bankDo.setAttribute("readOnly",true);
	
}
function isPaymnetModeRequired(rowId){
	
	var isPaymnetEnabled=false;
	var cnNumberDom = getDomElementById("rowConsignmentNumber"+rowId);
	var paymntModeDom=getDomElementById("rowModeOfPayment"+rowId);
	var chqNumberDom=getDomElementById("rowChequeNo"+rowId);
	var chqDateDom=getDomElementById("rowChequeDate"+rowId);
	var bankBranch=getDomElementById("rowBankNameAndBranch"+rowId);
	var compnySealSig=getDomElementById("rowCompanySealSign"+rowId);
	var chequeDateImg=getDomElementById('calImg'+rowId);
	
	if(paymntModeDom!=null){
		paymntModeDom.value="";
	}
	if(chqNumberDom!=null){
		chqNumberDom.value="";
	}
	if(chqDateDom!=null){
		chqDateDom.value="";
	}
	if(bankBranch!=null){
		bankBranch.value="";
	}
	compnySealSig.removeAttribute("onblur");
	paymntModeDom.removeAttribute("onblur");
	if(!isNull(cnNumberDom.value)){
		var product=getCnoteProduct(cnNumberDom.value);
		if(product==getLseries() || product==getTseries() || product==getDseries()){
			isPaymnetEnabled=true;
			paymntModeDom.value=getModeOfPaymntTypeCash();
		}
		
	}
	
	var imageFlag="none";
	if(isPaymnetEnabled){
		paymntModeDom.disabled=false;
		chqDateDom.setAttribute("readOnly",true);
		chqDateDom.removeAttribute("onkeydown");
		chqNumberDom.setAttribute("readOnly",true);
		bankBranch.setAttribute("readOnly",true);
		compnySealSig.removeAttribute("onkeydown");
		compnySealSig.setAttribute("onkeydown","enterKeyNav('rowModeOfPayment"+rowId+"'\,event.keyCode)");
		paymntModeDom.setAttribute("onkeydown","enterKeyNavForModeofPaymnt('"+rowId+"', event.keyCode)");
		paymntModeDom.setAttribute("onchange","enableForModeOfPaymntChequeType('"+rowId+"', event.keyCode)");
		paymntModeDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"', event.keyCode)");
		
	}else{
		paymntModeDom.value="";
		paymntModeDom.disabled=true;
		chqNumberDom.removeAttribute("onkeydown");
		chqNumberDom.setAttribute("readOnly",true);
		chqDateDom.setAttribute("readOnly",true);
		chqDateDom.removeAttribute("onkeydown");
		bankBranch.setAttribute("readOnly",true);
		bankBranch.removeAttribute("onkeydown");
		compnySealSig.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowCompanySealSign')");
	}
	if(chequeDateImg!=null){
		chequeDateImg.style.display = imageFlag;
	}
}
function enableForCCqSeries(rowId){
	var cnNumberDom = getDomElementById("rowConsignmentNumber"+rowId);
	var contactNumberDom=getDomElementById("rowContactNumber"+rowId);
	var compnySealSig=getDomElementById("rowCompanySealSign"+rowId);
	var relationDom=getDomElementById("rowRelationId"+rowId);
	var rowIdentityDom=getDomElementById("rowIdentityProofId"+rowId);
	var rowIdnumberDom=getDomElementById("rowIdNumber"+rowId);
	relationDom.value="";
	rowIdnumberDom.value="";
	rowIdentityDom.value="";
	
	relationDom.removeAttribute("onkeydown");
	rowIdentityDom.removeAttribute("onkeydown");
	rowIdentityDom.removeAttribute("onblur");
	rowIdnumberDom.removeAttribute("onkeydown");
	
	var product=getCnoteProduct(cnNumberDom.value);
	if(product == getCseries() || product==getQseries() ){
		//contactNumberDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
		//contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowContactNumber')");
		contactNumberDom.setAttribute("onkeydown","enterKeyNav('rowRelationId"+rowId+"'\,event.keyCode)");
	}
	
	if(!isNull(cnNumberDom.value) && getCnoteProduct(cnNumberDom.value) == getCseries()){
		compnySealSig.setAttribute("onkeydown","enterKeyNav('rowRelationId"+rowId+"'\,event.keyCode)");
		relationDom.disabled=false;
		rowIdentityDom.disabled=false;
		rowIdnumberDom.readOnly=false;
		contactNumberDom.readOnly=false;
		
		relationDom.removeAttribute("tabindex");
		rowIdnumberDom.removeAttribute("tabindex");
		rowIdentityDom.removeAttribute("tabindex");
		contactNumberDom.removeAttribute("tabindex");
		
		relationDom.setAttribute("onkeydown","enterKeyNav('rowIdentityProofId"+rowId+"'\,event.keyCode)");
		rowIdentityDom.setAttribute("onkeydown","enterKeyNav('rowIdNumber"+rowId+"'\,event.keyCode)");
		rowIdnumberDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
		rowIdnumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowIdNumber')");
		
	}else if(!isNull(cnNumberDom.value) && getCnoteProduct(cnNumberDom.value) == getQseries()) {
		
		contactNumberDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
		contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowContactNumber')");
		compnySealSig.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowCompanySealSign')");
		relationDom.disabled=true;
		relationDom.setAttribute("tabindex","-1");
		
		rowIdnumberDom.setAttribute("tabindex","-1");
		rowIdnumberDom.setAttribute("readOnly",true);
		
		rowIdentityDom.disabled=true;
		rowIdentityDom.setAttribute("tabindex","-1");

	}
}

function setOnBlurEventForCn(rowId){
	var cnNumberDom = getDomElementById("rowConsignmentNumber"+rowId);
	var contactNumberDom=getDomElementById("rowContactNumber"+rowId);
	var product=getCnoteProduct(cnNumberDom.value);
	if(product != getCseries() && product!=getQseries() && product!=getLseries() && product!=getTseries() && product!=getDseries()){
		contactNumberDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
		contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowId+",'rowContactNumber')");
	}
}
function removeOnblurEvent(rowId){
	var contactNumberDom=getDomElementById("rowContactNumber"+rowId);
	var rowIdnumberDom=getDomElementById("rowIdNumber"+rowId);
	var paymntModeDom=getDomElementById("rowModeOfPayment"+rowId);
	var bankBranchDom=getDomElementById("rowBankNameAndBranch"+rowId);
	var chequeNoDom=getDomElementById("rowChequeNo"+rowId);
	if(contactNumberDom!=null){
		contactNumberDom.removeAttribute("onblur");
	}
	if(rowIdnumberDom!=null){
		rowIdnumberDom.removeAttribute("onblur");
	}
	if(paymntModeDom!=null){
		paymntModeDom.removeAttribute("onblur");
	}
	if(bankBranchDom!=null){
		bankBranchDom.removeAttribute("onblur");
	}
	if(chequeNoDom!=null){
		chequeNoDom.removeAttribute("onblur");
	}
}

function disableGridForTpMf(){
	if(!isThirdPartyManifestDrs()){
		return false;
	}
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
	
	var relationDom= document.getElementsByName("to.rowRelationId");
	var idProofDom= document.getElementsByName("to.rowIdentityProofId");
	var idNumDom= document.getElementsByName("to.rowIdNumber");
	var deliveryIdDom= document.getElementsByName("to.rowDeliveryDetailId");
	if(deliveryTypeDom!=null && deliveryTypeDom.length>0){

		for(var i=0;i<deliveryTypeDom.length;i++) {
			var chequeDateImg=getDomElementById('calImg'+(i+1));
			chequeDateImg.style.display = "none";
			if(!isNull(deliveryIdDom[i].value)){
				//disable everything
				continue;
			}
			setCompnaySealForDlvType((i+1));
			compnySealDom[i].disabled=false;
			modeOfPaymentDom[i].value="";
			modeOfPaymentDom[i].disabled=true;
			recvNameDom[i].readOnly=false;
			recvNameDom[i].removeAttribute("tabindex");
			contactNumDom[i].readOnly=false;
			contactNumDom[i].removeAttribute("tabindex");
			compnySealDom[i].setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+(i+1)+"','rowCompanySealSign')");
			//get consignment product
			var product=getCnoteProduct(consgDom[i].value);
			if(product==getLseries() || product==getTseries() || product==getDseries()){
				compnySealDom[i].setAttribute("onkeydown","enterKeyNav('rowModeOfPayment"+(i+1)+"'\,event.keyCode)");
				modeOfPaymentDom[i].disabled=false;
				modeOfPaymentDom[i].value =getModeOfPaymntTypeCash();
				chequeNODom[i].setAttribute("readOnly",true);
				chequeNODom[i].setAttribute("tabindex","-1");
				chequeDateDom[i].setAttribute("readOnly",true);
				chequeDateDom[i].setAttribute("tabindex","-1");
				banknameAndBranchDom[i].setAttribute("readOnly",true);
				banknameAndBranchDom[i].setAttribute("tabindex","-1");
			}//close for validation For Cod Lc TO Pay
			relationDom[i].disabled=true;
			idProofDom[i].disabled=true;
			if(product==getQseries() || product==getCseries()){
				//check mandatory fields(For Credit card) Contact number and mobile number ,idProof, idNumber & relation
				if(product==getCseries()){
					relationDom[i].disabled=false;
					relationDom[i].removeAttribute("tabindex");
					idProofDom[i].removeAttribute("tabindex");
					idProofDom[i].disabled=false;
					compnySealDom[i].setAttribute("onkeydown","enterKeyNav('rowRelationId"+(i+1)+"'\,event.keyCode)");
					relationDom[i].setAttribute("onkeydown","enterKeyNav('rowIdentityProofId"+(i+1)+"'\,event.keyCode)");
					idProofDom[i].setAttribute("onkeydown","enterKeyNav('rowIdNumber"+(i+1)+"'\,event.keyCode)");
					idNumDom[i].setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+(i+1)+"','rowIdNumber')");
					idNumDom[i].readOnly=false;
					idNumDom[i].removeAttribute("tabindex");
				}//end of C-series validations
				
			}//end of CC & Q series validations
			
			

		}//close For Loop
	}else{
		alert("In-valid Grid details");
		return false;
	}
	return true;
}

function populateDlvTypeAndCompSeal(rowId){
	var dlvTypeDom = getDomElementById("rowDeliveryType"+rowId);
	var companySealDom = getDomElementById("rowCompanySealSign"+rowId);
	if(dlvTypeDom!=null && !dlvTypeDom.readOnly){
		dlvTypeDom.value=getOfficeDlvType();
		setFocusOnDom(dlvTypeDom);
	}
	if(companySealDom!=null && !companySealDom.readOnly){
		companySealDom.value=getCompanySealAndSign();
	}
}
function enableModeOfpaymentForMDrs(rowId){
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);

	var product=getCnoteProduct(consignmentNumber.value);
	if(product==getLseries() || product==getTseries() || product==getDseries()){
		enablePaymentModeOnlyOnce();
	}
}

function isProductValidForPayment(product){
	var isValid=false;
	if(product==getLseries() || product==getTseries() || product==getDseries()){
		isValid=true;
	}
	return isValid;
}
function isPaymentRequiredForLTDSeries(rowId,product){
	var isValid=false;
	if(isProductValidForPayment(product)){
		var cnType=getDomElementById("rowParentChildCnType"+rowId).value;
		if(isNull(cnType)){
			var rowNum=rowId+1;
			alert(" Given Consignment is not properly populated at row :"+rowNum);
			return false;
		}
		if(cnType=="P"){
			isValid=true;
		}
		
	}
	return isValid;
}
