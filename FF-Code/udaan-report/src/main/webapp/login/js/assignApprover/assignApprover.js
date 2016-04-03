var userId = null;
var userName = null;

function getUsers(userType) {
	document.getElementById("userName").value="";
	var url = "./assignApprover.do?submitName=getUsers&userType="+userType;
	jQuery.ajax({
		url : url,
		success : function(req) {
			displayUsers(req);
		}
	});
}

function displayUsers(obj){
	 userId = new Array();
	 userName = new Array();
	 if (obj != "" && obj != null) {
			var newStr=obj.replace("{","");
			var newStr1=newStr.replace("}","");
			var keyValList = newStr1.split(",");
			for(var i=0; i < keyValList.length; i++){
				var keyValPair = keyValList[i].split("=");
				userId[i] = keyValPair[0].trim();
				userName[i] = keyValPair[1];	
			}
		}
	 
	jQuery("#userName").autocomplete(userName);
}
function getUserDetails(){
	userName = getDomElementById("userName").value;
	if(!isNull(userName)){
	getDetails(userName, null);
	}else{
		alert('Please enter LoginID');
		userName.focus();
	}
}
function getDetails(userName, userId){
	
	disableFields();	
	if (!isNull(userName)){		
	  var url =
	  "./assignApprover.do?submitName=getDetails&userName="+userName+"&userId="+userId;
	  
	  ajaxCall(url, "assignApproverForm", populateDetails);
	} 
}
function populateDetails(data) {	
	if(!isNull(data)){
		if(data != "INACTIVE"){
		resp = 	jsonJqueryParser(data);	
		assignApproverTO = resp[0];
		cityTOList = resp[1];
		officeTOList = resp[2];
		regOfficeTOList = resp[3];
		
		if(!isNull(assignApproverTO) ){
		
				
		getDomElementById("empName").value = assignApproverTO.empFirstName+" "+assignApproverTO.empLastName;
		getDomElementById("empCode").value = assignApproverTO.empCode;
		getDomElementById("userId").value = assignApproverTO.userId;
		
		clearDropDown("officeIds");
		clearDropDown("cityIds");
		
		for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
			getDomElementById("regionalOfficeIds").options[i].selected = false;
		}	
		unSelectCheckBoxes();
		checkBoxDisabled(true);
		if(isNull(assignApproverTO.mappingTo)){
			if(isNull(officeTOList) && isNull(cityTOList) && isNull(regOfficeTOList)){
			getDomElementById("regionalOfficeIds").disabled = false;
			//getDomElementById("cityIds").disabled = false;
			//getDomElementById("officeIds").disabled = false;
			checkBoxDisabled(false);
			}
			if(!isNull(officeTOList)){
				officeDropDown(officeTOList);
			for(var j =0; j<assignApproverTO.officeAry.length;j++){
			for (var i = 0; i < getDomElementById("officeIds").options.length; i++) {
				if (getDomElementById("officeIds").options[i].value == assignApproverTO.officeAry[j]) {
					getDomElementById("officeIds").options[i].selected = true;
				}
				}
			}
			}
			
			
			if(!isNull(cityTOList)){
			addOptionTODropDown("cityIds", ALL, stationCode);
			for(var i=0;i<cityTOList.length;i++) {
				addOptionTODropDown("cityIds", cityTOList[i].cityName,cityTOList[i].cityId);
				getDomElementById("cityIds").options[i+1].selected = true;
			}
			}
			if(!isNull(regOfficeTOList)){
			for(var j = 0; j<regOfficeTOList.length;j++){
			for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
									
				if (getDomElementById("regionalOfficeIds").options[i].value == regOfficeTOList[j].officeId) {
					getDomElementById("regionalOfficeIds").options[i].selected = true;
				}
				}
			}
			}
			
		}else if(assignApproverTO.mappingTo == officeCode){
			
			dropDownValSelect("officeIds", officeCode);
			if(!isNull(cityTOList)){
				cityDropDown(cityTOList);
			for(var j =0; j<assignApproverTO.cityAry.length;j++) {
				for (var i = 0; i < getDomElementById("cityIds").options.length; i++) {
				if (getDomElementById("cityIds").options[i].value == assignApproverTO.cityAry[j]) {
					getDomElementById("cityIds").options[i].selected = true;
					break;
				}
				}
			}
			}if(!isNull(regOfficeTOList)){
				for(var j =0; j<regOfficeTOList.length;j++){
				for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
					if (getDomElementById("regionalOfficeIds").options[i].value == regOfficeTOList[j].officeId) {
					getDomElementById("regionalOfficeIds").options[i].selected = true;
				}
				}
			}
			}
		}else if(assignApproverTO.mappingTo == stationCode){
			dropDownValSelect("officeIds", officeCode);
			dropDownValSelect("cityIds", stationCode);
			if(!isNull(assignApproverTO.regOfficeAry)){
				for(var j =0; j<assignApproverTO.regOfficeAry.length;j++){
				for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
				if (getDomElementById("regionalOfficeIds").options[i].value == assignApproverTO.regOfficeAry[j]) {
					getDomElementById("regionalOfficeIds").options[i].selected = true;
				}
			}
			}
			}
		}else if(assignApproverTO.mappingTo == regionalOfficeCode){
			
			dropDownValSelect("officeIds", officeCode);
			
			dropDownValSelect("cityIds", stationCode);
			
			getDomElementById("regionalOfficeIds").options[0].selected = true;
		}
			
		if(!isNull(assignApproverTO.applScreenAry)){
			var checkBoxes = document.assignApproverForm.chkBox;
			for ( var k = 0; k < assignApproverTO.applScreenAry.length; k++) {
			for ( var j = 1; j <= checkBoxes.length; j++) {
				if(getDomElementById("chkBox"+j).value == assignApproverTO.applScreenAry[k]){
					getDomElementById("chkBox"+j).checked = true;
				}
			}
		}
		
		}
	
	}
	}else{
			alert("LoginID is Inactive.");	
			clearFields();
	}
	}else{
		alert("Invalid LoginID.");	
		clearFields();
	}
}

