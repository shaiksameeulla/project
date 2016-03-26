/**
 * Startup function for fixed charges
 */
function fixedChrgsStartup(){
	searchFixedChrgsDtls();
}

/**
 * To save fixed charges details for non-priority product(s)
 */
function saveOrUpdateFixedChrgsDtls(){
	if(validateFixedChrgsTab(PRO_CAT_TYPE_N)){
		jQuery("#productCatType").val(PRO_CAT_TYPE_N);
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateFixedChrgsDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSaveFixedChrgsCashRate);
	}
}
//call back function for saveOrUpdateFixedChrgsDtls
function callSaveFixedChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		jQuery("#nonPriorityChargesCheck").val("Y");
		alert("Date saved successfully.");
	} else {
		alert("Exception occurs. Data not saved.");
	}
}

/**
 * To search fixed charges details for non-priority product(s)
 */
function searchFixedChrgsDtls(){
	var cashRateId = jQuery("#cashRateHeaderId").val();
	if(!isNull(cashRateId)){
		var url = "./cashRateConfiguration.do?submitName=searchFixedChrgsDtls"
			+"&productMapId="+cashRateId+"&productType="+PRO_CAT_TYPE_N;
		ajaxCallWithoutForm(url, callSearchFixedChrgsCashRate);
	}
}
//call back function for searchFixedChrgsDtls
function callSearchFixedChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		showProcessing();
		//var data = eval('(' + ajaxResp + ')');
		var data = eval(ajaxResp);
		var fixedChrg = data.fixedChargesTO;
		//fuelSurcharge
		if(!isNull(fixedChrg.fuelSurcharge) && fixedChrg.priorityInd == 'N'){
			jQuery("#fuelSurcharge").val(fixedChrg.fuelSurcharge);
			getDomElementById("fuelSurchargeChk").checked=true;
		} else {
			jQuery("#fuelSurcharge").val("");
			getDomElementById("fuelSurchargeChk").checked=false;
		}
		//otherCharges
		if(!isNull(fixedChrg.otherCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#otherCharges").val(fixedChrg.otherCharges);
			getDomElementById("otherChargesChk").checked=true;
		} else {
			jQuery("#otherCharges").val("");
			getDomElementById("otherChargesChk").checked=false;
		}
		//serviceTax
		if(!isNull(fixedChrg.serviceTax) && fixedChrg.priorityInd == 'N'){
			jQuery("#serviceTax").val(fixedChrg.serviceTax);
			getDomElementById("serviceTaxChk").checked=true;
			checkStateTax(false);
		} else {
			//jQuery("#serviceTax").val("");
			//getDomElementById("serviceTaxChk").checked=false;
			
		}
		//eduCess
		if(!isNull(fixedChrg.eduCess) && fixedChrg.priorityInd == 'N'){
			jQuery("#eduCess").val(fixedChrg.eduCess);
			getDomElementById("eduCessChk").checked=true;
			checkStateTax(false);
		} else {
			//jQuery("#eduCess").val("");
			//getDomElementById("eduCessChk").checked=false;
			
		}
		//higherEduCess
		if(!isNull(fixedChrg.higherEduCess) && fixedChrg.priorityInd == 'N'){
			jQuery("#higherEduCess").val(fixedChrg.higherEduCess);
			getDomElementById("higherEduCessChk").checked=true;
			checkStateTax(false);
		} else {
			//jQuery("#higherEduCess").val("");
			//getDomElementById("higherEduCessChk").checked=false;
			
		}
		//stateTax
		if(!isNull(fixedChrg.stateTax) && fixedChrg.priorityInd == 'N'){
			jQuery("#stateTax").val(fixedChrg.stateTax);
			getDomElementById("stateTaxChk").checked=true;
			checkServiceTax(false);
		} else {
			//jQuery("#stateTax").val("");
			//getDomElementById("stateTaxChk").checked=false;
		
		}
		//surchargeOnST
		if(!isNull(fixedChrg.surchargeOnST) && fixedChrg.priorityInd == 'N'){
			jQuery("#surchargeOnST").val(fixedChrg.surchargeOnST);
			getDomElementById("surchargeOnSTChk").checked=true;
			checkServiceTax(false);
		} else {
			//jQuery("#surchargeOnST").val("");
			//getDomElementById("surchargeOnSTChk").checked=false;
			
		}
		//octroiServiceCharges
		if(!isNull(fixedChrg.octroiServiceCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#octroiServiceCharges").val(fixedChrg.octroiServiceCharges);
			getDomElementById("octroiServiceChargesChk").checked=true;
		} else {
			jQuery("#octroiServiceCharges").val("");
			getDomElementById("octroiServiceChargesChk").checked=false;
		}
		//parcelCharges
		if(!isNull(fixedChrg.parcelCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#parcelCharges").val(fixedChrg.parcelCharges);
			getDomElementById("parcelChargesChk").checked=true;
		} else {
			jQuery("#parcelCharges").val("");
			getDomElementById("parcelChargesChk").checked=false;
		}
		//toPay
		if(!isNull(fixedChrg.toPay) && fixedChrg.priorityInd == 'N'){
			jQuery("#toPay").val(fixedChrg.toPay);
			getDomElementById("toPayChk").checked=true;
		} else {
			jQuery("#toPay").val("");
			getDomElementById("toPayChk").checked=false;
		}
		//docCharges
		if(!isNull(fixedChrg.docCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#docCharges").val(fixedChrg.docCharges);
			getDomElementById("docChargesChk").checked=true;
		} else {
			jQuery("#docCharges").val("");
			getDomElementById("docChargesChk").checked=false;
		}
		//lcCharges
		if(!isNull(fixedChrg.lcCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#lcCharges").val(fixedChrg.lcCharges);
			getDomElementById("lcChargesChk").checked=true;
		} else {
			jQuery("#lcCharges").val("");
			getDomElementById("lcChargesChk").checked=false;
		}
		//airportCharges
		if(!isNull(fixedChrg.airportCharges) && fixedChrg.priorityInd == 'N'){
			jQuery("#airportCharges").val(fixedChrg.airportCharges);
			getDomElementById("airportChargesChk").checked=true;
		} else {
			jQuery("#airportCharges").val("");
			getDomElementById("airportChargesChk").checked=false;
		}
		//Octroi Bourne By
		if(!isNull(fixedChrg.octroiBourneBy) && fixedChrg.priorityInd == 'N'){
			jQuery("#octroiBourneBy").val(fixedChrg.octroiBourneBy);
			getDomElementById("octroiBourneByChk").checked=true;
		} else {
			jQuery("#octroiBourneBy").val("");
			getDomElementById("octroiBourneByChk").checked=false;
		}
		selectTax(fixedChrg);
		hideProcessing();
	}
}

function checkServiceTax(flag){
	getDomElementById("serviceTaxChk").checked=flag;
	getDomElementById("eduCessChk").checked=flag;
	getDomElementById("higherEduCessChk").checked=flag;
}

function checkStateTax(flag){
	getDomElementById("surchargeOnSTChk").checked=flag;
	getDomElementById("stateTaxChk").checked=flag;	
}

function selectTax(fixedChrg){
	if(isNull(fixedChrg.serviceTax) && isNull(fixedChrg.eduCess) && 
			isNull(fixedChrg.higherEduCess) && isNull(fixedChrg.stateTax) && isNull(fixedChrg.surchargeOnST)){
		checkServiceTax(true);
	}
}