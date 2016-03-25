var cnArray = new Array(); // Gobal variable to be used in CN# Validation
var childWindow;

String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
};
String.prototype.ltrim = function() {
    return this.replace(/^\s+/,"");
};
String.prototype.rtrim = function() {
    return this.replace(/\s+$/,"");
};

function isFutureDate(selectedDate){
//	  var month = new Date().getMonth()+1;
//	  var current = new Date().getDate()+"/"+month+"/"+new Date().getFullYear();
//	  selectedDate = selectedDate.split("/");
//	  current = current.split("/");
//	  var selectedDate = new Date(selectedDate[1]+"/"+selectedDate[0]+"/"+selectedDate[2]);
//	  var current = new Date(current[1]+"/"+current[0]+"/"+current[2]);
//	  if (selectedDate>current) { 
//	    	return true; 
//	  } 
//	  return false;
	var date = selectedDate.trim();
	if (date == "")
		return false;
	
	date = date.split("/");
	var myDate=new Date();
	myDate.setFullYear(date[2],date[1] - 1,date[0]);
	var today = new Date();
	if (myDate > today) {
		return true;
	}
	return false;
}
function isBackDate(selectedDate){
	var date = selectedDate.trim();
	if (date == "")
		return false;
	
	date = date.split("/");
	var myDate=new Date();
	myDate.setFullYear(date[2],date[1] - 1,date[0]);
	var today = new Date();
	if (myDate < today) {
		return true;
	}
	return false;
}
function compareDates(date1, date2) {
	if(date1 == undefined || date2 ==undefined) {
		return -100;//garbage value
	}
	date1 = date1.split("/");
	date2 = date2.split("/");
	
	var myDate1 = new Date();
	myDate1.setFullYear(date1[2],date1[1] - 1,date1[0]);
	
	var myDate2 = new Date();
	myDate2.setFullYear(date2[2],date2[1] - 1,date2[0]);
	if(myDate1 == myDate2) {
		return 0;
	} else if(myDate1 < myDate2) {
		return -1;
	} else if(myDate1 > myDate2) {
		return 1;
	}
}
function isValidTime(time){ 
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	time = time.split(":");
	if(time == null || time.length != 2) {
		return false;
	} else if (!anum.test(time[0]) || !anum.test(time[1])){
		return false;
	} else if(time[0] < 0 || time[0] > 23) {
		return false;
	} else if (time[1] < 0 || time[1] > 59) {
		return false;
	} else {
		return true;
	}
}

function isValidTimeHHMM(time){
	var validTime = false;
	if(time.length == 4){
		var hours = time.substring(0, 2);
		var mins = time.substring(2, time.length) ;
		if(hours < 0 || hours > 23){
			alert('Please enter valid hours.');
		}else if(mins < 0 || time[1] > 59){
			alert('Please enter valid minutes.');
		}else{
			validTime = true;
		}
	}else{
		alert('Please enter time in HHMM format');
	}
	return validTime;
}

function isValidPhone(phoneNumber) { 
	if(phoneNumber == null) {
		return false;
	}
	//check for valid us phone with or without space between area code
	//var objRegExp  = /^\([1-9]\d{2}\)\s?\d{3}\-\d{4}$/;

	//Expression for numeric values 
	 var objRegExp  = /(^-?\d\d*$)/;

	 if(objRegExp.test(phoneNumber)) {
		 if(7 <= phoneNumber.length && phoneNumber.length <=10 ) {
			 return true; 
		 } else {
			 return false; 
		 }
	 } else {
		 return false; 
	 }	
}
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)){
	   //alert("Please give number format value");
      return false;     
	}
   return true;
}
//check for valid numeric strings	
function IsNumeric(strString)
{
	var strValidChars = "0123456789";
	var strChar;
	var blnResult = true;
	if (strString.length == 0) return false;
		//  test strString consists of valid characters listed above
	for (i = 0; i < strString.length && blnResult == true; i++)
	   {
	   strChar = strString.charAt(i);
	   if (strValidChars.indexOf(strChar) == -1)
	      {
		   	blnResult = false;
	      }
	   }
	return blnResult;
}

