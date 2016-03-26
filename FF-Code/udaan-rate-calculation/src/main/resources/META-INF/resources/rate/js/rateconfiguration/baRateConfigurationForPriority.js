$(document).ready(function() {
	var oTable = $('#prioritySlabRates').dataTable({
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
	var oTable = $('#prioritySpecialDestination').dataTable({
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"bAutoWidth" : true,
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

function prioritySplDestTable() {
	$('.dataTables_scrollHeadInner,.dataTables_scrollHeadInner table').width(
			"100%");
	$('.dataTables_scroll').width("100%");
}

var rowCountforpr = 1;
var b = new Array();
// var productId = 1;
function addPrioritySpecialDestinationRow() {
	addAdditionalPriorityColumns();
	$('#prioritySpecialDestination').dataTable().fnAddData(b);
	loadStateDropDown("pr_stateId" + rowCountforpr, "", statesList);
	clearDropDownList("pr_cityIds" + rowCountforpr);
	rowCountforpr++;
}
function addPrioritySpecialDestRow(index, prod) {
	if (index == (rowCountforpr - 1) && stateValidation(index, prod)) {
		addAdditionalPriorityColumns();
		$('#prioritySpecialDestination').dataTable().fnAddData(b);
		loadStateDropDown("pr_stateId" + rowCountforpr, "", statesList);
		clearDropDownList("pr_cityIds" + rowCountforpr);
		rowCountforpr++;
	}
}

/**
 * add addition Priority column
 */
function addAdditionalPriorityColumns() {
	b[0] = '<select  id="pr_stateId'
			+ rowCountforpr
			+ '" name="to.baPrioritySlabRateTO.stateId" class="selectBox width155" onchange="getCityByState(this,'
			+ rowCountforpr + ',\'pr_cityIds\');"/>';
	b[1] = '<select id="pr_cityIds'
			+ rowCountforpr
			+ '" name="to.baPrioritySlabRateTO.cityIds" class="selectBox width155" onchange="validateCities(\'PR\','
			+ rowCountforpr + ',\'pr_cityIds\',\'pr_stateId\');"/>';

	var prioritySplDestcoloumCount = document
			.getElementById("prioritySplDestcoloumCount").value;
	prioritySplDestcoloumCount = parseInt(prioritySplDestcoloumCount) + 2;
	for ( var i = 2; i < prioritySplDestcoloumCount; i++) {
		if (i + 1 == prioritySplDestcoloumCount) {
			b[i] = '<input type="text" name = "to.baPrioritySlabRateTO.specialDestinationRate" id="pr_specialDestinationRate'
					+ rowCountforpr
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'PR\','
					+ rowCountforpr
					+ ',\'pr_cityIds\',\'pr_stateId\');" onblur="addPrioritySpecialDestRow('
					+ rowCountforpr
					+ ',\'PR\');"/>'
					+ '<input type="hidden" id="pr_specialDestinationId'
					+ rowCountforpr
					+ i
					+ '" name="to.baPrioritySlabRateTO.specialDestinationId" />';
		} else {
			b[i] = '<input type="text" name = "to.baPrioritySlabRateTO.specialDestinationRate" id="pr_specialDestinationRate'
					+ rowCountforpr
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'PR\','
					+ rowCountforpr
					+ ',\'pr_cityIds\',\'pr_stateId\');"/>'
					+ '<input type="hidden" id="pr_specialDestinationId'
					+ rowCountforpr
					+ i
					+ '" name="to.baPrioritySlabRateTO.specialDestinationId" />';
		}

	}
}

/**
 * @param pinObj
 */
function getPincodeId_PR(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './baRateConfiguration.do?submitName=getPincode&pincode='
				+ pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincodeDetails_PR(req, pinObj);
			}
		});
	}
}
/**
 * @param data
 * @param pinObj
 */
function printCallBackPincodeDetails_PR(data, pinObj) {
	var rowId = getRowId(pinObj, "pr_pincode");
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		pinObj.value = "";
		pinObj.focus();

	} else {
		var req = eval('(' + data + ')');
		document.getElementById("pr_pincodeId" + rowId).value = req.pincodeId;

	}
}

/**
 * @param pinObj
 */
function getCityName_PR(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './baRateConfiguration.do?submitName=getCityName&pincode='
				+ pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackCityDetails_PR(req, pinObj);
			}
		});
	}
}

