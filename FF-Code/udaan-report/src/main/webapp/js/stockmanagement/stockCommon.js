//variables
// Start of Changes by <31913> on 16/12/2012
/**
 * Allows you to tag each parameter supported by a function.
 */
var rowItemTypeList=null;
var rowItemList=null; 
var error_flag="ERROR";
var success_flag="SUCCESS";
var stockIssuedTOType="issuedToType";

//Ajax call  to get list of materials  Type/itmeType   
function getItemTypeList(formId){
//	alert("getItemTypeList");
	var url='./createRequisition.do?submitName=ajaxGetItemTypeMap';
	ajaxJquery(url,formId,ajxRespForItemType);
}
//Ajax response  to populate materials Type/itmeType 
function ajxRespForItemType(ajaxResp,rowId){
	//alert("ajxRespForItemType"+rowItemTypeList);
	if (!isNull(ajaxResp)) {
		rowItemTypeList = ajaxResp;
		var textElem = "rowItemTypeId";
		populateReqDropdown(rowItemTypeList,1,textElem);
	}else{
		//alert("Material Type(s) doesnot exist");
	}
}
//Ajax call to populate list of materials
function getItemList(domObj){
	var gridId = getRowId(domObj,"rowItemTypeId");
	clearItemDetailsByRowId(gridId);
	//alert("rowId : "+gridId +"value :"+domObj.value);
	if(!isNull(domObj.value)){
		var url="./createRequisition.do?submitName=ajaxGetItemByTypeMap&typeId="+domObj.value;
		ajaxJqueryWithRow(url,'createRequisition',ajxRespForItemByType,gridId);
	}else{
		clearItemDetailsByRowId(gridId);
	}

}
//Ajax call response to populate list of materials
function ajxRespForItemByType(ajaxResp,gridId){
	if (!isNull(ajaxResp)) {
		rowItemList = ajaxResp;
		var rid= trimString(gridId);
		populateReqDropdown(rowItemList,rid,"rowItemId");
	}else{
		alert("Material Details doesnot exist \n Please select another Material type");
		clearItemDetailsByRowId(gridId);
		var itemTypeDom=getDomElementById('rowItemTypeId'+gridId);
		itemTypeDom.value="";
		setTimeout(function(){itemTypeDom.focus();},10);
	}
}
//helper function to create dropdown
function populateReqDropdown(rowList,rowId,textElem){
	var itemTypeElem=textElem+rowId;
	if(!isNull(rowList)){
	createDropDown(itemTypeElem,rowList);
	}else{
		//alert("Material type details doesnot exist");
	}
}

//dont remove below line..it's for getting stock Rate for issue
var BA_TYPE_ISSUE=false;
//Ajax call to populate list of materials
function getItemDetails(domObj){
	var isIssueType=false;
	BA_TYPE_ISSUE=false;
	var gridId = getRowId(domObj,"rowItemId");
	//dont remove below line..it's for getting stock Qnty for issue
	var issueType= getDomElementById("stockIssueId"); 
	if(issueType!=null){
		isIssueType=true;
		//dont remove below line..it's for getting stock Rate for issue
		var paymntId= getDomElementById('stockPaymentId');
		if(isNull(getIssuedToTypeDOM().value)&& paymntId!=null){
			alert("Please select issued type details");
			domObj.value="";
			setTimeout(function(){getIssuedToTypeDOM().focus();},10);
			return false;
		}
		if(getIssuedToTypeDOM().value == getIssuedTypeBA().value){
			BA_TYPE_ISSUE=true;
		}
		
	}
	//alert("rowId : "+gridId +"value :"+domObj.value);
	var itemType=getDomElementById("rowItemTypeId"+gridId);
	var itemTypeId= itemType.value;
	clearItemDetailsByRowIdExcludeItemId(gridId);
	if(isNull(domObj.value)){
		return;
	}
	if (!isNull(itemTypeId)) {
	var url="./createRequisition.do?submitName=ajaxGetItemDetialsByItemIdAndTypeId&itemId="+domObj.value+"&itemTypeId="+itemTypeId;
	if(isIssueType){
		url= url+"&Issue=true";
		if(BA_TYPE_ISSUE){
			url= url+"&issuedTOType="+getIssuedToTypeDOM().value;
		}
	}
	ajaxJqueryWithRow(url,'reqGrid',ajxRespForItemDtls,gridId);
	}else{
		alert("Please select Material Type at line :"+gridId);
		domObj.value="";
		setTimeout(function(){itemType.focus();},10);
		return false;
	}
	
}
//Ajax call response to populate list of materials
function ajxRespForItemDtls(ajaxResp,gridId){
	//alert("ajaxResp"+ajaxResp);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[error_flag];
		if(responseText!=null && error!=null){
			alert(error);
			var domElm="rowItemId"+gridId;
			//createEmptyDropDown(domElm);
			setTimeout(function(){getDomElementById("rowItemId"+gridId) .focus();},10);
			clearItemDetailsByRowId(gridId);
			clearRateDetails(gridId);
			return false;
		}
		populateItemDetailsByRowId(responseText,gridId);
		if(BA_TYPE_ISSUE){
			populateRateDetails(responseText, gridId);
		}
	}else{
		alert("Material details does not exist for selected Material Type/Material");
		var domElm="rowItemId"+gridId;
		createEmptyDropDown(domElm);
		clearItemDetailsByRowId(gridId);
		clearRateDetails(gridId);
		var itemType=getDomElementById("rowItemTypeId"+gridId);
		if(!isNull(itemType.value)){
			getItemList(itemType);
		}
		
	}
	BA_TYPE_ISSUE=false;
}
/**
 * populateRateDetails
 * @param itemTo
 * @param gridId
 */
