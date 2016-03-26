var pleaseEnterMsg = "Please Enter ";
var errorEndMsg = " !" ;

$(document).ready(function() {
	var oTable = $('#example').dataTable({
		"sScrollY" : "350",
		"sScrollX" : "110%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
});

function getCityName(pinObj) {
	if (!isNull(pinObj.value)) {
		var pincode = pinObj.value;
		url = './rateCalculator.do?submitName=getCityName&pincode=' + pincode;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackCityDetails(req, pinObj);
			}
		});
	}
}

function printCallBackCityDetails(data, pinObj) {
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		pinObj.value = "";
		pinObj.focus();
		document.getElementById("destinationCity").value = "";
	} else {
		var req = jsonJqueryParser(data);
		document.getElementById("destinationCity").value = req.cityName;
	}
}

/**
 * enterKeyNavigationFocus
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author prmeher
 */
function enterKeyNavigationFocus(evt, elementIdToFocus) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode == 13) {
		$("#" + elementIdToFocus).focus();
		return true;
	}
	return false;
}

function calculateRates() {
	if (validateInput()) {
		var url = './rateCalculator.do?submitName=calculateRates';
		ajaxJquery(url, "rateCalculatorForm", callCalculateRates);
	}

}

function callCalculateRates(ajaxResp) {
	var data = jsonJqueryParser(ajaxResp);
	var error =  "";
	if (!isNull(data)) {
		error = data["ERROR"];
		if (isNull(error)) {
			var components = data.components;
			for ( var i = 0; components.length; i++) {
				if (components[i].rateComponentCode == 'SLBRT') {
					$("#slabRate").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'RSKCG') {
					$("#riskSurcharge").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'PRHCG') {
					$("#parcelCharges").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'DCHCG') {
					$("#documentCharges").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'ARHCG') {
					$("#airportCharges").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'OTSCG') {
					$("#otherCharges").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'TPCHG') {
					$("#toPayCharges").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'SRVTX') {
					$("#serviceTax").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'EDUCS') {
					$("#eduCess").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'HEDCS') {
					$("#higherEduCess").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'FSCHG') {
					$("#fuelSurcharge").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'STTAX') {
					$("#stateTax").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'SCSTX') {
					$("#surchargeOnState").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'CODCG') {
					$("#codCharge").val(
							(components[i].calculatedValue).toFixed(2));
				}
				if (components[i].rateComponentCode == 'GTTAX') {
					$("#total").val((components[i].calculatedValue).toFixed(2));
				}
			}
		}else {
			alert(error);
			clearFormData();
		}
	}
	hideProcessing();
}

function validateInput() {
	var productType = document.getElementById('productType').value;
	var serviceAt = document.getElementById('serviceAt').value;
	var CNtype = document.getElementById('CNtype').value;
	var pincode = document.getElementById('pincode').value;
	var weightKg = document.getElementById('weightKg').value;
	var weightGrm = document.getElementById('weightGrm').value;
	var declaredValue = document.getElementById('declaredValue1').value;

	if (isNull(productType)) {
		alert("Please Select productType.");
		document.getElementById("productType").focus();
		return false;
	}
	if (productType == "PC000005" && isNull(serviceAt)) {
		alert("Please  Select serviceAt.");
		document.getElementById("serviceAt").focus();
		return false;
	}
	if (isNull(CNtype)) {
		alert("Please Select CNType.");
		document.getElementById("CNtype").focus();
		return false;
	}
	if (isNull(pincode)) {
		alert("Please enter pincode.");
		document.getElementById("pincode").focus();
		return false;
	}
	if (isNull(weightKg)) {
		if (isNull(weightGrm)) {
			alert("Please Enter WeightKg OR WeightGrm.");
			document.getElementById("weightGrm").focus();
			return false;
		}
	}
	if(CNtype == "PPX" && isEmptyRate(declaredValue)){
		alert("Please Enter Declared Value.");
		document.getElementById("declaredValue1").focus();
		return false;
	}
	return true;
}

function checkIsPreferenceApplicable(prodObj) {
	if (!isNull(prodObj.value)) {
		if (prodObj.value == 'PC000001') {
			getDomElementById("preferences").disabled = false;
		} else {
			getDomElementById("preferences").value = "";
			getDomElementById("preferences").disabled = true;
		}
	}
}

function clearFormData(){
	document.getElementById('productType').value ="";
	document.getElementById('serviceAt').value="";
	document.getElementById('CNtype').value="";
	document.getElementById('pincode').value="";
	document.getElementById('weightKg').value="";
	document.getElementById('destinationCity').value="";
	document.getElementById('weightGrm').value="";
	document.getElementById('declaredValue1').value="";
}

function fnDeclaredValEditable(){
	var cnTypeIdCode = $('#CNtype').val();	
	if(isNull(cnTypeIdCode)){
		focusAlertMsg4TxtBox($("#CNtype"), "Consignment Type");
		return false;
	}else if(cnTypeIdCode == "PPX"){
		fnEnableDisbleField('declaredValue1', false);
		setTimeout(function() {
			$('#declaredValue1').focus();
		}, 10);
	}else{
		$('#declaredValue1').val("");
		fnEnableDisbleField('declaredValue1', true);
	}
	return true;
}
function fnEnableDisbleField(inputId, flag){
	$('#'+inputId).attr("readonly", flag);	
	$('#'+inputId).attr("disabled", flag);	
}

function isValidDecValue() {
	var decVal = $("#declaredValue1").val();	
	if (isNull(decVal)  || decVal == "0.00") {
		focusAlertMsg4TxtBox($("#declaredValue1"), "a non-zero declared value");
		return false;
	}else{
		$("#declaredValue1").val(parseFloat(decVal).toFixed(2));
	}
	return true;
}

function focusAlertMsg4TxtBox(obj, fieldName){
	obj.focus();
	alert(pleaseEnterMsg + fieldName + errorEndMsg);
}

function enterKeyForConsignmentType (event) {
	var cnTypeIdCode = $('#CNtype').val();	
	if(isNull(cnTypeIdCode)){
		focusAlertMsg4TxtBox($("#CNtype"), "Consignment Type");
		return false;
	}else if(cnTypeIdCode == "PPX"){
		fnEnableDisbleField('declaredValue1', false);
		enterKeyNavigationFocus(event,'declaredValue1');
	} else {
		enterKeyNavigationFocus(event,'weightKg');
	}
}

function fnServiceOnEditable(){
	var productCode = $('#productType').val();	
	if(productCode == "PC000005"){
		fnEnableDisbleField('serviceAt', false);
		setTimeout(function() {
			$('#serviceAt').focus();
		}, 10);
	}else{
		$('#serviceAt').val("");
		fnEnableDisbleField('serviceAt', true);
	}
	return true;
}

function enterKeyForProduct (event) {
	var productCode = $('#productType').val();	
	if(isNull(productCode)){
		focusAlertMsg4TxtBox($("#productType"), "Product");
		return false;
	}else if(productCode == "PC000005"){
		enterKeyNavigationFocus(event,'serviceAt');
	} else {
		enterKeyNavigationFocus(event,'CNtype');
	}
}
