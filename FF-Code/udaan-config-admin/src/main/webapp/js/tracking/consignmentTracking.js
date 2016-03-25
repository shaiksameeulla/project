var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

function addDetailRows(index, processMap,tableId) {
	if (userType == 'C') {
		//Customer Tracking
		$('#'+tableId).dataTable().fnAddData([
		      processMap.manifestType,  processMap.consignmentPath, !isNull(processMap.dateAndTime) ? processMap.dateAndTime : "" ,
		]);
	}else{
		$('#'+tableId).dataTable().fnAddData(
				[
				 index,
				 '<div align="left"> '+ processMap.manifestType +' </div>',
				 !isNull(processMap.dateAndTime) ? processMap.dateAndTime : "" ,
				 '<div align="left">' + processMap.consignmentPath + '</div>', ]);
	}
}
/**
 * adds new rows to the grid
 */
function addChildCnRows(childCN) {
	$('#childCN').dataTable().fnAddData([ childCN.childConsgNumber ]);
}

function showOffice(officeId) {
	url = "./consignmentTrackingHeader.do?submitName=showOffice&officeId="
			+ officeId;
	window
			.open(
					url,
					'_blank',
					'top=120, left=590, height=195, width=375, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no, location=no, directories=no');
	//$(window).unload(hideProcessing);
}

function setDefaultValues(consignmentNo, type){
	var headerDiv = getDomElementById('trackingHead');
	var clearBtn = getDomElementById('clearBtn');
	if(screen == "trackingPopup"){
		getDomElementById("consgNumber").value = consignmentNo;
		getDomElementById("typeName").value = type;
		headerDiv.style.display = 'none';
		clearBtn.style.display = 'none';
		trackconsignment();		
	}else{
		headerDiv.style.display = "";
		clearBtn.style.display = "";
		$('#consgNumber').focus();
	}
}

function trackconsignment() {
	var cnObj = getDomElementById("consgNumber");
	var consgNo = trimString(cnObj.value);
	var type = getDomElementById("typeName").value;
	if (isNull(consgNo)) {
		alert('Please enter Consg/Ref.no');
		cnObj.innerHTML = '';
		$('#consgNumber').focus();
		return;
	}
	if (isNull(type)) {
		alert('Please select type');
		$('#typeName').focus();
		return;
	}
	if (type == 'RN') {
		if (isValidConsgNo(cnObj, type)) {
			alert('Consignment Number not allowed');
			cnObj.value = "";
			$('#consgNumber').focus();
			return;
		}
	}
	if (type == 'CN') {
		isValidConsgNum(cnObj);
	}

	url = "./consignmentTrackingHeader.do?submitName=viewTrackInformation&type="
			+ type + "&number=" + consgNo;
	ajaxCalWithoutForm(url, populateConsignment);
}

function crmTracking(consgNo, type){
	if(!isNull(consgNo)){
		url = "./consignmentTrackingHeader.do?submitName=viewTrackInformation&type="
		+ type + "&number=" + consgNo;
		ajaxCalWithoutForm(url, populateConsignment);
	}
}

