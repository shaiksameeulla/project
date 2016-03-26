$(document).ready(function() {
	var oTable = $('#bplParcelGrid').dataTable({
		"sScrollY" : "250",
		"sScrollX" : "100%",
		"sScrollXInner" : "295%",
		"bInfo" : false,
		"bPaginate" : false,
		"bSort" : true,
		"bScrollCollapse" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});

	defaultChanges();
	getContentInsuredByPaperWorkList();
	// addBplParcelRow();
	//getWeightFromWeighingMachine();
});

/** The moduleName */
var moduleName = "inBplParcel";
/** The inBagManifestParcelTOArray */
var inBagManifestParcelTOArray = new Array();

// Using data grid
/** The weightKgFieldName */
var weightKgFieldName = "weightKg";
/** The weightGmFieldName */
var weightGmFieldName = "weightGm";
/** The actualWeightsFieldName */
var actualWeightsFieldName = "actualWeights";

/** The weightVWFieldName */
var weightVWFieldName = "weightVW";
/** The weightGmVWFieldName */
var weightGmVWFieldName = "weightGmVW";
/** The volWeightFieldName */
var volWeightFieldName = "volWeight";

/** The weightKgChFieldName */
var weightKgChFieldName = "weightKgCh";
/** The weightGmChFieldName */
var weightGmChFieldName = "weightGmCh";
/** The finalWeightsFieldName */
var finalWeightsFieldName = "finalWeights";

/** The cnContentsFieldName */
var cnContentsFieldName = "cnContents";
/** The declaredValuesFieldName */
var declaredValuesFieldName = "declaredValues";

/** The destPincodesFieldName */
var destPincodesFieldName = "destPincodes";

/** The number of pcs */
var noOfPcs = "numOfPcs";

var printUrl;

/** The Manifest no old Value */
var oldManifestNo = "";

/** Set header old details */
var originRegionVal = "";
var originCityVal = "";
var originOfficeVal = "";
var originOfficeTypeVal = "";

var consgNumbersFieldName = "consgNumbers";

/** The rowCount */
var rowCount = 1;

var MIN_DEC_VAL = 0;
var isAddRowInProcess = false;
/**
 * addBplParcelRow
 * 
 * @returns {Number}
 * @author narmdr
 */
function addBplParcelRow() {
	if(isAddRowInProcess){
		return;
	}
	isAddRowInProcess = true;
	$('#bplParcelGrid')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"   id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
							'<div id="serialNo' + rowCount + '">' + rowCount
									+ '</div>',
							'<input type="text" id="consgNumbers'
									+ rowCount
									+ '" name="to.consgNumbers" maxlength="12" size="20" class="txtbox width110" onchange="validateConsgNumber(this);" onfocus="storeConsgNumber(this.value);"  onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, \'destPincodes'+ rowCount +'\',\'Consignment No.\');"/>',
							'<input type="text" maxlength="6" id="destPincodes'
									+ rowCount
									+ '" name="to.destPincodes" size="20" class="txtbox width100" onchange="validatePincode('
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, noOfPcs,'
									+ rowCount
									+ ');" onfocus="setFocusToFieldIfEmptyById(\'consgNumbers'
									+ rowCount + '\');"/>',
							'<input type="text" id="destCityNames'
									+ rowCount
									+ '" name="to.destCityNames" size="20" class="txtbox width100" readonly="true"/>',
							'<input type="text" id="numOfPcs'
									+ rowCount
									+ '" name="to.numOfPcs" size="5" maxlength="3" class="txtbox" onkeypress="return validateNumberOfPcs(event, this, weightKgFieldName, '+rowCount+');" onblur="redirectToChildCNPage(this.value,'
									+ rowCount + ');"/>',
							'<input type="text" id="weightKg'
									+ rowCount
									+ '" maxlength="5" size="4" class="txtbox" style="text-align: right" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'
									+ rowCount
									+ ');" onblur="validateWeight(weightKgFieldName, weightGmFieldName, actualWeightsFieldName,'
									+ rowCount
									+ ');setTotalWeight();setFinalWeight('+rowCount+');"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGm'
									+ rowCount
									+ '" maxlength="3" size="4" class="txtbox" onblur="validateWeight(weightKgFieldName, weightGmFieldName, actualWeightsFieldName,'
									+ rowCount
									+ ');setTotalWeight();setFinalWeight('
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, \'cnContentCodes\','
									+ rowCount + ');" onfocus="clearWtGramIfEmpty(\'weightGm'+ rowCount +'\')"/>',
							'<input type="text" id="weightVW'
									+ rowCount
									+ '" maxlength="3" size="4" class="txtbox" onkeypress="return onlyNumeric(event);" style="text-align: right"  onblur="validateWeight(weightVWFieldName, weightGmVWFieldName, volWeightFieldName,'
									+ rowCount
									+ ');setFinalWeight('
									+ rowCount
									+ ');" readonly="true"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGmVW'
									+ rowCount
									+ '" maxlength="3" size="4" class="txtbox" onkeypress="return onlyNumeric(event);" onblur="validateWeight(weightVWFieldName, weightGmVWFieldName, volWeightFieldName,'
									+ rowCount
									+ ');setFinalWeight('
									+ rowCount
									+ ');" onfocus="setFinalWeight('
									+ rowCount
									+ ');" readonly="true" /> \
	<img src="images/calculator.png" alt="calculate volume" id="volWt'+rowCount+'"  onclick="redirectToVolumetricWeight('
									+ rowCount + ');"/>',
							'<input type="text" id="weightKgCh'
									+ rowCount
									+ '" maxlength="3" size="4" class="txtbox" onkeypress="return onlyNumeric(event);" style="text-align: right" readonly="true" onblur="validateWeight(weightKgChFieldName, weightGmChFieldName, finalWeightsFieldName,'
									+ rowCount
									+ ');"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGmCh'
									+ rowCount
									+ '" maxlength="3" size="4" class="txtbox" onkeypress="return onlyNumeric(event);" readonly="true" onblur="validateWeight(weightKgChFieldName, weightGmChFieldName, finalWeightsFieldName,'
									+ rowCount + ');"/>',
							'<input type="text" id="mobileNos'
									+ rowCount
									+ '" name="to.mobileNos" maxlength="10" size="20" class="txtbox width110" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, cnContentsFieldName,'
									+ rowCount + ');" onchange="validateMobileNo(this)";/>',
							'<input type="text" maxlength="3" class="txtbox width50" id="cnContentCodes'
									+ rowCount
									+ '" name="to.cnContentCodes" size="7" class="txtbox" onkeypress="return validateContents(event, this,\'C\',\'declaredValues'+rowCount+'\', '+rowCount+');"/> \
							<select name="to.cnContents" class="selectBox width90" onkeypress="return validateContents(event, this, \'N\',\'declaredValues'+rowCount+'\', '+rowCount+');" id="cnContents'
									+ rowCount
									+ '" onchange="setCnContentIdCode('
									+ rowCount
									+ ');"><option value="">--Select--</option></select> \
									<input type="text" class="txtbox width50" id="cnContentOther'
										+ rowCount
										+ '" name="to.cnContentOther"  onkeypress="return validateContents(event, this, \'O\',\'declaredValues'+rowCount+'\', '+rowCount+');"/> ',
							'<input type="text" maxlength="11" id="declaredValues'
									+ rowCount
									+ '" name="to.declaredValues" size="11" class="txtbox width100" onblur="fixFormatUptoTwoDecimalPlace(this);isValidDeclaredValue('
									+ rowCount + ');" onchange="getPaperWorks('+rowCount+');getInsuarnceConfigDtls(this);" onkeypress="return validateDeclareValue(event,this,\'consgNumbers' + rowCount+'\');" />',
							'<select name="to.cnPaperWorks" onkeypress="return enterKeyNavigationFocus4Grid(event, consgNumbers,'
									+ rowCount
									+ ');" id="cnPaperWorks'
									+ rowCount
									+ '" onchange="setCnPaperWorksIdCode('
									+ rowCount
									+ ');" class="selectBox width135"><option value="">--Select--</option></select> \
							<input type="text" id="paperRefNums'
									+ rowCount
									+ '" name="to.paperRefNums" size="7" class="txtbox"/>',
							'<select name="to.insuredByIds" id="insuredByIds'
									+ rowCount
									+ '"  onblur="validatePolicyToPayCod(' + rowCount+');" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');"/><option value="">--Select--</option></select>',
							'<input type="text" maxlength="11" id="policyNos'
									+ rowCount
									+ '" name="to.policyNos" size="8" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');" onblur="validatePolicyToPayCod(' + rowCount+');"/>',
							'<input type="text" maxlength="12" id="toPayAmts' + rowCount + '" name="to.toPayAmts" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');"  onblur="fixFormatUptoTwoDecimalPlace(this);validatePolicyToPayCod(' + rowCount+');"/>',
							'<input type="text" maxlength="6" id="codAmts' + rowCount + '" name="to.codAmts" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');" onchange="validateCodAmt(this);" onblur="fixFormatUptoTwoDecimalPlace(this);validatePolicyToPayCod(' + rowCount+');"/>',									
							'<input type="text" maxlength="20" id="baAmts'+ rowCount+ '" name="to.baAmts" size="7" class="txtbox" onblur="fixFormatUptoTwoDecimalPlace(this);" onkeypress="return onlyDecimal(event);"/>',
							'<input type="text" maxlength="20" id="lcBankNames'+ rowCount+ '" name="to.lcBankNames" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');" onblur="validatePolicyToPayCod(' + rowCount+');"/>',
							'<input type="text" onkeypress="return enterKeyNavigationFocus4Grid(event, \'remarks\','
									+  rowCount
									+ ');" id="refNos'
									+ rowCount
									+ '" name="to.refNos" size="10" class="txtbox"/>',
							'<select class="selectBox width145" onkeypress="return addNewRow(event,'+ rowCount+ ');" id="remarks'
									+ rowCount
									+ '" name="to.remarks">'  + REMARKS_OPTION
									+ '</select> \
	<input type="hidden" id="consignmentIds'
									+ rowCount
									+ '" name="to.consignmentIds"/> \
	<input type="hidden" id="consgOrgOffIds'
									+ rowCount
									+ '" name="to.consgOrgOffIds"/> \
	<input type="hidden" id="destPincodeIds'
									+ rowCount
									+ '" name="to.destPincodeIds"/> \
	<input type="hidden" id="childCns'
									+ rowCount
									+ '" name="to.childCns"/> \
	<input type="hidden" id="actualWeights'
									+ rowCount
									+ '" name="to.actualWeights"/> \
	<input type="hidden" id="volWeight'
									+ rowCount
									+ '" name="to.volWeights"/> \
	<input type="hidden" id="length'
									+ rowCount
									+ '" name="to.lengths"/> \
	<input type="hidden" id="breath'
									+ rowCount
									+ '" name="to.breadths"/> \
	<input type="hidden" id="height'
									+ rowCount
									+ '" name="to.heights"/> \
	<input type="hidden" id="finalWeights'
									+ rowCount
									+ '" name="to.finalWeights"/> \
	<input type="hidden" id="paperWorkIds'
									+ rowCount
									+ '" name="to.paperWorkIds"/> \
	<input type="hidden" id="cnContentIds'
									+ rowCount
									+ '" name="to.cnContentIds"/> \
	<input type="hidden" id="productIds'
									+ rowCount
									+ '" name="to.productIds"/> \
	<input type="hidden" id="consignmentTypeIds'
									+ rowCount
									+ '" name="to.consignmentTypeIds"/> \
	<input type="hidden" id="consignmentManifestIds'
									+ rowCount
									+ '" name="to.consignmentManifestIds"/> \
	<input type="hidden" id="priceIds'
									+ rowCount
									+ '" name="to.priceIds"/> \
	<input type="hidden" id="updateProcessIds'
									+ rowCount
									+ '" name="to.updateProcessIds"/> \
	<input type="hidden" id="bookingType'
									+ rowCount
									+ '" name="to.bookingTypes"/> \
	<input type="hidden" id="receivedStatus'
									+ rowCount
									+ '" name="to.receivedStatus" value="'
									+ notReceivedCode + '"/>' ]);

	// onkeypress="enterKeyNavigationFocus(event,\'destPincodes'+ rowCount
	// +'\');"
	// onkeypress="enterKeyNavigationFocus4Grid(event, destPincodesFieldName,'+
	// rowCount +');"

	// <input type="text" id="otherCNContents'+ rowCount +'"
	// name="to.otherCNContents" size="7" class="txtbox"/>
	/*
	 * <input type="hidden" id="chargeableWeights'+ rowCount +'"
	 * name="to.chargeableWeights"/> \
	 */
