var isRoleUpdation="N";
/*function saveOrUpdateUserRoles() {
	document.getElementById("saveButton").blur();
	if (validateFields()) {
		var roleType = document.getElementById("roleType").value;
		showProcessing();
		url = './userManagementRoles.do?submitName=saveOrUpdateUserRoles';
		jQuery.ajax({
			url : url,
			data : jQuery("#userManagementRolesForm").serialize(),
			success : function(req) {
				printCallBackSave(req, roleType);
			}
		});
	}
}

function printCallBackSave(data, roleType) {
	var response = data;
	if (response != null && response == 'success') {
		if (roleType == 'C'){
			if(isRoleUpdation =="Y")
				alert("Customer role updated successfully.");
			else 
				alert("Customer role created successfully.");
		}
			
		else if (roleType == 'E'){
			if(isRoleUpdation =="Y")
				alert("FFCL role updated successfully.");
			else 
				alert("FFCL role created successfully.");
		}
			
	} else {
		alert("Problem occurred. Role not created successfully.");
	}
	cancel();
	jQuery.unblockUI();
}*/

//Submitng the pahe
function saveOrUpdateUserRoles() {
	if (validateFields()) {
		showProcessing();
		//document.getElementById("roleType").value = roleType;
		url = './userManagementRoles.do?submitName=saveOrUpdateUserRoles';
		document.userManagementRolesForm.action = url;
		document.userManagementRolesForm.submit();
	}
}

function activateDeactivateUserRole(status) {
	document.getElementById("status").value = status;
	var roleType = document.getElementById("roleType").value;
	var userRole = document.getElementById('roleId').value;
	
	if (userRole == null || userRole == "" ) {
		alert("Please select any right for the role.");
		setTimeout(function() {
			document.getElementById("roleId").focus();
		}, 10);
	} else {
	showProcessing();
		url = './userManagementRoles.do?submitName=activateDeactivateUserRole';
		jQuery.ajax({
			url : url,
			data : jQuery("#userManagementRolesForm").serialize(),
			type: "POST",
			success : function(req) {
				printCallBackStatusChange(req, roleType, status);
			}
		,
		error: function( xhr, ajaxOptions, thrownError ) {
			jQuery.unblockUI();
		}
		
		});
		
	}
}

function printCallBackStatusChange(data, roleType, status) {
	var response = data;
	if (response != null && response == 'success') {
		if (roleType == 'C') {
			if (status == 'A')
				alert("Customer role activated successfully.");
		
			else if (status == 'I')
				alert("Customer role Deactivated successfully.");
		}

		else if (roleType == 'E') {
			if (status == 'A')
				alert("FFCL role activated successfully.");
			else if (status == 'I')
				alert("FFCL role Deactivated successfully.");
		}
	} else  {
		var userDtls = new Array();
		userDtls = response.split("#");
		if (userDtls != null && userDtls[0] == 'ASSIGNED') {
			if (roleType == 'C') {
				alert("Customer Role assigned to user(s) ( "+userDtls[1]+ " ) hence cannot deactivate.");
			}
			if (roleType == 'E') {
				alert("FFCL Role assigned to user(s) ( "+userDtls[1]+ " ) hence cannot deactivate.");
			}	
		}
	}
	
	cancel();
	jQuery.unblockUI();
}

function getUserRolesAndRights(roleType) {
	showProcessing();
	document.getElementById("roleType").value = roleType;
	url = './userManagementRoles.do?submitName=addUserRoles';
	document.userManagementRolesForm.action = url;
	document.userManagementRolesForm.submit();
	
	// Making ajax call 
}

function cancel() {
	url = './userManagementRoles.do?submitName=addUserRoles';
	document.userManagementRolesForm.action = url;
	document.userManagementRolesForm.submit();
}

function getUserRoles(roleType) {
	// showProcessing();
	

	url = './userManagementRoles.do?submitName=getUserRoles&roleType='
			+ roleType;
	jQuery.ajax({
		url : url,
		success : function(req) {
			displayUserRoles(req);
		}
	});
}

