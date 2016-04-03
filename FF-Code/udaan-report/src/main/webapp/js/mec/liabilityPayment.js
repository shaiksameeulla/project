var rowCount = 1;
var currentRow = 0;
var data1 = "";
var aaData = "";
var maxRows = 0;
var numPerPage =0;
var sum = 0.00;
$(document).ready(function() {
	var oTable = $('#liability').dataTable({
		"sScrollY" : "190",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"iDisplayLength" : 5,
		// "bRetrieve":true,
		/*
		 * "bProcessing": true, "sAjaxSource":
		 * 'http://localhost:8080/udaan-report/payLiability.do?submitName=getLiabilityDetails&custCode=B002',
		 * "sAjaxDataProp": aaData,
		 */
		// "sAjaxSource": aaData,
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

function getLiabilityCustomers() {
	var regionId = jQuery("#regionId").val();
	// alert(regionId);
	var url = './payLiability.do?submitName=getLiabilitycustomers&regionId='
			+ regionId;
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			populateCust(req);
		}
	});
}

function populateCust(req) {
	var ajaxResp = eval('(' + req + ')');
	for ( var i = 0; i < ajaxResp.length; i++) {
		data[i] = ajaxResp[i].businessName;
		custCodeArr[i] = ajaxResp[i].customerCode;
		custIdArr[i] = ajaxResp[i].customerId;
	}
	var vc = jQuery("#custName").autocomplete(data);
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
	var selectedName = jQuery("#custName").val();
	$.each(data, function(index, customer) {
		if (customer == selectedName) {
			jQuery("#custCode").val(custCodeArr[index]);
			jQuery("#custId").val(custIdArr[index]);
		}
	});

	if (!isNull(selectedName)) {
		var custCode = jQuery("#custCode").val();
		url = './payLiability.do?submitName=getLiabilityDetails&custCode='
				+ custCode;
		// showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				printForPagination(req, 'init');
			}
		});
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
							'<input type="text" value = "" id="consgNo'
									+ rowCount
									+ '" name="to.consgNo" class="txtbox width110" maxlength="12" size="11" readonly="true" />',
							'<input type="text" id="codLcAmt'
									+ rowCount
									+ '" name="to.codLcAmt" class="txtbox width70" size="11" maxlength="10" readonly="true"  />',
							'<input type="text" id="collectedAmt'
									+ rowCount
									+ '" name="to.collectedAmt" class="txtbox width70" size="11" maxlength="10" />',
							'<input type="text" name = "to.paidAmt" id="paidAmt'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70" size="11" onblur = "chkValidPaidAmt(this);"/>\
		 <input type="hidden" id="consgId'
									+ rowCount
									+ '" name="to.consgId" />\
		 <input type="hidden" name="to.isSelect" id ="isSelect'
									+ rowCount
									+ '" />\
		 <input type="hidden" name="to.libilityDetailsId" id ="libilityDetailsId'
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
		if (chqNo.value.length < 6 || chqNo.value.length > 6) {
			alert('Cheque No. length should be 6 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
			isValidReturnVal = false;
		}

	}
	return isValidReturnVal;
}

function saveLiabilityPayment() {
	if (validateHeader() && ischkSelected()) {
		// showProcessing();
		var url = './payLiability.do?submitName=saveOrUpdateLiability';
		jQuery.ajax({
			url : url,
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				callSaveOrUpdateLiability(req);
				// hideProcessing();
			}
		});

	}
}

function saveOrUpdateLiabilityDtlsForNext() {
	var url = './payLiability.do?submitName=saveOrUpdateLiabilityDtlsForNext&clkNext=Y';
	showProcessing();
	jQuery.ajax({
		url : url,
		type: "GET",
		dataType: "json",
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			//setTimeout("printForPagination(req, 'next');", 10);
			 printForPagination(req, 'next');
			// hideProcessing();
		}
	});
}

