var rowCountP = 1;
var branchCodeArr = new Array();
var arrIndex = 0;
var NORM_PICKUP_TABLE="pickupDetailsTable";
var ECOM_PICKUP_TABLE="eCommercePickupDtlsTable";
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
var delPickDlvIds = "";
/**
 * To add row(s) to Pickup Details Table. 
 */
function addRows(tableId) {
	if(tableId==NORM_PICKUP_TABLE){
			$('#'+NORM_PICKUP_TABLE).dataTable().fnAddData([
		        '<input type="checkbox" name="chkBoxName" id="chk'+ rowCountP +'" class="checkbox" tabindex="-1" />',
		        '<input type="text" name="to.pincode" id="pincode'+ rowCountP +'" class="txtbox width70" maxlength="6" onkeypress="return validatePinKey(event);" onblur="getPickupBranchs('+rowCountP+');" />',
		        '<select name="to.pickupBranch" id="pickupBranch'+ rowCountP +'" onclick="getPickupBranchsOnClick('+ rowCountP +');" onchange="getPickupBranchCode('+ rowCountP +');" class="selectBox width100"  /><option value="">---Select---</option></select>',
		        '<input type="text" name="to.locationName" id="locationName'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
		        '<input type="text" name="to.address1" id="address1'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
		        '<input type="text" name="to.address2" id="address2'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
		        '<input type="text" name="to.address3" id="address3'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
		        '<input type="text" name="to.contactPerson" id="contactPerson'+ rowCountP +'" class="txtbox width100" maxlength="30" />',
		        '<input type="text" name="to.designation" id="designation'+ rowCountP +'" class="txtbox width100" maxlength="30" />',
		        '<input type="text" name="to.mobile" id="mobile'+ rowCountP +'" class="txtbox width100"  onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);"  maxlength="10" />',
		        '<input type="text" name="to.email" id="email'+ rowCountP +'" class="txtbox width100" maxlength="50"  onblur="validateEmailAddress(this);"/>',
		        '<input type="checkbox" name="to.billing" id="billing'+ rowCountP +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocation(this,'+rowCountP+');" />\
		         <input type="hidden" name="to.isBillLoc" id="isBillLoc'+rowCountP+'" />',
		        '<input type="checkbox" name="to.payment" id="payment'+ rowCountP +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocation(this,'+rowCountP+');" />\
		         <input type="hidden" name="to.isPayLoc" id="isPayLoc'+rowCountP+'" />',
		        '<input type="text" name="to.customerCode" id="customerCode'+ rowCountP +'" class="txtbox width100" maxlength="10" tabindex="-1" readonly="true" onkeypress="return onlyNumeric(event);" onblur="validateCustomerCode(this,'+rowCountP+');"/>\
		         <input type="hidden" name="to.pickupBranchCode" id="pickupBranchCode'+rowCountP+'" />\
		         <input type="hidden" name="to.contractPaymentBillingLocationId" id="conPayBillLocId'+rowCountP+'" />\
		         <input type="hidden" name="to.pickupDlvLocId" id="pickupDlvLocId'+rowCountP+'" />\
		         <input type="hidden" name="to.contractId" id="contractId'+rowCountP+'" />\
		         <input type="hidden" name="to.addressId" id="addressId'+rowCountP+'" />\
		         <input type="hidden" name="branchFlag" id="branchFlag'+rowCountP+'" value = "N" />\
		         <input type="hidden" name="branchPincode" id="branchPincode'+rowCountP+'" />\
		         <input type="hidden" name="to.locCity" id="locCity'+rowCountP+'" />\
		         <input type="hidden" name="to.locPincodeId" id="locPincodeId'+rowCountP+'" />\
		         <input type="hidden" name="to.locCode" id="locCode'+rowCountP+'" />'
		], false);		
	} else if(tableId==ECOM_PICKUP_TABLE) {
		$('#'+ECOM_PICKUP_TABLE).dataTable().fnAddData([
			'<input type="checkbox" name="chkBoxName" id="chk'+ rowCountP +'" class="checkbox" tabindex="-1" />',
			'<input type="text" name="to.pincode" id="pincode'+ rowCountP +'" class="txtbox width70" maxlength="6" onkeypress="return validatePinKey(event);" onblur="getPickupBranchs('+rowCountP+');" />',
			'<select name="to.pickupBranch" id="pickupBranch'+ rowCountP +'" class="selectBox width100" onclick="getPickupBranchsOnClick('+rowCountP+');" onchange="getPickupBranchCode('+ rowCountP +');"/><option value="">---Select---</option></select>',
			'<input type="text" name="to.locationName" id="locationName'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address1" id="address1'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address2" id="address2'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address3" id="address3'+ rowCountP +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.contactPerson" id="contactPerson'+ rowCountP +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.designation" id="designation'+ rowCountP +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.mobile" id="mobile'+ rowCountP +'" class="txtbox width100" onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);"  maxlength="10" />',
			'<input type="text" name="to.email" id="email'+ rowCountP +'" class="txtbox width100" maxlength="50" onblur="validateEmailAddress(this);"/>',
			'<input type="checkbox" name="to.billing" id="billing'+ rowCountP +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocation(this,'+rowCountP+');" />\
			 <input type="hidden" name="to.isBillLoc" id="isBillLoc'+rowCountP+'" />',
			'<input type="checkbox" name="to.payment" id="payment'+ rowCountP +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocation(this,'+rowCountP+');" />\
			 <input type="hidden" name="to.isPayLoc" id="isPayLoc'+rowCountP+'" />',
			'<input type="text" name="to.customerCode" id="customerCode'+ rowCountP +'" class="txtbox width100" maxlength="10" tabindex="-1" readonly="true" onkeypress="return onlyNumeric(event);" onblur="validateCustomerCode(this,'+rowCountP+');"/>\
			 <input type="hidden" name="to.pickupBranchCode" id="pickupBranchCode'+rowCountP+'" />\
			 <input type="hidden" name="to.contractPaymentBillingLocationId" id="conPayBillLocId'+rowCountP+'" />\
			 <input type="hidden" name="to.pickupDlvLocId" id="pickupDlvLocId'+rowCountP+'" />\
			 <input type="hidden" name="to.contractId" id="contractId'+rowCountP+'" />\
			 <input type="hidden" name="to.addressId" id="addressId'+rowCountP+'" />\
			 <input type="hidden" name="branchFlag" id="branchFlag'+rowCountP+'" value = "N" />\
			 <input type="hidden" name="branchPincode" id="branchPincode'+rowCountP+'" />\
			 <input type="hidden" name="to.locCity" id="locCity'+rowCountP+'" />\
			 <input type="hidden" name="to.locPincodeId" id="locPincodeId'+rowCountP+'" />\
			 <input type="hidden" name="to.locCode" id="locCode'+rowCountP+'" />'
		], false);
	}
	rowCountP++;
}

