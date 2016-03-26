var giCount = 1;
var rowCount = 1;
var MIN_DEC_VAL = "";
var DEC_VAL = "";
var IS_POLICY_MANDATORY = "N";
var MAX_DECLARED_VALUE = 100000;
$(document).ready(function() {
	var oTable = $('#booking').dataTable({
		"sScrollY" : "310",
		"sScrollX" : "200%",
		"sScrollXInner" : "160%",
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
	var rowId = rowCount;
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
									+ '" name="to.consgNumbers" maxlength="12" size="14" class="txtbox"  onblur="convertDOMObjValueToUpperCase(this);validateConsignment(this);" onkeypress="return OnlyAlphabetsAndNosAndEnter(this, event, \'cnPincode'
									+ rowCount + '\',\'CN Number\');"/>',
							'<input type="text"    class="txtbox" size="3" id="noOfPieces'
									+ rowCount
									+ '" name="to.numOfPcs" maxlength="3"  value="1" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'cnPincode'
									+ rowCount
									+ '\');" onblur="redirectToChildCNPage(this,'
									+ rowCount
									+ ');"/> <input type="hidden" id="childCns'
									+ rowCount
									+ '" name="to.childCNDetails"/>	<input type="hidden" id="priorityServicedOns'
									+ rowCount
									+ '" name="to.priorityServicedOns" value=""/><input type="hidden" id="cnrdetails'
									+ rowCount
									+ '" name="to.cnrAddressDtls"/><input type="hidden" id="cnedetails'
									+ rowCount
									+ '" name="to.cneAddressDtls"/><input type="hidden" id="deliveryTimeMapId'
									+ rowCount + '" name="to.dlvTimeMapIds"/>',
							'<input type="text"    class="txtbox" size="6" maxlength="6" id="cnPincode'
									+ rowCount
									+ '" name="to.pincodes"  onblur="validatePincode(this);validateParentAndChildCnWt(this, '
									+ rowCount
									+ ');getCNRateDtlsParcel(this,'
									+ rowCount
									+ ');"  onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightAW'
									+ rowCount
									+ '\');"/><input type="hidden" id="pinIds'
									+ rowCount
									+ '" name="to.pincodeIds" value=""/><input type="hidden" id="consgPricingDtls'
									+ rowCount
									+ '" name="to.consgPricingDtls" value=""/>',
							'<input type="text"    class="txtbox" size="10" id="destCities'
									+ rowCount
									+ '" name="to.destCities"   readonly="readonly" onkeypress = "return callEnterKey(event, document.getElementById(\'destination'
									+ rowCount
									+ '\'),false);" tabindex="-1" /><input type="hidden" id="cityIds'
									+ rowCount + '" name="to.cityIds"/>',
							'<input type="text" name="weight" id="weightAW'
									+ rowCount
									+ '"  class="txtbox"  size="5" maxlength="4" onchange="getCNRateDtlsParcel(this,'
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNavWeightBAParcel(event,this, \'weightGmAW'
									+ rowCount
									+ '\','
									+ rowCount
									+ ');"/><span class="lable">.</span> <input type="text" name="weight" id="weightGmAW'
									+ rowCount
									+ '" class="txtbox"  size="3" maxlength="3" onchange="getCNRateDtlsParcel(this,'
									+ rowCount
									+ '); " onblur="validateChildPopUpDetailsParcel(this);"  onkeypress="return enterKeyForGram(event,this, \'contentCode'
									+ rowCount
									+ '\','
									+ rowCount
									+ ');"/> <input type="hidden" id="cnActualWeight'
									+ rowCount + '" name="to.actualWeights"/>',
							'<input type="text" name="weight" id="weightVW'
									+ rowCount
									+ '" class="txtbox"  size="5" readonly="true" tabindex="-1" onkeypress = "return callEnterKey(event, document.getElementById(\'volWeight'
									+ rowCount
									+ '\'),false);" /><span class="lable">.</span> <input type="text" name="weight" id="weightGmVW'
									+ rowCount
									+ '" class="txtbox"  size="3" readonly="true" tabindex="-1" onkeypress = "return callEnterKey(event, document.getElementById(\'volWeight'
									+ rowCount
									+ '\'),false);"/><input type="hidden" id="volWeight'
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
									+ '" class="txtbox"  size="5" tabindex="-1" readonly="true" /><span class="lable">.</span><input type="text" name="weight" id="weightGmCW'
									+ rowCount
									+ '" class="txtbox"  size="3" tabindex="-1" readonly="true" /><input type="hidden" id="cnChrgWeight'
									+ rowCount
									+ '" name="to.chargeableWeights"/>',
							'<input type="text"    class="txtbox width50" size="40" id="contentCode'
									+ rowCount
									+ '" name="to.cnContentCodes" maxlength="5" onfocus="setChargeableWt(this, '
									+ rowCount
									+ ');" onchange="contentVal(this);" onkeypress="return callEnterKey(event,document.getElementById(\'contentName'
									+ rowCount
									+ '\'));"/>&nbsp;<select name="" class="selectBox width90" id="contentName'
									+ rowCount
									+ '" onblur="setContentDtls(this);" onkeypress="return callEnterKeyInContent(event, '
									+ rowCount
									+ ',this.value);"><option value="">--Select--</option>'
									+ contentOpt
									+ '</select>&nbsp;<input type="text" class="txtbox width50"  id="cnContentOther'
									+ rowCount
									+ '" name="to.otherCNContents" maxlength="30"  readonly="true" onkeypress="return callEnterKey(event, document.getElementById(\'declaredValue'
									+ rowCount
									+ '\'));"/><input type="hidden" id="contentIds'
									+ rowCount
									+ '" name="to.cnContentIds"/><input type="hidden" id="contentNames'
									+ rowCount
									+ '" name="to.cnContentNames"/>  ',
							'<input type="text"    class="txtbox" size="6" maxlength="10" id="declaredValue'
									+ rowCount
									+ '" name="to.declaredValues" onfocus="getInsuredByDtls('
									+ rowCount
									+ ');"   onblur="isValidDecValue(this);" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'cnPaperWorkselect'
									+ rowCount + '\');" /> ',
							'<select name="" onchange="setPaperDetails(this)"  onblur="isPaperWorkSelected(this);" class="selectBox width90" id="cnPaperWorkselect'
									+ rowCount
									+ '" onkeypress="return callEnterKey(event, document.getElementById(\'cnPaperWorks'
									+ rowCount
									+ '\'));"><option value="" >--Select--</option></select>&nbsp;<input type="text"    class="txtbox width50"  id="cnPaperWorks'
									+ rowCount
									+ '" name="to.paperRefNum" maxlength="99" onkeypress="return callEnterKey(event, document.getElementById(\'insuaranceNo'
									+ rowCount
									+ '\'));"/><input type="hidden" id="cnPaperWorkIds'
									+ rowCount
									+ '" name="to.cnPaperWorkIds"/> <input type="hidden" id="paperWorkNames'
									+ rowCount
									+ '" name="to.cnPaperWorkNames"/>',
							'<select name="to.insuaranceNos" class="txtbox" id="insuaranceNo'
									+ rowCount
									+ '" onblur="isValidInsuredBy('
									+ rowCount
									+ ');" onkeypress="return callEnterKey(event, document.getElementById(\'policyNos'
									+ rowCount
									+ '\'));"><option value="">--Select--</option>'
									+ insOpt + '</select>  ',
							'<input type="text"    class="txtbox" size="6" id="policyNos'
									+ rowCount
									+ '" name="to.policyNos" maxlength="30" onblur="isPolicy(this)" onkeypress="return callEnterKey(event, document.getElementById(\'refNos'
									+ rowCount + '\'));"/>',
							'<input type="text"    class="txtbox" size="6" maxlength="30" id="refNos'
									+ rowCount
									+ '" name="to.refNos"  onkeypress="return callEnterKeyInRefNoParcel(this,event,'
									+ rowCount + ');"/>',
							'<input type="text"    class="txtbox" size="6"  id="baAmt'
									+ rowCount
									+ '" name="to.baAmts" maxlength="6" readonly="true" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'codAmt'
									+ rowCount + '\');"  tabindex="-1"  />',
							'<input type="text"    class="txtbox" size="6" id="codAmt'
									+ rowCount
									+ '" name="to.codAmts"   tabindex="-1" readonly="true"  maxlength="6" onkeypress="return onlyNumberAndEnterKeyNavCODAmt(event,this, \'codAmt'
									+ rowCount + '\');"/>',
							'<input type="text"    class="txtbox" size="6" id="amounts'
									+ rowCount
									+ '" name="to.amounts"  readonly="true" tabindex="-1" /><input type="hidden" id="weightCapturedModes'
									+ rowCount
									+ '" name="to.weightCapturedModes" value="M"/>', ]);
	rowCount++;
	updateSrlNo('booking');
	return rowCount - 1;
	$("#cnNumber" + rowId).focus();
}

function callEnterKeyInRefNoParcel(obj, e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var cnNumber = document.getElementById("cnNumber" + rowCount);
		var productCode = cnNumber.value.substring(4, 5).toUpperCase();
		if (productCode == "T") {
			document.getElementById("baAmt" + rowCount).focus();
		} else {
			var row = parseInt(rowCount) + 1;
			if (!isNull(document.getElementById("cnNumber" + row))) {
				getCNRateDtlsParcelWithoutFocus(obj, rowCount, "normal");
				document.getElementById("cnNumber" + row).focus();
			} else {
				getCNRateDtlsParcel(obj, rowCount, "normal");
			}
		}
		return false;
	}
}
function onlyNumberAndEnterKeyNavCODAmt(e, obj) {
	var roNo = getRowId(obj, "codAmt");
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key > 31 && (key < 48 || key > 57)) {
		return false;
	} else if (key == 13) {
		var row = parseInt(roNo) + 1;
		if (!isNull(document.getElementById("cnNumber" + row))) {
			getCNRateDtlsParcelWithoutFocus(obj, roNo, "normal");
			document.getElementById("cnNumber" + row).focus();
		} else {
			getCNRateDtlsParcel(obj, roNo, "normal");
		}

		return false;
	}
}

