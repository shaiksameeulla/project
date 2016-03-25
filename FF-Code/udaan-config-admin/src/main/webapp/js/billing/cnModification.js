var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var pleaseEnterMsg = "Please Enter ";
var errorEndMsg = " !" ;
var formId = "custModificationForm";
var HYPHEN_SPACE =" - ";

var custIDArr = new Array();
var custCodeArr = new Array();
var custNameArr = new Array();
var custShippedToCodeArr = new Array();
var custCodeNameArr = new Array();


function custModificationStartUp(){	
	disableEnableFields();
	fnEnableDisbleField('congNo', false);
	disableGlobalButton("submitBtn");
	$("#congNo").focus();		
}

function disableEnableFields(){
	disableAll();
	buttonEnabled("searchBtn",'btnintform');
//	enableGlobalButton("submitBtn");
	enableGlobalButton("modify");
	enableGlobalButton("clear");
}

function fnDisableDate4NonExcessCns(){
	if(!isNull($('#isExcessConsg').val()) && $('#isExcessConsg').val() == 'Y'){
		show_calendar("bookingDate", this.value);
	}
}

function isValidConsignment(consgNoObj) {
	//Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	//last 7 char are numeric only.
	
	var numpattern = /^[0-9]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	
	consgNoObj.val($.trim(consgNoObj.val()));
	consgNoObj.val(consgNoObj.val().toUpperCase());

	if (isNull(consgNoObj.val())) {
		focusAlertMsg4TxtBox(consgNoObj, "Consignment Number");
		return false;
	}

	if (consgNoObj.val().length!=12) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12 characters only!");
		return false;
	}
	
	if (!consgNoObj.val().substring(0, 5).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.val().substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}
	
	return true;
}
function focusAlertMsg4TxtBox(obj, fieldName){
	obj.focus();
	alert(pleaseEnterMsg + fieldName + errorEndMsg);
}
function getConsignmentDetails(){
	var consgNoObj = $("#congNo");
	consgNoObj.val($.trim(consgNoObj.val()));
	if(!isValidConsignment(consgNoObj)){
		consgNoObj.val("");
		return;
	}	
	var url = './custModification.do?submitName=getConsignmentDetails&ConsignmentNo='+ consgNoObj.val();
	ajaxCalWithoutForm(url, populateConsignmentDtls);
}
function populateConsignmentDtls(data){	
	if (isNull(data)) {	
		return;
	}
	var cnModifyTO = data;
	var errorMsg = cnModifyTO.errorMessage;
	if(!isNull(errorMsg)){
		clearFocusAlertMsg($("#congNo"), errorMsg);
		return;
	}
	enableAll();
	var custNameCode = "";
	//booking information
	if(!isNull(cnModifyTO.bkgCustTO)){
		custNameCode = cnModifyTO.bkgCustTO.businessName;
		if(!isNull(cnModifyTO.bkgCustTO.shippedToCode)){
			custNameCode = custNameCode + HYPHEN_SPACE + cnModifyTO.bkgCustTO.shippedToCode;
		}
		$('#bookCustName').val(custNameCode);
	}
	$('#bkgWeight').val(cnModifyTO.bkgFinalWeight);
	$('#wtKg').val(cnModifyTO.bkgWtKg);
	$('#wtGm').val(cnModifyTO.bkgWtGm);	
	$('#bkgCnType').val(cnModifyTO.bkgCnType);	
	$('#declaredValue').val(cnModifyTO.bkgDeclaredValue);
	
	//Excess consignment			
	$('#isExcessConsg').val(cnModifyTO.isExcessConsg);
	if(!isNull(cnModifyTO.bkgRegionTO)){
		fnEnableDisbleField('stationList', true);
		$('#regionId').val(cnModifyTO.bkgRegionTO.regionId);
		$('#regionName').val(cnModifyTO.bkgRegionTO.regionName);
	}
	if(!isNull(cnModifyTO.cityList)){
		printAjaxResList2Dropdown('stationList', cnModifyTO.cityList);				
	}
	if(!isNull(cnModifyTO.customerTOs)){
		populateCustomerName(cnModifyTO.customerTOs);
	}
	//New information
	if(!isNull(cnModifyTO.bkgCustTO)){
		$('#newCustName').val(custNameCode);
		$('#newCustShippedToCode').val(cnModifyTO.bkgCustTO.shippedToCode);
		$('#newCustCode').val(cnModifyTO.bkgCustTO.customerCode);
		$('#newCustId').val(cnModifyTO.bkgCustTO.customerId);
	}
	$('#newCnWeight').val(cnModifyTO.bkgFinalWeight);
	$('#finalwtKg').val(cnModifyTO.bkgWtKg);		
	$('#finalwtGm').val(cnModifyTO.bkgWtGm);
	if(!isNull(cnModifyTO.cnTypeTO)){
		var cnTypeIdCodeText = cnModifyTO.cnTypeTO.consignmentId+"#"+cnModifyTO.cnTypeTO.consignmentCode;
		showDropDownBySelected("consignmentType", cnTypeIdCodeText);
		$('#newCnTypeId').val(cnModifyTO.cnTypeTO.consignmentId);
		$('#newCnTypeCode').val(cnTypeIdCodeText);
	}			
	$('#newDeclaredValue').val(cnModifyTO.bkgDeclaredValue);
	$('#cnCityId').val(cnModifyTO.cityId);
	$('#bookingDate').val(cnModifyTO.exBookingDate);
	
	$('#isCustEditable').val(cnModifyTO.isCustEditable);
	$('#isCnTypeEditable').val(cnModifyTO.isCnTypeEditable);
	$('#isWeightEditable').val(cnModifyTO.isWeightEditable);
	$('#isDecValEditable').val(cnModifyTO.isDecValEditable);
	
	disableEnableFields();
}
function fnModifyConsigment(){
	if(!isNull($("#congNo").val())){
		enableGlobalButton("submitBtn");

		if($('#isCustEditable').val() == 'Y'){
			fnEnableDisbleField('newCustName', false);
		}
		if($('#isCnTypeEditable').val() == 'Y'){
			fnEnableDisbleField('consignmentType', false);
		}
		if($('#isDecValEditable').val() == 'Y'){
			fnEnableDisbleField('newDeclaredValue', false);
		}
		if($('#isWeightEditable').val() == 'Y'){
			fnEnableDisbleField('finalwtKg', false);
			fnEnableDisbleField('finalwtGm', false);
		}
		if($('#isExcessConsg').val() == 'Y'){
			fnEnableDisbleField('stationList', false);
			fnEnableDisbleField('officeList', false);
		}
	}
}
function fnEnableDisbleField(inputId, flag){
	$('#'+inputId).attr("readonly", flag);	
	$('#'+inputId).attr("disabled", flag);	
}
function fnClear(){
	if(confirm("Do you want to clear the details?")){
		var url = "./custModification.do?submitName=preparePage";
		document.custModificationForm.action = url;
		document.custModificationForm.submit();
	}
}
function getBranchesByCity() {
	var cityId = $("#stationList").val();
	if (!isNull(cityId)) {
		url = './custModification.do?submitName=getBranchesByCity&cityId='+ cityId;
		ajaxCallWithoutForm(url, populateBranches);
	}
}

