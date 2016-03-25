var ERROR_FLAG = "ERROR";
var rowCount = 0;
var YES = "Y";
var NO = "N";
var srNo = 1;

/**
 * To get customers according to the selected region
 */
function getCustomersForSelectedRegion(object) {
	if (!isNull(object) && !isNull(object.value)) {
		var url = './bulkCollectionValidation.do?submitName=getCustomersForSelectedRegion';
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#bulkCollectionValidationForm").serialize(),
			success : function(req) {
				populateCustomersForSelectedRegion(req);
			}
		});
	}
	else {
		alert('Kindly reload the page and try again');
	}
}


/**
 * Populates customer name list for auto-suggest feature on screen
 * @param req
 */
function populateCustomersForSelectedRegion(req) {
	var ajaxResp = eval('(' + req + ')');
	data = new Array();
	custCodeArr = new Array();
	custIdArr = new Array();
	/**
	 * The below line has been added to clear the previously populated list.
	 * The autocomplete "caches" the previously searched region in the list and hence, if the below line is not added
	 * then the previous results still persist along with the new ones 
	 * */
	jQuery("#customerName").unautocomplete();
	if (!isNull(ajaxResp)) {
		if (ajaxResp.length != 0) {
			/**
			 * The user will be allowed to enter a customer name in the customer name field only when the
			 * customer list is populated
			 * */
			getDomElementById('customerName').readOnly = false;
			for ( var i = 0; i < ajaxResp.length; i++) {
				data[i] = ajaxResp[i].businessName;
				custCodeArr[i] = ajaxResp[i].customerCode;
				custIdArr[i] = ajaxResp[i].customerId;
			}
			
			jQuery("#customerName").autocomplete(data);
			jQuery("#customerName").focus();
		}
	}
	else {
		alert('No customers found for the selected region');
	}
	getDomElementById('customerName').value = '';
	getDomElementById('customerCode').value = '';
	getDomElementById('currentPageNumber').value = 0;
	getDomElementById('numberOfPages').value = 0;
	getDomElementById('checkAll').checked = false;
	getDomElementById('pageNumbers').innerHTML = '';
	buttonDisabled('nextButton','button_disable');
	buttonDisabled('lastButton','button_disable');
	buttonDisabled('firstButton','button_disable');
	buttonDisabled('previousButton','button_disable');
	deleteAllRows();
	hideProcessing();
	resetTotalAmountField();
}


/**
 * Populates the customer code text box after the user selects a customer name
 */
function getCustCode() {
	var custNm = getDomElementById("customerName");
	if (custNm.readOnly == false) {
		var selectedName = jQuery("#customerName").val();
		$.each(data, function(index, customer) {
			if (customer == selectedName) {
				jQuery("#customerCode").val(custCodeArr[index]);
				jQuery("#custId").val(custIdArr[index]);
			}
		});
	}
}


/**
 * Gets the customer Id of the selected customer
 * @returns
 */
function getSelectedCustomerId() {
	var custNm = getDomElementById("customerName");
	var customerId = null;
	if (custNm.readOnly == false) {
		var selectedName = jQuery("#customerName").val();
		$.each(data, function(index, customer) {
			if (customer == selectedName) {
				customerId = custIdArr[index];
			}
		});
	}
	return customerId;
}


/**
 * Checks whether the region is selected from the drop down
 * @returns {Boolean}
 */
function isRegionSelected() {
	var flag = true;
	var selectedRegion = $('#regionId').val();
	if (isNull(selectedRegion)) {
		alert("Please select atleast 1 region before selecting customer");
		$('#regionId').focus();
		$("#customerName").val("");
		flag = false;
	}
	return flag;
}


/**
 * Searches collection details for the selected parameters
 */
