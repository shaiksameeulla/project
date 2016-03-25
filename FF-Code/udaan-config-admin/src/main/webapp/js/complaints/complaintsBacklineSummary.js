// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
$(document).ready(function() {
	var oTable = $('#backlineSummaryTable').dataTable({
		"sScrollY" : "250",
		"sScrollX" : "100%",
		"sScrollXInner" : "110%",
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

/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		responseText = jsonJqueryParser(ObjeResp);
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

function loadDefaultObjects() {
	//$('.dataTables_scroll').width("100%");
	//addRow();
	//getComplaintDetailsByUser();
}

function addRow() {

	$('#backlineSummaryTable')
			.dataTable()
			.fnAddData(
					[
					/* '<input type="checkbox" id="ischecked'+ rowCount+ '" name="chkBoxName" tabindex="-1" />',*/
					 serialNo,
					 '<label for="serviceRequestNo"><input type="text" name = "to.serviceRequestNo" id="serviceRequestNo'+ rowCount+ '" class="txtbox width115"  readonly="true"  /></label>', 
						
					 '<label for="bookingNo"><input type="text" name = "to.bookingNo" id="bookingNo'+ rowCount+ '" class="txtbox width115"  readonly="true"  /></label>', 
					 
					 '<label id="isCritical'+ rowCount+ '" readonly="true"  />', 
					 
					 '<input type="text" id="frontlineExc'+ rowCount+ '" class="txtbox width115"  readonly="true"  />', 
					 
					 '<input type="text" name = "to.assignedToEmpTO.employeeId" id="assignedToEmpTO'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.serviceRequestStatusTO.serviceRequestStatusId" id="serviceRequestStatusTO'+ rowCount+ '" class="txtbox width90"  readonly="true"  />',
					 
					 '<input type="text" name = "to.createdDateStr" id="createdDateStr'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.updateDateStr" id="updateDateStr'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.serviceRequestComplaintTypeTO.serviceRequestComplaintTypeId" id="serviceRequestComplaintTypeTO'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					]);
 
	rowCount++;
	serialNo++;
	//updateSrlNo('leadDetails');
}

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
			if(serviceReqTOs[cnt].assignedToEmpTO!=null){
				var name="";
				if(!isNull(serviceReqTOs[cnt].assignedToEmpTO.firstName )){
					name=serviceReqTOs[cnt].assignedToEmpTO.firstName ;
				}
				if(!isNull(serviceReqTOs[cnt].assignedToEmpTO.lastName )){
					name=name+serviceReqTOs[cnt].assignedToEmpTO.lastName ;
				}
				getDomElementById("assgnTO").innerHTML=name;
			}
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


function getComplaintDetailsByServiceRequestNo(){
	var serviceRequestNo = $("#serviceRequestNo" ).val();
	if(isNull(serviceRequestNo)){
		alert("Please enter serviceRequestNo.");
	}else{
	if(!isNull(serviceRequestNo)){
		var url = './backlikeSummary.do?submitName=getComplaintDetailsByServiceRequestNo&serviceRequestNo='+serviceRequestNo;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printComplaintDetails(req);
				}
			});	
		}
	}
}

function getComplaintDetailsByServiceRequestStatus(){
	var statusName = $("#serviceRequestStatusTO").val();
	if(isNull(statusName)){
		alert("Please select a status.");
	}else{
	if(!isNull(statusName)){
		var url = './backlikeSummary.do?submitName=getComplaintDetailsByServiceRequestStatus&serviceRequestStatusTO='+statusName;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printComplaintDetails(req);
				}
			});	
		}
	}
}

function getComplaintDetailsByUser(){
	var employeeId = $("#employeeId").val();
	if(!isNull(employeeId)){
		var url = './backlikeSummary.do?submitName=getComplaintDetailsByUser';
		jQuery.ajax({
			url : url,
			success : function(req) {
				printComplaintDetails(req);
				}
			});	
		}
}

function getComplaintDetailsFromSession(){
	var url = './backlikeSummary.do?submitName=getComplaintDetailsFromSession';
	jQuery.ajax({
		url : url,
		success : function(req) {
			printComplaintDetails(req);
			}
		});	
}

