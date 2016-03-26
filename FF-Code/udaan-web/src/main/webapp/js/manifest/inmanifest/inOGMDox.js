/** The moduleName */
var moduleName = "inOGM";

var rowCount = 1;

var manifestDestCityFieldName = "pincode";
var weightKgFieldName = "weightAW";
var weightGmFieldName = "weightGm";
var consgNoFieldName = "consgNo";
var printUrl;

var isAddRowInProcess = false;

/**
 * adds new rows in the grid
 */
function addInOGMDoxRows() {

	if(isAddRowInProcess){
		return;
	}
	if(!isRequiredToAddRow()){
		return;
	}
	isAddRowInProcess = true;
	
	$('#inOGMDoxTable')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"  id="chk' + rowCount
									+ '" name="chkBoxName"  />',
							'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
							'<input type="text" class="txtbox" styleClass="txtbox" id="consgNo'
									+ rowCount
									+ '" name="to.consgNumbers" maxlength="12" size="20" class="txtbox width110" onfocus ="validateHeaderFields();" onchange="fnValidateNumber(this);" onkeypress="enterKeyNavigationFocus4Grid(event, manifestDestCityFieldName,'+ rowCount +');"/>',
							'<input type="text" class="txtbox" styleClass="txtbox" id="pincode'
									+ rowCount
									+ '" name="to.destPincodes" maxlength="6" onchange="validatePincode('
									+ rowCount
									+ ');" readonly="true" size="15" class="txtbox width100" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');" />',
							'<input type="text" size="15" class="txtbox width100" class="txtbox"  id="destCitys'
									+ rowCount + '" name="to.destCityNames" readonly="true"/>',
							'<input type="text" class="txtbox width30" maxlength="5" size="4" onkeypress="return validateWeight4EnterKey(event, '+ rowCount +');" name="weight" id="weightAW'
									+ rowCount + '" onblur="validateWeight(' + rowCount + ');" /> \
									<span class="lable">.</span> \
									<input type="text" name="weightGm" id="weightGm' + rowCount + '" class="txtbox width30" maxlength="3" size="4" onblur="validateWeight(' + rowCount + ');validatePolicyToPayCod('+ rowCount + ', event);" onkeypress="return validateWeight4EnterKey(event, '
									+ rowCount + ');" onfocus="clearWtGramIfEmpty(\'weightGm'+ rowCount +'\')"/> <input type="hidden" id="manifestWeight'
									+ rowCount + '" name="to.manifestWeights"/>',

							'<input type="text" id="toPayAmts' + rowCount + '" name="to.toPayAmts" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');"  onblur="fixFormatUptoTwoDecimalPlace(this);validatePolicyToPayCod(' + rowCount+');"/>',
							'<input type="text" id="codAmts' + rowCount + '" name="to.codAmts" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');" onblur="fixFormatUptoTwoDecimalPlace(this);validatePolicyToPayCod(' + rowCount+');"/>',									
							'<input type="text" id="baAmts'+ rowCount+ '" name="to.baAmts" size="7" maxlength="20" class="txtbox" onblur="fixFormatUptoTwoDecimalPlace(this);" onkeypress="return onlyDecimal(event);"/>',
							'<input type="text" id="lcBankNames'+ rowCount+ '" name="to.lcBankNames" size="7" class="txtbox" onkeypress="return validatePolicyToPayCod4EnterKey(event,' + rowCount+');" onblur="validatePolicyToPayCod(' + rowCount+');"/>',
							
							'<input type="text" class="txtbox"  maxlength="10" id="mobileNos'
									+ rowCount
									+ '" onchange="validateMobileNo(this)" name="to.mobileNos" onkeypress="return enterKeyNavForMobileNo(event, consgNoFieldName,'
									+ rowCount
									+ ');"/>',
							'<select class="selectBox width145" id="remarks'
									+ rowCount
									+ '" name="to.remarks" onkeypress="return enterKeyNavForRemarks(event, consgNoFieldName,'
									+ rowCount
									+ ');">' + REMARKS_OPTION
									+ '</select>\
							<input type="hidden" id="manifestIds'
																+ rowCount
																+ '" name="to.consignmentIds" value=""/>\
							<input type="hidden" id="destCityIds'
																+ rowCount
																+ '" name="to.destCityIds" value=""/>\
							<input type="hidden" id="destPincodeIds'
																+ rowCount
																+ '" name="to.destPincodeIds" value=""/>\
							<input type="hidden" id="comailId'
																+ rowCount
																+ '" name="to.comailIds" value=""/>\
							<input type="hidden" id="isCN'
																+ rowCount
																+ '" name="to.isCN" value=""/>\
							<input type="hidden" id="processCode'
																+ rowCount
																+ '" name="to.processCodes" value=""/>\
							<input type="hidden" id="consgIds'+rowCount+'" />\
							<input type="hidden" id="coMailTOs'
																+ rowCount
																+ '" name="to.coMailTOs" value=""/>\
							<input type="hidden" id="consignmentManifestIds'
																+ rowCount
																+ '" name="to.consignmentManifestIds"/>\
							<input type="hidden" id="comailManifestIds'
																+ rowCount
																+ '" name="to.comailManifestIds"/>\
							<input type="hidden" id="receivedStatus'
																+ rowCount
																+ '" name="to.receivedStatus" value="N"/>\
							<input type="hidden" id="position'+ rowCount +'" name="to.position" value="'+ rowCount +'" />', ]);
	rowCount++;

	isAddRowInProcess = false;
	return rowCount-1;
}
	
		
/**
 * loadDefaultObjects
 *
 * @author uchauhan
 */
function loadDefaultObjects(){
	addInOGMDoxRows();
	//getWeightFromWeighingMachine();
	setDataTableDefaultWidth();
	$("#ogmNo").focus();
	setMaxRowsForInManifest();
}

/**

 * deletes the selected rows for given table Id
 * 
 * @param tableId
 */
function deleteTableRow(tableId) {

	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				var rowId = getRowId(chkbox, "chk");
				var consgManifestId = document.getElementById("consgIds"+rowId).value;
				if (!isNull(consgManifestId)) {
					setDeletedIds(consgManifestId);
				}
				deleteRow(tableId, i - 1);
				setFinalWeight();
				// table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
		updateSerialNoVal(tableId);
	} catch (e) {
		alert(e);
	}
}

