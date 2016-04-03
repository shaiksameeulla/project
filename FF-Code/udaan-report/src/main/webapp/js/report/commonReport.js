var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

var selectedRegions = 0;
var selectedStations = 0;
var selectedBranches = 0;

function getStationsListForSlab(id) {
	clearAllFields();
	var region = document.getElementById('regionList').value;
	if (!isNull(region)) {
		var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
				+ region;
		ajaxCallWithoutForm(url, populateStationListForSlab);
	}
}

function getStationsList(id) {
	clearAllFields();
	var region = document.getElementById('regionList').value;
	if (!isNull(region)) {
		var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
				+ region;
		ajaxCallWithoutForm(url, populateStationList);
	}
}
function clear(){
	$("#customerList").empty();
	//$("#client").empty();
	//clearDropDown("customerList");
}


function getStationsListForBrr(id) {
	clearAllFields();
	var region = document.getElementById('regionList').value;
	if (!isNull(region)) {
		var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
				+ region;
		ajaxCallWithoutForm(url, populateStationListForBrr);
	}
}





function getStationsList(id, clearFields) {
	clearAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './baSalesReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateStationList);
	}
}

function getStationsListFORSlabRevenue(id, clearFields) {
	clearAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './baSalesReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateStationListFORSlabRevenue);
	}
}

function getStationsListFORSlabRevenue1(id, clearFields) {
	clearAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './baSalesReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateStationListFORSlabRevenue1);
	}
}

function populateStationListFORSlabRevenue(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
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

function populateStationListFORSlabRevenue1(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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

function getStationsListForStatus(id) {

	clearFields();

	var region = document.getElementById('regionList').value;
	var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
			+ region;

	ajaxCallWithoutForm(url, populateStationList);
}

function getStationsListAndPopulateDestination(id) {

	var region = $("#" + id).val();
	var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
			+ region;

	ajaxCallWithoutForm(url, populateDestinationStationList);

}

function populateStationListForSlab(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		/*defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));*/
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

function populateStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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

function populateStationListForBrr(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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






function populateDestinationStationList(data) {

	if (!isNull(data)) {
		var content = document.getElementById('destStationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);

		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);

		$.each(data, function(index, value) {
			if (this.cityName != null && $.trim(this.cityName).length > 0) {
				// this.cityName.trim().length
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			}
		});
	}

}

function populateDestinationStationList1(data) {

	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);

		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);

		$.each(data, function(index, value) {
			if (this.cityName != null && $.trim(this.cityName).length > 0) {
				// this.cityName.trim().length
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			}
		});
	}

}


function getBranchListForBrr(id) {
	var cityID = $("#" + id).val();
	if (!isNull(cityID)) {
		clearBranchs();
		var url = null;
		if (cityID.toLocaleLowerCase() == 'all') {
			var ddlArray = new Array();
			var ddl = document.getElementById(id);
			for ( var i = 2; i < ddl.options.length; i++) {
				ddlArray[i - 2] = ddl.options[i].value;
			}
			url = './yieldReport.do?submitName=getBranchList&cityID='
					+ ddlArray;
		} else {
			url = './baSalesReport.do?submitName=getBranchList&cityID='
					+ cityID;
		}
		ajaxCallWithoutForm(url, populateBranchList1);
	}
}

function populateBranchList1(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}
}



function getBranchList(id) {
	var cityID = $("#" + id).val();
	if (!isNull(cityID)) {
		clearBranchs();
		var url = null;
		if (cityID == 'All') {
			var ddlArray = new Array();
			var ddl = document.getElementById(id);
			for ( var i = 2; i < ddl.options.length; i++) {
				ddlArray[i - 2] = ddl.options[i].value;
			}
			url = './yieldReport.do?submitName=getBranchList&cityID='
					+ ddlArray;
		} else {
			url = './baSalesReport.do?submitName=getBranchList&cityID='
					+ cityID;
		}
		ajaxCallWithoutForm(url, populateBranchList);
	}
}


/*function getBranchListForBrr(id) {
	var cityID = $("#" + id).val();
	if (!isNull(cityID)) {
		clearBranchs();
		var url = null;
		if (cityID.toLocaleLowerCase() == 'all') {
			var ddlArray = new Array();
			var ddl = document.getElementById(id);
			for ( var i = 2; i < ddl.options.length; i++) {
				ddlArray[i - 2] = ddl.options[i].value;
			}
			url = './yieldReport.do?submitName=getBranchList&cityID='
					+ ddlArray;
		} else {
			url = './baSalesReport.do?submitName=getBranchList&cityID='
					+ cityID;
		}
		ajaxCallWithoutForm(url, populateBranchListForBrr);
	}
}*/

function populateBranchList(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}
}


function populateBranchListForBrr(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}
}



function getDestBranchList(id) {
	var cityID = $("#" + id).val();
	var url = './consignmentBookingDetailsReport.do?submitName=getBranchList&cityID='
			+ cityID;
	ajaxCallWithoutForm(url, populateDestBranchList);

}

function populateDestBranchList(data) {
	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('destBranchList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}

}

function getClientList(id, clearFields) {
	var branch = readBranch();
	// showProcessing();
	var url = './custSlabWiseRevenue.do?submitName=getCustomerList&branch='
			+ branch;
	var clientObj = document.getElementById('client');
	if (!isNull("clientObj") && !isNaN(clientObj)) {
		// ajaxCallWithoutForm(url, populateClientList);
		jQuery.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			success : function(data) {
				// jQuery.unblockUI();
				populateClientList(data);
			}
		});
	}

}

function populateClientList(data) {

	if (!isNull(data)) {
		// showProcessing();
		var responseText = data;

		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.businessName != null
					&& $.trim(this.businessName).length > 0) {
				// this.businessName.trim().length
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
	}
	hideProcessing();
}

function addProduct() {
	selectedProduct = "selectedProduct";
	var sourceProduct = getDomElementById("sourceProduct");
	var selectedProduct = getDomElementById("selectedProduct");
	// var customerList = $("#customerList").val();
	var custList = $("#custList").val();
	var count = 0;
	var selectedCount = 0;
	// clearDropDownList(custList);
	if (sourceProduct.length != 0) {
		for ( var i = 0; i < sourceProduct.length; i++) {
			if (sourceProduct[i].selected) {
				selectedCount = selectedCount + 1;
				if (selectedProduct.length == null
						|| selectedProduct.length == 0) {
					appendValueToMultiselectDropsown(selectedProduct,
							sourceProduct[i].text, sourceProduct[i].value);
				} else {
					for ( var j = 0; j < custList.length; j++) {
						if (sourceProduct[i].text == selectedProduct[j].text) {
							alert('Product already in list');

						} else {
							count = count + 1;
						}
					}
					if (count == custList.length) {
						count = 0;
						appendValueToMultiselectDropsown(selectedProduct,
								sourceProduct[i].text, sourceProduct[i].value);
					}
				}

			}
		}
	} else {
		alert('No data available');
	}

	if (selectedCount >= 2) {
		disabledbills();
	} else if (selectedCount == 1) {
		enabledbills();
	}

}

function appendValueToMultiselectDropsown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}
function clearFields(clearFields) {

	if (getDomElementById("stationList")) {
		getDomElementById("stationList").options.length = 1;
	}
}

function clearAllFields(clearAllFields) {
	if (getDomElementById("stationList")) {
		getDomElementById("stationList").options.length = 1;
	}
	if (getDomElementById("branchList")) {
		getDomElementById("branchList").options.length = 1;
	}
}

function clearBranchs() {
	if (getDomElementById("branchList")) {
		getDomElementById("branchList").options.length = 1;
	}
}

/* Consignment Bokking Section */

function showConsignmentBookingDetail() {

	var msg = "";
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;

	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;

	var branch = $("#branchList").val();
	var branchName = document.getElementById('branchList').options[document
			.getElementById('branchList').selectedIndex].text;
	
	var customerType = $("#customerType").val();
	var customerTypeDesc = document.getElementById('customerType').options[document
			.getElementById('customerType').selectedIndex].text;
	
	var priority = $("#Priority").val();
	
	var destRegionId = $("#destRegionList").val();
	var destStationId = $("#destStationList").val();

	var destRegionList = selectedRegions;
	var destStationList = selectedStations;

	destRegionList = GetSelected(document.getElementById("destRegionList"));
	var destRegionName = $("#destRegionList option:selected").map(function() {
		return this.text;
	}).get().join(", ");

	destStationList = GetSelected(document.getElementById("destStationList"));
	var destStationName = $("#destStationList option:selected").map(function() {
		if (this.text == "ALL") {
			return this.val;
		} else {
			return this.text;
		}

	}).get().join(", ");

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	var client = GetSelected(document.getElementById("customerList")); // $("#client").val();
	var clientName = document.getElementById('customerList').options[document
	                                                    			.getElementById('customerList').selectedIndex].text;

	var product = GetSelected(document.getElementById("product"));
	var productName = $("#product option:selected").map(function() {
		return this.text;
	}).get().join(", ");
	if (isNull(productName)) {
		productName = 'All';
	}
	// getProduct(document.getElementById("product")); //$("#product").val();

	// if ((region == null || 'select' == region.toLocaleLowerCase())
	// || (station == null || 'select' == station.toLocaleLowerCase())
	// || (isNull(branch) || 'select' == branch.toLocaleLowerCase())
	// || (destRegionList == null || 'select' == destRegionList
	// .toLocaleLowerCase()) || (isNull(destStationList))
	// || (fromDate == null || $.trim(fromDate).length <= 0)
	// || (toDate == null || $.trim(toDate).length <= 0)) {

	if ((isNull(region) || 'select' == region.toLocaleLowerCase())
			|| (isNull(station) || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch) || 'select' == branch.toLocaleLowerCase())
			|| (isNull(customerType) || 'select' == customerType.toLocaleLowerCase())
			|| destRegionList == 0
			|| (destRegionList == null || 'select' == destRegionList
					.toLocaleLowerCase()) || destStationList == 0
			|| (isNull(destStationList))
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}

		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}

		if (isNull(branch) || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (isNull(customerType) || 'select' == customerType.toLocaleLowerCase()) {
			msg = msg + "Select Customer Type \n";
		}

		
		if (destRegionList == null || destRegionList == 0
				|| 'select' == destRegionList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}

		if (isNull(destStationList) || destStationList == 0) {
			msg = msg + "Select Destination Station \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		}

		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		}

		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		if (region == 0) {
			region = '';
		}
		if (station == 0) {
			station = '';
		}
		if (branch == 0) {
			branch = '';
		}
		var url;

		if (product == 4) {

			url = '/udaan-report/pages/reportviewer/baBookingViewer.jsp?'
					+ "&regionName="
					+ regionName
					+ "&stationName="
					+ stationName
					+ "&branchName="
					+ branchName
					+ "&branchID="
					+ branch
					+ "&dstStation="
					+ destStationList
					+ "&fromDate="
					+ fromDate
					+ "&destRegionName="
					+ destRegionName
					+ "&destStationName="
					+ destStationName
					+ "&toDate="
					+ toDate
					+ "&client="
					+ client
					+ "&product="
					+ product
					+ "&clientName="
					+ clientName
					+ "&productName="
					+ productName
					+ "&region="
					+ region
					+ "&station="
					+ station
					+ "&destRegionId="
					+ destRegionId
					+ "&destStationId="
					+ destStationId;
		} else {
			url = '/udaan-report/pages/reportviewer/bookingViewer.jsp?'
					+ "&regionName="
					+ regionName
					+ "&stationName="
					+ stationName
					+ "&branchName="
					+ branchName
					+ "&branchID="
					+ branch
					+ "&customerType="
					+ customerType
					+ "&priority="
					+ priority
					+  "&customerTypeDesc="
					+ customerTypeDesc
					+ "&dstStation="
					+ destStationList
					+ "&fromDate="
					+ fromDate
					+ "&destRegionName="
					+ destRegionName
					+ "&destStationName="
					+ destStationName
					+ "&toDate="
					+ toDate
					+ "&client="
					+ client
					+ "&product="
					+ product
					+ "&clientName="
					+ clientName
					+ "&productName="
					+ productName
					+ "&region="
					+ region
					+ "&station="
					+ station
					+ "&destRegionId="
					+ destRegionId
					+ "&destStationId="
					+ destStationId;
		}
		//alert(url);
		window.open(url, "_blank");
	}
}

/* Booking Summary Section */
function showSummaryReport() {
	var msg = "";
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var branch = $("#branchList").val();
	var destRegionList = $("#destRegionList").val();
	var destStationList = $("#destStationList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var client = $("#client").val();
	var product = $("#product").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (isNull(branch))
			|| (destRegionList == null || 'select' == destRegionList
					.toLocaleLowerCase()) || (isNull(destStationList))
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (destRegionList == null
				|| 'select' == destRegionList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}

		if (isNull(destStationList)) {
			msg = msg + "Select Destination Station \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		}

		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		}

		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/bookingSummaryViewer.jsp?'
				+ "&branchID=" + branch + "&dstStation=" + destStationList
				+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&client="
				+ client + "&product=" + product;

		window.open(url, "_blank");
	}
}

/* POD Section */
function showPodReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var product = $("#product").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch))
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch)) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/podViewer.jsp?'
				+ "&branchID=" + branch + "&fromDate=" + fromDate + "&toDate="
				+ toDate + "&product=" + product;
		window.open(url, "_blank");
	}
}

/* HeldUp */
function showHeldUpReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var regionId = region;
	var station = $("#stationList").val();
	var stationId = station;

	// ******* *****New Code added **************
	var regions = $("#regionList option:selected").map(function() {
		return this.text;
	}).get().join(", ");
	var stations = $("#stationList option:selected").map(function() {
		return this.text;
	}).get().join(", ");
	var branches = $("#branchList option:selected").map(function() {
		return this.text;
	}).get().join(", ");
	// ******* *****New Code added **************

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch) || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch) || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}

		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/heldUpViewer.jsp?'
				+ "&branchID=" + branch + "&fromDate=" + fromDate + "&toDate="
				+ toDate + "&regions=" + regions + "&stations=" + stations
				+ "&branches=" + branches + "&regionId=" + regionId
				+ "&stationId=" + stationId;
		window.open(url, "_blank");
	}
}

