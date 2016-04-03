var rowCount = 1;
var eachRowAmount = new Array();

/**
 * Add row to grid table on page load
 */
function addRow() {
	$('#expenseEntryTable')
			.dataTable()
			.fnAddData(
					[
							rowCount,
							'<select name="to.employeeIds" id="employeeIds'
									+ rowCount
									+ '" class="selectBox width170" onfocus="validateHeaderForGrid();" onchange="checkNullForEmp('
									+ rowCount
									+ ');" onkeypress="return callEnterKey(event,document.getElementById(\'amounts'
									+ rowCount
									+ '\'));"><option value="">---SELECT---</option></select>',
							'<input type="text" name="to.amounts" id="amounts'
									+ rowCount
									+ '" class="txtbox width110" maxlength="5" onfocus="validateHeaderForGrid();" onkeypress="return onlyNumberAndEnterKeyNav(event,this,\'remarks'
									+ rowCount
									+ '\');" onblur="setFinalAmount(this);" />',
							'<input type="text" name="to.remarks" id="remarks'
									+ rowCount
									+ '" class="txtbox width170" maxlength="50" onfocus="validateHeaderForGrid();" onkeypress="fnAddNewRow(event,this);" />\
		 <input type="hidden" name="to.expenseEntriesIds" id="expenseEntriesIds'
									+ rowCount
									+ '" />\
		 <input type="hidden" name="to.positions" id="positions'
									+ rowCount + '" value="' + rowCount + '"/>', ]);
	getEmployeeOfLoggedInOffice('employeeIds', rowCount);
	// getDomElementById("employeeIds"+rowCount).focus();
	rowCount++;
}

/**
 * To execute at page startup
 */
function expenseEmployeeStartup() {
	validateForExpMode(getDomElementById("expenseMode").value);
	if (isNewExpenseEntry()) {
		addRow();
	} else {
		var expStatus = getDomElementById("expenseStatus").value;
		var isValidateScreen = getDomElementById("isValidateScreen").value;
		disableElement(getDomElementById("txNumber"));
		if (IS_CR_NT != CR_NT_YES) {
			var list = eval('(' + empDtls + ')');
			for ( var i = 1; i <= parseFloat(NO_OF_ROWS); i++) {
				addRow();
				setEmpRowDetails(list[i - 1], i);
			}
		}
		if (getDomElementById("expenseMode").value == EX_MODE_CHQ)
			enableOrDisableForExpMode(false);
		if (expStatus == STATUS_SUBMITTED || expStatus == STATUS_VALIDATED) {
			if (!isNull(isValidateScreen)
					&& isValidateScreen == IS_VALIDATE_YES) {
				enableOrDisableForValidate(true);
				if (IS_CR_NT != CR_NT_YES) {
					for ( var i = 1; i < rowCount; i++) {
						enableOrDisableEmpGrid(i, true);
					}
				}
			} else {
				disableForSubmitted();
				getDomElementById("Cancel").focus();
			}
		} else {
			addRow();
		}
		setAmountFormat(getDomElementById("finalAmount"));
		if (IS_CR_NT == CR_NT_YES || expStatus == STATUS_OPENED) {
			if (expStatus != STATUS_OPENED) {
				getDomElementById("finalAmount").readOnly = false;
				getDomElementById("oldExpId").value = getDomElementById("expenseId").value;
				getDomElementById("expenseId").value = "";
			} else {
				var glPaymentType = getDomElementById("glPaymentType").value;
				if (glPaymentType == EX_MODE_CHQ
						|| glPaymentType == EX_MODE_CASH)
					getDomElementById("expenseMode").disabled = true;
			}
		} else {
			getDomElementById("finalAmount").readOnly = true;
		}
		setAmountFormat(getDomElementById("finalAmount"));
		enableFinalAmtForChqAndValidate();
		oldValue = getDomElementById("expenseType").value;
	}
}

/**
 * To check mandatory for add new row
 * 
 * @param rowId
 * @returns {Boolean}
 */