/**
 * On load of Rate Contract - Normal.
 */
function ratePickupDtlsStartup(){
	jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
	validateContractStatus();
	searchPickupDlvDtls();	
}

/**
 * On load of Rate Contract - ECommerce.
 */
function rateEcommercePickupDtlsStartup(){
	jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
	validateContractStatus();
	searchPickupDlvDtls();
}

/**
 * To Add Row(s) To Pickup Details Grid.
 */
function addRowToPickupDtls(tableId){
	/*var start = new Date().getTime();*/
	addRows(tableId);
	var rId=rowCountP-1;
	conType = getRateContractType();
	
	if(conType==NORM){
		$('#'+NORM_PICKUP_TABLE).dataTable().fnDraw();
	}else{
		$('#eCommercePickupDtlsTable').dataTable().fnDraw();
	}
	
	var type=getTypeOfBilling();
	if(type==DD) {
		buttonDisabled('addPickupBtn','btnintformbigdis');
	} else if(type==CC) {
		//if(isBillPayLocChkd()){
			//var rId=rowCountP-1;
			//getDomElementById("billing"+rId).disabled=true;
			//getDomElementById("payment"+rId).disabled=true;
		//}
	} else if(type==DC) {
		
		if(!isNull(getDomElementById("customerCode"+rId))){
			var rowElem = getDomElementById("customerCode"+rId);
			rowElem.readOnly = false;
			rowElem.setAttribute("tabindex","0");
		}
	}
	setBillPayLocation(rId);
}


function additioanlRowToPickupDtls(tableId){
	/*var start = new Date().getTime();*/
	addRows(tableId);	
}

/**
 * To check all checkbox
 * 
 * @param checkBoxObj
 */
function selectAllCheckBox(checkBoxObj) {
	if(checkBoxObj.checked){//check
		for(var i=1; i<rowCountP; i++){
			if(!isNull(getDomElementById("chk"+i))){
				getDomElementById("chk"+i).checked=true;
			}
		}
	} else {//un-check
		for(var i=1; i<rowCountP; i++){
			if(!isNull(getDomElementById("chk"+i))){
				getDomElementById("chk"+i).checked=false;
			}
		}
	}
}

/* ######### DELETE ROW START ######### */
/**
 * To Delete Row(s) To Pickup Details Grid.
 */
function deleteRowToPickupDtls(tableId){
	deleteRows(tableId, 'chk', 'checkAll');
}
/**
 * To delete the selected row(s) in the grid table
 * 
 * @param tableId
 * @returns {Boolean}
 */
function deleteRows(tableId, chk, chkAllId){
	var table=getDomElementById(tableId);
	var tablerowCountP=table.rows.length;
	var isSelectAnyCheckBox=false;
	var rowId;
	var pickupDlvType=getDomElementById("pickupDlvType").value;
	for (var i=0; i<tablerowCountP; i++) {
		var row=table.rows[i];
		var chkbox=row.cells[0].childNodes[0];
		if(chkbox!=null && chkbox.checked==true){
			isSelectAnyCheckBox=true;
			break;
		}
	}
	if(!isSelectAnyCheckBox){
		alert("Please select atleast one row to delete.");
		return false;
	}
	//delPickDlvIds = "";
	for (var i=0; i<tablerowCountP; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (tablerowCountP == 2){
			if(chkbox!=null && chkbox.checked==true){
				alert ("Default row can't delete");
				chkbox.checked = false;
				rowId=getRowId(chkbox, chk);
				getPickDlvIdValues(rowId);
				if(pickupDlvType==PICKUP_CONTRACT_TYPE){
					clearPickupRow(rowId);
				}else if(pickupDlvType==DLV_CONTRACT_TYPE){
					clearDlvRow(rowId);
				}
			}
		} else if (chkbox!=null && chkbox.checked==true) {
			rowId=getRowId(chkbox, chk);
			getPickDlvIdValues(rowId);
			if(pickupDlvType==PICKUP_CONTRACT_TYPE){
				validateRowForDel(rowId);
			}else if(pickupDlvType==DLV_CONTRACT_TYPE){
				validateRowForDelDlv(rowId);
			}
			deleteRow(tableId, i-1);
			tablerowCountP--;
			//rowCountP--;
			i--;
		}
	}
	getDomElementById(chkAllId).checked=false;
}
/**
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable=$('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}
/* ######### DELETE ROW END ######### */

var pincodeRowId = "";
/**
 * To get pickup branch(s).
 * @param obj
 */
function getPickupBranchs(rId){
	//pincodeRowId = getRowId(obj, "pincode");
	pincodeRowId = rId;
	var pinFlag = false;
	var obj = getDomElementById("pincode"+rId);
	if(!isNull(obj.value) && !isNull(getDomElementById("branchPincode"+rId).value)){
		if(obj.value != getDomElementById("branchPincode"+rId).value){
			pinFlag = true;
		}
	}else{
		pinFlag = true;
	}
	if(!isNull(obj.value) && validatePincode(obj,pinFlag)){
		if((pinFlag == true)){
		document.getElementById('branchPincode'+rId).value = obj.value;
		var url='./rateContract.do?submitName=getPickupBranchsByPincode'
			+ '&pincode='+obj.value;
		ajaxCallWithoutForm(url, callBackPickupBranchs);
		}
	} else {
		obj.value="";
		//clearPickupRow(pincodeRowId);
	}
}
//call back function for getPickupBranchs
function callBackPickupBranchs(ajaxResp){
	
	if (!isNull(ajaxResp)) {
		//var resp = jsonJqueryParser(ajaxResp);
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			var pincode=getDomElementById("pincode"+pincodeRowId);
			pincode.value="";
			setTimeout(function(){pincode.focus();}, 10);
			clearPickupRow(pincodeRowId);
		} else {
			preparePickupBranchDropDown(ajaxResp,pincodeRowId);
			document.getElementById('branchFlag'+pincodeRowId).value = 'Y';
		}
	}
	hideProcessing();
}

