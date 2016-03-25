$(document).ready(function() {
	var oTable = $('#pincodeBranch').dataTable({
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
	loadDefaultObjects();
});

var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var rowCount = 1;

function isValidJson(json) {
	try {
		JSON.parse(json);
		return true;
	} catch (e) {
		return false;
	}
}

function isJsonResponce(ObjeResp) {
	var responseText = null;
	try {
		if (isValidJson(ObjeResp)) {
			responseText = jsonJqueryParser(ObjeResp);
		} else {
			var error = ObjeResp[ERROR_FLAG];
			if (!isNull(error)) {
				alert(error);
				return true;
			}
		}
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

function loadDefaultObjects() {
	addRow();
}

/*function getStationsList() {
	showProcessing();
	var region = $("#region").val();
	var url = './searchByBranch.do?submitName=getStationsList&region=' + region;
	ajaxCallWithoutForm(url, getStations);
}

function getStations(data) {
	$("#station").empty();
	$("#branch").empty();
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}

		var content = document.getElementById('station');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName + "-"
					+ this.cityCode));
			content.appendChild(option);
		});
	}
	deleteAllRow();
	jQuery.unblockUI();
	hideProcessing();
}*/

function getBranchesList() {
	showProcessing();
	$("#branch").empty();
	var station = $("#station").val();
	var url = './searchByBranch.do?submitName=getBranchesList&station='
			+ station;
	ajaxCallWithoutForm(url, getBranches);
}
function getBranches(data) {

	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branch');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName + "-"
					+ this.officeCode));
			content.appendChild(option);

		});
	}
	deleteAllRow();
	hideProcessing();
}

function getPincodeList() {
	showProcessing();
	var branch = $("#branch").val();
	if (!isNull(branch)) {
		var url = './searchByBranch.do?submitName=getPincodeList&branch='
				+ branch;
		jQuery.ajax({
			url : url,
			data : jQuery("#searchByBranchForm").serialize(),
			success : function(req) {
				//callBack(req);
				ajaxCallWithoutForm(url, callBack);
			}
		});
	}
}
function callBack(ajaxResp) {
	if (isNull(ajaxResp) || ajaxResp == "[]") {
		alert("Pincodes Do not exist");
	}
	if (!isNull(ajaxResp)) {
		if (isJsonResponce(ajaxResp)) {
			return;
		}
		var branchSearchList = eval(ajaxResp);

		deleteAllRow();
		rowCount = 1;
		if (!isNull(branchSearchList.length))
			for ( var i = 0; i < branchSearchList.length; i++) {
				addRow();
				document.getElementById("pincode" + (i + 1)).innerHTML = branchSearchList[i].pincodeTO.pincode;
			}
		enablePrint();
	} else {
		deleteAllRow();
	}
	hideProcessing();
}

function deleteAllRow() {
	var table = getDomElementById("pincodeBranch");
	var tableRowCount = table.rows.length;
	var oTable = $('#pincodeBranch').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}

}

function addRow() {

	$('#pincodeBranch').dataTable().fnAddData([

	'<span id="pincode' + rowCount + '" style="float:left;"></span>', ]);

	rowCount++;
}

function enablePrint() {
	buttonDisabled("saveBtn", "btnintform");
	jQuery("#" + "printBtn").removeClass("btnintformbigdis");
	jQuery("#" + "printBtn").addClass("btnintform");
	buttonEnabled("printBtn", "btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
}

