var rowCount = 1;
var serialNo = 1;
var isSearch = false;
var printUrl;
var printFlag = "N";
/**
 * To add row(s) to grid.
 */
function addRows() {
	$('#rthRtoManifestTable')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox" name="chkBoxName" id="chk'
									+ rowCount + '" onclick="fnEditRow('
									+ rowCount + ');" tabindex="-1"/>',
							'<div id="serialNo' + rowCount + '">' + serialNo
									+ '</div>',
							'<input type="text" name="to.consgNumbers" id="consgNumber'
									+ rowCount
									+ '" class="txtbox width100" maxlength="12" onblur="getConsignmentDtls(this);" onkeypress="return callEnterKey(event,document.getElementById(\'reasonId'
									+ rowCount + '\'));"/>',
							'<input type="text" name="weight" id="weight'
									+ rowCount
									+ '" class="txtbox width60" readonly="readonly" tabindex="-1"/>\
		 <input type="hidden" name="to.cnWeights" id="cnWeight'
									+ rowCount + '" />',
							'<input type="text" name="to.receivedDate" id="receivedDate'
									+ rowCount
									+ '" class="txtbox width75" readonly="readonly" tabindex="-1"/>',
							'<input type="text" name="codAmt" id="codAmt'
									+ rowCount
									+ '" class="txtbox width75" readonly="readonly" tabindex="-1"/>',
							'<input type="text" name="toPayAmt" id="toPayAmt'
									+ rowCount
									+ '" class="txtbox width75" readonly="readonly" tabindex="-1"/>',
							'<select name="to.reasonIds" id="reasonId'
									+ rowCount
									+ '" class="selectBox width130"  onblur="fnCalculateRate('
									+ rowCount
									+ ');" onkeypress="return callEnterKey(event,document.getElementById(\'remarks'
									+ rowCount
									+ '\'));"/><option value="">---SELECT---</option>'
									+ reasonOptions + '</select>',
							'<input type="text" name="to.remarks" id="remarks'
									+ rowCount
									+ '" class="txtbox width155" maxlength="100" onkeypress="return addNewRowOnEnterKeyPress(event,'
									+ rowCount
									+ ');" />\
		 <input type="hidden" name="to.consignmentIds" id="consignmentId'
									+ rowCount
									+ '" />\
		 <input type="hidden" name="to.consignmentManifestIds" id="consignmentManifestId'
									+ rowCount + '" />' ]);
	serialNo++;
	rowCount++;
	updateSrlNo("rthRtoManifestTable");
}

function fnCalculateRate(rowId) {
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_RTO) {
		calcRtoRate(rowId);
	}
}
/**
 * RTH DOX Start up
 */
function rthDoxStartUp() {
	var manifestId = getDomElementById("manifestId").value;
	if (isNull(manifestId)) {
		setTimeout(function() {
			getDomElementById("manifestNo").focus();
		}, 10);
		addRows();
	} else {
		showProcessing();

		// prepareIframe();
		if(LOGIN_OFF_TYPE == "HO"){
			//Hub
			getDomElementById("destCity").value = destCityValue;
			createDropDown("destOffice", destOfficeValue, destOfficeName);
		}else{
			//Branch
			getDomElementById("destOffice").value = destOfficeValue;
		}		
		isSearch = true;
		disableForSearch();
		hideProcessing();

	}
}

/**
 * RTO DOX Start up
 */
function rtoDoxStartUp() {
	var manifestId = getDomElementById("manifestId").value;
	if (isNull(manifestId)) {
		setTimeout(function() {
			getDomElementById("manifestNo").focus();
		}, 10);
		addRows();
	} else {
		showProcessing();
		// prepareIframe();

		showDropDownBySelected("destRegion", DEST_REGION_ID);
		createDropDown("destCity", DEST_CITY_ID, DEST_CITY_NAME);
		createDropDown("destOffice", DEST_OFFICE_ID, DEST_OFFICE_NAME);
		isSearch = true;

		disableForSearch();
		hideProcessing();
	}
}

