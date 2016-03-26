/** The buttonDisableCss */
var buttonDisableCss = "button_disabled";
var isDeleted = false;
var branchCode = null;
var hubCode = null;
var originOfficeTypeCode = null;
// checking route for PURE and Transhipment
var isPureRoute = false;
var isTranshipmentRoute = false;
var isRouteServiceable = false;
var isPureRouteCheckingForDestnOfficeId = false;
var selectOption = "--Select--";
var isConfirmChanges = false;
var isFilled = false;

var manifestType;
var destRegion;
var destCity;
var officeType;
var destOffice;
var isRegion;

var newManifestType;
var newDestRegion;
var newDestCity;
var newOfficeType;
var newDestOffice;

/* Storing header Details */
var manifestTypeStore = "";
var destinationRegionIdStore = "";
var destCityStore = "";
var destCityNameStore = "";
var destOfficeTypeStore = "";
var destOfficeStore = "";

var maxCount;

var rowId;

var printUrl;
var printFlag = "N";
var refreshFlag = "N";

function setDefaultValue() {
	branchCode = $("#branchOfficeType").val();
	hubCode = $("#hubOfficeType").val();
	originOfficeTypeCode = $("#originOfficeTypeCode").val();

}

/**
 * addBplOutManifestRows
 * 
 * @author sdalli
 */
function addBplOutManifestRows() {
	maxCount = parseInt(maxManifestAllowed);
	for ( var rowCount = 1; rowCount <= maxCount; rowCount++) {

		$('#bplOutManifestDoxTab')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox"  id="chk'
										+ rowCount
										+ '" name="chkBoxName"  onclick="getManifestId(this);" />',
								'<div id="serialNo' + rowCount + '">'
										+ rowCount + '</div>',
								'<input type="text" class="txtbox" styleClass="txtbox" id="manifestNos'
										+ rowCount
										+ '" name="to.manifestNos" maxlength="10" onfocus="validateHeaderDetails();"  onkeypress="return callEnterKey(event, document.getElementById(\'manifestNos'
										+ (rowCount + 1)
										+ '\'));" onchange="getManifestDtsbyManifestNo(this);" onblur="convertDOMObjValueToUpperCase(this);focusOnclose();"/>',
								'<input type="text" name="weight" id="weight'
										+ rowCount
										+ '" class="txtbox width50"  size="11" maxlength="5" readonly="true"/><span class="lable">.</span><input type="text" name="weightGm" id="weightGm'
										+ rowCount
										+ '" class="txtbox width30"   size="11" maxlength="3"  readonly="true" onblur="setTotalWeightBPL();"  onchange="setGridWeightToHidden(this.value(),this);"/><input type="hidden" id="weights'
										+ rowCount + '" name="to.weights" />',
								'<input type="text" class="txtbox width30" align = "center" id="cnCount'
										+ rowCount
										+ '" readonly="true" tabindex="-1" />',
								'<input type="text" class="txtbox" id="manifestType'
										+ rowCount
										+ '" readonly="true" tabindex="-1" />',
								'<input type="text" class="txtbox" id="destCitys'
										+ rowCount
										+ '" name="to.destCityId"   readonly="true" tabindex="-1" />\
 	<input type="hidden" id="manifestIds'
										+ rowCount
										+ '" name="to.manifestIds" value=""/>\
 	<input type="hidden" id="destCityIds'
										+ rowCount
										+ '" name="to.destCityIds" value=""/>\
 	<input type="hidden" id="manifestMappedEmbeddeId'
										+ rowCount
										+ '" name="to.manifestMappedEmbeddeId" value="" />\
 	<input type="hidden" id="productIds'
										+ rowCount
										+ '" name="to.productIds" value=""/>\
 	<input type="hidden" id="position'
										+ rowCount
										+ '" name="to.position" value="'
										+ rowCount
										+ '"/>\
 	<input type="hidden" id="productSeries'
										+ rowCount
										+ '" name="to.productSeries" value=""/>\
 	<input type="hidden" id="cityIds'
										+ rowCount
										+ '" name="to.cityIds" value=""/>', ]);
	}// end of for loop
	document.getElementById("manifestNo").focus();
}

/**
 * get Manifest Dts by ManifestNo
 * 
 * @param manifestNumberObj
 */
function getManifestDtsbyManifestNo(manifestNumberObj) {
	manifestNumberObj.value = $.trim(manifestNumberObj.value);
	rowId = getRowId(manifestNumberObj, "manifestNos");
	var transCity = document.getElementById("destCity").value;
	var loginOfficeId = document.getElementById("loginOfficeId").value;
	var officeType = $("#originOfficeTypeCode").val();
	var headerManifestNo = getDomElementById("manifestNo").value;
	if (!fnValidateNumber(manifestNumberObj)
			&& isValidPacketNo(manifestNumberObj)) {
		var url = "./bplOutManifestDox.do?submitName=getManifestDtlsByProcess&manifestNo="
				+ manifestNumberObj.value
				+ "&transCity="
				+ transCity
				+ "&loginOfficeId="
				+ loginOfficeId
				+ "&officeType="
				+ officeType + "&headerManifestNo=" + headerManifestNo;
		showProcessing();
		$.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				populateManifest(req, manifestNumberObj);
			}
		});
	} else {
		clearGridValues(rowId);
		manifestNumberObj.value = "";
		setTimeout(function() {
			manifestNumberObj.focus();
		}, 10);
	}
}

/**
 * populateManifest
 * 
 * @param req
 * @param manifestNumberObj
 * @author sdalli
 */
function populateManifest(ajaxResp, manifestNumberObj) {
	var rowId = getRowId(manifestNumberObj, "manifestNos");
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			clearRowById(rowId);
			setTimeout(function() {
				document.getElementById("manifestNos" + rowId).focus();
			}, 10);
		} else {
			flushManifest(responseText, rowId, manifestNumberObj);
		}
	}

}

/**
 * flushManifest
 * 
 * @param bplOutManifestDoxTOValues
 * @param rowId
 * @param manifestNumberObj
 * @author sdalli
 */
function flushManifest(bplOutManifestDoxTOValues, rowId, manifestNumberObj) {

	var branchOfficType = $("#branchOfficeType").val();
	var loginOfficeType = $("#loginOfficeType").val();
	var transCity;

	var loginOfficeId = document.getElementById("loginCityId").value;
	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	var gridDstnCityId = "";
	var gridDstnOfficeId = "";

	if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)) {
		if (!isNull(bplOutManifestDoxTOValues.destinationCityTO.cityId)) {
			gridDstnCityId = bplOutManifestDoxTOValues.destinationCityTO.cityId;

		}
	}
	if (!isNull(bplOutManifestDoxTOValues.destinationOfficeTO)) {
		if (!isNull(bplOutManifestDoxTOValues.destinationOfficeTO.officeId)) {
			gridDstnOfficeId = bplOutManifestDoxTOValues.destinationOfficeTO.officeId;
		}

	}

	// Pure Route checking logic
	if (bplOutManifestDoxTOValues.receivedStatus=='E') {
		populateGridDetails(bplOutManifestDoxTOValues, rowId, manifestNumberObj);
	} else {
		if (isPureRoute) {

			if (loginOfficeType == branchOfficType
					&& manifestType == MANIFEST_TYPE_TRANSHIPMENT) {
				enableFieldById("destCity");
				transCity = document.getElementById("destCity").value;
				disableFieldById("destCity");
			} else {
				transCity = document.getElementById("destCity").value;
			}

			var isMulti = document.getElementById("isMulDest").value;

			if (manifestType == "PURE" && isMulti == "Y"
					&& (transCity == gridDstnCityId)) {
				populateGridDetails(bplOutManifestDoxTOValues, rowId,
						manifestNumberObj);
			} else {
				if (isRouteServiceable) {
					if (!getRouteByOriginCityAndDestCity(
							bplOutManifestDoxTOValues, gridDstnCityId,
							manifestNumberObj, rowId)) {
						return;
					}
				}
				if (isPureRouteCheckingForDestnOfficeId) {
					if (!isPureRouteCheckingForDestnOfficeIds(
							bplOutManifestDoxTOValues, gridDstnOfficeId,
							manifestNumberObj, rowId)) {
						return;
					}
				} else {
					if (!isPureRouteChecking(bplOutManifestDoxTOValues,
							gridDstnCityId, manifestNumberObj, rowId)) {
						return;
					}
				}
			}
		}
		// Transhipment Route checking logic
		if (isTranshipmentRoute) {
			if (loginOfficeType == branchOfficType
					&& manifestType == MANIFEST_TYPE_TRANSHIPMENT) {
				populateGridDetails(bplOutManifestDoxTOValues, rowId,
						manifestNumberObj);
			} else if (isRouteServiceable) {
				if (loginOfficeId != gridDstnCityId) {
					if (!getRouteByOriginCityAndDestCity(
							bplOutManifestDoxTOValues, gridDstnCityId,
							manifestNumberObj, rowId)) {
						return;
					}
					if (!isTranshipmentRouteChecking(bplOutManifestDoxTOValues,
							gridDstnCityId, manifestNumberObj, rowId)) {
						return;
					}

				} else {
					clearGridValues(rowId);
					alert("Logged in city and dectination city of the packet are same.");
					manifestNumberObj.value = "";
					manifestNumberObj.focus();
					manifestNumberObj.readOnly = false;
				}
			}

		}

		if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)) {
			if (!isPureRoute && !isTranshipmentRoute) {
				populateGridDetails(bplOutManifestDoxTOValues, rowId,
						manifestNumberObj);
			}

		} else {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);
		}
	}

	hideProcessing();
}

