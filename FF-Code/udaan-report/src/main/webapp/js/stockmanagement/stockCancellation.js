// Start of Changes by <31913> on 01/01/2013
/**
 * Allows you to tag each variable supported by a function.
 */
var rowId = 1;

/**
 * validationBeforSave
 * @returns {Boolean}
 */
function validationBeforSave(){

	if(!validateHeader()){
		return false;
	}
	if(!validateCancelledQnty()){
		return false;
	}
	var startCNNo = getDomElementById("rowStartSerialNumber1");
	if(isNull(startCNNo.value)){
		alert("Please provide Start Serial Number");
		clearCancelledSeriesDtls();
		setTimeout(function(){startCNNo.focus();},10);
		return false;
	}
	var endCNNo = getDomElementById("rowEndSerialNumber1");
	if(isNull(endCNNo.value)){
		clearCancelledSeriesDtls();
		alert("Please provide Start Serial Number");
		return false;
	}
	var cancelId = getDomElementById("stockCancelledId").value;
	if(isNull(cancelId)){
		 getDomElementById("cancellationNo").value="";
	}
	return true;
}
/**
 * validateHeader 
 * @returns {Boolean}
 */
function validateHeader(){
	var itemId = getDomElementById("itemId");
	if(isNull(itemId.value)){
		alert("Please select Material");
		setTimeout(function(){itemId.focus();},10);
		return false;
	}
	var reason = getDomElementById("reason");
	if(isNull(reason.value)){
		alert("Please provide Reason.");
		setTimeout(function(){reason.focus();},10);
		return false;
	}
	var qty = getDomElementById("quantity");
	if(isNull(qty.value)){
		alert("Please provide Quantity.");
		clearCancelledSeriesDtls();
		setTimeout(function(){qty.focus();},10);
		return false;
	}
	return true;
}

/**
 * save
 * @param action
 */
function save(action) {
	
	if(validationBeforSave()){
		if(action = "Save"){
			var url="./stockCancel.do?submitName=saveCancellation";
			submitStockCancel(url,'Save');
		}
	}
}
/**
 * submitStockCancel
 * @param url
 * @param action
 */
function submitStockCancel(url,action) {
	
	if(confirm("Do you want to "+action+" the Stock Cancellation details?")) {
		document.stockCancellationForm.action = url;
		document.stockCancellationForm.submit();
	}
}
/**
 * clearScreen
 * @param type
 */
function clearScreen(type) {
	
	var url;
	if(type == "stockCancel") {
		url="./stockCancel.do?submitName=viewFormDetails";
		submitStockCancel(url,'Clear');
	} 
}

/**
 * find
 * @param type
 * @returns {Boolean}
 */
function find(type) {
	
	var url = null;
	var cancelNo = getDomElementById("cancellationNo");
	
		if(type == 'stockCancel') {
			if(isNull(cancelNo.value)) {
				alert("Please provide Cancellation Number");
				cancelNo.focus();
				return false;
			}
			url="./stockCancel.do?submitName=findDetailsByCancellationNumber";
		}else {
			alert("Invalid Input details");
			return false;
		}
		submitCancellation(url);
}
/**
 * submitCancellation
 * @param url
 */
function submitCancellation(url) {
	
	document.stockCancellationForm.action = url;
	document.stockCancellationForm.method = "post";
	document.stockCancellationForm.submit();
}
/**
 * cancelSeriesValdiationWrapper :: it's wrapper for serial number validations
 */
function cancelSeriesValdiationWrapper(){
	validateSeries(getDomElementById("rowStartSerialNumber1"));
}
/**
 * validateSeries
 * @param domValue
 * @returns {Boolean}
 */
function validateSeries(domValue){
	
	var stSeries = domValue.value;
	var issuedType= getDomElementById("issuedToType").value;
	var officeId  = getDomElementById("loggedInOfficeId").value;
	var seriesType= getDomElementById("rowSeriesType1").value;
	if(!validateHeader()){
		return false;
	}
	if(!validateCancelledQnty()){
		return false;
	}
	var itemId = getDomElementById("itemId");
	var quantity = getDomElementById("quantity");
	
	stSeries = convertToUpperCase(stSeries);
	if(isNull(stSeries)){
		clearCancelledSeriesDtls();
		return false;
	}
	//check length of the series
	if(!isSeriesLengthValid(stSeries,rowId)){
		domValue.value="";
		clearCancelledSeriesDtls();
		setTimeout(function(){domValue.focus();},10);
		return false;
	}
	//check series has Branch and Product 
	if(!isSeriesHasBranchProduct(stSeries,rowId,null)){
		domValue.value="";
		clearCancelledSeriesDtls();
		setTimeout(function(){domValue.focus();},10);
		return false;
	}
	
	//make an ajax call
	if(!isNull(stSeries)){	
		url="./stockIssue.do?submitName=isValidSeriesForIssue&startSerialNum="+domValue.value+"&quantity="+quantity.value+"&issuedTOType="+issuedType+"&itemId="+itemId.value+"&partyTypeId="+officeId+"&itemDetailsId=''"+"&seriesType="+seriesType+"&issuedTO="+getBranchCode().value;
		ajaxJquery(url,'stockIssueForm',ajaxResponseForSeriesCancel);
	} 
}
/**
 * ajaxResponseForSeriesCancel
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajaxResponseForSeriesCancel(ajaxResp){
	var stSeries=getDomElementById("rowStartSerialNumber1");
	//alert("ajaxResp"+ajaxResp);
	if (!isNull(ajaxResp)) {
		rowItemList = ajaxResp;
		var responseText=jsonJqueryParser(ajaxResp);
		var error = responseText[error_flag];
		var result = responseText[success_flag];

		if(isNull(error) && isNull(result)){
			alert("Error in response");
			clearCancelledSeriesDtls();
			setTimeout(function(){stSeries.focus();},10);
			return false;
		}
		if(!isNull(error)){
			alert(error);
			clearCancelledSeriesDtls();
			setTimeout(function(){stSeries.focus();},10);
			return false;
		}
		if(!isNull(result)){
			populateSeriesResponse(result,1);
		}
	}else{
		alert("Invalid Series ,please provide correct series");
		clearCancelledSeriesDtls();
		setTimeout(function(){stSeries.focus();},10);
		return false;
	}

}

/**
 * 
 * @param domValue
 */
