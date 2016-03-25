var rowCount = 1;
var currentRow = 0;
var dataForPagination = "";
var aaData = "";
var maxRows = 0;
var pageNo = 1;
var numPages;
var noOfrowsPerGrid = 0;
// var numPerPage = 0;
var sum = 0.00;
var ERROR_FLAG = "ERROR";
var isSubmitted = false;
var CURRENT_PAGE_AMOUNT = 0;
//var ALL_TOTAL_MINUS_CURRENT_PAGE_AMOUNT = 0;
var ALL_TOTAL_PAGES_AMOUNT = 0;
var PAGE_RELOAD_URL="./payLiability.do?submitName=viewLiabilityPaymentPage";
var ROW_SELECT_COUNTER=0;
$(document).ready(function() {
	numPerPage = getDomElementById("maxPagingRowAllowed").value;
	numPerPage = parseFloat(numPerPage);
	var oTable = $('#liability').dataTable({
		"sScrollY" : "190",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"iDisplayLength" : 5,
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
 * To get liability customers - on load
 */
function getLiabilityCustomers() {
	var url = './payLiability.do?submitName=getLiabilitycustomers';
	showProcessing();
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			populateCust(req);
		}
	});
}
// callback function for populate customer: getLiabilityCustomers
function populateCust(req) {
	var ajaxResp = eval('(' + req + ')');
	if (!isNull(ajaxResp) || ajaxResp.length == 0) {
		for ( var i = 0; i < ajaxResp.length; i++) {
			data[i] = ajaxResp[i].businessName;
			custCodeArr[i] = ajaxResp[i].customerCode;
			custIdArr[i] = ajaxResp[i].customerId;
		}
	} else {
		data = new Array();
	}
	jQuery("#custName").autocomplete(data);
	var officeType = getDomElementById("officeType").value;
	if (officeType == CORP_OFFICE) {
		getLiabilityBankGLs();
	} else {
		hideProcessing();
	}
}

function getLiabilityBankGLs() {
	var url = './payLiability.do?submitName=getLiabilityBankGLs';
	// showProcessing();
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			populateBankGLsOptions(req);
		}
	});
}
// populating bank GL options: getLiabilityBankGLs
function populateBankGLsOptions(req) {
	if (!isNull(req)) {
		var ajaxResp = eval('(' + req + ')');
		getDomElementById("bankId").options.length = 0;
		var obj = getDomElementById("bankId");
		opt = document.createElement('OPTION');
		opt.value = "";
		opt.text = "-- SELECT --";
		obj.options.add(opt);
		for ( var i = 0; i < ajaxResp.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = ajaxResp[i].value;
			opt.text = ajaxResp[i].label;
			obj.options.add(opt);
		}
	}
	hideProcessing();
}

function isRegionSelected() {
	var flag = true;
	var selectedRegion = $('#regionId').val();
	if (isNull(selectedRegion)) {
		alert("Please select atleast 1 region before selecting customer");
		$('#regionId').focus();
		$("#custName").val("");
		flag = false;
	}
	return flag;
}

function getCustCode() {
	var custNm = getDomElementById("custName");
	if (custNm.readOnly == false) {
		var selectedName = jQuery("#custName").val();
		var isCustSearched = false;
		$.each(data, function(index, customer) {
			if (customer == selectedName) {
				jQuery("#custCode").val(custCodeArr[index]);
				jQuery("#custId").val(custIdArr[index]);
				isCustSearched = true;
			}
		});

		if (isCustSearched) {
			var custCode = jQuery("#custCode").val();
			isCustSearched = false;
			var regionId = "";
			var officeType = getDomElementById("officeType").value;
			if (officeType == CORP_OFFICE) {
				regionId = getDomElementById("regionId").value;
			} else {
				regionId = getDomElementById("selectedRegionId").value;
			}
			url = "./payLiability.do?submitName=getLiabilityDetails"
					+ "&custCode=" + custCode + "&regionId=" + regionId;
			showProcessing();
			jQuery.ajax({
				url : url,
				data : jQuery("#liabilityPaymentForm").serialize(),
				success : function(req) {
					//printForPagination(req, 'init');
					populatePageDetails(req);
				}
			});
		}
	}
}

function addRow() {
	$('#liability')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox" id="ischecked'
									+ rowCount
									+ '" name="chkBoxName" tabindex="-1" onclick="getchkdConsgId(this);calculateTotalAmountForCheck(this);"/>',
							'<input type="text" id="bookingDate'
									+ rowCount
									+ '" name="to.bookingDates" class="txtbox width100" readonly="true" tabindex="-1" />',
							'<input type="text" value = "" id="consgNo'
									+ rowCount
									+ '" name="to.consgNo" class="txtbox width110" maxlength="12" size="11" readonly="true" />',
							'<input type="text" id="codLcAmt'
									+ rowCount
									+ '" name="to.codLcAmt" class="txtbox width50" size="11" maxlength="10" readonly="true"  />',
							'<input type="text" id="collectedAmt'
									+ rowCount
									+ '" name="to.collectedAmt" class="txtbox width50" size="11" maxlength="10" readonly="true" />',
									'<input type="text" id="balanceAmount'
									+ rowCount
									+ '" name="to.balanceAmount" class="txtbox width50" size="11" maxlength="10" readonly="true" />',
							'<input type="text" name = "to.paidAmt" id="paidAmt'
									+ rowCount
									+ '" maxlength="10" class="txtbox width50" size="11" onblur = "chkValidPaidAmt(this);" onchange="calcTotalAmtOnChange();" onkeypress="return onlyDecimal(event)"/>\
							 <input type="hidden" id="consgId'
									+ rowCount
									+ '" name="to.consgId" />\
							 <input type="hidden" name="to.isSelect" id ="isSelect'
									+ rowCount
									+ '" />\
									<input type="hidden" name="to.libilityDetailsId" id ="libilityDetailsId'
									+ rowCount
									+ '" />\
									<input type="hidden" name="to.collectionEntriesId" id ="collectionEntriesId'
									+ rowCount
									+ '" />\
							 <input type="hidden" name="to.positions" id="positions'
									+ rowCount + '" value="' + rowCount + '"/>' ]);
	TOTAL_ROWS = rowCount;
	rowCount++;
}

