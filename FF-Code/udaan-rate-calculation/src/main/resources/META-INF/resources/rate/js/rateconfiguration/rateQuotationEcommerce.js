/*var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";*/
var codChargeLength;
var codType;
function ecommerceOnLoad(type) {
	document.getElementById("vwDenominatorChk").checked = true;
	codType = type;
	if(type == 'E'){
		document.getElementById("codChargesChk").checked = true;
	}
	var codChargeTO = document.getElementById("codChargeTO").value;
	codChargeLength = codChargeTO.split(",").length;
	for ( var i = 1; i <= codChargeLength; i++) {
		if(!isNull(document.getElementById("fcOrCODRadio" + (i)))){
			document.getElementById("fcOrCODRadio" + (i)).checked = true;
			if(type == 'N'){
				disableOrEnableCODChargeFields(true);
			}
		}
	}
}

function disableOrEnableCODChargeFields(flag){
	for ( var i = 1; i <= codChargeLength; i++) {
		document.getElementById("FixedChargesRadio" + (i)).disabled = flag;
		document.getElementById("fcOrCODRadio" + (i)).disabled = flag;
		document.getElementById("fixedCharges" + (i)).value = "";
		document.getElementById("codPercent" + (i)).value = "";
		document.getElementById("fixedCharges" + (i)).disabled = flag;
		document.getElementById("codPercent" + (i)).disabled = flag;
	}
}

//Added by Shaheed for Radio button disable
function disableOrEnableSingleRowOfFixedCharges(flag,loopCount){
	//alert(flag+ "Hello iam in disable method" + loopCount);
	//document.getElementById("fixedCharges" + loopCount).disabled = flag;
	var codPercent = document.getElementById("codPercent" + loopCount);
	codPercent.readOnly = flag;
	codPercent.value = "";
}



function loadCodChargeBoxes(){
	if(getDomElementById("codChargesChk").checked == true){
		disableOrEnableCODChargeFields(false);
	}else{
		disableOrEnableCODChargeFields(true);
	}
}
function saveOrUpdateEcomerceFixedCharges() {
	if (validateCheckForCharges()) {
		getDomElementById("codChargesChk").disabled = false;
		getDomElementById("saveFiexdChrgsBtn").disabled = true;
		showProcessing();
		disableOrEnableFixedChargesTaxChkBox(false);
		disableOrEnableFixedChargesTaxValues(false);
		var url = './rateQuotation.do?submitName=saveOrUpdateEcomerceFixedCharges';
		ajaxCall1(url, "rateQuotationForm", printCallEcomerceFixedCharges);
		showProcessing();
	}
}
/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallEcomerceFixedCharges(ajaxResp) {
	getDomElementById("codChargesChk").disabled = true;
	getDomElementById("saveFiexdChrgsBtn").disabled = false;
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;
			getDomElementById("QuotationNo").value = quotationTO.rateQuotation.rateQuotationNo;
			getDomElementById("CreatedDate").value = quotationTO.rateQuotation.createdDate;
			var industryType =	getDomElementById("IndustryType").value;
			if (industryType.split("~")[1] == government) {
				disableOrEnableFixedChargesTaxChk(false);
				disableOrEnableFixedChargesTaxChkBox(true);
			}else{
				disableOrEnableFixedChargesTaxChk(true);
				disableOrEnableFixedChargesTaxChkBox(true);
			}
			disableOrEnableFixedChargesTaxValues(true);
			//disableRemarksFields();
			enableTabs(3);
			hideProcessing();
			alert(quotationTO.transMsg);
		}
	}else{
		hideProcessing();
	}

}

