var oTable;
var url;
var printUrl;

var ROW_COUNT = "";
var isSaved = false;
var isDeleted = false;
var isAutoClose = false;
var PIECES_NEW = 0;
var PIECES_OLD = 0;
var PIECES_ACTUAL = 0;
var OLD_CN = new Array();

var cnWeightFromWM = 0.000;

var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

var scanedConsignment = new Array();
var scanedAllchildCNs = new Array();

// fnEnterKeyCallOnCnNo(event,\''+ cnt + '\');

/*
 * This function is used to generate rows depending on max rows allowed in the
 * grid
 */
function addRows() {
	showProcessing();
	// var maxCount = parseInt(maxCNsAllowed);
	oTable = $('#tpBPLDataGrid').dataTable();
	for ( var cnt = 1; cnt <= maxCNsAllowed; cnt++) {

		oTable
				.fnAddData([
						'<input type="checkbox" id="ischecked'
								+ cnt
								+ '" name="chkBoxName" tabindex="-1" onclick="getScannedIdList(this);"/>',
						cnt,
						'<input type="text" class="txtbox" id="scannedGridItemNo'
								+ cnt
								+ '"  name="to.scannedGridItemNo" maxlength="12" onfocus="validateHeader();" onkeypress="return callEnterKey(event, document.getElementById(\'scannedGridItemNo'
								+ (cnt + 1)
								+ '\'));return chechConsignment(event,this);" onblur="fnValidateNumber(this,\'G\');convertDOMObjValueToUpperCase(this);" onfocus="updateChildCNNumber(childCns'
								+ cnt + ');compairChildCNWtAndCNWt();"/>',
						'<input type="text" name="to.weight" id="weight'
								+ cnt
								+ '" class="txtbox width30" size="11" tabindex="-1" readonly="readonly" /><span class="lable">.</span><input type="text" name="to.weight" id="weightGm'
								+ cnt
								+ '" class="txtbox width30" tabindex="-1" maxlength="3" readonly="readonly" size="11" /><input type="hidden" id="weights'
								+ cnt + '" name="to.weights"/>',
						'<input type="text" class="txtbox" id="noOfPieces'
								+ cnt
								+ '" name="to.noOfPcs" maxlength="3" size="3" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'scannedGridItemNo'
								+ (cnt + 1)
								+ '\');" onblur="redirectToChildCNPage('
								+ cnt
								+ ');" tabindex="-1" />\
	     <input type="hidden" name="to.isCnUpdated" id="isCnUpdated'
								+ cnt
								+ '" value="N" tabindex="-1" />\
		 <input type="hidden" name="to.childCns" id="childCns'
								+ cnt + '" value="" />',
						'<input type="text" class="txtbox" id="cnContent'
								+ cnt
								+ '" name="to.cnContent" class="txtbox width100" tabindex="-1" readonly="true"  />\
	     <input type="hidden" id="cnContentId'
								+ cnt
								+ '" name="to.cnContentIds" tabindex="-1" />',
						'<input type="text" class="txtbox" id="decValue'
								+ cnt
								+ '" name="to.declaredValues" readonly=true tabindex="-1" />',
						'<input type="text" class="txtbox" id="paperWork'
								+ cnt
								+ '" name="to.paperWorks" readonly=true tabindex="-1" />\
		 <input type="hidden" id="paperWorkId'
								+ cnt + '" name="to.paperWorkIds" />',
						'<input type="text" class="txtbox" id="toPayAmt'
								+ cnt
								+ '" name="to.toPayAmts" readonly=true tabindex="-1" size="6" />',
						'<input type="text" class="txtbox" id="codAmt'
								+ cnt
								+ '" name="to.codAmts" readonly=true tabindex="-1" size="6" />',
						'<input type="text" class="txtbox" id="ba'
								+ cnt
								+ '" name="to.baAmounts" readonly=true tabindex="-1" size="6" />',
						'<input type="text" class="txtbox" id="octroiAmt'
								+ cnt
								+ '" name="to.octroiAmts" size="6" readonly=true tabindex="-1" />',
						'<input type="text" class="txtbox" id="stateTax'
								+ cnt
								+ '" name="to.stateTaxes" size="6" readonly=true tabindex="-1" />',
						'<input type="text" class="txtbox" id="serviceCharge'
								+ cnt
								+ '" name="to.serviceCharges" size="6" readonly=true tabindex="-1" />\
	     <input type="hidden" id="consgId'
								+ cnt
								+ '"  name="to.consgIds" />\
	     <input type="hidden" id="embeddedType'
								+ cnt
								+ '" name="to.embeddedType" />\
	     <input type="hidden" id="manifestIds'
								+ cnt
								+ '" name="to.manifestIds" />\
	     <input type="hidden" id="manifestMappedEmbeddeId'
								+ cnt
								+ '" name="to.manifestMappedEmbeddeId" value="" />\
	     <input type="hidden" id="consgManifestedIds'
								+ cnt
								+ '" name="to.consgManifestedIds" value="" />\
	     <input type="hidden" id="bookingType'
								+ cnt
								+ '" name="to.bookingTypes" />\
	     <input type="hidden" id="position'
								+ cnt
								+ '" name="to.position" value="'
								+ cnt
								+ '" />\
	     <input type="hidden" id="oldWeights'
								+ cnt + '" name="to.oldWeights" />' ]);
	}// end of for loop
	hideProcessing();
	document.getElementById("manifestNo").focus();
}

/*
 * **** This function is used to identify that the scanned item is a consignment
 * or manifest depending on its length It then calls respective format
 * validation methods and gets details to fill the grid***********
 */
