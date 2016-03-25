var oldValue = "";
var ERROR_FLAG = "ERROR";
var PREV_DOC_DT = "";

/**
 * isJsonResponce
 * 
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp) {
	var responseText = null;
	try {
		responseText = jsonJqueryParser(ObjeResp);
	} catch (e) {

	}
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			return true;
		}
	}
	return false;
}
/**
 * To submit form for given url
 * 
 * @param url
 * @param action
 */
function submitForm(url, action) {
	if (confirm("Do you want to " + action + " details?")) {
		document.getElementById("Submit").disabled = true;
		document.expenseEntryForm.action = url;
		document.expenseEntryForm.submit();
	}
}

/**
 * To submit the form without prompt the message
 * 
 * @param url
 */
function submitWithoutPrompt(url) {
	document.expenseEntryForm.action = url;
	document.expenseEntryForm.submit();
}


/**
 * To redirect respective expense screen i.e Office, Employee, Consig.
 * 
 * @param domElement
 */
function getExpenseScreen(domElement) {
	var domValue = domElement.value;
	var submitName = "viewExpenseOffice";
	if (domValue == EX_FOR_OFFICE) {// BRANCH
		submitName = "viewExpenseOffice";
	} else if (domValue == EX_FOR_EMP) {// EMP
		submitName = "viewExpenseEmployee";
	} else if (domValue == EX_FOR_CN) {// CN
		submitName = "viewExpenseConsignment";
	}
	var url = "./expenseEntry.do?submitName=" + submitName;
	document.expenseEntryForm.action = url;
	document.expenseEntryForm.submit();
}

/**
 * validate the TxNo. format i.e. Office Code + Tx. Code + 6 digit (MUMB EX
 * 123456)
 * 
 * @param domElement
 * @returns {Boolean}
 */
function validateTxNo(domElement) {
	var domValue = domElement.value.trim();
	if (!isNull(domValue)) {
		if (domValue.length == 12) {
			var officeCode = getDomElementById("loginOfficeCode").value;
			var offCodePattern = /^[A-Z0-9]{0,4}$/;
			var txCode = TX_CODE_EX;// EX
			var numpattern = /^[0-9]{6,20}$/;
			if (offCodePattern.test(domValue.substring(0, 4).toUpperCase())
					&& domValue.substring(4, 6).toUpperCase() == txCode
							.toUpperCase()
					&& numpattern.test(domValue.substring(6))) {
				if (domValue.substring(0, 4).toUpperCase() == officeCode
						.toUpperCase()) {
					return true;
				} else {
					alert("Transaction number does not belong to this office");
					domElement.value = "";
					setTimeout(function() {
						domElement.focus();
					}, 10);
				}
			} else {
				alert("Transaction number format is not correct");
				domElement.value = "";
				setTimeout(function() {
					domElement.focus();
				}, 10);
			}
		} else {
			alert("Transaction number should be 12 characters");
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
		}
	}
}

/**
 * To check whether it is new expense entry or not.
 * 
 * @returns {Boolean}
 */
function isNewExpenseEntry() {
	var expenseId = getDomElementById("expenseId");
	if (isNull(expenseId.value)) {
		return true;
	}
	return false;
}

/**
 * To check mandatory field for save
 * 
 * @returns {Boolean}
 */
