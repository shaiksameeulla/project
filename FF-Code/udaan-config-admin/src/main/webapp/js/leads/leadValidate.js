var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
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

$(document).ready(function() {     
	$('#leadsPlan').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "100%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
	"sPaginationType": "full_numbers"
	} );
	
//	loadDefaultObjects();
	
} ); 

function loadDefaultObjects(){
	if(userRole == salesExecutiveRole || userRole == controlTeamRole)
	{
	enableCheckBox();
	}else
	{
		alert("User does not have required role to operate Leads Validation screen.");
		disableForm();
	}
	//disableFeedBackPlaningPopUp();
}
function leadValidationStartUp(){
	/*var userRoleName = document.getElementById("userRoles").value;
	var status = document.getElementById("status").value;*/
	var userRoleName = $("#userRoles").val();
	var status = $("#status").val();
	if(userRole == salesExecutiveRole){
		dropdownDisable();//in common.js
		allTextBoxDisable();
		//to disable the button also
	} else if(userRole == controlTeamRole){
		enableCompetitordropdown();
		dropdownEnable();//in common .js
		allTextBoxEnable();
		checkLeadStatus(status);	
		
	}
	
}

function checkLeadStatus(status){
	if(status=="Approved"){
		dropdownDisable();//in common.js
		allTextBoxDisable();
		alert("Lead is already approved. Cannot be modified");
	}
	else{
		enableCheckBox();
		checkSelected();
		checkCourier();	
	}
}
function enableCompetitordropdown(){
	/*document.getElementById("competitorId1").disabled = false;
	document.getElementById("competitorId2").disabled = false;
	document.getElementById("competitorId3").disabled = false;
	document.getElementById("competitorId4").disabled = false;
	document.getElementById("competitorId5").disabled = false;
	document.getElementById("competitorId6").disabled = false;
	document.getElementById("competitorId7").disabled = false;*/
	
	$("#competitorId1").attr("disabled", false);
	$("#competitorId2").attr("disabled", false);
	$("#competitorId3").attr("disabled", false);
	$("#competitorId4").attr("disabled", false);
	$("#competitorId5").attr("disabled", false);
	$("#competitorId6").attr("disabled", false);
	$("#competitorId7").attr("disabled", false);
}