// onkeypress="return validateInsuredBy(event, this,\'policyNos' + rowCount+'\');"
	
/*	onblur="validateInsuredBy(event, '+rowCount+');"
		onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');
		validatePolicyToPayCod(' + rowCount+');
		 onkeypress="return validateToPayAmount(event, this);" 
		validateCODAmount(event, this,\'refNos'+rowCount+'\');
		onkeypress="return validateToPayAmount(event, this);"
		onkeypress="return validatePolicyNo(event, this, document.getElementById(\'toPayAmts'+rowCount+'\'));"
			*/
	populateContent(rowCount);
	populateInsuredBy(rowCount);
	updateSerialNoVal("bplParcelGrid");
	rowCount++;
	isAddRowInProcess = false;
	return rowCount - 1;
}

/**
 * defaultChanges
 * 
 * @author narmdr
 */
function defaultChanges() {
	document.getElementById("manifestNumber").focus();
	buttonDisableById("btnPrint");
}

/**
 * getContentInsuredByPaperWorkList
 * 
 * @author narmdr
 */
function getContentInsuredByPaperWorkList() {
	var url = "./inBagManifestParcel.do?submitName=getContentInsuredByPaperWorkList";
	ajaxCall(url, "inBagManifestParcelForm",
			populategetContentInsuredByPaperWorkList);
}

/**
 * populategetContentInsuredByPaperWorkList
 * 
 * @param data
 * @author narmdr
 */
function populategetContentInsuredByPaperWorkList(data) {
	inBagManifestParcelTOArray = eval('(' + data + ')');
	addBplParcelRow();
	setDataTableDefaultWidth();
}

/**
 * populateContent
 * 
 * @param rowId
 * @author narmdr
 */
function populateContent(rowId) {
	var cnContentTOs = inBagManifestParcelTOArray.cnContentTOs;
	clearDropDownList("cnContents" + rowId);
	if (isNull(cnContentTOs)) {
		return;
	}
	for ( var i = 0; i < cnContentTOs.length; i++) {
		addOptionTODropDown("cnContents" + rowId,
				cnContentTOs[i].cnContentName, cnContentTOs[i].cnContentId
						+ "~" + cnContentTOs[i].cnContentCode);
	}
}

/**
 * populateInsuredBy
 * 
 * @param rowId
 * @author narmdr
 */
function populateInsuredBy(rowId) {
	var insuredByTOs = inBagManifestParcelTOArray.insuredByTOs;
	clearDropDownList("insuredByIds" + rowId);
	if (isNull(insuredByTOs)) {
		return;
	}
	for ( var i = 0; i < insuredByTOs.length; i++) {
		addOptionTODropDown("insuredByIds" + rowId,
				insuredByTOs[i].insuredByDesc, insuredByTOs[i].insuredById);
	}
}

/**
 * redirectToVolumetricWeight
 * 
 * @param rowCount
 * @author narmdr
 */
function redirectToVolumetricWeight(rowCount) {
	if(!isNull($("#consignmentIds"+rowCount).val())){
		// Non-excess consignment
		disableFieldById("volWt" + rowCount);
		return;
	}
	url = "./volumetricWeight.do?rowCount=" + rowCount + "&processCode=" + PROCESS_IN_MANIFEST_BAG_PARCEL;
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');
}

/**
 * redirectToChildCNPage
 * 
 * @param pieces
 * @param rowCount
 * @author narmdr
 */
function redirectToChildCNPage(pieces, rowCount) {
	/*
	 * var rowCountName = getRowId(obj, "noOfPieces"); var pieces =
	 * document.getElementById('noOfPieces' + rowCountName).value; PIECES_OLD =
	 * PIECES_NEW; PIECES_NEW = pieces;
	 */

	var consignmentManifestIds = document
			.getElementById("consignmentManifestIds" + rowCount).value;
	if (!isNull(consignmentManifestIds)) {
		return;
	}

	if (isNull(pieces) || pieces <= 1) {
		document.getElementById("childCns" + rowCount).value = "";
		return;
	}
	if (pieces != null && pieces != "" && pieces > 1) {
		var processCode = "MANIFEST";
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
 * setTotalWeight
 * 
 * @author narmdr
 */
function setTotalWeight() {
	var totalWeight = 0;
	var table = document.getElementById('bplParcelGrid');

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weight = document.getElementById("actualWeights" + rowId).value;
		if (!isNull(weight)) {
			totalWeight += parseFloat(weight);
		}
	}
	var manifestWeightObj = document.getElementById("manifestWeight");
	manifestWeightObj.value = totalWeight;
	fixFormatUptoThreeDecimalPlace(manifestWeightObj);

	populateWeightInKgGmFieldByName(manifestWeightObj.value, "totalWeightKg",
			"totalWeightGm");
}

/**
 * populateWeightInKgGmFieldByName
 * 
 * @param weight
 * @param weightKgFieldName
 * @param weightGmFieldname
 * @param rowId
 * @author narmdr
 */
function populateWeightInKgGmFieldByName(weight, weightKgFieldName,
		weightGmFieldname, rowId) {
	weight += "";
	var weightKgObj;
	var weightGmObj;

	if (!isNull(rowId)) {
		weightKgObj = document.getElementById(weightKgFieldName + rowId);
		weightGmObj = document.getElementById(weightGmFieldname + rowId);
	} else {
		weightKgObj = document.getElementById(weightKgFieldName);
		weightGmObj = document.getElementById(weightGmFieldname);
	}

	if (!isNull(weight)) {
		weightKgObj.value = weight.split(".")[0];
		weightGmObj.value = weight.split(".")[1];
	}
	if(isEmptyWeight(weightKgObj.value)){
		weightKgObj.value = "0";
	}
	fixWeightFormatForGram(weightGmObj);
}

/**
 * validateWeight
 * 
 * @param weightKgFieldName
 * @param weightGmFieldname
 * @param weightFieldName
 * @param rowId
 * @author narmdr
 */
function validateWeight(weightKgFieldName, weightGmFieldname, weightFieldName,
		rowId) {
	/*
	 * if(!isNull(getValueByElementId("loadMovementId"))){ return; }
	 */
	// populateWeightInKgGmField(weight, weightKgFieldName, weightGmFieldname,
	// rowId);
	var weightKgObj = document.getElementById(weightKgFieldName + rowId);
	var weightGmObj = document.getElementById(weightGmFieldname + rowId);
	var weightsObj = document.getElementById(weightFieldName + rowId);

	fixWeightFormatForGram(weightGmObj);

	if (weightKgObj.value.length == 0) {
		weightKgObj.value = "0";
	}
	weightsObj.value = weightKgObj.value + "." + weightGmObj.value;
	setTotalWeight();
}

/**
 * addRow
 * 
 * @param selectedRowId
 * @author narmdr
 */
function addRow(selectedRowId) {
	var table = document.getElementById("bplParcelGrid");
	var lastRow = table.rows.length - 1;
	//var cnContentsObj = document.getElementById("cnContents" + selectedRowId);
	var isNewRow = false;

	if (!validateMandatoryRowFields(selectedRowId)) {
		//cnContentsObj.value = "";
		return;
	}

	var oldLastRowId = null;
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (rowId == selectedRowId && i == lastRow) {
			isNewRow = true;
		}
		oldLastRowId = rowId;
	}
	if (isNewRow) {
		var rowId = addBplParcelRow();
		//document.getElementById("consgNumbers" + rowId).focus();
		focusById("consgNumbers" + rowId);
	}else{
		
		//if last row is already have value then create new row
		if(!isNull($("#consgNumbers" + oldLastRowId).val())){
			oldLastRowId = addBplParcelRow();
		}
		//$("#consgNumbers" + oldLastRowId).focus();
		focusById("consgNumbers" + oldLastRowId);
	}
}

