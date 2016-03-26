var trProdCatId="";
$(document).ready( function () {
	var oTable = $('#trainWtSlabTable').dataTable( {
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
	var oTable = $('#trainSplDestTable').dataTable( {
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

var rowCount_TR=1;
var cols_TR=new Array();

/**
 * Startup function for train product
 */
function trainStartup(){
	var trHeaderProMapId = jQuery("#trHeaderProMapId").val();
	var cashRateHeaderId = getDomElementById("cashRateHeaderId").value;
	if(!isNull(cashRateHeaderId)){
		getDomElementById("regionId").disabled = false;
		getOriginSector(getDomElementById("regionId"));
		//getDomElementById("regionId").disabled = true;
	}
	if(isNull(trHeaderProMapId)){
		searchCashRateProductDtls(PRO_CODE_TRAIN);
	}
}

/**
 * To add special destination row(s).
 */
function addTrainSplDestRow() {
	addTrainSplDestCols();
	$('#trainSplDestTable').dataTable().fnAddData(cols_TR);
	loadStateDropDown("stateId"+trProdCatId+rowCount_TR, "", statesList);
	clearDropDownList("cityIds"+trProdCatId+rowCount_TR);
	rowCount_TR++;
}
/**
 * To add special destination additional column(s).
 */
function addTrainSplDestCols() {
	var splDestColCount=getDomElementById("trSplDestColCount").value;
	splDestColCount=parseInt(splDestColCount)+2;
	trProdCatId=getDomElementById("trProdCatId").value;
	cols_TR[0] = '<select  id="stateId'+trProdCatId+rowCount_TR+'" name="to.trainSplDestTO.stateAry" class="selectBox width145" onchange="getCityByState(this,'+trProdCatId+','+rowCount_TR+',\'cityIds\');" />';			
	cols_TR[1] = '<select  id="cityIds'+trProdCatId+rowCount_TR+'" name="to.trainSplDestTO.cityIds" class="selectBox width145" onchange="validateCities(\'TR\','+trProdCatId+','+rowCount_TR+',\'cityIds\',\'stateId\');"/>';
	for(var i=2; i<splDestColCount; i++) {
		if (i+1==splDestColCount) {
			cols_TR[i] = '<input type="text" id="specialDestRates'+trProdCatId+rowCount_TR+i+'" name="to.trainSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'TR\','+trProdCatId+','+rowCount_TR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event), addNewSplDestRow(event,'+trProdCatId+','+rowCount_TR+','+(splDestColCount-2)+',\''+PRO_CODE_TRAIN+'\');" />'
					+ '<input type="hidden" id="specialDestIds'+trProdCatId+rowCount_TR+i+'" name="to.trainSplDestTO.specialDestIds" />';
		} else {
			cols_TR[i] = '<input type="text" id="specialDestRates'+trProdCatId+rowCount_TR+i+'" name="to.trainSplDestTO.specialDestRates" class="txtbox width100" maxlength="5" onfocus="validateStates(\'TR\','+trProdCatId+','+rowCount_TR+',\'cityIds\',\'stateId\');" onkeypress="return onlyDecimal(event);"/>'
					+ '<input type="hidden" id="specialDestIds'+trProdCatId+rowCount_TR+i+'" name="to.trainSplDestTO.specialDestIds" />';
		}
	}
}

/**
 * To save or update train cash rate details
 */
function saveOrUpdateTrainCashRateDtls(){
	isSaveClicked = true;
	if(validateTabGrids(PRO_CODE_TRAIN) && validateMinChrgWt(PRO_CODE_TRAIN)) {
		jQuery("#productCode").val(PRO_CODE_TRAIN);
		jQuery("#productCatType").val(PRO_CAT_TYPE_N);
		jQuery("#headerStatus").val(INACTIVE);
		jQuery("#priorityservicedOn").val("");
		getDomElementById("trainOriginSectorId").disabled = false;
		getDomElementById("regionId").disabled = false;
		var url = "./cashRateConfiguration.do?submitName=saveOrUpdateCashRateProductDtls";
		ajaxJquery(url, CASH_RATE_FORM, callSaveTrainCashRate);
	}
	isSaveClicked = false;
}
//call back function for saveOrUpdateTrainCashRateDtls
function callSaveTrainCashRate(ajaxResp){
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		if(isNull(data.errorMsg)) {
			var trProdCatId = jQuery("#trProdCatId").val();
			var cols = jQuery("#trSplDestColCount").val();
			var rows = jQuery("#trSectorRowCount").val();
			//Header
			jQuery("#cashRateHeaderId").val(data.cashRateHeaderId);
			
			//Product
			for(var i=0; i<data.cashRateProductTOList.length; i++){
				var product = data.cashRateProductTOList[i];
				if(product.productCode == PRO_CODE_TRAIN){
				enableTabs("tabsnested",5);
				setFlags(data);
				jQuery("#trHeaderProMapId").val(product.headerProductMapId);
				jQuery("#trainMinChargeableWeight").val(product.minChargeableWeight);
				//Wt. Slab Rate
				for(var j=0; j<product.slabRateTOList.length; j++){
					var slabRate = product.slabRateTOList[j];
					//set slabRate Id.s
					setWtSlabRate(trProdCatId, parseInt(rows), parseInt(cols), slabRate);
				}
				var rowCnt = parseInt(product.specialDestTOList.length)/parseInt(cols);
				//Special Destination
				//for(var k=0; k<product.specialDestTOList.length; k++){
					var splDest = product.specialDestTOList;
					//set splDest Id.s
					setSplDestRate(trProdCatId, "TR", rowCnt, parseInt(cols), splDest,null);
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
	getDomElementById("trainOriginSectorId").disabled = true;
}


