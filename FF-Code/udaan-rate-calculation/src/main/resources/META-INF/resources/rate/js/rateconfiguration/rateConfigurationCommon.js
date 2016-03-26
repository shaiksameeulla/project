var baForm = "baRateConfigurationForm";
var baCourierSectorList;
var baCourierWtSlabList;
var baPrioritySectorList;
var baPriorityWtSlabList;
var sectorCode;
var statesList;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
function scrollDataTableWidth() {
	$('.dataTables_scroll').width("100%");
}
/**
 * Get all cities
 */
function getAllCities() {
	var regionId = "";
	var content = document.getElementById('cityId');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	document.getElementById('cityId').value = "";
	document.getElementById('baType').value = "";
	regionId = document.getElementById("regionId").value;
	url = './baRateConfiguration.do?submitName=getCitiesByRegion&regionId='
			+ regionId;
	ajaxCallWithoutForm(url, printAllCities);
}

/**
 * @param data
 */
function printAllCities(data) {
	var content = document.getElementById('cityId');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.cityId;
		option.appendChild(document.createTextNode(this.cityName));
		content.appendChild(option);
	});

}

function saveOrUpdateBARate() {
	headId = getDomElementById("headerId").value;
	var courierProductId = document.getElementById('courierProductCategoryId').value;
	// if ((!isNull(headId) || (isNull(headId) && validateDates("save"))) &&
	// !isExistsBAConfiguration() && isValidInput()) {
	// if (!isExistsBAConfiguration() && isValidInput()) {
	if ((!isNull(headId) || (isNull(headId) && validateDates("save")))
			&& isValidInput()
			&& checkRateGridValues(baCourierSectorList, baCourierWtSlabList,
					courierProductId, "CO")
			&& checkSpecialDestRateGridValues("CO")) {
		enableHeaderBeforeSave();

		disEnableSector(baCourierSectorList, baCourierWtSlabList,
				courierProductId, "CO", false, false);
		showProcessing();
		var url = './baRateConfiguration.do?submitName=saveOrUpdateBARate';
		ajaxJquery(url, baForm, callSaveOrUpdateBARate);
	}
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdateBARate(ajaxResp) {
	if (ajaxResp != null) {
		var headerTO = jsonJqueryParser(ajaxResp);
		if (headerTO.isSaved == 'saved') {
			getDomElementById("headerId").value = headerTO.headerId;
			getDomElementById("headerStatus").value = headerTO.headerStatus;
			$("#btnSearch").removeClass('button');
			$("#btnSearch").addClass('button_disable');
			var rateProductList = headerTO.baRateProductTOList;
			for ( var i = 0; i < rateProductList.length; i++) {
				if (rateProductList[i].rateProductCategory == (getDomElementById("productCategoryIdForCourier").value)) {
					getDomElementById("courierProductHeaderId").value = rateProductList[i].baProductHeaderId;
					callSearchBARateConfiguration(ajaxResp);
					break;
				}
			}
			alert('Data saved successfully.');

		}
	} else {
		alert('Exception Occured.Data Not saved');
	}
	hideProcessing();
	disableHeaderAfterSave();
}

/**
 * Search Ba rate configuration
 */
function searchBARateConfiguration() {
	if (isValidInput()) {
		showProcessing();
		document.getElementById('headerId').value = "";
		//document.getElementById('oldHeaderId').value = "";

		var frmDate = document.getElementById('frmDate').value;
		var toDate = document.getElementById('toDate').value;
		document.getElementById('frmDate').value = "";
		document.getElementById('toDate').value = "";

		// var region = document.getElementById('regionId').value;
		var city = document.getElementById('cityId').value;
		var baType = document.getElementById('baType').value;
		var headerId = document.getElementById('headerId').value;
		var courierProductId = document
				.getElementById('courierProductCategoryId').value;
		var priorityProductId = document
				.getElementById('productCategoryIdForPriority').value;
		var regionId = document.getElementById("regionId").value;

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
				+ courierProductId
				+ "&priorityProductId="
				+ priorityProductId
				+ "&regionId=" + regionId;
		ajaxJquery(url, baForm, callSearchBARateConfiguration);	

	}
}

/**
 * To set search result to respective fields
 * 
 * @param ajaxResp
 */
function callSearchBARateConfiguration(ajaxResp) {
	var headerId = document.getElementById('headerId').value;
	var isRenewWindow = document.getElementById('isRenewWindow').value;
	enableTabs("tabs", 2);
	enableTabs("tabsnested", 3);
	enableTabs("tabsnested1", 1);

	btnEnable('btnCOSlabsSave');
	btnEnable('btnCOSlabsCancel');
	btnEnable('btnSubmit');
	btnEnable('cancelBaRateConfiguration');
	var flag = true;
	deleteTableRow("courierSpecialDestination");
	rowCount = 1;
	addCourierSpecialDestinationRow();
	if (isRenewWindow == 'Y') {
		$("#btnSearch").removeClass('button');
		$("#btnSearch").addClass('button_disable');
		// $("#btnSearch").attr('disabled', true);
		if (isNull(ajaxResp)) {
			alert("BA Configuration Not found for given city.");
			flag = false;
		}
	} else {
		if (isNull(ajaxResp) && isNull(headerId)) {
			alert("BA Configuration Not found for given city.");
			flag = false;
		}
	}

	if (flag == true) {
		var headerTO = jsonJqueryParser(ajaxResp);
		if (!isNull(document.getElementById('sectorCode').value)) {
			document.getElementById('sectorCode').value = headerTO.sectorCode;
			sectorCode = headerTO.sectorCode;
		}
		baCourierSectorList = headerTO.sectorsList;
		baCourierWtSlabList = headerTO.wtSlabList;
		baPriorityWtSlabList = headerTO.priorityWtSlabList;
		baPrioritySectorList = headerTO.prioritySectorsList;
		sectorCode = headerTO.sectorCode;
		var courierProductId = document
				.getElementById('courierProductCategoryId').value;
		disEnableSector(baCourierSectorList, baCourierWtSlabList,
				courierProductId, "CO", true, false);
		if (isNull(headerTO.headerId) && isRenewWindow != 'Y') {
			alert("BA Configuration Not found for given city.");
		}
		// else if (isRenewWindow != 'Y'){
		else {
			$("#btnSearch").removeClass('button');
			$("#btnSearch").addClass('button_disable');

			// disableHeaderFields();
			$('#courierSpecialDestination :text').val('');
			var courierProdductId = document
					.getElementById('courierProductCategoryId').value;
			var sectorList = headerTO.sectorsList;
			var weightSlabList = headerTO.wtSlabList;
			var productTOList = headerTO.baRateProductTOList;
			document.getElementById('frmDate').value = headerTO.frmDate;
			document.getElementById('toDate').value = headerTO.toDate;
			getDomElementById("isBAConfigRenew").value = headerTO.isRenew;

			if (!isNull(productTOList)) {
				var slabRateTOList = productTOList[0].baSlabRateList;
				getDomElementById("headerId").value = headerTO.headerId;
				getDomElementById("courierProductHeaderId").value = productTOList[0].baProductHeaderId;
				getDomElementById("headerStatus").value = headerTO.headerStatus;
				getDomElementById("isCourierProductHeaderSaved").value = productTOList[0].isSave;
				getDomElementById("courierRatesCheck").value = headerTO.courierRatesCheck;
				enableTabs("tabsnested", 5);
				setFlags(headerTO);
				for ( var i = 0; i < sectorList.length; i++) {
					if (sectorList[i].sectorType == "D"
							&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == courierProdductId) {
						for ( var j = 0; j < weightSlabList.length; j++) {
							if (weightSlabList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == courierProdductId) {
								for ( var k = 0; k < slabRateTOList.length; k++) {
									if (slabRateTOList[k].destinationSector == sectorList[i].sectorTO.sectorId
											&& slabRateTOList[k].weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId) {
										getDomElementById("rate"
												+ courierProdductId
												+ sectorList[i].sectorTO.sectorId
												+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].rate;
										getDomElementById("slabRateid"
												+ courierProdductId
												+ sectorList[i].sectorTO.sectorId
												+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].baSlabRateId;
										break;
									}
								}
							}
						}
					}
				}

				var specialSlabsList = productTOList[0].baSpecialDestinationRateTOList;

				var splRateLen = 0;
				// var cnt = 0;
				var lenSlab = weightSlabList.length;

				splRateLen = specialSlabsList.length / lenSlab;
				n = 1;

				/*
				 * for ( var i = 0; i < specialSlabsList.length; i++) {
				 * getDomElementById("stateId" + (i + 1)).value =
				 * specialSlabsList[i].stateId; //var splSlabs =
				 * specialSlabsList[i].specialSlabs; for ( var k = 0; k <
				 * weightSlabList.length; k++) { for ( var j = i; j <
				 * specialSlabsList.length; j++) { if (splSlabs[j].weightSlabId ==
				 * weightSlabList[k].weightSlabTO.weightSlabId) {
				 * //getDomElementById("city" + (i + 1)).value =
				 * splSlabs[j].cityTO.cityName; getDomElementById("cityIds" + (i +
				 * 1)).value = splSlabs[j].cityTO.cityId;
				 * getDomElementById("specialDestinationRate" + (i + 1) + (k +
				 * 2)).value = splSlabs[j].rate;
				 * getDomElementById("specialDestinationId" + (i + 1) + (k +
				 * 2)).value = splSlabs[j].specialDestinationId; break; } } } if
				 * (isNull(getDomElementById("cityIds" + (i + 2)))) {
				 * addCourierSpecialDestinationRow(); } }
				 */

				/*
				 * for(var i = 0; i< splRateLen ; i++){ //addRow(gridId);
				 * getDomElementById("stateId"+n).value =
				 * specialSlabsList[i].stateId;
				 * 
				 * clearCityDropDownList("cityIds"+n);
				 * 
				 * loadCityDropDown("cityIds"+n, "",
				 * specialSlabsList[i].cityList);
				 * if(!isNull(specialSlabsList[i].cityTO)){
				 * getDomElementById("cityIds"+n).value =
				 * specialSlabsList[i].cityTO.cityId; } m = 2; for(var j = 0; j<
				 * lenSlab ; j++){ for(var k=0;k<specialSlabsList.length;k++){
				 * if((specialSlabsList[i].stateId ==
				 * specialSlabsList[k].stateId) &&
				 * (specialSlabsList[k].weightSlabId ==
				 * weightSlabList[j].weightSlabTO.weightSlabId)){
				 * getDomElementById("specialDestinationRate"+n+m).value =
				 * specialSlabsList[k].rate;
				 * getDomElementById("specialDestinationId"+n+m).value =
				 * specialSlabsList[k].specialDestinationId; break; } } m++; }
				 * addCourierSpecialDestinationRow(); n++; }
				 */

				n = 1;
				l = 0;
				c = 1;
				var isExist = false;

				for ( var r = 1; r <= splRateLen; r++) {

					if (splRateLen != (rowCount - 2)) {
						addCourierSpecialDestinationRow();
					}
					for ( var k = 0; k < specialSlabsList.length; k++) {
						isExist = false;

						if (c == 1) {
							getDomElementById("stateId" + r).value = specialSlabsList[k].stateId;

							clearCityDropDownList("cityIds" + r);

							loadCityDropDown("cityIds" + r, "",
									specialSlabsList[k].cityList);
							if (!isNull(specialSlabsList[k].cityTO)) {
								getDomElementById("cityIds" + r).value = specialSlabsList[k].cityTO.cityId;
							}

							c++;
							break;
						} else {

							for ( var b = 1; b < c; b++) {
								if (getDomElementById("stateId" + b).value == specialSlabsList[k].stateId
										&& (isNull(specialSlabsList[k].cityTO) || ((!isNull(specialSlabsList[k].cityTO)) && (getDomElementById("cityIds"
												+ b).value == specialSlabsList[k].cityTO.cityId)))) {
									isExist = true;
									break;
								}
							}
							if (!isExist) {
								getDomElementById("stateId" + r).value = specialSlabsList[k].stateId;
								clearCityDropDownList("cityIds" + r);
								loadCityDropDown("cityIds" + r, "",
										specialSlabsList[k].cityList);
								if (!isNull(specialSlabsList[k].cityTO)) {
									getDomElementById("cityIds" + r).value = specialSlabsList[k].cityTO.cityId;
								}
								c++;
								break;
							}

						}

					}
				}
				for ( var r = 1; r <= splRateLen; r++) {
					m = 2;
					for ( var j = 0; j < weightSlabList.length; j++) {
						// alert("coWtSlabId"+prodCatId+j);
						state = getDomElementById("stateId" + r).value;
						city = getDomElementById("cityIds" + r).value;

						for ( var k = 0; k < specialSlabsList.length; k++) {
							if ((state == specialSlabsList[k].stateId)
									&& ((isNull(city) && isNull(specialSlabsList[k].cityTO)) || ((!isNull(city) && (!isNull(specialSlabsList[k].cityTO)) && city == specialSlabsList[k].cityTO.cityId)))
									&& (specialSlabsList[k].weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId)) {
								getDomElementById("specialDestinationRate" + n
										+ m).value = specialSlabsList[k].rate;
								getDomElementById("specialDestinationId" + n
										+ m).value = specialSlabsList[k].specialDestinationId;
								break;
							}
						}

						m++;
					}

					n++;

					// END FOR LOOP - cols

				}

				$("#tabsnested").tabs("option", "active", 0);
				if (headerTO.headerStatus == 'A') {
					$("#tabs-2 :button").addClass('btnintformbigdis');
					$("#btnSubmit").addClass('btnintformbigdis');
					$('#tabs-2').find('input, textarea, button, select').attr(
							'disabled', 'disabled');
					$("#servicedOn").attr('disabled', false);
				}
				var renewCheck = checkRenew();
				if (headerTO.isRenew == 'Y' && renewCheck == true) {
					$("#btnRenew").removeClass('button_disable');
					$("#btnRenew").addClass('button');
					$("#btnRenew").attr('disabled', false);
					getDomElementById("isBAConfigRenew").value = headerTO.isRenew;
				} else if (renewCheck == true) {
					$("#btnRenew").removeClass('button_disable');
					$("#btnRenew").addClass('button');
					$("#btnRenew").attr('disabled', false);
				}
				if (headerTO.headerStatus == 'I') {
					$("#btnRenew").addClass('button_disable');
					$("#btnRenew").attr('disabled', 'disabled');
				}
				$('.dataTables_scroll').width("100%");
			}
		}
	}

	jQuery.unblockUI();
}

/**
 * @returns {Boolean}
 */
function isValidInput() {
	// var frmDate = document.getElementById('frmDate').value;
	// var toDate = document.getElementById('toDate').value;
	var region = document.getElementById('regionId').value;
	var city = document.getElementById('cityId').value;
	var baType = document.getElementById('baType').value;
	if (isNull(region)) {
		alert("Please Select Region.");
		document.getElementById("regionId").focus();
		return false;
	}
	if (isNull(city)) {
		alert("Please Select City.");
		document.getElementById("cityId").focus();
		return false;
	}
	if (isNull(baType)) {
		alert("Please Select BA Type.");
		document.getElementById("baType").focus();
		return false;
	}
	return true;
}

/**
 * Add default rows for special destination
 */
function addDefaultRows() {
	addCourierSpecialDestinationRow();
	addAirCargoSpecialDestinationRow();
	addTrainSpecialDestinationRow();
	addPrioritySpecialDestinationRow();

}

/**
 * Submit BA rate configuration Details
 */
function submitBaRateConfiguration() {

	var courierRatesCheck = jQuery("#courierRatesCheck").val();
	var airCargoRatesCheck = jQuery("#airCargoRatesCheck").val();
	var trainRatesCheck = jQuery("#trainRatesCheck").val();
	var courierChargesCheck = jQuery("#courierChargesCheck").val();
	// var courierRTOCheck = jQuery("#courierRTOCheck").val();

	var priorityARatesCheck = jQuery("#priorityARatesCheck").val();
	var priorityBRatesCheck = jQuery("#priorityBRatesCheck").val();
	var prioritySRatesCheck = jQuery("#prioritySRatesCheck").val();
	var priorityChargesCheck = jQuery("#priorityChargesCheck").val();
	// var priorityRTOCheck = jQuery("#priorityRTOCheck").val();

	if (courierRatesCheck != "Y") {
		alert("Please Save Courier Rates Details.");
		return false;
	} else if (airCargoRatesCheck != "Y") {
		alert("Please Save Air Cargo Rates Details.");
		return false;
	} else if (trainRatesCheck != "Y") {
		alert("Please Save Train Rates Details.");
		return false;
	} else if (priorityBRatesCheck != "Y") {
		alert("Please Save Before 14 Hrs Priority Rates Details.");
		return false;
	} else if (priorityARatesCheck != "Y") {
		alert("Please Save After 14 Hrs Priority Rates Details.");
		return false;
	} else if (prioritySRatesCheck != "Y") {
		alert("Please Save Sunday Priority Rates Details.");
		return false;
	} else if (courierChargesCheck != "Y") {
		alert("Please Save Non-Priority Fixed Charges Details.");
		return false;
	} else if (priorityChargesCheck != "Y") {
		alert("Please Save Priority Fixed Charges Details.");
		return false;
	}
	if (validateDates("submit")) {
		getDomElementById("btnSubmit").disabled = true;
		var url = './baRateConfiguration.do?submitName=submitBaRateConfiguration';
		ajaxJquery(url, baForm, callSubmitBARateConfiguration);
	}
}

/**
 * @param ajaxResp
 */
function callSubmitBARateConfiguration(ajaxResp) {
	if (!isNull(ajaxResp)) {
		// var isSubmited = jsonJqueryParser(ajaxResp);
		if (ajaxResp == "Yes") {
			alert('Data Submitted successfully.');
			// disableAll();
			$("#tabs-2 :button").addClass('btnintformbigdis');
			$("#btnSubmit").addClass('btnintformbigdis');
			$('#tabs-2').find('input, textarea, button, select').attr(
					'disabled', 'disabled');
			$("#servicedOn").attr('disabled', false);

			var renewCheck = checkRenew();
			if (headerTO.isRenew == 'Y' && renewCheck == true) {
				$("#btnRenew").removeClass('button_disable');
				$("#btnRenew").addClass('button');
				$("#btnRenew").attr('disabled', false);
				getDomElementById("isBAConfigRenew").value = headerTO.isRenew;
			} else if (renewCheck == true) {
				$("#btnRenew").removeClass('button_disable');
				$("#btnRenew").addClass('button');
				$("#btnRenew").attr('disabled', false);
			}
		} else {
			getDomElementById("btnSubmit").disabled = false;
			alert('Exception Occured.Data Not Submitted');
		}
	}
	else{
		getDomElementById("btnSubmit").disabled = false;
	}
}

// Popup window code
/**
 * To renew BA rate Configuration
 */
function renewBAConfiguration() {
	flag = confirm("Do you want to Renew Configuration");
	if (!flag) {
		return;
	}
	showProcessing();
	getDomElementById("btnRenew").disabled = true;
	var headerId = getDomElementById("headerId").value;
	var toDate = document.getElementById('toDate').value;
	var fromDate = document.getElementById('frmDate').value;
	var city = document.getElementById('cityId').value;
	var baType = document.getElementById('baType').value;
	var region = document.getElementById('regionId').value;

	var url = './baRateConfiguration.do?submitName=renewBARateConfiguration&headerId='
			+ headerId
			+ "&fromDate="
			+ fromDate
			+ "&toDate="
			+ toDate
			+ "&city="
			+ city
			+ "&region="
			+ region
			+ "&baType="
			+ baType
			+ "&isRenewWindow=Y";
	popupWindow = window
			.open(
					url,
					'popUpWindow',
					'resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');

	popupWindow.onbeforeunload = function() {
		hideProcessing();
		getDomElementById("btnRenew").disabled = false;
	};
}

/**
 * Load Default values for BA rate configuration
 */
function loadDefaultValues() {
	addDefaultRows();
	$('.dataTables_scroll').width("100%");
	var isRenewWindow = document.getElementById('isRenewWindow').value;
	if (isRenewWindow == 'Y') {
		loadDefaultValueForRenew();
		searchRenewedContract();
	}
}

function searchRenewedContract() {
	var baType = document.getElementById('baType').value;
	var city = document.getElementById('cityId').value;
	var courierProductId = document.getElementById('courierProductCategoryId').value;
	var priorityProductId = document.getElementById('productCategoryIdForPriority').value;
	var oldtoDate = document.getElementById('oldtoDate').value;
	var regionId = document.getElementById("regionId").value;
	var headerId = document.getElementById("oldHeaderId").value;

	var url = './baRateConfiguration.do?submitName=searchRenewedBARateConfiguration&city='
			+ city
			+ "&baType="
			+ baType
			+ "&courierProductId="
			+ courierProductId
			+ "&priorityProductId="
			+ priorityProductId
			+ "&regionId="
			+ regionId
			+ "&toDate="
			+ oldtoDate
			+ "&headerId="
			+ headerId
			+ "&productCode=CO";
	//ajaxJquery(url, baForm, callSearchBARateConfiguration);
	ajaxJquery(url, baForm, callSearchBARatesDtls);
	
}

/**
 * Set header values for renew contract
 */
function loadDefaultValueForRenew() {
	var oldbaType = document.getElementById('oldbaType').value;
	
	// var oldfromDate = document.getElementById('oldfromDate').value;
	// var oldtoDate = document.getElementById('oldtoDate').value;
	var oldCity = document.getElementById('oldCity').value;
	var oldRegion = document.getElementById('oldRegion').value;
	document.getElementById("regionId").value = oldRegion;
	getAllCitiesForRenew();
	document.getElementById('cityId').value = oldCity;
	document.getElementById('baType').value = oldbaType;
	disableHeaderFields();
}

/**
 * Disabled Header fields
 */
function disableHeaderFields() {
	document.getElementById("regionId").disabled = true;
	document.getElementById("cityId").disabled = true;
	document.getElementById("baType").disabled = true;
}

/**
 * Enable header fields
 */
function enableHeaderFields() {
	document.getElementById("regionId").disabled = false;
	document.getElementById("cityId").disabled = false;
	document.getElementById("baType").disabled = false;
}

function getAllCitiesForRenew() {
	var regionId = "";
	var content = document.getElementById('cityId');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	document.getElementById('cityId').value = "";
	document.getElementById('baType').value = "";
	regionId = document.getElementById("regionId").value;
	url = './baRateConfiguration.do?submitName=getCitiesByRegion&regionId='
			+ regionId;
	ajaxCallWithoutFormWithAsyn(url, printAllCities);
}

function ajaxCallWithoutFormWithAsyn(pageurl, ajaxResponse) {
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		async : false,
		success : function(data) {
			ajaxResponse(data);
		}
	});
}

