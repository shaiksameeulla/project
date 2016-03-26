var ROW_COUNT;

var maxWeightAllowed = 0;
var maxTolerenceAllowed = 0;
var isSaved = false;
var isDeleted = false;
var isAutoClose = false;
var cnWeightFromWM = 0.000;

var wmActualWeight = 0.000;
var wmCapturedWeight = 0.000;
var newWMWt = 0.000;

var bookingManifestDetail = null;

var printUrl;

/**
 * @desc to add grid rows while rendering the jsp
 */
function addRows() {
	var maxCount = parseInt(maxCNsAllowed);
	for ( var cnt = 1; cnt <= maxCount; cnt++) {

		$('#outManifestTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox" id="ischecked'
										+ cnt
										+ '" name="chkBoxName" onclick="getConsignmntIdOnCheck(this);" tabindex="-1"/>',
								cnt,
								'<input type="text" class="txtbox width160" maxlength="12" id="consigntNo'
										+ cnt
										+ '" name="to.consgNos" onkeypress="return callEnterKey(event, document.getElementById(\'consigntNo'
										+ (cnt + 1)
										+ '\'));" onblur="fnValidateConsNumber(this);validateConsignmentDetails(this,'
										+ cnt
										+ ');convertDOMObjValueToUpperCase(this);"/><input type="hidden" id="consgIds'
										+ cnt + '" name="to.consgIds" />',
								'<input type="text"  id="weight'
										+ cnt
										+ '" class="txtbox width30" readonly="readonly" tabindex="-1" /><span class="lable">.</span><input type="text"  id="weightGm'
										+ cnt
										+ '" class="txtbox width30" readonly="readonly" tabindex="-1" /><input type="hidden" id="weights'
										+ cnt + '" name="to.weights"/>',
								'<input type="text" class="txtbox width50" id="ba'
										+ cnt
										+ '" name="to.baAmounts" readonly="readonly" tabindex="-1" />',
								'<input type="text" class="txtbox width160" id="pincode'
										+ cnt
										+ '" name="to.pincodes" readonly="readonly" tabindex="-1" />\
	 	 <input type="hidden" id="consgManifestedIds'
										+ cnt
										+ '" name="to.consgManifestedIds" value="" />\
	 	 <input type="hidden" id="position'
										+ cnt
										+ '" name="to.position" value="'
										+ cnt
										+ '" />\
	 	<input type="hidden" id="oldWeights'
										+ cnt + '" name="to.oldWeights" />'

						]);
	}// end of for loop
	document.getElementById("manifestNo").focus();
}

/**
 * @desc To get Consg Id while selecting the check box
 * @param checkObj
 */
function getConsignmntIdOnCheck(checkObj) {
	var consIdListAtGrid = document.getElementById("consgIdListAtGrid").value;
	var rowId = getRowId(checkObj, "ischecked");

	if (isNull(consIdListAtGrid)) {
		var consignmtId = document.getElementById("consgIds" + rowId).value;
		document.getElementById("consgIdListAtGrid").value = consignmtId;
	} else {
		var consignmtId = consIdListAtGrid + ","
				+ document.getElementById("consgIds" + rowId).value;
		document.getElementById("consgIdListAtGrid").value = consignmtId;
	}
}

/**
 * @desc to disply message after deleting the consg record
 * @param data
 */
function printCallBackManifestDelete(data, action) {
	if (data != null && data != "") {
		if (data == "SUCCESS") {
			performSaveOrClose(action);
		} else if (data == "FAILURE") {
			alert("Exception occurred. Record not deleted successfully.");
		}
		jQuery('#outManifestTable >tbody >tr').each(function(i, tr) {
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if (isChecked) {
				getDomElementById("consigntNo" + (i + 1)).value = "";
				getDomElementById("weight" + (i + 1)).value = "";
				getDomElementById("weightGm" + (i + 1)).value = "";
				getDomElementById("pincode" + (i + 1)).value = "";
				getDomElementById("consgIds" + (i + 1)).value = "";
			}
		});
	}
}

/**
 * @desc to validate the consg no like already Manifested,booked
 * @param consgNumberObj
 * @param rowNo
 */
/*
 * function validateConsignment(consgNumberObj,rowNo) { var count =
 * getRowId(consgNumberObj, "consigntNo"); if (!isNull(consgNumberObj.value)) {
 * if (!isDuplicateScannedItem(consgNumberObj, count)){
 * validateConsignmentDetails(consgNumberObj,rowNo); } } else {
 * clearGridRow(count); } }
 */

/* to check whether a consg/manifest no is scanned again in the same grid */
/**
 * check whether grid contain any duplicate entry or not
 * 
 * @param domElement
 * @param rowId
 * @returns {Boolean}
 */