function validateCheckForCharges() {
	if (!validateCheckRate(getDomElementById("FuelSurchargesChk"),
			getDomElementById("FuelSurcharges"),"Fuel Surcharges")) {
		return false;
	}
	if (!validateCheckRate(getDomElementById("OtherChargesChk"),
			getDomElementById("OtherCharges"),"Other Charges")) {
		return false;
	}

	if (!validateCheckRate(getDomElementById("AirportHandlingChargesChk"),
			getDomElementById("AirportHandlingCharges"),"Airport Handling Charges")) {
		return false;
	}
	
	if (getDomElementById("RiskSurchargesChk").checked == true) {
		if (isNull(getDomElementById("RiskSurcharges").value)) {
			alert("Please select the Risk Surcharge.");
			return false;
		}
	}

	if (getDomElementById("OctroiBornByChk").checked == false) {
		alert("Please select the Octroi Bourne By.");
		return false;		
	}
	
	if (getDomElementById("OctroiBornByChk").checked == true) {
		if (isNull(getDomElementById("OctroiBornBy").value)) {
			alert("Please select the Octroi Bourne By.");
			return false;
		}
	}

	if (getDomElementById("OctroiBornByChk").checked == true) {
		if (getDomElementById("OctroiServiceChargesChk").checked == false) {
			alert("Please select the Octroi Service Charge.");
			return false;
		}
	}

	/*if (getDomElementById("ServiceTaxChk").checked == false) {
		alert("Please select the Service Tax.");
		return false;
	}
	if (getDomElementById("EducationCessChk").checked == false) {
		alert("Please select the Education Cess.");
		return false;
	}
	if (getDomElementById("HigherEducationCessChk").checked == false) {
		alert("Please select the Higher Education Cess.");
		return false;
	}
	if (getDomElementById("StateTaxChk").checked == false) {
		alert("Please select the State Tax.");
		return false;
	}
	if (getDomElementById("SurchargeOnSTChk").checked == false) {
		alert("Please select the Surcharge On ST.");
		return false;
	}*/

	if (!validateCODCharges()) {
		return false;
	}

	return true;

}

function validateCheckRate(chkObj, txtObj,txt) {
	if (chkObj.checked == true) {
		if (isEmptyRate(txtObj.value)) {
			alert("Please enter a non-zero value for " + txt);
			txtObj.value = "";
			txtObj.focus();
			return false;
		}
	}
	return true;
}

function isEmptyRate(amt) {
	var dotPattern = /^[.]{2,10}$/;
	if (isNull(amt) || isNull(parseFloat(amt)) || amt == "."
			|| amt.match(dotPattern)) {
		return true;
	}
	return false;
}




function cancelEccQuotationInfo() {
	flag = confirm("Do you want to Cancel the Details");
	if (!flag) {
		return;
	} else {
		document.rateQuotationForm.action = "./rateQuotation.do?submitName=viewEcommerceRateQuotation&sales="+document.getElementById("salesType").value;
		document.rateQuotationForm.submit();
	}
}

