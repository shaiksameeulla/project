var dropdownValue = null;
var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

$(document).ready(function() {
	var oTable = $('#pickup').dataTable({
		"sScrollY" : "200",
		"sScrollX" : "100%",
		"sScrollXInner" : "220%",
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

	readonly();
	setDataTableDefaultWidth();
});
/**
 * @Desc adds default Rows
 */
function addDefaultRows() {
	if (isNull(getValueByElementId("requestHeaderId"))) {
		// call the addRow Function
		fnClickAddRow(false);
		getDomElementById("custName").focus();
	}
}

/**
 * @Desc Makes the screen read Only if the CREATE_FLAG is true
 */
function readonlyCreate() {

	var branchObj = document.getElementById('branchName');
	var objOption = document.createElement("option");
	objOption.text = BRANCH_NAME;
	objOption.value = BRANCH_ID;
	branchObj[0] = objOption;
	branchObj[0].selected = 'selected';

	// var flag = '${createPickupOrderForm.pickupOrderTO.createFlag}';
	if (CREATE_FLAG == 'true') {

		var f = document.forms['createPickupOrderForm'];
		for ( var i = 0, fLen = f.length; i < fLen; i++) {

			f.elements[i].disabled = 'disabled';
		}
		document.getElementById('new').disabled = false;
		document.getElementById('cancel').style.display = 'none';
		document.getElementById('save').style.display = 'none';
		document.getElementById('add').style.display = 'none';
		document.getElementById('delete').style.display = 'none';
	}
}

/**
 * @Desc Makes the screen read Only and shows the error List excel file if
 *       IS_ERROR_FLAG=="Y"
 */
function readonly() {
	// var isError = '${createPickupOrderForm.pickupOrderTO.isError}';
	if (IS_ERROR_FLAG == "Y") {
		// gets the error list
		getErrorList();

	}
	// var flag = '${createPickupOrderForm.pickupOrderTO.flag}';
	if (HEADER_FLAG == 'true') {

		var f = document.forms['createPickupOrderForm'];
		for ( var i = 0, fLen = f.length; i < fLen; i++) {

			f.elements[i].disabled = 'disabled';
		}
		document.getElementById('back').disabled = false;
		document.getElementById('cancel').style.display = 'none';
		document.getElementById('new').style.display = 'none';
		document.getElementById('save').style.display = 'none';
		document.getElementById('add').style.display = 'none';
		document.getElementById('delete').style.display = 'none';
	}
	// disables the screen
	readonlyCreate();

}

/**
 * @Desc shows the error File as a new pop up window to the user
 */
function getErrorList() {
	var fileName = document.getElementById('fileName').value;

	window.open('createPickupOrder.do?method=getBulkUploadErrorList&fileName='
			+ fileName);
}

/**
 * @Desc Traverses back to the Confirm Request Order Screen
 */
function Back() {
	url = '/udaan-web/confirmPickupOrder.do?method=preparePage';
	window.location = url;
}

$(document)
		.ready(
				function() {

					$
							.getJSON(
									"/udaan-web/createPickupOrder.do?method=getReverseLogisticsCustomerList",
									{
										ajax : 'true'
									},
									function(data) {
										if (data[ERROR_FLAG] != null) {
											alert(data[ERROR_FLAG]);
											return;
										}
										var options = $("#custName");
										$
												.each(
														data,
														function(index, value) {

															if (CUST_CODE == this.customerCode
																	&& CUST_CODE != "") {
																options
																		.append($(
																				"<option />")
																				.val(
																						this.customerCode)
																				.text(
																						this.businessName)
																				.attr(
																						'selected',
																						'selected'));
															} else {
																options
																		.append($(
																				"<option />")
																				.val(
																						this.customerCode)
																				.text(
																						this.businessName));
															}
														});
									});
					$("#custName")
							.change(
									function() {
										var custCode = document
												.getElementById('custName').value;
										document.getElementById('customerCode').value = custCode;
										$("#custCode").val(custCode);
									});

					$
							.getJSON(
									"/udaan-web/createPickupOrder.do?method=getConsignmentType",
									{
										ajax : 'true'
									}, function(data) {
										getConsignmentTypeResponse(data);
									});

					$("#branchName")
							.change(
									function() {
										var branchObj = document
												.getElementById('branchName');
										var branchId = branchObj.value;
										var branchName = branchObj.options[branchObj.selectedIndex].text;
										$("#deliveryBranchId").val(branchId);
										$("#deliveryOfficeName")
												.val(branchName);
									});

				});

/**
 * @Desc sets the consignment type drop down and adds the default rows
 * @param data
 */
function getConsignmentTypeResponse(data) {
	if (data[ERROR_FLAG] != null) {
		alert(data[ERROR_FLAG]);
		return;
	}
	dropdownValue = data;
	addDefaultRows();

}

/**
 * @Desc populates the drop down for given drop down Id
 * @param rowid
 */
function populateDropDown(rowid) {
	var content = document.getElementById('consignmentType' + rowid);
	$.each(dropdownValue, function(index, value) {
		var option;
		option = document.createElement("option");

		option.value = this.consignmentId;
		option.appendChild(document.createTextNode(this.consignmentName));
		content.appendChild(option);
	});
}

/**
 * @Desc loads the branches for selected customer code
 */
function loadBranchAjaxCall() {
	var buisnessId = document.getElementById('custName').value;
	if (isNull(buisnessId)) {
		document.getElementById("branchName").value = "";
		getDomElementById("branchName").options.length = 0;
		var optionSelectType = document.createElement("OPTION");
		var text = document.createTextNode("SELECT");
		optionSelectType.value = "";
		optionSelectType.appendChild(text);
		getDomElementById("branchName").add(optionSelectType);
		document.getElementById("deliveryBranchId").value = "";
		document.getElementById("deliveryOfficeName").value = "";
		return;
	}
	var pageurl = "./createPickupOrder.do?method=getDeliveryBranchesOfCustomer&customerCode="
			+ buisnessId;
	$.ajax({
		url : pageurl,
		type : "GET",
		dataType : "text",
		data : jQuery("#createPickupOrderForm").serialize(),
		success : function(json) {
			ajaxResponseBranchAjaxCall(json);
		},
		error : function(json) {
			alert('Server Error');
		}

	});
}

/**
 * @param ajaxResp
 *            is the data recived as reponse to the ajax query
 */
function ajaxResponseBranchAjaxCall(ajaxResp) {
	var branchDom = "branchName";
	document.getElementById("branchName").options.length = 0;
	if (ajaxResp != null && ajaxResp != "") {
		if (ajaxResp[ERROR_FLAG] != null) {
			alert(ajaxResp[ERROR_FLAG]);
			return;
		}
		branchList = createDropDownList(branchDom, ajaxResp);
	}
}

/**
 * @Desc creates the drop down for delivery branches
 * @param domId
 *            is the Id of the element of drop down
 * @param resList
 * @returns
 */
function createDropDownList(domId, resList) {

	var domElement = document.getElementById(domId);
	var newStr = resList.replace("{", "");
	var newStr1 = newStr.replace("}", "");
	var keyValList = newStr1.split(",");
	if (isNull(keyValList.length)) {
		return;
	}
	for ( var i = 0; i < keyValList.length; i++) {
		var objOption = document.createElement("option");
		var keyValPair = keyValList[i].split("=");
		objOption.text = keyValPair[1].trim();
		objOption.value = keyValPair[0].trim();
		if (objOption.value == BRANCH_ID) {
			objOption.selected = 'selected';
		}
		try {
			domElement.add(objOption, null);
		} catch (e) {
			domElement.add(objOption);
		}
		if (i == 0) {
			$("#deliveryBranchId").val(objOption.value);
			$("#deliveryOfficeName").val(objOption.text);
		}
	}
	return domElement;
}

/**
 * @returns makes a call to save the details entered by the user
 */
function savePickup() {

	var CustName = document.getElementById('custName').value;
	var BranchName = document.getElementById('branchName').value;

	if (isNull(CustName) || CustName == "Select") {
		alert('Please choose Customer Name');
		$('#custName').focus();
		return false;
	}
	if (isNull(BranchName) || CustName == "Select") {
		alert('Please choose Branch ');
		$('#branchName').focus();
		return false;
	}

	// var flag = validateGrid();
	// if all the mandatory fields are present save the details
	if (validateMandatoryFields()) {
		if (promptConfirmation("save")) {
			pageurl = "/udaan-web/createPickupOrder.do?method=savePickupOrder";
			showProcessing();
			$.ajax({
				url : pageurl,
				type : "POST",
				dataType : "text",
				data : jQuery("#createPickupOrderForm").serialize(),
				success : function(json) {
					saveResponse(json);
				},
				error : function(json) {
					alert('Invalid results');
				}

			});
		}
	}

}

/**
 * @Desc displays the saved data and the order Number
 * @param data
 *            to be persisted
 */
function saveResponse(data) {
	hideProcessing();
	var errormsg = null;
	try {
		var jsonvalue = jsonJqueryParser(data);
		errormsg = jsonvalue[ERROR_FLAG];
		if (!isNull(data) && errormsg != null) {
			alert(errormsg);
			return;
		}
	} catch (e) {
		// alert(e);
	}

	if (isNull(data)) {
		alert("Data not saved properly ,please submit the details again");
		return;
	}

	alert('Data saved successfully');
	// disables all the buttons on screen after save
	document.getElementById('save').style.display = 'none';
	document.getElementById('cancel').style.display = 'none';
	document.getElementById('add').style.display = 'none';
	document.getElementById('delete').style.display = 'none';
	document.getElementById('check').disabled = true;

	// displayes the order Number
	var ajaxResponse = data;
	try {
		if (ajaxResponse != "" && ajaxResponse != null) {
			var newStr = ajaxResponse.replace("{", "");
			var newStr1 = newStr.replace("}", "");
			var keyValList = newStr1.split(",");
			for ( var i = 0; i < keyValList.length; i++) {
				var keyValPair = keyValList[i].split("=");
				if (keyValPair[0] != '' && keyValPair[1] != '') {
					$("#orderNumber" + keyValPair[0].trim()).val(keyValPair[1]);
				}
			}
		}
	} catch (ex) {
		alert("Error occured");
	}
}

/**
 * @Desc uploads the file selected by user
 */
function upload() {

	var CustName = document.getElementById('custName').value;
	var BranchName = document.getElementById('branchName').value;
	var fileValue = document.getElementById('fileUpload').value;

	if (isNull(fileValue)) {
		alert('Please choose a file to upload');
		return false;
	}
	// validates the extension of the excel file
	else {
		var ext = fileValue.split(".");
		if (!(ext[1] == 'xls' || ext[1] == 'xlsx')) {
			alert('Files with only xls and xlsx format are allowed');
			return false;
		}

	}

	if (isNull(CustName) || CustName == "Select") {

		alert('Please choose Customer Name');
		return false;
	}

	if (isNull(BranchName) || CustName == "Select") {

		alert('Please choose Branch ');
		return false;
	}

	document.createPickupOrderForm.action = "/udaan-web/createPickupOrder.do?method=uploadPickupDetails";
	document.createPickupOrderForm.submit();

}

/**
 * @Desc clears the details of the screen
 */
function New() {
	document.createPickupOrderForm.action = "/udaan-web/createPickupOrder.do?method=preparePage";
	document.createPickupOrderForm.submit();
}

/**
 * @Desc clears the details of the grid
 */
function cancelDetails() {
	if (promptConfirmation("cancel")) {
		getDomElementById("chkAll").checked = false;
		$('#pickup').dataTable().fnClearTable();
		rowCount = 1;
		// adds one default row
		fnClickAddRow(false);
	}
}

var SELECTED_ROW_NUMBER = "";

/**
 * @Desc validates the pincode
 * @param obj
 *            is the Pincode Object
 */
function validatePincode(obj) {
	var objId = obj.id;
	var objIdArray = objId.split("pincode");
	var rowNo = objIdArray[1];

	SELECTED_ROW_NUMBER = rowNo;

	if (vaidPin(obj)) {

		if (isValidPincode(obj)) {
			var pincode = obj.value;

			var pageurl = "/udaan-web/createPickupOrder.do?method=validatePincodeAndGetCity&pin="
					+ pincode;

			ajaxCallWithoutForm(pageurl, validatePincodeResponse);

		} else {
			obj.value = "";
			clearPicodeAndCity();
			SELECTED_ROW_NUMBER = null;
			obj.focus();
		}
	} else {
		clearPicodeAndCity();
		SELECTED_ROW_NUMBER = null;
	}
}

function clearPicodeAndCity() {
	$("#pincode" + SELECTED_ROW_NUMBER).val("");
	$("#pincodeId" + SELECTED_ROW_NUMBER).val("");
	$("#city" + SELECTED_ROW_NUMBER).val("");
	$("#cityId" + SELECTED_ROW_NUMBER).val("");
	$("#assignedBranch" + SELECTED_ROW_NUMBER).val("");
}

/**
 * @Desc gets the city if pincode is valid else clears the pincode field
 * @param response
 *            if the pincode is valid or not
 */
function validatePincodeResponse(data) {
	var response = data;

	if (!isNull(response)) {
		if (response[ERROR_FLAG] != null) {
			clearPicodeAndCity();
			$("#pincode" + SELECTED_ROW_NUMBER).focus();
			alert(response[ERROR_FLAG]);
			return;
		}
		var pincodeId = response.pincodeId;
		if (!isNull(pincodeId)) {
			$("#pincodeId" + SELECTED_ROW_NUMBER).val(pincodeId);
			$("#city" + SELECTED_ROW_NUMBER).val(response.cityTO.cityName);
			$("#cityId" + SELECTED_ROW_NUMBER).val(response.cityTO.cityId);
			$("#assignedBranch" + SELECTED_ROW_NUMBER).val(
					response.serviceableOfficeNames);
		} else {
			$("#pincode" + SELECTED_ROW_NUMBER).focus();
			alert('Invalid Pincode/pincode is not mapped with any city');
			clearPicodeAndCity();
			return;
		}

	}
}

/**
 * @Desc validates the format of given telephone number
 * @param obj
 *            is a telephone object
 */
function isValidTelephone(obj) {
	var telephone = obj.value;
	if (!isValidPhone(telephone)) {
		alert('Invalid Telephone Number');
		obj.value = "";
		setFocus(obj);
	}
}

/**
 * @Desc validates the format of given mobile number
 * @param obj
 *            is a mobile object
 */
function isValidMobile(obj) {
	var mobile = obj.value;
	if (isNaN(mobile) || mobile.indexOf(" ") != -1) {
		alert("Enter numeric value");
		obj.value = "";
		setFocus(obj);
	} else if (mobile.length != 10) {
		alert("enter 10 digits");
		obj.value = "";

	}
}

/**
 * @Desc validates the format of given pincode number
 * @param obj
 *            is a pincode object
 */
function isValidPincode(obj) {
	var pincode = obj.value;

	if (isNaN(pincode) || pincode.indexOf(" ") != -1) {
		alert("Enter numeric value");
		obj.value = "";
		setFocus(obj);
		return false;
	}
	if (pincode.length != 6) {
		alert("enter 6 digits");
		obj.value = "";
		setFocus(obj);
		return false;
	}
	return true;
}

var giCount = 1;
var rowCount = 1;
var custName = "address";
var address = "pincode";

/**
 * @Desc adds new row to the grid
 */
function fnClickAddRow(isAddClicked) {

	var rowId = rowCount;
	$('#pickup')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"    id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
							'<div id="serialNo' + rowCount + '">' + rowCount
									+ '</div>',
							'<input type="text"    class="txtbox" id="orderNumber'
									+ rowCount
									+ '" name="pickupOrderTO.orderNumber" readonly="readonly"  onkeypress = "return callEnterKey(event, document.getElementById(\'consignnorName'
									+ rowCount + '\'));" tabindex="-1"  />',
							'<input type="text"    class="txtbox" id="consignnorName'
									+ rowCount
									+ '" name="pickupOrderTO.consignnorName" maxlength="50" onblur="isValidCN(this)" onkeypress = "return onlyAlphabetEnterKey(event, \'address'
									+ rowCount + '\');"/>',
							'<input type="text" maxlength="100" class="txtbox" id="address'
									+ rowCount
									+ '" name="pickupOrderTO.address" onblur="isValidAdd(this)" onkeypress = "return callEnterKey(event, document.getElementById(\'pincode'
									+ rowCount + '\'));"/>',
							'<input type="text"    class="txtbox" size="10" maxlength="6" id="pincode'
									+ rowCount
									+ '" name="pickupOrderTO.pincode"  onchange="validatePincode(this)"  onkeypress = "return onlyNumberNenterKeyNav(event, \'telephone'
									+ rowCount
									+ '\');"/><input type="hidden" id="pincodeId'
									+ rowCount
									+ '" name="pickupOrderTO.pincodeId"/>',
							'<input type="text"    class="txtbox" id="city'
									+ rowCount
									+ '" name="pickupOrderTO.city" onkeypress = "return callEnterKey(event, document.getElementById(\'telephone'
									+ rowCount
									+ '\'));" tabindex="-1" readonly="readonly"/><input type="hidden" id="cityId'
									+ rowCount
									+ '" name="pickupOrderTO.cityId"/>',
							'<input type="text"    class="txtbox" size="12" maxlength="10" id="telephone'
									+ rowCount
									+ '" name="pickupOrderTO.telephone" onchange="isValidTelephone(this)" onkeypress = "return onlyNumberNenterKeyNav(event, \'mobile'
									+ rowCount + '\');"/>',
							'<input type="text"    class="txtbox" size="12" maxlength="10" id="mobile'
									+ rowCount
									+ '" name="pickupOrderTO.mobile" onchange="isValidMobile(this)"  onkeypress = "return onlyNumberNenterKeyNav(event, \'email'
									+ rowCount + '\');"/>',
							'<input type="text"    class="txtbox" id="assignedBranch'
									+ rowCount
									+ '" name="pickupOrderTO.assignedBranch" onkeypress = "return callEnterKey(event, document.getElementById(\'email'
									+ rowCount
									+ '\'));" tabindex="-1" readonly="readonly"/>',
							'<input type="text"    class="txtbox" id="email'
									+ rowCount
									+ '" name="pickupOrderTO.email" maxlength="50" onblur="checkMailId(this)" onkeypress = "return callEnterKey(event, document.getElementById(\'consignmentType'
									+ rowCount + '\'));"/>',
							'<select name="pickupOrderTO.consignmentTypeId" class="txtbox" id="consignmentType'
									+ rowCount
									+ '" onkeypress = "return callEnterKey(event, document.getElementById(\'materialDesc'
									+ rowCount
									+ '\'));"><option value="">--Select--</option></select>  ',
							'<input type="text"    class="txtbox" id="materialDesc'
									+ rowCount
									+ '" name="pickupOrderTO.materialDesc" maxlength="50" onkeypress = "return callEnterKey(event, document.getElementById(\'insuaranceRefNum'
									+ rowCount + '\'));"/>',
							'<input type="text"    class="txtbox" id="insuaranceRefNum'
									+ rowCount
									+ '" name="pickupOrderTO.insuaranceRefNum" maxlength="20" onkeypress = "return callEnterKey(event, document.getElementById(\'remarks'
									+ rowCount + '\'));"/>',
							'<input type="text" class="txtbox" id="remarks'
									+ rowCount
									+ '" name="pickupOrderTO.remarks" maxlength="50" onkeypress="return callEnterKey(event, document.getElementById(\'add'
									+ '\'),false);" /> \
							<input type="hidden" id="srNoArr'
									+ rowCount
									+ '" name="pickupOrderTO.srNoArr" value ="'
									+ rowCount + '" /> ' ]);

	populateDropDown(rowId);
	rowCount++;
	updateSerialNoVal("pickup");
	if(isAddClicked){
		setTimeout(function() {
			$('#consignnorName'+rowId).focus();
		}, 10);
	}	
	return rowCount - 1;
}

