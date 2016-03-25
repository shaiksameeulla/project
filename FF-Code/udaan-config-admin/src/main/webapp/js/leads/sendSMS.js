function cancelPage(){
	if(confirm("Do you want to Cancel the details?")){
		window.close();
	}
}

function checkMandatory(){
	var errorsData="";
	var msg = $("#description").val();
	if($('#customer').attr('checked') || $('#salesExecutive').attr('checked')){
		errorsData;
	}
	else{
		errorsData = errorsData+"Please select atleast one recipient."+"\n";	
	}
	if(isNull(msg)){
		errorsData = errorsData+"Please enter some message."+"\n";	
	}
	if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{
    	return true;
    }
}
/*function sendSMS(){
	if(checkMandatory()&&checkMsgLength()){
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
}*/

function sendSMS(){
	if(checkMandatory()&&checkMsgLength())
	{
		var leadNumber = window.opener.document.getElementById("leadNumber").value;
		if(document.getElementById("salesExecutive").checked == true){
			var salesExecutive = document.getElementById("salesExecutive").value;
			var url = './sendSMS.do?submitName=sendSMS&leadNumber='
				+ leadNumber+"&salesExecutive="+salesExecutive;
			jQuery.ajax({
				url : url,
				data : jQuery("#sendSMSForm").serialize(),
				success : function(req) {
					callBack(req);
				}
			});
		}else if(document.getElementById("customer").checked == true){
			var customer = document.getElementById("customer").value;
			var url = './sendSMS.do?submitName=sendSMS&leadNumber='
				+ leadNumber+"&customer="+customer;
			jQuery.ajax({
				url : url,
				data : jQuery("#sendSMSForm").serialize(),
				success : function(req) {
					callBack(req);
				}
			});
			}
	}
}

function callBack(data){
	if(data != null){
		if(data == "Success"){
			alert("SMS sent successfully");
			self.close();
		}else{
			alert("SMS could not be sent successfully");
		}
	}
	
}
function checkMsgLength(){
	var msg = $("#description").val();
	if(msg.length > 160)
	{
	alert("Message can contain only 160 characters");
	setTimeout(function(){subject.focus();},10);
	return false;
	}
return true;
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