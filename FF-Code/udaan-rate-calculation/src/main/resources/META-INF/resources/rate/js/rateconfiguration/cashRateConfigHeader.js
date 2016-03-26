var priorityRatesTO;
var sector;
var statesList;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";

/************ Date Validation START ************/
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
		//var i=compareDates(todayDate, obj.value);
		//if(i==undefined || i==0 || i==1){
		if(!validDate(obj.value)){
			alert("From Date Should Be Greater Than Today Date");
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			return false;
		}
		validateToDate(getDomElementById("toDateStr"));
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
		var fromDate=getDomElementById("fromDateStr").value;
		var i=compareDates(fromDate,obj.value);
		if(i==undefined || i==0 || i==1){
			alert("To Date Should Be Greater Than From Date");
			setTimeout(function(){obj.focus();}, 10);
			obj.value="";
			return false;
		}
	}
}
/**
 * To Validate From Date before setting Valid To Date
 * 
 * @param toDtId
 * @param toDtValue
 */
function validateFromDtToSetToDt(toDtId, toDtValue) {
	var fromDate=getDomElementById("fromDateStr");
	if(!isNull(fromDate.value)){
		show_calendar(toDtId, toDtValue);
	} else {
		alert("Please First Select From Date");
		setTimeout(function(){fromDate.focus();}, 10);
		return false;
	}
}
/************ Date Validation END ************/

/**
 * On load, to add default row(s)
 */
function addDefaultRows() {
	addCourierSplDestRow();
	addAirCargoSplDestRow();
	addTrainSplDestRow();
	addPrioritySplDestRow();
}

/**
 * To validate pincode
 * 
 * @param obj
 * @returns {Boolean}
 */
function validatePincode(obj){
	var pincode=$.trim(obj.value);
	if(pincode.length==6){
		return true;
	}
	alert("Pincode should be of 6 digits.");
	obj.value="";
	setTimeout(function(){obj.focus();}, 10);
	return false;
}
var PRO_CAT_ID="";

/**
 * To get pincode details
 * 
 * @param pinObj
 * @param prodCatId
 */
function getPincodeDtls(pinObj, prodCatId) {
	var pincode=$.trim(pinObj.value);
	if(!isNull(pincode) && validatePincode(pinObj)){
		PRO_CAT_ID = prodCatId;
		if (!isNull(pincode)) {
			var url = './baRateConfiguration.do?submitName=getPincode&pincode='+pincode;
			showProcessing();
			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBackPincodeDtls(req, pinObj);
				}
			});
		}
	}
}
//call back function for getPincodeDtls().
function printCallBackPincodeDtls(data, pinObj) {
	var rowId = getRowId(pinObj, "pincodes"+PRO_CAT_ID);
	if (data=="INVALID") {
		alert("Invalid Pincode.");
		setTimeout(function(){pinObj.focus();}, 10);
		pinObj.value = "";
		getDomElementById("pincodeIds"+PRO_CAT_ID+rowId).value="";
		getDomElementById("cityNames"+PRO_CAT_ID+rowId).value="";
		getDomElementById("cityIds"+PRO_CAT_ID+rowId).value="";
		hideProcessing();
	} else {
		var req = jsonJqueryParser(data);
		getDomElementById("pincodeIds"+PRO_CAT_ID+rowId).value = req.pincodeId;
		getCityDtls(pinObj,PRO_CAT_ID);
	}
}

/**
 * To get city details
 * 
 * @param pinObj
 * @param prodCatId
 */
function getCityDtls(pinObj,prodCatId) {
	PRO_CAT_ID = prodCatId;
	var pincode=$.trim(pinObj.value);
	if (!isNull(pinObj.value)) {
		var url = './baRateConfiguration.do?submitName=getCityName&pincode='+pincode;
		//showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackCityDtls(req, pinObj);
			}
		});
	}
}
//call back function for getCityDtls().
function printCallBackCityDtls(data, pinObj) {
	var rowId = getRowId(pinObj, "pincodes"+PRO_CAT_ID);
	if (data == "INVALID") {
		alert("Invalid Pincode.");
		setTimeout(function(){pinObj.focus();}, 10);
		pinObj.value="";
		getDomElementById("pincodeIds"+PRO_CAT_ID+rowId).value="";
		getDomElementById("cityNames"+PRO_CAT_ID+rowId).value="";
		getDomElementById("cityIds"+PRO_CAT_ID+rowId).value="";
	} else {
		var req = jsonJqueryParser(data);
		getDomElementById("cityNames"+PRO_CAT_ID+rowId).value = req.cityName;
		getDomElementById("cityIds"+PRO_CAT_ID+rowId).value = req.cityId;
	}
	hideProcessing();
}

/**
 * To set weight slab rate
 * 
 * @param prodCatId
 * @param rows
 * @param cols
 * @param slabRate
 */
function setWtSlabRate(prodCatId, rows, cols, slabRate, servicedOn) {
	var flag=0;
	for(var r=1; r<=rows; r++){
		for(var c=1; c<=cols; c++){
			var wtSlab = jQuery("#weightSlabIds"+prodCatId+r+c).val();
			var destSec = jQuery("#destSectorIds"+prodCatId+r+c).val();
			if(((isNull(servicedOn) || (!isNull(servicedOn) && (servicedOn == slabRate.servicedOn))) && wtSlab==slabRate.weightSlabId 
					&& destSec==slabRate.destinationSectorId)){
				jQuery("#slabRateIds"+prodCatId+r+c).val(slabRate.slabRateId);
				jQuery("#slabRates"+prodCatId+r+c).val(slabRate.slabRate);
				flag=1;
				break;
			}
		}//END FOR LOOP - cols
		if(flag==1){
			break;
		}
	}//END FOR LOOP - rows
}

/**
 * To set special destination rate
 * 
 * @param prodCatId
 * @param rows
 * @param cols
 * @param splDest
 */
