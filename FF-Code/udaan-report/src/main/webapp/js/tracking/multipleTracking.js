function trackmultiple(){
	
	var type = document.getElementById("typeName").value;
	var number = document.getElementById("consgRefNo").value;
	var pattern = /[,\n]/; 
	var txtArray=number.split(pattern);
	//var txtArray1=number.split('\n');
	/*if(txtArray!=null){
	for(var i=0;i<txtArray.length-1;i++){
	//alert(txtArray[i]);
	}*/
	//var numbers=number.split(',?\\n');
	/*url = "./multipleTracking.do?submitName=getMultipleConsgDetails&type="+type+"&number="+number;
	ajaxCallWithoutForm(url,populateConsignment);*/
	document.multipleTrackingForm.action = "/udaan-report/multipleTracking.do?submitName=getMultipleConsgDetails&type="+type+"&number="+txtArray;
	document.multipleTrackingForm.submit();
}

function clearScreen(action){
	var url = "./multipleTracking.do?submitName=viewMultipleTracking";
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		document.getElementById("consgRefNo").value="";
		document.multipleTrackingForm.action = url;
		document.multipleTrackingForm.submit();
	}
}