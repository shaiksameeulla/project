/**
 * prepare page
 */
$(document).ready(function() {     
	var oTable = $('#loadReceiveLocalGrid').dataTable( {         
	"sScrollY": "175",         
	"sScrollX": "100%",         
	"sScrollXInner": "120%",
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
	
	loadGlobalValues();
	defaultChanges();
	getConsignmentTypeList();
	getOriginOffices();
	//CR : Weight should be taken from weighing machin at hub
	//checkLoginOfficeTypeAndAllowWeighingMachine();
	
} ); 

/** The moduleName */
var moduleName = "receiveLocal";
/** The consignmentTypeTOArray */
var consignmentTypeTOArray = new Array();

//Using data grid
/** The rowCount */
var rowCount = 1;
var manifestDestCityFieldName = "manifestDestCity";
var weightKgFieldName = "weightKg";
var weightGmFieldName = "weightGm";

/**
 * addLoadReceiveLocalRow
 *
 * @returns {Number}
 * @author narmdr
 */
function addLoadReceiveLocalRow() {
	$('#loadReceiveLocalGrid').dataTable().fnAddData( [
	'<input type="checkbox"   id="chk'+ rowCount +'" name="chkBoxName" value=""/>',
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="loadNumber'+ rowCount +'" name="to.loadNumber" size="15" maxlength="10" class="txtbox" onchange="validateManifestNumber(this);" onblur="setTotalBags();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, \'manifestDestCity'+ rowCount +'\',\'BPL/MBPL No.\');" />',
	'<input type="text" id="manifestDestCity'+ rowCount +'" name="to.manifestDestCity" size="15" maxlength="3" onchange="getDestinationCity(this);" class="txtbox" onfocus="mBPLNumberFocus('+ rowCount +');" onkeypress="enterKeyNavigationFocus4Grid(event, weightKgFieldName,'+ rowCount +');"  />',
	'<select name="to.docType" id="docType'+ rowCount +'" onkeypress="enterKeyNavigationFocus(event,\'weightKg'+ rowCount +'\');" ><option value="">--Select--</option></select>',
	'<input type="text" id="weightKg'+ rowCount +'" maxlength="5" size="4" class="txtbox" onblur="validateWeight('+ rowCount +');" style="text-align: right" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGm'+ rowCount +'" maxlength="3" size="4" class="txtbox" onblur="validateWeight('+ rowCount +');addRow('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, \'lockNumber\','+ rowCount +');" onfocus="clearWtGramIfEmpty(\'weightGm'+ rowCount +'\')"/>',
	'<input type="text" id="lockNumber'+ rowCount +'" name="to.lockNumber" size="15" class="txtbox" onkeypress="enterKeyNavigationFocus(event,\'recvStatus'+ rowCount +'\');"/>',
	'<input type="text" id="recvStatus'+ rowCount +'" name="to.recvStatus" size="15" readonly="true" class="txtbox" onkeypress="enterKeyNavigationFocus(event,\'remarks'+ rowCount +'\');"/>',
	'<input type="text" id="remarks'+ rowCount +'" name="to.remarks" size="15" class="txtbox"/> \
	<input type="hidden" id="manifestId'+ rowCount +'" name="to.manifestId"/> \
	<input type="hidden" id="loadConnectedId'+ rowCount +'" name="to.loadConnectedId"/> \
	<input type="hidden" id="manifestDestCityDetails'+ rowCount +'" name="to.manifestDestCityDetails"/> \
	<input type="hidden" id="manifestWeight'+ rowCount +'" name="to.manifestWeight"/> \
	<input type="hidden" id="receivedStatus'+ rowCount +'" name="to.receivedStatus"/> \
	<input type="hidden" id="weight'+ rowCount +'" name="to.weight"/> \
	<input type="hidden" id="manifestOriginOffId'+ rowCount +'" name="to.manifestOriginOffId"/> \
	<input type="hidden" id="manifestDestOffId'+ rowCount +'" name="to.manifestDestOffId"/> \
	<input type="hidden" id="weightTolerance'+ rowCount +'" name="to.weightTolerance" value="N"/>'
	] );

	// onkeypress="enterKeyNavigationFocus4Grid(event, manifestDestCityFieldName,'+ rowCount +');" 
	rowCount++;
	populateDocType(rowCount-1);
	updateSerialNoVal("loadReceiveLocalGrid");
	return rowCount-1;
}

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	buttonDisabled("btnEdit", buttonDisableCss);
	document.getElementById("gatePassNumber").focus();
}

/**
 * validateWeight
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeight(rowIdd){
	var weightKgObj = document.getElementById("weightKg" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
	var weightObj = document.getElementById("weight" + rowIdd);
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	weightObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	validateWeightTolerance(weightObj);
	//addRow(weightObj);
	setTotalWeight();
}

/**
 * populateWeightInKgGmField
 *
 * @param weight
 * @param rowId
 * @author narmdr
 */
function populateWeightInKgGmField(weight, rowId){
	weight += "";
	var weightKgObj = document.getElementById("weightKg" + rowId);
	var weightGmObj = document.getElementById("weightGm" + rowId);
	
	if(!isNull(weight)){
		weightKgObj.value = weight.split(".")[0];
		weightGmObj.value = weight.split(".")[1];
	}
	fixWeightFormatForGram(weightGmObj);
}

/**
 * addRow
 *
 * @param selectedRowId
 * @author narmdr
 */
function addRow(selectedRowId){	
	var table = document.getElementById('loadReceiveLocalGrid');
	var lastRow = table.rows.length - 1;
	var weightObj = document.getElementById("weight" + selectedRowId);
	var isNewRow = false;

	if(!validateMandatoryRowFields(selectedRowId)){
		weightObj.value = "";
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
		var rowId = addLoadReceiveLocalRow();
		//document.getElementById("loadNumber" + rowId).focus();
		focusById("loadNumber" + rowId);
	}else{
		//document.getElementById("loadNumber" + oldLastRowId).focus();
		focusById("loadNumber" + oldLastRowId);
	}
}

/**
 * getDestinationCity
 *
 * @param manifestDestCityObj
 * @author narmdr
 */
function getDestinationCity(manifestDestCityObj){
	manifestDestCityObj.value = $.trim(manifestDestCityObj.value);
	
	if(isNull(manifestDestCityObj)){
		return;
	}
	var url = "./loadReceiveLocal.do?submitName=getCity&cityCode="+manifestDestCityObj.value;

	$.ajax({
		url: url,
		success: function(data){populateDestinationCity(data, manifestDestCityObj);}
	});
}

/**
 * populateDestinationCity
 *
 * @param data
 * @param manifestDestCityObj
 * @author narmdr
 */
function populateDestinationCity(data, manifestDestCityObj){
	var rowId = getRowId(manifestDestCityObj, "manifestDestCity");
	var cityTO = eval('(' + data + ')');
	
	if(!isNull(cityTO)){
		document.getElementById("manifestDestCityDetails" + rowId).value = 
			cityTO.cityId + tild + cityTO.cityCode + tild + cityTO.cityName;
	}else{
		manifestDestCityObj.value = "";
		clearFieldById("manifestDestCityDetails" + rowId);
		manifestDestCityObj.focus();
		alert("Invalid City Code.");
		return;
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
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var loadNumber = document.getElementById("loadNumber" + rowId);
	var manifestDestCity = document.getElementById("manifestDestCity" + rowId);
	var weight = document.getElementById("weight" + rowId);
	var manifestId = document.getElementById("manifestId" + rowId);
	
	if(isNull(manifestId.value)){
		if(isNull(loadNumber.value)){
			loadNumber.focus();
			alert(pleaseEnterMessage + bplMbplNoLabel + errorEndMsg);
			return false;
		}
		if(isNull(manifestDestCity.value)){
			manifestDestCity.focus();
			alert(pleaseEnterMessage + destinationCityLabel + errorEndMsg);
			return false;
		}
	}
	if(isNull(weight.value) || parseFloat(weight.value)==0.000){
		weight.focus();
		alert(pleaseEnterMessage + weightLabel + errorEndMsg);
		return false;
	}	
	return true;
}

/**
 * deleteLoadReceiveLocalRow
 *
 * @author narmdr
 */
function deleteLoadReceiveLocalRow(){
	deleteTableRow("loadReceiveLocalGrid");
	updateSerialNoVal("loadReceiveLocalGrid");
	setTotalBags();
}

/**
 * setTotalBags
 *
 * @author narmdr
 */
function setTotalBags(){	
	var totalBags = 0;
	var table = document.getElementById('loadReceiveLocalGrid');

	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var loadNumber = document.getElementById("loadNumber" + rowId).value;
		if(!isNull(loadNumber)){
			totalBags++;
		}
	}
	document.getElementById("totalBags").value = totalBags;
}

/**
 * setTotalWeight
 *
 * @author narmdr
 */
function setTotalWeight(){
	var totalWeight = 0;
	var table = document.getElementById('loadReceiveLocalGrid');

	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weight = document.getElementById("weight" + rowId).value;
		if(!isNull(weight)){
			totalWeight += parseFloat(weight);
		}
	}
	document.getElementById("totalWeight").value = totalWeight;
	fixFormatUptoThreeDecimalPlace(document.getElementById("totalWeight"));
	

	var weightKgObj = document.getElementById("totalWeightKg");
	var weightGmObj = document.getElementById("totalWeightGm");
	var totalWeight = document.getElementById("totalWeight").value;
	
	if(!isNull(totalWeight)){
		weightKgObj.value = totalWeight.split(".")[0];
		weightGmObj.value = totalWeight.split(".")[1];
	}
}

