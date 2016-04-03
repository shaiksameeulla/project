var seriesArray=[];
//var prevSeries="";

function getFormattedDate(input){
    var pattern=/(.*?)\/(.*?)\/(.*?)$/;
    var result = input.replace(pattern,function(match,p1,p2,p3){
        var months=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        return (months[(p2-1)]+" "+p1+", "+p3);
    });
    return result;
}
function getBrrReport1() {
	var report=$("#Name").val();
	alert(report);
	var status=$("#status").val();
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var office=$("#branchList").val();
	var series=$("#series").val();
	var type=$("#type").val();
	var category=$("#category").val();
	var load=$("#load").val();
	if(region=="Select"|| region=="All")region="";
	if(station=="Select"|| station=="All")station="";
	if(office=="Select"|| office=="All")office="";
	if(series=="Select"|| series=="All")series="";
	if(type=="Select"|| type=="All")type="";
	if(category=="Select"|| category=="All")category="";
	if(load=="Select"|| load=="All")load="";
	var fromdate=$("#fromdate").val();
	var todate=$("#todate").val();
	fromdate=getFormattedDate(fromdate);
	todate=getFormattedDate(todate);	
	if(isNull(region)){
			alert('Select Mandatory Field ');
			$("#regionList").focus();
	}
	else if(fromdate==null || fromdate==""){
		alert('Please Select the Date Range');
		$("#fromdate").focus();
	}
	else if(todate==null || todate==""){
		alert('Please Select the Date Range');
		$("#todate").focus();
	}
	else if(new Date(fromdate)>new Date()){
		alert("Date Can Not Be Future Date");
		$("#fromdate").focus();
	}
	else if(new Date(todate)>new Date()){
		alert("Date Can Not Be Future Date");
		$("#todate").focus();
	}
	else if(new Date(fromdate)>new Date(todate)){
		alert('Please Enter Valid Date Range');
		$("#fromdate").focus();
	}
	else{
		if(fromdate==todate)todate=todate.concat(" 11:59 PM");
		var url = '/udaan-report/pages/reportviewer/brrCommonReportViewer.jsp?'
			 + "&region="+region 
			 + "&station="+station 
			 + "&office="+office 
			 + "&status="+status
			 + "&series="+series 
			 + "&type="+type 
			 + "&category="+category
			 + "&load="+load
			 + "&fromdate="+fromdate
			 + "&todate="+todate;
		alert(url);
	
	 	window.open(url, "_blank");
	}
	
}


