//common
var rowCount=1;
var CURR_EMAIL="";
var CURR_SEARCHED_EMP="";
var EXISTING_EMAIL="";
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
function showBtn(btnId){
	jQuery("#"+btnId).show();
	
}
function hideBtn(btnId){
	jQuery("#"+btnId).hide();
}
function enableBtn(btnId){
	
	//jQuery("#"+btnId).attr("disabled", "");
	jQuery("#"+btnId).attr("disabled", false);
	jQuery("#" + btnId).removeClass("btnintformbigdis");
	jQuery("#" + btnId).addClass("btnintform");
}

function disableBtn(btnId){
	
	jQuery("#"+btnId).attr("disabled", "disabled");
	jQuery("#" + btnId).removeClass("btnintform");
	jQuery("#" + btnId).addClass("btnintformbigdis");
	//jQuery("#"+btnId).css("background-color", "grey");
}

function disableBtns(btnId){
	
	jQuery("#"+btnId).attr("disabled", "disabled");
	jQuery("#" + btnId).removeClass("btnintformbig1");
	jQuery("#" + btnId).addClass("btnintformbigdis");
	//jQuery("#"+btnId).css("background-color", "grey");
}

function enableBtns(btnId){
	
	//jQuery("#"+btnId).attr("disabled", "");
	jQuery("#"+btnId).attr("disabled", false);
	jQuery("#" + btnId).removeClass("btnintformbigdis");
	jQuery("#" + btnId).addClass("btnintformbig1");
}

function fnClickAddRow() {
	enableBtn("Save");
           	$('#loginIdTable').dataTable().fnAddData( [
           '<input type="checkbox" id="isLoginChkd'+ rowCount +'" name="chkBoxName" value="" hide="true" onclick="enableDisableFunctionalty(this)" />',
           rowCount,
           '<input type="text" styleClass="txtbox" id="userName'+ rowCount +'" name="userNames"  tabindex="-1"  />',
           '<input type="text" id="status'+ rowCount +'" name="status"  tabindex="-1" disabled="true"/>',
           
                 ] );
           	rowCount++;
           }

function isChecked(obj,count){
	if(obj.checked){
		document.getElementById("isLoginChkd"+count).value="Y";
	}
	else{
		document.getElementById("isLoginChkd"+count).value="N";
	}
}


function validateFields() {
	var isValid = true;
	var checkBoxes = document.createEmployeeLoginForm.isLoginChkd;
	var isSelected = false;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		if (document.getElementById('isLoginChkd' + j).checked) {
			isSelected = true;
		}
	}
	if (!isSelected) {
		alert("Please select atleast 1 Login ID.");
		isValid = false;
	}

	return isValid;
}




//customer Related 
function searchCustomer(){
	var cName = document.getElementById("businessName").value;
    if(isNull(cName))
	{
	alert('Customer Name should not be blank');
	custFormClear();
	}else{
		var custName=cName.replace('&','-');
		var customerName=custName.replace('%','Percent');
		var url = './createCustomerLogin.do?submitName=searchCust&cName='+customerName;
	createUserAjaxCall(url,"createCustomerLoginForm",searchCustomerResponse);
	}
}

function createUserAjaxCall(url,formId,ajaxResponse){
		jQuery.ajax({
			url: url,
			data:jQuery("#"+formId).serialize(),
			context: document.body,
			success: function (data){
				ajaxResponse(data);
			},
			error: function (data, err) {
			}
		});
	
}

