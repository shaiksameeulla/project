//var isNewRowExist = false;
$(document).ready(function() {
	var oTable = $('#dataGrid').dataTable({
		"sScrollY" : "260",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
	updatedDtlsFormat();
	setDataTableDefaultWidth();
});

/**
 * getSelectedRowCount
 * 
 * @param chkName
 * @returns {Number}
 * @author shahnsha
 */
function getSelectedRowCount(chkName) {
	var form = getDomElementById('pickupRunsheetForm');
	var count = 0;
	for ( var n = 0; n < form.length; n++) {
		if (form[n].name.substr(0, 18) == chkName && form[n].checked) {
			count++;
		}
	}
	return count;
}
/**
 * updatedDtlsFormat
 * 
 * @author shahnsha
 */
function updatedDtlsFormat() {
	var runsheetStatus = "";
	runsheetStatus = getDomElementById('runsheetStatus').value;
	if (runsheetStatus == "C") {
		disableAll();
		$("input[type='checkbox']").prop("disabled", true);
		btnEnable('close');		
	} else if (runsheetStatus == "U") {
		disableAll();
		$("input[type='checkbox']").prop("disabled", true);
		btnEnable('modify');
		btnEnable('close');
	}
}
function btnEnable(btnName) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(BUTTON_DISABLE_CLASS);
	jQuery("#" + btnName).addClass(BUTTON_ENABLE_CLASS);
}
function btnEnable4Grid(btnName) {
	var count = getDomElementById('dataGrid').rows.length;
	for ( var rowId = 1; rowId <= count; rowId++) {
		jQuery("#" + btnName + rowId).attr("disabled", false);
		jQuery("#" + btnName + rowId).removeClass(BUTTON_DISABLE_CLASS);
		jQuery("#" + btnName + rowId).addClass(BUTTON_ENABLE_CLASS);
	}
}
// Modify fn - edit columns : Time, Start CN , End CN
/**
 * fnModify
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function fnModify() {
	var runsheetStatus = "";
	runsheetStatus = getDomElementById('runsheetStatus').value;
	if (runsheetStatus == "C") {
		alert("Cannot be modified since runsheet status is closed");
	} else {
		fnReadOnlyGrid(false);
		buttonEnable();
		btnEnable('save');
		btnEnable('split');
		btnEnable('delete');
	}
}
function fnReadOnlyGrid(flag) {
	$("input[type='checkbox']").prop("disabled", flag);
	var count = getDomElementById('dataGrid').getElementsByTagName(
			'tbody')[0].rows.length;
	for ( var rowId = 1; rowId <= count; rowId++) {
		var startCnNo = getDomElementById('startCnNo' + rowId);
		var quantity = getDomElementById('qty' + rowId);
		var timeHrs = getDomElementById('timeHrs' + rowId);
		var timeMins = getDomElementById('timeMins' + rowId);

		startCnNo.readOnly = flag;
		quantity.readOnly = flag;
		timeHrs.readOnly = flag;
		timeMins.readOnly = flag;
	}
}
// Redirect to generate page
/**
 * fnClose
 * 
 * @author shahnsha
 */
function fnClose() {
	var url = "./generatePickupRunsheet.do?submitName=viewGeneratePickupRunSheet";
	document.pickupRunsheetForm.action = url;
	document.pickupRunsheetForm.submit();
}

/**
 * isChecked
 * 
 * @param obj
 * @param count
 * @author shahnsha
 */
function isChecked(obj, count) {
	if (obj.checked) {
		getDomElementById("updateChk" + count).value = "Y";
	} else {
		getDomElementById("updateChk" + count).value = "N";
	}
}
/**
 * zerosValidation
 * 
 * @param startCnSequence
 * @returns {String}
 * @author shahnsha
 */
/**
 * zerosValidation
 * 
 * @param startCnSequence
 * @returns {String}
 * @author shahnsha
 */
function zerosValidation(startCnSequence) {
	var appender = "";
	for ( var cnt = 0; cnt < startCnSequence.length; cnt++) {
		if (startCnSequence.charAt(cnt) == "0") {
			appender = appender + "0";
		}
	}
	return appender;
}

// Update run sheet functionality
/**
 * fnSaveOrUpdateRunsheet
 * 
 * @author shahnsha
 */
/**
 * fnSaveOrUpdateRunsheet
 * 
 * @author shahnsha
 */
function fnSaveOrUpdateRunsheet() {
	if (isCheckBoxSelectedWithMessage('to.rowCheckBox', 'Runsheet not selected')
			&& mandatoryCheckforUpdate()) {
		var url = "./updatePickupRunsheet.do?submitName=updatePickupRunsheetDetails";
		ajaxJquery(url, 'pickupRunsheetForm', updateCallBack);
	}
}

/**
 * updateCallBack
 * 
 * @param response
 * @author shahnsha
 */
function updateCallBack(response) {
	if (!isNull(response)) {
		var result = jsonJqueryParser(response);
		var success = result["SUCCESS"];
		var failure = result["ERROR"];
		if (!isNull(failure)) {
			alert(failure);
		} else if (!isNull(success)) {
			alert(success);
			refreshPage();			
		} else {
			alert("Problem in saving");
		}
	}
}
function refreshPage() {
	var url = "";
	var RunsheetNo = getDomElementById("pkupRunsheetNo").value;
	if (RunsheetNo != null) {
		progressBar();
		url = './updatePickupRunsheet.do?submitName=getPickupRunsheetDetails&RunsheetNo='
				+ RunsheetNo;
		document.pickupRunsheetForm.action = url;
		document.pickupRunsheetForm.submit();
	}
}
/**
 * mandatoryCheckforUpdate
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function mandatoryCheckforUpdate() {
	var tableee = getDomElementById('dataGrid');

	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.rowCheckBox');
	for ( var i = 1; i < totalRowCount; i++) {
		var rowId = getRowId(cell[i - 1], "chk");
		var startCnNo = getDomElementById('startCnNo' + rowId);
		var oldStartCnNo = getDomElementById('oldStartSerialNumber'+ rowId);
		var oldQuantity = getDomElementById('oldQuantity' + rowId);
		var quantity = getDomElementById('qty' + rowId);

		if (cell[i - 1].checked) {
			var endSIDom = getDomElementById('endCnNo' + rowId);
			var time = getDomElementById('time' + rowId);

			if (!isNull(oldStartCnNo.value)
					&& (isNull(startCnNo.value) && isNull(quantity.value))) {
				continue;
			}
			if (isNull(time.value)) {
				alert("Please provide Time at line :" + rowId);
				setTimeout(function() {
					time.focus();
				}, 10);
				return false;
			}
			if (isNull(startCnNo.value)) {
				alert("Please provide consignment number at line :" + rowId);
				setTimeout(function() {
					startCnNo.focus();
				}, 10);
				return false;
			}
			if (isNull(quantity.value)) {
				alert("Please provide Quantity at line :" + rowId);
				// Commented to fix : mail regd subject- "Pick UP Defects" dated
				// : 6/13/2014
				// getDomElementById('startCnNo' + rowId).value="";
				setTimeout(function() {
					quantity.focus();
				}, 10);
				return false;
			}

			if (isNull(endSIDom.value)) {
				alert("Please provide Start/end serial number at line :"
						+ rowId);
				startCnNo.value = "";
				endSIDom.value = "";
				setTimeout(function() {
					startCnNo.focus();
				}, 10);
				return false;
			}
		} else {
			if ((oldStartCnNo.value != startCnNo.value)
					|| (parseIntNumber(oldQuantity.value) != parseIntNumber(quantity.value))) {
				alert("Please select all modified rows. Line :" + rowId);
				return false;
			}
		}
	}
	return true;
}
/**
 * validateConsignments
 * 
 * @param rowId
 * @returns {Boolean}
 * @author shahnsha
 */
function validateConsignments(rowId) {
	var consgNumberDom = getDomElementById("startCnNo" + rowId);
	var consgNumber = trimString(consgNumberDom.value);
	var endNumberDom = getDomElementById("endCnNo" + rowId);
	var quantity = parseIntNumber(getValueByElementId('qty' + rowId));
	var custCategory = getValueByElementId('custCategory' + rowId);

	if (!isNull(consgNumber)) {
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consignment length should be 12 characters");
			consgNumberDom.value = "";
			setTimeout(function() {
				consgNumberDom.focus();
			}, 10);
			return;
		}
		endNumberDom.value = "";
		// For 'ACC' customer category rate contracts not available so contract validation not required.
		if (!isNull(custCategory) && custCategory.toUpperCase() != "ACC") {
			// REQ # Pickup Contract Integration
			 validateConsignmentProduct(rowId);
		}
		if (!isNull(quantity)) {
			// Changed to fix : mail subject- "Pick UP Defects" dated :
			// 6/13/2014
			/*
			 * endNumberDom.value = ""; getDomElementById('qty' + rowId).value
			 * =""; alert("Quantity cannot be zero"); setTimeout(function() {
			 * getDomElementById('qty' + rowId).focus(); }, 10); return false; }
			 * else {
			 */
			getDomElementById('qty' + rowId).value = quantity;
		} else {
			return;
		}

		var loginOfficeId = getDomElementById('loginOfficeId').value;
		var employeeId = getDomElementById('employeeId').value;
		var customerId = getDomElementById('customerId' + rowId).value;
		var pkupRunsheetNo = getDomElementById('pkupRunsheetNo').value;

		if (isNull(endNumberDom.value)) {
			var endNumber = getEndSerialNumber(consgNumber, quantity);

			if (checkForDuplicateSerialNumber(consgNumber, endNumber, rowId,
					'dataGrid', 'startCnNo', 'endCnNo')) {// duplicate
				// check
				showProcessing();
				url = './updatePickupRunsheet.do?submitName=validateConsignments&startCnNumber='
						+ consgNumber
						+ "&quantity="
						+ quantity
						+ "&officeId="
						+ loginOfficeId
						+ "&employeeId="
						+ employeeId
						+ "&customerId="
						+ customerId
						+ "&pkupRunsheetNo="
						+ pkupRunsheetNo;
				jQuery.ajax({
					url : url,
					success : function(req) {
						printCallBackConsignment(req, rowId);
					}
				});
			}
		}
	} else if (!isNull(quantity)) {
		alert("Please enter start CN number");
		setTimeout(function() {
			consgNumberDom.focus();
		}, 10);
	}
}
// added to fix : mail from- "Reshmi" dated : 7/3/2014
function fnValidateQuantity(rowId) {
	var consgNumber = trimString(getValueByElementId("startCnNo" + rowId));
	var quantity = parseIntNumber(getValueByElementId('qty' + rowId));
	var endNumberDom = getDomElementById("endCnNo" + rowId);
	if (!isNull(consgNumber)) {
		if (isNull(quantity)) {
			endNumberDom.value = "";
			getDomElementById('qty' + rowId).value = "";
			alert("Quantity cannot be zero");
			setTimeout(function() {
				getDomElementById('qty' + rowId).focus();
			}, 10);
			return false;
		} else {
			validateConsignments(rowId);
		}
	} else if (!isNull(quantity)) {
		alert("Please enter start consignment no");
		setTimeout(function() {
			getDomElementById("startCnNo" + rowId).focus();
		}, 10);
	}	
}
/**
 * printCallBackConsignment
 * 
 * @param data
 * @param rowId
 * @author shahnsha
 */