function checkMandatoryForSave() {
	var documentDt = getDomElementById("documentDate");
	var expenseType = getDomElementById("expenseType");
	var finalAmt = getDomElementById("finalAmount");
	var expFor = getDomElementById("expenseFor").value;
	var txRemarks = getDomElementById("txRemarks");

	if (!isNull(documentDt) && isNull(documentDt.value)) {
		alert("Please select Document date");
		setTimeout(function() {
			documentDt.focus();
		}, 10);
		return false;
	}
	if (isNull(expenseType.value)) {
		alert("Please select Type of expense");
		setTimeout(function() {
			expenseType.focus();
		}, 10);
		return false;
	}
	if (!checkMandatoryForExpMode()) {
		return false;
	}
	if (expFor != EX_FOR_EMP && isNull(finalAmt.value)) {// No need check
		// amount for
		// EMPLOYEE
		alert("Please provide Amount");
		finalAmt.value = '';
		setTimeout(function() {
			finalAmt.focus();
		}, 10);
		return false;
	}
	if (!isNull(txRemarks) && isNull(txRemarks.value)) {
		alert("Please provide remarks");
		setTimeout(function() {
			txRemarks.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * To check mandatory for mode of expense
 * 
 * @returns {Boolean}
 */
function checkMandatoryForExpMode() {
	var expenseMode = getDomElementById("expenseMode").value;
	if (!isNull(expenseMode) && expenseMode == EX_MODE_CHQ) {
		var chequeNumber = getDomElementById("chequeNumber");
		var chequeDate = getDomElementById("chequeDate");
		var bank = getDomElementById("bank");
		var bankBranchName = getDomElementById("bankBranchName");

		if (isNull(chequeNumber.value)) {
			alert("Please provide cheque number");
			setTimeout(function() {
				chequeNumber.focus();
			}, 10);
			return false;
		}
		if (isNull(chequeDate.value)) {
			alert("Please select cheque date");
			setTimeout(function() {
				chequeDate.focus();
			}, 10);
			return false;
		}
		if (isNull(bank.value)) {
			alert("Please select bank name");
			setTimeout(function() {
				bank.focus();
			}, 10);
			return false;
		}
		if (isNull(bankBranchName.value)) {
			alert("Please provide bank branch name");
			setTimeout(function() {
				bankBranchName.focus();
			}, 10);
			return false;
		}
	}
	return true;
}

/**
 * To validate for expense mode whether it is CHEQUE or not.
 * 
 * @param domElement
 */
function validateForExpMode(domValue) {
	if (!isNull(domValue) && domValue == EX_MODE_CHQ) {
		enableOrDisableForExpMode(false);
		$("#chequeDt").show();
		getDomElementById("chequeNumber").focus();
	} else {
		enableOrDisableForExpMode(true);
		$("#chequeDt").hide();
		clearForExpMode();
	}
}

/**
 * To enable or disable for expense mode
 * 
 * @param flag
 */
function enableOrDisableForExpMode(flag) {
	getDomElementById("chequeNumber").disabled = flag;
	getDomElementById("chequeDate").disabled = flag;
	getDomElementById("bank").disabled = flag;
	getDomElementById("bankBranchName").disabled = flag;
}

/**
 * To clear the expense mode relate details like, cheque no., cheque date, bank
 * name, branch name.
 */
function clearForExpMode() {
	getDomElementById("chequeNumber").value = "";
	getDomElementById("chequeDate").value = "";
	getDomElementById("bank").value = "";
	getDomElementById("bankBranchName").value = "";
}

/**
 * To disable all the elements
 */
function disableForSubmitted() {
	disableAll();
	jQuery(":input").attr("disabled", true);
	buttonDisabled("Save", "btnintform");
	buttonDisabled("Submit", "btnintform");
	buttonEnabled("Cancel", "btnintformbigdis");
	getDomElementById("txNumber").setAttribute("tabindex", "-1");
	if (!isNull(getDomElementById("documentDate")))
		getDomElementById("documentDate").setAttribute("tabindex", "-1");
	getDomElementById("finalAmount").setAttribute("tabindex", "-1");
}

/**
 * To disable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonDisabled(btnName, styleclass) {
	if (!isNull(getDomElementById(btnName))) {
		jQuery("#" + btnName).attr("disabled", true);
		jQuery("#" + btnName).removeClass(styleclass);
		jQuery("#" + btnName).removeAttr("tabindex");
		jQuery("#" + btnName).addClass("btnintformbigdis");
	}
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
	jQuery("#" + btnName).removeAttr("tabindex");
	jQuery("#" + btnName).addClass("btnintform");
}

/**
 * @Desc To make element read only (text field)
 * @param domElement
 */
function disableElement(domElement) {
	domElement.readOnly = true;
	domElement.setAttribute("tabindex", "-1");
}

/**
 * To disable all the elements for validate (for CASH)
 */
function disableForCash() {
	disableAll();
	jQuery(":input").attr("disabled", true);
	buttonEnabled("CreditNote", "btnintformbigdis");
	buttonEnabled("Validate", "btnintformbigdis");
	getDomElementById("txNumber").setAttribute("tabindex", "-1");
	if (!isNull(getDomElementById("documentDate")))
		getDomElementById("documentDate").setAttribute("tabindex", "-1");
	getDomElementById("finalAmount").setAttribute("tabindex", "-1");
}

/**
 * To create drop down for list
 * 
 * @param domElementId
 * @param list
 */
function createDropDownForEmp(domElementId, empList) {
	var content = getDomElementById(domElementId);
	var opt;
	content.innerHTML = "";
	opt = document.createElement("OPTION");
	opt.value = "";
	opt.appendChild(document.createTextNode("---SELECT---"));
	content.appendChild(opt);
	if (!isNull(empList)) {
		var list = eval('(' + empList + ')');
		$.each(list, function(index, value) {
			var option;
			option = document.createElement("OPTION");
			option.value = this.employeeId;
			var lbl = this.firstName + " " + this.lastName + " - "
					+ this.empCode;
			option.appendChild(document.createTextNode(lbl));
			content.appendChild(option);
		});
	}
}

/**
 * To get employee of logged in office
 */
function getEmployeeOfLoggedInOffice(domElement, rowId) {
	createDropDownForEmp(domElement + rowId, employeeList);
}

/**
 * To set amount format i.e. 12345.67
 * 
 * @param domElement
 */
function setAmountFormat(domElement) {
	if (!isEmptyAmount(domElement.value)) {
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
		domElement.value = "";
	}
}

/**
 * To set amount format and set zero if NULL
 * 
 * @param domElement
 */
/*
 * function setAmountFormatZero(domElement){
 * if(!isEmptyAmount(domElement.value)){ var domVal = domElement.value; var
 * amtValue = domVal.split("."); if(isNull(amtValue[0])){ amtValue[0] = "0"; }
 * if(isNull(amtValue[1])){ amtValue[1] = "00"; } else
 * if(amtValue[1].length==1){ amtValue[1] = amtValue[1] + "0"; } else
 * if(amtValue[1].length>2){ amtValue[1] = amtValue[1].substring(0,2); }
 * domElement.value = amtValue[0] + "." +amtValue[1]; } else {
 * domElement.value="0.00"; } }
 */
/**
 * To check amount on header and sum of amount in grid should be same
 * 
 * @returns {Boolean}
 */
function checkAmountBreakUp() {
	var finalAmt = getDomElementById("finalAmount");
	var sumOfAmt = 0;
	var isOctroiGL = getDomElementById("isOctroiGL").value;
	if (isOctroiGL == YES) {
		for ( var i = 1; i < rowCount; i++) {
			var amt = getDomElementById("amounts" + i).value;
			if (!isNull(amt))
				sumOfAmt += parseFloat(amt);
		}
	} else {
		for ( var i = 1; i < rowCount; i++) {
			var totalAmt = getDomElementById("totals" + i).value;
			if (!isNull(totalAmt))
				sumOfAmt += parseFloat(totalAmt);
		}
	}
	finalAmt.value = parseFloat(finalAmt.value);
	sumOfAmt = parseFloat(sumOfAmt);
	setAmountFormat(finalAmt);
	if (finalAmt.value == sumOfAmt) {
		return true;
	}
	alert("Header Amount & Break Up Amount(s) Does Not Match.");
	finalAmt.focus();
	return false;
}

/**
 * To get mode of expense on change of expense type
 * 
 * @param domElement
 */
function getModeOfExpense(domElement) {
	var domValue = domElement.value;
	if (!isNull(domValue)) {
		var url = "./expenseEntry.do?submitName=getGLDtlsById&glId=" + domValue;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateModeOfExpense(req);
			}
		});
	}
}

/**
 * To populate mode of expense according to GL payment type
 * 
 * @param ajaxResp
 */
function populateModeOfExpense(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var data = eval('(' + ajaxResp + ')');
		var modeId = data.paymentModeId;
		// var nature = data.nature;
		var isEmpGL = data.isEmpGL;
		var isOctroiGL = data.isOctroiGL;

		/* Set isEmpGL flag */
		if (!isNull(isEmpGL) && isEmpGL == "Y") {
			getDomElementById("isEmpGL").value = "Y";
		} else {
			getDomElementById("isEmpGL").value = "N";
		}

		/* Set GL Nature flag */
		/*
		 * if(!isNull(nature) && nature=="N"){
		 * getDomElementById("isNegativeGLNature").value="Y"; } else {
		 * getDomElementById("isNegativeGLNature").value="N"; }
		 */

		/* Set Octroi GL flag */
		if (!isNull(isOctroiGL) && isOctroiGL == "Y") {
			getDomElementById("isOctroiGL").value = "Y";
		} else {
			getDomElementById("isOctroiGL").value = "N";
		}
		// Check for Auto Calculate for OCTROI GL
		validateHeaderForOctroiGL();

		if (EX_MODE_CHQ == modeId || EX_MODE_CASH == modeId) {
			getDomElementById("expenseMode").disabled = true;
			jQuery("#expenseMode").val(modeId);
		} else {
			jQuery("#expenseMode").val(EX_MODE_CASH);
			getDomElementById("expenseMode").disabled = false;
		}
		validateForExpMode(modeId);
	}
	hideProcessing();
}

