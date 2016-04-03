// Start of Changes by <31913> on 16/11/2012
/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;

/**
 * fnClickAddRow : for Add row functionality
 */
function fnClickAddRow(){
	var tempRow=rowCount;
	$('#issueGrid').dataTable().fnAddData( [
	    '<input type="checkbox" id="checkbox'+ rowCount +'" name="to.checkbox" value="'+rowCount+'" tabindex="-1" />',
		rowCount,
		'<select name="to.rowItemTypeId" id="rowItemTypeId'+ rowCount +'" onchange="getItemList(this);clearSeriesForIssue(\''+ tempRow +'\')" class="selectBox width170" onkeypress = "return enterKeyNav(\'rowItemId'+ rowCount +'\',event.keyCode);" ><option value="">--Select--</option></select>',
		'<select name="to.rowItemId" id="rowItemId'+ rowCount +'" onchange="getItemDetails(this);clearSeriesForIssue(\''+ tempRow +'\')" class="selectBox width170" onkeypress = "return enterKeyNav(\'rowRequestedQuantity'+ rowCount +'\',event.keyCode);"><option value="">--Select--</option></select>',
		'<input type="text" id="rowDescription'+ rowCount +'" name="to.rowDescription" readOnly="true" tabindex="-1" class="txtbox width80"/>',
		'<input type="text" id="rowUom'+ rowCount +'" name="to.rowUom" readOnly="true" tabindex="-1" class="txtbox width80" />',
		'<input type="text" id="rowRequestedQuantity'+ rowCount +'" name="to.rowRequestedQuantity" onkeypress="return onlyNumeric(event)" maxlength="6" onkeydown="return enterKeyNav(\'rowApprovedQuantity'+ rowCount +'\',event.keyCode)" class="txtbox width80"/>',
		'<input type="text" id="rowApprovedQuantity'+ rowCount +'" name="to.rowApprovedQuantity" onkeypress="return onlyNumeric(event)" maxlength="6" onkeydown="return enterKeyNav(\'rowIssuedQuantity'+ rowCount +'\',event.keyCode)" class="txtbox width80"/>',
		'<input type="text" id="rowIssuedQuantity'+ rowCount +'" name="to.rowIssuedQuantity" onkeypress="return onlyNumeric(event)" maxlength="6" onchange="checkStockQnatity(this);seriesValidationWrapper(\''+ tempRow +'\');" onblur="isNonSerializedItem(\''+ tempRow +'\')" class="txtbox width80" onkeydown="return enterKeyNav(\'rowStartSerialNumber'+ rowCount +'\',event.keyCode)"/>',
		'<input type="text" id="rowStartSerialNumber'+ rowCount +'" name="to.rowStartSerialNumber" onchange="validateSeries(this)" maxlength="12" class="txtbox width140" onkeydown="return enterKeyNav(\'rowRemarks'+ rowCount +'\',event.keyCode)"/>',
		'<input type="text" id="rowEndSerialNumber'+ rowCount +'" name="to.rowEndSerialNumber" readOnly="true" tabindex="-1" maxlength="12" class="txtbox width140"/>',
		'<input type="text" id="rowRatePerUnitQuantity'+ rowCount +'" name="to.rowRatePerUnitQuantity" readOnly="true" tabindex="-1" maxlength="12" class="txtbox width140"/>',
		'<input type="text" id="rowTotalPrice'+ rowCount +'" name="to.rowTotalPrice" readOnly="true" tabindex="-1" maxlength="12" class="txtbox width140"/>',
		'<input type="text" id="rowRemarks'+ rowCount +'" name="to.rowRemarks" maxlength="20" class="txtbox width100"/> \
		<input type="hidden" id="rowStockIssueItemDtlsId'+ rowCount +'" name="to.rowStockIssueItemDtlsId" value="0"/> \
		<input type="hidden" id="rowSeries'+ rowCount +'" name="to.rowSeries" />\
		<input type="hidden" id="rowSeriesLength'+ rowCount +'" name="to.rowSeriesLength" />\
		<input type="hidden" id="rowCurrentStockQuantity'+ rowCount +'" name="to.rowCurrentStockQuantity" />\
		<input type="hidden" id="rowSeriesType'+ rowCount +'" name="to.rowSeriesType" />\
		<input type="hidden" id="rowIsItemHasSeries'+ rowCount +'" name="to.rowIsItemHasSeries" />\
		<input type="hidden" id="rowOfficeProduct'+ rowCount +'" name="to.rowOfficeProduct" />\
		<input type="hidden" id="rowStartLeaf'+ rowCount +'" name="to.rowStartLeaf" />\
		<input type="hidden" id="rowEndLeaf'+ rowCount +'" name="to.rowEndLeaf" />',
	] );
	rowCount++;
	populateReqDropdown(rowItemTypeList,tempRow,'rowItemTypeId');
	focusOnItemType(tempRow);
}

//#############Global Methods Starts###########
/**
 * isNonSerializedItem : check whether specified row item has series or not.
 * @param rowId
 */