function validateStd(stdObj){
	
	var val = stdObj.value;
	if(val.length >4||val.length <4)
	{
	alert("STD Code should be of length 4");
	stdObj.focus();
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
	obj.focus();
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


function validateMandatoryFields()
{
	var errorsData="";
	/*var customerName = document.getElementById("customerName").value;
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
	var customerName=$("#customerName").val();
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
	
	if(isNull(customerName)){
		/*alert("Please provide customer name.");
		$("#customerName").focus();
	//	setTimeout(function(){customerName.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide customer name."+"\n";	
	}
	if(isNull(contact)){
		/*alert("Please provide contact name.");
		$("#contactPerson").focus();
		//setTimeout(function(){contact.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide contact person."+"\n";
	}
	if(isNull(std)){
		/*alert("Please provide Std code.");
		$("#phoneNoSTD").focus();
		//setTimeout(function(){std.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide Std code."+"\n";	
		
	}
	if(isNull(phone)){
		/*alert("Please provide phone number.");
		$("#phoneNo").focus();
		//setTimeout(function(){phone.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide phone number."+"\n";
	}
	if(isNull(mobile)){
		/*alert("Please provide mobile number.");
		$("#mobileNo").focus();
		// setTimeout(function(){mobile.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide mobile number."+"\n";
	}
	if(isNull(email)){
		/*alert("Please provide email address.");
		$("#emailAddress").focus();
		//setTimeout(function(){email.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide email address."+"\n";
	}
	if(isNull(city)){
		/*alert("Please provide city.");
		$("#doorNoBuilding").focus();
		// setTimeout(function(){city.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide city."+"\n";
	}
	if(isNull(doorNo)){
		/*alert("Please provide door no./building.");
		$("#city").focus();
		// setTimeout(function(){doorNo.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide door no./building."+"\n";
	}
	if(isNull(pincode)){
		/*alert("Please provide pincode.");
		$("#pincode").focus();
		//setTimeout(function(){pincode.focus();},10);
		return false;*/
		errorsData = errorsData+"Please provide pincode."+"\n";
	}
	if(isNull(branch)){
		/*alert("Please select branch.");
		$("#branch").focus();
		//setTimeout(function(){branch.focus();},10);
		return false;*/
		errorsData = errorsData+"Please select branch."+"\n";
		
	}
	if(isNull(salesDesignation)){
		/*alert("Please select designation.");
		$("#assignedTo").focus();
		//setTimeout(function(){salesDesignation.focus();},10);
		return false;*/
		errorsData = errorsData+"Please select sales person title."+"\n";
	}
	if(isNull(assigned)){
		/*alert("Please select assignedTo field.");
		$("#salesDesignation").focus();
		//setTimeout(function(){assigned.focus();},10);
		return false;*/
		errorsData = errorsData+"Please select assigned To field."+"\n";
	}
	if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{
    	return true;
    }
}
/*function validateEmailAddress()
{
	var email = $("#emailAddress").val();
	//var email = document.getElementById("emailAddress").value;
	var atpos=email.indexOf("@");
	var dotpos=email.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length)
	  {
	  alert("Please enter a valid e-mail address");
	  setTimeout(function(){email.focus();},10);
	  return false;
	  }
	return true;	
}*/
function validateLeadNo()
{
	var leadNo = $("#leadNumber").val();
	//var leadNo = document.getElementById("leadNumber").value;
	var letter = /^[L]$/;
	var numpattern = /^[0-9]{3,20}$/;
	
	if (!leadNo.value.substring(0, 1).match(letter)
			|| !numpattern.test(leadNo.value.substring(5))) {
		alert('Lead number format is not correct');
		leadNo.value = "";
		setTimeout(function() {
			leadNo.focus();
		}, 10);
		return false;
	}
	return true;
	
}
/*function allTextBoxDisable(){
	//disable customerName field
	document.getElementById("customerName").readOnly = true;
	document.getElementById("customerName").setAttribute("tabindex","-1");
	
	//disable all checkbox
	for(var i=1;i<8;i++){
	getDomElementById("check"+i).disabled=true;
	}
	
	//disable potential and expectedVolume field
	for(var i=1;i<8;i++){
	document.getElementById("potential"+i).readOnly = true;
	document.getElementById("potential"+i).setAttribute("tabindex","-1");
	
	document.getElementById("expectedVolume"+i).readOnly = true;
	document.getElementById("expectedVolume"+i).setAttribute("tabindex","-1");
	}
	
	//disable contact person field
	document.getElementById("contactPerson").readOnly = true;
	document.getElementById("contactPerson").setAttribute("tabindex","-1");
	
	//disable door no field
	document.getElementById("doorNoBuilding").readOnly = true;
	document.getElementById("doorNoBuilding").setAttribute("tabindex","-1");

	//disable phn std field
	document.getElementById("phoneNoSTD").readOnly = true;
	document.getElementById("phoneNoSTD").setAttribute("tabindex","-1");

	//disable phn no field
	document.getElementById("phoneNo").readOnly = true;
	document.getElementById("phoneNo").setAttribute("tabindex","-1");

	//disable mobile no field
	document.getElementById("mobileNo").readOnly = true;
	document.getElementById("mobileNo").setAttribute("tabindex","-1");

	//disable street field
	document.getElementById("street").readOnly = true;
	document.getElementById("street").setAttribute("tabindex","-1");
	
	//disable locatn field
	document.getElementById("location").readOnly = true;
	document.getElementById("location").setAttribute("tabindex","-1");

	//disable secondary conatct field
	document.getElementById("secondaryContact").readOnly = true;
	document.getElementById("secondaryContact").setAttribute("tabindex","-1");

	//disable designatn field
	document.getElementById("designation").readOnly = true;
	document.getElementById("designation").setAttribute("tabindex","-1");
	
	//disable city field
	document.getElementById("city").readOnly = true;
	document.getElementById("city").setAttribute("tabindex","-1");
	
	//disable emailAdd field
	document.getElementById("emailAddress").readOnly = true;
	document.getElementById("emailAddress").setAttribute("tabindex","-1");
	
	//disable pincode field
	document.getElementById("pincode").readOnly = true;
	document.getElementById("pincode").setAttribute("tabindex","-1");
	
}*/
function allTextBoxDisable(){
	//disable customerName field
	$("#customerName").attr("readonly", true);
	$("#customerName").attr("tabindex", "-1");
	
	//disable all checkbox
	for(var i=1;i<8;i++){
		$("#check"+i).attr("disabled", true);
	}
	
	//disable potential and expectedVolume field
	for(var i=1;i<8;i++){
	$("#potential"+i).attr("readonly", true);
	$("#potential"+i).attr("tabindex", "-1");
	
	$("#expectedVolume"+i).attr("readonly", true);
	$("#expectedVolume"+i).attr("tabindex", "-1");
	}
	
	//disable contact person field
	$("#contactPerson").attr("readonly", true);
	$("#contactPerson").attr("tabindex", "-1");
	
	//disable door no field
	$("#doorNoBuilding").attr("readonly", true);
	$("#doorNoBuilding").attr("tabindex", "-1");

	//disable phn std field
	$("#phoneNoSTD").attr("readonly", true);
	$("#phoneNoSTD").attr("tabindex", "-1");

	//disable phn no field
	$("#phoneNo").attr("readonly", true);
	$("#phoneNo").attr("tabindex", "-1");

	//disable mobile no field
	$("#mobileNo").attr("readonly", true);
	$("#mobileNo").attr("tabindex", "-1");

	//disable street field
	$("#street").attr("readonly", true);
	$("#street").attr("tabindex", "-1");
	
	//disable locatn field
	$("#location").attr("readonly", true);
	$("#location").attr("tabindex", "-1");

	//disable secondary conatct field
	$("#secondaryContact").attr("readonly", true);
	$("#secondaryContact").attr("tabindex", "-1");

	//disable designatn field
	$("#designation").attr("readonly", true);
	$("#designation").attr("tabindex", "-1");
	
	//disable city field
	$("#city").attr("readonly", true);
	$("#city").attr("tabindex", "-1");
	
	//disable emailAdd field
	$("#emailAddress").attr("readonly", true);
	$("#emailAddress").attr("tabindex", "-1");
	
	//disable pincode field
	$("#pincode").attr("readonly", true);
	$("#pincode").attr("tabindex", "-1");
}
/*function allTextBoxEnable(){
	//enable customerName field
	document.getElementById("customerName").readOnly = false;
	document.getElementById("customerName").setAttribute("tabindex","0");
	
	//disable all checkbox
	for(var i=1;i<8;i++){
	getDomElementById("check"+i).disabled=true;
	}
	
	//disable potential and expectedVolume field
	for(var i=1;i<8;i++){
	document.getElementById("potential"+i).readOnly = true;
	document.getElementById("potential"+i).setAttribute("tabindex","-1");
	
	document.getElementById("expectedVolume"+i).readOnly = true;
	document.getElementById("expectedVolume"+i).setAttribute("tabindex","-1");
	
	document.getElementById("competitorId"+i).disabled = true;
	}
	
	
	//enable all checkbox
	for(var i=1;i<8;i++){
	getDomElementById("check"+i).disabled=false;
	}
	
	//enable potential and expectedVolume field
	for(var i=1;i<8;i++){
	document.getElementById("potential"+i).readOnly = false;
	document.getElementById("potential"+i).setAttribute("tabindex","0");
	
	document.getElementById("expectedVolume"+i).readOnly = false;
	document.getElementById("expectedVolume"+i).setAttribute("tabindex","0");
	}
	
	//enable contact person field
	document.getElementById("contactPerson").readOnly = false;
	document.getElementById("contactPerson").setAttribute("tabindex","0");
	
	//enable door no field
	document.getElementById("doorNoBuilding").readOnly = false;
	document.getElementById("doorNoBuilding").setAttribute("tabindex","0");

	//enable phn std field
	document.getElementById("phoneNoSTD").readOnly = false;
	document.getElementById("phoneNoSTD").setAttribute("tabindex","0");

	//enable phn no field
	document.getElementById("phoneNo").readOnly = false;
	document.getElementById("phoneNo").setAttribute("tabindex","0");

	//enable mobile no field
	document.getElementById("mobileNo").readOnly = false;
	document.getElementById("mobileNo").setAttribute("tabindex","0");

	//enable street field
	document.getElementById("street").readOnly = false;
	document.getElementById("street").setAttribute("tabindex","0");

	//enable locatn field
	document.getElementById("location").readOnly = false;
	document.getElementById("location").setAttribute("tabindex","0");

	//enable secondary conatct field
	document.getElementById("secondaryContact").readOnly = false;
	document.getElementById("secondaryContact").setAttribute("tabindex","0");

	//enable designatn field
	document.getElementById("designation").readOnly = false;
	document.getElementById("designation").setAttribute("tabindex","0");
	
	//enable city field
	document.getElementById("city").readOnly = false;
	document.getElementById("city").setAttribute("tabindex","0");
	
	//enable emailAdd field
	document.getElementById("emailAddress").readOnly = false;
	document.getElementById("emailAddress").setAttribute("tabindex","0");
	
	//enable pincode field
	document.getElementById("pincode").readOnly = false;
	document.getElementById("pincode").setAttribute("tabindex","0");
	
	
}
*/
function allTextBoxEnable(){
	//enable customerName field
	$("#customerName").attr("readonly", false);
	$("#customerName").attr("tabindex", "0");
	
	/*//disable all checkbox
	for(var i=1;i<8;i++){
	getDomElementById("check"+i).disabled=true;
	}*/
	
	//disable potential and expectedVolume field
	for(var i=1;i<8;i++){
	$("#potential"+i).attr("readonly", true);
	$("#potential"+i).attr("tabindex", "-1");
	
	$("#expectedVolume"+i).attr("readonly", true);
	$("#expectedVolume"+i).attr("tabindex", "-1");
	
	$("#competitorId"+i).attr("disabled", true);
	}
	
	
	/*//enable all checkbox
	for(var i=1;i<8;i++){
	getDomElementById("check"+i).disabled=false;
	}
	
	//enable potential and expectedVolume field
	for(var i=1;i<8;i++){
	document.getElementById("potential"+i).readOnly = false;
	document.getElementById("potential"+i).setAttribute("tabindex","0");
	
	document.getElementById("expectedVolume"+i).readOnly = false;
	document.getElementById("expectedVolume"+i).setAttribute("tabindex","0");
	}*/
	
	//enable contact person field
	$("#contactPerson").attr("readonly", false);
	$("#contactPerson").attr("tabindex", "0");
	
	//enable door no field
	$("#doorNoBuilding").attr("readonly", false);
	$("#doorNoBuilding").attr("tabindex", "0");

	//enable phn std field
	$("#phoneNoSTD").attr("readonly", false);
	$("#phoneNoSTD").attr("tabindex", "0");

	//enable phn no field
	$("#phoneNo").attr("readonly", false);
	$("#phoneNo").attr("tabindex", "0");

	//enable mobile no field
	$("#mobileNo").attr("readonly", false);
	$("#mobileNo").attr("tabindex", "0");

	//enable street field
	$("#street").attr("readonly", false);
	$("#street").attr("tabindex", "0");

	//enable locatn field
	$("#location").attr("readonly", false);
	$("#location").attr("tabindex", "0");

	//enable secondary conatct field
	$("#secondaryContact").attr("readonly", false);
	$("#secondaryContact").attr("tabindex", "0");

	//enable designatn field
	$("#designation").attr("readonly", false);
	$("#designation").attr("tabindex", "0");
	
	//enable city field
	$("#city").attr("readonly", false);
	$("#city").attr("tabindex", "0");
	
	//enable emailAdd field
	$("#emailAddress").attr("readonly", false);
	$("#emailAddress").attr("tabindex", "0");
	
	//enable pincode field
	$("#pincode").attr("readonly", false);
	$("#pincode").attr("tabindex", "0");
	
}

/*function checkSelected()
{
	if (document.getElementById("check1").checked == true)
	{
		
		document.getElementById("potential1").readOnly = false;
		document.getElementById("potential1").setAttribute("tabindex","0");
		document.getElementById("expectedVolume1").readOnly = false;
		document.getElementById("competitorId1").disabled = false;
		
	} else
	{
		//document.getElementById("potential1").value = "";
		document.getElementById("potential1").readOnly = true;
		//document.getElementById("competitorId1").value = "";
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
		//document.getElementById("potential2").value = "";
		document.getElementById("potential2").readOnly = true;
		//document.getElementById("expectedVolume2").value = "";
		document.getElementById("expectedVolume2").readOnly = true;
		//document.getElementById("competitorId2").value = "";
		document.getElementById("competitorId2").disabled = true;
		}
	if (document.getElementById("check3").checked == true)
	{
		document.getElementById("potential3").readOnly = false;
		document.getElementById("expectedVolume3").readOnly = false;
		document.getElementById("competitorId3").disabled = false;
	} else
	{
		//document.getElementById("potential3").value = "";
		document.getElementById("potential3").readOnly = true;
		//document.getElementById("expectedVolume3").value = "";
		document.getElementById("expectedVolume3").readOnly = true;
		//document.getElementById("competitorId3").value = "";
		document.getElementById("competitorId3").disabled = true;
		}
	
	if (document.getElementById("check4").checked == true)
	{
		document.getElementById("potential4").readOnly = false;
		document.getElementById("expectedVolume4").readOnly = false;
		document.getElementById("competitorId4").disabled = false;
	} else
	{
		//document.getElementById("potential4").value = "";
		document.getElementById("potential4").readOnly = true;
		//document.getElementById("expectedVolume4").value = "";
		document.getElementById("expectedVolume4").readOnly = true;
		//document.getElementById("competitorId4").value = "";
		document.getElementById("competitorId4").disabled = true;
		}
	if (document.getElementById("check5").checked == true)
	{
		document.getElementById("potential5").readOnly = false;
		document.getElementById("expectedVolume5").readOnly = false;
		document.getElementById("competitorId5").disabled = false;
	} else
	{
		//document.getElementById("potential5").value = "";
		document.getElementById("potential5").readOnly = true;
		//document.getElementById("expectedVolume5").value = "";
		document.getElementById("expectedVolume5").readOnly = true;
		//document.getElementById("competitorId5").value = "";
		document.getElementById("competitorId5").disabled = true;
		}
	if (document.getElementById("check6").checked == true)
	{
		document.getElementById("potential6").readOnly = false;
		document.getElementById("expectedVolume6").readOnly = false;
		document.getElementById("competitorId6").disabled = false;
	} else
	{
		//document.getElementById("potential6").value = "";
		document.getElementById("potential6").readOnly = true;
		//document.getElementById("expectedVolume6").value = "";
		document.getElementById("expectedVolume6").readOnly = true;
		//document.getElementById("competitorId6").value = "";
		document.getElementById("competitorId6").disabled = true;
		}
	if (document.getElementById("check7").checked == true)
	{
		document.getElementById("potential7").readOnly = false;
		document.getElementById("expectedVolume7").readOnly = false;
		document.getElementById("competitorId7").disabled = false;
	} else
	{
		//document.getElementById("potential7").value = "";
		document.getElementById("potential7").readOnly = true;
		//document.getElementById("expectedVolume7").value = "";
		document.getElementById("expectedVolume7").readOnly = true;
		//document.getElementById("competitorId7").value = "";
		document.getElementById("competitorId7").disabled = true;
		}
}*/
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
	 if(document.getElementById("check3").checked == true)
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

function approveLead(action){
	//var leadStatus = document.getElementById("status").value;
	var leadStatus=$("#status").val();
	if(!validateMandatoryFields()){
		return
	}
	if(leadStatus=='New'||leadStatus=='On Hold'){
		
	
		var url = './leadValidation.do?submitName=approveLead&action='+action;
		jQuery.ajax({
			url : url,
			data : jQuery("#createLeadForm").serialize(),
			success : function(req) {
				callBackApproveLead(req);
			}
		});
		
	}else{
		alert('Lead cannot be approved as it is '+leadStatus);
	}
}

function callBackApproveLead(data){
	if(data != null){
		var leadTO = eval('(' + data + ')');
		if(leadTO.transMag!="LM020"){
			alert('Lead approved successfully.');
			// leadDetailsThead
//			$('#leadDetails > tbody')
//			$('#leadDetails')
			
//			$("#leadDetails", window.opener.document)
/*			
			$("#leadDetails").each(function() {

			});*/
			
			$("#status0").val(leadTO.status.statusDescription);
		}else{
			alert('Lead could not be approved successfully.');
		}
	}
}


function putOnHold(action){
	//var leadStatus = document.getElementById("status").value;
	var leadStatus=$("#status").val();
	if(!validateMandatoryFields()){
		return
	}	
	if(leadStatus=='New'){
		
	
		var url = './leadValidation.do?submitName=putOnHoldLead';
		jQuery.ajax({
			url : url,
			data : jQuery("#createLeadForm").serialize(),
			success : function(req) {
				callBackPutOnHold(req);
			}
		});
		
	}
	else{
		alert('Lead cannot be put on hold as it is '+leadStatus);
	}
	}

function callBackPutOnHold(data){
		if(data != null){
			var leadTO = eval('(' + data + ')');
			if(leadTO.transMag!="LM024"){
				alert('Lead put on hold successfully.');
				$("#status").val(leadTO.status.statusDescription);
			}else{
				alert('Lead could not be put on hold successfully.');
			}
		}
}


function rejectLead(action){
	//var leadStatus = document.getElementById("status").value;
	var leadStatus=$("#status").val();
	if(!validateMandatoryFields()){
		return
	}
	if(leadStatus=='New'||leadStatus=='On Hold'){
		
	
		var url = './leadValidation.do?submitName=rejectLead';
		jQuery.ajax({
			url : url,
			data : jQuery("#createLeadForm").serialize(),
			success : function(req) {
				callBackRejectLead(req);
			}
		});
		
	}
	else{
		alert('Lead cannot be rejected as it is '+leadStatus);
	}
	}

function callBackRejectLead(data){
	if(data != null){
		var leadTO = eval('(' + data + ')');
		if(leadTO.transMag!="LM022"){
			alert('Lead rejected successfully.');
			$("#status").val(leadTO.status.statusDescription);
		}else{
			alert('Lead could not be rejected successfully.');
		}
	}
}

/*function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
		var url = "./leadValidation.do?submitName=preparePage";
		document.createLeadForm.action = url;
		document.createLeadForm.submit();	
	}
}*/

function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
/*		var url = "./leadsView.do?submitName=preparePage";
		document.leadsViewForm.action = url;
		document.leadsViewForm.submit();*/
		window.close();
//		window.location.assign(url);
	}
}

