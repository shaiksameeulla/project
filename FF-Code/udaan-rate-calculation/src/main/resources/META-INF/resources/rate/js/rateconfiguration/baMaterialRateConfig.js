$(document).ready( function () {
	var oTable = $('#'+BA_MTRL_GRID).dataTable( {
		"sScrollY": "150",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
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
	baMaterialRateStartup();
} );

/************ Date Validation START ************/
/**
 * To validate EFFECTIVE FROM DATE
 * @param obj
 * @returns {Boolean}
 */
function validateFromDate(obj){
	var isRenew = getDomElementById("isRenew").value;
	var baMtrlRateId = getDomElementById("baMaterialRateId").value;
	var isAlreadyRenewed = getDomElementById("isAlreadyRenewed").value;
	if((isRenew==YES || isNull(baMtrlRateId)) && isAlreadyRenewed==NO){
		if(!isNull(obj.value)){
			var todayDate=TODAY_DATE;
			var i=compareDates(todayDate, obj.value);
			if(i==undefined || i==0 || i==1){
				alert("Effective from date should be greater than today date");
				setTimeout(function(){obj.focus();}, 10);
				obj.value="";
				return false;
			}
		}
	}
}
/************ Date Validation END ************/

var rowCount=1;
var error_flag="ERROR";

/**
 * To add row for BA Material Rate table
 */
function addRow() {
	$('#'+BA_MTRL_GRID).dataTable().fnAddData([
	    rowCount,
		'<select name="to.itemTypeIds" id="itemTypeId'+rowCount+'" class="selectBox width170" onchange="getItemList(this);" onkeypress="return enterKeyNav(\'itemId'+rowCount+'\',event.keyCode);" ><option value="">---Select---</option>'+ itemTypeOptions +'</select>',
		'<select name="to.itemIds" id="itemId'+rowCount+'" class="selectBox width170" onchange="isDuplicateRowExist(this);" onkeypress="return enterKeyNav(\'amount'+rowCount+'\',event.keyCode);"><option value="">---Select---</option></select>',
		'<input type="text" name="to.uoms" id="uom'+rowCount+'" class="txtbox width100" readonly="true" tabindex="-1" />',
		'<input type="text" name="to.amounts" id="amount'+rowCount+'" class="txtbox width100" maxlength="6" onkeypress="return isValidToAddNewRow(event, this);" />\
		 <input type="hidden" name="to.rowNumber" value="'+rowCount+'" />'
	 ]);
	rowCount++;
}

/**
 * Start up function for BA Material Rate
 */
function baMaterialRateStartup(){
	var baMtrlRateId = getDomElementById("baMaterialRateId").value;
	if(isNull(baMtrlRateId)){
		addRow();
	}
}

/**
 * To add new row, when user opts to add
 * @returns {Boolean}
 */
function addNewRow(){
	for(var i=1;i<rowCount;i++){
		if(!validateBAMaterialGrid(i)){
			return false;
		}
	}
	addRow();
	var nextRowId = parseInt(rowCount-1);
	var nextDomEle = getDomElementById("itemTypeId"+nextRowId);
	setTimeout(function(){nextDomEle.focus();}, 10);
	return true;
}

/**
 * To validate grid details
 * @param rowId
 * @returns {Boolean}
 */
function validateBAMaterialGrid(rowId){
	var itemType = getDomElementById("itemTypeId"+rowId);
	var item = getDomElementById("itemId"+rowId);
	var amt = getDomElementById("amount"+rowId);
	var atLine = " at Line:"+rowId;
	if(isNull(itemType.value)){
		alert("Please select Material Type"+atLine);
		setTimeout(function(){itemType.focus();}, 10);
		return false;
	}
	if(isNull(item.value)){
		alert("Please select Item Type"+atLine);
		setTimeout(function(){item.focus();}, 10);
		return false;
	}
	if(isNull(amt.value)){
		alert("Please provide Amount"+atLine);
		setTimeout(function(){amt.focus();}, 10);
		return false;
	}
	return true;
}

/**
 * To check mandatory fields
 * @returns {Boolean}
 */
function checkMandatoryFields(){
	var fromDt = getDomElementById("fromDateStr");
	if(isNull(fromDt.value)){
		alert("Please provide effective from date");
		setTimeout(function(){fromDt.focus();}, 10);
		return false;
	}
	for(var i=1;i<rowCount;i++){
		if(!validateBAMaterialGrid(i)){
			return false;
		}
	}
	return true;
}

/**
 * To save BA Material Rate Details
 */
function saveBAMaterialRateDtls(){
	if(checkMandatoryFields()){
		var url="./baMaterialRateConfig.do?submitName=saveBAMaterialRateDtls";
		ajaxCall(url, BA_MTRL_RATE_FORM, callBackSaveBAMaterial);
	}
}
//call back function for saveBAMAterialRateDtls
function callBackSaveBAMaterial(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = eval('('+ajaxResp+')');
		alert("Data saved successfully.");
		var url = "./baMaterialRateConfig.do?submitName=viewBAMaterialRateConfig";
		if(data.isRenew==YES){
			var doc = window.opener;
			if(!isNull(doc)){
				/* To close child window */
				self.close();
				/* To reload opener window */
				window.opener.location.reload();
			}
		} else {
			submitForm(url);
		}
	} else {
		alert("Data not saved. Please try again.");
	}
}

/**
 * To cancel BA Material Rate Details
 */
function cancelBAMaterialRateDtls(){
	if(confirm("Do you want to cancel ?")){
		var isRenew = getDomElementById("isRenew").value;
		var url = "";
		if(isRenew==YES){
			var prevBAMtrlRateId = getDomElementById("prevBAMtrlRateId").value;
			url = "./baMaterialRateConfig.do?submitName=renewBAMaterialRateConfig"
				+"&baMaterialRateId="+prevBAMtrlRateId;
		} else {
			url = "./baMaterialRateConfig.do?submitName=viewBAMaterialRateConfig";
		}
		submitForm(url);
	}
}

/**
 * To submit form
 * @param pageURL
 */
function submitForm(pageURL){
	document.baMaterialRateConfigForm.action = pageURL;
	document.baMaterialRateConfigForm.submit();
}

/************ Populating item list drop down START ************/
/**
 * To get item list by item type
 * @param obj
 */
function getItemList(obj){
	var gridId = getRowId(obj, "itemTypeId");
	clearItemDtlsByRowId(gridId);
	if(!isNull(obj.value)){
		var url="./baMaterialRateConfig.do?submitName=getItemByTypeMap&typeId="+obj.value;
		ajaxJqueryWithRow(url, BA_MTRL_RATE_FORM, callGetItemByType, gridId);
	}else{
		clearItemDtlsByRowId(gridId);
		createEmptyDropDown('itemId'+gridId);
	}

}
//call back function for callGetItemByType
function callGetItemByType(ajaxResp, gridId){
	if (!isNull(ajaxResp)) {
		itemList = ajaxResp;
		var rid = trimString(gridId);
		populateItemDropdown(itemList, rid, "itemId");
	}else{
		alert("Material Details Does Not Exist \n Please Select Another Material Type");
		clearItemDtlsByRowId(gridId);
		createEmptyDropDown('itemId'+gridId);
		var itemType = getDomElementById('itemTypeId'+gridId);
		itemType.value="";
		setTimeout(function(){itemType.focus();}, 10);
	}
}
//helper function to create drop down
function populateItemDropdown(rowList, rowId, textElem){
	var itemTypeElem=textElem+rowId;
	if(!isNull(rowList)){
		createDropDown(itemTypeElem, rowList);
	}
}
/**
 * clearItemDtlsByRowId : clear grid information
 * @param rowId
 */
function clearItemDtlsByRowId(rowId){
	getDomElementById('itemId'+rowId).value="";
	clearItemDtlsByRowIdExcludeItemId(rowId);
}
/**
 * clearItemDtlsByRowIdExcludeItemId
 * @param rowId
 */
function clearItemDtlsByRowIdExcludeItemId(rowId){
	getDomElementById('uom'+rowId).value="";
	getDomElementById('amount'+rowId).value="";
}
/**
 * To check duplicate row details if any
 * @param domElem
 */
function isDuplicateRowExist(domElem){
	if(isValidDetails(domElem)){
		if(!isNull(domElem.value)){
			getItemDtls(domElem);
		}
	}
}
/**
 * To validate grid for duplicate details
 * @param domElem
 * @returns {Boolean}
 */
function isValidDetails(domElem){
	var currRowId = getRowId(domElem,"itemId");
	var currItemTypeDom=getDomElementById("itemTypeId"+currRowId);
	var tableee = getDomElementById(BA_MTRL_GRID);
	var totalRowCount =  tableee.rows.length;
	for(var i=1;i<totalRowCount;i++){
		var rowId=i;
		if(currRowId!=rowId){
			var itemType=getValueByElementId("itemTypeId"+rowId);
			var item=getValueByElementId("itemId"+rowId);
			if(currItemTypeDom.value == itemType && domElem.value==item){
				alert("Duplicate material type and material, already available at line:"+ rowId +"\n Please select another");
				domElem.value='';
				setTimeout(function(){domElem.focus();}, 10);
				clearItemDtlsByRowId(currRowId);
				return false;
			}
		}
	}
	return true;
}

/**
 * To get details of material
 * @param domObj
 * @returns {Boolean}
 */
function getItemDtls(domObj){
	var gridId = getRowId(domObj,"itemId");
	var itemType=getDomElementById("itemTypeId"+gridId);
	var itemTypeId= itemType.value;
	clearItemDtlsByRowIdExcludeItemId(gridId);
	if(isNull(domObj.value)){
		return;
	}
	if (!isNull(itemTypeId)) {
		var url="./baMaterialRateConfig.do?submitName=getItemDtlsByItemIdAndTypeId"
			+"&itemId="+domObj.value
			+"&itemTypeId="+itemTypeId;
		ajaxJqueryWithRow(url, BA_MTRL_RATE_FORM, callBackItemDtls, gridId);
	} else {
		alert("Please select Material Type at line:"+gridId);
		domObj.value="";
		setTimeout(function(){itemType.focus();}, 10);
		return false;
	}
}
//call back function to populate details of material - getItemDtls
function callBackItemDtls(ajaxResp, gridId){
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp); 
		var error = responseText[error_flag];
		if(responseText!=null && error!=null){
			alert(error);
			var domElm="itemId"+gridId;
			createEmptyDropDown(domElm);
			clearItemDtlsByRowId(gridId);
			return false;
		}
		populateItemDtlsByRowId(responseText, gridId);
	}else{
		alert("Material details does not exist for selected Material Type/Material");
		var domElm="itemId"+gridId;
		createEmptyDropDown(domElm);
		clearItemDtlsByRowId(gridId);
		var itemType=getDomElementById("itemTypeId"+gridId);
		if(!isNull(itemType.value)){
			getItemList(itemType);
		}
	}
}
/**
 * populateItemDtlsByRowId : common ajax response for ItemTO
 * @param result
 * @param rowId
 */