/**
 * To clear the page
 * 
 * @param action
 */
function clearPage() {
	// if(confirm("Do you really want to clear the page ?")){
	var submitName;
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_RTH) {
		submitName = "viewRthManifestDox";
	} else if (manifestType == MANIFEST_TYPE_RTO) {
		submitName = "viewRtoManifestDox";
	}
	var url = "./rthRtoManifestDox.do?submitName=" + submitName;
	document.rthRtoManifestDoxForm.action = url;
	document.rthRtoManifestDoxForm.submit();
	// }
}

/**
 * Add new row on Enter Key press
 * 
 * @param e
 * @param rowId
 * @returns {Boolean}
 */
function addNewRowOnEnterKeyPress(e, rowId) {
	var key;
	if (window.event) {
		key = window.event.keyCode;// IE
	} else {
		key = e.which;// firefox
	}
	/*
	 * if (!((key >= 97 && key <= 122) || (key >= 65 && key <= 90) || (key >= 48 &&
	 * key <= 57) || key == 13 || key == 8 || key == 9 || key == 0)) { // To
	 * avoid special characters return false; }
	 */
	if (key == 13 && validateRowDtls(rowId)) {
		var table = getDomElementById("rthRtoManifestTable");
		var tableRowCount = table.rows.length;
		if (rowId == (rowCount - 1) || tableRowCount == 2
				|| isLastRow(tableRowCount, rowId)) {
			addRows();
			var nextRowId = rowCount - 1;
			/*
			 * setTimeout(function() { getDomElementById("consgNumber" +
			 * nextRowId).focus(); }, 10);
			 */
			$("#consgNumber" + nextRowId).focus();
		}
		fnEnableOrDisableRow(rowId, true);
		return false;
	}
}

/**
 * To check is last Row
 * 
 * @param tableRowCount
 * @param rowId
 * @returns {Boolean}
 */
function isLastRow(tableRowCount, rowId) {
	var count = 0;
	for ( var i = 1; i < rowCount; i++) {
		if (!isNull(getDomElementById("consgNumber" + i)) && i <= rowId)
			count++;
	}
	if ((tableRowCount - 1) == count) {
		return true;
	} else {
		return false;
	}
}

/**
 * To validate row details
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateRowDtls(rowId) {
	var consgNo = getDomElementById("consgNumber" + rowId);
	var reason = getDomElementById("reasonId" + rowId);
	var remarks = getDomElementById("remarks" + rowId);
	if (!isNull(consgNo) && isNull(consgNo.value)) {
		clearFocusAlertMsg(consgNo, "Please enter consignment number");
		return false;
	}
	if (!isNull(reason)) {
		reason.disabled = false;
	}
	if (!isNull(reason) && isNull(reason.value)) {
		clearFocusAlertMsg(reason, "Please select pending reason");
		return false;
	}
	if (!isNull(remarks) && isNull(trimString(remarks.value))) {
		remarks.value = "";
	}
	reason.disabled = true;
	return true;
}

/**
 * To get consignment details to screen
 * 
 * @param consgNoObj
 */
function getConsignmentDtls(consgNoObj) {
	if (!consgNoObj.readOnly && isDuplicateConsignment(consgNoObj)) {
		var manifestType = getDomElementById("manifestType").value;
		var originOfficeId = getDomElementById("originOfficeId").value;
		var destCityObj = "";
		var destCityId = "";
		if (manifestType == MANIFEST_TYPE_RTO) {
			destCityObj = getDomElementById("destCity");
			destCityId = destCityObj.value;
			if (isNull(destCityId)) {
				var msg = "Please select station";
				consgNoObj.value = "";
				clearFocusAlertMsg(destCityObj, msg);
				return;
			}
		}
		var url = './rthRtoManifestDox.do?submitName=getConsignmentDtls&consignmentNumber='
				+ consgNoObj.value
				+ '&manifestType='
				+ manifestType
				+ '&originOfficeId='
				+ originOfficeId
				+ "&destCityId="
				+ destCityId;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req, consgNoObj);
			}
		});
	}
}