function setSplDestRate(prodCatId, prodCode, rows, cols, splDest, servicedOn) {
	n = 1;
	l = 0;
	c = 1;
	var isExist = false;
	
	for(var r=1; r<=rows; r++){
		
		if(prodCode == "CO"){
			
			if(rows != (rowCount_CO-2)){
				addCourierSplDestRow();
			}	
		}else if(prodCode == "TR"){
			
			if(rows != (rowCount_TR-2)){
				addTrainSplDestRow();
			}	
		}else if(prodCode == "AR"){
			
			if(rows != (rowCount_AR-2)){
				addAirCargoSplDestRow();
			}	
		}else if(prodCode == "PR"){
			if(rows != (rowCount_PR-2)){
				addPrioritySplDestRow();
			}	
		}
		
		
		for(var k=0;k<splDest.length;k++){
			isExist = false;
			if(((isNull(servicedOn) && isNull(splDest[k].servicedOn)) || (!isNull(servicedOn) && !isNull(splDest[k].servicedOn) &&  (splDest[k].servicedOn == servicedOn)))){
				if(c==1){
					getDomElementById("stateId"+prodCatId+r).value = splDest[k].stateId;
					
					clearCityDropDownList("cityIds"+prodCatId+r);	
					
					loadCityDropDown("cityIds"+prodCatId+r, "", splDest[k].cityList);
					if(!isNull(splDest[k].cityTO)){
						getDomElementById("cityIds"+prodCatId+r).value = splDest[k].cityTO.cityId;
					}
					
					c++;
					break;				
				}else{
					
					for(var b=1;b<c;b++){
						if(getDomElementById("stateId"+prodCatId+b).value == splDest[k].stateId 
								&& (isNull(splDest[k].cityTO) || ((!isNull(splDest[k].cityTO)) && (getDomElementById("cityIds"+prodCatId+b).value == splDest[k].cityTO.cityId)))){
							isExist = true;
							break;
						}
					}
					if(!isExist){
						getDomElementById("stateId"+prodCatId+r).value = splDest[k].stateId;
						clearCityDropDownList("cityIds"+prodCatId+r);
						loadCityDropDown("cityIds"+prodCatId+r, "", splDest[k].cityList);
						if(!isNull(splDest[k].cityTO)){
							getDomElementById("cityIds"+prodCatId+r).value = splDest[k].cityTO.cityId;
						}
						c++;
						break;
					}
				
				}
			}
		}
	}
		for(var r=1; r<=rows; r++){
		m = 2;
		for(var j = 1; j<=cols ; j++){
			//alert("coWtSlabId"+prodCatId+j);
			state = getDomElementById("stateId"+prodCatId+r).value;
			city = getDomElementById("cityIds"+prodCatId+r).value;
			var wtSlab = getDomElementById("weightSlabIds"+prodCatId+"1"+j).value;
			
			if(!isNull(wtSlab)){
				
				for(var k=0;k<splDest.length;k++){
					if((state == splDest[k].stateId) && ((isNull(city) && isNull(splDest[k].cityTO)) || ((!isNull(city) && (!isNull(splDest[k].cityTO)) && city == splDest[k].cityTO.cityId))) 
							&& (splDest[k].weightSlabId == wtSlab)
							&& ((isNull(servicedOn) && isNull(splDest[k].servicedOn)) || (!isNull(servicedOn) && !isNull(splDest[k].servicedOn) &&  (splDest[k].servicedOn == servicedOn)))){
					getDomElementById("specialDestRates"+prodCatId+n+m).value = splDest[k].slabRate;
					getDomElementById("specialDestIds"+prodCatId+n+m).value = splDest[k].specialDestId;									
					break;
					}
				}
			}
			m++;
		}
		
		//addCourierSpecialDestinationRow();
			n++;
		
		//END FOR LOOP - cols
		
	}//END FOR LOOP - rows
}

var isSaveClicked = false;

/**
 * To validate header details
 * 
 * @returns {Boolean}
 */
function validateHeader(){
	var msg = "Please provide : ";
	var isValid = true;
	var fromDate = getDomElementById("fromDateStr");
	var toDate = getDomElementById("toDateStr");
	var region = getDomElementById("regionId");
	var focusObj = fromDate;
	if(isSaveClicked && isNull(fromDate.value)){
		if(isValid)	focusObj = fromDate;
		msg = msg + ((!isValid)?", ":"") + "From Date";
		isValid=false;
	}
	if(isSaveClicked && isNull(toDate.value)){
		if(isValid)	focusObj = toDate;
		msg = msg + ((!isValid)?", ":"") + "To Date";
		isValid=false;
	}
	if(isNull(region.value)){
		if(isValid)	focusObj = region;
		msg = msg + ((!isValid)?", ":"") + "Region";
		isValid=false;
	}
	if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	isSaveClicked = false;
	return isValid;
}

var isCancel=false;
var isSearchBtn=false;

/**
 * To search rate cash rate product details onclick of search button
 */
function searchCashRateDtls(){
	isSearchBtn=true;
	searchCashRateProductDtls(PRO_CODE_COURIER);
	//searchCashRateProductDtls(PRO_CODE_COURIER);
}

/**
 * To search cash rate product details
 * 
 * @param productCode
 */
