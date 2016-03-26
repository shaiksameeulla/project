/**
 * Startup function for priority RTO charges
 */
function priorityRtoChrgsStartup(){
	searchPriorityRTOChrgsDtls();
}

/**
 * To save priority RTO charges details
 */
function saveOrUpdatePriorityRTOChrgsDtls(){
	if(validateRTOChrgsTab(PRO_CAT_TYPE_P)){
		jQuery("#productCatType").val(PRO_CAT_TYPE_P);
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateRTOChrgsDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSavePriorityRTOChrgsCashRate);
	}
}
//call back function for saveOrUpdatePriorityRTOChrgsDtls
function callSavePriorityRTOChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = eval('(' + ajaxResp + ')');
		//RTO Charges
		for(var i=0; i<data.rtoChrgsTOList.length; i++){
			var rtoChrgs = data.rtoChrgsTOList[i];
			//set rtoChrgs Id.s
			if(rtoChrgs.productMapId == jQuery("#prHeaderProMapId").val()){
				jQuery("#prRTOChargesId").val(rtoChrgs.rateCashRTOChargesId);
			}
		}//END FOR - RTO
		alert("Date saved successfully.");
	} else {
		alert("Exception occurs. Data not saved.");
	}
}

/**
 * To search RTO charges details for non-priority product(s)
 */
function searchPriorityRTOChrgsDtls(){
	var cashRateId = jQuery("#cashRateHeaderId").val();
	if(!isNull(cashRateId)){
		var url = "./cashRateConfiguration.do?submitName=searchRTOChrgsDtls"
			+"&productMapId="+cashRateId+"&productType="+PRO_CAT_TYPE_P;
		ajaxCallWithoutForm(url, callSearchPriorityRTOChrgsCashRate);
	}
}
//call back function for searchPriorityRTOChrgsDtls
function callSearchPriorityRTOChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		showProcessing();
		//var data = eval('(' + ajaxResp + ')');
		var data = eval(ajaxResp);
		var rtoChrg = data.rtoChargesTO;
		//RTO Chrg Applicable
		if(!isNull(rtoChrg.rtoChargeApplicable)){
			if(rtoChrg.rtoChargeApplicable == YES){//Y
				getDomElementById("rtoChargeApplicableChkPR").checked=true;
				//jQuery("#rtoChargeApplicablePR").val(rtoChrg.rtoChargeApplicable);
			} else {//N
				getDomElementById("rtoChargeApplicableChkPR").checked=false;
			}
		}
		//Same As Slab Rate
		if(!isNull(rtoChrg.sameAsSlabRate)){
			if(rtoChrg.sameAsSlabRate == YES){//Y
				getDomElementById("sameAsSlabRateChkPR").checked=true;
			} else {//N
				getDomElementById("sameAsSlabRateChkPR").checked=false;
			}
		}
		validateRTOChrgs(PRO_CAT_TYPE_P);
		//Discount On Slab
		if(!isNull(rtoChrg.discountOnSlab)){
			jQuery("#discountOnSlabPR").val(rtoChrg.discountOnSlab);
		} else {
			jQuery("#discountOnSlabPR").val("");
		}
		//RTO Chrg Id
		if(!isNull(rtoChrg.rateCashRTOChargesId)){
			jQuery("#prRTOChargesId").val(rtoChrg.rateCashRTOChargesId);
		} else {
			jQuery("#prRTOChargesId").val("");
		}
		//TO disable all if headerStatus "A" - Submitted
		var hStatus = jQuery("#headerStatus").val();
		if(hStatus==ACTIVE){
			disableAllForSubmit();
		}
		hideProcessing();
	}
}


