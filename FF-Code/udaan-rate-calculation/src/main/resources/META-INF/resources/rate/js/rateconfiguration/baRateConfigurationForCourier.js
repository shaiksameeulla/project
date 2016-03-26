$(document).ready(function() {
	var oTable = $('#courierNormalSlabs').dataTable({
		"sScrollY" : "70",
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
});

$(document).ready(function() {
	var oTable = $('#courierSpecialDestination').dataTable({
		"sScrollY" : "60",
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
});

var rowCount = 1;
var a = new Array();
;
function addCourierSpecialDestinationRow() {
	addAdditionColumns();
	$('#courierSpecialDestination').dataTable().fnAddData(a);
	loadStateDropDown("stateId" + rowCount, "", statesList);
	clearDropDownList("cityIds" + rowCount);
	rowCount++;
}

function addCourierSpecialDestRow(index, prod) {
	if (index == (rowCount - 1) && stateValidation(index, prod)) {
		addAdditionColumns();
		$('#courierSpecialDestination').dataTable().fnAddData(a);
		loadStateDropDown("stateId" + rowCount, "", statesList);
		clearDropDownList("cityIds" + rowCount);
		rowCount++;
	}
}

function addAdditionColumns() {
	a[0] = '<select  id="stateId'
			+ rowCount
			+ '" name="to.baCourierSlabRateTO.stateId" class="selectBox width155" onchange="getCityByState(this,'
			+ rowCount + ',\'cityIds\');"/>';
	a[1] = '<select id="cityIds'
			+ rowCount
			+ '" name="to.baCourierSlabRateTO.cityIds" class="selectBox width155" onchange="validateCities(\'CO\','
			+ rowCount + ',\'cityIds\',\'stateId\');"/>';

	var courierSplDestcoloumCount = document
			.getElementById("courierSplDestcoloumCount").value;
	courierSplDestcoloumCount = parseInt(courierSplDestcoloumCount) + 2;
	for ( var i = 2; i < courierSplDestcoloumCount; i++) {
		if (i + 1 == courierSplDestcoloumCount) {
			a[i] = '<input type="text" name = "to.baCourierSlabRateTO.specialDestinationRate" id="specialDestinationRate'
					+ rowCount
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'CO\','
					+ rowCount
					+ ',\'cityIds\',\'stateId\');" onblur="addCourierSpecialDestRow('
					+ rowCount
					+ ',\'CR\');"/>'
					+ '<input type="hidden" id="specialDestinationId'
					+ rowCount
					+ i
					+ '" name="to.baCourierSlabRateTO.specialDestinationId" />';

		} else {
			a[i] = '<input type="text" name = "to.baCourierSlabRateTO.specialDestinationRate" id="specialDestinationRate'
					+ rowCount
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'CO\','
					+ rowCount
					+ ',\'cityIds\',\'stateId\');"/>'
					+ '<input type="hidden" id="specialDestinationId'
					+ rowCount
					+ i
					+ '" name="to.baCourierSlabRateTO.specialDestinationId" />';

		}

	}
}

function getPincodeId(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './baRateConfiguration.do?submitName=getPincode&pincode='
				+ pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincodeDetails(req, pinObj);
			}
		});
	}
}
function printCallBackPincodeDetails(data, pinObj) {
	var rowId = getRowId(pinObj, "pincode");
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		pinObj.value = "";
		pinObj.focus();

	} else {
		var req = eval('(' + data + ')');
		document.getElementById("pincodeId" + rowId).value = req.pincodeId;

	}
}

function getCityName(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './baRateConfiguration.do?submitName=getCityName&pincode='
				+ pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackCityDetails(req, pinObj);
			}
		});
	}
}

function printCallBackCityDetails(data, pinObj) {
	var rowId = getRowId(pinObj, "pincode");
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		pinObj.value = "";
		pinObj.focus();
		document.getElementById("city" + rowId).value = "";
	} else {
		var req = eval('(' + data + ')');
		document.getElementById("city" + rowId).value = req.cityName;
		document.getElementById("cityIds" + rowId).value = req.cityId;
	}
}

function saveOrUpdateFixedChargesForCourier() {
	// if (validateDates() && !isExistsBAConfiguration() && isValidInput()) {
	if (validateDates("save") && checkCourierFixedCharges()) {
		enableHeaderBeforeSave();
		getDomElementById("btnSave").disabled = true;
		showProcessing();
		getDomElementById("isPriorityProduct").value = "N";
		var url = './baRateConfiguration.do?submitName=saveOrUpdateFixedChargesForCourier';
		ajaxJquery(url, baForm, callSaveOrUpdateFixedChargesForCourier);
	}
}