function searchCashRateProductDtls(productCode){
	if(validateHeader()){
		getDomElementById("regionId").disabled = false;
		var url = "./cashRateConfiguration.do?submitName=searchCashRateProductDtls"
			+ "&productCode=" + productCode;
		callAjaxFunction(productCode, url);
		//getDomElementById("regionId").disabled = true;
	}
}
//call back function for searchCashRateProductDtls (In cashRateConfigHeader.js)
function printAllCashRateDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		getDomElementById("regionId").disabled = true;
		showProcessing();
		var data = jsonJqueryParser(ajaxResp);
		
		//Default it shows Courier tab
		$("#tabsnested").tabs("option", "active", 0);
		
		//Header
		jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
		//set exact dates
		jQuery("#fromDateStr").val(data.fromDateStr);
		jQuery("#toDateStr").val(data.toDateStr);
		//header status
		jQuery("#headerStatus").val(data.headerStatus);
		jQuery("#priorityservicedOn").val("");
		
		//TO disable all if headerStatus "A" - Submitted
		var hStatus = jQuery("#headerStatus").val();
		if(hStatus==ACTIVE){
			disableAllForSubmit();
			$("#fromDtStr").hide();
			$("#toDtStr").hide();
		}
		
		//Product
		for(var i=0; i<data.cashRateProductTOList.length; i++){
			var product = data.cashRateProductTOList[i];
			if(product.productCode == PRO_CODE_COURIER){/***** COURIER *****/
				jQuery("#coHeaderProMapId").val(product.headerProductMapId);
				//jQuery("#coProdCatId").val(product.productId);
				var prodCatId = jQuery("#coProdCatId").val();
				var cols = jQuery("#coSplDestColCount").val();
				var rows = jQuery("#coSectorRowCount").val();
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				setFlags(data);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//Special Destination
				/*if(rowCount_CO-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addCourierSplDestRow();
					}
				}*/
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(prodCatId, "CO", rowCnt, parseInt(cols), splDest, null);
				//}
			} else if (product.productCode == PRO_CODE_AIR_CARGO) {/***** AIR CARGO *****/
				jQuery("#arHeaderProMapId").val(product.headerProductMapId);
				//jQuery("#arProdCatId").val(product.productId);
				var prodCatId = jQuery("#arProdCatId").val();
				var cols = jQuery("#arSplDestColCount").val();
				var rows = jQuery("#arSectorRowCount").val();
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				jQuery("#airCargoMinChargeableWeight").val(product.minChargeableWeight);
				setFlags(data);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//Origin Sector
					if(isNull(jQuery("#airCargoOriginSectorId").val())){
						jQuery("#airCargoOriginSectorId").val(slabRate.originSectorId);
					}
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//Special Destination
				/*if(rowCount_AR-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addAirCargoSplDestRow();
					}
				}*/
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(prodCatId, "AR", rowCnt, parseInt(cols), splDest, null);
				//}
			} if(product.productCode == PRO_CODE_TRAIN) {/***** TRAIN *****/
				jQuery("#trHeaderProMapId").val(product.headerProductMapId);
				//jQuery("#trProdCatId").val(product.productId);
				var prodCatId = jQuery("#trProdCatId").val();
				var cols = jQuery("#trSplDestColCount").val();
				var rows = jQuery("#trSectorRowCount").val();
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				jQuery("#trainMinChargeableWeight").val(product.minChargeableWeight);
				setFlags(data);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//Origin Sector
					if(isNull(jQuery("#trainOriginSectorId").val())){
						jQuery("#trainOriginSectorId").val(slabRate.originSectorId);
					}
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//Special Destination
				/*if(rowCount_TR-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addTrainSplDestRow();
					}
				}*/
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(prodCatId, "TR", rowCnt, parseInt(cols), splDest, null);
				//}
			} /*else if(product.productCode == PRO_CODE_PRIORITY) {*//***** PRIORITY *****//*
				jQuery("#prHeaderProMapId").val(product.headerProductMapId);
				//jQuery("#prProdCatId").val(product.productId);
				var prodCatId = jQuery("#prProdCatId").val();
				var cols = jQuery("#prSplDestColCount").val();
				var rows = jQuery("#prSectorRowCount").val();
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				//Serviced On
				jQuery("#priorityservicedOn").val(data.servicedOn);
				
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate);
				}
				
				//Special Destination
				if(rowCount_PR-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addPrioritySplDestRow();
					}
				}
				for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList[k];
					//set splDest Id.s
					setSplDestRate(prodCatId, parseInt(rowCount_PR), parseInt(cols), splDest);
				}
			}*/
		}
		hideProcessing();
	} else {
		if(isSearchBtn){
			if(getDomElementById("isRenew").value==YES){
				getDomElementById("regionId").disabled = true;
			} else { 
				alert("No Such Details Found.");
			}
			isSearchBtn=false;
		}
		isCancel = false;
	}
	disEnableSector("destSectorIds", "slabRates", "coProdCatId", "coSectorRowCount", "coSplDestColCount",  true, false);
}
//call back function for searchCashRateProductDtls (In cashRateConfigHeader.js)
function printCourierCashRateDtls(ajaxResp){
	enableTabs("tabs",2);
	enableTabs("tabsnested",3);
	enableTabs("tabsnested1",1);
	btnEnable("saveBtn1");
	btnEnable("cancelBtn1");
	if(!isNull(ajaxResp)){
		getDomElementById("regionId").disabled = true;
		showProcessing();
		var data = jsonJqueryParser(ajaxResp);		
		//Default it shows Courier tab
		$("#tabsnested").tabs("option", "active", 0);
		
		//Header
		jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
		//set exact dates
		jQuery("#fromDateStr").val(data.fromDateStr);
		jQuery("#toDateStr").val(data.toDateStr);
		//header status
		jQuery("#headerStatus").val(data.headerStatus);
		var hStatus = jQuery("#headerStatus").val();
		if(hStatus==ACTIVE){
			disableAllForSubmit();
			$("#fromDtStr").hide();
			$("#toDtStr").hide();
		}
		//Product
		for(var i=0; i<data.cashRateProductTOList.length; i++){
			var product = data.cashRateProductTOList[i];
			if(product.productCode == PRO_CODE_COURIER){
				jQuery("#coHeaderProMapId").val(product.headerProductMapId);
				enableTabs("tabsnested",5);
				if(!isCancel){
					jQuery("#arHeaderProMapId").val("");
					jQuery("#trHeaderProMapId").val("");
					jQuery("#prHeaderProMapId").val("");
				}
				setFlags(data);
				isCancel=false;
				//jQuery("#coProdCatId").val(product.productId);
				var prodCatId = jQuery("#coProdCatId").val();
				var cols = jQuery("#coSplDestColCount").val();
				var rows = jQuery("#coSectorRowCount").val();
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//alert(rowCnt);
				//alert(rowCount_AR);
				//Special Destination
				//if(rowCount_CO-1<rowCnt){
					//Add row(s) before setting the value(s)
				
					//for(var l=0; l<rowCnt; l++){
					//	addCourierSplDestRow();
					//}
				//}
				//for(var k=0; k<product.specialDestTOList.length; k++){
				//	var splDest = product.specialDestTOList[k];
					//set splDest Id.s
					var splDest = product.specialDestTOList;
					setSplDestRate(prodCatId, "CO", rowCnt, parseInt(cols), splDest, null);
				//}
			}
		}
		hideProcessing();
	} else {
		if(isSearchBtn){
			alert("No Such Details Found.");
			isSearchBtn=false;
		}
		isCancel = false;
	}
	if(isNull(sector)){
		sector = jQuery("#orgSectorId").val();
	}
	disEnableSector("destSectorIds", "slabRates", "coProdCatId", "coSectorRowCount", "coSplDestColCount", true, false);	
}
//call back function for searchCashRateProductDtls (In cashRateConfigHeader.js)
function printAirCargoCashRateDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		getDomElementById("regionId").disabled = true;
		showProcessing();
		var data = jsonJqueryParser(ajaxResp);
		//Header
		jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
		//Product
		for(var i=0; i<data.cashRateProductTOList.length; i++){
			var product = data.cashRateProductTOList[i];
			if(product.productCode == PRO_CODE_AIR_CARGO){
				jQuery("#arHeaderProMapId").val(product.headerProductMapId);
				enableTabs("tabsnested",5);
				//jQuery("#arProdCatId").val(product.productId);
				var prodCatId = jQuery("#arProdCatId").val();
				var cols = jQuery("#arSplDestColCount").val();
				var rows = jQuery("#arSectorRowCount").val();
				setFlags(data);
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				jQuery("#airCargoMinChargeableWeight").val(product.minChargeableWeight);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//Origin Sector
					if(isNull(jQuery("#airCargoOriginSectorId").val())){
						jQuery("#airCargoOriginSectorId").val(slabRate.originSectorId);
					}
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//Special Destination
				/*if(rowCount_AR-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addAirCargoSplDestRow();
					}
				}*/
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(prodCatId, "AR", rowCnt, parseInt(cols), splDest, null);
				//}
			}
		}
		hideProcessing();
	} else {
		//alert("No Such Details Found.");
	}
}
//call back function for searchCashRateProductDtls (In cashRateConfigHeader.js)
function printTrainCashRateDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		getDomElementById("regionId").disabled = true;
		showProcessing();
		var data = jsonJqueryParser(ajaxResp);
		//Header
		jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
		
		//Product
		for(var i=0; i<data.cashRateProductTOList.length; i++){
			var product = data.cashRateProductTOList[i];
			if(product.productCode == PRO_CODE_TRAIN){
				jQuery("#trHeaderProMapId").val(product.headerProductMapId);
				enableTabs("tabsnested",5);
				//jQuery("#trProdCatId").val(product.productId);
				var prodCatId = jQuery("#trProdCatId").val();
				var cols = jQuery("#trSplDestColCount").val();
				var rows = jQuery("#trSectorRowCount").val();
				setFlags(data);
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				jQuery("#trainMinChargeableWeight").val(product.minChargeableWeight);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//Origin Sector
					if(isNull(jQuery("#trainOriginSectorId").val())){
						jQuery("#trainOriginSectorId").val(slabRate.originSectorId);
					}
					//set slabRate Id.s
					setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, null);
				}
				
				//Special Destination
				/*if(rowCount_TR-1<rowCnt){
					//Add row(s) before setting the value(s)
					for(var l=1; l<rowCnt; l++){
						addTrainSplDestRow();
					}
				}*/
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(prodCatId, "TR",  rowCnt, parseInt(cols), splDest, null);
				//}
			}
		}
		hideProcessing();
	} else {
		//alert("No Such Details Found.");
	}
}
//call back function for searchCashRateProductDtls (In cashRateConfigHeader.js)
function printPriorityCashRateDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		getDomElementById("regionId").disabled = true;
		showProcessing();
		var data = jsonJqueryParser(ajaxResp);
		//Header
		jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
		//Product
		for(var i=0; i<data.cashRateProductTOList.length; i++){
			var product = data.cashRateProductTOList[i];
			if(product.productCode == PRO_CODE_PRIORITY){
				enableTabs("tabsnested1",3);
				setFlags(data);
				priorityRatesTO = product;
				assignPriorityRates();				
			}
		}
		hideProcessing();
	} else {
		//alert("No Such Details Found.");
	}
	disEnableSector("destSectorIds", "slabRates", "prProdCatId", "prSectorRowCount", "prSplDestColCount", true, false);
}

