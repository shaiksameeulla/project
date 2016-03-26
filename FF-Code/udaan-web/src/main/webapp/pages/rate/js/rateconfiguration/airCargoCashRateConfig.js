var arProdCatId="";
$(document).ready( function () {
	var oTable = $('#airCargoWtSlabTable').dataTable( {
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
	var oTable = $('#airCargoSplDestTable').dataTable( {
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

var rowCount_AR=1;
var cols_AR=new Array();

/**
 * Startup function for air-cargo product
 */
function airCargoStartup(){
	var arHeaderProMapId = jQuery("#arHeaderProMapId").val();
	var cashRateHeaderId = getDomElementById("cashRateHeaderId").value;
	if(!isNull(cashRateHeaderId)){
		getDomElementById("regionId").disabled = false;
		getOriginSector(getDomElementById("regionId"));
		//getDomElementById("regionId").disabled = true;
	}
	if(isNull(arHeaderProMapId)){
		searchCashRateProductDtls(PRO_CODE_AIR_CARGO);
	}
}

/**
 * To add special destination row(s).
 */
function addAirCargoSplDestRow() {
	addAirCargoSplDestCols();
	$('#airCargoSplDestTable').dataTable().fnAddData(cols_AR);
	loadStateDropDown("stateId"+arProdCatId+rowCount_AR, "", statesList);
	clearDropDownList("cityIds"+arProdCatId+rowCount_AR);	
	rowCount_AR++;
}
/**
 * To add special destination additional column(s).
 */
function addAirCargoSplDestCols() {
	var splDestColCount=getDomElementById("arSplDestColCount").value;
	splDestColCount=parseInt(splDestColCount)+2;
	arProdCatId=getDomElementById("arProdCatId").value;
	cols_AR[0] = '<select  id="stateId'+arProdCatId+rowCount_AR+'" name="to.airCargoSplDestTO.stateAry" class="selectBox width145" onchange="getCityByState(this,'+arProdCatId+','+rowCount_AR+',\'cityIds\');" />';			
	cols_AR[1] = '<select  id="cityIds'+arProdCatId+rowCount_AR+'" name="to.airCargoSplDestTO.cityIds"  class="selectBox width145" onchange="validateCities(\'AR\','+arProdCatId+','+rowCount_AR+',\'cityIds\',\'stateId\');"/>';
	for(var i=2; i<splDestColCount; i++) {
		if (i+1==splDestColCount) {
			cols_AR[i] = '<input type="text" id="specialDestRates'+arProdCatId+rowCount_AR+i+'" name="to.airCargoSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'AR\','+arProdCatId+','+rowCount_AR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event), addNewSplDestRow(event,'+arProdCatId+','+rowCount_AR+','+(splDestColCount-2)+',\''+PRO_CODE_AIR_CARGO+'\');" />'
					+ '<input type="hidden" id="specialDestIds'+arProdCatId+rowCount_AR+i+'" name="to.airCargoSplDestTO.specialDestIds" />';
		} else {
			cols_AR[i] = '<input type="text" id="specialDestRates'+arProdCatId+rowCount_AR+i+'" name="to.airCargoSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'AR\','+arProdCatId+','+rowCount_AR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event);"/>'
					+ '<input type="hidden" id="specialDestIds'+arProdCatId+rowCount_AR+i+'" name="to.airCargoSplDestTO.specialDestIds" />';
		}
	}
}

/**
 * To save or update air-cargo cash rate details
 */
function saveOrUpdateAirCargoCashRateDtls(){
	isSaveClicked = true;
	if(validateTabGrids(PRO_CODE_AIR_CARGO) && validateMinChrgWt(PRO_CODE_AIR_CARGO)) {
		jQuery("#productCode").val(PRO_CODE_AIR_CARGO);
		jQuery("#productCatType").val(PRO_CAT_TYPE_N);
		jQuery("#headerStatus").val(INACTIVE);
		jQuery("#priorityservicedOn").val("");
		getDomElementById("airCargoOriginSectorId").disabled = false;
		getDomElementById("regionId").disabled = false;
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateCashRateProductDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSaveAirCargoCashRate);
	}
	isSaveClicked = false;
}
//call back function for saveOrUpdateAirCargoCashRateDtls
function callSaveAirCargoCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		if(isNull(data.errorMsg)) {
			var arProdCatId = jQuery("#arProdCatId").val();
			var cols = jQuery("#arSplDestColCount").val();
			var rows = jQuery("#arSectorRowCount").val();
			
			//Header
			jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
			//Product
			for(var i=0; i<data.cashRateProductTOList.length; i++){
				var product = data.cashRateProductTOList[i];
				if(product.productCode == PRO_CODE_AIR_CARGO){
				enableTabs("tabsnested",5);
				jQuery("#arHeaderProMapId").val(product.headerProductMapId);
				jQuery("#airCargoMinChargeableWeight").val(product.minChargeableWeight);
				setFlags(data);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(arProdCatId, parseInt(rows), parseInt(cols), slabRate);
				}
				
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				//Special Destination
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(arProdCatId, "AR", rowCnt, parseInt(cols), splDest,null);
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
	getDomElementById("airCargoOriginSectorId").disabled = true;
}


