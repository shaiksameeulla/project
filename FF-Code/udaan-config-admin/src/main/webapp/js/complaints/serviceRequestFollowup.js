var followupForm = "serviceRequestFollowupForm";
var ERROR_FLAG = "ERROR";

// Add Row Funtions..
var serialNo = 1;
var followuprowCount = 1;

function addfollowupRow() {
	$('#followupDetails')
			.dataTable()
			.fnAddData(
					[
							serialNo,
							'<input type="text" id="followupDtls'
									+ followuprowCount
									+ '" name="followupDtls" class="txtbox width100"  readonly="true"/>',
							'<input type="text" id="consignNo'
									+ followuprowCount
									+ '" name="consignNo" class="txtbox width100"  readonly="true"/>',
							'<input type="text" id="followupDate'
									+ followuprowCount
									+ '" name="followupDate" class="txtbox width80"  readonly="true"/>',
							'<input type="text" id="followupTime'
									+ followuprowCount
									+ '" name="followupTime" class="txtbox width70"  readonly="true"/>',
							'<input type="text" id="modeOfInteraction'
									+ followuprowCount
									+ '" name="modeOfInteraction" class="txtbox width80"  readonly="true"/>',
							'<input type="text" id="interactionWith'
									+ followuprowCount
									+ '" name="interactionWith" class="txtbox width110"  readonly="true"/>',
							'<input type="text" id="name'
									+ followuprowCount
									+ '" name="name" class="txtbox width110"  readonly="true"/>',
							'<input type="text" id="email'
									+ followuprowCount
									+ '" name="email" class="txtbox width170"  readonly="true"/>',
							'<textarea style="width: 225px; height: 50px; resize:none" id="followupNote'
									+ followuprowCount
									+ '" name="followupNote" class="txtbox width170"  readonly="true"/>',
							'<input type="text" id="empDtls'
									+ followuprowCount
									+ '" name="empDtls" class="txtbox width170"  readonly="true"/>']);
	followuprowCount++;
	serialNo++;
}

/**
 * Initial setup
 */
function prepareComplaintFollowup() {
	var url = './serviceRequestFollowup.do?submitName=prepareComplaintFollowup&complaintId='
			+ complaintId;
	ajaxJquery(url, followupForm, setServiceRequestFollowup);
}

/**
 * @param ajaxResp
 */
function setServiceRequestFollowup(ajaxResp) {
	var followupDtls = jsonJqueryParser(ajaxResp);
	document.getElementById("followUpDate").value = followupDtls.followUpDate;
	document.getElementById("customerName").value = (followupDtls.customerName).trim();
	document.getElementById("email").value = followupDtls.email;
	callerEmail = followupDtls.email;
	if (!isNull(followupDtls.regionTOs)) {
		var content = document.getElementById('regionId');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(followupDtls.regionTOs, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.regionId;
			option.appendChild(document
							.createTextNode(this.regionName));
			content.appendChild(option);
		});
	}
}

/**
 * Save or update followup
 */
function saveOrUpdateFollowup() {
	if (validateFollowupDetails()) {
		var complaintId = document.getElementById("complaintId").value;
		var url = './serviceRequestFollowup.do?submitName=saveOrUpdateFollowup&complaintId='
				+ complaintId;
		ajaxJquery(url, followupForm, callSaveOrUpdateServiceRequestFollowup);
	}
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdateServiceRequestFollowup(ajaxResp) {
	var response = ajaxResp;
	if (response != null && response == 'SUCCESS') {
		alert('Follow up saved successfully.');
		clearFormData();
	} else {
		alert('Follow up saved Unsuccessfully. :: ' + response);
	}
}

/**
 * Validate Details
 * 
 * @returns {Boolean}
 */
function validateFollowupDetails() {
	var complaintNo = document.getElementById("complaintNo").value;
	var mail = document.getElementById("mail");
	var phone = document.getElementById("phone");
	var customerName = document.getElementById("customerName").value;
	document.getElementById("customerName").value = customerName.trim();
	var email = document.getElementById("email").value;
	var followupNote = document.getElementById("followupNote").value;
	var customer = document.getElementById("customer");
	var origin = document.getElementById("origin");
	var branch = document.getElementById("callerBranch");
/*	var regionId = document.getElementById('regionId').value;
	var cityId = document.getElementById('cityId').value;
	var officeId = document.getElementById('officeId').value;
	var employeeId = document.getElementById('employeeId').value;*/
	/*if (isNull(trimString(complaintNo))) {
		alert("Please Enter complaint Number");
		setTimeout(function() {
			document.getElementById("complaintNo").focus();
		}, 10);
		return false;
	} else */
	if ((mail.checked == false) && (phone.checked == false)) {
		alert("Please select Follow Up Mode");
		return false;
	} else if ((customer.checked == false) && (origin.checked == false)
			&& (branch.checked == false)) {
		alert("Please select Follow Up with");
		return false;
	} else if (isNull(trimString(customerName))) {
		alert("Please Enter Customer Name");
		setTimeout(function() {
			document.getElementById("customerName").focus();
		}, 10);
		return false;
	/*} else if (isNull(trimString(email))) {
		alert("Please Enter Email");
		setTimeout(function() {
			document.getElementById("email").focus();
		}, 10);
		return false;*/
/*	} else if (isNull(regionId)) {
		alert("Please Select Region");
		document.getElementById('regionId').focus();
		return false;
	}else if (isNull(cityId)) {
		alert("Please Select City");
		document.getElementById('cityId').focus();
		return false;
	}else if (isNull(officeId)) {
		alert("Please Select Office");
		document.getElementById('officeId').focus();
		return false;
	}else if (isNull(employeeId)) {
		alert("Please Select Employee");
		document.getElementById('employeeId').focus();
		return false;*/
	}else if (isNull(trimString(followupNote))) {
		alert("Please Enter Follow up Note");
		document.getElementById('followupNote').focus();
		return false;
	}
	return true;
}

/**
 * get followup Details
 */
function getComplaintFollowupDetails() {
	//var complaintId = document.getElementById("complaintId").value;
	var url = './serviceRequestFollowup.do?submitName=getComplaintFollowupDetails&complaintId='
			+ complaintId;
	ajaxCallWithoutForm(url, printComplaintFollowupDetails);
}

function printComplaintFollowupDetails(followupDtls) {
	if (!isNull(followupDtls)) {
		if (isJsonResponce(followupDtls)) {
			return;
		}
		for ( var i = 1; i <= followupDtls.length; i++) {
			$('.dataTables_scroll').width("100%");
			if (isNull(document.getElementById("followupDtls" + i))) {
				addfollowupRow();
			}
			document.getElementById("followupDtls" + i).value = followupDtls[i - 1].followupDetails;
			document.getElementById("consignNo" + i).value = followupDtls[i - 1].consigNo;
			document.getElementById("followupDate" + i).value = followupDtls[i - 1].followUpDate;
			document.getElementById("followupTime" + i).value = followupDtls[i - 1].followupTime;
			document.getElementById("modeOfInteraction" + i).value = followupDtls[i - 1].callFrom;
			document.getElementById("interactionWith" + i).value = followupDtls[i - 1].caller;
			document.getElementById("name" + i).value = (followupDtls[i - 1].customerName).trim();
			document.getElementById("email" + i).value = followupDtls[i - 1].email;
			document.getElementById("followupNote" + i).value = followupDtls[i - 1].followupNote;
			document.getElementById("empDtls" + i).value = followupDtls[i - 1].empDetails;
		}
	}

}

function isJsonResponce(ObjeResp) {
	var responseText = ObjeResp;
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			return true;
		}
	}
	return false;
}

