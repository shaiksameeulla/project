//Global Variables
var FORM_ID="serviceRequestForServiceForm";
var CURRENT_DOM=null;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
var IS_REQUEST_COMPLETED=true;
$(document).ready(function() {  
	isValidToReload();
	disableOnLoad();

} ); 
$( window ).load(function() {
	if(isNewServiceReq()){
		$("#serviceType").trigger("change");
	}else{
		disableGlobalButton('trackBtn');
		 $("#tabs").tabs({disabled: [0,1,2]});
		 $("#tabs").tabs("option", "active", 3);
		 var consignmentDom=getDomElementById('consignment');
			var consignmentStatusDom=getDomElementById('consignmentStatus');
			var consignmentErrorDom=getDomElementById('errorMsg');
			//consignmentDom.innerHTML="Consignment tracking information for <strong>CN No. <label id=cnNum></label></strong>";
			//consignmentStatusDom.innerHTML="Status :<strong><label id=cnStatus ></label></strong>";
			//consignmentErrorDom.innerHTML="";
			
			
			consignmentDom.innerHTML="";
			consignmentStatusDom.innerHTML="";
			consignmentErrorDom.innerHTML="";
	}
	});


function editServiceRequest(){
	alert("editServiceRequest");
}

function saveOrCloseServiceRequest(){

	/*if(!IS_REQUEST_COMPLETED){
		alert("Invalid Request");
		false;
	}*/

	if(validateServiceReqForSave() && confirm("Do you want to Save the Service Request Details ?")){
		enableAll();
		var url="./serviceRequestForService.do?submitName=saveServiceReqDetails";
		document.serviceRequestForServiceForm.action=url;
		document.serviceRequestForServiceForm.submit();
	}

}

function searchServiceRequestDtls(){
	if(validateForServiceRequestForSearch()){
		var url="./serviceRequestForService.do?submitName=searchServiceReqDetails";
		document.serviceRequestForServiceForm.action=url;
		document.serviceRequestForServiceForm.submit();
	}

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



//Disable Fields by IDs 
function disableFieldById(fieldId){
	var domElment = document.getElementById(fieldId);
	if(domElment!=null){
		domElment.disabled = true;
	}
}

//Enable Fields by IDs 
function enableFieldById(fieldId){
	document.getElementById(fieldId).disabled = false;
}




function enableLinkedReferenceNo(domelement){
	var mandatorySrvRefNumber=getDomElementById('mandatorySrvRefNumber');
	var linkedReferenceNo= getDomElementById('linkedServiceReqNo');
	if(domelement.checked){
		linkedReferenceNo.value="";
		linkedReferenceNo.readOnly=false;
		mandatorySrvRefNumber.style.display="inline";
	}else{
		linkedReferenceNo.value="";
		linkedReferenceNo.setAttribute("readOnly",true);
		linkedReferenceNo.setAttribute("tabindex","-1");
		mandatorySrvRefNumber.style.display="none";
	}
}

function populatePincodesByCity(domElemnt){
	CURRENT_DOM=domElemnt;
	createEmptyDropDown('pincode');
	var pincodeDom=getDomElementById('pincode');
	if(!isNull(domElemnt.value) && !pincodeDom.readOnly){
		var url='serviceRequestForService.do?submitName=ajaxActionGetPincodeDtlsByCityDetails&cityId='+domElemnt.value;
		ajaxJquery(url,FORM_ID,ajxRespTOPopulatePincodesByCity);
	}

}

/**
 * ajxRespTOPopulatePincodesByCity : For Pincode DropDown
 * @param rspObject
 * @returns {Boolean}
 */
function ajxRespTOPopulatePincodesByCity(rspObject){
	var cityDropDownValues=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
			cityDropDownValues = rspObject;
			createDropDown('pincode',cityDropDownValues);
		}else{
			CURRENT_DOM.value="";
			createEmptyDropDown('pincode');
			CURRENT_DOM.focus();
		}
	}else{
		alert(getSelectedDropDownTextByDOM(CURRENT_DOM)+ " details does not exist");
		CURRENT_DOM.value="";
		createEmptyDropDown('pincode');
		CURRENT_DOM.focus();
	}
	CURRENT_DOM=null;

}

function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		responseText = jsonJqueryParser(ObjeResp);
	}catch(e){

	}
	if(!isNull(responseText)){
		var error = responseText[ERROR_FLAG];
		if(!isNull(error)){
			alert(error);
			return true;
		}
	}
	return false;
}





function getRoleNameByServiceType(){
	var srvType=getDomElementById('serviceType').value;
	var roleName=getSalesCoordinator();
	//alert("roleName:"+roleName);
	if(!isNull(srvType)){
		if(srvType==getServiceRequestTypeForConsg() || srvType==getServiceRequestTypeForBookingRef()){
			roleName=getBackLineExecutive();
		}
	}
	return roleName;
}

function populateEmployeesByServiceType(domElemnt){
	CURRENT_DOM=domElemnt;
	createEmptyDropDown('employeeId');
	clearEmployeeInfo();

	if(!isNull(domElemnt.value)){
		var roleName=getRoleNameByServiceType();
		var url='serviceRequestForService.do?submitName=ajaxActionGetEmployeeDtslByRole&roleName='+roleName+"&officeId="+getLoggedInOfficeId();
		ajaxJquery(url,FORM_ID,ajxRespTOPopulateEmployeesByServiceType);
	}

}

function clearEmployeeInfo(){
	$("#empEmailId").val("");
	$("#empPhone").val("");
}
/**
 * ajxRespTOPopulatePincodesByCity : For Pincode DropDown
 * @param rspObject
 * @returns {Boolean}
 */
function ajxRespTOPopulateEmployeesByServiceType(rspObject){
	var employeeDropDown=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
			employeeDropDown = rspObject;
			createDropDown('employeeId',employeeDropDown);
		}else{
			CURRENT_DOM.value="";
			createEmptyDropDown('employeeId');
			CURRENT_DOM.focus();
		}
	}else{
		/*var roleName=getRoleNameByServiceType();
		if(roleName==getSalesCoordinator()){
			roleName=" Sales Coordinator ";
		}else{
			roleName=" Back line Executive ";
		}
		alert( roleName+" details does not exist");*/
		//CURRENT_DOM.value="";
		createEmptyDropDown('employeeId');
		//CURRENT_DOM.focus();
	}
	CURRENT_DOM=null;

}

function populateEmpEmailIdAndMobile(domElmnet){
	CURRENT_DOM=domElmnet;
	clearEmployeeInfo();
	if(!isNull(domElmnet.value)){
		var url='serviceRequestForService.do?submitName=ajaxActionGetEmpToDtslByEmpId&roleName='+getRoleNameByServiceType()+"&officeId="+getLoggedInOfficeId()+"&empId="+domElmnet.value;
		ajaxJquery(url,FORM_ID,ajxRespTOpopulateEmpEmailIdAndMobile);
	}else{
		CURRENT_DOM=null;
	}
}

function ajxRespTOpopulateEmpEmailIdAndMobile(rspObject){
	if(!isNull(rspObject)){
		var responseText =jsonJqueryParser(rspObject); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			$("#empEmailId").val('');
			return false;
		}
		$("#empEmailId").val(responseText.emailId);
		$("#empPhone").val(responseText.empPhone);

	}else{

		$("#empEmailId").val('');
	}
	CURRENT_DOM=null;

}
function createNewRequest(){
	clearValues();

}
function getLoggedInOfficeId(){
	return getDomElementById('loginOfficeId').value;
}