function isNonSerializedItem(rId){
	var rowId=parseInt(rId);
	var startSerialNum = getDomElementById("rowStartSerialNumber"+rowId);
	if(startSerialNum!=null && startSerialNum.readOnly){
		var tableee = document.getElementById('issueGrid');
		var totalRowCount = tableee.rows.length-1;

		if(rowId== totalRowCount){
			if(checkMandatoryForAdd(rowId) && isNewIssue()){
				addNewRow();
			}
		}
	}
	
}
/**
 * submitIssue :submits the form
 * @param url
 * @param action
 */
function submitIssue(url,action) {
	
	if(confirm("Do you want to "+action+" the details?")) {
		dropdownEnable();
		document.stockIssueForm.action = url;
		document.stockIssueForm.submit();
	}
}
//#############Global Methods END###########
/**
 * checkMandatoryForAdd : validating mandatory fields before adding new row
 * @param rowId 
 */
function checkMandatoryForAdd(rowId) {
	
	var itemType = getDomElementById("rowItemTypeId"+rowId);
	var item = getDomElementById("rowItemId"+rowId);
	var desc = getDomElementById("rowDescription"+rowId);
	var uom = getDomElementById("rowUom"+rowId);
	var reqQntyDom = getDomElementById("rowRequestedQuantity"+rowId);
	var apprvQntyDom = getDomElementById("rowApprovedQuantity"+rowId);
	var issQntyDom= getDomElementById("rowIssuedQuantity"+rowId);
	
	var ratePerQnty = getDomElementById("rowRatePerUnitQuantity"+rowId);
	
	var reqQnty =parseNumber(reqQntyDom.value);
	var apprvQnty = parseNumber(apprvQntyDom.value);
	var issQnty = parseNumber(issQntyDom.value);
	
	var startSerialNum = getDomElementById("rowStartSerialNumber"+rowId);
	var endSerialNum = getDomElementById("rowEndSerialNumber"+rowId);
	//var remarks = getDomElementById("rowRemarks"+rowId);
	var lineNum="at line :"+rowId;
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
	if(isNull(reqQnty)){
		alert("Please provide Requisition Quantity "+lineNum);
		setTimeout(function(){reqQntyDom.focus();},10);
		return false;
	}
	if(isNull(apprvQnty)){
		alert("Please provide Approved Quantity "+lineNum);
		setTimeout(function(){apprvQntyDom.focus();},10);
		return false;
	} 
		if(reqQnty<apprvQnty) {
			alert("Approved Quanity can not be more than requested Quantity "+lineNum);
			apprvQntyDom.value="";
			setTimeout(function(){apprvQntyDom.focus();},10);
			return false;
		}
	
	if(isNull(issQnty)){
		alert("Please provide Issued Quantity "+lineNum);
		setTimeout(function(){issQntyDom.focus();},10);
		return false;
	}
	if(apprvQnty < issQnty){
		alert("Issued Qunantity can not be more than Apptoved Quantity "+lineNum);
		issQntyDom.value="";
		setTimeout(function(){issQntyDom.focus();},10);
		return false;
	}
	if(ratePerQnty!=null && getIssuedToType()==getIssuedTypeBA().value){
		if(isNull(ratePerQnty.value)){
			alert("Rate details does not exist for the material at Line: "+lineNum+"\n please select another material");
			setTimeout(function(){item.focus();},10);
			return false;
		}
	}
	if(!startSerialNum.readOnly){
		if(isNull(startSerialNum.value)){
			alert("Please provide Start Serial Number "+lineNum);
			setTimeout(function(){startSerialNum.focus();},10);
			return false;
		}
		if(isNull(endSerialNum.value)){
			alert("Please provide valid start/End Serial Number "+lineNum);
			startSerialNum.value="";
			endSerialNum.value="";
			setTimeout(function(){startSerialNum.focus();},10);
			return false;
		}
	}
	
	
	//BR: only for Issue Against Requisition
	if(!checkBalanceQntyForPR(rowId)){
		return false;
	}
	return true;
}
/**
 * checkMandatoryForSave
 * @returns {Boolean}
 */
function checkMandatoryForSave() {

	var tableee = document.getElementById('issueGrid');
	
	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.checkbox');
	for(var i=1;i<totalRowCount;i++) {
		if(cell[i-1].checked){
			rowId = getRowId(cell[i-1],"checkbox");
			if(!checkMandatoryForAdd(rowId)) {
				return false;
			}
			
		}
	}
	return true;
}
/**
 * addNewRow
 */
function addNewRow() {
	
	if(checkMandatoryForSave()&& isNewIssue()) {
		fnClickAddRow();
	}
}
/**
 * issueStartup : it called onpage load
 */
function issueStartup() {
	if(isFromDomElement()){
		getItemTypeList('issueGrid');
		fnClickAddRow();
	}else{
		dropdownDisable();
	}
	disableAfterSave();
	hidePaymntDtls();
}
/**
 * save
 * @param action
 */
