var deletionRowIds = null;
var ERROR_FLAG = "ERROR";
var isManifestSaved = false;
var isDeleted = false;

var scannedProduct = "";
var manifestedProductSeries = "";

var loginCity = "";
var loginRepHub = "";

var cnWeightFromWM = 0.000;

var destRegionVal = "";
var destCityVal = "";
var destOfficeVal = "";
var ogmManifestType = "";
var printUrl;

$(document).ready(function() {
	var oTable = $('#outManifstTable').dataTable({
		"sScrollY" : "230",
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

	addRows();
	getWeightFromWeighingMachine();
});
/**
 * addRows
 * 
 * @author shahnsha
 */
function addRows() {
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	closedManifestActions();
	for ( var cnt = 1; cnt <= maxCNsAllowed; cnt++) {
		$('#outManifstTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox" id="chk' + cnt
										+ '" name="chkBoxName" value="' + cnt
										+ '"/>',
								cnt,
								'<input type="text" class="txtbox" id="consigntNo'
										+ cnt
										+ '" name="to.consgNos" maxlength="12" onfocus="validateCnMandatoryFields(this);" onkeypress="fnEnterKeyCallOnCnNo(event,this);" onblur="fnValidateNumber(this,'
										+ cnt
										+ ');convertDOMObjValueToUpperCase(this);openPopups(this.value,\''
										+ cnt
										+ '\');" /><input type="hidden" id="consgId'
										+ cnt
										+ '" name="to.consgIds"/><input type="hidden" id="isCNProcessedFromPickup'
										+ cnt
										+ '" name="to.isCNProcessedFromPickup" value="N"/><input type="hidden" id="bookingId'
										+ cnt
										+ '" name="to.bookingIds"/><input type="hidden" id="bookingTypeId'
										+ cnt
										+ '" name="to.bookingTypeIds"/><input type="hidden" id="customerId'
										+ cnt
										+ '" name="to.customerIds"/><input type="hidden" id="runsheetNo'
										+ cnt
										+ '" name="to.runsheetNos"/><input type="hidden" id="consignorId'
										+ cnt
										+ '" name="to.consignorIds"/><input type="hidden" id="lcDetails'
										+ cnt
										+ '" name="to.lcDetails"/><input type="hidden" id="isDataMismatch'
										+ cnt
										+ '" name="to.isDataMismatched" value="N"/><input type="hidden" id="bookingWeight'
										+ cnt
										+ '" name="to.bookingWeights"/><input type="hidden" id="consgManifestedId'
										+ cnt
										+ '" name="to.consgManifestedIds"/><input type="hidden" id="oldWeights'
										+ cnt
										+ '" name="to.oldWeights" /><input type="hidden" id="prevPin'
										+ cnt
										+ '" name="prevPin" value="" />\
								<input type="hidden" id="status'
										+ cnt + '" name="to.status" />',
								'<input type="text" id="pincode'
										+ cnt
										+ '" name="to.pincodes" class="txtbox" maxlength="6" readonly="true" onchange="validatePincodeForOGM(this);isDataMismatched('
										+ cnt
										+ ');" onkeypress="return onlyNumberAndEnterKeyNav(event,this,\'weightGm'
										+ cnt
										+ '\');"/><input type="hidden" id="oldPincode'
										+ cnt
										+ '" name="oldPincode" value="" /><input type="hidden" id="pincodeId'
										+ cnt + '" name="to.pincodeIds"/>',
								'<input type="text" id="destCity'
										+ cnt
										+ '" name="to.destCitys" class="txtbox" readonly=true /><input type="hidden" id="destCityId'
										+ cnt + '" name="to.destCityIds"/>',
								'<input type="text" name="weight" id="weight'
										+ cnt
										+ '" class="txtbox width50"  size="11"  onkeypress="return onlyNumberAndEnterKeyNav(event,this,\'weightGm'
										+ cnt
										+ '\');" onchange="isDataMismatched('
										+ cnt
										+ ');" maxlength="5" /><span class="lable">.</span><input type="text" name="weightGm" id="weightGm'
										+ cnt
										+ '" class="txtbox width30"  size="11" maxlength="3" onblur="calculateCnRate('
										+ cnt
										+ ');isDataMismatched('
										+ cnt
										+ ');calculateFinalWeight('
										+ cnt
										+ ');" onkeypress="return checkComail(event,'
										+ cnt
										+ ');" /><input type="hidden" id="weights'
										+ cnt + '" name="to.weights" />',
								'<input type="text" id="mobile'
										+ cnt
										+ '" name="to.mobileNos" class="txtbox" maxlength="10" readonly="true" onkeypress="return checkComail(event,'
										+ cnt
										+ ');" onblur="validateMobile(this);" onchange="isDataMismatched('
										+ cnt
										+ ');"/><input type="hidden" id="consigneeId'
										+ cnt
										+ '" name="to.consigneeIds"/><input type="hidden" id="oldWtKg'
										+ cnt
										+ '" name="oldWtKg" value="" /><input type="hidden" id="oldWtGm'
										+ cnt
										+ '" name="oldWtGm" value="" /><input type="hidden" id="oldMob'
										+ cnt + '" name="oldMob" value="" />',

						]);
	}// end of for loop
	document.getElementById("manifestNo").focus();
	var rowCount = parseIntNumber(maxCNsAllowed);
	addComailRows(rowCount);// for co-mail
}

/**
 * addComailRows
 * 
 * @param cnt
 * @author shahnsha
 */
function addComailRows(cnt) {
	// FIXME:defect NO - 7 : status id was like id="status'+ (cnt) but cnt is
	// not the row no. Row no is (i + 1) - Fixed
	// Similarly fnValidateNumber() second param was cnt but need to be (i+1) -
	// shahnaz
	$('#outManifstTable').dataTable().fnAddData(
			[ 'Co-Mail', '&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;',
					'&nbsp;', ]);
	var maxCMailsAllowed = document.getElementById("maxComailsAllowed").value;
	var rows = parseIntNumber(maxCMailsAllowed) + parseIntNumber(cnt) - 1;
	for ( var i = cnt; i <= rows; i++) {
		$('#outManifstTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox" id="chk'
										+ (i + 1)
										+ '" name="chkBoxName" tabindex="-1" value="'
										+ (i + 1) + '"/>',
								(i + 1),
								'<input type="text" class="txtbox" id="comailNo'
										+ (i + 1)
										+ '" name="to.comailNos" value="" maxlength="12" onkeypress="fnEnterKeyCallOnCnNo(event,this);" onblur="fnValidateNumber(this,'
										+ (i + 1)
										+ ');convertDOMObjValueToUpperCase(this);"/><input type="hidden" id="comailId'
										+ (i + 1)
										+ '" name="to.comailIds"/><input type="hidden" id="comailManifestedId'
										+ (i + 1)
										+ '" name="to.comailManifestedIds"/>\
								<input type="hidden" id="status'
										+ (i + 1) + '" name="to.status"  />',
								'<input type="text" id="pincode'
										+ (i + 1)
										+ '" name="pincodes" class="txtbox" readonly="true" tabindex="-1" onkeypress="return callEnterKey(event, document.getElementById(\'destCity'
										+ (i + 1) + '\'));"/>',
								'<input type="text" id="destCity'
										+ (i + 1)
										+ '" name="destCitys" class="txtbox" readonly="true" tabindex="-1" onkeypress="return callEnterKey(event, document.getElementById(\'weightGm'
										+ (i + 1) + '\'));"/>',
								'<input type="text" name="weight" id="weight'
										+ (i + 1)
										+ '" class="txtbox width30" size="11" disabled="true"/><span class="lable">.</span><input type="text" name="weightGm" id="weightGm'
										+ (i + 1)
										+ '" class="txtbox width30" size="11" disabled="true" / onkeypress="return callEnterKey(event, document.getElementById(\'consigntNo'
										+ ((i + 1) + 1)
										+ '\')); "><input type="hidden" id="weights'
										+ (i + 1) + '" name="weights"/>',
								'<input type="text" id="mobile'
										+ (i + 1)
										+ '" name="mobileNos" class="txtbox" disabled="true" />', ]);

	}
}

function setFocus2WeightGrms(count) {
	setTimeout(function() {
		getDomElementById("weightGm" + count).focus();
	}, 10);
}