function clearValues(){
	$("#callerName").val("");
	$("#callerPhone").val("");
	$("#callerEmail").val("");


	$("#originCity").val("");
	$("#pincode").val("");
	$("#product").val("");
	$("#serviceRelated").val("");
	$("#complaintCategory").val("");



	//paperwork
	$("#consgTypes").val("");
	//$("#weight").val("");
	$("#weightKgs").val("");
	$("#weightGrm").val("");


	$("#employeeId").val("");
	$("#empEmailId").val("");
	$("#empPhone").val("");
	$("#result").val("");
	$("#status").val("");
	$("#remark").val("");

	$("#referenceNo").val("");
	$("#serviceType").val("");
	$("#linkedReferenceNo").val("");
	$("#isLinkedWith").val("");
	$("#serviceRelatedId").val("");

	$('input:checkbox[name=isLinkedWith]').attr('checked',false);
}

function validateForServiceRequestForSearch(){
	var srvTypeDom= getDomElementById('serviceType');
	if(isNull(srvTypeDom.value)){
		alert("Please select Service type");
		setFocusOnElmentByDom(srvTypeDom);
		return false;
	}

	var numberDom =getDomElementById('number');
	if(isNull(numberDom.value)){
		alert("Please provide "+getSelectedDropDownTextByDOM(srvTypeDom));
		setFocusOnElmentByDom(numberDom);
		return false;
	}

	return true;
}

function disableOnLoad(){
	var serviceRltdDom =getDomElementById('serviceRelated');
	var queryType=getSelectedValue(serviceRltdDom.value);
	var labelOriginName=getDomElementById('originName');
	//alert("queryType"+queryType);

	if(queryType==getServiceRelatedQueryTypeLeadcall() || queryType==getServiceRelatedQueryTypePickupcall()){
		labelOriginName.innerHTML="Origin Branch";
	}

	var isNewComplnt=false;
	disableGlobalButton('Edit');
	if(getComplaintStatus()==getComplaintStatusResolved()){
		disableAll();
		disableGlobalButton('save');
		enableGlobalButton('cancelBtn');
		return;
	}
	//var searchTypeDom=getDomElementById('searchType');
	if(isNewServiceReq()){
		isNewComplnt=true;
		//	$("#serviceType").val(getServiceRequestTypeForService());
		$("#sourceOfQuery").val(getComplaintSourceOfQueryPhone());
		disableGlobalButton('Edit');
		$("#serviceType").val(getServiceRequestTypeForConsg());
		setServiceRequestStatusAsResolved();
		setFocusOnElmentById('number');
	}else{
		disableGlobalButton('save');
		makeReadOnlyById('callerName');
		makeReadOnlyById('callerPhone');
		makeReadOnlyById('callerEmail');

		makeDisableOnlyById('custType');
		makeDisableOnlyById('complaintCategory');
		makeDisableOnlyById('serviceRelated');
		makeDisableOnlyById('isLinkedWith');

		makeDisableOnlyById('originCity');
		makeDisableOnlyById('product');
		makeDisableOnlyById('pincode');

		makeDisableOnlyById('consgTypes');
		makeDisableOnlyById('searchType');
		//makeDisableOnlyById('employeeId');
		makeDisableOnlyById('serviceType');
		makeDisableOnlyById('smsToConsignor');
		makeDisableOnlyById('smsToConsignee');
		makeDisableOnlyById('emailToCaller');
		makeDisableOnlyById('sourceOfQuery');
		makeReadOnlyById('number');

		if(getComplaintStatus()!=getComplaintStatusResolved()){
			enableGlobalButton('Edit');
			//var statusDom=getDomElementById('status');
			var remarksDom=getDomElementById('remark');
			setFocusOnElmentById('status');
			remarksDom.setAttribute("onkeydown","return enterKeyNav('Edit' ,event.keyCode)");
		}
	}



	var cmplntCatgDom =getDomElementById('complaintCategory');
	cmplntCatgDom.disabled=true;
	if(isNewComplnt){
		//$("#serviceType").trigger("change");
	}

	

}

function applicabilityByServiceType(domElemnt){

	var callerNameDom =getDomElementById('callerName');
	var callerPhoneDom =getDomElementById('callerPhone');
	var callerEmailDom =getDomElementById('callerEmail');


	var customerTypeDom =getDomElementById('custType');
	var cmplntCatgDom =getDomElementById('complaintCategory');
	var serviceRltdDom =getDomElementById('serviceRelated');



	var originCityDom =getDomElementById('originCity');
	var productDom =getDomElementById('product');
	var pincodeDom =getDomElementById('pincode');



	var weightKgDom =getDomElementById('weightKgs');
	var weightGrmDom =getDomElementById('weightGrm');
	var consgtypeDom =getDomElementById('consgTypes');

	var smsToConsignorDom =getDomElementById('smsToConsignor');
	var smsToConsigneeDom =getDomElementById('smsToConsignee');
	var emailToCallerDom =getDomElementById('emailToCaller');
	
	var consignmentDom=getDomElementById('consignment');
	var consignmentStatusDom=getDomElementById('consignmentStatus');
	var consignmentErrorDom=getDomElementById('errorMsg');
	consignmentDom.innerHTML="Consignment tracking information for <strong>CN No. <label id=cnNum></label></strong>";
	consignmentStatusDom.innerHTML="Status :<strong><label id=cnStatus ></label></strong>";
	consignmentErrorDom.innerHTML="";

	$("#result").val("");
	$("#number").val("");

	serviceRltdDom.value="";
	customerTypeDom.value="";
	customerTypeDom.disabled=true;
	originCityDom.value="";
	originCityDom.disabled=true;
	consgtypeDom.value="";
	consgtypeDom.disabled=true;
	pincodeDom.value="";
	pincodeDom.disabled=true;
	
	
	weightKgDom.value="";
	weightKgDom.setAttribute("readOnly",true);
	
	weightGrmDom.value="";
	weightGrmDom.setAttribute("readOnly",true);
	
	cmplntCatgDom.value="";
	productDom.value="";
	smsToConsignorDom.disabled=true;
	smsToConsigneeDom.disabled=true;
	emailToCallerDom.disabled=true;
	emailToCallerDom.checked=false;
	smsToConsignorDom.checked=false;
	smsToConsigneeDom.checked=false;
	enableGlobalButton('save');
	disableGlobalButton('enquiryBtn');
	
	
	disableGlobalButton('trackBtn');
	
	$("#tabs").tabs( "enable" , 0 );
	$("#tabs").tabs( "enable" , 1 );
	$("#tabs").tabs( "enable" , 2);
	$("#tabs").tabs( "enable" , 3 );
	//$("#tabs").data('disabled.tabs', []);
	
	
	if(!isNull(domElemnt.value)){
		if(domElemnt.value==getServiceRequestTypeForConsg() || domElemnt.value==getServiceRequestTypeForBookingRef()){
			customerTypeDom.value="";
			customerTypeDom.disabled=true;
			originCityDom.value="";
			originCityDom.disabled=true;
			consgtypeDom.value="";
			consgtypeDom.disabled=true;
			pincodeDom.value="";
			pincodeDom.disabled=true;
			weightKgDom.value="";
			weightGrmDom.value="";
			productDom.disabled=true;
			smsToConsignorDom.disabled=false;
			smsToConsigneeDom.disabled=false;
			emailToCallerDom.disabled=false;
			enableGlobalButton('trackBtn');
			$("#tabs").tabs("option", "active", 0);
		}else if (domElemnt.value==getServiceRequestTypeForService()) {
			weightKgDom.value="";
			weightKgDom.readOnly=false;
			weightGrmDom.value="";
			weightGrmDom.readOnly=false;
			customerTypeDom.value="";
			customerTypeDom.disabled=false;
			originCityDom.value="";
			originCityDom.disabled=false;
			consgtypeDom.value="";
			consgtypeDom.disabled=false;
			pincodeDom.value="";
			pincodeDom.disabled=false;
			productDom.disabled=false;
			enableGlobalButton('enquiryBtn');
			 $("#tabs").tabs({disabled: [0,1,2]});
			 $("#tabs").tabs("option", "active", 3);
			 consignmentDom.innerHTML="";
				consignmentStatusDom.innerHTML="";
				consignmentErrorDom.innerHTML="";
		}else{
			disableGlobalButton('Edit');
			disableGlobalButton('save');
			// $("#tabs").tabs({disabled: [0,1,2,3]});
		}
	}

}

