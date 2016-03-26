var billingSearch=false;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
var isDeletePickupOrDlvLocations = "N";
var originatedRateContractId = null;

/**
 * Rate Contract Billing Details Start up
 */
function rateBillingDtlsStartup(){
	var rateContractId=getDomElementById("rateContractId").value;
	//setDefaultDates();
	validateContractStatus();
	//setDefaultBillingDtls();
	if(!isNull(rateContractId) && !billingSearch){
		searchRateContractBillingDtls(rateContractId);
	}
	configureSoldToCode();
	configureCustomerCode();
	var type=getTypeOfBilling();
	if(type==DD) {
		$('#tabs-6 #demo .title .title2').text(labelDD);
	} else if(type==CC) {
		$('#tabs-6 #demo .title .title2').text(labelCC);
	} else if(type==DC) {
		$('#tabs-6 #demo .title .title2').text(labelDC);
	}
	//validatePickupDlvTab(getDomElementById("billingContractType"));
}


/**
 * Rate Contract PICKUP/DELIVERY Details Start up
 */
function ratePickupDelDtlsStartup(){
	var rateContractId=getDomElementById("rateContractId").value;
	if(!isNull(rateContractId) && !billingSearch){
		searchRateContractPickupDelDtls(rateContractId);
	}
}

/**
 * To set Default value(s) 
 */
function setDefaultBillingDtls(){
	//setDefaultDates();
	jQuery("#billingContractType").val(NORMAL_CONTRACT);
	jQuery("#typeOfBilling").val(BILL_TYPE_DBDP);
	jQuery("#modeOfBilling").val(HARD_COPY);
	jQuery("#billingCycle").val(MONTHLY_BILLING);
	//jQuery("#octraiBourneBy").val(OCTROI_BY_CE);
}

/**
 * To set Default Date(s).
 */
function setDefaultDates(){
	var arrTodayDt=TODAY_DATE.split("/");//Today date
	var todayDt=new Date(arrTodayDt[2], arrTodayDt[1]-1, arrTodayDt[0]);
	var fromDt=new Date(todayDt.getTime()+(24*60*60*1000));
	var toDt=new Date(todayDt.getTime()+((24*60*60*1000)*2));
	jQuery("#validFromDate").val(getDateInDDMMYYYY(fromDt));
	jQuery("#validToDate").val(getDateInDDMMYYYY(toDt));
}

/**
 * To set VALID ToDate(s).
 */
function setValidToDates(){
	var fromDate=getDomElementById("validFromDate").value;
	var toDate=getDomElementById("validToDate").value;
	var i=compareDates(fromDate, toDate);
	if(i!=-1){//fromDate>=toDate
		var arrFromDt=fromDate.split("/");//From date
		var fromDt=new Date(arrFromDt[2], arrFromDt[1]-1, arrFromDt[0]);
		var toDt=new Date(fromDt.getTime()+(24*60*60*1000));//Next to from date
		jQuery("#validToDate").val(getDateInDDMMYYYY(toDt));
	}
}

/**
 * To convert Date into DD/MM/YYYY String
 * 
 * @param dt
 * @returns {String}
 */
function getDateInDDMMYYYY(dt){
	var DD=dt.getDate()+"";
	DD=(DD.length==1)?"0"+DD:DD;//Set 2 digit format 
	var MM=(dt.getMonth()+1)+"";
	MM=(MM.length==1)?"0"+MM:MM;//Set 2 digit format
	var YYYY=dt.getFullYear();
	var DDMMYYYY=DD+"/"+MM+"/"+YYYY;
	return DDMMYYYY;
}

/**
 * To validate EFFECTIVE FROM DATE
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateFromDate(obj){
	if(!isNull(obj.value)){
		var todayDate=TODAY_DATE;
		//var arrTodayDt=todayDate.split("/");//Today date
		//var todayDt=new Date(arrTodayDt[2], arrTodayDt[1]-1, arrTodayDt[0]);
		var i=compareDates(todayDate, obj.value);
		if(i==undefined || i==0 || i==1){
			alert("Effective from date should be greater than today date.");
			//var tDt=new Date(todayDt.getTime()+(24*60*60*1000));//Next to today date
			//obj.value=getDateInDDMMYYYY(tDt);
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			//setValidToDates();
			return false;
		}
		//setValidToDates();
		validateToDate(getDomElementById("validToDate"));
	}
}

/**
 * To validate VALID TO DATE
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateToDate(obj){
	if(!isNull(obj.value)){
		var fromDate=getDomElementById("validFromDate").value;
		//var arrFromDt=fromDate.split("/");//From date
		//var fromDt=new Date(arrFromDt[2], arrFromDt[1]-1, arrFromDt[0]);
		var i=compareDates(fromDate,obj.value);
		if(i==undefined || i==0 || i==1){
			alert("To Date should be greater than effective from date.");
			//var tDt=new Date(fromDt.getTime()+(24*60*60*1000));//Next day of from date
			//obj.value=getDateInDDMMYYYY(tDt);
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			return false;
		} else{
			if(!validateDateRange("validFromDate","validToDate")){
				obj.value="";
				setTimeout(function(){obj.focus();}, 10);
				return false;
			}
		}
	}
	return true;
}

/**
 * To Validate From Date before setting Valid To Date
 * 
 * @param toDtId
 * @param toDtValue
 */
function validateFromDtToSetToDt(toDtId, toDtObj) {
	var fromDate=getDomElementById("validFromDate");
	if(!isNull(fromDate.value)){
		setYears(1980, 2030);
		showCalender(toDtObj, toDtId);
		//show_calendar(toDtId, toDtValue);
	} else {
		alert("Please First Select Effective From Date");
		setTimeout(function(){fromDate.focus();}, 10);
		return false;
	}
}

/**
 * To check mandatory field for Billing details
 * 
 * @returns {Boolean}
 */
