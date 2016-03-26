var cityList="";
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";




rowCount = 1;
function addRow(record) {
	

	serialNo = 1;
	var ofcName = "";
	var ofcCode = "";
	if(!isNull(record.salesOfcCode) && (record.salesOfcCode == 'RO')){
		ofcCode = record.salesOfcCode;
		ofcName = record.salesOfcName;
	}else{
		ofcCode = record.rhoOfcCode;
		ofcName = record.regionalName;
	}
		
		$('#example')
				.dataTable()
				.fnAddData(
						[
						
						 	rowCount,
						 	'<span id="createdDate">'+record.quotationDate+'</span>',
						 	'<span id="contractNo'+rowCount+'"><a href="#" onclick="return openContract('+"'"
						 	+record.contractNo+"'"+','+"'"
						 	+record.rateContractType+"'"+','
						 	+record.userEmpId+','
						 	+record.salesUserEmpId+','
						 	+record.quotationCreatedBy+','
						 	+record.contractCreatedBy+','+"'"
						 	+ofcName+"'"+','+"'"+record.cityName+"'"+','
						 	+"'"+record.salesOfficeName+"'"+','+"'"
						 	+record.salesPersonName+"'"+');">'+record.contractNo+'</a></span>',
						 	'<span id="regionName">'+ofcName+'</span>',
						 	'<span id="stationName">'+record.cityName+'</span>',
						 	'<span id="customerName">'+record.customerName+'</span>',
						 	'<span id="salesOffcName">'+record.salesOfficeName+'</span>',
						 	'<span id="salesPerson">'+record.salesPersonName+'</span>',
						 	'<span id="salesPerson">'+record.groupKey+'</span>',
						 	'<span id="status">'+record.status+'</span>'
								
							]);
		

		rowCount++;
}



function getContracts(){
	
	//if(!isNull()){
	deleteTableRow();
	var regionId = "";
	var cityId = "";
	var userId = "";
	var contractNo = "";
	var cityVal="";
	
	userId = getDomElementById("userId").value;
	
	contractNo = getDomElementById("rateContractNo").value;
	
	if(isNull(contractNo)){
		
		if(!isNull(getDomElementById("regionDropId"))){
			regionId = getDomElementById("regionDropId").value;
		if(regionId == 'A'){
			cityVal = 'A';
			regionId = getList("regionDropId");
		}}else
		regionId = getDomElementById("regionOfcId").value;
		
		if(!isNull(getDomElementById("cityRegDropId"))){
			if(cityVal == 'A')
				cityId = null;
			else{
				cityId = getDomElementById("cityRegDropId").value;
			if(cityId == 'A')
				cityId = getList("cityRegDropId");
			//userId = null;
			}
		}/*else if(!isNull(getDomElementById("cityDropId"))){
				cityId = getDomElementById("cityDropId").value;
				if(cityId == 'A')
					cityId = getList("cityDropId");
		}*/else{		
			cityId = getDomElementById("cityId").value;	
		}
	}else{
		if(!isNull(getDomElementById("regionDropId"))){
			regionId = getList("regionDropId");
		}else
			regionId = getDomElementById("regionOfcId").value;
			
		if(!isNull(getDomElementById("cityRegDropId"))){
			//cityId = getList("cityRegDropId");
			cityId = null;
			
		}else{		
			cityId = getDomElementById("cityId").value;	
		}
		
	}
	
	if(validateDates()){
		var url = "./rateContract.do?submitName=getRateContractListViewDetails&region="+regionId+"&city="+cityId+"&fromDate="+getDomElementById("fromDate").value+"&toDate="+getDomElementById("toDate").value+"&status="+getDomElementById("status").value+"&userId="+userId+"&contractNo="+contractNo+"&type="+getDomElementById("type").value+"&ofcType="+getDomElementById("officeType").value;
		ajaxCallWithoutForm(url, showDetails);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function showDetails(data){
	deleteTableRow();
	if(!isNull(data))
		
		for(var i=0;i<data.length;i++){
			addRow(data[i]);
	}
	/*else{
		deleteTableRow();
			
	}*/
}

function deleteRow(tableId, rowIndex) {
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}


function deleteTableRow() {

	try {
	var table = getDomElementById("example");
	var tableRowCount = table.rows.length;
	
	for ( var i = 0; i < tableRowCount; i++) {
			deleteRow("example", i);
			tableRowCount--;
			i--;
		}
	}catch(e){
	alert(e);
	}
		rowCount = 1;

}


function getStationsByRegion(val){
	
	if(!isNull(val)){
		if(val == "A"){
			clearDropDownList("cityRegDropId");
			addOptionTODropDown("cityRegDropId","ALL","A");	
		}else{
		var url = "./rateQuotation.do?submitName=getStationsByRegion&region="+val;
		ajaxCallWithoutForm(url, populateStations);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
		}
	}else{
		clearDropDownList("cityRegDropId");
		addOptionTODropDown("cityRegDropId","ALL","A");	
	}
}

function populateStations(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
		} else {
			var data = ajaxResp;
			clearDropDownList("cityRegDropId");
			addOptionTODropDown("cityRegDropId", "ALL", "A");
			for ( var i = 0; i < data.length; i++) {
				addOptionTODropDown("cityRegDropId", data[i].cityName,
						data[i].cityId);
			}
			getDomElementById("cityRegDropId").value = "A";
		}
	}
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
}


