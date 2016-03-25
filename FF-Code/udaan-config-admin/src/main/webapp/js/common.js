
/***************************************************************************
This source is part of the First Flight Java App Server Software System and is
copyrighted by Capgemini Consulting India private Limited.
All rights reserved. No part of this work may be reproduced, stored in a
retrieval system, adopted or transmitted in any form or by any means,
electronic, mechanical, photographic, graphic, optic recording or otherwise,
translated in any language or computer language, without the prior written
permission of Capgemini Consulting India private Limited.

Capgemini Consulting India private Limited.
GODREJ INDUSTRIES COMPLEX, VIKHROLI (E)
Mumbai (ex Bombay)Maharashtra
India
Copyright © 2012 Capgemini Consulting India private Limited Solutions Limited. 

Modification History

Date 		Version 		Author 		Description
__________ ___________ _______________ ________________________________________
17-10-2012   1.1 		Sameeulla      created re-usable script elements

 **************************************************************************** */ 
//-----------------------------------------------------------------------------*/

//variables
/**
 * Allows you to tag each parameter supported by a function.
 */
var inputType="input";
var hiddenType="hidden";
var textAreaType="textarea";
var checkBoxType="checkbox";
var align="center";
var BUTTON_DISABLE_CLASS='btnintformbigdis';
var BUTTON_ENABLE_CLASS='btnintform';
var GLOBAL_CHARACTER_TILD="~";
var REQ_SUBMITTED_ALERT="Request already submitted, please wait while we process your request.";
var GRID_DTLS_NOT_EXIST_ALERT="There are no details to save";
/**ajaxCall
 *@param: url : url to be called.
 *@param: formId : Form to be serialized.
 *@param: ajaxResponse : function to be called as call back method after getting response.
 @return: 
 */
function ajaxCall(url,formId,ajaxResponse){
	ajaxCallWithParam(url,formId,ajaxResponse,null);
}
/**
 * ajaxCallWithParam
 *@param: url : url to be called.
 *@param: formId : Form to be serialized.
 *@param: ajaxResponse : function to be called as call back method after getting response.
 *@param: rowNum : Row number id of the table in the given form
 @return: 
 */
function ajaxCallWithParam(url,formId,ajaxResponse,rowNum){
	// jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	progressBar();
	//alert(url+"\t"+formId+"\t");
	jQuery.ajax({
		url: url,
		data:jQuery("#"+formId).serialize(),
		context: document.body,
		success: function (data){
			jQuery.unblockUI();
			if(isNull(rowNum)){
				ajaxResponse(data);
			}else{
				ajaxResponse(data, rowNum);}
		},
		error: function( xhr, ajaxOptions, thrownError ) {
			jQuery.unblockUI();
			//alert('Server Un-available(network error)');
			//alert( "xhr :"+xhr+"\tajaxOptions:"+ajaxOptions+"\tthrownError:"+thrownError);
		}
	});
	jQuery.unblockUI();
}

function ajaxCallWithoutForm(pageurl, ajaxResponse) {
	progressBar();
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		}
	});
	jQuery.unblockUI();
}
function ajaxCalWithoutForm(pageurl, ajaxResponse) {
	progressBar();
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		}
	});
}
/**
 * calculateDateDiff
 *@param: fromDate : From date Object 
 *@param: toDate : toDate date Object
 @return: difference between two days (in days as int)
 */
function calculateDateDiff(fromDate,toDate){
	var _fromDate =fromDate.split("/");
	var  _toDate = toDate.split("/");
	//alert(" _heldUpDate  &&&&&   "+_heldUpDate);
	var _fromDateObj = new Date(_fromDate[2],_fromDate[1]-1,_fromDate[0], 00);
	var _toDateObj = new Date(_toDate[2],_toDate[1]-1,_toDate[0], 00);
	var oneday = 24*60*60*1000;
	var calculateDate=_toDateObj-_fromDateObj;
	var dateDiff=Math.ceil(calculateDate/oneday);
	//alert(dateDiff);
	return dateDiff;
}
/**
 * isNull
 *@param: value : From date Object 
 @return: Boolean ie either true or false
 */
function isNull(value){
	var flag=true;
	if (value !=undefined && value!= null && value!=""  && value != "null" && value!=" " && value !="0"  ){
		flag = false;
	}
	return  flag;
}


/**
 * buttonDisabled
 *@param: btnName : Name of the button
 @return: styleclass ie css to be applied to given Element
 */
function buttonDisabled(btnName,styleclass){
	jQuery("#"+btnName).attr("disabled", true);
	jQuery("#"+btnName).addClass(styleclass);
}
/**
 * buttonEnabled
 *@param: btnName : Name of the button
 @return: styleclass ie css to be applied to given Element
 */
function buttonEnabled(btnName,styleclass){
	jQuery("#"+btnName).attr("disabled", false);
	jQuery("#"+btnName).addClass(styleclass);
}
/**
 * promptConfirmation
 *@param: action : Name of the Action
 @return: Boolean either True or false
 */