/* MisRoute Report */
function showMisRouteReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch))
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch)) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/misRouteViewer.jsp?'
				+ "&branchID=" + branch + "&fromDate=" + fromDate + "&toDate="
				+ toDate;
		window.open(url, "_blank");
	}
}
/* AirPort Offload */
function showAirportOffloadReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}

		alert(msg);

	} else {

		var url = '/udaan-report/pages/reportviewer/offloadViewer.jsp?'
				+ "&branchID=" + branch + "&fromDate=" + fromDate + "&toDate="
				+ toDate;
		window.open(url, "_blank");
	}
}

/* productRTO */
function showproductRTOReport() {
	var msg = "";
	// var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var destRegionList = $("#destRegionList").val();
	var destStationList = $("#destStationList").val();
	var product = $("#product").val();
	var client = $("#client").val();

	var originRegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var originStationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	var destRegionName = document.getElementById('destRegionList').options[document
			.getElementById('destRegionList').selectedIndex].text;
	var destStationName = document.getElementById('destStationList').options[document
			.getElementById('destStationList').selectedIndex].text;

	if (originRegionName == "Select")
		originRegionName = "";
	if (originStationName == "Select")
		originStationName = "";
	if (destRegionName == "Select")
		destRegionName = "";
	if (destStationName == "Select")
		destStationName = "";

	if (isNull(product)) {
		msg = msg + "Please select Product.";
		$("#product").focus();
	} else if (isNull(region)) {
		msg = msg + "Please select Origin Region";
		$("#regionList").focus();
	} else if (isNull(originStationName)) {
		msg = msg + "Please select Origin Station";
		$("#stationList").focus();
	} else if (isNull(destRegionName)) {
		msg = msg + "Please select Destination Region";
		$("#destRegionList").focus();
	} else if (isNull(destStationName)) {
		msg = msg + "Please select Destination Station";
		$("#destStationList").focus();
	} else if (fromDate == null || $.trim(fromDate).length <= 0) {
		msg = msg + "Please select From Date";
		$("#fromDate").focus();
	} else if (toDate == null || $.trim(toDate).length <= 0) {
		msg = msg + "Please select To Date";
		$("#toDate").focus();
	}
	if (!isNull(msg)) {
		alert(msg);

	} else {
		if (isNull(client)) {
			client = "All";
		}
		if (checkDate('fromDate', 'toDate')) {
			var url = '/udaan-report/pages/reportviewer/productRTOViewer.jsp?'
					+ "&fromDate="
					+ fromDate
					+ "&toDate="
					+ toDate
					+ "&product="
					+ product
					+ "&client="
					+ client
					+ "&originRegion="
					+ region
					+ "&originStation="
					+ station
					+ "&dstRegionId="
					+ destRegionList
					+ "&destStationList="
					+ destStationList
					+ "&originRegionName="
					+ originRegionName
					+ "&originStationName="
					+ originStationName
					+ "&destRegionName="
					+ destRegionName
					+ "&destStationName="
					+ destStationName;
			window.open(url, "_blank");
		}
	}
}

/* priorityTATFailure */
function showpriorityTATFailureReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch))
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch)) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/priorityViewer.jsp?'
				+ "&branchID=" + branch + "&region=" + region + "&station="
				+ station + "&fromDate=" + fromDate + "&toDate=" + toDate;
		window.open(url, "_blank");
	}
}
/* productStatus */
function showProductStatus() {
	var msg = "";
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var reportType = $("#reportType").val();
	var destRegionList = $("#destRegionList").val();
	var dstStation = $("#destStationList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var product = $("#product").val();

	if (isNull(product)) {
		msg = msg + "Select Product \n";
	} else if (region == null || 'select' == region.toLocaleLowerCase()) {
		msg = msg + "Select Origin Region \n";
	} else if (station == null || 'select' == station.toLocaleLowerCase()) {
		msg = msg + "Select Origin Station \n";
	} else if (destRegionList == null
			|| 'select' == destRegionList.toLocaleLowerCase()) {
		msg = msg + "Select Destination Region \n";
	} else if ((dstStation == null || '' == dstStation.toLocaleLowerCase())
			|| 'select' == dstStation.toLocaleLowerCase()) {
		msg = msg + "Select Destination Station \n";
	} else if (fromDate == null || $.trim(fromDate).length <= 0) {
		msg = msg + "Select From Date \n";
	} else if (toDate == null || $.trim(toDate).length <= 0) {
		msg = msg + "Select To Date \n";
	} else if (isNull(reportType)) {
		msg = msg + "Select Report Type \n";
	}
	if (!isNull(msg)) {
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {

		if (isNull(product)) {
			product = "All";
		}
		var originRegionName = document.getElementById('regionList').options[document
				.getElementById('regionList').selectedIndex].text;
		var originRegionId = document.getElementById('regionList').value;
		var originStationName = document.getElementById('stationList').options[document
				.getElementById('stationList').selectedIndex].text;
		var destRegionName = document.getElementById('destRegionList').options[document
				.getElementById('destRegionList').selectedIndex].text;
		var destRegionId = document.getElementById('destRegionList').value;
		var destStationName = document.getElementById('destStationList').options[document
				.getElementById('destStationList').selectedIndex].text;
		var reportTypeName = document.getElementById('reportType').options[document
				.getElementById('reportType').selectedIndex].text;
		var productName = $("#product option:selected").map(function() {
			return this.text;
		}).get().join(", ");

		var url = '/udaan-report/pages/reportviewer/statusViewer.jsp?'
				+ "&product="
				+ product
				+ "&dstStation="
				+ dstStation
				+ "&fromDate="
				+ fromDate
				+ "&toDate="
				+ toDate
				+ "&origionStation="
				+ station
				+ "&originRegionName="
				+ originRegionName
				+ "&originStationName="
				+ originStationName
				+ "&destRegionName="
				+ destRegionName
				+ "&destStationName="
				+ destStationName
				+ "&reportTypeName="
				+ reportTypeName
				+ "&originRegionId="
				+ originRegionId
				+ "&destRegionId="
				+ destRegionId
				+ "&productName="
				+ productName
				+ "&reportType="
				+ reportType;
//alert(url);
		window.open(url, "_blank");
	}

}

function showWeightDiscrepencyReport() {
	var msg = "";
	var manifestNo = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/weightDiscrepencyViewer.jsp?'
				+ "&branchID="
				+ branch
				+ "&region="
				+ region
				+ "&station="
				+ station + "&fromDate=" + fromDate + "&toDate=" + toDate;
		+"&Manifest=" + manifestNo;
		window.open(url, "_blank");
	}
}

function showHighValueReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select  Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select  Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select  Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		}

		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/highValueViewer.jsp?'
				+ "&branchID=" + branch + "&region=" + region + "&station="
				+ station + "&fromDate=" + fromDate + "&toDate=" + toDate;
		window.open(url, "_blank");
	}

}
function showClientWiseSummary() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var destRegionList = $("#destRegionList").val();
	var destStationList = $("#destStationList").val();
	var destBranchList = $("#destBranchList").val();
	var product = $("#product").val();
	var client = $("#client").val();
	if (product.toLocaleLowerCase() == 'select') {
		product = "";
	}
	if (client.toLocaleLowerCase() == 'select') {
		client = "";
	}

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch))
			|| (destRegionList == null || 'select' == destRegionList
					.toLocaleLowerCase())
			|| (isNull(destStationList))
			|| (destBranchList == null || 'select' == destBranchList
					.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch)) {
			msg = msg + "Select Origin Branch \n";
		}

		if (destRegionList == null
				|| 'select' == destRegionList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}

		if (isNull(destStationList)) {
			msg = msg + "Select Destination Station \n";
		}
		if (destBranchList == null
				|| 'select' == destBranchList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			fromDate = "Jan 01, 1990 01:00 AM";
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			toDate = "Jan 01, 2030 01:00 AM";
			msg = msg + "Select To Date \n";
		}
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {

		var url = '/udaan-report/pages/reportviewer/clientSummaryViewer.jsp?'
				+ "&branchID=" + branch + "&region=" + region + "&station="
				+ station + "&destRegionList=" + destRegionList
				+ "&destStationList=" + destStationList + "&destBranchList="
				+ destBranchList + "&product=" + product + "&client=" + client
				+ "&fromDate=" + fromDate + "&toDate=" + toDate;
		window.open(url, "_blank");
	}
}

function showClientWiseBooking() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var destRegionList = $("#destRegionList").val();
	var destStationList = $("#destStationList").val();
	var destBranchList = $("#destBranchList").val();
	var product = $("#product").val();
	var client = $("#client").val();
	if (product.toLocaleLowerCase() == 'select') {
		product = "";
	}
	if (client.toLocaleLowerCase() == 'select') {
		client = "";
	}

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch))
			|| (destRegionList == null || 'select' == destRegionList
					.toLocaleLowerCase())
			|| (isNull(destStationList))
			|| (destBranchList == null || 'select' == destBranchList
					.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch)) {
			msg = msg + "Select Origin Branch \n";
		}

		if (destRegionList == null
				|| 'select' == destRegionList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}

		if (isNull(destStationList)) {
			msg = msg + "Select Destination Station \n";
		}
		if (destBranchList == null
				|| 'select' == destBranchList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		}
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/clientProductWiseBookingViewer.jsp?'
				+ "&branchID="
				+ branch
				+ "&region="
				+ region
				+ "&station="
				+ station
				+ "&destRegionList="
				+ destRegionList
				+ "&destStationList="
				+ destStationList
				+ "&destBranchList="
				+ destBranchList
				+ "&product="
				+ product
				+ "&client="
				+ client
				+ "&fromDate=" + fromDate + "&toDate=" + toDate;
		window.open(url, "_blank");
	}
}

function clearScreen() {
	if (confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();
	}

}

function checkDate(fromDate, toDate) {
	var startDate = $("#" + fromDate).val();
	var endDate = $("#" + toDate).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1] - 1, arrEndDate[0]);

	var today = new Date();
	if (date2 > today) {
		alert('Future Date cannot be entered');
		return false;
	}

	if (date1 == "" || date1 > date2) {

		alert("Please ensure that end date is greater than or equal to start date");

		$("#" + fromDate).val("");

		$("#" + fromDate).focus();

		$("#" + toDate).val("");

		// $("#" + toDate).focus();

		return false;

	} else {
		return true;
	}
}

function checkMonth(fromDate, toDate) {
	var startDate = $("#" + fromDate).val();
	var endDate = $("#" + toDate).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[1], arrEndDate[0] - 1, 1);
	var today = new Date();
	if (date2 > today) {
		alert('Future Month cannot be entered');
		$("#" + toDate).val("");

		$("#" + toDate).focus();
		return false;
	}

	if (date1 == "" || date1 > date2) {

		alert("Please ensure that to Month is greater than or equal to from month");

		$("#" + fromDate).val("");

		$("#" + fromDate).focus();

		return false;

	} else {
		return true;
	}
}

function showOriginHitRatioReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var product = $("#product").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (product == null || 'select' == product.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (product == null || 'select' == product.toLocaleLowerCase()) {
			msg = msg + "Select Product \n";
		}

		alert(msg);
	}

	else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/originViewer.jsp?';
		window.open(url, "_blank");
	}

}

function showBrrDatewiseReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var product = $("#product").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (product == null || 'select' == product.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (product == null || 'select' == product.toLocaleLowerCase()) {
			msg = msg + "Select Product \n";
		}

		alert(msg);
	}

	else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/brrDatewiseViewer.jsp?';
		window.open(url, "_blank");
	}
}

function showBrrSummaryReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var product = $("#product").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (product == null || 'select' == product.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (product == null || 'select' == product.toLocaleLowerCase()) {
			msg = msg + "Select Product \n";
		}

		alert(msg);
	}

	else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/brrSummaryViewer.jsp';
		window.open(url, "_blank");
	}
}

function showHitRatioDatewiseReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		alert(msg);
	}

	else if (checkDate('fromDate', 'toDate')) {
		var url = '/udaan-report/pages/reportviewer/hitRatioDatewiseViewer.jsp?';
		window.open(url, "_blank");
	}
}