function getScannedGridItemNoDetails(scannedGridItemNoObj, scanLevel) {
	if (!isNull(trimString(scannedGridItemNoObj.value))) {
		var isManifestValid = false;
		var isConsigValid = false;
		var gridElementlength = scannedGridItemNoObj.value.length;
		var loginOfficeObj = document.getElementById("loginOfficeId");
		var loginOfficeId = "";
		var manifestDirectn = "O";
		var manifestNo = document.getElementById("manifestNo").value;
		if (loginOfficeObj != null)
			loginOfficeId = loginOfficeObj.value;
		var loginCityId = document.getElementById("loginCityId").value;
		var scannedId = getRowId(scannedGridItemNoObj, "scannedGridItemNo");

		ROW_COUNT = scannedId;

		// consignment type as consignment length is 12
		if (gridElementlength == 12) {
			// checks format validation and stock validation
			var domValue = scannedGridItemNoObj.value;
			var isScaned = checkedScanConsignments(domValue);
			var isInchildCN = checkInScanChildConsignments(domValue);
			if (isInchildCN == false) {
				alert("Consignment already entered as a Child CN");
				scannedGridItemNoObj.value = "";
				setTimeout(function() {
					scannedGridItemNoObj.focus();
				}, 10);
				return false;
			}
			isConsigValid = isValidConsignment(scannedGridItemNoObj);
			if (isConsigValid && isScaned && isInchildCN) {

				url = './thirdPartyBPLOutManifest.do?submitName=getConsignmentDtls'
						+ '&consignmentNo='
						+ scannedGridItemNoObj.value
						+ "&loginOfficeId="
						+ loginOfficeId
						+ "&officeId="
						+ loginOfficeId
						+ "&manifestDirection="
						+ manifestDirectn
						+ "&loginCityId="
						+ loginCityId
						+ "&manifestNo=" + manifestNo;
				showProcessing();
				jQuery.ajax({
					url : url,
					success : function(req) {
						// printCallBackTPConsignment(req,
						// scannedGridItemNoObj);
						populateConsDetails(req);
					}
				});
			}

			if (isScaned) {
				var cnRowId = parseInt(scannedId) + 1;
				var nextCn = getDomElementById("scannedGridItemNo" + cnRowId);
				if (nextCn != null) {
					nextCn.focus;
				}
			}
			if (isNull(scannedGridItemNoObj.value)) {
				var cnList = document.getElementsByName("to.scannedGridItemNo");
				scanedConsignment = new Array();
				for ( var i = 0; i < cnList.length; i++) {
					if (!isNull(cnList[i])) {
						scanedConsignment[scanedConsignment.length] = cnList[i];
					}
				}
				clearGridDetails(scannedId);
			}

			// jQuery("#embeddedType" + ROW_COUNT).val('C');
		} else if (gridElementlength == 10) {// manifest type as
			// consignment
			// length is 12

			jQuery('#seriesType').val(OGM_SERIES);
			isManifestValid = isValidManifestNo(scannedGridItemNoObj, scanLevel);
			// checks format validation and stock validation
			if (isManifestValid) {
				getManifestDtls(scannedGridItemNoObj, scanLevel);
			} else {
				return false;
			}
			// jQuery("#embeddedType" + ROW_COUNT).val('M');
		} else {
			alert("Invalid Consignment or Manifest Number");
			scannedGridItemNoObj.value = "";
			setTimeout(function() {
				scannedGridItemNoObj.focus();
			}, 10);
		}
	}
}

/*
 * ***This function checks for any validation violation of consignment and
 * displays appropriate error alerts****
 */
/*
 * function printCallBackTPConsignment(data, consgNumberObj) { var response =
 * data; if (response != null) { var cnValidation = eval('(' + response + ')');
 * if (cnValidation != null && cnValidation != "") { if
 * (cnValidation.isConsgExists == "N") { alert(cnValidation.errorMsg);
 * consgNumberObj.value = ""; setTimeout(function() { consgNumberObj.focus(); },
 * 10); hideProcessing(); } else if (cnValidation.isCnManifested == "Y") {
 * alert(cnValidation.errorMsg); consgNumberObj.value = "";
 * setTimeout(function() { consgNumberObj.focus(); }, 10); hideProcessing(); }
 * else if (cnValidation.isPincodeServiceable == "N") {
 * alert(cnValidation.errorMsg); consgNumberObj.value = "";
 * setTimeout(function() { consgNumberObj.focus(); }, 10); hideProcessing(); }
 * else if (cnValidation.isConsParcel == "N") { alert("Only parcel type
 * consignments are allowed"); consgNumberObj.value = ""; setTimeout(function() {
 * consgNumberObj.focus(); }, 10); hideProcessing(); } else if
 * (cnValidation.isConsInManifestd == "Y" && cnValidation.isCNBooked == "N") {
 * getInManifestConsDtls(consgNumberObj); } else { // to get the details of
 * consignment getTPConsignmentDetails(consgNumberObj); } } } //
 * jQuery.unblockUI(); }
 */

/* **To get the consignment details ******** */
/*
 * function getTPConsignmentDetails(consObj) { var consNo = consObj.value; var
 * consId = getRowId(consObj, "scannedGridItemNo"); ROW_COUNT = consId;
 * 
 * url =
 * './thirdPartyBPLOutManifest.do?submitName=getConsignmentDtls&consignmentNo=' +
 * consNo; ajaxCallWithoutForm(url, populateConsDetails); }
 */

/* **To get the inManifestdconsignment details ******** */
/*
 * function getInManifestConsDtls(consObj) { var consNo = consObj.value; var
 * consId = getRowId(consObj, "scannedGridItemNo"); ROW_COUNT = consId;
 * 
 * url =
 * './thirdPartyBPLOutManifest.do?submitName=getInManifestConsignmentDtls&consignmentNo=' +
 * consNo; ajaxCallWithoutForm(url, populateConsDetails); }
 */

/**
 * capturedWeightForManifest:Added this function for reverse weight capture
 * logic also populates consg details.
 * 
 * @param data
 * @param rowId
 * @author amimehta
 */
function capturedWeightForManifest(weigth) {
	// alert('>>>wmCapturedWeight>>>'+wmCapturedWeight);
	var weightInKg = null;
	var weightGm = null;

	wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// alert('machine weight'+wmActualWeight);
	newWMWt = wmActualWeight;

	var response = bookingDetail;
	if (response != null
			&& (!isEmptyWeight(newWMWt) || !isEmptyWeight(response.weight))) {

		var consgNo = document.getElementById("scannedGridItemNo" + ROW_COUNT).value;
		clearGridDetails(ROW_COUNT);

		// For Consg. NoOfPcs - Enable & For Manifest. NoOfPcs - Disable
		getDomElementById("noOfPieces" + ROW_COUNT).readOnly = false;

		document.getElementById("scannedGridItemNo" + ROW_COUNT).value = consgNo;
		OLD_CN[ROW_COUNT] = consgNo;
		scanedConsignment[scanedConsignment.length] = consgNo;

		jQuery("#noOfPieces" + ROW_COUNT).val(response.noOfPcs);
		PIECES_OLD = getDomElementById("noOfPieces" + ROW_COUNT).value;
		PIECES_ACTUAL = getDomElementById("noOfPieces" + ROW_COUNT).value;
		jQuery("#childCns" + ROW_COUNT).val(response.childCn);
		jQuery("#isCnUpdated" + ROW_COUNT).val("N");
		jQuery("#bookingType" + ROW_COUNT).val(response.bookingType);

		jQuery("#cnContent" + ROW_COUNT).val(response.cnContent);
		// jQuery("#cnContentId" + ROW_COUNT).val(response.cnContentId);
		jQuery("#decValue" + ROW_COUNT).val(response.declaredValues);
		jQuery("#paperWork" + ROW_COUNT).val(response.paperWork);
		// jQuery("#paperWorkId" + ROW_COUNT).val(response.paperWorkId);
		jQuery("#toPayAmt" + ROW_COUNT).val(response.toPayAmts);
		jQuery("#codAmt" + ROW_COUNT).val(response.codAmts);
		jQuery("#ba" + ROW_COUNT).val(response.baAmounts);
		jQuery("#octroiAmt" + ROW_COUNT).val(response.octroiAmts);
		jQuery("#stateTax" + ROW_COUNT).val(response.stateTaxes);
		jQuery("#serviceCharge" + ROW_COUNT).val(response.serviceCharges);
		// jQuery("#consgId" + ROW_COUNT).val(response.consgId);
		// jQuery("#pincode" + ROW_COUNT).val(response.pincode);
		// jQuery("#pincodeId" + ROW_COUNT).val(response.pincodeId);

		// weighing Machine Integration

		var weight = parseFloat(response.weight).toFixed(3);
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
		isSaved = false;

		if (maxWtCheck(ROW_COUNT)) {
			jQuery("#embeddedType" + ROW_COUNT).val(response.embeddedType);
		}
	} else {
		alert("Consignment cannot accepted due to zero weight");
		var domElement = getDomElementById("scannedGridItemNo" + ROW_COUNT);
		domElement.value = "";
		setTimeout(function() {
			domElement.focus();
		}, 10);
	}
	hideProcessing();
}

