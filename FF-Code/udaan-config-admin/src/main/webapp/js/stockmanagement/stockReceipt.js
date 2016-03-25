// Start of Changes by <31913> on 16/01/2013

/**
 * receiptStartUp : called on load of the jsp
 * @returns {Boolean}
 */
function receiptStartUp(){
	disableAfterSave();
	disableForUser();
	if(!isNewGR()){
		var qntyDomArray = document.getElementsByName('to.rowReceivingQuantity');
		for(var i = 0; i<qntyDomArray.length; i++) {
			var qntyDom =qntyDomArray[i];
			qntyDom.setAttribute("readOnly",true);
			qntyDom.setAttribute("tabindex","-1");
		}
	}else{
		var reqNumber= getDomElementById("requisitionNumber");
		var receiptFromType = getDomElementById("transactionFromType").value;
		if(reqNumber!=null && isNull(receiptFromType)){
			setFocusOnStDom(reqNumber);
		}
	}
	validateScreen();
	hidePrintButton();
}
/**
 * save
 * @param val
 */
function save(val) {
	if(updatedCheckBoxValues('to.checkbox') && isCheckBoxSelectedWithMessage('to.checkbox',SELECT_ATLEAST_ONE_ITEM) && checkMandatoryForSave()) {
		var url = "./stockReceipt.do?submitName=saveReceiptDetails";
		submitReceipt(url,val);
	}
}
/**
 * submitReceipt
 * @param url
 * @param action
 */
function submitReceipt(url,action) {
	if(promptConfirmation(action)) {
		dropdownEnable();
		document.stockReceiptForm.action = url;
		document.stockReceiptForm.method = "post";
		document.stockReceiptForm.submit();
	}
}
/**
 * clearScreen
 */
function clearScreen() {
	
	var url=null;
	if(isRhoScreen()){
		url = "./stockReceiptRho.do?submitName=viewFormDetails";
	}else{
	url = "./stockReceipt.do?submitName=viewFormDetails";
	}
	submitReceipt(url,'Clear');
}
/**
 * find
 * @param val
 */
function find(val) {
	var url = null;
	var num = null;
	var findBy = null;
	if(val=='req') {
		num = getDomElementById("requisitionNumber");
		findBy = "Requisition";
	} else if(val=="issue") {
		num = getDomElementById("stockIssueNumber");
		findBy = "Issue";
	} else { //acknowledgement
		num = getDomElementById("acknowledgementNumber");
		findBy = "Acknowledgement";
	}
	num.value=trimString(num.value);
	if(!isNull(num.value)) {
		if(val=='req') {
			url = "./stockReceipt.do?submitName=findDetailsByRequisitionNumber";
		} else if(val=='issue') {
			url = "./stockReceipt.do?submitName=findDetailsByIssueNumber";
		}else if(val=='rho'){
			findBy = "Requisition";
			url = "./stockReceiptRho.do?submitName=findDetailsByRequisitionNumber";
		} else { //val == 'ack'
			url = "./stockReceipt.do?submitName=findDetailsByReceiptNumber";
		}
		submitRecWithoutPrompt(url);
	}else{
		alert("Please provide Stock " + findBy + " Number");
		setFocusOnStDom(reqNum);
	}
}
/**
 * submitRecWithoutPrompt
 * @param url
 */
function submitRecWithoutPrompt(url) {
	document.stockReceiptForm.action = url;
	document.stockReceiptForm.method = "post";
	document.stockReceiptForm.submit();
}
/**
 * checkMandatoryForSave
 * @returns {Boolean}
 */
function checkMandatoryForSave(){
	var tableee = document.getElementById('receiptGrid');
	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.checkbox');
	for(var i = 1; i<totalRowCount; i++) {
		if(cell[i-1].checked) {
			rowId = getRowId(cell[i-1],"checkbox");
			if(!checkMandatoryForAdd(rowId)) {
				return false;
			}
		}
	}
	return true;
}
/**
 * checkMandatoryForAdd
 * @param rowId
 * @returns {Boolean}
 */
