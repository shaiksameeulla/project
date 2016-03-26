/**
 * redirectPage
 *
 * @author sdalli
 */
function redirectPage(){
					
					var docTypeIdName = document.getElementById('consgTypeName').value;
					var docType = "";
					if(docTypeIdName !=null)
					docType = docTypeIdName.split("#")[1];
					if(docType == "PPX"){
					url ="./focBookingParcel.do?submitName=viewFOCBookingParcel&docType="+docType;
				    window.location=url;
					}
					else if(docType == "DOX"){
						url = './focBookingDox.do?submitName=viewFOCBooking';
					    window.location=url;
						}
				}

/*
*//**
 * isValidDecValue
 *
 * @param obj
 * @author sdalli
 *//*
function isValidDecValue(obj){
	   
	   var bookingType = document.getElementById("bookingType").value;
	   var decVal = obj.value;
	   url = './baBookingParcel.do?submitName=validateDeclaredvalue&declaredVal='+decVal+'&bookingType='+bookingType;
	   ajaxCallWithoutForm(url,setBookingDtlsResponse);
	   
}

*//**
 * setBookingDtlsResponse
 *
 * @param data
 * @author sdalli
 *//*
function setBookingDtlsResponse(data){
	   if(!isNull(data)){
		   if(data.isValidDeclaredVal=="N"){
			   alert('Declared Value Max limit exceeded.');
		   document.getElementById("declaredValue").value="";
		   setTimeout(function() {
					document.getElementById("declaredValue").focus();
				}, 10);
		   }
		   else{
			   getPaperWorks();
		   }
	   }
}*/

/**
 * validateConsignmentFOC
 *
 * @param consgNumberObj
 * @author sdalli
 */

var consgNumber = null;//this field is shared amongst call and call back functions
function validateConsignmentFOC(consgNumberObj) {
	if(consgNumberObj.value !=null && consgNumberObj.value !="") {
		consgNumber = consgNumberObj.value;
		var bookingType = document.getElementById("bookingType").value;
		if(consgNumber.length < 12 || consgNumber.length > 12){
			alert("Consingment length should be 12 character");
			document.getElementById("cnNumber").value = "";
			setTimeout(function() {
				document.getElementById("cnNumber").focus();
			}, 10);
			return;
		}else{
			var numpattern = /^[0-9]{3,20}$/;
			var letters = /^[A-Za-z]+$/;
			if (!numpattern.test(consgNumberObj.value.substring(5))
					|| !letters.test(consgNumberObj.value.substring(0, 1))) {

				alert('Consignment number format is not correct');
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				return;
			}

		}
		showProcessing(); 
		url = './cashBooking.do?submitName=validateConsignment&consgNumber=' + consgNumber+"&bookingType="+bookingType;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req);
			}
		});
	}
	
}

/**
 * printCallBackConsignment
 *
 * @param data
 * @author sdalli
 */
function printCallBackConsignment(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
				if(cnValidation.isValidCN =="N"){
					alert(cnValidation.errorMsg);
					document.getElementById("cnNumber").value = "";
					setTimeout(function() {
						document.getElementById("cnNumber").focus();
					}, 10);
				}
				else if(cnValidation.isConsgExists =="Y"){
					alert(cnValidation.errorMsg);
					document.getElementById("cnNumber").value = "";
					setTimeout(function() {
						document.getElementById("cnNumber").focus();
					}, 10);
				}
				else {
					usedConsignments.push(consgNumber);
				}
			}
		}
	
	jQuery.unblockUI();
}


/**
 * validatePincodeFOC
 *
 * @param pinObj
 * @author sdalli
 */
function validatePincodeFOC(pinObj) {
	if(pinObj.value !=null && pinObj.value !="") {
		var bookingOfficeId="";
		var pincode = pinObj.value;
		var consgNumber = document.getElementById("cnNumber").value;
		var consgSeries = consgNumber.substring(4,5);
	
		if(pincode.length < 6){
			alert("Invalid pincode");
			document.getElementById("pinCode").value = "";
			document.getElementById("pinCode").focus();
			return;
		}
			
		bookingOfficeId = document.getElementById("bookingOfficeId").value;
		showProcessing();
		url = './cashBooking.do?submitName=validatePincode&pincode=' + pincode+ "&bookingOfficeId=" + bookingOfficeId+ "&consgSeries=" + consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincodeFOC(req);
			}
		});
	}
	
}