function assignPriorityRates(){
	jQuery("#prHeaderProMapId").val(priorityRatesTO.headerProductMapId);
	//jQuery("#prProdCatId").val(product.productId);
	var prodCatId = jQuery("#prProdCatId").val();
	var cols = jQuery("#prSplDestColCount").val();
	var rows = jQuery("#prSectorRowCount").val();
	var rowCnt = parseInt(priorityRatesTO.specialDestTOList.length)/parseInt(cols);
	var servicedOn = jQuery("#priorityservicedOn").val();
	deleteTableRow("prioritySplDestTable");
	
	//Serviced On
	for(var c = 1;c<=cols; c++){
		for(var r =1; r<=rows;r++){
			jQuery("#slabRateIds"+prodCatId+r+c).val("");
			jQuery("#slabRates"+prodCatId+r+c).val("");
		}
	}
	
	
	//Wt. Slab Rate
	for(var j=0; j<priorityRatesTO.slabRateTOList.length; j++){
		var slabRate = priorityRatesTO.slabRateTOList[j];
		//set slabRate Id.s
		setWtSlabRate(prodCatId, parseInt(rows), parseInt(cols), slabRate, servicedOn);
	}
	
	//Special Destination
	//if(rowCount_PR-1<rowCnt){
		//Add row(s) before setting the value(s)
	rowCnt = 0;
	var splLen = priorityRatesTO.specialDestTOList.length;
	for(var k=0; k<splLen; k++){
		var splDest = priorityRatesTO.specialDestTOList[k];
		if(servicedOn == splDest.servicedOn){
			rowCnt++;
		}
	}
	rowCnt = rowCnt/parseInt(cols);
	if(rowCount_PR == 1){
		addPrioritySplDestRow();
	}
		/*for(var l=0; l<rowCnt; l++){
			addPrioritySplDestRow();
		}
	//}
		if(rowCount_PR == 1){
			addPrioritySplDestRow();
		}*/
	//for(var k=0; k<splLen; k++){
		var splDest = priorityRatesTO.specialDestTOList;
		//set splDest Id.s
		setSplDestRate(prodCatId, "PR", rowCnt, parseInt(cols), splDest, servicedOn);
	//}
}
/**
 * To call ajax function for different product
 * 
 * @param productCode
 */
function callAjaxFunction(productCode, url) {
	if(!isNull(productCode)){
		if (productCode == PRO_CODE_COURIER){//Courier
			ajaxJquery(url, CASH_RATE_FORM, printCourierCashRateDtls);
		} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
			ajaxJquery(url, CASH_RATE_FORM, printAirCargoCashRateDtls);
		} else if(productCode == PRO_CODE_TRAIN) {//Train
			ajaxJquery(url, CASH_RATE_FORM, printTrainCashRateDtls);
		} else if(productCode == PRO_CODE_PRIORITY) {//Priority
			ajaxJquery(url, CASH_RATE_FORM, printPriorityCashRateDtls);
		}
	} else {
		ajaxJquery(url, CASH_RATE_FORM, printAllCashRateDtls);
	}
}

/**
 * To validate pincode element Id
 * 
 * @param prodCatId
 * @param rows
 * @param pincode
 * @returns {Number}
 */
function validatePincodeElementId(prodCatId, rows, pincode){
	var rowNo = 0;
	for(var r=1; r<rows; r++){
		if(jQuery("#pincodes"+prodCatId+r).val()==pincode){
			rowNo = r;
			break;
		}
	}
	return rowNo;
}

/**
 * To check whether enter key pressed or not
 * 
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
 * To validate special destination grid details
 * 
 * @param prodCatId
 * @param rowId
 * @param colCnt
 * @returns {Boolean}
 */
function validateSplDestGrid(prodCatId, rowId, colCnt){
	var stateObj = getDomElementById("stateId"+prodCatId+rowId);
	if(isNull(stateObj.value)){
		alert("Please provide state");
		setTimeout(function(){stateObj.focus();}, 10);
		return false;
	}
	colCnt = parseInt(colCnt);
	for(var i=2; i<colCnt+2; i++) {
		var splDestRateObj = getDomElementById("specialDestRates"+prodCatId+rowId+i);
		if(isNull(splDestRateObj.value)){
			alert("Please provide all slab rate for special destination state:"+ stateObj.options[stateObj.selectedIndex].innerHTML);
			setTimeout(function(){splDestRateObj.focus();}, 10);
			return false;
		}
	}
	return true;
}

/**
 * To add new row for special destination grid
 * 
 * @param e
 * @param prodCatId
 * @param rowId
 * @param colCnt
 * @param productCode
 * @returns {Boolean}
 */
function addNewSplDestRow(e, prodCatId, rowId, colCnt, productCode) {
	if (isEnterKeyPressed(e)) {//Enter Key- 13
		if (!validateSplDestGrid(prodCatId, rowId, colCnt)) {
			return false;
		}
		var flag=0;
		rowId = parseInt(rowId);
		if(productCode == PRO_CODE_COURIER){//Courier
			if((parseInt(rowCount_CO)-1)==rowId){
				addCourierSplDestRow();
				setTimeout(function() {
					getDomElementById("stateId"+prodCatId+(rowCount_CO-1)).focus();
				}, 10);
			} else {
				flag=1;
			}
		} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
			if((parseInt(rowCount_AR)-1)==rowId){
				addAirCargoSplDestRow();
				setTimeout(function() {
					getDomElementById("stateId"+prodCatId+(rowCount_AR-1)).focus();
				}, 10);
			} else {
				flag=1;
			}
		} else if(productCode == PRO_CODE_TRAIN) {//Train
			if((parseInt(rowCount_TR)-1)==rowId){
				addTrainSplDestRow();
				setTimeout(function() {
					getDomElementById("stateId"+prodCatId+(rowCount_TR-1)).focus();
				}, 10);
			} else {
				flag=1;
			}
		} else if(productCode == PRO_CODE_PRIORITY) {//Priority
			if((parseInt(rowCount_PR)-1)==rowId){
				addPrioritySplDestRow();
				setTimeout(function() {
					getDomElementById("stateId"+prodCatId+(rowCount_PR-1)).focus();
				}, 10);
			} else {
				flag=1;
			}
		}
		if(flag==1){
			setTimeout(function() {
				getDomElementById("stateId"+prodCatId+(rowId+1)).focus();
			}, 10);
		}
	}
}

/**
 * To validate tabs girds for save by product code
 * 
 * @param productCode
 * @returns {Boolean}
 */
function validateTabGrids(productCode){
	//validate header
	if(validateHeader()){
		var prodCatId = getProductCategoryId(productCode); 
		var sectorRowCnt = getSectorRowCnt(productCode);
		var splDestColCnt = getSplDestColCnt(productCode);
		var splDestRowCnt = parseInt(getSplDestRowCnt(productCode))-1;
		//validate for sector grid
		for(var i=1; i<=sectorRowCnt; i++){
			for(var j=1; j<=splDestColCnt; j++){
				var slabRateObj = getDomElementById("slabRates"+prodCatId+i+j);
				var destSec = jQuery("#destSectorIds"+prodCatId+i+j).val();
				if (isNull(slabRateObj.value)
						&& ((productCode != PRO_CODE_COURIER && productCode != PRO_CODE_PRIORITY) 
								|| (((productCode == PRO_CODE_COURIER) || (productCode == PRO_CODE_PRIORITY)) && sector != destSec))) {
					alert("Please provide all slab rate for \'"+jQuery("#sectorNames"+prodCatId+i).val()+"\'");
					setTimeout(function(){slabRateObj.focus();}, 10);
					slabRateObj.value="";
					return false;
				}
			}
		}
		//validate for special destination
		for(var k=1; k<=splDestRowCnt; k++){
			if(k==splDestRowCnt){//validate last row
				var stateObj = getDomElementById("stateId"+prodCatId+k);
				if(isNull(stateObj.value)){
					splDestColCnt = parseInt(splDestColCnt);
					var flag=0;
					for(var l=2; l<splDestColCnt+2; l++) {
						var splDestRateObj = getDomElementById("specialDestRates"+prodCatId+k+l);
						if(isNull(splDestRateObj.value)){
							continue;
						} else {
							flag=1;
							break;
						}
					}
					if(flag==0) continue;
				}
			}
			if(!validateSplDestGrid(prodCatId, k, splDestColCnt)){
				return false;
			}
		}
		return true;
	}//END IF - Validate Header
	return false;
}