function validateWeightKg(e, obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		getDomElementById(focusId).value = "000";
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/**
 * checkOgmOrOpenMnfst
 * 
 * @param obj
 * @author shahnsha
 */
function checkOgmOrOpenMnfst(obj) {
	if (obj.checked) {
		document.getElementById("manifestOpenType").value = obj.value;
		if (!isNull(obj.value) && obj.value == "P") {
			var loginRegion = getDomElementById("regionId").value;
			loginCity = getDomElementById("loginCityId").value;
			loginRepHub = getDomElementById("loginRepHub").value;
			showDropDownBySelected("destRegionId", loginRegion);
			getDomElementById("destRegionId").value = loginRegion;
			getAllCities();
			enableDisabledForOpenManifest(true);
		} else {
			refreshPage();
		}
		getDomElementById("manifestNo").focus();
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
		if (getDomElementById("manifestOpenType").value == "P") {
			getAllOffices();
		}
	} else {
		showDropDownBySelected("destinationRegionId", "");
		alert("No cities found for the selected region");
		createDropDown("destCity", "", "SELECT");
		createDropDown("destOffice", "", "SELECT");
		document.getElementById('destinationRegionId').focus();
	}
	hideProcessing();
}

function printAllOffices(data) {
	if (!isNull(data)) {
		var loginOfficeType = getDomElementById("loginOfficeType").value;
		var officeToList = jsonJqueryParser(data);
		var error = officeToList[ERROR_FLAG];
		if (officeToList != null && error != null) {
			showDropDownBySelected("destCity", "");
			createDropDown("destOffice", "", "SELECT");
			alert(error);
			document.getElementById('destCity').focus();
			// hideProcessing();
		} else {
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
										var opmRadObj = getDomElementById("opmRad");
										if (!opmRadObj.checked) {
											addOptionTODropDown("destOffice",
													officeTO.officeName,
													officeTO.officeId);
											showDropDownBySelected(
													"destOffice", "0");
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
										showDropDownBySelected("destOffice",
												"0");
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
		}
	} else {
		showDropDownBySelected("destCity", "");
		createDropDown("destOffice", "", "SELECT");
		alert("No offices found for the selected city");
		document.getElementById('destCity').focus();
	}
	hideProcessing();
}

function allowedCNTest(consgNumberObj) {
	var flag = true;
	if (!isNull(scannedProduct)) {
		var officeType = document.getElementById("loginOfficeType").value;
		var rowCount = getRowId(consgNumberObj, "consigntNo");
		if (officeType == OFF_TYPE_CODE_HUB) {
			if (rowCount >= 2) {
				var currentScannedProd = consgNumberObj.value.substring(4, 5);
				if (scannedProduct == "C"
						&& currentScannedProd != scannedProduct) {
					alert("'C' series products cannot be mixed with other products");
					clearRows(rowCount);
					flag = false;
					setTimeout(function() {
						document.getElementById("consigntNo" + rowCount)
								.focus();
					}, 10);
				} else if (scannedProduct == "P"
						&& currentScannedProd != scannedProduct) {
					alert("'P' series products cannot be mixed with other products");
					clearRows(rowCount);
					flag = false;
					setTimeout(function() {
						document.getElementById("consigntNo" + rowCount)
								.focus();
					}, 10);
				} else if (scannedProduct != "P" && scannedProduct != "C") {
					if (currentScannedProd == "P" || currentScannedProd == "C") {
						alert("'C' or 'P' series products cannot be mixed with other products");
						clearRows(rowCount);
						flag = false;
						setTimeout(function() {
							document.getElementById("consigntNo" + rowCount)
									.focus();
						}, 10);
					}
				}
			}
		}
	}
	return flag;
}
/**
 * validateConsignment
 * 
 * @param consgNumberObj
 * @author shahnsha
 */
function validateConsignment(consgNumberObj) {
	if (!consgNumberObj.readOnly && isValidConsignment(consgNumberObj)
			&& allowedCNTest(consgNumberObj)
			&& isValidForScanCN(consgNumberObj)) {
		var destOfficeObj = document.getElementById("destOffice");
		var destCityObj = document.getElementById("destCity");
		var destOfficeId = "";
		var destCityId = "";
		var loginOfficeId = "";
		destOfficeId = destOfficeObj.value;
		destCityId = destCityObj.value;
		var manifestOpenType = document.getElementById("manifestOpenType").value;
		var loginOfficeType = document.getElementById("loginOfficeType").value;
		loginOfficeId = document.getElementById("loginOfficeId").value;
		scannedProduct = consgNumberObj.value.substring(4, 5).toUpperCase();
		var allowedConsgManifestedType = getDomElementById("allowedConsgManifestedType").value;
		var isPincodeServChk = getPincodeServChk();
		var manifestNo = document.getElementById("manifestNo").value;
		var isManifestNoCheckReq = "N";
		var manifestId = getDomElementById("manifestId").value;
		if (isNull(manifestId)) {
			isManifestNoCheckReq = "Y";
		}
		// FIXME: defect NO - 5 :uncommented the below line-shahnaz
		showProcessing();
		url = './outManifestDox.do?submitName=validateConsignmentNumber&consgNumber='
				+ consgNumberObj.value
				+ "&officeId="
				+ destOfficeId
				+ "&cityId="
				+ destCityId
				+ "&manifestOpenType="
				+ manifestOpenType
				+ "&officeType="
				+ loginOfficeType
				+ "&loginOfficeId="
				+ loginOfficeId
				+ "&allowedConsgManifestedType="
				+ allowedConsgManifestedType
				+ "&isPincodeServChk="
				+ isPincodeServChk
				+ "&manifestNo="
				+ manifestNo + "&isManifestNoCheckReq=" + isManifestNoCheckReq;
		jQuery.ajax({
			url : url,
			success : function(req) {
				jQuery.unblockUI();
				printCallBackConsignment(req, consgNumberObj);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert("Server error : " + errorThrown);
			}
		});
	}
}
function getPincodeServChk() {
	var manifestOpenType = getDomElementById("manifestOpenType").value;
	var isPincodeServChk = "";
	if (manifestOpenType == "O") {
		isPincodeServChk = "Y";
	} else {
		isPincodeServChk = "N";
	}
	return isPincodeServChk;
}
/**
 * printCallBackConsignment
 * 
 * @param data
 * @param consgNumberObj
 * @author shahnsha
 */
function printCallBackConsignment(data, consgNumberObj) {
	if (!isNull(data)) {
		var response = jsonJqueryParser(data);
		var error = response[ERROR_FLAG];
		ROW_COUNT = getRowId(consgNumberObj, "consigntNo");
		if (response != null && error != null) {
			alert(error);
			clearRows(ROW_COUNT);
			setTimeout(function() {
				document.getElementById("consigntNo" + ROW_COUNT).focus();
			}, 10);
			hideProcessing();
		} else {
			if (!isNull(response)) {
				if (response == "error") {
					alert("Error occured! Try again.");
					clearRows(ROW_COUNT);
					setTimeout(function() {
						document.getElementById("consigntNo" + ROW_COUNT)
								.focus();
					}, 10);
					hideProcessing();
				} else {
					var cnValidation = eval('(' + data + ')');
					if (cnValidation != null && cnValidation != "") {
						if (!isNull(cnValidation.errorMsg)) {
							alert(cnValidation.errorMsg);
							clearRows(ROW_COUNT);
							setTimeout(function() {
								consgNumberObj.focus();
							}, 10);
							hideProcessing();
						} else if (cnValidation.isConsInManifestd == "Y"
								&& cnValidation.isCNBooked == "N") {
							getInManifestConsDtls(consgNumberObj);
						} else {
							populateConsignmentDetails(data);
						}
					}
				}
			}
		}
	}
	// hideProcessing();
}

/* @Desc: Gets the inManifestconsignment details */
function getInManifestConsDtls(consObj) {
	var consNo = consObj.value;
	var consid = getRowId(consObj, "consigntNo");
	ROW_COUNT = consid;
	var url = "./outManifestDox.do?submitName=getInManifestedConsignmentDetails&consignmentNo="
			+ consNo;
	ajaxCallWithoutForm(url, populateInManifestConsignmentDetails);
}

/**
 * populateConsignmentDetails
 * 
 * @param data
 * @author shahnsha
 */
function populateConsignmentDetails(data) {
	var response = "";
	response = data;

	if (response != null && response != "error") {
		showProcessing();
		response = eval('(' + data + ')');
		// response = jsonJqueryParser(data);
		bookingDetail = response;
		// To check whether weighing machine connected or not.
		// if (getDomElementById("isWMConnected").value == "Y") {// Y
		if (isWeighingMachineConnected) {
			getWtFromWMForOGM();
		} else {
			//if weighing machine is not connected to system Weight fields should be disabled  - Shahnaz
			fnEnableDisbleField('weight'+ROW_COUNT, true);
			fnEnableDisbleField('weightGm'+ROW_COUNT, true);
			capturedWeightForManifest(-1);
		}
		// } else {// N
		// capturedWeightForManifest("0");
		// }
	}
}

/**
 * populateInManifestConsignmentDetails
 * 
 * @param data
 * @author shahnsha
 */

function populateInManifestConsignmentDetails(data) {
	var response = "";
	response = data;
	var responseText = jsonJqueryParser(data);
	var error = responseText[ERROR_FLAG];
	if (responseText != null && error != null) {
		alert(error);
	} else if (response != null && response != "error") {
		showProcessing();
		bookingDetail = response;
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
}

function capturedWeightForManifest(weigth) {
	// wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	// wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	// wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// newWMWt = getExcatWeight(response, weigth);

	var response = bookingDetail;
	// if(!isNull(response) && (!isEmptyWeight(newWMWt) ||
	// !isEmptyWeight(response.weight))){
	if (!isNull(response)) {

		newWMWt = getExcatWeight(response, response.weight, weigth);

		if (isNull(newWMWt)) {
			var cnsObj = document.getElementById("consigntNo" + ROW_COUNT);
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
		if (isNull(ogmManifestType)
				&& !isNull(getDomElementById("ogmManifestType")))
			ogmManifestType = getDomElementById("ogmManifestType").value;

		document.getElementById("consigntNo" + ROW_COUNT).value = response.consgNo;
		getDomElementById("isCNProcessedFromPickup" + ROW_COUNT).value = response.isPickupCN;
		document.getElementById("consgId" + ROW_COUNT).value = response.consgId;
		document.getElementById("bookingId" + ROW_COUNT).value = response.bookingId;
		document.getElementById("bookingTypeId" + ROW_COUNT).value = response.bookingTypeId;
		document.getElementById("bookingWeight" + ROW_COUNT).value = response.bkgWeight;
		document.getElementById("customerId" + ROW_COUNT).value = response.customerId;
		document.getElementById("consignorId" + ROW_COUNT).value = response.consignorId;
		document.getElementById("lcDetails" + ROW_COUNT).value = response.lcDtls;
		document.getElementById("runsheetNo" + ROW_COUNT).value = response.runsheetNo;
		document.getElementById("pincode" + ROW_COUNT).value = response.pincode;
		document.getElementById("oldPincode" + ROW_COUNT).value = response.pincode; // old
		// pincode
		// value
		document.getElementById("pincodeId" + ROW_COUNT).value = response.pincodeId;
		document.getElementById("destCity" + ROW_COUNT).value = response.destCity;
		document.getElementById("destCityId" + ROW_COUNT).value = response.destCityId;

		// weighing Machine Integration
		// var weight = parseFloat(response.weight).toFixed(3);
		var weight = newWMWt;
		getDomElementById("oldWeights" + ROW_COUNT).value = weight;
		// if (!isNull(response.weight)) {
		if (!isNull(weight)) {
			// if (!isNull(newWMWt) && parseFloat(newWMWt) > weight) {
			// weight = newWMWt;
			// getDomElementById("isDataMismatch" + ROW_COUNT).value = "Y";
			// }
			jQuery("#weights" + ROW_COUNT).val(weight);
			document.getElementById("weight" + ROW_COUNT).value = weight
					.split(".")[0];
			document.getElementById("weightGm" + ROW_COUNT).value = weight
					.split(".")[1];
		}
		document.getElementById("oldWtKg" + ROW_COUNT).value = weight
				.split(".")[0];
		document.getElementById("oldWtGm" + ROW_COUNT).value = weight
				.split(".")[1];

		document.getElementById("mobile" + ROW_COUNT).value = "";
		if (response.mobileNo == "" || response.mobileNo == null) {
			document.getElementById("mobile" + ROW_COUNT).disabled = true;
		} else {
			document.getElementById("mobile" + ROW_COUNT).value = response.mobileNo;
			// old mobile value
			document.getElementById("oldMob" + ROW_COUNT).value = response.mobileNo;
			document.getElementById("mobile" + ROW_COUNT).disabled = true;
			document.getElementById("consigneeId" + ROW_COUNT).value = response.consigneeId;
		}

		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		calculateFinalWeight(ROW_COUNT);
		var nextRow = parseIntNumber(ROW_COUNT) + 1;
		if (response.isPickupCN == 'Y') {
			getDomElementById("status" + ROW_COUNT).value = "P";
			getDomElementById("pincode" + ROW_COUNT).readOnly = false;
			getDomElementById("pincode" + ROW_COUNT).focus();

			// Clear weight fields
			if (newWMWt == "0.000") {
				document.getElementById("weights" + ROW_COUNT).value = "";
				document.getElementById("weight" + ROW_COUNT).value = "";
				document.getElementById("weightGm" + ROW_COUNT).value = "";
			}
			if (!isWeighingMachineConnected) {
				//Enable for pickup CNs if weighing scale not connected
				fnEnableDisbleField('weight'+ROW_COUNT, false);
				fnEnableDisbleField('weightGm'+ROW_COUNT, false);				
			}
		} else if (nextRow <= maxCNsAllowed) {
			getDomElementById("status" + ROW_COUNT).value = "N";
			getDomElementById("consigntNo" + nextRow).focus();
		} else
			document.getElementById("saveBtn").focus();
		// show LC/COD Amount popup for D,L,T Series
		var consgNumber = response.consgNo;
		var consgSeries = consgNumber.substring(4, 5);

		openPopups(consgNumber, ROW_COUNT);

		/*
		 * if (consgSeries == "D") { openLCPopup(ROW_COUNT); } else if
		 * (consgSeries == "L" || consgSeries == "T") { openCODPopup(ROW_COUNT); }
		 */

		if (consgSeries == "P")
			manifestedProductSeries = "P";
		else if (consgSeries == "C")
			manifestedProductSeries = "C";

		getDomElementById("manifestedProductSeries").value = manifestedProductSeries;
		// }
	} else {
		alert('Invalid Consignment');
		clearRows(ROW_COUNT);
	}
	hideProcessing();
}

function calculateFinalWeight(ROW_COUNT) {
	var finalWeight = 0;
	var weight = 0;
	var bkgWeight = 0;
	// Grid Weight
	bkgWeight = getDomElementById("bookingWeight" + ROW_COUNT).value;
	var weightKg = getDomElementById("weight" + ROW_COUNT).value;
	var weightGm = getDomElementById("weightGm" + ROW_COUNT).value;
	if (isNull(weightKg))
		weightKg = "0";
	if (isNull(weightGm))
		weightGm = "0";
	weight = weightKg + "." + weightGm;
	weight = parseFloat(weight).toFixed(3);
	getDomElementById("weights" + ROW_COUNT).value = weight;

	if (!isEmptyWeight(weight)) {
		getDomElementById("weight" + ROW_COUNT).value = weight.split(".")[0];
		getDomElementById("weightGm" + ROW_COUNT).value = weight.split(".")[1];
		bkgWeight = parseFloat(bkgWeight).toFixed(3);
		if (weight > bkgWeight) {
			getDomElementById("isDataMismatch" + ROW_COUNT).value = "Y";
		}
	}
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	for ( var cnt = 1; cnt <= maxCNsAllowed; cnt++) {
		var cnWeight = getDomElementById("weights" + cnt).value;
		if (!isEmptyWeight(cnWeight))
			finalWeight = parseFloat(finalWeight) + parseFloat(cnWeight);
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
/**
 * splitFinalWeight
 * 
 * @param finalWeight
 * @author shahnsha
 */
// Un used
/*
 * function splitFinalWeight(finalWeight) { var weightInKg = new Array(); var
 * weightGm = null; if (!isNull(finalWeight)) { weightInKg =
 * finalWeight.split("."); document.getElementById("finalKgs").value =
 * weightInKg[0]; weightGm = weightInKg[1];
 * 
 * if (isNull(weightGm)) { weightGm = "00";
 * document.getElementById("finalGms").value = weightGm; } else {
 * document.getElementById("finalGms").value = weightGm; } } }
 */

/**
 * printManifest
 * 
 * @author shahnsha
 */
function printManifest() {
	if (confirm("Do you want to Print?")) {
		var manifestStatus = document.getElementById("manifestStatus").value;
		if (manifestStatus == MANIFEST_STATUS_OPEN) {
			alert("Only closed manifest can be printed");
		} else {

			var manifestNo = document.getElementById("manifestNo").value;
			if (!isNull(manifestNo)) {
				var loginOffceID = document.getElementById('loginOfficeId').value;
				var url = './outManifestDox.do?submitName=printOutManifestDoxDtls&manifestNo='
						+ manifestNo + "&loginOfficeId=" + loginOffceID;
				window
						.open(url, 'myPopUp',
								'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
				/*
				 * document.outManifestDoxForm.action = url;
				 * document.outManifestDoxForm.submit();
				 * window.frames['iFrame'].focus();
				 * window.frames['iFrame'].print();
				 */
			}
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
 * validateHeader
 * 
 * @returns {Boolean}
 * @author shahnsha
 */
function validateHeader() {
	var flag = true;
	var manifestNo = document.getElementById("manifestNo").value;
	var destRegion = document.getElementById("destRegionId").value;
	var destCity = document.getElementById("destCity").value;
	var destOffice = document.getElementById("destOffice").value;
	// UAT Fix
	var errorMsg = "Please provide : ";
	if (isNull(manifestNo)) {
		// alert("Please enter manifest no");
		flag = false;
		errorMsg = errorMsg + "Manifest no";
		document.getElementById("manifestNo").focus();
	}
	if (isNull(destRegion)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination Region";
		// alert("Please enter destination region");
		document.getElementById("destRegionId").focus();
	}
	if (isNull(destCity)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination City";
		// alert("Please enter destination city");
		document.getElementById("destCity").focus();
	}
	if (destOffice == undefined || destOffice == null || destOffice == ""
			|| destOffice == "null" || destOffice == " ") {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Destination Office";
		// alert("Please enter destination office");
		document.getElementById("destOffice").focus();
	}
	if (!flag) {
		alert(errorMsg);
	}
	return flag;
}
/**
 * validateCnMandatoryFields
 * 
 * @param obj
 * @returns {Boolean}
 * @author shahnsha
 */
function validateCnMandatoryFields(obj) {
	var flag = true;
	var count = getRowId(obj, "consigntNo");
	if (count == 1) {
		var coMailOnly = "";
		coMailOnly = document.getElementById("coMailOnly").value;
		if (coMailOnly == "N") {
			var destCity = document.getElementById("destCity").value;
			var destOffice = document.getElementById("destOffice").value;
			if (isNull(destCity)) {
				flag = false;
				alert("Please enter destination city");
				document.getElementById("destCity").focus();
			} else if (destOffice == undefined && destOffice == null
					&& destOffice == "" && destOffice == "null"
					&& destOffice == " ") {
				flag = false;
				alert("Please enter destination office");
				document.getElementById("destOffice").focus();
			}
		}
	} else {
		var coMailOnlyDomEle = document.getElementById("coMailOnly");
		if (!coMailOnlyDomEle.checked)
			flag = validateCnRow(count - 1);
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
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	var maxCMailsAllowed = document.getElementById("maxComailsAllowed").value;
	var coMailOnlyDomEle = document.getElementById("coMailOnly");
	if (coMailOnlyDomEle.checked) {
		var maxRows = parseIntNumber(maxCNsAllowed)
				+ parseIntNumber(maxCMailsAllowed) - 1;
		for ( var count = 1; count <= maxRows; count++) {
			var cmailNo = "";
			if (!isNull(document.getElementById("consigntNo" + count)))
				cmailNo = document.getElementById("consigntNo" + count).value;
			else if (!isNull(document.getElementById("comailNo" + count)))
				cmailNo = document.getElementById("comailNo" + count).value;
			if (!isNull(cmailNo)) {
				isAtleastOneRecEntered = true;
			}
		}
	} else {
		for ( var count = 1; count <= maxCNsAllowed; count++) {
			var consgNo = getDomElementById("consigntNo" + count).value;
			if (!isNull(consgNo)) {
				var pincode = "";
				var destCity = "";
				var weight = "";
				var weightGm = "";
				var weightInFraction = "";
				var isPickupCN = document
						.getElementById("isCNProcessedFromPickup" + count).value;
				var dSeries = consgNo.substring(4, 5);
				isAtleastOneRecEntered = true;
				pincode = document.getElementById("pincode" + count).value;
				destCity = document.getElementById("destCity" + count).value;
				weight = document.getElementById("weight" + count).value;
				weightGm = document.getElementById("weightGm" + count).value;
				weightInFraction = parseFloat(((weight) + (weightGm)) / 1000);
				if (isNull(pincode)) {
					flag = false;
					alert("Please enter pincode");
					document.getElementById("pincode" + count).focus();
				} else if (isNull(destCity)) {
					flag = false;
					alert("Please enter destination");
					document.getElementById("pincode" + count).focus();
				} else if (isNull(weightInFraction)) {
					flag = false;
					alert("Please enter weight");
					document.getElementById("weightGm" + count).focus();
				} else if (isPickupCN == "Y"
						&& (dSeries == "d" || dSeries == "D")) {
					var lcDtls = getDomElementById("lcDetails" + count).value;
					var atLine = " at Line : " + count;
					if (!isNull(lcDtls)) {
						var lcDetails = lcDtls.split("#");
						var lcAmount = lcDetails[0];
						var lcBank = lcDetails[1];
						if (!isNull(lcAmount)) {
							lcAmount = parseFloat(lcAmount).toFixed(2);
						}
						if (isEmptyRate(lcAmount)) {
							alert("Please provide LC Amount" + atLine);
							flag = false;
							openLCPopup(count);
						} else if (isNull(lcBank)) {
							alert("Please provide LC Bank Name" + atLine);
							flag = false;
							openLCPopup(count);
						}
					} else {
						alert("Please provide LC details" + atLine);
						flag = false;
						openLCPopup(count);
					}
				} else if (isPickupCN == "Y"
						&& (dSeries == "l" || dSeries == "L")) {
					var lcDtls = getDomElementById("lcDetails" + count).value;
					var atLine = " at Line : " + count;
					if (!isNull(lcDtls)) {
						var lcDetails = lcDtls.split("#");
						var codAmt = lcDetails[0];
						if (!isNull(codAmt)) {
							codAmt = parseFloat(codAmt).toFixed(2);
						}
						if (isEmptyRate(codAmt)) {
							alert("Please provide COD Amount" + atLine);
							flag = false;
							openCODPopup(count);
						}
					} else {
						alert("Please provide COD Amount" + atLine);
						flag = false;
						openCODPopup(count);
					}
				}

			}
		}
		for ( var count = (parseIntNumber(maxCNsAllowed) + 1); count <= maxCMailsAllowed; count++) {
			var cmailNo = "";
			cmailNo = document.getElementById("comailNo" + count).value;
			if (isNull(cmailNo)) {
				flag = false;
				alert("Please enter comail number");
				document.getElementById("consigntNo" + count).focus();
			} else {
				isAtleastOneRecEntered = true;
			}
		}
	}
	if (!isAtleastOneRecEntered) {
		flag = false;
		alert("Atleast one consignment/comail should be there in a packet");
		document.getElementById("consigntNo1").focus();
	}
	var coMailOnlyObj = document.getElementById("coMailOnly");
	var finalWeight = document.getElementById("finalWeight").value;
	if (isNull(finalWeight) && flag) {
		if (!coMailOnlyObj.checked) {
			flag = false;
			alert("Please enter Final Weight");
		}
	}

	return flag;
}
function validateCnRow(count) {
	var flag = true;
	var consgNo = getDomElementById("consigntNo" + count).value;
	if (!isNull(consgNo)) {
		var pincode = "";
		var destCity = "";
		var weight = "";
		var weightGm = "";
		var weightInFraction = "";
		isAtleastOneRecEntered = true;
		pincode = document.getElementById("pincode" + count).value;
		destCity = document.getElementById("destCity" + count).value;
		weight = document.getElementById("weight" + count).value;
		weightGm = document.getElementById("weightGm" + count).value;
		weightInFraction = parseFloat(((weight) + (weightGm)) / 1000);
		if (isNull(pincode)) {
			flag = false;
			alert("Please enter pincode");
			setTimeout(function() {
				document.getElementById("pincode" + count).focus();
			}, 10);
		} else if (isNull(destCity)) {
			flag = false;
			alert("Please enter destination");
			setTimeout(function() {
				document.getElementById("pincode" + count).focus();
			}, 10);
		} else if (isNull(weightInFraction)) {
			flag = false;
			alert("Please enter weight");
			setTimeout(function() {
				document.getElementById("weightGm" + count).focus();
			}, 10);
		}
	}
	return flag;
}
/**
 * searchManifest
 * 
 * @author shahnsha
 */
function searchManifest() {
	var manifestNo = document.getElementById("manifestNo").value;
	if (!isNull(manifestNo)) {
		// setIFrame();
		var loginOffceID = document.getElementById('loginOfficeId').value;
		url = './outManifestDox.do?submitName=searchManifestDetails&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestDetails(req);
			}
		});
	} else {
		alert("Please provide manifest number.");
		var manifestNumObj = getDomElementById("manifestNo");
		manifestNumObj.value = "";
		setTimeout(function() {
			manifestNumObj.focus();
		}, 10);
	}
}
/**
 * printCallBackManifestDetails
 * 
 * @param data
 * @author shahnsha
 */
function printCallBackManifestDetails(data) {
	var response = data;
	var doxToJsonArray = new Array();
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	if (!isNull(response)) {
		doxToJsonArray = response.split("~");
		doxTOJson = doxToJsonArray[1];
		detailTOJson = doxToJsonArray[2];
		var manifestDetails = "";
		if (!isNull(doxTOJson)) {
			manifestDetails = eval('(' + doxTOJson + ')');
			for ( var count = 1; count <= maxCNsAllowed; count++) {
				disableOrEnableGrid(count, false);
				clearRows(count);
			}
			if (!isNull(manifestDetails.doxTO.errorMsg)) {
				alert(manifestDetails.doxTO.errorMsg);
				// FIXME: defect NO - 12 :added below line to clear the header
				// details-shahnaz
				var manifestOpenType = document
						.getElementById("manifestOpenType").value;
				if (manifestOpenType == 'P') {
					enableDisabledForOpenManifest(true);
				} else {
					clearHeaderDtls();
				}
				getDomElementById("manifestNo").value = "";
				setTimeout(function() {
					getDomElementById("manifestNo").focus();
				}, 10);
			} else {
				// Setting Header values
				populateHeaderDetails(manifestDetails);
				// diable header
				disableOrEnableHeader(true);

				// Setting grid details
				if (!isNull(detailTOJson)) {
					var doxDetailTO = eval('(' + detailTOJson + ')');
					populateGridDetails(manifestDetails, doxDetailTO);
				}
				getDomElementById("manifestStatus").value = manifestDetails.doxTO.manifestStatus;
				if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_CLOSE) {
					for ( var count = 1; count <= maxCNsAllowed; count++) {
						disableOrEnableGrid(count, true);
					}
				}
			}
		}
		if (getDomElementById("manifestStatus").value == MANIFEST_STATUS_CLOSE)
			disableForClose();
		isManifestSaved = true;
	}
	hideProcessing();
}
function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}
function populateHeaderDetails(manifestDetails) {
	document.getElementById("manifestDate").value = manifestDetails.doxTO.manifestDate;
	document.getElementById("manifestNo").value = manifestDetails.doxTO.manifestNo;
	// document.getElementById("originOffice").value =
	// manifestDetails.doxTO.loginOfficeName;

	var weight = parseFloat(manifestDetails.doxTO.finalWeight).toFixed(3);
	document.getElementById("finalWeight").value = weight;
	document.getElementById("finalKgs").value = weight.split(".")[0];
	document.getElementById("finalGms").value = weight.split(".")[1];

	showDropDownBySelected("destRegionId",
			manifestDetails.doxTO.destRegionTO.regionId);
	createDropDown("destCity", manifestDetails.doxTO.destinationCityTO.cityId,
			manifestDetails.doxTO.destinationCityTO.cityName);
	if (manifestDetails.doxTO.isMulDestination == "Y") {
		var officeId = "0";
		var officeName = "ALL";
		createDropDown("destOffice", officeId, officeName);
	} else {
		createDropDown("destOffice", manifestDetails.doxTO.destinationOfficeId,
				manifestDetails.doxTO.destinationOfficeName);
	}
	if (manifestDetails.doxTO.isCoMailOnly == "Y")
		document.getElementById("coMailOnly").checked = true;
	else
		document.getElementById("coMailOnly").checked = false;
	document.getElementById("coMailOnly").value = manifestDetails.doxTO.isCoMailOnly;

	if (manifestDetails.doxTO.loginOfficeType == OFF_TYPE_CODE_BRANCH
			&& manifestDetails.doxTO.manifestOpenType == "O") {
		document.getElementById("ogmRad").checked = true;
		document.getElementById("manifestOpenType").value = manifestDetails.doxTO.manifestOpenType;
	}
	if (manifestDetails.doxTO.loginOfficeType == OFF_TYPE_CODE_BRANCH
			&& manifestDetails.doxTO.manifestOpenType == "P") {
		document.getElementById("opmRad").checked = true;
		document.getElementById("manifestOpenType").value = manifestDetails.doxTO.manifestOpenType;
	}
	if (!isNull(manifestDetails.doxTO.manifestProcessTo)) {
		document.getElementById("manifestProcessId").value = manifestDetails.doxTO.manifestProcessTo.manifestProcessId;
	}
	document.getElementById("regionId").value = manifestDetails.doxTO.regionId;
	document.getElementById("manifestId").value = manifestDetails.doxTO.manifestId;
	document.getElementById("loginOfficeId").value = manifestDetails.doxTO.loginOfficeId;
	document.getElementById("manifestStatus").value = manifestDetails.doxTO.manifestStatus;
	document.getElementById("outMnfstDestIds").value = manifestDetails.doxTO.outMnfstDestIds;
	document.getElementById("processId").value = manifestDetails.doxTO.processId;
	document.getElementById("consignmentTypeId").value = manifestDetails.doxTO.consignmentTypeTO.consignmentId;
	document.getElementById("operatingLevel").value = manifestDetails.doxTO.operatingLevel;

	/** Added manifest type PURE or TRANSHIPMENT - New C.R. */
	if (!isNull(getDomElementById("ogmManifestType"))) {
		getDomElementById("ogmManifestType").value = manifestDetails.doxTO.ogmManifestType;
	}

}
function populateGridDetails(manifestDetails, doxDetailTO) {
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	var noofRows = manifestDetails.doxTO.outManifestDoxDetailTOs.length;
	var startIndex = 0;
	var comailIndex = parseIntNumber(maxCNsAllowed) + 1;
	if (manifestDetails.doxTO.isCoMailOnly == "Y") {
		for ( var i = 1; i <= noofRows; i++) {
			if (i <= maxCNsAllowed) {
				document.getElementById("consigntNo" + i).value = doxDetailTO.detailTO[i - 1].comailNo;
				document.getElementById("consgId" + i).value = doxDetailTO.detailTO[i - 1].comailId;
				document.getElementById("consgManifestedId" + i).value = doxDetailTO.detailTO[i - 1].comailManifestedId;
				if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
					document.getElementById("consigntNo" + i).disabled = true;
					document.getElementById("weight" + i).readOnly = true;
					document.getElementById("weightGm" + i).readOnly = true;
				}
			} else {
				document.getElementById("comailNo" + i).value = doxDetailTO.detailTO[i - 1].comailNo;
				document.getElementById("comailId" + i).value = doxDetailTO.detailTO[i - 1].comailId;
				document.getElementById("comailManifestedId" + i).value = doxDetailTO.detailTO[i - 1].comailManifestedId;
				if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
					document.getElementById("comailNo" + i).disabled = true;
				}
			}
		}
	} else {
		startIndex = 1;
		comailIndex = parseIntNumber(maxCNsAllowed) + 1;

		for ( var i = 1; i <= noofRows; i++) {
			// To display in the comail and Consignment blocks seperately.

			if (!isNull(doxDetailTO.detailTO[i - 1].consgNo)) {
				document.getElementById("consigntNo" + startIndex).value = doxDetailTO.detailTO[i - 1].consgNo;
				document.getElementById("consgId" + startIndex).value = doxDetailTO.detailTO[i - 1].consgId;
				document.getElementById("consgManifestedId" + startIndex).value = doxDetailTO.detailTO[i - 1].consgManifestedId;
				document.getElementById("isCNProcessedFromPickup" + startIndex).value = doxDetailTO.detailTO[i - 1].isPickupCN;
				document.getElementById("bookingWeight" + startIndex).value = doxDetailTO.detailTO[i - 1].bkgWeight;
				document.getElementById("bookingId" + startIndex).value = doxDetailTO.detailTO[i - 1].bookingId;
				document.getElementById("bookingTypeId" + startIndex).value = doxDetailTO.detailTO[i - 1].bookingTypeId;
				document.getElementById("customerId" + startIndex).value = doxDetailTO.detailTO[i - 1].customerId;
				document.getElementById("consignorId" + startIndex).value = doxDetailTO.detailTO[i - 1].consignorId;
				document.getElementById("lcDetails" + startIndex).value = doxDetailTO.detailTO[i - 1].lcDtls;
				document.getElementById("runsheetNo" + startIndex).value = doxDetailTO.detailTO[i - 1].runsheetNo;

				document.getElementById("pincode" + startIndex).value = doxDetailTO.detailTO[i - 1].pincode;
				document.getElementById("pincodeId" + startIndex).value = doxDetailTO.detailTO[i - 1].pincodeId;
				document.getElementById("destCity" + startIndex).value = doxDetailTO.detailTO[i - 1].destCity;
				document.getElementById("destCityId" + startIndex).value = doxDetailTO.detailTO[i - 1].destCityId;

				document.getElementById("weights" + startIndex).value = doxDetailTO.detailTO[i - 1].weight;
				var weight = parseFloat(doxDetailTO.detailTO[i - 1].weight)
						.toFixed(3);
				document.getElementById("weights" + startIndex).value = weight;
				document.getElementById("weight" + startIndex).value = weight
						.split(".")[0];
				document.getElementById("weightGm" + startIndex).value = weight
						.split(".")[1];

				// disable grid
				if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
					document.getElementById("consigntNo" + startIndex).readOnly = true;
					document.getElementById("destCity" + startIndex).disabled = true;
				}

				if (doxDetailTO.detailTO[i - 1].mobileNo == ""
						|| doxDetailTO.detailTO[i - 1].mobileNo == null) {
					document.getElementById("mobile" + startIndex).disabled = true;
				} else {
					document.getElementById("mobile" + startIndex).value = doxDetailTO.detailTO[i - 1].mobileNo;
					document.getElementById("mobile" + startIndex).disabled = true;
					document.getElementById("consigneeId" + startIndex).value = doxDetailTO.detailTO[i - 1].consigneeId;
				}
				document.getElementById("status" + startIndex).value = "N";
				
				fnEnableDisbleField('weight'+startIndex, true);
				fnEnableDisbleField('weightGm'+startIndex, true);
				if (doxDetailTO.detailTO[i - 1].isPickupCN == 'Y' && !isWeighingMachineConnected) {
					//Enable for pickup CNs if weighing scale not connected
					fnEnableDisbleField('weight'+startIndex, false);
					fnEnableDisbleField('weightGm'+startIndex, false);				
				}				
				startIndex++;
			} else {
				if (!isNull(doxDetailTO.detailTO[i - 1].comailNo)) {
					document.getElementById("comailNo" + comailIndex).value = doxDetailTO.detailTO[i - 1].comailNo;
					document.getElementById("comailId" + comailIndex).value = doxDetailTO.detailTO[i - 1].comailId;
					document.getElementById("comailManifestedId" + comailIndex).value = doxDetailTO.detailTO[i - 1].comailManifestedId;
					// document.getElementById("status" + comailIndex).value =
					// "U";

					if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_OPEN) {
						document.getElementById("comailNo" + comailIndex).readOnly = true;
					}

					comailIndex++;
				}
			}
		}
		// Set focus on last row
		if (manifestDetails.doxTO.manifestStatus == MANIFEST_STATUS_OPEN && startIndex >=1 && !isNaN(document.getElementById("consigntNo" + startIndex)) && !isNull(document.getElementById("consigntNo" + startIndex))) {
			setTimeout(function() {
				document.getElementById("consigntNo" + startIndex).focus();
			}, 10);
		}
		
		isManifestSaved = true;
	}
}
/**
 * clearHeaderDtls
 * 
 * @author shahnsha
 */
function clearHeaderDtls() {
	document.getElementById("manifestNo").value = "";
	document.getElementById("finalKgs").value = "";
	document.getElementById("finalGms").value = "";

	// FIXME: defect NO - 12 :added below line to clear the header
	// details-shahnaz
	showDropDownBySelected('destRegionId', destRegionVal);
	createDropDown("destCity", "", "SELECT");
	createDropDown("destOffice", "", "SELECT");
	document.getElementById("coMailOnly").checked = false;
}
/**
 * clearRows
 * 
 * @param count
 * @author shahnsha
 */
function clearRows(count) {
	// enable grid
	disableOrEnableGrid(count, false);
	if (count > 0 && count <= 50) {
		if (!isNull(document.getElementById("consigntNo" + count))) {
			document.getElementById("consigntNo" + count).value = "";
			document.getElementById("pincode" + count).value = "";
			document.getElementById("destCity" + count).value = "";
			document.getElementById("weight" + count).value = "";
			document.getElementById("weightGm" + count).value = "";
			document.getElementById("mobile" + count).value = "";

			document.getElementById("consgId" + count).value = "";
			document.getElementById("consgManifestedId" + count).value = "";
			document.getElementById("pincodeId" + count).value = "";
			document.getElementById("destCityId" + count).value = "";
			document.getElementById("weights" + count).value = "";
			document.getElementById("consigneeId" + count).value = "";
			document.getElementById("status" + count).value = "";
		}
	}
	if (count >= 51 && count <= 60) {
		if (!isNull(document.getElementById("comailNo" + count))) {
			document.getElementById("comailNo" + count).value = "";
			document.getElementById("comailId" + count).value = "";
			document.getElementById("comailManifestedId" + count).value = "";
			// document.getElementById("status" + count).value = "";
		}
	}
}

/**
 * disableOrEnableHeader
 * 
 * @param flag
 * @author shahnsha
 */
function disableOrEnableHeader(flag) {
	if (getDomElementById("loginOfficeType").value == OFF_TYPE_CODE_BRANCH) {
		document.getElementById("ogmRad").disabled = flag;
		document.getElementById("opmRad").disabled = flag;
	}
	document.getElementById("manifestNo").readOnly = flag;
	document.getElementById("finalKgs").readOnly = flag;
	document.getElementById("finalGms").readOnly = flag;
	document.getElementById("coMailOnly").disabled = flag;
	document.getElementById("destRegionId").disabled = flag;
	document.getElementById("destCity").disabled = flag;
	document.getElementById("destOffice").disabled = flag;
	if (!isNull(getDomElementById("ogmManifestType"))) {
		document.getElementById("ogmManifestType").disabled = flag;
	}
}
/**
 * disableOrEnableGrid
 * 
 * @param rowCount
 * @param flag
 * @author shahnsha
 */
function disableOrEnableGrid(rowCount, flag) {
	if (!isNull(document.getElementById("consigntNo" + rowCount))) {
		document.getElementById("consigntNo" + rowCount).disabled = flag;
		document.getElementById("pincode" + rowCount).disabled = flag;
		document.getElementById("destCity" + rowCount).disabled = flag;
		document.getElementById("weight" + rowCount).disabled = flag;
		document.getElementById("weightGm" + rowCount).disabled = flag;
		document.getElementById("mobile" + rowCount).disabled = flag;
	} else if (!isNull(document.getElementById("comailNo" + rowCount)))
		document.getElementById("comailNo" + rowCount).disabled = flag;
}
/**
 * createDropDown
 * 
 * @param domId
 * @param value
 * @param desc
 * @returns {___domElement0}
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

/**
 * getCheckBoxValues
 * 
 * @returns {String}
 * @author shahnsha
 */
function getConsignmntIdOnCheck() {
	var chkRowCount = "";
	var consIdListAtGrid = "";
	var comailIdListAtGrid = "";
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	var coMailOnly = document.getElementById("coMailOnly");
	var checkBoxes = document.outManifestDoxForm.chkBoxName;
	if (checkBoxes.length == undefined) {
		if (document.getElementById('chk').checked) {
			chkRowCount = document.getElementById('chk').value;
			var consgId = "";
			var coMailId = "";
			if (parseIntNumber(chkRowCount) > parseIntNumber(maxCNsAllowed)) {
				coMailId = document.getElementById('comailId' + chkRowCount).value;
			} else {
				consgId = document.getElementById('consgId' + chkRowCount).value;
			}
			clearRows(chkRowCount);
			getDomElementById("chk" + chkRowCount).checked = "";
			if (!isNull(consgId)) {
				calculateFinalWeight(chkRowCount);
				consIdListAtGrid = consgId;
			}
			if (!isNull(coMailId)) {
				comailIdListAtGrid = coMailId;
			}
		}
	} else {
		var isRowSelected = false;
		for ( var i = 0; i < checkBoxes.length; i++) {
			if (document.getElementsByName('chkBoxName')[i].checked) {
				isRowSelected = true;
				chkRowCount = document.getElementsByName('chkBoxName')[i].value;
				var consgId = "";
				var coMailId = "";
				if (parseIntNumber(chkRowCount) > parseIntNumber(maxCNsAllowed)) {
					coMailId = document
							.getElementById('comailId' + chkRowCount).value;
				} else {
					consgId = document.getElementById('consgId' + chkRowCount).value;
				}
				clearRows(chkRowCount);
				getDomElementById("chk" + chkRowCount).checked = "";
				if (!isNull(consgId)) {
					calculateFinalWeight(chkRowCount);
					consIdListAtGrid = consIdListAtGrid + "," + consgId;
				}
				if (!isNull(coMailId)) {
					comailIdListAtGrid = comailIdListAtGrid + "," + coMailId;
				}
			}
		}
		if (isRowSelected == false) {
			alert("Please select row for delete");
		}
	}
	getDomElementById("checkAll").checked = "";
	if (coMailOnly.checked) {
		document.getElementById("comailIdListAtGrid").value = consIdListAtGrid
				+ "," + comailIdListAtGrid;
	} else {
		document.getElementById("consgIdListAtGrid").value = consIdListAtGrid;
		document.getElementById("comailIdListAtGrid").value = comailIdListAtGrid;
	}
	if (!isNull(consIdListAtGrid) || !isNull(comailIdListAtGrid)) {
		alert("Consignment/Comail deleted successfully");
	}
}

/**
 * validateComail
 * 
 * @param obj
 * @returns {Boolean}
 * @author shahnsha
 */
function validateComail(obj) {
	var flag = true;
	if (!isNull(obj.value)) {
		var comailNo = "";
		comailNo = obj.value;
		if (comailNo.length < 12 || comailNo.length > 12) {
			alert("Comail number length should be 12 character");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			flag = false;
		} else {
			var numpattern = /^[0-9]{3,20}$/;
			if (obj.value.substring(0, 2).toUpperCase() != coMailStartSeries
					|| !numpattern.test(obj.value.substring(3, 12))) {
				alert('Comail number format is not correct');
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				flag = false;
			} else {
				isComailBooked(obj);
			}
		}
	}
	return flag;
}
function isComailBooked(comailObj) {
	showProcessing();
	var manifestId = document.getElementById("manifestId").value;
	if (!isNull(comailObj.value)) {
		url = './outManifestDox.do?submitName=validateComail&comailNo='
				+ comailObj.value + "&manifestId=" + manifestId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackComail(req, comailObj);
			}
		});
	}
}
function printCallBackComail(data, comailObj) {
	var response = data;
	if (!isNull(response)) {
		if (response == "N") {
			alert("Comail number already used!");
			comailObj.value = "";
			setTimeout(function() {
				comailObj.focus();
			}, 10);
			// hideProcessing();
		} else {
			if (isNull(destRegionVal))
				destRegionVal = getDomElementById("destRegionId").value;
			if (isNull(destCityVal))
				destCityVal = getDomElementById("destCity").value;
			if (isNull(destOfficeVal))
				destOfficeVal = getDomElementById("destOffice").value;
			if (isNull(ogmManifestType)
					&& !isNull(getDomElementById("ogmManifestType")))
				ogmManifestType = getDomElementById("ogmManifestType").value;
			// TODO: Commented after discussion with Saumya
			/*
			 * showProcessing(); //getDomElementById("status" + rowId).value =
			 * "C"; url =
			 * './outManifestDox.do?submitName=getComailIdByComailNo&comailNo=' +
			 * comailObj.value; jQuery.ajax({ url : url, success : function(req) {
			 * printCallBackGetComailId(req, rowId); } });
			 */
		}
	}
	hideProcessing();
}

