var detailIdArr = null;
var orderNumbersArr = null;
var customersArr = null;
var currentSelectedArr = null;
var previousSelectedArr = null;
var pickupLocIdsArr = null;
var reversePickupIdsArr = null;

var isSelected = false;
var isPrevSelected = false;

// Added by Kaustubh on 19Sept2013
var previousSelectedCustomers = null;
var currentSelectedCustomers = null;
var customerList = null;

function setAssignmentTypeChange() {
	setRunsheetType();
	$('#branchEmployees').find('option:first').attr('selected', 'selected');
	clearItemGridRows();
}
function setRunsheetType() {
	var runsheetType = $('#runsheetType option:selected').text();
	$('#assignmentType').val(runsheetType);

	// Setting focus to pickup runsheet type
	document.getElementById("runsheetType").focus();
}

function searchCustomersForEmployee() {
	var runsheetType = document.getElementById("runsheetType").value;
	var branchEmployees = $('#branchEmployees');
	var offType = document.getElementById("assignmentCreatedAtOfficeType").value;
	var branch = $('#branch');
	if (isNull(branch.val())) {
		if (offType == "HO") {
			alert("Please select Branch");
			branchEmployees.val('');
			branch.focus();
			return false;
		}
	}
	if (isNull(branchEmployees.val())) {
		alert("Please select Employee");
		branchEmployees.focus();
		return false;
	}
	if (!isNull(runsheetType) && runsheetType != "select") {
		//Clear grid details.
		clearItemGridRows(); // New Ln
		var url = "/udaan-web/pickupAssignmentAction.do?method=getCustomerListForAssignment&branchId="
				+ branch.val() + "&employeeId=" + branchEmployees.val();
		ajaxCallWithType(url, "createRunsheetAssignmentForm",
				getCustomerListForAssignment, "json");
	} else {
		alert("Please select Pickup Run Sheet Type");
		document.getElementById("runsheetType").focus();
	}
}

function getCustomerListForAssignment(data) {
	if (!isNull(data)) {
		progressBar();
		// Exception handling
		if (data[ERROR_FLAG] != null) {
			alert(data[ERROR_FLAG]);
			return;
		}
		detailIdArr = new Array();
		orderNumbersArr = new Array();
		customersArr = new Array();
		currentSelectedArr = new Array();
		previousSelectedArr = new Array();
		pickupLocIdsArr = new Array();
		reversePickupIdsArr = new Array();
		previousSelectedCustomers = new Array();
		currentSelectedCustomers = new Array();
		assignmentHeader = eval(data);
		customerList = assignmentHeader.runsheetAssignmentDetailTOs;
		// Populating hidden fields
		populateHiddenFields(assignmentHeader);
		// Populating grid
		populateGrid(customerList);
		// check whether assignment has already generated or not
		checkAssignmentGenerated(assignmentHeader);
		disableSaveButton();
		hideProcessing();
	} else {
		alert("invalid data from server");
		$('#branchEmployees').val('');
	}
}

function checkAssignmentGenerated(assignmentHeader) {
	if (assignmentHeader.assignmentGenerated == 'G') {
		disableGrid();
		alert('Assignment has got generated for this employee');
	} else {
		enableGrid();
	}
}

function disableGrid() {
	disableSaveButton();
	disableAllCheckboxes();
}

function enableGrid() {
	enableSaveButton();
	enableAllCheckboxes();
}

function disableSaveButton() {
	$("#save").attr('disabled', true);
}

function enableSaveButton() {
	$("#save").attr('disabled', false);
}

function disableAllCheckboxes() {
	getDomElementById("headerCheckbox").disabled = true;
	$('#asignmentGrid input[type=checkbox]').attr('disabled', 'true');
}

function enableAllCheckboxes() {
	$('#asignmentGrid input[type=checkbox]').removeAttr('disabled');
}

function populateHiddenFields(assignmentHeader) {
	$("#assignmentHeaderId").val(assignmentHeader.assignmentHeaderId);
	$("#dataTransferStatus").val(assignmentHeader.dataTransferStatus);
	$("#createdBy").val(assignmentHeader.createdBy);
	$("#createdDate").val(assignmentHeader.createdDate);
	$("#updatedBy").val(assignmentHeader.updatedBy);
	$("#updatedDate").val(assignmentHeader.updatedDate);
}

function populateGrid(customerList) {
	clearItemGridRows();
	previousSelectedCustomers = new Array();
	currentSelectedCustomers = new Array();
	if (!isNull(customerList) && customerList.length > 0) {
		$.each(customerList, function(index) {
			var customerDetails = customerList[index];
			preapareFormData(customerDetails, index);
			createAssignmentGridRow(customerDetails,index); // Modified Ln
		});
	}
}