/**
 * Ajax call back method for populating consignment(s) details
 * 
 * @param ajaxResp
 * @param consgNoObj
 */
function printCallBackConsignment(ajaxResp, consgNoObj) {
	var rowId = getRowId(consgNoObj, "consgNumber");
	// var manifestType = getDomElementById("manifestType").value;
	if (!isNull(ajaxResp)) {
		var response = eval('(' + ajaxResp + ')');
		if (!isNull(response.errorMsg)) {
			clearFocusAlertMsg(consgNoObj, response.errorMsg);
			clearRow(rowId);
		} else {
			populatingConsgDtls(response, rowId);
		}
	} else {
		clearFocusAlertMsg(consgNoObj, "Invalid Consignment Number");
		clearRow(rowId);
	}
	hideProcessing();
}

/**
 * To populating consg details
 * 
 * @param response
 */
function populatingConsgDtls(response, rowId) {
	getDomElementById("consgNumber" + rowId).value = response.consgNumber;
	getDomElementById("consignmentId" + rowId).value = response.consignmentId;// hidden
	// field
	getDomElementById("consignmentManifestId" + rowId).value = response.consignmentManifestId;// hidden
	// field

	var actWeight = parseFloat(response.actualWeight).toFixed(3);
	getDomElementById("weight" + rowId).value = actWeight;
	getDomElementById("cnWeight" + rowId).value = actWeight;

	getDomElementById("receivedDate" + rowId).value = response.receivedDate;
	getDomElementById("codAmt" + rowId).value = response.codAmt;
	getDomElementById("toPayAmt" + rowId).value = response.toPayAmt;

	if (!isNull(response.reasonTO))
		getDomElementById("reasonId" + rowId).value = response.reasonTO.reasonId;
}

/**
 * To clear row by rowId
 * 
 * @param rowId
 */
function clearRow(rowId) {
	getDomElementById("consgNumber" + rowId).value = "";
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("cnWeight" + rowId).value = "";
	getDomElementById("receivedDate" + rowId).value = "";
	getDomElementById("codAmt" + rowId).value = "";
	getDomElementById("toPayAmt" + rowId).value = "";
	getDomElementById("reasonId" + rowId).value = "";
	getDomElementById("remarks" + rowId).value = "";

	getDomElementById("consignmentId" + rowId).value = "";// hidden
	getDomElementById("consignmentManifestId" + rowId).value = "";// hidden
}

/**
 * To save manifest DOX details
 * 
 * @param action
 */
function saveOrUpdateRtohManifest() {
	if (validateHeader() && validateGrid()) {
		dropdownEnable();
		var url = './rthRtoManifestDox.do?submitName=isRtohNoManifested';
		showProcessing();
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#rthRtoManifestDoxForm").serialize(),
			success : function(req) {
				printCallBackNoManifested(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert(textStatus);
			}
		});
	} else {
		// dropdownDisable();
		if (!isNull(getDomElementById("destRegion")))
			getDomElementById("destRegion").disabled = false;
		if (!isNull(getDomElementById("destCity")))
			getDomElementById("destCity").disabled = false;

		getDomElementById("destOffice").disabled = false;
	}
}

/**
 * @param data
 */
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

/**
 * Actual save the data to database
 */
function performSave4RtohManifest() {
	// showProcessing();
	var url = './rthRtoManifestDox.do?submitName=saveOrUpdateRthRtoManifestDox';
	jQuery.ajax({
		url : url,
		type : "POST",
		data : jQuery("#rthRtoManifestDoxForm").serialize(),
		success : function(req) {
			printCallBackManifestSave(req);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			alert(textStatus);
		}
	});
}