function printCallBackGetComailId(comailId, rowId) {
	if (!isNull(comailId)) {
		document.getElementById("comailId" + rowId).value = comailId;
	}
	hideProcessing();
}
/**
 * fnCoMailOnly
 * 
 * @param obj
 * @author shahnsha
 */
function fnCoMailOnly(obj) {
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	if (obj.checked) {
		obj.value = "Y";
		getDomElementById("finalWeight").value = "";
		getDomElementById("finalKgs").value = "0";
		getDomElementById("finalGms").value = "000";
		for ( var count = 1; count <= maxCNsAllowed; count++) {
			clearRows(count);
			disableOrEnableGrid(count, true);
			getDomElementById("consigntNo" + count).disabled = false;
		}
	} else {
		obj.value = "N";
		for ( var count = 1; count <= maxCNsAllowed; count++) {
			// Added below line to clear the grid
			clearRows(count);
			disableOrEnableGrid(count, false);
		}
	}
}

/**
 * fnValidateNumber
 * 
 * @param obj
 * @author shahnsha
 */
function fnValidateNumber(obj, count) {
	var gridObjValue = trimString(obj.value);
	if (!isNull(gridObjValue)) {
		if (isDuplicateConsignment(obj, count)) {
			var coMailOnlyObj = document.getElementById("coMailOnly");
			var objId = obj.id;
			var domEleId = "";
			if (objId.match("consigntNo")) {
				rowId = objId.split("consigntNo")[1];
				domEleId = "consigntNo";
			} else if (objId.match("comailNo")) {
				rowId = objId.split("comailNo")[1];
				domEleId = "comailNo";
			}
			if (coMailOnlyObj.checked) {
				validateComail(obj);
			} else if (domEleId.match("comailNo")) {
				validateComail(obj);
			} else {
				// Added TRANSHIPMENT and PURE logic - C.R.
				var ogmManifestType = getDomElementById("ogmManifestType");
				if (isNull(ogmManifestType)) {
					validateConsignment(obj);
				} else {
					if (ogmManifestType.value == PURE) {// PURE
						validateConsignment(obj);
					} else if (ogmManifestType.value == TRANS) {// TRANSHIPMENT
						validateTransshCN(obj);
					}
				}
			}
		}
	} else {
		clearRows(count);
		calculateFinalWeight(count);
	}
}
/**
 * isDuplicateConsignment
 * 
 * @param consgNumberObj
 * @param count
 * @returns {Boolean}
 * @author shahnsha
 */
