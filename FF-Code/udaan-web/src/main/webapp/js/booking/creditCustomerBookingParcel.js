/** The giCount */
var giCount = 1;
/** The rowCount */
var rowCount = 1;
/** The DEC_VAL */
var DEC_VAL = "";
var IS_POLICY_MANDATORY = "N";
var MAX_DECLARED_VALUE = 100000;
var isPolicyNo;
$(document).ready(function() {
	var oTable = $('#booking').dataTable({
		"sScrollY" : "310",
		"sScrollX" : "200%",
		"sScrollXInner" : "220%",
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

/**
 * fnClickAddRow
 * 
 * @author sdalli
 */
function fnClickAddRow() {
	// var rowId = rowCount;
	var contentOpt = getContentOptions();
	var insOpt = getInsuranceOptions();
	// var paperWrkOpt = getPaperWorksOptions();
	$('#booking')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"    id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
							rowCount,
							'<input type="text" id="cnNumber'
									+ rowCount
									+ '" name="to.consgNumbers" size="15" maxlength="12" class="txtbox" onblur="convertDOMObjValueToUpperCase(this);validateConsignmentCCBookingParcel(this);" onchange="" onkeypress="return OnlyAlphabetsAndNosAndEnter(this, event, \'cnPincode'
									+ rowCount
									+ '\',\'CN Number\');"/><input type="hidden" id="cnrdetails'
									+ rowCount
									+ '" name="to.cnrAddressDtls"/><input type="hidden" id="cnedetails'
									+ rowCount
									+ '" name="to.cneAddressDtls"/><input type="hidden" id="priorityServicedOns'
									+ rowCount
									+ '" name="to.priorityServicedOns" value=""/>',
							'<input type="text" id="custCode'
									+ rowCount
									+ '" name="to.customerCode" size="30" class="txtbox" onchange="validateCustCode(this)"  readOnly="true" /><input type="hidden" id="customerIds'
									+ rowCount + '" name="to.customerIds" />',
							'<input type="text"    class="txtbox" size="2" id="noOfPieces'
									+ rowCount
									+ '" name="to.numOfPieces" maxlength="3" value="1" onblur="redirectToChildCNPage(this,'
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'cnPincode'
									+ rowCount
									+ '\');"/> <input type="hidden" id="childCns'
									+ rowCount
									+ '" name="to.childCNDetails"/><input type="hidden" id="cnrdetails'
									+ rowCount
									+ '" name="to.cnrAddressDtls"/><input type="hidden" id="cnedetails'
									+ rowCount
									+ '" name="to.cneAddressDtls"/><input type="hidden" id="deliveryTimeMapId'
									+ rowCount + '" name="to.dlvTimeMapIds"/>',
							'<input type="text"    class="txtbox" size="6" maxlength="6" id="cnPincode'
									+ rowCount
									+ '" name="to.pincodes"  onblur="validatePincode(this);validateParentAndChildCnWt(this, '
									+ rowCount
									+ ');getCNRateDtlsParcel('
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightAW'
									+ rowCount
									+ '\');"/><input type="hidden" id="pinIds'
									+ rowCount
									+ '" name="to.pincodeIds" value=""/>',
							'<input type="text"    class="txtbox" size="12" id="destCities'
									+ rowCount
									+ '" name="to.destCities"   readonly="readonly" onkeypress = "return callEnterKey(event, document.getElementById(\'destination'
									+ rowCount
									+ '\'),false);" tabindex="-1" /><input type="hidden" id="cityIds'
									+ rowCount + '" name="to.cityIds"/>',
							'<input type="text" name="weight" id="weightAW'
									+ rowCount
									+ '" class="txtbox width30" onchange="getCNRateDtlsParcel('
									+ rowCount
									+ ');"  size="10" maxlength="4" onkeypress="return onlyNumberAndEnterKeyNavWeightCredParcel(event,this, \'weightGmAW'
									+ rowCount
									+ '\','
									+ rowCount
									+ ');" /><span class="lable">.</span> <input type="text" name="weight" id="weightGmAW'
									+ rowCount
									+ '" class="txtbox width30"   size="10" maxlength="3" onchange="getCNRateDtlsParcel('
									+ rowCount
									+ ');" onblur="validateChildPopUpDetailsParcel(this);"  onkeypress="return enterKeyForGram(event,this, \'contentCode'
									+ rowCount
									+ '\','
									+ rowCount
									+ ');" /> <input type="hidden" id="cnActualWeight'
									+ rowCount + '" name="to.actualWeights"/>',
							'<input type="text" name="weight" id="weightVW'
									+ rowCount
									+ '" class="txtbox width30"  size="15" readonly="true" tabindex="-1" onkeypress = "return callEnterKey(event, document.getElementById(\'volWeight'
									+ rowCount
									+ '\'),false);" /><span class="lable">.</span> <input type="text" name="weight" id="weightGmVW'
									+ rowCount
									+ '" class="txtbox width30"   size="11" readonly="true" tabindex="-1" onkeypress = "return callEnterKey(event, document.getElementById(\'volWeight'
									+ rowCount
									+ '\'),false);" /><input type="hidden" id="volWeight'
									+ rowCount
									+ '" name="to.volWeights"/>  <a href=#><img src="images/calculator.png" alt="calculate volume"  tabindex="-1" onclick="redirectToVolumetricWeight('
									+ rowCount
									+ ');"/></a><input type="hidden" id="length'
									+ rowCount
									+ '" name="to.lengths"/><input type="hidden" id="height'
									+ rowCount
									+ '" name="to.heights"/><input type="hidden" id="breath'
									+ rowCount
									+ '" name="to.breaths"/><input type="hidden" id="rowCountId" />',
							'<input type="text" name="weight" id="weightCW'
									+ rowCount
									+ '" class="txtbox width30"  size="15" tabindex="-1" readonly="true" /><span class="lable">.</span><input type="text" name="weight" id="weightGmCW'
									+ rowCount
									+ '" class="txtbox width30"   size="11" tabindex="-1" readonly="true" onblur="convertToFractionAW(this,3);"/><input type="hidden" id="cnChrgWeight'
									+ rowCount
									+ '" name="to.chargeableWeights"/><input type="hidden" id="consgPricingDtls'
									+ rowCount
									+ '" name="to.consgPricingDtls" value=""/>',

							'<input type="text"    class="txtbox width50"  size="40" id="contentCode'
									+ rowCount
									+ '" name="to.cnContentCodes"  maxlength="5" onfocus="setChargeableWt(this, '
									+ rowCount
									+ ');convertToFractionCW(this,3);" onchange="contentVal(this);" onkeypress="return callEnterKey(event, document.getElementById(\'contentName'
									+ rowCount
									+ '\'));"/>&nbsp;<select name="" class="selectBox width90" id="contentName'
									+ rowCount
									+ '" onblur="setContentDtls(this);" onkeypress="return callEnterKeyInContentCredit(event, '
									+ rowCount
									+ ',this.value);""><option value="">--Select--</option>'
									+ contentOpt
									+ '</select><input type="text" class="txtbox width90"  id="cnContentOther'
									+ rowCount
									+ '" name="to.otherCNContents"  maxlength="30" readonly="true" onkeypress="return callEnterKey(event, document.getElementById(\'declaredValue'
									+ rowCount
									+ '\'));" /><input type="hidden" id="contentIds'
									+ rowCount
									+ '" name="to.cnContentIds"/><input type="hidden" id="contentNames'
									+ rowCount
									+ '" name="to.cnContentNames"/>  ',
							'<input type="text"    class="txtbox" size="10" maxlength="10" id="declaredValue'
									+ rowCount
									+ '" name="to.declaredValues" onfocus="getInsuredByDtls('
									+ rowCount
									+ ');" onblur="isValidDecValue(this);" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'cnPaperWorkselect'
									+ rowCount + '\');" /> ',
							'<select name="" onchange="setPaperDetails(this)" class="selectBox width90"  onblur="isPaperWorkSelected(this);" id="cnPaperWorkselect'
									+ rowCount
									+ '" onkeypress="return callEnterKey(event, document.getElementById(\'cnPaperWorks'
									+ rowCount
									+ '\'));"><option value="">--Select--</option></select><input type="text"    class="txtbox width30"  id="cnPaperWorks'
									+ rowCount
									+ '" name="to.paperRefNum" maxlength="99" onkeypress="return callEnterKey(event, document.getElementById(\'insuaranceNo'
									+ rowCount
									+ '\'));" /> <input type="hidden" id="cnPaperWorkIds'
									+ rowCount
									+ '" name="to.cnPaperWorkIds"/> <input type="hidden" id="paperWorkNames'
									+ rowCount
									+ '" name="to.cnPaperWorkNames"/> <input type="hidden" id="bookingIds'
									+ rowCount
									+ '" name="to.bookingIds" value=""/><input type="hidden" id="pickupRunsheetNos'
									+ rowCount
									+ '" name="to.pickupRunsheetNos" value=""/>',
							'<select name="to.insuaranceNos" class="txtbox"  id="insuaranceNo'
									+ rowCount
									+ '" onkeypress="return callEnterKey(event, document.getElementById(\'policyNos'
									+ rowCount
									+ '\'));" onblur="isValidInsuredBy('
									+ rowCount
									+ ');"><option value="">--Select--</option>'
									+ insOpt + '</select>  ',
							'<input type="text"    class="txtbox" size="10" id="policyNos'
									+ rowCount
									+ '" name="to.policyNos" maxlength="30" onblur="isPolicy(this)" onkeypress="return callEnterKey(event, document.getElementById(\'refNos'
									+ rowCount + '\'));"/>',
							'<input type="text"    class="txtbox" size="10" id="refNos'
									+ rowCount
									+ '" name="to.refNos" maxlength="30"  onkeypress="return callEnterKeyInRefNoParcel(event,'
									+ rowCount + ');"/>',
							'<input type="text"    class="txtbox" size="8" maxlength="6" id="codOrLcAmts'
									+ rowCount
									+ '" name="to.codOrLCAmts" onkeypress="return callEnterKeyInLCAmtParcel(event,'
									+ rowCount + ');"     disabled="true"/>',
							'<input type="text"    class="txtbox" size="10" maxlength="20" id="lcBankNames'
									+ rowCount
									+ '" name="to.lcBankNames"  onkeypress="return callEnterKeyLCBankName(event, document.getElementById(\'lcBankNames'
									+ rowCount + '\'));" disabled="true"/>',
							'<input type="text"    class="txtbox" size="8" id="amounts'
									+ rowCount
									+ '" name="to.amounts"   readonly="true" tabindex="-1"  /><input type="hidden" id="weightCapturedModes'
									+ rowCount
									+ '" name="to.weightCapturedModes" value="M"/>', ]);
	rowCount++;
	updateSrlNo('booking');
	return rowCount - 1;
	$("#cnNumber" + rowId).focus();
}

function callEnterKeyLCBankName(e, obj) {
	var roNo = getRowId(obj, "lcBankNames");
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var row = parseInt(roNo) + 1;
		if (!isNull(document.getElementById("cnNumber" + row))) {
			getCNRateDtlsParcelWithoutFocus(roNo, "normal");
			document.getElementById("cnNumber" + row).focus();
		} else {
			getCNRateDtlsParcel(roNo, "normal");
		}

		return false;
	}
	return true;
}

function callEnterKeyInLCAmtParcel(e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key > 31 && (key < 48 || key > 57)) {
		return false;
	} else if (key == 13) {
		var consgNumber = document.getElementById("cnNumber" + rowCount).value;
		var productCode = consgNumber.substring(4, 5).toUpperCase();
		if (productCode == "D") {
			document.getElementById("lcBankNames" + rowCount).focus();
		} else {
			var row = parseInt(rowCount) + 1;
			if (!isNull(document.getElementById("cnNumber" + row))) {
				getCNRateDtlsParcelWithoutFocus(rowCount, "normal");
				document.getElementById("cnNumber" + row).focus();
			} else {
				getCNRateDtlsParcel(rowCount, "normal");
			}

		}
		return false;
	}
}