function ischkSelected() {
	var flag = false;
	// var totalLiabilityAmt=0;
	// var paidAmt=0;
	/*for ( var i = 1; i <= TOTAL_ROWS; i++) {
		var consgNo = getDomElementById("consgNo" + i).value;
		// paidAmt = getDomElementById("paidAmt"+i).value;
		if (getDomElementById("ischecked" + i).checked == true
				&& !isNull(consgNo)) {
			// totalLiabilityAmt+=parseFloat(paidAmt);
			// $("#liabilityAmt").val(totalLiabilityAmt);
			flag = true;
			break;
		}
	}*/
	for (var i = rowCount-numPerPage;i<rowCount;i++) {
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
	var response = ajaxResp;
	if (response != null) {
		alert('Liability Payment saved successfully.');
			buttonDisabled("Submit","btnintform");
			jQuery("#" + "Submit").addClass("btnintformbigdis");
		// document.getElementById('chqNo')
		/*
		 * $("#txnNo").val(response); searchLiability();
		 */
	} else {
		alert('Liability Payment not saved.');
	}
	jQuery.unblockUI();
	$("#New").show();
}

/*
 * function validateHeader() { var custName = getDomElementById("custName"); var
 * mode = jQuery("#paymentMode").val();
 * 
 * var chqNo = getDomElementById("chqNo"); var chqDate =
 * getDomElementById("chqDate"); var bankId = getDomElementById("bankId"); //
 * var amount = getDomElementById("amount");
 * 
 * if (isNull(custName.value)) { alert("Please provide Customer Name");
 * setTimeout(function() { txnNo.focus(); }, 10); return false; } if (mode ==
 * 'C') {
 * 
 * if (isNull(chqNo.value)) { alert("Please provide Cheque No.");
 * setTimeout(function() { chqNo.focus(); }, 10); return false; }
 * 
 * if (isNull(chqDate.value)) { alert("Please provide Cheque Date.");
 * setTimeout(function() { chqDate.focus(); }, 10); return false; }
 * 
 * if (isNull(bankId.value)) { alert("Please provide Bank Name.");
 * setTimeout(function() { bankId.focus(); }, 10); return false; } }
 * 
 * 
 * if(isNull(liabilityAmt.value)){ alert("Please provide Amount.");
 * setTimeout(function(){liabilityAmt.focus();},10); flag= false; }
 * 
 * return true; }
 */
function validateHeader() {
	var errorsData = "";
	var custName = $("#custName").val();

	var chqNo = $("#chqNo").val();
	var chqDate = $("#chqDate").val();
	var bankId = $("#bankId").val();
	var mode = jQuery("#paymentMode").val();
	// var amount = getDomElementById("amount");

	if (isNull(custName)) {
		errorsData = errorsData + "Please provide customer name." + "\n";
	}
	if (mode == 'C') {

		if (isNull(chqNo)) {
			errorsData = errorsData + "Please provide Cheque No." + "\n";
		}

		if (isNull(chqDate)) {
			errorsData = errorsData + "Please provide Cheque date." + "\n";
		}

		if (isNull(bankId)) {
			errorsData = errorsData + "Please provide Bank name." + "\n";
		}
	}
	if (!isNull(errorsData)) {
		alert(errorsData);
		return false;
	} else {
		return true;
	}
}
function searchLiability() {
	var txnNo = $("#txnNo").val();
	if (!isNull(txnNo)) {
		url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
				+ txnNo;
		// showProcessing();
		jQuery.ajax({
			url : url,
			/* data : jQuery("#liabilityPaymentForm").serialize(), */
			success : function(req) {
				printForPagination(req, 'search');
			}
		});
	} else {
		alert("Please provide Transaction Number");
	}
}

function printCallBackLiabilityDetails(ajaxResp, calledFrom) {
	var totalAmt = 0.00;
	// LC/COD consignments are fetched...
	var data = jsonJqueryParser(ajaxResp);
	data1 = data;
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
				// document.getElementById("consgNo"+j).value = listObj.consgNo;
				// alert("consgNo"+j);
				jQuery("#consgNo" + j).val(listObj.consgNo);
				// alert(j+" ----
				// "+jQuery(document.getElementById("consgNo"+j).value));

				jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
				jQuery("#collectedAmt" + j).val(listObj.collectedAmt);
				jQuery("#paidAmt" + j).val(listObj.paidAmt);
				jQuery("#postion" + j).val(j);// Hidden
				jQuery("#consgId" + j).val(listObj.consgId); // Hidden
				jQuery("#libilityDetailsId" + j).val(listObj.libilityDetailsId); // Hidden
				jQuery("#liabilityId").val(data.liabilityId); // Hidden
				totalAmt = parseFloat(totalAmt) + parseFloat(listObj.paidAmt);
				isSaved = true;
				// disable for search
				// disableForSearch(ROW_ID);
				// document.getElementById("consgNo" + j).value =
				// listObj.consgNo;
			}
		}
		// dataTableRecall();
		if (calledFrom == 'search')
			$("#liabilityAmt").val(totalAmt);
		// disableHeader();
	} else {
		alert('Invalid Search');
	}
	hideProcessing();
}
function printForPagination(ajaxResp, calledFrom) {
	var totalAmt = 0.00;
	var data = jsonJqueryParser(ajaxResp);
	maxRows = data.liabilityDetailsTOList.length;
	numPerPage = 3;
	var numPages = Math.ceil(maxRows / numPerPage);
	data1 = data;
	deleteAllRow();
	if (!isNull(data)) {
		hideProcessing();
		if (calledFrom == 'search')
			populateHeaderDetails(data);
		if (calledFrom == 'first' || calledFrom == 'init'
				|| calledFrom == 'search') {
			$("#checkAll").attr("checked",false);
			$("#liabilityAmt").val("0");
			buttonEnabled("Last", "button");
			buttonEnabled("Next", "button");
			buttonDisabled("First", "button");
			buttonDisabled("Previous", "button");
			if(numPages == 1){
				buttonDisabled("Next", "button");
				buttonDisabled("First", "button");
				buttonDisabled("Last", "button");
				buttonDisabled("Previous", "button");
				}
			rowCount = 1;
			currentRow = 0;
			for ( var i = 0; i < numPerPage; i++) {
				var j = i + 1;
				var listObj = data.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					addRow();
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					jQuery("#collectedAmt" + j).val(listObj.collectedAmt);
					jQuery("#paidAmt" + j).val(listObj.paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(data.liabilityId); // Hidden
					//totalAmt = parseFloat(totalAmt) + parseFloat(data.liabilityAmt);
					jQuery("#liabilityStatus").val(data.liabilityStatus);
					isSaved = true;
				}
			}
		}
		if (calledFrom == 'next') {
			$("#checkAll").attr("checked",false);
			buttonEnabled("First", "button");
			buttonEnabled("Previous", "button");
			currentRow = currentRow + numPerPage;
			if(currentRow == ((numPages - 1) * numPerPage))
				buttonDisabled("Next", "button");
			for ( var i = currentRow; i < currentRow + numPerPage; i++) {
				var j = i + 1;
				var listObj = data.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					addRow();
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					jQuery("#collectedAmt" + j).val(listObj.collectedAmt);
					jQuery("#paidAmt" + j).val(listObj.paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(data.liabilityId); // Hidden
					//totalAmt = parseFloat(totalAmt) + parseFloat(data.liabilityAmt);
					isSaved = true;
					// disableHeader();
				}
			}
			hideProcessing();
		}
		if (calledFrom == 'previous') {
			var total =$("#liabilityAmt").val() - sum;
			$("#liabilityAmt").val(total);
			buttonEnabled("Last", "button");
			buttonEnabled("Next", "button");
			currentRow = currentRow - numPerPage;
			if(currentRow == 0)
				buttonDisabled("Previous", "button");
			for ( var i = currentRow; i < currentRow + numPerPage; i++) {
				var j = i + 1;
				var listObj = data.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					rowCount = j;
					addRow();
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					jQuery("#collectedAmt" + j).val(listObj.collectedAmt);
					jQuery("#paidAmt" + j).val(listObj.paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(data.liabilityId); // Hidden
					//totalAmt = parseFloat(totalAmt) + parseFloat(data.liabilityAmt);
					isSaved = true;
				}
			}
		}
		if (calledFrom == 'last') {
			$("#checkAll").attr("checked",false);
			buttonDisabled("Next", "button");
			buttonDisabled("Last", "button");
			buttonEnabled("First", "button");
			buttonEnabled("Previous", "button");
			currentRow = (numPages - 1) * numPerPage;
			for ( var i = currentRow; i < maxRows; i++) {
				var j = i + 1;
				var listObj = data.liabilityDetailsTOList[i];
				if (!isNull(listObj)) {
					rowCount = j;
					addRow();
					jQuery("#consgNo" + j).val(listObj.consgNo);
					jQuery("#codLcAmt" + j).val(listObj.codLcAmt);
					jQuery("#collectedAmt" + j).val(listObj.collectedAmt);
					jQuery("#paidAmt" + j).val(listObj.paidAmt);
					jQuery("#postion" + j).val(j);// Hidden
					jQuery("#consgId" + j).val(listObj.consgId); // Hidden
					jQuery("#libilityDetailsId" + j).val(
							listObj.libilityDetailsId); // Hidden
					jQuery("#liabilityId").val(data.liabilityId); // Hidden
					//totalAmt = parseFloat(totalAmt) + parseFloat(data.liabilityAmt);
					isSaved = true;
				}
			}
		}
	}
	if (calledFrom == 'search'){
		if($("#liabilityStatus").val()=='S')
			disableIfSubmitted();
		$("#liabilityAmt").val(data.liabilityAmt);
	}
}
/** * fill only header details * */
function populateHeaderDetails(liabilityTO) {

	jQuery("#liabilityId").val(liabilityTO.liabilityId);
	jQuery("#txnNo").val(liabilityTO.txNumber);
	jQuery("#liabilityDate").val(liabilityTO.liabilityDate);
	jQuery("#regionId").val(liabilityTO.regionId);
	/*
	 * clearDropDownList("regionId");
	 * addOptionTODropDown("regionId",liabilityTO.regionName,liabilityTO.regionId);
	 */

	jQuery("#custName").val(liabilityTO.custName);
	jQuery("#custCode").val(liabilityTO.custCode);
	jQuery("#custId").val(liabilityTO.custId);

	jQuery("#paymentMode").val(liabilityTO.paymentMode);
	/*
	 * clearDropDownList("paymentModeId");
	 * addOptionTODropDown("paymentModeId",liabilityTO.paymentModeName,liabilityTO.paymentModeId);
	 */
	if (liabilityTO.paymentMode == "C") {
		jQuery("#chqNo").val(liabilityTO.chqNo);
		jQuery("#chqDate").val(liabilityTO.chqDate);
		jQuery("#bankId").val(liabilityTO.bankId);
	}
	checkPaymentMode(getDomElementById("paymentMode"));
	jQuery("#liabilityAmt").val(liabilityTO.liabilityAmt);
	jQuery("#liabilityStatus").val(liabilityTO.liabilityStatus);
	// populateTPDropDown(liabilityTO);
}

