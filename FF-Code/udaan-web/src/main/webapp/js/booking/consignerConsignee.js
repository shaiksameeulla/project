var selPriorityVal="";

/**
 * saveFileds
 *
 * @returns
 * @author sdalli
 */
function saveFileds(){
	
	var cnrName=document.getElementById('cnrName').value;
	var cnrMobile=document.getElementById('cnrMobile').value;
	var cnrPhone=document.getElementById('cnrPhone').value;
	var cnrAddress=document.getElementById('cnrAdress').value;
	var cneName=document.getElementById('cneName').value;
	var cneMobile=document.getElementById('cneMobile').value;
	var cnePhone=document.getElementById('cnePhone').value;
	var cneAddress=document.getElementById('cneAdress').value;
	var cnrPartyType = document.getElementById('cnrPartyType').value;
	var cnePartyType =  document.getElementById('cnePartyType').value;
	var prioritySelectval=document.getElementById("prioritySelect").value;
	var selectedPriorityService = jQuery('#prioritySelect option:selected').text();
	var CNnumber=document.getElementById("consgNo").value;
	var consgSeries = CNnumber.substring(4,5);


	if(consgSeries == "P" || consgSeries=="p"){
		if(isNull(prioritySelectval)){
			alert("Please select the Priortiy Service");
			setTimeout(function() {
			document.getElementById("prioritySelect").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	if (isNull($.trim(cnrName))) {
		alert("Please enter the Consignor Name.");
		setTimeout(function() {
			document.getElementById("cnrName").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull($.trim(cnrMobile))) {
		if (isNull($.trim(cnrPhone))) {
		alert("Please enter the Consignor Mobile Number.");
		setTimeout(function() {
			document.getElementById("cnrMobile").focus();
		}, 10);
		isValid = false;
		return isValid;
		}
	}
	
	if (isNull($.trim(cnrPhone))) {
		if (isNull($.trim(cnrMobile))) {
		alert("Please enter the Consignor Phone Number.");
		setTimeout(function() {
			document.getElementById("cnrPhone").focus();
		}, 10);
		isValid = false;
		return isValid;
		}
	}
	
	
	if (isNull($.trim(cneName))) {
		alert("Please enter the Consignee Name.");
		setTimeout(function() {
			document.getElementById("cneName").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	
	if (isNull($.trim(cneMobile))) {
		if (isNull($.trim(cnePhone))) {
		alert("Please enter the  Consignee Mobile Number.");
		setTimeout(function() {
			document.getElementById("cneMobile").focus();
		}, 10);
		isValid = false;
		return isValid;
		}
	}
	
	if (isNull($.trim(cnePhone))) {
		if (isNull($.trim(cneMobile))) {
		alert("Please enter the Consignee Phone Number.");
		setTimeout(function() {
			document.getElementById("cnePhone").focus();
		}, 10);
		isValid = false;
		return isValid;
		}
	}
	 window.opener.document.getElementById("cnrdetails"+rowCount).value = cnrName+"#"+cnrAddress+"#"+cnrMobile+"#"+cnrPhone+"#"+cnrPartyType;
	 window.opener.document.getElementById("cnedetails"+rowCount).value = cneName+"#"+cneAddress+"#"+cneMobile+"#"+cnePhone+"#"+cnePartyType;
	 window.opener.document.getElementById("deliveryTimeMapId"+rowCount).value=prioritySelectval;
	 
	 var consgTypeName = window.opener.document.getElementById("consgTypeName").value;
	 window.opener.document.getElementById("priorityServicedOns"+rowCount).value=selectedPriorityService
	 
	 var consgType = consgTypeName.split("#")[1];
	 if(consgType == "DOX") {
		 setTimeout(function() {
			 window.opener.document.getElementById("weightGmAV"+rowCount).focus();
			}, 10);
	 }
	 else if(consgType == "PPX") {
		 setTimeout(function() {
			 window.opener.document.getElementById("weightAW"+rowCount).focus();
			}, 10);
	 }
	 window.opener.SELECTED_SERVICED_ON=selectedPriorityService;
	 window.opener.SELECTED_CONSIGNMENT_TYPE=consgType;
	 window.opener.SELECTED_ROW_COUNT=rowCount;
	self.close();
}

/**
 * cancelFields
 *
 * @author sdalli
 */
function cancelFields(){
	self.close();
}

/**
 * getPriorityDtls
 *
 * @param rowCount
 * @author sdalli
 */
function getPriorityDtls(rowCount) {
	var consgNo= window.opener.document.getElementById("cnNumber"+rowCount).value;
	
	//Priority 
	var consgSeries = consgNo.substring(4,5);
	if("P" == consgSeries.toUpperCase()){
		selPriorityVal = window.opener.document.getElementById("deliveryTimeMapId"+rowCount).value;
	}
	
	
	document.getElementById("consgNo").value=consgNo;
	var consgSeries = consgNo.substring(4,5);
	if(consgSeries=="t" || consgSeries=="T"){
		document.getElementById("prioritySelect").disabled=true;
		document.getElementById("cnrName").focus();
	} else {
		document.getElementById("prioritySelect").disabled=false;
		document.getElementById("prioritySelect").focus();
	}
	var pincode=window.opener.document.getElementById("cnPincode"+rowCount).value;

	var url = './baBookingParcel.do?submitName=setPriorityServiceValues&pincode=' + pincode+ '&consgSeries='+consgSeries;
	ajaxCallWithoutForm(url,getPriorityDtlsResponse);
	
	var cnrDetails=window.opener.document.getElementById("cnrdetails"+rowCount).value;
	if(cnrDetails !=null && cnrDetails!="") {
		var keyValList = cnrDetails.split("#");
	   	document.getElementById('cnrName').value = $.trim(keyValList[0]);
   		document.getElementById('cnrMobile').value= $.trim(keyValList[2]);
   		document.getElementById('cnrPhone').value= $.trim(keyValList[3]);
   		document.getElementById('cnrAdress').value= $.trim(keyValList[1]);	
   		
   		
	}
				
	
	var cneDetails=window.opener.document.getElementById("cnedetails"+rowCount).value;
	if(cneDetails !=null && cneDetails!="") {
		var keyValPair = cneDetails.split("#");
			document.getElementById('cneName').value = $.trim(keyValPair[0]);
			document.getElementById('cneMobile').value= $.trim(keyValPair[2]);
			document.getElementById('cnePhone').value= $.trim(keyValPair[3]);
			document.getElementById('cneAdress').value= $.trim(keyValPair[1]);
	}
	
}


/**
 * getPriorityDtlsResponse
 *
 * @param data
 * @author sdalli
 */
function getPriorityDtlsResponse(data) {
	//if(!isNull(window.opener.SELECTED_SERVICED_ON))
	//	jQuery('#prioritySelect option:selected').text(window.opener.SELECTED_SERVICED_ON);
var prioritySelect = document.getElementById("prioritySelect");
$.each(data, function(index, value) {
	var option;
	option = document.createElement("option");
	option.value = this.pincodeDeliveryTimeMapId;
	if(!isNull(window.opener.SELECTED_SERVICED_ON)){
		if(window.opener.SELECTED_SERVICED_ON == this.deliveryTime){
			option.selected = 'selected';
		}
	}
	option.appendChild(document.createTextNode(this.deliveryTime));
	prioritySelect.appendChild(option);
});
}


/**
 * setDefaultValues
 *
 * @param rowCount
 * @author sdalli
 */
function setDefaultValues(rowCount) {
	if (rowCount > 0) {
		var cneDetailsArr = new Array();
		var cnrDetailsArr = new Array();
		var cneDetails =  window.opener.document.getElementById("cnrdetails"+rowCount).value;
		var cnrDetails = window.opener.document.getElementById("cnedetails"+rowCount).value;
		if(cneDetails !=null && cneDetails!=""){
			cneDetailsArr = cneDetails.split("#");
		}
	} else {
		var cneDetails =  window.opener.document.getElementById("cnrdetails").value;
		var cnrDetails = window.opener.document.getElementById("cnedetails").value;
	}

}

function getCustomerDtls(rowCount) {
	var bookingType = window.opener.document.getElementById("bookingType").value;
	if(bookingType == "CR") {
		var customerId =  window.opener.document.getElementById("customerIds"+rowCount).value;
		if(!isNull(customerId)) {
			var url = './creditCustomerBookingParcel.do?submitName=getCustometDtls&customerId=' + customerId;
			//ajaxCallWithoutForm(url,getCustomerDtlsCallback);
			 $.ajax({
		          url: url,
		          success: function(data){getCustomerDtlsCallback(data,rowCount);}
		    });
		}	
	}
}

function getCustomerDtlsCallback(data,rowCount){
	var customer = data;
	if (!isNull(customer)) {
		if (!isNull(customer.address.name)){
			document.getElementById("cnrName").value = customer.address.name;
		}
		var address = customer.address.address1 +","+customer.address.address2+","+customer.address.address3;
		document.getElementById("cnrAdress").value = address;
		if(!isNull(address.mobile))
		document.getElementById("cnrMobile").value = address.mobile;
		if(!isNull(address.phone))
		document.getElementById("cnrPhone").value = address.phone;
		
	}
	
}

/**
 * ESCclose
 *
 * @param evt
 * @author sdalli
 */
function ESCclose(evt) {
	if (evt.keyCode == 27)
		window.close();
}