function populateItemDtlsByRowId(result, rowId){
	getDomElementById('uom'+rowId).value = result.uom;
}
/************ Populating item list drop down END ************/

/**
 * To check whether enter key pressed or not
 * @param e
 * @returns {Boolean}
 */
function isEnterKeyPressed(e){
	var key;
	if (window.event) {
		key = window.event.keyCode;//IE
	} else {
		key = e.which;//Firefox
	}
	if (key==13) {//Enter Key- 13
		return true;
	} else {
		return false;
	}
}

/**
 * To check whether that is valid to add new row
 * @param e
 * @param domElement
 * @returns {Boolean}
 */
function isValidToAddNewRow(e, domElement){
	if(!domElement.readOnly){
		if(onlyDecimal(e)){//only decimal allow
			if (isEnterKeyPressed(e)) {
				var currentRowId = getRowId(domElement,'amount');
				var tbl = document.getElementById(BA_MTRL_GRID);
				var totalRowCount = tbl.rows.length-1;
				if(parseInt(currentRowId)==totalRowCount){
					if(!addNewRow())
						return false;
				} else {
					var nextRowId = parseInt(currentRowId) + 1;
					var nextDomEle = getDomElementById("itemTypeId"+nextRowId);
					setTimeout(function(){nextDomEle.focus();}, 10);
				}
			}
			return true;
		}
		return false;
	}
}

/**
 * To renew BA Material Rate Details
 */
function renewBAMtrlRateDtls(){
	//var toDt = getDomElementById("toDateStr").value;
	//var frmDt = getDomElementById("fromDateStr").value;
	//if(isNull(toDt) && TODAY_DATE>frmDt){
		var baMtrlRateId = getDomElementById("baMaterialRateId").value;
		var url = "./baMaterialRateConfig.do?submitName=renewBAMaterialRateConfig"
			+"&baMaterialRateId="+baMtrlRateId;
		window.open(url, 'BA Material Rate Configuration - Renew', 'toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
	/*} else {
		if(!isNull(toDt)){
			alert("BA material rates already renew till date: "+toDt);
		} else {
			alert("BA material rates can not renew before date: "+frmDt);
		}
	}*/
}