function searchCustomerResponse(data){
	if(checkNull(data) || data == 'ERROR'){
		alert("Customer does not exist with this name");
		custFormClear();
		//jQuery.unblockUI();
	}
	else{
	var customerDetails = eval('(' + data + ')');
	jQuery("#customerCode").val(customerDetails.custTO.customerCode);
	jQuery("#custName").val(customerDetails.custTO.businessName);
	var email=customerDetails.custTO.email;
	if(isNull(email)){
		jQuery("#email").prop('readonly', false);
		jQuery("#email").val("");
	}
	else
	{	jQuery("#email").val(email);}
	
	
	//jQuery("#email").val(customerDetails.custTO.email);
	jQuery("#mobile").val(customerDetails.custTO.mobile);
	//jQuery("#city").val(customerDetails.custTO.mappedOffice);
	jQuery("#city").val(customerDetails.custTO.city);
	jQuery("#customerType").val(customerDetails.custTO.customerTypeTO.customerTypeDesc);
	jQuery("#customerId").val(customerDetails.custTO.customerId);
	jQuery("#active").val(customerDetails.userTO.active);
	var assignedStatus=jQuery("#active").val();
	if(assignedStatus==""){
		enableBtn("Save");
		disableBtns("resetBtn");
		disableBtns("deactivate");
		disableBtn("activate");
	}else if(assignedStatus!=""){
	/*	disableBtn("Save");
		enableBtn("resetBtn");
		enableBtn("deactivate");
		enableBtn("activate");*/
	}
	
	if(assignedStatus== 'de-active'){
		disableBtns("resetBtn");
		disableBtns("deactivate");
		disableBtn("Save");
		enableBtn("activate");
	}
	if(assignedStatus== 'active'){
		enableBtns("resetBtn");
		enableBtns("deactivate");
		disableBtn("Save");
		disableBtn("activate");
	}
	jQuery("#userName").val(customerDetails.custTO.customerCode);
	if(customerDetails.userTO.active == 'active')
	{
		/*disableBtn("activate");
		disableBtn("Save");*/
	}
}
	
}


function saveOrUpdateCustomerLogin(){
	if(validateCustomerFields()){
	var custName = document.getElementById("businessName").value;
	//showProcessing();
	url = './createCustomerLogin.do?submitName=createCustLogin';
	jQuery.ajax({
		url: url,
		data:jQuery("#createCustomerLoginForm").serialize(),
		success: function(req){printCallBackCustSave(req,custName);}
	});
	}
}

function printCallBackCustSave(ajaxResp,userName){
	jQuery.unblockUI();
	//alert("ajaxResp"+ajaxResp);
	
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			alert(success);
			jQuery("#email").prop('readonly', 'readOnly');
			jQuery("#active").val('de-active');
			disableBtn("Save");
			disableBtn("deactivate");
			disableBtn("resetBtn");
			enableBtn("activate");
		}
	}else{
		alert("Invalid results");
	}
	
	jQuery.unblockUI();
}

function activateDeactivateCustomerLogin(status){
	
	var eName = document.getElementById("businessName").value;
	var emailVal=jQuery('#email').val();
	if(eName=="")
	{
		alert("Please enter Customer Name ");
	}
	else if (isNull(emailVal) && status=='active')
		{
		alert("Cannot activate user with no Email ID");
		jQuery('#email').focus();
		
		}
	else{
	if(validateCustomerFields()){
		showProcessing();
		var statusVal=jQuery("#active").val();
		url = './createCustomerLogin.do?submitName=activateDeactivateCustLogin&status='+status;
		jQuery.ajax({
			url: url,
			data:jQuery("#createCustomerLoginForm").serialize(),
			success: function(req){
				hideProcessing();
				printCallBackCustStatusChange(req,status);
				}
		});
		
	}}
}


function printCallBackCustStatusChange(data,status){
	var response = data;
	if(isNull(response)){
		alert("Error Occurred");
		return ;
	}
	
	var responseText =""; 
	try{
		responseText =jsonJqueryParser(data);
	}catch(e){
		responseText= eval('(' + data + ')');
	}
	var error = responseText["ERROR"];
	var success = responseText["SUCCESS"];
	if(responseText!=null && error!=null){
		alert(error);
		//document.getElementById("empName").value="";
		return;
	}else if(responseText!=null && !isNull(success)){
		alert(success);
		if(status=='active'){
			document.getElementById("active").value = status;
			//resetPassword("activate");
			disableBtn("activate");
			enableBtns("deactivate");
			enableBtns("resetBtn");
		}
		else if(status=='de-active'){
			//alert("Login ID  deactivated successfully");
			document.getElementById("active").value = status;
			disableBtns("deactivate");
			disableBtns("resetBtn");
			enableBtn("activate");
        }
	}else{
		alert("Error occurred");
	}
	//custFormClear();
	//jQuery.unblockUI();
}