function populateBranches(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var errorMsg = ajaxResp[ERROR_FLAG];
		if (!isNull(ajaxResp) && !isNull(errorMsg)) {
			showDropDownBySelected("officeList", "");
			alert(errorMsg);
			clearDropDown("officeList");
			$('#officeList').focus();
		} else {
			printAjaxResList2Dropdown('officeList', ajaxResp);
			$('#officeList').focus();
		}
	}else{
		clearDropDown("officeList");
	}
}
function printAjaxResList2Dropdown(domObjId, ajaxList){
	var content = getDomElementById(domObjId);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(ajaxList, function(index, value) {
		var option;
		option = document.createElement("option");
		if(domObjId == 'officeList'){
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
		}
		if(domObjId == 'stationList'){
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
		}		
		content.appendChild(option);
	});
}
function checkFutureDate(selectedDate){
	if(!isNull(selectedDate.value) && isFutureDate(selectedDate.value)){
		clearFocusAlertMsg($("#bookingDate"), "Future Date is not allowed!");		
	}
}
function checkBackDate(selectedDate){
	var dateDiff = null;
	if (!isNull(selectedDate.value)){
		var currentDate = $('#currentDate').val();
		dateDiff = calculateDateDiff(selectedDate.value, currentDate);
		if(dateDiff > CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED){
			clearFocusAlertMsg($('#bookingDate'), "Booking allows till "+CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED+" days before consignments");
			return false;
		}
	}
	return true;
}
function getCustomerListByCityAndBranch(){
	var cityId = getDomElementById("stationList").value;
	var officeId = getDomElementById("officeList").value;
	$('#newCustName').val("");
	$('#newCustId').val("");
	$('#newCustCode').val("");
	$('#newCustShippedToCode').val("");
	if (!isNull(cityId) && !isNull(officeId)) {
		$('#cnCityId').val(cityId);
		url = './custModification.do?submitName=getCustomerListByCityAndBranch&cityId='+ cityId + "&officeId="+officeId;
		ajaxCall(url, formId, loadCustomerList);
	}else{
		alert(pleaseEnterMsg+"station and office"+errorEndMsg);
	}
}
function loadCustomerList(ajaxResp){
	if (!isNull(ajaxResp)) {
		var result = jsonJqueryParser(ajaxResp);
		var errorMsg = result[ERROR_FLAG];
		if (!isNull(errorMsg)) {
			alert(errorMsg);
		} else {
			var custList = eval('(' + ajaxResp + ')');
			populateCustomerName(custList);			
		}
		$('#newCustName').focus();
	}
}
function populateCustomerName(custList){
	custIDArr = new Array();
	custCodeArr = new Array();
	custNameArr = new Array();
	custCodeNameArr = new Array();
	custShippedToCodeArr = new Array();
	
	$('#newCustName').flushCache();
	$.each(custList, function(index, value) {
		custIDArr.push(this.customerId);
		custCodeArr.push(this.customerCode);
		custNameArr.push(this.businessName.trim());
		custShippedToCodeArr.push(this.shippedToCode);
		custCodeNameArr.push(this.businessName.trim() + HYPHEN_SPACE + this.shippedToCode.trim());
	});
	$('#newCustName').autocomplete(custCodeNameArr);
}
function getAndSetCustomerDtls() {
	var selectedNewCustomer = $("#newCustName").val();
	var shipToCode = selectedNewCustomer.split('-');
	var finallength = shipToCode.length;
	if (finallength >= 2) {
		shipToCode = selectedNewCustomer.split('-')[finallength - 1];
		var customerName = selectedNewCustomer.replace(HYPHEN_SPACE+ shipToCode.trim(), '');
		
		for ( var i = 0; i < custShippedToCodeArr.length; i++) {
			if (custNameArr[i].trim() == customerName.trim()) {
				if (custShippedToCodeArr[i].trim() == shipToCode.trim()) {
					$('#newCustId').val(custIDArr[i]);
					$('#newCustCode').val(custCodeArr[i]);
					$('#newCustShippedToCode').val(custShippedToCodeArr[i]);
					break;
				}
			}
		}
	}
}
function saveOrUpdateCustModification(){
	getAndSetCustomerDtls();
	if(fnMandatoryCkeck()){
		var url = "./custModification.do?submitName=saveOrUpdateCustModification";
		ajaxJquery(url, formId, saveOrUpdateCustModificationCallback);
	}
}

