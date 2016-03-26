var isSaved = false;
var isDeleted = false;
var numpattern = /^[0-9]{3,20}$/;
var coMailStartSeries = 'CM';
var coMailDelete = false;
var maxRowAllowed;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

var OLD_DEST_OFFICE_ID = 0;
var NEW_DEST_OFFICE_ID = 0;

var printUrl;
var printFlag="N";
var refreshFlag="N";
var closeAction=null;

function branchOutManifestDoxStartup() {
	maxRowAllowed = parseInt(getDomElementById("maxCNsAllowed").value)
			+ parseInt(getDomElementById("maxComailsAllowed").value);
}

/* @Desc: Add rows in the grid */
function addRows() {
	var maxCNAllowd = getDomElementById("maxCNsAllowed").value;

	for ( var cnt = 1; cnt <= maxCNAllowd; cnt++) {
		$('#outManifestTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox" id="ischecked'
										+ cnt
										+ '" name="chkBoxName" onclick="getConsignmntIdOnCheck(this);" />',
								cnt,
								'<input type="text" class="txtbox width130" maxlength="12"  id="consigntNo'
										+ cnt
										+ '" name="to.consgNos" tabindex="-1" onkeypress="checkForLastConsRow(this,event);"  onblur="fnValidateNumber(this);"  onchange= "validateCons(this);" />',
								'<input type="text" class="txtbox width130" id="LCAmount'
										+ cnt
										+ '" name="to.lcAmounts"  readonly="true"   />',
								'<input type="text" class="txtbox width130" id="bankName'
										+ cnt
										+ '" name="to.bankNames"  tabindex="-1"   onblur="" readonly="true"/>\
 	 <input type="hidden" id="weight'
										+ cnt
										+ '" />\
 	 <input type="hidden" id="weightGm'
										+ cnt
										+ '" />\
 	 <input type="hidden" id="weights'
										+ cnt
										+ '" name="to.weights"/>\
 	 <input type="hidden" id="oldWeights'
										+ cnt
										+ '" name="to.oldWeights" />\
 	 <input type="hidden" name="to.consgIds" id="consignmtNo'
										+ cnt
										+ '"/>\
 	 <input type="hidden" name="to.position" id="position'
										+ cnt
										+ '" value="'
										+ cnt
										+ '"/>\
 	 <input type="hidden" name="to.rowToPay" id="rowToPay'
										+ cnt
										+ '"/>\
 	 <input type="hidden" id="consManifestId'
										+ cnt
										+ '" name="to.consgManifestedIds" value="" />\
 	 <input type="hidden" name="to.rowCOD" id="rowCOD'
										+ cnt + '"/>' ]);
	}// end of for loop
	document.getElementById("manifestNo").focus();
	var rowCount = parseIntNumber(maxCNAllowd);
	addComailRows(rowCount);
}

function addComailRows(cnt) {
	$('#outManifestTable').dataTable().fnAddData(
			[ 'Co-Mail', '&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;',
					'&nbsp;', ]);
	var temp = getDomElementById("maxComailsAllowed").value;
	var maxCNAllowd = getDomElementById("maxCNsAllowed").value;

	var rows = parseIntNumber(temp) + parseIntNumber(cnt) - 1;
	for ( var i = cnt; i <= rows; i++) {
		$('#outManifestTable')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox" id="ischecked'
										+ (i + 1)
										+ '" name="chkBoxName" onclick="getCoMailIdOnCheck(this);"/>',
								(i + 1),

								'<input type="text" class="txtbox width130" id="comailNo'
										+ (i + 1)
										+ '" name="to.comailNos" maxlength="12" onkeypress="return callEnterKey(event, document.getElementById(\'comailNo'
										+ ((i + 1) + 1)
										+ '\'));" onblur="convertDOMObjValueToUpperCase (this);validateComail(this);isComailBooked(this);"/>',

								'<input type="text" class="txtbox width130" id="LCAmount'
										+ ((i + 1))
										+ '" name="to.lcAmounts"  readonly="true" />',
								'<input type="text" class="txtbox width130" id="bankName'
										+ ((i + 1))
										+ '" name="to.bankNames"  tabindex="-1" readonly="true"  onblur=""/><input type="hidden" id="weight'
										+ ((i + 1))
										+ '" /><input type="hidden" id="weightGm'
										+ ((i + 1))
										+ '" /><input type="hidden" id="weights'
										+ ((i + 1))
										+ '" name="to.weights"/><input type="hidden" name="to.comailIds" id="comailIdHidden'
										+ ((i + 1))
										+ '"/><input type="hidden" id="comailManifestId'
										+ (i + 1)
										+ '" name="to.comailManifestedIds" value="" />'

						]);
	}
}

/* @Desc: Validates the consignment in the grid */
function validateCons(consgNumberObj) {
	if (!consgNumberObj.readOnly  && validateHeader() && isValidConsignment(consgNumberObj)) {
		var manifestDirectn = "O";
		var consid = getRowId(consgNumberObj, "consigntNo");
		ROW_COUNT = consid;
		var destOfficeId = getDomElementById("destOffice").value;
		var loginOfficeId = getDomElementById("loginOfficeId").value;
		var allowedConsgManifestedType = getDomElementById("allowedConsgManifestedType").value;
		var manifestNo = getDomElementById("manifestNo").value;
		// showProcessing();
		url = './branchOutManifestDox.do?submitName=getConsignmentDtls'
				+ "&consgNumber=" + consgNumberObj.value + "&officeId="
				+ destOfficeId + "&loginOfficeId=" + loginOfficeId
				+ "&manifestDirection=" + manifestDirectn
				+ "&allowedConsgManifestedType=" + allowedConsgManifestedType
				+ "&manifestNo="+manifestNo;
		 showProcessing();//added now
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateConsDtls(req, consgNumberObj);
			}
		});
		/*showProcessing();
		ajaxCallWithoutForm(url, populateConsDtls);*/
	}else{
		consgNumberObj.value = "";
		
	}
}

/* @Desc: Shows the message if any consignment validation fails */
/*function callBackConsignment(data, consgNumberObj) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			// checks for consignment exists
			if (cnValidation.isConsgExists == "N") {
				// hideProcessing();//added now
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			}// checks for consignment manifested
			else if (cnValidation.isCnManifested == "Y") {
				// hideProcessing();//added now
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			} // checks for valid pincode
			else if (cnValidation.isPincodeServiceable == "N"
					&& cnValidation.isCnPartyShifted != "Y") {
				// hideProcessing();//added now
				alert(cnValidation.errorMsg);
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			} else if (cnValidation.isConsParcel == "Y") {
				// hideProcessing();//added now
				alert("Consignment of parcel type not allowed");
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
			} else if (cnValidation.isConsInManifestd == "Y") {
				getInManifestConsDtls(consgNumberObj);
			}
			else if(cnValidation.isConsInManifestd == "N"  && cnValidation.isConsAllowed == "N"){
				alert("Invalid consignment. ");
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				hideProcessing();
			}
			else {
				// hideProcessing();//added now
				// to get the details of consignment in the grid
				getConsignmentDtls(consgNumberObj);
			}
		}
	}
	// jQuery.unblockUI();
}*/