/**
 * @desc Validates the consignment Number
 * @param custName
 * @returns {true if Consignment number is valid}
 */
function isValidCN(custName) {
	var cn = true;
	if (isNull(custName.value)) {
		alert('Please enter Consigner Name');
		cn = false;
	}
	return cn;
}

/**
 * @desc Validates the Address
 * @param address
 * @returns {true if address is valid}
 */
function isValidAdd(address) {
	var add = true;
	if (isNull(address.value)) {
		alert('Please enter Address');
		add = false;
	}
	return add;
}

/**
 * @desc Validates the Pincode
 * @param pincode
 * @returns {true if pincode is valid}
 */
function vaidPin(pin) {
	var pincode = true;
	if (isNull(pin.value)) {
		alert('Please enter Pincode');
		pincode = false;
	}
	return pincode;
}

/* @Desc Enter key navigation */
function callEnterKey(e, objectCn, isLastRow) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (isLastRow) {
			fnClickAddRow(true);
			// setFocus();
		} else
			objectCn.focus();
		return false;
	}
}

/* @Desc Enter key navigation */
function onlyAlphabetEnterKey(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	/*
	 * if ((charCode >= 97 && charCode <= 122) || (charCode >= 65 && charCode <=
	 * 90) || charCode == 32){ //alert("input was a letter or space"); return
	 * true; }
	 */
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 32
			|| charCode == 9 || charCode == 8 || charCode == 127
			|| charCode == 0) {
		return true;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return false;
	}
}

