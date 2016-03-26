/**
 * Startup function for priority fixed charges
 */
function priorityFixedChrgsStartup() {
	searchPriorityFixedChrgsDtls();
}

/**
 * To save fixed charges details for priority product(s)
 */
function saveOrUpdatePriorityFixedChrgsDtls(){
	if(validateFixedChrgsTab(PRO_CAT_TYPE_P)){
		jQuery("#productCatType").val(PRO_CAT_TYPE_P);
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateFixedChrgsDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSavePriorityFixedChrgsCashRate);
	}
}
//call back function for saveOrUpdatePriorityFixedChrgsDtls
function callSavePriorityFixedChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		jQuery("#priorityChargesCheck").val("Y");
		alert("Date saved successfully.");
	} else {
		alert("Exception occurs. Data not saved.");
	}
}

/**
 * To search fixed charges details for priority product(s)
 */
function searchPriorityFixedChrgsDtls(){
	var cashRateId = jQuery("#cashRateHeaderId").val();
	if(!isNull(cashRateId)){
		var url = "./cashRateConfiguration.do?submitName=searchFixedChrgsDtls"
			+"&productMapId="+cashRateId+"&productType="+PRO_CAT_TYPE_P;
		ajaxCallWithoutForm(url, callSearchPriorityFixedChrgsCashRate);
	}
}
//call back function for searchPriorityFixedChrgsDtls
function callSearchPriorityFixedChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		showProcessing();
		//var data = eval('(' + ajaxResp + ')');
		var data = eval(ajaxResp);
		var fixedChrg = data.fixedChargesTO;
		//fuelSurchargePR
		if(!isNull(fixedChrg.fuelSurcharge) && fixedChrg.priorityInd == 'Y'){
			jQuery("#fuelSurchargePR").val(fixedChrg.fuelSurcharge);
			getDomElementById("fuelSurchargeChkPR").checked=true;
		} else {
			jQuery("#fuelSurchargePR").val("");
			getDomElementById("fuelSurchargeChkPR").checked=false;
		}
		//otherChargesPR
		if(!isNull(fixedChrg.otherCharges) && fixedChrg.priorityInd == 'Y'){
			jQuery("#otherChargesPR").val(fixedChrg.otherCharges);
			getDomElementById("otherChargesChkPR").checked=true;
		} else {
			jQuery("#otherChargesPR").val("");
			getDomElementById("otherChargesChkPR").checked=false;
		}
		//serviceTaxPR
		if(!isNull(fixedChrg.serviceTax) && fixedChrg.priorityInd == 'Y'){
			jQuery("#serviceTaxPR").val(fixedChrg.serviceTax);
			getDomElementById("serviceTaxChkPR").checked=true;
			checkStateTaxPR(false);
		} else {
			//jQuery("#serviceTaxPR").val("");
			//getDomElementById("serviceTaxChkPR").checked=false;
			
		}
		//eduCessPR
		if(!isNull(fixedChrg.eduCess) && fixedChrg.priorityInd == 'Y'){
			jQuery("#eduCessPR").val(fixedChrg.eduCess);
			getDomElementById("eduCessChkPR").checked=true;
			checkStateTaxPR(false);
		} else {
			//jQuery("#eduCessPR").val("");
			//getDomElementById("eduCessChkPR").checked=false;
			
		}
		//higherEduCessPR
		if(!isNull(fixedChrg.higherEduCess) && fixedChrg.priorityInd == 'Y'){
			jQuery("#higherEduCessPR").val(fixedChrg.higherEduCess);
			getDomElementById("higherEduCessChkPR").checked=true;
			checkStateTaxPR(false);
		} else {
			//jQuery("#higherEduCessPR").val("");
			//getDomElementById("higherEduCessChkPR").checked=false;
			
		}
		//stateTaxPR
		if(!isNull(fixedChrg.stateTax) && fixedChrg.priorityInd == 'Y'){
			jQuery("#stateTaxPR").val(fixedChrg.stateTax);
			getDomElementById("stateTaxChkPR").checked=true;
			checkServiceTaxPR(false);
		} else {
			//jQuery("#stateTaxPR").val("");
			//getDomElementById("stateTaxChkPR").checked=false;
			
		}
		//surchargeOnSTPR
		if(!isNull(fixedChrg.surchargeOnST) && fixedChrg.priorityInd == 'Y'){
			jQuery("#surchargeOnSTPR").val(fixedChrg.surchargeOnST);
			getDomElementById("surchargeOnSTChkPR").checked=true;
			checkServiceTaxPR(false);
		} else {
			//jQuery("#surchargeOnSTPR").val("");
			//getDomElementById("surchargeOnSTChkPR").checked=false;
			
		}
		//octroiServiceChargesPR
		if(!isNull(fixedChrg.octroiServiceCharges) && fixedChrg.priorityInd == 'Y'){
			jQuery("#octroiServiceChargesPR").val(fixedChrg.octroiServiceCharges);
			getDomElementById("octroiServiceChargesChkPR").checked=true;
		} else {
			jQuery("#octroiServiceChargesPR").val("");
			getDomElementById("octroiServiceChargesChkPR").checked=false;
		}
		//parcelChargesPR
		if(!isNull(fixedChrg.parcelCharges) && fixedChrg.priorityInd == 'Y'){
			jQuery("#parcelChargesPR").val(fixedChrg.parcelCharges);
			getDomElementById("parcelChargesChkPR").checked=true;
		} else {
			jQuery("#parcelChargesPR").val("");
			getDomElementById("parcelChargesChkPR").checked=false;
		}
		//airportChargesPR
		if(!isNull(fixedChrg.airportCharges) && fixedChrg.priorityInd == 'Y'){
			jQuery("#airportChargesPR").val(fixedChrg.airportCharges);
			getDomElementById("airportChargesChkPR").checked=true;
		} else {
			jQuery("#airportChargesPR").val("");
			getDomElementById("airportChargesChkPR").checked=false;
		}
		//Octroi Bourne By
		if(!isNull(fixedChrg.octroiBourneBy) && fixedChrg.priorityInd == 'Y'){
			jQuery("#octroiBourneByPR").val(fixedChrg.octroiBourneBy);
			getDomElementById("octroiBourneByChkPR").checked=true;
		} else {
			jQuery("#octroiBourneByPR").val("");
			getDomElementById("octroiBourneByChkPR").checked=false;
		}
		selectTaxPR(fixedChrg);
		hideProcessing();
	}
}


function checkServiceTaxPR(flag){
	getDomElementById("serviceTaxChkPR").checked=flag;
	getDomElementById("eduCessChkPR").checked=flag;
	getDomElementById("higherEduCessChkPR").checked=flag;
}

function checkStateTaxPR(flag){
	getDomElementById("surchargeOnSTChkPR").checked=flag;
	getDomElementById("stateTaxChkPR").checked=flag;	
}

function selectTaxPR(fixedChrg){
	if(fixedChrg.priorityInd == 'Y' && isNull(fixedChrg.serviceTax) && isNull(fixedChrg.eduCess) && 
			isNull(fixedChrg.higherEduCess) && isNull(fixedChrg.stateTax) && isNull(fixedChrg.surchargeOnST)){
		checkServiceTaxPR(true);
	}
}