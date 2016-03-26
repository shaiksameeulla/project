var isSaved = false;

var misrouteType;
var destRegion;
var destCity;
var consgType;
var destOffice;

var newMisrouteType;
var newDestRegion;
var newDestCity;
var newConsgType;
var newDestOffice;

var isConfirmChanges = false;
var isFilled = false;
var isRegion;
rowCount = 1;
/* @Desc: Adds the rows in grid dynamically */
function fnClickAddRow() {

	$('#misrouteGrid')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"    id="checkBox'
									+ rowCount
									+ '" name="chkBoxName" onclick="getScannedIdList(this);" value=""/><input type="hidden" id="ischecked'
									+ rowCount + '" name="to.ischeckeds"/>',
							rowCount,
							'<input type="text" id="scannedGridItemNo'
									+ rowCount
									+ '" name="to.scannedItemNos" size="15" maxlength="12"  class="txtbox" onfocus="validateHeaderDetails();"  onblur="fnValidateNumber(this);" onkeypress="return callEnterKey(event, document.getElementById(\'origin'
									+ rowCount
									+ '\'));"/><input type="hidden" id="scannedItemId'
									+ rowCount
									+ '" name="to.scannedItemIds"/><input type="hidden" id="manifestEmbeddedIn'
									+ rowCount
									+ '" name="to.manifestEmbeddeIns"/><input type="hidden" id="consgId'
									+ rowCount + '" name="to.consgId"/>',
							'<input type="text" id="origin'
									+ rowCount
									+ '" name="to.origins" size="10" class="txtbox"   maxlength="10" readonly="readonly"  onkeypress="return callEnterKey(event, document.getElementById(\'remarks'
									+ rowCount + '\'));"  />',
							'<input type="text"    class="txtbox width30" size="2" id="noOfPieces'
									+ rowCount
									+ '" name="to.pieces" tabIndex="-1" readonly="readonly"  value=""  /> ',
							'<input type="text"    class="txtbox" size="10" id="Pincode'
									+ rowCount
									+ '" name="to.pincodes" tabIndex="-1" readonly="readonly" /><input type="hidden" id="pincodeId'
									+ rowCount
									+ '" name="to.pincodeId" value=""/>',
							'<input type="text" id="weight'
									+ rowCount
									+ '" class="txtbox width30" readonly="readonly" tabIndex="-1" size="11"  onkeypress="return callEnterKey(event, document.getElementById(\'remarks'
									+ rowCount
									+ '\'));"/><span class="lable">.</span><input type="text"  id="weightGm'
									+ rowCount
									+ '" class="txtbox width30" readonly="readonly" tabIndex="-1"  size="11"  /><input type="hidden" id="actualWeight'
									+ rowCount + '" name="to.actualWeights"/>',
							'<input type="text"    class="txtbox"  size="12" id="CnContent'
									+ rowCount
									+ '" name="to.cnContents" readonly="readonly" tabIndex="-1" /><input type="hidden" id="CnContentId'
									+ rowCount
									+ '" name="to.cnContentIds" value=""/>',
							'<input type="text"    class="txtbox" size="15" id="paperwork'
									+ rowCount
									+ '" name="to.paperWorks" readonly="readonly" tabIndex="-1"/> <input type="hidden" id="PaperWorkId'
									+ rowCount
									+ '" name="to.paperWorkIds" value="" />',
							'<input type="text"    class="txtbox" size="15" id="insurance'
									+ rowCount
									+ '" name="to.insurances"   readonly="readonly" tabIndex="-1"  /><input type="hidden" id="InsurancePolicyNo'
									+ rowCount
									+ '" name="to.insurancePolicyNo" value="" /><input type="hidden" id="InsuredBy'
									+ rowCount
									+ '" name="to.insuredBy" value=""/>',
							'<input type="text"    class="txtbox" size="20" id="remarks'
									+ rowCount
									+ '" name="to.remarksGrid" onfocus="setRemarksLength(this)" onkeypress="addNewRow(this,event);" />\
							<input type="hidden" id="manifestMappedEmbeddeId'
									+ rowCount
									+ '" name="to.manifestMappedEmbeddeId" value="" />\
									 <input type="hidden" id="consgManifestedIds'
									+ rowCount
									+ '" name="to.consgManifestedIds" value="" />\
								<input type="hidden" id="position'
									+ rowCount
									+ '" name="to.positions" value="'
									+ rowCount + '" />', ]);
	rowCount++;
	updateSrlNo('misrouteGrid');

}

function setRemarksLength(obj){
	var misrouteTpyObjVal = document.getElementById('misrouteType').value;
	if (misrouteTpyObjVal == CONSIGNMENT) {
		obj.maxLength=49;
	}else{
		obj.maxLength=199;
	}
	
}

function addRow(selectedRowId) {
	var table = document.getElementById("misrouteGrid");
	var lastRow = table.rows.length - 1;
	var isNewRow = false;
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(8);
		if (rowId == selectedRowId && i == lastRow) {
			isNewRow = true;
		}
	}
	if (isNewRow) {
		fnClickAddRow();
		var scannedGridItemNo = document.getElementById("scannedGridItemNo"
				+ (selectedRowId + 1));
		/* scannedGridItemNo.focus(); */
	}
}

/* ******prepare list of items scanned on chkbox selection **** */
function getScannedIdList(checkObj) {
	var rowId = getRowId(checkObj, "checkBox");
	jQuery('#misrouteGrid >tbody >tr').each(function(i, tr) {
		var isChecked = jQuery(this).find('input:checkbox').is(':checked');
		if (isChecked) {

			$(this).closest('tr').addClass('gradeAgrey');
		} else {
			$(this).closest('tr').removeClass('gradeAgrey');
		}
	});
}

function getAllMisrouteCities() {
	if (isRegion) {
		jQuery("#regionId").val(destRegion);
		jQuery("#cityId").val(destCity);
		jQuery("#stationId").val(destOffice);

	} else {
		getMisrouteCities();
	}
}

/* @Desc:Gets the offices based on selection of region */
function getMisrouteCities() {
	var destRegionId = document.getElementById("regionId").value;
	if (!isNull(destRegionId)) {
		showProcessing();
		createDropDown("cityId", "", "SELECT");
		createDropDown("stationId", "", "SELECT");
		url = './outManifestDox.do?submitName=getCitiesByRegion&regionId='
				+ destRegionId;
		ajaxCallWithoutForm(url, printAllMisrouteCities);
	}

}

function printAllMisrouteCities(data) {
	if (!isNull(data)) {
		var content = document.getElementById('cityId');
		content.innerHTML = "";
		defOption = document.createElement("option");
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

	} else {
		showDropDownBySelected("regionId", "");
		alert("No cities found for the selected region");
		document.getElementById('regionId').focus();
	}
	hideProcessing();
}

