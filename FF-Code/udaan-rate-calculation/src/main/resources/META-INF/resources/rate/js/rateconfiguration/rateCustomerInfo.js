function searchCustInfo(){	
	var custName = document.getElementById("custName").value;
	if(!isNull(custName)){
	document.rateContractForm.action = "./rateContract.do?submitName=searchCustomerInfo&custName="+custName;
	document.rateContractForm.submit();
	}else{
		alert("Please enter customer name");
	}
}

function customerEnterKeyNav(e){
	
	var charCode;
	
	
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	if(charCode == 13){	
			
		return false;
	}
	
		
	return true;
}