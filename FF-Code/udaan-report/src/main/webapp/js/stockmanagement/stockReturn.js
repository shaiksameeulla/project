// Start of Changes by <31913> on 16/12/2012
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
			setTimeout(function(){startSINumDom.focus();},10);
			return false;
		}
		if(isNull(endSINum) ){
			alert("Please provide Start/End Serial Number "+lineNum);
			clearSeries(rowId);
			setTimeout(function(){startSINumDom.focus();},10);
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
	var receivedQnty = parseNumber(receivedQntyDom.value);
	
	var returnQntyDom = getDomElementById("rowReturningQuantity"+rowId);
	var returnQnty = parseNumber(returnQntyDom.value);
	
	var stockQntyDom = getDomElementById("rowCurrentStockQuantity"+rowId);
	var stockQnty=parseNumber(stockQntyDom.value);
	var lineNum=" at line :"+rowId;
	if(isNull(returnQnty)){
		alert("Please provide Returning Quantity "+lineNum);
		clearSeries(rowId);
		setTimeout(function(){returnQntyDom.focus();},10);
		return false;
	}else {
		if(returnQnty>receivedQnty) {
			alert("Returning Quanity can not be more than received Quantity "+lineNum);
			returnQntyDom.value="";
			clearSeries(rowId);
			setTimeout(function(){returnQntyDom.focus();},10);
			return false;
		}
	}
	if(returnQnty>stockQnty) {
		alert("Returning Quanity can not be more than stock Quantity "+lineNum);
		returnQntyDom.value="";
		clearSeries(rowId);
		setTimeout(function(){returnQntyDom.focus();},10);
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
	var ackNum = getDomElementById("stockIssueNumber");
	var retNum = getDomElementById("stockReturnNumber");
	if(isNull(ackNum.value) && isNull(retNum.value)){
		alert("No data avialable.");
		setTimeout(function(){ackNum.focus();},10);
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
			isCheckBoxSelected('to.checkbox') && checkMandatoryForSave()
			){
		
			if(action = "Save"){
				var url="./stockReturn.do?submitName=saveReturnDetails";
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
		url="./stockReturn.do?submitName=viewFormDetails";
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
	var issueNumber = getDomElementById("stockIssueNumber");
	var retNum = getDomElementById("stockReturnNumber");
	
		if(type == 'stockIssue') {
			if(isNull(issueNumber.value)) {
				alert("Please provide Stock Issue Number");
				issueNumber.focus();
				return false;
			}
			url="./stockReturn.do?submitName=findDetailsByIssueNumber";
		}else if(type == 'stockReturn'){
			if(isNull(retNum.value)){
				alert("Please provide Stock Return Number");
				retNum.focus();
				return false;
			}
			url="./stockReturn.do?submitName=findDetailsByReturnNumber";
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
	if(!isNull(transType)){
		
		var returnNumber = getDomElementById("stockReturnNumber");
		returnNumber.setAttribute("readOnly",true);
		returnNumber.setAttribute("tabindex","-1");
		
		var issueNumber = getDomElementById("stockIssueNumber");
		issueNumber.setAttribute("readOnly",true);
		issueNumber.setAttribute("tabindex","-1");
	}
	disableStartSeriesNumber();
	if(!isNewReturn()){

		//step 1: to disable Series fields
		//step 2: to disable Qnties fields
		var tableee = getDomElementById('returnGrid');
		var totalRowCount =  tableee.rows.length;
		for(var i=1;i<totalRowCount;i++) {
			hasSeries(i);
			var retQnty = getDomElementById("rowReturningQuantity"+i);
			var stNumber = getDomElementById("rowStartSerialNumber"+i);
			
			
			retQnty.setAttribute("readOnly",true);
			retQnty.setAttribute("tabindex","-1");
			
			stNumber.setAttribute("readOnly",true);
			stNumber.setAttribute("tabindex","-1");
			
		}
		disableForUser();
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
	var returnQnty=parseNumber(returnQntyDom.value);
	if(isNull(returnQnty)){
		alert("Please provide Quantity at Line :"+rowId);
		clearSeries(rowId);
		return false;
	}
	
	var transactionNumber = getDomElementById("stockIssueNumber").value;
	if(isNull(transactionNumber)){
		alert("Stock Issue number is required to proceed");
		
		clearSeries(rowId);
		return false;
	}
	
	var seriesType = getRowSeriesType(rowId);
	var itemId = getDomElementById("rowItemId"+rowId).value;
	var itemDetailsId = getDomElementById("rowStockReturnItemDtlsId"+rowId).value;
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
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//check series has Branch and Product 
	if(!isSeriesHasBranchProduct(stSeries,rowId,null)){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	
	endSeries = getEndSerialNumber(stSeries,returnQnty);
	//check length of the series
	if(!isEndSeriesLengthValid(endSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//check duplicate series
	
	if(!checkForDuplicateSerialNumber(stSeries,endSeries,rowId,'returnGrid')){
		domElement.value="";
		clearSeries(rowId);
		//to focus on the element  after specified time(in millisecond)
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//make an ajax call
	url="./stockReturn.do?submitName=isValidSeriesForStockReturn&startSerialNum="+domElement.value+"&quantity="+returnQnty+"&itemId="+itemId+"&itemDetailsId="+itemDetailsId+"&seriesType="+seriesType+"&transactionNumber="+transactionNumber;
	ajaxJqueryWithRow(url,'stockReturnForm',ajaxResponseForSeries,rowId);
}


//end of Changes by <31913> on 10/01/2013
