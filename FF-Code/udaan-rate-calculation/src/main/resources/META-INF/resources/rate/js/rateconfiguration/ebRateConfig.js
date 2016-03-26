rowCount = 1;
var saveMode = "";
var editMode = 'N';
/* @Desc: Adds the rows in grid dynamically */
function fnClickAddRow() {

	$('#ebRateGrid')
			.dataTable()
			.fnAddData(
					[

							'<input type="checkbox" id="isChecked'
									+ rowCount
									+ '" name="chkBoxName" value="" /><input type="hidden" id="prefId'
									+ rowCount
									+ '" name="to.prefId"/><input type="hidden" id="applicabilitys'
									+ rowCount
									+ '" name="to.applicabilitys"/><input type="hidden" id="ebPrefRateId'
									+ rowCount + '" name="to.ebPrefRateId"/>',
							'<input type="text" id="prefCode'
									+ rowCount
									+ '" name="to.prefCodes" size="10" class="txtbox" readOnly="true" />',
							'<input type="text" id="prefName'
									+ rowCount
									+ '" name="to.prefNames"  class="txtbox"  onkeypress="return callEnterKey(event, document.getElementById(\'prefDesc'
									+ rowCount + '\'));"  />',
							'<input type="text" id="prefDesc'
									+ rowCount
									+ '"  name="to.prefDescription" class="txtbox" size=100 onkeypress="return callEnterKey(event, document.getElementById(\'amount'
									+ rowCount + '\'));"  />',
							'<input type="text" id="amount'
									+ rowCount
									+ '" class="txtbox"  size="10" name="to.amount" onkeypress="return isValidForAddNewRow(event,'+rowCount+')">', ]);
	rowCount++;
	updateSrlNo('ebRateGrid');

}

function updateSrlNo(tableID) {
	try {
		var table = document.getElementById(tableID);
		for ( var i = 1; i < rowCount; i++) {
			var row = table.rows[i];
			var val = row.cells[1];
			// val.innerHTML = i;
		}
	} catch (e) {
		// alert(e);
	}
}

function enableTaxComponent(originObj) {
		
	var origin = originObj.value;
	stateCode = getDomElementById("stateCode").value;
	if (stateCode == jammuKashmir && !isNull(origin)) {
		/*getDomElementById("SurchargeOnST").value = "";
		getDomElementById("HigherEducationCess").value = "";
		getDomElementById("StateTax").value = "";
		getDomElementById("ServiceTax").value = "";
		getDomElementById("EducationCess").value = "";*/

		enableOrDisableTaxFields(false,true);
	} else {
		/*getDomElementById("SurchargeOnST").value = "";
		getDomElementById("HigherEducationCess").value = "";
		getDomElementById("StateTax").value = "";
		getDomElementById("ServiceTax").value = "";
		getDomElementById("EducationCess").value = "";*/

		
		
		enableOrDisableTaxFields(true,false);
	}

	loadDefaultTaxDetails(origin);
	loadDefaultEBRates(origin, 'onChange');

}

function enableOrDisableTaxFields(flag1,flag2){
	document.getElementById("StateTaxChk").disabled = flag1;
	document.getElementById("SurchargeOnSTChk").disabled = flag1;
	document.getElementById("ServiceTaxChk").disabled = flag2;
	document.getElementById("EducationCessChk").disabled = flag2;
	document.getElementById("HigherEducationCessChk").disabled = flag2;
}

function loadDefaultTaxDetails(stateId) {
	url = './emotionalBondRate.do?submitName=loadDefaultTaxComponent&stateId='
			+ stateId;
	jQuery.ajax({
		url : url,
		success : function(req) {
			printDefaultTaxComponentDetails(req);
		}
	});
}