function promptConfirmation(action){
	return confirm("Do you want to "+action+"  details ?");
}
/**
 * getDomElementById : its wrapper method
 *@param: Id : id of the Element
 @return: Dom(Document Object model) element
 */
function getDomElementById(id){
	return document.getElementById(id);
}
/**
 * getValueByElementId : its wrapper method 
 *@param: Id : id of the Element
 @return: value Dom(Document Object model) element
 */
function getValueByElementId(id){
	return getDomElementById(id).value;
}

function getDomElementByName(id){
	return document.getElementsByName(id);
}
/**
 * onlyNumeric : it returns only numeric to the input element 
 *@param: e: key press event
 @return: numerics
 */
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

/**
 * onlyDecimal : it returns only Decimal to the input element 
 *@param: e: key press event
 @return: onlyDecimal
 */
function onlyDecimal(e, rateType)
{
	var fieldValue;
	var charCode;
	var obj;
	
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	var rateId = rateType.id;
	obj = document.getElementById(rateId);
	
	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode!=46){
		if(charCode == 8) { //  || charCode == 0
			return true;
		} else {
			return false;
		}
	}
	else{
		var decimalsAllowed = 1;
		fieldValue = obj.value;
		var dectext = checkDecimals(fieldValue);
		if (dectext.length > decimalsAllowed) {
			if(charCode == 8) { // || charCode == 0
				return true;
			}
			return false;
		} else {
			return true;
		}
		
		return true;
	}
}

function checkDecimals(fieldValue) {
	if (fieldValue.indexOf('.') == -1) 
		fieldValue += ".";
	dectext = fieldValue.substring(fieldValue.indexOf('.')+1, fieldValue.length);
	return dectext;
}		

function isNumberKey(evt, obj) {
	 
    var charCode = (evt.which) ? evt.which : event.keyCode
    var value = obj.value;
    var dotcontains = value.indexOf(".") != -1;
    if (dotcontains)
        if (charCode == 46) return false;
    if (charCode == 46) return true;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}

/**
 * onlyAlphabet : it returns only Alphabets to the input element 
 *@param: e: key press event
 @return: onlyAlphabet
 */
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

//It allows only [A-Za-z0-9] only
function onlyAlphaNumeric(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90) || (charCode>=48 && charCode<=57)||charCode==9 || charCode==8){
		return true;
	}
	else{
		return false;
	}
	return false;
}
/**
 * getRowId : split of dom object with given element,and returns Row id 
 *@param: domElement: Html Object
 *@param: elementName: Html Object name as String
 @return: integer as Row Id
 */
function getRowId(domElement, elementName){
	var id = domElement.id;
	var tokenArray = id.split(elementName);
	return tokenArray[1];  
}
/**
 * convertToUpperCase  
 *@param: val: input value
 @return: Upper case element 
 */
function convertToUpperCase(val){
	return val.toUpperCase();
}
function convertDOMObjValueToUpperCase(obj) {
	var domValue = $.trim(obj.value);
	obj.value = domValue.toUpperCase();
}
//Re-usable method – creating row
function createRow(name,id,size,type,inputType,isReadOnly,isDisabled,maxlength){
	var element = document.createElement('"'+inputType+'"');
	element.type = type;
	element.name = name;
	element.id = id;
	element.size = size;
	if(isReadOnly)
		element.readOnly = true;
	if(isDisabled)
		element.disabled = true;
	if(maxlength !=null && maxlength!="")
		element.maxlength=maxlength;  
	return element;
}