/**
 * printCallBackPincodeFOC
 *
 * @param data
 * @author sdalli
 */
function printCallBackPincodeFOC(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
				if(cnValidation.isValidPincode =="N"){
					alert(cnValidation.errorMsg);
					document.getElementById("pinCode").value = "";
					document.getElementById("pinCode").focus();
				}
				else if(cnValidation.isValidPriorityPincode =="N"){
					alert(cnValidation.errorMsg);
					document.getElementById("pinCode").value = "";
					document.getElementById("pinCode").focus();
				}
				else {
					document.getElementById("destCity").value = cnValidation.cityName;
					document.getElementById("cityId").value = cnValidation.cityId;
					document.getElementById("pincodeId").value = cnValidation.pincodeId;
					//getPriorityDtls();
				}
			}
		}
	jQuery.unblockUI();
}

/**
 * getFinalWeightFOC
 *
 * @author sdalli
 */
function getFinalWeightFOC(){
	
	var volWeight = document.getElementById("volWeight").value;
	var actWeight = document.getElementById("actualWeight").value;
	volWeight = parseFloat(volWeight);
	actWeight = parseFloat(actWeight);
	if(!isNull(actWeight)){
			document.getElementById("finalWeight").value=actWeight;
	}
	if(!isNull(volWeight) && !isNull(actWeight)){
		if(volWeight > actWeight)
			document.getElementById("finalWeight").value=volWeight;
	}
	//Child Cns validation
	var noOfPcs = document.getElementById("pieces").value;
	var finalWeight = document.getElementById("finalWeight").value;
	if(parseInt(noOfPcs) > 1) {
		var chilsCNDtls = document.getElementById("details").value;
		validateParentAndChildCnsWeight(actWeight, chilsCNDtls, 0);
	}
	}


/**
 * capturedWeight
 *
 * @param data
 * @author sdalli
 */
function capturedWeight(data) {
	var capturedWeight = "";
	if (!isNull(data)) {
		capturedWeight = eval('(' + data + ')');
		if (capturedWeight == -1) {
			wmWeight = capturedWeight;
		} else if (capturedWeight == -2) {
			wmWeight = "Exception occurred";
		} else if (!isNull(capturedWeight) && capturedWeight != -1) {
			wmWeight = parseFloat(capturedWeight).toFixed(3);

			if(wmWeight<0){
				alert("Negative Weight Reached");
				weightInkgs=0;
				weightInGms= '000';
				
			}else{
				
				weightInkgs = wmWeight.split(".")[0];
				weightInGms = wmWeight.split(".")[1];
				
			}
			disableEnableWeight();
		}
	}
}

/**
 * disableEnableWeight
 *
 * @author sdalli
 */
function disableEnableWeight(){
	if(wmWeight != "-1"){
		document.getElementById("weight").readOnly = true;
		document.getElementById("weightGm").readOnly = true;
		document.getElementById("weight").value=weightInkgs;
		document.getElementById("weightGm").value=weightInGms;
		document.getElementById("weightCapturedMode").value = "A";
		document.getElementById("finalWeight").value = wmWeight;
		
	}
	else {
		document.getElementById("weight").readOnly = false;
		document.getElementById("weightGm").readOnly = false;
	//	document.getElementById("weight").value="";
	//	document.getElementById("weightGm").value="";
		document.getElementById("weightCapturedMode").value = "M";
	//	document.getElementById("finalWeight").value = "";
	}
}

/**
 * getApprovers
 *
 * @author sdalli
 */
function getApprovers(){
	var bookingType = "FC";
	var url = "cashBooking.do?submitName=getApprovers&bookingType="+bookingType;
	ajaxCallWithoutForm(url,getApproversResponse);
}

/**
 * getApproversResponse
 *
 * @param data
 * @author sdalli
 */
function getApproversResponse(data){
	var content = document.getElementById('approver');
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.employeeId;
		option.appendChild(document.createTextNode(this.firstName+" "+ this.lastName));
		content.appendChild(option);
	});
	
}