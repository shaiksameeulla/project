/**
 * 
 */
function getLocationMappingDetails() {
	showProcessing();
	var selectedLocation = jQuery("#enteredLocation").val();

	var location = "";
	var cityName = "";
	var locationDetails = "";

	if (!isNull(selectedLocation)) {
		locationDetails = selectedLocation.split(",");
		location = locationDetails[0];
		cityName = locationDetails[1];
	} else {
		alert("Please select any location.");
		document.getElementById("enteredLocation").focus();
		hideProcessing();
	}

	if (!isNull(location)) {
		var url = './locationSearch.do?submitName=getLocationMapping&location='
				+ location + '&cityName=' + cityName;
		ajaxCallWithoutForm(url, showLocationDetails);
	}
}

/**
 * 
 * @param data
 */
function showLocationDetails(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var addressSearchList = eval(data);
		$("#addressTable").empty();
		var cnt = "<thead>" + "<tr><th width='30%'>Pincode Mapped</th>"
				+ "<th width='30%'>Office Mapped</th>"
				+ "<th width='40%'>Product Mapped </th>" + "</tr></thead>"
				+ "<tbody>";
		$("#addressTable").append(cnt);

		var cl = "";
		for ( var i = 0; i < addressSearchList.length; i++) {

			if (i % 2 == 0) {
				cl = "even";
			} else {
				cl = "odd";
			}
			var newRow = "<tr class='" + cl + "'><td>"
					+ addressSearchList[i].pincodeMapped + "</td>" + "<td>"
					+ addressSearchList[i].officeName + "</td>" + "<td>"
					+ addressSearchList[i].productMapped + "</td></tr>";
			$("#addressTable").append(newRow);

		}
		$("#addressTable").append("</tbody>");

	}
	hideProcessing();
}