function createEccomerceContract() {
	var QuotationNo = document.getElementById("QuotationNo").value;
	var loginOfficeCode = document.getElementById("loginOfficeCode").value;
	var createdBy = document.getElementById("createdBy").value;
	var updatedBy = document.getElementById("updatedBy").value;
	var rateQuotationType = getDomElementById("rateQuotationType").value;

	if (!isNull(QuotationNo)) {
		disableCreateContractBtn();
		url = './rateContract.do?submitName=createContract&QuotationNo='
				+ QuotationNo + '&loginOfficeCode=' + loginOfficeCode
				+ "&rateQuotationType=" + rateQuotationType + "&createdBy="
				+ createdBy+ "&updatedBy=" + updatedBy;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallcreateEcommerceContract(req);
			}
		});
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function printCallcreateEcommerceContract(data) {

	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var contractTO = responseText;
			var rateContractId = contractTO.rateContractId;
			var rateContractNo = contractTO.rateContractNo;
			var rateQuotationId = contractTO.rateQuotationTO.rateQuotationId;
			var rateQuotationNo = contractTO.rateQuotationTO.rateQuotationNo;
			var url = './rateContract.do?submitName=viewRateContractECommerce&rateContractNo='
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

function validateCODCharges(){
	if(getDomElementById("codChargesChk").checked == true){
		var codChargeTO = document.getElementById("codChargeTO").value;
		var isEmpty = false;
		var codLen = codChargeTO.split(",").length;
		//commented for artf3510122 
		/*for ( var i = 1; i <= codLen; i++) {
	
			if (isNull(document.getElementById("fixedCharges" + (i)).value)
					&& isNull(document.getElementById("codPercent" + (i)).value)) {
				isEmpty = true;
			} else if (!isNull(document.getElementById("fixedCharges" + (i)).value)
					|| !isNull(document.getElementById("codPercent" + (i)).value)) {
				isEmpty = false;
				break;
			}
	
		}
	
		if (isEmpty) {
			alert("Please enter the the non zero COD charges for at least one row");
			return false;
		}else{
			for ( var i = 1; i <= codLen; i++) {
				if(document.getElementById("FixedChargesRadio" + (i)).checked == true){
					if(isNull(document.getElementById("fixedCharges" + (i)).value)
							&& isNull(document.getElementById("codPercent" + (i)).value)){
						continue;
					}else if(isNull(document.getElementById("fixedCharges" + (i)).value)){
						alert("Please enter the non zero value for for fixed charges of COD amount");
						document.getElementById("fixedCharges" + (i)).value = "";
						setTimeout(function() {
							document.getElementById("fixedCharges" + (i)).focus();
						}, 10);
						return false;
					}
				} else if(document.getElementById("fcOrCODRadio" + (i)).checked == true){
					if(isNull(document.getElementById("fixedCharges" + (i)).value)
							&& isNull(document.getElementById("codPercent" + (i)).value)){
						continue;
					}else if(isNull(document.getElementById("codPercent" + (i)).value)){
						alert("Please enter the non zero value for FC or % COD Greater ");
						document.getElementById("codPercent" + (i)).value = "";
						setTimeout(function() {
							document.getElementById("codPercent" + (i)).focus();
						}, 10);
						return false;
					}
				}
				
			}
		}*/
		
		/*for ( var i = 1; i <= codLen; i++) {
			if(isNull(document.getElementById("fixedCharges" + (i)).value)){
				alert("Please enter the non zero value for fixed charges of COD amount");
				document.getElementById("fixedCharges" + (i)).value = "";
				setTimeout(function() {
					document.getElementById("fixedCharges" + (i)).focus();
				}, 10);
				return false;
			}if(isNull(document.getElementById("codPercent" + (i)).value)){
				alert("Please enter the non zero value for FC or % COD Greater ");
				document.getElementById("codPercent" + (i)).value = "";
				setTimeout(function() {
					document.getElementById("codPercent" + (i)).focus();
				}, 10);
				return false;
			}
		}*/
		
		for ( var i = 1; i <= codLen; i++) {
			if(document.getElementById("FixedChargesRadio" + (i)).checked == true){
				if(isNull(document.getElementById("fixedCharges" + (i)).value)){
					alert("Please enter the non zero value for fixed charges");
					document.getElementById("fixedCharges" + (i)).value = "";
					setTimeout(function() {
						document.getElementById("fixedCharges" + (i)).focus();
					}, 10);
					return false;
				}
			}else if(document.getElementById("fcOrCODRadio" + (i)).checked == true){
				if(isNull(document.getElementById("fixedCharges" + (i)).value)){
					alert("Please enter the non zero value for fixed charges");
					document.getElementById("fixedCharges" + (i)).value = "";
					setTimeout(function() {
						document.getElementById("fixedCharges" + (i)).focus();
					}, 10);
					return false;
				}else if(isNull(document.getElementById("codPercent" + (i)).value)){
			
					alert("Please enter the non zero value for % COD");
					document.getElementById("codPercent" + (i)).value = "";
					setTimeout(function() {
						document.getElementById("codPercent" + (i)).focus();
					}, 10);
					return false;
				}
			}
		}
	}	
	return true;
}

function allowOnlyNumbers(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode >= 48 && charCode <= 57)||charCode==9 || charCode==8 ||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function OnlyAlphabetsAndNos(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 || (charCode >= 48 && charCode <= 57)||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}