//var ROW_COUNT = 30;

var maxCNsAllowed;
var maxWeightAllowed;
var maxTolerenceAllowed;

var PIECES_NEW = 0;
var PIECES_OLD = 0;
var PIECES_ACTUAL = 0;

var OLD_CONSG_NO = new Array();
var NEW_CONSG_NO = new Array();

var OLD_DEST_OFFICE_ID=0;
var NEW_DEST_OFFICE_ID=0;

var isSaved = false;
var isDeleted = false;
var isAutoClose = false;

var cnWeightFromWM = 0.000;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

var closeAction=null;

var printUrl;
/**
 * @Desc Add row to grid table on page load
 */
function addRows() {
	for(var cnt=1;cnt<=maxCNsAllowed;cnt++){
 	$('#branchOutManifestParcelTable').dataTable().fnAddData( [
 	        '<input type="checkbox" id="ischecked'+ cnt +'" name="to.checkbox" tabindex="-1" onclick="getConsgIdOnCheck(this);" />',
 			cnt,
 			'<input type="text" id="consgNo'+ cnt +'" name="to.consgNos"  maxlength="12"  class="txtbox width130" onkeypress="return enterkeyNavigationFunctionForBranchManifst(this,event);"  onchange="getConsignmentDetails(this);" />\
 			 <input type="hidden" id="consgId'+ cnt +'" name="to.consgIds" />',
 			'<input type="text" id="noOfPieces'+ cnt +'" name="to.noOfPcs" class="txtbox width30"  maxlength="4" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'consgNo'+ (cnt+1)+ '\');" onblur="redirectToChildCNPage('+ cnt +')" />\
 			 <input type="hidden" id="isCnUpdated' + cnt + '" name="to.isCnUpdated" />\
 			 <input type="hidden" id="childCns' + cnt + '" name="to.childCns" />\
 			 <input type="hidden" id="bookingType' + cnt + '" name="to.bookingTypes" />',
 			'<input type="text" id="weight'+ cnt +'" maxlength="3" class="txtbox width30" readonly="true" tabindex="-1" /><span class="lable">.</span><input type="text" id="weightGm'+ cnt +'" maxlength="3" class="txtbox width30" readonly="true" tabindex="-1" /><input type="hidden" id="weights'+ cnt +'" name="to.weights" /><input type="hidden" id="oldWeights'+ cnt +'" name="to.oldWeights" />',
 			'<input type="text" id="cnContent'+ cnt +'" name="to.cnContent" class="txtbox width100" tabindex="-1" readonly="true"  />\
 			 <input type="hidden" id="cnContentId'+ cnt +'" name="to.cnContentIds" />',
 			'<input type="text" id="declaredValue'+ cnt +'" name="to.declaredValues" class="txtbox width100" tabindex="-1" readonly="true" />',
 			'<input type="text" id="paperWork'+ cnt +'" name="to.paperWork" class="txtbox width100" tabindex="-1" readonly="true" />\
 			 <input type="hidden" id="paperWorkId'+ cnt +'" name="to.paperWorkIds" />',
 			'<input type="text" id="toPayAmt'+ cnt +'" name="to.toPayAmts" class="txtbox width100" tabindex="-1" readonly="true"  />',
 			'<input type="text" id="codAmt'+ cnt +'" name="to.codAmts" class="txtbox width100" tabindex="-1" readonly="true"  />',
 			'<input type="text" id="customDutyAmt'+ cnt +'" name="to.customDutyAmts" class="txtbox width100" tabindex="-1" readonly="true" />',
 			'<input type="text" id="serviceCharge'+ cnt +'" name="to.serviceCharges" class="txtbox width100" tabindex="-1" readonly="true" />',
 			'<input type="text" id="stateTax'+ cnt +'" name="to.stateTaxes" class="txtbox width100" tabindex="-1" readonly="true"  />\
 			 <input type="hidden" id="consgManifestedIds'	+ cnt + '" name="to.consgManifestedIds" value="" />\
 			<input type="hidden" id="position'+ cnt +'" name="to.position" value="'+ cnt +'" />\
 			<input type="hidden" id="gridOriginOfficeId'+ cnt +'" name="to.gridOriginOfficeId" />'	//added by niharika
 	
 			 
 			 ] );
	}//end of for loop
	setTimeout(function(){jQuery("#manifestNo").focus();},10);
}
/**
 * @Desc call on page load
 */
function branchOutManifestParcelStartup(){
	maxCNsAllowed = jQuery("#maxCNsAllowed").val();
	maxWeightAllowed = jQuery("#maxWeightAllowed").val();
 	maxTolerenceAllowed = jQuery("#maxTolerenceAllowed").val();
 	addRows();
}
/**
 * @Desc search manifest details by manifest number
 */
function searchDtls(){
	if($('#branchOutManifestParcelTable').dataTable().fnGetData().length<1){
		addRows();
	}
	var manifestNo = jQuery("#manifestNo").val();
	var loginOfficeId = jQuery("#loginOfficeId").val();
	
	if(!isNull(manifestNo)){
		
		var url = './branchOutManifestParcel.do?submitName=searchManifestDtls&manifestNo='
			+ manifestNo + "&loginOfficeId=" + loginOfficeId;
		
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateManifestDetails(req);
			}
		});
	} else {
		alert("Please provide branch manifest number");
	}
}
/**
 * @Desc After getting ajax response successfully, populate manifest details 
 * on screen
 * @param ajaxResp
 */
