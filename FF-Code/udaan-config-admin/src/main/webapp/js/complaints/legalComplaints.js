var ERROR_FLAG = "ERROR";
function saveOrUpdateLegalComplaint() {

	if (validateLegalCompalaintDetails()) {
		var complaintId = getDomElementById("serviceRequestComplaintId").value;
		var complaintNumber = getDomElementById("complaintNo").value;
		var consignmentNumber = getDomElementById("consignmentNo").value;
		var complaintStatus = getDomElementById("complaintStatus").value;
		
		/*
		 * var url = './legalComplaint.do?submitName=save'; ajaxJquery(url,
		 * "legalComplaintForm", callBackSaveOrUpdateLegalComplaint);
		 */
		var url = './legalComplaint.do?submitName=saveOrUpdateLegalComplaint&complaintId='+complaintId+"&complaintNumber="+complaintNumber+"&consignmentNumber="+consignmentNumber+"&complaintStatus="+complaintStatus;
	//	ajaxCall(url, "legalComplaintForm", callBackSaveOrUpdateLegalComplaint);
		
		document.legalComplaintForm.action=url;
		document.legalComplaintForm.submit();
	}
}


function validateLegalCompalaintDetails() {

	var advocateNoticfromClient = document
			.getElementById("advocateNoticFromClient").value;
	var hearing1 = document.getElementById("hearing1").value;
	var investfeedbak = document.getElementById("investigFeedback").value;
	var hearing2 = document.getElementById("hearing2").value;
	var hearing3 = document.getElementById("hearing3").value;
	var remarks = document.getElementById("remarks").value;
	var hearing4 = document.getElementById("hearing4").value;
	var lawyrFees = document.getElementById("lawyerFees").value;
	var hearing5 = document.getElementById("hearing5").value;
	var hearing6 = document.getElementById("hearing6").value;
	var date = document.getElementById("date").value;
	var advNoticdate = document.getElementById("date1").value;
	var forwdToFfclLawyr = document.getElementById("hiddenForwardedFfclLayer").value;

	if (isNull(advocateNoticfromClient)) {
		alert("Please Select Advocate Notice");
		return false;
	/*} else if (isNull(hearing1)) {
		alert("Please Enter Hearing 1");
		return false;*/
	}else if (isNull(advNoticdate)) {
		
			alert("Please Enter date for Advocate Notice");
			return false;
		
	}
	else if (isNull(investfeedbak)) {
		alert("Please Enter investment feedbak");
		return false;
	/*} else if (isNull(hearing2)) {
		alert("Please Enter Hearing2");
		return false;*/
	}else if(isNull(forwdToFfclLawyr)){ 
		alert('Please select Forwarded to ffcl lawyer');
		return false;
	}else if (forwdToFfclLawyr=='Y') {
		if(isNull(date)){
			alert("Please Enter date");
			return false;
		}
	/*} else if (isNull(hearing3)) {
		alert("Please Enter Hearing3");
		return false;*/
	} else if (isNull(remarks)) {
		alert("Please Enter remarks");
		return false;
	/*} else if (isNull(hearing4)) {
		alert("Please Enter Hearing4");
		return false;*/
	} else if (isNull(lawyrFees)) {
		alert("Please Enter lawyer fees");
		return false;
	/*} else if (isNull(hearing5)) {
		alert("Please Enter Hearing5");
		return false;*/
	}

	return true;
}


function enableDisableDate(value){
	 if(value=='Y'){
		 document.getElementById("hiddenForwardedFfclLayer").value='Y';
		//enable date
				jQuery("#calendarImg").show();
				
				//jQuery("#date").prop('readonly', false);
	}else if(value=='N'){
		 document.getElementById("hiddenForwardedFfclLayer").value='N';
		jQuery("#calendarImg").hide();
		closeCalendarPopUp();
		jQuery("#date").prop('readonly', true);
		document.getElementById("date").value="";
	}
	 
	
}


/*function validateLawyerfees(obj){
	
	var val = document.getElementById("lawyerFees").value;
	  var num = new Number(val);
	if(/^[0-9]{0,9}(\.[0-9]{0,3})?$/.test(val) && num > 0){
        return true;
    	} else {
	       alert('Please enter the numeric value in decimal format ');
	       obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
	       return false;
    	}
}*/

function validateLawyerfees(obj) {
	var val = document.getElementById("lawyerFees").value;
	
	
	if(isNumeric(val)){
		var dblVar = parseFloat(val);

	    if(isNaN(dblVar)){
	        alert('Please enter the decimal value in lawyer fees');
	    obj.value = "";
	    return false;
	    }
	}else{
		return false;
	}
	
	if(getDomElementById("lawyerFees").value=="0" ||getDomElementById("lawyerFees").value=="0.0" ){
		 alert('Invalid value in lawyer fees');
		getDomElementById("lawyerFees").value="";
		return false;
	}
}


