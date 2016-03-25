var PREV_SELECTED_DT = "";
var PETTY_CASH_ERROR_FLAG = "ERROR";

/**
 * To show petty cash report of selected data
 */
function showPettyCashReport() {
	var todaysDateObj = document.getElementById("todaysDate");
	if (!isNull(todaysDateObj.value)) {
		var url = "./pettyCashReport.do?submitName=ajaxPettyCashReportViewer"
				+ "&pettyCashDate=" + todaysDateObj.value;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				callBackShowPettyCash(req);
			}
		});
	} else {
		alert("Please select date to view petty cash report");
		setTimeout(function() {
			todaysDateObj.focus();
		}, 10);
	}
}
// AJAX - call back function for show petty cash viewer via udaan-config-admin
// to udaan-report project
function callBackShowPettyCash(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[PETTY_CASH_ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = eval('(' + ajaxResp + ')');
			var pcUrl = data.pettyCashURL;
			var url = pcUrl + "pages/reportviewer/pettyCashReportViewer.jsp";
			var params = setParamToURL(data);
			url += params;
			window
					.open(
							url,
							'PettyCashBook',
							'toolbar=0, scrollbars=0, location=0, statusbar=0, menubar=0, resizable=1, fullscreen=yes');
		}
	}
	hideProcessing();
}

/**
 * To set param to petty cash URL
 * 
 * @param data
 */
function setParamToURL(data) {
	return "?currentDate=" + data.currentDate + "&regionId=" + data.regionId
			+ "&loggedInOfficeId=" + data.loggedInOfficeId + "&paymentMode="
			+ data.paymentMode + "&todaysDate=" + data.todaysDate
			+ "&closingDate=" + data.closingDate;
}

/**
 * To set petty cash date
 * 
 * @param obj
 */
function setPettyCashDate(obj) {
	show_calendar('todaysDate', obj.value);
}

/**
 * To validate selected date
 */
function validateSelectedDate(dtObj) {
	var arrSelDt = dtObj.value.split("/");// Selected date
	var selectedDt = new Date(arrSelDt[2], arrSelDt[1], arrSelDt[0]);

	var arrTodayDt = todaysDate.split("/");// Todays date
	var today = new Date(arrTodayDt[2], arrTodayDt[1], arrTodayDt[0]);

	var temp = dtObj.value;

	if (selectedDt > today) {
		alert("Selected date should not greater than todays date.");
		if (isNull(PREV_SELECTED_DT))
			PREV_SELECTED_DT = todaysDate;
		dtObj.value = PREV_SELECTED_DT;
		return false;
	}
	PREV_SELECTED_DT = temp;
}
