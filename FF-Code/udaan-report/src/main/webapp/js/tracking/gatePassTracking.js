var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

function trackGatepass(){
	
    var cragNum = document.getElementById("number").value;
    
	
	var type = document.getElementById("typename").value;
	if(isNull(type)){
		 alert('Please select type');
		}
	if(cragNum==''){
		 alert('Please enter CC/RR/AWB Gate Pass');
		/* document.getElementById("manifestNumber").innerHTML='';*/
		}
	else{
		/*document.getElementById("manifestNumber").innerHTML="Manifest tracking information for      " + manifest;*/
		url = "./gatePassTracking.do?submitName=viewGatepassTrackInformation&type="+type+"&number="+cragNum;
		ajaxCallWithoutForm(url,populateCrag);
     }
}	
function populateCrag(data){
       
	if(data!=null)
		{
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		
		document.getElementById("originoffice").value = data.loadMovementTO.originOffice;
		document.getElementById("destination").value = data.loadMovementTO.destOffice;
		document.getElementById("bagDispatched").value = data.bagsDispatch;
		document.getElementById("bagReceived").value = data.bagsReceive;
		document.getElementById("mode").value = data.loadMovementTO.transportMode;
		document.getElementById("ftvNo").value = data.loadMovementTO.vehicleNumber;
		document.getElementById("dispatchedDate").value = data.dispatchDate;
		document.getElementById("receiveDate").value = data.receiveDate;
		document.getElementById("dispatchedwt").value = data.dispReceiveWt;
		document.getElementById("receivedwt").value = data.dispReceiveWt;
		
		if(data.manifestTOs!=null && data.remarks!=null){
			for(var i=0 ; i<data.manifestTOs.length; i++){
				var childManifest = data.manifestTOs[i];
				var childRemark = data.remarks[i];
				if( data.dispatchDate!=""){
					var date =data.dispatchDate;
					addChildManifestRows(childManifest,childRemark,date);
				}
				if( data.receiveDate!=""){
					var date =data.receiveDate;
					addChildManifestRows(childManifest,childRemark,date);
				}
			   }	
			 }
		
		if(data.manifestTO!=null && data.remark!=null){
			var childManifest = data.manifestTO;
			var childRemark = data.remark;
			if( data.dispatchDate!=""){
				var date =data.dispatchDate;
				addChildManifestRows(childManifest,childRemark,date);
			}
			if( data.receiveDate!=""){
				var date =data.receiveDate;
				addChildManifestRows(childManifest,childRemark,date);
			}
			
			
		}
		
	 }
	
	else{
		
		alert("No details available");
		
	}
		
		

}




function addChildManifestRows(childManifest,childRemark,date){
	  $('#example').dataTable().fnAddData([childManifest.manifestNumber,childManifest.manifestWeight,childManifest.manifestStatus,date,childRemark]);
	 
}


function isValidNumber(numberObj){

	var type = document.getElementById("typename").value;
	if(type=='GP'){
	if (isNull(numberObj.value)) {
		return false;
	}

	if (numberObj.value.length!=12) {
		alert("Gatepass No. should contain 12 characters only!");
		numberObj.value = "";
		setTimeout(function() {
			numberObj.focus();
		}, 10);
		return false;
	}
	
	var numpattern = /^[0-9]{3,20}$/;
    var fourthCharG = /^[G]$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	if (numberObj.value.substring(0, 1).match(fourthCharG)) {
		if (numberObj.value.substring(1, 5).match(alphaNumeric)) {
			if (numpattern.test(numberObj.value.substring(5))) {
				return true;
			} else {
				alert('Gatepass Format is not correct');
				numberObj.value = "";
				setTimeout(function() {
					numberObj.focus();
				}, 10);
				return false;
			}
		} else {
			alert('Gatepass Format is not correct');
			numberObj.value = "";
			setTimeout(function() {
				numberObj.focus();
			}, 10);
			return false;
		}
	} else {
		alert('Format is not correct');
		numberObj.value = "";
		setTimeout(function() {
			numberObj.focus();
		}, 10);
		return false;
	}
 }
	
	
	else if(type=='CRAWB'){
		
		if (isNull(numberObj.value)) {
			return false;
		}

		if (numberObj.value.length!=10) {
			alert("CD/RR/AWB No. should contain 10 characters only!");
			numberObj.value = "";
			setTimeout(function() {
				numberObj.focus();
			}, 10);
			return false;
		}
		else{
			return true;
		}
		
	}
}

function clearScreen(action){
	var url = "./gatePassTracking.do?submitName=viewGatepassTracking";
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		document.getElementById("number").value="";
		document.gatepassTrackingForm.action = url;
		document.gatepassTrackingForm.submit();
	}
}
