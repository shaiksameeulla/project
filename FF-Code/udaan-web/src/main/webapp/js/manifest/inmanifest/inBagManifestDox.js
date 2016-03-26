$(document).ready(function() {     
	var oTable = $('#bplDoxGrid').dataTable( {         
	"sScrollY": "250",         
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
	addBplDoxRow();
	//getWeightFromWeighingMachine();
	setDataTableDefaultWidth();
} ); 

/** The moduleName */
var moduleName = "inBplDox";

//Using data grid
/** The rowCount */
var rowCount = 1;
/** The weightKgFieldName */
var weightKgFieldName = "weightKg";
/** The weightGmFieldName */
var weightGmFieldName = "weightGm";
/** The manifestNumbersFieldName */
var manifestNumbersFieldName = "manifestNumbers";

var printUrl;
/**
 * addBplDoxRow
 *
 * @returns {Number}
 * @author narmdr
 */
function addBplDoxRow() {
	$('#bplDoxGrid').dataTable().fnAddData( [
	'<input type="checkbox"   id="chk'+ rowCount +'" name="chkBoxName" value=""/>',
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="manifestNumbers'+ rowCount +'" name="to.manifestNumbers" maxlength="10" size="20" class="txtbox" onchange="validateManifestNumber(this);" onkeypress="enterKeyNavigationFocus(event,\'weightKg'+ rowCount +'\');"/>',
	'<input type="text" id="weightKg'+ rowCount +'" maxlength="5" size="4" class="txtbox" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');" onblur="validateWeight('+ rowCount +');" onfocus="manifestNumberFocus('+ rowCount +');" style="text-align: right"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGm'+ rowCount +'" maxlength="3" size="4" class="txtbox" onblur="validateWeight('+ rowCount +');addRow('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, manifestNumbersFieldName,'+ rowCount +');" onfocus="clearWtGramIfEmpty(\'weightGm'+ rowCount +'\')"/>',
	'<select class="selectBox width145" id="remarks'+ rowCount +'" name="to.remarks" onfocus="weightKgFocus('+ rowCount +');">' + REMARKS_OPTION
	+ '</select> \
	<input type="hidden" id="manifestIds'+ rowCount +'" name="to.manifestIds"/> \
	<input type="hidden" id="manifestWeights'+ rowCount +'" name="to.manifestWeights"/> \
	<input type="hidden" id="destCityIds'+ rowCount+ '" name="to.destCityIds"/>\
	<input type="hidden" id="originOfficeIds'+ rowCount+ '" name="to.originOfficeIds"/>\
	<input type="hidden" id="destOfficeIds'+ rowCount+ '" name="to.destOfficeIds"/>\
	<input type="hidden" id="receivedStatus'+ rowCount +'" name="to.receivedStatus" value="'+ notReceivedCode +'"/>'
	] );

	// onkeypress="enterKeyNavigationFocus4Grid(event, weightKgFieldName,'+ rowCount +');"
	rowCount++;
	updateSerialNoVal("bplDoxGrid");
	return rowCount-1;
}

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	document.getElementById("manifestNumber").focus();
	buttonDisableById("btnPrint");
}

/**
 * setTotalWeight
 *
 * @author narmdr
 */
function setTotalWeight(){
	var totalWeight = 0;
	var table = document.getElementById('bplDoxGrid');

	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weight = document.getElementById("manifestWeights" + rowId).value;
		if(!isNull(weight)){
			totalWeight += parseFloat(weight);
		}
	}
	var manifestWeightObj = document.getElementById("manifestWeight");
	manifestWeightObj.value = totalWeight;
	fixFormatUptoThreeDecimalPlace(manifestWeightObj);	
}

/**
 * validateWeight
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeight(rowIdd){
	/*if(!isNull(getValueByElementId("manifestIds"))){
		return;
	}*/
	var weightKgObj = document.getElementById("weightKg" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
	var manifestWeightsObj = document.getElementById("manifestWeights" + rowIdd);
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	manifestWeightsObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	setTotalWeight();
}

/**
 * addRow
 *
 * @param selectedRowId
 * @author narmdr
 */