function getBrrReport() {
	var report=$("#Name").val();
	if(report=="Detailed"){
		if(checkMandatoryForBrr()){
			if(checkValidDate()){
				var stationName="";
				var stationId="";
				var officeName="";
				var officeId="";
				var typeName="";
				var categoryName="";
				var typeCode="";
				var categoryCode="";
				var loadId="";
				var status=$("#status").val();
				var region=$("#regionList").val();
				//var regionName=$("#regionList").text();
				var regionName=$("#regionList option:selected").text();
				var station=$("#stationList").val();
				if(station=='All'){
					 stationName="ALL";
					 stationId=0;
				}else{
					 stationName=$("#stationList option:selected").text();
					 stationId=$("#stationList").val();
				}
				
				var office=$("#branchList").val();
				if(office=='All'){
					 officeName="ALL";
					 officeId=0;
				}else{
					officeName=$("#branchList option:selected").text();
					officeId=$("#branchList").val();
				}
				
				var load=$("#load").val();
				if(load=='ALL'){
					loadId=0;
				}else{
					loadId=$("#load").val();
				}
				var series=$("#series").val();
				var typeval=$("#type").val();
				if(typeval=='ALL'){
					
					typeName="ALL";
					typeCode="ALL";
				}
				else{
					typeName=$("#type option:selected").text();
					typeCode=$("#type").val();
				}
				
				var categoryVal=$("#category").val();
                 if(categoryVal=='ALL'){
					
                	 categoryName="ALL";
                	 categoryCode="ALL";
				}
				else{
					categoryName= $("#category option:selected").text();
					categoryCode=$("#category").val();
				}
				
				
				var fromdate=$("#fromdate").val();
				var todate=$("#todate").val();
				
				var arrStartDate = fromdate.split("/");
				var finalStartDate=arrStartDate[2]+'-'+arrStartDate[1]+'-'+arrStartDate[0];
				var arrTodate = todate.split("/");
				var finalTodate=arrTodate[2]+'-'+arrTodate[1]+'-'+arrTodate[0];
				
				var url = '/udaan-report/pages/reportviewer/brrCommonReportViewer.jsp?'
					 + "&ReportType="+status 
					 + "&RegionId="+region 
					 + "&StationId="+stationId 
					 + "&BranchId="+officeId 
					 + "&LoadNumber="+loadId
					 + "&Series="+series 
					 + "&ConsignmentTypeCode="+typeCode
					 + "&DeliveryCategoryCode="+categoryCode
					 + "&FromDate="+finalStartDate
					 + "&ToDate="+finalTodate
					 + "&Category="+categoryName
					 + "&RegionName="+regionName
					 + "&StationName="+stationName
					 + "&BranchName="+officeName;
				 window.open(url, "_blank");
				
			}
		}
		
	}
	
	
	if(report=="Summary"){
		if(checkMandatoryForBrrSummary()){
			if(checkValidDate()){
				var stationName="";
				var stationId="";
				
				var typeName="";
				var categoryName="";
				var typeCode="";
				var categoryCode="";
				var region=$("#regionList").val();
				//var regionName=$("#regionList").text();
				var regionName=$("#regionList option:selected").text();
				var station=$("#stationList").val();
				if(station=='All'){
					 stationName="ALL";
					 stationId=0;
				}else{
					 stationName=$("#stationList option:selected").text();
					 stationId=$("#stationList").val();
				}
				var series=$("#series").val();
				var typeval=$("#type").val();
				if(typeval=='ALL'){
					
					typeName="ALL";
					typeCode="ALL";
				}
				else{
					typeName=$("#type option:selected").text();
					typeCode=$("#type").val();
				}
				
				var categoryVal=$("#category").val();
                 if(categoryVal=='ALL'){
					
                	 categoryName="ALL";
                	 categoryCode="ALL";
				}
				else{
					categoryName= $("#category option:selected").text();
					categoryCode=$("#category").val();
				}
				
				
				var fromdate=$("#fromdate").val();
				var todate=$("#todate").val();
				
				var arrStartDate = fromdate.split("/");
				var finalStartDate=arrStartDate[2]+'-'+arrStartDate[1]+'-'+arrStartDate[0];
				var arrTodate = todate.split("/");
				var finalTodate=arrTodate[2]+'-'+arrTodate[1]+'-'+arrTodate[0];
				
				var url = '/udaan-report/pages/reportviewer/brrCommonReportAllIndiaViewer.jsp?'
					 + "&RegionId="+region 
					 + "&StationId="+stationId 
					 + "&FromDate="+finalStartDate
					 + "&ToDate="+finalTodate
					 + "&Series="+series 
					 + "&ConsignmentTypeCode="+typeCode
					 + "&Category="+categoryName
					 + "&RegionName="+regionName
					 + "&StationName="+stationName
					 + "&ConsignmentTypeName="+typeName
					 + "&DeliveryCategoryCode="+categoryCode;
					 
					
					
				 window.open(url, "_blank");
				
			}
		}
		
	}

	
	
	
}


function checkMandatoryForBrr(){
	var status=$("#status").val();
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var office=$("#branchList").val();
	var series=$("#series").val();
	var type=$("#type").val();
	var category=$("#category").val();
	var load=$("#load").val();
	var fromdate=$("#fromdate").val();
	var todate=$("#todate").val();
	
	if(isNull(status)){
		alert('Select Reports Field ');
		$("#status").focus();
		return false;
	}
	
	
	if(isNull(region)){
		alert('Select Region Field ');
		$("#regionList").focus();
		return false;
    }
	 
	
	if(isNull(station) || station=='Select'){
		alert('Select Station Field ');
		$("#stationList").focus();
		return false;
    }
	
	
	if(isNull(office) || office=='Select'){
		alert('Select Office Field ');
		$("#branchList").focus();
		return false;
    }
	
	
	if(isNull(series)){
		alert('Select Series Field ');
		$("#series").focus();
		return false;
    }
	
	
	if(isNull(type)){
		alert('Select Type Field ');
		$("#type").focus();
		return false;
    }
	
	
	if(isNull(category)){
		alert('Select Category Field ');
		$("#category").focus();
		return false;
    }
	
	
	if(isNull(load)){
		alert('Select Load Field ');
		$("#load").focus();
		return false;
    }
	
	
	if(isNull(fromdate)){
		alert('Select FromDate Field ');
		$("#fromdate").focus();
		return false;
    }
	
	if(isNull(todate)){
		alert('Select ToDate Field ');
		$("#todate").focus();
		return false;
    }
	return true;
}


