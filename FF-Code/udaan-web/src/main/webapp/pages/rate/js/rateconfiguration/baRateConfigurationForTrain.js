var trainWtSlabTable = "trainWtSlabTable";
var trainSplDestTable = "trainSplDestTable";

$(document).ready(function() {
	var oTable = $('#' + trainWtSlabTable).dataTable({
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
	var oTable = $('#' + trainSplDestTable).dataTable({
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

var rowCountForTr = 1;
var d = new Array();

/**
 * To add train special destination row on load - first time
 */
function addTrainSpecialDestinationRow() {
	addAdditionalTrainColumns();
	$('#' + trainSplDestTable).dataTable().fnAddData(d);
	loadStateDropDown("tr_stateId" + rowCountForTr, "", statesList);
	clearDropDownList("tr_cityIds" + rowCountForTr);
	rowCountForTr++;
	disableTabs();
}

/**
 * To add train special destination row dynamically - adding rows
 * 
 * @param index
 * @param prod
 */
function addTrainSpecialDestinationRow1(index, prod) {
	if (index == (rowCountForTr - 1) && stateValidation(index, prod)) {
		addAdditionalTrainColumns();
		$('#' + trainSplDestTable).dataTable().fnAddData(d);
		loadStateDropDown("tr_stateId" + rowCountForTr, "", statesList);
		clearDropDownList("tr_cityIds" + rowCountForTr);
		rowCountForTr++;
	}
}

/**
 * Add addition train column
 */
function addAdditionalTrainColumns() {
	d[0] = '<select  id="tr_stateId'
			+ rowCountForTr
			+ '" name="to.baTrainSlabRateTO.stateId" class="selectBox width155" onchange="getCityByState(this,'
			+ rowCountForTr + ',\'tr_cityIds\');"/>';
	d[1] = '<select id="tr_cityIds'
			+ rowCountForTr
			+ '" name="to.baTrainSlabRateTO.cityIds" class="selectBox width155" onchange="validateCities(\'TR\','
			+ rowCountForTr + ',\'tr_cityIds\',\'tr_stateId\');"/>';
	d[2] = '<input type="text" name="to.baTrainSlabRateTO.specialDestinationRate" id="tr_specialDestinationRate'
			+ rowCountForTr
			+ '2" class="txtbox width90" onfocus="validateStates(\'TR\','
			+ rowCountForTr
			+ ',\'tr_cityIds\',\'tr_stateId\');"/>'
			+ '<input type="hidden" id="tr_specialDestinationId'
			+ rowCountForTr
			+ '2" name="to.baTrainSlabRateTO.specialDestinationId" />';

	var trainSplDestcoloumCount = document
			.getElementById("trainSplDestcoloumCount").value;
	trainSplDestcoloumCount = parseInt(trainSplDestcoloumCount) + 3;
	for ( var i = 3; i < trainSplDestcoloumCount; i++) {
		if (i + 1 == trainSplDestcoloumCount) {
			d[i] = '<input type="text" name = "to.baTrainSlabRateTO.specialDestinationRate" id="tr_specialDestinationRate'
					+ rowCountForTr
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'TR\','
					+ rowCountForTr
					+ ',\'tr_cityIds\',\'tr_stateId\');" onblur="addTrainSpecialDestinationRow1('
					+ rowCountForTr
					+ ',\'TR\');"/>'
					+ '<input type="hidden" id="tr_specialDestinationId'
					+ rowCountForTr
					+ i
					+ '" name="to.baTrainSlabRateTO.specialDestinationId" />';
		} else {
			d[i] = '<input type="text" name = "to.baTrainSlabRateTO.specialDestinationRate" id="tr_specialDestinationRate'
					+ rowCountForTr
					+ i
					+ '" class="txtbox width90" onfocus="validateStates(\'TR\','
					+ rowCountForTr
					+ ',\'tr_cityIds\',\'tr_stateId\');"/>'
					+ '<input type="hidden" id="tr_specialDestinationId'
					+ rowCountForTr
					+ i
					+ '" name="to.baTrainSlabRateTO.specialDestinationId" />';
		}
	}
}
