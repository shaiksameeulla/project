var rowCount = 1;
var eachConsgWeightArr = new Array();
var ERROR_FLAG ="ERROR";
var SUCCESS_FLAG = "SUCCESS";

function getAllOffices() {
	document.getElementById('destOffice').value = "";
	var destCityId = "";
	destCityId = document.getElementById("destCity").value;
	if (!isNull(destCityId)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getAllOfficesByCity&cityId='
				+ destCityId;
		// ajaxCallWithoutForm(url, printAllOffices);
		jQuery.ajax({
			url : url,
			success : function(req) {
				printAllOffices(req);
			},
			error : function(req) {
				alert("Server error");
				hideProcessing();
			}
		});
	} else {
		createDropDown("destOffice", "", "SELECT");
	}
}

function getOfficesByCityAndofficeType() {
	var destCityId = getDomElementById("destCity").value;
	var officeTypeId = document.getElementById("destOfficeType").value;
	if (!isNull(destCityId) && !isNull(officeTypeId)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getAllOfficesByCityAndOfficeType&cityId='
				+ destCityId + "&officeTypeId=" + officeTypeId;

		ajaxCallWithoutForm(url, printAllOffices);
	}
}

function printAllOffices(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			showDropDownBySelected("destCity", "");
			createDropDown("destOffice", "", "SELECT");
			alert(error);
			document.getElementById('destCity').focus();
		} else {
			var response = ajaxResp;
			if (!isNull(response)) {
				var content = document.getElementById('destOffice');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				var allHubOfficeIds = "";
				// createOnlyAllOptionWithValue('destOffice', "0");
				$.each(response, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.officeId;
					option
							.appendChild(document
									.createTextNode(this.officeName));
					content.appendChild(option);

					// Only Hub of the City should be considered
					if (!isNull(this.officeTypeTO)) {
						var officeType = this.officeTypeTO.offcTypeCode.trim();
						if (officeType == "HO")
							allHubOfficeIds = allHubOfficeIds + this.officeId
									+ ",";
					}

				});
				if (!isNull(getDomElementById("multiDest"))) {
					document.getElementById("multiDest").value = allHubOfficeIds;
				}
			}

		}
		hideProcessing();
	}
}

function getAllCities() {
	var destRegionId = "";
	createDropDown("destCity", "", "SELECT");
	createDropDown("destOffice", "", "SELECT");
	destRegionId = document.getElementById("destRegionId").value;
	if (!isNull(destRegionId)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getCitiesByRegion&regionId='
				+ destRegionId;
		ajaxCallWithoutForm(url, printAllCities);
	}
}

function getAllCitiesByRegionAndManifestType() {
	var destRegionId = "";
	var manifestType = "";
	getDomElementById('destOfficeType').value = "";
	createDropDown("destCity", "", "SELECT");
	createDropDown("destOffice", "", "SELECT");
	destRegionId = document.getElementById("destRegionId").value;
	manifestType = document.getElementById("manifestType").value;
	if (!isNull(destRegionId) && !isNull(manifestType)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getCitiesByRegion&regionId='
				+ destRegionId + "&manifestType=" + manifestType;
		ajaxCallWithoutForm(url, printAllCities);
	}
}

function printAllCities(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("destRegionId", "");
			alert(error);
			document.getElementById('destRegionId').focus();
		}

		else {
			var response = ajaxResp;
			if (!isNull(response)) {
				var content = document.getElementById('destCity');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				// createOnlyAllOptionWithValue('destCity', "0");
				$.each(response, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.cityId;
					option.appendChild(document.createTextNode(this.cityName));
					content.appendChild(option);
				});

			}
		}

		hideProcessing();

	}
}

