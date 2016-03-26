var rbmhTO;
var sectorList;
var wtList;
var matrixList;
var vobList;
var prodList;
var prodCatId;
var vobSlabId;
var orgSecId;
var pId;
var load = false;
var action = "";
var indCatId;
var custCatId;
var benchMarkType;
var rateApproved;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
function getEmployeeDetails(){
	empCode = getDomElementById("empCode").value;
	
	if (!isNull(empCode)){		
	  var url =
	  "./rateBenchMark.do?submitName=getEmpDetails&empCode="+empCode;
	  
	  ajaxCall(url, "rateBenchMarkForm", populateDetails);
	} 
}
function populateDetails(data) {	
	/*if(!isNull(data)){
		resp = 	jsonJqueryParser(data);	
		getDomElementById("empName").value = resp.firstName+" "+resp.lastName;
		getDomElementById("empId").value = resp.employeeId; 
	}else{
		alert("Employee does not exist");
		getDomElementById("empName").value = "";
		getDomElementById("empCode").value  = "";
		getDomElementById("empCode").focus();
	}*/
	
	if (!isNull(data)) {
		var resp = jsonJqueryParser(data);
		var error = resp[ERROR_FLAG];
		if (data != null && error != null) {
			alert(error);
			getDomElementById("empName").value = "";
			getDomElementById("empCode").value  = "";
			getDomElementById("empCode").focus();
		} else {
			getDomElementById("empName").value = resp.firstName+" "+resp.lastName;
			getDomElementById("empId").value = resp.employeeId; 
		}

	}
	
}


function saveRateBenchMark(val) {
	action = val;
	var secArr =""; 
	var wtArr ="";
	var sCount = 0;
	var wCount = 0;
	
	getDomElementById("rateProdCatId").value = prodCatId;
	secLen = sectorList.length;
	wtLen = wtList.length;
	if(!isNull(orgSecId))
		getDomElementById("rateOriginSectorId").value = orgSecId;	
	else
		getDomElementById("rateOriginSectorId").value = null;;
	
	if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
		getDomElementById("rateMinChargWtId").value = getDomElementById("rateMinChargWt"+prodCatId).value;
	else
		getDomElementById("rateMinChargWtId").value = null;

	getDomElementById("rateVobSlabsId").value = vobSlabId;
	
	
	for(var i = 0; i< secLen ; i++){
	if(sectorList[i].sectorType == destCode && 
			sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
			sCount++;
			if(secArr.length >0)
			secArr = secArr+",";	
			secArr = secArr + sectorList[i].sectorTO.sectorId;
	}
	}
			for(var j = 0; j< wtLen ; j++){
				if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
					wCount++;
					if(wtArr.length >0)
						wtArr = wtArr+",";	
					wtArr = wtArr + wtList[j].weightSlabTO.weightSlabId;
				}					
			}				
			
				getDomElementById("secArrStr").value = secArr;
				getDomElementById("wtArrStr").value = wtArr;
	
			if(getDomElementById("rateEdit").value == editCode){
			var	url = "./rateBenchMark.do?submitName=updateApproverDetails&empId="+getDomElementById("empId").value+"&headerId="+getDomElementById("rateBenchMarkHeaderId").value;
			ajaxCall(url, "rateBenchMarkForm", editCallback); 
			}
			else{
				 if(!checkMandatoryFields())
					return false;
				 if(val == 'submit'){
					if(!validateGridValues())
						return false;
					getDomElementById("isApproved").value = approveCode;
				 }
				
			enableGridFields();
			var url = "./rateBenchMark.do?submitName=saveOrUpdateRateBenchMark";
			ajaxCall(url, "rateBenchMarkForm", saveCallback);
			jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
			//}
			}
	
}

function enableGridFields(){

	prodLen = prodList.length;
	for(var l=0;l<prodLen;l++){
		
	pCat = prodList[l].rateProductCategoryId;
	
	for(var i = 0; i< secLen ; i++){
		if(sectorList[i].sectorType == destCode
			&&
			sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
		{ 
				for(var j = 0; j< wtLen ; j++){
					
					if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
					
					field = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId);
					if(!isNull(field.value)){
					field.disabled = false;
					}
					
					
				}
		}
	}
	}
	}
	
}

function checkMandatoryFields(){
	
	if(!checkHeaderFields())
		return false;
	if(!checkGridFieldsData())
	   return false;
	
	return true;
}

