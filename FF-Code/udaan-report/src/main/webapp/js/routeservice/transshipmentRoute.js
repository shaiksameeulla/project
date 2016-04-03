var tableRowId;
var trTO;
var idsList = "";
var delStatus = false;
var action;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
/**
 * add new row to the grid if checkFields() returns true
 */
function addTransshipmentRouteRow() {
	if (checkFields())
		addRow();
}

/**
 * Check the data has been provided for Header fields or not
 * 
 * @returns {Boolean}
 */
function checkFields() {
	var transshipmentRegion = getDomElementById("transshipmentRegionId");
	var transshipmentCity = getDomElementById("transshipmentCityId");

	if (isNull(transshipmentRegion.value)) {
		alert('Please select Transshipment Region');
		transshipmentRegion.focus();
		return false;
	}
	if (isNull(transshipmentCity.value)) {
		alert('Please select Transshipment Location');
		transshipmentCity.focus();
		return false;
	}
	return true;
}

/**
 * Check the data has been provided for Grid fields or not
 * 
 * @returns {Boolean}
 */
function checkGridValues() {

	for ( var i = 1; i < rowCount; i++) {
		if (!checkMandatory(i)) {
			return false;
		}
	}

	return true;
}

/**
 * Check the data has been provided for the fields of Grid row or not
 * 
 * @returns {Boolean}
 */
function checkMandatory(rowId) {
	var servicedRegion = getDomElementById("servicedRegionIds" + rowId);
	var servicedCity = getDomElementById("servicedCityIds" + rowId);

	
	if (!isNull(servicedRegion) && !isNull(servicedCity)) {
		if(rowId < (rowCount-1)  && ((countNo-1) != 1) && checkLastRow(rowId)){
		if( !checkServiceRegionField(rowId))
			return false;
		if(!checkServiceCityField(rowId))
			return false;
		
	}
	
	if(((countNo-1) == 1) || !checkLastRow(rowId)){
		if (isNull(servicedRegion.value) && isNull(servicedCity.value)) 
			return true;
		else{
			if( !checkServiceRegionField(rowId))
				return false;
			if(!checkServiceCityField(rowId))
				return false;
			
		}
	}
	}
	return true;
}

// Using data grid
var serialNo = 1;
var rowCount = 1;
var countNo = 1;

/**
 * This method add the new row to the Grid
 */
function addRow() {
	transshipmentNumber = "";
	servicedRegionId = "";
	servicedCityId = "";
	servicedCityList = "";

	if (!isNull(trTO)) {
		if ((!isNull(trTO[rowCount - 1]))) {
			if (!isNull(trTO[rowCount - 1].transshipmentRouteId))
				transshipmentNumber = trTO[rowCount - 1].transshipmentRouteId;
			if (!isNull(trTO[rowCount - 1].servicedRegionId))
				servicedRegionId = trTO[rowCount - 1].servicedRegionId;
			if (!isNull(trTO[rowCount - 1].servicedCityId))
				servicedCityId = trTO[rowCount - 1].servicedCityId;
			if (!isNull(trTO[rowCount - 1].servicedCityList))
				servicedCityList = trTO[rowCount - 1].servicedCityList;
		}
	}
	$('#transshipmentRouteGrid')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"   id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
									'<span id="serial'+rowCount+'">'+countNo+'</span>',
							'<select  id="servicedRegionIds'
									+ rowCount
									+ '" name="to.servicedRegionIds" value = ""  class="selectBox width145" onkeypress="return enterKeyNav(\'servicedCityIds'+ rowCount +'\',event.keyCode);" onchange="getGridStationByRegion(this.value,'
									+ rowCount + ')" onblur="checkServiceRegion(this.value,'
									+ rowCount + ')"/></select>',
							'<select  id="servicedCityIds'
									+ rowCount
									+ '" name="to.servicedCityIds" value = ""  class="selectBox width145" onchange="validateStations('
									+ rowCount
									+ ')" onblur="checkServiceCity(this.value,'
									+ rowCount + ')"/></select><input type="hidden" id="transshipmentNumber'
									+ rowCount
									+ '" name="to.transshipmentNumber" value = "'
									+ transshipmentNumber + '"/><input type="hidden" id="count'
									+ rowCount
									+ '" name="count'+rowCount+'" value = "'
									+ countNo + '"/>' ]);

	clearRegionDropDownList("servicedRegionIds" + rowCount, servicedRegionId);
	loadServicedCityDropDown("servicedCityIds" + rowCount, servicedCityList,
			servicedCityId);
	rowCount++;
	serialNo++;
	countNo++;

}


