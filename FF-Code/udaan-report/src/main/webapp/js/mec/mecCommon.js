
function validateChqNumber(chqNo){
	var isValidReturnVal=true ;
	if (!isNull(chqNo.value)){
		if(chqNo.value.length < 6 || chqNo.value.length > 6 ){
			alert('Cheque No. length should be 6 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
		 isValidReturnVal= false;
		}
		
	}
	return isValidReturnVal;
}

/**
 * To check whether amount is empty or not
 * 
 * @param amt
 * @returns {Boolean}
 */
function isEmptyAmount(amt){
	var dotPattern = /^[.]{2,10}$/;
	if(isNull(amt) || isNull(parseFloat(amt)) || amt=="." || amt.match(dotPattern)){
		return true;
	}
	return false;
}
	
/**
 * To show processing image
 */
function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}

/**
 * To hide processing image
 */
function hideProcessing() {
	jQuery.unblockUI();
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, selectOption, "");
}

/**
 * It adds the values to dropdown
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * Enter Key navigation with allowing only decimal
 * 
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 */
function onlyNumberAndEnterKeyNav(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode>31 && (charCode<48 || charCode>57) && charCode!=46) {
		return false;
	} else if (charCode==13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/**
 * To set amount format and set zero if NULL
 * 
 * @param domElement
 */
function setAmountFormatZero(domElement){
	if(!isEmptyAmount(domElement.value)){
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if(isNull(amtValue[0])){
			amtValue[0] = "0";
		}
		if(isNull(amtValue[1])){
			amtValue[1] = "00";
		} else if(amtValue[1].length==1){
			amtValue[1] = amtValue[1] + "0";
		} else if(amtValue[1].length>2){
			amtValue[1] = amtValue[1].substring(0,2);
		}
		domElement.value = amtValue[0] + "." +amtValue[1];
	} else {
		domElement.value="0.00";
	}
}