function getAllOffices(cityObj) {
	if (isRegion) {
		jQuery("#regionId").val(destRegion);
		jQuery("#cityId").val(destCity);
		jQuery("#stationId").val(destOffice);

	} else {
		getAllMisrouteOffices(cityObj);
	}
}
/* @Desc:Gets the offices based on selection of region */
function getAllMisrouteOffices(cityObj) {

	var cityId = cityObj.value;

	if (!isNull(cityId)) {
		showProcessing();
		url = './misroute.do?submitName=getAllOfficesByCity&cityId=' + cityId;
		ajaxCallWithoutForm(url, printAllOffices);
	}

}
function printAllOffices(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationId');
		content.innerHTML = "";
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});

	} else {
		showDropDownBySelected("cityId", "");
		createDropDown("stationId", "", "SELECT");
		alert("No offices found for the selected city");
		document.getElementById('cityId').focus();
	}
	hideProcessing();
}

/* @Desc: validates the manifest/cong number in grid */
function fnValidateNumber(obj) {
	if (!isNull(obj.value)) {
		var count = getRowId(obj, "scannedGridItemNo");
		if (isDuplicateScannedItem(obj, count)) {
			getScannedGridItemNoDetails(obj);

		}
	}
}
/* @Desc: to check whether a consg/manifest no is scanned again in the same grid */
function isDuplicateScannedItem(scannedGridItemNoObj, count) {
	var isValid = true;
	var currentScan = scannedGridItemNoObj.value;
	var table = document.getElementById('misrouteGrid');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {

		var id = table.rows[i].cells[0].childNodes[0].id.substring(8);
		var prevScan = document.getElementById('scannedGridItemNo' + id).value;
		if (!isNull($.trim(prevScan)) && !isNull($.trim(currentScan))) {
			if (count != id) {
				if ($.trim(prevScan.toUpperCase()) == $.trim(currentScan
						.toUpperCase())) {
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

/*
 * **** This function is used to identify that the scanned item is a consignment
 * or manifest depending on its length It then calls repective format validation
 * methods and gets details to fill the grid***********
 */
function getScannedGridItemNoDetails(scannedGridItemNoObj) {
	var isConsigValid = false;
	var isManifestValid = false;
	var destOfficeId = document.getElementById("stationId").value;
	var cityId = document.getElementById("cityId").value;
	var manifestNo = document.getElementById("misrouteNumber").value;
	var loginOfficeObj = document.getElementById("loginOfficeId");
	var loginOfficeId = "";
	var manifestDirectn = "O";

	var scannedId = getRowId(scannedGridItemNoObj, "scannedGridItemNo");
	ROW_COUNT = scannedId;

	if (loginOfficeObj != null)
		loginOfficeId = loginOfficeObj.value;

	var misrouteTpyObjVal = document.getElementById('misrouteType').value;
	if (misrouteTpyObjVal == MASTER_BAG) {
		document.getElementById("noOfPieces" + ROW_COUNT).disabled = true;
		document.getElementById("Pincode" + ROW_COUNT).disabled = true;
		document.getElementById("CnContent" + ROW_COUNT).disabled = true;
		document.getElementById("paperwork" + ROW_COUNT).disabled = true;
		document.getElementById("insurance" + ROW_COUNT).disabled = true;
	}
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var consignmentType = "";
	if (docTypeIdName != null)
		consignmentType = docTypeIdName.split("#")[1];

	if ((!isNull(misrouteTpyObjVal)) && (!isNull(docTypeIdName))
			|| (misrouteTpyObjVal == MASTER_BAG)
			|| (misrouteTpyObjVal == PACKET)) {

		if (misrouteTpyObjVal == CONSIGNMENT) {
			isConsigValid = isValidConsignment(scannedGridItemNoObj);
			if (isConsigValid) {
				showProcessing();
				url = './misroute.do?submitName=getConsignmentDtls&consignmentNo='
						+ scannedGridItemNoObj.value
						+ "&loginOfficeId="+ loginOfficeId
						+ "&destOfficeId="+ destOfficeId
						+ "&cityId="+ cityId
						+ "&manifestDirection="+ manifestDirectn
						+ "&manifestNo="+ manifestNo
						+ "&consignmentType=" + consignmentType
						+ "&misrouteType=" + misrouteTpyObjVal;
				jQuery.ajax({
					url : url,
					success : function(req) {
						printCallMisrouteConsignment(req, scannedGridItemNoObj,
								consignmentType);
					}
				});
			}

		} else if (misrouteTpyObjVal == PACKET || misrouteTpyObjVal == BAG
				|| misrouteTpyObjVal == MASTER_BAG) {
			isManifestValid = validateManifestFormat(scannedGridItemNoObj);
			if (isManifestValid) {

				getManifestDtls(scannedGridItemNoObj, misrouteTpyObjVal,
						consignmentType);
			}
		}
	} else {
		alert("Please select misroute type or consignment type");
		scannedGridItemNoObj.value = "";

	}
}

// Validates Consignment number
function isValidConsignment(consgNumberObj) {
	var flag = true;
	if (!isNull(consgNumberObj.value)) {
		var consgNumber = "";
		consgNumber = consgNumberObj.value;
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consignment length should be 12 character");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			flag = false;
		}
	} else {
		flag = false;
	}

	return flag;
}

function printCallMisrouteConsignment(data, consgNumberObj) {
	var responseText = jsonJqueryParser(data);
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
		} else {
			populateConsDetails(responseText);
		}

	}

}

/* ***This function is use to populate the consg details**** */
function populateConsDetails(data) {
	var response = data;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var consignmentType = "";
	if (docTypeIdName != null)
		consignmentType = docTypeIdName.split("#")[1];

	if (!isNull(response)) {

		/*
		 * else if (loginOffId == response.originOffName && response.processCode ==
		 * "MSRT") { alert("Consignment used in another Misroute Manifest.");
		 * 
		 * setTimeout( function() { document .getElementById("scannedGridItemNo" +
		 * ROW_COUNT).value = ""; document .getElementById("scannedGridItemNo" +
		 * ROW_COUNT) .focus(); }, 10); }
		 */
		var weightInKg = null;
		var weightGm = null;
		if (consignmentType == DOCUMENT) {
			jQuery("#origin" + ROW_COUNT).val(response.origin);
			jQuery("#scannedItemId" + ROW_COUNT).val(response.scannedItemId);
			jQuery("#Pincode" + ROW_COUNT).val(response.pincode);
			jQuery("#pincodeId" + ROW_COUNT).val(response.pincodeId);
			jQuery("#actualWeight" + ROW_COUNT).val(response.actualWeight);
			var splitWeight = response.actualWeight + "";
			if (!isNull(splitWeight)) {
				weightInKg = splitWeight.split(".");
				document.getElementById("weight" + ROW_COUNT).value = weightInKg[0];
				weightGm = splitWeight.split(".")[1];

				if (isNull(weightGm)) {
					weightGm = "00";
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				} else {
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				}

			}
		} else {
			jQuery("#origin" + ROW_COUNT).val(response.origin);
			jQuery("#noOfPieces" + ROW_COUNT).val(response.noOfPieces);
			jQuery("#Pincode" + ROW_COUNT).val(response.pincode);
			jQuery("#pincodeId" + ROW_COUNT).val(response.pincodeId);
			jQuery("#actualWeight" + ROW_COUNT).val(response.actualWeight);
			jQuery("#CnContent" + ROW_COUNT).val(response.cnContent);
			jQuery("#CnContentId" + ROW_COUNT).val(response.cnContentId);
			jQuery("#paperwork" + ROW_COUNT).val(response.paperWork);
			jQuery("#PaperWorkId" + ROW_COUNT).val(response.paperWorkId);
			jQuery("#scannedItemId" + ROW_COUNT).val(response.scannedItemId);
			document.getElementById("InsuredBy" + ROW_COUNT).value = response.insuredBy;
			var insurance = document.getElementById("InsuredBy" + ROW_COUNT).value
					+ "/";
			document.getElementById("InsurancePolicyNo" + ROW_COUNT).value = response.insurancePolicyNo;
			var insurancePolicy = document.getElementById("InsurancePolicyNo"
					+ ROW_COUNT).value;
			var setinsurance = insurance.concat(insurancePolicy);
			jQuery("#insurance" + ROW_COUNT).val(setinsurance);

			var splitWeight = response.actualWeight + "";
			if (!isNull(splitWeight)) {
				weightInKg = splitWeight.split(".");
				document.getElementById("weight" + ROW_COUNT).value = weightInKg[0];
				weightGm = splitWeight.split(".")[1];

				if (isNull(weightGm)) {
					weightGm = "00";
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				} else {
					document.getElementById("weightGm" + ROW_COUNT).value = weightGm;
				}

			}
		}
	}
	hideProcessing();
}

/* to get grid level manifest details */
function getManifestDtls(manifestNoObj, misrouteTpyObjVal, consignmentType) {

	if (validateManifest(manifestNoObj, misrouteTpyObjVal)) {
		manifestNoObj.value = $.trim(manifestNoObj.value);
		var loginOfficeId = $.trim(getDomElementById("loginOfficeId").value);
		showProcessing();

		var url = "./misroute.do?submitName=getManifestDtls&manifestNo="
				+ manifestNoObj.value + " &loginOfficeId=" + loginOfficeId
				+ " &consignmentType=" + consignmentType + " &misrouteType="
				+ misrouteTpyObjVal;
		$.ajax({
			url : url,
			success : function(req) {
				populateManifest(req, manifestNoObj, consignmentType);
			}
		});
	} else {
		alert("Please Enter valid manifest number");
		manifestNoObj.value = "";
		setTimeout(function() {
			manifestNoObj.focus();
		}, 10);
		return false;
	}
}

function validateManifestFormat(manifestNoObj) {
	if (!isNull(manifestNoObj.value)) {
		var manifestNo = "";
		manifestNo = manifestNoObj.value;
		var numpattern = /^[0-9]{3,20}$/;
		var fourthCharB = /^[B]$/;
		var letter4 = manifestNoObj.value.substring(3, 4);
		var fourthCharM = /^[M]$/;

		if (manifestNo.length < 10 || manifestNo.length > 10) {
			alert("Manifest length should be 10 character");
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;

		} else {
			if ((numpattern.test(manifestNoObj.value.substring(3)))
					|| (numpattern.test(manifestNoObj.value.substring(4)))
					&& (letter4.match(fourthCharB))
					|| (numpattern.test(manifestNoObj.value.substring(4)))
					&& (letter4.match(fourthCharM))) {
				return true;
			} else {
				alert('Manifest Format is not correct');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				return false;
			}
		}

	}
}

/* checks whether the entered the manifest no satisfies the manifest type format */
function validateManifest(manifestNoObj, misrouteTpyObjVal) {
	var numpattern = /^[0-9]{3,20}$/;
	var fourthCharM = /^[M]$/;
	var fourthCharB = /^[B]$/;
	var letter4 = manifestNoObj.value.substring(3, 4);

	if (misrouteTpyObjVal == PACKET
			&& (numpattern.test(manifestNoObj.value.substring(3)))) {
		return true;
	} else if (misrouteTpyObjVal == BAG && letter4.match(fourthCharB)) {
		return true;
	} else if (misrouteTpyObjVal == MASTER_BAG && letter4.match(fourthCharM)) {
		return true;
	} else {
		return false;
	}
}

/* ***This function is use to populate the manifest details**** */
function populateManifest(ajaxResp, manifestNoObj, consignmentType) {
	var rowId = getRowId(manifestNoObj, "scannedGridItemNo");
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			setTimeout(
					function() {
						clearRow(rowId);
						document.getElementById("scannedGridItemNo" + rowId).value = "";
						document.getElementById("scannedGridItemNo" + rowId)
								.focus();
					}, 10);
		} else {
			fillManifestDetails(responseText, rowId, consignmentType);
		}
	}

}

