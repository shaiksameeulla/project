$(document).ready( function () {
	var oTable = $('#cnCollectionDetails').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"170%",
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

//Add Row Funtions..
var serialNo = 1;
var rowCount = 1;

/**
 * To add new rows to CN Collection grid
 */
function addCnCollectionRow() {
	$('#cnCollectionDetails').dataTable().fnAddData([
		'<input type="text" class="txtbox width110" id = "txnNo'+rowCount+'" name="to.txnNo" value ="" readonly="true" tabindex="-1"/>\
		 <input type="hidden" name="to.collectionEntryId" id="collectionEntryId' + rowCount+'" value =""/>\
		 <input type="hidden" name="to.collectionID" id="collectionID' + rowCount+'" value =""/>',
		'<input type="text" id="receiptNo' + rowCount + '" name="to.receiptNo" class="txtbox width70" size="11" onkeypress="return onlyNumeric(event);" value =""/>',
		'<input type="text" id="cnNo'+ rowCount + '" name="to.cnNo" class="txtbox width130" size="11" readonly="true" tabindex="-1" value =""/>\
		 <input type="hidden" name="to.consgIds" id="consgIds' + rowCount+'" value =""/>',
		'<input type="text" name = "to.collectionType" id="collectionType'+ rowCount + '" class="txtbox width70" size="11" readonly="true" tabindex="-1" value =""/>',
		'<input type="text" name = "to.amount" id="amount'+ rowCount+ '" class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" readonly="true" tabindex="-1" value =""/>',
		'<input type="text" name = "to.rcvdAmt" id="rcvdAmt'+ rowCount+ '" maxlength="10" class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur="validateRcvdAmt(this);" value =""/>',
		'<input type="text" name = "to.tdsAmt" id="tdsAmt'+ rowCount+ '" maxlength="10" class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur="setTotalAmt('+rowCount+');" value =""/>',
		'<input type="text" name = "to.totals" id="total'+ rowCount+ '" maxlength="10" class="txtbox width70" size="11" readonly="true" tabindex="-1" value =""/>',
		'<select id="mode'+ rowCount + '" name="to.mode" class="selectBox width145" onchange="checkPaymentModeDtls(this);" value =""/>' + modeOptions + '</select>',
		'<select id="cnfor'+ rowCount + '" name="to.cnfor" class="selectBox width145" value =""/>' + cnForOptions + '</select>',
		'<input type="text" name = "to.chqNo" readonly="true" id="chqNo'+ rowCount+ '" maxlength="6" class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur="validateChqNumber(this);" value =""/>',
		'<input type="text" name = "to.chqDate" readonly="true" id="chqDate'+ rowCount+ '" class="txtbox width70" size="11" value =""/>&nbsp;<a id="calendar'+ rowCount+ '" href="javascript:show_calendar(\'chqDate'+rowCount+'\', this.value);" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>',
		'<input type="text" id="bankName'+ rowCount +'" readonly="true" name="to.bankName" maxlength="25" class="txtbox width70" onkeypress="return onlyAlphabet(event);" value =""/>',
		'<select id="reasonId'+rowCount+'" name="to.reasonIds" class="selectBox width145" onchange="validateReasons(this);" value =""/>' + reasonOptions + '</select>\
		 <input type="hidden" name="to.cnDeliveryDt" id="cnDeliveryDt' + rowCount+'" value =""/>'
	]);
	//getModeDtls(document.getElementById("mode" + rowCount));
    //getCnforList(document.getElementById("cnfor" + rowCount));
    //getBankIdList(document.getElementById("bankId" + rowCount));
	rowCount++;
	serialNo++;
}

/**
 * To check payment mode details
 * 
 * @param domObj
 */
function checkPaymentModeDtls(domObj){
	rowId = getRowId(domObj,"mode");
	if(domObj.value == CHQ_MODE){
		$('#chqNo' + rowId).removeAttr("readonly");
		$('#chqNo' + rowId).removeAttr("tabindex");
		//$('#chqDate' + rowId).removeAttr("readonly");
		$("#bankName" + rowId).removeAttr("readonly");
		$('#bankName' + rowId).removeAttr("tabindex");
		$("#calendar" + rowId).show();
		getDomElementById("cnfor"+rowId).disabled = false;
	} else {
		jQuery("#chqNo"+rowId).val("");
		jQuery("#bankName"+rowId).val("");
		jQuery('#chqDate'+rowId).val("");
		jQuery("#chqNo"+rowId).attr("readonly", true);
		jQuery("#bankName"+rowId).attr("readonly", true);
		$("#calendar" + rowId).hide();
		getDomElementById("chqNo"+rowId).setAttribute("tabindex","-1");
		getDomElementById("chqDate"+rowId).setAttribute("tabindex","-1");
		getDomElementById("bankName"+rowId).setAttribute("tabindex","-1");
		getDomElementById("cnfor"+rowId).disabled = true;
	}
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
		},
	   async: false
	});
	jQuery.unblockUI();
}

