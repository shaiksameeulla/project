//Global Variables
var FORM_ID="searchServiceRequestForm";
var CURRENT_DOM=null;
var ERROR_FLAG="ERROR";
$(document).ready( function () {
	var oTable = $('#listGrid').dataTable( {
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

function searchServiceRequestDtls(){
	var searchTypeDom=getDomElementById('searchType');
	var searchNumberDom=getDomElementById('searchNumber');
	if(searchTypeDom!=null && isNull(searchTypeDom.value)){
		alert("Please select Search Type");
		setFocusOnElmentByDom(searchTypeDom);
		return false;
	}
	if(searchNumberDom!=null && isNull(searchNumberDom.value)){
		alert("Please provide  "+getSelectedDropDownTextByDOM(searchTypeDom));
		setFocusOnElmentByDom(searchNumberDom);
		return false;
	}
	var url="./searchServiceRequest.do?submitName=searchServiceReqDetails";
	globalFormSubmit(url,FORM_ID);
	
}
function enterKeyForSearch(keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		searchServiceRequestDtls();
	}
}
function navigateToComplaintScreen(number){
	var url="./serviceRequestForService.do?submitName=searchServiceReqDetails&serviceRequestNo="+number;
	if(!isNull(url)){
		window.location=url;
	}
}