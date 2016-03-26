/*var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";*/
var codChrgLen =0;
function loadFixedChargesDefault() {
	if(!isNull(getDomElementById("OctroiBornByChk"))){
		getDomElementById("OctroiBornByChk").checked = true;
	}
	if(!isNull(getDomElementById("OctroiServiceChargesChk"))){
		getDomElementById("OctroiServiceChargesChk").checked = true;
	}
	if(!isNull(getDomElementById("vwDenominatorChk"))){
		getDomElementById("vwDenominatorChk").checked = true;
	}
	if(!isNull(getDomElementById("FuelSurchargesChk"))){
		getDomElementById("FuelSurchargesChk").checked = true;
	}
	qNo = getDomElementById("rateQuotationId").value;
	if(!isNull(qNo)){
		loadDefaultChargesValue();
	}
}


function disableOrEnableFixedChargesTaxChk(flag){
	getDomElementById("ServiceTaxChk").checked=flag;
	getDomElementById("EducationCessChk").checked=flag;
	getDomElementById("HigherEducationCessChk").checked=flag;
	getDomElementById("SurchargeOnSTChk").checked=flag;
	getDomElementById("StateTaxChk").checked=flag;
}	
function disableOrEnableFixedChargesTaxChkBox(flag){	
	getDomElementById("ServiceTaxChk").disabled=flag;
	getDomElementById("EducationCessChk").disabled=flag;
	getDomElementById("HigherEducationCessChk").disabled=flag;
	getDomElementById("SurchargeOnSTChk").disabled=flag;
	getDomElementById("StateTaxChk").disabled=flag;
}
function disableOrEnableFixedChargesTaxValues(flag){	
	getDomElementById("SurchargeOnST").disabled=flag;
	getDomElementById("HigherEducationCess").disabled=flag;
	getDomElementById("StateTax").disabled=flag;
	getDomElementById("ServiceTax").disabled=flag;
	getDomElementById("EducationCess").disabled=flag;
	
}


function loadDefaultChargesValue() {
	var quotationId = getDomElementById("rateQuotationId").value;
	var stateId = getDomElementById("stateId").value;
	showProcessing();
	url = './rateQuotation.do?submitName=loadDefaultFixedChargesValue&quotationId='
			+ quotationId + '&stateId=' + stateId;
	jQuery.ajax({
		url : url,
		success : function(req) {
			printDefaultFixedChargesDetails(req);
		}
	});	
}

