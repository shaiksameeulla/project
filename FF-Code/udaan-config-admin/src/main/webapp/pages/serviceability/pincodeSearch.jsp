<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script> -->
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" src="js/serviceability/pincodeSearch.js"></script>

<script type="text/javascript" charset="utf-8">
	
</script>
</head>
<body>
	<html:form action="/searchByPincode.do" styleId="searchByPincodeForm">
		<!--wraper-->
		<div id="wraper">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Pincode Branch Serviceability</h1>
						<%-- <div class="mandatoryMsgf"><sup class="star"><bean:message key="symbol.common.star"/></sup><bean:message key="label.common.FieldsareMandatory"/></div> --%>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="14%" class="lable">Pincode</td>
								<td width="21%"><html:text property="to.pincodeNumber"
										styleClass="txtbox width130" styleId="pincodeSearch" value=""
										onkeypress="return callEnterKey(event, document.getElementById('searchBtn'))"
										maxlength="6" /></td>
								<td width="32%" class="lable1"><html:button
										property="Search" styleClass="btnintgrid" styleId="searchBtn"
										onclick="getPincodeMappingDetails('pincodeSearch');">
										<bean:message key="button.search" />
									</html:button></td> 
							</tr>
						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.pincode.details" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							width="100%" id="pincodeTable" style="overflow: visible;">
						</table>
					</div>
					<!-- Grid /-->
				</div>
			</div>
			<!-- Hidden Fields Start -->
			<%-- <html:hidden property="to.userTO.userId" styleId="userId"/> --%>
			<html:hidden property="employeeId" styleId="employeeId"
				value="${employeeId}" />
			<!-- Hidden Fields End -->
			<!-- Button -->
			<div class="button_containerform">
				<%-- <html:button property="Print" styleClass="btnintform" styleId="btnPrint" onclick=""><bean:message key="button.print"/></html:button> --%>
				<%-- <html:button property="Solve" styleClass="btnintform" styleId="btnSolve" onclick=""><bean:message key="button.solve"/></html:button>
	    <html:button property="Follow Up" styleClass="btnintform" styleId="btnFollowUp" onclick=""><bean:message key="button.followUp"/></html:button>
	    <html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelDetails();"><bean:message key="button.clear"/></html:button> --%>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</div>
		<!--wraper ends-->
	</html:form>
</body>
</html>
