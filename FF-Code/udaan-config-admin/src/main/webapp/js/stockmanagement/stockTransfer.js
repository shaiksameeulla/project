/** Start of Changes by <31913> on 10/01/2013
 * 
 */

//******************************Business Rules*********************************
//8.4.4	Before transfer, consignment issued to Customer / Field Representative / BA 
//8.4.5	Consignments issued to BA can be transferred only to other BA�s within the region.
//8.4.6	Consignments issued to customers can be transferred other customers  and to field representatives.
//8.4.7	Consignments issued to pickup boy can be transferred to pickup boys within the branch or customer�s reporting to the same branch.
//8.4.8	Stock transferred to a branch can be issued from Stock issue.

//********************************************Business Rules**************
/**
 * Allows you to tag each variable supported by a function.
 */
var transferFormId='stockTransferForm';
var domElemForTrasferFromType=null;
var domElemForTrasferTOType=null;
/**
 * clearScreen
 */
function clearScreen(){
	var url="./stockTransfer.do?submitName=viewFormDetails";
	submitTransfer(url,'Clear'); 
}
/**
 * submitTransfer
 * @param url
 * @param action
 */
function submitTransfer(url,action){
	if(confirm("Do you want to "+action+" the details?")) {
		document.stockTransferForm.action = url;
		document.stockTransferForm.method = "post";
		document.stockTransferForm.submit();
	}
}
/**
 * validateTransferFrom
 * @param domElem
 */
function validateTransferFrom(domElem){
	domElemForTrasferFromType=domElem;
	clearTransferTODtls();
	clearTransferFromDtls();
	if(!isNull(domElem) && !isNull(domElem.value) ){
		var url='./stockTransfer.do?submitName=ajaxActionGetPartyTypeDetails&issuedTOType='+domElem.value;
		ajaxJquery(url,transferFormId,ajxRespForTransferFromPartyDtls);
	}else{
		clearAllDetails();
		setFocusOnStDom(domElem);
	}
}
/**
 * ajxRespForTransferFromPartyDtls
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajxRespForTransferFromPartyDtls(ajaxResp){
	
	if(isJsonResponce(ajaxResp)){
		domElemForTrasferFromType.value="";
		setFocusOnStDom(domElemForTrasferFromType);
		return false;
	}
	
	if(!isNull(ajaxResp)){
		createDropDown('transferFromPersonId',ajaxResp);
	}else{
		alert(getSelectedDropDownText('transferFromType')+" Details Does not exist");
		domElemForTrasferFromType.value="";
		setFocusOnStDom(domElemForTrasferFromType);
		return false;
	}
}
/**
 * validateTransferTO : validates transferTo dropdown as per the BR
 * @param domElem
 * @returns {Boolean}
 */
function validateTransferTO(domElem){
	domElemForTrasferTOType=domElem;
	clearTransferTOPersonDtls();
	if(!isValidTransferTO(domElem)){
		//alert(getSelectedDropDownText('transferFromType')+"is not valid selection");
		domElemForTrasferTOType.value="";
		setFocusOnStDom(domElemForTrasferTOType);
		return false;
	}
	
	
	if(!isNull(domElem) && !isNull(domElem.value) ){
		var url='./stockTransfer.do?submitName=ajaxActionGetPartyTypeDetails&issuedTOType='+domElem.value;
		if(domElem.value != getIssuedTypeBranch().value){
			ajaxJquery(url,transferFormId,ajxRespForTransferTOPartyDtls);
		}
	}else{
		clearTransferTODtls();
	}
}