function validateChqNumber(chqNo) {
	var isValidReturnVal = true;
	if (!isNull(chqNo.value)) {
		if (chqNo.value.length < 6 ) {
			alert('Cheque No. length should be 6 to 9 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
			isValidReturnVal = false;
		}

	}
	return isValidReturnVal;
}
/**
 * @deprecated
 */
function saveLiabilityPayment() {
	if (validateHeader() && ischkSelected()) {
		showProcessing();
		if (getDomElementById("regionId").disabled == true) {
			getDomElementById("regionId").disabled = false;
		}
		var url = './payLiability.do?submitName=saveOrUpdateLiability';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				callSaveOrUpdateLiability(req);
			}
		});
	}
}
/**
 * @deprecated
 */
function saveOrUpdateLiabilityDtlsForNext() {
	var url = './payLiability.do?submitName=saveOrUpdateLiabilityDtlsForNext&clkNext=Y';
	showProcessing();
	jQuery.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			printForPagination(req, 'next');
		}
	});
}

function ischkSelected() {
	var flag = false;
	for ( var i = rowCount - noOfrowsPerGrid; i < rowCount; i++) {
		var consgNo = getDomElementById("consgNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true
				&& !isNull(consgNo)) {
			flag = true;
			break;
		}
	}
	if (!flag)
		alert("Please select atleast 1 record to save");
	return flag;
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdateLiability(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
			disableEnableAfterSubmit();
		} else {
			var data = eval('(' + ajaxResp + ')');
			alert("Transaction No : " + data.txNumber
					+ " submitted successfully.");
			getDomElementById("txnNo").value = data.txNumber;
			disableEnableAfterSubmit();
			isSubmitted = true;
		}
	} else {
		alert("ERROR: Liability detail(s) not submitted. Please try again.");
	}
	hideProcessing();
}

/**
 * To disabled or enable field(s) after submit transaction no.
 */
function disableEnableAfterSubmit() {
	// Disabled Submitt Button
	buttonDisabled("Submit", "btnintform");
	jQuery("#" + "Submit").addClass("btnintformbigdis");

	// Hide Search button
	$("#Search").hide();

	// Hide Cheque Date Calender Link Button
	$("#chqDt").hide();

	// Disabled All other fields
	disableAll();

	// Enable New Button
	buttonEnableById("New");

	// Enable First-Previos-Next-Last Button
	//buttonEnabled("First", "button");
	//buttonEnabled("Previous", "button");
	//buttonEnabled("Next", "button");
	//buttonEnabled("Last", "button");
}
function disableEnableAfterSearch() {
	// Disabled Submitt Button
	buttonDisabled("Submit", "btnintform");
	jQuery("#" + "Submit").addClass("btnintformbigdis");
	// Hide Search button
	$("#Search").hide();
	// Hide Cheque Date Calender Link Button
	$("#chqDt").hide();

	

	// Enable New Button
	buttonEnableById("New");
	disableEnableForPageNavi();
	disableHeader();

	
}

/**
 * To disable enable for page navigation
 */
function disableEnableForPageNavi() {
	inputTypeTextDisable();
	checkboxDisable();
}