/**
 * To disable/enable for validate
 */
function enableOrDisableForValidate(flag) {
	var EXP_FOR = getDomElementById("expenseMode").value;
	if (EXP_FOR == EX_MODE_CHQ) {
		enableOrDisableForCheque(flag);
		// getDomElementById("expenseType").disabled = !flag;
	} else {
		// if(IS_CR_NT==CR_NT_YES){
		enableOrDisableForCash(flag);
		// } else {
		// disableForCash();
		// }
	}
}

/**
 * To disable/enable for cheque
 * 
 * @param flag
 */
function enableOrDisableForCheque(flag) {
	getDomElementById("txNumber").disabled = flag;
	getDomElementById("postingDate").disabled = flag;
	getDomElementById("expenseFor").disabled = flag;
	getDomElementById("expenseType").disabled = flag;
	getDomElementById("expenseMode").disabled = flag;
}

/**
 * To disable/enable for cash
 * 
 * @param flag
 */
function enableOrDisableForCash(flag) {
	enableOrDisableForCheque(flag);
	if (!isNull(getDomElementById("documentDate")))
		getDomElementById("documentDate").disabled = flag;
	if (!isNull(getDomElementById("txRemarks")))
		getDomElementById("txRemarks").disabled = flag;
}

/**
 * To validate Expense
 * 
 * @param action
 */
