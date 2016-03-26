<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:directive.page import="java.util.Date" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="pages/utilities/branchInboundUtility.js"></script>
<script type="text/javascript">
	
	
</script>
<!-- DataGrids /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/inboundUtility.do" method="post"
			styleId="jobServiceForm">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							Inbound Branch ZIP file utility
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td class="lable" width="17%"><bean:message
										key="label.bulkBooking.dateTime" /></td>
								<td  width="30%"> <jsp:scriptlet>out.print(""+new Date()); </jsp:scriptlet></td>
								</tr>
								
								<tr>
								<td  class="lable" id="zipFileMessage" width="17%"></td>
								<td  id="zipFilePath" width="30%"> </td>
								</tr>
								<tr>
								<td class="lable" id="noteMsg" colspan="2"></td>
								
								</tr>
								<tr>
								<td id="downloadLink" style="display:none">
								<html:link action="/downloadfile">Download from here</html:link>
								</td>
								<td/>
								</tr>
								<tr>
								<td/>
								<td/>
								</tr>
								<tr>
								<td/>
								<td/>
								</tr>
								<tr>
								<td/>
								<td/>
								</tr>
								<tr>
								<td/>
								<td></td>
								</tr>
						</table>
					</div>
					<!-- Grid /-->
				</div>


			</div>

			<!-- Button -->
			<div class="button_containerform">

				<html:button styleId="Save" styleClass="btnintform"
					onclick="prepareZipFile()" property="">
					Create ZIP
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="inboundPageRefresh();" styleId="cancel">
					<bean:message key="label.bulkBooking.Cancel" locale="display" />
				</html:button>
					</div>
			<br />
			<br />
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>

	<!--wraper ends-->
</body>
</html>