function checkMandatoryForBillDtls(){
	var msg = "Please provide : ";
	var isValid = true;
	var fromDt = getDomElementById("validFromDate");
	var toDt = getDomElementById("validToDate");
	//var panNo=getDomElementById("panNo");
	//var tanNo=getDomElementById("tanNo");
	var focusObj = fromDt;
	
	if(isNull(fromDt.value)){
		//alert("Please Provide Effective From Date");
		//setTimeout(function(){fromDt.focus();}, 10);
		//return false;
		if(isValid)	focusObj = fromDt;
		msg = msg + ((!isValid)?", ":"") + "Effective From Date";
		isValid=false;
	}
	if(isNull(toDt.value)){
		//alert("Please Provide Valid To Date");
		//setTimeout(function(){toDt.focus();}, 10);
		//return false;
		if(isValid)	focusObj = toDt;
		msg = msg + ((!isValid)?", ":"") + "Valid Till Date";
		isValid=false;
	}
	
	
	/*if(isNull(panNo.value)){
		alert("Please provide pan no.");
		setTimeout(function(){panNo.focus();}, 10);
		return false;
	}
	if(isNull(tanNo.value)){
		alert("Please provide tan no.");
		setTimeout(function(){tanNo.focus();}, 10);
		return false;
	}*/
	if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	return isValid;
}

/**
 * To Save Rate Contract Billing Details
 */
function saveRateContractBillingDtls() {
	if(checkMandatoryForBillDtls() && validateFromAndToDates() && validateBillingType() && validateSoldToCode()){
		getDomElementById("soldToCode").disabled = false;
		var url='./rateContract.do?submitName=saveRateContractBillingDtls' + '&isDeletePickupOrDlvLocations=' + isDeletePickupOrDlvLocations;
		ajaxCall1(url, RATE_CON_FORM, callBackForSaveBillingDtls);//RATE_CON_FORM - Global var.
		isDeletePickupOrDlvLocations = "N";
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}
//call back function for saveRateContractBillingDtls
function callBackForSaveBillingDtls(ajaxResp) {
	delPickDlvIds = "";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			var data = responseText;
			jQuery("#rateContractId").val(data.rateContractId);
			jQuery("#sysFromDate").val(jQuery("#validFromDate").val());		
			vldPickupDlvTab();
			configureSoldToCode();
			jQuery.unblockUI();
			alert(data.transMsg);
		}
	}else{
		jQuery.unblockUI();
	}
}

function checkDeleteOfPickupOrDlvLocations(){
	// ($.inArray(5, $("#tabs").tabs("option", "disabled")) == -1)
	var isExistPkUpOrDlvDetails = getDomElementById("isExistPkUpOrDlvDetails").value;
	if(isExistPkUpOrDlvDetails == "true"){
		var selectedTypeOfBilling = getDomElementById("typeOfBilling").value;
		var existingTypeOfBilling = getDomElementById("typeOfBillingFromDatabase").value;
		if(selectedTypeOfBilling != existingTypeOfBilling){
			if(confirm("Changing the type of billing will delete previously entered pickup/delivery locations. Do you wish to continue?")){
				isDeletePickupOrDlvLocations = "Y";
			}
			else{
				jQuery("#typeOfBilling").val(existingTypeOfBilling);
				return false;
			}
		}
	}
}

/**
 * To Search Rate Contract Billing Details
 */
function searchRateContractBillingDtls(rateId) {
	delPickDlvIds = "";
	var url='./rateContract.do?submitName=searchRateContractBillingDtls'
		+'&rateContractId='+rateId;
	ajaxCallWithoutForm(url, callBackForSearchBillingDtls);
	showProcessing();
}

/**
 * To Search Rate Contract Pickup/Del Details
 */
function searchRateContractPickupDelDtls(rateId) {
	delPickDlvIds = "";
	var url='./rateContract.do?submitName=searchRateContractPickDlvDtls'
		+'&rateContractId='+rateId;
	ajaxCallWithoutForm(url, callBackForSearchPickupDelDtls);
	showProcessing();
}



//call back function for searchRateContractBillingDtls
function callBackForSearchBillingDtls(ajaxResp) {
	delPickDlvIds = "";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
	    } 
	}
	else {
			var data=ajaxResp;
	
		// This line has been added to identify whether a contract has been renewed
		originatedRateContractId = data.originatedRateContractId;
		
		jQuery("#validFromDate").val(data.validFromDate);
		jQuery("#sysFromDate").val(data.validFromDate);		
		jQuery("#validToDate").val(data.validToDate);
		jQuery("#billingContractType").val(data.billingContractType);
		jQuery("#typeOfBilling").val(data.typeOfBilling);
		jQuery("#modeOfBilling").val(data.modeOfBilling);
		jQuery("#billingCycle").val(data.billingCycle);
		jQuery("#paymentTerm").val(data.paymentTerm);
		
		jQuery("#isExistPkUpOrDlvDetails").val(data.isExistPkUpOrDlvDetails);
		jQuery("#soldToCode").val(data.soldToCode);
		jQuery("#panNo").val(data.panNo);
		jQuery("#tanNo").val(data.tanNo);
		//jQuery("#rateContractId").val(data.rateContractId);//hidden
		//jQuery("#customerNewId").val(data.customerId);//hidden
		//jQuery("#rateQuotationId").val(data.rateQuotationId);//hidden
		//jQuery("#createdBy").val(data.createdBy);//hidden
		//jQuery("#updatedBy").val(data.updatedBy);//hidden
		//jQuery("#pickupDlvType").val(data.pickupDlvContractType);//hidden
		
		jQuery("#rateContractNo").val(data.rateContractNo);//hidden
		jQuery("#rateContractType").val(data.rateContractType);//hidden
		jQuery("#contractStatus").val(data.contractStatus);//hidden
		jQuery("#typeOfBillingFromDatabase").val(data.typeOfBilling);//hidden
		
		if(!isNewBillingDtls(data)){
			
			if(getDomElementById("billingContractType").value == "N"){
				jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
			}else{
				jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
			}
			/*validatePickupDlvTab(getDomElementById("billingContractType"));
			var contractList = data.contractLocationList.length;
			var tabId = getTableIdByContractType(getRateContractType());
			//To set respect PICKUP/DELIVERY Tab.
			conType = getRateContractType();
			
			var pickupDlvType = getDomElementById("pickupDlvType").value;
			
			if(conType==NORM){
				if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
					deleteTableRow(NORM_PICKUP_TABLE);
					rowCountP = 1;
					if(contractList <= 0){
						addRowToPickupDtls(tabId);
					}
				}
				
				else if(pickupDlvType==DLV_CONTRACT_TYPE) {
					deleteTableRow(NORM_DLV_TABLE);
					rowCountD = 1;
					if(contractList <= 0){
						addRowToDlvDtls(tabId);
					}
				}
			} else if(conType==ECOM) {
				deleteTableRow("eCommercePickupDtlsTable");
				rowCountP = 1;
				if(contractList <= 0){
					addRowToPickupDtls(tabId);
				}
			}
			*/
			//billingSearch=false;
		} else {
			// setting default values
			setDefaultBillingDtls();
			validatePickupDlvTab(getDomElementById("billingContractType"));
		}
		
		//enableOrDisableBillingButtons();
	} 
	//validatePickupDlvTab(getDomElementById("billingContractType"));
	validateAllEnableOrDisabled();
}
	hideProcessing();	
}