/**
 * ajxRespForTransferTOPartyDtls
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajxRespForTransferTOPartyDtls(ajaxResp){
	if(isJsonResponce(ajaxResp)){
		domElemForTrasferTOType.value="";
		setFocusOnStDom(domElemForTrasferTOType);
		return false;
	}
	
	if(!isNull(ajaxResp)){
		createDropDown('transferTOPersonId',ajaxResp);
	}else{
		alert(getSelectedDropDownText('transferTOType')+"Details Does not exist");
		domElemForTrasferTOType.value="";
		setFocusOnStDom(domElemForTrasferTOType);
		return false;
	}
}
//check BR for Transfer From Type and  Transfer TO type 
//(Use BR which are available at the starting of the page)
function isValidTransferTO(domElem){
	disableTransferToPersonId(false);
	//alert(domElem.value);
	if(!isNull(domElem.value)){
		if(domElem.value==getIssuedTypeBranch().value){
			//alert(domElem.value);
			prepareDropDownForBranch();
			setFocusOnStDomId('itemId');
			return true;
		}else if(!isValidCombinationForTransferTO()){
			return false;
		}
	}else{
		clearTransferTODtls();
	}
	return true;
}



/**
 * prepareDropDownForBranch
 */
function prepareDropDownForBranch(){
	var officeId=getDomElementById('loggedInOfficeId').value;
	var officeCode=getDomElementById('loggedInOfficeCode').value;
	var officeName=getDomElementById('loggedInOfficeName').value;
	var office= officeCode +officeName;
	var resList ="{"+officeId+"="+office+"}";
	createDropDown('transferTOPersonId',resList);
	getDomElementById('transferTOPersonId').value=officeId;
	disableTransferToPersonId(true);
	
}
/**
 * disableTransferToPersonId
 * @param isDisabled
 */
function disableTransferToPersonId(isDisabled){
	var trsDom = getDomElementById('transferTOPersonId');
	if(isDisabled){
		trsDom.disabled=true;
		trsDom.setAttribute("tabindex","-1");
	}else{
		trsDom.disabled=false;
		trsDom.removeAttribute("tabindex");
	}
}
/**
 * isValidCombinationForTransferTO
 * @returns {Boolean}
 */
function isValidCombinationForTransferTO(){
	var transferFromDom = getDomElementById('transferFromType');
	var transferFrom = transferFromDom.value;
	
	if(isNull(transferFrom)){
		alert("Please select valid Transfer From");
		transferFromDom.value="";
		clearTransferFromDtls();
		setFocusOnStDom(transferFromDom);
		return false;
	}
	var transferTODom = getDomElementById('transferTOType');
	var transferTO = transferTODom.value;
	
	var issuedToTypeEmp=getIssuedTypeEmp().value;
	var issuedToTypeCustomer=getIssuedTypeCustomer().value;
	var issuedToTypeBA=getIssuedTypeBA().value;
	var issuedToTypeBr=getIssuedTypeBranch().value;
	if(transferFrom ==issuedToTypeBA ){
		if(transferTO != issuedToTypeBA){
			clearTransferTODtls();
			alert("Only transferred to other Business Associates is allowed");
			setFocusOnStDom(transferTODom);
			return false;
		}
		
	}else  if(transferFrom==issuedToTypeCustomer || transferFrom==issuedToTypeEmp){
		if(transferTO !=issuedToTypeCustomer && transferTO !=issuedToTypeEmp){
			alert("Transfer is allowed only Customers/Employees");
			clearTransferTODtls();
			setFocusOnStDom(transferTODom);
			return false;
		}
	}
	
	return true;
}

/**
 * clearTransferTODtls
 */
function clearTransferTODtls(){
	getDomElementById('transferTOPersonId').value="";
	getDomElementById('transferTOType').value="";
	clearTransferSeries();
	createEmptyDropDown('transferTOPersonId');
	disableTransferToPersonId(false);
}
/**
 * clearTransferTOPersonDtls
 */
function clearTransferTOPersonDtls(){
	getDomElementById('transferTOPersonId').value="";
	clearTransferSeries();
	createEmptyDropDown('transferTOPersonId');
	disableTransferToPersonId(false);
}
/**
 * clearTransferFromDtls
 */
function clearTransferFromDtls(){
	getDomElementById('transferFromPersonId').value="";
	createEmptyDropDown('transferFromPersonId');
	clearTransferTODtls();
}
/**
 * clearTransferSeries
 */