function validateServiceReqForSave(){

	var isServiceRelated=false;
	var executiveType=" Sales Coordinator";
	var serviceTypeDom =getDomElementById('serviceType');
	var numberDom =getDomElementById('number');

	var callerNameDom =getDomElementById('callerName');
	var callerPhoneDom =getDomElementById('callerPhone');
	var callerEmailDom =getDomElementById('callerEmail');


	var customerTypeDom =getDomElementById('custType');

	var serviceRltdDom =getDomElementById('serviceRelated');
	var cmplntCatgDom =getDomElementById('complaintCategory');


	var originCityDom =getDomElementById('originCity');
	var productDom =getDomElementById('product');
	var pincodeDom =getDomElementById('pincode');



	var weightKgDom =getDomElementById('weightKgs');
	var weightGrmDom =getDomElementById('weightGrm');
	var consgtypeDom =getDomElementById('consgTypes');


	var linkedChqBox=getDomElementById('isLinkedWith');
	var refNumberDom=getDomElementById('linkedServiceReqNo');

	var empDom=getDomElementById('employeeId');
	var empEmailDom=getDomElementById('empEmailId');
	var empMobileDom=getDomElementById('empPhone');

	var statusDom=getDomElementById('status');
	var remarksDom=getDomElementById('remark');
	var industryTypeDom=getDomElementById('industryType');
	var emailToCallerDom =getDomElementById('emailToCaller');

	var statusValue=getComplaintStatus();

	if(isNull(serviceTypeDom.value)){
		alert("Please select service Type");
		setFocusOnElmentByDom(serviceTypeDom);
		return false;
	}



	if(serviceTypeDom.value== getServiceRequestTypeForConsg() || serviceTypeDom.value== getServiceRequestTypeForBookingRef()){
		if(isNull(numberDom.value)){
			alert("Please provide "+getSelectedDropDownTextByDOM(serviceTypeDom)+" number ");
			setFocusOnElmentByDom(numberDom);
			return false;
		}

		executiveType=" Backline Executive ";
	}else if(serviceTypeDom.value== getServiceRequestTypeForService()){
		isServiceRelated=true;
	}else{
		alert("Please do not select service Type as Contact number");
		serviceTypeDom.value="";
		return false;
	}
	if(linkedChqBox.checked){
		if(isNull(refNumberDom.value)){
			alert("please provide Master Reference(linked with) number");
			setFocusOnElmentByDom(refNumberDom);
			return false;
		}
	}

	/** validate Caller information START*/
	callerNameDom.value=trimString(callerNameDom.value);
	if(isNull(callerNameDom.value)){
		alert("Please provide Caller Name");
		setFocusOnElmentByDom(callerNameDom);
		return false;
	}
	callerPhoneDom.value=trimString(callerPhoneDom.value);
	if(isNull(callerPhoneDom.value)){
		alert("Please provide Caller Phone number");
		setFocusOnElmentByDom(callerPhoneDom);
		return false;
	}
	callerEmailDom.value=trimString(callerEmailDom.value);
	/*if(isNull(callerEmailDom.value)){
		alert("Please provide Caller Email number");
		setFocusOnElmentByDom(callerEmailDom);
		return false;
	}*/
	if(emailToCallerDom!=null && emailToCallerDom.checked){
		if(isNull(callerEmailDom.value)){
			alert("Please provide Caller Email address");
			setFocusOnElmentByDom(callerEmailDom);
			return false;
		}
	}
	


	/** validate Caller information END*/

	/** validate Service type  information START*/
	if(isNull(serviceRltdDom.value)){
		alert("Please select  Service Related information");
		setFocusOnElmentByDom(serviceRltdDom);
		return false;
	}

	var queryType=getSelectedValue(serviceRltdDom.value);
	var isReqValidToClose=false;
	var isEmpDtlsRequired=false;

	if(serviceTypeDom.value==getServiceRequestTypeForConsg()|| serviceTypeDom.value==getServiceRequestTypeForBookingRef()){
		IS_REQUEST_COMPLETED=true;
		if(queryType !=getServiceRelatedQueryTypeComplaint() && queryType!=getServiceRelatedQueryTypePodStatus()){
			alert("Please select valid service related Query for Consignment");
			serviceRltdDom.value="";
			setFocusOnElmentByDom(serviceRltdDom);
			return false;
		}
		if(queryType == getServiceRelatedQueryTypeComplaint()){
			if(isNull(customerTypeDom.value)){
				alert("Please select customer type");
				setFocusOnElmentByDom(customerTypeDom);
				return false;
			}
		}
	}

	if(queryType ==getServiceRelatedQueryTypeComplaint()){

		if(isNull(cmplntCatgDom.value)){
			alert("Please select  Complaint Category information");
			setFocusOnElmentByDom(cmplntCatgDom);
			return false;
		}

	}else if (queryType==getServiceRelatedQueryTypeServiceChk() || queryType==getServiceRelatedQueryTypeEmotioncalBond()){
		//origin city,pincode,product
		if(isNull(originCityDom.value)){
			alert("Please select Origin City");
			setFocusOnElmentByDom(originCityDom);
			return false;
		}
		if(queryType!=getServiceRelatedQueryTypeEmotioncalBond()){
			if(isNull(productDom.value)){
				alert("Please select Product details");
				setFocusOnElmentByDom(productDom);
				return false;
			}
		}
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}

		//status should be resolved
		isReqValidToClose=true;
	}else if (queryType==getServiceRelatedQueryTypeTariffType()){
		//origin city,pincode,product,consg type,weight
		if(isNull(originCityDom.value)){
			alert("Please select Origin City");
			setFocusOnElmentByDom(originCityDom);
			return false;
		}
		if(isNull(productDom.value)){
			alert("Please select Product details");
			setFocusOnElmentByDom(productDom);
			return false;
		}
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}
		if(isNull(consgtypeDom.value)){
			alert("Please select Consignment Type ");
			setFocusOnElmentByDom(consgtypeDom);
			return false;
		}
		//if(consgtypeDom.value != getConsgTypeDox()){
		if(isNull(weightKgDom.value)){
			alert("Please provide weight (IN KG) ");
			setFocusOnElmentByDom(weightKgDom);
			return false;
		}
		if(isNull(weightGrmDom.value)){
			alert("Please provide weight (IN grm) ");
			setFocusOnElmentByDom(weightGrmDom);
			return false;
		}
		//}
		//status should be resolved
		isReqValidToClose=true;
	}else if (queryType==getServiceRelatedQueryTypePaperwrk()){
		//product,pincode
		/*if(isNull(productDom.value)){
			alert("Please select Product details");
			setFocusOnElmentByDom(productDom);
			return false;
		}*/
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}

		//status should be resolved
		isReqValidToClose=true;
	}else if (queryType==getServiceRelatedQueryTypeLeadcall() || queryType==getServiceRelatedQueryTypePickupcall()){
		//sales cordinator mobile no,email
		isEmpDtlsRequired=true;
		//status should be resolved
		isReqValidToClose=true;
		if(queryType==getServiceRelatedQueryTypeLeadcall()){
			if(isNull(originCityDom.value)){
				alert("Please select Origin Branch");
				setFocusOnElmentByDom(originCityDom);
				return false;
			}
			if(isNull(industryTypeDom.value)){
				alert("Please select Industry Type");
				setFocusOnElmentByDom(industryTypeDom);
				return false;
			}
		}else{
			//for pick up call
			if(isNull(customerTypeDom.value)){
				alert("Please select customer type");
				setFocusOnElmentByDom(customerTypeDom);
				return false;
			}
			if(isNull(originCityDom.value)){
				alert("Please select Origin Branch");
				setFocusOnElmentByDom(originCityDom);
				return false;
			}
			if(isNull(productDom.value)){
				alert("Please select Product details");
				setFocusOnElmentByDom(productDom);
				return false;
			}
			if(isNull(pincodeDom.value)){
				alert("Please select Destination pincode details");
				setFocusOnElmentByDom(pincodeDom);
				return false;
			}
			if(isNull(consgtypeDom.value)){
				alert("Please select Consignment Type ");
				setFocusOnElmentByDom(consgtypeDom);
				return false;
			}



		}
	}else if (queryType==getServiceRelatedQueryTypeGeneralInf()){
		//remarks

		//status should be resolved
		isReqValidToClose=true;
	}/*else if (queryType==getServiceRelatedQueryTypeCriticalComplaint() || queryType==getServiceRelatedQueryTypeEscComplaint()){
		//emp validation
		isEmpDtlsRequired=true;

		//status should be resolved
		isReqValidToClose=true;
	}*/
	if(isReqValidToClose){
		if(statusValue !=getComplaintStatusResolved()){
			alert("Please select status as resolved");
			setFocusOnElmentByDom(statusDom);
			return false;
		}
	}

	/** validate Service type  information END*/

	if(statusValue==getComplaintStatusBackline() || statusValue==getComplaintStatusFollowup() || isEmpDtlsRequired){
		/** validate Employee information START*/
		if(isNull(empDom.value)){
			alert("Please select"+executiveType+"Details");
			setFocusOnElmentByDom(empDom);
			return false;
		}
		if(isNull(empEmailDom.value)){
			alert("Please provide "+executiveType+" Email details");
			setFocusOnElmentByDom(empEmailDom);
			return false;
		}
		if(isNull(empMobileDom.value)){
			alert("Please provide "+executiveType+" Mobile number ");
			setFocusOnElmentByDom(empMobileDom);
			return false;
		}


	}
	if(isNull(statusDom.value)){
		alert("Please select status");
		setFocusOnElmentByDom(statusDom);
		return false;
	}
	remarksDom.value=trimString(remarksDom.value);
	if(isNull(remarksDom.value)){
		alert('Please provide remarks');
		setFocusOnElmentByDom(remarksDom);
		return false;
	}
	/** validate Employee information END*/
	
	/*********check whether user Clicked on Enquiry button
	 * */
	if(!IS_REQUEST_COMPLETED){
		alert("please press Enquiry");
		setFocusOnElmentById('enquiryBtn');
		return false;
	}
	return true;
}


