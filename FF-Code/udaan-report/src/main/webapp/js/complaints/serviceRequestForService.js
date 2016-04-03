$(document).ready(function() {     
	defaultChanges();
} ); 

var selectOption = "--Select--";
function defaultChanges(){
	$("#searchType").val('SREQ');
	disableFieldById("originCity");
	disableFieldById("product");
	disableFieldById("pincode");
	disableFieldById("weight");
	disableFieldById("consgTypes");
	disableFieldById("employeeId");
	disableFieldById("empEmail");
	disableFieldById("empMobile");
	
}

function editServiceRequest(){
	alert("editServiceRequest");
}

function saveOrCloseServiceRequest(){
	
	/*if(!mandatoryFields()){
		return;
	}*/
	
	flag=confirm("Do you want to Save the Service Request Details");
	if(!flag){
		return;
	}
	
		var url="./serviceRequestForService.do?submitName=saveServiceReqDetails";
		document.serviceRequestForServiceForm.action=url;
		document.serviceRequestForServiceForm.submit();

}




function clearPage(){
	if(confirm("Do you want to clear the page?")){
		refershPage();
	}
}

function refershPage(){
	var url = "./serviceRequestForService.do?submitName=preparePage";
	window.location = url;
}


function searchServiceReq(Obj){
	Obj.value = $.trim(Obj.value);
	var searchType = $("#searchType").val();
	if(isNull(Obj) && isNull(searchType)){
		return;
	}
	var url = "./serviceRequestForService.do?submitName=searchServiceRequest&serviceReqNo="+Obj.value+"&serviceType="+searchType;
	$.ajax({
		url: url,
		success: function(data){searchPopulate(data, Obj);}
	});
	
}

function searchPopulate(data, Obj){
	var serviceReq = eval('(' + data + ')');
	if(!isNull(serviceReq)){
		populateSearchValues(serviceReq);
	}else{
		alert("No data found");
	}
}
	
function populateSearchValues(serviceReq){
	$("#callerName").val(serviceReq.callerName);
	$("#callerPhone").val(serviceReq.callerPhone);
	$("#callerEmail").val(serviceReq.callerEmail);
	
	
	$("#originCity").val(serviceReq.cityTO.cityCode);
	$("#pincode").val(serviceReq.pincodeTO.pincode);
	$("#product").val(serviceReq.productTO.productId);
	
	/*$("#consgTypes").val(serviceReq.consgTypes);
	$("#consgTypes").val(serviceReq.consgTypes);
	$("#consgTypes").val(serviceReq.consgTypes);*/
	//paperwork
	$("#consgTypes").val(serviceReq.consgTypes);
	//$("#paperwork").val(serviceReq.paperwork);
	$("#weight").val(serviceReq.weight);
	
	/*$("#employeeId").val(serviceReq.weight);*/
	$("#empEmailId").val(serviceReq.empEmailId);
	$("#empPhone").val(serviceReq.empPhone);
	$("#result").val(serviceReq.result);
	$("#status").val(serviceReq.status);
	$("#remark").val(serviceReq.remark);
	
	
}	



function validateMobile(obj)
{
	var val = obj.value;
	if(val.length >10||val.length <10)
	{
	alert("Phone no. should be of length 10");
	obj.value="";
	obj.focus();
	return false;
	}
	return true;	
}

function validatePincode(obj){
	var val = obj.value;
	if(val.length >6||val.length <6)
	{
	alert("Pincode should be of length 6");
	obj.value="";
	obj.focus();
	return false;
	}
	return true;	
}
function validateEmail()
{
	//var email = document.getElementById("callerEmail").value;
	var email = $("#callerEmail").val();
	var atpos=email.indexOf("@");
	var dotpos=email.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length)
	  {
	  alert("Please enter a valid e-mail address");
	  $("#callerEmail").val("");
	  $("#callerEmail" ).focus();
	  return false;
	  }
	return true;	
}


function getDestinationCity(OrgCityObj){
	OrgCityObj.value = $.trim(OrgCityObj.value);
	
	if(isNull(OrgCityObj)){
		return;
	}
	var url = "./serviceRequestForService.do?submitName=getCity&cityCode="+OrgCityObj.value;

	$.ajax({
		url: url,
		success: function(data){populateDestinationCity(data, OrgCityObj);}
	});
}


