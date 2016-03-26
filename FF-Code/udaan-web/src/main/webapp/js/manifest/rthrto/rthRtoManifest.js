var MANIFEST_TYPE_RTO = "";
var isHeaderChg = true;
var destRegionVal = "";
var destCityVal = "";

var numpattern = /^[0-9]+$/;
var letters = /^[A-Za-z]+$/;
var alphaNumeric = /^[A-Za-z0-9]+$/;

// Validates Consignment number
function isValidConsignment(consgNumberObj) {
	var flag = true;
	consgNumberObj.value = trimString(consgNumberObj.value);
	if (!isNull(consgNumberObj.value)) {
		var consgNumber = "";
		consgNumber = consgNumberObj.value;
		consgNumberObj.value = trimString(consgNumberObj.value);
		consgNumberObj.value = consgNumberObj.value.toUpperCase();
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			clearFocusAlertMsg(consgNumberObj,
					"Consignment length should be 12 character");
			flag = false;
		} else {
			// Added by Himal
			// Declared as global constants
			/*
			 * var numpattern = /^[0-9]+$/; var letters = /^[A-Za-z]+$/; var
			 * alphaNumeric = /^[A-Za-z0-9]+$/;
			 */
			if (!consgNumberObj.value.substring(0, 1).match(letters)
					|| (!consgNumberObj.value.substring(4, 5).match(letters) && !consgNumberObj.value
							.substring(1, 4).match(alphaNumeric))
					|| !consgNumberObj.value.substring(1, 4)
							.match(alphaNumeric)
					|| !numpattern.test(consgNumberObj.value.substring(5))) {
				clearFocusAlertMsg(consgNumberObj,
						"Consignment number format is not correct");
				flag = false;
			}
		}
	} else {
		flag = false;
	}
	return flag;
}

function isDuplicateConsignment(consgNumberObj) {
	var isValid = true;
	if (isValidConsignment(consgNumberObj)) {
		var currentCn = consgNumberObj.value;
		var currentRowId = getRowId(consgNumberObj, "consgNumber");
		var cnList = document.getElementsByName("to.consgNumbers");
		for ( var i = 0; i < cnList.length; i++) {
			var rid = getRowId(cnList[i], 'consgNumber');
			if (rid != currentRowId) {
				if (cnList[i].value == currentCn) {
					// duplicate cn exist at row number i+1
					clearFocusAlertMsg(consgNumberObj,
							"Duplicate consignment number entered");
					isValid = false;
				}
			}
		}
	} else {
		isValid = false;
	}
	return isValid;
}

/**
 * updateSerialNoVal
 * 
 * @param tableId
 * @author narmdr
 */
function updateSerialNoVal(tableId) {
	try {
		var table = getDomElementById(tableId);

		for ( var i = 1; i < table.rows.length; i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var serialNo = getDomElementById("serialNo" + rowId);
			if (serialNo.innerHTML != i) {
				serialNo.innerHTML = i;
			}
		}
	} catch (e) {

	}
}
/*
 * function showDropDownBySelectedValue(domId, selectedVal) { var domElement =
 * getDomElementById(domId); for ( var i = 0; i < domElement.options.length;
 * i++) { if (domElement.options[i].value == selectedVal) {
 * domElement.options[i].selected = 'selected'; } } }
 */

