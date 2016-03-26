var isSaved = false;
var isDeleted = false;
var maxWeightAllowed ;
var maxTolerenceAllowed ;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
var OLD_DEST_OFFICE_ID=0;
var NEW_DEST_OFFICE_ID=0;

var gridManifstfourthCharM = /^[M]$/;
var gridManifstfourthCharB = /^[B]$/;

var maxRowAllowed;

var printUrl;
var printFlag="N";
var refreshFlag="N";
var closeAction=null;

function bplBranchOutManifestStartup() {
	maxRowAllowed = parseInt(getDomElementById("maxCNsAllowed").value);
	maxWeightAllowed = getDomElementById("maxWeightAllowed").value;
	maxTolerenceAllowed = getDomElementById("maxTolerenceAllowed").value;
    }

/*@Desc: Adds rows in then grid*/
function addRows(){
	for(var cnt=1;cnt<=maxRowAllowed;cnt++){
		
 	$('#outManifestTable').dataTable().fnAddData( [
 	'<input type="checkbox" id="ischecked'+ cnt +'" name="chkBoxName" onclick="getManifestId(this);" />',
 	cnt,
    '<input type="text" class="txtbox width130" id="manifestNo'+ cnt +'" name="to.manifestNos" maxlength="10" tabindex="-1"  onkeypress="return callEnterKey(event, document.getElementById(\'noOfCons'+ (cnt)+ '\'));" onblur="fnValidateNumber(this);getManifestDtsbyManifestNo(this);focusOnclose();convertDOMObjValueToUpperCase(this);" />',
	'<input type="text"  id="weight'+ cnt +'" readonly="true" class="txtbox width30"  onblur="setModifiedWeight('+cnt+')" size="11"   /><span class="lable">.</span><input type="text"  id="weightGm'+ cnt +'" readonly="true" class="txtbox width30" onblur = "setModifiedWeight('+cnt+')"   size="11"  /><input type="hidden" id="weights'+ cnt +'" name="to.weights"/>',
 	'<input type="text" class="txtbox width130" id="noOfCons'+ cnt +'" name="to.noOfConsignments" readonly=true onkeypress="return callEnterKey(event, document.getElementById(\'manifestNo'+ (cnt+1)+ '\'));" />\
 	<input type="hidden" name="to.consgIds" id="consignmtNo'+cnt+'"/>\
 	<input type="hidden" id="oldWeights'+ cnt +'" name="to.oldWeights" />\
 	<input type="hidden" id="manifestMappedEmbeddeId'+ cnt	+ '" name="to.manifestMappedEmbeddeId" value="" />\
 	<input type="hidden" id="manifestIds'+cnt+ '" name="to.manifestIds" value=""/>\
 	<input type="hidden" id="position'+cnt+'" name="to.position" value="'+cnt+'"/>'
 	   ] );
	}//end of for loop
	
	document.getElementById("bplNo").focus();
 }

/*@Desc: get the manifest details in the grid*/
function getManifestDtsbyManifestNo(manifestNumberObj){
	if(!manifestNumberObj.readOnly && validateHeader()){
	var rowId = getRowId(manifestNumberObj,"manifestNo");
	var selectdDestOffceId = document.getElementById("destOffice").value;
	var headerManifestNo = document.getElementById("bplNo").value;
	//var manifestIds = getDomElementById("manifestIds"+rowId).value;
	if(!manifestNumberObj.value==""){
	manifestNumberObj.value = $.trim(manifestNumberObj.value);
	//TODO validate Manifest Number format 
	if(!isValidManifestForBPL(manifestNumberObj.value)){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		return false;
	}
	
	
	var url = "./bplBranchOutManifest.do?submitName=getManifestDtlsByProcess&manifestNo="+manifestNumberObj.value+
	"&selectdDestOffceId="+selectdDestOffceId+"&headerManifestNo="+headerManifestNo;

/*	if(isDuplicateManifest(manifestNumberObj)){
		alert("Duplicate " + bplMbplNoLabel);
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		return;
	}
	*/
	$.ajax({
		url: url,
		 type:"POST",
		data : jQuery("#bplBranchOutManifestForm").serialize(),
		success: function(req){populateManifest(req, manifestNumberObj);}
	});
	showProcessing();
}
}else{
	manifestNumberObj.value = "";
	manifestNumberObj.focus();
	return false;
}
}

/*@Desc: populates  the manifest details in the grid*/
function populateManifest(req, manifestNumberObj){
	
	if (!isNull(req)) {
		var responseText = jsonJqueryParser(req);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			manifestNumberObj.value = "";
			alert(error);
			manifestNumberObj.value = "";
			setTimeout(function() {
				manifestNumberObj.focus();
			}, 10);

		}else{
	
	hideProcessing();
	var manifestTO = eval('(' + req + ')');
	var rowId = getRowId(manifestNumberObj, "manifestNo");
	flushManifest(manifestTO, rowId);
	//
	//var length=manifestNumberObj.value.length;
	
	/*if(length==12){
		alert('Manifest format is incorrect.Please enter valid manifest');
		manifestNumberObj.value = "";
		setTimeout(function() {
			manifestNumberObj.focus();
		}, 10);
	}*/
	/*else if(isNull(manifestTO)){
		alert('Invalid Manifest');
		manifestNumberObj.value = "";
		setTimeout(function() {
			manifestNumberObj.focus();
		}, 10);
	}*//*else if(manifestTO.manifestEmbedded==true){
			alert('Manifest already embedded');
			manifestNumberObj.value = "";
			setTimeout(function() {
				manifestNumberObj.focus();
			}, 10);*/
	/*}else if(manifestTO.manifestProcessTo.manifestProcessCode!='BOUT'){
		alert('Invalid Manifest');
		manifestNumberObj.value = "";
		setTimeout(function() {
			manifestNumberObj.focus();
		}, 10);
	}*//*else if(manifestTO.destinationOfficeTO!=null && manifestTO.destinationOfficeTO.officeId!=selectdDestOffceId){
		alert('Destination not serviced');
		manifestNumberObj.value = "";
		setTimeout(function() {
			manifestNumberObj.focus();
		}, 10);*/
	
	
	/*else{
			flushManifest(manifestTO, rowId);
			
			//sets the weight in teh grid
			//setTotalWt(rowId);
	}*/
		}
	}
}
		