function populateRateDetails(itemTo,gridId){
	if(!isNull(itemTo) && !isNull(itemTo.rateTO)){
		var rate=getDomElementById('rowRatePerUnitQuantity'+gridId);
		if(!isNull(rate)){
			rate.value= roundDecimal(itemTo.rateTO.ratePerUnit,2);
		}
		getDomElementById('totalTaxPerQuantityPerRupe').value=itemTo.rateTO.totalTaxPerQuantityPerRupe;
	}
}
function clearRateDetails(rowId){
	if(BA_TYPE_ISSUE){
		getDomElementById('rowRatePerUnitQuantity'+rowId).value='';
		clearTotalRowPrice(rowId);
	}
}
function clearTotalRowPrice(gridId){
	getDomElementById('rowTotalPrice'+gridId).value='';
}
/**
 * populateItemDetailsByRowId : common ajax responc for ItemTO
 * @param resultTo
 * @param rowId
 */
function populateItemDetailsByRowId(resultTo,rowId){
	//alert("populateItemDetailsByRowId"+resultTo);
	getDomElementById('rowDescription'+rowId).value=resultTo.description;
	getDomElementById('rowUom'+rowId).value=resultTo.uom;
	var rowSeries=getDomElementById('rowSeries'+rowId);
	if(rowSeries!=null){
	rowSeries.value=resultTo.itemSeries;
	getDomElementById('rowSeriesLength'+rowId).value=resultTo.seriesLength;
	getDomElementById('rowIsItemHasSeries'+rowId).value=resultTo.itemTypeTO.itemHasSeries;
	
	if(getDomElementById("rowStartSerialNumber"+rowId)!=null){
		enableSeries(rowId);
		hasSeries(rowId);
	}
	var qnty = getDomElementById("rowCurrentStockQuantity"+rowId);
	if(qnty!=null){
		qnty.value=resultTo.stockQuantity;
	}
	var seriesType = getDomElementById("rowSeriesType"+rowId);
	if(seriesType!=null){
		seriesType.value=resultTo.seriesType;
	}
	}
}
/**
 * clearItemDetailsByRowId : clear grid information
 * @param rowId
 */
function clearItemDetailsByRowId(rowId){
	//alert("populateItemDetailsByRowId"+resultTo);
	getDomElementById('rowItemId'+rowId).value="";
	clearItemDetailsByRowIdExcludeItemId(rowId);
}
/**
 * clearItemDetailsByRowIdExcludeItemId
 *  @param rowId
 */
function clearItemDetailsByRowIdExcludeItemId(rowId){
	getDomElementById('rowDescription'+rowId).value="";
	getDomElementById('rowUom'+rowId).value="";
	var rowSeries=getDomElementById('rowSeries'+rowId);
	if(rowSeries!=null){
		rowSeries.value="";
		getDomElementById('rowSeriesLength'+rowId).value="";
		getDomElementById('rowIsItemHasSeries'+rowId).value="";
		if(getDomElementById("rowStartSerialNumber"+rowId)!=null){
			enableSeries(rowId);		
		}
	}
	var qnty = getDomElementById("rowCurrentStockQuantity"+rowId);
	if(qnty!=null){
		qnty.value="";
	}
	var seriesType = getDomElementById("rowSerisType"+rowId);
	if(seriesType!=null){
		seriesType.value="";
		getDomElementById("rowOfficeProduct"+rowId).value="";
		getDomElementById("rowStartLeaf"+rowId).value="";
		getDomElementById("rowEndLeaf"+rowId).value="";
	}
}
/**
 * submitRequisition 
 * @param url
 * @param action
 */

function submitRequisition(url,action){
	if(promptConfirmation(action)){
		dropdownEnable();
	document.createRequisitionForm.action=url;
	document.createRequisitionForm.method="post";
	document.createRequisitionForm.submit();
	}
}
/**
 * submitReqWithoutPromp
 * @param url
 */
function submitReqWithoutPromp(url){
	document.createRequisitionForm.action=url;
	document.createRequisitionForm.method="post";
	document.createRequisitionForm.submit();
}
/**
 * isValidCheckBox
 * @param chxboxval
 * @returns {Boolean}
 */
function isValidCheckBox(chxboxval){
	var gridId = getRowId(chxboxval,"checkbox");
	var apprQnty=getDomElementById("rowApprovedQuantity"+gridId);
	var isAppr=getDomElementById("isApproved"+gridId);
	var isApprd=false;
	if(isAppr!=null && !isNull(isAppr)&& !isNull(isAppr.value)){
		isApprd=true;
	}
	if(chxboxval.checked){
		if(isApprd && !isNull(apprQnty.value)){
			chxboxval.checked=false;
			alert("Selected material(s) is already approved at line:"+gridId);
			return false;
		}
	}
	return true;
}


/**
 * getRowSeries
 * @param rowId
 * @returns DOM element(HTML Object)
 */
function  getRowSeries(rowId){
	return getDomElementById("rowSeries"+rowId);
}
/**getItemSeriesWithoutId
 * @returns series
 */
function  getItemSeriesWithoutId(){
	var itemSeries=getItemSeriesDOMWithoutId();
	if(!isNull(itemSeries)){
		itemSeries=itemSeries.value;
	}
	return itemSeries;
}
function getItemSeriesDOMWithoutId(){
	return getDomElementById("itemSeries");
}
/**
 * getSeriesLength
 * @param rowId
 * @returns length of the series
 */
function  getSeriesLength(rowId){
	return getDomElementById("rowSeriesLength"+rowId);
}
/**
 * getSeriesLengthWithoutId
 * @returns
 */
function  getSeriesLengthWithoutId(){
	return getDomElementById("seriesLength").value;
}
/**
 * getItemHasSeries :rowIsItemHasSeries
 * @param rowId
 * @returns
 */
function  getItemHasSeries(rowId){
	return getDomElementById("rowIsItemHasSeries"+rowId);
}
/**
 * getNoSeries
 * @returns HTML Object
 */
function  getNoSeries(){
	return getDomElementById("noSeries");
}
//validates whether series has valid length
function isSeriesLengthValid(series,rowId){
	if(!isNull(series)){
		if(series.length != getSeriesLength(rowId).value){
			alert(" Invalid Series length \n length should be :"+getSeriesLength(rowId).value);
			return false;
		}
	}else{
		alert("Start Serial number is Empty");
		return false;
	}
	return true;
}
/**
 * isSeriesLengthValidWithoutRowId
 * @param series
 * @returns {Boolean}
 */
