/**
 * setDefaultVal
 * 
 * @param rowCount
 * @author sdalli
 */
function setDefaultVal(rowCount) {
	document.getElementById("consgNo0").focus();
	if (rowCount > 0) {
		var cnDetails = window.opener.document.getElementById("childCns"
				+ rowCount).value;
		var keyValList = cnDetails.split("#");
		if (window.opener.parent.PIECES_OLD == window.opener.parent.PIECES_NEW) {
			for ( var i = 0; i < keyValList.length; i++) {
				var keyValPair = keyValList[i].split(",");
				if (!isNull(keyValPair[1])) {
					var weghtValPair = keyValPair[1].split(".");
					document.getElementById("consgNo" + i).value = $.trim(keyValPair[0]);
					document.getElementById("weight" + i).value = $.trim(weghtValPair[0]);
					document.getElementById("weightGm" + i).value = $.trim(weghtValPair[1]);
					weightFormatForGm(document.getElementById("weightGm" + i));
				}
			}
		}
	} else {
		var cnDetails = window.opener.document.getElementById("details").value;
		var keyValList = cnDetails.split("#");
		if (window.opener.parent.PIECES_OLD == window.opener.parent.PIECES_NEW) {
			for ( var i = 0; i < keyValList.length; i++) {
				var keyValPair = keyValList[i].split(",");
				var weghtValPair = keyValPair[1].split(".");
				document.getElementById("consgNo" + i).value = $.trim(keyValPair[0]);
				document.getElementById("weight" + i).value = $.trim(weghtValPair[0]);
				document.getElementById("weightGm" + i).value = $.trim(weghtValPair[1]);
				weightFormatForGm(document.getElementById("weightGm" + i));
			}
		}

	}

};

/**
 * submitVal
 * 
 * @param rows
 * @author sdalli
 */
function submitVal(rows) {
	if (checkForMandatoryField(rows)) {
		if (rowCount > 0) {

			for ( var i = 0; i < rows; i++) {
				var consgNo = document.getElementById("consgNo" + i).value;
				var actWeight = document.getElementById("actWeight" + i).value;
				cndetails = consgNo + "," + actWeight;
				if (details == "") {
					details = cndetails;
				} else {
					details = details + "#" + cndetails;
				}
			}
			window.opener.document.getElementById("childCns" + rowCount).value = details;
			if (process == "MANIFEST") {
				if (!isNull(window.opener.document.getElementById("actWeightKg"
						+ rowCount)))
					window.opener.document.getElementById(
							"actWeightKg" + rowCount).focus();
			} else {
				if (!isNull(window.opener.document.getElementById("cnPincode"
						+ rowCount)))
					window.opener.document.getElementById(
							"cnPincode" + rowCount).focus();
			}
			self.close();
		} else {
			for ( var i = 0; i < rows; i++) {
				var consgNo = document.getElementById("consgNo" + i).value;
				var actWeight = document.getElementById("actWeight" + i).value;
				cndetails = consgNo + "," + actWeight;
				if (details == "") {
					details = cndetails;
				} else {
					details = details + "#" + cndetails;
				}
			}
			window.opener.document.getElementById("details").value = details;
			if (process == "MANIFEST") {
				if (!isNull(window.opener.document
						.getElementById("declaredValue")))
					window.opener.document.getElementById("declaredValue")
							.focus();
			} else {
				if (!isNull(window.opener.document
						.getElementById("contentName")))
					window.opener.document.getElementById("contentName")
							.focus();
			}
			self.close();
		}
	}
}

/**
 * ESCclose
 * 
 * @param evt
 * @author sdalli
 */
function ESCclose(evt) {
	if (evt.keyCode == 27) {
		if (rowCount > 0) {
			window.opener.document.getElementById("noOfPieces" + rowCount)
					.focus();
		} else {
			window.opener.document.getElementById("pieces").focus();
		}
		window.close();
	}

}

/**
 * cancelVal
 * 
 * @author sdalli
 */
function cancelVal(rows) {
	var used= window.opener.usedConsignments;
	
	if (process == "BOOK") {
		for ( var i = 0; i < rows; i++) {
			var consgNumber = document.getElementById("consgNo" + i).value;
			if (!isNull(consgNumber)) {
				for ( var j = 0; j < used.length; j++) {
					if (used[j] == consgNumber) {
						used[j] = null;
					}
				}
			}
		}
	}
	if (rowCount > 0) {
		window.opener.document.getElementById("noOfPieces" + rowCount).focus();
	} else {
		window.opener.document.getElementById("pieces").focus();
	}
	window.opener.usedConsignments=used;
	self.close();
}



function isDuplicateConsignment(consgNumberObj) {
	var isConsgUsed = false;
	var selectedRowId = getRowId(consgNumberObj, "consgNo");
	var cnNo = document.getElementById("consgNo" + selectedRowId).value;
	var usedParentConsignments = window.opener.usedConsignments;
	if (usedParentConsignments != null && usedParentConsignments.length != 0) {
		for ( var i = 0; i < usedParentConsignments.length; i++) {
			if (cnNo != null && cnNo != "" && usedParentConsignments[i] == cnNo) {
				isConsgUsed = true;
			}
		}
		if (!isConsgUsed) {
			usedParentConsignments.push(cnNo);
		}
	}
	window.opener.usedConsignments = usedParentConsignments;
	return isConsgUsed;
}