/**
 * It checks whether Serviced Region has selected or not
 * @param region
 * @param rowId
 * @returns {Boolean}
 */
function checkServiceRegion(region, rowId) {
	
	if( !checkLastRow(rowId) && isNull(region)){
		return;	
	}
	/*if(!checkServiceRegionField(rowId)){
	return false;
	}*/
	return true;
}

/**
 * It checks whether Serviced City has selected or not
 * @param city
 * @param rowId
 * @returns {Boolean}
 */
function checkServiceCity(city, rowId) {
	if( !checkLastRow(rowId) && isNull(getDomElementById("servicedCityIds"+rowId).value)){
		return;	
	}
	
	if(!checkServiceRegionField(rowId)){
	return false;
	}
	var servicedCity = getDomElementById("servicedCityIds" + rowId);
	if (isNull(servicedCity.value)) {
		alert('Row Number: ' + getDomElementById("count"+rowId).value + '  Please select Service Station');
	}
	return true;
}



/**
 * It checks whether Serviced Region has selected or not
 * @param rowId
 * @returns {Boolean}
 */
function checkServiceRegionField(rowId){
	var servicedRegion = getDomElementById("servicedRegionIds" + rowId);
	
		if (isNull(servicedRegion.value)) {
			alert('Row Number: ' + getDomElementById("count"+rowId).value + '  Please select Service Region');
				setTimeout(function() {servicedRegion.focus();}, 10);
				return false;
		}
		return true;
}

/**
 * It checks whether Serviced City has selected or not
 * @param rowId
 * @returns {Boolean}
 */
function checkServiceCityField(rowId){
	var servicedCity = getDomElementById("servicedCityIds" + rowId);
		if (isNull(servicedCity.value)) {
			alert('Row Number: ' + getDomElementById("count"+rowId).value + '  Please select Service Station');
				setTimeout(function() {servicedCity.focus();}, 10);
				return false;
		}
		return true;
}
/**
 * This method delete the rows from the grid
 */
function deleteTableRow() {
	try {
		cnt = 1;
		if(hasCheckboxChecked()){
		var table = getDomElementById("transshipmentRouteGrid");
		var tableRowCount = table.rows.length;
		idsList = "";
		for(var j=1;j<rowCount;j++)
		{
			if(!isNull(getDomElementById("chk"+j)) && getDomElementById("chk"+j).checked == true){
				if(!isNull(getDomElementById("transshipmentNumber"+j).value)){
				if(idsList.length > 0 ){
				  idsList = idsList+",";
				}
				idsList = idsList+ getDomElementById("transshipmentNumber"+j).value;
				}
			}
		}
		
		for ( var i = 0; i < tableRowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				cnt++;
				deleteRow("transshipmentRouteGrid", i - 1);
				tableRowCount--;
				i--;
			}
		}
		var  rowAssign = true;
		for ( var i = 1, j=1; i < rowCount; i++) {
			if (!isNull(getDomElementById("count"+i))) {
				rowAssign = false;
				getDomElementById("serial"+i).innerHTML = j;
				getDomElementById("count"+i).value = j;
				j++;
				countNo = j;
			}
		}
		if(rowAssign)
			countNo = 1;
		
		getDomElementById("chk0").checked = false;
		
	}else{
		alert("Please check atleast one row");
	}
	} catch (e) {
		alert(e);
	}
}

/**
 * It checks whether atleast one row has been cheked or not 
 * @returns {Boolean}
 */
function hasCheckboxChecked(){
	for(var i = 1; i<rowCount;i++){
		if(!isNull(getDomElementById("chk"+i)) && getDomElementById("chk"+i).checked == true){
			hasChecked = true;
			return true;
		}
	}
	return false;
}
/**
 * This Method Deleted the selected row from the Grid
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

/**
 * This is AJAX Call This Method send the Transshipment Route details to server
 * to save in the database
 */
