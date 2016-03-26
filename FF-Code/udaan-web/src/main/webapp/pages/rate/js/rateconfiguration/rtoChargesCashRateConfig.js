/**
 * Startup function for RTO charges
 */
function rtoChrgsStartup(){
	searchRTOChrgsDtls();
}

/**
 * To save RTO charges details
 */
function saveOrUpdateRTOChrgsDtls(){
	if(validateRTOChrgsTab(PRO_CAT_TYPE_N)){
		jQuery("#productCatType").val(PRO_CAT_TYPE_N);
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateRTOChrgsDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSaveRTOChrgsCashRate);
	}
}
//call back function for saveOrUpdateRTOChrgsDtls
function callSaveRTOChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = eval('(' + ajaxResp + ')');
		//RTO Charges
		for(var i=0; i<data.rtoChrgsTOList.length; i++){
			var rtoChrgs = data.rtoChrgsTOList[i];
			//set rtoChrgs Id.s
			if(rtoChrgs.productMapId == jQuery("#coHeaderProMapId").val()){
				jQuery("#coRTOChargesId").val(rtoChrgs.rateCashRTOChargesId);
			} else if(rtoChrgs.productMapId == jQuery("#arHeaderProMapId").val()){
				jQuery("#arRTOChargesId").val(rtoChrgs.rateCashRTOChargesId);
			} else if(rtoChrgs.productMapId == jQuery("#trHeaderProMapId").val()){
				jQuery("#trRTOChargesId").val(rtoChrgs.rateCashRTOChargesId);
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
function searchRTOChrgsDtls(){
	var cashRateId = jQuery("#cashRateHeaderId").val();
	if(!isNull(cashRateId)){
		var url = "./cashRateConfiguration.do?submitName=searchRTOChrgsDtls"
			+"&productMapId="+cashRateId+"&productType="+PRO_CAT_TYPE_N;
		ajaxCallWithoutForm(url, callSearchRTOChrgsCashRate);
	}
}
//call back function for searchRTOChrgsDtls
function callSearchRTOChrgsCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		showProcessing();
		//var data = eval('(' + ajaxResp + ')');
		var data = eval(ajaxResp);
		var rtoChrg = data.rtoChargesTO;
		//RTO Chrg Applicable
		if(!isNull(rtoChrg.rtoChargeApplicable)){
			if(rtoChrg.rtoChargeApplicable == YES){//Y
				getDomElementById("rtoChargeApplicableChk").checked=true;
				//jQuery("#rtoChargeApplicable").val(rtoChrg.rtoChargeApplicable);
			} else {//N
				getDomElementById("rtoChargeApplicableChk").checked=false;
			}
		}
		//Same As Slab Rate
		if(!isNull(rtoChrg.sameAsSlabRate)){
			if(rtoChrg.sameAsSlabRate == YES){//Y
				getDomElementById("sameAsSlabRateChk").checked=true;
			} else {//N
				getDomElementById("sameAsSlabRateChk").checked=false;
			}
		}
		validateRTOChrgs(PRO_CAT_TYPE_N);
		//Discount On Slab
		if(!isNull(rtoChrg.discountOnSlab)){
			jQuery("#discountOnSlab").val(rtoChrg.discountOnSlab);
		} else {
			jQuery("#discountOnSlab").val("");
		}
		//RTO Chrg Id
		if(!isNull(rtoChrg.rateCashRTOChargesId)){
			jQuery("#coRTOChargesId").val(rtoChrg.rateCashRTOChargesId);
		} else {
			jQuery("#coRTOChargesId").val("");
		}
		//TO disable all if headerStatus "A" - Submitted
		var hStatus = jQuery("#headerStatus").val();
		if(hStatus==ACTIVE){
			disableAllForSubmit();
		}
		hideProcessing();
	}
}


