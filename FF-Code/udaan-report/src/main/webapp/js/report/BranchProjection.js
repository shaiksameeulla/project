function is_int(value){ 
  if((parseFloat(value) == parseInt(value)) && !isNaN(value)){
      return true;
  } else { 
      return false;
  } 
}
function branchProjectionDetails() {
	var regionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	var branchName = document.getElementById('branchList').options[document
			.getElementById('branchList').selectedIndex].text;
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var product = $("#product").val();
	var productName = document.getElementById('product').options[document
			.getElementById('product').selectedIndex].text;
	var expectedDays = $("#expectedDays").val();

	if (fromDate != "" && toDate != "") {
		var d = fromDate;
		var t = new Date(toDate);
		var month = new Array();
		month[0] = "January";
		month[1] = "February";
		month[2] = "March";
		month[3] = "April";
		month[4] = "May";
		month[5] = "June";
		month[6] = "July";
		month[7] = "August";
		month[8] = "September";
		month[9] = "October";
		month[10] = "November";
		month[11] = "December";
		/*var startMonth = (new Date(fromDate)).getMonth();
		var endMonth = (new Date(toDate)).getMonth();*/
		var endMonth = t.getDate();
		var monthArray = new Array();
		monthArray = getMonthsBetweenStartAndEndDate(d, endMonth);
		var monthNames = "";

		for ( var i = 0; i < monthArray.length; i++) {
			if (monthNames.length > 0) {
				monthNames = monthNames + ",";
			}
			monthNames = monthNames + "'" + monthArray[i] + "'";
		}
	}

	if (region.toLocaleLowerCase() == 'select') {
		region = "";
	}
	if (station.toLocaleLowerCase() == 'select'
			|| station.toLocaleLowerCase() == 'all') {
		station = 0;
	}
	if (branch.toLocaleLowerCase() == 'select'
			|| branch.toLocaleLowerCase() == 'all') {
		branch = 0;
	}
	if (product.toLocaleLowerCase() == 'all') {
		product = "";
	}
	/*
	 * if(isNull(region)|| isNull(station)||isNull(branch)){ alert('Select
	 * Mandatory Field'); if(isNull(region)){ $("#regionList").focus(); } else
	 * if(isNull(station)){ $("#stationList").focus(); } else
	 * if(isNull(branch)){ $("#branchList").focus(); } } else
	 */
	if (isNull(expectedDays)) {
		expectedDays = "0";
	}
	if (isNull(region)) {
		// alert('Select All the Mandatory Fields');
		alert('Select the region field');
		if (isNull(region)) {
			$("#regionList").focus();
		}
	} else if (fromDate == null || fromDate.trim().length <= 0) {
		alert('Please Select the Date Range');
		$("#fromDate").focus();

	} else if (toDate == null || toDate.trim().length <= 0) {
		alert('Please Select the Date Range');
		document.getElementById('toDate').value = "";
		$("#toDate").focus();

	} else if (formatDate(fromDate) > new Date()) {
		alert("From Date Can Not Be Future Date");
		document.getElementById('fromDate').value = "";
		$("#fromDate").focus();

	} else if (formatDate(toDate) > new Date()) {
		alert("ToDate Can Not Be Future Date");
		document.getElementById('toDate').value = "";
		$("#toDate").focus();

	} else if (formatDate(fromDate) > formatDate(toDate)) {
		alert('From Date cannot be greater than To Date');
		document.getElementById('fromDate').value = "";
		$("#fromDate").focus();

	}
	/*
	 * else if(!is_int(expectedDays)|| parseInt(expectedDays)<0||parseInt(expectedDays)==0||parseInt(expectedDays)==00){
	 * alert('Please Enter Valid Number');
	 * document.getElementById('expectedDays').value="";
	 * $("#expectedDays").focus(); }else if(parseInt(expectedDays)>99){
	 * alert('Expected Business Days Can Not Be More Than 99 Days');
	 * $("#expectedDays").focus(); }
	 */

	/*
	 * if( parseInt(expectedDays)<0||parseInt(expectedDays)==0||parseInt(expectedDays)==00){
	 * alert('Please Enter Valid Number');
	 * document.getElementById('expectedDays').value="";
	 * $("#expectedDays").focus(); }
	 */

	/*
	 * if(parseInt(expectedDays)>99){ alert('Expected Business Days Can Not Be
	 * More Than 99 Days'); $("#expectedDays").focus(); }
	 */
	else {
		var days = Math.round((formatDate(toDate).getTime() - formatDate(
				fromDate).getTime()) / 86400000);
		if (fromDate == toDate) {
			toDate = toDate.concat(" 11:59 PM");
			days = 1;
		}
		var nwFromDate = formatDatetoStr(fromDate);
		var nwToDate = formatDatetoStr(toDate);
		
		/*var ProjectedDays = calcWorkingDays();
		alert(ProjectedDays);*/
		
		
		/*if(ProjectedDays == null){
			ProjectedDays = 0;
		}*/
		var ProjectedDays = $("#workingDays").val();
		
		var mdy = fromDate.split('/');
		var mdy2 = toDate.split('/');
		var pass1 = mdy[1] + '/' + mdy[0] + '/'+mdy[2];
		var pass2 = mdy2[1] + '/' + mdy2[0] + '/' +mdy2[2];
		
		
		var startDate = new Date(pass1);
		var endDate = new Date(pass2);
		var totalSundays = 0;

		for (var i = startDate; i <= endDate; ){
		    if (i.getDay() == 0){
		        totalSundays++;
		    }
		    i.setTime(i.getTime() + 1000*60*60*24);
		}

		ProjectedDays = ProjectedDays - totalSundays;
		
		
		var url = '/udaan-report/pages/reportviewer/BranchProjectionViewer.jsp?'
				+ "&branch="
				+ branch
				+ "&regionName="
				+ regionName
				+ "&stationName="
				+ stationName
				+ "&branchName="
				+ branchName
				+ "&region="
				+ region
				+ "&station="
				+ station
				+ "&product="
				+ product
				+ "&productName="
				+ productName
				+ "&days="
				+ days
				+ "&expectedDays="
				+ parseInt(expectedDays)
				+ "&fromDate="
				+ nwFromDate
				+ "&toDate="
				+ nwToDate
				+ "&monthNames="
				+ monthNames
				+ "&ProjectedDays="
				+ parseInt(ProjectedDays);
		
		window.open(url, "_blank");
	}
}
function formatDatetoStr(str){
	var arrStartDate = str.split("/");
	return (arrStartDate[1]+"/"+ arrStartDate[0]+"/"+arrStartDate[2]);
}
function formatDate(str){
	var arrStartDate = str.split("/");
	return new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
}