function checkHeaderFields(){
	var empCode = getDomElementById("empCode");
	var date = getDomElementById("rateBenchMarkDateStr");
	
	if(isNull(empCode.value)){
		alert("Please enter Employee Code");
		empCode.focus();
		return false;
	}
	else if(isNull(date.value)){
		alert("Please select Effective From Date");
		date.focus();
		return false;
	}else if(action == 'submit'){
		if(!checkDate(date))
			return false;
	}
	return true;
}

function checkGridFieldsData(){
	

		secLen = sectorList.length;
		wtLen = wtList.length;
	
		
		
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == destCode
				&&
				sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
			{ 
					for(var j = 0; j< wtLen ; j++){
						
						if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
						
						field = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId);
						if(isNull(field.value)){
						alert("Please fill the Grid details for Weight Slab: "+wtList[j].weightSlabTO.startWeightLabel+" - "+ wtList[j].weightSlabTO.endWeightLabel + " Sector: "+ sectorList[i].sectorTO.sectorName);
						field.focus();
						return false;
						}
						
						
					}
			}
		}
	
		}		
		
		if(!isNull(getDomElementById("rateMinChargWt"+prodCatId))){
			if(isNull(getDomElementById("rateMinChargWt"+prodCatId).value)){
				alert("Please Select MinChargeable weight");
				getDomElementById("rateMinChargWt"+prodCatId).focus();
				return false;
			}
		}
		
	return true;
}

function checkDate(obj) {
	var effectiveDate = obj.value;
	if (!isNull(effectiveDate)) {
	if(getDomElementById("rateBenchMarkType").value != renewCode){
	var dt1 = parseInt(effectiveDate.substring(0, 2), 10);
	var mon1 = parseInt(effectiveDate.substring(3, 5), 10);
	var yr1 = parseInt(effectiveDate.substring(6, 10), 10);
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date = new Date();
	date3 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	if (date1 < date3) {
		alert("Effective From date should be same or greater than present date");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	} 
	}else{
		
			if (!isFutureDate(effectiveDate)) {
				alert("Effective From date should be greater than present date");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		}
	}
	return true;
	
}
function editCallback(data) {
	
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		}else{
			var msg = responseText[SUCCESS_FLAG];
			getDomElementById("empCode").disabled = true;
			getDomElementById("rateEdit").value = "";	
			alert(msg);					
		}
	}
	
		
}

/**
 * This Method popup message whether the data has been stored in database or not
 * 
 * @param data
 */
function saveCallback(data) {
	
	getDomElementById("rateVob"+prodCatId).value = vobSlabId;
	if(!isNull(getDomElementById("rateOrgSec"+prodCatId)))
	getDomElementById("rateOrgSec"+prodCatId).value = orgSecId;
	//loadValues();
	//loadGridValues();
	/*if(!isNull(data))
	if(data == "FAILURE"){
		if(action == 'submit')
			alert("Rate not submitted successfully");
		else
		alert("Rate not saved successfully");
	}
	
	if(loadData(data)){
		jQuery.unblockUI();
		if(action == 'submit')
			alert("Rate submitted successfully");
		else
		alert("Rate saved successfully");
		
	}*/
	
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		}else if(loadData(data)){
			rbmhTO = jsonJqueryParser(data);
			jQuery.unblockUI();
			alert(rbmhTO.transMsg);
		}
	}
	
	
	
}

