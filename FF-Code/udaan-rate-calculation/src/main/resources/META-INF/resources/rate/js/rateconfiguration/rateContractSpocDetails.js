var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
var spocType;
var empType;
var contactType;
function searchContractSpocDetails(spoc){
	spocType = spoc;
	contactType = document.getElementById("contactType"+spocType).value;
	var contractId = document.getElementById("rateContractId").value;
	if(!isNull(contactType) && !isNull(contractId)){
		var	url = "./rateContract.do?submitName=searchContractSpocDetails&contactType="+contactType+"&contractId="+contractId;
		ajaxCall1(url, "rateContractForm", searchSpocCallbackfun);
		showProcessing();
	}
	return true;
}
function searchSpocCallbackfun(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var contacts=responseText;
			var len = contacts.length;
			for(var i=0; i<len;i++){
				if(!isNull("contactName"+spocType+contacts[i].complaintType) && !isNull(contacts[i].contactName))
				getDomElementById("contactName"+spocType+contacts[i].complaintType).value = contacts[i].contactName;
				if(!isNull("contactNo"+spocType+contacts[i].complaintType) && !isNull(contacts[i].contactNo))
				getDomElementById("contactNo"+spocType+contacts[i].complaintType).value = contacts[i].contactNo;
				if(!isNull("mobile"+spocType+contacts[i].complaintType) && !isNull(contacts[i].mobile))
				getDomElementById("mobile"+spocType+contacts[i].complaintType).value = contacts[i].mobile;
				if(!isNull("fax"+spocType+contacts[i].complaintType) && !isNull(contacts[i].fax))
				getDomElementById("fax"+spocType+contacts[i].complaintType).value = contacts[i].fax;
				if(!isNull("email"+spocType+contacts[i].complaintType) && !isNull(contacts[i].email))
				getDomElementById("email"+spocType+contacts[i].complaintType).value = contacts[i].email;
				if(!isNull("contractSpocId"+spocType+contacts[i].complaintType) && !isNull(contacts[i].contractSpocId))
				getDomElementById("contractSpocId"+spocType+contacts[i].complaintType).value = contacts[i].contractSpocId;
				}
		}
	}
	}else{
		clearSpocGridfieldsData();
	}
	configureButton();
	hideProcessing();	
}



function saveSpocDetails(type){
	setContactType();
	if(!isNull(contactType) && checkSpocFieldsData()){
		allFieldsEditable();
		var	url = "./rateContract.do?submitName=saveContractSpocDetails";
		ajaxCall1(url, "rateContractForm", saveSpocCallbackfun);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function setContactType(){
	document.getElementById("contactType").value = spocType;
}

function saveSpocCallbackfun(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			jQuery.unblockUI();
			alert(error);
		} else {
			if (searchContractSpocDetails(spocType)) {
				if (responseText != null && success != null) {
					jQuery.unblockUI();
					alert(success);
					if(spocType == "F"){
						if(getDomElementById("rateQuotationType").value == "E"){
							enableTabs(7);
						}else{
							enableTabs(8);
						}
					}
				}
			}

		}
	}else{
		jQuery.unblockUI();
	}
}
function cancelSpocDetails(){
	searchContractSpocDetails(spocType);
}

function checkSpocFieldsData(){
	
	var rowCount = getDomElementById("rowCount"+spocType).value;
	var check = false;
	for(var i=1;i<=rowCount;i++){
		stdCode = getDomElementById("rowNo"+spocType+i).value;
		contactName = getDomElementById("contactName"+spocType+stdCode);
		contactNo = getDomElementById("contactNo"+spocType+stdCode);
		mobile = getDomElementById("mobile"+spocType+stdCode);
		fax = getDomElementById("fax"+spocType+stdCode);
		email = getDomElementById("email"+spocType+stdCode);

		if(!isNull(contactName.value) 
			&& !isNull(contactNo.value)
			&& !isNull(mobile.value)
			&& !isNull(fax.value)
			&& !isNull(email.value)){
			check = true;
		}
	}
	
	if(!check){
		alert(" For atleast one row, please enter details of all fields.");
		return false;
	}
	return true;
}