/**
 * To validate EFFECTIVE FROM DATE
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateFromDate(obj) {
	var isValid = false;
	if (!isNull(obj.value)) {
		var todayDate = TODAY_DATE;
		var i = compareDates(todayDate, obj.value);
		if (i == undefined || i == 0 || i == 1) {
			alert("From date should be greater than today date.");
			setTimeout(function() {
				obj.focus();
			}, 10);
			obj.value = "";
			return false;
		}
		isValid = validateToDate(getDomElementById("toDate"));
		return isValid;
	} else {
		alert("Please Select From date.");
		return false;
	}
}

/**
 * To validate VALID TO DATE
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateToDate(obj) {
	if (!isNull(obj.value)) {
		var fromDate = getDomElementById("frmDate").value;
		var i = compareDates(fromDate, obj.value);
		if (i == undefined || i == 0 || i == 1) {
			alert("To Date should be greater than from date.");
			setTimeout(function() {
				obj.focus();
			}, 10);
			obj.value = "";
			return false;
		}
		return true;
	} else {
		alert("Please Select to date.");
		return false;
	}
}

/**
 * To Validate From Date before setting Valid To Date
 * 
 * @param toDtId
 * @param toDtValue
 */
function validateFromDtToSetToDt(toDtId, toDtValue) {
	var fromDate = getDomElementById("frmDate");
	if (!isNull(fromDate.value)) {
		show_calendar(toDtId, toDtValue);
	} else {
		alert("Please first select From Date");
		setTimeout(function() {
			fromDate.focus();
		}, 10);
		return false;
	}
}

