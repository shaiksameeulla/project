var transportModeCode;
var tableRowId;
var tableId;
var prTO;
var edField = "expDepartureTime";
var eaField = "expArrivalTime";
var station;
var rCount;
var rowfill = false;
var c1 = false;
var c2 = false;

var c3 = false;
var c4 = false;
var delStatus = false;
var idsList = "";
var fieldStatus = true;
var trans1 = true;
var trans2 = true;
var trans3 = true;
var trans4 = true;
var trans = true;

var tra1 = true;
var tra2 = true;
var tra3 = true;
var tra4 = true;
var tra = true;
var action;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
/**
 * add new row to the grid if checkFields() returns true
 */
function addPureRouteRow() {
	if (checkFields())
		addRow();
}

/**
 * Check the data has been provided for Header fields or not
 * 
 * @returns {Boolean}
 */
function checkFields() {
	var originRegionDom = getDomElementById("originRegion");
	var destinationRegionDom = getDomElementById("destinationRegion");
	var destinationStationDom = getDomElementById("destinationStationId");
	var originStationDom = getDomElementById("originStationId");
	var transportModeDom = getDomElementById("transportMode");
	if (isNull(originRegionDom.value)) {
		alert('Please select Origin Region');
		originRegionDom.focus();
		return false;
	}
	if (isNull(originStationDom.value)) {
		alert('Please select Origin Station');
		originStationDom.focus();
		return false;
	}
	if (isNull(destinationRegionDom.value)) {
		alert('Please select Destination Region');
		destinationRegionDom.focus();
		return false;
	}
	if (isNull(destinationStationDom.value)) {
		alert('Please select Destination Station');
		destinationStationDom.focus();
		return false;
	}
	if (isNull(transportModeDom.value)) {
		alert('Please select Tranpsort Mode');
		transportModeDom.focus();
		return false;
	}
	if ((originStationDom.value) == (destinationStationDom.value)) {
		alert('Origin and Destination Stations should not be same');
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

	for ( var i = 1; i < rCount; i++) {
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
	var transportNumber = getDomElementById("transportNumber" + rowId);
	var transportName = getDomElementById("transportName" + rowId);
	var expDepartureTime = getDomElementById("expDepartureTime" + rowId);
	var expArrivalTime = getDomElementById("expArrivalTime" + rowId);
//
	//if (!(isNull(transportNumber) && isNull(expDepartureTime) && isNull(expArrivalTime))) {

		/*if ((transportModeCode == trainCode) || (transportModeCode == roadCode)) {
			if ((isNull(transportNumber.value) && isNull(transportName.value)
					&& isNull(expDepartureTime.value) && isNull(expArrivalTime.value))) {
				return true;
			}
		} else if (transportModeCode == airCode) {
			if ((isNull(transportNumber.value)
					&& isNull(expDepartureTime.value) && isNull(expArrivalTime.value))) {
				return true;
			}
		}*/
	if (!(isNull(transportNumber) && isNull(expDepartureTime) && isNull(expArrivalTime))){
		if(rowId < (rCount-1) && (countNo-1) != 1 && checkLastRow(rowId)){
			
		if( !checkTransportNumberField(rowId))
			return false;
		else if(!isNull(transportName) && !checkTransportNameField(rowId))
			return false;
		else if( !checkExpDepartureTimeField(rowId))
			return false;
		else if(!checkExpArrivalTimeField(rowId))
			return false;
			
		//}if(rowId == (rCount-1) || ((countNo-1) == 1) || !checkLastRow(rowId)){
		
		}if(((countNo-1) == 1) || !checkLastRow(rowId)){
		
			if (isNull(transportNumber.value) && isNull(expDepartureTime.value) && isNull(expArrivalTime.value)){
				
				return true;
			}else{
				
				if(!checkTransportNumberField(rowId))
					return false;
				else if(!isNull(transportName) && !checkTransportNameField(rowId))
					return false;
				else if(!checkExpDepartureTimeField(rowId))
					return false;
				else if(!checkExpArrivalTimeField(rowId))
					return false;
			}
		}
	}
		rowfill = true;
	//}
	return true;
}


/**
 * It checks whether Train/Vehicle/Flight Number has entered or not 
 * @param rowId
 * @returns {Boolean}
 */
function checkTransportNumberField(rowId){
	var transportNumber = getDomElementById("transportNumber" + rowId);
		
	
	//	if (checkLastRow("transportNumber",rowId) && isNull(transportNumber.value)) {
	if (isNull(transportNumber.value)) {
			if (transportModeCode == trainCode){ 
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Train Number');
				setTimeout(function() {getDomElementById("transNumber"+rowId).focus();}, 10);
			}
			else if (transportModeCode == roadCode){
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Vehicle Number');
				if(isNull(getDomElementById("transNumber1" + rowId).value))
					setTimeout(function() {getDomElementById("transNumber1"+rowId).focus();}, 10);
				else if(isNull(getDomElementById("transNumber2" + rowId).value))
					setTimeout(function() {getDomElementById("transNumber2"+rowId).focus();}, 10);
				else if(isNull(getDomElementById("transNumber3" + rowId).value))
					setTimeout(function() {getDomElementById("transNumber3"+rowId).focus();}, 10);
				else if(isNull(getDomElementById("transNumber4" + rowId).value))
					setTimeout(function() {getDomElementById("transNumber4"+rowId).focus();}, 10);
			}
			else if (transportModeCode == airCode){ 
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Flight Number');
				setTimeout(function() {getDomElementById("transNumber"+rowId).focus();}, 10);
			}
			
			//getDomElementById("transportNumber" + rowId).focus();
			return false;
		}
		return true;
}	
		
/**
 * It checks whether Train Name/Vehicle Type has entered or not
 * @param rowId
 * @returns {Boolean}
 */
function checkTransportNameField(rowId){
	var transportName = getDomElementById("transportName" + rowId);
	
	if(!isNull(transportName)){
	
		if (transportModeCode != airCode && isNull(transportName.value)) {
			//if (checkLastRow("transportName",rowId) && transportModeCode == trainCode) {
			if (transportModeCode == trainCode) {
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Train Namer');
				setTimeout(function() {transportName.focus();}, 10);
				return false;
			}//else if (checkLastRow("transportName",rowId) && transportModeCode == roadCode){
			else if (transportModeCode == roadCode){
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Vehicle Type');
				setTimeout(function() {transportName.focus();}, 10);
				return false;
			}
			
		}
	}
		return true;
}


/**
 * It checks whether Exp Departure Time has entered or not
 * @param rowId
 * @returns {Boolean}
 */
function checkExpDepartureTimeField(rowId){
	var expDepartureTime = getDomElementById("expDepartureTime" + rowId);
	
	//if (checkLastRow("expDepartureTime",rowId) && isNull(expDepartureTime.value)) {
	if ( isNull(expDepartureTime.value)) {
		alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Exp DepartureTime');
		setTimeout(function() {expDepartureTime.focus();}, 10);
		return false;
	}
		return true;
}		


/**
 * It checks whether Exp Arrival Time has entered or not
 * @param rowId
 * @returns {Boolean}
 */
function checkExpArrivalTimeField(rowId){
	var expArrivalTime = getDomElementById("expArrivalTime" + rowId);
	//if (checkLastRow("expArrivalTime",rowId) && isNull(expArrivalTime.value)) {
	if (isNull(expArrivalTime.value)) {
		alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter Exp ArrivalTime');
		setTimeout(function() {expArrivalTime.focus();}, 10);
		return false;
	}
		return true;
}
/**
 * This Method validates the time format has given correct or not for the Time
 * fields. If the data is given wrong then it popups a message
 * 
 * @param columnId
 * @param rowId
 * @param msg
 * @returns {Boolean}
 */
function validateTime(columnId, rowId, msg) {
	var timeObj = getDomElementById(columnId + rowId);
	if (!isNull(timeObj)) {
		var timeDetails = timeObj.value;
		
		if (timeDetails.indexOf(":") > 0) {
			var time = timeDetails.split(":");
			if(time.length >2){
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter the ' + msg
						+ ' in HH:MM format');
				setTimeout(function() {
					timeObj.focus();
				}, 10);
				c3 = true;
				return false;
			}
				
			if(time[0].length > 1){
				
			if (!(parseInt(time[0], 10) >= 0 && parseInt(time[0], 10) < 24)) {
				
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter ' + msg
						+ ' Hours b/w 00 to 23');
				setTimeout(function() {
					timeObj.focus();
				}, 10);
				c3 = true;
				return false;
			} else{
				
				if(time[1].length > 1){
				if (!(parseInt(time[1], 10) >= 0 && parseInt(time[1], 10) < 60)) {
					
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter ' + msg
						+ ' Minutes b/w 00 to 59');
				setTimeout(function() {
					timeObj.focus();
				}, 10);
				c3 = true;
				return false;
				}
			 
				}else{
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter ' + msg
						+ ' Minutes in MM format');
				setTimeout(function() {
					timeObj.focus();
				}, 10);
				c3 = true;
				return false;
			}
			}
			}else{
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter ' + msg
						+ ' Hours in HH format');
				setTimeout(function() {
					timeObj.focus();
				}, 10);
				c3 = true;
				return false;
			}
		} else {
			alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please enter the ' + msg
					+ ' in HH:MM format');
			setTimeout(function() {
				timeObj.focus();
			}, 10);
			c3 = true;
			return false;
		}
	}
	c3 = false;
	return true;
}