/*@Desc: populates the manifest details in the grid*/
function flushManifest(bplBranchOutManifestDoxTO, rowId) {
	
	/**For wt integratn*/
	ROW_ID=rowId;
	bookingDetail=bplBranchOutManifestDoxTO;
//	getWtFromWMForOGM();
	capturedWeightForManifest(-1);
	/*var splitweightGm = bplBranchOutManifestDoxTO.bplBranchOutManifestDetailsTOsList[0].weight +"";
	if(!isNull(splitweightGm)){
		weightKgValue = splitweightGm.split(".");
		document.getElementById("weight" + rowId).value = weightKgValue[0];
		weightGmValue = splitweightGm.split(".")[1];
		if(isNull(weightGmValue)){
			weightGmValue="00";
			document.getElementById("weightGm" + rowId).value = weightGmValue;
		}else{
			document.getElementById("weightGm" + rowId).value = weightGmValue;
			
		}
	}
	
	//maxWtCheck(rowId);
	document.getElementById("manifestIds" + rowId).value = bplBranchOutManifestDoxTO.manifestId;
	if (!isNull(bplBranchOutManifestDoxTO.bplBranchOutManifestDetailsTOsList[0].noOfConsignment)) {
		var noOfCons = bplBranchOutManifestDoxTO.bplBranchOutManifestDetailsTOsList[0].noOfConsignment;
		document.getElementById("noOfCons" + rowId).value = noOfCons;
	}*/
}

/*@Desc: search manifest*/
function searchManifest() {
	if($('#outManifestTable').dataTable().fnGetData().length<1){
	addRows();
	}
	//setIFrame();
	var manifestNO = document.getElementById("bplNo").value;
	if(!isNull(manifestNO)){
	var loginOffceID = document.getElementById('loginOfficeId').value;
	url = './bplBranchOutManifest.do?submitName=searchManifestDetails&manifestNo='
			+ manifestNO + "&loginOfficeId=" + loginOffceID;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printCallBackManifestDetails(req);
		}
	});
	showProcessing();
}else{
	alert('Please provide the BPL NO.');
	bplNo.value = "";
	setTimeout(function() {
		bplNo.focus();
	}, 10);
	return false;
}

}

/*@Desc: populates the manifest details */
function printCallBackManifestDetails(ajaxResp) {
	
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			document.getElementById("bplNo").value = "";
			alert(error);
			document.getElementById("bplNo").value = "";
			document.getElementById("bplNo").focus();

		} else {
			//populateSearchDetails(responseText);
		//}
	//}
	
	
	
	
	
	
	
	hideProcessing();
	if(!isNull(ajaxResp)){
	var manifestDetailsTO = eval('(' + ajaxResp + ')');
	if (!isNull(manifestDetailsTO)) {
	// Header Part
		hideProcessing();
	disableHeaderDropDown();
	document.getElementById("dateTime").value = manifestDetailsTO.manifestDate;
	document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
	/*var regionOffice = manifestDetailsTO.destinationOfficeName + "-";
	var loggedInOfcName = manifestDetailsTO.loginOfficeName;
	var setLoginOfficeName = regionOffice.concat(loggedInOfcName);
	document.getElementById("originOffice").value = setLoginOfficeName;*/
	document.getElementById("bagLockNo").value = manifestDetailsTO.bagLockNo;
	document.getElementById("rfidNo").value = manifestDetailsTO.rfidNo;
	document.getElementById("bagRFID").value = manifestDetailsTO.bagRFID;
	document.getElementById("loadNo").value = manifestDetailsTO.loadNo;
    document.getElementById("manifestProcessId").value = manifestDetailsTO.manifestProcessTo.manifestProcessId;
	/*document.getElementById("destinationRegionId").value = manifestDetailsTO.destRegionTO.regionId;

	clearDropDownList("destCity");
	addOptionTODropDown("destCity",
			manifestDetailsTO.destinationCityTO.cityName,
			manifestDetailsTO.destinationCityTO.cityId);
	document.getElementById("destCity").value = manifestDetailsTO.destinationCityTO.cityId;

	document.getElementById("destOfficeType").value = manifestDetailsTO.loginOfficeType;*/

	
	if (manifestDetailsTO.isMulDest == "Y") {
		var officeId = "0";
		var officeName = "ALL";
		clearDropDownList("destOffice");
		addOptionTODropDown("destOffice", officeName, officeId);
		document.getElementById("destOffice").value = manifestDetailsTO.destinationOfficeId;
		createDropDown("destOffice", officeId, officeName);
	} else {
		//clearDropDownList("destOffice");
		/*addOptionTODropDown("destOffice", manifestDetailsTO.destinationOfficeName,
				manifestDetailsTO.destinationOfficeId);*/
		document.getElementById("destOffice").value = manifestDetailsTO.destinationOfficeId;
		
	}
	/*// Set total Weight in header
	var splitweightGm = manifestDetailsTO.finalWeight + "";
	if (!isNull(splitweightGm)) {
		document.getElementById("finalWeight").value = splitweightGm;
		weightKgValue = splitweightGm.split(".");
		document.getElementById("finalKgs").value = weightKgValue[0];
		weightGmValue = splitweightGm.split(".")[1];
		if (isNull(weightGmValue)) {
			weightGmValue = "00";
			document.getElementById("finalGms").value = weightGmValue;
		} else {
			document.getElementById("finalGms").value = weightGmValue;
		}
	}*/
	
	//split and set final weight
	getDomElementById("finalWeight").value = manifestDetailsTO.finalWeight;
	var wt = getDomElementById("finalWeight").value;
	splitWeights(wt,"finalKgs","finalGms","");//4th param is null for final weight
	//disableHeaderDropDown();

	// Grid Details
	for ( var i = 0; i < manifestDetailsTO.bplBranchOutManifestDetailsTOsList.length; i++) {
		document.getElementById("manifestNo" + (i + 1)).value = manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].manifestNo;
		document.getElementById("manifestIds" + (i + 1)).value = manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].manifestId;
		document.getElementById("noOfCons" + (i + 1)).value =manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].noOfConsignment;
		document.getElementById("manifestMappedEmbeddeId" + (i + 1)).value = manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].mapEmbeddedManifestId;
		eachConsgWeightArr[(i+1)]=parseFloat(manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].weight);
		var splitweightGm = manifestDetailsTO.bplBranchOutManifestDetailsTOsList[i].weight
				+ "";
		if (!isNull(splitweightGm)) {
			weightKgValue = splitweightGm.split(".");
			document.getElementById("weight" + (i + 1)).value = weightKgValue[0];
			weightGmValue = splitweightGm.split(".")[1];
			if (isNull(weightGmValue)) {
				weightGmValue = "00";
				document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
			} else {
				document.getElementById("weightGm" + (i + 1)).value = weightGmValue;
			}
		}
		weightFormatForGm(document.getElementById("weightGm" + (i + 1)));
		disableGridRowById((i + 1));
	}
	
	//check if manifestStatus is CLOSE then all fields should be disable exclude PRINT BUTTON
	var manifestStatus = manifestDetailsTO.manifestStatus;
	if(manifestStatus=="C"){
		document.getElementById("manifestStatus").value =manifestStatus;
		disableForClose();
		var manifestNo = getDomElementById("bplNo").value;
		if(closeAction==null){
			alert("Manifest No.: " + manifestNo +' already closed');
		}
	}
	}
	hideProcessing();
	}else {
		hideProcessing();
	alert("No search results found");
	document.getElementById("bplNo").value = "";
	document.getElementById("bplNo").focus();
}
}
}
}