function isDuplicateConsignment(consgNumberObj, count) {
	var isValid = true;
	var enreredCn = consgNumberObj.value;
	var objId = consgNumberObj.id;
	var rowId = "";
	if (objId.match("consigntNo")) {
		rowId = objId.split("consigntNo")[1];
		getDomElementById("consigntNo" + rowId).value = enreredCn.toUpperCase();
	} else if (objId.match("comailNo")) {
		rowId = objId.split("comailNo")[1];
		getDomElementById("comailNo" + rowId).value = enreredCn.toUpperCase();
	}
	var table = document.getElementById('outManifstTable');
	var cntofRow = table.rows.length;
	for ( var i = 1; i < cntofRow; i++) {
		if (i == rowId) {
			continue;
		}
		var tableObject = getDomElementById("consigntNo" + i);
		if (isNull(tableObject)) {
			tableObject = getDomElementById("comailNo" + i);
			if (isNull(tableObject)) {
				continue;
			}
		}

		if (enreredCn.toUpperCase() == tableObject.value.toUpperCase()) {
			isValid = false;
			alert("Duplicate number entered");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			break;
		}
	}
	return isValid;
}

/**
 * saveOrCloseOutManifestDox
 * 
 * @param action
 * @author shahnsha
 * 
 * @Desc check wheather manifestNo is already manifested or not to avoid
 *       duplicate entry into the database before save or close.
 * @param action
 */