function isDuplicateScannedItem(domElement, rowId) {
	/*
	 * var isValid = true; var currentScan = consgNumberObj.value; var table =
	 * document.getElementById('outManifestTable'); var cntofR =
	 * table.rows.length; for ( var i = 1; i < cntofR; i++) {
	 * 
	 * var id = table.rows[i].cells[0].childNodes[0].id.substring(7); var
	 * prevScan = document.getElementById('consigntNo' + i).value; if
	 * (!isNull(prevScan.trim()) && !isNull(currentScan.trim())) { if (count !=
	 * id) { if (prevScan.trim() == currentScan.trim()) {
	 * alert("Consignment/Manifest Number already entered");
	 * consgNumberObj.value = ""; setTimeout(function() {
	 * consgNumberObj.focus(); }, 10); isValid = false; } } } }
	 * 
	 * return isValid;
	 */
	var consgNo = domElement.value;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (i == rowId)
			continue;
		var cnNos = getDomElementById("consigntNo" + i).value;
		if (consgNo == cnNos) {
			alert("Row contains Duplicate Consignment Number at line:" + i);
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
			return true;
		}
	}
	return false;
}

/**
 * validate the consignment number details
 * 
 * @param consgNumberObj
 * @param rowNo
 */
function validateConsignmentDetails(consgNumberObj, rowNo) {
	if (!consgNumberObj.readOnly && isValidConsignment(consgNumberObj)) {
		var loginOfficeObj = document.getElementById("loginOfficeId");
		var manifestDirectn = "O";
		var consId = getRowId(consgNumberObj, "consigntNo");
		ROW_COUNT = consId;
		loginOfficeId = loginOfficeObj.value;
		var manifestNo = getDomElementById("manifestNo").value;

		var url = './thirdPartyOutManifestDox.do?submitName=getConsignmentDtls'
				+ '&consgNumber=' + consgNumberObj.value + '&loginOfficeId='
				+ loginOfficeId + '&officeId=' + loginOfficeId + "&manifestNo="
				+ manifestNo + '&manifestDirection=' + manifestDirectn;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateConsDetails(req, consgNumberObj);
			}
		});
	}
}

/**
 * @desc to display consg details on jsp
 * @param ajaxResp
 */
function populateConsDetails(ajaxResp, consgNumberObj) {

	if (!isNull(ajaxResp)) {

		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
		} else {

			hideProcessing();
			/** For wt integratn */
			bookingDetail = responseText;
			if (isWeighingMachineConnected) {
				getWtFromWMForOGM();
			} else {
				capturedWeightForManifest(-1);
			}
			
		}

	} else {
		hideProcessing();
		alert('No details found');
	}

}

/**
 * @desc to display inManifedtdconsg details on jsp
 * @param ajaxResp
 */
function populateInManifestdConsDetails(ajaxResp) {
	bookingDetail = ajaxResp;
	if (bookingDetail != null) {
		hideProcessing();
		getWtFromWMForTPDX();
	}
}
/**
 * @desc to find the details by manifest no
 */
function searchByManifestNo() {
	if ($('#outManifestTable').dataTable().fnGetData().length < 1) {
		addRows();
	}
	// setIFrame();
	var manifestNO = document.getElementById("manifestNo").value;
	if (!isNull(manifestNO)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		url = './thirdPartyOutManifestDox.do?submitName=searchManifestDetails&manifestNo='
				+ manifestNO + "&loginOfficeId=" + loginOffceID;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateManifestDetails(req);
			}
		});
	} else {
		alert("Please provide manifest number");
	}
}

/**
 * @desc to display manifest details on jsp
 * @param ajaxResp
 */