function addNewRow(e, selectedRowId){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		addRow(selectedRowId);
		return true;
	}
}

/**
 * validateMandatoryRowFields
 * 
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId) {
	var consgNumbersObj = document.getElementById("consgNumbers" + rowId);
	var destPincodesObj = document.getElementById("destPincodes" + rowId);
	var cnContentsObj = document.getElementById("cnContents" + rowId);
	var consgWeightsObj = document.getElementById("actualWeights" + rowId);
	var weightKgObj = document.getElementById("weightKg" + rowId);
	var numOfPcsObj = document.getElementById("numOfPcs" + rowId);
	var insuredByObj = document.getElementById("insuredByIds" + rowId);
	var declaredByObj = document.getElementById("declaredValues" + rowId);
	var policyNoObj = document.getElementById("policyNos" + rowId);
	var toPayAmtObj = document.getElementById("toPayAmts" + rowId);
	var codAmtObj = document.getElementById("codAmts" + rowId);
	var lcBankNamesObj = document.getElementById("lcBankNames" + rowId);
	var insuredByText = getSelectedDropDownTextByDOM(insuredByObj);
	

	//var contentNameObj = getDomElementById("cnContents" + rowId);
	var contentNameText = cnContentsObj.options[cnContentsObj.selectedIndex].text;
	var contentOtherObj = getDomElementById("cnContentOther" + rowId);

	var consignmentManifestIdsObj = document
			.getElementById("consignmentManifestIds" + rowId);

	if (isNull(consignmentManifestIdsObj.value)) {
		if (isNull(consgNumbersObj.value)) {
			focusAlertMsg4TxtBox(consgNumbersObj, "Consignment Number");
			return false;
		}
		if (isNull(destPincodesObj.value)) {
			focusAlertMsg4TxtBox(destPincodesObj, "Pincode");
			return false;
		}
		if (isNull(numOfPcsObj.value)) {
			focusAlertMsg4TxtBox(numOfPcsObj, "Number of Pieces");
			return false;
		}
		if (isEmptyWeight(parseFloat(consgWeightsObj.value))) {
			focusAlertMsg4TxtBox(weightKgObj, "Weight");
			return false;
		}
		if (isNull(cnContentsObj.value)) {
			focusAlertMsg4Select(cnContentsObj, "Content Description");
			return false;
		}
		
		if ((contentNameText.toUpperCase() == "OTHERS")
				&& isNull(contentOtherObj.value)) {
			focusAlertMsg4TxtBox(contentOtherObj, "Other Contents Description");
			return false;
		}
		
		if (declaredByObj.value=="") {
			focusAlertMsg4TxtBox(declaredByObj, "Declared Value");
			return false;
		}
		if (isNull(insuredByObj.value)) {
			focusAlertMsg4Select(insuredByObj, "Insured By");
			return false;
		} else if (insuredByText.toUpperCase() == "CONSIGNOR" && isNull(policyNoObj.value)) {
			//else if (insuredByObj.value=="2" && isNull(policyNoObj.value)) {
			focusAlertMsg4TxtBox(policyNoObj, "Policy Number");
			return false;
		}

		var productCode = consgNumbersObj.value.substring(4, 5);
		
		if (productCode == "T" && isEmptyRate(toPayAmtObj.value)) {
			focusAlertMsg4TxtBox(toPayAmtObj, "To Pay Amt.");
			return false;
		}
		if ((productCode == "L" || productCode == "D") && isEmptyRate(codAmtObj.value)) {
			var msg1 = "COD Amt.";
			if(productCode == "D"){
				msg1 = "LC Amt.";
			}
			focusAlertMsg4TxtBox(codAmtObj, msg1);
			return false;
		}
		if (productCode == "D" && isNull(lcBankNamesObj.value)) {
			focusAlertMsg4TxtBox(lcBankNamesObj, "LC Bank Name");
			return false;
		}
		
		/*
		1.  To Pay Amt(mandatory for T series)
		2.	COD Amt(mandatory for L series) Optional for T series
		3.	LC Amt(mandatory for D series)
		4.	LC Bank Name(mandatory for D series)
		5. 	for others non editable
		*/

	}
	return true;
}

/**
 * validateMandatoryFields
 * 
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields() {
	var table = document.getElementById("bplParcelGrid");
	var manifestNumberObj = document.getElementById("manifestNumber");
	var manifestIdObj = document.getElementById("manifestId");
	var originRegionObj = document.getElementById("originRegion");
	var originCityObj = document.getElementById("originCity");
	var originOfficeTypeObj = document.getElementById("originOfficeType");
	var originOfficeObj = document.getElementById("originOffice");

	//if (isNull(manifestIdObj.value)) {
		if (isNull(manifestNumberObj.value)) {
			focusAlertMsg4TxtBox(manifestNumberObj, "BPL Number");
			return false;
		}
		if (isNull(originRegionObj.value)) {
			focusAlertMsg4Select(originRegionObj, "Origin Region");
			return false;
		}
		if (isNull(originCityObj.value)) {
			focusAlertMsg4Select(originCityObj, "Origin City");
			return false;
		}
		if (isNull(originOfficeTypeObj.value)) {
			focusAlertMsg4Select(originOfficeTypeObj, "Origin Office Type");
			return false;
		}
		if (isNull(originOfficeObj.value)) {
			focusAlertMsg4Select(originOfficeObj, "Origin Office");
			return false;
		}
	//}

	// Validating table data
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);

		var consgNumbersObj = $("#consgNumbers" + rowId);

		if (i == (table.rows.length - 1) && table.rows.length > 2) {
			if (isNull(consgNumbersObj.val())) {
				continue;
			}
		}
		if (!validateMandatoryRowFields(rowId)) {
			return false;
		}
	}

	return true;
}

/**
 * saveOrUpdateInBagManifestParcel
 * 
 * @author narmdr
 */
function saveOrUpdateInBagManifestParcel() {
	if (!validateMandatoryFields()) {
		return;
	}
	flag = confirm("Do you want to Save the In BPL - Parcel Details");
	if (!flag) {
		return;
	}
	enableAll();
	var url = "./inBagManifestParcel.do?submitName=saveOrUpdateInBagManifestParcel";
	ajaxCall(url, "inBagManifestParcelForm", saveCallback);
}
/**
 * saveCallback
 * 
 * @param msg
 * @author narmdr
 */
function saveCallback(msg) {
	alert(msg);
	showProcessing();
	cancelPage();
}
/**
 * cancelInBplParcel
 * 
 * @author narmdr
 */
function cancelInBplParcel() {
	var flag = confirm("Do you want to cancel the In BPL - Parcel Details!");
	if (flag) {
		cancelPage();
	}
}

/**
 * cancelPage
 * 
 * @author narmdr
 */
function cancelPage() {
	url = "./inBagManifestParcel.do?submitName=viewInBagManifestParcel";
	window.location = url;
}

/**
 * findBplNumberParcel
 * 
 * @author narmdr
 */
function findBplNumberParcel() {
	var manifestNumberObj = document.getElementById("manifestNumber");

	// validate format
	if (!isValidBplNo(manifestNumberObj)) {
		return;
	}
	if (!isNull(oldManifestNo)
			&& oldManifestNo != manifestNumberObj.value) {
		checkGridValueWhenHeaderModify();
	}

	showProcessing();
	isManifestNoIssued(null);
	// plz uncomment after test :: issue validation
	/*
	 * var url = "./inBagManifestParcel.do?submitName=isManifestNoIssued";
	 * 
	 * $.ajax({ url: url, data:
	 * "manifestNo="+manifestNumberObj.value+"&seriesType=BPL_NO", success:
	 * function(data){isManifestNoIssued(data);} });
	 */
}

function checkGridValueWhenHeaderModify() {
	if (usedConsignments != null && usedConsignments.length != 0) {
		if (confirm("Consignments already entered.\n\nDo you want to make the changes in header?")) {
			usedConsignments = new Array();
			cancelPage();
		} else {
			showProcessing();
			document.getElementById("manifestNumber").value = oldManifestNo;
			showDropDownBySelected('originRegion', originRegionVal);
			getOriginCitiesByRegion();
			showDropDownBySelected('originCity', originCityVal);
			showDropDownBySelected('originOfficeType', originOfficeTypeVal);
			getOriginOfficesByCityAndOfficeTypeAsyn();
			showDropDownBySelected('originOffice', originOfficeVal);
			hideProcessing();
		}
	}
}

function getOriginOfficesByCityAndOfficeTypeAsyn(){	
	var originCityId = document.getElementById("originCity").value;	
	var originOfficeTypeId =  document.getElementById("originOfficeType").value;	
	url = './inBagManifest.do?submitName=getAllOfficesByCityAndOfficeType&cityId=' + originCityId + "&officeTypeId=" + originOfficeTypeId;		
	ajaxCallWithoutFormAsyn(url, populateOriginOffices);	
}

