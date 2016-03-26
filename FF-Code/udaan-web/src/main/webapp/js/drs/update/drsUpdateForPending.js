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
formId='pendingDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to update DRS ?";
/** for Clear screen */
pageLoadUrl= "./pendingDrs.do?submitName=viewPendingDrsPage";

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
				setFocusOnFsIntime();
				focusOnDrsForUpdate();
				hideElementByDomId('Print');
				setAddedRowCount();
				disableFsInOutTime();
				addRowForPendingConsg();
				
} );

/*function setAddedRowCount(){
	var addedCount=$('#addedRowCount').val();
	if(!isNull(addedCount)){
		rowCount= parseInt(addedCount,10)+1;
	}
}*/
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
	if(!isNewDrs() && !isDrsClosed() && (canUpdate.value != flagNo())){
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
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount+"",
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignmentByDrsNumber(\''+ tempRow +'\')" class="txtbox width140" onkeypress = "return enterKeyNav(\'rowPendingReasonId'+ rowCount +'\',event.keyCode);" onblur="addNewPendingRow(\''+ tempRow +'\')"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width140" readOnly="true" tabindex="-1"/> \
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />',
	                                           '<select name="to.rowPendingReasonId" id="rowPendingReasonId'+ rowCount +'"  class="selectBox width140" onchange = "clearMissedCardRem(\''+ tempRow +'\');validatePendingReason(\''+ tempRow +'\')" onchange="addNewPendingRow(\''+ tempRow +'\')" onkeypress = "return focusOnPendingDrs(event.keyCode,\''+ tempRow +'\');" class="selectBox"><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowMissedCardNumber'+ rowCount +'" name="to.rowMissedCardNumber" onkeypress = "return enterKeyforMissedCardPendingDrs(\''+ tempRow +'\',event.keyCode,this);"  class="txtbox width100" maxlength="25" readOnly="true" tabindex="-1"/> ',
	                                           '<input type="text" id="rowRemarks'+ rowCount +'" name="to.rowRemarks" class="txtbox width100" maxlength="30" tabindex="-1" onkeypress="return enterKeyNavigationForPendingDrs(event.keyCode,\''+rowCount+'\',\'rowRemarks\')"/>\
	                                           <input type="hidden" id="rowDeliveryDetailId'+ rowCount +'" name="to.rowDeliveryDetailId" />\
	                                           <input type="hidden" id="rowAlreadyAddedRow'+ rowCount +'" name="to.rowAlreadyAddedRow" />',
	                                           ] );
	}catch(e){
		alert(e);
	}
	rowCount++;
	/**inspecting Whether delete functionality 
	 * executed at least once. 
	 * if so start calling updateGrid method for updating serial numbers */
	if(isDeleted){
		updateGrid(tableId);
	}
	focusOnElementWithId('rowConsignmentNumber',tempRow);
	populatePendingReasons(tempRow);
	
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
		var url="./pendingDrs.do?submitName=searchPendingConsignments";
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
		getDomElementById("Print").disabled=false;
		getDomElementById("Delete").disabled=false;
	}
}
function disableGrid(){
	if(!isNewDrs()){
		/*var tableee = getDomElementById(tableId);
		var totalRowCount =  tableee.rows.length;*/
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var originCityDom=document.getElementsByName("to.rowOriginCityName");
		var reasonDom=  document.getElementsByName("to.rowPendingReasonId");
		var missedCardNumDom= document.getElementsByName("to.rowMissedCardNumber");
		var remarksDom= document.getElementsByName("to.rowRemarks");
		
		for(var i=0;i<consgDom.length;i++) {
			if(consgDom==null || consgDom[i]==null){
				break;
			}
			consgDom[i].setAttribute("readOnly",true);
			consgDom[i].setAttribute("tabindex","-1");
			
			originCityDom[i].setAttribute("readOnly",true);
			originCityDom[i].setAttribute("tabindex","-1");
			
			
			
			reasonDom[i].disabled=true;
			missedCardNumDom[i].setAttribute("readOnly",true);
			missedCardNumDom[i].setAttribute("tabindex","-1");
			
			remarksDom[i].setAttribute("readOnly",true);
			remarksDom[i].setAttribute("tabindex","-1");
		}
	}
}