/**
 * getConsignmentTypeList
 *
 * @author narmdr
 */
function getConsignmentTypeList(){
	var url = "./loadReceiveLocal.do?submitName=getConsignmentTypeList";
	ajaxCall(url, "loadReceiveLocalForm", populateConsignmentTypeTOList);
}
/**
 * populateConsignmentTypeTOList
 *
 * @param req
 * @author narmdr
 */
function populateConsignmentTypeTOList(req){
	consignmentTypeTOArray = eval('(' + req + ')');
	addLoadReceiveLocalRow();
	setDataTableDefaultWidth();
}

/**
 * populateDocType
 *
 * @param rowId
 * @author narmdr
 */
function populateDocType(rowId){
	clearDropDownList("docType" + rowId);
	for(var i=0;i<consignmentTypeTOArray.length;i++) {
		addOptionTODropDown("docType" + rowId, consignmentTypeTOArray[i].consignmentName, 
				consignmentTypeTOArray[i].consignmentId + tild + 
				consignmentTypeTOArray[i].consignmentCode + tild + 
				consignmentTypeTOArray[i].consignmentName);
	}		
}

/**
 * getOriginOffices
 *
 * @author narmdr
 */
function getOriginOffices(){
	var regionalOfficeId = getValueByElementId("regionalOfficeId");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	
	var url = "./loadReceiveLocal.do?submitName=getOriginOffices&regionalOfficeId="+regionalOfficeId+"&loggedInOfficeId="+loggedInOfficeId;

	showProcessing();
	$.ajax({
		url: url,
		success: function(req){populateOriginOffices(req);}
	});
	//ajaxCall(url, "loadReceiveLocalForm", populateOriginOffices);
}

