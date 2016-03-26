var ROW_COUNT;

var manifestType;
var destRegion;
var destCity;
var officeType;
var destOffice;
var isRegion;
var isFilled = false;
var isConfirmChanges = true;

var newManifestType;
var newDestRegion;
var newDestCity;
var newOfficeType;
var newDestOffice;

var RowNo;

var maxWeightAllowed = 0;
var maxTolerenceAllowed = 0;

var isSaved = false;
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

var printUrl;

function setDefaultValue() {
	branchCode = $("#branchOfficeType").val();
	hubCode = $("#hubOfficeType").val();
	originOfficeTypeCode = $("#originOfficeTypeCode").val();

}

/* @Desc:For adding the rows in grid */
function addMBplOutManifestRows() {
	maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	var maxCount = parseInt(maxManifestAllowed);
	for ( var rowCount = 1; rowCount <= maxCount; rowCount++) {

		$('#outManifestTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox"  id="ischecked'
										+ rowCount
										+ '"   tabindex="-1" onclick="getManifestId(this);"/>',
								rowCount,
								'<input type="text" class="txtbox" maxlength="10" styleClass="txtbox" id="bplNo'
										+ rowCount
										+ '" name="to.bplNos" onblur="convertDOMObjValueToUpperCase(this);fnValidateNumber(this,\'G\');focusOnclose()" onfocus="validateHeaderDetails();" onkeypress="return callEnterKey(event, document.getElementById(\'bplNo'
										+ (rowCount + 1) + '\'));"    />',
								'<input type="text" class="txtbox" styleClass="txtbox" id="bagType'
										+ rowCount
										+ '" name="to.bagTypes" readonly=true tabindex="-1"  />',
								'<input type="text" class="txtbox" styleClass="txtbox" id="bagLockNo'
										+ rowCount
										+ '" name="to.bagLockNos" readonly=true tabindex="-1"  />',

								'<input type="text" name="weight" id="weight'
										+ rowCount
										+ '" class="txtbox width30"  size="11" readonly=true tabindex="-1" /><span class="lable">.</span><input type="text" name="weight" id="weightGm'
										+ rowCount
										+ '" class="txtbox width30" size="11" readonly=true  tabindex="-1" /><input type="hidden" id="weights'
										+ rowCount + '" name="to.weight"/>',
								'<input type="text" class="txtbox" id="destCitys'
										+ rowCount
										+ '" name="to.destCitys" readonly=true tabindex="-1"/>\
								<input type="hidden" id="manifestIds'
										+ rowCount
										+ '" name="to.manifestIds" value="" />\
								<input type="hidden" id="manifestMappedEmbeddeId'
										+ rowCount
										+ '" name="to.manifestMappedEmbeddeId" value="" />\
								<input type="hidden" id="destCityIds'
										+ rowCount
										+ '" name="to.destCityIds" value=""/>\
								<input type="hidden" id="cityIds'
										+ rowCount
										+ '" name="to.cityIds" value=""/>\
								<input type="hidden" id="pincodeIds'
										+ rowCount
										+ '" name="to.pincodeIds"/>\
								<input type="hidden" id="position'
										+ rowCount
										+ '" name="to.position" value="'
										+ rowCount + '"/>' ]);
	}// end of for loop

	document.getElementById("manifestNo").focus();
}

/* @Desc:For getting the details of BPL No entered in grid */
function getManifestDtsbyBPLNo(bplNumberObj) {

	bplNumberObj.value = $.trim(bplNumberObj.value);
	var loginOfficeId = document.getElementById("loginOfficeId").value;
	var headerManifestNo = getDomElementById("manifestNo").value;
	if (!isNull(bplNumberObj.value)) {
		showProcessing();
		var url = './mbplOutManifest.do?submitName=getManifestDtlsByProcess&manifestNo='
				+ bplNumberObj.value
				+ '&loginOfficeId='
				+ loginOfficeId
				+ "&headerManifestNo=" + headerManifestNo;
		$.ajax({
			url : url,
			success : function(req) {
				populateManifest(req, bplNumberObj);
			}
		});

	}
}