/* **To display the consignment details ******** */
function populateConsDetails(data) {
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			consgNoObj = getDomElementById("scannedGridItemNo" + ROW_COUNT);
			consgNoObj.value = "";
			setTimeout(function() {
				consgNoObj.focus();
			}, 10);
			hideProcessing();
		} else {
			// hideProcessing();
			/** For wt integratn */
			bookingDetail = responseText;
			getWtFromWMForOGM();
		}
	} else {
		hideProcessing();
		alert('No details found');
	}
}

/**
 * To check weight tolerance
 * 
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
		removeScanConsignments(document.getElementById("scannedGridItemNo"
				+ rowId).value);
		document.getElementById("scannedGridItemNo" + rowId).value = "";
		document.getElementById("weight" + rowId).value = "";
		document.getElementById("weightGm" + rowId).value = "";

		jQuery("#noOfPieces" + rowId).val("");
		jQuery("#childCns" + rowId).val("");
		jQuery("#isCnUpdated" + rowId).val("");
		jQuery("#bookingType" + rowId).val("");

		jQuery("#cnContent" + rowId).val("");
		jQuery("#cnContentId" + rowId).val("");
		jQuery("#decValue" + rowId).val("");
		jQuery("#paperWork" + rowId).val("");
		jQuery("#paperWorkId" + rowId).val("");
		jQuery("#toPayAmt" + rowId).val("");
		jQuery("#codAmt" + rowId).val("");
		jQuery("#octroiAmt" + rowId).val("");
		jQuery("#stateTax" + rowId).val("");
		jQuery("#serviceCharge" + rowId).val("");
		jQuery("#consgId" + rowId).val("");
		jQuery("#pincode" + rowId).val("");
		jQuery("#pincodeId" + rowId).val("");
		eachConsgWeightArr[rowId] = 0;
		setTimeout(function() {
			document.getElementById("scannedGridItemNo" + rowId).focus();
		}, 10);
		return false;
	}
	setFinalWeight(document.getElementById("finalWeight").value);
	return true;
}

/**
 * To get manifest details in grid
 * 
 * @param manifestNoObj
 * @param scanLevel
 */
function getManifestDtls(manifestNoObj, scanLevel) {
	manifestNoObj.value = $.trim(manifestNoObj.value);
	var loginOfficeId = getDomElementById("loginOfficeId").value;
	var headerManifestNo = getDomElementById("manifestNo").value;
	var thirdPartyType = getDomElementById("thirdPartyType").value;
	var thirdPartyName = getDomElementById("thirdPartyName").value;
	var url = "./thirdPartyBPLOutManifest.do?submitName=getManifestDtlsByProcess"
			+ "&manifestNo="
			+ manifestNoObj.value
			+ "&loginOfficeId="
			+ loginOfficeId
			+ "&headerManifestNo="
			+ headerManifestNo
			+ "&thirdPartyType="
			+ thirdPartyType
			+ "&thirdPartyName="
			+ thirdPartyName;
	showProcessing();
	$.ajax({
		url : url,
		// type : "POST",
		// data : jQuery("#thirdPartyBPLOutManifestForm").serialize(),
		success : function(req) {
			// hideProcessing();
			populateManifest(req, manifestNoObj, scanLevel);
		}
	});
}
/* ****if no details are retained from db then user can enter weight***** */
function populateManifest(req, manifestNoObj, scanLevel) {
	if (!isNull(req)) {
		var responseText = jsonJqueryParser(req);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			hideProcessing();
		} else {
			var data = eval('(' + req + ')');
			var rowId = getRowId(manifestNoObj, "scannedGridItemNo");
			fillManifestDetails(data, rowId);
		}
	} else {
		alert('No Details Found');
		manifestNoObj.value = "";
		setTimeout(function() {
			manifestNoObj.focus();
		}, 10);
		hideProcessing();
	}
}

/**
 * To populate grid level manifest details
 * 
 * @param manifestTO
 * @param rowId
 */
function fillManifestDetails(manifestTO, rowId) {
	ROW_ID = rowId;
	bookingManifestDetail = manifestTO;
	getWtFromWMForManifest();

}

/**
 * @Desc actual perform save or close operation
 * @param action
 */
function saveOrCloseOutManifestTPBP(action) {
	isAutoClose = false;
	// form
	if (isAllRowInGridFilled(maxCNsAllowed)) {
		action = 'close';
		isAutoClose = true;
	}

	// check weight tolerance during save or close
	var maxWeightAllowed = getDomElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = getDomElementById("maxTolerenceAllowed").value;
	var maxAllowedWtWithTollrence = parseFloat(maxWeightAllowed)
			+ (parseFloat(maxWeightAllowed) * parseFloat(maxTolerenceAllowed) / 100);
	if (maxAllowedWtWithTollrence == (getDomElementById("finalWeight").value)) {
		action = 'close';
		isAutoClose = true;
	} else if (getDomElementById("weights" + 1).value > maxAllowedWtWithTollrence) {
		// If first row exceeds weight limit
		action = 'close';
		isAutoClose = true;
	}
	if (action == 'save') { // Open
		document.getElementById("manifestStatus").value = "O";
	} else if (action == 'close') {// Close
		document.getElementById("manifestStatus").value = "C";
	}
	if (validateHeader() && validateGridDetails()) {
		enableHeaderDropDown();// enables the drop down values to be retrieved
		// from
		showProcessing();
		var url = './thirdPartyBPLOutManifest.do?submitName=saveOrUpdateOutManifestTPBP';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#thirdPartyBPLOutManifestForm").serialize(),
			success : function(req) {
				printCallBackTPManifestSave(req, action);
			}
		});
	}
}
// call back method for saveOrCloseOutManifestTPBP
function printCallBackTPManifestSave(data, action) {
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			var manifestId = getDomElementById("manifestId").value;
			alert(error);
			if (!isNull(manifestId)) {
				disableHeader();
			}
			hideProcessing();
		} else if (responseText != null) {
			alert(responseText.successMessage);
			// $('#tpBPLDataGrid').dataTable().fnClearTable();
			if (responseText.manifestStatus == "O") {
				searchManifest();
			} else if (responseText.manifestStatus == "C") {
				// var confirm1 = confirm("Manifest closed successfully.");
				// if (confirm1) {
				searchManifest();
				printThirdParty();
				refreshPage();
				/*
				 * setTimeout(function() { printThirdParty(); }, 3000);
				 * setTimeout(function() { refreshPage(); }, 4000);
				 */
				// } else {
				// refreshPage();
				// }
				disableForClose();
			}
		}
	} else {
		alert("Manifest Not Saved.");
	}
	hideProcessing();
}

