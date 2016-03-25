var VC_ERROR_FLAG = "ERROR";

$(document).ready(function() {
	var oTable = $('#collectionDetails').dataTable({
		"sScrollY" : "150",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
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

function addValidateCollectionRow() {
	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
							'<input type="text" id="transactionDate'
									+ rowCount
									+ '" name="transactionDate" class="txtbox width70" size="11" />',
							'<input type="text" id="custName'
									+ rowCount
									+ '" name="custName" class="txtbox width70" />',
							'<input type="text" id="custName'
									+ rowCount
									+ '" name="custName" class="txtbox width70" />',
							'<input type="text" id="collectionAgainst'
									+ rowCount
									+ '" name="collectionAgainst" class="txtbox width70" size="11" />',
							'<input type="text" name = "paymentMode" id="paymentMode'
									+ rowCount
									+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "amount" id="amount'
									+ rowCount
									+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "status" id="status'
									+ rowCount
									+ '"  class="txtbox width170" size="11" />', ]);
	rowCount++;
	serialNo++;
}

/**
 * To get Office code
 */
function getAllOffices() {
	document.getElementById('officeId').value = "";
	var cityId = "";
	cityId = document.getElementById("stationId").value;
	var url = './validateCollection.do?submitName=getAllOfficesByCity&cityId='
			+ cityId;
	ajaxCallWithoutForm(url, printAllOffices);
}

/**
 * @param data
 */
function printAllOffices(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = ajaxResp;
		var error = responseText[VC_ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		} else {
			data = eval(ajaxResp);
			var stationId = document.getElementById('stationId').value;
			var content = document.getElementById('officeId');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--SELECT--"));
			content.appendChild(defOption);
			if (!isNull(stationId)) {
				$.each(data, function(index, value) {
					var option;
					option = document.createElement("option");
					option.value = this.officeId;
					option
							.appendChild(document
									.createTextNode(this.officeName));
					content.appendChild(option);
				});
				var temp = document.getElementById('hiddenofficeId').value;
				if (!isNull(temp))
					document.getElementById('officeId').value = temp;
			}
		}
	}
	hideProcessing();
}

function searchCollectionDetlsForValidation() {
	var headerTransNo = document.getElementById('headerTransNo').value;
	var headerStatus = document.getElementById('headerStatus').value;
	var officeId = document.getElementById('officeId').value;
	var stationId = document.getElementById('stationId').value;
	var toDate = document.getElementById('toDate').value;
	var frmDate = document.getElementById('frmDate').value;

	cityId = document.getElementById("stationId").value;
	url = './validateCollection.do?submitName=searchCollectionDetlsForValidation&frmDate='
			+ frmDate
			+ '&toDate='
			+ toDate
			+ '&stationId='
			+ stationId
			+ '&officeId='
			+ officeId
			+ '&headerStatus='
			+ headerStatus
			+ '&headerTransNo=' + headerTransNo;
	document.validateCollectionForm.action = url;
	document.validateCollectionForm.submit();

}

// Popup window code
function collectionDtls(url) {
	popupWindow = window
			.open(
					url,
					'Bill Collection',
					'toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=0, fullscreen=yes, scrollbars=yes, directories=no');
}

/**
 * To set default values on load.
 */
function setDefaultValue() {
	var cityId = document.getElementById("stationId").value;
	if (!isNull(cityId)) {
		getAllOffices();
	}
	getDomElementById("frmDate").focus();
	validateHeaderStatus();
}

/**
 * To validate header status for check box enable or disable value.
 */
function validateHeaderStatus() {
	var status = getDomElementById("headerStatus").value;
	if (status == STATUS_VALIDATED) {
		var tableLength = getCollectionDtlsTableLength();
		getDomElementById("checkAll").checked = false;
		getDomElementById("checkAll").disabled = true;
		for ( var i = 1; i < tableLength; i++) {
			getDomElementById("chk" + i).checked = false;
			getDomElementById("chk" + i).disabled = true;
		}
	}
}

/**
 * To select all check box
 * 
 * @param obj
 */
function selectAllCheckBox(obj) {
	var tableLength = getCollectionDtlsTableLength();
	if (obj.checked == true) {
		for ( var i = 1; i < tableLength; i++) {
			getDomElementById("chk" + i).checked = true;
			getDomElementById("isChecked" + i).value = YES;
		}
	} else {
		for ( var i = 1; i < tableLength; i++) {
			getDomElementById("chk" + i).checked = false;
			getDomElementById("isChecked" + i).value = NO;
		}
	}
}

/**
 * To get table length
 * 
 * @returns tableLength
 */
function getCollectionDtlsTableLength() {
	return document.getElementById("collectionDetails").rows.length;
}

/**
 * To validate all selected Txn.(s)
 */
function validateTxns() {
	if (isAnyChecked()) {
		var url = "./validateCollection.do?submitName=validateTxns";
		ajaxCall(url, "validateCollectionForm", callBackValidateTxns);
	} else {
		alert("Please select atleast 1 transaction no. to validate.");
	}
}
// ajax call back method for validateTxns function
function callBackValidateTxns(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[VC_ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = jsonJqueryParser(ajaxResp);
			alert(data.transMsg);
			searchCollectionDetlsForValidation();
		}
	}
}

/**
 * To set isChecked element value according to checked and unchecked respective
 * check boxes.
 */
function setIsCheckedValue(obj) {
	var rowId = getRowId(obj, "chk");
	if (obj.checked == true) {
		getDomElementById("isChecked" + rowId).value = YES;
	} else {
		getDomElementById("isChecked" + rowId).value = NO;
	}
}

/**
 * To check whether any check box checked or not.
 * 
 * @returns {Boolean}
 */
function isAnyChecked() {
	var tableLength = getCollectionDtlsTableLength();
	var flag = false;
	for ( var i = 1; i < tableLength; i++) {
		if (getDomElementById("chk" + i).checked == true) {
			flag = true;
			break;
		}
	}
	return flag;
}