function saveTransshipmentRoute() {

	var check = false;
	if (checkGridValues()) {
		for ( var i = 1; i < rowCount-1; i++) {
			if (!isNull(getDomElementById("servicedRegionIds" + i))
					&& !isNull(getDomElementById("servicedRegionIds" + i).value)){
				check = true;
				break;
			}else
				check = false;
		}
		if(delStatus)
			check = true;
		
		if (check) {
			getDomElementById("pageAction").value = action;
			getDomElementById("transshipmentIdsArrStr").value = idsList;
			flag = confirm("Do you want to Save the Transshipment Route Details");
			if (!flag) {
				return;
			}
			enableHeaderFields();
			var url = "./transshipmentRoute.do?submitName=saveTransshipmentRoute";
			ajaxCall(url, "transshipmentRouteForm", saveCallback);
		} else {
			alert("Please provide atleast one row details of Grid");
			return;
		}
	}
}

/**
 * This Method popup message whether the data has been stored in database or not
 * 
 * @param data
 */
function saveCallback(data) {
	/*if (data == "SUCCESS") {
		searchTransshipmentRoute();
		if(delStatus)
			alert("Transshipment Route has updated successfully.");
		else
			alert("Transshipment Route has created successfully.");
	} else if (data == "FAILURE") {
		disableHeaderFields();
		if(delStatus)
			alert("Transshipment Transshipment Route not updated.");
		else
			alert("Exception occurred. Transshipment Route not Created.");
	}*/

	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			disableHeaderFields();
			alert(error);
		}else {
			resp = responseText[SUCCESS_FLAG];
			if(searchTransshipmentRoute()){
				alert(resp);
			}
		}
		}
	}
	delStatus = false;
}

/**
 * Erase the Grid fields Data
 */
function deleteTableData() {
	var tableee = getDomElementById('dataTable');
	for ( var i = tableee.rows.length; i > 1; i--) {
		tableee.deleteRow(i - 1);
	}
}

/**
 * This is Ajax Call This is method get the City list based on Region
 * 
 * @param region
 * @param city
 */
function getStationByRegion(region) {

	if (!isNull(region)) {
		var url = "./transshipmentRoute.do?submitName=getStationsByRegion&regionId="
				+ region;
		ajaxCall(url, "transshipmentRouteForm", populateCity);
	} else {
		clearDropDownList("transshipmentCityId");
	}
	
}

/**
 * This method populates the City Dropdown after get the response from
 * getStationByRegionId() AJAX Call
 * 
 * @param resp
 */
function populateCity(resp) {
	/*stationList = jsonJqueryParser(resp);
	station = "transshipmentCityId";
	clearDropDownList(station);
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown(station, stationList[i].cityName,
					stationList[i].cityId);
		}
	}*/
	
	station = "transshipmentCityId";
	clearDropDownList(station);
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			stationList = responseText[SUCCESS_FLAG];			
			if (!isNull(stationList)) {
				var stLen = stationList.length;
				for ( var i = 0; i < stLen; i++) {
					addOptionTODropDown(station, stationList[i].cityName,
							stationList[i].cityId);
				}
			}
		}
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error.");
	}

}

/**
 * This is Ajax Call This is method get the City list of the grid row based on
 * Region
 * 
 * @param region
 * @param city
 */
function getGridStationByRegion(region, rowId) {
	tableRowId = rowId;
	var check = true;
	if (!isNull(region)) {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("servicedRegionIds" + i))) {
				if ((rowId != i)
						&& (region == getDomElementById("servicedRegionIds" + i).value)) {
					addGridOptionTODropDown("servicedCityIds" + rowId, "",
							"servicedCityIds" + i);
					check = false;
					break;
				}
			}
		}
		if (check) {
			var url = "./transshipmentRoute.do?submitName=getStationsByRegion&regionId="
					+ region;
			ajaxCall(url, "transshipmentRouteForm", populateGridCity);
		}
	} else {
		clearDropDownList("servicedCityIds" + rowId);
	}
}

/**
 * This method populates the City Drop down of the Grid row after get the
 * response from getGridStationByRegion() AJAX Call
 * 
 * @param resp
 */