function populateDestinationCity(data, OrgCityObj){
	var cityTO = eval('(' + data + ')');
	if(!isNull(cityTO)){
		$("#originDtls").val(cityTO.cityId + "~" + cityTO.cityCode + "~" + cityTO.cityName);
		$("#originCityId").val(cityTO.cityId);
		$("#originCity").val(cityTO.cityCode);
	}else{
		alert("Invalid City Code");
		$("#originCity").val("");
		$("#originCity" ).focus();
		return;
	}
}


function clearFocusAlertMsg(obj, msg){
	obj.val("");
	obj.focus();
	alert(msg);
}



function serviceRelatedEnableORdisable(){
	var serviceCheckHidden = $("#transactionServiceCheck").val();
	var branchPincodeHidden = $("#transactionBranchPincode").val();
	var tariffEnquiryHidden = $("#transactionTariffEnquiry").val();
	var generalInfoHidden = $("#transactionGeneralInfo").val();
	var emotionalBondHidden = $("#transactionEmotionalBond").val();
	var supportingPapersHidden = $("#transactionSupportingPapers").val();  // No BR found for Supporting Papers
	var leadCallHidden = $("#transactionLeadCall").val();
	var pickUpCallHidden = $("#transactionPickUpCall").val();
	var paperworkHidden = $("#transactionPaperwork").val();
	
	var currentServiceReqValue=$("#serviceRelated").val();
		
	if( currentServiceReqValue==serviceCheckHidden || branchPincodeHidden == currentServiceReqValue ){
		// enable origin + product + pincode
		enableFieldById("originCity");
		enableFieldById("product");
		enableFieldById("pincode");
	}else if(currentServiceReqValue==tariffEnquiryHidden){
		// tariff --> origin City+ Product + Pincode + weight + Dox/Non Dox
		enableFieldById("originCity");
		enableFieldById("product");
		enableFieldById("pincode");
		enableFieldById("consgTypes");
	}else if(currentServiceReqValue==generalInfoHidden){
		// General Info --> only Status is enable
		
	}else if(currentServiceReqValue==emotionalBondHidden){
		// EmotionalBond -- > enable origin + product + pincode
		enableFieldById("originCity");
		enableFieldById("product");
		enableFieldById("pincode");
	}else if(currentServiceReqValue==paperworkHidden){
		// Paperwork--> Product + Pincode 
		/*enableFieldById("originCity");*/
		enableFieldById("product");
		enableFieldById("pincode");
	}else if(currentServiceReqValue==leadCallHidden){
		// lead Call -->  emplyee enable 
		enableFieldById("employeeId");
		enableFieldById("empEmail");
		enableFieldById("empMobile");
		
	}else if(currentServiceReqValue==pickUpCallHidden){
		// Pick Up Call--> emplyee enable 
		enableFieldById("employeeId");
		enableFieldById("empEmail");
		enableFieldById("empMobile");
	}
	
		
}

// Disable Fields by IDs 
function disableFieldById(fieldId){
	document.getElementById(fieldId).disabled = true;
}

//Enable Fields by IDs 
function enableFieldById(fieldId){
	document.getElementById(fieldId).disabled = false;
}


/*function getEmployeeDtls(){
	
	var url = "./serviceRequestForService.do?submitName=getEmployeeDtls";

	$.ajax({
		url: url,
		success: function(data){populateEmployeeDtls(data);}
	});
}
function populateEmployeeDtls(data){
	var employeeTO = eval('(' + data + ')');
}*/


function isValidPincode(pincodeObj){
	pincodeObj.value = $.trim(pincodeObj.value);
	
	if(isNull(pincodeObj)){
		return;
	}
	var url = "./serviceRequestForService.do?submitName=isValidPincode&pincode="+pincodeObj.value;

	$.ajax({
		url: url,
		success: function(data){populatePincodeDtls(data, pincodeObj);}
	});
}


function populatePincodeDtls(data, pincodeObj){
	var pincodeTO = eval('(' + data + ')');
	if(!isNull(pincodeTO)){
		$("#pincodeId").val(pincodeTO.pincodeId );
	}else{
		clearFocusAlertMsg($("#pincode"),"Invalid Pincode Code" );
		return;
	}
}

function setProduct(){
	var productNameArray = $("#product").val().split("~");
	
	$("#productId").val(productNameArray[0]);
	//$("#product").val(reasonNameArray[2]);
	
	if(isNull(productNameArray)){
		$("#productId").val("");
	}
}

