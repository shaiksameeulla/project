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
							'<input type="checkbox" id="ischecked' + rowCount
									+ '" name="chkBoxName" value=""/>',
							serialNo,
							'<input type="text" id="cnNumber'
									+ rowCount
									+ '" name="to.consgNumbers" size="15" maxlength="12" class="txtbox" onkeypress="enterKeyNavigationFocusGrid(event,this,\'O\');" onfocus = "validateHeader();"/><input type="hidden" name="to.consgIds" id="consgIds'
									+ rowCount
									+ '"/> <input type="hidden" name="to.position" id="position'
									+ rowCount + '" value = "' + serialNo
									+ '"/>',
							'<input type="text" id="receivedDate'
									+ rowCount
									+ '" name="to.receivedDates" size="15" class="txtbox" readonly="true"/>',
							'<input type="text" id="dlvDate'
									+ rowCount
									+ '" name="to.dlvDates" size="15" class="txtbox" readonly="true" tabindex="-1"  />',
							'<input type="text" name = "to.receiverNames" id="recvNameOrCompSeal'
									+ rowCount
									+ '" class="txtbox"  size="25" tabindex="-1" readonly="true"  />', ]);
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
		// document.getElementById("cnNumber" + nextRow).focus();
	}
}

/**
 * Retriving consignment details.
 * 
 * @param consObj
 */
function getConsignmentDtls(consObj) {

	var rowCount = getRowId(consObj, "cnNumber");
	var consgObj = consObj.value;
	if (!isNull(consgObj)) {
		var orgOffIdObj = document.getElementById("destOffice");
		var loggdOffcType = document.getElementById("orginOfficeType").value;
		var logdInOffic = document.getElementById("orginOfficeId").value;
		if (!isNull(orgOffIdObj.value)) {
			var isValid = true;
			if (rowCount > 1) {
				isValid = isDuplicateCongnt(rowCount);
			}
			if (isValid) {
				showProcessing();

				var orgOffId = "";
				orgOffId = orgOffIdObj.value;
				var consNo = consObj.value;
				url = './outgoingPODManifest.do?submitName=getDeliverdConsgDtls&consignment='
						+ consNo
						+ "&orgOffId="
						+ orgOffId
						+ "&loggdOffcType="
						+ loggdOffcType + "&logdInOffic=" + logdInOffic;
				;
				jQuery.ajax({
					url : url,
					success : function(req) {
						populateConsDetails(req, rowCount);
					}
				});
			}
		} else {
			alert("Please select the destination.");
			consObj.value = "";
			document.getElementById("destOffice").focus();
		}
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
	/*
	 * var isValid = true; if (rowCount > 1) { isValid =
	 * isDuplicateCongnt(rowCount); }
	 */
	// if (isValid) {
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
			if (rowCount < 90) {
				addNewRow(rowCount);
				var tempCount = parseInt(rowCount) + 1;
				if (!isNull(document.getElementById("cnNumber" + tempCount)))
					document.getElementById("cnNumber" + tempCount).focus();
			} else {
				alert("You have reached a maximum limit of 90 consignments");
			}
		}
	}
	// }
	jQuery.unblockUI();
}

/**
 * Save or update POD Manifest
 */
function saveOrUpdatePODManifest() {
	if (validateHeader() && validateGridDetails()) {
		isManifestExists();
	}
}

/**
 * Save or update outgoing POD Manifest Details
 */