function printComplaintDetails(ajaxResp){
	if(!isNull(ajaxResp)){
		if(isJsonResponce(ajaxResp)){
			$("#serviceRequestNo" ).val("");
			$("#serviceRequestNo" ).focus();
			return ;
		}
		var serviceRequestTOList = eval('(' + ajaxResp + ')');
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		if (!isNull(serviceRequestTOList.length)){
				addRow();
				for(var i =0 ;i<serviceRequestTOList.length;i++){
						$("#serviceRequestNo" + (i+1)).val(serviceRequestTOList[i].serviceRequestNo);
						$("#bookingNo" + (i+1)).val(serviceRequestTOList[i].bookingNo);
						var name="";
						if(serviceRequestTOList[i].assignedToEmpTO!=null){
							name=serviceRequestTOList[i].assignedToEmpTO.empCode;
							if(!isNull(serviceRequestTOList[i].assignedToEmpTO.firstName )){
								name=name+"-"+serviceRequestTOList[i].assignedToEmpTO.firstName ;
							}
							if(!isNull(serviceRequestTOList[i].assignedToEmpTO.lastName )){
								name=name+serviceRequestTOList[i].assignedToEmpTO.lastName ;
							}
						}
						$("#assignedToEmpTO" + (i+1)).val(name);
						$("#serviceRequestStatusTO" + (i+1)).val(serviceRequestTOList[i].serviceRequestStatusTO.statusName);
						$("#createdDateStr" + (i+1)).val(serviceRequestTOList[i].createdDateStr);
						$("#updateDateStr" + (i+1)).val(serviceRequestTOList[i].updateDateStr);
						var complntType="";
						var isCritical = "N";
						if(serviceRequestTOList[i].serviceRequestComplaintTypeTO!=null){
							if(!isNull(serviceRequestTOList[i].serviceRequestComplaintTypeTO.complaintTypeDescription)){
								complntType=serviceRequestTOList[i].serviceRequestComplaintTypeTO.complaintTypeDescription;
								isCritical = serviceRequestTOList[i].serviceRequestComplaintTypeTO.isCriticalComplaint;
							} 
						}
						
						if (isCritical == "Y"){
							$("#isCritical" + (i+1)).addClass('criticalYes');
						}
						if(((i+1)%2) !=0){
							$("#isCritical" + (i+1)).addClass('criticalYesBackground');
						}
						$("#serviceRequestComplaintTypeTO" + (i+1)).val(complntType);
						$("#isCritical" + (i+1)).text(isCritical);
						
						$("#frontlineExc" + (i+1)).val(serviceRequestTOList[i].frontlineExecName);
						
						/*$("#consignmentType" + (i+1)).val(serviceRequestTOList[i].consignmentType);*/
						$("#serviceRequestId" + (i+1)).val(serviceRequestTOList[i].serviceRequestId);
						$("#bookingNoType" + (i+1)).val(serviceRequestTOList[i].bookingNoType);
						
						var consgNo = serviceRequestTOList[i].bookingNo;
						var type = serviceRequestTOList[i].bookingNoType;
						var newService ='<a href="JavaScript:serviceDtls(\'./paperwork.do?submitName=preparePaperwork&complaintNumber=' + serviceRequestTOList[i].serviceRequestNo + '&complaintId=' + serviceRequestTOList[i].serviceRequestId + '&consignmentNumber=' + consgNo + '&bookingNoType=' + type +'&complaintStatus=' + serviceRequestTOList[i].serviceRequestStatusTO.statusName+'\');">' + serviceRequestTOList[i].serviceRequestNo + '</a>';
						var newConsg ='<a href="JavaScript:populateConsignmentDetails(\''+consgNo+'\',\''+type+'\');">' + serviceRequestTOList[i].bookingNo + '</a>';
						var temp = 1+i;
						var oTable = $('#backlineSummaryTable').dataTable();
						if(serviceRequestTOList[i].isLinkEnabled == "Y"){
						oTable.fnUpdate( newService, i, 1);
						}else{
							var newLabel = '<span>'+ serviceRequestTOList[i].serviceRequestNo +'</span>';
							oTable.fnUpdate( newLabel, i, 1);
						}
						oTable.fnUpdate( newConsg, i, 2);
						if(temp != serviceRequestTOList.length)
							addRow();	
			}
		}
	  
	}else{
		deleteAllRow();
	}
}



function deleteAllRow() {
	var table = getDomElementById("backlineSummaryTable");
	var tableRowCount = table.rows.length;
	var oTable = $('#backlineSummaryTable').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}
}

function cancelDetails(){
	if(confirm("Do you want to clear the details?")) {
	window.location.reload();
	}
}

function serviceDtls(url) {
	showProcessing();
	var w = window.open(url,'','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width='+screen.width+',height='+screen.height+',left = 100,top = 110');
	w.moveTo(0, 0);
	var watchClose = setInterval(function() {
		try {
			if (w.closed) {
				jQuery.unblockUI();
				clearTimeout(watchClose);
			}
		} catch (e) {
		}
	}, 200);
}


function getComplaintDetails(){
	var serviceRequestNo = $("#serviceRequestNo" ).val();
	var statusName = $("#serviceRequestStatusTO").val();
	if(!isNull(serviceRequestNo)){
		getComplaintDetailsByServiceRequestNo();
	}else if(!isNull(statusName)){
		getComplaintDetailsByServiceRequestStatus();
	}else{
		alert("Please select status or provide complaint number.");
	}
}

function populateConsignmentDetails(consignmentNo,type) {
    var url = "./consignmentTrackingHeader.do?submitName=viewConsignmentTracking&screen=" + "trackingPopup"+"&consignmentNo=" + consignmentNo +"&type=" + type;
    showProcessing();
    var w = window
    		.open(
                  url,
                  '',
                  'toolbar=0,scrollbars=auto,location=0,statusbar=0,menubar=0,resizable=0,width=1000,height=420,left = 412,top = 184');
    w.moveTo(0, 0);
    var watchClose = setInterval(function() {
		try {
			if (w.closed) {
				jQuery.unblockUI();
				clearTimeout(watchClose);
			}
		} catch (e) {
		}
	}, 200);
}
