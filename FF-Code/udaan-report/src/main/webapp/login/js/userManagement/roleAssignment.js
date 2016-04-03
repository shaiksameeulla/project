var userOfficeArray = new Array();
function saveRoleAssignments(){
	if (validateFields()) {
		if( getAssignedRoles()){
			var roleType = document.getElementById("roleType").value;
			if(roleType =="E")
				getAssignedOfficeIds();
			showProcessing();
			url = './roleAssignment.do?submitName=saveOrUpdateRoleAssignments';
			jQuery.ajax({
				url : url,
				data : jQuery("#userRoleAssignmentForm").serialize(),
				success : function(req) {
					printCallBackSave(req, roleType);
				}
			});
		}
	}
}

function printCallBackSave(res, roleType){
	if(res =="success"){
		alert("Role assigned successfully.");
	}
	cancel();
	jQuery.unblockUI();
}

function validateFields(assignRoleIds) {
	var isValid = true;
	if (document.getElementById('ffcl').checked == false) {
		if (document.getElementById('cust').checked == false) {
			alert("Please select user role!");
			isValid = false;
			return isValid;
		}
	} else if (document.getElementById('cust').checked == false) {
		if (document.getElementById('ffcl').checked == false) {
			alert("Please select user role!");
			isValid = false;
			return isValid;
		}
	}
	var userCode = document.getElementById('userCode').value;
	if (userCode == null || userCode == "") {
		alert("Please enter login id !");
		document.getElementById("userCode").focus();
		isValid = false;
		return isValid;
	}
	
	return isValid;
}

function getAssignedRoles(){
	var isValid = true;
	var assignedUserRoleIds="";
	//Get all values from assgined select 
	var assginedUserRoleObj = document.getElementById("assignRoleIds");
	for (i = 0; i < assginedUserRoleObj.options.length; i++) {
		if(assignedUserRoleIds ==null || assignedUserRoleIds=="")
		assignedUserRoleIds = assginedUserRoleObj.options[i].value;
		else 
			assignedUserRoleIds = assignedUserRoleIds + "," + assginedUserRoleObj.options[i].value;
	}
	document.getElementById("assignedUserRoleIds").value=assignedUserRoleIds;
	if(assignedUserRoleIds ==null || assignedUserRoleIds ==""){
		alert("Please assign user roles!.");
		isValid = false;
		return isValid;
	}
	return isValid;
  }
 
function getAssignedOfficeIds(){
	var assignedOfficeIds="";
	//Get all values from assgined select 
	var mappedOfficeObj = document.getElementById("mappedOfficeIds");
	for (i = 0; i < mappedOfficeObj.options.length; i++) {
		if(assignedOfficeIds ==null || assignedOfficeIds=="")
			assignedOfficeIds = mappedOfficeObj.options[i].value;
		else 
			assignedOfficeIds = assignedOfficeIds + "," + mappedOfficeObj.options[i].value;
	}
	document.getElementById("assignedOfficeIds").value=assignedOfficeIds;
  }



function getUserRoles(roleType) {
	
	showProcessing();
	url = './roleAssignment.do?submitName=getUserRoles&roleType=' + roleType;
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
		var rolesDetails = eval('(' + response + ')');
		if (rolesDetails != null && rolesDetails != "") {
			var noOfRoles = rolesDetails.length;
			for ( var i = 0; i < noOfRoles; i++) {
				var option = document.createElement("option");
				option.value = rolesDetails[i].roleId;
				option.appendChild(document
						.createTextNode(rolesDetails[i].roleName));
				roles.appendChild(option);
			}

		}

	}
	jQuery.unblockUI();
}

