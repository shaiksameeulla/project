/**
 * isDuplicateConsignment
 * 
 * @param consgNumberObj
 * @returns {Boolean}
 * @author Narasimha Rao kattunga
 */
function isDuplicateConsignment1(consgNumberObj) {
	var isConsgUsed = false;
	var selectedRowId = getRowId(consgNumberObj, "cnNumber");
	var cnNo = document.getElementById("cnNumber" + selectedRowId).value;
	if (usedConsignments != null && usedConsignments.length != 0) {
		for ( var i = 0; i < usedConsignments.length; i++) {
			if (cnNo != null && cnNo != "" && usedConsignments[i] == cnNo.toUpperCase()) {
				isConsgUsed = true;
			}
		}
		if (!isConsgUsed) {
			usedConsignments.push(cnNo);
		}
	} else {
		if (usedConsignments.length == 0) {
			usedConsignments.push(cnNo);
		}
	}
	return isConsgUsed;
}

function isDuplicateConsignment(consgNumberObj, fieldNameToValidate) {
	if(isNull(fieldNameToValidate))
		return isDuplicateConsignment1(consgNumberObj);
	else {
		var isConsgUsed = false;
		var selectedRowId = getRowId(consgNumberObj, fieldNameToValidate);
		var cnNo = document.getElementById(fieldNameToValidate + selectedRowId).value;
		if (usedConsignments != null && usedConsignments.length != 0) {
			for ( var i = 0; i < usedConsignments.length; i++) {
				if (cnNo != null && cnNo != "" && usedConsignments[i] == cnNo) {
					isConsgUsed = true;
				}
			}
			if (!isConsgUsed) {
				usedConsignments.push(cnNo);
			}
		} else {
			if (usedConsignments.length == 0) {
				usedConsignments.push(cnNo);
			}
		}
		return isConsgUsed;
	}
}


function removeUnUsedConsignment(consignmentNo) {
	if (usedConsignments.length == 0) {
		return;
	}
	var index = usedConsignments.indexOf(consignmentNo);
	
	if (index > -1) {
		usedConsignments.splice(index, 1);
	}
}