/* Enter key navigation */
function callEnterKey(e, objectCn) {
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

//Allow only Alphabets
/*function onlyAlphabet(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 46
			|| charCode == 32 || charCode == 9 || charCode == 8
			|| charCode == 127 || charCode == 37 || charCode == 39
			|| charCode == 0) {
		return true;
	} else {
		return false;
	}

}*/
//To Check the selected date is future date
function isFutureDate(selectedDate) {

	var date =  trimString(selectedDate);
	if (date == "")
		return false;

	date = date.split("/");
	var myDate = new Date();
	myDate.setFullYear(date[2], date[1] - 1, date[0]);
	var today = new Date();
	if (myDate > today) {
		return true;
	}
	return false;
}
//To Check the selected date is Back date
function isBackDate(selectedDate) {
	var date = trimString(selectedDate);
	if (date == "")
		return false;

	date = date.split("/");
	var myDate = new Date();
	myDate.setFullYear(date[2], date[1] - 1, date[0]);
	var today = new Date();
	if (myDate < today) {
		return true;
	}
	return false;
}
//Comapre two dates
function compareDates(date1, date2) {
	if (date1 == undefined || date2 == undefined) {
		return -100;// garbage value
	}
	date1 = date1.split("/");
	date2 = date2.split("/");

	var myDate1 = new Date();
	myDate1.setFullYear(date1[2], date1[1] - 1, date1[0]);

	var myDate2 = new Date();
	myDate2.setFullYear(date2[2], date2[1] - 1, date2[0]);
	if (myDate1 == myDate2) {
		return 0;
	} else if (myDate1 < myDate2) {
		return -1;
	} else if (myDate1 > myDate2) {
		return 1;
	}
}
//Phone number validation
function isValidPhone(phoneNumber) {
	if (phoneNumber == null) {
		return false;
	}
	// Expression for numeric values
	var objRegExp = /(^-?\d\d*$)/;

	if (objRegExp.test(phoneNumber)) {
		if (7 <= phoneNumber.length && phoneNumber.length <= 10) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
//NAN Check validation 
function isNANCheck(elementValue) {
	for ( var i = 0; i < elementValue.length; i++) {
		var cn = elementValue.charAt(i);
		if (isNaN(cn)) {
			return false;
		}
	}
	return true;
}


/** validation start for email */
/*function checkMailId(mailIds) {
	var mailId = mailIds.value;
	var val = true;
	var beforeat = "";
	var afterat = "";
	var afterat2 = "";


	var dot = mailId.lastIndexOf(".");
	var con = mailId.substring(dot, mailId.length);
	con = con.toLowerCase();
	con = con.toString();

	var att = mailId.lastIndexOf("@");
	beforeat = mailId.substring(0, att);
	beforeat = beforeat.toLowerCase();
	beforeat = beforeat.toString();
	var asci1 = beforeat.charCodeAt(0);

	afterat = mailId.substring(att + 1, dot);
	afterat = afterat.toLowerCase();
	afterat = afterat.toString();

	afterat2 = mailId.substring(att + 1, mailId.length);
	afterat2 = afterat2.toLowerCase();
	afterat2 = afterat2.toString();

	if (beforeat == "" || afterat == "" || beforeat.length > 30)
		val = false;

	if (afterat2.length > 64 || afterat.length < 2)
		val = false;

	if ((afterat.charCodeAt(0)) == 45
			|| (afterat.charCodeAt(afterat.length - 1)) == 45)
		val = false;

	if (val == true) {
		if (asci1 > 47 && asci1 < 58)
			val = false;

		if (asci1 < 48 || asci1 > 57) {
			for ( var i = 0; i <= beforeat.length - 1; i++) {
				var asci2 = beforeat.charCodeAt(i);
				if ((asci2 <= 44 || asci2 == 47)
						|| (asci2 >= 58 && asci2 <= 94) || (asci2 == 96)
						|| (asci2 >= 123 && asci2 <= 127)) {
					val = false;
					break;
				}
			}

			for ( var j = 0; j <= afterat.length - 1; j++) {
				var asci3 = afterat.charCodeAt(j);
				if ((asci3 <= 44) || (asci3 == 46) || (asci3 == 47)
						|| (asci3 >= 58 && asci3 <= 96)
						|| (asci3 >= 123 && asci3 <= 127)) {
					val = false;
					break;
				}
			}
		}
	}

	if (val == false) {
		if (mailId != "null" && mailId != "") {
			alert("Mail Id is not valid");
			mailIds.value = "";
			setTimeout(function() {
				document.getElementById(mailIds.id).focus();
			}, 10);
			return false;
		} else {
			return true;
		}
	} else {
		return true;
	}
}*/
function alphaNumericValidate(alphanumericChar){
	if(alphanumericChar.search(/[^a-zA-Z0-9 ]/g) != -1 )	{		
		return false;
	}else{	
		return true; 
	}
}
function checkAllBoxes(ckbxsName,checkedVal){
	var box = document.getElementsByName(ckbxsName);
	//alert("box.length"+box.length);
	if(box !=null && box.length >0){
		for(var i=0;i<box.length;i++){
			if(checkedVal == true){
				box[i].checked =true;
			}else if(checkedVal == false){
				box[i].checked =false;
			}
		}
	}
}
/**
 * createDropDown 
 * @param domId
 * @param resList
 * @returns {___domElement1}
 */
function createDropDown(domId,resList){
	var domElement = getDomElementById(domId);
	domElement.options.length=0;
	var optionSelectType = document.createElement("OPTION");
	//var text = document.createTextNode("--Select--");
	optionSelectType.value = "";
	optionSelectType.text=" Select ";
	//domElement.add(optionSelectType,null);
	try {
		domElement.add(optionSelectType,null);
	}catch(e){
		domElement.add(optionSelectType);
	}
	//var domElement = createEmptyDropDown(domId);
	var newStr=resList.replace("{","");
	var newStr1=newStr.replace("}","");
	var keyValList = newStr1.split(",");
	for(var i=0; i < keyValList.length; i++){
		var objOption = document.createElement("option");
		var keyValPair = keyValList[i].split("=");
		objOption.text = trimString(keyValPair[1]);
		objOption.value = trimString(keyValPair[0]);
		//domElement.add(objOption,null);
		//modified for Browser compatibility(By sami) 
		try {
			domElement.add(objOption,null);
		}catch(e){
			domElement.add(objOption);
		}
	}
	//domElement.setAttribute("style","width: 100px");
	return domElement;
}
/**
 * createEmptyDropDown
 * @param domId
 * @returns {___domElement4}
 */
function createEmptyDropDown(domId){
	var domElement = getDomElementById(domId);
	domElement.options.length=0;
	var optionSelectType = document.createElement("OPTION");
	//var text = document.createTextNode("--Select--");
	optionSelectType.value = "";
	optionSelectType.text=" Select ";
	//modified for Browser compatibility(By sami) 
	try {
		domElement.add(optionSelectType,null);
	}catch(e){
		domElement.add(optionSelectType);
	}
	return domElement;
}
/**
 * clearDropDown
 * @param elementId
 */
function clearDropDown(elementId){
	getDomElementById(elementId).options.length = 0;

}
/**ajaxJquery
 * 
 * @param url
 * @param formId
 * @param ajaxResponseMethod
 */
function ajaxJquery(url,formId,ajaxResponseMethod){
	ajaxJqueryWithRow(url,formId,ajaxResponseMethod,null);
}
/**
 * ajaxJqueryWithRow
 * @param url
 * @param formId
 * @param ajaxResponseMethod
 * @param rowId
 */
function ajaxJqueryWithRow(url,formId,ajaxResponseMethod,rowId){
	progressBar();
	$.ajax({
		url : url,
		type: "POST",
		dataType: "text",
		data:jQuery("#"+formId).serialize(),
		success : function(rsp) {
			jQuery.unblockUI();
			if(isNull(rowId)){
				ajaxResponseMethod(rsp);
			}else{
				ajaxResponseMethod(rsp,rowId);
			}	},
			error: function(rsp){
				if(isNull(rowId)){
					ajaxResponseMethod(null);
				}else{
					ajaxResponseMethod(null,rowId);
				}	
				jQuery.unblockUI();
			}
	});
}

/**
 * progressBar
 */
function progressBar(){
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}
/**
 * buttonDisable: Disables all button
 */
function buttonDisable(){
	jQuery(":button").attr("disabled", true);
	jQuery(":button").addClass(BUTTON_DISABLE_CLASS);

}
/**
 * inputDisable: Make readonly to all fields
 */
function inputDisable(){
	jQuery(":input").attr("readonly", true);

}
/**
 * inputDisable: Make readonly to all fields
 */
function inputTypeTextDisable(){
	jQuery("input:text").attr("readonly", true);

}
function makeReadOnlyById(filedId){
	var  fieldDom=$('#'+filedId);
	fieldDom.attr("readonly", true);

}
function makeDisableOnlyById(filedId){
	var  fieldDom=$('#'+filedId);
	fieldDom.attr("disabled", true);

}
/**
 * dropdownDisable
 */
function dropdownDisable(){
	jQuery("select").attr("disabled", 'disabled');

}
function checkboxDisable(){
	jQuery("input:checkbox").attr("disabled", true);

}
/**
 * dropdownEnable
 */
function dropdownEnable(){
	jQuery("select").attr("disabled", false);

}
/**
 * disableAll
 */
function disableAll(){
	buttonDisable();
	inputDisable();
	dropdownDisable();
	checkboxDisable();
}
function enableAll() {
	buttonEnable();
	inputEnable();
	dropdownEnable();
}
function buttonEnable(){
	jQuery(":button").attr("disabled", false);

}
/**
 * inputEnable
 *
 * @author narmdr
 */
function inputEnable(){
	jQuery(":input").attr("readonly", false);	
	jQuery(":input").attr("disabled", false);	
}


/**
 * getTableLength
 * @param tableId
 * @returns
 */
function getTableLength(tableId){
	var tableee = getDomElementById(tableId);
	return  tableee.rows.length;
}
/**
 * getTableRows
 * @param tableId
 * @returns
 */
function getTableRows(tableId){
	var tableee = getDomElementById(tableId);
	return tableee.getElementsByTagName("tr");
}
//check whether check box is selected or not
function isCheckBoxSelected(chkboxElem){
	return isCheckBoxSelectedWithMessage(chkboxElem,'CheckBox is not selected');
}

function isCheckBoxSelectedWithMessage(chkboxElem,message){
	var checkFalg = 0;   
	var box = document.getElementsByName(chkboxElem);
	for(var i=0;i<box.length;i++){
		if(box[i].checked){
			checkFalg = 1;
			break;
		}else{
			checkFalg = 0;
		}
	}	 
	if(box== null || box.length ==0){
		alert(GRID_DTLS_NOT_EXIST_ALERT);
		return false;
	}else	if(checkFalg==0){
		alert(message);
		return false;
	}
	return true;
}

function isAtleastOneCheckBoxSelected(chkboxElem){
	var checkFalg = 0;   
	var box = document.getElementsByName(chkboxElem);
	for(var i=0;i<box.length;i++){
		if(box[i].checked){
			checkFalg = 1;
			break;
		}else{
			checkFalg = 0;
		}
	}	 
	if(checkFalg==1){
		return true;
	}
	return false;
}
//return end serial number based on start serial number and Quantity
/**
 * @param stSlNo
 * @param quantity
 * @returns End serial numner (String)
 */
function getEndSerialNumber(stSlNo,quantity){
	var zeros=0;
	var nonZeros=0;
	var zeroDeletions=0;
	var count=getCountFromString(stSlNo);
	var startNum=stSlNo.substring(count,stSlNo.length);
	//get substring from count till quantity.length
	var startchar=stSlNo.substring(0,count);

	//alert("startchar"+startchar);

	if(startNum!=null && trimString(startNum)!=''){
//		get substring from count till quantity.length
		var startchar=stSlNo.substring(0,count);
		var EndNum=parseInt(quantity,10)+parseInt(startNum,10);
		if(EndNum>0) EndNum=EndNum-1;	
//		perform trailing zeros --start
		var startNumSize=startNum.length;
		var afterParsing=parseInt(startNum,10)+"";
		var afterParsingSize=afterParsing.length;
		var endNumva=EndNum+"";
		var EndNumsize=endNumva.length;
		if(startNumSize>afterParsingSize){
			//alert("sss");
			var startArray=new Array();
			for(var i=0;i<startNumSize;i++){
				startArray[i] =startNum.charAt(i);
				if(startArray[i]=="0"){
					++zeros;
				}else
					++nonZeros;
			}

			//alert("Total Zeros"+zeros);
			//alert("NonZeros"+nonZeros);
			if(EndNumsize>nonZeros){
				zeroDeletions=EndNumsize-nonZeros;
			}
			if(zeroDeletions>0){
				zeros=zeros-zeroDeletions;
			}
			for(var i=0;i<zeros;i++)
				EndNum="0"+EndNum;
		}
//		perform trailing zeros --end

		var EndSlNum=startchar+EndNum;
		//alert("start Num"+stSlNo+"\t"+"end serial Num"+EndSlNum);
		return EndSlNum;


	}else{
		alert("Serial number is having incorrect format");
		return false;
	}
}
//helper method for getEndSerialNumber
function getCountFromString(str){
	var count11=str.length;
	for(var k=str.length-1;k>=0;k--){
		var charCode=str.charCodeAt(k);
		if (charCode > 31 && (charCode < 48 || charCode > 57)){break;}
		count11--;
	}
	return count11;
}
/**jsonJqueryParser
 * @param jsonObj
 * @returns Json Object
 */
function jsonJqueryParser(jsonObj){
	return jQuery.parseJSON(jsonObj);
}
/**
 * getSelectedDropDownTextByDOM
 * @param domElement
 * @returns
 */
function getSelectedDropDownTextByDOM(domElement){
	var dropDownDom = domElement;
	var selectedIndex= dropDownDom.selectedIndex;
	return dropDownDom.options[selectedIndex].text;
}
/**
 * getSelectedDropDownText
 * @param selectId
 * @returns
 */
function getSelectedDropDownText(selectId){
	var dropDownDom = getDomElementById(selectId);
	return getSelectedDropDownTextByDOM(dropDownDom);
}
/**
 * updatedCheckBoxValues
 * @param chkboxElem
 * @returns {Boolean}
 */
function updatedCheckBoxValues(chkboxElem){
	var box = document.getElementsByName(chkboxElem);
	for(var i=0;i<box.length;i++){
		box[i].value=i;
	}	 
	return true;
}
/**
 * isExistInArray
 * @param value
 * @param array
 * @returns index of the array
 */
function isExistInArray(value,array){
	return jQuery.inArray( value, array  );
}
/**
 * enterKeyNav
 * @param nextFieldId
 * @param keyCode
 * @returns {Boolean}
 */
function enterKeyNav(nextFieldId,keyCode) { 
	if (keyCode == 13) { 
		var nextField = document.getElementById(nextFieldId); 
		nextField.focus(); 
		return false; 
	} 

} 
/**enterKeyNavWithoutFocus
 * 
 * @param keyCode
 * @returns {Boolean}
 */
function enterKeyNavWithoutFocus(keyCode) { 
	if (keyCode == 13) { 
		return true;
	} 
	return false;
} 
/**
 * isBackSpaceButton
 * @param keyCode
 * @returns {Boolean}
 */
function isBackSpaceButton(keyCode) { 
	if (keyCode == 8) { 
		return true;
	} 
	return false;
} 
/**
 * checkNull
 * @param obj
 * @returns {Boolean}
 */

function checkNull(obj) {
	if (obj != "" && obj != null && obj != "null" && obj != undefined) {
		return false;
	} else {
		return true;
	}
}

//inoder to use below method include following script in ur jsp
//<script type="text/javascript" src="/js/jquery/jQuery_jqprint.js"></script>	
/**
 * printResults
 */
function printResults(divId){	
	jQuery('#'+divId+'').jqprint();
}
/**
 * trimString : to trim string
 * @param str
 * @returns
 */
function trimString(str){
	var result="";
	if(!isNull(str)){
		result=$.trim(str);
	}
	return result ;
}
/**
 * To show processing image
 */
function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}

/**
 * To hide processing image
 */
function hideProcessing() {
	jQuery.unblockUI();
}
function onlyNumberAndEnterKeyNav(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}
function onlyDecimalAndEnterKeyNav(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57) )&& charCode!=46){
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
	} else {
		return true;
	}
}