function ajaxCallWithoutFormAsyn(pageurl, ajaxResponse) {
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		async: false,
		success : function(data) {
			ajaxResponse(data);
		}
	});

}

function callMethodAfterChange(field) {
	if (field == "MT") {
		getDestinationDtlsByBplManifestType();
	} else if (field == "R") {
		getCitiesByRegion();
	} else if (field == "C") {
		getOfficeTypeByBplManifestType();
	} else if (field == "OT") {
		getOfficesByCityAndoffType();
	}
}

/**
 * isManifestNoIssued
 * 
 * @param data
 * @author narmdr
 */
function isManifestNoIssued(data) {
	var manifestNumberObj = document.getElementById("manifestNumber");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");

	// plz uncomment after test :: issue validation
	/*
	 * var manifestIssueValidationTO = eval('(' + data + ')');
	 * if(manifestIssueValidationTO.isIssued=="N"){ hideProcessing();
	 * clearFocusAlertMsg(manifestNumberObj,
	 * manifestIssueValidationTO.errorMsg); return; }
	 */

	var url = "./inBagManifestParcel.do?submitName=findBplNumberParcel&manifestNumber="
			+ manifestNumberObj.value + "&loggedInOfficeId=" + loggedInOfficeId;
	$.ajax({
		url : url,
		success : function(data) {
			populateBplNumberParcel(data);
		}
	});
}

/**
 * populateBplNumberParcel
 * 
 * @param data
 * @author narmdr
 */
function populateBplNumberParcel(data) {
	hideProcessing();
	var inManifestValidationTO = eval('(' + data + ')');
	/*
	 * if (inManifestValidationTO.isInManifest ||
	 * inManifestValidationTO.isInManifestByReceive) {
	 * populateInBagManifestTO(inManifestValidationTO);
	 *  } else if (inManifestValidationTO.isNewManifest) {
	 * //document.getElementById("originRegion").focus();
	 * $("#originRegion").focus(); }
	 */
	if (!isNull(inManifestValidationTO.errorMsg)) {
		clearFocusAlertMsg(document.getElementById("manifestNumber"),
				inManifestValidationTO.errorMsg);
		return;
	}
	/* Set old Manifest no in global value */
	oldManifestNo = document.getElementById("manifestNumber").value;

	if (!inManifestValidationTO.isNewManifest) {
		populateInBagManifestTO(inManifestValidationTO);
	} else {
		$("#originRegion").focus();
	}

	// to prepare iFrame for print
	/*var manifestNumberObj = document.getElementById("manifestNumber");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	var url = "./inBagManifestParcel.do?submitName=printBplNumberParcel&manifestNumber="
			+ manifestNumberObj.value + "&loggedInOfficeId=" + loggedInOfficeId;
	printUrl = url;
	printIframe(printUrl);*/
}

function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}

/**
 * populateInBagManifestTO
 * 
 * @param inManifestValidationTO
 * @author narmdr
 */
function populateInBagManifestTO(inManifestValidationTO) {

	var inBagManifestParcelTO = inManifestValidationTO.inBagManifestParcelTO;

	document.getElementById("manifestId").value = inBagManifestParcelTO.manifestId;
	document.getElementById("lockNum").value = inBagManifestParcelTO.lockNum;
	document.getElementById("position").value = inBagManifestParcelTO.position;

	$("#headerRemarks").val(inBagManifestParcelTO.headerRemarks);
	$("#manifestEmbeddedIn").val(inBagManifestParcelTO.manifestEmbeddedIn);
	$("#manifestReceivedStatus").val(inBagManifestParcelTO.manifestReceivedStatus);

	if (!isNull(inBagManifestParcelTO.destinationOfficeId)) {
		document.getElementById("destinationOfficeId").value = inBagManifestParcelTO.destinationOfficeId;
	}
	if (!isNull(inBagManifestParcelTO.destCityId)) {
		document.getElementById("destCityId").value = inBagManifestParcelTO.destCityId;
	}

	document.getElementById("manifestNumber").readOnly = true;

	/* inManifestValidationTO.isInManifestByReceive && */
	if (isNull(inBagManifestParcelTO.originRegion)) {
		// document.getElementById("manifestId").value =
		// inBagManifestParcelTO.manifestId;
		// disableFieldById("manifestNumber");
		document.getElementById("lockNum").focus();
		return;
	}

	// TODO need to check whether add/select
	/*
	 * addOptionTODropDown("originRegion",
	 * inBagManifestDoxTO.originRegion.split("~")[1],
	 * inBagManifestDoxTO.originRegion.split("~")[0]);
	 * document.getElementById("originRegion").value =
	 * inBagManifestDoxTO.originRegion.split("~")[0];
	 */
	$("#originRegion").val(inBagManifestParcelTO.originRegion.split("~")[0]);

	addOptionTODropDown("originOffice", inBagManifestParcelTO.originOffice
			.split("~")[1], inBagManifestParcelTO.originOffice.split("~")[0]);
	document.getElementById("originOffice").value = inBagManifestParcelTO.originOffice
			.split("~")[0];

	addOptionTODropDown("originCity", inBagManifestParcelTO.originCity
			.split("~")[1], inBagManifestParcelTO.originCity.split("~")[0]);
	document.getElementById("originCity").value = inBagManifestParcelTO.originCity
			.split("~")[0];

	$("#originOfficeType").val(inBagManifestParcelTO.officeType.split("~")[0]);
	/*
	 * addOptionTODropDown("originOfficeType",
	 * inBagManifestDoxTO.officeType.split("~")[1],
	 * inBagManifestDoxTO.officeType.split("~")[0]);
	 * document.getElementById("originOfficeType").value =
	 * inBagManifestDoxTO.officeType.split("~")[0];
	 */

	/*
	 * if (inManifestValidationTO.isOutManifest) {
	 * disableFieldById("manifestNumber");
	 * document.getElementById("consgNumbers" + (rowCount-1)).focus(); return; }
	 */

	/*
	 * if (inManifestValidationTO.isInManifestByReceive) {
	 * //document.getElementById("manifestId").value =
	 * inBagManifestParcelTO.manifestId; document.getElementById("consgNumbers" +
	 * (rowCount-1)).focus(); disableFieldById("manifestNumber"); return; }
	 */

	if (!isNull(inBagManifestParcelTO.destinationOffice)) {
		document.getElementById("destinationOffice").value = inBagManifestParcelTO.destinationOffice;
	}
	
	// document.getElementById("consignmentTypeId").value =
	// inBagManifestParcelTO.consignmentTypeId;
	document.getElementById("manifestWeight").value = inBagManifestParcelTO.manifestWeight;
	document.getElementById("updateProcessId").value = inBagManifestParcelTO.updateProcessId;

	populateWeightInKgGmFieldByName(inBagManifestParcelTO.manifestWeight,
			"totalWeightKg", "totalWeightGm");

	if (!isNull(inBagManifestParcelTO.inBagManifestDetailsParcelTOs)) {
		// populateGrid();
		deleteAllRowOfTableExceptHeaderRow("bplParcelGrid");
		for ( var i = 0; i < inBagManifestParcelTO.inBagManifestDetailsParcelTOs.length; i++) {
			var rowId = addBplParcelRow();
			populateGrid(
					inBagManifestParcelTO.inBagManifestDetailsParcelTOs[i],
					rowId);
		}

		disableAll();
		/*
		 * var rowId = addBplParcelRow(); //disableFieldById("chk" + rowId);
		 * document.getElementById("consgNumbers" + rowId).focus();
		 */
		// setTotalWeight();
		// enableFieldById("btnSave");
		// enableFieldById("btnDelete");
		/*jQuery("#btnSave").css("background-color", "grey");
		jQuery("#btnDelete").css("background-color", "grey");
		enableFieldById("btnClear");
		enableFieldById("btnPrint");*/
		buttonEnableById("btnClear");
		if(inBagManifestParcelTO.inBagManifestDetailsParcelTOs.length>0){
			document.getElementById("manifestDateTime").value = inBagManifestParcelTO.manifestDateTime;
			buttonEnableById("btnPrint");
			isViewScreen = true;
		} else {
			isViewScreen = false;
		}
	} else {
		$("#consgNumbers1").focus();
	}

}

/**
 * populateGrid
 * 
 * @param inBagManifestDetailsParcelTO
 * @param rowId
 * @author narmdr
 */
