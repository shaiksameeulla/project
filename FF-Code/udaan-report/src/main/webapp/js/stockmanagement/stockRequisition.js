var rowCount = 1;



           function fnClickAddRow() {
        	   var tempRow=rowCount;
           	$('#reqGrid').dataTable().fnAddData( [
           rowCount,
           '<select name="to.rowItemTypeId" id="rowItemTypeId'+ rowCount +'" class="selectBox width170" onchange="getItemList(this)" onkeypress = "return enterKeyNav(\'rowItemId'+ rowCount +'\',event.keyCode);" ><option value="">--Select--</option></select>',
           '<select name="to.rowItemId" id="rowItemId'+ rowCount +'" class="selectBox width170" onchange="isDuplicateRowExist(this)" onkeypress = "return enterKeyNav(\'rowRequestedQuantity'+ rowCount +'\',event.keyCode);"><option value="">--Select--</option></select>',
           '<input type="text" id="rowDescription'+ rowCount +'" name="to.rowDescription" class="txtbox width145" readOnly="true" tabindex="-1"/>',
           '<input type="text" id="rowUom'+ rowCount +'" name="to.rowUom" class="txtbox width50" readOnly="true" tabindex="-1"/>',
           '<input type="text" id="rowRequestedQuantity'+ rowCount +'" name="to.rowRequestedQuantity" class="txtbox width40" maxlength="6" onkeypress="return onlyNumeric(event)" onblur="isValidToAddRow(this)" onkeydown="enterKeyNav(\'rowRemarks'+ rowCount +'\',event.keyCode);"/>',
           '<input type="text" id="rowRemarks'+ rowCount +'" name="to.rowRemarks" class="txtbox width145" maxlength="20" tabindex="-1" onkeydown="focusOnNextRowOnEnter(event.keyCode,this,\'reqGrid\',\'rowRemarks\',\'rowItemTypeId\')"/> \
           <input type="hidden" id="rowStockReqItemDtlsId'+ rowCount +'" name="to.rowStockReqItemDtlsId" value="0"/>',
                 ] );
           	rowCount++;
           	//alert("row added"+rowCount+"rowItemTypeList"+rowItemTypeList);
           	populateReqDropdown(rowItemTypeList,tempRow,'rowItemTypeId');
           	focusOnItemType(tempRow);
           }
           
function checkMandatoryForAdd(rowId){
	var itemType=getDomElementById("rowItemTypeId"+rowId);
	var item=getDomElementById("rowItemId"+rowId);
	var desc=getDomElementById("rowDescription"+rowId);
	var uom=getDomElementById("rowUom"+rowId);
	var reqQnty=getDomElementById("rowRequestedQuantity"+rowId);
	var approveQnty=getDomElementById("rowApprovedQuantity"+rowId);
	var procurementType=getDomElementById("rowStockProcurementType"+rowId);
	//var remarks=getDomElementById("rowRemarks"+rowId);
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
	if(isNull(reqQnty.value)){
		alert("Please provide Requested Quantity "+lineNum);
		reqQnty.value="";
		setTimeout(function(){reqQnty.focus();},10);
		return false;
	}else if(isNull(parseNumber(reqQnty.value))){
		alert(" Requested Quantity can not be zero "+lineNum);
		reqQnty.value="";
		setTimeout(function(){reqQnty.focus();},10);
		return false;
	}
	if(!isNull(approveQnty)){//For Only approval process
		if(isNull(approveQnty.value)){
			alert("Please provide Approved Quantity "+lineNum);
			approveQnty.value="";
			setTimeout(function(){approveQnty.focus();},10);
			return false;
		}else{
			if(parseNumber(approveQnty.value)==0){
				alert("Approved Quantity can not be zero "+lineNum);
				approveQnty.value="";
				setTimeout(function(){approveQnty.focus();},10);
				return false;
			}
			if(parseNumber(reqQnty.value)<parseNumber(approveQnty.value)){
				alert("Approved Quanity can not be more than requested quantity "+lineNum);
				approveQnty.value="";
				setTimeout(function(){approveQnty.focus();},10);
				return false;
			}
		}
		if(!isNull(procurementType)){
			if(isNull(procurementType.value)){
				alert("Please provide Procurement Type "+lineNum);
				setTimeout(function(){procurementType.focus();},10);
				return false;
			}
		}//end of procurement type
		
	}//End for Approval process
	
	/*if(isNull(remarks.value)){
		alert("Please provide remarks  "+lineNum);
		setTimeout(function(){remarks.focus();},10);
		return false;
	}*/
	return true;
}
function checkMandatoryForSave(){
	var isNew=false;
	 var rowId=null;
	
	if(isNewRequisition()){
		isNew=true;
	}
	var tableee = getDomElementById('reqGrid');
	var totalRowCount =  tableee.rows.length;
	var lastRowId = getTableLastRowIdByElement('reqGrid','to.rowItemTypeId','rowItemTypeId');
	var itemTypeNames=document.getElementsByName("to.rowItemTypeId");
	for(var i=1;i<totalRowCount;i++){
		if(isNew){
			var cell =itemTypeNames[i-1];
			rowId = getRowId(cell,"rowItemTypeId");
		}else{
			rowId=i;
		}
		if(rowId == lastRowId){
			if(isLastRowEmpty(lastRowId)){
				return true;
			}
		}
		
		if(!checkMandatoryForAdd(rowId)){
			return false;
		}
	}
	
	return true;
}
function isLastRowEmpty(rId){
	
	var itemType=getDomElementById("rowItemTypeId"+rId);
	if(itemType!=null && isNull(itemType.value)){
		return true;
	}
	return false;
}
function isValidToApprove(){

	var tableee = getDomElementById("approveTable");
	var totalRowCount =  tableee.rows.length;
	var box = document.getElementsByName('to.checkbox');
	for(var i=1;i<totalRowCount;i++){
		if(box[i-1].checked){
			if(!isValidCheckBox(box[i-1])|| !checkMandatoryForAdd(i)){
				return false;
			}
		}
	}
	
	return true;
}
function approve(){
	//Do you want to Approve the details ?
	if(isCheckBoxSelected('to.checkbox') && isValidToApprove()){
		var url="./approveRequisition.do?submitName=approveRequisitionDtls";
		submitRequisition(url,'Approve the requisition');
	}
}
function addNewRow(){
	if(checkMandatoryForSave()){
		fnClickAddRow();
	}
}
function requisitinStartup(){
	if(isNewRequisition()){
		getItemTypeList('createRequisitionForm');
		fnClickAddRow();
	}else{
		disableRequisition();
	}
	hidePrintButton();
}