/**
 * To prepare pickup branch drop down
 * 
 * @param list
 * @param rowId
 */
function preparePickupBranchDropDown(list,rowId){
	var content=document.getElementById('pickupBranch'+rowId);
	if(!isNull(content)){
	content.innerHTML="";
	option=document.createElement("option");
	option.value="";
	option.appendChild(document.createTextNode("---Select---"));
	content.appendChild(option);
	branchCodeArr[rowId] = new Array();
	rowId=parseInt(rowId);
	$.each(list, function(index, value) {
		var option;
		option=document.createElement("option");
		option.value=this.officeId;
		option.appendChild(document.createTextNode(this.officeCode+"-"+this.officeName));
		content.appendChild(option);
		branchCodeArr[rowId][arrIndex] = new Array();
		branchCodeArr[rowId][arrIndex][0] = this.officeId;
		branchCodeArr[rowId][arrIndex][1] = this.officeCode;
		arrIndex++;
	});
	arrIndex=0;
	}
}


function preparePickupDelBranchDropDown(obj, ofc,rowId){
	// clearDropDownList('pickupBranch'+rowId);
	 addOptionTODropDown(obj+rowId, ofc.officeCode+' - '+ofc.officeName, ofc.officeId); 
}

/**
 * To get Pickup Branch Code
 * 
 * @param obj
 */
function getPickupBranchsOnClick(rId){
	
	var objVal = "";
	var url= "";
	pincodeRowId = rId;
	var flagVal=document.getElementById('branchFlag'+rId).value;
	if(flagVal == 'N'){
		objVal = getDomElementById("pincode"+rId).value;
		document.getElementById('branchPincode'+rId).value = objVal;
		url="./rateContract.do?submitName=getPickupBranchsByPincode&pincode="+objVal;
		ajaxCallWithoutForm(url, callBackPickupBranchs);
		showProcessing();
	}
	
}

function getPickupBranchCode(rId){
	 var objValue = document.getElementById('pickupBranch'+rId).value;
	for(var i=0; i<branchCodeArr[rId].length; i++){
		if(branchCodeArr[rId][i][0]==objValue){
			jQuery("#pickupBranchCode"+rId).val(branchCodeArr[rId][i][1]);
		}
	}
}

/**
 * To validate pincode
 * 
 * @param obj
 * @returns {Boolean}
 */
function validatePincode(obj,pinFlag){
	if(obj.value.length==6){
		if(pinFlag){
			url="./rateContract.do?submitName=getValidatePincode&pincode="+obj.value;
			ajaxCallWithoutForm(url, callBackPincode);
		}
		return true;
	}else{
		alert("Pincode should be of 6 digits.");
		setTimeout(function(){obj.focus();}, 10);
	}
	
	
	return false;
}

//call back function for getPickupBranchs
function callBackPincode(ajaxResp){
	
	if (!isNull(ajaxResp)) {
		//var resp = jsonJqueryParser(ajaxResp);
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			var pincode=getDomElementById("pincode"+pincodeRowId);
			pincode.value="";
			setTimeout(function(){pincode.focus();}, 10);
			clearPickupRow(pincodeRowId);
		} else {
			var data = ajaxResp;
			getDomElementById("locCity"+pincodeRowId).value = data.cityId;
			getDomElementById("locPincodeId"+pincodeRowId).value = data.pincodeId;			
		}
	}
	hideProcessing();
}

/**
 * To check mandatory field(s).
 * 
 * @returns {Boolean}
 */
function checkMandatoryFields(){
	for(var i=1; i<rowCountP; i++){
		if(!isNull(getDomElementById("pincode"+i)) && !validateGridDtls(i)){
			return false;
		}
	}
	return true;
}

/**
 * To validate grid detail(s) by row id.
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateGridDtls(rowId){
	var pincode=getDomElementById("pincode"+rowId);
	var pickupBranch=getDomElementById("pickupBranch"+rowId);
	var locName=getDomElementById("locationName"+rowId);
	var address1=getDomElementById("address1"+rowId);
	var address2=getDomElementById("address2"+rowId);
	var address3=getDomElementById("address3"+rowId);
	var status = getDomElementById("contractStatus").value;
	var customerCode=getDomElementById("customerCode"+rowId);
	var billingType = getDomElementById("typeOfBilling").value;
	if(isNull(pincode.value)){
		alert("Please provide pincode");
		setTimeout(function(){pincode.focus();}, 10);
		return false;
	}
	if(isNull(pickupBranch.value)){
		alert("Please select pickup branch");
		setTimeout(function(){pickupBranch.focus();}, 10);
		return false;
	}
	if(isNull(locName.value)){
		alert("Please provide Location Name");
		setTimeout(function(){locName.focus();}, 10);
		return false;
	}
	if(isNull(address1.value)){
		alert("Please provide address 1");
		setTimeout(function(){address1.focus();}, 10);
		return false;
	}
	if(isNull(address2.value)){
		alert("Please provide address 2");
		setTimeout(function(){address2.focus();}, 10);
		return false;
	}
	if(isNull(address3.value)){
		alert("Please provide address 3");
		setTimeout(function(){address3.focus();}, 10);
		return false;
	}
	if((status == "A" || status == "B") && (billingType == "DBCP") && isNull(customerCode.value)){
		alert("Please provide customer code");
		setTimeout(function(){customerCode.focus();}, 10);
		return false;
	}
	if(billingType == "DBCP"){
		var customerCode = getDomElementById("customerCode" + rowId).value;
		if(isNull(customerCode)){
			alert("Please enter customer code at row number : " + rowId);
			return false;
		}
		if(customerCode.length != 10){
			alert("Customer Code [" + customerCode + "] should be 10 characters long");
			return false;
		}
	}
	return true;
}

/**
 * To save rate contract pickup details
 */
