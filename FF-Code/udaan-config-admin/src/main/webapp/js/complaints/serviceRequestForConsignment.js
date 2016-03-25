$(document).ready(function() {     
	defaultChanges();
} ); 

function defaultChanges(){
	$("#searchType").val('SREQ');

}

function clearPage(){
	if(confirm("Do you want to clear the page?")){
		cancelPage();
	}
}

function sendEmail(){
	var url = './serviceRequestForConsignment.do?submitName=sendEmail';
	document.serviceRequestForConsignmentForm.action = url;
	document.serviceRequestForConsignmentForm.submit();
}
function saveOrCloseServiceConsg(){
	/*if($('#caller').attr('checked')){
		sendEmail();
	}
	else if($('#consignor').attr('checked')){
		sendSMSToConsignor();
	}
	else if($('#consignee').attr('checked')){
		sendSMSToConsignee();
	}*/
	if(! mandatoryFields()){
		flag=confirm("Do you want to Save the Service Request Details");
		if(!flag){
			return;
		}
			var url="./serviceRequestForConsignment.do?submitName=savePlanServiceConsigDetails";
			document.serviceRequestForConsignmentForm.action = url;
			document.serviceRequestForConsignmentForm.submit();	
	}
	
			
}

function mandatoryFields() {
  	var msg = "Please provide : ";
	var isValid = true;
	var focusObj = callerName;
    var callerName = document.getElementById("callerName");
    var callerPhone = document.getElementById("callerPhone");
    var callerEmail = document.getElementById("callerEmail");
  
    if(isNull(callerName.value)){
        
    	if(isValid)	focusObj = callerName;
		msg = msg + ((!isValid)?", ":"") + "Caller Name";
		isValid=false;
    }

    if (isNull(callerPhone.value)) {
        
    	if(isValid)	focusObj = callerPhone;
		msg = msg + ((!isValid)?", ":"") + "Caller Phone";
		isValid=false;
    }
    if(isNull(callerEmail.value)){  
    	if(isValid)	focusObj = callerEmail;
		msg = msg + ((!isValid)?", ":"") + "Caller Email";
		isValid=false;
    }
 
    if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	return isValid;
}


function printCallBackSave(data){
	refershPage();
}
function refershPage(){
	var url = "./serviceRequestForConsignment.do?submitName=preparePage";
	window.location = url;
}

function trackingPopUp(){
		
//		var url = './leadPlanning.do?submitName=prepareLeadsFeedBackPage&leadNumber='+ leadNumber+"&customerName="+customerName+"&leadId="+leadId;
		var url = './serviceRequestForConsignment.do?submitName=getTrackingDtls';
			window
			.open(url,'newWindow','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=1000,height=600,left = 200,top = 184');
			//document.createLeadForm.action=url;
			//document.createLeadForm.submit();
	}

function sendSMSToConsignor(){
	 var consignor =  $('#consignor').val();
	 var cosnsigNo = $('#cosnsigNo').val();
	 var url = './serviceRequestForConsignment.do?submitName=sendSMS&consignor='+ consignor +"&cosnsigNo=" + cosnsigNo;
		document.serviceRequestForConsignmentForm.action = url;
		document.serviceRequestForConsignmentForm.submit();
}
function sendSMSToConsignee(){
	 var consignee =  $('#consignee').val();
	 var cosnsigNo = $('#cosnsigNo').val();
	 var url = './serviceRequestForConsignment.do?submitName=sendSMS&consignee='+ consignee +"&cosnsigNo=" + cosnsigNo;
		document.serviceRequestForConsignmentForm.action = url;
		document.serviceRequestForConsignmentForm.submit();
}


function mandatoryFields() {
  	var msg = "Please provide : ";
	var isValid = true;
	var focusObj = callerName;
    var callerName = document.getElementById("callerName");
    var callerPhone = document.getElementById("callerPhone");
    var callerEmail = document.getElementById("callerEmail");
   /* var destinationRegionId = document.getElementById("destinationRegionId");
    var destCity = document.getElementById("destCity");
    var destOfficeType = document.getElementById("destOfficeType");
    var destOffice = document.getElementById("destOffice");
    var bagLockNo = document.getElementById("bagLockNo");*/

    if(isNull(callerName.value)){
         /* alert("Please enter the BPL Number.");
          setTimeout(function() {
                document.getElementById("manifestNo").focus();
          }, 10);
          isValid = false;
          return isValid;*/
    	//errorsData = errorsData+"Please enter the BPL Number."+"\n";
    	if(isValid)	focusObj = callerName;
		msg = msg + ((!isValid)?", ":"") + "Caller Name";
		isValid=false;
    }

    if (isNull(callerPhone.value)) {
         /* alert("Please select the Destination Office");
          setTimeout(function() {
                document.getElementById("destOffice").focus();
          }, 10);
          isValid = false;
          return isValid;*/
    	/*errorsData = errorsData+"Please select the Destination Office."+"\n";*/
    	// missingFields=missingFields+"Destination Office,";
    	if(isValid)	focusObj = callerPhone;
		msg = msg + ((!isValid)?", ":"") + "Caller Phone";
		isValid=false;
    }
    if(isNull(callerEmail.value)){  
    	if(isValid)	focusObj = callerEmail;
		msg = msg + ((!isValid)?", ":"") + "Caller Email";
		isValid=false;
    }
 
    if(!isValid){
		alert(msg);
		setTimeout(function(){focusObj.focus();},10);
	}
	return isValid;
}