/* Validate the CN Number Length */
function validateCNNumberLength(cnDomObj, errorDivID){
	var flag= true;
	var cnNo = cnDomObj.value;
	var fieldID = cnDomObj.id;
	if(cnNo != "" && cnNo != null){
		if(cnNo.length == 9 || cnNo.length == 12){
			if(cnNo.length == 9){
				if(!isNANCheck(cnNo)){
					flag = true;
				}
				else{
					flag =false;
				}
			}
			if(cnNo.length == 12){
				if(isNANCheck(cnNo)){
					flag = true;
				}else{
					flag =false;
				}
			}
		}else{
			flag =false;
		}
	}else{
		flag =false;;
	}
	if(!flag){
		//document.getElementById(errorDivID).innerHTML="Please enter valid CN Number";
		//document.getElementById(errorDivID).style.visibility = "visible";
		alert('Please enter valid CN Number');
		cnDomObj.focus();
		return false;
	}else{
		//document.getElementById(errorDivID).innerHTML="";
		return true;
	}
	return true;		
}	

function isNANCheck(cnNo){
	for(var i=0;i < cnNo.length;i++){
		var cn = cnNo.charAt(i);
		if(isNaN(cn)){
			return false;
		}
	}
	return true;
}
/* Check CN number duplicacy in the grid table */
function checkDuplicateConsignment(cnDomObj, cnValue) {
	var uniqueConsignment = true;
	var par = cnDomObj.parentNode;
	while (par.nodeName.toLowerCase() != 'tr') {
		par = par.parentNode;
	}
	rowId = (par.rowIndex);
	
	if (cnArray != null && cnArray.length != 0) {
		for ( var i = 0; i < cnArray.length; i++) {
			if (cnValue!= null && cnValue!= "" && cnArray[i] == cnValue && rowId != i ) {
				alert("Consignment Number already entered !");
				document.getElementById(cnDomObj.id).value = "";
				uniqueConsignment = false;
			}
		}
		if(uniqueConsignment){
			cnArray[rowId] = cnValue;
		}
	}else {
		if(cnArray.length ==0){
		cnArray[rowId] = cnValue;
		}
	}
	return uniqueConsignment;
}

/*
 * Delete the checked row and Update the Sl. No. 
 */
function deleteRow(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	for ( var i = 1; i < rowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (null != chkbox && true == chkbox.checked) {
			table.deleteRow(i);
			rowCount--;
			i--;
		}
	}
	updateSrlNo(tableID);
}

function updateSrlNo(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	for ( var i = 1; i < rowCount; i++) {
		var row = table.rows[i];
		var val = row.cells[1];
		val.innerHTML = i;
	}
}

/*
 * Check/Uncheck All the grid row check boxes
 */
/*function checkAll(varDiv,checked){
    var containerCheckboxes = $(varDiv).select('input[type=checkbox]');
    var val = checked ? 1 : 0;
    containerCheckboxes.each(function(e){ e.checked = val; });
}*/
function checkAll(tableID, checked) {
	var table = document.getElementById(tableID);
	var rowNo = table.rows.length;
		if (checked == true) {
			for ( var i = 1; i < rowNo; i++) {
				var row = table.rows[i];
				var chkbox = row.cells[0].childNodes[0];
				chkbox.checked = true;
			}
		} else {
			for ( var i = 1; i < rowNo; i++) {
				var row = table.rows[i];
				var chkbox = row.cells[0].childNodes[0];
				chkbox.checked = false;
			}
		}
	}
/*
 * Refresh the Grid
 */
function refreshGrid(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	for ( var i = 1; i < rowCount; i++) {
		table.deleteRow(i);
		rowCount--;
		i--;
	}
}

/*
 * Check how many rows are selected in Grid
 */
function getCountOfSelectedRows(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	var count = 0;
	for ( var i = 1; i < rowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (null != chkbox && true == chkbox.checked) {
			count++;
		}
	}
	return count;
}

/*
 * Get the selected grid row Id
 */
function getRowIdOfSelectedRow(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	var selectedRowId = 0;
	for ( var i = 1; i < rowCount; i++) {
		var row = table.rows[i];
		var chkbox = row.cells[0].childNodes[0];
		if (null != chkbox && true == chkbox.checked) {
			selectedRowId = i;
		}
	}
	return selectedRowId;
}

/*
 * AutoComplete Functionality
 * 
 * Create Key and Value Array from the Ajax Respose (i.e. Map<k,V>)
 */
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
			
		}
	}
}

