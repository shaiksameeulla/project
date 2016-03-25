/**
 * To search service request details
 * 
 * @param serviceRequestNo
 */
function searchServiceReqDtls(serviceRequestNo) {
	var url = "./serviceRequestForService.do?submitName=searchServiceReqDtls&serviceRequestNo="
			+ serviceRequestNo;
	showProcessing();
	jQuery.ajax({
		url : url,
		dataType : "text",
		success : function(req) {
			populateServiceReqDtls(req);
		}
	});
}
// call back function for searchServiceReqDtls()
function populateServiceReqDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {

			var data = eval('(' + ajaxResp + ')');

			// Setting complaint details text field value(s).
			getDomElementById("number_cd").value = data.bookingNo;
			getDomElementById("serviceRequestNo_cd").value = data.serviceRequestNo;
			getDomElementById("linkedServiceReqNo_cd").value = data.linkedServiceReqNo;
			getDomElementById("callerName_cd").value = data.callerName;
			getDomElementById("callerPhone_cd").value = data.callerPhone;
			getDomElementById("callerEmail_cd").value = data.callerEmail;
			getDomElementById("weightKgs_cd").value = data.weightKgs;
			getDomElementById("weightGrm_cd").value = data.weightGrm;
			getDomElementById("empEmailId_cd").value = data.empEmailId;
			getDomElementById("empPhone_cd").value = data.empPhone;
			getDomElementById("result_cd").value = data.serviceResult;
			getDomElementById("remark_cd").value = data.remark;

			// Setting complaint details check box value(s).
			setCheckboxValById("isLinkedWith_cd", data.isLinkedWith);
			setCheckboxValById("smsToConsignor_cd", data.smsToConsignor);
			setCheckboxValById("smsToConsignee_cd", data.smsToConsignee);
			setCheckboxValById("emailToCaller_cd", data.emailToCaller);

			// Setting complaint details drop down value(s).
			setDropdownValById("serviceRelated_cd", data.serviceRelated);
			setDropdownValById("complaintCategory_cd", data.complaintCategory);
			setDropdownValById("custType_cd", data.customerType.split("~")[1]);
			setDropdownValById("originCity_cd", data.originCityId);
			setDropdownValById("product_cd", data.productId);
			setDropdownValById("pincode_cd", data.pincodeId);
			setDropdownValById("consgTypes_cd", data.consignmentType);
			setDropdownValById("employeeId_cd", data.employeeId);
			setDropdownValById("status_cd", data.status);

			// setDropdownValById("serviceType_cd", data.serviceType);
			createDropDownCmpltDtls("serviceType_cd", data.serviceTypeList);
			getDomElementById("serviceType_cd").value = data.serviceType;
			// setDropdownValById("industryType_cd", data.industryType);
			createDropDownCmpltDtls("industryType_cd", data.industryTypeList);
			getDomElementById("industryType_cd").value = data.industryType;
			// setDropdownValById("sourceOfQuery_cd", data.sourceOfQuery);
			createDropDownCmpltDtls("sourceOfQuery_cd", data.srcOfQryList);
			getDomElementById("sourceOfQuery_cd").value = data.sourceOfQuery;
		}
	}
	hideProcessing();
}

/**
 * To set check box value(s) checked or un-checked by its id.
 * 
 * @param domElemId
 * @param domElemVal
 */
function setCheckboxValById(domElemId, domElemVal) {
	getDomElementById(domElemId).disabled = false;
	if (domElemVal == "Y") {
		getDomElementById(domElemId).checked = true;
	} else {// N
		getDomElementById(domElemId).checked = false;
	}
	getDomElementById(domElemId).disabled = true;
}

/**
 * To prepare and set drop down value(s) by its id.
 * 
 * @param domElemId
 * @param domElemVal
 */
function setDropdownValById(domElemId, domElemVal) {
	if (!isNull(domElemVal)) {
		getDomElementById(domElemId).disabled = false;
		var content = getDomElementById(domElemId);
		var opt;
		content.innerHTML = "";
		opt = document.createElement("OPTION");
		opt.value = "";
		if (!isNull(domElemVal)) {
			opt.appendChild(document.createTextNode(domElemVal));
		} else {
			opt.appendChild(document.createTextNode("SELECT"));
		}
		content.appendChild(opt);
	}
	getDomElementById(domElemId).disabled = true;
}

/**
 * To create drop down for complaint details tab (Note: it is only for stock
 * standard type)
 * 
 * @param domElemId
 * @param objList
 */
function createDropDownCmpltDtls(domElemId, objList) {
	var content = getDomElementById(domElemId);
	var opt;
	content.innerHTML = "";
	opt = document.createElement("OPTION");
	opt.value = "";
	opt.appendChild(document.createTextNode("SELECT"));
	content.appendChild(opt);
	if (!isNull(objList)) {
		var list = eval(objList);
		$.each(list, function(index, value) {
			var option;
			option = document.createElement("OPTION");
			option.value = this.stdTypeCode;
			var lbl = this.description;
			option.appendChild(document.createTextNode(lbl));
			content.appendChild(option);
		});
	}
	getDomElementById(domElemId).disabled = true;
}
