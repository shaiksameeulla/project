function getCustomerList(id) {
	var region = $("#" + id).val();
	if (!isNull(region)) {
		var regId = region.split("-", 1);
		var url = './payment-adviceReport.do?submitName=getCustomerList&regionId='
				+ regId;
		ajaxCallWithoutForm(url, populateCustomerList);
	} else {
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
	}

}

function populateCustomerList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.customerId;
			var ch = this.businessName + " - " + this.customerCode;
			option.appendChild(document.createTextNode(ch));
			content.appendChild(option);
		});
	}
}

function getchequeNumbers() {
	var ptype = $("#paymentType").val();
	if (!isNull(ptype) && ptype == "Cheque") {
		var custId = $("#customerList").val();
		var fromDate = $("#fromDate").val();// from date
		var toDate = $("#toDate").val();// to date
		var regId = "0";
		var msgAlert = "";
		
			if (isNull(custId)) {
				msgAlert = msgAlert + "Select Customer \n";
			}
			if (fromDate == null || $.trim(fromDate).length <= 0) {
				msgAlert = msgAlert + "Select From Date \n";
			} 
			if (toDate == null || $.trim(toDate).length <= 0) {
				msgAlert = msgAlert + "Select To Date \n";
			} 		
			if (msgAlert != "") {
				alert(msgAlert);
				document.getElementById('paymentType').selectedIndex = 0;
				return;
			} else if (checkDate('fromDate', 'toDate')) {
				if (ptype == 'Cheque') {
					if(custId != "All"){
					document.getElementById("chequeNo").disabled = false;
						var url = './payment-adviceReport.do?submitName=getChequeNumbers&customerId='
							+ custId
							+ "&fromDate="
							+ fromDate
							+ "&toDate="
							+ toDate;
					ajaxCallWithoutForm(url, populateChequeNumbers);
				}else {
					var RegId = $("#regionList").val();
					document.getElementById("chequeNo").disabled = false;
					var url = './payment-adviceReport.do?submitName=getChequeNumbersByRegion&regionId='
						+ RegId
						+ "&fromDate="
						+ fromDate
						+ "&toDate="
						+ toDate;
				ajaxCallWithoutForm(url, populateChequeNumbers);
					
				}
				}else {
					$("#chequeNo").empty();
					document.getElementById("chequeNo").disabled = true;
				}
			}
	}

}

function populateChequeNumbers(data) {
	if (!isNull(data)) {
		var content = document.getElementById('chequeNo');
		content.innerHTML = "";
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = value;
			option.appendChild(document.createTextNode(value));
			content.appendChild(option);
		});
	}else{
		alert("No Check Details Found");
	}
}

function showPaymentAdvice() {
	var msg = "";
	var fromDate = $("#fromDate").val();// from date
	var toDate = $("#toDate").val();// to date
	var region = $("#regionList").val();
	var customerid = $("#customerList").val();// customer id
	var paymentType = $("#paymentType").val();// payment type
	var paymentCode;
	if (paymentType == "Cheque") {
		paymentCode = "C";
	} else {
		paymentCode = "F";
	}
	var chequeNo = $("#chequeNo").val();
	if(chequeNo ==null){
		chequeNo = 'All';
	}
	var r_id = null;
	var regionName = null;
	var c_name = null;
	var c_code = null;
	var customer = document.getElementById('customerList').options[document
			.getElementById('customerList').selectedIndex].text;
	if (isNull(region)) {
		msg = msg + "Select Origin Region \n";
	} else {
		/*var regDt = region.split("-");
		r_id = regDt[0];
		regionName = regDt[1] + " - " + regDt[2];*/
		regionName = document.getElementById('regionList').options[document
		                                  			.getElementById('regionList').selectedIndex].text;
	}
	if (isNull(customerid)) {
		msg = msg + "Select Customer \n";
	}else if(customerid == 'All'){
		c_code = 'All';
		c_name = 'All';
	}
	else {
		var custDt = customer.split("-");
		c_name = custDt[0];
		c_code = custDt[1]+custDt[2];
		c_name = trimString(c_name);
		c_code = trimString(c_code);
	}
	if (fromDate == null || $.trim(fromDate).length <= 0) {
		msg = msg + "Select From Date \n";
	} else {
		fromDate = changeDateFormat(fromDate);
	}
	if (toDate == null || $.trim(toDate).length <= 0) {
		msg = msg + "Select To Date \n";
	} else {
		toDate = changeDateFormat(toDate);
	}
	if (isNull(paymentType)) {
		msg = msg + "Select Payment type \n";
	}
	if (paymentCode == "C" && isNull(chequeNo)) {
		msg = msg + "Cheque numbers are not available for selected customer\n";
	}
	if (msg != "") {
		alert(msg);
		return;
	} else if (checkDate('fromDate', 'toDate')) {
		//alert(fromDate);
		var url = '/udaan-report/pages/reportviewer/paymentAdviceViewer.jsp?'
				+ "&regionName=" + regionName + "&regionId=" + region + "&c_id=" + customerid
				+ "&c_name=" + c_name + "&c_code=" + c_code
				+ "&paymentTypeName=" + paymentType + "&paymentTypeCode="
				+ paymentCode + "&checkNo=" + chequeNo + "&fromDate="
				+ fromDate + "&toDate=" + toDate;
		//alert(url);
		window.open(url, "_blank");
	}

}

function changeDateFormat(date) {
	var arrStartDate = date.split("/");
	var newDate = arrStartDate[2] + "-" + arrStartDate[1] + "-"
			+ arrStartDate[0];
	return newDate;
}

function resetFieldsOnCustomer() {
	document.getElementById('paymentType').selectedIndex = 0;
	$("#chequeNo").empty();
	document.getElementById("chequeNo").disabled = true;
}
function triggeringRegionCustomers(){
	var officeType = $("#officeType").val();
	//var branchOfficeType = $("#branchOfficeType").val();
	//var hubOfficeType = $("#hubOfficeType").val();
	
	/*if(!isNull(officeType) && officeType==branchOfficeType && officeType==hubOfficeType){
		//trigger branch customer
	
		$("#branchList").trigger("change");
	}else{
		//alert("event not triggered");
	}*/
	if(officeType == "HO" ||officeType == "BO" || officeType == "RO" ){
		//trigger branch customer
	
		$("#regionList").trigger("change");
	}else{
		//alert("event not triggered");
	}
}