/**
 * @Desc deletes the row from grid
 * @param tableId
 *            for which row is to be deleted
 */
function deleteTableRow(tableId) {

	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		isCheckBoxSelectedWithMessage('chkBoxName',
				"Please select the row to delete");
		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					checkAllBoxes('chkBoxName', false);
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId, i - 1);
				// table.deleteRow(i);
				rowCount--;
				i--;
				updateSerialNoVal(tableId);
			}
		}

	} catch (e) {
		alert(e);
	}
}

function deleteRow(tableId, rowIndex) {

	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

/**
 * @Desc sets the focus on the given field
 * @param obj
 */
function setFocus(obj) {
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * @Desc enables the bulk Import section
 * @param domElem
 */
function enableBultImport(domElem) {

	var fileUpld = getDomElementById("fileUpload");
	var upld = getDomElementById("Upload");
	// Added for artf2965531 : If bulk import check box is selected Add, Delete
	// and Save are not disabled
	var addBtn = getDomElementById("add");
	var deleteBtn = getDomElementById("delete");
	var saveBtn = getDomElementById("save");
	if (domElem.checked) {
		fileUpld.disabled = false;
		upld.disabled = false;
		jQuery("#" + "Upload").removeClass("btnintformbigdis");
		jQuery("#" + "Upload").addClass("button");
		// Added for artf2965531 : If bulk import check box is selected Add,
		// Delete and Save are not disabled
		addBtn.disabled = true;
		deleteBtn.disabled = true;
		saveBtn.disabled = true;
		$('#pickup').dataTable().fnClearTable();
		rowCount = 1;
		// adds one default row
		fnClickAddRow(false);
	} else {
		fileUpld.value = "";
		fileUpld.setAttribute("disabled", true);
		upld.setAttribute("disabled", true);
		jQuery("#" + "Upload").removeClass("button");
		jQuery("#" + "Upload").addClass("btnintformbigdis");
		// Added for artf2965531 : If bulk import check box is selected Add,
		// Delete and Save are not disabled
		addBtn.disabled = false;
		deleteBtn.disabled = false;
		saveBtn.disabled = false;
	}
}

/**
 * @ Desc validates the mandatory fields
 * @returns {true if mandatory filed are present}
 */
function validateMandatoryFields() {
	var isValid = true;
	var table = document.getElementById("pickup");
	var orderNumberDom = document
			.getElementsByName('pickupOrderTO.orderNumber');
	var lastRowId = getTableLastRowIdByElement('pickup',
			'pickupOrderTO.orderNumber', 'orderNumber');
	// get last row id
	for ( var i = 1; i < table.rows.length; i++) {
		var counter = i - 1;
		var rowId = getRowId(orderNumberDom[counter], 'orderNumber');

		var consignnorName = document.getElementById("consignnorName" + rowId);
		var address = document.getElementById("address" + rowId);
		var pincode = document.getElementById("pincode" + rowId);
		if (!isNull(lastRowId) && lastRowId == rowId) {// check whether last
														// row is empty, if it's
														// empty retun true
			if (isNull(consignnorName.value)) {
				return true;
			}
		}

		if (isNull(consignnorName.value)) {
			alert("Please Enter Consigner Name.");
			setFocus(consignnorName);
			isValid = false;
			return isValid;
		}
		
		if (isNull(address.value)) {
			alert("Please Enter Address.");
			setFocus(address);
			isValid = false;
			return isValid;
		}
		
		if (isNull(pincode.value)) {
			alert("Please Enter Pincode.");
			setFocus(pincode);
			isValid = false;
			return isValid;
		}
	}

	return isValid;
}

/**
 * validates the last row is empty or not
 * @param rId
 * @returns {true if the last row is empty }
 */
function isLastRowEmpty(rId) {

	var orderNum = getDomElementById("orderNumber" + rId);
	if (orderNum != null && isNull(orderNum.value)) {
		return true;
	}
	return false;
}
function updateSerialNoVal(tableId) {
	try {
		var table = document.getElementById(tableId);
		for ( var i = 1; i < table.rows.length; i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var serialNo = document.getElementById("serialNo" + rowId);
			if (serialNo.innerHTML != i) {
				serialNo.innerHTML = i;
			}
		}
	} catch (e) {

	}
}
function enterKeyNavigationFocus(evt, elementIdToFocus){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	
	if(charCode==13){
		$("#" + elementIdToFocus).focus();
		//document.getElementById(elementIdToFocus).focus();
		return true;
	}
	return false;
}