function showHitRatioBranchwiseReport() {
	var msg = "";
	//var branch = $("#branchList1").val();
	var branch=getDomElementById("branchList1");
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var category = $("#category").val();
	var series = $("#series").val();
	var type = $("#type").val();
	var load = $("#load").val();

	var originRegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			//|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (category == null || 'select' == category.toLocaleLowerCase())
			|| (series == null || 'select' == series.toLocaleLowerCase())
			|| (type == null || 'select' == type.toLocaleLowerCase())
			|| (load == null || 'select' == load.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		/*if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}*/

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (category == null || $.trim(category).length <= 0
				|| 'select' == category.toLocaleLowerCase()) {
			msg = msg + 'Please Select Category\n';

		}

		if (series == null || $.trim(series).length <= 0
				|| 'select' == series.toLocaleLowerCase()) {
			msg = msg + 'Please Select Series\n';

		}

		if (type == null || $.trim(type).length <= 0
				|| 'select' == type.toLocaleLowerCase()) {
			msg = msg + 'Please Select Type\n';

		}

		if (load == null || $.trim(load).length <= 0
				|| 'select' == load.toLocaleLowerCase()) {
			msg = msg + 'Please Select Load\n';

		}

		alert(msg);
		return;
	}

	else if (checkDate('fromDate', 'toDate')) {
		var stationId, stationName, branchId, branchName, delvCategoryCode = "", delvCategoryName = "", seriesData = "", seriesLabel = "", cntypeCode, cntypeName, loadNum, loadDesc;
		if (station == 'All') {
			stationId = 0;
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text
					.toUpperCase();
		} else {
			stationId = $("#stationList").val();
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
		}

		var branchId= "";
		$("#branchList1 option").each(function () {
			if(isNull(branchId)){
				branchId += $(this).val();
			} else {
				branchId+= "," + $(this).val(); 
			}

		});
		var branchName = "";
			$("#branchList1 option").each(function () {
		if(isNull(branchName)){
			branchName += $(this).text();
		} else {
			branchName+= "," + $(this).text(); 
		}
	});
		
		/*if (branch == 'All') {
			branchId = 0;
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text
					.toUpperCase();
		} else {
			branchId = $("#branchList").val();
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
		}*/

		if (category == 'ALL') {
			delvCategoryCode = category;
			delvCategoryName = category;
		} else {
			delvCategoryCode = category;
			delvCategoryName = document.getElementById('category').options[document
					.getElementById('category').selectedIndex].text;
		}

		var seriesdatas = document.getElementById("series");

		if ($("#series").val().toUpperCase() == "ALL") {
			for ( var i = 2; i < seriesdatas.length; i++) {
				seriesData = seriesData + seriesdatas.options[i].value;

				if (i != (seriesdatas.length - 1)) {
					seriesData = seriesData + ",";
				}
			}
		} else {
			seriesData = $("#series").val();
		}

		seriesLabel = $("#series").val();

		if (type == 'ALL') {
			cntypeCode = type;
			cntypeName = type;
		} else {
			cntypeCode = type;
			cntypeName = document.getElementById('type').options[document
					.getElementById('type').selectedIndex].text;
		}

		if (load == 'ALL') {
			loadNum = 0;
			loadDesc = load;
		} else {
			loadNum = load;
			loadDesc = document.getElementById('load').options[document
					.getElementById('load').selectedIndex].text;
		}

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var arrStartDate = fromDate.split("/");
		var startDateFormat = arrStartDate[2] + '-' + arrStartDate[1] + '-'
				+ arrStartDate[0];
		var arrEndDate = toDate.split("/");
		var endDateFormat = arrEndDate[2] + '-' + arrEndDate[1] + '-'
				+ arrEndDate[0];

		var report = $("#Name").val();
		if (report == "BranchWise") {
			var url = '/udaan-report/pages/reportviewer/hitRatioBranchwiseViewer.jsp?'
					+ "&RegionId="
					+ region
					+ "&RegionName="
					+ originRegionName
					+ "&StationId="
					+ stationId
					+ "&StationName="
					+ stationName
					+ "&BranchId="
					+ branchId
					+ "&BranchName="
					+ branchName
					+ "&FromDate="
					+ startDateFormat
					+ "&ToDate="
					+ endDateFormat
					+ "&DeliveryCategoryCode="
					+ delvCategoryCode
					+ "&DeliveryCategoryName="
					+ delvCategoryName
					+ "&Series="
					+ seriesData
					+ "&SeriesLabel="
					+ seriesLabel
					+ "&ConsignmentTypeCode="
					+ cntypeCode
					+ "&ConsignmentTypeName="
					+ cntypeName
					+ "&LoadNumber=" + loadNum + "&LoadDescription=" + loadDesc;
			 //alert(url);
			window.open(url, "_blank");
		}
		if (report == "DateWise") {
			var url = '/udaan-report/pages/reportviewer/hitRatioDestinationDateWise.jsp?'
					+ "&RegionId="
					+ region
					+ "&RegionName="
					+ originRegionName
					+ "&StationId="
					+ stationId
					+ "&StationName="
					+ stationName
					+ "&BranchId="
					+ branchId
					+ "&BranchName="
					+ branchName
					+ "&FromDate="
					+ startDateFormat
					+ "&ToDate="
					+ endDateFormat
					+ "&DeliveryCategoryCode="
					+ delvCategoryCode
					+ "&DeliveryCategoryName="
					+ delvCategoryName
					+ "&Series="
					+ seriesData
					+ "&SeriesLabel="
					+ seriesLabel
					+ "&ConsignmentTypeCode="
					+ cntypeCode
					+ "&ConsignmentTypeName="
					+ cntypeName
					+ "&LoadNumber=" + loadNum + "&LoadDescription=" + loadDesc;
			//alert(url);
			window.open(url, "_blank");
		}
	}
}

function showBASalesReport() {

	var msg = "";
	// var region = readRegion();
	// var station =readStation();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var product = $("#product").val();

	var region = selectedRegions;
	var station = selectedStations;
	var branch = selectedBranches;

	var branchName = document.getElementById("branchList").value;
	var stationName = document.getElementById("originStation").value;

	if (document.getElementById("originRegion").disabled) {
		region = document.getElementById("originRegion").value;
	} else {
		region = selectedRegions;
	}

	if (document.getElementById("originStation").disabled) {
		station = document.getElementById("originStation").value;
	} else {
		station = selectedStations;
	}

	if (document.getElementById("branchList").disabled) {
		branch = document.getElementById("branchList").value;
	} else {
		branch = selectedBranches;
	}

	if ((fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0) || isNull(region)
			|| 'select' == region.toLocaleLowerCase()) {

		if (isNull(region) || 'select' == region.toLocaleLowerCase())
			msg = msg + "Select Origin Region \n";

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {

		if (region == 0) {
			region = '';
		}
		if (station == 0) {
			station = '';
		}
		if (branch == 0) {
			branch = '';
		}
		var originRegionName = $("#originRegion option:selected").map(
				function() {
					return this.text;
				}).get().join(", ");
		var originStationName = $("#originStation option:selected").map(
				function() {
					return this.text;
				}).get().join(", ");
		if (isNull(originStationName)) {
			// originStationName = 'All';
			alert('Please select  origin station name');
		}
		var originBranchName = $("#branchList option:selected").map(function() {
			return this.text;
		}).get().join(", ");
		if (isNull(originBranchName)) {
			// originBranchName = "All";
			alert('Please select  branch name');
		}

		var productName = document.getElementById('product').options[document
				.getElementById('product').selectedIndex].text;
		var url = '/udaan-report/pages/reportviewer/baSalesViewer.jsp?'
				+ "&branch_ID=" + branch + "&fromDate=" + fromDate + "&toDate="
				+ toDate + "&product=" + product + "&region_ID=" + region
				+ "&originRegionName=" + originRegionName
				+ "&originStationName=" + originStationName
				+ "&originBranchName=" + originBranchName + "&productName="
				+ productName + "&city_ID=" + station;
		//alert(url);
		window.open(url, "_blank");

	}
}

function regionSalesReport() {
	var msg = "";
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	var branch = $("#branchList").val();
	var branchName = document.getElementById('branchList').options[document
			.getElementById('branchList').selectedIndex].text;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var product = $("#product").val();
	var productName = document.getElementById('product').options[document
			.getElementById('product').selectedIndex].text;

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (isNull(branch) || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (isNull(branch) || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Month \n";
		}

		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Month \n";
		}
		alert(msg);

	} else if (checkMonth('fromDate', 'toDate')) {

		var url = '/udaan-report/pages/reportviewer/regionSalesViewer.jsp?'
				+ "&region=" + region + "&regionName=" + regionName
				+ "&station=" + station + "&stationName=" + stationName
				+ "&branchID=" + branch + "&branchName=" + branchName
				+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&product="
				+ product + "&productName=" + productName;

		window.open(url, "_blank");
	}
}

/*
 * function calcWorkingDays(){
 * 
 * var region = document.getElementById('regionList').value; var fromDate =
 * $("#fromDate").val(); var mdy = fromDate.split('/'); var year = mdy[1]; var
 * month = mdy[0]; var days = Math.round(((new Date(year, month))-(new
 * Date(year, month-1)))/86400000);
 * 
 * var url = './salesReport.do?submitName=calcWorkingDays&region=' + region +
 * "&fromDate=" + fromDate + "&totalDays=" + days;
 * 
 * ajaxCallWithoutForm(url, calcwork); //ajaxCall(url, "consignmentReportForm",
 * calcwork); document.consignmentReportForm.action=url;
 * document.consignmentReportForm.submit(); }
 */

/*
 * function calcwork(data){
 * 
 * if (!isNull(data)) { var responseText =data; var error =
 * responseText[ERROR_FLAG]; //var success = responseText[SUCCESS_FLAG];
 * if(responseText!=null && error!=null){ alert(error); return ; }
 * document.getElementById("totWorking").value=data; } }
 */

function custSlabWiseReport() {

	if (checkCustSlabRevenueMandatory()) {
		var msg = "";
		var region = $("#regionList").val();
		var station = $("#stationList").val();
		var branch = $("#branchList").val();
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var product = $("#product").val();
		var slab = $("#slab").val();
		var client = $("#client").val();
		var allBranches = new Array();
		var allslab = new Array();
		var allclient = new Array();
		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Please Select Origin Region";
		} else if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Please Select Origin Station";
		} else if (isNull(branch) || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Please Select Origin Branch";
		} else if (isNull(fromDate) || $.trim(fromDate).length <= 0) {
			msg = msg + "Please Select From Date";
		} else if (isNull(toDate) || $.trim(toDate).length <= 0) {
			msg = msg + "Please Select To Date";
		} else if (isNull(product)) {
			msg = msg + "Please Select Product";
		} else if (isNull(slab)) {
			msg = msg + "Please Select Slab";
		}

		if (!isNull(msg)) {
			alert(msg);
		} else {
			if (slab.toLocaleLowerCase() == 'all') {
				slab = 0;
				/*
				 * var ddl = document.getElementById("slab"); for ( var i = 2; i <
				 * ddl.options.length; i++) { allslab[i - 2] =
				 * ddl.options[i].value; }
				 */
			} else {
				allslab[0] = slab;
			}

			if (branch.toLocaleLowerCase() == 'all') {
				branch = 0;
				/*
				 * var ddl = document.getElementById("branchList"); for ( var i =
				 * 2; i < ddl.options.length; i++) { allBranches[i - 2] =
				 * ddl.options[i].value; }
				 */
			} else {
				allBranches[0] = branch;
			}

			if (client.toLocaleLowerCase() == 'all') {
				client = 0;
				/*
				 * var ddl = document.getElementById("branchList"); for ( var i =
				 * 2; i < ddl.options.length; i++) { allclient[i - 2] =
				 * ddl.options[i].value; }
				 */
			} else {
				allclient[0] = branch;
			}
			if (isNull(client)) {
				client = 0;
			}
			var originRegionName = document.getElementById('regionList').options[document
					.getElementById('regionList').selectedIndex].text;
			var originStationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
			var originBranchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
			var productName = document.getElementById('product').options[document
					.getElementById('product').selectedIndex].text;
			var slabRange = document.getElementById('slab').options[document
					.getElementById('slab').selectedIndex].text;
			var clientName = document.getElementById('client').options[document
					.getElementById('client').selectedIndex].text;
			if (clientName.toLocaleLowerCase().trim() == 'select') {
				clientName = "All";
			}
			if (checkDate('fromDate', 'toDate')) {
				var url = '/udaan-report/pages/reportviewer/custSlabWiseRevenueViewer.jsp?'
						+ "&branchID="
						+ branch
						+ "&fromDate="
						+ fromDate
						+ "&toDate="
						+ toDate
						+ "&product="
						+ product
						+ "&slab="
						+ slab
						+ "&client="
						+ client
						+ "&region="
						+ region
						+ "&station="
						+ station
						+ "&originRegionName="
						+ originRegionName
						+ "&originStationName="
						+ originStationName
						+ "&originBranchName="
						+ originBranchName
						+ "&productName="
						+ productName
						+ "&slabRange="
						+ slabRange
						+ "&clientName="
						+ clientName;
				window.open(url, "_blank");
			}
		}
	}
}

// Code for Customer Slab Wise Revenue

function getSlabList(product) {
	$("#slab").empty();
	$("#client").empty();
	if (!isNull(product.value)) {
		var url = './custSlabWiseRevenue.do?submitName=getSlabList&product='
				+ product.value;
		ajaxCallWithoutFormWithAsync(url, populateSlabList);
	} else {
		alert('Please Select Product');
	}
}

function populateSlabList(data) {
	// $("#slab" ).empty();
	if (!isNull(data)) {
		var content = document.getElementById('slab');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "all";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.vobSlabId;
			if (!isNull(this.upperLimitLabel)) {
				option.appendChild(document.createTextNode(this.lowerLimitLabel
						+ "-" + this.upperLimitLabel));
			} else {
				option.appendChild(document
						.createTextNode(this.lowerLimitLabel));
			}
			content.appendChild(option);
		});
	}
	// hideProcessing();
	getCustomerByBranchAndRateProductCategory();
}

function showCustomerWiseQualityReport() {

	if (checkMandatoryCustWiseQuality()) {
		var msg = "";
		var region = readRegion();
		var station = readStation();
		var branch = readBranch();

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var product = $("#product").val();

		var slabList = $('#fuel option:selected').text().trim();
		var lowerlimit = 0;
		var upperlimit = 0;

		if (slabList != 'above 20' && slabList != 'All') {

			var split = slabList.split("-");
			lowerlimit = split[0];
			upperlimit = split[1];
			slabList = 0;
		}

		if (slabList == 'above 20') {

			slabList = 20;
			lowerlimit = 0;
			upperlimit = 0;
		}

		if ('All' == slabList) {

			slabList = 0;
			lowerlimit = 0;
			upperlimit = 0;
		}

		if ((fromDate == null || $.trim(fromDate).length <= 0)
				|| (toDate == null || $.trim(toDate).length <= 0)) {

			if (fromDate == null || $.trim(fromDate).length <= 0) {
				msg = msg + "Select From Date \n";
			}

			if (toDate == null || $.trim(toDate).length <= 0) {
				msg = msg + "Select To Date \n";
			}

			alert(msg);

		} else if (checkDate('fromDate', 'toDate')) {
			var originRegionName = $("#regionList option:selected").map(
					function() {
						return this.text;
					}).get().join(", ");
			var originStationName = $("#stationList option:selected").map(
					function() {
						return this.text;
					}).get().join(", ");
			var originBranchName = $("#branchList option:selected").map(
					function() {
						return this.text;
					}).get().join(", ");
			var productName = $("#product option:selected").map(function() {
				return this.text;
			}).get().join(", ");
			var fuelSlabValue = $("#fuel option:selected").map(function() {
				return this.text;
			}).get().join(", ");

			var url = '/udaan-report/pages/reportviewer/custWiseQualityAnalysisViewer.jsp?'
					+ "&region="
					+ region
					+ "&station="
					+ station
					+ "&branch="
					+ branch
					+ "&fromDate="
					+ fromDate
					+ "&toDate="
					+ toDate
					+ "&product="
					+ product
					+ "&slabList="
					+ slabList
					+ "&lowerlimit="
					+ lowerlimit
					+ "&upperlimit="
					+ upperlimit
					+ "&originRegionName="
					+ originRegionName
					+ "&originStationName="
					+ originStationName
					+ "&originBranchName="
					+ originBranchName
					+ "&productName="
					+ productName + "&fuelSlabValue=" + fuelSlabValue;
			window.open(url, "_blank");
		}
	}
}

