var scanedConsignment = new Array();
var scanedAllchildCNs = new Array();

$(document).ready(function() {
	var oTable = $('#outManifstTable').dataTable({
		"sScrollY" : "220",
		"sScrollX" : "100%",
		"sScrollXInner" : "200%",
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
	// getWeightFromWeighingMachine();
	getWeightFromWeighingMachine();
	document.getElementById("manifestNo").focus();
});

var IS_POLICY_MANDATORY = "";
var MIN_DEC_VAL = "";
var MAX_DEC_VAL = "";

/** The ROW_COUNT */
var ROW_COUNT = "";
/** The deletionRowIds */
var deletionRowIds = null;

var ERROR_FLAG = "ERROR";

var isSaved = false;
var isDeleted = false;

var isFirstRecExceedsWtLmt = "N";
var cnWeightFromWM = 0.000;

var PIECES_NEW = 0;
var PIECES_OLD = 0;
var PIECES_ACTUAL = 0;

var isCnWeightExceeded = false;

var loginCity = "";
var loginRepHub = "";

var destRegionVal = "";
var destCityVal = "";
var destOfficeVal = "";
var manifestTypeVal = "";
var destOfficeTypeVal = "";

var printUrl;
// To check the mandatory fields entry for Consignment validations
/**
 * validateCnMandatoryFields
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function validateCnMandatoryFields() {
	var manifestType = getDomElementById("manifestType").value;
	var destRegion = getDomElementById("destRegionId").value;
	var destCity = getDomElementById("destCity").value;
	var destOfficeType = getDomElementById("destOfficeType").value;
	var destOffice = getDomElementById("destOffice").value;
	var bagLockNo = getDomElementById("bagLockNo").value;
	// BR1: For manifest type as Pure destination should be mandatory
	var manifestNo = document.getElementById("manifestNo").value;

	if (isNull(manifestNo)) {
		alert("Please enter BPL manifest Number");
		getDomElementById("manifestNo").focus();
	} else if (isNull(manifestType)) {
		alert("Please enter manifest type");
		getDomElementById("manifestType").focus();
	} else if (isNull(destRegion)) {
		alert("Please select destination region");
		getDomElementById("destRegionId").focus();
	} else if (isNull(destCity)) {
		alert("Please enter destination city");
		getDomElementById("destCity").focus();
	} else if (isNull(destOfficeType)) {
		alert("Please select destination office type");
		getDomElementById("destOfficeType").focus();
	} else if (isNull(bagLockNo)) {
		alert("Please enter Bag Lock No");
		getDomElementById("bagLockNo").focus();
	} else if (!isNull(manifestType) && manifestType == MANIFEST_TYPE_PURE) {
		if (destOffice == undefined && destOffice == null && destOffice == ""
				&& destOffice == "null" && destOffice == " ") {
			alert("Please enter destination office");
			getDomElementById("destOffice").focus();
		}
	}
	// checkCNMaditoryFields();
}
// To Check the duplicate number occurance in the grid
/**
 * isDuplicateConsignment
 * 
 * @param consgNumberObj
 * @returns {Boolean}
 * @author shahnsha
 */
function isDuplicateConsignment(consgNumberObj) {
	var isValid = true;
	if (isValidConsignment(consgNumberObj)) {
		var currentCn = consgNumberObj.value;
		var currentRowId = getRowId(consgNumberObj, "consigntNo");
		var cnList = document.getElementsByName("to.consgNos");
		for ( var i = 0; i < cnList.length; i++) {
			var rid = getRowId(cnList[i], 'consigntNo');
			if (rid != currentRowId) {
				if (cnList[i].value.toUpperCase() == currentCn.toUpperCase()) {
					// duplicate cn exist at row number i+1
					alert("Duplicate consignment number entered");
					consgNumberObj.value = "";
					setTimeout(function() {
						consgNumberObj.focus();
					}, 10);
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
 * @returns {Boolean}
 */
function checkCNMaditoryFields() {
	var isValid = true;
	var cnList = document.getElementsByName("to.consgNos");
	for ( var rid = 1; rid < cnList.length; rid++) {
		var cnNo = getDomElementById("consigntNo" + rid).value;
		if (!isNull(cnNo)) {
			isValid = isValidRow(rid);
			if (!isValid) {
				break;
			}
		}
	}
}

/**
 * validateConsignment
 * 
 * @param consgNumberObj
 * @author shahnsha
 */
function validateConsignment(consgNumberObj) {

	var rowId = getRowId(consgNumberObj, "consigntNo");
	var domValue = $.trim(consgNumberObj.value);
	isConsigValid = isValidConsignment(consgNumberObj);
	var isScaned = checkedScanConsignments(domValue);
	var isInchildCN = checkInScanChildConsignments(domValue);
	if (isInchildCN == false) {
		alert("Consignment already entered as a Child CN");
		consgNumberObj.value = "";
		setTimeout(function() {
			consgNumberObj.focus();
		}, 20);
		return false;
	}
	if (isConsigValid && isDuplicateConsignment(consgNumberObj) && isScaned
			&& isInchildCN) {
		if (!consgNumberObj.readOnly && !isNull(domValue)) {
			var destOfficeObj = getDomElementById("destOffice");
			var destCityObj = getDomElementById("destCity");
			var destOfficeId = "";
			var destCityId = "";
			destOfficeId = destOfficeObj.value;
			destCityId = destCityObj.value;
			var officeType = getDomElementById("destOfficeType").value;
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var allowedConsgManifestedType = getDomElementById("allowedConsgManifestedType").value;
			var isPincodeServChk = getPincodeServChk();
			var manifestDirectn = "O";
			var manifestNo = document.getElementById("manifestNo").value;
			// var transhipmentCityIds=prepareTranshipmentCityIds();
			var bplManifestType = getDomElementById("manifestType").value;
			var loginOfficeType = getDomElementById("loginOfficeType").value;
			setTimeout(function() {
				showProcessing();
			}, 10);
			ROW_COUNT = getRowId(consgNumberObj, "consigntNo");
			url = './outManifestParcel.do?submitName=getConsignmentDtls&consgNumber='
					+ consgNumberObj.value
					+ "&officeId="
					+ destOfficeId
					+ "&cityId="
					+ destCityId
					+ "&officeType="
					+ officeType
					+ "&loginOfficeId="
					+ loginOfficeId
					+ "&manifestDirection="
					+ manifestDirectn
					+ "&allowedConsgManifestedType="
					+ allowedConsgManifestedType
					+ "&isPincodeServChk="
					+ isPincodeServChk
					+ "&bplManifestType="
					+ bplManifestType
					+ "&loginOfficeType="
					+ loginOfficeType
					+ "&manifestNo="
					+ manifestNo;

			jQuery.ajax({
				url : url,
				success : function(req) {
					populateConsDtls(req, consgNumberObj);
				}
			});
		}
	}
	if (isScaned) {
		var cnRowId = parseInt(rowId) + 1;
		var nextCn = getDomElementById("consigntNo" + cnRowId);
		if (nextCn != null) {
			nextCn.focus;
		}
	}
	domValue = $.trim(consgNumberObj.value);
	if (isNull(domValue)) {
		// var cnList = document.getElementsByName("to.consgNos");
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		scanedConsignment = new Array();
		for ( var i = 1; i <= maxCNsAllowed; i++) {
			if (!isNull(getDomElementById("consigntNo" + i).value)) {
				var cnNo = getDomElementById("consigntNo" + i).value;
				if (!isNull(cnNo)) {
					scanedConsignment[scanedConsignment.length] = cnNo;
				}
			}
		}
		clearRows(rowId);
	}
	hideProcessing();
}

/**
 * printCallBackConsignment
 * 
 * @param data
 * @param consgNumberObj
 * @author shahnsha
 */
/*
 * function printCallBackConsignment(data, consgNumberObj) { var response =
 * data; if (!isNull(response)) { ROW_COUNT = getRowId(consgNumberObj,
 * "consigntNo"); if (response != "error") { var cnValidation = eval('(' +
 * response + ')'); if (cnValidation != null && cnValidation != "") { if
 * (!isNull(cnValidation.errorMsg)) { alert(cnValidation.errorMsg);
 * clearRows(ROW_COUNT); setTimeout(function() { consgNumberObj.focus(); }, 10);
 * hideProcessing(); } else if (cnValidation.isConsInManifestd == "Y" ) {
 * getInManifestConsDtls(consgNumberObj); } else
 * if(cnValidation.isConsInManifestd == "N" && cnValidation.isConsAllowed ==
 * "N"){ alert("Invalid consignment "); consgNumberObj.value = "";
 * setTimeout(function() { consgNumberObj.focus(); }, 10); hideProcessing(); }
 * else if(cnValidation.isConsParcel == "N") { alert("Only parcel type
 * consignments are allowed"); consgNumberObj.value = ""; setTimeout(function() {
 * consgNumberObj.focus(); }, 10); hideProcessing(); } else { // to get
 * consignment details populateConsignmentDetails(data); } } } else if (response ==
 * "error") { alert("Error occured! Try again."); clearRows(ROW_COUNT);
 * setTimeout(function() { consgNumberObj.focus(); }, 10); hideProcessing(); } }
 * //jQuery.unblockUI(); }
 */

/* @Desc: Gets the inManifestconsignment details */
/*
 * function getInManifestConsDtls(consObj) { var consNo = consObj.value; var
 * consid = getRowId(consObj, "consigntNo"); ROW_COUNT = consid; var url =
 * "./outManifestParcel.do?submitName=getInManifestedConsignmentDetails&consignmentNo="+consNo;
 * ajaxCallWithoutForm(url, populateConsignmentDetails); }
 */
/** *To populate the consignment details******** */
/**
 * populateConsignmentDetails
 * 
 * @param data
 * @author shahnsha
 */
/*
 * function populateConsignmentDetails(data) { bookingDetail=data; if
 * (!isNull(bookingDetail)) { getWtFromWMForOGM(); } }
 */
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
function getDestinationDtlsByBplManifestType() {
	var manifestType = getDomElementById("manifestType").value;
	var loginOffType = getDomElementById("loginOfficeType").value;
	if ((manifestType == MANIFEST_TYPE_TRANSHIPMENT)
			&& (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
					.toLowerCase())) {
		// On selection of transshipment at branch, the drop down for
		// destination region, city, office should be disabled.
		getDomElementById("destRegionId").disabled = true;
		getDomElementById("destCity").disabled = true;
		getDomElementById("destOffice").disabled = true;
		var loginRegion = getDomElementById("regionId").value;
		showDropDownBySelected("destRegionId", loginRegion);
		getDomElementById("destRegionId").value = loginRegion;
		loginCity = getDomElementById("loginCityId").value;
		loginRepHub = getDomElementById("loginRepHub").value;
		getCitiesByRegion();
	} else {
		getDomElementById("destRegionId").disabled = false;
		getDomElementById("destCity").disabled = false;
		getDomElementById("destOffice").disabled = false;
		getDomElementById("destOfficeType").disabled = false;

		showDropDownBySelected("destRegionId", "");
		clearDropDownList("destCity", "SELECT");
		showDropDownBySelected("destOfficeType", "");
		clearDropDownList("destOffice", "SELECT");
		setTimeout(function() {
			document.getElementById('destRegionId').focus();
		}, 10);
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
function printAllCities(data) {
	if (!isNull(data)) {
		var error = data[ERROR_FLAG];
		if (error != null) {
			showDropDownBySelected("destRegionId", "");
			alert(error);
			createDropDown("destCity", "", "SELECT");
			createDropDown("destOffice", "", "SELECT");
			document.getElementById('destRegionId').focus();
		}
		var content = document.getElementById('destCity');
		content.innerHTML = "";
		createDropDown("destCity", "", "--Select--");
		$.each(data, function(index, value) {
			addOptionTODropDown("destCity", this.cityName, this.cityId);
		});
		showDropDownBySelected('destCity', loginCity);
		getOfficeTypeByBplManifestType();
	}/*
		 * else { showDropDownBySelected("destRegionId", ""); alert("No cities
		 * found for the selected region"); createDropDown("destCity", "",
		 * "SELECT"); createDropDown("destOffice", "", "SELECT");
		 * document.getElementById('destRegionId').focus(); }
		 */
	hideProcessing();
}

function getOfficesByCityAndoffType() {
	var destCityId = getDomElementById("destCity").value;
	var officeTypeId = document.getElementById("destOfficeType").value;
	if (!isNull(destCityId) && !isNull(officeTypeId)) {
		showProcessing();
		url = './outManifestDox.do?submitName=getAllOfficesByCityAndOfficeType&cityId='
				+ destCityId + "&officeTypeId=" + officeTypeId;

		ajaxCallWithoutForm(url, printAllOfficesList);
	} else {
		createDropDown("destOffice", "", "SELECT");
		document.getElementById("destOfficeType").value = "";
	}
}

function printAllOfficesList(data) {
	if (!isNull(data)) {
		var loginOfficeType = getDomElementById("loginOfficeType").value;
		// var officeToList = jsonJqueryParser(data);
		// var officeToList = eval('('+data+')');
		var officeToList = data;
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
								createDropDown("destOffice", "", "SELECT");
								alert("No offices found for the selected city");
							} else if (officeType != OFF_TYPE_REGION_HEAD_OFFICE
									&& officeToList.length >= 2) {
								if (loginOfficeType == OFF_TYPE_CODE_BRANCH) {
									var manifestTypeObj = getDomElementById("manifestType");
									if (manifestTypeObj.value == MANIFEST_TYPE_PURE) {
										addOptionTODropDown("destOffice",
												officeTO.officeName,
												officeTO.officeId);
										showDropDownBySelected("destOffice",
												"0");
									} else if (officeType.toLowerCase() == OFF_TYPE_CODE_HUB
											.toLowerCase()) {
										addOptionTODropDown("destOffice",
												officeTO.officeName,
												officeTO.officeId);
										if (!isNull(loginRepHub)
												&& loginRepHub == officeTO.officeId) {
											showDropDownBySelected(
													"destOffice",
													officeTO.officeId);
										}
									}
								} else {
									addOptionTODropDown("destOffice",
											officeTO.officeName,
											officeTO.officeId);
									showDropDownBySelected("destOffice", "0");
								}
								// Only Hub of the City should be considered
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
	} else {
		showDropDownBySelected("destCity", "");
		createDropDown("destOffice", "", "SELECT");
		alert("No offices found for the selected city");
		document.getElementById("destOfficeType").value = "";
		document.getElementById('destCity').focus();
	}
	hideProcessing();
}

function getPincodeServChk() {
	var loginCityId = getDomElementById("loginCityId").value;
	var destCityId = getDomElementById("destCity").value;
	var bplManifestType = getDomElementById("manifestType").value;
	var isPincodeServChk = "";
	// Origin Branch to Origin HUB (Malad to Marol)
	if (loginCityId == destCityId) {
		// - PURE
		if (bplManifestType == MANIFEST_TYPE_PURE) {
			isPincodeServChk = "Y";
		}
		// – TRANSHIPMENT
		else if (bplManifestType == MANIFEST_TYPE_TRANSHIPMENT) {
			isPincodeServChk = "N";
		}
	}
	return isPincodeServChk;
}
function prepareTranshipmentCityIds() {
	var transhipmentCityIds = "";
	var loginCityId = getDomElementById("loginCityId").value;
	var destCityObj = getDomElementById("destCity");
	var bplManifestType = getDomElementById("manifestType").value;
	if (loginCityId != destCityObj.value) {
		if (bplManifestType == MANIFEST_TYPE_TRANSHIPMENT) {
			for ( var i = 0; i < destCityObj.options.length; i++) {
				if (!isNull(destCityObj.options[i].value))
					transhipmentCityIds = transhipmentCityIds
							+ destCityObj.options[i].value + ",";
			}
		}
	}
	return transhipmentCityIds;
}

/**
 * validateHeader
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function validateHeader() {
	var flag = true;
	var manifestNo = getDomElementById("manifestNo").value;
	var manifestType = getDomElementById("manifestType").value;
	var destRegion = getDomElementById("destRegionId").value;
	var destCity = getDomElementById("destCity").value;
	var destOfficeType = getDomElementById("destOfficeType").value;
	var destOffice = getDomElementById("destOffice").value;
	// var finalWeight = getDomElementById("finalWeight").value;
	var bagLockNo = getDomElementById("bagLockNo").value;
	var errorMsg = "Please provide : ";
	if (isNull(manifestNo)) {
		flag = false;
		errorMsg = errorMsg + "Manifest no";
		// alert("Please enter manifest no");
		getDomElementById("manifestNo").focus();
	}
	if (isNull(manifestType)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Manifest Type";
		// alert("Please select manifest type");
		getDomElementById("manifestType").focus();
	}
	if (isNull(destRegion)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination Region";
		// alert("Please enter destination region");
		getDomElementById("destRegionId").focus();
	}
	if (isNull(destCity)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination City";
		// alert("Please enter destination city");
		getDomElementById("destCity").focus();
	}
	if (isNull(destOfficeType)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination Office Type";
		// alert("Please enter destination office type");
		getDomElementById("destOfficeType").focus();
	}
	if (isNull(bagLockNo)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Bag Lock no.";
		// alert("Please enter destination region");
		getDomElementById("bagLockNo").focus();
	}
	if (destOffice == undefined || destOffice == null || destOffice == ""
			|| destOffice == "null" || destOffice == " ") {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination Office";
		// alert("Please enter destination office");
		getDomElementById("destOffice").focus();
	} /*
		 * if (isEmptyWeight(finalWeight)){ if(!flag){ errorMsg=errorMsg+", "; }
		 * flag = false; errorMsg=errorMsg+"Final weight"; //alert("Please enter
		 * final weight"); }
		 */
	if (!flag) {
		alert(errorMsg);
	}
	return flag;
}
/**
 * validateGridDetails
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function validateGridDetails() {
	var flag = true;
	var isAtleastOneRecEntered = false;
	/*
	 * var consgId = ""; var bookingId = "";
	 */
	var cnNo = "";
	for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
		cnNo = getDomElementById("consigntNo" + count).value;
		// consgId = getDomElementById("consgId" + count).value;
		// bookingId = getDomElementById("bookingId" + count).value;
		if (!isNull(cnNo)) {
			isAtleastOneRecEntered = true;
			flag = isValidRow(count);
			if (!flag)
				break;
		}
	}
	if (!isAtleastOneRecEntered) {
		flag = false;
		alert("Atleast one consignment should be there in a parcel");
	}
	return flag;
}

function isValidRowForRateCalculation(count) {
	var flag = true;
	var cnNo = getDomElementById("consigntNo" + count).value;
	var noOfPcs = getDomElementById("noOfPcs" + count).value;
	var childCns = getDomElementById("childCns" + count).value;
	var pincode = getDomElementById("pincode" + count).value;
	var weight = getDomElementById("actWeight" + count).value;
	weight = parseFloat(weight).toFixed(3);
	var contentCode = getDomElementById("contentCode" + count).value;
	var otherContent = getDomElementById("cnContentOther" + count).value;
	var decValue = getDomElementById("declaredValue" + count).value;
	var insuredByObj = getDomElementById("insuredBy" + count);
	var insuredByText = insuredByObj.options[insuredByObj.selectedIndex].text;
	var policyNo = getDomElementById("policyNo" + count).value;
	var isPickupCN = getDomElementById("isCNProcessedFromPickup" + count).value;
	var codOrLcAmt = getDomElementById("codAmt" + count).value;
	var productCode = cnNo.substring(4, 5);
	if (isNull(cnNo)) {
		flag = false;
	} else if (isNull(noOfPcs)) {
		flag = false;
	} else if (noOfPcs > 1 && isNull(childCns)) {
		flag = false;
	} else if (isNull(weight) || isEmptyWeight(weight)) {
		flag = false;
	} else if (isNull(pincode)) {
		flag = false;
	} else if (isNull(contentCode) && isNull(otherContent)) {
		flag = false;
	} else if (isEmptyRate(decValue)) {
		flag = false;
	} else if (isPickupCN == "Y") {
		if ((!isNull(insuredByText)
				&& insuredByText.toUpperCase() == "CONSIGNOR" && isNull(policyNo))) {
			flag = false;
		}
		if (productCode == "L" || productCode == "D") {
			if (isNull(codOrLcAmt)) {
				flag = false;
			}
		}
	}
	return flag;

}

function isValidRow(count) {
	var flag = true;
	var cnNo = getDomElementById("consigntNo" + count).value;
	var noOfPcs = getDomElementById("noOfPcs" + count).value;
	var childCns = getDomElementById("childCns" + count).value;
	var pincode = getDomElementById("pincode" + count).value;
	var weight = getDomElementById("actWeight" + count).value;
	weight = parseFloat(weight).toFixed(3);
	var contentCode = getDomElementById("contentCode" + count).value;
	var otherContent = getDomElementById("cnContentOther" + count).value;
	var decValue = getDomElementById("declaredValue" + count).value;
	var insuredByObj = getDomElementById("insuredBy" + count);
	var insuredByText = insuredByObj.options[insuredByObj.selectedIndex].text;
	var policyNo = getDomElementById("policyNo" + count).value;
	var isPickupCN = getDomElementById("isCNProcessedFromPickup" + count).value;
	var codOrLcAmt = getDomElementById("codAmt" + count).value;
	var toPayAmount = getDomElementById("toPayAmt" + count).value;
	var productCode = cnNo.substring(4, 5);
	if (isNull(cnNo)) {
		alert("Please enter consignment number");
		getDomElementById("consigntNo" + count).disabled = false;
		getDomElementById("consigntNo" + count).focus();
		flag = false;
	} else if (isNull(noOfPcs)) {
		alert("Please enter no of Pieces");
		getDomElementById("noOfPcs" + count).disabled = false;
		getDomElementById("noOfPcs" + count).focus();
		flag = false;
	} else if (noOfPcs > 1 && isNull(childCns)) {
		alert("Please enter child consignment number for " + cnNo
				+ " at line : " + count);
		getDomElementById("noOfPcs" + count).disabled = false;
		getDomElementById("noOfPcs" + count).focus();
		flag = false;
	} else if (isNull(weight) || isEmptyWeight(weight)) {
		alert("Please enter weight");
		getDomElementById("actWeightKg" + count).focus();
		flag = false;
	} else if (isNull(pincode)) {
		alert("Please enter pincode");
		getDomElementById("pincode" + count).focus();
		flag = false;
	} else if (isNull(contentCode) && isNull(otherContent)) {
		alert("Please enter content description");
		getDomElementById("cnContentOther" + count).disabled = false;
		getDomElementById("cnContentOther" + count).focus();
		flag = false;
	} else if (isEmptyRate(decValue)) {
		alert("Please enter declared value");
		getDomElementById("declaredValue" + count).disabled = false;
		getDomElementById("declaredValue" + count).focus();
		flag = false;
	} else if (isNull(insuredByObj.value)) {
		alert("Please Select InsuredBy");
		insuredByObj.disabled = false;
		insuredByObj.focus();
		flag = false;
	} else if (isPickupCN == "Y") {
		if ((!isNull(insuredByText)
				&& insuredByText.toUpperCase() == "CONSIGNOR" && isNull(policyNo))) {
			alert("Please enter policy number.");
			getDomElementById("policyNo" + count).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNo" + count).focus();
			}, 10);
			flag = false;
		}
		/*
		 * if (productCode == "T") { if (isNull(toPayAmount)) { alert("Please
		 * enter To Pay Amount."); getDomElementById("toPayAmt" +
		 * count).disabled = false; setTimeout(function() {
		 * getDomElementById("toPayAmt" + count).focus(); }, 10); flag = false; } }
		 */
		if (!isNull(codOrLcAmt)) {
			codOrLcAmt = parseFloat(codOrLcAmt).toFixed(2);
		}
		if (productCode == "L" || productCode == "D") {
			if (isNull(codOrLcAmt) || codOrLcAmt == "0.00" || codOrLcAmt == "0.0" || codOrLcAmt == "0.000") {
				alert("Please enter COD Amount.");
				getDomElementById("codAmt" + count).disabled = false;
				setTimeout(function() {
					getDomElementById("codAmt" + count).focus();
				}, 10);
				flag = false;
			}
		}
	}
	return flag;
}
/**
 * isValidDecValue
 * 
 * @param obj
 * @author shahnsha
 */
function isValidDecValue(obj) {
	if (!isNull(obj.value)) {
		ROW_COUNT = getRowId(obj, "declaredValue");
		var bookingType = getDomElementById("bookingType" + ROW_COUNT).value;
		if (!isNull(bookingType)) {
			var decVal = obj.value;
			url = './outManifestParcel.do?submitName=validateDeclaredvalue&declaredVal='
					+ decVal + '&bookingType=' + bookingType;
			ajaxCallWithoutForm(url, setBookingDtlsResponse);
		}
	}
}

/**
 * setBookingDtlsResponse
 * 
 * @param data
 * @author shahnsha
 */
function setBookingDtlsResponse(data) {
	if (!isNull(data)) {
		var error = data.errorMsg;
		if (!isNull(error)) {
			alert('Declared value above the maximum limit is not allowed');
			getDomElementById("declaredValue" + ROW_COUNT).value = "";
			setTimeout(function() {
				getDomElementById("declaredValue" + ROW_COUNT).focus();
			}, 10);
		}
		if (data.isValidDeclaredVal == "N") {
			alert('Declared value above the maximum limit is not allowed');
			getDomElementById("declaredValue" + ROW_COUNT).value = "";
			setTimeout(function() {
				getDomElementById("declaredValue" + ROW_COUNT).focus();
			}, 10);
		} else {
			getPaperWorks(ROW_COUNT);
		}
	}
}
/**
 * getPaperWorks
 * 
 * @param ROW_COUNT
 * @author shahnsha
 */
function getPaperWorks(ROW_COUNT) {
	var declaredValue = getDomElementById('declaredValue' + ROW_COUNT).value;
	var pincode = getDomElementById('pincode' + ROW_COUNT).value;
	if (!isNull(declaredValue) && !isNull(pincode)) {
		var url = "outManifestParcel.do?submitName=getPaperWorks&pincode="
				+ pincode + "&declaredValue=" + declaredValue;
		ajaxCallWithoutFormWithSyn(url, getPaperWorksResponse);
	}
}
/**
 * getPaperWorksResponse
 * 
 * @param data
 * @author shahnsha
 */
function getPaperWorksResponse(data) {
	if (data != null) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var content = getDomElementById('cnPaperWorkselect' + ROW_COUNT);
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			if (!isNull(data)) {
				$.each(data, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.cnPaperWorkId + '#'
							+ this.cnPaperWorkCode;
					option.appendChild(document
							.createTextNode(this.cnPaperWorkName));
					content.appendChild(option);
				});
			}
		}
	} else {
		var content = getDomElementById('cnPaperWorkselect' + ROW_COUNT);
		content.innerHTML = "";
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
	}
}
/** The DEC_VAL */
var DEC_VAL = "";
/**
 * getInsuarnceConfigDtls
 * 
 * @param obj
 * @author shahnsha
 */
function getInsuarnceConfigDtls(obj) {
	ROW_COUNT = getRowId(obj, "declaredValue");
	var bookingType = getDomElementById("bookingType" + ROW_COUNT).value;
	var insuredByNo = obj.value;
	var decVal = getDomElementById("declaredValue" + ROW_COUNT).value;
	if (!isNull(decVal) && !isNull(bookingType)) {
		DEC_VAL = decVal;
		url = './baBookingParcel.do?submitName=getInsuarnceConfigDtls&declaredVal='
				+ decVal
				+ '&insNo='
				+ insuredByNo
				+ '&bookingType='
				+ bookingType;
		ajaxCallWithoutFormWithSyn(url, setInsDtlsResponse);
	}
}

function ajaxCallWithoutFormWithSyn(pageurl, ajaxResponse) {
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			ajaxResponse(data);
		},
		async : false
	});

}