//Onchange for Service Query
function validateServiceQuery(domElement){
	IS_REQUEST_COMPLETED=false;
	var serviceTypeDom =getDomElementById('serviceType');
	var originCityDom =getDomElementById('originCity');

	var productDom =getDomElementById('product');
	var pincodeDom =getDomElementById('pincode');


	var weightKgDom =getDomElementById('weightKgs');
	var weightGrmDom =getDomElementById('weightGrm');
	var consgtypeDom =getDomElementById('consgTypes');
	var cmplntCatgDom =getDomElementById('complaintCategory');
	var customerTypeDom =getDomElementById('custType');

	var industryTypeDom =getDomElementById('industryType');

	$("#result").val("");
	$("#status").val("");
	customerTypeDom.disabled=true;
	customerTypeDom.setAttribute("tabindex","-1");
	customerTypeDom.value="";
	cmplntCatgDom.disabled=true;
	cmplntCatgDom.setAttribute("tabindex","-1");
	cmplntCatgDom.value='';

	originCityDom.disabled=true;
	originCityDom.setAttribute("tabindex","-1");
	originCityDom.value="";

/*	industryTypeDom.disabled=true;
	industryTypeDom.setAttribute("tabindex","-1");
	industryTypeDom.value="";*/



	productDom.disabled=true;
	productDom.setAttribute("tabindex","-1");
	productDom.value="";

	pincodeDom.disabled=true;
	pincodeDom.setAttribute("tabindex","-1");
	pincodeDom.value="";

	consgtypeDom.disabled=true;
	consgtypeDom.setAttribute("tabindex","-1");
	consgtypeDom.value='';

	weightKgDom.value='';
	weightKgDom.setAttribute("tabindex","-1");
	weightKgDom.setAttribute("readonly",true);
	
	weightGrmDom.value='';
	weightGrmDom.setAttribute("tabindex","-1");
	weightGrmDom.setAttribute("readonly",true);

	var isEnquryBtnReqrd=false;
	disableGlobalButton('enquiryBtn');

	if(isNull(serviceTypeDom.value)){
		alert("Please select Service Type");
		domElement.value="";
		setFocusOnElmentByDom(serviceTypeDom);
		return false;
	}

	if(!isNull(domElement.value)){
		var queryType=getSelectedValue(domElement.value);
		if(serviceTypeDom.value==getServiceRequestTypeForConsg()|| serviceTypeDom.value==getServiceRequestTypeForBookingRef()){
			if(queryType !=getServiceRelatedQueryTypeComplaint() && queryType!=getServiceRelatedQueryTypePodStatus()){
				alert("Please select valid service related Query for Consignment");
				domElement.value="";
				setFocusOnElmentByDom(domElement);
				return false;
			}
			if(queryType ==getServiceRelatedQueryTypeComplaint()){
				customerTypeDom.disabled=false;
				customerTypeDom.removeAttribute("tabindex");
			}

		}
		if(serviceTypeDom.value==getServiceRequestTypeForService() && (queryType==getServiceRelatedQueryTypeComplaint() || queryType==getServiceRelatedQueryTypePodStatus() )){
			alert("Please select valid service related Query for Service");
			domElement.value="";
			setFocusOnElmentByDom(domElement);
			return false;
		}


		if(queryType==getServiceRelatedQueryTypeComplaint()){
			cmplntCatgDom.disabled=false;
			cmplntCatgDom.removeAttribute("tabindex");
			IS_REQUEST_COMPLETED=true;
			//setServiceRequestStatusAsResolved();
		}else if (queryType==getServiceRelatedQueryTypeServiceChk() || queryType==getServiceRelatedQueryTypeEmotioncalBond()){
			//origin city,pincode,product
			originCityDom.disabled=false;
			originCityDom.removeAttribute("tabindex");

			if(queryType !=getServiceRelatedQueryTypeEmotioncalBond()){
				productDom.disabled=false;
				productDom.removeAttribute("tabindex");
			}

			pincodeDom.disabled=false;
			pincodeDom.removeAttribute("tabindex");
			setServiceRequestStatusAsResolved();
			isEnquryBtnReqrd=true;
			IS_REQUEST_COMPLETED=false;

		}else if (queryType==getServiceRelatedQueryTypeTariffType()){
			//origin city,pincode,product,consg type,weight
			originCityDom.disabled=false;
			originCityDom.removeAttribute("tabindex");

			productDom.disabled=false;
			productDom.removeAttribute("tabindex");

			pincodeDom.disabled=false;
			pincodeDom.removeAttribute("tabindex");

			
			
			weightKgDom.readOnly=false;
			weightKgDom.removeAttribute("tabindex");
			
			weightGrmDom.readOnly=false;
			weightGrmDom.removeAttribute("tabindex");
			

			consgtypeDom.disabled=false;
			consgtypeDom.removeAttribute("tabindex");
			setServiceRequestStatusAsResolved();
			isEnquryBtnReqrd=true;
			IS_REQUEST_COMPLETED=false;
		}else if (queryType==getServiceRelatedQueryTypePaperwrk()){
			//product,pincode
			//productDom.disabled=false;
			originCityDom.disabled=false;
			originCityDom.removeAttribute("tabindex");

			pincodeDom.disabled=false;
			pincodeDom.removeAttribute("tabindex");

			isEnquryBtnReqrd=true;
			IS_REQUEST_COMPLETED=false;
			setServiceRequestStatusAsResolved();

		}else if (queryType==getServiceRelatedQueryTypeGeneralInf()){
			setServiceRequestStatusAsResolved();
			IS_REQUEST_COMPLETED=true;
		}else if (queryType==getServiceRelatedQueryTypeLeadcall() || queryType==getServiceRelatedQueryTypePickupcall()){
			setServiceRequestStatusAsResolved();
			originCityDom.disabled=false;
			originCityDom.removeAttribute("tabindex");
			IS_REQUEST_COMPLETED=true;
			if(queryType==getServiceRelatedQueryTypePickupcall()){
				productDom.disabled=false;
				productDom.removeAttribute("tabindex");

				pincodeDom.disabled=false;
				pincodeDom.removeAttribute("tabindex");

				customerTypeDom.disabled=false;
				customerTypeDom.removeAttribute("tabindex");

				consgtypeDom.disabled=false;
				consgtypeDom.removeAttribute("tabindex");
			}else{
				industryTypeDom.disabled=false;
				industryTypeDom.removeAttribute("tabindex");
			}
		}

	}

	if(isEnquryBtnReqrd){
		enableGlobalButton('enquiryBtn');
	}

}

