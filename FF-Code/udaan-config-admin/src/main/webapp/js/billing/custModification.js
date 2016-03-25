var ERROR_FLAG="ERROR";
var HYPHEN_SPACE ="- ";
function custModificationStartUp(){
	document.getElementById("congNo").focus();
	buttonDisabled("submitBtn","btnintform");
	jQuery("#" + "submitBtn").addClass("btnintformbigdis");
	$('#newCustCode').attr("readonly", true);
	var congNo_temp=$("#congNo" ).val();
	if(!congNo_temp == ""){
		document.getElementById("modify").focus();}	
}
function buttonDisabledSave(btnName, styleclass) {
	if (!isNull(getDomElementById(btnName))) {
		jQuery("#" + btnName).attr("disabled", true);
		jQuery("#" + btnName).removeClass(styleclass);
		jQuery("#" + btnName).removeAttr("tabindex");
		jQuery("#" + btnName).addClass("btnintformbigdis");
	}
}
function isValidConsignment(consgNoObj) {
	hideProcessing();
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	consgNoObj.value = $.trim(consgNoObj.value);

	if (isNull(consgNoObj.value)) {
		return false;
	}
	if (consgNoObj.value.length != 12) {
		// clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12
		// characters only!");
		alert("Consignment No. should contain 12 characters only!");
		//document.getElementById("consgNumber").value = "";
		cancelCustModification();
		document.getElementById("consgNo").focus();
		return false;
	}
	if (!consgNoObj.value.substring(0, 1).match(letters)
			|| (!consgNoObj.value.substring(4, 5).match(letters) && !consgNoObj.value
					.substring(1, 4).match(alphaNumeric))
					|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
					|| !numpattern.test(consgNoObj.value.substring(5))) {
		// clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not
		// correct!");
		alert("Consignment No. Format is not correct!");
		//document.getElementById("consgNumber").value = "";
		cancelCustModification();
		document.getElementById("consgNo").focus();
		return false;
	}
	return true;
}
function getConsgBookDeatils(){
	var consgNo=$("#congNo" ).val();
	var consgNo1 = document.getElementById("congNo");
	if(!isNull(consgNo)){
		if(isValidConsignment(consgNo1)){
			showProcessing();
			var url = './custModification.do?submitName=getConsgAndBookDetails&ConsignmentNo='+ consgNo;
			window.location=url;
		}
	}else{
		alert('Consignment No Is Empty');
		hideProcessing();
	}
}
function searchCallback(data){
	alert(data);
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){

			alert(error);  
			$("#bookCustCode" ).val("");
			$("#bookCustName" ).val("");
			$("#consgNo" ).val("");
			document.getElementById("consgNo").focus();
			hideProcessing();
			return ;
		}
		/* var custModificationTo = eval('(' + data + ')');*/

		var manifestWeight = data.totalConsignmentWeight + "";
		alert(manifestWeight);
		document.getElementById("wtKg").value = manifestWeight.split(".")[0];
		document.getElementById("wtGm").value = manifestWeight.split(".")[1];

		//document.getElementById("bookCustCode").value=data.bookCustCode;
		document.getElementById("bookCustName").value=data.bookCustName + '-'+data.bookCustCode;
		document.getElementById("bookCustId").value=data.bookCustId;
		document.getElementById("bookCustTypeCode").value=data.bookCustTypeCode;
		document.getElementById("consgId").value=data.consgId;
		$('#newCustCode').attr("readonly", false);
		document.getElementById("newCustCode").focus();
		hideProcessing();
	}
}
function checkNewCustomer(){
	var custCode=$("#newCustCode" ).val();
	var bookcustCode=$("#bookCustCode" ).val();
	var oldCustTypeCode=$("#bookCustTypeCode" ).val();
	if(!isNull(custCode)){
		if(custCode!=bookcustCode){
			var url = './custModification.do?submitName=checkNewValidCustomer&NewCustomerCode='+ custCode + "&OldCustTypeCode=" + oldCustTypeCode;
			ajaxCallWithoutForm(url, checkNewCustomerCallback);
		}
		else{
			$("#newCustCode" ).val("");
			focusById("newCustCode");
			//document.getElementById("newCustCode").focus();
			alert('Old Cust and New Customer Cant be Same');
		}
	}
	else{
		focusById("newCustCode");
		//document.getElementById("newCustCode").focus();
		alert('Enter New Customer Code');
	}
}
function checkNewCustomerCallback(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			$("#newCustCode" ).val("");
			$("#newCustName" ).val("");
			document.getElementById("newCustCode").focus();
			return ;
		}
		document.getElementById("newCustCode").value=data.newCustCode;
		document.getElementById("newCustName").value=data.newCustName;
		document.getElementById("newCustId").value=data.newCustId;
		jQuery("#"+ "submitBtn").attr("disabled", false);
		jQuery("#"+"submitBtn").removeClass("btnintformbigdis");
		jQuery("#" + "submitBtn").addClass("btnintform");
	}	
}
//submit calls this method for doing the action
function saveOrUpdateCustModification(){
	var newcustomerId="";
	var newCustomerCode="";
	var customerName="";
	var selectedNewCustomer=$("#newCustName").val();
	var shipToCode=selectedNewCustomer.split('-');
	var finallength=shipToCode.length;
	if(finallength >=2){
		shipToCode=selectedNewCustomer.split('-')[finallength-1];
		customerName=selectedNewCustomer.replace(HYPHEN_SPACE+shipToCode.trim(),'');
	}
	for(var i=0;i<custShipCodeArr.length;i++){
		if(data[i].trim()==customerName.trim()){
		if(custShipCodeArr[i].trim()==shipToCode.trim()){
			newcustomerId=custIDArr[i];
			newCustomerCode=custCodeArr[i];
			break;
		}
		}
	}
	if(newcustomerId ==""){
		alert("Please select valid customer name");
		document.getElementById('newCustName').focus();
		return false;
	}
	document.getElementById("newCustId").value=newcustomerId;
	var consgId=$("#consgId").val();
	var regionId=$("#regionId").val();
	var consgNo=$("#congNo").val();
	var cityId=$("#cityId").val();
	var newCustId=newcustomerId;
	var newCustCode=newCustomerCode;
	var shipToCodeValue=shipToCode.trim();
	var stationId=document.getElementById("stationList").value;
	var officeId=document.getElementById("officeList").value;
	var bookingDate=document.getElementById("bookingDate").value;
	var finalwtKg=document.getElementById("finalwtKg").value;
	var finalwtGm=document.getElementById("finalwtGm").value;
	if(finalwtKg.trim() ==0 && finalwtGm.trim() ==0){
		alert("Billing Weight should not be zero.");
		return false;
	}
	var billingWeight=finalwtKg.trim()+ "." +finalwtGm.trim();
	var declaredValue=$("#declareValue").val();
	if(!isNull(consgNo)){
		showProcessing();
		var url = './custModification.do?submitName=saveOrUpdateCustModification&ConsgId='+ consgId 
		+ "&NewCustId=" + newCustId 
		+ "&ConsignmentNo="+ consgNo 
		+ "&NewCustCode="+ newCustCode.trim() 
		+ "&RegionId=" + regionId 
		+ "&StationId=" + stationId 
		+ "&OfficeId=" + officeId 
		+ "&BookingDate=" + bookingDate 
		+ "&BillingWeight=" + billingWeight
		+ "&DeclaredVal=" + declaredValue
		+ "&CityId=" + cityId
		+ "&ShipToCodeId=" + shipToCodeValue;
		jQuery.ajax({
			url : url,
			type : "GET",
			dataType : "json",
			success : function(data) {
				saveOrUpdateCustModificationCallback(data);
			}
		});
	} 
	else{
		hideProcessing();
		document.getElementById("consgNo").focus();
		alert('Please Enter Valid Consignment No');		
	}
	//ajaxCall(url, "custModificationForm", searchCallback);     
}
function saveOrUpdateCustModificationCallback(data){
	hideProcessing();
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			cancelCustModification();
			return ;
		}
		alert(data.sucessMessage);
		cancelCustModification();
		disableAll();
	}		
}
function cancelCustModification(){
	buttonDisabled("submitBtn","btnintform");
	document.getElementById("congNo").focus();
	jQuery("#" + "submitBtn").addClass("btnintformbigdis");
	$("#newCustCode" ).val("");
	$("#newCustName" ).val("");
	$("#bookCustCode" ).val("");
	$("#bookCustName" ).val("");
	$("#consgNo" ).val("");
	$("#bookCustId" ).val("");
	$("#newCustId" ).val("");
	$("#bookCustTypeCode" ).val("");
	$("#newCustTypeCode" ).val("");
	$("#consgId" ).val("");
	$("#regionName" ).val("");
	$("#stationList" ).val("");
	$("#officeList" ).val("");
	$("#finalwtKg" ).val("");
	$("#finalwtGm" ).val("");
	$("#wtKg" ).val("");
	$("#wtGm" ).val("");
	$("#bookingDate" ).val("");
	$("#congNo" ).val("");
	$("#declaredValue").val("");
	$("#declareValue").val("");
	clearDropDownList("stationList");
	clearDropDownList("officeList");
	document.getElementById("consgNo").focus();
}
function getStationList() {
	var regionId = document.getElementById("regionId").value;
	var regionName ="region";
	if(regionName!="Select"){
		if (!isNull(regionId)) {
			showProcessing();
			url = './custModification.do?submitName=getCitysByRegion&regionId='
				+ regionId;
			ajaxCallWithoutForm(url, getCitysList);
		}}
	else if(regionName=="Select"){
		clearDropDownList("stationList");
		clearDropDownList("officeList");
	}

}
function getCitysList(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("stationList", "");
			alert(error);
			document.getElementById('stationList').focus();
		} else {
			var content = document.getElementById('stationList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});
		}

	}else{
		clearDropDownList("officeList");
	}
}
function getBranchesByCity() {
	//clearDropDownList("officeList");
	var cityId = document.getElementById("stationList").value;
	if (!isNull(cityId)) {
		showProcessing();

		url = './custModification.do?submitName=getBranchesByCity&cityId='
			+ cityId;
		ajaxCallWithoutForm(url, populateBranches);
	}
}