/**
 * populateOriginOffices
 *
 * @param req
 * @author narmdr
 */
function populateOriginOffices(req){
	hideProcessing();
	var officeTOList = eval('(' + req + ')');
	if(isNull(officeTOList)){
		return;
	}
	clearDropDownList("originOffice");
	for(var i=0;i<officeTOList.length;i++) {
		addOptionTODropDown("originOffice", 
				officeTOList[i].officeCode + hyphen + officeTOList[i].officeName, 
				officeTOList[i].officeId + tild + 
				officeTOList[i].officeCode + hyphen + officeTOList[i].officeName);
	}	
}

/**
 * validateOthersVehicle
 *
 * @author narmdr
 */
function validateOthersVehicle(){
	var vehicleNumber = getValueByElementId("vehicleNumber");
	if(vehicleNumber==othersVehicleCode){
		showFieldById("otherVehicleNumber");
//		$("#otherVehicleNumber").focus();
		focusById("otherVehicleNumber");
	}else{
		clearFieldById("otherVehicleNumber");
		hideFieldById("otherVehicleNumber");
	}
}

/**
 * getLoadReceiveLocal
 * find LoadReceiveLocal By GatePassNumber
 *
 * @returns {Boolean}
 * @author narmdr
 */
function getLoadReceiveLocal(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;
	var gatePassNumberObj = document.getElementById("gatePassNumber");
	var regionalOfficeId = getValueByElementId("regionalOfficeId");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	
	//TODO validate gatePassNumber format.
	if(!isValidGatepassNo(gatePassNumberObj)){
		//document.getElementById("gatePassNumber").focus();
		focusById("gatePassNumber");
		return;
	}
	
	if(isNull(gatePassNumberObj.value)){
		document.getElementById("gatePassNumber").readOnly = false;
		gatePassNumberObj.focus();
		alert(pleaseEnterMessage + gatePassNoLabel + errorEndMsg);
		return false;
	}
	var url = "./loadReceiveLocal.do?submitName=getLoadReceiveLocalTO&gatePassNumber="+gatePassNumberObj.value+"&regionalOfficeId="+regionalOfficeId+"&loggedInOfficeId="+loggedInOfficeId;
	showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateLoadReceiveLocal(data);}
	});
	//ajaxCall(url, "loadReceiveLocalForm", populateLoadReceiveLocal);
}

/**
 * populateLoadReceiveLocal
 *
 * @param data
 * @author narmdr
 */
function populateLoadReceiveLocal(data){
	hideProcessing();
	var gatePassNumberObj = document.getElementById("gatePassNumber");
	var loadReceiveLocalTO = eval('(' + data + ')');

	if(loadReceiveLocalTO.isNewReceive==true){
		document.getElementById("gatePassNumber").readOnly = true;
		document.getElementById("originOffice").focus();
		enableHeader();
		return;
	}
	if(!isNull(loadReceiveLocalTO.errorMsg)){
		gatePassNumberObj.value = "";
		gatePassNumberObj.focus();
		alert(loadReceiveLocalTO.errorMsg);
	}else{
		document.getElementById("gatePassNumber").readOnly = true;
		populateLoadReceiveLocalTO(loadReceiveLocalTO);
	}
}

/**
 * populateLoadReceiveLocalTO
 *
 * @param loadReceiveLocalTO
 * @author narmdr
 */
