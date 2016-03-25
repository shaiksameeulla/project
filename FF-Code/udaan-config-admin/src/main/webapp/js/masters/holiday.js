var clearMessage = "Do you want to clear the page?";

var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var STATUS;
var isEXIST = false;

function getStateList() {
	var regionId = document.getElementById("regionList").value;
	var stateList = document.getElementById("stateList");
	var cityList = document.getElementById("cityList");
	var branchlist = document.getElementById("branchlist");
	if (!isNull(regionId)) {
		showProcessing();
		url = './holiday.do?submitName=getStatesByRegion&regionId=' + regionId;
		ajaxCallWithoutForm(url, getStatesList);
		stateList.disabled = false;
		cityList.disabled = false;
		branchlist.disabled = false;
	} else {
		stateList.disabled = true;
		cityList.disabled = true;
		branchlist.disabled = true;
		$('#stateList').prop('selectedIndex', 0);
		$('#cityList').prop('selectedIndex', 0);
		$('#branchlist').prop('selectedIndex', 0);
	}
	clearDropDownList("cityList");
	clearDropDownList("branchlist");

}

function getStatesList(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (error != null) {
			showDropDownBySelected("regionList", "");
			alert(error);
			document.getElementById('regionList').focus();

		} else {
			var content = document.getElementById('stateList');
			content.innerHTML = "";
			addAllInDropoDown(content);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.stateId;
				option.appendChild(document.createTextNode(this.stateName));
				content.appendChild(option);
			});
		}

	} else {
		alert('No State is available');
	}
}
function getCityList() {
	var regionId = document.getElementById("regionList").value;
	var stateId = document.getElementById("stateList").value;
	var cityList = document.getElementById("cityList");
	var branchlist = document.getElementById("branchlist");
	if (!isNull(stateId)) {
		showProcessing();
		url = './holiday.do?submitName=getCitysByState&stateId=' + stateId
				+ '&regionId=' + regionId;
		ajaxCallWithoutForm(url, getCitysList);
		cityList.disabled = false;
		branchlist.disabled = false;
	} else {
		cityList.disabled = true;
		branchlist.disabled = true;
		$('#cityList').prop('selectedIndex', 0);
		$('#branchlist').prop('selectedIndex', 0);
	}
	clearDropDownList("branchlist");

}

function getCitysList(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (error != null) {
			showDropDownBySelected("stateList", "");
			alert(error);
			document.getElementById('stateList').focus();
		} else {
			var content = document.getElementById('cityList');
			content.innerHTML = "";
			addAllInDropoDown(content);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});
		}

	} else {
		alert('No City is available');
		// clearDropDownList("cityList");
	}

}

function getBranchesByCity() {
	var cityId = document.getElementById("cityList").value;
	var branchlist = document.getElementById("branchlist");

	if (!isNull(cityId)) {
		showProcessing();
		url = './holiday.do?submitName=getBranchesByCity&cityId=' + cityId;
		ajaxCallWithoutForm(url, populateBranches);
		branchlist.disabled = false;
	} else {
		branchlist.disabled = true;
		$('#branchlist').prop('selectedIndex', 0);
	}
}

function populateBranches(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (error != null) {
			clearDropDownList("branchlist");
			showDropDownBySelected("cityList", "");
			alert(error);
			document.getElementById('cityList').focus();
		} else {
			var content = document.getElementById('branchlist');
			content.innerHTML = "";
			addAllInDropoDown(content);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeCode
						+ "-" + this.officeName));
				content.appendChild(option);
			});
		}

	} else {
		alert('No Branch is available');
		// clearDropDownList("branchlist");
	}

}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	var content = document.getElementById(selectId);
	content.innerHTML = "";
	addSelectInDropoDown(content);
}

function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}

function clearScreen() {
	if (confirm(clearMessage)) {
		document.holidayForm.action = "./holiday.do?submitName=preparePage";
		document.holidayForm.submit();
	}
}

function saveHoliday() {
	var date = $("#date");
	if (date.val() == "" || date.val() == '-1') {
		alert('Select Mandatory Date');
		date.focus();
		return false;
	}
	var regionList = $("#regionList");
	if ((regionList.val() == "" || regionList.val() == '-1')
			&& regionList.val() != 'null') {
		alert('Select Mandatory Region');
		regionList.focus();
		return false;
	}
	if (regionList.val() != 'null') {
		var stateList = $("#stateList");
		if (stateList.val() == "" || stateList.val() == '-1') {
			alert('Select Mandatory State');
			stateList.focus();
			return false;
		}
		if (stateList.val() != 'null') {
			var cityList = $("#cityList");
			if (cityList.val() == "" || cityList.val() == '-1') {
				alert('Select Mandatory City');
				cityList.focus();
				return false;
			}
			if (cityList.val() != 'null') {
				var branchlist = $("#branchlist");
				if (branchlist.val() == "" || branchlist.val() == '-1') {
					alert('Select Mandatory branch');
					branchlist.focus();
					return false;
				}
			}
		}
	}
	var reason = $("#reason");
	if (reason.val() == "" || reason.val() == '-1') {
		alert('Select Mandatory reason');
		reason.focus();
		return false;
	}
	if (reason.val() == "0") {
		var others = $("#others");
		if (others.val() == "") {
			others.focus();
			alert('Please enter others');
			return false;
		}
	}

	document.holidayForm.action = "./holiday.do?submitName=saveHoliday&senderPage=masterPage";
	document.holidayForm.submit();
}

