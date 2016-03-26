<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UDAAN</title>
<style type="text/css">
body { /*  font:normal 16px "Lucida Console", Monaco, monospace; */
	font: normal 12px "Verdana", Monaco, monospace;
	/* font-family: monospace;
	font-size: 16px; */
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
	src="js/booking/BABookingPrint.js"></script>
</head>
<html:form action="/baBookingParcel.do" styleId="baBookingParcelForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr class="showTopBtmLine">
				<td align="left" valign="top">
					<%-- <input type="hidden" id="bizAssociateCode" value="${baBookingTOs[0].bizAssociateCode}"/> --%>

					<center>
						<bean:message key="label.print.header" />
					</center>
					<center>
						<bean:message key="label.print.baBookingDetails" />
					</center>

				</td>
			</tr>
		</table>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<thead>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="3" cellspacing="0" width="100%" border="0">
							<!-- Header Row 1 -->
							<tr class="showTopLine">
								<td width="15%" align="left" valign="top"><bean:message
										key="label.baBookingParcel.dateTime" /></td>
								<td>${baBookingTOs[0].bookingDate}</td>
								<td width="50%" align="left" valign="top"><bean:message
										key="label.print.baCode" /> / <bean:message
										key="label.print.baName" />:
									${baBookingTOs[0].bizAssociateCode}</td>
								<td><span id="baCode"></span></td>
								<!-- 	<th width="18%"  align="left" valign="top"> </th>
                  	<td><span id="baName"></span></td>  -->
							</tr>

						</table>
					</td>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="1" width="100%" border="0"
							id="baBookingPrint" class="showLine">
							<thead>
								<tr class="showTopBtmLine" style="line-height: 1.2em">
									<td width="7%" align="left" valign="top"><bean:message
											key="label.baBookingParcel.serialNo" /></td>
									<td width="10%" align="left" valign="top"><bean:message
											key="label.manifestGrid.consgNo" /></td>
									<td width="7%" align="left" valign="top"><bean:message
											key="label.print.docType" /></td>
									<td width="3%" align="left" valign="top"><bean:message
											key="label.baBookingDox.destination" /></td>
									<td width="3%" align="left" valign="top"><bean:message
											key="label.baBookingDox.pincode" /></td>
									<td width="7%" align="left" valign="top"><bean:message
											key="label.baBookingParcel.actualWeight" /></td>
									<td width="12%" align="left" valign="top"><bean:message
											key="label.print.booking.chargWt" /></td>
									<td width="1%" align="left" valign="top"><bean:message
											key="label.print.booking.pcs" /></td>
									<td width="3%" align="left" valign="top"><bean:message
											key="label.print.baFuelChrg" /></td>
									<td width="7%" align="left" valign="top"><bean:message
											key="label.print.baEdCess" /></td>
									<td width="5%" align="left" valign="top"><bean:message
											key="label.print.baHedCess" /></td>
									<td width="10%" align="left" valign="top"><bean:message
											key="label.print.booking.insurance" /></td>
									<%-- <td width="10%" align="left" valign="top"><bean:message key="label.print.baRevenue"/></td> --%>
									<td width="4%" align="left" valign="top"><bean:message
											key="label.print.baSTax" /></td>
<%-- 									<td width="4%" align="left" valign="top"><bean:message --%>
<%-- 											key="label.baBookingDox.baAmt" /></td> --%>
<%-- 									<td width="4%" align="left" valign="top"><bean:message --%>
<%-- 											key="label.baBookingDox.codAmt" /></td> --%>
									<td width="7%" align="left" valign="top"><bean:message
											key="label.print.booking.actaulAmt" /></td>



								</tr>
							</thead>
							<c:forEach var="dtlsTOs" items="${baBookingTOs}"
								varStatus="status">
								<tbody>
									<tr style="line-height: 1.4em">
										<td><c:out value="${status.count }" /></td>
										<td><c:out value="${dtlsTOs.consgNumber}" /></td>
										<td><c:out value="${dtlsTOs.consgTypeName }" /></td>
										<td><c:out value="${dtlsTOs.cityName }" /></td>
										<td><c:out value="${dtlsTOs.pincode }" /></td>
										<td align="right"><c:out value="${dtlsTOs.finalWeight }" /></td>
										<td align="right"><c:out value="${dtlsTOs.finalWeight }" /></td>
										<td align="right"><c:out value="${dtlsTOs.noOfPieces }" /></td>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.fuelChg}" /></td>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.eduCessChg }" /></td>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.higherEduCessChg }" /></td>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.riskSurChg }" /></td>
										<%-- <td ><c:out value="${dtlsTOs.consigmentTO.consgPriceDtls.freightChg}"/></td> --%>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.serviceTax }" /></td>