/**
 * isCNEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCNEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnNumber");
	var cnNumber = document.getElementById("cnNumber" + rowNo);
	if (isNull(cnNumber.value)) {
		alert("Please Enter CN Number.");
		setTimeout(function() {
			document.getElementById("cnNumber" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/**
 * isPinEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isPinEmpty(pinObj) {
	rowNo = getRowId(pinObj, "weightAW");
	var cnNumber = document.getElementById("cnPincode" + rowNo);
	if (isNull(cnNumber.value)) {
		alert("Please Enter Pincode.");
		setTimeout(function() {
			document.getElementById("cnPincode" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/**
 * addNewRow
 * 
 * @param rowNo
 * @author sdalli
 */
function addNewRow(selectedRowId) {
	var table = document.getElementById("booking");
	var lastRow = table.rows.length - 1;
	var isNewRow = false;

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (rowId == selectedRowId && i == lastRow) {
			isNewRow = true;
		}
	}
	var rowId = 0;
	if (isNewRow) {
		rowId = fnClickAddRow();
		$("#cnNumber" + rowId).focus();
	} else {
		rowId = parseInt(selectedRowId) + 1;
	}

}
/**
 * isPolicy
 * 
 * @param obj
 * @returns {Boolean}
 * @author sdalli
 */
function isPolicy(obj) {
	rowNo = getRowId(obj, "policyNos");
	var insDetails = document.getElementById("insuaranceNo" + rowNo).value;
	var policyNo = document.getElementById("policyNos" + rowNo).value;
	if (!isNull(insDetails)) {
		if (isNull(policyNo) && IS_POLICY_MANDATORY == "Y") {
			alert('Please enter the policy Number');
			setTimeout(function() {
				document.getElementById("policyNos" + rowNo).focus();
			}, 10);
			return false;
		} else {
			isPolicyNo = false;
		}
	}
}

function isPaperWorkSelected(obj) {
	rowNo = getRowId(obj, "cnPaperWorkselect");
	var paperWork = obj;
	var prdValue = paperWork.options[paperWork.selectedIndex].value;

	if (paperWork.options.length > 1) {
		if (isNull(prdValue)) {
			alert("Please Select Paperworks");
			setTimeout(function() {
				document.getElementById("cnPaperWorkselect" + rowNo).focus();
			}, 10);
			return false;
		}
	}
}

/**
 * isValidDecValue
 * 
 * @param obj
 * @author sdalli
 */
function isValidDecValue(obj) {
	rowNo = getRowId(obj, "declaredValue");
	var bookingType = document.getElementById("bookingType").value;
	ROW_COUNT = rowNo;
	var decVal = obj.value;
	var decValue = parseFloat(decVal);
	if (decValue == "0" || isNull(decVal)) {
		alert("Please enter a non-zero declared value.");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
	url = './baBookingParcel.do?submitName=validateDeclaredvalue&declaredVal='
			+ decVal + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setBookingDtlsResponse);

}