// Validates Consignment number
function isValidConsignment(consgNumberObj) {
	var flag = true;
	if (!isNull(consgNumberObj.value)) {
		var consgNumber = "";
		consgNumber = consgNumberObj.value;
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consignment length should be 12 character");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			flag = false;
		} else {
			//var letters = /^[A-Za-z]+$/;
			var numpattern = /^[0-9]{3,20}$/;
			var str = consgNumber.charAt(1);
			if (!numpattern.test(consgNumberObj.value.substring(5)) || (str.match(/^[^\d]*$/) )) {
				alert('Consignment number format is not correct');
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

function printCallBackConsignment(data, consgNumberObj) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isConsgExists == "N") {
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			} else if (cnValidation.isCnManifested == "Y") {
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			} else if (cnValidation.isValidPincode == "N") {
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			}
			// TODO:product allowed test
			/*
			 * else if (cnValidation.isValidPincode == "N") {
			 * alert(cnValidation.errorMsg); consgNumberObj.value = "";
			 * setTimeout(function() { consgNumberObj.focus(); }, 10); }
			 */else {
				// to get the details of consignment
				getConsignmentDetails(consgNumberObj);
			}
		}
	}
	jQuery.unblockUI();
}
function isValidPincodeFormat(pinObj){
	var flag=false;
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		if (pincode.length < 6) {
			alert("Invalid pincode");
			var pincodeId = getRowId(pinObj, "pincode");
			ROW_COUNT = pincodeId;
			pinObj.value = "";
			if (!isNull(getDomElementById("destCity" + ROW_COUNT))) {
				getDomElementById("destCity" + ROW_COUNT).value = "";
				getDomElementById("destCityId" + ROW_COUNT).value = "";
			}
			setTimeout(function() {
				pinObj.focus();
			}, 10);			
		}else{
			flag=true;
		}
	}
	return flag;
}
function validatePincode(pinObj) {
	if (!isNull(pinObj.value)) {
		var loggedInOfficeId = "";
		var pincode = pinObj.value;
		ROW_COUNT = getRowId(pinObj, "pincode");
		if (pincode.length < 6) {
			alert("Invalid pincode");			
			pinObj.value = "";
			if (!isNull(getDomElementById("destCity" + ROW_COUNT))) {
				getDomElementById("destCity" + ROW_COUNT).value = "";
				getDomElementById("destCityId" + ROW_COUNT).value = "";
			}
			setTimeout(function() {
				pinObj.focus();
			}, 10);
			return;
		}
		var manifestOpenType = "";
		var isPickupCN="";
		var consgNumber = document.getElementById("consigntNo" + ROW_COUNT).value;
		var consgSeries = consgNumber.substring(4, 5);
		loggedInOfficeId = document.getElementById("originOffice").value;
		var destCityId = document.getElementById("destCity").value;
		var destOfficeId = document.getElementById("destOffice").value;
		if (!isNull(getDomElementById("manifestOpenType")))
			manifestOpenType = getDomElementById("manifestOpenType").value;
		if (!isNull(getDomElementById("isCNProcessedFromPickup"+ ROW_COUNT)))
			isPickupCN = getDomElementById("isCNProcessedFromPickup" + ROW_COUNT).value;

		showProcessing();
		url = './outManifestDox.do?submitName=validatePincode&pincode='
				+ pincode + "&loggedInOfficeId=" + loggedInOfficeId
				+ "&consgSeries=" + consgSeries + "&destOfficeId="
				+ destOfficeId + "&destCityId=" + destCityId
				+ "&manifestOpenType=" + manifestOpenType
				+ "&isPickupCN=" + isPickupCN;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req);
			}
		});
	}

}