/*
//call back function for searchRateContractBillingDtls
function callBackForSearchBillingDtls(ajaxResp) {
	delPickDlvIds = "";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);

		} 
		}else {
			var data=ajaxResp;
	
		
		/*jQuery("#validFromDate").val(data.validFromDateStr);
		jQuery("#sysFromDate").val(data.validFromDateStr);		
		jQuery("#validToDate").val(data.validToDateStr);
		jQuery("#billingContractType").val(data.billingContractType);
		jQuery("#typeOfBilling").val(data.typeOfBilling);
		jQuery("#modeOfBilling").val(data.modeOfBilling);
		jQuery("#billingCycle").val(data.billingCycle);
		jQuery("#paymentTerm").val(data.paymentTerm);
		jQuery("#soldToCode").val(data.rateQuotationDO.customer.customerCode);
		jQuery("#panNo").val(data.rateQuotationDO.customer.panNo);
		jQuery("#tanNo").val(data.rateQuotationDO.customer.tanNo);
		
			jQuery("#validFromDate").val(data.validFromDate);
			jQuery("#sysFromDate").val(data.validFromDate);		
			jQuery("#validToDate").val(data.validToDate);
			jQuery("#billingContractType").val(data.billingContractType);
			jQuery("#typeOfBilling").val(data.typeOfBilling);
			jQuery("#modeOfBilling").val(data.modeOfBilling);
			jQuery("#billingCycle").val(data.billingCycle);
			jQuery("#paymentTerm").val(data.paymentTerm);
			jQuery("#soldToCode").val(data.soldToCode);
			jQuery("#panNo").val(data.panNo);
			jQuery("#tanNo").val(data.tanNo);
			
		jQuery("#isExistPkUpOrDlvDetails").val(data.isExistPkUpOrDlvDetails);
		jQuery("#rateContractId").val(data.rateContractId);//hidden
		jQuery("#customerNewId").val(data.customerId);//hidden
		jQuery("#rateQuotationId").val(data.rateQuotationId);//hidden
		jQuery("#createdBy").val(data.createdBy);//hidden
		jQuery("#updatedBy").val(data.updatedBy);//hidden
		jQuery("#pickupDlvType").val(data.pickupDlvContractType);//hidden
		
		jQuery("#rateContractNo").val(data.rateContractNo);//hidden
		jQuery("#rateContractType").val(data.rateContractType);//hidden
		jQuery("#contractStatus").val(data.contractStatus);//hidden
		
		if(!isNewBillingDtls(data)){
			
			if(getDomElementById("billingContractType").value == "N"){
				jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
			}else{
				jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
			}
			validatePickupDlvTab(getDomElementById("billingContractType"));
			var contractList = data.conPayBillLocDO.length;
			var tabId = getTableIdByContractType(getRateContractType());
			//To set respect PICKUP/DELIVERY Tab.
			conType = getRateContractType();
			
			var pickupDlvType = getDomElementById("pickupDlvType").value;
			
			if(conType==NORM){
				if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
					deletePickupDelTableRows(NORM_PICKUP_TABLE);
					rowCountP = 1;
					if(contractList <= 0){
						addRowToPickupDtls(tabId);
					}
				}
				
				else if(pickupDlvType==DLV_CONTRACT_TYPE) {
					deletePickupDelTableRows(NORM_DLV_TABLE);
					rowCountD = 1;
					if(contractList <= 0){
						addRowToDlvDtls(tabId);
					}
				}
			} else if(conType==ECOM) {
				deletePickupDelTableRows("eCommercePickupDtlsTable");
				rowCountP = 1;
				if(contractList <= 0){
					addRowToPickupDtls(tabId);
				}
			}
			
			billingSearch=false;
		} else {
			/* setting default values 
			setDefaultBillingDtls();
			validatePickupDlvTab(getDomElementById("billingContractType"));
		}
		
		//enableOrDisableBillingButtons();
	} /*else {
		setDefaultBillingDtls();
	}
	//validatePickupDlvTab(getDomElementById("billingContractType"));
	validateAllEnableOrDisabled();
}
	hideProcessing();	
}*/
/*
//call back function for searchRateContractPickup/DelDtls
function callBackForSearchPickupDelDtls(ajaxResp) {
	delPickDlvIds = "";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);

		} 
		}else {
			var data=ajaxResp;
	
		jQuery("#validFromDate").val(data.validFromDate);
		jQuery("#sysFromDate").val(data.validFromDate);		
		jQuery("#validToDate").val(data.validToDate);
		jQuery("#billingContractType").val(data.billingContractType);
		jQuery("#typeOfBilling").val(data.typeOfBilling);
		jQuery("#modeOfBilling").val(data.modeOfBilling);
		jQuery("#billingCycle").val(data.billingCycle);
		jQuery("#paymentTerm").val(data.paymentTerm);
		
		jQuery("#isExistPkUpOrDlvDetails").val(data.isExistPkUpOrDlvDetails);
		jQuery("#soldToCode").val(data.soldToCode);
		jQuery("#panNo").val(data.panNo);
		jQuery("#tanNo").val(data.tanNo);

		
	
			
			if(getDomElementById("billingContractType").value == "N"){
				jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
			}else{
				jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
			}
			validatePickupDlvTab(getDomElementById("billingContractType"));
			var contractList = data.contractLocationList.length;
			var tabId = getTableIdByContractType(getRateContractType());
			//To set respect PICKUP/DELIVERY Tab.
			conType = getRateContractType();
			
			
			
			var pickupDlvType = getDomElementById("pickupDlvType").value;
			
			if(conType==NORM){
				if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
					deleteTableRow(NORM_PICKUP_TABLE);
					rowCountP = 1;
					if(contractList <= 0){
						addRowToPickupDtls(tabId);
					}
				}
				
				else if(pickupDlvType==DLV_CONTRACT_TYPE) {
					deleteTableRow(NORM_DLV_TABLE);
					rowCountD = 1;
					if(contractList <= 0){
						addRowToDlvDtls(tabId);
					}
				}
			} else if(conType==ECOM) {
				deleteTableRow("eCommercePickupDtlsTable");
				rowCountP = 1;
				if(contractList <= 0){
					addRowToPickupDtls(tabId);
				}
			}
			
			
			if(!isNull(data.contractLocationList)){
				
				for(var i=0; i<contractList; i++) {
					var conPayBillLoc = data.contractLocationList[i];
					var loc = conPayBillLoc.pickupDeliveryLocationTO;
					var con = loc.pickupDlvContract;
					var addr = loc.pickupDlvAddress;
				
					jQuery("#pickupDlvType").val(con.contractType);
					
					
					
					
					if(pickupDlvType==PICKUP_CONTRACT_TYPE) {//P
						addRowToPickupDtls(tabId);
						var rId=rowCountP-1;
						jQuery("#pincode"+rId).val(addr.pincode.pincode);
						
						preparePickupBranchDropDown(conPayBillLoc.pickupDlvBranchList, rId);
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
						setBillPayLocation();
						
					} else if(pickupDlvType==DLV_CONTRACT_TYPE) {//D
						addRowToDlvDtls(tabId);
						var rId=rowCountD-1;
						jQuery("#pincodeDlv"+rId).val(addr.pincode.pincode);
						
						prepareDlvBranchDropDown(conPayBillLoc.pickupDlvBranchList, rId);
						jQuery("#pickupBranchDlv"+rId).val(con.office.officeId);
						getDlvBranchCode(getDomElementById("pickupBranchDlv"+rId));
						
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
						setBillPayLocDlv();
						
					}//End of ELSE-IF					
				}//End of FOR
				//enableSpocTabs();				
			}//End of IF
			
			
			
		
		
		//enableOrDisableBillingButtons();
	} 
	//validatePickupDlvTab(getDomElementById("billingContractType"));
	validateAllEnableOrDisabled();
}
}*/



