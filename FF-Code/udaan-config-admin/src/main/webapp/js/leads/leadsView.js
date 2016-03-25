// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
$(document).ready( function () {
	var oTable = $('#leadDetails').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"130%",
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


function loadDefaultObjects() {
	if(checkFields()) {
		if(userRole == salesExecutiveRole || userRole == controlTeamRole)
		{
			addRow();
			if(userRole == salesExecutiveRole){
				getLeadsByUser();
			}else if(userRole == controlTeamRole){
				getLeadsByRegion();
			}
		}
		else
		{
			alert("User does not have required role to operate Leads View screen.");
			disableForm();
		}
	}
}

function clearLeadsViewScreen() {
	document.leadsViewForm.action = "./leadsView.do?submitName=preparePage";
	document.leadsViewForm.submit();
}

function addRow() {
	$('#leadDetails')
			.dataTable()
			.fnAddData(
					[
					 serialNo,
					 '<label for="leadNumber"><input type="text" name = "to.leadNumber" id="leadNumber'+ rowCount+ '" class="txtbox width115"  readonly="true"  /></label>', 
						
					 '<input type="text" name = "to.customerName" id="customerName'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.contactPerson" id="contact'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.stdCode" id="stdCode'+ rowCount+ '" class="txtbox" style="float:left;width:30%;" readonly="true"  /><input type="text" name = "to.phoneNo" id="contactNumber'+ rowCount+ '" class="txtbox" style="float:right;width:65%;" readonly="true"  />',
					
					 '<input type="text" name = "to.mobileNo" id="mobileNumber'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.emailAddress" id="email'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.status.statusDescription" id="status'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
					 '<input type="text" name = "to.salesExecutive" id="salesExecutive'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					]);
 
	rowCount++;
	serialNo++;
	//updateSrlNo('leadDetails');
}



/*function toggle(radBtn){
    switch(radBtn.id)
    {
    case 'statusId':
    	$("#status" ).disabled = false;
    	$("#designation").val("");
    	$("#designation" ).disabled = false;
    	$("#assignedTo").val("");
    	
    	document.getElementById("status").disabled = false;
    	document.getElementById("designation").value = "";
    	document.getElementById("designation").disabled = true;
    	document.getElementById("assignedTo").value = "";
    	break;
    case 'designationId':
    	
    	$("#designation" ).disabled = false;
    	$("#status").val("");
    	$("#status" ).disabled = false;
    	$("#assignedTo").val("");
    	
    	
    	document.getElementById("designation").disabled = false;
    	document.getElementById("status").value = "";
    	document.getElementById("status").disabled = true;
    	document.getElementById("assignedTo").value = "";
    	//document.getElementById("assignedTo").disabled = true;
    	break;
    case 'salesId':
    	document.getElementById("assignedTo").disabled = false;
    	document.getElementById("status").value = "";
    	document.getElementById("status").disabled = true;
    	document.getElementById("designation").value = "";
    	document.getElementById("designation").disabled = true;
    	break;
    	
    }
}*/
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

function getSalesExecutiveList(){
	$("#assignedTo").val("");
	//document.getElementById('assignedTo').value="";
	//var designation = document.getElementById('designation').value;
	var designation = $("#designation").val();
	if (isNull(designation)) {
		var content = document.getElementById('assignedTo');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
	} else {
		var url = './leadsView.do?submitName=getSalesExecutiveDetails&designation='
				+ designation;
		ajaxCallWithoutForm(url, getSalesExecutiveDetails);
	}
}

function getSalesExecutiveDetails(data){
	deleteAllRow();
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	$("#assignedTo" ).empty();
	var responseText =data; 
	if (!isNull(data)) {
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
	var content = document.getElementById('assignedTo');
	content.innerHTML = "";
	/*var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));*/
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.empUserId;
		option.appendChild(document.createTextNode(this.empTO.firstName + " " + this.empTO.lastName));
		content.appendChild(option);
	});
	}
}

function getLeadsByStatus(){
	//var status = document.getElementById("status").value;
	var status =$("#status").val();
	if(!isNull(status)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsByStatus&status='+ status;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getByStatus(req);
			}
		});
	}
}

