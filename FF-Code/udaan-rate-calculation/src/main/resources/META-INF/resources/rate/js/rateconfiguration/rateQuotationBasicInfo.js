var searchFlag = false;
var aprvrLevel;
var aprvlReq;
var aprvdAt;
/*var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
*/
function getPincode(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './rateQuotation.do?submitName=getPincode&pincode=' + pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincodeDetails(req, pinObj);
			}
		});
	}
}

function printCallBackPincodeDetails(responseText, pinObj) {
	if (!isNull(responseText)) {
		var req = jsonJqueryParser(responseText);
		var error = req[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			pinObj.value = "";
			pinObj.focus();
			return false;
		} else {
			document.getElementById("PincodeId").value = req.pincodeId;
			document.getElementById("City").value = req.cityTO.cityName;
			document.getElementById("CityId").value = req.cityTO.cityId;
		}
	}

}

function saveOrUpdateBasicInfo() {

	if (validateBasicInfoFields()) {
		getDomElementById("saveBasicInfoBtn").disabled = true;
		var url = './rateQuotation.do?submitName=saveOrUpdateBasicInfo';
		ajaxCall1(url, "rateQuotationForm", printCallBackBasicInfo);
		showProcessing();
	}
}
/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallBackBasicInfo(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
		} else {
			var quotationTO = responseText;
			
			getDomElementById("QuotationNo").value = quotationTO.rateQuotationNo;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotationId;
			getDomElementById("hdnQuotationNo").value = quotationTO.rateQuotationNo;
			getDomElementById("customerNewId").value = quotationTO.customer.customerId;
			getDomElementById("contactId1").value = quotationTO.customer.primaryContact.contactId;
			getDomElementById("quotationStatus").value = quotationTO.status;
			if (!isNull(quotationTO.customer.secondaryContact.contactId)) {
				getDomElementById("contactId2").value = quotationTO.customer.secondaryContact.contactId;
			}
			hideProcessing();
			alert(quotationTO.transMsg);
			enableTabs(1);
		}
	}else{
		hideProcessing();
	}
	getDomElementById("saveBasicInfoBtn").disabled = false;
}

function searchQuotation() {
	document.getElementById("QuotationNo").focus();
	var QuotationNo = document.getElementById("QuotationNo").value;
	var rateQuotationId = null;
	var createdBy = getDomElementById("createdBy").value;
	var rateQuotationType = getDomElementById("rateQuotationType").value;
	var errorMsg = document.getElementById("errorMsg").value;
	if (isNull(errorMsg)) {
		if (!isNull(rateQuotationId) || !isNull(QuotationNo)) {

			showProcessing();

			url = './rateQuotation.do?submitName=searchQuotationDetails&QuotationNo='
					+ QuotationNo
					+ "&rateQuotationId="
					+ rateQuotationId
					+ "&rateQuotationType="
					+ rateQuotationType
					+ "&createdBy="
					+ createdBy;

			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBacsearchQuotation(req);
				}
			});
		}
	} else {
		disableAll();
	}
}