function setCustType(){
	var consgTypesArray = $("#consgTypes").val().split("~");
	
	$("#consgTypeId").val(consgTypesArray[0]);
	//$("#product").val(reasonNameArray[2]);
	
	if(isNull(consgTypesArray)){
		$("#consgTypeId").val("");
	}
}

function setEmployee(){
	var employeeArray = $("#employeeId").val().split("~");
	if(!isNull(employeeArray)){
		$("#empMobile").val(employeeArray[1]);
		$("#empEmail").val(employeeArray[2]);	
	}
	if(isNull(employeeArray)){
		$("#empMobile").val("");
		$("#empEmail").val("");
		
	}
}



function getPaperWork(){
	var pincode = $("#pincode").val();
	var docType = $("#consgTypes").val();
	var url = "./serviceRequestForService.do?submitName=getPaperworkDtls&pincode="+pincode+"&docType="+docType;

	$.ajax({
		url: url,
		success: function(data){populatePaperworkDtls(data);}
	});
}
function populatePaperworkDtls(data){
	var cnPaperWorksTOs = eval('(' + data + ')');
	if(!isNull(cnPaperWorksTOs)){
		clearDropDownList("paperwork");
		for(var i=0;i<cnPaperWorksTOs.length;i++) {
			//$("#paperwork").val(cnPaperWorksTOs.cnPaperWorkName);
			addOptionTODropDown("paperwork", cnPaperWorksTOs[i].cnPaperWorkName,cnPaperWorksTOs[i].cnPaperWorkId);
		}
	}


	/*var content = document.getElementById('paperwork');
	content.innerHTML="";
	defOption = document.createElement("option");
	defOption.value="";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if(!isNull(data)){
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cnPaperWorkId;
			option.appendChild(document.createTextNode(this.cnPaperWorkName));
			content.appendChild(option);
		});
	}*/
	
}
function addOptionTODropDown(selectId, label, value){
	var myselect=document.getElementById(selectId);
	try{
		 myselect.add(new Option(label, value), null); //add new option to end of "myselect"
		 //myselect.add(new Option(label, value), myselect.options[0]); //add new option to beginning of "myselect"
	}
	catch(e){ //in IE, try the below version instead of add()
		 myselect.add(new Option(label, value)); //add new option to end of "myselect"
		 //myselect.add(new Option(label, value), 0); //add new option to beginning of "myselect"
	}
}
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;	
	addOptionTODropDown(selectId, selectOption, "");
}


function mandatoryFields() {
	  	var msg = "Please provide : ";
		var isValid = true;
		var focusObj = callerName;
	    var callerName = document.getElementById("callerName");
	    var callerPhone = document.getElementById("callerPhone");
	    var callerEmail = document.getElementById("callerEmail");
	   /* var destinationRegionId = document.getElementById("destinationRegionId");
	    var destCity = document.getElementById("destCity");
	    var destOfficeType = document.getElementById("destOfficeType");
	    var destOffice = document.getElementById("destOffice");
	    var bagLockNo = document.getElementById("bagLockNo");*/

	    if(isNull(callerName.value)){
	         /* alert("Please enter the BPL Number.");
	          setTimeout(function() {
	                document.getElementById("manifestNo").focus();
	          }, 10);
	          isValid = false;
	          return isValid;*/
	    	//errorsData = errorsData+"Please enter the BPL Number."+"\n";
	    	if(isValid)	focusObj = callerName;
			msg = msg + ((!isValid)?", ":"") + "Caller Name";
			isValid=false;
	    }

	    if (isNull(callerPhone.value)) {
	         /* alert("Please select the Destination Office");
	          setTimeout(function() {
	                document.getElementById("destOffice").focus();
	          }, 10);
	          isValid = false;
	          return isValid;*/
	    	/*errorsData = errorsData+"Please select the Destination Office."+"\n";*/
	    	// missingFields=missingFields+"Destination Office,";
	    	if(isValid)	focusObj = callerPhone;
			msg = msg + ((!isValid)?", ":"") + "Caller Phone";
			isValid=false;
	    }
	    if(isNull(callerEmail.value)){  
	    	if(isValid)	focusObj = callerEmail;
			msg = msg + ((!isValid)?", ":"") + "Caller Email";
			isValid=false;
	    }
	 
	    if(!isValid){
			alert(msg);
			setTimeout(function(){focusObj.focus();},10);
		}
		return isValid;
	}




