	
/*function validatePotential(obj)
{
	var data = obj.value;
	if(data.length >= 10)
	{
	alert("Maximum limit exceeded");
	setTimeout(function(){obj.focus();},10);
	obj.value="";
	return false;
	}
	return true;	
}
function validateVolume(obj)
{
	var data = obj.value;
	if(data.length >= 10)
	{
	alert("Maximum limit exceeded");
	obj.focus();
	setTimeout(function(){obj.focus();},10);
	obj.value="";
	return false;
	}
	return true;	
}*/
function validateStd(stdObj){
	
	var val = stdObj.value;
	if(val.length >4||val.length <4)
	{
	alert("STD Code should be of length 4");
	setTimeout(function(){stdObj.focus();},10);
	stdObj.value="";
	return false;
	}
	return true;
}

function validatePhone(obj)
{
	var val = obj.value;
	if(val.length >8||val.length <8)
	{
	alert("Phone no. should be of length 8");
	setTimeout(function(){obj.focus();},10);
	obj.value="";
	return false;
	}
	return true;	
}

function validateMobile(obj)
{
	var val = obj.value;
	if(val.length >10||val.length <10)
	{
	alert("Mobile no. should be of length 10");
	setTimeout(function(){obj.focus();},10);
	obj.value="";
	return false;
	}
	return true;	
}

function validatePincode(obj){
	var val = obj.value;
	if(val.length >6||val.length <6)
	{
	alert("Pincode should be of length 6");
	setTimeout(function(){obj.focus();},10);
	obj.value="";
	return false;
	}
	return true;	
}
function validateMandatoryFields()
{	
	var errorsData="";
	var customerName=$("#customerName").val();
	var leadNumber=$("#leadNumber").val();
	var contact=$("#contactPerson").val();
	var std=$("#phoneNoSTD").val();
	var phone=$("#phoneNo").val();
	var mobile=$("#mobileNo").val();
	var email=$("#emailAddress").val();
	var doorNo=$("#doorNoBuilding").val();
	var city=$("#city").val();
	var pincode=$("#pincode").val();
	var branch=$("#branch").val();
	var assigned=$("#assignedTo").val();
	var salesDesignation=$("#salesDesignation").val();
	var businessType= $("#businessType").val();
	
	/*var customerName = document.getElementById("customerName").value;
	var leadNumber =  document.getElementById("leadNumber").value;
	var contact = document.getElementById("contactPerson").value;
	var std = document.getElementById("phoneNoSTD").value;
	var phone = document.getElementById("phoneNo").value;
	var mobile = document.getElementById("mobileNo").value;
	var email = document.getElementById("emailAddress").value;
	var doorNo = document.getElementById("doorNoBuilding").value;
	var city = document.getElementById("city").value;
	var pincode = document.getElementById("pincode").value;
	var branch = document.getElementById("branch").value;
	var assigned = document.getElementById("assignedTo").value;
	var salesDesignation = document.getElementById("salesDesignation").value;*/
	
	if(isNull(customerName)){
		// alert("Please provide customer name.");
		errorsData = errorsData+"Please provide customer name."+"\n";	
		//setTimeout(function(){customerName.focus();},10);
		/*$("#customerName").focus();
		return false;*/
	}
	if(isNull(leadNumber)){
		// alert("Lead number cannot be empty");
		errorsData = errorsData+"Lead number cannot be empty."+"\n";	
		//setTimeout(function(){customerName.focus();},10);
		/*$("#leadNumber").focus();
		return false;*/
	}
	if(isNull(contact)){
		//alert("Please provide contact name.");
		//setTimeout(function(){contact.focus();},10);
		errorsData = errorsData+"Please provide contact name."+"\n";	
		/*$("#contactPerson").focus();
		return false;*/
	}
	if(isNull(std)){
	//	alert("Please provide Std code.");
		//setTimeout(function(){std.focus();},10);
		errorsData = errorsData+"Please provide Std code."+"\n";	
		/*$("#phoneNoSTD").focus();
		return false;*/
	}
	if(isNull(phone)){
	//	alert("Please provide phone number.");
	//	setTimeout(function(){phone.focus();},10);
		errorsData = errorsData+"Please provide phone number."+"\n";
		/*$("#phoneNo").focus();
		return false;*/
	}
	if(isNull(mobile)){
	//	alert("Please provide mobile number.");
	//	setTimeout(function(){mobile.focus();},10);
		errorsData = errorsData+"Please provide mobile number."+"\n";
		/*$("#mobileNo").focus();
		return false;*/
	}
	if(isNull(email)){
	//	alert("Please provide email address.");
	//	setTimeout(function(){email.focus();},10);
		errorsData = errorsData+"Please provide email address."+"\n";
		/*$("#emailAddress").focus();
		return false;*/
	}
	if(isNull(doorNo)){
	//	alert("Please provide door no./building.");
	//	setTimeout(function(){doorNo.focus();},10);
		errorsData = errorsData+"Please provide door no./building."+"\n";
		/*$("#doorNoBuilding").focus();
		return false;*/
	}
	if(isNull(city)){
	//	alert("Please provide city.");
	//	setTimeout(function(){city.focus();},10);
		errorsData = errorsData+"Please provide city."+"\n";
	/*	$("#city").focus();
		return false;*/
	}
	if(isNull(pincode)){
	//	alert("Please provide pincode.");
		//setTimeout(function(){pincode.focus();},10);
		errorsData = errorsData+"Please provide pincode."+"\n";
		/*$("#pincode").focus();
		return false;*/
	}
	if(isNull(branch)){
	//	alert("Please select branch.");
	//	setTimeout(function(){branch.focus();},10);
		errorsData = errorsData+"Please select designation."+"\n";
		/*$("#branch").focus(); 
		return false;*/
	}
	if(isNull(salesDesignation)){
	//	alert("Please select designation.");
		//setTimeout(function(){salesDesignation.focus();},10);
		errorsData = errorsData+"Please select designation."+"\n";
		/*$("#salesDesignation").focus();
		return false;*/
	}
	if(isNull(assigned)){
	//	alert("Please select assignedTo field.");
	//	setTimeout(function(){assigned.focus();},10);
		errorsData = errorsData+"Please select assignedTo field."+"\n";
		/*$("#assignedTo").focus();
		return false;*/
	}
	if(isNull(businessType)){
		//	alert("Please select assignedTo field.");
		//	setTimeout(function(){assigned.focus();},10);
			errorsData = errorsData+"Please select Business Type field."+"\n";
			/*$("#assignedTo").focus();
			return false;*/
		}
	 if (!isNull(errorsData)){
	    	alert(errorsData);
	    	return false;
	    }else{
	    	return true;
	    }
}
function validateEmail()
{
	var email=$("#emailAddress").val();
	//var email = document.getElementById("emailAddress").value;
	var atpos=email.indexOf("@");
	var dotpos=email.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length)
	  {
	  alert("Please enter a valid e-mail address");
	  setTimeout(function(){ $("#emailAddress").focus();},10);
	 // $("#emailAddress").focus();
	  $("#emailAddress").val("");
	  return false;
	  }
	return true;	
}

