$(document).ready(function() {     
	var oTable = $('#rthValidationGrid').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "100%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
	"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});
	
	defaultChanges();
	getReasonsByReasonType();
} ); 

/** The tableId */
var tableId = "rthValidationGrid";
/** The reasonTOArray */
var reasonTOArray = new Array();
//Using data grid
/** The rowCount */
var rowCount = 1;

/**
 * addRthValidationRow
 *
 * @returns {Number}
 * @author narmdr
 */
function addRthValidationRow() {
	$('#rthValidationGrid').dataTable().fnAddData( [
	'<div id="sno'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="dateStr'+ rowCount +'" name="to.dateStr" size="20" class="txtbox" readonly="true"/>',
	'<input type="text" id="time'+ rowCount +'" name="to.time" size="10" class="txtbox" readonly="true"/>',
	'<select name="to.reasonId" id="reasonId'+ rowCount +'" class="selectBox width200" disabled="true" onkeypress="enterKeyNavigationFocus(event,\'btnSave\');"> \
	<option value="">--Select--</option></select>',
	'<input type="text" id="remarks'+ rowCount +'" name="to.remarks" class="txtbox" size="35" maxlength="100" readonly="true" onkeypress="enterKeyNavigationFocus(event,\'btnSave\');"/> \
	<input type="hidden" id="consignmentReturnReasonId'+ rowCount +'" name="to.consignmentReturnReasonId"/> '
	] );

	//'<input type="checkbox" id="chk'+ rowCount +'" name="chkBoxName" value="" disabled="true"/>',
	// onfocus="getCurrentDateTime('+ rowCount +');"
	populateReasonType(rowCount);
	rowCount++;
	return rowCount-1;
}

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	$("#consignmentNumber").focus();
	buttonDisableById("btnSave");
}

/**
 * getReasonsByReasonType
 *
 * @author narmdr
 */
function getReasonsByReasonType(){
	var reasonTypeCode = $("#reasonTypeCode").val();
	var url = "./rthRtoValidation.do?submitName=getReasonsByReasonType&reasonType="+reasonTypeCode;
	showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateReasonTOList(data);}
	});
}

/**
 * populateReasonTOList
 *
 * @param data
 * @author narmdr
 */
function populateReasonTOList(data){
	hideProcessing();
	reasonTOArray = eval('(' + data + ')');
	
	var errorMsg = getErrorMessage(reasonTOArray);	
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	
	createRthRow();
	setDataTableDefaultWidth();
}

/**
 * populateReasonType
 *
 * @param rowId
 * @author narmdr
 */
function populateReasonType(rowId){
	clearDropDownList("reasonId" + rowId);
	for(var i=0;i<reasonTOArray.length;i++) {
		addOptionTODropDown("reasonId" + rowId, 
				reasonTOArray[i].reasonName, 
				reasonTOArray[i].reasonId);
	}
}

/**
 * createRthRow
 *
 * @author narmdr
 */
function createRthRow(){
	var maxReasonsForRth = $("#maxReasonsForRth").val();
	if(isNull(maxReasonsForRth)){
		maxReasonsForRth = 3;
	}
	
	for(var i=0;i<maxReasonsForRth;i++){
		addRthValidationRow();
	}
	disableEnableRowField(1, false);
}

/**
 * validateMandatoryRowFields
 *
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId){
	var dateObj = $("#dateStr" + rowId);
	//var timeObj = $("#time" + rowId);
	var reasonIdObj = $("#reasonId" + rowId);
	var consignmentReturnReasonIdObj = $("#consignmentReturnReasonId" + rowId);
	
	/*if(isNull(consignmentReturnReasonIdObj.val())){
		if(isNull(dateObj.val())){
			focusAlertMsg4TxtBox(dateObj, "Date");
			return false;
		}
		if(isNull(timeObj.val())){
			focusAlertMsg4TxtBox(timeObj, "Time");
			return false;
		}
		if(isNull(reasonIdObj.val())){
			focusAlertMsg4Select(reasonIdObj, "Pending Reason");
			return false;
		}
	}*/
	if(isNull(consignmentReturnReasonIdObj.val()) && 
			!isNull(dateObj.val()) &&
			isNull(reasonIdObj.val())){
		focusAlertMsg4Select(reasonIdObj, "Pending Reason");
		return false;
	}
	return true;
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields(){
	var table = document.getElementById("rthValidationGrid");
	
	var consignmentNumberObj = $("#consignmentNumber");
	var consignmentIdObj = $("#consignmentId");
	//var actualWeightObj = $("#actualWeight");
	var contactNumberObj = $("#contactNumber");
	
	if(isNull(consignmentNumberObj.val())){
		focusAlertMsg4TxtBox(consignmentNumberObj, "Consignment Number");
		return false;
	}
	
	if(isNull(consignmentIdObj.val())){
		focusAlertMsg4TxtBox(consignmentNumberObj, "Consignment Number and click on search button");
		return false;
/*		if(isNull(actualWeightObj.val())){
			focusAlertMsg4TxtBox(actualWeightObj, "Actual Weight");
			return false;
		}*/
	}
/*	if(isNull(contactNumberObj.val())){		
		focusAlertMsg4TxtBox(contactNumberObj, "Contact Number");
		return false;
	}*/
	
	//Validating table data
	var rowValidation = false;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		
		/*if(i==(table.rows.length - 1) && table.rows.length>2){
			continue;
		}*/
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
		rowValidation = true;
	}
	if(!rowValidation){
		alert("Please Enter at least one Reason!");
	}
	return true;
}

/**
 * saveOrUpdateRthValidation
 *
 * @author narmdr
 */