/* @Desc:Disabling the elements after closing the mbpl */
function disableForClose() {
	var manifestStatus = document.getElementById("manifestStatus").value;
	if (manifestStatus == "C") {
		disableAllCheckBox();
		disableAll();
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
	// buttonEnabled("printBtn","btnintformbigdis");
}

/**
 * TO disabled all check box
 */
function disableAllCheckBox() {
	getDomElementById("checkAll").disabled = true;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		getDomElementById("ischecked" + i).disabled = true;
	}
}
/* ******mandatory field checks for header level**** */
function validateHeader() {
	var scannedGridItemNo = document.getElementById("manifestNo");
	var loadNo = document.getElementById("loadNo");
	var thirdPartyType = document.getElementById("thirdPartyType");
	var thirdPrtyName = document.getElementById("thirdPartyName");
	var baglockNo = document.getElementById("bagLockNo");
	var missingFields = "Please provide : ";
	var focusObj = scannedGridItemNo;
	var isMissed = true;
	if (isNull(scannedGridItemNo.value)) {
		if (isMissed)
			focusObj = scannedGridItemNo;
		missingFields = missingFields + ((!isMissed) ? ", " : "")
				+ "Manifest No";
		isMissed = false;
	}
	if (isNull(loadNo.value)) {
		if (isMissed)
			focusObj = loadNo;
		missingFields = missingFields + ((!isMissed) ? ", " : "") + "Load No";
		isMissed = false;
	}
	if (isNull(thirdPartyType.value)) {
		if (isMissed)
			focusObj = thirdPartyType;
		missingFields = missingFields + ((!isMissed) ? ", " : "")
				+ "Third Party Type";
		isMissed = false;
	}
	if (isNull(thirdPrtyName.value)) {
		if (isMissed)
			focusObj = thirdPrtyName;
		missingFields = missingFields + ((!isMissed) ? ", " : "")
				+ "Third Party Name";
		isMissed = false;
	}
	
	  if (isNull(baglockNo.value)) {
	   	if (isMissed)
	    focusObj = baglockNo;
	 	 missingFields = missingFields + ((!isMissed) ? ", " : "") + "Bag LockNo."; 
	 	 isMissed = false; 
	  }
	 
	if (!isMissed) {
		alert(missingFields);
		setTimeout(function() {
			focusObj.focus();
		}, 10);
	}
	return isMissed;
}

/* ******mandatory field checks for grid level when chkbox selected **** */
function validateGridDetails() {
	var flag = false;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById("scannedGridItemNo" + i).value)) {
			if (!validateSelectedTPRow(i)) {
				return false;
			}
			flag = true;
		}
	}
	if (!flag)
		alert("Please enter atleast one Consignment / Manifest");
	return flag;
}

/* ******checks if consg/manifest no is entered at grid level**** */
function validateSelectedTPRow(rowId) {

	var scannedNoObj = getDomElementById("scannedGridItemNo" + rowId);
	var noOfPcsObj = getDomElementById("noOfPieces" + rowId);
	var childCnsObj = getDomElementById("childCns" + rowId);
	var lineNum = " at line : " + rowId;

	if (isNull(scannedNoObj.value)) {
		alert("Please provide No. to be scanned" + lineNum);
		setTimeout(function() {
			scannedNoObj.focus();
		}, 10);
		return false;
	}
	if (!noOfPcsObj.readOnly) {
		if (isNull(noOfPcsObj.value) || noOfPcsObj.value == 0
				|| noOfPcsObj.value == "0") {
			alert("Please provide no. of pieces" + lineNum);
			setTimeout(function() {
				noOfPcsObj.focus();
			}, 10);
			return false;
		}
		if (noOfPcsObj.value > 1 && isNull(childCnsObj.value)) {
			alert("Please enter child consignment number for"
					+ scannedNoObj.value + lineNum);
			setTimeout(function() {
				noOfPcsObj.focus();
			}, 10);
			return false;
		}
	}
	return true;
}

/* ******prepare list of items scanned on chkbox selection **** */
function getScannedIdList(checkObj) {
	var manifestIdListAtGrid = document.getElementById("manifestIdListAtGrid").value;
	var consIdListAtGrid = document.getElementById("consgIdListAtGrid").value;
	var rowId = getRowId(checkObj, "ischecked");
	var scannedNo = document.getElementById("scannedGridItemNo" + rowId).value;
	if (scannedNo.length == 10) {// Manifest
		if (isNull(manifestIdListAtGrid)) {
			var manifestId = document.getElementById("manifestIds" + rowId).value;
			document.getElementById("manifestIdListAtGrid").value = manifestId;
		} else {
			var manifestId = manifestIdListAtGrid + ","
					+ document.getElementById("manifestIds" + rowId).value;
			document.getElementById("manifestIdListAtGrid").value = manifestId;
		}
		// jQuery("#embeddedType" + rowId).val('M');
	} else if (scannedNo.length == 12) {// Consignment
		if (isNull(consIdListAtGrid)) {
			var consignmtId = document.getElementById("consgId" + rowId).value;
			document.getElementById("consgIdListAtGrid").value = consignmtId;
		} else {
			var consignmtId = consIdListAtGrid + ","
					+ document.getElementById("consgId" + rowId).value;
			document.getElementById("consgIdListAtGrid").value = consignmtId;
		}
		// jQuery("#embeddedType" + rowId).val('C');
	}
}

/*
 * This function is used to search a manifest which is already created from same
 * office using third party BPL screen
 */
