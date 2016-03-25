// Start of Changes by <31913> on 16/11/2012
/**
 * Allows you to tag each variable supported by a function.
 */
var rowId = 1;
/**
 * Start up method for issue to customer/employee form
 */
function issueEmpStartup(){
	var stockIssueId = getDomElementById('stockIssueId');
	if(!isNull(stockIssueId.value)){
		inputDisable();
		dropdownDisable();
	}else{
		var itemId = getDomElementById("itemId");
		if(!isNull(itemId)){
			setFocusOnStDom(itemId);
		}
	}
	hidePrintButton();
}
/**
 * submitForm
 * @param type
 */
function submitForm(type){	
	var url;
	if(checkMandatoryForIssueEmpSave(type)){
		if (type == "Pickup Boy") {
			url="./stockIssueEmployee.do?submitName=saveIssueEmployeeDtls";
		} else { // type == "customer"
			url="./stockIssueCustomer.do?submitName=saveIssueCustomerDtls";
		}
		submitIssueEmp(url,'Submit');
	}
}
/**
 * checkMandatoryForIssueEmpSave
 * @param type
 * @returns {Boolean}
 */
function checkMandatoryForIssueEmpSave(type)
{
	var itemId = getDomElementById("itemId");
	var fieldRep = getDomElementById("recipientId");
	var qty = getDomElementById("issuedQuantity");
	var startSerialNum = getDomElementById("startSerialNumber");
	var endSerialNum = getDomElementById("endSerialNumber").value;
	var stockIssueId = getDomElementById('stockIssueId').value;
	var issueNumber = getDomElementById('stockIssueNumber');
	
	qty.value= trimString(qty.value);
	qty.value=parseNumber(qty.value);
	
	if(isNull(itemId.value)){
		alert("Please select series");
		setFocusOnStDom(itemId);
		return false;
	}
	if(isNull(fieldRep.value)){
		alert("Please select "+type+" from list");
		setFocusOnStDom(fieldRep);
		return false;
	}
	if(isNull(qty.value)){
		alert("Please provide quantity");
		setFocusOnStDom(qty);
		return false;
	}
	
		if(isNull(startSerialNum.value)){
			alert("Please provide Start Serial Number");
			setFocusOnStDom(startSerialNum);
			return false;
		}
		if(isNull(endSerialNum)){
			alert("Please provide Start/end Serial Number");
			startSerialNum.value="";
			setFocusOnStDom(startSerialNum);
			return false;
		}
		
		if(isNull(stockIssueId)){
			issueNumber.value="";
		}
		if(!checkStockForIssueEmp()){
			return false;
		}
	return true;
}
/**
 * submitIssueEmp
 * @param url
 * @param action
 */
function submitIssueEmp(url,action) {
	
	if(confirm("Do you want to "+action+" the details?")) {
		document.stockIssueEmpForm.action = url;
		document.stockIssueEmpForm.method = "post";
		document.stockIssueEmpForm.submit();
	}
}
/**
 * clearScreen
 * @param type
 */
function clearScreen(type)
{
	if (type == "stockIssueEmp") {
		url="./stockIssueEmployee.do?submitName=viewFormDetails";
	} 
	else {// type == "stockIssueCust"
		url="./stockIssueCustomer.do?submitName=viewFormDetails";
	}
	submitIssueEmp(url,'Clear');
}
/**
 * find
 * @param val
 * @returns {Boolean}
 */
function find(val)
{
	var issNum = getDomElementById("stockIssueNumber");
	if(isNull(issNum.value)) {
		alert("Please enter Issue Number");
		setFocusOnStDom(issNum);
		return false;
	}
	if(val=='issueToCust'){
		url = "./stockIssueCustomer.do?submitName=findIssueCustomerDtls";
	} else if(val=='issueToEmp'){
		url = "./stockIssueEmployee.do?submitName=findIssueEmployeeDtls";
	} else {
		alert("Invalid Input details");
		return false;
	}
	submitIssueEmpWithoutPrompt(url);
}
/**
 * submitIssueEmpWithoutPrompt
 * @param url
 */
function submitIssueEmpWithoutPrompt(url) { //For Stock issue to cust./emp.
	
	document.stockIssueEmpForm.action = url;
	document.stockIssueEmpForm.method = "post";
	document.stockIssueEmpForm.submit();
}
/**
 * getItemDtls
 * @param domValue
 */
function getItemDtls(domValue)
{
	clearItemDetls();
	var issueId = getDomElementById("stockIssueId").value; 
	var issueNum = getDomElementById("stockIssueNumber");
	if(isNull(issueId)){
		issueNum.value="";
		issueNum.setAttribute("readOnly",true);
		issueNum.setAttribute("tabindex","-1");
	}
	
	if(!isNull(domValue.value)){
		var url="./stockIssueCustomer.do?submitName=ajaxGetAllItemDetails&itemId="+domValue.value+"&Issue=true";
		ajaxJquery(url,'stockIssueEmpForm',ajaxRespIssueItem);
	}
}

/**
 * ajaxRespIssueItem
 * @param ajaxResp
 * @returns {Boolean}
 */