/**
 * isValidInsuredBy
 * 
 * @param obj
 * @returns {Boolean}
 * @author sdalli
 */
function isValidInsuredBy(rowCount) {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue" + rowCount).value;
	var insuredBy = document.getElementById("insuaranceNo" + rowCount).value;
	url = './baBookingParcel.do?submitName=validateInsuarnceConfigDtls&declaredVal='
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

/**
 * isCNEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCNEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnPincode");
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
	rowNo = getRowId(pinObj, "cnActualWeight");
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
 * setPaperDetails
 * 
 * @param obj
 * @author sdalli
 */
function setPaperDetails(obj) {
	var rowCountName = getRowId(obj, "cnPaperWorkselect");
	document.getElementById('cnPaperWorks' + rowCountName).value = "";
	document.getElementById('paperWorkNames' + rowCountName).value = "";
	document.getElementById('cnPaperWorkIds' + rowCountName).value = "";

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
	if (!isNull(insDetails) && insDetails == '2') {
		if (isNull(policyNo) && IS_POLICY_MANDATORY == "Y") {
			alert('Please enter the policy Number');
			setTimeout(function() {
				document.getElementById("policyNos" + rowNo).focus();
			}, 10);
			return false;
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

function getinsuarnceconfigdtls(rowNo) {
	ROW_COUNT = rowNo;
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue" + rowNo).value;
	var insNo = document.getElementById("insuaranceNo" + rowNo).value;
	DEC_VAL = decVal;
	url = './baBookingParcel.do?submitName=getInsuarnceConfigDtls&declaredVal='
			+ decVal + '&insNo=' + insNo + '&bookingType=' + bookingType;
	ajaxCallWithoutFormAsyncRequest(url, setInsDtlsResponse);
}

function setInsDtlsResponse(data) {

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
	}

	/*
	 * if (!isNull(data)) { var insDetails = data; if (insDetails.trasnStatus !=
	 * "NOINSURENCEMAPPING") { var contentValueArr =
	 * document.getElementById('insuaranceNo' + ROW_COUNT); for ( var i = 0; i <
	 * contentValueArr.length; i++) { var selectObj = contentValueArr[i]; var
	 * selectedVal = selectObj.value; if (selectedVal == insDetails.insuredById) {
	 * selectObj.selected = 'selected'; } if (DEC_VAL >= MAX_DECLARED_VALUE) {
	 * if (selectedVal == 1) { contentValueArr.remove(i); } } else {
	 * getInsuredByDtls(ROW_COUNT); }
	 *  } } else { alert('There is no proper mapping for declared value hence
	 * not allowed.'); document.getElementById("declaredValue" +
	 * ROW_COUNT).value = ""; setTimeout(function() {
	 * document.getElementById("declaredValue" + ROW_COUNT).focus(); }, 10); } }
	 */}

/**
 * setCNRateDtls
 * 
 * @param data
 * @author sdalli
 */
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
	document.getElementById("amounts" + rowCount).value="";
	document.getElementById("consgPricingDtls" + rowCount).value = "";
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

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg;
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
			//var row = parseInt(rowCount) + 1;
			//if(isNaN(document.getElementById("cnNumber" + row))){
			addNewRow(rowCount);

		}

	}
	jQuery.unblockUI();
}