/**
 * setBookingDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function setBookingDtlsResponse(data) {
	if (!isNull(data)) {
		if (data.isValidDeclaredVal == "N") {
			alert('Declared Value Max limit exceeded.');
			document.getElementById("declaredValue" + ROW_COUNT).value = "";
			setTimeout(function() {
				document.getElementById("declaredValue" + ROW_COUNT).focus();
			}, 10);
		} else {
			getPaperWorks(ROW_COUNT);
			getInsuarnceConfigDtls(ROW_COUNT);
			getCNRateDtlsParcel(ROW_COUNT);
		}
	}
}

/**
 * getInsuarnceConfigDtls
 * 
 * @param obj
 * @author sdalli
 */
function getInsuarnceConfigDtls(rowNo) {
	// rowNo = getRowId(obj, "insuaranceNo");
	ROW_COUNT = rowNo;
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue" + rowNo).value;
	var insNo = document.getElementById("insuaranceNo" + rowNo).value;
	DEC_VAL = decVal;
	url = './baBookingParcel.do?submitName=getInsuarnceConfigDtls&declaredVal='
			+ decVal + '&insNo=' + insNo + '&bookingType=' + bookingType;
	/*
	 * jQuery.ajax({ url : url, success : function(data) { setInsDtls(data); }
	 * });
	 */
	ajaxCallWithoutFormAsyncRequest(url, setInsDtlsResponse);
}

/**
 * setInsDtlsResponse
 * 
 * @param data
 * @author sdalli
 */

function setInsDtlsResponse(data) {
	isPolicyNo = false;
	if (!isNull(data)) {
		var insDetails = data;
		if (insDetails.length == 1) {
			for ( var i = 0; i < insDetails.length; i++) {
				if (insDetails[i].trasnStatus != "NOINSURENCEMAPPING") {
					var contentValueArr = document
							.getElementById('insuaranceNo' + ROW_COUNT);
					for ( var i = 0; i < contentValueArr.length; i++) {
						var selectObj = contentValueArr[i];
						var selectedVal = selectObj.value;
						if (selectedVal == insDetails[i].insuredById) {
							selectObj.selected = 'selected';
						}
						if (DEC_VAL >= MAX_DECLARED_VALUE) {
							if (selectedVal == 1) {
								contentValueArr.remove(i);
							}
						} else {
							getInsuredByDtls(ROW_COUNT);
						}

					}
				} else {
					alert('There is no proper mapping for declared value hence not allowed.');
					document.getElementById("declaredValue" + ROW_COUNT).value = "";
					setTimeout(function() {
						document.getElementById("declaredValue" + ROW_COUNT)
								.focus();
					}, 10);
				}
			}

		}
		/*
		 * var insDetails = data; if (insDetails.trasnStatus !=
		 * "NOINSURENCEMAPPING") { var contentValueArr =
		 * document.getElementById('insuaranceNo' + ROW_COUNT); for ( var i = 0;
		 * i < contentValueArr.length; i++) { var selectObj =
		 * contentValueArr[i]; var selectedVal = selectObj.value; if
		 * (selectedVal == insDetails.insuredById) { selectObj.selected =
		 * 'selected'; } if (DEC_VAL >= MAX_DECLARED_VALUE) { if (selectedVal ==
		 * 1) { contentValueArr.remove(i); isPolicyNo = true; } } else {
		 * getInsuredByDtls(ROW_COUNT); }
		 *  } } else { alert('There is no proper mapping for declared value
		 * hence not allowed.'); document.getElementById("declaredValue" +
		 * ROW_COUNT).value = ""; setTimeout(function() {
		 * document.getElementById("declaredValue" + ROW_COUNT).focus(); }, 10); }
		 */
	}
}