function searchCollectionDetailsForBulkValidation() {
	if (validateHeaderFieldsBeforeSearching()) {
		var fromDate = getDomElementById("fromDate");
		var toDate = getDomElementById("toDate");
		var customerId = getSelectedCustomerId();
		var url = "./bulkCollectionValidation.do?submitName=searchCollectionDetailsForBulkValidation&fromDate="
			+ fromDate.value
			+ "&toDate=" + toDate.value
			+ "&custId=" + customerId
			+ "&navigationLabel=" + 'S';
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#bulkCollectionValidationForm").serialize(),
			success : function(ajaxResponse) {
				populateDataTableWithSearchResults(ajaxResponse);
			}
		});
	}
}


/**
 * Validates whether all fields are selected before the user clicks on Search
 * @returns {Boolean}
 */
function validateHeaderFieldsBeforeSearching() {
	var fromDate = getDomElementById("fromDate");
	var toDate = getDomElementById("toDate");
	var regionId = getDomElementById("regionId");
	var customerName = getDomElementById("customerName");
	if (isNull(fromDate) || fromDate.value == "") {
		alert('Please select From Date');
		return false;
	}
	
	if (isNull(toDate) || toDate.value == "") {
		alert('Please select To Date');
		return false;
	}
	
	if (isNull(regionId) || regionId.value == "0") {
		alert('Please select a region');
		return false;
	}
	
	if (isNull(customerName) || customerName.value == "") {
		alert('Please select a customer name');
		return false;
	}
	return true;
}


/**
 * Call back method for populating the data grid on screen
 * @param ajaxResponse
 */
function populateDataTableWithSearchResults(ajaxResponse) {
	hideProcessing();
	deleteAllRows();
	resetTotalAmountField();
	
	if (!isNull(ajaxResponse)) {
		var data = jsonJqueryParser(ajaxResponse);
		var error = data[ERROR_FLAG];
		if (ajaxResponse != null && error != null) {
			alert(error);
			pageClear();
			return ;
		}
		
		if (!isNull(data)) {
			var numberOfEntries = data.bulkCollectionDetails.length;
			/* Proceed ahead only if there is data to display */
			if (numberOfEntries != 0) {
				setSerialNumber(data);
				getDomElementById('validateBtn').disabled = false;
				
				rowCount = 0;
				for ( var i = 0; i < numberOfEntries; i++) {
					var bulkCollectionDetailEntry = data.bulkCollectionDetails[i];
					if (!isNull(bulkCollectionDetailEntry)) {
						$('#collectionDetailsTable')
								.dataTable()
								.fnAddData(
										[
	                                            '<label id="srNo' + rowCount + '" class="row1">' + srNo + '</label>',
												'<input type="checkbox" id="isChecked' + rowCount + '" name="chkBoxName" tabindex="-1" onClick="setIsCheckedValue(this);"/>',
												'<label id="collectionDate' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.collectionDate + '</label>',
												'<label id="bookingDate' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.bookingDate + '</label>',
												'<a id="transactionNo' + rowCount + '" href="JavaScript:collectionDtls(\'./billCollection.do?submitName=viewCollectionEntryDtls&txnNo='  
												+ bulkCollectionDetailEntry.transactionNo  
												+ '&collectionType=' + bulkCollectionDetailEntry.collectionCategory
												+ '&isCorrectionParam=N'
												+ '&officeId=' + bulkCollectionDetailEntry.collectionOfficeId + '\');">' + 
												bulkCollectionDetailEntry.transactionNo + '</a>',
												'<label id="consignmentNo' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.consignmentNo + '</label>',
												'<label id="modeOfPayment' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.paymentType + '</label>',
												'<label id="bookingAmount' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.bookingAmount + '</label>',
												'<label id="collectedAmount' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.totalCollectionAmount + '</label>',
												'<label id="transactionStatus' + rowCount + '" class="row1">' + bulkCollectionDetailEntry.collectionStatus + '</label>\
												<input type="hidden" name="to.isChecked" id ="checkBoxes'	+ rowCount	+ '" />\
												<input type="hidden" name="to.transactionNumbers" id ="transactionNumbers' + rowCount	+ '" />'
										]);
						rowCount++;
						srNo++;
					}
				}
				setPaginationParameters(data);
			}
			else {
				alert('No transactions found for the selected search criteria');
				$('#checkAll').attr("checked", false);
			}
		}
	}
}


