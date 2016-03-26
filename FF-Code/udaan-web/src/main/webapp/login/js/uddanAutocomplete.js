var custId = new Array();
var custName = new Array();

var userId = null;
var userName = null;

function getUsers(userType) {
	document.getElementById("userCode").value="";
	var url = "./udaanAutocomplete.do?submitName=getUsersAutocomplete&userType="+userType;
	jQuery.ajax({
		url : url,
		success : function(req) {
			displayUsers(req);
		}
	});
}

function displayUsers(obj){
	 userId = new Array();
	 userName = new Array();
	 if (obj != "" && obj != null) {
			var newStr=obj.replace("{","");
			var newStr1=newStr.replace("}","");
			var keyValList = newStr1.split(",");
			for(i=0; i < keyValList.length; i++){
				var keyValPair = keyValList[i].split("=");
				userId[i] = keyValPair[0].trim();
				userName[i] = keyValPair[1];	
			}
		}
	 
	//getAutoCompleteKeyValueArrays(obj,userId,userName);
	jQuery("#userCode").autocomplete(userName);
}

function getCustomers() {
	var url = "./udaanAutoComplete.do?submitName=getCustomersAutocomplete";
	jQuery.ajax({
		url : url,
		success : function(req) {
			printCustomerDetails(req);
		}
	});
}




function printCustomerDetails(obj) {		
		getAutoCompleteKeyValueArrays(obj,custId,custName);
		
}

function displayCustomers(rowId) {
	jQuery("#businessName"+rowId).autocomplete(custId);
}



function getCustomerIdByName(obj,rowCount) {
		var custNameSelected = "";
		custNameSelected = obj.value;	
		var customerId = getKeyForAutoCompletedValue(custId,custName,custNameSelected);
		if(customerId !=null){
			document.getElementById('custId'+rowCount).value=customerId;			
		}else if(custNameSelected != ""){
			alert("Invalid customer. Please select again!");
		}	
}


/** AutoComplete Functionality
 * 
 * Create Key and Value Array from the Ajax Respose (i.e. Map<k,V>)*/
 
function getAutoCompleteKeyValueArrays(responseData,keyArray,valueArray){
	var ajaxResponse = responseData.responseText;
	if (ajaxResponse != "" && ajaxResponse != null) {
		var newStr=ajaxResponse.replace("{","");
		var newStr1=newStr.replace("}","");
		var keyValList = newStr1.split(",");
		for(i=0; i < keyValList.length; i++){
			var keyValPair = keyValList[i].split("=");
			keyArray[i] = keyValPair[0].trim();
			valueArray[i] = keyValPair[1].trim();
			alert('getAutoCompleteKeyValueArrays'+valueArray[i]);
		}
	}

}

/*  Get the Key for Value Selected in the Auto Complete */ 
function getKeyForAutoCompletedValue(keyArray,valueArray,selectedValue){
	for(j=0; j< valueArray.length; j++){
		if(valueArray[j]== selectedValue){
			return(keyArray[j]);
		}
	}
}