/**
 * @param data
 */
function callSaveOrUpdateFixedChargesForCourier(data) {
	if (data != null) {
		var headerTO = jsonJqueryParser(data);
		if (headerTO.isSaved == "saved") {
			getDomElementById("headerId").value = headerTO.headerId;
			var rateProductList = headerTO.baRateProductTOList;
			setFlags(headerTO);
			for ( var i = 0; i < rateProductList.length; i++) {
				if (rateProductList[i].rateProductCategory == 1) {
					getDomElementById("courierProductHeaderId").value = rateProductList[i].baProductHeaderId;
					break;
				}
				if (rateProductList[i].rateProductCategory == 5) {
					getDomElementById("priorityProductHeaderId").value = rateProductList[i].baProductHeaderId;
					break;
				}
			}
			// Added by Himal
			enableAllTabs();
			alert('Data saved successfully.');
		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	getDomElementById("btnSave").disabled = false;
	hideProcessing();
	disableHeaderAfterSave();
}

function searchFixedChargesForCourier() {
	if (isValidInput()) {
		clearAllGridFields();
		showProcessing();
		getDomElementById("priorityIndicator").value = "N";
		url = './baRateConfiguration.do?submitName=searchFixedChargesForCourier';
		ajaxJquery(url, baForm, callSearchFixedChargesForCourier);
	}

}

function callSearchFixedChargesForCourier(ajaxResp) {
	var baRateHeader = jsonJqueryParser(ajaxResp);
	if (!isNull(baRateHeader)) {
		var fixedCharges = baRateHeader.baCourierFixedChargesTO;
		if (!isNull(fixedCharges)) {
			if (!isNull(fixedCharges.fuelSurcharge)) {
				$("#FuelSurcharges").val(fixedCharges.fuelSurcharge);
				$('#FuelSurchargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.otherCharges)) {
				$("#otherCharges").val(fixedCharges.otherCharges);
				$('#otherChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.airportCharges)) {
				$("#airportCharges").val(fixedCharges.airportCharges);
				$('#airportChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.parcelCharges)) {
				$("#parcelCharges").val(fixedCharges.parcelCharges);
				$('#parcelChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.octroiServiceCharges)) {
				$("#octroiServiceCharges").val(
						fixedCharges.octroiServiceCharges);
				$('#octroiServiceChargesChk').attr('checked', true);
			}

			if (!isNull(fixedCharges.serviceTax)) {
				$("#serviceTax").val(fixedCharges.serviceTax);
				$('#serviceTaxChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.eduCharges)) {
				$("#eduCharges").val(fixedCharges.eduCharges);
				$('#eduChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.higherEduCharges)) {
				$("#higherEduCharges").val(fixedCharges.higherEduCharges);
				$('#higherEduChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.stateTax)) {
				$("#stateTax").val(fixedCharges.stateTax);
				$('#stateTaxChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.surchargeOnST)) {
				$("#surchargeOnST").val(fixedCharges.surchargeOnST);
				$('#surchargeOnSTChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.octroiBourneBy)) {
				// $("#octroiBourneBy").val(fixedCharges.octroiBourneBy);
				document.getElementById("octroiBourneBy").value = fixedCharges.octroiBourneBy;
				$('#octroiBourneByChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.toPayCharges)) {
				$("#toPayCharges").val(fixedCharges.toPayCharges);
				$('#toPayChargesChk').attr('checked', true);
			}
		}
		var codChargesTO = fixedCharges.codChargesTOs;
		if (!isNull(codChargesTO)) {
			codChrgLen = codChargesTO.length;
			totalcodChrgLen = document.getElementById("codRowsCnt").value;
			for ( var j = 0; j < totalcodChrgLen; j++) {
				if (!isNull(document.getElementById("codChargeId" + (j + 1)))) {
					var codChargeId = document.getElementById("codChargeId"
							+ (j + 1)).value;
					for ( var i = 0; i < codChrgLen; i++) {

						if (codChargeId == codChargesTO[i].codChargeTO.codChargeId) {
							document.getElementById("fixedCharges" + (j + 1)).value = codChargesTO[i].fixedChargeValue;
							document.getElementById("codPercent" + (j + 1)).value = codChargesTO[i].percentileValue;
							if (codChargesTO[i].considerHigherFixedOrPercent == "Y") {
								document.getElementById("fcOrCODRadio"
										+ (j + 1)).checked = true;
							}
							if (codChargesTO[i].considerFixed == "Y") {
								document.getElementById("FixedChargesRadio"
										+ (j + 1)).checked = true;
							}
							break;
						}
					}
				}
			}
		}
	}
	hideProcessing();
}

function searchRTOChargesForCourier() {
	if (isValidInput()) {
		showProcessing();
		clearRTOFields();
		getDomElementById("priorityIndicator").value = "N";
		url = './baRateConfiguration.do?submitName=searchRTOChargesForCourier';
		ajaxJquery(url, baForm, callSearchRTOChargesForCourier);
	}
}

function callSearchRTOChargesForCourier(ajaxResp) {
	var baRateHeader = jsonJqueryParser(ajaxResp);
	if (!isNull(baRateHeader)) {
		var rtoCharges = baRateHeader.baCourierRTOChargesTO;
		if (!isNull(rtoCharges)) {
			getDomElementById("sameAsSlabRateChk").disabled = false;
			getDomElementById("Discount").disabled = false;
			if (rtoCharges.sameAsSlabRate == "Y") {
				getDomElementById("Discount").disabled = true;
				getDomElementById("sameAsSlabRateChk").checked = true;
			}
			if (rtoCharges.rtoChargeApplicable == "Y") {
				getDomElementById("RTOChargesApplicableChk").checked = true;
				// document.getElementById("isApplicableForCourier").value =
				// rtoCharges.rtoChargeApplicable;
				getDomElementById("btnCourierRTOSave").disabled = false;
			}
			if (!isNull(rtoCharges.discountOnSlab)) {
				getDomElementById("sameAsSlabRateChk").disabled = true;
				getDomElementById("Discount").value = rtoCharges.discountOnSlab;
			}
			// disableRTOFields();
		}
	}
	hideProcessing();

}

function enableFields(chkObj) {
	if (chkObj.checked) {
		getDomElementById("sameAsSlabRateChk").disabled = false;
		getDomElementById("Discount").disabled = false;
		getDomElementById("sameAsSlabRate").disabled = false;
		// getDomElementById("btnCourierRTOSave").disabled = false;
	} else {
		getDomElementById("sameAsSlabRateChk").disabled = true;
		getDomElementById("sameAsSlabRateChk").checked = false;
		getDomElementById("Discount").disabled = true;
		getDomElementById("Discount").value = "";
		getDomElementById("sameAsSlabRate").disabled = true;
		// getDomElementById("btnCourierRTOSave").disabled = true;

	}
}

function enableDiscountFields(chkObj) {
	if (chkObj.checked) {
		getDomElementById("Discount").value = "";
		getDomElementById("Discount").disabled = true;

	} else {
		getDomElementById("Discount").disabled = false;
	}
}

function searchRateForCourier() {
	if (isValidInput()) {
		showProcessing();
		clearAllGridFields();
		var url = './baRateConfiguration.do?submitName=searchRateForCourier';
		ajaxJquery(url, baForm, callSearchBARateConfiguration);
	}
}

function searchBARateConfigurationForCourier() {
	if (isValidInput()) {
		showProcessing();
		clearAllGridFields();
		var frmDate = document.getElementById('frmDate').value;
		var toDate = document.getElementById('toDate').value;
		// var region = document.getElementById('regionId').value;
		var city = document.getElementById('cityId').value;
		var baType = document.getElementById('baType').value;
		var headerId = document.getElementById('headerId').value;
		var courierProductId = document
				.getElementById('courierProductCategoryId').value;
		var url = './baRateConfiguration.do?submitName=searchBARateConfiguration&fromDate='
				+ frmDate
				+ "&toDate="
				+ toDate
				+ "&city="
				+ city
				+ "&baType="
				+ baType
				+ "&headerId="
				+ headerId
				+ "&courierProductId="
				+ courierProductId;
		ajaxJquery(url, baForm, callSearchBARateConfiguration);
	}
}

function saveOrUpdateRTOChargesForCourier() {
	// if (validateDates() && !isExistsBAConfiguration() && isValidInput()) {
	// if (!isExistsBAConfiguration() && isValidInput()) {
	if (validateDates("save")) {
		enableHeaderBeforeSave();
		getDomElementById("btnCourierRTOSave").disabled = true;
		showProcessing();
		getDomElementById("isPriorityProduct").value = "N";
		var url = './baRateConfiguration.do?submitName=saveOrUpdateRTOChargesForCourier';
		ajaxJquery(url, baForm, callSaveOrUpdateRTOChargesForCourier);
	}
}

/**
 * @param data
 */
function callSaveOrUpdateRTOChargesForCourier(data) {
	if (data != null) {
		var headerTO = jsonJqueryParser(data);
		if (headerTO.isSaved == "saved") {
			getDomElementById("headerId").value = headerTO.headerId;
			getDomElementById("headerStatus").value = headerTO.headerStatus;
			var rateProductList = headerTO.baRateProductTOList;
			for ( var i = 0; i < rateProductList.length; i++) {
				if (rateProductList[i].rateProductCategory == 1) {
					getDomElementById("courierProductHeaderId").value = rateProductList[i].baProductHeaderId;
					break;
				}
				if (rateProductList[i].rateProductCategory == 5) {
					getDomElementById("priorityProductHeaderId").value = rateProductList[i].baProductHeaderId;
					break;
				}
			}

			// Added by Himal
			enableAllTabs();

			alert('Data saved successfully.');
		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	getDomElementById("btnCourierRTOSave").disabled = false;
	hideProcessing();
	disableHeaderAfterSave();
}

function clearRTOFields() {
	getDomElementById("RTOChargesApplicableChk").checked = false;
	getDomElementById("sameAsSlabRateChk").checked = false;
	getDomElementById("Discount").disabled = true;
}

function stateValidation(index, prod) {
	if (prod == "CR") {
		if (isNull(document.getElementById("stateId" + index).value)) {
			document.getElementById("stateId" + index).focus();
			alert("Please Enter state");
			return false;
		}
	} else if (prod == "PR") {
		if (isNull(document.getElementById("pr_stateId" + index).value)) {
			document.getElementById("pr_stateId" + index).focus();
			alert("Please Enter state");
			return false;
		}
	} else if (prod == "AC") {
		if (isNull(document.getElementById("ac_stateId" + index).value)) {
			document.getElementById("ac_stateId" + index).focus();
			alert("Please Enter state");
			return false;
		}
	} else if (prod == "TR") {
		if (isNull(document.getElementById("tr_stateId" + index).value)) {
			document.getElementById("tr_stateId" + index).focus();
			alert("Please Enter state");
			return false;
		}
	}
	return true;
}
function checkCourierFixedCharges() {

	if (document.getElementById("FuelSurchargesChk").checked == true) {
		if (isNull(document.getElementById("FuelSurcharges").value)) {
			document.getElementById("FuelSurcharges").focus();
			alert("Please Enter Fuel Surcharge value");
			return false;
		}
	} else if (document.getElementById("otherChargesChk").checked == true) {
		if (isNull(document.getElementById("otherCharges").value)) {
			document.getElementById("otherCharges").focus();
			alert("Please Enter Other Charges value");
			return false;
		}
	} else if (document.getElementById("parcelChargesChk").checked == true) {
		if (isNull(document.getElementById("parcelCharges").value)) {
			document.getElementById("parcelCharges").focus();
			alert("Please Enter Parcel handling charges value");
			return false;
		}
	} else if (document.getElementById("octroiBourneByChk").checked == true) {
		if (document.getElementById("octroiServiceChargesChk").checked == false) {
			document.getElementById("octroiServiceChargesChk").focus();
			alert("Please Check Octroi service Charges ");
			return false;
		}
	} else if (document.getElementById("octroiServiceChargesChk").checked == true) {
		if (isNull(document.getElementById("octroiServiceCharges").value)) {
			document.getElementById("octroiServiceCharges").focus();
			alert("Please Enter Octroi service Charges value");
			return false;
		}
	} else if (document.getElementById("airportChargesChk").checked == true) {
		if (isNull(document.getElementById("airportCharges").value)) {
			document.getElementById("airportCharges").focus();
			alert("Please Enter Airport handling charges value");
			return false;
		}
	} else if (document.getElementById("toPayChargesChk").checked == true) {
		if (isNull(document.getElementById("toPayCharges").value)) {
			document.getElementById("toPayCharges").focus();
			alert("Please Enter To pay charges value");
			return false;
		}
	}

	if (!validateCODCharges()) {
		return false;
	}

	return true;
}

function validateCODCharges() {
	var codChargeTOs = document.getElementById("codChargeTOs").value;
	var isEmpty = false;
	for ( var i = 1; i <= codChargeTOs.split(",").length; i++) {

		if (isNull(document.getElementById("fixedCharges" + (i)).value)
				&& isNull(document.getElementById("codPercent" + (i)).value)) {
			isEmpty = true;
		} else if (!isNull(document.getElementById("fixedCharges" + (i)).value)
				|| !isNull(document.getElementById("codPercent" + (i)).value)) {
			isEmpty = false;
			break;
		}

	}

	if (isEmpty) {
		alert("Please enter the COD charges for at least one row");
		return false;
	}
	return true;
}