function isExistsBAConfiguration() {
	var city = document.getElementById('cityId').value;
	var baType = document.getElementById('baType').value;
	var url = './baRateConfiguration.do?submitName=isExistsBaRateConfiguration&city='
			+ city + "&baType=" + baType;
	var result = false;
	$.ajax({
		url : url,
		type : "POST",
		dataType : "text",
		data : jQuery("#" + baForm).serialize(),
		async : false,
		success : function(rsp) {
			result = callIsExistsBaRateConfiguration(rsp);
		},
		error : function(rsp) {
			result = callIsExistsBaRateConfiguration(null);
		}
	});

	return result;

}

function callIsExistsBaRateConfiguration(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var headerId = getDomElementById("headerId").value;
		var isRenewWindow = document.getElementById('isRenewWindow').value;
		if (isNull(headerId) && ajaxResp == 'Yes' && isRenewWindow != 'Y') {
			alert("BA Rate Configuration already Exists for given city");
			return true;
		}
	}
	return false;
}

function validateDates(action) {
	var fromDate = getDomElementById("frmDate").value;
	var todate = getDomElementById("toDate").value;

	if (isNull(fromDate)) {
		alert("Please select From date");
		return false;
	} else if (isNull(todate)) {
		alert("Please select To date");
		return false;
	}

	if (action == "save") {
		var isFromDateValid = checkDates(fromDate, null, action);

		if (!isFromDateValid) {
			alert("From date should be greater than or equal to Present date");
			return false;
		} else {
			var isTODateValid = checkDates(todate, fromDate, action);
			if (!isTODateValid) {
				alert("To date should be greater than From date");
				return false;
			}
		}
	} else if (action == "submit") {
		var isFromDateValid = checkDates(fromDate, null, action);

		if (!isFromDateValid) {
			alert("From date should be greater than Present date");
			return false;
		} else {
			var isTODateValid = checkDates(todate, fromDate, "save");
			if (!isTODateValid) {
				alert("To date should be greater than From date");
				return false;
			}
		}
	}
	return true;
}

