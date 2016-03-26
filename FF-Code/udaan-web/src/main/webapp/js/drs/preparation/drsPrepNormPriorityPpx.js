// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsPrepNpPpx';
/** refer drsCommon.js */
formId='npDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to generate DRS ?";
/** for Clear screen */
pageLoadUrl= "./prepNormPpxDrs.do?submitName=viewPrepareDrsPage";

/** for Drs Discard */
dicardDrsUrl="./prepNormPpxDrs.do?submitName=discardDrs";

/** for Modify Drs  and it's a Ajax call ,
 * In-order to process the response  developer must implement a method with the name :ajaxResponseAfterModify*/
modifyDrsUrl="./prepNormPpxDrs.do?submitName=modifyDrs";

/** for Drs Print */
printDrsUrl="./deliveryPrintDrs.do?submitName=printNormalParcelDrs&drsNumber=";

/**  No of rows to be allowed in the Grid*/
maxAllowedRows=24;
/** Make sure that for add a new Row always use method Name as addRow() across the Module*/
/**
 * Jquery method to be called on load of the jsp Document
 */
$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
					"sScrollX": "100%",
					"sScrollXInner":"110%",
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
				/**call load method :  initialization*/
				load();
				
				/**Apply events to buttons*/
				applyButtonEvents();
			} );
/**
 * Start up method for Row Creation  and other enable/disable the Fields
 */
function load(){
	disableForDrsPrepOnLoad();
	
	/**If on page load of DRS set focus on DRS-FOR Field if it's new page*/
	setFocusOnDrsFor();
	/**If on page load of Existing drsDRS set focus on DRS-FOR Field if it's new page*/
	disableHeaderForPreparation();
	
	/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
	disableForUser();
	/**set max rows for screen*/
	setMaxRowsForDrs();
	
}
/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){
	/** On click of the Delete button*/
	$('#Delete').click(function() {
		/** delete function call*/
		deleteNpDoxRow();
	});
	
	/** On click of the save button*/
	$('#Save').click(function() {
		/** delete function call*/
		saveDrsDetails();
	});
	
	/** On click of the save button*/
	$('#Discard').click(function() {
		discardDrs();
	});
	/** On click of the save button*/
	$('#Modify').click(function() {
		modifyDrs();
	});
	$('#Cancel').click(function() {
		clearScreen();
	});
	
	/** On click of the print button*/
	$('#Print').click(function() {
		printDrsNormPpx();
	});
	applyEventForOpenDrsForPrep();
}
/**
 * fnClickAddRow : for Add row functionality
 *  Make sure that for add a new Row always use method Name as addRow() across the Module
 */
function addRow(){
	var tempRow=rowCount;
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount,
	                                           
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber"  onchange="validateConsignment(\''+ tempRow +'\')" onblur="isEligibleToAddRow(\''+ tempRow +'\')" class="txtbox width140" onkeydown="return focusOnNextRowOnEnterForDrsPrep(event.keyCode,this,\''+tableId+'\',\'rowConsignmentNumber\',\'rowConsignmentNumber\')" maxlength="12"/>\
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowParentChildCnType'+ rowCount +'" name="to.rowParentChildCnType"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width110" readOnly="true" tabindex="-1"/> \
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />',
	                                         
	                                           '<input type="text" id="rowNoOfPieces'+ rowCount +'" name="to.rowNoOfPieces"  readOnly="true" tabindex="-1" class="txtbox width50"/>',
	                                           '<input type="text" id="rowActualWeight'+ rowCount +'" name="to.rowActualWeight"  readOnly="true" tabindex="-1" class="txtbox width50"/>',
	                                           '<input type="text" id="rowChargeableWeight'+ rowCount +'" name="to.rowChargeableWeight"  readOnly="true" tabindex="-1" class="txtbox width50"/>',
	                                           '<input type="text" id="rowContentName'+ rowCount +'" name="to.rowContentName"  readOnly="true" tabindex="-1" class="txtbox width100"/>\
	                                           <input type="hidden" id="rowContentId'+ rowCount +'" name="to.rowContentId"/>',
	                                           '<input type="text" id="rowPaperworkName'+ rowCount +'" name="to.rowPaperworkName"  readOnly="true" tabindex="-1" class="txtbox width100" />\
	                                           <input type="hidden" id="rowPaperworkId'+ rowCount +'" name="to.rowPaperworkId"/>',
	                                           '<input type="text" id="rowAmount'+ rowCount +'" name="to.rowAmount"  readOnly="true" tabindex="-1" class="txtbox width90"/>',
	                                           '<input type="text" id="rowConsineeName'+ rowCount +'" name="to.rowConsineeName"  readOnly="true" tabindex="-1" class="txtbox width100" />',
	                                           ] );
	rowCount++;
	/**inspecting Whether delete functionality 
	 * executed at least once. 
	 * if so start calling updateGrid method for updating serial numbers */
	if(isDeleted){
		updateGrid(tableId);
	}
	focusOnElementWithId('rowConsignmentNumber',tempRow);
	clearDrsNumber();
	
}
/**
 * deleteNpDoxRow
 */
