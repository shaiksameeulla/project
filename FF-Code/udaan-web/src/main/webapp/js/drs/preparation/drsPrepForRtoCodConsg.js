
// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsPrepRtoCod';
/** refer drsCommon.js */
formId='rtoCodDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to generate DRS ?";
/** for Clear screen */
pageLoadUrl= "./prepRtoCodDrs.do?submitName=viewPrepareDrsPage";

/** for Drs Discard */
dicardDrsUrl="./prepRtoCodDrs.do?submitName=discardDrs";
/** for Modify Drs  and it's a Ajax call ,
 * In-order to process response  developer must implement a method with the name :ajaxResponseAfterModify*/
modifyDrsUrl="./prepRtoCodDrs.do?submitName=modifyDrs";

/** for Drs Print */
printDrsUrl="./deliveryPrintDrs.do?submitName=printRtoCodDrs&drsNumber=";

/**  No of rows to be allowed in the Grid*/
maxAllowedRows=48;
/** Make sure that for add a new Row always use method Name as addRow() across the Module*/
/**
 * Jquery method to be called on load of the jsp Document
 */
$(document).ready( function () {
	var oTable = $('#'+tableId).dataTable( {
		"sScrollY": "160",
		"sScrollX": "100%",
		"sScrollXInner":"185%",
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
	disableYpDrs();
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
	
	$('#Print').click(function() {
		printRtoCodDrs();
	});
	
	applyEventForOpenDrsForPrep();
}
/**
 * fnClickAddRow : for Add row functionality:
 *  Make sure that for add a new Row always use method Name as addRow() across the Module
 */
function addRow(){
	var tempRow=rowCount;
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount,
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber"  onchange="validateConsignment(\''+ tempRow +'\')" onblur="isEligibleToAddRow(\''+ tempRow +'\')" onkeydown="return focusOnNextRowOnEnterForDrsPrep(event.keyCode,this,\''+tableId+'\',\'rowConsignmentNumber\',\'rowConsignmentNumber\')" class="txtbox width110" maxlength="12"/>\
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowParentChildCnType'+ rowCount +'" name="to.rowParentChildCnType"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width80" readOnly="true" tabindex="-1"/> \
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />',
	                                           
	                                           '<input type="text" id="rowReferenceNumber'+ rowCount +'" name="to.rowReferenceNumber" class="txtbox width80" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowContentName'+ rowCount +'" name="to.rowContentName"  readOnly="true" tabindex="-1" class="txtbox width110"/>\
	                                           <input type="hidden" id="rowContentId'+ rowCount +'" name="to.rowContentId"/>',
	                                           '<input type="text" id="rowConsignorName'+ rowCount +'" name="to.rowConsignorName" class="txtbox width110" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowConsignorCode'+ rowCount +'" name="to.rowConsignorCode" class="txtbox width110" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowVendorName'+ rowCount +'" name="to.rowVendorName" class="txtbox width110" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowVendorCode'+ rowCount +'" name="to.rowVendorCode" class="txtbox width110" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowCodAmount'+ rowCount +'" name="to.rowCodAmount" class="txtbox width60" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="rowLCAmount'+ rowCount +'" name="to.rowLCAmount" class="txtbox width60" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowToPayAmount'+ rowCount +'" name="to.rowToPayAmount" class="txtbox width60" readonly="readonly" tabindex="-1"/>',
	                                          
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


function validateConsignment(rId){
	var rowId=parseInt(rId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	if(isNull(consignmentNumber.value)){
		alert("Please enter consignment number");
		setFocusOnDom(consignmentNumber);
		clearGrid(rowId);
		return;
	}
	/**Convert to upper case and make sure that Consg Number should exist in db with Uppercase letters*/
	consignmentNumber.value=convertToUpperCase(consignmentNumber.value);
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkAllowedConsignments(consignmentNumber)&& checkDuplicateConsignments(consignmentNumber)){
		/** Ajax call to validate Consignment number*/
		var url="./prepRtoCodDrs.do?submitName=ajaxValidateConsgForRtoCodDrs&consgNumber="+consignmentNumber.value;
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
		clearGrid(rowId);
		consignmentNumber.value="";
		setFocusOnDom(consignmentNumber);
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
/**
 * clearGrid :: to clear the Grid
 * @param rowId
 */
function clearGrid(rowId){
	getDomElementById("rowOriginCityId"+rowId).value ="";
	getDomElementById("rowOriginCityCode"+rowId).value ="";
	getDomElementById("rowOriginCityName"+rowId).value ="";

	getDomElementById("rowAttemptNumber"+rowId).value ="";

	getDomElementById("rowConsignmentId"+rowId).value ="";
	getDomElementById("rowConsignmentNumber"+rowId).value ="";

	getDomElementById("rowReferenceNumber"+rowId).value ="";

	getDomElementById("rowContentName"+rowId).value ="";
	getDomElementById("rowContentId"+rowId).value ="";

	getDomElementById("rowConsignorName"+rowId).value ="";
	getDomElementById("rowConsignorCode"+rowId).value ="";

	getDomElementById("rowVendorName"+rowId).value ="";
	getDomElementById("rowVendorCode"+rowId).value ="";

	getDomElementById("rowCodAmount"+rowId).value ="";
	getDomElementById("rowParentChildCnType"+rowId).value ="";
}
/**
 * populateGrid based on Grid id
 * @param consgDetails
 * @param rowId
 */
function populateGrid(consgDetails,rowId){
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	getDomElementById("rowOriginCityName"+rowId).value=consgDetails.cityName;

	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;

	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;

	if(!isNull(consgDetails.refNo)){
		getDomElementById("rowReferenceNumber"+rowId).value=consgDetails.refNo;
	}

	if(!isNull(consgDetails.customerCode)){
		getDomElementById("rowConsignorName"+rowId).value=consgDetails.customerName;
		getDomElementById("rowConsignorCode"+rowId).value=consgDetails.customerCode;
	}
	if(!isNull(consgDetails.vendorCode)){
		getDomElementById("rowVendorName"+rowId).value=consgDetails.vendorName;
		getDomElementById("rowVendorCode"+rowId).value=consgDetails.vendorCode;
	}
	//getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;

	populateParentChildCnType(consgDetails,rowId);

	populateContentAndPaperwork(consgDetails,rowId);
	populateCodLcAmountIfRequired(rowId, consgDetails);
}




function saveDrsDetails(){
	if(headerValidationsForSave()&& validateBasicGridValidations()&& promptConfirmation(savePromptMsg)){
	var url="./prepRtoCodDrs.do?submitName=savePrepareDrs";
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
		var url="./prepRtoCodDrs.do?submitName=findDrsDetailsByDrsNumber";
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

function disableYpDrs(){
	
	/** Since RTO Cod Drs will not have YP drs we are making inactive*/
	var ypDrs=getDomElementById("ypDrs");
	ypDrs.style.display="none";
	getDomElementById("ypDrstd1").style.display="none";
	getDomElementById("ypDrstd2").style.display="none";
	
	var drsType=getDomElementById("drsPartyId");
	drsType.removeAttribute("onkeydown");
//	drsType.onkeydown="return enterKeyNav('loadNumber',event.keyCode);";
	drsType.setAttribute("onkeydown","return enterKeyNav('loadNumber',event.keyCode)");
	/*$('#drsPartyId').onkeydown(function() {
		
	});*/
	
}