function isSeriesLengthValidWithoutRowId(series){
	if(!isNull(series)){
		var length=parseInt(getSeriesLengthWithoutId());
		if(series.length != length ){
			alert(" Invalid Series length \n length should be :"+length);
			return false;
		}
	}else{
		alert("Start Serial number is Empty");
		return false;
	}
	return true;
}
/**
 * isEndSeriesLengthValid
 * @param series
 * @param rowId
 * @returns {Boolean}
 */
function isEndSeriesLengthValid(series,rowId){
	if(!isNull(series)){
		if(series.length != getSeriesLength(rowId).value){
			alert(" Invalid End Series length \n length should be :"+getSeriesLength(rowId).value);
			return false;
		}
	}else{
		alert("Invalid Start Serial number ");
		return false;
	}
	return true;
}
/**
 * isEndSeriesLengthValidWithoutId
 * @param series
 * @returns {Boolean}
 */
function isEndSeriesLengthValidWithoutId(series){
	if(!isNull(series)){
		var length=parseNumber(getSeriesLengthWithoutId());
		if(series.length != length ){
			alert(" Invalid End Series length \n length should be :"+length);
			return false;
		}
	}else{
		alert("Invalid Start Serial number");
		return false;
	}
	return true;
}
/**
 * clearSeries
 * @param rowId
 */
function clearSeries(rowId){
	getDomElementById("rowStartSerialNumber"+rowId).value="";
	getDomElementById("rowEndSerialNumber"+rowId).value="";
	getDomElementById("rowOfficeProduct"+rowId).value="";
	getDomElementById("rowStartLeaf"+rowId).value="";
	getDomElementById("rowEndLeaf"+rowId).value="";
}
function clearItemTypeItemDtls(rowId){
	var itemType=getDomElementById("rowItemTypeId"+rowId);
	var item=getDomElementById("rowItemId"+rowId);
	var desc = getDomElementById("rowDescription"+rowId);
	var uom = getDomElementById("rowUom"+rowId);
	var reqQntyDom = getDomElementById("rowRequestedQuantity"+rowId);
	var apprvQntyDom = getDomElementById("rowApprovedQuantity"+rowId);
	var issQntyDom= getDomElementById("rowIssuedQuantity"+rowId);
	var ratePerUnit= getDomElementById("rowRatePerUnitQuantity"+rowId);
	var price= getDomElementById("rowTotalPrice"+rowId);
	var remarks= getDomElementById("rowRemarks"+rowId);
	var seriestype= getDomElementById("rowSeriesType"+rowId);
	if(itemType!=null){
		itemType.value="";
	}
	if(item!=null){
		item.value="";
	}
	if(desc!=null){
		desc.value="";
	}
	if(uom!=null){
		uom.value="";
	}
	if(reqQntyDom!=null){
		reqQntyDom.value="";
	}
	if(apprvQntyDom!=null){
		apprvQntyDom.value="";
	}
	if(issQntyDom!=null){
		issQntyDom.value="";
	}
	if(ratePerUnit!=null){
		ratePerUnit.value="";
	}
	if(price!=null){
		price.value="";
	}
	if(remarks!=null){
		remarks.value="";
	}
	if(seriestype!=null){
		seriestype.value='';
	}
}
/**
 * enableSeries :Start/end html elements in the grid
 * @param rowId
 */
function enableSeries(rowId){
	var stSerDom=getDomElementById("rowStartSerialNumber"+rowId);
	stSerDom.readOnly=false;
	stSerDom.removeAttribute("tabindex");
}
/**
 * disableSeries :Disable Start/end html elements in the grid
 * @param rowId
 */
function disableSeries(rowId){
	clearSeries(rowId);
	var stSerDom=getDomElementById("rowStartSerialNumber"+rowId);
	var endSerDom=getDomElementById("rowEndSerialNumber"+rowId);
	stSerDom.setAttribute("readOnly",true);
	stSerDom.setAttribute("tabindex","-1");
	//stSerDom.readOnly=true;
	stSerDom.tabindex="-1";
	endSerDom.setAttribute("readOnly",true);
	endSerDom.setAttribute("tabindex","-1");
}
/**
 * getBranchCode
 */
function getBranchCode(){
	return getDomElementById("loggedInOfficeCode");
}
/**
 * getRegionCode
 * @returns
 */
function getRegionCode(){
	return getDomElementById("regionCode").value;
}
/**
 * getBranchProduct
 * @param rowId
 * @param isBranch
 * @returns
 */
function getBranchProduct(rowId,isBranch){
	var branch=null;
	if(isBranch){
		branch=getBranchCode().value;
	}else{
		branch=getRegionCode();
	}
	var product=getRowSeries(rowId).value;
	if(isNull(product)){
		product="";
	}else{
		product = convertToUpperCase(trimString(product));
	}
	var branchProduct=branch+product;
	branchProduct = convertToUpperCase(branchProduct);
	return branchProduct = trimString(branchProduct);
}
/**
 * getCityAndProduct
 * @param rowId
 * @returns
 */
function getCityAndProduct(rowId){
	var cityCode=null;
	cityCode=getCityCode();
	var product=getRowSeries(rowId).value;
	if(isNull(product)){
		product="";
	}else{
		product = convertToUpperCase(trimString(product));
	}
	var cityProduct=cityCode+product;
	cityProduct = convertToUpperCase(cityProduct);
	return cityProduct = trimString(cityProduct);
}
/**
 * getRunningNumFromSeries
 * @param series
 * @param rowId
 * @returns
 */