function populateGridDetails(bplOutManifestDoxTOValues, rowId,
		manifestNumberObj) {
	manifestNumber = manifestNumberObj;
	bookingDetail = bplOutManifestDoxTOValues;
	// getWtFromWMForOGM();
	capturedWeightForManifest(-1);
}

/**
 * enableHeaderDropDown
 * 
 * @author sdalli
 */
function enableHeaderDropDown() {
	if (document.getElementById("dateTime").disabled == true) {
		document.getElementById("dateTime").disabled = false;
	}
	if (document.getElementById("manifestNo").disabled == true) {
		document.getElementById("manifestNo").disabled = false;
	}
	if (document.getElementById("manifestType").disabled == true) {
		document.getElementById("manifestType").disabled = false;
	}
	if (document.getElementById("destRegionId").disabled == true) {
		document.getElementById("destRegionId").disabled = false;
	}
	if (document.getElementById("destCity").disabled == true) {
		document.getElementById("destCity").disabled = false;
	}
	if (document.getElementById("destOfficeType").disabled == true) {
		document.getElementById("destOfficeType").disabled = false;
	}
	if (document.getElementById("destOffice").disabled == true) {
		document.getElementById("destOffice").disabled = false;
	}
	if (document.getElementById("bagLockNo").disabled == true) {
		document.getElementById("bagLockNo").disabled = false;
	}
	if (document.getElementById("rfidNo").disabled == true) {
		document.getElementById("rfidNo").disabled = false;
	}

}

/**
 * setTotalWeightBPL
 * 
 * @author sdalli
 */
function setTotalWeightBPL() {
	var finalWeight = 0;
	var weightInFraction = 0;
	var table = document.getElementById('bplOutManifestDoxTab');
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weightKg = document.getElementById("weight" + rowId).value;
		var weightGM = document.getElementById("weightGm" + rowId).value;
		if (!isNull(weightKg) || !isNull(weightGM)) {
			weightInFraction = weightKg + "." + weightGM;
			if (!isNull(weightInFraction)) {
				finalWeight += parseFloat(weightInFraction);
			}
		}
	}
	finalWeight += "";
	document.getElementById("finalWeight").value = finalWeight;
	// header Weight population
	weightPopulationInHeader(finalWeight);

}

/**
 * getManifestId
 * 
 * @param checkObj
 * @author sdalli
 */
function getManifestId(checkObj) {
	var manifestIdListAtGrid = document.getElementById("manifestIdListAtGrid").value;
	var rowId = getRowId(checkObj, "chk");

	if (isNull(manifestIdListAtGrid)) {
		var manifestId = document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	} else {
		var manifestId = manifestIdListAtGrid + ","
				+ document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	}
}

/**
 * searchManifestForBPL
 * 
 * @param obj
 * @author sdalli
 */
// Search Manifest Details by BPL No#..
function searchManifestForBPL() {

	if ($('#bplOutManifestDoxTab').dataTable().fnGetData().length < 1) {
		addBplOutManifestRows();
	}
	// setIFrame();
	var obj = document.getElementById("manifestNo");
	var manifestNO = document.getElementById("manifestNo").value;
	if (isValidBplNo(obj)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		url = "./bplOutManifestDox.do?submitName=searchManifestDtlsForBPL&manifestNo="
				+ manifestNO + "&loginOfficeId=" + loginOffceID;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestDetails(req);
			}
		});
	} else {
		alert("Please provide BPL No.");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
	}
}

function printCallBackManifestDetails(ajaxResp) {

	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
			document.getElementById("manifestNo").value = "";
			document.getElementById("manifestNo").focus();

		} else {
			searchDataPopulate(responseText);
		}
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
 * searchDataPopulate
 * 
 * @param data
 * @author sdalli
 */
function searchDataPopulate(manifestTO) {
	if (!isNull(manifestTO)) {
		document.getElementById("dateTime").value = manifestTO.manifestDate;
		document.getElementById("manifestNo").value = manifestTO.manifestNo;

		document.getElementById("loginOfficeId").value = manifestTO.loginOfficeId;
		document.getElementById("manifestId").value = manifestTO.manifestId;
		document.getElementById("manifestStatus").value = manifestTO.manifestStatus;
		if (!isNull(manifestTO.manifestProcessTo)) {
			document.getElementById("manifestProcessId").value = manifestTO.manifestProcessId;
			document.getElementById("manifestProcessNo").value = manifestTO.processNo;

		}
		$('#outMnfstDestIds').val(manifestTO.outMnfstDestIds);

		if (manifestTO.bplManifestType == 'PURE'
				|| manifestTO.bplManifestType == 'pure') {
			document.getElementById("manifestType").value = "PURE";
		} else if (manifestTO.bplManifestType == 'TRANS'
				|| manifestTO.bplManifestType == 'TRANS') {
			document.getElementById("manifestType").value = "TRANS";

		}

		if (!isNull(manifestTO.destRegionTO)) {
			document.getElementById("destRegionId").value = manifestTO.destRegionTO.regionId;
		} else {
			document.getElementById("destRegionId").value = manifestTO.destinationOffcId;
		}

		if (!isNull(manifestTO.destinationCityTO)) {
			addOptionTODropDown("destCity",
					manifestTO.destinationCityTO.cityName,
					manifestTO.destinationCityTO.cityId);
			document.getElementById("destCity").value = manifestTO.destinationCityTO.cityId;
		}
		if (manifestTO.multiDestinations != null) {
			document.getElementById("multiDest").value = manifestTO.multiDestinations;
		}
		if (!isNull(manifestTO.officeTypeId)) {
			document.getElementById("destOfficeType").value = manifestTO.officeTypeId
					+ "~" + manifestTO.officeCode;
		}
		if (!isNull(manifestTO.destOfficeType)) {
			document.getElementById("destOfficeType").value = manifestTO.destOfficeType;

		}

		if (!isNull(manifestTO.destinationOfficeId)) {
			addOptionTODropDown("destOffice", manifestTO.destinationOfficeName,
					manifestTO.destinationOfficeId + "~"
							+ manifestTO.bplDestOfficeCityId);
			document.getElementById("destOffice").value = manifestTO.destinationOfficeId
					+ "~" + manifestTO.bplDestOfficeCityId;
		} else if (manifestTO.isMulDestination == "Y") {
			addOptionTODropDown("destOffice", "ALL", "0~0");
			document.getElementById("destOffice").value = "0~0";
			document.getElementById("destRegionId").value = manifestTO.destinationOfficeId;
			document.getElementById("isMulDest").value = "Y";
		}

		document.getElementById("bagLockNo").value = manifestTO.bagLockNo;
		document.getElementById("bagRFID").value = manifestTO.bagRFID;
		document.getElementById("rfidNo").value = manifestTO.rfidNo;
		document.getElementById("finalWeight").value = manifestTO.finalWeight
				+ "";
		var convertWeightInString = manifestTO.finalWeight + "";
		weightPopulationInHeader(convertWeightInString);

		enableHeaderDropDown();
		validateBranchHubOffice();

		var rowId = 1;
		for ( var i = 0; i < manifestTO.bplOutManifestDoxDetailsTOList.length; i++) {

			populateBPLManifestGridDetailsTO(
					manifestTO.bplOutManifestDoxDetailsTOList[i], rowId++);

		}

		disableAllHeaderDtls();
		if (!isNull(manifestTO.manifestStatus)) {
			if (manifestTO.manifestStatus == 'C'
					|| manifestTO.manifestStatus == 'c') {
				disableForClose();
			}
		}

	}
	hideProcessing();
}

/**
 * populateBPLManifestGridDetailsTO
 * 
 * @param bplOutManifestDoxDetailsTOList
 * @param rowId
 * @author sdalli
 */
function populateBPLManifestGridDetailsTO(bplOutManifestDoxDetailsTOList, rowId) {
	if (bplOutManifestDoxDetailsTOList.isActive == "Y"
			|| bplOutManifestDoxDetailsTOList.isActive == "") {
		document.getElementById("manifestNos" + rowId).value = bplOutManifestDoxDetailsTOList.manifestNo;
		document.getElementById("manifestNos" + rowId).readOnly = true;
		document.getElementById("manifestIds" + rowId).value = bplOutManifestDoxDetailsTOList.manifestId
				+ "";
		document.getElementById("manifestMappedEmbeddeId" + rowId).value = bplOutManifestDoxDetailsTOList.mapEmbeddedManifestId;
		var weight = bplOutManifestDoxDetailsTOList.weight + "";
		// if(!isNull(weight) ){
		weightPopulateMtd(weight, rowId);
		document.getElementById("weight" + rowId).readOnly = true;
		document.getElementById("weightGm" + rowId).readOnly = true;
		// }
		document.getElementById("destCitys" + rowId).value = bplOutManifestDoxDetailsTOList.destCity;
		document.getElementById("destCitys" + rowId).readOnly = true;
		
		if(!isNull(bplOutManifestDoxDetailsTOList.noOfConsignment)){
			jQuery("#cnCount" + rowId).val(bplOutManifestDoxDetailsTOList.noOfConsignment);
		} else {
			jQuery("#cnCount" + rowId).val("E");
		}
		if (isNull(bplOutManifestDoxDetailsTOList.bplManifestType)) {
			if (bplOutManifestDoxDetailsTOList.manifestOpenType == 'O') {
				jQuery("#manifestType" + rowId).val("OGM");
			} else if(bplOutManifestDoxDetailsTOList.manifestOpenType == 'P') {
				jQuery("#manifestType" + rowId).val("Open");
			}
		} else {
			if (bplOutManifestDoxDetailsTOList.bplManifestType == 'T') {
				jQuery("#manifestType" + rowId).val("Transhipment");
			} else if (bplOutManifestDoxDetailsTOList.bplManifestType == 'P') {
				jQuery("#manifestType" + rowId).val("Pure");
			}
		}

	}
}

