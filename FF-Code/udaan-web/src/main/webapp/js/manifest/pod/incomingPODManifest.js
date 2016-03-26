var rowCount = 1;
var eachConsgWeightArr = new Array();

// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
$(document).ready(function() {
	var oTable = $('#podConsignmentDetails').dataTable({
		"sScrollY" : "225",
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

/**
 * To Load default Object
 */
function loadDefaultObjects() {
	addPODConsignmentRow();
}

/**
 * Add New Row for consignment
 */
function addPODConsignmentRow() {

	$('#podConsignmentDetails')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox" id="ischecked'
									+ rowCount
									+ '" name="chkBoxName" value=""/><input type="hidden" name="to.position" id="position'
									+ rowCount + '" value = "' + serialNo
									+ '"/>',
							serialNo,
							'<input type="text" id="cnNumber'
									+ rowCount
									+ '" name="to.consgNumbers" size="15" maxlength="12" class="txtbox" onkeypress="enterKeyNavigationFocusGrid(event,this,\'I\');" onfocus = "validateHeader();"/><input type="hidden" name="to.consgIds" id="consgIds'
									+ rowCount
									+ '" /><input type="hidden" name="to.receiveStatus" value = "" id="receiveStatus'
									+ rowCount + '"/>',
							'<input type="text" id="receivedDate'
									+ rowCount
									+ '" name="to.receivedDates" size="15" class="txtbox" readonly="true"/>',
							'<input type="text" id="dlvDate'
									+ rowCount
									+ '" name="to.dlvDates" size="15" class="txtbox" readonly="true" tabindex="-1"  />',
							'<input type="text" name = "to.receiverNames" id="recvNameOrCompSeal'
									+ rowCount
									+ '" class="txtbox"  size="35" tabindex="-1" readonly="true"  />', ]);
	rowCount++;
	serialNo++;
	updateSrlNo('podConsignmentDetails');
}

/**
 * @param rowNo
 */
function addNewRow(rowNo) {
	var nextRow = parseInt(rowNo) + 1;
	if (eval(document.getElementById("cnNumber" + nextRow)) == null) {
		addPODConsignmentRow();
		document.getElementById("cnNumber" + nextRow).focus();
	}
}

/**
 * Clear all field of outgoing POD consgs
 */
function clearForm() {
	if (confirm("Do you want to Clear Incoming POD Manifest details?")) {
		document.getElementById("manifestNo").value = "";
		var url = "./incomingPODManifest.do?submitName=viewIncomingPODManifest";
		document.incomingPODManifestForm.action = url;
		document.incomingPODManifestForm.submit();
	}
}

/**
 * @param consObj
 */
function getOutgoingPODConsignmentDtls(consObj) {
	var destOffice = document.getElementById("destOffice").value;
	var loggdOffice = document.getElementById("orginOfficeId").value;
	if (!isNull(destOffice)) {
		var rowCount = getRowId(consObj, "cnNumber");
		var consNo = consObj.value;
		if (!isNull(consNo)) {
			var isValid = true;
			if (rowCount > 1) {
				isValid = isDuplicateCongnt(rowCount);
			}
			if (isValid) {
				showProcessing();
				url = './incomingPODManifest.do?submitName=getOutgoingPODConsgDtls&consignment='
						+ consNo + "&destOffice=" + destOffice+"&loggdOffice="+loggdOffice;
				;
				jQuery.ajax({
					url : url,
					success : function(req) {
						populateConsDetails(req, rowCount);
					}
				});
			}
		}
	} else {
		alert("Please select the destination.");
		document.getElementById("destOffice").focus();
	}
}

/**
 * Populate consignment details
 * 
 * @param ajaxResp
 * @param rowCount
 * 
 */
function populateConsDetails(ajaxResp, rowCount) {
	if (!isNull(ajaxResp)) {
		var consgDtls = eval('(' + ajaxResp + ')');
		if (consgDtls.isValidCN == "N") {
			alert(consgDtls.errorMsg);
			document.getElementById("cnNumber" + rowCount).value = "";
			document.getElementById("cnNumber" + rowCount).focus();
		} else {
			document.getElementById("receivedDate" + rowCount).value = consgDtls.receivedDate;
			document.getElementById("dlvDate" + rowCount).value = consgDtls.dlvDate;
			document.getElementById("recvNameOrCompSeal" + rowCount).value = consgDtls.recvNameOrCompSeal;
			document.getElementById("consgIds" + rowCount).value = consgDtls.consgId;
			document.getElementById("receiveStatus" + rowCount).value = consgDtls.receivedStatus;
			if (rowCount < 90)
				addNewRow(rowCount);
			else
				alert("You have reached a maximum limit of 90 consignments");
		}
	}
	jQuery.unblockUI();
}

/**
 * Save or update POD Manifest
 */
function saveOrUpdatePODManifest() {
	if (validateHeader() && validateGridDetails()) {
		podConsignmentDtls();
	}
}

/**
 * Checked Missed and Entered consignment details
 */
function podConsignmentDtls() {
	var manifestNo = document.getElementById("manifestNo").value;
	var consigNos = "";
	for ( var i = 1; i < rowCount; i++) {
		if (!isNull(document.getElementById('cnNumber' + i))) {
			if (!isNull(document.getElementById("cnNumber" + i).value)) {
				consigNos = consigNos
						+ document.getElementById("cnNumber" + i).value + ",";
			}
		}
	}
	if (!isNull(manifestNo) && !isNull(consigNos)) {
		url = './incomingPODManifest.do?submitName=podConsignmentDtls&manifestNo='
				+ manifestNo + "&consigNos=" + consigNos;
		jQuery.ajax({
			url : url,
			success : function(req) {
				podConsignmentStatusDetails(req);
			}
		});
	}
}

/**
 * check missed consignments
 * 
 * @param ajaxResp
 */
function podConsignmentStatusDetails(ajaxResp) {
	// var podConsigDtlsTO = ajaxResp;
	if (!isNull(ajaxResp)) {
		if (isJsonResponce(ajaxResp)) {
			return;
		}
	}
	var podConsigDtlsTO = eval('(' + ajaxResp + ')');
	if (!isNull(podConsigDtlsTO.missedConsignments)) {
		alert(podConsigDtlsTO.errorMsg);
	}
	isManifestExists();
}
/**
 * Save or update outgoing POD Manifest Details
 */
function saveOrUpdateIncomingPODManifest() {
	showProcessing();
	var loggdOffcId = document.getElementById("orginOfficeId").value;
	var url = './incomingPODManifest.do?submitName=saveOrUpdateIncomingPODMnfst&loggdOffcId='+loggdOffcId;
	jQuery.ajax({
		url : url,
		data : jQuery("#incomingPODManifestForm").serialize(),
		success : function(req) {
			callSaveOrUpdatePODManifest(req);
		}
	});
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdatePODManifest(ajaxResp) {
	var response = ajaxResp;
	if (response != null && response == 'SUCCESS') {
		alert('Manifest saved successfully.');
	} else {
		alert('Manifest saved Unsuccessfully. :: ' + response);
	}
	jQuery.unblockUI();
	document.getElementById("manifestNo").value = "";
	var url = "./incomingPODManifest.do?submitName=viewIncomingPODManifest";
	document.incomingPODManifestForm.action = url;
	document.incomingPODManifestForm.submit();
}

/**
 * Check for wheather manifest number is exists in db or not
 */
function isManifestExists() {
	var manifestNo = document.getElementById("manifestNo").value;
	var manifestDirection = document.getElementById("manifestType").value;
	if (!isNull(manifestNo)) {
		url = './incomingPODManifest.do?submitName=isManifestExists&manifestNo='
				+ manifestNo + "&manifestDirection=" + manifestDirection;
		jQuery.ajax({
			url : url,
			success : function(req) {
				validateManifest(req);
			}
		});
	}
}

/**
 * validateManifest client side
 */
function validateManifest(data) {
	if (data != null && data == "Y") {
		alert("Manifest already exists.");
		document.getElementById("manifestNo").value = "";
		setTimeout(function() {
			document.getElementById("manifestNo").focus();
		}, 10);

	} else if (data != null && data != "Y" && data != "N") {
		alert(data);
		document.getElementById("manifestNo").value = "";
		setTimeout(function() {
			document.getElementById("manifestNo").focus();
		}, 10);

	} else {
		saveOrUpdateIncomingPODManifest();
	}
}

/**
 * Search POD Manifest Details
 */
function outPODManifestDestDtls() {
	var manifestNO = document.getElementById("manifestNo").value;
	if (!isNull(manifestNO)) {
		// showProcessing();
		var manifestType = document.getElementById('manifestType').value;
		var orginOfficeId = document.getElementById('orginOfficeId').value;
		url = './incomingPODManifest.do?submitName=outPODManifestDestDtls&manifestNo='
				+ manifestNO
				+ "&manifestType="
				+ manifestType
				+ "&orginOfficeId=" + orginOfficeId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				outgoingPODDestDtls(req);
			}
		});
	}
}

