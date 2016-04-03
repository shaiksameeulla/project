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
	var url="./salesProspectsReport.do?submitName=getSalesProspectsReport";
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
		var url = './salesProspectsReport.do?submitName=getStations&region='
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
	var cityID = $("#" + id).val();
	if (!isNull(cityID)) {
		clearBranchs();
		var url = null;
		if (cityID.toLocaleLowerCase() == 'all') {
			var ddlArray = new Array();
			var ddl = document.getElementById(id);
			for ( var i = 2; i < ddl.options.length; i++) {
				ddlArray[i - 2] = ddl.options[i].value;
			}
			url = './yieldReport.do?submitName=getBranchList&cityID='
					+ ddlArray;
		} else {
			url = './baSalesReport.do?submitName=getBranchList&cityID='
					+ cityID;
		}
		ajaxCallWithoutForm(url, populateBranchList);
	}
}
	/*if(cityID){
	var url = './salesProspectsReport.do?submitName=getBranchList&cityID=' + cityID;
	*/
	
		


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
	var url = './salesProspectsReport.do?submitName=getProductList&branchId=' + branchId;
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

function getSalesSlabList(product) {
	
	getDomElementById("slab").options.length = 1;
	var url = './salesProspectsReport.do?submitName=getSlabList&product='+ product.value;

	ajaxCallWithoutForm(url, populateSalesSlabList);
}

function populateSalesSlabList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('slab');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "all";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.vobSlabId;
			if (!isNull(this.upperLimitLabel)){
				option.appendChild(document.createTextNode(this.lowerLimitLabel+"-"+this.upperLimitLabel));
			} else {
				option.appendChild(document.createTextNode(this.lowerLimitLabel));
			}
			content.appendChild(option);
		});
	}
	
}


function printReport(){	

	
	var region=$("#regionList" ).val();
	var station=$("#stationList" ).val();
	var branch=$("#branchList" ).val();
	var salesPerson=$("#salesPerson" ).val();
	var business=$("#business" ).val();
	var product=$("#product" ).val();	
	
	
	var slabList=$('#slab option:selected' ).text();		
	var lowerlimit =0;
	var upperlimit =0;
	
	if(slabList !='Above 100000' && slabList !='Above 500000' && slabList != 'All'){				
	
		var split = slabList.split("-");
		lowerlimit = split[0];
		upperlimit = split[1];		
		slabList=0;
		
		
	}
	
	if(slabList =='Above 100000'){	
		
		slabList = 100000;
		lowerlimit=0;
		upperlimit=0;
	}

	if(slabList =='Above 500000'){	
		
		slabList=50;
		lowerlimit=0;
		upperlimit=0;
	}
	
	
	
	if(slabList =='All'){			
		
		slabList=0;
		lowerlimit=0;
		upperlimit=0;
	}
	
	
	if(isNull(product) || isNull(region) || 'select' ==  region.toLocaleLowerCase() || isNull(station) ){
		
		alert('Select Mandatory Field ');
		if(isNull(region) || 'select' ==  region.toLocaleLowerCase()){
			$("#regionList").focus();
	    }
		if(isNull(product)){
			$("#product").focus();
		}	
		
	
	}
	else{
		
		if(region.toLocaleLowerCase()=='select'){
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
 		var productName = document.getElementById('product').options[document.getElementById('product').selectedIndex].text;	
 		var salesPersonName = document.getElementById('salesPerson').options[document.getElementById('salesPerson').selectedIndex].text;
 		var slabValue = document.getElementById('slab').options[document.getElementById('slab').selectedIndex].text;
 		var businessName = document.getElementById('business').options[document.getElementById('business').selectedIndex].text;
 		 if (station.toLocaleLowerCase() =='all'){
 			 station==0;
 		 }   
 		
 		/*//For Not mandatory Fields
 		if(originStationName.toLocaleLowerCase()=='select' ){
 			originStationName='';		
		}
 		if(originBranchName.toLocaleLowerCase()=='select' ){
 			originBranchName='';		
		}*/ 		
			var url = '/udaan-report/pages/reportviewer/salesProspectViewer.jsp?'					
			 + "&Region="+region
			 + "&Station="+station
			 + "&Branch="+branch	
			 + "&salesPerson="+salesPerson
			 + "&business="+business
			 + "&product="+product
			 + "&slabList="+slabList
			 + "&lowerlimit="+lowerlimit
			 + "&upperlimit="+upperlimit
			 + "&originRegionName="+originRegionName
			 + "&originStationName="+originStationName
			 + "&originBranchName="+originBranchName
			 + "&productName="+productName
			 + "&salesPersonName="+salesPersonName
			 + "&slabValue="+slabValue
			 + "&businessName="+businessName;
			
				
		//alert(url);
		 	window.open(url, "_blank");
		 	
		 	
		
		 	
	
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

function getSalesPersonList(obj) {
	if (obj.value != "" && obj.value.toUpperCase() != "SELECT") {
		var branch = $("#branchList").val();
		var region = $("#regionList").val();
		var station = $("#stationList").val();
		if (region.toLocaleLowerCase() == 'select' || region.toLocaleLowerCase() == "all") {
			region = "";
		}
		if (station.toLocaleLowerCase() == 'select' || station.toLocaleLowerCase() == "all") {
			station = "";
		}
		if (branch.toLocaleLowerCase() == 'select' || branch.toLocaleLowerCase() == "all") {
			branch = "";
		}
		$("#salesPerson").empty();
		var url = './salesProspectsReport.do?submitName=getSalesPersonList&region='
				+ region + "&branch=" + branch + "&station=" + station;
		ajaxCallWithoutForm(url, callBackGetSalesList);
	}
}