/**
 * validateConsignmentByDrsNumber
 * @param rId
 */
function validateConsignmentByDrsNumber(rId){
	var rowId=parseInt(rId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var drsDom= getDomElementById('drsNumber');
	
	if(isNull(consignmentNumber.value)){
		alert("Please enter consignment number");
		setFocusOnDom(consignmentNumber);
		return;
	}
	if(isNull(drsDom.value)){
		alert("Please provide DRS number(please find the data first)");
		drsDom.value="";
		setFocusOnDom(drsDom);
		return;
	}
	/**Convert to upper case and make sure that Consg Number should exist in db with Uppercase letters*/
	consignmentNumber.value=convertToUpperCase(consignmentNumber.value);
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkDuplicateConsignments(consignmentNumber)){
		/** Ajax call to validate Consignment number by DRS number*/
		var url="./pendingDrs.do?submitName=ajaxValidateConsignmentByDrsNumber&consgNumber="+consignmentNumber.value+"&drsNumber="+drsDom.value;
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
	var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
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
			//addRowAfterResponse(rowId,tableId);
			setFocusOnDom(reasonDom);
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
	getDomElementById("rowDeliveryDetailId"+rowId).value="";
	var missedCardDom = getDomElementById("rowMissedCardNumber"+rowId);
	if(missedCardDom!=null){
		missedCardDom.value="";
		//missedCardDom.readOnly=false;
		//missedCardDom.removeAttribute("tabindex");
		missedCardDom.setAttribute("readOnly",true);
		missedCardDom.setAttribute("tabindex","-1");
	}
	getDomElementById("rowPendingReasonId"+rowId).value="";
	getDomElementById("rowRemarks"+rowId).value="";
}

function clearMissedCardRem(rowId){
	var missedCardDom = getDomElementById("rowMissedCardNumber"+rowId);
	if(missedCardDom!=null){
		missedCardDom.value="";
		
	}
	getDomElementById("rowRemarks"+rowId).value="";
}
/**
 * populateGrid 
 * @param deliveryDetlTO
 * @param rowId
 */
function populateGrid(deliveryDetlTO,rowId){
	if(deliveryDetlTO.originCityTO!=null){
		getDomElementById("rowOriginCityId"+rowId).value=deliveryDetlTO.originCityTO.cityId;
		getDomElementById("rowOriginCityCode"+rowId).value=deliveryDetlTO.originCityTO.cityCode;
		getDomElementById("rowOriginCityName"+rowId).value=deliveryDetlTO.originCityTO.cityName;
		var consgTo= deliveryDetlTO.consignmentTO;
		if(!isNull(consgTo)){
			getDomElementById("rowConsignmentId"+rowId).value=consgTo.consgId;
		}
	}
	getDomElementById("rowDeliveryDetailId"+rowId).value=deliveryDetlTO.deliveryDetailId;
}
/**
 * updateUndeliveredDrsConsg 
 */
function updateUndeliveredDrsConsg(){
	if(headerValidationsForSave()&& validateFSInTime('drs')&& validateGridValidations()&& promptConfirmation(savePromptMsg)){
		var url="./pendingDrs.do?submitName=updateUndeliveredDrs";
		enableAll();
		ajaxJquery(url,formId,ajaxResponseAfterSave);
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
			}else {
				drsToObject=responseText;
				alert(drsToObject.successMessage);
				disableAll();
				var fsInDateCal= getDomElementById('fsInTimeImg');
				if(fsInDateCal!=null){
					fsInDateCal.style.display = "none";
				}
				enableGlobalButton('Cancel');
				/*
				disableGrid();
				getDomElementById("Save").disabled=true;
				drsToObject=responseText;
				alert(drsToObject.successMessage);
				//var fsInTimeDom= getDomElementById('fsInTime');
				var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
				var fsInTimeHHDom= getDomElementById('fsInTimeHHStr');
				var fsInTimeMMDom= getDomElementById('fsInTimeMMStr');
				if(fsInTimeDateDom!=null && fsInTimeHHDom !=null){
					fsInTimeHHDom.setAttribute("readOnly",true);
					fsInTimeHHDom.setAttribute("tabindex","-1");
					
					fsInTimeMMDom.setAttribute("readOnly",true);
					fsInTimeMMDom.setAttribute("tabindex","-1");
					
					fsInTimeDateDom.setAttribute("readOnly",true);
					fsInTimeDateDom.setAttribute("tabindex","-1");
				}
			*/}
		}
}
function validateGridValidations(){
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var reasonDom=  document.getElementsByName("to.rowPendingReasonId");
	var missedCardNumDom= document.getElementsByName("to.rowMissedCardNumber");
//	var remarksDom= document.getElementsByName("to.rowRemarks");

	if(cNumber!=null && cNumber.length>=1 ){
		for(var i=0;i<cNumber.length;i++){
			var rId= getRowId(cNumber[i],'rowConsignmentNumber');
			if(i!=0 && i==cNumber.length-1 && isNull(cNumber[i].value)){
				continue;
			}
			 if(isNull(cNumber[i].value)){
				alert("Please provide consignment number at line :"+(i+1));
				//cNId[i].value="";
				cNumber[i].value="";
				setFocusOnDom(cNumber[i]);
				return false;
			}
			if(isNull(reasonDom[i].value)){
				alert("Please provide Pending Reasons at line :"+(i+1));
				reasonDom[i].value="";
				reasonDom[i].value="";
				setFocusOnDom(reasonDom[i]);
				return false;
			}
			
			if(isMissedCardNumRequired(rId)){
				if(isNull(missedCardNumDom[i].value)){
					alert("Please provide Missed card number  at line :"+(i+1));
					missedCardNumDom[i].value="";
					missedCardNumDom[i].value="";
					setFocusOnDom(missedCardNumDom[i]);
					return false;
				}
			}
			
		}
	}else{
		alert("Please add atleast one consignment details");
		return false;
	}
	return true;
}


