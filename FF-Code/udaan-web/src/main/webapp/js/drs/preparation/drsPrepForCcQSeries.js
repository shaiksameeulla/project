/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsPrepCcqDox';
/** refer drsCommon.js */
formId='creditCardDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to generate DRS ?";

pageLoadUrl= "./prepCcqDoxDrs.do?submitName=viewPrepareDrsPage";
/** for DRS Discard */
dicardDrsUrl="./prepCcqDoxDrs.do?submitName=discardDrs";

/** for Modify Drs  and it's a Ajax call ,
 * In-order to process response  developer must implement a method with the name :ajaxResponseAfterModify*/
modifyDrsUrl="./prepCcqDoxDrs.do?submitName=modifyDrs";

/** for Drs Print */
printDrsUrl="./deliveryPrintDrs.do?submitName=printCreditCardDrs&drsNumber=";

/**  No of rows to be allowed in the Grid*/
maxAllowedRows=48;
/**
 * Jquery method to be called on load of the jsp Document
 */
$(document).ready( function () {
	var oTable = $('#'+tableId).dataTable( {
		"sScrollY": "125",
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
 * fnClickAddRow : for Add row functionality
 * Make sure that for add a new Row always use method Name as addRow() across the Module
 */
function addRow(){
	var tempRow=rowCount;
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount,
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignment(\''+ tempRow +'\')"  onblur="isEligibleToAddRow(\''+ tempRow +'\')" onkeydown="return focusOnNextRowOnEnterForDrsPrep(event.keyCode,this,\''+tableId+'\',\'rowConsignmentNumber\',\'rowConsignmentNumber\')" class="txtbox width140"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width140" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="consigneeName'+ rowCount +'" name="to.consigneeName" class="txtbox width140" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="consigneeMailingAddress'+ rowCount +'" name="to.consigneeMailingAddress" class="txtbox width140" readOnly="true" tabindex="-1"/>',
	                                           '<input type="text" id="consigneeNonMailingAddress'+ rowCount +'" name="to.consigneeNonMailingAddress" class="txtbox width140" readOnly="true" tabindex="-1"/>\
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
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
		deleteCCDoxRow();
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
		printCreditCardDrs();
	});
	applyEventForOpenDrsForPrep();
}

/**
 * saving the drs details
 */
function saveDrsDetails(){
	if(headerValidationsForSave()&& validateBasicGridValidations()&& promptConfirmation(savePromptMsg)){
	var url="./prepCcqDoxDrs.do?submitName=savePrepareDrs";
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
		var url="./prepCcqDoxDrs.do?submitName=ajaxValidateConsignment&consgNumber="+consignmentNumber.value;
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

function clearGrid(rowId){
	getDomElementById("rowOriginCityId"+rowId).value="";
	getDomElementById("rowOriginCityCode"+rowId).value="";
	getDomElementById("rowOriginCityName"+rowId).value="";
	getDomElementById("rowConsignmentNumber"+rowId).value="";
	getDomElementById("rowConsignmentId"+rowId).value="";
	getDomElementById("consigneeMailingAddress"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
}
function populateGrid(consgDetails,rowId){
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	getDomElementById("rowOriginCityName"+rowId).value=consgDetails.cityName;
	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;
	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;
	if(!isNull(consgDetails.consigneeTO)){
		var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
			var fname=consgDetails.consigneeTO.firstName;
			var lname=consgDetails.consigneeTO.lastName;
			var name=(!isNull(fname)?fname:"")+" "+(!isNull(lname)?lname:"");
			getDomElementById("consigneeName"+rowId).value=name;
		
			if(getCnoteProduct(consignmentNumber.value)==getCseries()){
			var address=consgDetails.consigneeTO.address;
			if(!isNull(address)){
			getDomElementById("consigneeMailingAddress"+rowId).value=address;
			}
		var email=consgDetails.consigneeTO.email;
		if(!isNull(email)){
			getDomElementById("consigneeNonMailingAddress"+rowId).value=email;
			}
		}
	}
	populateParentChildCnType(consgDetails,rowId);
}



/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./prepCcqDoxDrs.do?submitName=findDrsDetailsByDrsNumber";
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
	function deleteCCDoxRow(){
		if(isCheckBoxSelectedWithMessage('chkbx','Please select the check box to be deleted')){
			deleteRow(tableId,'chkbx');
		}
	}
	