/**
 * setInsDtlsResponse
 * 
 * @param data
 * @author shahnsha
 */
function setInsDtlsResponse(data) {
	if (!isNull(data)) {
		var insDetails = data;
		if (insDetails.length == 1) {
			IS_POLICY_MANDATORY = insDetails[0].isPolicyNoMandatory;
			MIN_DEC_VAL = insDetails[0].minDeclaredValue;
			MAX_DEC_VAL = insDetails[0].maxDeclaredValue;
			if (insDetails[0].trasnStatus != "NOINSURENCEMAPPING") {
				showDropDownBySelected('insuredBy' + ROW_COUNT,
						insDetails[0].insuredById);
				if (DEC_VAL == MIN_DEC_VAL) {
					getDomElementById("insuredBy" + ROW_COUNT).disabled = false;
				} else if (DEC_VAL >= MIN_DEC_VAL && DEC_VAL <= MAX_DEC_VAL) {
					getDomElementById("insuredBy" + ROW_COUNT).disabled = true;
				}
			}
			var insuredByDom = getDomElementById("insuredBy" + ROW_COUNT);
			var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
			if (selectedText.toUpperCase() == "CONSIGNOR") {
				getDomElementById("policyNo" + ROW_COUNT).disabled = false;
				setTimeout(function() {
					getDomElementById("policyNo" + ROW_COUNT).focus();
				}, 10);
			}
		} else {
			getDomElementById("insuredBy" + ROW_COUNT).disabled = false;
			setTimeout(function() {
				getDomElementById("insuredBy" + ROW_COUNT).focus();
			}, 10);
		}
	} else {
		getDomElementById("insuredBy" + ROW_COUNT).disabled = false;
		setTimeout(function() {
			getDomElementById("insuredBy" + ROW_COUNT).focus();
		}, 10);
	}
}
/**
 * redirectToVolumetricWeight
 * 
 * @param ROW_COUNT
 * @author shahnsha
 */