/*
 * function saveOrCloseOutManifestDox(action) { if (validateHeader() &&
 * validateGridDetails()) { disableOrEnableHeader(false);
 * checkIfDeletedBeforeSave(action); } }
 */

/**
 * @Desc check if delete before save or close
 * @param action
 */
/*
 * function checkIfDeletedBeforeSave(action) { var manifestId =
 * getDomElementById("manifestId").value; // delete the Consg/Manifest/Co-Mail
 * before saving if (isDeleted && !isNull(manifestId)) {
 * //deleteTableRow(action); printCallBackManifestDelete("SUCCESS", action); }
 * else { performSaveOrClose(action); } }
 */

/**
 * @Desc actual perform save or close operation
 * @param action
 */
function saveOrCloseOutManifestDox(action) {
	// if all row is filled then manifestStatus should be closed ("C")
	// if(isAllRowInGridFilled(maxCNsAllowed)) action='close';
	if (validateHeader() && validateGridDetails()) {
		if (action == 'save') {// Open
			getDomElementById("manifestStatus").value = MANIFEST_STATUS_OPEN;
		} else if (action == 'close') {// Close
			getDomElementById("manifestStatus").value = MANIFEST_STATUS_CLOSE;
		}
		disableOrEnableHeader(false);
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		var maxComailsAllowed = document.getElementById("maxComailsAllowed").value;
		for ( var count = 1; count <= (parseIntNumber(maxCNsAllowed) + parseIntNumber(maxComailsAllowed)); count++) {
			disableOrEnableGrid(count, false);
		}

		showProcessing();
		var url = './outManifestDox.do?submitName=saveOrUpdateOutManifestDox';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#outManifestDoxForm").serialize(),
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
				document.getElementById("manifestNo").value = "";
				document.getElementById("manifestNo").focus();
			}
			hideProcessing();
		}
	} else if (!isNull(response)) {

		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		var maxCMailsAllowed = document.getElementById("maxComailsAllowed").value;
		var noofRows = parseIntNumber(maxCNsAllowed)
				+ parseIntNumber(maxCMailsAllowed);
		if (response == MANIFEST_STATUS_CLOSE) {
			// alert("Manifest saved and closed successfully");
			getDomElementById("manifestStatus").value = response;
			var coMailOnlyObj = document.getElementById("coMailOnly");
			if (!coMailOnlyObj.checked) {
				if (confirm("Manifest saved and closed successfully.")) {
					searchManifest();
					printManifest();
					refreshPage();
					/*
					 * setTimeout(function() { printManifest(); }, 3000);
					 * setTimeout(function() { refreshPage(); }, 4000);
					 */
				} else {
					refreshPage();
				}
				// printManifest();
			} else {
				refreshPage();
			}
			for ( var count = 1; count <= noofRows; count++) {
				disableOrEnableGrid(count, true);
			}
			disableOrEnableHeader(true);
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
						disableOrEnableGrid(count, true);
					} else {
						disableOrEnableGrid(count, false);
					}
				} else {
					if (!isNull(getDomElementById("comailNo" + count).value)) {
						disableOrEnableGrid(count, true);
					} else {
						disableOrEnableGrid(count, false);
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
			var manifestNoObj = getDomElementById("manifestNo");
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			jQuery.unblockUI();
		}
	}
}