function getRunningNumFromSeries(series,rowId){
	var branchProduct=getBranchProduct(rowId);
	var tokenArray = series.split(branchProduct);
	return tokenArray[1];  

}
//validates whether series has valid BranchCode/Product
function isSeriesHasBranchProduct(series,rowId,type){
	var issuedType = getIssuedToTypeDOM();
	var seriesType=getRowSeriesType(rowId);
	var branchProduct=null;
	
	if(seriesType == getBagLocNumber()){
		//alert(" series not defined");
		branchProduct = getBranchProduct(rowId,false);
		if(checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Baglock should not contain product ");
				return false;
			}
		}
		
	}else if(seriesType == getComailNumber()){
		var product=getRowSeries(rowId).value;
		branchProduct=product;
		if(isNull(branchProduct)){
			alert("Please define Comail Starts with series");
			return false;
		}
		if(checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(product);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Series(CO-Mail) should not contain product ");
				return false;
			}
		}
	}else if(seriesType == getOgmStickers())  {//ogm Stickers
		branchProduct = getCityAndProduct(rowId);
		if(checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Series(OGM) should not contain  product ");
				return false;
			}
		}
		
	}else if(seriesType == getBplStickers()){//BPL Stickers
		
		var product=getRowSeries(rowId).value;
		branchProduct = getCityAndProduct(rowId);
		if(!isNull(product)&& checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Series(BPL) should not contain more than one product ");
				return false;
			}
		}
		
	
	}else if(seriesType == getMbplStickers()){//BPL Stickers
		
		var product=getRowSeries(rowId).value;
		branchProduct = getCityAndProduct(rowId);
		if(!isNull(product)&& checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Series(MBPL) should not contain more than one product ");
				return false;
			}
		}
		
	
	}else if(seriesType == getCnType()){
		//for Normal Cnote --it must starts with logged in office Code
		//for Product Cnote --it must starts with logged in RHO Code
		var product=getRowSeries(rowId).value;
		if(isNull(product)){
			if(issuedType!=null && type=='issue'&& issuedType.value == getIssuedTypeBranch().value){
				branchProduct=getReceipientProduct(rowId);
			}else{
				branchProduct=getBranchProduct(rowId,true);
			}
			if(checkStartsWith(series,branchProduct)){
				var tokenArray = series.split(branchProduct);
				if(!isSeriesStartsWithProduct(tokenArray[1])){
					alert("Normal cnote(s) should not contain product ");
					return false;
				}
			}
		}else{
				branchProduct = getRhoCode()+product;
		}
		if(!isNull(product)&& checkStartsWith(series,branchProduct)){
			var tokenArray = series.split(branchProduct);
			if(!isSeriesStartsWithProduct(tokenArray[1])){
				alert("Cnote should not contain more than one product ");
				return false;
			}
		}
	}else{
		alert("Invalid series");
		return false;
	}
	if(!hasSeries(rowId)){
		alert("Selected material doesnot have series");
		disableSeries(rowId);
		return false;
	}else if(!checkStartsWith(series,branchProduct)){
		alert("series must start with :"+branchProduct);
		clearSeries(rowId);
		getDomElementById("rowStartSerialNumber"+rowId).focus();
		return false;
	}
	
	if(!isLeafValid(series,branchProduct)){
		return false;
	}
	
	return true;
}

function isLeafValid(series,branchProduct){
	if(!isNull(series) && !isNull(branchProduct)){
		var seriesParts=series.split(branchProduct);
		if(!isNull(seriesParts) && seriesParts.length>1){
			var leaf=parseNumber(seriesParts[1]);
			if(leaf<=0){
				alert("Leaf should not starts with zero");
				return false;
			}
		}
	}
	return true;
}
/**
 * hasSeries
 * @param rowId
 * @returns {Boolean}
 */
function hasSeries(rowId){
	var noSeries=getNoSeries().value;
	var hasSeries=getItemHasSeries(rowId).value;
	if(trimString(noSeries)== hasSeries){
		disableSeries(rowId);
		return false;
	}
	return true;
}
/**
 * getRhoCode
 * @returns
 */
function getRhoCode(){
	return	getDomElementById("rhoOfficeCode").value;
}
/**
 * getCityCode
 * @returns
 */
function getCityCode(){
	return	getDomElementById("cityCode").value;
}
/**
 * isCnoteWithoutProduct
 * @param series
 * @param branchProduct
 * @returns {Boolean}
 */
function isCnoteWithoutProduct(series,branchProduct){
	if(!isNull(branchProduct)){
		var len=branchProduct.length;
		if(len==4){
			var charCode=series.charCodeAt(4);
			if (charCode > 31 && (charCode < 48 || charCode > 57)){
				alert("Consignment should not contain product series");
				return false;
			}
			
		}
	}else{
		return false;
	}
	return true;
}
/**
 * checkForDuplicateSerialNumber
 * @param startSlNum
 * @param endSlNum
 * @param currentRowId
 * @param tableId
 * @returns {Boolean}
 */