function validateFormat(obj) {
	val = obj.value;
	if (!isNull(val)) {
		if (val.indexOf(".") > 0 || val.length <= 3) {
			var parts = val.split(".");

			if (typeof parts[0] == "string"
					&& (parseInt(parts[0], 10) < 0 && parseInt(parts[0], 10) >= 1000)) {
				alert("Pelase enter valid Rate value.");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}

			if (parseInt(parts[1], 10) < 0 && parseInt(parts[1], 10) >= 100) {
				alert("Please enter valid Rate value.");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		} else {
			alert("Please enter the Rate value in 999.99 format");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}

	}
	return true;
}

function enableHeaderBeforeSave() {
	var isRenewWindow = document.getElementById('isRenewWindow').value;
	if (isRenewWindow == 'Y') {
		enableHeaderFields();
	}
}

function disableHeaderAfterSave() {
	var isRenewWindow = document.getElementById('isRenewWindow').value;
	if (isRenewWindow == 'Y') {
		disableHeaderFields();
	}
}

function clearAllGridFields() {
	var pr_ifInsuredByFF = document.getElementById('pr_ifInsuredByFF').value;
	var pr_ifInsuredByCustomer = document
			.getElementById('pr_ifInsuredByCustomer').value;
	var ifInsuredByFF = document.getElementById('ifInsuredByFF').value;
	var ifInsuredByCustomer = document.getElementById('ifInsuredByCustomer').value;

	// $('#tabs-11,#tabs-14').find('input').val("");
	$('#tabs-11,#tabs-14').find('input:checkbox').attr('checked', false);

	document.getElementById('pr_ifInsuredByFF').value = pr_ifInsuredByFF;
	document.getElementById('pr_ifInsuredByCustomer').value = pr_ifInsuredByCustomer;
	document.getElementById('ifInsuredByFF').value = ifInsuredByFF;
	document.getElementById('ifInsuredByCustomer').value = ifInsuredByCustomer;
}

function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function deleteTableRow(tableId) {
	// $("#example4 > tbody > tr").remove();
	// $("#example4 > tbody").empty();
	try {
		var table = getDomElementById(tableId);

		var tableRowCount = table.rows.length;
		for ( var i = 0; i < tableRowCount; i++) {
			deleteRow(tableId, i);
			tableRowCount--;
			i--;
		}
	} catch (e) {
		alert(e);
	}
}

/**
 * To setting flags
 * 
 * @param data
 */
function setFlags(data) {
	// Courier
	if (data.courierRatesCheck == 'Y') {
		jQuery("#courierRatesCheck").val(data.courierRatesCheck);
	}
	// Air-Cargo
	if (data.airCargoRatesCheck == 'Y') {
		jQuery("#airCargoRatesCheck").val(data.airCargoRatesCheck);
	}
	// Train
	if (data.trainRatesCheck == 'Y') {
		jQuery("#trainRatesCheck").val(data.trainRatesCheck);
	}
	// Priority - A (After 14.00)
	if (data.priorityARatesCheck == 'Y') {
		jQuery("#priorityARatesCheck").val(data.priorityARatesCheck);
	}
	// Priority - B (Before 14.00)
	if (data.priorityBRatesCheck == 'Y') {
		jQuery("#priorityBRatesCheck").val(data.priorityBRatesCheck);
	}
	// Priority - S (Sunday)
	if (data.prioritySRatesCheck == 'Y') {
		jQuery("#prioritySRatesCheck").val(data.prioritySRatesCheck);
	}
	// Fixed Charges - Non-priority
	if (data.courierChargesCheck == 'Y') {
		jQuery("#courierChargesCheck").val(data.courierChargesCheck);
	}
	// Fixed Charges - Priority
	if (data.priorityChargesCheck == 'Y') {
		jQuery("#priorityChargesCheck").val(data.priorityChargesCheck);
	}
	// RTO - Non-priority
	if (data.courierRTOCheck == 'Y') {
		jQuery("#courierRTOCheck").val(data.courierRTOCheck);
	}
	// RTO - Priority
	if (data.priorityRTOCheck == 'Y') {
		jQuery("#priorityRTOCheck").val(data.priorityRTOCheck);
	}
}

function checkDates(fromDate, todate, operation) {
	var date2;
	var dt1 = parseInt(fromDate.substring(0, 2), 10);
	var mon1 = parseInt(fromDate.substring(3, 5), 10);
	var yr1 = parseInt(fromDate.substring(6, 10), 10);

	var date1 = new Date(yr1, (mon1 - 1), dt1);
	if (!isNull(todate)) {
		var dt2 = parseInt(todate.substring(0, 2), 10);
		var mon2 = parseInt(todate.substring(3, 5), 10);
		var yr2 = parseInt(todate.substring(6, 10), 10);
		date2 = new Date(yr2, (mon2 - 1), dt2);
	} else {
		var date = new Date();
		date2 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	}
	if (operation == "save") {
		if (date1 < date2)
			return false;
	} else if (operation == "submit") {
		if (date1 <= date2)
			return false;
	} else if (operation == "renew") {
		if (date1 > date2)
			return false;
	}
	return true;
}

function checkRenew() {
	var fromDate = getDomElementById("frmDate").value;
	return checkDates(fromDate, null, "renew");
}

/**
 * To cancel and/or clear BA Rate Configuration
 */
function cancelConfiguration() {
	flag = confirm("Do you want to Clear the Details");
	if (!flag) {
		return;
	}
	getDomElementById("frmDate").value = "";
	getDomElementById("toDate").value = "";
	document.baRateConfigurationForm.action = "./baRateConfiguration.do?submitName=viewBARateConfiguration";
	document.baRateConfigurationForm.submit();
}

function checkRateGridValues(secList, wtList, prodId, product) {
	var rows = secList.length;
	var cols = wtList.length;

	for ( var c = 0; c < rows; c++) {
		if (secList[c].sectorType == 'D'
				&& secList[c].sectorTO.sectorCode != sectorCode
				&& (secList[c].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == product)) {
			for ( var r = 0; r < cols; r++) {
				if (wtList[r].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == product) {

					field = document.getElementById("rate" + prodId
							+ secList[c].sectorTO.sectorId
							+ wtList[r].weightSlabTO.weightSlabId);
					if (isNull(field.value)) {
						var msg = "Please fill the Grid details for Weight Slab: "
								+ wtList[r].weightSlabTO.startWeightLabel;
						if (!isNull(wtList[r].weightSlabTO.endWeightLabel)) {
							msg = msg + " - "
									+ wtList[r].weightSlabTO.endWeightLabel;
						}
						msg = msg + " Sector: "
								+ secList[c].sectorTO.sectorName;
						alert(msg);
						field.focus();
						return false;
					}
				}
			}
		}

	}
	return true;
}

function disEnableSector(secList, wtList, prodId, product, flag1, flag2) {
	var rows = secList.length;
	var cols = wtList.length;
	for ( var c = 0; c < rows; c++) {
		if (secList[c].sectorType == 'D'
				&& (secList[c].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == product)) {
			for ( var r = 0; r < cols; r++) {
				if (wtList[r].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == product) {
					if (secList[c].sectorTO.sectorCode == sectorCode) {
						document.getElementById("rate" + prodId
								+ secList[c].sectorTO.sectorId
								+ wtList[r].weightSlabTO.weightSlabId).disabled = flag1;
					} else {
						document.getElementById("rate" + prodId
								+ secList[c].sectorTO.sectorId
								+ wtList[r].weightSlabTO.weightSlabId).disabled = flag2;
					}
				}
			}
		}

	}
}

function checkSpecialDestRateGridValues(product) {
	var splDestcoloumCount;
	if (product == "CO") {
		splDestcoloumCount = document
				.getElementById("courierSplDestcoloumCount").value;
		splDestcoloumCount = parseInt(splDestcoloumCount) + 2;
		for ( var i = 1; i < (rowCount); i++) {

			if (i != (rowCount - 1)) {
				if (isNull(document.getElementById("stateId" + i).value)) {
					alert("Please select state");
					document.getElementById("stateId" + i).focus();
					return false;
				} else {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (isNull(document
								.getElementById("specialDestinationRate" + i
										+ r).value)) {
							alert("Please enter Rate value");
							document.getElementById(
									"specialDestinationRate" + i + r).focus();
							return false;
						}
					}
				}
			} else {
				if (isNull(document.getElementById("stateId" + i).value)) {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (!isNull(document
								.getElementById("specialDestinationRate" + i
										+ r).value)) {
							alert("Please select state");
							document.getElementById("stateId" + i).focus();
							return false;
						}
					}
				} else {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (isNull(document
								.getElementById("specialDestinationRate" + i
										+ r).value)) {
							alert("Please enter Rate value");
							document.getElementById(
									"specialDestinationRate" + i + r).focus();
							return false;
						}
					}
				}
			}
		}
	} else if (product == "PR") {

		splDestcoloumCount = document
				.getElementById("prioritySplDestcoloumCount").value;
		splDestcoloumCount = parseInt(splDestcoloumCount) + 2;
		for ( var i = 1; i < (rowCountforpr); i++) {
			if (i != (rowCountforpr - 1)) {
				if (isNull(document.getElementById("pr_stateId" + i).value)) {
					alert("Please select state");
					document.getElementById("pr_stateId" + i).focus();
					return false;
				} else {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (isNull(document
								.getElementById("pr_specialDestinationRate" + i
										+ r).value)) {
							alert("Please enter Rate value");
							document.getElementById(
									"pr_specialDestinationRate" + i + r)
									.focus();
							return false;
						}
					}
				}
			} else {
				if (isNull(document.getElementById("pr_stateId" + i).value)) {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (!isNull(document
								.getElementById("pr_specialDestinationRate" + i
										+ r).value)) {
							alert("Please select state");
							document.getElementById("pr_stateId" + i).focus();
							return false;
						}
					}
				} else {
					for ( var r = 2; r < splDestcoloumCount; r++) {
						if (isNull(document
								.getElementById("pr_specialDestinationRate" + i
										+ r).value)) {
							alert("Please enter Rate value");
							document.getElementById(
									"pr_specialDestinationRate" + i + r)
									.focus();
							return false;
						}
					}
				}
			}
		}
	}

	return true;
}

function clearcheckBoxValue(chekBoxField, valField) {

	if (document.getElementById(chekBoxField).checked == false) {
		if (chekBoxField == "octroiBourneByChk") {
			document.getElementById("octroiServiceChargesChk").checked = false;
			document.getElementById("octroiServiceCharges").value = "";
		} else if (chekBoxField == "pr_octroiBourneByChk") {
			document.getElementById("pr_octroiServiceChargesChk").checked = false;
			document.getElementById("pr_octroiServiceCharges").value = "";
		} else {
			document.getElementById(valField).value = "";
		}
	} else if (chekBoxField == "octroiBourneByChk"
			&& document.getElementById(chekBoxField).checked == true) {
		document.getElementById("octroiServiceChargesChk").checked = true;
	} else if (chekBoxField == "pr_octroiBourneByChk"
			&& document.getElementById(chekBoxField).checked == true) {
		document.getElementById("pr_octroiServiceChargesChk").checked = true;
	}
}

function btnDisable(btnId) {
	buttonDisabled(btnId, 'btnintformbigdis');
}
/**
 * To enable button
 * 
 * @param btnId
 */
function btnEnable(btnId) {
	jQuery('#' + btnId).removeClass('btnintformbigdis');
	buttonEnabled(btnId, 'btnintform');
}

function loadStateDropDown(selectId, val, statesList) {
	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (!isNull(statesList) && statesList.length > 0) {
		for ( var i = 0; i < statesList.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = statesList[i].stateId;
			opt.text = statesList[i].stateName;
			obj.options.add(opt);
		}
		obj.value = val;
	}
}

function loadCityDropDown(selectId, val, datalist) {
	var obj = getDomElementById(selectId);
	clearCityDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].cityId;
			opt.text = datalist[i].cityName;
			obj.options.add(opt);
		}
		obj.value = val;
	}
}

function validateCities(prodCode, rowId, cityObj, stateObj) {

	cityObj1 = getDomElementById(cityObj + rowId);
	cityObjVal1 = getDomElementById(cityObj + rowId).value;
	stateObj1 = getDomElementById(stateObj + rowId);
	stateObjVal1 = getDomElementById(stateObj + rowId).value;
	var rCnt = 0;
	if (prodCode == "PR") {
		rCnt = rowCountforpr;
	} else if (prodCode == "CO") {
		rCnt = rowCount;
	} else if (prodCode == "AC") {
		rCnt = rowCountForAc;
	} else if (prodCode == "TR") {
		rCnt = rowCountForTr;
	}

	for ( var i = 1; i < rCnt; i++) {
		if (i != rowId) {
			cityObj2 = getDomElementById(cityObj + i);
			stateObj2 = getDomElementById(stateObj + i);

			cityObjVal2 = cityObj2.value;
			stateObjVal2 = stateObj2.value;
			if (!isNull(stateObjVal1) && !isNull(stateObjVal2)
					&& !isNull(cityObjVal1) && !isNull(cityObjVal2)) {
				if ((stateObjVal1 == stateObjVal2)
						&& (cityObjVal1 == cityObjVal2)) {
					alert("State row number " + i + " and " + rowId
							+ " are same. Cities should not be same");
					cityObj1.value = "";
					setTimeout(function() {
						cityObj1.focus();
					}, 10);
					return false;
				}
			}
		}
	}

	return true;
}

function validateStates(prodCode, rowId, cityObj, stateObj) {
	if (isNull(getDomElementById(stateObj + rowId).value)) {
		alert("Please select state");
		setTimeout(function() {
			getDomElementById(stateObj + rowId).focus();
		}, 10);
		return false;
	}

	cityObj1 = getDomElementById(cityObj + rowId);
	cityObjVal1 = getDomElementById(cityObj + rowId).value;
	stateObj1 = getDomElementById(stateObj + rowId);
	stateObjVal1 = getDomElementById(stateObj + rowId).value;
	var rCnt = 0;

	if (prodCode == "PR") {
		rCnt = rowCountforpr;
	} else if (prodCode == "CO") {
		rCnt = rowCount;
	} else if (prodCode == "AC") {
		rCnt = rowCountForAc;
	} else if (prodCode == "TR") {
		rCnt = rowCountForTr;
	}

	for ( var i = 1; i < rCnt; i++) {
		if (i != rowId) {
			cityObj2 = getDomElementById(cityObj + i);
			stateObj2 = getDomElementById(stateObj + i);

			cityObjVal2 = cityObj2.value;
			stateObjVal2 = stateObj2.value;
			if (!isNull(stateObjVal1) && !isNull(stateObjVal2)
					&& isNull(cityObjVal1) && isNull(cityObjVal2)) {
				if ((stateObjVal1 == stateObjVal2)) {
					alert("State row number " + i + " and " + rowId
							+ " should not be same");
					stateObj1.value = "";
					setTimeout(function() {
						stateObj1.focus();
					}, 10);
					return false;
				}
			}
		}
	}

	return true;
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");
}

function clearCityDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "ALL", "");
}

function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

function getCityByState(stateObj, rowId, cityObj) {
	if (!isNull(stateObj.value)) {
		var url = "./baRateConfiguration.do?submitName=getCityListByStateId&&stateId="
				+ stateObj.value;
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateCity(req, stateObj, rowId, cityObj);
			}
		});
	} else {
		// getDomElementById(stateObj).value = "";
		clearDropDownList(cityObj + rowId);
	}
}