function validateManifestNo(manifestNoObj) {
	var isValidNo = true;
	var errorMsg = 'Invalid manifest number format';
	manifestNoObj.value = trimString(manifestNoObj.value);
	if (!isNull(manifestNoObj.value)) {
		manifestNoObj.value = manifestNoObj.value.toUpperCase();
		var originCityCode = getDomElementById("originCityCode").value;
		if (manifestNoObj.value.length < 10 || manifestNoObj.value.length > 10) {
			errorMsg = 'Manifest No. must contain atleast 10 characters';
			isValidNo = false;
		} else if (manifestNoObj.value.substring(0, 3).toUpperCase() == originCityCode
				.toUpperCase()) {
			var bplNo = false;
			var ogmNo = false;
			var consgType = getDomElementById("consignmentTypeCode").value;
			if (consgType == CN_TYPE_PPX) {
				if (fnBplSeriesTest(manifestNoObj)) {
					getDomElementById("seriesType").value = BPL_SERIES;
					bplNo = true;
				}
			} else {
				if (fnNumberPatternTest(manifestNoObj.value.substring(3))) {
					getDomElementById("seriesType").value = OGM_SERIES;
					ogmNo = true;
				}
			}
			if (!bplNo && !ogmNo) {
				isValidNo = false;
			}
		} else {
			errorMsg = 'Manifest does not belong to this city';
			isValidNo = false;
		}
		if (!isValidNo) {
			clearFocusAlertMsg(manifestNoObj, errorMsg);
		}
		// Stock Validation for manifest No. Added By Himal
		if (isValidNo) {
			stockIssueValidation4RtohNo();
		}
	}
	return isValidNo;
}
function fnNumberPatternTest(series) {
	if (numpattern.test(series))
		return true;
	else
		return false;
}
function fnBplSeriesTest(manifestNoObj) {
	var fourthCharB = /^[B]$/;
	var letter4 = manifestNoObj.value.substring(3, 4);
	if (letter4.match(fourthCharB)) {
		if (fnNumberPatternTest(manifestNoObj.value.substring(4)))
			return true;
		else
			return false;
	} else
		return false;
}
function getCitiesByRegion() {
	fnHeaderDataChanges();
	if (isHeaderChg) {
		var destRegionId = "";
		createDropDown("destCity", "", "SELECT");
		createDropDown("destOffice", "", "SELECT");
		destRegionId = getDomElementById("destRegion").value;
		if (!isNull(destRegionId)) {
			showProcessing();
			url = './rthRtoManifestParcel.do?submitName=getCitiesByRegion&regionId='
					+ destRegionId;
			ajaxCallWithoutForm(url, printAllCities);
		}
	}
}

function printAllCities(data) {
	var response = data;

	var errorMsg = getErrorMessage(response);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}

	if (!isNull(response)) {
		var content = getDomElementById('destCity');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(response, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	} else {
		alert("No cities found for the selected region");
	}
	hideProcessing();
}

function getOfficesByCity() {
	fnHeaderDataChanges();
	if (isHeaderChg) {
		var cityId = "";
		createDropDown("destOffice", "", "SELECT");
		cityId = getDomElementById("destCity").value;
		if (!isNull(cityId)) {
			showProcessing();
			url = './rthRtoManifestParcel.do?submitName=getOfficesByCity&cityId='
					+ cityId;
			ajaxCallWithoutForm(url, printAllOffices);
		}
	}
}

function printAllOffices(data) {
	var response = data;

	var errorMsg = getErrorMessage(response);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}

	if (!isNull(response)) {
		var content = getDomElementById('destOffice');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(response, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	} else {
		alert("No offices found for the selected city");
	}
	hideProcessing();
}
function addOptionTODropDown(selectId, label, value) {
	var myselect = getDomElementById(selectId);
	try {
		myselect.add(new Option(label, value), null); // add new option to end
		// of "myselect"
		// myselect.add(new Option(label, value), myselect.options[0]); //add
		// new option to beginning of "myselect"
	} catch (e) { // in IE, try the below version instead of add()
		myselect.add(new Option(label, value)); // add new option to end of
		// "myselect"
		// myselect.add(new Option(label, value), 0); //add new option to
		// beginning of "myselect"
	}
}
function createDropDown(domId, value, desc) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	var text = document.createTextNode(desc);
	optionSelectType.value = value;
	optionSelectType.appendChild(text);
	// domElement.add(optionSelectType);
	domElement.appendChild(optionSelectType);
	return domElement;
}
/**
 * To check all checkbox
 * 
 * @param checkBoxObj
 */
