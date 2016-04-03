var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

var serialNo = 1;
var rowCount = 1;

$(document).ready( function () {
	var oTable = $('#rebillGdr').dataTable( {
		"sScrollY": "160",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,				"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );


function addRow() {

	$('#rebillGdr')
			.dataTable()
			.fnAddData(
					[
					 serialNo,
					 '<span id="reBillNo'+rowCount+'"></span>',
					 '<span id="amount'+rowCount+'"></span>',
					 '<span id="amount1'+rowCount+'"></span>',
					 '<span id="amount2'+rowCount+'"></span>',
					 '<span id="amount3'+rowCount+'"></span> <input type="hidden" name = "to.reBillingId" id="rebillId'+ rowCount+ '" class="txtbox width115"/>'
					]);
 
	rowCount++;
	serialNo++;
}


function deleteAllRow() {
	var table = getDomElementById("rebillGdr");
	var tableRowCount = table.rows.length;
	var oTable = $('#rebillGdr').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}

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
	$("#station" ).val("");
	$("#branch" ).val("");
	$("#customer" ).val("");
	deleteAllRow();
	$("#startDate" ).val("");
	$("#endDate" ).val("");
	var region = $("#region" ).val();
	var url = './reBillingGDR.do?submitName=getStationsList&region='
		+ region;
	ajaxCallWithoutForm(url, getStations);
}
function getStations(data){
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
	$("#branch" ).val("");
	$("#customer" ).val("");
	var station = $("#station" ).val();
	var url = './reBillingGDR.do?submitName=getBranchesList&station='
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
	$("#customer" ).val("");
	var branch = $("#branch" ).val();
	var url = './reBillingGDR.do?submitName=getCustomersList&branch='
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
				option.appendChild(document.createTextNode(this.customerCode + '-' +this.businessName + '-' + this.shippedToCode));
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


function getReBillNos(){
	if(checkMandatoryOnce())
	{
	 if(checkDate()){
	  var url = './reBillingGDR.do?submitName=getRebillNosDetails';
	  ajaxCall(url, "reBillingGDRForm", saveCallback);
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
	
	if (!isNull(data)) {
		//var responseText =data; 
		var responseText = eval('(' + data + ')');
		//var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && !isNull(responseText.errorMessage)){
			alert(responseText.errorMessage);
			deleteAllRow();
			window.location.reload();
			return ;
		}else{
			var j = 0;
			//var custBillList = eval('(' + data + ')');
			var custReBillList =eval('(' + data + ')');
			deleteAllRow();
			serialNo = 1;
			rowCount = 1;
			//var j = 0;
		
			for(var i =0 ;i<custReBillList.reBillList.length;i++){
				addRow();	
				/*$("#customer" + (j+1)).val(custBillList[i].customerTO.customerId);
				$("#billNumber" + (j+1)).val(custBillList[i].billTOs[k].invoiceId);
				$("#signature" + (j+1)).val("");
				$("#shipToCode" + (j+1)).val(custBillList[i].billTOs[k].shipToCode);
				*/
				$("#rebillId" + (j+1)).val(custReBillList.reBillList[i].reBillingId);
				
				$("#reBillNo" + (j+1)).html("<align:left>" +custReBillList.reBillList[i].reBillingNumber+"</align:left>");
				$("#amount" + (j+1)).html(custReBillList.reBillList[i].totalCns);
				$("#amount1" + (j+1)).html(custReBillList.reBillList[i].oldContrFor);
				$("#amount2" + (j+1)).html(custReBillList.reBillList[i].newContrFor);
				/*$("#amount3" + (j+1)).html('<a href="#ANCHOR_NAME">Click</a>');*/
				/*$("#amount3" + (j+1)).innerHTML='<a id="amount3'+ cnt+'" href="#" onclick="populateGenerateGDR('+"'" + custReBillList.reBillList[i].reBillingId + "'"+');">'+custReBillList.reBillList[i].reBillingId+'</a>';*/
				getDomElementById("amount3"+ (j+1)).innerHTML='<a id="amount3'+ (j+1) +'" href="#" onclick="populateGenerateGDR('+"'" + custReBillList.reBillList[i].reBillingId + "'"+');">'+ 'Generate Report' +'</a>';
				j++;
				
				}
		}
		
	}	
}


function populateGenerateGDR(reBillId){
	var url = document.getElementById("reportUrl").value + 'pages/reportviewer/RebillGDRReoprt.jsp?'
		 + "&ReBillingID="+reBillId; 
	 	window.open(url, "_blank");
}