// Using data grid
var serialNo = 1;
var gridSerialNo = 1;
var rowCount = 1;
var gridRowCount = 1;
var countNo = 1;

/**
 * This method add the new row to the Grid
 */
function addRow() {
	transportNumber = "";
	expDepTime = "";
	expArrTime = "";
	transportName = "";
	tripNumber = "";
	transportId = "";
	transporterId = "";
	transNumber1 = "";
	transNumber2 = "";
	transNumber3 = "";
	transNumber4 = "";

	if (transportModeCode == airCode) {

		if ((!isNull(prTO))) {
			if (!isNull(prTO.transportNumber[rowCount - 1])) {
				transportNumber = prTO.transportNumber[rowCount - 1];
			}
			if (!isNull(prTO.expDepartureTime[rowCount - 1]))
				expDepTime = prTO.expDepartureTime[rowCount - 1];
			if (!isNull(prTO.expArrivalTime[rowCount - 1]))
				expArrTime = prTO.expArrivalTime[rowCount - 1];
			if (!isNull(prTO.tripNumber[rowCount - 1]))
				tripNumber = prTO.tripNumber[rowCount - 1];
			if (!isNull(prTO.transportId[rowCount - 1]))
				transportId = prTO.transportId[rowCount - 1];
			if (!isNull(prTO.transporterId[rowCount - 1]))
				transporterId = prTO.transporterId[rowCount - 1];
		}

		$('#pureRouteGrid')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox"   id="chk' + rowCount
										+ '" name="chkBoxName" value=""/>',
										'<span id="serial'+rowCount+'">'+countNo+'</span>',
								'<input type="text" id="transNumber'
										+ rowCount
										+ '" name="transNumber" value = "'
										+ transportNumber
										+ '" size="10" class="txtbox" onkeypress="return alphaNumericAndEnterKeyNav(event,'+rowCount+',\'transNumber\',\'expDepartureTime\');" onblur="checkTransportNo(this.value,'
										+ rowCount + ');"/><input type="hidden" id="transportNumber'
										+ rowCount
										+ '" name="to.transportNumber" value = "'
										+ transportNumber + '"/>',
								'<input type="text" id="expDepartureTime'
										+ rowCount
										+ '" name="to.expDepartureTime" value = "'
										+ expDepTime
										+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expDepartureTime\',\'expArrivalTime\','+ rowCount +');" onblur="checkDepartureTime(this.value,'
										+ rowCount + ')"/>',
								'<input type="text" id="expArrivalTime'
										+ rowCount
										+ '" name="to.expArrivalTime" value = "'
										+ expArrTime
										+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expArrivalTime\',\'expArrivalTime\','+ rowCount +');" onblur="checkArrivalTime(this.value,'
										+ rowCount
										+ ')"/><input type="hidden" id="tripNumber'
										+ rowCount
										+ '" name="to.tripNumber" value = "'
										+ tripNumber
										+ '"/><input type="hidden" id="transportId'
										+ rowCount
										+ '" name="to.transportId" value = "'
										+ transportId
										+ '"/><input type="hidden" id="transporterId'
										+ rowCount
										+ '" name="to.transporterId" value = "'
										+ transporterId + '"/><input type="hidden" id="count'
										+ rowCount
										+ '" name="count'+rowCount+'" value = "'
										+ countNo + '"/>' ]);

		rowCount++;
		serialNo++;
		countNo++;
		rCount = rowCount;

	} else {
		if (!isNull(prTO)) {
			if (!isNull(prTO.transportNumber[gridRowCount - 1])) {
				transportNumber = prTO.transportNumber[gridRowCount - 1];
			}
			if (!isNull(prTO.expDepartureTime[gridRowCount - 1]))
				expDepTime = prTO.expDepartureTime[gridRowCount - 1];
			if (!isNull(prTO.expArrivalTime[gridRowCount - 1]))
				expArrTime = prTO.expArrivalTime[gridRowCount - 1];
			if (!isNull(prTO.transportName[gridRowCount - 1]))
				transportName = prTO.transportName[gridRowCount - 1];
			if (!isNull(prTO.tripNumber[gridRowCount - 1]))
				tripNumber = prTO.tripNumber[gridRowCount - 1];
			if (!isNull(prTO.transportId[gridRowCount - 1]))
				transportId = prTO.transportId[gridRowCount - 1];
			if (!isNull(prTO.transporterId[gridRowCount - 1]))
				transporterId = prTO.transporterId[gridRowCount - 1];
		}
		
		if (transportModeCode == trainCode) {
		$('#pureRouteGrid1')
				.dataTable()
				.fnAddData(
						[
								'<input type="checkbox"   id="chk'
										+ gridRowCount
										+ '" name="chkBoxName" value=""/>',
										'<span id="serial'+gridRowCount+'">'+countNo+'</span>',
								'<input type="text" id="transNumber'
										+ gridRowCount
										+ '" name="transNumber" value = "'
										+ transportNumber
										+ '" size="5" class="txtbox" onkeypress="return numericAndEnterKeyNav(event,'+ gridRowCount+',\'transNumber\',\'transportName\');" onblur="checkTransportNo(this.value,'
										+ gridRowCount +');"/><input type="hidden" id="transportNumber'
										+ gridRowCount
										+ '" name="to.transportNumber" value = "'
										+ transportNumber + '"/>',
								'<input type="text" id="transportName'
										+ gridRowCount
										+ '" name="to.transportName" value = "'
										+ transportName
										+ '" size="15" class="txtbox" onkeypress="return enterKeyNav(\'expDepartureTime'
										+ gridRowCount +'\',event.keyCode);" onblur="checkTransportName(this.value,'
										+ gridRowCount + ')"/>',
								'<input type="text" id="expDepartureTime'
										+ gridRowCount
										+ '" name="to.expDepartureTime" value = "'
										+ expDepTime
										+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expDepartureTime\',\'expArrivalTime\','+ gridRowCount +');" onblur="checkDepartureTime(this.value,'
										+ gridRowCount + ')"/>',
								'<input type="text" id="expArrivalTime'
										+ gridRowCount
										+ '" name="to.expArrivalTime"  value = "'
										+ expArrTime
										+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expArrivalTime\',\'expArrivalTime\','+ gridRowCount +');" onblur="checkArrivalTime(this.value,'
										+ gridRowCount
										+ ')"/><input type="hidden" id="tripNumber'
										+ gridRowCount
										+ '" name="to.tripNumber" value = "'
										+ tripNumber
										+ '"/><input type="hidden" id="transportId'
										+ gridRowCount
										+ '" name="to.transportId" value = "'
										+ transportId
										+ '"/><input type="hidden" id="transporterId'
										+ gridRowCount
										+ '" name="to.transporterId" value = "'
										+ transporterId + '"/><input type="hidden" id="count'
										+ gridRowCount
										+ '" name="count'+gridRowCount+'" value = "'
										+ countNo + '"/>']);
		}
		else if (transportModeCode == roadCode) {
			
			if(!isNull(transportNumber)){
				transNo = transportNumber.split("-");
				transNumber1 = transNo[0];
				transNumber2 = transNo[1];
				transNumber3 = transNo[2];
				transNumber4 = transNo[3];
			}
				$('#pureRouteGrid1')
						.dataTable()
						.fnAddData(
								[
										'<input type="checkbox"   id="chk'
												+ gridRowCount
												+ '" name="chkBoxName" value=""/>',
												'<span id="serial'+gridRowCount+'">'+countNo+'</span>',
										'<input type="text" id="transNumber1'
												+ gridRowCount
												+ '" name="transNumber1" value = "'
												+ transNumber1
												+ '" size="2" class="txtbox" onkeypress="return alphaAndEnterKeyNav(event,'+gridRowCount+',\'transNumber1\',\'transNumber2\');" onblur="validateTrans1FieldLength(this.value,\'transNumber1\','
												+ gridRowCount +','+2+', \'Vehicle\'); " />-<input type="text" id="transNumber2'
												+ gridRowCount
												+ '" name="transNumber2" value = "'
												+ transNumber2
												+ '" size="2" class="txtbox" onkeypress="return numericAndEnterKeyNav(event,'+gridRowCount+',\'transNumber2\',\'transNumber3\');" onblur="validateTrans2FieldLength(this.value,\'transNumber2\','
												+ gridRowCount +','+2+', \'Vehicle\'); "/>-<input type="text" id="transNumber3'
												+ gridRowCount
												+ '" name="transNumber3" value = "'
												+ transNumber3
												+ '" size="2" class="txtbox" onkeypress="return alphaAndEnterKeyNav(event,'+gridRowCount+',\'transNumber3\',\'transNumber4\');" onblur="validateTrans3FieldLength(this.value,\'transNumber3\','
												+ gridRowCount +','+2+', \'Vehicle\'); "/>-<input type="text" id="transNumber4'
												+ gridRowCount
												+ '" name="transNumber4" value = "'
												+ transNumber4
												+ '" size="4" class="txtbox" onkeypress="return numericAndEnterKeyNav(event,'+gridRowCount+',\'transNumber4\',\'transportName\');" onblur=" validateTrans4FieldLength(this.value,\'transNumber4\','
												+ gridRowCount +','+4+', \'Vehicle\'); "/><input type="hidden" id="transportNumber'
												+ gridRowCount
												+ '" name="to.transportNumber" value = "'
												+ transportNumber + '"/>',
										'<input type="text" id="transportName'
												+ gridRowCount
												+ '" name="to.transportName" value = "'
												+ transportName
												+ '" size="15" class="txtbox" onkeypress="return enterKeyNav(\'expDepartureTime'
												+ gridRowCount +'\',event.keyCode);" onblur="checkTransportName(this.value,'
												+ gridRowCount + ')"/>',
										'<input type="text" id="expDepartureTime'
												+ gridRowCount
												+ '" name="to.expDepartureTime" value = "'
												+ expDepTime
												+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expDepartureTime\',\'expArrivalTime\','+ gridRowCount +');" onblur="checkDepartureTime(this.value,'
												+ gridRowCount + ')"/>',
										'<input type="text" id="expArrivalTime'
												+ gridRowCount
												+ '" name="to.expArrivalTime"  value = "'
												+ expArrTime
												+ '" size="10" class="txtbox" onkeypress="return numericColAndEnterKeyNav(event,\'expArrivalTime\',\'expArrivalTime\','+ gridRowCount +');" onblur="checkArrivalTime(this.value,'
												+ gridRowCount
												+ ')"/><input type="hidden" id="tripNumber'
												+ gridRowCount
												+ '" name="to.tripNumber" value = "'
												+ tripNumber
												+ '"/><input type="hidden" id="transportId'
												+ gridRowCount
												+ '" name="to.transportId" value = "'
												+ transportId
												+ '"/><input type="hidden" id="transporterId'
												+ gridRowCount
												+ '" name="to.transporterId" value = "'
												+ transporterId + '"/><input type="hidden" id="count'
												+ gridRowCount
												+ '" name="count'+gridRowCount+'" value = "'
												+ countNo + '"/>']);
				}
		gridRowCount++;
		gridSerialNo++;
		rCount = gridRowCount;
		countNo++;
		
	}
		
}