//call back function for searchRateContractPickup/DelDtls
function callBackForSearchPickupDelDtls(ajaxResp) {
	delPickDlvIds = "";
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);

		} 
		}else {
			var data=ajaxResp;
	
		jQuery("#validFromDate").val(data.validFromDateStr);
		jQuery("#sysFromDate").val(data.validFromDateStr);		
		jQuery("#validToDate").val(data.validToDateStr);
		jQuery("#billingContractType").val(data.billingContractType);
		jQuery("#typeOfBilling").val(data.typeOfBilling);
		jQuery("#modeOfBilling").val(data.modeOfBilling);
		jQuery("#billingCycle").val(data.billingCycle);
		jQuery("#paymentTerm").val(data.paymentTerm);
		
		//jQuery("#isExistPkUpOrDlvDetails").val(data.isExistPkUpOrDlvDetails);
		jQuery("#soldToCode").val(data.rateQuotationDO.customer.customerCode);
		jQuery("#panNo").val(data.rateQuotationDO.customer.panNo);
		jQuery("#tanNo").val(data.rateQuotationDO.customer.tanNo);

		
	
			
			if(getDomElementById("billingContractType").value == "N"){
				jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
			}else{
				jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
			}
			validatePickupDlvTab(getDomElementById("billingContractType"));
			var contractList = data.conPayBillLocDO.length;
			var tabId = getTableIdByContractType(getRateContractType());
			//To set respect PICKUP/DELIVERY Tab.
			conType = getRateContractType();
			
			
			
			var pickupDlvType = getDomElementById("pickupDlvType").value;
			
			if(conType==NORM){
				if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
					deletePickupDelTableRows(NORM_PICKUP_TABLE);
					rowCountP = 1;
					if(contractList <= 0){
						addRowToPickupDtls(tabId);
					}
				}
				
				else if(pickupDlvType==DLV_CONTRACT_TYPE) {
					deletePickupDelTableRows(NORM_DLV_TABLE);
					rowCountD = 1;
					if(contractList <= 0){
						addRowToDlvDtls(tabId);
					}
				}
			} else if(conType==ECOM) {
				deletePickupDelTableRows("eCommercePickupDtlsTable");
				rowCountP = 1;
				if(contractList <= 0){
					addRowToPickupDtls(tabId);
				}
			}
			
			
			//alert((new Date()).getSeconds());
			if(!isNull(data.conPayBillLocDO) && contractList > 0){
				var type=getTypeOfBilling();
				var conBillLocList = data.conPayBillLocDO;
				//var start = new Date().getTime();
				for(var i=0; i<contractList; i++) {
					if(pickupDlvType==PICKUP_CONTRACT_TYPE){
						additioanlRowToPickupDtls(tabId);
					}else{
						additioanlRowToDlvDtls(tabId);
					}
				}
				if(conType==NORM){
					if(pickupDlvType==PICKUP_CONTRACT_TYPE){
						$('#'+NORM_PICKUP_TABLE).dataTable().fnDraw();
					}else{
						$('#'+NORM_DLV_TABLE).dataTable().fnDraw();
					}
				}else{
					$('#eCommercePickupDtlsTable').dataTable().fnDraw();
				}
				for(var i=0; i<contractList; i++) {
					//addRowToPickupDtls(tabId);
					var conPayBillLoc = conBillLocList[i];
					var loc = conPayBillLoc.pickupDeliveryLocation;
					var con = loc.pickupDlvContract;
					var addr = loc.address;
				
					jQuery("#pickupDlvType").val(con.contractType);
					
					
					
					
					if(pickupDlvType==PICKUP_CONTRACT_TYPE) {//P
						
						//addRowToPickupDtls(tabId);
						var rId=i+1;
						jQuery("#pincode"+rId).val(addr.pincodeDO.pincode);
						jQuery("#branchPincode"+rId).val(addr.pincodeDO.pincode);
						
						preparePickupDelBranchDropDown("pickupBranch", con.office, rId);
						jQuery("#pickupBranch"+rId).val(con.office.officeId);
						jQuery("#pickupBranchCode"+rId).val(con.office.officeCode);
						//getPickupBranchCode(getDomElementById("pickupBranch"+rId));
						
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
						
						
					} else if(pickupDlvType==DLV_CONTRACT_TYPE) {//D
						//addRowToDlvDtls(tabId);
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
						
					}//End of ELSE-IF
					
				}//End of FOR
				
				//var end = new Date().getTime();
				//var time = end - start;
				//console.log("Time taken by addRowToPickupDtls - "+time);

				//enableSpocTabs();				
			}//End of IF
			/*else{
				if(conType==NORM){
					if(pickupDlvType==PICKUP_CONTRACT_TYPE) {
						addRowToPickupDtls(tabId);
					}else if(pickupDlvType==DLV_CONTRACT_TYPE) {
						addRowToDlvDtls(tabId);
					}
				} else if(conType==ECOM) {
					addRowToPickupDtls(tabId);
				}
			}*/
			
			
			//alert((new Date()).getSeconds());
		
		//enableOrDisableBillingButtons();
	} /*else {
		setDefaultBillingDtls();
	}*/
	//validatePickupDlvTab(getDomElementById("billingContractType"));
	validateAllEnableOrDisabled();
	var type=getTypeOfBilling();
	if(type==DD) {
		$('#tabs-6 #demo .title .title2').text(labelDD);
	} else if(type==CC) {
		$('#tabs-6 #demo .title .title2').text(labelCC);
	} else if(type==DC) {
		$('#tabs-6 #demo .title .title2').text(labelDC);
	}
}
	hideProcessing();
}


