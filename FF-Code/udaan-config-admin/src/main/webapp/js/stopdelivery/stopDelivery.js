function stopDeliveryStartUp(){
	
	document.getElementById("consgNo").focus();
}


function isValidConsignment(consgNoObj) {
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

		consgNoObj.value = $.trim(consgNoObj.value);

		if (isNull(consgNoObj.value)) {
			return false;
		}

		if (consgNoObj.value.length != 12) {
			// clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12
			// characters only!");
			alert("Consignment No. should contain 12 characters only!");
			//document.getElementById("consgNumber").value = "";
			cancelStopDelivery();
			document.getElementById("consgNo").focus();
			return false;
		}

		if (!consgNoObj.value.substring(0, 1).match(letters)
				|| (!consgNoObj.value.substring(4, 5).match(letters) && !consgNoObj.value
						.substring(1, 4).match(alphaNumeric))
				|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
				|| !numpattern.test(consgNoObj.value.substring(5))) {
			// clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not
			// correct!");
			alert("Consignment No. Format is not correct!");
			//document.getElementById("consgNumber").value = "";
			cancelStopDelivery();
			document.getElementById("consgNo").focus();
			return false;
		}
	return true;
}

function getConsgDeatils(){
	//var consgNo=$("#consgNo" ).val();
	
	var consgNo = document.getElementById("consgNo");
	if(!isNull(consgNo.value)){
	if(isValidConsignment(consgNo)){
		
		 var url = './stopDelivery.do?submitName=searchStopDelivery&ConsignmentNo='+ consgNo.value;
		 
			ajaxCall(url, "stopDeliveryForm", searchCallback);
		 //ajaxCallWithoutForm(url, getStationList);
	 }
	}else{
		alert('Consignment No Is Empty');
	}
}

function searchCallback(data){
	cancelStopDelivery();
    var stopDeliveryTo = eval('(' + data + ')');
	
	if(!isNull(stopDeliveryTo.errorMessage)){
		alert(stopDeliveryTo.errorMessage);
		clearVendorDetails();
		return;
		
	} else if(!isNull(stopDeliveryTo.successMessage)){
		alert(stopDeliveryTo.successMessage);
		return;
	}
	else{
	//var detailsTo = eval('(' + data + ')');
	//document.getElementById("date").value=stopDeliveryTo.date;
	document.getElementById("consgNo").value=stopDeliveryTo.consgNo;
	document.getElementById("bookingDate").value=stopDeliveryTo.bookingDate;
	document.getElementById("pincode").value=stopDeliveryTo.pincode;
	document.getElementById("weight").value=stopDeliveryTo.weight;
	if(!isNull(stopDeliveryTo.customer)){
	document.getElementById("customer").value=stopDeliveryTo.customer;
	}
	if(!isNull(stopDeliveryTo.codLcTopay)){
	document.getElementById("codLcTopay").value=stopDeliveryTo.codLcTopay;
	}
	if(!isNull(stopDeliveryTo.reasonList)){
		
		 reasonDropDown(stopDeliveryTo.reasonList);
		 document.getElementById("reasonList").focus();
	}
  }
}

function submitStopDelivery(){
	var consgNo = document.getElementById("consgNo");
	var reason=$("#reasonList" ).val();
	var remark=$("#remarks" ).val();
	if(!isNull(consgNo.value)){
	 if(!isNull(reason)){
	   if(isValidConsignment(consgNo)){
		
		 var url = './stopDelivery.do?submitName=submitStopDelivery&ConsignmentNo='+ consgNo.value + "&reasonId=" + reason +"&remark=" +remark;
		 
			ajaxCall(url, "stopDeliveryForm", submitCallback);
		 //ajaxCallWithoutForm(url, getStationList);
	       }
	     }
	     else{
		  alert('Please Select Stop Delivery Reason');   
		  document.getElementById("reasonList").focus();
	    }
	  }else{
		alert('Consignment No Is Empty');
		document.getElementById("consgNo").focus();
	}
	
}




function reasonDropDown(reasonTOList){
	reason = "reasonList";
	/*clearDropDownList(office);*/
	$("#reasonList" ).empty();
	addOptionTODropDown(reason, "----select----", "");
	if(!isNull(reasonTOList)){
		//addOptionTODropDown(office, ALL, officeCode);
		for(var i=0;i<reasonTOList.length;i++) {
			addOptionTODropDown(reason, reasonTOList[i].reasonName,reasonTOList[i].reasonId);
		}
	}
}

function addOptionTODropDown(selectId, label, value){
	
	var obj=getDomElementById(selectId);
		opt = document.createElement('OPTION');
		opt.value = value;
		opt.text = label;
		 obj.options.add(opt);
	
}

function cancelStopDelivery(){
/*	var url = './stopDelivery.do?submitName=preparePage';
	document.stopDeliveryForm.action = url;
	document.stopDeliveryForm.submit();*/
	$("#reasonList" ).empty();
	document.getElementById("consgNo").value="";
	document.getElementById("bookingDate").value="";
	document.getElementById("pincode").value="";
	document.getElementById("weight").value="";
	document.getElementById("customer").value="";
	document.getElementById("codLcTopay").value="";
	 document.getElementById("remarks").value="";

}

function submitCallback(data){
var stopDeliveryTo = eval('(' + data + ')');
	
	if(!isNull(stopDeliveryTo.errorMessage)){
		alert(stopDeliveryTo.errorMessage);
		cancelStopDelivery();
		return;
		
	} else if(!isNull(stopDeliveryTo.successMessage)){
		alert(stopDeliveryTo.successMessage);
		cancelStopDelivery();
		return;
	}
}


function canceledStopDelivery(){
 if(confirm("Do you want to cancel?")) {
	$("#reasonList" ).empty();
	document.getElementById("consgNo").value="";
	document.getElementById("bookingDate").value="";
	document.getElementById("pincode").value="";
	document.getElementById("weight").value="";
	document.getElementById("customer").value="";
	document.getElementById("codLcTopay").value="";
	document.getElementById("remarks").value="";
  }
}