function populateManifestDetails(ajaxResp){
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			document.getElementById("manifestNo").value = "";
			alert(error);			
			document.getElementById("manifestNo").focus();

		} else {


			var data = eval('('+ajaxResp+')');

			//populate header details...
			jQuery("#destinationOfficeId").val(data.destinationOfficeId);
			//jQuery("#loadNoId").val(data.manifestProcessTo.loadNo);
			jQuery("#loadNoId").val(data.loadNoId);
			//jQuery("#bagLockNo").val(data.manifestProcessTo.bagLockNo);
			jQuery("#bagLockNo").val(data.bagLockNo);
			jQuery("#manifestDate").val(data.manifestDate);

			//hidden details
			jQuery("#manifestId").val(data.manifestId);
			jQuery("#manifestStatus").val(data.manifestStatus);
			jQuery("#manifestProcessId").val(data.manifestProcessTo.manifestProcessId);

			//populate RFID
			jQuery("#rfidNo").val(data.rfidNo);
			jQuery("#bagRFID").val(data.bagRFID);//hidden field
			disableHeaderForSearch();//disabled after search

			//split and set final weight
			jQuery("#finalWeight").val(data.finalWeight);
			var wt = jQuery("#finalWeight").val();
			splitWeights(wt,"finalKgs","finalGms","");//4th param is null for final weight

			//populate grid details...
			for(var i=0; i<data.branchOutManifestParcelDetailsList.length; i++){
				var listObj = data.branchOutManifestParcelDetailsList[i];
				var rowId = i+1;
				setConsignmentDetails(listObj,rowId);
				//set global array for weight tolerance
				eachConsgWeightArr[rowId] = parseFloat(listObj.weight);
				disableGridRowById(rowId);//disabled already existing record(s)
			}//end for loop

			//check if manifestStatus is CLOSE then all fields should be disable exclude PRINT BUTTON
			var manifestStatus = data.manifestStatus;
			if (manifestStatus == "C") {
				//disableForClose();
				// var manifestNo = jQuery("#manifestNo").val();
				//alert("Given Branch Manifest No. is already Closed.");
				
				document.getElementById("manifestStatus").value =manifestStatus;
				// disables the buttons if manifest status is close
				disableForClose();
				var manifestNo = getDomElementById("manifestNo").value;
				if(closeAction==null){
				alert("Branch OutManifest No.: " + manifestNo + ' already closed');
				}
				
			}

			isSaved = true;
			hideProcessing();
		}
	}
	/*var manifestNoObj = $("#manifestNo")[0];
	if (!isNull(manifestNo)) {
		var loginOfficeId = jQuery("#loginOfficeId").val();
		var url ="./branchOutManifestParcel.do?submitName=printManifestDtls&manifestNo="+ 
		manifestNoObj.value + "&loginOfficeId=" + loginOfficeId;
		printUrl = url;
		printIframe(printUrl);
		}*/
	hideProcessing();
}

function printIframe(printUrl){
	showProcessing();
	document.getElementById("iFrame").setAttribute('src',printUrl);
	hideProcessing();
}
/**
 * @Desc setting details in grid
 * @param listObj
 * @param rowId
 */
function setConsignmentDetails(listObj,rowId){
	jQuery("#consgNo"+rowId).val(listObj.consgNo);

	jQuery("#noOfPieces"+rowId).val(listObj.noOfPcs);
	jQuery("#childCns"+rowId).val(listObj.childCn);//hidden
	jQuery("#isCnUpdated"+rowId).val("N");//hidden
	PIECES_OLD = jQuery("#noOfPieces"+rowId).val();
	PIECES_ACTUAL = jQuery("#noOfPieces"+rowId).val();
		
	jQuery("#weights"+rowId).val(listObj.weight);//hidden field
	//split weight
	var wt = jQuery("#weights"+rowId).val();
	splitWeights(wt,"weight","weightGm",rowId);
	
	if(!isNull(listObj.cnContentId)){
		if(listObj.cnContent=='Others'){
			//jQuery("#cnContent"+rowId).val(listObj.otherCNContent);
			jQuery("#cnContent"+rowId).val(listObj.cnContent);
		}else{
			jQuery("#cnContent"+rowId).val(listObj.cnContent);
		}
		
		jQuery("#cnContentId"+rowId).val(listObj.cnContentId);//hidden
	} else {
		jQuery("#cnContent"+rowId).val("OTHERS");
		
		jQuery("#cnContentId"+rowId).val("");//hidden
	}
	
	jQuery("#declaredValue"+rowId).val(listObj.declaredValue);
	jQuery("#paperWork"+rowId).val(listObj.paperWork);
	jQuery("#paperWorkId"+rowId).val(listObj.paperWorkId);//hidden
	jQuery("#toPayAmt"+rowId).val(listObj.toPayAmt);
	jQuery("#codAmt"+rowId).val(listObj.codAmt);
	jQuery("#customDutyAmt"+rowId).val(listObj.customDutyAmt);
	jQuery("#stateTax"+rowId).val(listObj.stateTax);
	jQuery("#serviceCharge"+rowId).val(listObj.serviceCharge);
	
	//setting hidden fields
	jQuery("#consgId"+rowId).val(listObj.consgId);
	jQuery("#bookingType"+rowId).val(listObj.bookingType);
	jQuery("#consgManifestedIds"+rowId).val(listObj.consgManifestedId);
}
/**
 * @Desc clear details in grid
 * @param domElementId
 */
function clearGridRowDetails(domElementId){
	jQuery("#consgId"+domElementId).val("");//hidden field
	jQuery("#consgNo"+domElementId).val("");
	jQuery("#noOfPieces"+domElementId).val("");
	jQuery("#isCnUpdated"+domElementId).val("");//hidden field
	jQuery("#childCns"+domElementId).val("");//hidden field
	jQuery("#weights"+domElementId).val("");//hidden field
	jQuery("#weight"+domElementId).val("");
	jQuery("#weightGm"+domElementId).val("");
	jQuery("#cnContent"+domElementId).val("");
	jQuery("#cnContentId"+domElementId).val("");//hidden field
	jQuery("#declaredValue"+domElementId).val("");
	jQuery("#paperWork"+domElementId).val("");
	jQuery("#paperWorkId"+domElementId).val("");//hidden field
	jQuery("#toPayAmt"+domElementId).val("");
	jQuery("#codAmt"+domElementId).val("");
	jQuery("#customDutyAmt"+domElementId).val("");
	jQuery("#serviceCharge"+domElementId).val("");
	jQuery("#stateTax"+domElementId).val("");
	jQuery("#bookingType"+domElementId).val("");//hidden
	//jQuery("#ischecked"+domElementId).val("");
	jQuery("#ischecked"+domElementId).attr("checked",false);
}

