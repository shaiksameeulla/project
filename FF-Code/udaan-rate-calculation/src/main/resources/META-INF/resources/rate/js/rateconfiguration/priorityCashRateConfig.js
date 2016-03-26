var prProdCatId = "";
$(document).ready( function () {
	var oTable = $('#priorityWtSlabTable').dataTable( {
		"sScrollY": "70",
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

$(document).ready( function () {
	var oTable = $('#prioritySplDestTable').dataTable( {
		"sScrollY": "40",
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


var rowCount_PR=1;
var cols_PR=new Array();

/**
 * Startup function for priority product
 */
function priorityStartup(){
	//var prHeaderProMapId = jQuery("#prHeaderProMapId").val();
	getDomElementById("regionId").disabled = false;
	getOriginSector(getDomElementById("regionId"));
	getDomElementById("regionId").disabled = true;
	
	getDomElementById("priorityservicedOn").options[0].selected = true;
	//if(isNull(prHeaderProMapId)){
		searchCashRateProductDtls(PRO_CODE_PRIORITY);
		getDomElementById("priorityservicedOn").disabled = false;
	//}		
}

/**
 * To add special destination row(s).
 */
function addPrioritySplDestRow() {
	addPrioritySplDestCols();
	$('#prioritySplDestTable').dataTable().fnAddData(cols_PR);
	loadStateDropDown("stateId"+prProdCatId+rowCount_PR, "", statesList);
	clearDropDownList("cityIds"+prProdCatId+rowCount_PR);
	rowCount_PR++;
}
/**
 * To add special destination additional column(s).
 */
function addPrioritySplDestCols() {
	var splDestColCount=getDomElementById("prSplDestColCount").value;
	splDestColCount=parseInt(splDestColCount)+2;
	prProdCatId=getDomElementById("prProdCatId").value;
	cols_PR[0] = '<select  id="stateId'+prProdCatId+rowCount_PR+'" name="to.prioritySplDestTO.stateAry" class="selectBox width145" onchange="getCityByState(this,'+prProdCatId+','+rowCount_PR+',\'cityIds\');" />';		
	cols_PR[1] = '<select  id="cityIds'+prProdCatId+rowCount_PR+'" name="to.prioritySplDestTO.cityIds"  onchange="validateCities(\'PR\','+prProdCatId+','+rowCount_PR+',\'cityIds\',\'stateId\');"/>';
	for(var i=2; i<splDestColCount; i++) {
		if (i+1==splDestColCount) {
			cols_PR[i] = '<input type="text" id="specialDestRates'+prProdCatId+rowCount_PR+i+'" name="to.prioritySplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'PR\','+prProdCatId+','+rowCount_PR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event), addNewSplDestRow(event,'+prProdCatId+','+rowCount_PR+','+(splDestColCount-2)+',\''+PRO_CODE_PRIORITY+'\');" />'
					+ '<input type="hidden" id="specialDestIds'+prProdCatId+rowCount_PR+i+'" name="to.prioritySplDestTO.specialDestIds" />';
		} else {
			cols_PR[i] = '<input type="text" id="specialDestRates'+prProdCatId+rowCount_PR+i+'" name="to.prioritySplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'PR\','+prProdCatId+','+rowCount_PR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event);"/>'
					+ '<input type="hidden" id="specialDestIds'+prProdCatId+rowCount_PR+i+'" name="to.prioritySplDestTO.specialDestIds" />';
		}
	}
}

/**
 * To save or update priority cash rate details
 */
function saveOrUpdatePriorityCashRateDtls(){
	isSaveClicked = true;
	if(validateTabGrids(PRO_CODE_PRIORITY)) {
		disEnableSector("destSectorIds", "slabRates", "prProdCatId", "prSectorRowCount", "prSplDestColCount", false, false);
		jQuery("#productCatType").val(PRO_CAT_TYPE_P);
		jQuery("#productCode").val(PRO_CODE_PRIORITY);
		jQuery("#headerStatus").val(INACTIVE);
		getDomElementById("regionId").disabled = false;
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateCashRateProductDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSavePriorityCashRate);
	}
	isSaveClicked = false;
}
//call back function for saveOrUpdatePriorityCashRateDtls
function callSavePriorityCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		if(isNull(data.errorMsg)) {
			var prProdCatId = jQuery("#prProdCatId").val();
			var cols = jQuery("#prSplDestColCount").val();
			var rows = jQuery("#prSectorRowCount").val();
			//Header
			jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
			
			//Product
			
			for(var i=0; i<data.cashRateProductTOList.length; i++){
				var product = data.cashRateProductTOList[i];
				jQuery("#prHeaderProMapId").val(product.headerProductMapId);
				if(product.productCode == PRO_CODE_PRIORITY){
					enableTabs("tabsnested1",3);
					setFlags(data);
					disEnableSector("destSectorIds", "slabRates", "prProdCatId", "prSectorRowCount", "prSplDestColCount", true, false);
					priorityRatesTO = product;
					assignPriorityRates();					
				}
				//Wt. Slab Rate
				/*for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(prProdCatId, parseInt(rows), parseInt(cols), slabRate);
				}
				
				//Special Destination
				for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList[k];
					//set splDest Id.s
					setSplDestRate(prProdCatId, parseInt(rowCount_PR), parseInt(cols), splDest);
				}*/
			}
			alert(DATA_SAVED);
			getDomElementById("regionId").disabled = true;
		} else {
			alert(data.errorMsg);
		}
	} else {
		alert(DATA_NOT_SAVED);
	}
}

function getPrioroityRates(){
	assignPriorityRates();
}


function deleteRow(tableId, rowIndex) {
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}


function deleteTableRow(tableId) {
	//$("#example4 > tbody > tr").remove();
	//$("#example4 > tbody").empty();
	try {		
	var table = getDomElementById(tableId);

	var tableRowCount = table.rows.length;
	for ( var i = 0; i < tableRowCount; i++) {
			deleteRow(tableId, i);
			tableRowCount--;
			i--;
	}
	}catch(e){
	alert(e);
	}
	rowCount_PR = 1;

}