function populateLoadReceiveLocalTO(loadReceiveLocalTO){
	if(loadReceiveLocalTO.movementDirection=="R"){
		document.getElementById("receiveDateTime").value = loadReceiveLocalTO.receiveDateTime;
		document.getElementById("loadMovementId").value = loadReceiveLocalTO.loadMovementId;
		document.getElementById("headerReceivedStatus").value = loadReceiveLocalTO.headerReceivedStatus;
		document.getElementById("destOfficeType").value = loadReceiveLocalTO.destOfficeType;
		document.getElementById("destOffice").value = loadReceiveLocalTO.destOffice;
		document.getElementById("destOfficeId").value = loadReceiveLocalTO.destOfficeId;
		document.getElementById("processId").value = loadReceiveLocalTO.processId;
		document.getElementById("processNumber").value = loadReceiveLocalTO.processNumber;
		//document.getElementById("regionalOffice").value = loadReceiveLocalTO.regionalOffice;		
	}

	document.getElementById("receivedAgainstId").value = loadReceiveLocalTO.receivedAgainstId;
	document.getElementById("gatePassNumber").value = loadReceiveLocalTO.gatePassNumber;	
	document.getElementById("transportMode").value = loadReceiveLocalTO.transportMode;
	
	addOptionTODropDown("originOffice", 
			loadReceiveLocalTO.originOffice.split(tild)[1], 
			loadReceiveLocalTO.originOffice);
	document.getElementById("originOffice").value = loadReceiveLocalTO.originOffice;	
		
	/*document.getElementById("originOfficeId").value = loadReceiveLocalTO.originOfficeId;
	document.getElementById("loggedInOfficeId").value = loadReceiveLocalTO.loggedInOfficeId;
	document.getElementById("loggedInOffice").value = loadReceiveLocalTO.loggedInOffice;*/
	
	document.getElementById("vehicleNumber").value = loadReceiveLocalTO.vehicleNumber;
	document.getElementById("otherVehicleNumber").value = loadReceiveLocalTO.otherVehicleNumber;
	if(isNull(getValueByElementId("vehicleNumber"))){
		addOptionTODropDown("vehicleNumber", 
				loadReceiveLocalTO.vehicleNumber.split(tild)[1], 
				loadReceiveLocalTO.vehicleNumber);
		document.getElementById("vehicleNumber").value = loadReceiveLocalTO.vehicleNumber;
	}
	validateOthersVehicle();
	document.getElementById("driverName").value = loadReceiveLocalTO.driverName;
	document.getElementById("actualArrival").value = loadReceiveLocalTO.actualArrival;

	/*if(loadReceiveLocalTO.loadReceiveDetailsTOs.length>0){
		deleteAllRowOfTableExceptHeaderRow('loadReceiveLocalGrid');
	}*/
	deleteAllRowOfTableExceptHeaderRow('loadReceiveLocalGrid');
	
	for(var i=0;i<loadReceiveLocalTO.loadReceiveDetailsTOs.length;i++) {
		if(loadReceiveLocalTO.loadReceiveDetailsTOs[i].receivedStatus=="N"){
			continue;
		}
		var rowId = addLoadReceiveLocalRow();
		populateLoadReceiveDetailsTO(loadReceiveLocalTO.loadReceiveDetailsTOs[i], rowId);		
	}
	
	/*if(loadReceiveLocalTO.loadReceiveDetailsTOs.length==0){
		document.getElementById("actualArrival").focus();
	}else{
		var rowIdd = addLoadReceiveLocalRow();
		disableFieldById("chk" + rowIdd);
		document.getElementById("loadNumber" + rowIdd).focus();
	}*/

	var rowIdd = addLoadReceiveLocalRow();
	//disableFieldById("chk" + rowIdd);
	document.getElementById("loadNumber" + rowIdd).focus();
	
	setTotalWeight();
	setTotalBags();
	disableHeader();	
	buttonEnabled("btnEdit", buttonDisableCss);
	
	if(loadReceiveLocalTO.headerReceivedStatus=="C"){//receive completed
		disableAll();
		hideFieldById("deleteBtn");	
		buttonDisabled("btnSave", buttonDisableCss);
		buttonDisabled("btnEdit", buttonDisableCss);
		document.getElementById("btnCancel").focus();
		alert("Gatepass Number Already Received Completely.");
	}
	buttonEnabled("btnCancel", buttonDisableCss);
	buttonEnabled("btnPrint", buttonDisableCss);
}

/**
 * populateLoadReceiveDetailsTO
 *
 * @param loadReceiveDetailsTO
 * @param rowId
 * @author narmdr
 */