function checkValidDate(){
	var startDate=$("#fromdate" ).val();
	var endDate=$("#todate" ).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);
	var currentdate = new Date();
	if(date1 >currentdate){
		alert("Start Date Can't be Greater that Current date");

		$("#fromdate" ).val("");

		$("#fromdate" ).focus();
		return false;
	}
	if(date1=="" || date1>date2){

		alert("Please ensure that end date is greater than or equal to start date");

		$("#fromdate" ).val("");

		$("#fromdate" ).focus();

		return false;

		}
	else {
		var currentdate = new Date();
		if(date2 >currentdate ){
			alert("End Date Can't be Greater that Current date");
			$("#todate" ).val("");
			$("#todate" ).focus();
			return false;
		}
		return true;
	}
}

function checkMandatoryForBrrSummary(){
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var series=$("#series").val();
	var type=$("#type").val();
	var category=$("#category").val();
	var fromdate=$("#fromdate").val();
	var todate=$("#todate").val();
	//var office=$("#branchList").val();
	
	if(isNull(region)){
		alert('Select Region Field ');
		$("#regionList").focus();
		return false;
    }
	 
	
	if(station == null || station == "" || station=='Select'){
		alert('Select Station Field ');
		$("#stationList").focus();
		return false;
    }
	
	/*if(isNull(office) || office=='Select'){
		alert('Select Office Field ');
		$("#branchList").focus();
		return false;
    }*/
	
	if(isNull(series)){
		alert('Select Series Field ');
		$("#series").focus();
		return false;
    }
	
	
	if(isNull(type) || type=='Select'){
		alert('Select Type Field ');
		$("#type").focus();
		return false;
    }
	
	
	if(isNull(category) || category=='Select'){
		alert('Select Category Field ');
		$("#category").focus();
		return false;
    }
	
	
	if(isNull(fromdate)){
		alert('Select FromDate Field ');
		$("#fromdate").focus();
		return false;
    }
	
	if(isNull(todate)){
		alert('Select ToDate Field ');
		$("#todate").focus();
		return false;
    }
	return true;
}



/**
 * clearScreen
 * @param type
 */
function clearScreen() {
	if(confirm("Do you want to clear the filter details?")) {
		var url="./brrReport.do?submitName=viewFormDetails";
		globalFormSubmit(url,'consignmentReportForm');
		var seriesList=getDomElementById("series");
		for (var i=0; i<seriesList.length; i++) {
			seriesList[i].disabled =  false;
			}
		
		}
}
function clearBrrSummaryScreen() {
	if(confirm("Do you want to clear the filter details?")) {
		window.location.reload();
		var region=$("#regionList").val();
		var station=$("#stationList").val();
		if (isNull(region)){
			var content = document.getElementById('stationList');
			content.innerHTML = "";
			var defOption;
			defOption = document.createElement("option");
			defOption.value = "Select";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
		}
        if (isNull(station)){
        	var content = document.getElementById('branchList');
			content.innerHTML = "";
			var defOption;
			defOption = document.createElement("option");
			defOption.value = "Select";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
		}
		var seriesList=getDomElementById("series");
		for (var i=0; i<seriesList.length; i++) {
			seriesList[i].disabled =  false;
			}
		
		}
}

function clearScreenForBrrDateWiseStatus() {
	if(confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();
		var seriesList=getDomElementById("series");
		for (var i=0; i<seriesList.length; i++) {
			seriesList[i].disabled =  false;
			}
		
		}
	
}