function custFormClear(){
	jQuery("#customerCode").val("");
	jQuery("#custName").val("");
	jQuery("#email").val("");
	jQuery("#mobile").val("");
	jQuery("#city").val("");
	jQuery("#customerType").val("");
	jQuery("#active").val("");
	jQuery("#userName").val("");
	jQuery("#customerId").val("");
	
}

 
function showProcessing(){
	//jQuery.blockUI( {message : '<img src="../../images/loading_animation.gif"/>'}); 
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}


//Employee Related 
function searchEmployee(){
	emailChange=false;
	var eName = document.getElementById("empName").value;
	if(eName=="")
	{
		alert("Please enter Employee Name");
	}else if(eName==CURR_SEARCHED_EMP){
		
	} else{
	var url = './createEmployeeLogin.do?submitName=searchEmp&eName='+eName;
	//alert(url);
	ajaxJquery(url,"createEmployeeLoginForm",searchEmployeeResponse);
	}
}


function searchEmployeeResponse(data){
	jQuery.unblockUI();
	$('#loginIdTable').dataTable().fnClearTable();
	rowCount=1;
	clearEmployee();
	hideBtn('add');
	if(isNull(data))
	{
		alert("Employee does not exist with this name");
		document.getElementById("empName").value="";
		return;
	}
	else{
		
		var responseText =jsonJqueryParser(data); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			document.getElementById("empName").value="";
			return;
		}
				
		
	//var empDetails = eval('(' + data + ')');	
		var empDetails =responseText;	
	
	jQuery("#empCode").val(empDetails.empTO.empCode);
	jQuery("#firstName").val(empDetails.empTO.firstName);
	jQuery("#lastName").val(empDetails.empTO.lastName);
	var email=empDetails.empTO.emailId;
	if(isNull(email)){
		jQuery("#email").attr('readonly', false);
	}
	else
	{	
		jQuery("#email").val(email);
	}
	EXISTING_EMAIL=email;
	jQuery("#mobile").val(empDetails.empTO.empPhone);
	jQuery("#designation").val(empDetails.empTO.designation);
	jQuery("#officeID").val(empDetails.empTO.officeName);
	jQuery("#employeeId").val(empDetails.empTO.employeeId);
	
	CURR_SEARCHED_EMP=empDetails.empTO.firstName;
	var loginList=empDetails.userToList;
	if(!checkNull(loginList))
	{
		 listLength = loginList.length;
	
	if(listLength >0)
	{
		fnClickAddRow();
		for(var i=0;i<listLength;i++)
		{
			var listVal = empDetails.userToList[i];
			jQuery("#userName"+(i+1)).val(listVal.userName);
			jQuery("#userName"+(i+1)).attr('readonly','readonly');
			if(listVal.active=="Y"){
			   jQuery("#status"+(i+1)).val("active");
			}
			else{
				jQuery("#status"+(i+1)).val("de-active");
			}
			if((i+1)<listLength){
			fnClickAddRow();
			}
		}
	}
	}else{
		fnClickAddRow();
		disableBtns("de-active");
		disableBtns("resetPawsd");
		disableBtn("active");
		disableBtn("modify");
		enableBtn("Save");
	}
	
}
}

function modifyEmployeeLogin(){
	enableBtn("Save");
	var eName = document.getElementById("empName").value;
	CURR_EMAIL = document.getElementById("email").value;
	
	if(eName=="")
	{
		alert("Please enter Employee Name");
	}else{
	showBtn("add");
	jQuery("#email").attr('readonly', false);
	}

}