function refreshPage() {
	var url = "outManifestDox.do?submitName=viewOutManifestDox";
	document.outManifestDoxForm.action = url;
	document.outManifestDoxForm.submit();
}
/**
 * @Desc delete Consg Dtls from client side
 */
function deleteGridDtls() {
	getConsignmntIdOnCheck();
	isManifestSaved = false;
	isDeleted = true;
}
/**
 * printCallBackManifestDelete
 * 
 * @param data
 * @author shahnsha
 */
function printCallBackManifestDelete(data, action) {
	if (data != null && data != "") {
		if (data == "SUCCESS") {
			performSaveOrClose(action);
		} else if (data == "FAILURE") {
			hideProcessing();
			alert("Exception occurred. Record could not be saved.");
		}
	}
}
function getAllOfficeIds(obj) {
	if (obj.value == 0) {
		document.getElementById("isMulDest").value = "Y";
	} else {
		document.getElementById("isMulDest").value = "N";
	}
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("consigntNo" + rowId);
	return callEnterKey(e, focusId);
}

function checkComail(e, rowId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		maxCNsAllowed = parseIntNumber(maxCNsAllowed);
		// var rowId = getRowId(consgObj, "weightGm");
		rowId = parseIntNumber(rowId);
		if (document.getElementById("coMailOnly").checked) {
			return enterKeyNav("consigntNo" + (++rowId), e.keyCode);
		} else {
			if (rowId < maxCNsAllowed) {
				return enterKeyNav("consigntNo" + (++rowId), e.keyCode);
			} else {
				document.getElementById("saveBtn").focus();
				// return false;
			}
		}
	} else {
		return true;
	}
}
function disableForClose() {
	var coMailOnlyObj = document.getElementById("coMailOnly");
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
	if (coMailOnlyObj.checked) {
		jQuery("#" + "printBtn").addClass("btnintformbigdis");
	} else {
		jQuery("#" + "printBtn").attr("disabled", false);
		jQuery("#" + "printBtn").removeClass("btnintformbigdis");
		jQuery("#" + "printBtn").addClass("btnintform");
	}
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
	jQuery("#" + "closeBtn").addClass("btnintformbigdis");
	jQuery("#" + "deleteBtn").addClass("btnintformbigdis");
	jQuery("#" + "cancelBtn").attr("disabled", false);
	jQuery("#" + "cancelBtn").removeClass("btnintformbigdis");
	jQuery("#" + "cancelBtn").addClass("btnintform");
}
/* @Desc:Disabling the checkbox of the grid */
function disableAllCheckBox() {
	$("input[type='checkbox']").prop("disabled", true);
}