function fillManifestDetails(manifestTO, rowId, consignmentType) {
	if (manifestTO != null) {
		var loginOffId = document.getElementById("loginOfficeId").value;

		if (loginOffId == manifestTO.originOffName) {
			alert("Manifest made in current office , cannot be misrouted.");
			document.getElementById("scannedGridItemNo" + rowId).value = "";
			document.getElementById("scannedGridItemNo" + rowId).focus();
			clearRow(rowId);
			return;

		} else if (consignmentType == DOCUMENT) {
			document.getElementById("scannedItemId" + rowId).value = manifestTO.scannedItemId;
			document.getElementById("origin" + rowId).value = manifestTO.origin;
			document.getElementById("manifestEmbeddedIn" + rowId).value = manifestTO.manifestEmbeddeIn;

		} else if (isNull(consignmentType)) {
			document.getElementById("scannedItemId" + rowId).value = manifestTO.scannedItemId;
			document.getElementById("origin" + rowId).value = manifestTO.origin;
			document.getElementById("manifestEmbeddedIn" + rowId).value = manifestTO.manifestEmbeddeIn;

			var weightToValue = manifestTO.actualWeight + "";
			if (!isNull(weightToValue)) {
				document.getElementById("actualWeight" + rowId).value = manifestTO.actualWeight;
				var weightKgValue = "";
				var weightGmValue = "";
				weightKgValue = weightToValue.split(".");
				document.getElementById("weight" + rowId).value = weightKgValue[0];
				weightGmValue = weightToValue.split(".")[1];
				document.getElementById("weightGm" + rowId).value = weightGmValue;
				if (weightKgValue.length == 0 || weightGmValue == ""
						|| weightGmValue == null) {
					document.getElementById("weightGm" + rowId).value = "000";
					weightGmValue += "000";
				} else if (weightGmValue.length == 1) {
					document.getElementById("weightGm" + rowId).value += "00";
					weightGmValue += "00";
				} else if (weightGmValue.length == 2) {
					document.getElementById("weightGm" + rowId).value += "0";
					weightGmValue += "0";
				}
			}
		} else {
			document.getElementById("scannedItemId" + rowId).value = manifestTO.scannedItemId;
			document.getElementById("origin" + rowId).value = manifestTO.origin;
			document.getElementById("Pincode" + rowId).value = manifestTO.pincode;
			document.getElementById("pincodeId" + rowId).value = manifestTO.pincodeId;
			document.getElementById("manifestEmbeddedIn" + rowId).value = manifestTO.manifestEmbeddeIn;

			var weightToValue = manifestTO.actualWeight + "";
			if (!isNull(weightToValue)) {
				document.getElementById("actualWeight" + rowId).value = manifestTO.actualWeight;
				var weightKgValue = "";
				var weightGmValue = "";
				weightKgValue = weightToValue.split(".");
				document.getElementById("weight" + rowId).value = weightKgValue[0];
				weightGmValue = weightToValue.split(".")[1];
				document.getElementById("weightGm" + rowId).value = weightGmValue;
				if (weightKgValue.length == 0 || weightGmValue == ""
						|| weightGmValue == null) {
					document.getElementById("weightGm" + rowId).value = "000";
					weightGmValue += "000";
				} else if (weightGmValue.length == 1) {
					document.getElementById("weightGm" + rowId).value += "00";
					weightGmValue += "00";
				} else if (weightGmValue.length == 2) {
					document.getElementById("weightGm" + rowId).value += "0";
					weightGmValue += "0";
				}
			}
		}
	}

	hideProcessing();
}

