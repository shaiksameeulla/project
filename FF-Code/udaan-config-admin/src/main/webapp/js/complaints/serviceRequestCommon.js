
function getSelectedValue(selectedValue){
	 var resultValue=null;
	 if(!isNull(selectedValue)){
		 var statusValue=(trimString(selectedValue)).split(GLOBAL_CHARACTER_TILD);
		 if(statusValue!=null && statusValue.length==2){
			 resultValue=statusValue[1];
		 }
	 }
	 return resultValue;
}

function getComplaintStatus(){
	 var status=null;
	 var statusDom=getDomElementById('status');
	 if(!isNull(statusDom.value)){
		 status=getSelectedValue(statusDom.value);
	 }
	 return status;
}

function validateWeight(domElmnt){
	if(!isNull(domElmnt.value)){
		domElmnt.value=roundDecimalLength(domElmnt.value, 3);
	}
	
}

function parseNumber(num){
	var inpNum =(isNull(num)?0:num);
	return parseInt(inpNum,10);
 }

function getSalesCoordinator(){
	return getDomElementById('salesCoordinatorRole').value;
}
function getBackLineExecutive(){
	return getDomElementById('backlineExecutiveRole').value;
}
function getConsgTypeDox(){
	return getDomElementById('consignmentTypeDox').value;
}



function getServiceRequestTypeForConsg(){
	return getDomElementById('serviceRequestTypeForConsg').value;
}
function getServiceRequestTypeForService(){
	return getDomElementById('serviceRequestTypeForService').value;
}
function getServiceRequestTypeForBookingRef(){
	return getDomElementById('serviceRequestTypeForBref').value;
}

function getComplaintStatusResolved(){
	return getDomElementById('complaintStatusResolved').value;
}

function getComplaintStatusBackline(){
	return getDomElementById('complaintStatusBackline').value;
}
function getComplaintStatusFollowup(){
	return getDomElementById('complaintStatusFollowup').value;
}

function getServiceRelatedQueryTypeTariffType(){
	return getDomElementById('queryTypeTariffEnquiry').value;
}
function getServiceRelatedQueryTypeServiceChk(){
	return getDomElementById('queryTypeServiceCheck').value;
}
function getServiceRelatedQueryTypeGeneralInf(){
	return getDomElementById('queryTypeGeneralInfo').value;
}
function getServiceRelatedQueryTypeLeadcall(){
	return getDomElementById('queryTypeLeadCall').value;
}
function getServiceRelatedQueryTypePickupcall(){
	return getDomElementById('queryTypePickupCall').value;
}
function getServiceRelatedQueryTypePaperwrk(){
	return getDomElementById('queryTypePaperwork').value;
}
function getServiceRelatedQueryTypeEmotioncalBond(){
	return getDomElementById('queryTypeEmotionalBond').value;
}
function getServiceRelatedQueryTypeComplaint(){
	return getDomElementById('queryTypeComplaint').value;
}
function getServiceRelatedQueryTypePodStatus(){
	return getDomElementById('queryTypePodStatus').value;
}
/*function getServiceRelatedQueryTypeCriticalComplaint(){
	return getDomElementById('queryTypeCriticalComplaint').value;
}
function getServiceRelatedQueryTypeEscComplaint(){
	return getDomElementById('queryTypeEscalationComplaint').value;
}
function getServiceRelatedQueryTypeFinComplaint(){
	return getDomElementById('queryTypeFinancialComplaint').value;
}*/

function getComplaintSourceOfQueryPhone(){
	return getDomElementById('sourceOfQueryPhone').value;
}

function isNewServiceReq(){
	var isNewFlag=true;
	var srqId= getDomElementById('serviceRequestId').value;
	if(!isNull(srqId)){
		isNewFlag=false;
	}
	return isNewFlag;
}

function validateSrvReqEmail(domelemnt){
	if(!validateEmail(domelemnt.value)){
		domelemnt.value="";
		setFocusOnElmentByDom(domelemnt);
	}
	return true;
}
function validateSrvReqPhone(domelemnt){
	var phoneNumber=domelemnt.value;
	if(isNull(phoneNumber)) {
		alert("Contact number can not be empty");
		setFocusOnElmentByDom(domelemnt);
		return false;
	}

	//Expression for numeric values 
	var objRegExp  = /(^\d{9,11}$)/;//size 9 10 11

	if(objRegExp.test(phoneNumber)) {
		return true;
	} else {
		alert("Contact number length should be in between 9 to 11 digits only");
		domelemnt.value="";
		setFocusOnElmentByDom(domelemnt);
		return false; 
	}	


	return true;
}

function globalFormSubmit(url,formId){

	 document.forms[formId].action=url;
	 document.forms[formId].submit();
}

function OnlyAlphabetsAndNos(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 || (charCode >= 48 && charCode <= 57)||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function setServiceRequestStatusAsResolved(){
	var resolvedStatus= getComplaintStatusResolved();
	 $("#status option").each(function()
			 {
			     var currentValue= $(this).val();
			     if(!isNull(currentValue)&& $(currentValue).has(resolvedStatus)){
			    	// alert("0000000");
			    	 $(this).attr("selected", true);	
			    	 return;
			     }
			 });
	 
}

function validateCNFormat(cnNumber){

	// Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	// last 7 char are numeric only.
var cnNumber1=cnNumber;
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	cnNumber1 = $.trim(cnNumber);

		if (isNull(cnNumber1)) {
			return false;
		}

		if (cnNumber1.length != 12) {
			alert("Consignment No. should contain 12 characters only!");
			return false;
		}

		if (!cnNumber1.substring(0, 1).match(letters)
				|| (!cnNumber1.substring(4, 5).match(letters) && !cnNumber1
						.substring(1, 4).match(alphaNumeric))
				|| !cnNumber1.substring(1, 4).match(alphaNumeric)
				|| !numpattern.test(cnNumber1.substring(5))) {
			alert("Consignment No. Format is not correct!");
			return false;
		}
	return true;

}