function validateautoCompleteResponse(responseData){
	var success = true;
	var ajaxResponse = responseData.responseText;
	var newStr=ajaxResponse.replace("{","");
	var newStr1=newStr.replace("}","");
	if(newStr1.trim() == ""){
			success = false;
	}
	
	return success;
}

/*
 * AutoComplete Functionality
 * 
 * Create Key (Combination of Id and Code) and Value Array 
 * from the Ajax Respose (i.e. Map<k,V>)
 */
function getAutoCompleteIdCodeValueArrays(responseData,idArray,codeArray,valueArray){
	var ajaxResponse = responseData.responseText;
	try{
		if (ajaxResponse != "" && ajaxResponse != null) {
			var newStr=ajaxResponse.replace("{","");
			var newStr1=newStr.replace("}","");
			var keyValList = newStr1.split(",");
			for(i=0; i < keyValList.length; i++){
				var keyValPair = keyValList[i].split("=");
				if(keyValPair[0] != '' && keyValPair[1] != ''){
					var keyCombo = keyValPair[0].split("~");
					if(keyCombo[0].trim() != '' && keyCombo[1].trim() != ''){
						idArray[i] = keyCombo[0].trim();
						codeArray[i] = keyCombo[1].trim();
					}	
					valueArray[i] = keyValPair[1].replace("^",",").trim();
				}	
			}
		}
	}catch(ex){
		alert("Error occured in Auto Complete...");
	}
}

/*
 * Get the Key for Value Selected in the Auto Complete
 */
function getKeyForAutoCompletedValue(keyArray,valueArray,selectedValue){
	for(j=0; j< valueArray.length; j++){
		if(valueArray[j]== selectedValue){
//			alert(selectedValue+"-"+keyArray[j]);
			return(keyArray[j]);
		}
	}
}

function resetCombo(comboBoxID){
	var comboElement = document.getElementById(comboBoxID);
	comboElement.options.length = 0;
	var option = document.createElement("OPTION");
	option.value = 'Select';
	option.text = '-- Select --';
	comboElement.add(option,null);
}

function onlyNumeric(e)
{
	var charCode;
	
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
	            return false;
	 }
	 else{
		 	return true;
	 }
	
}
function onlyDecimal(e)
{
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	if ((charCode > 31 && (charCode < 48 || charCode > 57) )&& charCode!=46){
	        return false;
	 }
	 else{
		 return true;
	 }
	
}


function onlyAlphabet(e)
{
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90) || charCode==46 || charCode==32 ||
			charCode==9 || charCode==8 || charCode==127 ||charCode==37 ||charCode==39 ||charCode==0){
	        return true;
	 }
	 else{
	       	return false;
	 }
	
}

function onlyDate(e)
{
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	// 47: forward slash && 8: backspace && 0: Tab
	
	if ((charCode >= 48 && charCode <= 57) || charCode == 47 || charCode == 8 || charCode==0){
	        return true;
	 }
	 else{
		 return false;
	 }
	
}

function onlyTime(e)
{
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	// 58: colon && 8: backspace && 0: Tab
	if ((charCode >= 48 && charCode <= 58) || charCode == 8 || charCode==0){
	        return true;
	 }
	 else{
		 return false;
	 }
	
}

function setDefaultSelectForCombo(comboObj, val, id){
	for(i=0; i< comboObj.options.length; i++){
		if(id.trim() != ''){
			if(comboObj.options[i].value == id){
				comboObj.options[i].selected = true;
			}
		}else if(val.trim() != ''){
			if(comboObj.options[i].text == val){
				comboObj.options[i].selected = true;
			}
		}	
	}
}

/************************************ DATE VALIDATION : START ********************************************/
/**
 * Validates if the entered date is valid
 * 
 * @param dateObj
 * @param dateSeparator: The separator used (either / or -)
 * @returns {Boolean}
 */
function isValidDate(dateObj, dateSeparator){
	var dateValid = true;
	
	if(dateObj.value == undefined || dateObj.value == null || dateObj.value == 'null' || dateObj.value == '' ){
		dateObj.value="";
		alert("Please provide a Date in dd/mm/yyyy format.");
		dateValid = false;
	} else {
		var inputDate = dateObj.value.trim();
		if(!checkDate(inputDate,dateSeparator)){
			dateObj.value="";
			dateValid = false;
		}
	}
	return dateValid;
}