function populateGrid(inBagManifestDetailsParcelTO, rowId) {
	/*
	 * if(!isNull(inBagManifestDetailsParcelTO.consignmentManifestId)){
	 * disableFieldById("chk" + rowId); }
	 */
	
	if (isNull(originRegionVal))
		originRegionVal = getDomElementById("originRegion").value;
	if (isNull(originCityVal))
		originCityVal = getDomElementById("originCity").value;
	if (isNull(originOfficeVal))
		originOfficeVal = getDomElementById("originOffice").value;
	if (isNull(originOfficeTypeVal))
		originOfficeTypeVal = getDomElementById("originOfficeType").value;

	
	document.getElementById("consignmentIds" + rowId).value = inBagManifestDetailsParcelTO.consignmentId;
	document.getElementById("consgOrgOffIds" + rowId).value = inBagManifestDetailsParcelTO.consgOrgOffId;
	document.getElementById("remarks" + rowId).value = inBagManifestDetailsParcelTO.remarks;
	document.getElementById("receivedStatus" + rowId).value = inBagManifestDetailsParcelTO.receivedStatus;
	document.getElementById("consgNumbers" + rowId).value = inBagManifestDetailsParcelTO.consgNumber;
	document.getElementById("childCns" + rowId).value = inBagManifestDetailsParcelTO.childCn;
	document.getElementById("actualWeights" + rowId).value = inBagManifestDetailsParcelTO.actualWeight;
	document.getElementById("finalWeights" + rowId).value = inBagManifestDetailsParcelTO.finalWeight;
	document.getElementById("volWeight" + rowId).value = inBagManifestDetailsParcelTO.volWeight;
	document.getElementById("length" + rowId).value = inBagManifestDetailsParcelTO.length;
	document.getElementById("height" + rowId).value = inBagManifestDetailsParcelTO.height;
	document.getElementById("breath" + rowId).value = inBagManifestDetailsParcelTO.breadth;
	document.getElementById("mobileNos" + rowId).value = inBagManifestDetailsParcelTO.mobileNo;
	document.getElementById("policyNos" + rowId).value = inBagManifestDetailsParcelTO.policyNo;
	document.getElementById("refNos" + rowId).value = inBagManifestDetailsParcelTO.refNo;
	document.getElementById("productIds" + rowId).value = inBagManifestDetailsParcelTO.productId;
	document.getElementById("consignmentTypeIds" + rowId).value = inBagManifestDetailsParcelTO.consignmentTypeId;
	document.getElementById("consignmentManifestIds" + rowId).value = inBagManifestDetailsParcelTO.consignmentManifestId;
	document.getElementById("updateProcessIds" + rowId).value = inBagManifestDetailsParcelTO.updateProcessId;
	document.getElementById("bookingType" + rowId).value = inBagManifestDetailsParcelTO.bookingType;

	if(!isNull(inBagManifestDetailsParcelTO.numOfPc)){
		document.getElementById("numOfPcs" + rowId).value = inBagManifestDetailsParcelTO.numOfPc;		
	}else{
		document.getElementById("numOfPcs" + rowId).value = "1";
	}
	
	// check
	document.getElementById("declaredValues" + rowId).value = inBagManifestDetailsParcelTO.declaredValue;
	document.getElementById("toPayAmts" + rowId).value = inBagManifestDetailsParcelTO.toPayAmt;
	document.getElementById("codAmts" + rowId).value = inBagManifestDetailsParcelTO.codAmt;
	document.getElementById("lcBankNames" + rowId).value = inBagManifestDetailsParcelTO.lcBankName;
	document.getElementById("priceIds" + rowId).value = inBagManifestDetailsParcelTO.priceId;
	document.getElementById("baAmts" + rowId).value = inBagManifestDetailsParcelTO.baAmt;

	if (!isNull(inBagManifestDetailsParcelTO.cnContentTO)) {
		document.getElementById("cnContentIds" + rowId).value = inBagManifestDetailsParcelTO.cnContentTO.cnContentId;
		document.getElementById("cnContentCodes" + rowId).value = inBagManifestDetailsParcelTO.cnContentTO.cnContentCode;
		document.getElementById("cnContents" + rowId).value = inBagManifestDetailsParcelTO.cnContentTO.cnContentId
				+ "~" + inBagManifestDetailsParcelTO.cnContentTO.cnContentCode;
		// other cnContents
		document.getElementById("cnContentOther" + rowId).value = inBagManifestDetailsParcelTO.cnContentTO.otherContent;
	}
	if (!isNull(inBagManifestDetailsParcelTO.cnPaperWorksTO)) {
		document.getElementById("paperWorkIds" + rowId).value = inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkId;
		document.getElementById("paperRefNums" + rowId).value = inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkCode;
		addOptionTODropDown(
				"cnPaperWorks" + rowId,
				inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkName,
				inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkId
						+ "~"
						+ inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkCode);
		document.getElementById("cnPaperWorks" + rowId).value = inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkId
				+ "~"
				+ inBagManifestDetailsParcelTO.cnPaperWorksTO.cnPaperWorkCode;
	}
	if (!isNull(inBagManifestDetailsParcelTO.insuredByTO)) {
		document.getElementById("insuredByIds" + rowId).value = inBagManifestDetailsParcelTO.insuredByTO.insuredById;
	}
	if (!isNull(inBagManifestDetailsParcelTO.pincodeTO)) {
		document.getElementById("destPincodeIds" + rowId).value = inBagManifestDetailsParcelTO.pincodeTO.pincodeId;
		document.getElementById("destPincodes" + rowId).value = inBagManifestDetailsParcelTO.pincodeTO.pincode;
	}
	if (!isNull(inBagManifestDetailsParcelTO.destCity)) {
		document.getElementById("destCityNames" + rowId).value = inBagManifestDetailsParcelTO.destCity.cityName;
	}

	populateWeightInKgGmFieldByName(inBagManifestDetailsParcelTO.actualWeight,
			"weightKg", "weightGm", rowId);
	populateWeightInKgGmFieldByName(inBagManifestDetailsParcelTO.volWeight,
			"weightVW", "weightGmVW", rowId);
	populateWeightInKgGmFieldByName(inBagManifestDetailsParcelTO.finalWeight,
			"weightKgCh", "weightGmCh", rowId);
}
/*
 * function weightKgFocus(rowIdd){ var weightKgObj =
 * document.getElementById("weightKg" + rowIdd); var manifestWeightsObj =
 * document.getElementById("manifestWeights" + rowIdd);
 * if(isNull(manifestWeightsObj.value)){ weightKgObj.focus(); } }
 * 
 * function manifestNumberFocus(rowId){ var manifestNumbersObj =
 * document.getElementById("manifestNumbers" + rowId);
 * if(isNull(manifestNumbersObj.value)){ manifestNumbersObj.focus(); } }
 */

/**
 * bplNumberFocus
 * 
 * @author narmdr
 */
function bplNumberFocus() {
	var manifestNumbersObj = document.getElementById("manifestNumber");
	if (isNull(manifestNumbersObj.value)) {
		manifestNumbersObj.focus();
	}
}



var prevConsgNumber = null;
function storeConsgNumber(consgNumber) {
	prevConsgNumber = consgNumber;
}

// Manifest BPL/MBPL validation Starts
/**
 * validateConsgNumber
 * 
 * @param consgNumberObj
 * @author narmdr
 */
function validateConsgNumber(consgNumberObj) {
	var bplNumberObj = document.getElementById("manifestNumber");
	var rowId = getRowId(consgNumberObj, "consgNumbers");
	var ogmProcessCode = getValueByElementId("gridOgmProcessCode");
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");

	var url = "./inBagManifestParcel.do?submitName=validateConsgNumber&consgNumber="
			+ consgNumberObj.value
			+ "&processCode="
			+ ogmProcessCode
			+ "&updatedProcessCode="
			+ ogmProcessCode
			+ "&loggedInOfficeId="
			+ loggedInOfficeId;

	if(isNull(consgNumberObj.value) || prevConsgNumber!=consgNumberObj.value){
		removeUnUsedConsignment(prevConsgNumber);
	}
	
	if (isNull(bplNumberObj.value)) {
		removeUnUsedConsignment(consgNumberObj.value);
		consgNumberObj.value = "";
		focusAlertMsg4TxtBox(bplNumberObj, "BPL Number first");
		enableClearManifestFields(rowId);
		return;
	}

	// validate Consg Number format
	if (!validateConsignmentBranchCode(consgNumberObj)) {
		clearFocusAlertMsg(consgNumberObj, "Invalid Number");
		removeUnUsedConsignment(prevConsgNumber);
		removeUnUsedConsignment(consgNumberObj.value);
		consgNumberObj.value = "";
		enableClearManifestFields(rowId);
		return;
	}

	/*
	 * if(isDuplicateFieldInGrid(consgNumberObj, "consgNumbers",
	 * "bplParcelGrid")){ clearFocusAlertMsg(consgNumberObj, "Duplicate
	 * Consignment Number!"); return; }
	 */
	if (isDuplicateConsignment(consgNumberObj, "consgNumbers")) {
		clearFocusAlertMsg(consgNumberObj, "Duplicate Consignment Number!");
		return;
	}
	//prevConsgNumber = consgNumberObj.value;
	
	showProcessing();
	$.ajax({
		url : url,
		success : function(data) {
			populateConsignment(data, rowId);
		}
	});
}

/**
 * populateConsignment
 * 
 * @param data
 * @param rowId
 * @author narmdr
 */
function populateConsignment(data, rowId) {
	hideProcessing();
	var inManifestValidationTO = eval('(' + data + ')');

	if (!isNull(inManifestValidationTO.errorMsg)) {
		removeUnUsedConsignment($("#consgNumbers" + rowId).val());
		clearFocusAlertMsg(document.getElementById("consgNumbers" + rowId),
				inManifestValidationTO.errorMsg);
		return;
	}
	if (!isNull(inManifestValidationTO.inBagManifestDetailsParcelTO)) {

		populateGrid(inManifestValidationTO.inBagManifestDetailsParcelTO, rowId);
		document.getElementById("receivedStatus" + rowId).value = receivedCode;

		setTotalWeight();
		validateWeightFromWeighingMachine(rowId);
		addRow(rowId);
		/*var rowIdd = addBplParcelRow();
		document.getElementById("consgNumbers" + rowIdd).focus();*/
	} else {
		// enableClearManifestFields(rowId);
		$("#destPincodes" + rowId).focus();
	}
	enableDisableConsgFields(rowId);
	enableDisableAmountBankFiels(rowId);
}

/**
 * enableDisableConsgFields
 *
 * @param rowId
 * @author narmdr
 */
