var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

function weightFormatForGm(weightObj) {
	var rowNo = getRowId(weightObj, "weightGm");
	ROW_COUNT = rowNo;

	var gmValue = document.getElementById("weightGm" + ROW_COUNT).value;
	if (gmValue.length == 0) {
		document.getElementById("weightGm" + ROW_COUNT).value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm" + ROW_COUNT).value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm" + ROW_COUNT).value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	var kgValue = document.getElementById("weight" + ROW_COUNT).value;
	if (kgValue.length == 0) {
		document.getElementById("weight" + ROW_COUNT).value += "0";
		kgValue += "0";
	}
	var tempVal = (kgValue + "." + gmValue);
	document.getElementById("weights" + ROW_COUNT).value = tempVal;
}

function setTotalWeight(rowId) {
	var finalWeight = 0;
	var weightInFraction = 0;
	var finalAddedWeight;
	var finalWt = document.getElementById("finalWeight").value;
	var weightKg = document.getElementById("weight" + rowId).value;
	var weightGM = document.getElementById("weightGm" + rowId).value;
	if (!isNull(weightKg) || !isNull(weightGM)) {
		weightInFraction = weightKg + "." + weightGM;
		if (!isNull(weightInFraction)) {
			if (weightKg == 0) {
				finalWeight = (weightInFraction);
			} else {
				finalWeight += parseFloat(weightInFraction);
			}
		}
	}
	if (isNull(finalWt)) {
		document.getElementById("finalWeight").value = finalWeight;
		finalAddedWeight = finalWeight;

	} else {
		finalAddedWeight = parseFloat(finalWeight) + parseFloat(finalWt);
		document.getElementById("finalWeight").value = finalAddedWeight;
	}
	if (!isEmptyWeight(finalAddedWeight)) {
		finalAddedWeight = parseFloat(finalAddedWeight).toFixed(3);
		document.getElementById("finalKgs").value = finalAddedWeight.split(".")[0];
		document.getElementById("finalGms").value = finalAddedWeight.split(".")[1];
	}
}
var numpattern = /^[0-9]{3,20}$/;
var letters = /^[A-Za-z]+$/;
var fourthCharM = /^[M]$/;
var fourthCharB = /^[B]$/;
function isValidManifestNo(manifestNoObj, manifestScanlevel) {

	var isValidReturnVal = true;
	if (!isNull(manifestNoObj.value)) {
		var stockItemNo = manifestNoObj.value;
		var regionCode = manifestNoObj.value.substring(0, 3);
		var loginCityCode = (getDomElementById("loginCityCode").value)
				.toUpperCase();
		var seriesType = getDomElementById("seriesType").value;
		var officeCode = getDomElementById("officeCode").value;
		var manifestCity = manifestNoObj.value.substring(0, 3).toUpperCase();

		if (manifestNoObj.value.length < 10 || manifestNoObj.value.length > 10) {
			alert('Manifest No. must contain atleast 10 characters');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		} else {
			if (manifestCity == loginCityCode) {
				if (!isNull(OGM_SERIES) && seriesType == OGM_SERIES) {
					if (!numpattern.test(manifestNoObj.value.substring(3))) {
						alert('Manifest number format is not correct');
						manifestNoObj.value = "";
						setTimeout(function() {
							manifestNoObj.focus();
						}, 10);
						isValidReturnVal = false;
					}
				} else if (!isNull(MBPL_SERIES) && seriesType == MBPL_SERIES) {
					isValidReturnVal = mbplValidation(manifestNoObj);
				} else if (!isNull(BPL_SERIES) && seriesType == BPL_SERIES) {
					isValidReturnVal = bplValidation(manifestNoObj);
				}
			} else {
				alert('Manifest does not belong to this city');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				isValidReturnVal = false;
			}
		}

		if (manifestScanlevel != 'G' && isValidReturnVal == true)
			stockValidation(manifestNoObj, loginCityCode, regionCode,
					officeCode, manifestScanlevel, seriesType);

	} else {
		// alert('Please enter the Manifest number');
		// manifestNoObj.value = "";
		// setTimeout(function() {manifestNoObj.focus();}, 10);
		isValidReturnVal = false;
	}
	if (isValidReturnVal == true && manifestScanlevel == 'H') {
		var manifestNo = manifestNoObj.value;
		var loginOffceID = getDomElementById('loginOfficeId').value;
		var manifestProcessCode = getDomElementById('processCode').value;
		url = './outManifestParcel.do?submitName=isManifestExist&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID
				+ "&manifestProcessCode=" + manifestProcessCode;
		// showProcessing();
		ajaxCallWithoutForm(url, printIsExist);
		/*
		 * jQuery.ajax({ url : url, type: "POST", success : function(req) {
		 * isValidReturnVal = printIsExist(req); } });
		 */
	}

	return isValidReturnVal;
}

