/**
 * prepare page
 */
$(document).ready(function (){
	var oTable = $('#editReceiveDetailsGrid').dataTable({
		"sScrollY": "100%",
		"sScrollX": "100%",
		"sScrollXInner":"150%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	});
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});
	editBagStartup();
});
	
/**
 * cancelEditBagDetails
 *
 */
function cancelEditBagDetails(){
	
	if (promptConfirmation("cancel")){
		showProcessing();
		var url = "./loadReceiveEditBag.do?submitName=viewLoadReceiveEditBag";
	    document.loadReceiveEditBagForm.action = url;
	    document.loadReceiveEditBagForm.submit();
	}
}
/**
 * findEditBagDetails
 *
 */
function findEditBagDetails(){
	
	var bplMbplNo = getDomElementById("loadNumber");
	
	//validate BPL/MBPL No. (OR Manifest Number) format 
	if(!isValidManifestNo(bplMbplNo)){
		bplMbplNo.value = "";
		bplMbplNo.focus();
		return;
	} else {
		showProcessing();
		var url = "./loadReceiveEditBag.do?submitName=findLoadReceiveEditBagDetails";
		document.loadReceiveEditBagForm.action = url;
		document.loadReceiveEditBagForm.submit();
	}
}
/**
 * editBagStartup
 *
 */
function editBagStartup(){
	
	var weight = getDomElementById("weight");
	if(!isNull(weight)){
		splitWeight();
		getDomElementById("weightKg").focus();
		getWeightFromWeighingMachine4Edit();
	}
	disableSearch();
}
/**
 * disableSearch
 *
 */
function disableSearch(){

	var loadConnectedId = getDomElementById("loadConnectedId"); 
	if(!isNull(loadConnectedId) && !isNull(loadConnectedId.value)){
		var loadNum = getDomElementById("loadNumber");
		loadNum.setAttribute("readOnly",true);
		loadNum.setAttribute("tabindex","-1");
		getDomElementById("Submit").disabled = false;
		focusById("weightKg");
	}else{
		focusById("loadNumber");
		getDomElementById("Submit").disabled = true;
	}
}
/**
 * splitWeight
 *
 */
function splitWeight(){
	
	var weight = getDomElementById("weight");
	var weightGmObj = getDomElementById("weightGm");
	var weightKgObj = getDomElementById("weightKg");
	
	if(!isNull(weight.value)){
		var weightString = weight.value + "";
		var weightArr = weightString.split(".");
		weightKgObj.value = weightArr[0];
		weightGmObj.value = weightArr[1];
		fixWeightFormatForGram(weightGmObj);
	}
}
/**
 * saveDetails
 *
 */
function saveDetails() {
	
	if(checkMandatoryFields() && promptConfirmation("save/update")){
		showProcessing();
		var url = "./loadReceiveEditBag.do?submitName=saveOrUpdateLoadReceiveEditBagDetails";
		document.loadReceiveEditBagForm.action = url;
		document.loadReceiveEditBagForm.submit();
	}
}
/**
 * checkMandatoryFields
 *
 * @returns {Boolean}
 */
function checkMandatoryFields(){
	
	var weight = getDomElementById("weight");
	var wKg = getDomElementById("weightKg");
	var wGm = getDomElementById("weightGm");
	var lockNum = getDomElementById("lockNumber");
	var transNum = getDomElementById("transportNumber");
	
	if(isNull(wKg.value)){
		if(isNull(wGm.value) || parseFloat(weight.value)==0.000){
			alert("Please provide Weight Gm(s)");
			setTimeout(function(){wGm.focus();},10);
			return false;
		}
	}
	if(isNull(lockNum.value)){
		alert("Please provide Lock Number");
		setTimeout(function(){lockNum.focus();},10);
		return false;
	}
	if(isNull(transNum.value)){
		alert("Please provide Flight/Train/Vehicle Number");
		setTimeout(function(){transNum.focus();},10);
		return false;
	}
	return true;
}
/**
 * validateWeight
 *
 */
function validateWeight(){

	var weightKgObj = document.getElementById("weightKg");
	var weightGmObj = document.getElementById("weightGm");
	var weightObj = document.getElementById("weight");
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	weightObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	getDomElementById("weightTolerance").value = isWeightTolerance()?"Y":"N";
}
/**
 * isWeightTolerance
 *
 * @returns {Boolean}
 */
function isWeightTolerance(){
	
	var manifestWeight = getDomElementById("manifestWeight").value;
	var weight = getDomElementById("weight").value;
	var tenPerMore = eval(manifestWeight) + (manifestWeight)*(10/100);
	
	if(weight > tenPerMore){ //new weight is 10% more than actual manifest weight 
		return true;
	}
	return false;
}
/**
 * findOnEnterKey
 *
 * @param evt
 * @param objId
 * @returns {Boolean}
 */
function findOnEnterKey(evt,objId){
	
	if(evt==13){
		getDomElementById(objId).click();
		return true;
	}
	return false;
}

/****************Weighing Machine Integration Start************************/

/**
 * getWeightFromWeighingMachine4Edit
 *
 * @author narmdr
 */
function getWeightFromWeighingMachine4Edit(){
	getWeightFromWeighingMachineWithParam(capturedWeightForEdit);
}
function capturedWeightForEdit(data) {
	var capturedWeight = eval('(' + data + ')');
	var flag = true;
	if (isNull(capturedWeight) || capturedWeight == -1 || capturedWeight == -2) {
		wmWeight = capturedWeight;
		flag = false;
	} else {
		wmWeight = parseFloat(capturedWeight).toFixed(3);
		document.getElementById("weightKg").value = wmWeight.split(".")[0];
		document.getElementById("weightGm").value = wmWeight.split(".")[1];
		flag = true;
	}
	disableEnableWeightField4Edit(flag);
}

/**
 * disableEnableWeightField4Edit
 *
 * @param flag
 * @author narmdr
 */
function disableEnableWeightField4Edit(flag){
	document.getElementById("weightKg").readOnly = flag;
	document.getElementById("weightGm").readOnly = flag;	
}

/****************Weighing Machine Integration End************************/