function save(action) {
	
	if(validateHeader() && updatedCheckBoxValues('to.checkbox')&& isCheckBoxSelected('to.checkbox')&& checkMandatoryForSave()&& isValidToSave()) {
		var url="./stockIssue.do?submitName=saveIssueDtls";
		submitIssue(url,action);
	}
}
/**
 * clearScreen : for page reload
 * @param type
 */
function clearScreen(type) {
	var url;
	if(type == "stockIssue") {
		url="./stockIssue.do?submitName=viewFormDetails";
		submitIssue(url,'Clear');
	} 
	
	
}
/**
 * find : for find funtionality
 * @param val
 * @returns {Boolean}
 */
function find(val) {

	var url = null;
	var reqNum = getDomElementById("requisitionNumber");
	var issueNum = getDomElementById("stockIssueNumber");

	if(val == 'issue') {
		if(isNull(issueNum.value)){
			alert("Please enter Issue Number");
			issueNum.focus();
			return false;
		}
		url="./stockIssue.do?submitName=findDetailsByIssueNumber";
	}else if(val == 'req') {
		if(isNull(reqNum.value)){
			alert("Please enter Requisition Number");
			reqNum.focus();
			return false;
		}
		url="./stockIssue.do?submitName=findDetailsByRequisitionNumber";
	} else {
		alert("Invalid Input details");
		return false;
	}
	submitIssueWithoutPromp(url);
}
/**
 * submitIssueWithoutPromp
 * @param url
 */
function submitIssueWithoutPromp(url) {
	
	document.stockIssueForm.action = url;
	document.stockIssueForm.method = "post";
	document.stockIssueForm.submit();
}
/**
 * isNewIssue : checks whether data available in the form is  new data or existing data(which is retrieved from DB)
 * @returns {Boolean}
 */
function isNewIssue() {
	
	var issue = getDomElementById("stockIssueId");
	if(isNull(issue.value)) {
		return true;
	}
	return false;
}
/**
 * validateSeries :for series validations
 * @param domElement
 * @returns {Boolean}
 */
