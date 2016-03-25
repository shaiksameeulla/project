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

function cancelPage(){
	if(confirm("Do you want to Cancel the details?")){
		window.close();
	}
	}
	

function validateEmailAddress(email) {
	/*var regExp = /(^[a-z]([a-z_\.]*)@([a-z_\.]*)([.][a-z]{3})$)|(^[a-z]([a-z_\.]*)@([a-z_\.]*)(\.[a-z]{3})(\.[a-z]{2})*$)/i;*/
	var regExp = /^[a-z0-9_\+-]+(\.[a-z0-9_\+-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*\.([a-z]{2,4})$/i;
	return regExp.test(email);
	}
	 
function validateEmails(){
	//var emails = document.getElementById("sentTo").value;
	var emails = $("#sentTo").val();
	var emailArray = emails.split(";");
	var invEmails = "";
	for(var i = 0; i <= (emailArray.length - 1); i++){
	     if(validateEmailAddress(emailArray[i])){
	    	 emailArray[i];
	     }else{
	         invEmails += emailArray[i] + " ";
	    }
	    }
	    /*if(invEmails.trim() != ""){
	        alert("Invalid emails:\n" + invEmails);
	        setTimeout(function(){$("#sentTo" ).focus();},10);
	    }*/
	    if(invEmails.replace(/^\s+|\s+$/, '')){
	        alert("Invalid emails:\n" + invEmails);
	        return false;
	        $("#sentTo" ).val("");
	        setTimeout(function(){$("#sentTo" ).focus();},10);
	    }
	    else{
	    	 return true;
	    }
	}
function validateCopy(){
	//var emails = document.getElementById("sentCc").value;
	var emails = $("#sentCc").val();
	var emailArray = emails.split(";");
	var invEmails = "";
	for(var i = 0; i <= (emailArray.length - 1); i++){
	     if(validateEmailAddress(emailArray[i])){
	    	 emailArray[i];
	     }else{
	         invEmails += emailArray[i] + " ";
	        // invEmails.trim();
	        invEmails.replace(/^\s+|\s+$/, '');
	    }
	    }
	    if(invEmails.replace(/^\s+|\s+$/, '')){
	        alert("Invalid emails:\n" + invEmails);
	        return false;
	         $("#sentCc" ).val("");
	        setTimeout(function(){$("#sentCc" ).focus();},10);
	    }else{
	    	return true;
	    }
	}
/*function toggle(radBtn){
    switch(radBtn.id)
    {
    case 'salesExecutive':
    	document.getElementById("customer").disabled = false;
    	break;
    case 'customer':
    	document.getElementById("salesExecutive").disabled = false;
    	break;
    }
}*/
function checkMandatory(){
	//var recipient = document.getElementById("sentTo").value;
	//var subject = document.getElementById("subject").value;
	var errorsData="";
	var recipient = $("#sentTo").val();
	var subject = $("#subject").val();
	if($('#salesExecutive').attr('checked') || $('#customer').attr('checked')){
		errorsData;
	}
	else{
		errorsData = errorsData+"Please select atleast one recipient."+"\n";	
	}
	/*if(isNull(recipient)){
		alert("Please enter a recipient name");
		setTimeout(function(){recipient.focus();},10);
		return false;
	}
	if(isNull(subject)){
		alert("Please enter Subject");
		setTimeout(function(){subject.focus();},10);
		return false;
	}
	return true;*/
	if(isNull(recipient.trim())){
		errorsData = errorsData+"Please enter a recipient name."+"\n";	
	}
	if(isNull(subject.trim())){
		errorsData = errorsData+"Please enter Subject."+"\n";	
	}
	if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{
    	return true;
    }
}



/*function sendEmail(){
	if(checkMandatory() && validateEmails() && validateCopy()){
	//var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var leadNumber = window.opener.$("#leadNumber").val();
	//var salesExecutive = document.getElementById("salesExecutive").value;
	//var customer = document.getElementById("customer").value;
	var customer = $("#customer").val();
	var url = './sendEmail.do?submitName=sendEmail&leadNumber='
		+ leadNumber +"&customer="+ customer;
	document.sendEmailForm.action = url;
	document.sendEmailForm.submit();
	alert("Email sent successfully");
	setTimeout(function() {
		window.close();
	}, 2000);
	}
}*/

function sendEmail(){
	if(checkMandatory() && validateEmails() && validateCopy()){
		var leadNumber = window.opener.$("#leadNumber").val();
		var customer = $("#customer").val();
		var url = './sendEmail.do?submitName=sendEmail&leadNumber='
			+ leadNumber +"&customer="+ customer;
		jQuery.ajax({
			url : url,
			data : jQuery("#sendEmailForm").serialize(),
			success : function(req) {
				callBack(req);
			}
		});
	}
}

function callBack(data){
	if(data != null){
		if(data == "Success"){
			alert("Email sent successfully");
			self.close();
		}else{
			alert("Email could not be sent successfully");
		}
	}
}

function emailSalesExecutive(){
	//var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var leadNumber = window.opener.$("#leadNumber").val();
	if(!isNull(leadNumber)) {
		//showProcessing();
		var url = './sendEmail.do?submitName=emailSalesExecutive&leadNumber='
			+ leadNumber;
		jQuery.ajax({
			url : url,
			success : function(req) {
				emailSales(req);
			}
		});
	}	
}

function emailSales(ajaxResp){
	if(!isNull(ajaxResp)){
		if(isJsonResponce(ajaxResp)){
			return ;
		}
		var leadInfo = eval('(' + ajaxResp + ')');
			$("#sentTo").val(leadInfo.assignedTo.empTO.emailId);
	}
	//hideProcessing();
}

function emailCustomer(){
	//var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var leadNumber = window.opener.$("#leadNumber").val();
	if(!isNull(leadNumber)) {
		//showProcessing();
		var url = './sendEmail.do?submitName=emailCustomer&leadNumber='
			+ leadNumber;
		jQuery.ajax({
			url : url,
			success : function(req) {
				emailCust(req);
			}
		});
	}	
}
function emailCust(ajaxResp){
	if(!isNull(ajaxResp)){
		if(isJsonResponce(ajaxResp)){
			return ;
		}
		var leadInfo = eval('(' + ajaxResp + ')');
			$("#sentTo").val(leadInfo.emailAddress);
	}
	//hideProcessing();
}
