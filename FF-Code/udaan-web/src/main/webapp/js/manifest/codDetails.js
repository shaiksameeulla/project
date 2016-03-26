/**
 * cancelVal
 * 
 * @author sdalli
 */
function cancelVal() {
	self.close();
}

/**
 * ESCclose
 * 
 * @param evt
 * @author sdalli
 */
function ESCclose(evt) {
	if (evt.keyCode == 27)
		window.close();
}

/**
 * setDefaultVal
 * 
 * @param rowCount
 * @author Shahanaz
 */
function setDefaultValues(rowCount) {
	if (rowCount > 0) {
		var lcAmtDetails = window.opener.document.getElementById("lcDetails"
				+ rowCount).value;
		if (!isNull(lcAmtDetails)) {
			var keyValList = lcAmtDetails.split("#");
			document.getElementById("lcAmount").value = keyValList[0];
			document.getElementById("lcAmount").focus();
		} else {
			document.getElementById("lcAmount").focus();
		}
	}
}

/**
 * submitVal
 * 
 * @param rows
 * @author Shahanaz
 */
function submitValues() {
	if (rowCount > 0) {
		var lcAmtDetails = "";
		var lcAmt = document.getElementById("lcAmount").value;
		lcAmtDetails = lcAmt + "#0";
		window.opener.document.getElementById("lcDetails" + rowCount).value = lcAmtDetails;
		window.opener.document.getElementById("isDataMismatch" + rowCount).value = "Y";
		self.close();
	}
}

/**
 * To set to fixed to COD amount
 * 
 * @param lcAmtObj
 */
function setToFixed(lcAmtObj) {
	if (!isNull(lcAmtObj.value))
		lcAmtObj.value = parseFloat(lcAmtObj.value).toFixed(2);
}