function printCallBackConsignment(data, rowId) {
	var response = data;
	var stSiDom = getDomElementById("startCnNo" + rowId);
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (!isNull(cnValidation.errorMsg)) {
				alert(cnValidation.errorMsg);
				clearSerialNumbers(rowId);
				setTimeout(function() {
					stSiDom.focus();
				}, 10);
			} else if (cnValidation.isValidCN == "Y") {
				var consgNumber = getDomElementById("startCnNo" + rowId).value;
				var quantity = getDomElementById("qty" + rowId).value;
				var endNum = getEndSerialNumber(consgNumber, quantity);
				getDomElementById("endCnNo" + rowId).value = endNum;
				navigateToNextRow(rowId);
			}
		}
	}
	jQuery.unblockUI();
}
function validateConsignmentProduct(rowId) {
	var consgNumberDom = getDomElementById("startCnNo" + rowId);
	var customerCode = getDomElementById('customerCode' + rowId).value;
	var loginCityId = getDomElementById('loginCityId').value;
	var custType = getDomElementById('customerType' + rowId).value;
	var consgNumber = consgNumberDom.value;
	if (!isNull(consgNumber)) {
		var url = './updatePickupRunsheet.do?submitName=validateConsignmentProduct&startCnNumber='
				+ consgNumber
				+ "&customerCode="
				+ customerCode
				+ "&loginCityId=" + loginCityId + "&customerType=" + custType;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackValidateConsignmentProduct(req, rowId);
			}
		});
	}
}
function printCallBackValidateConsignmentProduct(response, rowId) {
	if (!isNull(response)) {
		var consgNumberDom = getDomElementById("startCnNo" + rowId);
		var result = jsonJqueryParser(response);
		var failure = result["ERROR"];
		if (!isNull(failure)) {
			consgNumberDom.value = "";
			alert(failure);
			consgNumberDom.focus();
			return false;
		} else {
			return true;
		}
	}
}
/**
 * clearSerialNumbers
 * 
 * @param rowId
 * @author shahnsha
 */