function clearTransferSeries(){
	getDomElementById('startSerialNum').value="";
	getDomElementById('endSerialNum').value="";
	getDomElementById('officeProduct').value="";
	getDomElementById('startLeaf').value="";
	getDomElementById('endLeaf').value="";
}

/**
 * clearAllDetails
 */
function clearAllDetails(){
	clearTransferFromDtls();
	clearTransferTODtls();
}


/**
 * find
 * @param action
 * @returns {Boolean}
 */
function find(action){
	var url = null;
	var transferNum = getDomElementById("stockTransferNumber");
	if(isNull(transferNum.value)){
		alert("Please enter Stock Transfer Number");
		setFocusOnStDom(transferNum);
		return false;
	}
	url="./stockTransfer.do?submitName=findDetailsByTransferNumber";
	submitTransferWithoutPrompt(url);
}
function submitTransferWithoutPrompt(url) {
	
	document.stockTransferForm.action = url;
	document.stockTransferForm.method = "post";
	document.stockTransferForm.submit();
}

function save(){
	var transferNum = getDomElementById("stockTransferNumber");
	if(isStockTransferNew()){
		transferNum.value="";
	}
	if(validateMandatory()){
	var	url="./stockTransfer.do?submitName=saveTransferDtls";
		if(promptConfirmation('Save')) {
			dropdownEnable();
			submitTransferWithoutPrompt(url);
			
		}
	}
	
}

function isStockTransferNew(){
	var stockId= getDomElementById("stockTransferId").value;
	if(isNull(stockId)){
		return true;
	}
	return false;
}

function validateMandatory(){

	if(validateHeader()){
		
		
		var stSerialNumDom =getDomElementById('startSerialNum');
		var stSerialNum =stSerialNumDom.value;

		var endSerialNumDom =getDomElementById('endSerialNum');
		var endSerialNum =endSerialNumDom.value;
		
		
		if(isNull(stSerialNum)){
			alert("Please provide Start Serial number");
			stSerialNumDom.value="";
			endSerialNumDom.value="";
			setFocusOnStDom(stSerialNumDom);
			return false;
		}
		if(isNull(endSerialNum)){
			alert("Please provide Start/End Serial number ");
			stSerialNumDom.value="";
			endSerialNumDom.value="";
			setFocusOnStDom(stSerialNumDom);
			return false;
		}
	}else{
		return false;
	}
	return true;

}


/**validateHeader
 * @returns {Boolean}
 */
function validateHeader(){
	var transferFromDom = getDomElementById('transferFromType');
	var transferFrom = transferFromDom.value;

	var transferTODom = getDomElementById('transferTOType');
	var transferTO = transferTODom.value;

	var transferTOPartyDom =getDomElementById('transferTOPersonId');
	var transferTOPartyId =transferTOPartyDom.value;

	var transferFromPartyDom =getDomElementById('transferFromPersonId');
	var transferFromPartyId =transferFromPartyDom.value;
	
	var quantityDom =getDomElementById('transferQuantity');
	quantityDom.value=parseNumber(quantityDom.value);
	var quantity =quantityDom.value;
	

	var materialCodeDom =getDomElementById('itemId');
	var materialCode =materialCodeDom.value;
	
	var stSerialNumDom =getDomElementById('startSerialNum');

	var endSerialNumDom =getDomElementById('endSerialNum');
	
	if(isNull(transferFrom)){
		alert("Please select Transfer-From details");
		transferFromDom.value="";
		setFocusOnStDom(transferFromDom);
		return false;
	}
	if(isNull(transferFromPartyId)){
		alert("Please select Transfer-From "+getSelectedDropDownText('transferFromType')+" details");
		transferFromPartyDom.value="";
		setFocusOnStDom(transferFromPartyDom);
		return false;
	}


	if(isNull(transferTO)){
		alert("Please select Transfer-To details");
		transferTODom.value="";
		setFocusOnStDom(transferTODom);
		return false;
	}
	if(isNull(transferTOPartyId)){
		alert("Please select Transfer-To "+getSelectedDropDownText('transferTOType')+" details");
		transferTOPartyDom.value="";
		setFocusOnStDom(transferTOPartyDom);
		return false;
	}
	if(isNull(materialCode)){
		alert("Please select Material details");
		stSerialNumDom.value="";
		endSerialNumDom.value="";
		materialCodeDom.value="";
		setFocusOnStDom(materialCodeDom);
		return false;
	}
	if(isNull(quantity)){
		alert("Please provide Stock Transfer Quantity details");
		quantityDom.value="";
		setFocusOnStDom(quantityDom);
		return false;
	}
	return true;
}
/**
 * seriesValidatorWrapperForTransfer
 */
