var ROW_COUNT = "";
var IS_MANDATORY_DOC = "";


/** The moduleName */
var moduleName = "inMasterBag";

var manifestDestCityFieldName = "weightAW";
var weightGmFieldName = "weightGm";
var dest = "destCitys";
var printUrl;

var isAddRowInProcess = false;
/**
 * adds new rows to the grid
 */
function addInManifestRows(){
	if(isAddRowInProcess){
		return;
	}
	isAddRowInProcess = true;
	$('#inManifestTable').dataTable().fnAddData( [
		'<input type="checkbox"  id="chk'+ rowCount +'" name="chkBoxName"  />',
		'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
		'<input type="text" class="txtbox" styleClass="txtbox" id="bplNo'+ rowCount +'" name="to.bplNumbers" maxlength="10" onchange="isValidBplNo(this);isBPLExists('+ rowCount +');" onkeypress="enterKeyNavigationFocus4Grid(event, manifestDestCityFieldName,'+ rowCount +');"/>',
		'<input type="text" class="txtbox" styleClass="txtbox" id="bagLockNo'+ rowCount	+ '" onchange="isValidLockNo(this)" name="to.bagLockNumbers"  tabindex="-1" />',
		'<input type="text" name="weight" id="weightAW'+ rowCount+ '" class="txtbox width30"  size="4"  class="txtbox" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');" onblur="validateWeight('+ rowCount +');" onkeypress="return onlyNumeric(event);" maxlength="5"/> \
		<span class="lable">.</span> \
		<input type="text" maxlength="3" name="weightGm" id="weightGm'+ rowCount+ '" class="txtbox width30"   size="11"   onblur="validateWeight('+ rowCount +');addRow('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, \'bplNo\','+ rowCount +');" onfocus="clearWtGramIfEmpty(\'weightGm'+ rowCount +'\')"/> \
		<input type="hidden" id="manifestWeight'+ rowCount + '" name="to.manifestWeights"/>',
		'<input type="text" class="txtbox"  id="destCitys'+ rowCount +'" onchange="isValidCity(this)" name="to.destCityNames"/>',
		'<select name="to.docType" onchange="setProcessCode(this);setConsignmentTypeId('+ rowCount +')" disable="true" id="docType'+ rowCount +'" ><option value="">--Select--</option></select>',
		'<select class="selectBox width145" id="remarks'+ rowCount +'" name="to.remarks" >' + REMARKS_OPTION
		+ '</select>\
		<input type="hidden" id="manifestIds'+ rowCount+ '" name="to.manifestIds" value=""/>\
		<input type="hidden" id="destCityIds'+ rowCount+ '" name="to.destCityIds"/>\
		<input type="hidden" id="originOfficeIds'+ rowCount+ '" name="to.originOfficeIds"/>\
		<input type="hidden" id="destOfficeIds'+ rowCount+ '" name="to.destOfficeIds"/>\
		<input type="hidden" id="productIds'+ rowCount+ '" name="" value=""/>\
		<input type="hidden" id="bplProcessCode'+ rowCount+ '" name="to.bplProcessCode" value=""/>\
		<input type="hidden" id="totalWeight" name="to.totalManifestWeight"/> \
		<input type="hidden" id="receivedStatus'+ rowCount +'" name="to.receivedStatus" value="N"/>\
		<input type="hidden" id="consignmentTypeIds'+ rowCount +'" name="to.consignmentTypeIds"/>',		
	] );
	populateDocType(rowCount);
	rowCount++;
	isAddRowInProcess = false;
 }


/**
 * loadDefaultObjects
 *
 * @author uchauhan
 *//*
function loadDefaultObjects(){
	addInManifestRows();
	getWeightFromWeighingMachine();
	
}*/


/**
 * loadDefaultObjects
 *
 * @author uchauhan
 */
function loadDefaultObjects(){
	$("#mbplNo").focus();
	//getWeightFromWeighingMachine();
}

/** The consignmentTypeTOArray */
var consignmentTypeTOArray = new Array();

/**
 * validateBagLockNo
 *
 * @author uchauhan
 * validate bagLockNo with issue validation
 */
function validateBagLockNumber(lockNumberObj){
	//var lockNumberObj = document.getElementById("bagLockNo");
	
	//validate format
	if(!isValidLockNo(lockNumberObj)){
		lockNumberObj.value = "";
		return;
	}

	showProcessing();
	var url = "./inMasterBagManifest.do?submitName=isManifestNoIssued";
	$.ajax({
		url: url,
		data: "manifestNo="+lockNumberObj.value+"&seriesType=BAG_LOCK_NO",
		success: function(data){isLockNoIssued(data);}
	});
}

/**
 * isValidLockNo
 *
 * @param lockNoObj
 * @returns {Boolean}
 * @author uchauhan
 */
function isValidLockNo(lockNoObj) {	
	//Region Code+7digits :: R1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	lockNoObj.value = $.trim(lockNoObj.value);
		
	if (isNull(lockNoObj.value)) {
		return false;
	}

	if (lockNoObj.value.length!=8) {
		clearFocusAlertMsg(lockNoObj, "Lock No. should contain 8 characters only!");
		return false;
	}
		
	if (!lockNoObj.value.substring(0, 1).match(letters)
			|| !numpattern.test(lockNoObj.value.substring(1))) {
		clearFocusAlertMsg(lockNoObj, "Lock No. Format is not correct!");
		return false;
	}

	return true;
}