function validateHeader() {
	var flag = true;
	var region = document.getElementById("regionId");
	var custName = document.getElementById("custName");
	var chqNo = document.getElementById("chqNo");
	var chqDate = document.getElementById("chqDate");
	var bankId = document.getElementById("bankId");
	var mode = document.getElementById("paymentMode").value;
	var focusObj = "";

	var errorMsg = "Please provide : ";
	if (isNull(region.value)) {
		flag = false;
		errorMsg = errorMsg + "Region";
		if (isNull(focusObj)) {
			focusObj = region;
		}
	}
	if (isNull(custName.value)) {
		if (!flag) {
			errorMsg = errorMsg + ", ";
		}
		flag = false;
		errorMsg = errorMsg + "Customer Name";
		if (isNull(focusObj)) {
			focusObj = custName;
		}
	}
	// If payment mode Cheque
	if (mode == 'C') {
		if (isNull(chqNo.value)) {
			if (!flag) {
				errorMsg = errorMsg + ", ";
			}
			flag = false;
			errorMsg = errorMsg + "Cheque Number";
			if (isNull(focusObj)) {
				focusObj = chqNo;
			}
		}
		if (isNull(chqDate.value)) {
			if (!flag) {
				errorMsg = errorMsg + ", ";
			}
			flag = false;
			errorMsg = errorMsg + "Cheque Date";
			if (isNull(focusObj)) {
				focusObj = chqDate;
			}
		}
		if (isNull(bankId.value)) {
			if (!flag) {
				errorMsg = errorMsg + ", ";
			}
			flag = false;
			errorMsg = errorMsg + "Bank Name";
			if (isNull(focusObj)) {
				focusObj = bankId;
			}
		}
	}
	if (!flag) {
		alert(errorMsg);
		setTimeout(function() {
			focusObj.focus();
		}, 10);
	}
	return flag;
}
function searchLiability() {
	var txnNo = $("#txnNo").val();
	if (!isNull(txnNo)) {
		url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
				+ txnNo;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				callBackSearchLiability(req);
			}
		});
	} else {
		alert("Please provide Transaction Number");
	}
}
// Call back function search liability
function callBackSearchLiability(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
			pageClear();
			getDomElementById("txnNo").value = "";
			hideProcessing();
		} else {
			isSubmitted = true;
			getDomElementById('pagination').innerHTML="";
			getDomElementById('paginationSearch').style.display = "inline";
			disableGlobalButton('Submit');
			printForPagination(ajaxResp, 'search');
		}
	} else {
		alert("ERROR: Given Transaction No. does not found.");
		hideProcessing();
	}
}
function printCallBackLiabilityDetails(ajaxResp, calledFrom) {
	var totalAmt = 0.00;
	// LC/COD consignments are fetched...
	var data = jsonJqueryParser(ajaxResp);
	dataForPagination = data;
	deleteAllRow();
	if (!isNull(data)) {
		if (calledFrom == 'search')
			populateHeaderDetails(data);
		else {
			jQuery("#custCode").val(data.custCode);
		}
		if (calledFrom == 'next')
			disableHeader();
		// grid details : setting those values to screen..
		var maxRows = data.liabilityDetailsTOList.length;
		clearGridForSearch(maxRows + 1);
		for ( var i = 0; i < data.liabilityDetailsTOList.length; i++) {
			var j = i + 1;
			var listObj = data.liabilityDetailsTOList[i];
			if (!isNull(listObj)) {
				addRow();
				jQuery("#consgNo" + j).val(listObj.consgNo);

				jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
				var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
						: listObj.collectedAmt;
				jQuery("#collectedAmt" + j).val(collectedAmt);
				var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
						: listObj.paidAmt;
				jQuery("#paidAmt" + j).val(paidAmt);
				jQuery("#postion" + j).val(j);// Hidden
				jQuery("#consgId" + j).val(listObj.consgId); // Hidden
				jQuery("#libilityDetailsId" + j).val(listObj.libilityDetailsId); // Hidden
				jQuery("#liabilityId").val(data.liabilityId); // Hidden
				totalAmt = parseFloat(totalAmt) + parseFloat(listObj.paidAmt);
				isSaved = true;
			}
		}
		if (calledFrom == 'search')
			$("#liabilityAmt").val(totalAmt);
		// disableHeader();
	} else {
		alert('Invalid Search');
	}
	hideProcessing();
}
/**
 * @deprecated
 */
