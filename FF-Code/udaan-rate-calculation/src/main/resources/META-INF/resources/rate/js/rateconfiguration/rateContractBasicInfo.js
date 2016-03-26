var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var salesOfcTypeCode = "";
function createContract() {
	var QuotationNo = document.getElementById("QuotationNo").value;
	var loginOfficeCode = document.getElementById("loginOfficeCode").value;
	var createdBy = document.getElementById("createdBy").value;
	var updatedBy = document.getElementById("updatedBy").value;
	var rateQuotationType=getDomElementById("rateQuotationType").value;



	if (!isNull(QuotationNo)) {
		disableCreateContractBtn();
		url = './rateContract.do?submitName=createContract&QuotationNo='
				+ QuotationNo + '&loginOfficeCode=' + loginOfficeCode + "&rateQuotationType=" + rateQuotationType
				+ "&createdBy=" + createdBy+ "&updatedBy=" + updatedBy;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallcreateContract(req);
			}
		});
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });		
	}
}

function printCallcreateContract(data) {
	jQuery.unblockUI();
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var rateContractId = responseText.rateContractId;
			var rateContractNo = responseText.rateContractNo;
			var rateQuotationId = responseText.rateQuotationTO.rateQuotationId;
			var rateQuotationNo = responseText.rateQuotationTO.rateQuotationNo;
			var url = './rateContract.do?submitName=viewRateContractNormal&rateContractNo='
					+ rateContractNo
					+ "&rateContractId="
					+ rateContractId
					+ "&rateQuotationId="
					+ rateQuotationId
					+ "&rateQuotationNo="
					+ rateQuotationNo;
			window.location.assign(url);
		}

	}
}

function searchContractDetails() {
	document.getElementById("rateContractNo").focus();
	var rateContractNo = document.getElementById("rateContractNo").value;
	//var rateContractId = getDomElementById("rateContractId").value;
	var rateContractId = "";
	var createdBy = getDomElementById("createdBy").value;
	var rateContractType = getDomElementById("rateContractType").value;
	var usrType = getDomElementById("userType").value;
	billingSearch = false;
	
	if (!isNull(rateContractNo)) {
		
		if(getDomElementById("rateQuotationType").value == "N"){
			$("#tabs").tabs({disabled: [1,2,3,4,5,6,7,8]});
		}else{
			$("#tabs").tabs({disabled: [1,2,3,4,5,6,7]});
		}
		showProcessing();

		var url = './rateContract.do?submitName=searchContractDetails&rateContractNo='
				+ rateContractNo
				+ "&rateContractId="
				+ rateContractId
				+ "&rateContractType="
				+ rateContractType
				+ "&createdBy=" 
				+ createdBy
				+ "&userType=" 
				+ usrType;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallContractDetails(req);
			}
		});
	}
}

