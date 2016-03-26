function getWeightFromWeighingMachine() {
	var url = "weightReader.do";
	jQuery.ajax({
		url : url,
		timeout: 2000,
		success : function(req) {
			capturedWeight(req);
		}
	});
}
function getWeightFromWeighingMachineWithParam(weightResponse, rowIdd) {
	var url = "weightReader.do";
	jQuery.ajax({
		url : url,
		timeout: 2000,
		success : function(req) {
			weightResponse(req, rowIdd);
		}
	});
}
//Needs to be write in individuals js files
/*function capturedWeight(data) {
	var capturedWeight = "";
	if (!isNull(data)) {
		capturedWeight = eval('(' + data + ')');
		if (capturedWeight == -1) {
			wmWeight = capturedWeight;
		} else if (capturedWeight == -2) {
			wmWeight = "Exception occurred";
		} else if (!isNull(capturedWeight) && capturedWeight != -1) {
			wmWeight = parseFloat(capturedWeight).toFixed(3);
		}
	}
}*/