/**
 * This method delete the rows from the grid
 */
function deleteTableRow() {
	try {
		cnt = 1;
		if(hasCheckboxChecked()){
		var table = getDomElementById(tableId);
		var tableRowCount = table.rows.length;
		idsList = "";
		for(var j=1;j<rCount;j++)
		{
			if(!isNull(getDomElementById("chk"+j)) && getDomElementById("chk"+j).checked == true){
				if(!isNull(getDomElementById("tripNumber"+j).value)){
				if(idsList.length > 0 ){
				  idsList = idsList+",";
				}
				idsList = idsList+ getDomElementById("tripNumber"+j).value;
				}
			}
		}
		for ( var i = 0; i < tableRowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				cnt++;
				deleteRow(tableId, i - 1);
				tableRowCount--;
				i--;
			}
		}
		var  rowAssign = true;
		var j=1;
		for ( var i = 1; i < rCount; i++) {
			if (!isNull(getDomElementById("count"+i))) {
				rowAssign = false;
				getDomElementById("serial"+i).innerHTML = j;
				getDomElementById("count"+i).value = j;
				j++;
				countNo = j;
			}
		}
		
		if(rowAssign)
			countNo = j;
		if (tableId == "pureRouteGrid") {
			getDomElementById("chk0").checked = false;
		} else if (tableId == "pureRouteGrid1") {
			getDomElementById("chkGrid").checked =false;
		}
	}else{
		alert("Please check atleast one row");
	}
	}catch (e) {
		alert(e);
	}

}