function printIframe(printUrl){
	showProcessing();
	document.getElementById("iFrame").setAttribute('src',printUrl);
	var myFrame = document.getElementById("iFrame");
	if(myFrame){
		printFlag="Y";
	}
	hideProcessing();
}

// saves or closes the manifest
function saveOrCloseBplBranchOutManifest(action) {
	
	var finalKgs=document.getElementById("finalKgs").value; 
	var finalGms=document.getElementById("finalGms").value;
	var finalWt=document.getElementById("finalWeight").value;
	if(isNull(finalKgs) &&  isNull(finalGms)){
		setFinalWeight(finalWt);
}
	if(!isSaved){
	//checks if all the row in the grid are filled
	if(isAllRowInGridFilled(maxRowAllowed)){
		action='close';
	}
	//check weight tolerance during save or close 
	var maxAllowedWtWithTollrence = parseInt(maxWeightAllowed) + 
		(parseInt(maxWeightAllowed) * parseInt(maxTolerenceAllowed) / 100);
	
	if(maxAllowedWtWithTollrence==(getDomElementById("finalWeight").value)) 
		action='close';
	else {
		for(var i=1; i<=maxRowAllowed; i++){
			if(getDomElementById("weights"+i).value > maxAllowedWtWithTollrence)
				action='close';	
		}
	}
	
	if (action == 'save') {
		//setTotalWt('+cnt+');
		// Open
		document.getElementById("manifestStatus").value = "O";
	} else if (action == 'close') {
		// Close
		document.getElementById("manifestStatus").value = "C";
	}
	getDomElementById("action").value = action;
	  enableHeaderDropDown();
	if (validateHeader() && validateGridDetails()) {
		 showProcessing();
		var url = './bplBranchOutManifest.do?submitName=saveOrUpdateBPLBranchOutManifest';
		jQuery.ajax({
			url : url,
			 type:"POST",
			data : jQuery("#bplBranchOutManifestForm").serialize(),
			success : function(req) {
				printCallBackManifestSave(req, action);
			}
		});
		
		

	}
	}else{
		alert('Data already saved');
	}
}

function printCallBackManifestSave(ajaxResp, action) {
	var manifestDetailsTO = null;
	if (!isNull(ajaxResp)) {
	
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);
		} else {
			manifestDetailsTO = responseText;
			document.getElementById("manifestId").value = manifestDetailsTO.manifestId;
			if (action == 'save') {
				alert(manifestDetailsTO.successMessage);
				$('#outManifestTable').dataTable().fnClearTable();
				searchManifest();
				disableForSave();

			} else if (action == 'close') {
				closeAction = action;
				var confirm1 = confirm("Manifest closed successfully.");
				//alert(manifestDetailsTO.successMessage);
				disableForClose();
				if(confirm1){	
					searchManifest();
					printDtls();
					refreshPage();
					/*setTimeout(function(){printDtls();}, 3000);
					setTimeout(function(){
						if(refreshFlag=="Y"){
							refreshPage();
						}
						}, 4000);*/
				}else{
					refreshPage();
				}
			}
		}
	}
	hideProcessing();
}