function printCallBacsearchQuotation(responseText) {
	hideProcessing();
	
	if (!isNull(responseText)) {
		var response = jsonJqueryParser(responseText);
		var error = response[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			getDomElementById("QuotationNo").value = "";
			getDomElementById("rateQuotationId").value = "";
			getDomElementById("hdnQuotationNo").value = "";
			getDomElementById("Address1").value = "";
			getDomElementById("Address2").value = "";
			getDomElementById("Address3").value = "";
			getDomElementById("CreatedDate").value = "";
			getDomElementById("CustomerName").value = "";
			getDomElementById("Pincode").value = "";
			getDomElementById("City").value = "";
			getDomElementById("quotationStatus").value = "";
			getDomElementById("approvalRequired").value = "";
			getDomElementById("approvedAt").value = "";
			getDomElementById("contractCreated").value = "";
			getDomElementById("quotationCreatedFrom").value = "O";
			getDomElementById("legacyCustomerCode").value = "";
			getDomElementById("customerNewId").value = "";
			if(!isNull(getDomElementById("proposedRatesP"))){
				getDomElementById("proposedRatesP").value = "N";
				getDomElementById("proposedRatesD").value = "N";
				getDomElementById("proposedRatesB").value = "N";
				getDomElementById("proposedRatesCO").value = "N";
			}
			if (salesType == "C") {
				getDomElementById("Station").value = "";
				getDomElementById("SalesOffice").value = "";
				getDomElementById("SalesPerson").value = "";

			}

			getDomElementById("excecutiveRemarks").value = "";
			getDomElementById("approversRemarks").value = "";
			getDomElementById("PincodeId").value = "";
			getDomElementById("CityId").value = "";
			getDomElementById("IndustryType").value = "";
			getDomElementById("GroupKey").value = "";
			if (!isNull(getDomElementById("IndustryCategory"))) {
				getDomElementById("IndustryCategory").value = "";
				getDomElementById("BusinessType").value = "";
			}

			getDomElementById("contactId1").value = "";
			getDomElementById("Title1").value = "";
			getDomElementById("AuthorizedPerson1").value = "";
			getDomElementById("Designation1").value = "";
			getDomElementById("Department1").value = "";
			getDomElementById("Email1").value = "";
			getDomElementById("Contact1").value = "";
			getDomElementById("Extn1").value = "";
			getDomElementById("Mobile1").value = "";
			getDomElementById("FAX1").value = "";

			if (getDomElementById("secondaryCheck").checked == true) {
				getDomElementById("contactId2").value = "";
				getDomElementById("Title2").value = "";
				getDomElementById("AuthorizedPerson2").value = "";
				getDomElementById("Designation2").value = "";
				getDomElementById("Department2").value = "";
				getDomElementById("Email2").value = "";
				getDomElementById("Contact2").value = "";
				getDomElementById("Extn2").value = "";
				getDomElementById("Mobile2").value = "";
				getDomElementById("FAX2").value = "";
			}
			$("#tabs").tabs({disabled: [1,2,3]});
		} else {
			var rateQuotationTO = response;
			
		getDomElementById("rateQuotationId").value = rateQuotationTO.rateQuotationId;
		getDomElementById("QuotationNo").value = rateQuotationTO.rateQuotationNo;
		getDomElementById("hdnQuotationNo").value = rateQuotationTO.rateQuotationNo;
		getDomElementById("Address1").value = rateQuotationTO.customer.address.address1;
		getDomElementById("Address2").value = rateQuotationTO.customer.address.address2;
		getDomElementById("Address3").value = rateQuotationTO.customer.address.address3;
		getDomElementById("CreatedDate").value = rateQuotationTO.createdDate;
		getDomElementById("CustomerName").value = rateQuotationTO.customer.businessName;
		getDomElementById("Pincode").value = rateQuotationTO.customer.address.pincode.pincode;
		getDomElementById("City").value = rateQuotationTO.customer.address.city.cityName;
		getDomElementById("quotationStatus").value = rateQuotationTO.status;
		getDomElementById("approvalRequired").value = rateQuotationTO.approvalRequired;
		getDomElementById("approvedAt").value = rateQuotationTO.approvedAt;
		getDomElementById("contractCreated").value = rateQuotationTO.contractCreated;
		getDomElementById("quotationCreatedFrom").value = rateQuotationTO.quotationCreatedFrom;
		if(!isNull(getDomElementById("proposedRatesP"))){
			getDomElementById("proposedRatesP").value = rateQuotationTO.proposedRatesP;
			getDomElementById("proposedRatesD").value = rateQuotationTO.proposedRatesD;
			getDomElementById("proposedRatesB").value = rateQuotationTO.proposedRatesB;
			getDomElementById("proposedRatesCO").value = rateQuotationTO.proposedRatesCO;
		}

		module = getDomElementById("module").value;
		if (salesType == "C" && module != "view") {
			if(!isNull(document.getElementById("rhoOfcId"))){
				clearDropDownList("Station");
				document.getElementById("rhoOfcId").value = rateQuotationTO.rhoOfcId;
				if(!isNull(rateQuotationTO.cityTOList)){					
					var cityList = rateQuotationTO.cityTOList;
					for(var i=0;i<cityList.length;i++){
						addOptionTODropDown("Station",
							cityList[i].cityName,cityList[i].cityId);
					}
				}
				
			}
			getDomElementById("Station").value = rateQuotationTO.customer.salesOffice.cityId;
			
			clearDropDownList("SalesOffice");
			addOptionTODropDown("SalesOffice",
					rateQuotationTO.customer.salesOffice.officeName,
					rateQuotationTO.customer.salesOffice.officeId);

			getDomElementById("SalesOffice").value = rateQuotationTO.customer.salesOffice.officeId;

			clearDropDownList("SalesPerson");
			addOptionTODropDown("SalesPerson",
					rateQuotationTO.customer.salesPerson.firstName + " "
							+ rateQuotationTO.customer.salesPerson.lastName,
					rateQuotationTO.customer.salesPerson.employeeId);

			getDomElementById("SalesPerson").value = rateQuotationTO.customer.salesPerson.employeeId;

		} else if (module == "view") {
			getDomElementById("SalesPerson").value = rateQuotationTO.customer.salesPerson.firstName
					+ "-" + rateQuotationTO.customer.salesPerson.lastName;
		}

		if (!isNull(rateQuotationTO.excecutiveRemarks)) {
			getDomElementById("excecutiveRemarks").value = rateQuotationTO.excecutiveRemarks;
		}
		if (!isNull(rateQuotationTO.approversRemarks)) {
			getDomElementById("approversRemarks").value = rateQuotationTO.approversRemarks;
		}

		getDomElementById("PincodeId").value = rateQuotationTO.customer.address.pincode.pincodeId;
		getDomElementById("CityId").value = rateQuotationTO.customer.address.city.cityId;
		getDomElementById("legacyCustomerCode").value = rateQuotationTO.customer.legacyCustomerCode;
		getDomElementById("customerNewId").value = rateQuotationTO.customer.customerId;
		getDomElementById("IndustryType").value = rateQuotationTO.customer.industryType;
		getDomElementById("GroupKey").value = rateQuotationTO.customer.groupKey.customerGroupId;
		if (!isNull(getDomElementById("IndustryCategory"))) {
			getDomElementById("IndustryCategory").value = rateQuotationTO.customer.industryCategory;
			var IndustryCategory = getDomElementById("IndustryCategory").value;
			var indCatGeneral = jQuery("#indCatGeneral").val();
			if (IndustryCategory.split("~")[0] == indCatGeneral) {
				document.getElementById("BusinessType").disabled = false;
			}else{
				document.getElementById("BusinessType").disabled = true;
			}

			getDomElementById("BusinessType").value = rateQuotationTO.customer.businessType;
		}
		if (!isNull(rateQuotationTO.customer.primaryContact)) {
			getDomElementById("contactId1").value = rateQuotationTO.customer.primaryContact.contactId;
			getDomElementById("Title1").value = rateQuotationTO.customer.primaryContact.title;
			getDomElementById("AuthorizedPerson1").value = rateQuotationTO.customer.primaryContact.name;
			getDomElementById("Designation1").value = rateQuotationTO.customer.primaryContact.designation;
			getDomElementById("Department1").value = rateQuotationTO.customer.primaryContact.department;
			getDomElementById("Email1").value = rateQuotationTO.customer.primaryContact.email;
			getDomElementById("Contact1").value = rateQuotationTO.customer.primaryContact.contactNo;
			getDomElementById("Extn1").value = rateQuotationTO.customer.primaryContact.extension;
			getDomElementById("Mobile1").value = rateQuotationTO.customer.primaryContact.mobile;
			getDomElementById("FAX1").value = rateQuotationTO.customer.primaryContact.fax;
		}

		if (!isNull(rateQuotationTO.customer.secondaryContact.contactId)) {
			getDomElementById("secondaryCheck").checked = true;
			getDomElementById("contactId2").value = rateQuotationTO.customer.secondaryContact.contactId;
			getDomElementById("Title2").value = rateQuotationTO.customer.secondaryContact.title;
			getDomElementById("AuthorizedPerson2").value = rateQuotationTO.customer.secondaryContact.name;
			getDomElementById("Designation2").value = rateQuotationTO.customer.secondaryContact.designation;
			getDomElementById("Department2").value = rateQuotationTO.customer.secondaryContact.department;
			getDomElementById("Email2").value = rateQuotationTO.customer.secondaryContact.email;
			getDomElementById("Contact2").value = rateQuotationTO.customer.secondaryContact.contactNo;
			getDomElementById("Extn2").value = rateQuotationTO.customer.secondaryContact.extension;
			getDomElementById("Mobile2").value = rateQuotationTO.customer.secondaryContact.mobile;
			getDomElementById("FAX2").value = rateQuotationTO.customer.secondaryContact.fax;
			getDomElementById("Title2").disabled = false;
			getDomElementById("AuthorizedPerson2").disabled = false;
			getDomElementById("Designation2").disabled = false;
			getDomElementById("Department2").disabled = false;
			getDomElementById("Email2").disabled = false;
			getDomElementById("Contact2").disabled = false;
			getDomElementById("Extn2").disabled = false;
			getDomElementById("Mobile2").disabled = false;
			getDomElementById("FAX2").disabled = false;
			
		}
		aprvrLevel = getValueByElementId("approverLevel");
		aprvlReq = getValueByElementId("approvalRequired");
		aprvdAt = getValueByElementId("approvedAt");

		enableTabs(1);
		if (rateQuotationTO.proposedRates == true) {
			enableTabs(2);
			if (rateQuotationTO.fixedChrgs == true)
				enableTabs(3);
			else
				$("#tabs").tabs({disabled: [3]});
		}else{
			$("#tabs").tabs({disabled: [2,3]});
		}

		var status = getDomElementById("quotationStatus").value;
		if (!isNull(status) && status != 'N') {
			disableBasicInfoFields();
			getDomElementById("QuotationNo").disabled = false;
		}
	} 
}
}
function changeBusinessType(indCatObj) {
	var indCatVal = indCatObj.value;
	var indCatGeneral= jQuery("#indCatGeneral").val();
	if (indCatVal.split("~")[0] == indCatGeneral) {
		document.getElementById("BusinessType").disabled = false;
	} else {
		document.getElementById("BusinessType").disabled = true;
		document.getElementById("BusinessType").value = "";
	}

}