function preapareFormData(customerDetails, index) {
	previousSelectedArr[index] = customerDetails.previouslyMapped;
	if (customerDetails.previouslyMapped == true) {
		previousSelectedCustomers[previousSelectedCustomers.length] = getCustomerCode(customerDetails);
		currentSelectedCustomers[currentSelectedCustomers.length] = getCustomerCode(customerDetails);
	}
	detailIdArr[index] = customerDetails.assignmentDetailId;
	orderNumbersArr[index] = customerDetails.reversePickupOrderNumber;
	pickupLocIdsArr[index] = customerDetails.pickupLocationId;
	reversePickupIdsArr[index] = customerDetails.revPickupId;
}

function getCustomerName(customerDetails) {
	return customerDetails.customerName;
}

function getCustomerCode(customerDetails) {
	var customerCode = "";
	var shippedToCode = "";
//	customerCode = customerDetails.customerCode;
	shippedToCode = customerDetails.shippedToCode;
	if(isNull(shippedToCode)){
		customerCode = customerDetails.customerCode;
	}else{
		customerCode = customerDetails.shippedToCode;
	}
	
	return customerCode;
}

function getPickupLocation(customerDetails) {
	return customerDetails.pickupLocation;
}

function getCustomerOrderNo(customerDetails) {
	var orderNo = "";
	if (customerDetails.pickupType == 'S') {
		orderNo = "-";
	} else if (customerDetails.pickupType == 'R') {
		orderNo = customerDetails.reversePickupOrderNumber;
	}
	return orderNo;
}

function getCheckboxStatus(customerDetails,rowCount) {
	var check = null;
	var customerCode = getCustomerCode(customerDetails);
	var index = rowCount - 1;
	if (customerDetails.previouslyMapped == true) {
		isPrevSelected = true;
	 	check = '<input type="checkbox" id="checkbox' + rowCount+ '" name="checkbox" checked ="checked" value="' + index + '"  onclick ="return checkSelection(\''+customerCode+'\' ,event)"/>';
	}else{
		isPrevSelected = false;
		check = '<input type="checkbox" id="checkbox' + rowCount+ '" name="checkbox" value="' + index + '"  onclick ="return checkSelection(\''+customerCode+'\' ,event)"/>';
	}
	return check;
}

function clearItemGridRows() {
	//Clear grid details.
	$('#asignmentGrid').dataTable().fnClearTable(); //New Ln
	
	//Clear select all check box status
	getDomElementById("headerCheckbox").disabled = false;
	getDomElementById("headerCheckbox").checked = false;
}

function createAssignmentGridRow(customerDetails,index) {
	var rowCount = index + 1;
	$('#asignmentGrid').dataTable().fnAddData([
	     rowCount,
	     getCustomerName(customerDetails),
	     getCustomerCode(customerDetails),
	     getPickupLocation(customerDetails),
	     getCustomerOrderNo(customerDetails),
	     getCheckboxStatus(customerDetails,rowCount),
	 ]);
}

var CUST_CODE_INDEX = 2;
var ORDER_NO_INDEX = 4;
var IS_MAPPED_INDEX = 5;

function saveAssignment() {
	prepareCurrentlyMapped();
	setHiddenParams();
	if (!isPrevSelected && !isSelected) {
		alert("please select atleast one customer.");
	} else {
		var url = "/udaan-web/pickupAssignmentAction.do?method=savePickupAssignment&operation=delete";
		ajaxCall(url, "createRunsheetAssignmentForm", processSaveResponse);
	}
}

function prepareCurrentlyMapped() {
	$('#asignmentGrid >tbody >tr').each(function(i, tr) {
		$('td', tr).each(function(column, td) {
			switch (column) {
			case CUST_CODE_INDEX:
				var code = $(this).html();
				customersArr[i] = code;
				break;
			case ORDER_NO_INDEX:
				var orderN = $(this).html();
				orderNumbersArr[i] = orderN;
				break;
			case IS_MAPPED_INDEX:
				var isChecked = $(this).find('input:checkbox').attr('checked');
				currentSelectedArr[i] = isChecked;
				if (isChecked) {
					isSelected = true;
				}
				break;
			default:
				break;
			}
		});
	});
}

function setHiddenParams() {
	$("#assignmentDetailIds").val(detailIdArr);
	$("#orderNumbers").val(orderNumbersArr);
	$("#customerCodes").val(customersArr);
	$("#currentSelected").val(currentSelectedArr);
	$("#previousSelected").val(previousSelectedArr);
	$("#pickupLocIds").val(pickupLocIdsArr);
	$("#revPickupIds").val(reversePickupIdsArr);
}
function processSaveResponse(data) {
	if (!isNull(data)) {
		var result = jsonJqueryParser(data);
		var success = result["SUCCESS"];
		var failure = result["ERROR"];
		if (!isNull(failure)) {
			alert(failure);
		} else if (!isNull(success)) {
			searchCustomersForEmployee();
			alert(success);
		} else {
			aler("Problem in saving");
		}
	}
}