function validateSeries(domElement){
	var rowId = getRowId(domElement,"rowStartSerialNumber");
	var endSeries=null;
	getDomElementById("rowEndSerialNumber"+rowId).value="";//clear end serial number
	var stSeries =domElement.value;
	var issuedType = getIssuedToType();
	var partyTypeId = getDomElementById("recipientId").value;
	var issQntyDom = getDomElementById("rowIssuedQuantity"+rowId);
	var issQnty=parseNumber(issQntyDom.value);
	if(isNull(issQnty)){
		alert("Please provide Quantity at Line :"+rowId);
		clearSeries(rowId);
		issQntyDom.focus();
		return false;
	}
	var seriesType = getRowSeriesType(rowId);
	
	var itemId = getDomElementById("rowItemId"+rowId).value;
	var itemDetailsId = getDomElementById("rowStockIssueItemDtlsId"+rowId).value;
	if(!validateHeader()){
		clearSeries(rowId);
		return false;
	}
	stSeries = convertToUpperCase(stSeries);
	
	if(!isValidCnoteForIssueToParty(rowId,stSeries)){
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	
	if(!checkSeriesForBA(rowId)){
		alert("entered series is not allowed for Business Associate");
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	
	if(isNull(stSeries)){
		clearSeries(rowId);
		return false;
	}
	//check length of the series
	if(!isSeriesLengthValid(stSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//check series has Branch and Product 
	if(!isSeriesHasBranchProduct(stSeries,rowId,'issue')){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	
	endSeries = getEndSerialNumber(stSeries,issQnty);
	//check length of the series
	if(!isEndSeriesLengthValid(endSeries,rowId)){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//check duplicate series
	
	if(!checkForDuplicateSerialNumber(stSeries,endSeries,rowId,'issueGrid')){
		domElement.value="";
		clearSeries(rowId);
		setTimeout(function(){domElement.focus();},10);
		return false;
	}
	//make an ajax call
	url="./stockIssue.do?submitName=isValidSeriesForIssue&startSerialNum="+domElement.value+"&quantity="+issQnty+"&issuedTOType="+issuedType+"&itemId="+itemId+"&partyTypeId="+partyTypeId+"&itemDetailsId="+itemDetailsId+"&seriesType="+seriesType+"&issuedTO="+getReceipientCode();
	ajaxJqueryWithRow(url,'stockIssueForm',ajaxResponseForSeries,rowId);
	
	

	
}

/**
 * getReceipientCode
 */
function getReceipientCode(){
	return getDomElementById("recipientCode").value;
}

	
	
// to populate code and name( for issued to)
/**
 * @deprecated 
 */
function getReceiverDetails(domElem){
	var receiverCode=domElem.value;
	var issueTo = getIssuedToTypeDOM();
	if(isNull(issueTo.value)){
		alert("Please select Issued-TO type");
		issueTo.focus();
		clearReceiverDetails();
		return false;
	}
	clearReceiverDetails();
	if(!isNull(receiverCode)){
		var url="./stockIssue.do?submitName=ajaxGetReceivingTypeByCode&code="+receiverCode+"&receivingType="+issueTo.value;
		ajaxJquery(url,'stockIssueForm',ajxRespForReceivingType);
	}
	
}
/**
 * clearReceiverDetails
 */
function clearReceiverDetails(){
	 getDomElementById("recipientId").value="";
	 //getDomElementById("recipientCode").value="";
	 //getDomElementById("recipientName").value="";
}


/**@deprecated 
 * ajxRespForReceivingType : for IssuedTo dropdown
 * @param rspObject
 * @returns {Boolean}
 */

function ajxRespForReceivingType(rspObject){
	var stockUserTo=null;
	if(!isNull(rspObject)){
		stockUserTo = jsonJqueryParser(rspObject);
		if(stockUserTo!=null && stockUserTo[error_flag]){
			alert(stockUserTo[error_flag]);
			clearReceiverDetails();
			setTimeout(function(){getDomElementById("recipientCode").focus();},10);
			return false;
		}
		populateReceiverDetails(stockUserTo);
	}else{
		alert("Invalid "+getSelectedDropDownText(stockIssuedTOType)+" Code");
		clearReceiverDetails();
		 setTimeout(function(){getDomElementById("recipientCode").focus();},10);
		return false;
	}
}
/**@deprecated 
 * populateReceiverDetails
 * @param stockUserTo
 */
function populateReceiverDetails(stockUserTo){
	 getDomElementById("recipientId").value=stockUserTo.stockUserId;
	 getDomElementById("recipientCode").value=stockUserTo.stockUserCode;
	 getDomElementById("recipientName").value=stockUserTo.stockUserName;
}

/**
 * validateIssuedTO
 * @param domElm
 */
function validateIssuedTO(domElm){
	/** default behaviour is INLINE */
	var flag= "inline";
	if(domElm.value == getIssuedTypeBranch().value){
		alert("You can not select "+getSelectedDropDownTextByDOM(domElm));
		domElm.value="";
		domElm.focus();
		/** Do not let the user to select this type, since it's auto populate */
		return false;
	}else if(domElm.value == getIssuedTypeFr().value){
		 flag= "none";
		 /** Not showing payment details for Franchisee */
	}
	hideShowPaymnt(flag);
	clearReceiverDetails();
	clearNumber(domElm);
	clearGridInfo('issueGrid');
	createEmptyDropDown('recipientId');
	
	//Ajax call for Party Type details
	if(!isNull(domElm.value)){
		var url="./stockIssue.do?submitName=ajaxActionGetPartyTypeDetails&issuedTOType="+domElm.value;
		ajaxJquery(url,'stockIssueForm',ajxRespForPartyTypeDropdown);
	}
	
}
function ajxRespForPartyTypeDropdown(ajaxResp){
	var issueTo = getIssuedToTypeDOM();
	var partyType=getDomElementById('recipientId');
	if(isJsonResponce(ajaxResp)){
		issueTo.value="";
		setTimeout(function(){issueTo.focus();},10);
		return false;
	}
	
	if(!isNull(ajaxResp)){
		createDropDown('recipientId',ajaxResp);
		setTimeout(function(){partyType.focus();},10);
	}else{
		alert(getSelectedDropDownText(stockIssuedTOType)+" Details Does not exist for this Office");
		issueTo.value="";
		setTimeout(function(){issueTo.focus();},10);
		return false;
	}
}
/**
 * isValidToSave
 * @returns {Boolean}
 */
function isValidToSave(){
	var issuedTo= getIssuedToType();
	if(getIssuedTypeBA().value==issuedTo){
		if(!isValidPaymentDtls()){
			return false;
		}
	}
	return true;
}
/**
 * isValidPaymentDtls
 * @returns {Boolean}
 */
function isValidPaymentDtls(){
	var chequeDateDom=getDomElementById("chequeDate");
	var chequeNumDom=getDomElementById("chequeNum");
	var bankNameDom=getDomElementById("bankName");
	var amountDom=getDomElementById("amountReceived");
	var branchDom=getDomElementById("branch");
	var totalToPayamnt=getDomElementById("totalToPayAmount");
	var paymntMode=getDomElementById("paymentMode");
	if(paymntMode==null || isNull(paymntMode.value)){
		alert("Please select payment Type");
		setTimeout(function(){paymntMode.focus();},10);
		return false;
	}
	if(paymntMode.value != getPaymentModeCash()){
		if(isNull(chequeDateDom.value)){
			alert("Please provide Cheque/DD Date");
			setTimeout(function(){chequeDateDom.focus();},10);
			return false;
		}
		if(isNull(chequeNumDom.value)){
			alert("Please provide Cheque/DD Number");
			setTimeout(function(){chequeNumDom.focus();},10);
			return false;
		}
		if(isNull(bankNameDom.value)){
			alert("Please provide Bank Name");
			setTimeout(function(){bankNameDom.focus();},10);
			return false;
		}
		if(isNull(branchDom.value)){
			alert("Please provide Branch");
			setTimeout(function(){branchDom.focus();},10);
			return false;
		}
	}
	
	if(isNull(amountDom.value)){
		alert("Please provide Amount in INR");
		setTimeout(function(){amountDom.focus();},10);
		return false;
	}
	
	
	if(!isNull(totalToPayamnt)&& !isNull(totalToPayamnt.value)){
		var amountRcvd=parseFloat(amountDom.value);
		var amontTopay=parseFloat(totalToPayamnt.value);
		if(amountRcvd<amontTopay){
			alert("Received Amount can not be lesser than To-Pay Amount");
			setTimeout(function(){amountDom.focus();},10);
			return false;
		}
	}
	return true;
}
/**
 * isAtleastRowExist
 * @returns {Boolean}
 */
function isAtleastRowExist(){
	var isNew=false;
	var rowId =1;
	if(isFromDomElement()){
		isNew=true;
	}
	var itemTypeNames=document.getElementsByName("to.rowItemTypeId");
	 if(isNew){
		var cell = itemTypeNames[0];
		 rowId = getRowId(cell,"rowItemTypeId");
	 }
		
		if(!checkMandatoryForAdd(rowId)){
			return false;
		}
	return true;
}
//on change of IssuedTo Dropdown,clears the Stock Requisition/issue number and make it disable
function clearNumber(domElement){
	var reqNum = getDomElementById("requisitionNumber");
	var issueNum = getDomElementById("stockIssueNumber");
	var issueSearch = getDomElementById("issueSearch");
	var reqSearch = getDomElementById("requisitionSearch");
	reqNum.value="";
	issueNum.value="";
	if(isNull(domElement.value)){
		issueNum.readOnly=false;
		issueNum.removeAttribute("tabindex");
		reqNum.readOnly=false;
		reqNum.removeAttribute("tabindex");
		issueSearch.removeAttribute("tabindex");
		reqSearch.removeAttribute("tabindex");
	}else{
		issueNum.setAttribute("readOnly",true);
		issueNum.setAttribute("tabindex","-1");
		reqNum.setAttribute("readOnly",true);
		reqNum.setAttribute("tabindex","-1");
		issueSearch.setAttribute("tabindex","-1");
		reqSearch.setAttribute("tabindex","-1");
	}
}
/**
 * isFromDomElement
 * @returns {Boolean}
 */
function isFromDomElement(){
	var isNew=false;
	if(isNewIssue()){
		isNew=true;
	}
	var issueFromType=getDomElementById("transactionFromType").value;
	if(isNew && !isNull(issueFromType)){
		isNew=false;
	}
	return isNew;
}
/**
 * validateHeader
 * @returns {Boolean}
 */
function validateHeader(){
	var issueTo=getIssuedToTypeDOM();
	var issueFromType=getDomElementById("transactionFromType").value;
	
	if(isNewIssue() && isNull(issueFromType) ){
		clearNumber(issueTo);
	}
	if(isNull(issueTo.value)){
		alert("Please select Issued TO type");
		setTimeout(function(){issueTo.focus();},10);
		return false;
	}else{
		var receipId= getDomElementById("recipientId");
		if(isNull(receipId.value)){
			alert("Please Select "+getSelectedDropDownText(stockIssuedTOType)+" Code");
			receipId.value="";
			setTimeout(function(){receipId.focus();},10);
			return false;
		}
	}
	
	return true;

}
/**
 * disableAfterSave : called on start up( on page load)
 */
function disableAfterSave(){
	var issueFromType=getDomElementById("transactionFromType").value;
	
	var isNew=true;
	if(!isNewIssue()){
		isNew=false;
	}
	
	if(!isNewIssue()|| !isNull(issueFromType)){
		//step 1: to disable Series fields
		//step 2: to disable Qnties fields
		var tableee = getDomElementById('issueGrid');
		var totalRowCount =  tableee.rows.length;
		for(var i=1;i<totalRowCount;i++) {
			hasSeries(i);
			var reqQnty = getDomElementById("rowRequestedQuantity"+i);
			var apprvQnty = getDomElementById("rowApprovedQuantity"+i);
			var issQnty = getDomElementById("rowIssuedQuantity"+i);
			var stSeries = getDomElementById("rowStartSerialNumber"+i);
			reqQnty.setAttribute("readOnly",true);
			reqQnty.setAttribute("tabindex","-1");
			
			if(!isNew){
				stSeries.setAttribute("readOnly",true);
				stSeries.setAttribute("tabindex","-1");
			}
			apprvQnty.setAttribute("readOnly",true);
			apprvQnty.setAttribute("tabindex","-1");
			if(!isNull(issueFromType)){
			issQnty.readOnly=false;
			issQnty.removeAttribute("tabindex");
			}else{
				issQnty.setAttribute("readOnly",true);
				issQnty.setAttribute("tabindex","-1");
			}
		}
		//step 2: to disable issue number/req number/Code/
		var receipId=  getDomElementById("recipientId");
		receipId.setAttribute("readOnly",true);
		receipId.setAttribute("tabindex","-1");
		
		var reqNum = getDomElementById("requisitionNumber");
		var issueNum = getDomElementById("stockIssueNumber");
		
		reqNum.setAttribute("readOnly",true);
		reqNum.setAttribute("tabindex","-1");
		
		issueNum.setAttribute("readOnly",true);
		issueNum.setAttribute("tabindex","-1");
		
		
	}
	//for Paymnt details
	disablePaymntAfterSave();
	disableForUser();
}
/**
 * checkBalanceQntyForPR
 * @param rowId
 * @returns {Boolean}
 */
function checkBalanceQntyForPR(rowId){
	var issueFromType=getDomElementById("transactionFromType").value;
	if(!isNull(issueFromType)){
		var issQntyDom = getDomElementById("rowIssuedQuantity"+rowId);
		var balQntyDom = getDomElementById("rowBalanceIssueQuantity"+rowId);

		var issQnty = parseNumber(issQntyDom.value);
		var balQnty = parseNumber(balQntyDom.value);
		if(issQnty>balQnty){
			alert("Issue Quantity can not be more than Balance Quantity");
			issQntyDom.value="";
			setTimeout(function(){issQntyDom.focus();},10);
			return false;
		}
	}
	return true;
}

/**
 * checkSeriesForBA
 * @param rowId
 * @returns {Boolean}
 */
function checkSeriesForBA(rowId){
	
	var seriesType=getRowSeriesType(rowId);
	
	var issueTo=getIssuedToType();
	var issueBa=getIssuedTypeBA().value;
	if(issueTo==issueBa ){
		if(seriesType != getCnType()){ //check whether cnote is not
			return true;
		}
		var product=getRowSeries(rowId).value;
		if(isNull(product)){
			return false;
		}else{
			product = convertToUpperCase(trimString(product));
		}

		if(!isSeriesAllowedForBa(product)){
			return false;
		}
	}
	return true;
}
/**
 * checkStockForIssue
 * @param rowId
 * @returns {Boolean}
 */
function checkStockForIssue(rowId){
	var stockQntyDom = getDomElementById("rowCurrentStockQuantity"+rowId);
	var stockQnty=parseNumber(stockQntyDom.value);
	var issueQntyDom = getDomElementById("rowIssuedQuantity"+rowId);
	var issQnty=parseNumber(issueQntyDom.value);
	
	var reqQntyDom = getDomElementById("rowRequestedQuantity"+rowId);
	var reqQnty=parseNumber(reqQntyDom.value);
	
	var apprQntyDom = getDomElementById("rowApprovedQuantity"+rowId);
	var apprQnty =parseNumber(apprQntyDom.value);
	if(reqQnty < apprQnty){
		alert("Approved Quantity can not be more than Requested Quantity at line item:"+rowId);
		issueQntyDom.value="";
		apprQntyDom.value="";
		setTimeout(function(){apprQntyDom.focus();},10);
		return false;
	}
	
	
	if(stockQntyDom!=null && isNull(stockQnty)){
		alert("Stock Quantity is zero  for line item :"+rowId);
		issueQntyDom.value="";
		issueQntyDom.focus();
		return false;
	}
	if(stockQnty < issQnty){
		alert("Issue Quantity can not be more than Stock Quantity at line item:"+rowId);
		issueQntyDom.value="";
		setTimeout(function(){issueQntyDom.focus();},10);
		return false;
	}
	if(apprQnty < issQnty){
		alert(" Issue Quantity can not be more than Approved Quantity at line item:"+rowId);
		issueQntyDom.value="";
		setTimeout(function(){issueQntyDom.focus();},10);
		return false;
	}
	var totalIssQnty=getTotalIssuedStockByItem(rowId);
	if(stockQnty < totalIssQnty){
		alert("Total Issue Quantity can not be more than Stock Quantity at line item:"+rowId);
		issueQntyDom.value="";
		setTimeout(function(){issueQntyDom.focus();},10);
		return false;
	}
	return true;
}
/**
 * checkStockQnatity
 * @param domElem
 * @returns {Boolean}
 */
function checkStockQnatity(domElem){
	var flag=true;
	var rowId = getRowId(domElem,'rowIssuedQuantity');
	rowId = parseInt(rowId);
	if(!checkStockForIssue(rowId)){
		flag=false;
	}
	calculatePriceForAllMaterials();
	return flag;
}
/**
 * getTotalIssuedStockByItem
 * @param rowId
 * @returns {Number}
 */
function getTotalIssuedStockByItem(rowId){
var tableee = getDomElementById('issueGrid');
	var itemId=getDomElementById('rowItemId'+rowId).value;
	var issuedQuntity=0;
	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.rowIssuedQuantity');
	for(var i=1;i<totalRowCount;i++) {
			var currRowId = getRowId(cell[i-1],"rowIssuedQuantity");
			var curItemId=getDomElementById('rowItemId'+currRowId).value;
			if( curItemId == itemId){
				issuedQuntity=issuedQuntity+ parseNumber(getDomElementById('rowIssuedQuantity'+currRowId).value);
			}
	}
	return issuedQuntity;
}
/**
 * isValidToAddRow
 * @param domElemnt
 */

function isValidToAddRow(domElemnt){

	var curRowId = getRowId(domElemnt,'rowStartSerialNumber');
	var tableee = getDomElementById('issueGrid');
	var totalRowCount = tableee.rows.length-1;

	if(parseInt(curRowId)== totalRowCount && isNewIssue()){
		if(checkMandatoryForAdd(curRowId)){
			addNewRow();
		}
	}
}
/**
 * isValidForAddNewRow
 */
function isValidForAddNewRow(){
	
	var tableee = getDomElementById('issueGrid');
	var totalRowCount = tableee.rows.length-1;
	if(checkMandatoryForAdd(totalRowCount)&& isNewIssue()){
		addNewRow();
	}
}
/**
 * For clearing the Serial number and issue quanity of the row
 * it will be called on change of Item Type/Item
 */
function clearSeriesForIssue(rowId){
	getDomElementById("rowIssuedQuantity"+rowId).value='';
	clearSeries(rowId);
}
function hidePaymntDtls(){
	var issueTo=getIssuedToType();
	var issueBr=getIssuedTypeBranch().value;
	if(issueBr == issueTo){
		var flag= "none";
		hideShowPaymnt(flag);
	}
	
}
function hideShowPaymnt(flag){
	var paymntTtl=getDomElementById("paymntTitle");
	var paymntGrid=getDomElementById("paymntDtls");
	paymntGrid.style.display = flag;
	paymntTtl.style.display =flag;
}
function disablePaymntAfterSave(){
	var chequeDateDom=getDomElementById("chequeDate");
	var chequeNumDom=getDomElementById("chequeNum");
	var bankNameDom=getDomElementById("bankName");
	var amountDom=getDomElementById("amountReceived");
	var branchDom=getDomElementById("branch");
	var calender=getDomElementById("calImg");
	var issueTo=getIssuedToType();
	var issueBr=getIssuedTypeBranch().value;
	if(issueBr == issueTo || getIssuedTypeFr().value==issueTo|| !isNewIssue()){
		if(calender!=null){
			calender.style.display = "none";
		}
		chequeNumDom.setAttribute("readOnly",true);
		chequeNumDom.setAttribute("tabindex","-1");

		bankNameDom.setAttribute("readOnly",true);
		bankNameDom.setAttribute("tabindex","-1");

		amountDom.setAttribute("readOnly",true);
		amountDom.setAttribute("tabindex","-1");

		branchDom.setAttribute("readOnly",true);
		branchDom.setAttribute("tabindex","-1");
	}
	return true;

}
function validateAmount(){
	var amountDom= getDomElementById('amountReceived');

	if(!isNull(amountDom)){
		var amount =parseNumber(amountDom.value);
		if(amount==0){
			alert("Amount can not be Empty/Zero");
			amountDom.value="";
			setTimeout(function(){amountDom.focus();},10);
			return;
		}else{
			amountDom.value=roundDecimal(amountDom.value,2);
		}
	}
}
function setShippedToCodeForBaFr(customerDOM){
	var issuedType=getDomElementById('issuedToType');
	clearShippedToCode();
	if(!isNull(customerDOM.value) && !isNull(issuedType) && issuedType.value != getIssuedTypeBranch().value){
		url="./stockIssue.do?submitName=ajaxGetBaFrDetailsById&partyTypeId="+customerDOM.value+"&issuedTOType="+issuedType.value;
		ajaxJquery(url,'stockIssueForm',ajaxResponseForShippedToCode);
	}
}
function ajaxResponseForShippedToCode(rspObject){
	var customerShippedCode=getDomElementById("shippedToCode");
	var stockUserTo=null;
	if(!isNull(rspObject) && !isErrorJsonResponce(rspObject)){
		stockUserTo = jsonJqueryParser(rspObject);
		if(customerShippedCode!=null && stockUserTo!=null){
			customerShippedCode.value=stockUserTo.shippedToCode;
		}
		}else {
			customerShippedCode.value="";
		}
}

function calculateRateForSave(){
	var flag=true;
	var totalTopayAmount=getDomElementById('totalToPayAmount');
	var taxPerUnit=parseFloat(getDomElementById('totalTaxPerQuantityPerRupe').value);

	var tableee = document.getElementById('issueGrid');
	var totalRowCount = tableee.rows.length;
	var cell = document.getElementsByName('to.checkbox');
	var amountCounter=0;
	for(var i=0;i<totalRowCount-1;i++) {
		if(cell[i].checked){
			var itemDom = getDomElementByName("to.rowItemId");
			var issuedQntityDom = getDomElementByName("to.rowIssuedQuantity");
			var pricePerQntyDom = getDomElementByName("to.rowRatePerUnitQuantity");	
			var rowTotalprceDom = getDomElementByName("to.rowTotalPrice");	
			if(isNull(pricePerQntyDom[i].value)){
				alert("Price Not Defined for the material at Line :"+(i+1));
				flag=false;
				break;
			}
			if(!isNull(itemDom[i].value) && !isNull(issuedQntityDom[i].value)){
				if(!isNull(pricePerQntyDom[i].value)&& isNull(rowTotalprceDom[i].value)){
					var qunty=parseNumber(issuedQntityDom[i].value);
					rowTotalprceDom[i].value=roundDecimal(qunty * parseFloat(pricePerQntyDom[i]),2);
					amountCounter=parseFloat(amountCounter)+parseFloat(rowTotalprceDom[i].value);
				}

			}
		}
	}//check whether check box is selected
	if(flag){
		var amountToPay=parseFloat(taxPerUnit * amountCounter);
		totalTopayAmount.value=roundingOff(roundDecimal(amountToPay, 2));

	}

	return flag;
}
function isForpanIndia(){
	var isForPanIndia=getDomElementById('isForPanIndia');
	if(isForPanIndia!=null){
		if(isForPanIndia.value=="Y"){
			return true;
		}
	}
	return false;
}

function calculatePriceForAllMaterials(){
	var totalTopayAmount=getDomElementById('totalToPayAmount');
	var taxPerUnit=parseFloat(getDomElementById('totalTaxPerQuantityPerRupe').value);

	var itemDom = getDomElementByName("to.rowItemId");
	var amountCounter=0;
	for(var i=0;i<itemDom.length;i++) {
			
			var issuedQntityDom = getDomElementByName("to.rowIssuedQuantity");
			var pricePerQntyDom = getDomElementByName("to.rowRatePerUnitQuantity");	
			var rowTotalprceDom = getDomElementByName("to.rowTotalPrice");	
			if(isNull(pricePerQntyDom[i].value)){
				rowTotalprceDom.value="";
				continue;
			}
			if(!isNull(itemDom[i].value) && !isNull(issuedQntityDom[i].value)){
				if(!isNull(pricePerQntyDom[i].value)){
					var qunty=parseNumber(issuedQntityDom[i].value);
					rowTotalprceDom[i].value=roundDecimal(qunty * parseFloat(pricePerQntyDom[i].value),2);
					amountCounter=parseFloat(amountCounter)+parseFloat(rowTotalprceDom[i].value);
				}

			}
		}
	var amountToPay=parseFloat(taxPerUnit * amountCounter);
	//alert("Before Round OFF:"+amountToPay);
	totalTopayAmount.value=roundDecimal(roundingOff(roundDecimal(amountToPay,2)), 2);
	//alert("Total ToPay Amount:"+totalTopayAmount.value);
}
function getPaymentModeCash(){
	return getDomElementById('paymentCash').value;
}
function validatePayment(){
	var chequeDateDom=getDomElementById("chequeDate");
	var chequeNumDom=getDomElementById("chequeNum");
	var bankNameDom=getDomElementById("bankName");
	var amountDom=getDomElementById("amountReceived");
	var branchDom=getDomElementById("branch");
	var paymntMode=getDomElementById("paymentMode");
	var calImg=getDomElementById('calImg');
	
	if(paymntMode!=null){
		chequeNumDom.readonly=false;
		chequeDateDom.readonly=false;
		bankNameDom.readonly=false;
		branchDom.readonly=false;
		
		if(paymntMode.value==getPaymentModeCash()){
			
			paymntMode.setAttribute("onkeypress","enterKeyNav('amountReceived',event.keyCode)");
			chequeNumDom.removeAttribute("onkeypress");
			bankNameDom.removeAttribute("onkeypress");
			branchDom.removeAttribute("onkeypress");
			
			chequeNumDom.value="";
			chequeDateDom.value="";
			bankNameDom.value="";
			branchDom.value="";
			
			chequeNumDom.setAttribute("readOnly",true);
			chequeNumDom.setAttribute("tabindex","-1");
			
			chequeDateDom.setAttribute("readOnly",true);
			chequeDateDom.setAttribute("tabindex","-1");
			
			bankNameDom.setAttribute("readOnly",true);
			bankNameDom.setAttribute("tabindex","-1");
			
			branchDom.setAttribute("readOnly",true);
			branchDom.setAttribute("tabindex","-1");
			
		}else{
			paymntMode.setAttribute("onkeypress","enterKeyNav('calImg',event.keyCode)");
			chequeDateDom.setAttribute("onkeypress","enterKeyNav('chequeNum',event.keyCode)");
			chequeNumDom.setAttribute("onkeypress","enterKeyNav('bankName',event.keyCode)");
			bankNameDom.setAttribute("onkeypress","enterKeyNav('branch',event.keyCode)");
			branchDom.setAttribute("onkeypress","enterKeyNav('amountReceived',event.keyCode)");
			
			chequeDateDom.value="";
			chequeNumDom.value="";
			bankNameDom.value="";
			amountDom.value="";
			branchDom.value="";
			
		}
	}
}
//end of Changes by <31913> on 16/12/2012