/**
 * @Desc To enable header drop down menus 
 */
function enableHeaderDropDown() {
	if(jQuery("#loadNoId").attr("disabled") == true) {
		jQuery("#loadNoId").attr("disabled", false); 
	}
	if(jQuery("#destinationOfficeId").attr("disabled") == true) {
		jQuery("#destinationOfficeId").attr("disabled", false);
	}
}
/**
 * @Desc To disable header drop down menus 
 */
function disableHeaderDropDown() {
	jQuery("#loadNoId").attr("disabled", true); 
	jQuery("#destinationOfficeId").attr("disabled", true);
}
/**
 * @Desc After details searched successfully: call disableHeaderForSearch function 
 */
function disableHeaderForSearch(){
	//read only search text field
	var manifestNo = $("#manifestNo")[0];
	disableElement(manifestNo);
	var bagLockNo = $("#bagLockNo")[0];
	disableElement(bagLockNo);
	var rfidNo = $("#rfidNo")[0];
	disableElement(rfidNo);
	//disable drop downs
	disableHeaderDropDown();
	//disable search button
	jQuery("#Find").attr("disabled", true);
}
/**
 * @Desc To disable grid by rowId
 * @param domElementId
 */
function disableGridRowById(domElementId){
	//read only text fields
	disableElement($("#consgNo"+domElementId)[0]);
	disableElement($("#noOfPieces"+domElementId)[0]);
	disableElement($("#weight"+domElementId)[0]);
	disableElement($("#weightGm"+domElementId)[0]);
}
/**
 * @Desc To make element read only (text field)
 * @param domElement
 */
function disableElement(domElement){
	domElement.readOnly = true;
	domElement.setAttribute("tabindex","-1");
}
/**
 * @Desc To enable grid by rowId
 * @param domElementId
 */
function enableGridRowById(domElementId){
	//read only text fields
	enableElement($("#consgNo"+domElementId)[0]);
	enableElement($("#noOfPieces"+domElementId)[0]);
}
/**
 * @Desc To enable element (text field)
 * @param domElement
 */
function enableElement(domElement){
	domElement.readOnly = false;
	domElement.setAttribute("tabindex","0");
}
/**
 * @Desc get consignement details : call it on blur & validate consg number 
 * @param domElement
 */
function getConsignmentDetails(domElement) {
	var rowId = getRowId(domElement, "consgNo");
	ROW_ID = rowId;
	var domValue = $.trim(domElement.value);
	if(!isNull(domValue) && validateHeader()){
		//NEW_CONSG_NO[rowId] = jQuery("#consgNo"+rowId).val();
		
		if(!domElement.readOnly && !isNull(domValue) && !isDuplicateConsgNo(domElement,rowId)){//&& NEW_CONSG_NO[rowId]!=OLD_CONSG_NO[rowId]
			var loginOfficeId = jQuery("#loginOfficeId").val();
			var destOfficeId = jQuery("#destinationOfficeId").val();
			var manifestDirection = jQuery("#manifestDirection").val();//"O"
			var allowedConsgManifestedType = jQuery("#allowedConsgManifestedType").val();
			var manifestNo = document.getElementById("manifestNo").value;
			if(isValidConsignment(domElement)){
				var url = './branchOutManifestParcel.do?submitName=getConsignmentDtls'
					+ "&consgNumber=" + domElement.value 
					+ "&loginOfficeId=" + loginOfficeId 
					+ "&officeId=" + destOfficeId 
					+ "&manifestDirection=" + manifestDirection
					+ "&allowedConsgManifestedType=" + allowedConsgManifestedType
					+ "&manifestNo=" + manifestNo;
				showProcessing();
				jQuery.ajax({
					url : url,
					success : function(req) {
						populateConsgDtls(req, domElement);
					}
				});
			} else {
				clearGridRowDetails(rowId);
			}
		}
		//clear row if consg. no. field is blank/empty/null 
		if(isNull(domValue)){
			//getConsgIdOnCheck($("#ischecked"+rowId)[0]);
			//if weight is not empty then subtract from finalWeight
			if(!isEmptyWeight(jQuery("#weights"+rowId).val())) 
				decreaseWeight(rowId);
			clearGridRowDetails(rowId);
			//OLD_CONSG_NO[rowId] = "";
			//NEW_CONSG_NO[rowId] = "";
		}
		//OLD_CONSG_NO[rowId] = NEW_CONSG_NO[rowId];
	} else {
		jQuery("#consgNo"+rowId).val("");
		if(isNull(domValue)){
			//getConsgIdOnCheck($("#ischecked"+rowId)[0]);
			//if weight is not empty then subtract from finalWeight
			if(!isEmptyWeight(jQuery("#weights"+rowId).val())) 
				decreaseWeight(rowId);
			clearGridRowDetails(rowId);
			//OLD_CONSG_NO[rowId] = "";
			//NEW_CONSG_NO[rowId] = "";
		}
	}
	
	
}
/**
 * @Desc After getting successful ajax response check wheather consg is already
 * manifested, not exist or invalid for destination pincode
 * @param ajaxResp
 * @param domElement
 */