function save(action){
	if(checkMandatoryForSave()&& isAtleastRowExist()){
		var url="./createRequisition.do?submitName=saveRequisitionDtls";
		if(action =='Save'){
			action="Save Requisition";
			getDomElementById("requisitionNumber").value="";
		}
		submitRequisition(url,action);
	}
}

function clearScreen(type){
	var url;
	if(type =="create"){
		url="./createRequisition.do?submitName=viewFormDetails";
	}else{
		url="./approveRequisition.do?submitName=viewFormDetails";
	}
	submitRequisition(url,'Clear');
}
		function isValidDetails(domElem){
			var isNew=false;
			var currRowId = getRowId(domElem,"rowItemId");
			var currItemTypeDom=getDomElementById("rowItemTypeId"+currRowId);
			var tableee = getDomElementById('reqGrid');
			var totalRowCount =  tableee.rows.length;
			var itemTypeNames=document.getElementsByName("to.rowItemTypeId");
			if(isNewRequisition()){
				isNew=true;
			}
			for(var i=1;i<totalRowCount;i++){
				var rowId=i;
				if(isNew){
					var cell = itemTypeNames[i-1];
					rowId = getRowId(cell,"rowItemTypeId");
				}
				if(currRowId!=rowId){
					var itemType=getValueByElementId("rowItemTypeId"+rowId);
					var item=getValueByElementId("rowItemId"+rowId);
					if(currItemTypeDom.value == itemType && domElem.value==item){
						alert("Duplicate material type and material,already available at line :"+rowId +"\n Please select another");
						domElem.value='';
						domElem.focus();
						clearItemDetailsByRowId(currRowId);
						return false;
					}
				}
			}
			return true;
		}
		
		function isAtleastRowExist(){
			var isNew=false;
			var rowId =1;
			if(isNewRequisition()){
				isNew=true;
			}
			 if(isNew){
				 var itemTypeNames=document.getElementsByName("to.rowItemTypeId");
				var cell = itemTypeNames[0];
				 rowId = getRowId(cell,"rowItemTypeId");
			 }
				
				if(!checkMandatoryForAdd(rowId)){
					return false;
				}
			return true;
		}