function isNumeric(val){
	 var validChars = '0123456789.';
	 for(var i = 0; i < val.length; i++) {
	        if(validChars.indexOf(val.charAt(i)) == -1){
	        	alert('Please enter the numeric value in lawyer Fees');
	        	document.getElementById("lawyerFees").value = "";
	            return false;
	        }
	    }
	    return true;
}

function viewLegalComplaint(complaintId,complaintNo,consignNo,complaintStatus){
	getDomElementById("serviceRequestComplaintId").value = complaintId;
	getDomElementById("complaintNo").value = complaintNo;
	getDomElementById("consignmentNo").value = consignNo;
	getDomElementById("complaintStatus").value = complaintStatus;
	
	var url = "./legalComplaint.do?submitName=viewLegalComplaint&complaintId="+complaintId+"&complaintNo="+complaintNo+"&consignNo="+consignNo+"&complaintStatus="+complaintStatus;
	// submitFormWithoutPrompt(url);
	ajaxCall(url, "legalComplaintForm", callBackViewLegalComplaint);
}

function callBackViewLegalComplaint(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = eval('(' + ajaxResp + ')');
			setLegalCompltDtls(data);
		}
	}
}

function setLegalCompltDtls(legalcompltsDetail){
	
if(!isNull(legalcompltsDetail)){
	getDomElementById("date1").value = legalcompltsDetail.advocateNoticeFileDate;
	getDomElementById("hearing1").value = legalcompltsDetail.hearing1;
	getDomElementById("date2").value = legalcompltsDetail.hearing1_date;
	getDomElementById("investigFeedback").value = legalcompltsDetail.investigFeedback;
	getDomElementById("hearing2").value = legalcompltsDetail.hearing2;
	getDomElementById("date3").value = legalcompltsDetail.hearing2_date;
	getDomElementById("hearing3").value = legalcompltsDetail.hearing3;
	getDomElementById("date4").value = legalcompltsDetail.hearing3_date;
	getDomElementById("remarks").value = legalcompltsDetail.remarks;
	getDomElementById("hearing4").value = legalcompltsDetail.hearing4;
	getDomElementById("date4").value = legalcompltsDetail.hearing4_date;
	getDomElementById("lawyerFees").value = legalcompltsDetail.lawyerFees;
	getDomElementById("hearing5").value = legalcompltsDetail.hearing5;
	getDomElementById("date6").value = legalcompltsDetail.hearing5_date;
	getDomElementById("hearing6").value = legalcompltsDetail.hearing6;
	getDomElementById("date7").value = legalcompltsDetail.hearing6_date;
	getDomElementById("systemDateAndTime").value = legalcompltsDetail.systemDateAndTime;
	getDomElementById("legalComplaintStatus").value = legalcompltsDetail.legalComplaintStatus;
	
	
	if(legalcompltsDetail.forwardedToFFclLawyer=='Y'){
		getDomElementById("hiddenForwardedFfclLayer").value = legalcompltsDetail.forwardedToFFclLawyer;
		getDomElementById("date").value = legalcompltsDetail.date;
		getDomElementById("forwardedToFFclLawyerYes").checked=true;
	}else if(legalcompltsDetail.forwardedToFFclLawyer=='N'){
		getDomElementById("hiddenForwardedFfclLayer").value = legalcompltsDetail.forwardedToFFclLawyer;
		getDomElementById("forwardedToFFclLawyerNo").checked=true;
	}
	
}

if(getDomElementById("lawyerFees").value=="0" ||getDomElementById("lawyerFees").value=="0.0" ){
	getDomElementById("lawyerFees").value="";
}



/*if(legalcompltsDetail.remarks!=""){
	disableSaveBtn();
}*/

}


function disableSaveBtn(){
	jQuery("#save").attr("disabled", "disabled");
	jQuery("#save").removeClass("btnintform");
	jQuery("#save").addClass("btnintformbigdis");
}

function clearAllFields(){
	
document.getElementById("advocateNoticFromClient").value="";
document.getElementById("hearing1").value="";
document.getElementById("investigFeedback").value="";
document.getElementById("hearing2").value="";
document.getElementById("hearing3").value="";
document.getElementById("remarks").value="";
document.getElementById("hearing4").value="";
document.getElementById("lawyerFees").value="";
document.getElementById("hearing5").value="";
document.getElementById("hearing6").value="";
document.getElementById("date").value="";
document.getElementById("hiddenForwardedFfclLayer").value="";
getDomElementById("forwardedToFFclLawyerYes").checked=false;
getDomElementById("forwardedToFFclLawyerNo").checked=false;

}

function compareDatefromSysDate(){
	
	var mydate = document.getElementById("date").value;
	var systemDateAndTime = document.getElementById("systemDateAndTime").value;
	
	var value = compareDates(mydate, systemDateAndTime);
	
	if(value==1){
		alert('Date cannot be greater than the system date');
		document.getElementById("date").value="";
		document.getElementById("date").focus();
		return false;
	}
	
	
}