/**
 * validateHeaderDetails
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateHeaderDetails() {
	var msg = "Please provide : ";
	var isValid = true;
	var focusObj = manifestNo;
	var manifestNo = document.getElementById("manifestNo");
	var originOffice = document.getElementById("loginOfficeName");
	var manifestType = document.getElementById("manifestType");
	var destinationRegionId = document.getElementById("destRegionId");
	var destCity = document.getElementById("destCity");
	var destOfficeType = document.getElementById("destOfficeType");
	var destOffice = document.getElementById("destOffice");
	var bagLockNo = document.getElementById("bagLockNo");

	if (isNull(manifestNo.value)) {
		if (isValid)
			focusObj = manifestNo;
		msg = msg + ((!isValid) ? ", " : "") + "Manifest No";
		isValid = false;
	}

	if (isNull(originOffice.value)) {
		if (isValid)
			focusObj = originOffice;
		msg = msg + ((!isValid) ? ", " : "") + "Origin Office";
		isValid = false;
	}
	if (isNull(manifestType.value)) {
		if (isValid)
			focusObj = manifestType;
		msg = msg + ((!isValid) ? ", " : "") + "Manifest Type";
		isValid = false;
	}

	if (isNull(destinationRegionId.value)) {
		if (isValid)
			focusObj = destinationRegionId;
		msg = msg + ((!isValid) ? ", " : "") + "Destination Region";
		isValid = false;
	}

	if (isNull(destCity.value)) {
		if (isValid)
			focusObj = destCity;
		msg = msg + ((!isValid) ? ", " : "") + "Destination City";
		isValid = false;
	}

	if (isNull(destOfficeType.value)) {
		if (isValid)
			focusObj = destOfficeType;
		msg = msg + ((!isValid) ? ", " : "") + "Office Type";
		isValid = false;

	}

	if (isNull(destOffice.value)) {
		if (isValid)
			focusObj = destOffice;
		msg = msg + ((!isValid) ? ", " : "") + "Destination Office";
		isValid = false;
	}
	if (isNull(bagLockNo.value)) {
		if (isValid)
			focusObj = bagLockNo;
		msg = msg + ((!isValid) ? ", " : "") + "Bag Lock no.";
		isValid = false;
	}
	if (!isValid) {
		alert(msg);
		setTimeout(function() {
			focusObj.focus();
		}, 10);
	}
	return isValid;
}

/**
 * validateSelectedRow
 * 
 * @param rowId
 * @returns {Boolean}
 * @author sdalli
 */
function validateSelectedRow(rowId) {

	var bplNo = getDomElementById("manifestNos" + rowId);
	var bPLNo = bplNo.value;
	var lineNum = "at line :" + rowId;

	if (isNull(bPLNo)) {
		alert("Please provide Manifest No " + lineNum);
		setTimeout(function() {
			bplNo.focus();
		}, 10);
		return false;
	}
	return true;
}

// common starts
/**
 * inputDisable
 * 
 * @author sdalli
 */
function inputDisable() {
	jQuery(":input").attr("readonly", true);
}
/**
 * inputEnable
 * 
 * @author sdalli
 */
function inputEnable() {
	jQuery(":input").attr("readonly", false);
	jQuery(":input").attr("disabled", false);
}
/**
 * dropdownDisable
 * 
 * @author sdalli
 */
function dropdownDisable() {
	jQuery("select").attr("disabled", 'disabled');
}
/**
 * dropdownEnable
 * 
 * @author sdalli
 */
function dropdownEnable() {
	jQuery("select").attr("disabled", false);

}
/**
 * buttonDisable
 * 
 * @author sdalli
 */
function buttonDisable() {
	jQuery(":button").attr("disabled", true);

}
/**
 * buttonEnable
 * 
 * @author sdalli
 */
function buttonEnable() {
	jQuery(":button").attr("disabled", false);

}

/**
 * enableAll
 * 
 * @author sdalli
 */
function enableAll() {
	buttonEnable();
	inputEnable();
	dropdownEnable();
}
function disableFieldById(fieldId) {
	document.getElementById(fieldId).disabled = true;
}
/**
 * buttonDisabled
 * 
 * @param btnName
 * @param styleClassToAdd
 * @author sdalli
 */
function buttonDisabled(btnName, styleClassToAdd) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).addClass(styleClassToAdd);

}
/**
 * buttonEnabled
 * 
 * @param btnName
 * @param styleClassToRemove
 * @author sdalli
 */
function buttonEnabled(btnName, styleClassToRemove) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleClassToRemove);
}

/**
 * disableAllHeaderDtls Disable all the header details after searching BPL No#
 * 
 * @author sdalli
 */
function disableAllHeaderDtls() {
	jQuery("#dateTime").attr("disabled", true);
	jQuery("#manifestNo").attr("disabled", true);
	jQuery("#loginOfficeName").attr("disabled", true);
	jQuery("#manifestType").attr("disabled", true);
	jQuery("#destRegionId").attr("disabled", true);
	jQuery("#destCity").attr("disabled", true);
	jQuery("#destOfficeType").attr("disabled", true);
	jQuery("#destOffice").attr("disabled", true);
	jQuery("#bagLockNo").attr("disabled", true);
	jQuery("#rfidNo").attr("disabled", true);
	jQuery("#finalKgs").attr("disabled", true);
	jQuery("#finalGms").attr("disabled", true);
}

/**
 * getTableLength
 * 
 * @param tableId
 * @returns
 * @author sdalli
 */
function getTableLength(tableId) {
	var tableee = getDomElementById(tableId);
	return tableee.rows.length;
}
/**
 * getTableRows
 * 
 * @param tableId
 * @returns
 * @author sdalli
 */
function getTableRows(tableId) {
	var tableee = getDomElementById(tableId);
	return tableee.getElementsByTagName("tr");
}
/**
 * addOptionTODropDown
 * 
 * @param selectId
 * @param label
 * @param value
 * @author sdalli
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

// Common method for weight population
/**
 * weightPopulateMtd
 * 
 * @param WeightInString
 * @param rowId
 * @author sdalli
 */
function weightPopulateMtd(WeightInString, rowId) {
	var weightKgValue = null;
	var weightGmValue = null;
	// if (!isNull(WeightInString)) {
	weightKgValue = WeightInString.split(".");
	document.getElementById("weight" + rowId).value = weightKgValue[0];
	weightGmValue = weightKgValue[1];
	if (isNull(weightGmValue) || weightGmValue == "undefined") {
		weightGmValue = "000";
	} else if (weightGmValue.length == 1) {
		weightGmValue += "00";
	} else if (weightGmValue.length == 2) {
		weightGmValue += "0";
	}
	document.getElementById("weightGm" + rowId).value = weightGmValue;
	// }
}

/**
 * weightPopulationInHeader
 * 
 * @param finalWeight
 * @author sdalli
 */
// Common method for weight population header level
function weightPopulationInHeader(finalWeight) {
	var weightKgValue = finalWeight.split(".");
	document.getElementById("finalKgs").value = weightKgValue[0];
	var finalGms = weightKgValue[1];
	if (finalGms == undefined || finalGms == "undefined") {
		document.getElementById("finalGms").value = "000";
	} else {
		finalGms = weightKgValue[1].substring(0, 3);
		if (isNull(finalGms) || finalGms == "undefined") {
			finalGms = "000";
		} else if (finalGms.length == 1) {
			finalGms += "00";
		} else if (finalGms.length == 2) {
			finalGms += "0";
		}
		document.getElementById("finalGms").value = finalGms;
	}
}
// common ends

/**
 * isValidWeightTolerance
 * 
 * 
 * @author sdalli
 */
function isValidWeightTolerance() {
	var finalWeight = document.getElementById("finalWeight").value;
	if (isNull(finalWeight)) {
		return;
	}
	if (isNANCheck(finalWeight)) {
		finalWeight = 0.0;
	}
	weight = parseFloat(finalWeight);
	var toleranceWeight = parseFloat(maxWeightAllowed)
			+ (parseFloat(maxWeightAllowed) * (parseFloat(maxTolerenceAllowed) / 100));

	if (weight > toleranceWeight) {
		// clear grid details
	} else if (weight == toleranceWeight) {
		document.getElementById("close").focus();
	}
}

/**
 * mergeWeighInGmKg
 * 
 * @param Obj
 * @author sdalli
 */
function mergeWeighInGmKg(Obj) {
	var finalKgs = document.getElementById("finalKgs").value;
	var finalGms = document.getElementById("finalGms").value;
	var finalWeight = 0.0;
	var weightInFraction = 0;
	if (!isNull(finalKgs) && !isNull(finalGms)) {
		weightInFraction = finalKgs + "." + finalGms;
		finalWeight = parseFloat(weightInFraction);
		document.getElementById("finalWeight").value = finalWeight;
		if (!isNull(finalWeight)) {
			isValidWeightTolerance(Obj);
		}
	}

}

/**
 * isValidSeriesProduct
 * 
 * @param productId
 * @returns {Boolean}
 * @author sdalli
 */
