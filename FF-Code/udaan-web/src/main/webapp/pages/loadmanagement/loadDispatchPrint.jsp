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
body { /* font: normal 16px "Lucida Console", Monaco, monospace; */
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
	src="js/loadmanagement/loadDispatchPrint.js"></script>
</head>
<!-- onload="printpage();" -->
<body onload="hideurl();">
	<html:form action="/loadDispatch.do" styleId="loadManagementForm">

		<!-- <span id=curTime></span><br/> -->
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top"><input type="hidden"
					id="totalWeightHidden" value="${loadMovementTO.totalWeightPrint}" />
					<center>
						<bean:message key="label.print.header" />
						${loadMovementTO.destCity}<br />
					</center> <%-- <center>
						<b>
							${loadMovementTO.originOfficeTO.address1},${loadMovementTO.originOfficeTO.address2},${loadMovementTO.originOfficeTO.address3}</b>
					</center> --%> <%-- <center>
						${loadMovementTO.loggedInOfficeTO.officeName}
					</center>
					<br /> --%>
					<center>
						<bean:message key="label.print.gatePass" />
					</center> <!-- -------------------------------------------------------------------------------- -->
					</center> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
		</table>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<thead>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<!-- Header Row 1 -->
							<tr class="showTopLine">
								<td width="27%" align="left" valign="top"><bean:message
										key="label.print.dateTime" /> &nbsp ${loadMovementTO.dispatchDateTime}</td>
								<td width="27%" align="left" valign="top"><bean:message
										key="label.gatePassNo" />: &nbsp ${loadMovementTO.gatePassNumber}</td>
							</tr>
							<!-- Header Row 2 -->
							<tr>
								<td width="22%" align="left" valign="top"><bean:message
										key="label.print.origin" /> : &nbsp ${loadMovementTO.originOffice}</td>
								<td width="22%" align="left" valign="top"><bean:message
										key="label.print.bplDestination" /> &nbsp ${loadMovementTO.destOffice}</td>
								<!-- <th width="12%" align="left" valign="top">Br. Load No.:</th>
                  	<th width="17%" align="left" valign="top">2</th> -->
							</tr>
							<!-- Header Row 3 -->
							<tr>
								<td width="16%" align="left" valign="top"><bean:message
										key="label.mode" />: &nbsp ${loadMovementTO.transportMode}</td>
								<td width="16%" align="left" valign="top"><bean:message
										key="label.type" />: &nbsp ${loadMovementTO.serviceByType}</td>


							</tr>

							<!-- Header Row 4 -->
							<tr>
								<c:if test="${not empty loadMovementTO.serviceByType}">
									<td width="16%" align="left" valign="top">
										<%-- <bean:message key="label.coloader" /> --%>${loadMovementTO.serviceByType}
										:
									&nbsp ${loadMovementTO.loadMovementVendor}</td>
								</c:if>
								<td width="18%" align="left" valign="top">
									<%-- <bean:message key="label.flightNumber" /> --%>${loadMovementTO.transportModeLabelPrint}
									:
								&nbsp ${loadMovementTO.transportNumber} <c:if
										test="${not empty loadMovementTO.otherTransportNumber}">${loadMovementTO.otherTransportNumber} </c:if></td>

							</tr>

							<!-- Header Row 5 -->
							<tr>
								<td width="16%" align="left" valign="top"><bean:message
										key="label.print.ETD" />&nbsp ${loadMovementTO.expectedDeparture}</td>
								<td width="18%" align="left" valign="top"><bean:message
										key="label.print.ETA" />&nbsp ${loadMovementTO.expectedArrival}</td>
							</tr>

							<!-- Header Row 6 -->
							<tr>
								<%-- <th width="16%" align="left" valign="top"><bean:message
									key="label.vehicleNumber" /> :</th>
							<td>${loadMovementTO.otherVehicleNumber}</td> --%>
								<td width="18%" align="left" valign="top"><bean:message
										key="label.driverName" /> : &nbsp ${loadMovementTO.driverName}</td>
								<td width="16%" align="left" valign="top"><bean:message
										key="label.departure" />: &nbsp ${loadMovementTO.loadingTime}</td>
							</tr>

							<!-- 						<tr> -->
							<%-- 							<td width="16%" align="left" valign="top"><bean:message --%>
							<%-- 									key="label.departure" />:</td> --%>
							<%-- 							<td>${loadMovementTO.loadingTime}</td> --%>
							<!-- 							<td colspan="3" align="left" valign="top"></td> -->
							<!-- 						</tr> -->

						</table>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="3" cellspacing="2" width="100%" border="0"
							class="showLine">
							<thead>
								<tr class="showTopBtmLine">
									<td width="4%" align="center" valign="top"><bean:message
											key="label.print.serialNo" /></td>
									<td width="17%" align="center" valign="top"><bean:message
											key="label.bplMbplNo" /></td>
									<td width="16%" align="center" valign="top"><bean:message
											key="label.print.destinationCity" /></td>
									<td width="8%" align="center" valign="top"><bean:message
											key="label.print.documentType" /></td>
									<td width="10%" align="center" valign="top"><bean:message
											key="label.weight" /></td>
									<td width="14%" align="center" valign="top"><bean:message
											key="label.cdWeight" /></td>
									<td width="10%" align="center" valign="top"><bean:message
											key="label.lockNo" /></td>
									<td width="29%" align="center" valign="top"><bean:message
											key="label.print.CD.AWB.RR" /></td>
									<td width="6%" align="center" valign="top"><bean:message
											key="label.remarks" /></td>
								</tr>
							</thead>

							<c:forEach var="dtlsTOs"
								items="${loadMovementTO.loadDispatchDetailsTOs}"
								varStatus="status">
								<tbody>
									<tr>
										<td align="center"><c:out value="${status.count }" /></td>
										<td align="center"><c:out value="${dtlsTOs.loadNumber }" /></td>
										<td align="center"><c:out
												value="${dtlsTOs.manifestDestCity }" /></td>
										<td align="center"><c:out value="${dtlsTOs.docType }" /></td>
										<td align="center"><c:out value="${dtlsTOs.weight }" /></td>
										<td align="center"><c:out value="${dtlsTOs.cdWeight }" /></td>
										<td align="center"><c:out value="${dtlsTOs.lockNumber }" /></td>
										<td align="center"><c:out value="${dtlsTOs.tokenNumber }" /></td>
										<td align="center"><c:out value="${dtlsTOs.remarks }" /></td>
									</tr>
								</tbody>
							</c:forEach>

						</table>
					</td>
				</tr>




				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">

							<tr class="showTopLine">
								<td align="left" valign="top" colspan="10">
									<table cellpadding="5" cellspacing="0" width="100%" border="0">
										<tr>
											<td width="10%" align="left" valign="top">Total Count :
												${loadMovementTO.totalBagPrint}</td>

											<td width="10%" align="left" valign="top">Total Weight :
												<span id="totalWeight">
													${loadMovementTO.totalWeightPrint}</span>
											</td>

											<td width="10%" align="left" valign="top">Total CD
												Weight : ${loadMovementTO.totalCdWeightPrint}</td>
											<br />
											<tr />
											<!-- 										<tr> -->
											<!-- 											<td width="19%" align="left" valign="top">Total Weight : -->
											<!-- 												<span id="totalWeight"> -->
											<%-- 													${loadMovementTO.totalWeightPrint}</span> --%>
											<!-- 											</td> -->
											<!-- 											<br /> -->
											<!-- 											</tr> -->
											<!-- 											<tr /> -->

											<!-- 											<tr> -->
											<!-- 												<td width="10%" align="left" valign="top">Total CD -->
											<%-- 													Weight : ${loadMovementTO.totalCdWeightPrint}</td> --%>
											<!-- 												<br /> -->

											<!-- 												<tr /> -->

											<!-- 											</tr> -->
									</table>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top" colspan="10">
									<table cellpadding="5" cellspacing="0" width="100%" border="0">
										<tr class="showTopBtmLine">
											<td width="10%" align="left" valign="top">Load Recd. On
												:</td>
											<td width="10%" align="left" valign="top">At:</td>
											<td width="10%" align="left" valign="top">Hrs. :</td>
											<tr class="showBtmLine">
												<td width="10%" align="left" valign="top">Load Recd. By
													:</td>
												<td width="10%" align="left" valign="top">Signature /
													Seal:</td>
												<td></td>
											</tr>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="left" valign="top" colspan="10">
									<table cellpadding="5" cellspacing="0" width="100%" border="0">
										<tr class="showTopBtmLine">
											<%-- <td width="10%" align="left" valign="top">From :
											${loadMovementTO.destCity}</td> --%>
										</tr>
									</table>
								</td>
							</tr>

							<tr>

							</tr>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<br />
	</html:form>
</body>
</html>
