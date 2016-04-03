var rowCount = 1;
var eachConsgWeightArr = new Array();
var ERROR_FLAG="ERROR";

// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
$(document).ready( function () {
	var oTable = $('#collectionDetails').dataTable( {
		"sScrollY": "120",
		"sScrollX": "100%",
		"sScrollXInner":"110%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );


/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		responseText = jsonJqueryParser(ObjeResp);
	}catch(e){
		
	}
	if(!isNull(responseText)){
		var error = responseText[ERROR_FLAG];
		if(!isNull(error)){
		alert(error);
		return true;
		}
	}
	return false;
}

function loadDefaultObjects() {
	checkCollectionMode(getDomElementById("collectionModeId"));
	addCollectionRow();
	loadCustomerName();
	getDomElementById("custName").focus();
}

function addCollectionRow() {

	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
							 serialNo+ '<input type="hidden" name="to.srNos" value ='+serialNo+' readonly="true"/><input type="hidden" name="to.collectionEntryIds" id="collectionEntryIds' + rowCount+'"/>',
							/*'<select id="CollectionAgainsts'
									+ rowCount
									+ '" name="to.CollectionAgainsts"  class="selectBox width100" onblur=""> <option value="0">Bill</option> </select>',*/
							'<select  id="collectionAgainsts'
									+ rowCount
									+ '" name="to.collectionAgainsts"  value = ""  class="selectBox width120" onchange= "collectionAgainstView(this);" onkeypress="return callEnterKey(event,getDomElementById(\'billNos'+ rowCount+ '\'));"/><option value="B">BILL</option></select>',
									
									'<select id="billNos' + rowCount+ '" name="to.billNos" class="selectBox width120" onchange="getBillAmount(this);" onkeypress="return callEnterKey(event,getDomElementById(\'billAmounts'+ rowCount+ '\'));"/><option value="">---Select---</option></select>',
									
									
							'<input type="text" id="billAmounts'
									+ rowCount
									+ '" name="to.billAmounts" class="txtbox width70" size="11" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'receivedAmounts'+ rowCount+ '\');"/>',
							'<input type="text" name = "to.receivedAmounts" id="receivedAmounts'+ rowCount+ '" class="txtbox width70" size="11" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'tdsAmounts'+ rowCount+ '\');" onblur = "calculateTotalAmount(this,\'R\');"/>',
							'<input type="text" name = "to.tdsAmounts" id="tdsAmounts'
									+ rowCount
									+ '"  class="txtbox width70" size="11"  onkeypress="return onlyDecimalAndEnterKeyNav(event,\'deductions'+ rowCount+ '\');"  onblur = "calculateTotalAmount(this,\'T\');"  />',
							'<input type="text" name = "to.deductions" id="deductions'
									+ rowCount
									+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'remarks'+ rowCount+ '\');" onblur = "calculateTotalAmount(this,\'D\');" />',
							'<input type="text" name = "to.totals" id="totals'
									+ rowCount
									+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'remarks'+ rowCount+ '\');"/>',
							'<input type="text" name = "to.remarks" id="remarks'
									+ rowCount
									+ '"  class="txtbox width170" size="11" onblur = "addNewRow(this);" />',]);
	rowCount++;
	serialNo++;
	//updateSrlNo('collectionDetails');
	//onfocus="getItemList(this);"
	getItemList(getDomElementById("collectionAgainsts"+(rowCount-1)));
	getBillDeatils(rowCount-1);
}

