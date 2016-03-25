// Title: Timestamp picker
// Description: See the demo at url
// URL: http://us.geocities.com/tspicker/
// Script featured on: http://javascriptkit.com/script/script2/timestamp.shtml
// Version: 1.0
// Date: 12-05-2001 (mm-dd-yyyy)
// Author: Denis Gritcyuk <denis@softcomplex.com>; <tspicker@yahoo.com>
// Notes: Permission given to use this script in any kind of applications if
//    header lines are left unchanged. Feel free to contact the author
//    for feature requests and/or donations

String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); };
var calendarPopUpWin = null;

function show_calendar(str_target, str_datetime) {
	
	//for validating date string
	//isDate(str_datetime);
	//alert("hi" +str_target +"second "+str_datetime );
	var arr_months = ["January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"];
	var week_days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
	var n_weekstart = 1; // day week starts from (normally 0 or 1)
	var temp;
	var array;
	if(str_datetime == null || str_datetime =="" ){
		temp=new Date();
	} else {
		array = str_datetime.split("/");
		//new Date(year, month, day, hours, minutes, seconds, milliseconds2)
		//dd-MM-yyyy
		try {
			temp=new Date(array[2],array[1]-1,array[0]);
		} catch (e) {
			alert ("Invalid Date, Please provide a valid date");
			window.opener.document.getElementById("&#34;str_target&#34;").focus();
		}
	}
	
	
	
	var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : temp);
	//alert("dt_datetime >>> "+dt_datetime);
	var dt_prev_month = new Date(dt_datetime);
	dt_prev_month.setMonth(dt_datetime.getMonth()-1);
	//alert("Previous Month >>> " + dt_prev_month);
	var dt_next_month = new Date(dt_datetime);
	dt_next_month.setMonth(dt_datetime.getMonth()+1);
	//alert("next Month >>> " + dt_next_month);
	var dt_firstday = new Date(dt_datetime);
	dt_firstday.setDate(1);
	dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7);
	var dt_lastday = new Date(dt_next_month);
	dt_lastday.setDate(0);
	//alert("first day >>> " + dt_firstday);
	// html generation (feel free to tune it for your particular application)
	// print calendar header
	//alert("show month >>> "+arr_months[dt_datetime.getMonth()]);
	
	var str_buffer = new String (
		"<html>\n"+
		"<head>\n"+
		"	<title>Calendar</title>\n"+
		"</head>\n"+
		"<body bgcolor=\"White\">\n"+
		"<table class=\"clsOTable\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n"+
		"<tr><td bgcolor=\"#4682B4\">\n"+
		"<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\">\n"+
		"<tr>\n	<td bgcolor=\"#4682B4\"><a href=\"javascript:window.opener.show_calendar(&#34;"+
		str_target+"&#34;, &#34;"+ dt2dtstr(dt_prev_month)+"&#34;);\">"+
		"<img src=\"images/prev.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"previous month\"></a></td>\n"+
		"	<td bgcolor=\"#4682B4\" colspan=\"5\">"+
		"<font color=\"white\" face=\"tahoma, verdana\" size=\"2\">"
		+arr_months[dt_datetime.getMonth()]+" "+dt_datetime.getFullYear()+"</font></td>\n"+
		"	<td bgcolor=\"#4682B4\" align=\"right\"><a href=\"javascript:window.opener.show_calendar(&#34;"+
		str_target+"&#34;, &#34;"+dt2dtstr(dt_next_month)+"&#34;);\">"+
		"<img src=\"images/next.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"next month\"></a></td>\n</tr>\n"
	);
	
	var dt_current_day = new Date(dt_firstday);
	// print weekdays titles
	str_buffer += "<tr>\n";
	for (var n=0; n<7; n++)
		str_buffer += "	<td bgcolor=\"#87CEFA\">"+
		"<font color=\"white\" face=\"tahoma, verdana\" size=\"2\">"+
		week_days[(n_weekstart+n)%7]+"</font></td>\n";
	// print calendar table
	str_buffer += "</tr>\n";
	
	
	while (dt_current_day.getMonth() == dt_datetime.getMonth() ||
		dt_current_day.getMonth() == dt_firstday.getMonth()) {
		// print row heder
		str_buffer += "<tr>\n";
		for (var n_current_wday=0; n_current_wday<7; n_current_wday++) {
				if (dt_current_day.getDate() == dt_datetime.getDate() &&
					dt_current_day.getMonth() == dt_datetime.getMonth())
					// print current date
					str_buffer += "	<td bgcolor=\"#FFB6C1\" align=\"right\">";
				else if (dt_current_day.getDay() == 0 || dt_current_day.getDay() == 6)
					// weekend days
					str_buffer += "	<td bgcolor=\"#DBEAF5\" align=\"right\">";
				else
					// print working days of current month
					str_buffer += "	<td bgcolor=\"white\" align=\"right\">";

				if (dt_current_day.getMonth() == dt_datetime.getMonth()){
					// print days of current month
					//window.opener.document.getElementById("str_target").focus();
					str_buffer += "<a href=\'javascript:window.opener.document.getElementById(&#34;"+str_target+"&#34;).value=\""+dt2dtstr(dt_current_day)+"\"; window.close();\'>"+
					"<font color=\"black\" face=\"tahoma, verdana\" size=\"2\">";
				
		}
				else {
					// print days of other months
					
					str_buffer += "<a href=\'javascript:window.opener.document.getElementById(&#34;"+str_target+"&#34;).value=\""+dt2dtstr(dt_current_day)+"\"; window.close();\'>"+
					"<font color=\"gray\" face=\"tahoma, verdana\" size=\"2\">";
				
				}
				str_buffer += dt_current_day.getDate()+"</font></a></td>\n";
				dt_current_day.setDate(dt_current_day.getDate()+1);
		}
		// print row footer
		str_buffer += "</tr>\n";
	}
	
	// print calendar footer
	/*
	str_buffer +=
		"<form id=\"cal\" name=\"cal\">\n<tr><td colspan=\"7\" bgcolor=\"#87CEFA\">"+
		"<font color=\"White\" face=\"tahoma, verdana\" size=\"2\">"+
		"Time: <input type=\"hidden\" id=\"time\" name=\"time\" value=\""+dt2tmstr(dt_datetime)+
		"\" size=\"8\" maxlength=\"8\"></font></td></tr>\n</form>\n" +
		"</table>\n" +
		"</tr>\n</td>\n</table>\n" +
		"</body>\n" +
		"</html>\n";
	*/
	//var vWinCal = window.open("", "Calendar", 
		//"width=200,height=200,status=no,resizable=no,top=200,left=200");
	var vWinCal = window.open("", "Calendar","width=200,height=250,status=no,resizable=no,location=no,titlebar=no,top=200,left=200");
	calendarPopUpWin = vWinCal;
	vWinCal.opener = self;
	var calc_doc = vWinCal.document;
	calc_doc.write (str_buffer);
	document.getElementById(str_target).focus();
	calc_doc.close();
	//document.getElementById(str_target).focus();
}
// datetime parsing and formatting routimes. modify them if you wish other datetime format
function str2dt (str_datetime) {
	var re_date = /^(\d+)\-(\d+)\-(\d+)\s/;
	if (!re_date.exec(str_datetime))
		return alert("Invalid Datetime format: "+ str_datetime);
	return (new Date (RegExp.$3, RegExp.$2-1, RegExp.$1, RegExp.$4, RegExp.$5, RegExp.$6));
}
function dt2dtstr (dt_datetime) {
	var M = "" + (dt_datetime.getMonth()+1);
    var MM = "0" + M;
    MM = MM.substring(MM.length-2, MM.length);
    var day = "" + dt_datetime.getDate();
    if(day.length == 1 ){
    	day = "0" + day;	
    }
    return (new String (
    		day+"/"+(MM)+"/"+dt_datetime.getFullYear()+" ").replace(/^\s*/, "").replace(/\s*$/, ""));
}
function dt2tmstr (dt_datetime) {
	return (new String (
			dt_datetime.getHours()+":"+dt_datetime.getMinutes()+":"+dt_datetime.getSeconds()).replace(/^\s*/, "").replace(/\s*$/, ""));
}
function getCurrentTime (dt_datetime) {
	return (new String (
			dt_datetime.getHours()+":"+dt_datetime.getMinutes()+":"+dt_datetime.getSeconds()).replace(/^\s*/, "").replace(/\s*$/, ""));
}

function daysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {
			this[i] = 30;
		}
		if (i==2) {
			this[i] = 29;
		}
	}
	return this;
}



// Function is resposible for validate Date  
function isDate(dtStr){
	//alert('>>>'+dtStr+'<<<')
	var dtCh= "-";
	
	if(dtStr != 'null' && dtStr != ""){
		var daysInMonth = daysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strDay=dtStr.substring(0,pos1);
		var strMonth=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
		if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
		for (var i = 1; i <= 3; i++) {
			if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
		}
		month=parseInt(strMonth);
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){
			alert("The date format should be : dd-mm-yyyy");
			return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
			alert("Please enter a valid month");
			return false;
		}
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			alert("Please enter a valid day");
			return false;
		}
		// || year<minYear || year>maxYear
		if (strYear.trim().length != 4 ){
			alert("Please enter a valid 4 digit year");
			return false;
		}
//		alert(isIntegerForDateChecking(stripCharsInBag(dtStr, dtCh))+"-- is integer--"+stripCharsInBag(dtStr, dtCh));|| isIntegerForDateChecking(stripCharsInBag(dtStr, dtCh))==false
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 ){
			alert("Please enter a valid date");
			return false;
		}
	}
	return true;
}

/**
 * isNull
 *@param: value : From date Object 
 @return: Boolean ie either true or false
 */
function isNull(value){
	var flag=true;
	if (value !=undefined && value!= null && value!=""  && value != "null" && value!=" " && value !="0"  ){
		flag = false;
	}
	return  flag;
}

/**
 * closeCalendarPopUp
 * @author narmdr
 */
function closeCalendarPopUp() {
	if(!isNull(calendarPopUpWin)){
		calendarPopUpWin.close();
		calendarPopUpWin = null;
	}	
}