function printCallBackPincode(data) {
	var destCityDomEle = getDomElementById("destCity" + ROW_COUNT);
	var destCityIdDomEle = getDomElementById("destCityId"+ ROW_COUNT);
	var cnValidation = jsonJqueryParser(data);
	if (!isNull(cnValidation)) {
		var error = cnValidation[ERROR_FLAG];
		if (cnValidation != null && error != null) {
			alert(error);
			document.getElementById("pincode" + ROW_COUNT).value = "";
			if (!isNull(destCityDomEle))
				getDomElementById("destCity" + ROW_COUNT).value="";
			if (!isNull(destCityIdDomEle))
				getDomElementById("destCityId"+ ROW_COUNT).value="";
			document.getElementById("pincode" + ROW_COUNT).focus();
		} else {
			document.getElementById("pincodeId" + ROW_COUNT).value = cnValidation.cnPincodeTO.pincodeId;				
			if (!isNull(destCityDomEle))
				getDomElementById("destCity" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityName;
			if (!isNull(destCityIdDomEle))
				getDomElementById("destCityId" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityId;
		}

	}
	jQuery.unblockUI();

}
	/*
	var response = data;
	if (!isNull(response)) {
		var cnValidation = eval('(' + response + ')');
		var destCityDomEle = getDomElementById("destCity" + ROW_COUNT);
		var destCityIdDomEle = getDomElementById("destCityId"+ ROW_COUNT);
		if (cnValidation != null && cnValidation != "") {
			if (!isNull(cnValidation.errorMsg)) {
				alert(cnValidation.errorMsg);
				document.getElementById("pincode" + ROW_COUNT).value = "";
				if (!isNull(destCityDomEle))
					getDomElementById("destCity" + ROW_COUNT).value="";
				if (!isNull(destCityIdDomEle))
					getDomElementById("destCityId"+ ROW_COUNT).value="";
				document.getElementById("pincode" + ROW_COUNT).focus();
			} else {
				document.getElementById("pincodeId" + ROW_COUNT).value = cnValidation.cnPincodeTO.pincodeId;				
				if (!isNull(destCityDomEle))
					getDomElementById("destCity" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityName;
				if (!isNull(destCityIdDomEle))
					getDomElementById("destCityId" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityId;
			}
		}
	}
	jQuery.unblockUI();
}*/

function validateMobile(obj) {
	if (!isNull(obj.value)) {
		var mobile = obj.value;
		var numpattern = /^[0-9]{3,20}$/;
		if (numpattern.test(mobile)) {
			if (mobile.length != 10) {
				alert("Enter 10 Digits Mobile Number");
				setTimeout(function() {
					obj.focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		} else {
			alert('Mobile number should be numeric');
		}
	}
}

function getRfIdByRfNo(rfNoObj) {
	rfNoObj.value = $.trim(rfNoObj.value);
	if (!isNull(rfNoObj.value)) {
		var url = "./bplOutManifestDox.do?submitName=getRfIdByRfNo&rfNo="
				+ rfNoObj.value;
		$.ajax({
			url : url,
			success : function(req) {
				populateRfId(req, rfNoObj);
			}
		});
	}
}

function populateRfId(data, rfNoObj) {
	var responseText = jsonJqueryParser(data);
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			rfNoObj.value = "";
			setTimeout(function() {
				rfNoObj.focus();
			}, 10);
		} else {
			document.getElementById("bagRFID").value = rfNoObj.value;
		}

	}

}
	



function deleteTableRow(action) {
	var comailIdListAtGrid;
	var manifestIdListAtGrid;
	var consgIdListAtGrid;

	var manifestStatus = document.getElementById("manifestStatus");

	if (!(isNull(manifestStatus) && manifestStatus.value == "O")) {
		var consgIdList = document.getElementById("consgIdListAtGrid");
		if (!isNull(consgIdList)) {
			consgIdListAtGrid = document.getElementById("consgIdListAtGrid").value;
		} else {
			consgIdListAtGrid = "";
		}

		var manifestId = document.getElementById("manifestIdListAtGrid");
		if (manifestId == undefined) {
			manifestIdListAtGrid = "";
		} else {
			manifestIdListAtGrid = document
					.getElementById("manifestIdListAtGrid").value;
		}
		var comailIdList = document.getElementById("comailIdListAtGrid");
		if (comailIdList == undefined) {
			comailIdListAtGrid = "";
		} else {
			comailIdListAtGrid = document.getElementById("comailIdListAtGrid").value;
		}
		if (!isNull(consgIdListAtGrid) || !isNull(manifestIdListAtGrid)
				|| !isNull(comailIdListAtGrid)) {
			url = './outManifestDox.do?submitName=deleteGridElement&consgId='
					+ consgIdListAtGrid + '&manifestId=' + manifestIdListAtGrid
					+ '&comailId=' + comailIdListAtGrid;
			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBackManifestDelete(req, action);
				}
			});
		}/* else {
			alert("Please select the data to delete");
		}*/
	} else {
		alert("Closed manifest can not be deleted");
	}
}

function selectAllCheckBox(maxRowsAllowed) {
	if (getDomElementById("checkAll").checked == true) {
		for ( var i = 1; i <= maxRowsAllowed; i++) {
			getDomElementById("ischecked" + i).checked = true;
		}
	} else {
		for ( var i = 1; i <= maxRowsAllowed; i++) {
			getDomElementById("ischecked" + i).checked = false;
		}
	}
}

