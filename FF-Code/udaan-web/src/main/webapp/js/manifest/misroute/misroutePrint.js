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
	var url = "/udaan-web/misroute.do?submitName=viewMisroute";
	document.bplOutManifestDoxForm.action=url;
	document.bplOutManifestDoxForm.submit();
}



