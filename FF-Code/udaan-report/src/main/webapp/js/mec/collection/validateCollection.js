$(document).ready( function () {
	var oTable = $('#collectionDetails').dataTable( {
		"sScrollY": "150",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
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


function addValidateCollectionRow() {
	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
					 		'<input type="text" id="transactionDate'+ rowCount+ '" name="transactionDate" class="txtbox width70" size="11" />',
					 		'<input type="text" id="custName'+ rowCount+ '" name="custName" class="txtbox width70" />',
					 		'<input type="text" id="custName'+ rowCount+ '" name="custName" class="txtbox width70" />',
							'<input type="text" id="collectionAgainst'+ rowCount+ '" name="collectionAgainst" class="txtbox width70" size="11" />',
							'<input type="text" name = "paymentMode" id="paymentMode'+ rowCount+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "amount" id="amount'+ rowCount+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "status" id="status'+ rowCount+ '"  class="txtbox width170" size="11" />',]);
	rowCount++;
	serialNo++;
}


/**
 * To get Office code
 */
function getAllOffices() {
	document.getElementById('officeId').value="";
	var cityId = "";
	cityId = document.getElementById("stationId").value;
	url = './validateCollection.do?submitName=getAllOfficesByCity&cityId='
			+ cityId;
	ajaxCallWithoutForm(url, printAllOffices);
}

/**
 * @param data
 */
function printAllOffices(data) {
	var stationId = document.getElementById('stationId').value;
	var content = document.getElementById('officeId');
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(stationId)){
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	var temp =document.getElementById('hiddenofficeId').value;
	if(!isNull(temp))
		document.getElementById('officeId').value = temp;
		
	}
}

function searchCollectionDetlsForValidation(){
	var headerTransNo= document.getElementById('headerTransNo').value;
	var headerStatus= document.getElementById('headerStatus').value;
	var officeId= document.getElementById('officeId').value;
	var stationId= document.getElementById('stationId').value;
	var toDate= document.getElementById('toDate').value;
	var frmDate= document.getElementById('frmDate').value;
	
	cityId = document.getElementById("stationId").value;
	url = './validateCollection.do?submitName=searchCollectionDetlsForValidation&frmDate='
			+ frmDate +'&toDate='+toDate+ '&stationId='+stationId+'&officeId='+officeId+ '&headerStatus='+headerStatus+'&headerTransNo='+headerTransNo;
	document.validateCollectionForm.action = url;
	document.validateCollectionForm.submit();
	
}


//Popup window code
function collectionDtls(url) {
	popupWindow = window.open(
		url,'popUpWindow','height=600,width=1100,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');
}


function setDefaultValue(){
	var cityId = document.getElementById("stationId").value;
	if(!isNull(cityId)){
		getAllOffices();
	}
	getDomElementById("frmDate").focus();
}