function populateConsignment(data) {
	if (!isNull(data)) {
		if (userType == 'C') {
			//Customer Tracking
			tableId = 'detailsTable4Customer';
//			document.getElementById("detailsTable4Customer").style.display = "table";
//			document.getElementById("detailsTable").style.display = "none";
		} else {
			tableId = 'detailsTable';
		}
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		var consgNoObj = getDomElementById("consgNumber");

		if(responseText!=null && error!=null){			
			getDomElementById("typeName").value = "CN";
			clearFocusAlertMsg(consgNoObj,error);
			return ;
		}
		if(!isNull(data.incompleteData)){
			getDomElementById("errorMsg").innerHTML = data.incompleteData;
		}
		consgNoObj.disabled = true;;
		getDomElementById("typeName").disabled = true;
		
		getDomElementById("pickdate").value = data.pckDate;
		getDomElementById("bookname").value = data.bookedBy;
		
		if(!isNull(data.consignmentTO)){
			getDomElementById("firstName").value = data.consignmentTO.consignorTO.firstName;
			getDomElementById("cityName").value = data.consignmentTO.consignorTO.orgCity;
			getDomElementById("address").value = data.consignmentTO.consignorTO.address;
			getDomElementById("pincode").value = data.consignmentTO.consignorTO.orgPincode;
			getDomElementById("phone").value = data.consignmentTO.consignorTO.phone;
			getDomElementById("state").value = data.consignmentTO.consignorTO.orgState;
			getDomElementById("mobile").value = data.consignmentTO.consignorTO.mobile;
			getDomElementById("addr").value = data.consignmentTO.consignorTO.address;
			getDomElementById("bkgInfoMobile").value = data.consignmentTO.consignorTO.mobile;

			getDomElementById("firstname").value = data.consignmentTO.consigneeTO.firstName;
			getDomElementById("city").value = data.consignmentTO.consigneeTO.destCity;
			getDomElementById("adress").value = data.consignmentTO.consigneeTO.address;
			getDomElementById("pincodes").value = data.consignmentTO.consigneeTO.destPincode;
			getDomElementById("phones").value = data.consignmentTO.consigneeTO.phone;
			getDomElementById("State").value = data.consignmentTO.consigneeTO.destState;
			getDomElementById("mobiles").value = data.consignmentTO.consigneeTO.mobile;
			if(!isNull(data.consignmentTO.customerTO)){
				getDomElementById("custname").value = data.consignmentTO.customerTO.customerCode;
			}
			getDomElementById("mailId").value = data.consignmentTO.consigneeTO.email;
			getDomElementById("mobileNo").value = data.consignmentTO.consigneeTO.mobile;
			if(!isNull(data.consignmentTO.cnContents)){
				getDomElementById("cnContent").value = data.consignmentTO.cnContents.cnContentDesc;
			}
			getDomElementById("declareValue").value = data.consignmentTO.declaredValue;
		}
		if(!isNull(data.bookingTO)){
			getDomElementById("bookingoffice").value = data.bookingTO.officeName;
			getDomElementById("destoffice").value = data.bookingTO.cityName;
			getDomElementById("consgtype").value = data.bookingTO.consgTypeName;
			getDomElementById("bookdate").value = data.bookingTO.bookingDate;
			getDomElementById("finalwt").value = formatWeight(data.bookingTO.finalWeight);
			getDomElementById("paperworkno").value = data.bookingTO.paperWorkRefNo;
			getDomElementById("paperworktype").value = data.bookingTO.paperWork;
			getDomElementById("actualwt").value = formatWeight(data.bookingTO.actualWeight);
			getDomElementById("volweight").value = formatWeight(data.bookingTO.volWeight);
			getDomElementById("insured").value = data.bookingTO.insuredBy;
			getDomElementById("length").value = data.bookingTO.length;
			getDomElementById("breadth").value = data.bookingTO.breath;
			getDomElementById("height").value = data.bookingTO.height;			
			getDomElementById("custname").value = data.bookingTO.customerCodeSingle;
			getDomElementById("cnNum").innerHTML = data.bookingTO.consgNumber;
			if(!isNull(data.bookingTO.cnStatus)){
				getDomElementById("status").style.backgroundColor = "#ff8314"; 
				getDomElementById("cnStatus").innerHTML = data.bookingTO.cnStatus;
			}
			if((isNull(data.consignmentTO) || isNull(data.consignmentTO.declaredValue)) && !isNull(data.bookingTO.declaredValue)){
				getDomElementById("declareValue").value = data.bookingTO.declaredValue;
			}
		}
		if (userType != 'C') {
			//Non - Customer Tracking
			if (data.childCNTO != null) {
				var noOfChildCns = data.childCNTO.length;
				getDomElementById("nopeices").innerHTML = noOfChildCns;
				for ( var i = 0; i < noOfChildCns; i++) {
					var childCN = data.childCNTO[i];
					addChildCnRows(childCN);
				}
			}else{
				getDomElementById("nopeices").innerHTML = '0';
			}
		}

		if (data.processMapTO != null) {
			for ( var i = 0; i < data.processMapTO.length; i++) {
				var processMap = data.processMapTO[i];
				var j = i + 1;
				addDetailRows(j, processMap,tableId);
			}
		}
		getDomElementById("trackBtn").style.display = 'none';
	} else {
		alert('No search Results found.');
	}	
}
function formatWeight(num){
	if(!isNull(num)){
		return parseFloat(num).toFixed(3);
	}else{
		return "0.000";
	}
}