function validatePendingReason(rowId){
	enableMissedCardNumber(rowId);
	var missedCardDom= getDomElementById("rowMissedCardNumber"+rowId);
	if(missedCardDom!=null && !missedCardDom.readOnly && isNull(missedCardDom.value)){
		/***Artifact artf3000662*/
			alert("Please provide Missed you card number ");
			setFocusOnDom(missedCardDom);
		   //missedCardDom.focus();
			return false;
	}else{
		//alert("validatePendingReason"+rowCount);
		addNewPendingRow(rowId);
	}
	
}
function validateMandatoryForAdd(rowId){
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var dlvDtlsDom = getDomElementById("rowDeliveryDetailId"+rowId);
	var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
	
	var missedCardDom= getDomElementById("rowMissedCardNumber"+rowId);
	
	if(isNull(dlvDtlsDom.value)){
		return false;
	}
	if(isNull(consignmentNumber.value)){
		return false;
	}
	if(isNull(reasonDom.value)){
		return false;
	}
	if(isMissedCardNumRequired(rowId)){
		if(isNull(missedCardDom.value)){
			setFocusOnDom(missedCardDom);
			return false;
		}
	}
	return true;
}

function addNewPendingRow(rowId){
	if(validateMandatoryForAdd(rowId)){
		//alert("addNewPendingRow"+rowCount);
		addRowAfterResponse(rowId,tableId);
	}
}

function enterKeyforMissedCardPendingDrs(rowId,keyCode,domObject){
	if(enterKeyNavWithoutFocus(keyCode)){
		if(isMissedCardNumRequired(rowId) && !isNull(domObject.value)){
			addNewPendingRow(rowId);
		}
	}
}