/**
 * isLockNoIssued
 *
 * @param data
 * @author uchauhan
 */
function isLockNoIssued(data){
	var lockNumberObj = document.getElementById("bagLockNo");
	var manifestIssueValidationTO = eval('(' + data + ')');
	hideProcessing();
	
	if(manifestIssueValidationTO.isIssued=="N"){
		clearFocusAlertMsg(lockNumberObj, manifestIssueValidationTO.errorMsg);
		return;
	}
}

/**
 * get the list of consignment type
 */
function getConsignmentTypeList(){
	var url = "./inMasterBagManifest.do?submitName=getConsignmentTypeList";
	ajaxCall(url,"inMasterBagManifestForm",populateConsignmentTypeTOList);
	//ajaxCallWithoutForm(url, populateConsignmentTypeTOList);
}
/**
 * @param req contains the list of values for consignment types
 */
function populateConsignmentTypeTOList(req){
	consignmentTypeTOArray = eval('(' + req + ')');
	addInManifestRows();
	setDataTableDefaultWidth();
}

/**
 * clears the screen
 */
function clearDetails(){
	
	if (promptConfirmation("clear")){
	document.getElementById("region").options.length = 0;
	 document.inMasterBagManifestForm.action="/udaan-web/inMasterBagManifest.do?submitName=viewInMBPL";
	 document.inMasterBagManifestForm.submit();
	}
}



/**
 * @param manifestNoObj contains the manifest number
 * @returns true if format of manifest number is valid 
 * else returns false
 */
function validateManifest(manifestNoObj){
	
	if (isValidMBplNo(manifestNoObj)){
		searchManifestDetails();
	}
	else return false;
}

/**
 * @param manifestNoObj conatins manifest Number
 * @returns {true} if format is valid
 * else returns false
 */
function isValidMBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();
		
	if (isNull(manifestNoObj.value)) {
		focusAlertMsg4TxtBox(manifestNoObj, "MBPL Number");
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric) 
			|| !manifestNoObj.value.substring(3, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "M") {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * clears and sets focus
 * @param obj which has to be cleared 
 * @param msg which needs to be shown as popup
 */
function clearFocusAlertMsg(obj, msg){
	obj.value = "";
	setFocus(obj);
	alert(msg);
}

/**
 * sets focus
 * @Desc sets the focus on the given field
 * @param obj 
 */
function setFocus(obj) {
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * validates BPL number
 * @param manifestNoObj contains the BPL Number
 * @returns {true}
 */
function isValidBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL No. should contain 10 characters only!");
		return false;
	}

	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric) 
			|| !manifestNoObj.value.substring(3, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}


/**
 * searches the manifest Number
 */
function searchManifestDetails(){
	var manifestNumberObj = document.getElementById("mbplNo");
	var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
	var loginOff = document.getElementById("officeName").value;

	/*if(isNull(mbplNo)){
		return;
	}*/
	// validate format
	if (!isValidMBplNo(manifestNumberObj)) {
		return;
	}
	
	showProcessing();
	url = './inMasterBagManifest.do?submitName=searchMBPLManifest&ManifestNumber='+manifestNumberObj.value+'&LoginOfficeId='+loggedInOfficeId+'&loginOffName='+loginOff;
	ajaxCallWithoutForm(url,printCallBackManifestDetails);
}


/**
 * populates the screen with manifest data
 * @param data
 */