function populateCity(req, stateObj, rowId, cityObj) {
	clearCityDropDownList(cityObj + rowId);
	if (!isNull(req)) {
		city = jsonJqueryParser(req);
		var error = city[ERROR_FLAG];
		if (req != null && error != null) {
			alert(error);
			getDomElementById(cityObj + rowId).value = "";
			// getDomElementById("stateId"+prodCatId+rowNo).value = "";
			// getDomElementById("stateId"+prodCatId+rowNo).focus();
		} else {
			loadCityDropDown(cityObj + rowId, "", city);
		}

	}
}

function clearBaType() {
	document.getElementById('baType').value = "";
}

function dataTableScrollWidth() {
	$('.dataTables_scrollHeadInner,.dataTables_scrollHeadInner table').width(
			"100%");
	$('.dataTables_scroll').width("100%");
}

function loadDefaultCODCharges() {
	document.getElementById("codChargesChk").checked = true;
	var codChargeTOs = document.getElementById("codChargeTOs").value;
	for ( var i = 1; i <= codChargeTOs.split(",").length; i++) {
		if (!isNull(document.getElementById("FixedChargesRadio" + (i)))) {
			document.getElementById("FixedChargesRadio" + (i)).checked = true;
		}
	}
}

function validateFixedChargeVal(obj, type) {
	val = obj.value;
	if (!isNull(val)) {
		var num = new Number(val);
		if (type == "P") {
			if (/^[0-9]{0,2}(\.[0-9]{0,2})?$/.test(val) && num > 0) {
				return true;
			} else {
				alert('Please enter the value in 99.99 format');
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		} else {
			if (/^[0-9]{0,4}(\.[0-9]{0,2})?$/.test(val) && num > 0) {
				return true;
			} else {
				alert('Please enter the value in 9999.99 format');
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		}
	}

	return true;
}

function validateFCKey(event) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = event.which;

	if (charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0)
		return true;
	else
		return onlyDecimal(event);
}

/**
 * Search Ba rate configuration details
 * 
 * @author hkansagr
 */
function searchBARatesDtls(productCode) {
	if (isValidInput()) {
		showProcessing();

		//document.getElementById('oldHeaderId').value = "";

		var regionId = document.getElementById("regionId").value;
		var city = document.getElementById('cityId').value;
		var baType = document.getElementById('baType').value;
		var headerId = document.getElementById('headerId').value;
		var productId = "";
		var servicedOn = "";

		if (productCode == TRAIN_PROD_CAT_CODE) {
			productId = document.getElementById('trainProductCategoryId').value;
		} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
			productId = document.getElementById('airCargoProductCategoryId').value;
		} else if (productCode == COURIER_PROD_CAT_CODE) {
			productId = document.getElementById('courierProductCategoryId').value;
		} else if (productCode == PRIORITY_PROD_CAT_CODE) {
			productId = document.getElementById('productCategoryIdForPriority').value;
		}

		var url = "./baRateConfiguration.do?submitName=searchBARatesDtls"
				+ "&regionId=" + regionId + "&city=" + city + "&baType="
				+ baType + "&headerId=" + headerId + "&productId=" + productId
				+ "&productCode=" + productCode;

		if (productCode == PRIORITY_PROD_CAT_CODE) {
			servicedOn = document.getElementById('servicedOn').value;
			if (!isNull(servicedOn)) {
				url += "&servicedOn=" + servicedOn;
			}
		}
		// Call Back AJAX function
		ajaxJquery(url, baForm, callSearchBARatesDtls);
	}
}

/**
 * To set search result to respective fields
 * 
 * @param ajaxResp
 * @author hkansagr
 */
function callSearchBARatesDtls(ajaxResp) {
	var headerId = document.getElementById('headerId').value;
	var isRenewWindow = document.getElementById('isRenewWindow').value;
	// Enable buttons
	btnEnable('btnCOSlabsSave');
	btnEnable('btnCOSlabsCancel');
	// btnEnable('btnSubmit');
	btnEnable('cancelBaRateConfiguration');

	var flag = true;
	var productCode = "";
	var stateCityLen = 0;
	if (isRenewWindow == 'Y') {
		$("#btnSearch").removeClass('button');
		$("#btnSearch").addClass('button_disable');
		if (isNull(ajaxResp)) {
			alert("BA Configuration Not found for given city.");
			flag = false;
		}
	} else {
		if (isNull(ajaxResp) && isNull(headerId)) {
			alert("BA Configuration Not found for given city.");
			flag = false;
		}
	}
	
	if (flag == true) {
		var headerTO = jsonJqueryParser(ajaxResp);
		if (!isNull(document.getElementById('sectorCode').value)) {
			document.getElementById('sectorCode').value = headerTO.sectorCode;
			sectorCode = headerTO.sectorCode;
		}
		sectorCode = headerTO.sectorCode;
		productCode = headerTO.commonProdCatCode;

		// Disabled Sector row for courier and priority
		if (productCode == COURIER_PROD_CAT_CODE
				|| productCode == PRIORITY_PROD_CAT_CODE) {
			disableSector(productCode);
		}

		// To active tabs on click of respective product tabs
		if (productCode == COURIER_PROD_CAT_CODE) { // COURIER
			toActiveTabs("tabs", 0);
			toActiveTabs("tabsnested", 0);
		} else if (productCode == PRIORITY_PROD_CAT_CODE) { // PRIORITY
			toActiveTabs("tabs", 1);
			toActiveTabs("tabsnested1", 0);
		}

		enableOrDisableBtn(productCode, true);

		// Claer slab rate grid by product code
		clearSlabRateGrid(productCode, headerTO);

		// Delete rows
		deleteTableRowByProcessCode(productCode);

		// Add Rows
		addSplDestRowByProcessCode(productCode);

		var productId = getProductIdByProductCode(productCode);
		/*
		 * disEnableSector(baCourierSectorList, baCourierWtSlabList, productId,
		 * productCode, true, false);
		 */
		if (isNull(headerTO.headerId) && isRenewWindow != 'Y') {
			alert("BA Configuration Not found for given city.");
		} else {
			var courierProdductId = productId;

			// Getting sector list and weight slab list by product code
			var sectorList = getSectorListByProductCode(productCode, headerTO);
			var weightSlabList = getWeightSlabListByProductCode(productCode,
					headerTO);

			var productTOList = headerTO.baRateProductTOList;
			document.getElementById('frmDate').value = headerTO.frmDate;
			document.getElementById('toDate').value = headerTO.toDate;
			getDomElementById("headerId").value = headerTO.headerId;
			getDomElementById("headerStatus").value = headerTO.headerStatus;
			getDomElementById("isBAConfigRenew").value = headerTO.isRenew;
	
			if (!isNull(productTOList)) {
				var baRateWtSlabList = productTOList[0].baRateWeightSlabTOList;
				setRespectiveProductHeaderId(productCode,
						productTOList[0].rateProductCategory);
				setBAProductHeaderId(productCode,
						productTOList[0].baProductHeaderId);

				// Setting flags
				setFlags(headerTO);

				// Enable or Disabled All Tabs
				enableAllTabs();

				for ( var z = 0; z < baRateWtSlabList.length; z++) {

					// Setting Values to Rate Slab Table
					var slabRateTOList = baRateWtSlabList[z].baSlabRateList;
					for ( var i = 0; i < sectorList.length; i++) {
						if (sectorList[i].sectorType == "D"
								&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == courierProdductId) {
							for ( var j = 0; j < weightSlabList.length; j++) {
								if (weightSlabList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == courierProdductId) {
									for ( var k = 0; k < slabRateTOList.length; k++) {

										// Setting serviced on for priority
										// product
										var servicedOnObj = getDomElementById("servicedOn");
										if (productCode == PRIORITY_PROD_CAT_CODE
												&& ((!isNull(servicedOnObj) && isNull(servicedOnObj.value)) || servicedOnObj.value != slabRateTOList[k].servicedOn)) {
											servicedOnObj.value = slabRateTOList[k].servicedOn;
										}

										// First Column for Train and Air Cargo
										// only
										if (baRateWtSlabList[z].slabOrder == 1
												&& (productCode == TRAIN_PROD_CAT_CODE || productCode == AIR_CARGO_PROD_CAT_CODE)) {
											var zeroWtSlabId = getDomElementById("zeroWeightSlabId").value;
											var pos = getDomElementById("position"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ zeroWtSlabId).value;
											var wtSlabMinChrg = getDomElementById("wtSlabMinChrg"
													+ productCode).value;
											if (slabRateTOList[k].destinationSector == sectorList[i].sectorTO.sectorId
													&& slabRateTOList[k].baWeightSlabId == baRateWtSlabList[z].baWeightSlabId
													&& pos == baRateWtSlabList[z].slabOrder) {
												getDomElementById("rate"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ zeroWtSlabId).value = slabRateTOList[k].rate;
												getDomElementById("slabRateid"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ zeroWtSlabId).value = slabRateTOList[k].baSlabRateId;
												getDomElementById("startWeightSlabId"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ zeroWtSlabId).value = baRateWtSlabList[z].startWeightTO.weightSlabId;
												getDomElementById("endWeightSlabId"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ zeroWtSlabId).value = baRateWtSlabList[z].endWeightTO.weightSlabId;
												getDomElementById("baWeightSlabId"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ zeroWtSlabId).value = slabRateTOList[k].baWeightSlabId;
												if (isNull(wtSlabMinChrg)
														|| (getDomElementById("wtSlabMinChrg"
																+ productCode).value != baRateWtSlabList[z].endWeightTO.weightSlabId)) {
													getDomElementById("wtSlabMinChrg"
															+ productCode).value = baRateWtSlabList[z].endWeightTO.weightSlabId;
													setSplDestColLabel(productCode);
												}
												break;
											}
										}

										// Rest of Columns
										var position = getDomElementById("position"
												+ courierProdductId
												+ sectorList[i].sectorTO.sectorId
												+ weightSlabList[j].weightSlabTO.weightSlabId).value;
										if (slabRateTOList[k].destinationSector == sectorList[i].sectorTO.sectorId
												&& slabRateTOList[k].baWeightSlabId == baRateWtSlabList[z].baWeightSlabId
												&& position == baRateWtSlabList[z].slabOrder) {
											if(productCode == PRIORITY_PROD_CAT_CODE && headerTO.headerStatus == 'A'){
												var newValue = '';
												if(baRateWtSlabList[z].startWeightTO.additional == 'Y'){
													 newValue =  baRateWtSlabList[z].startWeightTO.startWeightLabel;
												} else {
													 newValue = baRateWtSlabList[z].startWeightTO.startWeightLabel + '-' + baRateWtSlabList[z].endWeightTO.endWeightLabel;
												}
												var temp = parseInt(position) - 1;
												if(temp == 1){
													document.getElementById("prcol0").innerHTML = newValue;
													document.getElementById("prsdcol0").innerHTML = newValue;
												} else if(temp == 2){
													document.getElementById("prcol1").innerHTML = newValue;
													document.getElementById("prsdcol1").innerHTML = newValue;
												} else{
													document.getElementById("prcol2").innerHTML = newValue;
													document.getElementById("prsdcol2").innerHTML = newValue;
												}
												
											}
											
											getDomElementById("rate"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].rate;
											getDomElementById("slabRateid"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].baSlabRateId;
											
											if(baRateWtSlabList[z].startWeightTO.weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId){
												getDomElementById("startWeightSlabId"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ weightSlabList[j].weightSlabTO.weightSlabId).value = baRateWtSlabList[z].startWeightTO.weightSlabId;
											} else {
												getDomElementById("startWeightSlabId"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ weightSlabList[j].weightSlabTO.weightSlabId).value =  weightSlabList[j].weightSlabTO.weightSlabId;
											}
											
											if(baRateWtSlabList[z].endWeightTO.weightSlabId == weightSlabList[j].weightSlabTO.weightSlabId){
												getDomElementById("endWeightSlabId"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ weightSlabList[j].weightSlabTO.weightSlabId).value = baRateWtSlabList[z].endWeightTO.weightSlabId;
											} else {
												getDomElementById("endWeightSlabId"
														+ courierProdductId
														+ sectorList[i].sectorTO.sectorId
														+ weightSlabList[j].weightSlabTO.weightSlabId).value = weightSlabList[j].weightSlabTO.weightSlabId;
											}
											
											
											getDomElementById("baWeightSlabId"
													+ courierProdductId
													+ sectorList[i].sectorTO.sectorId
													+ weightSlabList[j].weightSlabTO.weightSlabId).value = slabRateTOList[k].baWeightSlabId;
											break;
										}
									}
								}
							}// J - loop end
						}// If loop
					}// I - loop end

					// Setting values to special destination - table
					var splDestRateList = baRateWtSlabList[z].baSpecialDestinationRateTOList;
					
					stateCityLen = splDestRateList.length;
					// State Id Element Name
					var stateId = getStateIdByProdCode(productCode);
					// City Id Element Name
					var cityIds = getCityIdsByProdCode(productCode);
					// Spl Dest Rate Id Element Name
					var splDestRate = getSplDestRowIdName(productCode);
					var splDestId = getSplDestHiddenIdName(productCode);

					// Add row as same as no. of state id list
					for ( var x = 0; x < stateCityLen; x++) {
						if (z == 0 && x + 1 != stateCityLen) {
							addSplDestRowByProcessCode(productCode);
						}
					}

					// column counter
					var cc = "";
					if (productCode == TRAIN_PROD_CAT_CODE
							|| productCode == AIR_CARGO_PROD_CAT_CODE) {
						cc = parseFloat(baRateWtSlabList[z].slabOrder) + 1;
					} else {
						cc = parseFloat(baRateWtSlabList[z].slabOrder);
					}

					// Execute, no. of state and citys array length - times
					
					for ( var x = 0; x < stateCityLen; x++) {
						// column counter
						var x1 = x + 1;
						// First time set the value of state and city
						if (isNull(getDomElementById(stateId + x1).value)) {							
							// Setting state id
							getDomElementById(stateId + x1).value = splDestRateList[x].stateId;
							clearCityDropDownList(cityIds + x1);
							loadCityDropDown(cityIds + x1, "",
									splDestRateList[x].cityList);
							// Setting city id
							if (!isNull(splDestRateList[x].cityTO)) {
								getDomElementById(cityIds + x1).value = splDestRateList[x].cityTO.cityId;
							}
						}
						
						// Setting respective special destination rate
						if ((getDomElementById(stateId + x1).value == splDestRateList[x].stateId)
								&& ((isNull(splDestRateList[x].cityTO) && (isNull(getDomElementById(cityIds + x1).value))) 
										|| (!isNull(splDestRateList[x].cityTO) && (!isNull(getDomElementById(cityIds + x1).value)) && getDomElementById(cityIds	+ x1).value == splDestRateList[x].cityTO.cityId))) {
							getDomElementById(splDestRate + x1 + cc).value = splDestRateList[x].rate;
							getDomElementById(splDestId + x1 + cc).value = splDestRateList[x].specialDestinationId;
						} else {
							// Iterate loop with all state and city ids in row
							// if not equal							
							for ( var y = 1; y <= stateCityLen; y++) {
								if ((getDomElementById(stateId + y).value == splDestRateList[x].stateId)
										&& ((isNull(splDestRateList[x].cityTO) && (isNull(getDomElementById(cityIds + y).value))) 
											|| (!isNull(splDestRateList[x].cityTO) && (!isNull(getDomElementById(cityIds + y).value)) &&  getDomElementById(cityIds	+ y).value == splDestRateList[x].cityTO.cityId))) {
									getDomElementById(splDestRate + y + cc).value = splDestRateList[x].rate;
									getDomElementById(splDestId + y + cc).value = splDestRateList[x].specialDestinationId;
									break;
								}
							}
						}

					}// X - loop end

				}// END BA RATE WT SLAB - LOOP - Z

				// Add extra row for special destination grid if not submitted
				if (stateCityLen > 0 && headerTO.headerStatus != 'A') {
					 addSplDestRowByProcessCode(productCode);
				}
			}
			// Disable the respective fields or element after submit
			disableForSubmit();

			// Setting for renew
			var renewCheck = checkRenew();
			if (headerTO.isRenew == 'Y' && renewCheck == true) {
				$("#btnRenew").removeClass('button_disable');
				$("#btnRenew").addClass('button');
				$("#btnRenew").attr('disabled', false);
				getDomElementById("isBAConfigRenew").value = headerTO.isRenew;
			} else if (renewCheck == true) {
				$("#btnRenew").removeClass('button_disable');
				$("#btnRenew").addClass('button');
				$("#btnRenew").attr('disabled', false);
			}
			if (headerTO.headerStatus == 'I') {
				$("#btnRenew").addClass('button_disable');
				$("#btnRenew").attr('disabled', 'disabled');
			}
			// $('.dataTables_scroll').width("100%");
		}
	}
	
	if (isRenewWindow == 'Y') {
		getDomElementById("regionId").disabled = true;
		getDomElementById("cityId").disabled = true;
		getDomElementById("baType").disabled = true;
	}
	hideProcessing();
}

