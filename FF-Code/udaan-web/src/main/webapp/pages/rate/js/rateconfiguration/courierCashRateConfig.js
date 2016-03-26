var coProdCatId="";
$(document).ready( function () {
	var oTable = $('#courierWtSlabTable').dataTable( {
		"sScrollY": "100",
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
	var oTable = $('#courierSplDestTable').dataTable( {
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

var rowCount_CO=1;
var cols_CO=new Array();

/**
 * To add special destination row(s).
 */
function addCourierSplDestRow() {
	addCourierSplDestCols();
	$('#courierSplDestTable').dataTable().fnAddData(cols_CO);
	loadStateDropDown("stateId"+coProdCatId+rowCount_CO, "", statesList);
	clearDropDownList("cityIds"+coProdCatId+rowCount_CO);	
	rowCount_CO++;
}
/**
 * To add special destination additional column(s).
 */
function addCourierSplDestCols() {
	var splDestColCount=getDomElementById("coSplDestColCount").value;
	splDestColCount=parseInt(splDestColCount)+2;
	coProdCatId=getDomElementById("coProdCatId").value;
	cols_CO[0] = '<select  id="stateId'+coProdCatId+rowCount_CO+'" name="to.courierSplDestTO.stateAry" class="selectBox width145" onchange="getCityByState(this,'+coProdCatId+','+rowCount_CO+',\'cityIds\');" />';
	cols_CO[1] = '<select  id="cityIds'+coProdCatId+rowCount_CO+'" name="to.courierSplDestTO.cityIds" class="selectBox width145" onchange="validateCities(\'CO\','+coProdCatId+','+rowCount_CO+',\'cityIds\',\'stateId\');"/>';
	for(var i=2; i<splDestColCount; i++) {
		if (i+1==splDestColCount) {
			cols_CO[i] = '<input type="text" id="specialDestRates'+coProdCatId+rowCount_CO+i+'" name="to.courierSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'CO\','+coProdCatId+','+rowCount_CO+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event), addNewSplDestRow(event,'+coProdCatId+','+rowCount_CO+','+(splDestColCount-2)+',\''+PRO_CODE_COURIER+'\');" />'
					+ '<input type="hidden" id="specialDestIds'+coProdCatId+rowCount_CO+i+'" name="to.courierSplDestTO.specialDestIds" />';
		} else {
			cols_CO[i] = '<input type="text" id="specialDestRates'+coProdCatId+rowCount_CO+i+'" name="to.courierSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'CO\','+coProdCatId+','+rowCount_CO+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event);"/>'
					+ '<input type="hidden" id="specialDestIds'+coProdCatId+rowCount_CO+i+'" name="to.courierSplDestTO.specialDestIds" />';
		}
	}
}

/**
 * To save or update courier cash rate details
 */
function saveOrUpdateCourierCashRateDtls(){
	isSaveClicked = true;
	if(validateTabGrids(PRO_CODE_COURIER)) {
		disEnableSector("destSectorIds", "slabRates", "coProdCatId", "coSectorRowCount", "coSplDestColCount", false, false);
		jQuery("#productCode").val(PRO_CODE_COURIER);
		jQuery("#productCatType").val(PRO_CAT_TYPE_N);
		jQuery("#headerStatus").val(INACTIVE);
		jQuery("#priorityservicedOn").val("");
		getDomElementById("regionId").disabled = false;
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateCashRateProductDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSaveCourierCashRate);
	}
	isSaveClicked = false;
}
//call back function for saveOrUpdateCourierCashRateDtls
function callSaveCourierCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		if(isNull(data.errorMsg)) {
			var coProdCatId = jQuery("#coProdCatId").val();
			var cols = jQuery("#coSplDestColCount").val();
			var rows = jQuery("#coSectorRowCount").val();
			//Header
			jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);			
			//Product
			for(var i=0; i<data.cashRateProductTOList.length; i++){
				var product = data.cashRateProductTOList[i];
				if(product.productCode == PRO_CODE_COURIER){
				jQuery("#coHeaderProMapId").val(product.headerProductMapId);
				enableTabs("tabsnested",5);
				setFlags(data);
				disEnableSector("destSectorIds", "slabRates", "coProdCatId", "coSectorRowCount", "coSplDestColCount", true, false);
				
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(coProdCatId, parseInt(rows), parseInt(cols), slabRate);
				}
				
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				
				/*for(var l=0; l<rowCnt; l++){
					//addCourierSplDestRow();
				}*/
				
				//Special Destination
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(coProdCatId, "CO", rowCnt, parseInt(cols), splDest,null);
				//}
				}
				
			}
			alert(DATA_SAVED);			
		//getDomElementById("regionId").disabled = true;
		} else {
			alert(data.errorMsg);
		}
	} else {
		alert(DATA_NOT_SAVED);
	}
}