/**
 * It checks whether atleast one row has been cheked or not 
 * @returns {Boolean}
 */
function hasCheckboxChecked(){
	for(var i = 1; i<rCount;i++){
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
 * This is AJAX Call This Method check whether Flight/Train/Vehicle Number is
 * exist or not
 * 
 * @param transportNo
 * @param rowId
 */

function checkTransportNo(transportNo, rowId) {
	
	if(transportModeCode == airCode || transportModeCode == trainCode){
	getDomElementById("transportNumber"+rowId).value = transportNo.toUpperCase();
	
	if(transportModeCode == trainCode)
		{
		if(!isNull(transportNo) && transportNo.length < 5)
			{
			alert("Please enter Train number");
			setTimeout(function() {getDomElementById("transNumber"+rowId).focus();}, 10);
			return;
			}
		}
	
	}else{
		
		if(!isNull(getDomElementById("transNumber4"+rowId).value)){
			
			if(isNull(getDomElementById("transNumber1"+rowId).value) || getDomElementById("transNumber1"+rowId).value.length < 2){
				alert("Please enter Vehicle number");
				setTimeout(function() {getDomElementById("transNumber1"+rowId).focus();}, 10);
				return;
			}else if(isNull(getDomElementById("transNumber2"+rowId).value || getDomElementById("transNumber2"+rowId).value.length < 2)){
				alert("Please enter Vehicle number");
				setTimeout(function() {getDomElementById("transNumber2"+rowId).focus();}, 10);
				return;
			}else if(isNull(getDomElementById("transNumber3"+rowId).value)){
				alert("Please enter Vehicle number");
				setTimeout(function() {getDomElementById("transNumber3"+rowId).focus();}, 10);
				return;
			}else if(getDomElementById("transNumber1"+rowId).value.length < 2){
				setTimeout(function() {getDomElementById("transNumber1"+rowId).focus();}, 10);
				return;
			}else if(getDomElementById("transNumber2"+rowId).value.length < 2){
				setTimeout(function() {getDomElementById("transNumber2"+rowId).focus();}, 10);
				return;
			}else if(getDomElementById("transNumber3"+rowId).value.length < 2){
				setTimeout(function() {getDomElementById("transNumber3"+rowId).focus();}, 10);
				return;
			}
				
			getDomElementById("transportNumber"+rowId).value = (getDomElementById("transNumber1"+rowId).value +"-"
			+ getDomElementById("transNumber2"+rowId).value +"-"+getDomElementById("transNumber3"+rowId).value+"-"
			+transportNo).toUpperCase();
			
		}else{
			
			if(isNull(getDomElementById("transNumber4"+rowId).value) && isNull(getDomElementById("transNumber3"+rowId).value)
					&& isNull(getDomElementById("transNumber2"+rowId).value) && isNull(getDomElementById("transNumber1"+rowId).value))
				getDomElementById("transportNumber"+rowId).value = "";
			
			else{
				if(isNull(getDomElementById("transNumber1"+rowId).value)){
					alert("Please enter Vehicle number");
					setTimeout(function() {getDomElementById("transNumber1"+rowId).focus();}, 10);
					return;
				}else if(getDomElementById("transNumber1"+rowId).value.length < 2){
					setTimeout(function() {getDomElementById("transNumber1"+rowId).focus();}, 10);
					return;
				}  else if(isNull(getDomElementById("transNumber2"+rowId).value)){
					alert("Please enter Vehicle number");
					setTimeout(function() {getDomElementById("transNumber2"+rowId).focus();}, 10);
					return;
				}else if(getDomElementById("transNumber2"+rowId).value.length < 2){
					setTimeout(function() {getDomElementById("transNumber2"+rowId).focus();}, 10);
					return;
				}else if(isNull(getDomElementById("transNumber3"+rowId).value)){
					alert("Please enter Vehicle number");
					setTimeout(function() {getDomElementById("transNumber3"+rowId).focus();}, 10);
					return;
				}else if(getDomElementById("transNumber3"+rowId).value.length < 2){
					setTimeout(function() {getDomElementById("transNumber3"+rowId).focus();}, 10);
					return;
				}else if(isNull(getDomElementById("transNumber4"+rowId).value)){
					alert("Please enter Vehicle number");
					setTimeout(function() {getDomElementById("transNumber4"+rowId).focus();}, 10);
					return;
				}
				
			}
		}
	}
	
	
	
	tableRowId = rowId;
	c2 = false;
	c1 = false;
	if (transportModeCode == airCode) {
		//if( rowId == (rCount-1) && isNull(transportNo) && isNull(getDomElementById("expArrivalTime"+rowId).value)
		if( !checkLastRow(rowId) && isNull(transportNo) && isNull(getDomElementById("expArrivalTime"+rowId).value)
				&& isNull(getDomElementById("expDepartureTime"+rowId).value)){
			return;	
		}
		}else{
			//if( rowId == (rCount-1) && isNull(transportNo) && isNull(getDomElementById("expArrivalTime"+rowId).value) 
			if( !checkLastRow(rowId) && isNull(transportNo) && isNull(getDomElementById("expArrivalTime"+rowId).value)
					&& isNull(getDomElementById("expDepartureTime"+rowId).value)&& isNull(getDomElementById("transportName"+rowId).value) ){
				return;	
			}	
	}
	
	if (!isNull(transportNo)) {
		var transportMode = getValueByElementId("transportMode");
		transportModeCode = transportMode.split(tild)[1];
		var url = "./pureRoute.do?submitName=getTransportDetails&transportNo="
				+ transportNo + "&transportMode=" + transportModeCode;
		ajaxCall(url, "pureRouteForm", populateTransportDetails);
	} else {
		getDomElementById("transportId" + tableRowId).value = "";
		getDomElementById("transporterId" + tableRowId).value = "";
	}
	
}

/**
 * It checks whether Vehicle Type/Train Name Has entered or not
 * @param transportName
 * @param rowId
 * @returns {Boolean}
 */
function checkTransportName(transportName, rowId){
	tableRowId = rowId;
	c2 = false;
	if (transportModeCode == airCode) {
		//if( rowId == (rCount-1) && isNull(transportName) && isNull(getDomElementById("expArrivalTime"+rowId).value) 
		if( !checkLastRow(rowId) && isNull(transportName) && isNull(getDomElementById("expArrivalTime"+rowId).value)
				&& isNull(getDomElementById("expDepartureTime"+rowId).value)){
			return;	
		}
		}else{
			//if( rowId == (rCount-1) && isNull(transportName) && isNull(getDomElementById("expArrivalTime"+rowId).value)
			if( !checkLastRow(rowId) && isNull(transportName) && isNull(getDomElementById("expArrivalTime"+rowId).value)
					&& isNull(getDomElementById("expDepartureTime"+rowId).value)&& isNull(getDomElementById("transportNumber"+rowId).value) ){
				return;	
			}	
	}
	/*if(c1 == false){
	if(!checkTransportNameField(rowId)){
		c2 = true;
		return false;
	}
	}*/
		return true;
}

/**
 * This Method populates the data into grid fields after get the response from
 * server if the data exist
 * 
 * @param data
 * @returns {Boolean}
 */
function populateTransportDetails(data) {
	// alert(transportResponse.pureRouteTO.transportTO.flightTO.flightId);
	/*if (!isNull(data)) {
		if (data.indexOf(",") > 0) {
			var idValues = data.split(",");
			getDomElementById("transportId" + tableRowId).value = idValues[0];
			getDomElementById("transporterId" + tableRowId).value = idValues[1];

		} else
			getDomElementById("transporterId" + tableRowId).value = data;
		// alert(getDomElementById("transportId" + tableRowId).value);
	}*/
	
	
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			resp = responseText[SUCCESS_FLAG];
			if(!isNull(resp)){
				if (resp.indexOf(",") > 0) {
					var idValues = resp.split(",");
					getDomElementById("transportId" + tableRowId).value = idValues[0];
					getDomElementById("transporterId" + tableRowId).value = idValues[1];

				} else
					getDomElementById("transporterId" + tableRowId).value = resp;
			}
		}
		}
	}

	/*if (!validateObjTimes(tableRowId)) {
		return false;
	}*/
}