function isValidSeriesProduct(productId, currentProductSeries, rowIdIn) {
	var table = document.getElementById('bplOutManifestDoxTab');
	var firstRowProductSeries = "-";

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		/*
		 * var productIds = document.getElementById("productIds" + rowId).value;
		 * var productSeries = document.getElementById("productSeries" +
		 * rowId).value;
		 */
		if (rowId == 1) {
			firstRowProductSeries = document.getElementById("productSeries"
					+ rowId).value;
		}
		if (!isNull(currentProductSeries) || !isNull(firstRowProductSeries)) {
			if (currentProductSeries == "C") {
				if (firstRowProductSeries != currentProductSeries) {
					alert("A manifest containing C- Series should allow only products of same Type.");
					document.getElementById("manifestNos" + rowIdIn).value = "";
					document.getElementById("weight" + rowIdIn).value = "";
					document.getElementById("weightGm" + rowIdIn).value = "";
					document.getElementById("destCitys" + rowIdIn).value = "";
					document.getElementById("manifestNos" + rowIdIn).focus();
					return false;
				}
			}
		}

	}
	return true;
}

function clearGridValues(rowId) {
	document.getElementById("manifestNos" + rowId).value = "";
	document.getElementById("weight" + rowId).value = "";
	document.getElementById("weightGm" + rowId).value = "";
	document.getElementById("destCitys" + rowId).value = "";
}
/**
 * getAllOfficeIdsBPL
 * 
 * @param obj
 * @author sdalli
 */

function getAllOfficeIdsBPL(obj) {
	if (!isNull(obj.value)) {
		officeId = obj.value.split("~")[0];
		if (officeId == 0) {
			document.getElementById("isMulDest").value = "Y";
			var officeIds = "";
			for ( var i = 0; i < obj.options.length; i++) {
				officeIds += obj.options[i].value.split("~")[0] + ",";
			}
			document.getElementById("multiDest").value = officeIds;
		} else {
			document.getElementById("isMulDest").value = "N";
		}
	}
}

/**
 * maxWtCheck
 * 
 * @param rowId
 * @author sdalli
 */
function maxWtCheck(rowId) {
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	var maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;

	maxWeightToleranceCheckBPL(rowId, maxManifestAllowed, maxWeightAllowed,
			maxTolerenceAllowed);
}

/**
 * disableForAfterSave
 * 
 * @author sdalli
 */
function disableForAfterSave() {
	for ( var i = 1; i <= 30; i++) {
		if (!isNull(getDomElementById("manifestNos" + i).value))
			disableGridRowById(i);
	}
}

function disableAllCheckBox() {
	$("input[type='checkbox']").prop("disabled", true);
}

/**
 * cancelPage
 * 
 * @author sdalli
 */
function cancelPage() {
	var url = "./bplOutManifestDox.do?submitName=viewBPLOutManifestDox";
	window.location = url;
}

function refreshPage() {
	var url = "./bplOutManifestDox.do?submitName=viewBPLOutManifestDox";
	window.location = url;
	/*
	 * document.bplOutManifestDoxForm.action = url;
	 * document.bplOutManifestDoxForm.submit();
	 */
}
/**
 * hideFieldById
 * 
 * @param fieldId
 * @author sdalli
 */
function hideFieldById(fieldId) {
	document.getElementById(fieldId).style.visibility = "hidden";
}

function validateGridDetails() {
	var flag = true;
	var isAtleastOneRecEntered = false;
	var manifestNo = "";
	for ( var count = 1; count <= maxManifestAllowed; count++) {
		manifestNo = document.getElementById("manifestNos" + count).value;
		if (!isNull(manifestNo)) {
			var cnNo = "";
			isAtleastOneRecEntered = true;
			cnNo = document.getElementById("manifestNos" + count).value;
			if (isNull(cnNo)) {
				flag = false;
				alert("Please enter manifest number");
				document.getElementById("manifestNos" + count).focus();
			}
		}
	}
	for ( var count = (parseIntNumber(maxManifestAllowed) + 1); count <= maxManifestAllowed; count++) {
		var cmailNo = "";
		cmailNo = document.getElementById("manifestNos" + count).value;
		if (isNull(cmailNo)) {
			flag = false;
			alert("Please enter manifestNo number");
			document.getElementById("manifestNos" + count).focus();
		} else {
			isAtleastOneRecEntered = true;
		}
	}
	if (!isAtleastOneRecEntered) {
		flag = false;
		alert("Atleast one Manifest should be there in a Bag");
		document.getElementById("manifestNos" + count).focus();
	}
	return flag;
}

/**
 * fnValidateNumber
 * 
 * @param ManifestObj
 * @returns {Boolean}
 * @author sdalli
 */
function fnValidateNumber(ManifestObj) {
	if (!isNull(ManifestObj.value)) {
		// var count = getRowId(obj, "manifestNos");
		if (isDuplicateBPL(ManifestObj)) {
			alert("Manifest Number is already in Use.");
			document.getElementById(ManifestObj.id).value = "";
			setTimeout(function() {
				document.getElementById(ManifestObj.id).focus();
			}, 10);
			return true;
		}
	}
	return false;
}

/**
 * isDuplicateBPL
 * 
 * @param ManifestObj
 * @returns {Boolean}
 * @author sdalli
 */
function isDuplicateBPL(ManifestObj) {
	var table = document.getElementById("bplOutManifestDoxTab");
	var selectedRowId = getRowId(ManifestObj, "manifestNos");
	var currentOgm = ManifestObj.value;

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var prevOgm = document.getElementById("manifestNos" + rowId).value;
		if (selectedRowId != rowId) {
			if ($.trim(prevOgm.toUpperCase()) == $.trim(currentOgm
					.toUpperCase())) {

				return true;
			}
		}
	}
	return false;
}

/**
 * buttonDisabled
 * 
 * @param btnName
 * @param styleClassToAdd
 * @author sdalli
 */
function buttonDisabled(btnName, styleClassToAdd) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).addClass(styleClassToAdd);
}

/**
 * clearDropDownList
 * 
 * @param selectId
 * @author sdalli
 */
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");
}

/**
 * reachedMaxRowCount
 * 
 * @param ManifestObj
 * @returns {Boolean}
 * @author sdalli
 */

function focusOnclose() {
	if (isAllRowInGridFilled(maxManifestAllowed)) {
		var close = document.getElementById("close");
		setTimeout(function() {
			close.focus();
		}, 20);
	}
}

/**
 * isGridEmptyOnSave
 * 
 * @author sdalli
 */
function isGridEmptyOnSave() {
	var table = document.getElementById("bplOutManifestDoxTab");
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var manifestNo = document.getElementById("manifestNos" + rowId).value;
		if (isNull(manifestNo)) {
			document.getElementById("manifestStatus").value = "O";
			break;
		} else {
			document.getElementById("manifestStatus").value = "C";
		}

	}
}

/**
 * 
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidBplNo(manifestNoObj) {
	// Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"BPL No. should contain 10 characters only!");
		return false;
	}

	if (!numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}
/**
 * 
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidPacketNo(manifestNoObj) {
	// City Code+7 digits :: BOY1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);

	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}

	if (!numpattern.test(manifestNoObj.value.substring(3))) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. Format is not correct!");
		return false;
	}
	return true;
}
/**
 * 
 * @param obj
 * @param msg
 */
function clearFocusAlertMsg(obj, msg) {
	obj.value = "";
	obj.focus();
	alert(msg);
}

function deleteBag() {
	try {
		var tableId = "bplOutManifestDoxTab";
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		var isChecked = false;
		for ( var i = 1; i < rowCount; i++) {
			var chkbox = table.rows[i].cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				isChecked = true;
				var rowId = getRowId(chkbox, "chk");
				getDomElementById("chk" + rowId).checked = false;
				setTotalWeightBPL();

			}
		}
		if (!isChecked) {
			alert("Please check at least one row.");
		}
		updateSerialNoVal("bplOutManifestDoxTab");

		setTotalWeightBPL();
	} catch (e) {
		// alert(e);
	}
}

function setDeletedIds(deletedId) {
	if (isNull(deletedId)) {
		return;
	}
	var deletedIdsObj = document.getElementById("deletedIds");
	if (isNull(deletedIdsObj.value)) {
		deletedIdsObj.value = deletedId;
	} else {
		deletedIdsObj.value += "~" + deletedId;
	}
}

function updateSerialNoVal(tableId) {
	try {
		var table = document.getElementById(tableId);

		for ( var i = 1; i < table.rows.length; i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var serialNo = document.getElementById("serialNo" + rowId);
			if (serialNo.innerHTML != i) {
				serialNo.innerHTML = i;
			}
		}
	} catch (e) {

	}
}

function validateWeightTolence(WeightValue, Obj) {
	var rowId = getRowId(Obj, "manifestNos");
	var finalWeight = document.getElementById("finalWeight").value;
	if (isNull(finalWeight)) {
		finalWeight = "0";
	}

	var totalWeight = parseFloat(WeightValue) + parseFloat(finalWeight);
	var maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;

	var toleranceWeight = parseFloat(maxWeightAllowed)
			+ (parseFloat(maxWeightAllowed) * (parseFloat(maxTolerenceAllowed) / 100));
	if (totalWeight > toleranceWeight && rowId == 1) {

		alert("BPL weight exceeds the limit");
		disableForSave();
		inputDisable();

	} else if (totalWeight > toleranceWeight) {

		alert("BPL weight exceeds the limit");

		clearGridTextBox(rowId);
		document.getElementById("manifestNos" + rowId).readOnly = false;
		;
		document.getElementById("manifestNos" + rowId).focus();

	} else if (totalWeight == toleranceWeight) {
		disableForSave();
		alert("Maximum weight limit is reached ");
		inputDisable();

	}
	setTotalWeightBPL();
}

/**
 * clearGridTextBox
 * 
 * @param rowId
 * @author sdalli
 */
function clearGridTextBox(rowId) {
	document.getElementById("manifestNos" + rowId).value = "";
	document.getElementById("weight" + rowId).value = "";
	document.getElementById("weightGm" + rowId).value = "";
	document.getElementById("destCitys" + rowId).value = "";
}

