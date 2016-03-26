$(document).ready(function() {	
	
	printpage();
	//setData4Print();
		
} );



/**
 * printpage
 *
 * @author 
 */
function printpage() {	
	window.print();
	self.close();
}
/**
 * back
 *
 * @author
 */
function back(){
	var url = "/udaan-web/mbplOutManifest.do?submitName=viewMBPL";
	document.mbplOutManifestForm.action=url;
	document.mbplOutManifestForm.submit();
}

/**
 * setData4Print
 *
 * @author
 */
function setData4Print(){
	
	var bizAssociateCode = document.getElementById("bizAssociateCode").value;
	document.getElementById("baCode").innerHTML = bizAssociateCode.split("-")[0];
	document.getElementById("baName").innerHTML  = bizAssociateCode.split("-")[1];
	
}

function hideurl(){
	var url = "./mbplOutManifest.do?submitName=printMBPLOutManifest";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}