/**
 * To delete spl destination row by product code
 * 
 * @param productCode
 */
function deleteTableRowByProcessCode(productCode) {
	if (productCode == COURIER_PROD_CAT_CODE) {
		deleteTableRow("courierSpecialDestination");
		rowCount = 1;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		deleteTableRow("trainSplDestTable");
		rowCountForTr = 1;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		deleteTableRow("airCargoSplDestTable");
		rowCountForAc = 1;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		deleteTableRow("prioritySpecialDestination");
		rowCountforpr = 1;
	}
}

/**
 * To add spl destination row by product code
 * 
 * @param productCode
 */
function addSplDestRowByProcessCode(productCode) {
	if (productCode == COURIER_PROD_CAT_CODE) {
		addCourierSpecialDestinationRow();
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		addTrainSpecialDestinationRow();
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		addAirCargoSpecialDestinationRow();
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		addPrioritySpecialDestinationRow();
	}
}

/**
 * To get row Counter by product code
 * 
 * @param productCode
 * @returns rowCounter
 */
function getRowCounterByProductCode(productCode) {
	var rowCounter = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		rowCounter = rowCount;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		rowCounter = rowCountForTr;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		rowCounter = rowCountForAc;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		rowCounter = rowCountforpr;
	}
	return parseFloat(rowCounter);
}

/**
 * To get special destination column count
 * 
 * @param productCode
 * @returns {String} - Integer
 */
function getSplDestColumnCount(productCode) {
	var colCnt = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		colCnt = getDomElementById("courierSplDestcoloumCount").value;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		colCnt = parseInt(getDomElementById("trainSplDestcoloumCount").value);
		colCnt++;
		colCnt += " ";
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		colCnt = parseInt(getDomElementById("airCargoSplDestcoloumCount").value);
		colCnt++;
		colCnt += " ";
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		colCnt = getDomElementById("prioritySplDestcoloumCount").value;
	}
	return trimString(colCnt);
}

/**
 * To get city id name for special destination by product code
 * 
 * @param prodCode
 * @returns {String}
 */
function getCityIdsByProdCode(prodCode) {
	var cityIds = "";
	if (prodCode == COURIER_PROD_CAT_CODE) {
		cityIds = "cityIds";
	} else if (prodCode == TRAIN_PROD_CAT_CODE) {
		cityIds = "tr_cityIds";
	} else if (prodCode == AIR_CARGO_PROD_CAT_CODE) {
		cityIds = "ac_cityIds";
	} else if (prodCode == PRIORITY_PROD_CAT_CODE) {
		cityIds = "pr_cityIds";
	}
	return cityIds;
}

/**
 * To get state id name for special destination by product code
 * 
 * @param prodCode
 * @returns {String}
 */
function getStateIdByProdCode(prodCode) {
	var stateId = "";
	if (prodCode == COURIER_PROD_CAT_CODE) {
		stateId = "stateId";
	} else if (prodCode == TRAIN_PROD_CAT_CODE) {
		stateId = "tr_stateId";
	} else if (prodCode == AIR_CARGO_PROD_CAT_CODE) {
		stateId = "ac_stateId";
	} else if (prodCode == PRIORITY_PROD_CAT_CODE) {
		stateId = "pr_stateId";
	}
	return stateId;
}

/**
 * To get special destination row id name for special destination tables by
 * product code
 * 
 * @param prodCode
 * @returns {String}
 */
function getSplDestRowIdName(prodCode) {
	var splDestRow = "";
	if (prodCode == COURIER_PROD_CAT_CODE) {
		splDestRow = "specialDestinationRate";
	} else if (prodCode == TRAIN_PROD_CAT_CODE) {
		splDestRow = "tr_specialDestinationRate";
	} else if (prodCode == AIR_CARGO_PROD_CAT_CODE) {
		splDestRow = "ac_specialDestinationRate";
	} else if (prodCode == PRIORITY_PROD_CAT_CODE) {
		splDestRow = "pr_specialDestinationRate";
	}
	return splDestRow;
}

/**
 * To get special destination hidden id of row name for special destination
 * tables by product code
 * 
 * @param prodCode
 * @returns {String}
 */