function childCNProductDiff(consgNoObj, processCode){
	var selectedRowId = getRowId(consgNoObj, "consgNo");
	var cnNo = document.getElementById("consgNo" + selectedRowId).value;
	var parentCnNo="";
	var usedParentConsignments = window.opener.usedConsignments;
	var childSeries = cnNo.charAt(4);
	if (processCode == "MANIFEST") {
		parentCnNo = window.opener.document.getElementById("consigntNo"
				+ rowCount).value;
	}
	if (processCode == "BOOK") {
		if (rowCount > 0) {
			parentCnNo = window.opener.document.getElementById("cnNumber"
					+ rowCount).value;
		} else {
			parentCnNo = window.opener.document.getElementById("cnNumber").value;
		}
	}
	var parentSeries=parentCnNo.charAt(4);
	if (childSeries != parentSeries) {
		for ( var j = 0; j < usedParentConsignments.length; j++) {
			if (usedParentConsignments[j] == cnNo) {
				usedParentConsignments[j] = null;
			}
		}
		window.opener.usedConsignments = usedParentConsignments;
		return false;
	}else{
		return true;
	}
	
	
}
/**
 * validateConsignment
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignment(consgNumberObj, processCode, rowCount) {
	var bookingType = "";
	var oldchildCn = new Array();
	var rowNo = getRowId(consgNumberObj, "consgNo");
	if (processCode == "MANIFEST") {
		bookingType = window.opener.document.getElementById("bookingType"
				+ rowCount).value;
	}
	if (processCode == "BOOK") {
		bookingType = window.opener.document.getElementById("bookingType").value;
		oldchildCn= window.opener.parent.OLD_CHILD_CN;
	}

	// if (rowNo > 0) {

	if (consgNumberObj.value != null && consgNumberObj.value != "") {
		var consgNumber = consgNumberObj.value;
		if (consgNumber.length != 12) {
			alert("Consingment length should be 12 character");
			document.getElementById(consgNumberObj.id).value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;
		}
		if (isDuplicateConsignment(consgNumberObj)) {
			alert("CN Number is already in Use.");
			consgNumberObj.value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;
		}
				
		if (!childCNProductDiff(consgNumberObj, processCode)) {
			alert("Child CN Series should be the same series as Parent CN.");
			consgNumberObj.value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;

		}
		var isExists = false;
		if (!isNull(bookingType)) {
			if (oldchildCn.length != 0 && processCode == "BOOK") {
				for ( var i = 0; i <= oldchildCn.length; i++) {
					if (oldchildCn[i] == consgNumber) {
						isExists = true;
						break;
					}
				}
				if (isExists == false) {
					showProcessing();
					url = './cashBooking.do?submitName=validateConsignment&consgNumber='
							+ consgNumber + "&bookingType=" + bookingType;
					jQuery.ajax({
						url : url,
						success : function(req) {
							printCallBackConsignment(req, rowNo, consgNumber);
						}
					});
				}

			} else {
				url = './cashBooking.do?submitName=validateConsignment&consgNumber='
						+ consgNumber + "&bookingType=" + bookingType;
				jQuery.ajax({
					url : url,
					success : function(req) {
						printCallBackConsignment(req, rowNo, consgNumber);
					}
				});
			}
		}
	} else if (isNull(consgNumberObj.value)){
		alert("Please Enter Consingment Number");
		setTimeout(function() {
			document.getElementById(consgNumberObj.id).focus();
		}, 10);
		return;
	}


}
/**
 * printCallBackConsignment
 * 
 * @param data
 * @param rowNo
 * @author sdalli
 */
function printCallBackConsignment(data, rowNo, consgNumber) {
	hideProcessing();
	if (rowCount > 0) {

		var response = data;
		if (response != null) {
			var cnValidation = eval('(' + response + ')');
			if (cnValidation != null && cnValidation != "") {
				if (cnValidation.isValidCN == "N") {
					alert(cnValidation.errorMsg);
					document.getElementById("consgNo" + rowNo).value = "";
					setTimeout(function() {
						document.getElementById("consgNo" + rowNo).focus();
					}, 10);
				} else if (cnValidation.isConsgExists == "Y") {
					alert(cnValidation.errorMsg);
					document.getElementById("consgNo" + rowNo).value = "";
					setTimeout(function() {
						document.getElementById("consgNo" + rowNo).focus();
					}, 10);
				} else {
					usedConsignments[rowNo] = consgNumber;
				}
			}
		}

	} else {

		var response = data;
		if (response != null) {
			var cnValidation = eval('(' + response + ')');
			if (cnValidation != null && cnValidation != "") {
				if (cnValidation.isValidCN == "N") {
					alert(cnValidation.errorMsg);
					document.getElementById("consgNo" + rowNo).value = "";
					setTimeout(function() {
						document.getElementById("consgNo" + rowNo).focus();
					}, 10);
				} else if (cnValidation.isConsgExists == "Y") {
					alert(cnValidation.errorMsg);
					document.getElementById("consgNo" + rowNo).value = "";
					setTimeout(function() {
						document.getElementById("consgNo" + rowNo).focus();
					}, 10);
				} else {
					usedConsignments[0] = consgNumber;
				}
			}
		}
	}
}
/**
 * weightFormatForGm
 * 
 * @param weightObj
 * @author sdalli
 */
