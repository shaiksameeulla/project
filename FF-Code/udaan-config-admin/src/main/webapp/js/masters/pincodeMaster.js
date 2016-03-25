var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var STATUS;
var cityNameAfterSearch;
var cityIdAfterSearch;

function getStateList() {
	var region = document.getElementById("regionList");
	
	 var regionName = region.options[region.selectedIndex].innerHTML;
	 
	if(regionName!="Select"){
	var regionId = document.getElementById("regionList").value;
	if (!isNull(regionId)) {
		showProcessing();
		url = './pincodeMaster.do?submitName=getStatesByRegion&regionId='
				+ regionId;
		ajaxCallWithoutForm(url, getStatesList);
	}
}else if(regionName=="Select"){
	clearDropDownList("stateList");
	clearDropDownList("cityList");
}
}
function getStatesList(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("regionList", "");
			alert(error);
			document.getElementById('regionList').focus();

		} else {
			var content = document.getElementById('stateList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.stateId;
				option.appendChild(document.createTextNode(this.stateName));
				content.appendChild(option);
			});

		}

	}
}
function getCityList() {
	var stateId = document.getElementById("stateList").value;
	if (!isNull(stateId)) {
		showProcessing();
		url = './pincodeMaster.do?submitName=getCitysByState&stateId='
				+ stateId;
		ajaxCallWithoutForm(url, getCitysList);

	}
}

function getCitysList(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			showDropDownBySelected("stateList", "");
			alert(error);
			document.getElementById('stateList').focus();
		} else {
			var content = document.getElementById('cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});
			
			//to populate for multiselect for grup 3
			/*var content = document.getElementById('group3cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});*/
			
			
			//to populate for multiselect for grup 5
			/*var content = document.getElementById('group5cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});*/
			

		}

	}else{
		clearDropDownList("cityList");
	}

}

function getBranchesByCity() {
	var regionId = document.getElementById("regionList").value;
	if (!isNull(regionId)) {
		showProcessing();

		url = './pincodeMaster.do?submitName=getBranchesByCity&cityId='
				+ regionId;
		ajaxCallWithoutForm(url, populateBranches);
		//document.PinCodeMasterForm.action=url;
		//document.PinCodeMasterForm.submit();
	}
}

function populateBranches(ajaxResp) {
	hideProcessing();
	if (!isNull(ajaxResp)) {
		branch = "branchlist";
		branchList = ajaxResp;
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			clearDropDownList(branch);
		} else {
			/*clearDropDownList(branch);
			for ( var i = 0; i < branchList.length; i++) {
				addOptionTODropDown(branch, branchList[i].officeCode,
						branchList[i].officeId);
			}*/

			
			var content = document.getElementById('branchlist');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(ajaxResp, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeCode+"-"+this.officeName));
				content.appendChild(option);
			});
			
			
		}

	}else{
		clearDropDownList("branchlist");
		alert('No branches mapped for the selected city');
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

function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}

function disableEnable(domElement){
	
	
	if(document.getElementById("group3").checked==true){
		
		getDomElementById("group3A").disabled=false;
		getDomElementById("group3B").disabled=false;
		getDomElementById("group3C").disabled=false;
				
		
	}else{
		getDomElementById("group3A").disabled=true;
		getDomElementById("group3B").disabled=true;
		getDomElementById("group3C").disabled=true;
		
		getDomElementById("group3A").checked=false;
		getDomElementById("group3B").checked=false;
		getDomElementById("group3C").checked=false;
	}
	
}

function savePincodeMaster(){
	
	if (validatePincodeDetails()) {
		var url = './pincodeMaster.do?submitName=savePincodeMaster';
	//	ajaxCall(url, "PinCodeMasterForm", callBackSavePincodeMaster);
		progressBar();
		document.PinCodeMasterForm.action=url;
		document.PinCodeMasterForm.submit();
	}
}