function assignRateValues(){
	
	
	vobSlabId = getDomElementById("rateVob"+prodCatId).value;
	
	if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
	getDomElementById("rateMinChargWt"+prodCatId).options[0].selected = true;

	getDomElementById("rateBenchMarkMatrixHeaderId").value = "";
	secLen = sectorList.length;
	wtLen = wtList.length;

		
		if(!isNull(getDomElementById("rateOrgSec"+prodCatId)))
		orgSecId = getDomElementById("rateOrgSec"+prodCatId).value ;
		else
		orgSecId = null;
		
		
		
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == destCode  && 
					sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){ 
					
					for(var j = 0; j< wtLen ; j++){
						if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
						if(!isNull(getDomElementById("rateBenchMarkMatrixId"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId)))
						getDomElementById("rateBenchMarkMatrixId"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId).value	= "";
						if(!isNull(getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId)))
						getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId).value	= "";
						
						}
					}
			}
		}
	
		
		if(!isNull(matrixList) && !isNull(matrixList[prodCatId])){
		
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == destCode && 
				sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
				
				for(var j = 0; j< wtLen ; j++){
				if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId){
				
					for ( var k = 0; k < matrixList[prodCatId].length; k++){ 
					
					if(!isNull(matrixList[prodCatId][k]) && matrixList[prodCatId][k].rateDestinationSector == sectorList[i].sectorTO.sectorId && 
							matrixList[prodCatId][k].weightSlab == wtList[j].weightSlabTO.weightSlabId &&
							matrixList[prodCatId][k].vobId == vobSlabId &&
							matrixList[prodCatId][k].prodCatId == prodCatId &&
							(((!isNull(matrixList[prodCatId][k].rateOriginSector) && (matrixList[prodCatId][k].rateOriginSector != 0) && !isNull(orgSecId)) && (matrixList[prodCatId][k].rateOriginSector == orgSecId)) || isNull(matrixList[prodCatId][k].rateOriginSector)))
						{
						
						getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId).value	= matrixList[prodCatId][k].rate;
						getDomElementById("rateBenchMarkProductId").value = matrixList[prodCatId][k].productId;
						getDomElementById("rateBenchMarkMatrixId"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId).value	= matrixList[prodCatId][k].rateBenchMarkMatrixId;
						getDomElementById("rateBenchMarkMatrixHeaderId").value = matrixList[prodCatId][k].rateBenchMarkMatrixHeaderId;
						if(rateApproved == approveCode)
						getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+wtList[j].weightSlabTO.weightSlabId).disabled = true;
						if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
							getDomElementById("rateMinChargWt"+prodCatId).value	= matrixList[prodCatId][k].minChrgWtId;
						break;
						}
				}
				}
			}
			}
	}
		}
		
		
		return true;
}


function loadValues(){
	
	indCatId = getDomElementById("rateIndustryCategoryId").value;
	prodCatId = getDomElementById("rateProdCatId").value;
	custCatId = getDomElementById("rateCustCatId").value;
	benchMarkType = getDomElementById("rateBenchMarkType").value;
	oldHeaderId = getDomElementById("rateCurrentHeaderId").value;
	
	//alert(type);
	
	var url = "./rateBenchMark.do?submitName=getValues&industryCategoryId="+indCatId+"&customerCategoryId="+custCatId+"&type="+benchMarkType+"&oldHeaderId="+oldHeaderId;
	ajaxCall(url, "rateBenchMarkForm", loadData);
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}

