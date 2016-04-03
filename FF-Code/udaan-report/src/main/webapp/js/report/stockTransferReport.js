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
function clearScreen(){
	var url="./stockTransferReport.do?submitName=getStockTransferReport";
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
		var url = './stockTransferReport.do?submitName=getStations&region='
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


function getBranchList(id){
	
	getDomElementById("branchList").options.length = 1;
	var cityID=$("#"+id).val();		
	if(cityID){
	var url = './stockTransferReport.do?submitName=getBranchList&cityID=' + cityID;
	ajaxCallWithoutForm(url, populateBranchList);
	}
		
}

function populateBranchList(data){
	
	if (!isNull(data)) {
		
	
		var responseText =data; 
		
		var error = responseText[ERROR_FLAG];		
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
			var content = document.getElementById('branchList');
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


function getProductList(id){
	
	var branchId=$("#"+id).val();		
	var url = './stockTransferReport.do?submitName=getProductList&branchId=' + branchId;
	ajaxCallWithoutForm(url, populateProductList);
		
}

function populateProductList(data){

	if (!isNull(data)) {
		
		alert("back"+data);
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

	var productId=$("#productTo" ).val();	
	var region=$("#originRegion" ).val();
	var station=$("#originStation" ).val();
	var branch=$("#branchList" ).val();
	
	
	
	if(isNull(region)|| isNull(station) || isNull(branch) ){
		
		alert('Select Mandatory Field ');
		if(isNull(region)){
			$("#originRegion").focus();
		}
		else if(isNull(station)){
			$("#originStation").focus();
		}
		else if(isNull(branch)){
			$("#branchList").focus();
		}		
		
	
	}
	else{
		
		var startDate=$("#startDate" ).val();
		var endDate=$("#endDate" ).val();
		if(startDate=="" || endDate==""){
			alert('Select start date and end date');
		}
		else{
		
		
		if(checkDate()){	
			
		
			/*alert("Start Date:"+startDate);
			alert("End Date:"+endDate);
			alert("Region:" + region);
			alert("Station:" + station);
			alert("Branch:" + branch);
			alert("ProductID"+productId);*/
	
			
			var url = '/udaan-report/pages/reportviewer/stockTransferViewer.jsp?'			
			 + "&StartDate="+startDate 
			 + "&EndDate="+endDate
			 + "&Region="+region
			 + "&Station="+station
			 + "&Branch="+branch			
			 + "&productTo="+productId;
				
		
		 	window.open(url, "_blank");
		 	
		} 	
		
		 }	
	
	}

 }


function checkDate(){
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