function populateGridCity(resp) {
	/*stationList = jsonJqueryParser(resp);
	station = "servicedCityIds" + tableRowId;
	clearDropDownList(station);
	obj = getDomElementById(station);
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown(station, stationList[i].cityName,
					stationList[i].cityId);
		}
	}*/

	station = "servicedCityIds" + tableRowId;
	clearDropDownList(station);
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			stationList = responseText[SUCCESS_FLAG];			
			if (!isNull(stationList)) {
				var stLen = stationList.length;
				for ( var i = 0; i < stLen; i++) {
					addOptionTODropDown(station, stationList[i].cityName,
							stationList[i].cityId);
				}
			}
		}
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error.");
	}
	
}

/**
 * This is AJAX Call This is method get the Transshipment Route details by the
 * search parameters.
 */
function searchTransshipmentRoute() {
	if (checkFields()) {
		clearGrid();
		getDomElementById("demo").style.display = '';
		getDomElementById("button_adddelete").style.display = '';
		getDomElementById("button_savecancel").style.display = '';

		var transshipmentCityId = getDomElementById("transshipmentCityId").value;

		var url = "./transshipmentRoute.do?submitName=getTransshipmentRoute&transshipmentCityId="
				+ transshipmentCityId;

		ajaxCall(url, "transshipmentRouteForm",
				populateTransshipmentRouteDetails);
	}
	return true;
}

/**
 * This method populates the Transshipment route details in the screen after get
 * the response from searchTransshipmentRoute() AJAX Call
 * 
 * @param data
 */
function populateTransshipmentRouteDetails(data) {
	
	delStatus = false;
	action = "save";
	if(countNo != 1)
		countNo = 1;
	
	
	/*disableHeaderFields();
	trTO = jsonJqueryParser(data);
	if(countNo != 1)
		countNo = 1;
	delStatus = false;
	if (!isNull(trTO)) {
		delStatus = true;
		for ( var i = 0; i < trTO.length; i++)
			if(!isNull(trTO[i].servicedRegionId)){
				addRow();
				}

	} else
		addRow();*/
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			trTO = responseText[SUCCESS_FLAG];
			disableHeaderFields();
			if (!isNull(trTO)) {
				
				var trLen = trTO.length;
				if(trLen >0){
				delStatus = true;
				action = "update";
				}
				for ( var i = 0; i < trLen; i++){
					if(!isNull(trTO[i].servicedRegionId)){
						addRow();
					}
				}
			} else{
				addRow();
			}
		}
		}else{
			alert("Details are not fecthed due to Business Logic Error");
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error");
	}
}

/**
 * This method deletes the rows of the grid
 */
function clearGrid() {

	if (rowCount > 1) {
		var table = getDomElementById("transshipmentRouteGrid");
		var tableRowCount = table.rows.length;
		clearGridFieldsData();
		for ( var i = 1; i < tableRowCount; i++) {
			deleteRow("transshipmentRouteGrid", i - 1);
			tableRowCount--;
			i--;
		}
		rowCount = 1;
		serialNo = 1;
	}
}

/**
 * This method delete the Grid Fields data
 * 
 * @param tableId
 * @param rowIndex
 */
function clearGridFieldsData() {
	for ( var i = 1; i < rowCount; i++) {
		if (!isNull(getDomElementById("transshipmentNumber" + i))) {
			getDomElementById("transshipmentNumber" + i).value = "";
			clearDropDownList("servicedRegionIds" + i);
			clearDropDownList("servicedCityIds" + i);
		}
	}
}

/**
 * This method disabled the Header fields to prevent data changes
 */
function disableHeaderFields() {
	getDomElementById("transshipmentRegionId").disabled = true;
	getDomElementById("transshipmentCityId").disabled = true;
}

/**
 * This method enabled the Header fields to do data changes
 */
function enableHeaderFields() {
	getDomElementById("transshipmentRegionId").disabled = false;
	getDomElementById("transshipmentCityId").disabled = false;
}

/**
 * It deletes the Dropdown values of the fields
 * 
 * @param selectId
 */
function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, selectOption, "");
}

/**
 * Clears the dropdown values of the Region
 * 
 * @param selectId
 * @param servicedRegionId
 */
function clearRegionDropDownList(selectId, servicedRegionId) {
	getDomElementById(selectId).options.length = 0;
	addGridOptionTODropDown(selectId, servicedRegionId, 'transshipmentRegionId');
}

/**
 * Add the Values to Drop down
 * 
 * @param selectId
 * @param servicedRegionId
 * @param optionId
 */
