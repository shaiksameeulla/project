function checkSecondaryContact(obj) {
	var regex = /^[a-zA-Z]([.' ]?[a-zA-Z]*)*( [a-zA-Z]([.']?[a-zA-Z]+)*)*$/;
	if (obj.value.length == 0)
		return true;

	if (regex.test(obj.value)) {
		return true;
	} else {
		alert("Entered value is not proper for Secondary Contact.");
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
}

function checkContactPerson(obj) {
	var regex = /^[a-zA-Z]([.' ]?[a-zA-Z]*)*( [a-zA-Z]([.']?[a-zA-Z]+)*)*$/;
	if (obj.value.length == 0)
		return true;
	if (regex.test(obj.value)) {
		return true;
	} else {
		alert("Entered value is not proper for Contact Person.");
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
}
function charactersForSecondaryContactPerson(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || charCode == 0 || charCode == 46
			|| charCode == 222 || charCode == 32 || charCode == 39) {
		return true;
	} else {
		return false;
	}
	return false;
}

function charactersForPurVisit(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || charCode == 0 || charCode == 32) {
		return true;
	} else {
		return false;
	}
	return false;
}

function charactersForCity(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || charCode == 0 || charCode == 46
			|| charCode == 222 || charCode == 32 || charCode == 39
			|| charCode == 40 || charCode == 41 || charCode == 47) {
		return true;
	} else {
		return false;
	}
	return false;
}

function characterForDesignation(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || charCode == 0 || charCode == 46
			|| charCode == 222 || charCode == 32 || charCode == 39
			|| charCode == 45 || charCode == 44) {
		return true;
	} else {
		return false;
	}
	return false;
}

function checkCity(obj) {
	var regex = /^[a-zA-Z]+(')?[a-zA-Z]*(\s)?(\/)?(\s)?((\(([a-zA-Z]+(')?[a-zA-Z]*)*\))|(([a-zA-Z]+(')?[a-zA-Z]*)*))$/;
	if (obj.value.length == 0)
		return true;
	if (regex.test(obj.value)) {
		return true;
	} else {
		alert("Entered value is not proper for City.");
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
}

function checkDesignation(obj) {
	var regex = /^[a-zA-Z0-9]+([ -\.'\,a-zA-Z0-9]*)[^-\s\.'\,]+$/;
	if (obj.value.length == 0)
		return true;

	if (regex.test(obj.value)) {
		return true;
	} else {
		alert("Entered value is not proper Designation.");
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
}

function checkExpectedVolume(obj) {
	var potential = $("#potential" + obj).val();
	var volume = $("#expectedVolume" + obj).val();
	var potential1 = 0;
	var volume1 = 0;
	if (isNull(volume)) {
		volume1 = 0;
	} else {
		volume1 = parseFloat(volume).toFixed(2);
		$("#expectedVolume" + obj).val(volume1);
		volume1 = parseFloat(volume1);
		// volume1 =parseInt(volume);
	}
	if (isNull(potential)) {
		potential1 = 0;
	} else {
		potential1 = parseFloat(parseFloat(potential).toFixed(2));
		// potential1 = parseInt(potential);
	}

	if (volume1 <= potential1) {
		return true;
	} else {
		var msg = "Expected volume cannot be greater than potential";
		alertWithoutNotice(msg);
		// alert("Expected volume cannot be greater than potential");
		setTimeout(function() {
			$("#expectedVolume" + obj).focus();
		}, 5);
		return false;
	}

}


function formatDecValue(row) {
	var potential = $("#potential" + row).val();
	var potential1;
	if (isNull(potential)) {
		potential1 = 0;
	} else {
		potential1 = parseFloat(potential).toFixed(2);
		$("#potential" + row).val(potential1);
	}

}
function alertWithoutNotice(message) {
	setTimeout(function() {
		alert(message);
	}, 1);
}