function populateBranches(ajaxResp) {	
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("officeList", "");
			alert(error);
			clearDropDownList("officeList");
			document.getElementById('officeList').focus();

		} else {
			var content = document.getElementById('officeList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
			});
			document.getElementById("officeList").focus();
		}
	}else{
		clearDropDownList("officeList");
	}
}

function getNewCustomerList(){
	var cityId = document.getElementById("stationList").value;
	var officeId = document.getElementById("officeList").value;
	if (!isNull(cityId)&&!isNull(officeId)) {
		showProcessing();
		url = './custModification.do?submitName=getNewCustomerList&cityId='
			+ cityId + "&officeId="+officeId;
		jQuery.ajax({
			url : url,
			type : "GET",
			dataType : "json",
			success : function(res) {
				loadNewCustomerList(res);
			}
		});

	}else{
		alert("station and office field's should not be empty.");
	}
}

function loadNewCustomerList(res){
	setDefaultValOnBillingWeight();
	var custCodeName = new Array();
	jQuery('input#newCustName').flushCache();
	$.each( res, function( i, val ) {
		data.push(val.businessName);
		custIDArr.push(val.customerId);
		custCodeArr.push(val.customerCode);
		custShipCodeArr.push(val.shippedToCode);
		custCodeName.push(val.businessName.trim()+" - "+val.shippedToCode.trim());	
	});
	jQuery("#newCustName").autocomplete(custCodeName);
	document.getElementById("bookingDate").focus();
	hideProcessing();

	
}
function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}
function checkFutureDate(fromDate) {
	if (fromDate.value == null || fromDate.value == "")
		return true;
	else {
		if (isFutureDate(fromDate.value)) {
			alert("Future date is not allowed.");
			document.getElementById('bookingDate').value = "";
			document.getElementById('bookingDate').focus();
		} else {
			return true;
		}

	}
}

