var ERROR_FLAG="ERROR";

function trackmultiple(){	
	var typeDom = document.getElementById("typeName");
	var numberDom = document.getElementById("consgRefNo");
	var text = (numberDom.value).replace(/\n\r?/g, ',');
	var number = trimString(text);
	var type = typeDom.value;
	
	if (isNull(type)) {
		alert('Please select type');
		typeDom.focus();
		return;
	}
	if (isNull(number)) {
		alert('Please enter Consignment / Reference Number');
		numberDom.focus();
		return;
	} else {
		getDomElementById("trackingText").value = number;
		showProcessing();
		var url="./multipleTracking.do?submitName=getMultipleConsgDetails";
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#multipleTrackingForm").serialize(),
			success : function(req) {
				populateConsignment(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert("Server error : " + errorThrown);
			}
		});
	}
}

function addRows(rowcount){
	$('#example').dataTable().fnAddData(
	[
	 '<div id="consgNum' + rowcount + '"></div>',
	 '<div id="refNum' + rowcount + '"></div>',
	 '<div id="bookingDate' + rowcount + '"></div>',
	 '<div id="origin' + rowcount + '"></div>',
	 '<div id="destination' + rowcount + '"></div>',
	 '<div id="weight' + rowcount + '"></div>',	 
	 '<div id="status' + rowcount + '"></div>',
	 
	 '<div id="drsNo' + rowcount + '"></div>',
	 '<div id="delvDate' + rowcount + '"></div>',
	 '<div id="dlvBranchName' + rowcount + '"></div>',	 
	 '<div id="rcvName' + rowcount + '"></div>',
	 '<div id="thirdPartyName' + rowcount + '"></div>',
	 '<div id="pendingReason' + rowcount + '"></div>',
	 
	 '<div id="ogmNum' + rowcount + '"></div>',	 
	 '<div id="ogmDate' + rowcount + '"></div>',
	 '<div id="bplNum' + rowcount + '"></div>',
	 '<div id="bplDate' + rowcount + '"></div>',
	 
	 '<div id="cdNum' + rowcount + '"></div>',
	 '<div id="cdDate' + rowcount + '"></div>',
	 '<div id="flightNum' + rowcount + '"></div>',
	 '<div id="flightDept' + rowcount + '"></div>',
	 '<div id="flightArrvl' + rowcount + '"></div>',
	 '<div id="rcvDate' + rowcount + '"></div>',
	 '<div id="manifestDate' + rowcount + '"></div>',
	]);	
}

function populateConsignment(data){
	if(!isNull(data)){
		var inValidNumbers = "";
		var typeDom = getDomElementById("typeName");
		var consgRefNoObj = document.getElementById("consgRefNo");
		data = jsonJqueryParser(data);
		if(!isNull(data[ERROR_FLAG])){
			typeDom.value = "CN";
			clearFocusAlertMsg(consgRefNoObj,data[ERROR_FLAG]);
			hideProcessing();
			return;
		}else{
			consgRefNoObj.disabled = true;;
			typeDom.disabled = true;
			
			$.each(data, function(index, value) {
				addRows(index);
				getDomElementById("consgNum" + index).innerHTML = this.consgNum;
				getDomElementById("refNum" + index).innerHTML = this.refNum;
				getDomElementById("bookingDate" + index).innerHTML = this.bookingDate;
				getDomElementById("origin" + index).innerHTML = this.origin;
				getDomElementById("destination" + index).innerHTML = this.destination;
				getDomElementById("weight" + index).innerHTML = this.weight;
				getDomElementById("status" + index).innerHTML = this.status;
				
				/*getDomElementById("status" + index).innerHTML = this.status;				
				getDomElementById("delvDate" + index).innerHTML = this.delvDate;
				getDomElementById("rcvName" + index).innerHTML = this.rcvrName;
				getDomElementById("weight" + index).innerHTML = this.weight;*/

				getDomElementById("drsNo" + index).innerHTML = this.drsNo;
				getDomElementById("delvDate" + index).innerHTML = this.delvDate;
				getDomElementById("dlvBranchName" + index).innerHTML = this.dlvBranchName;
				getDomElementById("rcvName" + index).innerHTML = this.rcvrName;
				getDomElementById("thirdPartyName" + index).innerHTML = this.thirdPartyName;
				getDomElementById("pendingReason" + index).innerHTML = this.pendingReason;
				
				getDomElementById("ogmNum" + index).innerHTML = this.ogmNum;				
				getDomElementById("ogmDate" + index).innerHTML = this.ogmDate;
				getDomElementById("bplNum" + index).innerHTML = this.bplNum;
				getDomElementById("bplDate" + index).innerHTML = this.bplDate;
				
				getDomElementById("cdNum" + index).innerHTML = this.cdNum;
				getDomElementById("cdDate" + index).innerHTML = this.cdDate;
				getDomElementById("flightNum" + index).innerHTML = this.flightNum;
				getDomElementById("flightDept" + index).innerHTML = this.flightDept;
				getDomElementById("flightArrvl" + index).innerHTML = this.flightArrvl;
				getDomElementById("rcvDate" + index).innerHTML = this.rcvDate;
				getDomElementById("manifestDate" + index).innerHTML = this.manifestDate;
				
				if(typeDom.value == 'CN'){
					inValidNumbers = this.inValidConsg;	
				}else if(typeDom.value == 'RN'){
					inValidNumbers = this.inValidRef;
				}				
			});	
			
			if(typeDom.value == 'CN' && !isNull(inValidNumbers)){
				alert("Invalid consignment numbers: "+ inValidNumbers);
			}else if(typeDom.value == 'RN' && !isNull(inValidNumbers)){
				alert("Invalid Ref numbers: "+ inValidNumbers);
			}
			document.getElementById("trackBtn").style.display = 'none';
		}
	}
	hideProcessing();
}

function clearScreen(action){
	var url = "./multipleTracking.do?submitName=viewMultipleTracking";
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		document.getElementById("consgRefNo").value="";
		document.multipleTrackingForm.action = url;
		document.multipleTrackingForm.submit();
	}
}