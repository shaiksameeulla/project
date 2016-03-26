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
		//Commented : As per the discussion with Project manager and BA
		/*var officeType=window.opener.document.getElementById("loginOfficeType").value;
		if(officeType=="HO"){
			document.getElementById("lcAmount").disabled=false;
			document.getElementById("bankName").disabled=false;			
		}else{
			document.getElementById("lcAmount").disabled=true;
			document.getElementById("bankName").disabled=true;
		}*/
		var lcAmtDetails = window.opener.document.getElementById("lcDetails" + rowCount).value;
		if(!isNull(lcAmtDetails)){
			var keyValList = lcAmtDetails.split("#");
			document.getElementById("lcAmount").value = keyValList[0];
			document.getElementById("bankName").value = keyValList[1];
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
		var bankName = document.getElementById("bankName").value;
		lcAmtDetails = lcAmt + "#" + bankName;			
		window.opener.document.getElementById("lcDetails" + rowCount).value = lcAmtDetails;
		window.opener.document.getElementById("isDataMismatch"+ rowCount).value="Y";
		self.close();
	} 
}
function setToFixed(lcAmtObj){
	if(!isNull(lcAmtObj.value))
		lcAmtObj.value=parseFloat(lcAmtObj.value).toFixed(2);
}