function printForPagination(ajaxResp, calledFrom) {
	CURRENT_PAGE_AMOUNT=0;
	ROW_SELECT_COUNTER=0;
	showProcessing();
	//ALL_TOTAL_MINUS_CURRENT_PAGE_AMOUNT=0;
	if (calledFrom == "init" || calledFrom == "search") {
		var data = jsonJqueryParser(ajaxResp);
		dataForPagination = data;
	}
	maxRows = dataForPagination.liabilityDetailsTOList.length;
	numPages = Math.ceil(maxRows / numPerPage);
	deleteAllRow();
	if (!isNull(dataForPagination)) {
		hideProcessing();
		if (calledFrom == 'search'){
			populateHeaderDetails(dataForPagination);
		}
		if (calledFrom == 'first' || calledFrom == 'init'
				|| calledFrom == 'search') {
			$("#checkAll").attr("checked", false);
			buttonEnabled("Last", "button");
			buttonEnabled("Next", "button");
			buttonDisabled("First", "button");
			buttonDisabled("Previous", "button");
			if (numPages == 1) {
				buttonDisabled("Next", "button");
				buttonDisabled("First", "button");
				buttonDisabled("Last", "button");
				buttonDisabled("Previous", "button");
			}
			rowCount = 1;
			currentRow = 0;
			var gridRowCount = 0;
			for ( var i = 0; i < numPerPage; i++) {
				var j = i + 1;
				var listObj = dataForPagination.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					addRow();
					gridRowCount = gridRowCount + 1;
					checkIfSelected(j);
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#bookingDate" + j).val(listObj.bookingDate);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
							: listObj.collectedAmt;
					jQuery("#collectedAmt" + j).val(collectedAmt);
					var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
							: listObj.paidAmt;
					jQuery("#paidAmt" + j).val(paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#balanceAmount" + j).val(listObj.balanceAmount);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(dataForPagination.liabilityId); // Hidden
					jQuery("#liabilityStatus").val(
							dataForPagination.liabilityStatus);
					calculationCurrentPageAmount(j, paidAmt);
					
					isSaved = true;
					noOfrowsPerGrid = gridRowCount;
				}
			}
		}
		if (calledFrom == 'next') {
			$("#checkAll").attr("checked", false);
			buttonEnabled("First", "button");
			buttonEnabled("Previous", "button");
			currentRow = currentRow + numPerPage;
			if (currentRow == ((numPages - 1) * numPerPage))
				buttonDisabled("Next", "button");
			var gridRowCount = 0;
			for ( var i = currentRow; i < currentRow + numPerPage; i++) {
				var j = i + 1;
				var listObj = dataForPagination.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					addRow();
					gridRowCount = gridRowCount + 1;
					checkIfSelected(j);
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#bookingDate" + j).val(listObj.bookingDate);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
							: listObj.collectedAmt;
					jQuery("#collectedAmt" + j).val(collectedAmt);
					var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
							: listObj.paidAmt;
					jQuery("#paidAmt" + j).val(paidAmt);
					jQuery("#balanceAmount" + j).val(listObj.balanceAmount);// Hidden
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(dataForPagination.liabilityId); // Hidden
					calculationCurrentPageAmount(j, paidAmt);
					
					isSaved = true;
					// disableHeader();
					noOfrowsPerGrid = gridRowCount;
				}
			}
			hideProcessing();
		}
		if (calledFrom == 'previous') {
			buttonEnabled("Last", "button");
			buttonEnabled("Next", "button");
			currentRow = currentRow - numPerPage;
			if (currentRow == 0)
				buttonDisabled("Previous", "button");
			var gridRowCount = 0;
			for ( var i = currentRow; i < currentRow + numPerPage; i++) {
				var j = i + 1;
				var listObj = dataForPagination.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					rowCount = j;
					addRow();
					gridRowCount = gridRowCount + 1;
					checkIfSelected(j);
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#bookingDate" + j).val(listObj.bookingDate);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
							: listObj.collectedAmt;
					jQuery("#collectedAmt" + j).val(collectedAmt);
					var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
							: listObj.paidAmt;
					jQuery("#paidAmt" + j).val(paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#balanceAmount" + j).val(listObj.balanceAmount);// Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(dataForPagination.liabilityId); // Hidden
					calculationCurrentPageAmount(j, paidAmt);
					
					isSaved = true;
					noOfrowsPerGrid = gridRowCount;
				}
			}
		}
		if (calledFrom == 'last') {
			$("#checkAll").attr("checked", false);
			buttonDisabled("Next", "button");
			buttonDisabled("Last", "button");
			buttonEnabled("First", "button");
			buttonEnabled("Previous", "button");
			var gridRowCount = 0;
			currentRow = (numPages - 1) * numPerPage;
			for ( var i = currentRow; i < maxRows; i++) {
				var j = i + 1;
				var listObj = dataForPagination.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					rowCount = j;
					addRow();
					gridRowCount = gridRowCount + 1;
					checkIfSelected(j);
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#bookingDate" + j).val(listObj.bookingDate);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
							: listObj.collectedAmt;
					jQuery("#collectedAmt" + j).val(collectedAmt);
					var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
							: listObj.paidAmt;
					jQuery("#paidAmt" + j).val(paidAmt);
					jQuery("#balanceAmount" + j).val(listObj.balanceAmount);// Hidden
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(dataForPagination.liabilityId); // Hidden
					calculationCurrentPageAmount(j, paidAmt);
					
					isSaved = true;
					noOfrowsPerGrid = gridRowCount;
				}
			}
		}
		// Recalculate total amount
		calcTotalAmtOnChange();
	}
	if (calledFrom == 'search') {
		if ($("#liabilityStatus").val() == 'S') {
			// disableIfSubmitted();
			//disableEnableAfterSubmit();
			disableEnableAfterSearch();
		}
		$("#liabilityAmt").val(dataForPagination.liabilityAmt);
	}

	// To keep field disabled or enable if transaction submitted.
	if (isSubmitted) {
		disableEnableForPageNavi();
	}

	// Explicit set header amount if txn. submitted
	if ($("#liabilityStatus").val() == 'S') {
		$("#liabilityAmt").val(dataForPagination.liabilityAmt);
	}else{
		var totalAmntforLib=ALL_TOTAL_PAGES_AMOUNT;
		$("#liabilityAmt").val(parseFloat(totalAmntforLib).toFixed(2));
	}
	autoSelectForCheckAll();
	hideProcessing();
}
/** * fill only header details * */
function populateHeaderDetails(liabilityTO) {

	jQuery("#liabilityId").val(liabilityTO.liabilityId);
	jQuery("#txnNo").val(liabilityTO.txNumber);
	jQuery("#liabilityDate").val(liabilityTO.liabilityDate);
	jQuery("#regionId").val(liabilityTO.regionId);

	jQuery("#custName").val(liabilityTO.custName);
	jQuery("#custCode").val(liabilityTO.custCode);
	jQuery("#custId").val(liabilityTO.custId);

	jQuery("#paymentMode").val(liabilityTO.paymentMode);

	if (liabilityTO.paymentMode == "C") {
		jQuery("#chqNo").val(liabilityTO.chqNo);
		jQuery("#chqDate").val(liabilityTO.chqDate);
		// Populate Bank Name in drop down
		populateDropDownVal("bankId", liabilityTO.bankId, liabilityTO.bankName);
		jQuery("#bankId").val(liabilityTO.bankId);
	}
	checkPaymentMode(getDomElementById("paymentMode"));
	jQuery("#liabilityAmt").val(liabilityTO.liabilityAmt);
	jQuery("#liabilityStatus").val(liabilityTO.liabilityStatus);
}

function chkValidPaidAmt(paidAmtObj) {
	var flag=true;
	
	var paidAmt = 0;
	if(!isNull(paidAmtObj.value)){
		paidAmt= parseFloat(paidAmtObj.value);
		paidAmtObj.value=paidAmt.toFixed(2);
	}
	var rowId = getRowId(paidAmtObj, "paidAmt");
	var codAmt = parseFloat($("#codLcAmt" + rowId).val());

	if (paidAmt > codAmt) {
		alert("Paid amount cannot be greater than Collected/Balance amount");
		$("#paidAmt" + rowId).val("");
		$("#paidAmt" + rowId).focus();
		calcTotalAmtOnChange();
		flag=false;
	} 
	if(isNull(paidAmt) || paidAmt<0){
		$('#ischecked' + rowId).attr('checked',false);
	}
	return flag;
}