function saveRateContractPickupDtls(){
	if(checkMandatoryFields() && validateBillPayLocation()){
		buttonDisableById("savePickupBtn");
		setBillPayLocForSave();
		jQuery("#pickDlvIdsArr").val(delPickDlvIds);
		var url="./rateContract.do?submitName=saveRateContractPickupDlvDtls";
		jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
		ajaxCall1(url, RATE_CON_FORM, callBackSavePickupDtls);//RATE_CON_FORM - Global var.
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}
//call back function for saveRateContractPickupDtls
function callBackSavePickupDtls(ajaxResp) {
	var errorFlag = "ERROR";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[errorFlag];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			delPickDlvIds = "";
			//alert((new Date()).getSeconds());
			var data = responseText;
			assignPickUpRowValues(data);
			validateAllEnableOrDisabled();
			jQuery.unblockUI();
			//alert((new Date()).getSeconds());
			alert("Data Saved Successfully");
			
			if(getDomElementById("rateQuotationType").value == "E"){
				enableTabs(6);
			}else{
				enableTabs(7);
			}
		}

	}else{
		jQuery.unblockUI();
	}
	buttonEnableById("savePickupBtn");
}

function assignPickUpRowValues(data){
	var conType = getRateContractType();
	if(conType==NORM){
		deletePickupDelTableRows(NORM_PICKUP_TABLE);
		rowCountP = 1;
	} else if(conType==ECOM) {
		deletePickupDelTableRows("eCommercePickupDtlsTable");
		rowCountP = 1;
	}
	
	var type=getTypeOfBilling();
	//alert((new Date()).getSeconds());
	/*deleteTableRow(NORM_PICKUP_TABLE);
	rowCountP = 1;
*/	if(!isNull(data.conPayBillLocDO)){
		contractListLen = data.conPayBillLocDO.length;
		if(contractListLen > 0){
			var conBillLocList = data.conPayBillLocDO;
			for(var i=0; i<contractListLen; i++) {
				if(conType==NORM){
					additioanlRowToPickupDtls(NORM_PICKUP_TABLE);
				}else if(conType==ECOM){
					additioanlRowToPickupDtls("eCommercePickupDtlsTable");
				}
			}
			if(conType==NORM){
				$('#'+NORM_PICKUP_TABLE).dataTable().fnDraw();
			}else if(conType==ECOM){
				$('#eCommercePickupDtlsTable').dataTable().fnDraw();
			}
			
			
		for(var i=0; i<contractListLen; i++) {
			//addRowToPickupDtls(NORM_PICKUP_TABLE);
			var conPayBillLoc = conBillLocList[i];
			var loc = conPayBillLoc.pickupDeliveryLocation;
			var con = loc.pickupDlvContract;
			var addr = loc.address;
		
			jQuery("#pickupDlvType").val(con.contractType);		
				var rId=i+1;
				jQuery("#pincode"+rId).val(addr.pincodeDO.pincode);
				
				preparePickupDelBranchDropDown("pickupBranch", con.office, rId);
				jQuery("#pickupBranch"+rId).val(con.office.officeId);
				//getPickupBranchCode(getDomElementById("pickupBranch"+rId));
				jQuery("#pickupBranchCode"+rId).val(con.office.officeCode);
				
				
				jQuery("#locationName"+rId).val(addr.name);
				jQuery("#address1"+rId).val(addr.address1);
				jQuery("#address2"+rId).val(addr.address2);
				jQuery("#address3"+rId).val(addr.address3);
				jQuery("#contactPerson"+rId).val(addr.contactPerson);
				jQuery("#designation"+rId).val(addr.designation);
				jQuery("#mobile"+rId).val(addr.mobile);
				jQuery("#email"+rId).val(addr.email);
				if(conPayBillLoc.locationOperationType==(BILL+PAY)){//BP
					getDomElementById("billing"+rId).checked=true;
					getDomElementById("payment"+rId).checked=true;
				} else {
					if(conPayBillLoc.locationOperationType==BILL){//B
						getDomElementById("billing"+rId).checked=true;
					}
					if(conPayBillLoc.locationOperationType==PAY){//P
						getDomElementById("payment"+rId).checked=true;
					}
				}
				jQuery("#customerCode"+rId).val(conPayBillLoc.shippedToCode);
				jQuery("#pickupBranchCode"+rId).val(con.office.officeCode);//hidden
				jQuery("#conPayBillLocId"+rId).val(conPayBillLoc.contractPaymentBillingLocationId);//hidden
				jQuery("#pickupDlvLocId"+rId).val(loc.pickupDlvLocId);//hidden
				jQuery("#contractId"+rId).val(con.contractId);//hidden
				jQuery("#addressId"+rId).val(addr.addressId);//hidden
				jQuery("#locCode"+rId).val(loc.pickupDlvLocCode);//hidden
				jQuery("#locCity"+rId).val(addr.pincodeDO.cityId);//hidden
				jQuery("#locPincodeId"+rId).val(addr.pincodeDO.pincodeId);//hidden
				
				if(type==DD) {
					buttonDisabled('addPickupBtn','btnintformbigdis');
				} else if(type==CC) {
					//if(isBillPayLocChkd()){
						//var rId=rowCountP-1;
						//getDomElementById("billing"+rId).disabled=true;
						//getDomElementById("payment"+rId).disabled=true;
					//}
				} else if(type==DC) {
					if(!isNull(getDomElementById("customerCode"+rId))){
						var rowElem = getDomElementById("customerCode"+rId);
						rowElem.readOnly = false;
						rowElem.setAttribute("tabindex","0");
					}
				}
				setBillPayLocation(rId);
				
			} //End of ELSE-IF
		}
		}//End of FOR
}
/**
 * To Block Or Unblock Customer
 * 
 * @param action
 */