function validateEmail(addr) {
	if (addr == '') {
		alert('email address is mandatory');
		return false;
	}

	var invalidChars = '\/\'\\ ";:?!%#()[]\{\}^|';
	for (var i=0; i<invalidChars.length; i++) {
		if (addr.indexOf(invalidChars.charAt(i),0) > -1) {
			alert('email address contains invalid characters');
			return false;
		}
	}
	for (var i=0; i<addr.length; i++) {
		if (addr.charCodeAt(i)>127) {
			alert("email address contains non ascii characters.");
			return false;
		}
	}

	var atPos = addr.indexOf('@',0);
	if (atPos == -1) {
		alert('email address must contain an @');
		return false;
	}
	if (atPos == 0) {
		alert('email address must not start with @');
		return false;
	}
	if (addr.indexOf('@', atPos + 1) > - 1) {
		alert('email address must contain only one @');
		return false;
	}
	if (addr.indexOf('.', atPos) == -1) {
		alert('email address must contain a period in the domain name');
		return false;
	}
	if (addr.indexOf('@.',0) != -1) {
		alert('period must not immediately follow @ in email address');
		return false;
	}
	if (addr.indexOf('.@',0) != -1){
		alert('period must not immediately precede @ in email address');
		return false;
	}
	if (addr.indexOf('..',0) != -1) {
		alert('two periods must not be adjacent in email address');
		return false;
	}
	var suffix = addr.substring(addr.lastIndexOf('.')+1);
	suffix=suffix.toLowerCase();
	if (suffix.length != 2 && suffix != 'com' && suffix != 'net' && suffix != 'org' && suffix != 'edu' && suffix != 'int' && suffix != 'mil' && suffix != 'gov' & suffix != 'arpa' && suffix != 'biz' && suffix != 'aero' && suffix != 'name' && suffix != 'coop' && suffix != 'info' && suffix != 'pro' && suffix != 'museum') {
		alert('invalid primary domain in email address');
		return false;
	}
	return true;
}
/** validation start for email */
function checkMailId(mailids)
{

	var arr = new Array('.com','.net','.org','.biz','.coop','.info','.museum','.name','.pro'
			,'.edu','.gov','.int','.mil','.ac','.ad','.ae','.af','.ag','.ai','.al',
			'.am','.an','.ao','.aq','.ar','.as','.at','.au','.aw','.az','.ba','.bb',
			'.bd','.be','.bf','.bg','.bh','.bi','.bj','.bm','.bn','.bo','.br','.bs',
			'.bt','.bv','.bw','.by','.bz','.ca','.cc','.cd','.cf','.cg','.ch','.ci',
			'.ck','.cl','.cm','.cn','.co','.cr','.cu','.cv','.cx','.cy','.cz','.de',
			'.dj','.dk','.dm','.do','.dz','.ec','.ee','.eg','.eh','.er','.es','.et',
			'.fi','.fj','.fk','.fm','.fo','.fr','.ga','.gd','.ge','.gf','.gg','.gh',
			'.gi','.gl','.gm','.gn','.gp','.gq','.gr','.gs','.gt','.gu','.gv','.gy',
			'.hk','.hm','.hn','.hr','.ht','.hu','.id','.ie','.il','.im','.in','.io',
			'.iq','.ir','.is','.it','.je','.jm','.jo','.jp','.ke','.kg','.kh','.ki',
			'.km','.kn','.kp','.kr','.kw','.ky','.kz','.la','.lb','.lc','.li','.lk',
			'.lr','.ls','.lt','.lu','.lv','.ly','.ma','.mc','.md','.mg','.mh','.mk',
			'.ml','.mm','.mn','.mo','.mp','.mq','.mr','.ms','.mt','.mu','.mv','.mw',
			'.mx','.my','.mz','.na','.nc','.ne','.nf','.ng','.ni','.nl','.no','.np',
			'.nr','.nu','.nz','.om','.pa','.pe','.pf','.pg','.ph','.pk','.pl','.pm',
			'.pn','.pr','.ps','.pt','.pw','.py','.qa','.re','.ro','.rw','.ru','.sa',
			'.sb','.sc','.sd','.se','.sg','.sh','.si','.sj','.sk','.sl','.sm','.sn',
			'.so','.sr','.st','.sv','.sy','.sz','.tc','.td','.tf','.tg','.th','.tj',
			'.tk','.tm','.tn','.to','.tp','.tr','.tt','.tv','.tw','.tz','.ua','.ug',
			'.uk','.um','.us','.uy','.uz','.va','.vc','.ve','.vg','.vi','.vn','.vu',
			'.ws','.wf','.ye','.yt','.yu','.za','.zm','.zw');

	var mai = mailids.value;
	var val = true;
	var beforeat="";
	var afterat="";
	var afterat2="";


	/*
	 * for(var i=0;i<(arr.length);i++) { if(con == arr[i]) { val=true; break; }
	 * else { val=false; } }
	 */

	var dot=mai.lastIndexOf(".");
	var con=mai.substring(dot, mai.length);
	con=con.toLowerCase();
	con=con.toString();
	var hostName=null;
	var att=mai.lastIndexOf("@");
	beforeat=mai.substring(0,att);
	beforeat=beforeat.toLowerCase();
	beforeat=beforeat.toString();
	var asci1=beforeat.charCodeAt(0);

	afterat=mai.substring(att+1, dot);
	hostName=afterat;
	hostName=hostName.toUpperCase();
//	alert("hostName"+hostName);

//	Host Name validations
	if(hostName.length <=2){
		alert("Email Host Name should contain atleast 3 characters");
		return false;
	}

	var invalidChars = '\/\'\\ ";:?!%()#$&*[]\{\}^|';
	for (var i=0; i<invalidChars.length; i++) {
		if (hostName.indexOf(invalidChars.charAt(i),0) > -1) {
			alert('email Host contains invalid characters');
			return false;
		}
	}
	afterat=afterat.toLowerCase();
	afterat=afterat.toString();

	afterat2=mai.substring(att+1, mai.length);
	afterat2=afterat2.toLowerCase();
	afterat2=afterat2.toString();

	if(beforeat=="" || afterat=="" || beforeat.length>30)
		val=false;

	if(afterat2.length>64 || afterat.length<2)
		val=false;

	if((afterat.charCodeAt(0))==45 || (afterat.charCodeAt(afterat.length-1))==45)
		val=false;

	if(val==true)
	{
		if(asci1 > 47 && asci1 < 58)
			val=false;

		if(asci1 < 48 || asci1 > 57)
		{
			for(var i=0; i<=beforeat.length-1; i++)
			{
				var asci2=beforeat.charCodeAt(i);
				if((asci2<=44 || asci2==47) || (asci2>=58 && asci2<=94) || (asci2==96) || (asci2>=123 && asci2<=127))
				{
					val=false;
					break;
				}
			}

			for(var j=0; j<=afterat.length-1; j++)
			{
				var asci3=afterat.charCodeAt(j);
				if((asci3<=44) || (asci3==46) || (asci3==47) || (asci3>=58 && asci3<=96) || (asci3>=123 && asci3<=127))
				{
					val=false;
					break;
				}
			}	
		}
	}

	if(val==false)
	{		
		if(mai!="null" && mai!=""){
			alert("Mail Id is not valid");		
			mailids.value="";
			setTimeout(function(){document.getElementById(mailids.id).focus();},10);
			return false;
		}else
		{
			return true;
		}
	}
	else
	{	
		return true;
	}
}
/** validation end for email */