function checkMandatoryForAdd(rowId){
	var itemType = getDomElementById("rowItemTypeId"+rowId);
	var item = getDomElementById("rowItemId"+rowId);
	var desc = getDomElementById("rowDescription"+rowId);
	var uom = getDomElementById("rowUom"+rowId);
	var apprvQnty = getDomElementById("rowApprovedQuantity"+rowId);
	var issQnty = getDomElementById("rowIssuedQuantity"+rowId);
	var recQnty = getDomElementById("rowReceivingQuantity"+rowId);
	var startSerialNum = getDomElementById("rowStartSerialNumber"+rowId);
	var endSerialNum = getDomElementById("rowEndSerialNumber"+rowId);
	//var remarks = getDomElementById("rowRemarks"+rowId);
	
	
	
	apprvQnty.value=trimString(apprvQnty.value);
	issQnty.value=trimString(issQnty.value);
	recQnty.value=trimString(recQnty.value);
	
	apprvQnty.value=parseNumber(apprvQnty.value);
	issQnty.value=parseNumber(issQnty.value);
	recQnty.value=parseNumber(recQnty.value);
	
	var lineNum = " at line :"+rowId;
	if(isNull(itemType.value)){
		alert("Please select  Material Type "+lineNum);
		setFocusOnStDom(itemType);
		return false;
	}
	if(isNull(item.value)){
		alert("Please select  Material Code "+lineNum);
		setFocusOnStDom(item);
		return false;
	}
	if(isNull(desc.value)){
		alert("Please provide Description "+lineNum);
		setFocusOnStDom(desc);
		return false;
	}
	if(isNull(uom.value)){
		alert("Please provide UOM  "+lineNum);
		setFocusOnStDom(uom);
		return false;
	}
	if(isNull(apprvQnty.value)){
		alert("Please provide Approved Quantity "+lineNum);
		apprvQnty.value="";
		setFocusOnStDom(apprvQnty);
		return false;
	} 
	if(isNull(issQnty.value)){
		alert("Please provide Issued Quantity "+lineNum);
		issQnty.value="";
		setFocusOnStDom(issQnty);
		return false;
	}
	if(isNull(recQnty.value)){
		alert("Please provide Receiving Quantity "+lineNum);
		recQnty.value="";
		setFocusOnStDom(recQnty);
		return false;
	}
	if(!checkBalanceQnty(rowId)){
		return false;
	}
	if(!startSerialNum.readOnly){
		if(isNull(startSerialNum.value)) {
			alert("Please provide start serial number "+lineNum);
			setFocusOnStDom(startSerialNum);
			return false;
		}
		if(isNull(endSerialNum.value)) {
			alert("Please provide valid start/end serial number "+lineNum);
			startSerialNum.value="";
			endSerialNum.value="";
			setFocusOnStDom(startSerialNum);
			return false;
		}
	}
	return true;
}

/**
 * validateSeries
 * @param domElement
 * @returns {Boolean}
 */