/**
 * To get enable or disable pickup/delivery tabs 
 * (BR - 4, 5)
 * 
 * @param obj
 */
function validatePickupDlvTab(obj){
	//var PICKUP_TABLE='pickupDetailsTable';
	//var DLV_TABLE='deliveryDetailsTable';
	/*var PICKUP_TABLE = getTableIdByPickupDlvType(PICKUP_CONTRACT_TYPE);
	var DLV_TABLE = getTableIdByPickupDlvType(DLV_CONTRACT_TYPE);
	if(obj.value==NORMAL_CON){
		enableTabs(5);//Pickup
		disableTabs(6);//Delivery		
		jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
		if(getRateContractType()==NORM){
			clearTable(DLV_TABLE);
		}
		clearTable(PICKUP_TABLE);
		validatePickupDlvTabs();
		//addRows(PICKUP_TABLE);
	} else if(obj.value==REVS_LOGI_CON){
		disableTabs(5);//Pickup
		enableTabs(6);//Delivery
		jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
		clearTable(PICKUP_TABLE);
		if(getRateContractType()==NORM){
			clearTable(DLV_TABLE);
		}
		validatePickupDlvTabs();
		//addRowsDlv(DLV_TABLE);
	}*/
	
	//configureCustomerCode();
}

/**
 * To clear all row(s) from grid/table by its id
 * 
 * @param tableId
 */
function clearTable(tableId){
	$('#'+tableId).dataTable().fnClearTable();
}

/**
 * To get table id by pickupDlvType
 * 
 * @param type
 * @returns table Id */
function getTableIdByPickupDlvType(type){
	var conType = getRateContractType();
	if(conType==NORM) {
		if(type==PICKUP_CONTRACT_TYPE){
			return NORM_PICKUP_TABLE;
		} else if(type==DLV_CONTRACT_TYPE) {
			return NORM_DLV_TABLE;
		}
	} else if(conType==ECOM) {
		if(type==PICKUP_CONTRACT_TYPE){
			return ECOM_PICKUP_TABLE;
		} else if(type==DLV_CONTRACT_TYPE) {
			return ECOM_DLV_TABLE; 
		}
	}
}

/**
 * To check whether is Billing details tab is fresh screen or not. 
 * 
 * @returns {Boolean}
 */
function isNewBillingDtls(list){
	var isNew=false;
	var billConType = list.billingContractType;
	var type = list.typeOfBilling;
	var mode = list.modeOfBilling;
	var billCyc = list.billingCycle;
	var payTerm = list.paymentTerm;
	if(isNull(billConType)){
		isNew=true;
	}
	if(isNull(type)){
		isNew=true;
	}
	if(isNull(mode)){
		isNew=true;
	}
	if(isNull(billCyc)){
		isNew=true;
	}
	if(isNull(payTerm)){
		isNew=true;
	}
	return isNew;	
}

function validateFromAndToDates(){
	fromDate = getDomElementById("validFromDate");
	toDate = getDomElementById("validToDate");
	
	var i=compareDates(fromDate.value, toDate.value);
	if(i==undefined || i==0 || i==1){
		alert("Valid Till date should be greater than Effective From date.");
		
		setTimeout(function(){toDate.focus();}, 10);
		toDate.value="";
		return false;
	}
	
	return true;
}