function isDuplicateRowExist(domElem){
	if(isValidDetails(domElem)){
		if(!isNull(domElem.value)){
			getItemDetails(domElem);
		}
	}
}
function find(req){
	var url=null;
	var reqNum=getDomElementById("requisitionNumber");
	if(!isNull(reqNum.value)){
		if(req =='approve'){
			 url="./approveRequisition.do?submitName=searchRequisitionDtls";
		}else{
		 url="./createRequisition.do?submitName=findRequisitionDtlsByReqNumber";
		}
		submitReqWithoutPromp(url);
	}else{
		alert("Please provide Stock Requisition Number");
		reqNum.focus();
	}
}
function isNewRequisition(){
	var requisition=getDomElementById("stockRequisitionId");
	if(isNull(requisition.value)){
		return true;
	}
	return false;
}
function disableRequisition(){
	var reqNum=getDomElementById("requisitionNumber");
	reqNum.readOnly="true";
	disableForUser();
	
	/*if(!isNewRequisition()){
		getDomElementById("Add").style.display='none';
	}*/
	/*if(!isNewRequisition()){
		dropdownDisable();
	}*/
}

//disabling the approved fields for next time
function disableForApprove(){
	var leng= getTableLength('approveTable');
	for(var i=1;i<leng;i++){
		var apprQnty= getDomElementById("rowApprovedQuantity"+i);
		var procType= getDomElementById("rowStockProcurementType"+i);
		var apprfalg=getDomElementById("isApproved"+i);
		var isAppred=false;
		if(apprfalg!=null && !isNull(apprfalg.value)){
			isAppred=true;
		}
		var apprRem= getDomElementById("rowRemarks"+i);
		if(apprQnty != null ){
			if(!isNull(apprQnty.value)&& isAppred){
				//apprQnty.readOnly=true;
				apprQnty.setAttribute("readOnly",true);
				apprQnty.setAttribute("tabindex","-1");
				procType.disabled=true;
			}else{
				apprQnty.readOnly=false;
				apprQnty.removeAttribute("tabindex");
			}
		}
		
		if(apprRem != null && isAppred){
			//apprRem.readOnly=true;
			apprRem.setAttribute("readOnly",true);
			apprRem.setAttribute("tabindex","-1");
		}
	}
	if(!isNewRequisition()){
	var reqNum=getDomElementById("requisitionNumber");
	reqNum.readOnly="true";
	}else{
		getDomElementById("Approve").style.display='none';
		getDomElementById("Print").style.display='none';
		
		
	}
	hidePrintButton();
}

function isValidToAddRow(domElemnt){

	var curRowId = getRowId(domElemnt,'rowRequestedQuantity');
	var tableee = document.getElementById('reqGrid');
	var totalRowCount = tableee.rows.length-1;

	if(parseInt(curRowId)== totalRowCount){
		if(checkMandatoryForAdd(curRowId)){
			addNewRow();
		}
	}
}

function isValidForAddNewRow()
{
	var tableee = document.getElementById('reqGrid');
	var totalRowCount = tableee.rows.length-1;
	if(checkMandatoryForAdd(totalRowCount)){
		addNewRow();
	}
}

function checkAllForApprove(ckbxsName,checkedVal){
	var box = document.getElementsByName(ckbxsName);
	var isApproved = document.getElementsByName('isApproved');
	//alert("box.length"+box.length);
	if(box !=null && box.length >0){
		for(var i=0;i<box.length;i++){
			var isAppred=false;
			var apprfalg=isApproved[i];
			if(apprfalg!=null && !isNull(apprfalg.value) && apprfalg.value==getApprovedFlagYes()){
				isAppred=true;
				
			}else{
				
				isAppred=false;
			}
			if(checkedVal == true){
				if(!isAppred){
					box[i].checked =true;
				}
			}else if(checkedVal == false){
				box[i].checked =false;
			}
		}
		if(!isAtleastOneCheckBoxSelected(ckbxsName)){
			getDomElementById('chkAll').checked =false;
			if(!atleastOneNotApproved()){
				alert("All Materials Approved ");
			}
		}
	}else{
		getDomElementById('chkAll').checked =false;
	}
}
function atleastOneNotApproved(){
	var isApproved = document.getElementsByName('isApproved');
	var checkFalg = false;   
	if(isApproved !=null && isApproved.length >0){
		
		for(var i=0;i<isApproved.length;i++){
			if(isApproved[i]!=null && !isNull(isApproved[i].value) && isApproved[i].value==getApprovedFlagYes()){
				checkFalg=false;
			}else{
				checkFalg=true;
				break;
			}
		}
	}
	return checkFalg;
}