function clearSerialNumbers(rowId) {
	getDomElementById("startCnNo" + rowId).value = "";
	getDomElementById("endCnNo" + rowId).value = "";
	getDomElementById('qty' + rowId).value = "";
}
function navigateToNextRow(count) {
	var endCnNo = getValueByElementId("endCnNo" + count);
	if (!isNull(endCnNo) && !isNull(count)) {
		var tabLength = getDomElementById('dataGrid')
				.getElementsByTagName('tbody')[0].rows.length;
		count = parseIntNumber(count) + 1;
		if (count <= tabLength)
			$("#timeHrs" + count).focus();
		else
			$("#save").focus();
	}
}
function concatenateHHMM(rowId) {
	var hrs = "00";
	var mins = "00";
	// UAT Dec Fix
	if (!isNull(getValueByElementId("timeHrs" + rowId))) {
		hrs = getValueByElementId("timeHrs" + rowId);
	}
	if (!isNull(getValueByElementId("timeMins" + rowId))) {
		mins = getValueByElementId("timeMins" + rowId);
	}
	if (!compareTimeWithSystemTime(hrs, mins)) {
		alert("Pickup time cannot be greater than system time");
		getDomElementById("timeHrs" + rowId).value = "";
		getDomElementById("timeMins" + rowId).value = "";
		getDomElementById("timeHrs" + rowId).focus();
		return;
	}
	var time = hrs + ":" + mins;
	getDomElementById("time" + rowId).value = time;
	getDomElementById("timeHrs" + rowId).value = hrs;
	getDomElementById("timeMins" + rowId).value = mins;
	// UAT Dec Fix
}
// Validation on time for pick-up runsheet;system time validation is required
// It should not accept greater than system time.
function compareTimeWithSystemTime(hrs, mins) {
	var date = getValueByElementId("systemTime");
	var hrsMins = trimString(date).split(":");
	var sysTimeHrs = hrsMins[0];
	var sysTimeMins = hrsMins[1];
	if (parseInt(hrs, 10) > parseInt(sysTimeHrs, 10)) {
		return false;
	}
	if ((parseInt(hrs, 10) == parseInt(sysTimeHrs, 10))
			&& (parseInt(mins, 10) > parseInt(sysTimeMins, 10))) {
		return false;
	}
	return true;
}
/* Enter key navigation */
function setFocusOnEnterKey(e, rowId) {
	var quantity = getDomElementById('qty' + rowId).value;
	if (isNull(quantity)) {
		getDomElementById("endCnNo" + rowId).value = "";
		setTimeout(function() {
			getDomElementById('qty' + rowId).focus();
		}, 10);
		return false;
	} else {
		return onlyNumberNenterKeyNav(e, 'endCnNo' + rowId);
	}
}
function newEntry(rowId) {
	if (isNull(getValueByElementId("startCnNo" + rowId))) {
		getDomElementById('qty' + rowId).value = "";
	}
}