/**
 * ajax call back function for SAVE
 * 
 * @param data
 * @param action
 */
function printCallBackManifestSave(data) {
	var response = jsonJqueryParser(data);
	if (!isNull(response.errorMsg)) {
		alert(response.errorMsg);
	} else {
		alert(response.transMsg);
	}
	clearPage();
	hideProcessing();
}

/**
 * To validate the Header details
 * 
 * @returns {Boolean}
 */
function validateHeader() {
	var manifestNo = getDomElementById("manifestNo");
	var destRegion = getDomElementById("destRegion");
	var destCity = getDomElementById("destCity");
	var destOffice = getDomElementById("destOffice");
	if (isNull(trimString(manifestNo.value))) {
		clearFocusAlertMsg(manifestNo, "Please enter manifest number");
		return false;
	}
	if (!isNull(destRegion) && isNull(destRegion.value)) {
		clearFocusAlertMsg(destRegion, "Please select destination region");
		return false;
	}
	if (!isNull(destCity) && isNull(destCity.value)) {
		clearFocusAlertMsg(destCity, "Please select destination city");
		return false;
	}
	if (isNull(destOffice.value)) {
		clearFocusAlertMsg(destOffice, "Please select destination office");
		return false;
	}
	return true;
}

/**
 * To validate the grid
 * 
 * @returns {Boolean}
 */
function validateGrid() {
	var table = document.getElementById("rthRtoManifestTable");
	//for ( var i = 1; i < rowCount; i++) {
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var consgNo = getDomElementById("consgNumber" + rowId);
		if(isNull(consgNo.value)){
			continue;
		}
		if (!validateRowDtls(rowId)) {
			return false;
		}
	}
	return true;
}

/**
 * To delete the selected row(s) in the grid table
 */
function deleteRows() {
	var table = getDomElementById("rthRtoManifestTable");
	var tableRowCount = table.rows.length;

	var isSelectAnyCheckBox = false;
	for ( var i = 0; i < tableRowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (chkbox != null && chkbox.checked == true) {
			isSelectAnyCheckBox = true;
			break;
		}
	}
	if (!isSelectAnyCheckBox) {
		alert("Please select atleast one row to delete.");
		return false;
	}
	for ( var i = 0; i < tableRowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (tableRowCount == 2) {
			for ( var j = 1; j < rowCount; j++) {
				if (!isNull(getDomElementById("consgNumber" + j))) {
					clearRow(j);
				}
			}
			if (chkbox != null && chkbox.checked == true) {
				alert("Default row can't delete");
				chkbox.checked = false;
				getDomElementById("checkAll").checked = false;
			}
		} else if (null != chkbox && true == chkbox.checked) {
			deleteRow("rthRtoManifestTable", i - 1);
			tableRowCount--;
			// rowCount--;
			i--;
		}
	}
}
/**
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
	updateSrlNo(tableId);
}

/**
 * To search manifest details
 * 
 * @param action
 */
