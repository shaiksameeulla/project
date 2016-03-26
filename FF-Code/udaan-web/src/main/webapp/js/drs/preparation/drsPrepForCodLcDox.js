/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsPrepCodLcDox';
/** refer drsCommon.js */
formId='codLcDoxDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to generate DRS ?";

pageLoadUrl= "./prepCodLcDoxDrs.do?submitName=viewPrepareDrsPage";
/** for DRS Discard */
dicardDrsUrl="./prepCodLcDoxDrs.do?submitName=discardDrs";

/** for DRS Modify and include methodName:ajaxResponseAfterModify in this js and 
 * the naming convention for this method name should be same*/
modifyDrsUrl="./prepCodLcDoxDrs.do?submitName=modifyDrs";

/** for Drs Print */
printDrsUrl="./deliveryPrintDrs.do?submitName=printCodLcDoxDrs&drsNumber=";

/**  No of rows to be allowed in the Grid*/
maxAllowedRows=48;
/**
 * Jquery method to be called on load of the jsp Document
 */

$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "225",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
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
 * fnClickAddRow : for Add row functionality
 */
function addRow(){
	var tempRow=rowCount;
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount,
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignment(\''+ tempRow +'\')" onblur="isEligibleToAddRow(\''+ tempRow +'\')" onkeydown="return focusOnNextRowOnEnterForDrsPrep(event.keyCode,this,\''+tableId+'\',\'rowConsignmentNumber\',\'rowConsignmentNumber\')" class="txtbox width140"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width110" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowCodAmount'+ rowCount +'" name="to.rowCodAmount" class="txtbox width110" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowLCAmount'+ rowCount +'" name="to.rowLCAmount" class="txtbox width110" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowToPayAmount'+ rowCount +'" name="to.rowToPayAmount" class="txtbox width110" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowOtherCharges'+ rowCount +'" name="to.rowOtherCharges" class="txtbox width110" readonly="readonly" tabindex="-1"/>',
	                                           '<input type="text" id="rowBaAmount'+ rowCount +'" name="to.rowBaAmount" class="txtbox width110" readonly="readonly" tabindex="-1"/>\
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowParentChildCnType'+ rowCount +'" name="to.rowParentChildCnType"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />'
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
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){
	/** On click of the Delete button*/
	$('#Delete').click(function() {
		/** delete function call*/
		deleteCodLcDoxRow();
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
		printCodLcDrs();
	});
	applyEventForOpenDrsForPrep();
}

/**
 * @desc validates the  grid consignment
 * @param rId
 */
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
		var url="./prepCodLcDoxDrs.do?submitName=ajaxValidateConsignment&consgNumber="+consignmentNumber.value;
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
		consignmentNumber.value="";
		setFocusOnDom(consignmentNumber);
		clearGrid(rowId);
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
	getDomElementById("rowCodAmount"+rowId).value="";
	getDomElementById("rowLCAmount"+rowId).value="";
	getDomElementById("rowToPayAmount"+rowId).value="";
	getDomElementById("rowOtherCharges"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
	getDomElementById("rowBaAmount"+rowId).value="";
}

function populateGrid(consgDetails,rowId){
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	getDomElementById("rowOriginCityName"+rowId).value=consgDetails.cityName;
	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;
	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;
	
	
	/*if(!isNull(consgDetails.codAmount)){
		var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
		if(getCnoteProduct(consignmentNumber.value)==getLseries()){
			getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;
		 }
		if(getCnoteProduct(consignmentNumber.value)==getTseries()){
			getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;
		 }
	}
	
	if(!isNull(consgDetails.lcAmount)){
		var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
		if(getCnoteProduct(consignmentNumber.value)==getDseries()){
			getDomElementById("rowLCAmount"+rowId).value=consgDetails.lcAmount;
		 }
	}
	
	if(!isNull(consgDetails.toPayAmount)){
		var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
		if(getCnoteProduct(consignmentNumber.value)==getTseries()){
			getDomElementById("rowToPayAmount"+rowId).value=consgDetails.toPayAmount;
		}
	}*/
	
	if(!isNull(consgDetails.otherAmount)){
	getDomElementById("rowOtherCharges"+rowId).value=consgDetails.otherAmount;
	}
	populateParentChildCnType(consgDetails,rowId);
	populateCodLcAmountIfRequired(rowId, consgDetails);
}



/**
 * saving the drs details
 */
function saveDrsDetails(){
	if(headerValidationsForSave()&& validateBasicGridValidations()&& promptConfirmation(savePromptMsg)){
	var url="./prepCodLcDoxDrs.do?submitName=saveCodLcDoxPrepareDrs";
	ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}

/**
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
		var url="./prepCodLcDoxDrs.do?submitName=findDrsDetailsByDrsNumber";
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

/**
 * deleteCCDoxRow
 */
function deleteCodLcDoxRow(){
	if(isCheckBoxSelectedWithMessage('chkbx','Please select the check box to be deleted')){
		deleteRow(tableId,'chkbx');
	}
}
