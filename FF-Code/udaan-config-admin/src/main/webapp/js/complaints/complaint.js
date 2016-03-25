/**
 * @param emailObj
 */
function validateEmailId(emailObj) {
	if (!validateEmail(emailObj.value)) {
		emailObj.value = "";
		setTimeout(function() {
			emailObj.focus();
		}, 10);
	} else if (!checkMailId(emailObj)) {
		emailObj.value = "";
		setTimeout(function() {
			emailObj.focus();
		}, 10);
	}
}

/**
 * To allow only alpha-numeric value(s)
 * 
 * @param e -
 *            event object
 * @returns {Boolean}
 */
function alphaNumAllow(e) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	// 8 - backspace, 9 - tab
	if ((key >= 48 && key <= 57) || (key >= 65 && key <= 90)
			|| (key >= 97 && key <= 122) || key == 8 || key == 9 || key == 0) {
		return true;
	}
	return false;
}

/**
 * 
 */
function closePopup(){
	$(function(){
	        window.close();
	});
}