function addValidateBillCollectionRow() {

	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
							 serialNo+ '<input type="hidden" name="to.srNos" value ='+serialNo+' readonly="true"/><input type="hidden" name="to.collectionEntryIds" id="collectionEntryIds' + rowCount+'"/>',
							'<select  id="collectionAgainsts'+ rowCount+ '" name="to.collectionAgainsts"  value = ""  class="selectBox width145" onfocus="getItemList(this);" onchange= "collectionAgainstView(this);"/><option value="B">BILL</option></select>',
							'<input type="text" id="billNos'+ rowCount+ '" name="to.billNos" class="txtbox width70" size="11" onchange="getBillAmount(this);" />',
							'<input type="text" id="billAmounts'+ rowCount+ '" name="to.billAmounts" class="txtbox width70" size="11" />',
							'<input type="text" name = "to.receivedAmounts" id="receivedAmounts'+ rowCount+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur = "calculateTotalAmount(this,\'R\');"/>',
							'<input type="text" name = "to.correctedRecvAmount" id="correctedRecvAmount'+ rowCount+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur = "calculateTotalAmount(this,\'R\');"/>',
							'<input type="text" name = "to.tdsAmounts" id="tdsAmounts'+ rowCount+ '"  class="txtbox width70" size="11"  onkeypress="return onlyDecimal(event);" onblur = "calculateTotalAmount(this,\'T\');"  />',
							'<input type="text" name = "to.correctedTDS" id="correctedTDS'+ rowCount+ '"  class="txtbox width70" size="11"  onkeypress="return onlyDecimal(event);" onblur = "calculateTotalAmount(this,\'T\');"  />',
							'<input type="text" name = "to.totals" id="totals'+ rowCount+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "to.remarks" id="remarks'+ rowCount+ '"  class="txtbox width170" size="11" />',]);
	rowCount++;
	serialNo++;
	//updateSrlNo('collectionDetails');
}


function loadBillValidateDefaultObjects() {
	var isCorrectionParam = document.getElementById("isCorrectionParam").value;
	var collectionModeId = document.getElementById("collectionModeId").value;
	if(!isNull(isCorrectionParam) && isCorrectionParam != 'Y' && BC_MODE != collectionModeId){
		inputDisable();
	    dropdownDisable();
	} 
	if(collStatus == 'V'){
		inputDisable();
	    dropdownDisable();
	} 
}

function addNewRow(domObj) {
	var rowNo = getRowId(domObj,"remarks");
	var nextRow = parseInt(rowNo) + 1;
	if (!isNull(document.getElementById("receivedAmounts" + rowNo).value)) {
		addCollectionRow();
		document.getElementById("collectionAgainsts" + nextRow).focus();
	}
}
function updateSrlNo(tableID) {
	try {
		var table = document.getElementById(tableID);
		for ( var i = 1; i < rowCount; i++) {
			var row = table.rows[i];
			var val = row.cells[1];
			val.innerHTML = i;			
		}
	} catch (e) {
		//alert(e);
	}
}

//Ajax call to populate list of CollectionAgainst
var collectionAgainstsgridId;
function getItemList(domObj){
	collectionAgainstsgridId = getRowId(domObj,"collectionAgainsts");
	var url="./billCollection.do?submitName=getCollectionAgainstDtls";
	ajaxCallWithoutForm(url, ajxRespForCollectionAgainsts);
}

function ajaxCallWithoutForm(pageurl, ajaxResponse) {
	progressBar();
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		},
	  async: false
	});
	jQuery.unblockUI();
}