function validateSeries(domElement){
	var rowId = getRowId(domElement,"rowStartSerialNumber");
	var stSeries = domElement.value;
	var endSeries = null;
	var recQntyDom = getDomElementById("rowReceivingQuantity"+rowId);
	var endSerialNum = getDomElementById("rowEndSerialNumber"+rowId);
	endSerialNum.value="";
	recQntyDom.value=trimString(recQntyDom.value);
	recQntyDom.value=parseNumber(recQntyDom.value);
	var recQnty=recQntyDom.value;
	
	if(isNull(recQnty)){
		alert("Please provide Quantity at Line :"+rowId);
		clearSeries(rowId);
		setFocusOnStDom(recQntyDom);
		return false;
	}
	var itemDom = getRowItemId(rowId);
	var itemId=itemDom.value;
	var seriesType = getRowSeriesType(rowId);

	stSeries = convertToUpperCase(stSeries);
	var transType=getTransactionType();
	var transNumber=getTransactionNumber();
	var itemDetlId=getStockItemDtlsId(rowId);//key of respective tables
	var receiptDetlId=getReceiptItemDtlsId(rowId);//key of respective tables
	
	
	if(isNull(stSeries)){
		return false;
	}
	//check length of the series
	if(!isSeriesLengthValid(stSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setFocusOnStDom(domElement);
		return false;
	}
	//check series has Branch and Product 
	if(!isSeriesHasBranchProduct(stSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setFocusOnStDom(domElement);
		return false;
	}
	endSeries = getEndSerialNumber(stSeries,recQnty);
	//check length of the series
	if(!isEndSeriesLengthValid(endSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setFocusOnStDom(domElement);
		return false;
	}
	

	//check duplicate series
	
	if(!checkForDuplicateSerialNumber(stSeries,endSeries,rowId,'receiptGrid')){
		domElement.value="";
		clearSeries(rowId);
		setFocusOnStDom(domElement);
		return false;
	}
	//prepare URL to validate
	
	var	url = "./stockReceipt.do?submitName=isValidSeriesForStockReceipt&startSerialNum="+domElement.value+"&quantity="+recQnty +"&itemId="+itemId+"&seriesType="+seriesType+"&transType="+transType+"&transactionNumber="+transNumber+"&itemDetailsId="+itemDetlId+"&receptItemDtlId="+receiptDetlId;
	
	
	//make an ajax call
	if(!isNull(url)){
		ajaxJqueryWithRow(url, 'stockReceiptForm', ajaxResponseForSeries, rowId);
	}else{
		alert("Invalid Business");
		return false;
	}
}
/**
 * disableAfterSave
 */
function disableAfterSave(){
	var receiptFromType = getDomElementById("transactionFromType").value;
	
	var isNew=true;
	if(!isNewGR()){
		isNew=false;
	}
	if(!isNull(receiptFromType)) {
		//to disable issue/requisition/acknowledgement number
		var reqNum = getDomElementById("requisitionNumber");
		var issueNum = getDomElementById("stockIssueNumber");
		var ackNum = getDomElementById("acknowledgementNumber");

		reqNum.setAttribute("readOnly",true);
		reqNum.setAttribute("tabindex","-1");

		issueNum.setAttribute("readOnly",true);
		issueNum.setAttribute("tabindex","-1");

		ackNum.setAttribute("readOnly",true);
		ackNum.setAttribute("tabindex","-1");
		var tableee = getDomElementById('receiptGrid');
		var totalRowCount =  tableee.rows.length;
		var focusGained=false;
		for(var i=1;i<totalRowCount;i++) {
			hasSeries(i);
			if(isNew && !focusGained){
				var recQnty = getDomElementById("rowReceivingQuantity"+i);
				if(recQnty!=null){
					setFocusOnStDom(recQnty);
				}
				focusGained=true;
			}
			if(!isNew){
				
				var stSeries = getDomElementById("rowStartSerialNumber"+i);
				stSeries.setAttribute("readOnly",true);
				stSeries.setAttribute("tabindex","-1");
			}
		}
	}
}
/**
 * checkBalanceQnty
 * @param rowId
 * @returns {Boolean}
 */
function checkBalanceQnty(rowId){
	var transactionFromType=getTransactionType();
	var lineInfo=" at line :"+rowId;
	// BR (1) ::Approved Qnty>Issued Qnty > Receing Qnty <=Balance Qnty
	if(!isNull(transactionFromType)){
		var approvedQntyDom = getDomElementById("rowApprovedQuantity"+rowId);
		var issuedQntyDom = getDomElementById("rowIssuedQuantity"+rowId);
		var recQntyDom = getDomElementById("rowReceivingQuantity"+rowId);
		var balQntyDom = getDomElementById("rowBalanceQuantity"+rowId);
		
		
		var approvedQnty = parseNumber(approvedQntyDom.value);
		var issueQnty = parseNumber(issuedQntyDom.value);
		var receiveQnty = parseNumber(recQntyDom.value);
		
		var balQnty = parseNumber(balQntyDom.value);
		
		if(approvedQnty < issueQnty){
			alert("Issued Quantity can not be more than Approved Quantity"+lineInfo);
			recQntyDom.value="";
			setFocusOnStDom(recQntyDom);
			return false;
		}
		
		if(issueQnty < receiveQnty){
			alert("Receiving Quantity can not be more than Issued Quantity"+lineInfo);
			recQntyDom.value="";
			setFocusOnStDom(recQntyDom);
			return false;
		}
		if(receiveQnty>balQnty){
			alert("Receiving Quantity can not be more than Balance Quantity"+lineInfo);
			recQntyDom.value="";
			setFocusOnStDom(recQntyDom);
			return false;
		}
	}
	return true;
}
/**getTransactionPRType
 * @returns transactionPRType
 */
function getTransactionPRType(){
	return getValueByElementId("transactionPRType");
}
/**getTransactionSIType
 * @returns TransactionSIType
 */
function getTransactionSIType(){
	return getValueByElementId("transactionIssueType");
}
/**
 * isFromPR
 * @returns {Boolean}
 */
function isFromPR(){
	var transactionFromType=getTransactionType();
	if(transactionFromType ==getTransactionPRType()){
		return true;
	}
	return false;
}
/**
 * isFromSI
 * @returns {Boolean}
 */
function isFromSI(){
	var transactionFromType=getTransactionType();
	if(transactionFromType ==getTransactionSIType()){
		return true;
	}
	return false;
}
/**
 * isNewGR
 * @returns {Boolean}
 */
function isNewGR(){
	if(isNull(getDomElementById("stockReceiptId").value)){
		return true;
	}
	return false;
}
/**
 * getTransactionNumber
 * @returns
 */
function getTransactionNumber(){
	if(isFromSI()){
		return getDomElementById("stockIssueNumber").value;
	}else if(isFromPR()){
		return getDomElementById("requisitionNumber").value;
	}
}
/**
 * getReceiptItemDtlsId
 * @param rowId
 * @returns StockReceiptItemDtlsId By id
 */
function getReceiptItemDtlsId(rowId){
	 return getDomElementById("rowStockReceiptItemDtlsId"+rowId).value; 
}
function validateScreen(){
	var isScreenValid=getDomElementById("rhoScreen");
	if(!isNull(isScreenValid)){
		
		var rhoConstnt=getDomElementById("officeRhoType");
		var officeType=getDomElementById("officeType");
		if(!isNull(rhoConstnt)&&!isNull(officeType)){
			if(officeType.value == rhoConstnt.value){
				
			}else{
				alert("Only RHO can use this Screen \n Logged in Office Type :"+officeType.value);
				getDomElementById("canUpdate").value="N";
				disableForUser();
				
			}
		}
		
	}
	
}
function isRhoScreen(){
	var isScreenValid=getDomElementById("rhoScreen");
	if(!isNull(isScreenValid)){
		return true;
	}
	return false;
}