/**
 * getErrorMessage
 *
 * @param obj
 * @returns
 * @author narmdr
 */
function getErrorMessage(obj){
	var errorMsg = null;
	if(!isNull(obj)){
		errorMsg = obj["ERROR"];
		//errorMsg = obj.ERROR;
	}
	return errorMsg;
}
function buttonEnableById(fieldId) {
	document.getElementById(fieldId).disabled = false;
	jQuery("#"+fieldId).removeClass(BUTTON_DISABLE_CLASS);
	jQuery("#"+fieldId).addClass(BUTTON_ENABLE_CLASS);
}
function buttonDisableById(fieldId) {
	jQuery("#"+fieldId).attr("disabled", true);
	jQuery("#"+fieldId).addClass(BUTTON_DISABLE_CLASS);
}

function enableGlobalButton(btnId){
	var  glbBtn=$('#'+btnId);
	glbBtn.removeClass();
	glbBtn.addClass(BUTTON_ENABLE_CLASS);
	glbBtn.removeAttr("tabindex");
	glbBtn.attr("disabled", false);
}
function disableGlobalButton(btnId){
	var  glbBtn=$('#'+btnId);
	glbBtn.removeClass();
	glbBtn.addClass(BUTTON_DISABLE_CLASS);
	glbBtn.attr("disabled", true);
	glbBtn.attr("tabindex", -1);
}