function enableOrDisableBillingButtons(){
	
	var contractStatus="";
	var isNew="";
	var userType = "";
	if(!isNull(getDomElementById("contractStatus"))){
		contractStatus = getDomElementById("contractStatus").value;
		isNew = getDomElementById("isNew").value;
		userType = getDomElementById("userType").value;
	}
	if((!isNull(contractStatus) && contractStatus == "C" && !isNull(isNew) && isNew == "true") 
		/*	|| (!isNull(userType) && userType == "S" && !isNull(contractStatus) && 
					( contractStatus == "S" || contractStatus == "A"))*/
			)
	{
		//buttonDisabled("renewBtn","btnintform");
		//jQuery("#" +"renewBtn").addClass("btnintformbigdis");
		
		//buttonDisabled("printBtn","btnintform");
		//jQuery("#" +"printBtn").addClass("btnintformbigdis");
		
		//buttonEnabled("submitBtn","btnintform");
		//jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
		
		buttonEnabled("saveBillingBtn","btnintform");
		jQuery("#" +"saveBillingBtn").removeClass("btnintformbigdis");
		buttonEnabled("cancelBillingBtn","btnintform");
		jQuery("#" +"cancelBillingBtn").removeClass("btnintformbigdis");
	}
	else if(!isNull(contractStatus) && 
			( contractStatus == "S" || contractStatus == "A" || contractStatus == "I" || contractStatus == "B")){
		
		//if(checkRenewDates() && (getDomElementById("rnewContract").value != "true") ){
		//	buttonEnabled("renewBtn","btnintform");
		//	jQuery("#" +"renewBtn").removeClass("btnintformbigdis");			
		//}else{
		//	buttonDisabled("renewBtn","btnintform");
		//	jQuery("#" +"renewBtn").addClass("btnintformbigdis");
		//}
		//buttonEnabled("printBtn","btnintform");
		//jQuery("#" +"printBtn").removeClass("btnintformbigdis");
		
		//buttonDisabled("submitBtn","btnintform");
		//jQuery("#" +"submitBtn").addClass("btnintformbigdis");
		
		buttonDisabled("saveBillingBtn","btnintform");
		jQuery("#" +"saveBillingBtn").addClass("btnintformbigdis");
		buttonDisabled("cancelBillingBtn","btnintform");
		jQuery("#" +"cancelBillingBtn").addClass("btnintformbigdis");

	}
}

function checkRenewDates(){
	
	validDate = document.getElementById("validFromDate").value;
	
	
	var validDateDay = parseInt(validDate.substring(0, 2), 10);
	var validDateMon = parseInt(validDate.substring(3, 5), 10);
	var validDateYr = parseInt(validDate.substring(6, 10), 10);
	
	var fromDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
	
	var toDayDate = new Date();
	toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
	
	
	if(fromDate <= toDayDate)
	return true;
	
	return false;
	
}

/**
 * To get contract status
 * @returns contractStatus
 */
function getContractStatus(){
	return getDomElementById("contractStatus").value;
}
/**
 * To disabled button
 * @param btnId
 */
function btnDisable(btnId){
	buttonDisabled(btnId, 'btnintformbigdis');
}
/**
 * To enable button
 * @param btnId
 */
function btnEnable(btnId){
	buttonEnabled(btnId,'btnintform');
	jQuery('#'+btnId).removeClass('btnintformbigdis');
}

/**
 * To validate enable or disable for all fields/elements by contract status
 * calling function: 
 * 			callBackForSearchBillingDtls, 
 * 			start up on page load in jsp,
 */
function validateAllEnableOrDisabled(){
	var contractStatus = getContractStatus();
	var userType = getDomElementById("userType").value;
	
	if(contractStatus == CON_STATUS_CREATED) 
	{  //Created
		ED_BillingTab(false);
		//ED_PickupDlvTab(false);
		allBtnEnabled();
		disableFieldsForRenewedContract();
	} 
	else if(contractStatus == CON_STATUS_SUBMITTED) 
	{  //Submitted
		ED_BillingTab(true);
		ED_PickupDlvTab(true);
		allBtnDisabled();
		ED_Submit();		
	} 
	else if(contractStatus == ACTIVE) 
	{   //Active
		if (!isNull(userType) && userType == "S")
		{
			ED_BillingTab(false);
			ED_PickupDlvTab(false);
			allBtnEnabled();
			ED_Active();
			disableFieldsForSuperEditUser();
		}
		else 
		{
		  ED_BillingTab(true);
		  ED_PickupDlvTab(true);
		  allBtnDisabled();
		  ED_Active();
		  ED_Submit();	
		}
	} 
	else if(contractStatus == INACTIVE) {//Inactive
		ED_BillingTab(true);
		ED_PickupDlvTab(true);
		allBtnDisabled();
		//ED_Inactive();
	} 
	else if(contractStatus == CON_STATUS_BLOCKED) {//Block
		ED_BillingTab(true);
		ED_PickupDlvTab(true);
		allBtnDisabled();
		ED_Block();
		ED_Submit();	
	}
}

/**
 * To enable or disable billing tab elements
 * @param flag
 */
function ED_BillingTab(flag){
	getDomElementById("billingContractType").disabled = flag;
	getDomElementById("typeOfBilling").disabled = flag;
	getDomElementById("modeOfBilling").disabled = flag;
	getDomElementById("billingCycle").disabled = flag;
	getDomElementById("paymentTerm").disabled = flag;
	getDomElementById("panNo").disabled = flag;
	getDomElementById("tanNo").disabled = flag;
	//getDomElementById("soldToCode").disabled = flag;
	if(!flag){
		configureSoldToCode();
	}else{
		getDomElementById("soldToCode").disabled = flag;
	}
	if(flag){
		$("#validFromDt").hide();
		$("#validToDt").hide();
	} else {
		$("#validFromDt").show();
		$("#validToDt").show();
	}
}
/**
 * To enable or disable pickup/delivery tab elements
 * @param flag
 */