function printCallBackManifestDetails(manifestDetailsTO) {
	hideProcessing();
	var errorMsg = getErrorMessage(manifestDetailsTO);
	var mbplNoObj = document.getElementById("mbplNo");

	if(!isNull(errorMsg)){
		clearFocusAlertMsg(mbplNoObj, errorMsg);
		return;
	}
	
	if (!isNull(manifestDetailsTO) && !manifestDetailsTO.empty) {
		// Header Part

		if(manifestDetailsTO.isInManifest){
			document.getElementById("dateTime").value = manifestDetailsTO.manifestDate;
			document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
			if (!isNull(manifestDetailsTO.loginOffName)) {
				document.getElementById("officeName").value = manifestDetailsTO.loginOffName;				
			}
		}
		document.getElementById("lockNum").value = manifestDetailsTO.lockNum;
		$("#manifestReceivedStatus").val(manifestDetailsTO.manifestReceivedStatus);

		if(!isNull(manifestDetailsTO.originRegionTO)){
			document.getElementById("region").value = manifestDetailsTO.originRegionTO.regionId;			
		}
	
		if(!isNull(manifestDetailsTO.originCityTO)){
			clearDropDownList("destCity");
			addOptionTODropDown("destCity",
					manifestDetailsTO.originCityTO.cityName,
					manifestDetailsTO.originCityTO.cityId);
			document.getElementById("destCity").value = manifestDetailsTO.originCityTO.cityId;
		}
		if(!isNull(manifestDetailsTO.officeTypeTO)){
			clearDropDownList("destOfficeType");
			addOptionTODropDown("destOfficeType",manifestDetailsTO.officeTypeTO.offcTypeDesc,manifestDetailsTO.officeTypeTO.offcTypeId);
			document.getElementById("destOfficeType").value = manifestDetailsTO.officeTypeTO.offcTypeId;
		}
		if(!isNull(manifestDetailsTO.originOfficeTO)){
			var code = manifestDetailsTO.originOfficeTO.officeCode+"-"+manifestDetailsTO.originOfficeTO.officeName;			
			clearDropDownList("office");
			addOptionTODropDown("office", code,
					manifestDetailsTO.originOfficeTO.officeId);
			document.getElementById("office").value = manifestDetailsTO.originOfficeTO.officeId;
		}
		
		//set dest office & city
		if(!isNull(manifestDetailsTO.destinationOfficeId)){
			$("#destOffcId").val(manifestDetailsTO.destinationOfficeId);
			$("#destinationOfficeId").val(manifestDetailsTO.destinationOfficeId);
		}
		if(!isNull(manifestDetailsTO.destinationCityId)){
			$("#destCityId").val(manifestDetailsTO.destinationCityId);
		}
		
		for ( var i = 0; i < manifestDetailsTO.inMasterBagManifestDtlsTOs.length; i++) {
			
			document.getElementById("bplNo" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].bplNumber;
			document.getElementById("manifestIds" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].manifestId;
			document.getElementById("bagLockNo" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].bagLackNo;
			document.getElementById("bplNo" + (i + 1)).readOnly = true;
			document.getElementById("weightAW" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].manifestWeight;
			document.getElementById("destCitys" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].destCity.cityName;
			document.getElementById("destCityIds" + (i + 1)).value = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].destCity.cityId;
			
			var docType = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].docType;			
			
			var contentValueArr = document.getElementById("docType"+(i + 1));
			for ( var j = 0; j < contentValueArr.length; j++) {
				var selectObj = contentValueArr[j];
				var selectedVal = selectObj.value;
				var type = selectedVal.split("#")[1];
				if (type == docType) {
					selectObj.selected = 'selected';
				}
			}
		
	
			var splitweightGm = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].manifestWeight
					+ "";
			if (!isNull(splitweightGm)) {
				weightKgValue = splitweightGm.split(".");
				document.getElementById("weightAW" + (i + 1)).value = weightKgValue[0];
				weightGmValue = splitweightGm.split(".")[1];
				if (isNull(weightGmValue)) {
					weightGmValue = "000";
					document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
				} else {
					document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
					fixWeightFormatForGram(document.getElementById("weightGm" + (i + 1)));
				}
			}
			
			$("#remarks" + (i + 1)).val(manifestDetailsTO.inMasterBagManifestDtlsTOs[i].remarks);
			addInManifestRows();
			disableScreen();
		}

		document.getElementById("mbplNo").readOnly = true;
		$("#bplNo1").focus();
	}
	else {
		$("#region").focus();
		//isManifest(); not required separate call
	}
	//to prepare iFrame for print
	/*var mbplNo = document.getElementById("mbplNo").value;
	var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
	var loginOff = document.getElementById("officeName").value;
	var url = './inMasterBagManifest.do?submitName=printMBPLManifest&ManifestNumber='
		+mbplNo+'&LoginOfficeId='+loggedInOfficeId+'&loginOffName='+loginOff;
	printUrl = url;
	printIframe(printUrl);*/
}

function printIframe(printUrl){
	showProcessing();
	document.getElementById("iFrame").setAttribute('src',printUrl);
	hideProcessing();
}
/**
 * disables the screen
 */
function disableScreen() {
	
//	var f = document.forms['inMasterBagManifestForm'];
//	for ( var i = 0, fLen = f.length; i < fLen; i++) {
//
//		f.elements[i].disabled = 'disabled';
//	}
//	document.getElementById('clear').disabled = false;
//	document.getElementById('print').disabled = false;
//	document.getElementById('delete').style.display = 'none';
//	document.getElementById('save').style.display = 'none';

	disableAll();
	buttonEnableById("print");
	buttonEnableById("clear");
}


/**
 * adds new row if there is no existing row
 * @param selectedRowId
 */
function addRow(selectedRowId){	
	var table = document.getElementById("inManifestTable");
	var lastRow = table.rows.length - 1;
	var isNewRow = false;
    var manifestWeightsObj = document.getElementById("manifestWeight" + selectedRowId);
	
	if(isEmptyWeight(manifestWeightsObj.value)){
		var weightKgObj = document.getElementById("weightAW" + selectedRowId);
		var weightGmObj = document.getElementById("weightGm" + selectedRowId);
		weightKgObj.value="";
		weightGmObj.value="";
		clearFocusAlertMsg(weightKgObj,"Please Enter Weight");
		return false;
	}
	
       for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(rowId==selectedRowId && i==lastRow){
			isNewRow = true;
		}
	}
	if(isNewRow){
		addInManifestRows();
		var bplNo = document.getElementById("bplNo"+(selectedRowId+1)); 
		setFocus(bplNo);
	}
}