/**
 * enterKeyNavFocusWithAlertIfEmpty
 *
 * @param evt
 * @param elementIdToFocus
 * @param msgLabel
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavFocusWithAlertIfEmpty(evt, elementIdToFocus, msgLabel){
	var pleaseSelectMsg = "Please Select ";
	var pleaseEnterMsg = "Please Enter ";
	var errorEndMsg = " !" ;

	var currentObj = $(evt.target);
	if(isEnterKey(evt)){
		if(!isNull(currentObj.val())){
			$("#" + elementIdToFocus).focus();
		}else{
			var msg = null;
			if(isDropDown(currentObj.attr("id"))){
				msg = pleaseSelectMsg + msgLabel + errorEndMsg;
			}else{
				msg = pleaseEnterMsg + msgLabel + errorEndMsg;
			}
			clearFocusAlertMsg(currentObj, msg);
		}
		return true;
	}
	return false;
}

/**
 * isButton
 *
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isButton(fieldId){
	if($("#"+fieldId).is("input:button")){
		return true;
	}
	return false;
}

/**
 * isTextBox
 *
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isTextBox(fieldId){
	if($("#"+fieldId).is("input:text")){
		return true;
	}
	return false;
}

/**
 * isDropDown
 *
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isDropDown(fieldId){
	if($("#"+fieldId).is("select")){
		return true;
	}
	return false;
}


/**
 * isEnterKey
 *
 * @param evt
 * @returns {Boolean}
 * @author narmdr
 */