/**
 * Deletes all the rows of the data grid on screen
 */
function deleteAllRows() {
	var table = getDomElementById("collectionDetailsTable");
	var tableRowCount = table.rows.length;
	var oTable = $('#collectionDetailsTable').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}
}


function pageClear() {
	window.location=PAGE_RELOAD_URL;
}


/**
 * Validates the selected transactions and updates their status in the database
 */
function validateSelectedTransactions() {
	if (isAnyChecked()) {
		getDomElementById("validateBtn").disabled = true;
		var url = "./bulkCollectionValidation.do?submitName=validateSelectedTransactions";
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#bulkCollectionValidationForm").serialize(),
			success : function(ajaxResponse) {
				clearValidatedTransactions(ajaxResponse);
			}
		});
	}
	else {
		alert('Please select atleast one transaction to validate');
	}
}


/**
 * Call back method used to clear the validated transactions
 * @param ajaxResponse
 */
function clearValidatedTransactions(ajaxResponse) {
	hideProcessing();
	getDomElementById("validateBtn").disabled = false;
	if (!isNull(ajaxResponse)) {
		var data = jsonJqueryParser(ajaxResponse);
		var error = data[ERROR_FLAG];
		if (ajaxResponse != null && error != null) {
			alert(error);
			pageClear();
			return ;
		}
		
		if (!isNull(data)) {
			alert('Transactions validated successfully');
			searchCollectionDetailsForBulkValidation();
		}
	}	
}


/**
 * To set isChecked element value according to checked and unchecked respective
 * check boxes.
 */
function setIsCheckedValue(obj) {
	var rowId = getRowId(obj, "isChecked");
	if (obj.checked == true) {
		getDomElementById("checkBoxes" + rowId).value = 'Y';
		var transNo = getDomElementById("transactionNo" + rowId).textContent;
		getDomElementById("transactionNumbers" + rowId).value = transNo;
		addAmountToTotalAmountField(rowId);
	} else {
		getDomElementById("isChecked" + rowId).value = null;
		getDomElementById("transactionNumbers" + rowId).value = null;
		subtractAmountFromTotalAmountField(rowId);
	}
}


function addAmountToTotalAmountField(rowId) {
	var labelTextNumber = parseFloat(getDomElementById("collectedAmount" + rowId).textContent);
	totalSelectedAmount = totalSelectedAmount + labelTextNumber;
	getDomElementById("totalAmount").value = totalSelectedAmount;
}

function subtractAmountFromTotalAmountField(rowId) {
	var labelTextNumber = parseFloat(getDomElementById("collectedAmount" + rowId).textContent);
	totalSelectedAmount = totalSelectedAmount - labelTextNumber;
	getDomElementById("totalAmount").value = totalSelectedAmount;
}

/**
 * Checks all the check boxes in a single click
 */
function selectAllCheckBoxes() {
	var isCheckAll = false;
	if ($('#checkAll').attr('checked')) {
		isCheckAll = true;
		totalSelectedAmount = 0;
	}
	
	var rowCount = getDomElementById("collectionDetailsTable").rows.length;
	for ( var i = 0; i < rowCount; i++) {
		var isCheckedObj = getDomElementById("isChecked" + i);
		if (!isNull(isCheckedObj)) {
			$('#isChecked' + i).attr("checked", isCheckAll);
			if (isCheckAll) {
				setIsCheckedValue(isCheckedObj);
			}
		}
	}
	
	if (!isCheckAll) {
		totalSelectedAmount = 0;
		getDomElementById("totalAmount").value = totalSelectedAmount;
	}
}


/**
 * To check whether any check box is checked or not.
 * 
 * @returns {Boolean}
 */
function isAnyChecked() {
	var tableLength = getDomElementById("collectionDetailsTable").rows.length;
	var flag = false;
	for ( var i = 0; i < tableLength-1; i++) {
		if (getDomElementById("isChecked" + i).checked == true) {
			flag = true;
			break;
		}
	}
	return flag;
}