/*
 * ***This function is use change the label in grid on basis of misroute type
 * selected****
 */
function labelChange(misrouteTpyObj) {
	misrouteTpyObjVal = misrouteTpyObj.value;
	if (misrouteTpyObjVal == undefined) {
		misrouteTpyObjVal = misrouteTpyObj;
	}
	if (misrouteTpyObjVal == "0") {
		document.getElementById("scannedItem").innerHTML = "CN/Paclet/Bag/MasterBag Number";
		document.getElementById("consgTypeName").disabled = false;
	} else if (misrouteTpyObjVal == CONSIGNMENT) {
		document.getElementById("scannedItem").innerHTML = "Consignment Number";
		document.getElementById("consgTypeName").disabled = false;
		fnGetConsgTypeByMnfstAndMsrtType(misrouteTpyObjVal);
	} else if (misrouteTpyObjVal == PACKET) {
		getDomElementById("consgTypeName").value = "0";
		document.getElementById("scannedItem").innerHTML = "Packet Number";
		document.getElementById("consgTypeName").disabled = true;
	} else if (misrouteTpyObjVal == BAG) {
		document.getElementById("scannedItem").innerHTML = "Bag Number";
		document.getElementById("consgTypeName").disabled = false;
		fnGetConsgTypeByMnfstAndMsrtType(misrouteTpyObjVal);
	} else if (misrouteTpyObjVal == MASTER_BAG) {
		getDomElementById("consgTypeName").value = "0";
		document.getElementById("scannedItem").innerHTML = "Master Bag Number";
		document.getElementById("consgTypeName").disabled = true;
	}
}

/* ***This function is use for adding new row**** */
function addNewRow(obj, event) {
	if (event.keyCode == "13" || event.keyCode == "40") {
		var rowId = getRowId(obj, "remarks");
		var table = document.getElementById("misrouteGrid");
		var lastRow = table.rows.length - 1;
		var isNewRow = false;
		for ( var i = 1; i < table.rows.length; i++) {
			var selectedRowId = table.rows[i].cells[0].childNodes[0].id
					.substring(8);
			if (selectedRowId == rowId && i == lastRow) {
				isNewRow = true;
			}
		}

		if (isNewRow) {
			var rowId = getRowId(obj, "remarks");
			if (checkMandatoryForAdd(rowId)) {
				addNewValidRow(rowId);
				rowNo = rowId++;
				getDomElementById("scannedGridItemNo" + (rowNo + 1)).focus();
			}

		}
	}
}
/*
 * ***This function is use to Check manadatory fields in grid before adding new
 * row****
 */
function checkMandatoryForAdd(rowId) {
	var scannedGridItemNo = getDomElementById("scannedGridItemNo" + rowId);
	var remarks = getDomElementById("remarks" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull($.trim(scannedGridItemNo.value))) {
		alert("Please enter CN/Paclet/Bag/MasterBag Number" + lineNum);
		setTimeout(function() {
			scannedGridItemNo.focus();
		}, 10);
		return false;
	}

	if (isNull($.trim(remarks.value))) {
		alert("Please enter Remarks" + lineNum);
		setTimeout(function() {
			remarks.focus();
		}, 10);
		return false;
	}

	return true;
}

function addNewValidRow(rowId) {
	fnClickAddRow();

}

/* validates the mandatory fields */
function validateHeaderDetails() {
	var isMissed = true;
	var misrouteNumber = document.getElementById("misrouteNumber");
	var regionId = document.getElementById("regionId");
	var stationId = document.getElementById("stationId");
	var misrouteType = document.getElementById("misrouteType");
	var consgTypeName = document.getElementById("consgTypeName");
	var cityId = document.getElementById("cityId");
	var bagLockNo = document.getElementById("bagLockNo");

	var numpattern = /^[0-9]{3,20}$/;
	var fourthCharB = /^[B]$/;
	var letter4 = misrouteNumber.value.substring(3, 4);

	var missingFields = "Please provide : ";

	if (isNull($.trim(misrouteNumber.value))) {
		missingFields = missingFields + " Misroute Number,";
		setTimeout(function() {
			misrouteNumber.focus();
		}, 10);
		isMissed = false;
	}

	if (isNull(regionId.value)) {

		missingFields = missingFields + "Destination Region,";
		setTimeout(function() {
			regionId.focus();
		}, 10);
		isMissed = false;
	}
	if (isNull(cityId.value)) {

		missingFields = missingFields + "Destination City,";
		setTimeout(function() {
			cityId.focus();
		}, 10);
		isMissed = false;
	}

	if (isNull(stationId.value)) {

		missingFields = missingFields + "Destination Office,";
		setTimeout(function() {
			stationId.focus();
		}, 10);
		isMissed = false;
	}

	if (isNull(misrouteType.value)) {

		missingFields = missingFields + "Misroute Type,";
		setTimeout(function() {
			misrouteType.focus();
		}, 10);
		isMissed = false;
	}

	if (misrouteType.value == CONSIGNMENT || misrouteType.value == BAG) {
		if (isNull(consgTypeName.value)) {

			missingFields = missingFields + "Consignment Type,";
			setTimeout(function() {
				consgTypeName.focus();
			}, 10);
			isMissed = false;
		}

	}
	if (letter4.match(fourthCharB)) {
		if (isNull($.trim(bagLockNo.value))) {

			missingFields = missingFields + "BagLock No,";
			setTimeout(function() {
				bagLockNo.focus();
			}, 10);
			isMissed = false;
		}
	}

	if (!isMissed)
		alert(missingFields);
	return isMissed;

}
function validateGridDetails() {
	var table = document.getElementById("misrouteGrid");
	var maxManifestAllowed = table.rows.length;
	for ( var i = 1; i < maxManifestAllowed; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(8);
		if (!isNull(getDomElementById("scannedGridItemNo" + rowId).value)) {
			if (!validateSelectedRow(rowId)) {
				return false;
			}
			flag = true;
		}
	}
	if (!flag)
		alert("Please enter at least one Consignment/Manifest Number");
	return flag;

}