function isEnterKey(evt){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if(charCode==13){
		return true;
	}
	return false;
}


/**
 * enterKeyNavigationFocus
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * 
 * @author narmdr
 */
function enterKeyNavigationFocus(evt, elementIdToFocus){	
	if(isEnterKey(evt)){
		focusById(elementIdToFocus);
		return true;
	}
	return false;
}

/**
 * enterKeyNavigation
 * 
 * @param evt
 * @param elementId
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigation(evt, elementId) {
	if (isEnterKey(evt)) {
		$("#" + elementId).click();
		return true;
	}
	return false;
}

function setFocusOnElmentByDom(domElemnt){
	if(domElemnt!=null){
		setTimeout(function(){domElemnt.focus();},10);
	}
}
function setFocusOnElmentById(domElemntId){
	var domEmmnt=getDomElementById(domElemntId);
	if(domEmmnt!=null){
		setFocusOnElmentByDom(domEmmnt);
	}
}
function isJQueryObject(obj){
	if(obj instanceof jQuery){
		return true;
	}
	return false;
}
function clearFocusAlertMsg(obj, msg){
	if(isJQueryObject(obj)){
		obj.val("");
	}else{
		obj.value = "";
	}
	obj.focus();
	alert(msg);
	setTimeout(function() {
		obj.focus();
	}, 10);
}

function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}
/**roundDecimal
 * 
 * @param inpValue
 * @param decim
 * @returns {Number}
 */