function hederSelectBoxClick(event) {
	var isChecked = jQuery('#headerCheckbox').attr('checked');
	if (isChecked) {
		checkAll();
	} else {
		unCheckAll();
	}
	checkSelection("ALL", event);
} 

function clearChanges() {
	//BA Comment : After assignment is saved user remains on same page with saved data , so clear button should refresh the page
	if(confirm("Do you want to Clear the details ?")) {
		var offType = document.getElementById("assignmentCreatedAtOfficeType").value;
		if (offType == "HO") {
			$('#branch').find('option:first').attr('selected', 'selected');
		}	
		$('#branchEmployees').find('option:first').attr('selected', 'selected');
		setRunsheetType();
	
		var url = "./pickupAssignmentAction.do?method=preparePage";	
		document.createRunsheetAssignmentForm.action = url;
		document.createRunsheetAssignmentForm.submit();
	}
}

function checkAll() {
	jQuery('#asignmentGrid >tbody >tr').each(function(i, tr) {
		jQuery('td', tr).each(function(i, td) {
			jQuery(this).find('input:checkbox').attr('checked', 'checked');
		});
	});
}
function unCheckAll() {
	jQuery('#asignmentGrid >tbody >tr').each(function(i, tr) {
		jQuery('td', tr).each(function(i, td) {
			jQuery(this).find('input:checkbox').attr('checked', false);
		});
	});
}
function getEmployeesByOfficeId(domObject) {
	clearItemGridRows();
	createEmptyDropDown('branchEmployees');
	if (!isNull(domObject.value)) {
		var url = "./pickupAssignmentAction.do?method=ajaxEmployeesByOffice&branchId="
				+ domObject.value;
		ajaxCall(url, "createRunsheetAssignmentForm",
				ajaxResponseForEmployeeList);
	} else {

	}
}
function ajaxResponseForEmployeeList(resp) {
	var employeeList = null;
	if (!isNull(resp)) {
		employeeList = jsonJqueryParser(resp);
		if (!isNull(employeeList)) {
			// process list in for style id=='customerList'
			// clearDropDown('branchEmployees');
			createEmptyDropDown('branchEmployees');
			$.each(employeeList, function(k, employee) {
				var code = employee.empCode;
				var empName = "";
				var name = "";
				if (!isNull(employee.firstName)) {
					name = employee.firstName;
				}
				if (!isNull(employee.lastName)) {
					name = name + " " + employee.lastName;
				}
				empName = name + " - " + code;
				$('#branchEmployees').append(
						"<option value='" + employee.employeeId + "'>"
								+ empName + "</option>");
			});
		} else {
			alert("Employee list Does not exist");
		}
	} else {
		alert("Employee list Does not exist");
	}
}

// Added by Narasimha for enter key navigation

function callEnterKeyForPickup(e, offType) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (offType == "HO") {
			document.getElementById("branch").focus();
		} else {
			document.getElementById("branchEmployees").focus();
		}
	}
	return false;
}
$(document).on('click', '.checkbox1', function() {
    var id = $(this).closest("tr").attr("id");
    var string = 'div_id=' + id;
    alert(string);
       // string sent to processing script here
});
function checkSelection(customerCode, event) {
	// handle new customer selection/de-selection
	var source = event.target || event.srcElement;
	if (source.checked == 1) {
		if(customerCode == "ALL"){
			$.each(customerList, function(index) {
				currentSelectedCustomers[currentSelectedCustomers.length] = getCustomerCode(customerList[index]);
			});
		}else{
			currentSelectedCustomers[currentSelectedCustomers.length] = customerCode;
		}		
	} else {
		if(customerCode == "ALL"){
			$.each(customerList, function(index) {
				var custcode = getCustomerCode(customerList[index]);
				remove(currentSelectedCustomers, custcode);
			});
		}else{
			remove(currentSelectedCustomers, customerCode);
		}
	}
	// compare if previously selected customers are same as currently selected
	// customers
	var list1 = $(previousSelectedCustomers).not(currentSelectedCustomers);
	var list2 = $(currentSelectedCustomers).not(previousSelectedCustomers);
	if (list1.length == 0	&& list2.length == 0) {
		disableSaveButton();
	} else {
		enableSaveButton();
	}
}

function remove(arr, what) {
	//var found = arr.indexOf(what);
	var found = null;
	if (window.event)
		found = jQuery.inArray( what, arr ); // IE
	else
		found = arr.indexOf(what); // firefox	
	
	while (found != -1) {
		arr.splice(found, 1);
		//found = arr.indexOf(what);
		if (window.event)
			found = jQuery.inArray( what, arr ); // IE
		else
			found = arr.indexOf(what); // firefox
	}
}