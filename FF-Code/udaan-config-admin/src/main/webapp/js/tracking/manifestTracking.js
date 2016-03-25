var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

function manifestInfo(tableId){
	var oTable = null;
	if(tableId == "example2"){
		oTable = $('#'+tableId).dataTable( {
			 "sScrollY": "293",
				"sScrollX": "80%",
				"sScrollXInner":"100%",
				"bScrollCollapse": false,
				"bSort": false,
				"bInfo": false,
				"bPaginate": false,
				 "bDestroy":true,
				 "bRetrieve":true,
				"sPaginationType": "full_numbers"
			} );
	}else{
		oTable = $('#'+tableId).dataTable( {
			"sScrollY": "225",
			"sScrollX": "100%",
			"sScrollXInner":"99%",
			"bScrollCollapse": false,
			"bSort": false,
			"bInfo": false,
			"bDestroy":true,
			"bRetrieve":true,
			"bPaginate": false,
			"sPaginationType": "full_numbers"
		} );
	}
	
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
}
function trackmanifest() {

	/* clearField(); */
	var isValidMnfst = false;
	var manifestObj = getDomElementById("manifestNumber");
	var manifest = manifestObj.value;
	var type = getDomElementById("typeName").value;

	if (isNull(type)) {
		clearFocusAlertMsg(getDomElementById("typeName"), 'Please select type');
		return;
	}

	if (isNull(manifest)) {
		clearFocusAlertMsg(manifestObj, 'Please enter ManifestNo');
		return;
	}

	if (type == 'OGM') {
		isValidMnfst = isValidPacketNo(manifestObj);
	} else if (type == 'BPL') {
		isValidMnfst = isValidBplNo(manifestObj);
	} else if (type == 'MBPL') {
		isValidMnfst = isValidMBplNo(manifestObj);
	}
	if (isValidMnfst) {
		url = "./manifestTrackingHeader.do?submitName=viewManifestTrackInformation&type="
				+ type + "&number=" + manifest;
		ajaxCalWithoutForm(url, populateManifest);
	}

}

/**
 * validates BPL number
 * 
 * @param manifestNoObj
 *            contains the BPL Number
 * @returns {true}
 */
function isValidBplNo(manifestNoObj) {
	// Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	
	if (isNull(manifestNoObj.value)) {
		return false;
	}
	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"BPL No. should contain 10 characters only!");
		return false;
	}
	var test2 = manifestNoObj.value.substring(4);	
	manifestNoObj.value = convertToUpperCase(manifestNoObj.value);
	
	if (!numpattern.test(test2)
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * @param manifestNoObj
 *            conatins manifest Number
 * @returns {true} if format is valid else returns false
 */
function isValidMBplNo(manifestNoObj) {
	// Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	if (isNull(manifestNoObj.value)) {
		return false;
	}
	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"MBPL No. should contain 10 characters only!");
		return false;
	}
	var test2 = manifestNoObj.value.substring(4);
	manifestNoObj.value = convertToUpperCase(manifestNoObj.value);
	
	if (!numpattern.test(test2)
			|| manifestNoObj.value.substring(3, 4) != "M") {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * validates the packet number
 * 
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidPacketNo(manifestNoObj) {
	// City Code(Alpha numeric)+7 digits :: BOY1234567 
	var numpattern = /^[0-9]+$/;

	manifestNoObj.value = $.trim(manifestNoObj.value);

	if (isNull(manifestNoObj.value)) {
		return false;
	}
	manifestNoObj.value = convertToUpperCase(manifestNoObj.value);
	var manifestNoLength = manifestNoObj.value.length;
	if (manifestNoLength == 10) {
		if (!numpattern.test(manifestNoObj.value.substring(3))) {
			clearFocusAlertMsg(manifestNoObj,
					"OGM/Open Manifest No. Format is not correct!");
			return false;
		}
	} else if (manifestNoLength == 12) {		
		if (manifestNoObj.value.substring(0, 2).toUpperCase() != "CM"
				|| !numpattern.test(manifestNoObj.value.substring(3, 12))) {
			clearFocusAlertMsg(manifestNoObj,
			"OGM/Open Manifest No. Format is not correct!");
			return false;
		}		
	} else {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}
	return true;
}

function populateManifest(data) {
	if (data != null) {
		var manifestObj = getDomElementById("manifestNumber");
		var typeObj = getDomElementById("typeName");
		
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			clearFocusAlertMsg(manifestObj, error);
			return;
		}
		manifestObj.disabled = true;;
		typeObj.disabled = true;	
		
		getDomElementById("maniNum").innerHTML = manifestObj.value;
		//Out manifest Tab
		setOutOrInManifestTabs(data.outManifestTO,'example');
		//In manifest Tab
		setOutOrInManifestTabs(data.inManifestTO,'example1');
		// Tab -3
		if (data.processMapTO != null) {
			for ( var i = 0; i < data.processMapTO.length; i++) {
				var processMap = data.processMapTO[i];
				var j = i + 1;
				addDetailRows(j, processMap);
			}
		}

		if (data.processMapTO.length == 0) {
			alert('No Load movement details available');
		}

		getDomElementById("trackBtn").style.display = 'none';
	}
}