function getMonthsBetweenStartAndEndDate(startDate,endMonth){

	var startMonth = parseInt(startDate.split('/')[1], 10),
    
    monthArray = [];

	if( startMonth < 1 ) return [];

	//monthArray.push(moment(startMonth, "M").format('MMMM'));

	for( var i = startMonth; i <= endMonth; i++ ){
		if( i > 12 ) i = 1;
		monthArray.push( moment(i, "M").format("MMMM") );
	}

	return monthArray;
}

function checkForOnlyNumeric(event){
	
	if(!onlyNumeric(event)){
		/*alert("Please enter only numeric value");
		document.getElementById('expectedDays').value="";
		$("#expectedDays").focus();*/
		return false;
		
	}
}

function calcWorkingDays1() {
	 
	var offs = "";
	var region = document.getElementById('regionList').value;
	var fromDate1 = $("#fromDate").val();
	var Month = fromDate1.split('/');
	 fromDate1 = Month[1]+"/" +Month[2];
	 
	 var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var branch = document.getElementById('branchList').value;
	var regioncheck = document.getElementById('regionList').value;
	var stationCheck = document.getElementById('stationList').value;

	if (regioncheck == 'Select' || isNull(regioncheck)) {
		alert('Please Select Region value ');
		$("#regionList").focus();
		return;
	}

	if (stationCheck == 'Select' || isNull(stationCheck)) {
		alert('Please Select Station value ');
		$("#stationList").focus();
		return;
	}

	if (branch == 'Select' || isNull(branch)) {
		alert('Please Select branch value ');
		$("#branchList").focus();
		return;
	}

	if (checkMonthSalesDsrReport1()) {
		if (!isNull(fromDate)) {
			showProcessing();
			var mdy = fromDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			/*var mdy2 = toDate.split('/');
			var yearTo = mdy2[2];
			var monthTo = mdy2[1];
			var days = Math.round(((new Date(year, month)) - (new Date(year,
					month - 1))) / 86400000);*/
			
			
			days = calculateDateDiff(fromDate, toDate);
			days = days + 1;
			
			
			
			/*var day1 = fromDate.split('/');
			var StartDay = day1[0];
			var day2 = toDate.split('/');
			var endDay = day2[0];
			var days = endDay - StartDay;
			days = days+1;*/
			/*
			 * var startDate= new Date( year, (month-1) , 01 ); var
			 * start1=startDate.toString(); var endDate= new Date( year,
			 * (month-1) , days);
			 */
			var startDate = year + "-" + month + "-" + '01';
			var endDate = year + "-" + month + "-" + days;
			var branch = document.getElementById('branchList').value;
			var branchList = document.getElementById('branchList');
			var regioncheck = document.getElementById('regionList').value;
			var stationCheck = document.getElementById('stationList').value;

			if (regioncheck == 'Select') {
				alert('Please Select Region value ');
				$("#regionList").focus();
				return;
			}

			if (stationCheck == 'Select') {
				alert('Please Select Station value ');
				$("#stationList").focus();
				return;
			}

			if (branch == 'Select') {
				alert('Please Select branch value ');
				$("#branchList").focus();
				return;
			}

			if (branch == 'All') {
				if (!isNull(branchList)) {
					for ( var i = 0; i < branchList.length; i++) {
						if (!isNull(branchList[i].value)) {
							if (branchList[i].value != 'All'
									&& branchList[i].value != 'Select') {
								if (isNull(offs)) {
									offs += branchList[i].value;
								} else {
									offs += "," + branchList[i].value;
								}
							}
						}
					}
				}
			} else {
				offs = "";
				offs = document.getElementById('branchList').value;
			}

			if (isNull(offs)) {
				alert('branch is empty');
				return;
			}
			var url = './salesReport.do?submitName=calcWorkingDays&region='
					+ region + "&fromDate=" + fromDate1 + "&totalDays=" + days;

			/*
			 * ajaxCallWithoutForm(url, calcwork); ajaxCall(url,
			 * "consignmentReportForm", calcwork);
			 */
			jQuery.ajax({
				url : url,
				type : "GET",
				dataType : "json",
				async : false,
				success : function(data) {
					calcwork1(data);
				}
			});
			
			
			

			/*
			 * document.consignmentReportForm.action=url;
			 * document.consignmentReportForm.submit();
			 */
			//var workingDays = document.getElementById('totWorking').value;
			/*var originRegionName = document.getElementById('regionList').options[document
					.getElementById('regionList').selectedIndex].text;
			var originStationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
			var originbranchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
			 var offs1="46,79,599"; 
			var url = '/udaan-report/pages/reportviewer/salesDsrViewer.jsp?'
					+ "&StartDate=" + startDate + "&EndDate=" + endDate
					+ "&BranchOffs=" + offs + "&WorkingDays="
					+ parseInt(workingDays) + "&originRegion="
					+ originRegionName + "&originStation=" + originStationName
					+ "&originBranch=" + originbranchName + "&forMonth="
					+ fromDate;

			window.open(url, "_blank");*/
			hideProcessing();
		} else {
			alert('Please select month');
			return;
		}
			return workingDays;
			
	}
	
}