function readBranch() {
	var branch = "";
	var branches = document.getElementById("branchList");

	if ($("#branchList").val().toUpperCase() == "ALL") {
		for ( var i = 2; i < branches.length; i++) {
			branch = branch + branches.options[i].value;

			if (i != (branches.length - 1)) {
				branch = branch + ", ";
			}
		}
	} else {
		branch = $("#branchList").val();
	}
	return branch;
}



function readCustomerType() {
	var typeId = "";
	var typeIds = document.getElementById("customerType");

	if ($("#customerType").val().toUpperCase() == "ALL") {
		for ( var i = 2; i < typeIds.length; i++) {
			typeId = typeId + typeIds.options[i].value;

			if (i != (typeIds.length - 1)) {
				typeId = typeId + ", ";
			}
		}
	} else {
		typeId = $("#customerType").val();
	}
	return typeId;
}






function readStation() {
	var station = "";
	var stations = document.getElementById("stationList");
	if ($("#stationList").val().toUpperCase() == "ALL") {
		for ( var i = 2; i < stations.length; i++) {
			station = station + stations.options[i].value;

			if (i != (stations.length - 1)) {
				station = station + ", ";
			}
		}
	} else {
		station = $("#stationList").val();
	}

	return station;
}

function readRegion() {
	var region = "";
	var regions = document.getElementById("regionList");

	if ($("#regionList").val().toUpperCase() == "ALL") {
		for ( var i = 2; i < regions.length; i++) {
			region = region + regions.options[i].value;

			if (i != (regions.length - 1)) {
				region = region + ", ";
			}
		}
	} else {
		region = $("#regionList").val();
	}

	return region;

}

function calcWorkingDays() {
	var offs = "";
	var region = document.getElementById('regionList').value;
	var fromDate = $("#fromDate").val();
	var branch = document.getElementById('branchList').value;
	var regioncheck = document.getElementById('regionList').value;
	var stationCheck = document.getElementById('stationList').value;

	if (regioncheck == 'Select' || isNull(regioncheck)) {
		alert('Please Select Region value ');
		$("#regionList").focus();
		return;
	}

	if (stationCheck == 'Select' || isNull(stationCheck)) {
		alert('Please Select Station value ');
		$("#stationList").focus();
		return;
	}

	if (branch == 'Select' || isNull(branch)) {
		alert('Please Select branch value ');
		$("#branchList").focus();
		return;
	}

	if (checkMonthSalesDsrReport()) {
		if (!isNull(fromDate)) {
			var mdy = fromDate.split('/');
			var year = mdy[1];
			var month = mdy[0];
			var days = Math.round(((new Date(year, month)) - (new Date(year,
					month - 1))) / 86400000);
			/*
			 * var startDate= new Date( year, (month-1) , 01 ); var
			 * start1=startDate.toString(); var endDate= new Date( year,
			 * (month-1) , days);
			 */
			var startDate = year + "-" + month + "-" + '01';
			var endDate = year + "-" + month + "-" + days;
			var branch = document.getElementById('branchList').value;
			var branchList = document.getElementById('branchList');
			var regioncheck = document.getElementById('regionList').value;
			var stationCheck = document.getElementById('stationList').value;

			if (regioncheck == 'Select') {
				alert('Please Select Region value ');
				$("#regionList").focus();
				return;
			}

			if (stationCheck == 'Select') {
				alert('Please Select Station value ');
				$("#stationList").focus();
				return;
			}

			if (branch == 'Select') {
				alert('Please Select branch value ');
				$("#branchList").focus();
				return;
			}

			if (branch == 'All') {
				if (!isNull(branchList)) {
					for ( var i = 0; i < branchList.length; i++) {
						if (!isNull(branchList[i].value)) {
							if (branchList[i].value != 'All'
									&& branchList[i].value != 'Select') {
								if (isNull(offs)) {
									offs += branchList[i].value;
								} else {
									offs += "," + branchList[i].value;
								}
							}
						}
					}
				}
			} else {
				offs = "";
				offs = document.getElementById('branchList').value;
			}

			if (isNull(offs)) {
				alert('branch is empty');
				return;
			}
			var url = './salesReport.do?submitName=calcWorkingDays&region='
					+ region + "&fromDate=" + fromDate + "&totalDays=" + days;

			/*
			 * ajaxCallWithoutForm(url, calcwork); ajaxCall(url,
			 * "consignmentReportForm", calcwork);
			 */
			jQuery.ajax({
				url : url,
				type : "GET",
				dataType : "json",
				async : false,
				success : function(data) {
					calcwork(data);
				}
			});

			/*
			 * document.consignmentReportForm.action=url;
			 * document.consignmentReportForm.submit();
			 */
			var workingDays = document.getElementById('totWorking').value;
			var originRegionName = document.getElementById('regionList').options[document
					.getElementById('regionList').selectedIndex].text;
			var originStationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
			var originbranchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
			/* var offs1="46,79,599"; */
			var url = '/udaan-report/pages/reportviewer/salesDsrViewer.jsp?'
					+ "&StartDate=" + startDate + "&EndDate=" + endDate
					+ "&BranchOffs=" + offs + "&WorkingDays="
					+ parseInt(workingDays) + "&originRegion="
					+ originRegionName + "&originStation=" + originStationName
					+ "&originBranch=" + originbranchName + "&forMonth="
					+ fromDate;

			window.open(url, "_blank");

		} else {
			alert('Please select month');
			return;
		}
	}
}

function calcwork(data) {

	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		document.getElementById("totWorking").value = data;
	}
}

function cancelSaleDsr() {
	var url = './salesReport.do?submitName=preparePage';
	document.consignmentReportForm.action = url;
	document.consignmentReportForm.submit();
}

function printLcCodReport() {

	var reportType = $("#reportName").val();
	var customerList = "";
	if (checkMandatoryFieldForLcCod()) {
		if (checkDateForLcCod()) {
			var prodSeries = $("#productTo").val();
			var region = $("#regionList").val();
			var type = $("#typeList").val();
			var summaryOption = $("#summaryOptionList").val();

			var sortOrder = $("#sortOrderList").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var sortList = $("#sortingList").val();

			if (document.getElementById("partyNameList").style.visibility == 'hidden') {
				customerList = null;
			} else {
				customerList = $("#partyNameList").val();
			}
			/*
			 * if($('#partyNameList').is(':visible')){ customerList =
			 * $("#partyNameList").val(); } else{ customerList=null; }
			 */
			var report = "report";
			var cust = 3336;
			var reg = 2;

			var mdy = startDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var startDates = year + "-" + month + "-" + day;

			var mdy = endDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var endDates = year + "-" + month + "-" + day;

			/*
			 * var url =
			 * './lcCodReport.do?submitName=getCustomerByRegionAndProduct&RegionId=' +
			 * region + "&ProdSeries=" + prodSeries;
			 */
			if (reportType == 'lcCodInSummary') {
				var url = '/udaan-report/pages/reportviewer/lcCodReportInSummaryViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers=" + customerList;

				window.open(url, "_blank");
			}

			else if (reportType == 'lcCodOutSummary') {
				var url = '/udaan-report/pages/reportviewer/lcCodReportOutSummaryViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers=" + customerList;

				window.open(url, "_blank");
			}

			else if (reportType == 'lcCodPreAlert') {
				var url = '/udaan-report/pages/reportviewer/lcCodReportPreAlertViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers=" + customerList;

				window.open(url, "_blank");
			}

			else if (reportType == 'lcCodPartyWise') {
				var url = '/udaan-report/pages/reportviewer/lcCodReportOutPartyViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers=" + customerList;

				window.open(url, "_blank");
			}
		}
	}

	// ajaxCallWithoutForm(url, customerCallBack);
}

function printLcCodReportInSummary() {

	var reportType = $("#reportName").val();
	var customerList = null;
	if (checkMandatoryFieldForLcCodInSummary()) {
		if (checkDateForLcCod()) {
			var prodSeries = $("#productTo").val();
			var region = $("#regionList").val();
			var type = $("#typeList").val();
			var summaryOption = $("#summaryOptionList").val();

			var sortOrder = $("#sortOrderList").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var sortList = null;

			var report = "report";
			var cust = 3336;
			var reg = 2;

			var mdy = startDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var startDates = year + "-" + month + "-" + day;

			var mdy = endDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var endDates = year + "-" + month + "-" + day;

			/*
			 * var url =
			 * './lcCodReport.do?submitName=getCustomerByRegionAndProduct&RegionId=' +
			 * region + "&ProdSeries=" + prodSeries;
			 */
			if (reportType == 'lcCodInSummary') {
				var typeName = document.getElementById('typeList').options[document
						.getElementById('typeList').selectedIndex].text;
				var productName = document.getElementById('productTo').options[document
						.getElementById('productTo').selectedIndex].text;
				var summaryOptionName = document
						.getElementById('summaryOptionList').options[document
						.getElementById('summaryOptionList').selectedIndex].text;
				var sortingOrderName = document.getElementById('sortOrderList').options[document
						.getElementById('sortOrderList').selectedIndex].text;
				// var regionName =
				// document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
				// var regionName=
				// document.getElementById("regionList").select();
				var regionName = $("#regionList option:selected").map(
						function() {
							return this.text;
						}).get().join(", ");
				var url = '/udaan-report/pages/reportviewer/lcCodReportInSummaryViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList
						+ "&Customers="
						+ customerList
						+ "&TypeName="
						+ typeName
						+ "&ProductName="
						+ productName
						+ "&SummaryOptionName="
						+ summaryOptionName
						+ "&SortingOrderName="
						+ sortingOrderName
						+ "&RegionNames=" + regionName;

				// alert(url);
				window.open(url, "_blank");
			}

			// ajaxCallWithoutForm(url, customerCallBack);
		}
	}
}

function printLcCodReportInPreAlert() {

	var reportType = $("#reportName").val();
	var customerList = null;
	if (checkMandatoryFieldForLcCodInPreAlert()) {
		if (checkDateForLcCod()) {
			var prodSeries = $("#productTo").val();
			var region = $("#regionList").val();
			var type = $("#typeList").val();
			var summaryOption = null;

			var sortOrder = $("#sortOrderList").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var sortList = $("#sortingList").val();

			var report = "report";
			var cust = 3336;
			var reg = 2;

			var mdy = startDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var startDates = year + "-" + month + "-" + day;

			var mdy = endDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var endDates = year + "-" + month + "-" + day;

			if (reportType == 'lcCodPreAlert') {
				var typeName = document.getElementById('typeList').options[document
						.getElementById('typeList').selectedIndex].text;
				var productName = document.getElementById('productTo').options[document
						.getElementById('productTo').selectedIndex].text;
				var sortingOrderName = document.getElementById('sortOrderList').options[document
						.getElementById('sortOrderList').selectedIndex].text;
				var sortingName = document.getElementById('sortingList').options[document
						.getElementById('sortingList').selectedIndex].text;
				// var regionName =
				// document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
				// var regionName=
				// document.getElementById("regionList").select();
				var regionName = $("#regionList option:selected").map(
						function() {
							return this.text;
						}).get().join(", ");

				var url = '/udaan-report/pages/reportviewer/lcCodReportPreAlertViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers="
						+ customerList
						+ "&TypeName="
						+ typeName
						+ "&ProductName="
						+ productName
						+ "&SortingOrderName="
						+ sortingOrderName
						+ "&RegionNames="
						+ regionName
						+ "&SortingName="
						+ sortingName;

				window.open(url, "_blank");
			}

		}
	}

	// ajaxCallWithoutForm(url, customerCallBack);
}

function printLcCodReportPartyWise() {

	var reportType = $("#reportName").val();
	var customerList = "";
	if (checkMandatoryFieldForLcCodPartyWise()) {
		if (checkDateForLcCod()) {
			var prodSeries = $("#productTo").val();
			var region = $("#regionList").val();
			var type = $("#typeList").val();
			var summaryOption = null;

			var sortOrder = $("#sortOrderList").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var sortList = $("#sortingList").val();

			customerList = $("#partyNameList").val();

			var report = "report";
			var cust = 3336;
			var reg = 2;

			var mdy = startDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var startDates = year + "-" + month + "-" + day;

			var mdy = endDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var endDates = year + "-" + month + "-" + day;

			if (reportType == 'lcCodPartyWise') {
				var typeName = document.getElementById('typeList').options[document
						.getElementById('typeList').selectedIndex].text;
				var productName = document.getElementById('productTo').options[document
						.getElementById('productTo').selectedIndex].text;
				var sortingOrderName = document.getElementById('sortOrderList').options[document
						.getElementById('sortOrderList').selectedIndex].text;
				var sortingName = document.getElementById('sortingList').options[document
						.getElementById('sortingList').selectedIndex].text;
				// var regionName =
				// document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
				// var regionName=
				// document.getElementById("regionList").select();
				var regionName = $("#regionList option:selected").map(
						function() {
							return this.text;
						}).get().join(", ");
				// var
				// partyName=document.getElementById('partyNameList').options[document.getElementById('partyNameList').selectedIndex].text;
				var partyName = $("#partyNameList option:selected").map(
						function() {
							if (this.value != "") {
								return this.text;
							}
						}).get().join(", ");
				if(customerList!= 'All'){
				customerList = $("#partyNameList option:selected").map(
						function() {
							if (this.value != "") {
								return this.value;
							}
						}).get().join(", ");
				}
				var url = '/udaan-report/pages/reportviewer/lcCodReportOutPartyViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption
						+ "&Sorting="
						+ sortList.trim()
						+ "&Customers="
						+ customerList
						+ "&TypeName="
						+ typeName
						+ "&ProductName="
						+ productName
						+ "&SortingOrderName="
						+ sortingOrderName
						+ "&SortingName="
						+ sortingName
						+ "&RegionNames="
						+ regionName + "&PartyName=" + partyName;
				window.open(url, "_blank");
			}
		}
	}

	// ajaxCallWithoutForm(url, customerCallBack);
}

