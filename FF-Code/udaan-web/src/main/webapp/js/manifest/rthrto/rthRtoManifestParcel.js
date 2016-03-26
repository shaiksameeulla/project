$(document).ready(function() {
	var oTable = $('#rthRtoManifestTable').dataTable({
		"sScrollY" : "220",
		"sScrollX" : "100%",
		"sScrollXInner" : "170%",
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
	addRows();
	getDomElementById("manifestNo").focus();
	setDataTableDefaultWidth();
});
var isSearch = false;
var cnt = "1";
var printUrl;
var printFlag = "N";

function addRows() {
	var reasonOptions = getReasons();
	$('#rthRtoManifestTable')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox" id="chk' + cnt
									+ '" name="chkBoxName" value="' + cnt
									+ '" onclick="fnEditRow(' + cnt + ');"/>',

							'<div id="serialNo' + cnt + '">' + cnt + '</div>',

							'<input type="text" class="txtbox" id="consgNumber'
									+ cnt
									+ '" name="to.consgNumbers" maxlength="12" onblur="getConsignmentDetails(this)" onkeypress="return callEnterKey(event, document.getElementById(\'pendingReason'
									+ cnt
									+ '\'));"/><input type="hidden" id="consignmentId'
									+ cnt
									+ '" name="to.consignmentIds"/><input type="hidden" id="consignmentManifestId'
									+ cnt
									+ '" name="to.consignmentManifestIds"/>',

							'<input type="text" class="txtbox" id="noOfPcs'
									+ cnt
									+ '" name="noOfPcs" size="3" readonly=true/>',

							'<input type="text" id="pincode'
									+ cnt
									+ '" name="pincode" class="txtbox" size="6" readonly=true/>',

							'<input type="text" name="weightKg" id="weightKg'
									+ cnt
									+ '" class="txtbox width30"  size="11" readonly=true/><span class="lable">.</span><input type="text" name="weightGm" id="weightGm'
									+ cnt
									+ '" class="txtbox width30"  size="11" readonly=true/><input type="hidden" id="cnWeight'
									+ cnt + '" name="to.cnWeights"/>',

							'<input type="text" id="content'
									+ cnt
									+ '" name="content" class="txtbox" readonly=true />',

							'<input type="text" id="paperwork'
									+ cnt
									+ '" name="paperwork" class="txtbox" readonly=true />',

							'<input type="text" id="recievedDate'
									+ cnt
									+ '" name="to.receivedDate" class="txtbox" size="16"/>',

							'<input type="text" id="codAmt'
									+ cnt
									+ '" name="codAmt" class="txtbox" size="6" readonly=true/>',

							'<input type="text" id="toPayAmt'
									+ cnt
									+ '" name="toPayAmt" class="txtbox" size="6" readonly=true/>',

							'<select id="pendingReason'
									+ cnt
									+ '" name="to.reasonIds" class="txtbox"  onblur="fnCalculateRate('
									+ cnt
									+ ');" onkeypress="return callEnterKey(event, document.getElementById(\'remarks'
									+ cnt
									+ '\'));"><option value="">-- SELECT --</option>'
									+ reasonOptions + ' </select>',

							'<input type="text" id="remarks'
									+ cnt
									+ '" name="to.remarks" class="txtbox" onkeypress="return addNewRowByEnterKeyPress(event, '
									+ cnt + ');"/>'

					]);
	cnt++;
	updateSerialNoVal("rthRtoManifestTable");
	return cnt - 1;
}
// Before scanning consignments Baglock number should be validated.
/*
 * function validateCnEntryMandatoryFields(rowCount) { var flag = true; if
 * (rowCount == 1) { var bagLockNo = getDomElementById("bagLockNo"); if
 * (isNull(trimString(bagLockNo.value))) { clearFocusAlertMsg(bagLockNo,"Please
 * enter Bag Lock No."); flag = false; } } return flag; }
 */

function fnCalculateRate(rowId) {
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_RTO) {
		calcRtoParcelRate(rowId);
	}
}

function getConsignmentDetails(consgNumberObj) {
	if (isDuplicateConsignment(consgNumberObj)) {
		var manifestType = getDomElementById("manifestType").value;
		var originOfficeId = getDomElementById("originOfficeId").value;
		var bagLockNoObj = getDomElementById("bagLockNo");
		var destCityObj = "";
		var destCityId = "";
		if (manifestType == MANIFEST_TYPE_RTO) {
			destCityObj = getDomElementById("destCity");
			destCityId = destCityObj.value;
			if (isNull(destCityId)) {
				var msg = "Please select station";
				consgNumberObj.value = "";
				clearFocusAlertMsg(destCityObj, msg);
				return;
			}
		}
		// Baglock number mandatory
		if (isNull(trimString(bagLockNoObj.value))) {
			consgNumberObj.value = "";
			clearFocusAlertMsg(bagLockNoObj, "Please enter Bag Lock No.");
			return;
		}

		showProcessing();
		var url = './rthRtoManifestParcel.do?submitName=getConsignmentDetails&consignmentNumber='
				+ consgNumberObj.value
				+ "&manifestType="
				+ manifestType
				+ "&originOfficeId="
				+ originOfficeId
				+ "&destCityId="
				+ destCityId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req, consgNumberObj);
			}
		});
	}
}