function printCallContractDetails(ajaxResp) {
	hideProcessing();
	salesOfcTypeCode = "";
	var userType = getDomElementById("userType").value;
	getDomElementById("rateContractId").value = "";
		if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
			var error = responseText[ERROR_FLAG];
			if (responseText != null && error != null) {
				alert(error);
				getDomElementById("rateContractNo").value = "";
				getDomElementById("contractStatus").value = "";
				getDomElementById("isNew").value = "";
				getDomElementById("rnewContract").value = "";
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
				jQuery("#validFromDate").val("");
				jQuery("#sysFromDate").val("");		
				jQuery("#validToDate").val("");
				if(!isNull(getDomElementById("proposedRatesP"))){
					getDomElementById("proposedRatesP").value = "N";
					getDomElementById("proposedRatesD").value = "N";
					getDomElementById("proposedRatesB").value = "N";
					getDomElementById("proposedRatesCO").value = "N";
				}
				
				if(userType != 'S'){
					getDomElementById("Station").value = "";
					getDomElementById("SalesOffice").value = "";
					getDomElementById("SalesPerson").value = "";
				}else{
					clearDropDownList("Station"); 
					clearDropDownList("SalesOffice"); 
					clearDropDownList("SalesPerson"); 
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
				getDomElementById("originatedRateContractId").value = "";
				getDomElementById("oldContractExpDate").value = "";
				getDomElementById("isRenewed").value = "";
			} else {
				var contractTO = responseText;
				var rateQuotationTO = contractTO.rateQuotationTO;
				
				getDomElementById("Region").value = rateQuotationTO.rhoOfcName;
				getDomElementById("rateContractId").value = contractTO.rateContractId;
				getDomElementById("rateContractNo").value = contractTO.rateContractNo;
				getDomElementById("CreatedDate").value = contractTO.createdDate;
				getDomElementById("contractStatus").value = contractTO.contractStatus;
				getDomElementById("isNew").value = contractTO.isNew;
				getDomElementById("rnewContract").value = contractTO.renewContract;
				getDomElementById("rateQuotationId").value = rateQuotationTO.rateQuotationId;
				getDomElementById("rateQuotationNo").value = rateQuotationTO.rateQuotationNo;
				getDomElementById("customerPanNo").value = rateQuotationTO.customer.panNo;
				getDomElementById("customerTanNo").value = rateQuotationTO.customer.tanNo;
				getDomElementById("customerCode").value = rateQuotationTO.customer.customerCode;
				
				jQuery("#validFromDate").val(contractTO.validFromDate);
				jQuery("#sysFromDate").val(contractTO.validFromDate);		
				jQuery("#validToDate").val(contractTO.validToDate);

				getDomElementById("Address1").value = rateQuotationTO.customer.address.address1;
				getDomElementById("Address2").value = rateQuotationTO.customer.address.address2;
				getDomElementById("Address3").value = rateQuotationTO.customer.address.address3;

				getDomElementById("CustomerName").value = rateQuotationTO.customer.businessName;
				getDomElementById("Pincode").value = rateQuotationTO.customer.address.pincode.pincode;
				getDomElementById("City").value = rateQuotationTO.customer.address.city.cityName;
				getDomElementById("rateQuotationType").value = rateQuotationTO.rateQuotationType;

				getDomElementById("customerNewId").value = rateQuotationTO.customer.customerId;
				getDomElementById("contactId1").value = rateQuotationTO.customer.primaryContact.contactId;
				getDomElementById("contactId2").value = rateQuotationTO.rateQuotationType;
				getDomElementById("CityId").value = rateQuotationTO.customer.address.city.cityId;
				getDomElementById("PincodeId").value = rateQuotationTO.customer.address.pincode.pincodeId;
				if(userType != 'S'){
					//getDomElementById("SalesOffice").value = rateQuotationTO.customer.salesOffice.officeId;
					//getDomElementById("SalesPerson").value = rateQuotationTO.customer.salesPerson.employeeId;
					getDomElementById("SalesOffice").value = rateQuotationTO.customer.salesOffice.officeName;
					getDomElementById("SalesPerson").value = rateQuotationTO.customer.salesPerson.firstName
							+ " " + rateQuotationTO.customer.salesPerson.lastName;
					getDomElementById("Station").value = rateQuotationTO.customer.salesOffice.cityName;
				}else{
					if(!isNull(contractTO.salesCityList)){
						loadSalesCity("Station",rateQuotationTO.customer.salesOffice.cityId,contractTO.salesCityList); 
					}
					if(!isNull(contractTO.salesOfcList)){
						loadSalesOfc("SalesOffice",rateQuotationTO.customer.salesOffice.officeId,contractTO.salesOfcList); 
					}
					if(!isNull(contractTO.salesPersonList)){
						loadSalesPerson("SalesPerson",rateQuotationTO.customer.salesPerson.employeeId,contractTO.salesPersonList); 
					}
					
					salesOfcTypeCode = rateQuotationTO.customer.salesOffice.officeTypeTO.offcTypeCode;
					
					if(salesOfcTypeCode == 'CO'){
						getDomElementById("SalesOffice").disabled = true;
						getDomElementById("Station").disabled = true;
					}else{
						getDomElementById("SalesOffice").disabled = false;
						getDomElementById("Station").disabled = false;
					}
				}
				getDomElementById("quotationStatus").value = rateQuotationTO.status;
				getDomElementById("quotationCreatedFrom").value = rateQuotationTO.quotationCreatedFrom;
				getDomElementById("legacyCustomerCode").value = rateQuotationTO.customer.legacyCustomerCode;
	
				if(!isNull(getDomElementById("proposedRatesP"))){
					getDomElementById("proposedRatesP").value = rateQuotationTO.proposedRatesP;
					getDomElementById("proposedRatesD").value = rateQuotationTO.proposedRatesD;
					getDomElementById("proposedRatesB").value = rateQuotationTO.proposedRatesB;
					getDomElementById("proposedRatesCO").value = rateQuotationTO.proposedRatesCO;
				}

				if (!isNull(rateQuotationTO.excecutiveRemarks)) {
					getDomElementById("excecutiveRemarks").value = rateQuotationTO.excecutiveRemarks;
				}
				if (!isNull(rateQuotationTO.approversRemarks)) {
					getDomElementById("approversRemarks").value = rateQuotationTO.approversRemarks;
				}

				getDomElementById("PincodeId").value = rateQuotationTO.customer.address.pincode.pincodeId;
				getDomElementById("CityId").value = rateQuotationTO.customer.address.city.cityId;

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
					
				if (!isNull(rateQuotationTO.customer.secondaryContact) && !isNull(rateQuotationTO.customer.secondaryContact.contactId)) {
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
				}

				jQuery("#originatedRateContractId").val(contractTO.originatedRateContractId);
				jQuery("#oldContractExpDate").val(contractTO.oldContractExpDate);
				jQuery("#isRenewed").val(contractTO.isRenewed);

				enableTabs(1);
				enableTabs(2);
				enableTabs(3);
				enableTabs(4);
				if (contractTO.billingSaved == "Y") {
					if (contractTO.billingContractType == "N"){
						enableTabs(5);
					}else{
						enableTabs(6);
					}
				}
				if (contractTO.pkpupSaved == "Y") {
					if(getDomElementById("rateQuotationType").value == "E"){
						enableTabs(6);
					}else{
						enableTabs(7);
					}
				}
				
				if (contractTO.dlvSaved == "Y") {
					enableTabs(7);
				}
				
				if (contractTO.ffContactsSaved == "Y") {
					if(getDomElementById("rateQuotationType").value == "E"){
						enableTabs(7);
					}else{
						enableTabs(8);
					}
				}
				
				if (userType == 'S') {

					buttonEnabled("saveBasicInfoBtn", "btnintformbigdis");
					buttonEnabled("clearBasicInfoBtn", "btnintformbigdis");
					buttonEnabled("saveFiexdChrgsBtn", "btnintformbigdis");
					buttonEnabled("clearFiexdChrgsBtn", "btnintformbigdis");
					buttonEnabled("saveRTOBtn", "btnintformbigdis");
					buttonEnabled("clearRTOBtn", "btnintformbigdis");
					buttonEnabled("btnProposedRatesSave" + prodCatId, "btnintformbigdis");
					buttonEnabled("btnProposedRatesClear" + prodCatId, "btnintformbigdis");
					buttonEnabled("btnAdd" + prodCatId, "btnintformbigdis");
				}

				var status = contractTO.contractStatus;
				getDomElementById("rateQuotationNo").disabled = true;
				getDomElementById("CreatedDate").disabled = true;

				if (!isNull(status)) {
					if (status == "C") {
						disableBasicInfoFields();						
						var isNew = getDomElementById("isNew").value;
						if (isNew == true || isNew == "true") {
							enableToEditFields();
							buttonEnabled("saveBasicInfoBtn", "btnintformbigdis");
							buttonEnabled("clearBasicInfoBtn", "btnintformbigdis");
						}
					} else {
						disableContactBasicInfoFields(status, userType);
					}
				}
				getDomElementById("rateContractNo").readOnly = true;
				//rateBillingDtlsStartup();
			}
		}
	}}
