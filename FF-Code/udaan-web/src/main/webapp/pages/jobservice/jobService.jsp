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
	$(document).ready(function() {
		var oTable = $('#example').dataTable({
			"sScrollY" : "100",
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"bScrollCollapse" : false,
			"bSort" : false,
			"bInfo" : false,
			"bPaginate" : false,
			"sPaginationType" : "full_numbers"
		});
		new FixedColumns(oTable, {
			"sLeftWidth" : 'relative',
			"iLeftColumns" : 0,
			"iRightColumns" : 0,
			"iLeftWidth" : 0,
			"iRightWidth" : 0,
		});
	});
</script>
<script type="text/javascript" charset="utf-8">
	var auto = setInterval(function() {
		getJobServices();
	}, 120000); // refresh every 2 mins
</script>

</head>
<body onload="getJobServices();">
	<html:form action="/jobService.do?submitName=listViewJobService"
		styleId="jobServiceForm">
		<div id="wraper">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<strong>Job Services</strong>
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.Rate.fieldMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<td width="13%" class="lable">From Date:</td>
								<td width="18%"><html:text styleId="fromDate"
										property="to.fromDate" styleClass="txtbox width130"
										readonly="true" tabindex="-1"
										onfocus="validateFromDate(this);" /> &nbsp;<a href="#"
									onclick="javascript:show_calendar('fromDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" /></a></td>
								<td width="20%" class="lable">To Date:</td>
								<td width="18%"><html:text styleId="toDate"
										property="to.toDate" styleClass="txtbox width130"
										readonly="true" tabindex="-1" onfocus="validateToDate(this);" />
									&nbsp;<a href="#"
									onclick="javascript:show_calendar('toDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" /></a></td>
								<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;Process
									Name:</td>

								<td><html:select styleId="processCode"
										property="to.processCode" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.option.select" />
										</html:option>
										<logic:present name="jobProcessList" scope="request">
											<c:forEach var="process" items="${jobProcessList}"
												varStatus="loop">
												<html:option value="${process.stdTypeCode}">${process.description}</html:option>
											</c:forEach>
										</logic:present>
									</html:select></td>


							</tr>
							<tr>


								<td class="lable">Job Number:</td>
								<td width="17%"><html:text styleId="jobNumber"
										property="to.jobNumber" styleClass="txtbox width130" /></td>
							</tr>
							<tr>
								<td align="right" valign="top" colspan="6"><input
									name="Search" type="button" value="Search" class="button"
									onclick="getJobServices();" title="Search" /></td>
							</tr>
						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">Details</div>
						</div>
						<div style="width: 985px">
							<table cellpadding="0" cellspacing="0" border="0" class="display"
								id="example" width="100%">
								<thead>
									<tr>
										<th width="5%">Sr No</th>
										<th width="13%">Job Number</th>
										<th width="13%">File Submission Date</th>
										<th width="13%">Remarks</th>
										<th width="13%">Percentage Completed</th>
										<th width="13%">Status</th>
										<th width="13%">Error file</th>
										<th width="13%">Success file</th>

									</tr>
								</thead>

							</table>
						</div>
					</div>
					<!-- Grid /-->
				</div>
			</div>



		
			<!-- main content ends -->
			<!-- footer -->

			<!-- footer ends -->
		</div>
		<!-- wrapper ends -->
	</html:form>
</body>

