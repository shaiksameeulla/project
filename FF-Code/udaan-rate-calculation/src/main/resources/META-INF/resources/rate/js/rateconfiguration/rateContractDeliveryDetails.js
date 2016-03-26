var rowCountD = 1;
var branchCodeArr = new Array();
var arrIndex = 0;
var NORM_DLV_TABLE="deliveryDetailsTable";
var ECOM_DLV_TABLE="eCommerceDeliveryDtlsTable";
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
/**
 * To add row(s) to Delivery Details Table. 
 */
function addRowsDlv(tableId) {
	if(tableId==NORM_DLV_TABLE){
		$('#'+NORM_DLV_TABLE).dataTable().fnAddData([
		    '<input type="checkbox" name="chkBoxName" id="chkd'+ rowCountD +'" class="checkbox" tabindex="-1" />',
			'<input type="text" name="to.pincode" id="pincodeDlv'+ rowCountD +'" class="txtbox width70" maxlength="6" onkeypress="return validatePinKey(event);" onblur="getDlvBranchs('+rowCountD+');" />',
			'<select name="to.pickupBranch" id="pickupBranchDlv'+ rowCountD +'" class="selectBox width100" onclick="getDlvBranchOnClick('+rowCountD+');" onchange="getDlvBranchCode('+rowCountD+');" /><option value="">---Select---</option></select>',
			'<input type="text" name="to.locationName" id="locationNameDlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address1" id="address1Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address2" id="address2Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address3" id="address3Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.contactPerson" id="contactPersonDlv'+ rowCountD +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.designation" id="designationDlv'+ rowCountD +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.mobile" id="mobileDlv'+ rowCountD +'" onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);"  class="txtbox width100" maxlength="10" />',
			'<input type="text" name="to.email" id="emailDlv'+ rowCountD +'" class="txtbox width100" maxlength="75" onblur="validateEmailAddress(this)"/>',
			'<input type="checkbox" name="to.billing" id="billingDlv'+ rowCountD +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocDlv(this,'+rowCountD+');" />\
			 <input type="hidden" name="to.isBillLoc" id="isBillLocDlv'+rowCountD+'" />',
			'<input type="checkbox" name="to.payment" id="paymentDlv'+ rowCountD +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocDlv(this,'+rowCountD+');" />\
			 <input type="hidden" name="to.isPayLoc" id="isPayLocDlv'+rowCountD+'" />',
			'<input type="text" name="to.customerCode" id="customerCodeDlv'+ rowCountD +'" class="txtbox width100" maxlength="10" tabindex="-1" readonly="true" onkeypress="return onlyNumeric(event);"/>\
			 <input type="hidden" name="to.pickupBranchCode" id="pickupBranchCodeDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.contractPaymentBillingLocationId" id="conPayBillLocIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.pickupDlvLocId" id="pickupDlvLocIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.contractId" id="contractIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.addressId" id="addressIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="branchFlagDlv" id="branchFlagDlv'+rowCountD+'" value = "N" />\
			 <input type="hidden" name="branchPincodeDlv" id="branchPincodeDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locCity" id="locCityDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locPincodeId" id="locPincodeIdDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locCode" id="locCodeDlv'+rowCountD+'" />'
		], false);
	} else if(tableId==ECOM_DLV_TABLE) {
		$('#'+ECOM_DLV_TABLE).dataTable().fnAddData([
			'<input type="checkbox" name="chkBoxName" id="chkd'+ rowCountD +'" class="checkbox" tabindex="-1" />',
			'<input type="text" name="to.pincode" id="pincodeDlv'+ rowCountD +'" class="txtbox width70" maxlength="6" onkeypress="return validatePinKey(event);" onblur="getDlvBranchs('+rowCountD+');" />',
			'<select name="to.pickupBranch" id="pickupBranchDlv'+ rowCountD +'" class="selectBox width100" onclick="getDlvBranchOnClick('+rowCountD+');" onchange="getDlvBranchCode('+rowCountD+');" /><option value="">---Select---</option></select>',
			'<input type="text" name="to.locationName" id="locationNameDlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address1" id="address1Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address2" id="address2Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.address3" id="address3Dlv'+ rowCountD +'" class="txtbox width100" maxlength="100" />',
			'<input type="text" name="to.contactPerson" id="contactPersonDlv'+ rowCountD +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.designation" id="designationDlv'+ rowCountD +'" class="txtbox width100" maxlength="30" />',
			'<input type="text" name="to.mobile" id="mobileDlv'+ rowCountD +'" class="txtbox width100" maxlength="10" />',
			'<input type="text" name="to.email" id="emailDlv'+ rowCountD +'" class="txtbox width100" maxlength="75" />',
			'<input type="checkbox" name="to.billing" id="billingDlv'+ rowCountD +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocDlv(this,'+rowCountD+');" />\
			 <input type="hidden" name="to.isBillLoc" id="isBillLocDlv'+rowCountD+'" />',
			'<input type="checkbox" name="to.payment" id="paymentDlv'+ rowCountD +'" class="checkbox" tabindex="-1" onchange="selectBillPayLocDlv(this,'+rowCountD+');" />\
			 <input type="hidden" name="to.isPayLoc" id="isPayLocDlv'+rowCountD+'" />',
			'<input type="text" name="to.customerCode" id="customerCodeDlv'+ rowCountD +'" class="txtbox width100" maxlength="10" tabindex="-1" readonly="true" />\
			 <input type="hidden" name="to.pickupBranchCode" id="pickupBranchCodeDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.contractPaymentBillingLocationId" id="conPayBillLocIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.pickupDlvLocId" id="pickupDlvLocIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.contractId" id="contractIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="to.addressId" id="addressIdDlv'+ rowCountD +'" />\
			 <input type="hidden" name="branchFlagDlv" id="branchFlagDlv'+rowCountD+'" value = "N" />\
			 <input type="hidden" name="branchPincodeDlv" id="branchPincodeDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locCity" id="locCityDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locPincodeId" id="locPincodeIdDlv'+rowCountD+'" />\
			 <input type="hidden" name="to.locCode" id="locCodeDlv'+rowCountD+'" />'
		], false);
	}
	rowCountD++;
}