function searchPincode(){
	
	showProcessing();
	var pincodeNO = document.getElementById("pincodeNo").value;
	
	if(pincodeNO==""){
		alert("Please enter the valid pincode");
		return false;
	}else if(!validatePincodeField()){
		return false;
	}else if(pincodeNO.length<6){
		alert("Pincode Length should be 0f 6 characters");
		document.getElementById("pincodeNo").value="";
		return false;
	}else{
		var url = './pincodeMaster.do?submitName=searchPincode&pincodeNO='+pincodeNO;
		ajaxCallWithoutForm(url, populatePincodeDetails);
	}
	
}

function populatePincodeDetails(pincodeDetails){
	
	if(!isNull(pincodeDetails)){
		jQuery("#pincodeNo").attr('readonly', true);
		//populate the region id
		document.getElementById("regionList").value = pincodeDetails.regionId;
		document.getElementById("pincodeId").value = pincodeDetails.pincodeId;
		
		 
		//drop down for states to show all valuues
		 var content = document.getElementById('stateList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(pincodeDetails.stateList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.stateId;
				option.appendChild(document.createTextNode(this.stateName));
				content.appendChild(option);
			});
			
			//populate the selected state
			document.getElementById("stateList").value = pincodeDetails.stateId;
			
			
			
			//dropdown for cities to show all values
			var content = document.getElementById('cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(pincodeDetails.cityList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});
			
			/*//dropdown for grup3 cities to show all values
			var content = document.getElementById('group3cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(pincodeDetails.cityList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});*/
			
			/*//dropdown for grup5 cities to show all values
			var content = document.getElementById('group5cityList');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(pincodeDetails.cityList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			});*/
			
			
			//populate only the selected city
			document.getElementById("cityList").value = pincodeDetails.cityId;
			 cityIdAfterSearch=pincodeDetails.cityId;
			
			var city = document.getElementById("cityList");
		
			 cityNameAfterSearch = city.options[city.selectedIndex].innerHTML;
			
			// Populate Location
			 document.getElementById("location").value = pincodeDetails.location;
			
			
			//populate all the serviceable branches by city
			var content = document.getElementById('branchlist');
			content.innerHTML = "";
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			$.each(pincodeDetails.serviceableBranchList, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeCode+"-"+this.officeName));
				content.appendChild(option);
			});
			
			//getBranchesByCity();
			
			//to show the selected serviceable branches
			for(var j =0; j<pincodeDetails.servicablebranch.length;j++){
				for (var i = 0; i < getDomElementById("branchlist").options.length; i++) {
				if (getDomElementById("branchlist").options[i].value == pincodeDetails.servicablebranch[j]) {
					getDomElementById("branchlist").options[i].selected = true;
				}
				}
			}
			
			//to show the selected papaer work
			if(!isNull(pincodeDetails.paperWorkIds)){
			
			for(var j =0; j<pincodeDetails.paperWorkIds.length;j++){
				for (var i = 0; i < getDomElementById("paperworklist").options.length; i++) {
				if (getDomElementById("paperworklist").options[i].value == pincodeDetails.paperWorkIds[j]) {
					getDomElementById("paperworklist").options[i].selected = true;
				}
				}
			}
			}
			
			
			//to show the checked product grup
			
				for(var i=0;i<pincodeDetails.groupIds.length;i++){
					if(!isNull(pincodeDetails.groupIds[i])){
						document.getElementById("group"+pincodeDetails.groupIds[i]).checked=true;
					}
					
				}
				
				if(document.getElementById("group3").checked==true){
					getDomElementById("group3A").disabled=false;
					getDomElementById("group3B").disabled=false;
					getDomElementById("group3C").disabled=false;
					
					//populate the values for grup3
					
					if(!isNull(pincodeDetails.group3Ids)){
						for(var i=0;i<pincodeDetails.group3Ids.length;i++){
							if(!isNull(pincodeDetails.group3Ids[i]) && pincodeDetails.group3Ids[i]=="B"){
								getDomElementById("group3A").checked=true;
							}
							if(!isNull(pincodeDetails.group3Ids[i]) && pincodeDetails.group3Ids[i]=="A"){
								getDomElementById("group3B").checked=true;
							}
							if(!isNull(pincodeDetails.group3Ids[i])&&pincodeDetails.group3Ids[i]=="S"){
								getDomElementById("group3C").checked=true;
							}
						}
					}
					
					//populate grup 3 city list
					for(var a =0; a<pincodeDetails.group3cityList.length;a++){
						
						for (var i = 0; i < getDomElementById("group3cityList").options.length; i++) {
							if(!isNull(pincodeDetails.group3cityList[a])){
								if (getDomElementById("group3cityList").options[i].value == pincodeDetails.group3cityList[a]) {
									getDomElementById("group3cityList").options[i].selected = true;
									break;
								}
							}
						
						}
					}
				}
				
				
				if(document.getElementById("group5").checked==true){
					//populate grup5 city list
					for(var a =0; a<pincodeDetails.group5cityList.length;a++){
						
						for (var i = 0; i < getDomElementById("group5cityList").options.length; i++) {
							if(!isNull(pincodeDetails.group5cityList[a])){
								if (getDomElementById("group5cityList").options[i].value == pincodeDetails.group5cityList[a]) {
									getDomElementById("group5cityList").options[i].selected = true;
									break;
								}
							}
						
						}
					}
				}
				
				if(pincodeDetails.status=="I"){
					//disable deactivate
					jQuery("#deactivateBtn").attr("disabled", "disabled");
					jQuery("#deactivateBtn").removeClass("btnintform");
					jQuery("#deactivateBtn").addClass("btnintformbigdis");
					//enable activate
					jQuery("#activateBtn").attr("disabled", false);
					jQuery("#activateBtn").removeClass("btnintformbigdis");
					jQuery("#activateBtn").addClass("btnintform");
					
				}else if(pincodeDetails.status=="A"){
					//disable activate
					jQuery("#activateBtn").attr("disabled", "disabled");
					jQuery("#activateBtn").removeClass("btnintform");
					jQuery("#activateBtn").addClass("btnintformbigdis");
					
					
					//enable deactivate
					jQuery("#deactivateBtn").attr("disabled", false);
					jQuery("#deactivateBtn").removeClass("btnintformbigdis");
					jQuery("#deactivateBtn").addClass("btnintform");
				}
	}/*if(pincodeDetails.servicablebranch.length==0){
		alert('No Result Found');
		jQuery("#pincodeNo").attr('readonly', false);
	}*/
	hideProcessing();
}
	
