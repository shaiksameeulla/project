// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
var errorListLenth;
var successListLenth;
var interfaceName;

var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
$(document).ready( function () {
	var oTable = $('#leadDetails').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

function checkFields() {
	var effectiveFrom = getDomElementById("effectiveFromStr");
	var effectiveTo = getDomElementById("effectiveToStr");
	
	if (isNull(effectiveFrom.value)) {
		alert('Please select Effective From date');
		effectiveFrom.focus();
		return false;
	}
	
	if (isNull(effectiveTo.value)) {
		alert('Please select Effective To date');
		effectiveTo.focus();
		return false;
	}

	if (!compareTwoDates())
		return false;

	return true;
}

/**
 * It compare Effective date and Effective To dats values
 * 
 * @returns {Boolean}
 */
function compareTwoDates() {
	var str1 = getDomElementById("effectiveFromStr").value;
	var str2 = getDomElementById("effectiveToStr").value;
	
	var dt1 = parseInt(str1.substring(0, 2), 10);
	var mon1 = parseInt(str1.substring(3, 5), 10);
	var yr1 = parseInt(str1.substring(6, 10), 10);
	var dt2 = parseInt(str2.substring(0, 2), 10);
	var mon2 = parseInt(str2.substring(3, 5), 10);
	var yr2 = parseInt(str2.substring(6, 10), 10);
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date2 = new Date(yr2, (mon2 - 1), dt2);
	var date = new Date();
	date3 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	
	if (date1 > date3) {
		alert("Effective From date should not be greater than present date");
		return false;		
	} else if (date2 < date1) {
		alert("Effective To date should be greater than Effective From date");
		return false;
	}
	else if (date2 > date3) {
		alert("Effective To date should not be greater than present date");
		return false;
	}
	
	return true;
}

function loadMasterOrTransactionData() {
	if(checkFields()) {
		var effectiveFrom = getDomElementById("effectiveFromStr").value;
		var effectiveTo = getDomElementById("effectiveToStr").value;
	
		var interfaceid = $('input[type=radio][name=ffcl]:checked').val();
	
		showProcessing();
		var url = './errorHandling.do?submitName=viewErrorHandlingScreen&effectiveFromStr='+effectiveFrom + '&effectiveToStr=' + effectiveTo + '&interfaceid=' + interfaceid;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getSuccessFailedRecords(req);
			}
		});
	}
}

/*function loadMasterOrTransactionData() {
	if(checkFields()) {
		var effectiveFrom = getDomElementById("effectiveFromStr").value;
		var effectiveTo = getDomElementById("effectiveToStr").value;
	
		var interfaceid = $('input[type=radio][name=ffcl]:checked').val();
	
		showProcessing();
		var url = './errorHandling.do?submitName=viewErrorHandlingScreen&effectiveFromStr='+effectiveFrom + '&effectiveToStr=' + effectiveTo + '&interfaceid=' + interfaceid;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getSuccessFailedRecords(req);
			}
		});
	}
}*/

 var errorList = null;
 var leadsList = null; 
function getSuccessFailedRecords(ajaxResp){
	serialNo = 1;
	rowCount = 1;
	
	deleteAllRow();
	
 leadsList = eval('(' + ajaxResp + ')');

	for(var i =0 ; i<leadsList.length; i++){	
		errorList = leadsList[i].errorList;
		if(leadsList[i].errorList.length > 0) {
			errorListLenth ='<a href="#" onclick="prepareErrorList('+i+');">' + leadsList[i].errorList.length + '</a>';
		} else {
			errorListLenth = leadsList[i].errorList.length;
		}
		
		successListLenth = leadsList[i].successList.length;
		interfaceName = leadsList[i].interfaceName;
		
		hideProcessing();
		
		$('#leadDetails')
		.dataTable()
		.fnAddData(
				[
				 serialNo,
				 interfaceName,
				 successListLenth, 
			errorListLenth
				]);
serialNo++;
		
		
	}
	
	hideProcessing();
}