function getEmpID(empName){
	var id=0;
	if(!checkNull(empIDArr)){
		for(var i=0;i<empIDArr.length;i++){
			var nameID=empIDArr[i];
			var nameIDArr=nameID.split("-");
			
			if(nameIDArr[1]==empName){
				id=nameIDArr[0];
				break;
			}
		}
	}
	return id;
}
function saveOrUpdateEmployeeLogin(){
	var userNameEmpty="false";
	var flag="false";
	var eName = document.getElementById("empName").value;
	var modifiedEmail = document.getElementById("email").value;
	
	var selectedUsers="";
	var empID=0;
	if(isNull(eName))
	{
		alert("Please enter Employee Name");
		jQuery("#empName").focus();
	}
	else if(isNull(modifiedEmail)){
		alert("Please enter Email ID before saving an user");
		jQuery("#email").focus();
	}
	else if(validateEmail(modifiedEmail)){
		empID= jQuery("#employeeId").val();
		//empID=getEmpID(eName);
	
		jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
			
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if(isChecked) {
				flag="true";
				var selectedName = jQuery("#userName"+(i+1)).val();
				if(isNull(selectedName)){
					userNameEmpty="true";
				}
				selectedUsers = selectedUsers + selectedName +",";
			}
		});
		if((flag=="false"||userNameEmpty=="true") && (!emailChange)){
			alert('Atleast 1 LoginID should be selected and should not be empty');
		}else{
	var fName = document.getElementById("firstName").value;
	showProcessing();
	if(EXISTING_EMAIL != modifiedEmail)
	{
		if(emailChange&&(selectedUsers=="")){
			url = './createEmployeeLogin.do?submitName=saveUpdateEmpLogin&empID='+empID+"&emailID="+modifiedEmail+"&onlyEmail=onlyEmail";
		}else{
			url = './createEmployeeLogin.do?submitName=saveUpdateEmpLogin&selectedUsers='+selectedUsers+"&empID="+empID+"&emailID="+modifiedEmail;
		}
	}else{
		url = './createEmployeeLogin.do?submitName=saveUpdateEmpLogin&selectedUsers='+selectedUsers+"&empID="+empID;	
	}
	
	ajaxJqueryWithRow(url,"createEmployeeLoginForm",printCallBackEmpSave,fName);
	/*jQuery.ajax({
		url: url,
		data:jQuery("#createEmployeeLoginForm").serialize(),
		success: function(req){printCallBackEmpSave(req,fName);}
	});*/
		}
		}
	
}

function printCallBackEmpSave(data,userName){
	
	jQuery.unblockUI();
	jQuery("#email").attr('readonly', true);
	
	/*****/
	var modifiedEmail = document.getElementById("email").value;
	
	
	if(isNull(data)){
		alert("Error occurred");
		return ;
	}
		var responseText =""; 
		try{
			responseText =jsonJqueryParser(data);
		}catch(e){
			responseText= eval('(' + data + ')');
		}
		
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			document.getElementById("empName").value="";
			empFormClear();
			jQuery.unblockUI();
			return ;
		}else if(responseText!=null && !isNull(success)){
			alert(success);

			/*if(CURR_EMAIL != modifiedEmail){
				//alert("Saved successfully.");
				disableBtn("Save");
			}*/
			disableBtn("Save");
				jQuery("#email").attr('readonly', true);
				jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
					var isChecked = jQuery(this).find('input:checkbox').is(':checked');
					if(isChecked) {
							jQuery("#status"+(i+1)).val('de-active');
					}
				});
				//disableBtn("Save");
				enableBtn("active");
				jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
					var isChecked = jQuery(this).find('input:checkbox').is(':checked');
					if(isChecked) {
						var selectedName = jQuery("#userName"+(i+1)).val();
						jQuery("#userName"+(i+1)).attr("disabled", "disabled");
					}
				});
				
		
		}else{
			alert("Error occurred");
		}
		
	
	jQuery.unblockUI();
} 


function empFormClear(){
	jQuery("#empCode").val("");
	jQuery("#firstName").val("");
	jQuery("#lastName").val("");
	jQuery("#email").val("");
	jQuery("#mobile").val("");
	jQuery("#designation").val("");
	jQuery("#status").val("");
	jQuery("#officeID").val("");
	jQuery("#employeeId").val("");
	var newRowCount= (rowCount-1);
	jQuery("#userName"+ newRowCount).val("");
	document.getElementById("#isLoginChkd"+ newRowCount).checked=false
}