function checkDate(dtStr,dtCh){
	//var dtCh= "/";
	if(dtStr != 'null' && dtStr != ""){
		var daysInMonth = DaysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strDay=dtStr.substring(0,pos1);
		var strMonth=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
		if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
		for (var i = 1; i <= 3; i++) {
			if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
		}
		month=parseInt(strMonth);
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){
			alert('The date format should be : dd/mm/yyyy');
			return false;
		}
		
		if (strMonth.length<1 || month<1 || month>12){
			alert('Please enter a valid month');
			return false;
		}
		
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			alert('Please enter a valid day');
			return false;
		}
		
		if (strYear.trim().length != 4 ){
			alert('Please enter a valid 4 digit year');
			return false;
		}
		
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 ){
			alert('Please enter a valid date');
			return false;
		}
	}
	return true;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {
			this[i] = 30;
		}
		if (i==2) {
			this[i] = 29;
		}
   } 
   return this;
}
/************************************ DATE VALIDATION : END ********************************************/

// Validate Time in HH:MM:SS AM/PM format
function validateTime(timeObj){ 
	var anum=/(^\d+$)|(^\d+\.\d+$)/;
	var time = timeObj.value.trim().split(":");
	if(time == null || time.length != 3) {
		alert('Please enter time in HH:MM:SS AM/PM format');
		return false;
	} else if (!anum.test(time[0])){
		alert('Please enter a valid Hour');
		return false;
	} else if(!anum.test(time[1])){
		alert('Please enter a valid Minute');
		return false;
	}else if(time[0] < 0 || time[0] > 23) {
		alert('Please enter a valid Hour');
		return false;
	} else if (time[1] < 0 || time[1] > 59) {
		alert('Please enter a valid Minute');
		return false;
	} else {
		var time1 = time[2].split(' ');
		if(time1 == null || time1.length != 2){
			alert('Please enter time in HH:MM:SS AM/PM format');
			return false;
		}else if(!anum.test(time1[0])){
			alert('Please enter a valid second');
			return false;
		}else if(time1[0] < 0 || time1[0] > 59){
			alert('Please enter a valid second');
			return false;
		}else if(time1[1] != 'AM' && time1[1] != 'PM'){
			alert('Please enter either AM/PM.');
			return false;
		}
	}
	return true;
}