function serviceEnquiry(){
	//alert("serviceEnquiry");
	IS_REQUEST_COMPLETED=false;
	var serviceTypeDom =getDomElementById('serviceType');
	var originCityDom =getDomElementById('originCity');

	var productDom =getDomElementById('product');
	var pincodeDom =getDomElementById('pincode');

	var customerTypeDom =getDomElementById('custType');


	
	var weightKgDom =getDomElementById('weightKgs');
	var weightGrmDom =getDomElementById('weightGrm');
	
	var consgtypeDom =getDomElementById('consgTypes');
	var cmplntCatgDom =getDomElementById('complaintCategory');
	var queryTypedomElement =getDomElementById('serviceRelated');
	var queryType=getSelectedValue(queryTypedomElement.value);
	var weightValue=0.0;
	if(isNull(queryType)){
		setFocusOnElmentByDom(queryTypedomElement);
		//alert("Please select Service Query Type");
		alert("Please select Service Related details");
		return false;
	}


	if(serviceTypeDom.value==getServiceRequestTypeForConsg()|| serviceTypeDom.value==getServiceRequestTypeForBookingRef()){
		alert("User does not have this facility for Consignment");
		return false;

	}

	if(queryType==getServiceRelatedQueryTypeComplaint()){
		alert('User does not have this facility for Complaints');
		return false;
	}else if (queryType==getServiceRelatedQueryTypeServiceChk() || queryType==getServiceRelatedQueryTypeEmotioncalBond()){
		//origin city,pincode,product
		if(isNull(originCityDom.value)){
			alert("Please select Origin City");
			setFocusOnElmentByDom(originCityDom);
			return false;
		}
		if(queryType!=getServiceRelatedQueryTypeEmotioncalBond()){
			if(isNull(productDom.value)){
				alert("Please select Product details");
				setFocusOnElmentByDom(productDom);
				return false;
			}
		}
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}


	}else if (queryType==getServiceRelatedQueryTypeTariffType()){
		//origin city,pincode,product,consg type,weight
		if(isNull(originCityDom.value)){
			alert("Please select Origin City");
			setFocusOnElmentByDom(originCityDom);
			return false;
		}
		if(isNull(productDom.value)){
			alert("Please select Product details");
			setFocusOnElmentByDom(productDom);
			return false;
		}
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}
		if(isNull(consgtypeDom.value)){
			alert("Please select Consignment Type ");
			setFocusOnElmentByDom(consgtypeDom);
			return false;
		}
		//if(consgtypeDom.value != getConsgTypeDox()){
		if(isNull(weightKgDom.value)){
			alert("Please provide weight(Kg)  ");
			setFocusOnElmentByDom(weightKgDom);
			return false;
		}
		if(isNull(weightGrmDom.value)){
			alert("Please provide weight(Gr)  ");
			setFocusOnElmentByDom(weightGrmDom);
			return false;
		}
		weightKgDom.value=trimString(weightKgDom.value);
		weightGrmDom.value=trimString(weightGrmDom.value);
		 weightValue=trimString(weightKgDom.value)+"."+trimString(weightGrmDom.value);
		if(parseFloat(weightValue)==0.0){
			alert("Please provide valid weight  ");
			weightKgDom.value="";
			weightGrmDom.value="";
			setFocusOnElmentByDom(weightKgDom);
			return false;
		}
		//}
	}else if (queryType==getServiceRelatedQueryTypePaperwrk()){
		/*if(isNull(productDom.value)){
			alert("Please select Product details");
			setFocusOnElmentByDom(productDom);
			return false;
		}*/
		if(isNull(pincodeDom.value)){
			alert("Please select Destination pincode details");
			setFocusOnElmentByDom(pincodeDom);
			return false;
		}

	}
	$("#result").val("");

	var url='./serviceRequestForService.do?submitName=ajaxEnquiryForServiceRequest&cityId='+originCityDom.value+"&pincode="+pincodeDom.value+"&weight="+weightValue+"&product="+productDom.value+"&customerType="+customerTypeDom.value+"&queryType="+queryType+"&consgType="+consgtypeDom.value;
	ajaxJquery(url,FORM_ID,ajaxReponseForTariffEnquiry);

}
function ajaxReponseForTariffEnquiry(rspObject){
	var result=null;
	if(!isNull(rspObject)){
		var responseText =jsonJqueryParser(rspObject); 
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return false;
		}else if(success!=null){
			result=success;
		}else{
			result=responseText.queryResult;
		}
		$("#result").val(result);
		alert(responseText.queryResult);
		IS_REQUEST_COMPLETED=true;

	}

}
function enditForServiceRequest(){
	if(!isNewServiceReq()){
		var empDom=getDomElementById('employeeId');
		var empEmailDom=getDomElementById('empEmailId');
		var empMobileDom=getDomElementById('empPhone');

		var statusDom=getDomElementById('status');
		var remarksDom=getDomElementById('remark');

		//var statusValue=getComplaintStatus();

		if(isNull(empDom.value)){
			alert('Please select Employee details');
			setFocusOnElmentByDom(empDom);
			return false;
		}
		if(isNull(empEmailDom.value)){
			alert(' Employee Email is mandatory ');
			setFocusOnElmentByDom(empEmailDom);
			return false;
		}
		if(isNull(empMobileDom.value)){
			alert(' Employee Mobile is mandatory ');
			setFocusOnElmentByDom(empMobileDom);
			return false;
		}
		if(isNull(statusDom.value)){
			alert(' Please select status ');
			setFocusOnElmentByDom(statusDom);
			return false;
		}
		if(isNull(remarksDom.value)){
			alert(' Please provide Remarks ');
			setFocusOnElmentByDom(remarksDom);
			return false;
		}
		if( confirm("Do you want to Update the Service Request Details ?")){
			enableAll();
			var url="./serviceRequestForService.do?submitName=saveServiceReqDetails";
			document.serviceRequestForServiceForm.action=url;
			document.serviceRequestForServiceForm.submit();
		}
	}else{
		alert("user can not modify the details");
		return false;
	}
}
function onlyAlphabetForComplaints(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 46
			|| charCode == 32 || charCode == 9 || charCode == 8
			|| charCode == 127  || charCode == 39
			|| charCode == 0) {
		return true;
	} else {
		return false;
	}

}