function populateLoadReceiveDetailsTO(loadReceiveDetailsTO, rowId){
	document.getElementById("loadNumber" + rowId).value = loadReceiveDetailsTO.loadNumber;
	selectOptionByFirstSplitValue(document.getElementById("docType" + rowId), loadReceiveDetailsTO.docType);
	document.getElementById("weight" + rowId).value = loadReceiveDetailsTO.weight;
	document.getElementById("lockNumber" + rowId).value = loadReceiveDetailsTO.lockNumber;
	document.getElementById("remarks" + rowId).value = loadReceiveDetailsTO.remarks;
	document.getElementById("manifestId" + rowId).value = loadReceiveDetailsTO.manifestId;
	document.getElementById("loadConnectedId" + rowId).value = loadReceiveDetailsTO.loadConnectedId;
	document.getElementById("manifestDestCity" + rowId).value = loadReceiveDetailsTO.manifestDestCity;
	document.getElementById("manifestDestCityDetails" + rowId).value = loadReceiveDetailsTO.manifestDestCityDetails;
	document.getElementById("manifestWeight" + rowId).value = loadReceiveDetailsTO.manifestWeight;
	document.getElementById("receivedStatus" + rowId).value = loadReceiveDetailsTO.receivedStatus;
	
	validateReceivedStatusByCode(rowId, loadReceiveDetailsTO.receivedStatus);
	fixFormatUptoThreeDecimalPlace(document.getElementById("weight" + rowId));
	
	disableFieldById("chk" + rowId);
	document.getElementById("loadNumber" + rowId).readOnly = true;

	var weightKgObj = document.getElementById("weightKg" + rowId);
	var weightGmObj = document.getElementById("weightGm" + rowId);

	populateWeightInKgGmField(loadReceiveDetailsTO.weight, rowId);
	
	weightKgObj.readOnly = true;
	weightGmObj.readOnly = true;
	document.getElementById("lockNumber" + rowId).readOnly = true;
	document.getElementById("remarks" + rowId).readOnly = true;
	disableFieldById("docType" + rowId);
	disableFieldById("manifestDestCity" + rowId);
}

/**
 * validateReceivedStatusByCode
 *
 * @param rowId
 * @param recvStatusCode
 * @author narmdr
 */
function validateReceivedStatusByCode(rowId, recvStatusCode){
	var recvStatus = null;
	if(recvStatusCode==receivedCode){
		recvStatus = received;
	}else if(recvStatusCode==excessCode){
		recvStatus = excess;
	}else if(recvStatusCode==notReceivedCode){
		recvStatus = notReceived;
	}
	document.getElementById("recvStatus" + rowId).value = recvStatus;
}

//Manifest BPL/MBPL validation Starts
/**
 * validateManifestNumber
 *
 * @param manifestNumberObj
 * @returns {Boolean}
 * @author narmdr
 */
function validateManifestNumber(manifestNumberObj){
	manifestNumberObj.value = $.trim(manifestNumberObj.value);
	var loadMovementId = getValueByElementId("loadMovementId");
	var receivedAgainstId = getValueByElementId("receivedAgainstId");
	var gatePassNumberObj = document.getElementById("gatePassNumber");
	var destOfficeId = getValueByElementId("destOfficeId");
	var rowId = getRowId(manifestNumberObj, "loadNumber");
			
	var url = "./loadReceiveLocal.do?submitName=validateManifestNumber&manifestNumber="+manifestNumberObj.value+"&destOfficeId="+destOfficeId;
	
	if(isNull(gatePassNumberObj.value)){
		manifestNumberObj.value = "";
		gatePassNumberObj.focus();
		alert(pleaseEnterMsg + " " + gatePassNoLabel + " first.");
		enableManifestFields(rowId);
		clearManifestFields(rowId);	
		return false;
	}
	
	//validate Manifest Number format 
	if(!isValidManifestNo(manifestNumberObj)){
		enableManifestFields(rowId);
		clearManifestFields(rowId);	
		return;
	}
	
	if(!isNull(receivedAgainstId)){
		url+= "&receivedAgainstId="+receivedAgainstId; //dispatchId
		
	}else if(!isNull(loadMovementId)){
			url+= "&loadMovementId="+loadMovementId; //receiveId			
	} 		
	
	if(isDuplicateFieldInGrid(manifestNumberObj, "loadNumber", "loadReceiveLocalGrid")){
		/*manifestNumberObj.value = "";
		manifestNumberObj.focus();
		alert("Duplicate " + bplMbplNoLabel);*/
		clearFocusAlertMsg(manifestNumberObj, "Duplicate " + bplMbplNoLabel);
		
		return;
	}

	showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateManifest(data, manifestNumberObj);}
	});
}

/**
 * populateManifest
 *
 * @param data
 * @param manifestNumberObj
 * @author narmdr
 */