function validateConsignmentNumber(obj) {
	// block to check if consignment number is 9 digit alpha numeric or 12 digit numeric
	var par = obj.parentNode;
	while (par.nodeName.toLowerCase() != 'tr') {
		par = par.parentNode;
	}
	rowId = (par.rowIndex);
	var isConsignmentValid;
	var flag= true;
	var cnNo = obj.value;
	cnNo=cnNo.trim();
	var fieldID = obj.id;
	if(cnNo != "" && cnNo != null){
		document.getElementById(obj.id).value = cnNo.toUpperCase();
		if(cnNo.length == 9 || cnNo.length == 12){
			if(cnNo.length == 9){
				if(!isNANCheck(cnNo)){
					flag = true;
				}
				else{
					flag =false;
				}
			}
			if(cnNo.length == 12){
				if(isNANCheck(cnNo)){
					flag = true;
				}else{
					flag =false;
				}
			}
		}else{
			flag =false;
		}
		if(!flag){
			alert("Please enter valid CN Number, CN Number should be 9 digit as first letter alphabet with 8 digit or 12 digit Numeric!");
			//isConsignmentValid=false;
			//obj.focus();
			setTimeout(function(){document.getElementById(obj.id).focus();},10);
			//document.getElementById("gridErrMsg").innerHTML="Please enter valid CN Number, CN Number should be 9 digit as first letter alphabet with 8 digit or 12 digit Numeric!";
			//document.getElementById("gridErrMsg").style.visibility="visible";
			document.getElementById(obj.id).value = "";
			
			//return false;
		}else{
			//document.getElementById("gridErrMsg").innerHTML="";
			//return true;
			isConsignmentValid=true;
		}
	/*else{
		document.getElementById(obj.id).focus();
		document.getElementById("gridErrMsg").innerHTML="";
	}*/
	
	//block to check if cn no is already entered in the grid
	// bug fix for duplicate consignment check by Ravi
	
	/*var uniqueConsignment = true;
	//checkDuplicateInGrid(obj.name);
		//alert(" Consignment Number already entered !");
	
	//if(cnArray[rowId] == "undefined"){
	if (cnArray != null && cnArray.length != 0) {
		for ( var i = 0; i < cnArray.length; i++) {
			if (cnNo!= null && cnNo!= "" && cnNo.lenght!=0 && cnArray[i] == cnNo && rowId != i ) {
				isConsignmentValid=false;
				alert(cnArray[i]);
				//obj.focus();
				setTimeout(function(){document.getElementById(obj.id).focus();},1);
				//document.getElementById(obj.id).focus();
				alert(" Consignment Number already entered !"+i+""+rowId);
				document.getElementById(obj.id).value = "";
				uniqueConsignment = false;
			}
		}
		if(uniqueConsignment){
			cnArray[rowId] = cnNo;
		}
	}else {
		if(cnArray.length ==0){
		cnArray[rowId] = cnNo;
		}
	}*/
	
	//block to check if cn no starts with X,Y and J series
	
	 if(cnNo.charAt(0).toUpperCase() == 'Y' || cnNo.charAt(0).toUpperCase()=='J'){
		 	isConsignmentValid=false;
		 	setTimeout(function(){obj.focus();},10);
		 	alert("Y and J series consignments are not allowed!");
		 	//document.getElementById('gridErrMsg').innerHTML="X,Y and J series consignments are not allowed!";
			//document.getElementById('gridErrMsg').style.visibility="visible";
	 }
	
	}else{
		isConsignmentValid=false;
		if(cnArray.length !=0){
			cnArray[rowId] = cnNo;
			}
		//alert("Please enter Consignment Number!");
		//document.getElementById('gridErrMsg').innerHTML="Please enter Consignment Number!";
		//document.getElementById('gridErrMsg').style.visibility="visible";
	}
	//return true;
	 return isConsignmentValid;
		
}

	function manifestValidation(obj) {
		var validManifest = false;
		var manisfestNo = obj.value.trim();		
		if(lengthCheckForManifestNumber(manisfestNo)){
			// if (alphaNumericValidate(manisfestNo)) {
			validManifest = true;
			// }else{
				//manifestChkFlg = false;
		} else{
			alert("Please enter 8 digit Manifest Number.");
		} 
		return validManifest;
	}

	function lengthCheckForManifestNumber(manifestNo) {
		if (manifestNo == "" || manifestNo.length != 8 ) {
			return false;
		} else {
			return true;
		}
	}
	function alphaNumericValidate(alphanumericChar){
		if(alphanumericChar.search(/[^a-zA-Z0-9 ]/g) != -1 )	{		
			return false;
		}else{	
			return true; 
		}
	}
	function alphaValidate(alphaChar){
		if(alphaChar.search(/[^a-zA-Z]/g) != -1 )	{		
			return false;
		}else{	
			return true; 
		}
	}
	
	function isAlphaAndNumber(stringVal){
		//TODO needs to find better solution
		var isAlpha = alphaValidate(stringVal);
		var isNumber = IsNumeric(stringVal);
		var isAlphaNum = alphaNumericValidate(stringVal);
		if(isAlpha){
			return "alpha";
		} else if(isNumber){
			return "number";
		} else if(isAlphaNum){
			return "alphaNumber";
		}
	}
	
	
	
	/* Show Short Approval PopUp */
	function shortApprovalPopup(obj,exceptionName,processName,transType,transNo,value,rowId) {
		//alert("$$@@shortApprovalPopup");
		//alert("$$@@exceptionName ="+exceptionName);
		//alert("$$@@processName ="+processName);
		//alert("$$@@transType ="+transType);
		//alert("$$@@transNo ="+transNo);
		//alert("$$@@value ="+value);
		
		var url = "./shortApproval.do?method=isActive&exceptionName="+exceptionName+"&processName="+processName;		
		new Ajax.Request(url, {
			method : 'post',
			onSuccess : function(req){showShortApprovalPopUp(req,obj,exceptionName,processName,transType,transNo,value,rowId);}
		});
		
	}

	function showShortApprovalPopUp(req,obj,exceptionName,processName,transType,transNo,value,rowId){
		var response = req.responseText;
		//alert("$$@@response "+response);
		if(response.trim() == "true"){			
			if(obj!=null){
				var par = obj.parentNode;
				while (par.nodeName.toLowerCase() != 'tr') {
					par = par.parentNode;
				}
				myRowId = (par.rowIndex);
			}
			var url = "./shortApproval.do?method=" + "showForm" + "&exceptionName=" + exceptionName + "&processName="+processName + "&transType="+ transType + "&transNo="+null + "&value="+null+"&rowId="+rowId;
			window.open(url, 'SHORTAPPROVAL','height=300,width=540,left=200,top=200,toolbar=yes,resizable=no,scrollbars=auto,location=no');
		}
	}
	
	function showAutoInscanPopup(pageURL,title,popup_width,popup_height){
		
		var left = (screen.width/2)-(popup_width/2);
		var top = (screen.height/2)-(popup_height/2);
		
		childWindow = window.open(pageURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+popup_width+', height='+popup_height+', top='+top+', left='+left);
		return childWindow;
	}
	
	/**
	 * The function generates the ID for the new Grid Row
	 * 
	 * @param tableID : Grid Table ID
	 * @param idSeparator : Grid's first element ID separator
	 * @returns {Number}: the row ID for the new Row to be created
	 */
	function getRowCountForNewRow(tableID,idSeparator){
		var rowCount = 0;
		var table = document.getElementById(tableID);
		var totalRows = table.rows.length;
		if(totalRows == 1){
			 rowCount = totalRows;
		}else{
			var row = table.rows[totalRows-1];
			var gridFirstElement = row.cells[0].childNodes[0];
			rowCount = getCurrentRowId(gridFirstElement,idSeparator);
			rowCount = rowCount+1; 
		}
		return rowCount;
	}
	
	/**
	 * The function returns the ID for the current Grid DOM Element
	 * 
	 * @param domElemet: Grid Element
	 * @param idSeparator: The prefix string of the Grid Element's ID
	 * @returns currentRowID
	 */
	function getCurrentRowId(domElement,idSeparator){
		var elemetID = domElement.id;
		var tokenArray = elemetID.split(idSeparator);
		return parseInt(tokenArray[1]);
	}
	
	/**
	 * The function checks if new row can be added or not
	 * 
	 * @param tableID: Grid table ID
	 * @param slNoIDSeparator: Grid's SL No element's ID separator
	 * @param currentRow: Current Row No
	 * @returns {Boolean}
	 */
	function canAddNewRow(tableID,slNoIDSeparator, currentRow){
		var addRowPossible = false;
		var table = document.getElementById(tableID);
		var totalRows = table.rows.length;
		var currentSlNo = document.getElementById(slNoIDSeparator+currentRow).innerHTML;
		if(currentSlNo == totalRows-1){
			addRowPossible = true;
		}
		return addRowPossible;
	}
	function releasePopUp(manifestNumber,mnfstTypeCode,rowId){
		 var url = "./release.do?submitName=showReleasePopUp&mnfstNum="+manifestNumber+"&mnfstTypeCode="+mnfstTypeCode+"&rowId="+rowId;;
		 var w =window.open(url,'Release PopUp','height=350,width=500,left=60,top=120,resizable=yes,scrollbars=auto');
	}
	
	// This below three fuctions are used for When any popup will appear, Parent screen will be grayed and desabled
	function unloadNotification() {
		setTimeout('checkChildWindowStatus()', 50);
	}
	
	function checkChildWindowStatus() {
		if (!childWindow || childWindow.closed) {
			jQuery.unblockUI();
		 }
	}
	
	