function fnAddBranchRow(cnt) {
	var rowId = getDomElementById('dataGrid').rows.length;
	$('#dataGrid').dataTable().fnAddData(
		[
		 	'<input type="checkbox" id="chk' + rowId+ '" name="to.rowCheckBox" value="'+ (rowId-1) + '"/>',
			'<div id="serialNo' + rowId + '">' + rowId+ '</div>',
			'<div id="custName'+ rowId+ '"> '+ getDomElementById("custName" + cnt).innerHTML+ ' </div>',
			'<div id="custCode'+ rowId+ '"> '+ getDomElementById("custCode" + cnt).innerHTML+ ' </div>',
			'<div id="pickupDlvLocationName'+ rowId+ '"> '+ getDomElementById("pickupDlvLocationName"+ cnt).innerHTML + ' </div>',
			'<div id="orderNo'+ rowId+ '"> '+ getDomElementById("orderNo" + cnt).innerHTML+ ' </div>',
			'<input type="text" class="txtbox" id="timeHrs'+ rowId+ '" name=to.timeHrs onkeypress="return onlyNumberNenterKeyNav(event, \'timeMins'+ rowId+ '\');" '
				+ 'size="2" maxlength="2" onblur="isValidTimeHHMM(this, \'HH\');concatenateHHMM('+ rowId+ ');" value=""/> : '
				+ '<input type="text" class="txtbox" id="timeMins'+ rowId
				+ '" name=to.timeMins onkeypress="return onlyNumberNenterKeyNav(event,\'startCnNo'+ rowId+ '\');" '
				+ 'size="2" maxlength="2" onblur="isValidTimeHHMM(this, \'MM\');concatenateHHMM('+ rowId + ');" value=""/>',
			'<input type="text" class="txtbox" id="startCnNo'+ rowId+ '" name="to.startCnNo" maxlength="12" size="14" value="" '
				+ 'onkeypress="return callEnterKey(event, document.getElementById(\'qty'+ rowId+ '\'));" onchange="validateConsignments('+ rowId + ');" onfocus="newEntry('+rowId+');" />',
			'<input type="text" class="txtbox" id="qty'+ rowId+ '" name="to.quantity" maxlength="5" size="5" value="" '
				+ 'onkeypress="return onlyNumeric(event);" onchange="fnValidateQuantity('+ rowId + ');" />',
			'<input type="text" class="txtbox" id="endCnNo'+ rowId+ '" name="to.endCnNo" value="" tabindex="-1" maxlength="12" size="14" readonly="true" />'
//			'<a href="#" onclick="isDuplicateCustomerToBeDeleted('+ rowId+ ');"><img name="jsbutton" src="images/delete_icon.gif"></a>'
				+ '<input type="hidden" id="customerId'+ rowId+ '" name="to.customerIds" value="'+ getValueByElementId("customerId" + cnt)+ '"/>'
				+ '<input type="hidden" id="customerCode'+ rowId+ '" name="to.custCode" value="'+ getValueByElementId("customerCode" + cnt)+ '"/>'
				+ '<input type="hidden" id="customerType'+ rowId+ '" name="to.custType" value="'+ getValueByElementId("customerType" + cnt)+ '"/>'
				+ '<input type="hidden" id="custCategory'+ rowId+ '" name="to.custCategory" value="'+ getValueByElementId("custCategory" + cnt)+ '"/>'
				+ '<input type="hidden" id="shippedToCode'+ rowId+ '" name="to.shippedToCode" value="'+ getValueByElementId("shippedToCode" + cnt)+ '"/>'
				+ '<input type="hidden" id="pickupLocationId'+ rowId+ '" name="to.pickupLocationId" value="'+ getValueByElementId("pickupLocationId"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pkupRunsheetDtl'+ rowId+ '" name="to.pickupRunsheetDtlIds" value="'+ getValueByElementId("pkupRunsheetDtl"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pkupRunsheetHeader'+ rowId+ '" name="to.pickupRunsheetHeaderIds" value="'+ getValueByElementId("pkupRunsheetHeader"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pickupType'+ rowId+ '" name="to.pickupType" value="'+ getValueByElementId("pickupType" + cnt)+ '"/>'
				+ '<input type="hidden" id="revPickupOrderDtlId'+ rowId+ '" name="to.revPickupOrderDtlId" value="'+ getValueByElementId("revPickupOrderDtlId"+ cnt)+ '"/>'
				+ '<input type="hidden" id="time'+ rowId+ '" name="to.time" value=""/>'
				+ '<input type="hidden" id="oldStartSerialNumber'+ rowId+ '" name="to.oldStartSerialNumber" value=""/>'
				+ '<input type="hidden" id="oldQuantity'+ rowId+ '" name="to.oldQuantity" value=""/>' 
				+ '<input type="hidden" id="newRow'+ rowId+ '" name="to.isNewRow" value="Y"/>'
				+ '<input type="hidden" id="deleteRow'+ rowId+ '" name="to.deleteRow" value="N"/>']);
}

function fnAddHubRow(cnt) {
	var rowId = getDomElementById('dataGrid').rows.length;
	$('#dataGrid').dataTable().fnAddData(
		[
		 	'<input type="checkbox" id="chk' + rowId+ '" name="to.rowCheckBox" value="'+ (rowId-1) + '"/>',
			'<div id="serialNo' + rowId + '">' + rowId+ '</div>',
			'<div id="branchName'+ rowId+ '"> '+ getDomElementById("branchName" + cnt).innerHTML+ ' </div>',
			'<div id="custName'+ rowId+ '"> '+ getDomElementById("custName" + cnt).innerHTML+ ' </div>',
			'<div id="custCode'+ rowId+ '"> '+ getDomElementById("custCode" + cnt).innerHTML+ ' </div>',
			'<div id="pickupDlvLocationName'+ rowId+ '"> '+ getDomElementById("pickupDlvLocationName"+ cnt).innerHTML + ' </div>',
			'<div id="orderNo'+ rowId+ '"> '+ getDomElementById("orderNo" + cnt).innerHTML+ ' </div>',
			'<input type="text" class="txtbox" id="timeHrs'+ rowId+ '" name=to.timeHrs onkeypress="return onlyNumberNenterKeyNav(event, \'timeMins'+ rowId+ '\');" '
				+ 'size="2" maxlength="2" onblur="isValidTimeHHMM(this, \'HH\');concatenateHHMM('+ rowId+ ');" value=""/> : '
				+ '<input type="text" class="txtbox" id="timeMins'+ rowId+ '" name=to.timeMins onkeypress="return onlyNumberNenterKeyNav(event,\'startCnNo'+ rowId+ '\');" '
				+ 'size="2" maxlength="2" onblur="isValidTimeHHMM(this, \'MM\');concatenateHHMM('+ rowId + ');" value=""/>',
			'<input type="text" class="txtbox" id="startCnNo'+ rowId+ '" name="to.startCnNo" maxlength="12" size="14" value="" '
				+ 'onkeypress="return callEnterKey(event, document.getElementById(\'qty'+ rowId+ '\'));" onchange="validateConsignments('+ rowId + ');" onfocus="newEntry('+rowId+');" />',
			'<input type="text" class="txtbox" id="qty'+ rowId+ '" name="to.quantity" maxlength="5" size="5" value="" '
				+ 'onkeypress="return onlyNumeric(event);" onchange="fnValidateQuantity('+ rowId + ');" />',
			'<input type="text" class="txtbox" id="endCnNo'+ rowId+ '" name="to.endCnNo" value="" tabindex="-1" maxlength="12" size="14" readonly="true"/>'
//			'<a href="#" onclick="fnDeleteCustomer(\'dataGrid\', '+ rowId+ ');"><img name="jsbutton" src="images/delete_icon.gif"></a>'
				+ '<input type="hidden" id="customerId'+ rowId+ '" name="to.customerIds" value="'+ getValueByElementId("customerId" + cnt)+ '"/>'
				+ '<input type="hidden" id="customerCode'+ rowId+ '" name="to.custCode" value="'+ getValueByElementId("customerCode" + cnt)+ '"/>'
				+ '<input type="hidden" id="customerType'+ rowId+ '" name="to.custType" value="'+ getValueByElementId("customerType" + cnt)+ '"/>'
				+ '<input type="hidden" id="custCategory'+ rowId+ '" name="to.custCategory" value="'+ getValueByElementId("custCategory" + cnt)+ '"/>'
				+ '<input type="hidden" id="shippedToCode'+ rowId+ '" name="to.shippedToCode" value="'+ getValueByElementId("shippedToCode" + cnt)+ '"/>'
				+ '<input type="hidden" id="pickupLocationId'+ rowId+ '" name="to.pickupLocationId" value="'+ getValueByElementId("pickupLocationId"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pkupRunsheetDtl'+ rowId+ '" name="to.pickupRunsheetDtlIds" value="'+ getValueByElementId("pkupRunsheetDtl"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pkupRunsheetHeader'+ rowId+ '" name="to.pickupRunsheetHeaderIds" value="'+ getValueByElementId("pkupRunsheetHeader"+ cnt)+ '"/>'
				+ '<input type="hidden" id="pickupType'+ rowId+ '" name="to.pickupType" value="'+ getValueByElementId("pickupType" + cnt)+ '"/>'
				+ '<input type="hidden" id="revPickupOrderDtlId'+ rowId+ '" name="to.revPickupOrderDtlId" value="'+ getValueByElementId("revPickupOrderDtlId"+ cnt)+ '"/>'
				+ '<input type="hidden" id="time'+ rowId+ '" name="to.time" value=""/>'
				+ '<input type="hidden" id="oldStartSerialNumber'+ rowId+ '" name="to.oldStartSerialNumber" value=""/>'
				+ '<input type="hidden" id="oldQuantity'+ rowId+ '" name="to.oldQuantity" value=""/>' 
				+ '<input type="hidden" id="newRow'+ rowId+ '" name="to.isNewRow" value="Y"/>'
				+ '<input type="hidden" id="deleteRow'+ rowId+ '" name="to.deleteRow" value="N"/>']);
}

function fnSplitCustomer() {
	if (isCheckBoxSelectedWithMessage('to.rowCheckBox', 'Runsheet not selected')) {
		if (confirm("Do you want to split the row(s)?")) {
			var box = document.getElementsByName('to.rowCheckBox');
			var chkBoxLen = box.length;
			for ( var i = 0; i < chkBoxLen; i++) {
				var rowId = i + 1;
//				$("#delete" + rowId).show();
				if (box[i].checked) {
					getDomElementById("chk" + rowId).checked = false;
//					$("#deleteTh").show();
					if (getValueByElementId("loginOffTypeCode") == "HO") {
						fnAddHubRow(rowId);
					}else{
						fnAddBranchRow(rowId);
					}
				}
			}
			if (getDomElementById("chkAll").checked) {
				getDomElementById("chkAll").checked = false;
			}
			updateSerialNoVal("dataGrid");
		}		
	}	
}

function updateSerialNoVal(tableId) {
	try {
		var table = getDomElementById(tableId);
//		isNewRowExist = false;
		for ( var i = 1; i < table.rows.length; i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var serialNo = getDomElementById("serialNo" + rowId);
			if (serialNo.innerHTML != i) {
				serialNo.innerHTML = i;
			}
			getDomElementById("chk" + rowId).value = (i-1);
//			var isNewLine = getValueByElementId("newRow" + rowId);
//			if (isNewLine == 'Y') {
//				isNewRowExist = true;
//			}
		}
	} catch (e) {

	}
}

function fnDeleteCustomer(tableId, rowIndex) {
	rowIndex = getDomElementById("serialNo" + rowIndex).innerHTML;
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex - 1);
	updateSerialNoVal(tableId);
//	if (!isNewRowExist) {
//		$("#deleteTh").hide();
//		var table = getDomElementById(tableId);
//		for ( var i = 1; i < table.rows.length; i++) {
//			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
//			$("#delete" + rowId).hide();			
//		}
//	}	
}

function isDuplicateCustomerToBeDeleted(){	
	if (isCheckBoxSelectedWithMessage('to.rowCheckBox', 'Please select runsheet to delete')) {
		if (confirm("Do you want to delete the row(s)?")) {
			var tableee = getDomElementById('dataGrid');
			var totalRowCount = tableee.rows.length;		
			var box = document.getElementsByName('to.rowCheckBox');
			var appendRowId = null;
			var saveRunsheet = 'N';
			var k = 0;
			for ( var i = totalRowCount-1; i > 0; i--) {
				var rowId = getRowId(box[i - 1], "chk");
				if (box[i - 1].checked) {
					var isNewCustRow = getValueByElementId('newRow' + rowId);
					if (isNewCustRow == 'N') {
						if (k > 0) {
							appendRowId = appendRowId + ", " + rowId;
						} else {
							appendRowId = rowId;
						}
						k++;
					} else {
						// flag for db level delete
						var oldStartCnNo = getDomElementById('oldStartSerialNumber'+ rowId);
						var oldQuantity = getDomElementById('oldQuantity'+ rowId);
						if (!isNull(oldStartCnNo.value) && !isNull(oldQuantity.value)) {
							getDomElementById("deleteRow" + rowId).value = "Y";
							saveRunsheet = 'Y';
						} else {
							// screen level delete
							fnDeleteCustomer('dataGrid', rowId);
						}
					}
				}
			}
			if(!isNull(appendRowId)){
				alert("Actual customer not supposed to delete Line: "+appendRowId);
			}
			if(saveRunsheet == 'Y'){
				var url = "./updatePickupRunsheet.do?submitName=updatePickupRunsheetDetails";
				ajaxJquery(url, 'pickupRunsheetForm', updateCallBack);
			}
		}
		$("input[type='checkbox']").prop("checked", false);
	}
}