function validateSelectedRow(rowId) {
	var scannedGridItemNo = document
			.getElementById("scannedGridItemNo" + rowId);
	var remarks = document.getElementById("remarks" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull($.trim(scannedGridItemNo.value))) {
		alert("Please Enter CN/Parcel/Bag/MasterBag Number." + lineNum);
		setTimeout(function() {
			document.getElementById("scannedGridItemNo" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(remarks.value))) {
		alert("Please Enter remarks." + lineNum);
		setTimeout(function() {
			document.getElementById("remarks" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return true;
}

/* ***This function is use to psave the misroute details**** */
function saveMisroute() {
	if (validateHeaderDetails() && validateGridDetails()) {
		enableHeaderDropDown();
		showProcessing();
		var url = './misroute.do?submitName=saveOrUpdateMisroute';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#misrouteForm").serialize(),
			success : function(req) {
				printCallBackMisrouteSave(req);
			}
		});

	}
}

/*
 * @Desc:Response Method of Saving or Closing the details of MBPL No entered in
 * Header
 */
function printCallBackMisrouteSave(ajaxResp) {
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			alert(success);
			disableForSearch();
		}
	}
}

/*
 * This function is used to search a Misroute manifest which is already created
 * from same office
 */
function searchMisroute() {
	var misroutNo = document.getElementById("misrouteNumber").value;
	var manifestType = document.getElementById("ManifestDirection").value;
	var loginOffceID = document.getElementById('loginOfficeId').value;
	if (!isNull(misroutNo)) {
		showProcessing();
		url = './misroute.do?submitName=searchMisrouteDetails&misroutNo='
				+ misroutNo + "&manifestType=" + manifestType
				+ "&loginOfficeId=" + loginOffceID;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackManifestDetails(req);
			}
		});
	} else {
		alert("Please enter manifest number.");
	}
}

function printCallBackManifestDetails(ajaxResp) {

	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			document.getElementById("misrouteNumber").value = "";
			document.getElementById("misrouteNumber").focus();

		} else {
			populateManifestDetails(responseText);
		}
	}
}
/* @Desc:Response method of searching the details of misroute manifest no */
function populateManifestDetails(data) {
	var misrouteTO = data;
	if (!isNull(misrouteTO)) {
		// Header Part
		document.getElementById("dateTime").value = misrouteTO.misrouteDate;
		document.getElementById("misrouteId").value = misrouteTO.misrouteId;
		document.getElementById("manifestProcessId").value = misrouteTO.manifestProcessTo.manifestProcessId;

		document.getElementById("regionId").value = misrouteTO.destinationRegionId;
		document.getElementById("bagLockNo").value = misrouteTO.bagLockNo;

		clearDropDownList("cityId");
		addOptionTODropDown("cityId", misrouteTO.destinationCityName,
				misrouteTO.destinationCityId);
		document.getElementById("cityId").value = misrouteTO.destinationCityId;

		clearDropDownList("stationId");
		addOptionTODropDown("stationId", misrouteTO.destinationStationName,
				misrouteTO.destinationStationId);
		document.getElementById("stationId").value = misrouteTO.destinationStationId;

		document.getElementById("misrouteType").value = misrouteTO.misrouteType;
		labelChange(misrouteTO.misrouteType);
		if (!isNull(misrouteTO.consignmentTypeTO)) {
			document.getElementById("consgTypeName").value = misrouteTO.consignmentTypeTO.consignmentId
					+ "#" + misrouteTO.consignmentTypeTO.consignmentName;

		}

		// Grid Details
		for ( var i = 0; i < misrouteTO.misrouteDetailsTO.length; i++) {
			document.getElementById("scannedGridItemNo" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].scannedItemNo;
			document.getElementById("origin" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].origin;
			document.getElementById("remarks" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].remarks;
			document.getElementById("manifestMappedEmbeddeId" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].mapEmbeddedManifestId;
			getDomElementById("consgManifestedIds" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].consgManifestedId;
			if (misrouteTO.misrouteType == CONSIGNMENT) {
				if (misrouteTO.consignmentTypeTO.consignmentId == 1) {
					document.getElementById("Pincode" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].pincode;
					var splitweightGm = misrouteTO.misrouteDetailsTO[i].actualWeight
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
				} else if (misrouteTO.consignmentTypeTO.consignmentId == 2) {

					document.getElementById("scannedGridItemNo" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].scannedItemNo;
					document.getElementById("origin" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].origin;
					document.getElementById("noOfPieces" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].noOfPieces;
					document.getElementById("Pincode" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].pincode;
					document.getElementById("CnContent" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].cnContent;
					document.getElementById("paperwork" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].paperWork;
					document.getElementById("scannedItemId" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].scannedItemNo;
					if (!isNull(misrouteTO.misrouteDetailsTO[i].insuredBy)
							|| !isNull(misrouteTO.misrouteDetailsTO[i].insurancePolicyNo)) {
						document.getElementById("insurance" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].insuredBy
								+ "/"
								+ misrouteTO.misrouteDetailsTO[i].insurancePolicyNo;
					}
					document.getElementById("remarks" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].remarks;

					var splitweightGm = misrouteTO.misrouteDetailsTO[i].actualWeight
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

				}
			} else if (misrouteTO.misrouteType == MASTER_BAG) {

				var splitweightGm = misrouteTO.misrouteDetailsTO[i].actualWeight
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
			} else {
				document.getElementById("scannedGridItemNo" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].scannedItemNo;
				document.getElementById("origin" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].origin;
				document.getElementById("noOfPieces" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].noOfPieces;
				document.getElementById("Pincode" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].pincode;
				document.getElementById("CnContent" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].cnContent;
				document.getElementById("paperwork" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].paperWork;
				document.getElementById("scannedItemId" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].scannedItemNo;
				if (!isNull(misrouteTO.misrouteDetailsTO[i].insuredBy)
						|| !isNull(misrouteTO.misrouteDetailsTO[i].insurancePolicyNo)) {
					document.getElementById("insurance" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].insuredBy
							+ "/"
							+ misrouteTO.misrouteDetailsTO[i].insurancePolicyNo;
				}
				document.getElementById("remarks" + (i + 1)).value = misrouteTO.misrouteDetailsTO[i].remarks;

				var splitweightGm = misrouteTO.misrouteDetailsTO[i].actualWeight
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
			}
			if (i < ((misrouteTO.misrouteDetailsTO.length) - 1)) {
				addRow((i + 1));
			}
		}
		disableHeaderDropDown();
		disableForSearch();
		disableAllCheckBox();

	}
	// to prepare Iframe for print
	/*
	 * var manifestNo = document.getElementById("misrouteNumber"); var
	 * loginOffceID = document.getElementById('loginOfficeId').value; var
	 * manifestType = document.getElementById("ManifestDirection").value; url =
	 * "./misroute.do?submitName=printMisrouteDetails&misroutNo=" +
	 * manifestNo.value + "&loginOfficeId=" + loginOffceID + "&manifestType=" +
	 * manifestType; printUrl = url; printIframe(printUrl);
	 */
}

function printIframe(printUrl) {
	showProcessing();
	document.getElementById("iFrame").setAttribute('src', printUrl);
	hideProcessing();
}