//pincode validations
	
	function isValidPincode(e,obj){
		
		var charCode;
		var pincode = obj.value;
		var id = obj.id;
		var len=pincode.length;
		i=parseInt(len)+1;	
		
		if(pincode != "" && pincode != null){
			if (window.event)
				charCode = window.event.keyCode; // IE
			else
				charCode = e.which; // firefox
			
				//alert("charCode "+charCode);
			if (charCode > 31 && (charCode < 48 || charCode > 57) || (i>6 && charCode!=8 && charCode!=0)){
				//alert("inside if");
					return false;
			 }
			 else{
				 //alert("inside else");
				 	return true;
			 }
		}
	}
	
	
	function timeValidation(obj,objvalue){
		var timRegX = /^(\d{1,2}):(\d{2})?$/;

		var timArr = objvalue.match(timRegX);
		if (timArr == null) {
		alert("Time is not in a valid format.");		
		document.getElementById(obj.id).value = "";
		document.getElementById(obj.id).focus();		
		return false;
		}
		hour = timArr[1];
		minute = timArr[2];



		if (hour < 0  || hour > 23) {
		alert("Hour must be between 1 and 12. (or 0 and 23 for 24 hours time)");
		return false;
		}

		if (minute<0 || minute > 59) {
		alert ("Minute must be between 0 and 59.");
		return false;
		}

		return false;

	}
	
	function validateCnLength(cnDomObj){
		var flag= true;
		var cnNo = cnDomObj.value;
		var fieldID = cnDomObj.id;
		if(cnNo != "" && cnNo != null){
			if(cnNo.length == 9 || cnNo.length == 12){
				if(cnNo.length == 9){
					if(!isNANCheck(cnNo)){
						flag = true;
					}
					else{
						flag =false;
					}
				}
				if(cnNo.length == 12){
					if(isNANCheck(cnNo)){
						flag = true;
					}else{
						flag =false;
					}
				}
			}else{
				flag =false;
			}
		}else{
			flag =false;;
		}
		
		return flag;		
	}	
	
	function checkDuplicateInGrid(elementName) {
		var isDuplicate = false;
		var arr = document.getElementsByName(elementName);			
		var values = new Array();
		for(var i = 0; i < arr.length; i++) {
	        var obj = document.getElementsByName(elementName).item(i);
	        var content = obj.value;
	        if(content.trim() != "") {
	        	values[i] = content;
	        }
	    }
	    if (values.length == 0) {
	    	isDuplicate = true;
	    } else {
	    	var sorted_arr = values.sort();
			var results = [];
			for (var i = 0; i < values.length - 1; i += 1) {
		        if (sorted_arr[i + 1] == sorted_arr[i]) {
		            isDuplicate = true;
		            break; 
		        }
			}
		}
		return isDuplicate;
	}
		//method created for duplicate consignment check by Ravi
	function checkDuplicateInGridByID(elementID) {
		var isDuplicate = false;
		var arr = document.getElementById(elementID);			
		var values = new Array();
		for(var i = 0; i < arr.length; i++) {
	        var obj = document.getElementById(elementID).item(i);
	        var content = obj.value;
	        if(content.trim() != "") {
	        	values[i] = content;
	        }
	    }
	    if (values.length == 0) {
	    	isDuplicate = true;
	    } else {
	    	var sorted_arr = values.sort();
			var results = [];
			for (var i = 0; i < values.length - 1; i += 1) {
		        if (sorted_arr[i + 1] == sorted_arr[i]) {
		            isDuplicate = true;
		            break; 
		        }
			}
		}
		return isDuplicate;
	}
	
	
	
	// Method to check weight range: Weight range is 0.001 to 99999.999
	function checkMaxWeight(weight){		
		var validWeight = true;		
		var tempWeight = weight.split('.');
		var nonDecimalNumber = tempWeight[0];
		var decimalNumber = tempWeight[1];
		// Only decimal point is provided as weight
		if(nonDecimalNumber == '' && decimalNumber == ''){
			validWeight = false;
		}else if(decimalNumber == undefined){
			// No decimal value is provided as weight [weight=5]
			if(parseInt(nonDecimalNumber,10) == 0 || parseInt(nonDecimalNumber,10) > 99999){
				validWeight = false;
			}
		}else if((nonDecimalNumber == '' || parseInt(nonDecimalNumber,10) == 0) && decimalNumber != ''){
			// Only decimal value is provided as weight [weight=0.005 or .005]
			if(parseInt(decimalNumber) > 999){
				validWeight = false;
			}else if(parseInt(decimalNumber,10) == 0 && parseInt(nonDecimalNumber,10) == 0){
				// Both decimal and number value is provided as weight but both are 0
				validWeight = false;
			}
		}else{
			// Both decimal and number value is provided as weight
			if((parseInt(nonDecimalNumber,10) == 0 || parseInt(nonDecimalNumber,10) > 99999) 
					|| decimalNumber.length > 3){
				validWeight = false;
			}
		}
		return validWeight;
	}
	
	function isAllowedBackDate(selectedDateStr,dateConfigLimit) {//date string should be in dd/mm/yyyy
		var today = new Date;
		var backDate=new Date;
		backDate.setDate(today.getDate()- dateConfigLimit);
		
		var selectDateArr = selectedDateStr.split("/");		// dd/mm/yyyy formate
		var selectedDate = new Date();
		selectedDate.setFullYear(selectDateArr[2],selectDateArr[1] - 1,selectDateArr[0]);
		
		return selectedDate >= backDate ? true : false;
	}
	
	function isValidDateRange(selectedDate,dateConfigLimit){
		var today = new Date;
		var flag = true;
		var validFlag = true;
		var futureDateFlag = false;
		var backDate=new Date;
		var date = new Date();
		backDate.setDate(today.getDate()- dateConfigLimit);
		var selectDate = selectedDate.split("/");
		date.setFullYear(selectDate[2],selectDate[1] - 1,selectDate[0]);
		
		if(date > today){
			futureDateFlag = true;		
		}
		if(date>=backDate && date<=today){
			flag = true;
		}
		else {
			flag = false;
		}
		if(!flag){
			if(futureDateFlag){
				alert("Future date is not allowed.");
				validFlag = false;
			}
			else {
			alert("Back date is not within allowed limit.");
			validFlag = false;
			}
		}
		return validFlag;
	}
	
	function showProcessing() {
		jQuery.blockUI( {message : '<img src="/ctbs-web/pages/resources/images/loading_animation.gif"/>'}); 
	}
	
	function showProcessingForPopUP() {
		jQuery.blockUI({ 
			//message: '<img src="pages/resources/images/ajax-loader.gif"/> Please close the popup to continue..', 
			message: '',
			fadeIn : 100, fadeOut : 200, timeOut : 8000 
			}); 
	}
	
	
	function isDecimalCheck(value){		
		if(value != null && value.trim() != ""){
			var valueArr = value.split(".");
			var length = valueArr.length;
			if(length < 3){
				return true;
			}
			else{
				return false;
			}
		}			
	} 
	
	function disableEnterKey(e, objectCn) {
		var key;
		if (window.event)
			key = window.event.keyCode; // IE
		else
			key = e.which; // firefox
		if (key == 13){
			objectCn.focus();
			return false;
		}else{
			
		}
	}