function redirectToVolumetricWeight(ROW_COUNT) {
	getDomElementById("isDataMismatch" + ROW_COUNT).value = "Y";
	url = "./volumetricWeight.do?rowCount=" + ROW_COUNT + "&processCode=OGM";
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');
}

/**
 * @Desc check wheather manifestNo is already manifested or not to avoid
 *       duplicate entry into the database before save or close.
 * @param action
 */
/*
 * function saveOrCloseOutManifestParcel(action) { //if (action ==
 * "close")isSaved = false; var
 * manifestStatus=getDomElementById("manifestStatus").value; if (isSaved &&
 * manifestStatus == MANIFEST_STATUS_CLOSE) alert("Manifest already closed.");
 * else if (validateHeader() && validateGridDetails()) { var manifestId =
 * getDomElementById("manifestId").value; if (isNull(manifestId)) {// create new
 * manifest
 *//** To avoid duplicate entry into Database */
/*
 * isManifestNoSavedOrClosed(action); } else { // save or close after search
 * checkIfDeletedBeforeSave(action); } } }
 */

function saveOrCloseOutManifestParcel(action) {

	// if all row is filled then manifestStatus should be closed ("C")
	// if(isAllRowInGridFilled(maxCNsAllowed)) action='close';
	var manifestStatus = getDomElementById("manifestStatus").value;
	if (action == 'save') {// Open
		getDomElementById("manifestStatus").value = MANIFEST_STATUS_OPEN;
	} else if (action == 'close') {// Close
		getDomElementById("manifestStatus").value = MANIFEST_STATUS_CLOSE;
	}

	if ((action == 'close') && manifestStatus == MANIFEST_STATUS_CLOSE)
		alert("Manifest already closed.");
	else if (validateHeader() && validateGridDetails()) {
		enableOrDisableHeader(false);
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		for ( var count = 1; count <= (parseIntNumber(maxCNsAllowed)); count++) {
			enableOrDisableGrid(count, false);
		}

		showProcessing();
		var url = './outManifestParcel.do?submitName=saveOrUpdateOutManifestParcel';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#outManifestParcelForm").serialize(),
			success : function(req) {
				printCallBackManifestSave(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				hideProcessing();
				alert("Server error : " + errorThrown);
			}
		});
	}
}

/**
 * @Desc check whether manifest number is already saved or closed (To avoid
 *       duplicate entry into DataBase)
 * @param action
 */
/*
 * function isManifestNoSavedOrClosed(action) { var manifestNo =
 * getDomElementById("manifestNo"); var loginOfficeId =
 * getDomElementById("loginOfficeId").value; var processCode =
 * getDomElementById("processCode").value; var url =
 * "./branchOutManifestParcel.do?submitName=isManifested" + "&manifestNo=" +
 * manifestNo.value + "&loginOfficeId=" + loginOfficeId + "&processCode=" +
 * processCode;
 * 
 * jQuery.ajax({ url : url, success : function(req) { hideProcessing(); if (req ==
 * "FAILURE") { alert("Manifest number already used to create another
 * manifest."); manifestNo.value = ""; setTimeout(function() {
 * manifestNo.focus(); }, 10); return false; } else {
 * checkIfDeletedBeforeSave(action); } } }); }
 */

/**
 * @Desc check if delete before save or close
 * @param action
 */
/*
 * function checkIfDeletedBeforeSave(action) { var manifestId =
 * getDomElementById("manifestId").value; // delete the Consg/Manifest/Co-Mail
 * before saving if (isDeleted && !isNull(manifestId)) { deleteTableRow(action); }
 * else { performSaveOrClose(action); } }
 */

/**
 * performSaveOrClose: actually perform save or close operation
 * 
 * @param action
 * @author shahnsha
 */
/*
 * function performSaveOrClose(action) { // if all row is filled then
 * manifestStatus should be closed ("C") if
 * (isAllRowInGridFilled(MAX_CNS_ALLOWED)) action = 'close'; // if wt is 38.5
 * then close should be happen even though user click on "save // button" var
 * maxWeightAllowed = parseFloat(getDomElementById("maxWeightAllowed").value);
 * //Code commented By narasimha var maxTolerenceAllowed =
 * parseFloat(getDomElementById("maxTolerenceAllowed").value); var
 * maxAllowedWtWithTollrence = maxWeightAllowed + (maxWeightAllowed *
 * maxTolerenceAllowed / 100); if (maxWeightAllowed ==
 * (getDomElementById("finalWeight").value)) action = 'close';
 * 
 * if (action == 'save') {// Open getDomElementById("manifestStatus").value =
 * MANIFEST_STATUS_OPEN; } else if (action == 'close') {// Close
 * getDomElementById("manifestStatus").value = MANIFEST_STATUS_CLOSE; }
 * enableOrDisableHeader(false); for ( var count = 1; count <= MAX_CNS_ALLOWED;
 * count++) { if(!isNull(getValueByElementId("consigntNo"+count))){
 * enableOrDisableGrid(count, false); }else{ enableOrDisableGrid(count, true); } }
 * showProcessing(); for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
 * if(!isNull(getValueByElementId("consigntNo"+count))){
 * enableOrDisableGrid(count, false); } } var url =
 * './outManifestParcel.do?submitName=saveOrUpdateOutManifestParcel';
 * jQuery.ajax({ url : url, type: "POST", data :
 * jQuery("#outManifestParcelForm").serialize(), success : function(req) {
 * printCallBackManifestSave(req); }, error : function(jqXHR, textStatus,
 * errorThrown) { jQuery.unblockUI(); alert("Server error : "+errorThrown); }
 * }); }
 */
/**
 * printCallBackManifestSave
 * 
 * @param data
 * @author shahnsha
 */
/*
 * function printCallBackManifestSave(data) { var response = data; if
 * (!isNull(response)) { var resultArr = response.split("."); response =
 * resultArr[0]; if(!isNull(resultArr[1]))
 * jQuery('#manifestId').val(resultArr[1]); if (response == 'CLOSE') {
 * alert("Manifest saved and closed successfully"); if(confirm("Do you want to
 * print?")){ printManifest(); }else{ refreshPage(); } for ( var count = 1;
 * count <= MAX_CNS_ALLOWED; count++) { enableOrDisableGrid(count, true); }
 * //Disable all when manifest Closed disableForClose(); isSaved = true;
 * jQuery.unblockUI(); } else if(response== 'SUCCESS'){ //When saved return
 * manifestId isSaved = true; alert("Manifest saved successfully");
 * 
 * for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
 * if(!isNull(getValueByElementId("consigntNo"+count))){
 * enableOrDisableGrid(count, true); }else{ enableOrDisableGrid(count, false); } }
 * //call search function here.. showProcessing(); //clear grid after save...
 * for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) { clearRows(count); }
 * searchManifest(); }else { alert("Manifest not saved successfully..");
 * jQuery.unblockUI(); } enableOrDisableHeader(true); } //jQuery.unblockUI(); }
 */

function printCallBackManifestSave(data) {
	var response = data;
	if (response != 'O' && response != 'C') {
		var responsetext = jsonJqueryParser(data);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			var errs = error.split("~");
			if (!isNull(errs) && errs.length > 1) {
				alert(errs[1]);
			} else {
				alert(error);
			}
			hideProcessing();
		}
	} else if (!isNull(response)) {
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		var noofRows = parseIntNumber(maxCNsAllowed);

		if (response == MANIFEST_STATUS_CLOSE) {
			// alert("Manifest saved and closed successfully");
			getDomElementById("manifestStatus").value = response;
			if (confirm("Manifest saved and closed successfully.")) {
				searchManifest();
				printManifest();
				refreshPage();
				/*
				 * setTimeout(function(){printManifest();}, 3000);
				 * setTimeout(function(){refreshPage();}, 4000);
				 */
			} else {
				refreshPage();
			}
			for ( var count = 1; count <= noofRows; count++) {
				enableOrDisableGrid(count, true);
			}
			enableOrDisableHeader(true);
			// Disable all when manifest Closed
			disableForClose();
			isManifestSaved = true;
			jQuery.unblockUI();
		} else if (response == MANIFEST_STATUS_OPEN) { // When saved return
			// manifestId
			isManifestSaved = true;
			alert("Manifest saved successfully");
			for ( var count = 1; count <= noofRows; count++) {
				if (!isNull(getDomElementById("consigntNo" + count))) {
					if (!isNull(getDomElementById("consigntNo" + count).value)) {
						enableOrDisableGrid(count, true);
					} else {
						enableOrDisableGrid(count, false);
					}
				}
			}
			// call search function here..
			showProcessing();
			// clear grid after save...
			for ( var count = 1; count <= noofRows; count++) {
				clearRows(count);
			}
			
			//Reset initial value of ws weight
			wmWeight = parseFloat(wmCapturedWeight);
			if (wmWeight == "0.000" || wmWeight == "0.0" || isNull(wmWeight)
					|| parseFloat(wmWeight) < 0) {
				getWeightFromWeighingMachine();
			}
			searchManifest();
			// hideProcessing();
		} else {
			alert(response);
			getDomElementById("manifestNo").value = "";
			getDomElementById("manifestNo").focus();
			jQuery.unblockUI();
		}
	}
}

function refreshPage() {
	var url = "outManifestParcel.do?submitName=viewOutManifestParcel";
	document.outManifestParcelForm.action = url;
	document.outManifestParcelForm.submit();
}
/**
 * enableOrDisableHeader
 * 
 * @param bool
 * @author shahnsha
 */
function enableOrDisableHeader(bool) {
	getDomElementById("manifestNo").disabled = bool;
	getDomElementById("finalKgs").disabled = bool;
	getDomElementById("finalGms").disabled = bool;
	getDomElementById("manifestType").disabled = bool;
	getDomElementById("destRegionId").disabled = bool;
	getDomElementById("destCity").disabled = bool;
	getDomElementById("destOfficeType").disabled = bool;
	getDomElementById("destOffice").disabled = bool;
	getDomElementById("bagLockNo").disabled = bool;
	getDomElementById("rfidNo").disabled = bool;
}

/**
 * enableOrDisableGrid
 * 
 * @param rowCount
 * @param bool
 * @author shahnsha
 */
function enableOrDisableGrid(rowCount, bool) {
	getDomElementById("consigntNo" + rowCount).disabled = bool;
	getDomElementById("noOfPcs" + rowCount).disabled = bool;
	getDomElementById("actWeightKg" + rowCount).disabled = bool;
	getDomElementById("actWeightGm" + rowCount).disabled = bool;
	getDomElementById("weightVW" + rowCount).disabled = bool;
	getDomElementById("weightGmVW" + rowCount).disabled = bool;
	getDomElementById("pincode" + rowCount).disabled = bool;
	getDomElementById("mobile" + rowCount).disabled = bool;
	getDomElementById("cnContentOther" + rowCount).disabled = bool;
	getDomElementById("contentCode" + rowCount).disabled = bool;
	getDomElementById("contentName" + rowCount).disabled = bool;
	getDomElementById("declaredValue" + rowCount).disabled = bool;
	getDomElementById("cnPaperWorkselect" + rowCount).disabled = bool;
	getDomElementById("cnPaperWorks" + rowCount).disabled = bool;
	getDomElementById("policyNo" + rowCount).disabled = bool;
	getDomElementById("insuredBy" + rowCount).disabled = bool;
	getDomElementById("toPayAmt" + rowCount).disabled = bool;
	getDomElementById("codAmt" + rowCount).disabled = bool;
	getDomElementById("custRefNo" + rowCount).disabled = bool;
	getDomElementById("lcBankName" + rowCount).disabled = bool;
}

// Selecting the consignment Ids and preparing a comma seperated string which is
// used to delete the selected Ids
/**
 * getConsignmntIdOnCheck
 * 
 * @param checkObj
 * @author shahnsha
 */