function populateManifest(data, manifestNumberObj){
	hideProcessing();
	var loadReceiveManifestValidationTO = eval('(' + data + ')');
	var rowId = getRowId(manifestNumberObj, "loadNumber");
	
	if(loadReceiveManifestValidationTO.isNewManifest==true){
		//create new manifest
		document.getElementById("manifestDestCity" + rowId).focus();
		enableManifestFields(rowId);
		clearManifestFields(rowId);	
		
		var statusCode = excessCode;

		/*var receivedAgainstId = getValueByElementId("receivedAgainstId");
		if(isNull(receivedAgainstId)){
			statusCode = receivedCode;
		}else{
			statusCode = excessCode;
		}*/
		document.getElementById("receivedStatus" + rowId).value = statusCode;
		validateReceivedStatusByCode(rowId, statusCode);		
		//return;
		
	}else if(!isNull(loadReceiveManifestValidationTO.manifestTO)){
		flushManifest(loadReceiveManifestValidationTO.manifestTO, rowId);
		//CR : Weight should be taken from weighing machine at hub
		/*var officetype = document.getElementById("originOfficeType").value;
		if(officetype ==hubCode){
			document.getElementById("weightKg" + rowId).focus();
		} else {
			//var weightObj = document.getElementById("weight" + rowId);
			addRow(rowId);
		}*/
		//CR : Weight should be prepopulated wt
		addRow(rowId);
		//document.getElementById("weightKg" + rowId).focus();
		disableManifestFields(rowId);
		
	}else if(loadReceiveManifestValidationTO.isReceive==true || 
			loadReceiveManifestValidationTO.isDispatch==true){
		populateLoadReceiveDetailsTO4Manifest(loadReceiveManifestValidationTO, rowId);
		//CR : Weight should be taken from weighing machine at hub
		/*var officetype = document.getElementById("originOfficeType").value;
		if(officetype ==hubCode){
			document.getElementById("weightKg" + rowId).focus();
		} else {
			//var weightObj = document.getElementById("weight" + rowId);
			addRow(rowId);
		}*/
		//CR : Weight should be prepopulated wt
		addRow(rowId);
		//document.getElementById("weightKg" + rowId).focus();
		disableManifestFields(rowId);
		
	}else if(!isNull(loadReceiveManifestValidationTO.errorMsg)){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		alert(loadReceiveManifestValidationTO.errorMsg);

		enableManifestFields(rowId);
		clearManifestFields(rowId);
	}

	setTotalWeight();
	setTotalBags();
}

/**
 * flushManifest
 *
 * @param manifestTO
 * @param rowId
 * @author narmdr
 */
function flushManifest(manifestTO, rowId){	
	if(!isNull(manifestTO.destinationCityTO)){
		var cityTO = manifestTO.destinationCityTO;
		document.getElementById("manifestDestCity" + rowId).value = cityTO.cityCode;
		document.getElementById("manifestDestCityDetails" + rowId).value = cityTO.cityId + tild + 
					cityTO.cityCode + tild + cityTO.cityName;		
	}
	document.getElementById("manifestWeight" + rowId).value = manifestTO.manifestWeight;
    //var totalWt = manifestTO.manifestWeight;
  //CR : Weight should be taken from weighing machine at hub
	/*var officetype = document.getElementById("originOfficeType").value;
	if(officetype !=hubCode){
	}*/

	//CR : Weight should be prepopulated wt
	var manifestWeight =  parseFloat(manifestTO.manifestWeight).toFixed(3);
	document.getElementById("weightKg" + rowId).value = manifestWeight.split(".")[0];
	document.getElementById("weightGm" + rowId).value = manifestWeight.split(".")[1];
	document.getElementById("weight" + rowId).value = manifestTO.manifestWeight;
    
	/*weightKgValue = totalWt.split(".")[0];
	document.getElementById("weightKg" + rowId).value = weightKgValue;
	weightGmValue = totalWt.split(".")[1];
	if(isNull(weightGmValue)){
		weightGmValue="000";
		document.getElementById("weightGm" + rowId).value = weightGmValue;
	}else{
		   if (weightGmValue.length == 1) {
				weightGmValue += "00";
			} else if (weightGmValue.length == 2) {
				weightGmValue += "0";
			} else {
				weightGmValue = weightGmValue.substring(0, 3);
			}
		document.getElementById("weightGm" + rowId).value = weightGmValue;
	}*/
	document.getElementById("manifestId" + rowId).value = manifestTO.manifestId;
	document.getElementById("lockNumber" + rowId).value = manifestTO.bagLockNo;
	
	if(!isNull(manifestTO.consignmentTypeTO)){
		selectOptionByFirstSplitValue(document.getElementById("docType" + rowId), 
				manifestTO.consignmentTypeTO.consignmentId);
	}

	var statusCode = excessCode;
	
	/*
	var receivedAgainstId = getValueByElementId("receivedAgainstId");
	if(isNull(receivedAgainstId)){
		statusCode = receivedCode;
	}else{
		statusCode = excessCode;
	}*/
	document.getElementById("receivedStatus" + rowId).value = statusCode;
	validateReceivedStatusByCode(rowId, statusCode);
	
	//added origin office and destination office for manifest
	if(!isNull(manifestTO.originOfficeTO)){
		$("#manifestOriginOffId" + rowId).val(manifestTO.originOfficeTO.officeId);
	}
	if(!isNull(manifestTO.destinationOfficeTO)){
		$("#manifestDestOffId" + rowId).val(manifestTO.destinationOfficeTO.officeId);		
	}
}