/**
 * On load get default Objects
 */
function loadDefaultObjects() {
	//addCnCollectionRow();
	getTodaysDeliverdConsgDtls();
}

//Ajax call to populate list of Payment Mode.
/*var modegridId;
function getModeDtls(domObj){
	modegridId = getRowId(domObj,"mode");
	var url="./cnCollection.do?submitName=getPaymentModeDtls";
	ajaxCallWithoutForm(url, ajxRespForModeDtls);
}
//Ajax call response to populate list of CollectionAgainst
function ajxRespForModeDtls(ajaxResp){
	if (!isNull(ajaxResp)) {
		var content = document.getElementById('mode'+modegridId);
		content.innerHTML = "";
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.paymentId;
			option.appendChild(document.createTextNode(this.paymentType));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}*/

//Ajax call to populate list of Bank List.
//This function is not getting called from anywhere 
var bankId;
function getBankIdList(domObj){
	bankId = getRowId(domObj,"bankId");
	var url="./cnCollection.do?submitName=getBankDtls";
	ajaxCallWithoutForm(url, ajxRespForBankIdList);
}
//Ajax call response to populate list of CollectionAgainst
function ajxRespForBankIdList(ajaxResp){
	if (!isNull(ajaxResp)) {
		var content = document.getElementById('bankId'+bankId);
		content.innerHTML = "";
		option = document.createElement("option");
		option.value = "";
		option.appendChild(document.createTextNode("----Select----"));
		content.appendChild(option);
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.bankId;
			option.appendChild(document.createTextNode(this.bankName));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}

//Ajax call to populate list of CollectionAgainst
/*var cnfor;
function getCnforList(domObj){
	cnfor = getRowId(domObj,"cnfor");
	var url="./cnCollection.do?submitName=getCNForDtls";
	ajaxCallWithoutForm(url, ajxRespForgetCnforList);
}
//Ajax call response to populate list of CollectionAgainst
function ajxRespForgetCnforList(ajaxResp){
	if (!isNull(ajaxResp)) {
		var content = document.getElementById('cnfor'+cnfor);
		content.innerHTML = "";
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.stdTypeCode;
			option.appendChild(document.createTextNode(this.description));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}*/

/**
 * TO get Todays deliverd CN details
 */
function getTodaysDeliverdConsgDtls(){
	//var cureentDate = document.getElementById("collectionDate").value;
	var loginOffId = document.getElementById("originOfficeId").value;
	var url="./cnCollection.do?submitName=getTodaysDeliverdConsgDtls&originOfficeId="+loginOffId;
	showProcessing();
	jQuery.ajax({
		url : url,
		success : function(req) {
			respFordeliverdConsgDtls(req);
		}
	});
}

function respFordeliverdConsgDtls(ajaxResp){
	if(!isNull(ajaxResp)){
		if(isJsonResponce()){
			return;
		}
		var cnCollection = eval('(' + ajaxResp + ')');
		if(!isNull(cnCollection.cnCollectionDtls.length)){
			addCnCollectionRow();
			for (var i=0; i < cnCollection.cnCollectionDtls.length; i++){
				document.getElementById("txnNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].txnNo;
				document.getElementById("cnNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].cnNo;
				document.getElementById("collectionType"+(i+1)).value = cnCollection.cnCollectionDtls[i].collectionType;
				document.getElementById("consgIds"+(i+1)).value =cnCollection.cnCollectionDtls[i].consgId;
				document.getElementById("collectionEntryId"+(i+1)).value =cnCollection.cnCollectionDtls[i].entryId;
				document.getElementById("collectionID"+(i+1)).value =cnCollection.cnCollectionDtls[i].collectionID;
				document.getElementById("receiptNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].receiptNo;
				if(!isNull(cnCollection.cnCollectionDtls[i].rcvdAmt))
					document.getElementById("rcvdAmt"+(i+1)).value =cnCollection.cnCollectionDtls[i].rcvdAmt;
				//setAmountFormatZero(document.getElementById("rcvdAmt"+(i+1)));
				if(!isNull(cnCollection.cnCollectionDtls[i].tdsAmt))
					document.getElementById("tdsAmt"+(i+1)).value =cnCollection.cnCollectionDtls[i].tdsAmt;
				if(!isNull(cnCollection.cnCollectionDtls[i].total))
					document.getElementById("total"+(i+1)).value =cnCollection.cnCollectionDtls[i].total;
				//setAmountFormatZero(document.getElementById("tdsAmt"+(i+1)));
				if(!isNull(cnCollection.cnCollectionDtls[i].amount))
					document.getElementById("amount"+(i+1)).value = cnCollection.cnCollectionDtls[i].amount;
				//setAmountFormatZero(document.getElementById("amount"+(i+1)));
				document.getElementById("mode"+(i+1)).value =cnCollection.cnCollectionDtls[i].paymentModeId;
				document.getElementById("cnfor"+(i+1)).value =cnCollection.cnCollectionDtls[i].cnfor;
				document.getElementById("chqNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].chqNo;
				document.getElementById("chqDate"+(i+1)).value =cnCollection.cnCollectionDtls[i].chqDate;
				document.getElementById("bankName"+(i+1)).value =cnCollection.cnCollectionDtls[i].bankName;
				document.getElementById("reasonId"+(i+1)).value = cnCollection.cnCollectionDtls[i].reasonId;
				document.getElementById("cnDeliveryDt"+(i+1)).value = cnCollection.cnCollectionDtls[i].consgDeliveryDate;
				checkPaymentModeDtls(document.getElementById("mode"+(i+1)));
				if ((i+1)!=cnCollection.cnCollectionDtls.length)
				 addCnCollectionRow();
			}
		} else {
			alert("No Consignment(s) Found For CN Collection");
		}
	}
	hideProcessing();
}

/**
 * To save Cn Collection details
 */
function saveOrUpdateCNCollection(){
	//if(validateCNGridDtls()){
		showProcessing();
		dropdownEnable();//To enable all disabled drop down to SAVE
		var url = './cnCollection.do?submitName=saveOrUpdateCNCollection';
		jQuery.ajax({
			url : url,
			data : jQuery("#cnCollectionForm").serialize(),
			success : function(req) {
				callSaveOrUpdateCNCollection(req);
			}
		});
	//}
}

function callSaveOrUpdateCNCollection(ajaxResp) {
	var cnCollection  = eval('(' + ajaxResp + ')');
	if (!isNull(cnCollection.isSaved) && cnCollection.isSaved == 'SUCCESS') {
		alert('CN Collection saved successfully.');
		/*for (var i=0; i < cnCollection.cnCollectionDtls.length; i++){
			document.getElementById("txnNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].txnNo;
			document.getElementById("cnNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].cnNo;
			document.getElementById("collectionType"+(i+1)).value = cnCollection.cnCollectionDtls[i].collectionType;
			document.getElementById("consgIds"+(i+1)).value =cnCollection.cnCollectionDtls[i].consgId;
			document.getElementById("collectionEntryId"+(i+1)).value =cnCollection.cnCollectionDtls[i].entryId;
			document.getElementById("collectionID"+(i+1)).value =cnCollection.cnCollectionDtls[i].collectionID;
			document.getElementById("receiptNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].receiptNo;
			if(!isNull(cnCollection.cnCollectionDtls[i].amount))
				document.getElementById("amount"+(i+1)).value = cnCollection.cnCollectionDtls[i].amount;
			if(!isNull(cnCollection.cnCollectionDtls[i].rcvdAmt))
				document.getElementById("rcvdAmt"+(i+1)).value =cnCollection.cnCollectionDtls[i].rcvdAmt;
			if(!isNull(cnCollection.cnCollectionDtls[i].tdsAmt))
				document.getElementById("tdsAmt"+(i+1)).value =cnCollection.cnCollectionDtls[i].tdsAmt;
			document.getElementById("mode"+(i+1)).value =cnCollection.cnCollectionDtls[i].paymentModeId;
			document.getElementById("cnfor"+(i+1)).value =cnCollection.cnCollectionDtls[i].cnfor;
			document.getElementById("chqNo"+(i+1)).value =cnCollection.cnCollectionDtls[i].chqNo;
			document.getElementById("chqDate"+(i+1)).value =cnCollection.cnCollectionDtls[i].chqDate;
			document.getElementById("bankName"+(i+1)).value =cnCollection.cnCollectionDtls[i].bankName;
			document.getElementById("reasonId"+(i+1)).value =cnCollection.cnCollectionDtls[i].reasonId;
			checkPaymentModeDtls(document.getElementById("mode"+(i+1)));
		}*/
	} else {
		alert('CN Collection Can Not Saved.');
	}
	cancleCnData();
	hideProcessing();
}

/**
 * To submit cn collection details
 */
function submitCnCollection(){
	if(validateCNGridDtls()){
		var url = './cnCollection.do?submitName=submitCnCollection';
		showProcessing();
		dropdownEnable();//To enable all disabled drop down to SUBMIT
		jQuery.ajax({
			url : url,
			data : jQuery("#cnCollectionForm").serialize(),
			success : function(req) {
				callsubmitCnCollection(req);
			}
		});
	}
}

function callsubmitCnCollection(ajaxResp) {
	var collectionDtlsTO  = eval('(' + ajaxResp + ')');
	if(!isNull(ajaxResp)){
		if(isJsonResponce()){
			return;
		}
	}
	if (!isNull(collectionDtlsTO.isSaved) && collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('CN Collection Submitted successfully.');
	} else {
		alert('CN Collection Can Not Submitted.');
	}
	hideProcessing();
	cancleCnData();
	/*var url = "./cnCollection.do?submitName=viewCNCollection";
	document.cnCollectionForm.action = url;
	document.cnCollectionForm.submit();*/
}

/**
 * To clear screen
 */
function cancleCnData(){
	var url = "./cnCollection.do?submitName=viewCNCollection";
	document.cnCollectionForm.action = url;
	document.cnCollectionForm.submit();
}

/**
 * validate CN collection grid row(s)
 * 
 * @returns {Boolean}
 */
function validateCNGridDtls(){
	for(var i=1; i<rowCount; i++) {
		var receiptNo = getDomElementById("receiptNo"+i);//           
		var rcvdAmt = getDomElementById("rcvdAmt"+i);
		var tdsAmt = getDomElementById("tdsAmt"+i);
		var mode = getDomElementById("mode"+i);
		var reasonId = getDomElementById("reasonId"+i);
		var atLine = " at Line:"+i;
		if(isNull(receiptNo.value)){
			alert("Please Provide Receipt No." + atLine);
			setTimeout(function(){receiptNo.focus();}, 10);
			return false;
		}
		if(PARTY_LETTER!=reasonId.value && isNull(rcvdAmt.value)){
			alert("Please Provide Recieve Amount" + atLine);
			setTimeout(function(){rcvdAmt.focus();}, 10);
			return false;
		}
		if(PARTY_LETTER==reasonId.value){
			rcvdAmt.value = 0;
		}
		if(isNull(tdsAmt.value)){
			tdsAmt.value = 0;
			/*alert("Please Provide TDS Amount" + atLine);
			setTimeout(function(){tdsAmt.focus();}, 10);
			return false;*/
		}
		/* Check madatory for mode. */
		if(mode.value==CHQ_MODE){
			var cnfor = getDomElementById("cnfor"+i);
			var chqNo = getDomElementById("chqNo"+i);
			var chqDate = getDomElementById("chqDate"+i);
			var bankName = getDomElementById("bankName"+i);
			if(isNull(cnfor.value)){
				alert("Please Select CN For" + atLine);
				setTimeout(function(){cnfor.focus();}, 10);
				return false;
			}
			if(isNull(chqNo.value)){
				alert("Please Provide Cheque No." + atLine);
				setTimeout(function(){chqNo.focus();}, 10);
				return false;
			}
			if(isNull(chqDate.value)){
				alert("Please Provide Cheque Date" + atLine);
				setTimeout(function(){chqDate.focus();}, 10);
				return false;
			}
			if(isNull(bankName.value)){
				alert("Please Provide Bank" + atLine);
				setTimeout(function(){bankName.focus();}, 10);
				return false;
			}
		}
		/*if(isNull(reasonId.value)){
			alert("Please Select Reason" + atLine);
			setTimeout(function(){reasonId.focus();}, 10);
			return false;
		}*/
	}//END FOR LOOP
	return true;
}

/**
 * To validate receive amount
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateRcvdAmt(obj){
	var rowId = getRowId(obj,"rcvdAmt");
	var amt = getDomElementById("amount"+rowId);
	var atLine = " at Line:"+rowId;
	//setAmountFormatZero(obj);
	if(parseFloat(obj.value) > parseFloat(amt.value)){
		alert("Received Amount should not greater than Amount"+atLine);
		obj.value="";
		setTimeout(function(){obj.focus();}, 10);
		return false;
	}
	setTotalAmt(rowId);
	return true;
}

/**
 * Validate reason value
 * 
 * @param obj
 */
function validateReasons(obj){
	var rowId = getRowId(obj,"reasonId");
	if(obj.value==PARTY_LETTER){
		getDomElementById("rcvdAmt"+rowId).value="";
		setTotalAmt(rowId);
	}
}

/**
 * To set Total Amount to grid
 * 
 * @param rowId
 */
function setTotalAmt(rowId){
	var rcvdAmt = getDomElementById("rcvdAmt"+rowId).value;
	var tdsAmt = getDomElementById("tdsAmt"+rowId).value;
	 
	rcvdAmt = (isNull(rcvdAmt))?0:parseFloat(rcvdAmt)*100;
	tdsAmt = (isNull(tdsAmt))?0:parseFloat(tdsAmt)*100;
	
	var totAmt = rcvdAmt + tdsAmt;
	totAmt/=100;
	jQuery("#total"+rowId).val(totAmt);
}