function getSalesExecutiveList(){
	$("#assignedTo").val("");
	//document.getElementById('assignedTo').value="";
	var office=$("#branch").val();
	var designation=$("#salesDesignation").val();
	//var office = document.getElementById('branch').value;
	//var designation = document.getElementById('salesDesignation').value;
	var url = './leadValidation.do?submitName=getSalesExecutiveDetails&office='
		+ office +"&designation="+ designation;
	ajaxCallWithoutForm(url, getSalesExecutiveDetails);
}

function getSalesDesignationList(){
	$("#assignedTo").val("");
	$("#salesDesignation").val("");
	var office = $("#branch").val();
	/*document.getElementById('assignedTo').value="";
	document.getElementById('salesDesignation').value="";
	var office = document.getElementById('branch').value;*/
	var url = './leadValidation.do?submitName=getSalesDesignation&office='
		+ office;
	ajaxCallWithoutForm(url, getSalesDesignation);
}

function getSalesDesignation(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
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
}
function getSalesExecutiveDetails(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
	var content = document.getElementById('assignedTo');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.empUserDo.empUserId;
		option.appendChild(document.createTextNode(this.userName + " - " + this.empUserDo.empDO.firstName + " " +this.empUserDo.empDO.lastName));
		content.appendChild(option);
	});
	}
}