/*function checkSelected()
{
	if (document.getElementById("check1").checked == true)
	{
		
		document.getElementById("potential1").readOnly = false;
		document.getElementById("expectedVolume1").readOnly = false;
		document.getElementById("competitorId1").disabled = false;
		
	} else
	{
		document.getElementById("potential1").value = "";
		document.getElementById("potential1").readOnly = true;
		document.getElementById("competitorId1").value = "";
		document.getElementById("expectedVolume1").value = "";
		document.getElementById("expectedVolume1").readOnly = true;
		document.getElementById("competitorId1").disabled = true;
		}
	
	if (document.getElementById("check2").checked == true)
	{
		document.getElementById("potential2").readOnly = false;
		document.getElementById("expectedVolume2").readOnly = false;
		document.getElementById("competitorId2").disabled = false;
	} else
	{
		document.getElementById("potential2").value = "";
		document.getElementById("potential2").readOnly = true;
		document.getElementById("expectedVolume2").value = "";
		document.getElementById("expectedVolume2").readOnly = true;
		document.getElementById("competitorId2").value = "";
		document.getElementById("competitorId2").disabled = true;
		}
	if (document.getElementById("check3").checked == true)
	{
		document.getElementById("potential3").readOnly = false;
		document.getElementById("expectedVolume3").readOnly = false;
		document.getElementById("competitorId3").disabled = false;
	} else
	{
		document.getElementById("potential3").value = "";
		document.getElementById("potential3").readOnly = true;
		document.getElementById("expectedVolume3").value = "";
		document.getElementById("expectedVolume3").readOnly = true;
		document.getElementById("competitorId3").value = "";
		document.getElementById("competitorId3").disabled = true;
		}
	
	if (document.getElementById("check4").checked == true)
	{
		document.getElementById("potential4").readOnly = false;
		document.getElementById("expectedVolume4").readOnly = false;
		document.getElementById("competitorId4").disabled = false;
	} else
	{
		document.getElementById("potential4").value = "";
		document.getElementById("potential4").readOnly = true;
		document.getElementById("expectedVolume4").value = "";
		document.getElementById("expectedVolume4").readOnly = true;
		document.getElementById("competitorId4").value = "";
		document.getElementById("competitorId4").disabled = true;
		}
	if (document.getElementById("check5").checked == true)
	{
		document.getElementById("potential5").readOnly = false;
		document.getElementById("expectedVolume5").readOnly = false;
		document.getElementById("competitorId5").disabled = false;
	} else
	{
		document.getElementById("potential5").value = "";
		document.getElementById("potential5").readOnly = true;
		document.getElementById("expectedVolume5").value = "";
		document.getElementById("expectedVolume5").readOnly = true;
		document.getElementById("competitorId5").value = "";
		document.getElementById("competitorId5").disabled = true;
		}
	if (document.getElementById("check6").checked == true)
	{
		document.getElementById("potential6").readOnly = false;
		document.getElementById("expectedVolume6").readOnly = false;
		document.getElementById("competitorId6").disabled = false;
	} else
	{
		document.getElementById("potential6").value = "";
		document.getElementById("potential6").readOnly = true;
		document.getElementById("expectedVolume6").value = "";
		document.getElementById("expectedVolume6").readOnly = true;
		document.getElementById("competitorId6").value = "";
		document.getElementById("competitorId6").disabled = true;
		}
	if (document.getElementById("check7").checked == true)
	{
		document.getElementById("potential7").readOnly = false;
		document.getElementById("expectedVolume7").readOnly = false;
		document.getElementById("competitorId7").disabled = false;
	} else
	{
		document.getElementById("potential7").value = "";
		document.getElementById("potential7").readOnly = true;
		document.getElementById("expectedVolume7").value = "";
		document.getElementById("expectedVolume7").readOnly = true;
		document.getElementById("competitorId7").value = "";
		document.getElementById("competitorId7").disabled = true;
		}
}
*/
function checkSelected()
{
	if ($('#check1').attr('checked'))
	{
		$("#potential1").attr("readonly", false);
		$("#expectedVolume1").attr("readonly", false);
		$("#competitorId1").attr("disabled", false);
	} else
	{
		$("#potential1").val("");
		$("#potential1").attr("readonly", true);
		$("#competitorId1").val("");
		$("#expectedVolume1").val("");
		$("#expectedVolume1").attr("readonly", true);
		$("#competitorId1").attr("disabled", true);
		}
	
	if ($('#check2').attr('checked'))
	{
		$("#potential2").attr("readonly", false);
		$("#expectedVolume2").attr("readonly", false);
		$("#competitorId2").attr("disabled", false);
	} else
	{
		$("#potential2").val("");
		$("#potential2").attr("readonly", true);
		$("#expectedVolume2").val("");
		$("#expectedVolume2").attr("readonly", true);
		$("#competitorId2").val("");
		$("#competitorId2").attr("disabled", true);
		}
	if ($('#check3').attr('checked'))
	{
		$("#potential3").attr("readonly", false);
		$("#expectedVolume3").attr("readonly", false);
		$("#competitorId3").attr("disabled", false);
	} else
	{
		$("#potential3").val("");
		$("#potential3").attr("readonly", true);
		$("#expectedVolume3").val("");
		$("#expectedVolume3").attr("readonly", true);
		$("#competitorId3").val("");
		$("#competitorId3").attr("disabled", true);
		}
	
	if ($('#check4').attr('checked'))
	{
		$("#potential4").attr("readonly", false);
		$("#expectedVolume4").attr("readonly", false);
		$("#competitorId4").attr("disabled", false);
	} else
	{
		$("#potential4").val("");
		$("#potential4").attr("readonly", true);
		$("#expectedVolume4").val("");
		$("#expectedVolume4").attr("readonly", true);
		$("#competitorId4").val("");
		$("#competitorId4").attr("disabled", true);
		}
	if ($('#check5').attr('checked'))
	{
		$("#potential5").attr("readonly", false);
		$("#expectedVolume5").attr("readonly", false);
		$("#competitorId5").attr("disabled", false);
	} else
	{
		$("#potential5").val("");
		$("#potential5").attr("readonly", true);
		$("#expectedVolume5").val("");
		$("#expectedVolume5").attr("readonly", true);
		$("#competitorId5").val("");
		$("#competitorId5").attr("disabled", true);
		}
	if ($('#check6').attr('checked'))
	{
		$("#potential6").attr("readonly", false);
		$("#expectedVolume6").attr("readonly", false);
		$("#competitorId6").attr("disabled", false);
	} else
	{
		$("#potential6").val("");
		$("#potential6").attr("readonly", true);
		$("#expectedVolume6").val("");
		$("#expectedVolume6").attr("readonly", true);
		$("#competitorId6").val("");
		$("#competitorId6").attr("disabled", true);
		}
	if ($('#check7').attr('checked'))
	{
		$("#potential7").attr("readonly", false);
		$("#expectedVolume7").attr("readonly", false);
		$("#competitorId7").attr("disabled", false);
	} else
	{
		$("#potential7").val("");
		$("#potential7").attr("readonly", true);
		$("#expectedVolume7").val("");
		$("#expectedVolume7").attr("readonly", true);
		$("#competitorId7").val("");
		$("#competitorId7").attr("disabled", true);
		}
}

