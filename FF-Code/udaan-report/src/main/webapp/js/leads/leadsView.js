// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
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

function loadDefaultObjects() {
	addRow();
	if(userRole == userSaleRole){
		getLeadsByUser();
	}else{
		getLeadsByRegion();
	}
		
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
					 
					 '<input type="text" name = "to.phoneNo" id="contactNumber'+ rowCount+ '" class="txtbox width115"  readonly="true"  />',
					 
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
	}
	else if($('#designationId').attr('checked')){
		$("#designation").attr("disabled", false);
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
	var url = './leadsView.do?submitName=getSalesExecutiveDetails&designation='
		+ designation;
	ajaxCallWithoutForm(url, getSalesExecutiveDetails);
}

function getSalesExecutiveDetails(data){
	var content = document.getElementById('assignedTo');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.empUserId;
		option.appendChild(document.createTextNode(this.empTO.firstName + " " + this.empTO.lastName));
		content.appendChild(option);
	});
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

function getByStatus(ajaxResp){
	var leadsList = null;
	if(!isNull(ajaxResp)){
		leadsList = eval('(' + ajaxResp + ')');
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
			$("#contactNumber"+ (i+1)).val(leadsList[i].phoneNo);
			$("#mobileNumber"+ (i+1)).val(leadsList[i].mobileNo);
			$("#email"+ (i+1)).val(leadsList[i].emailAddress);
			$("#status"+ (i+1)).val(leadsList[i].status.statusDescription);
			$("#salesExecutive"+ (i+1)).val(leadsList[i].assignedTo.empTO.firstName + ' ' + leadsList[i].assignedTo.empTO.lastName);
			var newValue ='<a href="./leadValidation.do?submitName=preparePage&leadNumber='+leadsList[i].leadNumber + '&status='+leadsList[i].status.statusDescription + '">' + leadsList[i].leadNumber + '</a>';
			var temp = 1+i;
			var oTable = $('#leadDetails').dataTable();
			oTable.fnUpdate( newValue, i, 1);
			if(temp != leadsList.length)
				addRow();	
		}
	} 
	
	hideProcessing();
}


function deleteAllRow() {
	var table = getDomElementById("leadDetails");
	var tableRowCount = table.rows.length;
	var oTable = $('#leadDetails').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
		
	}

}

/*function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
}
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}*/

function getLeadsByUser(){
	var user =$("#userId").val();
	//var user = document.getElementById("userId").value;
	if(!isNull(user)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsByUser&user='+ user;
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
	//var regionId = document.getElementById("regionId").value;
	if(!isNull(regionId)) {
		showProcessing();
		var url = './leadsView.do?submitName=getLeadsByRegion&regionId='+ regionId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getByStatus(req);
			}
		});
	}	
}


	