function getAllEmployees() {
	var salesOffice = getDomElementById("SalesOffice").value;
	showProcessing();
	url = './rateQuotation.do?submitName=getAllEmployees&salesOffice='
			+ salesOffice;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printAllEmployees(req);
		}
	});
}

function printAllEmployees(responseText) {
	hideProcessing();
	
	if (!isNull(responseText)) {
		var response = jsonJqueryParser(responseText);
		var error = response[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var content = document.getElementById('SalesPerson');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(response, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.employeeId;
				option.appendChild(document.createTextNode(this.firstName + " "
						+ this.lastName));
				content.appendChild(option);
			});

		}

	}

}

function getAlloffices() {

	var Station = getDomElementById("Station").value;
	showProcessing();
	url = './rateQuotation.do?submitName=getAlloffices&Station=' + Station;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printAllOffices(req);
		}
	});
}

function printAllOffices(responseText) {
	hideProcessing();
	if (!isNull(responseText)) {
		
		var response = jsonJqueryParser(responseText);
		var error = response[ERROR_FLAG];
		if (responseText != null && error != null) {
		alert(error);
		} else {
				var content = document.getElementById('SalesOffice');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(response, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
			});

		}

	}

}

function validateBasicInfoFields() {

	var isValid = true;

	
	var Station = getDomElementById("Station").value;
	var SalesOffice = getDomElementById("SalesOffice").value;
	var SalesPerson = getDomElementById("SalesPerson").value;
	
	
	var IndustryType = getDomElementById("IndustryType").value;
	var GroupKey = getDomElementById("GroupKey").value;
	var CustomerName = getDomElementById("CustomerName").value;
	var Address = getDomElementById("Address1").value;
	var address2 = getDomElementById("Address2").value;
	var address3 = getDomElementById("Address3").value;
	var Pincode = getDomElementById("Pincode").value;
	var City = getDomElementById("City").value;
	var Title1 = getDomElementById("Title1").value;
	var AuthorizedPerson1 = getDomElementById("AuthorizedPerson1").value;
	var Designation1 = getDomElementById("Designation1").value;
	var Department1 = getDomElementById("Department1").value;
	var Email1 = getDomElementById("Email1").value;
	var Contact1 = getDomElementById("Contact1").value;
	var Title2 = getDomElementById("Title2").value;
	var AuthorizedPerson2 = getDomElementById("AuthorizedPerson2").value;
	var Designation2 = getDomElementById("Designation2").value;
	var Department2 = getDomElementById("Department2").value;
	var Email2 = getDomElementById("Email2").value;
	var Contact2 = getDomElementById("Contact2").value;
	var rateQuotationType = getDomElementById("rateQuotationType").value;

	var empOfcType = getDomElementById("empOfcType").value;
	if (isNull(empOfcType) && !isNull(getDomElementById("rhoOfcId"))) {
		var region = getDomElementById("rhoOfcId").value;
		if (isNull(region)) {
			
			alert("Please select the Region.");
			setTimeout(function() {
				document.getElementById("Region").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	if (isNull(Station)) {
		alert("Please select the Station");
		setTimeout(function() {
			document.getElementById("Station").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(SalesOffice)) {
		alert("Please select the Sales Office");
		setTimeout(function() {
			document.getElementById("SalesOffice").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(SalesPerson)) {
		alert("Please select the Sales Person");
		setTimeout(function() {
			document.getElementById("SalesPerson").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if(rateQuotationType=="N" && !isNull(getDomElementById("IndustryCategory"))){
		var IndustryCategory = getDomElementById("IndustryCategory").value;
		var BusinessType = getDomElementById("BusinessType").value;
	if (isNull(IndustryCategory)) {
		alert("Please select the Industry Category");
		setTimeout(function() {
			document.getElementById("IndustryCategory").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	var indCatGeneral= jQuery("#indCatGeneral").val();
	if (IndustryCategory.split("~")[0] == indCatGeneral) {
	if (isNull(BusinessType)) {
		alert("Please select the Business Type");
		setTimeout(function() {
			document.getElementById("BusinessType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	}
	}
	if (isNull(IndustryType)) {
		alert("Please select the Industry Type");
		setTimeout(function() {
			document.getElementById("IndustryType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(GroupKey)) {
		alert("Please select the Group Key");
		setTimeout(function() {
			document.getElementById("GroupKey").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (CustomerName == null || CustomerName == "") {
		alert("Please enter the Customer Name.");
		setTimeout(function() {
			document.getElementById("CustomerName").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	
	

	if (Address == null || Address == "") {
		alert("Please enter the Address Line 1.");
		setTimeout(function() {
			document.getElementById("Address1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (address2 == null || address2 == "") {
		alert("Please enter the Address Line 1.");
		setTimeout(function() {
			document.getElementById("Address2").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (address3 == null || address3 == "") {
		alert("Please enter the Address Line 3.");
		setTimeout(function() {
			document.getElementById("Address3").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (Pincode == null || Pincode == "") {
		alert("Please enter the Pincode.");
		setTimeout(function() {
			document.getElementById("Pincode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (City == null || City == "") {
		alert("Please enter the City.");
		setTimeout(function() {
			document.getElementById("City").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (Title1 == null || Title1 == "") {
		alert("Please enter the Title for Primary contact.");
		setTimeout(function() {
			document.getElementById("Title1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (AuthorizedPerson1 == null || AuthorizedPerson1 == "") {
		alert("Please enter the Authorized Person for Primary contact.");
		setTimeout(function() {
			document.getElementById("AuthorizedPerson1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (Designation1 == null || Designation1 == "") {
		alert("Please enter the Designation for Primary contact.");
		setTimeout(function() {
			document.getElementById("Designation1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(Department1)) {
		alert("Please select the Department for Primary contact.");
		setTimeout(function() {
			document.getElementById("Department1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (Email1 == null || Email1 == "") {
		alert("Please enter the Email for Primary contact.");
		setTimeout(function() {
			document.getElementById("Email1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (Contact1 == null || Contact1 == "") {
		alert("Please enter the Contact No for Primary contact.");
		setTimeout(function() {
			document.getElementById("Contact1").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (document.getElementById("secondaryCheck").checked) {
		if (Title2 == null || Title2 == "") {
			alert("Please enter the Title for secondary contact.");
			setTimeout(function() {
				document.getElementById("Title1").focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (AuthorizedPerson2 == null || AuthorizedPerson2 == "") {
			alert("Please enter the Authorized Person for secondary contact.");
			setTimeout(function() {
				document.getElementById("AuthorizedPerson2").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
		if (Designation2 == null || Designation2 == "") {
			alert("Please enter the Designation for secondary contact.");
			setTimeout(function() {
				document.getElementById("Designation2").focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (isNull(Department2)) {
			alert("Please select the Department for secondary contact.");
			setTimeout(function() {
				document.getElementById("Department2").focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (Email2 == null || Email2 == "") {
			alert("Please enter the Email for secondary contact.");
			setTimeout(function() {
				document.getElementById("Email2").focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (Contact2 == null || Contact2 == "") {
			alert("Please enter the Contact No for secondary contact.");
			setTimeout(function() {
				document.getElementById("Contact2").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	return isValid;
}

function enableSecondary(chkObj) {
	if (chkObj.checked) {
	
		getDomElementById("Title2").disabled = false;
		getDomElementById("Title2").focus();
		getDomElementById("AuthorizedPerson2").disabled = false;
		getDomElementById("Designation2").disabled = false;
		getDomElementById("Department2").disabled = false;
		getDomElementById("Email2").disabled = false;
		getDomElementById("Contact2").disabled = false;
		getDomElementById("Extn2").disabled = false;
		getDomElementById("Mobile2").disabled = false;
		getDomElementById("FAX2").disabled = false;
	} else {
		getDomElementById("contactId2").value = "";
		getDomElementById("Title2").value = "";
		getDomElementById("AuthorizedPerson2").value = "";
		getDomElementById("Designation2").value = "";
		getDomElementById("Department2").value = "";
		getDomElementById("Email2").value = "";
		getDomElementById("Contact2").value = "";
		getDomElementById("Extn2").value = "";
		getDomElementById("Mobile2").value = "";
		getDomElementById("FAX2").value = "";

		getDomElementById("Title2").disabled = true;
		getDomElementById("AuthorizedPerson2").disabled = true;
		getDomElementById("Designation2").disabled = true;
		getDomElementById("Department2").disabled = true;
		getDomElementById("Email2").disabled = true;
		getDomElementById("Contact2").disabled = true;
		getDomElementById("Extn2").disabled = true;
		getDomElementById("Mobile2").disabled = true;
		getDomElementById("FAX2").disabled = true;

	}
}

function clearBasicInfo() {
	flag = confirm("Do you want to Clear the Details");
	if (!flag) {
		return;
	} else {
		searchQuotation();
	}
}

function cancelNormalQuotationInfo() {
	flag = confirm("Do you want to Cancel the Details");
	if (!flag) {
		return;
	} else {
		document.rateQuotationForm.action = "./rateQuotation.do?submitName=viewRateQuotation&sales="+document.getElementById("salesType").value;
		document.rateQuotationForm.submit();
	}
}


function copyQuotation(){
	jQuery("#" + "copyBtn").attr("disabled", false);
	jQuery("#" + "copyBtn").removeClass("btnintformbigdis");
	jQuery("#" + "copyBtn").addClass("btnintform");
	
	var QuotationNo = document.getElementById("QuotationNo").value;
	var loginOfficeCode = document.getElementById("loginOfficeCode").value;
	var createdBy = document.getElementById("createdBy").value;
	var updatedBy = document.getElementById("updatedBy").value;
	var rateQuotationType=getDomElementById("rateQuotationType").value;

	
	if(!isNull(QuotationNo)){
		showProcessing();
		url = './rateQuotation.do?submitName=copyQuotation&QuotationNo='
			+ QuotationNo + '&loginOfficeCode=' + loginOfficeCode + "&rateQuotationType=" + rateQuotationType + "&createdBy=" + createdBy+ "&updatedBy=" + updatedBy;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallCopyQuotation(req);
			}
		});
	}
}

function printCallCopyQuotation(ajaxResp) {
	hideProcessing();
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var quotationTO = responseText;
			var rateQuotationTO = responseText;
			//getDomElementById("rateQuotationId").value = rateQuotationTO.rateQuotationId;
			//getDomElementById("QuotationNo").value = rateQuotationTO.rateQuotationNo;
			alert(quotationTO.transMsg+"\n New Quotation Number: "+ rateQuotationTO.rateQuotationNo);
			//document.getElementById("createdBy").value = rateQuotationTO.createdBy;
			//document.getElementById("updatedBy").value = rateQuotationTO.updatedBy;
			//alert("Quotation Copied Successfully");
			//searchQuotation();
			//$("#tabs").tabs("option", "active", 0);
		}

	}
}
/* @Desc:For Clearing the dropdown */
/*function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");

}*/

/* @Desc:For Adding option to the dropdown */
/*function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}*/



function closeWindow(){
	window.close();
}

function disableBasicInfoFieldsOnLoad(){
	disableBasicInfoFields();
	buttonDisabled("Find","btnintform");
	jQuery("#" +"Find").addClass("btnintformbigdis");
	
}

function disableBasicInfoFields(){
	
	
	
			if(!isNull(document.getElementById("Region"))){
				document.getElementById("Region").disabled = true;
			}
			if(!isNull(document.getElementById("rhoOfcId"))){
				document.getElementById("rhoOfcId").disabled = true;
			}
			document.getElementById("Station").disabled = true;
			document.getElementById("SalesOffice").disabled = true;
			document.getElementById("SalesPerson").disabled = true;
			if(!isNull(getDomElementById("IndustryCategory")))
			document.getElementById("IndustryCategory").disabled = true;
			if(!isNull(getDomElementById("BusinessType")))
			document.getElementById("BusinessType").disabled = true;
			document.getElementById("IndustryType").disabled = true;
			document.getElementById("GroupKey").disabled = true;
			document.getElementById("CustomerName").disabled = true;
			document.getElementById("Address1").disabled = true;
			document.getElementById("Address2").disabled = true;
			document.getElementById("Address3").disabled = true;
			document.getElementById("Pincode").disabled = true;
			document.getElementById("Title1").disabled = true;
			document.getElementById("AuthorizedPerson1").disabled = true;
			document.getElementById("Designation1").disabled = true;
			document.getElementById("Department1").disabled = true;
			document.getElementById("Email1").disabled = true;
			document.getElementById("Contact1").disabled = true;
			document.getElementById("Extn1").disabled = true;
			document.getElementById("Mobile1").disabled = true;
			document.getElementById("FAX1").disabled = true;
			document.getElementById("secondaryCheck").disabled = true;
			document.getElementById("Title2").disabled = true;
			document.getElementById("AuthorizedPerson2").disabled = true;
			document.getElementById("Designation2").disabled = true;
			document.getElementById("Department2").disabled = true;
			document.getElementById("Email2").disabled = true;
			document.getElementById("Contact2").disabled = true;
			document.getElementById("Extn2").disabled = true;
			document.getElementById("Mobile2").disabled = true;
			document.getElementById("FAX2").disabled = true;
			
			buttonDisabled("saveBasicInfoBtn","btnintform");
			jQuery("#" +"saveBasicInfoBtn").addClass("btnintformbigdis");
			
			buttonDisabled("clearBasicInfoBtn","btnintform");
			jQuery("#" +"clearBasicInfoBtn").addClass("btnintformbigdis");
	
	//jQuery("#tabs-1").d

	//jQuery(":input").attr("readonly", true);
	//disableAll();
	
	}

function validateEmailAddress(addrObj) {
	var addr=addrObj.value;
	
	if(!isNull(addr)){
	var invalidChars = '\/\'\\ ";:?!()[]\{\}^|';
	for (var i=0; i<invalidChars.length; i++) {
	   if (addr.indexOf(invalidChars.charAt(i),0) > -1) {
	       alert('email address contains invalid characters');
	       addrObj.value="";
	       setTimeout(function() {
			   addrObj.focus();
			}, 10);
	      return false;
	   }
	}
	for (var i=0; i<addr.length; i++) {
	   if (addr.charCodeAt(i)>127) {
	       alert("email address contains non ascii characters.");
	       addrObj.value="";
	       setTimeout(function() {
			   addrObj.focus();
			}, 10);
	      return false;
	   }
	}

	var atPos = addr.indexOf('@',0);
	if (atPos == -1) {
	    alert('email address must contain an @');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (atPos == 0) {
	    alert('email address must not start with @');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (addr.indexOf('@', atPos + 1) > - 1) {
	    alert('email address must contain only one @');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (addr.indexOf('.', atPos) == -1) {
	    alert('email address must contain a period in the domain name');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (addr.indexOf('@.',0) != -1) {
	    alert('period must not immediately follow @ in email address');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (addr.indexOf('.@',0) != -1){
	    alert('period must not immediately precede @ in email address');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	if (addr.indexOf('..',0) != -1) {
	    alert('two periods must not be adjacent in email address');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	var suffix = addr.substring(addr.lastIndexOf('.')+1);
	suffix=suffix.toLowerCase();
	if (suffix.length != 2 && suffix != 'com' && suffix != 'net' && suffix != 'org' && suffix != 'edu' && suffix != 'int' && suffix != 'mil' && suffix != 'gov' & suffix != 'arpa' && suffix != 'biz' && suffix != 'aero' && suffix != 'name' && suffix != 'coop' && suffix != 'info' && suffix != 'pro' && suffix != 'museum') {
	    alert('invalid primary domain in email address');
	    addrObj.value="";
	    setTimeout(function() {
			   addrObj.focus();
			}, 10);
	   return false;
	}
	}
	return true;
}

function isValidMobile(obj) {
	var mobile = obj.value;
	if (!isNull(mobile)) {
		if (mobile.length != 10) {
			alert("Enter 10 Digits Mobile Number");
			obj.value="";
			setTimeout(function() {
				obj.focus();
			}, 10);
		
			isValid = false;
			return isValid;

		}
	}
}

function nonAlphabet(e) {

	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90)) {
		return false;
	} else {
		return true;
	}
	if (charCode == 46 || charCode == 32 || charCode == 9 || charCode == 8
			|| charCode == 127  
			|| charCode == 0) {
		return true;
	} else {
		return false;
	}

}


function setOctroiChargeValue() {
	qNo = getDomElementById("rateQuotationId").value;
	if(!isNull(qNo)){
	var riskSurcharges = getDomElementById("OctroiBornBy").value;
	url = './rateQuotation.do?submitName=getOctroiChargeValue&riskSurcharges='
			+ riskSurcharges;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printCallBackOctroiCharge(req);
		}
	});
	}
}

function printCallBackOctroiCharge(responseText) {
	if (!isNull(responseText)) {
		var quotationTO = jsonJqueryParser(responseText);
		var error = quotationTO[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			getDomElementById("OctroiServiceCharges").value = quotationTO.serviceCharge;
		}
	}
}

function validatePinKey(event){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = event.which; 
	
	if(charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0)
		return true;
	else return onlyNumeric(event);	
}

function getAllCities(){

	var rhoOfcId = getDomElementById("rhoOfcId").value;
	showProcessing();
	url = './rateQuotation.do?submitName=getAllCities&rhoOfcId=' + rhoOfcId;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printAllCities(req);
		}
	});
}

function printAllCities(responseText){
	hideProcessing();
	if (!isNull(responseText)) {
		
		var response = jsonJqueryParser(responseText);
		var error = response[ERROR_FLAG];
		if (responseText != null && error != null) {
		alert(error);
		} else {
				var content = document.getElementById('Station');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(response, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});

		}

	}
}