
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
var selectedRegions =0;
var selectedStations=0;
var selectedBranches=0;

function GetSelected (selectTag) {	
	
    var selIndexes = "";

    for (var i = 0; i < selectTag.options.length; i++) {
        var optionTag = selectTag.options[i];
        if (optionTag.selected) {
            if (selIndexes.length > 0)
                selIndexes += ",";
            selIndexes += optionTag.value;
        }
    }    
    return selIndexes;
   
}

function GetSelectedBranches (param1) {	
	var selected = GetSelected(param1);
	selectedBranches=selected;
}

/**
 * clearScreen
 */
function clearScreen(){	
	var url="./yieldReport.do?submitName=getYieldReport";
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

function getSelectedValues(id) {
    var select1 = document.getElementById(id); 
    var selected1 = [];
    while(select1.selectedIndex != -1) {
      if(select1.selectedIndex != 0) selected1.push(select1.options[select1.selectedIndex].value); 
      select1.options[select1.selectedIndex].selected = false;
   }
   return selected1;
}

function getOrgStationsList(param1){	
	clearFields();	
	selectedRegions=0;
	selectedStations=0;
	selectedBranches=0;
	
	var selected = GetSelected(param1);
	selectedRegions=selected;	
	if(selected!='' && selected!='Select' ){		
		var url = './yieldReport.do?submitName=getStations&region='
			+ selected;
		ajaxCallWithoutForm(url, getOrgStationList);
	}
	
}



function getOrgStationList(data){	
	if (!isNull(data)) {	
			var content = document.getElementById('originStation');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "Select";
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





function getBranchList(id,param2){	
	
	getDomElementById("branchList").options.length = 1;
	selectedStations=0;
	selectedBranches=0;
	var selected = GetSelected(param2);		
	selectedStations=selected;		
	if(selected!='' && selected!='Select'){
	var url = './yieldReport.do?submitName=getBranchList&cityID=' + selected;
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






function printReport(){	

	
	var region = selectedRegions;	
	var station = selectedStations;		
	var branch = selectedBranches;
	
	
	if(document.getElementById("originRegion").disabled){
		region = document.getElementById("originRegion").value;
	}
	else{
		region = selectedRegions;	
	}
	
	if(document.getElementById("originStation").disabled){
		station = document.getElementById("originStation").value;
	}
	else{
		station = selectedStations;	
	}
	
	if(document.getElementById("branchList").disabled){
		branch = document.getElementById("branchList").value;
	}
	else{
		branch = selectedBranches;	
	}	
	
	
	var month1=$("#month1" ).val();
	var month2=$("#month2" ).val();
	
			
/*	var frommonthappend="01-";
	var tomonthappend="31-";
	var frommonth=frommonthappend.concat(month1);
	var tomonth=tomonthappend.concat(month2);
*/
		
	var product=$("#productTo" ).val();	
	var productName=$('#productTo option:selected' ).text();	
	
	if(product == ''){
		productName= new Array();
		var ddl = document.getElementById('productTo');
		for (var i = 0; i < ddl.options.length; i++) {
			productName[i] = ddl .options[i].text ;	
		}	
		productName=productName.slice(1);
	}
	else{
		productName=$('#productTo option:selected' ).text();	
	}
	if(product == ''){
		productName = 'All';
	}
	
	if(product == ''){
		product= new Array();
		var ddl = document.getElementById('productTo');
		for (i = 0; i < ddl.options.length; i++) {
			product[i] = ddl .options[i].value ;	
		}	
		product=product.slice(1);		
	}
	else{
		product=$("#productTo" ).val();		
	}
	

	if(isNull(region) || 'select' == region.toLocaleLowerCase() || isNull(month1)|| isNull(month2)  ){
		
		alert('Select Mandatory Field ');
		if(isNull(region)){
			$("#originRegion").focus();
		}
		else if(isNull(month1)){
			$("#month1").focus();
		}	
		else if(isNull(month2)){
			$("#month2").focus();
		}	
	  	
	}
	else{

		if(checkMonth('month1', 'month2')){	

			
			if(region == 0){
				region = '';
			}
			if(station == 0){
				station = '';
			}	
			if(branch == 0){		
				branch = '';
			}
			
		    var originRegionName = $("#originRegion option:selected").map(function(){ return this.text;}).get().join(", ");
			var originStationName =  $("#originStation option:selected").map(function(){ return this.text;}).get().join(", ");
			var originBranchName = $("#branchList option:selected").map(function(){ return this.text;}).get().join(", ");
			//var productName = $("#product option:selected").map(function(){ return this.text;}).get().join(", ");
					
			var url = '/udaan-report/pages/reportviewer/yieldViewer.jsp?'					
			 + "&Region="+region
			 + "&Station="+station
			 + "&Branch="+branch	
			 + "&month1="+month1
			 + "&month2="+month2			
			 + "&product="+product
			 + "&originRegionName="+originRegionName
			 + "&originStationName="+originStationName
			 + "&originBranchName="+originBranchName
			 + "&productName="+productName;
			
				
		
		 	window.open(url, "_blank");
		 	
		 	
		}
		 	
	
	}

 }

function checkMonth(fromDate, toDate) {
	var startDate = $("#" + fromDate).val();
	var endDate = $("#" + toDate).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[1], arrEndDate[0] - 1, 1);
	var today = new Date();
	if (date2 > today) {
		alert('Future Month cannot be entered');
		$("#" + toDate).val("");

		$("#" + toDate).focus();
		return false;
	}

	if (date1 == "" || date1 > date2) {

		alert("Please ensure that to Month is greater than or equal to from month");

		$("#" + fromDate).val("");

		$("#" + fromDate).focus();

		return false;

	} else {
		return true;
	}
}



function checkDate(){
	var startDate=$("#month1" ).val();
	var endDate=$("#month2" ).val();
	
	var append = "01/";
	
	//from Date
	var replace= startDate.replace("-","/");	
	var month1=append.concat(replace);	
	var date1=new Date(month1);
	
	
	//to Date
	var replace1= endDate.replace("-","/");	
	var month2=append.concat(replace1);	
	var date2=new Date(month2);	
	
	
	var today = new Date();
	
	if (date2 > today) {
		alert('Future Month cannot be entered');
		return false;
	}
	if(date1=="" || date1>date2){

		alert("Please ensure that to month is greater than or equal to from month");

		$("#month1" ).val("");

		$("#month1" ).focus();

		return false;

		}
	else {
		return true;
	}


}



function getProductList(id){
	getDomElementById("product").options.length = 1;	
	var customerID=$("#"+id).val();	
	var url = './yieldReport.do?submitName=getProductList&customerID=' + customerID;	
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
			var content = document.getElementById('product');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.rateProductCategoryId;
				option.appendChild(document.createTextNode(this.rateProductCategoryName));
				content.appendChild(option);
	});
   }
  
}