function getConsignmntIdOnCheck(checkObj) {
	if (checkObj.checked) {
		var consIdListAtGrid = getDomElementById("consgIdListAtGrid").value;
		var rowId = getRowId(checkObj, "chk");
		var isCN = "Y";
		deletionRowIds = rowId + ",";
		isCN = getDomElementById("isCN" + rowId).value;
		if (isCN == "Y") {
			if (isNull(consIdListAtGrid)) {
				var consignmtId = getDomElementById("consgId" + rowId).value;
				getDomElementById("consgIdListAtGrid").value = consignmtId;
			} else {
				var consignmtIds = "";
				var consId = getDomElementById("consgId" + rowId).value;
				if (!isNull(consId))
					consignmtIds = consIdListAtGrid + "," + consId;
				getDomElementById("consgIdListAtGrid").value = consignmtIds;
			}
		}
	}
}
/*
 * function capturedWeight(data){ var capturedWeight = ""; if (!isNull(data)) {
 * capturedWeight = eval('(' + data + ')'); if (capturedWeight == -1) { wmWeight =
 * capturedWeight; } else if (capturedWeight == -2) { wmWeight = "Exception
 * occurred"; } else if (!isNull(capturedWeight) && capturedWeight != -1) {
 * wmWeight = parseFloat(capturedWeight).toFixed(3);
 * weightInkgs=wmWeight.split(".")[0]; weightInGms=wmWeight.split(".")[1];
 * //disableEnableWeight(); } } }
 */
/**
 * searchManifest
 * 
 * @author shahnsha
 */
function searchManifest() {
	var manifestNo = getDomElementById("manifestNo").value;
	if (isNull(manifestNo)) {
		alert("Enter manifest no to search...");
	} else {
		// setIFrame();
		var loginOffceID = getDomElementById('loginOfficeId').value;
		url = './outManifestParcel.do?submitName=searchManifestDetails&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID;
		showProcessing();
		// Clear existing data in the grid
		for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
			clearRows(count);
		}
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestSearch(req);
			}
		});
	}
}
/**
 * printCallBackManifestSearch
 * 
 * @param data
 * @author shahnsha
 */
function printCallBackManifestSearch(data) {
	var response = "";
	response = data;
	var manifestHDetails = "";
	if (!isNull(response)) {
		doxToJsonArray = response.split("~");
		doxTOJson = doxToJsonArray[1];
		detailTOJson = doxToJsonArray[2];

		if (!isNull(doxTOJson)) {
			manifestHDetails = eval('(' + doxTOJson + ')');
			if (!isNull(manifestHDetails.ppxTO.manifestDate)) {
				getDomElementById("manifestDate").value = manifestHDetails.ppxTO.manifestDate;
				getDomElementById("manifestNo").value = manifestHDetails.ppxTO.manifestNo;
				getDomElementById("originOffice").value = manifestHDetails.ppxTO.loginOfficeName;
				var finalWeight = parseFloat(manifestHDetails.ppxTO.finalWeight)
						.toFixed(3);
				getDomElementById("finalWeight").value = finalWeight;
				getDomElementById("finalKgs").value = finalWeight.split(".")[0];
				getDomElementById("finalGms").value = finalWeight.split(".")[1];
				getDomElementById("bagLockNo").value = manifestHDetails.ppxTO.bagLockNo;
				getDomElementById("rfidNo").value = manifestHDetails.ppxTO.rfidNo;
				document.getElementById("operatingLevel").value = manifestHDetails.ppxTO.operatingLevel;

				showDropDownBySelected("manifestType",
						manifestHDetails.ppxTO.bplManifestType);
				if (!isNull(manifestHDetails.ppxTO.destRegionTO))
					showDropDownBySelected("destRegionId",
							manifestHDetails.ppxTO.destRegionTO.regionId);
				showDropDownBySelected("destOfficeType",
						manifestHDetails.ppxTO.destOfficeType);

				// this is done by preeti
				if (!isNull(manifestHDetails.ppxTO.destOfficeType)) {
					document.getElementById("destOfficeType").value = manifestHDetails.ppxTO.destOfficeType;
				}
				if (!isNull(manifestHDetails.ppxTO.destinationCityTO)) {
					var destCity = createDropDown("destCity",
							manifestHDetails.ppxTO.destinationCityTO.cityId,
							manifestHDetails.ppxTO.destinationCityTO.cityName);
				}
				if (manifestHDetails.ppxTO.isMulDestination == "Y") {
					var officeId = "0";
					var officeName = "ALL";
					createDropDown("destOffice", officeId, officeName);
				} else {
					createDropDown("destOffice",
							manifestHDetails.ppxTO.destinationOfficeId,
							manifestHDetails.ppxTO.destinationOfficeName);
				}
				if (!isNull(manifestHDetails.ppxTO.manifestProcessTo)) {
					getDomElementById("manifestProcessId").value = manifestHDetails.ppxTO.manifestProcessTo.manifestProcessId;
				}
				getDomElementById("regionId").value = manifestHDetails.ppxTO.regionId;
				getDomElementById("manifestId").value = manifestHDetails.ppxTO.manifestId;
				getDomElementById("loginOfficeId").value = manifestHDetails.ppxTO.loginOfficeId;
				getDomElementById("manifestStatus").value = manifestHDetails.ppxTO.manifestStatus;
				getDomElementById("outMnfstDestIds").value = manifestHDetails.ppxTO.outMnfstDestIds;

				// Set process Id
				getDomElementById("processId").value = manifestHDetails.ppxTO.processId;
				// Set consg type
				// getDomElementById("consignmentTypeId").value =
				// manifestHDetails.ppxTO.consignmentTypeTO.consignmentId;

				// diable header
				// if(manifestHDetails.ppxTO.manifestStatus == "C"){
				enableOrDisableHeader(true);
				// }

				// Setting grid details
				if (!isNull(detailTOJson)) {
					var manifestDetails = eval('(' + detailTOJson + ')');
					// populateGridDetails(manifestDetails, doxDetailTO);

					if (!isNull(manifestDetails.detailTO)) {
						var noofRows = manifestDetails.detailTO.length;
						for ( var i = 1; i <= noofRows; i++) {
							getDomElementById("consigntNo" + i).value = manifestDetails.detailTO[i - 1].consgNo;
							if (!isNull(manifestDetails.detailTO[i - 1].consgNo)) {
								scanedConsignment[scanedConsignment.length] = manifestDetails.detailTO[i - 1].consgNo;
							}
							// getDomElementById("consigntNo" +
							// i).disabled=true;
							/*
							 * if (manifestDetails.detailTO[i - 1].isPickupCN ==
							 * 'Y') { getDomElementById("status" + i).value =
							 * "P"; } else { getDomElementById("status" +
							 * i).value = "N"; }
							 */
							getDomElementById("isCN" + i).value = manifestDetails.detailTO[i - 1].isCN;
							getDomElementById("consgId" + i).value = manifestDetails.detailTO[i - 1].consgId;
							getDomElementById("bookingWeight" + i).value = manifestDetails.detailTO[i - 1].bkgWeight;
							getDomElementById("consgManifestedId" + i).value = manifestDetails.detailTO[i - 1].consgManifestedId;

							getDomElementById("isCNProcessedFromPickup" + i).value = manifestDetails.detailTO[i - 1].isPickupCN;
							getDomElementById("bookingId" + i).value = manifestDetails.detailTO[i - 1].bookingId;
							getDomElementById("bookingTypeId" + i).value = manifestDetails.detailTO[i - 1].bookingTypeId;
							getDomElementById("customerId" + i).value = manifestDetails.detailTO[i - 1].customerId;
							getDomElementById("consignorId" + i).value = manifestDetails.detailTO[i - 1].consignorId;
							getDomElementById("runsheetNo" + i).value = manifestDetails.detailTO[i - 1].runsheetNo;

							getDomElementById("bookingType" + i).value = manifestDetails.detailTO[i - 1].bookingType;
							getDomElementById("noOfPcs" + i).value = manifestDetails.detailTO[i - 1].noOfPcs;

							getDomElementById("childCns" + i).value = manifestDetails.detailTO[i - 1].childCn; // hidden

							/*
							 * Add Child CN in Global array. FOR child CN should
							 * not be allowd to scan.
							 */
							addChildCNInArray(manifestDetails.detailTO[i - 1].childCn);

							var actWeight = parseFloat(
									manifestDetails.detailTO[i - 1].actWeight)
									.toFixed(3);
							getDomElementById("actWeight" + i).value = actWeight;
							getDomElementById("actWeightKg" + i).value = actWeight
									.split(".")[0];
							getDomElementById("actWeightGm" + i).value = actWeight
									.split(".")[1];
							var volWeight = parseFloat(
									manifestDetails.detailTO[i - 1].volumetricWeight)
									.toFixed(3);
							getDomElementById("volWeight" + i).value = volWeight;
							getDomElementById("length" + i).value = manifestDetails.detailTO[i - 1].length;
							getDomElementById("breath" + i).value = manifestDetails.detailTO[i - 1].breadth;
							getDomElementById("height" + i).value = manifestDetails.detailTO[i - 1].height;
							getDomElementById("weightVW" + i).value = volWeight
									.split(".")[0];
							getDomElementById("weightGmVW" + i).value = volWeight
									.split(".")[1];
							var weight = parseFloat(
									manifestDetails.detailTO[i - 1].weight)
									.toFixed(3);
							getDomElementById("weight" + i).value = weight;
							getDomElementById("weightKg" + i).value = weight
									.split(".")[0];
							getDomElementById("weightGm" + i).value = weight
									.split(".")[1];
							getDomElementById("pincode" + i).value = manifestDetails.detailTO[i - 1].pincode;
							getDomElementById("pincodeId" + i).value = manifestDetails.detailTO[i - 1].pincodeId;
							getDomElementById("mobile" + i).value = manifestDetails.detailTO[i - 1].mobileNo;
							getDomElementById("consigneeId" + i).value = manifestDetails.detailTO[i - 1].consigneeId;

							if (!isNull(manifestDetails.detailTO[i - 1].cnContentId)) {
								getDomElementById("contentIds" + i).value = manifestDetails.detailTO[i - 1].cnContentId;
								getDomElementById("contentCode" + i).value = manifestDetails.detailTO[i - 1].cnContentCode;
								showDropDownBySelected(
										"contentName" + i,
										manifestDetails.detailTO[i - 1].cnContentId
												+ "#"
												+ manifestDetails.detailTO[i - 1].cnContentCode);
								if (!isNull(manifestDetails.detailTO[i - 1].otherCNContent)) {
									getDomElementById("cnContentOther" + i).value = manifestDetails.detailTO[i - 1].otherCNContent;
								}

							}

							getDomElementById("declaredValue" + i).value = parseFloat(
									manifestDetails.detailTO[i - 1].declaredValue)
									.toFixed(2);

							if (!isNull(response.cnPaperWorksTOList)) {
								getPaperWorksResponse(response.cnPaperWorksTOList);
							}

							if (!isNull(manifestDetails.detailTO[i - 1].paperWorkId)) {
								showDropDownBySelected(
										"cnPaperWorkselect" + i,
										manifestDetails.detailTO[i - 1].paperWorkId
												+ '#'
												+ manifestDetails.detailTO[i - 1].paperWorkCode);
								getDomElementById("cnPaperWorks" + i).value = manifestDetails.detailTO[i - 1].paperRefNum;
								getDomElementById("paperWorkId" + i).value = manifestDetails.detailTO[i - 1].paperWorkId;
							}

							getDomElementById("insuredBy" + i).value = manifestDetails.detailTO[i - 1].insuredById;
							getDomElementById("policyNo" + i).value = manifestDetails.detailTO[i - 1].policyNo;

							var cnSeries = manifestDetails.detailTO[i - 1].consgNo
									.substring(4, 5);
							if (cnSeries == "T") {
								getDomElementById("toPayAmt" + i).value = manifestDetails.detailTO[i - 1].toPayAmt;
								getDomElementById("codAmt" + i).value = parseFloat(
										manifestDetails.detailTO[i - 1].codAmt)
										.toFixed(2);
							} else if (cnSeries == "L") {
								getDomElementById("codAmt" + i).value = parseFloat(
										manifestDetails.detailTO[i - 1].codAmt)
										.toFixed(2);
							} else if (cnSeries == "D") {
								getDomElementById("codAmt" + i).value = parseFloat(
										manifestDetails.detailTO[i - 1].lcAmt)
										.toFixed(2);
							}
							getDomElementById("lcBankName" + i).value = manifestDetails.detailTO[i - 1].lcBankName;
							getDomElementById("custRefNo" + i).value = manifestDetails.detailTO[i - 1].custRefNo;
							// disable grid
							if (manifestHDetails.ppxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
								/*
								 * getDomElementById("consigntNo" + i).readOnly =
								 * true; getDomElementById("weightVW" +
								 * i).readOnly = true;
								 * getDomElementById("weightGmVW" + i).readOnly =
								 * true; getDomElementById("weightKg" +
								 * i).readOnly = true;
								 * getDomElementById("weightGm" + i).readOnly =
								 * true;
								 */
								disableOrEnableRowDetails(i);
							}
							eachConsgWeightArr[i] = parseFloat(getDomElementById("actWeight"
									+ i).value);
							// enableOrDisableGrid(i, true);
						}
						
						//Set focus to last row.
						if (manifestHDetails.ppxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
							var maxRowNo = manifestDetails.detailTO.length;
							var focusRow = parseInt(maxRowNo) +1;
							if (!isNaN(getDomElementById("consigntNo" + focusRow)) && !isNull(getDomElementById("consigntNo" + focusRow))){
								setTimeout(function() {
									document.getElementById("consigntNo" + focusRow).focus();
								}, 10);
							}
							
						}
						
					}
				}// manifestDetails.detailTO chk ends
			}// recent added
			else {
				// alert(doxTOJson.errorMsg);
				if (!isNull(doxTOJson)) {
					var responseText = null;
					try {
						responseText = jsonJqueryParser(doxTOJson);
					} catch (e) {

					}
					if (!isNull(responseText)) {
						var error = responseText.ppxTO.errorMsg;
						if (!isNull(error)) {
							alert(error);
						}
					}
				}

				// clearHeaderDtls();
				getDomElementById("manifestNo").value = "";
				for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
					clearRows(count);
				}
				getDomElementById("manifestNo").focus();
			}
		}
		if (manifestHDetails.ppxTO.manifestStatus == MANIFEST_STATUS_CLOSE) {
			disableForClose();
			for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
				enableOrDisableGrid(count, true);
			}
		}
		isSaved = true;
	}
	// jQuery.unblockUI();
	hideProcessing();
}

function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}
/**
 * clearHeaderDtls
 * 
 * @author shahnsha
 */