function weightFormatForGm(weightObj) {
	var rowNo = getRowId(weightObj, "weightGm");
	var gmValue = document.getElementById("weightGm" + rowNo).value;
	if (gmValue.length == 0) {
		document.getElementById("weightGm" + rowNo).value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm" + rowNo).value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm" + rowNo).value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	var kgValue = document.getElementById("weight" + rowNo).value;
	if (kgValue.length == 0) {
		document.getElementById("weight" + rowNo).value += "0";
		kgValue += "0";
	}
	var tempVal = (kgValue + "." + gmValue);
	var finalWeight = parseFloat(tempVal).toFixed(3);
	if (isEmptyWeight(finalWeight)){
		alert ("Please Enter Weight");
		document.getElementById("weight" + rowNo).value = "";
		document.getElementById("weightGm" + rowNo).value = "";
		document.getElementById("actWeight" + rowNo).value ="";
		setTimeout(function() {
			document.getElementById("weight" + rowNo).focus();
		}, 10);
	} else {
		document.getElementById("actWeight" + rowNo).value = tempVal;
	}
	

}


function weightFormatForGmOnSubmit(rowNo) {
	var gmValue = document.getElementById("weightGm" + rowNo).value;
	if (gmValue.length == 0) {
		document.getElementById("weightGm" + rowNo).value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm" + rowNo).value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm" + rowNo).value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	var kgValue = document.getElementById("weight" + rowNo).value;
	if (kgValue.length == 0) {
		document.getElementById("weight" + rowNo).value += "0";
		kgValue += "0";
	}
	var tempVal = (kgValue + "." + gmValue);
	var finalWeight = parseFloat(tempVal).toFixed(3);
	if (isEmptyWeight(finalWeight)){
		alert ("Please Enter Weight");
		document.getElementById("weight" + rowNo).value = "";
		document.getElementById("weightGm" + rowNo).value = "";
		document.getElementById("actWeight" + rowNo).value ="";
		setTimeout(function() {
			document.getElementById("weight" + rowNo).focus();
		}, 10);
	} else {
		document.getElementById("actWeight" + rowNo).value = tempVal;
	}
	

}
/**
 * check mandatory field(s) before submit
 * 
 * @param rows
 * @returns {Boolean}
 * @author hkansagr
 */
function checkForMandatoryField(rows) {
	for ( var i = 0; i < rows; i++) {
		if (!validateRowDtls(i)) {
			return false;
		} else {
			weightFormatForGmOnSubmit(i);
		}
	}
	return true;
}

/**
 * validate the row values by its Id
 * 
 * @param rowId
 * @returns {Boolean}
 * @author hkansagr
 */
function validateRowDtls(rowId) {
	var consgNo = getDomElementById("consgNo" + rowId);
	var weight = getDomElementById("weight" + rowId);
	var weightGm = getDomElementById("weightGm" + rowId);
	var tempVal = (weight.value + "." + weightGm.value);
	tempVal=parseFloat(tempVal);
	var atLine = "at line:" + (rowId + 1);
	if (isNull(consgNo.value)) {
		alert("Please enter consignment number " + atLine);
		consgNo.value = "";
		setTimeout(function() {
			consgNo.focus();
		}, 10);
		return false;
	}
	if (isEmptyWeight(tempVal) || isNaN(tempVal)) {// if Kgs is Null then Gms should not be
									// Null
		alert("Please enter weight  " + atLine);
		weightGm.value = "";
		weight.value = "";
		setTimeout(function() {
			weight.focus();
		}, 10);
		return false;
	}
	
	return true;
}

/**
 * onlyNumberAndEnterKeyNav
 * 
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 * @author sdalli
 */
function onlyNumberAndEnterKeyNav(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

function onlyNumberAndEnterKeyNav1(e, noOfrows, currentRow) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		var currentRow1 = parseInt(currentRow) + 1;
		if (currentRow1 == noOfrows) {
			document.getElementById("submit").focus();
		} else {
			var nextRowId = parseInt(currentRow) + 1;
			document.getElementById("consgNo" + nextRowId).focus();
		}

		return false;
	} else {
		return true;
	}
}

function enterKeyValidationOnConsignment (e, weightObj, cnObj){
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (isNull(cnObj.value)){
			alert ("Please Enter Consignment Number");
			cnObj.focus();
		} else {
			weightObj.focus();
		}
		return false;
	} else {

	}
}

function convertDOMObjValueToUpperCase(obj) {
	var domValue = $.trim(obj.value);
	obj.value = domValue.toUpperCase();
}
