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
		return true;

	}
	validateScreen();
}
/**
 * save
 * @param val
 */
function save(val) {
	if(updatedCheckBoxValues('to.checkbox') && isCheckBoxSelected('to.checkbox') && checkMandatoryForSave()) {
		var url = "./stockReceiptRho.do?submitName=saveReceiptDetails";
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
	url = "./stockReceiptRho.do?submitName=viewFormDetails";
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
	if(val=='reqNumber' ) {
		num = getDomElementById("requisitionNumber");
		findBy = "Requisition";
		url = "./stockReceiptRho.do?submitName=findDetailsByRequisitionNumberAtRHO";
	} else  if(val=='ackNumber'){ //acknowledgement
		findBy = "Acknowledgement";
		num = getDomElementById("acknowledgementNumber");
		url = "./stockReceiptRho.do?submitName=findDetailsByReceiptNumber";
	}
	if(isNull(num.value)){
		alert("Please provide Stock " + findBy + " Number");
		num.focus();
		return false;
	}
	if(!isNull(url)) {
		submitRecWithoutPrompt(url);
	}else{
		alert("Invalid URL/Input");
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
			if(!checkMandatoryInGrid(rowId)) {
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
function checkMandatoryInGrid(rowId){
	var itemType = getDomElementById("rowItemTypeId"+rowId);
	var item = getDomElementById("rowItemId"+rowId);
	var desc = getDomElementById("rowDescription"+rowId);
	var uom = getDomElementById("rowUom"+rowId);
	var apprvQnty = getDomElementById("rowApprovedQuantity"+rowId);
	var recQnty = getDomElementById("rowReceivingQuantity"+rowId);
	var startSerialNum = getDomElementById("rowStartSerialNumber"+rowId);
	var endSerialNum = getDomElementById("rowEndSerialNumber"+rowId);
	//var remarks = getDomElementById("rowRemarks"+rowId);
	var lineNum = " at line :"+rowId;
	if(isNull(itemType.value)){
		alert("Please select  Material Type "+lineNum);
		setTimeout(function(){itemType.focus();},10);
		return false;
	}
	if(isNull(item.value)){
		alert("Please select  Material Code "+lineNum);
		setTimeout(function(){item.focus();},10);
		return false;
	}
	if(isNull(desc.value)){
		alert("Please provide Description "+lineNum);
		setTimeout(function(){desc.focus();},10);
		return false;
	}
	if(isNull(uom.value)){
		alert("Please provide UOM  "+lineNum);
		setTimeout(function(){uom.focus();},10);
		return false;
	}
	if(isNull(apprvQnty.value)){
		alert("Please provide Approved Quantity "+lineNum);
		setTimeout(function(){apprvQnty.focus();},10);
		return false;
	} 
	
	if(isNull(recQnty.value)){
		alert("Please provide Receiving Quantity "+lineNum);
		setTimeout(function(){recQnty.focus();},10);
		return false;
	}
	if(!checkBalanceQnty(rowId)){
		return false;
	}
	if(!startSerialNum.readOnly){
		if(isNull(startSerialNum.value)) {
			alert("Please provide start serial number "+lineNum);
			setTimeout(function(){startSerialNum.focus();},10);
			return false;
		}
		if(isNull(endSerialNum.value)) {
			alert("Please provide valid start/end serial number "+lineNum);
			startSerialNum.value="";
			endSerialNum.value="";
			setTimeout(function(){startSerialNum.focus();},10);
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
function validateSeriesAtRHO(domElement){
	var rowId = getRowId(domElement,"rowStartSerialNumber");
	var stSeries = domElement.value;
	var endSeries = null;
	var endSerialNum = getDomElementById("rowEndSerialNumber"+rowId);
	endSerialNum.value="";
	var recQntyDom = getDomElementById("rowReceivingQuantity"+rowId);
	var recQnty=parseNumber(recQntyDom.value);
	
	var seriesStartsWithDom = getDomElementById("seriesStartsWith"+rowId);
	var seriesStartsWith=trimString(seriesStartsWithDom.value);
	
	if(isNull(recQnty)){
		alert("Please provide Quantity at Line :"+rowId);
		clearSeries(rowId);
		recQntyDom.focus();
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
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	
	if(!checkStartsWith(stSeries,seriesStartsWith)){
		alert("series must start with :"+seriesStartsWith);
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}else{

		var tokenArray = stSeries.split(seriesStartsWith);
		if(!isSeriesStartsWithProduct(tokenArray[1])){
			alert("Series should not contain Alphabets after:"+seriesStartsWith);
			clearSeries(rowId);
			return false;
		}
		if(!isLeafValid(stSeries,seriesStartsWith)){
			clearSeries(rowId);
			setTimeout(function(){domElement.focus();},10);
			return false;
		}
		
	
	}
	endSeries = getEndSerialNumber(stSeries,recQnty);
	//check length of the series
	if(!isEndSeriesLengthValid(endSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	

	//check duplicate series
	
	if(!checkForDuplicateSerialNumber(stSeries,endSeries,rowId,'receiptGrid')){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	var rhoScreen=getDomElementById("rhoScreen");
	if(!isNull(rhoScreen)){
		rhoScreen=rhoScreen.value;
	}else{
		rhoScreen=null;
	}
	//prepare URL to validate
	
	var	url = "./stockReceiptRho.do?submitName=isValidSeriesForStockReceipt&startSerialNum="+domElement.value+"&quantity="+recQnty +"&itemId="+itemId+"&seriesType="+seriesType+"&transType="+transType+"&transactionNumber="+transNumber+"&itemDetailsId="+itemDetlId+"&receptItemDtlId="+receiptDetlId+"&rhoScreen="+rhoScreen+"&seriesStartsWith="+seriesStartsWith;
	
	
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
	if(!isNull(receiptFromType)) {
		//to disable issue/requisition/acknowledgement number
		var reqNum = getDomElementById("requisitionNumber");
		var ackNum = getDomElementById("acknowledgementNumber");

		reqNum.setAttribute("readOnly",true);
		reqNum.setAttribute("tabindex","-1");

		
		ackNum.setAttribute("readOnly",true);
		ackNum.setAttribute("tabindex","-1");
		var tableee = getDomElementById('receiptGrid');
		var totalRowCount =  tableee.rows.length;
		
		var isNew=isNewGR();
		
		for(var i=1;i<totalRowCount;i++) {
			hasSeries(i);
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

	var lineInfo=" at line :"+rowId;
	// BR (1) ::Approved Qnty>Issued Qnty > Receing Qnty <=Balance Qnty

	var approvedQntyDom = getDomElementById("rowApprovedQuantity"+rowId);
	var recQntyDom = getDomElementById("rowReceivingQuantity"+rowId);
	var balQntyDom = getDomElementById("rowBalanceQuantity"+rowId);


	var approvedQnty = parseNumber(approvedQntyDom.value);
	var receiveQnty = parseNumber(recQntyDom.value);

	var balQnty = parseNumber(balQntyDom.value);

	if(approvedQnty < receiveQnty){
		alert("Approved Quantity can not be more than Receiving Quantity"+lineInfo);
		receiveQnty.value="";
		setTimeout(function(){receiveQnty.focus();},10);
		return false;
	}


	if(receiveQnty>balQnty){
		alert("Receiving Quantity can not be more than Balance Quantity"+lineInfo);
		recQntyDom.value="";
		setTimeout(function(){recQntyDom.focus();},10);
		return false;
	}
	return true;
}

function seriesValidationWrapperAtRho(rowId){
	var seriesDom= getDomElementById("rowStartSerialNumber"+rowId);
	if(seriesDom!=null && !isNull(seriesDom.value))
		validateSeriesAtRHO(seriesDom);
}
/**getTransactionPRType
 * @returns transactionPRType
 */
function getTransactionPRType(){
	return getValueByElementId("transactionPRType");
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
	 if(isFromPR()){
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