function validatePincodeDetails(){
		
	var pincodeNo = document.getElementById("pincodeNo").value;
	
	var region = document.getElementById("regionList").value;
	var state = document.getElementById("stateList").value;
	var city = document.getElementById("cityList").value;
	var location = document.getElementById("location").value;
	var branchOffice = document.getElementById("branchlist").value;
	var paperWorkList = document.getElementById("paperworklist").value;
	
	
	var group3cityList = document.getElementById("group3cityList").value;
	
	
	var group5cityList = document.getElementById("group5cityList").value;
	
		var group1 = document.getElementById("group1");
		var group2 = document.getElementById("group2");
		var group3 = document.getElementById("group3");
		var group4 = document.getElementById("group4");
		var group5 = document.getElementById("group5");
	
	if (isNull(pincodeNo)) {
		alert("Please Enter Pincode no");
		jquery("#pincodeNo").focus();
		return false;
	} else if (isNull(region)) {
		alert("Please select a region");
		return false;
	} else if (isNull(state)) {
		alert("Please select a state");
		return false;
	} else if (isNull(city)) {
		alert("Please select a city");
		return false;
	} else if (isNull(location)) {
		alert("Please provide a location");
		return false;
	}else if(isNull(branchOffice)){ 
		var branch = document.getElementById("branchlist");
		
		if(branch.selectedIndex!=-1){
			 var branchName = branch.options[branch.selectedIndex].innerHTML;
			 
			 if(branchName=="Select"){
				 alert('You have selected the SELECT option for  serviceable branches');
					return false;
			 }else{
				 alert('Please select at least one serviceable branch');
					return false;
			 }
		}else{
			 alert('Please select at least one serviceable branch');
			return false;
		}
		
		
	}/*else if(paperWorkList==""){ 
		
		var paperWork = document.getElementById("paperworklist");
		
		if(paperWork.selectedIndex!=-1){
			var  paperWorkName = paperWork.options[paperWork.selectedIndex].innerHTML;
		
		
		
		 if(paperWorkName=="Select"){
			 alert('You have selected the SELECT option for PaperWork');
				return false;
		 }
		}
		
	}*/
	else if(!group1.checked && !group2.checked && !group3.checked && !group4.checked && !group5.checked){
		alert('Please select at least one product group');
		return false;
	}else if(group3.checked){
		var group3A = document.getElementById("group3A");
		var group3B = document.getElementById("group3B");
		var group3C = document.getElementById("group3C");
		
		if(!group3A.checked && !group3B.checked && !group3C.checked){
			alert('Select at least one group under Priority');
			return false;
		}
		
		if(isNull(group3cityList)){
			var grup3city = document.getElementById("group3cityList");
		
			if(grup3city.selectedIndex!=-1){
			 var cityNameForGrup3 = grup3city.options[grup3city.selectedIndex].innerHTML;
			 
			 if(cityNameForGrup3=="Select"){
				 alert('You have selected the SELECT option for group 3 cities');
					return false;
			 }else{
				 alert('Please select at least one city for priority group');
					return false;
			 }
			}else{
				alert("Please select at least one city for priority group");
				return false;
			}
		
			
		}
		
		
	}else if(!group3.checked && !isNull(group3cityList) ){
		alert('Please select Group 3 product ');
		return false;
	}
	/*else if(group5.checked){
		
		if(isNull(group5cityList)){
			var grup5city = document.getElementById("group5cityList");
				 var cityNameForGrup5 = grup5city.options[grup5city.selectedIndex].innerHTML;
				 if(cityNameForGrup5=="Select"){
					 alert("You have selected the SELECT option for group 5 cities");
						return false;
				 }else{
					 alert('Please select at least one city for S group');
					 return false;
				 }
			
		}
		
	}*/else if(!group5.checked && !isNull(group5cityList)){
		alert('Please select Group 5 product ');
		return false;
	}
	
	
	if(group5.checked){
		
		if(isNull(group5cityList)){
			var grup5city = document.getElementById("group5cityList");
			if(grup5city.selectedIndex!=-1){
				 var cityNameForGrup5 = grup5city.options[grup5city.selectedIndex].innerHTML;
				 if(cityNameForGrup5=="Select"){
					 alert("You have selected the SELECT option for group 5 cities");
						return false;
				 }else{
					 alert('Please select at least one city for S group');
					 return false;
				 }
			}else{
				alert('Please select at least one city for S group');
				 return false;
			}
			
		}
		
	}
	
if(paperWorkList==""){ 
		
		var paperWork = document.getElementById("paperworklist");
		
		if(paperWork.selectedIndex!=-1){
			var  paperWorkName = paperWork.options[paperWork.selectedIndex].innerHTML;
		
		
		
		 if(paperWorkName=="Select"){
			 alert('You have selected the SELECT option for PaperWork');
				return false;
		 }
		}
		
	}
	
	return true;
}
	