function checkBackDate(fromDate) {
	if (fromDate.value == null || fromDate.value == "")
		return true;
	var d2 = new Date();
	var date = trimString(fromDate.value);
	date = date.split("/");
	var myDate = new Date();
	myDate.setFullYear(date[2], date[1] - 1, date[0]);
	var months;
	months = (d2.getFullYear() - myDate.getFullYear()) * 12;
	months =(months- myDate.getMonth()) + 1;
	//if(months == 0 || months == -1 ||months == -2 ){
	var temp=d2.getDate()+1;
	var lastdate=new Date(d2.getFullYear(),d2.getMonth(),temp);
	lastdate.setMonth(d2.getMonth()-1);
	var dynamicdate=new Date(myDate.getFullYear(),myDate.getMonth(),myDate.getDate());
	var futurdate=new Date(d2.getFullYear(),d2.getMonth(),d2.getDate());
	if(lastdate<=dynamicdate){	
		if(dynamicdate<=futurdate)
		{
			return true;
		}else{
			alert("Booking allows only one month before consignments");
			document.getElementById('bookingDate').value = "";
			document.getElementById('bookingDate').focus();
		}		
	}else{	
		alert("Booking allows only one month before consignments");
		document.getElementById('bookingDate').value = "";
		document.getElementById('bookingDate').focus();
	}
	return true;
} 
/**
 * clears the screen
 * 
 * @author uchauhan
 */