function printDefaultFixedChargesDetails(responseText) {
	hideProcessing();
	
	if (!isNull(responseText)) {
		var quotationTO = jsonJqueryParser(responseText);
		var error = quotationTO[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {

		var riskSurchrgeValue=null;
		var quotaionFixedChargesTO = quotationTO[0];
		var componentTO = quotationTO[1];
		var taxComponentTO = quotationTO[2];
		var codChargesTO = quotationTO[3];
		var rateQuotationType = getDomElementById("rateQuotationType").value;
		if (rateQuotationType == "N") {
			getDomElementById("FuelSurcharges").value = "";
			getDomElementById("OtherCharges").value = "";
			getDomElementById("DocumentHandlingCharges").value = "";
			getDomElementById("ToPayCharge").value = "";
			getDomElementById("ParcelHandlingCharges").value = "";
			getDomElementById("ServiceTax").value = "";
			getDomElementById("AirportHandlingCharges").value = "";
			getDomElementById("EducationCess").value = "";
			getDomElementById("LCCharges").value = "";
			getDomElementById("SurchargeOnST").value = "";
			getDomElementById("HigherEducationCess").value = "";
			getDomElementById("StateTax").value = "";
			
			if (!isNull(quotaionFixedChargesTO)) {
			enableTabs(3);
			var fuelChk = false;
				for ( var i = 0; i < quotaionFixedChargesTO.length; i++) {
					if (quotaionFixedChargesTO[i].rateComponentCode == fuelSurcharge) {
						fuelChk = true;
						getDomElementById("FuelSurchargesChk").checked=true;
						getDomElementById("FuelSurcharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("FuelSurcharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == otherCharges) {
						getDomElementById("OtherChargesChk").checked=true;
						getDomElementById("OtherCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("OtherCharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == documentCharges) {
						getDomElementById("DocumentHandlingChargesChk").checked=true;
						getDomElementById("DocumentHandlingCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("DocumentHandlingCharges"));
					}

					if (quotaionFixedChargesTO[i].rateComponentCode == toPayCharges) {
						getDomElementById("ToPayChargeChk").checked=true;
						getDomElementById("ToPayCharge").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("ToPayCharge"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == parcelCharges) {
						getDomElementById("ParcelHandlingChargesChk").checked=true;
						getDomElementById("ParcelHandlingCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("ParcelHandlingCharges"));
					}

					/*if (quotaionFixedChargesTO[i].rateComponentCode == serviceTax) {
						getDomElementById("ServiceTaxChk").checked=true;
						getDomElementById("ServiceTax").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("ServiceTax"));
					}*/
					if (quotaionFixedChargesTO[i].rateComponentCode == airportCharges) {
						getDomElementById("AirportHandlingChargesChk").checked=true;
						getDomElementById("AirportHandlingCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("AirportHandlingCharges"));
					}
					/*if (quotaionFixedChargesTO[i].rateComponentCode == eduCharges) {
						getDomElementById("EducationCessChk").checked=true;
						getDomElementById("EducationCess").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("EducationCess"));
					}*/
					if (quotaionFixedChargesTO[i].rateComponentCode == lcCharges) {
						getDomElementById("LCChargesChk").checked=true;
						getDomElementById("LCCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("LCCharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == surchargeOnST) {
						getDomElementById("SurchargeOnSTChk").checked=true;
						getDomElementById("SurchargeOnST").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("SurchargeOnST"));
					}
					/*if (quotaionFixedChargesTO[i].rateComponentCode == HigherEduCharges) {
						getDomElementById("HigherEducationCessChk").checked=true;
						getDomElementById("HigherEducationCess").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("HigherEducationCess"));
					}*/
					if (quotaionFixedChargesTO[i].rateComponentCode == stateTax) {
						getDomElementById("StateTaxChk").checked=true;
						getDomElementById("StateTax").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("StateTax"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == octroiServiceCharges) {
						getDomElementById("OctroiServiceChargesChk").checked=true;
						getDomElementById("OctroiServiceCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("OctroiServiceCharges"));

					}
					if (quotaionFixedChargesTO[i].rateComponentCode == riskSurchrge) {
						if(!isNull(riskSurchrgeValue)){
							var riskSC=getDomElementById(riskSurchrgeValue).value;
							getDomElementById("RiskSurchargesChk").checked=true;
							getDomElementById("RiskSurcharges").value = riskSC;
						}else
							riskSurchrgeValue = setCharge(quotaionFixedChargesTO[i].value);
					}
					if (!isNull(quotaionFixedChargesTO[i].octroiBourneBy)) {
						getDomElementById("OctroiBornByChk").checked=true;
						getDomElementById("OctroiBornBy").value = quotaionFixedChargesTO[i].octroiBourneBy;

					}
					if (!isNull(quotaionFixedChargesTO[i].riskSurchrge)) {
						if(!isNull(riskSurchrgeValue)){
							getDomElementById("RiskSurchargesChk").checked=true;
						getDomElementById("RiskSurcharges").value = riskSC;
						}else
							riskSurchrgeValue = quotaionFixedChargesTO[i].riskSurchrge;
					}
				}
				if (!fuelChk) {
					getDomElementById("FuelSurchargesChk").checked=false;
				}
			}

			if (!isNull(componentTO)) {
				for ( var i = 0; i < componentTO.length; i++) {
					if (componentTO[i].rateComponentCode == fuelSurcharge
							&& getDomElementById("FuelSurcharges").value == "") {
						getDomElementById("FuelSurcharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("FuelSurcharges"));
					}
					if (componentTO[i].rateComponentCode == otherCharges
							&& getDomElementById("OtherCharges").value == "") {
						getDomElementById("OtherCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("OtherCharges"));
					}
					if (componentTO[i].rateComponentCode == documentCharges
							&& getDomElementById("DocumentHandlingCharges").value == "") {
						getDomElementById("DocumentHandlingCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("DocumentHandlingCharges"));
					}

					if (componentTO[i].rateComponentCode == toPayCharges
							&& getDomElementById("ToPayCharge").value == "") {
						getDomElementById("ToPayCharge").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("ToPayCharge"));
					}
					if (componentTO[i].rateComponentCode == parcelCharges
							&& getDomElementById("ParcelHandlingCharges").value == "") {
						getDomElementById("ParcelHandlingCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("ParcelHandlingCharges"));
					}

					if (componentTO[i].rateComponentCode == airportCharges
							&& getDomElementById("AirportHandlingCharges").value == "") {
						getDomElementById("AirportHandlingCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("AirportHandlingCharges"));
					}

					if (componentTO[i].rateComponentCode == lcCharges
							&& getDomElementById("LCCharges").value == "") {
						getDomElementById("LCCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("LCCharges"));
					}
				}

				if (!isNull(taxComponentTO)) {
					for ( var i = 0; i < taxComponentTO.length; i++) {
						if (taxComponentTO[i].rateComponentId.rateComponentCode == surchargeOnST
								&& getDomElementById("SurchargeOnST").value == "") {
							getDomElementById("SurchargeOnST").value = taxComponentTO[i].taxPercentile;
							setAmountFormatZero(getDomElementById("SurchargeOnST"));
						}
						if (taxComponentTO[i].rateComponentId.rateComponentCode == HigherEduCharges
								&& getDomElementById("HigherEducationCess").value == "") {
							getDomElementById("HigherEducationCessChk").checked=true;
							getDomElementById("HigherEducationCess").value = taxComponentTO[i].taxPercentile;
							setAmountFormatZero(getDomElementById("HigherEducationCess"));
						}
						if (taxComponentTO[i].rateComponentId.rateComponentCode == stateTax
								&& getDomElementById("StateTax").value == "") {
							getDomElementById("StateTax").value = taxComponentTO[i].taxPercentile;
							setAmountFormatZero(getDomElementById("StateTax"));
						}

						if (taxComponentTO[i].rateComponentId.rateComponentCode == serviceTax
								&& getDomElementById("ServiceTax").value == "") {
							getDomElementById("ServiceTaxChk").checked=true;
							getDomElementById("ServiceTax").value = taxComponentTO[i].taxPercentile;
							setAmountFormatZero(getDomElementById("ServiceTax"));
						}

						if (taxComponentTO[i].rateComponentId.rateComponentCode == eduCharges
								&& getDomElementById("EducationCess").value == "") {
							getDomElementById("EducationCessChk").checked=true;
							getDomElementById("EducationCess").value = taxComponentTO[i].taxPercentile;
							setAmountFormatZero(getDomElementById("EducationCess"));
						}

					}

				}
				
			}
			
		} else {
			getDomElementById("FuelSurcharges").value = "";
			getDomElementById("OtherCharges").value = "";
			
			getDomElementById("ServiceTax").value = "";
			getDomElementById("AirportHandlingCharges").value = "";
			getDomElementById("EducationCess").value = "";
		
			getDomElementById("SurchargeOnST").value = "";
			getDomElementById("HigherEducationCess").value = "";
			getDomElementById("StateTax").value = "";
			if (!isNull(quotaionFixedChargesTO)) {
				var fuelChk = false;
				for ( var i = 0; i < quotaionFixedChargesTO.length; i++) {
					if (quotaionFixedChargesTO[i].rateComponentCode == fuelSurcharge) {
						fuelChk = true;
						getDomElementById("FuelSurchargesChk").checked=true;
						getDomElementById("FuelSurcharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("FuelSurcharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == otherCharges) {
						getDomElementById("OtherChargesChk").checked=true;
						getDomElementById("OtherCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("OtherCharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == airportCharges) {
						getDomElementById("AirportHandlingChargesChk").checked=true;
						getDomElementById("AirportHandlingCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("AirportHandlingCharges"));
					}
					if (quotaionFixedChargesTO[i].rateComponentCode == octroiServiceCharges) {
						getDomElementById("OctroiServiceChargesChk").checked=true;
						getDomElementById("OctroiServiceCharges").value = quotaionFixedChargesTO[i].value;
						setAmountFormatZero(getDomElementById("OctroiServiceCharges"));

					}
					if (quotaionFixedChargesTO[i].rateComponentCode == riskSurchrge) {
						if(!isNull(riskSurchrgeValue)){
							var riskSC=getDomElementById(riskSurchrgeValue).value;
							getDomElementById("RiskSurchargesChk").checked=true;
							getDomElementById("RiskSurcharges").value = riskSC;
						}else
							riskSurchrgeValue = setCharge(quotaionFixedChargesTO[i].value);
					}
					if (!isNull(quotaionFixedChargesTO[i].octroiBourneBy)) {
						getDomElementById("OctroiBornByChk").checked=true;
						getDomElementById("OctroiBornBy").value = quotaionFixedChargesTO[i].octroiBourneBy;

					}
					if (!isNull(quotaionFixedChargesTO[i].riskSurchrge)) {
						if(!isNull(riskSurchrgeValue)){
							getDomElementById("RiskSurchargesChk").checked=true;
						getDomElementById("RiskSurcharges").value = riskSC;
						}else
							riskSurchrgeValue = quotaionFixedChargesTO[i].riskSurchrge;

					}
				}
				
				if (!fuelChk) {
					getDomElementById("FuelSurchargesChk").checked=false;
				}
			}
			
			
			if (!isNull(componentTO)) {
				for ( var i = 0; i < componentTO.length; i++) {
					if (componentTO[i].rateComponentCode == fuelSurcharge
							&& getDomElementById("FuelSurcharges").value == "") {
						getDomElementById("FuelSurcharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("FuelSurcharges"));
					}
					if (componentTO[i].rateComponentCode == otherCharges
							&& getDomElementById("OtherCharges").value == "") {
						getDomElementById("OtherCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("OtherCharges"));
					}

					if (componentTO[i].rateComponentCode == airportCharges
							&& getDomElementById("AirportHandlingCharges").value == "") {
						getDomElementById("AirportHandlingCharges").value = componentTO[i].rateGlobalConfigValue;
						setAmountFormatZero(getDomElementById("AirportHandlingCharges"));
					}
				}

			}
			
			if (!isNull(taxComponentTO)) {
				for ( var i = 0; i < taxComponentTO.length; i++) {
					if (taxComponentTO[i].rateComponentId.rateComponentCode == surchargeOnST
							&& getDomElementById("SurchargeOnST").value == "") {
						getDomElementById("SurchargeOnST").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("SurchargeOnST"));
					}
					if (taxComponentTO[i].rateComponentId.rateComponentCode == HigherEduCharges
							&& getDomElementById("HigherEducationCess").value == "") {
						getDomElementById("HigherEducationCess").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("HigherEducationCess"));
					}
					if (taxComponentTO[i].rateComponentId.rateComponentCode == stateTax
							&& getDomElementById("StateTax").value == "") {
						getDomElementById("StateTax").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("StateTax"));
					}

					if (taxComponentTO[i].rateComponentId.rateComponentCode == serviceTax
							&& getDomElementById("ServiceTax").value == "") {
						getDomElementById("ServiceTax").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("ServiceTax"));
					}

					if (taxComponentTO[i].rateComponentId.rateComponentCode == eduCharges
							&& getDomElementById("EducationCess").value == "") {
						getDomElementById("EducationCess").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("EducationCess"));
					}

				}
			}
		}
		
		if (!isNull(codChargesTO)) {
			getDomElementById("codChargesChk").checked = true;
			disableOrEnableCODChargeFields(false);
			codChrgLen =  codChargesTO.length;
			for ( var j = 0; j < codChrgLen; j++) {
				if(!isNull(document.getElementById("codChargeId" + (j + 1)))){
				var codChargeId=document.getElementById("codChargeId" + (j + 1)).value;
				for ( var i = 0; i < codChrgLen; i++) {
				
				if(codChargeId==codChargesTO[i].codChargeId){
				if(codChargesTO[i].fixedChargeValue == 0){
					document.getElementById("fixedCharges" + (j + 1)).value = '';
				}else{
					document.getElementById("fixedCharges" + (j + 1)).value = codChargesTO[i].fixedChargeValue;
				}
				if(codChargesTO[i].percentileValue == 0){
					document.getElementById("codPercent" + (j + 1)).value = '';
				}else{
					document.getElementById("codPercent" + (j + 1)).value = codChargesTO[i].percentileValue;
				}
				if(codChargesTO[i].consideeHigherFixedPercent=="Y"){
					document.getElementById("fcOrCODRadio" + (j+1)).checked = true;
				}
				if(codChargesTO[i].considerFixed=="Y"){
					document.getElementById("FixedChargesRadio" + (j+1)).checked = true;
				}
				break;
				}
				}
				}
			}
		}
		
	}
	var industryType =	getDomElementById("IndustryType").value;
	if (industryType.split("~")[1] == government) {
		getDomElementById("SurchargeOnSTChk").disabled = false;
		getDomElementById("HigherEducationCessChk").disabled = false;
		getDomElementById("StateTaxChk").disabled = false;
		getDomElementById("ServiceTaxChk"). disabled = false;
		getDomElementById("EducationCessChk"). disabled = false;
		
		getDomElementById("ServiceTaxChk").checked=false;
		getDomElementById("EducationCessChk").checked=false;
		getDomElementById("HigherEducationCessChk").checked=false;
		getDomElementById("SurchargeOnSTChk").checked=false;
		getDomElementById("StateTaxChk").checked=false;
		
		getDomElementById("SurchargeOnSTChk").disabled = true;
		getDomElementById("HigherEducationCessChk").disabled = true;
		getDomElementById("StateTaxChk").disabled = true;
		getDomElementById("ServiceTaxChk"). disabled = true;
		getDomElementById("EducationCessChk"). disabled = true;

	} else {
		getDomElementById("SurchargeOnSTChk").disabled = false;
		getDomElementById("HigherEducationCessChk").disabled = false;
		getDomElementById("StateTaxChk"). disabled = false;
		getDomElementById("ServiceTaxChk").disabled = false;
		getDomElementById("EducationCessChk").disabled = false;
		
		getDomElementById("ServiceTaxChk").checked=true;
		getDomElementById("EducationCessChk").checked=true;
		getDomElementById("HigherEducationCessChk").checked=true;
		getDomElementById("SurchargeOnSTChk").checked=true;
		getDomElementById("StateTaxChk").checked=true;
		
		getDomElementById("SurchargeOnSTChk").disabled = true;
		getDomElementById("HigherEducationCessChk").disabled = true;
		getDomElementById("StateTaxChk").disabled = true;
		getDomElementById("ServiceTaxChk"). disabled = true;
		getDomElementById("EducationCessChk"). disabled = true;

	}
	
	
	var status = getDomElementById("quotationStatus").value;
	var module = getDomElementById("module").value;
	var page =  getDomElementById("page").value;
	
	var contractStatus="";
	var isNew="";
	var userType = "";
	
	var industryType =	getDomElementById("IndustryType").value;
	if (industryType.split("~")[1] == government) {
		disableOrEnableFixedChargesTaxChk(false);
		disableOrEnableFixedChargesTaxChkBox(true);
	}else{
		disableOrEnableFixedChargesTaxChk(true);
		disableOrEnableFixedChargesTaxChkBox(true);
	}
	disableOrEnableFixedChargesTaxValues(true);
	
	if(!isNull(getDomElementById("contractStatus"))){
		contractStatus = getDomElementById("contractStatus").value;
		isNew = getDomElementById("isNew").value;
		userType = getDomElementById("userType").value;
	}
	//disableRemarksFields();
	if((!isNull(contractStatus) && contractStatus == "C" && !isNull(isNew) && isNew == "true") 
			|| (!isNull(userType) && userType == "S" && !isNull(contractStatus) && 
					( contractStatus == "S" || contractStatus == "A")))
	{
		//disFixedChrgFileds();
		enableFixedChargedButtons();
		
	}else if(module == "view" && status == 'S' && isNull(page)){
		if((aprvdAt == "RO") && aprvrLevel == "R"){
			disableFixedChargedFields();			
		}else{
			enableFixedChargedButtons();
		}
	}else if(!isNull(status) && status != 'N'){
		disableFixedChargedFields();
	}	
  }	
	
	/* The below method is called to restrict user from modifying fixed charges tab using super-edit functionality */
	restrictFixedChargesModificationViaSuperEdit();
}


function saveOrUpdateFixedCharges() {
if(validateCheck()){
	showProcessing();
	disableOrEnableFixedChargesTaxValues(false);
	//getDomElementById("excecutiveRemarks").disabled = false;
	//getDomElementById("approversRemarks").disabled = false;
	getDomElementById("saveFiexdChrgsBtn").disabled = true;
	var url = './rateQuotation.do?submitName=saveOrUpdateFixedCharges';
	ajaxCall1(url, "rateQuotationForm", printCallFixedCharges);
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallFixedCharges(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var quotationTO = responseText;
			getDomElementById("rateQuotationId").value = quotationTO.rateQuotation.rateQuotationId;
			getDomElementById("QuotationNo").value = quotationTO.rateQuotation.rateQuotationNo;
			getDomElementById("CreatedDate").value = quotationTO.rateQuotation.createdDate;
			//getDomElementById("quotationStatus").value = quotationTO.rateQuotation.status;
			var industryType =	getDomElementById("IndustryType").value;
			if (industryType.split("~")[1] == government) {
				disableOrEnableFixedChargesTaxChk(false);
				disableOrEnableFixedChargesTaxChkBox(true);
			}else{
				disableOrEnableFixedChargesTaxChk(true);
				disableOrEnableFixedChargesTaxChkBox(true);
			}
			disableOrEnableFixedChargesTaxValues(true);
			disableRemarksFields();
			enableTabs(3);
			jQuery.unblockUI();
			alert(quotationTO.transMsg);
		}
	}else{
		jQuery.unblockUI();
	}
	getDomElementById("saveFiexdChrgsBtn").disabled = false;
}



function setAmountFormatZero(domElement) {
	if (!isNull(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];

	} else {
		domElement.value = "0.00";
	}
}

function setCharge(chrgVal) {

	var newVal = "";
	var val=chrgVal;
	if(val.length == 1)
		 newVal = val[0] + "." + 0;
	else{
	
	newVal = val;
	}
	return newVal;
}


function disableFixedChargedFields(){
	
		disFixedChrgFileds();
		
		buttonDisabled("saveFiexdChrgsBtn","btnintform");
		jQuery("#" +"saveFiexdChrgsBtn").addClass("btnintformbigdis");
		
		buttonDisabled("clearFiexdChrgsBtn","btnintform");
		jQuery("#" +"clearFiexdChrgsBtn").addClass("btnintformbigdis");
		
}

function enableFixedChargedButtons(){
	
	buttonEnabled("saveFiexdChrgsBtn","btnintform");
	jQuery("#" +"saveFiexdChrgsBtn").removeClass("btnintformbigdis");
	
	buttonEnabled("clearFiexdChrgsBtn","btnintform");
	jQuery("#" +"clearFiexdChrgsBtn").removeClass("btnintformbigdis");
}

function disFixedChrgFileds(){
getDomElementById("FuelSurcharges").disabled = true;
getDomElementById("FuelSurchargesChk").disabled=true;
getDomElementById("OtherCharges").disabled = true;
getDomElementById("OtherChargesChk").disabled=true;
if(!isNull(getDomElementById("DocumentHandlingCharges"))){
getDomElementById("DocumentHandlingCharges").disabled = true;
getDomElementById("DocumentHandlingChargesChk").disabled = true;
}if(!isNull(getDomElementById("ToPayCharge"))){
getDomElementById("ToPayCharge").disabled = true;
getDomElementById("ToPayChargeChk").disabled = true;
}if(!isNull(getDomElementById("ParcelHandlingCharges"))){
getDomElementById("ParcelHandlingCharges").disabled = true;
getDomElementById("ParcelHandlingChargesChk").disabled = true;
}
getDomElementById("ServiceTax").disabled = true;
getDomElementById("ServiceTaxChk").disabled = true;
getDomElementById("AirportHandlingCharges").disabled = true;
getDomElementById("AirportHandlingChargesChk").disabled=true;
getDomElementById("EducationCess").disabled = true;
getDomElementById("EducationCessChk").disabled=true;
if(!isNull(getDomElementById("LCCharges"))){
getDomElementById("LCCharges").disabled = true;
getDomElementById("LCChargesChk").disabled = true;
}
getDomElementById("SurchargeOnST").disabled = true;
getDomElementById("SurchargeOnSTChk").disabled = true;
getDomElementById("HigherEducationCess").disabled = true;
getDomElementById("HigherEducationCessChk").disabled = true;
getDomElementById("StateTax").disabled = true;
getDomElementById("StateTaxChk").disabled = true;
if(!isNull(getDomElementById("OctroiServiceCharges"))){
getDomElementById("OctroiServiceChargesChk").disabled = true;
getDomElementById("OctroiServiceCharges").disabled = true;
getDomElementById("OctroiBornBy").disabled = true;
getDomElementById("OctroiBornByChk").disabled = true;
}
if(!isNull(getDomElementById("RiskSurcharges"))){
getDomElementById("RiskSurcharges").disabled = true;
getDomElementById("RiskSurchargesChk").disabled = true;
}
//getDomElementById("excecutiveRemarks").disabled = true;
//getDomElementById("approversRemarks").disabled = true;

getDomElementById("codChargesChk").disabled = true;
	if(!isNull(getDomElementById("codRowsCnt"))){
		var cntVal = parseInt(getDomElementById("codRowsCnt").value);
		
		for ( var i = 1; i <= cntVal; i++) {
			if(!isNull(document.getElementById("fixedCharges" + i))){
				document.getElementById("fixedCharges" + i).disabled = true;
				document.getElementById("codPercent" + i).disabled = true;
				document.getElementById("fcOrCODRadio" +i).disabled = true;
				document.getElementById("FixedChargesRadio" + i).disabled = true;
			}
		}
	}
}

function validateCheck() {
	if (!validateCheckRate(getDomElementById("FuelSurchargesChk"),
			getDomElementById("FuelSurcharges"),"Fuel Surcharges")) {
		return false;
	}
	if (!validateCheckRate(getDomElementById("OtherChargesChk"),
			getDomElementById("OtherCharges"),"Other Charges")) {
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

	if (!validateCheckRate(getDomElementById("ParcelHandlingChargesChk"),
			getDomElementById("ParcelHandlingCharges"),"Parcel Handling Charges")) {
		return false;
	}

	if (!validateCheckRate(getDomElementById("OctroiServiceChargesChk"),
			getDomElementById("OctroiServiceCharges"),"Octroi Service Charges")) {
		return false;
	}

	if (!validateCheckRate(getDomElementById("AirportHandlingChargesChk"),
			getDomElementById("AirportHandlingCharges"),"Airport Handling Charges")) {
		return false;
	}
	if (!validateCheckRate(getDomElementById("ToPayChargeChk"),
			getDomElementById("ToPayCharge"),"ToPay Charge")) {
		return false;
	}

	if (!validateCheckRate(getDomElementById("DocumentHandlingChargesChk"),
			getDomElementById("DocumentHandlingCharges"),"Document Handling Charges")) {
		return false;
	}

	if (!validateCheckRate(getDomElementById("LCChargesChk"),
			getDomElementById("LCCharges"),"LC Charges")) {
		return false;
	}
	
	if (!isNull(getDomElementById("BusinessType"))) {
		var BusinessType = getDomElementById("BusinessType").value;
		var lcCode = getDomElementById("lcCode").value;
		if (!isNull(BusinessType) && ! isNull(lcCode) && BusinessType == lcCode) {
			if (getDomElementById("LCChargesChk").checked == false) {
				alert("Please select the LC charge.");
				return false;
			}
		}
	}
	/*if (getDomElementById("RiskSurchargesChk").checked == false) {
			alert("Please select the Risk Surcharges.");
			return false;
		}*/
	if (!validateCODCharges()) {
		return false;
	}
	
	
	getDomElementById("SurchargeOnSTChk").disabled = false;
	getDomElementById("HigherEducationCessChk").disabled = false;
	getDomElementById("StateTaxChk").disabled = false;
	getDomElementById("ServiceTaxChk"). disabled = false;
	getDomElementById("EducationCessChk"). disabled = false;

	return true;

}


function validateCheckRate(chkObj,txtObj,txt){ 
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
		if(isNull(amt) || isNull(parseFloat(amt)) || amt=="." || amt.match(dotPattern)){
			return true;
		}
		return false;
	}

function tabKeyForOtherCharge(e, nextFieldId) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 9 || key == 0) {
		nextFieldId.focus();
		return false;
	} else {

	}

} 

function validateFCKey(event){		
		var charCode;
		if (window.event)
			charCode = window.event.keyCode; // IE
		else
			charCode = event.which; 
		
		if(charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0)
			return true;
		else return onlyDecimal(event);	
}

function validateFixedChargeVal(obj, type, chkObj){
	val = obj.value;
	if(document.getElementById(chkObj).checked ==  true){
		if(!isNull(val)){
		    var num = new Number(val);
		    if(type == "P"){
		    	if(/^[0-9]{0,2}(\.[0-9]{0,2})?$/.test(val) && num > 0){
		        return true;
		    	} else {
			       alert('Please enter the value in 99.99 format');
			       obj.value = "";
					setTimeout(function() {
						obj.focus();
					}, 10);
			       return false;
		    	}
			}else{
				if(/^[0-9]{0,4}(\.[0-9]{0,2})?$/.test(val) && num > 0){
			        return true;
			    	} else {
				       alert('Please enter the value in 9999.99 format');
				       obj.value = "";
						setTimeout(function() {
							obj.focus();
						}, 10);
				       return false;
			   	}
			}
		}
	}
	
	return true;
}


function disableRemarksFields(){
	var approver =  getDomElementById("approver").value;
	if(approver=="Y"){
		getDomElementById("excecutiveRemarks").disabled = true;
		getDomElementById("approversRemarks").disabled = false;
	}else{
		getDomElementById("approversRemarks").disabled = true;
		getDomElementById("excecutiveRemarks").disabled = false;
	}
}

/* The user should not be allowed to modify any part of the fixed charges tab using the super-edit functionality.
 * Currently, users modify the fixed charges components from any contract at will. This causes problems in the billing module when the bills get generated.
 * Hence, the below lines of code have been added*/
function restrictFixedChargesModificationViaSuperEdit() {
	var userType = getDomElementById("userType");
	if (!isNull(userType)) {
		if (userType.value != "" && userType.value == "S") {
			disableFixedChargedFields();
		}
	}
}