function addRow(selectedRowId){	
	var table = document.getElementById("bplDoxGrid");
	var lastRow = table.rows.length - 1;
	var manifestWeightsObj = document.getElementById("manifestWeights" + selectedRowId);
	var isNewRow = false;

	if(!validateMandatoryRowFields(selectedRowId)){
		manifestWeightsObj.value = "";
		document.getElementById("weightKg" + selectedRowId).value = "";
		document.getElementById("weightGm" + selectedRowId).value = "";
		return;
	}
	var oldLastRowId = null;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(rowId==selectedRowId && i==lastRow){
			isNewRow = true;
		}
		oldLastRowId = rowId;
	}
	if(isNewRow){
		var rowId = addBplDoxRow();
		document.getElementById("manifestNumbers" + rowId).focus(); 
	}else{
		document.getElementById("manifestNumbers" + oldLastRowId).focus(); 
	}
}

/**
 * validateMandatoryRowFields
 *
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId){
	var manifestNumbersObj = document.getElementById("manifestNumbers" + rowId);
	var manifestWeightsObj = document.getElementById("manifestWeights" + rowId);
	var manifestIdsObj = document.getElementById("manifestIds" + rowId);
	var weightKgObj = document.getElementById("weightKg" + rowId);
	
	if(isNull(manifestIdsObj.value)){
		if(isNull(manifestNumbersObj.value)){
			focusAlertMsg4TxtBox(manifestNumbersObj, "OGM / Open Manifest Number");
			return false;
		}
		//if(isNull(manifestWeightsObj.value) || parseFloat(manifestWeightsObj.value)==0.000){
		if(isEmptyWeight(manifestWeightsObj.value)){
			focusAlertMsg4TxtBox(weightKgObj, "Weight");
			return false;
		}
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
	var table = document.getElementById("bplDoxGrid");
	var manifestNumberObj = document.getElementById("manifestNumber");
	var manifestIdObj = document.getElementById("manifestId");
	var originRegionObj = document.getElementById("originRegion");
	var originCityObj = document.getElementById("originCity");
	var originOfficeTypeObj = document.getElementById("originOfficeType");
	var originOfficeObj = document.getElementById("originOffice");
		
	//if(isNull(manifestIdObj.value)){
		if(isNull(manifestNumberObj.value)){		
			focusAlertMsg4TxtBox(manifestNumberObj, "BPL Number");
			return false;
		}
		if(isNull(originRegionObj.value)){		
			focusAlertMsg4Select(originRegionObj, "Origin Region");
			return false;
		}
		if(isNull(originCityObj.value)){		
			focusAlertMsg4Select(originCityObj, "Origin City");
			return false;
		}
		if(isNull(originOfficeTypeObj.value)){		
			focusAlertMsg4Select(originOfficeTypeObj, "Origin Office Type");
			return false;
		}
		if(isNull(originOfficeObj.value)){		
			focusAlertMsg4Select(originOfficeObj, "Origin Office");
			return false;
		}
	//}
	
	//Validating table data	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var manifestNumbersObj = $("#manifestNumbers" + rowId);
		if(i==(table.rows.length - 1) && table.rows.length>2){
			if (isNull(manifestNumbersObj.val())) {
				continue;
			}
		}
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
	}	
	
	return true;
}

/**
 * saveOrUpdateInBagManifetDox
 *
 * @author narmdr
 */
function saveOrUpdateInBagManifetDox(){
	if(!validateMandatoryFields()){
		return;
	}
	flag=confirm("Do you want to Save the In BPL - Dox Details");
	if(!flag){
		return;
	}
	enableAll();
	var url="./inBagManifest.do?submitName=saveOrUpdateInBagManifetDox";
	ajaxCall(url, "inBagManifestForm", saveCallback);
}
/**
 * saveCallback
 *
 * @param msg
 * @author narmdr
 */
function saveCallback(msg){
	//hideProcessing();
	/*var inBagManifestDoxTO = eval('(' + msg + ')');
	alert("In BPL - DOX Number : "+ inBagManifestDoxTO.manifestNumber +" Saved Successfully.\nLess Bags are : "+ inBagManifestDoxTO.lessManifest +".\nExcess Bags are : "+ inBagManifestDoxTO.excessManifest +".");
	*/
	alert(msg);
	showProcessing();
	cancelPage();
}

/**
 * cancelInBplDox
 *
 * @author narmdr
 */
function cancelInBplDox(){
	var flag = confirm("Do you want to cancel the In BPL - DOX Details!");
	if(flag){
		cancelPage();
	}
}

/**
 * cancelPage
 *
 * @author narmdr
 */
function cancelPage(){
	url = "./inBagManifest.do?submitName=viewInBagManifestDox";
	window.location = url;
}

/**
 * findBplNumberDox
 *
 * @author narmdr
 */