/**
 * This is AJAX Call This Method send the Pure Route details to server to save
 * in the database
 */
function savePureRoute() {
	var check = false;
	if (checkGridValues()) {
		for ( var i = 1; i < rCount-1; i++) {
			if (!isNull(getDomElementById("transportNumber" + i)) && !isNull(getDomElementById("transportNumber" + i).value)){
				check = true;
				break;
			}else
				check = false;
		}
		
		if(delStatus)
			check = true;
		if (check) {
			getDomElementById("tripIdsArrStr").value = idsList;
			flag = confirm("Do you want to Save the Route Details");
			if (!flag) {
				return;
			}
			getDomElementById("pageAction").value = action;
			enableHeaderFields();
			var url = "./pureRoute.do?submitName=savePureRoute";
			ajaxCall(url, "pureRouteForm", saveCallback);
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
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			disableHeaderFields();
			alert(error);
		}else {
			resp = responseText[SUCCESS_FLAG];
			if(searchPureRoute()){
				alert(resp);
			}
		}
		}
	}
	delStatus = false;
	
	
	/*if (data == "SUCCESS") {
		searchPureRoute();
		if(delStatus == true)
		alert("Route has updated successfully.");
		else
		alert("Route has created successfully.");
		
	} else if (data == "FAILURE") {
		disableHeaderFields();
		if(delStatus == true)
		alert("Exception occurred. Route not updated.");
		else
		alert("Exception occurred. Route not created.");	
	}*/

	// jQuery.unblockUI();
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
 * It loads the grid with appropriate labels based on Transport Mode
 */
function validateTransportByMode() {
	var transportMode = getValueByElementId("transportMode");
	if (isNull(transportMode)) {
		return;
	}

	transportModeCode = transportMode.split(tild)[1];

	if (transportModeCode == airCode) {
		changeLabelValue("flightTrainVehicleLabel", flightNumberLabel);
		getDomElementById("demo").style.display = '';
		getDomElementById("demo1").style.display = 'none';
		tableId = "pureRouteGrid";
	} else if (transportModeCode == trainCode) {
		changeLabelValue("flightTrainVehicleLabel1", trainNumberLabel);
		changeLabelValue("TrainVehicleTypeLabel1", trainNameLabel);
		getDomElementById("demo").style.display = 'none';
		getDomElementById("demo1").style.display = '';
		tableId = "pureRouteGrid1";
	} else if (transportModeCode == roadCode) {
		changeLabelValue("flightTrainVehicleLabel1", vehicleNumberLabel);
		changeLabelValue("TrainVehicleTypeLabel1", vehicleTypeLabel);
		getDomElementById("demo").style.display = 'none';
		getDomElementById("demo1").style.display = '';
		tableId = "pureRouteGrid1";
	}
}

/**
 * This is Ajax Call This is method get the City list based on Region
 * 
 * @param region
 * @param city
 */
