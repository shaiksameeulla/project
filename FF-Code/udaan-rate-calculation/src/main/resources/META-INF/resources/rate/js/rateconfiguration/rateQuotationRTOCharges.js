/*var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";*/
function enableFields(chkObj) {
	if (chkObj.checked) {
		getDomElementById("sameAsSlabRateChk").disabled = false;
		getDomElementById("sameAsSlabRate").disabled = false;
		getDomElementById("Discount").disabled = false;
		
	} else {
		getDomElementById("sameAsSlabRateChk").disabled = true;
		getDomElementById("sameAsSlabRateChk").checked = false;	
		getDomElementById("sameAsSlabRate").disabled = true;
		getDomElementById("Discount").disabled = true;
		getDomElementById("Discount").value = "";
	}
}

function enableDiscountFields(chkObj) {
	if (chkObj.checked) {
		getDomElementById("Discount").value = "";
		getDomElementById("Discount").disabled = true;

	} else {
		getDomElementById("Discount").disabled = false;
	}
}
function saveOrUpdateRTOCharges() {
	if(validateRTOFields()){
		getDomElementById("excecutiveRemarks").disabled = false;
		getDomElementById("approversRemarks").disabled = false;
		getDomElementById("saveRTOBtn").disabled = true;
		var url = './rateQuotation.do?submitName=saveOrUpdateRTOCharges';
		ajaxCall1(url, "rateQuotationForm", printCallBackRTOCharges);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function validateRTOFields(){
	var disValue = getDomElementById("Discount").value;
	if(getDomElementById("RTOChargesApplicableChk").checked == true){		
		
		if(getDomElementById("sameAsSlabRateChk").checked == false && isNull(disValue)){
			alert("Please select the check box Same As Tariff No. or provide the Discount value");
			setTimeout(function() {
				document.getElementById("sameAsSlabRateChk").focus();
			}, 10);
			return false;
		}		
	
	}
	
	return true;
}
/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallBackRTOCharges(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var quotationTO = responseText;
			alert(quotationTO.transMsg);
		}
	}
	disableRemarksFields();
	enableDisableRTOFieldsBasedOnStatus();
	jQuery.unblockUI();
	getDomElementById("saveRTOBtn").disabled = false;
}

function loadRTOChargesDefault() {
	
	var quotationId = getDomElementById("rateQuotationId").value;
	if(!isNull(quotationId)){
	url = './rateQuotation.do?submitName=loadRTOChargesDefault&quotationId='
			+ quotationId;
	jQuery.ajax({
		url : url,
		success : function(req) {
			printDefaultDetails(req);
		}
	});
	}
}

function printDefaultDetails(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var RTOChargesTO = responseText;
			if(!isNull(RTOChargesTO.quotaionRTOChargesId)){
		getDomElementById("sameAsSlabRateChk").disabled = false;
		getDomElementById("Discount").disabled = false;

		if (RTOChargesTO.sameAsSlabRate == "Y") {
			getDomElementById("Discount").disabled = true;
			getDomElementById("sameAsSlabRateChk").checked = true;
		}
		if (RTOChargesTO.rtoChargesApplicable == "Y") {
			getDomElementById("RTOChargesApplicableChk").checked = true;
	
		}
		if (!isNull(RTOChargesTO.discountOnSlab)) {
			getDomElementById("sameAsSlabRateChk").disabled = true;
			getDomElementById("Discount").value = RTOChargesTO.discountOnSlab;

		}
		}else{
			getDomElementById("RTOChargesApplicableChk").checked = false;		
			getDomElementById("sameAsSlabRateChk").checked = false;		
			getDomElementById("Discount").value = "";
		}
			
	}
		disableRemarksFields();
		enableDisableRTOFieldsBasedOnStatus();
		
		/* The below method is called to restrict user from modifying RTO charges tab using super-edit functionality */
		restrictRtoChargesModificationViaSuperEdit();
}
}