function populateManifest(ajaxResp, manifestNumberObj) {
	var rowId = getRowId(manifestNumberObj, "bplNo");
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			clearRowById(rowId);
			setTimeout(function() {
				document.getElementById("bplNo" + rowId).focus();
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
	var transCity;
	enableFieldById("branchOfficeType");
	var branchOfficType = $("#branchOfficeType").val();
	disableFieldById("branchOfficeType");
	var loginOfficeType = $("#loginOfficeType").val();
	var gridDstnCityId = "";
	var gridDstnOfficeId = "";
	var manifestType = document.getElementById("manifestType").value;

	if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)) {
		if (!isNull(bplOutManifestDoxTOValues.destinationCityTO.cityId)) {
			gridDstnCityId = bplOutManifestDoxTOValues.destinationCityTO.cityId;

		}
	}
	if (!isNull(bplOutManifestDoxTOValues.destinationOfficeId)) {
		gridDstnOfficeId = bplOutManifestDoxTOValues.destinationOfficeId;
	}

	if (bplOutManifestDoxTOValues.receivedStatus=='E') {
		populateGridDetails(bplOutManifestDoxTOValues, rowId,
				manifestNumberObj);		
	}else{
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
				if (!getRouteByOriginCityAndDestCity(bplOutManifestDoxTOValues,
						gridDstnCityId, manifestNumberObj, rowId)) {
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
	if (isTranshipmentRoute) {
		if (loginOfficeType == branchOfficType
				&& manifestType == MANIFEST_TYPE_TRANSHIPMENT) {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);
		} else {
			if (isRouteServiceable) {
				if (!getRouteByOriginCityAndDestCity(bplOutManifestDoxTOValues,
						gridDstnCityId, manifestNumberObj, rowId)) {
					return;
				}
			}
			if (!isTranshipmentRouteChecking(bplOutManifestDoxTOValues,
					gridDstnCityId, manifestNumberObj, rowId)) {
				return;
			}
		}

	}

	if (!isNull(bplOutManifestDoxTOValues.destinationCityTO)) {
		if (!isPureRoute && !isTranshipmentRoute) {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);
		}
	}

}
}

function populateGridDetails(bplOutManifestDoxTOValues, rowId,
		manifestNumberObj) {
	RowNo = rowId;
	manifestNumber = manifestNumberObj;
	bookingDetail = bplOutManifestDoxTOValues;
	//getWtFromWMForOGM();
	capturedWeightForManifest(-1);
}

/* @Desc:For Searching the details of MBPL No entered in Header */
function searchManifest() {
	if ($('#outManifestTable').dataTable().fnGetData().length < 1) {
		addMBplOutManifestRows();
	}
	// setIFrame();
	var manifestNO = document.getElementById("manifestNo").value;
	var loginOffceID = document.getElementById('loginOfficeId').value;
	if (!isNull(manifestNO)) {
		showProcessing();
		url = './mbplOutManifest.do?submitName=searchManifestDetails&manifestNo='
				+ manifestNO + "&loginOfficeId=" + loginOffceID;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestDetails(req);
			}
		});
	} else {
		alert("Please provid MBPL No.");
		document.getElementById("manifestNo").value = "";
		setTimeout(function() {
			document.getElementById("manifestNo").focus();
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
			populateSearchDetails(responseText);
		}
	}
}

