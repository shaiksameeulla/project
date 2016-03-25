var ERROR_FLAG = "ERROR";

function showProcessing(){
	//jQuery.blockUI( {message : '<img src="../../images/loading_animation.gif"/>'}); 
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}


function getVendorMappingDetails() {
	var vendorCode = getVendorCode();
	if (!isNull(vendorCode)) {
		var url = './vendorMapping.do?submitName=getVendorMappingInfo&vendorCode='
				+ vendorCode;
		ajaxCallWithoutForm(url, getVendorDetailsForScreen);
	}
}

function getVendorCode() {
	var selectedName = jQuery("#vendorName").val();
	var vendorCode = "";
	if (!isNull(selectedName)) {
		vendorDetails = selectedName.split("~");
		// vendorName = vendorDetails[0];
		vendorCode = vendorDetails[1];
	}
	return vendorCode;
}

function getVendorDetailsForScreen(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		document.getElementById("vendorCode").value = data.vendorCode;
		document.getElementById("address").value = data.address;
		document.getElementById("service").value = data.vendorType;

		var content = document.getElementById('regionList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data.vendorRegionMappingTO, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.regionTO.regionId;
			option.appendChild(document
					.createTextNode(this.regionTO.regionDisplayName));
			content.appendChild(option);
		});

	}
}

function vendorMappingStartUp() {
	// clearVendorDetails();
	
	document.getElementById("officeList").disabled = true;
	document.getElementById("stationList").disabled = true;
	document.getElementById("officeListSelect").disabled = true;
	// document.getElementById("saveBtn").disabled = true;

	/*
	 * buttonDisabled("saveBtn","btnintform"); jQuery("#" +
	 * "saveBtn").addClass("btnintformbigdis");
	 */
	//showprocessing();
}

/*
 * function disabledVendorName(){
 * document.getElementById("vendorName").readOnly=true; }
 */

function clearVendorDetails() {
	/*
	 * var url = './vendorMapping.do?submitName=preparePage';
	 * document.vendorMappingForm.action = url;
	 * document.vendorMappingForm.submit();
	 */
	document.getElementById("vendorName").value = "";
	document.getElementById("address").value = "";
	document.getElementById("vendorCode").value = "";
	document.getElementById("service").value = "";
	$("#officeList").empty();
	$("#officeListSelect").empty();
	$("#regionList").empty();
	$("#stationList").empty();

}

function enabledFields() {
	document.getElementById("officeList").disabled = false;
	document.getElementById("stationList").disabled = false;
	document.getElementById("officeListSelect").disabled = false;
}

function clearFields() {
	/*
	 * stationList="stationList"; clearDropDownList(stationList);
	 */
	$("#officeList").empty();
	$("#officeListSelect").empty();
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;

}

function getStationsList() {

	/*
	 * document.getElementById('stationList').value=""; var region =
	 * document.getElementById('regionList').value;
	 */
	$("#stationList").empty();// need to checked ok
	var region = $("#regionList").val();
	if (!isNull(region)) {
		var url = './vendorMapping.do?submitName=getStations&region=' + region;
		ajaxCallWithoutForm(url, getStationList);
	} else {
		alert('Please Select Region');
	}

}

function getStationList(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}

		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	}
}

function getBranchesList() {

	/* document.getElementById('branchList').value=""; */
	/* var station = document.getElementById('stationList').value; */
	var station = $("#stationList").val();
	var reg = $("#regionList").val();
	if (!isNull(station)) {
		var url = './vendorMapping.do?submitName=getBranches&station='+ station+'&region='+ reg;
		ajaxCallWithoutForm(url, getBranchList);
	} else {
		alert('Please Select Station');
	}
}

function getBranchList(data) {
	branchList = "officeList";
	var responseText = data;
	var error = responseText[ERROR_FLAG];
	// var success = responseText[SUCCESS_FLAG];
	if (responseText != null && error != null) {
		alert(error);
		return;
	}
	officeList = data[0];
	if (officeList != null) {
		officeDropDown(officeList);
	} else {
		/* clearDropDownList(branchList); */
		$("#officeList").empty();
	}
	selofficeList = data[1];
	if (selofficeList != null) {
		selofficeDropDown(selofficeList);
	} else {
		/* clearDropDownList(branchList); */
		$("#officeListSelect").empty();
	}
}

function selofficeDropDown(officeTOList) {
	office = "officeListSelect";
	/* clearDropDownList(office); */
	$("#officeListSelect").empty();
	if (!isNull(officeTOList)) {
		// addOptionTODropDown(office, ALL, officeCode);
		for ( var i = 0; i < officeTOList.length; i++) {
			addOptionTODropDown(office, officeTOList[i].officeName,
					officeTOList[i].officeId);
		}
	}
}

function officeDropDown(officeTOList) {
	office = "officeList";
	/* clearDropDownList(office); */
	$("#officeList").empty();
	if (!isNull(officeTOList)) {
		// addOptionTODropDown(office, ALL, officeCode);
		for ( var i = 0; i < officeTOList.length; i++) {
			addOptionTODropDown(office, officeTOList[i].officeName,
					officeTOList[i].officeId);
		}
	}
}

function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