/* @Desc: Gets the consignment details */
/*function getConsignmentDtls(consObj) {
	var consNo = consObj.value;
	var consid = getRowId(consObj, "consigntNo");
	ROW_COUNT = consid;
	var url = "./branchOutManifestDox.do?submitName=getConsignmentDtls&consignmentNo="
			+ consNo;
	showProcessing();
	ajaxCallWithoutForm(url, populateConsDtls);
}*/

/* @Desc: Gets the inManifestconsignment details */
/*function getInManifestConsDtls(consObj) {
	var consNo = consObj.value;
	var consid = getRowId(consObj, "consigntNo");
	ROW_COUNT = consid;
	var url = "./branchOutManifestDox.do?submitName=getInManifestdConsignmentDtls&consignmentNo="
			+ consNo;
	ajaxCallWithoutForm(url, populateConsDtls);
}*/

/* @Desc: Populates the consignment details in the grid */
function populateConsDtls(data,consgNumberObj) {
	hideProcessing();
	
	if (!isNull(data)) {
		
		
			var responseText =jsonJqueryParser(data); 
			var error = responseText[ERROR_FLAG];
			var success = responseText[SUCCESS_FLAG];
			if(responseText!=null && error!=null){
				hideProcessing();
				consgNumberObj.value = "";
			alert(error);
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
			}, 10);
			}else {
	
				hideProcessing();
			/** For wt integratn */
			bookingDetail = responseText;
			if(isWeighingMachineConnected){
				getWtFromWMForOGM();
			}else{
				capturedWeightForManifest(-1);
			}
			
		}
	/*else{
		var error = data[ERROR_FLAG];
		var success = data[SUCCESS_FLAG];
		if(error!=null){
			hideProcessing();
		alert(error);
		consgNumberObj.value = "";
		setTimeout(function() {
			consgNumberObj.focus();
		}, 10);
		}
		if(success!=null)
			hideProcessing();
		bookingDetail = data;
		getWtFromWMForOGM();
		}*/
	}else{
		hideProcessing();
		alert('No details found');
	}
	
}

/* @Desc: Adds the grid weight and displays the total weight in the header */
function setTotalWt(rowId) {
	// var weightInFraction = 0;
	var finalWeight = 0;
	// var finalWeight = document.getElementById("finalWeight").value;
	// finalWeight=(isNull(finalWeight))?"0.000":finalWeight;
	var maxRowsAllowed = parseInt(maxRowAllowed);
	// wt at each line item
	var weightKg = document.getElementById("weight" + rowId).value;
	weightKg = (isNull(weightKg)) ? "0" : weightKg;
	var weightGM = document.getElementById("weightGm" + rowId).value;
	weightGM = (isNull(weightGM)) ? "000" : weightGM;

	if (weightGM == undefined) {
		weightGM = "000";
	} else if (weightGM.length == 1) {
		weightGM += "00";
	} else if (weightGM.length == 2) {
		weightGM += "0";
	} else {
		weightGM = weightGM.substring(0, 3);
	}

	weightInFraction = parseFloat(weightKg) + parseFloat(weightGM) / 1000;
	eachConsgWeightArr[rowId] = weightInFraction;

	// Final Wt
	for ( var j = 1; j <= maxRowsAllowed; j++) {
		if (eachConsgWeightArr[j] != undefined) {
			finalWeight = parseFloat(finalWeight) * 1000
					+ parseFloat(eachConsgWeightArr[j]) * 1000;
			finalWeight /= 1000;
		}
	}
	// var finalWt = document.getElementById("finalWeight").value;
	document.getElementById("finalWeight").value = finalWeight;
	setFinalWeight(finalWeight);

}

/* @Desc: Gets the consignment id on check */
function getConsignmntIdOnCheck(checkObj) {
	var consIdListAtGrid = document.getElementById("consgIdListAtGrid").value;
	var rowId = getRowId(checkObj, "ischecked");
	if (isNull(consIdListAtGrid)) {
		var consignmtId = document.getElementById("consignmtNo" + rowId).value;
		document.getElementById("consgIdListAtGrid").value = consignmtId;
	} else {
		var consignmtId = consIdListAtGrid + ","
				+ document.getElementById("consignmtNo" + rowId).value;
		document.getElementById("consgIdListAtGrid").value = consignmtId;
	}
}

/* @Desc: Gets the comail id on check */
function getCoMailIdOnCheck(checkObj) {
	coMailDelete = true;
	var comailIdListAtGrid = document.getElementById("comailIdListAtGrid").value;
	var comailManifestIdListAtGrid = document.getElementById("comailManifestListId").value;
	var rowId = getRowId(checkObj, "ischecked");
	if(isNull(comailManifestIdListAtGrid)){
		var comailmanifstId = document.getElementById("comailManifestId" + rowId).value;
		document.getElementById("comailManifestListId").value = comailmanifstId;
	}else{
		var comailmanifstId = comailManifestIdListAtGrid + ","
		+ document.getElementById("comailManifestId" + rowId).value;
		document.getElementById("comailManifestListId").value = comailmanifstId;
	}
	
	if (isNull(comailIdListAtGrid)) {
		var consignmtId = document.getElementById("comailIdHidden" + rowId).value;
		document.getElementById("comailIdListAtGrid").value = consignmtId;
	} else {
		var consignmtId = comailIdListAtGrid + ","
				+ document.getElementById("comailIdHidden" + rowId).value;
		document.getElementById("consgIdListAtGrid").value = consignmtId;
	}
}

/* @Desc: Shows the message if deleted successfully or not */
function printCallBackManifestDelete(data, action) {
	if (data != null && data != "") {
		if (data == "SUCCESS") {
			performSaveOrClose(action);
			// alert("Record deleted from the database successfully.");
		} else if (data == "FAILURE") {
			alert("Exception occurred. Data not Saved successfully.");
		}
		jQuery('#outManifestTable >tbody >tr').each(function(i, tr) {
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if (isChecked) {
				alert(i);
				document.getElementById("consigntNo" + (i + 1)).value = "";
				document.getElementById("LCAmount" + (i + 1)).value = "";
				document.getElementById("bankName" + (i + 1)).value = "";
			}
		});
	}
}

