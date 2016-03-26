// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 * -------------
 *  Business Rules:
 * --------------
 *  
 *  5.	The Pending Reason Code field is a drop down entry and the format of the entries in
 *  	 the list should be ‘Code-Pending Reason’ (Eg: 110 –Door locked).
*	6.	Missed You Card No. is enabled/entered only 
*	when the Pending Reason is ‘Door Locked’ or Person not available
 * 
 * 
 * 
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdate';
/** refer drsCommon.js */
formId='bulkCnPendingDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to update DRS ?";
/** for Clear screen */
pageLoadUrl= "./bulkCnPendingDrs.do?submitName=viewBulkPendingDrsPageForBranch";

/** mentioning no of rows (as 30) in illogical here , 
 * but no of rows should fetch from db ie no of child records for given a drs number to be set here  */
maxAllowedRows=30;
var isMissedCardVldated=false;


$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
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
				/**Apply events to buttons*/
				applyButtonEvents();
				disableHeader();
				disableGrid();
				
				/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
				disableForUser();
				setMaxRowsForDrs();
				addRowForPendingConsg();
				
} );


/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){
	/** On click of the Delete button*/
	$('#Delete').click(function() {
		/** delete function call*/
		deletePendingDrsRow();
	});
	
	/** On click of the save button*/
	$('#Save').click(function() {
		/** updateUndeliveredDrsConsg*/
		updateUndeliveredDrsConsg();
	});
	
	$('#Cancel').click(function() {
		/** Clear screen  call*/
		clearScreen();
	});
	removeFsInEventsIfDrsNotOpnd();
}

function addRowForPendingConsg(){
	//var consgDom=document.getElementsByName("to.rowConsignmentNumber");
	//&& (isDrsUpdated() && maxAllowedRows != rowCount+1)
	var canUpdate=getDomElementById("canUpdate");
	if(isNewDrs() && canUpdate.value != flagNo()){
		addRow();
	}
}

/**
 * fnClickAddRow : for Add row functionality
 */