/**
 * To get sector row count
 * 
 * @param productCode
 * @returns sectorRowCnt
 */
function getSectorRowCnt(productCode){
	if (productCode == PRO_CODE_COURIER){//Courier
		return jQuery("#coSectorRowCount").val();
	} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
		return jQuery("#arSectorRowCount").val();
	} else if(productCode == PRO_CODE_TRAIN) {//Train
		return jQuery("#trSectorRowCount").val();
	} else if(productCode == PRO_CODE_PRIORITY) {//Priority
		return jQuery("#prSectorRowCount").val();
	}
}

/**
 * To get special destination count
 * 
 * @param productCode
 * @returns splDestColCnt
 */
function getSplDestColCnt(productCode){
	if (productCode == PRO_CODE_COURIER){//Courier
		return jQuery("#coSplDestColCount").val();
	} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
		return jQuery("#arSplDestColCount").val();
	} else if(productCode == PRO_CODE_TRAIN) {//Train
		return jQuery("#trSplDestColCount").val();
	} else if(productCode == PRO_CODE_PRIORITY) {//Priority
		return jQuery("#prSplDestColCount").val();
	}
}

/**
 * To get special destination row count
 * 
 * @param productCode
 * @returns {Number}
 */
function getSplDestRowCnt(productCode){
	if (productCode == PRO_CODE_COURIER){//Courier
		return rowCount_CO;
	} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
		return rowCount_AR;
	} else if(productCode == PRO_CODE_TRAIN) {//Train
		return rowCount_TR;
	} else if(productCode == PRO_CODE_PRIORITY) {//Priority
		return rowCount_PR;
	}
}

/**
 * To get product category id
 * 
 * @param productCode
 * @returns prodCatId
 */
function getProductCategoryId(productCode){
	if (productCode == PRO_CODE_COURIER){//Courier
		return jQuery("#coProdCatId").val();
	} else if(productCode == PRO_CODE_AIR_CARGO) {//Air-Cargo
		return jQuery("#arProdCatId").val();
	} else if(productCode == PRO_CODE_TRAIN) {//Train
		return jQuery("#trProdCatId").val();
	} else if(productCode == PRO_CODE_PRIORITY) {//Priority
		return jQuery("#prProdCatId").val();
	}
}

/**
 * To submit the cash rate configuration details
 */
function submitCashRateDtls(){
	var cashRateHeaderId = jQuery("#cashRateHeaderId").val();
	if(!isNull(cashRateHeaderId)){
		/*var coHeaderProMapId = jQuery("#coHeaderProMapId").val();
		var arHeaderProMapId = jQuery("#arHeaderProMapId").val();
		var trHeaderProMapId = jQuery("#trHeaderProMapId").val();
		var prHeaderProMapId = jQuery("#prHeaderProMapId").val();
		if(isNull(coHeaderProMapId) && isNull(arHeaderProMapId) && isNull(trHeaderProMapId)){
			
		if(isNull(coHeaderProMapId)){
			alert("Before Submit, Please Save Courier Rates Details.");
			return false;
		}
		else if(isNull(arHeaderProMapId)){
			alert("Before Submit, Please Save Air-Cargo Rates Details.");
			return false;
		}
		else if(isNull(trHeaderProMapId)){
			alert("Before Submit, Please Save Train Rates Details.");
			return false;
			}
		}
		else if(isNull(prHeaderProMapId)){
			alert("Before Submit, Please Save Priority Rates Details.");
			return false;
		}*/
		
		var nonPriorityRatesCheck = jQuery("#nonPriorityRatesCheck").val();
		var priorityARatesCheck = jQuery("#priorityARatesCheck").val();
		var priorityBRatesCheck = jQuery("#priorityBRatesCheck").val();
		var prioritySRatesCheck = jQuery("#prioritySRatesCheck").val();
		var nonPriorityChargesCheck = jQuery("#nonPriorityChargesCheck").val();
		var priorityChargesCheck = jQuery("#priorityChargesCheck").val();
		
		if(nonPriorityRatesCheck != "Y"){
			alert("Please Save Courier Rates Details.");
			return false;
		}else if(priorityARatesCheck != "Y"){	
			alert("Please Save After 14 Hrs Priority Rates Details.");
			return false;
		}else if(priorityBRatesCheck != "Y"){	
			alert("Please Save Before 14 Hrs Priority Rates Details.");
			return false;
		}else if(prioritySRatesCheck != "Y"){	
			alert("Please Save Sunday Priority Rates Details.");
			return false;
		}else if(nonPriorityChargesCheck != "Y"){
			alert("Please Save Non Priority Fixed Charges Details.");
			return false;
		}else if(priorityChargesCheck != "Y"){
			alert("Please Save Priority Fixed Charges Details.");
			return false;
		}
		
		var fromDateStr = getDomElementById("fromDateStr").value;
		var toDateStr = getDomElementById("toDateStr").value;
		if(validDate(fromDateStr)){
			var url = "./cashRateConfiguration.do?submitName=submitCashRateDtls"
				+ "&cashRateHeaderId=" + cashRateHeaderId
				+ "&fromDate=" + fromDateStr
				+ "&toDate=" + toDateStr;
			var prevId = getDomElementById("prevCashRateHeaderId").value;
			if(!isNull(prevId)){
				url = url + "&prevCashRateHeaderId=" + prevId; 
			}
			ajaxCallWithoutForm(url, callBackSubmitCashRateDtls);
		}else{
			alert("From Date Should Be Greater Than Present Date");
			return false;
		}
	} else {
		alert("There is no such data to submit.");
	}
}
//call back function for submitCashRateDtls
function callBackSubmitCashRateDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = ajaxResp;
		if(data.transMsg=="SUCCESS"){
			disableAllForSubmit();
			alert("Data submitted successfully.");
			var doc = window.opener;
			if(!isNull(doc)){
				/* To close child window */
				self.close();
				/* To reload opener window */
				window.opener.location.reload();
			}
		}
	} else {
		alert("Data can not be submitted. Please try again.");
	}
}

/**
 * To clear the perticular tab and search again
 * 
 * @param productCode
 */
function clearProductDtls(productCode){
	if(productCode==PRO_CODE_COURIER){
		isCancel = true;
	}
	searchCashRateProductDtls(productCode);
}

/**
 * To validate RTO charges tab for save
 * 
 * @returns {Boolean}
 */
function validateRTOChrgsTab(productCatType){
	var idPrefix="";
	if(productCatType==PRO_CAT_TYPE_P){//If Priority
		idPrefix="PR";
	}
	if(getDomElementById("rtoChargeApplicableChk"+idPrefix).checked==false){
		alert("Please provide RTO charges details");
		return false;
	} else {
		if(getDomElementById("sameAsSlabRateChk"+idPrefix).checked==false){
			var discount = getDomElementById("discountOnSlab"+idPrefix);
			if(isNull(discount.value)){
				alert("Please provide discont on rate");
				setTimeout(function(){discount.focus();}, 10);
				discount.value="";
				return false;
			}
		}
	}
	return true;
}
/**
 * To validate RTO Charges before save
 */