/**
 * fnCoMailOnly
 * 
 * @param obj
 * @author uchauhan
 */
function fnCoMailOnly(obj) {

	var table = document.getElementById('inOGMDoxTable');
	
	if (obj.checked) {
		obj.value = "Y";
		for ( var i = 1; i < table.rows.length; i++) {
			var count = table.rows[i].cells[0].childNodes[0].id.substring(3);
			document.getElementById("isCN" + count).value = "N";
			//document.getElementById("isCoMail" + count).value = "Y";
			document.getElementById("coMailOnly").value = "Y";
		}
	} else {
		obj.value = "N";
		for ( var i = 1; i < table.rows.length; i++) {
			var count = table.rows[i].cells[0].childNodes[0].id.substring(3);
			document.getElementById("isCN" + count).value = "Y";
		}
	}
}

function getValueByElementId(id) {
	return getDomElementById(id).value;
}

/**
 * setDeletedIds
 * 
 * @param consgManifestId
 * @author uchauhan
 */
function setDeletedIds(consgManifestId) {
	var offLoadIdsObj = document.getElementById("deletedIds");
	if (isNull(offLoadIdsObj.value)) {
		offLoadIdsObj.value = consgManifestId;
		document.getElementById("deletedIds").value = offLoadIdsObj.value;
	} else {
		offLoadIdsObj.value = offLoadIdsObj.value + "," + consgManifestId;
		document.getElementById("deletedIds").value = offLoadIdsObj.value;
	}
}

/**
 * delets the table row
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

/**
 * validates the weight for required format
 * 
 * @param rowIdd
 *//*
function validateWeight(rowIdd) {
	
	 * if(!isNull(getValueByElementId("manifestIds"))){ return; }
	 
	var weightKgObj = document.getElementById("weightAW" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
	var manifestWeightsObj = document.getElementById("manifestWeight" + rowIdd);

	fixWeightFormatForGram(weightGmObj);

	if (weightKgObj.value.length == 0) {
		weightKgObj.value = "0";
	}
	manifestWeightsObj.value = weightKgObj.value + "." + weightGmObj.value;
	setFinalWeight();
}*/



/**
 * validates the weight for required format
 * @param rowIdd
 */
function validateWeight(rowIdd){
	/*if(!isNull(getValueByElementId("manifestIds"))){
		return;
	}*/
	var weightKgObj = document.getElementById("weightAW" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
    var manifestWeightsObj = document.getElementById("manifestWeight" + rowIdd);
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
/*	if(weightKgObj.value.length>3){
		clearFocusAlertMsg(weightKgObj,"enter only three digits");
		return;
	}
	if(weightGmObj.value.length>3){
		clearFocusAlertMsg(weightGmObj,"enter only three digits");
		return;
	}*/
	

	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	manifestWeightsObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	
	
/*	
	if(isEmptyWeight(manifestWeightsObj.value)){
		clearFocusAlertMsg(manifestWeightsObj,"Please Enter Weight");
		return false;
	}*/
	
	manifestWeightsObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	setFinalWeight();
}

/**
 * sets the final weight as sum of all weights of packet
 */
function setFinalWeight() {
	var totalWeight = 0;
	var table = document.getElementById('inOGMDoxTable');

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weight = document.getElementById("manifestWeight" + rowId).value;
		if (!isNull(weight)) {
			totalWeight += parseFloat(weight);
		}
	}
	var manifestWeightObj = document.getElementById("totalWeight");
	manifestWeightObj.value = totalWeight;
	fixFormatUptoThreeDecimalPlace(manifestWeightObj);
	var manifestWeight = manifestWeightObj.value + "";
	document.getElementById("wtKg").value = manifestWeight.split(".")[0];
	document.getElementById("wtGm").value = manifestWeight.split(".")[1];
}

/**
 * validates the mobile Number
 * 
 * @param mobileNoObj
 */
function validateMobileNo(mobileNoObj) {
	if (mobileNoObj.value.length != 10) {
		clearFocusAlertMsg(mobileNoObj, "Mobile No. must be of 10 digit!");
	}
}

/**
 * clears and sets focus
 * 
 * @param obj
 *            which has to be cleared
 * @param msg
 *            which needs to be shown as popup
 */
function clearFocusAlertMsg(obj, msg) {
	obj.value = "";
	setFocus(obj);
	alert(msg);
}

/**
 * sets focus
 * 
 * @Desc sets the focus on the given field
 * @param obj
 */