function clearFields(){
	getDomElementById("userName").value = "";
	getDomElementById("empName").value = "";
	getDomElementById("empCode").value = "";
	clearDropDown("officeIds");
	clearDropDown("cityIds");
	for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
		getDomElementById("regionalOfficeIds").options[i].selected = false;
	}
	unSelectCheckBoxes();
	checkBoxDisabled(true);
}

function dropDownValSelect(field, mapping){
	clearDropDown(field);
	//if(!isNull(list)){
	addOptionTODropDown(field, ALL ,mapping);
	/*for(var i=0;i<cityTOList.length;i++) {
		addOptionTODropDown("cityIds", cityTOList[i].cityName,cityTOList[i].cityId);
	}*/
	getDomElementById(field).options[0].selected = true;
	//}
}



function getStationsByRegionalOffices(regionalOfficeIds){
	clearDropDownList("officeIds");
	clearDropDownList("cityIds");
	var officeId = "";
	var all = false;
	for (var i = 0; i < regionalOfficeIds.options.length; i++) {
		
		if (regionalOfficeIds.options[i].selected) {
			rVal = regionalOfficeIds.options[i].value;
			if(rVal == regionalOfficeCode)
			{
			all = true;
			addAllToDropDown("cityIds", stationCode);
			addAllToDropDown("officeIds",officeCode);
			break;	
			}else{
			if(officeId.length>0)
			officeId=officeId+",";
			officeId = officeId + rVal;
			}
			
		} 
	}
	if(!all && officeId.length>0){
	
	  var url =
	  "./assignApprover.do?submitName=getStationsByRegionalOffices&regionalOffices="+officeId;
	  
	  ajaxCall(url, "assignApproverForm", populateStationDetails);
	}
}
function populateStationDetails(data) {
	
	station = "cityIds";
	stationList = 	jsonJqueryParser(data);
	getDomElementById("cityIds").disabled = false;		
	if(!isNull(stationList)){		
		cityDropDown(stationList);
	}
	else
		clearDropDownList(station);
}

function cityDropDown(stationList){
	station = "cityIds";
	clearDropDownList(station);
	
	if(!isNull(stationList)){
		addOptionTODropDown(station, ALL, stationCode);
	for(var i=0;i<stationList.length;i++) {			
		addOptionTODropDown(station, stationList[i].cityName,stationList[i].cityId);
	}
	}
}
function getOfficesByCity(citiIds){
	clearDropDownList("officeIds");
	var citiId = "";
	var all = false;
	for (var i = 0; i < citiIds.options.length; i++) {
		
		if (citiIds.options[i].selected) {
			
		rVal = citiIds.options[i].value;
			if(rVal == stationCode)
			{
			all = true;
			addAllToDropDown("officeIds", officeCode);
			break;	
			}else{
			if(citiId.length>0)
				citiId=citiId+",";
			
			citiId = citiId + rVal;
			}
		} 
	}
	if(!all && citiId.length>0){
	  var url =
	  "./assignApprover.do?submitName=getOfficesByCityList&cities="+citiId;
	  
	  ajaxCall(url, "assignApproverForm", populateOfficeDetails);
	}
}
function populateOfficeDetails(data) {
	getDomElementById("officeIds").disabled = false;
	office = "officeIds";
	officeList = jsonJqueryParser(data);
	if(!isNull(officeList)){
		officeDropDown(officeList);
	}
	else
		clearDropDownList(office);
}

function addAllToDropDown(field,mapping){
	clearDropDownList(field);
	addOptionTODropDown(field, ALL,mapping);
	getDomElementById(field).value = mapping;
	getDomElementById(field).disabled = true;
}


function officeDropDown(officeTOList){
	office = "officeIds";
	clearDropDownList(office);
	if(!isNull(officeTOList)){
	addOptionTODropDown(office, ALL, officeCode);
	
	for(var i=0;i<officeTOList.length;i++) {
		addOptionTODropDown(office, officeTOList[i].officeName,officeTOList[i].officeId);
	}
	}
}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;	
}

function addOptionTODropDown(selectId, label, value){
	
	var obj=getDomElementById(selectId);
		opt = document.createElement('OPTION');
		opt.value = value;
		opt.text = label;
		 obj.options.add(opt);
		
		 
	
}