/*function populateConsignmentDetails(ajaxResp, domElement){
	var rowId = getRowId(domElement, "consgNo");
	if (!isNull(ajaxResp)) {
		var cnValidation = eval('(' + ajaxResp + ')');
		if (cnValidation!=null && cnValidation!="") {
			if (cnValidation.isConsgExists=="N") {
				alert(cnValidation.errorMsg);
				domElement.value = "";
				clearGridRowDetails(rowId);
				setTimeout(function(){domElement.focus();},10);
				hideProcessing();
			}else if (cnValidation.isCnManifested == "Y") {
				alert(cnValidation.errorMsg);
				domElement.value = "";
				clearGridRowDetails(rowId);
				setTimeout(function(){domElement.focus();},10);
				hideProcessing();
			} else if (cnValidation.isPincodeServiceable=="N"  && cnValidation.isCnPartyShifted != "Y") {
				alert(cnValidation.errorMsg);
				domElement.value="";
				clearGridRowDetails(rowId);
				setTimeout(function(){domElement.focus();},10);
				hideProcessing();
			} 
			else if(cnValidation.isConsParcel == "N") {
				alert("Only parcel type consignments are allowed");
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				hideProcessing();
			}
			else if (cnValidation.isConsInManifestd == "Y" && cnValidation.isCNBooked == "N") {
				getInManifestConsDtls(domElement);
			} else {
				//To get the details of consignment
				getConsgnDetails(domElement);
			}
		}
	} else {
		hideProcessing();
		alert("Error occured! Try again.");
		clearGridRowDetails(rowId);
		domElement.value = "";
		setTimeout(function(){domElement.focus();}, 10);
	}
}*/
/**
 * @Desc To populate the consignment details:
 * call in populateConsignmentDetails function
 * @param consObj
 */
/*function getConsgnDetails(consObj) {
	var consNo = consObj.value;
	var rowId = getRowId(consObj, "consgNo");
	ROW_ID = rowId;
	url = './branchOutManifestParcel.do?submitName=getConsignmentDtls&consignmentNo='
			+ consNo;
	ajaxCallWithoutForm(url, populateConsgDtls);
}*/

/**
 * @param consObj
 */
/*function getInManifestConsDtls(consObj) {
	var consNo = consObj.value;
	var rowId = getRowId(consObj, "consgNo");
	ROW_ID = rowId;
	url = './branchOutManifestParcel.do?submitName=getInManifestdConsignmentDtls&consignmentNo='
			+ consNo;
	ajaxCallWithoutForm(url, populateConsgDtls);
}*/
/**
 * @Desc if consg number is validated successfully then populate consg details
 * in grid
 * @param data
 */
function populateConsgDtlsFromDB(data) {
	if(!isNull(data) && !isEmptyWeight(data.weight)){
		/* weighing Machine Integration START */
		var weight = parseFloat(data.weight).toFixed(3);
		jQuery("#oldWeights"+ROW_ID).val(weight);//hidden field
		if(!isNull(newWMWt) && parseFloat(newWMWt)>weight){
			weight=newWMWt;
		}
		jQuery("#weights" + ROW_ID).val(weight);
		/* weighing Machine Integration END */
		jQuery("#weights"+ROW_ID).val(weight);//hidden field
		//split weight
		var wt = jQuery("#weights"+ROW_ID).val();
		splitWeights(wt,"weight","weightGm",ROW_ID);
		//check format for Gram
		weightFormatForGm($("#weightGm"+ROW_ID)[0]);
		//check weigth tolerance
		if(maxWtCheck(ROW_ID))
			setConsignmentDetailsForWM(data, ROW_ID);
		isSaved = false;
	}else{
		/* No Consignment would be allowed with zero weight */
		var domElement = $("#consgNo"+ROW_ID)[0];
		domElement.value = "";
		if(isNull(data)){
			alert("Consignment is not of type: PARCEL");
		} else{ 
			alert("Consignment can not accepted due to ZERO weight.");
		}
		//OLD_CONSG_NO[ROW_ID] = "";
		//NEW_CONSG_NO[ROW_ID] = "";
		setTimeout(function(){domElement.focus();},10);
	}
	hideProcessing();
}
/**
 * @Desc check wheather manifestNo is already manifested or not to avoid duplicate entry 
 * into the database before save or close.
 * @param action
 */
/*function saveOrCloseBranchOutManifestParcel(action){
	if(action=="close") isSaved = false;
	if(!isSaved && validateHeader() && validateGridDetails()) {
		var manifestId = jQuery("#manifestId").val();
		if(isNull(manifestId)){//create new manifest
			*//** To avoid duplicate enrty into Database *//*
			isManifestNoSavedOrClosed(action);
		} else { //save or close after search
			checkIfDeletedBeforeSave(action);
		}
	} else {
		if(isSaved) alert("Manifest already saved.");
	}
}*/

/**
 * @Desc check whether manifest number is already saved or closed 
 * (To avoid duplicate entry into DataBase)
 * @param action
 */
function isManifestNoSavedOrClosed(action){
	var manifestNo = $("#manifestNo")[0];
	var loginOfficeId = jQuery("#loginOfficeId").val();
	var processCode = jQuery("#processCode").val();
	var url = "./branchOutManifestParcel.do?submitName=isManifested" 
			+ "&manifestNo=" + manifestNo.value 
			+ "&loginOfficeId=" + loginOfficeId 
			+ "&processCode=" + processCode;
	
	showProcessing();
	jQuery.ajax({
		url : url,
		success : function(req) {
			hideProcessing();
			if(req=="FAILURE"){
				manifestNo.value="";
				alert("Manifest number already used to create another manifest.");				
				setTimeout(function(){manifestNo.focus();},10);
				return false;
			} else {
				checkIfDeletedBeforeSave(action);
			}
		}
	});
}

/**
 * @Desc check if delete before save or close
 * @param action
 */
function checkIfDeletedBeforeSave(action){
	var manifestId = jQuery("#manifestId").val();
	//delete the Consg/Manifest/Co-Mail before saving
	if(isDeleted && !isNull(manifestId)){
		deleteTableRow(action);
	}else{
		performSaveOrClose(action);
	}
}

/**
 * @Desc actual perform save or close operation
 * @param action
 */