function findBplNumberDox(){
	var manifestNumberObj = document.getElementById("manifestNumber");
	//validate format
	if(!isValidBplNo(manifestNumberObj)){
		return;
	}

	showProcessing();
	//url = "./inBagManifest.do?submitName=isManifestNoIssued&manifestNo="+manifestNumberObj.value+"&seriesType=BPL_NO";

	isManifestNoIssued(null);
	//plz uncomment after test :: issue validation
	/*var url = "./inBagManifest.do?submitName=isManifestNoIssued";
	$.ajax({
		url: url,
		data: "manifestNo="+manifestNumberObj.value+"&seriesType=BPL_NO",
		success: function(data){isManifestNoIssued(data);}
	});*/
}

/**
 * isManifestNoIssued
 *
 * @param data
 * @author narmdr
 */
function isManifestNoIssued(data){
	var manifestNumberObj = document.getElementById("manifestNumber");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	
	//plz uncomment after test :: issue validation
	/*var manifestIssueValidationTO = eval('(' + data + ')');
	if(manifestIssueValidationTO.isIssued=="N"){
		hideProcessing();
		clearFocusAlertMsg(manifestNumberObj, manifestIssueValidationTO.errorMsg);
		return;
	}*/
	
	url = "./inBagManifest.do?submitName=findBplNumberDox&manifestNumber="+manifestNumberObj.value+"&loggedInOfficeId="+loggedInOfficeId;
	$.ajax({
		url: url,
		success: function(data){populateBplNumberDox(data);}
	});
}

/**
 * populateBplNumberDox
 *
 * @param data
 * @author narmdr
 */
function populateBplNumberDox(data){
	hideProcessing();
	var inManifestValidationTO = eval('(' + data + ')');
	/*if (inManifestValidationTO.isInManifest
			|| inManifestValidationTO.isInManifestByReceive) {
		populateInBagManifestTO(inManifestValidationTO);
		
	} else if (inManifestValidationTO.isNewManifest) {

	}*/
	if(!isNull(inManifestValidationTO.errorMsg)){
		clearFocusAlertMsg(document.getElementById("manifestNumber"), inManifestValidationTO.errorMsg);
		return;
	}
	
	if (!inManifestValidationTO.isNewManifest) {
		populateInBagManifestTO(inManifestValidationTO);
	}else{
		$("#originRegion").focus();
	}
	//to prepare iFrame for print
	/*var manifestNumberObj = document.getElementById("manifestNumber");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	url = "./inBagManifest.do?submitName=printBplNumberDox&manifestNumber="
		+manifestNumberObj.value+"&loggedInOfficeId="+loggedInOfficeId;
	printUrl = url;
	printIframe(printUrl);*/
}

function printIframe(printUrl){
	showProcessing();
	document.getElementById("iFrame").setAttribute('src',printUrl);
	hideProcessing();
}
/**
 * populateInBagManifestTO
 *
 * @param inManifestValidationTO
 * @author narmdr
 */