function populateManifestDetails(ajaxResp) {

	var responseText = jsonJqueryParser(ajaxResp);
	var error = responseText[ERROR_FLAG];
	if (responseText != null && error != null) {
		hideProcessing();
		alert(error);
		document.getElementById("manifestNo").value = "";
		document.getElementById("manifestNo").focus();

	} else {

		if (!isNull(ajaxResp)) {
			var manifestDetailsTORes = eval('(' + ajaxResp + ')');
			var manifestDetailsTO = manifestDetailsTORes;
			// Header Part
			disableHeaderForSearch();
			document.getElementById("manifestDate").value = manifestDetailsTO.manifestDate;
			document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
			document.getElementById("manifestProcessId").value = manifestDetailsTO.manifestProcessTo.manifestProcessId;
			/*
			 * var regionOffice = manifestDetailsTO.destinationOfficeName +"-";
			 * var loggedInOfcName = manifestDetailsTO.loginOfficeName; var
			 * setLoginOfficeName = regionOffice.concat(loggedInOfcName);
			 * document.getElementById("loginOfficeName").value =
			 * setLoginOfficeName;
			 */
			document.getElementById("loadNo").value = manifestDetailsTO.manifestProcessTo.loadNo;
			document.getElementById("thirdPartyType").value = manifestDetailsTO.thirdPartyType;
			addOptionTODropDown("thirdPartyName",
					manifestDetailsTO.thirdPartyName,
					manifestDetailsTO.vendorId);
			document.getElementById("thirdPartyName").value = manifestDetailsTO.vendorId;
			/*
			 * if(manifestDetailsTO.thirdPartyType == "BA"){
			 * document.getElementById("thirdPartyType").value =
			 * manifestDetailsTO.thirdPartyType;
			 * clearDropDownList("thirdPartyName");
			 * addOptionTODropDown("thirdPartyName",
			 * manifestDetailsTO.businessName, manifestDetailsTO.baId);
			 * document.getElementById("thirdPartyName").value =
			 * manifestDetailsTO.baId; }else if(manifestDetailsTO.thirdPartyType ==
			 * "CC"){ document.getElementById("thirdPartyType").value =
			 * manifestDetailsTO.thirdPartyType;
			 * clearDropDownList("thirdPartyName");
			 * addOptionTODropDown("thirdPartyName",
			 * manifestDetailsTO.businessName, manifestDetailsTO.vendorId);
			 * document.getElementById("thirdPartyName").value =
			 * manifestDetailsTO.vendorId; }else
			 * if(manifestDetailsTO.thirdPartyType == "FR"){
			 * document.getElementById("thirdPartyType").value =
			 * manifestDetailsTO.thirdPartyType;
			 * clearDropDownList("thirdPartyName");
			 * addOptionTODropDown("thirdPartyName",
			 * manifestDetailsTO.businessName, manifestDetailsTO.franchiseeId);
			 * document.getElementById("thirdPartyName").value =
			 * manifestDetailsTO.franchiseeId; }
			 */
			// Set toatal Weight
			getDomElementById("finalWeight").value = manifestDetailsTO.finalWeight;
			var wt = getDomElementById("finalWeight").value;
			splitWeights(wt, "finalKgs", "finalGms", "");

			// Grid Details
			for ( var i = 0; i < manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList.length; i++) {
				disableGridRowById((i + 1));
				document.getElementById("consigntNo" + (i + 1)).value = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].consgNo;
				document.getElementById("consgIds" + (i + 1)).value = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].consgId;
				document.getElementById("consigntNo" + (i + 1)).readonly = true;
				document.getElementById("ba" + (i + 1)).value = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].baAmounts;
				document.getElementById("pincode" + (i + 1)).value = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].pincode;
				document.getElementById("consgManifestedIds" + (i + 1)).value = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].consgManifestedId;

				eachConsgWeightArr[(i + 1)] = parseFloat(manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].weight);
				var splitweightGm = manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].weight
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
				weightFormatForGm(document.getElementById("weightGm" + (i + 1)));

			}// end for loop
			if (manifestDetailsTO.manifestStatus == "C") {
				getDomElementById("manifestStatus").value = manifestDetailsTO.manifestStatus;
				disableForClose();
			}
			isSaved = true;
		} else {
			alert("Manifest number : " + getDomElementById("manifestNo").value
					+ " could not be found");
		}
		hideProcessing();
	}
	hideProcessing();
}
function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}

/**
 * @desc to disable header fields after search
 */
function disableHeaderDropDown() {
	document.getElementById("manifestNo").readOnly = true;
	document.getElementById("Find").disabled = true;// disabled search button
	document.getElementById("loadNo").disabled = true;
	document.getElementById("thirdPartyType").disabled = true;
	document.getElementById("thirdPartyName").disabled = true;
}

/**
 * @desc to enable header fields before update
 */
function enableHeaderDropDown() {
	if (document.getElementById("loadNo").disabled == true) {
		document.getElementById("loadNo").disabled = false;
	}
	if (document.getElementById("thirdPartyType").disabled == true) {
		document.getElementById("thirdPartyType").disabled = false;
	}
	if (document.getElementById("thirdPartyName").disabled == true) {
		document.getElementById("thirdPartyName").disabled = false;
	}
}

/**
 * @desc to delete consg record and clear the row
 */