/**
 * On load of Rate Contract - Normal.
 */
function rateDeliveryDtlsStartup(){
	jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
	validateContractStatus();
	searchDlvDtls();
	//validatePickupDlvTabs();
	//addRowsDlv(ECOM_DLV_TABLE);
}

/**
 * On load of Rate Contract - ECommerce.
 */
function rateEcommerceDeliveryDtlsStartup(){
	jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
	validateContractStatus();
	searchDlvDtls();
	//validatePickupDlvTabs();
	//addRowsDlv(NORM_DLV_TABLE);
}

/**
 * To Add Row(s) To Delivery Details Grid.
 */
function addRowToDlvDtls(tableId){
	addRowsDlv(tableId);
	$('#'+NORM_DLV_TABLE).dataTable().fnDraw();
	var type=getTypeOfBilling();
	var rId=rowCountD-1;
	if(type==DD) {
		buttonDisabled('addDlvBtn','btnintformbigdis');
	} else if(type==CC) {
		if(isBillPayLocChkdDlv()){
			//var rId=rowCountD-1;
			//getDomElementById("billingDlv"+rId).disabled=true;
			//getDomElementById("paymentDlv"+rId).disabled=true;
		}
	} else if(type==DC) {
		var rowElem = getDomElementById("customerCodeDlv"+rId);
		rowElem.readOnly = false;
		rowElem.setAttribute("tabindex","0");
	}
	setBillPayLocDlv(rId);
}


function additioanlRowToDlvDtls(tableId){
	addRowsDlv(tableId);
	
}

/**
 * To check all checkbox - Dlv
 * 
 * @param checkBoxObj
 */
function selectAllCheckBoxDlv(checkBoxObj) {
	if(checkBoxObj.checked){//check
		for(var i=1; i<rowCountD; i++){
			if(!isNull(getDomElementById("chkd"+i))){
				getDomElementById("chkd"+i).checked=true;
			}
		}
	} else {//un-check
		for(var i=1; i<rowCountD; i++){
			if(!isNull(getDomElementById("chkd"+i))){
				getDomElementById("chkd"+i).checked=false;
			}
		}
	}
}

/* ######### DELETE ROW START ######### */
/**
 * To Delete Row(s) To Delivery Details Grid.
 * For deleteRows : COMMON CODE WRITTEN IN rateContractPickupDelivery.js 
 */
function deleteRowToDlvDtls(tableId){
	deleteRows(tableId, 'chkd', 'checkAllDlv');
}
/* ######### DELETE ROW END ######### */

var pincodeDlvRowId = "";
/**
 * To get pickup branch(s).
 * @param obj
 */