function clearFormData(){
	document.getElementById("customerName").value = "";
	document.getElementById("email").value = "";
	document.getElementById("followupNote").value = "";
	document.getElementById('regionId').value = "";
	document.getElementById('cityId').value = "";
	document.getElementById('officeId').value = "";
	document.getElementById('employeeId').value = "";
	$('#mail').removeAttr('checked');
	$('#phone').removeAttr('checked');
	$('#customer').removeAttr('checked');
	$('#origin').removeAttr('checked');
	$('#callerBranch').removeAttr('checked');
}

function getAllOffices() {
	document.getElementById('officeId').value = "";
	var cityId = "";
	cityId = document.getElementById("cityId").value;
	if (!isNull(cityId)) {
		showProcessing();
		url = './serviceRequestFollowup.do?submitName=getAllOfficesByCity&cityId='
				+ cityId;
		 ajaxCallWithoutForm(url, printAllOffices);
	} else {
		createDropDown("officeId", "", "SELECT");
	}
}

function printAllOffices(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			showDropDownBySelected("cityId", "");
			createDropDown("officeId", "", "SELECT");
			alert(error);
			document.getElementById('cityId').focus();
		} else {
			var response = ajaxResp;
			if (!isNull(response)) {
				var content = document.getElementById('officeId');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				$.each(response, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.officeId;
					option
							.appendChild(document
									.createTextNode(this.officeName));
					content.appendChild(option);
				});
			}

		}
		hideProcessing();
	}
}


function getAllCities() {
	var regionId = "";
	createDropDown("cityId", "", "SELECT");
	createDropDown("officeId", "", "SELECT");
	regionId = document.getElementById("regionId").value;
	if (!isNull(regionId)) {
		showProcessing();
		url = './serviceRequestFollowup.do?submitName=getCitiesByRegion&regionId='
				+ regionId;
		ajaxCallWithoutForm(url, printAllCities);
	}
}

function printAllCities(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("regionId", "");
			alert(error);
			document.getElementById('regionId').focus();
		}

		else {
			var response = ajaxResp;
			if (!isNull(response)) {
				var content = document.getElementById('cityId');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				// createOnlyAllOptionWithValue('destCity', "0");
				$.each(response, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.cityId;
					option.appendChild(document.createTextNode(this.cityName));
					content.appendChild(option);
				});

			}
		}

		hideProcessing();

	}
}

function getAllEmployeeByOfficeAndRole() {
	var officeId = "";
	createDropDown("employeeId", "", "SELECT");
	officeId = document.getElementById("officeId").value;
	if (!isNull(officeId)) {
		showProcessing();
		url = './serviceRequestFollowup.do?submitName=getAllEmployeeByOfficeAndRole&officeId='
				+ officeId;
		ajaxCallWithoutForm(url, printAllEmployee);
	}
}

function printAllEmployee(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			document.getElementById('officeId').value = "";
			document.getElementById('officeId').focus();
		}

		else {
			var response = ajaxResp;
			if (!isNull(response)) {
				var content = document.getElementById('employeeId');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				// createOnlyAllOptionWithValue('destCity', "0");
				$.each(response, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.employeeId;
					option.appendChild(document.createTextNode(this.empCode  +"-" +this.firstName + " " + this.lastName));
					content.appendChild(option);
				});

			}
		}

		hideProcessing();

	}
}