/**
 * clears the dropdown for the given d
 * @param selectId
 */
function clearDropDownList(selectId) {
	
	document.getElementById(selectId).options.length = 0;
	


}

/**
 * Adds options to the drop down
 * @param selectId
 * @param label to be displayed
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

/**
 * deletes the selected rows for given table Id
 * @param tableId
 */
function deleteTableRow(tableId) {
	   
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for(var i=0; i<rowCount; i++) {    	
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
				if(rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId,i-1);
				//table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
	}catch(e) {
		alert(e);
	}

	updateSerialNoVal('inManifestTable');
	setTotalWeight();
}






/**
 * saves or updates the manifest data
 */
function saveOrUpdateInManifestMBPL(){
	var obj = document.getElementById("weightGm");
	//convertToFractionAW(obj);
	setTotalWeight();
	if (validateMandatoryFields()) {
		showProcessing();
		url = './inMasterBagManifest.do?submitName=saveOrUpdateInMBPL';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#inMasterBagManifestForm").serialize(),
			success : function(req) {
				printCallBackSave(req);
			}
		});
	}
}


/**
 * sets the total weight of all the bags
 */
function setTotalWeight(){
	var totalWeight = 0;
	var table = document.getElementById('inManifestTable');

	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var weight = document.getElementById("manifestWeight" + rowId).value;
		if(!isNull(weight)){
			totalWeight += parseFloat(weight);
		}
	}
	var manifestWeightObj = document.getElementById("totalWeight");
	manifestWeightObj.value = totalWeight;
	fixFormatUptoThreeDecimalPlace(manifestWeightObj);	
	
}


/**
 * fixFormatUptoThreeDecimalPlace
 *
 * @param obj
 * @author uchauhan
 */
function fixFormatUptoThreeDecimalPlace(obj){
	if(obj.value != "" && obj.value != null){
		obj.value=parseFloat(obj.value).toFixed(3);
	}
}


/**
 * displays less/excess baggage msg after save
 * 
 * @param data
 */
function printCallBackSave(msg) {
	hideProcessing();
    alert(msg);
    document.getElementById("region").options.length = 0;
     document.inMasterBagManifestForm.action="/udaan-web/inMasterBagManifest.do?submitName=viewInMBPL";
	 document.inMasterBagManifestForm.submit();
}

/**
 * validates the mandatory fields
 * @returns {true} if all fields are valid
 */