function searchManifest() {
	/*
	 * if ($('#tpBPLDataGrid').dataTable().fnGetData().length < 1) { addRows(); }
	 */
	// setIFrame();
	var manifestNO = document.getElementById("manifestNo").value;
	if (!isNull(manifestNO)) {
		var loginOffceID = document.getElementById('loginOfficeId').value;
		url = './thirdPartyBPLOutManifest.do?submitName=searchManifestDetails&manifestNo='
				+ manifestNO + "&loginOfficeId=" + loginOffceID;
		showProcessing();
		// Clear existing data in the grid
		for ( var count = 1; count <= maxCNsAllowed; count++) {
			clearGridDetails(count);
		}
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestDetails(req);
			}
		});
	} else {
		alert("Please Provide Manifest Number");
	}
}
// call back method for searchManifest function
function printCallBackManifestDetails(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			hideProcessing();
			document.getElementById("manifestNo").value = "";
			document.getElementById("manifestNo").focus();
		} else {
			/* All the consignment/manifest are fetched */
			var data = eval('(' + ajaxResp + ')');
			populateHeaderDetails(data);
			// grid details : setting those values to screen...
			for ( var i = 0; i < data.thirdPartyBPLDetailsListTO.length; i++) {
				var listObj = data.thirdPartyBPLDetailsListTO[i];
				var ROW_ID = i + 1;
				if (listObj.embeddedType == "C") { // Consignment
					getDomElementById("scannedGridItemNo" + ROW_ID).value = listObj.consgNo;
					if (!isNull(listObj.consgNo)) {
						scanedConsignment[scanedConsignment.length] = listObj.consgNo;
					}
					OLD_CN[ROW_ID] = listObj.consgNo;
					getDomElementById("scannedGridItemNo" + ROW_ID).readOnly = true;
					getDomElementById("weights" + ROW_ID).value = listObj.weight; // Hidden
					var wt = getDomElementById("weights" + ROW_ID).value + "";
					var wts = wt.split(".");
					getDomElementById("weight" + ROW_ID).value = (isNull(wts[0])) ? "0"
							: wts[0]; // Kgs
					getDomElementById("weightGm" + ROW_ID).value = (isNull(wts[1])) ? "0"
							: wts[1]; // Gms
					weightFormatForGm(getDomElementById("weightGm" + ROW_ID));
					getDomElementById("noOfPieces" + ROW_ID).value = listObj.noOfPcs;
					getDomElementById("childCns" + ROW_ID).value = listObj.childCn;

					/*
					 * Add Child CN in Global array. FOR child CN should not be
					 * allowd to scan.
					 */
					addChildCNInArray(listObj.childCn);

					getDomElementById("cnContent" + ROW_ID).value = listObj.cnContent;
					getDomElementById("decValue" + ROW_ID).value = listObj.declaredValues;
					getDomElementById("paperWork" + ROW_ID).value = listObj.paperWork;
					getDomElementById("toPayAmt" + ROW_ID).value = listObj.toPayAmts;
					getDomElementById("codAmt" + ROW_ID).value = listObj.codAmts;
					getDomElementById("ba" + ROW_ID).value = listObj.baAmounts;
					getDomElementById("octroiAmt" + ROW_ID).value = listObj.octroiAmts;
					getDomElementById("stateTax" + ROW_ID).value = listObj.stateTaxes;
					getDomElementById("serviceCharge" + ROW_ID).value = listObj.serviceCharges;
					getDomElementById("embeddedType" + ROW_ID).value = listObj.embeddedType;
				} else if (listObj.embeddedType == "M") {// Manifest
					getDomElementById("scannedGridItemNo" + ROW_ID).value = listObj.manifestNo;
					getDomElementById("scannedGridItemNo" + ROW_ID).readOnly = true;
					getDomElementById("weights" + ROW_ID).value = listObj.weight; // Hidden
					var wt = getDomElementById("weights" + ROW_ID).value + "";
					var wts = wt.split(".");
					getDomElementById("weight" + ROW_ID).value = (isNull(wts[0])) ? "0"
							: wts[0]; // Kgs
					getDomElementById("weightGm" + ROW_ID).value = (isNull(wts[1])) ? "0"
							: wts[1]; // Gms
					weightFormatForGm(getDomElementById("weightGm" + ROW_ID));
					getDomElementById("noOfPieces" + ROW_ID).readOnly = true;
					getDomElementById("embeddedType" + ROW_ID).value = listObj.embeddedType;
				}
				eachConsgWeightArr[ROW_ID] = parseFloat(getDomElementById("weights"
						+ ROW_ID).value);
				isSaved = true;
			}
			/* Set manifest status */
			getDomElementById("manifestStatus").value = data.manifestStatus;

			disableHeader();
			if (data.manifestStatus == "C") {
				disableForClose();
			}
		}

	} else {
		alert('Invalid Manifest Number');
	}
	hideProcessing();
}

function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}

/* clears the drop down values */
/*
 * function clearDropDownList(selectId) {
 * getDomElementById(selectId).options.length = 0; addOptionTODropDown(selectId,
 * selectOption, ""); }
 */
/* adds values to the drop down */
/*
 * function addOptionTODropDown(selectId, label, value) { var obj =
 * getDomElementById(selectId); opt = document.createElement('OPTION');
 * opt.value = value; opt.text = label; obj.options.add(opt); }
 */

/* used for client side deletion means just clearing the selected row */
function deleteTPDtlsClientSide() {
	var isDel = false;
	var subTotalWeight = 0.000;
	// var manifestId = document.getElementById("manifestId");
	// if(isNull(manifestId.value)){
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		var scanNo = getDomElementById("scannedGridItemNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true
				&& !isNull(scanNo)) {

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
			var weightInFraction = parseFloat(weightKg) + parseFloat(weightGm)
					/ 1000;
			// Header Total Weight
			var totalWeight = document.getElementById("finalWeight").value;
			totalWeight = parseFloat(totalWeight);
			subTotalWeight = totalWeight * 1000
					- parseFloat(weightInFraction * 1000);
			subTotalWeight /= 1000;
			// Setting final wt after deduction in header and hidden Field
			document.getElementById("finalWeight").value = parseFloat(subTotalWeight);
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
			eachConsgWeightArr[i] = 0;
			clearGridDetails(i);
			enableGridRowById(i);
			isDeleted = true;
			isSaved = false;
			isDel = true;
			removeScanConsignments(scanNo);
		}
		if (isNull(scanNo)) {
			getDomElementById("ischecked" + i).checked = false;
		}
	}
	getDomElementById("checkAll").checked = false;
	// }
	if (isDel)
		alert("Record(s) deleted successfully.");
	else
		alert("Please select a Non Empty row to delete.");
}

/**
 * To calculate final weight
 * 
 * @param rowId
 */
function calcFinalWeight(rowId) {
	var subTotalWeight = 0.000;
	// before clearing grid details getting weight
	var weightKg = document.getElementById("weight" + rowId).value;
	weightKg = (isNull(weightKg)) ? "0" : weightKg;
	var weightGm = document.getElementById("weightGm" + rowId).value;
	weightGm = (isNull(weightGm)) ? "000" : weightGm;
	if (weightGm == undefined) {
		weightGm = "000";
	} else if (weightGm.length == 1) {
		weightGm += "00";
	} else if (weightGm.length == 2) {
		weightGm += "0";
	} else
		weightGm = weightGm.substring(0, 3);
	var weightInFraction = parseFloat(weightKg) + parseFloat(weightGm) / 1000;
	// Header Total Weight
	var totalWeight = document.getElementById("finalWeight").value;
	totalWeight = (!isNull(totalWeight)) ? parseFloat(totalWeight) : 0;
	subTotalWeight = totalWeight * 1000 - parseFloat(weightInFraction * 1000);
	subTotalWeight /= 1000;
	// Setting final wt after deduction in header and hidden Field
	document.getElementById("finalWeight").value = parseFloat(subTotalWeight);
	var finalWeightStr = subTotalWeight + "";
	var weightKgValueFinal = finalWeightStr.split(".");
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
}

/*
 * display appropriate alerts as per values returned after deletion and clear
 * the seelcted rows
 */
/**
 * @Desc ajax response for delete rows from database
 * @param ajaxResp
 */
