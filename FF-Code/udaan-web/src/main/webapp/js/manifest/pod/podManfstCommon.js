var ERROR_FLAG = "ERROR";
/**
 * To get Office code
 */
function getAllOffices() {
	document.getElementById('destOffice').value = "";
	var destCityId = "";
	destCityId = document.getElementById("destCity").value;
	url = './outgoingPODManifest.do?submitName=getAllOfficesByCity&cityId='
			+ destCityId;
	ajaxCallWithoutForm(url, printAllOffices);
}

/**
 * @param data
 */
function printAllOffices(data) {
	if (!isNull(data)) {
		if (isJsonResponce(data)) {
			return;
		}
	}
	var destCityId = document.getElementById('destCity').value;
	var regionId = document.getElementById('destRegionId').value;
	var content = document.getElementById('destOffice');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(destCityId) && !isNull(regionId)) {
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}
}

/**
 * Get all cities
 */
function getAllCities() {
	var destRegionId = "";
	document.getElementById('destCity').value = "";
	document.getElementById('destOffice').value = "";
	destRegionId = document.getElementById("destRegionId").value;
	url = './outgoingPODManifest.do?submitName=getCitiesByRegion&regionId='
			+ destRegionId;
	ajaxCallWithoutForm(url, printAllCities);
}

/**
 * @param data
 */
function printAllCities(data) {
	if (!isNull(data)) {
		if (isJsonResponce(data)) {
			return;
		}
	}
	var regionId = document.getElementById('destRegionId').value;
	var content = document.getElementById('destCity');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(regionId)) {
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	}
	if (isNull(regionId)) {
		var content = document.getElementById('destOffice');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
	}

}

/**
 * Get all offices under selected city
 * 
 * @param obj
 */
function getAllOfficeIds(obj) {
	if (obj.value == 0) {
		document.getElementById("isMulDest").value = "Y";
		var officeIds = "";
		for ( var i = 0; i < obj.options.length; i++) {
			officeIds = obj.options[i].value + ",";
		}
		document.getElementById("multiDest").value = officeIds;
	} else {
		document.getElementById("isMulDest").value = "N";
	}
}

/**
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidManifestNo(manifestNoObj) {
	var isValidReturnVal = true;
	if (!isNull(manifestNoObj.value)) {
		if (manifestNoObj.value.length < 12 || manifestNoObj.value.length > 12) {
			alert('Manifest No. length should be 12 character');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		} else {
			var numpattern = /^[0-9]{3,20}$/;
			var manifestSeries = 'CM';
			if (manifestNoObj.value.substring(0, 2).toUpperCase() != manifestSeries
					|| !numpattern.test(manifestNoObj.value.substring(3, 12))) {
				alert('Manifest number format is not correct');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				isValidReturnVal = false;

			}
		}
	}
	return isValidReturnVal;
}

// Validates Consignment number
/**
 * @param consgNumberObj
 * @returns {Boolean}
 */
function isValidConsignment(consgNumberObj) {
	
	
	var flag = true;
	var selectedRowId = getRowId(consgNumberObj, "cnNumber");
	
	if (!isNull(consgNumberObj.value)) {
		
		var consgNumber = "";
		consgNumber = consgNumberObj.value;
		
		 document.getElementById("cnNumber" + selectedRowId).value = consgNumber.toUpperCase();
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consignment length should be 12 character");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			clearRow(selectedRowId);
			flag = false;
		} else {
			//var letters = /^[A-Za-z]+$/;
			var numpattern = /^[0-9]{3,20}$/;
			if (!numpattern.test(consgNumberObj.value.substring(5))) {
				alert('Consignment number format is not correct');
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				clearRow(selectedRowId);
				flag = false;
			}
		}
		return flag;
	} else {
		flag = false;
	}

	return flag;
}

/**
 * 
 * Check Duplicate entry of consignment
 * 
 * @param rowCount
 * @returns {Boolean}
 */
function isDuplicateCongnt(rowCount) {
	var isValid = true;
	var currentScan = document.getElementById("cnNumber" + rowCount).value;
	var table = document.getElementById('podConsignmentDetails');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {

		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevScan = document.getElementById('cnNumber' + id).value;
		if (!isNull(prevScan.trim()) && !isNull(currentScan.trim())) {
			if (rowCount != id) {
				if (prevScan.trim() == currentScan.trim()) {
					alert("Consignment Number already entered");
					document.getElementById("cnNumber" + rowCount).value = "";
					setTimeout(function() {
						document.getElementById("cnNumber" + rowCount).focus();
					}, 10);
					isValid = false;
				}
			}
		}
	}

	return isValid;
}