/* @Desc:Response method of searching the details of MBPL No entered in grid */
function populateSearchDetails(manifestDetailsTO) {
	document.getElementById("dateTime").value = manifestDetailsTO.manifestDate;
	document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
	if (!isNull(manifestDetailsTO.manifestProcessTo)) {
		document.getElementById("manifestProcessId").value = manifestDetailsTO.manifestProcessId;
		document.getElementById("manifestProcessNo").value = manifestDetailsTO.processNo;
	}
	getDomElementById("manifestStatus").value = manifestDetailsTO.manifestStatus;

	if (manifestDetailsTO.bplManifestType == "PURE") {
		document.getElementById("manifestType").value = "PURE";
	} else {// TRANS
		document.getElementById("manifestType").value = "TRANS";
	}
	validateBranchHubOffice();
	document.getElementById("bagLockNo").value = manifestDetailsTO.bagLockNo;
	document.getElementById("rfidNo").value = manifestDetailsTO.rfidNo;
	document.getElementById("bagRFID").value = manifestDetailsTO.bagRFID;// hidden
	// field

	document.getElementById("destRegionId").value = manifestDetailsTO.destRegionTO.regionId;

	clearDropDownList("destCity");
	addOptionTODropDown("destCity",
			manifestDetailsTO.destinationCityTO.cityName,
			manifestDetailsTO.destinationCityTO.cityId);
	document.getElementById("destCity").value = manifestDetailsTO.destinationCityTO.cityId;
	if (!isNull(manifestDetailsTO.officeTypeId)) {
		document.getElementById("destOfficeType").value = manifestDetailsTO.officeTypeId
				+ "~" + manifestDetailsTO.officeCode;
	}
	if (!isNull(manifestDetailsTO.destOfficeType)) {
		document.getElementById("destOfficeType").value = manifestDetailsTO.destOfficeType;

	}
	if (manifestDetailsTO.isMulDestination == "Y"
			|| isNull(manifestDetailsTO.destinationOfficeId)) {
		// document.getElementById("destOffice").value = "0";
		addOptionTODropDown("destOffice", "ALL", "0~0");
		document.getElementById("destOffice").value = "0~0";

	} else {

		addOptionTODropDown("destOffice",
				manifestDetailsTO.destinationOfficeName,
				manifestDetailsTO.destinationOfficeId + "~"
						+ manifestDetailsTO.bplDestOfficeCityId);
		document.getElementById("destOffice").value = manifestDetailsTO.destinationOfficeId
				+ "~" + manifestDetailsTO.bplDestOfficeCityId;
	}

	// Set total Weight
	var splitweightGm = manifestDetailsTO.finalWeight + "";
	if (!isNull(splitweightGm)) {
		weightKgValue = splitweightGm.split(".");
		document.getElementById("finalKgs").value = weightKgValue[0];
		weightGmValue = splitweightGm.split(".")[1];
		if (isNull(weightGmValue)) {
			weightGmValue = "00";
			document.getElementById("finalGms").value = weightGmValue;
		} else {
			document.getElementById("finalGms").value = weightGmValue;
		}
	}
	getDomElementById("finalWeight").value = document
			.getElementById("finalKgs").value
			+ "." + document.getElementById("finalGms").value;
	var wt = getDomElementById("finalWeight").value;
	splitWeights(wt, "finalKgs", "finalGms", "");// 4th param is null
	// for final weight
	document.getElementById("outMnfstDestIds").value = manifestDetailsTO.outMnfstDestIds;
	disableHeaderDropDown();

	// Grid Details
	for ( var i = 0; i < manifestDetailsTO.mbplOutManifestDetailsTOsList.length; i++) {
		document.getElementById("bplNo" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].manifestNo;
		document.getElementById("manifestIds" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].manifestId;
		document.getElementById("bagLockNo" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].bagLockNo;
		document.getElementById("weights" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].weight;
		document.getElementById("manifestMappedEmbeddeId" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].mapEmbeddedManifestId;
		document.getElementById("bagType" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].bagType;
		if (document.getElementById("manifestType").value == "TRANS") {
			document.getElementById("destCitys" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].destCity;
			document.getElementById("destCityIds" + (i + 1)).value = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].destCityId;
		} else {
			document.getElementById("destCitys" + (i + 1)).value = "";
			document.getElementById("destCityIds" + (i + 1)).value = "";

		}

		var splitweightGm = manifestDetailsTO.mbplOutManifestDetailsTOsList[i].weight
				+ "";
		if (!isNull(splitweightGm)) {
			weightKgValue = splitweightGm.split(".");
			document.getElementById("weight" + (i + 1)).value = weightKgValue[0];
			weightGmValue = splitweightGm.split(".")[1];
			if (isNull(weightGmValue)) {
				weightGmValue = "00";
				document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
			} else {
				document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
			}
		}
		eachConsgWeightArr[i + 1] = parseFloat(manifestDetailsTO.mbplOutManifestDetailsTOsList[i].weight);
		var wt = getDomElementById("weights" + (i + 1)).value;
		splitWeights(wt, "weight", "weightGm", (i + 1));
		// disable saved row
		document.getElementById("bplNo" + (i + 1)).readOnly = true;
		document.getElementById("bagLockNo" + (i + 1)).readOnly = true;
		document.getElementById("weight" + (i + 1)).readOnly = true;
		document.getElementById("weightGm" + (i + 1)).readOnly = true;
	}
	isSaved = true;
	/* If already closed manifest was searchd:diabling the grid */
	if (manifestDetailsTO.manifestStatus == "C") {
		disableForClose();
		for ( var i = 0; i < maxManifestAllowed; i++) {
			document.getElementById("bplNo" + (i + 1)).disabled = true;
			document.getElementById("bagLockNo" + (i + 1)).disabled = true;
			document.getElementById("weight" + (i + 1)).disabled = true;
			document.getElementById("weightGm" + (i + 1)).disabled = true;
			document.getElementById("destCitys" + (i + 1)).disabled = true;
		}

	}
	hideProcessing();
}
function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}

/*
 * @Desc:For Disabling the Header Fields after searching the details of MBPL No
 * entered in grid
 */
function disableHeaderDropDown() {
	getDomElementById("Find").disabled = true;
	document.getElementById("manifestNo").readOnly = true;
	document.getElementById("manifestType").disabled = true;
	document.getElementById("destRegionId").disabled = true;
	document.getElementById("destCity").disabled = true;
	document.getElementById("destOfficeType").disabled = true;
	document.getElementById("destOffice").disabled = true;
	document.getElementById("bagLockNo").disabled = true;
	// document.getElementById("bagRFID").disabled = true;
	document.getElementById("rfidNo").disabled = true;

}

/*
 * @Desc:For Enabling the Header Fields after searching the details of MBPL No
 * entered in grid
 */
function enableHeaderDropDown() {
	// if (document.getElementById("manifestNo").disabled == true) {
	document.getElementById("manifestNo").disabled = false;
	// }
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
	// if (document.getElementById("rfidNo").disabled == true) {
	document.getElementById("rfidNo").disabled = false;
	// }

}
/* @Desc:For Clearing the dropdown */
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");

}

/* @Desc:For Adding option to the dropdown */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

