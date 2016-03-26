/**
 * setDefaultVal
 *
 * @param rowCount
 * @author sdalli
 */
function setDefaultVal(rowCount) {
	document.getElementById("length").focus();
	if (rowCount > 0) {
		document.getElementById("length").value = window.opener.document
				.getElementById("length" + rowCount).value;
		document.getElementById("breath").value = window.opener.document
				.getElementById("breath" + rowCount).value;
		document.getElementById("height").value = window.opener.document
				.getElementById("height" + rowCount).value;
	} else {
		document.getElementById("length").value = window.opener.document
				.getElementById("length").value;
		document.getElementById("breath").value = window.opener.document
				.getElementById("breath").value;
		document.getElementById("height").value = window.opener.document
				.getElementById("height").value;
	}

}

/**
 * submitVal
 *
 * @returns
 * @author sdalli
 */
function submitVal() {
	var length = document.getElementById('length').value;
	var breath = document.getElementById('breath').value;
	var height = document.getElementById('height').value;

	if (rowCount > 0) {
		var volWeight = parseFloat(length * breath * height / 6000).toFixed(3);
		window.opener.document.getElementById('volWeight' + rowCount).value = volWeight;
		var keyValuekg = volWeight.split(".")[0];
		var keyValueGm = volWeight.split(".")[1];
		window.opener.document.getElementById('weightVW' + rowCount).value = keyValuekg;
		window.opener.document.getElementById('weightGmVW' + rowCount).value = keyValueGm;
		window.opener.document.getElementById("length" + rowCount).value = length;
		window.opener.document.getElementById("breath" + rowCount).value = breath;
		window.opener.document.getElementById("height" + rowCount).value = height;
		
		if(processCode !=null && processCode!="" && processCode=="OGM"){
			setChargeableWeight(rowCount);
			window.opener.document.getElementById("pincode"+rowCount).focus();
		}
		else {
			var contentCodeObj = window.opener.document.getElementById("contentCode"+rowCount);
			if(!isNull(contentCodeObj)){
				contentCodeObj.focus();
			}
			//window.opener.document.getElementById("contentCode"+rowCount).focus();
		}
	} else {
		var volWeight = parseFloat(length * breath * height / 6000).toFixed(3);
		window.opener.document.getElementById('volWeight').value = volWeight;
		var keyValuekg = volWeight.split(".")[0];
		var keyValueGm = volWeight.split(".")[1];
		window.opener.document.getElementById('weightVW').value = keyValuekg;
		window.opener.document.getElementById('weightGmVW').value = keyValueGm;

		window.opener.document.getElementById("length").value = length;
		window.opener.document.getElementById("breath").value = breath;
		window.opener.document.getElementById("height").value = height;
		
		window.opener.document.getElementById("contentCode").focus();
	}

	if (length == null || length == "") {
		alert("Please enter the Length.");
		setTimeout(function() {
			document.getElementById("length").focus();
		}, 10);
		isValid = false;
		return isValid;

	}
	if (breath == null || breath == "") {
		alert("Please enter the Breadth.");
		setTimeout(function() {
			document.getElementById("breath").focus();
		}, 10);
		isValid = false;
		return isValid;

	}
	if (height == null || height == "") {
		alert("Please enter the Height.");
		setTimeout(function() {
			document.getElementById("height").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	/*if (rowCount > 0) 
	window.opener.document.getElementById('weightGmVW' + rowCount).focus();
	else
		window.opener.document.getElementById('weightGmVW').focus();*/
	
	//In manifest
	if(processCode==PROCESS_IN_MANIFEST_BAG_PARCEL){
		window.opener.document.getElementById("cnContentCodes"+rowCount).focus();
		window.opener.setFinalWeight(rowCount);
	}
	
	self.close();

}

/**
 * cancelVal
 *
 * @author sdalli
 */
function cancelVal() {
	if (rowCount > 0) {
	if(confirm("Do you want clear  and close the screen ")){
		window.opener.document.getElementById("length" + rowCount).value = "";
		window.opener.document.getElementById("breath" + rowCount).value = "";
		window.opener.document.getElementById("height" + rowCount).value = "";
		window.opener.document.getElementById('weightVW'+ rowCount).value = "";
		window.opener.document.getElementById('weightGmVW'+ rowCount).value = "";
		self.close();	
	}
		
	}else{
		if(confirm("Do you want clear  and close the screen ")){
		window.opener.document.getElementById("length").value = "";
		window.opener.document.getElementById("breath").value = "";
		window.opener.document.getElementById("height").value = "";
		window.opener.document.getElementById('weightVW').value = "";
		window.opener.document.getElementById('weightGmVW').value = "";
		self.close();	
		
		}
	}
	
}

/**
 * onlyDecimal
 *
 * @param e
 * @returns {Boolean}
 * @author sdalli
 */
function onlyDecimal(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else {
		return true;
	}
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
function onlyNumberAndEnterKeyNav(e,Obj,focusId){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	}else {
		return true;
	}
}

function setChargeableWeight(rowCount) {
	var cnWeight = "";
	var volWeight = window.opener.document.getElementById("volWeight" + rowCount).value;
	var actWeight = window.opener.document.getElementById("actWeight" + rowCount).value;
	volWeight=parseFloat(volWeight);
	actWeight=parseFloat(actWeight);
	if (actWeight < volWeight)
		cnWeight = volWeight;
	else
		cnWeight = actWeight;
	if (!isEmptyWeight(cnWeight)) {
		cnWeight = parseFloat(cnWeight).toFixed(3);
		window.opener.document.getElementById("weight" + rowCount).value = cnWeight;
		window.opener.document.getElementById("weightKg" + rowCount).value = cnWeight.split(".")[0];
		window.opener.document.getElementById("weightGm" + rowCount).value = cnWeight.split(".")[1];
	}
}