function setHeaderValues4OutManifest(data){
	if(!isNull(data.manifestBaseTO)){
		getDomElementById("originoffice").value = data.manifestBaseTO.originOfficeTO.officeName;
		if(isNull(data.manifestBaseTO.destinationOfficeTO.officeName))
			getDomElementById("destination").value=data.manifestBaseTO.destCityTO.cityName;
		else
			getDomElementById("destination").value = data.manifestBaseTO.destinationOfficeTO.officeName;
		getDomElementById("type").value = data.manifestBaseTO.manifestType;
		getDomElementById("actualwt").value = data.manifestBaseTO.mnfstWeight;
		getDomElementById("manifestdate").value = data.manifestBaseTO.manifestDate;
	}
}
function setHeaderValues4InManifest(data){
	if(!isNull(data.manifestBaseTO)){
		getDomElementById("inOriginoffice").value = data.manifestBaseTO.originOfficeTO.officeName;
		if(isNull(data.manifestBaseTO.destinationOfficeTO.officeName))
			getDomElementById("inDestination").value=data.manifestBaseTO.destCityTO.cityName;
		else
			getDomElementById("inDestination").value = data.manifestBaseTO.destinationOfficeTO.officeName;
		getDomElementById("inActualwt").value = data.manifestBaseTO.mnfstWeight;
		getDomElementById("inManifestdate").value = data.manifestBaseTO.manifestDate;
	}	
	getDomElementById("OperatingOff").value = data.operatingOff;
	getDomElementById("receiveStatus").value=data.receiveStatus;	
}
function setOutOrInManifestTabs(data, tableId){
	if(tableId == 'example')
		setHeaderValues4OutManifest(data);
	else
		setHeaderValues4InManifest(data);
	manifestInfo(tableId);		
	if (data.manifestTOs != null) {
		for ( var i = 0; i < data.manifestTOs.length; i++) {
			var childManifest = data.manifestTOs[i];
			addChildManifestRows(childManifest,tableId);
		}
	}

	if (data.consignmentTO != null) {
		for ( var i = 0; i < data.consignmentTO.length; i++) {
			var childConsgManifest = data.consignmentTO[i];
			addChildConsgManifestRows(childConsgManifest,tableId);
		}
	}
	if (data.comailTO != null) {
		for ( var i = 0; i < data.comailTO.length; i++) {
			var comailsManifested = data.comailTO[i];
			addComailManifestedRows(comailsManifested,tableId);
		}
	}
}
function addChildManifestRows(childManifest,tableId) {
	var destination = "";
	if(isNull(childManifest.destinationOfficeTO.officeName))
		destination = childManifest.destCityTO.cityName;
	else
		destination = childManifest.destinationOfficeTO.officeName;
	if(childManifest.manifestType == "O"){
		$('#'+tableId).dataTable().fnAddData(
				[ childManifest.manifestNumber,"Out", childManifest.mnfstWeight, destination ]);
	}else if(childManifest.manifestType == "I"){
		$('#'+tableId).dataTable().fnAddData(
				[ childManifest.manifestNumber,"In", childManifest.mnfstWeight, destination ]);
	}
}

function addChildConsgManifestRows(childConsgManifest,tableId) {
	$('#'+tableId).dataTable().fnAddData(
			[ childConsgManifest.consgNo, "NA", childConsgManifest.strCnWeight,
					childConsgManifest.destCity.cityName ]);

}

function addComailManifestedRows(comailsManifested,tableId) {
	if (!isNull(comailsManifested.destOffice)) {
		$('#'+tableId).dataTable().fnAddData(
				[ comailsManifested.coMailNo,"NA", "NA",
						comailsManifested.destOffice.officeName ]);
	} else {
		$('#'+tableId).dataTable().fnAddData(
				[ comailsManifested.coMailNo, "NA","NA", "-" ]);
	}
}

function addDetailRows(rowCount, processMap) {
	$('#example2').dataTable().fnAddData(
			[ rowCount, 
			  '<div align="left">' + processMap.manifestType + '</div>', 
			  processMap.dateAndTime,
			  '<div align="left">' + processMap.consignmentPath + '</div>']);
}

function clearScreen(action) {
	var url = "./manifestTrackingHeader.do?submitName=viewManifestTracking";
	submitForm(url, action);
}

function submitForm(url, action) {
	if (confirm("Do you want to " + action + " details?")) {
		getDomElementById("manifestNumber").value = "";
		document.manifestTrackingForm.action = url;
		document.manifestTrackingForm.submit();
	}
}

function showOffice(officeId) {
	url = "./manifestTrackingHeader.do?submitName=showOffice&officeId="
			+ officeId;
	window.open(url,'_blank','top=120, left=590, height=195, width=375, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no, location=no, directories=no');
}