function printCallBackConsignment(data, consgNumberObj) {
	if (!isNull(data)) {
		var response = eval('(' + data + ')');
		var ROW_COUNT = getRowId(consgNumberObj, "consgNumber");
		if (!isNull(response.errorMsg)) {
			clearFocusAlertMsg(consgNumberObj, response.errorMsg);
			clearRows(ROW_COUNT);
		} else {
			populateGridDetails(ROW_COUNT, response);
		}
	}
	hideProcessing();
}
function populateGridDetails(ROW_COUNT, response) {
	if (!isSearch) {
		var manifestType = getDomElementById("manifestType").value;
		if (manifestType == MANIFEST_TYPE_RTO) {
			destRegionVal = getValueByElementId("destRegion");
			destCityVal = getValueByElementId("destCity");
		}
	}
	getDomElementById("consgNumber" + ROW_COUNT).value = response.consgNumber;
	getDomElementById("consignmentId" + ROW_COUNT).value = response.consignmentId;
	getDomElementById("consignmentManifestId" + ROW_COUNT).value = response.consignmentManifestId;

	getDomElementById("noOfPcs" + ROW_COUNT).value = response.numOfPc;
	getDomElementById("pincode" + ROW_COUNT).value = response.pincode;

	var actWeight = parseFloat(response.actualWeight).toFixed(3);
	getDomElementById("cnWeight" + ROW_COUNT).value = actWeight;
	getDomElementById("weightKg" + ROW_COUNT).value = actWeight.split(".")[0];
	getDomElementById("weightGm" + ROW_COUNT).value = actWeight.split(".")[1];

	getDomElementById("content" + ROW_COUNT).value = response.cnContent;
	getDomElementById("paperwork" + ROW_COUNT).value = response.cnPaperWorks;
	getDomElementById("recievedDate" + ROW_COUNT).value = response.receivedDate;
	if (!isNull(response.codAmt)) {
		getDomElementById("codAmt" + ROW_COUNT).value = response.codAmt
				.toFixed(2);
	}
	if (!isNull(response.toPayAmt)) {
		getDomElementById("toPayAmt" + ROW_COUNT).value = response.toPayAmt
				.toFixed(2);
	}

	if (!isNull(response.reasonTO))
		showDropDownBySelected("pendingReason" + ROW_COUNT,
				response.reasonTO.reasonId);
	getDomElementById("remarks" + ROW_COUNT).value = response.remarks;
}
function clearRows(ROW_COUNT) {
	getDomElementById("consgNumber" + ROW_COUNT).value = "";
	getDomElementById("consignmentId" + ROW_COUNT).value = "";
	getDomElementById("consignmentManifestId" + ROW_COUNT).value = "";

	getDomElementById("noOfPcs" + ROW_COUNT).value = "";
	getDomElementById("pincode" + ROW_COUNT).value = "";

	getDomElementById("weightKg" + ROW_COUNT).value = "";
	getDomElementById("weightGm" + ROW_COUNT).value = "";

	getDomElementById("content" + ROW_COUNT).value = "";
	getDomElementById("paperwork" + ROW_COUNT).value = "";
	getDomElementById("recievedDate" + ROW_COUNT).value = "";

	getDomElementById("codAmt" + ROW_COUNT).value = "";
	getDomElementById("toPayAmt" + ROW_COUNT).value = "";

	showDropDownBySelected("pendingReason" + ROW_COUNT, "");
	getDomElementById("remarks" + ROW_COUNT).value = "";
}

