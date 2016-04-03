/**
 * 
 */

function getProductByCustomersBD(){
	var customers = $("#customerList").val();
	if (isNull(customers)) {
		alert("Please select Customer");
	} else {
		var customerIds = $("#customerList option:selected").map(function(){ return this.value;}).get().join(", ");
		if(customerIds.toUpperCase() == 'ALL') {
			customerIds = -1;
		}
		showProcessing();
		var url = './rateRevisionAnalysisReport.do?submitName=getProductByCustomers&customerIds='
			+ customerIds;
		jQuery.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			success : function(data) {
				
				//jQuery.unblockUI();
				populateProductsBD(data);
			}
		});
	}
	
}

function populateProductsBD(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('product');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("ALL"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.productId != null) {
				var option;
				option = document.createElement("option");
				option.value = this.productId;
				option.appendChild(document.createTextNode(this.consgSeries));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('product');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		alert("Product not mapped for selected customer(s)");
		$("#client").focus();
	}
	hideProcessing();

}

function getConsignmentBookingClientList(id, typeId) {
	getDomElementById("client").options.length = 1;
	var branch = readBranch();
	var cityId = readStation();
	var typeId = readCustomerType();
	if ($("#branchList").val().toUpperCase() != "SELECT") {
		var url = './consignmentBookingDetailsReport.do?submitName=getCustomersByContractBranches&officeIds='
				+ branch + '&cityId=' + cityId + '&typeId=' + typeId;
		ajaxCallWithoutForm(url, populateClientList1);
	}
}

/**
 * @param data
 */
function populateClientList1(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "All";
		defOption.appendChild(document.createTextNode("All"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			if (this.businessName != null
					&& $.trim(this.businessName).length > 0) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName
						+ "-" + this.customerCode));

				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
	}
	hideProcessing();
}
function triggeringBranchCustomers(){
	var officeType = $("#officeType").val();
	var branchOfficeType = $("#branchOfficeType").val();
	var hubOfficeType = $("#hubOfficeType").val();
	
	/*if(!isNull(officeType) && officeType==branchOfficeType && officeType==hubOfficeType){
		//trigger branch customer
	
		$("#branchList").trigger("change");
	}else{
		//alert("event not triggered");
	}*/
	if(officeType == "HO" ||officeType == "BO" ){
		//trigger branch customer
	
		$("#branchList").trigger("change");
	}else{
		//alert("event not triggered");
	}
}

function getCustomerByOffice(){
	//showProcessing();
	var selBranchVal="";
	var offs = "";
	var products = "";
	//var countCheck=0;
	var branchList=$("#branchList" ).val();
	
	var priority=$("#priority" ).val();
	
	/*var branchList=$('#branchList').val();*/
	var productId=$("#customerType" ).val();
	if(productId=="" || productId=='Select'){

		//alert('Select Mandatory Field product');
		document.getElementById("productTo").focus();
		hideProcessing();
	}else if(productId =='All'){
		var customer = document.getElementById("customerType").value;
		var options = document.getElementById("customerType").options;
		if (!isNull(customer)) {
			for ( var i = 0; i < options.length; i++) {
				if (!isNull(options[i].value)) {
					if (options[i].value != 'All'
							&& options[i].value != 'Select') {
						if (isNull(products)) {
							products += options[i].value;
						} else {
							products += "," + options[i].value;
						}
					}
				}
			}
		}
	}else {	
		products =productId;
			}
	
	
	
	var station=$("#stationList" ).val();
	if(branchList == 'Select'){
		//alert('Select Mandatory Field Branch');
		document.getElementById("branchList").focus();
		hideProcessing();
		clearScreen();
		//clearDropDownList("#customerType");
	}else if(station == "" || station == 'Select'){
		alert('Select Mandatory Field Station');
	}else if(station==""){
		alert('Select Mandatory Field Station');
		document.getElementById("stationList").focus();
		hideProcessing();
	}else if(productId==""){

		alert('Select Mandatory Field product');
		document.getElementById("productTo").focus();
		hideProcessing();
	}else {
		if(branchList =='All'){
			var customer = document.getElementById("branchList").value;
			var options = document.getElementById("branchList").options;
			if (!isNull(customer)) {
				for ( var i = 0; i < options.length; i++) {
					if (!isNull(options[i].value)) {
						if (options[i].value != 'All'
								&& options[i].value != 'Select') {
							if (isNull(offs)) {
								offs += options[i].value;
							} else {
								offs += "," + options[i].value;
							}
						}
					}
				}
			}
		}else {	
					offs =branchList;
				}
			
			if(branchList.length!=0){ 
				selBranchVal=offs;
				showProcessing();
				var url = './billPrinting.do?submitName=getCustomersByBranchForReport&BranchIds='
					+ selBranchVal + "&FinancialPId=" + products;
				//ajaxCallWithoutForm(url, populateClientList1);
				
				jQuery.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					success : function(data) {
						getCustList(data);
					}
				});

			}	
			else{
				alert('No Branch Available');
				hideProcessing();
			}
		}
		
	
	//alert('check');
	//hideProcessing();
}

function getCustList(data){
	
	customerList = "customerList";
	customList="custList";
	custList = data;
	var responseText =data; 
	var error = responseText[ERROR_FLAG];
	//var success = responseText[SUCCESS_FLAG];
	if(responseText!=null && error!=null){
		$("#customerList" ).empty();
		$("#custList" ).empty();
		$("#startDate" ).val("");
		$("#endDate" ).val("");
		alert(error);
		hideProcessing();
		return ;
	}

	if(custList!=null ){
		customerDropDown(custList);
	}
	else{
		/*clearDropDownList(customerList);
		clearDropDownList(customList);*/

		$("#customerList" ).empty();
		$("#custList" ).empty();
	}

	hideProcessing();
}
function customerDropDown(custList){
	//showProcessing();
	customer = "customerList";
	listCust="custList";
	var All= "All";
	
	/*clearDropDownList(customer);
	clearDropDownList(listCust);*/

	$("#customerList" ).empty();
	$("#custList" ).empty();
	if(!isNull(custList)){
		//addOptionTODropDown(office, ALL, officeCode);
		addAllTODropDown(customer, All);
		for(var i=0;i<custList.length;i++) {
			addCustomerTODropDown(customer, custList[i].businessName,custList[i].customerId,custList[i].customerCode,custList[i].shippedToCode);
		}
	}
}

function addCustomerTODropDown(selectId,label1,value,label2,label3){
	
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	
	//alert(label3);
	if (isNull(label3)){
		opt.text = label1 + '-' + label2;
	} else {
		//opt.text = label2 + '-' + label1 + '-' + label3 ;	
		opt.text =  label1 + '-' + label2 ;
	}
	obj.options.add(opt);
	hideProcessing();
}

function clearScreen(action){
	
		$("#customerType" ).val("");
}
function enable(){
	var productId=$("#customerType" ).val();
	if(productId == 6){
		document.getElementById("Priority").disabled=false;
	}else{
		var priorityDOM=document.getElementById("Priority");
		priorityDOM.setAttribute("disabled",true);
	}
}
function addAllTODropDown(selectId,label){
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.text = label;
	obj.options.add(opt);
}