function selectAllCheckBox(checkBoxObj) {
	if (checkBoxObj.checked) {// check
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = true;
				// fnEnableOrDisableRow(i, false);//Must be there in respective
				// .js
				fnEditRow(i);
			}
		}
	} else {// un-check
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = false;
				// fnEnableOrDisableRow(i, true);//Must be there in respective
				// .js
				fnEditRow(i);
			}
		}
	}
}
function fnEditRow(rowId) {
	if (!isNull(getValueByElementId("consgNumber" + rowId))) {
		if (getDomElementById("chk" + rowId).checked) {
			fnEnableOrDisableRow(rowId, false);
		} else {
			fnEnableOrDisableRow(rowId, true);
		}
	}
}
function stockIssueValidation4RtohNo() {
	var manifestNo = getDomElementById("manifestNo").value;
	var seriesType = getDomElementById("seriesType").value;
	var originCityCode = getDomElementById("originCityCode").value;
	// showProcessing();
	var url = "./rthRtoManifestParcel.do?submitName=isManifestNoIssued";
	$.ajax({
		url : url,
		data : "manifestNo=" + manifestNo + "&seriesType=" + seriesType
				+ "&cityCode=" + originCityCode,
		success : function(data) {
			isRtohNoIssued(data);
		}
	});
}

function isRtohNoIssued(data) {
	var manifestNoObj = getDomElementById("manifestNo");
	var manifestIssueValidationTO = eval('(' + data + ')');
	// hideProcessing();

	if (!isNull(manifestIssueValidationTO.errorMsg)) {
		clearFocusAlertMsg(manifestNoObj, manifestIssueValidationTO.errorMsg);
		return;
	}

	// plz uncomment after test :: issue validation
	if (manifestIssueValidationTO.isIssued == "N") {
		clearFocusAlertMsg(manifestNoObj, manifestIssueValidationTO.errorMsg);
		return;
	}
}
/*
 * function clearFocusAlertMsg(obj, msg){ obj.value = ""; alert(msg);
 * setTimeout(function() { obj.focus(); }, 10); }
 */
function fnHeaderDataChanges() {
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_RTO) {
		var cnList = document.getElementsByName("to.consgNumbers");
		var noofRows = cnList.length;
		var rowEntered = false;
		var consgNumber = "";
		for ( var count = 1; count <= noofRows; count++) {
			consgNumber = getDomElementById("consgNumber" + count);
			if (!isNull(consgNumber) && !isNull(consgNumber.value)) {
				rowEntered = true;
				break;
			}
		}
		if (rowEntered) {
			var userConfirmed = confirm("Consignments already entered.\n\nDo you want to change the destination?");
			if (!userConfirmed) {
				isHeaderChg = false;
				showDropDownBySelected('destRegion', destRegionVal);
				showDropDownBySelected('destCity', destCityVal);
			} else if (userConfirmed) {
				isHeaderChg = true;
				for ( var count = 1; count <= noofRows; count++) {
					clearRows(count);
				}
			}
		}
	}
}

/**
 * To check stock validation for bag lock number
 * 
 * @param bagLockObj
 * @param manifestScanLevel
 * @returns {Boolean}
 */
function bagLockAndStockValidation(bagLockObj, manifestScanLevel) {
	var seriesType = BAGLOCK_SERIES;
	var regionCode = getDomElementById("regionCode").value;
	var loginCityCode = getDomElementById("originCityCode").value;
	var officeCode = getDomElementById("originOfficeCode").value;
	bagLockObj.value = trimString(bagLockObj.value);
	if (!isNull(bagLockObj.value)) {
		if (bagLockObj.value.substring(0, 1).match(letters)) {
			if (bagLockObj.value.substring(0, 1).toUpperCase() == regionCode
					&& numpattern.test(bagLockObj.value.substring(1))) {
				stockValidation(bagLockObj, loginCityCode, regionCode,
						officeCode, manifestScanLevel, seriesType);
			} else {
				clearFocusAlertMsg(bagLockObj,
						'Baglock No. format is not correct');
				return false;
			}
		} else {
			clearFocusAlertMsg(bagLockObj, 'Baglock No. format is not correct');
			return false;
		}
	}
}