function validateNameValidation(domElment){
	var regExp=new RegExp("^[a-zA-Z]");
	var callerName=domElment.value;
	if(!isNull(callerName)){
		var firstChar=callerName.charAt(0);
		var isValid=regExp.test(firstChar);
		if(!isValid){

			alert("Caller Name should starts with a Alphabet");
			setFocusOnElmentByDom(domElment);
			domElment.value="";

		}

	}
}

/**
 * For Service type DropDown
 * @param domElement
 */
function dynamicMandatorySymbolForServiceType(domElement){
	var linkedChqBox=getDomElementById('isLinkedWith');

	var mandatoryNumber=getDomElementById('mandatoryNumber');
	var mandatorySrvRefNumber=getDomElementById('mandatorySrvRefNumber');
	var mandatoryCompCtgry= getDomElementById('mandatoryCompCtgry');
	var mandatoryCustomerType=getDomElementById('mandatoryCustomerType');

	mandatoryNumber.style.display="none";
	mandatorySrvRefNumber.style.display="none";
	mandatoryCompCtgry.style.display="none";
	mandatoryCustomerType.style.display="none";
	if(!isNull(domElement.value)){
		if(domElement.value==getServiceRequestTypeForConsg()|| domElement.value==getServiceRequestTypeForBookingRef()){
			mandatoryNumber.style.display="inline";
			mandatoryCustomerType.style.display="inline";
		}
	}

	if(linkedChqBox.checked){
		mandatorySrvRefNumber.style.display="inline";
	}
}
function dynamicMandatorySymbolForServiceRelated(domElement){
	var mandatoryCompCtgry=getDomElementById('mandatoryCompCtgry');

	var mandatoryCustomerType=getDomElementById('mandatoryCustomerType');
	var mandatoryOrigin=getDomElementById('mandatoryOriginCity');
	var mandatoryProduct= getDomElementById('mandatoryProduct');

	var mandatoryPincode=getDomElementById('mandatoryPincode');
	var mandatoryWeight=getDomElementById('mandatoryWeight');
	var mandatoryConsgType=getDomElementById('mandatoryConsgType');

	var mandatoryEmployee=getDomElementById('mandatoryEmployee');
	var mandatoryEmpMail=getDomElementById('mandatoryEmpMail');
	var mandatoryEmpPhone=getDomElementById('mandatoryEmpPhone');
	var mandatoryIndustryType=getDomElementById('mandatoryIndustryType');

	var labelOriginName=getDomElementById('originName');




	labelOriginName.innerHTML="Origin";
	mandatoryCompCtgry.style.display="none";
	//mandatoryCustomerType.style.display="none";
	mandatoryOrigin.style.display="none";

	mandatoryProduct.style.display="none";
	mandatoryPincode.style.display="none";
	mandatoryWeight.style.display="none";
	mandatoryCustomerType.style.display="none";

	mandatoryConsgType.style.display="none";
	mandatoryEmployee.style.display="none";
	mandatoryEmpMail.style.display="none";
	mandatoryEmpPhone.style.display="none";	
	mandatoryIndustryType.style.display="none";

	var queryType=getSelectedValue(domElement.value);
	if(!isNull(queryType)){
		if(queryType ==getServiceRelatedQueryTypeComplaint()){
			mandatoryCompCtgry.style.display="inline";
			mandatoryCustomerType.style.display="inline";
		}else 	if(queryType==getServiceRelatedQueryTypeServiceChk() || queryType==getServiceRelatedQueryTypeEmotioncalBond() || queryType==getServiceRelatedQueryTypeTariffType()){
			mandatoryOrigin.style.display="inline";
			mandatoryProduct.style.display="inline";
			mandatoryPincode.style.display="inline";
			if(queryType==getServiceRelatedQueryTypeTariffType()){
				mandatoryWeight.style.display="inline";
				mandatoryConsgType.style.display="inline";
			}else if(queryType==getServiceRelatedQueryTypeEmotioncalBond()){
				mandatoryProduct.style.display="none";
			}
		}else if(queryType==getServiceRelatedQueryTypePaperwrk()){
			mandatoryOrigin.style.display="inline";
			mandatoryPincode.style.display="inline";
		}else if (queryType==getServiceRelatedQueryTypeLeadcall() || queryType==getServiceRelatedQueryTypePickupcall()){
			mandatoryEmployee.style.display="inline";
			mandatoryEmpMail.style.display="inline";
			mandatoryEmpPhone.style.display="inline";	
			labelOriginName.innerHTML="Origin Branch";
			if(queryType==getServiceRelatedQueryTypeLeadcall()){
				mandatoryOrigin.style.display="inline";
				mandatoryIndustryType.style.display="inline";
			}else{
				//for pick up call
				mandatoryProduct.style.display="inline";
				mandatoryPincode.style.display="inline";
				mandatoryConsgType.style.display="inline";
				mandatoryCustomerType.style.display="inline";
			}
		}
	}


}
function dynamicMandatorySymbolForStatus(domElement){
	var mandatoryEmployee=getDomElementById('mandatoryEmployee');
	var mandatoryEmpMail=getDomElementById('mandatoryEmpMail');
	var mandatoryEmpPhone=getDomElementById('mandatoryEmpPhone');

	var statusValue=null;

	mandatoryEmployee.style.display="none";
	mandatoryEmpMail.style.display="none";
	mandatoryEmpPhone.style.display="none";	

	if(!isNull(domElement.value)){
		statusValue=getSelectedValue(domElement.value);
	}

	if(!isNull(statusValue)){
		if(statusValue==getComplaintStatusBackline() || statusValue==getComplaintStatusFollowup()){
			mandatoryEmployee.style.display="inline";
			mandatoryEmpMail.style.display="inline";
			mandatoryEmpPhone.style.display="inline";	
		}
	}

}