<%-- 										<td align="right"><c:out --%>
<%-- 												value="${dtlsTOs.consigmentTO.consgPriceDtls.baAmt }" /></td> --%>
<%-- 										<td align="right"><c:out --%>
<%-- 												value="${dtlsTOs.consigmentTO.consgPriceDtls.codAmt }" /></td> --%>
										<td align="right"><c:out
												value="${dtlsTOs.consigmentTO.consgPriceDtls.finalPrice}" /></td>


									</tr>
							</c:forEach>
							<tr class="showTopLine">
								<td><bean:message key="label.print.Total" />:</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td align="right">${finalWeight}</td>
								<td align="right">${finalWeight}</td>
								<td align="right">${totalNoOfPcs}</td>
								<td align="right">${fuelChg}</td>
								<td align="right">${eduCessChg}</td>
								<td align="right">${higherEduCessChg}</td>
								<td align="right">${risSurchrg}</td>
								<td align="right">${serviceTax}</td>
								<%-- <td>${slabChg}</td> --%>
<%-- 								<td align="right">${baAmt}</td> --%>
<%-- 								<td align="right">${codAmt}</td> --%>
								<td align="right">${finalAmt}</td>
								<!-- <td>&nbsp;</td> -->
								<!-- <td>&nbsp;</td> -->
							</tr>
							</tbody>
						</table>
					</td>
				</tr>


				<tr>
					<td align="left" valign="top" colspan="10">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<!-- <tr class="showTopBtmLine">
						<td width="11%" align="left" valign="top">Load Recd. On : </td>
						<td width="2%" align="left" valign="top">At: </td>
						<td width="10%" align="left" valign="top">Hrs. : </td>
						<td width="10%"></td>
						<tr>
						<td width="6%" align="left" valign="top">Load Recd. By : </td>
						<td width="10%"></td>
						<td width="22%" align="left" valign="top">Signature / Seal: </td>
						</tr>
					</tr> -->

							<tr class="showTopLine">
								<td width="19%" align="left" valign="top">Load Received
									On:${todayDate}</td>
								<td width="9%" align="left" valign="top"></td>
								<td width="4%" align="right" valign="top">At:</td>
								<td width="15%" align="left" valign="top">${baBookingOffice.officeName}-${baBookingOffice.officeCode}</td>
								<td width="5%" align="right" valign="top">Hrs:</td>
								<td width="15%" align="left" valign="top">${todayTime}</td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td width="19%" align="left" valign="top">Load Received By:</td>
								<td width="9%" align="left" valign="top" colspan="2">&nbsp;</td>
								<td align="left" valign="top" colspan="2">Signature / Seal:</td>
								<td width="18%" rowspan="2" align="left" valign="top">&nbsp;</td>
							</tr>
							<!-- 
                            <tr>
                               <td width="19%" align="left" valign="top">Date:</td>
                                <td width="13%" align="left" valign="top">&nbsp;</td>
                                <td width="4%" align="right" valign="top">Time:</td>
                                <td  align="left" valign="top">&nbsp;</td>    
                                <td rowspan="2" align="left" valign="top">&nbsp;</td>                                
                            </tr> -->

						</table>
					</td>
				</tr>
			</tbody>
		</table>
</html:form>
</body>
</html>