// validate Current Row for New Row Creation
function isValidRow(rowId) {
	var consignmentNoObj = getDomElementById("consgNumber" + rowId);
	var pendingReasonObj = getDomElementById("pendingReason" + rowId);
	// Null check for space(s) in remarks fields
	var remarks = getDomElementById("remarks" + rowId);
	if (!isNull(remarks) && isNull(trimString(remarks.value))) {
		remarks.value = "";
	}
	if (isNull(consignmentNoObj.value)) {
		clearFocusAlertMsg(consignmentNoObj, "Please enter consignment number");
		return false;
	} else if (isNull(pendingReasonObj.value)) {
		clearFocusAlertMsg(pendingReasonObj, "Please enter pending Reason");
		return false;
	} else
		return true;
}
function addNewRowByEnterKeyPress(e, rowId) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13 && isValidRow(rowId)) {
		if (!isSearch) {
			cnt = parseIntNumber(rowId) + 1;
			// Make the row read only
			fnEnableOrDisableRow(rowId, true);
			if (isNull(getDomElementById("consgNumber" + cnt))) {
				var rowIdd = addRows();
				setTimeout(function() {
					getDomElementById("consgNumber" + rowIdd).focus();
				}, 10);
			}
			// return false;
		}
	}
}
function fnEnableOrDisableRow(rowId, flag) {
	getDomElementById("consgNumber" + rowId).readOnly = flag;
	getDomElementById("remarks" + rowId).readOnly = flag;
	getDomElementById("pendingReason" + rowId).disabled = flag;
	getDomElementById("chk" + rowId).checked = !flag;
}
// Moved to rthRtoManifest.js
/*
 * function fnEditRow(rowId) { if (getDomElementById("chk" + rowId).checked) {
 * fnEnableOrDisableRow(rowId, false); } else { fnEnableOrDisableRow(rowId,
 * true); } }
 */
function isValidHeader() {
	var isValid = true;
	var manifestNoObj = getDomElementById("manifestNo");
	var destOfficeObj = getDomElementById("destOffice");
	var bagLockNo = getDomElementById("bagLockNo");
	if (isNull(manifestNoObj.value)) {
		clearFocusAlertMsg(manifestNoObj, "Please enter manifest no");
		isValid = false;
	} else if (isNull(destOfficeObj.value)) {
		clearFocusAlertMsg(destOfficeObj, "Please select destination");
		isValid = false;
	} else if (isNull(trimString(bagLockNo.value))) {
		clearFocusAlertMsg(bagLockNo, "Please enter Bag Lock No.");
		isValid = false;
	}
	return isValid;
}
function validateRows() {
	var validRow = false;
	var cnList = document.getElementsByName("to.consgNumbers");
	for ( var i = 0; i < cnList.length; i++) {
		//var cnVal = getDomElementById("consgNumber" + i).value;
		if (!isNull(cnList[i])) {
			var cnRowId = getRowId(cnList[i], "consgNumber");
			var cnVal = getDomElementById("consgNumber" + cnRowId).value;
			if(!isNull(cnVal)){
				validRow = isValidRow(cnRowId);
				if (!validRow) {
					break;
				}
			}
		}
	}
	if (cnList.length == 1 && isNull(getDomElementById("consgNumber1").value)) {
		clearFocusAlertMsg(getDomElementById("consgNumber1"),
				"Atleast one consignment need to be scanned");
	}
	return validRow;
}
function saveOrUpdateRtohManifest() {
	dropdownEnable();
	if (isValidHeader() && validateRows()) {
		url = './rthRtoManifestParcel.do?submitName=isRtohNoManifested';
		showProcessing();
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#rthRtoManifestForm").serialize(),
			success : function(req) {
				printCallBackNoManifested(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert(textStatus);
			}
		});
	}
}
function printCallBackNoManifested(data) {
	if (data != 'N') {
		var responsetext = jsonJqueryParser(data);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			clearFocusAlertMsg(getDomElementById("manifestNo"), error);
			hideProcessing();
		}
	} else {
		performSave4RtohManifest();
	}
}
function performSave4RtohManifest() {
	showProcessing();
	var url = './rthRtoManifestParcel.do?submitName=saveOrUpdateRthRtoManifestParcel';
	jQuery.ajax({
		url : url,
		type : "POST",
		data : jQuery("#rthRtoManifestForm").serialize(),
		success : function(req) {
			printCallBackManifestSave(req);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			alert("Server error : " + errorThrown);
		}
	});
}
function printCallBackManifestSave(data) {
	if (!isNull(data)) {
		var response = jsonJqueryParser(data);
		if (!isNull(response.transMsg)) {
			alert(response.transMsg);
			clearPage();
		} else {
			if (!isNull(response.errorMsg)) {
				alert(response.errorMsg);
			} else
				alert("Error Occured : Manifest not saved successfully.");
		}
	}
	hideProcessing();
}
function clearPage() {
	var url = "";
	var manifestType = getDomElementById("manifestType").value;
	if (!isNull(manifestType)) {
		if (manifestType == MANIFEST_TYPE_RTH)
			url = "./rthRtoManifestParcel.do?submitName=viewRthManifestParcel";
		else if (manifestType == MANIFEST_TYPE_RTO)
			url = "./rthRtoManifestParcel.do?submitName=viewRtoManifestParcel";
	}
	document.rthRtoManifestForm.action = url;
	document.rthRtoManifestForm.submit();
}
function searchManifest() {
	var manifestNo = getDomElementById("manifestNo").value;
	if (!isNull(manifestNo)) {
		url = './rthRtoManifestParcel.do?submitName=searchRTOHManifestDetails';
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#rthRtoManifestForm").serialize(),
			success : function(req) {
				printCallBackManifestSearch(req);
			}
		});
	} else {
		clearFocusAlertMsg(getDomElementById("manifestNo"),
				"Please provide Manifest No.");
	}
}
function printCallBackManifestSearch(data) {
	var response = jsonJqueryParser(data);
	if (!isNull(response.errorMsg)) {
		clearFocusAlertMsg(getDomElementById("manifestNo"), response.errorMsg);
	} else if (!isNull(response)) {
		// to prepare iFrame
		// prepareIframe();
		isSearch = true;
		populateHeaderDetails(response);
		var detailTO = response.rthRtoDetailsTOs;
		var noofRows = detailTO.length;
		for ( var rowCnt = 1; rowCnt <= noofRows; rowCnt++) {
			if (rowCnt > 1) {
				cnt = rowCnt;
				addRows();
			}
			populateGridDetails(rowCnt, detailTO[rowCnt - 1]);
		}
		inputDisable();
		dropdownDisable();
		disableAllCheckBox();
		jQuery("#" + "saveBtn").addClass("btnintformbigdis");
		jQuery("#" + "deleteBtn").addClass("btnintformbigdis");
	}
	hideProcessing();
}
function disableAllCheckBox() {
	$("input[type='checkbox']").prop("disabled", true);
}
function populateHeaderDetails(response) {
	getDomElementById("manifestDate").value = response.manifestDate;
	getDomElementById("manifestProcessId").value = response.manifestProcessId;
	getDomElementById("manifestId").value = response.manifestId;
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_RTH) {
		if(LOGIN_OFF_TYPE == "BO"){
			//Branch
			showDropDownBySelected("destOffice", response.destOfficeTO.officeId);
		}else{
			//Hub
			createDropDown("destCity", response.destCityTO.cityId,
					response.destCityTO.cityName);
			createDropDown("destOffice", response.destOfficeTO.officeId,
					response.destOfficeTO.officeName);
		}		
	} else if (manifestType == MANIFEST_TYPE_RTO) {
		showDropDownBySelected("destRegion", response.destRegionTO.regionId);
		createDropDown("destCity", response.destCityTO.cityId,
				response.destCityTO.cityName);
		createDropDown("destOffice", response.destOfficeTO.officeId,
				response.destOfficeTO.officeName);
	}
	// Added By Himal
	getDomElementById("bagLockNo").value = response.bagLockNo;
}