function validatePincodeField(){
	var pincodeValue = document.getElementById("pincodeNo").value;
	var pincodeid = document.getElementById("pincodeId").value;
	
	
	var objRegExp = /(^-?\d\d*$)/;
	
	if(objRegExp.test(pincodeValue) && !isNull(pincodeValue)){
		return true;
	}else{
		alert("Enter a valid pincode");
		document.getElementById("pincodeNo").value="";
		//document.getElementById("pincodeNo").focus();
	}
	return false;
}

function enterkeyForPincode(e){
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) {
    	if(validatePincodeField()){
    		//document.getElementById("Find").click();
    		searchPincode();
    	}else{
    		
    	}
    }
}

function cancel(){
	var confirm1 = confirm("Do you wish to cancel");
	if (confirm1) {
		var url = './pincodeMaster.do?submitName=preparePage';
		document.getElementById("pincodeNo").value="";
			document.PinCodeMasterForm.action=url;
			document.PinCodeMasterForm.submit();
	}
	
}

function activateDeactivatePincode(status){
	
	var pincodeValue = document.getElementById("pincodeNo").value;
	if(!isNull(pincodeValue)){
		
	
	STATUS=status;
	
	var url = './pincodeMaster.do?submitName=activateDeactivatePincode&pincodeNO='+pincodeValue+'&status='+status;
	
	ajaxCallWithoutForm(url, callBackActDeact);	
}else{
	alert('Please enter the pincode no');
}
}