/**
 * enableManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function enableManifestFields(rowId){
	document.getElementById("manifestDestCity" + rowId).readOnly = false;
	//document.getElementById("lockNumber" + rowId).readOnly = false;
	enableFieldById("docType" + rowId);
	enableFieldById("weightKg" + rowId);
	document.getElementById("weightGm" + rowId).readOnly = false;
}

/**
 * disableManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function disableManifestFields(rowId){
	document.getElementById("manifestDestCity" + rowId).readOnly = true;
	//document.getElementById("lockNumber" + rowId).readOnly = true;
	disableFieldById("docType" + rowId);
	disableFieldById("weightKg" + rowId);
	document.getElementById("weightGm" + rowId).readOnly = true;
}

/**
 * clearManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function clearManifestFields(rowId){
	clearFieldById("manifestId" + rowId);
	document.getElementById("manifestDestCity" + rowId).value = "";
	document.getElementById("lockNumber" + rowId).value = "";
	document.getElementById("docType" + rowId).value = "";
}

/**
 * populateLoadReceiveDetailsTO4Manifest
 *
 * @param loadReceiveManifestValidationTO
 * @param rowId
 * @author narmdr
 */
function populateLoadReceiveDetailsTO4Manifest(loadReceiveManifestValidationTO, rowId){
	var loadReceiveDetailsTO = loadReceiveManifestValidationTO.loadReceiveDetailsTO;
	
	document.getElementById("loadNumber" + rowId).value = loadReceiveDetailsTO.loadNumber;
	selectOptionByFirstSplitValue(document.getElementById("docType" + rowId), loadReceiveDetailsTO.docType);
	document.getElementById("lockNumber" + rowId).value = loadReceiveDetailsTO.lockNumber;
	document.getElementById("manifestId" + rowId).value = loadReceiveDetailsTO.manifestId;
	document.getElementById("manifestDestCity" + rowId).value = loadReceiveDetailsTO.manifestDestCity;
	document.getElementById("manifestDestCityDetails" + rowId).value = loadReceiveDetailsTO.manifestDestCityDetails;
	document.getElementById("manifestWeight" + rowId).value = loadReceiveDetailsTO.manifestWeight;
	document.getElementById("receivedStatus" + rowId).value = receivedCode;

	//added origin office and destination office for manifest
	$("#manifestOriginOffId" + rowId).val(loadReceiveDetailsTO.manifestOriginOffId);
	$("#manifestDestOffId" + rowId).val(loadReceiveDetailsTO.manifestDestOffId);
	

	//CR : Weight should be prepopulated wt
	document.getElementById("weight" + rowId).value = loadReceiveDetailsTO.weight;
	populateWeightInKgGmField(loadReceiveDetailsTO.weight, rowId);
	/*//CR : Weight should be taken from weighing machine at hub
	var officetype = document.getElementById("originOfficeType").value;
	if(officetype !=hubCode){
	}*/
	
	validateReceivedStatusByCode(rowId, receivedCode);
	fixFormatUptoThreeDecimalPlace(document.getElementById("weight" + rowId));

	if(loadReceiveManifestValidationTO.isReceive==true){
		document.getElementById("remarks" + rowId).value = loadReceiveDetailsTO.remarks;
	}
}
//Manifest BPL/MBPL validation End

/**
 * disableHeader
 *
 * @author narmdr
 */
function disableHeader(){
	disableEnableHeaderByFlag(true);
}

/**
 * enableHeader
 *
 * @author narmdr
 */
function enableHeader(){
	disableEnableHeaderByFlag(false);
}

/**
 * disableEnableHeaderByFlag
 *
 * @param flag
 * @author narmdr
 */
function disableEnableHeaderByFlag(flag){
	document.getElementById("regionalOffice").disabled = flag;
	document.getElementById("originOffice").disabled = flag;
}

/**
 * cancelLoadReceiveLocal
 *
 * @author narmdr
 */
function cancelLoadReceiveLocal(){
	var flag = confirm("Do you want to cancel the Load Receive Local details.");
	if(flag){
		cancelPage();
		/*url = "./loadReceiveLocal.do?submitName=viewLoadReceiveLocal";
		submitForm(url, "loadReceiveLocalForm");*/
	}
}

/**
 * saveLoadReceiveLocal
 *
 * @author narmdr
 */
function saveLoadReceiveLocal(){
	/*var loadMovementId = getValueByElementId("loadMovementId");
	if(isNull(loadMovementId)){
		if(!validateMandatoryFields()){
			return;
		}
	}*/
	if(!validateMandatoryFields()){
		return;
	}	
	flag=confirm("Do you want to Save the Receive - Local Load Details");
	if(!flag){
		return;
	}
	enableAll();
	var url = "./loadReceiveLocal.do?submitName=saveOrUpdateLoadReceiveLocal";
	ajaxCall(url, "loadReceiveLocalForm", saveCallback);
}

/**
 * saveCallback
 *
 * @param data
 * @author narmdr
 */
