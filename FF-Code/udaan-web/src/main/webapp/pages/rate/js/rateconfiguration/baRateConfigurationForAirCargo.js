var airCargoWtSlabTable = "airCargoWtSlabTable";
var airCargoSplDestTable = "airCargoSplDestTable";

$(document).ready(function() {
	var oTable = $('#' + airCargoWtSlabTable).dataTable({
		"sScrollY" : "70",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
});

$(document).ready(function() {
	var oTable = $('#' + airCargoSplDestTable).dataTable({
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
});

var rowCountForAc = 1;
var cArr = new Array();

/**
 * To add air cargo special destination row on load - first time
 */
function addAirCargoSpecialDestinationRow() {
	addAdditionalAirCargoColumns();
	$('#' + airCargoSplDestTable).dataTable().fnAddData(cArr);
	loadStateDropDown("ac_stateId" + rowCountForAc, "", statesList);
	clearDropDownList("ac_cityIds" + rowCountForAc);
	rowCountForAc++;
	disableTabs();
}

/**
 * To air cargo train special destination row dynamically - adding rows
 * 
 * @param index
 * @param prod
 */
function addAirCargoSpecialDestinationRow1(index, prod) {
	if (index == (rowCountForAc - 1) && stateValidation(index, prod)) {
		addAdditionalAirCargoColumns();
		$('#' + airCargoSplDestTable).dataTable().fnAddData(cArr);
		loadStateDropDown("ac_stateId" + rowCountForAc, "", statesList);
		clearDropDownList("ac_cityIds" + rowCountForAc);
		rowCountForAc++;
	}
}

/**
 * add addition Air cargo column
 */
function addAdditionalAirCargoColumns() {
	cArr[0] = '<select  id="ac_stateId'
			+ rowCountForAc
			+ '" name="to.baAirCargoSlabRateTO.stateId" class="selectBox width155" onchange="getCityByState(this,'
			+ rowCountForAc + ',\'ac_cityIds\');"/>';
	cArr[1] = '<select id="ac_cityIds'
			+ rowCountForAc
			+ '" name="to.baAirCargoSlabRateTO.cityIds" class="selectBox width155" onchange="validateCities(\'AC\','
			+ rowCountForAc + ',\'ac_cityIds\',\'ac_stateId\');"/>';
	
	cArr[2] = '<input type="text" name = "to.baAirCargoSlabRateTO.specialDestinationRate" id="ac_specialDestinationRate'
			+ rowCountForAc
			+ '2" class="txtbox width90" onfocus="validateStates(\'AC\','
			+ rowCountForAc
			+ ',\'ac_cityIds\',\'ac_stateId\');"/>'
			+ '<input type="hidden" id="ac_specialDestinationId'
			+ rowCountForAc
			+ '2" name="to.baAirCargoSlabRateTO.specialDestinationId" />';

	var airCargoSplDestcoloumCount = document
			.getElementById("airCargoSplDestcoloumCount").value;
	airCargoSplDestcoloumCount = parseInt(airCargoSplDestcoloumCount) + 3;
	for ( var i = 3; i < airCargoSplDestcoloumCount; i++) {
		if (i + 1 == airCargoSplDestcoloumCount) {
			cArr[i] = '<input type="text" name = "to.baAirCargoSlabRateTO.specialDestinationRate" id="ac_specialDestinationRate'
					+ rowCountForAc
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'AC\','
					+ rowCountForAc
					+ ',\'ac_cityIds\',\'ac_stateId\');" onblur="addAirCargoSpecialDestinationRow1('
					+ rowCountForAc
					+ ',\'AC\');"/>'
					+ '<input type="hidden" id="ac_specialDestinationId'
					+ rowCountForAc
					+ i
					+ '" name="to.baAirCargoSlabRateTO.specialDestinationId" />';
		} else {
			cArr[i] = '<input type="text" name = "to.baAirCargoSlabRateTO.specialDestinationRate" id="ac_specialDestinationRate'
					+ rowCountForAc
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'AC\','
					+ rowCountForAc
					+ ',\'ac_cityIds\',\'ac_stateId\');"/>'
					+ '<input type="hidden" id="ac_specialDestinationId'
					+ rowCountForAc
					+ i
					+ '" name="to.baAirCargoSlabRateTO.specialDestinationId" />';
		}
	}
}