function printLcCodReportOutSummary() {
	var reportType = $("#reportName").val();
	var customerList = "";
	if (checkMandatoryFieldForLcCodOutSummary()) {
		if (checkDateForLcCod()) {
			var prodSeries = $("#productTo").val();
			var region = $("#regionList").val();
			var type = $("#typeList").val();
			var summaryOption = $("#summaryOptionList").val();

			var sortOrder = $("#sortOrderList").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var sortList = null;
			customerList = $("#partyNameList").val();
			var report = "report";
			var cust = 3336;
			var reg = 2;

			var mdy = startDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var startDates = year + "-" + month + "-" + day;

			var mdy = endDate.split('/');
			var year = mdy[2];
			var month = mdy[1];
			var day = mdy[0];

			var endDates = year + "-" + month + "-" + day;

			if (reportType == 'lcCodOutSummary') {
				var typeName = document.getElementById('typeList').options[document
						.getElementById('typeList').selectedIndex].text;
				var productName = document.getElementById('productTo').options[document
						.getElementById('productTo').selectedIndex].text;
				var sortingOrderName = document.getElementById('sortOrderList').options[document
						.getElementById('sortOrderList').selectedIndex].text;
				var summaryOptionName = document
						.getElementById('summaryOptionList').options[document
						.getElementById('summaryOptionList').selectedIndex].text;
				// var regionName =
				// document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
				// var regionName=
				// document.getElementById("regionList").select();
				var regionName = $("#regionList option:selected").map(
						function() {
							return this.text;
						}).get().join(", ");
				var partyName = document.getElementById('partyNameList').options[document
						.getElementById('partyNameList').selectedIndex].text;

				var url = '/udaan-report/pages/reportviewer/lcCodReportOutSummaryViewer.jsp?'
						+ "&Report="
						+ report
						+ "&SortingOrder="
						+ sortOrder.trim()
						+ "&FromDate="
						+ startDates
						+ "&ToDate="
						+ endDates
						+ "&Products="
						+ prodSeries.trim()
						+ "&Region="
						+ region
						+ "&Type="
						+ type.trim()
						+ "&SummaryOption="
						+ summaryOption.trim()
						+ "&Sorting="
						+ sortList
						+ "&Customers="
						+ customerList
						+ "&TypeName="
						+ typeName
						+ "&ProductName="
						+ productName
						+ "&SummaryOptionName="
						+ summaryOptionName
						+ "&SortingOrderName="
						+ sortingOrderName
						+ "&RegionNames="
						+ regionName
						+ "&PartyName="
						+ partyName;

				window.open(url, "_blank");
			}

		}
	}

	// ajaxCallWithoutForm(url, customerCallBack);
}

function getCustomerByRegionAndProduct() {
	showProcessing();
	var productSelection = $("#productTo option:selected").text().toUpperCase();
	// document.getElementById('productTo').options[document.getElementById('productTo').selectedIndex].text;
	var prodSeries = "";
	if (productSelection == "ALL") {
		prodSeries = productSelection;
	} else {
		prodSeries = $("#productTo").val();
	}

	if (document.getElementById("partyNameList").style.visibility == 'hidden') {
		hideProcessing();
	} else if (!isNull(prodSeries)) {
		var region = $("#regionList").val();
		if (!isNull(region)) {

			var url = './lcCodReport.do?submitName=getCustomerByRegionAndProduct&RegionId='
					+ region + "&ProdSeries=" + prodSeries;
			/*
			 * document.lcCodReportForm.action = url;
			 * document.lcCodReportForm.submit();
			 */

			ajaxCallWithoutForm(url, customerCallBack);
		} else {
			hideProcessing();
			alert('Please Select Region');
			$("#regionList").focus();
		}

	} else {
		hideProcessing();
		/* cancelLcCodReport(); */
		alert('Please Select Product');
		$("#productTo").focus();
		document.getElementById("regionList").value = "";
	}

}

function checkMandatoryFieldForLcCod() {
	var prodSeries = $("#productTo").val();
	var region = $("#regionList").val();
	var type = $("#typeList").val();
	var summaryOption = $("#summaryOptionList").val();

	var sortOrder = $("#sortOrderList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var sortList = $("#sortingList").val();
	var customerList = $("#partyNameList").val();

	if (isNull(type)) {
		alert('Please Select Mandatory Field Type');
		$("#typeList").focus();
		return false;
	}

	if (isNull(prodSeries)) {
		alert('Please Select Mandatory Field Product');
		$("#productTo").focus();
		return false;
	}

	if (isNull(summaryOption)) {
		alert('Please Select Mandatory Field Summary Option');
		$("#summaryOptionList").focus();
		return false;
	}
	if (isNull(sortOrder)) {
		alert('Please Select Mandatory Field Sorting Order');
		$("#sortOrderList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	if ($('#sortingList').is(':disabled') == false) {
		if (isNull(sortList)) {
			alert('Please Select Mandatory Field Sorting');
			$("#sortingList").focus();
			return false;
		}
	}

	if (isNull(region)) {
		alert('Please Select Mandatory Field Region');
		$("#regionList").focus();
		return false;
	}

	/*
	 * if($('#partyNameList').is(':visible')){ if(isNull(customerList)){
	 * alert('Please Select Mandatory Field Party Name'); $("#partyNameList"
	 * ).focus(); return false; } }
	 */

	if (document.getElementById("partyNameList").style.visibility == 'hidden') {
		// alert('hidden');
	} else {
		if (isNull(customerList)) {
			alert('Please Select Mandatory Field Party Name');
			$("#partyNameList").focus();
			return false;
		}
	}

	/*
	 * if(document.getElementById("partyNameList").style.visibility ==
	 * "visible"){ if(isNull(customerList)){ alert('Please Select Mandatory
	 * Field Party Name'); $("#partyNameList" ).focus(); return false; } }
	 */
	return true;
}

function checkMandatoryFieldForLcCodInSummary() {
	var prodSeries = $("#productTo").val();
	var region = $("#regionList").val();
	var type = $("#typeList").val();
	var summaryOption = $("#summaryOptionList").val();

	var sortOrder = $("#sortOrderList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	/* var sortList = $("#sortingList").val(); */
	// var customerList = $("#partyNameList").val();
	if (isNull(type)) {
		alert('Please Select Mandatory Field Type');
		$("#typeList").focus();
		return false;
	}

	if (isNull(prodSeries)) {
		alert('Please Select Mandatory Field Product');
		$("#productTo").focus();
		return false;
	}

	if (isNull(summaryOption)) {
		alert('Please Select Mandatory Field Summary Option');
		$("#summaryOptionList").focus();
		return false;
	}
	if (isNull(sortOrder)) {
		alert('Please Select Mandatory Field Sorting Order');
		$("#sortOrderList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	/*
	 * if($('#sortingList').is(':disabled') == false){ if(isNull(sortList)){
	 * alert('Please Select Mandatory Field Sorting'); $("#sortingList"
	 * ).focus(); return false; } }
	 */

	var region = selectedRegions;

	if (document.getElementById("regionList").disabled) {
		region = document.getElementById("regionList").value;
	} else {
		region = selectedRegions;
	}
	
	
	/*if (isNull(region)) {
		alert('Please Select Mandatory Field Region');
		$("#regionList").focus();
		return false;
	}*/
	

	/*
	 * if($('#partyNameList').is(':visible')){ if(isNull(customerList)){
	 * alert('Please Select Mandatory Field Party Name'); $("#partyNameList"
	 * ).focus(); return false; } }
	 */

	/*
	 * if ( document.getElementById("partyNameList").style.visibility=='hidden') {
	 * //alert('hidden'); } else{ if(isNull(customerList)){ alert('Please Select
	 * Mandatory Field Party Name'); $("#partyNameList" ).focus(); return false; } }
	 */

	/*
	 * if(document.getElementById("partyNameList").style.visibility ==
	 * "visible"){ if(isNull(customerList)){ alert('Please Select Mandatory
	 * Field Party Name'); $("#partyNameList" ).focus(); return false; } }
	 */
	return true;
}

function checkMandatoryFieldForLcCodInPreAlert() {
	var prodSeries = $("#productTo").val();
	var region = $("#regionList").val();
	var type = $("#typeList").val();
	// var summaryOption = $("#summaryOptionList").val();

	var sortOrder = $("#sortOrderList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var sortList = $("#sortingList").val();
	// var customerList = $("#partyNameList").val();

	if (isNull(type)) {
		alert('Please Select Mandatory Field Type');
		$("#typeList").focus();
		return false;
	}

	if (isNull(prodSeries)) {
		alert('Please Select Mandatory Field Product');
		$("#productTo").focus();
		return false;
	}

	/*
	 * if(isNull(summaryOption)){ alert('Please Select Mandatory Field Summary
	 * Option'); $("#summaryOptionList" ).focus(); return false; }
	 */
	if (isNull(sortOrder)) {
		alert('Please Select Mandatory Field Sorting Order');
		$("#sortOrderList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	if (isNull(sortList)) {
		alert('Please Select Mandatory Field Sorting');
		$("#sortingList").focus();
		return false;
	}

	if (isNull(region)) {
		alert('Please Select Mandatory Field Region');
		$("#regionList").focus();
		return false;
	}

	return true;
}

function checkMandatoryFieldForLcCodPartyWise() {
	var prodSeries = $("#productTo").val();
	var region = $("#regionList").val();
	var type = $("#typeList").val();
	// var summaryOption = $("#summaryOptionList").val();

	var sortOrder = $("#sortOrderList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var sortList = $("#sortingList").val();
	var customerList = $("#partyNameList").val();

	if (isNull(type)) {
		alert('Please Select Mandatory Field Type');
		$("#typeList").focus();
		return false;
	}

	if (isNull(prodSeries)) {
		alert('Please Select Mandatory Field Product');
		$("#productTo").focus();
		return false;
	}

	/*
	 * if(isNull(summaryOption)){ alert('Please Select Mandatory Field Summary
	 * Option'); $("#summaryOptionList" ).focus(); return false; }
	 */
	if (isNull(sortOrder)) {
		alert('Please Select Mandatory Field Sorting Order');
		$("#sortOrderList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	if (isNull(sortList)) {
		alert('Please Select Mandatory Field Sorting');
		$("#sortingList").focus();
		return false;
	}

	if (isNull(region)) {
		alert('Please Select Mandatory Field Region');
		$("#regionList").focus();
		return false;
	}

	if (isNull(customerList)) {
		alert('Please Select Mandatory Field Party Name');
		$("#partyNameList").focus();
		return false;
	}

	return true;
}

function checkMandatoryFieldForLcCodOutSummary() {
	var prodSeries = $("#productTo").val();
	var region = $("#regionList").val();
	var type = $("#typeList").val();
	var summaryOption = $("#summaryOptionList").val();

	var sortOrder = $("#sortOrderList").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	// var sortList = $("#sortingList").val();
	var customerList = $("#partyNameList").val();

	if (isNull(type)) {
		alert('Please Select Mandatory Field Type');
		$("#typeList").focus();
		return false;
	}

	if (isNull(prodSeries)) {
		alert('Please Select Mandatory Field Product');
		$("#productTo").focus();
		return false;
	}

	if (isNull(summaryOption)) {
		alert('Please Select Mandatory Field Summary Option');
		$("#summaryOptionList").focus();
		return false;
	}
	if (isNull(sortOrder)) {
		alert('Please Select Mandatory Field Sorting Order');
		$("#sortOrderList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	if (isNull(region)) {
		alert('Please Select Mandatory Field Region');
		$("#regionList").focus();
		return false;
	}

	if (isNull(customerList)) {
		alert('Please Select Mandatory Field Party Name');
		$("#partyNameList").focus();
		return false;
	}

	return true;
}

function customerCallBack(data) {
	hideProcessing();
	$("#partyNameList").empty();
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			$("#partyNameList").empty();
			alert(error);
			return;
		}

		var content = document.getElementById('partyNameList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("ALL"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.customerId;
			option.appendChild(document.createTextNode(this.customerName));
			option.appendChild(document.createTextNode("-" + this.custCode));
			content.appendChild(option);
		});
	}
}


function checkDateForLcCod() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1] - 1, arrEndDate[0]);
	var currentdate = new Date();
	if (date1 > currentdate) {
		alert("Start Date Can't be Greater that Current date");

		$("#startDate").val("");

		$("#startDate").focus();
		return false;
	}
	if (date1 == "" || date1 > date2) {

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate").val("");

		$("#startDate").focus();

		return false;

	} else {
		var currentdate = new Date();
		if (date2 > currentdate) {
			alert("End Date Can't be Greater that Current date");
			$("#endDate").val("");
			$("#endDate").focus();
			return false;
		}
		return true;
	}
}


function checkDateForLcCod1() {
	var startDate = $("#startDate").val(); // 
	var endDate = $("#endDate").val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1] - 1, arrEndDate[0]);
	var currentdate = document.getElementById('todayDate').value;
	
	//futrure data validation
	
	
	var noOfDays=calculateDateDiff(startDate, endDate);
	if (noOfDays > 7) {
		alert("User Restriction:Date Range should not be greated than 7 days:");

		$("#startDate").val("");

		$("#startDate").focus();
		return false;
	}
	//alert("noOfDays" +noOfDays )
	
	if (date1 > currentdate) {
		alert("Start Date Can't be Greater than Current date");

		$("#startDate").val("");

		$("#startDate").focus();
		return false;
	}
	if (date1 == "" || date1 > date2) {

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate").val("");

		$("#startDate").focus();

		return false;

	} else {
		var currentdate = new Date();
		if (date2 > currentdate) {
			alert("End Date Can't be Greater that Current date");
			$("#endDate").val("");
			$("#endDate").focus();
			return false;
		}
		return true;
	}
}

/*
 * function loadDefaultForLcCodOne(){ var
 * selectObj=getDomElementById("sortingList"); var valueToSet="Disabled"; for
 * (var i = 0; i < selectObj.options.length; i++) { if
 * (selectObj.options[i].value.trim() == valueToSet) {
 * selectObj.options[i].selected = true; $('#sortingList').attr('disabled',
 * 'disabled'); //return; } }
 * 
 * document.getElementById('partyNameList').style.display = 'none';
 * document.getElementById("partyNameList").style.visibility = "hidden";
 * document.getElementById("partyNameLabel").style.visibility = "hidden";
 * 
 * $("#partyNameList").attr('type') == 'hidden';
 * 
 * var x = document.getElementById("partyNameList"); x.setAttribute("type",
 * "hidden"); }
 */

function cancelLcCodReport() {
	/*
	 * var url = './lcCodReport.do?submitName=preparePage';
	 * document.lcCodReportForm.action=url; document.lcCodReportForm.submit();
	 */
	// window.opener.location.reload();
	location.reload(true);
}

function loadDefaultForLcCodTwo() {
	var selectObj = getDomElementById("sortingList");
	var valueToSet = "Disabled";
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.trim() == valueToSet) {
			selectObj.remove(i);

		}
	}

	var selectObj = getDomElementById("summaryOptionList");
	var valueToSet = "Disabled";
	addDisabledForLcCod("summaryOptionList", "Disabled", "Disabled");
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.trim() == valueToSet) {
			selectObj.options[i].selected = true;
			$('#summaryOptionList').attr('disabled', 'disabled');
			// return;
		}
	}

	document.getElementById("partyNameList").style.visibility = "hidden";
	document.getElementById("partyNameLabel").style.visibility = "hidden";

}