function printDefaultTaxComponentDetails(ajaxResp) {
	var taxComponentTO = null;
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);

		} else {
			taxComponentTO = responseText;
			/*getDomElementById("SurchargeOnST").value = "";
			getDomElementById("HigherEducationCess").value = "";
			getDomElementById("StateTax").value = "";
			getDomElementById("ServiceTax").value = "";
			getDomElementById("EducationCess").value = "";*/

			if (!isNull(taxComponentTO)) {
				for ( var i = 0; i < taxComponentTO.length; i++) {
					if (taxComponentTO[i].rateComponentId.rateComponentCode == surchargeOnST
							&& getDomElementById("SurchargeOnST").value == "") {
						getDomElementById("SurchargeOnST").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("SurchargeOnST"));
					}
					if (taxComponentTO[i].rateComponentId.rateComponentCode == HigherEduCharges
							&& getDomElementById("HigherEducationCess").value == "") {
						getDomElementById("HigherEducationCess").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("HigherEducationCess"));
					}
					if (taxComponentTO[i].rateComponentId.rateComponentCode == stateTax
							&& getDomElementById("StateTax").value == "") {
						getDomElementById("StateTax").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("StateTax"));
					}

					if (taxComponentTO[i].rateComponentId.rateComponentCode == serviceTax
							&& getDomElementById("ServiceTax").value == "") {
						getDomElementById("ServiceTax").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("ServiceTax"));
					}

					if (taxComponentTO[i].rateComponentId.rateComponentCode == eduCharges
							&& getDomElementById("EducationCess").value == "") {
						getDomElementById("EducationCess").value = taxComponentTO[i].taxPercentile;
						setAmountFormatZero(getDomElementById("EducationCess"));
					}

				}

			}

		}

	}
}

function isValidForAddNewRow(e, row) {
	
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	
	if(key == 9 || key == 8 || key == 0){
		return true;
	}else if (key == 13){
		var tableee = document.getElementById('ebRateGrid');
		var totalRowCount = tableee.rows.length - 1;
		if (((rowCount-1) == row) && checkMandatoryForAdd(totalRowCount)) {
			var status = getDomElementById("rateStatus").value;
			if(isNull(status) || (status == CREATED)){
				//if(validateFormat(getDomElementById("amount"+row))){
					fnClickAddRow();
					if(!isNull(getDomElementById("prefName"+(rowCount-1)))){						
						setTimeout(function() {
							getDomElementById("prefName"+(rowCount-1)).focus();
						}, 10);
					}
				//}
			}
		}
	}
	
	else return onlyDecimal(e);
}

function addNewRow() {
	if (checkMandatoryForSave()) {
		fnClickAddRow();
	}
}

function checkMandatoryForAdd(rowId) {
	var prefName = getDomElementById("prefName" + rowId);
	var prefDesc = getDomElementById("prefDesc" + rowId);
	var amount = getDomElementById("amount" + rowId);

	var lineNum = "at line :" + rowId;

	if (isNull(prefName.value)) {
		alert("Please provide Preference Name " + lineNum);
		setTimeout(function() {
			prefName.focus();
		}, 10);
		return false;
	}
	if (isNull(prefDesc.value)) {
		alert("Please provide Preference Description " + lineNum);
		setTimeout(function() {
			prefDesc.focus();
		}, 10);
		return false;
	}
	if (isNull(amount.value)) {
		alert("Please provide Amount" + lineNum);
		setTimeout(function() {
			amount.focus();
		}, 10);
		return false;
	}
	return true;
}

function checkMandatoryForSave() {
	var rowId = null;

	var tableee = getDomElementById('ebRateGrid');
	var totalRowCount = tableee.rows.length;
	var lastRowId = getTableLastRowIdByElement('ebRateGrid', 'to.prefNames',
			'prefName');
	var prefCodeName = document.getElementsByName("to.prefNames");
	
	for ( var i = 1; i < totalRowCount; i++) {
		var cell = prefCodeName[i - 1];
		rowId = getRowId(cell, "prefName");

		if (rowId == lastRowId) {
			
			if (isLastRowEmpty(lastRowId)) {
				if((rowCount-1) != 1){
					return true;
				}
			}
		}

		if (!checkMandatoryForAdd(rowId)) {
			return false;
		}

	}

	return true;
}

/**
 * getTableLastRowIdByElement
 * 
 * @param tableId
 * @param elementName
 * @param elementId
 * @returns {Number}
 */
function getTableLastRowIdByElement(tableId, elementName, elementId) {
	var rowCount = 1;
	var tableee = document.getElementById(tableId);
	var cell = document.getElementsByName(elementName);
	var cntofRow = tableee.rows.length;
	if (cntofRow > 2) {
		var dom = cell[cntofRow - 2];
		rowCount = getRowId(dom, elementId);
	}
	return rowCount;
}