/*@Desc: deletes  the manifest details in the grid and updates the total weight in the header*/
function deleteManifstDtlsClientSide() {
	var isDel = false;
	var subTotalWeight = 0;
	var weightInFraction = 0.0;
    for(var i = 1 ; i <=maxRowAllowed; i++){
    	var bplNo =  getDomElementById("manifestNo"+i).value;
           if(getDomElementById("ischecked"+i).checked == true && !isNull(bplNo)){
                 //before clearing grid details getting weight
                 var weightKg = document.getElementById("weight" + i).value;
                 weightKg=(isNull(weightKg))?"0":weightKg;
                 var weightGm = document.getElementById("weightGm" + i).value;
                 weightGm=(isNull(weightGm))?"000":weightGm;
                 if (weightGm == undefined) {
                        weightGm = "000";
                 } else if (weightGm.length == 1) {
                        weightGm += "00";
                 } else if (weightGm.length == 2) {
                        weightGm += "0";
                 } else
                        weightGm = weightGm.substring(0, 3);
                 
                 weightInFraction = parseFloat(weightKg) +parseFloat(weightGm)/1000;
                 //Header Total Weight
                 var totalWeight = document.getElementById("finalWeight").value;
                 totalWeight = parseFloat(totalWeight);
                 subTotalWeight = totalWeight*1000-weightInFraction*1000;
                 subTotalWeight /=1000;
                 //Setting final wt after deduction in header and hidden Field
                 document.getElementById("finalWeight").value = subTotalWeight;
                  var finalWeightStr = subTotalWeight+"";
                 weightKgValueFinal = finalWeightStr.split(".");
                 document.getElementById("finalKgs").value = weightKgValueFinal[0];
                 var weightGmValueFinal = weightKgValueFinal[1];
                 if (weightKgValueFinal[1] == undefined) {
                        weightGmValueFinal = "000";
                 } else if (weightKgValueFinal[1].length == 1) {
                        weightGmValueFinal += "00";
                 } else if (weightKgValueFinal[1].length == 2) {
                        weightGmValueFinal += "0";
                 } else 
                        weightGmValueFinal =weightGmValueFinal
                 .substring(0, 3);
                 document.getElementById("finalGms").value =weightGmValueFinal;
                 //setting the weight 0 in global array while deleting
                 eachConsgWeightArr[i] = "0";
                 //Clearing the fields
                 getDomElementById("ischecked" + i).checked = "";
                 getDomElementById("manifestNo"+i).value = "";
                 getDomElementById("manifestIds"+i).value = "";
                 getDomElementById("weights"+i).value="";//hidden field
                 getDomElementById("weight"+i).value = "";
                 getDomElementById("weightGm"+i).value = "";
                 getDomElementById("noOfCons"+i).value = "";
                 /*//enable field for new consignment 
                   getDomElementById("ischecked" + i).checked = "";
                 
                 getDomElementById("checked"+i).checked=false;
                 getDomElementById("consigntNo"+ (i+1)).disabled = false;
                 getDomElementById("weight"+ (i+1)).disabled = false; 
                 getDomElementById("weightGm"+ (i+1)).disabled = false;
                 getDomElementById("pincode"+ (i+1)).disabled = TSfalse;*/
                 isDel = true;
                 isDeleted = true;
                 isSaved=false;
                 enableElement(getDomElementById("manifestNo"+i));
           }
           if(isNull(bplNo)){
      			getDomElementById("ischecked"+i).checked = false;
      		}
    }
if(isDel){
	alert('Record(s) deleted successfully');
}else{
	 alert("Please select a Non Empty row to delete.");
}
}

/*@Desc: shows the message accordingly on delete*/
function printCallBackManifestDelete(data,action) {
	if (data != null && data != "") {
		if (data == "SUCCESS") {
			performSaveOrClose(action);
			//alert("Record deleted successfully.");
		} else if (data == "FAILURE") {
			alert("Exception occurred. Record not deleted successfully.");
		}
		jQuery('#outManifestTable >tbody >tr').each(function(i, tr) {
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if (isChecked) {
				document.getElementById("manifestNo" + (i + 1)).value = "";
				document.getElementById("weight" + (i + 1)).value = "";
				document.getElementById("weightGm" + (i + 1)).value = "";
				document.getElementById("manifestIds" + (i + 1)).value = "";
				document.getElementById("noOfCons" + (i + 1)).value = "";
			}
		});
	}
}

/*@Desc: get the manifest id on check*/
function getManifestId(checkObj) {
	var manifestIdListAtGrid = document.getElementById("manifestIdListAtGrid").value;
	var rowId = getRowId(checkObj, "ischecked");

	if (isNull(manifestIdListAtGrid)) {
		var manifestId = document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	} else {
		var manifestId = manifestIdListAtGrid + ","
				+ document.getElementById("manifestIds" + rowId).value;
		document.getElementById("manifestIdListAtGrid").value = manifestId;
	}
}

/*@Desc: validates the header fields*/
/*function validateHeader(){
	var bplNo = getDomElementById("bplNo");
	var loadNo = getDomElementById("loadNo");
	var bagLockNo = getDomElementById("bagLockNo");
	var rfidNo = getDomElementById("rfidNo");
	var destOffic = getDomElementById("destOffice");
	
	if(isNull(bplNo.value)){
		alert("Please provide Bpl No.");
		setTimeout(function(){bplNo.focus();},10);
		return false;
	}
	if(isNull(loadNo.value)){
		alert("Please select Load No.");
		setTimeout(function(){loadNo.focus();},10);
		return false;
	}
	if(isNull(bagLockNo.value)){
		alert("Please provide bagLock no.");
		setTimeout(function(){bagLockNo.focus();},10);
		return false;
	}

	if(isNull(rfidNo.value)){
		alert("Please provide Rfid NO.");
		setTimeout(function(){rfidNo.focus();},10);
		return false;
	}
	if(isNull(destOffic.value)){
		alert("Please select Destination Office.");
		setTimeout(function(){destOffic.focus();},10);
		return false;
	}
	return true;
}*/

function validateHeader(){
	var msg = "Please provide : ";
	var isValid = true;
	var manifestNo = $("#bplNo")[0];
	var destOfficeId = $("#destOffice")[0];
	var loadNoId = $("#loadNo")[0];
	var bagLockNo = $("#bagLockNo")[0];
	var focusObj = manifestNo;
	
	//var rfidNo = $("#rfidNo")[0];
	if(isNull(manifestNo.value)){
		//alert("Please provide Manifest No.");
		if(isValid)	focusObj = manifestNo;
		msg = msg + ((!isValid)?", ":"") + "Manifest No";
		//setTimeout(function(){manifestNo.focus();},10);
		//return false;
		isValid=false;
	}
	if(isNull(destOfficeId.value)){
		//alert("Please select Destination Office");
		if(isValid)	focusObj = destOfficeId;
		msg = msg + ((!isValid)?", ":"") + "Destination Office";
		//setTimeout(function(){destOfficeId.focus();},10);
		//return false;
		isValid=false;
	}
	if(isNull(loadNoId.value)){
		//alert("Please select Load No.");
		if(isValid)	focusObj = loadNoId;
		msg = msg + ((!isValid)?", ":"") + "Load No";
		//setTimeout(function(){loadNoId.focus();},10);
		//return false;
		isValid=false;
	}
	if(isNull(bagLockNo.value)){
		//alert("Please select Bag Lock No.");
		if(isValid)	focusObj = bagLockNo;
		msg = msg + ((!isValid)?", ":"") + "Bag Lock No";
		//setTimeout(function(){bagLockNo.focus();},10);
		//return false;
		isValid=false;
	}
	if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	return isValid;
}

