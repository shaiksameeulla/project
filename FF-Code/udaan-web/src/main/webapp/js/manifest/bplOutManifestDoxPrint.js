$(document).ready(function() {	
	
	printpage();
	//setData4Print();
		
} );



/**
 * printpage
 *
 * @author sdalli
 */
function printpage() {	
	window.print();
	self.close();
}
/**
 * back
 *
 * @author sdalli
 */
function back(){
	var url = "/udaan-web/bplOutManifestDox.do?submitName=viewBPLOutManifestDox";
	document.bplOutManifestDoxForm.action=url;
	document.bplOutManifestDoxForm.submit();
}

/**
 * setData4Print
 *
 * @author sdalli
 */
function setData4Print(){
	
	var bizAssociateCode = document.getElementById("bizAssociateCode").value;
	document.getElementById("baCode").innerHTML = bizAssociateCode.split("-")[0];
	document.getElementById("baName").innerHTML  = bizAssociateCode.split("-")[1];
	
}

function hideurl(){
	var url = "./bplOutManifestDox.do?submitName=printBPLOutManifestDox";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}