/**
 * @param maxRowsAllowed
 */
function selectAllCheckBox() {
	$(document).ready(function() {
		$('#checkAll').change('click', function() {
			checked = ($(this).attr('checked') == 'checked') ? 'true' : false;
			$(':checkbox').each(function() {
				$(this).attr('checked', checked);
			});
		});
	});

}

/**
 * Validate header details.
 * 
 * @returns {Boolean}
 */
function validateHeader() {
	var manifestNo = getDomElementById("manifestNo");
	var manifestDate = getDomElementById("manifestDate");
	var dispatchOffice = getDomElementById("dispatchOffice");
	var destRegionId = getDomElementById("destRegionId");
	var destCity = getDomElementById("destCity");
	var destOffice = getDomElementById("destOffice");
	if (isNull(manifestNo.value)) {
		alert("Please provide Manifest No.");
		setTimeout(function() {
			manifestNo.focus();
		}, 10);
		return false;
	}
	if (isNull(manifestDate.value)) {
		alert("Please provide Manifest Date");
		setTimeout(function() {
			manifestDate.focus();
		}, 10);
		return false;
	}
	if (isNull(dispatchOffice.value)) {
		alert("Please Provide Dispatch Office.");
		setTimeout(function() {
			dispatchOffice.focus();
		}, 10);
		return false;
	}
	if (isNull(destRegionId.value)) {
		alert("Please select destination region.");
		setTimeout(function() {
			destRegionId.focus();
		}, 10);
		return false;
	}
	if (isNull(destCity.value)) {
		alert("Please select destination City.");
		setTimeout(function() {
			destCity.focus();
		}, 10);
		return false;
	}
	if (isNull(destOffice.value)) {
		alert("Please select destination office.");
		setTimeout(function() {
			destOffice.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails() {
	for ( var i = 1; i <= rowCount; i++) {
		if (validateSelectedRow(i)) {
			return true;
		}
	}
	return false;
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var consgvalue = null;
	if (!isNull(document.getElementById('cnNumber' + rowId))) {
		consgvalue = document.getElementById('cnNumber' + rowId).value;
		if (isNull(consgvalue)) {
			alert("Please provide Consignment No. to be scanned ");
			setTimeout(function() {
				consgNo.focus();
			}, 10);
			return false;
		}
		if (!isNull(consgvalue)) {
			return true;
		}

	}
	return false;
}

/**
 * Delete consignment client side
 */
function deleteConsgDtlsClientSide() {
	var table = getDomElementById("podConsignmentDetails");
	var tableRowCount = table.rows.length;
	for ( var i = 0; i < tableRowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (tableRowCount == 2) {
			for ( var j = 1; j < rowCount; j++) {
				if (!isNull(getDomElementById("cnNumber" + j))) {
					getDomElementById("cnNumber" + j).value = "";
					getDomElementById("receivedDate" + j).value = "";
					getDomElementById("dlvDate" + j).value = "";
					getDomElementById("recvNameOrCompSeal" + j).value = "";
					getDomElementById("consgIds" + j).value = "";
				}
			}
			if (null != chkbox && true == chkbox.checked) {
				alert("Default row can't delete");
			}

		} else if (null != chkbox && true == chkbox.checked) {
			deleteRow("podConsignmentDetails", i - 1);
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
 * enterKeyNavigationFocus
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author prmeher
 */
function enterKeyNavigationFocus(evt, elementIdToFocus) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode == 13) {
		$("#" + elementIdToFocus).focus();
		return true;
	}
	return false;
}

/**
 * enterKeyNavigationFocus4Grid
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author prmeher
 */
function enterKeyNavigationFocus4Grid(evt, elementIdToFocus, rowId) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode == 13) {
		$("#" + elementIdToFocus + rowId).focus();
		return true;
	}
	return false;
}

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
 * @param event
 * @param cnObj
 */
function enterKeyNavigationFocusGrid(event, cnObj, podType) {

	var charCode;
	if (window.event) {
		
		charCode = window.event.keyCode; // IE
	} else {
		
		charCode = event.which; // firefox
	}
	if (charCode == 13) {
		
		var flag = isValidConsignment(cnObj);
		if (flag == true) {
			if (podType == "O") {
				
				getConsignmentDtls(cnObj);
			} else if (podType == "I") {
				getOutgoingPODConsignmentDtls(cnObj);
			}
		}
		// return true;
	}
	// return false;
}

function clearRow(rowId) {
	document.getElementById("receivedDate" + rowId).value = "";
	document.getElementById("dlvDate" + rowId).value = "";
	document.getElementById("recvNameOrCompSeal" + rowId).value = "";
}