/*@Desc: disables the header drop down*/
function disableHeaderDropDown(){
	document.getElementById("bplNo").readOnly = true; 
	document.getElementById("loadNo").disabled = true; 
	document.getElementById("bagLockNo").disabled = true; 
	document.getElementById("rfidNo").disabled = true; 
	document.getElementById("destOffice").disabled = true; 
}

/*@Desc: enables the header drop down*/
function enableHeaderDropDown(){
	if(document.getElementById("loadNo").disabled == true){
		document.getElementById("loadNo").disabled = false; 
	} if (document.getElementById("bplNo").disabled == true){
		document.getElementById("bplNo").disabled = false;
	} if (document.getElementById("bagLockNo").disabled == true){
		document.getElementById("bagLockNo").disabled = false;
	}
	if(document.getElementById("destOffice").disabled == true){
		document.getElementById("destOffice").disabled = false; 
	}if(document.getElementById("rfidNo").disabled == true){
		document.getElementById("rfidNo").disabled = false; 
	}
	//document.getElementById("manifestNo").readOnly = false; 
}

/*@Desc: disables Grid By Id*/
function disableGridRowById(domElementId){
	//read only text fields
	disableElement(getDomElementById("manifestNo"+domElementId));
}

/*@Desc: disables the element*/
function disableElement(domElement){
	domElement.setAttribute("readOnly",true);
	domElement.setAttribute("tabindex","-1");
}

/*@Desc: disables on close*/
/*function disableForClose(){
	disableAllCheckBox();
	disableAll();
	buttonEnabled("printBtn","btnintform");
}*/

/*@Desc: disables the check box*/
function disableAllCheckBox(){
	getDomElementById("checkAll").disabled=true;
	for(var i=1; i<=maxRowAllowed; i++){
		getDomElementById("ischecked"+i).disabled=true;
	}
}

/*@Desc: splits weight*/
function splitWeights(wt,wtKgId,wtGmId,rowId){
	var weightArr = wt.split(".");
	var weightKgId = wtKgId + rowId;
	var weightGmId = wtGmId + rowId;
	getDomElementById(weightKgId).value = weightArr[0];
	getDomElementById(weightGmId).value = (!isNull(weightArr[1]))?weightArr[1].substring(0, 3):"";
	if (weightArr[1] == undefined) {
		getDomElementById(weightGmId).value += "000";
	} else if (weightArr[1].length == 1) {
		getDomElementById(weightGmId).value += "00";
	} else if (weightArr[1].length == 2) {
		getDomElementById(weightGmId).value += "0";
	}
}

/*@Desc: sets the total weight*/
function setTotalWt(rowId) {
	var ROW_ID = rowId;
	var finalWeight = 0;
	var weightInFraction = 0;
	var finalAddedWeight;
	var finalWt = document.getElementById("finalWeight").value;
	var weightKg = document.getElementById("weight" + ROW_ID).value;
	var weightGM = document.getElementById("weightGm" + ROW_ID).value;
	if (!isNull(weightKg) || !isNull(weightGM)) {
		weightInFraction = weightKg + "." + weightGM;
		if (!isNull(weightInFraction)) {
			if (weightKg == 0) {
				finalWeight = (weightInFraction);
			} else {
				finalWeight += parseFloat(weightInFraction);
			}
		}
	}
	if (isNull(finalWt)) {
		document.getElementById("finalWeight").value = finalWeight;
		finalAddedWeight = finalWeight;

	} else {
		finalAddedWeight = parseFloat(finalWeight) + parseFloat(finalWt);
		document.getElementById("finalWeight").value = finalAddedWeight;
	}
	finalAddedWeight += "";	
	weightKgValueFinal = finalAddedWeight.split(".");
	
	if(!isNull(weightKgValueFinal[0])){
		document.getElementById("finalKgs").value = weightKgValueFinal[0];
	}else{
		document.getElementById("finalKgs").value = "0";
	}
	if(!isNull(weightKgValueFinal[1])){
		document.getElementById("finalGms").value = weightKgValueFinal[1]
		.substring(0, 3);
	}else if (weightKgValueFinal[1] == undefined) {
		document.getElementById("finalGms").value += "000";
	} else if (weightKgValueFinal[1].length == 1) {
		document.getElementById("finalGms").value += "00";
	} else if (weightKgValueFinal[1].length == 2) {
		document.getElementById("finalGms").value += "0";
	}
}

/*@Desc: sets the total weight*/
function setTotalWeight() {
	var finalWeight = 0;
	var weightInFraction = 0;
	var table = document.getElementById('outManifestTable');
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(6);
		var weightKg = document.getElementById("weight" + rowId).value;
		var weightGM = document.getElementById("weightGm" + rowId).value;
		if (!isNull(weightKg) && !isNull(weightGM)) {
			weightInFraction = weightKg + "." + weightGM;
			if (!isNull(weightInFraction)) {
				finalWeight += parseFloat(weightInFraction);
			}
		}
	}
	finalWeight += "";
	document.getElementById("finalWeight").value = finalWeight;
	weightKgValue = finalWeight.split(".");
	document.getElementById("finalKgs").value = weightKgValue[0];
	var finalGms=weightKgValue[1];
	if(finalGms  == undefined || finalGms  == "undefined" ){
		document.getElementById("finalGms").value = "000";
	}else{
		finalGms=weightKgValue[1].substring(0,2);
		document.getElementById("finalGms").value = finalGms;
	}
}

/*@Desc: validates the manifest no. in the grid*/
function fnValidateNumber(obj) {
	if (!isNull(obj.value)) {
		var count = getRowId(obj, "manifestNo");
		if (!isDuplicateManifest(obj, count)){
			//isValidManifestNo(obj);
		} 
	}
}

