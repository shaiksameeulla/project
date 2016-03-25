$(document).ready( function () {
	var oTable = $('#stockListView').dataTable( {
		"sScrollY": "220",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
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

var formId="listStockRequisitionForm";

/**navigateToApproveRequisition page
 * 
 * @param url
 */
function navigateToApproveReq(url){
	if(!isNull(url)){
		window.location=url;
	}
}

function checkDateDifferenceForTodate(domElm,nxtElm){
	if(isFutureDateForIssue(domElm,nxtElm)){
		return false;
	}
	checkDateDifference(domElm);
	
}
function checkDateDifference(domElm){
	var fromdate=getDomElementById('fromDateStr');
	var todate=domElm;
	var fromDateImg=getDomElementById("calImgFrom");
	if(!isNull(fromdate) && isNull(fromdate.value)){
		alert("Please enter From date");
		fromdate.value="";
		fromdate.focus();
		return false;
	}
	if(!isNull(todate) && isNull(todate.value)){
		alert("Please enter To date");
		todate.value="";
		todate.focus();
		return false;
	}
	
	 var serverDate= getDomElementById('todayDate');
	 if(isNull(serverDate) && isNull(serverDate.value)){
		 alert("Server date is empty, please set the server date");
		 return false;
	 }
	 if(!isNull(fromdate) && isFutureDateByServerDate(fromdate.value,serverDate.value)){
		 alert("From Date cannot be  future date");
		 fromdate.value="";
		 setFocusOnStDom(fromDateImg);
		 return false;
	 }
	
	 if(!isNull(todate) && isFutureDateByServerDate(todate.value,serverDate.value)){
		 alert("To Date cannot be future date");
		 todate.value="";
		 return false;
	 }
	
	
	var days=calculateDateDiff(fromdate.value, todate.value);
	//alert("days:"+days);
	if(days<0){
		alert("From date can not be greater than To-date");
		fromdate.value="";
		setFocusOnStDom(fromDateImg);
		return false;
	}
	
return true;
}

function clearScreen(){
	var url="./listViewRequisition.do?submitName=viewFormDetails";
	if(confirm("Do you want to clear the screen details?")){
		globalFormSubmit(url, formId);
	}

}
var isProcessing=false;
function search(){
	var todate=getDomElementById('toDateStr');
	var fromdate=getDomElementById('fromDateStr');
	if(!checkDateDifference(todate)){
		fromdate.value="";
		setFocusOnStDom(fromdate);
		return false;
	}
	var status= getDomElementById("status");
	if(isNull(status.value)){
		alert("Please select the Status");
		status.value="";
		setFocusOnStDom(status);
	}
	var url="./listViewRequisition.do?submitName=searchRequisitonDetails";
	if(isProcessing){
		alert(REQ_SUBMITTED_ALERT);
		return false;
	}
	isProcessing=true;
	globalFormSubmit(url, formId);
}