function getDlvBranchs(rId){
	/*if(!isNull(obj.value) && validatePincode(obj)){
		//pincodeDlvRowId = getRowId(obj, "pincodeDlv");
		pincodeDlvRowId = rId;
		var url='./rateContract.do?submitName=getPickupBranchsByPincode'
			+ '&pincode='+obj.value;
		ajaxCallWithoutForm(url, callBackDlvBranchs);
	} else {
		obj.value="";
		//clearDlvRow(pincodeDlvRowId);
	}*/
	
	pincodeDlvRowId = rId;
	var pinFlag = false;
	var obj = getDomElementById("pincodeDlv"+rId);
	if(!isNull(obj.value) && !isNull(getDomElementById("branchPincodeDlv"+rId).value)){
		if(obj.value != getDomElementById("branchPincodeDlv"+rId).value){
			pinFlag = true;
		}
	}else{
		pinFlag = true;
	}
	if(!isNull(obj.value) && validateDelPincode(obj,pinFlag)){
		if((pinFlag == true)){
		document.getElementById('branchPincodeDlv'+rId).value = obj.value;
		var url='./rateContract.do?submitName=getPickupBranchsByPincode'
			+ '&pincode='+obj.value;
		ajaxCallWithoutForm(url, callBackDlvBranchs);
		}
	} else {
		obj.value="";
		//clearPickupRow(pincodeRowId);
	}
	
}


function validateDelPincode(obj,pinFlag){
	if(obj.value.length==6){
		if(pinFlag){
			url="./rateContract.do?submitName=getValidatePincode&pincode="+obj.value;
			ajaxCallWithoutForm(url, callBackDelPincode);
		}
		return true;
	}else{
		alert("Pincode should be of 6 digits.");
		setTimeout(function(){obj.focus();}, 10);
	}
	
	
	return false;
}

//call back function for getPickupBranchs
function callBackDelPincode(ajaxResp){
	
	if (!isNull(ajaxResp)) {
		//var resp = jsonJqueryParser(ajaxResp);
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			var pincode=getDomElementById("pincode"+pincodeDlvRowId);
			pincode.value="";
			setTimeout(function(){pincode.focus();}, 10);
			clearPickupRow(pincodeDlvRowId);
		} else {
			var data = ajaxResp;
			getDomElementById("locCityDlv"+pincodeDlvRowId).value = data.cityId;
			getDomElementById("locPincodeIdDlv"+pincodeDlvRowId).value = data.pincodeId;	
		}
	}
	hideProcessing();
}
//call back function for getPickupBranchs
function callBackDlvBranchs(ajaxResp){
	/*if(!isNull(ajaxResp)){
		prepareDlvBranchDropDown(ajaxResp,pincodeDlvRowId);
		var content=document.getElementById('pickupBranchDlv'+pincodeDlvRowId);
		content.innerHTML="";
		option=document.createElement("option");
		option.value="";
		option.appendChild(document.createTextNode("---Select---"));
		content.appendChild(option);
		branchCodeArr[pincodeDlvRowId] = new Array();
		pincodeDlvRowId=parseInt(pincodeDlvRowId);
		$.each(ajaxResp, function(index, value) {
			var option;
			option=document.createElement("option");
			option.value=this.officeId;
			option.appendChild(document.createTextNode(this.officeCode+"-"+this.officeName));
			content.appendChild(option);
			branchCodeArr[pincodeDlvRowId][arrIndex] = new Array();
			branchCodeArr[pincodeDlvRowId][arrIndex][0] = this.officeId;
			branchCodeArr[pincodeDlvRowId][arrIndex][1] = this.officeCode;
			arrIndex++;
		});
		arrIndex=0;
	} else {
		alert("No branch(s) available for given pincode.");
		var pincode=getDomElementById("pincodeDlv"+pincodeDlvRowId);
		pincode.value="";
		setTimeout(function(){pincode.focus();}, 10);
		clearDlvRow(pincodeDlvRowId);
	}*/
	if (!isNull(ajaxResp)) {
		//var resp = jsonJqueryParser(ajaxResp);
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			var pincode=getDomElementById("pincodeDlv"+pincodeDlvRowId);
			pincode.value="";
			setTimeout(function(){pincode.focus();}, 10);
			clearDlvRow(pincodeDlvRowId);
		} else {
			prepareDlvBranchDropDown(ajaxResp,pincodeDlvRowId);
			document.getElementById('branchFlagDlv'+pincodeDlvRowId).value = 'Y';
		}
	}
	hideProcessing();
}

/**
 * To prepare delivery branch drop down
 * 
 * @param list
 * @param rowId
 */