function isLastRowEmpty(rId) {

	var prefCode = getDomElementById("prefCode" + rId);
	if (prefCode != null && isNull(prefCode.value)) {
		return true;
	}
	return false;
}

function deactivatePreference() {
	var prefIdAtGrid = "";
	if((rowCount-1) == 1){
		alert("Only one preference exist, so Preference details can't Deactivate");
		return false;
	}
	
	var status = document.getElementById("rateStatus").value;
	if (status == SUBMITED) {
		/*jQuery('#ebRateGrid >tbody >tr')
				.each(
						function(i, tr) {
							
							var isChecked = jQuery(this).find('input:checkbox')
									.is(':checked');
							if (isChecked) {
								prefIdAtGrid = document
										.getElementById("prefIdAtGrid").value;
								if (isNull(prefIdAtGrid)) {
									var prefCode = getDomElementById("prefId"
											+ (i + 1)).value;
									document.getElementById("prefIdAtGrid").value = prefCode;
								} else {
									var prefCode = prefIdAtGrid
											+ ","
											+ document.getElementById("prefId"
													+ (i + 1)).value;
									prefIdAtGrid = prefCode;

								alert("Hello");
								}
							}

						});
*/
		
		for(var j=1;j<rowCount;j++)
		{
			if(!isNull(getDomElementById("isChecked"+j)) && getDomElementById("isChecked"+j).checked == true){
				if(!isNull(getDomElementById("ebPrefRateId"+j).value)){
				if(prefIdAtGrid.length > 0 ){
					prefIdAtGrid = idsList+",";
				}
				prefIdAtGrid = prefIdAtGrid+ getDomElementById("ebPrefRateId"+j).value;
				}
			}
		}
		
		if (!isNull(prefIdAtGrid)) {
			showProcessing();
			url = './emotionalBondRate.do?submitName=deactivatePreferences&prefId='
					+ prefIdAtGrid;
			jQuery.ajax({
				url : url,
				success : function(req) {
					printCallBackDeactivatePreference(req);
				}
			});
		} else {
			alert("Please select the Preferences to Deactivate");
		}
	} else {
		alert("Please Submit the Rates");
	}
}

function printCallBackDeactivatePreference(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			hideProcessing();
			alert(error);

		} else {
			hideProcessing();
			alert(success);
			try {
				var table = document.getElementById("ebRateGrid");
				var rowCount = table.rows.length;

				for ( var i = 0; i < rowCount; i++) {
					var row = table.rows[i];
					var chkbox = row.cells[0].childNodes[0];
					if (null != chkbox && true == chkbox.checked) {
						if (rowCount <= 2) {
							alert("Cannot deactivate all the preferences.");
							break;
						}
						deleteRow("ebRateGrid", i - 1);
						rowCount--;
						i--;
					}
				}
			} catch (e) {
				alert(e);
			}
		}
	}

}
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function setAmountFormatZero(domElement) {
	if (!isNull(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];

	} else {
		domElement.value = "0.00";
	}
}

/**
 * To set Default Date(s).
 */
function setDefaultDates() {
	var arrTodayDt = todayDate.split("/");// Today date
	var todayDt = new Date(arrTodayDt[2], arrTodayDt[1] - 1, arrTodayDt[0]);
	var fromDt = new Date(todayDt.getTime() + (24 * 60 * 60 * 1000));
	jQuery("#validFromDate").val(getDateInDDMMYYYY(fromDt));

}

/**
 * To convert Date into DD/MM/YYYY String
 * 
 * @param dt
 * @returns {String}
 */
function getDateInDDMMYYYY(dt) {
	var DD = dt.getDate() + "";
	DD = (DD.length == 1) ? "0" + DD : DD;// Set 2 digit format
	var MM = (dt.getMonth() + 1) + "";
	MM = (MM.length == 1) ? "0" + MM : MM;// Set 2 digit format
	var YYYY = dt.getFullYear();
	var DDMMYYYY = DD + "/" + MM + "/" + YYYY;
	return DDMMYYYY;
}