function checkMandatoryForAddRow(rowId) {
	var empNm = getDomElementById("employeeIds" + rowId);
	var amt = getDomElementById("amounts" + rowId);
	var remark = getDomElementById("remarks" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull(empNm.value)) {
		alert("Please select  Employee Name " + lineNum);
		setTimeout(function() {
			empNm.focus();
		}, 10);
		return false;
	}
	if (isNull(amt.value)) {
		alert("Please provide Amount " + lineNum);
		setTimeout(function() {
			amt.focus();
		}, 10);
		return false;
	}
	if (isNull(remark.value)) {
		alert("Please provide Remark " + lineNum);
		setTimeout(function() {
			remark.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * The function to add new row
 * 
 * @param domElement
 */
function fnAddNewRow(e, domElement) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var currentRowId = getRowId(domElement, 'remarks');
		var tbl = document.getElementById('expenseEntryTable');
		var totalRowCount = tbl.rows.length - 1;
		if (parseInt(currentRowId) == totalRowCount) {

			// var glNature = getDomElementById("isNegativeGLNature").value;
			// if(checkMandatoryForAddRow(currentRowId) && (isNull(glNature) ||
			// glNature!="Y")){
			var isEmpGL = getDomElementById("isEmpGL").value;
			if (checkMandatoryForAddRow(currentRowId)
					&& (isNull(isEmpGL) || isEmpGL != "Y")) {
				addRow();
				getDomElementById("employeeIds" + (rowCount - 1)).focus();
			}
		} else {
			var nextRowId = parseInt(currentRowId) + 1;
			getDomElementById("employeeIds" + nextRowId).focus();
		}
	}
}

/**
 * To save or update the expense details
 * 
 * @param action
 */
function saveOrUpdateExpenseDtls(action) {
	if (checkMandatoryForSave() && validateEmpGrid()) {
		var expMode = getDomElementById("expenseMode").value;
		if (expMode != EX_MODE_CHQ) {
			getDomElementById("chequeDate").value = "";
		}
		if (action == 'save') {
			getDomElementById("expenseStatus").value = STATUS_OPENED;
		} else if (action == 'submit') {
			getDomElementById("expenseStatus").value = STATUS_SUBMITTED;
		}
		getDomElementById("expenseMode").disabled = false;
		var url = "./expenseEntry.do?submitName=saveOrUpdateExpenseDtls";
		submitForm(url, action);
	}
}

/**
 * To search the details from database
 */
function searchExpenseDtls() {
	var txNo = getDomElementById("txNumber");
	if (!isNull(txNo.value.trim())) {
		var url = "./expenseEntry.do?submitName=searchExpenseDtls";
		submitWithoutPrompt(url);
	} else {
		alert("Please provide Transaction Number");
		txNo.value = "";
		setTimeout(function() {
			txNo.focus();
		}, 10);
	}
}

/**
 * To clear screen
 * 
 * @param action
 */
function clearScreen(action) {
	var url = "./expenseEntry.do?submitName=viewExpenseEmployee";
	submitForm(url, action);
}

/**
 * To set employee details to grid
 * 
 * @param list
 * @param rowId
 */
function setEmpRowDetails(list, rowId) {
	jQuery("#employeeIds" + rowId).val(list.employeeId);
	jQuery("#amounts" + rowId).val(list.amount);
	setFinalAmount(getDomElementById("amounts" + rowId));
	// setAmountFormat(getDomElementById("amounts"+rowId));
	jQuery("#remarks" + rowId).val(list.remark);
	jQuery("#expenseEntriesIds" + rowId).val(list.expenseEntriesId);
}

/**
 * To set final amount to header
 * 
 * @param domElement
 */
function setFinalAmount(domElement) {
	var rowId = getRowId(domElement, "amounts");
	var finalAmt = 0;
	if (!isEmptyAmount(domElement.value)) {
		setAmountFormat(domElement);
		eachRowAmount[rowId] = domElement.value;
	} else {
		eachRowAmount[rowId] = 0;
		domElement.value = "";
	}
	for ( var i = 1; i < rowCount; i++) {
		if (isNull(eachRowAmount[i]))
			eachRowAmount[i] = 0;
		finalAmt = parseFloat(finalAmt) + parseFloat(eachRowAmount[i]);
	}
	jQuery("#finalAmount").val(finalAmt);
	setAmountFormat(getDomElementById("finalAmount"));
}

/**
 * To enable & disable Emp grid
 * 
 * @param rowId
 * @param flag
 */
function enableOrDisableEmpGrid(rowId, flag) {
	getDomElementById("employeeIds" + rowId).disabled = flag;
	getDomElementById("amounts" + rowId).disabled = flag;
	getDomElementById("remarks" + rowId).disabled = flag;
}

/**
 * To check mandatory for EMP Grid
 * 
 * @returns {Boolean}
 */
function validateEmpGrid() {
	for ( var i = 1; i < rowCount; i++) {
		if (i == 1 || !isNull(getDomElementById("employeeIds" + i).value)) {
			if (!validateSelectRow(i)) {
				return false;
			}
		}
	}
	return true;
}

/**
 * To check mandatory for EMP row by its row id
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectRow(rowId) {
	var empId = getDomElementById("employeeIds" + rowId);
	var amt = getDomElementById("amounts" + rowId);
	var remark = getDomElementById("remarks" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(empId.value)) {
		alert("Please select Employee" + atLine);
		setTimeout(function() {
			empId.focus();
		}, 10);
		return false;
	}
	if (isNull(amt.value)) {
		alert("Please provide amount" + atLine);
		setTimeout(function() {
			amt.focus();
		}, 10);
		return false;
	}
	if (isNull(remark.value)) {
		alert("Please provide Remark" + atLine);
		setTimeout(function() {
			remark.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * To check whether employee drop down has NULL selected value or not if yes
 * then clear row
 * 
 * @param rowId
 */
function checkNullForEmp(rowId) {
	var empIdObj = getDomElementById("employeeIds" + rowId);
	if (isNull(empIdObj.value)) {
		clearEmpRow(rowId);
	} else if (!isDuplicateValInGrid(empIdObj, "employeeIds")) {
		return false;
	}
}

/**
 * To clear row
 * 
 * @param rowId
 */
function clearEmpRow(rowId) {
	getDomElementById("employeeIds" + rowId).value = "";
	getDomElementById("amounts" + rowId).value = "";
	getDomElementById("remarks" + rowId).value = "";
	getDomElementById("expenseEntriesIds" + rowId).value = "";// hidden
}