function getBrrDateWiseStatus(){
	if(checkMandatoryForBrrDate()){
		if(checkValidDate()){
			var stationName="";
			var stationId="";
			var branchName="";
			var branchId="";
			var typeName="";
			var categoryName="";
			var typeCode="";
			var categoryCode="";
			var region=$("#regionList").val();
			//var regionName=$("#regionList").text();
			var regionName=$("#regionList option:selected").text();
			var station=$("#stationList").val();
			if(station=='All'){
				 stationName="ALL";
				 stationId=0;
			}else{
				 stationName=$("#stationList option:selected").text();
				 stationId=$("#stationList").val();
			}
			var office=$("#branchList").val();
			if(office=='All'){
				branchName="ALL";
				 branchId=0;
			}else{
				branchName=$("#branchList option:selected").text();
				branchId=$("#branchList").val();
			}
			
			// var series=$("#series").val();
			var series = GetSelected(document.getElementById("series"));
			var typeval=$("#type").val();
			if(typeval=='ALL'){
				
				typeName="ALL";
				typeCode="ALL";
			}
			else{
				typeName=$("#type option:selected").text();
				typeCode=$("#type").val();
			}
			
			var categoryVal=$("#category").val();
             if(categoryVal=='ALL'){
				
            	 categoryName="ALL";
            	 categoryCode="ALL";
			}
			else{
				categoryName= $("#category option:selected").text();
				categoryCode=$("#category").val();
			}
			
			
			var fromdate=$("#fromdate").val();
			var todate=$("#todate").val();
			
			var arrStartDate = fromdate.split("/");
			var finalStartDate=arrStartDate[2]+'-'+arrStartDate[1]+'-'+arrStartDate[0];
			var arrTodate = todate.split("/");
			var finalTodate=arrTodate[2]+'-'+arrTodate[1]+'-'+arrTodate[0];
			
			var url = '/udaan-report/pages/reportviewer/brrDateWiseStatusViewer.jsp?'
				 + "&RegionId="+region 
				 + "&StationId="+stationId 
				 + "&BranchId="+branchId 
				 + "&FromDate="+finalStartDate
				 + "&ToDate="+finalTodate
				 + "&Series="+series 
				 + "&ConsignmentTypeCode="+typeCode
				 + "&Category="+categoryName
				 + "&RegionName="+regionName
				 + "&StationName="+stationName
				 + "&BranchName="+branchName
				 + "&ConsignmentTypeName="+typeName
				 + "&DeliveryCategoryCode="+categoryCode;
				 
				
				
			 window.open(url, "_blank");
			
		}
	}
	
}



function getBrrDetailReport(){
	if(checkMandatoryForBrrDetail()){
		if(checkValidDate()){
			var stationName="";
			var stationId="";
			var branchName="";
			var branchId="";
			var typeName="";
			var categoryName="";
			var typeCode="";
			var categoryCode="";
			var region=$("#regionList").val();
			//var regionName=$("#regionList").text();
			var regionName=$("#regionList option:selected").text();
			var station=$("#stationList").val();
			if(station=='All'){
				 stationName="ALL";
				 stationId=0;
			}else{
				 stationName=$("#stationList option:selected").text();
				 stationId=$("#stationList").val();
			}
			var office=$("#branchList").val();
			if(office=='All'){
				branchName="ALL";
				 branchId=0;
			}else{
				branchName=$("#branchList option:selected").text();
				branchId=$("#branchList").val();
			}
			
			// var series=$("#series").val();
			var series = GetSelected(document.getElementById("series"));
			var typeval=$("#type").val();
			if(typeval=='ALL'){
				
				typeName="ALL";
				typeCode="ALL";
			}
			else{
				typeName=$("#type option:selected").text();
				typeCode=$("#type").val();
			}
			
			var categoryVal=$("#category").val();
             if(categoryVal=='ALL'){
				
            	 categoryName="ALL";
            	 categoryCode="ALL";
			}
			else{
				categoryName= $("#category option:selected").text();
				categoryCode=$("#category").val();
			}
			
             var loadVal=$("#load").val();
             if(loadVal=='ALL'){
				
            	 loadVal="ALL";
            	 loadVal="ALL";
			}
			else{
				loadVal= $("#load option:selected").text();
				loadVal=$("#load").val();
			}
             
             
             
			
			var fromdate=$("#fromdate").val();
			var todate=$("#todate").val();
			
			var arrStartDate = fromdate.split("/");
			var finalStartDate=arrStartDate[2]+'-'+arrStartDate[1]+'-'+arrStartDate[0];
			var arrTodate = todate.split("/");
			var finalTodate=arrTodate[2]+'-'+arrTodate[1]+'-'+arrTodate[0];			
			var reportType=$("#reportType").val();
			var reportTypeName=$("#reportType option:selected").text();
			
			
			
			var url = '/udaan-report/pages/reportviewer/brrDetailReportViewer.jsp?'
				 + "&RegionId="+region 
				 + "&StationId="+stationId 
				 + "&BranchId="+branchId 
				 + "&FromDate="+finalStartDate
				 + "&ToDate="+finalTodate
				 + "&Series="+series 
				 + "&ConsignmentTypeCode="+typeCode
				 + "&Category="+categoryName
				 + "&RegionName="+regionName
				 + "&StationName="+stationName
				 + "&BranchName="+branchName
				 + "&ConsignmentTypeName="+typeName
				 + "&DeliveryCategoryCode="+categoryCode
				 + "&ReportType="+reportType
				 + "&ReportTypeName="+reportTypeName
				 + "&LoadNumber="+loadVal;
				 
			 window.open(url, "_blank");
			
		}
	}
	
}
	