/* disabling the fields after search */
function disableHeaderDropDown() {

	document.getElementById("misrouteNumber").readOnly = true;
	document.getElementById("manifestType").disabled = true;
	document.getElementById("regionId").disabled = true;
	document.getElementById("stationId").disabled = true;
	document.getElementById("misrouteType").disabled = true;
	document.getElementById("consgTypeName").disabled = true;

}

/* enabling the fields after save */
function enableHeaderDropDown() {
	if (document.getElementById("misrouteNumber").disabled == true) {
		document.getElementById("misrouteNumber").disabled = false;
	}
	if (document.getElementById("misrouteNumber").disabled == true) {
		document.getElementById("misrouteNumber").disabled = false;
	}
	if (document.getElementById("manifestType").disabled == true) {
		document.getElementById("manifestType").disabled = false;
	}
	if (document.getElementById("regionId").disabled == true) {
		document.getElementById("regionId").disabled = false;
	}
	if (document.getElementById("stationId").disabled == true) {
		document.getElementById("stationId").disabled = false;
	}
	if (document.getElementById("misrouteType").disabled == true) {
		document.getElementById("misrouteType").disabled = false;
	}

	var misrouteType = document.getElementById("misrouteType").value;
	if (misrouteType == CONSIGNMENT || misrouteType == PACKET
			|| misrouteType == BAG) {
		if (document.getElementById("consgTypeName").disabled == true) {
			document.getElementById("consgTypeName").disabled = false;
		}
	}
}
/* disabling the fields after save */
function disableForSave() {
	disableAllCheckBoxGridElement();
	disableHeaderDropDown();
	buttonDisabled("Save", "btnintform");
	buttonDisabled("Delete", "btnintform");
	buttonDisabled("Cancel", "btnintform");
	jQuery("#" + "Print").removeClass("btnintformbigdis");
	jQuery("#" + "Print").addClass("btnintform");
	jQuery("#" + "Save").addClass("btnintformbigdis");
	jQuery("#" + "Delete").addClass("btnintformbigdis");
	jQuery("#" + "Cancel").addClass("btnintformbigdis");

}

function disableForSearch() {
	inputDisable();
	dropdownDisable();
	buttonDisabled("Save", "btnintform");
	buttonDisabled("Delete", "btnintform");
	disableAllCheckBoxGridElement();
	document.getElementById("misrouteNumber").disabled = false;
	document.getElementById("misrouteNumber").readOnly = false;
	buttonEnabled("Print", "btnintform");
	buttonEnabled("Find", "btnintform");
	jQuery("#" + "Save").addClass("btnintformbigdis");
	jQuery("#" + "Delete").addClass("btnintformbigdis");

}

function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintformbigdis");
}

/**
 * To enable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintform");
}

/* disabling the checkboxes & gris elements */
function disableAllCheckBoxGridElement() {
	getDomElementById("checked").disabled = true;
	var table = document.getElementById("misrouteGrid");
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(8);
		getDomElementById("checkBox" + rowId).disabled = true;
		document.getElementById("scannedGridItemNo" + rowId).disabled = true;
		document.getElementById("remarks" + rowId).disabled = true;
	}
}
/* ***This function is use to cancel the misroute details**** */
function cancelMisrouteDetails() {
	if (promptConfirmation("cancel")) {
		document.misrouteForm.action = "/udaan-web/misroute.do?submitName=viewMisroute&office="
				+ officeType;
		document.misrouteForm.submit();

	}
}

function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	// addOptionTODropDown(selectId, selectOption, "");
}

function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

function checkGridEmpty(obj) {

	var Id = obj.id;

	if (Id == "misrouteType") {
		newMisrouteType = obj.value;
	}
	if (Id == "regionId") {
		newDestRegion = obj.value;
	}
	if (Id == "cityId") {
		newDestCity = obj.value;
	}
	if (Id == "consgTypeName") {
		newConsgType = obj.value;
	}else{
		newConsgType = null; // Resetting consignment type drop down or else it is considering earlier loaded value.
	}
	if (Id == "stationId") {
		newDestOffice = obj.value;
	}
	row = 1;
	var scannedITemId = getDomElementById("scannedGridItemNo" + row).value;

	if (!isNull(scannedITemId)) {
		isFilled = true;
		isConfirmChanges = confirm("Consignment Type/Misroute Type already selected.Do you want to change again?");

	} else {
		isConfirmChanges = true;
	}

	if (isConfirmChanges) {
		isRegion = false;
		jQuery("#misrouteType").val(newMisrouteType);
		if (getDomElementById("regionId").disabled == false) {
			jQuery("#regionId").val(newDestRegion);
		}
		jQuery("#cityId").val(newDestCity);
		jQuery("#consgTypeName").val(newConsgType);
		jQuery("#stationId").val(newDestOffice);

		deleteRow();
	} else {
		isRegion = true;
		jQuery("#misrouteType").val(misrouteType);
		jQuery("#regionId").val(destRegion);
		jQuery("#cityId").val(destCity);
		jQuery("#consgTypeName").val(consgType);
		jQuery("#stationId").val(destOffice);
		if (obj.value == MASTER_BAG || obj.value == PACKET) {
			document.getElementById("consgTypeName").disabled = true;
		}

		return false;
	}

	misrouteType = newMisrouteType;
	destRegion = newDestRegion;
	destCity = newDestCity;
	consgType = newConsgType;
	destOffice = newDestOffice;

}

/* validates the format of misroute manifest number */

function validateMisrouteNumber(manifestNoObj) {
	var regionCode = manifestNoObj.value.substring(0, 3);
	var loginCityCode = (getDomElementById("loginCityCode").value)
			.toUpperCase();
	var seriesType = getDomElementById("seriesType").value;
	var officeCode = getDomElementById("officeCodeProcess").value;
	var manifestScanlevel = "H";
	var numpattern = /^[0-9]{3,20}$/;
	var isValidReturnVal = true;
	var fourthCharB = /^[B]$/;
	var letter4 = manifestNoObj.value.substring(3, 4);
	if (!isNull(manifestNoObj.value)) {

		if (numpattern.test(manifestNoObj.value.substring(3))) {
			seriesType = OGM_SERIES;
			getDomElementById("bagLockNo").disabled = true;
		}
		if (numpattern.test(manifestNoObj.value.substring(4))
				&& (letter4.match(fourthCharB))) {
			seriesType = BPL_SERIES;
			getDomElementById("bagLockNo").disabled = false;
		}

		if (manifestNoObj.value.length < 10 || manifestNoObj.value.length > 10) {
			alert('Manifest No. must contain atleast 10 characters');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		} else if (regionCode.toUpperCase() != loginCityCode.toUpperCase()) {

			alert('Manifest does not belong to this city');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		}

		else if ((numpattern.test(manifestNoObj.value.substring(3)))
				|| (numpattern.test(manifestNoObj.value.substring(4)))
				&& (letter4.match(fourthCharB))) {
			isValidReturnVal = true;
		} else {
			alert('Manifest Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
		if (manifestScanlevel == 'H' && isValidReturnVal == true)
			stockValidation(manifestNoObj, loginCityCode, regionCode,
					officeCode, manifestScanlevel, seriesType);

		if (isValidReturnVal == true && manifestScanlevel == 'H') {
			var manifestNo = manifestNoObj.value;
			var loginOffceID = getDomElementById('loginOfficeId').value;
			var manifestProcessCode = getDomElementById('processCode').value;
			url = './misroute.do?submitName=isManifestExist&manifestNo='
					+ manifestNo + "&loginOfficeId=" + loginOffceID
					+ "&manifestProcessCode=" + manifestProcessCode;
			ajaxCallWithoutForm(url, printIsExist);

		}

	} else {
		isValidReturnVal = false;
	}

	return isValidReturnVal;
}

function stockValidation(stockItemNoObj, loginCityCode, regionCode, officeCode,
		manifestScanlevel, seriesType) {
	var stockItemNo = stockItemNoObj.value;
	var loginOfficeId = getDomElementById('loginOfficeId').value;
	var processCode = getDomElementById('processCode').value;
	var regionId = getDomElementById("loginRegionId").value;
	var manifestDirection = getDomElementById("ManifestDirection").value;

	url = './misroute.do?submitName=validateManifestNo&loggedinOfficeId='
			+ loginOfficeId + "&loginCityCode=" + loginCityCode
			+ "&seriesType=" + seriesType + "&stockItemNo=" + stockItemNo
			+ "&manifestType=" + manifestDirection + "&regionId=" + regionId
			+ "&manifestScanlevel=" + manifestScanlevel + "&processCode="
			+ processCode + "&regionCode=" + regionCode + "&officeCode="
			+ officeCode;

	jQuery.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		success : function(data) {
			printManifestDetails(data, stockItemNoObj);
		}
	});
}