function saveCallback(data){
	showProcessing();
	var loadReceiveLocalTO = eval('(' + data + ')');
	
	if(!isNull(loadReceiveLocalTO.errorMessage)){
		alert(loadReceiveLocalTO.errorMessage);
		cancelPage();
		return;
		
	} else if (!isNull(loadReceiveLocalTO.successMessage)){
		alert(loadReceiveLocalTO.successMessage);
		
		//call print function printLoadDispatch(url)
		var confirm1 = confirm("Do you want to print Load Receive Details.");
		if(confirm1){			
			printLoadReceiveLocal();
		}else{
			cancelPage();
		}
	}
		
	/*var loadReceiveLocalTO = eval('(' + data + ')');
	if(!isNull(loadReceiveLocalTO) && loadReceiveLocalTO.isReceiveSaved==true){
		alert(gatePassNoLabel  + " : " + loadReceiveLocalTO.gatePassNumber  + " is saved successfully." );
		//cancelPage();
		var confirm1 = confirm("Do you want to print Load Receive Details.");
		if(confirm1){			
			printLoadReceiveLocal();
		}else{
			cancelPage();
		}
	}else{
		alert("Error in saving.");
		cancelPage();
		return;
	}*/
}

/**
 * cancelPage
 *
 * @author narmdr
 */
function cancelPage(){
	url = "./loadReceiveLocal.do?submitName=viewLoadReceiveLocal";
	window.location = url;
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var pleaseSelectMessage = pleaseSelectMsg + " ";
	var errorEndMsg = "." ;

	var originOffice = document.getElementById("originOffice");
	var vehicleNumber = document.getElementById("vehicleNumber");
	var otherVehicleNumber = document.getElementById("otherVehicleNumber");
	//var actualArrival = document.getElementById("actualArrival");	
	var table = document.getElementById("loadReceiveLocalGrid");
	
	if(isNull(getValueByElementId("loadMovementId")) 
			&& isNull(getValueByElementId("receivedAgainstId")) 
			&& isNull(originOffice.value)){
		originOffice.focus();
		alert(pleaseSelectMessage + originOfficeLabel + errorEndMsg);
		return false;
	}
	if(isNull(vehicleNumber.value)){
		vehicleNumber.focus();
		alert(pleaseSelectMessage + vehicleNumberLabel + errorEndMsg);
		return false;
	}
	if(vehicleNumber.value==othersVehicleCode && isNull(otherVehicleNumber.value)){
		otherVehicleNumber.focus();
		alert(pleaseEnterMessage + "Other " + vehicleNumberLabel + errorEndMsg);
		return false;
	}
	/*if(isNull(actualArrival.value)){
		actualArrival.focus();
		alert(pleaseEnterMessage + actualArrivalLabel + errorEndMsg);
		return false;
	}*/	
	
	//Validating table data	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		
		if(i==(table.rows.length - 1) && table.rows.length>2){
			continue;
		}
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
	}	
	
	return true;
}

/**
 * edit
 *
 * @author narmdr
 */
function edit(){
	buttonEnabled("btnSave", buttonDisableCss);
	var table = document.getElementById('loadReceiveLocalGrid');
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		document.getElementById("lockNumber" + rowId).readOnly = false;
	}
}

/**
 * gatePassNumberFocus
 *
 * @author narmdr
 */
function gatePassNumberFocus(){
	var gatePassNumberObj = document.getElementById("gatePassNumber");	
	if(isNull(gatePassNumberObj.value)){
		document.getElementById("gatePassNumber").readOnly = false;
		gatePassNumberObj.focus();
	}
}

/**
 * mBPLNumberFocus
 *
 * @param rowIdd
 * @author narmdr
 */
function mBPLNumberFocus(rowIdd){
	var loadNumberObj = document.getElementById("loadNumber" + rowIdd);	
	if(isNull(loadNumberObj.value)){
		loadNumberObj.focus();
	}
}


function printLoadReceive(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var gatePassNumberObj = document.getElementById("gatePassNumber");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	
	if(isNull(gatePassNumberObj.value)){
		gatePassNumberObj.focus();
		alert(pleaseEnterMessage + gatePassNoLabel + errorEndMsg);
		return false;
	}
		
	if(!isValidGatepassNo(gatePassNumberObj)){
		//document.getElementById("gatePassNumber").readOnly = true;
		return;
	}
	
	var confirm1 = confirm("Do you want to print Load Receive Details.");
	if(confirm1){			
		printLoadReceiveLocal();
	}
}



function printLoadReceiveLocal(){
	var gatePassNumberObj = document.getElementById("gatePassNumber");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	var regionalOfficeId = getValueByElementId("regionalOfficeId");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	
	var url = "./loadReceiveLocal.do?submitName=printLoadReceiveLocal&gatePassNumber="+gatePassNumberObj.value+"&regionalOfficeId="+regionalOfficeId+"&loggedInOfficeId="+loggedInOfficeId;
	//var w =window.open(url,'','height=450,width=600,left=60,top=120,resizable=yes,scrollbars=auto');
		document.loadReceiveLocalForm.action=url;
		document.loadReceiveLocalForm.submit();
		
		
}





