var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";

function uploadFile() {

	complaintNumber = document.getElementById("complaintNumber").value;
	complaintId = document.getElementById("paperWorkServiceRequestId").value;
	consignmentNumber = document.getElementById("consignmentNumber").value;
		var url = "./paperwork.do?submitName=saveOrUpdatePaperwork&complaintNumber="
				+ complaintNumber
				+ "&complaintId="
				+ complaintId
				+ "&consignmentNumber=" + consignmentNumber;
		document.paperworkForm.action = url;
		document.paperworkForm.submit();
}

function saveCallbackfun(resp) {
	alert("uploaded successfully");
}

function searchPaperwork() {

	var serviceRequestId = document.getElementById("paperWorkServiceRequestId").value;

	var url = "./paperwork.do?submitName=searchPaperwork&serviceRequestId="
			+ serviceRequestId;
	ajaxCall(url, "paperworkForm", showValues);

}

function showValues(responseText) {
	if (!isNull(responseText)) {
		var response = jsonJqueryParser(responseText);
		var error = response[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			response = jsonJqueryParser(responseText);
			if (!isNull(response)) {
				for ( var i = 0; i < response.length; i++) {
					document.getElementById("clientMeet").value = response[i].clientMeet;
					document.getElementById("feedback").value = response[i].feedback;
					document.getElementById("serviceRequestPaperworkDateStr").value = response[i].serviceRequestPaperworkDateStr;
					//document.getElementById("transferIcc").value = response[i].transferIcc;

					if (!isNull(response[i].complaintFileId))
						document.getElementById("complaintFileId").value = response[i].complaintFileId;

					if (!isNull(response[i].consignorCopyFileId))
						document.getElementById("consignorCopyFileId").value = response[i].consignorCopyFileId;

					if (!isNull(response[i].mailCopyFileId))
						document.getElementById("mailCopyFileId").value = response[i].mailCopyFileId;

					if (!isNull(response[i].undertakingFileId))
						document.getElementById("undertakingFileId").value = response[i].undertakingFileId;

					if (!isNull(response[i].invoiceFileId))
						document.getElementById("invoiceFileId").value = response[i].invoiceFileId;
				}
			}
		}
	}
}

function clearPaperwork() {
	searchPaperwork();
}

function checkMadartoryFields() {
	var feedback = document.getElementById("feedback");

	var complaintFile = document.getElementById("complaintFile");
	var consignorCopyFile = document.getElementById("consignorCopyFile");
	var mailCopyFile = document.getElementById("mailCopyFile");

	var complaintFileId = document.getElementById("complaintFileId").value;
	var consignorCopyFileId = document.getElementById("consignorCopyFileId").value;
	var mailCopyFileId = document.getElementById("mailCopyFileId").value;

	if (isNull(feedback.value)) {
		alert("Please enter the Feedback.");
		setTimeout(function() {
			feedback.focus();
		}, 10);
		return false;
	}

	if (isNull(complaintFileId) && isNull(complaintFile.value)) {
		alert("Please select the Complaint Letter.");
		setTimeout(function() {
			complaintFile.focus();
		}, 10);
		return false;
	}

	if (isNull(consignorCopyFileId) && isNull(consignorCopyFile.value)) {
		alert("Please select the Consignor Copy.");
		setTimeout(function() {
			consignorCopyFile.focus();
		}, 10);
		return false;
	}

	if (isNull(mailCopyFileId) && isNull(mailCopyFile.value)) {
		alert("Please select the Mail Copy.");
		setTimeout(function() {
			mailCopyFile.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * Redirect to prepare paperwork
 */
function preparePaperwork() {
	var url = "./paperwork.do?submitName=preparePaperwork"
			+ "&complaintNumber=" + complaintNumber 
			+ "&complaintId=" + complaintId 
			+ "&consignmentNumber=" + consignmentNumber
			+ "&complaintStatus=" + complaintStatus
			+ "&isDownload=Y";
	document.paperworkForm.action = url;
	document.paperworkForm.submit();
}
