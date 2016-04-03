function cancelPage(){
	window.close();
}

function checkMandatory(){
	//var msg = document.getElementById("description").value;
	var msg = $("#description").val();
	if(isNull(msg)){
		alert("Please enter some message");
		setTimeout(function(){subject.focus();},10);
		return false;
	}
	if(msg.length > 160)
		{
		alert("Message can contain only 160 characters");
		setTimeout(function(){subject.focus();},10);
		return false;
		}
	return true;
}
function sendSMS(){
	if(checkMandatory()){
	var leadNumber = window.opener.document.getElementById("leadNumber").value;
	if(document.getElementById("salesExecutive").checked == true){
		var salesExecutive = document.getElementById("salesExecutive").value;
		var url = './sendSMS.do?submitName=sendSMS&leadNumber='
			+ leadNumber+"&salesExecutive="+salesExecutive;
		document.sendSMSForm.action = url;
		document.sendSMSForm.submit();	
		alert("message sent successfully");
		self.close();
	}else if(document.getElementById("customer").checked == true){
		var customer = document.getElementById("customer").value;
		var url = './sendSMS.do?submitName=sendSMS&leadNumber='
			+ leadNumber+"&customer="+customer;
		document.sendSMSForm.action = url;
		document.sendSMSForm.submit();
		alert("message sent successfully");
		self.close();
		}
	}
}
/*function sendSMS(){
	if(checkMandatory()){
	//var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var leadNumber = window.opener.$("#leadNumber").val();
	if($('#salesExecutive').attr('checked')){
		var salesExecutive = $('#salesExecutive').val();
		var url = './sendSMS.do?submitName=sendSMS&leadNumber='
			+ leadNumber+"&salesExecutive="+salesExecutive;
		document.sendSMSForm.action = url;
		document.sendSMSForm.submit();	
		alert("message sent successfully");
		self.close();
	}else if($('#customer').attr('checked')){
		var customer = $('#customer').val();
		var url = './sendSMS.do?submitName=sendSMS&leadNumber='
			+ leadNumber+"&customer="+customer;
		document.sendSMSForm.action = url;
		document.sendSMSForm.submit();
		alert("message sent successfully");
		self.close();
		}
	}
}*/