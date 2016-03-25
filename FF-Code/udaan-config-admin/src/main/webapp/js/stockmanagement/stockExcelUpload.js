var FORM_ID="stockExcelUploadForm";


/*$(document).ready( function () {
	
	
} );*/
function clearScreen(){
	var url= "./stockExcelUpload.do?submitName=viewFormDetails";
	if(promptConfirmation("Clear")){
		globalFormSubmit(url,FORM_ID);
	}

}


function upload(){
	
		var url= "./stockExcelUpload.do?submitName=saveStockExcelUpload";
		
		var fileUploadDom = getDomElementById("fileUpload");
		
		if(isNull(fileUploadDom.value)){
			alert("Please upload a file");
			setFocusOnStDom(fileUploadDom);
			return false;
		}
		var fileName=fileUploadDom.value;
		if(!fileName.endsWith(".xls")  && !fileName.endsWith(".xlsx")){
			alert("Please upload only Excel files");
			fileUploadDom.value="";
			setFocusOnStDom(fileUploadDom);
			return false;
		}
		
		if(promptConfirmation("Upload")){
			globalFormSubmit(url,FORM_ID);
		}
}