function saveOrCloseBranchOutManifestParcel(action){
if (validateHeader() && validateGridDetails()) {

	var destOfficeId = jQuery("#destinationOfficeId").val();
	if(action=='close'){
		isSaved = false;
	}
	
	isAutoClose = false;
	//if all row is filled then manifestStatus should be closed ("C") 
	if(isAllRowInGridFilled(maxCNsAllowed)){
		action='close';
		isAutoClose = true;
	}
	
	//check weight tolerance during save or close 
	var maxAllowedWtWithTollrence = parseFloat(maxWeightAllowed) + 
		(parseFloat(maxWeightAllowed) * 
				parseFloat(maxTolerenceAllowed) / 100);
	if(maxAllowedWtWithTollrence==(jQuery("#finalWeight").val())) {
		action='close';
		isAutoClose = true;
	} else if (jQuery("#weights"+1).val() > maxAllowedWtWithTollrence){
		//If first row exceeds weight limit
		action='close';
		isAutoClose = true;
	}
	
	if (action=='save') {//Open
		jQuery("#manifestStatus").val(MANIFEST_STATUS_OPEN);
	} else if(action=='close') {//Close
		jQuery("#manifestStatus").val(MANIFEST_STATUS_CLOSE);
	}
	enableHeaderDropDown();
	showProcessing();
	var url = './branchOutManifestParcel.do?submitName=saveOrUpdateBranchOutManifestParcel&destOfficeId='+destOfficeId;
	jQuery.ajax({
		url : url,
		type : "POST",
		data : jQuery("#branchOutManifestParcelForm").serialize(),
		success : function(req) {
			
			callBackManifestSave(req, action);
		}
	});
}
}

/**
 * @Desc call back method for manifest save or close
 * @param ajaxResp
 * @param action
 */
function callBackManifestSave(ajaxResp, action){
	hideProcessing();
	if (!isNull(ajaxResp)) {
	
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();	
			alert(error);
		}else if(action=="save"){
			isSaved = true;
			hideProcessing();
			alert(responseText.successMessage);
			$('#branchOutManifestParcelTable').dataTable().fnClearTable();
			searchDtls();
			//hideProcessing();
			}
		else if(action=="close"){
				
			closeAction=action;
			hideProcessing();
			//alert(responseText.successMessage);
			var confirm1 = confirm("Manifest closed successfully.");
			if (confirm1) {
				searchDtls();
				printBranchOutManifest();
				refreshPage();
				/*setTimeout(function() {
					printBranchOutManifest();
				}, 3000);
				setTimeout(function() {
					refreshPage();
				}, 4000);*/

			
					
				} else {
					refreshPage();
				}
				if (action == 'close') {
					disableForClose();
				}
				}
	
		}
}
/**
 * @Desc get RFID by RFID number
 * @param domElement
 */
function getRfId(domElement) {
	var rfIdRfNo = $.trim(domElement.value);
	//var bagRFID = jQuery("#bagRFID").val();
	if(!domElement.readOnly) {
		if(!isNull(rfIdRfNo)) {
				showProcessing();
				getRfIdByRfNo(domElement);
		} else {
			jQuery("#bagRFID").val("");//hidden
			jQuery("#rfidNo").val("");
		}
		hideProcessing();
	}
}
/**
 * @Desc delete Consg Dtls from client side
 */
function deleteConsgDtlsClientSide() {
	var isDel = false;
	for(var i=1; i<=maxCNsAllowed; i++) {
		var cnNo = jQuery("#consgNo"+i).val();
		if ($("#ischecked"+i).is(':checked')==true
				&& !isNull(cnNo)) {
			decreaseWeight(i);
			// Clearing the fields
			//jQuery("#ischecked" + i).val("");
			jQuery("#ischecked"+ i).attr("checked",false);
			clearGridRowDetails(i);
			enableGridRowById(i);//enable rows for new consignments
			isSaved = false;
			isDeleted = true;
			isDel = true;
		}//End If
		if(isNull(cnNo)){
			//jQuery("#ischecked"+i).val("");
			jQuery("#ischecked"+ i).attr("checked",false);
		}
	}//End For loop
	jQuery("#checkAll").attr("checked",false);
	if(isDel) alert("Record(s) deleted successfully.");
	else alert("Please select a Non Empty row to delete.");
}
/**
 * @Desc ajax response for delete rows from database
 * @param ajaxResp
 */
function printCallBackManifestDelete(ajaxResp,action){
	var data = ajaxResp;
	showProcessing();
	if(!isNull(data)) {
		if(data=="SUCCESS"){
			//alert("Record deleted successfully.");
			performSaveOrClose(action);
		} else if(data=="FAILURE") {
			//alert("Exception occurred. Record not deleted successfully.");
			alert("Exception occurred. Record could not be saved.");
		}
	}
	hideProcessing();
}
/**
 * @Desc get consg id on click of check box
 * @param checkObj
 */
function getConsgIdOnCheck(checkObj){
	var consgIdListAtGrid = jQuery("#consgIdListAtGrid").val();
	var rowId = getRowId(checkObj,"ischecked");
	var consgId = "";
	if(isNull(consgIdListAtGrid)){
		consgId = jQuery("#consgId"+rowId).val();
	} else {
		consgId = consgIdListAtGrid + "," + jQuery("#consgId"+rowId).val();
	}
	jQuery("#consgIdListAtGrid").val(consgId);
}
/**
 * @Desc disable all check box on search if manifest closed
 */
function disableAllCheckBox(){
	jQuery("#checkAll").attr("disabled",true);
	for(var i=1; i<=maxCNsAllowed; i++){
		jQuery("#ischecked"+i).attr("disabled",true);
	}
}
/**
 * @Desc disable elements for closed manifest
 */
/*function disableForClose(){
	disableAllCheckBox();
	disableAll();
	jQuery(":input").attr("tabindex", "-1");
	buttonEnabled("printBtn","btnintform");
}*/
/**
 * @Desc disable elements for saved manifest
 */