function printCallBackManifestDelete(ajaxResp, action) {
	var data = ajaxResp;
	showProcessing();
	if (!isNull(data)) {
		if (data == "SUCCESS") {
			// alert("Record deleted successfully.");
			performSaveOrClose(action);
		} else if (data == "FAILURE") {
			// alert("Exception occurred. Record not deleted successfully.");
			alert("Exception occurred. Record could not be saved.");
		}
	}
	hideProcessing();
}
/*
 * function printCallBackManifestDelete(data){ if(data !=null && data!=""){
 * if(data =="SUCCESS"){ alert("Record deleted successfully."); } else if(data
 * =="FAILURE"){ alert("Exception occurred. Record not deleted successfully."); }
 * jQuery('#tpBPLDataGrid >tbody >tr').each(function(i, tr) { var isChecked =
 * jQuery(this).find('input:checkbox').is(':checked'); if(isChecked) {
 * clearGridDetails(i+1); } }); } }
 */

/* tests if total weight of grid exceeds the maximum weight allowed + tollerance */
function maxWeightCheck(rowId) {
	var maxRowsAllowed = parseInt(maxCNsAllowed);
	maxWeightToleranceCheck(rowId, maxRowsAllowed, maxWeightAllowed,
			maxTolerenceAllowed);/*
									 * if(!maxWeightToleranceCheck(rowId,maxCNsAllowed,maxWeightAllowed,maxTolerenceAllowed)){
									 * for(var i = 0 ; i <= maxCNsAllowed ;
									 * i++){
									 * document.getElementById("scannedGridItemNo"+
									 * (i+1)).disabled = true;
									 * document.getElementById("weight"+
									 * (i+1)).disabled = true;
									 * document.getElementById("weightGm"+
									 * (i+1)).disabled = true;
									 * //document.getElementById("pincode"+
									 * (i+1)).disabled = true; } return false; }
									 */
}
// disabled for save/close/search
function disableForSearch(rowId) {
	disableHeader();
	disableElement(getDomElementById("scannedGridItemNo" + rowId));
	disableElement(getDomElementById("weight" + rowId));
	disableElement(getDomElementById("weightGm" + rowId));
	disableElement(getDomElementById("noOfPieces" + rowId));
}

/**
 * To disabled elements
 * 
 * @param domElement
 */
function disableElement(domElement) {
	domElement.setAttribute("readOnly", true);
	domElement.setAttribute("tabindex", "-1");
}

/**
 * To disable header
 */
function disableHeader() {
	// disabled drop down
	getDomElementById("loadNo").disabled = true;
	getDomElementById("thirdPartyType").disabled = true;
	getDomElementById("thirdPartyName").disabled = true;

	// disabled search textfield, Bag lock No. text flied
	disableElement(getDomElementById("bagLockNo"));
	disableElement(getDomElementById("manifestNo"));

	// disable search button
	getDomElementById("Search").disabled = true;
}

/* to clear a grid row */
function clearGridDetails(domElementId) {
	removeScanConsignments(getDomElementById("scannedGridItemNo" + domElementId).value);
	OLD_CN[domElementId] = "";
	eachConsgWeightArr[domElementId] = 0;
	getDomElementById("scannedGridItemNo" + domElementId).value = "";
	getDomElementById("scannedGridItemNo" + domElementId).readOnly = false;
	getDomElementById("weight" + domElementId).value = "";
	getDomElementById("weightGm" + domElementId).value = "";
	// getDomElementById("pincode"+domElementId).value = "";
	getDomElementById("consgId" + domElementId).value = "";// hidden field
	getDomElementById("manifestIds" + domElementId).value = "";// hidden field
	// getDomElementById("consgNo"+domElementId).value="";

	getDomElementById("noOfPieces" + domElementId).value = "";
	getDomElementById("noOfPieces" + domElementId).readOnly = false;
	getDomElementById("childCns" + domElementId).value = "";

	getDomElementById("weights" + domElementId).value = "";// hidden field
	getDomElementById("cnContent" + domElementId).value = "";
	getDomElementById("cnContentId" + domElementId).value = "";
	getDomElementById("decValue" + domElementId).value = "";
	getDomElementById("paperWork" + domElementId).value = "";
	// getDomElementById("paperWorkId"+domElementId).value="";
	getDomElementById("toPayAmt" + domElementId).value = "";
	getDomElementById("codAmt" + domElementId).value = "";
	getDomElementById("octroiAmt" + domElementId).value = "";
	getDomElementById("serviceCharge" + domElementId).value = "";
	getDomElementById("stateTax" + domElementId).value = "";
	document.getElementById("ischecked" + (domElementId)).checked = false;
	getDomElementById("embeddedType" + domElementId).value = "";
}

/* called on blur of header level manifest no */
function fnValidateNumber(obj, scanLevel) {
	var count = getRowId(obj, "scannedGridItemNo");
	if (!obj.readOnly) {
		if (!isNull(obj.value)) {
			if (isDuplicateScannedItem(obj, count)) {
				getScannedGridItemNoDetails(obj, scanLevel);
			}
		} else {
			calcFinalWeight(count);
			removeScanConsignments(OLD_CN[count]);
			// removeFromArray(OLD_CN[count]);
			clearGridDetails(count);
		}
	}
}

/* to check whether a consg/manifest no is scanned again in the same grid */
function isDuplicateScannedItem(scannedGridItemNoObj, count) {
	var isValid = true;
	var currentScan = scannedGridItemNoObj.value;
	var table = document.getElementById('tpBPLDataGrid');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {
		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevScan = document.getElementById('scannedGridItemNo' + id).value;
		if (!isNull(trimString(prevScan)) && !isNull(trimString(currentScan))) {
			if (count != id) {
				if (trimString(prevScan).toUpperCase() == trimString(
						currentScan).toUpperCase()) {
					alert("Consignment/Manifest Number already entered");
					scannedGridItemNoObj.value = "";
					setTimeout(function() {
						scannedGridItemNoObj.focus();
					}, 10);
					isValid = false;
				}
			}
		}
	}
	return isValid;
}

/* enable header drop downs */
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
 * To validate header manifest number
 * 
 * @param manifestNoObj
 */
function validateHeaderManifestNo(manifestNoObj) {
	var scanLevel = 'H';
	// var clkOnSearch = 'N';
	// var validStock =
	isValidManifestNo(manifestNoObj, scanLevel);
	/*
	 * if(validStock) { searchManifest(clkOnSearch);}
	 */

}

/**
 * To populate Third Party drop downs in header
 * 
 * @param manifestTO
 */