function trackconsignment(){
	
	var consgNo = document.getElementById("consgNumber").value;
	var obj = document.getElementById("consgNumber");
	var type = document.getElementById("typeName").value;
		if(isNull(consgNo)){
		 alert('Please enter Consg/Ref.no');
		 document.getElementById("consgNo").innerHTML='';
		}
		 if(isNull(type )){
			alert('Please select type');
			return;
		}
		 if(type == 'RN'){
			if (isValidConsgNo(obj,type)){
				alert('Consignment Number not allowed');
				document.getElementById("consgNumber").value="";
				return;
			}
		}
		 if(type == 'CN'){
			 isValidConsgNum(obj);
		 }
		
	url = "./serviceRequestForConsignment.do?submitName=viewTrackInformation&type="+type+"&number="+consgNo;
	ajaxCallWithoutForm(url,populateConsignment);
	
}

function populateConsignment(data){
	if(data!=null){

		/*document.getElementById("firstName").value = data.bookingTO.consignor.firstName;
		document.getElementById("cityName").value = data.bookingTO.consignor.orgCity;
		document.getElementById("address").value = data.bookingTO.consignor.address;
		document.getElementById("pincode").value = data.bookingTO.consignor.orgPincode;
		document.getElementById("phone").value = data.bookingTO.consignor.phone;
		document.getElementById("state").value = data.bookingTO.consignor.orgState;
		document.getElementById("mobile").value = data.bookingTO.consignor.mobile;
		
		document.getElementById("firstname").value = data.bookingTO.consignee.firstName;
		document.getElementById("city").value = data.bookingTO.consignee.destCity;
		document.getElementById("adress").value = data.bookingTO.consignee.address;
		document.getElementById("pincodes").value = data.bookingTO.consignee.destPincode;
		document.getElementById("phones").value =  data.bookingTO.consignee.phone;
		document.getElementById("State").value = data.bookingTO.consignor.destState;
		document.getElementById("mobiles").value = data.bookingTO.consignee.mobile;
		
		document.getElementById("pickdate").value = data.pckDate;
		
		document.getElementById("bookingoffice").value =  data.bookingTO.officeName;
		document.getElementById("destoffice").value =  data.bookingTO.cityName;
		document.getElementById("consgtype").value =  data.bookingTO.consgTypeName;
		document.getElementById("bookdate").value =  data.bookingTO.bookingDate;
		document.getElementById("addr").value =  data.bookingTO.consignor.address;
		document.getElementById("finalwt").value =  data.bookingTO.finalWeight;
		document.getElementById("mobile").value =  data.bookingTO.consignor.phone;
		document.getElementById("paperworkno").value =  data.bookingTO.paperWorkRefNo;
		document.getElementById("paperworktype").value =  data.bookingTO.paperWork;
		document.getElementById("actualwt").value =  data.bookingTO.actualWeight;
		document.getElementById("volweight").value =  data.bookingTO.volWeight;
		document.getElementById("insured").value =  data.bookingTO.insuredBy;
		document.getElementById("length").value =  data.bookingTO.length;
		document.getElementById("breadth").value =  data.bookingTO.breath;
		document.getElementById("height").value =  data.bookingTO.height;
		document.getElementById("insuredamount").value =  data.bookingTO.insAmount;
		document.getElementById("cnNum").innerHTML =  data.bookingTO.consgNumber;
		document.getElementById("nopeices").innerHTML =  data.bookingTO.noOfPieces;
		document.getElementById("bookname").value =  data.bookedBy;
		*/
		
		if(data.childCNTO!=null){
			for(var i=0 ; i<data.childCNTO.length; i++){
				var j=i+1;
				var childCN = data.childCNTO[i];
				addChildCnRows(childCN);
		}
	}
		
		if(data.processMapTO!=null){
			for(var i=0 ; i<data.processMapTO.length; i++){
				var processMap=data.processMapTO[i];
				var j=i+1;
				addDetailRows1(j, processMap);
			}
		}
		document.getElementById("trackBtn").style.display='none';
}
	else{
		alert('No search Results found.');
	}
}

function addDetailRows1(rowCount, processMap){
	  $('#detailsTable').dataTable().fnAddData([
rowCount,
processMap.manifestType,
!isNull(processMap.date)?processMap.date:"",
processMap.consignmentPath, ]);
}

function isValidConsgNum(consgNoObj) {
	//Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	//last 7 char are numeric only.
	var type = document.getElementById("typeName").value;
	
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;	
	
	
	if(type=='CN'){
		consgNoObj.value = $.trim(consgNoObj.value);
		
	if (isNull(consgNoObj.value)) {
		return false;
	}

	if (consgNoObj.value.length!=12) {
		//clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12 characters only!");
		alert("Consignment No. should contain 12 characters only!");
		document.getElementById("consgNumber").value="";
		return false;
	}
			
	if (!consgNoObj.value.substring(0, 1).match(letters)
			|| !consgNoObj.value.substring(4, 5).match(letters)
			|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		//clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		alert("Consignment No. Format is not correct!");
		document.getElementById("consgNumber").value="";
		return false;
	}
 }
	return true;
}