function clearHeaderDtls() {
	getDomElementById("manifestNo").value = "";
	getDomElementById("finalKgs").value = "";
	getDomElementById("finalGms").value = "";
	getDomElementById("bagLockNo").value = "";
	getDomElementById("rfidNo").value = "";

	createDropDown("manifestType", "", "SELECT");
	createDropDown("destRegionId", "", "SELECT");
	createDropDown("destCity", "", "SELECT");
	createDropDown("destOffice", "", "SELECT");
	createDropDown("destOfficeType", "", "SELECT");
}
/**
 * clearRows
 * 
 * @param count
 * @author shahnsha
 */
function clearRows(count) {
	// enable grid
	enableOrDisableGrid(count, false);
	removeScanConsignments(getDomElementById("consigntNo" + count).value);
	getDomElementById("consigntNo" + count).value = "";
	/* getDomElementById("status" + count).value = ""; */
	getDomElementById("noOfPcs" + count).value = "";
	getDomElementById("actWeightKg" + count).value = "";
	getDomElementById("actWeightGm" + count).value = "";
	getDomElementById("weightVW" + count).value = "";
	getDomElementById("weightGmVW" + count).value = "";
	getDomElementById("pincode" + count).value = "";
	getDomElementById("mobile" + count).value = "";
	getDomElementById("cnContentOther" + count).value = "";
	getDomElementById("contentCode" + count).value = "";
	getDomElementById("contentName" + count).value = "";
	getDomElementById("declaredValue" + count).value = "";
	getDomElementById("cnPaperWorkselect" + count).value = "";
	getDomElementById("cnPaperWorks" + count).value = "";
	getDomElementById("policyNo" + count).value = "";
	getDomElementById("insuredBy" + count).value = "";
	getDomElementById("toPayAmt" + count).value = "";
	getDomElementById("codAmt" + count).value = "";
	getDomElementById("custRefNo" + count).value = "";

	getDomElementById("consgId" + count).value = "";
	getDomElementById("bookingId" + count).value = "";
	getDomElementById("isCN" + count).value = "Y";
	getDomElementById("bookingType" + count).value = "";
	getDomElementById("actWeight" + count).value = "";
	getDomElementById("volWeight" + count).value = "";
	getDomElementById("length" + count).value = "";
	getDomElementById("breath" + count).value = "";
	getDomElementById("height" + count).value = "";
	getDomElementById("weight" + count).value = "";
	getDomElementById("pincodeId" + count).value = "";
	getDomElementById("consigneeId" + count).value = "";
	getDomElementById("paperWorkId" + count).value = "";
	getDomElementById("contentIds" + count).value = "";

	getDomElementById("weightKg" + count).value = "";
	getDomElementById("weightGm" + count).value = "";
	getDomElementById("bookingWeight" + count).value = "";
	getDomElementById("isDataMismatch" + count).value = "N";
	getDomElementById("isCNProcessedFromPickup" + count).value = "N";
	getDomElementById("consgManifestedId" + count).value = "";

}
/**
 * createDropDown
 * 
 * @param domId
 * @param value
 * @param desc
 * @returns {___domElement3}
 * @author shahnsha
 */
function createDropDown(domId, value, desc) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	// var text = document.createTextNode(desc);
	// optionSelectType.value = value;
	// optionSelectType.appendChild(text);
	// domElement.add(optionSelectType);

	optionSelectType.text = trimString(desc);
	optionSelectType.value = value;
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}
	return domElement;
}
// redirect to child CN page
/**
 * redirectToChildCNPage
 * 
 * @param rowId
 * @author shahnsha
 */
function redirectToChildCNPage(rowId) {
	if (!isNull(getDomElementById("consigntNo" + rowId).value)) {
		var noOfPcElement = getDomElementById('noOfPcs' + rowId);
		if (noOfPcElement.readOnly == false) {
			var pieces = noOfPcElement.value;
			var processCode = "MANIFEST";
			// PIECES_OLD = PIECES_NEW;
			PIECES_NEW = pieces;
			PIECES_OLD = PIECES_NEW;
			if (PIECES_NEW < PIECES_ACTUAL) {
				PIECES_OLD = 0;
			}
			getDomElementById("isDataMismatch" + rowId).value = "Y";
			if (!isNull(pieces) && pieces > 1) {
				usedConsignments = new Array();
				usedConsignments = scanedConsignment.concat(scanedAllchildCNs);
				getDomElementById("bookingType").value = getDomElementById("bookingType"
						+ rowId).value;

				url = "childCNPopup.do?&pieces=" + pieces + '&rowCount='
						+ rowId + '&processCode=' + processCode;
				window
						.open(
								url,
								'newWindow',
								'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');
				if (PIECES_NEW != PIECES_ACTUAL)
					getDomElementById("isCnUpdated" + rowId).value = "Y";
			}
		}
	}
}
/**
 * setContentValues
 * 
 * @param obj
 * @author shahnsha
 */
function setContentValues(obj) {
	var isValidContent = "";
	var rowCountName = getRowId(obj, "contentCode");
	ROW_COUNT = rowCountName;
	var contentValueArr = getDomElementById('contentName' + rowCountName);
	getDomElementById('contentIds' + rowCountName).value = "";
	for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var selectedCode = selectedVal.split("#")[1];
		var conetntId = selectedVal.split("#")[0];
		if (selectedCode == obj.value) {
			getDomElementById('contentIds' + rowCountName).value = conetntId;
			selectObj.selected = 'selected';
			isValidContent = "Y";
			break;
		} else {
			isValidContent = "N";
		}
	}
	if (isValidContent == "N") {
		getDomElementById('contentCode' + rowCountName).value = "";
		getDomElementById('contentName' + rowCountName).value = "";
		alert("Invalid content code.");
		setTimeout(function() {
			getDomElementById("contentCode").focus();
		}, 10);
		return false;
	}
	return true;
}
/**
 * setContentDtls
 * 
 * @param obj
 * @author shahnsha
 */
function setContentDtls(obj) {
	var rowCountName = getRowId(obj, "contentName");

	getDomElementById('contentCode' + rowCountName).value = "";
	getDomElementById('contentIds' + rowCountName).value = "";

	ROW_COUNT = rowCountName;
	var contentValue = getDomElementById('contentName' + rowCountName).value;
	if (contentValue != "" && contentValue != "" && contentValue != "O") {
		getDomElementById("cnContentOther" + rowCountName).value = "";
		getDomElementById("cnContentOther" + rowCountName).readOnly = true;
		var contentCode = contentValue.split("#")[1];
		var contentId = contentValue.split("#")[0];

		getDomElementById('contentIds' + rowCountName).value = contentId;
		getDomElementById('contentCode' + rowCountName).value = contentCode;
	}
	if (contentValue != "" && contentValue != "" && contentValue == "O") {
		getDomElementById("cnContentOther" + rowCountName).readOnly = false;
	}
}
/**
 * setPaperWorkDetails
 * 
 * @param obj
 * @author shahnsha
 */
function setPaperWorkDetails(obj) {
	var rowCountName = getRowId(obj, "cnPaperWorkselect");
	getDomElementById('cnPaperWorks' + rowCountName).value = "";
	getDomElementById('paperWorkId' + rowCountName).value = "";

	ROW_COUNT = rowCountName;
	var paperValue = document
			.getElementById('cnPaperWorkselect' + rowCountName).value;
	if (paperValue != "") {
		var paperId = paperValue.split("#")[0];

		getDomElementById('paperWorkId' + rowCountName).value = paperId;
	}
}
/**
 * @Desc check weight tolerance
 * @param rowId
 */
/**
 * maxWtCheck
 * 
 * @param rowId
 * @author shahnsha
 */
function maxWtCheck(rowId) {
	// var consgNo = getDomElementById("consgNo"+rowId).value;
	var maxWeightAllowed = getDomElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = getDomElementById("maxTolerenceAllowed").value;
	maxWeightAllowed = parseInt(maxWeightAllowed);
	maxTolerenceAllowed = parseInt(maxTolerenceAllowed);
	// added on 0609 by Ami for calculating tolerence

	maxAllowedWtWithTollrence = maxWeightAllowed
			+ (maxWeightAllowed * maxTolerenceAllowed / 100);

	isWeightExceeded(maxWeightAllowed);
	// if(isFirstRecExceedsWtLmt=="N"){
	if (!maxWeightToleranceCheck(rowId, MAX_CNS_ALLOWED, maxWeightAllowed,
			maxTolerenceAllowed)) {
		// clear row grid...
		clearRows(rowId);
		eachConsgWeightArr[rowId] = 0;
		setTimeout(function() {
			getDomElementById("consigntNo" + rowId).focus();
		}, 10);
		return false;
	} else {
		return true;
	}
	/*
	 * }else{ var close = getDomElementById("closeBtn"); setTimeout(function() {
	 * close.focus(); }, 20); }
	 */
}
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
	if (!isNull(document.getElementById("actWeightKg" + rowId)))
		weightKg = document.getElementById("actWeightKg" + rowId).value;
	else
		weightKg = document.getElementById("actWeight" + rowId).value;

	weightKg = (isNull(weightKg)) ? "0" : weightKg;
	var weightGM = document.getElementById("actWeightGm" + rowId).value;
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
		if (eachConsgWeightArr[j] != undefined) {
			finalWeight = parseFloat(finalWeight) * 1000
					+ parseFloat(eachConsgWeightArr[j]) * 1000;
			finalWeight /= 1000;
		}
	}
	document.getElementById("finalWeight").value = finalWeight;
	// Max wt with tollerence
	maxAllowedWtWithTollrence = maxWeightAllowed
			+ (maxWeightAllowed * maxTolerenceAllowed / 100);

	// For 1 Grid wt > 38.5
	if (eachConsgWeightArr[rowId] > maxAllowedWtWithTollrence && rowId == 1) {
		alert("Maximum weight limit exceeds.");
		// Check for all mandatory fields..
		// focusOnMandatoryFields(rowId);
		setTimeout(function() {
			document.getElementById("closeBtn").focus();
		}, 20);
		isCnWeightExceeded = true;
		return true;
		// For multiple grid items
	} else if (finalWeight > maxAllowedWtWithTollrence) {
		// Set Final Wt in Header
		finalWeight = parseFloat(finalWeight)
				- parseFloat(eachConsgWeightArr[rowId]);
		document.getElementById("finalWeight").value = finalWeight;
		alert("Maximum weight limit exceeds.");
		return false;
	} else if (finalWeight == maxAllowedWtWithTollrence) {
		// Set Final Wt in Header
		alert("Maximum weight limit reached.");
		// focusOnMandatoryFields(rowId);
		setTimeout(function() {
			document.getElementById("closeBtn").focus();
		}, 20);
		isCnWeightExceeded = true;
		return true;
	}
	setFinalWeight(finalWeight);
	return true;
}

function isWeightExceeded(maxWeightAllowed) {
	for ( var i = 1; i <= MAX_CNS_ALLOWED; i++) {
		var chargWt = getDomElementById("actWeight" + i).value;
		if (!isEmptyWeight(chargWt) && !isEmptyWeight(maxWeightAllowed)) {
			chargWt = parseFloat(chargWt);
			maxWeightAllowed = parseFloat(maxWeightAllowed);
			if (chargWt > maxWeightAllowed) {
				isFirstRecExceedsWtLmt = "Y";
				break;
			} else
				isFirstRecExceedsWtLmt = "N";
		}
	}
}
function setFocusForSingleCnWtExcess() {
	if (isFirstRecExceedsWtLmt == "Y") {
		alert("Even one consignment weight exceeds max limit need to Close manifest");
		var close = getDomElementById("closeBtn");
		setTimeout(function() {
			close.focus();
		}, 20);
		return;
	}
}
/**
 * printCallBackManifestDelete
 * 
 * @param data
 * @author shahnsha
 */
function printCallBackManifestDelete(data, action) {
	if (!isNull(data)) {
		if (data == "SUCCESS") {
			performSaveOrClose(action);
		} else if (data == "FAILURE") {
			alert("Exception occurred. Record could not be saved.! Try again.");
		}
	}
}
/**
 * printManifest
 * 
 * @author shahnsha
 */