/* @Desc: Saves or Closes the manifest */
function saveOrCloseBranchOutManifestDox(action) {
	if(action=='close'){
		isSaved = false;
	}
	if (!isSaved) {
		// checks if all the row in the grid are filled
		var maxCn = getDomElementById("maxCNsAllowed").value;
		var maxComail = getDomElementById("maxComailsAllowed").value;
		if (isAllConsignmtRowInGridFilled(maxCn)
				&& isAllComailRowInGridFilled(maxComail)) {
			action = 'close';
		}
		if (action == 'save') {
			// Open
			document.getElementById("manifestStatus").value = "O";
			var stockItemNo = getDomElementById("manifestNo").value;
			var regionCode = stockItemNo.substring(0, 3);
			var loginCityCode = getDomElementById("loginCityCode").value;
			var seriesType = getDomElementById("seriesType").value;
			var officeCode = getDomElementById("officeCode").value;

		} else if (action == 'close') {
			// Close
			document.getElementById("manifestStatus").value = "C";
		}
		enableHeaderDropDown();
		if (validateHeader() && validateGridDetails()) {
			 showProcessing();
			var url = './branchOutManifestDox.do?submitName=saveOrUpdateBranchOutManifestDox';
			jQuery.ajax({
				url : url,
				type : "POST",
				data : jQuery("#branchOutManifestDoxForm").serialize(),
				success : function(req) {
					printCallBackManifestSave(req, action);
				}
			});
			
		}
		
	} else {
		alert("Data already saved.");
	}
}

/* @Desc: Shows the message if saved or closed successfully or not */
function printCallBackManifestSave(ajaxResp, action) {
	
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
			$('#outManifestTable').dataTable().fnClearTable();
			searchManifestDtls();
			}
		else if(action=="close"){
				closeAction=action;
				hideProcessing();
				//alert(responseText.successMessage);
				var confirm1 = confirm("Manifest closed successfully.");
				if (confirm1) {
					searchManifestDtls();
					printBranchOutManifest();
					refreshPage();
					/*setTimeout(function(){printBranchOutManifest();}, 3000);
					setTimeout(function(){
						if(refreshFlag=="Y"){
							refreshPage();
						}
						}, 4000);*/
				} else {
					refreshPage();
				}
				if (action == 'close') {
					disableForClose();
				}
				}
		hideProcessing();
		}
	
	
	/*var response = data;
	if (action == 'save') {
		if (response != null && response != 'FAILURE') {
			jQuery('#manifestId').val(response);
			isSaved = true;
			hideProcessing();	
			alert('Manifest saved successfully.');
			$('#outManifestTable').dataTable().fnClearTable();
			searchManifestDtls();
			
			 * disableHeaderForSearch(); disableGridAfterSave();
			 
		} else {
			hideProcessing();	
			alert('Manifest not  saved successfully.');
		}
	} else if (action == 'close') {
		if (response != null && response != 'FAILURE') {
			jQuery('#manifestId').val(response);
			hideProcessing();	
			alert('Manifest closed successfully.');
			// disableBranchOutForClose();
			
			 * disableForClose(); if(!printManifestDtls()){ var url =
			 * "./branchOutManifestDox.do?submitName=viewBranchOutManifestDox";
			 * document.branchOutManifestDoxForm.action = url;
			 * document.branchOutManifestDoxForm.submit(); }
			 
			var confirm1 = confirm("Do you want to print BPL Out Manifest Details!");
			if (confirm1) {
				printDtls();
			} else {
				cancelPage();
			}
			if (action == 'close') {
				disableForClose();
			}
		} else {
			hideProcessing();	
			alert('Manifest not closed successfully.');
		}

	}*/
}

/* @Desc: Search the manifest details */
function searchManifestDtls() {
	if ($('#outManifestTable').dataTable().fnGetData().length < 1) {
		addRows();
	}
	//setIFrame();
	var manifestNO = document.getElementById("manifestNo").value;
	var loginOffceID = document.getElementById('loginOfficeId').value;
	
	if (!isNull(manifestNO)) {
	url = './branchOutManifestDox.do?submitName=searchManifestDetails&manifestNo='
			+ manifestNO + "&loginOfficeId=" + loginOffceID;
	showProcessing();
	jQuery.ajax({
		url : url,
		success : function(req) {
			printCallBackManifestDetails(req);
		}
	});
	}else{
		alert("Please provide branch manifest number");
	}
}