function ED_PickupDlvTab(flag) {
	//Pickup
	if(!isNull(getDomElementById(NORM_PICKUP_TABLE))){//Normal
		var tabId = NORM_PICKUP_TABLE;
		$("#"+tabId).find("input").attr("disabled", flag);
		$("#"+tabId).find("select").attr("disabled", flag);
		$("#checkAll").attr("disabled", flag);
	} else if(!isNull(getDomElementById(ECOM_PICKUP_TABLE))){//ECommerce
		var tabId = ECOM_PICKUP_TABLE;
		$("#"+tabId).find("input").attr("disabled", flag);
		$("#"+tabId).find("select").attr("disabled", flag);
		$("#checkAll").attr("disabled", flag);
	}
	//Delivery
	if(!isNull(getDomElementById(NORM_DLV_TABLE))){//Normal
		var tabId = NORM_DLV_TABLE;
		$("#"+tabId).find("input").attr("disabled", flag);
		$("#"+tabId).find("select").attr("disabled", flag);
		$("#checkAllDlv").attr("disabled", flag);
	}
}
/**
 * To disable all buttons of Billing, Pickup and Delivery Tab..
 */
function allBtnDisabled(){
	//Bill
	btnDisable("saveBillingBtn");//Save
	btnDisable("cancelBillingBtn");//Cancel
	//btnDisable("renewBtn");//Renew
	//btnDisable("printBtn");//Print
	//Pickup
	btnDisable("blockPickupBtn");//Block
	btnDisable("unblockPickupBtn");//Unblock
	btnDisable("editPickupBtn");//Edit
	btnDisable("addPickupBtn");//Add
	btnDisable("deletePickupBtn");//Delete
	btnDisable("savePickupBtn");//Save
	btnDisable("cancelPickupBtn");//Cancel
	//Delivery
	if(!isNull(getDomElementById("saveDlvBtn"))){
		btnDisable("blockDlvBtn");//Block
		btnDisable("unblockDlvBtn");//Unblock
		btnDisable("editDlvBtn");//Edit
		btnDisable("addDlvBtn");//Add
		btnDisable("deleteDlvBtn");//Delete
		btnDisable("saveDlvBtn");//Save
		btnDisable("cancelDlvBtn");//Cancel
	}
}
/**
 * To enable all buttons of Billing, Pickup and Delivery Tab..
 */
function allBtnEnabled(){
	//Bill
	btnEnable("saveBillingBtn");//Save
	btnEnable("cancelBillingBtn");//Cancel
	//btnDisable("submitBtn");//Submit
	//btnDisable("renewBtn");//Renew
	//btnDisable("printBtn");//Print
	//Pickup
	btnDisable("blockPickupBtn");//Block
	btnDisable("unblockPickupBtn");//Unblock
	btnDisable("editPickupBtn");//Edit
	type=getTypeOfBilling();
	if(type == DD){
		btnDisable("addPickupBtn");//Add
	}else{
		btnEnable("addPickupBtn");//Add
	}
	btnEnable("deletePickupBtn");//Delete
	btnEnable("savePickupBtn");//Save
	btnEnable("cancelPickupBtn");//Cancel
	//Delivery
	if(!isNull(getDomElementById("saveDlvBtn"))){
		btnDisable("blockDlvBtn");//Block
		btnDisable("unblockDlvBtn");//Unblock
		btnDisable("editDlvBtn");//Edit
		btnEnable("addDlvBtn");//Add
		btnEnable("deleteDlvBtn");//Delete
		btnEnable("saveDlvBtn");//Save
		btnEnable("cancelDlvBtn");//Cancel
	}
}
/**
 * To enable or disable buttons for submitted status
 */
function ED_Submit(){
	//btnEnable("printBtn");
	btnEnable("editPickupBtn");
	if(!isNull(getDomElementById("editDlvBtn"))){
		btnEnable("editDlvBtn");
	}
}
/**
 * To enable or disable buttons for active status
 */
function ED_Active(){
	//btnEnable("printBtn");
	//btnEnable("renewBtn");
	btnEnable("blockPickupBtn");
	if(!isNull(getDomElementById("blockDlvBtn"))){
		btnEnable("blockDlvBtn");
	}
}
/**
 * To enable or disable buttons for blocked status
 */
function ED_Block(){
	btnEnable("unblockPickupBtn");
	if(!isNull(getDomElementById("unblockDlvBtn"))){
		btnEnable("unblockDlvBtn");
	}
}

function configureSoldToCode(){
	var billingType = getDomElementById("typeOfBilling").value;
	var cStatus = getDomElementById("contractStatus").value;
	var isNew = getDomElementById("isNew").value;
	if(billingType == "DBCP"){
		if(cStatus == "C" &&  !isNull(isNew) && (isNew == "false")){
			document.getElementById("soldToCode").disabled = false;			
		}else{
			document.getElementById("soldToCode").disabled = true; 
		}		
	}else{
		if(cStatus == "C" && !isNull(isNew) && isNew == "false"){
			getDomElementById("soldToCode").value = "";
		}
		document.getElementById("soldToCode").disabled = true;		
	}	
}

function configureCustomerCode(){
	var billingType = getDomElementById("typeOfBilling").value;
	var cnrtType = getDomElementById("billingContractType").value;
	if(billingType == "DBCP"){
	if(!isNull(cnrtType) && cnrtType == "N"){
		readOnlyFields(rowCountP, "customerCode", false);
	}else if(!isNull(cnrtType) && cnrtType == "R"){
		readOnlyFields(rowCountD, "customerCodeDlv", false);
	}
	}else{
		if(!isNull(cnrtType) && cnrtType == "N"){
			readOnlyFields(rowCountP, "customerCode", true);
		}else if(!isNull(cnrtType) && cnrtType == "R"){
			readOnlyFields(rowCountD, "customerCodeDlv", true);
		}
	}
}

function readOnlyFields(rowCnt, field, disabled){
	var rCnt = rowCnt;
	var cStatus = getDomElementById("contractStatus").value;
	var isNew = getDomElementById("isNew").value;
	for(var i=1 ; i<rCnt ; i++){
		if(!isNull(getDomElementById(field+i))){
			if(disabled){
				getDomElementById(field+i).setAttribute('readonly', 'readonly');
				if(cStatus == "C"){					
					if(isNull(isNew) || (isNew == "false")){
						getDomElementById(field+i).value="";
					}
				}
			}else if(getDomElementById(field+i).hasAttribute('readonly')){
					if(cStatus == "C"){
						if(isNull(isNew) || (isNew == "false")){
							getDomElementById(field+i).removeAttribute('readonly');
						}
					}
			}else{
				getDomElementById(field+i).setAttribute('readonly', 'readonly');					
				if(cStatus == "C"){
					if(isNull(isNew) || (isNew == "false")){
						getDomElementById(field+i).removeAttribute('readonly');
					}
				}
			}
		}	
	}
}