/**
 * enableFieldById
 * 
 * @param fieldId
 * @author sdalli
 */
function enableFieldById(fieldId) {
	document.getElementById(fieldId).disabled = false;
}

/**
 * @Desc check manifest number at header level
 */
function isValidManifestNoForHeader(domElement) {
	if (!isNull(domElement.value)) {
		getDomElementById("seriesType").value = BPL_SERIES;

		isValidManifestNo(domElement, 'H');
	}
}

function printBPL() {
	if (confirm("Do you want to Print?")) {
		var manifestNoObj = document.getElementById("manifestNo");

		if (isNull(manifestNoObj.value)) {
			alert("Please Enter BPL No.");
			document.getElementById("manifestNo").value = "";
			document.getElementById("manifestNo").focus();
			return false;
		}

		if (!isValidBplNo(manifestNoObj)) {
			return;
		}

		var manifestStatus = getDomElementById("manifestStatus").value;
		if (manifestStatus == "O") {
			alert("Only closed manifest can be printed");
		} else {
			var manifestNo = document.getElementById("manifestNo");
			var loginOffceID = document.getElementById('loginOfficeId').value;
			if (!isNull(manifestNo.value) && !isNull(loginOffceID)) {
				url = "./bplOutManifestDox.do?submitName=printBPLOutManifestDox&manifestNo="
						+ manifestNo.value + "&loginOfficeId=" + loginOffceID;
				var w = window
						.open(url, 'myPopUp',
								'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			}
			/*
			 * var flag= true; while(flag){ if(printFlag=="Y"){ flag=false;
			 * refreshFlag="Y"; window.frames['iFrame'].focus();
			 * window.frames['iFrame'].print(); break; } }
			 */
		}
	}

	/*
	 * var confirm1 = confirm("Do you want to print BPL Out Manifest Details!");
	 * if (confirm1) { printBplOutManifestDox(); }
	 */
	/*
	 * if (!confirm1) { cancelPage(); }
	 */
}

function printBplOutManifestDox() {
	/*
	 * var manifestNo = document.getElementById("manifestNo"); var loginOffceID =
	 * document.getElementById('loginOfficeId').value; if
	 * (!isNull(manifestNo.value) && !isNull(loginOffceID)) { showProcessing();
	 * url =
	 * "./bplOutManifestDox.do?submitName=printBPLOutManifestDox&manifestNo=" +
	 * manifestNo.value + "&loginOfficeId=" + loginOffceID;
	 * document.bplOutManifestDoxForm.action = url;
	 * document.bplOutManifestDoxForm.submit(); hideProcessing(); }
	 */

}

function saveOrCloseOutManifestMBPL(action) {
	var maxRowAllowed = parseInt(document.getElementById("maxManifestAllowed").value);
	if (isAllRowInGridFilled(maxRowAllowed)) {
		action = 'close';
	}

	// check weight tolerance during save or close
	var maxWeightAllowed = getDomElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = getDomElementById("maxTolerenceAllowed").value;
	var maxAllowedWtWithTollrence = parseFloat(maxWeightAllowed)
			+ (parseFloat(maxWeightAllowed) * parseFloat(maxTolerenceAllowed) / 100);
	if (maxAllowedWtWithTollrence == (getDomElementById("finalWeight").value)) {
		action = 'close';
	} else if (getDomElementById("weights" + 1).value > maxAllowedWtWithTollrence) {
		// If first row exceeds weight limit
		action = 'close';
	}

	if (action == 'save') { // Open
		document.getElementById("manifestStatus").value = MANIFEST_STATUS_OPEN;
	} else if (action == 'close') { // Close
		document.getElementById("manifestStatus").value = MANIFEST_STATUS_CLOSE;
	}
	getDomElementById("action").value = action;

	enableHeaderDropDown();
	if (validateHeaderDetails() && validateGridDetails()) {
		showProcessing();
		var url = './bplOutManifestDox.do?submitName=saveOrUpdateBPLOutManifestDox';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#bplOutManifestDoxForm").serialize(),
			success : function(req) {
				printCallBackManifestSave(req, action);
			}
		});

	}
}

function printCallBackManifestSave(ajaxResp, action) {
	var manifestDetailsTO = null;
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			document.getElementById("manifestNo").value = "";
			document.getElementById("manifestNo").focus();
		} else {
			manifestDetailsTO = responseText;
			document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
			document.getElementById("manifestProcessId").value = manifestDetailsTO.manifestProcessTo.manifestProcessId;
			if (action == 'save') {
				alert(manifestDetailsTO.successMessage);
				hideProcessing();
				$('#bplOutManifestDoxTab').dataTable().fnClearTable();
				searchManifestForBPL();
				// disableForSave();

			} else if (action == 'close') {
				// alert(manifestDetailsTO.successMessage);
				var confirm1 = confirm("Manifest closed successfully.");
				hideProcessing();
				disableForClose();
				if (confirm1) {
					searchManifestForBPL();
					printBPL();
					refreshPage();
					/*
					 * setTimeout(function(){printBPL();}, 3000);
					 * setTimeout(function(){ if(refreshFlag=="Y"){
					 * refreshPage(); } }, 4000);
					 */
				} else {
					refreshPage();
				}
			}
		}
	}
}

/**
 * @Desc If all row is filled then CLOSE automatically.
 * @param maxRowAllowed
 * @returns {Boolean}
 */
function isAllRowInGridFilledBPL(maxRowAllowed) {
	for ( var i = 1; i <= maxRowAllowed; i++) {
		if (isNull(getDomElementById("manifestNos" + i).value))
			return false;
	}
	return true;
}

function getOfficesByCityAndofficeTypeBPL() {

	var destCityId = document.getElementById("destCity").value;
	var officeType = document.getElementById("destOfficeType").value;
	var officeTypeId = officeType.split("~")[0];
	url = './outManifestDox.do?submitName=getAllOfficesByCityAndOfficeType&cityId='
			+ destCityId + "&officeTypeId=" + officeTypeId;
	ajaxCallWithoutForm(url, printAllOfficesBPL);
}

function printAllOfficesBPL(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			showDropDownBySelected("destCity", "");
			createDropDown("destOffice", "", "SELECT");
			alert("No offices found for the selected city");
			document.getElementById('destCity').focus();
			showDropDownBySelected("destRegionId", "");
			alert(error);
			document.getElementById('destRegionId').focus();
		}

		else {

			var content = document.getElementById('destOffice');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId + "~" + this.cityId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
			});

			var manifestType = document.getElementById("manifestType").value;
			if (manifestType == "TRANS") {
				$("#destOffice option[value='0~0']").remove();

			}
		}
	}
	hideProcessing();
}

function validateBranchHubOffice() {
	var destOfficeType = document.getElementById("destOfficeType").value;
	var destOfficeTypeCode = destOfficeType.split("~")[1];
	var manifestType = document.getElementById("manifestType").value;
	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	var originCityId = document.getElementById("originCityId").value;
	var isMulti = document.getElementById("isMulDest").value;

	// Origin Branch to Origin Hub with in same city
	if (originOfficeTypeCode == branchCode && destOfficeTypeCode == hubCode
			&& originCityId == OGMDstCityId) {
		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = true;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
	}
	// Origin Hub to Destination Hub with Different city
	if (originOfficeTypeCode == hubCode && destOfficeTypeCode == hubCode
			&& originCityId != OGMDstCityId) {
		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = true;
			isTranshipmentRoute = true;
		}
	}

	// Origin Branch to Destn Hub with Different city
	if (originOfficeTypeCode == branchCode && destOfficeTypeCode == hubCode
			&& originCityId != OGMDstCityId) {

		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = true;
			isTranshipmentRoute = true;
		}
	}

	// Origin Branch to Destn Branch
	if (originOfficeTypeCode == branchCode && destOfficeTypeCode == branchCode) {
		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = true;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = true;
			isTranshipmentRoute = true;
		}
	}

	// Origin hub to Destn Branch
	if (originOfficeTypeCode == hubCode && destOfficeTypeCode == branchCode
			&& originCityId != OGMDstCityId) {
		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = true;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = true;
			isTranshipmentRoute = true;
		}
	}

	if (isMulti == "Y") {
		if (manifestType == 'PURE') {
			isPureRoute = true;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
		if (manifestType == 'TRANS') {
			isPureRoute = false;
			isPureRouteCheckingForDestnOfficeId = false;
			isRouteServiceable = false;
			isTranshipmentRoute = false;
		}
	}
}

function isPureRouteChecking(bplOutManifestDoxTOValues, gridDstnCityId,
		manifestNumberObj, rowId) {

	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	if (!isNullWithOutZero(OGMDstCityId)) {
		if (OGMDstCityId != gridDstnCityId) {
			alert("OGM destination not served by the selected destination.");
			manifestNumberObj.value = "";
			manifestNumberObj.focus();
			manifestNumberObj.readOnly = false;
			hideProcessing();
			return false;
		} else {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);
		}
		return true;
	}

}
function isPureRouteCheckingForDestnOfficeIds(bplOutManifestDoxTOValues,
		gridDstnOfficeId, manifestNumberObj, rowId) {
	var OGMDestOffice = document.getElementById("destOffice").value;
	var OGMDestOfficeId = OGMDestOffice.split("~")[0];
	if (!isNullWithOutZero(OGMDestOfficeId)) {
		if (OGMDestOfficeId != gridDstnOfficeId) {
			alert("OGM destination not served by the selected destination.");
			manifestNumberObj.value = "";
			manifestNumberObj.focus();
			manifestNumberObj.readOnly = false;
			hideProcessing();
			return false;
		} else {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);
		}
	}

}