function blockOrUnblockCustomer(action){
	var custStatus = "";
	if(action=='block'){
		custStatus = INACTIVE;
	} else if(action=='unblock') {
		custStatus = ACTIVE;
	}
	if(!isNull(custStatus)){
		var custId = getDomElementById("customerNewId").value;
		var rateConId = getDomElementById("rateContractId").value;
		var url = './rateContract.do?submitName=blockOrUnblockCustomer'
			+'&customerId='+custId
			+'&rateContractId='+rateConId
			+'&status='+custStatus;
		ajaxCallWithoutForm(url, callBackBlockUnblockCustomer);
	}
}
//call back function for blockOrUnblockCustomer
function callBackBlockUnblockCustomer(ajaxResp){
	
	if (!isNull(ajaxResp)) {
		var data=ajaxResp;
		var error = data[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
		} else {
			if(data.customerStatus==ACTIVE) {
				alert(MSG_CUST_UNBLOCK);
				jQuery("#customerStatus").val(ACTIVE);
				jQuery("#contractStatus").val(data.contractStatus);//A
			} else if(data.customerStatus==INACTIVE) {
				alert(MSG_CUST_BLOCK);
				jQuery("#customerStatus").val(INACTIVE);
				jQuery("#contractStatus").val(data.contractStatus);//B
			}
			var pickupDlvType=getDomElementById("pickupDlvType").value;
			if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
				validateBlockUnblockCust();
			} else if(pickupDlvType==DLV_CONTRACT_TYPE) {
				validateBlockUnblockCustDlv();
			}
		}
	}
	
}

/**
 * To get type of billing
 * 
 * @returns typeOfBilling
 */
function getTypeOfBilling(){
	return getDomElementById("typeOfBilling").value;
}

/**
 * To validate tab for billing type DBDP
 */
function validateForDD(){
	$('#tabs-6 #demo .title .title2').text(labelDD);
	var tabId=getTableIdByContractType(getRateContractType());
	var table=getDomElementById(tabId);
	var tabLen=table.rows.length;
	if(tabLen==2){
		if(isNull(getDomElementById("pincode"+(tabLen-1))) 
				&& isNull(getDomElementById("pincodeDlv"+(tabLen-1))))
			tabLen--;
	}
	if(tabLen==1){
		buttonEnabled('addPickupBtn','btnintform');
		jQuery('#addPickupBtn').removeClass('btnintformbigdis');
		if(!isNull(getDomElementById("addDlvBtn"))){
			buttonEnabled('addDlvBtn','btnintform');
			jQuery('#addDlvBtn').removeClass('btnintformbigdis');
		}
	}
	buttonDisabled('addPickupBtn','btnintformbigdis');
}

/**
 * To validate tab for billing type CBCP
 */
function validateForCC(){
	$('#tabs-6 #demo .title .title2').text(labelCC);
	buttonEnabled('addPickupBtn','btnintform');
	jQuery('#addPickupBtn').removeClass('btnintformbigdis');
	//buttonDisabled('addPickupBtn','btnintformbigdis');
	if(!isNull(getDomElementById("addDlvBtn"))){
		jQuery('#addDlvBtn').removeClass('btnintformbigdis');
		buttonEnabled('addDlvBtn','btnintform');
	}
}

/**
 * To validate tab for billing type DBCP
 */
function validateForDC(){
	$('#tabs-6 #demo .title .title2').text(labelDC);
	buttonEnabled('addPickupBtn','btnintform');
	jQuery('#addPickupBtn').removeClass('btnintformbigdis');
	if(!isNull(getDomElementById("addDlvBtn"))){
		buttonEnabled('addDlvBtn','btnintform');
		jQuery('#addDlvBtn').removeClass('btnintformbigdis');
	}
}

/**
 * To validate billing and payment location 
 * 
 * @returns {Boolean}
 */
function validateBillPayLocation(){
	var type=getTypeOfBilling();
	if(type==DD){
		for(var i=1; i<rowCountP; i++){
			if(!isNull(getDomElementById("billing"+i))
					&& !isNull(getDomElementById("payment"+i))){
				if(getDomElementById("billing"+i).checked==false){
					alert("Please select billing location");
					setTimeout(function(){getDomElementById("billing"+i).focus();}, 10);
					return false;
				} else if(getDomElementById("payment"+i).checked==false){
					alert("Please select payment location");
					setTimeout(function(){getDomElementById("payment"+i).focus();}, 10);
					return false;
				}
			}
		}
	} else if(type==CC){
		chkElm = false;
		for(var i=1; i<rowCountP; i++){
			/*if(!isNull(getDomElementById("billing"+i)) && getDomElementById("billing"+i).disabled==false
					&& !isNull(getDomElementById("payment"+i)) && getDomElementById("payment"+i).disabled==false){*/
			if(!isNull(getDomElementById("billing"+i))
					&& !isNull(getDomElementById("payment"+i))){
				if(getDomElementById("billing"+i).checked==true 
						&& getDomElementById("payment"+i).checked==true){
					chkElm = true;
					break;
				}
			}
		}
		if(!chkElm){
			alert("Please select aleast one billing & payment location");
			return false;
		}
	} else if(type==DC){
		var flag=false;
		for(var i=1; i<rowCountP; i++){
			if(!isNull(getDomElementById("billing"+i))
					&& !isNull(getDomElementById("payment"+i))){
				if(getDomElementById("payment"+i).checked==true && flag){
					alert("Only one location should be select as payment location.");
					setTimeout(function(){getDomElementById("payment"+i).focus();}, 10);
					return false;
				}
				if(getDomElementById("payment"+i).checked==true){
					flag=true;
				}
			}
		}
		if(!flag){
			alert("Please select atleast one payment location.");
			return false;
		}
	}
	return true;
}

/**
 * To set Billing or Payment location
 */
function setBillPayLocation(rId){
	var type=getTypeOfBilling();
	if(type==DD){
		if(!isNull(getDomElementById("billing"+rId))){
			getDomElementById("billing"+rId).checked=true;
			getDomElementById("billing"+rId).disabled=true;
		}
		if(!isNull(getDomElementById("payment"+rId))){
			getDomElementById("payment"+rId).checked=true;
			getDomElementById("payment"+rId).disabled=true;
		}
	} else if(type==DC){
		if(!isNull(getDomElementById("billing"+rId))){
			getDomElementById("billing"+rId).checked=true;
			getDomElementById("billing"+rId).disabled=true;
		}
	}
}