function getStationByRegionId(region, city) {
	station = city;
	if (!isNull(region)) {
		var url = "./pureRoute.do?submitName=getStationsByRegion&regionId="
				+ region;
		ajaxCall(url, "pureRouteForm", populateCity);
	} else {
		clearDropDownList(station);
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
	clearDropDownList(station);
	obj = getDomElementById(station);
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown(station, stationList[i].cityName,
					stationList[i].cityId);
		}
	}
	*/
	
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			stationList = responseText[SUCCESS_FLAG];			
			clearDropDownList(station);
			obj = getDomElementById(station);
			if (!isNull(stationList)) {
				for ( var i = 0; i < stationList.length; i++) {
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
 * This Method change the label value of grid columns
 * 
 * @param labelId
 * @param labelValue
 */
function changeLabelValue(labelId, labelValue) {
	getDomElementById(labelId).innerHTML = labelValue;
}

/**
 * This is AJAX Call This is method get the Pure Route details by the search
 * parameters.
 */
function searchPureRoute() {
	
	var transportMode = getValueByElementId("transportMode");
	var transportModeId = transportMode.split(tild)[0];
	getDomElementById("tripIdsArrStr").value = "";
	idsList = "";
	if (checkFields()) {
		validateTransportByMode();

		clearGrid();
		getDomElementById("button_adddelete").style.display = '';
		getDomElementById("button_savecancel").style.display = '';

		if (isNull(transportMode)) {
			return;
		}

		var originStationId = getDomElementById("originStationId").value;
		var destinationStationId = getDomElementById("destinationStationId").value;
		var url = "./pureRoute.do?submitName=getRouteByOriginStationAndDestinationStation&originStationId="
				+ originStationId
				+ "&destinationStationId="
				+ destinationStationId + "&transportMode=" + transportModeId;

		ajaxCall(url, "pureRouteForm", populateRouteDetails);
	}
	return true;
}

/**
 * This method populates the pure route details in the screen after get the
 * response from searchPureRoute() AJAX Call
 * 
 * @param data
 */
function populateRouteDetails(data) {
	//resp = jsonJqueryParser(data);
	delStatus = false;
	action = "save";
	if(countNo != 1)
		countNo = 1;
	/*if (!isNull(prTO)) {
		if ((!isNull(prTO.routeId)))
			getDomElementById("routeId").value = prTO.routeId;
		if(prTO.rowCount >0){
		delStatus = true;
		action = "update";
		}
		for ( var i = 0; i < prTO.rowCount; i++){
			if(!isNull(prTO.transportNumber[i])){
			addRow();
			}
		}
	} else{
		addRow();
	}*/
	
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			prTO = responseText[SUCCESS_FLAG];
			disableHeaderFields();
			if (!isNull(prTO)) {
				if ((!isNull(prTO.routeId)))
					getDomElementById("routeId").value = prTO.routeId;
				if(prTO.rowCount >0){
				delStatus = true;
				action = "update";
				}
				for ( var i = 0; i < prTO.rowCount; i++){
					if(!isNull(prTO.transportNumber[i])){
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
		var table = getDomElementById(tableId);
		var tableRowCount = table.rows.length;
		clearGridFieldsData(tableId, serialNo);
		for ( var i = 1; i < tableRowCount; i++) {
			deleteRow(tableId, i - 1);
			tableRowCount--;
			i--;
		}
		rowCount = 1;
		serialNo = 1;
	}
	if (gridRowCount > 1) {
		var table = getDomElementById(tableId);
		var tableRowCount = table.rows.length;
		clearGridFieldsData(tableId, gridSerialNo);
		for ( var i = 1; i < tableRowCount; i++) {
			deleteRow(tableId, i - 1);
			tableRowCount--;
			i--;
		}
		gridRowCount = 1;
		gridSerialNo = 1;
	}
	rCount = 0;
}
/**
 * This method delete the Grid Fields data
 * 
 * @param tableId
 * @param rowIndex
 */
function clearGridFieldsData(tableId, rowIndex) {
	for ( var i = 1; i <= rowIndex; i++) {
		if (!isNull(getDomElementById("transportNumber" + rowIndex))) {
			getDomElementById("transportNumber" + rowIndex).value = "";
			if (tableId == 'pureRouteGrid1')
				getDomElementById("transportName" + rowIndex).value = "";
			getDomElementById("expDepartureTime" + rowIndex).value = "";
			getDomElementById("expArrivalTime" + rowIndex).value = "";
			getDomElementById("transporterId" + rowIndex).value = "";
			getDomElementById("transportId" + rowIndex).value = "";
		}
	}

	getDomElementById("routeId").value = "";
}

/**
 * This method disabled the Header fields to prevent data changes
 */
function disableHeaderFields() {
	getDomElementById("originRegion").disabled = true;
	getDomElementById("originStationId").disabled = true;
	getDomElementById("destinationRegion").disabled = true;
	getDomElementById("destinationStationId").disabled = true;
	getDomElementById("transportMode").disabled = true;
}

/**
 * This method enabled the Header fields to do data changes
 */
function enableHeaderFields() {
	getDomElementById("originRegion").disabled = false;
	getDomElementById("originStationId").disabled = false;
	getDomElementById("destinationRegion").disabled = false;
	getDomElementById("destinationStationId").disabled = false;
	getDomElementById("transportMode").disabled = false;
}

/**
 * It checks Whether Departure time has given in Correct format or not It checks
 * Departure time is greater than the Arrival time of another record which has
 * same Train/Vehicle/Flight Number
 * 
 * @param time
 * @param rowId
 * @returns {Boolean}
 */
function checkDepartureTime(time, rowId) {
	c3 = false;
	if (transportModeCode == airCode) {
	//if( rowId == (rCount-1) && isNull(time) && isNull(getDomElementById("expArrivalTime"+rowId).value) 
		if( !checkLastRow(rowId) && isNull(time) && isNull(getDomElementById("expArrivalTime"+rowId).value)
			&& isNull(getDomElementById("transportNumber"+rowId).value)){
		return;	
	}
	}else{
		//if( rowId == (rCount-1) && isNull(time) && isNull(getDomElementById("expArrivalTime"+rowId).value) 
		if( !checkLastRow(rowId) && isNull(time) && isNull(getDomElementById("expArrivalTime"+rowId).value)
				&& isNull(getDomElementById("transportNumber"+rowId).value)&& isNull(getDomElementById("transportName"+rowId).value) ){
			return;	
		}	
	}
	
	//if(c2 == false){
	//if (checkExpDepartureTimeField(rowId)) {
	if (!isNull(time)) {
		
		if (!validateTime("expDepartureTime", rowId, "Expected Departure Time")) {
			c3 = true;
			return false;
		} else if (!validateObjTimes(rowId)) {
			c3 = true;
			return false;
		} else if (!validateExpTime(rowId)){
			c3 = true;
			return false;
		}
	//}else{
	//	c3 = true;
	//	return false;
	//}
	}
	return true;
}

/**
 * It checks Whether Departure time has given in Correct format or not
 * 
 * @param time
 * @param rowId
 * @returns {Boolean}
 */
function checkArrivalTime(time, rowId) {
	
	/*if (transportModeCode == airCode) {
		if( rowId == (rCount-1) && isNull(time) && isNull(getDomElementById("transportNumber"+rowId).value) 
				&& isNull(getDomElementById("expDepartureTime"+rowId).value)){
			return;	
		}
		}else{
			if( rowId == (rCount-1) && isNull(time) && isNull(getDomElementById("transportNumber"+rowId).value) 
					&& isNull(getDomElementById("expDepartureTime"+rowId).value)&& isNull(getDomElementById("transportName"+rowId).value) ){
				return;	
			}	
	}*/
	if(c3 == false){
	//var expArrivalTime = getDomElementById("expArrivalTime" + rowId);
		
	if (!isNull(time)) {
		if (!validateTime("expArrivalTime", rowId, "Expected Arrival Time")) {
			c3 = false;
			return false;
		} else if (!validateObjectTimes(rowId)) {
			c3 = false;
			return false;
		} else if (!validateExpTime(rowId)){
			c3 = false;
			return false;
		}
	} if(checkMandatory(rowId)){
		//if(rowId == (rCount-1)){
		if(!checkLastRow(rowId)){
			if(!isNull(time)){
			addRow();
			
			if(!isNull(getDomElementById("transportNumber" + (rCount-1)))){
			if (transportModeCode == airCode || transportModeCode == trainCode) {
			setTimeout(function() {
				getDomElementById("transNumber" + (rCount-1)).focus();
					}, 10);
			}else{
				setTimeout(function() {
					getDomElementById("transNumber1" + (rCount-1)).focus();
						}, 10);
			}
			}
			}
		}
		
	}
	
	}
	c3 = false;
	return true;
}

/**
 * It checks Whether Arrival time is greater than the Departure Time of the Same
 * Row Id or not
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateExpTime(rowId) {
	time = getDomElementById("expDepartureTime" + rowId).value;
	time1 = getDomElementById("expArrivalTime" + rowId).value;

/*	if (!isNull(time) && !isNull(time1)) {
		depTime = time.split(":");
		arrTime = time1.split(":");

		if (parseInt(depTime[0], 10) > parseInt(arrTime[0], 10)) {
			alert("Row number: "
					+ getDomElementById("count" + rowId).value
					+ " Expected Arrival Time should be greater than the Expected Departure Time");
			setTimeout(function() {
				getDomElementById("expArrivalTime" + rowId).focus();
			}, 10);
			c3 = false;
			return false;
		} else if ((parseInt(depTime[0], 10) == parseInt(arrTime[0], 10))
				&& (parseInt(depTime[1], 10) >= parseInt(arrTime[1], 10))) {
			alert("Row number: "
					+ getDomElementById("count" + rowId).value
					+ " Expected Arrival Time should be greater than the Expected Departure Time");
			setTimeout(function() {
				getDomElementById("expArrivalTime" + rowId).focus();
			}, 10);
			c3 = false;
			return false;
		}
	}*/
	c3 = false;
	return true;
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
	getDomElementById("originRegion").value = "";
	getDomElementById("originStationId").value = "";
	getDomElementById("destinationRegion").value = "";
	getDomElementById("destinationStationId").value = "";
	getDomElementById("transportMode").value = "";
}

/**
 * It clears the data of the screen (Header fields data and Grid fields Data)
 */
function cancelPureRoute() {
	flag = confirm("Do you want to Clear the Details");
	if (!flag) {
		return;
	}
	document.pureRouteForm.action = "./pureRoute.do?submitName=viewPureRoute";
	document.pureRouteForm.submit();
}

/**
 * It checks whether the Origin station and Destination Station are same or not
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateStations(obj) {
	origin = getDomElementById("originStationId").value;
	destination = getDomElementById("destinationStationId").value;

	if (!isNull(origin) && !isNull(destination)) {
		if (origin == destination) {
			alert("Origin Station and Destination Station should not be same");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}
	}
}

/**
 * It checks whether The Departure Time is greater than the Arrival Time of
 * another record which contain same Train/Flight/Vehicle Number
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateObjTimes(rowId) {
	depBean = true;
	arrBean = false;
	tObj = getDomElementById("transportNumber" + rowId);
	obj1 = getDomElementById("expDepartureTime" + rowId);
	/*if (!isNull(tObj) && !isNull(obj1.value)) {
		for(var i = 1; i<rCount; i++){
			depBean = true;
			arrBean = false;
			if(i != rowId){
			obj2 = getDomElementById("expDepartureTime" + i);
			tObj1 = getDomElementById("transportNumber" + i);

				
			if((!isNull(tObj1) && !isNull(tObj1.value)) && (tObj1.value.toUpperCase() == tObj.value.toUpperCase())){
			if(!isNull(obj2)){
				time1 = obj1.value;
				time2 = obj2.value;
				
				if (!isNull(time1) && !isNull(time2)) {
					arrTime = time2.split(":");
					depTime = time1.split(":");
					if (parseInt(depTime[0], 10) < parseInt(arrTime[0],
							10)) {
						depBean = false;
						arrBean = true;
					}else if ((parseInt(depTime[0], 10) == parseInt(
								arrTime[0], 10))
								&& (parseInt(depTime[1], 10) < parseInt(
										arrTime[1], 10))){
					
						depBean = false;
						arrBean = true;
					}
					}
				
				if(depBean)
					{
					obj3 = getDomElementById("expArrivalTime" + i);
					time3 = obj3.value;
					if (!isNull(time3)) {
						arrTime = time3.split(":");
						depTime = time1.split(":");
						if (parseInt(depTime[0], 10) < parseInt(arrTime[0],
								10)) {
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Departute Time should be greater than the Expected Arrival Time of row number " 
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								obj1.focus();
							}, 10);
							return false;
						}else if ((parseInt(depTime[0], 10) == parseInt(
									arrTime[0], 10))
									&& (parseInt(depTime[1], 10) <= parseInt(
											arrTime[1], 10)))
							{
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Departute Time should be greater than the Expected Arrival Time of row number "
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								obj1.focus();
							}, 10);
							return false;
							}
						}
					if(arrBean){
					obj4 = getDomElementById("expArrivalTime" + rowId);
					time4 = obj4.value;
					if(!isNull(time4)){
						arrTime = time2.split(":");
						depTime = time4.split(":");
						if (parseInt(depTime[0], 10) > parseInt(arrTime[0],
								10)) {
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Arrival Time should be less than the Expected Departure Time of row number " 
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								obj4.focus();
							}, 10);
							return false;
						}else if ((parseInt(depTime[0], 10) == parseInt(
									arrTime[0], 10))
									&& (parseInt(depTime[1], 10) >= parseInt(
											arrTime[1], 10)))
							{
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Arrival Time should be less than the Expected Departure Time of row number "
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								obj4.focus();
							}, 10);
							return false;
							}
					}
					}
					}
				}
			
			}
			}
			}
		}
		*/
	return true;
}



/**
 * It compares exp arrival time of the Row with other rows of exp departure time of same Vehicle/Train/Flight Number
 * @param rowId
 * @returns {Boolean}
 */
function validateObjectTimes(rowId) {
	depBean = false;
	tObj = getDomElementById("transportNumber" + rowId);
	depObj1 = getDomElementById("expDepartureTime" + rowId);
	arrObj1 = getDomElementById("expArrivalTime" + rowId);
	/*if (!isNull(tObj) && !isNull(arrObj1.value)) {
		for(var i = 1; i<rCount; i++){
			
			if(i != rowId){
			depObj2 = getDomElementById("expDepartureTime" + i);
			arrObj2 = getDomElementById("expArrivalTime" + i);
			tObj1 = getDomElementById("transportNumber" + i);
			
			if((!isNull(tObj1) && !isNull(tObj1.value)) && (tObj1.value.toUpperCase() == tObj.value.toUpperCase())){
				depBean = false;
			if(!isNull(depObj2.value) && !isNull(depObj1.value)){
				time1 = depObj1.value;
				time2 = depObj2.value;
				
				if (!isNull(time1) && !isNull(time2)) {
					arrTime = time2.split(":");
					depTime = time1.split(":");
					if (parseInt(depTime[0], 10) < parseInt(arrTime[0],
							10)) 
						depBean = true;
					else if ((parseInt(depTime[0], 10) == parseInt(
								arrTime[0], 10))
								&& (parseInt(depTime[1], 10) < parseInt(
										arrTime[1], 10)))
						depBean = true;
					}
				if(depBean)
					{
					time4 = arrObj1.value;
					time3 = depObj2.value;
					
					if (!isNull(time3)) {
						arrTime = time3.split(":");
						depTime = time4.split(":");
						
						if (parseInt(depTime[0], 10) > parseInt(arrTime[0],
								10)) {
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Arrival Time should be less than the Expected Departure Time of row number " 
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								arrObj1.focus();
							}, 10);
							return false;
						}else if ((parseInt(depTime[0], 10) == parseInt(
									arrTime[0], 10))
									&& (parseInt(depTime[1], 10) >= parseInt(
											arrTime[1], 10)))
							{
							alert("Row number "
									+ getDomElementById("count" + rowId).value
									+ " Expected Arrival Time should be less than the Expected Departure Time of row number "
									+ getDomElementById("count" + i).value);
							setTimeout(function() {
								arrObj1.focus();
							}, 10);
							return false;
							}
						}
					}
				}
			
			}
			}
			}
		}*/
		
	return true;
}

/**
 * It selects or unselects the check box of all rows of the Grid
 */
function gridCheckAll() {
	var checkB = false;
	if (tableId == "pureRouteGrid") {
		if (getDomElementById("chk0").checked == true) {
			checkB = true;
		}
	} else if (tableId == "pureRouteGrid1") {
		if (getDomElementById("chkGrid").checked == true) {
			checkB = true;
		}
	}

	if (checkB) {
		for ( var i = 1; i < rCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = true;
			}
		}
	} else {
		for ( var i = 1; i < rCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = false;
			}
		}
	}
}