function loadGridValues(){
	
	var indCatId = getDomElementById("rateIndustryCategoryId").value;
	var prodCatId = getDomElementById("rateProdCatId").value;
	var custCatId = getDomElementById("rateCustCatId").value;
	var vobSlabId = getDomElementById("rateVob"+prodCatId).value;
	
	type = getDomElementById("rateBenchMarkType").value;
	
	
	var url = "./rateBenchMark.do?submitName=loadAllGridValues&industryCategoryId="+indCatId+"&customerCategoryId="+custCatId+"&productCategoryId="+prodCatId+"&vobSlabId="+vobSlabId+"&type="+type;
	if(!isNull(getDomElementById("rateOrgSec"+prodCatId)))
		url = url+ "&originSecId="+getDomElementById("rateOrgSec"+prodCatId).value;
	
	ajaxCall(url, "rateBenchMarkForm", loadData);
	//jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}

function loadData(data) {
	
	rbmhTO = jsonJqueryParser(data);
	getDomElementById("rateBenchMarkDateStr").value = rbmhTO.rateBenchMarkDateStr;
	getDomElementById("empCode").value = rbmhTO.empCode;
	getDomElementById("empName").value = rbmhTO.empName;
	getDomElementById("empId").value = rbmhTO.empId;
	
	if(isNull(rbmhTO.rateMatrixMap))
		getDomElementById("rateBenchMarkHeaderId").value = "";
	else
		getDomElementById("rateBenchMarkHeaderId").value = rbmhTO.rateBenchMarkHeaderId;
	
	rateApproved = rbmhTO.isApproved;
	
	if(rateApproved == approveCode){
		getDomElementById("empCode").disabled = true;
		
		buttonDisabled("btnSave"+prodCatId,"btnintform");
		jQuery("#" +"btnSave"+prodCatId).addClass("btnintformbigdis");
		
		if(getDomElementById("rateBenchMarkType").value != renewCode){
		buttonEnabled("btnReNew","btnintform");
		jQuery("#" +"btnReNew").removeClass("btnintformbigdis");
		}else{
			buttonDisabled("btnReNew","btnintform");
			jQuery("#" +"btnReNew").addClass("btnintformbigdis");
		}
		
		buttonEnabled("btnEdit","btnintform");
		jQuery("#" +"btnEdit").removeClass("btnintformbigdis");
		
		}else{
			getDomElementById("empCode").disabled = false;
			buttonDisabled("btnReNew","btnintform");
			jQuery("#" +"btnReNew").addClass("btnintformbigdis");

			buttonDisabled("btnEdit","btnintform");
			jQuery("#" +"btnEdit").addClass("btnintformbigdis");
			
			
		}
	
	
	getDomElementById("isApproved").value = rbmhTO.isApproved;
	
	
	getDomElementById("rateBenchMarkType").value = rbmhTO.rateBenchMarkType;
	
	
	sectorList = rbmhTO.rateSectorsList;
	wtList = rbmhTO.rateWtSlabsList;
	matrixList = rbmhTO.rateMatrixMap;
	vobList = rbmhTO.rateVobSlabsList;
	prodList = rbmhTO.rateProdCatList;
	
	assignRateValues();
	
	
	return true;
}

function loadAllGridValues(val){
	
	
	//getDomElementById("rateProdCatId").value = val;
	
	
	if(rateApproved == approveCode){
		buttonDisabled("btnSave"+val,"btnintform");
		jQuery("#" +"btnSave"+val).addClass("btnintformbigdis");
	}	
	prodCatId = val;
	
	getDomElementById("rateVob"+val).options[0].selected = true;
	
	if(!isNull(getDomElementById("rateOrgSec"+val))){
	getDomElementById("rateOrgSec"+val).options[0].selected = true;
	}
	
	getDomElementById("rateBenchMarkProductId").value = "";
	
	assignRateValues();
}


function renewBenchMark(){
	
	getDomElementById("rateBenchMarkType").value = renewCode;
	
	getDomElementById("empName").value = "";
	getDomElementById("rateBenchMarkDateStr").value = "";
	getDomElementById("empCode").value = "";
	
	getDomElementById("rateCurrentHeaderId").value = getDomElementById("rateBenchMarkHeaderId").value;
	
	url = "./rateBenchMark.do?submitName=viewReNewRateBenchMark";
	document.rateBenchMarkForm.action = url;
	document.rateBenchMarkForm.submit();
	
}

function editBenchMark(){


	getDomElementById("rateEdit").value = editCode;
		
	getDomElementById("empCode").disabled = false;
}



function validateGridValues(){
	
	
	var valid = false;
	
	secLen = sectorList.length;
	prodLen = prodList.length;
	vobLen = vobList.length;
	osecVal = "";
	pCat ="";
	
		
		wtLen = wtList.length;
		osVal= null;
		for(var l=0;l<prodLen;l++){
		osVal= null;
		pCat = prodList[l].rateProductCategoryId;
		if(prodList[l].rateProductCategoryCode == courierCode){
		for(var m=0;m<vobLen;m++){
			if(vobList[m].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
		vobId = vobList[m].vobSlabTO.vobSlabId;	
			valid = false;
			
			
		if(!isNull(matrixList) && !isNull(matrixList[pCat])){
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == destCode && 
				sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
				for(var j = 0; j< wtLen ; j++){
				if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
				
					matrixLen = matrixList[pCat].length;
					for ( var k = 0; k < matrixLen; k++){
					
					if(!isNull(matrixList[pCat][k]) && matrixList[pCat][k].rateDestinationSector == sectorList[i].sectorTO.sectorId && 
							matrixList[pCat][k].weightSlab == wtList[j].weightSlabTO.weightSlabId &&
							matrixList[pCat][k].vobId == vobId &&
							matrixList[pCat][k].prodCatId == pCat &&
							(((!isNull(matrixList[pCat][k].rateOriginSector) 
									&& (matrixList[pCat][k].rateOriginSector != 0)
									&& !isNull(osVal)) && (matrixList[pCat][k].rateOriginSector == osVal))
									|| ( isNull(matrixList[pCat][k].rateOriginSector))) )
						{
						if(!isNull(matrixList[pCat][k].rate)){
							valid = true;
							break;
						}
						}
				}
					
				}
			}
				
			}
	}
		
		}
		if(!valid){
			if(!(vobSlabId==vobId)){
			alert("Please fill the Grid details  For Product :  "+prodList[l].rateProductCategoryName+"   Vol of business: "+ vobList[m].vobSlabTO.lowerLimit +" - "+ vobList[m].vobSlabTO.upperLimit);				
			return false;
			}
			}
			}
			}
		
		}else{
			
			
			for(var n = 0; n< secLen ; n++){
				if(sectorList[n].sectorType == originCode && 
						sectorList[n].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
					osVal= sectorList[n].sectorTO.sectorId;
			for(m=0;m<vobLen;m++){
				if(vobList[m].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
			vobId = vobList[m].vobSlabTO.vobSlabId;	
				valid = false;
				
				
			if(!isNull(matrixList[pCat])){
				matrixLen = matrixList[pCat].length;
			for(var i = 0; i< secLen ; i++){
				if(sectorList[i].sectorType == destCode && 
					sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
					for(var j = 0; j< wtLen ; j++){
					if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == pCat){
						
						for ( var k = 0; k < matrixLen; k++){
						
						if(!isNull(matrixList[pCat][k]) && matrixList[pCat][k].rateDestinationSector == sectorList[i].sectorTO.sectorId && 
								matrixList[pCat][k].weightSlab == wtList[j].weightSlabTO.weightSlabId &&
								matrixList[pCat][k].vobId == vobId &&
								matrixList[pCat][k].prodCatId == pCat &&
								(((!isNull(matrixList[pCat][k].rateOriginSector) 
										&& (matrixList[pCat][k].rateOriginSector != 0)
										&& !isNull(osVal)) && (matrixList[pCat][k].rateOriginSector == osVal))
										|| ( isNull(matrixList[pCat][k].rateOriginSector))) )
							{
							if(!isNull(matrixList[pCat][k].rate)){
								valid = true;
								break;
							}
							}
					}
						
					}
				}
					
				}
		}
			
			}
			if(!valid){
				
				if(!((vobSlabId == vobId)  && (orgSecId == osVal))){
				alert("Please fill the Grid details  For Product :  "+prodList[l].rateProductCategoryName+"   Vol of business: "+ vobList[m].vobSlabTO.lowerLimit +" - "+ vobList[m].vobSlabTO.upperLimit +" Origin: "+sectorList[n].sectorTO.sectorName  );
				return false;
				}
				}
				}
				}
				}
			}
			
		}
		}
		
		
		return true;
	
}