function isTranshipmentRouteChecking(bplOutManifestDoxTOValues, gridDstnCityId,
		manifestNumberObj, rowId) {
	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	if (OGMDstCityId != gridDstnCityId) {
		/*
		 * if (!isNullWithOutZero(OGMDstCityId)){ if (OGMDstCityId !=
		 * gridDstnCityId) {
		 */
		url = './bplOutManifestDox.do?submitName=isValidTransshipmentRoute&cityId='
				+ gridDstnCityId + "&transCity=" + OGMDstCityId;
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#bplOutManifestDoxForm").serialize(),
			success : function(req) {
				routeChecking(req, bplOutManifestDoxTOValues, gridDstnCityId,
						manifestNumberObj, rowId);
			}
		});
	} else {
		populateGridDetails(bplOutManifestDoxTOValues, rowId, manifestNumberObj);

	}
}

function routeChecking(ajaxResp, bplOutManifestDoxTOValues, gridDstnCityId,
		manifestNumberObj, rowId) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			manifestNumberObj.value = "";
			manifestNumberObj.focus();
			manifestNumberObj.readOnly = false;
			clearGridValues(rowId);
			setTotalWeightBPL();
			hideProcessing();
			return false;
		} else {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);

		}

	}
}

/**
 * getRouteByOriginCityAndDestCity
 * 
 * @author sdalli
 */
function getRouteByOriginCityAndDestCity(bplOutManifestDoxTOValues,
		gridDstnCityId, manifestNumberObj, rowId) {
	var originCityId = document.getElementById("originCityId").value;
	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	if (!isNull(originCityId) && !isNull(OGMDstCityId)) {
		var url = "./bplOutManifestDox.do?submitName=getRouteByOriginCityAndDestCity&originCityId="
				+ originCityId + "&destCityId=" + OGMDstCityId;
		showProcessing();
		$.ajax({
			url : url,
			success : function(req) {
				populateRouteId(req, bplOutManifestDoxTOValues, gridDstnCityId,
						rowId, manifestNumberObj);
			}
		});
	}

}

/**
 * populateRouteId
 * 
 * @param routeId
 * @author sdalli
 */
function populateRouteId(ajaxResp, bplOutManifestDoxTOValues, gridDstnCityId,
		rowId, manifestNumberObj) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			manifestNumberObj.value = "";
			manifestNumberObj.focus();
			manifestNumberObj.readOnly = false;
			return false;

		} else {
			if (isTranshipmentRoute) {
				isTranshipmentRouteChecking(bplOutManifestDoxTOValues,
						gridDstnCityId, manifestNumberObj, rowId);
			}

		}

	}
}

function clearDropDownValues4Header() {
	if (isConfirmChanges) {
		var manifestTypeObj = document.getElementById("manifestType");
		$("#destRegionId").val("");
		clearDropDownList("destCity");
		if (!isNull(manifestTypeObj.value) && manifestTypeObj.value != 'TRANS') {

			$("#destOfficeType").val("");
		}

		clearDropDownList("destOffice");
	}
}

function clearDropDownValOnDestnRegn4Header() {
	if (isConfirmChanges) {
		var manifestTypeObj = document.getElementById("manifestType");
		clearDropDownList("destCity");
		if (!isNull(manifestTypeObj.value) && manifestTypeObj.value != 'TRANS') {

			$("#destOfficeType").val("");
		}

		clearDropDownList("destOffice");
	}
}

function clearDropDownValForDeatCity() {
	if (isConfirmChanges) {
		var manifestTypeObj = document.getElementById("manifestType");
		if (!isNull(manifestTypeObj.value) && manifestTypeObj.value != 'TRANS') {
			$("#destOfficeType").val("");
		}
		$("#destOfficeType").val("");
		clearDropDownList("destOffice");
	}
}

function clearOriginOffice() {

	clearDropDownList("originOffice");
	$("#originOfficeType").val("");

}

/**
 * isLockNoIssued
 * 
 * @param data
 * @author sdalli
 */
function isLockNoIssued(data) {
	var lockNumberObj = document.getElementById("bagLockNo");
	var manifestIssueValidationTO = eval('(' + data + ')');
	hideProcessing();

	if (manifestIssueValidationTO.isIssued == "N") {
		clearFocusAlertMsg(lockNumberObj, manifestIssueValidationTO.errorMsg);
		return;
	}
}

/**
 * isValidLockNo
 * 
 * @param lockNoObj
 * @returns {Boolean}
 * @author sdalli
 */
function isValidLockNo(lockNoObj) {
	// Region Code+7digits :: R1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	lockNoObj.value = $.trim(lockNoObj.value);

	if (isNull(lockNoObj.value)) {
		return false;
	}

	if (lockNoObj.value.length != 8) {
		clearFocusAlertMsg(lockNoObj,
				"Lock No. should contain 8 characters only!");
		return false;
	}

	if (!lockNoObj.value.substring(0, 1).match(letters)
			|| !numpattern.test(lockNoObj.value.substring(1))) {
		clearFocusAlertMsg(lockNoObj, "Lock No. Format is not correct!");
		return false;
	}

	return true;
}

function valiedManifestType() {
	var manifestTypeObj = document.getElementById("manifestType");
	var destOfficeTypeObj = document.getElementById("destOfficeType");
	var loginOffType = getDomElementById("loginOfficeType").value;
	if (!isNull(manifestTypeObj.value)) {

		if (manifestTypeObj.value == 'TRANS'
				&& loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
						.toLowerCase()) {
			// disable office type and set Hub
			selectOptionByFirstSplitValue(destOfficeTypeObj, hubCode);
			disableFieldById("destOfficeType");
			if (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
					.toLowerCase()) {
				getOfficesByCityAndoffType();
			}
		} else {
			// enable and value empty
			enableFieldById("destOfficeType");
			if (!isRegion) {
				$("#destOfficeType").val("");

			}
		}
	}

}

function selectOptionByFirstSplitValue(selectObj, selectValue) {
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.split("~")[1] == selectValue) {
			selectObj.selectedIndex = i;
			break;
		}
	}
}

function isValiedDestnOffice() {
	var manifestTypeObj = document.getElementById("manifestType");
	var transCity = document.getElementById("destCity").value;
	if (!isNull(transCity)) {
		if (!isNull(manifestTypeObj.value) && manifestTypeObj.value == 'TRANS') {
			getOfficesByCityAndofficeTypeBPL();
		} else {
			getOfficesByCityAndofficeTypeBPL();
		}
	}
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("manifestNos" + rowId);
	return callEnterKey(e, focusId);

}
function callEnterKeyOnBagLockNo(e, bagLockObj) {
	if (bagLockObj.value.trim().length == 8
			|| bagLockObj.value.trim().length == 0) {
		rowId = 1;
		var focusId = document.getElementById("manifestNos" + rowId);
		return callEnterKey(e, focusId);
	}
}

/*
 * function getValuemanifestType(obj) { manifestType = obj.value; //
 * alert(manifestType); } function getValuedestRegion(obj) { destRegion =
 * obj.value; } function getValuedestCity(obj) { destCity = obj.value; }
 * function getValueofficeType(obj) { officeType = obj.value; } function
 * getValuedestOffice(obj) { destOffice = obj.value; }
 */

/**
 * checkGridEmpty grid clearing functionality
 * 
 * @param obj
 * @param idValue
 * @returns {Boolean}
 * @author sdalli
 */
function checkGridEmpty(obj, idValue) {
	var Id = obj.id;

	if (Id == "manifestType") {
		newManifestType = obj.value;
	}
	if (Id == "destRegionId") {
		newDestRegion = obj.value;
	}
	if (Id == "destCity") {
		newDestCity = obj.value;
	}
	if (Id == "destOfficeType") {
		newOfficeType = obj.value;
	}
	if (Id == "destOffice") {
		newDestOffice = obj.value;
	}
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;

	for ( var i = 1; i <= maxManifestAllowed; i++) {
		if (!isNull(getDomElementById("manifestNos" + i).value)) {
			isFilled = true;
			isConfirmChanges = confirm("BPL number(s) already entered in the grid.\n\nDo you still want to make the changes in header?");
			break;
		} else {
			isConfirmChanges = true;
		}
	}
	if (isConfirmChanges) {
		if (!isFilled) {
			jQuery("#manifestType").val(newManifestType);
			jQuery("#destRegionId").val(newDestRegion);
			jQuery("#destCity").val(newDestCity);
			jQuery("#destOfficeType").val(newOfficeType);
			jQuery("#destOffice").val(newDestOffice);
		}
		isRegion = false;
		for ( var i = 1; i <= maxManifestAllowed; i++) {
			if (!isNull(getDomElementById("manifestNos" + i).value)) {
				if (!isEmptyWeight(getDomElementById("weights" + i).value))
					decreaseWt(i);
				clearRowById(i);
				document.getElementById("manifestNos" + i).readOnly = false;
			}
		}

	} else {
		if (isFilled) {
			isRegion = true;
			jQuery("#manifestType").val(manifestType);
			jQuery("#destRegionId").val(destRegion);
			jQuery("#destCity").val(destCity);
			jQuery("#destOfficeType").val(officeType);
			jQuery("#destOffice").val(destOffice);

			return false;
		} else {
			obj.value = obj.value;
			return false;
		}
		return isConfirmChanges;
	}

	manifestType = newManifestType;
	destRegion = newDestRegion;
	destCity = newDestCity;
	officeType = newOfficeType;
	destOffice = newDestOffice;
}

