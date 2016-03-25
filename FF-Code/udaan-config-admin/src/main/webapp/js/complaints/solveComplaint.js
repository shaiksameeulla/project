var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
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
function getStatusList(){
	var url = './solveComplaint.do?submitName=getStatusList';
	jQuery.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		async: false,
		success : function(data) {
			getStatus(data);
		}
	});
}

function getStatus(data) {
	if (!isNull(data)) {
		if (isJsonResponce(data)) {
			return;
		}
	}
	var content = document.getElementById('serviceRequestStatusTO');
	content.innerHTML = "";
	$.each(data, function(index, value) {
		if (this.statusName == "Resolved") {
			var option;
			option = document.createElement("option");
			option.value = this.serviceRequestStatusId;
			option.appendChild(document.createTextNode(this.statusName));
			content.appendChild(option);
		}
	});
}

function getServiceRequestTransferList(){
	var url = './solveComplaint.do?submitName=getServiceRequestTransferList';
	jQuery.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		async: false,
		success : function(data) {
			getServiceRequestTransfer(data);
		}
	});
}

function getServiceRequestTransfer(data) {
	if (!isNull(data)) {
		if (isJsonResponce(data)) {
			return;
		}
	}
	var content = document.getElementById('serviceRequestTranferTO');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.serviceRequestTransfertoId;
		option.appendChild(document.createTextNode(this.transfertoName));
		content.appendChild(option);
	});
}

function getComplaintDetails(){
	var serviceRequestNo = complaintNumber;
	if(!isNull(serviceRequestNo)){
		var url = './solveComplaint.do?submitName=getComplaintDetails&serviceRequestNo='+serviceRequestNo;
		jQuery.ajax({
			url : url,
			async: false,
			success : function(req) {
				printComplaintDetails(req);
				}
			});	
		}
}

function printComplaintDetails(ajaxResp){
	var serviceRequestTO = eval('(' + ajaxResp + ')');
	if(!isNull(serviceRequestTO)){
		$("#bookingNo").val(serviceRequestTO.bookingNo);
		$("#updateDateStr").val(serviceRequestTO.updateDateStr);
		$("#serviceRequestId").val(serviceRequestTO.serviceRequestId);
		$("#consgDeliveryDate").val(serviceRequestTO.consgDeliveryDate);
		if(!isNull(serviceRequestTO.callerEmail)){
			$("#callerEmail").val(serviceRequestTO.callerEmail);
			$("#isEmailSend").attr('checked', true);
		}
		var status = serviceRequestTO.serviceRequestStatusTO.statusName;
		if(status == "Resolved" || status == "RESOLVED"){
			$("#serviceRequestTranferTO").val(serviceRequestTO.serviceRequestTranferTO.serviceRequestTransfertoId);
			$("#serviceRequestStatusTO").val(serviceRequestTO.serviceRequestStatusTO.serviceRequestStatusId);
			$("#solveComplntremark").val(serviceRequestTO.remark);
		}
	}
}


function solveComplaints(){
	getStatusList();
	getServiceRequestTransferList();
	getComplaintDetails();
}

function saveServiceRequestDetails() {
	if (checkMandatory()) {
		showProcessing();
		var url = './solveComplaint.do?submitName=saveServiceRequestDetails';
		jQuery.ajax({
			url : url,
			data : jQuery("#solveComplaintForm").serialize(),
			success : function(req) {
				callBack(req);
			}
		});
	}
}

function callBack(data){
	hideProcessing();
	if (data != null && data!="N") {
		var serviceRequestTO = eval('(' + data + ')');
		if(serviceRequestTO.transMag="SUCCESS"){
		alert('Data saved successfully.');
		complaintStatus = "Resolved";
		disableAllInputElements();
		}else {
		alert('Data saved Unsuccessfully.');
		}
	} 
}

function checkMandatory() {
	var errorsData = "";
	var updateDateStr = $("#updateDateStr").val();
	var bookingNo = $("#bookingNo").val();
	var serviceRequestTranferTO = $("#serviceRequestTranferTO").val();
	var remark = $("#solveComplntremark").val();
	var serviceRequestStatusTO = $("#serviceRequestStatusTO").val();
	if (isNull(updateDateStr)) {
		errorsData = errorsData + "Please provide Date." + "\n";
	}
	if (isNull(bookingNo)) {
		errorsData = errorsData + "Please provide Consignment No." + "\n";
	}
	if (isNull(serviceRequestTranferTO)) {
		errorsData = errorsData + "Please provide Transfer To ICC value."+ "\n";
	}
	if (isNull(trimString(remark))) {
		errorsData = errorsData + "Please provide Actual Reason for Complaint" + "\n";
	}
	if (isNull(serviceRequestStatusTO)) {
		errorsData = errorsData + "Please provide status of complaint." + "\n";
	}
	if (!isNull(errorsData)) {
		alert(errorsData);
		return false;
	} else {
		return true;
	}
}

function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
		$("#serviceRequestTranferTO").val("");
		$("#solveComplntremark").val("");
		//document.getElementById("remark").value = "";
		$("#serviceRequestStatusTO").val("");
	}
}

function setEmailId(obj){
	if(obj.checked){
		$("#callerEmail").prop('disabled',false);
		$("#callerEmail").val(callerEmail);
	} else {
		$("#callerEmail").val("");
		$("#callerEmail").prop('disabled',true);
	}
	
}