function seriesValidatorWrapperForTransfer(){
	var stSerialNumDom =getDomElementById('startSerialNum');
	if(!isNull(stSerialNumDom.value)){
	validateSeries(stSerialNumDom);
	}else{
		clearTransferSeries();
	}
}
/**
 * validateSeries
 * @param domElem
 * @returns {Boolean}
 */
function validateSeries(domElem){
	var seriesDom= domElem;
	var stSeries =seriesDom.value;
	stSeries = convertToUpperCase(stSeries);
	seriesDom.value=stSeries;
	if(validateHeader()){
		
		var itemIdDom = getDomElementById('itemId');
		var itemIdDomValue = itemIdDom.value;
		
		if(isNull(itemIdDomValue)){
			alert("Please provide Start Serial number details");
			setFocusOnStDom(itemIdDom);
			return false;
		}
		if(isNull(stSeries)){
			alert("Please provide Start Serial number details");
			clearTransferSeries();
			setFocusOnStDom(seriesDom);
		}else{
			if(!checkSerieslength(stSeries)){
				clearTransferSeries();
				return false;
			}
			
			if(!validateSeriesForBA()){
				clearTransferSeries();
				setFocusOnStDom(seriesDom);
				return false;
			}
			var branchCode =null;
			var product=getProductForSt();
			var quantityDom =getDomElementById('transferQuantity');
			quantityDom.value=parseNumber(quantityDom.value);
			var qty = quantityDom.value;
			branchCode = getRhoCode();
			branchCode=branchCode+product;
			
			var tokenArray = stSeries.split(branchCode);
			var se1 = tokenArray[1]+qty;
			var se2 = tokenArray[0];
			//alert("Make an ajax call");
			
			//check if cnote without product it should have RHO code
			
			if(!isNull(product)){
				branchCode = getRhoCode();
				branchCode=branchCode+product;
				if(checkStartsWith(stSeries,branchCode)){
					var tokenArray = stSeries.split(branchCode);
					if(!isSeriesStartsWithProduct(tokenArray[1])){
						alert("Cnote(s) should not contain more than a product ");
						clearTransferSeries();
						return false;
					}
				}

			}else{
				branchCode =getBranchCode().value;
				if(checkStartsWith(stSeries,branchCode)){
					var tokenArray = stSeries.split(branchCode);
					if(!isSeriesStartsWithProduct(tokenArray[1])){
						alert("Normal cnote(s) should not contain product ");
						clearTransferSeries();
						return false;
					}
				}
			}
			//if(!stSeries.startsWith(branchCode)){
			if(!checkStartsWith(stSeries,branchCode)){
				alert("series must start with :"+branchCode);
				clearTransferSeries();
				setFocusOnStDom(domElem);
				return false;
			}
			
			var cnMatrixFlag=false;
			var transferTODom = getDomElementById('transferTOType');
			var transferTO = transferTODom.value;
			if(transferTO==getIssuedTypeBA().value|| transferTO ==getIssuedTypeCustomer().value ||transferTO== getIssuedTypeEmp()){
				getDomElementById('issuedToType').value=transferTO;
				//validating series against Customer type/Franchisee/Employee/BA
				cnMatrixFlag=isValidCnoteForIssueToParty(null, stSeries);
			}else{
				cnMatrixFlag=true;
			}
			
			if(cnMatrixFlag){
				//make an ajax call;
				ajaxSerialNumberValidations();
			}else{
				clearTransferSeries();
				var itemId=getDomElementById('itemId');
				itemId.value="";
				setFocusOnStDom(itemId);
			}

		}
		
	}else{
		stSeries.value="";
		return false;
	}
	
}
/**
 * isCnoteDoNotHaveProduct
 * @param series
 * @returns {Boolean}
 */