function printCallBackCNRateDetails(data, rowCount) {
	var rateDtlsFormat = "";
	var finalPrice = "";
	var fuelChg = "";
	var riskSurChg = "";
	var topayChg = "";
	var airportHandlingChg = "";
	var splChg = "";
	var serviceTax = "";
	var eduCessChg = "";
	var higherEduCessChg = "";
	var freightChg = "";
	var rateType = "R";
	document.getElementById("consgPricingDtls" + rowCount).value = "";
	document.getElementById("amounts" + rowCount).value="";
	
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			setTimeout(function() {
				document.getElementById("cnNumber" + rowCount).focus();
			}, 10);
			// addNewRow(rowNo);
		} else {
			if (cnRateDetails.finalPrice != null
					&& cnRateDetails.finalPrice > 0) {
				finalPrice = cnRateDetails.finalPrice;
			} else {
				finalPrice = "0.00";
			}
			if (cnRateDetails.fuelChg != null && cnRateDetails.fuelChg > 0) {
				fuelChg = cnRateDetails.fuelChg;
			} else {
				fuelChg = "0.00";
			}
			if (cnRateDetails.riskSurChg != null
					&& cnRateDetails.riskSurChg > 0) {
				riskSurChg = cnRateDetails.riskSurChg;
			} else {
				riskSurChg = "0.00";
			}
			if (cnRateDetails.topayChg != null && cnRateDetails.topayChg > 0) {
				topayChg = cnRateDetails.topayChg;
			} else {
				topayChg = "0.00";
			}
			if (cnRateDetails.airportHandlingChg != null
					&& cnRateDetails.airportHandlingChg > 0) {
				airportHandlingChg = cnRateDetails.airportHandlingChg;
			} else {
				airportHandlingChg = "0.00";
			}
			if (cnRateDetails.splChg != null && cnRateDetails.splChg > 0) {
				splChg = cnRateDetails.splChg;
			} else {
				splChg = "0.00";
			}
			if (cnRateDetails.serviceTax != null
					&& cnRateDetails.serviceTax > 0) {
				serviceTax = cnRateDetails.serviceTax;
			} else {
				serviceTax = "0.00";
			}
			if (cnRateDetails.eduCessChg != null
					&& cnRateDetails.eduCessChg > 0) {
				eduCessChg = cnRateDetails.eduCessChg;
			} else {
				eduCessChg = "0.00";
			}
			if (cnRateDetails.higherEduCessChg != null
					&& cnRateDetails.higherEduCessChg > 0) {
				higherEduCessChg = cnRateDetails.higherEduCessChg;
			} else {
				higherEduCessChg = "0.00";
			}
			if (cnRateDetails.freightChg != null
					&& cnRateDetails.freightChg > 0) {
				freightChg = cnRateDetails.freightChg;
			} else {
				freightChg = "0.00";
			}
			if (cnRateDetails.rateType != null && cnRateDetails.rateType != "") {
				rateType = cnRateDetails.rateType;
			}

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg + "#" + rateType;
			document.getElementById("consgPricingDtls" + rowCount).value = rateDtlsFormat;
			document.getElementById("amounts" + rowCount).value = finalPrice;
			validateFields(rowCount);
			var amount = document.getElementById("amounts" + rowCount).value;
			if (isNull(amount)) {
				alert("Please calculate rate for consignment at row no: "
						+ rowCount);
				setTimeout(function() {
					document.getElementById("cnNumber" + rowCount).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			addNewRow(rowCount);
		}

	}
	jQuery.unblockUI();
}

/**
 * setChargeableWt
 * 
 * @param obj
 * @author sdalli
 */
function setChargeableWt(obj, rowNo) {
	// rowNo = getRowId(obj, "weightGmAW");
	var actWtStr = document.getElementById("cnActualWeight" + rowNo).value;
	var volWtStr = document.getElementById("volWeight" + rowNo).value;

	if (!isNull(actWtStr) || !isNull(volWtStr)) {
		var actWt = parseFloat(actWtStr);
		var volWt = parseFloat(volWtStr);
		if (volWt > actWt) {
			var keyValuekg = volWtStr.split(".")[0];
			var keyValueGm = volWtStr.split(".")[1];
			if (keyValuekg != "" && keyValuekg != null && keyValuekg != "null"
					&& keyValuekg != undefined && isNANCheck(keyValuekg)) {

				if (keyValuekg.length == 0) {
					document.getElementById("weightCW" + rowNo).value += "0";
					keyValuekg += "0";
				}
				document.getElementById("weightCW" + rowNo).value = keyValuekg;
			} else {
				document.getElementById("weightCW" + rowNo).value = "0";
				// keyValuekg="0";
			}

			if (keyValueGm != "" && keyValueGm != null && keyValueGm != "null"
					&& keyValueGm != undefined && isNANCheck(keyValuekg)) {
				if (keyValueGm.length == 0) {
					document.getElementById("weightGmCW" + rowNo).value = "000";
					keyValueGm += "000";
				} else if (keyValueGm.length == 1) {
					document.getElementById("weightGmCW" + rowNo).value += "00";
					keyValueGm += "00";
				} else if (keyValueGm.length == 2) {
					document.getElementById("weightGmCW" + rowNo).value += "0";
					keyValueGm += "0";
				}

				document.getElementById("weightGmCW" + rowNo).value = keyValueGm;
			} else {

				document.getElementById("weightGmCW" + rowNo).value = "000";
				// keyValueGm = "000";
			}

			document.getElementById("cnChrgWeight" + rowNo).value = volWtStr;

		}

		else {
			var keyValuekg = actWtStr.split(".")[0];
			var keyValueGm = actWtStr.split(".")[1];
			if (keyValuekg != "" && keyValuekg != null && keyValuekg != "null"
					&& keyValuekg != undefined && isNANCheck(keyValuekg)) {

				if (keyValuekg.length == 0) {
					document.getElementById("weightCW" + rowNo).value += "0";
					keyValuekg += "0";
				}
				document.getElementById("weightCW" + rowNo).value = keyValuekg;
			} else {
				document.getElementById("weightCW" + rowNo).value = "0";
				// keyValuekg="0";
			}

			if (keyValueGm != "" && keyValueGm != null && keyValueGm != "null"
					&& keyValueGm != undefined && isNANCheck(keyValuekg)) {
				if (keyValueGm.length == 0) {
					document.getElementById("weightGmCW" + rowNo).value = "000";
					keyValueGm += "000";
				} else if (keyValueGm.length == 1) {
					document.getElementById("weightGmCW" + rowNo).value += "00";
					keyValueGm += "00";
				} else if (keyValueGm.length == 2) {
					document.getElementById("weightGmCW" + rowNo).value += "0";
					keyValueGm += "0";
				}

				document.getElementById("weightGmCW" + rowNo).value = keyValueGm;
			} else {

				document.getElementById("weightGmCW" + rowNo).value = "000";
				// keyValueGm = "000";
			}

			document.getElementById("cnChrgWeight" + rowNo).value = actWtStr;

		}
	}

}

function validateParentAndChildCnWt(obj, rowNo) {
	// Child Cns validation
	var noOfPcs = document.getElementById("noOfPieces" + rowNo).value;
	var chilsCNDtls = document.getElementById("childCns" + rowNo).value;
	var actWtStr = document.getElementById("cnActualWeight" + rowNo).value;
	if (parseInt(noOfPcs) > 1 && !isNull(chilsCNDtls)) {
		validateParentAndChildCnsWeight(actWtStr, chilsCNDtls, rowNo);
	}
}

// Calculating rate
function getCNRateDtlsParcel(rowNo, value) {
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('customerIds' + rowNo).value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var insuredBy = document.getElementById('insuaranceNo' + rowNo).value;
	var rateType = "CC";
	var consgType = "";
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowNo).value;
	codOrLcAmt = parseFloat(codOrLcAmt);
	var lcAmt = "";
	var codAmt = "";
	if (productCode == "L" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	} else if (productCode == "D" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		lcAmt = codOrLcAmt;
	} else if (productCode == "T"
			&& (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	}
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (validateRateInputsParcel(rowNo)) {
		showProcessing();
		url = './baBookingDox.do?submitName=calculateRateForConsignment&productCode='
				+ productCode
				+ "&consgType="
				+ consgType
				+ "&destPincode="
				+ pincode
				+ "&consgWeight="
				+ finalWeight
				+ "&rateType="
				+ rateType
				+ "&bookingOfficeId="
				+ originOfficeId
				+ "&customerId="
				+ customerId
				+ "&declaredValue="
				+ declaredValue
				+ "&insuredBy="
				+ insuredBy
				+ "&priorityService="
				+ SELECTED_SERVICED_ON
				+ "&consgNumber="
				+ consgNumber + "&codAmt=" + codAmt + "&lcAmt=" + lcAmt;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetails(req, rowNo);
			}
		});
	} else {

		if (!isNull(value)) {
			if (value == "popup") {
				document.getElementById("weightAW" + rowNo).focus();
			} else {
				validateFields(rowNo);
			}
		}

	}

}

function getCNRateDtlsParcelWithoutFocus(rowNo, value) {
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('customerIds' + rowNo).value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var insuredBy = document.getElementById('insuaranceNo' + rowNo).value;
	var rateType = "CC";
	var consgType = "";
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowNo).value;
	codOrLcAmt = parseFloat(codOrLcAmt);
	var lcAmt = "";
	var codAmt = "";
	if (productCode == "L" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	} else if (productCode == "D" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		lcAmt = codOrLcAmt;
	} else if (productCode == "T"
			&& (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	}
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (validateRateInputsParcel(rowNo)) {
		showProcessing();
		url = './baBookingDox.do?submitName=calculateRateForConsignment&productCode='
				+ productCode
				+ "&consgType="
				+ consgType
				+ "&destPincode="
				+ pincode
				+ "&consgWeight="
				+ finalWeight
				+ "&rateType="
				+ rateType
				+ "&bookingOfficeId="
				+ originOfficeId
				+ "&customerId="
				+ customerId
				+ "&declaredValue="
				+ declaredValue
				+ "&insuredBy="
				+ insuredBy
				+ "&priorityService="
				+ SELECTED_SERVICED_ON
				+ "&consgNumber="
				+ consgNumber + "&codAmt=" + codAmt + "&lcAmt=" + lcAmt;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetailsWithoutFocus(req, rowNo);
			}
		});
	} else {

		if (!isNull(value)) {
			if (value == "popup") {
				document.getElementById("weightAW" + rowNo).focus();
			} else {
				validateFields(rowNo);
			}
		}

	}

}