function disableForAfterSave(){
	disableHeaderForSearch();
	for(var i=1;i<=maxCNsAllowed;i++){
		if(!isNull(jQuery("#consgNo"+i).val())){
			disableGridRowById(i);
			jQuery("#isCnUpdated"+i).val("N");
		}
	}
}
/**
 * @Desc split Weight by "." and setting to respective Kgs and Gms elements
 * @param wt
 * @param wtKgId
 * @param wtGmId
 * @param rowId
 */
function splitWeights(wt,wtKgId,wtGmId,rowId){
	var weightArr = wt.split(".");
	var weightKgId = wtKgId + rowId;
	var weightGmId = wtGmId + rowId;
	jQuery("#"+weightKgId).val(weightArr[0]);
	jQuery("#"+weightGmId).val((!isNull(weightArr[1]))?weightArr[1].substring(0, 3):"");
	if (weightArr[1] == undefined) {
		jQuery("#"+weightGmId).val(jQuery("#"+weightGmId).val()+"000");
	} else if (weightArr[1].length == 1) {
		jQuery("#"+weightGmId).val(jQuery("#"+weightGmId).val()+"00");
	} else if (weightArr[1].length == 2) {
		jQuery("#"+weightGmId).val(jQuery("#"+weightGmId).val()+"0");
	}
}
/**
 * @Desc check weight tolerance
 * @param rowId
 */
function maxWtCheck(rowId) {
	var maxCNsAllowed = jQuery("#maxCNsAllowed").val();
	var maxWeightAllowed = jQuery("#maxWeightAllowed").val();
	var maxTolerenceAllowed = jQuery("#maxTolerenceAllowed").val();
	if(!maxWeightToleranceCheck(rowId, maxCNsAllowed, 
			maxWeightAllowed, maxTolerenceAllowed)){
		clearGridRowDetails(rowId);
		eachConsgWeightArr[rowId]=0;
		jQuery("#consgNo"+rowId).focus();
		return false;
	}
	var wtKg = jQuery("#weight"+rowId).val();
	var wtGm = jQuery("#weightGm"+rowId).val();
	jQuery("#weights"+rowId).val(wtKg+"."+wtGm);
	setFinalWeight(jQuery("#finalWeight").val());
	return true;
}
/**
 * @Desc validate header and grid details before save/close
 * @returns {Boolean}
 */
function validateHeader(){
	var msg = "Please provide : ";
	var isValid = true;
	var manifestNo = $("#manifestNo")[0];
	var destOfficeId = $("#destinationOfficeId")[0];
	var loadNoId = $("#loadNoId")[0];
	var focusObj = manifestNo;
	var bagLockNo = $("#bagLockNo")[0];
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
	/*if(isNull(rfidNo.value)){
		alert("Please select RFID No.");
		setTimeout(function(){rfidNo.focus();},10);
		return false;
	}*/
	if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	return isValid;
}
/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails(){
	var flag = false;
	for(var i=1; i<=maxCNsAllowed; i++){
		if(!isNull(jQuery("#consgId"+i).val())){//ischecked
			if(!validateSelectedRow(i)) {
				return false;
			}
			flag = true;
		}
	}
	if(!flag)
		alert("Please enter atleast one Consignment Number");
	return flag;
}
/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var consgNo = $("#consgNo"+rowId)[0];
	var noOfPc = $("#noOfPieces"+rowId)[0];
	var lineNum = "at line :"+rowId;
	if(isNull(consgNo.value)){
		alert("Please provide Consignment No. to be scanned "+lineNum);
		setTimeout(function(){consgNo.focus();},10);
		return false;
	}
	if(isNull(noOfPc.value)){
		alert("Please provide no of pieces "+lineNum);
		setTimeout(function(){noOfPc.focus();},10);
		return false;
	}
	return true;
}
/**
 * @Desc check wheather row duplicate consignment number
 * @param domElement
 * @param rowId
 * @returns {Boolean}
 */
function isDuplicateConsgNo(domElement,rowId){
	var consgNo = domElement.value.toUpperCase();
	for(var i=1; i<=maxCNsAllowed; i++){
		if(i==rowId) continue;
		var cnNos = jQuery("#consgNo"+i).val();
		if(consgNo==cnNos){
			domElement.value="";
			alert("Row contains Duplicate Consignment Number at line:"+i);			
			setTimeout(function(){domElement.focus();},10);
			return true;
		}
	}
	return false;
}
/**
 * @Desc redirect to child CN page
 * @param rowId
 */
function redirectToChildCNPage(rowId){
	if(!isNull(jQuery("#consgNo"+rowId).val())){
		var noOfPcElement = $("#noOfPieces"+rowId)[0];
		if(noOfPcElement.readOnly==false){
			var pieces = noOfPcElement.value;
			//PIECES_OLD = PIECES_NEW;
			PIECES_NEW = pieces;
			PIECES_OLD = PIECES_NEW;
			if(PIECES_NEW<PIECES_ACTUAL){
				PIECES_OLD = 0;
			}
			if(!isNull(pieces) && pieces>1) { //PIECES_NEW!=PIECES_OLD
				jQuery("#bookingType").val(jQuery("#bookingType"+rowId).val()); 
				url = "childCNPopup.do?&pieces=" + pieces + '&rowCount=' + rowId;
				window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');
				if(PIECES_NEW!=PIECES_ACTUAL)
					jQuery("#isCnUpdated"+rowId).val("Y");
			}
		}
	}
}
/**
 * @Desc print the details 
 */
/*function printDtls(){
	var manifestStatus = jQuery("#manifestStatus").val();
	if(manifestStatus==MANIFEST_STATUS_CLOSE){
		var manifestNo = jQuery("#manifestNo").val();
		var loginOfficeId = jQuery("#loginOfficeId").val();
		if(!isNull(manifestNo)){
			if(confirm("Do you want to print?")){
				var url = './branchOutManifestParcel.do?submitName=printManifestDtls&manifestNo='
					+ manifestNo + "&loginOfficeId=" + loginOfficeId;
				//window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left = 412,top = 184,scrollbars=yes');
				document.branchOutManifestParcelForm.action = url;
				document.branchOutManifestParcelForm.submit();
				//showProcessing();
				jQuery.ajax({
					url : url,
					success : function(req) {
						hideProcessing();
					}
				});
			}
		} else {
			alert("Please provide Branch Manifest No.");
		}
	} else {
		alert("Manifest is not closed.");
	}
}*/