function printManifest() {
	if (confirm("Do you want to Print?")) {
		var manifestStatus = getDomElementById("manifestStatus").value;
		if (manifestStatus == MANIFEST_STATUS_OPEN) {
			alert("Only closed manifest can be printed");
		} else {

			var manifestNo = getDomElementById("manifestNo").value;
			var loginOffceID = getDomElementById('loginOfficeId').value;
			if (!isNull(manifestNo)) {
				url = './outManifestParcel.do?submitName=printOutManifestParcelDtls&manifestNo='
						+ manifestNo + "&loginOfficeId=" + loginOffceID;
				var w = window
						.open(url, 'myPopUp',
								'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			}

			/*
			 * window.frames['iFrame'].focus(); window.frames['iFrame'].print();
			 */
		}
	}
}

/**
 * printCallBackManifestPrint
 * 
 * @param data
 * @author shahnsha
 */
function printCallBackManifestPrint(data) {
	var response = data;
	if (response != null && response == 'success') {
		alert('Manifest printed successfully');
	}
}

/**
 * setTotalFinalWeight
 * 
 * @param rowId
 * @author shahnsha
 */
function setTotalFinalWeight(rowId) {
	var finalWeight = 0;
	var actWeight = "";
	var actualWeight = 0;
	var bkgWeight = 0;
	bkgWeight = getDomElementById("bookingWeight" + rowId).value;
	if (!isEmptyWeight(bkgWeight))
		bkgWeight = parseFloat(bkgWeight);
	// Actual Weight
	var actWtKg = getDomElementById("actWeightKg" + rowId).value;
	var actWtGm = getDomElementById("actWeightGm" + rowId).value;
	if (isNull(actWtKg))
		actWtKg = "0";
	if (isNull(actWtGm))
		actWtGm = "0";
	actWeight = actWtKg + "." + actWtGm;
	actWeight = parseFloat(actWeight).toFixed(3);
	if (!isEmptyWeight(actWeight)) {
		getDomElementById("actWeight" + rowId).value = actWeight;
		getDomElementById("actWeightKg" + rowId).value = actWeight.split(".")[0];
		getDomElementById("actWeightGm" + rowId).value = actWeight.split(".")[1];
	}

	// chargeable Weight
	setCnWeight(rowId);
	for ( var cnt = 1; cnt <= MAX_CNS_ALLOWED; cnt++) {
		actualWeight = getDomElementById("actWeight" + cnt).value;
		if (!isEmptyWeight(actualWeight))
			actualWeight = parseFloat(actualWeight);
		// Incase of manual entry need to update the flag
		if (parseIntNumber(cnt) == parseIntNumber(rowId)) {
			if (actualWeight > bkgWeight) {
				getDomElementById("isDataMismatch" + rowId).value = "Y";
			}
		}
		if (!isEmptyWeight(actualWeight))
			finalWeight = parseFloat(finalWeight) + parseFloat(actualWeight);
	}
	if (!isEmptyWeight(finalWeight)) {
		finalWeight = parseFloat(finalWeight).toFixed(3);
	} else {
		finalWeight = "0.000";
	}
	getDomElementById("finalWeight").value = finalWeight;
	getDomElementById("finalKgs").value = finalWeight.split(".")[0];
	getDomElementById("finalGms").value = finalWeight.split(".")[1];
}
function setChargeableWeight(rowId) {
	var cnWeight = "";
	var volWeight = getDomElementById("volWeight" + rowId).value;
	var actWeight = getDomElementById("actWeight" + rowId).value;
	if (actWeight < volWeight)
		cnWeight = volWeight;
	else
		cnWeight = actWeight;
	if (!isEmptyWeight(cnWeight)) {
		cnWeight = parseFloat(cnWeight).toFixed(3);
		getDomElementById("weight" + rowId).value = cnWeight;
		getDomElementById("weightKg" + rowId).value = cnWeight.split(".")[0];
		getDomElementById("weightGm" + rowId).value = cnWeight.split(".")[1];
	}
}
/**
 * setCnWeight
 * 
 * @param rowId
 * @author shahnsha
 */
function setCnWeight(rowId) {
	var cnWeight = "";
	var volWeight = parseFloat(getDomElementById("volWeight" + rowId).value);
	var actWeight = parseFloat(getDomElementById("actWeight" + rowId).value);
	if (actWeight < volWeight)
		cnWeight = volWeight;
	else
		cnWeight = actWeight;
	if (!isNANCheckForDecimals(cnWeight)) {
		cnWeight = "0.000";
	}
	if (!isEmptyWeight(cnWeight)) {
		if (maxWtCheck(rowId)) {
			cnWeight = parseFloat(cnWeight).toFixed(3);
			getDomElementById("weight" + rowId).value = cnWeight;
			getDomElementById("weightKg" + rowId).value = cnWeight.split(".")[0];
			getDomElementById("weightGm" + rowId).value = cnWeight.split(".")[1];
		}
	}
}
/**
 * @Desc If all row is filled then CLOSE automatically.
 * @param maxRowAllowed
 * @returns {Boolean}
 */
function isAllRowInGridFilled(maxRowAllowed) {
	for ( var i = 1; i <= MAX_CNS_ALLOWED; i++) {
		if (isNull(getDomElementById("consigntNo" + i).value))
			return false;
	}
	return true;
}

/**
 * @Desc delete Consg Dtls from client side
 */
function deleteConsgDtlsClientSide() {
	var isDel = false;
	for ( var i = 1; i <= MAX_CNS_ALLOWED; i++) {
		var cnNo = getDomElementById("consigntNo" + i).value;
		if (getDomElementById("chk" + i).checked == true && !isNull(cnNo)) {
			decreaseWeight(i);
			// Clearing the fields
			getDomElementById("checkAll").checked = "";
			getDomElementById("chk" + i).checked = "";
			clearRows(i);// clear the row
			// enable row grid
			// enableOrDisableGrid(i, false);// enable rows for new consignments
			isSaved = false;
			isDeleted = true;
			isDel = true;
			removeScanConsignments(cnNo);
		}// End If
		if (isNull(cnNo)) {
			getDomElementById("checkAll").checked = "";
			getDomElementById("chk" + i).checked = false;
		}
	}// End For loop
	if (isDel)
		alert("Record(s) deleted successfully.");
	else
		alert("Please select a Non Empty row to delete.");
}
/**
 * To decrease the weight from final weight
 * 
 * @param rowId
 */
function decreaseWeight(rowId) {
	var subTotalWeight = 0.000;
	// before clearing grid details getweight
	var weightKg = getDomElementById("actWeightKg" + rowId).value;
	var weightGm = getDomElementById("actWeightGm" + rowId).value;
	/*
	 * actWeightKg actWeightGm var weightKg = getDomElementById("weightKg" +
	 * rowId).value; var weightGm = getDomElementById("weightGm" + rowId).value;
	 */
	var weightInFraction = ((weightKg) + (weightGm)) / 1000;

	// Header Total Weight
	var totalWeight = parseFloat(getDomElementById("finalWeight").value);
	subTotalWeight = totalWeight * 1000 - parseFloat(weightInFraction) * 1000;
	subTotalWeight /= 1000;
	if (isEmptyWeight(subTotalWeight)) {
		subTotalWeight = 0.000;
	}

	// Setting final wt after deduction in header and hidden Field
	getDomElementById("finalWeight").value = parseFloat(subTotalWeight);

	var finalWeightStr = subTotalWeight + "";
	weightKgValueFinal = finalWeightStr.split(".");
	getDomElementById("finalKgs").value = weightKgValueFinal[0];
	if (!isNull(weightKgValueFinal[1]))
		getDomElementById("finalGms").value = weightKgValueFinal[1].substring(
				0, 3);
	if (weightKgValueFinal[1] == undefined) {
		getDomElementById("finalGms").value = "000";
	} else if (weightKgValueFinal[1].length == 1) {
		getDomElementById("finalGms").value += "00";
	} else if (weightKgValueFinal[1].length == 2) {
		getDomElementById("finalGms").value += "0";
	}

	// setting the weight 0 in global array while deleting
	eachConsgWeightArr[rowId] = 0;
}

/**
 * @Desc Disable when manifestStatus "CLOSE"
 */
function disableForClose() {
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
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
	getDomElementById("checkAll").disabled = true;
	for ( var i = 1; i <= MAX_CNS_ALLOWED; i++) {
		getDomElementById("chk" + i).disabled = true;
	}
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("consigntNo" + rowId);
	return callEnterKey(e, focusId);
}
function callEnterManifest(e) {
	var manifestType = getDomElementById("manifestType").value;
	if (manifestType.match(MANIFEST_TYPE_TRANSHIPMENT)) {
		setTimeout(function() {
			return enterKeyNav("destOfficeType", e.keyCode);
		}, 20);
	}
	// if(manifestType.match("PURE")){
	else {
		return enterKeyNav("destOfficeType", e.keyCode);
	}
}
function callEnterKeyForManifestType(e) {
	var manifestType = getDomElementById("manifestType").value;
	var loginOffType = getDomElementById("loginOfficeType").value;
	if ((manifestType == MANIFEST_TYPE_TRANSHIPMENT)
			&& (loginOffType.toLowerCase() == OFF_TYPE_CODE_BRANCH
					.toLowerCase())) {
		return callEnterKey(e, document.getElementById('bagLockNo'));
	} else {
		return callEnterKey(e, document.getElementById('destRegionId'));
	}
}
/*
 * function fnManifestTypeChng(){ showDropDownBySelected("destRegionId","");
 * showDropDownBySelected("destCity","");
 * showDropDownBySelected("destOfficeType","");
 * showDropDownBySelected("destOffice","");
 * getDomElementById("destOfficeType").disabled=false; }
 */

/* @Desc:for setting the the office ids if multiple destination is selected */
function getAllOfficeIds(obj) {
	if (!isNull(!obj.value)) {
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

function capturedWeightForManifest(weight) {
	// wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weight);
	// wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	// wmCapturedWeight = parseFloat(weight).toFixed(3);
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	// alert('machine weight'+wmActualWeight);
	// newWMWt = wmActualWeight;
	var response = bookingDetail;
	if (response != null && response != "error") {
		// var response = eval('(' + bookingDetail + ')');

		newWMWt = getExcatWeight(response, response.actWeight, weight);

		if (isNull(newWMWt)) {
			alert("Booked Consignment can not accepted due to ZERO weight.");
			var cnsObj = getDomElementById("consigntNo" + ROW_COUNT);
			cnsObj.value = "";
			setTimeout(function() {
				cnsObj.focus();
			}, 10);
			hideProcessing();
			return false;
		}

		newWMWt = parseFloat(newWMWt).toFixed(3);

		if (isNull(destRegionVal))
			destRegionVal = getDomElementById("destRegionId").value;
		if (isNull(destCityVal))
			destCityVal = getDomElementById("destCity").value;
		if (isNull(destOfficeVal))
			destOfficeVal = getDomElementById("destOffice").value;
		if (isNull(manifestTypeVal))
			manifestTypeVal = getDomElementById("manifestType").value;
		if (isNull(destOfficeTypeVal))
			destOfficeTypeVal = getDomElementById("destOfficeType").value;

		// var actWeight = parseFloat(response.actWeight).toFixed(3);
		var actWeight = newWMWt;
		// if (!isNull(newWMWt) && parseFloat(newWMWt) > actWeight) {
		// getDomElementById("isDataMismatch" + rowId).value = "Y";
		// actWeight = newWMWt;
		// }
		getDomElementById("actWeight" + ROW_COUNT).value = actWeight;
		var volWeight = parseFloat(response.volumetricWeight).toFixed(3);
		getDomElementById("volWeight" + ROW_COUNT).value = volWeight;
		if (actWeight < volWeight)
			cnWeight = volWeight;
		else
			cnWeight = actWeight;
		cnWeight = parseFloat(cnWeight).toFixed(3);
		getDomElementById("weight" + ROW_COUNT).value = cnWeight;// hidden
		// field
		// split weight
		var wt = getDomElementById("weight" + ROW_COUNT).value;
		splitWeights(wt, "weightKg", "weightGm", ROW_COUNT);

		// if(maxWtCheck(ROW_COUNT)) {
		getDomElementById("consigntNo" + ROW_COUNT).value = response.consgNo;
		getDomElementById("consgId" + ROW_COUNT).value = response.consgId;

		// added in global array
		scanedConsignment[scanedConsignment.length] = response.consgNo;

		getDomElementById("isCNProcessedFromPickup" + ROW_COUNT).value = response.isPickupCN;
		getDomElementById("bookingId" + ROW_COUNT).value = response.bookingId;
		getDomElementById("bookingTypeId" + ROW_COUNT).value = response.bookingTypeId;
		getDomElementById("customerId" + ROW_COUNT).value = response.customerId;
		getDomElementById("consignorId" + ROW_COUNT).value = response.consignorId;
		getDomElementById("runsheetNo" + ROW_COUNT).value = response.runsheetNo;

		getDomElementById("bookingType" + ROW_COUNT).value = response.bookingType;
		getDomElementById("noOfPcs" + ROW_COUNT).value = (!isNull(response.noOfPcs)) ? response.noOfPcs
				: "";
		PIECES_OLD = getDomElementById("noOfPcs" + ROW_COUNT).value;
		PIECES_ACTUAL = getDomElementById("noOfPcs" + ROW_COUNT).value;

		// getDomElementById("childCns" + ROW_COUNT).value = response.childCn;
		getDomElementById("bookingWeight" + ROW_COUNT).value = response.bkgWeight;
		// var actWeight = parseFloat(response.actWeight).toFixed(3);
		// getDomElementById("oldWeights" + ROW_COUNT).value = actWeight;
		getDomElementById("oldWeights" + ROW_COUNT).value = parseFloat(
				response.actWeight).toFixed(3);
		// if (!isNull(newWMWt) && parseFloat(newWMWt) > actWeight) {
		// actWeight = newWMWt;
		// }
		getDomElementById("actWeight" + ROW_COUNT).value = actWeight;
		if (!isEmptyWeight(actWeight)) {
			getDomElementById("actWeightKg" + ROW_COUNT).value = actWeight
					.split(".")[0];
			getDomElementById("actWeightGm" + ROW_COUNT).value = actWeight
					.split(".")[1];
		}
		var volWeight = parseFloat(response.volumetricWeight).toFixed(3);
		getDomElementById("volWeight" + ROW_COUNT).value = volWeight;
		if (!isNull(response.length))
			getDomElementById("length" + ROW_COUNT).value = response.length;
		if (!isNull(response.breadth))
			getDomElementById("breath" + ROW_COUNT).value = response.breadth;
		if (!isNull(response.height))
			getDomElementById("height" + ROW_COUNT).value = response.height;
		getDomElementById("weightVW" + ROW_COUNT).value = volWeight.split(".")[0];
		getDomElementById("weightGmVW" + ROW_COUNT).value = volWeight
				.split(".")[1];

		// Ami commented refer out dox
		getDomElementById("pincode" + ROW_COUNT).value = response.pincode;
		getDomElementById("pincodeId" + ROW_COUNT).value = response.pincodeId;
		var nextRow = parseIntNumber(ROW_COUNT) + 1;
		if (response.isPickupCN == 'Y') {
			// getDomElementById("status" + ROW_COUNT).value = "P";

			getDomElementById("pincode" + ROW_COUNT).readOnly = false;
			getDomElementById("pincode" + ROW_COUNT).focus();
		} else if (nextRow <= maxCNsAllowed) {
			// getDomElementById("status" + ROW_COUNT).value = "N";
			getDomElementById("consigntNo" + nextRow).focus();
		} else
			document.getElementById("saveBtn").focus();

		if (response.mobileNo == "" || response.mobileNo == null) {
			getDomElementById("mobile" + ROW_COUNT).disabled = true;
		} else {
			getDomElementById("mobile" + ROW_COUNT).value = response.mobileNo;
			getDomElementById("consigneeId" + ROW_COUNT).value = response.consigneeId;
		}
		if (!isNull(response.cnContentId)) {
			getDomElementById("contentIds" + ROW_COUNT).value = response.cnContentId;
			getDomElementById("contentCode" + ROW_COUNT).value = response.cnContentCode;
			showDropDownBySelected("contentName" + ROW_COUNT,
					response.cnContentId + "#" + response.cnContentCode);
		}
		if (!isNull(response.otherCNContent)) {
			getDomElementById("cnContentOther" + ROW_COUNT).value = response.otherCNContent;
		}
		if (!isNull(response.declaredValue)) {
			getDomElementById("declaredValue" + ROW_COUNT).value = parseFloat(
					response.declaredValue).toFixed(2);
		}

		if (!isNull(response.cnPaperWorksTOList)) {
			getPaperWorksResponse(response.cnPaperWorksTOList);
		}

		if (!isNull(response.paperWorkId)) {
			showDropDownBySelected("cnPaperWorkselect" + ROW_COUNT,
					response.paperWorkId + '#' + response.paperWorkCode);
			getDomElementById("cnPaperWorks" + ROW_COUNT).value = response.paperRefNum;
			getDomElementById("paperWorkId" + ROW_COUNT).value = response.paperWorkId;
		}
		getDomElementById("insuredBy" + ROW_COUNT).value = response.insuredById;
		getDomElementById("policyNo" + ROW_COUNT).value = response.policyNo;
		var cnSeries = response.consgNo.substring(4, 5);
		if (cnSeries == "T") {
			getDomElementById("toPayAmt" + ROW_COUNT).value = (!isNull(response.toPayAmt)) ? parseFloat(
					response.toPayAmt).toFixed(2)
					: "";
			getDomElementById("codAmt" + ROW_COUNT).value = (!isNull(response.codAmt)) ? parseFloat(
					response.codAmt).toFixed(2)
					: "";
		} else if (cnSeries == "L") {
			getDomElementById("codAmt" + ROW_COUNT).value = (!isNull(response.codAmt)) ? parseFloat(
					response.codAmt).toFixed(2)
					: "";
		} else if (cnSeries == "D") {
			getDomElementById("codAmt" + ROW_COUNT).value = (!isNull(response.lcAmt)) ? parseFloat(
					response.lcAmt).toFixed(2)
					: "";
		}
		getDomElementById("lcBankName" + ROW_COUNT).value = response.lcBankName;

		getDomElementById("custRefNo" + ROW_COUNT).value = response.custRefNo;

		getDomElementById("childCns" + ROW_COUNT).value = response.childCn; // hidden
		/* Adding child CN in array */
		addChildCNInArray(response.childCn);

		setTotalFinalWeight(ROW_COUNT);
		// above the same code added by Ami
		var nextRow = parseIntNumber(ROW_COUNT) + 1;
		if (response.isPickupCN == 'Y') {
			getDomElementById("noOfPcs" + ROW_COUNT).focus();
		} else if (nextRow <= MAX_CNS_ALLOWED) {
			// getDomElementById("consigntNo" + nextRow).focus();
			disableOrEnableRowDetails(ROW_COUNT);
		} else {
			getDomElementById("saveBtn").focus();
		}
		isSaved = false;
	}
	// }
	jQuery.unblockUI();
}

function fnHeaderDataChanges(field) {
	var maxCNsAllowed = getDomElementById("maxCNsAllowed").value;
	var noofRows = parseIntNumber(maxCNsAllowed);
	var gridValueExists = false;
	var gridNumber = "";
	for ( var count = 1; count <= noofRows; count++) {
		if (!isNull(document.getElementById("consigntNo" + count)))
			gridNumber = document.getElementById("consigntNo" + count).value;
		if (!isNull(gridNumber)) {
			gridValueExists = true;
			break;
		}
	}
	if (gridValueExists) {
		if (confirm("Consignments already entered.\n\nDo you want to make the changes in header?")) {
			callMethodAfterChange(field);
			for ( var count = 1; count <= noofRows; count++) {
				clearRows(count);
			}
		} else {
			showDropDownBySelected('destRegionId', destRegionVal);
			showDropDownBySelected('destCity', destCityVal);
			showDropDownBySelected('destOffice', destOfficeVal);
			showDropDownBySelected('manifestType', manifestTypeVal);
			showDropDownBySelected('destOfficeType', destOfficeTypeVal);
		}
	} else {
		callMethodAfterChange(field);
	}
}

function callMethodAfterChange(field) {
	if (field == "MT") {
		getDestinationDtlsByBplManifestType();
	} else if (field == "R") {
		getCitiesByRegion();
	} else if (field == "C") {
		// getOfficeTypeByBplManifestType();
		getDomElementById("destOfficeType").disabled = false;
		getDomElementById("destOfficeType").value = "";
		getOfficesByCityAndoffType();
	} else if (field == "OT") {
		getOfficesByCityAndoffType();
	}
}

function fnEnterKeyCallOnCnNo(e, count) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var cnNo = document.getElementById('consigntNo' + count).value;
		if (!isNull(cnNo)) {
			var isCNFromPickup = getDomElementById("isCNProcessedFromPickup"
					+ count).value;
			if (isCNFromPickup == "Y") {
				getDomElementById("noOfPcs" + count).focus();
				return false;
			} else {
				var nextRow = parseIntNumber(count) + 1;
				if (!isNull(document.getElementById('consigntNo' + nextRow))) {
					return callEnterKey(e, document.getElementById('consigntNo'
							+ nextRow));
				}
				return false;
			}
		} else {
			alert("Please Enter Consignment Number");
			getDomElementById("consigntNo" + count).focus();
			return false;
		}
	}
}
/**
 * To Select All Check Box
 * 
 * @param maxRowsAllowed
 * @author hkansagr
 */
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
function isDataMismatched(domEleValue, rowId) {
	if (!isNull(domEleValue)) {
		getDomElementById("isDataMismatch" + rowId).value = "Y";
	}
}

function validatePincode(pinObj) {
	var rowId = getRowId(pinObj, "pincode");
	var consgNo = getDomElementById("consigntNo" + rowId).value;
	if (!isNull(consgNo) && isValidPincodeFormat(pinObj)) {
		var pincode = pinObj.value;
		var destOfficeId = document.getElementById("destOffice").value;
		var destCityId = document.getElementById("destCity").value;
		var officeType = getDomElementById("destOfficeType").value;
		var loginOfficeType = getDomElementById("loginOfficeType").value;
		var bplManifestType = getDomElementById("manifestType").value;
		ROW_COUNT = rowId;
		var consgNumber = document.getElementById("consigntNo" + ROW_COUNT).value;
		var consgSeries = consgNumber.substring(4, 5);
		var isPickupCN = getDomElementById("isCNProcessedFromPickup"
				+ ROW_COUNT).value;

		var manifestProcessCode = getDomElementById("processCode").value;

		// TRANSSHIPMENT Added by Himal
		var isChkTransCityPincodeServ = "N";
		if (bplManifestType == MANIFEST_TYPE_TRANSHIPMENT
				&& loginOfficeType == OFF_TYPE_CODE_HUB) {
			isChkTransCityPincodeServ = "Y";
		}

		showProcessing();
		url = './outManifestParcel.do?submitName=validatePincodeWithManifestType&pincode='
				+ pincode
				+ "&destOfficeId="
				+ destOfficeId
				+ "&destCityId="
				+ destCityId
				+ "&officeType="
				+ officeType
				+ "&bplManifestType="
				+ bplManifestType
				+ "&consgSeries="
				+ consgSeries
				+ "&isPickupCN="
				+ isPickupCN
				+ "&isChkTransCityPincodeServ="
				+ isChkTransCityPincodeServ
				+ "&manifestProcessCode="
				+ manifestProcessCode
				+ "&loginOfficeType=" + loginOfficeType;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req);
			}
		});
	}
}

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		refreshPage();
	}
}