/*function checkCourier(){
	if (document.getElementById("check1").checked == true)
		{
		document.getElementById("check2").disabled = true;
		document.getElementById("check3").disabled = true;
		
		}
	else
		{
		document.getElementById("check2").disabled = false;
		document.getElementById("check3").disabled = false;
		}
	 if(document.getElementById("check2").checked == true)
		{
		document.getElementById("check1").disabled = true;
		}
	 else
		 {
		 document.getElementById("check1").disabled = false;
		 }
	 if(document.getElementById("check3").checked == true || document.getElementById("check2").checked == true)
		{
		document.getElementById("check1").disabled = true;
		}
	 else
		 {
		 document.getElementById("check1").disabled = false;
		 }
}
*/
function checkCourier(){
	if ($('#check1').attr('checked'))
		{
		$("#check2").attr("disabled", true);
		$("#check3").attr("disabled", true);
		
		}
	else
		{
		$("#check2").attr("disabled", false);
		$("#check3").attr("disabled", false);
		}
	 if($('#check2').attr('checked'))
		{
		 $("#check1").attr("disabled", true);
		}
	 else
		 {
		 $("#check1").attr("disabled", false);
		 }
	 if($('#check3').attr('checked')|| $('#check2').attr('checked'))
		{
		 $("#check1").attr("disabled", true);
		}
	 else
		 {
		 $("#check1").attr("disabled", false);
		 }
}

