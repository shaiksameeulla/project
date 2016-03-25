<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/pettycash/pettyCashReport.js"></script>

<script type="text/javascript" charset="utf-8">

var currentDate='${pettyCashReportForm.to.currentDate}';// YYYY-MM-DD
var todaysDate='${pettyCashReportForm.to.todaysDate}';// DD/MM/YYYY
var closingDate='${pettyCashReportForm.to.closingDate}';// YYYY-MM-DD (-1)
var regionId='${pettyCashReportForm.to.regionId}';
var loggedInOffice='${pettyCashReportForm.to.loggedInOfficeId}';
var paymentMode='${pettyCashReportForm.to.paymentMode}';

</script>
</head>
<body>
<div id="wraper"> 
<html:form styleId="pettyCashReportForm">
	<!-- main content -->
	<div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
        		<h1><bean:message key="label.pettycash.title" /></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.mec.fieldsAreMandatory" /></div>
      		</div>
            <div class="formTable">
	        	<table border="0" cellpadding="0" cellspacing="5" width="100%">
	        		<tr>
	        			<td width="45%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.date" />:</td>
	        			<td width="65%">
	        				<html:text property="to.todaysDate" styleId="todaysDate" styleClass="txtbox width100" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this);" onkeypress="return callEnterKey(event,document.getElementById('viewBtn'));" />
	        				&nbsp;<a href="#" onclick="setPettyCashDate(this);" title="Please select date" id="currentDt" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
	        			</td>
	        		</tr>
				</table>
			</div>
		</div>
	</div>
    <!-- Button -->
	<div class="button_containerform">
		<div class="button_containerform" align="left">
			<html:button property="View" styleId="viewBtn" styleClass="btnintform" onclick="showPettyCashReport();">
					<bean:message key="button.view" /></html:button>
		</div>
  	</div>
    <!-- Button ends -->
	<!-- main content ends -->
</html:form>
</div>
<!--wraper ends-->
</body>
</html>