function fnHeaderDataChanges() {
	var maxCMailsAllowed = document.getElementById("maxComailsAllowed").value;
	var maxCNsAllowed = getDomElementById("maxCNsAllowed").value;
	var coMailOnlyObj = getDomElementById("coMailOnly");
	var noofRows = parseIntNumber(maxCNsAllowed);
	var gridValueExists = false;
	var gridNumber = "";
	if (coMailOnlyObj.checked) {
		noofRows = parseIntNumber(noofRows) + parseIntNumber(maxCMailsAllowed);
	}
	for ( var count = 1; count <= noofRows; count++) {
		if (!isNull(document.getElementById("consigntNo" + count)))
			gridNumber = document.getElementById("consigntNo" + count).value;
		if (!isNull(document.getElementById("comailNo" + count)))
			gridNumber = document.getElementById("comailNo" + count).value;
		if (!isNull(gridNumber)) {
			gridValueExists = true;
			break;
		}
	}
	if (gridValueExists) {
		if (confirm("Consignments/Comails already entered.\n\nDo you want to make the changes in header?")) {
			for ( var count = 1; count <= noofRows; count++)
				clearRows(count);
		} else {
			showDropDownBySelected('destRegionId', destRegionVal);
			showDropDownBySelected('destCity', destCityVal);
			showDropDownBySelected('destOffice', destOfficeVal);
			if (!isNull(getDomElementById("ogmManifestType"))) {
				showDropDownBySelected('ogmManifestType', ogmManifestType);
			}
		}
	}
}
function fnEnterKeyCallOnCnNo(e, obj) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var coMailOnlyObj = document.getElementById("coMailOnly");
		var objId = obj.id;
		var count = "";
		if (objId.match("consigntNo")) {
			count = objId.split("consigntNo")[1];
			objId = "consigntNo";
		} else if (objId.match("comailNo")) {
			count = objId.split("comailNo")[1];
			objId = "comailNo";
		}
		var nextRow = parseIntNumber(count) + 1;
		var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
		var maxCMailsAllowed = document.getElementById("maxComailsAllowed").value;
		maxCNsAllowed = parseIntNumber(maxCNsAllowed);
		maxCMailsAllowed = parseIntNumber(maxCMailsAllowed);
		var noOfRows = maxCNsAllowed + maxCMailsAllowed;
		noOfRows = parseIntNumber(noOfRows);
		if (coMailOnlyObj.checked) {
			if (nextRow > maxCNsAllowed) {
				objId = "comailNo";
			}
			if (nextRow <= noOfRows) {
				getDomElementById(objId + nextRow).focus();
			} else
				document.getElementById("saveBtn").focus();
			return false;
		} else if (objId.match("comailNo")) {
			if (nextRow <= noOfRows) {
				getDomElementById("comailNo" + nextRow).focus();
			} else
				document.getElementById("saveBtn").focus();
		} else {
			return callEnterKey(e, document.getElementById('pincode' + count));
		}
	}
}

/**
 * @param domEleValue
 * @param rowId
 * 
 * 1. oldPincode - Pincode - pincode 2. oldWtKg - Weight Kg - weight 3. oldWtGm -
 * Weight Gm - weightGm 4. oldMob - Mobile Number - mobile
 * 
 */
function isDataMismatched(rowId) {
	/** old values */
	var oldPincode = getDomElementById("oldPincode" + rowId).value;
	var oldWtKg = getDomElementById("oldWtKg" + rowId).value;
	var oldWtGm = getDomElementById("oldWtGm" + rowId).value;
	var oldMob = getDomElementById("oldMob" + rowId).value;

	/** new value */
	var newPincode = getDomElementById("pincode" + rowId).value;
	var newWtKg = getDomElementById("weight" + rowId).value;
	var newWtGm = getDomElementById("weightGm" + rowId).value;
	var newMob = getDomElementById("mobile" + rowId).value;

	/* Pincode */
	if (oldPincode != newPincode) {
		getDomElementById("isDataMismatch" + rowId).value = "Y";
	}
	/* Weight Kg */
	else if (oldWtKg != newWtKg) {
		getDomElementById("isDataMismatch" + rowId).value = "Y";
	}
	/* Weight Gm */
	else if (oldWtGm != newWtGm) {
		getDomElementById("isDataMismatch" + rowId).value = "Y";
	}
	/* Mobile */
	else if (oldMob != newMob) {
		getDomElementById("isDataMismatch" + rowId).value = "Y";
	} else {
		getDomElementById("isDataMismatch" + rowId).value = "N";
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

/**
 * To validate CN No./Comail No. for scan in grid
 * 
 * @param cnObj
 * @returns {Boolean}
 */
function isValidForScanCN(cnObj) {
	var rowId = getRowId(cnObj, "consigntNo");
	var prevPin = getDomElementById("prevPin" + rowId).value;
	if (prevPin == cnObj.value) {
		var pincode = getDomElementById("pincode" + rowId).value;
		if (!isNull(pincode)) {
			return false;
		}
	}
	getDomElementById("prevPin" + rowId).value = cnObj.value;
	return true;
}

/**
 * To enable or disabled fields for open manifest
 * 
 * @param flag
 */
function enableDisabledForOpenManifest(flag) {
	getDomElementById("destRegionId").disabled = flag;
	getDomElementById("destCity").disabled = flag;
}
function isBookingWeightReduced(rowId) {
	var bookingWeight = "";
	var currentMnfstCNWeight = "";
	bookingWeight = parseFloat(getValueByElementId("bookingWeight" + rowId));
	currentMnfstCNWeight = parseFloat(getValueByElementId("weights" + rowId));
	if (currentMnfstCNWeight < bookingWeight) {
		alert("Booking weight could not be reduced");
		getDomElementById("weights" + rowId).value = bookingWeight;
		bookingWeight = getValueByElementId("bookingWeight" + rowId);
		getDomElementById("weight" + rowId).value = bookingWeight.split(".")[0];
		getDomElementById("weightGm" + rowId).value = bookingWeight.split(".")[1];
	}
}

/**
 * To validate for enter key navigation
 * 
 * @param e
 * @param nextDomObj
 */
function validateEnterKey(e, nextDomObj) {
	var manifestOpenType = getDomElementById("manifestOpenType").value;
	if (manifestOpenType == "O") { // O - OGM
		return callEnterKey(e, nextDomObj);
	} else { // P - Open Manifest
		return callFocusEnterKey(e);
	}
}

/**
 * @param rowId
 * @returns {Boolean}
 */
function isValidRowForRateCalculation(rowId) {
	var flag = true;
	var consgNo = getDomElementById("consigntNo" + rowId);
	var pincode = document.getElementById("pincode" + rowId);
	var weight = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId);
	var weightInFraction = parseFloat(((weight) + (weightGm.value)) / 1000)
			.toFixed(3);
	if (isNull(consgNo.value)) {
		flag = false;
	} else if (isNull(pincode.value)) {
		flag = false;
	} else if (isEmptyWeight(weightInFraction)) {
		flag = false;
	}
	return flag;
}
/**
 * To validate row by row id
 * 
 * @param rowId
 * @returns {Boolean}
 */
function isValidRow(rowId) {
	var flag = true;
	var consgNo = getDomElementById("consigntNo" + rowId);
	var pincode = document.getElementById("pincode" + rowId);
	var weight = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId);
	var weightInFraction = parseFloat(((weight) + (weightGm.value)) / 1000)
			.toFixed(3);
	if (isNull(consgNo.value)) {
		flag = false;
		alert("Please enter consignment number");
		setTimeout(function() {
			consgNo.focus();
		}, 10);

	} else if (isNull(pincode.value)) {
		flag = false;
		alert("Please enter pincode");
		setTimeout(function() {
			pincode.focus();
		}, 10);
	} else if (isEmptyWeight(weightInFraction)) {
		flag = false;
		alert("Please enter weight");
		setTimeout(function() {
			weightGm.focus();
		}, 10);
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
		var cnSeries = cnNo.substring(4, 5).toUpperCase();
		var pincode = getDomElementById("pincode" + rowId).value;
		var weight = document.getElementById("weight" + rowId).value;
		var weightGm = document.getElementById("weightGm" + rowId).value;
		if (isNull(weight)) {
			weight = "0";
		}
		weightInFraction = parseFloat(weight + "." + weightGm).toFixed(3);
		var officeId = getDomElementById("loginOfficeId").value;
		var cnType = CONSG_TYPE_DOX;
		RATE_ROW_COUNT = rowId;
		var url = "./outManifestDox.do?submitName=calculateCnRate"
				+ "&consigntNo=" + cnNo + "&pincode=" + pincode + "&weight="
				+ weightInFraction + "&loginOfficeId=" + officeId
				+ "&consgType=" + cnType;
		// Check for D,L or T series consignment
		if (cnSeries == "D" || cnSeries == "L" || cnSeries == "T") {
			if (mandatoryLcCodAmtCheck(cnSeries, rowId)) {
				url += getLcCodAmtInUrl(cnSeries, rowId);
			} else {
				return false;
			}
		}
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
		}
	}
	hideProcessing();
}