function populateTPDropDown(manifestTO) {

	// populate the drop down...

	// getDomElementById("thirdPartyType").value =
	// manifestTO.manifestProcessTo.thirdPartyType;
	getDomElementById("thirdPartyType").value = manifestTO.thirdPartyType;
	// clearDropDownList("thirdPartyName");
	// addOptionTODropDown("thirdPartyName",
	// manifestTO.manifestProcessTo.businessName,
	// manifestTO.manifestProcessTo.vendorId);
	// getDomElementById("thirdPartyName").value =
	// manifestTO.manifestProcessTo.vendorId;

	clearDropDownList("thirdPartyName");
	/*
	 * addOptionTODropDown("thirdPartyName",
	 * manifestDetailsTO.destinationCityTO.cityName,
	 * manifestDetailsTO.destinationCityTO.cityId);
	 */
	addOptionTODropDown("thirdPartyName", manifestTO.thirdPartyName,
			manifestTO.vendorId);
	// document.getElementById("thirdPartyName").value =
	// manifestTO.thirdPartyName;
	getDomElementById("thirdPartyName").value = manifestTO.vendorId;

}

/** * fill only header details * */
function populateHeaderDetails(manifestTO) {
	document.getElementById("manifestDate").value = manifestTO.manifestDate;
	// document.getElementById("manifestProcessId").value =
	// manifestTO.manifestProcessTo.manifestProcessId;
	getDomElementById("manifestId").value = manifestTO.manifestId;
	// getDomElementById("loadNo").value = manifestTO.manifestProcessTo.loadNo;
	getDomElementById("loadNo").value = manifestTO.loadNoId;
	getDomElementById("bagLockNo").value = manifestTO.bagLockNo;
	document.getElementById("outMnfstDestIds").value = manifestTO.outMnfstDestIds;
	// jQuery('#manifestProcessCode').value=data.manifestProcessTo.manifestProcessCode;
	populateTPDropDown(manifestTO);
	// split weight and display to screen
	var finalWeight = manifestTO.finalWeight + "";
	populateFinalWeight(finalWeight);
}

/**
 * To populate final weight in header
 * 
 * @param finalWeight
 */
function populateFinalWeight(finalWeight) {
	// setFinalWeight(finalWeight);
	getDomElementById("finalWeight").value = finalWeight;
	var wt = finalWeight.split(".");
	getDomElementById("finalKgs").value = (isNull(wt[0])) ? 0 : wt[0];
	getDomElementById("finalGms").value = (isNull(wt[1])) ? "000" : wt[1];

	var gmValue = document.getElementById("finalGms").value;
	if (gmValue.length == 0) {
		document.getElementById("finalGms").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("finalGms").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("finalGms").value += "0";
		gmValue += "0";
	}
}

/**
 * @Desc checks if all the rows in the grid is filled
 * @returns {Boolean}
 */
function isAllRowInGridFilled(maxRowAllowed) {
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (isNull(getDomElementById("scannedGridItemNo" + i).value))
			return false;
	}
	return true;
}

/**
 * redirectToChildCNPage
 * 
 * @param rowId
 */
function redirectToChildCNPage(rowId) {
	if (!isNull(getDomElementById("scannedGridItemNo" + rowId).value)) {
		var noOfPcElement = getDomElementById('noOfPieces' + rowId);
		if (noOfPcElement.readOnly == false) {
			var pieces = noOfPcElement.value;
			var processCode = "MANIFEST";
			// PIECES_OLD = PIECES_NEW;
			PIECES_NEW = pieces;
			PIECES_OLD = PIECES_NEW;
			if (PIECES_NEW < PIECES_ACTUAL) {
				PIECES_OLD = 0;
			}
			if (!isNull(pieces) && pieces > 1) {// PIECES_NEW!=PIECES_OLD
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
 * @desc to disable blank rows before save
 */
function disableForSave() {
	// disableHeaderDropDown();
	disableHeader();
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById("scannedGridItemNo" + i).value))
			disableGridRowById(i);
	}
}
/**
 * @Desc To disable grid by rowId
 * @param domElementId
 */
function disableGridRowById(domElementId) {
	// read only text fields
	disableElement(getDomElementById("scannedGridItemNo" + domElementId));
	/*
	 * disableElement(getDomElementById("weight"+domElementId));
	 * disableElement(getDomElementById("weightGm"+domElementId));
	 */
	disableElement(getDomElementById("noOfPieces" + domElementId));
}

/* enable header drop downs */
function disableHeaderDropDown() {
	document.getElementById("loadNo").disabled == true;
	document.getElementById("thirdPartyType").disabled == true;
	document.getElementById("thirdPartyName").disabled == true;
}

/**
 * @Desc To enble grid by rowId
 * @param domElementId
 */
function enableGridRowById(domElementId) {
	// read only text fields
	enableElement(getDomElementById("scannedGridItemNo" + domElementId));
	/*
	 * enableElement(getDomElementById("weight"+domElementId));
	 * enableElement(getDomElementById("weightGm"+domElementId));
	 */
	enableElement(getDomElementById("noOfPieces" + domElementId));
}
/**
 * @Desc To enable element (text field)
 * @param domElement
 */
function enableElement(domElement) {
	domElement.readOnly = false;
	domElement.setAttribute("tabindex", "0");
}

/**
 * To call this function on enter key navigation
 * 
 * @param e
 * @returns {Boolean}
 */
function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("scannedGridItemNo" + rowId);
	return callEnterKey(e, focusId);

}

/**
 * To check consignment
 * 
 * @param e
 * @param consgObj
 * @returns {Boolean}
 */
function chechConsignment(e, consgObj) {

	var consgObjVal = consgObj.value;
	var rowId = getRowId(consgObj, "scannedGridItemNo");
	if (!isNull(consgObjVal)) {
		if (consgObjVal.length == 10) {
			return enterKeyNav("weight" + (rowId), e.keyCode);
		} else {

			return enterKeyNav("scannedGridItemNo" + (++rowId), e.keyCode);
		}
	}
}
/* **** capture weight for grid - Manifest in grid - weighing machine */
function capturedWeightForGrid() {
	/*
	 * var weightInKg = null; var weightGm = null;
	 * 
	 * wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	 * wmActualWeight = parseFloat(wmActualWeight).toFixed(3); wmCapturedWeight =
	 * parseFloat(weigth).toFixed(3);
	 * 
	 * newWMWt=wmActualWeight;
	 */

	var detailsTO = bookingManifestDetail;

	// weighing Machine Integration
	var weight = parseFloat(detailsTO.weight).toFixed(3);
	/*
	 * if(!isNull(cnWeightFromWM) && parseFloat(cnWeightFromWM)>weight){
	 * weight=cnWeightFromWM; }
	 */
	var weightToValue = weight + "";

	if (!isNull(weightToValue)) {
		var manifestNo = document.getElementById("scannedGridItemNo" + ROW_ID).value;
		// clear the row before populating the manifest details
		clearGridDetails(ROW_COUNT);
		document.getElementById("scannedGridItemNo" + ROW_ID).value = manifestNo;
		// document.getElementById("manifestIds" + ROW_ID).value =
		// manifestTO.manifestId;
		document.getElementById("weights" + ROW_ID).value = weightToValue;
		var weightKgValue = "";
		var weightGmValue = "";
		weightKgValue = weightToValue.split(".");
		document.getElementById("weight" + ROW_ID).value = (isNull(weightKgValue[0])) ? "0"
				: weightKgValue[0];
		weightGmValue = weightToValue.split(".")[1];
		document.getElementById("weightGm" + ROW_ID).value = (isNull(weightGmValue)) ? "0"
				: weightGmValue;

		// check weightTolerance for manifest
		weightFormatForGm(getDomElementById("weightGm" + ROW_ID));

		// For Consg. NoOfPcs - Enable & For Manifest. NoOfPcs - Disable
		getDomElementById("noOfPieces" + ROW_ID).readOnly = true;
		getDomElementById("noOfPieces" + ROW_ID).value = "";

		if (maxWtCheck(ROW_COUNT)) {
			jQuery("#embeddedType" + ROW_ID).val(detailsTO.embeddedType);
		}
		isSaved = false;
	}
	hideProcessing();
}

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		var url = "./thirdPartyBPLOutManifest.do?submitName=viewThirdPartyBPL";
		document.thirdPartyBPLOutManifestForm.action = url;
		document.thirdPartyBPLOutManifestForm.submit();
	}
}