/**
 * This method performs 2 validations on the to date : 
 * 1) To date should not be less than the From date
 * 2) To date should not be greater than the current date
 * @param obj
 * @returns {Boolean}
 */
function validateToDate(obj){
	if(!isNull(obj.value)){
		var fromDate = getDomElementById("fromDate").value;
		if (!isNull(fromDate) && fromDate != "") {
			var i = compareSelectedDates(fromDate,obj.value);
			if(i == undefined || i==1) {
				alert("To Date should be greater than or equal to From Date");
				setTimeout(function(){obj.focus();}, 10);
				obj.value="";
				return false;
			}
		}
		
		var currentDate = getDateInDDMMYYYY(new Date());
		i = compareSelectedDates(obj.value,currentDate);
		if(i == undefined || i==1) {
			alert("To Date should not be greater than current date");
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			return false;
		}
	}
	return true;
}


/**
 * This method performs 2 validations on the from date : 
 * 1) From date should not be greater than the To date
 * 2) From date should not be greater than the current date
 * @param obj
 * @returns {Boolean}
 */
function validateFromDate(obj){
	if(!isNull(obj.value)){
		var toDate = getDomElementById("toDate").value;
		if (!isNull(toDate) && toDate != "") {
			var i = compareSelectedDates(obj.value,toDate);
			if(i == undefined || i==1) {
				alert("From Date should be less than or equal to To Date");
				setTimeout(function(){obj.focus();}, 10);
				obj.value="";
				return false;
			}
		}
		
		var currentDate = getDateInDDMMYYYY(new Date());
		i = compareSelectedDates(obj.value,currentDate);
		if(i == undefined || i==1) {
			alert("From Date should not be greater than current date");
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			return false;
		}
	}
	return true;
}


/**
 * Compares the 2 given dates
 * @param date1
 * @param date2
 * @returns {Number}
 */
function compareSelectedDates(date1, date2) {
	if (date1 == undefined || date2 == undefined) {
		return -100;// garbage value
	}
	date1 = date1.split("/");
	date2 = date2.split("/");

	var myDate1 = new Date();
	myDate1.setFullYear(date1[2], date1[1] - 1, date1[0]);
	myDate1.setHours(0, 0, 0, 0);

	var myDate2 = new Date();
	myDate2.setFullYear(date2[2], date2[1] - 1, date2[0]);
	myDate2.setHours(0, 0, 0, 0);
	
	if (myDate1.valueOf() == myDate2.valueOf()) {
		return 0;
	} else if (myDate1 < myDate2) {
		return -1;
	} else if (myDate1 > myDate2) {
		return 1;
	}
}


/**
 * To convert Date into DD/MM/YYYY String
 * 
 * @param dt
 * @returns {String}
 */
function getDateInDDMMYYYY(dt){
	var DD=dt.getDate()+"";
	DD=(DD.length==1)?"0"+DD:DD;//Set 2 digit format 
	var MM=(dt.getMonth()+1)+"";
	MM=(MM.length==1)?"0"+MM:MM;//Set 2 digit format
	var YYYY=dt.getFullYear();
	var DDMMYYYY=DD+"/"+MM+"/"+YYYY;
	return DDMMYYYY;
}


/**
 * Opens a new window for validating an individual collection entry
 * @param url
 */
function collectionDtls(url) {
	popupWindow = window
			.open(
					url,
					'Bill Collection',
					'toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=0, fullscreen=yes, scrollbars=yes, directories=no');
}


/**
 * Logic to navigate through the result set
 * @param navigationLabel
 */