function searchManifestDtls() {
	var manifestNo = getDomElementById("manifestNo");
	if (!isNull(trimString(manifestNo.value))) {
		manifestNo = showProcessing();
		var url = "./rthRtoManifestDox.do?submitName=searchRthRtoManifestDoxDtls";
		document.rthRtoManifestDoxForm.action = url;
		document.rthRtoManifestDoxForm.submit();

	} else {
		clearFocusAlertMsg(manifestNo, "Please enter manifest number");
		return false;
	}
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
/**
 * Disable after search
 */
function disableForSearch() {
	getDomElementById("manifestNo").readOnly = true;
	getDomElementById("manifestNo").setAttribute("tabindex", "-1");
	getDomElementById("Search").disabled = true;// search button
	getDomElementById("destOffice").disabled = true;
	if (!isNull(getDomElementById("destRegion")))
		getDomElementById("destRegion").disabled = true;
	if (!isNull(getDomElementById("destCity")))
		getDomElementById("destCity").disabled = true;
	buttonDisabled("saveBtn", "btnintform");
	buttonDisabled("deleteBtn", "btnintform");
}

/**
 * To disable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
	jQuery("#" + btnName).addClass("btnintformbigdis");
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
 * To enable or disable row
 * 
 * @param rowId
 * @param flag
 */
function fnEnableOrDisableRow(rowId, flag) {
	getDomElementById("consgNumber" + rowId).readOnly = flag;
	getDomElementById("remarks" + rowId).readOnly = flag;
	getDomElementById("reasonId" + rowId).disabled = flag;
	getDomElementById("chk" + rowId).checked = !flag;
}

/**
 * To edit Row
 * 
 * @param rowId
 */
// Moved to rthRtoManifest.js
/*
 * function fnEditRow(rowId) { if (!isNull(getValueByElementId("consgNumber" +
 * rowId))) { if (getDomElementById("chk" + rowId).checked) {
 * fnEnableOrDisableRow(rowId, false); } else { fnEnableOrDisableRow(rowId,
 * true); } } }
 */

var oldRegionValue = "";
var oldCityValue = "";

/**
 * To check whether grid is empty of not during header values change(s).
 * 
 * @param newObj
 * @param dropDownName
 */
function checkIsGridEmpty(newObj, dropDownName) {
	var isFilled = false;
	var flag = true;
	for ( var i = 1; i < rowCount; i++) {
		var consgObj = getDomElementById("consgNumber" + i);
		if (!isNull(consgObj) && !isNull(consgObj.value)) {
			isFilled = true;
			flag = confirm("Consignment number(s) already entered in the grid.\n\nDo you still want to make the change(s) in header?");
			break;
		}
	}
	if (flag) {
		if (isFilled) {
			$('#rthRtoManifestTable').dataTable().fnClearTable();
			addRows();
		}
		if (dropDownName == "REGION") {
			getCitiesByRegion();
			oldRegionValue = newObj.value;
		} else {
			getOfficesByCity();
			oldCityValue = newObj.value;
		}
	} else {
		if (dropDownName == "REGION") {
			getDomElementById("destRegion").value = oldRegionValue;
		} else {// CITY
			getDomElementById("destCity").value = oldCityValue;
		}
	}
}

function printManifest() {
	var manifestNoObj = document.getElementById("manifestNo");
	if (!isNull(trimString(manifestNoObj.value))) {
		if (confirm("Do you want to Print?")) {
			var manifestNumber = manifestNoObj.value;
			var manifestType = document.getElementById("manifestType").value;
			if (!isNull(manifestNumber) && !isNull(manifestType)) {
				url = './rthRtoManifestDox.do?submitName=printRthRtoManifestDoxDtls&manifestNumber='
						+ manifestNumber + '&manifestType=' + manifestType;
				var w = window
						.open(url, 'myPopUp',
								'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			}
		}
	} else {
		clearFocusAlertMsg(manifestNoObj, "Please enter manifest number");
	}
}

function prepareIframe() {
	// to prepare iFrame
	var url2 = null;
	var manifestNumber = document.getElementById("manifestNo").value;
	var manifestType = document.getElementById("manifestType").value;
	if (!isNull(trimString(manifestNumber)) && !isNull(manifestType)) {
		url2 = './rthRtoManifestDox.do?submitName=printRthRtoManifestDoxDtls&manifestNumber='
				+ manifestNumber + '&manifestType=' + manifestType;
	}
	printUrl = url2;
	printIframe(printUrl);
}

/**
 * To submit for to given url without prompt any alert message.
 * 
 * @param url
 */
function submitRtohDoxWithoutPrompt(url) {
	document.rthRtoManifestDoxForm.action = url;
	document.rthRtoManifestDoxForm.submit();
}