function validateRateInputsParcel(rowNo) {
	var isValid = true;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var customerId = document.getElementById('baId').value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var baAmt = document.getElementById("baAmt" + rowNo).value;
	var contentCode = document.getElementById("contentCode" + rowNo);
	var content = document.getElementById("contentName" + rowNo);
	var paperWork = document.getElementById("cnPaperWorkselect" + rowNo);
	var prdValue = paperWork.options[paperWork.selectedIndex].value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowNo);
	var policyNo = document.getElementById("policyNos" + rowNo);

	if (!isNull(consgNumber)
			&& !isNull(pincode)
			&& !isNull(finalWeight)
			&& !isEmptyWeight(finalWeight)
			&& finalWeight != "NaN"
			&& !isNull(customerId)
			&& !isNull(declaredValue)
			&& isValidPincode == true
			&& (!isNull($.trim(content.value)) || !isNull($
					.trim(contentCode.value)))) {
		if (productCode == "T" && (isNull(baAmt) || isNaN(baAmt))) {
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

// Calculating rate
function getCNRateDtlsParcel(Obj, rowNumber, value) {
	rowNo = rowNumber;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('baId').value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var insuredBy = document.getElementById('insuaranceNo' + rowNo).value;
	var rateType = "BA";
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	var cod = document.getElementById("codAmt" + rowNo).value;
	var codAmt = "";
	if (productCode == "T" && (!isNull(cod) && !isNaN(cod))) {
		codAmt = cod;
	}
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
				+ consgNumber + "&codAmt=" + codAmt;
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

function getCNRateDtlsParcelWithoutFocus(Obj, rowNumber, value) {
	rowNo = rowNumber;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('baId').value;
	var declaredValue = document.getElementById('declaredValue' + rowNo).value;
	var insuredBy = document.getElementById('insuaranceNo' + rowNo).value;
	var rateType = "BA";
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	var cod = document.getElementById("codAmt" + rowNo).value;
	var codAmt = "";
	if (productCode == "T" && (!isNull(cod) && !isNaN(cod))) {
		codAmt = cod;
	}
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
				+ consgNumber + "&codAmt=" + codAmt;
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
	document.getElementById("consgPricingDtls" + rowCount).value = "";
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

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg;
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
			//var row = parseInt(rowCount) + 1;
			//if(isNaN(document.getElementById("cnNumber" + row))){
			//addNewRow(rowCount);

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
	// rowNo = getRowId(obj, "contentCode");
	var actWtStr = document.getElementById("cnActualWeight" + rowNo).value;
	document.getElementById('cnChrgWeight' + rowNo).value = actWtStr;
	var volWtStr = document.getElementById("volWeight" + rowNo).value;

	if (!isNull(actWtStr) || !isNull(volWtStr)) {
		var actWt = parseFloat(actWtStr);
		var volWt = parseFloat(volWtStr);

		var keyValuekg = actWtStr.split(".")[0];
		var keyValueGm = actWtStr.split(".")[1];
		document.getElementById('weightCW' + rowNo).value = keyValuekg;
		document.getElementById('weightGmCW' + rowNo).value = keyValueGm;
		if (volWt != "") {
			var volWt = parseFloat(volWtStr);
			if (volWt > actWt) {
				var keyValuekg = volWtStr.split(".")[0];
				var keyValueGm = volWtStr.split(".")[1];

				if (keyValuekg != "" && keyValuekg != null
						&& keyValuekg != "null" && keyValuekg != undefined
						&& isNANCheck(keyValuekg)) {

					if (keyValuekg.length == 0) {
						document.getElementById("weightCW" + rowNo).value += "0";
						keyValuekg += "0";
					}
					document.getElementById("weightCW" + rowNo).value = keyValuekg;
				} else {
					document.getElementById("weightCW" + rowNo).value = "0";
					// keyValuekg="0";
				}

				if (keyValueGm != "" && keyValueGm != null
						&& keyValueGm != "null" && keyValueGm != undefined
						&& isNANCheck(keyValuekg)) {
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

				document.getElementById('cnChrgWeight' + rowNo).value = volWtStr;
			} else {
				var keyValuekg = actWtStr.split(".")[0];
				var keyValueGm = actWtStr.split(".")[1];

				if (keyValuekg != "" && keyValuekg != null
						&& keyValuekg != "null" && keyValuekg != undefined
						&& isNANCheck(keyValuekg)) {

					if (keyValuekg.length == 0) {
						document.getElementById("weightCW" + rowNo).value += "0";
						keyValuekg += "0";
					}
					document.getElementById("weightCW" + rowNo).value = keyValuekg;
				} else {
					document.getElementById("weightCW" + rowNo).value = "0";
					// keyValuekg="0";
				}

				if (keyValueGm != "" && keyValueGm != null
						&& keyValueGm != "null" && keyValueGm != undefined
						&& isNANCheck(keyValuekg)) {
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

				document.getElementById('cnChrgWeight' + rowNo).value = actWtStr;

			}
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
	var cnNumber = document.getElementById("cnNumber" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightCW = document.getElementById("weightCW" + rowId);
	var weightGmAW = document.getElementById("weightGmAW" + rowId);
	var weightAW = document.getElementById("weightAW" + rowId);
	var weightGmCW = document.getElementById("weightGmCW" + rowId);
	var contentCode = document.getElementById("contentCode" + rowId);
	var content = document.getElementById("contentName" + rowId);
	var otherContent = document.getElementById("cnContentOther" + rowId);
	var declaredValue = document.getElementById("declaredValue" + rowId).value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowId);
	var policyNo = document.getElementById("policyNos" + rowId);
	var noOfPcs = document.getElementById("noOfPieces" + rowId);

	if (isNull($.trim(cnNumber.value))) {
		alert("Please Enter CN Number at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please Enter Pincode at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var tempValAW = (weightAW.value + "." + weightGmAW.value);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight at row no: " + count);
		setTimeout(function() {
			document.getElementById("weightAW" + rowId).value = "";
			document.getElementById("weightAW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var tempValAW = (weightCW.value + "." + weightGmCW.value);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Charge Weight at row no: " + count);
		setTimeout(function() {
			document.getElementById("weightCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(content.value)) || isNull($.trim(contentCode.value))) {
		alert("Please Enter Content at row no: " + count);
		setTimeout(function() {
			document.getElementById("contentCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var contentNameValue = "";
	var cntValue = content.value;
	if (!isNull(cntValue)) {
		contentNameValue = cntValue.split("#")[1];
	}
	if (contentNameValue == '999') {
		if (isNull($.trim(otherContent.value))) {
			alert("Please enter other content at row no: " + count);
			setTimeout(function() {
				document.getElementById("cnContentOther" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (isNull($.trim(declaredValue))) {
		alert("Please enter declared value at row no:" + count);
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
			alert("Please Select Paperworks at row no:" + count);
			setTimeout(function() {
				document.getElementById("cnPaperWorkselect" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (!isNull(insuaranceNo) && insuaranceNo.value == '2') {
		if (isNull($.trim(policyNo.value))) {
			alert("Please enter policy number at row no: " + count);
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
			alert("Please enter the valid No Of Pieces at row no: " + count);
			setTimeout(function() {
				document.getElementById("pieces" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;

		} else if (noOfPcs > 1) {
			var childCNDetails = document.getElementById("childCns" + rowId).value;
			if (isNull($.trim(childCNDetails))) {
				alert("Please capture child consignment details at row no."
						+ count);
				setTimeout(function() {
					document.getElementById("noOfPieces" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		}
	}

	var baAmt = document.getElementById("baAmt" + rowId).value;
	var consgSeries = cnNumber.value.substring(4, 5).toUpperCase();
	if (consgSeries == "T") {
		baAmt = parseFloat(baAmt);
		if (isNull(baAmt) || isNaN(baAmt)) {
			alert("Please enter BA amount at row no: " + count);

			setTimeout(function() {
				document.getElementById('baAmt' + rowId).value = "";
				document.getElementById("baAmt" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	var amount = document.getElementById("amounts" + rowId).value;
	if (isNull($.trim(amount))) {
		alert("Please calculate rate for consignment at row no: " + count);
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
	var cnNumber = document.getElementById("cnNumber" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightCW = document.getElementById("weightCW" + rowId);
	var weightGmAW = document.getElementById("weightGmAW" + rowId);
	var weightAW = document.getElementById("weightAW" + rowId);
	var weightGmCW = document.getElementById("weightGmCW" + rowId);
	var contentCode = document.getElementById("contentCode" + rowId);
	var content = document.getElementById("contentName" + rowId);
	var otherContent = document.getElementById("cnContentOther" + rowId);
	var declaredValue = document.getElementById("declaredValue" + rowId).value;
	var insuaranceNo = document.getElementById("insuaranceNo" + rowId);
	var policyNo = document.getElementById("policyNos" + rowId);
	var noOfPcs = document.getElementById("noOfPieces" + rowId);

	if (isNull($.trim(cnNumber.value))) {
		alert("Please Enter CN Number at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please Enter Pincode at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var tempValAW = (weightAW.value + "." + weightGmAW.value);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("weightAW" + rowId).value = "";
			document.getElementById("weightAW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var tempValAW = (weightCW.value + "." + weightGmCW.value);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Charge Weight at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("weightCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(content.value)) || isNull($.trim(contentCode.value))) {
		alert("Please Enter Content at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("contentCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var contentNameValue = "";
	var cntValue = content.value;
	if (!isNull(cntValue)) {
		contentNameValue = cntValue.split("#")[1];
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
		alert("Please enter declared value at row no:" + rowId);
		setTimeout(function() {
			document.getElementById("declaredValue" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (!isNull(insuaranceNo) && insuaranceNo.value == '2') {
		if (isNull($.trim(policyNo.value))) {
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
				alert("Please capture child consignment details " + rowId);
				setTimeout(function() {
					document.getElementById("noOfPieces" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		}

	}

	var baAmt = document.getElementById("baAmt" + rowId).value;
	var consgSeries = cnNumber.value.substring(4, 5).toUpperCase();
	if (consgSeries == "T") {
		baAmt = parseFloat(baAmt);
		if (isNull(baAmt) || isNaN(baAmt)) {
			alert("Please enter BA amount at row no: " + rowId);

			setTimeout(function() {
				document.getElementById('baAmt' + rowId).value = "";
				document.getElementById("baAmt" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	return isValid;
}

/**
 * saveOrUpdateBABookingParcel
 * 
 * @author sdalli
 */
function saveOrUpdateBABookingParcel() {
	if (validateParcelMandatoryFields()) {
		showProcessing();
		url = './baBookingParcel.do?submitName=saveOrUpdateBABookingParcel&isWeighingMachineConnected=' + isWeighingMachineConnected;
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#baBookingParcelForm").serialize(),
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
/*
 * function printCallBackSave(data) {
 * 
 * if (data == "SUCCESS") { alert("Consignment booked successfully."); } else if
 * (data == "FAILURE") { alert("Exception occurred. Consignment not booked."); }
 * redirectPage(); jQuery.unblockUI(); }
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
	redirectPage();
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
					'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=200,left = 412,top = 184');

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
	ajaxCallWithoutFormAsyncRequest(url, setBookingDtlsResponse);

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
			getinsuarnceconfigdtls(ROW_COUNT);
			getCNRateDtlsParcel(ROW_COUNT);
		}
	}
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
		var url = "baBookingParcel.do?submitName=getPaperWorks&pincode="
				+ pincode + "&declaredValue=" + declaredValue + "&docType="
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
}

/**
 * cancelDetails
 * 
 * @author sdalli
 */
function cancelDetails() {
	if (promptConfirmation("cancel")) {
		/*
		 * document.getElementById('baCode').value = "";
		 * document.getElementById('baId').value = "";
		 * $('#booking').dataTable().fnClearTable(); rowCount = 1;
		 * fnClickAddRow(); document.getElementById("cnNumber1").focus();
		 */
		var docType = "PPX";
		url = "baBookingParcel.do?submitName=viewBABookingParcel&docType="
				+ docType;
		document.baBookingParcelForm.action = url;
		document.baBookingParcelForm.submit();
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
			document.getElementById("contentCode").focus();
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

/**
 * redirectToChildCNPage
 * 
 * @param obj
 * @param rowCount
 * @author sdalli
 */
function redirectToChildCNPage(obj, rowCount) {
	var rowCountName = getRowId(obj, "noOfPieces");
	var pieces = document.getElementById('noOfPieces' + rowCountName).value;
	PIECES_OLD = PIECES_NEW;
	PIECES_NEW = pieces;
	if (pieces != null && pieces != "" && pieces > 1) {
		var processCode = "BOOK";
		url = "childCNPopup.do?&pieces=" + pieces + '&rowCount=' + rowCount
				+ '&processCode=' + processCode;
		window
				.open(
						url,
						'newWindow',
						'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');

	}
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

			/*document.getElementById("weightAW" + rowId).value =0;
			document.getElementById("weightGmAW" + rowId).value = '000';*/

			flag = false;
			alert("Negative Weight reached.");
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
	disableEnableWeightFieldParel(rowId, flag);
}

/**
 * disableEnableWeightField
 * 
 * @param rowId
 * @param flag
 * @author sdalli
 */
function disableEnableWeightFieldParel(rowId, flag) {
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

function convertToFractionAWParcel(obj, digits) {
	rowNo = getRowId(obj, "weightGmAW");
	var kgValue = document.getElementById("weightAW" + rowNo).value;
	var gmValue = document.getElementById("weightGmAW" + rowNo).value;
	var finalWeight = parseFloat(kgValue + "." + gmValue);
	/*	if (gmValue.length > 0) {
	 gmValue = parseFloat(gmValue);
	 }
	 if (kgValue.length > 0) {
	 kgValue = parseFloat(kgValue);
	 }*/

	if (!isNull(gmValue)) {
		if (gmValue.length == 0) {
			document.getElementById("weightGmAW" + rowNo).value = "000";
			gmValue += "000";
		} else if (gmValue.length == 1) {
			document.getElementById("weightGmAW" + rowNo).value += "00";
			gmValue += "00";
		} else if (gmValue.length == 2) {
			document.getElementById("weightGmAW" + rowNo).value += "0";
			gmValue += "0";
		}
	} else {

		document.getElementById("weightGmAW" + rowNo).value = "000";
		gmValue = "000";
	}

	if (!isNull(kgValue)) {
		if (kgValue.length == 0) {
			document.getElementById("weightAW" + rowNo).value += "0";
			kgValue += "0";
		}
	} else {
		document.getElementById("weightAW" + rowNo).value = "0";
		// kgValue += "0";
		kgValue = "0";
	}
	document.getElementById("cnActualWeight" + rowNo).value = finalWeight;

}
function validateChildPopUpDetailsParcel(obj) {
	var isValid = true;
	var rowIds = getRowId(obj, "weightGmAW");
	var cnNumber = document.getElementById('cnNumber' + rowIds).value;
	var cnValue = cnNumber.charAt(4);
	var cnPincode = document.getElementById("cnPincode" + rowIds).value;
	var cnrPopDtls = document.getElementById("cnrdetails" + rowIds).value;
	var cnePopDtls = document.getElementById("cnedetails" + rowIds).value;
	if (!isNull(cnPincode)) {
		if (cnValue == "p" || cnValue == "P" || cnValue == "t"
				|| cnValue == "T") {
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
		}
	}

}

function onlyNumberAndEnterKeyNavWeightBAParcel(e, Obj, focusId, rowId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13
			&& validateWeightForBAParcel(e, Obj, focusId, rowId)) {
		setTimeout(function() {
			document.getElementById(focusId).focus();
		}, 10);
		return false;
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
			&& validateWeightForBAParcel(e, Obj, focusId, rowId)) {
		convertToFractionAWParcel(Obj, 3);
		setTimeout(function() {
			document.getElementById(focusId).focus();
		}, 10);
		validateParentAndChildCnWt(Obj, rowId);
		return false;
	} else {
		return true;
	}

}

function validateWeightForBAParcel(e, Obj, focusId, rowId) {
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