function saveOrUpdateCustModificationCallback(response) {
	if (!isNull(response)) {
		var result = jsonJqueryParser(response);
		var success = result[SUCCESS_FLAG];
		var failure = result[ERROR_FLAG];
		if (!isNull(success)) {
			alert(success);
			fnClear();
		} else if (!isNull(failure)) {
			alert(failure);
		} else {
			alert("Error in saving");
		}
		disableAll();
	}
	disableEnableFields();
}

function fnMandatoryCkeck(){
	if($('#isExcessConsg').val() == 'Y')
	{
		if(isNull($('#stationList').val())){
			focusAlertMsg4TxtBox($("#stationList"), "Station");
			return false;
		}else{
			if(isNull($('#officeList').val())){
				focusAlertMsg4TxtBox($("#officeList"), "Office");
				return false;
			}else{
				if(isNull($('#bookingDate').val())){
					focusAlertMsg4TxtBox($("#bookingDate"), "Booking Date");
					return false;	
				}
			}	
		}	
	}
	var newCustName = $("#newCustName").val();
	if(isNull(newCustName) || isNull($('#newCustId').val())){
		focusAlertMsg4TxtBox($("#newCustName"), "Customer");
		return false;
	}
	if(!fnDeclaredValEditable()){
		return false;
	}
	var cnType = $('#consignmentType').val();
	if(cnType.split("#")[1] == CONSIGNMENT_TYPE_PARCEL_CODE){
		return isValidDecValue();
	}
	if(!(newCustName.indexOf("-") > -1)){
		alert("Customer is not in a correct format");
		$("#newCustName").focus();
		return false;
	}	
	return true;
}
function setCnFinalWeight(){
	var finalWeight = 0;
	var weightKg = $("#finalwtKg").val();
	var weightGm = $("#finalwtGm").val();
	if (isNull(weightKg))
		weightKg = "0";
	if (isNull(weightGm))
		weightGm = "0";
	finalWeight = weightKg + "." + weightGm;
	finalWeight = parseFloat(finalWeight).toFixed(3);
	$("#newCnWeight").val(finalWeight);

	if (!isEmptyWeight(finalWeight)) {
		$("#finalwtKg").val(finalWeight.split(".")[0]);
		$("#finalwtGm").val(finalWeight.split(".")[1]);		
	}
}
function isEmptyWeight(value) {
	var flag = false;
	if (isNull(value) || value == "0.000") {
		flag = true;
	}
	return flag;
}
function isValidDecValue() {
	var decVal = $("#newDeclaredValue").val();	
	if (isNull(decVal)  || decVal == "0.00") {
		focusAlertMsg4TxtBox($("#newDeclaredValue"), "a non-zero declared value");
		return false;
	}else{
		$("#newDeclaredValue").val(parseFloat(decVal).toFixed(2));
	}
	return true;
}

function fnDeclaredValEditable(){
	var cnTypeIdCode = $('#consignmentType').val();	
	if(isNull(cnTypeIdCode)){
		focusAlertMsg4TxtBox($("#consignmentType"), "Consignment Type");
		return false;
	}else if(cnTypeIdCode.split("#")[1] == CONSIGNMENT_TYPE_PARCEL_CODE){
		fnEnableDisbleField('newDeclaredValue', false);
		$('#newDeclaredValue').focus();
	}else{
		$('#newDeclaredValue').val("");
		fnEnableDisbleField('newDeclaredValue', true);
	}
	$('#newCnTypeId').val(cnTypeIdCode.split("#")[0]);
	return true;
}

function fnClearCustomer(){
	$('#newCustName').val("");
	$('#newCustShippedToCode').val("");
	$('#newCustCode').val("");
	$('#newCustId').val("");
}