function populateInBagManifestTO(inManifestValidationTO){
	
	var inBagManifestDoxTO = inManifestValidationTO.inBagManifestDoxTO;
	
	document.getElementById("manifestId").value = inBagManifestDoxTO.manifestId;
	document.getElementById("lockNum").value = inBagManifestDoxTO.lockNum;
	document.getElementById("position").value = inBagManifestDoxTO.position;
	
	$("#headerRemarks").val(inBagManifestDoxTO.headerRemarks);
	$("#manifestEmbeddedIn").val(inBagManifestDoxTO.manifestEmbeddedIn);
	$("#manifestReceivedStatus").val(inBagManifestDoxTO.manifestReceivedStatus);
	
	if(!isNull(inBagManifestDoxTO.destinationOfficeId)){
		document.getElementById("destinationOfficeId").value = inBagManifestDoxTO.destinationOfficeId;		
	}
	if(!isNull(inBagManifestDoxTO.destCityId)){
		document.getElementById("destCityId").value = inBagManifestDoxTO.destCityId;		
	}

	document.getElementById("manifestNumber").readOnly = true;
	
	/*inManifestValidationTO.isInManifestByReceive && */
	if (isNull(inBagManifestDoxTO.originRegion)) {
		//document.getElementById("manifestId").value = inBagManifestDoxTO.manifestId;
		//disableFieldById("manifestNumber");
		$("#originRegion").focus();
		return;
	}
	
	//TODO need to check whether add/select
	/*addOptionTODropDown("originRegion", inBagManifestDoxTO.originRegion.split("~")[1], inBagManifestDoxTO.originRegion.split("~")[0]);
	document.getElementById("originRegion").value = inBagManifestDoxTO.originRegion.split("~")[0];*/
	$("#originRegion").val(inBagManifestDoxTO.originRegion.split("~")[0]);

	addOptionTODropDown("originOffice", inBagManifestDoxTO.originOffice.split("~")[1], inBagManifestDoxTO.originOffice.split("~")[0]);
	document.getElementById("originOffice").value = inBagManifestDoxTO.originOffice.split("~")[0];

	addOptionTODropDown("originCity", inBagManifestDoxTO.originCity.split("~")[1], inBagManifestDoxTO.originCity.split("~")[0]);
	document.getElementById("originCity").value = inBagManifestDoxTO.originCity.split("~")[0];

	/*addOptionTODropDown("originOfficeType", inBagManifestDoxTO.officeType.split("~")[1], inBagManifestDoxTO.officeType.split("~")[0]);
	document.getElementById("originOfficeType").value = inBagManifestDoxTO.officeType.split("~")[0];*/
	$("#originOfficeType").val(inBagManifestDoxTO.officeType.split("~")[0]);
	
	/*if (inManifestValidationTO.isOutManifest) {
		disableFieldById("manifestNumber");
		document.getElementById("manifestNumbers" + (rowCount-1)).focus();
		return;
	}*/
	
	/*if (inManifestValidationTO.isInManifestByReceive) {
		//document.getElementById("manifestId").value = inBagManifestDoxTO.manifestId;
		document.getElementById("manifestNumbers" + (rowCount-1)).focus();
		disableFieldById("manifestNumber");
		return;
	}*/

	if (!isNull(inBagManifestDoxTO.destinationRegion)) {
		document.getElementById("destinationRegion").value = inBagManifestDoxTO.destinationRegion;		
	}
	if (!isNull(inBagManifestDoxTO.destinationOffice)) {
		document.getElementById("destinationOffice").value = inBagManifestDoxTO.destinationOffice;
	}
	//document.getElementById("consignmentTypeId").value = inBagManifestDoxTO.consignmentTypeId;
	document.getElementById("manifestWeight").value = inBagManifestDoxTO.manifestWeight;
	document.getElementById("updateProcessId").value = inBagManifestDoxTO.updateProcessId;
	
	
	if(!isNull(inBagManifestDoxTO.inBagManifestDetailsDoxTOs)){
		//populateGrid();
		deleteAllRowOfTableExceptHeaderRow("bplDoxGrid");
		
		for(var i=0;i<inBagManifestDoxTO.inBagManifestDetailsDoxTOs.length;i++) {
			var rowId = addBplDoxRow();
			populateGrid(inBagManifestDoxTO.inBagManifestDetailsDoxTOs[i], rowId);		
		}
		
		disableAll();
		/*var rowId = addBplDoxRow();
		//disableFieldById("chk" + rowId);
		document.getElementById("manifestNumbers" + rowId).focus();*/
		setTotalWeight();
		//enableFieldById("btnSave");
		//enableFieldById("btnDelete");

		/*jQuery("#btnSave").css("background-color", "grey");
		jQuery("#btnDelete").css("background-color", "grey");*/
		
		/*enableFieldById("btnClear");
		enableFieldById("btnPrint");*/
		buttonEnableById("btnClear");
		if(inBagManifestDoxTO.inBagManifestDetailsDoxTOs.length>0){
			document.getElementById("manifestDateTime").value = inBagManifestDoxTO.manifestDateTime;
			buttonEnableById("btnPrint");
			isViewScreen = true;
		} else {
			isViewScreen = false;
		}
		
	}else{
		$("#manifestNumbers1").focus();
	}

}

/**
 * populateGrid
 *
 * @param inBagManifestDetailsDoxTO
 * @param rowId
 * @author narmdr
 */
function populateGrid(inBagManifestDetailsDoxTO, rowId){
	document.getElementById("manifestNumbers" + rowId).value = inBagManifestDetailsDoxTO.manifestNumber;
	document.getElementById("remarks" + rowId).value = inBagManifestDetailsDoxTO.remarks;
	document.getElementById("manifestIds" + rowId).value = inBagManifestDetailsDoxTO.manifestId;
	document.getElementById("manifestWeights" + rowId).value = inBagManifestDetailsDoxTO.manifestWeight;
	document.getElementById("receivedStatus" + rowId).value = inBagManifestDetailsDoxTO.receivedStatus;
	populateWeightInKgGmField(inBagManifestDetailsDoxTO.manifestWeight, rowId);
	disableFieldById("chk" + rowId);
}