function printManifestDetails(responseText, stockItemNoObj) {
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
			stockItemNoObj.value = "";
			setTimeout(function() {
				stockItemNoObj.focus();
			}, 10);
			return false;
		}
	}

}

function printIsExist(ajaxRes) {
	// hideProcessing();
	if (!isNull(ajaxRes)) {
		if (isJsonResponce(ajaxRes)) {
			var manifestNoObj = document.getElementById("misrouteNumber");
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	}
	return true;
}

function isJsonResponce(ObjeResp) {
	var responseText = null;
	responseText = ObjeResp;
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			return true;
		}
	}
	return false;
}

function deleteTableRow() {
	try {
		var isDeleted = false;
		var isChk = false;
		tableId = "misrouteGrid";
		var table = getDomElementById(tableId);
		var rowId = table.rows.length;

		for ( var i = 0; i < rowId; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {

				isChk = true;
				if (rowId <= 2) {
					alert("Cannot delete all the rows.");
					isDeleted = false;
					jQuery('#misrouteGrid >tbody >tr')
							.each(
									function(i, tr) {
										var isChecked = jQuery(this).find(
												'input:checkbox')
												.is(':checked');
										if (isChecked) {

											$(this).closest('tr').removeClass(
													'gradeAgrey');

											for ( var i = 0; i < rowCount; i++) {
												if (!isNull(document
														.getElementById("checkBox"
																+ i)))
													document
															.getElementById("checkBox"
																	+ i).checked = false;
											}
										}

										else {
											$(this).closest('tr').addClass(
													'gradeAgrey');
										}
									});
					break;
				}
				deleteRowTable(tableId, i - 1);
				rowId--;
				i--;
				isDeleted = true;
			}

		}
		if (!isChk) {
			alert("Please select the consignments/Manifest to delete");
		} else {
			// Updating serial no
			updateSrlNo(tableId);
			if (isDeleted) {
				alert("Rows deleted successfully.");
			}
		}
	} catch (e) {
		alert(e);
	}
}

function selectAllCheckBox() {
	var table = document.getElementById("misrouteGrid");
	var tableRowCount = table.rows.length;

	if (getDomElementById("checked").checked == true) {
		for ( var i = 1; i < tableRowCount; i++) {
			getDomElementById("checkBox" + i).checked = true;
		}
	} else {
		for ( var i = 1; i < tableRowCount; i++) {
			getDomElementById("checkBox" + i).checked = false;
		}
	}
}

function disableAllCheckBox() {
	var table = document.getElementById("misrouteGrid");
	var tableRowCount = table.rows.length;
	getDomElementById("checked").disabled = true;
	for ( var i = 1; i < tableRowCount; i++) {
		getDomElementById("checkBox" + i).disabled = true;
	}
}

function deleteRow() {
	try {

		var table = document.getElementById("misrouteGrid");
		var tableRowCount = table.rows.length;

		for ( var i = 1; i < tableRowCount; i++) {

			deleteRowTable("misrouteGrid", i - 1);
			tableRowCount--;
			i--;
		}

		rowCount = 1;
		fnClickAddRow();
	} catch (e) {
		alert(e);
	}

}

/**
 * This Method Deleted the selected row from the Grid
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRowTable(misrouteGrid, rowIndex) {
	var oTable = $('#' + misrouteGrid).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("scannedGridItemNo" + rowId);
	return callEnterKey(e, focusId);

}

function getValuemisroutType(obj) {
	misroutType = obj.value;

}
function getValueconsgType(obj) {
	consgType = obj.value;
}
function getValueregion(obj) {
	region = obj.value;
}
function getValuestation(obj) {
	station = obj.value;
}

function clearDropDownValueCity() {

	if (isConfirmChanges) {
		clearDropDownList("cityId");
		clearDropDownList("stationId");

	}
}
function clearDropDownValueOffice() {

	if (isConfirmChanges) {
		clearDropDownList("stationId");

	}
}
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");

}

/* @Desc:For Adding option to the dropdown */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

function clearRow(rowId) {
	document.getElementById("scannedGridItemNo" + rowId).value = "";
	document.getElementById("origin" + rowId).value = "";
	document.getElementById("Pincode" + rowId).value = "";
	document.getElementById("actualWeight" + rowId).value;

}

function printMisroute() {

	/*
	 * var misroutNo = document.getElementById("misrouteNumber"); if
	 * (isNull(misroutNo.value)) { alert("Please Enter Misroute No.");
	 * document.getElementById("misrouteNumber").value = "";
	 * document.getElementById("misrouteNumber").focus(); return false; }
	 * 
	 * if (!validateMisrouteNumber(misroutNo)) { return; }
	 * 
	 * var confirm1 = confirm("Do you want to Print Misroute Details!"); if
	 * (confirm1) { printMisrouteDetails(); } if (!confirm1) { cancelPage(); }
	 */
	if (confirm("Do you want to print?")) {
		var misroutNo = document.getElementById("misrouteNumber");
		if (!isNull(misroutNo.value)) {
			printMisrouteDetails();
			/*
			 * window.frames['iFrame'].focus(); window.frames['iFrame'].print();
			 */
		} else {
			alert("Enter valid Misroute No.");
		}
	}

}