function getSplDestHiddenIdName(prodCode) {
	var splDestId = "";
	if (prodCode == COURIER_PROD_CAT_CODE) {
		splDestId = "specialDestinationId";
	} else if (prodCode == TRAIN_PROD_CAT_CODE) {
		splDestId = "tr_specialDestinationId";
	} else if (prodCode == AIR_CARGO_PROD_CAT_CODE) {
		splDestId = "ac_specialDestinationId";
	} else if (prodCode == PRIORITY_PROD_CAT_CODE) {
		splDestId = "pr_specialDestinationId";
	}
	return splDestId;
}

/**
 * To get product id by product code
 * 
 * @param productCode
 * @returns {String}
 */
function getProductIdByProductCode(productCode) {
	var productId = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		productId = getDomElementById("courierProductCategoryId").value;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		productId = getDomElementById("trainProductCategoryId").value;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		productId = getDomElementById("airCargoProductCategoryId").value;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		productId = getDomElementById("productCategoryIdForPriority").value;
	}
	return productId;
}

/**
 * To get sector list by product code
 * 
 * @param productCode
 * @param data
 * @returns {String}
 */
function getSectorListByProductCode(productCode, data) {
	var obj = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		obj = data.sectorsList;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		obj = data.trainSectorsList;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		obj = data.airCargoSectorsList;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		obj = data.prioritySectorsList;
	}
	return obj;
}

/**
 * To get weight slab list by product code
 * 
 * @param productCode
 * @param data
 * @returns {String}
 */
function getWeightSlabListByProductCode(productCode, data) {
	var obj = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		obj = data.wtSlabList;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		obj = data.trainWtSlabList;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		obj = data.airCargoWtSlabList;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		obj = data.priorityWtSlabList;
	}
	return obj;
}

/**
 * To set respective product id by product code
 * 
 * @param productCode
 * @param productId
 */
function setRespectiveProductHeaderId(productCode, productId) {
	if (productCode == COURIER_PROD_CAT_CODE) {
		getDomElementById("courierProductCategoryId").value = productId;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		getDomElementById("trainProductCategoryId").value = productId;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		getDomElementById("airCargoProductCategoryId").value = productId;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		getDomElementById("productCategoryIdForPriority").value = productId;
	}
}

/**
 * To set BA Product Header Id
 * 
 * @param productCode
 * @param baProdHeaderId
 */
function setBAProductHeaderId(productCode, baProdHeaderId) {
	if (productCode == COURIER_PROD_CAT_CODE) {
		getDomElementById("courierProductHeaderId").value = baProdHeaderId;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		getDomElementById("trainProductHeaderId").value = baProdHeaderId;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		getDomElementById("airCargoProductHeaderId").value = baProdHeaderId;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		getDomElementById("priorityProductHeaderId").value = baProdHeaderId;
	}
}

/**
 * To prepare spl destination table - for all product
 * 
 * @param productCode
 */
function prepareSplDestTable(productCode) {
	$('.dataTables_scrollHeadInner,.dataTables_scrollHeadInner table').width(
			"100%");
	$('.dataTables_scroll').width("100%");
}

/**
 * To validate for MC - minimum chargable weight
 * 
 * @param obj
 * @param processCode
 */
function validateForMC(obj, processCode) {
	var splDestMC = "splDestMC_" + processCode;
	var mcDropDownId = "wtSlabMinChrg" + processCode;
	var labelMC = "";
	if (!isNull($("#" + mcDropDownId).val())) {
		labelMC = $("#" + mcDropDownId).find("option:selected").text();
		setNextColStartWt(processCode);
	}
	document.getElementById(splDestMC).innerHTML = labelMC;
}

/**
 * To set next column start weight
 * 
 * @param processCode
 */
function setNextColStartWt(processCode) {
	var wtSlabMinChrg = getDomElementById("wtSlabMinChrg" + processCode).value;
	if (!isNull(wtSlabMinChrg)) {
		var labelMC = $("#wtSlabMinChrg" + processCode).find("option:selected")
				.text();
		labelMC = parseFloat(labelMC) + parseFloat(0.001);
		labelMC = labelMC.toFixed(3);

		var rateSectors = "";
		var weightSlabList = "";
		var productCategoryId = "";
		var zeroWeightSlabId = getDomElementById("zeroWeightSlabId").value;

		if (processCode == TRAIN_PROD_CAT_CODE) {
			productCategoryId = getDomElementById("trainProductCategoryId").value;
			rateSectors = rateSectorsForTrain;
			weightSlabList = weightSlabListForTrain;
		} else if (processCode == AIR_CARGO_PROD_CAT_CODE) {
			productCategoryId = getDomElementById("airCargoProductCategoryId").value;
			rateSectors = rateSectorsForAirCargo;
			weightSlabList = weightSlabListForAirCargo;
		} else if (processCode == COURIER_PROD_CAT_CODE) {
			productCategoryId = getDomElementById("courierProductCategoryId").value;
			rateSectors = rateSectorsForCourier;
			weightSlabList = weightSlabListForCourier;
		} else if (processCode == PRIORITY_PROD_CAT_CODE) {
			productCategoryId = getDomElementById("productCategoryIdForPriority").value;
			rateSectors = rateSectorsForPriority;
			weightSlabList = weightSlabListForPriority;
		}

		for ( var i = 0; i < nextColumnMinChrg.length; i++) {
			if (nextColumnMinChrg[i].startWeight == labelMC) {
				for ( var j = 0; j < rateSectors.length; j++) {
					var nextStartSlabObj = getDomElementById("startWeightSlabId"
							+ productCategoryId
							+ rateSectors[j].sectorTO.sectorId
							+ weightSlabList[0].weightSlabTO.weightSlabId);
					var nextEndSlabObj = getDomElementById("endWeightSlabId"
							+ productCategoryId
							+ rateSectors[j].sectorTO.sectorId
							+ weightSlabList[0].weightSlabTO.weightSlabId);
					var firstSlabObj = getDomElementById("endWeightSlabId"
							+ productCategoryId
							+ rateSectors[j].sectorTO.sectorId
							+ zeroWeightSlabId);
					nextStartSlabObj.value = nextColumnMinChrg[i].weightSlabId;
					nextEndSlabObj.value = nextColumnMinChrg[i].weightSlabId;
					firstSlabObj.value = getDomElementById("wtSlabMinChrg"
							+ processCode).value;
				} // SECTOR - LOOP
				break;
			} // IF - END
		} // NEXT_COLUMN_MIN_CHRG - LOOP
	}
}

/**
 * To set special destination column label
 * 
 * @param processCode
 */
function setSplDestColLabel(processCode) {
	var splDestMC = "splDestMC_" + processCode;
	var mcDropDownId = "wtSlabMinChrg" + processCode;
	var labelMC = "";
	if (!isNull($("#" + mcDropDownId).val())) {
		labelMC = $("#" + mcDropDownId).find("option:selected").text();
	}
	document.getElementById(splDestMC).innerHTML = labelMC;
}

var COMMON_PRODUCT_CODE = "";
/**
 * To save or update BA Rate details
 * 
 * @param productCode
 */
function saveOrUpdateBARatesDtls(productCode) {
	var headId = getDomElementById("headerId").value;
	if ((!isNull(headId) || (isNull(headId) && validateDates("save")))
			&& isValidInput() && checkSlabRateGrid(productCode)
			&& checkSplDestGrid(productCode)) {
		getDomElementById("commonProdCatCode").value = productCode;
		var url = './baRateConfiguration.do?submitName=saveOrUpdateBARatesDtls';
		// Initialy serviced on - common property should blank
		var servicedOnCmn = document.getElementById('servicedOnCmn');
		servicedOnCmn.value = "";
		if (productCode == PRIORITY_PROD_CAT_CODE) {
			var servicedOn = document.getElementById('servicedOn').value;
			if (!isNull(servicedOn)) {
				servicedOnCmn.value = servicedOn;
			}
		}
		enableHeaderBeforeSave();
		enableOrDisableBtn(productCode,false);
		ajaxJquery(url, baForm, callSaveOrUpdateBARatesDtls);
		COMMON_PRODUCT_CODE = productCode;
	}
}
// Ajax call back function for saveOrUpdateBARatesDtls
function callSaveOrUpdateBARatesDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			callSearchBARatesDtls(ajaxResp);
			alert("Data saved successfully.");
		}
	} else {
		alert("ERROR :: Data not saved.");
	}
}

/**
 * To clear slab rate grid - only priority product
 * 
 * @param productCode
 */
function clearSlabRateGrid(productCode, data) {
	var prodCatId = getProductIdByProductCode(productCode);
	var sectorList = getSectorListByProductCode(productCode, data);
	var wtSlabList = getWeightSlabListByProductCode(productCode, data);
	for ( var i = 0; i < sectorList.length; i++) {
		for ( var j = 0; j < wtSlabList.length; j++) {
			var idSeqStr = prodCatId + sectorList[i].sectorTO.sectorId
					+ wtSlabList[j].weightSlabTO.weightSlabId;
			var rate = getDomElementById("rate" + idSeqStr);
			var slabRateid = getDomElementById("slabRateid" + idSeqStr);
			var baWeightSlabId = getDomElementById("baWeightSlabId" + idSeqStr);
			rate.value = "";
			slabRateid.value = "";
			baWeightSlabId.value = "";
		}
	}
}

/**
 * To enable all tabs
 * 
 * @param isEnable
 */