/**
 * @param data
 * @param pinObj
 */
function printCallBackCityDetails_PR(data, pinObj) {
	var rowId = getRowId(pinObj, "pr_pincode");
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		pinObj.value = "";
		pinObj.focus();
		document.getElementById("pr_city" + rowId).value = "";
	} else {
		var req = eval('(' + data + ')');
		document.getElementById("pr_city" + rowId).value = req.cityName;
		document.getElementById("pr_cityIds" + rowId).value = req.cityId;
	}
}

/**
 * 
 */
function saveOrUpdateSlabsForPriority() {
	headId = getDomElementById("headerId").value;
	var priorityProductId = document
			.getElementById('productCategoryIdForPriority').value;
	if ((!isNull(headId) || (isNull(headId) && validateDates("save")))
			&& isValidInput()
			&& checkRateGridValues(baPrioritySectorList, baPriorityWtSlabList,
					priorityProductId, "PR")
			&& checkSpecialDestRateGridValues("PR")) {
		enableHeaderBeforeSave();

		disEnableSector(baPrioritySectorList, baPriorityWtSlabList,
				priorityProductId, "PR", false, false);

		var servicedOn = getDomElementById("servicedOn").value;
		var isRenewWindow = document.getElementById('isRenewWindow').value;
		if (isNull(servicedOn)) {
			servicedOn = 'B';
		}
		if (isRenewWindow == 'Y') {
			enableHeaderFields();
		}
		showProcessing();
		var url = './baRateConfiguration.do?submitName=saveOrUpdateBARateConfgForPriority';
		ajaxJqueryWithRow(url, baForm,
				callsaveOrUpdateBARateConfigurationForPriority, servicedOn);
	}

}

/**
 * @param ajaxResp
 * @param servicedOn
 */