var leadsList = null;
function getByStatus(ajaxResp){
//	var leadsList = null;
	if(!isNull(ajaxResp)){
		if(isJsonResponce(ajaxResp)){
			hideProcessing();
			return ;
		}
		leadsList = eval('(' + ajaxResp + ')');
	}
	
	if(leadsList.length == 0){
		deleteAllRow();
		alert("No Leads available for processing.");
	}
	
	if(!isNull(leadsList) && !isNull(leadsList.alertMsg)){
		alert(leadsList.alertMsg);	
	}else if(!isNull(leadsList) && isNull(leadsList.alertMsg)){
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		if (!isNull(leadsList.length))
			addRow();	
		for(var i =0 ;i<leadsList.length;i++){
			$("#leadNumber"+ (i+1)).val(leadsList[i].leadNumber);
			$("#customerName"+ (i+1)).val(leadsList[i].customerName);
			$("#contact"+ (i+1)).val(leadsList[i].contactPerson);
			$("#stdCode"+ (i+1)).val(leadsList[i].phoneNoSTD);
			$("#contactNumber"+ (i+1)).val(leadsList[i].phoneNo);
			$("#mobileNumber"+ (i+1)).val(leadsList[i].mobileNo);
			$("#email"+ (i+1)).val(leadsList[i].emailAddress);
			$("#status"+ (i+1)).val(leadsList[i].status.statusDescription);
			$("#salesExecutive"+ (i+1)).val(leadsList[i].assignedTo.empTO.firstName + ' ' + leadsList[i].assignedTo.empTO.lastName);

//			var leadUrl = '<a href="./leadValidation.do?submitName=preparePage&leadNumber='+leadsList[i].leadNumber + '&status='+leadsList[i].status.statusDescription + '">' + leadsList[i].leadNumber + '</a>';
			var newValue ='<a href="#" onclick="prepareLeadsPopUp('+i+');">' + leadsList[i].leadNumber + '</a>';
			
//			var newValue ='<a href="./leadValidation.do?submitName=preparePage&leadNumber='+leadsList[i].leadNumber + '&status='+leadsList[i].status.statusDescription + '">' + leadsList[i].leadNumber + '</a>';
			var temp = 1+i;
			var oTable = $('#leadDetails').dataTable();
			oTable.fnUpdate( newValue, i, 1);
			if(temp != leadsList.length)
				addRow();	
		}
	} 
	
	hideProcessing();
}

function prepareLeadsPopUp(index) {
	var leadNumber = leadsList[index].leadNumber;
	var statusDescription = leadsList[index].status.statusDescription;

	var leadUrl = './leadValidation.do?submitName=preparePage&leadNumber='+leadNumber + '&status='+statusDescription + '">' + leadNumber;

	popupWindow = window.open(leadUrl,'name','width=1050, height=1000');

	if(popupWindow && !popupWindow.closed) {	
		popupWindow.focus();
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

function getLeadsByUser(){
	var effectiveFrom = getDomElementById("effectiveFromStr").value;
	var effectiveTo = getDomElementById("effectiveToStr").value;
	var status = getDomElementById("status").value;

	var user =$("#userId").val();

	if(!isNull(user)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsByUser&user='+ user + '&effectiveFrom='+effectiveFrom + '&effectiveTo=' + effectiveTo + '&status=' + status;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getByStatus(req);
			}
		});
	}	
}

function getLeadsBySalesExecutive(){
	var salesExecutive =$("#assignedTo").val();
	//var salesExecutive = document.getElementById("assignedTo").value;
	if(!isNull(salesExecutive)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsBySalesExecutive&salesExecutive='+ salesExecutive;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getByStatus(req);
			}
		});
	}
	
}

function getLeadsByRegion(){
	var regionId =$("#regionId").val();
	
	var effectiveFrom = getDomElementById("effectiveFromStr").value;
	var effectiveTo = getDomElementById("effectiveToStr").value;
	var status = getDomElementById("status").value;
	
	if(!isNull(regionId)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsByRegion&regionId='+ regionId + '&effectiveFrom='+effectiveFrom + '&effectiveTo=' + effectiveTo + '&status=' + status;
		jQuery.ajax({
			url : url,
			success : function(req) {
				setTimeout(function(){getByStatus(req);},30);
			}
		});
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