function addDisabledForLcCod(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

function loadDefaultForLcCodThree() {
	var selectObj = getDomElementById("summaryOptionList");
	var valueToSet = "Disabled";
	addDisabledForLcCod("summaryOptionList", "Disabled", "Disabled");
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.trim() == valueToSet) {
			selectObj.options[i].selected = true;
			$('#summaryOptionList').attr('disabled', 'disabled');
			// return;
		}
	}

	var selectObj = getDomElementById("sortingList");
	var valueToSet = "Disabled";
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.trim() == valueToSet) {
			selectObj.remove(i);

		}
	}
}

function showDsrReport() {

	var offs = "";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var typeList = $("#type").val();
	if (checkDailySalesDsr()) {
		if (checkDateForLcCod1()) {
			var branch = document.getElementById('branchList').value;
			//var branchList = document.getElementById('branchList');
			/*if (branch == 'All') {
				if (!isNull(branchList)) {
					for ( var i = 0; i < branchList.length; i++) {
						if (!isNull(branchList[i].value)) {
							if (branchList[i].value != 'All'
									&& branchList[i].value != 'Select') {
								if (isNull(offs)) {
									offs += branchList[i].value;
								} else {
									offs += "," + branchList[i].value;
								}
							}
						}
					}
				}
			} else {
				offs = "";
				offs = document.getElementById('branchList').value;
			}
			 */
			/*if (isNull(offs)) {
				return;
			}*/
			regionId = document.getElementById('regionList').value;
			stationId = document.getElementById('stationList').value;
			var originRegionName = document.getElementById('regionList').options[document
					.getElementById('regionList').selectedIndex].text;
			var originStationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
			var originbranchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
			var bookTypeName = document.getElementById('type').options[document
					.getElementById('type').selectedIndex].text;
			var url = '/udaan-report/pages/reportviewer/salesDsrReportViewer.jsp?'
					+ "&branchId=" + branch + "&regionId=" + regionId
					+ "&stationId=" + stationId + "&startDate=" + startDate
					+ "&endDate=" + endDate + "&bookType=" + typeList.trim()
					+ "&originRegion=" + originRegionName + "&originStation="
					+ originStationName + "&originBranch=" + originbranchName
					+ "&bookTypeName=" + bookTypeName;

			window.open(url, "_blank");

		}

	}
}

function checkDailySalesDsr() {
	var branch = document.getElementById('branchList').value;
	var regioncheck = document.getElementById('regionList').value;
	var stationCheck = document.getElementById('stationList').value;
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var type = $("#type").val();

	if (regioncheck == 'Select' || isNull(regioncheck)) {
		alert('Please Select Region value ');
		$("#regionList").focus();
		return false;
	}

	if (stationCheck == 'Select' || isNull(stationCheck)) {
		alert('Please Select Station value ');
		$("#stationList").focus();
		return false;
	}

	if (branch == 'Select' || isNull(branch)) {
		alert('Please Select branch value ');
		$("#branchList").focus();
		return false;
	}

	if (isNull(startDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#startDate").focus();
		return false;
	}

	if (isNull(endDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#endDate").focus();
		return false;
	}

	if (type == 'Select') {
		alert('Please Select Mandatory Field Type');
		$("#type").focus();
		return false;
	}

	return true;
}

function cancelDsr() {
	$("#startDate").val("");
	$("#endDate").val("");
	location.reload(true);
	/*
	 * var url = './salesReport.do?submitName=prepareDsrPage';
	 * document.consignmentReportForm.action=url;
	 * document.consignmentReportForm.submit();
	 */

}

function clearProduct() {
	// Empty product drop down
	//clearDropDown("product");
	$("#product").val("");
	//createEmptyDropDown("product");
	// Empty slab drop down
	clearDropDown("slab");
	createEmptyDropDown("slab");
	// Empty client drop down
	clearDropDown("client");
	createEmptyDropDown("client");
}

function loadDefaultForLcCodFour() {
	var selectObj = getDomElementById("sortingList");
	var valueToSet = "Disabled";
	for ( var i = 0; i < selectObj.options.length; i++) {
		if (selectObj.options[i].value.trim() == valueToSet) {
			selectObj.options[i].selected = true;
			$('#sortingList').attr('disabled', 'disabled');
			// return;
		}
	}
}

function checkMonthSalesDsrReport() {
	var fromDate = $("#fromDate").val();
	var arrStartDate = fromDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var today = new Date();
	if (date1 > today) {
		alert('You cannot select future month');
		$("#fromDate").val("");
		return false;
	}

	return true;
}

function getMultiStationList(param1) {

	getDomElementById('destStationList').options.length = 1;
	selectedRegions = 0;
	selectedStations = 0;
	var selected = GetSelected(param1);
	selectedRegions = selected;
	if (selected != '' && selected != 'Select') {
		var url = "";
		if (selected == 'All') {
			url = './consignmentBookingDetailsReport.do?submitName=getAllStations';
		} else {
			url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
					+ selected;
		}
		ajaxCallWithoutForm(url, populateDestinationStationList);
	}

}


function getMultiStationList1(param1) {

	getDomElementById('stationList').options.length = 1;
	selectedRegions = 0;
	selectedStations = 0;
	var selected = GetSelected(param1);
	selectedRegions = selected;
	if (selected != '' && selected != 'Select') {
		var url = "";
		if (selected == 'All') {
			url = './consignmentBookingDetailsReport.do?submitName=getAllStations';
		} else {
			url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
					+ selected;
		}
		ajaxCallWithoutForm(url, populateDestinationStationList1);
	}

}

function GetSelected(selectTag) {

	var selIndexes = "";

	for ( var i = 0; i < selectTag.options.length; i++) {
		var optionTag = selectTag.options[i];
		if (optionTag.selected) {
			if (selIndexes.length > 0)
				selIndexes += ",";
			selIndexes += optionTag.value;
		}
	}
	return selIndexes;

}

function getBranches(id, param2) {

	selectedStations = 0;
	var selected = GetSelected(param2);
	selectedStations = selected;

}

function getOrgStationsList(param1) {

	getDomElementById("originStation").options.length = 1;
	getDomElementById("branchList").options.length = 1;

	selectedRegions = 0;
	selectedStations = 0;
	selectedBranches = 0;

	var selected = GetSelected(param1);
	selectedRegions = selected;
	if (selected != '' && selected != 'Select') {
		var url = './yieldReport.do?submitName=getStations&region=' + selected;
		ajaxCallWithoutForm(url, getOrgStationList1);
	}

}

function getOrgStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('originStation');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
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
function getOrgStationList1(data) {
	if (!isNull(data)) {
		var content = document.getElementById('originStation');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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

function getOrgBranchList(id, param2) {
	
	
	getDomElementById("branchList").options.length = 1;
	selectedStations = 0;
	selectedBranches = 0;
	var selected = GetSelected(param2);
	selectedStations = selected;
	if (selectedStations == 'All') {
		var ddlArray = new Array();
		var ddl = document.getElementById(id);
		for ( var i = 2; i < ddl.options.length; i++) {
			ddlArray[i - 2] = ddl.options[i].value;
		}
		url = './yieldReport.do?submitName=getBranchList&cityID='
				+ ddlArray;
	}else if (selected != '' && selected != 'Select') {
	
		var url = './yieldReport.do?submitName=getBranchList&cityID='
				+ selected;
	}
	
		ajaxCallWithoutForm(url, populateOrgBranchList1);


}

function populateOrgBranchList(data) {

	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}

}

function populateOrgBranchList1(data) {

	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}

}

function GetSelectedBranches(param1) {
	var selected = GetSelected(param1);
	selectedBranches = selected;
}

function checkMultiSelectDropdown(id) {

	var val = $("#" + id).val();
	if (val.indexOf('All') != -1 || val.indexOf('all') != -1) {
		$('#' + id + ' option').prop('selected', true);
		$('#' + id + ' option:first').prop('selected', false);

	}
}

function clearMultiSelect(id1, id2) {
	clearFields(id1);
	clearFields(id2);
}

function clearFields(id) {
	var content = document.getElementById(id);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "Select";
	defOption.appendChild(document.createTextNode("Select"));
	content.appendChild(defOption);
}
/**
 * Get customer by officeid and rate product category
 */
function getCustomerByBranchAndRateProductCategory() {
	var rateProduct = $("#product").val();
	var officeId = $("#branchList").val();
	var branch = new Array();
	if (isNull(officeId)) {
		alert("Please select Branch office");
	} else if (isNull(rateProduct)) {
		alert("Please select product");
	} else {
		if (officeId.toLocaleLowerCase() == 'all') {
			var branchList = document.getElementById("branchList");
			for ( var i = 2; i < branchList.options.length; i++) {
				branch[i - 2] = branchList.options[i].value;
			}
		} else {
			branch = officeId;
		}
		branch = branch.toString();
		// showProcessing();
		var url = './custSlabWiseRevenue.do?submitName=getCustomerByBranchAndRateProductCategory&branch='
				+ branch + '&rateProduct=' + rateProduct;
		ajaxCallWithoutFormWithAsync(url, populateClientList);
	}
}

/**
 * @param data
 */
function populateClientList(data) {
	// showProcessing();
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('client');
		content.innerHTML = "";
		/*
		 * var defOption = document.createElement("option"); defOption.value =
		 * ""; defOption.appendChild(document.createTextNode("Select"));
		 * content.appendChild(defOption);
		 */
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.businessName != null
					&& $.trim(this.businessName).length > 0) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName
						+ "-" + this.customerCode));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
	}
	hideProcessing();

}

function ajaxCallWithoutFormWithAsync(pageurl, ajaxResponse) {
	showProcessing();
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		// async: false,
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		}
	});
	// jQuery.unblockUI();
}

function getStationsListForAllRegions(id, clearFields) {
	var region = $("#" + id).val();
	clearAllFields();
	var url = null;
	if (region.toLowerCase() == 'all') {
		var ddlArray = new Array();
		var ddl = document.getElementById(id);
		for ( var i = 2; i < ddl.options.length; i++) {
			ddlArray[i - 2] = ddl.options[i].value;
		}
		url = './yieldReport.do?submitName=getStations&region=' + ddlArray;
	} else {
		url = './baSalesReport.do?submitName=getStations&region=' + region;
	}
	ajaxCallWithoutForm(url, populateStationList);

}

function getProduct(selectTag) {

	var selIndexes = "";

	for ( var i = 0; i < selectTag.options.length; i++) {
		var optionTag = selectTag.options[i];
		if (optionTag.selected) {
			if (selIndexes.length > 0)
				selIndexes += ",";
			selIndexes += optionTag.value;
		}
	}
	alert(selIndexes);
	return selIndexes;

}

function getCustomerByStationAndProduct() {
	var product = $("#product").val();
	var stationId = $("#stationList").val();
	var productIds = new Array();
	if (isNull(product)) {
		alert("Please select product");
	} else if (isNull(stationId)) {
		// alert("Please select Origin Station");
	} else {
		if (product[0].toLowerCase() == 'all') {
			var productList = document.getElementById("product");
			for ( var i = 1; i < productList.options.length; i++) {
				productIds[i - 1] = productList.options[i].value;
			}
		} else {
			productIds = product;
		}
		product = productIds.toString();
		// showProcessing();
		var url = './custSlabWiseRevenue.do?submitName=getCustomerByStationAndProduct&product='
				+ productIds + '&station=' + stationId;
		ajaxCallWithoutFormWithAsync(url, populateClientList);
	}

}

