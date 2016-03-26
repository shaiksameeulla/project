var serialNo = 1;
var rowCount = 1;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

$(document).ready( function () {
	var oTable = $('#codReceipt').dataTable( {
		"sScrollY": "160",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,				"bInfo": false,
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


function addRow() {

	$('#codReceipt')
			.dataTable()
			.fnAddData(
					[
					 serialNo,
					 '<span id="expenseConsgType'+rowCount+'"></span>',
					 '<span id="amount'+rowCount+'"></span>'
					]);
 
	rowCount++;
	serialNo++;
}


function deleteAllRow() {
	var table = getDomElementById("codReceipt");
	var tableRowCount = table.rows.length;
	var oTable = $('#codReceipt').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}

}

function codReceiptStartUp(){
	document.getElementById("consgNo").focus();
	buttonPrintDisabled("printBtn","btnintform");
	jQuery("#" + "printBtn").addClass("btnintformbigdis");
}

function isValidConsignment(consgNoObj) {
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

		consgNoObj.value = $.trim(consgNoObj.value);

		if (isNull(consgNoObj.value)) {
			return false;
		}

		if (consgNoObj.value.length != 12) {
			// clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12
			// characters only!");
			alert("Consignment No. should contain 12 characters only!");
			document.getElementById("consgNo").value = "";
			return false;
		}

		if (!consgNoObj.value.substring(0, 1).match(letters)
				|| (!consgNoObj.value.substring(4, 5).match(letters) && !consgNoObj.value
						.substring(1, 4).match(alphaNumeric))
				|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
				|| !numpattern.test(consgNoObj.value.substring(5))) {
			// clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not
			// correct!");
			alert("Consignment No. Format is not correct!");
			document.getElementById("consgNo").value = "";
			return false;
		}
	return true;
}

function searchConsignmentDetails(){
		var consgNo = document.getElementById("consgNo");
		if(!isNull(consgNo.value)){
		if(isValidConsignment(consgNo)){
			
			 var url = './codReceipt.do?submitName=searchConsignmentDetails&ConsgNo='+ consgNo.value;
			 
			 //ajaxCall(url, "codReceiptForm", searchCallback);
			 ajaxCallWithoutForm(url, searchCallback);
			 
		 }
		}else{
			alert('Consignment No Is Empty');
			document.getElementById("consgNo").focus();
		}	
}

function searchCallback(data){
	cancelPage();
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		
		var j = 0;
		//var custBillList = eval('(' + data + ')');
		var custBillList =data;
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		//var j = 0;
		if(!isNull(custBillList)){
		  document.getElementById("consgNo").value=custBillList.consgNo;
		  document.getElementById("region").value=custBillList.regionName;
		  document.getElementById("branch").value=custBillList.branchName;
		  document.getElementById("bookingDate").value=custBillList.bookDate;
		for(var i =0 ;i<custBillList.codReceiptDetailsTOs.length;i++){
			addRow();	
			/*$("#customer" + (j+1)).val(custBillList[i].customerTO.customerId);
			$("#billNumber" + (j+1)).val(custBillList[i].billTOs[k].invoiceId);
			$("#signature" + (j+1)).val("");
			$("#shipToCode" + (j+1)).val(custBillList[i].billTOs[k].shipToCode);
			*/
			$("#expenseConsgType" + (j+1)).html("<align:left>" +custBillList.codReceiptDetailsTOs[i].expenseDescription+"</align:left>");
			$("#amount" + (j+1)).html(custBillList.codReceiptDetailsTOs[i].expenseTotalAmount);
			j++;
			
			}
		if(!isNull(custBillList.grandTotal)){
			addRow();	
			$("#expenseConsgType" + (j+1)).html("<b>Grand Total</b>");
			$("#amount" + (j+1)).html("<b>"+custBillList.grandTotal+"</b>");
			jQuery("#"+ "printBtn").attr("disabled", false);
			jQuery("#"+"printBtn").removeClass("btnintformbigdis");
			jQuery("#" + "printBtn").addClass("btnintform");
		}
	  }	
	}
}

function cancelPage(){
	/*var url = "./codReceipt.do?submitName=preparePage";
	document.codReceiptForm.action=url;
	document.codReceiptForm.submit();*/
	document.getElementById("region").value="";
	document.getElementById("branch").value="";
	deleteAllRow();
	document.getElementById("branch").value="";
	document.getElementById("bookingDate").value="";
	document.getElementById("consgNo").value="";
	buttonPrintDisabled("printBtn","btnintform");
	jQuery("#" + "printBtn").addClass("btnintformbigdis");
	document.getElementById("consgNo").focus();
}

function buttonPrintDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
	jQuery("#" + btnName).addClass("btnintformbigdis");
}

function printCodReceipt(){
	var consgNo = document.getElementById("consgNo");
	var printCodUrl="./codReceipt.do?submitName=printCodReceipt&ConsgNo="+ consgNo.value;
	window.open(printCodUrl,'CodReceipt Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
}