function getItemDtls(domValue){
	clearCancelledSeriesDtls();
	if(!isNull(domValue.value)){
		var url="./stockIssueCustomer.do?submitName=ajaxGetAllItemDetails&itemId="+domValue.value+"&Issue=true";
		ajaxJquery(url,'stockCancellationForm',ajaxRespIssueItem);
	}
}
/**
 * ajaxRespIssueItem
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajaxRespIssueItem(ajaxResp){
	//alert('AJAX Successful Response:');
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[error_flag];
		if(responseText!=null && !isNull(error)){
			alert(error);
			getDomElementById("itemId").value="";
			clearItemDetls();
			return false;
		}else if(!isNull(responseText)){
			populateMaterialDtls(responseText);
			return true;
		}else{
			getDomElementById("itemId").value="";
			alert("Invalid Item/Material Details");
			clearMaterialDtls();
			return false;
		}

	}else{
		alert("Invalid Item/Material Details");
		getDomElementById("itemId").value="";
		clearMaterialDtls();
		return false;
	}
}
/**
 * populateMaterialDtls
 * @param itemTo
 */
function populateMaterialDtls(itemTo){
	getDomElementById("rowSeries1").value=itemTo.itemSeries;
	getDomElementById("rowSeriesLength1").value=itemTo.seriesLength;
	getDomElementById("rowIsItemHasSeries1").value=itemTo.itemTypeTO.itemHasSeries;
	getDomElementById("rowCurrentStockQuantity1").value=itemTo.stockQuantity;
	getDomElementById("rowSeriesType1").value=itemTo.seriesType;
}
/**
 * clearMaterialDtls
 */
function clearMaterialDtls(){
	getDomElementById("rowSeries1").value='';
	getDomElementById("rowSeriesLength1").value='';
	getDomElementById("rowIsItemHasSeries1").value='';
	getDomElementById("rowCurrentStockQuantity1").value='';
	getDomElementById("rowSeriesType1").value='';
}
/**
 * clearCancelledSeriesDtls
 */
function clearCancelledSeriesDtls(){
	getDomElementById("rowOfficeProduct1").value='';
	getDomElementById("rowStartLeaf1").value='';
	getDomElementById("rowEndLeaf1").value='';
	getDomElementById("rowStartSerialNumber1").value='';
	getDomElementById("rowEndSerialNumber1").value='';
}
/**
 * validateCancelledQnty
 * @returns {Boolean}
 */
function validateCancelledQnty(){
	var qtyDom = getDomElementById("quantity");
	var qty = qtyDom.value;
	if(isNull(qty)){
		clearCancelledSeriesDtls();
		return false;
	}
	qty = parseNumber(qty);
	var stokQnty = getDomElementById("rowCurrentStockQuantity1").value;
	stokQnty = parseNumber(stokQnty);
	if(qty>stokQnty){
		alert("Quantity can not be greater than stock quantity");
		qtyDom.value="";
		clearCancelledSeriesDtls();
		return false;
	}
	return true;
}
/**
 * cancellationStartup
 */
function cancellationStartup(){
	var cancelId = getDomElementById("stockCancelledId");
	if(!isNull(cancelId.value))	{
		disableAll();
		getDomElementById("Cancel").disabled=false;
		var itemDom = getDomElementById("itemId");
		modifyLabel(itemDom);
	}
}
var globalStartNo="Start SI No";
var globalEndNo="End SI No";
function modifyLabel(itemDom){
	var startCnDom = getDomElementById("startCnName");
	var endCnDom = getDomElementById("endCnName");
	var materialName=null;
	if(!isNull(itemDom.value)){
		materialName=getSelectedDropDownTextByDOM(itemDom);
		startCnDom.innerHTML=materialName +" Start No";
		endCnDom.innerHTML=materialName +" End No";
	}else{
		startCnDom.innerHTML=globalStartNo;
		endCnDom.innerHTML=globalEndNo;
	}
	return true;
}
//end of Changes by <31913> on 08/01/2013