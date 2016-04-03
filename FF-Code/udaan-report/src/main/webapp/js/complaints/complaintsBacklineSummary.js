$(document).ready(function() {
	var oTable = $('#backlineSummaryTable').dataTable({
		"sScrollY" : "220",
		"sScrollX" : "100%",
		"sScrollXInner" : "170%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
});

function getComplaintDetails(){
	var statusObj=getDomElementById("compStatus");
	if(!isNull(statusObj.value)){
		var url="./backlikeSummary.do?submitName=getComplaintDetails";
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#backlikeSummaryForm").serialize(),
			success : function(req) {
				printCallBackComplaintDetails(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert("Server error : " + errorThrown);
			}
		});
	}else{
		alert("Please select status.");
		statusObj.focus();
	}
}
function printCallBackComplaintDetails(data){
	if (!isNull(data)) {
		var summaryTO = jsonJqueryParser(data);
		serviceReqTOs=summaryTO.serviceRequestTOs;
		for(var cnt=0;cnt<serviceReqTOs.length;cnt++){
			getDomElementById("chk").innerHTML="<input type='checkbox' name='chkBoxName'/>";
			getDomElementById("srNo").innerHTML=cnt+1;
			//getDomElementById("cnNo").innerHTML=serviceReqTOs[cnt].consignmentTO.consgNo;
			//getDomElementById("assgnTO").innerHTML=serviceReqTOs[cnt].userTO.userName;
			getDomElementById("status").innerHTML=serviceReqTOs[cnt].status;
			getDomElementById("createdDate").innerHTML=serviceReqTOs[cnt].createdDate;
			getDomElementById("lastDate").innerHTML=serviceReqTOs[cnt].updateDate;
			getDomElementById("compType").innerHTML=serviceReqTOs[cnt].serviceRequestType;
			//getDomElementById("dlvStatus").innerHTML=serviceReqTOs[cnt].;
			//getDomElementById("flag").innerHTML=serviceReqTOs[cnt].;
		} 
	}else{
		alert("null");
	}
}