function checkMandatoryCustWiseQuality() {
	var branch = document.getElementById('branchList').value;
	var regioncheck = document.getElementById('regionList').value;
	var stationCheck = document.getElementById('stationList').value;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var product = $("#product").val();
	/*
	 * var startDate = $("#startDate").val(); var endDate = $("#endDate").val();
	 * var type = $("#type").val();
	 */

	if (regioncheck == 'Select' || isNull(regioncheck)) {
		alert('Please Select Region value ');
		$("#regionList").focus();
		return false;
	}

	if (stationCheck == 'Select' || isNull(stationCheck)) {
		alert('Please Select Station value ');
		$("#stationList").focus();
		return false;
	}

	if (branch == 'Select' || isNull(branch)) {
		alert('Please Select branch value ');
		$("#branchList").focus();
		return false;
	}

	if (isNull(fromDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#fromDate").focus();
		return false;
	}

	if (isNull(toDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#toDate").focus();
		return false;
	}

	if (isNull(product) || product == 'Select') {
		alert('Please Select Mandatory Field Product');
		$("#product").focus();
		return false;
	}

	return true;

}

function checkCustSlabRevenueMandatory() {
	var branch = document.getElementById('branchList').value;
	var regioncheck = document.getElementById('regionList').value;
	var stationCheck = document.getElementById('stationList').value;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var product = $("#product").val();
	var slab = $("#slab").val();
	/*
	 * var startDate = $("#startDate").val(); var endDate = $("#endDate").val();
	 * var type = $("#type").val();
	 */

	if (regioncheck == 'Select' || isNull(regioncheck)) {
		alert('Please Select Region value ');
		$("#regionList").focus();
		return false;
	}

	if (stationCheck == 'Select' || isNull(stationCheck)) {
		alert('Please Select Station value ');
		$("#stationList").focus();
		return false;
	}

	if (branch == 'Select' || isNull(branch)) {
		alert('Please Select branch value ');
		$("#branchList").focus();
		return false;
	}

	if (isNull(fromDate)) {
		alert('Please Select Mandatory Field From Date');
		$("#fromDate").focus();
		return false;
	}

	if (isNull(toDate)) {
		alert('Please Select Mandatory Field To Date');
		$("#toDate").focus();
		return false;
	}

	if (isNull(product) || product == 'Select') {
		alert('Please Select Mandatory Field Product');
		$("#product").focus();
		return false;
	}

	if (isNull(slab) || slab == 'Select') {
		alert('Please Select Mandatory Field Slab');
		$("#slab").focus();
		return false;
	}

	return true;

}

function clearScreenCustSlabRev() {
	window.location.reload();
	$("#slab").empty();
	$("#client").empty();
}

function clearScreenCustSlabproduct() {
	window.location.reload();
	$("#product").empty();
	$("#slab").empty();
	$("#client").empty();
}

function getConsignmentBookingClientList1(id, clearFields) {

	getDomElementById("client").options.length = 1;
	var branch = readBranch();
	var cityId = readStation();
	if ($("#branchList").val().toUpperCase() != "SELECT") {
		var url = './consignmentBookingDetailsReport.do?submitName=getCustomersByContractBranches&officeIds='
				+ branch + '&cityId=' + cityId;
		ajaxCallWithoutForm(url, populateClientList);
	}

}

function clearHeldupScreen() {
	var url = "./heldUpReport.do?submitName=getHeldUpReport";
	submitTransfer(url, 'Clear');
}

function clearPaymentAdviceScreen() {
	var url = "./payment-adviceReport.do?submitName=getPaymentAdviceReport";
	submitTransfer(url, 'Clear');
}

function clearProductStatusScreen() {
	var url = "./productStatusReport.do?submitName=getProductStatusReport";
	submitTransfer(url, 'Clear');
}

function clearCustomerQualityScreen() {
	var url = "./customerWiseQualityAnalysisReport.do?submitName=getCustomerWiseQualityReport";
	submitTransfer(url, 'Clear');
}

function submitTransfer(url, action) {
	if (confirm("Do you want to " + action + " the details?")) {
		document.consignmentReportForm.action = url;
		document.consignmentReportForm.method = "post";
		document.consignmentReportForm.submit();
	}
}

function clearBRRSummaryScreen() {

	if (confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();
		getDomElementById("stationList").options.length = 1;
		var seriesList = getDomElementById("series");
		for ( var i = 0; i < seriesList.length; i++) {
			seriesList[i].disabled = false;
		}

	}
	getDomElementById("branchList").options.length = 1;
}

function clearDestinationFields() {
	selectedRegions = 0;
	selectedStations = 0;

}

function getDestinationStationsList(id, clearFields) {
	clearHitRatioOriginAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './baSalesReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateHitRatioDestinationStationList);
	}
}


function getDestinationStationsListForBrr(id, clearFields) {
	clearHitRatioOriginAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './baSalesReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateHitRatioDestinationStationListForBrr);
	}
}



function populateHitRatioDestinationStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('destStationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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

function populateHitRatioDestinationStationListForBrr(data) {
	if (!isNull(data)) {
		var content = document.getElementById('destStationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
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



function clearHitRatioOriginAllFields() {
	if (getDomElementById("destStationList")) {
		getDomElementById("destStationList").options.length = 1;
	}
}

function showHitRatioOriginwiseReport() {
	var msg = "";
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var series = $("#series").val();
	var type = $("#type").val();
	var destRegion = $("#destRegionList").val();
	var destStation = $("#destStationList").val();

	var originRegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var destRegionName = document.getElementById('destRegionList').options[document
			.getElementById('destRegionList').selectedIndex].text;

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (destRegion == null || 'select' == destRegion
					.toLocaleLowerCase())
			|| (destStation == null || 'select' == destStation
					.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (series == null || 'select' == series.toLocaleLowerCase())
			|| (type == null || 'select' == type.toLocaleLowerCase())

	) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}

		if (destRegion == null || 'select' == destRegion.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}
		if (destStation == null || 'select' == destStation.toLocaleLowerCase()) {
			msg = msg + "Select Destination Station \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (series == null || $.trim(series).length <= 0
				|| 'select' == series.toLocaleLowerCase()) {
			msg = msg + 'Please Select Series\n';

		}

		if (type == null || $.trim(type).length <= 0
				|| 'select' == type.toLocaleLowerCase()) {
			msg = msg + 'Please Select Type\n';

		}

		alert(msg);
		return;
	}

	else if (checkDate('fromDate', 'toDate')) {
		var stationId, stationName, destStationId, destStationName, delvCategoryCode = "", delvCategoryName = "", seriesData = "", cntypeCode, cntypeName, loadNum, loadDesc;
		if (station == 'All') {
			stationId = 0;
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
		} else {
			stationId = $("#stationList").val();
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
		}

		if (destStation == 'All') {
			destStationId = 0;
			destStationName = document.getElementById('destStationList').options[document
					.getElementById('destStationList').selectedIndex].text;
		} else {
			destStationId = $("#destStationList").val();
			destStationName = document.getElementById('destStationList').options[document
					.getElementById('destStationList').selectedIndex].text;
		}

		var seriesdatas = document.getElementById("series");

		if ($("#series").val().toUpperCase() == "ALL") {
			for ( var i = 2; i < seriesdatas.length; i++) {
				seriesData = seriesData + seriesdatas.options[i].value;

				if (i != (seriesdatas.length - 1)) {
					seriesData = seriesData + ",";
				}
			}
		} else {
			seriesData = $("#series").val();
		}

		if (type == 'ALL') {
			cntypeCode = type;
			cntypeName = type;
		} else {
			cntypeCode = type;
			cntypeName = document.getElementById('type').options[document
					.getElementById('type').selectedIndex].text;
		}

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var arrStartDate = fromDate.split("/");
		var startDateFormat = arrStartDate[2] + '-' + arrStartDate[1] + '-'
				+ arrStartDate[0];
		var arrEndDate = toDate.split("/");
		var endDateFormat = arrEndDate[2] + '-' + arrEndDate[1] + '-'
				+ arrEndDate[0];

		var url = '/udaan-report/pages/reportviewer/hitRatioOriginwiseViewer.jsp?'
				+ "&OriginRegionId="
				+ region
				+ "&OriginRegionName="
				+ originRegionName
				+ "&DestinationRegionId="
				+ destRegion
				+ "&DestinationRegionName="
				+ destRegionName
				+ "&OriginStationId="
				+ stationId
				+ "&OriginStationName="
				+ stationName
				+ "&DestinationStationId="
				+ destStationId
				+ "&DestinationStationName="
				+ destStationName
				+ "&FromDate="
				+ startDateFormat
				+ "&ToDate="
				+ endDateFormat
				+ "&Series="
				+ seriesData
				+ "&ConsignmentTypeCode="
				+ cntypeCode
				+ "&ConsignmentTypeName=" + cntypeName;
		// alert(url);
		window.open(url, "_blank");
		//document.getElementById('rateRevisionAnalysisReportForm').submit();
	}

	function cancelHitRatio() {
		location.reload(true);
	}
}

function getHitRatioOriginWiseDestinationStation(id) {
	// clearAllFields();
	clearDestStationForHitRatioOrigin();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if (isNaN(region)) {
			return;
		}
		var url = './hitRatioOriginwise.do?submitName=getStations&region='
				+ region;

		ajaxCallWithoutForm(url, populateHitRatioDestinationStationList);
	}
}

/*
 * ################ RATE REVISION ANALYSIS REPORT - START
 * ##########################
 */

function checkValidMonth() {
	var startMonth = $("#month1").val();
	var endMonth = $("#month2").val();
	var arrStartMonth = startMonth.split("/");
	var month1 = arrStartMonth[0];
	var arrEndMonth = endMonth.split("/");
	var month2 = arrEndMonth[0];
	var currentDate = new Date();
	var currentMonth = currentDate.getMonth() + 1;
	if (month1 > currentMonth) {
		alert("Month 1 Can't be Greater that Current Month");

		$("#month1").val("");

		$("#month1").focus();
		return false;
	}
	if (month1 == "" || month1 > month2) {

		alert("Please ensure that Month 2 is greater than or equal to Month 1");

		$("#month1").val("");

		$("#month1").focus();

		return false;

	} else {
		if (month2 > currentMonth) {
			alert("Month 2 Can't be Greater that Current Month");
			$("#month2").val("");
			$("#month2").focus();
			return false;
		}
		return true;
	}
}

/**
 * 
 */

function checkMonthSalesRateRevisionReport(date) {
	var fromDate = date.value;
	var arrStartDate = fromDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var today = new Date();
	if (date1 > today) {
		alert('You cannot select future month');
		date.value = "";
		date.focus();
		return false;
	}

	return true;
}

function showRateRevisionAnalysis() {
	var msg = "";
	var offs = "";
	var month;
	var region = $("#region").val();
	var regionName = document.getElementById('region').options[document
			.getElementById('region').selectedIndex].text;
	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	var monthStart = document.getElementById('month1');
	var monthEnd = document.getElementById('month2');
	var month1 = $("#month1").val();
	var month1Name;
	var month2 = $("#month2").val();
	var month2Name;
	var sector = $("#sector").val();
	var sectorName = document.getElementById('sector').options[document
			.getElementById('sector').selectedIndex].text;
	var fuelPercent = $("#fuelPercent").val();
	var fuelPercentFrom;
	var fuelPercentTo;
	var customerList = GetSelected(document.getElementById("customerList"));
	//var custValues = $("customerList").val();
	if (customerList == 'All') {
		//customerList = All;
		//var custCount = customerList.split(",");
		//}

		/*var customer = document.getElementById("customerList").value;
		var options = document.getElementById("customerList").options;
		if (!isNull(customer)) {
			for ( var i = 0; i < options.length; i++) {
				if (!isNull(options[i].value)) {
					if (options[i].value != 'All'
							&& options[i].value != 'Select') {
						if (isNull(offs)) {
							offs += options[i].value;
						} else {
							offs += "," + options[i].value;
						}
					}
				}
			}
		}*/		
		//customerList = 0;
		offs = 0;

	}else {
		
		offs =customerList;
	}
	
	
	var custCount = customerList.split(",");
	var product = $("#product").val();
	var consignmentSeries = document.getElementById('product').options[document
			.getElementById('product').selectedIndex].text;

	var monthNames = [ "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" ];

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (sector == null || 'select' == sector.toLocaleLowerCase())
			|| (fuelPercent == null || 'select' == fuelPercent
					.toLocaleLowerCase())
			|| (isNull(customerList) || customerList.length == 0 || 'select' == customerList
					.toLocaleLowerCase())
			|| (product == null || 'select' == product.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Please Select Region \n";
		}

		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Please Select Station \n";
		}

		if (month1 == "" || 'select' == month1.toLocaleLowerCase()) {
			msg = msg + "Please Select Month 1 \n";
		}

		if (month2 == "" || 'select' == month2.toLocaleLowerCase()) {
			msg = msg + "Please Select Month 2 \n";
		}

		if (sector == "" || 'select' == sector.toLocaleLowerCase()) {
			msg = msg + "Please Select Sector \n";
		}

		if (fuelPercent == "" || 'select' == fuelPercent.toLocaleLowerCase()) {
			msg = msg + "Please Select Fuel % \n";
		}

		if (customerList == "" || 'select' == customerList.toLocaleLowerCase()) {
			msg = msg + "Please Select Customer \n";
		}

		if (product == "" || 'select' == product.toLocaleLowerCase()) {
			msg = msg + "Please Select Product \n";

		}

		alert(msg);
		return;
	} else {
		if (checkMonthSalesRateRevisionReport(monthStart)
				&& checkMonthSalesRateRevisionReport(monthEnd)) {
			if (custCount.length > 1) {
				if (customerList.indexOf("All") >= 0) {
					alert("You cannot select 'All' as well as customers");
					return;
				}
			}

			/*
			 * var status = checkValidMonth(); if(false == status) { return; }
			 */

			if (fuelPercent.indexOf("+") >= 0) {
				var fuelPercentPart = fuelPercent.split("+");
				fuelPercentFrom = fuelPercentPart[0];
				fuelPercentTo = 100;
			} else {
				var fuelPercentPart = fuelPercent.split("-");
				fuelPercentFrom = fuelPercentPart[0];
				fuelPercentTo = fuelPercentPart[1];
			}

			month = month1.split("/");
			month1Name = monthNames[parseInt(month[0]) - 1];

			month = month2.split("/");
			month2Name = monthNames[parseInt(month[0]) - 1];

			// if (product == 'Select') {
			// product = '';
			// }
			//
			// if (consignmentSeries == 'Select') {
			// consignmentSeries = 'All';
			// }

			if (station == 'All') {
				station = 0;
			}

			if (sector == 'All') {
				sector = 0;
			}
			
			/*if (customerList == 'All') {
				customerList = 0;
				}	*/

			//alert(offs);
			/*if (isNull(offs)) {
				return;
			}
			 */

			var url = '/udaan-report/pages/reportviewer/rateRevisionReportViewer.jsp?'
					+ "&region="
					+ region
					+ "&regionName="
					+ regionName
					+ "&station="
					+ station
					+ "&stationName="
					+ stationName
					+ "&month1="
					+ month1
					+ "&month1Name="
					+ month1Name
					+ "&month2="
					+ month2
					+ "&month2Name="
					+ month2Name
					+ "&sector="
					+ sector
					+ "&sectorName="
					+ sectorName
					+ "&fuelPercentFrom="
					+ fuelPercentFrom
					+ "&fuelPercentTo="
					+ fuelPercentTo
					+ "&customerList="
					+ offs
					+ "&product="
					+ product + "&consignmentSeries=" + consignmentSeries;
			//alert(url);
			window.open(url, "_blank");
		}
	}
}

function getRraCustomerList(id, clearFields) {
	var station = readStation();
	showProcessing();
	var url = './rateRevisionAnalysisReport.do?submitName=getCustomerList&station='
			+ station;
	// var clientObj = document.getElementById('customerList');
	// ajaxCallWithoutForm(url, populateClientList);
	jQuery.ajax({
		url : url,
		type : "POST",
		dataType : "json",
		success : function(data) {

			// jQuery.unblockUI();
			populateCustomerList(data);
		}
	});

}