function ajaxRespIssueItem(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[error_flag];
		if(responseText!=null && !isNull(error)){
			alert(error);
			 getDomElementById("itemId").value="";
			clearItemDetls();
			return false;
		}else if(!isNull(responseText)){
			populateItemDetls(responseText);
			return true;
		}else{
			 getDomElementById("itemId").value="";
			alert("Invalid Item/Material Details");
			clearItemDetls();
			return false;
		}

	}else{
		alert("Invalid Item/Material Details");
		 getDomElementById("itemId").value="";
		clearItemDetls();
		return false;
	}
	
}
/**
 * populateItemDetls
 * @param resultTo
 */
function populateItemDetls(resultTo){
	getDomElementById('seriesLength').value=resultTo.seriesLength;
	getDomElementById('isItemHasSeries').value=resultTo.itemTypeTO.itemHasSeries;
	getDomElementById("currentStockQuantity").value=resultTo.stockQuantity;
	getDomElementById('itemSeries').value=resultTo.itemSeries;
	getDomElementById("seriesType").value=resultTo.seriesType;
}

/**
 * clearItemDetls
 */
function clearItemDetls(){
	getDomElementById('seriesLength').value='';
	getDomElementById('isItemHasSeries').value='';
	getDomElementById("currentStockQuantity").value='';
	getDomElementById('itemSeries').value='';
	getDomElementById("seriesType").value='';
	clearSeriesWithoutId();
}
/**
 * clearSeriesWithoutId
 */
function clearSeriesWithoutId(){
	getDomElementById("startSerialNumber").value='';
	getDomElementById("endSerialNumber").value='';
	getDomElementById('officeProduct').value="";
	getDomElementById('startLeaf').value="";
	getDomElementById('endLeaf').value="";
}
/**
 * checkStockForIssueEmp
 * @returns {Boolean}
 */
function checkStockForIssueEmp(){
	
	var itemDom = getDomElementById("itemId");
	var item=itemDom.value;
	
	if(isNull(item)){
		alert("Please select the material");
		setFocusOnStDom(itemDom);
		return false;
	}
	var stockQntyDom = getDomElementById("currentStockQuantity");
	var issueQntyDom = getDomElementById("issuedQuantity");
	var issQnty=parseNumber(issueQntyDom.value);
	var stock=parseNumber(stockQntyDom.value);
	if(stockQntyDom!=null && isNull(stock)){
		alert("Stock Quantity is zero ");
		issueQntyDom.value="";
		setFocusOnStDom(issueQntyDom);
		return false;
	}
	if(isNull(issQnty)){
		alert("Please provide Issue Quantity");
		issueQntyDom.value="";
		setFocusOnStDom(issueQntyDom);
		return false;
	}
	
	if(stock < issQnty){
		alert("Issue Quantity can not be more than Stock Quantity");
		issueQntyDom.value="";
		setFocusOnStDom(issueQntyDom);
		return false;
	}
	
	return true;
}
/**
 * validateQuantity
 */
function validateQuantity(){
	if(checkStockForIssueEmp()){
		validateSeries( getDomElementById('startSerialNumber'),'Customer');
	}else{
	clearSeriesWithoutId();
	}
}
/**
 * validateSeries
 * @param domElement
 * @param type
 * @returns {Boolean}
 */
function validateSeries(domElement,type){
	
	var itemIdDom = getDomElementById("itemId");
	var itemId = itemIdDom.value;
	var repIdDom = getDomElementById("recipientId");
	var repId = repIdDom.value;
	var issuedType = getIssuedToType();
	var seriesType =  getDomElementById("seriesType").value;

	var stSeries = domElement.value;
	
	getDomElementById('endSerialNumber').value="";
	var issQntyDom = getDomElementById("issuedQuantity");
	 issQntyDom.value = parseNumber(issQntyDom.value);
	var issQnty = issQntyDom.value;
	
	var endSeries = null;
	if(isNull(itemId)){
		alert("Please select CNote details ");
		setFocusOnStDom(itemIdDom);
		return false;
	}
	if(isNull(repId)){
		alert("Please select "+type+" details ");
		repIdDom.value="";
		setFocusOnStDom(repIdDom);
		return false;
	}
	if(isNull(issQnty)){
		clearSeriesWithoutId();
		alert("Please enter the quantity");
		issQntyDom.value="";
		setFocusOnStDom(issQntyDom);
		return false;
	}
	//stock Comparision
	if(!checkStockForIssueEmp()){
		return false;
	}
	
	stSeries = convertToUpperCase(stSeries);
	if(isNull(stSeries)){
		clearSeriesWithoutId();
		return false;
	}
	//check length of the series
	if(!isSeriesLengthValidWithoutRowId(stSeries)){
		domElement.value="";
		clearSeriesWithoutId();
		setFocusOnStDom(domElement);
		return false;
	}
	//check series has Branch and Product 
	if(!checkSeriesFormat(stSeries)){
		domElement.value="";
		clearSeriesWithoutId();
		setFocusOnStDom(domElement);
		return false;
	}
	
	//check generated series length
	endSeries= getEndSerialNumber(stSeries,issQnty);
	if(isNull(endSeries)){
		alert("Invalid Series for Issue");
		domElement.value="";
		clearSeriesWithoutId();
		setFocusOnStDom(domElement);
		return false;
	}
	if(!isEndSeriesLengthValidWithoutId(endSeries)){
		domElement.value="";
		clearSeriesWithoutId();
		setFocusOnStDom(domElement);
		return false;
	}
	
	if(!isValidCnoteForIssueToParty(null,domElement.value)){
		domElement.value="";
		clearSeriesWithoutId();
		setFocusOnStDom(domElement);
		return false;
	}
	//check duplicate series
	
	//make an ajax call
	if(!isNull(stSeries) && !isNull(issQnty) && !isNull(issuedType) 
			&& !isNull(itemId) && !isNull(repId)){
		url="./stockIssue.do?submitName=isValidSeriesForIssue&startSerialNum="+domElement.value+"&quantity="+issQnty+"&issuedTOType="+issuedType+"&itemId="+itemId+"&partyTypeId="+repId+"&itemDetailsId="+getStockIssueDtlsValue()+"&seriesType="+seriesType;
		ajaxJquery(url,'stockIssueForm',ajaxResponseForIssueSeries);
	}
}
/**
 * for validating series format for the screen IssutTopickupboy/customer
 * @param series
 * @returns {Boolean}
 */