function setFocus(obj) {
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * validates the mandatory fields
 * 
 * @returns {true} if all fields are valid
 */
function validateMandatoryFields() {
	var isValid = true;
	var ogmNo = document.getElementById("ogmNo").value;
	var destOfficeName = document.getElementById("officeName").value;
	var region = document.getElementById("region").value;
	var destCity = document.getElementById("destCity").value;
	var destOfficeType = document.getElementById("destOfficeType").value;
	var originOffice = document.getElementById("office").value;

	var table = document.getElementById("inOGMDoxTable");

	if (isNull(ogmNo)) {
		alert("Please enter OGM Number");
		setTimeout(function() {
			document.getElementById("ogmNo").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(region)) {
		alert("Please select region");
		setTimeout(function() {
			document.getElementById("region").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(destCity)) {
		alert("Please select Origin City");
		setTimeout(function() {
			document.getElementById("destCity").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(destOfficeType)) {
		alert("Please select Office Type");
		setTimeout(function() {
			document.getElementById("destOfficeType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(originOffice)) {
		alert("Please select Origin Office");
		setTimeout(function() {
			document.getElementById("originOffice").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		
		var consgNumbersObj = $("#consgNo" + rowId);

		if (isNull(consgNumbersObj.val())) {
			continue;
		}
		if (!validateMandatoryRowFields(rowId)) {
			return false;
		}
/*
		var consgNo = document.getElementById("consgNo" + rowId);
		var pincode = document.getElementById("pincode" + rowId);
		var weightAW = document.getElementById("manifestWeight" + rowId);
		var isCN = document.getElementById("isCN" + rowId).value;

		if ( isNull(isCN) || isCN == 'Y') {

			if (rowId == 1) {
				if (isNull(consgNo.value) ) {
					alert("Please enter Consignment Number.");
					setTimeout(function() {
						document.getElementById("consgNo" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
				if (isEmptyWeight(weightAW.value)) {
					alert("Please Enter Weight.");
					setTimeout(function() {
						document.getElementById("weightAW" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}

				if (isNull(pincode.value)) {
					alert("Please Enter Pincode.");
					setTimeout(function() {
						document.getElementById("pincode" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
			} else {
				if (!isNull(consgNo.value)) {
					if (isNull(consgNo.value)) {
						alert("Please enter OGM Number.");
						setTimeout(function() {
							document.getElementById("consgNo" + rowId).focus();
						}, 10);
						isValid = false;
						return isValid;
					}
					if (isEmptyWeight(weightAW.value)) {
						alert("Please Enter Weight.");
						setTimeout(
								function() {
									document.getElementById("weightAW" + rowId)
											.focus();
								}, 10);
						isValid = false;
						return isValid;
					}

					if (isNull(pincode.value)) {
						alert("Please Enter Pincode.");
						setTimeout(function() {
							document.getElementById("pincode" + rowId).focus();
						}, 10);
						isValid = false;
						return isValid;
					}
				}
			}

		}
		else if( isCN == 'N'){
			if (rowId == 1) {
				if (isNull(consgNo.value) ) {
					alert("Please enter Comail Number.");
					setTimeout(function() {
						document.getElementById("consgNo" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
			}
			
		}

		return isValid;*/
	}
	return true;
}

var isWeightMandatory = "N";
/**
 * validateMandatoryRowFields
 * 
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId) {
	var isCN = document.getElementById("isCN" + rowId).value;
	var consgNumbersObj = document.getElementById("consgNo" + rowId);
	var destPincodesObj = document.getElementById("pincode" + rowId);
	var consgWeightsObj = document.getElementById("manifestWeight" + rowId);
	var weightKgObj = document.getElementById("weightAW" + rowId);
	var weightGmObj = document.getElementById("weightGm" + rowId);
	var toPayAmtObj = document.getElementById("toPayAmts" + rowId);
	var codAmtObj = document.getElementById("codAmts" + rowId);
	var lcBankNamesObj = document.getElementById("lcBankNames" + rowId);
	
	if(isWeightMandatory=="Y"){
		return;
	}

	if (isNull(consgNumbersObj.value)) {
		focusAlertMsg4TxtBox(consgNumbersObj, "Consignment Number");
		return false;
	}
	if ( isNull(isCN) || isCN == 'Y') {
		
		if (isNull(destPincodesObj.value)) {
			focusAlertMsg4TxtBox(destPincodesObj, "Pincode");
			return false;
		}
		
		if (isEmptyWeight(consgWeightsObj.value)) {
			isWeightMandatory = "Y";
			focusAlertMsg4TxtBox(weightGmObj, "Weight");
			isWeightMandatory = "N";
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
 * validates the pincode
 * 
 * @param rowId
 */
function validatePincode(rowId) {
	var destPincodeObj = document.getElementById("pincode" + rowId);

	destPincodeObj.value = $.trim(destPincodeObj.value);

	var url = "./inOGMDoxManifest.do?submitName=validatePincode&pincode="
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
 * populates the pincode
 * 
 * @param data
 * @param rowId
 */
function populatePincode(data, rowId) {

	var inManifestValidationTO = eval('(' + data + ')');
	if (!isNull(inManifestValidationTO.errorMsg)) {
		var destPincodesObj = document.getElementById("pincode" + rowId);
		clearFocusAlertMsg(destPincodesObj, inManifestValidationTO.errorMsg);
		return;
	}
	document.getElementById("destPincodeIds" + rowId).value = inManifestValidationTO.pincodeTO.pincodeId;

	if (!isNull(inManifestValidationTO.cityTO)) {
		document.getElementById("destCitys" + rowId).value = inManifestValidationTO.cityTO.cityName;
		document.getElementById("destCityIds" + rowId).value = inManifestValidationTO.cityTO.cityId;
	}

}

/**
 * save the InOgmDox
 */
function saveOrUpdateInOGMDox() {

	setFinalWeight();
	
	if (validateMandatoryFields()) {

		var flag = confirm("Do you want to Save the In OGM Details.");
		if (!flag) {
			return;
		}
		
		enableScreen();
		showProcessing();
		url = './inOGMDoxManifest.do?submitName=saveOrUpdateInOGMDox';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#inOGMDoxManifestForm").serialize(),
			success : function(req) {
				saveResponse(req);
			}
		});
	}

}

/**
 * displays the response as successfull or unsuccessfull
 * @param msg
 */
function saveResponse(msg) {
	hideProcessing();
	alert(msg);
	document.getElementById("region").options.length = 0;
	document.inOGMDoxManifestForm.action = "/udaan-web/inOGMDoxManifest.do?submitName=viewInOGMDox";
	document.inOGMDoxManifestForm.submit();

}

/**
 * adds new row if there is no existing row
 * 
 * @param selectedRowId
 */
function addRow(selectedRowId) {
	var table = document.getElementById("inOGMDoxTable");
	var lastRow = table.rows.length - 1;
	var isNewRow = false;
	/*var manifestWeightsObj = document.getElementById("manifestWeight" + selectedRowId);
	var weightKgObj = document.getElementById("weightAW" + selectedRowId);
	if(isEmptyWeight(manifestWeightsObj.value) && $("#isCN" + selectedRowId).val()=="Y"){
		clearFocusAlertMsg(weightKgObj,"Please Enter Weight");
		return false;
	}*/
	

	if (!validateMandatoryRowFields(selectedRowId)) {
		//cnContentsObj.value = "";
		return;
	}

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (rowId == selectedRowId && i == lastRow) {
			isNewRow = true;
		}
	}
	var rowId = 0;
	var cnNo = document.getElementById("consgNo"+ selectedRowId).value;
	if (isNewRow && !isNull(cnNo)) {
		rowId = addInOGMDoxRows();
	}else if (isNull(cnNo)){
		rowId = selectedRowId;
	}else{
		rowId = parseInt(selectedRowId) + 1;
	}
	
	$("#consgNo" + rowId).focus();
}


/**
 * disables the screen
 */
function disableScreen() {

	var f = document.forms['inOGMDoxManifestForm'];
	for ( var i = 0, fLen = f.length; i < fLen; i++) {

		f.elements[i].disabled = 'disabled';
	}
	document.getElementById('clear').disabled = false;
	document.getElementById('print').disabled = false;
	document.getElementById('delete').disabled = false;
	document.getElementById('save').disabled = false;
	document.getElementById('edit').disabled = false;
}

function disableButton(){
//	var f = document.forms['inOGMDoxManifestForm'];
//	for ( var i = 0, fLen = f.length; i < fLen; i++) {
//		f.elements[i].disabled = 'disabled';
//	}
	
//	document.getElementById('clear').style.display= 'none';
//	document.getElementById('delete').style.display='none';
//	document.getElementById('save').style.display='none';
//	document.getElementById('edit').style.display='none';
//	document.getElementById('print').disabled=false;
//	document.getElementById('cancel').disabled=false;
	disableAll();
	buttonEnableById("print");
	buttonEnableById("cancel");
}

/**
 * enables the screen
 */
function enableScreen() {

	var f = document.forms['inOGMDoxManifestForm'];
	for ( var i = 0, fLen = f.length; i < fLen; i++) {

		f.elements[i].disabled = false;
	}
	document.getElementById('clear').disabled = false;
	document.getElementById('print').disabled = false;
	document.getElementById('delete').disabled = false;
	document.getElementById('save').disabled = false;
	document.getElementById('edit').disabled = false;
}

/**
 * @Desc gets the city if pincode is valid else clears the pincode field
 * @param response
 *            if the pincode is valid or not
 */

/**
 * @Desc validates the format of given pincode number
 * @param obj
 *            is a pincode object
 */
function isValidPincode(obj) {
	var pincode = obj.value;

	if (isNaN(pincode) || pincode.indexOf(" ") != -1) {
		alert("Enter numeric value");
		obj.value = "";
		setFocus(obj);
		return false;
	}
	if (pincode.length != 6) {
		alert("enter 6 digits");
		obj.value = "";
		setFocus(obj);
		return false;
	}
	return true;
}

/**
 * @param manifestNoObj
 *            contains the manifest number
 * @returns true if format of manifest number is valid else returns false
 */
function isValidManifest() {
	var manifestNoObj = document.getElementById("ogmNo");
	if (isValidPacketNo(manifestNoObj)) {
		isOGMManifested();
	} else
		return false;
}

/**
 * gets the details if mbpl number exsists in the database
 */
function isOGMManifested() {
	var ogmNo = document.getElementById("ogmNo").value;
	var processCode = document.getElementById("processCode").value;
	var updatedProcessCode = document.getElementById("updatedProcessCode").value;
	var loginOff = document.getElementById("officeName").value;
	var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;

	showProcessing();
	url = "./inOGMDoxManifest.do?submitName=getConsgManifestedDetails&manifestNumber="+ ogmNo+"&processCode="+processCode+"&updatedProcessCode="+updatedProcessCode+"&loggedInOffice="+loginOff+"&loggedInOfficeId="+loggedInOfficeId;
	ajaxCallWithoutForm(url, populateManifest);
}

/*
 * function getConsgDetails(congObject){
 * 
 * var consgNo = document.getElementById("consgNo").value; url =
 * "./inOGMDoxManifest.do?submitName=getConsgDetails&consignmentNumber="+consgNo;
 * ajaxCallWithoutForm(url,populateManifest); }
 */

/**
 * fnValidateNumber
 * 
 * @param obj
 * @author uchauhan
 */
function fnValidateNumber(obj) {
	if (!isNull(obj.value)) {
		obj.value = obj.value.toUpperCase();
		var count = getRowId(obj, "consgNo");
		
		var coMail = document.getElementById("coMailOnly");		

		ROW_COUNT = count;
		
		if (validateComail(obj)) {

			validateCoMailNumber(obj);
			
		} else if (validateConsignmentBranchCode(obj)) {
			document.getElementById("isCN" + count).value = "Y";
			enableNonComail(count);
			if (coMail.checked) {
				clearFocusAlertMsg(obj, "Consignments not allowed");
				return false;
			}
			getConsgDetails(obj);

		} else {
			clearFocusAlertMsg(obj, "Invalid Number");
			return false;
		}

	}
}

function validateCoMailNumber(consgNumberObj){
	var coMailNo = consgNumberObj.value;
	var loggedInOfficeId = $("#loggedInOfficeId").val();
	
	if (isDuplicateFieldInGrid(consgNumberObj, "consgNo", "inOGMDoxTable")) {
		clearFocusAlertMsg(consgNumberObj, "Duplicate Comail Number!");
		return false;
	}
	
	url = "./inOGMDoxManifest.do?submitName=validateCoMailNumber&coMailNo="+coMailNo+"&loggedInOfficeId="+loggedInOfficeId;

	showProcessing();
	ajaxCallWithoutForm(url, populateCoMail);
}

function populateCoMail(inManifestValidationTO) {
	hideProcessing();
	//document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
	
	if(!isNull(inManifestValidationTO.errorMsg)){
		clearFocusAlertMsg(document.getElementById("consgNo" + ROW_COUNT), inManifestValidationTO.errorMsg);
		return;
	}
	$("#comailId" + ROW_COUNT).val(inManifestValidationTO.coMailId);
	
	document.getElementById("isCN" + ROW_COUNT).value = "N";
	var coMailNum = document.getElementById("consgNo" + ROW_COUNT).value;
	document.getElementById("coMailTOs" + ROW_COUNT).value = coMailNum;
	disableNonComail(ROW_COUNT);
	addRow(ROW_COUNT);
}

function isConsgManifested(consgNum) {
	url = "./inOGMDoxManifest.do?submitName=isConsgManifested&manifestNumber="
			+ consgNum;
	ajaxCallWithoutForm(url, isConsgManifestedResp);
}

function isConsgManifestedResp(data) {
}


/**
 * disables all non comail fields 
 * @param count
 */
function disableNonComail(count) {

	document.getElementById("pincode" + count).disabled = true;
	document.getElementById("destCitys" + count).disabled = true;
	document.getElementById("weightAW" + count).disabled = true;
	document.getElementById("weightGm" + count).disabled = true;
	document.getElementById("mobileNos" + count).disabled = true;
	document.getElementById("remarks" + count).disabled = true;
}


/**
 * enables all non comail fields
 * @param count
 */
function enableNonComail(count) {

	document.getElementById("pincode" + count).disabled = false;
	document.getElementById("destCitys" + count).disabled = false;
	document.getElementById("weightAW" + count).disabled = false;
	document.getElementById("weightGm" + count).disabled = false;
	document.getElementById("mobileNos" + count).disabled = false;
	document.getElementById("remarks" + count).disabled = false;

}

/**
 * validates the consg Number
 * @param consgNoObj
 * @returns {Boolean}
 */
function isValidConsgNo(consgNoObj) {
	// Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	// last 7 char are numeric only.

	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	consgNoObj.value = $.trim(consgNoObj.value);
	consgNoObj.value = consgNoObj.value.toUpperCase();

	if (isNull(consgNoObj.value)) {
		return false;
	}

	if (consgNoObj.value.length != 12) {
		// clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12
		// characters only!");
		return false;
	}

/*	if (!consgNoObj.value.substring(0, 1).match(letters)
			|| !consgNoObj.value.substring(4, 5).match(letters)
			|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		// clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not
		// correct!");
		return false;
	}*/
	//alert(consgNoObj.value.substring(0, 5));

	if (!consgNoObj.value.substring(0, 5).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		// clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not
		// correct!");
		return false;
	}

	return true;
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
			/*
			 * alert("Comail number length should be 12 character"); obj.value =
			 * ""; setTimeout(function() { obj.focus(); }, 10);
			 */
			flag = false;
		} else {
			var numpattern = /^[0-9]{3,20}$/;

			if (obj.value.substring(0, 1).toUpperCase() != 'C'
					|| obj.value.substring(1, 2).toUpperCase() != 'M'
					|| !numpattern.test(obj.value.substring(3, 12))) {
				/*
				 * alert('Comail number format is not correct'); obj.value = "";
				 * setTimeout(function() { obj.focus(); }, 10);
				 */
				flag = false;
			} else {
				// var count = getRowId(obj, "consgNo");
				flag = true;
				/*
				 * // last row count = parseIntNumber(count) + 1; if (count <=
				 * (parseIntNumber(maxCNsAllowed) +
				 * parseIntNumber(maxComailsAllowed)))
				 * document.getElementById("consgNo" + count).focus();
				 */
			}
		}
	}
	return flag;
}

// Manifest BPL/MBPL validation Starts
function getConsgDetails(manifestNumberObj) {
	// var bplNumberObj = document.getElementById("manifestNumber");
	var rowId = getRowId(manifestNumberObj, "consgNo");
	var consgNo = document.getElementById("consgNo" + rowId).value;
	var manifestNum = document.getElementById("ogmNo").value;
	if(isNull(manifestNum)){
		clearFocusAlertMsg(manifestNumberObj, "Please enter OGM Number");
		return;
	}
	ROW_COUNT = rowId;
	var loggedInOfficeId = $("#loggedInOfficeId").val();
	url = "./inOGMDoxManifest.do?submitName=getConsgDetails&consignmentNumber="+consgNo+"&loggedInOfficeId="+loggedInOfficeId+"&manifestNum="+manifestNum;

	if (isDuplicateFieldInGrid(manifestNumberObj, "consgNo", "inOGMDoxTable")) {
		clearFocusAlertMsg(manifestNumberObj, "Duplicate Consignment Number!");
		return;
	}
	
	showProcessing();
	ajaxCallWithoutForm(url, populateConsignment);
}

/**
 * populates the grid with consignment data
 * @param data
 */
function populateConsignment(data) {
	hideProcessing();
	document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
	
	var consginment = data;
	if (!isNull(consginment)) {
		if(!isNull(consginment.message)){
			//alert(consginment.message);
			clearFocusAlertMsg(document.getElementById("consgNo" + ROW_COUNT), consginment.message);
			return;
		}
		
		if(consginment.isNewConsignment){
			$("#pincode" + ROW_COUNT).focus();
			readOnlyConsgFields(ROW_COUNT); 
			enableDisableAmountBankFiels(ROW_COUNT);
			return;
		}
		/*bookingDetail=data;
		getWtFromWMForOGM();*/

		document.getElementById("consgNo" + ROW_COUNT).value = consginment.consgNo;
		document.getElementById("pincode" + ROW_COUNT).value = consginment.destPincode.pincode;
		document.getElementById("destPincodeIds" + ROW_COUNT).value = consginment.destPincode.pincodeId;
		document.getElementById("destCitys" + ROW_COUNT).value = consginment.destCity.cityName;
		document.getElementById("weightAW" + ROW_COUNT).value = consginment.finalWeight;
		document.getElementById("manifestWeight" + ROW_COUNT).value = consginment.finalWeight;
		document.getElementById("mobileNos" + ROW_COUNT).value = consginment.mobileNo;
		document.getElementById("remarks"+ROW_COUNT).value = consginment.remarks;
		document.getElementById("manifestIds" + ROW_COUNT).value = consginment.consgId;
		document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
		document.getElementById("destCityIds" + ROW_COUNT).value = consginment.destCity.cityId;
		document.getElementById("consgIds" + ROW_COUNT).value = consginment.consgId;
		
		//added price details
		document.getElementById("toPayAmts" + ROW_COUNT).value = consginment.topayAmt;
		document.getElementById("lcBankNames" + ROW_COUNT).value = consginment.lcBankName;
		if (!isEmptyRate(consginment.lcAmount)) {
			document.getElementById("codAmts" + ROW_COUNT).value = consginment.lcAmount;
		} else {
			document.getElementById("codAmts" + ROW_COUNT).value = consginment.codAmt;
		}

		document.getElementById("baAmts" + ROW_COUNT).value = consginment.baAmt;
		
		validateWeightFromWeighingMachine(ROW_COUNT);
		
		var splitweightGm = consginment.finalWeight + "";
		if (!isNull(splitweightGm)) {
			weightKgValue = splitweightGm.split(".");
			document.getElementById("weightAW" + ROW_COUNT).value = weightKgValue[0];
			weightGmValue = splitweightGm.split(".")[1];
			if (isNull(weightGmValue)) {
				weightGmValue = "000";
				document.getElementById("weightGm" + ROW_COUNT).value = weightGmValue;
			} else {
				document.getElementById("weightGm" + ROW_COUNT).value = weightGmValue;
				fixWeightFormatForGram(document.getElementById("weightGm" + ROW_COUNT));
			}
			setFinalWeight();
		}
		addRow(ROW_COUNT);
	}
	else{
		document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
	}
	readOnlyConsgFields(ROW_COUNT);
	enableDisableAmountBankFiels(ROW_COUNT);
}

/**
 * readOnlyConsgFields
 *
 * @param rowId
 * @author narmdr
 */
function readOnlyConsgFields(rowId){
	/*isCN3
	manifestIds3
	consgIds3*/
	var isReadOnly = false;
	if(!isNull($("#consgIds"+rowId).val())){
		isReadOnly = true;
	}
	document.getElementById("pincode" + rowId).readOnly = isReadOnly;	
}

/**
 * checks if there are any duplicate numbers in the grid
 * @param fieldNameToValidateObj
 * @param fieldNameToValidate
 * @param tableId
 * @returns {Boolean}
 */
function isDuplicateFieldInGrid(fieldNameToValidateObj, fieldNameToValidate,
		tableId) {
	var table = document.getElementById(tableId);
	var selectedRowId = getRowId(fieldNameToValidateObj, fieldNameToValidate);

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var fieldValue = document.getElementById(fieldNameToValidate + rowId).value;
		if (fieldNameToValidateObj.value.toUpperCase() == fieldValue.toUpperCase()
				&& rowId != selectedRowId) {
			return true;
		}
	}
	return false;
}

/**
 * validates the packet number
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidPacketNo(manifestNoObj) {
	// City Code+7 digits :: BOY1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();

	if (isNull(manifestNoObj.value)) {
		focusAlertMsg4TxtBox(manifestNoObj, "OGM/Open Manifest No.");
		return false;
	}

	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}

	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric)
			|| !numpattern.test(manifestNoObj.value.substring(3))) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. Format is not correct!");
		return false;
	}
	return true;
}

/**
 * splitFinalWeight
 * 
 * @param finalWeight
 * @author uchauhan
 */
function splitFinalWeight(finalWeight) {
	var weightInKg = new Array();
	var weightGm = null;
	if (!isNull(finalWeight)) {
		weightInKg = finalWeight.split(".");
		document.getElementById("wtKg").value = weightInKg[0];
		weightGm = weightInKg[1];

		if (isNull(weightGm)) {
			weightGm = "000";
			document.getElementById("wtGm").value = weightGm;
		} else {
			document.getElementById("wtGm").value = weightGm;
		}
	}
}


//var isPopulateManifestInProcess = false;
/**
 * populates the manifest data
 * @param manifestDetailsTO
 */
function populateManifest(manifestDetailsTO) {
//	if(isPopulateManifestInProcess){
//		return;
//	}
//	isPopulateManifestInProcess = true;
	
	hideProcessing();
	// var manifestBaseTO = eval('(' + data + ')');
	//var manifestDetailsTO = data;	

	var errorMsg = getErrorMessage(manifestDetailsTO);
	var ogmNoObj = document.getElementById("ogmNo");

	if(!isNull(errorMsg)){
//		isPopulateManifestInProcess = false;
		clearFocusAlertMsg(ogmNoObj, errorMsg);
		return;
	}
	
	if (!isNull(manifestDetailsTO)) {
		// Header Part
		document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
		//document.getElementById("headerRemarks").value = manifestDetailsTO.headerRemarks;
		$("#headerRemarks").val(manifestDetailsTO.headerRemarks);
		$("#manifestEmbeddedIn").val(manifestDetailsTO.manifestEmbeddedIn);
		$("#manifestReceivedStatus").val(manifestDetailsTO.manifestReceivedStatus);
		
		/*var regionOffice = manifestDetailsTO.destinationOfficeTO.officeName
				+ "-";
		var loggedInOfcName = manifestDetailsTO.loginOfficeName;
		var setLoginOfficeName = regionOffice.concat(loggedInOfcName);*/
		/*var loginOffice = manifestDetailsTO.loginRegionOffice;
		document.getElementById("officeName").value = loginOffice;*/
		if (!isNull(manifestDetailsTO.loginRegionOffice)) {
			document.getElementById("officeName").value = manifestDetailsTO.loginRegionOffice;				
		}
		
        if(!isNull(manifestDetailsTO.originRegionTO)){
		document.getElementById("region").value = manifestDetailsTO.originRegionTO.regionId;
        }

        if(!manifestDetailsTO.isInBplDoxPacket){
    		splitFinalWeight(manifestDetailsTO.manifestWeight + "");

    		if (manifestDetailsTO.isCoMail == "Y"){
    			document.getElementById("coMailOnly").checked = true;			
    		} else{
    			document.getElementById("coMailOnly").disabled = true;			
    		}
    		$("#coMailOnly").val(manifestDetailsTO.isCoMail);
        }

		if (!isNull(manifestDetailsTO.originCityTO)) {
			clearDropDownList("destCity");
			addOptionTODropDown("destCity",
					manifestDetailsTO.originCityTO.cityName,
					manifestDetailsTO.originCityTO.cityId);
			document.getElementById("destCity").value = manifestDetailsTO.originCityTO.cityId;
		}

		if (!isNull(manifestDetailsTO.officeTypeTO)) {
			clearDropDownList("destOfficeType");
			addOptionTODropDown("destOfficeType",
					manifestDetailsTO.officeTypeTO.offcTypeDesc,
					manifestDetailsTO.officeTypeTO.offcTypeId);
			document.getElementById("destOfficeType").value = manifestDetailsTO.officeTypeTO.offcTypeId;
		}

		var code = manifestDetailsTO.originOfficeTO.officeCode+"-"+manifestDetailsTO.originOfficeTO.officeName;
		
		if (!isNull(manifestDetailsTO.originOfficeTO)) {
			clearDropDownList("office");
			addOptionTODropDown("office",code,manifestDetailsTO.originOfficeTO.officeId);
			document.getElementById("office").value = manifestDetailsTO.originOfficeTO.officeId;
		}

		//set dest office & city
		if(!isNull(manifestDetailsTO.destinationOfficeId)){
			$("#destOffcId").val(manifestDetailsTO.destinationOfficeId);
			$("#destinationOfficeId").val(manifestDetailsTO.destinationOfficeId);
		}
		if(!isNull(manifestDetailsTO.destinationCityId)){
			$("#destCityId").val(manifestDetailsTO.destinationCityId);
		}
		
		var rowIddToFocus = null;
		var comailStartIndex = 0;
		var isConsgComail = false;
		if (!isNull(manifestDetailsTO.inManifestOGMDetailTOs)) {
			isConsgComail = true;
			for ( var i = 0; i < manifestDetailsTO.inManifestOGMDetailTOs.length; i++) {
				document.getElementById("consgNo" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].consignmentNumber;
				document.getElementById("pincode" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].destPincode.pincode;
				document.getElementById("destCitys" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].destCity.cityName;
				document.getElementById("weightAW" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].manifestWeight;
				document.getElementById("manifestWeight" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].manifestWeight;
				document.getElementById("mobileNos" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].mobileNumber;
				document.getElementById("remarks" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].remarks;
				document.getElementById("manifestIds" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].consignmentId;
				document.getElementById("consgIds" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].consignmentId;
				document.getElementById("destPincodeIds" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].destPincode.pincodeId;
				document.getElementById("destCityIds" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].destCity.cityId;
				document.getElementById("receivedStatus" + (i + 1)).value = "R";
				document.getElementById("consignmentManifestIds" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].consignmentManifestId;
				document.getElementById("isCN" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].isCN;
				
				//added price details
				document.getElementById("toPayAmts" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].toPayAmt;
				document.getElementById("lcBankNames" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].lcBankName;
				document.getElementById("codAmts" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].codAmt;

				document.getElementById("baAmts" + (i + 1)).value = manifestDetailsTO.inManifestOGMDetailTOs[i].baAmt;
				
				var splitweightGm = manifestDetailsTO.inManifestOGMDetailTOs[i].manifestWeight+ "";
				if (!isNull(splitweightGm)) {
					weightKgValue = splitweightGm.split(".");
					document.getElementById("weightAW" + (i + 1)).value = weightKgValue[0];
					weightGmValue = splitweightGm.split(".")[1];
					if (isNull(weightGmValue)) {
						weightGmValue = "000";
						document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
					} else {
						document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
						fixWeightFormatForGram(document.getElementById("weightGm" + (i + 1)));
					}
				}
				comailStartIndex = (i + 1);
				enableDisableAmountBankFiels((i + 1));
				//commented bcz of bulk populate affected if any data missed.
				//addRow((i + 1));
				rowIddToFocus = addInOGMDoxRows();
				//$("#consgNo"+rowIddToFocus).focus();
			}
		}
		if (!isNull(manifestDetailsTO.inCoMailTOs)) {
			isConsgComail = true;
			for ( var i = 0; i < manifestDetailsTO.inCoMailTOs.length; i++, comailStartIndex++) {
				document.getElementById("consgNo" + (comailStartIndex + 1)).value = manifestDetailsTO.inCoMailTOs[i].comailNo;
				document.getElementById("coMailTOs" + (comailStartIndex + 1)).value = manifestDetailsTO.inCoMailTOs[i].comailNo;
				document.getElementById("comailId" + (comailStartIndex + 1)).value = manifestDetailsTO.inCoMailTOs[i].comailId;
				document.getElementById("isCN" + (comailStartIndex + 1)).value = manifestDetailsTO.inCoMailTOs[i].isCN;
				// document.getElementById("comailManifestIds" +
				// (comailStartIndex+1)).value =
				// manifestDetailsTO.inCoMailTOs[i].comailManifestId;
				addRow(comailStartIndex + 1);
			}
		}
		
		if (manifestDetailsTO.isManifested == "Y"){
			document.getElementById("dateTime").value = manifestDetailsTO.manifestDate;
			disableButton();
		}
		/*else if(manifestDetailsTO.isManifested == "N"){
			disableScreen();
		}*/
		if(!isConsgComail){
			$("#consgNo1").focus();
		} else if(!isNull(rowIddToFocus)){
			$("#consgNo"+rowIddToFocus).focus();
		}

		document.getElementById("ogmNo").readOnly = true;
	}
	else {
		$("#region").focus();
	}

//	isPopulateManifestInProcess = false;
	
	//to prepare iFrame for print
	/*var ogmNo = document.getElementById("ogmNo").value;
	var processCode = document.getElementById("processCode").value;
	var updatedProcessCode = document.getElementById("updatedProcessCode").value;
	var loginOff = document.getElementById("officeName").value;
	var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
	url = "./inOGMDoxManifest.do?submitName=printManifestedDetails&manifestNumber="+ ogmNo+"&processCode="
		+processCode+"&updatedProcessCode="+updatedProcessCode+"&loggedInOffice="+loginOff+"&loggedInOfficeId="+loggedInOfficeId;
	printUrl = url;
	printIframe(printUrl);*/
}

function printIframe(printUrl){
	document.getElementById("iFrame").setAttribute('src',printUrl);
}

function cancelScreen(){
	if (promptConfirmation("clear")){
	document.getElementById("region").options.length = 0;
	document.inOGMDoxManifestForm.action = "/udaan-web/inOGMDoxManifest.do?submitName=viewInOGMDox";
	document.inOGMDoxManifestForm.submit();
	}
}

/**
 * clears the details in the grid 
 */
function clearDetails() {
	if (promptConfirmation("clear")){
	$('#inOGMDoxTable').dataTable().fnClearTable();
	document.getElementById("wtKg").value = "";
	document.getElementById("wtGm").value = "";
	rowCount = 1;
	addInOGMDoxRows();
	}
}


/**
 * validateDestOriginOffice
 *
 * @author narmdr
 */
function validateDestOriginOffice(){
	var destOfficeId = document.getElementById("loggedInOfficeId").value;
	var originOfficeObj = document.getElementById("office");
	if(originOfficeObj.value==destOfficeId){
		clearFocusAlertMsg(originOfficeObj, "Origin Office and Destination Office should not be same!");
	}
}

function printOgm(){
	if(confirm("Do you want to print?")){
		var ogmNo = document.getElementById("ogmNo").value;
		var processCode = document.getElementById("processCode").value;
		var updatedProcessCode = document.getElementById("updatedProcessCode").value;
		var loginOff = document.getElementById("officeName").value;
		var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
		
		
		/*if(!isNull(ogmNo)){
			url = "./inOGMDoxManifest.do?submitName=printManifestedDetails&manifestNumber="+ ogmNo+"&processCode="+processCode+"&updatedProcessCode="+updatedProcessCode+"&loggedInOffice="+loginOff+"&loggedInOfficeId="+loggedInOfficeId;
			window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left = 412,top = 184,scrollbars=yes');
			//showProcessing();
			jQuery.ajax({
				url : url,
				success : function(req) {
					//hideProcessing();
				}
			});
			document.inOGMDoxManifestForm.action=url;
			document.inOGMDoxManifestForm.submit();
		} else {
			alert("Please provide OGM No.");
		}*/
		if(!isNull(ogmNo)){
			url = "./inOGMDoxManifest.do?submitName=printManifestedDetails&manifestNumber="+ ogmNo+"&processCode="+processCode+"&updatedProcessCode="+updatedProcessCode+"&loggedInOffice="+loginOff+"&loggedInOfficeId="+loggedInOfficeId;
			  var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			/*window.frames['iFrame'].focus();
			window.frames['iFrame'].print();*/
			
		} else {
			alert("Please provide OGM No.");
		}
		
	}
}

function enterKeyNavForMobileNo(evt, elementIdToFocus, rowId){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	var table = document.getElementById("inOGMDoxTable");
	var newrowId = parseInt(rowId) + 1;
	var nextRowId = rowId;
	for ( var i = newrowId; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (!isNull(document.getElementById("consgNo"+ rowId))) {
			nextRowId = rowId;
			break;
		}
	}
	return onlyNumberAndEnterKeyNavFocus4Grid(evt, elementIdToFocus, nextRowId);
}

function enterKeyNavForRemarks(evt, elementIdToFocus, rowId){
	var table = document.getElementById("inOGMDoxTable");
	var newrowId = parseInt(rowId) + 1;
	var nextRowId = rowId;
	for ( var i = newrowId; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (!isNull(document.getElementById("consgNo"+ rowId))) {
			nextRowId = rowId;
			break;
		}
	}
	return enterKeyNavigationFocus4Grid(evt, elementIdToFocus, nextRowId);
}


function validateHeaderFields() {
	var isValid = true;
	var ogmNo = document.getElementById("ogmNo").value;
	var destOfficeName = document.getElementById("officeName").value;
	var region = document.getElementById("region").value;
	var destCity = document.getElementById("destCity").value;
	var destOfficeType = document.getElementById("destOfficeType").value;
	var originOffice = document.getElementById("office").value;

	var table = document.getElementById("inOGMDoxTable");

	if (isNull(ogmNo)) {
		alert("Please enter OGM Number");
		setTimeout(function() {
			document.getElementById("ogmNo").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(region)) {
		alert("Please select region");
		setTimeout(function() {
			document.getElementById("region").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(destCity)) {
		alert("Please select Origin City");
		setTimeout(function() {
			document.getElementById("destCity").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(destOfficeType)) {
		alert("Please select Office Type");
		setTimeout(function() {
			document.getElementById("destOfficeType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(originOffice)) {
		alert("Please select Origin Office");
		setTimeout(function() {
			document.getElementById("office").focus();
		}, 10);
		isValid = false;
		return isValid;
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
		//focusById(focusId);
		$("#"+focusId).focus();
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
	var consgNumbersObj = document.getElementById("consgNo" + rowId);
	var toPayAmtObj = document.getElementById("toPayAmts" + rowId);
	var codAmtObj = document.getElementById("codAmts" + rowId);
	var lcBankNamesObj = document.getElementById("lcBankNames" + rowId);
	var currentObj = null;
	if(!isNull(event)){
		currentObj = getEventTargetJQObject(event);
	}
	
	var focusId = null;
	if (isNull(consgNumbersObj.value)) {
		focusAlertMsg4TxtBox(consgNumbersObj, "Consignment Number");
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

function enableDisableAmountBankFiels(rowId){
	var consgNumbersObj = document.getElementById("consgNo" + rowId);	
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
	5. 	for others non editable
	*/
}

function validateWeight4EnterKey(evt, rowId){
	var charCode = getPressedKeyCode(evt);
	var currentObj = null;
	
	if(!isNull(evt)){
		currentObj = getEventTargetJQObject(evt);
	}
	
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		var weightKgId = "weightAW"+rowId;
		if(currentObj.attr("id") == weightKgId){
			$("#weightGm" + rowId).focus();
		}else{
			//wightGm enter Key
			validateWeight(rowId);
			validatePolicyToPayCod(rowId, evt);
		}
		return false;
	}else {
		return true;
	}
	return false;
}

function getAddedRowsCount(){
	var cnListdom=document.getElementsByName("to.consgNumbers");
	var rowsLength=null;
	if(!isNull(cnListdom)){
		rowsLength= cnListdom.length;
	}
	
	return rowsLength;
}
function isRequiredToAddRow(){
	var addedRc=parseIntNumber(getAddedRowsCount());
	MAX_CN_ALLOWED_FOR_IN_DOX =parseIntNumber(MAX_CN_ALLOWED_FOR_IN_DOX);
	
	if(addedRc >= MAX_CN_ALLOWED_FOR_IN_DOX){
		focusById('save');
		alert("Maximum CN number allowed per In-Manifest No. is :"+MAX_CN_ALLOWED_FOR_IN_DOX);
		return false;
	}
	return true;
}