function addOffices() {
	officeListSelected = "officeListSelect";
	var officeList = getDomElementById("officeList");
	var officeSelected = getDomElementById("officeListSelect");
	var count = 0;
	if (officeList.length != 0) {
		/*
		 * for (var i=0; i<officeList.length; i++) { if
		 * (officeList[i].selected) { if(officeSelected.length==null ||
		 * officeSelected.length==0){ addOffice(officeListSelected,
		 * officeList[i].text,officeList[i].value); } else{ for(var j=0;j<officeSelected.length;
		 * j++){ if(officeList[i].text==officeSelected[j].text){ alert('Customer
		 * already in list'); } else{ count=count+1; } }
		 * if(count==officeSelected.length){ count=0;
		 * addOffice(officeListSelected,
		 * officeList[i].text,officeList[i].value); } } } }
		 */

		/*
		 * for (var i=0; i<officeList.length; i++) { if
		 * (officeList[i].selected) { addOffice(officeListSelected,
		 * officeList[i].text,officeList[i].value); officeList.remove(i); } }
		 */

		for ( var i = officeList.length - 1; i >= 0; i--) {
			if (officeList.options[i].selected) {
				addOffice(officeListSelected, officeList[i].text,
						officeList[i].value);
				officeList.remove(i);
			}
		}

	} else {
		alert('No data available');
	}
}

function addOffice(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

function removeOffices() {
	officeList = "officeList";
	customList = "officeListSelect";
	var officeSelected = getDomElementById("officeListSelect");
	/* var custList=$("#custList" ).val(); */
	if (officeSelected.length != 0) {
		/*
		 * for (var i=0; i<custList.length; i++) { if (custList[i].selected) {
		 * custList.remove(i); } }
		 */
		for ( var i = officeSelected.length - 1; i >= 0; i--) {
			if (officeSelected.options[i].selected) {
				addOffice(officeList, officeSelected[i].text,
						officeSelected[i].value);
				officeSelected.remove(i);
			}
		}
	} else {
		alert('No data available');
	}
}

function editVendorMapping() {
	document.getElementById("vendorName").readOnly = false;
	document.getElementById("vendorCode").readOnly = false;
	document.getElementById("address").readOnly = false;
	/*
	 * buttonEnabled("saveBtn","btnintformbigdis"); jQuery("#" +
	 * "saveBtn").addClass("btnintform");
	 */
	// buttonEnabled("saveBtn","btnintformbigdis");
	jQuery("#" + "saveBtn").attr("disabled", false);
	jQuery("#" + "saveBtn").removeClass("btnintformbigdis");
	jQuery("#" + "saveBtn").addClass("btnintform");
}

function makeFiledReadOnly() {

}

function saveOrUpdateVendorMapping() {
	var officeLists = "";
	var abc = $("#address").val();
	// var abc1=abc.replace(/(\r\n|\n|\r^\s+|\s+$)/gm,' ');(/(^\s+|\s+$)/g,'');
	// var abc1=abc.replace(/(^\s+|\s+$)/g,'');
	/*
	 * alert(abc1); var vendorOfficeMapTO = eval('(' + abc + ')'); var
	 * addr=eval( '(' + abc + ')' ); alert(vendorOfficeMapTO);
	 */
	// var officeListSelect=$("#officeListSelect" );
	var offList = getDomElementById("officeListSelect");
	var regionId = $("#regionList").val();
	var count = 0;

	if (offList.length != 0) {
		/*
		 * for (var i=0; i<custList.length; i++) { if (custList[i].selected) {
		 * custId+=custList[i].value+","; count=count+1; } }
		 */

		$("#officeListSelect option").each(function() {
			if (isNull(officeLists)) {
				officeLists += $(this).val();
				count = count + 1;
			} else {
				officeLists += "," + $(this).val();
			}

		});

		var url = './vendorMapping.do?submitName=saveOrUpdate&officeLists='
				+ officeLists + "&regionId=" + regionId + "&address=" + abc;
		ajaxCall(url, "vendorMappingForm", saveCallback);
	} else {

		alert('No Office Available');
	}

	/*
	 * if (officeListSelect==null){ alert('Office Details is Empty'); } else
	 * if(officeListSelect.length!=0 ){ $("#officeListSelect
	 * option:selected").each(function () { if(isNull(officeLists)){ officeLists +=
	 * $(this).val(); count=count+1; } else { officeLists+= "," + $(this).val(); }
	 * 
	 * }); if(count==0){ alert('Select Atleast one office'); } else{ var url =
	 * './vendorMapping.do?submitName=saveOrUpdate&officeLists='+officeLists +
	 * "&regionId=" + regionId + "&address=" + abc; ajaxCall(url,
	 * "vendorMappingForm", saveCallback); } }
	 */
	/*
	 * var url =
	 * './vendorMapping.do?submitName=saveOrUpdate&officeLists='+officeLists +
	 * "&regionId=" + regionId; ajaxCall(url, "vendorMappingForm",
	 * saveCallback);
	 */
	/*
	 * document.vendorMappingForm.action = url;
	 * document.vendorMappingForm.submit();
	 */
	// ajaxCallWithoutForm(url, saveOrUpdateDeails);
}

function saveCallback(data) {
	// showProcessing();
	var vendorOfficeMapTO = eval('(' + data + ')');

	if (!isNull(vendorOfficeMapTO.errorMessage)) {
		alert(vendorOfficeMapTO.errorMessage);
		clearVendorDetails();

	} else if (!isNull(vendorOfficeMapTO.successMessage)) {
		alert(vendorOfficeMapTO.successMessage);
		clearVendorDetails();
	}

}