/**
 * To select billing or payment location as per type of billing  
 * 
 * @param chkObj
 * @param rowId
 */
function selectBillPayLocation(chkObj, rowId) {
	var type=getTypeOfBilling();
	if(type==CC){
		if(chkObj.checked==true){
			for(var i=1; i<rowCountP; i++) {
				if(!isNull(getDomElementById("billing"+i)) 
						&& !isNull(getDomElementById("payment"+i))){
					if(i==rowId) {
						getDomElementById("billing"+i).checked=true;
						getDomElementById("payment"+i).checked=true;
					} else {
						getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					}
				}				
			} 
		} else {
			for(var i=1; i<rowCountP; i++) {
				if(!isNull(getDomElementById("billing"+i)) 
						&& !isNull(getDomElementById("payment"+i))){
					if(i==rowId) {
						getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					} else {
						getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					}
				}				
			}
		}
	}else if(type==DC){
		if(chkObj.checked==true){
			for(var i=1; i<rowCountP; i++) {
				if(!isNull(getDomElementById("billing"+i)) 
						&& !isNull(getDomElementById("payment"+i))){
					if(i==rowId) {
						getDomElementById("billing"+i).checked=true;
						//getDomElementById("payment"+i).checked=true;
					} else {
						//getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					}
				}				
			} 
		} else {
			for(var i=1; i<rowCountP; i++) {
				if(!isNull(getDomElementById("payment"+i))){
					if(i==rowId) {
						//getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					} else {
						//getDomElementById("billing"+i).checked=false;
						getDomElementById("payment"+i).checked=false;
					}
				}				
			}
		}
	}
}

/**
 * To check whether any bill pay location checked
 * 
 * @returns {Boolean}
 */
function isBillPayLocChkd(){
	for(var i=1; i<rowCountP; i++) {
		if(!isNull(getDomElementById("billing"+i)) 
				&& !isNull(getDomElementById("payment"+i))){
			if(getDomElementById("billing"+i).checked==true 
					&& getDomElementById("payment"+i).checked==true){
				return true;
			}
		}				
	}
	return false;
}

/**
 * To validate row before delete (for bill & pay location)
 */
function validateRowForDel(rowId){
	var type=getTypeOfBilling();
	if(type==CC){
		/*for(var i=1; i<rowCountP; i++){
			if(!isNull(getDomElementById("chk"+i)) 
					&& getDomElementById("chk"+i).checked==true){*/
				if(getDomElementById("billing"+rowId).checked==true 
						&& getDomElementById("payment"+rowId).checked==true){
					getDomElementById("billing"+rowId).checked=false;
					//getDomElementById("payment"+rowId).checked=false;
					selectBillPayLocation(getDomElementById("billing"+rowId), rowId);
				}
		/*	}
		}*/
	}
}

/**
 * To clear pickup row
 * 
 * @param rowId
 */
function clearPickupRow(rowId){
	
	var cpblId =  getDomElementById("conPayBillLocId"+rowId).value;
	if(!isNull(cpblId)){
	
		if(!isNull(delPickDlvIds)){
			delPickDlvIds = delPickDlvIds + ",";
		}
		delPickDlvIds = delPickDlvIds + cpblId;
	}
	getDomElementById("chk"+rowId).checked=false;
	jQuery("#pincode"+rowId).val("");
	jQuery("#pickupBranch"+rowId).val("");
	jQuery("#locationName"+rowId).val("");
	jQuery("#address1"+rowId).val("");
	jQuery("#address2"+rowId).val("");
	jQuery("#address3"+rowId).val("");
	jQuery("#contactPerson"+rowId).val("");
	jQuery("#designation"+rowId).val("");
	jQuery("#mobile"+rowId).val("");
	jQuery("#email"+rowId).val("");   
	//getDomElementById("billing"+rowId).checked=false;
	//getDomElementById("payment"+rowId).checked=false;
	jQuery("#isBillLoc"+rowId).val("");
	jQuery("#isPayLoc"+rowId).val("");
	jQuery("#customerCode"+rowId).val("");
	jQuery("#pickupBranchCode"+rowId).val("");//hidden
	jQuery("#conPayBillLocId"+rowId).val("");//hidden
	jQuery("#pickupDlvLocId"+rowId).val("");//hidden
	jQuery("#contractId"+rowId).val("");//hidden
	jQuery("#addressId"+rowId).val("");//hidden
	jQuery("#locCode"+rowId).val("");//hidden
}

/**
 * To set Billing and Payment Location during save
 */
function setBillPayLocForSave(){
	for(var i=1; i<rowCountP; i++) {
		if(!isNull(getDomElementById("billing"+i))
				&& !isNull(getDomElementById("payment"+i))){
			getDomElementById("billing"+i).disabled=false;
			getDomElementById("payment"+i).disabled=false;
			if(getDomElementById("billing"+i).checked==true){
				jQuery("#isBillLoc"+i).val("Y");
			} else {
				jQuery("#isBillLoc"+i).val("N");
			}
			if(getDomElementById("payment"+i).checked==true){
				jQuery("#isPayLoc"+i).val("Y");
			} else {
				jQuery("#isPayLoc"+i).val("N");
			}
		}				
	}
}

/**
 * To validate Block and Unblock Button after successfully changed customer status.
 */
function validateBlockUnblockCust(){
	var status=getDomElementById("customerStatus").value;
	if(status==ACTIVE){
		//pickup
		buttonEnabled('blockPickupBtn','btnintform');
		jQuery('#blockPickupBtn').removeClass('btnintformbigdis');
		buttonDisabled('unblockPickupBtn','btnintformbigdis');
		//dlv
		buttonEnabled('blockDlvBtn','btnintform');
		jQuery('#blockDlvBtn').removeClass('btnintformbigdis');
		buttonDisabled('unblockDlvBtn','btnintformbigdis');
	} else if(status==INACTIVE) {
		//pickup
		buttonEnabled('unblockPickupBtn','btnintform');
		jQuery('#unblockPickupBtn').removeClass('btnintformbigdis');
		buttonDisabled('blockPickupBtn','btnintformbigdis');
		//dlv
		buttonEnabled('unblockDlvBtn','btnintform');
		jQuery('#unblockDlvBtn').removeClass('btnintformbigdis');
		buttonDisabled('blockDlvBtn','btnintformbigdis');
	}
}

/**
 * To get rate contract type i.e. N- Normal, E-ECommerce
 * 
 * @returns rateContractType
 */
function getRateContractType(){
	return getDomElementById("rateContractType").value;
}

/**
 * To get table grid Id by contract type
 * 
 * @param conType
 * @returns {String}
 */
function getTableIdByContractType(conType){
	var pickupDlvType=getDomElementById("pickupDlvType").value;
	if(conType==NORM){
		if(pickupDlvType==PICKUP_CONTRACT_TYPE){
			return NORM_PICKUP_TABLE;
		} else if(pickupDlvType==DLV_CONTRACT_TYPE) {
			return NORM_DLV_TABLE;
		}
	} else if(conType==ECOM) {
		if(pickupDlvType==PICKUP_CONTRACT_TYPE){
			return ECOM_PICKUP_TABLE;
		} else if(pickupDlvType==DLV_CONTRACT_TYPE) {
			return ECOM_DLV_TABLE; 
		}
	}
}

/**
 * To search pickup details
 */
function searchPickupDlvDtls(){
	delPickDlvIds = "";
	var rateConId=getDomElementById("rateContractId").value;
	if(!isNull(rateConId)){
		var url='./rateContract.do?submitName=searchRateContractPickupDlvDtls'
			+ '&rateContractId='+rateConId;
		ajaxCallWithoutForm(url, callBackSearchPickupDlvDtls);
	} else {
		validatePickupDlvTabs();
	}
}
//call back function for : searchPickupDlvDtls
function callBackSearchPickupDlvDtls(ajaxResp){
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		}
		} else {
			delPickDlvIds = "";
			var data=ajaxResp;
		for(var i=0; i<data.length; i++){
			var tabId = getTableIdByContractType(getRateContractType());
			additioanlRowToPickupDtls(tabId);
			var rId=rowCountP-1;
			var loc = data[i].pickupDeliveryLocationTO;
			var con = loc.pickupDlvContract;
			var addr = loc.pickupDlvAddress;
			jQuery("#isExistPkUpOrDlvDetails").val(true);
			if(con.contractType==PICKUP_CONTRACT_TYPE){
				jQuery("#pincode"+rId).val(addr.pincode.pincode);
				
				preparePickupBranchDropDown(data[i].pickupDlvBranchList, rId);
				jQuery("#pickupBranch"+rId).val(con.office.officeId);
				getPickupBranchCode(getDomElementById("pickupBranch"+rId));
				
				jQuery("#locationName"+rId).val(addr.name);
				jQuery("#address1"+rId).val(addr.address1);
				jQuery("#address2"+rId).val(addr.address2);
				jQuery("#address3"+rId).val(addr.address3);
				jQuery("#contactPerson"+rId).val(addr.contactPerson);
				jQuery("#designation"+rId).val(addr.designation);
				jQuery("#mobile"+rId).val(addr.mobile);
				jQuery("#email"+rId).val(addr.email);
				if(data[i].locationOperationType==(BILL+PAY)){//BP
					getDomElementById("billing"+rId).checked=true;
					getDomElementById("payment"+rId).checked=true;
				} else {
					if(data[i].locationOperationType==BILL){//B
						getDomElementById("billing"+rId).checked=true;
					}
					if(data[i].locationOperationType==PAY){//P
						getDomElementById("payment"+rId).checked=true;
					}
				}
				jQuery("#customerCode"+rId).val(data[i].shippedToCode);
				jQuery("#pickupBranchCode"+rId).val(con.office.officeCode);//hidden
				jQuery("#conPayBillLocId"+rId).val(data[i].contractPaymentBillingLocationId);//hidden
				jQuery("#pickupDlvLocId"+rId).val(loc.pickupDlvLocId);//hidden
				jQuery("#contractId"+rId).val(con.contractId);//hidden
				jQuery("#addressId"+rId).val(addr.addressId);//hidden
				jQuery("#locCode"+rId).val(loc.pickupDlvLocCode);//hidden
			}
		}// END OF FOR LOOP
	} 
		}
	else {
		delPickDlvIds = "";
		validatePickupDlvTabs();
	}
}