function addRow(){
	var tempRow=rowCount;
	try{
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ tempRow +'" name="chkbx" value="'+tempRow+'" tabindex="-1" />',
	                                           tempRow+"",
	                                           '<input type="text" id="rowConsignmentNumber'+ tempRow +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignmentForBulkDrs(\''+ tempRow +'\')" class="txtbox width145" onkeypress = "return enterKeyNav(\'rowRemarks'+ tempRow +'\',event.keyCode);"/>',
	                                           '<input type="text" id="rowOriginCityName'+ tempRow +'" name="to.rowOriginCityName" class="txtbox width140" readOnly="true" tabindex="-1"/> \
	                                           <input type="hidden" id="rowOriginCityId'+ tempRow +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ tempRow +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ tempRow +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ tempRow +'" name="to.rowOriginCityCode" />',
	                                           '<input type="text" id="rowRemarks'+ tempRow +'" name="to.rowRemarks" class="txtbox width100" maxlength="30" tabindex="-1" onkeypress="return isBulkDrsAddRowRequired(event.keyCode,\''+tempRow+'\',this)"/> \
	                                           <input type="hidden" id="rowAlreadyAddedRow'+ tempRow +'" name="to.rowAlreadyAddedRow" /> \
	                                           <input type="hidden" id="rowDeliveryDetailId'+ tempRow +'" name="to.rowDeliveryDetailId" /> \
	                                           <input type="hidden" id="rowParentChildCnType'+ tempRow +'" name="to.rowParentChildCnType" />',
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
	
	if(rowCount==2){
		setFocusOnDomId('pendingReasonForBulkCn');
	}
}

function addRowAfterResponseForMDrs(rowId,tableId){
	if(rowId!=null ){
		var cn=getDomElementById("rowConsignmentNumber"+rowId);
		var attmptNumber=getDomElementById("rowAttemptNumber"+rowId);
		var cnListdom=document.getElementsByName("to.rowConsignmentNumber");
		var rowsLength=null;
		if(!isNull(cnListdom)){
			rowsLength= cnListdom.length;
		}
		if(!isNull(rowsLength)&& rowsLength >0){
			var lastId=rowsLength-1;
			var lastRowId =getRowId(cnListdom[lastId],'rowConsignmentNumber');
			if(!isNull(cn.value)&& !isNull(cnListdom[lastId].value) && rowId == lastRowId ){
				if(rowsLength == maxAllowedRows ){
					if(!isMaxAllowdRowsVldtd){
						alert("Maximum CN number allowed per DRS No. is :"+maxAllowedRows);
						isMaxAllowdRowsVldtd=true;
					}
					/** set  a flag ,to make sure that alert the message only one time*/
					
					return false;
				}
				//make sure current row consgId and last  row Consid is not null
				if((!isConsgNumNull(rowId)&& !isConsgNumNull(lastRowId) )&&(!isConsigmentIdNull(rowId) && !isConsigmentIdNull(lastRowId))|| (!isNull(attmptNumber)&& !isNull(attmptNumber.value))){
					addRow();
				}
			}

		}

	}
}

function addNewPendingRow(rowId){
	if(validateMandatoryForAdd(rowId)){
		addRowAfterResponseForMDrs(rowId,tableId);
	}
}


/**
 * deleteNpDoxRow
 */
function deletePendingDrsRow(){
	if(isCheckBoxSelectedWithMessage('chkbx','Please select the check box to be deleted')){
		deleteRow(tableId,'chkbx');
	}
}
/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./bulkCnPendingDrs.do?submitName=findDrsDetailsByDrsNumber";
		globalFormSubmit(url,formId);
	}
}
function disableHeader(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		dropdownDisable();
		getDomElementById("Cancel").disabled=false;
		getDomElementById("Save").disabled=false;
		
		var printDom= getDomElementById("Print");
		if(printDom!=null){
			printDom.disabled=false;
		}
		getDomElementById("Delete").disabled=false;
	}
}
function disableGrid(){
	if(!isNewDrs()){
		/*var tableee = getDomElementById(tableId);
		var totalRowCount =  tableee.rows.length;*/
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var originCityDom=document.getElementsByName("to.rowOriginCityName");
		
		var remarksDom= document.getElementsByName("to.rowRemarks");
		
		for(var i=0;i<consgDom.length;i++) {
			if(consgDom==null || consgDom[i]==null){
				break;
			}
			consgDom[i].setAttribute("readOnly",true);
			consgDom[i].setAttribute("tabindex","-1");
			
			originCityDom[i].setAttribute("readOnly",true);
			originCityDom[i].setAttribute("tabindex","-1");

			remarksDom[i].setAttribute("readOnly",true);
			remarksDom[i].setAttribute("tabindex","-1");
		}
	}
}

/**
 * validateConsignmentByDrsNumber
 * @param rId
 */
function validateConsignmentForBulkDrs(rId){
	var rowId=parseInt(rId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	if(isNull(consignmentNumber.value)){
		alert("Please enter consignment number");
		setFocusOnDom(consignmentNumber);
		return;
	}
	
	/**Convert to upper case and make sure that Consg Number should exist in db with Uppercase letters*/
	consignmentNumber.value=convertToUpperCase(consignmentNumber.value);
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkDuplicateConsignments(consignmentNumber)){
		/** Ajax call to validate Consignment number by DRS number*/
		var url="./bulkCnPendingDrs.do?submitName=ajaxValidateConsignment&consgNumber="+consignmentNumber.value;
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
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
	//alert("ajaxResp"+ajaxResp);
	var deliveryDetailsTo=null;
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	//var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			clearGrid(rowId);
			setFocusOnDom(consignmentNumber);
		}else{
			deliveryDetailsTo=responseText;
			//populate Consignment details
			populateGrid(deliveryDetailsTo,rowId);
			addRowAfterResponse(rowId,tableId);
			//setFocusOnDom(reasonDom);
		}
	}else{
		alert("Invalid response,please Re-try...");
		clearGrid(rowId);
		setFocusOnDom(consignmentNumber);
	}

}
/**
 * clearGrid : clear grid
 * @param rowId
 */