function disableF5(evt){
	if(evt.keyCode == 116){
		 if (evt.preventDefault) {
	         evt.preventDefault();
	         return false;
	     }
	     else {
	         evt.keyCode = 0;
	         evt.returnValue = false;
	     }
	}

}
function calculateDateDiff(fromDate, toDate) {
	var _fromDate = fromDate.split("/");
	var _toDate = toDate.split("/");
	// alert(" _heldUpDate &&&&& "+_heldUpDate);
	var _fromDateObj = new Date(_fromDate[2], _fromDate[1] - 1, _fromDate[0],
			00);
	var _toDateObj = new Date(_toDate[2], _toDate[1] - 1, _toDate[0], 00);
	var oneday = 24 * 60 * 60 * 1000;
	var calculateDate = _toDateObj - _fromDateObj;
	var dateDiff = Math.ceil(calculateDate / oneday);
	// alert(dateDiff);
	return dateDiff;
}
function createDropDownForManualDrs(domIdElmnt) {
	var domElement = document.getElementById('status');
	domElement.options.length = 0;
	var keyValList=null;
	// var domElement = createEmptyDropDown(domId);
	var keyValListForStock=["N=UNREAD","I=IN-Progress","T=READ","P=ALL","M=MANUAL"];
	var keyValListForOthers=["N=UNREAD","I=IN-Progress"];
	
	if(domIdElmnt.value == "S" || domIdElmnt.value =="U"){
		keyValList=keyValListForStock;
	}else{
		keyValList=keyValListForOthers;
	}
	for ( var i = 0; i < keyValList.length; i++) {
		var objOption = document.createElement("option");
		var keyValPair = keyValList[i].split("=");
		objOption.text = trimString(keyValPair[1]);
		objOption.value = trimString(keyValPair[0]);
		
		// modified for Browser compatibility(By sami)
		try {
			domElement.add(objOption, null);
		} catch (e) {
			domElement.add(objOption);
		}
	}
	domElement.selectedIndex=0;
}
function isNull(value) {
	var flag = true;
	if (value != undefined && value != null && value != "" && value != "null"
			&& value != " " && value != "0") {
		flag = false;
	}
	return flag;
}
function trimString(str) {
	var result = "";
	if (!isNull(str) && jQuery.type(str) == "string") {
		try {
			result = jQuery.trim(str);
		} catch (e) {
			result = str;
		}
	}
	return result;
}
	