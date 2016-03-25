// Start of Changes by <31913> on 16/12/2012

$(document).ready( function () {
		var oTable = $('#returnGrid').dataTable( {
			"sScrollY": "220",
			"sScrollX": "100%",
			"sScrollXInner":"180%",
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
		disableAfterFind();
		hideElementByDomId('Print');
	} );
/**
 * checkMandatoryForRow
 * @param rowId
 * @returns {Boolean}
 */
function checkMandatoryForRow(rowId) {
	if(!checkQuantity(rowId)){
		return false;
	}
	var startSINumDom = getDomElementById("rowStartSerialNumber"+rowId);
	var startSINum=startSINumDom.value;
	var endSINumDom = getDomElementById("rowEndSerialNumber"+rowId);
	var endSINum=endSINumDom.value;
	var lineNum=" at line :"+rowId;
	
	if(!startSINumDom.readOnly){
		if(isNull(startSINum)){
			alert("Please provide Start Serial Number "+lineNum);
			clearSeries(rowId);
			setFocusOnStDom(startSINumDom);
			return false;
		}
		if(isNull(endSINum) ){
			alert("Please provide Start/End Serial Number "+lineNum);
			clearSeries(rowId);
			setFocusOnStDom(startSINumDom);
			return false;
		}
	}
	return true;
}
/**
 * checkQuantity
 * @param rowId
 * @returns {Boolean}
 */
function checkQuantity(rowId){
	var receivedQntyDom = getDomElementById("rowReceivingQuantity"+rowId);
	receivedQntyDom.value=trimString(receivedQntyDom.value);
	
	var receivedQnty = parseNumber(receivedQntyDom.value);
	
	var returnQntyDom = getDomElementById("rowReturningQuantity"+rowId);
	returnQntyDom.value=trimString(returnQntyDom.value);
	
	var returnQnty = parseNumber(returnQntyDom.value);
	
	var stockQntyDom = getDomElementById("rowCurrentStockQuantity"+rowId);
	var stockQnty=parseNumber(stockQntyDom.value);
	
	var balanceRetQntyDom = getDomElementById("rowBalanceReturnQuantity"+rowId);
	var balanceQnty=parseNumber(balanceRetQntyDom.value);
	var lineNum=" at line :"+rowId;
	if(isNull(returnQnty)){
		alert("Please provide Returning Quantity "+lineNum);
		returnQntyDom.value="";
		clearSeries(rowId);
		setFocusOnStDom(returnQntyDom);
		return false;
	}else {
		if(returnQnty>receivedQnty) {
			alert("Returning Quanity can not be more than received Quantity "+lineNum);
			returnQntyDom.value="";
			clearSeries(rowId);
			setFocusOnStDom(returnQntyDom);
			return false;
		}
	}
	if(returnQnty>stockQnty) {
		alert("Returning Quanity can not be more than stock Quantity "+lineNum);
		returnQntyDom.value="";
		clearSeries(rowId);
		setFocusOnStDom(returnQntyDom);
		return false;
	}
	if(returnQnty>balanceQnty) {
		alert("Returning Quanity can not be more than Balance Return Quantity "+lineNum);
		returnQntyDom.value="";
		clearSeries(rowId);
		setFocusOnStDom(returnQntyDom);
		return false;
	}
	return true;
}
/**
 * checkMandatoryForSave
 * @returns {Boolean}
 */
function checkMandatoryForSave(){
	var tableee = document.getElementById('returnGrid');
	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.checkbox');
	for(var i = 1; i<totalRowCount; i++) {
		if(cell[i-1] && cell[i-1].checked) {
			rowId = getRowId(cell[i-1],"checkbox");
			if(!checkMandatoryForRow(rowId)) {
				return false;
			}
		}
	}
	return true;
}


/**
 * disableStartSeriesNumber
 */
function disableStartSeriesNumber(){
	var issueFromType=getDomElementById("transactionFromType").value;
	if(!isNull(issueFromType)){
		var tableee = getDomElementById('returnGrid');
		var totalRowCount =  tableee.rows.length;
		//alert(totalRowCount);
		for(var i=1;i<totalRowCount;i++) {
			hasSeries(i);
		}
	}
}

/**
 * validateHeader
 * @returns {Boolean}
 */
function validateHeader(){
	var ackNum = getDomElementById("acknowledgementNumber");
	var retNum = getDomElementById("stockReturnNumber");
	if(isNull(ackNum.value) && isNull(retNum.value)){
		//alert("No data avialable.");
		alert(GRID_DTLS_NOT_EXIST_ALERT);
		setFocusOnStDom(ackNum);
		return false;
	}
	return true;
}
/**
 * save
 * @param action
 */
function save(action) {
	if(validateHeader() && updatedCheckBoxValues('to.checkbox')&& 
			isCheckBoxSelectedWithMessage('to.checkbox',SELECT_ATLEAST_ONE_ITEM) && checkMandatoryForSave()
			){
		
			if(action = "Save"){
				var url="./stockReturnRho.do?submitName=saveReturnDetails";
				submitReturn(url,'Save');
			}
	}
}
/**
 * submitReturn
 * @param url
 * @param action
 */
function submitReturn(url,action) {
	if(confirm("Do you want to "+action+" the Stock Return details?")) {
		dropdownEnable();
		document.stockReturnForm.action = url;
		document.stockReturnForm.submit();
	}
}
/**
 * clearScreen
 * @param type
 */
function clearScreen(type) {
	var url;
	if(type == "stockReturn") {
		url="./stockReturnRho.do?submitName=viewFormDetails";
		submitReturn(url,'Clear');
	} 
}
/**
 * find :serach the details  based on return nu
 * @param type
 * @returns {Boolean}
 */
function find(type) {
	var url = null;
	var receiptNumber = getDomElementById("acknowledgementNumber");
	var retNum = getDomElementById("stockReturnNumber");
	
	receiptNumber.value=trimString(receiptNumber.value);
	retNum.value=trimString(retNum.value);
	
		if(type == 'stockReceipt') {
			if(isNull(receiptNumber.value)) {
				alert("Please provide Stock Acknowledge Number");
				setFocusOnStDom(receiptNumber);
				return false;
			}
			url="./stockReturnRho.do?submitName=findDetailsByAcknowledgeNumber";
		}else if(type == 'stockReturn'){
			if(isNull(retNum.value)){
				alert("Please provide Stock Return Number");
				setFocusOnStDom(retNum);
				return false;
			}
			url="./stockReturnRho.do?submitName=findDetailsByReturnNumber";
		}else {
			alert("Invalid Input details");
			return false;
		}
		submitReturnWithoutPrompt(url);
}
/**
 * submitReturnWithoutPrompt
 * @param url
 */
function submitReturnWithoutPrompt(url) {
	document.stockReturnForm.action = url;
	document.stockReturnForm.method = "post";
	document.stockReturnForm.submit();
}
/**
 * isNewReturn
 * @returns {Boolean}
 */
function isNewReturn() {
	var retId = getDomElementById("stockReturnId").value;
	if(isNull(retId)) {
		return true;
	}
	return false;
}
/**
 * disableAfterFind
 */
function disableAfterFind(){
	var transType = getDomElementById("transactionFromType").value;
	var receiptNumber = getDomElementById("acknowledgementNumber");
	var returnNumber = getDomElementById("stockReturnNumber");
	if(!isNull(transType)){
		
		
		returnNumber.setAttribute("readOnly",true);
		returnNumber.setAttribute("tabindex","-1");
		
		
		receiptNumber.setAttribute("readOnly",true);
		receiptNumber.setAttribute("tabindex","-1");
	}
	var tableee = getDomElementById('returnGrid');
	var totalRowCount =  tableee.rows.length;
	disableStartSeriesNumber();
	if(!isNewReturn()){

		//step 1: to disable Series fields
		//step 2: to disable Qnties fields
		
		for(var i=1;i<totalRowCount;i++) {
			var retQnty = getDomElementById("rowReturningQuantity"+i);
			hasSeries(i);
			
			var stNumber = getDomElementById("rowStartSerialNumber"+i);
			
			
			retQnty.setAttribute("readOnly",true);
			retQnty.setAttribute("tabindex","-1");
			
			stNumber.setAttribute("readOnly",true);
			stNumber.setAttribute("tabindex","-1");
			
		}
		disableForUser();
	}else{
		setFocusOnStDom(receiptNumber);
		isLoggedInOfficeTypeBranch();
		var focusGained=false;
		for(var i=1;i<totalRowCount;i++) {
			var retQnty = getDomElementById("rowReturningQuantity"+i);
			if(retQnty==null){
				return;
			}
			hasSeries(i);
			if(retQnty!=null){
				if(!focusGained){

					setFocusOnStDom(retQnty);
					break;
				}
				focusGained=true;
			}else{
				break;
			}

		}
		
	}
	hidePrintButton();
}
/**
 * validateSeries : for series validations
 * @param domElement
 * @returns {Boolean}
 */

function validateSeries(domElement){
	var rowId = getRowId(domElement,"rowStartSerialNumber");
	var endSeries=null;
	getDomElementById("rowEndSerialNumber"+rowId).value="";//clear end serial number
	var stSeries =domElement.value;
	var returnQntyDom = getDomElementById("rowReturningQuantity"+rowId);
	returnQntyDom.value=parseNumber(returnQntyDom.value);
	var returnQnty=returnQntyDom.value;
	if(isNull(returnQnty)){
		alert("Please provide Quantity at Line :"+rowId);
		clearSeries(rowId);
		return false;
	}
	
	var transactionNumber = getDomElementById("acknowledgementNumber").value;
	if(isNull(transactionNumber)){
		alert("Stock Issue number is required to proceed");
		
		clearSeries(rowId);
		return false;
	}
	
	var seriesType = getRowSeriesType(rowId);
	var itemId = getDomElementById("rowItemId"+rowId).value;
	var itemDetailsId = getStockItemDtlsId(rowId);
	stSeries = convertToUpperCase(stSeries);
	if(isNull(stSeries)){
		clearSeries(rowId);
		return false;
	}
	//check length of the series
	if(!isSeriesLengthValid(stSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setFocusOnStDom(domElement);
		return false;
	}
	//check series has Branch and Product 
	if(!isSeriesHasBranchProduct(stSeries,rowId,null)){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setFocusOnStDom(domElement);
		return false;
	}
	
	endSeries = getEndSerialNumber(stSeries,returnQnty);
	//check length of the series
	if(!isEndSeriesLengthValid(endSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setFocusOnStDom(domElement);
		return false;
	}
	//check duplicate series
	
	if(!checkForDuplicateSerialNumber(stSeries,endSeries,rowId,'returnGrid')){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setFocusOnStDom(domElement);
		return false;
	}
	//make an ajax call
	url="./stockReturnRho.do?submitName=isValidSeriesForStockReturn&startSerialNum="+domElement.value+"&quantity="+returnQnty+"&itemId="+itemId+"&itemDetailsId="+itemDetailsId+"&seriesType="+seriesType+"&transactionNumber="+transactionNumber;
	ajaxJqueryWithRow(url,'stockReturnForm',ajaxResponseForSeries,rowId);
}


function enterKeyforStockReturnQnty(keyCode,rowId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var currentDomId = getDomElementById("rowStartSerialNumber"+rowId);
		var remarksDom = getDomElementById("rowRemarks"+rowId);
		if(currentDomId.readOnly){
			setFocusOnStDom(remarksDom);
			remarksDom.setAttribute("onkeypress","return enterKeyforStockReturn(event.keyCode,\'"+rowId+"\','rowRemarks')");
		}else{
			setFocusOnStDom(currentDomId);
		}
	}
}

function enterKeyforStockReturn(keyCode,rowId,domElemetId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var currentDomId=null;
		var currentDomByName =null;
		var destDomByName =null;
		if(domElemetId =="rowRemarks"){
			currentDomId = getDomElementById("rowRemarks"+rowId);
			currentDomByName = document.getElementsByName("to.rowRemarks");
			destDomByName=document.getElementsByName("to.rowReturningQuantity");
		}else if(domElemetId =="rowReturningQuantity"){
			currentDomId = getDomElementById("rowReturningQuantity"+rowId);
			currentDomByName = document.getElementsByName("to.rowReturningQuantity");
		    destDomByName=document.getElementsByName("to.rowReturningQuantity");
		}
		
		for(var i=0;i<currentDomByName!=null && currentDomByName.length;i++){
			if(currentDomByName[i]==currentDomId){
				//alert("Eql"+domElement.value)
				if(i == currentDomByName.length-1){
					setFocusOnStDomId('Save');
					break;
				}else if((i+1)<currentDomByName.length){
					var j=i+1;
					setFocusOnStDom(destDomByName[j]);
					break;
				}
			}
		}


	}
	
}
//end of Changes by <31913> on 10/01/2013