function checkForDuplicateSerialNumber(startSlNum,endSlNum,currentRowId,tableId){
	
	var currentItemCode =getDomElementById("rowItemTypeId"+currentRowId).value;
	var leng = getTableLength(tableId);
	var count=getCountFromString(startSlNum);
	
	//for Branch+product
	var currStProduct=startSlNum.substring(0,count);//alert("currStNum" +currStNum);
	currStProduct = trimString(currStProduct);
	
	var currStNum=startSlNum.substring(count,startSlNum.length);//alert("currStNum" +currStNum);
	var currEndNum=endSlNum.substring(count,endSlNum.length);//alert("currEndNum" +currEndNum);
	
	var _currStNum=parseInt(currStNum,10);//getting start SI Number,number part and parsing that number
	var _currEndNum=parseInt(currEndNum,10);//getting end End Number,number part and parsing that number
	
	
	for(var i=1;i<leng;i++){
		var existingRowCode =document.getElementById("rowItemTypeId"+i).value;
		var stSI=  document.getElementById("rowStartSerialNumber"+i);
		if(existingRowCode == currentItemCode && !stSI.readOnly && currentRowId!=i && !isNull(stSI.value)){
			var prevStSi = stSI.value;
			var prevEndSi =  document.getElementById("rowEndSerialNumber"+i).value;
			var countP=getCountFromString(prevStSi);;
			var prvStNum=prevStSi.substring(countP,prevStSi.length);//alert("prvStNum" +prvStNum);
			var prvEndNum=prevEndSi.substring(countP,prevEndSi.length);//alert("prvEndNum" +prvEndNum);
			var _prvStNum=parseInt(prvStNum,10);//getting start SI Number,number part and parsing that number
			var _prvEndNum=parseInt(prvEndNum,10);//getting end End Number,number part and parsing that number
			
			//for Branch+product
			var prevStProduct=prevStSi.substring(0,countP);//alert("currStNum" +currStNum);
			prevStProduct=trimString(prevStProduct);
			if(currStProduct != prevStProduct){
				continue;
			}
			
			if(((_currStNum >=_prvStNum)&& (_currStNum<= _prvEndNum)) ||( (_currEndNum >=_prvStNum)&& (_currEndNum<= _prvEndNum))){
				alert("Start/End serial number already exist at the line :"+i);
				return false;
			}else if(((_currStNum <=_prvStNum)&& (_currEndNum >= _prvEndNum)) || (((_currStNum <= _prvStNum) && (_prvStNum<= _currEndNum)) && ((_currStNum <= _prvEndNum)&& (_prvEndNum <= _currEndNum )))){
				alert("Start/End serial number already exist at the line :"+i);
				return false;
			}
			
		}
		
	}
	return true;
	
}
/**
 * getTableLastRowIdByElement
 * @param tableId
 * @param elementName
 * @param elementId
 * @returns {Number}
 */
function getTableLastRowIdByElement(tableId,elementName,elementId){
	var rowCount=1;
	var tableee = document.getElementById(tableId);
	var cell = document.getElementsByName(elementName);
	var cntofRow = tableee.rows.length; 
	if(cntofRow > 2) {
		var dom=cell[cntofRow-2];
		rowCount = getRowId(dom,elementId);
	}
	return rowCount;
}

function getTableFirstRowIdByElement(elementName,elementId){
	var rowCount=1;
	var cell = document.getElementsByName(elementName);
	if(!isNull(cell) && cell.length >0) {
		var dom=cell[0];
		rowCount = getRowId(dom,elementId);
	}
	return rowCount;
}
/**
 * findOnEnterKey
 * @param action
 * @param keyCode
 */
function findOnEnterKey(action,keyCode){
	
	if(enterKeyNavWithoutFocus(keyCode)){
	find(action);
	}
}
/**
 * isSeriesAllowedForBa
 * @param series
 * @returns {Boolean}
 */