/*@Desc: checks for duplicate manifest no. in the grid*/
function isDuplicateManifest(bplNumberObj, count) {
	var isValid = true;
	
	var currentManifest = bplNumberObj.value.toUpperCase();
	var table = document.getElementById('outManifestTable');
	var cntofR = table.rows.length;
	for ( var i = 1; i < cntofR; i++) {
			var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
			var prevManifest = document.getElementById('manifestNo' + id).value;
			if (!isNull(prevManifest.trim()) && !isNull(currentManifest.trim())) {
				if (count != id) {
					if (prevManifest.trim().toUpperCase() == currentManifest.trim()) {
						bplNumberObj.value = "";
						alert("Manifest already entered");
						setTimeout(function() {
							bplNumberObj.focus();
						}, 10);
						isValid = false;
					}
				}
			}
		}
	return isValid;
}

//Validates Manifest number format in the header
function isValidManifestFormat(consgNumberObj) {
	var flag = true;
	if (!isNull(consgNumberObj.value)) {
		var consgNumber = "";
		consgNumber = consgNumberObj.value;
		if (consgNumber.length < 10 || consgNumber.length > 10) {
			consgNumberObj.value = "";
			alert("Manifest number should be of 10 character");			
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			flag = false;
		} 
	}
	return flag;
}

/**
 * @Desc checks if all the rows in the grid is filled
 * @returns {Boolean}
 */
function isAllRowInGridFilled(maxRowAllowed){
	for(var i=1; i<=maxRowAllowed; i++){
		if(isNull(getDomElementById("manifestNo" + i).value))
			return false;
	}
	return true;
}

/**
 * @Desc check wheather header manifestNo is already manifested or not to avoid duplicate entry 
 * into the database
 */
function validateForSaveOrClose(action){
	var flag = true;
	if(action=="close")
		isSaved = false;
	if(!isSaved && validateHeader() && validateGridDetails()) {
		//to check manifest id for saving after update on search
		var manifestId = getDomElementById("manifestId").value;
		if(isNull(manifestId)){
			var bplNo = getDomElementById("bplNo").value;
			var manifestType = getDomElementById("manifestDirection").value;
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var processCode = getDomElementById("processCode").value;
			var url = "./branchOutManifestParcel.do?submitName=isManifested&manifestNo=" + bplNo 
					+ "&manifestType=" + manifestType 
					+ "&loginOfficeId=" + loginOfficeId 
					+ "&processCode=" + processCode;
			
			//showProcessing();
			jQuery.ajax({
				url : url,
				success : function(req) {
					//hideProcessing();
					if(req=="FAILURE"){
						var bplNoObj=getDomElementById("bplNo");
						bplNoObj.value="";
						alert("Manifest number already used to create another manifest.");						
						setTimeout(function() {
							bplNoObj.focus();
						}, 10);
						return false;
					} else {
						checkIfDeletedBeforeSave(action);
					}
				}
			});
		} 
	} 
}

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
/*function validateGridDetails(){
	for(var i=1; i<=maxRowAllowed; i++){
		
		if(i==1){//atleast one row should be filled
			if(!validateSelectedRow(i)) {
				return false;
			}
		} else if(getDomElementById("ischecked"+i).checked){
			if(!validateSelectedRow(i)) {
				return false;
			}
		}
	}
	return true;
}*/

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails(){
	var flag = false;
	//var maxCNsAllowed=getDomElementById("maxCNsAllowed").value;
	for(var i=1; i<=maxRowAllowed; i++){
		if(!isNull(getDomElementById("manifestNo"+i).value)){//ischecked
			/*if(!validateSelectedRow(i)) {
				return false;
			}*/
			flag = true;
		}
	}
	if(!flag)
		alert("Please enter atleast one Manifest");
	return flag;
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var consgNo = getDomElementById("manifestNo"+rowId);
	var lineNum = "at line :"+rowId;
	if(isNull(consgNo.value)){
		alert("Please provide Manifest No. to be scanned "+lineNum);
		setTimeout(function(){consgNo.focus();},10);
		return false;
	}
	return true;
}

/**
 * @Desc print the details 
 */
function printDtls(){
	if(confirm("Do you want print?")){
	var manifestStatus = getDomElementById("manifestStatus").value;
	if(manifestStatus=="O"){
		alert("Only closed manifest can be printed.");
	}else{
		hideProcessing();
		/*var flag= true;
		while(flag){
			if(printFlag=="Y"){
				flag=false;
				refreshFlag="Y";
				window.frames['iFrame'].focus();
				window.frames['iFrame'].print();
				break;
			}
		}*/
		var manifestNo = getDomElementById("bplNo").value;
		var loginOfficeId = getDomElementById("loginOfficeId").value;
		if(!isNull(manifestNo)){
			var url = './bplBranchOutManifest.do?submitName=printManifestDtls&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOfficeId;
			 var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
		} else {
			alert("Please provide BPL No.");
			}
		}
	}
}

/**
 * @desc max weight tolerance check - total wt shld not be > 38.5
 * @param rowId
 * @returns {Boolean}
 */
function maxWtCheck(rowId){
	var maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	var maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
	var maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;
	maxCNsAllowed = parseInt(maxCNsAllowed);
	//Calling common method for max wt chk 
	if(!maxWeightToleranceCheck(rowId,maxCNsAllowed,maxWeightAllowed,maxTolerenceAllowed)){
		document.getElementById("manifestNo" + rowId).value = "";
		document.getElementById("weight" + rowId).value = "";
		document.getElementById("weightGm" + rowId).value = "";
		document.getElementById("noOfCons" + rowId).value = "";
		eachConsgWeightArr[rowId]=0;
		/*for(var i = 0 ; i <= maxCNsAllowed ; i++){
			document.getElementById("consigntNo"+ (i+1)).disabled = true; 
			document.getElementById("weight"+ (i+1)).disabled = true; 
			document.getElementById("weightGm"+ (i+1)).disabled = true;
			document.getElementById("pincode"+ (i+1)).disabled = true;
		} */
		//return false;
	}
}