function getchkdConsgId(checkObj) {
	var rowId = getRowId(checkObj, "ischecked");
	// If checked then paste COD/LC amount as PAID Amount else make it blank
	if ($('#ischecked' + rowId).attr('checked')) {
		getDomElementById("paidAmt" + rowId).value = getDomElementById("balanceAmount"
				+ rowId).value;
		
		jQuery("#isSelect" + rowId).val('Y');
		++ROW_SELECT_COUNTER;
	} else {
		getDomElementById("paidAmt" + rowId).value = "";
		jQuery("#isSelect" + rowId).val('N');
		getDomElementById("checkAll").checked = false;
		--ROW_SELECT_COUNTER;
	}
	autoSelectForCheckAll();
	calcTotalAmtOnChange();
}

function selectAllCheckBox() {
	var isCheckAll = false;
	$("#liabilityAmt").val("0.00");
	if ($('#checkAll').attr('checked')) {
		isCheckAll = true;
	}
	ROW_SELECT_COUNTER=0;
	for ( var i = 1; i < rowCount; i++) {
		var ischeckedObj = getDomElementById("ischecked" + i);
		if (!isNull(ischeckedObj)) {
			$('#ischecked' + i).attr("checked", isCheckAll);
			if (isCheckAll)
				calculateTotalAmountForCheck(ischeckedObj);

			// If checked then paste COD/LC amount as PAID Amount else make it
			// blank
			if ($('#ischecked' + i).attr('checked')) {
				getDomElementById("paidAmt" + i).value = getDomElementById("balanceAmount"
						+ i).value;
				jQuery("#isSelect" + i).val('Y');
				++ROW_SELECT_COUNTER;
			} else {
				getDomElementById("paidAmt" + i).value = "";
				jQuery("#isSelect" + i).val('N');
			}
			calcTotalAmtOnChange();
		}
	}
}
function calculateAmountforPrevious() {
	sum = 0.00;
	for ( var i = rowCount - noOfrowsPerGrid; i < rowCount; i++) {
		if ($('#ischecked' + i).attr('checked')) {
			if (!isNull($("#paidAmt" + i).val())) {
				sum = parseFloat(sum) + parseFloat($("#paidAmt" + i).val());
			}
		}
	}
}

function reload() {
	clearHeaderDetails();
	clearGrid();
}

function clearGrid() {
	var oTable = $('#liability').dataTable();
	for ( var j = 0; j < TOTAL_ROWS; j++) {
		oTable.fnDeleteRow(j);
	}
	rowCount = 1;
}

function clearGridForSearch(maxRows) {
	var oTable = $('#liability').dataTable();
	for ( var j = 0; j < maxRows; j++) {
		oTable.fnDeleteRow(j);
	}
	rowCount = 1;
}

function checkPaymentMode(modeObj) {
	var paymentMode = modeObj.value;
	if (paymentMode == "F") {
		enableOrDisablePaymentDtls(true);
		document.getElementById('chqNo').value = "";
		document.getElementById('chqDate').value = "";
		document.getElementById('bankId').value = "";
		$("#chqDt").hide();
	} else {// "C"
		enableOrDisablePaymentDtls(false);
		$("#chqDt").show();
	}
}

function enableOrDisablePaymentDtls(flag) {
	document.getElementById('chqNo').disabled = flag;
	document.getElementById('chqDate').disabled = flag;
	document.getElementById('bankId').disabled = flag;
}

function clearHeaderDetails() {
	jQuery("#liabilityId").val("");
	jQuery("#txnNo").val("");
	jQuery("#liabilityDate").val(TODAYS_DATE);
	jQuery("#regionId").val(0);
	jQuery("#custName").val("");
	jQuery("#custCode").val("");
	jQuery("#paymentMode").val(0);
	jQuery("#chqNo").val("");
	jQuery("#chqDate").val("");
	jQuery("#liabilityAmt").val("");
	jQuery("#liabilityStatus").val("");
}

/**
 * validate the TxNo. format i.e. Office Code + Tx. Code + 6 digit (MUMB LP
 * 123456)
 * 
 * @param domElement
 * @returns {Boolean}
 */