/**
 * @Desc If all row is filled then CLOSE automatically. 
 * @param maxRowAllowed
 * @returns {Boolean}
 */
function isAllRowInGridFilled(maxRowAllowed){
    for(var i=1; i<=maxCNsAllowed; i++){
      if(isNull(jQuery("#consgNo" + i).val()))
            return false;
    }
    return true;
}


/**
 * To decrease the weight from final weight
 * @param rowId
 */
function decreaseWeight(rowId){
	var subTotalWeight = 0.000;
	// before clearing grid details getweight
	var weightKg = jQuery("#weight" + rowId).val();
	var weightGm = jQuery("#weightGm" + rowId).val();
	var weightInFraction =((weightKg) + (weightGm))/1000;
	
	// Header Total Weight
	var totalWeight = parseFloat(jQuery("#finalWeight").val());
	subTotalWeight = totalWeight*1000 - parseFloat(weightInFraction*1000);
	subTotalWeight /= 1000;
	if(isEmptyWeight(subTotalWeight)){
		subTotalWeight=0.000;
	}

	// Setting final wt after deduction in header and hidden Field
	jQuery("#finalWeight").val(parseFloat(subTotalWeight));
	
	var finalWeightStr = subTotalWeight + "";
	weightKgValueFinal = finalWeightStr.split(".");
	jQuery("#finalKgs").val(weightKgValueFinal[0]);
	if(!isNull(weightKgValueFinal[1]))
		jQuery("#finalGms").val(weightKgValueFinal[1].substring(0,3));
	if (weightKgValueFinal[1] == undefined) {
		jQuery("#finalGms").val("000");
	} else if (weightKgValueFinal[1].length == 1) {
		jQuery("#finalGms").val(jQuery("#finalGms").val()+"00");
	} else if (weightKgValueFinal[1].length == 2) {
		jQuery("#finalGms").val(jQuery("#finalGms").val()+"0");
	}

	// setting the weight 0 in global array while deleting
	eachConsgWeightArr[rowId] = 0;
}


/**
 * printBranchOutManifest
 *
 * @author sdalli
 */
function printBranchOutManifest(){
	if(confirm("Do you want to Print?")){
	var manifestNoObj = $("#manifestNo")[0];
	//var loginOfficeId = jQuery("#loginOfficeId").val();
	//if(isValidBplNo(manifestNoObj)){
		var manifestNoObj = $("#manifestNo")[0];
		if (!isNull(manifestNoObj)) {
			var loginOfficeId = jQuery("#loginOfficeId").val();
			var url ="./branchOutManifestParcel.do?submitName=printManifestDtls&manifestNo="+ 
			manifestNoObj.value + "&loginOfficeId=" + loginOfficeId;
			var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			}
		
		/*window.frames['iFrame'].focus();
		window.frames['iFrame'].print();*/
		/*var confirm1 = confirm("Do you want to print Branch Manifest Details!");
		if(confirm1){			
			printBranchOutManifestDtls(manifestNoObj.value,loginOfficeId);
			window.frames['iFrame'].focus();
			window.frames['iFrame'].print();
		}else {
			var url = "./branchOutManifestParcel.do?submitName=viewBranchOutManifestParcel";
			document.branchOutManifestParcelForm.action = url;
			document.branchOutManifestParcelForm.submit();
		}*/
		
	/*//}else{
		  alert("Please Enter BPL No.");
		  jQuery("#manifestNo").val("");
		  jQuery("#manifestNo").focus();
		}*/
	}
}

/**
 * printBranchOutManifestDtls
 *
 * @param manifestNO
 * @param loginOfficeId
 * @author sdalli
 */
function printBranchOutManifestDtls(manifestNO,loginOffceID){
		var url ="./branchOutManifestParcel.do?submitName=printManifestDtls&manifestNo="+ manifestNO + "&loginOfficeId=" + loginOffceID;
		document.branchOutManifestParcelForm.action=url;
		document.branchOutManifestParcelForm.submit();
}


/**
 * isValidBplNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author sdalli
 */
function isValidBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	
	//var  test1 = manifestNoObj.value.substring(1, 4);
	//var  test2 = manifestNoObj.value.substring(4);
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * clearFocusAlertMsg
 *
 * @param obj
 * @param msg
 * @author sdalli
 */