function deleteConsgDtlsClientSide() {
	var subTotalWeight = 0;
	var isDel = false;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		var cnNo = getDomElementById("consigntNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true && !isNull(cnNo)) {
			// before clearing grid details getting weight
			var weightKg = document.getElementById("weight" + i).value;
			weightKg = (isNull(weightKg)) ? "0" : weightKg;
			var weightGm = document.getElementById("weightGm" + i).value;
			weightGm = (isNull(weightGm)) ? "000" : weightGm;
			if (weightGm == undefined) {
				weightGm = "000";
			} else if (weightGm.length == 1) {
				weightGm += "00";
			} else if (weightGm.length == 2) {
				weightGm += "0";
			} else
				weightGm = weightGm.substring(0, 3);

			weightInFraction = ((weightKg) + (weightGm)) / 1000;

			// Header Total Weight
			var totalWeight = document.getElementById("finalWeight").value;
			totalWeight = parseFloat(totalWeight);
			subTotalWeight = totalWeight * 1000
					- parseFloat(weightInFraction * 1000);
			subTotalWeight /= 1000;
			// Setting final wt after deduction in header and hidden Field
			document.getElementById("finalWeight").value = subTotalWeight;
			var finalWeightStr = subTotalWeight + "";
			weightKgValueFinal = finalWeightStr.split(".");
			document.getElementById("finalKgs").value = weightKgValueFinal[0];
			var weightGmValueFinal = weightKgValueFinal[1];
			if (weightKgValueFinal[1] == undefined) {
				weightGmValueFinal = "000";
			} else if (weightKgValueFinal[1].length == 1) {
				weightGmValueFinal += "00";
			} else if (weightKgValueFinal[1].length == 2) {
				weightGmValueFinal += "0";
			} else
				weightGmValueFinal = weightGmValueFinal.substring(0, 3);
			document.getElementById("finalGms").value = weightGmValueFinal;
			// setting the weight 0 in global array while deleting
			eachConsgWeightArr[i] = "0";
			// Clearing the fields
			getDomElementById("consigntNo" + i).value = "";
			getDomElementById("weight" + i).value = "";
			getDomElementById("weightGm" + i).value = "";
			getDomElementById("pincode" + i).value = "";

			// enable field for new consignment
			getDomElementById("ischecked" + i).checked = false;
			getDomElementById("consigntNo" + (i + 1)).disabled = false;
			getDomElementById("weight" + (i + 1)).disabled = false;
			getDomElementById("weightGm" + (i + 1)).disabled = false;
			getDomElementById("pincode" + (i + 1)).disabled = false;
			isSaved = false;
			isDeleted = true;
			isDel = true;
			enableGridRowById(i + 1);
		}
		if (isNull(cnNo)) {
			getDomElementById("ischecked" + i).checked = false;
		}
		getDomElementById("checkAll").checked = false;
	}
	if (isDel)
		alert("Record(s) deleted successfully.");
	else
		alert("Please select a Non Empty row to delete.");
}

/**
 * @desc Cleint side validation for header level fields
 * @returns {Boolean}
 */
function validateHeader() {
	var msg = "Please provide : ";
	var isValid = true;
	var focusObj = manifestNo;
	var manifestNo = getDomElementById("manifestNo");
	var loadNo = getDomElementById("loadNo");
	var thirdPartyType = getDomElementById("thirdPartyType");
	var thirdPartyName = getDomElementById("thirdPartyName");
	if (isNull(manifestNo.value)) {
		// alert("Please provide Manifest No.");
		// setTimeout(function(){manifestNo.focus();},10);
		// return false;
		if (isValid)
			focusObj = manifestNo;
		msg = msg + ((!isValid) ? ", " : "") + "Manifest No";
		isValid = false;
	}
	if (isNull(loadNo.value)) {
		// alert("Please select Load No.");
		// setTimeout(function(){loadNo.focus();},10);
		// return false;
		if (isValid)
			focusObj = loadNo;
		msg = msg + ((!isValid) ? ", " : "") + "Load No";
		isValid = false;
	}
	if (isNull(thirdPartyType.value)) {
		// alert("Please select Third Party Type.");
		// setTimeout(function(){thirdPartyType.focus();},10);
		// return false;
		if (isValid)
			focusObj = thirdPartyType;
		msg = msg + ((!isValid) ? ", " : "") + "Third Party Type";
		isValid = false;
	}
	if (isNull(thirdPartyName.value)) {
		// alert("Please select Third Party Name.");
		// setTimeout(function(){thirdPartyName.focus();},10);
		// return false;
		if (isValid)
			focusObj = thirdPartyName;
		msg = msg + ((!isValid) ? ", " : "") + "Third Party Name";
		isValid = false;
	}
	if (!isValid) {
		alert(msg);
		setTimeout(function() {
			focusObj.focus();
		}, 10);
	}
	return isValid;
}

/**
 * @desc to print the manifest
 * @returns {Boolean}
 */
function print() {
	if (validateHeader() && validateGridDetails()) {
		return true;
	}
}

/**
 * mandatory field checks for grid level when chkbox selected
 * 
 * @returns {Boolean}
 */
function validateGridDetails() {
	var flag = false;
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	for ( var i = 1; i < maxCNsAllowed; i++) {
		var consigntNoValue = document.getElementById("consigntNo" + i).value;
		if (!isNull(consigntNoValue)) {
			if (!validateSelectedRow(i)) {
				return false;
			}
			flag = true;
		}
	}
	if (!flag)
		alert("Please enter atleast one Consignment Number");
	return flag;
}

/**
 * @desc to save or update manifest
 * @param action
 */
/*
 * function saveOrCloseOutManifestTPDX(action) { if(action=="close") isSaved =
 * false; if(!isSaved && validateHeader() && validateGridDetails()) { var
 * manifestId = getDomElementById("manifestId").value;
 * if(isNull(manifestId)){//create new manifest
 *//** To avoid duplicate enrty into Database */