/**
 * @param e
 * @param rowCount
 * @param fromField
 * @param toField
 * @returns
 */
function alphaNumericAndEnterKeyNav(e,rowCount,fromField,toField){
	
	var charCode;

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	if(charCode == 13){
		return enterKeyNav(toField+ rowCount,e.keyCode);
	}
	
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90) || (charCode>=48 && charCode<=57)||charCode==9 || charCode==8 || charCode ==  0){
		return true;
	}
	return false;
	
}

/**
 * @param e
 * @param rowCount
 * @param fromField
 * @param toField
 * @returns
 */
function alphaAndEnterKeyNav(e,rowCount,fromField,toField){
	/*trans1 = true;
	trans3 = false;
	tra1 = false;
	tra3 = false;*/
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	
	objVal = getDomElementById(fromField+rowCount).value;
		
	if(charCode == 13){
		//trans1 = true;
		trans3 = true;
		tra1 = true;
		tra3 = true;
		
		return enterKeyNav(toField+ rowCount,e.keyCode);
	}
	if(objVal.length<2){
		 if(onlyAlphabet(e))
			 return true;
		 
	}if(charCode == 9 || charCode == 8 || charCode == 0)
		return true;
	
	return false;
	
}

/**
 * @param e
 * @param rowCount
 * @param fromField
 * @param toField
 * @returns
 */
