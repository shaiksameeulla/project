$(document).ready(function() {	
	printpage();
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
	var url = "/udaan-web/branchOutManifestParcel.do?submitName=viewBranchOutManifestParcel";
	document.branchOutManifestParcelForm.action=url;
	document.branchOutManifestParcelForm.submit();
}

function hideurl(){
	var url = "./branchOutManifestParcel.do?submitName=printManifestDtls";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}