function deleteNpDoxRow(){
	if(isCheckBoxSelectedWithMessage('chkbx','Please select the check box to be deleted')){
		deleteRow(tableId,'chkbx');
	}
}
/**
 * validateConsignment : to validate consg details 
 * @param rId
 */
function validateConsignment(rId){
	var rowId=parseInt(rId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	if(isNull(consignmentNumber.value)){
		alert("Please enter consignment number");
		clearGrid(rowId);
		consignmentNumber.focus();
		return;
	}
	/**Convert to upper case and make sure that Consg Number should exist in db with Uppercase letters*/
	consignmentNumber.value=convertToUpperCase(consignmentNumber.value);
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkAllowedConsignments(consignmentNumber)&& checkDuplicateConsignments(consignmentNumber)){
		/** Ajax call to validate Consignment number*/
		var url="./prepNormPpxDrs.do?submitName=ajaxValidateConsignment&consgNumber="+consignmentNumber.value;
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
		clearGrid(rowId);
		consignmentNumber.value="";
		consignmentNumber.focus();
		return;
	}
}
function ajaxResponseForConsignment(ajaxResp,rowId){
	//alert("ajaxResp"+ajaxResp);
	var consgDetails=null;
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			clearGrid(rowId);
			setFocusOnDom(consignmentNumber);
		}else{
			consgDetails=responseText;
			//populate Consignment details
			populateGrid(consgDetails,rowId);
			addRowAfterResponse(rowId,tableId);
		}
	}else{
		alert("Invalid response,please Re-try...");
		clearGrid(rowId);
		setFocusOnDom(consignmentNumber);
	}

}

function clearGrid(rowId){
	getDomElementById("rowOriginCityId"+rowId).value="";
	getDomElementById("rowOriginCityCode"+rowId).value="";
	getDomElementById("rowOriginCityName"+rowId).value="";
	getDomElementById("rowConsignmentNumber"+rowId).value="";
	getDomElementById("rowConsignmentId"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
	
	getDomElementById("rowActualWeight"+rowId).value="";
	getDomElementById("rowChargeableWeight"+rowId).value="";
	getDomElementById("rowContentName"+rowId).value="";
	getDomElementById("rowContentId"+rowId).value="";
	getDomElementById("rowPaperworkName"+rowId).value="";
	
	getDomElementById("rowPaperworkId"+rowId).value="";
	getDomElementById("rowAmount"+rowId).value="";
	getDomElementById("rowConsineeName"+rowId).value="";
	getDomElementById("rowParentChildCnType"+rowId).value="";
	getDomElementById("rowNoOfPieces"+rowId).value="";
	
}
function populateGrid(consgDetails,rowId){
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	getDomElementById("rowOriginCityName"+rowId).value=consgDetails.cityName;
	
	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;
	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;
	if(!isNull(consgDetails.actualWeight)){
	getDomElementById("rowActualWeight"+rowId).value=consgDetails.actualWeight;
	}
	if(!isNull(consgDetails.finalWeight)){
	getDomElementById("rowChargeableWeight"+rowId).value=consgDetails.finalWeight;
	}
	/** populate contents & paperworks*/
	populateContentAndPaperwork(consgDetails,rowId);
	/*if(!isNull(consgDetails.otherAmount)){
		getDomElementById("rowAmount"+rowId).value=consgDetails.otherAmount;
	}*/
	if(!isNull(consgDetails.consigneeTO)){
		var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
		if(getCnoteProduct(consignmentNumber.value)==getQseries()){
			var fname=consgDetails.consigneeTO.firstName;
			var lname=consgDetails.consigneeTO.lastName;
			var name=(!isNull(fname)?fname:"")+" "+(!isNull(lname)?lname:"");
			getDomElementById("rowConsineeName"+rowId).value=name;
		}
	}
	getDomElementById("rowParentChildCnType"+rowId).value=consgDetails.parentChildCnType;
	
	getDomElementById("rowNoOfPieces"+rowId).value=consgDetails.noOfPcs;
	checkIsOctroiAmountAlreadyPopulated(rowId, consgDetails);
}
/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(headerValidationsForSave()&& validateBasicGridValidations()&& promptConfirmation(savePromptMsg)){
	var url="./prepNormPpxDrs.do?submitName=savePrepareDrs";
	ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}
/**
 * saveDrsDetails ::ajaxResponseAfterSave
 * @param ajaxResp
 */
function ajaxResponseAfterSave(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	var drsToObject=null;
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			clearScreenWithoutConfirmation();
		}else {
			drsToObject=responseText;
			showSuccessAftersave(drsToObject);
		}
	}else{
		alert(" Error in saving ");
	}
}
/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./prepNormPpxDrs.do?submitName=findDrsDetailsByDrsNumber";
		globalFormSubmit(url,formId);
	}
}
/**
 * ajaxResponseAfterModify
 * @param ajaxResp
 */
function ajaxResponseAfterModify(ajaxResp){
	ajaxResponseAfterSave(ajaxResp);
}