/**
* @Desc check if delete before save or close
* @param action
*/
function checkIfDeletedBeforeSave(action){
	var manifestId = getDomElementById("manifestId").value;
	//delete the Consg/Manifest/Co-Mail before saving
	if(isDeleted && !isNull(manifestId)){
		deleteTableRow(action);
	}else{
		performSaveOrClose(action);
	}
}

/**
 * @Desc check wheather manifestNo is already manifested or not to avoid duplicate entry 
 * into the database
 */

/*function saveOrCloseBplBranchOutManifest(action){
	if(action=="close") isSaved = false;
	if(!isSaved && validateHeader() && validateGridDetails()) {
		var manifestId = getDomElementById("manifestId").value;
		if(isNull(manifestId)){//create new manifest
			*//** To avoid duplicate enrty into Database *//*
			validateForSaveOrClose(action);
		} else { //save or close after search
			checkIfDeletedBeforeSave(action);
		}
	} else {
		if(isSaved) alert("Data already saved.");
	}
}*/

//Bring the focus on close if all the rows in the grid are filled
function focusOnclose(){
	if(isAllRowInGridFilled(maxRowAllowed)){
	var close = document.getElementById("closeBtn");
	setTimeout(function() {
		close.focus();
	}, 20);
	}
}


function callFocusEnterKey(e){
	rowId=1;
	var focusId=document.getElementById("manifestNo" + rowId);
	return callEnterKey(e, focusId);

}

/**
 * @Desc To enable element (text field)
 * @param domElement
 */
function enableElement(domElement){
	domElement.readOnly = false;
	domElement.setAttribute("tabindex","0");
}

/***For wt integration***/
function capturedWeightForManifest(weigth) {
	//alert('>>>wmCapturedWeight>>>'+wmCapturedWeight);
	hideProcessing();
	var weightInKg = null;
	var weightGm = null;
	
//	wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
//	wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
//	wmCapturedWeight = parseFloat(weigth).toFixed(3);
	
	//alert('machine weight'+wmActualWeight);
//	newWMWt=wmActualWeight;
	
	var response=bookingDetail;
//	if(response!=null && (!isEmptyWeight(newWMWt) || !isEmptyWeight(response.bplBranchOutManifestDetailsTOsList[0].weight))){
	if(response!=null || !isEmptyWeight(response.bplBranchOutManifestDetailsTOsList[0].weight)){
		var rowId = ROW_ID;
		document.getElementById("manifestIds" + rowId).value = response.manifestId;
		if (!isNull(response.bplBranchOutManifestDetailsTOsList[0].noOfConsignment)) {
			var noOfCons = response.bplBranchOutManifestDetailsTOsList[0].noOfConsignment;
			document.getElementById("noOfCons" + rowId).value = noOfCons;
		}
	
		//weighing Machine Integration
		
		var weight = parseFloat(response.bplBranchOutManifestDetailsTOsList[0].weight).toFixed(3);
		getDomElementById("oldWeights"+rowId).value = weight; //added for manifestWT updation
		
		/*if(!isNull(newWMWt) && parseFloat(newWMWt)>weight){
			weight=newWMWt;
		}*/
		
		//jQuery("#weights" + rowId).val(weight);
		getDomElementById("weights"+rowId).value = weight;
		
		var splitweightGm = weight +"";
		
		//sets the weight in the grid
		if(!isNull(splitweightGm)){
			var weightKgValue = splitweightGm.split(".");
			document.getElementById("weight" + rowId).value = weightKgValue[0];
			weightGmValue = splitweightGm.split(".")[1];
			if(isNull(weightGmValue)){
				weightGmValue="00";
				document.getElementById("weightGm" + rowId).value = weightGmValue;
			}else{
				document.getElementById("weightGm" + rowId).value = weightGmValue;
				
			}
		}
		

		//checks for weight format
		weightFormatForGm(getDomElementById("weightGm"+rowId));
		maxWtCheck(rowId);
		isSaved = false;
		
	}
}

/**
 * To check whether grid is empty or not
 * 
 * @param domElementId
 */
function checkGridEmpty(domElement, domElementId){
	var flag = false;
	var isFilled = false;
	NEW_DEST_OFFICE_ID = domElement.value;
	if(isNull(OLD_DEST_OFFICE_ID)) 
		OLD_DEST_OFFICE_ID = NEW_DEST_OFFICE_ID;
	//check whether any record(s) present in grid or not
	var maxCNsAllowed = getDomElementById("maxCNsAllowed").value;
	for(var i=1; i<=maxCNsAllowed; i++){
		if(!isNull(getDomElementById(domElementId + i).value)){
			isFilled = true;
			flag = confirm("Manifest number(s) already entered in the grid.\n\nDo you still want to make the changes in header?");
			break;
		}
	}
	//clear the non-empty row
	if(flag){
		for(var i=1; i<=maxCNsAllowed; i++){
			if(!isNull(getDomElementById(domElementId + i).value)){
				if(!isEmptyWeight(getDomElementById("weights"+i).value)) 
					decreaseWeight(i);
				//clearGridRowDetails(i);
				 getDomElementById("manifestNo"+i).value = "";
				 getDomElementById("manifestIds"+i).value="";
				 getDomElementById("noOfCons"+i).value = "";
                 getDomElementById("weights"+i).value="";//hidden field
             	getDomElementById("weight"+i).value="";
             	getDomElementById("weightGm"+i).value="";
                 
                /* $("#LCAmount"+i).val("");
         		$("#bankName"+i).val("");
         		$("#consigntNo"+i).val("");*/
			}
		}
		OLD_DEST_OFFICE_ID = NEW_DEST_OFFICE_ID;
		setValue = NEW_DEST_OFFICE_ID;
	} else {
		if(isFilled) 
			setValue = OLD_DEST_OFFICE_ID;
		else 
			setValue = NEW_DEST_OFFICE_ID;
	}
	domElement.value = setValue;
		
	return flag;
}

/**
 * To decrease the weight from final weight
 * @param rowId
 */
