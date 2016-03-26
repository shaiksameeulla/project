function uploadFile() {
	
	var uploadFile = document.getElementById("quotationUploadFile").value;
	if(!isNull(uploadFile)){
		var url = './rateQuotation.do?submitName=fileUploadRateQuotation';
		document.rateQuotationForm.action = url;		
		document.rateQuotationForm.submit();
	}else{
		alert("please select quotation upload file");
		return;
	}
}