function displayUserRoles(data) {
	
	var response = data;
	var roles = document.getElementById('roleId');
	roles.innerHTML = "";
	if (response != null && response != 'NOROLES') {
		var option;
		option = document.createElement("option");
		option.value = "N";
		option.description = "New";
		option.appendChild(document.createTextNode("New"));
		roles.appendChild(option);

		var rolesDetails = eval('(' + response + ')');
		if (rolesDetails != null && rolesDetails != "") {
			var noOfRoles = rolesDetails.length;
			for (i = 0; i < noOfRoles; i++) {
				option = document.createElement("option");
				option.value = rolesDetails[i].roleId;
				option.description = rolesDetails[i].roneName;
				option.appendChild(document
						.createTextNode(rolesDetails[i].roleName));
			}
			roles.appendChild(option);
		}

	}
	// jQuery.unblockUI();
}

function getUserRights(value) {
	
	
	if(document.getElementById("roleId").value="New"){
		var checkBoxes = document.userManagementRolesForm.rightIdChkBox;
		for ( var j = 1; j <= checkBoxes.length; j++) {
				
				document.getElementById('isRightAssigned' + j).value = "N";
				document.getElementById('rightIdChkBox' + j).checked=false;
		}
	}
	// showProcessing();
	rightsChkEnable();
	
	document.getElementById("saveButton").disabled = false;
	
	if (value == "N") {
		document.getElementById("roleName").readOnly = false;
		document.getElementById("roleDesc").readOnly = false;
		document.getElementById("roleName").value = "";
		document.getElementById("roleDesc").value = "";
	} else {
		document.getElementById("roleName").readOnly = true;
		document.getElementById("roleDesc").readOnly = true;
		url = './userManagementRoles.do?submitName=getUserRights&userRoleId='
				+ value;
		jQuery.ajax({
			url : url,
			success : function(req) {
				displayUserRights(req);
			}
		});
	}
}

function displayUserRights(data) {
	var response = data;
	if (response != null && response != 'NOUSERRIGHTS' && response!="[]") {
		document.getElementById("saveButton").disabled = true;
		var rightsDetails = eval('(' + response + ')');
		if (rightsDetails != null && rightsDetails != "") {
			var noOfRights = rightsDetails.length;
			document.getElementById("roleId").value=rightsDetails[0].userRoleId;
			document.getElementById("roleName").value = rightsDetails[0].userRoleName;
			document.getElementById("roleDesc").value = rightsDetails[0].userRoleDesc;
			
			
			var checkBoxes = document.getElementsByName("rightIdChkBox");
			for ( var j = 1; j <= checkBoxes.length; j++) {
					document.getElementById('rightIdChkBox' + j).checked = false;
					document.getElementById('isRightAssigned' + j).value = "N";
			}

			
			
			for ( var i = 0; i < noOfRights; i++) {
				// Checking for right for role
				for ( var j = 1; j <= checkBoxes.length; j++) {
					if (rightsDetails[i].screenId == document
							.getElementById('rightIdChkBox' + j).value) {
						document.getElementById("userRightIds" + j).value = rightsDetails[i].rightsId;
						document.getElementById('rightIdChkBox' + j).checked = true;
						document.getElementById('isRightAssigned' + j).value = "Y";
					}
					document.getElementById('rightIdChkBox' + j).disabled = true;
				}

			}
		}

		// jQuery.unblockUI();
	}else{
		//document.getElementById("saveButton").disabled = true;
		
		document.getElementById("roleName").value = "";
		document.getElementById("roleDesc").value = "";
		
		var checkBoxes = document.userManagementRolesForm.rightIdChkBox;
		for ( var j = 1; j <= checkBoxes.length; j++) {
				document.getElementById('rightIdChkBox' + j).checked = false;
				document.getElementById('isRightAssigned' + j).value = "N";
		}
	}
}