function printCallBackCNRateDetailsWithoutFocus(data, rowCount) {
	var rateDtlsFormat = "";
	var finalPrice = "";
	var fuelChg = "";
	var riskSurChg = "";
	var topayChg = "";
	var airportHandlingChg = "";
	var splChg = "";
	var serviceTax = "";
	var eduCessChg = "";
	var higherEduCessChg = "";
	var freightChg = "";
	var rateType = "R";
	document.getElementById("consgPricingDtls" + rowCount).value = "";
	document.getElementById("amounts" + rowCount).value="";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			setTimeout(function() {
				document.getElementById("cnNumber" + rowCount).focus();
			}, 10);
			// addNewRow(rowNo);
		} else {
			if (cnRateDetails.finalPrice != null
					&& cnRateDetails.finalPrice > 0) {
				finalPrice = cnRateDetails.finalPrice;
			} else {
				finalPrice = "0.00";
			}
			if (cnRateDetails.fuelChg != null && cnRateDetails.fuelChg > 0) {
				fuelChg = cnRateDetails.fuelChg;
			} else {
				fuelChg = "0.00";
			}
			if (cnRateDetails.riskSurChg != null
					&& cnRateDetails.riskSurChg > 0) {
				riskSurChg = cnRateDetails.riskSurChg;
			} else {
				riskSurChg = "0.00";
			}
			if (cnRateDetails.topayChg != null && cnRateDetails.topayChg > 0) {
				topayChg = cnRateDetails.topayChg;
			} else {
				topayChg = "0.00";
			}
			if (cnRateDetails.airportHandlingChg != null
					&& cnRateDetails.airportHandlingChg > 0) {
				airportHandlingChg = cnRateDetails.airportHandlingChg;
			} else {
				airportHandlingChg = "0.00";
			}
			if (cnRateDetails.splChg != null && cnRateDetails.splChg > 0) {
				splChg = cnRateDetails.splChg;
			} else {
				splChg = "0.00";
			}
			if (cnRateDetails.serviceTax != null
					&& cnRateDetails.serviceTax > 0) {
				serviceTax = cnRateDetails.serviceTax;
			} else {
				serviceTax = "0.00";
			}
			if (cnRateDetails.eduCessChg != null
					&& cnRateDetails.eduCessChg > 0) {
				eduCessChg = cnRateDetails.eduCessChg;
			} else {
				eduCessChg = "0.00";
			}
			if (cnRateDetails.higherEduCessChg != null
					&& cnRateDetails.higherEduCessChg > 0) {
				higherEduCessChg = cnRateDetails.higherEduCessChg;
			} else {
				higherEduCessChg = "0.00";
			}
			if (cnRateDetails.freightChg != null
					&& cnRateDetails.freightChg > 0) {
				freightChg = cnRateDetails.freightChg;
			} else {
				freightChg = "0.00";
			}
			if (cnRateDetails.rateType != null && cnRateDetails.rateType != "") {
				rateType = cnRateDetails.rateType;
			}

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg + "#" + rateType;
			document.getElementById("consgPricingDtls" + rowCount).value = rateDtlsFormat;
			document.getElementById("amounts" + rowCount).value = finalPrice;
			validateFields(rowCount);
			var amount = document.getElementById("amounts" + rowCount).value;
			if (isNull(amount)) {
				alert("Please calculate rate for consignment at row no: "
						+ rowCount);
				setTimeout(function() {
					document.getElementById("cnNumber" + rowCount).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			// addNewRow(rowCount);
		}

	}
	jQuery.unblockUI();
}

/**
 * saveOrUpdateCreditCustomerBookingParcel
 * 
 * @author sdalli
 */
function saveOrUpdateCreditCustomerBookingParcel() {
	if (validateParcelMandatoryFields()) {
		jQuery(":input").attr("disabled", false);
		showProcessing();
		url = './creditCustomerBookingParcel.do?submitName=saveOrUpdateCreditCustBookingParcel&isWeighingMachineConnected=' + isWeighingMachineConnected;
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#creditCustomerBookingParcelForm").serialize(),
			success : function(req) {
				printCallBackSave(req);
			}
		});
	}
}

/**
 * printCallBackSave
 * 
 * @param data
 * @author sdalli
 */
function printCallBackSave(data) {
	var results = new Array();
	results = data.split("#");
	if (results[0] == "SUCCESS" && results[1] == "Y") {
		alert(cnBooked);
	} else if (results[0] == "FAILURE" && results[1] == "N") {
		alert(cnNotBooked);
	} else {
		alert(cnPartiallyBooked + "\n" + results[1]);
	}
	redirectPagecredit();
	jQuery.unblockUI();
}

/**
 * redirectToVolumetricWeight
 * 
 * @param rowCount
 * @author sdalli
 */
function redirectToVolumetricWeight(rowCount) {

	url = "./volumetricWeight.do?rowCount=" + rowCount;
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=200,left = 412,top = 184');

}

/**
 * getPaperWorks
 * 
 * @param ROW_COUNT
 * @author sdalli
 */
function getPaperWorks(ROW_COUNT) {

	var declaredValue = document.getElementById('declaredValue' + ROW_COUNT).value;
	var pincode = document.getElementById('cnPincode' + ROW_COUNT).value;
	var docType = "";
	var docTypeIdName = document.getElementById('consgTypeName').value;
	if (docTypeIdName != null)
		docType = docTypeIdName.split("#")[1];
	if (pincode != null && pincode != "") {
		var url = "creditCustomerBookingParcel.do?submitName=getPaperWorks&pincode="
				+ pincode
				+ "&declaredValue="
				+ declaredValue
				+ "&docType="
				+ docType;
		ajaxCallWithoutFormAsyncRequest(url, getPaperWorksResponse);
	}
}

/**
 * getPaperWorksResponse
 * 
 * @param data
 * @author sdalli
 */
function getPaperWorksResponse(data) {
	// var rowCount = getRowId(obj,"declaredValue");
	var content = document.getElementById('cnPaperWorkselect' + ROW_COUNT);
	content.innerHTML = "";
	defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(data)) {
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cnPaperWorkId + '#' + this.cnPaperWorkCode;
			option.appendChild(document.createTextNode(this.cnPaperWorkName));
			content.appendChild(option);
		});
	}

	// jQuery.unblockUI();
}

/**
 * cancelDetails
 * 
 * @author sdalli
 */
function cancelDetails() {
	if (promptConfirmation("cancel")) {
		// $('#booking').dataTable().fnClearTable();
		// rowCount = 1;
		// fnClickAddRow();
		var docType = "PPX";
		url = "creditCustomerBookingParcel.do?submitName=viewCreditCustomerBookingParcel&docType="
				+ docType;
		document.creditCustomerBookingParcelForm.action = url;
		document.creditCustomerBookingParcelForm.submit();
	}
}

/**
 * contentVal
 * 
 * @param obj
 * @author sdalli
 */
function contentVal(obj) {
	var isValidContent = "";
	var rowCountName = getRowId(obj, "contentCode");
	ROW_COUNT = rowCountName;
	var contentValueArr = document.getElementById('contentName' + rowCountName);
	document.getElementById('contentIds' + rowCountName).value = "";
	for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var selectedCode = selectedVal.split("#")[1];
		var conetntId = selectedVal.split("#")[0];
		if (selectedCode == obj.value) {
			document.getElementById('contentIds' + rowCountName).value = conetntId;
			selectObj.selected = 'selected';
			isValidContent = "Y";
			break;
		} else {
			isValidContent = "N";
		}
	}
	if (isValidContent == "N") {
		document.getElementById('contentCode' + rowCountName).value = "";
		document.getElementById('contentName' + rowCountName).value = "";
		alert("Invalid content code.");
		setTimeout(function() {
			document.getElementById("contentCode" + rowCountName).focus();
		}, 10);
	}
}

/**
 * paperWrkVal
 * 
 * @param obj
 * @author sdalli
 */