function getUserDetails() {
	var userCode = document.getElementById("userCode").value;
	if (userCode != null && userCode != "") {
		showProcessing();
		var roleType = document.getElementById('roleType').value;
		url = './roleAssignment.do?submitName=getUserDetails&roleType='+ roleType + "&userCode=" + userCode;
		jQuery.ajax({
			url : url,
			dataType: "text",
		 	data:jQuery("#userRoleAssignmentForm").serialize(),
			success : function(req) {
				displayUserDetails(req);
			}
			
		});
	}
}
function displayUserDetails(data) {
	var userJSONListArray = new Array();
	var rolesArray = new Array();
	var response = data;
	if (response != null && response == 'INVALID_USER') {
		alert("Invalid User!Try again.");
		document.getElementById("userName").value="";
		document.getElementById("userCode").value="";
		document.getElementById("userCode").focus();
		

	} else {
		userJSONListArray = response.split("~");
		rolesArray = userJSONListArray[1];
		userOfficeArray = userJSONListArray[2];
		var roleElement = eval('(' + rolesArray + ')');
		var assignRoles = document.getElementById('assignRoleIds');
		assignRoles.innerHTML = "";
		if (roleElement != null && roleElement.userRights.length != 0) {
			disabledFields();
			document.getElementById("userName").value = roleElement.userRights[0].userName;
			document.getElementById("userId").value = roleElement.userRights[0].userId;
			document.getElementById("userCityId").value = roleElement.userRights[0].userCityId;
			
			for ( var i = 0; i < roleElement.userRights.length; i++) {
				if(roleElement.userRights[i].roleId !=null && roleElement.userRights[i].roleId!=""){
					var option = document.createElement("option");
					option.value = roleElement.userRights[i].roleId;
					option.appendChild(document
							.createTextNode(roleElement.userRights[i].roleCode));
					assignRoles.appendChild(option);
					//Deleting option form role 
					var userRoleIds = document.getElementById("roleId");
					for ( var j = 0; j < userRoleIds.length; j++) {
						if(roleElement.userRights[i].roleId == userRoleIds[j].value) {
							userRoleIds.remove(j);
						}
					}
			}
				else{
					enableFields();
				}
				
				
		}
			
	}
		
		
		//Setting Branches
		var officeElement = eval('(' + userOfficeArray + ')');
		if (officeElement != null && officeElement.userOffices.length != 0) {
			var mappedTO = officeElement.userOffices[0].mappedTo;
			if(mappedTO == "A"){
				document.getElementById("rho").checked=false;
				document.getElementById("area").checked=true;
				document.getElementById("rho").disabled=true;
				getOfficeDetails(mappedTO);
			}
			if(mappedTO == "R"){
				document.getElementById("area").checked=false;
				document.getElementById("area").disabled=true;
				document.getElementById("rho").checked=true;
				getOfficeDetails(mappedTO);
			}
	}
		
	}
	jQuery.unblockUI();
}

function gettingExistingUserMapping(){
	
}

function move(tbFrom, tbTo) {
	document.getElementById("btnfrom").blur();
	document.getElementById("btnto").blur();
	document.getElementById("btnfrom1").blur();
	document.getElementById("btnto1").blur();
	var arrFrom = new Array();
	var arrTo = new Array();
	var arrLU = new Array();
	var i;
	for (i = 0; i < tbTo.options.length; i++) {
		arrLU[tbTo.options[i].text] = tbTo.options[i].value;
		arrTo[i] = tbTo.options[i].text;
	}
	var fLength = 0;
	var tLength = arrTo.length;
	for (i = 0; i < tbFrom.options.length; i++) {
		arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
		if (tbFrom.options[i].selected && tbFrom.options[i].value != "") {
			arrTo[tLength] = tbFrom.options[i].text;
			tLength++;
		} else {
			arrFrom[fLength] = tbFrom.options[i].text;
			fLength++;
		}
	}

	tbFrom.length = 0;
	tbTo.length = 0;
	var ii;

	for (ii = 0; ii < arrFrom.length; ii++) {
		var no = new Option();
		no.value = arrLU[arrFrom[ii]];
		no.text = arrFrom[ii];
		tbFrom[ii] = no;
	}

	for (ii = 0; ii < arrTo.length; ii++) {
		var no = new Option();
		no.value = arrLU[arrTo[ii]];
		no.text = arrTo[ii];
		tbTo[ii] = no;
	}
}

function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}