function rightsChkEnable() {
	var checkBoxes = document.userManagementRolesForm.rightIdChkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		document.getElementById('rightIdChkBox' + j).disabled = false;
		document.getElementById('rightIdChkBox' + j).checked = false;
	}

}
function rightsChkEnable4Edit() {
	isRoleUpdation = "Y";
	document.getElementById("isUpdationMode").value="Y"; 
	document.getElementById("saveButton").disabled = false;
	
	var userRole = document.getElementById("roleId").value;
	
	if ( userRole== "N") {
		alert("Please select the role.");
		setTimeout(function() {
			document.getElementById("roleId").focus();
		}, 10);
	} 
	else {
	alert("Please Modify the Roles.");
			}
	document.getElementById("modifyButton").blur();
	var checkBoxes = document.userManagementRolesForm.rightIdChkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		document.getElementById('rightIdChkBox' + j).disabled = false;
		// document.getElementsByName('rightIdChkBox')[j].disabled=false;
	}

}

function isChecked(obj, count) {
	if (obj.checked) {
		document.getElementById("isRightAssigned" + count).value = "Y";
	} else {
		document.getElementById("isRightAssigned" + count).value = "N";
	}
}

function validateFields() {
	var isValid = true;
	if (document.getElementById('ffcl').checked == false) {
		if (document.getElementById('cust').checked == false) {
			alert("Please select user role.");
			isValid = false;
			return isValid;
		}
	} else if (document.getElementById('cust').checked == false) {
		if (document.getElementById('ffcl').checked == false) {
			alert("Please select user role.");
			isValid = false;
			return isValid;
		}
	}
	var userRole = document.getElementById('roleId').value;
	if (userRole == 'N') {
		var roleName = document.getElementById('roleName').value;
		if (roleName == null || roleName == "") {
			alert("Please enter role name.");
			setTimeout(function() {
				document.getElementById("roleName").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
		
		
		var roleDesc = document.getElementById('roleDesc').value;
		if (roleDesc == null || roleDesc == "") {
			alert("Please enter role description.");
			setTimeout(function() {
				document.getElementById("roleDesc").focus();
			}, 10);
			
			isValid = false;
			return isValid;
		}
		
	}
	//var checkBoxes = document.userManagementRolesForm.rightIdChkBox;
	var checkBoxes = document.getElementsByName("rightIdChkBox");
	var isSelected = false;
	for ( var j = 0; j <= checkBoxes.length; j++) {
		if(checkBoxes[j].checked){
			isSelected = true;
			break;
		}
		/*if (document.getElementById('rightIdChkBox' + j).checked) {
			isSelected = true;
		}*/
	}
	if (!isSelected) {
		alert("Please select any right(s) for the role.");
		isValid = false;
		return isValid;
	}

	return isValid;
}

function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}
function imposeMaxLength(textBox, e, length) {

    if (!checkSpecialKeys(e)) {
        if (textBox.value.length > length - 1) {
        		return false;
            }
}

function checkSpecialKeys(e) {
    if (e.keyCode != 8 && e.keyCode != 46 && e.keyCode != 35 && e.keyCode != 36 && e.keyCode != 37 && e.keyCode != 38 && e.keyCode != 39 && e.keyCode != 40)
        return false;
    else
        return true;
}     
}



function isUserRoleExists(roleName,event) {
	
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = event.which; // firefox
	}

	if(charCode==13){
		var roleId= document.getElementById("roleId").value;
		if(roleId=="N"){	
			if (!isNull(roleName)) {
				showProcessing();
				url = './userManagementRoles.do?submitName=isUserRoleExists&userRoleName='+ roleName;
				jQuery.ajax({
					url : url,
					success : function(req) {
						callBackIsUserRoleExists(req);
					}
				});
			}
		}
		
	}else{
		
	}
	
	
	
	/*if (window.event)
        e = window.event;*/
   /* if (e.keyCode == 13) {
	
	var roleId= document.getElementById("roleId").value;
	if(roleId=="N"){	
		if (!isNull(roleName)) {
			showProcessing();
			url = './userManagementRoles.do?submitName=isUserRoleExists&userRoleName='+ roleName;
			jQuery.ajax({
				url : url,
				success : function(req) {
					callBackIsUserRoleExists(req);
				}
			});
		}
	}
	return true;
    }else{
    	return false;
    }*/
}
		
function callBackIsUserRoleExists(data) {
	var response = data;
	if (response != null && response=="Y") {
	alert("User Role already created");		
	document.getElementById("roleName").value="";
	setTimeout(function() {
		document.getElementById("roleName").focus();
	}, 10);
	}
	jQuery.unblockUI();
}