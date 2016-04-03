/** Start of Changes by <31913> on 10/01/2013
 * 
 */

//******************************Business Rules*********************************
//8.4.4	Before transfer, consignment issued to Customer / Field Representative / BA 
//8.4.5	Consignments issued to BA can be transferred only to other BA’s within the region.
//8.4.6	Consignments issued to customers can be transferred other customers  and to field representatives.
//8.4.7	Consignments issued to pickup boy can be transferred to pickup boys within the branch or customer’s reporting to the same branch.
//8.4.8	Stock transferred to a branch can be issued from Stock issue.

//********************************************Business Rules**************
/**
 * Allows you to tag each variable supported by a function.
 */
var transferFormId='StockReportForm';
var domElemForTrasferFromType=null;
var domElemForTrasferTOType=null;
var ERROR_FLAG="ERROR";
/**
 * clearScreen
 */
function clearSRScreen(){
	var url="./stockRequestBranchReport.do?submitName=getStockRequestBranchReport";
	submitTransfer(url,'Clear'); 
}
/**
 * submitTransfer
 * @param url
 * @param action
 */
function submitTransfer(url,action){
	if(confirm("Do you want to "+action+" the details?")) {
		document.consignmentReportForm.action = url;
		document.consignmentReportForm.method = "post";
		document.consignmentReportForm.submit();
	}
}

function clearFields() {
	getDomElementById("originStation").options.length = 1;	
	getDomElementById("branchList").options.length = 1;
}

function getOrgStationsList(){
	
	clearFields();		
	var region = document.getElementById('originRegion').value;	
	if(region){
		var url = './stockRequestBranchReport.do?submitName=getStations&region='
			+ region;
		ajaxCallWithoutForm(url, getOrgStationList);
	}
	
}
function getOrgStationList(data){	
	if (!isNull(data)) {	
			var content = document.getElementById('originStation');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
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

function readSRBranch(){
	var branch = "";
	var branches = document.getElementById("branchList");	
	if($("#branchList").val().toUpperCase() == "ALL"){
		for (var i = 2; i<branches.length; i++){
			branch = branch + branches.options[i].value;
			
			if(i != (branches.length-1)){
				branch = branch + ", ";
			}
		}
	}else{
		branch = $("#branchList").val();
	}
	return branch;
}


function getCustomerList(id){
	
	getDomElementById("client").options.length = 1;	
	var branch = readSRBranch();		
	if ($("#branchList").val().toUpperCase() != "SELECT") {
		var url = './custSlabWiseRevenue.do?submitName=getCustomerList&branch='
			+ branch;	
		ajaxCallWithoutForm(url, populateCustomerList);
	}	
}

function populateCustomerList(data){
	
	if (!isNull(data)) {
	
		var responseText =data; 
		
		var error = responseText[ERROR_FLAG];		
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			var content = document.getElementById('client');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName));
				content.appendChild(option);
	});
   }
	 else {
			var content = document.getElementById('client');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "Select";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
		}
  
}




function getProductList(id){
	
	var branchId=$("#"+id).val();		
	var url = './stockRequestBranchReport.do?submitName=getProductList&branchId=' + branchId;
	ajaxCallWithoutForm(url, populateProductList);
		
}

function populateProductList(data){

	if (!isNull(data)) {
		
		
		var responseText =data; 
		
		var error = responseText[ERROR_FLAG];		
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			var content = document.getElementById('productList');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
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

function printReport(){	

	var product=$("#productTo" ).val();		
	var region=$("#regionList" ).val();
	var station=$("#stationList" ).val();
	var branch=$("#branchList" ).val();
	//var client=$("#client" ).val();	

	
	//if(isNull(region)|| isNull(station) || isNull(branch) || isNull(product) || isNull(client)){
	if(isNull(region) || 'select' ==  region.toLocaleLowerCase()) {
		
		alert('Select Mandatory Field ');
		if(isNull(region) || 'select' ==  region.toLocaleLowerCase()){
			$("#regionList").focus();
		}
		/*else if(isNull(station)){
			$("#originStation").focus();
		}
		else if(isNull(branch)){
			$("#branchList").focus();
		}		
		/*else if(isNull(product)){
			$("#productTo").focus();
		}
		/*else if(isNull(client)){
			$("#customerList").focus();
		}*/
	
	}
	else{
		
		var startDate=$("#startDate" ).val();
		var endDate=$("#endDate" ).val();

		if(startDate=="" || endDate==""){
			alert('Select start date and end date');
		}
		else{			
			
		if(checkStockRequestDate()){	
			
		/*
			alert("Start Date:"+startDate);
			alert("End Date:"+endDate);
			alert("Region:" + region);
			alert("Station:" + station);
			alert("Branch:" + branch);
			alert("product"+product);*/
		//	alert("client"+client);
			
//			if(client=='Select'){
//				client='';		
//			}
			if(region=='Select'){
				region='';		
			}
			if(station.toLocaleLowerCase()=='select' || station.toLocaleLowerCase() =='all'){
				station='';		
			}
			if(branch.toLocaleLowerCase() =='select' || branch.toLocaleLowerCase() =='all'){
				branch='';		
			}
			
			var originRegionName = document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
     		var originStationName = document.getElementById('stationList').options[document.getElementById('stationList').selectedIndex].text;
     		var originBranchName = document.getElementById('branchList').options[document.getElementById('branchList').selectedIndex].text;     			
     		var productName = document.getElementById('productTo').options[document.getElementById('productTo').selectedIndex].text;	
     		//var clientName = document.getElementById('client').options[document.getElementById('client').selectedIndex].text;     
     		
     		//For Not mandatory Fields
     		if(originStationName.toLocaleLowerCase()=='select' ){
     			originStationName='';		
			}
     		if(originBranchName.toLocaleLowerCase()=='select' ){
     			originBranchName='';		
			}
     		if(productName.toLocaleLowerCase()=='select' ){
     			productName='';		
			}     		
//     		if(clientName.toLocaleLowerCase()=='select' ){
//     			clientName='';		
//			}
					
			
			var url = '/udaan-report/pages/reportviewer/stockRequestBranchViewer.jsp?'			
			 + "&StartDate="+startDate 
			 + "&EndDate="+endDate
			 + "&Region="+region
			 + "&Station="+station
			 + "&Branch="+branch			
			 + "&product="+product
			// + "&client="+client
			 + "&originRegionName="+originRegionName
			 + "&originStationName="+originStationName
			 + "&originBranchName="+originBranchName
			 + "&productName="+productName
			 //+ "&clientName="+clientName;
		
		 	window.open(url, "_blank");
		 	
		} 	
		
		 }	
	
	}
	}

 // }


function checkStockRequestDate(){	
	
	var startDate=$("#startDate" ).val();
	var endDate=$("#endDate" ).val();
		
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);
	var today = new Date();
	if (date2 > today) {
		alert('Future Date cannot be entered');
		return false;
	}
	if(date1=="" || date1>date2){

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate" ).val("");

		$("#startDate" ).focus();

		return false;

		}
	else {
		return true;
	}


}

function getEmployees(id){
	
	var branchId=$("#"+id).val();	
	
	var url = './stockRequestBranchReport.do?submitName=getEmployeeList&branchId=' + branchId;
	ajaxCallWithoutForm(url, populateEmployeesList);
		
}

function populateEmployeesList(data){

	if (!isNull(data)) {
		
	
		var responseText =data; 
		
		var error = responseText[ERROR_FLAG];		
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			var content = document.getElementById('employeeList');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.employeeId;
				option.appendChild(document.createTextNode(this.firstName + " " +this.lastName));
				content.appendChild(option);
	});
   }
  
}