function disableContactBasicInfoFields(status,userType) {

	if (status == "S" && isNull(userType)) {
		disableBasicInfoFields();
		jQuery("input.checkbox").attr("disabled", true);
	}else if ((status == "A" && isNull(userType))) {
		disableBasicInfoFields();
		jQuery("input.checkbox").attr("disabled", true);
	}else if (status == "I" && isNull(userType)) {
		disableBasicInfoFields();
		jQuery("input.checkbox").attr("disabled", true);
	}else if ((status == "S" || status == "A" || status == "I") && userType == 'S') {
		disableContactCustomerInfoFields();
	}

}
/**
 * To disable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintformbigdis");
}

/**
 * To enable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintform");
}

function submitContract() {
	var rateContractId = getDomElementById("rateContractId").value;
	//var userType = getDomElementById("userType").value;
	if (!isNull(rateContractId) && checkSpocFieldsData()) {

		//var billingType = getDomElementById("typeOfBilling").value;
		//if( billingType != "DBCP" || (billingType == "DBCP" && isValidSoldToCode() && isValidShipToCode())){
		//if(validateEffectiveDates() && renewValidDate()){
		//if((!isNull(userType) && userType == 'S') || (isNull(userType) && renewValidDate())){
		
		setContactType();
		url = './rateContract.do?submitName=submitContract';
		ajaxCall1(url,"rateContractForm",submitContractCallback);
		showProcessing();
		/*}else{
			return false;
		}
		}else{
			return false;
		}*/
	}
}