function isCnoteDoNotHaveProduct(series){
	var charCode=series.charCodeAt(4);
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
		return false;
	}
	return true;
}
/**
 * validateSeriesForBA
 * @returns {Boolean}
 */
function validateSeriesForBA(){
	if(!isBa()){
		//if given Transfer type is not BA type do not proceed further
		return true;
	}
	
	var stSeries = getDomElementById('startSerialNum').value;
	stSeries = convertToUpperCase(stSeries);
	if(!isNull(stSeries)){
		if(stSeries.length <12){
			alert("Series length should be 12 digits");
			clearTransferSeries();
			return false;
			
		}
		if(!verifyProductForBA(stSeries)){
			clearTransferSeries();
			return false;
		}
		
	}else{
		return false;
	}
	return true;
}
/**
 * isBa :validates whether Business Associate or not
 * @returns {Boolean}
 */
function isBa(){
	var transferTO = getDomElementById('transferTOType').value;
	if(transferTO == getIssuedTypeBA().value){
		return true;
	}
	return false;
}
/**verifyProductForBA
 * @param stSeries
 * @returns {Boolean}
 */
function verifyProductForBA(stSeries){
	var fifthCharCode=stSeries.charCodeAt(4);
	var fifthChar=stSeries.charAt(4);
		//alert("fifthCharCode"+fifthCharCode);
		//alert("fifthChar"+fifthChar);
	if (!(fifthCharCode >= 65 && fifthCharCode <= 90)){
		alert("Given Series is not allowed for Business Associate");
		return false;
	}else{
		if(!isSeriesAllowedForBa(fifthChar)){
			alert("Given Series is not allowed for Business Associate");
			return false;
		}
	}
	return true;
	
}
/**isValidTransferPartyId
 * @returns {Boolean}
 */
function isValidTransferPartyId(domElemnt){
	var transferFromDom = getDomElementById('transferFromType');
	var transferFrom = transferFromDom.value;
	
	var transferTODom = getDomElementById('transferTOType');
	var transferTO = transferTODom.value;
	
	var transferTOPersonDom= getDomElementById('transferTOPersonId');
	var transferTOPerson=transferTOPersonDom.value;
	
	var transferFromPersonDom= getDomElementById('transferFromPersonId');
	var transferFromPerson =transferFromPersonDom.value; 
	if(!isNull(transferFrom)&& transferFrom == transferTO){
		if(!isNull(transferFromPerson)&& transferFromPerson ==transferTOPerson){
			alert("Both Transfer From and Transfer To "+getSelectedDropDownText('transferTOType') +" Name can not be same");
			transferTOPersonDom.value="";
			setFocusOnStDom(transferTOPersonDom);
			return false;
		}
		
	}
	
}
/**
 * transferStartup
 */
function transferStartup(){
	if(!isStockTransferNew()){
		disableAll();
		//getDomElementById("Cancel").disabled=false;
		enableGlobalButton("Cancel");
		disableForUser();
	}
}
/**
 * ajaxSerialNumberValidations
 */