function isValidConsgNum(consgNoObj) {
	// Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	// last 7 char are numeric only.
	var type = getDomElementById("typeName").value;

	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	if (type == 'CN') {
		consgNoObj.value = $.trim(consgNoObj.value);

		if (isNull(consgNoObj.value)) {
			return false;
		}

		if (consgNoObj.value.length != 12) {
			alert("Consignment No. should contain 12 characters only!");
			getDomElementById("consgNumber").value = "";
			return false;
		}

		if (!consgNoObj.value.substring(0, 1).match(letters)
				|| (!consgNoObj.value.substring(4, 5).match(letters) && !consgNoObj.value
						.substring(1, 4).match(alphaNumeric))
				|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
				|| !numpattern.test(consgNoObj.value.substring(5))) {
			alert("Consignment No. Format is not correct!");
			getDomElementById("consgNumber").value = "";
			$('#consgNumber').focus();
			return false;
		}
	}
	return true;
}

function isValidConsgNo(consgNoObj, type) {

	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	if (type == 'RN') {
		consgNoObj.value = $.trim(consgNoObj.value);

		if (isNull(consgNoObj.value)) {
			return false;
		}

		if (consgNoObj.value.length != 12) {
			return false;
		}

		if (!consgNoObj.value.substring(0, 1).match(letters)
				|| !consgNoObj.value.substring(4, 5).match(letters)
				|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
				|| !numpattern.test(consgNoObj.value.substring(5))) {

			return false;
		}
	}
	return true;
}

function clearScreen(action) {
	var url = "./consignmentTrackingHeader.do?submitName=viewConsignmentTracking";
	submitForm(url, action);
}
function closeWindow() {
	window.close();
}

function submitForm(url, action) {
	if (confirm("Do you want to " + action + " details?")) {
		getDomElementById("consgNumber").value = "";
		document.consignmentTrackingForm.action = url;
		document.consignmentTrackingForm.submit();
	}
}
/**
 * clears and sets focus
 * 
 * @param obj
 *            which has to be cleared
 * @param msg
 *            which needs to be shown as popup
 */
/*function clearFocusAlertMsg(obj, msg) {
	obj.value = "";
	setFocusOnElmentByDom(obj);
	alert(msg);
}*/
function ESCclose(evt) {
	if (screen == "trackingPopup" && evt.keyCode == 27)
		window.close();
}

function fnSendMailOrSMS(mode){
	var mobileNo = getValueByElementId("mobileNo");
	var mailId = getValueByElementId("mailId");
	var cnNumber = $('#consgNumber').val();
	var cnStatus = document.getElementById("cnStatus").innerHTML;
//	var start = cnStatus.indexOf("<");
//	var end = cnStatus.indexOf(">");
//	var newStatus =  cnStatus.substring(0,start)+" " + cnStatus.substring(end);
	var url = "./consignmentTrackingHeader.do?submitName=sendMailOrSMS&type="
		+ mode + "&mobileNo=" + mobileNo+ "&mailId=" + mailId +"&cnNumber=" + cnNumber +"&status="+cnStatus;
		
//	var lenMobileNumber=mobileNo.length;
//	var lenMailId=mailId.length;
		
	if((mailId == null || mailId =="") && (mobileNo == null || mobileNo =="")){
		alert("Please enter either mailId or mobile number");
		return false;
	}
	
	ajaxCalWithoutForm(url, fnSendMailOrSMSResponse);
}

function fnSendMailOrSMSResponse(data){
	if (!isNull(data)) {
//		var responseText =data; 
		var messageDate=data.message;
		
		if(messageDate==SUCCESS_FLAG){
			alert("Message Send successfully");
			return false;
		}if(messageDate==ERROR_FLAG){
			alert("Message failed");
			return false;
		}else{
			alert("Message failed");
			return false;
		}
		if(responseText!=null && error!=null){		
			return true;
		}
	}
}