function submitContractCallback(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else if (!isNull(success)) {
			configureStatus();
			configureButton();
			alert(success);
		}

	}
	hideProcessing();
}

function saveOrUpdateContractBasicInfo() {

	if (validateBasicInfoFields()) {
		enableBasicInfoFields();
		showProcessing();
		var url = './rateContract.do?submitName=saveOrUpdateContractBasicInfo';
		ajaxCall1(url,"rateContractForm",printCallBackContractBasicInfo);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
		document.getElementById("GroupKey").disabled = true;
		document.getElementById("IndustryType").disabled = true;
		document.getElementById("CustomerName").disabled = true;
	}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallBackContractBasicInfo(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotationId;
			getDomElementById("hdnQuotationNo").value = quotationTO.rateQuotationNo;
			getDomElementById("customerNewId").value = quotationTO.customer.customerId;
			getDomElementById("contactId1").value = quotationTO.customer.primaryContact.contactId;
			if (!isNull(quotationTO.customer.secondaryContact.contactId)) {
				getDomElementById("contactId2").value = quotationTO.customer.secondaryContact.contactId;
			}
			if(userType == 'S'){
				disableContactCustomerInfoFields();
			}
			enableTabs(1);
			jQuery.unblockUI();
			alert(quotationTO.transMsg);
		}
	}else{
		jQuery.unblockUI();
	}
}

function saveOrUpdateContractFixedCharges() {
	if(validateCheck()){
	showProcessing();
	disableOrEnableFixedChargesTaxValues(false);

	getDomElementById("excecutiveRemarks").disabled = false;
	getDomElementById("approversRemarks").disabled = false;
	var url = './rateContract.do?submitName=saveOrUpdateContractFixedCharges';
	ajaxCall1(url, "rateContractForm", printCallContractFixedCharges);
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallContractFixedCharges(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;	
			var industryType =	getDomElementById("IndustryType").value;
			if (industryType.split("~")[1] == government) {
				disableOrEnableFixedChargesTaxChk(false);
				disableOrEnableFixedChargesTaxChkBox(true);
			}else{
				disableOrEnableFixedChargesTaxChk(true);
				disableOrEnableFixedChargesTaxChkBox(true);
			}
			disableOrEnableFixedChargesTaxValues(true);
			jQuery.unblockUI();
			alert(quotationTO.transMsg);
		}
	}else{
		jQuery.unblockUI();
	}

}

function saveOrUpdateContractEcomerceFixedCharges() {
	if (validateCheckForCharges()) {
	disableOrEnableFixedChargesTaxChkBox(false);
	disableOrEnableFixedChargesTaxValues(false);
	var url = './rateContract.do?submitName=saveOrUpdateEcomerceContractFixedCharges';
	ajaxCall1(url, "rateContractForm", printCallContractEcomerceFixedCharges);
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallContractEcomerceFixedCharges(ajaxResp) {

	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;
			var industryType =	getDomElementById("IndustryType").value;
			if (industryType.split("~")[1] == government) {
				disableOrEnableFixedChargesTaxChk(false);
				disableOrEnableFixedChargesTaxChkBox(true);
			}else{
				disableOrEnableFixedChargesTaxChk(true);
				disableOrEnableFixedChargesTaxChkBox(true);
			}
			disableOrEnableFixedChargesTaxValues(true);
			alert(quotationTO.transMsg);
			jQuery.unblockUI();
		}
	}else{
		jQuery.unblockUI();
	}

}

