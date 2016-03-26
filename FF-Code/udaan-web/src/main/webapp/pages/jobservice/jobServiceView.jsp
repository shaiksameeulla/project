<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JOB SERVICE</title>

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jobservice/jobservice.js"></script>

<script type="text/javascript" charset="utf-8">
	setInterval(function() {
		getJob();
	}, 30000); // refresh every 1 mins

	//for print bulk booking

	function printBulkBookings() {
		var succ_Value = document.getElementById("successFile").innerHTML;
		if (succ_Value == "Not available") {
			alert("No any success record available for print!");
		} else {
			var jbno = document.getElementById("jobNo").innerHTML;
			var s_type = document.getElementById("sticker_Type").value;
			if (s_type == 'S') {
				url = './cnPrinting.do?submitName=printCnSeries&jobno=' + jbno;
			} else {
				url = './bulkBooking.do?submitName=printBulkBookings&jobNo='
						+ jbno;
			}
			window
					.open(
							url,
							'newWindow',
							'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left= 412,top = 184,scrollbars=yes');
		}

	}
</script>

</head>
<body onload="getJob();">
	<html:form action="/jobService.do?submitName=listViewJobService"
		styleId="jobServiceForm">
		<div id="wraper">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">


					<div id="demo">

						<div class="formTable">
							<table border="0" cellpadding="1" cellspacing="3" width="100%">
								<tr>
									<td width="20%" class="lable">Job Number:</td>
									<td width="14%" class="lable1"><span id="jobNo"></span></td>

									<td width="20%" class="lable">Job Submitted Date:</td>
									<td width="14%" class="lable1"><span id="jobSubmittedDate"></span></td>
								</tr>

								<tr>
									<td width="20%" class="lable">Job Completed %:</td>
									<td width="14%" class="lable1"><span
										id="percentageCompleted"></span></td>

									<td width="20%" class="lable">Job Status:</td>
									<td width="14%" class="lable1"><span id="jobStatus"></span></td>
								</tr>
								<tr>
									<td width="20%" class="lable">Error file:</td>
									<td width="14%" class="lable1"><span id="errorFile"></span></td>
								</tr>
								<tr>
									<td width="20%" class="lable">Success file:</td>
									<td width="14%" class="lable1"><span id="successFile"></span></td>
								</tr>
								<tr>
									<td width="20%" class="lable">Details:</td>
									<td width="14%" class="lable1"><span id="remarks"></span></td>

								</tr>

							</table>
						</div>
					</div>


					<!-- Grid /-->
				</div>

			</div>
			<!-- Button -->
			<div class="button_containerform">
				<html:button property="Print" styleClass="btnintformbigdis"
					onclick="printBulkBookings();" styleId="bulk_print_btn" disabled="true">
					<bean:message key="label.bulkBooking.Print" locale="display" />
				</html:button>

			</div>
			<!-- Button ends -->
			<input type="hidden" id="jobNumber" name="jobNumber"
				value="${jobNumber}" /> <input type="hidden" id="sticker_Type"
				name="sticker_Type" value="${sticker_Type}" />
			<div id="processImg">
				<center>
					<h3>
						<img src="images/loading_animation.gif" />
					</h3>
				</center>
			</div>

		</div>
		<!-- wrapper ends -->
	</html:form>
</body>