function activateDeactivateEmployeeLogin(status){
	var checkLoginID="false";
	var flag="false";
	var userStatus="";
	var sameStatusBtnClik="false";
	var activeFlag="false";
	var eName = document.getElementById("empName").value;
	var emailVal=jQuery('#email').val();
	if(eName=="")
	{
		alert("Please enter Employee Name ");
	}
	else if (isNull(emailVal) && status=='active')
		{
		alert("Cannot activate user with no Email ID");
		jQuery('#email').focus();
		
		}
	
	else{
		jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
			
		var isChecked = jQuery(this).find('input:checkbox').is(':checked');
		if(isChecked) {
			
			flag="true";
			var selectedName = jQuery("#userName"+(i+1)).val();
			if(selectedName==""){
				checkLoginID="true";
				status="";
			  }
			
			else{
				var selectedStatus = jQuery("#status"+(i+1)).val();
				userStatus=userStatus+selectedName+"~"+status+",";
					jQuery("#status"+(i+1)).val(status);
					if(selectedStatus=="active"){
						activeFlag=false;
						disableBtns("resetPawsd");
						enableBtn("active");
					}
					else if(selectedStatus=="de-active"){
						activeFlag=true;
						disableBtns("resetPawsd");
					}
			}
		}
	});
		if(checkLoginID=="true"||flag=="false"){
			alert('Atleast 1 Login ID should be selected and should not be blank');
		}
		else{ 
				url = './createEmployeeLogin.do?submitName=activateDeactivateEmpLogin&userStatus='+userStatus;
				ajaxJqueryWithRow(url,"createEmployeeLoginForm",printCallBackEmpStatusChange,status);
			
		}//else
		}
	
	}	

function printCallBackEmpStatusChange(data,status){
	jQuery.unblockUI();
	if(isNull(data)){
		alert("Error Occurred");
		return ;
	}
		var responseText =""; 
		try{
			responseText =jsonJqueryParser(data);
		}catch(e){
			responseText= eval('(' + data + ')');
		}
		
		var error = responseText["ERROR"];
		var success = responseText["SUCCESS"];
		if(responseText!=null && error!=null){
			alert(error);
			document.getElementById("empName").value="";
			return;
		}else if(responseText!=null && !isNull(success)){
			alert(success);
			if(status=='active'){
				disableBtn("active");
				enableBtns("resetPawsd");
				enableBtns("de-active");
			}
			else if(status=='de-active'){
				disableBtns("de-active");
				enableBtn("active");
			}
		}else{
			alert("Error occurred");
		}
		//alert("Error occured during activate/deactive of user");
	
	jQuery.unblockUI();
}

function validateCustomerFields() {
	var emailValue = document.getElementById("email").value;
	if(document.getElementById("businessName").value == "")
		{
		alert('CustomerName should not be blank');
		return false;
		}else if(isNull(emailValue)){
			alert("Please enter valid Email ID before saving an user");
			jQuery("#email").prop('readonly', false);
			jQuery("#email").focus();
			return false;
		}
		else if(!validateEmail(emailValue)){
			
			return false;
		}
	return true;
}


function resetPassword(opName) {
	if(document.getElementById("businessName").value == "")
	{
	alert('CustomerName should not be blank');
	}
	else{
	var url = './createCustomerLogin.do?submitName=resetPassword&opName='+opName;
	//document.createCustomerLoginForm.action=url;
    //document.createCustomerLoginForm.submit();
    ajaxJqueryWithRow(url,"createCustomerLoginForm",printCallBackResetCustPaswd,opName);//added by niharika on 17 july
	}
}

function printCallBackResetCustPaswd(result){
    //jQuery.unblockUI();
    if(!isNull(result)){
    	 responseText= jsonJqueryParser(result);
    	var error = responseText["ERROR"];
		var success = responseText["SUCCESS"];
		if(responseText!=null && error!=null){
			hideProcessing();
			alert(error);
			return false;
		}else if(responseText!=null && !isNull(success)){
			hideProcessing();
			alert(success);
			return true;
			
		}
         
    }
}