function enableDisableConsgFields(rowId) {	
	var isReadOnly = false;
	if(!isNull($("#consignmentIds"+rowId).val())){
		isReadOnly = true;
	}
	if(isReadOnly){
		// Non-excess consignment
		disableFieldById("weightKg" + rowId);
		disableFieldById("destPincodes" + rowId);
		disableFieldById("numOfPcs" + rowId);
		disableFieldById("mobileNos" + rowId);
		disableFieldById("cnContentCodes" + rowId);
		disableFieldById("cnContents" + rowId);
		disableFieldById("cnContentOther" + rowId);
		disableFieldById("declaredValues" + rowId);
		disableFieldById("cnPaperWorks" + rowId);
		disableFieldById("paperRefNums" + rowId);
		disableFieldById("insuredByIds" + rowId);
		disableFieldById("policyNos" + rowId);
		disableFieldById("toPayAmts" + rowId);
		disableFieldById("codAmts" + rowId);
		disableFieldById("baAmts" + rowId);
		disableFieldById("lcBankNames" + rowId);
		disableFieldById("refNos" + rowId);
		disableFieldById("remarks" + rowId);
	}else{
		enableFieldById("weightKg" + rowId);
	}
	document.getElementById("weightGm" + rowId).readOnly = isReadOnly;
}

/**
 * enableClearManifestFields
 * 
 * @param rowId
 * @author narmdr
 */
function enableClearManifestFields(rowId) {
	enableFieldById("weightKg" + rowId);
	document.getElementById("weightGm" + rowId).readOnly = false;
	document.getElementById("receivedStatus" + rowId).value = notReceivedCode;
}

/**
 * validateDestOriginOffice
 * 
 * @author narmdr
 */
function validateDestOriginOffice() {
	var destOfficeId = getValueByElementId("destinationOfficeId");
	var originOfficeObj = document.getElementById("originOffice");
	if (originOfficeObj.value == destOfficeId) {
		clearFocusAlertMsg(originOfficeObj,
				"Origin Office and Destination Office should not be same!");
	}
}

/**
 * setFinalWeight
 * 
 * @param rowId
 * @author narmdr
 */
function setFinalWeight(rowId) {
	var volWeight = document.getElementById("volWeight" + rowId).value;
	var actualWeight = document.getElementById("actualWeights" + rowId).value;
	var finalWeightsObj = document.getElementById("finalWeights" + rowId);
	/*
	 * if(!isNull(actualWeight)){ finalWeightsObj.value = actualWeight; }
	 * if(!isNull(volWeight) && !isNull(actualWeight)){ if(volWeight >
	 * actualWeight){ finalWeightsObj.value = volWeight; } }
	 */

	if (volWeight > actualWeight) {
		finalWeightsObj.value = volWeight;
	} else {
		finalWeightsObj.value = actualWeight;
	}
	populateWeightInKgGmFieldByName(finalWeightsObj.value, "weightKgCh",
			"weightGmCh", rowId);
	// calculate rate
	// calcCNrate();
}

/**
 * validateMobileNo
 * 
 * @param mobileNoObj
 * @author narmdr
 */
function validateMobileNo(mobileNoObj) {
	if (mobileNoObj.value.length != 10) {
		clearFocusAlertMsg(mobileNoObj, "Mobile No. must be of 10 digit!");
	}
}
/**
 * setCnContentIdCode
 * 
 * @param rowId
 * @author narmdr
 */
function setCnContentIdCode(rowCountName) {
	//var rowCountName = getRowId(obj, "cnContents");

	getDomElementById('cnContentCodes' + rowCountName).value = "";
	getDomElementById('cnContentIds' + rowCountName).value = "";

	ROW_COUNT = rowCountName;
	var contentValue = getDomElementById('cnContents' + rowCountName).value;
	if (contentValue != "" && contentValue != "" && contentValue != "O") {
		getDomElementById("cnContentOther" + rowCountName).value = "";
		getDomElementById("cnContentOther" + rowCountName).readOnly = true;
		var contentCode = contentValue.split("~")[1];
		var contentId = contentValue.split("~")[0];

		getDomElementById('cnContentIds' + rowCountName).value = contentId;
		getDomElementById('cnContentCodes' + rowCountName).value = contentCode;
	}
	if (contentValue != "" && contentValue != "" && contentValue == "O") {
		getDomElementById("cnContentOther" + rowCountName).readOnly = false;
	}
}



/**
 * validatePincode
 * 
 * @param rowId
 * @author narmdr
 */
function validatePincode(rowId) {
	var destPincodeObj = document.getElementById("destPincodes" + rowId);

	destPincodeObj.value = $.trim(destPincodeObj.value);

	var url = "./inBagManifestParcel.do?submitName=validatePincode&pincode="
			+ destPincodeObj.value;

	if (isNull(destPincodeObj.value)) {
		return;
	}

	$.ajax({
		url : url,
		success : function(data) {
			populatePincode(data, rowId);
		}
	});
}
/**
 * populatePincode
 * 
 * @param data
 * @param rowId
 * @author narmdr
 */
function populatePincode(data, rowId) {

	var inManifestValidationTO = eval('(' + data + ')');
	if (!isNull(inManifestValidationTO.errorMsg)) {
		var destPincodesObj = document.getElementById("destPincodes" + rowId);
		clearFocusAlertMsg(destPincodesObj, inManifestValidationTO.errorMsg);
		return;
	}
	document.getElementById("destPincodeIds" + rowId).value = inManifestValidationTO.pincodeTO.pincodeId;

	if (!isNull(inManifestValidationTO.cityTO)) {
		document.getElementById("destCityNames" + rowId).value = inManifestValidationTO.cityTO.cityName;
	}
	document.getElementById("numOfPcs" + rowId).focus();
}

/**
 * validateBagLockNo
 * 
 * @author narmdr
 */
function validateBagLockNo() {
	var lockNumberObj = document.getElementById("lockNum");

	// validate format
	if (!isValidLockNo(lockNumberObj)) {
		lockNumberObj.value = "";
		return;
	}

	showProcessing();

	isLockNoIssued(null);
	// plz uncomment after test :: issue validation
	/*
	 * var url = "./inBagManifest.do?submitName=isManifestNoIssued"; $.ajax({
	 * url: url, data:
	 * "manifestNo="+lockNumberObj.value+"&seriesType=BAG_LOCK_NO", success:
	 * function(data){isLockNoIssued(data);} });
	 */
}

/**
 * isLockNoIssued
 * 
 * @param data
 * @author narmdr
 */
function isLockNoIssued(data) {
	var lockNumberObj = document.getElementById("lockNum");
	var manifestIssueValidationTO = eval('(' + data + ')');
	hideProcessing();

	// plz uncomment after test :: issue validation
	/*
	 * if(manifestIssueValidationTO.isIssued=="N"){
	 * clearFocusAlertMsg(lockNumberObj, manifestIssueValidationTO.errorMsg);
	 * return; }
	 */
}

// **********Declared Value Validation
// Start****************************************************
/** The ROW_COUNT */
var ROW_COUNT = 0;
/**
 * isValidDeclaredValue
 * 
 * @param rowId
 * @author narmdr
 */
function isValidDeclaredValue(rowId) {
	ROW_COUNT = rowId;
	var declaredValueObj = document.getElementById("declaredValues" + rowId);
	var bookingType = document.getElementById("bookingType" + rowId).value;
	var pincodeObj = document.getElementById("destPincodes" + rowId);
	// bookingType = "CA";

	/*
	 * if(isNull(declaredValueObj.value)){ return; }
	 */
	if (isNull(pincodeObj.value)) {
		focusAlertMsg4TxtBox(pincodeObj, "Pincode");
		return;
	}
	if (isNull(bookingType)) {
		return;
	}
	url = './inBagManifestParcel.do?submitName=validateDeclaredvalue&declaredVal='
			+ declaredValueObj.value + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setBookingDtlsResponse);
}

/**
 * setBookingDtlsResponse
 * 
 * @param bookingValidationTO
 * @author narmdr
 */
function setBookingDtlsResponse(bookingValidationTO) {
	if (isNull(bookingValidationTO)) {
		return;
	}
	if (bookingValidationTO.isValidDeclaredVal == "N") {
		var declaredValueObj = document.getElementById("declaredValues"
				+ ROW_COUNT);
		var msg = "";
		if (!isNull(bookingValidationTO.errorMsg)) {
			msg = bookingValidationTO.errorMsg;
		} else {
			msg = "Declared value above the maximum limit allowed!";
		}
		clearFocusAlertMsg(declaredValueObj, msg);
		/*
		 * alert("Declared value above the maximum limit allowed");
		 * document.getElementById("declaredValues" + ROW_COUNT).value = "";
		 * setTimeout(function() { document.getElementById("declaredValues" +
		 * ROW_COUNT).focus(); }, 10);
		 */
	} else {
		getPaperWorks(ROW_COUNT);
	}

}

/**
 * getPaperWorks
 * 
 * @param ROW_COUNT
 * @author narmdr
 */
function getPaperWorks(ROW_COUNT) {
	var declaredValue = document.getElementById("declaredValues" + ROW_COUNT).value;
	var pincode = document.getElementById("destPincodes" + ROW_COUNT).value;
	if (!isNull(declaredValue) && !isNull(pincode)) {
		var url = "inBagManifestParcel.do?submitName=getPaperWorks&pincode="
				+ pincode + "&declaredValue=" + declaredValue;
		ajaxCallWithoutForm(url, getPaperWorksResponse);
	}
}

/**
 * getPaperWorksResponse
 * 
 * @param cnPaperWorksTOs
 * @author narmdr
 */