//Ajax call response to populate list of CollectionAgainst
function ajxRespForCollectionAgainsts(ajaxResp){
	if (!isNull(ajaxResp)) {
		var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		var content = document.getElementById('collectionAgainsts'+collectionAgainstsgridId);
		content.innerHTML = "";
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.stdTypeCode;
			option.appendChild(document.createTextNode(this.description));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}


function validateChqNumber(chqNo){
	var isValidReturnVal=true ;
	if (!isNull(chqNo.value)){
		if(chqNo.value.length < 6 || chqNo.value.length > 6 ){
			alert('Cheque No. length should be 6 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
		 isValidReturnVal= false;
		}
		
	}
	return isValidReturnVal;
}

function saveOrUpdateBillCollection(){
	if(validateHeader() && validateGridDetails()){
		showProcessing();
		var url = './billCollection.do?submitName=saveOrUpdateCollection';
		jQuery.ajax({
			url : url,
			data : jQuery("#billCollectionForm").serialize(),
			success : function(req) {
				callSaveOrUpdateBillCollection(req);
			}
		});
	}
}

/**
 * @param ajaxResp
 */
function callSaveOrUpdateBillCollection(ajaxResp) {
	//var response = ajaxResp;
	var collectionDtlsTO  = eval('(' + ajaxResp + ')');
	if (!isNull(collectionDtlsTO.isSaved) && collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('Bill Collection saved successfully. Transaction No is : ' + collectionDtlsTO.txnNo);
		
		getDomElementById("txnNo").value = collectionDtlsTO.txnNo;
		getDomElementById("txnNo").readOnly = true;
		getDomElementById("collectionDate").value =collectionDtlsTO.collectionDate;
		getDomElementById("custName").value = collectionDtlsTO.custName;
		getDomElementById("custCode").value =collectionDtlsTO.custCode;
		getDomElementById("collectionModeId").value = collectionDtlsTO.collectionModeId;
		getDomElementById("chqNo").value =collectionDtlsTO.chqNo;
		getDomElementById("chqDate").value = collectionDtlsTO.chqDate;
		getDomElementById("bankName").value = collectionDtlsTO.bankName;
		getDomElementById("amount").value = collectionDtlsTO.amount;
		getDomElementById("collectionID").value = collectionDtlsTO.collectionID;
		//getDomElementById("bankGlId").value = collectionDtlsTO.bankGlId;
		//Grid Details
		for(var i =0 ; i<collectionDtlsTO.billCollectionDetailTO.length; i++){
			
			document.getElementById("collectionEntryIds"+ (i+1)).value = collectionDtlsTO.billCollectionDetailTO[i].collectionEntryId;
			document.getElementById("collectionAgainsts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].collectionAgainst;
			document.getElementById("billNos"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].billNo;
			document.getElementById("billAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].billAmount;
			document.getElementById("receivedAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].recvdAmt;
			document.getElementById("tdsAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].tdsAmt;
			document.getElementById("deductions"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].deduction;
			document.getElementById("totals"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].total;
			document.getElementById("remarks"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].remarks;
			if(isNull(document.getElementById("remarks"+ (i+2))))
			   addCollectionRow();		
		}
	} else {
		alert('Bill Collection saved Unsuccessfully.');
	}
	jQuery.unblockUI();
	//var url = "./billCollection.do?submitName=prepareBillCollectionPage";
	//document.billCollectionForm.action = url;
	//document.billCollectionForm.submit();	
}

function validateHeader(){
	//var txnNo = getDomElementById("txnNo");
	var collectionDate = getDomElementById("collectionDate");
	var custName = getDomElementById("custName");
	var custCode = getDomElementById("custCode");
	var collectionModeId = getDomElementById("collectionModeId");
	var chqNo = getDomElementById("chqNo");
	var chqDate = getDomElementById("chqDate");
	var bankName = getDomElementById("bankName");
	var amount = getDomElementById("amount");
/*	if(isNull(txnNo.value)){
		alert("Please provide Transaction No.");
		setTimeout(function(){txnNo.focus();},10);
		return false;
	}*/
	if(isNull(collectionDate.value)){
		alert("Please provide Collection Date.");
		setTimeout(function(){collectionDate.focus();},10);
		return false;
	}
	if(isNull(custName.value)){
		alert("Please provide Customer Name.");
		setTimeout(function(){custName.focus();},10);
		return false;
	}
	if(isNull(custCode.value)){
		alert("Please provide Customer Code.");
		setTimeout(function(){custCode.focus();},10);
		return false;
	}
	if(isNull(collectionModeId.value)){
		alert("Please provide Mode of Collection.");
		setTimeout(function(){collectionModeId.focus();},10);
		return false;
	}
	if(collectionModeId.value == "2"){
		if(isNull(chqNo.value)){
			alert("Please provide Cheque Number.");
			setTimeout(function(){chqNo.focus();},10);
			return false;
		}
		if(isNull(chqDate.value)){
			alert("Please provide Cheque Date.");
			setTimeout(function(){chqDate.focus();},10);
			return false;
		}
		if(isNull(bankName.value)){
			alert("Please provide Bank Name.");
			setTimeout(function(){bankName.focus();},10);
			return false;
		}
	}
	if(isNull(amount.value)){
		alert("Please provide Amount.");
		setTimeout(function(){amount.focus();},10);
		return false;
	}
	
	return true;
}

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails(){
	for(var i=1; i<=rowCount; i++){
		
	//	if(i==1){//atleast one row should be filled
			if(validateSelectedRow(i)) {
				return true;
			}
		//}
	}
	
	alert("Please provide Atleast One received amount.");
	setTimeout(function(){billNos.focus();},10);
	return flase;
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var receivedAmounts = getDomElementById("receivedAmounts"+rowId);
	//var lineNum = "at line :"+rowId;
	if(!isNull(receivedAmounts.value)){
		return true;
	}
	return false;
}

function validateAmount(){
	var receivedAmount = 0;
	var amount = parseFloat(getDomElementById("amount").value);
	for(var i=1; i< rowCount; i++){
		if(!isNull(getDomElementById("receivedAmounts"+i).value))
			receivedAmount = parseFloat(receivedAmount) + parseFloat(getDomElementById("receivedAmounts"+i).value);
	}
   if (amount != receivedAmount){
	   alert("Received amount total and header amount are not matched");
	   return false;
   }
   return true;
}

function searchBillCollectionDetails(){
	var txnNo = document.getElementById("txnNo").value;
	if(!isNull(txnNo)){
		url = './billCollection.do?submitName=searchBillCollectionDetails&txnNo='
				+ txnNo;
	//	showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateBillCollectionDetails(req);
			}
		});
	} else{
		alert("Please provide Transaction number");
	}
}

function populateBillCollectionDetails(ajaxResp) {
	if(!isNull(ajaxResp)){
		var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		var collectionDtlsTORes = eval('(' + ajaxResp + ')');
		var collectionDtlsTO = collectionDtlsTORes;
		document.getElementById("txnNo").value = collectionDtlsTO.txnNo;
		getDomElementById("txnNo").value =collectionDtlsTO.txnNo;
		getDomElementById("collectionDate").value =collectionDtlsTO.collectionDate;
		getDomElementById("custName").value = collectionDtlsTO.custName;
		getDomElementById("custCode").value =collectionDtlsTO.custCode;
		getDomElementById("collectionModeId").value = collectionDtlsTO.collectionModeId;
		getDomElementById("chqNo").value =collectionDtlsTO.chqNo;
		getDomElementById("chqDate").value = collectionDtlsTO.chqDate;
		getDomElementById("bankName").value = collectionDtlsTO.bankName;
		getDomElementById("amount").value = collectionDtlsTO.amount;
		getDomElementById("collectionID").value = collectionDtlsTO.collectionID;
		getDomElementById("trxStatus").value = collectionDtlsTO.status;
		getDomElementById("custId").value = collectionDtlsTO.custId;
		getBillDtls(getDomElementById("custId"));
		//getDomElementById("bankGlId").value = collectionDtlsTO.bankGlId;
		//Grid Details
		for(var i =0 ; i<collectionDtlsTO.billCollectionDetailTO.length; i++){
			
			document.getElementById("collectionEntryIds"+ (i+1)).value = collectionDtlsTO.billCollectionDetailTO[i].collectionEntryId;
			document.getElementById("collectionAgainsts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].collectionAgainst;
			var oTable = $('#collectionDetails').dataTable();
			if(collectionDtlsTO.billCollectionDetailTO[i].collectionAgainst == 'B'){
					var newValue = '<select id="billNos' + (i+1) + '" name="to.billNos" class="selectBox width120" /><option value="">---Select---</option></select>';
					oTable.fnUpdate( newValue, i, 2);
					getBillDeatils(i+1);
				}else{
					var newValue ='<input type="text" id="billNos' + (i+1) + '" name="to.billNos" class="txtbox width70" size="11" />';
					oTable.fnUpdate( newValue, i, 2);
				}
			document.getElementById("billNos"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].billNo;
			document.getElementById("billAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].billAmount;
			document.getElementById("receivedAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].recvdAmt;
			document.getElementById("tdsAmounts"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].tdsAmt;
			document.getElementById("deductions"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].deduction;
			document.getElementById("totals"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].total;
			document.getElementById("remarks"+ (i+1)).value =collectionDtlsTO.billCollectionDetailTO[i].remarks;
			if(isNull(document.getElementById("totals"+ (i+2)))){
				addCollectionRow();
			}
					
						
		}
		if(collectionDtlsTO.status == "S" || collectionDtlsTO.status == "V"){
			disableAll();
		}
	}
}

function submitBillCollection(){
	if(validateHeader() && validateGridDetails() && validateAmount()){
		showProcessing();
		var url = './billCollection.do?submitName=submitBillCollection';
		jQuery.ajax({
			url : url,
			data : jQuery("#billCollectionForm").serialize(),
			success : function(req) {
				callsubmitBillCollection(req);
			}
		});
  }
}

/**
 * @param ajaxResp
 */
function callsubmitBillCollection(ajaxResp) {
	if(!isNull(ajaxResp)){
		if(isJsonResponce()){
			return;
		}
	}
	var collectionDtlsTO  = eval('(' + ajaxResp + ')');
	if (!isNull(collectionDtlsTO.isSaved) && collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('Bill Collection Submitted successfully.');
	}else {
		alert('Bill Collection Submitted Unsuccessfully.');
	}
	jQuery.unblockUI();
	var url = "./billCollection.do?submitName=prepareBillCollectionPage";
	document.billCollectionForm.action = url;
	document.billCollectionForm.submit();	
}

function collectionAgainstView(domObj){
	var rowNo = getRowId(domObj,"collectionAgainsts");
	var collAgnstVal = document.getElementById("collectionAgainsts" + rowNo).value;
	var oTable = $('#collectionDetails').dataTable();
	if(collAgnstVal == 'B'){
		var newValue = '<select id="billNos' + rowNo+ '" name="to.billNos" class="selectBox width120" /><option value="">---Select---</option></select>';
		var temp = parseInt(rowNo) -1;
		oTable.fnUpdate( newValue, temp, 2);
		getBillDeatils(rowNo);
	}else{
		var newValue ='<input type="text" id="billNos' + rowNo+ '" name="to.billNos" class="txtbox width70" size="11" />';
		var temp = parseInt(rowNo) -1;
		oTable.fnUpdate( newValue, temp, 2);
	}
	if(collAgnstVal == 'B'){
		document.getElementById("totals"+ rowNo).readOnly = true;
		document.getElementById("billNos"+ rowNo).readOnly = false;
		document.getElementById("billAmounts"+ rowNo).readOnly = false;
		document.getElementById("tdsAmounts"+ rowNo).readOnly = false;
		document.getElementById("deductions"+ rowNo).readOnly = false;
	}
	if(collAgnstVal == 'D'){
		document.getElementById("totals"+ rowNo).readOnly = true;
		document.getElementById("billNos"+ rowNo).readOnly = false;
		document.getElementById("billAmounts"+ rowNo).readOnly = false;
		document.getElementById("tdsAmounts"+ rowNo).readOnly = false;
		document.getElementById("deductions"+ rowNo).readOnly = false;
	}
	if(collAgnstVal == 'C'){
		document.getElementById("totals"+ rowNo).readOnly = true;
		document.getElementById("billNos"+ rowNo).readOnly = false;
		document.getElementById("billAmounts"+ rowNo).readOnly = false;
		document.getElementById("tdsAmounts"+ rowNo).readOnly = false;
		document.getElementById("deductions"+ rowNo).readOnly = false;
	}
	if(collAgnstVal == 'O'){
		document.getElementById("billNos"+ rowNo).readOnly = true;
		document.getElementById("billAmounts"+ rowNo).readOnly = true;
		document.getElementById("tdsAmounts"+ rowNo).readOnly = true;
		document.getElementById("deductions"+ rowNo).readOnly = true;
		document.getElementById("totals"+ rowNo).readOnly = true;
	}
}

/**
 * @param domObj
 * @param id = R,T,D
 */
function calculateTotalAmount(domObj, id){
	var total = 0;
	var rowNo = 0;
	if(id =='R'){
		rowNo = getRowId(domObj,"receivedAmounts");
	}
	if(id =='T'){
		rowNo = getRowId(domObj,"tdsAmounts");
	}
	if(id =='D'){
		rowNo = getRowId(domObj,"deductions");
	}
	if (!isNull(document.getElementById("receivedAmounts"+ rowNo).value)){
		total = parseFloat(total) + parseFloat(document.getElementById("receivedAmounts"+ rowNo).value);
	}
	if (!isNull(document.getElementById("tdsAmounts"+ rowNo).value)){
		total = parseFloat(total) + parseFloat(document.getElementById("tdsAmounts"+ rowNo).value);
	}
	if (!isNull(document.getElementById("deductions"+ rowNo).value)){
		total = parseFloat(total) + parseFloat(document.getElementById("deductions"+ rowNo).value);
	}
	document.getElementById("totals"+ rowNo).value = total;
	document.getElementById("totals"+ rowNo).readOnly = true;
}


function calculateCorrectedTotal(domObj, id){
	var total = 0;
	var rowNo = 0;
	if(id =='R'){
		rowNo = getRowId(domObj,"correctedRecvAmount");
	}
	if(id =='T'){
		rowNo = getRowId(domObj,"correctedTDS");
	}
	
	if (!isNull(document.getElementById("correctedRecvAmount"+ rowNo).value)){
		total = parseFloat(total) + parseFloat(document.getElementById("correctedRecvAmount"+ rowNo).value);
	}else{
		total = parseFloat(total) + parseFloat(document.getElementById("receivedAmounts"+ rowNo).value);
	}
	if (!isNull(document.getElementById("correctedTDS"+ rowNo).value)){
		total = parseFloat(total) + parseFloat(document.getElementById("correctedTDS"+ rowNo).value);
	}else{
		total = parseFloat(total) + parseFloat(document.getElementById("tdsAmounts"+ rowNo).value);
	}
	document.getElementById("totals"+ rowNo).value = total;
	document.getElementById("totals"+ rowNo).readOnly = true;
}

function cancleData(){
	var txnNo = document.getElementById("txnNo").value;
	if (isNull(txnNo)){
		var url = "./billCollection.do?submitName=prepareBillCollectionPage";
		document.billCollectionForm.action = url;
		document.billCollectionForm.submit();	
	} else {
		searchBillCollectionDetails();
	}
}

function validateTotalAmount(){
	var headerAmount = getDomElementById("amount").value;
	var totalAmount = 0;
	for (var i=1; i < serialNo ; i++){
		totalAmount = parseFloat(totalAmount) + parseFloat(document.getElementById("totals"+ i).value);
	}
	if (headerAmount ==totalAmount){
		return true;
	}
	return false;
}


function getCustCode(){
	var selectedName = jQuery("#custName").val();
	$.each(data, function(index, customer) {
		if(customer == selectedName){
			jQuery("#custCode").val(custCodeArr[index]);
			jQuery("#custId").val(custIDArr[index]);
			getBillDtls(getDomElementById("custId"));
		}
	});
}

function ValidateCollection(){
	var url = './billCollection.do?submitName=validateCollection';
	jQuery.ajax({
		url : url,
		data : jQuery("#billCollectionForm").serialize(),
		success : function(req) {
			callValidateCollection(req);
		}
	});
}

function callValidateCollection(ajaxResp) {
	var collectionDtlsTO  = eval('(' + ajaxResp + ')');
	if (!isNull(collectionDtlsTO.isSaved) && collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('Bill Collection validated successfully.');
	} else {
		alert('Bill Collection validated Unsuccessfully.');
	}
	self.close();
}

function correction(){
	var txnNo = getDomElementById("txnNo").value;
	var collectionType = getDomElementById("collectionType").value;
	var url="./billCollection.do?submitName=viewCollectionEntryDtls&txnNo=" + txnNo 
				+ "&collectionType=" + collectionType
				+ "&isCorrectionParam=Y";
	document.billCollectionForm.action = url;
	document.billCollectionForm.submit();	
}


function checkCollectionMode(obj){
	var collType = obj.value;
	if(collType == BC_MODE){
		$('#chqNo').removeAttr("readonly");
		$("#bankName").removeAttr("readonly");
		$("#calendar").show();
	}else{
		jQuery("#chqNo").val("");
		jQuery("#bankName").val("");
		jQuery("#chqNo").attr("readonly", true);
		jQuery("#bankName").attr("readonly", true);
		$("#calendar").hide();
	}
}

function closePage(){
	
	self.close();
}

function getBillDeatils(rowId){
	var content = document.getElementById('billNos' + rowId);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("---SELECT---"));
	content.appendChild(defOption);
	
	for(var i=0; i<billOptions.length; i++){
		defOption=document.createElement("option");
		defOption.value=billOptions[i].invoiceNumber;
		defOption.appendChild(document.createTextNode(billOptions[i].invoiceNumber));
		content.appendChild(defOption);
	}
	/*defOption = document.createElement("option");
	defOption.value = "101";
	defOption.appendChild(document.createTextNode("101"));
	content.appendChild(defOption);
	defOption = document.createElement("option");
	defOption.value = "102";
	defOption.appendChild(document.createTextNode("102"));
	content.appendChild(defOption);*/
	
	//setTimeout(function(){content.focus();},10);
}

function getBillDtls(obj){
	if(!isNull(obj.value)){
		var url="./billCollection.do?submitName=getBillDtls&custId="+obj.value;
		ajaxCallWithoutForm(url, ajaxRespForBillDtls);
	}
}

function ajaxRespForBillDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		billOptions=eval(ajaxResp);
		for(var i=1; i<rowCount; i++){
			var collAgainst=getDomElementById("collectionAgainsts"+i).value;
			if(collAgainst==COLL_AGAINST_BILL){
				getBillDeatils(i);
				jQuery("#billAmounts"+i).val("");
			}
		}
	}
}

function getBillAmount(obj){
	var rowId=getRowId(obj,"billNos");
	if(!isNull(obj.value)){
		for(var i=0; i<billOptions.length; i++) {
			if(obj.value==billOptions[i].invoiceNumber){
				jQuery("#billAmounts"+rowId).val(billOptions[i].total);
			}
		}
	} else {
		jQuery("#billAmounts"+rowId).val("");
	}
}
function fnEnterKeyNav(e,colModeObj){
	if(!isNull(colModeObj.value)){
		var key;
		if (window.event)
			key = window.event.keyCode; // IE
		else
			key = e.which; // firefox
		if (key == 13) {
			if(colModeObj.value==BC_MODE){
				getDomElementById("chqNo").focus();
			}else{
				getDomElementById("amount").focus();
			}
		}
	}
}
function focusCalendar(e){
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var chqDateObj = getDomElementById("chqDate");
		if (isNull(chqDateObj.value)) {
			getDomElementById("calendarImg").focus();
		}else{
			getDomElementById("bankName").focus();
		}
	}
}