function getSalesDesignation(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
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
}
function getSalesDesignationList(){
	$("#assignedTo").val("");
	$("#salesDesignation").val("");
	var office=$("#branch").val();
	/*document.getElementById('assignedTo').value="";
	document.getElementById('salesDesignation').value="";
	var office = document.getElementById('branch').value;*/
	var url = './leadValidation.do?submitName=getSalesDesignation&office='
		+ office;
	ajaxCallWithoutForm(url, getSalesDesignation);
}


function emailPopUp(){
	var leadNumber=$("#leadNumber").val();
	var url = './sendEmail.do?submitName=preparePage&leadNumber='+ leadNumber;
	showProcessing();
	var w =	window
		.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=600,height=300,left = 200,top = 184');
	var watchClose = setInterval(function() {
		try {
			if (w.closed) {
				jQuery.unblockUI();
				clearTimeout(watchClose);
			}
		} catch (e) {
		}
	}, 200);
		//document.createLeadForm.action=url;
		//document.createLeadForm.submit();
}
function SMSPopUp(){
	var leadNumber=$("#leadNumber").val();
	var url = './sendSMS.do?submitName=preparePage&leadNumber='+ leadNumber;
	//jQuery.blockUI();
	showProcessing();
		var w= window
		.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=600,height=250,left = 200,top = 184');
		var watchClose = setInterval(function() {
			try {
				if (w.closed) {
					jQuery.unblockUI();
					clearTimeout(watchClose);
				}
			} catch (e) {
			}
		}, 200);
		//document.createLeadForm.action=url;
		//document.createLeadForm.submit();
}