function numericAndEnterKeyNav(e,rowCount,fromField,toField){
	
	var charCode;
	var allow = false;
	/*trans2 = false;
	trans4 = false;
	tra2 = false;
	tra4 = false;*/
	
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	objVal = getDomElementById(fromField+rowCount).value;
	
	if(charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0){
		
		trans2 = true;
		trans4 = true;
		tra2 = true;
		tra4 = true;
		if(charCode == 13){
		
		return enterKeyNav(toField+ rowCount,e.keyCode);
		}
		
		return true;
	}
	if(fromField == "transNumber"){
	
	if(objVal.length<5)
		allow = true;
	
	}else if(fromField == "transNumber2"){
		
		if(objVal.length<2)
			allow = true;
	}else if(fromField == "transNumber4"){
		objVal = getDomElementById(fromField+rowCount).value;
		if(objVal.length<4)
			allow = true;
	}
	
	if(allow){
		if(onlyNumeric(e))
			return true;
		else 
			return false;
	}
	else
	return false;
}


function numericColAndEnterKeyNav(e,fromField,toField,rowCount){
	
	var charCode;
	
	
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	
	
	objVal = getDomElementById(fromField+rowCount).value;
	
	if(charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0){
		
		
		if(fromField == 'expDepartureTime' &&  charCode == 13){
		
		return enterKeyNav(toField+ rowCount,e.keyCode);
		}
		
		return true;
	}
	
	if (charCode != 58 && charCode > 31 && (charCode < 48 || charCode > 57)){
		return false;
	}
	else{
		return true;
	}
	
	return false;
}
/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param fieldLen
 * @param fieldName
 * @param boolVal
 * @param falVal
 * @returns {Boolean}
 */
function validateFieldLength(fieldVal, field, rowCount, fieldLen, fieldName, boolVal, falVal){
	
	if(field == 'transNumber1'){
		trans2 = false;
		tra2 = false;
	}
	else if(field == 'transNumber2'){
		trans3 = false;
		tra3 = false;
	}
	else if(field == 'transNumber3'){
		trans4 = false;
		tra4 = false;
	}
	if(!isNull(fieldVal)){
		
		if(fieldVal.length<fieldLen && boolVal == true){
			alert("Please enter "+ fieldName +" number");
			
			if(field == 'transNumber1'){
				trans2 = false;
				tra2 = false;
			}
			else if(field == 'transNumber2'){
				trans3 = false;
				tra3 = false;
			}
			else if(field == 'transNumber3'){
				trans4 = false;
				tra4 = false;
			}
			
			setTimeout(function() {
				getDomElementById(field+rowCount).focus();
			}, 10);
			
			return false;
		}
		}
		return true;
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param fieldLen
 * @param fieldName
 * @returns
 */
function validateTrans3FieldLength(fieldVal, field, rowCount, fieldLen, fieldName){
	
	if(validateFieldLength(fieldVal, field, rowCount, fieldLen, fieldName, trans3, trans4)){
		 return validateTrans3Fieldvalue(fieldVal, field, rowCount, 'transNumber2','transNumber1','transNumber4');
	 }
	 return false;
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param fieldLen
 * @param fieldName
 * @returns
 */
function validateTrans4FieldLength(fieldVal, field, rowCount, fieldLen, fieldName){
	
 if(validateFieldLength(fieldVal, field, rowCount, fieldLen, fieldName, trans4, trans)){
	 return validateTrans4Fieldvalue(fieldVal, field, rowCount, 'transNumber1','transNumber2','transNumber3');
 }
 return false;
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param fieldLen
 * @param fieldName
 * @returns
 */
function validateTrans2FieldLength(fieldVal, field, rowCount, fieldLen, fieldName){
	
	if(validateFieldLength(fieldVal, field, rowCount, fieldLen, fieldName, trans2, trans3)){
		 return validateTrans2Fieldvalue(fieldVal, field, rowCount, 'transNumber1','transNumber3','transNumber4');
	 }
	 return false;
	
	
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param fieldLen
 * @param fieldName
 * @returns
 */
function validateTrans1FieldLength(fieldVal, field, rowCount, fieldLen, fieldName){
	
	if(validateFieldLength(fieldVal, field, rowCount, fieldLen, fieldName, trans1, trans2)){
		 return validateTrans1Fieldvalue(fieldVal, field, rowCount, 'transNumber2','transNumber3','transNumber4');
	 }
	 return false;
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param field1
 * @param field2
 * @param field3
 * @param boolVal
 * @param falVal
 * @returns {Boolean}
 */
function validateFieldvalue(fieldVal, field, rowCount, field1,field2,field3,boolVal,falVal){
	
	if(isNull(getDomElementById(field+rowCount).value) && isNull(getDomElementById(field1+rowCount).value) && isNull(getDomElementById(field2+rowCount).value) && isNull(getDomElementById(field3+rowCount).value))
		return true;
	else{
		
		if(isNull(fieldVal) && boolVal == true){
			
		alert("Please enter Vehicle number");
		
		if(field == 'transNumber1')
			tra2 = false;
		else if(field == 'transNumber2')
			tra3 = false;
		else if(field == 'transNumber3')
			tra4 = false;

		setTimeout(function() {
			getDomElementById(field+rowCount).focus();
		}, 10);
		return false;
		}
	}	
	return true;
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param field1
 * @param field2
 * @param field3
 * @returns
 */
function validateTrans1Fieldvalue(fieldVal, field, rowCount, field1,field2,field3){
	
	return validateFieldvalue(fieldVal, field, rowCount, field1,field2,field3,tra1,tra2);
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param field1
 * @param field2
 * @param field3
 * @returns
 */
function validateTrans2Fieldvalue(fieldVal, field, rowCount, field1,field2,field3){
	
	return validateFieldvalue(fieldVal, field, rowCount, field1,field2,field3,tra2,tra3);
}

/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param field1
 * @param field2
 * @param field3
 * @returns
 */
function validateTrans3Fieldvalue(fieldVal, field, rowCount, field1,field2,field3){
	
	return validateFieldvalue(fieldVal, field, rowCount, field1,field2,field3,tra3,tra4);
}
/**
 * @param fieldVal
 * @param field
 * @param rowCount
 * @param field1
 * @param field2
 * @param field3
 * @returns
 */
function validateTrans4Fieldvalue(fieldVal, field, rowCount, field1,field2,field3){
	
	if(validateFieldvalue(fieldVal, field, rowCount, field1,field2,field3,tra4,true) && tra4 == true){
		
		return checkTransportNo(fieldVal,rowCount);
	}
	return false;
}

//function checkLastRow(fieldName,rowId){
function checkLastRow(rowId){
		if(getDomElementById("count" + rowId).value == (countNo-1)){
			return false;
		}
		
	return true;
}