/**
 * weightKgFocus
 *
 * @param rowIdd
 * @author narmdr
 */
function weightKgFocus(rowIdd){
	var weightKgObj = document.getElementById("weightKg" + rowIdd);	
	var manifestWeightsObj = document.getElementById("manifestWeights" + rowIdd);		
	if(isNull(manifestWeightsObj.value)){
		weightKgObj.focus();
	}
}

/**
 * manifestNumberFocus
 *
 * @param rowId
 * @author narmdr
 */
function manifestNumberFocus(rowId){
	var manifestNumbersObj = document.getElementById("manifestNumbers" + rowId);	
	if(isNull(manifestNumbersObj.value)){
		manifestNumbersObj.focus();
	}/*else{
		validateWeightFromWeighingMachine(rowId);
	}*/
}
/**
 * bplNumberFocus
 *
 * @author narmdr
 */
function bplNumberFocus(){
	var manifestNumbersObj = document.getElementById("manifestNumber");	
	if(isNull(manifestNumbersObj.value)){
		manifestNumbersObj.focus();
	}
}

//Manifest OGM validation Starts
/**
 * validateManifestNumber
 *
 * @param manifestNumberObj
 * @author narmdr
 */
function validateManifestNumber(manifestNumberObj){
	var bplNumberObj = document.getElementById("manifestNumber");
	var rowId = getRowId(manifestNumberObj, "manifestNumbers");
	
	if(isNull(bplNumberObj.value)){
		manifestNumberObj.value = "";
		focusAlertMsg4TxtBox(bplNumberObj, "BPL Number first");
		enableClearManifestFields(rowId);
		return;
	}
	
	//validate Manifest Number format 
	if(!isValidPacketNo(manifestNumberObj)){
		//manifestNumberObj.value = "";
		enableClearManifestFields(rowId);
		return;
	}
	
	if(isDuplicateFieldInGrid(manifestNumberObj, "manifestNumbers", "bplDoxGrid")){
		clearFocusAlertMsg(manifestNumberObj, "Duplicate OGM / Open Manifest Number!");
		return;
	}
	
	showProcessing();
	isPacketNoIssued(null, manifestNumberObj);
	//plz uncomment after test :: issue validation
	/*var url = "./inBagManifest.do?submitName=isManifestNoIssued";
	$.ajax({
		url: url,
		data: "manifestNo="+manifestNumberObj.value+"&seriesType=OGM_NO",
		success: function(data){isPacketNoIssued(data, manifestNumberObj);}
	});*/
}

/**
 * isPacketNoIssued
 *
 * @param data
 * @param manifestNumberObj
 * @author narmdr
 */
function isPacketNoIssued(data, manifestNumberObj){
	//var rowId = getRowId(manifestNumberObj, "manifestNumbers");
	var ogmProcessCode = getValueByElementId("gridOgmProcessCode");
		
	//plz uncomment after test :: issue validation
	/*var manifestIssueValidationTO = eval('(' + data + ')');
	if(manifestIssueValidationTO.isIssued=="N"){
		hideProcessing();
		clearFocusAlertMsg(manifestNumberObj, manifestIssueValidationTO.errorMsg);
		return;
	}*/

	var processCode = getValueByElementId("gridProcessCode");
	//var updateProcessCode = getValueByElementId("updateProcessCode");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	
	var url = "./inBagManifest.do?submitName=isManifestNumInManifested&manifestNumber="+manifestNumberObj.value+"&processCode="+processCode+"&updatedProcessCode="+processCode+"&loggedInOfficeId="+loggedInOfficeId;
	
	$.ajax({
		url: url,
		success: function(data){isManifestNumInManifested(data, manifestNumberObj);}
	});
}

/**
 * isManifestNumInManifested
 *
 * @param data
 * @param manifestNumberObj
 * @author narmdr
 */
