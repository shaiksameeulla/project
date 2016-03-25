
var rowCount = 1;
var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

/**
 * isJsonResponce
 * 
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp) {
	var responseText = null;
	try {
		responseText = jsonJqueryParser(ObjeResp);
	} catch (e) {

	}
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			return true;
		}
	}
	return false;
}

function deleteAllRow() {
	var table = getDomElementById(pincodeTable);
	var tableRowCount = table.rows.length;
	var oTable = $('#' + pincodeTable).dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}

}
function getPincodeMappingDetails(id) {
	var pincodeNumber = $("#" + id).val();
	var pncode = document.getElementById("pincodeSearch").value;
	if (isNull(pncode)) {
		alert("Please enter PincodeNumber.");
	}
	
	
	if (!isNull(pincodeNumber)) {
		var url = './searchByPincode.do?submitName=getPincodeDetails&pncode='
			+ pncode;
		
		jQuery
		.ajax({
			url : url,
			data : jQuery("#searchByPincodeForm").serialize(),
			success : function(req) {
				$("#pincodeTable").empty();
				if (isNull(req) || req == "[]") {
					alert("No serviceablity defined for this pincode.");
					$("#pincodeNumber").val("");
					$("#pincodeNumber").focus();
				}
		ajaxCallWithoutForm(url, getPincodeListDetails);
			}});
}
}

function getPincodeListDetails(data) {
	
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var pincodeSearchList = eval(data);
		$("#pincodeTable").empty();
		
		for ( var i = 0; i < pincodeSearchList.length; i++) {
			var add1=pincodeSearchList[i].address1;
			var add2=pincodeSearchList[i].address2;
			var res = add1 + ', ' + add2;
			var status = pincodeSearchList[i].servStatus == 'A' ? 'Active' : (pincodeSearchList[i].servStatus == 'I' ? 'In-active' : null);
			var cnt = "<tr><td><table style='text-align:justify;'>"
					+ "<tr><th align='right' width='15%'>Region:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].regionName
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>State:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].stateName
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Station:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].cityName
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Branch Name:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].officeName
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Branch Address:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ res
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Branch Email:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].email
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Branch Phone:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ pincodeSearchList[i].phone
					+ "</td></tr>"
					+ "<tr><th align='right' width='15%'>Status:</th><td width='60%' style='border: none;text-align:justify;'>"
					+ status
					+ "</td></tr>"
					+ "</table></td></tr>";
			$("#pincodeTable").append(cnt);
			
			}
		}
	}

function enablePrint() {
	buttonDisabled("saveBtn", "btnintform");
	jQuery("#" + "printBtn").removeClass("btnintformbigdis");
	jQuery("#" + "printBtn").addClass("btnintform");
	buttonEnabled("printBtn", "btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
}