function populateCustomerList(data) {
	// showProcessing();
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.businessName != null
					&& $.trim(this.businessName).length > 0) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		alert("Customers not found for this station, please select another station.");
		$("#stationList").focus();
	}
	hideProcessing();

}

function getProductByCustomers() {
	var customers = $("#customerList").val();
	if (isNull(customers)) {
		alert("Please select Customer");
	} else {
		var customerIds = $("#customerList option:selected").map(function() {
			return this.value;
		}).get().join(", ");
		if (customerIds.toUpperCase() == 'ALL') {
			customerIds = '0';
		}
		showProcessing();
		var url = './rateRevisionAnalysisReport.do?submitName=getProductByCustomers&customerIds='
				+ customerIds;
		jQuery.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			success : function(data) {

				// jQuery.unblockUI();
				populateProducts(data);
			}
		});
	}

}

function populateProducts(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('product');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.productId != null) {
				var option;
				option = document.createElement("option");
				option.value = this.productId;
				option.appendChild(document.createTextNode(this.productName));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('product');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		alert("Product not mapped for selected customer(s)");
		$("#customerList").focus();
	}
	hideProcessing();

}

function clearRateRevisionScreen() {
	if (confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();
		$('#customerList').find('option').remove().end();
	}
}
/* ################ RATE REVISION REPORT - END ########################## */

function clearConsgBookingDetailStation() {

	var x = document.getElementById("regionList").disabled;

	if (x) {

		$('#stationList').find('optionelect').attr('selected', 'selected');

	} else {

		var content = document.getElementById('stationList');

		content.innerHTML = "";

		var defOption = document.createElement("option");

		defOption.value = "Select";

		defOption.appendChild(document.createTextNode("Select"));

		content.appendChild(defOption);

	}

}

function clearHitRatioDatewiseReport() {
	if (confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();
		$('#stationList').find('option').remove().end().append(
				'<option value="">Select</option>');
		$('#branchList').find('option').remove().end().append(
				'<option value="">Select</option>');
	}
}

function clearDestStationForHitRatioOrigin() {
	if (getDomElementById("destStationList")) {
		getDomElementById("destStationList").options.length = 1;
	}
}


//shaheed

function showOnlinePendingDatewiseReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var category = $("#category").val();
	var series = $("#series").val();
	var type = $("#type").val();
	var load = $("#load").val();

	var originRegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (category == null || 'select' == category.toLocaleLowerCase())
			|| (series == null || 'select' == series.toLocaleLowerCase())
			|| (type == null || 'select' == type.toLocaleLowerCase())
			|| (load == null || 'select' == load.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (category == null || $.trim(category).length <= 0
				|| 'select' == category.toLocaleLowerCase()) {
			msg = msg + 'Please Select Category\n';

		}

		if (series == null || $.trim(series).length <= 0
				|| 'select' == series.toLocaleLowerCase()) {
			msg = msg + 'Please Select Series\n';

		}

		if (type == null || $.trim(type).length <= 0
				|| 'select' == type.toLocaleLowerCase()) {
			msg = msg + 'Please Select Type\n';

		}

		if (load == null || $.trim(load).length <= 0
				|| 'select' == load.toLocaleLowerCase()) {
			msg = msg + 'Please Select Load\n';

		}

		alert(msg);
		return;
	}

	else if (checkDate('fromDate', 'toDate')) {
		var stationId, stationName, branchId, branchName, delvCategoryCode = "", delvCategoryName = "", seriesData = "", seriesLabel = "", cntypeCode, cntypeName, loadNum, loadDesc;
		if (station == 'All') {
			stationId = 0;
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text
					.toUpperCase();
		} else {
			stationId = $("#stationList").val();
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
		}

		if (branch == 'All') {
			branchId = 0;
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text
					.toUpperCase();
		} else {
			branchId = $("#branchList").val();
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
		}

		if (category == 'ALL') {
			delvCategoryCode = category;
			delvCategoryName = category;
		} else {
			delvCategoryCode = category;
			delvCategoryName = document.getElementById('category').options[document
					.getElementById('category').selectedIndex].text;
		}

		var seriesdatas = document.getElementById("series");

		if ($("#series").val().toUpperCase() == "ALL") {
			for ( var i = 1; i < seriesdatas.length; i++) {
				seriesData = seriesData + seriesdatas.options[i].value;

				if (i != (seriesdatas.length - 1)) {
					seriesData = seriesData + ",";
				}
			}
		} else {
			seriesData = $("#series").val();
		}

		seriesLabel = $("#series").val();

		if (type == 'ALL') {
			cntypeCode = type;
			cntypeName = type;
		} else {
			cntypeCode = type;
			cntypeName = document.getElementById('type').options[document
					.getElementById('type').selectedIndex].text;
		}

		if (load == 'ALL') {
			loadNum = 0;
			loadDesc = load;
		} else {
			loadNum = load;
			loadDesc = document.getElementById('load').options[document
					.getElementById('load').selectedIndex].text;
		}

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var arrStartDate = fromDate.split("/");
		var startDateFormat = arrStartDate[2] + '-' + arrStartDate[1] + '-'
				+ arrStartDate[0];
		var arrEndDate = toDate.split("/");
		var endDateFormat = arrEndDate[2] + '-' + arrEndDate[1] + '-'
				+ arrEndDate[0];

			var url = '/udaan-report/pages/reportviewer/onlinePendingDatewiseViewer.jsp?'
					+ "&RegionId="
					+ region
					+ "&RegionName="
					+ originRegionName
					+ "&StationId="
					+ stationId
					+ "&StationName="
					+ stationName
					+ "&BranchId="
					+ branchId
					+ "&BranchName="
					+ branchName
					+ "&FromDate="
					+ startDateFormat
					+ "&ToDate="
					+ endDateFormat
					+ "&DeliveryCategoryCode="
					+ delvCategoryCode
					+ "&DeliveryCategoryName="
					+ delvCategoryName
					+ "&Series="
					+ seriesData
					+ "&SeriesLabel="
					+ seriesLabel
					+ "&ConsignmentTypeCode="
					+ cntypeCode
					+ "&ConsignmentTypeName="
					+ cntypeName
					+ "&LoadNumber=" + loadNum + "&LoadDescription=" + loadDesc;
			// alert(url);
			window.open(url, "_blank");
		
	}
}

function showHitRatioProductwiseReport() {
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var category = $("#category").val();
	var series = $("#series").val();
	var type = $("#type").val();
	var load = $("#load").val();

	var originRegionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;

	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (fromDate == null || $.trim(fromDate).length <= 0)
			|| (toDate == null || $.trim(toDate).length <= 0)
			|| (category == null || 'select' == category.toLocaleLowerCase())
			
			|| (type == null || 'select' == type.toLocaleLowerCase())
			|| (load == null || 'select' == load.toLocaleLowerCase())) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (fromDate == null || $.trim(fromDate).length <= 0)
			msg = msg + "Select From Date \n";

		if (toDate == null || $.trim(toDate).length <= 0)
			msg = msg + "Select To Date \n";

		if (category == null || $.trim(category).length <= 0
				|| 'select' == category.toLocaleLowerCase()) {
			msg = msg + 'Please Select Category\n';

		}

		if (series == null || $.trim(series).length <= 0
				|| 'select' == series.toLocaleLowerCase()) {
			msg = msg + 'Please Select Series\n';

		}

		if (type == null || $.trim(type).length <= 0
				|| 'select' == type.toLocaleLowerCase()) {
			msg = msg + 'Please Select Type\n';

		}

		if (load == null || $.trim(load).length <= 0
				|| 'select' == load.toLocaleLowerCase()) {
			msg = msg + 'Please Select Load\n';

		}

		alert(msg);
		return;
	}

	else if (checkDate('fromDate', 'toDate')) {
		var stationId, stationName, branchId, branchName, delvCategoryCode = "", delvCategoryName = "", seriesData = "", seriesLabel = "", cntypeCode, cntypeName, loadNum, loadDesc;
		if (station == 'All') {
			stationId = 0;
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text
					.toUpperCase();
		} else {
			stationId = $("#stationList").val();
			stationName = document.getElementById('stationList').options[document
					.getElementById('stationList').selectedIndex].text;
		}

		if (branch == 'All') {
			branchId = 0;
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text
					.toUpperCase();
		} else {
			branchId = $("#branchList").val();
			branchName = document.getElementById('branchList').options[document
					.getElementById('branchList').selectedIndex].text;
		}

		if (category == 'ALL') {
			delvCategoryCode = category;
			delvCategoryName = category;
		} else {
			delvCategoryCode = category;
			delvCategoryName = document.getElementById('category').options[document
					.getElementById('category').selectedIndex].text;
		}

		var seriesdatas = document.getElementById("series");

		/*if ($("#series").val().toUpperCase() == "ALL") {
			for ( var i = 2; i < seriesdatas.length; i++) {
				seriesData = seriesData + seriesdatas.options[i].value;

				if (i != (seriesdatas.length - 1)) {
					seriesData = seriesData + ",";
				}
			}
		} else {
			seriesData = $("#series").val();
		}
*/
		seriesData = $("#series").val();
		seriesLabel = $("#series").val();

		if (type == 'ALL') {
			cntypeCode = type;
			cntypeName = type;
		} else {
			cntypeCode = type;
			cntypeName = document.getElementById('type').options[document
					.getElementById('type').selectedIndex].text;
		}

		if (load == 'ALL') {
			loadNum = 0;
			loadDesc = load;
		} else {
			loadNum = load;
			loadDesc = document.getElementById('load').options[document
					.getElementById('load').selectedIndex].text;
		}

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var arrStartDate = fromDate.split("/");
		var startDateFormat = arrStartDate[2] + '-' + arrStartDate[1] + '-'
				+ arrStartDate[0];
		var arrEndDate = toDate.split("/");
		var endDateFormat = arrEndDate[2] + '-' + arrEndDate[1] + '-'
				+ arrEndDate[0];
		
			var url = '/udaan-report/pages/reportviewer/hitRatioProductWiseReportViewer.jsp?'
					+ "&RegionId="
					+ region
					+ "&RegionName="
					+ originRegionName
					+ "&StationId="
					+ stationId
					+ "&StationName="
					+ stationName
					+ "&BranchId="
					+ branchId
					+ "&BranchName="
					+ branchName
					+ "&FromDate="
					+ startDateFormat
					+ "&ToDate="
					+ endDateFormat
					+ "&DeliveryCategoryCode="
					+ delvCategoryCode
					+ "&DeliveryCategoryName="
					+ delvCategoryName
					+ "&Series="
					+ seriesData
					+ "&SeriesLabel="
					+ seriesLabel
					+ "&ConsignmentTypeCode="
					+ cntypeCode
					+ "&ConsignmentTypeName="
					+ cntypeName
					+ "&LoadNumber=" + loadNum + "&LoadDescription=" + loadDesc;
			 //alert(url);
			window.open(url, "_blank");
		}
		
}

function getCustomer1(){
	customList="branchList1";
	var customerList=getDomElementById("branchList");
	var custList=getDomElementById("branchList1");
	/*var customerList=$("#customerList" ).val();
	var custList=$("#custList" ).val();*/
	var count1=0;
	var selectedCount=0;
	//clearDropDownList(custList);
	if(customerList.length!=0){ 
		/*for (var i=0; i<customerList.length; i++) {
			  if (customerList[i].selected) {
				     if(custList.length==null || custList.length==0){
				    	 addCustomer(customList, customerList[i].text,customerList[i].value);
				     } 
				     else{
				    	 for(var j=0;j<custList.length; j++){
					    	 if(customerList[i].text==custList[j].text){
					    		 alert('Customer already in list');

					    	 }
					    	 else{
					    		  count=count+1;
					    	 }
				    	 }
				    	 if(count==custList.length){
				    		 count=0;
				    		 addCustomer(customList, customerList[i].text,customerList[i].value); 
				    	 }
				     	}

				    	}
					}*/

		for (var i = customerList.length - 1; i>=0; i--) {
			if (customerList.options[i].selected) {
				addCustomer(customList, customerList[i].text,customerList[i].value);
				customerList.remove(i);
				count1=count1+1;
			}
		}
		/*var custOptions = custList.options;
		       custOptions[0].selected = true;*/
		/* if(count1>=2){
			   		disabledbills();
			   	  }
			   	   else{
			   		enabledbills();
			      }*/
		/*var no1=custList.length;
		if(no1==1){
			enabledbills();
		}
		else{
			disabledbills();
		}*/

	}
	else{
		alert('No data available');
	}

	/*if(selectedCount>=2){
	        	   disabledbills();
	           }
	           else if(selectedCount==1 ){
	        	   enabledbills();
	           }*/

}

function removeCustomers(){
	customList="branchList1";
	customerList="branchList";
	var custList=getDomElementById("branchList1");
	
	/*var custList=$("#custList" ).val();*/
	if(custList.length!=0){ 
		/*for (var i=0; i<custList.length; i++) {
		  if (custList[i].selected) {
			  custList.remove(i);
		  }
		}*/
		/*for (var i = custList.length - 1; i>=0; i--) {
		    if (custList.options[i].selected) {
		    	custList.remove(i);
		    }
		  }*/
		
		for (var i = custList.length - 1; i>=0; i--) {
			if (custList.options[i].selected) {
				addCustomer(customerList, custList[i].text,custList[i].value);
				custList.remove(i);
			}
		}

		/*var no=custList.length;
		if(no==1){
			enabledbills();
		}
		else{
			disabledbills();
		}*/
	}
	else{
		alert('No data available');
	}
}
function clearItem(){

	$("#branchList1").empty();
	$("#branchList").empty();
	$("#branchList1").empty();
}
function addCustomer(selectId,label,value){
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	//op= document.getElementsByName(label);
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}
function triggeringBranches(){
	var officeType = $("#officeType").val();
	var branchOfficeType = $("#branchOfficeType").val();
	var hubOfficeType = $("#hubOfficeType").val();
	var rhoOfficeType = $("#rhoOfficeType").val();
	if(officeType == "HO" ||officeType == "BO" || officeType == "RO" ){
		//trigger branches
	
		$("#stationList").trigger("change");
	}else{
		//alert("Event Not Triggered");
	}
}