function configureButton(){
	var status = getDomElementById("contractStatus").value;
	var empType = getDomElementById("empType").value;
	var userType = getDomElementById("userType").value;
	if(status == 'A'){
		if(!isNull(userType) && userType == "S"){
			superUserStatus();
		}else{
			activeStatus();
		}
	}else if(status == 'I'){
		inActiveStatus();
	}else if(status == 'S'){
		submittedStatus();
	}else if(status == 'C'){
		createdStatus();
	}else if(status == 'B'){
		blockedStatus();
	}
		
}
function activeStatus(){
		btndisabled("saveDtlsBtn"+spocType,"","");
		btndisabled("cancelDtlsBtn"+spocType,"","");
		btndisabled("submitContractBtn","","");
		rCon = getDomElementById("rnewContract").value;
		if(checkRenewDates() && (rCon != "true" || rCon == true) )
			btnEnabled("renewBtn","","");
		else
			btndisabled("renewBtn","","");
		btnEnabled("printContractBtn","","");
		if(!isNull(empType) && empType == "C")
			btndisabled("editBtn"+spocType,"","");
		else
			btnEnabled("editBtn"+spocType,"","");
		fieldsDisable();
}
function inActiveStatus(){
	btndisabled("saveDtlsBtn"+spocType,"","");
	btndisabled("cancelDtlsBtn"+spocType,"","");
	btndisabled("editBtn"+spocType,"","");
	btndisabled("submitContractBtn","","");
	btndisabled("renewBtn","","");
	btnEnabled("printContractBtn","","");
	fieldsDisable();
}
function submittedStatus(){
	btndisabled("saveDtlsBtn"+spocType,"","");
	btndisabled("cancelDtlsBtn"+spocType,"","");
	btndisabled("submitContractBtn","","");
	btndisabled("renewBtn","","");
	btnEnabled("printContractBtn","","");
	if(isNull(empType) || empType != "C")
		btnEnabled("editBtn"+spocType,"","");
	fieldsDisable();
}
function createdStatus(){
	btnEnabled("saveDtlsBtn"+spocType,"","");
	btnEnabled("cancelDtlsBtn"+spocType,"","");
	btndisabled("editBtn"+spocType,"","");
	btnEnabled("submitContractBtn","","");
	btndisabled("renewBtn","","");
	btndisabled("printContractBtn","","");
	fieldsEditable();
}
function blockedStatus(){
	btndisabled("saveDtlsBtn"+spocType,"","");
	btndisabled("cancelDtlsBtn"+spocType,"","");
	btndisabled("editBtn"+spocType,"","");
	btndisabled("submitContractBtn","","");
	btndisabled("renewBtn","","");
	btnEnabled("printContractBtn","","");
	fieldsDisable();
}
function superUserStatus(){
	btnEnabled("saveDtlsBtn"+spocType,"","");
	btnEnabled("cancelDtlsBtn"+spocType,"","");
	btndisabled("editBtn"+spocType,"","");
	btnEnabled("submitContractBtn","","");
	btndisabled("renewBtn","","");
	btnEnabled("printContractBtn","","");
	fieldsEditable();
}
function btnEnabled(btnName,btnForm,style){
	buttonEnabled(btnName,"btnintform");
	jQuery("#" +btnName).removeClass("btnintformbigdis");
}
function btndisabled(btnName,btnForm,style){
	buttonDisabled(btnName,"btnintform");
	jQuery("#" +btnName).addClass("btnintformbigdis");
}

function editSpocDetails(spoc){
	fieldsEditable();
	btnEnabled("saveDtlsBtn"+spocType,"","");
	btnEnabled("cancelDtlsBtn"+spocType,"","");
}

function fieldsEditable(){
	var rowCount = getDomElementById("rowCount"+spocType).value;
	for(var i=1;i<=rowCount;i++){
	stdCode = getDomElementById("rowNo"+spocType+i).value;
	getDomElementById("contactName"+spocType+stdCode).disabled = false;
	getDomElementById("contactNo"+spocType+stdCode).disabled = false;
	getDomElementById("mobile"+spocType+stdCode).disabled = false;
	getDomElementById("fax"+spocType+stdCode).disabled = false;
	getDomElementById("email"+spocType+stdCode).disabled = false;
	}
}

function fieldsDisable(){
	var rowCount = getDomElementById("rowCount"+spocType).value;
	for(var i=1;i<=rowCount;i++){
		stdCode = getDomElementById("rowNo"+spocType+i).value;
		getDomElementById("contactName"+spocType+stdCode).disabled = true;
		getDomElementById("contactNo"+spocType+stdCode).disabled = true;
		getDomElementById("mobile"+spocType+stdCode).disabled = true;
		getDomElementById("fax"+spocType+stdCode).disabled = true;
		getDomElementById("email"+spocType+stdCode).disabled = true;
		}
}

function printContract(){
	var contractNo=getDomElementById("rateContractNo").value;
	var url = getDomElementById("contractPrintUrl").value+'pages/reportviewer/contractReport.jsp?'
		 + "ContractNo="+contractNo ;
	window.open(url, "_blank");
	
}

function allFieldsEditable(){
	var rowCount = getDomElementById("rowCount"+spocType).value;
	for(var i=1;i<=rowCount;i++){
	stdCode = getDomElementById("rowNo"+spocType+i).value;
	getDomElementById("contactNameF"+stdCode).disabled = false;
	getDomElementById("contactNameC"+stdCode).disabled = false;
	getDomElementById("contactNoF"+stdCode).disabled = false;
	getDomElementById("contactNoC"+stdCode).disabled = false;
	getDomElementById("mobileF"+stdCode).disabled = false;
	getDomElementById("mobileC"+stdCode).disabled = false;
	getDomElementById("faxF"+stdCode).disabled = false;
	getDomElementById("faxC"+stdCode).disabled = false;
	getDomElementById("emailF"+stdCode).disabled = false;
	getDomElementById("emailC"+stdCode).disabled = false;
	}
}

function clearSpocGridfieldsData(){
	var rowCount = getDomElementById("rowCount"+spocType).value;
	for(var i=1;i<=rowCount;i++){
		stdCode = getDomElementById("rowNo"+spocType+i).value;
		getDomElementById("contactName"+spocType+stdCode).value = "";
		getDomElementById("contactNo"+spocType+stdCode).value = "";
		getDomElementById("mobile"+spocType+stdCode).value = "";
		getDomElementById("fax"+spocType+stdCode).value = "";
		getDomElementById("email"+spocType+stdCode).value = "";
	}
	stdCode = getDomElementById("rowNo"+spocType+5).value;
	getDomElementById("contactName"+spocType+stdCode).value = "corporate office";
	getDomElementById("contactNo"+spocType+stdCode).value = "39576666";
	getDomElementById("mobile"+spocType+stdCode).value = "9839576666";
	getDomElementById("fax"+spocType+stdCode).value = "6666";
	getDomElementById("email"+spocType+stdCode).value = "udaan.customer@firstflight.net";
}