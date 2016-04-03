var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
function isValidJson(json) {
	try {
		JSON.parse(json);
		return true;
	} catch (e) {
		return false;
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
		if(isValidJson(ObjeResp)){
			responseText = jsonJqueryParser(ObjeResp);
		}
		else{
			var error =ObjeResp[ERROR_FLAG];
			if(!isNull(error)){
				alert(error);
				return true;
			}
		}
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

function billPrintingStartup(){
	/*var region=document.getElementById("regionList");*/
	var region=$("#regionList" ).val();
	if((OFFICE_TYPE==BR_OFFICE && BR_USER_REGION==region) ||(OFFICE_TYPE==HUB_OFFICE && HUB_USER_REGION==region)){


		$('#regionList').attr('disabled', 'disabled');

	}
	else{
		$('#regionList').attr('disabled', 'disabled');
		document.getElementById("stationList").focus();
	}
	if((OFFICE_TYPE==BR_OFFICE) ){
		$('#stationList').attr('disabled', 'disabled'); 
		$('#branchList').attr('disabled', 'disabled'); 
		/*buttonDisabled("getCustomerBtn","btnintform");*/
		/*jQuery("#" + "getCustomerBtn").addClass("btnintformbigdis");
		$('#customerList').attr('disabled', 'disabled'); */
		document.getElementById("productTo").focus();
	}if((OFFICE_TYPE==HUB_OFFICE)){
		$('#stationList').attr('disabled', 'disabled'); 
		// $('#branchList').attr('disabled', 'disabled'); 
		/*buttonDisabled("getCustomerBtn","btnintform");*/
		/*jQuery("#" + "getCustomerBtn").addClass("btnintformbigdis");
		$('#customerList').attr('disabled', 'disabled'); */
		document.getElementById("productTo").focus();
	}

}
function getStationsList(){

	/*document.getElementById('stationList').value="";
	var region = document.getElementById('regionList').value;*/
	$("#stationList" ).empty();//need to checked ok
	var region=$("#regionList" ).val();
	var url = './billPrinting.do?submitName=getStations&region='
		+ region;
	ajaxCallWithoutForm(url, getStationList);


}

function getStationList(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}

		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	}
}


function getBranchesList(){

	/*document.getElementById('branchList').value="";*/
	/*var station = document.getElementById('stationList').value;*/
	var station=$("#stationList" ).val();
	var url = './billPrinting.do?submitName=getBranches&station='
		+ station;
	ajaxCallWithoutForm(url, getBranchList);
}

function getBranchList(data){
	branchList = "branchList";
	officeList = data;
	var responseText =data; 
	var error = responseText[ERROR_FLAG];
	//var success = responseText[SUCCESS_FLAG];
	if(responseText!=null && error!=null){
		alert(error);
		return ;
	}

	if(officeList!=null){
		officeDropDown(officeList);
	}
	else{
		/*clearDropDownList(branchList);*/	
		$("#branchList" ).empty();
	}
}


