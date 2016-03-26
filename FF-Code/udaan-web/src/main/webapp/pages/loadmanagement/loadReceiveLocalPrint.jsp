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
<title>UDAAN</title>
<style type="text/css">
body { /* font:normal 16px "Lucida Console", Monaco, monospace; */
	/* font-family: monospace;
	font-size: 16px; */
	font: normal 12px "Verdana", Monaco, monospace;
}

table tr .showLine td {
	border-left: 2px dashed black;
}

table tr .showLine td:last-child {
	border-left: 2px dashed black;
	border-right: 2px dashed black;
}

table tr .showBtmLine td,table tr .showBtmLine th {
	border-bottom: 2px dashed black;
}

table tr .showTopLine td,table tr .showTopLine th {
	border-top: 2px dashed black;
}

table tr .showTopBtmLine td,table tr .showTopBtmLine th {
	border-bottom: 2px dashed black;
	border-top: 2px dashed black;
}

table tr .showToptopLine td,table tr .showTopBtmLine th {
	border-bottom: 2px dashed black;
}

.header {
	display: table-header-group;
}
</style>
<style type="text/css" media="print">
@page {
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}
</style>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/loadmanagement/loadReceiveLocalPrint.js"></script>
</head>
<!-- onload="printpage();" -->
<body onload="hideurl();">
	<html:form action="/loadDispatch.do" styleId="loadManagementForm">

		<!-- <span id=curTime></span><br/> -->
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top"><input type="hidden"
					id="totalWeightHidden"
					value="${loadReceiveLocalTO.totalWeightPrint}" />
					<center>
						<bean:message key="label.print.header" />
						: ${loadReceiveLocalTO.destCity}<br />
					</center> <%-- <center><b>${loadReceiveLocalTO.loggedInOfficeTO.officeName} </b></center><br/> --%>1
					<center>
						<bean:message key="label.print.gatePass" />
					</center>
					<br /> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="0" width="100%" border="0">
						<!-- Header Row 1 -->
						<tr class="showTopLine">
							<td width="27%" align="left" valign="top"><bean:message
									key="label.receiveDate" /> :</td>
							<td>${loadReceiveLocalTO.receiveDateTime}</td>
							<td width="27%" align="left" valign="top"><bean:message
									key="label.gatePassNo" />:</td>
							<td>${loadReceiveLocalTO.gatePassNumber}</td>
						</tr>

						<!-- Header Row 2 -->
						<tr>
							<td width="27%" align="left" valign="top"><bean:message
									key="label.originOffice" /> :</td>
							<td>${loadReceiveLocalTO.originOffice}</td>
							<td width="27%" align="left" valign="top"><bean:message
									key="label.print.destinationOffice" /> :</td>
							<td>${loadReceiveLocalTO.destOffice}</td>
							<!-- <th width="12%" align="left" valign="top">Br. Load No.:</th>
                  	<th width="17%" align="left" valign="top">2</th> -->
						</tr>


						<!-- Header Row 5 -->


						<!-- Header Row 6 -->
						<tr>
							<td width="16%" align="left" valign="top"><bean:message
									key="label.mode" />:</td>
							<td>${loadReceiveLocalTO.transportMode}</td>
							<td width="16%" align="left" valign="top"><bean:message
									key="label.vehicleNumber" /> :</td>
							<td>${loadReceiveLocalTO.vehicleNumber}</td>

							<!-- <th width="12%" align="left" valign="top">Br. Load No.:</th>
                  	<th width="17%" align="left" valign="top">2</th> -->
						</tr>

						<tr>

							<td width="16%" align="left" valign="top"><bean:message
									key="label.actualArrival" />:</td>
							<td>${loadReceiveLocalTO.actualArrival}</td>
							<td width="18%" align="left" valign="top"><bean:message
									key="label.driverName" /> :</td>
							<td>${loadReceiveLocalTO.driverName}</td>

							<th colspan="3" align="left" valign="top"></th>
						</tr>

					</table> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="0" width="100%" border="0"
						class="showLine">
						<thead>
							<tr class="showTopBtmLine">
								<td width="4%" align="center" valign="top"><bean:message
										key="label.print.serialNo" /></td>
								<td width="19%" align="center" valign="top"><bean:message
										key="label.bplMbplNo" /></td>
								<td width="15%" align="center" valign="top"><bean:message
										key="label.print.destinationCity" /></td>
								<td width="15%" align="center" valign="top"><bean:message
										key="label.print.documentType" /></td>
								<td width="10%" align="center" valign="top"><bean:message
										key="label.weight" /></td>
								<td width="15%" align="center" valign="top"><bean:message
										key="label.lockNo" /></td>
								<td width="10%" align="center" valign="top"><bean:message
										key="label.status" /></td>
								<td width="14%" align="center" valign="top"><bean:message
										key="label.remarks" /></td>
							</tr>
						</thead>
						<c:forEach var="dtlsTOs"
							items="${loadReceiveLocalTO.loadReceiveDetailsTOs}"
							varStatus="status">
							<tbody>
								<tr>
									<td align="center"><c:out value="${status.count }" /></td>
									<td align="center"><c:out value="${dtlsTOs.loadNumber }" /></td>
									<td align="center"><c:out
											value="${dtlsTOs.manifestDestCityDetails }" /></td>
									<td align="center"><c:out value="${dtlsTOs.docType }" /></td>
									<td align="center"><c:out value="${dtlsTOs.weight }" /></td>
									<td align="center"><c:out value="${dtlsTOs.lockNumber }" /></td>
									<td align="center"><c:out
											value="${dtlsTOs.receivedStatus }" /></td>
									<td align="center"><c:out value="${dtlsTOs.remarks }" /></td>
								</tr>
							</tbody>
						</c:forEach>
					</table> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>


			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="0" width="100%" border="0">

						<tr class="showTopLine">
							<td align="left" valign="top" colspan="10">
								<table cellpadding="5" cellspacing="0" width="100%" border="0">
									<!--  <tr class="showTopBtmLine"></tr> -->
									<tr class="showTopLine">
										<td width="10%" align="left" valign="top">Total Count :
											${loadReceiveLocalTO.totalBagPrint}</td>
										<br />
										<tr />
										<%-- <td>${loadMovementTO.totalBagPrint}</td>  --%>
										<tr>
											<td width="19%" align="left" valign="top">Total Weight :
												<span id="totalWeight">
											</td>
											<br />
											<tr />


										</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="left" valign="top" colspan="10">
								<table cellpadding="5" cellspacing="0" width="100%" border="0">
									<tr class="showTopBtmLine">
										<td width="10%" align="left" valign="top">Load Recd. On :
											${loadReceiveLocalTO.receiveDateTime}</td>
										<td width="10%" align="left" valign="top">At:
											${loadReceiveLocalTO.destOffice}</td>
										<td width="10%" align="left" valign="top">Hrs. :
											${loadReceiveLocalTO.actualArrival}</td>
										<tr>
											<td width="10%" align="left" valign="top">Load Recd. By
												:</td>
											<td width="10%" align="left" valign="top">Signature /
												Seal:</td>
										</tr>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="left" valign="top" colspan="10">
								<table cellpadding="5" cellspacing="0" width="100%" border="0">
									<tr class="showTopBtmLine">
										<td width="10%" align="left" valign="top">From :
											${loadReceiveLocalTO.destCity}</td>

									</tr>
								</table>
							</td>
						</tr>

						<tr>

						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br />

	</html:form>
</body>
</html>