function validateExpense(action) {
	if (checkMandatoryForSave()) {
		if (action == 'validate') {
			showProcessing();
			// To disable validate button during validation of expense screen
			getDomElementById("Validate").disabled = true;
			enableOrDisableForValidate(false);
			enableOrDisableForExpMode(false);
			if (getDomElementById("expenseFor").value == "E") {
				for ( var i = 1; i < rowCount; i++) {
					enableOrDisableEmpGrid(i, false);
				}
			} else if (getDomElementById("expenseFor").value == "C") {
				if (IS_CR_NT != CR_NT_YES && !checkAmountBreakUp()) {
					return false;
				}
				for ( var i = 1; i < rowCount; i++) {
					enableOrDisableCNGrid(i, false);
				}
			}
			getDomElementById("expenseStatus").value = STATUS_VALIDATED;
			var url = "./expenseEntry.do?submitName=validateExpenseDtls";
			jQuery.ajax({
				url : url,
				type : "POST",
				data : jQuery("#expenseEntryForm").serialize(),
				success : function(req) {
					callBackValidateExpense(req);
				}
			});
		} else if (action == 'creditNote') {
			getDomElementById("CreditNote").disabled = true;
			enableOrDisableForCrNt(false);
			var txNo = getDomElementById("txNumber").value;
			var expFor = getDomElementById("expenseFor").value;
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var url = "expenseEntry.do?submitName=searchForValidateExpense"
					+ "&txNumber=" + txNo + "&expenseFor=" + expFor
					+ "&officeId=" + loginOfficeId + "&isCrNote=" + CR_NT_YES;
			submitWithoutPrompt(url);
		}
	}
}