function saveOrUpdateOutgoingPODManifest() {
	showProcessing();
	
	var loggdOffcId = document.getElementById("orginOfficeId").value;
	var url = './outgoingPODManifest.do?submitName=saveOrUpdateOutgoingPODMnfst&loggdOffcId='+loggdOffcId;
	jQuery.ajax({
		url : url,
		type: "POST",
		data : jQuery("#outgoingPODManifestForm").serialize(),
		success : function(req) {
			callSaveOrUpdatePODManifest(req);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			alert("jqXHR:===>"+jqXHR);
			alert("textStatus:===>"+textStatus);
			alert("errorThrown:===>"+errorThrown);
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
		alert('Manifest not saved successfully.:::' + ajaxResp);
	}
	jQuery.unblockUI();
	document.getElementById("manifestNo").value = "";
	var url = "./outgoingPODManifest.do?submitName=viewOutgoingPODManifest";
	document.outgoingPODManifestForm.action = url;
	document.outgoingPODManifestForm.submit();
}

/**
 * Check for wheather manifest number is exists in db or not
 */
function isManifestExists() {
	var manifestNo = document.getElementById("manifestNo").value;
	var manifestDirection = document.getElementById("manifestType").value;
	if (!isNull(manifestNo)) {
		url = './outgoingPODManifest.do?submitName=isManifestExists&manifestNo='
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

	} else {
		saveOrUpdateOutgoingPODManifest();
	}
}

/**
 * Clear all field of outgoing POD consgs
 */
function clearForm() {
	if (confirm("Do you want to Clear Outgoing POD Manifest details?")) {
		document.getElementById("manifestNo").value = "";
		var url = "./outgoingPODManifest.do?submitName=viewOutgoingPODManifest";
		document.outgoingPODManifestForm.action = url;
		document.outgoingPODManifestForm.submit();
	}
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
		url = './outgoingPODManifest.do?submitName=searchPODManifest&manifestNo='
				+ manifestNO
				+ "&manifestType="
				+ manifestType
				+ "&orginOfficeId=" + orginOfficeId;
		document.outgoingPODManifestForm.action = url;
		document.outgoingPODManifestForm.submit();
	}
}

function checkForLoggedInOffice() {

	var orginOfficeType = document.getElementById('orginOfficeType').value;
	if (orginOfficeType == "Branch Office") {
		var destCityId = "";

		destCityId = document.getElementById("destCity").value;

		url = './outgoingPODManifest.do?submitName=getHubsByCity&cityId='
				+ destCityId;
		ajaxCallWithoutForm(url, printAllOffices);
	} else if (orginOfficeType == "Hub Office") {
		// getAllOffices();
		// checkForOfficType();
	}

}

function checkForLoggedInOfficeForDestCity() {

	var orginOfficeType = document.getElementById('orginOfficeType').value;
	if (orginOfficeType == "Branch Office") {

		var loggdOffcId = "";
		loggdOffcId = document.getElementById("orginOfficeId").value;

		url = './outgoingPODManifest.do?submitName=getCitiesOfLoggdInBranchOffic&loggdOffcId='
				+ loggdOffcId;
		ajaxCallWithoutForm(url, printAllCities);
	} else {
		getAllCities();
	}

}

function checkForOfficType() {

	var officeTypeSelctd = document.getElementById('officeType').value;

	if (officeTypeSelctd == "BO") {
		// show all branches

		var destCityId = "";
		destCityId = document.getElementById("destCity").value;

		url = './outgoingPODManifest.do?submitName=getBranchesByCity&cityId='
				+ destCityId;
		ajaxCallWithoutForm(url, printAllOffices);

	} else if (officeTypeSelctd == "HO") {
		// show all hubs
		var destCityId = "";
		destCityId = document.getElementById("destCity").value;
		url = './outgoingPODManifest.do?submitName=getHubsByCity&cityId='
				+ destCityId;
		ajaxCallWithoutForm(url, printAllOffices);
	}
}

// ******Following code executes when clicks on print button *******
// *************************Start**************************************

function printOutgoingPOD() {
	var mno = document.getElementById("manifestNo").value;
	var mntp = document.getElementById("manifestType").value;
	var offid = document.getElementById("orginOfficeId").value;
	
	url = './outgoingPODManifest.do?submitName=printPODManifestDetails&manifestNo='
			+ mno + '&manifestType=' + mntp+'&originOfficeId='+offid;
	var w = window
			.open(url, 'myPopUp',
					'height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
}

// *************************End****************************************