function isManifestNumInManifested(data, manifestNumberObj){
	
	if(!isNull(data)){
		hideProcessing();
		clearFocusAlertMsg(manifestNumberObj, data);
		return;
	}
	
	var rowId = getRowId(manifestNumberObj, "manifestNumbers");
	//var ogmProcessCode = getValueByElementId("gridOgmProcessCode");
		
	//var url = "./inBagManifest.do?submitName=getManifestDtls&manifestNumber="+manifestNumberObj.value+"&processCode="+ogmProcessCode+"&updatedProcessCode="+ogmProcessCode;
	var url = "./inBagManifest.do?submitName=getManifestDtlsByManifestNo&manifestNumber="+manifestNumberObj.value;
	
	$.ajax({
		url: url,
		success: function(data){populateManifest(data, rowId);}
	});
}

/**
 * populateManifest
 *
 * @param data
 * @param rowId
 * @author narmdr
 */
function populateManifest(data, rowId){
	hideProcessing();
	var manifestBaseTO = eval('(' + data + ')');
	var errorMsg = getErrorMessage(manifestBaseTO);

	if(!isNull(errorMsg)){
		clearFocusAlertMsg(document.getElementById("manifestNumbers" + rowId), errorMsg);
		return;
	}

	if(!isNull(manifestBaseTO)){
		disableFieldById("weightKg" + rowId);
		//disableFieldById("weightGm" + rowId);

		$("#manifestIds" + rowId).val(manifestBaseTO.manifestId);
		document.getElementById("weightGm" + rowId).readOnly = true;
		document.getElementById("receivedStatus" + rowId).value = receivedCode;

		//set origin office, dest office & dest city
		if(!isNull(manifestBaseTO.originOfficeId)){
			$("#originOfficeIds" + rowId).val(manifestBaseTO.originOfficeId);
		}
		if(!isNull(manifestBaseTO.destinationOfficeId)){
			$("#destOfficeIds" + rowId).val(manifestBaseTO.destinationOfficeId);
		}
		if(!isNull(manifestBaseTO.destinationCityId)){
			$("#destCityIds" + rowId).val(manifestBaseTO.destinationCityId);
		}
		
		//if(!isWeighingMachineConnected){
		populateWeightInKgGmField(manifestBaseTO.manifestWeight, rowId);
		var manifestWeightsObj = document.getElementById("manifestWeights" + rowId);
		manifestWeightsObj.value = manifestBaseTO.manifestWeight;
		fixFormatUptoThreeDecimalPlace(manifestWeightsObj);
		setTotalWeight();
		//}
		
		var rowIdd = addBplDoxRow();
		document.getElementById("manifestNumbers" + rowIdd).focus(); 
	}else{
		enableClearManifestFields(rowId);
		$("#weightKg"+rowId).focus();
	}
	validateWeightFromWeighingMachine(rowId);
	/*var isDisabled = $("#weightKg"+rowId).prop('disabled');
	alert(isDisabled)*/;
	if($("#weightKg"+rowId).is(":disabled")){
		document.getElementById("weightGm" + rowId).readOnly = true;
	} else{
		document.getElementById("weightGm" + rowId).readOnly = false;
	}
}

/**
 * enableClearManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function enableClearManifestFields(rowId){
	enableFieldById("weightKg" + rowId);	
	document.getElementById("weightGm" + rowId).readOnly = false;
	document.getElementById("receivedStatus" + rowId).value = notReceivedCode;	
}

/**
 * validateDestOriginOffice
 *
 * @author narmdr
 */
function validateDestOriginOffice(){
	var destOfficeId = getValueByElementId("destinationOfficeId");
	var originOfficeObj = document.getElementById("originOffice");
	if(originOfficeObj.value==destOfficeId){
		clearFocusAlertMsg(originOfficeObj, "Origin Office and Destination Office should not be same!");
	}
}


function printInBplDox(){
	if(confirm("Do you want to Print?")){
	var manifestNumberObj = document.getElementById("manifestNumber");
	//var destinationOfficeId = getValueByElementId("destinationOfficeId");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	if(!isNull(manifestNumberObj.value)){
	if(!isValidBplNo(manifestNumberObj)){
		manifestNumberObj.value = "";
		return;
	}
	url = "./inBagManifest.do?submitName=printBplNumberDox&manifestNumber="+manifestNumberObj.value+"&loggedInOfficeId="+loggedInOfficeId;
	 var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
	/*var manifestNumberObj = document.getElementById("manifestNumber");
	if(!isNull(manifestNumberObj.value)){
		if(confirm("Do you want to print?")){
			window.frames['iFrame'].focus();
			window.frames['iFrame'].print();				
		}				
	}*/
	}else{
		focusAlertMsg4TxtBox(manifestNumberObj, "BPL Number");
		//alert("Please Enter BPL No.");
		}	
	}
}