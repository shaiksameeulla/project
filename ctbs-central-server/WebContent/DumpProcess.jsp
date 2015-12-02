<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ include file="./pages/resources/html/calendar.html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<title>DTDC HELIOS - Service @ Speed of Thought</title>
	<link href="pages/resources/css/global.css" rel="stylesheet" type="text/css" />
	<link href="pages/resources/css/content.css" rel="stylesheet" type="text/css" />
	<link href="pages/resources/css/calendar.css" rel="stylesheet" type="text/css" />
	<link href="pages/resources/js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">
	<link href="pages/resources/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	<script language="JavaScript" src="pages/resources/js/jquery/jquery-1.4.4.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/jquery-ui.min.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/ui/jquery.ui.core.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/ui/jquery.ui.widget.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/jquery.dimensions.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/jquery.bgiframe-2.1.2.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
	<script language="JavaScript" type="text/javascript" src="pages/resources/js/jquery/jquery.idletimeout.js"></script>
	<script language="JavaScript" type="text/javascript" src="pages/resources/js/jquery/jquery.idletimer.js"></script>
	<script language="JavaScript" type="text/javascript" src="pages/resources/js/firstLevelNav.js"></script>	
	<script language="JavaScript" type="text/javascript" src="pages/resources/js/prototype.js"></script>
	<script language="JavaScript" type="text/javascript" src="pages/resources/js/common.js"></script>
	<script language="JavaScript" src="pages/resources/js/ts_picker.js" type="text/javascript" ></script>
	<script language="JavaScript" src="pages/resources/js/calendar.js" type="text/javascript" ></script>	
	
<script>
jQuery.noConflict();
function validate(){
	
}

window.onbeforeunload = confirmExit;
window.onload =myOnload;

function confirmExit()
{
	jQuery.blockUI({ message: '<h3><img src="/ctbs-central-server/pages/resources/images/loading_animation.gif"/></h3>'});
}	
function myOnload() {
	jQuery.unblockUI();
}
function callDownLoad(){
	
}
</script>
</head>
<body>

 
<form  name="processFrom" method="post" action="dumpExtractor.dtdc">
<h2>
							<span class="mandatoryMsg">Fields marked with <span
								class="mandatory">*</span> are mandatory</span>
						</h2>
						<div style="border:1px #cccccc solid; width:600px; margin:0px auto; width:600px">
 <table >
 <tr><td> <br/><br/></td></tr>
 <tr align="center"> <td colspan="9" align="center"><img src="pages/resources/images/DTDC_Logo.gif" width="280" height="64" alt="DTDC" /><br />
 </td></tr>
 <tr>
 <td align="center">
 <br/><br/>
 <P><strong>Manual Central Data Extraction process</strong></P>
 
 </td>
 </tr>
 <tr>
 <td>
 <br/><br/><br/>
 </td>
 </tr>
 <tr><td>
<table align="center">
<tr style="border-bottom:none;">
<th class="leftLogo"></th>
<th class="rightLogo"></th>
</tr>
<tr>
<td class="lable" align="center">BranchCode : <span
								class="mandatory">*</span></td>
<td class="tdTwo"><input type="text" id="brCode" name="BRANCH_CODE" size="15" maxlength="8" /></td>
</tr>
<tr>
<td class="lable" align="center">Max Records To be Fetch: <span
								class="mandatory">*</span></td>
<td class="tdTwo"><input type="text" id="maxRecords" name="MAX_RECORD" size="15" maxlength="8" /></td>
</tr>
<tr>
<td class="lable" align="center">Record Status: <span
								class="mandatory">*</span></td>
<td class="lable" align="center">


<SELECT name=STATUS id="status" style="width:150px">
<OPTION value="U" selected>UNREAD</OPTION>
<OPTION value="M">MANUAL</OPTION>
<OPTION value="R">READ</OPTION>
<OPTION value="P">ALL</OPTION>
<OPTION value="T">TRANSMISSION</OPTION>
</SELECT>
</td>
</tr>
<tr>
<td class="lable"  align="center"> Start Date:<span
								class="mandatory">*</span> </td>
<td class="data"><input type="text" id="stdate" name="START_DATE" size="15" maxlength="20" />
<span class="data"> <img src="pages/resources/images/icon_calendar.gif"
                                                        width="14" height="17" alt="Select Date" border="0"
                                                        longdesc="#" onclick="setYears(1980, 2030);showCalender(this, 'stdate');"/> </span>
</td>
<td class="lable" align="center">End Date:<span
								class="mandatory">*</span> </td>
<td class="data"><input type="text" id="enddate" name="END_DATE" size="15" maxlength="20" />
<span class="data"> <img src="pages/resources/images/icon_calendar.gif"
                                                        width="14" height="17" alt="Select Date" border="0"
                                                        longdesc="#" onclick="setYears(1980, 2030);showCalender(this, 'enddate');"/> </span>
</td>
</tr>
 <tr>
<td colspan="2" align="right"><input type="submit" style="button"  value="PROCESS" /></td>
</tr>
</table></td>
<td></td>
</tr>
<tr></tr>
</table>
</div>
</form>
 
</body>
</html>