function checkAll(){
	var series=$("#series").val();
	var seriesList=getDomElementById("series");
	//seriesArray.push(series);
	/*
	alert(seriesArray);
	var seriesList=getDomElementById("series");
	if(seriesList.length!=0){ 
		for (var i=0; i<seriesList.length; i++) {
			  if (seriesList[i].selected) {
				  count=count+1;
			  }
			}
	
	}
	
	if(count>1){
         for(var i=0;i<seriesArray.length;i++){
        	 if(seriesArray[i]=='ALL'){
        		 alert('You have selected ALL option you can not select other option');
        		 seriesList[i].focus();
        	 }
         }		
	}*/
	
	if(series=='ALL'){
		for (var i=1; i<seriesList.length; i++) {
			seriesList[i].disabled =  true;
			}
	}
	else{
		seriesList[0].disabled =  true;
	}
	
	
}


function showBrrSummary() {

	var RegionId = $("#regionList").val();
	var RegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var StationId = $("#stationList").val();
	var StationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	var BranchId = $("#branchList").val();
	var BranchName = document.getElementById('branchList').options[document
			.getElementById('branchList').selectedIndex].text;
	var LoadNumber = $("#load").val();
	var LoadDescription = document.getElementById('load').options[document
			.getElementById('load').selectedIndex].text;
	var Series = GetSelected(document.getElementById("series"));
	var SeriesName=Series;
	var ConsignmentTypeCode = $("#type").val();
	var ConsignmentTypeName = document.getElementById('type').options[document
			.getElementById('type').selectedIndex].text;
	var DeliveryCategoryCode = $("#category").val();
	var Category = document.getElementById('category').options[document
			.getElementById('category').selectedIndex].text;
	var Date = $("#date").val();

	if ((RegionId == null || 'select' == RegionId.toLocaleLowerCase())
			|| (isNull(StationId)|| 'select' == StationId.toLocaleLowerCase())
			|| (BranchId == null || 'select' == BranchId.toLocaleLowerCase())
			|| (LoadNumber == null || 'select' == LoadNumber
					.toLocaleLowerCase())
			|| (isNull(Series) == null || 'select' == Series.toLocaleLowerCase())
			|| (ConsignmentTypeCode == null || 'select' == ConsignmentTypeCode
					.toLocaleLowerCase())
			|| (DeliveryCategoryCode == null || 'select' == DeliveryCategoryCode
					.toLocaleLowerCase())
			|| (isNull(Date) || 'select' == Date.toLocaleLowerCase())) {

		if (isNull(RegionId) || RegionId == 'Select') {
			alert('Select Region Field ');
			$("#regionList").focus();
			return false;
		}

		if (isNull(StationId) || StationId == 'Select') {
			alert('Select Station Field ');
			$("#stationList").focus();
			return false;
		}

		if (isNull(BranchId) || BranchId == 'Select') {
			alert('Select Branch Field ');
			$("#branchList").focus();
			return false;
		}

		if (isNull(LoadNumber)) {
			alert('Select Load Number Field ');
			$("#load").focus();
			return false;
		}

		if (isNull(Series) || Series == 'Select') {
			alert('Select Series Field ');
			$("#series").focus();
			return false;
		}
		
		if (isNull(Date)) {
			alert('Select Date Field ');
			$("#date").focus();
			return false;
		}

		if (isNull(ConsignmentTypeCode) || ConsignmentTypeCode == 'Select') {
			alert('Select Consignment Type Field ');
			$("#type").focus();
			return false;
		}

		if (isNull(DeliveryCategoryCode) || DeliveryCategoryCode == 'Select') {
			alert('Select Category Field ');
			$("#category").focus();
			return false;
		}

		return true;
	} else {
		
		if(BranchId == 'All') {
			BranchId = 0;
			BranchName = 'All';
		}
		
		if(LoadNumber == 'All') {
			LoadNumber = 0;
			LoadDescription = 'All';
		}
		
		if (type == 'All') {
			type = 'ALL';
		}
		
		if(Series == "ALL") {
			Series="A,B,M,L,C,E,D,N,P,Q,X,T,S,R,Z";
			SeriesName=$("#series").val();
		}
		
		//var Date = $("#fromDate").val();
		//var toDate = $("#toDate").val();
		var arrDate = Date.split("/");
		var dateFormat=arrDate[2]+'-'+arrDate[1]+'-'+arrDate[0];
		
		var url = '/udaan-report/pages/reportviewer/brrSummaryViewer.jsp?'
				+ "&RegionId="
				+ RegionId
				+ "&RegionName="
				+ RegionName
				+ "&StationId="
				+ StationId
				+ "&StationName="
				+ StationName
				+ "&BranchId="
				+ BranchId
				+ "&BranchName="
				+ BranchName
				+ "&LoadNumber="
				+ LoadNumber
				+ "&LoadDescription="
				+ LoadDescription
				+ "&Series="
				+ Series
				+ "&SeriesName="
				+ SeriesName
				+ "&ConsignmentTypeCode="
				+ ConsignmentTypeCode
				+ "&ConsignmentTypeName="
				+ ConsignmentTypeName
				+ "&DeliveryCategoryCode="
				+ DeliveryCategoryCode
				+ "&Category="
				+ Category
				+ "&Date="
				+ dateFormat;
		window.open(url, "_blank");
	}
}


