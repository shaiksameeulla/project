var ERROR_FLAG = "ERROR";

/**
 * To calculate RTH/RTO DOX rate.
 * 
 * @param rowId
 */
function calcRtoRate(rowId) {
	var cnNo = getDomElementById("consgNumber" + rowId).value;
	if (!isNull(cnNo)) {
		var cnId = getDomElementById("consignmentId" + rowId).value;
		var manifestType = getDomElementById("manifestType").value;
		if (manifestType == MANIFEST_TYPE_RTO) {
			var url = "./rthRtoManifestDox.do?submitName=calculateCnRtoRate"
					+ "&consigntNo=" + cnNo + "&consgId=" + cnId;
			showProcessing();
			ajaxCallWithoutForm(url, callBackCalculateCnRtoRate);
		}
	}
}

/**
 * To calculate RTH/RTO PPX rate.
 * 
 * @param rowId
 */
function calcRtoParcelRate(rowId) {
	var cnNo = getDomElementById("consgNumber" + rowId).value;
	if (!isNull(cnNo)) {
		var cnId = getDomElementById("consignmentId" + rowId).value;
		var manifestType = getDomElementById("manifestType").value;
		if (manifestType == MANIFEST_TYPE_RTO) {
			var url = "./rthRtoManifestParcel.do?submitName=calculateCnRtoRate"
					+ "&consigntNo=" + cnNo + "&consgId=" + cnId;
			showProcessing();
			ajaxCallWithoutForm(url, callBackCalculateCnRtoRate);
		}
	}
}
// Call back method for RTH/RTO DOX-PPX rate calculation.
function callBackCalculateCnRtoRate(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = ajaxResp;
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		}
		hideProcessing();
	}
	hideProcessing();
}