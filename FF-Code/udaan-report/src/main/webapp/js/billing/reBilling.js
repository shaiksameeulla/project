var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
function checkDate(){
	var stDate=$("#startDate" ).val();
	var enDate=$("#endDate" ).val();
	var sDate = new Date(stDate);
	var eDate = new Date(enDate);
	if(stDate=="" || sDate>eDate){
		alert("Please ensure that end date is greater than or equal to start date");
		$("#endDate" ).val("");
		$("#endDate" ).focus();
		return false;
	}
}

function isValidJson(json) {
    try {
        JSON.parse(json);
        return true;
    } catch (e) {
        return false;
    }
}
/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		if(isValidJson(ObjeResp)){
			responseText = jsonJqueryParser(ObjeResp);
		}
		else{
			var error =ObjeResp[ERROR_FLAG];
			if(!isNull(error)){
				alert(error);
				return true;
				}
		}
	}catch(e){
		
	}
	if(!isNull(responseText)){
		var error = responseText[ERROR_FLAG];
		if(!isNull(error)){
		alert(error);
		return true;
		}
	}
	return false;
}

function getStationsList(){
	/*$("#station" ).val("");
	$("#branch" ).val("");
	$("#customer" ).val("");*/
	var region = $("#region" ).val();
	var url = './reBilling.do?submitName=getStationsList&region='
		+ region;
	ajaxCallWithoutForm(url, getStations);
}
function getStations(data){
/*	$("#branchList" ).empty();*/
	$("#station" ).empty();
	$("#branch" ).empty();
	$("#customer" ).empty();
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			
			var content = document.getElementById('station');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
	});
  }
 }

function getBranchesList(){
	/*$("#branch" ).val("");
	$("#customer" ).val("");*/
	$("#branch" ).empty();
	$("#customer" ).empty();
	var station = $("#station" ).val();
	var url = './reBilling.do?submitName=getBranchesList&station='
		+ station;
	ajaxCallWithoutForm(url, getBranches);
}
function getBranches(data){
	
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			var content = document.getElementById('branch');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
	});
  }
 }

function getCustomersList(){
	/*$("#customer" ).val("");*/
	$("#customer" ).empty();
	var branch = $("#branch" ).val();
	var url = './reBilling.do?submitName=getCustomersList&branch='
		+ branch;
	ajaxCallWithoutForm(url, getCustomers);
}
function getCustomers(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			
			var content =document.getElementById('customer');// $("#customer" ).val();
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.customerCode + '-' + this.businessName+ '-' + this.shippedToCode));
				content.appendChild(option);
	});
   }
 }

function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
	window.location.reload();
	}
}
/*function checkMandatory(){
	var region=$("#region" ).val();
	var station=$("#station" ).val();
	var branch=$("#branch" ).val();
	var customer=$("#customer" ).val();
	var stDate=$("#startDate" ).val();
	var enDate=$("#endDate" ).val();
	if(isNull(region)){
		alert("Please select a region.");
		setTimeout(function(){$("#region" ).focus();},10);
		return false;
	}
	if(isNull(station)){
		alert("Please select a station.");
		setTimeout(function(){$("#station" ).focus();},10);
		return false;
	}
	if(isNull(branch)){
		alert("Please select a branch.");
		setTimeout(function(){$("#branch" ).focus();},10);
		return false;
	}
	if(isNull(customer)){
		alert("Please select a customer.");
		setTimeout(function(){$("#customer" ).focus();},10);
		return false;
	}
	if(isNull(stDate)){
		alert("Please provide start date.");
		setTimeout(function(){$("#startDate" ).focus();},10);
		return false;
	}
	if(isNull(enDate)){
		alert("Please provide end date.");
		setTimeout(function(){$("#endDate" ).focus();},10);
		return false;
	}
	return true;
}*/
function checkMandatoryOnce(){
	var errorsData="";
	var region=$("#region" ).val();
	var station=$("#station" ).val();
	var branch=$("#branch" ).val();
	var customer=$("#customer" ).val();
	var stDate=$("#startDate" ).val();
	var enDate=$("#endDate" ).val();
	if(isNull(region)){
		errorsData = errorsData+"Please select a region."+"\n";	
		alert(errorsData);
		$("#region" ).focus();
		return false;
	}
	if(isNull(station)){
		errorsData = errorsData+"Please select a station."+"\n";	
		alert(errorsData);
		$("#station" ).focus();
		return false;
	}
	if(isNull(branch)){
		errorsData = errorsData+"Please select a branch."+"\n";	
		alert(errorsData);
		$("#branch" ).focus();
		return false;
	}
	if(isNull(customer)){
		errorsData = errorsData+"Please select a customer."+"\n";
		alert(errorsData);
		$("#customer" ).focus();
		return false;
	}
	if(isNull(stDate)){
		errorsData = errorsData+"Please provide start date."+"\n";	
		alert(errorsData);
		$("#startDate" ).focus();
		return false;
	}
	if(isNull(enDate)){
		errorsData = errorsData+"Please provide end date."+"\n";
		alert(errorsData);
		$("#endDate" ).focus();
		return false;
	}
	/*if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{*/
    	return true;
  /*  }*/
}

function checkDate(){
	var startDate=$("#startDate" ).val();
	var endDate=$("#endDate" ).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);
	if(date1=="" || date1>date2){

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate" ).val("");

		$("#startDate" ).focus();

		return false;

		}
	else {
		var currentdate = new Date();
		if(date2 >currentdate ){
			alert("End Date Can't be Greater that Current date");
			$("#endDate" ).val("");
			$("#endDate" ).focus();
			return false;
		}
		return true;
	}


}


function reCalculateRates(){
	if(checkMandatoryOnce())
	{
	 if(checkDate()){
	  var url = './reBilling.do?submitName=reCalculate';
	  ajaxCall(url, "reBillingForm", saveCallback);
	/*  jQuery.ajax({
		url : url,
		data : jQuery("#reBillingForm").serialize(),
		success : function(req) {
			callBack(req);
			}
		});*/
	  
	  
	}
  }
}
	
function saveCallback(data){
	//showProcessing();
	var reBillTO = eval('(' + data + ')');
	if(!isNull(reBillTO.errorMessage)){
		alert(reBillTO.errorMessage);
		//cancelPage();
		$("#startDate").val("");
		$("#endDate").val("");
		cancelReBill();
		
		
	} else if(!isNull(reBillTO.successMessage)){
		alert(reBillTO.successMessage);
		$("#startDate").val("");
		$("#endDate").val("");
		cancelReBill();
	}
}

function cancelReBill(){
	var url = "./reBilling.do?submitName=preparePage";
	document.reBillingForm.action=url;
	document.reBillingForm.submit();
}