function validateBagLockNo(bagLockObj, manifestScanLevel) {
	if (!isNull(bagLockObj.value)) {
		if (bagLockObj.value.trim().length != 8) {
			clearFocusAlertMsg(bagLockObj,
					"Baglock No. must contain atleast 8 characters");
			return false;
		} else if (!isNull(bagLockObj.value)) {
			// Check the baglock number already used or not
			var url = "./bplOutManifestDox.do?submitName=isValiedBagLockNo&bagLockNo="
					+ bagLockObj.value;
			jQuery.ajax({
				url : url,
				type : "GET",
				dataType : "json",
				success : function(data) {
					isvalidateBagLockNo(data, bagLockObj, manifestScanLevel);
				}
			});
		}
	}
}

function isvalidateBagLockNo(data, bagLockObj, manifestScanLevel) {
	// var responseText = jsonJqueryParser(data);
	var responseText = data;
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			clearFocusAlertMsg(bagLockObj, error);
			return false;
		} else {
			bagLockAndStockValidation(bagLockObj, manifestScanLevel);
		}
	}
}

/**
 * To validate stock validation for RTH/RTO manifest Parcel - bag lock number
 * 
 * @param stockItemNoObj
 * @param loginCityCode
 * @param regionCode
 * @param officeCode
 * @param manifestScanlevel
 * @param seriesType
 */
function stockValidation(stockItemNoObj, loginCityCode, regionCode, officeCode,
		manifestScanlevel, seriesType) {
	var stockItemNo = stockItemNoObj.value;
	var loginOfficeId = getDomElementById("originOfficeId").value;
	var regionId = getDomElementById("regionId").value;
	var manifestDirection = getDomElementById("manifestDirection").value;
	var processCode = getDomElementById("processCode").value;

	var url = './thirdPartyOutManifestDox.do?submitName=validateManifestNo&loggedinOfficeId='
			+ loginOfficeId
			+ "&loginCityCode="
			+ loginCityCode
			+ "&seriesType="
			+ seriesType
			+ "&stockItemNo="
			+ stockItemNo
			+ "&manifestType="
			+ manifestDirection
			+ "&regionId="
			+ regionId
			+ "&manifestScanlevel="
			+ manifestScanlevel
			+ "&processCode="
			+ processCode
			+ "&regionCode="
			+ regionCode
			+ "&officeCode="
			+ officeCode;

	jQuery.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		success : function(data) {
			if (!isNull(data)) {
				var error = data[ERROR_FLAG];
				if (data != null) {
					if (error != null) {
						hideProcessing();
						clearFocusAlertMsg(stockItemNoObj, error);
						return false;
					} else {
						return true;
					}
				}
			}
		}
	});
}

/**
 * To call function to avoid special character(s) - Allow only alpha numeric
 * 
 * @param e
 * @param focusObj
 * @returns {boolean}
 */
function callEnterKeyAlphaNum(e, focusObj) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = e.which; // firefox
	}
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90)
			|| (charCode >= 48 && charCode <= 57) || charCode == 8
			|| charCode == 9 || charCode == 0) {
		flag = true;
	} else if (charCode == 13) {
		setTimeout(function() {
			focusObj.focus();
		}, 10);
		flag = false;
	} else {
		flag = false;
	}
	return flag;
}
function callEnterKey4HubLoginRTH(e, loginOfficeType){
	if(LOGIN_OFF_TYPE == "BO"){
		//Branch
		callEnterKeyAlphaNum(e, getDomElementById('destOffice'));
	}else{
		//Hub
		callEnterKeyAlphaNum(e, getDomElementById('destCity'));
	}
}
function getHubOfficesByCity(){
	fnHeaderDataChanges();
	if (isHeaderChg) {
		var cityId = "";
		createDropDown("destOffice", "", "SELECT");
		cityId = getDomElementById("destCity").value;
		if (!isNull(cityId)) {
			showProcessing();
			url = './rthRtoManifestParcel.do?submitName=getHubOfficesByCity&cityId='+ cityId;
			ajaxCallWithoutForm(url, printAllOffices);
		}
	}
}