function populateConsDtls(data, consgNumberObj) {
	// clearRows(ROW_COUNT);
	var ERROR_FLAG = "ERROR";
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
			var rowId = getRowId(consgNumberObj, "consigntNo");
			clearRows(rowId);
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
		} else {
			/** For wt integratn */
			bookingDetail = responseText;
			// To check whether weighing machine connected or not.
			// if (getDomElementById("isWMConnected").value == "Y") {// Y
			if (isWeighingMachineConnected) {
				getWtFromWMForOGM();
			} else {
				capturedWeightForManifest(-1);
			}
			// } else {// N
			// capturedWeightForManifest("0");
			// }
		}
	} else {
		hideProcessing();
		alert('No details found');
	}
}
function isBookingWeightReduced(rowId) {
	var bookingWeight = "";
	var currentMnfstCNWeight = "";
	bookingWeight = parseFloat(getValueByElementId("bookingWeight" + rowId))
			.toFixed(3);
	;
	currentMnfstCNWeight = parseFloat(getValueByElementId("actWeight" + rowId));
	if (currentMnfstCNWeight < bookingWeight) {
		alert("Booking weight could not be reduced");
		getDomElementById("actWeight" + rowId).value = bookingWeight;
		// bookingWeight = getValueByElementId("bookingWeight" + rowId);
		getDomElementById("actWeightKg" + rowId).value = bookingWeight
				.split(".")[0];
		getDomElementById("actWeightGm" + rowId).value = bookingWeight
				.split(".")[1];
		setTotalFinalWeight(rowId);
	}
}
function disableOrEnableRowDetails(count) {
	var noOfPcsDomEle = getDomElementById("noOfPcs" + count);
	var actWtDomEle = getDomElementById("actWeight" + count);
	var actWeightKgDomEle = getDomElementById("actWeightKg" + count);
	var actWeightGmDomEle = getDomElementById("actWeightGm" + count);
	var pincodeDomEle = getDomElementById("pincode" + count);
	var mobileDomEle = getDomElementById("mobile" + count);
	var cnContentOtherDomEle = getDomElementById("cnContentOther" + count);
	var contentCodeDomEle = getDomElementById("contentCode" + count);
	var contentNameDomEle = getDomElementById("contentName" + count);
	var declaredValueDomEle = getDomElementById("declaredValue" + count);
	var cnPaperWorkselectDomEle = getDomElementById("cnPaperWorkselect" + count);
	var cnPaperWorksDomEle = getDomElementById("cnPaperWorks" + count);
	var policyNoDomEle = getDomElementById("policyNo" + count);
	var insuredByDomEle = getDomElementById("insuredBy" + count);
	var toPayAmtDomEle = getDomElementById("toPayAmt" + count);
	var codAmtDomEle = getDomElementById("codAmt" + count);
	var custRefNoDomEle = getDomElementById("custRefNo" + count);
	var isPickupCN = getDomElementById("isCNProcessedFromPickup" + count).value;
	var lcBankName = getDomElementById("lcBankName" + count);

	if (!isNull(noOfPcsDomEle.value)) {
		noOfPcsDomEle.disabled = true;
	}
	if (!isNull(pincodeDomEle.value)) {
		pincodeDomEle.disabled = true;
	}
	if (!isNull(contentCodeDomEle.value) || !isNull(cnContentOtherDomEle.value)) {
		contentCodeDomEle.disabled = true;
		contentNameDomEle.disabled = true;
		cnContentOtherDomEle.disabled = true;
	}
	if (!isEmptyRate(declaredValueDomEle.value)) {
		declaredValueDomEle.disabled = true;
	}
	if (isPickupCN == "N") {
		noOfPcsDomEle.disabled = true;
		pincodeDomEle.disabled = true;
		contentCodeDomEle.disabled = true;
		contentNameDomEle.disabled = true;
		cnContentOtherDomEle.disabled = true;
		declaredValueDomEle.disabled = true;
		custRefNoDomEle.disabled = true;

		mobileDomEle.disabled = true;
		cnPaperWorksDomEle.disabled = true;
		cnPaperWorkselectDomEle.disabled = true;
		policyNoDomEle.disabled = true;
		insuredByDomEle.disabled = true;
		toPayAmtDomEle.disabled = true;
		codAmtDomEle.disabled = true;
		lcBankName.disabled = true;
		custRefNoDomEle.disabled = true;
		
		if (!isWeighingMachineConnected) {
			//if weighing machine is not connected to system Weight fields should be disabled  - Shahnaz
			fnEnableDisbleField("actWeightKg" + count, true);
			fnEnableDisbleField("actWeightGm" + count, true);
		}
	}
	if (isPickupCN == "Y") {
		noOfPcsDomEle.disabled = false;
		pincodeDomEle.disabled = false;
		contentCodeDomEle.disabled = false;
		contentNameDomEle.disabled = false;
		cnContentOtherDomEle.disabled = false;
		declaredValueDomEle.disabled = false;

		mobileDomEle.disabled = false;
		cnPaperWorksDomEle.disabled = false;
		cnPaperWorkselectDomEle.disabled = false;
		policyNoDomEle.disabled = false;
		insuredByDomEle.disabled = false;
		toPayAmtDomEle.disabled = false;
		codAmtDomEle.disabled = false;
		lcBankName.disabled = false;
		custRefNoDomEle.disabled = false;
		
		if (!isWeighingMachineConnected) {
			//Enable for pickup CNs if weighing scale not connected
			fnEnableDisbleField("actWeightKg" + count, false);
			fnEnableDisbleField("actWeightGm" + count, false);
		}
	}
}

/**
 * @param obj
 */
function validateNumberOfPcs(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(Obj.value)) {
			alert("Please enter no of Pieces");
			setTimeout(function() {
				Obj.focus();
			}, 10);
			return true;
		} else {
			return onlyNumberAndEnterKeyNav(e, Obj, focusId);
		}
	}

}

/**
 * @param obj
 */
function validateWeight(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "actWeightGm");
	var weightGm = getDomElementById("actWeightGm" + currentRowId).value;
	var weightKg = getDomElementById("actWeightKg" + currentRowId).value;
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(weightKg)
				&& (isNull(weightGm) || weightGm == "00" || weightGm == "000")) {
			alert("Please enter weight");
			getDomElementById("actWeightGm" + currentRowId).value = "";
			getDomElementById("actWeightKg" + currentRowId).value = "";
			setTimeout(function() {
				getDomElementById("actWeightKg" + currentRowId).focus();
			}, 10);
			return true;
		} else {
			return onlyNumberAndEnterKeyNav(e, Obj, focusId);
		}
	}
}