function resetEmpPassword(operationName) {
	
	var emailVal=jQuery('#email').val();
    var flag="false";
    var empUserName="";
    var eName = document.getElementById("empName").value;
    if(eName=="")
    {
          alert("Please enter Employee Name ");
    }else if(isNull(emailVal)){
		alert("Please provide a valid Email ID");
		jQuery('#email').focus();
		
	}else{
        jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
                
                var isChecked = jQuery(this).find('input:checkbox').is(':checked');
                if(isChecked) {
                      flag="true";
                      var selectedName = jQuery("#userName"+(i+1)).val();
                      var selectedStatus = jQuery("#status"+(i+1)).val();
                      empUserName=empUserName+selectedName+",";
                      
                }
                
          });
        
        if(flag=="false"){
                alert('Please select atleast 1 Login ID.');
        }
        else{
        var url = './createEmployeeLogin.do?submitName=resetPassword&empUserName='+empUserName+"&operationName="+operationName;
          //document.createEmployeeLoginForm.action=url;
        //document.createEmployeeLoginForm.submit();
        ajaxJqueryWithRow(url,"createEmployeeLoginForm",printCallBackResetEmpPaswd,operationName);//added by niharika on 17 july
          }
    }
    }

//added by niharika on 17 july
function printCallBackResetEmpPaswd(result){
    //jQuery.unblockUI();
    if(!isNull(result)){
    	 responseText= jsonJqueryParser(result);
    	var error = responseText["ERROR"];
		var success = responseText["SUCCESS"];
		if(responseText!=null && error!=null){
			alert(error);
			return false;
		}else if(responseText!=null && !isNull(success)){
			alert(success);
			return true;
		}
    }
}




/*function resetEmpPassword(operationName) {
	var flag="false";
	var empUserName="";
	var eName = document.getElementById("empName").value;
	if(eName=="")
	{
		alert("Please enter Employee Name ");
	}else{
	    jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
			
			var isChecked = jQuery(this).find('input:checkbox').is(':checked');
			if(isChecked) {
				flag="true";
				var selectedName = jQuery("#userName"+(i+1)).val();
				var selectedStatus = jQuery("#status"+(i+1)).val();
				empUserName=empUserName+selectedName+",";
				
			}
			
		});
	    
	    if(flag=="false"){
			alert('Please select atleast 1 Login ID.');
	    }
	    else{
	    var url = './createEmployeeLogin.do?submitName=resetPassword&empUserName='+empUserName+"&operationName="+operationName;
		document.createEmployeeLoginForm.action=url;
	    document.createEmployeeLoginForm.submit();
		}
	}
	}*/

function isEmptyEmpLoginID(){
jQuery('#loginIdTable >tbody >tr').each(function(i, tr) {
	var isChecked = jQuery(this).find('input:checkbox').is(':checked');
	if(!isChecked) {
		var selectedName = jQuery("#userName"+(i+1)).val();
		if(selectedName==""){
			alert("Please enter the Login id");
			return false;
		}
		else{
			return true;
		}
	}
});


}

function editSaveFunctionalty(obj){
	if(this.value!="")
	enableBtn("Save");
}


function enableDisableFunctionalty(obj){
	var selectedID=obj.id
	var selectedIDArr=selectedID.split("isLoginChkd");
	if(document.getElementById("status"+selectedIDArr[1]).value == "active"){
		disableBtn("Save");
		disableBtn("active");
		enableBtns("resetPawsd");
		enableBtns("de-active");
	}else if(document.getElementById("status"+selectedIDArr[1]).value == "de-active"){
		disableBtn("Save");
		disableBtns("de-active");
		disableBtns("resetPawsd");
		enableBtn("active");
	}else if(document.getElementById("status"+selectedIDArr[1]).value == ""){
		//disableBtn("Save");
		disableBtns("de-active");
		disableBtns("resetPawsd");
		disableBtn("active");
	}
	
}

var emailChange=false;
function handleEmailChange(){
	emailChange=true;
	enableBtn("Save");
}



	
function clearEmployee(){
	jQuery("#empCode").val('');
	jQuery("#firstName").val('');
	jQuery("#lastName").val('');
	jQuery("#email").val('');
	jQuery("#mobile").val('');
	jQuery("#designation").val('');
	jQuery("#officeID").val('');
	jQuery("#employeeId").val('');
}