function printIsExist(ajaxRes) {
	// hideProcessing();
	if (!isNull(ajaxRes)) {
		if (isJsonResponce(ajaxRes)) {
			var manifestNoObj = document.getElementById("manifestNo");
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	}
	return true;
}

function bplValidation(manifestNoObj) {
	var letter4 = manifestNoObj.value.substring(3, 4).toUpperCase();
	// if (manifestNoObj.value.substring(0, 4).match(letters)) {
	if (letter4.match(fourthCharB)) {
		if (numpattern.test(manifestNoObj.value.substring(4))) {
			return true;
		} else {
			alert('Manifest Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	} else {
		alert('Format is not correct');
		manifestNoObj.value = "";
		setTimeout(function() {
			manifestNoObj.focus();
		}, 10);
		return false;
	}
	/*
	 * } else { alert('Manifest Format is not correct'); manifestNoObj.value =
	 * ""; setTimeout(function() { manifestNoObj.focus(); }, 10); return false; }
	 */
}

function mbplValidation(manifestNoObj) {
	var numpattern = /^[0-9]{3,20}$/;
	var letters = /^[A-Za-z]+$/;
	var fourthCharM = /^[M]$/;
	//if (manifestNoObj.value.substring(0, 4).match(letters)) {
		var letter4 = manifestNoObj.value.substring(3, 4).toUpperCase();
		if (letter4.match(fourthCharM)) {
			if (numpattern.test(manifestNoObj.value.substring(4))) {
				return true;
			} else {
				alert('Manifest Format is not correct');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				return false;
			}
		} else {
			alert('Manifest Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	}/* else {
		alert('Format is not correct');
		manifestNoObj.value = "";
		setTimeout(function() {
			manifestNoObj.focus();
		}, 10);
		return false;
	}
}*/

function stockValidation(stockItemNoObj, loginCityCode, regionCode, officeCode,
		manifestScanlevel, seriesType) {
	var stockItemNo = stockItemNoObj.value;
	var loginOfficeId = getDomElementById("loginOfficeId").value;
	var regionId = getDomElementById("regionId").value;
	var manifestDirection = getDomElementById("manifestDirection").value;
	// var seriesType = getDomElementById("seriesType").value;
	var processCode = getDomElementById("processCode").value;

	url = './thirdPartyOutManifestDox.do?submitName=validateManifestNo&loggedinOfficeId='
			+ loginOfficeId
			+ "&loginCityCode="
			+ loginCityCode
			+ "&seriesType="
			+ seriesType
			+ "&stockItemNo="
			+ stockItemNo
			+ "&manifestType="
			+ manifestDirection
			+ "&regionId="
			+ regionId
			+ "&manifestScanlevel="
			+ manifestScanlevel
			+ "&processCode="
			+ processCode
			+ "&regionCode="
			+ regionCode
			+ "&officeCode="
			+ officeCode;

	// ajaxCallWithoutForm(url, printManifestDetails);
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
/*
 * var stockIssueInputs = data; if (stockIssueInputs != null) { if
 * (stockIssueInputs != null && stockIssueInputs != "") { if
 * (stockIssueInputs.isIssuedTOParty == "N") { alert(stockIssueInputs.errorMsg);
 * stockItemNoObj.value = ""; setTimeout(function() { stockItemNoObj.focus(); },
 * 10); return false; } else if (stockIssueInputs.isManifested == "Y") {
 * alert(stockIssueInputs.errorMsg); stockItemNoObj.value = "";
 * setTimeout(function() { stockItemNoObj.focus(); }, 10); return false; } } } }
 */

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
			var url = "./bplOutManifestDox.do?submitName=isValiedBagLockNo&bagLockNo="
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
	// var responseText = jsonJqueryParser(data);
	var responseText = data;
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
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
	var regionCode = document.getElementById("regionCode").value;
	var loginCityCode = getDomElementById("loginCityCode").value;
	var officeCode = getDomElementById("officeCode").value;
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

function printCallBackManifest(data) {
	var response = data;
	if (response != null) {
		var manifestValidation = eval('(' + response + ')');
		if (manifestValidation != null && manifestValidation != "") {
			alert(manifestValidation.value);
		}
	}
	jQuery.unblockUI();
}

function isValidWeightTolerance(Obj) {
	var finalWeight = document.getElementById("finalWeight").value;
	if (isNull(finalWeight)) {
		return;
	}
	if (isNANCheck(finalWeight)) {
		finalWeight = 0.0;
	}
	weight = parseFloat(finalWeight);
	var toleranceWeight = parseFloat(maxWeightAllowed)
			+ (parseFloat(maxWeightAllowed) * (parseFloat(maxTolerenceAllowed) / 100));

	if (weight > toleranceWeight) {
		document.getElementById("close").focus();
	} else {

	}
}

function mergeWeighInGmKg(Obj) {
	var finalKgs = document.getElementById("finalKgs").value;
	var finalGms = document.getElementById("finalGms").value;
	var finalWeight = 0.0;
	var weightInFraction = 0;
	if (!isNull(finalKgs) && !isNull(finalGms)) {
		weightInFraction = finalKgs + "." + finalGms;
		finalWeight = parseFloat(weightInFraction);
		document.getElementById("finalWeight").value = finalWeight;
		if (!isNull(finalWeight)) {
			isValidWeightTolerance(Obj);
		}
	}

}

function getThirdPartyName(Obj) {
	if (!isNull(Obj)) {
		var regionId = jQuery("#regionId").val();
		if (!isNull(Obj)) {
			var url = "./thirdPartyBPLOutManifest.do?submitName=getThirdPartyName&partyID="
					+ Obj + "&regionId=" + regionId;
			ajaxCall(url, 'thirdPartyOutManifestDoxForm',
					ajaxRespForBAPartyNames);
		} else {
			clearDropDownList("thirdPartyName");
		}
	}
}

/*
 * function ajaxRespForBAPartyNames(ajaxResp) { if(!isNull(ajaxResp)) { var
 * listOfThirdPartyNameResp = eval('(' + ajaxResp + ')'); var partyNameList =
 * listOfThirdPartyNameResp; clearDropDownList("thirdPartyName");
 * addOptionTODropDown("thirdPartyName",document.createTextNode("--Select--"),"");
 * for ( var i = 0; i < partyNameList.length; i++) {
 * addOptionTODropDown("thirdPartyName", partyNameList[i].businessName,
 * partyNameList[i].vendorId); } hideProcessing(); } else {
 * clearDropDownList("thirdPartyName"); } }
 */

function ajaxRespForBAPartyNames(ajaxResp) {
	if (!isNull(ajaxResp)) {
		// var responseText = jsonJqueryParser(ajaxResp);
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			clearDropDownList('thirdPartyName', '---Select---');
		} else if (responseText != null) {
			// var listOfThirdPartyNameResp = eval('(' + ajaxResp + ')');
			var listOfThirdPartyNameResp = eval(ajaxResp);
			var partyNameList = listOfThirdPartyNameResp;
			var content = document.getElementById('thirdPartyName');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("---SELECT---"));
			content.appendChild(defOption);
			$.each(partyNameList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.vendorId;
				option.appendChild(document.createTextNode(this.businessName));
				content.appendChild(option);
			});
		}
	}
	hideProcessing();
}

function clearDropDownList(selectId, selectOption) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, selectOption, "");
}