/**
 * To validate Pickup/Delivery Tab(s).
 */
function validatePickupDlvTabs(){
	var type=getTypeOfBilling();
	if(type==DD) {
		//Decentralized Billing Decentralized Payment
		validateForDD();
	} else if(type==CC) {
		//Centralized Billing Centralized Payment
		validateForCC();
	} else if(type==DC) {
		//Decentralized Billing Centralized Payment
		validateForDC();
	}
}

/**
 * To validate Contract status - Common function
 */
function validateContractStatus(){
	var conStatus = jQuery("#contractStatus").val();
	var custStatus = jQuery("#customerStatus").val();
	if(conStatus==ACTIVE || conStatus==INACTIVE || conStatus==CON_STATUS_BLOCKED){//A-Active, B-Blocked
		if(custStatus==ACTIVE){//A
			buttonEnabled('blockPickupBtn','btnintform');
			buttonEnabled('blockDlvBtn','btnintform');
			jQuery('#blockPickupBtn').removeClass('btnintformbigdis');
			jQuery('#blockDlvBtn').removeClass('btnintformbigdis');
			//unblock button to be disabled
			buttonDisabled('unblockPickupBtn','btnintformbigdis');
			buttonDisabled('unblockDlvBtn','btnintformbigdis');
			buttonDisabled('cancelPickupBtn','btnintformbigdis');
			buttonDisabled('cancelDlvBtn','btnintformbigdis');
		} else if(custStatus==INACTIVE){//I
			buttonDisabled('unblockPickupBtn','btnintform');
			buttonDisabled('unblockDlvBtn','btnintform');
			jQuery('#unblockPickupBtn').removeClass('btnintformbigdis');	
			jQuery('#unblockDlvBtn').removeClass('btnintformbigdis');
			//unblock button to be disabled
			buttonDisabled('blockPickupBtn','btnintformbigdis');
			buttonDisabled('blockDlvBtn','btnintformbigdis');
			buttonDisabled('cancelPickupBtn','btnintformbigdis');
			buttonDisabled('cancelDlvBtn','btnintformbigdis');
		} else if(conStatus==CON_STATUS_BLOCKED){
			buttonEnabled('unblockPickupBtn','btnintform');
			buttonEnabled('unblockDlvBtn','btnintform');
			jQuery('#unblockPickupBtn').removeClass('btnintformbigdis');	
			jQuery('#unblockDlvBtn').removeClass('btnintformbigdis');
			//unblock button to be disabled
			buttonDisabled('blockPickupBtn','btnintformbigdis');
			buttonDisabled('blockDlvBtn','btnintformbigdis');
			buttonDisabled('cancelPickupBtn','btnintformbigdis');
			buttonDisabled('cancelDlvBtn','btnintformbigdis');
		}
	} else {//C-Customer, S-Submitted, I-Inactive
		buttonDisabled('unblockPickupBtn','btnintformbigdis');
		buttonDisabled('unblockDlvBtn','btnintformbigdis');
		buttonDisabled('blockPickupBtn','btnintformbigdis');
		buttonDisabled('blockDlvBtn','btnintformbigdis');
	}
}

