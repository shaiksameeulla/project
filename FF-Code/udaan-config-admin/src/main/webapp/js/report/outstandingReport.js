var custIDArr = new Array();
var custCodeArr = new Array();
var data = new Array();

function body_onload(){
	isDataSaved();
	loadCustomerName();
}

function setCustomerCode(){
	var customerName = jQuery("#customerName").val();   
	$.each(data, function(index, customer) {
		if(customer == customerName){
			jQuery("#customerCode").val(custCodeArr[index]);
		}
	});

}


function checkForDisableField(){
	var outstandingFor = document.getElementById('outstandingFor').value;
	
	if(outstandingFor == 'P' ){
		document.getElementById('customerCode').value = "";
		document.getElementById('customerName').value = "";
		document.getElementById('customerName').disabled = true;
		document.getElementById('customerCode').disabled = true;
	}else{
		document.getElementById('customerName').disabled = false;
		document.getElementById('customerCode').disabled = false;
	}
	
}

function resetField() {
	document.getElementById('customerName').disabled = false;
	document.getElementById('customerCode').disabled = false;
	document.getElementById("outstandingReportForm").reset();
	document.getElementsByName("outstandingReportForm1").reset();
	document.getElementById("errorMessageBlock").style.display = "none";
		
}



function validate(){
	var datePattern =/^([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])[/]([1-9]|0[1-9]|1[0-2])[/](1[9][0-9][0-9]|2[0][0-9][0-9])$/;
	
	var retVal = true;
	var errorMessageArray = new Array();
	errorMessageArray.push("<ul>");
	
	if(document.getElementById('reportForID').value == "" || document.getElementById('reportForID').value == null){
		errorMessageArray.push("<li> please select Report For </li>");
		retVal = false;
	}
	if(!datePattern.test(document.getElementById('billUptoID').value)){
		errorMessageArray.push("<li>please select Bill Upto </li>");
		retVal = false;
	}
	if(!datePattern.test(document.getElementById('paymentUptoID').value)){
		errorMessageArray.push("<li>please select Payment Upto </li>");
		retVal = false;
	}
	
	if(document.getElementById('outstandingFor').value == "" || document.getElementById('outstandingFor').value == null){
		errorMessageArray.push("<li>please select Outstanding For </li>");
		retVal = false;
	}
	
	if(document.getElementById('outstandingFor').value =='C' && (document.getElementById('customerName').value == "" || document.getElementById('customerName').value  == null)){
		errorMessageArray.push("<li>please select Customer Name </li>");
		retVal = false;
	}
	errorMessageArray.push("</ul>");
	document.getElementById("errorMessage").innerHTML="";
	
	if(errorMessageArray.length > 2){
		for( i=0; i<errorMessageArray.length; i++){
			jQuery("#errorMessage").append(errorMessageArray[i]);
		}
	}
	if(!retVal){
		document.getElementById("errorMessageBlock").style.display = "block";
	}
	
	
	return retVal;
}