function ajaxSerialNumberValidations(){
	var transferFrom = getValueByElementId('transferFromType');
	var transferTO = getValueByElementId('transferTOType');
	var transferTOPartyId =getValueByElementId('transferTOPersonId');
	var transferFromPartyId =getValueByElementId('transferFromPersonId');
	var quantity =getValueByElementId('transferQuantity');
	var itemId =getValueByElementId('itemId');
	var stSerialNumber =getValueByElementId('startSerialNum');
	var endSerialNumber = getEndSerialNumber(stSerialNumber,quantity);
	
	var	url="./stockTransfer.do?submitName=ajaxActionSeriesValidation&transferFrom="+transferFrom+"&transferTO="+transferTO+"&transferFromPersonId="+transferFromPartyId+"&transferTOPersonId="+transferTOPartyId+"&quantity="+quantity+"&startSerialNum="+stSerialNumber+"&itemId="+itemId+"&endSerialNumber="+endSerialNumber;
	ajaxJquery(url,transferFormId,ajxRespForSerialNumberDtlsForTransfer);
}
/**
 * ajxRespForSerialNumberDtlsForTransfer
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajxRespForSerialNumberDtlsForTransfer(ajaxResp){
	//alert(ajaxResp);
	var stSerialNumberDom =getDomElementById('startSerialNum');
	var stSerialNumber =stSerialNumberDom.value;
	var endSerialNumber=getDomElementById('endSerialNum');
	var itemId=getDomElementById('itemId');
	var officeProduct=getDomElementById('officeProduct');
	var stLeaf=getDomElementById('startLeaf');
	var endLeaf=getDomElementById('endLeaf');
	if (!isNull(ajaxResp)) {
		var responseText=jsonJqueryParser(ajaxResp);
		var error = responseText[error_flag];
		var result = responseText[success_flag];

		if(isNull(error) && isNull(result)){
			alert("Error in response");
			clearTransferSeries();
			setFocusOnStDom(stSerialNumberDom);
			return false;
		}
		if(!isNull(error)){
			alert(error);
			clearTransferSeries();
			setFocusOnStDom(stSerialNumberDom);
			return false;
		}
		if(!isNull(result)){
			//alert("success"+result);
			//response Format : startserialnumber,endserialnumber,officeproductcode,startleaf,endleaf,itemId
			result=result.split(",");
			stSerialNumber.value=result[0];//start serialnumber
			endSerialNumber.value=result[1];//end serialnumber
			officeProduct.value=result[2];
			stLeaf.value=result[3];
			endLeaf.value=result[4];
			itemId.value=result[5];

		}
	}else{
		alert("Invalid Series ,please provide correct series");
		//FIXME
		clearTransferSeries();
		setFocusOnStDom(stSerialNumberDom);
		return false;
	}

}
/**getItemDtls
 * 
 * @param domValue
 */

function getItemDtls(domValue){
	clearTransferSeries();
	clearMaterialDtlsForTransfer();
	if(!isNull(domValue.value)){
		var url="./stockTransfer.do?submitName=ajaxGetItemDetialsByItemId&typeId="+domValue.value;
		ajaxJquery(url,transferFormId,ajaxRespTransferItem);
	}
}
/**
 * ajaxRespIssueItem
 * @param ajaxResp
 * @returns {Boolean}
 */
function ajaxRespTransferItem(ajaxResp){
	//alert('AJAX Successful Response:');
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[error_flag];
		if(responseText!=null && !isNull(error)){
			alert(error);
			getDomElementById("itemId").value="";
			clearTransferSeries();
			return false;
		}else if(!isNull(responseText)){
			populateMaterialDtlsForTransfer(responseText);
			return true;
		}else{
			getDomElementById("itemId").value="";
			alert("Invalid Item/Material Details");
			clearTransferSeries();
			return false;
		}

	}else{
		alert("Invalid Item/Material Details");
		getDomElementById("itemId").value="";
		clearTransferSeries();
		return false;
	}
}
function populateMaterialDtlsForTransfer(itemTo){
	getDomElementById("itemSeries").value=itemTo.itemSeries;
	getDomElementById("rowSeriesLength").value=itemTo.seriesLength;
	getDomElementById("seriesType").value=itemTo.seriesType;
}
function clearMaterialDtlsForTransfer(){
	getDomElementById("itemSeries").value="";
	getDomElementById("rowSeriesLength").value="";
	getDomElementById("seriesType").value="";
	
}