function enableDisableAreaOfficeMapping(roleType)
{
	document.getElementById("userId").value="";
	document.getElementById("userName").value="";
	
	assignRoleIds=document.getElementById("assignRoleIds");
	for (var i=assignRoleIds.options.length-1; i>=0; i--){
		assignRoleIds.options[i] = null;
	  }
	
	mappedOfficeIds=document.getElementById("mappedOfficeIds");
	for (var i=mappedOfficeIds.options.length-1; i>=0; i--){
		mappedOfficeIds.options[i] = null;
	  }
	
	
	document.getElementById("roleType").value=roleType;
	if(roleType=="C"){
		document.getElementById("area").checked=false;
		document.getElementById("area").disabled=true;
		document.getElementById("rho").checked=false;
		document.getElementById("rho").disabled=true;
		var offices = document.getElementById("officeIds");
		offices.innerHTML="";
		offices.disabled=true;
		var mappedOfficeIds = document.getElementById("mappedOfficeIds");
		mappedOfficeIds.innerHTML="";
		mappedOfficeIds.disabled=true;
	}
if(roleType=="E"){
	document.getElementById("area").checked=false;
	document.getElementById("area").disabled=false;
	document.getElementById("rho").checked=false;
	document.getElementById("rho").disabled=false;
	var offices = document.getElementById("officeIds");
	offices.innerHTML="";
	offices.disabled=false;
	var mappedOfficeIds = document.getElementById("mappedOfficeIds");
	mappedOfficeIds.innerHTML="";
	mappedOfficeIds.disabled=false;
	}
	
	
}

function cancel() {
	url = './roleAssignment.do?submitName=assignUserRoles';
	document.userRoleAssignmentForm.action = url;
	document.userRoleAssignmentForm.submit();
}

function getOfficeDetails(mappingType){
	document.getElementById("mappingTO").value =mappingType;
	var userCityId=document.getElementById("userCityId").value;
	if (mappingType != null && mappingType != "") {
		showProcessing();
		url = './roleAssignment.do?submitName=getOfficeDetails&mappingType='+ mappingType+'&userCityId='+userCityId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				displayOffices(req);
			}
		});
	}
}

function displayOffices(data) {
	var response = data;
	var officeIds = document.getElementById('officeIds');
	officeIds.innerHTML = "";
	if (response != null && response != 'NOOFFICES') {
		var officeDetails = eval('(' + response + ')');
		if (officeDetails != null && officeDetails != "") {
			var noOfOffices = officeDetails.length;
			for ( var i = 0; i < noOfOffices; i++) {
				var option = document.createElement("option");
				option.value = officeDetails[i].officeId;
				option.appendChild(document
						.createTextNode(officeDetails[i].officeName));
				officeIds.appendChild(option);
			}

		}
	}
	//Setting old ids
	if(userOfficeArray !=null && userOfficeArray!=""){
		var officeElement = eval('(' + userOfficeArray + ')');
		var assignOfficeIds = document.getElementById('mappedOfficeIds');
		if (officeElement != null && officeElement.userOffices.length != 0) {
			for ( var i = 0; i < officeElement.userOffices.length; i++) {
				if(officeElement.userOffices[i].officeId !=null && officeElement.userOffices[i].officeId!=""){
					var option = document.createElement("option");
					option.value = officeElement.userOffices[i].officeId;
					option.appendChild(document
							.createTextNode(officeElement.userOffices[i].officeName));
					assignOfficeIds.appendChild(option);
					//
					var officeIds = document.getElementById("officeIds");
					for ( var j = 0; j < officeIds.length; j++) {
						if(officeElement.userOffices[i].officeId == officeIds[j].value) {
							officeIds.remove(j);
						}
					}
			}
		}
		}
	}
	jQuery.unblockUI();
}


function disabledFields(){
	document.getElementById("btnSave").disabled=true;
	document.getElementById("mappedOfficeIds").disabled=true;
	document.getElementById("officeIds").disabled=true;
	document.getElementById("assignRoleIds").disabled=true;
	document.getElementById("roleId").disabled=true;
	
}

function enableFields(){
	
	document.getElementById("btnSave").disabled=false;
	document.getElementById("mappedOfficeIds").disabled=false;
	document.getElementById("officeIds").disabled=false;
	document.getElementById("assignRoleIds").disabled=false;
	document.getElementById("roleId").disabled=false;
	
}