function validateTxNo(domElement) {
	var domValue = domElement.value.trim();
	if (!isNull(domValue)) {
		if (domValue.length == 12) {
			var officeCode = getDomElementById("loginOfficeCode").value;
			var offCodePattern = /^[A-Z0-9]{0,4}$/;
			var txCode = "LP";// LP
			var numpattern = /^[0-9]{6,20}$/;
			if (offCodePattern.test(domValue.substring(0, 4).toUpperCase())
					&& domValue.substring(4, 6).toUpperCase() == txCode
							.toUpperCase()
					&& numpattern.test(domValue.substring(6))) {
				if (domValue.substring(0, 4).toUpperCase() == officeCode
						.toUpperCase()) {
					return true;
				} else {
					alert("Transaction number does not belong to this office");
					domElement.value = "";
					setTimeout(function() {
						domElement.focus();
					}, 10);
				}
			} else {
				alert("Transaction number format is not correct");
				domElement.value = "";
				setTimeout(function() {
					domElement.focus();
				}, 10);
			}
		} else {
			alert("Transaction number should be 12 characters");
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
		}
	}
}
function calculateTotalAmount(obj) {
	var total = $("#liabilityAmt").val();
	var rowId = getRowId(obj, "paidAmt");
	var i = rowId;
	if ($('#ischecked' + i).attr('checked')) {
		if (!isNull($("#paidAmt" + i).val())) {
			total = parseFloat(total) + parseFloat($("#paidAmt" + i).val());
		}
		//$("#liabilityAmt").val(total);
	} else {
		total = parseFloat(total) - parseFloat($("#paidAmt" + i).val());
		
		//$("#liabilityAmt").val(total);
	}
//	total=total+ALL_TOTAL_PAGES_AMOUNT;
	$("#liabilityAmt").val(total);
}
function calculateTotalAmountForCheck(obj) {
	/*
	 * var total = $("#liabilityAmt").val(); total = (isEmptyAmt(total)) ? 0 :
	 * parseFloat(total).toFixed(2); var rowId = getRowId(obj, "ischecked"); var
	 * i = rowId; var paidAmt = $("#paidAmt" + i).val(); if
	 * (isEmptyAmt(paidAmt)) { paidAmt = 0; } paidAmt =
	 * parseFloat(paidAmt).toFixed(2); if ($('#ischecked' + i).attr('checked')) {
	 * total = parseFloat(total) + parseFloat(paidAmt);
	 * $("#liabilityAmt").val(parseFloat(total).toFixed(2)); } else { total =
	 * parseFloat(total) - parseFloat(paidAmt);
	 * $("#liabilityAmt").val(parseFloat(total).toFixed(2)); }
	 */
}
function calcTotalAmtOnChange() {
	var total = 0;
	total = (isEmptyAmt(total)) ? 0 : parseFloat(total).toFixed(2);
	for ( var i = 1; i < rowCount; i++) {
		//var paidAmt = $("#paidAmt" + i).val();
		var paidAmt = $("#paidAmt" + i).val();
		if (isEmptyAmt(paidAmt)) {
			paidAmt = 0;
		}
		paidAmt = parseFloat(paidAmt).toFixed(2);
		if ($('#ischecked' + i).attr('checked')) {
			total = parseFloat(total) + parseFloat(paidAmt);
		}
	}
	//alert("calculationCurrentPageAmount"+CURRENT_PAGE_AMOUNT);
	total=total+ALL_TOTAL_PAGES_AMOUNT-CURRENT_PAGE_AMOUNT;
	//alert("total"+total);
	$("#liabilityAmt").val(parseFloat(total).toFixed(2));
	
}
function deleteAllRow() {
	var table = getDomElementById("liability");
	var tableRowCount = table.rows.length;
	var oTable = $('#liability').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);

	}
}
function cancelDetails() {
	if (confirm("Do you want to Cancel the details?")) {
		pageClear();
	}
}
function pageClear(){
	window.location=PAGE_RELOAD_URL;
}
function disableHeader() {
	$('.formTable').find('input, button, select').attr("readonly", true);
	$('.formTable').find('button').attr("disabled", true);
	$('#chqDt').removeAttr('href');
	$('.formTable').css("background-color", "#DBDAD8");
}
function enableHeader() {
	$('.formTable').find('input, button, select').attr("disabled", false);
}