function prepareErrorList(index) {
	var transException;	
	
	var effectiveFrom = getDomElementById("effectiveFromStr").value;
	var effectiveTo = getDomElementById("effectiveToStr").value;

	var newErrorList = leadsList[index].errorList;

	var errorArray = new Array();
	for(var k =0 ;k<newErrorList.length;k++){
		newException = newErrorList[k].tansException;
		transException = newErrorList[k].customerNo + "~" + newErrorList[k].tansException;
		errorArray.push(transException);
	}
	var url = './errorHandling.do?submitName=viewTransactionErrorHandling&effectiveFromStr='+effectiveFrom + '&effectiveToStr=' + effectiveTo + '&errorArray='+ errorArray;
	
	popupWindow = window.open(url,'name','width=700, height=1000, scrollbars=1');
	if(popupWindow && !popupWindow.closed) {
		popupWindow.focus();
	}
	
}

function parent_disable() {
if(popupWindow && !popupWindow.closed)
popupWindow.focus();
}


function addRow() {
	$('#leadDetails')
			.dataTable()
			.fnAddData(
					[
					 serialNo,
					 interfaceName,
					 successListLenth, 
					errorListLenth
					]);
	rowCount++;
	serialNo++;
}

function checkFields() {
	var effectiveFrom = getDomElementById("effectiveFromStr");
	var effectiveTo = getDomElementById("effectiveToStr");
	
	if (isNull(effectiveFrom.value)) {
		alert('Please select Effective From date');
		effectiveFrom.focus();
		return false;
	}
	
	if (isNull(effectiveTo.value)) {
		alert('Please select Effective To date');
		effectiveTo.focus();
		return false;
	}

	if (!compareTwoDates())
		return false;

	return true;
}

/**
 * It compare Effective date and Effective To dats values
 * 
 * @returns {Boolean}
 */
function compareTwoDates() {
	var str1 = getDomElementById("effectiveFromStr").value;
	var str2 = getDomElementById("effectiveToStr").value;
	
	var dt1 = parseInt(str1.substring(0, 2), 10);
	var mon1 = parseInt(str1.substring(3, 5), 10);
	var yr1 = parseInt(str1.substring(6, 10), 10);
	var dt2 = parseInt(str2.substring(0, 2), 10);
	var mon2 = parseInt(str2.substring(3, 5), 10);
	var yr2 = parseInt(str2.substring(6, 10), 10);
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date2 = new Date(yr2, (mon2 - 1), dt2);
	var date = new Date();
	date3 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	
	if (date1 > date3) {
		alert("Effective From date should not be greater than present date");
		return false;		
	} else if (date2 < date1) {
		alert("Effective To date should be greater than Effective From date");
		return false;
	}
	else if (date2 > date3) {
		alert("Effective To date should not be greater than present date");
		return false;
	}
	
	return true;
}

function clearErrorHandlingScreen() {
	document.errorHandlingForm.action = "./errorHandling.do?submitName=viewErrorHandlingScreen";
	document.errorHandlingForm.submit();
}

function checkRadio(){
	if($('#statusId').attr('checked')){
		$("#status").attr("disabled", false);
		$("#designation").val("");
		$("#designation").attr("disabled", true);
    	$("#assignedTo").val("");
    	$("#assignedTo").attr("disabled", true);
	}
	else if($('#designationId').attr('checked')){
		$("#designation").attr("disabled", false);
		$("#assignedTo").attr("disabled", false);
		$("#status").val("");
    	$("#status").attr("disabled", true);
    	$("#assignedTo").val("");
	}
	
}

function deleteAllRow() {
	var table = getDomElementById("leadDetails");
	var tableRowCount = table.rows.length;
	var oTable = $('#leadDetails').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
		
	}

}

/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		responseText = jsonJqueryParser(ObjeResp);
	}catch(e){
		
	}
	if(!isNull(responseText)){
		var error = responseText[ERROR_FLAG];
		if(!isNull(error)){
		alert(error);
		return true;
		}
	}
	return false;
}

function clearRowsOnRadioSelect(){
	if($('#statusId').attr('checked')){
		deleteAllRow();
	}
	if($('#designationId').attr('checked')){
		deleteAllRow();
	}
}
	
function disableForm() {
  var limit = document.forms[0].elements.length;
  for (var i=0;i<limit;i++) {
    document.forms[0].elements[i].disabled = true;
  }
}