function roundDecimalLength(inpValue,decim){
	var inputValue=inpValue;
	var result=0;
	if(isNaN(inputValue) || isNull(inputValue) ){
		inputValue=0;
	}

	try{
		var num = new Number(inputValue);
		result=num.toFixed(decim);
	}catch (e){
		alert(e);
	}
	return result;
}

function  hideElementByDomId(domElmId){
	//var targentElmt=getDomElementById(domElmId);
	var  targentElmt=$('#'+domElmId);
	if(targentElmt!=null){
		//targentElmt.style.display ="none";
		targentElmt.hide();
	}
	
}

function focusById(elementId){	
	var elementObj = $("#"+elementId);
	//elementObj.focus();
	setTimeout(function() {
		elementObj.focus();
	}, 10);
}

function enterKeyNavCallOnChange(evt, obj){
	if(isEnterKey(evt)){
		obj.onchange();
	}
}

/**
 * To set maxlength for text area
 * @param e
 * @param obj
 * @param size
 * @returns {Boolean}
 */
function setMaxLengthForTextArea(e, obj, size) {
	var charCode;

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode == 127 || charCode == Number('08') || charCode == 0) {
		return true;
	} else {
		if (obj.value.length > size) {
			return false;
		}
		return true;
	}

}
function screenClose(){
	window.close();
}
function createDropDownForJquery(domId, jsonArrayList) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	optionSelectType.value = "";
	optionSelectType.text = "--Select--";
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}

	for ( var i = 0; i < jsonArrayList.length; i++) {
		var objOption = document.createElement("option");
		objOption.value = trimString(jsonArrayList[i]['KEY']);
		objOption.text = trimString(jsonArrayList[i]['VALUE']);
		// modified for Browser compatibility(By sami)
		try {
			domElement.add(objOption, null);
		} catch (e) {
			domElement.add(objOption);
		}
	}
	// domElement.setAttribute("style","width: 100px");
	return domElement;
}

function ajaxCallWithPost(url,formId,ajaxResponse){
	ajaxCallWithParamWithPost(url,formId,ajaxResponse,null);
}
/**
 * ajaxCallWithParam
 *@param: url : url to be called.
 *@param: formId : Form to be serialized.
 *@param: ajaxResponse : function to be called as call back method after getting response.
 *@param: rowNum : Row number id of the table in the given form
 @return: 
 */
function ajaxCallWithParamWithPost(url,formId,ajaxResponse,rowNum){
	// jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	progressBar();
	//alert(url+"\t"+formId+"\t");
	jQuery.ajax({
		url: url,
		type: "POST",
		data:jQuery("#"+formId).serialize(),
		context: document.body,
		success: function (data){
//			jQuery.unblockUI();
			if(isNull(rowNum)){
				ajaxResponse(data);
			}else{
				ajaxResponse(data, rowNum);}
		},
		error: function( xhr, ajaxOptions, thrownError ) {
			jQuery.unblockUI();
			//alert('Server Un-available(network error)');
			//alert( "xhr :"+xhr+"\tajaxOptions:"+ajaxOptions+"\tthrownError:"+thrownError);
		}
	});
	jQuery.unblockUI();
}
function setDataTableDefaultWidth() {
	$('.dataTables_scrollHeadInner').width("100%");
}

function setFractions(obj, digits) {
	if (!isNull(obj.value))
		obj.value = parseFloat(obj.value).toFixed(digits);
}
function onlyNumberNenterKeyNav(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}