/**
 * @desc maximum weight tolerance check (Shld not be greater than 38.5)
 * @param rowId
 * @param maxRowsAllowed
 * @param maxWeightAllowed
 * @param maxTolerenceAllowed
 * @returns {Boolean}
 */
function maxWeightToleranceCheck(rowId, maxRowsAllowed, maxWeightAllowed,
		maxTolerenceAllowed) {
	var weightInFraction = 0;
	var finalWeight = 0;
	var maxAllowedWtWithTollrence = 0;

	maxRowsAllowed = parseInt(maxRowsAllowed);
	maxWeightAllowed = parseInt(maxWeightAllowed);
	maxTolerenceAllowed = parseInt(maxTolerenceAllowed);

	// wt at each line item
	var weightKg;
	if(!isNull(document.getElementById("weightKg" + rowId)))
		weightKg = document.getElementById("weightKg" + rowId).value;
	else
	 weightKg = document.getElementById("weight" + rowId).value;
	
	weightKg = (isNull(weightKg)) ? "0" : weightKg;
	var weightGM = document.getElementById("weightGm" + rowId).value;
	weightGM = (isNull(weightGM)) ? "000" : weightGM;

	if (weightGM == undefined) {
		weightGM = "000";
	} else if (weightGM.length == 1) {
		weightGM += "00";
	} else if (weightGM.length == 2) {
		weightGM += "0";
	} else {
		weightGM = weightGM.substring(0, 3);
	}

	weightInFraction = parseFloat(weightKg) + parseFloat(weightGM) / 1000;
	eachConsgWeightArr[rowId] = weightInFraction;

	// Final Wt
	for ( var j = 1; j <= maxRowsAllowed; j++) {
		if (eachConsgWeightArr[j] != undefined){
			finalWeight = parseFloat(finalWeight)*1000
					+ parseFloat(eachConsgWeightArr[j])*1000;
			finalWeight /= 1000;
		}
	}
	document.getElementById("finalWeight").value = finalWeight;
	// Max wt with tollerence
	maxAllowedWtWithTollrence = maxWeightAllowed
			+ (maxWeightAllowed * maxTolerenceAllowed / 100);

	// For 1 Grid wt > 38.5
	if (eachConsgWeightArr[rowId] > maxAllowedWtWithTollrence && rowId == 1) {
		// Set Final Wt in Header
		 setFinalWeight(finalWeight);
		var close = document.getElementById("closeBtn");
		alert("Maximum weight limit exceeds.");
		setTimeout(function() {
			close.focus();
		}, 20);
		return true;

		// For multiple grid items
	} else if (finalWeight > maxAllowedWtWithTollrence) {
		// Set Final Wt in Header
		finalWeight = parseFloat(finalWeight)
				- parseFloat(eachConsgWeightArr[rowId]);
		document.getElementById("finalWeight").value = finalWeight;
		// setFinalWeight(finalWeight);
		alert("Maximum weight limit exceeds.");
		return false;
	} else if (finalWeight == maxAllowedWtWithTollrence) {
		// Set Final Wt in Header
		// setFinalWeight(finalWeight);
		var close = document.getElementById("closeBtn");
		alert("Maximum weight limit reached.");
		setTimeout(function() {
			close.focus();
		}, 20);
		return true;
	}
	setFinalWeight(finalWeight);
	return true;
}

/**
 * @desc to set final weight at header
 * @param finalWeight
 */