function checkBoxData(){

	var screenId = "";
	var checkBoxes = document.assignApproverForm.chkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		if(getDomElementById("chkBox"+j).checked){
			if(screenId.length>0)
			screenId = screenId +",";
		screenId = screenId+getDomElementById("chkBox"+j).value;
		}
	}
	return screenId;
}

function  saveOrUpdateAssignApprover(){
	var officeId = "";
	var cityId = "";
	var regionalOfficeId = "";
	var all = false;
	if(checkFields()){
	for (var i = 0; i < getDomElementById("officeIds").options.length; i++) {
		
		if (getDomElementById("officeIds").options[i].selected) {
			rVal = getDomElementById("officeIds").options[i].value;
			if(rVal == officeCode)
			{
				 all = true;
			getDomElementById("officeIdsStr").value = rVal;		
			break;	
			}else{
			if(officeId.length>0)
			officeId=officeId+",";			
			officeId = officeId + rVal;
			}
		} 
	}
	if(!all && officeId.length>0){
		getDomElementById("officeIdsStr").value = officeId;
	}else {
		 all = false;
		for (var i = 0; i < getDomElementById("cityIds").options.length; i++) {
			if (getDomElementById("cityIds").options[i].selected) {
			rVal = getDomElementById("cityIds").options[i].value;
			if(rVal == stationCode)
			{
				 all = true;
			getDomElementById("cityIdsStr").value = rVal;		
			break;	
			}else{
			
				if(cityId.length>0)
					cityId=cityId+",";
				
				cityId = cityId + rVal;
			}
			} 
		}
	if(!all && cityId.length>0){
		getDomElementById("cityIdsStr").value = cityId;
	}
	else {
		 all = false;
		for (var i = 0; i < getDomElementById("regionalOfficeIds").options.length; i++) {
			
			if (getDomElementById("regionalOfficeIds").options[i].selected) {
				rVal = getDomElementById("regionalOfficeIds").options[i].value;
				if(rVal == regionalOfficeCode)
				{
				 all = true;
				getDomElementById("regionalOfficeIdsStr").value = rVal;		
				break;	
				}else{
				if(regionalOfficeId.length>0)
					regionalOfficeId=officeId+",";
				
				regionalOfficeId = regionalOfficeId + rVal;
				}
			} 
		}
	if(!all && regionalOfficeId.length>0){
		getDomElementById("regionalOfficeIdsStr").value = regionalOfficeId;
	}
	}
	}
	
	getDomElementById("screenIdsStr").value = checkBoxData();
	
	
	  var url =
	  "./assignApprover.do?submitName=saveOrUpdateAssignApprover";
	  
	  ajaxCall(url, "assignApproverForm", populateFormDetails);
	}
} 

   
function populateFormDetails(data){
	if (data == "SUCCESS") {
		userName = getDomElementById("userName").value;
		userId = getDomElementById("userId").value;
		if(!isNull(userName)){
		getDetails(userName,userId);
		}
		alert("Rights assigned successfully.");
	} else if (data == "FAILURE") {		
		alert("Exception occurred. Rights not assigned.");
	}
	
}

function modifyAssignApprover(){
	if(checkFields()){
	enableFields();
	}
}

function enableFields(){
	getDomElementById("regionalOfficeIds").disabled = false;
	getDomElementById("cityIds").disabled = false;
	getDomElementById("officeIds").disabled = false;

	checkBoxDisabled(false);
}
function disableFields(){

		getDomElementById("regionalOfficeIds").disabled = true;
		getDomElementById("cityIds").disabled = true;
		getDomElementById("officeIds").disabled = true;
		checkBoxDisabled(true);
}

function checkBoxDisabled(check){
	var checkBoxes = document.assignApproverForm.chkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		getDomElementById("chkBox"+j).disabled = check;
	}
}

function checkFields(){
	var userName = getDomElementById("userName");
	var regionalOfficeIds = getDomElementById("regionalOfficeIds");
	var cityIds = getDomElementById("cityIds");
	var officeIds = getDomElementById("officeIds");
	
	if (isNull(userName.value)) {
		alert('Please Enter LoginID');
		userName.focus();
		return false;
	}
	
	if (isNull(regionalOfficeIds.value)) {
		alert('Please select Regional Office');
		regionalOfficeIds.focus();
		return false;
	}
	if (isNull(cityIds.value)) {
		alert('Please select Station');
		cityIds.focus();
		return false;
	}
	if (isNull(officeIds.value)) {
		alert('Please select Office');
		officeIds.focus();
		return false;
	}
	
	if(!checkBoxSelected()){
		alert('Please check at least one Screen');
		return false;
	}
	
	return true;

}

function checkBoxSelected(){
	var checkBoxes = document.assignApproverForm.chkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		if(getDomElementById("chkBox"+j).checked)
			return true;
	}
	return false;
}
function unSelectCheckBoxes(){
	var checkBoxes = document.assignApproverForm.chkBox;
	for ( var j = 1; j <= checkBoxes.length; j++) {
		getDomElementById("chkBox"+j).checked = false;		
	}
}