/* @Desc:For Saving or Closing the details of MBPL No entered in Header */
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
		var url = './mbplOutManifest.do?submitName=saveOrUpdateOutManifestMBPL';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#mbplOutManifestForm").serialize(),
			success : function(req) {
				printCallBackManifestSave(req, action);
			}
		});

	}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
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
				$('#outManifestTable').dataTable().fnClearTable();
				searchManifest();
				disableForSave();

			} else if (action == 'close') {
				//alert(manifestDetailsTO.successMessage);
				disableForClose();
				var confirm1 = confirm("Manifest closed successfully.");
				if (confirm1) {
					searchManifest();
					printManifest();
					refreshPage();
					/*setTimeout(function(){printManifest();}, 3000);
					setTimeout(function(){refreshPage();}, 4000);*/
				} else {
					refreshPage();
				}
			}
		}
	}
}

/* @Desc:Validating the header details */
function validateHeaderDetails() {

	var isMissed = true;
	var manifestNo = document.getElementById("manifestNo");
	var manifestType = document.getElementById("manifestType");
	var destinationRegionId = document.getElementById("destRegionId");
	var destCity = document.getElementById("destCity");
	var destOfficeType = document.getElementById("destOfficeType");
	var destOffice = document.getElementById("destOffice");
	var bagLockNo = document.getElementById("bagLockNo");

	var missingFields = "Please provide : ";

	if (isNull(bagLockNo.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Bag Lock No,";
		setTimeout(function() {
			bagLockNo.focus();
		}, 10);
		isMissed = false;
	}

	if (isNull(destOffice.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Destination Office,";
		setTimeout(function() {
			destOffice.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(destOfficeType.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Destination Office Type,";
		setTimeout(function() {
			destOfficeType.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(destCity.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Destination City,";
		setTimeout(function() {
			destCity.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(destinationRegionId.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Destination Region,";
		setTimeout(function() {
			destinationRegionId.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(manifestType.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "Manifest Type,";
		setTimeout(function() {
			manifestType.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(manifestNo.value)) {
		// alert("Please provide Manifest No.");
		missingFields = missingFields + "MBPL No,";
		setTimeout(function() {
			manifestNo.focus();
		}, 10);
		isMissed = false;
	}
	if (!isMissed)
		alert(missingFields);
	return isMissed;

}

/* ******mandatory field checks for grid level when chkbox selected **** */
function validateGridDetails() {
	var flag = false;
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	for ( var i = 1; i <= maxManifestAllowed; i++) {
		if (!isNull(getDomElementById("bplNo" + i).value)) {
			if (!validateSelectedRow(i)) {
				return false;
			}
			flag = true;
		}
	}
	if (!flag)
		alert("Please enter atleast one BPL Number");
	return flag;
}

function validateSelectedRow(rowId) {

	var bplNo = getDomElementById("bplNo" + rowId);
	var bPLNo = bplNo.value;
	var lineNum = "at line :" + rowId;

	if (isNull(bPLNo)) {
		alert("Please provide BPL No " + lineNum);
		setTimeout(function() {
			bplNo.focus();
		}, 10);
		return false;
	}
	return true;
}

/* @Desc:Response Method of Deleting the record from grid */

function printCallBackManifestDelete(data, action) {
	if (data != null && data != "") {
		if (data == "SUCCESS") {
			// alert("Record deleted successfully.");
			performSaveOrClose(action);
		} else if (data == "FAILURE") {
			alert("Exception occurred.Data not Saved successfully.");
		}
		jQuery('#outManifestTable >tbody >tr').each(function(i, tr) {
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if (isChecked) {
				document.getElementById("ischecked" + (i + 1)).checked = false;

				document.getElementById("bplNo" + (i + 1)).value = "";
				document.getElementById("bagLockNo" + (i + 1)).value = "";
				document.getElementById("weight" + (i + 1)).value = "";
				document.getElementById("weightGm" + (i + 1)).value = "";
				document.getElementById("destCitys" + (i + 1)).value = "";
				document.getElementById("manifestIds" + (i + 1)).value = "";
				document.getElementById("destCityIds" + (i + 1)).value = "";
				document.getElementById("cityIds" + (i + 1)).value = "";
			}

		});

	}
}

/* @Desc:Deleting the record from grid at client side */
function deleteConsgDtlsClientSide() {
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	var isDel = false;
	for ( var i = 1; i <= maxManifestAllowed; i++) {
		var bplNo = getDomElementById("bplNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true
				&& !isNull(bplNo)) {
			decreaseWt(i);
			// Clearing the fields
			getDomElementById("ischecked" + i).checked = "";
			clearRowById(i);
			enableGridRowById(i);// enable rows for new consignments
			document.getElementById("bplNo" + i).readOnly = false;
			isSaved = false;
			isDeleted = true;
			isDel = true;
		}// End If
		if (isNull(bplNo)) {
			getDomElementById("ischecked" + i).checked = false;
		}
	}// End For loop
	if (isDel)
		alert("Record(s) deleted successfully.");
	else
		alert("Please select a Non Empty row to delete.");

	if (getDomElementById("checkAll").checked == true) {
		getDomElementById("checkAll").checked = false;
	}
}

/* @Desc:Getting the manifest id on check of the Grid row */
function getManifestId(checkObj) {
	var manifestIdListAtGrid = document.getElementById("manifestIdListAtGrid").value;
	var rowId = getRowId(checkObj, "ischecked");

	if (isNull(manifestIdListAtGrid)) {
		var manifestId = document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	} else {
		var manifestId = manifestIdListAtGrid + ","
				+ document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	}
}

/* @Desc:checking the maximum tolerence and weight */
function maxWtCheck(rowId) {
	var bplNo = getDomElementById("bplNo" + rowId).value;
	if (!isNull(bplNo)) {
		var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
		var maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
		var maxTolerenceAllowed = document
				.getElementById("maxTolerenceAllowed").value;
		// maxManifestAllowed = parseInt(maxManifestAllowed);
		if (!maxWeightToleranceCheck(rowId, maxManifestAllowed,
				maxWeightAllowed, maxTolerenceAllowed)) {
			document.getElementById("bplNo" + rowId).readOnly = false;
			clearRowById(rowId);
			eachConsgWeightArr[rowId] = 0;
			return false;

		}
		var wtKg = getDomElementById("weight" + rowId).value;
		var wtGm = getDomElementById("weightGm" + rowId).value;
		getDomElementById("weights" + rowId).value = wtKg + "." + wtGm;
		setFinalWeight(document.getElementById("finalWeight").value);
		return true;

	}

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
		}

		var manifestType = document.getElementById("manifestType").value;
		if (manifestType == "TRANS") {
			$("#destOffice option[value='0~0']").remove();

		}
	}
	hideProcessing();
}

/*function printAllOfficesBPL(officeTOs) {
 // if (!isNull(officeTOs)) {
 var content = document.getElementById('destOffice');
 content.innerHTML = "";
 var defOption = document.createElement("option");
 defOption.value = "";
 defOption.appendChild(document.createTextNode("--Select--"));
 content.appendChild(defOption);
 $.each(officeTOs, function(index, value) {
 var option;
 option = document.createElement("option");
 option.value = this.officeId + "~" + this.cityId;
 option.appendChild(document.createTextNode(this.officeName));
 content.appendChild(option);
 });
 }
 * else { showDropDownBySelected("destCity", "");
 * createDropDown("destOffice", "", "SELECT"); alert("No offices found for
 * the selected city"); document.getElementById('destCity').focus(); }

 var manifestType = document.getElementById("manifestType").value;
 if (manifestType == "TRANS") {
 $("#destOffice option[value='0~0']").remove();

 }*/

/* @Desc:for setting the the office ids if multiple destination is selected */
function getAllOfficeIds(obj) {

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
function fnValidateNumber(obj, scanLevel) {
	var domValue = $.trim(obj.value);

	if (!isNull(obj.value)) {
		var count = getRowId(obj, "bplNo");
		if (isDuplicateBPL(obj, count)) {
			getDomElementById("seriesType").value = BPL_SERIES;
			if (isValidManifestNoForMBPLGrid(obj, scanLevel)) {
				if (!obj.readOnly && !isNull(domValue)) {
					getManifestDtsbyBPLNo(obj);
				} else {
					return false;
				}
			} else {
				if (!isEmptyWeight(getDomElementById("finalWeight").value))
					decreaseWt(count);
				// clear the row
				clearRowById(count);
			}
		}
	}

}

// }
/* @Desc:Checking the duplicate entry of bpl No in grid */
function isDuplicateBPL(bplNumberObj, count) {
	var isValid = true;
	var currentBPL = bplNumberObj.value;
	var table = document.getElementById('outManifestTable');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {

		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevBPL = document.getElementById('bplNo' + id).value;
		if (!isNull($.trim(prevBPL)) && !isNull($.trim(currentBPL))) {
			if (count != id) {
				if ($.trim(prevBPL.toUpperCase()) == $.trim(currentBPL
						.toUpperCase())) {
					alert("BPL Number already entered");
					bplNumberObj.value = "";
					setTimeout(function() {
						bplNumberObj.focus();
					}, 10);
					isValid = false;
				}

			}
		}
	}

	return isValid;

}


/* @Desc:Disabling the checkbox of the grid */
function disableAllCheckBox() {
	getDomElementById("checkAll").disabled = true;
	for ( var i = 1; i <= maxManifestAllowed; i++) {
		getDomElementById("ischecked" + i).disabled = true;
	}
}

/* @Desc:Disabling the elements after saving the mbpl */
function disableForSave() {
	disableHeaderDropDown();
	for ( var i = 1; i <= maxManifestAllowed; i++) {
		if (!isNull(getDomElementById("bplNo" + i).value))
			disableGridRowById(i);
	}
}

/* @Desc:Disabling the elements of grid */
function disableGridRowById(domElementId) {
	// read only text fields
	disableElement(getDomElementById("bplNo" + domElementId));
	disableElement(getDomElementById("bagLockNo" + domElementId));
	disableElement(getDomElementById("weight" + domElementId));
	disableElement(getDomElementById("weightGm" + domElementId));

}
/* @Desc:Disabling the elements */
function disableElement(domElement) {
	domElement.setAttribute("readOnly", true);
	domElement.setAttribute("tabindex", "-1");
}

/* @Desc:enabling the elements of grid */
function enableGridRowById(domElementId) {
	enableElement(getDomElementById("bplNo" + domElementId));
}

/* @Desc:Enabling the elements */
function enableElement(domElement) {
	domElement.readOnly = false;
	domElement.setAttribute("tabindex", "0");
}

/* @Desc:Printing the Manifest after closing */
function printManifest() {
	if(confirm("Do you want to print?")){
	var manifestStatus = getDomElementById("manifestStatus").value;
	var manifestNO = document.getElementById("manifestNo").value;
	var loginOffceID = document.getElementById('loginOfficeId').value;

	if (manifestStatus == MANIFEST_STATUS_OPEN) {
		alert("Only closed manifest can be printed.");
	} else {
		if (!isNull(manifestNO) && !isNull(loginOffceID)) {
			url = "./mbplOutManifest.do?submitName=printMBPLOutManifest&manifestNo="
					+ manifestNO + "&loginOfficeId=" + loginOffceID;
			 var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
		} else {
			alert("Please provide MBPL No.");
		}
		/*window.frames['iFrame'].focus();
		window.frames['iFrame'].print();*/
		}
	}
}

/**
 * @Desc checks if all the rows in the grid is filled
 * @returns {Boolean}
 */
function isAllRowInGridFilled(maxRowAllowed) {
	for ( var i = 1; i <= maxRowAllowed; i++) {
		if (isNull(getDomElementById("bplNo" + i).value))
			return false;
	}
	return true;
}
/**
 * @Desc check manifest number at header level
 */
function isValidManifestNoForHeader(domElement) {
	if (!isNull(domElement.value)) {
		getDomElementById("seriesType").value = MBPL_SERIES;
		isValidManifestNo(domElement, 'H');
	}
}
/**
 * @desc to disable fields and buttons after close
 */
function disableForClose() {
	disableHeaderDropDown();
	disableAllCheckBox();
	disableAll();
	var manifestStatus = document.getElementById("manifestStatus").value;
	if (manifestStatus == "C") {
		jQuery("#" + "printBtn").attr("disabled", false);
		jQuery("#" + "printBtn").removeClass("btnintformbigdis");
		jQuery("#" + "printBtn").addClass("btnintform");
		jQuery("#" + "saveBtn").addClass("btnintformbigdis");
		jQuery("#" + "closeBtn").addClass("btnintformbigdis");
		jQuery("#" + "deleteBtn").addClass("btnintformbigdis");
		jQuery("#" + "cancelBtn").attr("disabled", false);
	}
	buttonEnabled("printBtn", "btnintformbigdis");
	buttonEnabled("cancelBtn", "btnintformbigdis");
}

/**
 * clear the row by its Id
 * 
 * @param rowId
 */
function clearRowById(rowId) {
	getDomElementById("ischecked" + rowId).checked == false;
	getDomElementById("bplNo" + rowId).value = "";
	getDomElementById("bagType" + rowId).value = "";
	
	getDomElementById("bagLockNo" + rowId).value = "";
	getDomElementById("weights" + rowId).value = "";
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("weightGm" + rowId).value = "";
	getDomElementById("destCitys" + rowId).value = "";
	// hidden field
	getDomElementById("manifestIds" + rowId).value = "";
	getDomElementById("destCityIds" + rowId).value = "";
	getDomElementById("cityIds" + rowId).value = "";
	getDomElementById("pincodeIds" + rowId).value = "";
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

		var table = document.getElementById("outManifestTable");
		var tableRowCount = table.rows.length;

		for ( var i = 1; i < tableRowCount; i++) {

			deleteRowTable("outManifestTable", i - 1);
			tableRowCount--;
			i--;
		}

		rowCount = 1;
		addMBplOutManifestRows();
	} catch (e) {
		alert(e);
	}

}

/**
 * This Method Deleted the selected row from the Grid
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRowTable(outManifestTable, rowIndex) {
	var oTable = $('#' + outManifestTable).dataTable();
	oTable.fnDeleteRow(rowIndex);
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

	// Origin hub  to Destn Branch
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
			alert("Bag destination not served by the selected Master Bag destination.");
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
			alert("Bag destination not served by the selected Master Bag destination.");
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

		url = './mbplOutManifest.do?submitName=isValidTransshipmentRoute&cityId='
				+ gridDstnCityId + "&transCity=" + OGMDstCityId;
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#mbplOutManifestForm").serialize(),
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
			manifestNumberObj.readOnly = false;
			clearRowById(rowId);
			//	decreaseWt(decreaseWt);
			hideProcessing();
			return false;
		} else {
			populateGridDetails(bplOutManifestDoxTOValues, rowId,
					manifestNumberObj);

		}

	}
}

function getRouteByOriginCityAndDestCity(bplOutManifestDoxTOValues,
		gridDstnCityId, manifestNumberObj, rowId) {
	var originCityId = document.getElementById("originCityId").value;
	var OGMDstCity = document.getElementById("destOffice").value;
	var OGMDstCityId = OGMDstCity.split("~")[1];
	if (!isNull(OGMDstCityId)) {
		var url = "./mbplOutManifest.do?submitName=getRouteByOriginCityAndDestCity&originCityId="
				+ originCityId + "&destCityId=" + OGMDstCityId;

		$.ajax({
			url : url,
			success : function(req) {
				populateRouteId(req, bplOutManifestDoxTOValues, rowId,
						manifestNumberObj, gridDstnCityId);
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
function populateRouteId(ajaxResp, bplOutManifestDoxTOValues, rowId,
		manifestNumberObj, gridDstnCityId) {

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
		if (!isNull(getDomElementById("bplNo" + i).value)) {
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
			if (!isNull(getDomElementById("bplNo" + i).value)) {
				if (!isEmptyWeight(getDomElementById("weights" + i).value))
					decreaseWt(i);
				clearRowById(i);
				getDomElementById("bplNo" + i).readOnly = false;
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

function getAllCitiesByRegion() {
	if (isRegion) {
		jQuery("#destCity").val(destCity);
		jQuery("#destOfficeType").val(officeType);
		jQuery("#destOffice").val(destOffice);
	} else {
		getAllCitiesByRegionAndManifestTypeMBPL();

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
		getAllCitiesByRegionAndManifestTypeMBPL();
	} else if (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH.toLowerCase()) {
		getAllCities();
	}
}

function getAllCitiesByRegionAndManifestTypeMBPL() {
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
function printAllCities(data) {
	var manifestType = getDomElementById("manifestType").value;

	if (!isNull(data)) {
		var content = document.getElementById('destCity');
		content.innerHTML = "";
		createDropDown("destCity", "", "--Select--");
		$.each(data, function(index, value) {
			addOptionTODropDown("destCity", this.cityName, this.cityId);
		});
		if (manifestType == MANIFEST_TYPE_TRANSHIPMENT) {
			showDropDownBySelected('destCity', loginCity);
			disableFieldById("destCity");
		}
		// destCitygetDomElementById("destCity").readOnly=true;
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

function valiedManifestType() {
	var manifestTypeObj = document.getElementById("manifestType");
	var destOfficeTypeObj = document.getElementById("destOfficeType");
	var loginOffType = getDomElementById("loginOfficeType").value;

	if (!isNull(manifestTypeObj.value)) {

		if (manifestTypeObj.value == 'TRANS'
				&& loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
						.toLowerCase()) {
			selectOptionByFirstSplitValue(destOfficeTypeObj, hubCode);
			disableFieldById("destOfficeType");
			if (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
					.toLowerCase()) {
				getOfficesByCityAndoffType();
			}

		} else {
			enableFieldById("destOfficeType");
			if (!isRegion) {
				$("#destOfficeType").val("");

			}
		}
	}

}

function getOfficesByCityAndoffType() {
	var destCityId = getDomElementById("destCity").value;
	var officeType = document.getElementById("destOfficeType").value;
	var officeTypeId = officeType.split("~")[0];
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

function disableFieldById(fieldId) {
	document.getElementById(fieldId).disabled = true;
}

function enableFieldById(fieldId) {
	document.getElementById(fieldId).disabled = false;
}

function selectOptionByFirstSplitValue(selectObj, selectValue) {
	for ( var i = 0; i < selectObj.options.length; i++) {

		if (selectObj.options[i].value.split("~")[1] == selectValue) {
			selectObj.selectedIndex = i;
			break;
		}
	}
}

function getAllOffc() {
	if (isRegion) {
		jQuery("#destCity").val(destCity);
		jQuery("#destOfficeType").val(officeType);
		jQuery("#destOffice").val(destOffice);
	} else {

		isValiedDestnOffice();
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
	var focusId = document.getElementById("bplNo" + rowId);
	return callEnterKey(e, focusId);

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

function splitWeights(wt, wtKgId, wtGmId, rowId) {
	var weightArr = wt.split(".");
	var weightKgId = wtKgId + rowId;
	var weightGmId = wtGmId + rowId;
	getDomElementById(weightKgId).value = weightArr[0];
	getDomElementById(weightGmId).value = (!isNull(weightArr[1])) ? weightArr[1]
			.substring(0, 3)
			: "";
	if (weightArr[1] == undefined) {
		getDomElementById(weightGmId).value += "000";
	} else if (weightArr[1].length == 1) {
		getDomElementById(weightGmId).value += "00";
	} else if (weightArr[1].length == 2) {
		getDomElementById(weightGmId).value += "0";
	}
}

function capturedWeightForManifest(weigth) {
	var mbplOutManifestTO = bookingDetail;
	document.getElementById("bplNo" + RowNo).readOnly = true;
	document.getElementById("bagLockNo" + RowNo).value = mbplOutManifestTO.bagLockNo;
	document.getElementById("bagType" + RowNo).value = mbplOutManifestTO.bagType;
	document.getElementById("manifestIds" + RowNo).value = mbplOutManifestTO.manifestId;
	document.getElementById("weights" + RowNo).value = mbplOutManifestTO.finalWeight;

	if (!isNull(mbplOutManifestTO.destinationCityTO)) {
		var cityName = mbplOutManifestTO.destinationCityTO.cityName;
		if (document.getElementById("manifestType").value == "PURE") {
			document.getElementById("destCitys" + RowNo).value = "";
		} else {
			document.getElementById("destCitys" + RowNo).value = cityName;
		}
		document.getElementById("destCityIds" + RowNo).value = mbplOutManifestTO.destinationCityTO.cityId;
	}
	
	var weightToValue = mbplOutManifestTO.finalWeight + "";
	var weightKgValue = "";
	var weightGmValue = "";
	weightKgValue = weightToValue.split(".");
	document.getElementById("weight" + RowNo).value = weightKgValue[0];
	weightGmValue = weightToValue.split(".")[1];
	document.getElementById("weightGm" + RowNo).value = weightGmValue;
	if (weightKgValue.length == 0 || weightGmValue == ""
			|| weightGmValue == null) {
		document.getElementById("weightGm" + RowNo).value = "000";
		weightGmValue += "000";
	} else if (weightGmValue.length == 1) {
		document.getElementById("weightGm" + RowNo).value += "00";
		weightGmValue += "00";
	} else if (weightGmValue.length == 2) {
		document.getElementById("weightGm" + RowNo).value += "0";
		weightGmValue += "0";
	}
	maxWtCheck(RowNo);

	isSaved = false;

	hideProcessing();
}

function cancelPage() {
	var url = "./mbplOutManifest.do?submitName=viewMBPL";
	window.location = url;
}

function refreshPage() {
	var url = "./mbplOutManifest.do?submitName=viewMBPL";
	window.location = url;
	/*document.mbplOutManifestForm.action = url;
	document.mbplOutManifestForm.submit();*/
}

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		var url = "./mbplOutManifest.do?submitName=viewMBPL";
		window.location = url;
		/*document.mbplOutManifestForm.action = url;
		document.mbplOutManifestForm.submit();*/
	}
}

function enterKeyForManifestType(event, manifestTypeObj) {
	var originOfficeTypeCode=getDomElementById("originOfficeTypeCode").value
	if (manifestTypeObj.value == MANIFEST_TYPE_TRANSHIPMENT && 
			originOfficeTypeCode == OFF_TYPE_CODE_BRANCH){
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

function focusOnclose() {
	var maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
	if (isAllRowInGridFilled(maxManifestAllowed)) {
		var close = document.getElementById("closeBtn");
		setTimeout(function() {
			close.focus();
		}, 20);
	}
}

function setIFrame(){
	var manifestNO = document.getElementById("manifestNo").value;
	if (!isNull(manifestNO)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		var url = "./mbplOutManifest.do?submitName=printMBPLOutManifest&manifestNo="
				+ manifestNO + "&loginOfficeId=" + loginOffceID;
		printUrl = url;
		printIframe(printUrl);
	}
}

function isValidManifestNoForMBPLGrid(manifestNoObj, manifestScanlevel) {
	var isValidReturnVal = true;
	if (!isNull(manifestNoObj.value)) {
		var seriesType = getDomElementById("seriesType").value;

		if (manifestNoObj.value.length < 10 || manifestNoObj.value.length > 10) {
			alert('Manifest No. must contain atleast 10 characters');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		} else {
			if (!isNull(OGM_SERIES) && seriesType == OGM_SERIES) {
				if (!numpattern.test(manifestNoObj.value.substring(3))) {
					alert('Manifest number format is not correct');
					manifestNoObj.value = "";
					setTimeout(function() {
						manifestNoObj.focus();
					}, 10);
					isValidReturnVal = false;
				}
			} else if (!isNull(MBPL_SERIES) && seriesType == MBPL_SERIES) {
				isValidReturnVal = mbplValidation(manifestNoObj);
			} else if (!isNull(BPL_SERIES) && seriesType == BPL_SERIES) {
				isValidReturnVal = bplValidation(manifestNoObj);
			}
		}

	} else {
		isValidReturnVal = false;
	}
	return isValidReturnVal;
}