/**
 * To Edit Pickup Details
 */
function editPickupDtls() {
	var tabId=getTableIdByPickupDlvType(jQuery("#pickupDlvType").val());
	$("#"+tabId).find("input").attr("disabled", false);
	$("#"+tabId).find("select").attr("disabled", false);
	$("#checkAll").attr("disabled", false);
	callReqPickupValidation();
}

/**
 * To call other required pickup validation(s)-function(s)
 */
function callReqPickupValidation(){
	validateContractStatus();
	validatePickupDlvTabs();
	validatePayBillLoc();
	validateButtonForEdit();
}

/**
 * To Validate Payment/Billing Location
 */
function validatePayBillLoc(){
	var type=getTypeOfBilling();
	//var isBillPayLocChked = false;
	for(var i=1;i<rowCountP;i++){
		if(!isNull(getDomElementById("billing"+i)) 
				&& !isNull(getDomElementById("payment"+i))){
			if(type==DD){
				getDomElementById("billing"+i).checked=true;
				getDomElementById("payment"+i).checked=true;
			} else if(type==DC){
				getDomElementById("billing"+i).checked=true;
				//getDomElementById("billing"+i).disabled=true;
			} else if(type==CC) {
				/*if(isBillPayLocChked || isBillPayLocChkd()){
					if(getDomElementById("billing"+i).checked==false 
							&& getDomElementById("payment"+i).checked==false){
						getDomElementById("billing"+i).disabled=true;
						getDomElementById("payment"+i).disabled=true;
					}
					isBillPayLocChked=true;
				}*/
			}
		}
	}
}

/**
 * To validate buttons for edit - Common function
 */
function validateButtonForEdit(){
	var type=getTypeOfBilling();
	if(type==DD) {
		buttonDisabled('addPickupBtn','btnintformbigdis');
		buttonDisabled('addDlvBtn','btnintformbigdis');
	} else if(type==CC || type==DC) {
		buttonEnabled('addPickupBtn','btnintform');
		jQuery('#addPickupBtn').removeClass('btnintformbigdis');
		buttonEnabled('addDlvBtn','btnintform');
		jQuery('#addDlvBtn').removeClass('btnintformbigdis');
	}
	buttonEnabled('savePickupBtn','btnintform');
	jQuery('#savePickupBtn').removeClass('btnintformbigdis');
	buttonEnabled('saveDlvBtn','btnintform');
	jQuery('#saveDlvBtn').removeClass('btnintformbigdis');
	buttonEnabled('deletePickupBtn','btnintform');
	jQuery('#deletePickupBtn').removeClass('btnintformbigdis');
	buttonEnabled('deleteDlvBtn','btnintform');
	jQuery('#deleteDlvBtn').removeClass('btnintformbigdis');
}

function cancelPickupDetails(){
	rateBillingDtlsStartup();
}

function getPickDlvIdValues(rowId){
	conType = getRateContractType();
	if(conType==NORM){
		pickupDlvType = getDomElementById("pickupDlvType").value;
		if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
			if(!isNull(getDomElementById("conPayBillLocId"+rowId))){
				pickDlvId=getDomElementById("conPayBillLocId"+rowId).value;
			if(!isNull(pickDlvId)){
				if(!isNull(delPickDlvIds)){
					delPickDlvIds = delPickDlvIds + ",";
				}
				delPickDlvIds = delPickDlvIds + pickDlvId;
				}
			}
		}else if(pickupDlvType==DLV_CONTRACT_TYPE) {
			
			if(!isNull(getDomElementById("conPayBillLocIdDlv"+rowId))){
				pickDlvId=getDomElementById("conPayBillLocIdDlv"+rowId).value;
			if(!isNull(pickDlvId)){
				if(!isNull(delPickDlvIds)){
					delPickDlvIds = delPickDlvIds + ",";
				}
				delPickDlvIds = delPickDlvIds + pickDlvId;
				}
			}
		}
	}else if(conType==ECOM) {
		if(!isNull(getDomElementById("conPayBillLocId"+rowId))){
			pickDlvId=getDomElementById("conPayBillLocId"+rowId).value;
		if(!isNull(pickDlvId)){
			if(!isNull(delPickDlvIds)){
				delPickDlvIds = delPickDlvIds + ",";
			}
			delPickDlvIds = delPickDlvIds + pickDlvId;
			}
		}
	}
}

function validateCustomerCode(obj, rowId){
	var billingType = getDomElementById("typeOfBilling").value;
	// var customerCode = getDomElementById("customerCode" + rowId).value;
	if(billingType == "DBCP"){
		var customerCode = obj.value;
		if(customerCode.length != 10){
			alert('Customer code should always be 10 characters long');
			return false;
		}
		else{
			return true;
		}
	}
}