function officeDropDown(officeTOList){
	office = "branchList";
	/*clearDropDownList(office);*/
	$("#branchList" ).empty();
	if(!isNull(officeTOList)){
		//addOptionTODropDown(office, ALL, officeCode);
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

/*var selBranchVal="";*/
function getCustomerByOffice(){
	showProcessing();
	var selBranchVal="";
	var countCheck=0;
	var branchList=getDomElementById("branchList");
	//var branchList=$('#branchList').val();
	var productId=$("#productTo" ).val();
	var station=$("#stationList" ).val();
	if(station==""){
		alert('Select Mandatory Field Station');
		document.getElementById("stationList").focus();
		hideProcessing();
	}
	else if(productId==""){

		alert('Select Mandatory Field product');
		document.getElementById("productTo").focus();
		hideProcessing();
	}
	else{
		if(OFFICE_TYPE==BR_OFFICE ){
			if(branchList.length!=0){ 
				//selBranchVal=branchList[0].value;
				selBranchVal=$('#branchList').val();
				var url = './billPrinting.do?submitName=getCustomersByBranch&BranchIds='
					+ selBranchVal + "&FinancialPId=" + productId;
				//ajaxCallWithoutForm(url, getCustList);

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
		/*else if(OFFICE_TYPE == 'HO'){
			for (var i=0; i<branchList.length; i++) {
				selBranchVal+=branchList[i].value+",";
				countCheck=countCheck+1;
			}
			if(countCheck!=0){

				//showProcessing();
				var url = './billPrinting.do?submitName=getCustomersByBranch&BranchIds='
					+ selBranchVal + "&FinancialPId=" + productId;
				//ajaxCallWithoutForm(url, getCustList);

				jQuery.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					success : function(data) {
						getCustList(data);
					}
				});

		}else {
				alert('Select Atleast One Branch');
				hideProcessing();
		}
		}*/
		else{
			if(branchList.length!=0){ 
				for (var i=0; i<branchList.length; i++) {
					if (branchList[i].selected) {
						selBranchVal+=branchList[i].value+",";
						countCheck=countCheck+1;
					}
				}
				if(countCheck!=0){

					//showProcessing();
					var url = './billPrinting.do?submitName=getCustomersByBranch&BranchIds='
						+ selBranchVal + "&FinancialPId=" + productId;
					//ajaxCallWithoutForm(url, getCustList);

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
					alert('Select Atleast One Branch');
					hideProcessing();
				}
			}

			else{
				alert('No Branch Available');
				hideProcessing();
			}
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
	customer = "customerList";
	listCust="custList";
	/*clearDropDownList(customer);
	clearDropDownList(listCust);*/

	$("#customerList" ).empty();
	$("#custList" ).empty();
	if(!isNull(custList)){
		var All="All";
		customerList = "customerList";
		addAllTODropDown(customerList,All);
		//addOptionTODropDown(office, ALL, officeCode);
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
}

function getCustomer1(){
	customList="custList";
	var customerList=getDomElementById("customerList");
	var custList=getDomElementById("custList");
	var custList1=$("#customerList" ).val();
	/*var customerList=$("#customerList" ).val();
	var custList=$("#custList" ).val();*/
	var count1=0;
	var selectedCount=0;
	//clearDropDownList(custList);
	if(customerList.length!=0){ 
		/*for (var i=0; i<customerList.length; i++) {
			  if (customerList[i].selected) {
				     if(custList.length==null || custList.length==0){
				    	 addCustomer(customList, customerList[i].text,customerList[i].value);
				     } 
				     else{
				    	 for(var j=0;j<custList.length; j++){
					    	 if(customerList[i].text==custList[j].text){
					    		 alert('Customer already in list');

					    	 }
					    	 else{
					    		  count=count+1;
					    	 }
				    	 }
				    	 if(count==custList.length){
				    		 count=0;
				    		 addCustomer(customList, customerList[i].text,customerList[i].value); 
				    	 }
				     	}

				    	}
					}*/

		for (var i = customerList.length - 1; i>=0; i--) {
			if (customerList.options[i].selected) {
				addCustomer(customList, customerList[i].text,customerList[i].value);
				customerList.remove(i);
				count1=count1+1;
			}
		}
		/*var custOptions = custList.options;
		       custOptions[0].selected = true;*/
		/* if(count1>=2){
			   		disabledbills();
			   	  }
			   	   else{
			   		enabledbills();
			      }*/
		
		var no1=custList.length;
		if(no1==1 && custList1 != 'All' ){
			enabledbills();
		}
		else{
			disabledbills();
		}

	}
	else{
		alert('No data available');
	}

	/*if(selectedCount>=2){
	        	   disabledbills();
	           }
	           else if(selectedCount==1 ){
	        	   enabledbills();
	           }*/

}

function disabledbills(){

	/*document.getElementById("bills").disabled = true;*/
	$('#bills').attr('disabled', 'disabled'); 
	/*jQuery("#" + "getBillsBtn").removeClass("btnintform");btnintformbigdis*/

	buttonDisabled("getBillsBtn","btnintform");
	jQuery("#" + "getBillsBtn").addClass("btnintform");

	/*jQuery("#" + "getBillsBtn").attr("disabled", true);
	jQuery("#" + "getBillsBtn").removeClass("btnintform");
	jQuery("#" + "getBillsBtn").removeAttr("tabindex");
	jQuery("#" + "getBillsBtn").addClass("btnintformbigdis");*/
}

function enabledbills(){

	/*	jQuery("#" + "getBillsBtn").addClass("btnintform");*/
	/*getDomElementById("bills").disabled=false;*/
	/*$('#bills').attr('enabled', 'enabled'); */

	$("#bills").removeAttr('disabled');
	buttonEnabled("getBillsBtn","btnintformbigdis");
	jQuery("#" + "getBillsBtn").addClass("btnintform");

	/*jQuery("#"+ "getBillsBtn").attr("disabled", false);
	jQuery("#"+ "getBillsBtn").removeClass("btnintformbigdis");
	jQuery("#" + "getBillsBtn").removeAttr("tabindex");
	jQuery("#" + "getBillsBtn").addClass("btnintform");*/
}
function addCustomer(selectId,label,value){
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

function removeCustomers(){
	customList="custList";
	customerList="customerList";
	var custList=getDomElementById("custList");
	var custList1=$("#customerList" ).val();
	/*var custList=$("#custList" ).val();*/
	if(custList.length!=0){ 
		/*for (var i=0; i<custList.length; i++) {
		  if (custList[i].selected) {
			  custList.remove(i);
		  }
		}*/
		/*for (var i = custList.length - 1; i>=0; i--) {
		    if (custList.options[i].selected) {
		    	custList.remove(i);
		    }
		  }*/
		for (var i = custList.length - 1; i>=0; i--) {
			if (custList.options[i].selected) {
				addCustomer(customerList, custList[i].text,custList[i].value);
				custList.remove(i);
			}
		}

		var no=custList.length;
		if(no==1 && custList1 !='All'){
			enabledbills();
		}
		else{
			disabledbills();
		}
	}
	else{
		alert('No data available');
	}
}


function getcount(){
	var custList=getDomElementById("custList");
	/*var custList=$("#custList" ).val();*/
	var count=0;
	if(custList.length!=0){ 
		for (var i=0; i<custList.length; i++) {
			if (custList[i].selected) {
				count=count+1;
			}
		}
	}
	else{
		alert('No data available');
	}

	if(count>=2){
		disabledbills();
	}
	else{
		enabledbills();
	}
}


function getCustomerBills(){
	showProcessing();
	bills="bills";
	
	
	/*clearDropDownList(bills);*/
	$("#bills" ).empty();
	var custId="";
	var billingOffs="";
	var custList=getDomElementById("custList");
	/*var custList=$("#custList" ).val();*/
	var count=0;

	var productId=$("#productTo" ).val();
	var productInfo=productId.split(","); 
	if(productId==""){

		alert('Select Mandatory Field product');
		hideProcessing();
	}
	else{
		if(custList.length!=0){ 

			for (var i=0; i<custList.length; i++) {
				/*if (custList[i].selected) {*/
				custId=custList[i].value;
				count=count+1;
				/*}*/
			}
			if(count!=0){
				var startDate=$("#startDate" ).val();
				var endDate=$("#endDate" ).val();
				if(startDate=="" || endDate==""){
					alert('Select start date and end date');
					hideProcessing();
				}
				else{
					/*var arrStartDate = startDate.value.split("/");
		var date1 = new Date(arrStartDate[2], arrStartDate[1], arrStartDate[0]);
		var arrEndDate = endDate.value.split("/");
		var date2 = new Date(arrEndDate[2], arrEndDate[1], arrEndDate[0]);*/
					/*var date1 = new Date(startDate.value);
		var date2 = new Date(endDate.value);*/
					if(checkDate()){
						var startDate=$("#startDate" ).val();
						var endDate=$("#endDate" ).val();
						if(OFFICE_TYPE==BR_OFFICE){
							//var branchList=getDomElementById("branchList");
							var branchList=$('#branchList').val();
							var url = './billPrinting.do?submitName=getBills&CustomerId='
								+ custId + "&StartDate=" + startDate + "&EndDate=" + endDate  + "&ProductID="+productId + "&BillingOffs="+
								//branchList[0].value;
								branchList;
							// ajaxCallWithoutForm(url, getBillsList);

							jQuery.ajax({
								url : url,
								type : "GET",
								dataType : "json",
								success : function(data) {
									getBillsList(data);
								}
							});

						}
						else{
							$("#branchList option:selected").each(function () {
								if(isNull(billingOffs)){
									billingOffs += $(this).val();
									//count=count+1;
								} else {
									billingOffs+= "," + $(this).val(); 
								}

							});

							var url = './billPrinting.do?submitName=getBills&CustomerId='
								+ custId + "&StartDate=" + startDate + "&EndDate=" + endDate  + "&ProductID="+productId + "&BillingOffs="+billingOffs;
							//ajaxCallWithoutForm(url, getBillsList);
							jQuery.ajax({
								url : url,
								type : "GET",
								dataType : "json",
								success : function(data) {
									getBillsList(data);
								}
							});
						}
					}
				}	
			}else{
				alert('SELECT ATLEAST ONE CUSTOMER');
				hideProcessing();
			}
		}	
		else{
			alert('No Customer Available');
			hideProcessing();
		}
	}
}
function getBillsList(data){
	billList = "bills";

	billsList = data;//jsonJqueryParser(data);
	var responseText =data; 
	var error = responseText[ERROR_FLAG];
	//var success = responseText[SUCCESS_FLAG];
	if(responseText!=null && error!=null){
		alert(error);
		hideProcessing();
		return ;
	}
	else if(billsList!=null ){
		billsDropDown(billsList);
	}
	else{
		/*clearDropDownList(billList);*/

		$("#bills").empty();
	}

	hideProcessing();
}

function billsDropDown(billsList){
	bills = "bills";
	var ALL="ALL";
	/*clearDropDownList(bills);*/
	$("#bills").empty();
	if(!isNull(billsList)){
		//addOptionTODropDown(office, ALL, officeCode);
		addAllTODropDown(bills, ALL);
		for(var i=0;i<billsList.length;i++) {
			addBillTODropDown(bills, billsList[i].invoiceNumber);
		}
	}
}

function addAllTODropDown(selectId,label){
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.text = label;
	obj.options.add(opt);
}

function addBillTODropDown(selectId,label){
	var obj=getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.text = label;
	obj.options.add(opt);
}

function printBill(){
	
	
	
	var custId="";
	var custList=getDomElementById("custList");
	/*var billList=getDomElementById("bills");*/
	/*var custList1=$("#custList" ).val();*/
	var billList=$("#bills" ).val();
	var invoiceNo="";
	var count=0;
	var customerId="";
	var customerIds = new Array();

	$("#custList option").each(function () {
		if(isNull(customerId)){
			customerId += $(this).val();
			customerIds[count] = customerId;
		}});
		if(customerIds != 'All'){
			
	var billingOffs="";
	//var counter=0;
	//var invoiceNos="";
	var productId=$("#productTo" ).val();
	var region=$("#regionList" ).val();
	var station=$("#stationList" ).val();

	if(isNull(region)|| isNull(productId)||isNull(station)){

		alert('Select Mandatory Field ');
		if(isNull(region)){
			$("#regionList").focus();
		}
		else if(isNull(productId)){
			$("#productTo").focus();
		}
		else if(isNull(station)){
			$("#stationList").focus();
		}
	}
	else{
		/*if(document.getElementById("bills").disabled==true){*/
		if($('#bills').is(':disabled') == true){

			if(custList.length!=0){
				if(custList.length <=10){
					/*for (var i=0; i<custList.length; i++) {
			  if (custList[i].selected) {
				  custId+=custList[i].value+",";
				  count=count+1;
			  }
			}*/

					$("#custList option").each(function () {
						if(isNull(customerId)){
							customerId += $(this).val();
							customerIds[count] = customerId;
						} else {
							customerId+= "," + $(this).val(); 
							customerIds[count] = customerId;
						}
						count=count+1;
					});

					if(count!=0){
						/*	for (var i=0; i<custId.length; i++) {
			if(i!=custId.length-1){

				customerId+=custId[i];
			}
		}*/

						var startDate=$("#startDate" ).val();
						var endDate=$("#endDate" ).val();
						if(startDate=="" || endDate==""){
							alert('Select start date and end date');
						}
						else{
							/*var arrStartDate = startDate.value.split("/");
		var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
		var arrEndDate = endDate.value.split("/");
		var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);*/

							if(checkDate()){
								var detail=null;
								var productId=$("#productTo" ).val();
								var productInfo=productId.split(","); 
								if(isDetailsChecked()){
									detail='Y';
								}
								else{
									detail='N';
								}
								$("#branchList option:selected").each(function () {
									if(isNull(billingOffs)){
										billingOffs += $(this).val();
									} else {
										billingOffs+= "," + $(this).val(); 
									}

								});

								/*For Server Deployment*/
								var url = 'pages/reportviewer/salesInvoiceSDSReport.jsp?'
									/*For Local Deployment*/
									/*var url = 'http://localhost:8080/udaan-report/pages/reportviewer/salesInvoiceSDSReport.jsp?'*/
									+ "&Customers="+customerIds
									+ "&StartDate="+startDate 
									+ "&EndDate="+endDate
									+ "&ProductID="+productId
									+ "&BillingOffs="+billingOffs
									+ "&ProductSeries="+null
									+ "&Details="+detail;
								//alert(url);
								window.open(url, "_blank");

							} 	
							/* document.billPrintingForm.action = "/udaan-config-admin/billPrinting.do?submitName=printBills&CustomerId="
				+ custId + "&StartDate=" + startDate.value + "&EndDate=" + endDate.value;*/
							/*document.billPrintingForm.action=url;
		 document.billPrintingForm.submit();*/
						}
					}
					else{
						alert('select Atleast one Customer');
					}}else{alert('More than 10 Customers are not allowed');}
			}

			else{
				alert('No Customer Available');
			}
		}

		/*if(document.getElementById("bills").disabled==false){*/
		if($('#bills').is(':disabled') == false){
			if(!isNull(billList)){
				if($("#bills" ).val()=="ALL"){
					/*for(var i=1;i<billList.length;i++) {

				//invoiceNo+=billList[i].text()+",";
				invoiceNo+=$("#bills" +i).text()+",";
			}*/
					var j=0;
					$('#bills option').each(function(){

						//here this can be refered as an option.. like
						if($(this).text()!="ALL"){
							//invoiceNo+=this.label+",";
							if(isNull(invoiceNo)){
								invoiceNo+= $(this).text();	
							} else {
								invoiceNo+= "," + $(this).text(); 
							}

						}
						//your code here

					});
				}
				else{
					/*invoiceNo=document.getElementById("bills").value;*/
					invoiceNo=$("#bills" ).val();
				}



				/*for (var i=0; i<invoiceNo.length; i++) {
			if(i==invoiceNo.length-1 && invoiceNo[i]==',' ){

				invoiceNos+=invoiceNo[i];
			}
			else{
				invoiceNos+=invoiceNo[i];
			}
		}*/
				/*var startDate=document.getElementById("startDate");
		var endDate=document.getElementById("endDate");
		var arrStartDate = startDate.value.split("/");
		var date1 = new Date(arrStartDate[2], arrStartDate[1], arrStartDate[0]);
		var arrEndDate = endDate.value.split("/");
		var date2 = new Date(arrEndDate[2], arrEndDate[1], arrEndDate[0]);
		if(date1>date2 ){
			alert('End Date Cant Be Less');
		}else{*/
				if(checkDate()){
					var startDate=$("#startDate" ).val();
					var endDate=$("#endDate" ).val();
					var productId=$("#productTo" ).val();
					//var productInfo=productId.split(","); 
					if(isDetailsChecked()){
						detail='Y';
					}
					else{
						detail='N';
					}

					$("#branchList option:selected").each(function () {
						if(isNull(billingOffs)){
							billingOffs += $(this).val();
						} else {
							billingOffs+= "," + $(this).val(); 
						}

					});

					/*For Server Deployment*/
					var url ='pages/reportviewer/salesInvoiceSDSReport.jsp?'
						/*For Local Deployment*/
						/*var url = 'http://localhost:8080/udaan-report/pages/reportviewer/salesInvoiceSDSReport.jsp?'*/
						+ "&InvoiceNumbers="+invoiceNo 
						+ "&StartDate="+startDate
						+ "&EndDate="+endDate
						+ "&ProductID="+productId
						+ "&BillingOffs="+billingOffs
						+ "&ProductSeries="+null
						+ "&Details="+detail;
					//alert(url);
					window.open(url, "_blank");
				}	
			}
			else{
				alert('No Bills Available');
			}
		}
	}
	}else{
		alert("Please select BulkPrint option");
		
	}
	
	}

function checkMandatory(){
	var productId=$("#productTo" ).val();
	if(productId=="" || productId=='select'){

		alert('Select Mandatory Field product');
	}

}

function checkDate(){
	var startDate=$("#startDate" ).val();
	var endDate=$("#endDate" ).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);
	var currentdate = new Date();
	if(date1 >currentdate){
		alert("Start Date Can't be Greater that Current date");

		$("#startDate" ).val("");

		$("#startDate" ).focus();
		return false;
	}
	if(date1=="" || date1>date2){

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate" ).val("");

		$("#startDate" ).focus();

		return false;

	}
	else {
		var currentdate = new Date();
		if(date2 >currentdate ){
			alert("End Date Can't be Greater that Current date");
			$("#endDate" ).val("");
			$("#endDate" ).focus();
			return false;
		}
		return true;
	}


}
/*function showReport() {
    window.open("<%= request.getContextPath( ) + "/salesInvoiceSDSReport.jsp?Customers="+custId%>", "_blank");
}*/
function clearScreen(action){
	if(confirm("Do you want to clear the page?")){
		$("#startDate" ).val("");
		$("#endDate" ).val("");
		var url = "./billPrinting.do?submitName=preparePage";
		document.billPrintingForm.action=url;
		document.billPrintingForm.submit();


	}
}

function clearFields(){
	customer = "customerList";
	customList="custList";
	branchList="branchList";
	/*clearDropDownList(customer);
	clearDropDownList(customList);
	clearDropDownList(branchList);*/
	clearDropDownList(branchList);

	$("#customerList").empty();
	$("#custList").empty();
	$("#branchList").empty();
}

function clearItem(){

	//$("#startDate").val("");
	//$("#endDate").val("");
	$("#bills").empty();
}

function isDetailsChecked(){
	var addChkBox = getDomElementById("Details");
	if (addChkBox.checked) {
		return true;
	} else {
		return false;
	}
}

function clearFieldsOnProduct(){
	$("#customerList").empty();
	$("#custList").empty();
	$("#startDate").val("");
	$("#endDate").val("");
	$("#bills").empty();	

}


function printBillsInBulkUsingBirtViewer() {
	var custList=getDomElementById("custList");
	var billList=$("#bills" ).val();
	var invoiceNo="";
	var count=0;
	var customerId="";
	var billingOffs="";
	var productId=$("#productTo" ).val();
	var region=$("#regionList" ).val();
	var station=$("#stationList" ).val();

	if(isNull(region)|| isNull(productId)||isNull(station)){
		alert('Select Mandatory Field ');
		if(isNull(region)){
			$("#regionList").focus();
		}
		else if(isNull(productId)){
			$("#productTo").focus();
		}
		else if(isNull(station)){
			$("#stationList").focus();
		}
	}
	else{
		if($('#bills').is(':disabled') == true){
			if(custList.length!=0){
				$("#custList option").each(function () {
					if(isNull(customerId)){
						customerId += $(this).val();
						//customerIds[count] = customerId;
					} else {
						customerId+= "," + $(this).val(); 
						//customerIds[count] = customerId;
					}
					count=count+1;
				});

				if(count!=0){
					var startDate=$("#startDate" ).val();
					var endDate=$("#endDate" ).val();
					if(startDate=="" || endDate==""){
						alert('Select start date and end date');
					}
					else{
						if(checkDate()){
							var detail=null;
							var productId=$("#productTo" ).val();
							if(isDetailsChecked()){
								detail='Y';
							}
							else{
								detail='N';
							}
							$("#branchList option:selected").each(function () {
								if(isNull(billingOffs)){
									billingOffs += $(this).val();
								} else {
									billingOffs+= "," + $(this).val(); 
								}
							});

							/*For Server Deployment*/
							var url = 'pages/reportviewer/salesInvoiceBulkPrintingSDSReport.jsp?'
								/*For Local Deployment*/
								/*var url = 'http://localhost:8080/udaan-report/pages/reportviewer/salesInvoiceSDSReport.jsp?'*/
								+ "&Customers="+customerId
								+ "&StartDate="+startDate 
								+ "&EndDate="+endDate
								+ "&ProductID="+productId
								+ "&BillingOffs="+billingOffs
								+ "&ProductSeries="+null
								+ "&Details="+detail;
							//alert(url);
							window.open(url, "_blank");
						} 	
					}
				}
				else{
					alert('select Atleast one Customer');
				}
			}
			else{
				alert('No Customer Available');
			}
		}

		if($('#bills').is(':disabled') == false){
			if(!isNull(billList)){
				if($("#bills" ).val()=="ALL"){
					$('#bills option').each(function(){
						//here this can be refered as an option.. like
						if($(this).text()!="ALL"){
							//invoiceNo+=this.label+",";
							if(isNull(invoiceNo)){
								invoiceNo+= $(this).text();	
							} else {
								invoiceNo+= "," + $(this).text(); 
							}
						}
						//your code here
					});
				}
				else{
					/*invoiceNo=document.getElementById("bills").value;*/
					invoiceNo=$("#bills" ).val();
				}
				if(checkDate()){
					var startDate=$("#startDate" ).val();
					var endDate=$("#endDate" ).val();
					var productId=$("#productTo" ).val();
					//var productInfo=productId.split(","); 
					if(isDetailsChecked()){
						detail='Y';
					}
					else{
						detail='N';
					}

					$("#branchList option:selected").each(function () {
						if(isNull(billingOffs)){
							billingOffs += $(this).val();
						} else {
							billingOffs+= "," + $(this).val(); 
						}
					});

					/*For Server Deployment*/
					var url ='pages/reportviewer/salesInvoiceBulkPrintingSDSReport.jsp?'
						/*For Local Deployment*/
						/*var url = 'http://localhost:8080/udaan-report/pages/reportviewer/salesInvoiceSDSReport.jsp?'*/
						+ "&InvoiceNumbers="+invoiceNo 
						+ "&StartDate="+startDate
						+ "&EndDate="+endDate
						+ "&ProductID="+productId
						+ "&BillingOffs="+billingOffs
						+ "&ProductSeries="+null
						+ "&Details="+detail;
					//alert(url);
					window.open(url, "_blank");
				}	
			}
			else{
				alert('No Bills Available');
			}
		}
	}
}