/**
 * To call back method for validate expense
 * 
 * @param ajaxResp
 */
function callBackValidateExpense(ajaxResp) {
	if (!isNull(ajaxResp)) {
		if (isJsonResponce()) {
			return;
		}
	}
	var data = ajaxResp;
	if (!isNull(data) && data == "SUCCESS") {
		alert("Expense Validated Successfully.");
	} else {
		alert("Expense Not Validated. Please Try Again");
	}
	// To Enable validate button after validation of expense screen
	hideProcessing();
	getDomElementById("Validate").disabled = false;
	// window.opener.location.reload();
	window.opener.searchValidateExpDtls();
	self.close();
}

/**
 * To validate the Consignment number format
 * 
 * @param consgNumberObj
 * @returns {Boolean}
 */
function isValidConsignment(consgNumberObj) {
	var flag = true;
	if (!isNull(trimString(consgNumberObj.value))) {
		var consgNumber = "";
		consgNumber = trimString(consgNumberObj.value);
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consignment length should be 12 character");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			flag = false;
		} else {
			var numpattern = /^[0-9]+$/;
			var letters = /^[A-Za-z]+$/;
			var alphaNumeric = /^[A-Za-z0-9]+$/;
			consgNumber = trimString(consgNumberObj.value);
			if (!consgNumber.substring(0, 1).match(letters)
					|| (!consgNumber.substring(4, 5).match(letters) && !consgNumber
							.substring(1, 4).match(alphaNumeric))
					|| !consgNumber.substring(1, 4).match(alphaNumeric)
					|| !numpattern.test(consgNumber.substring(5))) {
				alert("Consignment number format is not correct");
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				flag = false;
			}
		}
	} else {
		flag = false;
	}
	return flag;
}

/**
 * To enable or disable for credit note
 * 
 * @param flag
 */
function enableOrDisableForCrNt(flag) {
	getDomElementById("txNumber").disabled = flag;
	getDomElementById("expenseFor").disabled = flag;
	if (IS_CR_NT != CR_NT_YES) {
		getDomElementById("finalAmount").disabled = false;
	}
}

/**
 * To check whether grid is empty of not during header value(s) (Type of
 * Expense) change.
 * 
 * @param newObj
 * @param expenseFor
 */
function checkGridEmpty(newObj, expenseFor) {
	var isFilled = false;
	var flag = true;
	for ( var i = 1; i < rowCount; i++) {
		var gridObj;
		if (expenseFor == EX_FOR_CN) {
			gridObj = getDomElementById("consgNos" + i);
		} else {// else if(expenseFor==EX_FOR_EMP) {
			gridObj = getDomElementById("employeeIds" + i);
		}
		if (!isNull(gridObj) && !isNull(gridObj.value)) {
			isFilled = true;
			flag = confirm("Detail(s) already entered in the grid.\n\nDo you still want to make the change(s) in header?");
			break;
		}
	}
	if (flag) {
		if (isFilled) {
			showProcessing();
			$('#expenseEntryTable').dataTable().fnClearTable();
			rowCount = 1;// declared in respective .js file(s).
			addRow();// declared in respective .js file(s).
			hideProcessing();
		}
		getModeOfExpense(newObj);
		oldValue = newObj.value;
	} else {
		getDomElementById("expenseType").value = oldValue;
	}
}