function getProductForSt(){
	return getDomElementById("itemSeries").value;
}

function checkSerieslength(stSeries){
	var stNum=trimString(stSeries);
	var expLenght=getDomElementById("rowSeriesLength").value;
	expLenght=parseInt(expLenght,10);
	var actLenght=stNum.length;
	actLenght=parseInt(actLenght,10);
	if(expLenght !=actLenght){
		alert("Invalid series length \n length should be:"+expLenght);
		return false;
	}
	return true;
}


function getSelectedCustomerTypeForSt(customerDOM){
	var customerId=customerDOM.value;
	clearCustomerTypeTr();
	clearTransferSeries();
	clearShippedToCode();
	var transferTO = getValueByElementId('transferTOType');
	if(!isNull(customerId)&& transferTO==getIssuedTypeCustomer().value){
		url="./stockTransfer.do?submitName=ajaxGetCustomerDetailsById&partyTypeId="+customerId+"&issuedTOType="+transferTO;
		ajaxJquery(url,transferFormId,ajaxResponseForCustomerTypeSt);
	}
}
function ajaxResponseForCustomerTypeSt(rspObject){
	var customerType=getDomElementById("customerType");
	var shippedToCode=getDomElementById('shippedToCode');
	var stockUserTo=null;
	if(!isNull(rspObject)){
		stockUserTo = jsonJqueryParser(rspObject);
		if(stockUserTo!=null && stockUserTo[error_flag]!=null){
			alert(stockUserTo[error_flag]);
			return false;
		}
		if(!isNull(stockUserTo) && !isNull(stockUserTo.customerTypeTO)){
		customerType.value=stockUserTo.customerTypeTO.customerTypeCode;
		}
		if(shippedToCode!=null){
			shippedToCode.value=stockUserTo.shippedToCode;
		}
	}
}
function clearCustomerTypeTr(){
	getDomElementById("customerType").value="";
}
function getEndSerialNumber(stSlNo,quantity){
	var zeros=0;
	var nonZeros=0;
	var zeroDeletions=0;
	var count=getCountFromString(stSlNo);
	var startNum=stSlNo.substring(count,stSlNo.length);
	//get substring from count till quantity.length
	var startchar=stSlNo.substring(0,count);

	//alert("startchar"+startchar);

	if(startNum!=null && trimString(startNum)!=''){
//		get substring from count till quantity.length
		var startchar=stSlNo.substring(0,count);
		var EndNum=parseInt(quantity,10)+parseInt(startNum,10);
		if(EndNum>0) EndNum=EndNum-1;	
//		perform trailing zeros --start
		var startNumSize=startNum.length;
		var afterParsing=parseInt(startNum,10)+"";
		var afterParsingSize=afterParsing.length;
		var endNumva=EndNum+"";
		var EndNumsize=endNumva.length;
		if(startNumSize>afterParsingSize){
			//alert("sss");
			var startArray=new Array();
			for(var i=0;i<startNumSize;i++){
				startArray[i] =startNum.charAt(i);
				if(startArray[i]=="0"){
					++zeros;
				}else
					++nonZeros;
			}

			//alert("Total Zeros"+zeros);
			//alert("NonZeros"+nonZeros);
			if(EndNumsize>nonZeros){
				zeroDeletions=EndNumsize-nonZeros;
			}
			if(zeroDeletions>0){
				zeros=zeros-zeroDeletions;
			}
			for(var i=0;i<zeros;i++)
				EndNum="0"+EndNum;
		}
//		perform trailing zeros --end

		var EndSlNum=startchar+EndNum;
		//alert("start Num"+stSlNo+"\t"+"end serial Num"+EndSlNum);
		return EndSlNum;


	}else{
		alert("Serial number is having incorrect format");
		return false;
	}
}
/** End of Changes by <31913> on 18/09/2013
 * 
 */