/*
 * isManifestNoSavedOrClosed(action); } else { //save or close after search
 * checkIfDeletedBeforeSave(action); } } else { if(isSaved) alert("Manifest
 * already saved."); } }
 */

/**
 * @Desc check whether manifest number is already saved or closed (To avoid
 *       duplicate entry into Database)
 * @param action
 */
function isManifestNoSavedOrClosed(action) {
	var manifestNo = getDomElementById("manifestNo").value;
	var loginOfficeId = getDomElementById("loginOfficeId").value;
	var processCode = getDomElementById("processCode").value;
	var url = "./branchOutManifestParcel.do?submitName=isManifested"
			+ "&manifestNo=" + manifestNo + "&loginOfficeId=" + loginOfficeId
			+ "&processCode=" + processCode;

	showProcessing();
	jQuery
			.ajax({
				url : url,
				success : function(req) {
					hideProcessing();
					if (req == "FAILURE") {
						alert("Manifest number already used to create another manifest.");
						return false;
					} else {
						checkIfDeletedBeforeSave(action);
					}
				}
			});
}

/**
 * @Desc check if delete before save or close
 * @param action
 */
function checkIfDeletedBeforeSave(action) {
	var manifestId = getDomElementById("manifestId").value;
	// delete the Consg/Manifest/Co-Mail before saving
	if (isDeleted && !isNull(manifestId)) {
		deleteTableRow(action);
	} else {
		performSaveOrClose(action);
	}
}

/**
 * perform save or close operation
 * 
 * @param action
 */
function saveOrCloseOutManifestTPDX(action) {
	if (action == 'close') {
		isSaved = false;
	}
	var finalKgs = document.getElementById("finalKgs").value;
	var finalGms = document.getElementById("finalGms").value;
	var finalWt = document.getElementById("finalWeight").value;
	maxRowAllowed = parseInt(getDomElementById("maxCNsAllowed").value);
	maxWeightAllowed = getDomElementById("maxWeightAllowed").value;
	maxTolerenceAllowed = getDomElementById("maxTolerenceAllowed").value;
	if (isNull(finalKgs) && isNull(finalGms)) {
		setFinalWeight(finalWt);
	}
	if (!isSaved) {
		// checks if all the row in the grid are filled
		if (isAllRowInGridFilled(maxRowAllowed)) {
			action = 'close';
		}
		// check weight tolerance during save or close
		var maxAllowedWtWithTollrence = parseInt(maxWeightAllowed)
				+ (parseInt(maxWeightAllowed) * parseInt(maxTolerenceAllowed) / 100);

		if (maxAllowedWtWithTollrence == (getDomElementById("finalWeight").value))
			action = 'close';
		else {
			for ( var i = 1; i <= maxRowAllowed; i++) {
				if (getDomElementById("weights" + i).value > maxAllowedWtWithTollrence)
					action = 'close';
			}
		}

		if (action == 'save') {
			// setTotalWt('+cnt+');
			// Open
			document.getElementById("manifestStatus").value = "O";
		} else if (action == 'close') {
			// Close
			document.getElementById("manifestStatus").value = "C";
		}
		getDomElementById("action").value = action;
		enableHeaderDropDown();
		if (validateHeader() && validateGridDetails()) {
			showProcessing();
			var url = './thirdPartyOutManifestDox.do?submitName=saveOrUpdateOutManifestTPDX';
			jQuery.ajax({
				url : url,
				data : jQuery("#thirdPartyOutManifestDoxForm").serialize(),
				success : function(req) {
					printCallBackManifestSave(req, action);
				}
			});
		}
	} else {
		alert('Data already saved');
	}
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var consgNo = getDomElementById("consigntNo" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull(consgNo.value)) {
		alert("Please provide Consignment No. to be scanned " + lineNum);
		setTimeout(function() {
			consgNo.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * @desc to display message after save or update or close
 * @param data
 * @param action
 */
function printCallBackManifestSave(ajaxResp, action) {
	var manifestDetailsTO = null;
	if (!isNull(ajaxResp)) {

		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
		} else {
			manifestDetailsTO = responseText;
			document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
			if (action == 'save') {
				alert(manifestDetailsTO.successMessage);
				$('#outManifestTable').dataTable().fnClearTable();
				searchByManifestNo();
				// disableForSave();

			} else if (action == 'close') {
				// alert(manifestDetailsTO.successMessage);
				disableForClose();
				var confirm1 = confirm("Manifest closed successfully.");
				if (confirm1) {
					searchByManifestNo();
					printThirdPartyDox();
					refreshPage();
					/*
					 * setTimeout(function(){printThirdPartyDox();}, 3000);
					 * setTimeout(function(){refreshPage();}, 4000);
					 */
				} else {
					refreshPage();
				}
			}
		}
	}
}

/**
 * @desc max weight tolerance check - total wt shld not be > 38.5
 * @param rowId
 * @returns {Boolean}
 */
function maxWtCheck(rowId) {
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	var maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;
	maxCNsAllowed = parseInt(maxCNsAllowed);
	// Calling common method for max wt chk
	if (!maxWeightToleranceCheck(rowId, maxCNsAllowed, maxWeightAllowed,
			maxTolerenceAllowed)) {
		document.getElementById("consigntNo" + rowId).value = "";
		document.getElementById("weight" + rowId).value = "";
		document.getElementById("weightGm" + rowId).value = "";
		document.getElementById("pincode" + rowId).value = "";
		/*
		 * for(var i = 0 ; i <= maxCNsAllowed ; i++){
		 * document.getElementById("consigntNo"+ (i+1)).disabled = true;
		 * document.getElementById("weight"+ (i+1)).disabled = true;
		 * document.getElementById("weightGm"+ (i+1)).disabled = true;
		 * document.getElementById("pincode"+ (i+1)).disabled = true; }
		 */
		// return false;
		eachConsgWeightArr[rowId] = 0;
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
		jQuery("#" + "cancelBtn").removeClass("btnintformbigdis");
		jQuery("#" + "cancelBtn").addClass("btnintform");
	}
	buttonEnabled("printBtn", "btnintformbigdis");
}

/**
 * @desc to disable all check boxes
 */
function disableAllCheckBox() {
	getDomElementById("checkAll").disabled = true;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		getDomElementById("ischecked" + i).disabled = true;
	}
}

/**
 * @desc to disable blank rows before save
 */
function disableForSave() {
	disableHeaderDropDown();
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById("consigntNo" + i).value))
			disableGridRowById(i);
	}
}
function enableForSave() {

	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById("consigntNo" + i).value))
			enableGridRowById(i);
	}
}
/**
 * @param domElementId
 */