function leadPlanPopUp(){
	var leadNumber=$("#leadNumber").val();
	var customerName=$("#customerName").val();
	var status=$("#status").val();
	var leadId=$("#leadId").val();
	var contactPerson=$("#contactPerson").val();
	if(status == "Approved" || status == "APPROVED"){
	var url = "./leadPlanning.do?submitName=preparePage&leadNumber="+ leadNumber+"&customerName="+customerName+"&leadId="+leadId+"&status="+status+"&contactPerson="+contactPerson;
	showProcessing();
		var w = window
		.open(url,'newWindow','toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
		var watchClose = setInterval(function() {
			try {
				if (w.closed) {
					jQuery.unblockUI();
					clearTimeout(watchClose);
				}
			} catch (e) {
			}
		}, 200);
	}else{
		alert("Leads planning can be done only for approved leads");
	}
}

function leadFeedbackPopUp(){
	var leadNumber=$("#leadNumber").val();
	var customerName=$("#customerName").val();
	var leadId=$("#leadId").val();
	var status=$("#status").val();
	if(status == "Approved" || status == "APPROVED"){
	var url = './leadPlanning.do?submitName=prepareLeadsFeedBackPage&leadNumber='+ leadNumber+"&customerName="+customerName+"&leadId="+leadId;
	showProcessing();
		var w = window
		.open(url,'newWindow','toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
		var watchClose = setInterval(function() {
			try {
				if (w.closed) {
					jQuery.unblockUI();
					clearTimeout(watchClose);
				}
			} catch (e) {
			}
		}, 200);
	}else{
		alert("Leads Feedback can be done only for approved leads");
	}
}

/*function disableFeedBackPlaningPopUp(){
	var leadStatus = $("#status").val();
	if(!isNull(leadStatus) && leadStatus=='Approved' || leadStatus=='Active'){
		$("#feedbackBtn").attr("disabled", true);
		$("#planBtn").attr("disabled", true);
		alert("Planning or feedback cannot be done as lead is "+leadStatus);
		document.getElementById("feedbackBtn").disabled = false;
		document.getElementById("planBtn").disabled = false;
	}else{
		$("#feedbackBtn").attr("disabled", false);
		$("#planBtn").attr("disabled", false);
		document.getElementById("feedbackBtn").disabled = true;
		document.getElementById("planBtn").disabled = true;
	}
	
}*/


/*function enableCheckBox(){
	var courier =$("#competitorId1").val();
	var dox =$("#competitorId2").val();
	var nonDox =$("#competitorId3").val();
	var eComm =$("#competitorId4").val();
	var airCargo =$("#competitorId5").val();
	var trainCargo =$("#competitorId6").val();
	var international =$("#competitorId7").val();
	if(!isNull(courier)){
		//check1
		//$('#check1').attr("checked",true);
		document.getElementById("check1").checked = true;	
	}
	if(!isNull(dox)){
		//check2
		//$('#check2').attr("checked",true);
		document.getElementById("check2").checked = true;	
	}
	if(!isNull(nonDox)){
		//check3
		//$('#check3').attr("checked",true);
		document.getElementById("check3").checked = true;	
	}
	if(!isNull(eComm)){
		//check4
		//$('#check4').attr("checked",true);
		document.getElementById("check4").checked = true;	
	}
	if(!isNull(airCargo)){
		//check5
		//$('#check5').attr("checked",true);
		document.getElementById("check5").checked = true;	
	}
	if(!isNull(trainCargo)){
		//check6
		//$('#check6').attr("checked",true);
		document.getElementById("check6").checked = true;	
	}
	if(!isNull(international)){
		//check7
		//$('#check7').attr('checked');
		document.getElementById("check7").checked = true;	
	}
}*/
function enableCheckBox(){
	var courier =$("#competitorId1").val();
	var dox =$("#competitorId2").val();
	var nonDox =$("#competitorId3").val();
	var eComm =$("#competitorId4").val();
	var airCargo =$("#competitorId5").val();
	var trainCargo =$("#competitorId6").val();
	var international =$("#competitorId7").val();
	if(!isNull(courier)){
		//check1
		$('#check1').attr("checked",true);
		//document.getElementById("check1").checked = true;	
	}
	if(!isNull(dox)){
		//check2
		$('#check2').attr("checked",true);
		//document.getElementById("check2").checked = true;	
	}
	if(!isNull(nonDox)){
		//check3
		$('#check3').attr("checked",true);
		//document.getElementById("check3").checked = true;	
	}
	if(!isNull(eComm)){
		//check4
		$('#check4').attr("checked",true);
		//document.getElementById("check4").checked = true;	
	}
	if(!isNull(airCargo)){
		//check5
		$('#check5').attr("checked",true);
		//document.getElementById("check5").checked = true;	
	}
	if(!isNull(trainCargo)){
		//check6
		$('#check6').attr("checked",true);
		//document.getElementById("check6").checked = true;	
	}
	if(!isNull(international)){
		//check7
		$('#check7').attr("checked",true);
		//document.getElementById("check7").checked = true;	
	}
}

/*function generateECommerceQuotation() {
	var businessType =$("#businessType").val();
	var status=$("#status").val();
	if(status == "Approved" || status == "APPROVED"){
		if(!isNull(businessType)){
			document.getElementById("submitName").value = "viewEcommerceRateQuotation";
			var url = './leadValidation.do';
			document.createLeadForm.action=url;
			document.createLeadForm.submit();
		}else{
			alert("Business type not selected");
		}
	}else{
		alert("Quotation can be generated only for approved leads");
	}
}

function generateNormalQuotation() {
	var businessType =$("#businessType").val();
	var status=$("#status").val();
	if(status == "Approved" || status == "APPROVED"){
		if(!isNull(businessType)){
			document.getElementById("submitName").value = "viewRateQuotation";
			var url = './leadValidation.do';
			document.createLeadForm.action=url;
			document.createLeadForm.submit();
		}else{
			alert("Business type not selected");
		}
	}else{
		alert("Quotation can be generated only for approved leads");
	}
}*/

function generateQuotation(){
	
	urlExtn = "";
	urlExtn = urlExtn+ "&customerName="+document.getElementById("customerName").value;
	urlExtn = urlExtn+ "&leadNumber="+document.getElementById("leadNumber").value;
	urlExtn = urlExtn+"&contactPerson="+document.getElementById("contactPerson").value;
	urlExtn = urlExtn+"&contactNo="+document.getElementById("phoneNo").value;
	urlExtn = urlExtn+ "&mobileNo="+document.getElementById("mobileNo").value;
	urlExtn = urlExtn+"&address1="+document.getElementById("doorNoBuilding").value;
	urlExtn = urlExtn+"&address2="+document.getElementById("street").value;
	urlExtn = urlExtn+ "&address3="+document.getElementById("location").value;
	urlExtn = urlExtn+"&city="+document.getElementById("city").value;
	urlExtn = urlExtn+"&pincode="+document.getElementById("pincode").value;
	urlExtn = urlExtn+"&designation="+document.getElementById("designation").value;
	urlExtn = urlExtn+"&email="+document.getElementById("emailAddress").value;
	urlExtn = urlExtn+"&indTypeCode="+document.getElementById("industryTypeCode").value;
	
	
	var businessType =$("#businessType").val();
	var status=$("#status").val();
	if(status == "Approved" || status == "APPROVED"){
		if(!isNull(businessType)){
			if(businessType == "N"){
				var url = './leadValidation.do';
				//window.location = url;
				document.getElementById("submitName").value = "viewRateQuotation";
				document.createLeadForm.action=url;
				document.createLeadForm.submit();
				//window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,height=600,width=1001,left=50,top=50');
			} else 
			if(businessType == "E"){
				var url = './leadValidation.do';
				//window.location = url;
				document.getElementById("submitName").value = "viewEcommerceRateQuotation";
				document.createLeadForm.action=url;
				document.createLeadForm.submit();
				//window.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,height=600,width=1001,left=50,top=50');
			}
		}else{
			alert("Business type not selected");
		}
	}else{
		alert("Quotation can be generated only for approved leads");
	}
}
function OnlyAlphabetsAndNos(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 || (charCode >= 48 && charCode <= 57)||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function checkProductChange(obj){
	$("#potential"+obj).val("");
	$("#expectedVolume"+obj).val("");
}

function allowOnlyNumbers(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode >= 48 && charCode <= 57)||charCode==9 || charCode==8 ||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}
function allowOnlyAlphabets(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 ||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}
function allowOnlyNumbersAndDot(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode >= 48 && charCode <= 57)||charCode==9 || charCode==8 ||charCode==0||charCode==46){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function disableForm() {
	  var limit = document.forms[0].elements.length;
	  for (var i=0;i<limit;i++) {
	    document.forms[0].elements[i].disabled = true;
	  }
	}


function validateEmailAddress()
{
	var email=$("#emailAddress").val();
	/*var email = document.getElementById("emailAddress").value;
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
	return true;	*/
	if(validateEmail(email)){
		return true;
	}else{
		setTimeout(function(){$("#emailAddress").focus();},10);
		return false;
	}
}