function getPaperWorksResponse(cnPaperWorksTOs) {
	// var cnPaperWorksObj = document.getElementById("cnPaperWorks" +
	// ROW_COUNT);
	var errorMsg = getErrorMessage(cnPaperWorksTOs);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}

	clearDropDownList("cnPaperWorks" + ROW_COUNT);
	for ( var i = 0; i < cnPaperWorksTOs.length; i++) {
		addOptionTODropDown("cnPaperWorks" + ROW_COUNT,
				cnPaperWorksTOs[i].cnPaperWorkName,
				cnPaperWorksTOs[i].cnPaperWorkId + "~"
						+ cnPaperWorksTOs[i].cnPaperWorkCode);
	}
	/*
	 * cnPaperWorksObj.innerHTML = ""; defOption =
	 * document.createElement("option"); defOption.value = "";
	 * defOption.appendChild(document.createTextNode("--Select--"));
	 * cnPaperWorksObj.appendChild(defOption); if (!isNull(cnPaperWorksTOs)) {
	 * $.each(cnPaperWorksTOs, function(index, value) { var option; option =
	 * document.createElement("option"); option.value = this.cnPaperWorkId + "#" +
	 * this.cnPaperWorkCode;
	 * option.appendChild(document.createTextNode(this.cnPaperWorkName));
	 * cnPaperWorksObj.appendChild(option); }); }
	 */
}

/**
 * setCnPaperWorksIdCode
 * 
 * @param rowId
 * @author narmdr
 */
function setCnPaperWorksIdCode(rowId) {
	var cnPaperWorksObj = document.getElementById("cnPaperWorks" + rowId);
	var paperWorkIdsObj = document.getElementById("paperWorkIds" + rowId);
	var paperRefNumsObj = document.getElementById("paperRefNums" + rowId);
	paperWorkIdsObj.value = cnPaperWorksObj.value.split("~")[0];
	paperRefNumsObj.value = cnPaperWorksObj.value.split("~")[1];
	if (isNull(cnPaperWorksObj.value)) {
		paperWorkIdsObj.value = "";
		paperRefNumsObj.value = "";
	}
}

// **********Declared Value Validation
// End****************************************************

function PrintInBplparcel() {
	if(confirm("Do you want to Print?")){
	  var manifestNumberObj = document.getElementById("manifestNumber"); 
	 // var destinationOfficeId = getValueByElementId("destinationOfficeId");
	  var loggedInOfficeId = getValueByElementId("loggedInOfficeId");
	  if (!isNull(manifestNumberObj.value)) {
	  if(!isValidBplNo(manifestNumberObj)){
		  manifestNumberObj.value = "";
		  return; 
	  } 
	  var url = "./inBagManifestParcel.do?submitName=printBplNumberParcel&manifestNumber="+manifestNumberObj.value+"&loggedInOfficeId="+loggedInOfficeId;
	  var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
	
	/*var manifestNumberObj = document.getElementById("manifestNumber");
	if (!isNull(manifestNumberObj.value)) {
		if (confirm("Do you want to print?")) {
			window.frames['iFrame'].focus();
			window.frames['iFrame'].print();
		}
	}*/
	  }else {
		focusAlertMsg4TxtBox(manifestNumberObj, "BPL Number");
		//alert("Please Enter BPL No.");
	  }
	}
}

function deleteInBplParcelRow(tableId){
	deleteTableRow4Parcel(tableId);
	updateSerialNoVal(tableId);
	setTotalWeight();
}

function deleteTableRow4Parcel(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for(var i=0; i<rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
				///////////////
				var rowId = getRowId(chkbox,"chk");
				removeUnUsedConsignment($("#consgNumbers" + rowId).val());
				
				if(rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId,i-1);
				rowCount--;
				i--;
			}
		}
	}catch(e) {
		alert(e);
	}
}

function fnEnterKeyCallOnCnNo(e, count) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var isCNFromPickup = getDomElementById("isCNProcessedFromPickup"
				+ count).value;
		if (isCNFromPickup == "Y") {
			getDomElementById("noOfPcs" + count).focus();
			return false;
		} else {
			var nextRow = parseIntNumber(count) + 1;
			return callEnterKey(e, document.getElementById('consgNumbers'
					+ nextRow));
		}
	}
}

/**
 * @param obj
 */
function validateNumberOfPcs(e, Obj, focusId, rowNo) {
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
			return onlyNumberAndEnterKeyNav(e, Obj, focusId+rowNo);
		}
	}

}

/**
 * @param obj
 */
function validateContents(e, obj, field, focusId,currentRowId) {
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
		if (field == "C"){
			isValidCode = setContentValues(obj);
		}
		var contentNameObj = getDomElementById("cnContents" + currentRowId);
		var contentCode = getDomElementById("cnContents" + currentRowId).value;
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
			getDomElementById("cnContents" + currentRowId).disabled = false;
			alert("Please enter content description");
			setTimeout(function() {
				getDomElementById("cnContents" + currentRowId).focus();
			}, 10);
			return false;
		} else if(isValidCode == true){
			getDomElementById(focusId).disabled = false;
			getDomElementById(focusId).focus();
			return false;
		}
	}
}



function setContentValues(obj) {
	var isValidContent = "";
	var rowCountName = getRowId(obj, "cnContentCodes");
	ROW_COUNT = rowCountName;
	var contentValueArr = getDomElementById('cnContents' + rowCountName);
	getDomElementById('cnContentIds' + rowCountName).value = "";
	for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var selectedCode = selectedVal.split("~")[1];
		var conetntId = selectedVal.split("~")[0];
		if (selectedCode == obj.value) {
			getDomElementById('cnContentIds' + rowCountName).value = conetntId;
			selectObj.selected = 'selected';
			isValidContent = "Y";
			break;
		} else {
			isValidContent = "N";
		}
	}
	if (isValidContent == "N") {
		getDomElementById('cnContentCodes' + rowCountName).value = "";
		getDomElementById('cnContents' + rowCountName).value = "";
		alert("Invalid content code.");
		setTimeout(function() {
			getDomElementById("cnContentCodes").focus();
		}, 10);
		return false;
	}
	return true;
}


/** The DEC_VAL */
var DEC_VAL = "";
/**
 * getInsuarnceConfigDtls
 * 
 * @param obj
 * @author prmeher
 */
function getInsuarnceConfigDtls(obj) {
	ROW_COUNT = getRowId(obj, "declaredValues");
	var bookingType = getDomElementById("bookingType" + ROW_COUNT).value;
	var insuredByNo = obj.value;
	var decVal = getDomElementById("declaredValues" + ROW_COUNT).value;
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
 * @author prmeher
 */
function setInsDtlsResponse(data) {
	if (!isNull(data)) {
		var insDetails = data;
		IS_POLICY_MANDATORY = insDetails.isPolicyNoMandatory;
		MIN_DEC_VAL = insDetails.minDeclaredValue;
		MAX_DEC_VAL = insDetails.maxDeclaredValue;
		if (insDetails.trasnStatus != "NOINSURENCEMAPPING") {
			showDropDownBySelected('insuredByIds' + ROW_COUNT,
					insDetails.insuredById);
			if (DEC_VAL == MIN_DEC_VAL) {
				getDomElementById("insuredByIds" + ROW_COUNT).disabled = false;
			} else if (DEC_VAL >= MIN_DEC_VAL && DEC_VAL <= MAX_DEC_VAL) {
				getDomElementById("insuredByIds" + ROW_COUNT).disabled = true;
			}
		}
		var insuredByDom = getDomElementById("insuredByIds" + ROW_COUNT);
		var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
		if (selectedText.toUpperCase() == "CONSIGNOR") {
			getDomElementById("policyNos" + ROW_COUNT).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNos" + ROW_COUNT).focus();
			}, 10);
		}
	}
}

/**
 * isValidDecValue
 * 
 * @param obj
 * @author prmeher
 */
function isValidDecValue(obj) {
	if (!isNull(obj.value)) {
		ROW_COUNT = getRowId(obj, "declaredValues");
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
 * @author prmeher
 */
function setBookingDtlsResponse(data) {
	if (!isNull(data)) {
		var error = data.errorMsg;
		if (!isNull(error)) {
			alert('Declared value above the maximum limit is not allowed');
			getDomElementById("declaredValues" + ROW_COUNT).value = "";
			setTimeout(function() {
				getDomElementById("declaredValues" + ROW_COUNT).focus();
			}, 10);
		}
		if (data.isValidDeclaredVal == "N") {
			alert('Declared value above the maximum limit is not allowed');
			getDomElementById("declaredValues" + ROW_COUNT).value = "";
			setTimeout(function() {
				getDomElementById("declaredValues" + ROW_COUNT).focus();
			}, 10);
		} else {
			getPaperWorks(ROW_COUNT);
		}
	}
}


/**
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 */
function validateDeclareValue(e, Obj, focusId) {
	var charCode;
	var currentRowId = getRowId(Obj, "declaredValues");
	var insuredByDom = getDomElementById("insuredByIds" + currentRowId);
	var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
	var cnNo = getDomElementById("consgNumbers" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(Obj.value)) {
			alert("Please enter declared value");
			getDomElementById("declaredValues" + currentRowId).disabled = false;
			setTimeout(function() {
				Obj.focus();
			}, 10);
			return true;
		} else if (selectedText.toUpperCase() == "CONSIGNOR") {
			getDomElementById("policyNos" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNos" + currentRowId).focus();
			}, 20);
			return false;
		} else if (isNull(insuredByDom.value)) {
			setTimeout(function() {
				getDomElementById("insuredByIds" + currentRowId).focus();
			}, 10);
			return false;
		} else if (productCode == "T") {
			getDomElementById("toPayAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("toPayAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmts" + currentRowId).disabled = true;
			getDomElementById("toPayAmts" + currentRowId).disabled = true;
			getDomElementById("policyNos" + currentRowId).disabled = true;
			getDomElementById("refNos" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("refNos" + currentRowId).focus();
			}, 10);
			flag = false;
		}
	}
}