function navigatePage(navigationLabel) {
	var requestedPageNumber = null;
	if (navigationLabel == 'N') {
		requestedPageNumber = getDomElementById('currentPageNumber').value;
		requestedPageNumber ++;
	}
	else if (navigationLabel == 'P') {
		requestedPageNumber = getDomElementById('currentPageNumber').value;
		requestedPageNumber --;
	}
	else if (navigationLabel == 'F') {
		requestedPageNumber = 1;
	}
	else if (navigationLabel == 'L') {
		requestedPageNumber = getDomElementById('numberOfPages').value;
	}
	showProcessing();
	var fromDate = getDomElementById("fromDate");
	var toDate = getDomElementById("toDate");
	var customerId = getSelectedCustomerId();
	var url = "./bulkCollectionValidation.do?submitName=searchCollectionDetailsForBulkValidation&fromDate="
		+ fromDate.value
		+ "&toDate=" + toDate.value
		+ "&custId=" + customerId
		+ "&pageNumber=" + requestedPageNumber
		+ "&navigationLabel=" + navigationLabel;
	jQuery.ajax({
		url : url,
		data : jQuery("#bulkCollectionValidationForm").serialize(),
		success : function(ajaxResponse) {
			populateDataTableWithSearchResults(ajaxResponse);
		}
	});
}


/**
 * Sets the srial numbers on each page.
 * This method uses a logic to calculate the serial numbers. Since the entire list is not fetched at once from the database,
 * this logic has been implemented.
 */
function setSerialNumber(data) {
	var currentPageNumber = data.currentPageNumber;
	var numberOfRecordsPerPage = data.numberOfRecordsPerPage;
	srNo = ((numberOfRecordsPerPage * currentPageNumber) - numberOfRecordsPerPage) + 1;
}


/**
 * In the below method, the button class is yet to be changed. As of now the button gets disabled, but it looks unpleasant.
 * Need to work on that once the functionality is completed 
 * @param data
 */
function setPaginationParameters(data) {
	var currentPageNumber = data.currentPageNumber;
	var numberOfPages = data.numberOfPages;
	var navigationLabel = data.navigationLabel;
	getDomElementById('currentPageNumber').value = currentPageNumber;
	getDomElementById('numberOfPages').value = numberOfPages;
	getDomElementById('checkAll').checked = false;
	getDomElementById('pageNumbers').innerHTML = 'Page ' + currentPageNumber + ' of ' + numberOfPages;
	
	if (numberOfPages == 1) {
		buttonDisabled('previousButton','button_disable');
		buttonDisabled('firstButton','button_disable');
		buttonDisabled('nextButton','button_disable');
		buttonDisabled('lastButton','button_disable');
		return true;
	}
	
	if (navigationLabel == 'S') {
		buttonDisabled('previousButton','button_disable');
		buttonDisabled('firstButton','button_disable');
		enableButton('nextButton','button');
		enableButton('lastButton','button');
	}
	else if (navigationLabel == 'F') {
		buttonDisabled('previousButton','button_disable');
		buttonDisabled('firstButton','button_disable');
		enableButton('nextButton','button');
		enableButton('lastButton','button');
	}
	else if (navigationLabel == 'N') {
		if (numberOfPages == currentPageNumber) {
			buttonDisabled('nextButton','button_disable');
			buttonDisabled('lastButton','button_disable');
		}
		enableButton('previousButton','button');
		enableButton('firstButton','button');
	}
	else if (navigationLabel == 'P') {
		if (currentPageNumber == 1) {
			buttonDisabled('previousButton','button_disable');
			buttonDisabled('firstButton','button_disable');
		}
		enableButton('nextButton','button');
		enableButton('lastButton','button');
	}
	else if (navigationLabel == 'L') {
		enableButton('previousButton','button');
		enableButton('firstButton','button');
		buttonDisabled('nextButton','button_disable');
		buttonDisabled('lastButton','button_disable');
	}
}


/**
 * enableButton
 *@param: btnName : Name of the button
 @return: styleclass ie css to be applied to given Element
 */
function enableButton(btnName,styleclass){
	jQuery("#"+btnName).attr("disabled", false);
	jQuery("#"+btnName).removeClass();
	jQuery("#"+btnName).addClass(styleclass);
}


function resetTotalAmountField() {
	totalSelectedAmount = 0;
	getDomElementById("totalAmount").value = totalSelectedAmount;
}