function cancelBillingDetails(){
	rateBillingDtlsStartup();
}

function enableSpocTabs(){
	if(getDomElementById("rateQuotationType").value == "E"){
		enableTabs(6);
		enableTabs(7);
	}else{
		enableTabs(7);
		enableTabs(8);
	}
}


function vldPickupDlvTab(){
	//var PICKUP_TABLE='pickupDetailsTable';
	//var DLV_TABLE='deliveryDetailsTable';
	var PICKUP_TABLE = getTableIdByPickupDlvType(PICKUP_CONTRACT_TYPE);
	var DLV_TABLE = getTableIdByPickupDlvType(DLV_CONTRACT_TYPE);
	var cnrtType = getDomElementById("billingContractType").value;
	var quotType = getDomElementById("rateQuotationType").value;
	
	if(cnrtType==NORMAL_CON){
		if(quotType == "N"){
			enableTabs(5);//Pickup
			disableTabs(6);//Delivery
		}else{
			enableTabs(5);//Pickup
		}
		jQuery("#pickupDlvType").val(PICKUP_CONTRACT_TYPE);
		if(getRateContractType()==NORM){
			clearTable(DLV_TABLE);
		}
		clearTable(PICKUP_TABLE);
		validatePickupDlvTabs();
		//addRows(PICKUP_TABLE);
	} else if(cnrtType==REVS_LOGI_CON){
		disableTabs(5);//Pickup
		enableTabs(6);//Delivery
		jQuery("#pickupDlvType").val(DLV_CONTRACT_TYPE);
		clearTable(PICKUP_TABLE);
		if(getRateContractType()==NORM){
			clearTable(DLV_TABLE);
		}
		validatePickupDlvTabs();
		//addRowsDlv(DLV_TABLE);
	}
	
	//configureCustomerCode();
}

function validateBillingType(){
	
	var cnrtType = getDomElementById("billingContractType").value;
	if(!isNull(cnrtType) && cnrtType == "N"){
		var rCnt = rowCountP;
		for(var i=1 ; i<rCnt ; i++){
			if(!isNull(getDomElementById("customerCode"+i))
					&& isNull(getDomElementById("customerCode"+i).value)){
				alert("Please fill Customer Codes in Pickip Details tab");
				return false;
			}
			
		}
	}else if(!isNull(cnrtType) && cnrtType == "R"){
		var rCnt = rowCountD;
		for(var i=1 ; i<rCnt ; i++){
			if(!isNull(getDomElementById("customerCodeDlv"+i))
					&& isNull(getDomElementById("customerCodeDlv"+i).value)){
				alert("Please fill Customer Codes in Delivery Details tab");
				return false;
			}
			
		}
	}
	return true;
}

function validateBillingType(){
	var contractType = getDomElementById("billingContractType").value;
	if( contractType == "R"){
		var billingType = getDomElementById("typeOfBilling").value;
		if(billingType != "CBCP"){
			alert("Please select Type of Biling as Centralized billing centralized payment");
			return false;
		}
	}
	return true;
}


function validateDateRange(fromDate,toDate){
	var fDate = getDomElementById(fromDate).value;
	var tDate = getDomElementById(toDate).value;
	
	var validDateDay = parseInt(tDate.substring(0, 2), 10);
	var validDateMon = parseInt(tDate.substring(3, 5), 10);
	var validDateYr = parseInt(tDate.substring(6, 10), 10);
	
	var validfromDateDay = parseInt(fDate.substring(0, 2), 10);
	var validfromDateMon = parseInt(fDate.substring(3, 5), 10);
	var validfromDateYr = parseInt(fDate.substring(6, 10), 10)+1;
	
	var fromDate = new Date(validfromDateYr, (validfromDateMon - 1), validfromDateDay);
	var toDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
	
	fromDate.setDate(fromDate.getDate()-1);
	
	if(toDate > fromDate){
		alert("Valid Till date should not exceed one year than Effective From date.");
		return false;
	}
	
	return true;
}

function validateDates(fromDate, toDate){
	/*var fDate = getDomElementById(fromDate).value;
	var tDate = getDomElementById(toDate).value;
	if(!isNull(fDate) && !isNull(tDate)){
		var url='./rateContract.do?submitName=validateContractDates&fromDate='+fDate+'&toDate='+tDate;
		ajaxCall(url, RATE_CON_FORM, callBackForValidateDates);//RATE_CON_FORM - Global var.
	}*/
}
//call back function for validateDates
function callBackForValidateDates(ajaxResp) {
if (!isNull(ajaxResp)) {
	var responseText = jsonJqueryParser(ajaxResp);
	var error = responseText[ERROR_FLAG];
	if (responseText != null && error != null) {
		alert(error);
		getDomElementById("validToDate").value = "";
	} 
}
}

function deletePickupDelTableRows(tableId){
	$('#' + tableId).dataTable().fnClearTable();
}


function validateSoldToCode(){
	var type=getTypeOfBilling();
	if(type == "DBCP"){
		var soldToCode = getDomElementById("soldToCode").value;
		if(soldToCode.length != 10){
			alert('Sold To Code should always be 10 characters long');
			return false;
		}
		else{
			return true;
		}
	}
	return true;
}

/*
 * The below functions have been added to prevent the users from changing the from date of the contract and also
 * the type of billing.
 * This has been done to avoid issues in billing.
 * */
function disableFieldsForRenewedContract() {
	if (!isNull(originatedRateContractId)) {
		getDomElementById("typeOfBilling").disabled = true;
	}
}

function disableFieldsForSuperEditUser() {
	if (!isNull(userType) && userType == "S") {
		$("#validFromDt").hide();
		getDomElementById("validFromDate").disabled = true;
		getDomElementById("typeOfBilling").disabled = true;
	}
}