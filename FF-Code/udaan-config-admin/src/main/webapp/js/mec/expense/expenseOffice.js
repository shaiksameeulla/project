/**
 * To execute at page startup
 */
function expenseOfficeStartup(){
	validateForExpMode(getDomElementById("expenseMode").value);
	if(!isNewExpenseEntry()){
		var expStatus = getDomElementById("expenseStatus").value;
		var isValidateScreen = getDomElementById("isValidateScreen").value;
		disableElement(getDomElementById("txNumber"));
		if(getDomElementById("expenseMode").value==EX_MODE_CHQ)
			enableOrDisableForExpMode(false);
		if(expStatus==STATUS_SUBMITTED || expStatus==STATUS_VALIDATED){
			if(!isNull(isValidateScreen) && isValidateScreen==IS_VALIDATE_YES){
				enableOrDisableForValidate(true);
			} else {
				disableForSubmitted();	
			}
		}
		if(IS_CR_NT==CR_NT_YES || expStatus==STATUS_OPENED){
			getDomElementById("finalAmount").readOnly = false;
			if(expStatus!=STATUS_OPENED){
				getDomElementById("oldExpId").value = getDomElementById("expenseId").value;
				getDomElementById("expenseId").value = "";
				var txRemarks = getDomElementById("txRemarks");
				if (!isNull(txRemarks)) {
					txRemarks.disabled = false;
				}
			} else {
				var glPaymentType = getDomElementById("glPaymentType").value;
				if(glPaymentType==EX_MODE_CHQ || glPaymentType==EX_MODE_CASH)
					getDomElementById("expenseMode").disabled=true;
			}
		} else {
			getDomElementById("finalAmount").readOnly = true;
		}
		setAmountFormat(getDomElementById("finalAmount"));
		enableFinalAmtForChqAndValidate();
	}
}

/**
 * To save or update the expense details
 * 
 * @param action
 */
function saveOrUpdateExpenseDtls(action){
	var finalAmt = getDomElementById("finalAmount");
	if(checkMandatoryForSave() && isEmptyZeroAmount(finalAmt)){
		clearIfNewTxn("expenseId", "txNumber");
		var expMode = getDomElementById("expenseMode").value;
		if(expMode!=EX_MODE_CHQ){
			getDomElementById("chequeDate").value = "";
		}
		if(action=='save'){
			getDomElementById("expenseStatus").value = STATUS_OPENED;
		} else if(action=='submit') {
			getDomElementById("expenseStatus").value = STATUS_SUBMITTED;
			enableOrDisableForExpMode(false);
		}
		getDomElementById("expenseMode").disabled = false;
		var url = "./expenseEntry.do?submitName=saveOrUpdateExpenseDtls";
		submitForm(url, action);
	}
}

/**
 * To search the details from database
 */
function searchExpenseDtls(){
	var txNo = getDomElementById("txNumber");
	if(!isNull(txNo.value.trim())){
		var url = "./expenseEntry.do?submitName=searchExpenseDtls";
		submitWithoutPrompt(url);
	} else {
		alert("Please provide Transaction Number");
		txNo.value="";
		setTimeout(function(){txNo.focus();}, 10);
	}
}

/**
 * To clear screen
 * 
 * @param action
 */
function clearScreen(action){
	var url = "./expenseEntry.do?submitName=viewExpenseOffice";
	submitForm(url, action);
}