/**
 * @param ajaxResp
 */
function outgoingPODDestDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
			// hideProcessing();
		} else {
			var podManifestTORes = eval('(' + ajaxResp + ')');
			var podManifest = podManifestTORes;
			clearDropDownList("destRegionId");
			addOptionTODropDown("destRegionId", podManifest.destRegion,
					podManifest.regionId);
			document.getElementById("destRegionId").value = podManifest.regionId;
			document.getElementById("destRegionId").readOnly = true;

			clearDropDownList("destCity");
			addOptionTODropDown("destCity", podManifest.destCity,
					podManifest.destCityId);
			document.getElementById("destCity").value = podManifest.destCityId;
			document.getElementById("destCity").readOnly = true;

			clearDropDownList("destOffice");
			/*addOptionTODropDown("destOffice", podManifest.destOffice,
					podManifest.destOffId);
			document.getElementById("destOffice").value = podManifest.destOffId;
			document.getElementById("destOffice").readOnly = true;*/
			
			addOptionTODropDown("destOffice", podManifest.dispachOfficeTO.officeName,
			podManifest.dispachOfficeTO.officeId);
			document.getElementById("destOffice").value = podManifest.dispachOfficeTO.officeId;
			document.getElementById("destOffice").readOnly = true;
		}

	} else {
		var url = "./incomingPODManifest.do?submitName=viewIncomingPODManifest";
		document.incomingPODManifestForm.action = url;
		document.incomingPODManifestForm.submit();
	}
}