function disableGridRowById(domElementId) {
	// read only text fields
	// disableElement(getDomElementById("consigntNo"+domElementId));
	// getDomElementById("consigntNo"+domElementId).disabled=true;
	var domElement = getDomElementById("consigntNo" + domElementId);
	domElement.readOnly = true;
	domElement.setAttribute("tabindex", "-1");

	// getDomElementById("weight"+domElementId).disabled=true;
	// getDomElementById("weightGm"+domElementId).disabled=true;
}
/**
 * @param domElementId
 */
function enableGridRowById(domElementId) {
	// read only text fields
	/*
	 * enableElement(getDomElementById("consigntNo"+domElementId));
	 * enableElement(getDomElementById("weight"+domElementId));
	 * enableElement(getDomElementById("weightGm"+domElementId));
	 */
	getDomElementById("consigntNo" + domElementId).disabled = false;
	getDomElementById("weight" + domElementId).disabled = false;
	getDomElementById("weightGm" + domElementId).disabled = false;
}
/**
 * @param domElement
 */
function disableElement(domElement) {
	domElement.setAttribute("readOnly", true);
	domElement.setAttribute("tabindex", "-1");
}
/**
 * @param domElement
 */
function enableElement(domElement) {
	domElement.setAttribute("readOnly", false);
	domElement.setAttribute("tabindex", "0");
}

/**
 * @param domElement
 */

function isAllRowInGridFilled(maxRowAllowed) {
	for ( var i = 1; i <= maxRowAllowed; i++) {
		if (isNull(getDomElementById("consigntNo" + i).value))
			return false;
	}
	return true;
}

/**
 * clear the row by its ID
 * 
 * @param rowId
 */
function clearGridRow(rowId) {
	getDomElementById("consigntNo" + rowId).value = "";
	getDomElementById("consgIds" + rowId).value = "";// hidden
	getDomElementById("weight" + rowId).value = "";
	getDomElementById("weightGm" + rowId).value = "";
	getDomElementById("weights" + rowId).value = "";// hidden
	getDomElementById("pincode" + rowId).value = "";
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("consigntNo" + rowId);
	return callEnterKey(e, focusId);

}

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		var url = "./thirdPartyOutManifestDox.do?submitName=viewThirdPartyOutManifestDox";
		// document.thirdPartyOutManifestDoxForm.action = url;
		// document.thirdPartyOutManifestDoxForm.submit();
		window.location = url;
	}
}