function printMisrouteDetails() {

	var manifestNo = document.getElementById("misrouteNumber");
	var loginOffceID = document.getElementById('loginOfficeId').value;
	var manifestType = document.getElementById("ManifestDirection").value;
	if (!isNull(manifestNo.value) && !isNull(loginOffceID)
			&& !isNull(manifestType)) {
		url = "./misroute.do?submitName=printMisrouteDetails&misroutNo="
				+ manifestNo.value + "&loginOfficeId=" + loginOffceID
				+ "&manifestType=" + manifestType;
		var w = window
				.open(url, 'myPopUp',
						'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
		/*
		 * document.misrouteForm.action = url; document.misrouteForm.submit();
		 */
	}

}

function cancelPage() {
	var url = "./misroute.do?submitName=viewMisroute";
	window.location = url;
}

/** *To get the inManifestd Consignment details******** */
function getInManifestConsDtls(consObj, consignmentType) {
	var consNo = consObj.value;
	var consId = getRowId(consObj, "scannedGridItemNo");
	ROW_COUNT = consId;
	// showProcessing();

	url = './misroute.do?submitName=getInManifestConsgDtls&consignmentNo='
			+ consNo + "&consignmentType=" + consignmentType;

	ajaxCallWithoutForm(url, populateConsDetails);
}

function validateBagLockNo(bagLockObj, manifestScanLevel) {
	if (!isNull(bagLockObj.value)) {
		if (bagLockObj.value.trim().length != 8) {
			alert("Baglock No. must contain atleast 8 characters");
			bagLockObj.value = "";
			setTimeout(function() {
				bagLockObj.focus();
			}, 10);
			return false;
		} else if (!isNull(bagLockObj.value)) {
			var url = "./misroute.do?submitName=isValiedBagLockNo&bagLockNo="
					+ bagLockObj.value;
			jQuery.ajax({
				url : url,
				type : "GET",
				dataType : "json",
				success : function(data) {
					isvalidateBagLockNo(data, bagLockObj, manifestScanLevel);
				}
			});
		}
	}
}

function isvalidateBagLockNo(data, bagLockObj, manifestScanLevel) {
	hideProcessing();
	var responseText = data;
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			bagLockObj.value = "";
			setTimeout(function() {
				bagLockObj.focus();
			}, 10);
			return false;
		} else {
			bagLockAndStockValidation(bagLockObj, manifestScanLevel);
		}

	}

}

function bagLockAndStockValidation(bagLockObj, manifestScanLevel) {
	var seriesType = BAGLOCK_SERIES;
	var regionCode = getDomElementById("loginRegionCode").value;
	var loginCityCode = (getDomElementById("loginCityCode").value)
			.toUpperCase();
	var officeCode = getDomElementById("officeCodeProcess").value;

	if (!isNull(bagLockObj.value)) {
		if (bagLockObj.value.substring(0, 1).match(letters)) {
			if (bagLockObj.value.substring(0, 1) == regionCode
					&& numpattern.test(bagLockObj.value.substring(1))) {
				stockValidation(bagLockObj, loginCityCode, regionCode,
						officeCode, manifestScanLevel, seriesType);

			} else {
				alert('Baglock No. format is not correct');
				bagLockObj.value = "";
				setTimeout(function() {
					bagLockObj.focus();
				}, 8);
				return false;
			}
		} else {
			alert('Baglock No. format is not correct');
			bagLockObj.value = "";
			setTimeout(function() {
				bagLockObj.focus();
			}, 8);
			return false;
		}
	}
}

function enterKeyForMisrouteType(event, manifestTypeObj) {
	var misrouteTpyObjVal = document.getElementById('misrouteType').value;
	if (misrouteTpyObjVal == MASTER_BAG || misrouteTpyObjVal == PACKET) {
		if (getDomElementById("bagLockNo").disabled == true) {
			return callFocusEnterKey(event);
		} else {
			return callEnterKey(event, document.getElementById('bagLockNo'));
		}
	} else {
		return callEnterKey(event, document.getElementById('consgTypeName'));
	}
}

function enterKeyForConsgType(event, manifestTypeObj) {
	if (getDomElementById("bagLockNo").disabled == true) {
		return callFocusEnterKey(event);
	} else {
		return callEnterKey(event, document.getElementById('bagLockNo'));
	}
}

function callEnterForRegion(event) {
	if (getDomElementById("regionId").disabled == true) {
		return callEnterKey(event, document.getElementById('cityId'));
	} else {
		return callEnterKey(event, document.getElementById('regionId'));
	}
}

function OnlyAlphabetsAndNosAndEnter(event) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = event.which; // firefox
	// alert("charCode"+charCode);
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || (charCode >= 48 && charCode <= 57)
			|| charCode == 0) {

		return true;

	} else if (charCode == 13) {
		if (getDomElementById("regionId").disabled == true) {
			return callEnterKey(event, document.getElementById('cityId'));
		} else {
			return callEnterKey(event, document.getElementById('regionId'));
		}
	} else {
		return false;
	}
	return false;
}

function fnGetManifestNoType(){
	var manifestNoObjVal = getValueByElementId("misrouteNumber");
	if(!isNull(manifestNoObjVal)){
		var letter4 = manifestNoObjVal.substring(3, 4);
		var fourthCharB = /^[B]$/;
		var fourthCharM = /^[M]$/;
		var numpattern = /^[0-9]{3,20}$/;
		if (letter4.match(fourthCharB)){
			return BAG;
		}else if(letter4.match(fourthCharM)) {
			return MASTER_BAG;
		}else if(numpattern.test(manifestNoObjVal.substring(3))){
			return PACKET;
		}else{
			return null;
		}
	}	
}

function fnValidateMisrouteByMnfstNo(){
	var manifestNoType = fnGetManifestNoType();
	getDomElementById("misrouteType").value = "0";
	getDomElementById("consgTypeName").value = "0";
	
	if (manifestNoType == BAG){
		$('option[value=C]').prop('disabled', false);
		$('option[value=P]').prop('disabled', false);
		$('option[value=B]').prop('disabled', false);
		$('option[value=M]').prop('disabled', true);
	}else if(manifestNoType == MASTER_BAG) {		
		$('option[value=C]').prop('disabled', true);
		$('option[value=P]').prop('disabled', false);
		$('option[value=B]').prop('disabled', false);
		$('option[value=M]').prop('disabled', false);
	}else if(!isNull(manifestNoType)){
		$('option[value=C]').prop('disabled', false);
		$('option[value=P]').prop('disabled', false);
		$('option[value=B]').prop('disabled', true);
		$('option[value=M]').prop('disabled', true);		
	}else{
		$('option[value=C]').prop('disabled', false);
		$('option[value=P]').prop('disabled', false);
		$('option[value=B]').prop('disabled', false);
		$('option[value=M]').prop('disabled', false);
	}
}

function fnGetConsgTypeByMnfstAndMsrtType(misrouteType){
	var manifestNoType = fnGetManifestNoType();
	getDomElementById("consgTypeName").value = "0";
	
	if(manifestNoType == PACKET && misrouteType == CONSIGNMENT){
		$('option[value=1#Document]').prop('disabled', false);
		$('option[value=2#Parcel]').prop('disabled', true);		
	}else if(manifestNoType == BAG && misrouteType == CONSIGNMENT){
		$('option[value=1#Document]').prop('disabled', true);
		$('option[value=2#Parcel]').prop('disabled', false);		
	}else{
		$('option[value=1#Document]').prop('disabled', false);
		$('option[value=2#Parcel]').prop('disabled', false);	
	}
}
