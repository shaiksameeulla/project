var offCodeArr = new Array();
var ERROR_FLAG="ERROR";

/**
 * To execute on load of page
 */
function validateExpStartup(){
	var expId = getDomElementById("expenseId").value;
	if(isNull(expId)){
		getAllOffices(getDomElementById("stationId"));
	} 
}

/**
 * To get All Offices by city
 */
function getAllOffices(domElement) {
	if(!isNull(domElement.value)) {
		var cityId = domElement.value;
		var url = "./validateExpense.do?submitName=getAllOfficesByCity&cityId="+cityId;
		//ajaxCallWithoutForm(url, populateAllOffices);
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateAllOffices(req);
			}
		});
	} else {
		clearDropDownList("officeId");
	}
}

/**
 * @param data
 */
function populateAllOffices(ajaxResp) {
	if(!isNull(ajaxResp)){
		var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}else{
			data = eval('('+ ajaxResp + ')');
			var stationId = document.getElementById("stationId").value;
			var content = document.getElementById("officeId");
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("---Select---"));
			content.appendChild(defOption);
			if(!isNull(stationId)){
				$.each(data, function(index, value) {
					var option = document.createElement("option");
					option.value = this.officeId;
					option.appendChild(document.createTextNode(this.officeName.toUpperCase()));
					content.appendChild(option);
					offCodeArr[this.officeId]=this.officeCode.toUpperCase();
				});
			}
			jQuery("#officeId").val(officeId);
		}
	} else {
		clearDropDownList("officeId");
	}
	hideProcessing();
}

/**
 * To search validate expense details
 */
function searchValidateExpDtls(){
	if(checkMandatoryForValidateExpSearch()){
		var url = "./validateExpense.do?submitName=searchValidateExpenseDtls";
		document.validateExpenseForm.action = url;
		document.validateExpenseForm.submit();
	}
}

/**
 * To check mandatory fields for search validate expense details
 */
function checkMandatoryForValidateExpSearch(){
	var fromDt = getDomElementById("fromDate");
	var toDt = getDomElementById("toDate");
	var station = getDomElementById("stationId");
	var office = getDomElementById("officeId");
	if(isNull(fromDt.value)){
		alert("Please Select From Date");
		setTimeout(function(){fromDt.focus();}, 10);
		return false;
	}
	if(isNull(toDt.value)){
		alert("Please Select To Date");
		setTimeout(function(){toDt.focus();}, 10);
		return false;
	}
	if(isNull(station.value)){
		alert("Please Select Station");
		setTimeout(function(){station.focus();}, 10);
		return false;
	}
	if(isNull(office.value)){
		alert("Please Select Office");
		setTimeout(function(){office.focus();}, 10);
		return false;
	}
	return true;
}

/**
 * To open validate pop-up
 * 
 * @param rowId
 */
function validatePopup(rowId){
	var txNo = getDomElementById("rowTxNumber"+rowId).value;
	var expFor = getDomElementById("rowExpenseFor"+rowId).value;
	var officeId = getDomElementById("rowExpenseOfficeId"+rowId).value;
	var url = "expenseEntry.do?submitName=searchForValidateExpense"
				+ "&txNumber=" + txNo
				+ "&expenseFor=" + expFor
				+ "&officeId=" + officeId;
	if(!isNull(url)){
		window.open(url, 'Validate Expense', 'toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
	}
}

/**
 * validate the TxNo. format i.e. Office Code + Tx. Code + 6 digit + "-1" (MUMB EX 123456 / MUMB EX 123456-1)
 * 
 * @param domElement
 * @returns {Boolean}
 */
function validateTxNoForValidate(domElement){
	var domValue = domElement.value.trim();
	if(!isNull(domValue)){
		if(domValue.length==12 || domValue.length==14){
			var selectedOffCode = getSelectedOfficeCode();
			var officeCodePattern=/^[A-Z0-9]{0,4}$/;
			var txCode="EX";
			var numpattern=/^[0-9]{6,14}$/;
			if(officeCodePattern.test(domValue.toUpperCase().substring(0,4))
					&& domValue.substring(4,6).toUpperCase()==txCode.toUpperCase()
						&& (numpattern.test(domValue.substring(6)) 
							|| (numpattern.test(domValue.substring(6,12)) && domValue.substring(12)=="-1"))){
				if(domValue.toUpperCase().substring(0,4)==selectedOffCode){
					return true;
				} else {
					alert("Transaction number does not belong to selected office:"+selectedOffCode);
					domElement.value = "";
					setTimeout(function(){domElement.focus();}, 10);
				}
			} else {
				alert("Transaction number format is not correct");
				domElement.value = "";
				setTimeout(function(){domElement.focus();}, 10);
			}
		} else {
			alert("Transaction number should be 12 or 14 characters");
			domElement.value = "";
			setTimeout(function(){domElement.focus();}, 10);
		}
	}
}

/**
 * To get select office's office code
 * 
 * @returns Select Office's Code
 */
function getSelectedOfficeCode(){
	return offCodeArr[getDomElementById("officeId").value];
}