function decreaseWeight(rowId){
	var subTotalWeight = 0.000;
	// before clearing grid details getweight
	var weightKg = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId).value;
	var weightInFraction =((weightKg) + (weightGm))/1000;
	
	// Header Total Weight
	var totalWeight = parseFloat(document.getElementById("finalWeight").value);
	subTotalWeight = totalWeight*1000 - parseFloat(weightInFraction*1000);
	subTotalWeight /= 1000;
	if(isEmptyWeight(subTotalWeight)){
		subTotalWeight=0.000;
	}

	// Setting final wt after deduction in header and hidden Field
	document.getElementById("finalWeight").value = parseFloat(subTotalWeight);
	
	var finalWeightStr = subTotalWeight + "";
	weightKgValueFinal = finalWeightStr.split(".");
	document.getElementById("finalKgs").value = weightKgValueFinal[0];
	if(!isNull(weightKgValueFinal[1]))
		document.getElementById("finalGms").value = weightKgValueFinal[1].substring(0,3);
	if (weightKgValueFinal[1] == undefined) {
		document.getElementById("finalGms").value = "000";
	} else if (weightKgValueFinal[1].length == 1) {
		document.getElementById("finalGms").value += "00";
	} else if (weightKgValueFinal[1].length == 2) {
		document.getElementById("finalGms").value += "0";
	}

	// setting the weight 0 in global array while deleting
	eachConsgWeightArr[rowId] = 0;
}


function cancelPage(){
	var url = "./bplBranchOutManifest.do?submitName=viewBPLBranchOutManifest";
	window.location = url;
}

function refreshPage() {
	var url = "./bplBranchOutManifest.do?submitName=viewBPLBranchOutManifest";
	window.location = url;
	/*document.bplBranchOutManifestForm.action = url;
	document.bplBranchOutManifestForm.submit();*/
}

/**
 * To clear page
 */
function clearPage(){
	if(confirm("Do you want to clear the page?")){
		var url = "./bplBranchOutManifest.do?submitName=viewBPLBranchOutManifest";
		window.location = url;
		/*document.bplBranchOutManifestForm.action = url;
		document.bplBranchOutManifestForm.submit();*/
	}
}

function setModifiedWeight(rowId){
	var wtKg = getDomElementById("weight"+rowId).value;
	var wtGm = getDomElementById("weightGm"+rowId).value; 
	//getDomElementById("weights"+rowId).value=wtKg+wtGm; 

}

function isValidManifestForBPL(gridManifestNo){
	var letter4 = gridManifestNo.substring(3, 4);
		if (letter4.match(gridManifstfourthCharM)||letter4.match(gridManifstfourthCharB)) {
			alert('Manifest No of this format not allowed in the grid');
			return false;
		}else{
			return true;
		}
}

function setIFrame(){
	var manifestNo = getDomElementById("bplNo").value;
	if (!isNull(manifestNo)) {
		var loginOfficeId = getDomElementById("loginOfficeId").value;
		var url = './bplBranchOutManifest.do?submitName=printManifestDtls&manifestNo='
			+ manifestNo + "&loginOfficeId=" + loginOfficeId;
		printUrl = url;
		printIframe(printUrl);
		}
}


function isValidManifestNoForBout(manifestNoObj, manifestScanlevel) {

	var isValidReturnVal = true;
	if (!isNull(manifestNoObj.value)) {
		var stockItemNo = manifestNoObj.value;
		var regionCode = manifestNoObj.value.substring(0, 3);
		var loginCityCode = (getDomElementById("loginCityCode").value)
				.toUpperCase();
		var seriesType = getDomElementById("seriesType").value;
		var officeCode = getDomElementById("officeCode").value;
		var manifestCity = manifestNoObj.value.substring(0, 3).toUpperCase();

		if (manifestNoObj.value.length < 10 || manifestNoObj.value.length > 10) {
			manifestNoObj.value = "";
			alert('Manifest No. must contain atleast 10 characters');
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			isValidReturnVal = false;
		} else {
			if (manifestCity == loginCityCode) {
				if (!isNull(OGM_SERIES) && seriesType == OGM_SERIES) {
					if (!numpattern.test(manifestNoObj.value.substring(3))) {
						manifestNoObj.value = "";
						alert('Manifest number format is not correct');
						setTimeout(function() {
							manifestNoObj.focus();
						}, 10);
						isValidReturnVal = false;
					}
				} else if (!isNull(MBPL_SERIES) && seriesType == MBPL_SERIES) {
					isValidReturnVal = mbplValidation(manifestNoObj);
				} else if (!isNull(BPL_SERIES) && seriesType == BPL_SERIES) {
					isValidReturnVal = bplValidation(manifestNoObj);
				}
			} else {
				manifestNoObj.value = "";
				alert('Manifest does not belong to this city');
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				isValidReturnVal = false;
			}
		}
	

				
		  if (manifestScanlevel != 'G' && isValidReturnVal == true)
			stockValidation(manifestNoObj, loginCityCode, regionCode,
					officeCode, manifestScanlevel, seriesType);
		

	} else {
		// alert('Please enter the Manifest number');
		// manifestNoObj.value = "";
		// setTimeout(function() {manifestNoObj.focus();}, 10);
		isValidReturnVal = false;
	}
	if (isValidReturnVal == true && manifestScanlevel == 'H') {
		var manifestNo = manifestNoObj.value;
		var loginOffceID = getDomElementById('loginOfficeId').value;
		var manifestProcessCode = getDomElementById('processCode').value;
		url = './outManifestParcel.do?submitName=isManifestExist&manifestNo='
				+ manifestNo + "&loginOfficeId=" + loginOffceID
				+ "&manifestProcessCode=" + manifestProcessCode;
		// showProcessing();
		ajaxCallWithoutForm(url, printIsExistforBout);
		/*
		 * jQuery.ajax({ url : url, type: "POST", success : function(req) {
		 * isValidReturnVal = printIsExist(req); } });
		 */
	}

	return isValidReturnVal;
}

function printIsExistforBout(ajaxRes) {
	// hideProcessing();
	if (!isNull(ajaxRes)) {
		if (isJsonResponce(ajaxRes)) {
			var manifestNoObj = document.getElementById("bplNo");
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	}
	return true;
}