function paperWrkVal(obj) {
	var isValidContent = "";
	var rowCountName = getRowId(obj, "cnPaperWorks");
	ROW_COUNT = rowCountName;
	var contentValueArr = document.getElementById('cnPaperWorkselect'
			+ rowCountName);
	document.getElementById('cnPaperWorkIds' + rowCountName).value = "";
	for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var selectedCode = selectedVal.split("#")[1];
		var paperId = selectedVal.split("#")[0];
		if (selectedCode == obj.value) {
			document.getElementById('cnPaperWorkIds' + rowCountName).value = paperId;
			selectObj.selected = 'selected';
			isValidContent = "Y";
			break;
		} else {
			isValidContent = "N";
		}
	}
	if (isValidContent == "N") {
		document.getElementById('cnPaperWorks' + rowCountName).value = "";
		document.getElementById('cnPaperWorkselect' + rowCountName).value = "";
		alert("Invalid content code.");
		setTimeout(function() {
			document.getElementById("cnPaperWorks").focus();
		}, 10);
	}

}

/**
 * setPaperDetails
 * 
 * @param obj
 * @author sdalli
 */
function setPaperDetails(obj) {
	var rowCountName = getRowId(obj, "cnPaperWorkselect");
	document.getElementById('cnPaperWorks' + rowCountName).value = "";
	document.getElementById('cnPaperWorkIds' + rowCountName).value = "";
	document.getElementById('paperWorkNames' + rowCountName).value = "";

	var e = document.getElementById("cnPaperWorkselect" + rowCountName);
	var paperName = e.options[e.selectedIndex].text;
	document.getElementById("paperWorkNames" + rowCountName).value = paperName;

	ROW_COUNT = rowCountName;
	var paperValue = document
			.getElementById('cnPaperWorkselect' + rowCountName).value;
	if (paperValue != "") {

		var paperCode = paperValue.split("#")[1];
		var paperId = paperValue.split("#")[0];

		document.getElementById('cnPaperWorkIds' + rowCountName).value = paperId;

	}

}

/**
 * setContentDtls
 * 
 * @param obj
 * @author sdalli
 */
function setContentDtls(obj) {
	var rowCountName = getRowId(obj, "contentName");
	document.getElementById('contentCode' + rowCountName).value = "";
	document.getElementById('contentIds' + rowCountName).value = "";
	var e = document.getElementById("contentName" + rowCountName);
	var contentName = e.options[e.selectedIndex].text;
	document.getElementById("contentNames" + rowCountName).value = contentName;
	ROW_COUNT = rowCountName;
	var contentValue = document.getElementById('contentName' + rowCountName).value;
	if (contentValue != "" && contentValue != "") {
		document.getElementById("cnContentOther" + rowCountName).value = "";
		document.getElementById("cnContentOther" + rowCountName).readOnly = true;
		var contentCode = contentValue.split("#")[1];
		var contentId = contentValue.split("#")[0];
		document.getElementById('contentIds' + rowCountName).value = contentId;
		document.getElementById('contentCode' + rowCountName).value = contentCode;
		if (contentCode == "999") {
			document.getElementById("cnContentOther" + rowCountName).readOnly = false;
			setTimeout(function() {
				document.getElementById("cnContentOther" + rowCountName)
						.focus();
			}, 10);
		}
	}
}

function redirectToChildCNPage(obj, rowCount) {

	var rowCountName = getRowId(obj, "noOfPieces");
	var pieces = document.getElementById('noOfPieces' + rowCountName).value;
	var processCode = "BOOK";
	PIECES_OLD = PIECES_NEW;
	PIECES_NEW = pieces;
	if (pieces != null && pieces != "" && pieces > 1) {
		url = "childCNPopup.do?&pieces=" + pieces + '&rowCount=' + rowCount
				+ '&processCode=' + processCode;
		window
				.open(
						url,
						'newWindow',
						'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');

		document.getElementById("rowCountId").value = rowCount;

	}
}