function getServiceRealtedQueryTypeByServiceType(domElemnt){
	CURRENT_DOM=domElemnt;
	if(!isNull(domElemnt.value)){
		var url='./serviceRequestForService.do?submitName=ajaxActionGetServiceRequestQueryTypeByServiceType&serviceType='+domElemnt.value;
		ajaxJquery(url,FORM_ID,ajxRespTOServiceRelatedQueryType);
	}else{
		createEmptyDropDown('serviceRelated');
	}

}
function ajxRespTOServiceRelatedQueryType(rspObject){
	var serviceRelatedDropdDown=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
			serviceRelatedDropdDown = rspObject;
			createDropDown('serviceRelated',serviceRelatedDropdDown);
		}else{
			CURRENT_DOM.value="";
			createEmptyDropDown('serviceRelated');
			CURRENT_DOM.focus();
		}
	}else{
		alert("Service related details does not exist for "+getSelectedDropDownTextByDOM(CURRENT_DOM));
		CURRENT_DOM.value="";
		createEmptyDropDown('serviceRelated');
		CURRENT_DOM.focus();
	}
	CURRENT_DOM=null;
}

//Onchange for Service Query
function enterKeyForServiceQuery(domElement,keyCode){

	if(!enterKeyNavWithoutFocus(keyCode)){
		return;
	}

	var originCityDom =getDomElementById('originCity');

	var productDom =getDomElementById('product');
	var pincodeDom =getDomElementById('pincode');

	var empDom=getDomElementById('employeeId');


	
	var weightKgDom =getDomElementById('weightKgs');
	var weightGrmDom =getDomElementById('weightGrm');
	
	var consgtypeDom =getDomElementById('consgTypes');
	var cmplntCatgDom =getDomElementById('complaintCategory');
	var statusDom=getDomElementById('status');
	var customerTypeDom =getDomElementById('custType');
	var industryTypeDom =getDomElementById('industryType');

	originCityDom.removeAttribute('onkeydown');
	consgtypeDom.removeAttribute('onkeydown');

	productDom.removeAttribute('onkeydown');
	pincodeDom.removeAttribute('onkeydown');

	weightKgDom.removeAttribute('onkeydown');
	weightGrmDom.removeAttribute('onkeydown');
	
	cmplntCatgDom.removeAttribute('onkeydown');
	industryTypeDom.removeAttribute('onkeydown');

	if(!isNull(domElement.value)){

		var queryType=getSelectedValue(domElement.value);

		if(queryType==getServiceRelatedQueryTypeComplaint()){
			setFocusOnElmentByDom(cmplntCatgDom);
			cmplntCatgDom.setAttribute("onkeydown","return enterKeyNav('custType' ,event.keyCode)");
			customerTypeDom.setAttribute("onkeydown","return enterKeyNav('status' ,event.keyCode)");
		}else if (queryType==getServiceRelatedQueryTypeServiceChk() || queryType==getServiceRelatedQueryTypeEmotioncalBond()){
			setFocusOnElmentByDom(originCityDom);
			//origin city,pincode,product
			originCityDom.setAttribute("onkeydown","return enterKeyNav('product' ,event.keyCode)");

			if(queryType ==getServiceRelatedQueryTypeEmotioncalBond()){
				originCityDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			}else{
				productDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			}

			pincodeDom.setAttribute("onkeydown","return enterKeyNav('status' ,event.keyCode)");

		}else if (queryType==getServiceRelatedQueryTypeTariffType()){
			//origin city,pincode,product,consg type,weight
			setFocusOnElmentByDom(originCityDom);
			originCityDom.setAttribute("onkeydown","return enterKeyNav('product' ,event.keyCode)");
			productDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			pincodeDom.setAttribute("onkeydown","return enterKeyNav('weightKgs' ,event.keyCode)");

			//weightDom.setAttribute("onkeydown","return enterKeyNav('consgTypes' ,event.keyCode)");
			weightKgDom.setAttribute("onkeydown","return enterKeyNav('weightGrm' ,event.keyCode)");
			weightGrmDom.setAttribute("onkeydown","return enterKeyNav('consgTypes' ,event.keyCode)");
			
			consgtypeDom.setAttribute("onkeydown","return enterKeyNav('status' ,event.keyCode)");

		}else if (queryType==getServiceRelatedQueryTypePaperwrk()){
			//product,pincode
			//productDom.disabled=false;
			setFocusOnElmentByDom(originCityDom);
			originCityDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			//productDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			pincodeDom.setAttribute("onkeydown","return enterKeyNav('status' ,event.keyCode)");

		}else if(queryType==getServiceRelatedQueryTypeLeadcall()){
			setFocusOnElmentByDom(originCityDom);
			originCityDom.setAttribute("onkeydown","return enterKeyNav('industryType' ,event.keyCode)");
			industryTypeDom.setAttribute("onkeydown","return enterKeyNav('employeeId' ,event.keyCode)");
		}else if(queryType==getServiceRelatedQueryTypePickupcall()){

			setFocusOnElmentByDom(customerTypeDom);
			customerTypeDom.setAttribute("onkeydown","return enterKeyNav('originCity' ,event.keyCode)");
			originCityDom.setAttribute("onkeydown","return enterKeyNav('product' ,event.keyCode)");
			productDom.setAttribute("onkeydown","return enterKeyNav('pincode' ,event.keyCode)");
			pincodeDom.setAttribute("onkeydown","return enterKeyNav('consgTypes' ,event.keyCode)");
			consgtypeDom.setAttribute("onkeydown","return enterKeyNav('employeeId' ,event.keyCode)");
		}else if(queryType==getServiceRelatedQueryTypeGeneralInf()){
			setFocusOnElmentByDom(statusDom);
		}else if(queryType==getServiceRelatedQueryTypePodStatus()){
			//setFocusOnElmentByDom(customerTypeDom);
			//customerTypeDom.setAttribute("onkeydown","return enterKeyNav('status' ,event.keyCode)");
			/** FFCL Raised issue in Mail shared by BA on Thursday, December 04, 2014 5:38 PM*/
			setFocusOnElmentByDom(statusDom);
		}

	}else{
		setFocusOnElmentById('save');
	}

}

function populateAllBranchesOrCities(domElemnt){
	CURRENT_DOM=domElemnt;
	createEmptyDropDown('originCity');
	if(!isNull(domElemnt.value)){
		var url=null;
		var queryType=getSelectedValue(domElemnt.value);

		url='serviceRequestForService.do?submitName=ajaxActionToGetAllCities';
		if(queryType==getServiceRelatedQueryTypeLeadcall()||queryType==getServiceRelatedQueryTypePickupcall()){
			url='serviceRequestForService.do?submitName=ajaxActionToGetAllBranches';
		}

		ajaxJquery(url,FORM_ID,ajxRespTOPopulateAllBranchesOrCities);
	}

}

/**
 * ajxRespTOPopulatePincodesByCity : For Pincode DropDown
 * @param rspObject
 * @returns {Boolean}
 */