function compairChildCNWeightAndCNWeight(rowNo) {
	// Child Cns validation
	var noOfPcs = document.getElementById("noOfPcs" + rowNo).value;
	var chilsCNDtls = document.getElementById("childCns" + rowNo).value;

	// Actual Weight
	var actWeight = "";
	var actWtKg = getDomElementById("actWeightKg" + rowNo).value;
	var actWtGm = getDomElementById("actWeightGm" + rowNo).value;
	if (isNull(actWtKg))
		actWtKg = "0";
	if (isNull(actWtGm))
		actWtGm = "0";
	actWeight = actWtKg + "." + actWtGm;
	if (parseInt(noOfPcs) > 1 && !isNull(chilsCNDtls)) {
		validateParentAndChildCnsWeight(actWeight, chilsCNDtls, rowNo);
	}
}
/**
 * @param obj
 */
function validateManditoryPincode(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(Obj.value)) {
			alert("Please enter pincode");
			setTimeout(function() {
				Obj.focus();
			}, 10);
			return true;
		} else {
			return onlyNumberAndEnterKeyNav(e, Obj, focusId);
		}
	}
}

/**
 * @param obj
 */
function validateDeclareValue(e, Obj, focusId) {
	var charCode;
	var currentRowId = getRowId(Obj, "declaredValue");
	var insuredByDom = getDomElementById("insuredBy" + currentRowId);
	var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		getInsuarnceConfigDtls(Obj);
		getPaperWorks(currentRowId);
		if (isNull(Obj.value)) {
			alert("Please enter declared value");
			getDomElementById("declaredValue" + currentRowId).disabled = false;
			setTimeout(function() {
				Obj.focus();
			}, 10);
			return true;
		} else if (isNull(insuredByDom.value)) {
			getDomElementById("insuredBy" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("insuredBy" + currentRowId).focus();
			}, 20);
		} else if (IS_POLICY_MANDATORY == "Y"
				|| selectedText.toUpperCase() == "CONSIGNOR") {
			getDomElementById("policyNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNo" + currentRowId).focus();
			}, 20);
			return false;
		} else if (productCode == "T") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmt" + currentRowId).disabled = true;
			getDomElementById("toPayAmt" + currentRowId).disabled = true;
			getDomElementById("policyNo" + currentRowId).disabled = true;
			getDomElementById("custRefNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("custRefNo" + currentRowId).focus();
			}, 10);
			flag = false;
		}
	}
}
/**
 * @param obj
 */
function validateContents(e, obj, field, focusId, currentRowId) {
	var isOther = "N";
	var isValidCode = true;
	if (field == "O") {
		isOther = "Y";
	}
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57) && isOther == "N") {
		return false;
	}

	else if (charCode == 13) {
		if (field == "C") {
			isValidCode = setContentValues(obj);
		}
		var contentNameObj = getDomElementById("contentName" + currentRowId);
		var contentCode = getDomElementById("contentName" + currentRowId).value;
		var contentNameText = contentNameObj.options[contentNameObj.selectedIndex].text;

		if ((contentNameText.toUpperCase() == "OTHERS")
				&& isNull(getDomElementById("cnContentOther" + currentRowId).value)) {
			getDomElementById("cnContentOther" + currentRowId).readOnly = false;
			if (isOther == "Y") {
				alert("Please Enter Other Contents Description");
			}
			getDomElementById("cnContentOther" + currentRowId).focus();
			return false;
		} else if (isNull(contentCode) && field != "C") {
			getDomElementById("contentName" + currentRowId).disabled = false;
			alert("Please enter content description");
			setTimeout(function() {
				getDomElementById("contentName" + currentRowId).focus();
			}, 10);
			return false;
		} else if (isValidCode == true) {
			getDomElementById(focusId).disabled = false;
			getDomElementById(focusId).focus();
			return false;
		}
	}
}

/**
 * @param obj
 */
function validateInsuredBy(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "insuredBy");
	var decValue = getDomElementById("declaredValue" + currentRowId).value;
	var charCode;
	var insuredByDom = getDomElementById("insuredBy" + currentRowId);
	var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(insuredByDom.value)) {
			alert("Please select insured By.");
			getDomElementById("insuredBy" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("insuredBy" + currentRowId).focus();
			}, 10);
			return false;
		} else if (IS_POLICY_MANDATORY == "Y"
				|| selectedText.toUpperCase() == "CONSIGNOR") {
			getDomElementById("policyNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNo" + currentRowId).focus();
			}, 20);
			return false;
		} else if (productCode == "T") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmt" + currentRowId).disabled = true;
			getDomElementById("toPayAmt" + currentRowId).disabled = true;
			getDomElementById("policyNo" + currentRowId).disabled = true;
			getDomElementById("custRefNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("custRefNo" + currentRowId).focus();
			}, 10);
			flag = false;
		}
	}
}

/**
 * @param obj
 */
function validatePolicyNo(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "policyNo");
	var policyNo = getDomElementById("policyNo" + currentRowId).value;
	var insuredByDom = getDomElementById("insuredBy" + currentRowId);
	var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
	var charCode;
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(policyNo) && !isNull(selectedText)
				&& selectedText.toUpperCase() == "CONSIGNOR") {
			alert("Please enter policy number.");
			getDomElementById("policyNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNo" + currentRowId).focus();
			}, 10);
			return true;
			/*
			 * } else if (productCode == "T") { getDomElementById("toPayAmt" +
			 * currentRowId).disabled = false; setTimeout(function() {
			 * getDomElementById("toPayAmt" + currentRowId).focus(); }, 10);
			 * flag = false;
			 */
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmt" + currentRowId).disabled = true;
			getDomElementById("toPayAmt" + currentRowId).disabled = true;
			getDomElementById("policyNo" + currentRowId).disabled = true;
			getDomElementById("custRefNo" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("custRefNo" + currentRowId).focus();
			}, 10);
		}
	}
}

function checkedScanConsignments(cnNo) {
	var flag = true;
	for ( var i = 0; i < scanedConsignment.length; i++) {
		if (scanedConsignment[i] == cnNo) {
			flag = false;
			break;
		}
	}
	return flag;
}

// Remove from global
function removeScanConsignments(cnNo) {
	for ( var i = 0; i < scanedConsignment.length; i++) {
		if (scanedConsignment[i] == cnNo) {
			scanedConsignment[i] = null;
		}
	}
}

function validateToPayAmount(e, Obj) {
	var currentRowId = getRowId(Obj, "toPayAmt");
	var charCode;
	var toPayAmount = getDomElementById("toPayAmt" + currentRowId).value;
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(toPayAmount) && productCode == "T") {
			alert("Please Enter To Pay Amount.");
			getDomElementById("toPayAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("toPayAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (!isNull(toPayAmount) && productCode == "T") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmt" + currentRowId).disabled = true;
			getDomElementById("toPayAmt" + currentRowId).disabled = true;
			getDomElementById("policyNo" + currentRowId).disabled = true;
			var nextRow = parseIntNumber(currentRowId) + 1;
			return callEnterKey(e, document.getElementById('consigntNo'
					+ nextRow));
		}
	}
}

function validateCODAmount(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "codAmt");
	var charCode;
	var codOrLcAmt = getDomElementById("codAmt" + currentRowId).value;
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (!isNull(codOrLcAmt)) {
			codOrLcAmt = parseFloat(codOrLcAmt).toFixed(2);
		}
		if (isEmptyRate(codOrLcAmt)
				&& (productCode == "L" || productCode == "D")) {
			getDomElementById("codAmt" + currentRowId).disabled = false;
			alert("Please Enter COD/LC Amount.");
			getDomElementById("codAmt" + currentRowId).value = "";
			setTimeout(function() {
				getDomElementById("codAmt" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmt" + currentRowId).disabled = true;
			if (productCode == "T") {
				getDomElementById("codAmt" + currentRowId).disabled = false;
			}
			// getDomElementById("toPayAmt" + currentRowId).disabled = true;
			// getDomElementById("policyNo" + currentRowId).disabled = true;
			if (productCode == "L") {
				getDomElementById("codAmt" + currentRowId).disabled = false;
			}
			if (productCode == "D") {
				getDomElementById("codAmt" + currentRowId).disabled = false;
				getDomElementById("lcBankName" + currentRowId).disabled = false;
				setTimeout(function() {
					getDomElementById("lcBankName" + currentRowId).focus();
				}, 10);
			} else {
				getDomElementById(focusId).disabled = false;
				setTimeout(function() {
					getDomElementById(focusId).focus();
				}, 10);
			}

		}
	}
}

/**
 * @param childConsignment
 */
function addChildCNInArray(childConsignment) {
	if (!isNull(childConsignment)) {
		var childCN = childConsignment;
		var keyValList = childCN.split("#");
		for ( var i = 0; i < keyValList.length; i++) {
			var keyValPair = keyValList[i].split(",");
			if (!isNull(keyValPair[1])) {
				scanedAllchildCNs[scanedAllchildCNs.length] = keyValPair[0]
						.trim();
			}
		}
	}
}

/**
 * @param childCNId
 */
function updateChildCNNumber(childCNId) {
	var chCn = childCNId.value;
	if (!isNull(chCn)) {
		scanedAllchildCNs = new Array();
		for ( var count = 1; count <= MAX_CNS_ALLOWED; count++) {
			childcnNo = getDomElementById("childCns" + count).value;
			if (!isNull(childcnNo)) {
				addChildCNInArray(childcnNo);
			}
		}
	}
}

/**
 * @param cnNo
 * @returns {Boolean}
 */
function checkInScanChildConsignments(cnNo) {
	var flag = true;
	for ( var i = 0; i < scanedAllchildCNs.length; i++) {
		if (scanedAllchildCNs[i] == cnNo) {
			flag = false;
			break;
		}
	}
	return flag;
}

/**
 * To calculate CN rate
 * 
 * @param rowId
 */
function calculateCnRate(rowId) {
	var isPickupCN = getDomElementById("isCNProcessedFromPickup" + rowId).value;
	if (isPickupCN == "Y" && isValidRowForRateCalculation(rowId)) {
		var cnNo = getDomElementById("consigntNo" + rowId).value;
		var pincode = getDomElementById("pincode" + rowId).value;
		var weight = getDomElementById("weight" + rowId).value;
		var decValue = getDomElementById("declaredValue" + rowId).value;
		var insuredByObj = getDomElementById("insuredBy" + rowId);
		var insuredByText = insuredByObj.options[insuredByObj.selectedIndex].text;
		insuredByText = (insuredByText.toUpperCase() == CONSIGNOR) ? INSURED_BY_CONSIGNOR
				: INSURED_BY_FFCL;
		var codOrLcAmt = getDomElementById("codAmt" + rowId).value;
		var toPayAmount = getDomElementById("toPayAmt" + rowId).value;
		var officeId = getDomElementById("loginOfficeId").value;
		var cnType = CONSG_TYPE_PPX;
		RATE_ROW_COUNT = rowId;
		var url = "./outManifestParcel.do?submitName=calculateCnRate"
				+ "&consigntNo=" + cnNo + "&pincode=" + pincode + "&weight="
				+ weight + "&declaredValue=" + decValue + "&insuredBy="
				+ insuredByText + "&codAmt=" + codOrLcAmt + "&toPayAmt="
				+ toPayAmount + "&loginOfficeId=" + officeId + "&consgType="
				+ cnType;
		showProcessing();
		ajaxCallWithoutForm(url, callBackCalculateCnRate);
	}
}
// ajax call back function for calculateCnRate
function callBackCalculateCnRate(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = ajaxResp;
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = ajaxResp;
			var cnNo = getDomElementById("consigntNo" + RATE_ROW_COUNT).value;
			var cnSeries = cnNo.substring(4, 5);
			if (cnSeries == "T") {
				getDomElementById("toPayAmt" + RATE_ROW_COUNT).value = data.topayChg;
			}
		}
	}
	hideProcessing();
}

function validateParentAndChildCnsWeight(parentCnWeight, chilsCNDtls, rowCount) {
	var consgWeight = parseFloat(parentCnWeight).toFixed(3);
	if (!isNull(chilsCNDtls) && !isEmptyWeight(consgWeight)) {
		// var consgWeight = parseFloat(parentCnWeight);
		var childCns = new Array();
		var childCnsTotalWeight = 0.00;
		childCns = chilsCNDtls.split("#");
		for ( var i = 0; i < childCns.length; i++) {
			var childCn = childCns[i];
			var childCNWeight = childCn.split(",")[1];
			childCnsTotalWeight = childCnsTotalWeight
					+ parseFloat(childCNWeight);
		}
		childCnsTotalWeight = (childCnsTotalWeight * 100) / 100;
		childCnsTotalWeight = parseFloat(childCnsTotalWeight).toFixed(3);
		if (parseFloat(consgWeight) < parseFloat(childCnsTotalWeight)) {
			alert("Parent consignment weight can't be less than total of child consignments weight.("
					+ childCnsTotalWeight + ")");
			document.getElementById('actWeightGm' + rowCount).value = "";
			document.getElementById('actWeightKg' + rowCount).value = "";
			setTimeout(function() {
				document.getElementById("actWeightKg" + rowCount).focus();
			}, 10);
		}
	}
}

function setIFrame() {
	var manifestNo = getDomElementById("manifestNo").value;
	if (!isNull(manifestNo)) {
		var loginOffceID = getDomElementById('loginOfficeId').value;
		var url = './outManifestParcel.do?submitName=printOutManifestParcelDtls&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID;
		printUrl = url;
		printIframe(printUrl);
	}
}

function validatelcBankName(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "lcBankName");
	var charCode;
	var lcBankName = getDomElementById("lcBankName" + currentRowId).value;
	var cnNo = getDomElementById("consigntNo" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode == 13) {
		if (isNull(lcBankName) && (productCode == "D")) {
			alert("Please Enter Bank Name");
			getDomElementById("lcBankName" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("lcBankName" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById(focusId).disabled = false;
			setTimeout(function() {
				getDomElementById(focusId).focus();
			}, 10);
		}
	}

}