function chkValidPaidAmt(paidAmtObj) {

	var paidAmt = parseFloat(paidAmtObj.value);
	var rowId = getRowId(paidAmtObj, "paidAmt");
	var codAmt = parseFloat($("#codLcAmt" + rowId).val());

	if (paidAmt > codAmt) {
		alert("Paid amount cannot be greater than COD amount");
		$("#paidAmt" + rowId).val("");
		$("#paidAmt" + rowId).focus();
		return false;
	} else
		return true;
}

function getchkdConsgId(checkObj) {
	var rowId = getRowId(checkObj, "ischecked");
	jQuery("#isSelect" + rowId).val('Y');
}

function selectAllCheckBox() {
	/*var total = 0;
	var len = document.getElementById('liability').rows.length;
	for ( var i = 1; i < len; i++) {
		if (!isNull($("#paidAmt" + i).val())) {
			total = parseFloat(total) + parseFloat($("#paidAmt" + i).val());
		}
		$("#liabilityAmt").val(total);
	}
	if (!$('#checkAll').attr('checked')) {
		$('#liabilityAmt').val("");
	}*/
	var total =$("#liabilityAmt").val();
	if($('#checkAll').attr('checked')){
	for(var i = rowCount-numPerPage;i<rowCount;i++){
		$('#ischecked' + i).attr("checked",true);
		if (!isNull($("#paidAmt" + i).val())) {
			total = parseFloat(total) + parseFloat($("#paidAmt" + i).val());
		}
		$("#liabilityAmt").val(total);
	}
	}else{
		for(var i = rowCount-numPerPage;i<rowCount;i++){
		$('#ischecked' + i).attr("checked",false);
		if (!isNull($("#paidAmt" + i).val())) {
			total = $("#liabilityAmt").val();
			total = parseFloat(total) - parseFloat($("#paidAmt" + i).val());
		}
		$("#liabilityAmt").val(total);
			}
		}
}
function calculateAmountforPrevious(){
	//var total =$("#liabilityAmt").val();
	sum = 0.00;
	for(var i = rowCount-numPerPage;i<rowCount;i++){
		if($('#ischecked' + i).attr('checked')){
		if (!isNull($("#paidAmt" + i).val())) {
			sum = parseFloat(sum) + parseFloat($("#paidAmt" + i).val());
		}
		}
		//$("#liabilityAmt").val(total);
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
	// for ( var i = rowId; i < maxRows; i++) {
	if ($('#ischecked' + i).attr('checked')) {
		if (!isNull($("#paidAmt" + i).val())) {
			total = parseFloat(total) + parseFloat($("#paidAmt" + i).val());
		}
		$("#liabilityAmt").val(total);
	} else {
		total = parseFloat(total) - parseFloat($("#paidAmt" + i).val());
		$("#liabilityAmt").val(total);
	}
	// }
}
function calculateTotalAmountForCheck(obj) {
	var total = $("#liabilityAmt").val();
	var rowId = getRowId(obj, "ischecked");
	var i = rowId;
	// for ( var i = rowId; i < maxRows; i++) {
	if ($('#ischecked' + i).attr('checked')) {
		if (!isNull($("#paidAmt" + i).val())) {
			total = parseFloat(total) + parseFloat($("#paidAmt" + i).val());
		}
		$("#liabilityAmt").val(total);
	} else {
		total = parseFloat(total) - parseFloat($("#paidAmt" + i).val());
		$("#liabilityAmt").val(total);
	}

	// }
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
	if(confirm("Do you want to Cancel the details?")) {
	window.location.reload();
	}
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
			/*
			 * "aoColumns": [ { "sTitle": "" }, { "sTitle": "Consignment Number" }, {
			 * "sTitle": "COD/LC Amount" }, { "sTitle": "Collected Amount" }, {
			 * "sTitle": "Paid Amount" } ],
			 */
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

function nextPage() {
	calculateAmountforPrevious();
	var headerOK = validateHeader();
	if (headerOK && ischkSelected()) {
		saveOrUpdateLiabilityDtlsForNext();
		disableHeader();
		var txnNo = $("#txnNo").val();
		if (!isNull(txnNo)) {
			if($("#liabilityStatus").val()=='S')
				disableIfSubmitted();
			url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
					+ txnNo;
		} else {
			var custCode = jQuery("#custCode").val();
			url = './payLiability.do?submitName=getLiabilityDetails&custCode='
					+ custCode;
		}
		jQuery.ajax({
			url : url,
/*			type: "POST",
			dataType: "text",*/
			data : jQuery("#liabilityPaymentForm").serialize(),
			success : function(req) {
				printForPagination(req, 'next');
			}
		});
	}

	else {
		hideProcessing();
		return false;
	}
}
function lastPage() {
	var headerOK = validateHeader();
	if (headerOK && ischkSelected()) {
		saveOrUpdateLiabilityDtlsForNext();
		disableHeader();
	var txnNo = $("#txnNo").val();
	if (!isNull(txnNo)) {
		if($("#liabilityStatus").val()=='S')
			disableIfSubmitted();
		url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
				+ txnNo;
	} else {
		var custCode = jQuery("#custCode").val();
		url = './payLiability.do?submitName=getLiabilityDetails&custCode='
				+ custCode;
	}
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			printForPagination(req, 'last');
		}
	});
	}
	else {
		hideProcessing();
		return false;
	}
}
function firstPage() {
	calculateAmountforPrevious();
	var txnNo = $("#txnNo").val();
	if (!isNull(txnNo)) {
		if($("#liabilityStatus").val()=='S')
			disableIfSubmitted();
		url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
				+ txnNo;
	} else {
		var custCode = jQuery("#custCode").val();
		url = './payLiability.do?submitName=getLiabilityDetails&custCode='
				+ custCode;
	}
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			printForPagination(req, 'first');
		}
	});
}
function previousPage() {
	var txnNo = $("#txnNo").val();
	if (!isNull(txnNo)) {
		if($("#liabilityStatus").val()=='S')
			disableIfSubmitted();
		url = './payLiability.do?submitName=searchLiabilityByTxn&txnNo='
				+ txnNo;
	} else {
		var custCode = jQuery("#custCode").val();
		url = './payLiability.do?submitName=getLiabilityDetails&custCode='
				+ custCode;
	}
	jQuery.ajax({
		url : url,
		data : jQuery("#liabilityPaymentForm").serialize(),
		success : function(req) {
			printForPagination(req, 'previous');
		}
	});
}
function disableGrid(){
	/*var rowId = getRowId(obj, "paidAmt");
	 for ( var i = rowId; i < maxRows; i++) {
		 $("#ischecked" + i).attr("readonly", true);
		 $("#consgNo" + i).attr("readonly", true);
		 $("#codLcAmt" + i).attr("readonly", true);
		 $("#collectedAmt" + i).attr("readonly", true);
		 $("#paidAmt" + i).attr("readonly", true);
	 }*/
	 
	 $("#demo").find("input").attr("disabled", "disabled");
	 $("#demo").find("button").attr("disabled", "disabled");
}
function disablePagination(){
	buttonDisabled("Next", "button");
	buttonDisabled("First", "button");
	buttonDisabled("Last", "button");
	buttonDisabled("Previous", "button");
}
function disableIfSubmitted(){
	disableHeader();
	disableGrid();
	disablePagination();
}

function checkOfficeType(){
	if(REGION_TYPE == 'RO'){
		$("#regionId").attr("disabled", true);
	getLiabilityCustomers();
	}
}
