var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

function trackmanifest(){
	
	/*clearField();*/
	
	var manifest = document.getElementById("manifestNumber").value;
	var manifestObj = document.getElementById("manifestNumber");
	
	var type = document.getElementById("typeName").value;
	
	if(type==''){
		alert('Please select type');
		return;
	}
	
	if(manifest==''){
		 alert('Please enter ManifestNo');
		 document.getElementById("maniNum").innerHTML='';
		 return;
		}
	
	if(type == 'OGM'){
		isValidPacketNo(manifestObj);
	}else if(type == 'BPL'){
		isValidBplNo(manifestObj);
	}else if(type == 'MBPL'){
		isValidMBplNo(manifestObj);
	}
	
		url = "./manifestTrackingHeader.do?submitName=viewManifestTrackInformation&type="+type+"&number="+manifest;
		ajaxCallWithoutForm(url,populateManifest);
}


/**
 * clears and sets focus
 * 
 * @param obj
 *            which has to be cleared
 * @param msg
 *            which needs to be shown as popup
 */
function clearFocusAlertMsg(obj, msg) {
	obj.value = "";
	setFocus(obj);
	alert(msg);
}

/**
 * sets focus
 * 
 * @Desc sets the focus on the given field
 * @param obj
 */
function setFocus(obj) {
	setTimeout(function() {
		obj.focus();
	}, 10);
}


/**
 * validates BPL number
 * @param manifestNoObj contains the BPL Number
 * @returns {true}
 */
function isValidBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	var  test1 = manifestNoObj.value.substring(0, 4);
	var  test2 = manifestNoObj.value.substring(4);
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}



/**
 * @param manifestNoObj conatins manifest Number
 * @returns {true} if format is valid
 * else returns false
 */
function isValidMBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	var  test1 = manifestNoObj.value.substring(0, 4);
	var  test2 = manifestNoObj.value.substring(4);
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "M") {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. Format is not correct!");
		return false;
	}

	return true;
}



/**
 * validates the packet number
 * @param manifestNoObj
 * @returns {Boolean}
 */
function isValidPacketNo(manifestNoObj) {
	// City Code+7 digits :: BOY1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);

	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length != 10) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}

	if (!manifestNoObj.value.substring(0, 3).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(3))) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. Format is not correct!");
		return false;
	}
	return true;
}

function populateManifest(data){
	var type = document.getElementById("typeName").value;
	if(data!=null){
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		document.getElementById("originoffice").value = data.manifestTO.originOfficeTO.officeName;
		document.getElementById("destination").value = data.manifestTO.destinationOfficeTO.officeName;
		document.getElementById("type").value = data.manifestTO.manifestType;
		document.getElementById("actualwt").value = data.manifestTO.manifestWeight;
		document.getElementById("manifestdate").value = data.manifestTO.manifestDate;
		document.getElementById("maniNum").innerHTML =  data.manifestTO.manifestNumber;
		
	
		if(data.manifestTOs!=null){
			for(var i=0 ; i<data.manifestTOs.length; i++){
				var childManifest = data.manifestTOs[i];
				addChildManifestRows(childManifest);
				
			 }
	 }
	
		
		
			if(data.consignmentTO!=null){
				for(var i=0 ; i<data.consignmentTO.length; i++){
					var childConsgManifest = data.consignmentTO[i];
					addChildConsgManifestRows(childConsgManifest);
					
				 }
		 }
	
		
		if(data.processMapTO!=null){
			for(var i=0 ; i<data.processMapTO.length; i++){
				var processMap=data.processMapTO[i];
				var j=i+1;
				addDetailRows1(j, processMap);
				
			}
		}
		
		if(data.processMapTO.length==0){
			alert('No Load movement details available');
		}
		
		document.getElementById("trackBtn").style.display='none';
	}
	
}

function addChildManifestRows(childManifest){
	  $('#example').dataTable().fnAddData([childManifest.manifestNumber,childManifest.manifestWeight,childManifest.destinationOfficeTO.officeName]);
	 
}