function clearDetails() {
	var flag = confirm("Do you want to clear the details ?");
	document.getElementById("congNo").focus();
	if (flag) {
		$("#newCustCode" ).val("");
		$("#newCustName" ).val("");
		$("#bookCustCode" ).val("");
		$("#bookCustName" ).val("");
		$("#consgNo" ).val("");
		$("#bookCustId" ).val("");
		$("#newCustId" ).val("");
		$("#bookCustTypeCode" ).val("");
		$("#newCustTypeCode" ).val("");
		$("#consgId" ).val("");
		$("#regionName" ).val("");
		$("#stationList" ).val("");
		$("#officeList" ).val("");
		$("#finalwtKg" ).val("");
		$("#finalwtGm" ).val("");
		$("#wtKg" ).val("");
		$("#wtGm" ).val("");
		$("#bookingDate" ).val("");
		$("#congNo" ).val("");
		$("#declaredValue" ).val("");
		$("#declareValue" ).val("");
		clearDropDownList("stationList");
		clearDropDownList("officeList");
	}
}
function mandatoryFieldsCheck(){
	var finalwtKg=document.getElementById("finalwtKg").value;
	var finalwtGm=document.getElementById("finalwtGm").value;
	var newCustName=document.getElementById("newCustName").value;
	if(newCustName == ""){
		alert("New Customer Field should not be empty");
		document.getElementById('newCustName').focus();
		return false;
	}
	if(!(newCustName.indexOf("-") > -1)){
		alert("New Customer Field is not correct format");
		document.getElementById('newCustName').focus();
		return false;
	}
	var regionName=document.getElementById("regionName").value;
	if(regionName!="")
	{
		var stationList=document.getElementById("stationList").value;
		if(stationList == ""){
			alert("Station field should not be empty");
			document.getElementById('stationList').focus();
			return false;
		}else{
			var officeList=document.getElementById("officeList").value;
			if(officeList == ""){
				alert("office field should not be empty");
				document.getElementById('officeList').focus();
				return false;
			}else{
				var bookingDate=document.getElementById("bookingDate").value;
				if(bookingDate == ""){
					alert("Please select the booking date");
					document.getElementById('bookingDate').focus();
					return false;	
				}
			}	
		}	
	}
	saveOrUpdateCustModification();
}
function updateCustConsigment(){
	enableSubmitButton();
	//getDomElementById("newCustName").disabled = false;
	var flag=document.getElementById("isInvoice").value;
	var consgflag=$('#declaredValue').val();
	if(flag!="true"){
		$('#newCustName').attr("readonly", false);}
	getDomElementById("finalwtKg").disabled = false;
	getDomElementById("finalwtGm").disabled = false;
	var regionName=document.getElementById("regionName").value;
	if(regionName != ""){
		getDomElementById("regionName").disabled = false;
		$('#regionName').attr("readonly", true);
		getDomElementById("bookingDate").disabled = false;
		jQuery("select").attr("disabled", false);
		jQuery("select").removeAttr("tabindex");
		document.getElementById("stationList").focus();
	}else{
		document.getElementById("newCustName").focus();
	}
	if(consgflag!=""){
		$('#declareValue').attr("readonly", false);
	}	
	document.getElementById("submitBtn").disabled = false;	 
}
function disableAll(){
	getDomElementById("bookCustName").disabled = true;
	getDomElementById("wtKg").disabled = true;
	getDomElementById("wtGm").disabled = true;
	getDomElementById("newCustName").disabled = true;
	getDomElementById("finalwtKg").disabled = true;
	getDomElementById("finalwtGm").disabled = true;
	getDomElementById("regionName").disabled = true;
	getDomElementById("bookingDate").disabled = true;
	jQuery("select").attr("disabled", true);
	jQuery("select").removeAttr("tabindex");	
}
function onlyNos(e, t) {
	try {
		if (window.event) {
			var charCode = window.event.keyCode;   
		}
		else if (e) {
			var charCode = e.which;
		}
		else { return true; }
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			return false;
		}
		if (charCode == 13) { 
			document.getElementById("finalwtGm").focus();
			if(t == "finalwtGm")
				document.getElementById("declareValue").focus();	
		}
		return true;
	}
	catch (err) {
		alert(err.Description);
	}

} 
function enableSubmitButton(){
	var consgNo=$("#congNo" ).val();
	var consgNo1 = document.getElementById("congNo");
	if(!isNull(consgNo)){
		if(isValidConsignment(consgNo1)){
			var existingcust=document.getElementById("bookCustName").value;
			var regionname=document.getElementById("regionName").value;
			if(existingcust!=""|| regionname!=""){
				buttonEnabled("submitBtn","btnintform");
				jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
			}
		}else{
			alert('Consignment No Is Empty');
			hideProcessing();
		}
	}
}