function calcwork1(data) {

	if (!isNull(data)) {
		var responseText = eval(data);
//		alert("responseText : "+responseText);
		//var totWorking = responseText;
		$("#workingDays").val(responseText);
		/*var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}*/
		//document.getElementById("totWorking").value = totWorking;
	}
	
	//return totWorking;
}

function checkMonthSalesDsrReport1() {
	var fromDate = $("#fromDate").val();
	var arrStartDate = fromDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var today = new Date();
	if (date1 > today) {
		alert('You cannot select future month');
		$("#fromDate").val("");
		return false;
	}

	return true;
	hideProcessing();
}
function calculateDateDiff(fromDate, toDate) {
	var _fromDate = fromDate.split("/");
	var _toDate = toDate.split("/");
	// alert(" _heldUpDate &&&&& "+_heldUpDate);
	var _fromDateObj = new Date(_fromDate[2], _fromDate[1] - 1, _fromDate[0],
			00);
	var _toDateObj = new Date(_toDate[2], _toDate[1] - 1, _toDate[0], 00);
	var oneday = 24 * 60 * 60 * 1000;
	var calculateDate = _toDateObj - _fromDateObj;
	var dateDiff = Math.ceil(calculateDate / oneday);
	// alert(dateDiff);
	return dateDiff;
}

function clearItemProduct(){

	$("#product").empty();
	// $("#branchList").empty();
	// $("#branchList1").empty();
}