function addChildConsgManifestRows(childConsgManifest){
	  $('#example').dataTable().fnAddData([childConsgManifest.consgNo,childConsgManifest.actualWeight,childConsgManifest.destCity.cityName]);
	 
}


function addDetailRows1(rowCount, processMap){
	  $('#example1').dataTable().fnAddData([
rowCount,
processMap.manifestType,
processMap.dateAndTime,
processMap.consignmentPath ]);
//rowCount++;
}

/*function isValidManifest(manifestNoObj)
{
	var type = document.getElementById("typeName").value;
	if(type=='MBPL')
		{
		if (isNull(manifestNoObj.value)) {
			return false;
		}

		if (manifestNoObj.value.length!=10) {
			clearFocusAlertMsg(manifestNoObj, "Manifest No. should contain 10 characters only!");
			return false;
		}
		var numpattern = /^[0-9]{3,20}$/;
		var letters = /^[A-Za-z]+$/;
		var fourthCharM = /^[M]$/;
		if (manifestNoObj.value.substring(0, 4).match(letters)) {
			if (manifestNoObj.value.substring(3, 4).match(fourthCharM)) {
				if (numpattern.test(manifestNoObj.value.substring(4))) {
					return true;
				} else {
					alert('Manifest Format is not correct');
					manifestNoObj.value = "";
					setTimeout(function() {
						manifestNoObj.focus();
					}, 10);
					return false;
				}
			} else {
				alert('Manifest Format is not correct');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				return false;
			}
		} else {
			alert('Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
		
		}
	
	if(type=='BPL'){
		if (isNull(manifestNoObj.value)) {
			return false;
		}

		if (manifestNoObj.value.length!=10) {
			clearFocusAlertMsg(manifestNoObj, "Manifest No. should contain 10 characters only!");
			return false;
		}
		
		var numpattern = /^[0-9]{3,20}$/;
		var letters = /^[A-Za-z]+$/;
		var fourthCharB = /^[B]$/;
		if (manifestNoObj.value.substring(0, 4).match(letters)) {
			if (manifestNoObj.value.substring(3, 4).match(fourthCharB)) {
				if (numpattern.test(manifestNoObj.value.substring(4))) {
					return true;
				} else {
					alert('Manifest Format is not correct');
					manifestNoObj.value = "";
					setTimeout(function() {
						manifestNoObj.focus();
					}, 10);
					return false;
				}
			} else {
				alert('Manifest Format is not correct');
				manifestNoObj.value = "";
				setTimeout(function() {
					manifestNoObj.focus();
				}, 10);
				return false;
			}
		} else {
			alert('Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
	}
	
	
	if(type=='OGM')
		{
		if (isNull(manifestNoObj.value)) {
			return false;
		}

		if (manifestNoObj.value.length!=10) {
			clearFocusAlertMsg(manifestNoObj, "Manifest No. should contain 10 characters only!");
			return false;
		}
		var numpattern = /^[0-9]{3,20}$/;
		var letters = /^[A-Za-z]+$/;
		var fourthCharM = /^[M]$/;
		if (manifestNoObj.value.substring(0, 3).match(letters)) {
			
				if (numpattern.test(manifestNoObj.value.substring(3))) {
					return true;
				} else {
					alert('Manifest Format is not correct');
					manifestNoObj.value = "";
					setTimeout(function() {
						manifestNoObj.focus();
					}, 10);
					return false;
				}
			 
		} else {
			alert('Format is not correct');
			manifestNoObj.value = "";
			setTimeout(function() {
				manifestNoObj.focus();
			}, 10);
			return false;
		}
		
		}
	
}
*/

function clearScreen(action){
	var url = "./manifestTrackingHeader.do?submitName=viewManifestTracking";
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		document.getElementById("manifestNumber").value="";
		document.manifestTrackingForm.action = url;
		document.manifestTrackingForm.submit();
	}
}


function showOffice(officeId){
	
	url = "./manifestTrackingHeader.do?submitName=showOffice&officeId="+officeId;
	//window.open(url);
	window.open(url,'_blank','top=120, left=590, height=195, width=375, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no, location=no, directories=no');
	//window.location = url;
	//ajaxCallWithoutForm(url,showOfficeResp);
	
}