//this alert for wraping the text alert
function wrapText(text, maxChars) {
	var ret = [];
	var words = text.split(/\b/);
	var currentLine = '';
	var lastWhite = '';
	words.forEach(function(d) {
		var prev = currentLine;
		currentLine += lastWhite + d;
		var l = currentLine.length;
		if (l > maxChars) {
			ret.push(prev.trim());
			currentLine = d;
			lastWhite = '';
		} else {
			var m = currentLine.match(/(.*)(\s+)$/);
			lastWhite = (m && m.length === 3 && m[2]) || '';
			currentLine = (m && m.length === 3 && m[1]) || currentLine;
		}
	});
	if (currentLine) {
		ret.push(currentLine.trim());
	}
	return ret.join("\n");
}
function setDefaultValOnBillingWeight(){
	var finalwtKg=document.getElementById("finalwtKg").value;
	var finalwtGm=document.getElementById("finalwtGm").value;
	if(finalwtKg == ""){
		document.getElementById("finalwtKg").value=0;
	}
	if(finalwtGm == ""){
		document.getElementById("finalwtGm").value=0;
	}
}
/**
 * isValidDecValue
 * 
 * @param obj
 * @author sdalli
 */
function isValidDecValueCNModi(obj) {
	var decVal = obj.value;
	var decValue = parseFloat(decVal);
	if (decValue == "0" || isNull(decVal)) {
		alert("Please enter a non-zero declared value.");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
}
function numbersOnlyAllow(myfield, e){
	var key;
	var keychar;
	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;
	keychar = String.fromCharCode(key);
	// control keys
	if ((key==null) || (key==0) || (key==8) || (key==9) || (key==27) )
		return true;
	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
		return true;
	// only one decimal point
	/*else if ((keychar == ".")){
        if (myfield.value.indexOf(keychar) > -1)
            return false;
    }*/
	else if(key==13){
		document.getElementById("submitBtn").focus();
	}

	else
		return false;
}