/**
 * validateParcelMandatoryFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateParcelMandatoryFields() {
	var isValid = true;
	var table = document.getElementById("booking");
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var cnNumber = document.getElementById("cnNumber" + rowId).value;
		if (rowId == 1) {
			isValid = validateMandatoryFields(rowId, i);
			if (!isValid) {
				return false;
			}
		} else if (!isNull(cnNumber) && rowId > 1) {
			isValid = validateMandatoryFields(rowId, i);
			if (!isValid) {
				return false;
			}
		}
	}
	return isValid;
}

function validateMandatoryFields(rowId, count) {
	var isValid = true;
	var cnNumber = document.getElementById("cnNumber" + rowId).value;
	var custCode = document.getElementById("custCode" + rowId).value;
	var cnPincode = document.getElementById("cnPincode" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAW" + rowId).value;
	var weightAW = document.getElementById("weightAW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;
	var contentCode = document.getElementById("contentCode" + rowId).value;
	var content = document.getElementById("contentName" + rowId).value;
	var otherContent = document.getElementById("cnContentOther" + rowId);
	var declaredValue = document.getElementById("declaredValue" + rowId).value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowId).value;
	var policyNo = document.getElementById("policyNos" + rowId).value;
	var noOfPcs = document.getElementById("noOfPieces" + rowId);
	var lcBankNames = document.getElementById("lcBankNames" + rowId).value;
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowId).value;

	var consgSeries = cnNumber.substring(4, 5);
	var rowNum = "at Row No :" + count + ".";
	if (isNull($.trim(cnNumber))) {
		alert("Please Enter CN Number " + rowNum);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(custCode))) {
		alert("Please Enter Customer " + rowNum);
		setTimeout(function() {
			document.getElementById("custCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode))) {
		alert("Please Enter Pincode " + rowNum);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight " + rowNum);
		setTimeout(function() {
			document.getElementById("weightAW" + rowId).value = "";
			document.getElementById("weightAW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Charge Weight " + rowNum);
		setTimeout(function() {
			document.getElementById("weightCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(content)) || isNull($.trim(contentCode))) {
		alert("Please Enter Content " + rowNum);
		setTimeout(function() {
			document.getElementById("contentName" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var contentNameValue = "";
	if (!isNull(content)) {
		contentNameValue = content.split("#")[1];
	}
	if (contentNameValue == '999') {
		if (isNull($.trim(otherContent.value))) {
			alert("Please enter other content at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("cnContentOther" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}

	}
	if (isNull($.trim(declaredValue))) {
		alert("Please enter declared value " + rowNum);
		setTimeout(function() {
			document.getElementById("declaredValue" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var paperWork = document.getElementById("cnPaperWorkselect" + rowId);
	var prdValue = paperWork.options[paperWork.selectedIndex].value;

	if (paperWork.options.length > 1) {
		if (isNull(prdValue)) {
			alert("Please Select Paperworks " + rowNum);
			setTimeout(function() {
				document.getElementById("cnPaperWorkselect" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (!isNull(insuaranceNo) && insuaranceNo == '2') {
		if (isNull($.trim(policyNo))) {
			alert("Please enter policy number at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("policyNos" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (!isNull(noOfPcs) && isNull($.trim(noOfPcs.value))) {
		alert("Please enter the valid No Of Pieces at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("pieces" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;

	} else if (noOfPcs.value > 1) {
		var childCNDetails = document.getElementById("childCns" + rowId).value;
		if (isNull($.trim(childCNDetails))) {
			alert("Please capture child consignment details " + rowNum);
			setTimeout(function() {
				document.getElementById("noOfPieces" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (consgSeries == "D" || consgSeries == "d") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			alert("Please enter LC amount at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("codOrLcAmts" + rowId).focus();
				document.getElementById('codOrLcAmts' + rowId).value = "";
			}, 10);
			isValid = false;
			return isValid;
		}
		if (isNull($.trim(lcBankNames))) {
			alert("Please enter LC Bank Name at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("lcBankNames" + rowId).focus();
				document.getElementById("lcBankNames" + rowId).value = "";

			}, 10);
			isValid = false;
			return isValid;
		}

	}

	if (consgSeries == "L" || consgSeries == "l") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			alert("Please enter COD amount at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("codOrLcAmts" + rowId).focus();
				document.getElementById('codOrLcAmts' + rowId).value = "";
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	var amount = document.getElementById("amounts" + rowId).value;
	if (isNull($.trim(amount))) {
		alert("Please calculate rate for consignment at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	isValid = validateConsigneeConsignorDetails(rowId);
	return isValid;
}

function validateFields(rowId, count) {
	var isValid = true;
	var cnNumber = document.getElementById("cnNumber" + rowId).value;
	var custCode = document.getElementById("custCode" + rowId).value;
	var cnPincode = document.getElementById("cnPincode" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAW" + rowId).value;
	var weightAW = document.getElementById("weightAW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;
	var contentCode = document.getElementById("contentCode" + rowId).value;
	var content = document.getElementById("contentName" + rowId).value;
	var otherContent = document.getElementById("cnContentOther" + rowId);
	var declaredValue = document.getElementById("declaredValue" + rowId).value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowId).value;
	var policyNo = document.getElementById("policyNos" + rowId).value;
	var noOfPcs = document.getElementById("noOfPieces" + rowId);
	var lcBankNames = document.getElementById("lcBankNames" + rowId).value;
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowId).value;
	var consgSeries = cnNumber.substring(4, 5);
	var rowNum = "at Row No :" + rowId + ".";
	if (isNull($.trim(cnNumber))) {
		alert("Please Enter CN Number " + rowNum);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(custCode))) {
		alert("Please Enter Customer " + rowNum);
		setTimeout(function() {
			document.getElementById("custCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode))) {
		alert("Please Enter Pincode " + rowNum);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight " + rowNum);
		setTimeout(function() {
			document.getElementById("weightAW" + rowId).value = "";
			document.getElementById("weightAW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Charge Weight " + rowNum);
		setTimeout(function() {
			document.getElementById("weightCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(content)) || isNull($.trim(contentCode))) {
		alert("Please Enter Content " + rowNum);
		setTimeout(function() {
			document.getElementById("contentName" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var contentNameValue = "";
	if (!isNull(content)) {
		contentNameValue = content.split("#")[1];
	}
	if (contentNameValue == '999') {
		if (isNull($.trim(otherContent.value))) {
			alert("Please enter other content at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("cnContentOther" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}

	}
	if (isNull($.trim(declaredValue))) {
		alert("Please enter declared value " + rowNum);
		setTimeout(function() {
			document.getElementById("declaredValue" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (!isNull(insuaranceNo) && insuaranceNo == '2') {
		if (isNull($.trim(policyNo))) {
			alert("Please enter policy number at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("policyNos" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (!isNull(noOfPcs)) {
		noOfPcs = parseInt(noOfPcs.value);
		if (isNull(noOfPcs)) {
			alert("Please enter the valid No Of Pieces at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("pieces" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;

		}

		if (noOfPcs > 1) {
			var childCNDetails = document.getElementById("childCns" + rowId).value;
			if (isNull($.trim(childCNDetails))) {
				alert("Please capture child consignment details " + rowNum);
				setTimeout(function() {
					document.getElementById("noOfPieces" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		}

	}

	if (consgSeries == "D" || consgSeries == "d") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			alert("Please enter LC amount at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("codOrLcAmts" + rowId).focus();
				document.getElementById('codOrLcAmts' + rowId).value = "";
			}, 10);
			isValid = false;
			return isValid;
		}
		if (isNull($.trim(lcBankNames))) {
			alert("Please enter LC Bank Name at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("lcBankNames" + rowId).focus();
				document.getElementById("lcBankNames" + rowId).value = "";
			}, 10);
			isValid = false;
			return isValid;
		}

	}

	if (consgSeries == "L" || consgSeries == "l") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			alert("Please enter COD amount at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("codOrLcAmts" + rowId).focus();
				document.getElementById('codOrLcAmts' + rowId).value = "";
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	return isValid;
}

/**
 * capturedWeightWithParam
 * 
 * @param data
 * @param rowId
 * @author sdalli
 */
function capturedWeightInGridParcel(data, rowId) {
	var capturedWeight = eval('(' + data + ')');
	var flag = true;
	wmWeightActual = parseFloat(wmWeightActual);
	if ((isNull(capturedWeight) || capturedWeight == -1 || capturedWeight == -2)
			&& isNull(wmWeightActual)) {
		flag = false;
		alert("Weight reached to zero.");
		var consgNumber = document.getElementById("cnNumber" + rowId).value;
		document.getElementById("cnNumber" + rowId).value = "";
		document.getElementById("cnNumber" + rowId).focus();
		document.getElementById("cnNumber" + rowId).readOnly = false;
		for ( var j = 0; j < usedConsignments.length; j++) {
			if (usedConsignments[j] == consgNumber) {
				usedConsignments[j] = null;
			}
		}
		wmWeightActual = 0.0;
		return false;

	} else {
		wmWeight = parseFloat(wmWeightActual) - parseFloat(capturedWeight);
		wmWeight = parseFloat(wmWeight).toFixed(3);
		wmWeightActual = parseFloat(capturedWeight).toFixed(3);

		if (wmWeight < 0) {

			/* document.getElementById("weightAW" + rowId).value =0;
			 document.getElementById("weightGmAW" + rowId).value = '000';*/

			flag = false;
			alert("Negative Weight Reached.");
			var consgNumber = document.getElementById("cnNumber" + rowId).value;
			document.getElementById("cnNumber" + rowId).value = "";
			document.getElementById("cnNumber" + rowId).focus();
			document.getElementById("cnNumber" + rowId).readOnly = false;
			for ( var j = 0; j < usedConsignments.length; j++) {
				if (usedConsignments[j] == consgNumber) {
					usedConsignments[j] = null;
				}
			}
			wmWeightActual = 0.0;
			return false;
		} else {

			document.getElementById("weightAW" + rowId).value = wmWeight
					.split(".")[0];
			document.getElementById("weightGmAW" + rowId).value = wmWeight
					.split(".")[1];

		}

		flag = true;
	}
	disableEnableWeightField(rowId, flag);
}

/**
 * disableEnableWeightField
 * 
 * @param rowId
 * @param flag
 * @author sdalli
 */
function disableEnableWeightField(rowId, flag) {
	document.getElementById("weightAW" + rowId).readOnly = flag;
	document.getElementById("weightGmAW" + rowId).readOnly = flag;
}

function setRateCompomentDtls(consgRateDtls, consgRate) {
	var rateCompomentDtls = null;
	rateCompomentDtls = consgRate.rateComponentId + "#"
			+ consgRate.rateComponentCode + "#" + consgRate.calculatedValue;
	if (isNull(consgRateDtls))
		consgRateDtls = rateCompomentDtls;
	else
		consgRateDtls = consgRateDtls + "," + rateCompomentDtls;
	return consgRateDtls;
}

function getInsuredByDtls(rowCount) {
	var url = './creditCustomerBookingParcel.do?submitName=getInsuredDtls';
	jQuery.ajax({
		url : url,
		type : "POST",
		success : function(req) {
			papulateInsuredByDtls(req, rowCount);
		}
	});
}