function validatePincode(pinObj) {
	if (!isNull(pinObj.value)) {
		var loggedInOfficeId = "";
		var pincode = pinObj.value;
		var bplManifestType = PURE;
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
		var isPickupCN = "";
		var consgNumber = document.getElementById("consigntNo" + ROW_COUNT).value;
		var consgSeries = consgNumber.substring(4, 5);
		loggedInOfficeId = document.getElementById("originOffice").value;
		var destCityId = document.getElementById("destCity").value;
		var destOfficeId = document.getElementById("destOffice").value;
		if (!isNull(getDomElementById("manifestOpenType")))
			manifestOpenType = getDomElementById("manifestOpenType").value;
		if (!isNull(getDomElementById("isCNProcessedFromPickup" + ROW_COUNT)))
			isPickupCN = getDomElementById("isCNProcessedFromPickup"
					+ ROW_COUNT).value;

		var isChkTransCityPincodeServ = "N";
		if (!isNull(getDomElementById("ogmManifestType"))) {
			bplManifestType = getDomElementById("ogmManifestType").value;
			if (bplManifestType == TRANS) {
				isChkTransCityPincodeServ = "Y";
			}
		}
		var manifestProcessCode = getDomElementById("processCode").value;

		showProcessing();
		url = './outManifestDox.do?submitName=validatePincode&pincode='
				+ pincode + "&loggedInOfficeId=" + loggedInOfficeId
				+ "&consgSeries=" + consgSeries + "&destOfficeId="
				+ destOfficeId + "&destCityId=" + destCityId
				+ "&manifestOpenType=" + manifestOpenType + "&isPickupCN="
				+ isPickupCN + "&isChkTransCityPincodeServ="
				+ isChkTransCityPincodeServ + "&manifestProcessCode="
				+ manifestProcessCode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req);
			}
		});
	}
}

function validatePincodeAtHub(pinObj) {
	var rowId = getRowId(pinObj, "pincode");
	var consgNo = getDomElementById("consigntNo" + rowId).value;
	if (!isNull(consgNo) && isValidPincodeFormat(pinObj)) {
		var bplManifestType = 'PURE';
		var pincode = pinObj.value;
		var destOfficeId = document.getElementById("destOffice").value;
		var destCityId = document.getElementById("destCity").value;
		var officeType = '';
		// getDomElementById("destOfficeType").value;
		if (!isNull(getDomElementById("ogmManifestType"))) {
			bplManifestType = getDomElementById("ogmManifestType").value;
		}
		ROW_COUNT = rowId;
		var consgNumber = document.getElementById("consigntNo" + ROW_COUNT).value;
		var consgSeries = consgNumber.substring(4, 5);
		var isPickupCN = getDomElementById("isCNProcessedFromPickup"
				+ ROW_COUNT).value;
		showProcessing();
		url = './outManifestDox.do?submitName=validatePincodeWithManifestType&pincode='
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
				+ consgSeries + "&isPickupCN=" + isPickupCN;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req);
			}
		});
	}
}

// call back method for validatePincode from manifestCommon.js
function printCallBackPincode(data) {
	var destCityDomEle = getDomElementById("destCity" + ROW_COUNT);
	var destCityIdDomEle = getDomElementById("destCityId" + ROW_COUNT);
	var cnValidation = jsonJqueryParser(data);
	if (!isNull(cnValidation)) {
		var error = cnValidation[ERROR_FLAG];
		if (cnValidation != null && error != null) {
			alert(error);
			document.getElementById("pincode" + ROW_COUNT).value = "";
			if (!isNull(destCityDomEle))
				getDomElementById("destCity" + ROW_COUNT).value = "";
			if (!isNull(destCityIdDomEle))
				getDomElementById("destCityId" + ROW_COUNT).value = "";
			setTimeout(function() {
				document.getElementById("pincode" + ROW_COUNT).focus();
			}, 10);
		} else {
			document.getElementById("pincodeId" + ROW_COUNT).value = cnValidation.cnPincodeTO.pincodeId;
			if (!isNull(destCityDomEle))
				getDomElementById("destCity" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityName;
			if (!isNull(destCityIdDomEle))
				getDomElementById("destCityId" + ROW_COUNT).value = cnValidation.cnDestCityTO.cityId;
			setTimeout(function() {
				getDomElementById("weightGm" + ROW_COUNT).focus();
			}, 10);
		}
	}
	jQuery.unblockUI();
}

function setIFrame() {
	var manifestNo = document.getElementById("manifestNo").value;
	if (!isNull(manifestNo)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		var url = './outManifestDox.do?submitName=printOutManifestDoxDtls&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID;
		printUrl = url;
		printIframe(printUrl);
	}
}

/**
 * To open LC pop up
 * 
 * @param count
 */
function openLCPopup(count) {
	var url = "./lcDetails.do?rowCount=" + count;
	window
			.open(
					url,
					'LC Details',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=375,height=200,left = 412,top = 184');
}

/**
 * To open COD pop up
 * 
 * @param count
 */
function openCODPopup(count) {
	var url = "./codDetails.do?rowCount=" + count;
	window
			.open(
					url,
					'COD Amount',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=375,height=200,left = 412,top = 184');
}

/**
 * To open popup for D, L or T series for pickup consignment
 * 
 * @param consgNo
 * @param rowId
 */
function openPopups(consgNo, rowId) {
	var isPickupCN = getDomElementById("isCNProcessedFromPickup" + rowId).value;
	if (!isNull(consgNo) && isPickupCN == "Y") {
		var consgSeries = consgNo.substring(4, 5);
		if (consgSeries == "D") {
			openLCPopup(rowId);
		} else if (consgSeries == "L" || consgSeries == "T") {
			openCODPopup(rowId);
		}
	}
}

/**
 * To validate transhipment consignment(s)
 * 
 * @param consgNumberObj
 * @author hkansagr
 */
function validateTransshCN(consgNumberObj) {
	var domValue = $.trim(consgNumberObj.value);
	if (!consgNumberObj.readOnly && !isNull(domValue)) {
		var isConsigValid = isValidConsignment(consgNumberObj);
		if (isConsigValid && isDuplicateConsignment(consgNumberObj)) {
			var destOfficeObj = getDomElementById("destOffice");
			var destCityObj = getDomElementById("destCity");
			var destOfficeId = "";
			var destCityId = "";
			destOfficeId = destOfficeObj.value;
			destCityId = destCityObj.value;
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var allowedConsgManifestedType = getDomElementById("allowedConsgManifestedType").value;
			var isPincodeServChk = getPincodeServChk();
			// var isPincodeServChk = "N";
			var manifestDirectn = "O";
			var manifestNo = document.getElementById("manifestNo").value;
			var ogmManifestType = getDomElementById("ogmManifestType").value;
			var loginOfficeType = getDomElementById("loginOfficeType").value;
			showProcessing();
			var url = './outManifestDox.do?submitName=validateConsignmentNumber&consgNumber='
					+ consgNumberObj.value
					+ "&officeId="
					+ destOfficeId
					+ "&cityId="
					+ destCityId
					+ "&loginOfficeId="
					+ loginOfficeId
					+ "&manifestDirection="
					+ manifestDirectn
					+ "&allowedConsgManifestedType="
					+ allowedConsgManifestedType
					+ "&isPincodeServChk="
					+ isPincodeServChk
					+ "&ogmManifestType="
					+ ogmManifestType
					+ "&officeType="
					+ loginOfficeType
					+ "&manifestNo="
					+ manifestNo;
			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBackConsignment(req, consgNumberObj);
				}
			});
		}
	}
}

/**
 * To mandatory check for rate calucation - LC / COD amount for L,D,T - series
 * 
 * @param cnSeries
 * @param count
 */
function mandatoryLcCodAmtCheck(cnSeries, count) {
	var flag = true;
	var lcDtls = getDomElementById("lcDetails" + count).value;
	var atLine = " at Line : " + count;
	if (cnSeries == "D") {
		amountName = "LC Amount";
	} else if (cnSeries == "L") {
		amountName = "COD Amount";
	} else if (cnSeries == "T") {
		return flag;
	}
	if (!isNull(lcDtls)) {
		var lcDetails = lcDtls.split("#");
		var lcCodAmount = lcDetails[0];
		if (!isNull(lcCodAmount)) {
			lcCodAmount = parseFloat(lcCodAmount).toFixed(2);
		}
		if (isEmptyRate(lcCodAmount)) {
			alert("Please provide " + amountName + atLine);
			flag = false;
			openRespectivePopup(cnSeries, count);
		}
	} else {
		alert("Please provide " + amountName + atLine);
		flag = false;
		openRespectivePopup(cnSeries, count);
	}
	return flag;
}

/**
 * To open respective popup as per cn series
 * 
 * @param cnSeries
 * @param count
 */
function openRespectivePopup(cnSeries, count) {
	if (cnSeries == "D") {
		openLCPopup(count);
	} else if (cnSeries == "L") {
		openCODPopup(count);
	}
}

/**
 * To get LC/Code Amount param/arg string to append rate calc. URL
 * 
 * @param cnSeries
 * @param count
 * @returns {String}
 */
function getLcCodAmtInUrl(cnSeries, count) {
	var param = "";
	var lcDtls = getDomElementById("lcDetails" + count).value;
	var lcDetails = lcDtls.split("#");
	var lcCodAmount = lcDetails[0];
	if (cnSeries == "D") {
		param = "&lcAmt=" + lcCodAmount;
	} else if (cnSeries == "L") {
		param = "&codAmt=" + lcCodAmount;
	} else if (cnSeries == "T") {
		if (!isNull(lcCodAmount)) {
			param = "&codAmt=" + lcCodAmount;
		}
	}
	return param;
}

/**
 * To call approriate validate pincode function as per office type
 * 
 * @param pinObj
 */
function validatePincodeForOGM(pinObj) {
	// var loginOfficeType = getDomElementById("loginOfficeType").value;
	// if (loginOfficeType == OFF_TYPE_CODE_BRANCH) {
	// validatePincode(pinObj);
	// } else { // "HO" = OFF_TYPE_CODE_HUB
	validatePincode(pinObj);
	// }
}