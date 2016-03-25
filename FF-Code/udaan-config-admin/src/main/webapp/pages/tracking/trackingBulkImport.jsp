<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>

<!-- tab css-->
<!--<link rel="stylesheet" href="jquery-tab-ui.css" />-->
<!--tab css ends-->

<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/tracking/bulkImportTracking.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script>
	var isFileUploaded = false;
	$(function() {    $( "#tabs" ).tabs();  
					  $("#fileUpload").focus();
				 });
</script>
<!-- Tabs ends /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<!--header-->
		<!-- main content -->
		<html:form action="/bulkImportTracking.do" method="post"
			styleId="bulkImportForm" enctype="multipart/form-data">
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.tracking.bulkTracking.header" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.consignmenttracking.type" /></td>
								<td><html:select property="to.type" styleId="typeName"
										styleClass="selectBox width140" onkeypress="return callEnterKey(event,document.getElementById('fileUpload'));">
										<%-- <c:forEach var="type" items="${typeNameTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode}">
												<c:out value="${type.description}" />
											</html:option>
										</c:forEach> --%>
									</html:select></td>
								<td><html:file property="to.fileUpload"
										styleId="fileUpload" styleClass="txtbox" onkeypress="return callEnterKey(event,document.getElementById('Upload'));"/> &nbsp;&nbsp;
									
									<c:if test="${empty bulkTOs}">	
									<html:button
										styleId="Upload" styleClass="button" onclick="upload()"
										property="">
										<bean:message key="button.label.track" locale="display" />
									</html:button></c:if>
									<c:if test="${not empty bulkTOs}">	
									<html:button
										styleId="Upload" styleClass="button" onclick="upload()"
										property="" style="display: none;">
										<bean:message key="button.label.track" locale="display" />
									</html:button></c:if>
							</td>
							</tr>
							<tr>

								<td width="66%" colspan="4" align="center" class="lable1"><bean:message
										key="label.tracking.bulkTracking.information" /></td>
							</tr>
							<tr>
								<td colspan="4" class="lable1" align="center"></td>
							</tr>
						</table>
					</div>

					 <c:if test="${not empty bulkTO.inValidConsg}">
							<script>  alert(" Invalid consignment/Reference No : <c:out value="${bulkTO.inValidConsg}" /> ");</script>
					</c:if>

					<%--
					<c:forEach var="bulkDetails" items="${bulkTOs}" varStatus="loop">
						<c:if test="${not empty bulkDetails.inValidRef}">
							<script>  alert(" Invalid Ref No: <c:out value="${bulkDetails.inValidRef}" /> ");</script>
						</c:if>
					</c:forEach> --%>

					<div id="tabs">
						<ul>
							<li><a href="#tabs-1"><bean:message
										key="label.bulkTracking.information" /></a></li>
						</ul>

						<div id="tabs-1">
							<div>
								<table cellpadding="0" cellspacing="0" border="0"
									class="display" id="example" width="90%">
									<thead>
										<tr>
											  <th align="center">
						  <bean:message key="label.tracking.consignmentNo"/></th>
                          <th align="center"><bean:message key="label.tracking.referenceNo"/></th>
                          <th align="center"><bean:message key="label.tracking.bookingDate"/></th>
                          <th align="center"><bean:message key="label.tracking.Origin"/></th>
                          <th align="center"><bean:message key="label.tracking.Destination"/></th>
                          <th align="center"><bean:message key="label.tracking.Weight"/></th>
                          <th align="center"><bean:message key="label.tracking.Status"/></th>
                          
                          <th align="center">DRS NO</th>
                          <th align="center"><bean:message key="label.tracking.DeliveryDate"/></th>
                          <th align="center">Delivery Office</th>
                          <th align="center"><bean:message key="label.tracking.ReceiverName"/></th>
                          <th align="center">Third Party Name</th>
                          <th align="center"><bean:message key="label.tracking.PendingReason"/></th>
                          
                          <th align="center"><bean:message key="label.tracking.ogmNo"/></th>
                          <th align="center"><bean:message key="label.tracking.ogmDate"/></th>
                          <th align="center"><bean:message key="label.tracking.bplNo"/></th>
                          <th align="center"><bean:message key="label.tracking.bplDate"/></th>
                          
                          <th align="center"><bean:message key="label.tracking.cdNo"/></th>
                          <th align="center"><bean:message key="label.tracking.cdDate"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportNo"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportDep"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportArr"/></th>
                          <th align="center"><bean:message key="label.tracking.ReceiveDate"/></th>
                          <th align="center"><bean:message key="label.tracking.InManifestDate"/></th>
										</tr>
									</thead>

									<tbody>

										<c:if test="${not empty bulkTOs}">
										<script>isFileUploaded = true;</script>
											<c:forEach var="bulkDetails" items="${bulkTOs}"
												varStatus="loop">
												<tr>
													<td><c:out value='${bulkDetails.consgNum}' /></td>
													<td><c:out value='${bulkDetails.refNum}' /></td>
													<td><c:out value='${bulkDetails.bookingDate}' /></td>
													<td><c:out value='${bulkDetails.origin}' /></td>
													<td><c:out value='${bulkDetails.destination}' /></td>
													<td><c:out value='${bulkDetails.weight}' /></td>													
													<td><c:out value='${bulkDetails.status}' /></td>

													<td><c:out value='${bulkDetails.drsNo}' /></td>
													<td><c:out value='${bulkDetails.delvDate}' /></td>
													<td><c:out value='${bulkDetails.dlvBranchName}' /></td>
													<td><c:out value='${bulkDetails.rcvrName}' /></td>
													<td><c:out value='${bulkDetails.thirdPartyName}' /></td>
													<td><c:out value='${bulkDetails.pendingReason}' /></td>

													<td><c:out value='${bulkDetails.ogmNum}' /></td>
													<td><c:out value='${bulkDetails.ogmDate}' /></td>
													<td><c:out value='${bulkDetails.bplNum}' /></td>
													<td><c:out value='${bulkDetails.bplDate}' /></td>
													<td><c:out value='${bulkDetails.cdNum}' /></td>
													<td><c:out value='${bulkDetails.cdDate}' /></td>
													<td><c:out value='${bulkDetails.flightNum}' /></td>
													<td><c:out value='${bulkDetails.flightDept}' /></td>
													<td><c:out value='${bulkDetails.flightArrvl}' /></td>
													<td><c:out value='${bulkDetails.rcvDate}' /></td>
													<td><c:out value='${bulkDetails.manifestDate}' /></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- Grid /-->
				</div>

			</div>
			<!-- Button -->
			<div class="button_containerform">
				<input name="Export Data to Excel" type="button"
					value="Export Data to Excel" class="btnintformbig1"
					title="Export Data to Excel" onclick="getUploadList();" />
				<html:button property="Clear" styleClass="btnintform"
					styleId="clearBtn" onclick="clearScreen('clear');">
					<bean:message key="button.clear" />
				</html:button>
			</div>
		</html:form>
		<!-- footer ends -->
	</div>
	<!--wraper ends-->
</body>
</html>