function isSeriesAllowedForBa(product){
	var arraySplit=convertStringToArray(getBaAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}

function isSeriesAllowedForEmp(product){
	var arraySplit=convertStringToArray(getEmpAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForFr(product){
	var arraySplit=convertStringToArray(getFrAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}

function isSeriesAllowedForCreditCust(product){
	var arraySplit=convertStringToArray(getCreditCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}

function isSeriesAllowedForAccCust(product){
	var arraySplit=convertStringToArray(getAccCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForCcCust(product){
	var arraySplit=convertStringToArray(getCcCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForLcCust(product){
	var arraySplit=convertStringToArray(getLcCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForCodCust(product){
	var arraySplit=convertStringToArray(getCodCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForGvCust(product){
	var arraySplit=convertStringToArray(getGvCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
function isSeriesAllowedForRlCust(product){
	var arraySplit=convertStringToArray(getRlCustomerAllowedSeries());
	var isExistIn = isExistInArray(product, arraySplit);
	if(isExistIn<0){
		return false;
	}
	return true;
}
/**
 * getBaAllowedSeries
 * @returns
 */
function getBaAllowedSeries(){
	return getDomElementById("baAllowedSeries").value;
}

function getFrAllowedSeries(){
	return getDomElementById("franchiseeAllowedSeries").value;
}

function getCreditCustomerAllowedSeries(){
	return getDomElementById("creditCustAllowedSeries").value;
}
function getAccCustomerAllowedSeries(){
	return getDomElementById("accCustAllowedSeries").value;
}


function getCcCustomerAllowedSeries(){
	return getDomElementById("creditCardCustomerAllowedSeries").value;
}
function getGvCustomerAllowedSeries(){
	return getDomElementById("govtEntityCustomerAllowedSeries").value;
}
function getLcCustomerAllowedSeries(){
	return getDomElementById("lcCustomerAllowedSeries").value;
}
function getCodCustomerAllowedSeries(){
	return getDomElementById("codCustomerAllowedSeries").value;
}
function getRlCustomerAllowedSeries(){
	return getDomElementById("reverseLogCustomerAllowedSeries").value;
}
function getEmpAllowedSeries(){
	return getDomElementById("empAllowedSeries").value;
}
/**
 * activeStatus
 * @returns :status
 */
function activeStatus(){
	return getDomElementById("status").value;
}
/**
 * populateSeriesResponse : To populate response after Serial number validations
 * @param response
 * @param rowId
 */
function populateSeriesResponse(response,rowId){
	var result=response.split(",");
	var stSeries=getDomElementById("rowStartSerialNumber"+rowId);
	var endSeries=getDomElementById("rowEndSerialNumber"+rowId);
	var officeProduct=getDomElementById("rowOfficeProduct"+rowId);
	var startLeaf=getDomElementById("rowStartLeaf"+rowId);
	var endLeaf=getDomElementById("rowEndLeaf"+rowId);
	stSeries.value=result[0];
	endSeries.value=result[1];
	officeProduct.value=result[2];
	startLeaf.value=result[3];
	endLeaf.value=result[4];
}
/**
 * clearGridInfo
 * @param tableId
 * @returns {Boolean}
 */

function clearGridInfo(tableId){
	var tableee = document.getElementById(tableId);
	var totalRowCount = tableee.rows.length;
	var noOfrows=0;
	var cell = document.getElementsByName('to.checkbox');
	var typeId = document.getElementsByName('to.rowItemTypeId');
	noOfrows = (!isNull(cell) && cell.length>0)?cell.length:((!isNull(typeId) && typeId.length>0)?typeId.length:0)
	for(var i=0;i<noOfrows;i++) {
		if(cell!=null && cell.length>0){
		rowId = getRowId(cell[i],"checkbox");
		}else if(typeId!=null && typeId.length>0){
			rowId = getRowId(typeId[i],"checkbox");
		}
		clearSeries(rowId);
		clearItemTypeItemDtls(rowId);
	}
	return true;
}
//*****************for Series Type ********start
/**
 * getBagLocNumber
 */
 function getBagLocNumber(){
		return getDomElementById("bagLocNumber").value;
	}
 //for Co-mail constant 
 function getComailNumber(){
		return getDomElementById("comailNumber").value;
	}
 // for Bpl sticker constant
 function getBplStickers(){
	 var sticker1=getDomElementById("bplStickers").value;
	// alert(sticker1);
	 if(!isNull(sticker1)){
		 sticker1 = trimString(sticker1);
	 }
	 return sticker1;
 }
 //for Mbpl sticker constants
 function getMbplStickers(){
	 var sticker1=getDomElementById("mbplStickers").value;
	// alert(sticker1);
	 if(!isNull(sticker1)){
		 sticker1 = trimString(sticker1);
	 }
	 return sticker1;
 }
 //for Ogm sticker constants
 function getOgmStickers(){
	 var sticker=getDomElementById("ogmStickers").value;
	// alert(sticker);
	 if(!isNull(sticker)){
		 sticker = trimString(sticker);
	 }
	 return sticker;
 }
 //for conignment type constants
 function getCnType(){
		return getDomElementById("consignmentType").value;
	}
 

//*****************for series type***********end
 //for Stock Issue
 function getReceipientProduct(rowId){
		var branch=getDomElementById("recipientCode").value;
		var product=getRowSeries(rowId).value;
		if(isNull(product)){
			product="";
		}else{
			product = convertToUpperCase(trimString(product));
		}
		var branchProduct=branch+product;
		branchProduct = convertToUpperCase(branchProduct);
		return branchProduct = trimString(branchProduct);
	}
 /**
  * parseNumber
  * @param num
  * @returns parse integer
  */
 function parseNumber(num){
	var inpNum =(isNull(num)?0:num);
	return parseInt(inpNum,10);
 }
 /**
  * getRowItemId
  * @param rowId
  * @returns HTML Object
  */
 function getRowItemId(rowId){
	return getDomElementById("rowItemId"+rowId);
 }
 /**
  * getRowSeriesType
  * @param rowId
  * @returns :row series type
  */
 function getRowSeriesType(rowId){
	return getDomElementById("rowSeriesType"+rowId).value;
 }
 /**
  * focusOnItemType
  * @param rowId
  */
 function focusOnItemType(rowId){
	var itemType =getDomElementById("rowItemTypeId"+rowId);
	setTimeout(function(){itemType.focus();},10);
}
 //for transactio type constant
 function getTransactionType(){
	 return getDomElementById("transactionFromType").value;
 }
 //for stock itemdetails id from the grid
 function getStockItemDtlsId(rowId){
	 return getDomElementById("rowStockItemDtlsId"+rowId).value; 
 }
//ajaxResponseForSeries :it will be called globlly across stock module
 /**
  * ajaxResponseForSeries :it will be called globlly across stock module
  */
 function ajaxResponseForSeries(ajaxResp,rowId){
	 clearSeries(rowId);
		var stSeries=getDomElementById("rowStartSerialNumber"+rowId);
		var endSeries=getDomElementById("rowEndSerialNumber"+rowId);
		
		//alert("ajaxResp"+ajaxResp);
		if (!isNull(ajaxResp)) {
			rowItemList = ajaxResp;
			var responseText=jsonJqueryParser(ajaxResp);
			var error = responseText[error_flag];
			var result = responseText[success_flag];
			
			if(isNull(error) && isNull(result)){
				alert("Error in response");
				clearSeries(rowId);
				setTimeout(function(){stSeries.focus();},10);
				return false;
			}
			if(!isNull(error)){
				alert(error);
				clearSeries(rowId);
				setTimeout(function(){stSeries.focus();},10);
				return false;
			}
			if(!isNull(result)){
				//alert("success"+result);
				populateSeriesResponse(result,rowId);
				//Its for issue screen To add new Row 
				var issueScreen=getIssuedToType();
				var issueTobr=getIssuedTypeBranch();
				if(issueScreen!=null && issueTobr !=null && issueTobr.value != issueScreen.value){
					if(isNewIssue()){//check if new Transaction/modifying existing details
						isValidToAddRow(stSeries);
					}//inner if
				}//screen if
			}//result 
		}else{
			alert("Invalid Series ,please provide correct series");
			clearSeries(rowId);
			setTimeout(function(){endSeries.focus();},10);
			return false;
		}

	}
 /**
  * disableForUser
  */
 function disableForUser(){
	 var canUpdateDom=getDomElementById("canUpdate");

	 if(canUpdateDom!=null && !isNull(canUpdateDom.value) && (canUpdateDom.value =='N'|| trimString(canUpdateDom.value)=='N')){
		 disableAll();
		 getDomElementById("Cancel").disabled=false;
	 }
}
 /**
  * seriesValidationWrapper : it's wrapper to serial number validations
  * @param rowId
  */
 function seriesValidationWrapper(rowId){
		var seriesDom= getDomElementById("rowStartSerialNumber"+rowId);
		if(seriesDom!=null && !isNull(seriesDom.value))
		validateSeries(seriesDom);
	}
 /**
  * isSeriesStartsWithProduct 
  * @param series
  * @returns {Boolean}
  */
 function isSeriesStartsWithProduct(series){
	 var charCode=series.charCodeAt(0);
		if (charCode > 31 && (charCode < 48 || charCode > 57)){
			
			return false;
		}
		return true;
 }
 /**
  * globalFormSubmit
  * @param url
  * @param formId
  */
 function globalFormSubmit(url,formId){

	 document.forms[formId].action=url;
	 document.forms[formId].submit();
 }
 /**
  * checkStartsWith :: it checks whether sourceStr starts with text or not
  * @param text
  * @returns {Boolean}
  */
 function checkStartsWith(sourceStr,text){
	 var result=false;
	 try{
	 var expression = new RegExp('^' + text);
	 result=expression.test(sourceStr);
	 }catch(e){
		 alert(e);
		 result=(sourceStr.indexOf(text) === 0);
	 }
	 return result;
 }
 function getApprovedFlagYes(){
	return getDomElementById('approveFlagYes').value;
 }
 function focusOnNextRow(keyCode,tableId,elementName,elementId){
	 if(enterKeyNavWithoutFocus(keyCode)){
	var rowId= getTableLastRowIdByElement(tableId, elementName, elementId);
	var targetDom= getDomElementById(elementId+rowId);
	setTimeout(function(){targetDom.focus();},10);
	 }
 }
 /**
  * focusOnFirstRow
  * @param keyCode
  * @param tableId
  * @param elementName
  * @param elementId
  */
 
 function focusOnFirstRow(keyCode,elementName,elementId){
	 if(enterKeyNavWithoutFocus(keyCode)){
		 var rowId= getTableFirstRowIdByElement(elementName, elementId);
		 rowId=parseNumber(rowId);
		 var targetDom= getDomElementById(elementId+rowId);
		 setTimeout(function(){targetDom.focus();},10);
	 }
 }
 
 function isFutureDateByServerDate(selectedDate,todayDate) {

		var date = selectedDate.trim();
		if (date == "")
			return false;

		date = date.split("/");
		var myDate = new Date();
		myDate.setFullYear(date[2], date[1] - 1, date[0]);
		///
		
		var currdate = todayDate.trim();
		if (currdate == "")
			return false;

		currdate = currdate.split("/");
		var newDate = new Date();
		newDate.setFullYear(currdate[2], currdate[1] - 1, currdate[0]);
		
		if (myDate > newDate) {
			return true;
		}
		return false;
	}
 /**
  * isFutureDateForIssue
  * @param selectedDom
  * @param domId
  */
 function isFutureDateForIssue(selectedDom,domId){
	 var dateDom= selectedDom;
	 var nextDom=getDomElementById(domId);
	 var serverDate= getDomElementById('todayDate');
	 if(isNull(serverDate) && isNull(serverDate.value)){
		 alert("Server date is empty, please set the server date");
		 return false;
	 }
	 if(!isNull(dateDom) && isFutureDateByServerDate(dateDom.value,serverDate.value)){
		 dateDom.value="";
		 dateDom.focus();
		 alert("Date can not be future date");
	 }else{
		 if(!isNull(nextDom)){
			 //nextDom.value="";
			 nextDom.focus();
		 }
	 }
 }
 /**roundDecimal
  * 
  * @param inpValue
  * @param decim
  * @returns {Number}
  */
 function roundDecimal(inpValue,decim){
	 var inputValue=inpValue;
	 var result=0;
	 if(isNaN(inputValue) || isNull(inputValue) ){
		 inputValue=0;
	 }

	 try{
		 var num = new Number(inputValue);
		 result=num.toFixed(decim);
	 }catch (e){
		 alert(e);
	 }
	 return result;
 }
 function hidePrintButton(){
	//FIXME hiding temporary
		var print=getDomElementById("Print");
		if(!isNull(print)){
			print.style.display='none';
		}
	 
 }
 /**
  * 
  * @param domElement
  * @param tableId
  * @param elementId to be focused
  */
 function focusOnNextRowOnEnter(keyCode,domElement,tableId,currentElmId,elementId){
	 if(enterKeyNavWithoutFocus(keyCode)){
		 var curRcount= getRowId(domElement,currentElmId);
		 curRcount=parseNumber(curRcount);
		var  nxtRid=curRcount+1;
		 var nextDom=getDomElementById(elementId+nxtRid);
		 if(!isNull(nextDom)){
			 setTimeout(function(){nextDom.focus();},10);
		 }
	 }
 }
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
 		var error = responseText[error_flag];
 		if(!isNull(error)){
 		alert(error);
 		return true;
 		}
 	}
 	return false;
 }
 
 function getIssuedToTypeDOM(){
	 return getDomElementById(stockIssuedTOType);
 }
 /**getIssuedToType
  * 
  * @returns
  */
 function getIssuedToType(){
	 var issued=getIssuedToTypeDOM();
	 var issuedValue=null;
	 if(!isNull(issued)){
		 issuedValue=issued.value;
	 }
	 return issuedValue;
 }
 function getIssuedTypeBA(){
	 return getDomElementById("issuedToBa");
 }
 function getIssuedTypeCustomer(){
	 return getDomElementById("issuedToCustomer");
 }
 function getIssuedTypeEmp(){
	 return getDomElementById("issuedToEmp");
 }
 function getIssuedTypeFr(){
	 return getDomElementById("issuedToFr");
 }
 function getIssuedTypeBranch(){
	 return getDomElementById("issuedToBranch");
 }
 
 function getNormalCnoteidentifier(){
	 return getDomElementById("normalCnoteIdentifier");
 }
 /**isValidCnoteForIssueToParty :: Stock Issue to Customer/BA/FR/Emp cn validations
  * 
  * @param rowId
  * @param inputSeries
  * @returns {Boolean}
  */
 function isValidCnoteForIssueToParty(rowId,inputSeries){
	 var issuedTOType=getIssuedToType();
	 
	 
	 if(isNull(issuedTOType)){
		 alert("Please select issued To Type ");
		 return false;
	 }
	var issueTobr=getIssuedTypeBranch();
	var issueToCust=getIssuedTypeCustomer();
	var issueToEmp=getIssuedTypeEmp();
	var issueToFr=getIssuedTypeFr();
	var issueToBA=getIssuedTypeBA();
	
	var accCustType=getDomElementById("accCustomerType");
	var creditCustType=getDomElementById("creditCustomerType");
	var ccCustType=getDomElementById("creditCardCustomerType");
	var lcCustType=getDomElementById("lcCustomerType");
	var codCustType=getDomElementById("codCustomerType");
	var gvCustType=getDomElementById("govtEntityCustomerType");
	var rlCustType=getDomElementById("reverseLogCustomerType");
	
	var customerType=getDomElementById("customerType");
	
	//to get series type ex:ZCON,ZPP,BPL etc
	var seriesType=null;
	var seriesTypeDom =  getDomElementById("seriesType");
	var rowSeriesType= getDomElementById("rowSeriesType"+rowId);
	if(!isNull(seriesTypeDom)){
		seriesType = seriesTypeDom.value;
	}else if(!isNull(rowSeriesType)){
		seriesType =rowSeriesType.value;
	}
	
	if(isNull(seriesType)){
		alert("Series type is not defined");
		return false;
	}
	
	//getproduct series like A,T,B, etc
	var product=null;
	var itemSeriesDOM= getItemSeriesDOMWithoutId();
	var productDom=getRowSeries(rowId);
	if(!isNull(productDom)){
		product= productDom.value;
	}else if(!isNull(itemSeriesDOM)){
		product=itemSeriesDOM.value;
	}
	
	if(!isNull(product)){
		product = convertToUpperCase(trimString(product));
	}
	
	//get cnote product to be issued to each party type
	
	

	if(seriesType == getCnType()){
			
		if(isNull(product)){
			product=getNormalCnoteidentifier().value;
		}
			if(!isNull(issueTobr) && issuedTOType ==issueTobr.value ){//Branch type
				
			}else if (!isNull(issueToCust) && issuedTOType ==issueToCust.value){//Customer type
				//In customer we have 7 types
				
				//case (i): Credit customers case(ii):Acc Customers
				if(!isNull(customerType) && !isNull(customerType.value)){
					
					var customer = trimString(customerType.value);
					if(customer == trimString(accCustType.value)){
						if(!isSeriesAllowedForAccCust(product)){
							alert("Cnote with product :"+product+" for the ACC customer is not allowed");
							return false;
						}
					}else if(customer == trimString(creditCustType.value)){
						if(!isSeriesAllowedForCreditCust(product)){
							alert("Cnote with product :"+product+" for the credit customer is not allowed");
							return false;
						}
					}else if(customer == trimString(ccCustType.value)){
						if(!isSeriesAllowedForCcCust(product)){
							alert("Cnote with product :"+product+" for the CC customer is not allowed");
							return false;
						}
					}else if(customer == trimString(lcCustType.value)){
						if(!isSeriesAllowedForLcCust(product)){
							alert("Cnote with product :"+product+" for the LC customer is not allowed");
							return false;
						}
					}else if(customer == trimString(codCustType.value)){
						if(!isSeriesAllowedForCodCust(product)){
							alert("Cnote with product :"+product+" for the COD customer is not allowed");
							return false;
						}
					}else if(customer == trimString(gvCustType.value)){
						if(!isSeriesAllowedForGvCust(product)){
							alert("Cnote with product :"+product+" for the Govt Entity customer is not allowed");
							return false;
						}
					}else if(customer == trimString(rlCustType.value)){
						if(!isSeriesAllowedForRlCust(product)){
							alert("Cnote with product :"+product+" for the Rev Logistics customer is not allowed");
							return false;
						}
					}
					
					
					
					
					else{
						alert("Invalid customer type");
						return false;
					}
				}else{
					alert("Customer type is not defined");
					return false;
				}
				
			}else if (!isNull(issueToEmp) && issuedTOType ==issueToEmp.value){
				//cnote validation for Employee
				if(!isSeriesAllowedForEmp(product)){
					alert("Cnote with product :"+product+" for the Employee is not allowed");
					return false;
				}
				
			}else if (!isNull(issueToFr) && issuedTOType ==issueToFr.value){
				//cnote validation for Franchisee
				if(!isSeriesAllowedForFr(product)){
					alert("Cnote with product :"+product+" for the Franchisee is not allowed");
					return false;
				}
				
			}else if (!isNull(issueToBA) && issuedTOType ==issueToBA.value){
				//cnote validation for Business Associate
				
				if(!isSeriesAllowedForBa(product)){
					alert("Cnote with product :"+product+" for the BA is not allowed");
					return false;
				}
			}else {
				alert("Improper issue information");
				return false;
			}
			
		
	}
	
	return true;
 }
 function convertStringToArray(str){
		var arrayA=null;
		var seriesArray= str.split(",");
		if(seriesArray!=null){
			arrayA= new Array();
			for(var i=0;i<seriesArray.length;i++){
				arrayA[i]=trimString(seriesArray[i]);
			}
		}
		return arrayA;
	}
 /**
  * clearShippedToCode
  */
 function clearShippedToCode(){
	var shippedToCode =getDomElementById("shippedToCode");
	if(shippedToCode!=null){
		shippedToCode.value="";
	}
 }
 function isErrorJsonResponce(ObjeResp){
	 	var responseText=null;
	 	try{
	 		responseText = jsonJqueryParser(ObjeResp);
	 	}catch(e){
	 		
	 	}
	 	if(!isNull(responseText)){
	 		var error = responseText[error_flag];
	 		if(!isNull(error)){
	 		alert(error);
	 		return true;
	 		}
	 	}
	 	return false;
	 }
 function roundingOff(amountInput){
		var amount = amountInput+"";
		var splittedValue=amount.split(".");
		
		if(splittedValue.length>1){
			var decValue= splittedValue[1];
			if(!isNull(decValue)&& parseInt(decValue)>=51){
				var amount = parseInt(splittedValue[0]);
				return amount+1;
			}else{
				return splittedValue[0];
			}
		}
	}
//end of Changes by <31913> on 16/01/2013