/**
 * @param selectId
 */
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	// addOptionTODropDown(selectId, selectOption, "");
}

/**
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * Search POD Manifest Details
 */
function searchPODManifestDtls() {
	var manifestNO = document.getElementById("manifestNo").value;
	if (isNull(manifestNO)) {
		alert("Please enter manifest number.");
		document.getElementById("manifestNo").focus();
	} else {
		showProcessing();
		var manifestType = document.getElementById('manifestType').value;
		var orginOfficeId = document.getElementById('orginOfficeId').value;
		url = './incomingPODManifest.do?submitName=searchPODManifest&manifestNo='
				+ manifestNO
				+ "&manifestType="
				+ manifestType
				+ "&orginOfficeId=" + orginOfficeId;
		document.incomingPODManifestForm.action = url;
		document.incomingPODManifestForm.submit();
	}
}

//******Following code executes when clicks on print button *******
//*************************Start**************************************

function printIncomingPOD() {
	var mno = document.getElementById("manifestNo").value;
	var mntp = document.getElementById("manifestType").value;
	var offid = document.getElementById("orginOfficeId").value;
	url = './incomingPODManifest.do?submitName=printPODManifestDetails&manifestNo='
			+ mno + '&manifestType=' + mntp+'&originOfficeId='+offid;
	var w = window
			.open(url, 'myPopUp',
					'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
}

//*************************End****************************************