function clearRowById(rowId) {
	getDomElementById("chk" + rowId).checked == false;
	/* getDomElementById("bplNo" + rowId).value = ""; */
	getDomElementById("manifestNos" + rowId).value = "";
	getDomElementById("weights" + rowId).value = "";
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("weightGm" + rowId).value = "";
	getDomElementById("cnCount" + rowId).value = "";
	getDomElementById("manifestType" + rowId).value = "";
	getDomElementById("destCitys" + rowId).value = "";
	
	
	// hidden field
	getDomElementById("manifestIds" + rowId).value = "";
	getDomElementById("destCityIds" + rowId).value = "";
	getDomElementById("cityIds" + rowId).value = "";
}

function decreaseWt(rowId) {
	var subTotalWeight = 0.000;
	// before clearing grid details getting weight
	var weightKg = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId).value;
	var weightInFraction = ((weightKg) + (weightGm)) / 1000;

	// Header Total Weight
	var totalWeight = parseFloat(document.getElementById("finalWeight").value);
	subTotalWeight = totalWeight * 1000 - parseFloat(weightInFraction * 1000);
	if (isEmptyWeight(subTotalWeight)) {
		subTotalWeight = 0.000;
	}
	subTotalWeight /= 1000;
	// Setting final wt after deduction in header and hidden Field
	document.getElementById("finalWeight").value = parseFloat(subTotalWeight);

	var finalWeightStr = subTotalWeight + "";
	weightKgValueFinal = finalWeightStr.split(".");
	document.getElementById("finalKgs").value = weightKgValueFinal[0];
	if (!isNull(weightKgValueFinal[1]))
		document.getElementById("finalGms").value = weightKgValueFinal[1]
				.substring(0, 3);
	if (weightKgValueFinal[1] == undefined) {
		document.getElementById("finalGms").value = "000";
	} else if (weightKgValueFinal[1].length == 1) {
		document.getElementById("finalGms").value += "00";
	} else if (weightKgValueFinal[1].length == 2) {
		document.getElementById("finalGms").value += "0";
	}

	// setting the weight 0 in global array while deleting
	eachConsgWeightArr[rowId] = 0;
}

function deleteRow() {
	try {

		var table = document.getElementById("bplOutManifestDoxTab");
		var tableRowCount = table.rows.length;

		for ( var i = 1; i < tableRowCount; i++) {

			deleteRowTable("bplOutManifestDoxTab", i - 1);
			tableRowCount--;
			i--;
		}
		rowCount = 1;
		addBplOutManifestRows();
	} catch (e) {
		alert(e);
	}

}

function deleteRowTable(bplOutManifestDoxTab, rowIndex) {
	var oTable = $('#' + bplOutManifestDoxTab).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function callEnterManifest(e) {
	var destOfficeType = document.getElementById("destOfficeType");
	valiedManifestType();
	if (destOfficeType.disabled == true) {
		return enterKeyNav("destOffice", e.keyCode);

	} else {
		return enterKeyNav("destOfficeType", e.keyCode);

	}
}

/* deleteConsgDtlsClientSide */

function deleteConsgDtlsClientSide() {
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	var isDel = false;
	for ( var i = 1; i <= maxManifestAllowed; i++) {
		var bplNo = getDomElementById("manifestNos" + i).value;
		if (getDomElementById("chk" + i).checked == true && !isNull(bplNo)) {
			decreaseWt(i);
			// Clearing the fields
			getDomElementById("chk" + i).checked = "";
			clearRowById(i);
			enableGridRowById(i);// enable rows for new consignments
			document.getElementById("manifestNos" + i).readOnly = false;
			isSaved = false;
			isDeleted = true;
			isDel = true;
		}// End If
		if (isNull(bplNo)) {
			getDomElementById("chk" + i).checked = false;
		}
	}// End For loop
	if (isDel) {
		alert("Record(s) deleted successfully.");

	} else
		alert("Please select a non-empty row to delete.");

}

function enableGridRowById(domElementId) {
	enableElement($("#manifestNos" + domElementId)[0]);

}
function enableElement(domElement) {
	domElement.readOnly = false;
	domElement.setAttribute("tabindex", "0");
}

function disableForClose() {
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
	jQuery("#" + "print").attr("disabled", false);
	jQuery("#" + "print").removeClass("btnintformbigdis");
	jQuery("#" + "print").addClass("btnintform");
	jQuery("#" + "save").addClass("btnintformbigdis");
	jQuery("#" + "close").addClass("btnintformbigdis");
	jQuery("#" + "cancelBtn").attr("disabled", false);
	jQuery("#" + "cancelBtn").removeClass("btnintformbigdis");
	jQuery("#" + "deletedId").addClass("btnintformbigdis");
}

function disableForSave() {
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
	jQuery("#" + "print").addClass("btnintformbigdis");
	jQuery("#" + "close").attr("disabled", false);
	jQuery("#" + "close").removeClass("btnintformbigdis");
	jQuery("#" + "close").addClass("btnintform");
	jQuery("#" + "save").addClass("btnintformbigdis");
	jQuery("#" + "deletedId").addClass("btnintformbigdis");
}

function capturedWeightForManifest(weigth) {
	// wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	// wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	// wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// alert('machine weight'+wmActualWeight);
	// newWMWt = wmActualWeight;

	var bplOutManifestDoxTOValues = bookingDetail;

	if (!isNull(bplOutManifestDoxTOValues.finalWeight)) {
		populateGridValueAfterWeightWeightMachine(bplOutManifestDoxTOValues,
				rowId);

	} else if ((isEmptyWeight(bplOutManifestDoxTOValues.finalWeight) || bplOutManifestDoxTOValues.finalWeight === 0)
			&& bplOutManifestDoxTOValues.coMail == "Y") {

		populateGridValueAfterWeightWeightMachine(bplOutManifestDoxTOValues,
				rowId);

	} else {
		clearGridValues(rowId);
		$("#manifestNos" + rowId).focus();
		alert("The weight of the packet cannot be zero");

	}

	hideProcessing();
}

function populateGridValueAfterWeightWeightMachine(bplOutManifestDoxTOValues,
		rowId) {
	var weightKgValue = "";
	var weightGmValue = "";
	var productId = "";
	var productSeries = "";
	var cityName = "";
	document.getElementById("manifestIds" + rowId).value = bplOutManifestDoxTOValues.manifestId
			+ "";
	if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)) {
		cityName = bplOutManifestDoxTOValues.destinationCityTO.cityName;
	}

	if (bplOutManifestDoxTOValues.product != null) {
		if (bplOutManifestDoxTOValues.product.productId != null) {
			productId = bplOutManifestDoxTOValues.product.productId;
			productSeries = bplOutManifestDoxTOValues.product.consgSeries;
			document.getElementById("productIds" + rowId).value = productId;
			document.getElementById("productSeries" + rowId).value = productSeries;
		}
	}
	// weighing Machine Integration
	document.getElementById("manifestNos" + rowId).readOnly = true;
	var weight = parseFloat(bplOutManifestDoxTOValues.finalWeight).toFixed(3);

	/*if (!isNull(newWMWt) && parseFloat(newWMWt) > weight) {
		weight = newWMWt;
	}*/

	jQuery("#weights" + rowId).val(weight);

	var weightToValue = weight + "";
	if (!isNull(weightToValue)) {
		weightKgValue = weightToValue.split(".");
		document.getElementById("weight" + rowId).value = weightKgValue[0];
		document.getElementById("weight" + rowId).readOnly = true;
		weightGmValue = weightToValue.split(".")[1];
		if (isNull(weightGmValue) || weightGmValue == "undefined") {
			weightGmValue = "000";
		} else if (weightGmValue.length == 1) {
			weightGmValue += "00";
		} else if (weightGmValue.length == 2) {
			weightGmValue += "0";
		}

		document.getElementById("weightGm" + rowId).value = weightGmValue;
		document.getElementById("weightGm" + rowId).readOnly = true;
		document.getElementById("destCitys" + rowId).value = cityName;
		if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)
				&& !isNull(bplOutManifestDoxTOValues.destinationCityTO.cityId)) {
			document.getElementById("destCityIds" + rowId).value = bplOutManifestDoxTOValues.destinationCityTO.cityId
					+ "";
		}
		if(!isNull(bplOutManifestDoxTOValues.noOfElements)){
			jQuery("#cnCount" + rowId).val(bplOutManifestDoxTOValues.noOfElements);
		} else {
			jQuery("#cnCount" + rowId).val("E");
		}
		if (isNull(bplOutManifestDoxTOValues.bplManifestType)) {
			if (bplOutManifestDoxTOValues.manifestOpenType == 'O') {
				jQuery("#manifestType" + rowId).val("OGM");
			} else if(bplOutManifestDoxTOValues.manifestOpenType == 'P') {
				jQuery("#manifestType" + rowId).val("Open");
			}
		} else {
			if (bplOutManifestDoxTOValues.bplManifestType == 'T') {
				jQuery("#manifestType" + rowId).val("Transhipment");
			} else if (bplOutManifestDoxTOValues.bplManifestType == 'P') {
				jQuery("#manifestType" + rowId).val("Pure");
			}
		}
		
		validateWeightTolence(weightToValue, manifestNumber);

	}

	hideProcessing();
}

function storeHeaderValue() {

	manifestTypeStore = document.getElementById("manifestType").value;
	destinationRegionIdStore = document.getElementById("destRegionId").value;
	destCityStore = document.getElementById("destCity").value;
	destOfficeTypeStore = document.getElementById("destOfficeType").value;
	destOfficeStore = document.getElementById("destOffice").value;

}