function enabledOthers(obj) {
	var other = getDomElementById('others');
	if (obj.value == 0) {
		other.disabled = false;
	} else {
		other.disabled = true;
		other.value = '';
	}
}

function doDataValidation(obj) {
	if (obj.value != '') {
		var targetDate = new Date();
		var dd = targetDate.getDate();
		var mm = targetDate.getMonth() + 1; // 0 is January, so we must add 1
		var yyyy = targetDate.getFullYear() + 2;
		var dateString = dd + "/" + mm + "/" + yyyy;
		if (compareDates(obj.value, dateString) == 1) {
			alert("Date connot be greater than 2 years");
			obj.value = '';
			obj.focus();
			return;
		}

		var targetDate = new Date();
		targetDate.setDate(targetDate.getDate() - 60);
		var dd = targetDate.getDate();
		var mm = targetDate.getMonth() + 1; // 0 is January, so we must add 1
		var yyyy = targetDate.getFullYear();
		var dateString = dd + "/" + mm + "/" + yyyy;
		if (compareDates(obj.value, dateString) == -1) {
			alert("Date cannot be more than 60 days before current date");
			obj.value = '';
			obj.focus();
			return;
		}
	}
}

function stopRKey(evt) {
	var evt = (evt) ? evt : ((event) ? event : null);
	var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement
			: null);
	if ((evt.keyCode == 13) && (node.type == "text")) {
		return false;
	}
}

function addSelectInDropoDown(content) {
	var select = document.createElement("option");
	select.value = "";
	select.appendChild(document.createTextNode("Select"));
	content.appendChild(select);
}

function addAllInDropoDown(content) {
	var select = document.createElement("option");
	select.value = "";
	select.appendChild(document.createTextNode("Select"));
	content.appendChild(select);
	var all = document.createElement("option");
	all.value = "null";
	all.appendChild(document.createTextNode("ALL"));
	content.appendChild(all);
}

function searchHoliday() {
	formValidationHM();

	/*
	 * if (formValidationHM()) { }
	 */

	var url = './holiday.do?submitName=searchHoliday';
	ajaxJquery(url, "holidayForm", searchHolidayResponse);
	// setSelectedValue(n,n);
}

var countNo = 1;
var rowCount = 0;

function searchHolidayResponse(data) {
	if (data != null && data != "") {
		isEXIST = true;
		var holidayTOList = eval(data);

		$("#ViewYearly").prop("disabled", false);
		$("#ViewYearly").attr('class', 'btnintform');

		$("#Search").prop("disabled", true);
		$("#Search").attr('class', 'btnintformbigdis');

		for ( var i = 0; i < holidayTOList.length; i++) {
			addRow();
			$("#date" + i).text(holidayTOList[i].date);

			if (holidayTOList[i].regionId == 0) {
				$("#regionName" + i).text("All");
			} else {
				$("#regionName" + i).text(holidayTOList[i].regionName);
			}

			if (holidayTOList[i].stateId == 0) {
				$("#stateName" + i).text("All");
			} else {
				$("#stateName" + i).text(holidayTOList[i].stateName);
			}

			if (holidayTOList[i].cityId == 0) {
				$("#stationName" + i).text("All");
			} else {
				$("#stationName" + i).text(holidayTOList[i].cityName);
			}

			if (holidayTOList[i].branchId == 0) {
				$("#branchName" + i).text("All");
			} else {
				$("#branchName" + i).text(holidayTOList[i].branchName);
			}

			$("#holidayName" + i).text(holidayTOList[i].holidayName);
			$("#ifOthers" + i).text(holidayTOList[i].others);
			document.getElementById("regionId" + i).value = holidayTOList[i].regionId;
			document.getElementById("stateId" + i).value = holidayTOList[i].stateId;
			document.getElementById("stationId" + i).value = holidayTOList[i].cityId;
			document.getElementById("branchId" + i).value = holidayTOList[i].branchId;
			document.getElementById("holidayId" + i).value = holidayTOList[i].holidayNameId;
		}
	} else {
		$("#Save").prop("disabled", false);
		$("#Save").attr('class', 'btnintform');

		$("#Search").prop("disabled", true);
		$("#Search").attr('class', 'btnintformbigdis');

		$("#ViewYearly").prop("disabled", false);
		$("#ViewYearly").attr('class', 'btnintform');
	}
}