/**
 * To validate Header For Grid (EMP & CN)
 * 
 * @returns {Boolean}
 */
function validateHeaderForGrid() {
	var expenseType = getDomElementById("expenseType");
	if (isNull(expenseType.value)) {
		alert("Please select Type of expense");
		setTimeout(function() {
			expenseType.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * To enable finalAmount for validation screen if mode of payment is CHEQUE
 */
function enableFinalAmtForChqAndValidate() {
	var expFor = getDomElementById("expenseMode").value;
	if (expFor == EX_MODE_CHQ
			&& getDomElementById("isValidateScreen").value == IS_VALIDATE_YES) {
		getDomElementById("finalAmount").readOnly = false;
		getDomElementById("expenseType").disabled = false;
	}
}

/**
 * To validate duplicate values in grid or not.
 * 
 * @param domObj
 * @param domId
 * @returns {Boolean}
 */
function isDuplicateValInGrid(domObj, domId) {
	var currRowId = getRowId(domObj, domId);
	for ( var i = 1; i < rowCount; i++) {
		if (currRowId == i)
			continue;
		if (domObj.value == getDomElementById(domId + i).value) {
			var inputValue = "";
			if (getDomElementById("expenseFor").value == EX_FOR_EMP) {
				var employeeText = domObj.options[domObj.selectedIndex].text;
				inputValue = "Employee " + employeeText;
				clearEmpRow(currRowId);
			} else {
				inputValue = "Consignment No. " + domObj.value;
			}
			alert(inputValue + " is duplicated at line : " + i);
			domObj.value = "";
			setTimeout(function() {
				domObj.focus();
			}, 10);
			return true;
		}
	}
	return false;
}

/**
 * To set document date
 * 
 * @param obj
 */
function setDocumentDate(obj) {
	show_calendar('documentDate', obj.value);
	// getDomElementById("documentDate").focus();
}

/**
 * To validate document date
 */
function validateDocDate(dtObj) {
	var arrDocDt = dtObj.value.split("/");// Document date
	var docDt = new Date(arrDocDt[2], arrDocDt[1], arrDocDt[0]);

	var arrTodayDt = TODAY_DATE.split("/");// Current date
	var today = new Date(arrTodayDt[2], arrTodayDt[1], arrTodayDt[0]);

	var temp = dtObj.value;

	if (docDt > today) {
		alert("Document date should not greater than Posting date.");
		if (isNull(PREV_DOC_DT))
			PREV_DOC_DT = TODAY_DATE;
		dtObj.value = PREV_DOC_DT;
		return false;
	}
	PREV_DOC_DT = temp;
}

/**
 * To clear Txn field if new transaction is creating.
 * 
 * @param expId
 * @param txNoId
 */
function clearIfNewTxn(expId, txNoId) {
	var expenseId = getDomElementById(expId).value;
	if (isNull(expenseId)) {
		var txNo = getDomElementById(txNoId);
		if (txNo.length != 14) {
			txNo.value = "";
		}
	}
}

/** Check for Auto Calculate for Octroi GL */
function validateHeaderForOctroiGL() {
	var expenseFor = getDomElementById("expenseFor").value;
	if (expenseFor == EX_FOR_CN) {
		var isOctroiGL = getDomElementById("isOctroiGL").value;
		if (isOctroiGL == "Y") {
			// Make header amount read only and auto calculate enabled
			getDomElementById("finalAmount").readOnly = true;
		} else {
			// Make header amount editable and auto calculate disabled
			getDomElementById("finalAmount").readOnly = false;
		}
	}
}