function clearGrid(rowId){
	getDomElementById("rowOriginCityId"+rowId).value="";
	getDomElementById("rowOriginCityCode"+rowId).value="";
	getDomElementById("rowOriginCityName"+rowId).value="";
	getDomElementById("rowConsignmentNumber"+rowId).value="";
	getDomElementById("rowConsignmentId"+rowId).value="";
	getDomElementById("rowRemarks"+rowId).value="";
	getDomElementById("rowParentChildCnType"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
}


/**
 * populateGrid 
 * @param deliveryDetlTO
 * @param rowId
 */
function populateGrid(consgDetails,rowId){
	
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	getDomElementById("rowOriginCityName"+rowId).value=consgDetails.cityName;
	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;
	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;
	populateParentChildCnType(consgDetails, rowId);
}
/**
 * updateUndeliveredDrsConsg 
 */
function updateUndeliveredDrsConsg(){
	if(validateHeaderForBulkDrsSave()&& validateBasicGridValidations()&& promptConfirmation(savePromptMsg)){
		var url="./bulkCnPendingDrs.do?submitName=updatePendingDrs";
		enableAll();
		ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}

function validateHeaderForBulkDrsSave(){
	
		var drsDom= getDomElementById('drsNumber');
		
		var pendingReasonDom= getDomElementById('pendingReasonForBulkCn');
		var ypDrsDom= getDomElementById('ypDrs');
		
		if(isNewDrs()){

			drsDom.value="";
			if(isNull(pendingReasonDom.value)){
				alert("Please select pending reason");
				setFocusOnDom(pendingReasonDom);
				return false;
			}
			if(isNull(ypDrsDom.value)){
				alert("Please select DRS-Type Details");
				setFocusOnDom(ypDrsDom);
				return false;
			}
			
			return true;
		}else {
			return false;
		}
}

	function ajaxResponseAfterSave(ajaxResp){
		//alert("ajaxResp"+ajaxResp);
		disableHeader();
		var drsToObject=null;
		if (!isNull(ajaxResp)) {
			var responseText =jsonJqueryParser(ajaxResp); 
			var error = responseText[ERROR_FLAG];
			if(responseText!=null && error!=null){
				alert(error);
				clearScreenWithoutConfirmation();
			}else {
				drsToObject=responseText;
				alert(drsToObject.successMessage);
				getDomElementById("deliveryId").value=drsToObject.deliveryId;
				getDomElementById('drsNumber').value=drsToObject.drsNumber;
				//getDomElementById('fsOutTime').value=drsToObject.fsOutTimeStr;
				getDomElementById('fsInTimeDateStr').value=drsToObject.fsInTimeDateStr;
				disableAll();
				enableGlobalButton('Cancel');
				}
		}
}


function validateMandatoryForAdd(rowId){
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	if(isNull(consignmentNumber.value)){
		return false;
	}
	
	return true;
}

function addNewPendingRow(rowId){
	if(validateMandatoryForAdd(rowId)){
		//alert("addNewPendingRow"+rowCount);
		addRowAfterResponse(rowId,tableId);
	}
}
function isLastRowNonEmpty(){
	var lastRowId=getLastRowBulkDrsId();
	var isLastRowFilled=false;
	if(lastRowId!=null){
		var cnNumDom=getDomElementById("rowConsignmentNumber"+lastRowId);
		var cnIdDom=getDomElementById("rowConsignmentId"+lastRowId);
		
		if(!isNull(cnNumDom.value)){
			isLastRowFilled=true;
		}
		if(!isNull(cnIdDom.value) && (parseInt(cnIdDom.value,10)>0)){
			isLastRowFilled=true;
		}
	}
	return isLastRowFilled;
}

function getLastRowBulkDrsId(){
	var lastRowId=null;
	var consgDom=document.getElementsByName("to.rowConsignmentNumber");
	if(consgDom!=null){
		var lastRowConsgDom= consgDom[consgDom.length-1];
		lastRowId =getRowId(lastRowConsgDom,'rowConsignmentNumber');
	}
	return lastRowId;	
}

function isBulkDrsAddRowRequired(keyCode,lastBulkId,thisDom){
		if(enterKeyNavWithoutFocus(keyCode)){
			var lastRowId=getLastRowBulkDrsId();
			if(isLastRowNonEmpty() && lastRowId==lastBulkId){
				addNewPendingRow(lastBulkId);
			}else {
				var currentDomId = getDomElementById("rowConsignmentNumber"+lastBulkId);
				var domByName = document.getElementsByName("to.rowConsignmentNumber");
				if(domByName!=null && domByName.length>0){
					for(var i=0;i<domByName.length;i++){
						if(domByName[i]==currentDomId){
							//alert("Eql"+domElement.value)
							if(i == domByName.length-1){
								setFocusOnDomId('Save');
							}else if((i+1)<domByName.length){
								var j=i+1;
								setFocusOnDom(domByName[j]);
							}
						}
					}

				}

			
				
			}
		}
}