function ajxRespTOPopulateAllBranchesOrCities(rspObject){
	var cityDropDownValues=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
			cityDropDownValues = rspObject;
			createDropDown('originCity',cityDropDownValues);
		}else{
			CURRENT_DOM.value="";
			createEmptyDropDown('originCity');
			CURRENT_DOM.focus();
		}
	}else{
		alert("Branch Details doesnot exist for "+getSelectedDropDownTextByDOM(CURRENT_DOM));
		CURRENT_DOM.value="";
		createEmptyDropDown('originCity');
		CURRENT_DOM.focus();
	}
	CURRENT_DOM=null;

}

function openComplaintTransferScreen(complaintNumber){
	var url="./complaintTrasfer.do?submitName=preparePageForTransfer&serviceReqNo="+complaintNumber;
	progressBar();
	var w =window.open(url,'','height=450,width=600,left=60,top=120,resizable=yes,scrollbars=auto');
	setTimeout(function() 
			{
		if (w.closed) {
			jQuery.unblockUI();
			refershPage();
		}
		else
			setTimeout(arguments.callee, 100);
			}, 100);
	
}

function consignmentTrackingForComplaint(){
	var cnObj = getDomElementById("number");
	var consgNo = trimString(cnObj.value);
	var type = getDomElementById("serviceType");
	var trakcingCnType=null;
	
	if (isNull(consgNo)) {
		alert('Please enter '+getSelectedDropDownTextByDOM(type));
		cnObj.value = '';
		cnObj.focus();
		return;
	}
	if (isNull(type.value)) {
		alert('Please select type');
		$('#typeName').focus();
		return;
	}
	if (type.value == getServiceRequestTypeForBookingRef()) {
		if (validateCNFormat(consgNo)) {
			alert('Consignment Number not allowed');
			cnObj.value = "";
			$('#number').focus();
			return;
		}
		trakcingCnType="RN";
	}
	if (type.value == getServiceRequestTypeForConsg()) {
		if(!validateCNFormat(consgNo)){
			return false;
		}
		trakcingCnType="CN";
	}

	url = "./consignmentTrackingHeader.do?submitName=viewTrackInformation&type="
			+ trakcingCnType + "&number=" + consgNo;
	ajaxCalWithoutForm(url, populateConsignmentForComplaint);
}
function populateConsignmentForComplaint(data) {
	var cnObj = getDomElementById("number");
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];

		if(responseText!=null && error!=null){			
			//cnObj.value="";
			alert(error);
			return ;
		}
		if(!isNull(data.incompleteData)){
			getDomElementById("errorMsg").innerHTML = data.incompleteData;
		}
		cnObj.readOnly=true;
		getDomElementById("serviceType").disabled = true;
		
		getDomElementById("pickdate").value = data.pckDate;
		getDomElementById("bookname").value = data.bookedBy;
		
		if(!isNull(data.consignmentTO)){
			getDomElementById("firstName").value = data.consignmentTO.consignorTO.firstName;
			getDomElementById("cityName").value = data.consignmentTO.consignorTO.orgCity;
			getDomElementById("address").value = data.consignmentTO.consignorTO.address;
			getDomElementById("pincodeTr").value = data.consignmentTO.consignorTO.orgPincode;
			getDomElementById("phone").value = data.consignmentTO.consignorTO.phone;
			getDomElementById("state").value = data.consignmentTO.consignorTO.orgState;
			getDomElementById("mobile").value = data.consignmentTO.consignorTO.mobile;
			getDomElementById("addr").value = data.consignmentTO.consignorTO.address;
			getDomElementById("bkgInfoMobile").value = data.consignmentTO.consignorTO.mobile;

			getDomElementById("firstname").value = data.consignmentTO.consigneeTO.firstName;
			getDomElementById("city").value = data.consignmentTO.consigneeTO.destCity;
			getDomElementById("adress").value = data.consignmentTO.consigneeTO.address;
			getDomElementById("pincodes").value = data.consignmentTO.consigneeTO.destPincode;
			getDomElementById("phones").value = data.consignmentTO.consigneeTO.phone;
			getDomElementById("State").value = data.consignmentTO.consigneeTO.destState;
			getDomElementById("mobiles").value = data.consignmentTO.consigneeTO.mobile;
			if(!isNull(data.consignmentTO.customerTO)){
				getDomElementById("custname").value = data.consignmentTO.customerTO.customerCode;
			}
		}
		if(!isNull(data.bookingTO)){
			getDomElementById("bookingoffice").value = data.bookingTO.officeName;
			getDomElementById("destoffice").value = data.bookingTO.cityName;
			getDomElementById("consgtype").value = data.bookingTO.consgTypeName;
			getDomElementById("bookdate").value = data.bookingTO.bookingDate;
			getDomElementById("finalwt").value = data.bookingTO.finalWeight;
			getDomElementById("paperworkno").value = data.bookingTO.paperWorkRefNo;
			getDomElementById("paperworktype").value = data.bookingTO.paperWork;
			getDomElementById("actualwt").value = data.bookingTO.actualWeight;
			getDomElementById("volweight").value = data.bookingTO.volWeight;
			getDomElementById("insured").value = data.bookingTO.insuredBy;
			getDomElementById("length").value = data.bookingTO.length;
			getDomElementById("breadth").value = data.bookingTO.breath;
			getDomElementById("height").value = data.bookingTO.height;
			getDomElementById("declareValue").value = data.bookingTO.declaredValue;
			getDomElementById("custname").value = data.bookingTO.customerCodeSingle;
			getDomElementById("cnNum").innerHTML = data.bookingTO.consgNumber;
			getDomElementById("cnStatus").innerHTML = data.bookingTO.cnStatus;
		}

		if (data.childCNTO != null) {
			var noOfChildCns = data.childCNTO.length;
			getDomElementById("nopeices").innerHTML = noOfChildCns;
			for ( var i = 0; i < noOfChildCns; i++) {
				var childCN = data.childCNTO[i];
				addChildCnRows(childCN);
			}
		}else{
			getDomElementById("nopeices").innerHTML = '0';
		}
		
		detailTrackingForComplaints();
		if (data.processMapTO != null) {
			for ( var indexer = 0; indexer < data.processMapTO.length; indexer++) {
				var rowTrackingDtls = data.processMapTO[indexer];
				var screenIndexer = indexer + 1;
				addDetailRows(screenIndexer, rowTrackingDtls);
			}
		}
		disableGlobalButton('trackBtn');
	} else {
		alert('No search Results found.');
	}	
}
function detailTrackingForComplaints() {	 
	 var oTable2 = $('#detailsTable').dataTable( {
			"sScrollY": "220",
			"sScrollX": "80%",
			"sScrollXInner":"100%",
			"bScrollCollapse": false,
			"bSort": false,
			"bInfo": false,
			"bPaginate": false,
			 "bDestroy":true,
			 "bRetrieve":true,
			"sPaginationType": "full_numbers"
		} );
	 new FixedColumns( oTable2, {
			"sLeftWidth": 'relative',
			"iLeftColumns": 0,
			"iRightColumns": 0,
			"iLeftWidth": 0,
			"iRightWidth": 0
		} );
}
function enableMandatoryMark(domelement){
	var mandatoryEmailId=getDomElementById('mandatoryCallerEmail');
	if(domelement.checked){
		mandatoryEmailId.style.display="inline";
	}else{
		mandatoryEmailId.style.display="none";
	}
}