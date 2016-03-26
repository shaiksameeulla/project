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
body { /* font:normal 16px "Lucida Console", Monaco, monospace; */
	/* font-family: monospace;
	font-size: 16px; */
	font: normal 12px "Verdana", Monaco, monospace;
}

.tdBorder {
	border-left: 2px dashed black;
	text-align: center;
	line-height: 15px
}

.tdBorderRight {
	border-right: 2px dashed black;
	text-align: center;
	line-height: 15px
}

.line {
	border-top: 2px dashed black;
}

.pageBreak {
	page-break-after: always;
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
	src="js/manifest/bplOutManifestDoxPrint.js"></script>
</head>
<body>
	<html:form action="/bplOutManifestDox.do"
		styleId="bplOutManifestDoxForm">
		<!-- ######### Start ######### main header title of page ###########-->
		<table cellpadding="0" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center>
					<center>
						<bean:message key="label.print.bplHeader" />
					</center>
				</td>
			</tr>
		</table>
		<!-- ######### End ######### main header title of page ###########-->
		<!-- ################## Main Div Starts ###########-->
		<div style="width: 100%;">
			<!-- ################## for loop Starts ###########-->
			<c:forEach var="page" items="${mainList}" varStatus="page_status">
				<table>
					<tr>
						<td colspan="6">
							<div class="line"></div>
						</td>
					</tr>
					<tr>
						<td width="5%" align="left" valign="top"><bean:message
								key="label.print.headerOrigin" /></td>
						<td valign="top" width="20%">${bplOutManifestDoxTO.loginOfficeAddress3}-${bplOutManifestDoxTO.loginOfficeName}</td>
						<td align="left" valign="top" width="8%"><bean:message
								key="label.print.bplDestination" /></td>
						<td valign="top" width="20%">${bplOutManifestDoxTO.destinationCityTO.cityName}-${bplOutManifestDoxTO.destinationOfficeName}</td>

						<td width="14%" align="left" valign="top"><bean:message
								key="label.print.bagLocNo" /></td>
						<td align="left" valign="top" width="5%">${bplOutManifestDoxTO.bagLockNo}</td>
					</tr>
					<tr>
						<td width="9%" align="left" valign="top"><bean:message
								key="label.print.bplNo" /></td>
						<td valign="top" width="28%">${bplOutManifestDoxTO.manifestNo}</td>
						<td width="5%" align="left" valign="top"><bean:message
								key="label.baBookingParcel.dateTime" /></td>
						<td valign="top" width="30%">${bplOutManifestDoxTO.manifestDate}</td>
					</tr>
				</table>
				<!-- ################## Left Div Starts ###########-->
				<div style="float: left; width: 50%;">
					<c:if test="${not empty page.leftBPLDoxList}">
						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
								<td width="100px;" class="tdBorder"><bean:message
										key="label.baBookingParcel.serialNo" /></td>
								<td width="150px;" class="tdBorder"><bean:message
										key="label.manifestGrid.manifestNo" /></td>
								<td width="150px;" class="tdBorder"><bean:message
											key="label.manifestGrid.weight" /></td>
								<td width="150px;" class="tdBorder"><bean:message
											key="label.manifestGrid.manifestType" /></td>			
								<td width="235px;" class="tdBorder"><bean:message
										key="label.manifestGrid.destination" /></td>
								<td width="140px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.noOfCN" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<c:forEach var="f_col" items="${page.leftBPLDoxList}"
								varStatus="f_status">
								<tr>
									<td width="100px;" class="tdBorder">${f_col.srNo}</td>
									<td width="150px;" class="tdBorder">${f_col.manifestNo}</td>
									<td width="150px;" class="tdBorder">${f_col.weight}</td>
									<td width="150px;" class="tdBorder"><c:if test="${not empty f_col.manifestOpenType}">${f_col.manifestOpenType}</c:if>
									<c:if test="${not empty f_col.bplManifestType}">${f_col.bplManifestType}</c:if></td>							
									<td width="235px;" class="tdBorder">${f_col.destCity}</td>
									<td width="140px;" class="tdBorder tdBorderRight">${f_col.noOfConsignment}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="6">
									<div class="line"></div>
								</td>
							</tr>

						</table>
					</c:if>
				</div>
				<!-- ################## Left Div End ###########-->
				<!-- ################## right Div Starts ###########-->
				<div style="float: right; width: 50%;">
					<c:if test="${not empty page.rightBPLDoxList}">
						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
							<td width="100px;" class="tdBorder"><bean:message
										key="label.baBookingParcel.serialNo" /></td>
								<td width="150px;" class="tdBorder"><bean:message
										key="label.manifestGrid.manifestNo" /></td>
								<td width="150px;" class="tdBorder"><bean:message
											key="label.manifestGrid.weight" /></td>
								<td width="150px;" class="tdBorder"><bean:message
											key="label.manifestGrid.manifestType" /></td>		
								<td width="235px;" class="tdBorder"><bean:message
										key="label.manifestGrid.destination" /></td>
								<td width="140px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.noOfCN" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<c:forEach var="s_col" items="${page.rightBPLDoxList}"
								varStatus="f_status">
								<tr>
									<td width="100px;" class="tdBorder">${s_col.srNo}</td>
									<td width="150px;" class="tdBorder">${s_col.manifestNo}</td>
									<td width="150px;" class="tdBorder">${s_col.weight}</td>
									<td width="150px;" class="tdBorder"><c:if test="${not empty s_col.manifestOpenType}">${s_col.manifestOpenType}</c:if>
									<c:if test="${not empty s_col.bplManifestType}">${s_col.bplManifestType}</c:if></td>
									<td width="235px;" class="tdBorder">${s_col.destCity}</td>
									<td width="140px;" class="tdBorder tdBorderRight">${s_col.noOfConsignment}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="6">
									<div class="line"></div>
								</td>
							</tr>

						</table>
					</c:if>
				</div>
				<!-- ################## right Div End ###########-->
			</c:forEach>
			<!-- ################## for loop Ends ###########-->

			<div style="float: left; width: 100%;">
				<table>
					<tr>
						<td><bean:message key="label.print.TotalNoPackets" />${bplOutManifestDoxTO.rowcount}</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><bean:message key="label.print.TotalNoConsig" />${bplOutManifestDoxTO.consigTotal}</td>

					</tr>
				</table>
			</div>
		</div>
		<!-- ################## Main Div End ###########-->
	</html:form>
</body>
</html>