function validateMandatoryFields(){
	var isValid = true;
	var mbplNo = document.getElementById("mbplNo").value;
	var destOfficeName = document.getElementById("officeName").value;
	var region = document.getElementById("region").value;
	var destCity = document.getElementById("destCity").value;
	var destOfficeType = document.getElementById("destOfficeType").value;
	var originOffice = document.getElementById("office").value;
	
	var table = document.getElementById("inManifestTable");

	if (isNull(mbplNo)) {
		alert("Please enter MBPL Number");
		setTimeout(function() {
			document.getElementById("mbplNo").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull(region)) {
		alert("Please select region");
		setTimeout(function() {
			document.getElementById("region").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull(destCity)) {
		alert("Please select Origin City");
		setTimeout(function() {
			document.getElementById("destCity").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull(destOfficeType)) {
		alert("Please select Office Type");
		setTimeout(function() {
			document.getElementById("destOfficeType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull(originOffice)) {
		alert("Please select Origin Office");
		setTimeout(function() {
			document.getElementById("originOffice").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var bplNo = document.getElementById("bplNo" + rowId);
		var weightAW = document.getElementById("manifestWeight" + rowId);
		var destCitys = document.getElementById("destCitys" + rowId);
		var docType = document.getElementById("docType" + rowId);
		

		if (rowId == 1) {
			if (isNull(bplNo.value)) {
				alert("Please enter BPL Number.");
				setTimeout(function() {
					document.getElementById("bplNo" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			if (isEmptyWeight(weightAW.value)) {
				alert("Please Enter Weight.");
				setTimeout(function() {
					document.getElementById("weightAW" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}

			if (isNull(destCitys.value)) {
				alert("Please Enter Destination.");
				setTimeout(function() {
					document.getElementById("destCitys" + rowId).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			
			if (IS_MANDATORY_DOC == 'Y'){
				
				if (isNull(docType.value)) {
					alert("Please Select Document Type.");
					setTimeout(function() {
						document.getElementById("docType" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
				
			}
			
		} else {
			if (!isNull(bplNo.value)) {
				if (isNull(bplNo.value)) {
					alert("Please enter BPL Number.");
					setTimeout(function() {
						document.getElementById("bplNo" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
				if (isEmptyWeight(weightAW.value)) {
					alert("Please Enter Weight.");
					setTimeout(function() {
						document.getElementById("weightAW" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}

				if (isNull(destCitys.value)) {
					alert("Please Enter Destination.");
					setTimeout(function() {
						document.getElementById("destCitys" + rowId).focus();
					}, 10);
					isValid = false;
					return isValid;
				}
				
				if (IS_MANDATORY_DOC == 'Y'){
					
					if (isNull(docType.value)) {
						alert("Please Select Document Type.");
						setTimeout(function() {
							document.getElementById("docType" + rowId).focus();
						}, 10);
						isValid = false;
						return isValid;
					}
					
				}
				
			}
		}

	}

	return isValid;

}

/**
 * gets the details if mbpl number exsists in the database
 */
function isManifest(){
	var mbplNo = document.getElementById("mbplNo").value;
	var processCode = document.getElementById("processCode").value;
	var updatedProcessCode = document.getElementById("updatedProcessCode").value;

	showProcessing();
	url = "./inMasterBagManifest.do?submitName=getManifestDtls&manifestNumber="+mbplNo+"&processCode="+processCode+"&updatedProcessCode="+updatedProcessCode;
	ajaxCallWithoutForm(url,isManifestResponse);
}

/**
 * populates the screen with data
 * @param data
 */
function isManifestResponse(manifest){
	hideProcessing();
	var errorMsg = getErrorMessage(manifest);
	var mbplNoObj = document.getElementById("mbplNo");

	if(!isNull(errorMsg)){
		clearFocusAlertMsg(mbplNoObj, errorMsg);
		return;
	}
	
	if(!isNull(manifest)){
		//var manifest = data;
		document.getElementById("lockNum").value = manifest.lockNum;
		if(!isNull(manifest.destinationOfficeTO)){
			document.getElementById("destOffcId").value = manifest.destinationOfficeTO.officeId;
		}
		document.getElementById("manifestId").value = manifest.manifestId;
		
		clearDropDownList("destCity");
		if(!isNull(manifest.originCityTO)){
			addOptionTODropDown("destCity",
					manifest.originCityTO.cityName,
					manifest.originCityTO.cityId);
			document.getElementById("destCity").value = manifest.originCityTO.cityId;
		}
		
		if(!isNull(manifest.originOfficeTO)){
			var code = manifest.originOfficeTO.officeCode+"-"+manifest.originOfficeTO.officeName;
			clearDropDownList("office");
			addOptionTODropDown("office", code,manifest.originOfficeTO.officeId);
			document.getElementById("office").value = manifest.originOfficeTO.officeId;
		}
		
		var contentValueArr = document.getElementById('region');
		if(!isNull(manifest.originRegionTO)){
			for ( var i = 0; i < contentValueArr.length; i++) {
				var selectObj = contentValueArr[i];
				var selectedVal = selectObj.value;
				
			if (selectedVal == manifest.originRegionTO.regionId) {
					selectObj.selected = 'selected';
				}
			}
		}
		
		var contentValueArr = document.getElementById('destOfficeType');
		if(!isNull(manifest.officeTypeTO)){
			for ( var i = 0; i < contentValueArr.length; i++) {
				var selectObj = contentValueArr[i];
				var selectedVal = selectObj.value;
				if (selectedVal == manifest.officeTypeTO.offcTypeId) {
					selectObj.selected = 'selected';
				}
			}
		}
		/*var docType = manifestDetailsTO.inMasterBagManifestDtlsTOs[i].docType;
		
		
		var contentValueArr = document.getElementById("docType"+(i + 1));
		for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var type = selectedVal.split("#")[1];
		if (type == docType) {
			selectObj.selected = 'selected';
		}
		}*/
		
	}
	
}

/**
 * checks if the bpl number exsists in the database
 * @param obj is the bpl number
 */
function isManifestBPL(obj){
	var processCodeBPL = document.getElementById("processCodeBPL").value;
	var updatedProcessCode = document.getElementById("updatedProcessCodeBPL").value;
	var rowCount = getRowId(obj,"bplNo");
	var bplNo = document.getElementById("bplNo" + rowCount).value;
	var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
	
	if (!isNull(bplNo)) {
		ROW_COUNT = rowCount;
		url = "./inMasterBagManifest.do?submitName=getManifestGridDtls&manifestNumber="+bplNo+"&processCode="+processCodeBPL+"&updatedProcessCode="+updatedProcessCode+'&LoginOfficeId='+loggedInOfficeId;
		showProcessing();
		ajaxCallWithoutForm(url,isManifestBPLResponse);
	}
}


/**
 * validates if the bag is already inmanifested
 * @param rowCount
 */
function isBPLExists(rowCount){
	var bplObj = document.getElementById("bplNo" + rowCount);
	var bplNo = bplObj.value;
	var processCodeBPL = document.getElementById("processCodeBPL").value;
	ROW_COUNT = rowCount;
	
	var loggedInOfficeId = getValueByElementId("loggedInOfficeId");	
	var url = "./inMasterBagManifest.do?submitName=isManifestNumInManifested&manifestNumber="+bplNo+"&loggedInOfficeId="+loggedInOfficeId+"&processCode="+processCodeBPL+"&updatedProcessCode="+processCodeBPL;
	
	if(!isNull(bplNo)){

		if(isDuplicateFieldInGrid(bplObj, "bplNo", "inManifestTable")){
			clearFocusAlertMsg(bplObj, "Duplicate Bag Manifest Number!");
			return;
		}
		
		showProcessing();
		$.ajax({
			url: url,
			success: function(data){isBPLExistsResponse(data);}
		});
	}
}

/**
 * @param data
 * @returns {Boolean}
 */
function isBPLExistsResponse(data){
	hideProcessing();
	var bplObj = document.getElementById("bplNo" + ROW_COUNT);
	if(!isNull(data)){
		clearFocusAlertMsg(bplObj,data);
		return false;
	}
	else{
		isManifestBPL(bplObj);
	}	
}


/**
 * populates the screen with data
 * @param data
 */
function isManifestBPLResponse(manifest){
	hideProcessing();
	var errorMsg = getErrorMessage(manifest);
	var bplNoObj = document.getElementById("bplNo" + ROW_COUNT);
	
	if(!isNull(errorMsg)){
		clearFocusAlertMsg(bplNoObj, errorMsg);
		return;
	}
	
	if(!isNull(manifest)){
		//addRow(ROW_COUNT);\
		document.getElementById("bagLockNo"+ROW_COUNT).value = manifest.bagLackNo;
		//populateWeightInKgGmField(manifest.manifestWeight,ROW_COUNT);
		document.getElementById("bplProcessCode"+ROW_COUNT).value = manifest.bplProcessCode;
		document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
		document.getElementById("manifestIds" + ROW_COUNT).value = manifest.manifestId;
		if(!isNull(manifest.destCity)){
			document.getElementById("destCitys"+ROW_COUNT).value = manifest.destCity.cityName;
			document.getElementById("destCityIds"+ROW_COUNT).value = manifest.destCity.cityId;
		}
		
		if(!isNull(manifest.consignmentTypeTO)){
			$("#consignmentTypeIds" + ROW_COUNT).val(manifest.consignmentTypeTO.consignmentId);
		}
		
		//set origin & dest office
		if(!isNull(manifest.destinationOfficeId)){
			$("#destOfficeIds" + ROW_COUNT).val(manifest.destinationOfficeId);
		}
		if(!isNull(manifest.originOfficeId)){
			$("#originOfficeIds" + ROW_COUNT).val(manifest.originOfficeId);
		}
		validateWeightFromWeighingMachine(ROW_COUNT);
		
		var docType = manifest.docType;		
		
		var contentValueArr = document.getElementById("docType"+ROW_COUNT);
		for ( var i = 0; i < contentValueArr.length; i++) {
			var selectObj = contentValueArr[i];
			var selectedVal = selectObj.value;
			var type = selectedVal.split("#")[1];
			if (type == docType) {
				selectObj.selected = 'selected';
			}
		}
		
	}
	else{
		document.getElementById("receivedStatus" + ROW_COUNT).value = "R";
		document.getElementById("docType" + ROW_COUNT).disabled = false;
		IS_MANDATORY_DOC = 'Y';
	}
}

/**
 * populates the weight in Kg and Gm fileds for given rowid
 * @param weight
 * @param rowId
 */
function populateWeightInKgGmField(weight, rowId){
	weight += "";
	var weightKgObj = document.getElementById("weightAW" + rowId);
	var weightGmObj = document.getElementById("weightGm" + rowId);
	var manifestWeightsObj = document.getElementById("manifestWeight" + rowId);
	
	if(!isNull(weight)){
		weightKgObj.value = weight.split(".")[0];
		weightGmObj.value = weight.split(".")[1];
	}
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	manifestWeightsObj.value = weightKgObj.value+ "."+ weightGmObj.value;
}

/**
 * checks the Gm field for specified format
 * @param weightGmObj
 */
function fixWeightFormatForGram(weightGmObj){
	if(isNull(weightGmObj.value) || weightGmObj.value=="undefined"){
		weightGmObj.value="000";
	}else if(weightGmObj.value.length==1){
		weightGmObj.value += "00";
	}else if(weightGmObj.value.length==2){
		weightGmObj.value += "0";
	}
}

/**
 * checks if there is a duplicate bpl number in the grid
 * @param fieldNameToValidateObj
 * @param fieldNameToValidate
 * @param tableId
 * @returns {Boolean}
 */
function isDuplicateFieldInGrid(fieldNameToValidateObj, fieldNameToValidate, tableId){
	var table = document.getElementById(tableId);
	var selectedRowId  = getRowId(fieldNameToValidateObj , fieldNameToValidate);
	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var fieldValue = document.getElementById(fieldNameToValidate + rowId).value;
		if(fieldNameToValidateObj.value==fieldValue && rowId!=selectedRowId){
			return true;
		}
	}
	return false;
}

/**
 * validates  the city 
 * @param cityObj
 */
function isValidCity(cityObj){
	rowNo = getRowId(cityObj,"destCitys");
	var cityCode = cityObj.value;
	ROW_COUNT = rowNo;
	if(validateCityCode(cityObj)){
		url = './inMasterBagManifest.do?submitName=getCityByCode&cityCode='
			+ cityCode;
	ajaxCallWithoutForm(url, isValidCityResponse);
		
	}
	
}

/**
 * @param data
 */
function isValidCityResponse(cityTO){
	var errorMsg = getErrorMessage(cityTO);
	var destCityObj = document.getElementById("destCitys"+ROW_COUNT);
	
	if(isNull(cityTO)){
		clearFocusAlertMsg(destCityObj, 'Invalid city code');		
		
	} else if(!isNull(errorMsg)){
		clearFocusAlertMsg(destCityObj, errorMsg);
		
	}else{
		document.getElementById("destCityIds"+ROW_COUNT).value = cityTO.cityId;
	}
}

/**
 * validates the format of city code
 * @param cityObj
 * @returns {Boolean}
 */
function validateCityCode(cityObj){
	var cityCode = cityObj.value;
	if(cityCode.length!=3){
		alert('City Code should not be more than 3 charachters');
		return false;
	}
	else
		return true;
}

/**
 * validates the weight for required format
 * @param rowIdd
 */
function validateWeight(rowIdd){
	/*if(!isNull(getValueByElementId("manifestIds"))){
		return;
	}*/
	var weightKgObj = document.getElementById("weightAW" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
    var manifestWeightsObj = document.getElementById("manifestWeight" + rowIdd);
	
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	/*if(weightKgObj.value.length>3){
		clearFocusAlertMsg(weightKgObj,"enter only three digits");
		return;
	}
	if(weightGmObj.value.length>3){
		clearFocusAlertMsg(weightGmObj,"enter only three digits");
		return;
	}
	
	if(isEmptyWeight(manifestWeightsObj.value)){
		clearFocusAlertMsg(manifestWeightsObj,"Please Enter Weight");
		return false;
	}*/

	manifestWeightsObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	
}


/**
 * sets the process code depending on the selected Type
 * @param obj
 */
function setProcessCode(obj){
	rowNo = getRowId(obj,"docType");
	var typeValue = obj.value;
	var type = typeValue.split("#")[1];
	if(type=="DOX"){
		document.getElementById("bplProcessCode"+rowNo).value="IBDX";
		
	}else{
		document.getElementById("bplProcessCode"+rowNo).value="IBPC";
	}
	
}


/**
 * populateDocType
 *
 * @param rowId
 * @author narmdr
 */
function populateDocType(rowId){
	clearDropDownList("docType" + rowId);
	addOptionTODropDown("docType" + rowId, "--Select--", "");
	document.getElementById("docType" + rowId).disabled = true;
	
	var errorMsg = getErrorMessage(consignmentTypeTOArray);
	
	if(isNull(consignmentTypeTOArray)){
		alert("Consignment Type Details not found!");
		return;
	} else if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	
	for(var i=0;i<consignmentTypeTOArray.length;i++) {
		addOptionTODropDown("docType" + rowId, consignmentTypeTOArray[i].consignmentName, 
				consignmentTypeTOArray[i].consignmentId + "#" + 
				consignmentTypeTOArray[i].consignmentCode);
	}
}


/**
 * converts the weight to required format
 * @param obj
 * @param digits
 */
function convertToFractionAW(obj,digits){
	rowNo = getRowId(obj,"weightGm");
	var gmValue=document.getElementById("weightGm"+rowNo).value;
		if(gmValue.length==0){
			document.getElementById("weightGm"+rowNo).value="000";
			gmValue+="000";
		}else if(gmValue.length==1){
			document.getElementById("weightGm"+rowNo).value += "00";
			gmValue+="00";
		}else if(gmValue.length==2){
			document.getElementById("weightGm"+rowNo).value += "0";
			gmValue+="0";
		}
		
	 kgValue = document.getElementById("weightAW"+rowNo).value;
	 document.getElementById("manifestWeight"+rowNo).value=kgValue+"."+gmValue;
	
	 }
	
	



/**
 * gets the cities by selected region
 */
function getCitiesByRegion() {
	var destRegionId = "";
	destRegionId = document.getElementById("region").value;
	url = './inMasterBagManifest.do?submitName=getCitiesByRegion&regionId='
			+ destRegionId;
	ajaxCallWithoutForm(url, printAllCities);
}


/**
 * populates the cities 
 * @param data
 */
function printAllCities(data) {
	var errorMsg = getErrorMessage(data);

	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	
	var content = document.getElementById('destCity');
	content.innerHTML="";
	defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(data)) {
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.cityId;
		option.appendChild(document.createTextNode(this.cityName));
		content.appendChild(option);
	});
	}
}

/**
 * gets all offices by city
 */
function getAllOfficesByCity() {
	var destCityId = "";
	destCityId = document.getElementById("destCity").value;
	url = './inMasterBagManifest.do?submitName=getAllOfficesByCity&cityId='
			+ destCityId;
	ajaxCallWithoutForm(url, printAllOffices);
}


/**
 * populates the Office drop down
 * @param data
 */
function printAllOffices(data) {
	var errorMsg = getErrorMessage(data);

	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	var content = document.getElementById('office');
	content.innerHTML="";
	defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(data)) {
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.officeId;
		option.appendChild(document.createTextNode(this.officeCode+"-"+this.officeName));
		content.appendChild(option);
	});
	}
}

/**
 * gets all offices by city and office type
 */
function getAllOfficesByCityAndOfficeType(){
	
	var destCityId = document.getElementById("destCity").value;	
	var officeTypeId =  document.getElementById("destOfficeType").value;	
	url = './inMasterBagManifest.do?submitName=getAllOfficesByCityAndOfficeType&cityId=' + destCityId + "&officeTypeId=" + officeTypeId;		
	ajaxCallWithoutForm(url, printAllOffices);
	
}

/**
 * delets the table row
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId,rowIndex){ 
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex); 
}

/**
 * onlyNumberAndEnterKeyNav
 *
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 * @author sdalli
 */
function onlyNumberAndEnterKeyNavForInMBPL(e,rowId){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		addRow(rowId);
		var nextRow = rowId + 1;
		return false;
	}else {
		return true;
	}
}


/****************Weighing Machine Integration Start************************/

var inMasterBagModuleName = "inMasterBag";
var inOGMModuleName = "inOGM";

/** The wmWeight */
var wmWeight = 0.00;
var totalWmWeight = 0.00;
/** The isWeighingMachineConnected */
var isWeighingMachineConnected = false;

/**
 * capturedWeight
 *
 * @param data
 * @author narmdr
 */
function capturedWeight(data) {
	var capturedWeight = eval('(' + data + ')');
	if (capturedWeight == -1 || capturedWeight == -2) {
		totalWmWeight = capturedWeight;
		isWeighingMachineConnected = false;		
	}else {
		totalWmWeight = parseFloat(capturedWeight).toFixed(3);
		isWeighingMachineConnected = true;
	}
}

/**
 * validateWeightFromWeighingMachine
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeightFromWeighingMachine(rowIdd){
	//if(isWeighingMachineConnected){
		//getWeightFromWeighingMachineWithParam(capturedWeightWithParam, rowIdd);
		capturedWeightWithParam(-1, rowIdd);
		
	//}
	disableEnableWeightField(rowIdd, isWeighingMachineConnected);
}
/**
 * capturedWeightWithParam
 *
 * @param data
 * @param rowIdd
 * @author narmdr
 */
function capturedWeightWithParam(data, rowIdd) {
	var capturedWeight = eval('(' + data + ')');
	var flag = true;
	if (capturedWeight == -1 || capturedWeight == -2) {
		wmWeight = capturedWeight;
		flag = false;
	} else {
		wmWeight = parseFloat(capturedWeight).toFixed(3);
		var cnWeight = 0;
		cnWeight = totalWmWeight - wmWeight;
		totalWmWeight = wmWeight;
		cnWeight = parseFloat(cnWeight).toFixed(3);
		
		document.getElementById("weightAW" + rowIdd).value = cnWeight.split(".")[0];
		document.getElementById("weightGm" + rowIdd).value = cnWeight.split(".")[1];
		
		document.getElementById("manifestWeight" + rowIdd).value = cnWeight;
		flag = true;
		
		if(moduleName == inMasterBagModuleName){
			setTotalWeight();
			addRow(rowIdd);
		}
		if(moduleName == inOGMModuleName){
			setFinalWeight();
		}
	}
	disableEnableWeightField(rowIdd, flag);
}

/**
 * disableEnableWeightField
 *
 * @param rowIdd
 * @param flag
 * @author narmdr
 */
function disableEnableWeightField(rowIdd, flag){
	document.getElementById("weightAW" + rowIdd).readOnly = flag;
	document.getElementById("weightGm" + rowIdd).readOnly = flag;	
}

/****************Weighing Machine Integration End************************/


function printMbpl(){
	if(confirm("Do you want to print?")){
		var mbplNo = document.getElementById("mbplNo").value;
		var loggedInOfficeId = document.getElementById("loggedInOfficeId").value;
		var loginOff = document.getElementById("officeName").value;
		if(!isNull(mbplNo)){
			var url = './inMasterBagManifest.do?submitName=printMBPLManifest&ManifestNumber='+mbplNo+'&LoginOfficeId='+loggedInOfficeId+'&loginOffName='+loginOff;
			  var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
		} else {
			alert("Please provide MBPL No.");
		}
		/*var mbplNo = document.getElementById("mbplNo").value;
		if(!isNull(mbplNo)){
		window.frames['iFrame'].focus();
		window.frames['iFrame'].print();
		}else {
			alert("Please provide MBPL No.");
		}*/
	}
	
}

function cancelPage(){
	var url = "./inMasterBagManifest.do?submitName=viewInMBPL";
	window.location = url;
}


/**
 * validateDestOriginOffice
 * @author uchauhan
 */
function validateDestOriginOffice(){
	var destOfficeId = document.getElementById("loggedInOfficeId").value;
	var originOfficeObj = document.getElementById("office");
	if(originOfficeObj.value==destOfficeId){
		clearFocusAlertMsg(originOfficeObj, "Origin Office and Destination Office should not be same!");
	}
}

function setConsignmentTypeId(rowId){
	var docTypeArray = $("#docType" + rowId).val().split("#");	
	$("#consignmentTypeIds" + rowId).val(docTypeArray[0]);
	
	if(isNull(docTypeArray)){
		$("#consignmentTypeIds" + rowId).val("");
	}
}