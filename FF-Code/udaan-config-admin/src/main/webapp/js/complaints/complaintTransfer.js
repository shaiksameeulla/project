//Global Variables
var FORM_ID="serviceRequestForServiceForm";
var CURRENT_DOM=null;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";


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



function populateCityByOffice(domElemnt){
	CURRENT_DOM=domElemnt;
	createEmptyDropDown('destinationStationId');
	if(!isNull(domElemnt.value) ){
		var url='./serviceRequestForService.do?submitName=ajaxActionGetCityListByRhoOffice&officeId='+domElemnt.value;
		ajaxJquery(url,FORM_ID,ajxRespTOPopulateCityList);
	}

}

/**
 * ajxRespTOPopulatePincodesByCity : For Pincode DropDown
 * @param rspObject
 * @returns {Boolean}
 */
function ajxRespTOPopulateCityList(rspObject){
	var CITY_DROP_DOWN_VALUES=null;
	var EMP_DROP_DOWN_VALUES=null;
	createEmptyDropDown('destinationStationId');
	createEmptyDropDown('employeeId');
	if(!isNull(rspObject)){
		var responseText =jsonJqueryParser(rspObject); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			//case (i):get dropdown values for DElivery Type
			CITY_DROP_DOWN_VALUES =responseText["CITY"];
			EMP_DROP_DOWN_VALUES =responseText["EMP"];
			if(CITY_DROP_DOWN_VALUES!=null){
				createDropDownForJquery('destinationStationId',CITY_DROP_DOWN_VALUES);
				setFocusOnElmentById('destinationStationId');
			}else if (EMP_DROP_DOWN_VALUES!=null){
				createDropDownForJquery('employeeId',EMP_DROP_DOWN_VALUES);
				setFocusOnElmentById('employeeId');
			}else{
				alert("City details not exist for the  office "+getSelectedDropDownTextByDOM(CURRENT_DOM));
				CURRENT_DOM.value="";
				CURRENT_DOM.focus();
			}
			
		}
	}else{
		alert("City details not exist for the  office "+getSelectedDropDownTextByDOM(CURRENT_DOM));
		CURRENT_DOM.value="";
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
function populateEmpEmailIdAndMobile(domElmnet){
	CURRENT_DOM=domElmnet;
	clearEmployeeInfo();
	if(!isNull(domElmnet.value)){
		var url='./serviceRequestForService.do?submitName=ajaxActionGetEmpToDtslByEmpId&roleName='+getBackLineExecutive()+"&officeId="+$("#destinationStationId").val()+"&empId="+domElmnet.value+"&transferScreen=transferScreen";
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

function getCityWiseEmployeeListForComplaintTransfer(domElemnt){
	CURRENT_DOM=domElemnt;
	createEmptyDropDown('employeeId');
	clearEmployeeInfo();

	if(!isNull(domElemnt.value)){
		var roleName=getBackLineExecutive();
		var url='serviceRequestForService.do?submitName=ajaxActionGetEmployeeDtslByRoleAndCity&roleName='+roleName+"&cityId="+$("#destinationStationId").val();
		ajaxJquery(url,FORM_ID,ajxRespTOPopulateCityWiseEmployeeListForComplaintTransfer);
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
function ajxRespTOPopulateCityWiseEmployeeListForComplaintTransfer(rspObject){
	var employeeDropDown=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
			employeeDropDown = rspObject;
			createDropDown('employeeId',employeeDropDown);
		}else{
			CURRENT_DOM.value="";
			createEmptyDropDown('employeeId');
			CURRENT_DOM.focus();
			alert("Employee details doesnot exist ");
		}
	}else{
		createEmptyDropDown('employeeId');
		CURRENT_DOM.focus();
		alert("Employee details doesnot exist ");
		
	}
	CURRENT_DOM=null;

}

function updateComplaintTransfer(){
	var empDom=getDomElementById('employeeId');
	var empEmailDom=getDomElementById('empEmailId');
	var empMobileDom=getDomElementById('empPhone');
	
	if(isNull(empDom.value)){
		alert("Please select Employee Details");
		setFocusOnElmentByDom(empDom);
		return false;
	}
	if(isNull(empEmailDom.value)){
		alert(" Employee Email address is mandatory");
		setFocusOnElmentByDom(empEmailDom);
		return false;
	}
	if(isNull(empMobileDom.value)){
		alert("Employee Mobile number  is mandatory ");
		setFocusOnElmentByDom(empMobileDom);
		return false;
	}
	
	if(confirm("Do you want to update the details ?")){
		var url="./serviceRequestForService.do?submitName=updateServiceTransferDetails";
		ajaxJquery(url,FORM_ID,ajaxResponseAfterComplaintTransfer);
	}
}

function ajaxResponseAfterComplaintTransfer(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			alert(success);
			screenClose();
		}
	}else{
		alert(" Error in saving ");
	}
}