/**
 * To validate EFFECTIVE FROM DATE
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateFromDate(obj) {
	if (!isNull(obj.value)) {
		var i = compareDates(todayDate, obj.value);
		if (i == undefined || i == 0 || i == 1) {
			alert("Effective from date should be greater than today date.");
			setTimeout(function() {
				obj.focus();
			}, 10);
			obj.value = "";
			return false;
		}

	}
	return true;
}

function compareDates(date1, date2) {
	if (date1 == undefined || date2 == undefined) {
		return -100;// garbage value
	}
	date1 = date1.split("/");
	date2 = date2.split("/");

	var myDate1 = new Date();
	myDate1.setFullYear(date1[2], date1[1] - 1, date1[0]);

	var myDate2 = new Date();
	myDate2.setFullYear(date2[2], date2[1] - 1, date2[0]);
	if (myDate1 == myDate2) {
		return 0;
	} else if (myDate1 < myDate2) {
		return -1;
	} else if (myDate1 > myDate2) {
		return 1;
	}
}
function checkValidDate() {
	if(editMode == 'N'){
		var validFromDate = getDomElementById("validFromDate");
		if (isNull(validFromDate.value)) {
			alert("Please select a Effective from date");
			setTimeout(function() {
				validFromDate.focus();
			}, 10);
			return false;
		}
		if(!validateFromDate(validFromDate)){
			return false;
		}
	}
	return true;
}

function saveOrUpdateEBRate(saveMode) {
	document.getElementById("saveMode").value = saveMode;
	//document.getElementById("action").value = saveMode;
	
	var orgState = document.getElementById("originState").value;
	
	if(!isNull(orgState)){
		document.getElementById("curStateId").value = orgState;
	}
	
	
	//if (status == SUBMITED) {
		document.getElementById("EducationCessChk").disabled = false;
		document.getElementById("HigherEducationCessChk").disabled = false;
		document.getElementById("StateTaxChk").disabled = false;
		document.getElementById("SurchargeOnSTChk").disabled = false;
		document.getElementById("ServiceTaxChk").disabled = false;
	//}
	if (saveMode == RENEWED) {
		document.getElementById("saveMode").value = CREATED;
		jQuery("#isRenew").val("Y");
		jQuery("#currentEBRateConfigId").val(jQuery("#ebRateConfigId").val());
		jQuery("#ebRateConfigId").val("");

		jQuery('#ebRateGrid >tbody >tr').each(function(i, tr) {
			getDomElementById("ebPrefRateId" + (i + 1)).value = "";

		});
	}
	if (checkMandatoryForSave() && checkValidDate()) {
		showProcessing();
		var url = './emotionalBondRate.do?submitName=saveOrUpdateEBRate';
		jQuery.ajax({
			url : url,
			data : jQuery("#ebRateConfigForm").serialize(),
			success : function(req) {
				printCallBackABRate(req, saveMode);
			}
		});
	}
}

function printCallBackABRate(ajaxResp, saveMode) {
	var ebConfigTO = null;
	var status = document.getElementById("rateStatus").value;
	if (status == SUBMITED) {
		document.getElementById("EducationCessChk").disabled = true;
		document.getElementById("HigherEducationCessChk").disabled = true;
		document.getElementById("StateTaxChk").disabled = true;
		document.getElementById("SurchargeOnSTChk").disabled = true;
		document.getElementById("ServiceTaxChk").disabled = true;
	}
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			ebConfigTO = responseText;
			jQuery("#ebRateConfigId").val(ebConfigTO.ebRateConfigId);
			document.getElementById("rateStatus").value = ebConfigTO.status;
			for ( var i = 0; i < ebConfigTO.prefCodes.length; i++) {
				document.getElementById("prefCode" + (i + 1)).value = ebConfigTO.prefCodes[i];
			}

			if (saveMode == CREATED) {
				buttonDisabled("editBtn", "btnintform");
				buttonDisabled("renewBtn", "btnintform");
				alert(ebConfigTO.successMessage);
				loadDefaultEBRates("", "onLoad","");				
			} else if (saveMode == SUBMITED) {

				buttonEnabled("editBtn", "btnintformbigdis");
				buttonEnabled("renewBtn", "btnintformbigdis");
				
				document.getElementById("EducationCessChk").disabled = true;
				document.getElementById("HigherEducationCessChk").disabled = true;
				document.getElementById("StateTaxChk").disabled = true;
				document.getElementById("SurchargeOnSTChk").disabled = true;
				document.getElementById("ServiceTaxChk").disabled = true;

				jQuery('#ebRateGrid >tbody >tr').each(function(i, tr) {
					getDomElementById("prefCode" + (i + 1)).disabled = true;
					getDomElementById("prefName" + (i + 1)).disabled = true;
					getDomElementById("prefDesc" + (i + 1)).disabled = true;
					getDomElementById("amount" + (i + 1)).disabled = true;

				});
				alert(ebConfigTO.successMessage);
				loadDefaultEBRates("", "onLoad","");				
			} else if (saveMode == RENEWED) {
				buttonDisabled("editBtn", "btnintform");
				buttonDisabled("renewBtn", "btnintform");

				for ( var i = 0; i < ebConfigTO.prefCodes.length; i++) {
					document.getElementById("prefCode" + (i + 1)).value = ebConfigTO.prefCodes[i];
					document.getElementById("ebPrefRateId" + (i + 1)).value = ebConfigTO.ebPrefRateId[i];
					document.getElementById("prefId" + (i + 1)).value = ebConfigTO.prefId[i];
					document.getElementById("prefId" + (i + 1)).readOnly = false;
					document.getElementById("prefCode" + (i + 1)).readOnly = false;
					document.getElementById("prefName" + (i + 1)).readOnly = false;
					document.getElementById("prefDesc" + (i + 1)).readOnly = false;
					document.getElementById("amount" + (i + 1)).readOnly = false;
				}
				alert(ebConfigTO.successMessage);
				loadDefaultEBRates("", "onLoad","");				
			}
		}

	}

}

function loadDefaultEBRates(stateId, value, action) {
	showProcessing();

	var action = document.getElementById("action").value;
	var stateId = document.getElementById("originState").value;
	if(isNull(stateId)){
		enableOrDisableTaxFields(true,false);
	}else{
		enableOrDisableTaxFields(false,true);
	}
	
	var ebRateConfigId = document.getElementById("ebRateConfigId").value;
	url = './emotionalBondRate.do?submitName=loadDefaultEBRates&stateId='
			+ stateId + "&action=" + action + "&ebRateConfigId="
			+ ebRateConfigId;

	jQuery.ajax({
		url : url,
		success : function(req) {
			printCallBackEBDetails(req, value, action);
		}
	});
}

function printCallBackEBDetails(ajaxResp, value, action) {
	var ebConfigTO = null;
	if (!isNull(ajaxResp)) {
		hideProcessing();
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			/*if (!isNull(action) && action == RENEWED) {
				alert("Rates Renewed Successfully");
			}*/
			ebConfigTO = responseText;

			if (ebConfigTO.cessTaxApplicable == 'Y') {
				document.getElementById("EducationCessChk").checked = true;
			}
			if (ebConfigTO.hcesstaxApplicable == 'Y') {
				document.getElementById("HigherEducationCessChk").checked = true;
			}
			if (ebConfigTO.stateTaxApplicable == 'Y') {
				document.getElementById("StateTaxChk").checked = true;
			}
			if (ebConfigTO.surchargeOnSTApplicable == 'Y') {
				document.getElementById("SurchargeOnSTChk").checked = true;
			}
			if (ebConfigTO.serviceTaxApplicable == 'Y') {
				document.getElementById("ServiceTaxChk").checked = true;
			}
			//if (value == "onLoad") {
				onLoadValue(responseText);
			//}
		}

	}
}