function loadValuesByIndustry(){
	
	url = "./rateBenchMark.do?submitName=viewRateBenchMarkByIndustry";
	document.rateBenchMarkForm.action = url;
	document.rateBenchMarkForm.submit();	
}

function validateFormat(obj){
	val = obj.value;
	/*if(!isNull(val)){
		if (val.indexOf(".") > 0  || val.length <= 3) {
		var parts = val.split(".");
	
		if (typeof parts[0] == "string"
				&& (parseInt(parts[0],10) < 0 && parseInt(parts[0],10) >= 1000)) {
			alert("Pelase enter valid Rate value.");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}

		if (parseInt(parts[1],10) < 0 && parseInt(parts[1],10) >= 100) {
			alert("Please enter valid Rate value.");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
			}
		}else{
			 alert("Please enter the Rate value in 999.99 format");
			 obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
		}
		
	}
	return true;*/

	if(!isNull(val)){
	    var num = new Number(val);
	    if(/^[0-9]{0,3}(\.[0-9]{0,2})?$/.test(val) && num > 0){
	        return true;
	    } else {
	       alert('Please enter the Rate value in 999.99 format');
	       obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
	       return false;
	  }
	}
	return true;
}



function selectDate(field, value) {
	
	if(rateApproved == approveCode){
		alert('You can not modify the Date');
		return false;
	} else {
		show_calendar(field, value);
	}
	return true;
}

/**
 * Change the Cursor Style
 * 
 * @param fieldId
 */
function changeCursor(fieldId) {

	getDomElementById(fieldId).style.cursor = "pointer";

}