function saveOrUpdateRthValidation(){
	if(!validateMandatoryFields()){
		return;
	}
	flag=confirm("Do you want to Save the RTH Validation Details");
	if(!flag){
		return;
	}
	enableAll();
	var url="./rthRtoValidation.do?submitName=saveOrUpdateRthRtoValidation";
	document.rthRtoValidationForm.action=url;
	document.rthRtoValidationForm.submit();
}

/**
 * findConsignmentNumber
 *
 * @author narmdr
 */
function findConsignmentNumber(){
	var consignmentNumberObj = $("#consignmentNumber");
	//var loggedInOfficeId = $("#officeId").val();
	
	/*if(isNull(consignmentNumberObj.val())){
		focusAlertMsg4TxtBox(consignmentNumberObj, "Consignment Number");
		return;
	}*/
	
	//validate Consg Number format 
	if(!isValidConsgNo(consignmentNumberObj)){
		consignmentNumberObj.val("");
		return;
	}
	var url="./rthRtoValidation.do?submitName=findConsignmentNumber4Rth";
	
	//showProcessing();
	/*$.ajax({
		url: url,
		data: "consignmentNumber="+consignmentNumberObj.val()+"&loggedInOfficeId="+loggedInOfficeId,
		success: function(data){populateConsignment(data, rowId);}
	});*/
	ajaxCall(url, "rthRtoValidationForm", populateConsignment);
}

/**
 * populateConsignment
 *
 * @param data
 * @author narmdr
 */
function populateConsignment(data){
	//hideProcessing();
	var rthRtoValidationTO = eval('(' + data + ')');

	if(!isNull(rthRtoValidationTO.errorMsg)){
		clearFocusAlertMsg($("#consignmentNumber"), rthRtoValidationTO.errorMsg);
		return;
	} else if(!isNull(rthRtoValidationTO.isRth)){
		populateRthRtoValidationTO(rthRtoValidationTO);
		$("#btnEdit").focus();
		
	} else if(!isNull(rthRtoValidationTO.isConsignment)){
		populateConsignmentDetails(rthRtoValidationTO);
		getCurrentDateTime(1);
	}

	document.getElementById("consignmentNumber").readOnly = true;
}

/**
 * populateRthRtoValidationTO
 *
 * @param rthRtoValidationTO
 * @author narmdr
 */
function populateRthRtoValidationTO(rthRtoValidationTO){
	
	populateConsignmentDetails(rthRtoValidationTO);
	$("#consignmentReturnId").val(rthRtoValidationTO.consignmentReturnId);
	$("#processNumber").val(rthRtoValidationTO.processNumber);

	if(!isNull(rthRtoValidationTO.processTO)){
		$("#processId").val(rthRtoValidationTO.processTO.processId);		
	}
	
	if(!isNull(rthRtoValidationTO.rthRtoValidationDetailsTOs)){
		for(var i=0;i<rthRtoValidationTO.rthRtoValidationDetailsTOs.length;i++) {
			populateGrid(rthRtoValidationTO.rthRtoValidationDetailsTOs[i], i+1);
		}
	}/*else{
		if(isNull($("#contactNumber").val())){
			$("#contactNumber").focus();
		}else{
			$("#dateStr1").focus();
		}
	}*/

	disableAll();
	enableFieldById("btnCancel");
	//enableFieldById("btnSave");
	if(!rthRtoValidationTO.isRtohManifest){
		enableFieldById("btnEdit");
		$("#btnEdit").focus();
	} else {
		$("#btnCancel").focus();
	}
}

/**
 * populateConsignmentDetails
 *
 * @param rthRtoValidationTO
 * @author narmdr
 */
function populateConsignmentDetails(rthRtoValidationTO){
	$("#consignmentId").val(rthRtoValidationTO.consignmentId);
	$("#drsDateTimeStr").val(rthRtoValidationTO.drsDateTimeStr);
	$("#contactNumber").val(rthRtoValidationTO.contactNumber);
	if(!isNull(rthRtoValidationTO.consignmentTypeTO)){
		$("#consignmentTypeId").val(rthRtoValidationTO.consignmentTypeTO.consignmentId);
		$("#consignmentName").val(rthRtoValidationTO.consignmentTypeTO.consignmentName);
	}
	$("#actualWeight").val(rthRtoValidationTO.actualWeight);
	populateWeightInKgGmField(rthRtoValidationTO.actualWeight);
}

/**
 * populateGrid
 *
 * @param rthRtoValidationDetailsTO
 * @param rowId
 * @author narmdr
 */
function populateGrid(rthRtoValidationDetailsTO, rowId){
	$("#dateStr" + rowId).val(rthRtoValidationDetailsTO.dateStr);
	$("#time" + rowId).val(rthRtoValidationDetailsTO.time);
	$("#remarks" + rowId).val(rthRtoValidationDetailsTO.remarks);
	$("#consignmentReturnReasonId" + rowId).val(rthRtoValidationDetailsTO.consignmentReturnReasonId);
	if(!isNull(rthRtoValidationDetailsTO.reasonTO)){
		$("#reasonId" + rowId).val(rthRtoValidationDetailsTO.reasonTO.reasonId);
	}
}

/**
 * edit
 *
 * @author narmdr
 */
function edit(){
	if(!isConsgNoEntered()){
		return;
	}
	var table = document.getElementById(tableId);
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(isNull($("#dateStr" + rowId).val())){
			disableEnableRowField(rowId, false);
			getCurrentDateTime(rowId);
			break;
		}else{
			if(!validateMandatoryRowFields(rowId)){
				break;
			}
		}
	}
}

/**
 * cancelRth
 *
 * @author narmdr
 */
function cancelRth(){
	var flag = confirm("Do you want to Cancel the RTH Validation Details!");
	if(flag){
		var url = "./rthRtoValidation.do?submitName=viewRthValidation";
		window.location = url;
	}
}