function deleteRows() {
	try {
		var table = document.getElementById("rthRtoManifestTable");
		var rowCount = table.rows.length;
		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					clearRows(i);
					chkbox.checked = "";
					alert("Cannot delete the default row");
					break;
				}
				deleteRow("rthRtoManifestTable", i - 1);
				rowCount--;
				i--;
			}
		}
		getDomElementById("checkAll").checked = false;
	} catch (e) {
		alert(e);
	}
}

function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
	updateSerialNoVal("rthRtoManifestTable");
}
function checkAllBoxes(checkedVal) {
	var box = document.getElementsByName('chkBoxName');
	if (box != null && box.length > 0) {
		for ( var i = 1; i <= box.length; i++) {
			// if (!isNull(getValueByElementId("consgNumber" + i))) {
			var chkboxId = i - 1;
			if (checkedVal == true) {
				box[chkboxId].checked = true;
			} else if (checkedVal == false) {
				box[chkboxId].checked = false;
			}
			fnEditRow(i);
			// }
		}
	}
}

function printManifest() {
	var manifestNoObj = document.getElementById("manifestNo");
	if (!isNull(manifestNoObj.value)) {
		var manifestNumber = document.getElementById("manifestNo").value;
		var manifestType = document.getElementById("manifestType").value;
		if (!isNull(manifestNumber) && !isNull(manifestType)) {
			url = './rthRtoManifestParcel.do?submitName=printRthRtoManifestParcelDtls&manifestNumber='
					+ manifestNumber + '&manifestType=' + manifestType;
			window
					.open(url, 'myPopUp',
							'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
		}
	} else {
		clearFocusAlertMsg(manifestNoObj, "Please enter manifest number");
	}
}
function prepareIframe() {
	// to prepare iFrame
	var url2;
	var manifestNumber = document.getElementById("manifestNo").value;
	var manifestType = document.getElementById("manifestType").value;
	if (!isNull(manifestNumber) && !isNull(manifestType)) {
		url2 = './rthRtoManifestParcel.do?submitName=printRthRtoManifestParcelDtls&manifestNumber='
				+ manifestNumber + '&manifestType=' + manifestType;
	}
	printUrl = url2;
	printIframe(printUrl);
}
function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	var myFrame = document.getElementById("iFrame");
	if (myFrame) {
		printFlag = "Y";
	}
	hideProcessing();
}