function setFinalWeight(finalWeight) {
	// Setting final wt
	var finalWeightStr = finalWeight + "";
	// finalWeight += "";
	weightKgValueFinal = finalWeightStr.split(".");

	if (!isNull(weightKgValueFinal[0])) {
		document.getElementById("finalKgs").value = weightKgValueFinal[0];
	} else if (weightKgValueFinal[0] == undefined) {
		document.getElementById("finalKgs").value = "0";
	} else {// for null check
		document.getElementById("finalKgs").value = "0";
	}
	if (!isNull(weightKgValueFinal[1])) {
		document.getElementById("finalGms").value = weightKgValueFinal[1]
				.substring(0, 3);
	}
	if (weightKgValueFinal[1] == undefined) {
		document.getElementById("finalGms").value = "000";
	} else if (weightKgValueFinal[1].length == 1) {
		document.getElementById("finalGms").value += "00";
	} else if (weightKgValueFinal[1].length == 2) {
		document.getElementById("finalGms").value += "0";
	} else if (weightKgValueFinal[1] == "" || isNull(weightKgValueFinal[1])) {// for
		// null
		// check
		document.getElementById("finalGms").value = "000";
	}

}
// Set the button natures based on manifest status
function closedManifestActions() {
	var manifestStatus = document.getElementById("manifestStatus").value;
	if (manifestStatus == "C") {
		document.getElementById("saveBtn").disabled = true;
		// document.getElementById("deleteBtn").disabled = true;
		document.getElementById("closeBtn").disabled = true;
		// document.getElementById("printBtn").disabled = false;
	} else {
		document.getElementById("saveBtn").disabled = false;
		// document.getElementById("deleteBtn").disabled = false;
		document.getElementById("closeBtn").disabled = false;
		// document.getElementById("printBtn").disabled = true;
	}
}

function printManifestDtls() {
	flag = confirm("Do you want to print manifest details?");
	if (flag) {
		alert("Print functionality need to implemet");
	} else {
		flag = false;
	}
	return flag;
}
function setFractions(obj, digits) {
	if (!isNull(obj.value))
		obj.value = parseFloat(obj.value).toFixed(digits);
	//incase of null value also value showing..
	/*else if (digits == "2")
		obj.value = "0.00";*/
}

function setDeclaredFractions(obj, digits) {
	if (!isNull(obj.value))
		obj.value = parseFloat(obj.value).toFixed(digits);
	else if (digits == "2")
		obj.value = "";
}

/* @Desc:Disabling the elements after closing the mbpl */
function disableForClose() {
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
	// buttonEnabled("printBtn","btnintformbigdis");
	jQuery("#" + "printBtn").attr("disabled", false);
	jQuery("#" + "printBtn").removeClass("btnintformbigdis");
	jQuery("#" + "printBtn").addClass("btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
	jQuery("#" + "closeBtn").addClass("btnintformbigdis");
	jQuery("#" + "deleteBtn").addClass("btnintformbigdis");
	jQuery("#" + "cancelBtn").attr("disabled", false);
	jQuery("#" + "cancelBtn").removeClass("btnintformbigdis");
	jQuery("#" + "cancelBtn").addClass("btnintform");
}

/* @Desc:Disabling the checkbox of the grid */
function disableAllCheckBox() {
	getDomElementById("checkboxAll").disabled = true;
	for ( var i = 1; i <= 10; i++) {
		getDomElementById("ischecked" + i).disabled = true;
	}
}

function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).addClass("btnintform");
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
}

function disableAll() {
	buttonDisable();
	inputDisable();
	dropdownDisable();
}
function getReportingRHOByoffice(officeId) {
	if (!isNull(officeId)) {
		var url = './outManifestDox.do?submitName=getRHOOfficeIdByOffice&officeId='
				+ officeId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getReportingRHOId(req,officeId);
			}
		});
	}
}

function getReportingRHOId(data,officeId) {
	var responseText = jsonJqueryParser(data);
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			document.getElementById("repHubOfficeId").value = officeId;
		}

	}

}
	
function onlyNumberAndEnterKeyNav(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/**
 * Enter Key NavigationFocus4Grid
 * 
 * @param evt
 * @param elementIdToFocus
 * @param rowId
 * @returns {Boolean}
 * @author sdalli
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
		// document.getElementById(elementIdToFocus).focus();
		return true;
	}
	return false;
}

/**
 * onlyNumber And EnterKeyNavFocus4Grid
 * 
 * @param evt
 * @param elementIdToFocus
 * @param rowId
 * @returns {Boolean}
 * @author sdalli
 */
function onlyNumberAndEnterKeyNavFocus4Grid(evt, elementIdToFocus, rowId) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		$("#" + elementIdToFocus + rowId).focus();
		return false;
	} else {
		return true;
	}
	return false;
}

/**
 * enterKeyNavigationFocus
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author sdalli
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
		// document.getElementById(elementIdToFocus).focus();
		return true;
	}
	return false;
}