function enableAllTabs() {

	var courier = getDomElementById("courierRatesCheck").value;
	var airCargo = getDomElementById("airCargoRatesCheck").value;
	var train = getDomElementById("trainRatesCheck").value;
	var fixedChrg = getDomElementById("courierChargesCheck").value;
	var coRTO = getDomElementById("courierChargesCheck").value;

	var prA = getDomElementById("priorityARatesCheck").value;
	var prB = getDomElementById("priorityBRatesCheck").value;
	var prS = getDomElementById("prioritySRatesCheck").value;
	var prFixedChrg = getDomElementById("priorityChargesCheck").value;
	var prRTO = getDomElementById("priorityChargesCheck").value;

	/* NON-PRIORITY */
	// Courier
	if (courier == 'Y') {
		enableTabs("tabs", 1);
		enableTabs("tabsnested", 1);
		// Next tab
		enableTabs("tabsnested", 2);
	}
	// Air-Cargo
	if (airCargo == 'Y') {
		enableTabs("tabs", 1);
		enableTabs("tabsnested", 2);
		// Next tab
		enableTabs("tabsnested", 3);
	}
	// Train
	if (train == 'Y') {
		enableTabs("tabs", 1);
		enableTabs("tabsnested", 3);
		// Next tab
		enableTabs("tabsnested", 4);
	}
	// Fixed Charges - Non-priority
	if (fixedChrg == 'Y') {
		enableTabs("tabs", 1);
		enableTabs("tabsnested", 4);
		// Next tab
		enableTabs("tabsnested", 5);
	}
	// RTO charges - Non-priority
	if (coRTO == 'Y') {
		enableTabs("tabs", 1);
		enableTabs("tabsnested", 5);
		// Next tab
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 1);
	}

	/* PRIORITY */
	// Priority - A (After 14.00)
	if (prA == 'Y') {
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 1);
		// Next tab
		enableTabs("tabsnested1", 2);
	}
	// Priority - B (Before 14.00)
	if (prB == 'Y') {
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 1);
		// Next tab
		enableTabs("tabsnested1", 2);
	}
	// Priority - S (Sunday)
	if (prS == 'Y') {
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 1);
		// Next tab
		enableTabs("tabsnested1", 2);
	}
	// Fixed Charges - Priority
	if (prFixedChrg == 'Y') {
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 2);
		// Next tab
		enableTabs("tabsnested1", 3);
	}
	// RTO charges - Priority
	if (prRTO == 'Y') {
		enableTabs("tabs", 2);
		enableTabs("tabsnested1", 3);
	}
}

/**
 * To active tabs
 * 
 * @param id
 * @param index
 */
function toActiveTabs(id, index) {
	$("#" + id).tabs("option", "active", index);
}

/**
 * To enable or disable slab rate buttons - save,cancel
 * 
 * @param productCode
 * @param isEnable
 */
function enableOrDisableBtn(productCode, isEnable) {
	if (isEnable) {// Enabled
		if (productCode == COURIER_PROD_CAT_CODE) {
			btnEnable('btnCOSlabsSave');
			btnEnable('btnCOSlabsCancel');
		} else if (productCode == TRAIN_PROD_CAT_CODE) {
			btnEnable('btnTRSlabsSave');
			btnEnable('btnTRSlabsCancel');
		} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
			btnEnable('btnARSlabsSave');
			btnEnable('btnARSlabsCancel');
		} else if (productCode == PRIORITY_PROD_CAT_CODE) {
			btnEnable('btnPRSlabsSave');
			btnEnable('btnPRSlabsCancel');
		}
	} else { // Disbaled
		if (productCode == COURIER_PROD_CAT_CODE) {
			btnDisable('btnCOSlabsSave');
			btnDisable('btnCOSlabsCancel');
		} else if (productCode == TRAIN_PROD_CAT_CODE) {
			btnDisable('btnTRSlabsSave');
			btnDisable('btnTRSlabsCancel');
		} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
			btnDisable('btnARSlabsSave');
			btnDisable('btnARSlabsCancel');
		} else if (productCode == PRIORITY_PROD_CAT_CODE) {
			btnDisable('btnPRSlabsSave');
			btnDisable('btnPRSlabsCancel');
		}
	}
}

/**
 * To disable after submit the BA rate configuration details
 */
function disableForSubmit() {
	var headerStatus = getDomElementById("headerStatus").value;
	if (headerStatus == 'A') {
		// Non-priority - Tabs-2
		$("#tabs-2 :button").addClass('btnintformbigdis');
		$("#tabs-2 :button").attr('disabled', 'disabled');
		$('#tabs-2').find('input, textarea, button, select').attr('disabled',
				'disabled');

		// Priority - Tabs-3
		$("#tabs-3 :button").addClass('btnintformbigdis');
		$("#tabs-3 :button").attr('disabled', 'disabled');
		$('#tabs-3').find('input, textarea, button, select').attr('disabled',
				'disabled');

		// Keep Serviced On drop down enable
		$("#servicedOn").attr('disabled', false);

		// Submit Button Disabled
		$("#btnSubmit").addClass('btnintformbigdis');
		$("#btnSubmit").attr('disabled', 'disabled');

		// Hide calendar icons
		$("#frmDt").hide();
		$("#toDt").hide();
	}
}

/**
 * To get weight slab object by product code
 * 
 * @param productCode
 * @returns {String}-JSON Object
 */
function getWtSlabObjByProductCode(productCode) {
	var obj = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		obj = weightSlabListForCourier;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		obj = weightSlabListForTrain;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		obj = weightSlabListForAirCargo;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		obj = weightSlabListForPriority;
	}
	return obj;
}

/**
 * To get sector list object by product code
 * 
 * @param productCode
 * @returns {String}-JSON Object
 */
function getSectorObjByProductCode(productCode) {
	var obj = "";
	if (productCode == COURIER_PROD_CAT_CODE) {
		obj = rateSectorsForCourier;
	} else if (productCode == TRAIN_PROD_CAT_CODE) {
		obj = rateSectorsForTrain;
	} else if (productCode == AIR_CARGO_PROD_CAT_CODE) {
		obj = rateSectorsForAirCargo;
	} else if (productCode == PRIORITY_PROD_CAT_CODE) {
		obj = rateSectorsForPriority;
	}
	return obj;
}

/**
 * To get Minimun chargable weight Drop Down value
 * 
 * @param productCode
 * @returns {String}
 */
function getMinChrgWtDD(productCode) {
	var minChrgWtDD = "";
	if (productCode == TRAIN_PROD_CAT_CODE
			|| productCode == AIR_CARGO_PROD_CAT_CODE) {
		minChrgWtDD = getDomElementById("wtSlabMinChrg" + productCode);
	}
	return minChrgWtDD;
}

/**
 * To check slab rate grid for all product
 * 
 * @param productCode
 * @returns {Boolean}
 */
function checkSlabRateGrid(productCode) {
	var prodId = getProductIdByProductCode(productCode);
	var secList = getSectorObjByProductCode(productCode);
	var wtList = getWtSlabObjByProductCode(productCode);
	var rows = secList.length;
	var cols = wtList.length;
	var wtSlabMinChrg = getMinChrgWtDD(productCode);

	for ( var c = 0; c < rows; c++) {
		if (secList[c].sectorType == 'D'
				&& (secList[c].sectorTO.sectorCode != sectorCode || (productCode == TRAIN_PROD_CAT_CODE || productCode == AIR_CARGO_PROD_CAT_CODE))
				&& (secList[c].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == productCode)) {
			if (!isNull(wtSlabMinChrg) && !isNull(wtSlabMinChrg.value)) {
				var zeroWtSlabId = getDomElementById("zeroWeightSlabId").value;
				field = document.getElementById("rate" + prodId
						+ secList[c].sectorTO.sectorId + zeroWtSlabId);
				if (isNull(field.value)) {
					var msg = "Please fill the Grid details for Minimum Pay Load : ";
					msg = msg
							+ $("#wtSlabMinChrg" + productCode).find(
									"option:selected").text();
					msg = msg + " Sector: " + secList[c].sectorTO.sectorName;
					alert(msg);
					setTimeout(function() {
						field.focus();
					}, 10);
					return false;
				}
			}
			for ( var r = 0; r < cols; r++) {
				if (wtList[r].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == productCode) {

					field = document.getElementById("rate" + prodId
							+ secList[c].sectorTO.sectorId
							+ wtList[r].weightSlabTO.weightSlabId);
					if (isNull(field.value)) {
						var msg = "Please fill the Grid details for Weight Slab: "
								+ wtList[r].weightSlabTO.startWeightLabel;
						if (!isNull(wtList[r].weightSlabTO.endWeightLabel)) {
							msg = msg + " - "
									+ wtList[r].weightSlabTO.endWeightLabel;
						}
						msg = msg + " Sector: "
								+ secList[c].sectorTO.sectorName;
						alert(msg);
						setTimeout(function() {
							field.focus();
						}, 10);
						return false;
					}
				}
			}
		}
	}
	return true;
}

/**
 * To check special destination grid for all product
 * 
 * @param productCode
 * @returns {Boolean}
 */
function checkSplDestGrid(productCode) {
	var stateId = getStateIdByProdCode(productCode);
	var splDestId = getSplDestRowIdName(productCode);
	var rowCnt = parseInt(getRowCounterByProductCode(productCode));
	var colCnt = parseInt(getSplDestColumnCount(productCode));

	for ( var i = 1; i < (rowCnt); i++) {
		var rowCnt2 = rowCnt - 1;
		if (i != (rowCnt2)) {
			if (isNull(document.getElementById(stateId + i).value)) {
				alert("Please select state");
				setTimeout(function() {
					document.getElementById(stateId + i).focus();
				}, 10);
				return false;
			} else {
				for ( var r = 2; r < colCnt + 2; r++) {
					if (isNull(document.getElementById(splDestId + i + r + "").value)) {
						alert("Please enter Rate value");
						setTimeout(function() {
							document.getElementById(splDestId + i + r + "")
									.focus();
						}, 10);
						return false;
					}
				}
			}
		} else {
			if (isNull(document.getElementById(stateId + i).value)) {
				for ( var r = 2; r < colCnt + 2; r++) {
					if (!isNull(document.getElementById(splDestId + i + r + "").value)) {
						alert("Please select state");
						setTimeout(function() {
							document.getElementById(stateId + i).focus();
						}, 10);
						return false;
					}
				}
			} else {
				for ( var r = 2; r < colCnt + 2; r++) {
					if (isNull(document.getElementById(splDestId + i + r + "").value)) {
						alert("Please enter Rate value");
						setTimeout(function() {
							document.getElementById(splDestId + i + r + "")
									.focus();
						}, 10);

						return false;
					}
				}
			}
		}
	}
	return true;
}

/**
 * To disable sector - readonly for courier and priority
 * 
 * @param productCode
 */
function disableSector(productCode) {
	var prodId = getProductIdByProductCode(productCode);
	var secList = getSectorObjByProductCode(productCode);
	var wtList = getWtSlabObjByProductCode(productCode);
	var rows = secList.length;
	var cols = wtList.length;

	for ( var c = 0; c < rows; c++) {
		if (secList[c].sectorType == 'D'
				&& (secList[c].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == productCode)) {
			for ( var r = 0; r < cols; r++) {
				if (wtList[r].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == productCode) {
					if (secList[c].sectorTO.sectorCode == sectorCode) {
						var rate = document.getElementById("rate" + prodId
								+ secList[c].sectorTO.sectorId
								+ wtList[r].weightSlabTO.weightSlabId);
						rate.setAttribute("readonly", true);
						rate.setAttribute("tabindex", "-1");
					}
				}
			}
		}
	}
}