/**
 * @param obj
 */
function validateInsuredBy(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "insuredByIds");
	var decValue = getDomElementById("declaredValues" + currentRowId).value;
	var insuredByText = getDomElementById("insuredByIds" + currentRowId).value;
	var charCode;
	if(isNull(MIN_DEC_VAL)){
		MIN_DEC_VAL = 0;
	}
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(insuredByText) && !isNull(decValue)
				&& decValue > MIN_DEC_VAL) {
			alert("Please select insuredBy.");
			getDomElementById("insuredByIds" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("insuredByIds" + currentRowId).focus();
			}, 10);
			return false;
		} else {
			return onlyNumberAndEnterKeyNav(e, Obj, focusId);
		}
	}
}

/**
 * @param evt
 * @param rowId
 */
function validatePolicyToPayCod4EnterKey(evt, rowId){	
	var charCode = getPressedKeyCode(evt);
	var currentObj = null;
	var currentObjId = null;
	if(!isNull(evt)){
		currentObj = getEventTargetJQObject(evt);
		currentObjId = currentObj.attr("id"); 
	}

	var toPayAmtsId = "toPayAmts" + rowId;
	var codAmtsId = "codAmts" + rowId;
	
	if( (currentObjId==toPayAmtsId || currentObjId==codAmtsId) && (charCode > 31 && (charCode < 48 || charCode > 57)) ){
		return false;
	}
	
	if (isEnterKey(evt)) {
		validatePolicyToPayCod(rowId, evt);
	}
}
/**
 * @param rowId
 * @param event
 */
function validatePolicyToPayCod(rowId, event){
	var focusId = getIdToFocus(rowId, event);
	if(!isNull(focusId)){
		focusById(focusId);
		//$("#"+focusId).focus();
	}else{
		addRow(rowId);
	}
}

/**
 * @param rowId
 * @param event
 * @returns
 */
function getIdToFocus(rowId, event) {

	//getIdToFocus for Policy No., To Pay Amt, Cod Amt
	var consgNumbersObj = document.getElementById("consgNumbers" + rowId);
	var insuredByObj = document.getElementById("insuredByIds" + rowId);
	//var declaredByObj = document.getElementById("declaredValues" + rowId);
	var policyNoObj = document.getElementById("policyNos" + rowId);
	var toPayAmtObj = document.getElementById("toPayAmts" + rowId);
	var codAmtObj = document.getElementById("codAmts" + rowId);
	var lcBankNamesObj = document.getElementById("lcBankNames" + rowId);
	var insuredByText = getSelectedDropDownTextByDOM(insuredByObj);
	var currentObj = null;
	if(!isNull(event)){
		currentObj = getEventTargetJQObject(event);
	}
	
	var focusId = null;
	if (isNull(consgNumbersObj.value)) {
		focusAlertMsg4TxtBox(consgNumbersObj, "Consignment Number");
	}
	if (isNull(insuredByObj.value)) {
		focusId = insuredByObj.id;
		if(!isNull(currentObj) && currentObj.attr("id") == focusId){
			focusAlertMsg4Select(currentObj, "Insured By");
		}
	}
	if (insuredByText.toUpperCase() == "CONSIGNOR" && isNull(policyNoObj.value)) {
		focusId = policyNoObj.id;
		if(!isNull(currentObj) && currentObj.attr("id") == focusId){
			focusAlertMsg4TxtBox(currentObj, "Policy Number");
		}
	}

	var productCode = consgNumbersObj.value.substring(4, 5);
	
	if (productCode == "T" && isEmptyRate(toPayAmtObj.value) && isNull(focusId)) {
		focusId = toPayAmtObj.id;
		if(!isNull(currentObj) && currentObj.attr("id") == focusId){
			focusAlertMsg4TxtBox(currentObj, "To Pay Amt.");
		}
	}
	
	if ((productCode == "L" || productCode == "D") && isEmptyRate(codAmtObj.value)  && isNull(focusId)) {
		focusId = codAmtObj.id;
		if(!isNull(currentObj) && currentObj.attr("id") == focusId){
			var msg1 = "COD Amt.";
			if(productCode == "D"){
				msg1 = "LC Amt.";
			}
			focusAlertMsg4TxtBox(currentObj, msg1);
		}
	}
	if (productCode == "D" && isNull(lcBankNamesObj.value) && isNull(focusId)) {
		focusId = lcBankNamesObj.id;
		if(!isNull(currentObj) && currentObj.attr("id") == focusId){
			focusAlertMsg4TxtBox(currentObj, "LC Bank Name");
		}
	}
	
	/*
	1.  To Pay Amt(mandatory for T series)
	2.	COD Amt(mandatory for L series) Optional for T series
	3.	LC Amt(mandatory for D series)
	4.	LC Bank Name(mandatory for D series)
	5. 	for others non editable
	*/

	return focusId;
}


/**
 * @param obj
 */
function validatePolicyNo(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "policyNos");
	var policyNo = getDomElementById("policyNos" + currentRowId).value;
	var insuredByDom = getDomElementById("insuredByIds" + currentRowId);
	var selectedText = getSelectedDropDownTextByDOM(insuredByDom);
	var charCode;
	var cnNo = getDomElementById("consgNumbers" + currentRowId).value;
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
			getDomElementById("policyNos" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("policyNos" + currentRowId).focus();
			}, 10);
			return true;
		} else if (productCode == "T") {
			getDomElementById("toPayAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("toPayAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmts" + currentRowId).disabled = true;
			getDomElementById("toPayAmts" + currentRowId).disabled = true;
			getDomElementById("policyNos" + currentRowId).disabled = true;
			var nextRow = parseIntNumber(currentRowId) + 1;
			return callEnterKey(e, document.getElementById('consgNumbers'
					+ nextRow));
		}
	}
}

function validateToPayAmount(e, Obj) {
	var currentRowId = getRowId(Obj, "toPayAmts");
	var charCode;
	var toPayAmount = getDomElementById("toPayAmts" + currentRowId).value;
	var cnNo = getDomElementById("consgNumbers" + currentRowId).value;
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
			getDomElementById("toPayAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("toPayAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (!isNull(toPayAmount) && productCode == "T") {
			getDomElementById("codAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else if (productCode == "L" || productCode == "D") {
			getDomElementById("codAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmts" + currentRowId).disabled = true;
			getDomElementById("toPayAmts" + currentRowId).disabled = true;
			getDomElementById("policyNos" + currentRowId).disabled = true;
			var nextRow = parseIntNumber(currentRowId) + 1;
			return callEnterKey(e, document.getElementById('consgNumbers'
					+ nextRow));
		}
	}
}

function validateCODAmount(e, Obj, focusId) {
	var currentRowId = getRowId(Obj, "codAmts");
	var charCode;
	var codOrLcAmt = getDomElementById("codAmts" + currentRowId).value;
	var cnNo = getDomElementById("consgNumbers" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (isNull(codOrLcAmt) && (productCode == "L" || productCode == "D")) {
			getDomElementById("codAmts" + currentRowId).disabled = false;
			setTimeout(function() {
				getDomElementById("codAmts" + currentRowId).focus();
			}, 10);
			flag = false;
		} else {
			getDomElementById("codAmts" + currentRowId).disabled = true;
			getDomElementById("toPayAmts" + currentRowId).disabled = true;
			getDomElementById("policyNos" + currentRowId).disabled = true;
			getDomElementById(focusId).disabled = false;
			setTimeout(function() {
				getDomElementById(focusId).focus();
			}, 10);
		}
	}
}

function validateCodAmt(codAmtObj) {
	// COD amount  should be accepted in L / D/ T series Cons only.
	//codAmts
	if (isNull(codAmtObj.value)){
		return;
	}
			
	var currentRowId = getRowId(codAmtObj, "codAmts");
	var cnNo = getDomElementById("consgNumbers" + currentRowId).value;
	var productCode = cnNo.substring(4, 5);
	
	if (productCode != "L" && productCode != "D" && productCode != "T") {
		codAmtObj.value = "";
		alert("COD amount should be accepted in L / D / T series Consignment only.");		
	}else{
		fixFormatUptoTwoDecimalPlace(codAmtObj);
	}
}

function enableDisableAmountBankFiels(rowId){
	var consgNumbersObj = document.getElementById("consgNumbers" + rowId);	
	var productCode = consgNumbersObj.value.substring(4, 5);	

	disableFieldById("toPayAmts" + rowId);
	disableFieldById("codAmts" + rowId);
	disableFieldById("lcBankNames" + rowId);
	disableFieldById("baAmts" + rowId);
	
	if (productCode == "T") {
		enableFieldById("toPayAmts" + rowId);
		enableFieldById("codAmts" + rowId);

		if(isNull($("#baAmts"+rowId).val())){
			enableFieldById("baAmts" + rowId);
		}
		/*if(!isNull($("#baAmts"+rowId).val())){
			disableFieldById("baAmts" + rowId);
		} else{
			enableFieldById("baAmts" + rowId);
		}*/
		
	} else if (productCode == "L") {
		enableFieldById("codAmts" + rowId);//cod amt
		
	} else if (productCode == "D") {
		enableFieldById("codAmts" + rowId);//lc amt
		enableFieldById("lcBankNames" + rowId);
		
	}/* else {
		disableFieldById("toPayAmts" + rowId);
		disableFieldById("codAmts" + rowId);
		disableFieldById("lcBankNames" + rowId);
	}*/

	/*
	1.  To Pay Amt(mandatory for T series)
	2.	COD Amt(mandatory for L series) Optional for T series
	3.	LC Amt(mandatory for D series)
	4.	LC Bank Name(mandatory for D series)
	5. 	BA Amt Optional for T series
	6. 	for others non editable
	*/
}