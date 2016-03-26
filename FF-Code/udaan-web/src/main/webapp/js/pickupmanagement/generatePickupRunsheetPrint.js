$(document).ready(function() {	
	printpage();
} );

function setTotalWeight(){	
	var totalWeightHidObj = document.getElementById("totalWeightHidden");
	fixFormatUptoThreeDecimalPlace(totalWeightHidObj);
	document.getElementById("totalWeight").innerHTML = totalWeightHidObj.value;
	
}

function printpage() {	
	window.print();
	self.close();
	//back();
}
function back(){
	var url = "./generatePickupRunsheet.do?submitName=viewGeneratePickupRunSheet";
	document.pickupRunsheetForm.action=url;
	document.pickupRunsheetForm.submit();
}

/*function hideurl(){
	var url = "./generatePickupRunsheet.do?submitName=printGenPickupRunSheet";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}*/