/**
 * To print Third Party BPL Details
 */
function printThirdParty() {
	if (confirm("Do you want to Print?")) {
		var manifestStatus = document.getElementById("manifestStatus").value;
		var manifestNO = document.getElementById("manifestNo").value;
		var loginOffceID = document.getElementById('loginOfficeId').value;
		if (manifestStatus != null && manifestStatus == "O") {
			alert("Only closed manifest can be printed.");
			return false;
		}
		if (!isNull(manifestNO)) {
			showProcessing();
			var url = './thirdPartyBPLOutManifest.do?submitName=printThirdPartyDtls&manifestNo='
					+ manifestNO + "&loginOfficeId=" + loginOffceID;
			var w = window
					.open(url, 'myPopUp',
							'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			/*
			 * window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left =
			 * 412,top = 184,scrollbars=yes'); //showProcessing(); jQuery.ajax({
			 * url : url, success : function(req) { //hideProcessing(); } });
			 */

			// var w= window.open(url,'_blank','top=1, left=1, height=1,
			// width=1,
			// status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,
			// location=no, directories=no');
			// setTimeout(function() { w.close(); }, 50000);
			/*
			 * document.thirdPartyBPLOutManifestForm.action = url;
			 * document.thirdPartyBPLOutManifestForm.submit(); window.focus();
			 * w.print();
			 */
			/*
			 * window.frames['iFrame'].focus(); window.frames['iFrame'].print();
			 */
			hideProcessing();
		} else {
			alert("Please provide BPL No.");
		}
	}
}

/**
 * TO cancel or clear page
 */
function cancelPage() {
	var url = "./thirdPartyBPLOutManifest.do?submitName=viewThirdPartyBPL";
	window.location = url;
}

function refreshPage() {
	var url = "./thirdPartyBPLOutManifest.do?submitName=viewThirdPartyBPL";
	document.thirdPartyBPLOutManifestForm.action = url;
	document.thirdPartyBPLOutManifestForm.submit();
}

/**
 * To get Third Party Names
 * 
 * @param Obj
 */
function getTPNames(Obj) {
	var regionId = jQuery("#regionId").val();
	if (!isNull(Obj)) {
		var url = "./thirdPartyBPLOutManifest.do?submitName=getThirdPartyName&partyID="
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
		clearDropDownList('thirdPartyName', '---SELECT---');
	}
}

/**
 * To check manifest number validation
 * 
 * @param manifestObj
 * @param gridLevel
 */
function checkManifestNoValidation(manifestObj, gridLevel) {
	if (!manifestObj.readOnly) {
		isValidManifestNo(manifestObj, gridLevel);
	}
}

/**
 * To check for scaned Consignment
 * 
 * @param cnNo
 * @returns {Boolean}
 */
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

/**
 * Remove from global
 * 
 * @param cnNo
 */
function removeScanConsignments(cnNo) {
	for ( var i = 0; i < scanedConsignment.length; i++) {
		if (scanedConsignment[i] == cnNo) {
			scanedConsignment[i] = null;
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
	// var chCn = childCNId.value;
	// if (!isNull(chCn)) {
	scanedAllchildCNs = new Array();
	for ( var count = 1; count <= maxCNsAllowed; count++) {
		childcnNo = getDomElementById("childCns" + count).value;
		if (!isNull(childcnNo)) {
			addChildCNInArray(childcnNo);
		}
	}
	// }
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
 * To compare child CN weight
 * 
 * @param rowNo
 */
function compairChildCNWeightAndCNWeight(rowNo) {
	// Child Cns validation
	var noOfPcs = document.getElementById("noOfPieces" + rowNo).value;
	var chilsCNDtls = document.getElementById("childCns" + rowNo).value;

	// Actual Weight
	var actWeight = "";
	var actWtKg = getDomElementById("weight" + rowNo).value;
	var actWtGm = getDomElementById("weightGm" + rowNo).value;
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
 * To validate parent and child CNs Weight
 * 
 * @param parentCnWeight
 * @param chilsCNDtls
 * @param rowCount
 */
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
		if (parseFloat(consgWeight) < parseFloat(childCnsTotalWeight)) {
			alert("Parent consignment weight can't be less than total of child consignments weight ("
					+ childCnsTotalWeight + ") at Line:" + rowCount);
			var noOfPcs = document.getElementById("noOfPieces" + rowCount);
			var chilsCNDtls = document.getElementById("childCns" + rowCount);
			noOfPcs.value = "";
			chilsCNDtls.value = "";
			setTimeout(function() {
				noOfPcs.focus();
			}, 10);
		}
	}
}

/**
 * To compair child CNWt And CnWt
 */
function compairChildCNWtAndCNWt() {
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		var noOfPcs = document.getElementById("noOfPieces" + i).value;
		var chilsCNDtls = document.getElementById("childCns" + i).value;
		if (!isNull(noOfPcs) && !isNull(chilsCNDtls)) {
			compairChildCNWeightAndCNWeight(i);
		}
	}
}

function setIFrame() {
	var manifestNO = document.getElementById("manifestNo").value;
	var loginOffceID = document.getElementById('loginOfficeId').value;
	var url = './thirdPartyBPLOutManifest.do?submitName=printThirdPartyDtls&manifestNo='
			+ manifestNO + "&loginOfficeId=" + loginOffceID;
	printUrl = url;
	printIframe(printUrl);
}

/**
 * To call function on key press to validate enter key in grid
 * 
 * @param e
 * @param count
 * @returns
 */
function fnEnterKeyCallOnCnNo(e, count) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var scnNo = document.getElementById('scannedGridItemNo' + count).value;
		if (!isNull(scnNo)) {
			var nextRow = parseIntNumber(count) + 1;
			if (!isNull(document.getElementById('scannedGridItemNo' + nextRow))) {
				return callEnterKey(e, document
						.getElementById('scannedGridItemNo' + nextRow));
			}
			setTimeout(function() {
				getDomElementById("saveBtn").focus();
			}, 10);
			return false;
		} else {
			alert("Please Enter Consignment/Manifest Number");
			setTimeout(function() {
				getDomElementById("scannedGridItemNo" + count).focus();
			}, 10);
			return false;
		}
	}
}