function addRow() {
	$('#holidayTableGrid').dataTable().fnAddData(
			[
					'<input type="radio" name="radioHoliday" id="ischecked'
							+ rowCount + '" tabindex="-1" value="' + rowCount
							+ '" onclick="enableEditButton()"/>',
					'<span id="serial' + rowCount + '">' + countNo + '</span>',
					'<span id="date' + rowCount + '"><span/>',
					'<span id="regionName' + rowCount
							+ '"></span><input type="hidden" id="regionId'
							+ rowCount + '"/>',
					'<span id="stateName' + rowCount
							+ '"></span><input type="hidden" id="stateId'
							+ rowCount + '"/>',
					'<span id="stationName' + rowCount
							+ '"></span><input type="hidden" id="stationId'
							+ rowCount + '"/>',
					'<span id="branchName' + rowCount
							+ '"></span><input type="hidden" id="branchId'
							+ rowCount + '"/>',
					'<span id="holidayName' + rowCount
							+ '"></span><input type="hidden" id="holidayId'
							+ rowCount + '"/>',
					'<span id="ifOthers' + rowCount + '"></span>' ]);
	rowCount++;
	countNo++;

}

function enableEditButton() {
	$("#Edit").prop("disabled", false);
	$("#Edit").attr('class', 'btnintform');
}

function formValidationHM() {

	var date = $("#date");
	if (date.val() == "" || date.val() == '-1') {
		alert('Select Mandatory Date');
		date.focus();
		return false;
	}
	var regionList = $("#regionList");
	if ((regionList.val() == "" || regionList.val() == '-1')
			&& regionList.val() != 'null') {
		alert('Select Mandatory Region');
		regionList.focus();
		return false;
	}
	if (regionList.val() != 'null') {
		var stateList = $("#stateList");
		if (stateList.val() == "" || stateList.val() == '-1') {
			alert('Select Mandatory State');
			stateList.focus();
			return false;
		}
		if (stateList.val() != 'null') {
			var cityList = $("#cityList");
			if (cityList.val() == "" || cityList.val() == '-1') {
				alert('Select Mandatory City');
				cityList.focus();
				return false;
			}
			if (cityList.val() != 'null') {
				var branchlist = $("#branchlist");
				if (branchlist.val() == "" || branchlist.val() == '-1') {
					alert('Select Mandatory branch');
					branchlist.focus();
					return false;
				}
			}
		}
	}
	var reason = $("#reason");
	if (reason.val() == "" || reason.val() == '-1') {
		alert('Select Mandatory reason');
		reason.focus();
		return false;
	}
	if (reason.val() == "0") {
		var others = $("#others");
		if (others.val() == "") {
			others.focus();
			alert('Please enter others');
			return false;
		}
	}
}

function editExistingHoliday() {
	var selectedRow = $('input[type=radio][name=radioHoliday]:checked').val();

	var date = $('#date' + selectedRow).html();
	var regionId = $('#regionId' + selectedRow).val();
	//var regionName = $('#regionName' + selectedRow + ' span').text();
	var regionName = $('#regionName' + selectedRow).html();
	var stateId = $('#stateId' + selectedRow).val();
	var stateName = $('#stateName' + selectedRow).html();
	var stationId = $('#stationId' + selectedRow).val();
	var stationName = $('#stationName' + selectedRow).html();
	var branchId = $('#branchId' + selectedRow).val();
	var branchName = $('#branchName' + selectedRow).html();
	var reasonId = $('#holidayId' + selectedRow).val();

	var others = $('#ifOthers' + selectedRow).html();

	var url = "holiday.do?submitName=prepareEditHolidayPage&regionId="
			+ regionId + "&stateId=" + stateId + "&stationId=" + stationId
			+ "&branchId=" + branchId + "&reasonId=" + reasonId + "&others="
			+ others + "&regionName=" + regionName + "&stateName=" + stateName
			+ "&stationName=" + stationName + "&branchName=" + branchName + "&date=" + date;
	
	alert(url);

	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=800,height=300,left = 412,top = 184');
}

function enableElements(btnId){
	
	//jQuery("#"+btnId).attr("disabled", "");
	jQuery("#"+btnId).attr("disabled", false);
	jQuery("#" + btnId).removeClass("btnintformbigdis");
	jQuery("#" + btnId).addClass("btnintform");
}

function editHoliday() {
	formValidationHM();
	
	enableElements("regionList");
	enableElements("stateList");
	enableElements("cityList");
	enableElements("branchList");
	
	document.holidayForm.action = "./holiday.do?submitName=saveHoliday&senderPage=editPopUp";
	document.holidayForm.submit();
}

document.onkeypress = stopRKey;