function callBackActDeact(ajaxResp){
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		var success = ajaxResp[SUCCESS_FLAG];
		if (ajaxResp != null && error != null) {
			alert(error);
			
		} else if(ajaxResp != null && success != null){
			
			alert(success);
			if(STATUS=="A"){
				//disable activate
				jQuery("#activateBtn").attr("disabled", "disabled");
				jQuery("#activateBtn").removeClass("btnintform");
				jQuery("#activateBtn").addClass("btnintformbigdis");
				
				//enable deactivate
				jQuery("#deactivateBtn").attr("disabled", false);
				jQuery("#deactivateBtn").removeClass("btnintformbigdis");
				jQuery("#deactivateBtn").addClass("btnintform");
				
				
			}else if(STATUS=="I"){
				//disable deactivate
				jQuery("#deactivateBtn").attr("disabled", "disabled");
				jQuery("#deactivateBtn").removeClass("btnintform");
				jQuery("#deactivateBtn").addClass("btnintformbigdis");
				
				//enable activate
				jQuery("#activateBtn").attr("disabled", false);
				jQuery("#activateBtn").removeClass("btnintformbigdis");
				jQuery("#activateBtn").addClass("btnintform");
			}
		}
	}
}


function confirmTheCitySelected(){
	//var city = document.getElementById("cityList");
	var pincodeid = document.getElementById("pincodeId").value;
	//var cityName = city.options[city.selectedIndex].innerHTML;
	
	//if(!isNull(pincodeid)){
		if(!isNull(cityNameAfterSearch)){
			if(cityNameAfterSearch=="Select"){
				getBranchesByCity();
			}else{
			var confirm2 = confirm("City "+cityNameAfterSearch+" is already assigned to this pincode. \n Do you wish to proceed to change the city");
			
			if(confirm2){
				getBranchesByCity();
			}else{
				//keep the old city
				
				var city = document.getElementById("cityList");
				document.getElementById("cityList").value=cityIdAfterSearch;
				
				 //city.options[city.selectedIndex].innerHTML = cityNameAfterSearch;
				
			}
			}
		}
		
	//}
		else{
		getBranchesByCity();
	}
	
}

function checkOnBlurIfPincodeAlreadyexists(){
	
	var pincodeid = document.getElementById("pincodeId").value;
	if(!isNull(pincodeid)){
		alert("Pincode No already exists");
	}
}


function checkOnlyAlphabet(event){
	
	if(onlyAlphabet(event)){
		alert('Characters not allowed');
		document.getElementById("pincodeNo").value="";
	}
	
}

function enterKeyNavigationForPincode(event) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
    if (key == 13) {
    	if (validatePincodeField()){
    		document.getElementById("Find").focus();
    		return true;
    	}
    	return false;
    }
}