function checkMandatoryForBrrDate(){
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var series=$("#series").val();
	var type=$("#type").val();
	var category=$("#category").val();
	var fromdate=$("#fromdate").val();
	var todate=$("#todate").val();
	var office=$("#branchList").val();
	var loadVal=$("#load").val();
	//var reportType=$("#reportType").val();
	
	
	/*if(isNull(reportType) || reportType=='Select'){
		alert('Select ReportType Field ');
		$("#reportType").focus();
		return false;
    }*/
	
	if(isNull(region)){
		alert('Select Region Field ');
		$("#regionList").focus();
		return false;
    }
	 
	
	if(isNull(station) || station=='Select'){
		alert('Select Station Field ');
		$("#stationList").focus();
		return false;
    }
	
	if(isNull(office) || office=='Select'){
		alert('Select Office Field ');
		$("#branchList").focus();
		return false;
    }
	
	if(isNull(series)){
		alert('Select Series Field ');
		$("#series").focus();
		return false;
    }
	
	
	if(isNull(type) || type=='Select'){
		alert('Select Type Field ');
		$("#type").focus();
		return false;
    }
	
	
	if(isNull(category) || category=='Select'){
		alert('Select Category Field ');
		$("#category").focus();
		return false;
    }
	
	
	if(isNull(fromdate)){
		alert('Select FromDate Field ');
		$("#fromdate").focus();
		return false;
    }
	
	if(isNull(todate)){
		alert('Select ToDate Field ');
		$("#todate").focus();
		return false;
    }
	return true;
}


function checkMandatoryForBrrDetail(){
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var series=$("#series").val();
	var type=$("#type").val();
	var category=$("#category").val();
	var fromdate=$("#fromdate").val();
	var todate=$("#todate").val();
	var office=$("#branchList").val();
	var loadVal=$("#load").val();
	var reportType=$("#reportType").val();
	
	
	if(isNull(reportType) || reportType=='Select'){
		alert('Select ReportType Field ');
		$("#reportType").focus();
		return false;
    }
	
	if(isNull(region)){
		alert('Select Region Field ');
		$("#regionList").focus();
		return false;
    }
	 
	
	if(isNull(station) || station=='Select'){
		alert('Select Station Field ');
		$("#stationList").focus();
		return false;
    }
	
	if(isNull(office) || office=='Select'){
		alert('Select Office Field ');
		$("#branchList").focus();
		return false;
    }
	
	if(isNull(series)){
		alert('Select Series Field ');
		$("#series").focus();
		return false;
    }
	
	
	if(isNull(type) || type=='Select'){
		alert('Select Type Field ');
		$("#type").focus();
		return false;
    }
	
	
	if(isNull(category) || category=='Select'){
		alert('Select Category Field ');
		$("#category").focus();
		return false;
    }
	
	
	if(isNull(fromdate)){
		alert('Select FromDate Field ');
		$("#fromdate").focus();
		return false;
    }
	
	if(isNull(todate)){
		alert('Select ToDate Field ');
		$("#todate").focus();
		return false;
    }
	return true;
}