function checkSeriesFormat(series){
	//var branchProduct = getBranchProduct(rowId);
	var branchProduct = getDomElementById("loggedInOfficeCode").value;
	var product=getItemSeriesWithoutId();	
	if(isNull(product)){
		product =branchProduct;
		//commented to fix Browser issues
		//if(series.startsWith(branchProduct)){ 
			if(checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Normal cnote(s) should not contain product ");
				return false;
			}
		}
	}else{
		product= trimString(product);
		branchProduct =getRhoCode()+product;
	}
	//if(!series.startsWith(branchProduct)){
		if(!checkStartsWith(series,branchProduct)){
		alert("series must start with :"+branchProduct);
		clearSeriesWithoutId();
		setFocusOnStDomId("startSerialNumber");
		return false;
	}
	
	return true;
}
/**
 * ajaxResponseForIssueSeries
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajaxResponseForIssueSeries(ajaxResp) {
	var stSeries=getDomElementById("startSerialNumber");
	var endSeries=getDomElementById("endSerialNumber");
	var officeProduct=getDomElementById('officeProduct');
	var startLeaf=getDomElementById('startLeaf');
	var endLeaf=getDomElementById('endLeaf');
	

	if (!isNull(ajaxResp)) {
		rowItemList = ajaxResp;
		var responseText=jsonJqueryParser(ajaxResp);
		var error = responseText[error_flag];
		var result = responseText[success_flag];
		
		if(isNull(error) && isNull(result)){
			alert("Error in response");
			clearSeriesWithoutId();
			setFocusOnStDom(stSeries);
			return false;
		}
		if(!isNull(error)){
			alert(error);
			clearSeriesWithoutId();
			setFocusOnStDom(stSeries);
			return false;
		}
		if(!isNull(result)){
			//alert("success"+result);
			result=result.split(",");
			stSeries.value=result[0];
			endSeries.value=result[1];
			officeProduct.value=result[2];
			startLeaf.value=result[3];
			endLeaf.value=result[4];
		}
	} else {
		alert("Invalid Series ,please provide correct series");
		clearSeriesWithoutId();
		setFocusOnStDom(stSeries);
		return false;
	}

}
/**
 * getStockIssueDtlsValue
 * @returns stockIssueItemDtlsId
 */
function getStockIssueDtlsValue(){
	return getDomElementById("stockIssueItemDtlsId").value;
}
function getSelectedCustomerType(customerDOM){
	var customerId=customerDOM.value;
	clearCustomerType();
	clearShippedToCode();
	if(!isNull(customerId)){
		url="./stockIssueCustomer.do?submitName=ajaxGetCustomerDetailsById&partyTypeId="+customerId;
		ajaxJquery(url,'stockIssueEmpForm',ajaxResponseForCustomerType);
	}
}
function ajaxResponseForCustomerType(rspObject){
	var customerType=getDomElementById("customerType");
	var customerShippedCode=getDomElementById("shippedToCode");
	var stockUserTo=null;
	if(!isNull(rspObject)){
		stockUserTo = jsonJqueryParser(rspObject);
		if(stockUserTo!=null && stockUserTo[error_flag]!=null){
			alert(stockUserTo[error_flag]);
			return false;
		}
		if(!isNull(stockUserTo) && !isNull(stockUserTo.customerTypeTO)){
		customerType.value=stockUserTo.customerTypeTO.customerTypeCode;
		if(customerShippedCode!=null){
			customerShippedCode.value=stockUserTo.shippedToCode;
		}
		}else {
			clearShippedToCode();
			customerType.value="";
			alert("Invalid customer Details");
		}
	}else{
		clearCustomerType();
	}
}

function clearCustomerType(){
	getDomElementById("customerType").value="";
	clearSeriesWithoutId();
}