function prepareDlvBranchDropDown(list,rowId){
	if(!isNull(document.getElementById('pickupBranchDlv'+rowId))){
	var content=document.getElementById('pickupBranchDlv'+rowId);
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

function prepareDeliveryBranchDropDown(ofc,rowId){
	// clearDropDownList('pickupBranch'+rowId);
	 addOptionTODropDown('pickupBranchDlv'+rowId, ofc.officeCode+' - '+ofc.officeName, ofc.officeId); 
}

/**
 * To get Pickup/Delivery Branch Code
 * 
 * @param obj
 */
function getDlvBranchOnClick(rId){
	/*var rowId=getRowId(obj, "pickupBranchDlv");
	rowId=parseInt(rowId);
	for(var i=0; i<branchCodeArr[rowId].length; i++){
		if(branchCodeArr[rowId][i][0]==obj.value){
			jQuery("#pickupBranchCodeDlv"+rowId).val(branchCodeArr[rowId][i][1]);
		}
	}*/
	var objVal = "";
	var url= "";
	pincodeDlvRowId = rId;
	var flagVal=document.getElementById('branchFlagDlv'+rId).value;
	if(flagVal == 'N'){
		objVal = getDomElementById("pincodeDlv"+rId).value;
		document.getElementById('branchPincodeDlv'+rId).value = objVal;
		url="./rateContract.do?submitName=getPickupBranchsByPincode&pincode="+objVal;
		ajaxCallWithoutForm(url, callBackDlvBranchs);
		showProcessing();
	}
}

function getDlvBranchCode(rId){
	 var objValue = document.getElementById('pickupBranchDlv'+rId).value;
	for(var i=0; i<branchCodeArr[rId].length; i++){
		if(branchCodeArr[rId][i][0]==objValue){
			jQuery("#pickupBranchCodeDlv"+rId).val(branchCodeArr[rId][i][1]);
		}
	}
}

/**
 * To check mandatory field(s).
 * 
 * @returns {Boolean}
 */
function checkDlvMandatoryFields(){
	for(var i=1; i<rowCountD; i++){
		if(!isNull(getDomElementById("pincodeDlv"+i)) && !validateDlvGridDtls(i)){
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
function validateDlvGridDtls(rowId){
	var pincode=getDomElementById("pincodeDlv"+rowId);
	var pickupBranch=getDomElementById("pickupBranchDlv"+rowId);
	var locName=getDomElementById("locationNameDlv"+rowId);
	var address1=getDomElementById("address1Dlv"+rowId);
	var address2=getDomElementById("address2Dlv"+rowId);
	var address3=getDomElementById("address3Dlv"+rowId);
	var status = getDomElementById("contractStatus").value;
	var customerCode=getDomElementById("customerCodeDlv"+rowId);
	var billingType = getDomElementById("typeOfBilling").value;

	//var customerCode=getDomElementById("customerCodeDlv"+rowId);
	if(isNull(pincode.value)){
		alert("Please provide pincode");
		setTimeout(function(){pincode.focus();}, 10);
		return false;
	}
	if(isNull(pickupBranch.value)){
		alert("Please select delivery branch");
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
	if(!isNull(address2) && isNull(address2.value)){
		alert("Please provide address 2");
		setTimeout(function(){address2.focus();}, 10);
		return false;
	}
	if(!isNull(address3) && isNull(address3.value)){
		alert("Please provide address 3");
		setTimeout(function(){address3.focus();}, 10);
		return false;
	}
	if((status == "A" || status == "B") && (billingType == "DBCP") && isNull(customerCode.value)){
		alert("Please provide customer code");
		setTimeout(function(){customerCode.focus();}, 10);
		return false;
	}
	return true;
}

/**
 * To save rate contract delivery details
 */
function saveRateContractDlvDtls(){
	if(checkDlvMandatoryFields() && validateBillPayLocDlv()){
		buttonDisableById("saveDlvBtn");
		setBillPayLocForSaveDlv();
		jQuery("#pickDlvIdsArr").val(delPickDlvIds);
		var url="./rateContract.do?submitName=saveRateContractPickupDlvDtls";
		jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
		ajaxCall1(url, RATE_CON_FORM, callBackSaveDlvDtls);//RATE_CON_FORM - Global var.
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}
//call back function for saveRateContractDlvDtls
function callBackSaveDlvDtls(ajaxResp){
	var errorFlag = "ERROR";
	if(!isNull(ajaxResp)){
	var responseText = jsonJqueryParser(ajaxResp);
	var error = responseText[errorFlag];
	if (responseText != null && error != null) {
		jQuery.unblockUI();
		alert(error);
	} else {
		delPickDlvIds = "";
		var data = responseText;
		assignDlvRowValues(data);
		validateAllEnableOrDisabled();
		enableTabs(7);
		jQuery.unblockUI();
		alert("Data Saved Successfully");
	}
	}else{
		jQuery.unblockUI();
	}
	buttonEnableById("saveDlvBtn");
}
function assignDlvRowValues(data){
	conType = getRateContractType();
	if(conType==NORM){
		deletePickupDelTableRows(NORM_DLV_TABLE);
		rowCountD = 1;
	}
	var type=getTypeOfBilling();
	/*deleteTableRow(NORM_PICKUP_TABLE);
	rowCountP = 1;
*/	if(!isNull(data.conPayBillLocDO)){
		contractListLen = data.conPayBillLocDO.length;
		if(contractListLen > 0){
			var conBillLocList = data.conPayBillLocDO;
			
			for(var i=0; i<contractListLen; i++) {
				additioanlRowToDlvDtls(NORM_DLV_TABLE);
			}
			$('#'+NORM_DLV_TABLE).dataTable().fnDraw();			
			
		for(var i=0; i<contractListLen; i++) {
			var conPayBillLoc = conBillLocList[i];
			var loc = conPayBillLoc.pickupDeliveryLocation;
			var con = loc.pickupDlvContract;
			var addr = loc.address;
		
			jQuery("#pickupDlvType").val(con.contractType);		
		
				//addRowToDlvDtls(NORM_DLV_TABLE);
				var rId=i+1;
				jQuery("#pincodeDlv"+rId).val(addr.pincodeDO.pincode);
				
				preparePickupDelBranchDropDown("pickupBranchDlv", con.office, rId);
				jQuery("#pickupBranchDlv"+rId).val(con.office.officeId);
				jQuery("#pickupBranchCodeDlv"+rId).val(con.office.officeCode);
				
				//getDlvBranchCode(getDomElementById("pickupBranchDlv"+rId));
				
				jQuery("#locationNameDlv"+rId).val(addr.name);
				jQuery("#address1Dlv"+rId).val(addr.address1);
				jQuery("#address2Dlv"+rId).val(addr.address2);
				jQuery("#address3Dlv"+rId).val(addr.address3);
				jQuery("#contactPersonDlv"+rId).val(addr.contactPerson);
				jQuery("#designationDlv"+rId).val(addr.designation);
				jQuery("#mobileDlv"+rId).val(addr.mobile);
				jQuery("#emailDlv"+rId).val(addr.email);
				if(conPayBillLoc.locationOperationType==(BILL+PAY)){//BP
					getDomElementById("billingDlv"+rId).checked=true;
					getDomElementById("paymentDlv"+rId).checked=true;
				} else {
					if(conPayBillLoc.locationOperationType==BILL){//B
						getDomElementById("billingDlv"+rId).checked=true;
					}
					if(conPayBillLoc.locationOperationType==PAY){//P
						getDomElementById("paymentDlv"+rId).checked=true;
					}
				}
				jQuery("#customerCodeDlv"+rId).val(conPayBillLoc.shippedToCode);
				jQuery("#pickupBranchCodeDlv"+rId).val(con.office.officeCode);//hidden
				jQuery("#conPayBillLocIdDlv"+rId).val(conPayBillLoc.contractPaymentBillingLocationId);//hidden
				jQuery("#pickupDlvLocIdDlv"+rId).val(loc.pickupDlvLocId);//hidden
				jQuery("#contractIdDlv"+rId).val(con.contractId);//hidden
				jQuery("#addressIdDlv"+rId).val(addr.addressId);//hidden
				jQuery("#locCodeDlv"+rId).val(loc.pickupDlvLocCode);//hidden
				jQuery("#locCityDlv"+rId).val(addr.pincodeDO.cityId);//hidden
				jQuery("#locPincodeIdDlv"+rId).val(addr.pincodeDO.pincodeId);//hidden
				
				if(type==DD) {
					buttonDisabled('addDlvBtn','btnintformbigdis');
				} else if(type==CC) {
					if(isBillPayLocChkdDlv()){
						//var rId=rowCountD-1;
						//getDomElementById("billingDlv"+rId).disabled=true;
						//getDomElementById("paymentDlv"+rId).disabled=true;
					}
				} else if(type==DC) {					
					var rowElem = getDomElementById("customerCodeDlv"+rId);
					rowElem.readOnly = false;
					rowElem.setAttribute("tabindex","0");
				}
				setBillPayLocDlv(rId);
				
			} //End of ELSE-IF
		}
		}//End of FOR
}
/**
 * To validate tab for billing type DBDP
 */
function validateDlvForDD(){
	$('#tabs-7 #demo .title .title2').text(labelDD);
	var table=getDomElementById(NORM_DLV_TABLE);
	var tabLen=table.rows.length;
	if(tabLen==2){
		if(isNull(getDomElementById("pincodeDlv"+(tabLen-1))))
			tabLen--;		
	}
	if(tabLen==1){
		buttonEnabled('addDlvBtn','btnintform');
		jQuery('#addDlvBtn').removeClass('btnintformbigdis');
	}
	//buttonDisabled('addPickupBtn','btnintformbigdis');
}

/**
 * To validate tab for billing type CBCP
 */
function validateDlvForCC(){
	$('#tabs-7 #demo .title .title2').text(labelCC);
	buttonEnabled('addDlvBtn','btnintform');
	jQuery('#addDlvBtn').removeClass('btnintformbigdis');
	//buttonDisabled('addPickupBtn','btnintformbigdis');
}

/**
 * To validate tab for billing type DBCP
 */
function validateDlvForDC(){
	$('#tabs-7 #demo .title .title2').text(labelDC);
	buttonEnabled('addDlvBtn','btnintform');
	jQuery('#addDlvBtn').removeClass('btnintformbigdis');
}

/**
 * To validate billing and payment location 
 * 
 * @returns {Boolean}
 */
function validateBillPayLocDlv(){
	var type=getTypeOfBilling();
	if(type==DD){
		for(var i=1; i<rowCountD; i++){
			if(!isNull(getDomElementById("billingDlv"+i))
					&& !isNull(getDomElementById("paymentDlv"+i))){
				if(getDomElementById("billingDlv"+i).checked==false){
					alert("Please select billing location");
					setTimeout(function(){getDomElementById("billingDlv"+i).focus();}, 10);
					return false;
				} else if(getDomElementById("paymentDlv"+i).checked==false){
					alert("Please select payment location");
					setTimeout(function(){getDomElementById("paymentDlv"+i).focus();}, 10);
					return false;
				}
			}
		}
	} else if(type==CC){
		chkElm = false;
		for(var i=1; i<rowCountD; i++){
			/*if(!isNull(getDomElementById("billingDlv"+i)) && getDomElementById("billingDlv"+i).disabled==false
					&& !isNull(getDomElementById("paymentDlv"+i)) && getDomElementById("paymentDlv"+i).disabled==false){
*/					if(!isNull(getDomElementById("billingDlv"+i))
					&& !isNull(getDomElementById("paymentDlv"+i))){
					if(getDomElementById("billingDlv"+i).checked==true 
							&& getDomElementById("paymentDlv"+i).checked==true){
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
		for(var i=1; i<rowCountD; i++){
			if(!isNull(getDomElementById("billingDlv"+i))
					&& !isNull(getDomElementById("paymentDlv"+i))){
				if(getDomElementById("paymentDlv"+i).checked==true && flag){
					alert("Only one location should be select as payment location.");
					setTimeout(function(){getDomElementById("paymentDlv"+i).focus();}, 10);
					return false;
				}
				if(getDomElementById("paymentDlv"+i).checked==true){
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
function setBillPayLocDlv(rId){
	var type=getTypeOfBilling();
	if(type==DD){
		getDomElementById("billingDlv"+rId).checked=true;
		getDomElementById("billingDlv"+rId).disabled=true;
		getDomElementById("paymentDlv"+rId).checked=true;
		getDomElementById("paymentDlv"+rId).disabled=true;
	} else if(type==DC){
		getDomElementById("billingDlv"+rId).checked=true;
		getDomElementById("billingDlv"+rId).disabled=true;
	}
}

/**
 * To select billing or payment location as per type of billing  
 * 
 * @param chkObj
 * @param rowId
 */
function selectBillPayLocDlv(chkObj, rowId) {
	var type=getTypeOfBilling();
	if(type==CC){
		if(chkObj.checked==true){
			for(var i=1; i<rowCountD; i++) {
				if(!isNull(getDomElementById("billingDlv"+i)) 
						&& !isNull(getDomElementById("paymentDlv"+i))){
					if(i==rowId) {
						getDomElementById("billingDlv"+i).checked=true;
						getDomElementById("paymentDlv"+i).checked=true;
					} else {
						getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
					}
				}				
			} 
		} else {
			for(var i=1; i<rowCountD; i++) {
				if(!isNull(getDomElementById("billingDlv"+i)) 
						&& !isNull(getDomElementById("paymentDlv"+i))){
					if(i==rowId) {
						getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
					} else {
						getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
					}
				}				
			}
		}
	}else if(type==DC){
		if(chkObj.checked==true){
			for(var i=1; i<rowCountD; i++) {
				if(!isNull(getDomElementById("billingDlv"+i)) 
						&& !isNull(getDomElementById("paymentDlv"+i))){
					if(i==rowId) {
						getDomElementById("billingDlv"+i).checked=true;
						//getDomElementById("paymentDlv"+i).checked=true;
					} else {
						//getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
					}
				}				
			} 
		} else {
			for(var i=1; i<rowCountD; i++) {
				if(!isNull(getDomElementById("paymentDlv"+i))){
					if(i==rowId) {
						//getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
					} else {
						//getDomElementById("billingDlv"+i).checked=false;
						getDomElementById("paymentDlv"+i).checked=false;
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
function isBillPayLocChkdDlv(){
	for(var i=1; i<rowCountD; i++) {
		if(!isNull(getDomElementById("billingDlv"+i)) 
				&& !isNull(getDomElementById("paymentDlv"+i))){
			if(getDomElementById("billingDlv"+i).checked==true 
					&& getDomElementById("paymentDlv"+i).checked==true){
				return true;
			}
		}				
	}
	return false;
}

/**
 * To validate row before delete (for bill & pay location)
 */
function validateRowForDelDlv(rowId){
	var type=getTypeOfBilling();
	if(type==CC){
		/*for(var i=1; i<rowCountD; i++){
			if(!isNull(getDomElementById("chkd"+i)) 
					&& getDomElementById("chkd"+i).checked==true){*/
				if(getDomElementById("billingDlv"+rowId).checked==true 
						&& getDomElementById("paymentDlv"+rowId).checked==true){
					getDomElementById("billingDlv"+rowId).checked=false;
					//getDomElementById("paymentDlv"+rowId).checked=false;
					selectBillPayLocDlv(getDomElementById("billingDlv"+rowId), rowId);
				}
		/*	}
		}*/
	}
}

/**
 * To clear delivery row
 * 
 * @param rowId
 */
function clearDlvRow(rowId){
	
	var cbblId = getDomElementById("conPayBillLocIdDlv"+rowId).value;
	
	if(!isNull(cpblId)){
		if(!isNull(delPickDlvIds)){
			delPickDlvIds = delPickDlvIds + ",";
		}
			delPickDlvIds = delPickDlvIds + cbblId;
	}
	getDomElementById("chkd"+rowId).checked=false;
	jQuery("#pincodeDlv"+rowId).val("");
	jQuery("#pickupBranchDlv"+rowId).val("");
	jQuery("#locationNameDlv"+rowId).val("");
	jQuery("#address1Dlv"+rowId).val("");
	jQuery("#address2Dlv"+rowId).val("");
	jQuery("#address3Dlv"+rowId).val("");
	jQuery("#contactPersonDlv"+rowId).val("");
	jQuery("#designationDlv"+rowId).val("");
	jQuery("#mobileDlv"+rowId).val("");
	jQuery("#emailDlv"+rowId).val("");
	//getDomElementById("billingDlv"+rowId).checked=false;
	//getDomElementById("paymentDlv"+rowId).checked=false;
	jQuery("#isBillLocDlv"+rowId).val("");
	jQuery("#isPayLocDlv"+rowId).val("");
	jQuery("#customerCodeDlv"+rowId).val("");
	jQuery("#pickupBranchCodeDlv"+rowId).val("");//hidden
	jQuery("#conPayBillLocIdDlv"+rowId).val("");//hidden
	jQuery("#pickupDlvLocIdDlv"+rowId).val("");//hidden
	jQuery("#contractIdDlv"+rowId).val("");//hidden
	jQuery("#addressIdDlv"+rowId).val("");//hidden
	jQuery("#locCodeDlv"+rowId).val("");//hidden
}

/**
 * To set Billing and Payment Location during save
 */
function setBillPayLocForSaveDlv(){
	for(var i=1; i<rowCountD; i++) {
		if(!isNull(getDomElementById("billingDlv"+i))
				&& !isNull(getDomElementById("paymentDlv"+i))){
			getDomElementById("billingDlv"+i).disabled = false;
			getDomElementById("paymentDlv"+i).disabled = false;
			if(getDomElementById("billingDlv"+i).checked==true){
				jQuery("#isBillLocDlv"+i).val("Y");
			} else {
				jQuery("#isBillLocDlv"+i).val("N");
			}
			if(getDomElementById("paymentDlv"+i).checked==true){
				jQuery("#isPayLocDlv"+i).val("Y");
			} else {
				jQuery("#isPayLocDlv"+i).val("N");
			}
		}				
	}
}

/**
 * To validate Block and Unblock Button after successfully changed customer status.
 */
function validateBlockUnblockCustDlv(){
	var status=getDomElementById("customerStatus").value;
	if(status==ACTIVE){
		buttonEnabled('blockDlvBtn','btnintform');
		jQuery('#blockDlvBtn').removeClass('btnintformbigdis');
		buttonDisabled('unblockDlvBtn','btnintformbigdis');
		buttonDisabled('cancelDlvBtn','btnintformbigdis');
	} else if(status==INACTIVE) {
		buttonEnabled('unblockDlvBtn','btnintform');
		jQuery('#unblockDlvBtn').removeClass('btnintformbigdis');
		buttonDisabled('blockDlvBtn','btnintformbigdis');
		buttonDisabled('cancelDlvBtn','btnintformbigdis');
	}
}

/**
 * To search Delivery Details
 */
function searchDlvDtls(){
	delPickDlvIds = "";
	var rateConId=getDomElementById("rateContractId").value;
	if(!isNull(rateConId)){
		var url='./rateContract.do?submitName=searchRateContractPickupDlvDtls'
			+ '&rateContractId='+rateConId;
		ajaxCallWithoutForm(url, callBackSearchDlvDtls);
	} else {
		validatePickupDlvTabs();
	}
}
//call back function for : searchDlvDtls
function callBackSearchDlvDtls(ajaxResp){
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
			addRowToDlvDtls(tabId);
			var rId=rowCountD-1;
			var loc = data[i].pickupDeliveryLocationTO;
			var con = loc.pickupDlvContract;
			var addr = loc.pickupDlvAddress;
			jQuery("#isExistPkUpOrDlvDetails").val(true);
			if(con.contractType==DLV_CONTRACT_TYPE){
				jQuery("#pincodeDlv"+rId).val(addr.pincode.pincode);
				
				prepareDeliveryBranchDropDown(con.office, rId);
				jQuery("#pickupBranchDlv"+rId).val(con.office.officeId);
				//getDlvBranchCode(getDomElementById("pickupBranchDlv"+rId));
				
				jQuery("#locationNameDlv"+rId).val(addr.name);
				jQuery("#address1Dlv"+rId).val(addr.address1);
				jQuery("#address2Dlv"+rId).val(addr.address2);
				jQuery("#address3Dlv"+rId).val(addr.address3);
				jQuery("#contactPersonDlv"+rId).val(addr.contactPerson);
				jQuery("#designationDlv"+rId).val(addr.designation);
				jQuery("#mobileDlv"+rId).val(addr.mobile);
				jQuery("#emailDlv"+rId).val(addr.email);
				if(data[i].locationOperationType==(BILL+PAY)){//BP
					getDomElementById("billingDlv"+rId).checked=true;
					getDomElementById("paymentDlv"+rId).checked=true;
				} else {
					if(data[i].locationOperationType==BILL){//B
						getDomElementById("billingDlv"+rId).checked=true;
					}
					if(data[i].locationOperationType==PAY){//P
						getDomElementById("paymentDlv"+rId).checked=true;
					}
				}
				jQuery("#customerCodeDlv"+rId).val(data[i].shippedToCode);
				jQuery("#pickupBranchCodeDlv"+rId).val(con.office.officeCode);//hidden
				jQuery("#conPayBillLocIdDlv"+rId).val(data[i].contractPaymentBillingLocationId);//hidden
				jQuery("#pickupDlvLocIdDlv"+rId).val(loc.pickupDlvLocId);//hidden
				jQuery("#contractIdDlv"+rId).val(con.contractId);//hidden
				jQuery("#addressIdDlv"+rId).val(addr.addressId);//hidden
				jQuery("#locCodeDlv"+rId).val(loc.pickupDlvLocCode);//hidden
			}
		}// END OF FOR LOOP
	}
		} else {
			delPickDlvIds = "";
		validatePickupDlvTabs();
	}
}

/**
 * To Edit Delivery Details
 */
function editDlvDtls() {
	var tabId=getTableIdByPickupDlvType(jQuery("#pickupDlvType").val());
	$("#"+tabId).find("input").attr("disabled", false);
	$("#"+tabId).find("select").attr("disabled", false);
	$("#checkAllDlv").attr("disabled", false);
	callReqDlvValidation();
}

/**
 * To call other required delivery validation(s)-function(s)
 */
function callReqDlvValidation(){
	validateContractStatus();//Common function
	validatePickupDlvTabs();
	validateDlvPayBillLoc();
	validateButtonForEdit();//Common function
}

/**
 * To Validate Delivery Payment/Billing Location
 */
function validateDlvPayBillLoc(){
	var type=getTypeOfBilling();
	var isBillPayLocChked = false;
	for(var i=1;i<rowCountD;i++){
		if(!isNull(getDomElementById("billingDlv"+i)) 
				&& !isNull(getDomElementById("paymentDlv"+i))){
			if(type==DD){
				getDomElementById("billingDlv"+i).checked=true;
				getDomElementById("paymentDlv"+i).checked=true;
			} else if(type==DC){
				getDomElementById("billingDlv"+i).checked=true;
				//getDomElementById("billingDlv"+i).disabled=true;
			} else if(type==CC) {
				if(isBillPayLocChked || isBillPayLocChkdDlv()){
					if(getDomElementById("billingDlv"+i).checked==false 
							&& getDomElementById("paymentDlv"+i).checked==false){
						//getDomElementById("billingDlv"+i).disabled=true;
						//getDomElementById("paymentDlv"+i).disabled=true;
					}
					isBillPayLocChked=true;
				}
			}
		}
	}
}

function cancelDlvDetails(){
	rateBillingDtlsStartup();
}