function validateRTOChrgs(productCatType) {
	var idPrefix="";
	if(productCatType==PRO_CAT_TYPE_P){//If Priority
		idPrefix="PR";
	}
	//RTO Charge Applicable
	if(getDomElementById("rtoChargeApplicableChk"+idPrefix).checked==true){
		getDomElementById("sameAsSlabRateChk"+idPrefix).disabled=false;
		getDomElementById("discountOnSlab"+idPrefix).disabled=false;
	} else {
		getDomElementById("sameAsSlabRateChk"+idPrefix).checked=false;
		getDomElementById("discountOnSlab"+idPrefix).value="";
		getDomElementById("sameAsSlabRateChk"+idPrefix).disabled=true;
		getDomElementById("discountOnSlab"+idPrefix).disabled=true;
	}
	//Same As Slab Rate
	if(getDomElementById("sameAsSlabRateChk"+idPrefix).checked==true){
		getDomElementById("discountOnSlab"+idPrefix).value="";
		getDomElementById("discountOnSlab"+idPrefix).disabled=true;
	} else {
		getDomElementById("discountOnSlab"+idPrefix).disabled=false;
	}
}

/**
 * To validate fixed charges details for save
 * 
 * @returns {Boolean}
 */
function validateFixedChrgsTab(productCatType){
	var idPrefix="";
	validateRTOChrgs(PRO_CAT_TYPE_P);
	if(productCatType==PRO_CAT_TYPE_P){//If Priority
		idPrefix="PR";
	}
	//checkbox
	var fuleChrg = getDomElementById("fuelSurchargeChk"+idPrefix);
	var otherChrg = getDomElementById("otherChargesChk"+idPrefix);
	var serviceTax = getDomElementById("serviceTaxChk"+idPrefix);
	var eduCess = getDomElementById("eduCessChk"+idPrefix);
	var hEduCess = getDomElementById("higherEduCessChk"+idPrefix);
	var stateTax = getDomElementById("stateTaxChk"+idPrefix);
	var surChrgOnST = getDomElementById("surchargeOnSTChk"+idPrefix);
	var octrSrvChrg = getDomElementById("octroiServiceChargesChk"+idPrefix);
	var prclChrg = getDomElementById("parcelChargesChk"+idPrefix);
	var toPay = getDomElementById("toPayChk"+idPrefix);
	var docChrg = getDomElementById("docChargesChk"+idPrefix);
	var lcChrg = getDomElementById("lcChargesChk"+idPrefix);
	var airChrg = getDomElementById("airportChargesChk"+idPrefix);
	//var octrBornBy = getDomElementById("octroiBourneByChk"+idPrefix);
	
	//value
	var fuleChrgVal = getDomElementById("fuelSurcharge"+idPrefix);
	var otherChrgVal = getDomElementById("otherCharges"+idPrefix);
	var serviceTaxVal = getDomElementById("serviceTax"+idPrefix);
	var eduCessVal = getDomElementById("eduCess"+idPrefix);
	var hEduCessVal = getDomElementById("higherEduCess"+idPrefix);
	var stateTaxVal = getDomElementById("stateTax"+idPrefix);
	var surChrgOnSTVal = getDomElementById("surchargeOnST"+idPrefix);
	var octrSrvChrgVal = getDomElementById("octroiServiceCharges"+idPrefix);
	var prclChrgVal = getDomElementById("parcelCharges"+idPrefix);
	var toPayVal = getDomElementById("toPay"+idPrefix);
	var docChrgVal = getDomElementById("docCharges"+idPrefix);
	var lcChrgVal = getDomElementById("lcCharges"+idPrefix);
	var airChrgVal = getDomElementById("airportCharges"+idPrefix);
	//var octrBornByVal = getDomElementById("octroiBourneBy"+idPrefix);
	
	//fuelSurchargeChk
	if(!isNull(fuleChrg) && fuleChrg.checked){
		if(isNull(fuleChrgVal.value)){
			alert("Please provide Fuel Surcharges");
			setTimeout(function(){fuleChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//otherChargesChk
	if(!isNull(otherChrg) && otherChrg.checked){
		if(isNull(otherChrgVal.value)){
			alert("Please provide Other Charges");
			setTimeout(function(){otherChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//serviceTaxChk
	if(!isNull(serviceTax) && serviceTax.checked){
		if(isNull(serviceTaxVal.value)){
			alert("Please provide Service Tax");
			setTimeout(function(){serviceTaxVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//eduCessChk
	if(!isNull(eduCess) && eduCess.checked){
		if(isNull(eduCessVal.value)){
			alert("Please provide Education Cess");
			setTimeout(function(){eduCessVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//higherEduCessChk
	if(!isNull(hEduCess) && hEduCess.checked){
		if(isNull(hEduCessVal.value)){
			alert("Please provide Higher Education Cess");
			setTimeout(function(){hEduCessVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//stateTaxChk
	if(!isNull(stateTax) && stateTax.checked){
		if(isNull(stateTaxVal.value)){
			alert("Please provide State Tax");
			setTimeout(function(){stateTaxVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//surchargeOnSTChk
	if(!isNull(surChrgOnST) && surChrgOnST.checked){
		if(isNull(surChrgOnSTVal.value)){
			alert("Please provide Surcharge on ST");
			setTimeout(function(){surChrgOnSTVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//octroiServiceChargesChk
	if(!isNull(octrSrvChrg) && octrSrvChrg.checked){
		if(isNull(octrSrvChrgVal.value)){
			alert("Please provide Octroi Service Charges");
			setTimeout(function(){octrSrvChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//parcelChargesChk
	if(!isNull(prclChrg) && prclChrg.checked){
		if(isNull(prclChrgVal.value)){
			alert("Please provide Parcel Handling Charges");
			setTimeout(function(){prclChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//toPayChk
	if(!isNull(toPay) && toPay.checked){
		if(isNull(toPayVal.value)){
			alert("Please provide To Pay Charges");
			setTimeout(function(){toPayVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//docChargesChk
	if(!isNull(docChrg) && docChrg.checked){
		if(isNull(docChrgVal.value)){
			alert("Please provide Document Handling Charges");
			setTimeout(function(){docChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//lcChargesChk
	if(!isNull(lcChrg) && lcChrg.checked){
		if(isNull(lcChrgVal.value)){
			alert("Please provide LC Charges");
			setTimeout(function(){lcChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//airportChargesChk
	if(!isNull(airChrg) && airChrg.checked){
		if(isNull(airChrgVal.value)){
			alert("Please provide Airport Handling Charges");
			setTimeout(function(){airChrgVal.focus();}, 10);
			return false;
		}
		//return true;
	}
	//octroiBourneByChk
	/*if(!isNull(octrBornBy) && octrBornBy.checked){
		return true;
	}*/
	//alert("Please provide fixed charges details");
	//return false;
	return true;
}

/**
 * To renew cash rate configuration
 */
function renewCashRateConfig(){
	var cashRateHeaderId = getDomElementById("cashRateHeaderId").value;
	if(!isNull(cashRateHeaderId)){
		getDomElementById("regionId").disabled = false;
		var regionId = getDomElementById("regionId").value;
		var url = "./cashRateConfiguration.do?submitName=viewRenewCashRateConfiguration"
			+ "&cashRateHeaderId=" + cashRateHeaderId
			+ "&regionId=" + regionId;
		getDomElementById("regionId").disabled = true;
		window.open(url,'Cash Rate Configuration - Renew','toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
	}
}

/**
 * To get Origin Sector By Region Id
 * @param obj
 */
function getOriginSector(obj){
	if(!isNull(obj.value)){
		var url = "./cashRateConfiguration.do?submitName=getOriginSectorByRegionId"
			+ "&regionId="+obj.value;
		showProcessing();
		ajaxCallWithoutForm(url, callBackGetOriginSector);
	}
}
//call back function for getOriginSector
function callBackGetOriginSector(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = eval(ajaxResp);
		getDomElementById("airCargoOriginSectorId").value = data.sectorId;
		getDomElementById("trainOriginSectorId").value = data.sectorId;
		sector = data.sectorId;		
	}
	hideProcessing();
}

/**
 * To disable all elements for submit
 */
function disableAllForSubmit(){
	jQuery(":input").attr("disabled", true);
	jQuery(":input").attr("tabindex", -1);
	//jQuery("select").attr("disabled", true);
	jQuery("select").attr("tabindex", -1);
	/* Courier */
	btnDisable("saveBtn1");
	btnDisable("cancelBtn1");
	/* Air-Cargo */
	btnDisable("saveBtn2");
	btnDisable("cancelBtn2");
	/* Train */
	btnDisable("saveBtn3");
	btnDisable("cancelBtn3");
	/* Fixed Charges */
	btnDisable("saveBtn4");
	btnDisable("cancelBtn4");
	/* RTO */
	btnDisable("saveBtn5");
	btnDisable("cancelBtn5");
	/* Priority */
	btnDisable("saveBtn6");
	btnDisable("cancelBtn6");
	/* Priority Fixed Charges */
	btnDisable("saveBtn7");
	btnDisable("cancelBtn7");
	/* Priority RTO */
	btnDisable("saveBtn8");
	btnDisable("cancelBtn8");
	/* Submit & Search */
	btnDisable("submitBtn1");
	/*_btnDisable("searchBtn1");*/
	/* Renew */
	if(isvalidForRenew()) {
		_btnEnable("renewBtn1");
	}
	/*if(getDomElementById("isRenew").value!=YES){
		btnEnable("cancelBtn9");//CANCEL ALL
	}*/
	btnEnable("cancelBtn9");
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
 * To disabled button
 * @param btnId
 */
function _btnDisable(btnId){
	jQuery('#'+btnId).removeClass('button');
	buttonDisabled(btnId, 'btnintformbigdis');
}
/**
 * To enable button
 * @param btnId
 */
function _btnEnable(btnId){
	buttonEnabled(btnId,'button');
	jQuery('#'+btnId).removeClass('btnintformbigdis');
}

/**
 * To validate renew
 */
function validateRenew(){
	var prevCashRateHeaderId = getDomElementById("prevCashRateHeaderId").value;
	if(!isNull(prevCashRateHeaderId)){
		searchCashRateDtls();
	}
}

/**
 * To validate whether it is validate for renew or not
 * @returns {Boolean}
 */
function isvalidForRenew(){
	var fromDtStr = getDomElementById("fromDateStr").value;
	
	var dt1 = parseInt(fromDtStr.substring(0, 2), 10);
	var mon1 = parseInt(fromDtStr.substring(3, 5), 10);
	var yr1 = parseInt(fromDtStr.substring(6, 10), 10);
	
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date = new Date();
	var date2 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	
	//var i = compareDates(TODAY_DATE, fromDtStr);
	if(date1 <= date2)
		return true;
	else
		return false;
	
}

/**
 * To validate Minimum Chargable Weight
 * @param processCode
 * @returns {Boolean}
 */
function validateMinChrgWt(processCode){
	if(processCode==PRO_CODE_AIR_CARGO){//AIR-CARGO
		var minChrgWt = getDomElementById("airCargoMinChargeableWeight");
		if(isNull(minChrgWt.value)){
			alert("Please Select Minimum Chargable Weight for Air-Cargo");
			setTimeout(function(){minChrgWt.focus();}, 10);
			return false;
		}
	} else if(processCode==PRO_CODE_TRAIN) {//TRAIN
		var minChrgWt = getDomElementById("trainMinChargeableWeight");
		if(isNull(minChrgWt.value)){
			alert("Please Select Minimum Chargable Weight for Train");
			setTimeout(function(){minChrgWt.focus();}, 10);
			return false;
		}
	}
	return true;
}

/**
 * To cancel All or clear whole page - Cash Rate Configuration
 */
function cancelAllCash(){
	var url="./cashRateConfiguration.do?submitName=viewCashRateConfiguration";
	document.cashRateConfigurationForm.action=url;
	document.cashRateConfigurationForm.submit();
}

function disEnableSector(secField, field, prodField, rowField, colField, flag1, flag2){
	var prodId = jQuery("#"+prodField).val();
	var cols = jQuery("#"+rowField).val();
	var rows = jQuery("#"+colField).val();
	for(var r=1; r<=rows; r++){
		for(var c=1; c<=cols; c++){
			var destSec = jQuery("#"+secField+prodId+c+r).val();
			if(destSec == sector){
				document.getElementById(field+prodId+c+r).disabled = flag1;
			}else{
				document.getElementById(field+prodId+c+r).disabled = flag2;
			}
		}
	}
}

function setFlags(data){

		if(data.nonPriorityRatesCheck == 'Y'){
			jQuery("#nonPriorityRatesCheck").val(data.nonPriorityRatesCheck);
		}
		if(data.priorityRatesCheck == 'Y'){
			jQuery("#priorityRatesCheck").val(data.priorityRatesCheck);
		}
		if(data.priorityARatesCheck == 'Y'){
			jQuery("#priorityARatesCheck").val(data.priorityARatesCheck);
		}
		if(data.priorityBRatesCheck == 'Y'){
			jQuery("#priorityBRatesCheck").val(data.priorityBRatesCheck);
		}
		if(data.prioritySRatesCheck == 'Y'){
			jQuery("#prioritySRatesCheck").val(data.prioritySRatesCheck);
		}
		if(data.nonPriorityChargesCheck == 'Y'){
			jQuery("#nonPriorityChargesCheck").val(data.nonPriorityChargesCheck);
		}
		if(data.priorityChargesCheck == 'Y'){
			jQuery("#priorityChargesCheck").val(data.priorityChargesCheck);
		}
		
}


function checkTax(obj){
	if(document.getElementById(obj).checked == true){
		checkTaxBox(obj, 'serviceTaxChk', 'eduCessChk', 'higherEduCessChk', true);	
		document.getElementById("stateTaxChk").checked = false;
		document.getElementById("surchargeOnSTChk").checked = false;
	}else if(document.getElementById(obj).checked == false){
		checkTaxBox(obj, 'serviceTaxChk', 'eduCessChk', 'higherEduCessChk', false);	
		document.getElementById("stateTaxChk").checked = true;
		document.getElementById("surchargeOnSTChk").checked = true;
	}	
}

function checkSTTax(obj){
	if(document.getElementById(obj).checked == true){
		checkSTTaxBox(obj, 'stateTaxChk', 'surchargeOnSTChk', true);
		document.getElementById("higherEduCessChk").checked = false;
		document.getElementById("eduCessChk").checked = false;
		document.getElementById("serviceTaxChk").checked = false;
	}else if(document.getElementById(obj).checked == false){
		checkSTTaxBox(obj, 'stateTaxChk', 'surchargeOnSTChk', false);
		document.getElementById("higherEduCessChk").checked = true;
		document.getElementById("eduCessChk").checked = true;
		document.getElementById("serviceTaxChk").checked = true;
	}	
}

function checkTaxPR(obj){
	if(document.getElementById(obj).checked == true){
		checkTaxBox(obj, 'serviceTaxChkPR', 'eduCessChkPR', 'higherEduCessChkPR', true);	
		document.getElementById("stateTaxChkPR").checked = false;
		document.getElementById("surchargeOnSTChkPR").checked = false;
	}else if(document.getElementById(obj).checked == false){
		checkTaxBox(obj, 'serviceTaxChkPR', 'eduCessChkPR', 'higherEduCessChkPR', false);
		document.getElementById("stateTaxChkPR").checked = true;
		document.getElementById("surchargeOnSTChkPR").checked = true;
	}	
}

function checkSTTaxPR(obj){
	if(document.getElementById(obj).checked == true){
		checkSTTaxBox(obj, 'stateTaxChkPR', 'surchargeOnSTChkPR', true);	
		document.getElementById("higherEduCessChkPR").checked = false;
		document.getElementById("eduCessChkPR").checked = false;
		document.getElementById("serviceTaxChkPR").checked = false;
	}else if(document.getElementById(obj).checked == false){
		checkSTTaxBox(obj, 'stateTaxChkPR', 'surchargeOnSTChkPR', false);
		document.getElementById("higherEduCessChkPR").checked = true;
		document.getElementById("eduCessChkPR").checked = true;
		document.getElementById("serviceTaxChkPR").checked = true;
	}	
}

function checkSTTaxBox(obj, obj1, obj2, flag){
	if(obj == obj1){
		document.getElementById(obj2).checked = flag;
	}else if(obj == obj2){
		document.getElementById(obj1).checked = flag;
	}
}

function checkTaxBox(obj, obj1, obj2, obj3, flag){
	if(obj == obj1){
		document.getElementById(obj2).checked = flag;
		document.getElementById(obj3).checked = flag;
	}else if(obj == obj2){
		document.getElementById(obj1).checked = flag;
		document.getElementById(obj3).checked = flag;
	}else if(obj == obj3){
		document.getElementById(obj1).checked = flag;
		document.getElementById(obj2).checked = flag;
	}
}
function validDate(fromDtStr){

	var dt1 = parseInt(fromDtStr.substring(0, 2), 10);
	var mon1 = parseInt(fromDtStr.substring(3, 5), 10);
	var yr1 = parseInt(fromDtStr.substring(6, 10), 10);
	
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date = new Date();
	var date2 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	
	if(date1 <= date2)
		return false;
	
		return true;
}


function loadStateDropDown(selectId, val, statesList){
	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (!isNull(statesList) && statesList.length > 0) {
		for ( var i = 0; i < statesList.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = statesList[i].stateId;
			opt.text = statesList[i].stateName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function loadCityDropDown(selectId, val, datalist){
	var obj = getDomElementById(selectId);
	clearCityDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].cityId;
			opt.text = datalist[i].cityName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function validateCities(prodCode, prodId, rowId, cityObj, stateObj) {
	
	cityObj1 = getDomElementById(cityObj+prodId+ rowId);
	cityObjVal1 = getDomElementById(cityObj+prodId+ rowId).value;
	stateObj1 = getDomElementById(stateObj+prodId+ rowId);
	stateObjVal1 = getDomElementById(stateObj+prodId+ rowId).value;
	
	var rCnt = 0;
	
	if(prodCode == "PR"){
		rCnt = rowCount_PR;
	}else if(prodCode == "CO"){
		rCnt = rowCount_CO;
	}else if(prodCode == "TR"){
		rCnt = rowCount_TR;
	}else if(prodCode == "AR"){
		rCnt = rowCount_AR;
	}
	
	for ( var i = 1; i < rCnt; i++) {
			if (i != rowId) {
				cityObj2 = getDomElementById(cityObj+prodId+ i);
				stateObj2 = getDomElementById(stateObj+prodId + i);
			
					cityObjVal2 = cityObj2.value;
					stateObjVal2 = stateObj2.value;
					if (!isNull(stateObjVal1) && !isNull(stateObjVal2) && !isNull(cityObjVal1) && !isNull(cityObjVal2) ) {			
					if ((stateObjVal1 == stateObjVal2) && (cityObjVal1 == cityObjVal2)) {
						alert("State row number " + i + " and "	+ rowId
								+ " are same. Cities should not be same");
						cityObj1.value = "";
						setTimeout(function() {
							cityObj1.focus();
						}, 10);
						return false;
					}
				}
			}
		}
	
	
	return true;
}


function validateStates(prodCode, prodId, rowId, cityObj, stateObj) {
	if(isNull(getDomElementById(stateObj+prodId+rowId).value)){
		alert("Please select state");
		setTimeout(function() {
			getDomElementById(stateObj+prodId+ rowId).focus();
		}, 10);
		return false;
	}
	
	cityObj1 = getDomElementById(cityObj+prodId+ rowId);
	cityObjVal1 = getDomElementById(cityObj+prodId+ rowId).value;
	stateObj1 = getDomElementById(stateObj+prodId+ rowId);
	stateObjVal1 = getDomElementById(stateObj+prodId+ rowId).value;
	
	var rCnt = 0;
	
	if(prodCode == "PR"){
		rCnt = rowCount_PR;
	}else if(prodCode == "CO"){
		rCnt = rowCount_CO;
	}else if(prodCode == "TR"){
		rCnt = rowCount_TR;
	}else if(prodCode == "AR"){
		rCnt = rowCount_AR;
	}
	
	
	
	for ( var i = 1; i < rCnt; i++) {
			if (i != rowId) {
				cityObj2 = getDomElementById(cityObj+prodId+ i);
				stateObj2 = getDomElementById(stateObj+prodId + i);
				
					cityObjVal2 = cityObj2.value;
					stateObjVal2 = stateObj2.value;
					if (!isNull(stateObjVal1) && !isNull(stateObjVal2) && isNull(cityObjVal1) && isNull(cityObjVal2) ) {	
					if ((stateObjVal1 == stateObjVal2)) {
						alert("State row number " + i + " and "	+ rowId
								+ " should not be same");
						stateObj1.value = "";
						setTimeout(function() {
							stateObj1.focus();
						}, 10);
						return false;
					}
				}
			}
		}
	
	
	return true;
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");
}

function clearCityDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "ALL", "");
}



function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}



function getCityByState(stateObj,prodId, rowId,cityObj){
	if(!isNull(stateObj.value)){	
	var url = "./cashRateConfiguration.do?submitName=getCityListByStateId&&stateId="+stateObj.value;
	jQuery.ajax({
		url : url,
		success : function(req) {
			populateCity(req, prodId, stateObj, rowId,cityObj);
		}
	});	
	}else{		
		//getDomElementById(stateObj).value = "";
		clearDropDownList(cityObj+prodId+rowId);
	}
}

function populateCity(req, prodId, stateObj,rowId,cityObj){
	clearCityDropDownList(cityObj+prodId+rowId);	
	if (!isNull(req)) {
		city = jsonJqueryParser(req);
		var error = city[ERROR_FLAG];
		if (req != null && error != null) {
			alert(error);
			getDomElementById(cityObj+prodId+rowId).value = "";			
			//getDomElementById("stateId"+prodCatId+rowNo).value = "";
			//getDomElementById("stateId"+prodCatId+rowNo).focus();
		} else {
			loadCityDropDown(cityObj+prodId+rowId, "", city);
		}

	}
}