function addGridOptionTODropDown(selectId, servicedRegionId, optionId) {

	var obj = getDomElementById(selectId);
	obj.options.length = 0;
	var optionsList = getDomElementById(optionId).options;
	for ( var i = 0; i < optionsList.length; i++) {
		opt = document.createElement('OPTION');
		opt.value = optionsList[i].value;
		opt.text = optionsList[i].text;
		obj.options.add(opt);
	}
	obj.value = servicedRegionId;
}

/**
 * Add the values to Grid row city Drop down
 * 
 * @param selectId
 * @param servicedCityList
 * @param servicedCityId
 */
function loadServicedCityDropDown(selectId, servicedCityList, servicedCityId) {

	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (servicedCityList.length > 0) {
		for ( var i = 0; i < servicedCityList.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = servicedCityList[i].cityId;
			opt.text = servicedCityList[i].cityName;
			obj.options.add(opt);
		}
		obj.value = servicedCityId;
	} else {
		//addOptionTODropDown(selectId, selectOption, "");
	}
}

/**
 * It adds the values to dropdown
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * It clears the data of the Header fieds
 */
function clearHeaderFieldsData() {
	getDomElementById("transshipmentRegionId").value = "";
	clearDropDownList("transshipmentCityId");
}

/**
 * It clears the data of the screen (Header fields data and Grid fields Data)
 */
function cancelTransshipmentRoute() {
	flag = confirm("Do you want to Clear the Details");
	if (!flag) {
		return;
	}
	document.transshipmentRouteForm.action = "./transshipmentRoute.do?submitName=viewTransshipmentRoute";
	document.transshipmentRouteForm.submit();

}

/**
 * It checks whether the Transshipment station and Serviced Station of Grid are
 * same or not It checks whether the Serviced Stations of Grid rows which have
 * same Region are same or not
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateStations(rowId) {
	addR = true;
	obj2 = getDomElementById("servicedCityIds" + rowId);
	objVal2 = getDomElementById("servicedCityIds" + rowId).value;
	reg2 = getDomElementById("servicedRegionIds" + rowId);
	regVal2 = getDomElementById("servicedRegionIds" + rowId).value;
	obj3 = getDomElementById("transshipmentCityId");
	objVal3 = getDomElementById("transshipmentCityId").value;
	reg3 = getDomElementById("transshipmentRegionId");
	regVal3 = getDomElementById("transshipmentRegionId").value;
	if ((!isNull(obj2) && !isNull(obj3)) && (regVal2 == regVal3)
			&& (objVal2 == objVal3)) {
		alert("Serviced Station row number " + getDomElementById("count"+rowId).value
				+ " and Transshipment Location should not be same");
		obj2.value = "";
		setTimeout(function() {
			obj2.focus();
		}, 10);
		addR = false;
		return false;
	} else {
		for ( var i = 1; i < rowCount; i++) {
			if (i != rowId) {
				obj1 = getDomElementById("servicedCityIds" + i);
				reg1 = getDomElementById("servicedRegionIds" + i);
				if (!isNull(obj1) && !isNull(reg1)) {
					objVal1 = getDomElementById("servicedCityIds" + i).value;
					regVal1 = getDomElementById("servicedRegionIds" + i).value;
					if ((regVal1 == regVal2) && (objVal1 == objVal2)) {
						alert("Serviced Stations row number " + getDomElementById("count"+rowId).value + " and "
								+ getDomElementById("count"+i).value
								+ " are same. Stations should not be same");
						obj2.value = "";
						setTimeout(function() {
							obj2.focus();
						}, 10);
						addR = false;
						return false;
					}
				}
			}
		}
	}
	if(addR){
		if(!checkLastRow(rowId)){
		addRow();
		if(!isNull(getDomElementById("servicedRegionIds" + (rowCount-1)))){
		getDomElementById("servicedRegionIds" + (rowCount-1)).focus();
		}
		}
	}
	return true;
}

/**
 * It selects or unselects the check box of all rows of the Grid
 */
function gridCheckAll() {

	if (getDomElementById("chk0").checked == true) {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = true;
			}
		}
	} else {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = false;
			}
		}
	}
}

function checkLastRow(rowId){
	if(getDomElementById("count" + rowId).value == (countNo-1)){
		return false;
	}
	
return true;
}