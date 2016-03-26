$(document).ready(function() {	
	setTotalWeight();
	printpage();
} );

/**
 * setTotalWeight
 *
 * @author sdalli
 */
function setTotalWeight(){	
	var totalWeightHidObj = document.getElementById("totalWeightHidden");
	fixFormatUptoThreeDecimalPlace(totalWeightHidObj);
	document.getElementById("totalWeight").innerHTML = totalWeightHidObj.value;
	
}

/**
 * printpage
 *
 * @author sdalli
 */
function printpage() {	
	window.print();
	back();
}
/**
 * back
 *
 * @author sdalli
 */
function back(){
	var url = "./loadDispatch.do?submitName=viewLoadDispatch";
	document.loadManagementForm.action=url;
	document.loadManagementForm.submit();
}


/**
 * fixFormatUptoThreeDecimalPlace
 *
 * @param obj
 * @author sdalli
 */
function fixFormatUptoThreeDecimalPlace(obj){
	if(obj.value != "" && obj.value != null){
		obj.value=parseFloat(obj.value).toFixed(3);
	}
}

function hideurl(){
	var url = "./loadDispatch.do?submitName=printLoadDispatch";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}