function saveLead(){
	if(validateMandatoryFields())
		{
		var url = './createLead.do?submitName=saveLead';
		jQuery.ajax({
			url : url,
			data : jQuery("#createLeadForm").serialize(),
			success : function(req) {
				callBack(req);
			}
		});
		
		}
	}
function callBack(data){
	/*if (data != null && data!="Failure") {
		alert('Data saved successfully.');
		//disableForSave();
		cancelAfterSave();
	} else {
		alert('Data saved Unsuccessfully.');
	}*/
	var leadTO = eval('(' + data + ')');
	if(!isNull(leadTO.alertMsg)){
		alert(leadTO.alertMsg);
		cancelAfterSave();
		return;
	}
}

function disableForSave(){
	buttonDisabled("saveBtn","btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
}

function cancelDetails(){
	var leadNumber=$("#leadNumber").val();
	//var leadNumber = document.getElementById('leadNumber').value;
	if(confirm("Do you want to Cancel the details?")) {
		var url = "./createLead.do?submitName=preparePage&leadNumber="+ leadNumber;
		document.createLeadForm.action = url;
		document.createLeadForm.submit();	
	}
}

function cancelAfterSave(){
		var url = "./createLead.do?submitName=preparePage";
		document.createLeadForm.action = url;
		document.createLeadForm.submit();	
}

function getSalesExecutiveList(){
	$("#assignedTo").val("");
	var office=$("#branch").val();
	var designation=$("#salesDesignation").val();
	/*document.getElementById('assignedTo').value="";
	var office = document.getElementById('branch').value;
	var designation = document.getElementById('salesDesignation').value;*/
	var url = './createLead.do?submitName=getSalesExecutiveDetails&office='
		+ office +"&designation="+ designation;
	ajaxCallWithoutForm(url, getSalesExecutiveDetails);
}

function getSalesExecutiveDetails(data){
	var content = document.getElementById('assignedTo');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.empUserId;
		option.appendChild(document.createTextNode(this.empTO.firstName + " " +this.empTO.lastName));
		content.appendChild(option);
	});
}

function getSalesDesignation(data){
	var content = document.getElementById('salesDesignation');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.empTO.designation;
		option.appendChild(document.createTextNode(this.empTO.designation));
		content.appendChild(option);
	});
}
function getSalesDesignationList(){
	$("#assignedTo").val("");
	$("#salesDesignation").val("");
	var office = $("#branch").val();
	/*document.getElementById('assignedTo').value="";
	document.getElementById('salesDesignation').value="";
	var office = document.getElementById('branch').value;*/
	var url = './createLead.do?submitName=getSalesDesignation&office='
		+ office;
	ajaxCallWithoutForm(url, getSalesDesignation);
}