function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}


function getList(fieldId){
	var list = "";
		var optionsList = getDomElementById(fieldId).options;
		for ( var i = 1; i < optionsList.length; i++) {
			opt = document.createElement('OPTION');
			if(optionsList[i].value == "A")
				continue;
			else{
				if(list.length >0)
				list = list +",";
				list = list + optionsList[i].value;
			}
		}
		return list;
}

function openContract(contractNo,contractType,userEmpId,salesUserEmpId,quotUserId,userId,regionalName,cityName,salesOfcName,salesPerson){
	var url = "./rateContract.do?submitName=listViewRateContract&rateContractNo="+contractNo+"&type="+contractType+
	"&userEmpId="
	+userEmpId+"&salesUserEmpId="+salesUserEmpId+"&quotUserId="+quotUserId+"&userId="+userId+"&regionalName="+regionalName+"&cityName="
	+cityName+"&salesOfcName="+salesOfcName+"&salesPerson="+salesPerson+"&page=listView";
	window.open(
			url,'popUpWindow','height=600,width=1100,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');

	return;
}


function validateFromDate(obj){
	if(!isNull(obj.value)){
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);
		var fromDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
		if(fromDate > toDayDate){
			alert("From Date should not be greater than today date");
			obj.value="";
			return false;
		}
		return true;
	}
}

function validateToDate(obj){
	var validfromDate = document.getElementById("fromDate").value;
	if(!isNull(obj.value) && !isNull(validfromDate)){
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);
		
		var validfromDateDay = parseInt(validfromDate.substring(0, 2), 10);
		var validfromDateMon = parseInt(validfromDate.substring(3, 5), 10);
		var validfromDateYr = parseInt(validfromDate.substring(6, 10), 10);
		
		var fromDate = new Date(validfromDateYr, (validfromDateMon - 1), validfromDateDay);
		var toDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
		
		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
		
		if(toDate > toDayDate){
			alert("To Date should not be greater than today date");
			obj.value="";
			return false;
		}else if(toDate < fromDate){
			alert("To Date should be greater than From date");
			obj.value="";
			return false;
		}
		
	}else if(isNull(validfromDate)){
		alert("Please select From date");
		obj.value="";
		return false;
	}
	
	
	return true;
}

function validateDates(){
	var validFromDate = document.getElementById("fromDate").value;
	var validToDate = document.getElementById("toDate").value;
	
	if(isNull(validFromDate)){
		alert("Please select From date");
		return false;
	}else if(isNull(validToDate)){
		alert("Please select To date");
		return false;
	}else if(!isNull(validFromDate) && !isNull(validToDate)){
		if(!validateToDate(document.getElementById("toDate"))){
			return false;
		}
	}
	return true;
}