// populates details from Dtl TO for InManifestdConsignmnts
function capturedWeightForInManifestCons(weigth) {

	var weightInKg = null;
	var weightGm = null;

	wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// alert('machine weight'+wmActualWeight);
	newWMWt = wmActualWeight;

	// var ajaxResp=bookingDetail;
	var ajaxResp = eval('(' + bookingDetail + ')');

	var weight = ajaxResp.weight;

	/*
	 * if(ajaxResp!=null ) {
	 * 
	 * if(!isEmptyWeight(newWMWt) || !isEmptyWeight(ajaxResp.weight)){
	 */

	if (ajaxResp != null && (!isEmptyWeight(newWMWt) || !isEmptyWeight(weight))) {

		// var consignmentDtlsToResp = eval('(' + ajaxResp + ')');
		// var cnTO = consignmentDtlsToResp;

		// weighing Machine Integration

		var weight = parseFloat(ajaxResp.weight).toFixed(3);
		// added for updating weight
		getDomElementById("oldWeights" + ROW_COUNT).value = weight;// hidden
		// field

		if (!isNull(newWMWt) && parseFloat(newWMWt) > weight) {
			weight = newWMWt;
		}

		jQuery("#weights" + ROW_COUNT).val(weight);

		var splitWeight = weight + "";

		if (!isNull(splitWeight)) {
			weightInKg = splitWeight.split(".");
			document.getElementById("weight" + ROW_COUNT).value = (isNull(weightInKg[0])) ? "0"
					: weightInKg[0];
			weightGm = splitWeight.split(".")[1];

			if (isNull(weightGm)) {
				weightGm = "000";
				document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
			} else {
				if (weightGm == undefined) {
					weightGm = "000";
				} else if (weightGm.length == 1) {
					weightGm += "00";
				} else if (weightGm.length == 2) {
					weightGm += "0";
				} else {
					weightGm = weightGm.substring(0, 3);
				}
				document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
			}
		} else {
			document.getElementById("weight" + ROW_COUNT).value = 0;
			document.getElementById("weightGm" + ROW_COUNT).value = "000";
		}

		weightFormatForGm(getDomElementById("weightGm" + ROW_COUNT));
		jQuery("#pincode" + ROW_COUNT).val(ajaxResp.pincode);
		document.getElementById("consgIds" + ROW_COUNT).value = ajaxResp.consgId;
		isSaved = false;
		maxWtCheck(ROW_COUNT);
	} else {
		alert("Consignment cannot accepted due to zero weight.");
		clearGridRow(ROW_COUNT);
	}
	/*
	 * } else { alert("Consignment is not type: DOX"); clearGridRow(ROW_COUNT); }
	 */
	hideProcessing();

}