/* @Desc: Populates the manifest details on search in the header and grid */
function printCallBackManifestDetails(data) {
	// TODO: Need to display the values on screen
	// setting the header details
	
	if (!isNull(data)) {
		var responseText = jsonJqueryParser(data);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			document.getElementById("manifestNo").value = "";
			document.getElementById("manifestNo").focus();
			alert(error);
		} else {
	
			hideProcessing();
			if (!isNull(data)) {

		var response = eval('(' + data + ')');
		disableHeaderForSearch();
		document.getElementById("destOffice").value = response.destinationOfficeId;
		document.getElementById("loadNo").value = response.loadNo;
		document.getElementById("dateTime").value = response.manifestDate;
		/*
		 * var regionOffice = response.destinationOfficeName + "-"; var
		 * loggedInOfcName = response.loginOfficeName; var setLoginOfficeName =
		 * regionOffice.concat(loggedInOfcName);
		 * document.getElementById("originOffice").value = setLoginOfficeName;
		 */
		document.getElementById("manifestId").value = response.manifestId;
		document.getElementById("manifestProcessId").value = response.manifestProcessTo.manifestProcessId;
		// setting the total weight in the header
		/*
		 * var splitweightGm = response.finalWeight +""; if
		 * (!isNull(splitweightGm)) {
		 * document.getElementById("finalWeight").value = splitweightGm;
		 * weightKgValue = splitweightGm.split(".");
		 * document.getElementById("finalKgs").value = weightKgValue[0];
		 * weightGmValue = splitweightGm.split(".")[1]; if
		 * (isNull(weightGmValue)) { weightGmValue = "00";
		 * document.getElementById("finalGms").value = weightGmValue; } else {
		 * document.getElementById("finalGms").value = weightGmValue; } }
		 */

		// split and set final weight
		getDomElementById("finalWeight").value = response.finalWeight;
		var wt = getDomElementById("finalWeight").value;
		splitWeights(wt, "finalKgs", "finalGms", "");// 4th param is null for
														// final weight

		// setting the grid details
		var maxCn = parseInt(getDomElementById("maxCNsAllowed").value);
		var coMailCount = 1;
		for ( var i = 0; i < response.branchOutManifestDoxDetailsTOList.length; i++) {
			// disableFields(i+1);
			if (isNull(response.branchOutManifestDoxDetailsTOList[i].comailNo)) {
				disableGridRowById(i + 1);
			}
			// jQuery("#consigntNo" +
			// i+1).val(manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].consgNo);
			// document.getElementById("consgIds" + ROW_COUNT).value =
			// manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[0].consgId;
			if (isNull(response.branchOutManifestDoxDetailsTOList[i].comailNo)) {
				document.getElementById("consigntNo" + (i + 1)).value = response.branchOutManifestDoxDetailsTOList[i].consgNo;
				// document.getElementById("consgIds" + (i+1)).value =
				// response.branchOutManifestDoxDetailsTOList[i].consgId;
				document.getElementById("consignmtNo" + (i + 1)).value = response.branchOutManifestDoxDetailsTOList[i].consgId;
				document.getElementById("bankName" + (i + 1)).value = response.branchOutManifestDoxDetailsTOList[i].bankName;
				document.getElementById("consManifestId" + (i + 1)).value = response.branchOutManifestDoxDetailsTOList[i].consgManifestedId;
				if (!(response.branchOutManifestDoxDetailsTOList[i].lcAmount == '0.0')) {
					document.getElementById("LCAmount" + (i + 1)).value = response.branchOutManifestDoxDetailsTOList[i].lcAmount;
				}
			}
			if (!isNull(response.branchOutManifestDoxDetailsTOList[i].comailNo)) {
				disableComailGridRowById((parseInt(maxCn + coMailCount)));
				document.getElementById("comailNo"
						+ (parseInt(maxCn + coMailCount))).value = response.branchOutManifestDoxDetailsTOList[i].comailNo;
				document.getElementById("comailIdHidden"
						+ (parseInt(maxCn + coMailCount))).value = response.branchOutManifestDoxDetailsTOList[i].comailId;
				document.getElementById("comailManifestId"
						+ (parseInt(maxCn + coMailCount))).value = response.branchOutManifestDoxDetailsTOList[i].comailManifestedId;
				if (!(response.branchOutManifestDoxDetailsTOList[i].lcAmount == '0.0')) {
					document.getElementById("LCAmount"
							+ (parseInt(maxCn + coMailCount))).value = response.branchOutManifestDoxDetailsTOList[i].lcAmount;
				}
				coMailCount++;
			}

			// document.getElementById("pincode" + (i+1)).value =
			// manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].pincode;
			// jQuery("#pincode" +
			// i+1).val(manifestDetailsTO.thirdPartyOutManifestDoxDetailsToList[i].pincode);
			eachConsgWeightArr[(i + 1)] = parseFloat(response.branchOutManifestDoxDetailsTOList[i].weight);
			var splitweightGm = response.branchOutManifestDoxDetailsTOList[i].weight
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
		}
		var manifestStatus = response.manifestStatus;
		if (manifestStatus == "C") {
			document.getElementById("manifestStatus").value =manifestStatus;
			// disables the buttons if manifest status is close
			disableForClose();
			var manifestNo = getDomElementById("manifestNo").value;
			if(closeAction==null){
			alert("Branch OutManifest No.: " + manifestNo + ' already closed');
			}
		}
		// isSaved = true;
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

/*
 * @Desc: Deletes the consignment and accordingly modifies the total weight in
 * the header
 */
function deleteConsDtlsClientSide() {

	var result = deleteComailClientSide();
	// if(coMailDelete){
	// deleteComailClientSide();
	// }
	// else{
	var isDel = false;
	var subTotalWeight = 0;
	var maxCNRows = getDomElementById("maxCNsAllowed").value;
	var weightInFraction = 0.0;
	for ( var i = 1; i <= maxCNRows; i++) {
		var cnNo = getDomElementById("consigntNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true && !isNull(cnNo)) {
			// before clearing grid details getting weight
			var weightKg = document.getElementById("weight" + i).value;
			weightKg = (isNull(weightKg)) ? "0" : weightKg;
			var weightGm = document.getElementById("weightGm" + i).value;
			weightGm = (isNull(weightGm)) ? "000" : weightGm;
			if (weightGm == undefined) {
				weightGm = "000";
			} else if (weightGm.length == 1) {
				weightGm += "00";
			} else if (weightGm.length == 2) {
				weightGm += "0";
			} else
				weightGm = weightGm.substring(0, 3);

			weightInFraction = parseFloat(weightKg) + parseFloat(weightGm)
					/ 1000;
			// Header Total Weight
			var totalWeight = document.getElementById("finalWeight").value;
			totalWeight = parseFloat(totalWeight);
			subTotalWeight = totalWeight * 1000 - weightInFraction * 1000;
			subTotalWeight /= 1000;

			// Setting final wt after deduction in header and hidden Field
			document.getElementById("finalWeight").value = subTotalWeight;
			var finalWeightStr = subTotalWeight + "";
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
				weightGmValueFinal = weightGmValueFinal.substring(0, 3);
			document.getElementById("finalGms").value = weightGmValueFinal;
			// setting the weight 0 in global array while deleting
			eachConsgWeightArr[i] = "0";
			// Clearing the fields
			getDomElementById("ischecked" + i).checked = "";
			getDomElementById("consigntNo" + i).value = "";
			getDomElementById("consignmtNo" + i).value = "";
			getDomElementById("LCAmount" + i).value = "";
			getDomElementById("bankName" + i).value = "";
			getDomElementById("weights" + i).value = "";// hidden field
			getDomElementById("weight" + i).value = "";
			getDomElementById("weightGm" + i).value = "";

			/*
			 * //enable field for new consignment
			 * getDomElementById("checked"+i).checked=false;
			 * getDomElementById("consigntNo"+ (i+1)).disabled = false;
			 * getDomElementById("weight"+ (i+1)).disabled = false;
			 * getDomElementById("weightGm"+ (i+1)).disabled = false;
			 * getDomElementById("pincode"+ (i+1)).disabled = TSfalse;
			 */

			isDeleted = true;
			isDel = true;
			isSaved = false;
			enableElement(getDomElementById("consigntNo" + i));
		}
		if (isNull(cnNo)) {
			getDomElementById("ischecked" + i).checked = false;
		}
	}
	if (isDel || result == "success") {
		alert('Record(s) deleted successfully');
	} else {
		alert("Please select a Non Empty row to delete.");
	}
	// }//end of else
}

/* @Desc: Validates the manifest number */
function fnValidateNumber(obj) {
	if (!isNull(obj.value)) {
		var count = getRowId(obj, "consigntNo");
		if (!isDuplicateManifest(obj, count)) {
			// isValidManifestNo(obj);
			// validateCons(getDomElementById("consigntNo"+count));
		}
	}
}

/* @Desc: Checks for duplicate consignment number in the grid */
function isDuplicateManifest(consObj, count) {
	var isValid = true;
	var currentManifest = consObj.value.toUpperCase();
	// var currManifestAfterTrim = $.trim(currentManifest.value);
	var maxCNRow = getDomElementById("maxCNsAllowed").value;
	var table = document.getElementById('outManifestTable');
	var cntofR = table.rows.length;
	var totalRows = parseInt(maxCNRow) + 1;
	var lastRowAftrCN = parseInt(maxCNRow) + 1;
	for ( var i = 1; i < (totalRows); i++) {
		if (i == (lastRowAftrCN)) {
			i++;
		}
		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevManifest = document.getElementById('consigntNo' + id).value;
		// var prevManifestAfterTrim = $.trim(prevManifest.value);

		if (!isNull(prevManifest) && !isNull(currentManifest)) {
			if (count != id) {
				if (prevManifest.toUpperCase() == currentManifest) {
					consObj.value = "";
					setTimeout(function() {
						consObj.focus();
					}, 10);
					alert("Consignment already entered");
					isValid = false;
				}
			}
		}
	}
	return isValid;
}

// disables all the checkbox and the buttons
function disableBranchOutForClose() {
	disableAllCheckBox();
	disableAll();
	buttonEnabled("printBtn", "btnintform");
	jQuery("#" + "cancelBtn").attr("disabled", false);
}

// disables all the checkbox
function disableAllCheckBox() {
	getDomElementById("checkAll").disabled = true;
	for ( var i = 1; i <= maxRowAllowed; i++) {
		getDomElementById("ischecked" + i).disabled = true;
	}
}

/*function validateHeader() {
	var manifestNo = getDomElementById("manifestNo");
	var destOfficeId = getDomElementById("destOffice");
	var loadNoId = getDomElementById("loadNo");

	if (isNull(manifestNo.value)) {
		alert("Please provide Manifest No.");
		setTimeout(function() {
			manifestNo.focus();
		}, 10);
		return false;
	}
	if (isNull(destOfficeId.value)) {
		alert("Please select Destination Office");
		setTimeout(function() {
			destOffice.focus();
		}, 10);
		return false;
	}
	if (isNull(loadNoId.value)) {
		alert("Please select Load No.");
		setTimeout(function() {
			loadNo.focus();
		}, 10);
		return false;
	}
	return true;
}*/

function validateHeader(){
	var msg = "Please provide : ";
	var isValid = true;
	var manifestNo = $("#manifestNo")[0];
	var destOfficeId = $("#destOffice")[0];
	var loadNoId = $("#loadNo")[0];
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
/*
 * function validateGridDetails(){ for(var i=1; i<=maxCNsAllowed.value; i++){
 * 
 * if(i==1){//atleast one row should be filled if(!validateSelectedRow(i)) {
 * return false; } } else if(getDomElementById("ischecked"+i).checked){
 * if(!validateSelectedRow(i)) { return false; } } } return true; }
 */

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails() {
	var flag = false;
	var maxCNsAllowed = getDomElementById("maxCNsAllowed").value;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById("consignmtNo" + i).value)
				|| !isNull(getDomElementById("consigntNo" + i).value)) {// ischecked
			/*
			 * if(!validateSelectedRow(i)) { return false; }
			 */
			flag = true;
		}
	}
	if (!flag) {
		alert("Please enter atleast one Consignment Number");
		var maxComail = getDomElementById("maxComailsAllowed").value;
		for ( var i = 1; i <= maxComail; i++) {
			if (!isNull(getDomElementById("comailNo"
					+ (i + parseInt(maxCNsAllowed))).value)) {// ischecked
				getDomElementById("comailNo" + (i + parseInt(maxCNsAllowed))).value = "";
			}
		}
	}
	return flag;
}
/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var consgNo = getDomElementById("consigntNo" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull(consgNo.value)) {
		alert("Please provide Consignment No. to be scanned " + lineNum);
		setTimeout(function() {
			consgNo.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
/*
 * function validateSelectedRow(rowId) { var consgNo =
 * getDomElementById("consigntNo"+rowId); var lineNum = "at line :"+rowId; var
 * manifestId = getDomElementById("manifestId").value;
 * if(isNull(consgNo.value)){ alert("Please provide Consignment No. to be
 * scanned "+lineNum); setTimeout(function(){consgNo.focus();},10); return
 * false; } return true; }
 */

/**
 * @Desc checks if all the consignmnt rows in the grid is filled
 * @returns {Boolean}
 */
function isAllConsignmtRowInGridFilled(maxconsRowAllowed) {
	var isAllConsRowFilled = true;
	for ( var i = 1; i <= maxconsRowAllowed; i++) {
		if (isNull(getDomElementById("consigntNo" + i).value)) {
			isAllConsRowFilled = false;
			break;
		}
	}
	return isAllConsRowFilled;
}

/**
 * @Desc checks if all the comail rows in the grid is filled
 * @returns {Boolean}
 */
function isAllComailRowInGridFilled(maxcomailRowAllowed) {
	var isAllComailRowFilled = true;
	var maxCn = getDomElementById("maxCNsAllowed").value;
	for ( var i = 1; i <= maxcomailRowAllowed; i++) {
		if (isNull(getDomElementById("comailNo" + (i + parseInt(maxCn))).value)) {
			isAllComailRowFilled = false;
			break;
		}
	}
	return isAllComailRowFilled;
}

/**
 * @Desc check wheather manifestNo is already manifested or not to avoid
 *       duplicate entry into the database
 */

/*function saveOrCloseBranchOutManifestDox(action) {
	if (action == "close")
		isSaved = false;
	if (!isSaved && validateHeader() && validateGridDetails()) {
		var manifestId = getDomElementById("manifestId").value;
		if (isNull(manifestId)) {// create new manifest
			*//** To avoid duplicate enrty into Database *//*
			validateForSaveOrClose(action);
		} else { // save or close after search
			checkIfDeletedBeforeSave(action);
		}
	} else {
		if (isSaved)
			alert("Data already saved.");
	}
}*/

function validateForSaveOrClose(action) {
	var flag = true;
	if (action == "close")
		isSaved = false;
	if (!isSaved && validateHeader() && validateGridDetails()) {
		// to check manifest id for saving after update on search
		var manifestId = getDomElementById("manifestId").value;
		if (isNull(manifestId)) {
			var manifestNo = getDomElementById("manifestNo").value;
			var manifestType = getDomElementById("manifestDirection").value;
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var processCode = getDomElementById("processCode").value;
			var url = "./branchOutManifestParcel.do?submitName=isManifested&manifestNo="
					+ manifestNo
					+ "&manifestType="
					+ manifestType
					+ "&loginOfficeId="
					+ loginOfficeId
					+ "&processCode="
					+ processCode;

			// showProcessing();
			jQuery
					.ajax({
						url : url,
						success : function(req) {
							// hideProcessing();
							if (req == "FAILURE") {								
								var manifestNumObj = getDomElementById("manifestNo");
								manifestNumObj.value = "";
								setTimeout(function() {
									manifestNumObj.focus();
								}, 10);
								alert("Manifest number already used to create another manifest.");
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
 * @Desc check if delete before save or close
 * @param action
 */
function checkIfDeletedBeforeSave(action) {
	var manifestId = getDomElementById("manifestId").value;
	// delete the Consg/Manifest/Co-Mail before saving
	if (isDeleted && !isNull(manifestId)) {
		deleteTableRow(action);
		//deleteTableRowForBout(action)
	} else {
		performSaveOrClose(action);
	}
}

/**
 * @Desc To make element read only (text field)
 * @param domElement
 */
function disableElement(domElement) {
	domElement.readOnly = true;
	domElement.setAttribute("tabindex", "-1");
}

/**
 * @Desc After details searched successfully: call disableHeaderForSearch
 *       function
 */
function disableHeaderForSearch() {
	// read only search text field
	var manifestNo = getDomElementById("manifestNo");
	disableElement(manifestNo);
	// disable drop downs
	disableHeaderDropDown();
	// disable search button
	getDomElementById("Find").disabled = true;
}

/**
 * @Desc To disable header drop down menus
 */
function disableHeaderDropDown() {
	getDomElementById("loadNo").disabled = true;
	getDomElementById("destOffice").disabled = true;
}

/**
 * @Desc To disable grid by rowId
 * @param domElementId
 */
function disableGridRowById(domElementId) {
	// read only text fields
	// jQuery("consigntNo"+domElementId).attr("disabled", "disabled");
	disableElement(getDomElementById("consigntNo" + domElementId));
	disableElement(getDomElementById("comailNo" + domElementId));
	// disableElement(getDomElementById("bankName"+domElementId));
	// disableElement(getDomElementById("LCAmount"+domElementId));
}

/**
 * @Desc To disable grid by rowId
 * @param domElementId
 */
function disableComailGridRowById(domElementId) {
	// read only text fields
	// jQuery("consigntNo"+domElementId).attr("disabled", "disabled");
	disableElement(getDomElementById("comailNo" + domElementId));
	// disableElement(getDomElementById("bankName"+domElementId));
	// disableElement(getDomElementById("LCAmount"+domElementId));
}

/* Co-Mail */
function validateComail(obj) {
	// if(!isAllConsignmtRowInGridFilled()){
	var flag = true;
	if (!isNull(obj.value)) {
		var comailNo = "";
		comailNo = obj.value;
		if (comailNo.length < 12 || comailNo.length > 12) {
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			alert("Comail number length should be 12 character");
			flag = false;
		} // {
		// var numpattern = /^[0-9]{3,20}$/;
		// var coMailStartSeries = 'CM';
		else if (obj.value.substring(0, 2).toUpperCase() != coMailStartSeries
				|| !numpattern.test(obj.value.substring(3, 12))) {
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			alert('Comail number format is not correct');
			flag = false;
		} else {
			var count = getRowId(obj, "comailNo");
			isDuplicateCoMail(obj, count);
		}/*
			 * else { var count = getRowId(obj, "consigntNo"); count =
			 * parseIntNumber(count) + 1; // last row if (count <=
			 * (parseIntNumber(maxCNsAllowed) +
			 * parseIntNumber(maxComailsAllowed)))
			 * document.getElementById("consigntNo" + count).focus(); }
			 */
		// }
	}
	return flag;
	// }
	/*
	 * else{ if(!isNull(obj.value)){ alert('Please enter atleast one consignment
	 * number'); obj.value = ""; } setTimeout(function() { obj.focus(); }, 10); }
	 */
}

/**
 * @Desc print the details
 */
/*function printDtls() {
	if (confirm("Do you want to print?")) {
		// TODO implements print functionality
		var manifestNo = getDomElementById("manifestNo").value;
		var loginOfficeId = getDomElementById("loginOfficeId").value;
		if (!isNull(manifestNo)) {
			var url = './branchOutManifestDox.do?submitName=printManifestDtls&manifestNo='
					+ manifestNo + "&loginOfficeId=" + loginOfficeId;
			window
					.open(
							url,
							'newWindow',
							'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left = 412,top = 184,scrollbars=yes');
			// showProcessing();
			jQuery.ajax({
				url : url,
				success : function(req) {
					// hideProcessing();
				}
			});
		} else {
			alert("Please provide Manifest No.");
		}
	}
}*/

// Bring the focus on close if all the rows in the grid are filled
function focusOnclose() {
	if (isAllRowInGridFilled(maxRowAllowed)) {
		var save = document.getElementById("saveBtn");
		setTimeout(function() {
			save.focus();
		}, 20);
	}
}

/* @Desc: enables the header drop down */
function enableHeaderDropDown() {
	if (document.getElementById("loadNo").disabled == true) {
		document.getElementById("loadNo").disabled = false;
	}
	if (document.getElementById("destOffice").disabled == true) {
		document.getElementById("destOffice").disabled = false;
	}
	// document.getElementById("manifestNo").readOnly = false;
}

/**
 * @Desc disable elements for saved manifest
 */
function disableGridAfterSave() {
	for ( var i = 1; i <= maxRowAllowed; i++) {
		if (!isNull(getDomElementById("consigntNo" + i).value)) {
			disableGridRowById(i);
		}
	}
}

/**
 * @Desc To disable grid by rowId
 * @param domElementId
 */
function disableGridRowById(domElementId) {
	// read only text fields
	disableElement(getDomElementById("consigntNo" + domElementId));
	disableElement(getDomElementById("consigntNo" + domElementId));
}

/**
 * @Desc To check for duplicate co mail
 * @param
 */
function isDuplicateCoMail(comailObj, count) {
	var currentCoMail = comailObj.value;
	
	var maxCN = getDomElementById("maxCNsAllowed").value;
	var maxCoMailRow = getDomElementById("maxComailsAllowed").value;
	var table = document.getElementById('outManifestTable');
	var totalRows = parseInt(maxCN) + 1 + parseInt(maxCoMailRow);
	for ( var i = parseInt(maxCN) + 2; i < (totalRows); i++) {
		var id = table.rows[i].cells[0].childNodes[0].id.substring(9);
		var prevCoMail = document.getElementById('comailNo' + id).value;
	
		if (!isNull(prevCoMail)
				&& !isNull(currentCoMail)) {
			if (count != id) {
				if (prevCoMail == currentCoMail) {
					comailObj.value = "";
					setTimeout(function() {
						comailObj.focus();
					}, 10);
					alert("CoMail already entered");
				}
			}
		}
	}
}

/**
 * printBranchOutManifest
 * 
 * @author sdalli
 */
function printBranchOutManifest() {
	if(confirm("Do you want to print?")){
	var manifestStatus = getDomElementById("manifestStatus").value;

	//var manifestNoObj = getDomElementById("manifestNo");
	var loginOfficeId = getDomElementById("loginOfficeId").value;
	if (manifestStatus == "O") {
		alert("Only closed manifest can be printed.");
	} else{
	/*if (!isNull(manifestNoObj.value)) {
		var flag= true;
		while(flag){
			if(printFlag=="Y"){
				flag=false;
				refreshFlag="Y";
				window.frames['iFrame'].focus();
				window.frames['iFrame'].print();
				break;
			}
		}
	}*/
		/*var confirm1 = confirm("Do you want to print Branch Manifest Details!");
		if (confirm1) {
			printBranchOutManifestDtls(manifestNoObj.value, loginOfficeId);
			window.frames['iFrame'].focus();
			window.frames['iFrame'].print();
		} else {
			var url = "./branchOutManifestDox.do?submitName=viewBranchOutManifestDox";
			document.branchOutManifestDoxForm.action = url;
			document.branchOutManifestDoxForm.submit();
		}*/
		
		var manifestNoObj = getDomElementById("manifestNo").value;
		if (!isNull(manifestNoObj)) {
			var loginOfficeId = getDomElementById("loginOfficeId").value;
			var url = "./branchOutManifestDox.do?submitName=printManifestDtls&manifestNo="
				+ manifestNoObj + "&loginOfficeId=" + loginOfficeId;
			var w =window.open(url,'myPopUp','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			}else {
		document.getElementById("manifestNo").value = "";
		document.getElementById("manifestNo").focus();
		alert("Please Enter BPL No.");
			}
		}
	}
}

/**
 * printBranchOutManifestDtls
 * 
 * @param manifestNO
 * @param loginOfficeId
 * @author sdalli
 */
function printBranchOutManifestDtls(manifestNO, loginOffceID) {
	var url = "./branchOutManifestDox.do?submitName=printManifestDtls&manifestNo="
			+ manifestNO + "&loginOfficeId=" + loginOffceID;
	document.branchOutManifestDoxForm.action = url;
	document.branchOutManifestDoxForm.submit();
}

function isComailBooked(comailObj) {
	// showProcessing();
	if (!comailObj.readOnly) {
		
		url = './outManifestDox.do?submitName=validateComail&comailNo='
				+ comailObj.value;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackComail(req, comailObj);
			}
		});
	}
}
function printCallBackComail(data, comailObj) {
	var response = data;
	var rowId = getRowId(comailObj, "comailNo");
	if (!isNull(response)) {
		if (response == "N") {
			comailObj.value = "";
			setTimeout(function() {
				comailObj.focus();
			}, 10);
			alert("Comail number already used!");
		} else {
			url = './outManifestDox.do?submitName=getComailIdByComailNo&comailNo='
				+ comailObj.value;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackGetComailId(req,rowId);
			}
		});
			
			isSaved = false;
		}
	} else {
		isSaved = false;
	}
	// hideProcessing();
}



/**
 * delete the comail grid client side
 * 
 */
function deleteComailClientSide() {
	var isDel = false;
	var maxCN = getDomElementById("maxCNsAllowed").value;
	var maxComailRows = getDomElementById("maxComailsAllowed").value;
	var totalRows = parseInt(maxCN) + 1 + parseInt(maxComailRows);
	for ( var i = parseInt(maxCN) + 1; i < (totalRows); i++) {
		var comailNo = getDomElementById("comailNo" + i).value;
		if (getDomElementById("ischecked" + i).checked == true
				&& !isNull(comailNo)) {

			// Clearing the fields
			getDomElementById("ischecked" + i).checked = "";
			getDomElementById("comailNo" + i).value = "";
			getDomElementById("LCAmount" + i).value = "";
			getDomElementById("bankName" + i).value = "";

			/*
			 * //enable field for new consignment
			 * getDomElementById("checked"+i).checked=false;
			 * getDomElementById("consigntNo"+ (i+1)).disabled = false;
			 * getDomElementById("weight"+ (i+1)).disabled = false;
			 * getDomElementById("weightGm"+ (i+1)).disabled = false;
			 * getDomElementById("pincode"+ (i+1)).disabled = TSfalse;
			 */

			isDeleted = true;
			isDel = true;
			isSaved = false;
			enableElement(getDomElementById("comailNo" + i));
		}
		if (isNull(comailNo)) {
			getDomElementById("ischecked" + i).checked = false;
		}

	}
	var result = "";
	if (isDel) {
		// alert('Record(s) deleted successfully');
		result = "success";

	} else {
		// alert("Please select a Non Empty row to delete.");
		result = "empty";
	}
	return result;
}

function callFocusEnterKey(e) {
	rowId = 1;
	var focusId = document.getElementById("consigntNo" + rowId);
	return callEnterKey(e, focusId);

}

/**
 * @Desc To enable element (text field)
 * @param domElement
 */
function enableElement(domElement) {
	domElement.readOnly = false;
	domElement.setAttribute("tabindex", "0");
}

/* @Desc: splits weight */
function splitWeights(wt, wtKgId, wtGmId, rowId) {
	var weightArr = wt.split(".");
	var weightKgId = wtKgId + rowId;
	var weightGmId = wtGmId + rowId;
	getDomElementById(weightKgId).value = weightArr[0];
	getDomElementById(weightGmId).value = (!isNull(weightArr[1])) ? weightArr[1]
			.substring(0, 3)
			: "";
	if (weightArr[1] == undefined) {
		getDomElementById(weightGmId).value += "000";
	} else if (weightArr[1].length == 1) {
		getDomElementById(weightGmId).value += "00";
	} else if (weightArr[1].length == 2) {
		getDomElementById(weightGmId).value += "0";
	}
}

/** *For wt integration** */
function capturedWeightForManifest(weigth) {
	// alert('>>>wmCapturedWeight>>>'+wmCapturedWeight);\
	hideProcessing();
	var weightInKg = null;
	var weightGm = null;

	//wmActualWeight = parseFloat(wmCapturedWeight) - parseFloat(weigth);
	//wmActualWeight = parseFloat(wmActualWeight).toFixed(3);
	wmCapturedWeight = parseFloat(weigth).toFixed(3);

	// alert('machine weight'+wmActualWeight);
	newWMWt = wmCapturedWeight;
	var response = bookingDetail;
	if (response != null
			&& (!isEmptyWeight(newWMWt) || !isEmptyWeight(response.weight))) {

		jQuery("#consignmtNo" + ROW_COUNT).val(response.consgId);
		if (response.lcAmount != 0) {
			jQuery("#LCAmount" + ROW_COUNT).val(response.lcAmount);
		}
		// jQuery("#gridOriginOfficeId" +
		// ROW_COUNT).val(response.gridOriginOfficeId);
		jQuery("#bankName" + ROW_COUNT).val(response.bankName);
		jQuery("#rowToPay" + ROW_COUNT).val(response.toPayAmount);
		jQuery("#rowCOD" + ROW_COUNT).val(response.codAmount);

		// weighing Machine Integration

		var weight = parseFloat(response.weight).toFixed(3);
		getDomElementById("oldWeights" + ROW_COUNT).value = weight; // added for
																	// consignmnt
																	// wt
																	// updation
																	// in db

		if (!isNull(newWMWt) && parseFloat(newWMWt) > weight) {
			weight = newWMWt;
		}

		jQuery("#weights" + ROW_COUNT).val(weight);

		var splitweightGm = weight + "";

		// sets the weight in the grid
		if (!isNull(splitweightGm)) {
			weightKgValue = splitweightGm.split(".");
			document.getElementById("weight" + ROW_COUNT).value = weightKgValue[0];
			weightGmValue = splitweightGm.split(".")[1];
			document.getElementById("weightGm" + ROW_COUNT).value = (isNull(weightGmValue)) ? "000"
					: weightGmValue;

		}
		weightFormatForGm(getDomElementById("weightGm" + ROW_COUNT));
		isSaved = false;

		setTotalWt(ROW_COUNT);

	} else {
		hideProcessing();
		$("#LCAmount" + ROW_COUNT).val("");
		$("#bankName" + ROW_COUNT).val("");
		$("#consigntNo" + ROW_COUNT).val("");
		$("#consigntNo" + ROW_COUNT).focus();
		alert('Invalid Consignment');
	}
}

/**
 * To check whether grid is empty or not
 * 
 * @param domElementId
 */
function checkGridEmpty(domElement, domElementId) {
	var flag = false;
	var isFilled = false;
	NEW_DEST_OFFICE_ID = domElement.value;
	if (isNull(OLD_DEST_OFFICE_ID))
		OLD_DEST_OFFICE_ID = NEW_DEST_OFFICE_ID;
	// check whether any record(s) present in grid or not
	var maxCNsAllowed = getDomElementById("maxCNsAllowed").value;
	for ( var i = 1; i <= maxCNsAllowed; i++) {
		if (!isNull(getDomElementById(domElementId + i).value)) {
			isFilled = true;
			flag = confirm("Consignment number(s) already entered in the grid.\n\nDo you still want to make the changes in header?");
			break;
		}
	}
	// clear the non-empty row
	if (flag) {
		for ( var i = 1; i <= maxCNsAllowed; i++) {
			if (!isNull(getDomElementById(domElementId + i).value)) {
				if (!isEmptyWeight(getDomElementById("weights" + i).value))
					decreaseWeight(i);
				// clearGridRowDetails(i);
				getDomElementById("consigntNo" + i).value = "";
				getDomElementById("LCAmount" + i).value = "";
				getDomElementById("bankName" + i).value = "";
				getDomElementById("consignmtNo" + i).value = "";
				getDomElementById("weights" + i).value = "";// hidden field
				getDomElementById("weight" + i).value = "";
				getDomElementById("weightGm" + i).value = "";

				/*
				 * $("#LCAmount"+i).val(""); $("#bankName"+i).val("");
				 * $("#consigntNo"+i).val("");
				 */
			}
		}
		OLD_DEST_OFFICE_ID = NEW_DEST_OFFICE_ID;
		setValue = NEW_DEST_OFFICE_ID;
	} else {
		if (isFilled)
			setValue = OLD_DEST_OFFICE_ID;
		else
			setValue = NEW_DEST_OFFICE_ID;
	}
	domElement.value = setValue;

	return flag;
}

/**
 * To decrease the weight from final weight
 * 
 * @param rowId
 */
function decreaseWeight(rowId) {
	var subTotalWeight = 0.000;
	// before clearing grid details getweight
	var weightKg = document.getElementById("weight" + rowId).value;
	var weightGm = document.getElementById("weightGm" + rowId).value;
	var weightInFraction = ((weightKg) + (weightGm)) / 1000;

	// Header Total Weight
	var totalWeight = parseFloat(document.getElementById("finalWeight").value);
	subTotalWeight = totalWeight * 1000 - parseFloat(weightInFraction * 1000);
	subTotalWeight /= 1000;
	if (isEmptyWeight(subTotalWeight)) {
		subTotalWeight = 0.000;
	}

	// Setting final wt after deduction in header and hidden Field
	document.getElementById("finalWeight").value = parseFloat(subTotalWeight);

	var finalWeightStr = subTotalWeight + "";
	weightKgValueFinal = finalWeightStr.split(".");
	document.getElementById("finalKgs").value = weightKgValueFinal[0];
	if (!isNull(weightKgValueFinal[1]))
		document.getElementById("finalGms").value = weightKgValueFinal[1]
				.substring(0, 3);
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

/**
 * To clear page
 */
function clearPage() {
	if (confirm("Do you want to clear the page?")) {
		var url = "./branchOutManifestDox.do?submitName=viewBranchOutManifestDox";
		document.branchOutManifestDoxForm.action = url;
		document.branchOutManifestDoxForm.submit();
	}
}

function cancelPage() {
	var url = "./branchOutManifestDox.do?submitName=viewBranchOutManifestDox";
	window.location = url;
}

function refreshPage() {
	var url = "./branchOutManifestDox.do?submitName=viewBranchOutManifestDox";
	document.branchOutManifestDoxForm.action = url;
	document.branchOutManifestDoxForm.submit();
}


function getCoMailManifestIdOnCheck(checkObj) {
	var comailManifestIdListAtGrid = document.getElementById("comailManifestListId").value;
	var rowId = getRowId(checkObj, "ischecked");
	if(isNull(comailManifestIdListAtGrid)){
		var comailmanifstId = document.getElementById("comailManifestId" + rowId).value;
		document.getElementById("comailManifestListId").value = comailmanifstId;
	}else{
		var comailmanifstId = comailManifestIdListAtGrid + ","
		+ document.getElementById("comailManifestId" + rowId).value;
		document.getElementById("comailManifestListId").value = comailmanifstId;
	}
}

function deleteTableRowForBout(action) {
	
	var manifestIdListAtGrid;
	var consgIdListAtGrid;

	var manifestStatus = document.getElementById("manifestStatus");

	if (!(isNull(manifestStatus) && manifestStatus.value == "O")) {
		var consgIdList = document.getElementById("consgIdListAtGrid");
		if (!isNull(consgIdList)) {
			consgIdListAtGrid = document.getElementById("consgIdListAtGrid").value;
		} else {
			consgIdListAtGrid = "";
		}

		var manifestId = document.getElementById("manifestIdListAtGrid");
		if (manifestId == undefined) {
			manifestIdListAtGrid = "";
		} else {
			manifestIdListAtGrid = document
					.getElementById("manifestIdListAtGrid").value;
		}
		
		if (!isNull(consgIdListAtGrid) || !isNull(manifestIdListAtGrid)
				|| !isNull(comailIdListAtGrid)) {
			url = './outManifestDox.do?submitName=deleteGridElement&consgId='
					+ consgIdListAtGrid + '&manifestId=' + manifestIdListAtGrid
				
			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBackManifestDelete(req, action);
				}
			});
		} else {
			alert("Please select the data to delete");
		}
	} else {
		alert("Closed manifest can not be deleted");
	}
}

function printCallBackGetComailId(comailId,rowId){
	if(!isNull(comailId)){
		document.getElementById("comailIdHidden"+rowId).value =comailId ;
	}
}

function isValidJson(json) {
    try {
        JSON.parse(json);
        return true;
    } catch (e) {
        return false;
    }
}
function setIFrame(){
	var manifestNoObj = getDomElementById("manifestNo").value;
	if (!isNull(manifestNoObj)) {
		var loginOfficeId = getDomElementById("loginOfficeId").value;
		var url = "./branchOutManifestDox.do?submitName=printManifestDtls&manifestNo="
			+ manifestNoObj + "&loginOfficeId=" + loginOfficeId;
		printUrl = url;
		printIframe(printUrl);
		}
}

function checkForLastConsRow(consgNumberObj,event){
	var consid = getRowId(consgNumberObj, "consigntNo");
	if(consid==50){
		return callEnterKey(event, document.getElementById("comailNo51"));
	}else{
		//var count=parseInt(consid)+1;
		return callEnterKey(event, document.getElementById("consigntNo"+(parseInt(consid)+1)));
	}
}