function callsaveOrUpdateBARateConfigurationForPriority(ajaxResp, servicedOn) {
	if (ajaxResp != null) {
		var headerTO = jsonJqueryParser(ajaxResp);
		if (headerTO.isSaved == 'saved') {
			$("#btnSearch").removeClass('button');
			$("#btnSearch").addClass('button_disable');
			alert('Data saved successfully.');
			callsearchBARateConfigurationForPriority(ajaxResp, servicedOn);
		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	hideProcessing();
	disableHeaderAfterSave();
}

/**
 * 
 */
function searchBARateConfigurationForPriority() {
	if (isValidInput()) {
		showProcessing();
		clearAllGridFields();
		var servicedOn = getDomElementById("servicedOn").value;
		var isRenewWindow = document.getElementById('isRenewWindow').value;
		if (isNull(servicedOn)) {
			servicedOn = 'B';
		}
		if (isRenewWindow == 'Y') {
			enableHeaderFields();
		}
		var url = './baRateConfiguration.do?submitName=searchBARateConfigurationForPriority';
		ajaxJqueryWithRow(url, baForm,
				callsearchBARateConfigurationForPriority, servicedOn);
		if (isRenewWindow == 'Y') {
			disableHeaderFields();
		}
	}
}

/**
 * @param ajaxResp
 * @param servicedOn
 */
function callsearchBARateConfigurationForPriority(ajaxResp, servicedOn) {
	$('#prioritySlabRates :text').val('');
	$('#prioritySpecialDestination :text').val('');
	// $('#prioritySlabRates').find('input').val('');
	// $('#prioritySpecialDestination').find('input').val('');
	var headerTO = jsonJqueryParser(ajaxResp);
	var sectorList = headerTO.prioritySectorsList;
	var weightSlabList = headerTO.priorityWtSlabList;
	var productTOList = headerTO.baRateProductTOList;
	var productId = getDomElementById("productCategoryIdForPriority").value;

	disEnableSector(baPrioritySectorList, baPriorityWtSlabList, productId,
			"PR", true, false);

	getDomElementById("isBAConfigRenew").value = headerTO.isRenew;
	deleteTableRow("prioritySpecialDestination");
	rowCountforpr = 1;
	addPrioritySpecialDestinationRow();
	for ( var i = 0; i < sectorList.length; i++) {
		if (sectorList[i].sectorType == "D"
				&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
			for ( var j = 0; j < weightSlabList.length; j++) {
				if (weightSlabList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
					getDomElementById("slabRateid" + productId
							+ sectorList[i].sectorTO.sectorId
							+ weightSlabList[j].weightSlabTO.weightSlabId).value = "";
					if (!isNull(getDomElementById("rateid" + productId
							+ sectorList[i].sectorTO.sectorId
							+ weightSlabList[j].weightSlabTO.weightSlabId))) {
						getDomElementById("rateid" + productId
								+ sectorList[i].sectorTO.sectorId
								+ weightSlabList[j].weightSlabTO.weightSlabId).value = "";
					}
				}
			}
		}
	}

	if (!isNull(productTOList)) {
		enableTabs("tabsnested1", 3);
		setFlags(headerTO);
		for ( var p = 0; p < productTOList.length; p++) {
			var slabRateTOList = productTOList[p].baSlabRateList;
			getDomElementById("headerId").value = headerTO.headerId;
			for ( var i = 0; i < sectorList.length; i++) {
				if (sectorList[i].sectorType == "D"
						&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
					for ( var j = 0; j < weightSlabList.length; j++) {
						if (weightSlabList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
							for ( var k = 0; k < slabRateTOList.length; k++) {
								if (slabRateTOList[k].destinationSector == sectorList[i].sectorTO.sectorId
										&& slabRateTOList[k].weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId
										&& slabRateTOList[k].servicedOn == servicedOn) {
									getDomElementById("rate"
											+ productId
											+ sectorList[i].sectorTO.sectorId
											+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].rate;
									getDomElementById("slabRateid"
											+ productId
											+ sectorList[i].sectorTO.sectorId
											+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].baSlabRateId;

									getDomElementById("priorityProductHeaderId").value = productTOList[p].baProductHeaderId;
									getDomElementById("isPriorityProductHeaderSaved").value = productTOList[p].isSave;
									break;
								}
							}
						}
					}
				}
			}

			var specialSlabsList = productTOList[p].baSpecialDestinationRateTOList;
			rowCnt = 0;
			var splLen = specialSlabsList.length;
			for ( var k = 0; k < splLen; k++) {
				if (servicedOn == specialSlabsList[k].servicedOn) {
					rowCnt++;
				}
			}

			rowCnt = rowCnt / parseInt(weightSlabList.length);

			if (rowCnt != (rowCountforpr - 2)) {
				for ( var l = 0; l < rowCnt; l++) {
					addPrioritySpecialDestinationRow();
				}
			}
			// deleteSpecialDestPriority();
			l = 0, m = 0;
			var destspecialSlabsList = productTOList[p].baSpecialDestinationRateTOList;

			/*
			 * for ( var k = 0; k < weightSlabList.length; k++) { l =0; for (
			 * var j = 0; j < destspecialSlabsList.length; j++) { if
			 * ((destspecialSlabsList[j].servicedOn == servicedOn) &&
			 * destspecialSlabsList[j].weightSlabId ==
			 * weightSlabList[k].weightSlabTO.weightSlabId &&
			 * weightSlabList[k].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId ==
			 * productId) { if(!isNull(getDomElementById("pr_stateId" + (l +
			 * 1)))){ getDomElementById("pr_stateId" + (l + 1)).value =
			 * destspecialSlabsList[j].stateId; //getDomElementById("pr_city" +
			 * (l + 1)).value = destspecialSlabsList[j].cityTO.cityName;
			 * loadCityDropDown("pr_cityIds" + (l+ 1), "",
			 * destspecialSlabsList[j].cityList); getDomElementById("pr_cityIds" +
			 * (l+ 1)).value = destspecialSlabsList[j].cityTO.cityId; l++; } }
			 * if(l==(rowCnt)){ m++; break; } } } //var splSlabs =
			 * specialSlabsList[i].specialSlabs;
			 * 
			 * m=1;l=0; for ( var j = 0; j < destspecialSlabsList.length; j++) {
			 * for ( var k = 0; k < weightSlabList.length; k++) { if
			 * ((destspecialSlabsList[j].servicedOn == servicedOn) &&
			 * destspecialSlabsList[j].weightSlabId ==
			 * weightSlabList[k].weightSlabTO.weightSlabId &&
			 * weightSlabList[k].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId ==
			 * productId) { for(var r =0;r<rowCnt;r++){
			 * 
			 * if(getDomElementById("pr_stateId" + (r + 1)).value ==
			 * destspecialSlabsList[j].stateId &&
			 * (isNull(destspecialSlabsList[j].cityTO) ||
			 * ((!isNull(destspecialSlabsList[j].cityTO)) &&
			 * (getDomElementById("pr_stateId" + (r + 1)).value ==
			 * destspecialSlabsList[j].cityTO.cityId)))){
			 * getDomElementById("pr_specialDestinationRate" + (r + 1) + (k +
			 * 2)).value = destspecialSlabsList[j].rate;
			 * getDomElementById("pr_specialDestinationId" + (r + 1) + (k +
			 * 2)).value = destspecialSlabsList[j].specialDestinationId; m++; } }
			 *  } }if(m == weightSlabList.length){ l++; } }
			 */

			// #####################
			n = 1;
			l = 0;
			c = 1;
			var isExist = false;

			for ( var r = 1; r <= rowCnt; r++) {

				for ( var k = 0; k < destspecialSlabsList.length; k++) {
					isExist = false;
					if (((isNull(servicedOn) && isNull(destspecialSlabsList[k].servicedOn)) || (!isNull(servicedOn)
							&& !isNull(destspecialSlabsList[k].servicedOn) && (destspecialSlabsList[k].servicedOn == servicedOn)))) {
						if (c == 1) {
							getDomElementById("pr_stateId" + r).value = destspecialSlabsList[k].stateId;

							clearCityDropDownList("pr_cityIds" + r);

							loadCityDropDown("pr_cityIds" + r, "",
									destspecialSlabsList[k].cityList);
							if (!isNull(destspecialSlabsList[k].cityTO)) {
								getDomElementById("pr_cityIds" + r).value = destspecialSlabsList[k].cityTO.cityId;
							}

							c++;
							break;
						} else {

							for ( var b = 1; b < c; b++) {
								if (getDomElementById("pr_stateId" + b).value == destspecialSlabsList[k].stateId
										&& (isNull(destspecialSlabsList[k].cityTO) || ((!isNull(destspecialSlabsList[k].cityTO)) && (getDomElementById("pr_cityIds"
												+ b).value == destspecialSlabsList[k].cityTO.cityId)))) {
									isExist = true;
									break;
								}
							}
							if (!isExist) {
								getDomElementById("pr_stateId" + r).value = destspecialSlabsList[k].stateId;
								clearCityDropDownList("pr_cityIds" + r);
								loadCityDropDown("pr_cityIds" + r, "",
										destspecialSlabsList[k].cityList);
								if (!isNull(destspecialSlabsList[k].cityTO)) {
									getDomElementById("pr_cityIds" + r).value = destspecialSlabsList[k].cityTO.cityId;
								}
								c++;
								break;
							}

						}
					}
				}
			}
			for ( var r = 1; r <= rowCnt; r++) {
				m = 2;
				for ( var j = 0; j < weightSlabList.length; j++) {
					// alert("coWtSlabId"+prodCatId+j);
					state = getDomElementById("pr_stateId" + r).value;
					city = getDomElementById("pr_cityIds" + r).value;

					for ( var k = 0; k < destspecialSlabsList.length; k++) {
						if ((state == destspecialSlabsList[k].stateId)
								&& ((isNull(city) && isNull(destspecialSlabsList[k].cityTO)) || ((!isNull(city) && city == destspecialSlabsList[k].cityTO.cityId)))
								&& (destspecialSlabsList[k].weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId)
								&& ((isNull(servicedOn) && isNull(destspecialSlabsList[k].servicedOn)) || (!isNull(servicedOn)
										&& !isNull(destspecialSlabsList[k].servicedOn) && (destspecialSlabsList[k].servicedOn == servicedOn)))) {
							getDomElementById("pr_specialDestinationRate" + n
									+ m).value = destspecialSlabsList[k].rate;
							getDomElementById("pr_specialDestinationId" + n + m).value = destspecialSlabsList[k].specialDestinationId;
							break;
						}
					}

					m++;
				}

				// addCourierSpecialDestinationRow();
				n++;

				// END FOR LOOP - cols

			}
			// #######################
			/*
			 * if (isNull(getDomElementById("pr_city" + (i + 2)))) {
			 * addPrioritySpecialDestinationRow(); }
			 */

			$("#tabsnested1").tabs("option", "active", 0);
			if (headerTO.headerStatus == 'A') {
				$("#tabs-3 :button").addClass('btnintformbigdis');
				$('#tabs-3').find('input, textarea, button, select').attr(
						'disabled', 'disabled');
				$("#servicedOn").attr('disabled', false);

			}
		}
	}
	getDomElementById("servicedOn").value = servicedOn;
	jQuery.unblockUI();

}

/**
 * 
 */
function searchRTOChargesForPriority() {
	if (isValidInput()) {
		// clearAllGridFields();
		clearPRRTOFields();
		getDomElementById("priorityIndicator").value = "Y";
		url = './baRateConfiguration.do?submitName=searchRTOChargesForPriority';
		ajaxJquery(url, baForm, callSearchRTOChargesForPriority);
	}
}

/**
 * @param ajaxResp
 */
function callSearchRTOChargesForPriority(ajaxResp) {
	var baRateHeader = jsonJqueryParser(ajaxResp);
	if (!isNull(baRateHeader)) {
		var rtoCharges = baRateHeader.baPriorityRTOChargesTO;
		if (!isNull(rtoCharges)) {
			getDomElementById("prsameAsSlabRateChk").disabled = false;
			getDomElementById("prDiscount").disabled = false;
			if (rtoCharges.sameAsSlabRate == "Y") {
				getDomElementById("prDiscount").disabled = true;
				getDomElementById("prsameAsSlabRateChk").checked = true;
			}
			if (rtoCharges.rtoChargeApplicable == "Y") {
				getDomElementById("prRTOChargesApplicableChk").checked = true;
				// document.getElementById("isApplicableForPriority").value =
				// rtoCharges.rtoChargeApplicable;
				getDomElementById("btnPriorityRTOSave").disabled = false;
			}
			if (!isNull(rtoCharges.discountOnSlab)) {
				getDomElementById("prsameAsSlabRateChk").disabled = true;
				getDomElementById("prDiscount").value = rtoCharges.discountOnSlab;
			}
			// disableRTOFields();
		}
	}
}

/**
 * 
 */
function searchFixedChargesForPriority() {
	if (isValidInput()) {
		clearAllGridFields();
		getDomElementById("priorityIndicator").value = "Y";
		url = './baRateConfiguration.do?submitName=searchFixedChargesForPriority';
		ajaxJquery(url, baForm, callSearchFixedChargesForPriority);
	}
}

/**
 * @param ajaxResp
 */
function callSearchFixedChargesForPriority(ajaxResp) {
	var baRateHeader = jsonJqueryParser(ajaxResp);
	if (!isNull(baRateHeader)) {
		var fixedCharges = baRateHeader.baCourierFixedChargesTO;
		if (!isNull(fixedCharges)) {
			if (!isNull(fixedCharges.fuelSurcharge)) {
				$("#pr_FuelSurcharges").val(fixedCharges.fuelSurcharge);
				$('#pr_FuelSurchargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.otherCharges)) {
				$("#pr_otherCharges").val(fixedCharges.otherCharges);
				$('#pr_otherChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.airportCharges)) {
				$("#pr_airportCharges").val(fixedCharges.airportCharges);
				$('#pr_airportChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.parcelCharges)) {
				$("#pr_parcelCharges").val(fixedCharges.parcelCharges);
				$('#pr_parcelChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.octroiServiceCharges)) {
				$("#pr_octroiServiceCharges").val(
						fixedCharges.octroiServiceCharges);
				$('#pr_octroiServiceChargesChk').attr('checked', true);
			}

			if (!isNull(fixedCharges.serviceTax)) {
				$("#pr_serviceTax").val(fixedCharges.serviceTax);
				$('#pr_serviceTaxChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.eduCharges)) {
				$("#pr_eduCharges").val(fixedCharges.eduCharges);
				$('#pr_eduChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.higherEduCharges)) {
				$("#pr_higherEduCharges").val(fixedCharges.higherEduCharges);
				$('#pr_higherEduChargesChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.stateTax)) {
				$("#pr_stateTax").val(fixedCharges.stateTax);
				$('#pr_stateTaxChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.surchargeOnST)) {
				$("#pr_surchargeOnST").val(fixedCharges.surchargeOnST);
				$('#pr_surchargeOnSTChk').attr('checked', true);
			}
			if (!isNull(fixedCharges.octroiBourneBy)) {
				// $("#octroiBourneBy").val(fixedCharges.octroiBourneBy);
				document.getElementById("pr_octroiBourneBy").value = fixedCharges.octroiBourneBy;
				$('#pr_octroiBourneByChk').attr('checked', true);
			}
		}
	}
}

function saveOrUpdateFixedChargesForPriority() {
	// if (validateDates() && isValidInput() && !isExistsBAConfiguration()) {
	if (validateDates("save") && checkPriorityFixedCharges()) {
		enableHeaderBeforeSave();
		getDomElementById("btnPriorityFixedChargeSave").disabled = true;
		showProcessing();
		getDomElementById("isPriorityProduct").value = "Y";
		var url = './baRateConfiguration.do?submitName=saveOrUpdateFixedChargesForPriority';
		ajaxJquery(url, baForm, callSaveOrUpdateFixedChargesForPriority);
	}
}

/**
 * @param data
 */
function callSaveOrUpdateFixedChargesForPriority(data) {
	if (data != null) {
		var headerTO = jsonJqueryParser(data);
		if (headerTO.isSaved == "saved") {
			getDomElementById("headerId").value = headerTO.headerId;
			var rateProductList = headerTO.baRateProductTOList;
			setFlags(headerTO);
			for ( var i = 0; i < rateProductList.length; i++) {
				if (rateProductList[i].rateProductCategory == (getDomElementById("productCategoryIdForCourier").value)) {
					getDomElementById("courierProductHeaderId").value = rateProductList[i].baProductHeaderId;
					break;
				}
				if (rateProductList[i].rateProductCategory == (getDomElementById("productCategoryIdForPriority").value)) {
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
	getDomElementById("btnPriorityFixedChargeSave").disabled = false;
	hideProcessing();
	disableHeaderAfterSave();
}

/**
 * 
 */
function saveOrUpdateRTOChargesForPriority() {
	// if (validateDates() && isValidInput() && !isExistsBAConfiguration()) {
	// if (isValidInput() && !isExistsBAConfiguration()) {
	if (validateDates("save")) {
		enableHeaderBeforeSave();
		getDomElementById("btnPriorityRTOSave").disabled = true;
		showProcessing();
		getDomElementById("isPriorityProduct").value = "Y";
		var url = './baRateConfiguration.do?submitName=saveOrUpdateRTOChargesForPriority';
		ajaxJquery(url, baForm, callSaveOrUpdateRTOChargesForPriority);
	}
}

/**
 * @param data
 */
function callSaveOrUpdateRTOChargesForPriority(data) {
	if (data != null) {
		var headerTO = jsonJqueryParser(data);
		if (headerTO.isSaved == "saved") {
			getDomElementById("headerId").value = headerTO.headerId;
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
			alert('Data saved successfully');
		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	getDomElementById("btnPriorityRTOSave").disabled = false;
	hideProcessing();
	disableHeaderAfterSave();
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdateBARateForPriority(ajaxResp) {
	if (ajaxResp != null) {
		var headerTO = jsonJqueryParser(ajaxResp);
		if (headerTO.isSaved == 'saved') {
			getDomElementById("headerId").value = headerTO.headerId;
			var rateProductList = headerTO.baRateProductTOList;
			for ( var i = 0; i < rateProductList.length; i++) {
				if (rateProductList[i].rateProductCategory == 5) {
					getDomElementById("priorityProductHeaderId").value = rateProductList[i].baProductHeaderId;
					setBARateConfigurationValuesForPriority(headerTO,
							"prioritySpecialDestination",
							"priorityProductHeaderId", rateProductList[i]);
					break;
				}
			}
			alert('Data saved successfully.');

		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	hideProcessing();
}

/**
 * @param headerTO
 * @param tableIdLabel
 * @param productHeaderIdlabel
 * @param productTO
 */
function setBARateConfigurationValuesForPriority(headerTO, tableIdLabel,
		productHeaderIdlabel, productTO) {
	// var headerTO = jsonJqueryParser(ajaxResp);
	$('#' + tableIdLabel + ' :text').val('');
	var productId = document.getElementById(productHeaderIdlabel).value;
	var sectorList = headerTO.sectorsList;
	var weightSlabList = headerTO.wtSlabList;
	if (!isNull(productTO)) {
		var slabRateTOList = productTO.baSlabRateList;
		getDomElementById(productHeaderIdlabel).value = productTO.baProductHeaderId;

		for ( var i = 0; i < sectorList.length; i++) {
			if (sectorList[i].sectorType == "D"
					&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
				for ( var j = 0; j < weightSlabList.length; j++) {
					if (weightSlabList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == productId) {
						for ( var k = 0; k < slabRateTOList.length; k++) {
							if (slabRateTOList[k].destinationSector == sectorList[i].sectorTO.sectorId
									&& slabRateTOList[k].weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId
									&& slabRateTOList[k].servicedOn == servicedOn) {
								getDomElementById("rate"
										+ productId
										+ sectorList[i].sectorTO.sectorId
										+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].rate;
								getDomElementById("slabRateid"
										+ productId
										+ sectorList[i].sectorTO.sectorId
										+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].baSlabRateId;

								getDomElementById("priorityProductHeaderId").value = productTOList[p].baProductHeaderId;
								break;
							}
						}
					}
				}
			}
		}
		// deleteSpecialDestPriority();
		var specialSlabsList = productTO.specialSlabsList;

		for ( var i = 0; i < specialSlabsList.length; i++) {
			getDomElementById("pr_stateId" + (i + 1)).value = specialSlabsList[i].stateId;
			var splSlabs = specialSlabsList[i].specialSlabs;
			for ( var k = 0; k < weightSlabList.length; k++) {
				for ( var j = 0; j < splSlabs.length; j++) {
					if (splSlabs[j].weightSlabId == weightSlabList[k].weightSlabTO.weightSlabId) {
						// getDomElementById("pr_city" + (i + 1)).value =
						// splSlabs[j].cityTO.cityName;
						getDomElementById("pr_cityIds" + (i + 1)).value = splSlabs[j].cityTO.cityId;
						getDomElementById("pr_specialDestinationRate" + (i + 1)
								+ (k + 2)).value = splSlabs[j].rate;
						getDomElementById("pr_specialDestinationId" + (i + 1)
								+ (k + 2)).value = splSlabs[j].specialDestinationId;
						break;
					}
				}
			}
			addCourierSpecialDestinationRow();
		}
		$("#tabsnested1").tabs("option", "active", 0);
	}
	jQuery.unblockUI();
}

/**
 * @param chkObj
 */
function enableFields_PR(chkObj) {
	if (chkObj.checked) {
		getDomElementById("prsameAsSlabRateChk").disabled = false;
		getDomElementById("prDiscount").disabled = false;
		getDomElementById("prsameAsSlabRate").disabled = false;
		// getDomElementById("btnPriorityRTOSave").disabled = false;
	} else {
		getDomElementById("prsameAsSlabRateChk").disabled = true;
		getDomElementById("sameAsSlabRateChk").checked = false;
		getDomElementById("prDiscount").disabled = true;
		getDomElementById("prDiscount").value = "";
		getDomElementById("prsameAsSlabRate").disabled = true;
		// getDomElementById("btnPriorityRTOSave").disabled = true;

	}
}

/**
 * @param chkObj
 */
function enableDiscountFields_PR(chkObj) {
	if (chkObj.checked) {
		getDomElementById("prDiscount").value = "";
		getDomElementById("prDiscount").disabled = true;

	} else {
		getDomElementById("prDiscount").disabled = false;
	}
}

/**
 * 
 */
function deleteSpecialDestPriority() {
	var table = getDomElementById("prioritySpecialDestination");
	var tableRowCount = table.rows.length;
	if (tableRowCount != 2) {
		for ( var i = 0; i < tableRowCount - 1; i++) {
			deleteRow("prioritySpecialDestination", i - 1);
		}
		rowCountforpr = 1;
		addPrioritySpecialDestinationRow();
	}

}

/**
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function clearPRRTOFields() {
	getDomElementById("prRTOChargesApplicableChk").checked = false;
	getDomElementById("prsameAsSlabRateChk").checked = false;
	getDomElementById("prDiscount").disabled = true;
}

function checkPriorityFixedCharges() {

	if (document.getElementById("pr_FuelSurchargesChk").checked == true) {
		if (isNull(document.getElementById("pr_FuelSurcharges").value)) {
			document.getElementById("pr_FuelSurcharges").focus();
			alert("Please Enter Fuel Surcharge value");
			return false;
		}
	} else if (document.getElementById("pr_otherChargesChk").checked == true) {
		if (isNull(document.getElementById("pr_otherCharges").value)) {
			document.getElementById("pr_otherCharges").focus();
			alert("Please Enter Other Charges value");
			return false;
		}
	} else if (document.getElementById("pr_parcelChargesChk").checked == true) {
		if (isNull(document.getElementById("pr_parcelCharges").value)) {
			document.getElementById("pr_parcelCharges").focus();
			alert("Please Enter Parcel handling charges value");
			return false;
		}
	} else if (document.getElementById("pr_octroiBourneByChk").checked == true) {
		if (document.getElementById("pr_octroiServiceChargesChk").checked == false) {
			alert("Please Check Octroi service Charges ");
			document.getElementById("octroiServiceChargesChk").focus();
			return false;
		}
	} else if (document.getElementById("pr_octroiServiceChargesChk").checked == true) {
		if (isNull(document.getElementById("pr_octroiServiceCharges").value)) {
			document.getElementById("pr_octroiServiceCharges").focus();
			alert("Please Enter Octroi service Charges value");
			return false;
		}
	} else if (document.getElementById("pr_airportChargesChk").checked == true) {
		if (isNull(document.getElementById("pr_airportCharges").value)) {
			document.getElementById("pr_airportCharges").focus();
			alert("Please Enter Airport handling charges value");
			return false;
		}
	}

	return true;
}