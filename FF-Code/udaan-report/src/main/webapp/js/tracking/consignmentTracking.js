var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
//var rowCount = 1;
/**
 * adds new rows to the grid
 */
/*
 * function addDetailRows(rowCount){ $('#detailsTable').dataTable().fnAddData([
 * rowCount, '<label class="gradeA" id="type'+ rowCount +'" name="to.type" />', '<label
 * class="gradeA" id="date'+ rowCount + '" name="date" />', '<label
 * class="gradeA" id="path'+ rowCount +'" name="path" />', ]); //rowCount++; }
 */

function addDetailRows1(rowCount, processMap) {
	$('#detailsTable').dataTable().fnAddData(
			[
					rowCount,
					processMap.manifestType,
					!isNull(processMap.dateAndTime) ? processMap.dateAndTime
							: "", processMap.consignmentPath, ]);
	// rowCount++;
}

/*
 * function addChildCnRows(){ var data =
 * document.getElementById("childRows").value; for ( var cnt = 1; cnt <=
 * data.length; cnt++) { $('#childCN').dataTable().fnAddData(['<input
 * type="text" id="numberCn'+cnt +'" />',]); } }
 */

var rowIdd = 1;

/**
 * adds new rows to the grid
 */
function addChildCnRows(childCN) {
	$('#childCN').dataTable().fnAddData([ childCN.childConsgNumber ]);
}

function showOffice(officeId) {

	url = "./consignmentTrackingHeader.do?submitName=showOffice&officeId="
			+ officeId;
	// window.open(url);
	window
			.open(
					url,
					'_blank',
					'top=120, left=590, height=195, width=375, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no, location=no, directories=no');
	// window.location = url;
	// ajaxCallWithoutForm(url,showOfficeResp);

}

/*
 * function showOfficeResp(data){ if(data!=null){ alert(data.address1 ,
 * data.address2 , data.address3 , data.phone,data.email); }
 *  }
 */

function trackconsignment() {

	var consgNo = document.getElementById("consgNumber").value;
	var obj = document.getElementById("consgNumber");
	var type = document.getElementById("typeName").value;
	if (consgNo == '') {
		alert('Please enter Consg/Ref.no');
		document.getElementById("consgNo").innerHTML = '';
	}
	if (type == '') {
		alert('Please select type');
		return;
	}
	if (type == 'RN') {
		if (isValidConsgNo(obj, type)) {
			alert('Consignment Number not allowed');
			document.getElementById("consgNumber").value = "";
			return;
		}
	}
	if (type == 'CN') {
		isValidConsgNum(obj);
	}

	url = "./consignmentTrackingHeader.do?submitName=viewTrackInformation&type="
			+ type + "&number=" + consgNo;
	ajaxCallWithoutForm(url, populateConsignment);

}

function populateConsignment(data) {
	if (data != null) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}

		document.getElementById("firstName").value = data.bookingTO.consignor.firstName;
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
		document.getElementById("phones").value = data.bookingTO.consignee.phone;
		document.getElementById("State").value = data.bookingTO.consignor.destState;
		document.getElementById("mobiles").value = data.bookingTO.consignee.mobile;

		document.getElementById("pickdate").value = data.pckDate;

		document.getElementById("bookingoffice").value = data.bookingTO.officeName;
		document.getElementById("destoffice").value = data.bookingTO.cityName;
		document.getElementById("consgtype").value = data.bookingTO.consgTypeName;
		document.getElementById("bookdate").value = data.bookingTO.bookingDate;
		document.getElementById("addr").value = data.bookingTO.consignor.address;
		document.getElementById("finalwt").value = data.bookingTO.finalWeight;
		document.getElementById("mobile").value = data.bookingTO.consignor.phone;
		document.getElementById("paperworkno").value = data.bookingTO.paperWorkRefNo;
		document.getElementById("paperworktype").value = data.bookingTO.paperWork;
		document.getElementById("actualwt").value = data.bookingTO.actualWeight;
		document.getElementById("volweight").value = data.bookingTO.volWeight;
		document.getElementById("insured").value = data.bookingTO.insuredBy;
		document.getElementById("length").value = data.bookingTO.length;
		document.getElementById("breadth").value = data.bookingTO.breath;
		document.getElementById("height").value = data.bookingTO.height;
		document.getElementById("insuredamount").value = data.bookingTO.insAmount;
		document.getElementById("cnNum").innerHTML = data.bookingTO.consgNumber;
		document.getElementById("nopeices").innerHTML = data.bookingTO.noOfPieces;
		document.getElementById("bookname").value = data.bookedBy;

		if (data.childCNTO != null) {
			for ( var i = 0; i < data.childCNTO.length; i++) {
				var j = i + 1;
				var childCN = data.childCNTO[i];
				addChildCnRows(childCN);
			}
		}

		if (data.processMapTO != null) {
			for ( var i = 0; i < data.processMapTO.length; i++) {
				var processMap = data.processMapTO[i];
				var j = i + 1;
				addDetailRows1(j, processMap);
			}
		}
		document.getElementById("trackBtn").style.display = 'none';
	} else {
		alert('No search Results found.');
	}
}

function disableDiv() {

	document.getElementById("wraper").disabled = true;

}

function showDiv() {

	document.getElementById("wraper").disabled = false;

}

function isValidConsgNum(consgNoObj) {
	// Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	// last 7 char are numeric only.
	var type = document.getElementById("typeName").value;

	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	if (type == 'CN') {
		consgNoObj.value = $.trim(consgNoObj.value);

		if (isNull(consgNoObj.value)) {
			return false;
		}

		if (consgNoObj.value.length != 12) {
			// clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12
			// characters only!");
			alert("Consignment No. should contain 12 characters only!");
			document.getElementById("consgNumber").value = "";
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
			document.getElementById("consgNumber").value = "";
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

function submitForm(url, action) {
	if (confirm("Do you want to " + action + " details?")) {
		document.getElementById("consgNumber").value = "";
		document.consignmentTrackingForm.action = url;
		document.consignmentTrackingForm.submit();
	}
}