function papulateInsuredByDtls(data, rowCount) {
	var insuredBy = "";
	var option;
	if (rowCount > 0)
		insuredBy = document.getElementById('insuaranceNo' + rowCount);
	else
		insuredBy = document.getElementById("insuaranceNo");
	insuredBy.innerHTML = "";

	var insuredDetails = eval('(' + data + ')');
	if (insuredDetails != null && insuredDetails != "") {
		var noOfInsuredBy = insuredDetails.length;
		for (i = 0; i < noOfInsuredBy; i++) {
			addOptionTODropDown(insuredBy.id, insuredDetails[i].insuredByDesc,
					insuredDetails[i].insuredById);
		}
	}
}

function isValidInsuredBy(rowCount) {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue" + rowCount).value;
	var insuredBy = document.getElementById("insuaranceNo" + rowCount).value;
	url = './creditCustomerBookingParcel.do?submitName=validateInsuarnceConfigDtls&declaredVal='
			+ decVal
			+ '&bookingType='
			+ bookingType
			+ '&insuredBy='
			+ insuredBy;
	ajaxCallWithoutForm(url, validateInsDtlsResponse);
}

function validateInsDtlsResponse(data) {
	if (!isNull(data)) {
		var insDetails = data;
		if (insDetails.trasnStatus != "NOINSURENCEMAPPING") {
			IS_POLICY_MANDATORY = insDetails.isPolicyNoMandatory;
		}
	}
}

function validateRateInputsParcel(rowNo) {
	var isValid = true;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var customerId = document.getElementById('customerIds' + rowNo).value;
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowNo).value;
	codOrLcAmt = parseFloat(codOrLcAmt);
	var lcBankName = document.getElementById('lcBankNames' + rowNo).value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var contentCode = document.getElementById("contentCode" + rowNo).value;
	var content = document.getElementById("contentName" + rowNo).value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowNo).value;
	var policyNo = document.getElementById("policyNos" + rowNo).value;
	var paperWork = document.getElementById("cnPaperWorkselect" + rowNo);
	var prdValue = paperWork.options[paperWork.selectedIndex].value;

	if (!isNull(consgNumber) && !isNull(pincode) && !isNull(finalWeight)
			&& !isNaN(finalWeight) && !isNull(customerId)
			&& !isNull(declaredValue) && isValidPincode == true
			&& isPolicyNo == false
			&& (!isNull($.trim(content)) || !isNull($.trim(contentCode)))) {

		if (productCode == "L" && (isNull(codOrLcAmt) || isNaN(codOrLcAmt))) {
			isValid = false;
		}
		if (productCode == "D"
				&& (isNull(codOrLcAmt) || isNaN(codOrLcAmt) || isNull(lcBankName))) {
			isValid = false;
		}
		if ((paperWork.options.length > 1) && isNull(prdValue)) {
			isValid = false;
		}
		if (!isNull(insuaranceNo) && insuaranceNo.value == '2'
				&& isNull($.trim(policyNo.value))) {
			isValid = false;
		}
		return isValid;
	} else {
		return false;
	}

}

function callEnterKeyInRefNoParcel(e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var consgNumber = document.getElementById("cnNumber" + rowCount).value;
		var productCode = consgNumber.substring(4, 5).toUpperCase();
		if (productCode == "L" || productCode == "T" || productCode == "D") {
			document.getElementById("codOrLcAmts" + rowCount).focus();
		} else {
			var row = parseInt(rowCount) + 1;
			if (!isNull(document.getElementById("cnNumber" + row))) {
				getCNRateDtlsParcelWithoutFocus(rowCount, "normal");
				document.getElementById("cnNumber" + row).focus();
			} else {
				getCNRateDtlsParcel(rowCount, "normal");
			}

		}
		return false;
	}
	return true;
}

function validateParentAndChildCnsWeight(parentCnWeight, chilsCNDtls, rowCount) {
	if (!isNull(chilsCNDtls)) {
		var consgWeight = parseFloat(parentCnWeight);
		var childCns = new Array();
		var childCnsTotalWeight = 0.00;
		childCns = chilsCNDtls.split("#");
		for ( var i = 0; i < childCns.length; i++) {
			var childCn = childCns[i];
			var childCNWeight = childCn.split(",")[1];
			childCnsTotalWeight = childCnsTotalWeight
					+ parseFloat(childCNWeight);
		}
		if (parseFloat(consgWeight) < parseFloat(childCnsTotalWeight)) {
			childCnsTotalWeight = parseFloat(childCnsTotalWeight).toFixed(3);
			alert("Parent consignment weight can't be less than total of child consignments weight.("
					+ childCnsTotalWeight + ")");
			document.getElementById('weightAW' + rowCount).value = "";
			document.getElementById('weightGmAW' + rowCount).value = "";
			document.getElementById('weightCW' + rowCount).value = "";
			document.getElementById('weightGmCW' + rowCount).value = "";
			document.getElementById("cnActualWeight" + rowCount).value = "";
			setTimeout(function() {
				document.getElementById("weightAW" + rowCount).focus();
			}, 10);
		}
	}
}

function validateChildPopUpDetailsParcel(obj) {
	var isValid = true;
	var rowIds = getRowId(obj, "weightGmAW");
	var cnPincode = document.getElementById("cnPincode" + rowIds).value;
	var cnrPopDtls = document.getElementById("cnrdetails" + rowIds).value;
	var cnePopDtls = document.getElementById("cnedetails" + rowIds).value;
	var cnNumber = document.getElementById('cnNumber' + rowIds).value;
	var cnValue = cnNumber.charAt(4);

	if (cnValue == "p" || cnValue == "P" || cnValue == "t" || cnValue == "T") {
		if (!isNull(cnPincode)) {
			if (isNull(cnrPopDtls)) {
				alert("Please Enter consignor Details in PopUp  At Row: "
						+ rowIds);
				setTimeout(function() {
					document.getElementById("cnPincode" + rowIds).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			if (isNull(cnePopDtls)) {
				alert("Please Enter ConsigneeDetails in PopUp At Row: "
						+ rowIds);
				setTimeout(function() {
					document.getElementById("cnPincode" + rowIds).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			getCNRateDtlsParcel(rowIds);
		}
	}
}

function onlyNumberAndEnterKeyNavWeightCredParcel(e, Obj, focusId, rowId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13
			&& validateWeightForCredParcel(e, Obj, focusId, rowId)) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}

}
function validateWeightForCredParcel(e, Obj, focusId, rowId) {
	setChargeableWt(Obj, rowId);
	var weightGmAW = document.getElementById("weightGmAW" + rowId).value;
	var weightAW = document.getElementById("weightAW" + rowId).value;
	var actualWt = document.getElementById("cnActualWeight" + rowId).value;
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which;
	if (isNaN(actualWt)) {
		actualWt = "0.000";
	}
	actualWt = weightAW + "." + weightGmAW;
	actualWt = parseFloat(actualWt).toFixed(3);
	if (isEmptyWeight(actualWt) || isNaN(actualWt)) {
		if (key == 13) {
			if (focusId != ("weightGmAW" + rowId)) {
				alert("Please Enter Valid Weight.");
				document.getElementById("weightAW" + rowId).value = "";
				setTimeout(function() {
					document.getElementById("weightAW" + rowId).focus();
				}, 10);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	} else {
		return true;
	}

}
function enterKeyForGram(e, Obj, focusId, rowId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13
			&& validateWeightForCredParcel(e, Obj, focusId, rowId)) {
		convertToFractionAW(Obj, 3);
		setTimeout(function() {
			document.getElementById(focusId).focus();
		}, 10);
		validateParentAndChildCnWt(Obj, rowId);
		return false;
	} else {
		return true;
	}

}