function populateHeaderDtlsAfterChange() {

	if (manifestTypeStore == 'PURE' || manifestTypeStore == 'pure') {
		document.getElementById("manifestType").value = "PURE";

	} else {
		document.getElementById("manifestType").value = "TRANS";
	}

	if (!isNull(destinationRegionIdStore)) {
		document.getElementById("destRegionId").value = destinationRegionIdStore;
	}

	if (!isNull(destCityStore)) {
		document.getElementById("destCity").value = destCityStore;
	}

	if (!isNull(destOfficeTypeStore)) {
		document.getElementById("destOfficeType").value = destOfficeTypeStore;
	}
	if (!isNull(manifestTO.destOfficeType)) {
		document.getElementById("destOfficeType").value = manifestTO.destOfficeType;
	}

	if (destOfficeStore) {
		addOptionTODropDown("destOffice", destOfficeStore, destOfficeStore);
		document.getElementById("destOffice").value = destOfficeStore;
	}

}

function getAllOffc() {
	if (isRegion) {

		getDomElementById("destOffice").value = destOffice;
		getDomElementById("destOfficeType").value = officeType;
	} else {

		isValiedDestnOffice();
	}

}

function getAllCitiesByRegion() {
	if (isRegion) {

		getDomElementById("destCity").value = destCity;
		getDomElementById("destOfficeType").value = officeType;
		getDomElementById("destOffice").value = destOffice;

	} else {
		getAllCitiesByRegionAndManifestType();

	}
}

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		cancelPage();
	}
}

function printAllCities(data) {
	if (!isNull(data)) {
		var content = document.getElementById('destCity');
		content.innerHTML = "";
		createDropDown("destCity", "", "--Select--");
		$.each(data, function(index, value) {
			addOptionTODropDown("destCity", this.cityName, this.cityId);
		});
		showDropDownBySelected('destCity', loginCity);
		disableFieldById("destCity");
		valiedManifestType();
	} else {
		showDropDownBySelected("destRegionId", "");
		alert("No cities found for the selected region");
		createDropDown("destCity", "", "SELECT");
		createDropDown("destOffice", "", "SELECT");
		document.getElementById('destRegionId').focus();
	}
	hideProcessing();
}

function getOfficeTypeByBplManifestType() {
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType == MANIFEST_TYPE_TRANSHIPMENT) {
		var domElement = getDomElementById("destOfficeType");
		for ( var i = 0; i < domElement.options.length; i++) {
			var offTypeVal = domElement.options[i].text.toUpperCase();
			if (offTypeVal.match("HUB")) {
				domElement.options[i].selected = 'selected';
				break;
			}
		}
		getOfficesByCityAndoffType();
		getDomElementById("destOfficeType").disabled = true;
	}
}

function getOfficesByCityAndoffType() {
	var destCityId = getDomElementById("destCity").value;
	var officeTypeIds = document.getElementById("destOfficeType").value;
	var officeTypeId = officeTypeIds.split("~")[0];
	if (!isNull(destCityId) && !isNull(officeTypeId)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getAllOfficesByCityAndOfficeType&cityId='
				+ destCityId + "&officeTypeId=" + officeTypeId;

		ajaxCallWithoutForm(url, printAllOfficesList);
	}
}
function printAllOfficesList(ajaxResp) {
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

			var officeToList = ajaxResp;
			if (!isNull(officeToList)) {
				var loginOfficeType = getDomElementById("loginOfficeType").value;
				var content = document.getElementById('destOffice');
				content.innerHTML = "";
				var allHubOfficeIds = "";
				$
						.each(
								officeToList,
								function(index, officeTO) {
									var officeType = trimString(officeTO.officeTypeTO.offcTypeCode);
									if (officeType == OFF_TYPE_REGION_HEAD_OFFICE
											&& officeToList.length == 2) {
										createDropDown("destOffice", "",
												"SELECT");
										alert("No offices found for the selected city");
									} else if (officeType != OFF_TYPE_REGION_HEAD_OFFICE
											&& officeToList.length >= 2) {
										if (loginOfficeType == OFF_TYPE_CODE_BRANCH) {
											var manifestTypeObj = getDomElementById("manifestType");
											if (manifestTypeObj.value == MANIFEST_TYPE_PURE) {
												addOptionTODropDown(
														"destOffice",
														officeTO.officeName,
														officeTO.officeId);
												showDropDownBySelected(
														"destOffice", "0");
											} else if (officeType.toLowerCase() == OFF_TYPE_CODE_HUB
													.toLowerCase()) {
												addOptionTODropDown(
														"destOffice",
														officeTO.officeName,
														officeTO.officeId);
												if (!isNull(loginRepHub)
														&& loginRepHub == officeTO.officeId) {
													showDropDownBySelected(
															"destOffice",
															officeTO.officeId);
													disableFieldById("destOffice");
												}
											}
										} else {
											addOptionTODropDown("destOffice",
													officeTO.officeName,
													officeTO.officeId);
											showDropDownBySelected(
													"destOffice", "0");
											disableFieldById("destOffice");

											// getDomElementById("destOffice").readOnly=readOnly;
										}
										// Only Hub of the City should be
										// considered
										if (officeType.toLowerCase() == OFF_TYPE_CODE_HUB
												.toLowerCase()) {
											allHubOfficeIds = allHubOfficeIds
													+ officeTO.officeId + ",";
										}
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

function getDestinationDtlsByBplManifestType() {
	enableFieldById("branchOfficeType");
	enableFieldById("destRegionId");
	enableFieldById("destCity");
	enableFieldById("destOffice");
	if (isConfirmChanges) {
		var manifestType = getDomElementById("manifestType").value;
		var loginOffType = getDomElementById("loginOfficeType").value;
		if ((manifestType == MANIFEST_TYPE_TRANSHIPMENT)
				&& (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
						.toLowerCase())) {
			var loginRegion = getDomElementById("regionId").value;
			showDropDownBySelected("destRegionId", loginRegion);
			getDomElementById("destRegionId").value = loginRegion;
			loginCity = getDomElementById("loginCityId").value;
			loginRepHub = getDomElementById("loginRepHub").value;
			getCitiesByRegion();
			validateBranchHubOffice();
			disableFieldById("destRegionId");

		} else {
			getDomElementById("destOfficeType").disabled = false;
			showDropDownBySelected("destRegionId", "");
			clearDropDownList("destCity", "SELECT");
			showDropDownBySelected("destOfficeType", "");
			clearDropDownList("destOffice", "SELECT");
			valiedManifestType();
		}
	}
}

function getCitiesByRegion() {
	var loginOffType = getDomElementById("loginOfficeType").value;
	if (loginOffType.toLowerCase() == OFF_TYPE_CODE_HUB.toLowerCase()) {
		getAllCitiesByRegionAndManifestType();
	} else if (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH.toLowerCase()) {
		getAllCities();
	}
}

function getAllCitiesByRegionAndManifestType() {
	var destRegionId = "";
	var manifestType = "";
	// getDomElementById('destOfficeType').value = "";
	createDropDown("destCity", "", "SELECT");
	createDropDown("destOffice", "", "SELECT");
	destRegionId = document.getElementById("destRegionId").value;
	manifestType = document.getElementById("manifestType").value;
	if (!isNull(destRegionId) && !isNull(manifestType)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getCitiesByRegion&regionId='
				+ destRegionId + "&manifestType=" + manifestType;
		ajaxCallWithoutForm(url, printAllCitiesManifestType);
	}
}

function printAllCitiesManifestType(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("destRegionId", "");
			alert(error);
			document.getElementById('destRegionId').focus();
		}

		else {
			var data = ajaxResp;
			if (!isNull(data)) {
				var content = document.getElementById('destCity');
				content.innerHTML = "";
				var defOption = document.createElement("option");
				defOption.value = "";
				defOption.appendChild(document.createTextNode("--Select--"));
				content.appendChild(defOption);
				// createOnlyAllOptionWithValue('destCity', "0");
				$.each(data, function(index, value) {
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

function isAllRowInGridFilled(maxRowAllowed) {
	for ( var i = 1; i <= maxRowAllowed; i++) {
		if (isNull(getDomElementById("manifestNos" + i).value))
			return false;
	}
	return true;
}

function setGridWeightToHidden(WeightValue, Obj) {
	var rowId = getRowId(Obj, "manifestNos");
	var weight = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId).value;
	if (isNull(finalWeight) && isNull(finalWeight)) {
		weight = "0";
		weightGm = "0";
	}

	var totalWeight = parseFloat(weight) + parseFloat(weightGm);
	document.getElementById("weights" + rowId).value = totalWeight;
}

function selectAllCheckBox(maxRowsAllowed) {
	if (getDomElementById("checkAll").checked == true) {
		for ( var i = 1; i <= maxRowsAllowed; i++) {
			getDomElementById("chk" + i).checked = true;
		}
	} else {
		for ( var i = 1; i <= maxRowsAllowed; i++) {
			getDomElementById("chk" + i).checked = false;
		}
	}
}

function enterKeyForManifestType(event, manifestTypeObj) {
	var originOfficeTypeCode = getDomElementById("originOfficeTypeCode").value;
	if (manifestTypeObj.value == MANIFEST_TYPE_TRANSHIPMENT
			&& originOfficeTypeCode == OFF_TYPE_CODE_BRANCH) {
		return callEnterKey(event, document.getElementById('bagLockNo'));
	} else {
		return callEnterKey(event, document.getElementById('destRegionId'));
	}
}

function isNullWithOutZero(value) {
	var flag = true;
	if (value != undefined && value != null && value != "" && value != "null"
			&& value != " ") {
		flag = false;
	}
	return flag;
}

function setIFrame() {
	// prepare Iframe for print
	var manifestNo = document.getElementById("manifestNo").value;
	if (!isNull(manifestNo)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		var url = "./bplOutManifestDox.do?submitName=printBPLOutManifestDox&manifestNo="
				+ manifestNo + "&loginOfficeId=" + loginOffceID;
		printUrl = url;
		printIframe(printUrl);
	}
}