function dataTableRecall() {
	$(document).ready(function() {
		var oTable = $('#liability').dataTable({
			"sScrollY" : "190",
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"bScrollCollapse" : false,
			"bSort" : false,
			"bInfo" : false,
			"bPaginate" : true,
			"bRetrieve" : true,
			"bProcessing" : true,
			"iDisplayLength" : 5,
			"sAjaxSource" : aaData,
			"sAjaxDataProp" : "aaData",
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
}

/**
 * @deprecated
 */
function nextPage() {
	// calculateAmountforPrevious();
	var headerOK = validateHeader();
	if (headerOK) {
		pageNo++;
		saveDataForPagination();
		// saveOrUpdateLiabilityDtlsForNext();
		// disableHeader();
		printForPagination(dataForPagination, 'next');
	}

	else {
		hideProcessing();
		return false;
	}
}
/**
 * @deprecated
 */
function lastPage() {
	var headerOK = validateHeader();
	if (headerOK) {
		pageNo = numPages;
		saveDataForPagination();
		// saveOrUpdateLiabilityDtlsForNext();
		// disableHeader();
		printForPagination(dataForPagination, 'last');
	} else {
		hideProcessing();
		return false;
	}
}
/**
 * @deprecated
 */
function firstPage() {
	pageNo = 1;
	saveDataForPagination();
	printForPagination(dataForPagination, 'first');
}
/**
 * @deprecated
 */
function previousPage() {
	pageNo--;
	saveDataForPagination();
	printForPagination(dataForPagination, 'previous');
}
function disableGrid() {
	$("#demo").find("input").attr("disabled", "disabled");
	$("#demo").find("button").attr("disabled", "disabled");
}
function disablePagination() {
	buttonDisabled("Next", "button");
	buttonDisabled("First", "button");
	buttonDisabled("Last", "button");
	buttonDisabled("Previous", "button");
}
function disableIfSubmitted() {
	disableHeader();
	disableGrid();
	disablePagination();
}

function checkOfficeType() {
	if (REGION_TYPE == 'RO') {
		$("#regionId").attr("disabled", true);
		getLiabilityCustomers();
	} else {
		var regionId = getDomElementById("regionId").value;
		if (!isNull(regionId)) {
			getLiabilityCustomers();
		}
	}
}
/***
 * To check empty amount
 * 
 * @param domValue
 * @returns {Boolean}
 */
function isEmptyAmt(domValue) {
	if (!isNull(domValue)) {
		domValue = parseFloat(domValue).toFixed(2);
		if (domValue == "0.00") {
			return true;
		}
	} else if (isNull(domValue)) {
		return true;
	}
	return false;
}
/**
 * @deprecated
 */
function saveDataForPagination() {
	for ( var i = rowCount - noOfrowsPerGrid; i < rowCount; i++) {
		dataForPagination.liabilityDetailsTOList[i - 1].codLcAmt = getDomElementById("codLcAmt"
				+ i).value;
		var collectedAmt = (isEmptyAmt(getDomElementById("collectedAmt" + i).value)) ? ""
				: getDomElementById("collectedAmt" + i).value;
		dataForPagination.liabilityDetailsTOList[i - 1].collectedAmt = collectedAmt;
		var paidAmt = (isEmptyAmt(getDomElementById("paidAmt" + i).value)) ? ""
				: getDomElementById("paidAmt" + i).value;
		dataForPagination.liabilityDetailsTOList[i - 1].paidAmt = paidAmt;
		if (getDomElementById("ischecked" + i).checked) {
			dataForPagination.isChecked[i - 1] = "Y";
		} else {
			dataForPagination.isChecked[i - 1] = "";
		}
	}
	populateAllPagesAmount();
}

/**
 * @deprecated
 */
function checkIfSelected(i) {
	if (dataForPagination.isChecked[i - 1] == "Y") {
		getDomElementById("ischecked" + i).checked = true;
		++ROW_SELECT_COUNTER;
	} else {
		getDomElementById("ischecked" + i).checked = false;
	}
}

/**
 * On change of region screen should get reload
 */
function reloadPage(obj) {
	if (!isNull(prevRegion) && !isNull(obj.value)) {
		var lpUrl = "./payLiability.do?submitName=viewLiabilityPaymentPage";
		submitLiabilityPage(lpUrl);
	} else {
		prevRegion = obj.value;
		if (!isNull(obj.value)) {
			getLiabilityCustomers();
		} else {
			var lpUrl = "./payLiability.do?submitName=viewLiabilityPaymentPage";
			data = new Array();
			submitLiabilityPage(lpUrl);
		}
	}
}

/**
 * To submit liability page
 * 
 * @param lpUrl
 */
function submitLiabilityPage(lpUrl) {
	document.liabilityPaymentForm.action = lpUrl;
	document.liabilityPaymentForm.submit();
}

/**
 * To populate drop down value and label
 * 
 * @param domId
 * @param val
 * @param label
 */
function populateDropDownVal(domId, val, label) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	optionSelectType.value = val;
	optionSelectType.text = label;
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}
}
/**
 * @deprecated
 */
function populateAllPagesAmount(){
	ALL_TOTAL_PAGES_AMOUNT=0;
	var liabilitiesList=dataForPagination.liabilityDetailsTOList;
	
	jQuery.each(liabilitiesList, function( index, detailsTO ){
		var checkd=dataForPagination.isChecked[index];
		if(!isNull(checkd) && checkd=='Y'){
			if(!isEmptyAmt(detailsTO.paidAmt)){
				ALL_TOTAL_PAGES_AMOUNT=ALL_TOTAL_PAGES_AMOUNT+parseFloat(detailsTO.paidAmt);
			}
		}
	});
	//alert("ALL_TOTAL_PAGES_AMOUNT" +ALL_TOTAL_PAGES_AMOUNT);
	var totalAll=ALL_TOTAL_PAGES_AMOUNT;
	$("#liabilityAmt").val(parseFloat(totalAll).toFixed(2));
}

/**
 * @deprecated
 */
function calculationCurrentPageAmount(rowIdentifier,paidAmt1){
	if(getDomElementById("ischecked" + rowIdentifier).checked){
		CURRENT_PAGE_AMOUNT=parseFloat(CURRENT_PAGE_AMOUNT)+parseFloat(paidAmt1);
	}
	
}
/**
 * @deprecated
 */
function pageNavigate() {
	// calculateAmountforPrevious();
	var headerOK = validateHeader();
	if (headerOK) {
		pageNo++;
		saveDataForPagination();
		// saveOrUpdateLiabilityDtlsForNext();
		// disableHeader();
		printForPagination(dataForPagination, 'next');
	}

	else {
		hideProcessing();
		return false;
	}
}

function populatePageDetails(ajaxResp) {
	CURRENT_PAGE_AMOUNT=0;
	ROW_SELECT_COUNTER=0;
	hideProcessing();
	var currentPage=0;
	var totalPages=0;
	deleteAllRow();
	//ALL_TOTAL_MINUS_CURRENT_PAGE_AMOUNT=0;
	$('#checkAll').attr('checked',false);
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		var error = data[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			pageClear();
			return ;
		} 
		if(!isNull(data)){
			getDomElementById('paginationSearch').innerHTML="";
			dataForPagination = data;
			jQuery("#totalNoPages").val(dataForPagination.totalNoPages);
			jQuery("#currentPageNumber").val(dataForPagination.currentPageNumber);
			jQuery("#liabilityAmt").val(dataForPagination.liabilityAmt);
			
			
			numPerPage = dataForPagination.liabilityDetailsTOList.length;

			deleteAllRow();
			TOTAL_ROWS=numPerPage;
			rowCount = 1;
			currentRow = 0;
			var gridRowCount = 0;
			currentPage=dataForPagination.currentPageNumber;
			CURRENT_PAGE_AMOUNT=dataForPagination.currentPageAmount;
			ALL_TOTAL_PAGES_AMOUNT=dataForPagination.liabilityAmt;
			totalPages=dataForPagination.totalNoPages;
			for ( var i = 0; i < numPerPage; i++) {
				var j = i + 1;
				var listObj = dataForPagination.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					addRow();
					gridRowCount = gridRowCount + 1;

					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#bookingDate" + j).val(listObj.bookingDate);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					var collectedAmt = (isEmptyAmt(listObj.collectedAmt)) ? ""
							: listObj.collectedAmt;
					jQuery("#collectedAmt" + j).val(collectedAmt);
					var paidAmt = (isEmptyAmt(listObj.paidAmt)) ? ""
							: listObj.paidAmt;
					jQuery("#paidAmt" + j).val(paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#isSelect" + j).val(
							listObj.isSelect); // Hidden
					jQuery("#liabilityId").val(dataForPagination.liabilityId); // Hidden
					//alert(listObj.balanceAmount);
					jQuery("#balanceAmount"+j).val(listObj.balanceAmount); // Hidden
					jQuery("#collectionEntriesId"+j).val(listObj.collectionEntriesId); // Hidden
					
					
					jQuery("#liabilityStatus").val(
							dataForPagination.liabilityStatus);
					autoSelectRowIfRequired(listObj, j);

				}//End of Object
			}//end of For Loop
			enableGlobalButton("Next", "button");
			enableGlobalButton("Last", "button");
			enableGlobalButton("First", "button");
			enableGlobalButton("Previous", "button");
			if(currentPage==0){
				//previous button disable
				disableGlobalButton("Previous", "button");
			}
			if(currentPage ==(totalPages-1)){
				//disable all buttons
				disableGlobalButton("Next", "button");
				disableGlobalButton("Last", "button");
				disableGlobalButton("First", "button");
				disableGlobalButton("Previous", "button");
				if(currentPage!=0){
					enableGlobalButton("First", "button");
					enableGlobalButton("Previous", "button");
				}
			}

		}//end of Json parse 
	}//end of Ajax resp null check
	autoSelectForCheckAll();
	hideProcessing();
}
function pageNavigation(navigationType){
	if (validateHeader() && validatePageGrid()) {
		showProcessing();
		jQuery("#navigationType").val(navigationType);
		var url = './payLiability.do?submitName=getLiabilityDetailsForNavigation';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				hideProcessing();
				populatePageDetails(req);
			}
		});
	}
}
function autoSelectRowIfRequired(libObject,i) {
	if (libObject.isSelect == "Y") {
		//alert(libObject.isSelect);
		getDomElementById("ischecked" + i).checked = true;
		++ROW_SELECT_COUNTER;
	} else {
		getDomElementById("ischecked" + i).checked = false;
	}
}
function isValidTOSave() {
	var flag=true;
	var liabltyAmt=jQuery("#liabilityAmt").val();
	if(isNull(liabltyAmt) || parseFloat(liabltyAmt)<0){
		flag=false;
		alert("Please select atleast 1 record to save");
		
	}
	return flag;
}
function save() {
	if (validateHeader() && isValidTOSave() && validatePageGrid()) {
		showProcessing();
		if (getDomElementById("regionId").disabled == true) {
			getDomElementById("regionId").disabled = false;
		}
		var url = './payLiability.do?submitName=save';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				callSaveOrUpdateLiability(req);
			}
		});
	}
}
function validatePageGrid(){
var gridFlag=true;
	var chkBoxDom=document.getElementsByName("chkBoxName");
	var consgNumDom=document.getElementsByName("to.consgNo");
	var codDom=document.getElementsByName("to.codLcAmt");
	var collectedAmtDom=document.getElementsByName("to.collectedAmt");
	var consgIdDom=document.getElementsByName("to.consgId");
	var paidAmtDom=document.getElementsByName("to.paidAmt");
	var balanceDom=document.getElementsByName("to.balanceAmount");
	if(consgNumDom == null || consgNumDom.length==0){
		alert("There is no data to navigate");
		return false;
	}

	if(chkBoxDom!=null && chkBoxDom.length>=1 ){
		for(var i=0;i<chkBoxDom.length;i++){
			if(chkBoxDom[i].checked){
				var forTheRow=" at the line item :"+(i+1);
				var codAmt=codDom[i].value;
				var collectdAmt=collectedAmtDom[i].value;
				var consgId=consgIdDom[i].value;
				var paidAmt=paidAmtDom[i].value;
				var balanceAmt=balanceDom[i].value;
				if(isNull(consgId)){
					chkBoxDom[i].checked =false;
					alert("selected line item is invalid "+forTheRow);
					return false;
				}else if(isNull(consgNumDom[i].value)){
					chkBoxDom[i].checked =false;
					alert(" consignment number can not be empty "+forTheRow);
					return false;
				}else if(isNull(codAmt)){
					alert("Cod/LC amount should not be empty "+forTheRow);
					return false;
				}else if(isNull(collectdAmt)){
					alert("Collected amount should not be empty "+forTheRow);
					return false;
				}else if(isNull(paidAmt)){
					alert("Paid amount should not be empty "+forTheRow);
					return false;
				}else if(parseFloat(paidAmt)>parseFloat(collectdAmt)){
					alert("Paid Amount can not be greater than Collected amount "+forTheRow);
					setFocusOnElmentByDom(paidAmtDom[i]);
					paidAmtDom[i].value='';
					return false;
				}else if(parseFloat(paidAmt)>parseFloat(balanceAmt)){
					alert("Paid Amount can not be greater than balance amount "+forTheRow);
					setFocusOnElmentByDom(paidAmtDom[i]);
					paidAmtDom[i].value='';
					return false;
				}
				
			}
			
		}
	}else{
		alert("There is no data to save");
		return false;
	}
	return gridFlag;

	
}
function autoSelectForCheckAll(){
	var consgNumDom=document.getElementsByName("to.consgNo");
	if(consgNumDom!=null && !isNull(ROW_SELECT_COUNTER) && ROW_SELECT_COUNTER == consgNumDom.length){
		getDomElementById("checkAll").checked = true;
	}
}