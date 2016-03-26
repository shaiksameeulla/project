<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
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
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/backDatedBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	var maxBackBookingDateAllowed = 0;
	function setDefaultvalues() {
		getAllOffices();
		maxBackBookingDateAllowed = '${maxBackBookingDateAllowed}';
		var flag = '${isError}';
		var trasnStatus = '${trasnStatus}';
		if (trasnStatus != null && trasnStatus != "") {
			alert(trasnStatus);
		}
		if (flag == 'Y') {
			getErrorList();
		}
		jQuery.unblockUI();
	}
</script>

</head>
<body onload="setDefaultvalues();">
	<div id="wraper">
		<html:form action="/backdatedBooking.do" method="post"
			styleId="backdatedBookingForm" enctype="multipart/form-data">

			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.backDatedBooking.backDatedBooking" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.backDatedBooking.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>

								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="" styleId="sysdate" value="${todaysDate}" />
								<html:hidden property="to.bookingDate" styleId="bookingDate"
									value="" />
								<html:hidden property="to.fileName" styleId="fileName"
									value="${fileName}" />
								<html:hidden property="to.error" styleId="error"
									value="${error}" />
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />

								<html:hidden property="to.bookingOfficeId"
									styleId="bookingOfficeId" value="${originOfficeId}" />
								<html:hidden property="to.bookingOffCode"
									styleId="bookingOfficeCode" value="${originOfficeCode}" />
								<html:hidden property="to.originCityId" styleId="originCityId"
									value="${originCityId}" />
								<html:hidden property="to.loginRHOCode" styleId="loginRHOCode"
									value="${loginRHOCode}" />



								<td width="10%" class="lable"><sup class="mandatoryf">*</sup><bean:message
										key="label.backDatedBooking.dateTime" /></td>
								<td width="18%" class="lable1"><html:text
										property="to.backDate" styleClass="txtbox width75"
										styleId="dlvDateTime" onkeypress="return callEnterKey(event, document.getElementById('dlvTime'));" readonly="true" value=""
										onblur="checkDate(this.value);" /> <a
									href='javascript:show_calendar("dlvDateTime", this.value)'>
										<img src="images/calender.gif" alt="Select Date" width="16"
										height="16" border="0" />
								</a> <html:text property="to.backTime" styleClass="txtbox"
										styleId="dlvTime" size="6" maxlength="5" value=""
										onkeypress="return callEnterKey(event, document.getElementById('branch'));"
										onchange="chckFormat(this);" /> <bean:message
										key="label.backDatedBooking.time" /></td>
								<html:hidden property="to.delvDateTime"
									styleId="deliveryDateTime" />
								<html:hidden property="to.fileType" styleId="fileType" />

								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.backDatedBooking.Region" /></td>

								<td width="19%"><html:text property=""
										styleClass="txtbox width110" styleId="regionName" size="6"
										maxlength="5" value="${regionName}" readonly="true" /></td>

								<html:hidden property="to.destinationRegionList"
									styleId="destRegionId" value="${regionId}" />



							</tr>
							<tr>
								<td width="17%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.backDatedBooking.Branch" /></td>
								<td width="30%"><html:select property="to.bookingOfficeId"
										styleClass="selectBox width140" onkeypress="return callEnterKey(event, document.getElementById('fileUpload'));" styleId="branch" value="">
									</html:select></td>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.backDatedBooking.ExcelUpload" /></td>
								<td width="35%"><html:file property="to.fileUpload"
										styleId="fileUpload" styleClass="txtbox" /> &nbsp;&nbsp;</td>

							</tr>
						</table>
					</div>
					<!-- Grid /-->
				</div>


			</div>

			<!-- Button -->
			<div class="button_containerform">
				<html:button styleId="Upload" styleClass="btnintform"
					onclick="upload()" property="">
					<bean:message key="label.backDatedBooking.Submit" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBackdatedBooking();" styleId="cancel">
					<bean:message key="label.backDatedBooking.Cancel" locale="display" />
				</html:button>
	</div>
			<br />
			<br />
			<!-- Button ends -->
		</html:form>
	</div>
</body>
</html>