function onLoadValue(responseText) {
	var ebConfigTO = responseText;
	jQuery("#ebRateConfigId").val(ebConfigTO.ebRateConfigId);
	jQuery("#validFromDate").val(ebConfigTO.validFromDateStr);
	//jQuery("#originState").val(ebConfigTO.originState);
	jQuery("#rateStatus").val(ebConfigTO.status);
	jQuery("#curStateId").val(ebConfigTO.curStateId);

	var loopLength = ebConfigTO.preferenceTOs.length;
	if ($('#ebRateGrid').dataTable().fnGetData().length > 1) {
		$('#ebRateGrid').dataTable().fnClearTable();
		rowCount = 1;
		fnClickAddRow();
	}
	for ( var i = 0; i < loopLength; i++) {

		document.getElementById("prefId" + (i + 1)).value = ebConfigTO.preferenceTOs[i].preferenceDetailsTO.preferenceId;
		document.getElementById("prefCode" + (i + 1)).value = ebConfigTO.preferenceTOs[i].preferenceDetailsTO.preferenceCode;
		document.getElementById("prefName" + (i + 1)).value = ebConfigTO.preferenceTOs[i].preferenceDetailsTO.preferenceName;
		document.getElementById("prefDesc" + (i + 1)).value = ebConfigTO.preferenceTOs[i].preferenceDetailsTO.description;
		document.getElementById("applicabilitys" + (i + 1)).value = ebConfigTO.preferenceTOs[i].applicability;
		document.getElementById("amount" + (i + 1)).value = ebConfigTO.preferenceTOs[i].rate;
		document.getElementById("ebPrefRateId" + (i + 1)).value = ebConfigTO.preferenceTOs[i].ebRatePrefId;

		if (i < ((loopLength) - 1)) {
			addNewRow();
		}
	}

	var status = ebConfigTO.status;
	if (status == CREATED) {
		$("#validFromDt").show();
		buttonDisabled("editBtn", "btnintform");
		buttonDisabled("renewBtn", "btnintform");
		buttonDisabled("deactivateBtn", "btnintform");
		buttonEnabled("submitBtn", "btnintformbigdis");
		buttonEnabled("saveBtn", "btnintformbigdis");
		fnClickAddRow();
	}
	if (status == SUBMITED) {
		buttonEnabled("editBtn", "btnintformbigdis");
		if(validRenewDate()){
			buttonEnabled("renewBtn", "btnintformbigdis");
		}else{
			buttonDisabled("renewBtn", "btnintform");
		}
		buttonDisabled("saveBtn", "btnintform");
		buttonDisabled("submitBtn", "btnintform");
		buttonEnabled("deactivateBtn", "btnintformbigdis");
		$("#validFromDt").hide();
		document.getElementById("EducationCessChk").disabled = true;
		document.getElementById("HigherEducationCessChk").disabled = true;
		document.getElementById("StateTaxChk").disabled = true;
		document.getElementById("SurchargeOnSTChk").disabled = true;
		document.getElementById("ServiceTaxChk").disabled = true;

		for ( var j = 0; j < loopLength; j++) {
			document.getElementById("prefId" + (j + 1)).readOnly = true;
			document.getElementById("prefCode" + (j + 1)).readOnly = true;
			document.getElementById("prefName" + (j + 1)).readOnly = true;
			document.getElementById("prefDesc" + (j + 1)).readOnly = true;
			document.getElementById("amount" + (j + 1)).readOnly = true;
		}
		
	}
}
/**
 * To disable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintformbigdis");
}

/**
 * To enable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).addClass("btnintform");
}

function editEBRate() {
	var table = document.getElementById("ebRateGrid");
	var tableRowCount = table.rows.length;

	for ( var i = 1; i < tableRowCount; i++) {
		document.getElementById("amount" + (i)).readOnly = false;
	}
	editMode = 'Y';
	buttonEnabled("submitBtn", "btnintformbigdis");	
}

function loadDefaultEBRatesForRenew() {
	document.getElementById("action").value = RENEWED;
	jQuery("#isRenew").val("Y");
	jQuery("#currentEBRateConfigId").val(jQuery("#ebRateConfigId").val());
	loadDefaultEBRates("", "onLoad", "RENEWED");

}

function validRenewDate(){
	var fDate = getDomElementById("validFromDate").value;
	
	
	var tDate = new Date();
	
	var validfromDateDay = parseInt(fDate.substring(0, 2), 10);
	var validfromDateMon = parseInt(fDate.substring(3, 5), 10);
	var validfromDateYr = parseInt(fDate.substring(6, 10), 10);
	
	var fromDate = new Date(validfromDateYr, (validfromDateMon - 1), validfromDateDay);
	var toDate = new Date(tDate.getFullYear(), tDate.getMonth(), tDate.getDate());
	
	if(fromDate <= toDate){
		return true;
	}
	
	return false;
}

function validateFormat(obj){
	val = obj.value;
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