function submitQuotation(){
	
	if(	validateRTOFields() && isProposedRatesConfigured()){
		var url = "./rateQuotation.do?submitName=submitRateQuotation";
		ajaxCall1(url, "rateQuotationForm", submitQuotationcallback);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
	
}
function submitQuotationcallback(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("QuotationNo").value = quotationTO.rateQuotation.rateQuotationNo;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;
			getDomElementById("CreatedDate").value = quotationTO.rateQuotation.createdDate;
			getDomElementById("quotationStatus").value = quotationTO.rateQuotation.status;
			if (quotationTO.rateQuotation.status == "S"
					|| quotationTO.rateQuotation.status == "A") {
				disableRTOFields();
				hideProcessing();
				alert(quotationTO.transMsg);
			} else if (quotationTO.rateQuotation.status == "N"){
				hideProcessing();
				alert('Data saved successfully.');
			}
		}
	}else{
		hideProcessing();
	}
}


function disableAllRTOButtons(){
	buttonDisabled("approveBtn","btnintform");
	jQuery("#" +"approveBtn").addClass("btnintformbigdis");
	buttonDisabled("rejectBtn","btnintform");
	jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
	buttonDisabled("copyBtn","btnintform");
	jQuery("#" +"copyBtn").addClass("btnintformbigdis");
	buttonDisabled("saveRTOBtn","btnintform");
	jQuery("#" +"saveRTOBtn").addClass("btnintformbigdis");
	buttonDisabled("clearRTOBtn","btnintform");
	jQuery("#" +"clearRTOBtn").addClass("btnintformbigdis");
	buttonDisabled("createBtn","btnintform");
	jQuery("#" +"createBtn").addClass("btnintformbigdis");
	buttonDisabled("submitBtn","btnintform");
	jQuery("#" +"submitBtn").addClass("btnintformbigdis");
	buttonDisabled("printBtn","btnintform");
	jQuery("#" +"printBtn").addClass("btnintformbigdis");
}
function disableRTOFields(){
	var status = getValueByElementId("quotationStatus");
	var contractCreated=getValueByElementId("contractCreated");
	var empType = getDomElementById("empType").value;
	var module = getDomElementById("module").value;
	
	if(!isNull(status) && status == 'R'){
		enableDisableRTOfields(true);
		buttonDisabled("approveBtn","btnintform");
		jQuery("#" +"approveBtn").addClass("btnintformbigdis");
		buttonDisabled("rejectBtn","btnintform");
		jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
		if(isNull(module)){
			buttonEnabled("copyBtn","btnintform");
			jQuery("#" +"copyBtn").removeClass("btnintformbigdis");
		}else{
			buttonDisabled("copyBtn","btnintform");
			jQuery("#" +"copyBtn").addClass("btnintformbigdis");
		}
		buttonDisabled("saveRTOBtn","btnintform");
		jQuery("#" +"saveRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("clearRTOBtn","btnintform");
		jQuery("#" +"clearRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("createBtn","btnintform");
		jQuery("#" +"createBtn").addClass("btnintformbigdis");
		buttonDisabled("submitBtn","btnintform");
		jQuery("#" +"submitBtn").addClass("btnintformbigdis");
		buttonDisabled("printBtn","btnintform");
		jQuery("#" +"printBtn").addClass("btnintformbigdis");
	}
	else if(!isNull(status) && status == 'S'){
		enableDisableRTOfields(true);
		buttonDisabled("approveBtn","btnintform");
		jQuery("#" +"approveBtn").addClass("btnintformbigdis");
		buttonDisabled("rejectBtn","btnintform");
		jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
		buttonDisabled("saveRTOBtn","btnintform");
		jQuery("#" +"saveRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("clearRTOBtn","btnintform");
		jQuery("#" +"clearRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("submitBtn","btnintform");
		jQuery("#" +"submitBtn").addClass("btnintformbigdis");
		buttonDisabled("createBtn","btnintform");
		jQuery("#" +"createBtn").addClass("btnintformbigdis");
		buttonDisabled("copyBtn","btnintform");
		jQuery("#" +"copyBtn").addClass("btnintformbigdis");
		buttonEnabled("printBtn","btnintform");
		jQuery("#" +"printBtn").removeClass("btnintformbigdis");
	}
	else if(!isNull(status) && status == 'A'){
		enableDisableRTOfields(true);
		buttonDisabled("approveBtn","btnintform");
		jQuery("#" +"approveBtn").addClass("btnintformbigdis");
		buttonDisabled("rejectBtn","btnintform");
		jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
		buttonDisabled("saveRTOBtn","btnintform");
		jQuery("#" +"saveRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("clearRTOBtn","btnintform");
		jQuery("#" +"clearRTOBtn").addClass("btnintformbigdis");
		buttonDisabled("submitBtn","btnintform");
		jQuery("#" +"submitBtn").addClass("btnintformbigdis");
		
		if(isNull(aprvrLevel) || 
				(!isNull(aprvrLevel) && (aprvrLevel != "R" || aprvrLevel != "C"))){
		if (contractCreated == "true" || aprvrLevel == "C" || (!isNull(empType))) {
			buttonDisabled("createBtn", "btnintform");
			jQuery("#" + "createBtn").addClass("btnintformbigdis");
		} else {
			buttonEnabled("createBtn", "btnintform");
			jQuery("#" + "createBtn").addClass("btnintformbig1");
		}
		}
		buttonDisabled("copyBtn","btnintform");
		jQuery("#" +"copyBtn").addClass("btnintformbigdis");
		buttonEnabled("printBtn","btnintform");
		jQuery("#" +"printBtn").removeClass("btnintformbigdis");
	}
	//else if(!isNull(status) && status == 'N'){
	else{
		buttonDisabled("approveBtn","btnintform");
		jQuery("#" +"approveBtn").addClass("btnintformbigdis");
		buttonDisabled("rejectBtn","btnintform");
		jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
		buttonEnabled("saveRTOBtn","btnintform");
		jQuery("#" +"saveRTOBtn").removeClass("btnintformbigdis");
		buttonEnabled("clearRTOBtn","btnintform");
		jQuery("#" +"clearRTOBtn").removeClass("btnintformbigdis");
		buttonEnabled("submitBtn","btnintform");
		jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
		buttonDisabled("createBtn","btnintform");
		jQuery("#" +"createBtn").addClass("btnintformbigdis");
		buttonDisabled("copyBtn","btnintform");
		jQuery("#" +"copyBtn").addClass("btnintformbigdis");
		buttonDisabled("printBtn","btnintform");
		jQuery("#" +"printBtn").addClass("btnintformbigdis");
	}
	
}

function enableDisableRTOfields(flag){
	getDomElementById("RTOChargesApplicableChk").disabled = flag;
	getDomElementById("Discount").disabled = flag;
	getDomElementById("sameAsSlabRateChk").disabled = flag;	
	
	getDomElementById("excecutiveRemarks").disabled = flag;
	getDomElementById("approversRemarks").disabled = flag;
}
function clearRTOChargesFields(){
	loadRTOChargesDefault();
}

function enableRTOChargesButtons(){
	buttonEnabled("saveRTOBtn","btnintform");
	jQuery("#" +"saveRTOBtn").removeClass("btnintformbigdis");
	buttonEnabled("clearRTOBtn","btnintform");
	jQuery("#" +"clearRTOBtn").removeClass("btnintformbigdis");
}
function enableApproveRejectButtons(){
	buttonEnabled("approveBtn","btnintform");
	jQuery("#" +"approveBtn").removeClass("btnintformbigdis");
	buttonEnabled("rejectBtn","btnintform");
	jQuery("#" +"rejectBtn").removeClass("btnintformbigdis");
	buttonEnabled("printBtn","btnintform");
	jQuery("#" +"printBtn").removeClass("btnintformbigdis");
	buttonDisabled("submitBtn","btnintform");
	jQuery("#" +"submitBtn").addClass("btnintformbigdis");
}

function approveQuotation(opName){
	var selectdQuotationNos = document.getElementById("QuotationNo").value;
	var selectdApprovalRequirds= document.getElementById("approvalRequired").value;
	
	var url="./rateQuotation.do?submitName=approveQuotation&selectdQuotationNos="+selectdQuotationNos+"&selectdApprovalRequirds="+selectdApprovalRequirds+"&opName="+opName;
	ajaxJqueryWithRow(url,"rateQuotationForm",approvQuotationResponse,opName);
}

function approvQuotationResponse(ajaxResp,status){
	jQuery.unblockUI();
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		
		var error = responseText[ERROR_FLAG];
		
		if (responseText != null && error != null) {
			alert(error);
			clear();
		} else {
			var success = responseText[SUCCESS_FLAG];
			
			if (status == "approve") {
				if (responseText != null && success != null) {
					if(aprvrLevel == "R"){
						getDomElementById("quotationStatus").value = "S";
						aprvdAt = "RO";
					}else{
						getDomElementById("quotationStatus").value = "A";
						aprvdAt = "RC";
					}
					disableRTOFields();
					alert(success);
				}

			} else if (status == "reject") {
				if (responseText != null && success != null) {
					getDomElementById("quotationStatus").value = "R";
					disableRTOFields();
					alert(success);

				}
			}
		}
	}

}

function enableDisableRTOFieldsBasedOnStatus(){
	disableAllRTOButtons();
	var status = getDomElementById("quotationStatus").value;
	
	var module = getDomElementById("module").value;
	var page =  getDomElementById("page").value;
	var contractStatus="";
	var isNew="";
	var userType = "";
	if(!isNull(getDomElementById("contractStatus"))){
		contractStatus = getDomElementById("contractStatus").value;
		isNew = getDomElementById("isNew").value;
		userType = getDomElementById("userType").value;
	}
	if((!isNull(contractStatus) && contractStatus == "C" && !isNull(isNew) && isNew == "true") 
			|| (!isNull(userType) && userType == "S" && !isNull(contractStatus) && 
					( contractStatus == "S" || contractStatus == "A"))
	 )
	{
		enableRTOChargesButtons();
	}else if(module == "view" && status == 'S' && isNull(page)){
		
		if((aprvdAt == "RO") && aprvrLevel == "R"){
			buttonEnabled("printBtn","btnintform");
			jQuery("#" +"printBtn").removeClass("btnintformbigdis");
		}else{
			enableRTOChargesButtons();
			enableApproveRejectButtons();
		}
	}else if(status != "N"){
		disableRTOFields();
	}else{
		enableRTOChargesButtons();
		buttonEnabled("submitBtn","btnintform");
		jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
		buttonDisabled("approveBtn","btnintform");
		jQuery("#" +"approveBtn").addClass("btnintformbigdis");
		buttonDisabled("rejectBtn","btnintform");
		jQuery("#" +"rejectBtn").addClass("btnintformbigdis");
		buttonDisabled("printBtn","btnintform");
		jQuery("#" +"printBtn").addClass("btnintformbigdis");
		buttonDisabled("copyBtn","btnintform");
		jQuery("#" +"copyBtn").addClass("btnintformbigdis");
	}
}


function printQuotation(){
	var quotationNo=getDomElementById("QuotationNo").value;
	var url = getDomElementById("contractPrintUrl").value+'pages/reportviewer/quotationReport.jsp?'
		 + "quotationNo="+quotationNo ;
	window.open(url, "_blank");
}

function enableCreateContractBtn(){
	buttonEnabled("createBtn", "btnintform");
	jQuery("#" + "createBtn").addClass("btnintformbig1");
}

function disableCreateContractBtn(){	
	jQuery("#createBtn").attr("disabled", true);
	jQuery("#createBtn").removeClass("btnintformbig1");
	jQuery("#createBtn").addClass("btnintformbigdis");
}

/* The user should not be allowed to modify any part of RTO charges tab using the super-edit functionality.
 * Currently, users modify the RTO charges components from any contract at will. This causes problems in the billing module when the bills get generated.
 * Hence, the below lines of code have been added*/
function restrictRtoChargesModificationViaSuperEdit() {
	var userType = getDomElementById("userType");
	if (!isNull(userType)) {
		if (userType.value != "" && userType.value == "S") {
			disableRTOFields();
		}
	}
}