function clearFocusAlertMsg(obj, msg){
	obj.value = "";
	obj.focus();
	alert(msg);
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
	for(var i=1; i<=maxCNsAllowed; i++){
		if(!isNull(jQuery("#"+domElementId + i).val())){
			isFilled = true;
			flag = confirm("Consignment number(s) already entered in the grid.\n\nDo you still want to make the changes in header?");
			break;
		}
	}
	//clear the non-empty row
	if(flag){
		for(var i=1; i<=maxCNsAllowed; i++){
			if(!isNull(jQuery("#"+domElementId + i).val())){
				if(!isEmptyWeight(jQuery("#weights"+i).val())) 
					decreaseWeight(i);
				clearGridRowDetails(i);
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
 * To implements enter key navigation on (next) mandatory field(s) 
 * 
 * @param e (event)
 * @returns {Boolean}
 */
function callFocusEnterKey(e){
	rowId=1;
	var focusId=$("#consgNo" + rowId)[0];
	return callEnterKey(e, focusId);
}

/* Weighing machine integration */
/** 
 * To display the consignment details 
 */
function populateConsgDtls(data,domElement) {
	hideProcessing();
	
	if (!isNull(data)) {
		
		
			var responseText =jsonJqueryParser(data); 
			var error = responseText[ERROR_FLAG];
			var success = responseText[SUCCESS_FLAG];
			if(responseText!=null && error!=null){
				hideProcessing();
				domElement.value = "";
			alert(error);
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
			}else {
	
				hideProcessing();
			/** For wt integratn */
				bookingDetail=responseText;
				if(isWeighingMachineConnected){
					getWtFromWMForOGM();
				}else{
					capturedWeightForManifest(-1);
				}
				
		}
	}else{
			hideProcessing();
			alert('No details found');
	}
	hideProcessing();
	
}

function capturedWeightForManifest(weigth){
	//alert('>>>wmCapturedWeight>>>'+wmCapturedWeight);
	//var weightInKg = null;
	//var weightGm = null;
	
	wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	wmCapturedWeight = parseFloat(weigth).toFixed(3);
	
	//alert('machine weight'+wmActualWeight);
	newWMWt=wmActualWeight;
	
	var response = bookingDetail;
	if(response!=null && (!isEmptyWeight(newWMWt) || !isEmptyWeight(response.weight))){
		populateConsgDtlsFromDB(response);
	} else if(isNull(response)){
		clearGridRowDetails(ROW_ID);
		alert("Invalid consignment number");
		clearGridRowDetails(ROW_ID);
		setTimeout(function(){jQuery("#consgNo"+ROW_ID).focus();},10);
		hideProcessing();
	} else if(response!=null && (isEmptyWeight(newWMWt) || isEmptyWeight(response.weight))){
		clearGridRowDetails(ROW_ID);
		alert("Consignment can not accepted due to ZERO weight.");
		setTimeout(function(){jQuery("#consgNo"+ROW_ID).focus();},10);
		clearGridRowDetails(ROW_ID);
		hideProcessing();
	} else {//remove this later if not required.
		clearGridRowDetails(ROW_ID);
		alert("Invalid consignment number.");
		clearGridRowDetails(ROW_ID);
		setTimeout(function(){jQuery("#consgNo"+ROW_ID).focus();},10);
		hideProcessing();
	}
}

/**
 * @Desc setting details in grid
 * @param listObj
 * @param rowId
 */
function setConsignmentDetailsForWM(listObj,rowId){
	jQuery("#consgNo"+rowId).val(listObj.consgNo);

	jQuery("#noOfPieces"+rowId).val(listObj.noOfPcs);
	//jQuery("#childCns"+rowId).val(listObj.childCn);//hidden
	jQuery("#isCnUpdated"+rowId).val("N");//hidden
	jQuery("#gridOriginOfficeId"+rowId).val(listObj.gridOriginOfficeId);//hidden added by niharika
	PIECES_OLD = jQuery("#noOfPieces"+rowId).val();
	PIECES_ACTUAL = jQuery("#noOfPieces"+rowId).val();
		
	/*jQuery("#weights"+rowId).val() = listObj.weight;//hidden field
	//split weight
	var wt = jQuery("#weights"+rowId).val();
	splitWeights(wt,"weight","weightGm",rowId);*/
	
	if(!isNull(listObj.cnContentId)){
		jQuery("#cnContent"+rowId).val(listObj.cnContent);
		jQuery("#cnContentId"+rowId).val(listObj.cnContentId);//hidden	
	} else {
		jQuery("#cnContent"+rowId).val("OTHERS");
		jQuery("#cnContentId"+rowId).val("");//hidden
	}
	
	jQuery("#declaredValue"+rowId).val(listObj.declaredValue);
	jQuery("#paperWork"+rowId).val(listObj.paperWork);
	jQuery("#paperWorkId"+rowId).val(listObj.paperWorkId);//hidden
	jQuery("#toPayAmt"+rowId).val(listObj.toPayAmt);
	jQuery("#codAmt"+rowId).val(listObj.codAmt);
	jQuery("#customDutyAmt"+rowId).val(listObj.customDutyAmt);
	jQuery("#stateTax"+rowId).val(listObj.stateTax);
	jQuery("#serviceCharge"+rowId).val(listObj.serviceCharge);
	
	//setting hidden fields
	jQuery("#consgId"+rowId).val(listObj.consgId);
	jQuery("#bookingType"+rowId).val(listObj.bookingType);
}

/**
 * To check is weight limit exceeds
 * 
 * @returns {Boolean}
 */
function isWtLimitExceeds(){
	//check weight tolerance during consg. population
	var maxAllowedWtWithTollrence = parseFloat(maxWeightAllowed) + 
		(parseFloat(maxWeightAllowed) * 
				parseFloat(maxTolerenceAllowed) / 100);
	var finalWt = jQuery("#finalWeight").val();
	if(finalWt>=maxAllowedWtWithTollrence) {
		alert("Maximum weight limit exceeds.");
		return false;
	}
	return true;
}

/**
 * To clear page
 */
function clearPage(){
	if(confirm("Do you want to clear the page?")){
		var url = "./branchOutManifestParcel.do?submitName=viewBranchOutManifestParcel";
		document.branchOutManifestParcelForm.action = url;
		document.branchOutManifestParcelForm.submit();
	}
}

function cancelPage() {
	var url = "./branchOutManifestParcel.do?submitName=viewBranchOutManifestParcel";
	window.location = url;
}

function refreshPage() {

	var url = "./branchOutManifestParcel.do?submitName=viewBranchOutManifestParcel";

	document.branchOutManifestParcelForm.action = url;

	document.branchOutManifestParcelForm.submit();

	}

function enterkeyNavigationFunctionForBranchManifst(domElement,event){
	
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = event.which; // firefox
	if (key == 13){
//		Commenting and calling onchange event
//		getConsignmentDetails(domElement);
		convertDOMObjValueToUpperCase(domElement);
		goToNxtRow(domElement,event);
		
	}else{
		//getConsignmentDetails(domElement);
		convertDOMObjValueToUpperCase(domElement);
	}
}


function goToNxtRow(consgNumberObj,event){
	var consid = getRowId(consgNumberObj, "consgNo");
	var rowId = ++consid;
	
	return callEnterKey(event, document.getElementById("consgNo"+rowId));
}