/*
 * function addOptionTODropDown(selectId, label, value) { var obj =
 * getDomElementById(selectId); opt = document.createElement('OPTION');
 * opt.value = value; opt.text = label; obj.options.add(opt); }
 */

function addOptionTODropDown(selectId, label, value) {
	/*
	 * var myselect=document.getElementById(selectId); try{ myselect.add(new
	 * Option(label, value), null); //add new option to end of "myselect"
	 * //myselect.add(new Option(label, value), myselect.options[0]); //add new
	 * option to beginning of "myselect" } catch(e){ //in IE, try the below
	 * version instead of add() myselect.add(new Option(label, value)); //add
	 * new option to end of "myselect" //myselect.add(new Option(label, value),
	 * 0); //add new option to beginning of "myselect" }
	 */

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}
function getAllOfficeIds(obj) {
	if (obj.value == 0) {
		document.getElementById("isMulDest").value = "Y";
		// No need to set all the office Ids need to set Hub office Ids only...
		// Its taken care in getAllOffices()..

		/*
		 * var officeIds = ""; for ( var i = 0; i < obj.options.length; i++) {
		 * if(obj.options[i].value !=0 && obj.options[i].value!="") officeIds =
		 * officeIds+ obj.options[i].value + ","; }
		 * document.getElementById("multiDest").value = officeIds;
		 */
	} else {
		document.getElementById("isMulDest").value = "N";
	}
}