function getAllCitiesByRegion(domElemnt){
	var regionId=jQuery("#regionId").val();
	var url = './createEmployeeLogin.do?submitName=getCitiesByRegion&regionId='+regionId;
//	document.createEmployeeLoginForm.action=url;
//    document.createEmployeeLoginForm.submit();
	createUserAjaxCall(url,"createEmployeeLoginForm",populateCity);
}

function getAllCitiesByRegionForCust(domElemnt){
	var regionId=jQuery("#regionId").val();
	var url = './createCustomerLogin.do?submitName=getCitiesByRegionForCust&regionId='+regionId;
//	document.createEmployeeLoginForm.action=url;
//    document.createEmployeeLoginForm.submit();
	createUserAjaxCall(url,"createCustomerLoginForm",populateCityForCust);
}


function printAllCities(data) {
	var response = data;
	if (!isNull(response)) {
		var content = document.getElementById('cityId');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		// createOnlyAllOptionWithValue('destCity', "0");
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});

	}

	else {
		showDropDownBySelected("regionId", "");
		alert("No cities found for the selected region");
		document.getElementById('regionId').focus();
	}

	hideProcessing();

}

function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}


function populateCity(resp) {
	stationList = jsonJqueryParser(resp);
	clearDropDownList("cityId");
	obj = getDomElementById("cityId");
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown("cityId", stationList[i].cityName,
					stationList[i].cityId);
		}
		obj.focus();
	}

}



function populateCityForCust(resp) {
	stationList = jsonJqueryParser(resp);
	clearDropDownList("cityId");
	obj = getDomElementById("cityId");
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown("cityId", stationList[i].cityName,
					stationList[i].cityId);
		}
		obj.focus();
	}

}

/**
 * It deletes the Dropdown values of the fields
 * 
 * @param selectId
 */
function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "----Select----", "");
}

/**
 * It adds the values to dropdown
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}


function getEmpByCity(domElement){
	var cityId = getDomElementById("cityId").value;
	 
	url="./createEmployeeLogin.do?submitName=getEmpByCity&cityId="+cityId;
	  createUserAjaxCall(url,"createEmployeeLoginForm",populateEmployee);
	  showProcessing();
}

function populateEmployee(empData){
	if(!isNull(empData)){
		 responseText= jsonJqueryParser(empData);
	    	var error = responseText["ERROR"];
			
			if(responseText!=null && error!=null){
				hideProcessing();
				alert(error);
				
			}else {
				var emp = responseText["SUCCESS"];
				jQuery('input#empName').flushCache();
				var data = new Array();
				
				for(i=0;i<emp.length;i++ ){
				 data[i] = emp[i].firstName+"-"+emp[i].lastName+"~"+emp[i].empCode;
				 empIDArr[i] = emp[i].employeeId;
				}
				
				jQuery("#empName").autocomplete(data);
				
				hideProcessing();
			}
	}
	empObj = getDomElementById("empName");
	empObj.focus();
}




function getCustByCity(domElement){
	var cityId = getDomElementById("cityId").value;
	 
	url="./createCustomerLogin.do?submitName=getCustByCity&cityId="+cityId;
	  createUserAjaxCall(url,"createCustomerLoginForm",populateCustomer);
	  showProcessing();
}


function populateCustomer(custData){

	 if(!isNull(custData)){
		 responseText= jsonJqueryParser(custData);
	    	var error = responseText["ERROR"];
			
			if(responseText!=null && error!=null){
				hideProcessing();
				alert(error);
				
			}else {
				var cust = responseText["SUCCESS"];
				jQuery('input#businessName').flushCache();
				hideProcessing();
				jQuery("#businessName").autocomplete(cust);
			}
	    
	/*jQuery('input#businessName').flushCache();
	var data = new Array();
	var cust=jsonJqueryParser(custData);
	for(i=0;i<cust.length;i++ ){
	 data[i] = cust[i].businessName+"~"+cust[i].customerCode;
	}
	jQuery("#businessName").autocomplete(data);*/
	
	}
	 custObj=getDomElementById("businessName");
	 custObj.focus();
	 hideProcessing();
}