function saveOrUpdateContractRTOCharges() {
	var url = './rateContract.do?submitName=saveOrUpdateContractRTOCharges';
	ajaxCall1(url, "rateContractForm", printCallBackContractRTOCharges);
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}


function printCallBackContractRTOCharges(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;
			alert(quotationTO.transMsg);
			jQuery.unblockUI();
		}
	}else{
		jQuery.unblockUI();
	}

}

function renewContract(){
	
	/* The below disable statement has been added in order to prevent the user from pressing the renew button again
	 * when the current renew request is getting processed */
	buttonDisableById("renewBtn");
	var contractId = getDomElementById("rateContractId").value;
	var quotationId = getDomElementById("rateQuotationId").value;
	var contractType = getDomElementById("rateContractType").value;
	
	var url = "./rateContract.do?submitName=renewContract&contractId="+contractId+"&quotationId="+quotationId+"&contractType="+contractType;
	document.rateContractForm.action = url;
	document.rateContractForm.submit();
	
}
function saveContractProposedRates(val) {
action = val;
	
	getDomElementById("rateProdCatId").value = prodCatId;
	getDomElementById("rateQuotId").value = getDomElementById("rateQuotationId").value;
	if(!isNull(getDomElementById("IndustryCategory")))
		getDomElementById("indCatCode").value = getDomElementById("IndustryCategory").value;
	
	secLen = sectorList.length;
	wtLen = wtList.length;
	
	setupDropdownValues();
	prepareSectorArr();
	
	getDomElementById("rowNo").value = rowCount-1;
	
	if(productCode == 'CO' && (configuredType == PPX || configuredType == BOTH)){
		if(!checkFinalWeightSlabs()){
			return;
		}
		if(!checkAddWtSlabs()){
			return;
		}
	}
	
	var cols = 5;
	var wtCols = 0; 
	
	var conType = getDomElementById("configuredType").value;
	var coCols = 0;
	if(conType == PPX){
		coCols = 7;
	}else if(conType == BOTH){
		coCols = 9;
	}else{
		coCols = 5;
	}
	
	for(var j =0 ;j<secLen;j++){
		if(sectorList[j].sectorType == "D" 
				&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
			for(var p =0;p<prodCatAry.length;p++){
				if(prodCatCodeAry[p] ==  "CO"){
					wtCols = coCols;
				}else{
					wtCols = cols;
				}
				for(var n = 1 ; n <= wtCols ; n++){
					if(!isNull(getDomElementById("rate"+prodCatAry[p]+sectorList[j].sectorTO.sectorId+n))){
						getDomElementById("rate"+prodCatAry[p]+sectorList[j].sectorTO.sectorId+n).disabled = false;
					}
				}
			}
		}
	}
	for(var i=1;i<rowCount;i++){
		for(var k=1;k<=coCols;k++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+k+i))){
				getDomElementById("specialDestRate"+prodCatId+k+i).disabled = false;
			}
		}
	}
	
	assignROIValuesToZones();
	
	if(!isNull(document.getElementById("flatRate"))){
		document.getElementById("flatRate").value = 'P';
	}
	if(!isNull(document.getElementById("flatRateChk"))){
	if(productCode == "TR"){
		if(document.getElementById("flatRateChk").checked == true){
			document.getElementById("flatRate").value = 'F';
		}else{
			document.getElementById("flatRate").value = 'P';
		}
	}
	}
	
		if(checkGridFieldsData()){
	
		var url = "./rateContract.do?submitName=saveOrUpdateContractRateQuotationProposedRates";
		ajaxCall1(url, "rateContractForm", saveCallbackfun);
		jQuery.blockUI({
			message : '<h3><img src="images/loading_animation.gif"/></h3>'
		});
		}
}
function saveCallbackfun(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var rbmhTO = responseText;
			if (assignVal(ajaxResp)) {
				if (!isNull(rbmhTO.productCatHeaderTO)) {
					if(productCode == "CO"){
						getDomElementById("proposedRatesCO").value = "Y";
						if(configuredType == DOX){
							getDomElementById("proposedRatesD").value = 'Y';
						}else if(configuredType == PPX){
							getDomElementById("proposedRatesP").value = 'Y';
						}else if(configuredType == BOTH){
							getDomElementById("proposedRatesB").value = 'Y';
						}
					}
					jQuery.unblockUI();
					alert(rbmhTO.transMsg);
				}
			}else{
				jQuery.unblockUI();
			}
		}
	}else{
		jQuery.unblockUI();
	}

}
	