// TODO: remove this
function capturedWeightWithParam(data, rowId) {
	var capturedWeight = eval('(' + data + ')');
	// var flag = true;
	if (isNull(capturedWeight) || capturedWeight == -1 || capturedWeight == -2) {
		wmCapturedWeight = capturedWeight;
		// flag = false;
	} else {
		wmCapturedWeight = parseFloat(capturedWeight).toFixed(3);
		cnWeightFromWM = 0.000;
		cnWeightFromWM = (parseFloat(wmCapturedWeight) - parseFloat(capturedWeight))
				.toFixed(3);
		wmCapturedWeight = capturedWeight;
		// weightKgDomEle.value = cnWeight.split(".")[0];
		// weightGmDomEle.value = cnWeight.split(".")[1];
		// flag = true;
	}
	// disableEnableWeightField(flag);
}

// TODO remove this..
function disableEnableWeightField(flag, weightKgDomEle, weightGmDomEle) {
	weightKgDomEle.readOnly = flag;
	weightGmDomEle.readOnly = flag;
}

function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}

/** **************Weighing Machine Integration starts*********************** */
/*
 * author: amimehta used to call the weight reader action and get weight of
 * scanned consg/manifest
 */

var isWeighingMachineConnected = false;

function getWtFromWMForOGM() {
	var url = "weightReader.do";
	jQuery.ajax({
		url : url,
		success : function(req) {
			capturedWeightForManifest(req);
		}
	});
}

function getWtFromWMForTPDX() {
	var url = "weightReader.do";
	jQuery.ajax({
		url : url,
		success : function(req) {
			capturedWeightForInManifestCons(req);
		}
	});
}

function getWtFromWMForManifest() {
	var url = "weightReader.do";
	jQuery.ajax({
		url : url,
		success : function(req) {
			capturedWeightForGrid(req);
		}
	});
}

function capturedWeight(data) {
	var capturedWeight = eval('(' + data + ')');
	if (capturedWeight == -1 || capturedWeight == -2) {
		wmCapturedWeight = capturedWeight;
		isWeighingMachineConnected = false;
	} else {
		wmCapturedWeight = parseFloat(capturedWeight).toFixed(3);
		isWeighingMachineConnected = true;
	}
}

/** **************Weighing Machine Integration End*********************** */

/**
 * isJsonResponce
 * 
 * @param ObjeResp
 * @returns {Boolean}
 */
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

/**
 * To get excat actual weight from WM for out manifest
 * 
 * @param data
 * @param actWeight
 * @param wmWt
 * @returns {String}
 */
function getExcatWeight(data, actWeight, wmWt) {
	var resultWt = "";
	// If WM connected
	if (isWeighingMachineConnected) {
		var capturedWeight = wmWt;
		wmCapturedWeight = parseFloat(wmCapturedWeight);
		if ((isNull(capturedWeight) || capturedWeight == -1
				|| capturedWeight == -2 || capturedWeight == "0.0")
				&& isNull(wmCapturedWeight)) {
			alert("Weight reached to zero.");
			// wmCapturedWeight = 0.0;
		} else {
			wmWeight = parseFloat(wmCapturedWeight)
					- parseFloat(capturedWeight);
			wmWeight = parseFloat(wmWeight).toFixed(3);
			wmCapturedWeight = parseFloat(capturedWeight).toFixed(3);
			if (wmWeight == "0.000" || wmWeight == "0.0" || isNull(wmWeight)
					|| parseFloat(wmWeight) < 0) {
				alert("Weight reached to zero.");
				resultWt = "";
			} else {
				resultWt = wmWeight;
			}
		}
	} else {// If WM not connected
		// If Pickup
		if (data.isPickupCN == "Y") {
			resultWt = "0.0";
		} else {// If Booked
			resultWt = actWeight;
		}
	}
	return resultWt;
}
//Added by  - Shahnaz
function fnEnableDisbleField(inputId, flag){
	$('#'+inputId).attr("readonly", flag);	
	$('#'+inputId).attr("disabled", flag);	
}