function printThirdPartyDox() {
	if (confirm("Do you want to Print?")) {
		var manifestNoObj = document.getElementById("manifestNo");
		var manifestStatus = getDomElementById("manifestStatus").value;

		if (manifestStatus != null && manifestStatus == "O") {
			alert("Only closed manifest can be printed.");
		} else {
			if (isNull(manifestNoObj.value)) {
				alert("Please Enter Manifest No.");
				document.getElementById("manifestNo").value = "";
				document.getElementById("manifestNo").focus();
				return false;
			}

			/*
			 * if(!isValidBplNo(manifestNoObj)){ return; }
			 */
			var manifestNo = document.getElementById("manifestNo").value;
			var processCode = getDomElementById("processCode").value;
			if (!isNull(manifestNo)) {
				var loginOffceID = document.getElementById('loginOfficeId').value;
				var url = "./thirdPartyOutManifestDox.do?submitName=printThirdPartyOutManifestDox&manifestNo="
						+ manifestNo
						+ "&loginOfficeId="
						+ loginOffceID
						+ "&processCode=" + processCode;
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

function printThirdPartyOutManifestDox() {
	var manifestNo = document.getElementById("manifestNo");
	var loginOffceID = document.getElementById('loginOfficeId').value;
	var processCode = getDomElementById("processCode").value;
	if (!isNull(manifestNo.value) && !isNull(loginOffceID)) {
		showProcessing();
		url = "./thirdPartyOutManifestDox.do?submitName=printThirdPartyOutManifestDox&manifestNo="
				+ manifestNo.value
				+ "&loginOfficeId="
				+ loginOffceID
				+ "&processCode=" + processCode;
		window
				.open(
						url,
						'_blank',
						'top=0, left=0, height=0, width=0, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no, location=no, directories=no');
		/*
		 * document.thirdPartyOutManifestDoxForm.action=url;
		 * document.thirdPartyOutManifestDoxForm.submit();
		 */
		hideProcessing();
	}
}

function cancelPage() {
	var url = "./thirdPartyOutManifestDox.do?submitName=viewThirdPartyOutManifestDox";
	window.location = url;
}

function refreshPage() {
	var url = "./thirdPartyOutManifestDox.do?submitName=viewThirdPartyOutManifestDox";
	document.thirdPartyOutManifestDoxForm.action = url;
	document.thirdPartyOutManifestDoxForm.submit();
}

// populates details from TO
function capturedWeightForManifest(weigth) {

	var weightInKg = null;
	var weightGm = null;

	wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// alert('machine weight'+wmActualWeight);
	newWMWt = wmActualWeight;

	var ajaxResp = bookingDetail;

	if (ajaxResp != null) {

		// var consignmentDtlsToResp = eval('(' + ajaxResp + ')');
		// var cnTO =
		// consignmentDtlsToResp.thirdPartyOutManifestDoxDetailsToList[0];

		if (!isEmptyWeight(newWMWt) || !isEmptyWeight(ajaxResp.weight)) {

			// weighing Machine Integration

			var weight = parseFloat(ajaxResp.weight).toFixed(3);
			// added for updating weight
			getDomElementById("oldWeights" + ROW_COUNT).value = weight;// hidden
			// field

			if (!isNull(newWMWt) && parseFloat(newWMWt) > weight) {
				weight = newWMWt;
			}

			jQuery("#weights" + ROW_COUNT).val(weight);

			var splitWeight = weight + "";

			if (!isNull(splitWeight)) {
				weightInKg = splitWeight.split(".");
				document.getElementById("weight" + ROW_COUNT).value = (isNull(weightInKg[0])) ? "0"
						: weightInKg[0];
				weightGm = splitWeight.split(".")[1];

				if (isNull(weightGm)) {
					weightGm = "000";
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				} else {
					if (weightGm == undefined) {
						weightGm = "000";
					} else if (weightGm.length == 1) {
						weightGm += "00";
					} else if (weightGm.length == 2) {
						weightGm += "0";
					} else {
						weightGm = weightGm.substring(0, 3);
					}
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				}
			} else {
				document.getElementById("weight" + ROW_COUNT).value = 0;
				document.getElementById("weightGm" + ROW_COUNT).value = "000";
			}

			weightFormatForGm(getDomElementById("weightGm" + ROW_COUNT));
			jQuery("#pincode" + ROW_COUNT).val(ajaxResp.pincode);
			jQuery("#ba" + ROW_COUNT).val(ajaxResp.baAmounts);
			document.getElementById("consgIds" + ROW_COUNT).value = ajaxResp.consgId;
			isSaved = false;
			maxWtCheck(ROW_COUNT);
		} else {
			hideProcessing();
			alert("Consignment cannot accepted due to zero weight.");
			clearGridRow(ROW_COUNT);
		}
	} else {
		hideProcessing();
		alert("Consignment is not type: DOX");
		clearGridRow(ROW_COUNT);
	}
	hideProcessing();

}

/*
 * function printDtls(){
 * 
 * if (confirm("Do you want to print?")) { // TODO implements print
 * functionality var manifestNo = getDomElementById("manifestNo").value; var
 * loginOfficeId = getDomElementById("loginOfficeId").value; if
 * (!isNull(manifestNo)) { var url =
 * './thirdPartyOutManifestDox.do?submitName=printThirdPartyOutManifestDox&manifestNo=' +
 * manifestNo + "&loginOfficeId=" + loginOfficeId; window .open( url,
 * 'newWindow',
 * 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left =
 * 412,top = 184,scrollbars=yes'); // showProcessing(); jQuery.ajax({ url : url,
 * success : function(req) { // hideProcessing(); } }); hideProcessing(); } else {
 * alert("Please provide Manifest No."); } } }
 */

function disableHeaderForSearch() {
	// read only search text field
	var manifestNo = getDomElementById("manifestNo");
	disableElement(manifestNo);
	// disable drop downs
	disableHeaderDropDown();
	// disable search button
	getDomElementById("Find").disabled = true;
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

function fnValidateConsNumber(obj) {
	if (!isNull(obj.value)) {
		var count = getRowId(obj, "consigntNo");
		if (!isDuplicateCons(obj, count)) {
			// isValidManifestNo(obj);
		}
	}
}

function isDuplicateCons(consNumberObj, count) {
	var isValid = true;
	var currentManifest = consNumberObj.value;
	var table = document.getElementById('outManifestTable');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {
		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevManifest = document.getElementById('consigntNo' + id).value;
		if (!isNull(prevManifest.trim()) && !isNull(currentManifest.trim())) {
			if (count != id) {
				if (prevManifest.trim().toUpperCase() == currentManifest.trim()
						.toUpperCase()) {
					alert("Consignment already entered");
					consNumberObj.value = "";
					setTimeout(function() {
						consNumberObj.focus();
					}, 10);
					isValid = false;
				}
			}
		}
	}
	return isValid;
}

function setIFrame() {
	var manifestNo = document.getElementById("manifestNo").value;
	var processCode = getDomElementById("processCode").value;
	if (!isNull(manifestNo)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		var url = "./thirdPartyOutManifestDox.do?submitName=printThirdPartyOutManifestDox&manifestNo="
				+ manifestNo
				+ "&loginOfficeId="
				+ loginOffceID
				+ "&processCode=" + processCode;
		printUrl = url;
		printIframe(printUrl);
	}
}

function getTPNamesForThirdPartyDox(Obj) {
	var regionId = jQuery("#regionId").val();
	if (!isNull(Obj)) {
		var url = "./thirdPartyOutManifestDox.do?submitName=getThirdPartyName&partyID="
				+ Obj + "&regionId=" + regionId;
		showProcessing();
		jQuery.ajax({
			url : url,
			type : "GET",
			dataType : "json",
			success : function(data) {
				ajaxRespForBAPartyNames(data);
			}
		});
		// ajaxCallWithoutForm(url, ajaxRespForBAPartyNames);
	} else {
		clearDropDownList("thirdPartyName");
	}
}