function enableToEditFields(){
	
	getDomElementById("Address1").disabled = false;
	getDomElementById("Address2").disabled = false;
	getDomElementById("Address3").disabled = false;
	getDomElementById("Pincode").disabled = false;
	getDomElementById("Title1").disabled = false;
	getDomElementById("AuthorizedPerson1").disabled = false;
	getDomElementById("Designation1").disabled = false;
	getDomElementById("Department1").disabled = false;
	getDomElementById("Email1").disabled = false;
	getDomElementById("Contact1").disabled = false;
	getDomElementById("Extn1").disabled = false;
	getDomElementById("Mobile1").disabled = false;
	getDomElementById("FAX1").disabled = false;
	
	if(getDomElementById("secondaryCheck").checked == true){
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
}

function enableBasicInfoFields(){
	
	document.getElementById("Region").disabled = false;
	document.getElementById("Station").disabled = false;
	document.getElementById("rateQuotationNo").disabled = false;
	getDomElementById("CreatedDate").disabled = false;
	getDomElementById("Station").disabled = false;
	getDomElementById("SalesOffice").disabled = false;
	//document.getElementById("SalesPerson").disabled = false;
	if(!isNull(getDomElementById("IndustryCategory")))
		document.getElementById("IndustryCategory").disabled = false;
	if(!isNull(getDomElementById("BusinessType")))
		document.getElementById("BusinessType").disabled = false;
    document.getElementById("IndustryType").disabled = false;
    document.getElementById("GroupKey").disabled = false;
    document.getElementById("CustomerName").disabled = false;
}

function renewValidDate(){
	
	var validDate = document.getElementById("validFromDate").value;
	if(isNull(validDate)){
		alert("Please select Effective From Date");
		setTimeout(function(){document.getElementById("validFromDate").focus();}, 10);
		return false;
	}
	
	
	var validDateDay = parseInt(validDate.substring(0, 2), 10);
	var validDateMon = parseInt(validDate.substring(3, 5), 10);
	var validDateYr = parseInt(validDate.substring(6, 10), 10);
	
	var fromDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
	
	
	var toDayDate = new Date();
	toDayDate.setDate(toDayDate.getDate()+1);
	toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
	
	if(fromDate < toDayDate ){
		alert("Effective From Date should be greater than today date");
		return false;
	}
	
	var sysValidDate = document.getElementById("sysFromDate").value;
	
	var sysDateDay = parseInt(sysValidDate.substring(0, 2), 10);
	var sysDateMon = parseInt(sysValidDate.substring(3, 5), 10);
	var sysDateYr = parseInt(sysValidDate.substring(6, 10), 10);
	var sysFromDate = new Date(sysDateYr, (sysDateMon - 1), sysDateDay);
	
	if((sysFromDate - fromDate) != 0){
		alert("Before Submit, Please save the Efffective From Date details in Billing details Tab");
		setTimeout(function(){document.getElementById("validFromDate").focus();}, 10);
		return false;
	}
	
	expDate = document.getElementById("oldContractExpDate").value;
	if(!isNull(expDate)){
	
	var expDateDay = parseInt(expDate.substring(0, 2), 10);
	var expDateMon = parseInt(expDate.substring(3, 5), 10);
	var expDateYr = parseInt(expDate.substring(6, 10), 10);
	
	
	var toDate = new Date(expDateYr, (expDateMon - 1), expDateDay);
	
	if(fromDate <= toDate){
		alert("Efffective From Date should be greater than Old Contract Expire Date "+document.getElementById("oldContractExpDate").value);
		setTimeout(function(){document.getElementById("validFromDate").focus();}, 10);		
		return false;
	}
	}
	
	return true;
}

function isValidSoldToCode(){
	

		var soldToCde = getDomElementById("soldToCode").value;
		if(isNull(soldToCde)){
			alert("Please enter Sold To Code");
			return false;
		}
	
	return true;
}


function isValidShipToCode(){
	
	var cnrtType = getDomElementById("billingContractType").value;
	if(!isNull(cnrtType) && cnrtType == "N"){
		var rCnt = rowCountP;
		for(var i=1 ; i<rCnt ; i++){
			if(!isNull(getDomElementById("customerCode"+i))
					&& isNull(getDomElementById("customerCode"+i).value)){
				alert("Please fill Customer Codes in Pickip Details tab");
				return false;
			}
			
		}
	}else if(!isNull(cnrtType) && cnrtType == "R"){
		var rCnt = rowCountD;
		for(var i=1 ; i<rCnt ; i++){
			if(!isNull(getDomElementById("customerCodeDlv"+i))
					&& isNull(getDomElementById("customerCodeDlv"+i).value)){
				alert("Please fill Customer Codes in Delivery Details tab");
				return false;
			}
			
		}
	}
	return true;
}

function configureStatus(){
	var uType = getDomElementById("userType").value;
	
		if(!isNull(uType) && uType == "S"){
			getDomElementById("contractStatus").value = 'A';
		}else{
			var billingType = getDomElementById("typeOfBilling").value;
			if( billingType == "DBCP"){
				var soldToCde = getDomElementById("soldToCode").value;
				if(!isNull(soldToCde))
					getDomElementById("contractStatus").value = 'A';
			}else{
				getDomElementById("contractStatus").value = 'S';
			}
		}
}

function validateEffectiveDates(){
	var fDate = getDomElementById(fromDate).value;
	
	/*if(!isNull(fDate) && !isNull(tDate)){
		var url='./rateContract.do?submitName=validateContractDates&fromDate='+fDate+'&toDate='+tDate;
		ajaxCall(url, RATE_CON_FORM, callBackForValidateDates);//RATE_CON_FORM - Global var.
	}*/
	
	var validfromDateDay = parseInt(fDate.substring(0, 2), 10);
	var validfromDateMon = parseInt(fDate.substring(3, 5), 10);
	var validfromDateYr = parseInt(fDate.substring(6, 10), 10);
	
	
	var fromDate = new Date(validfromDateYr, (validfromDateMon - 1), validfromDateDay);
	var toDate = new Date();
	
	
	
	if(toDate > fromDate){
		alert("Valid Till date should not exceed one year than Effective From date.");
		return false;
	}
	
	return true;
}

function loadSalesCity(selectId,val,datalist){
	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].cityId;
			opt.text = datalist[i].cityName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function loadSalesOfc(selectId,val,datalist){
	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].officeId;
			opt.text = datalist[i].officeName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function loadSalesPerson(selectId,val,datalist){
	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].employeeId;
			opt.text = datalist[i].firstName + " "
			+ datalist[i].lastName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function disableContactCustomerInfoFields(){
	document.getElementById("Region").disabled = true;
	//document.getElementById("Station").disabled = true;
	document.getElementById("rateQuotationNo").disabled = true;
	getDomElementById("CreatedDate").disabled = true;
	//document.getElementById("SalesPerson").disabled = false;
	if(!isNull(getDomElementById("IndustryCategory")))
		document.getElementById("IndustryCategory").disabled = true;
	if(!isNull(getDomElementById("BusinessType")))
		document.getElementById("BusinessType").disabled = true;
	if(salesOfcTypeCode == 'CO'){
		getDomElementById("SalesOffice").disabled = true;
		getDomElementById("Station").disabled = true;
	}else{
		getDomElementById("SalesOffice").disabled = false;
		getDomElementById("Station").disabled = false;
	}
	/*document.getElementById("IndustryType").disabled = true;
	document.getElementById("GroupKey").disabled = true;
	document.getElementById("CustomerName").disabled = true;*/
}