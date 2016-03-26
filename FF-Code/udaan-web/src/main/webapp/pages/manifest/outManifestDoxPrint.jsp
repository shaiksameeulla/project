
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
	/* font:normal 16px Arial; */
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
	src="js/manifest/outManifestDoxPrint.js"></script>
</head>
<body>
	<html:form action="/outManifestDox.do" method="post"
		styleId="outManifestDoxForm">
		<!-- ######### Start ######### main header title of page ###########-->
		<table cellpadding="0" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center>
					<center>
						<bean:message key="label.print.outmanifestDox" />
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
						<td colspan="4">
							<div class="line"></div>
						</td>
					</tr>
					<tr>
						<td width="5%"><bean:message key="label.print.headerOrigin" /></td>
						<td width="15%">${outManifestDoxTO.loginOfficeName}</td>
						<td width="5%"><bean:message key="label.print.bplDestination" /></td>
						<td width="7%">${outManifestDoxTO.destinationCityTO.cityName}-${outManifestDoxTO.destinationOfficeName}</td>
					</tr>
					<tr>
						<td width="6%"><bean:message key="label.print.ogmNo" /></td>
						<td width="15%">${outManifestDoxTO.manifestNo}</td>
						<td width="5%"><bean:message key="label.print.dateTime" /></td>
						<td width="7%">${outManifestDoxTO.manifestDate}</td>
						<td width="5%"><bean:message key="label.print.manifestType" /></td>
						
						 <c:choose>
						<c:when test="${outManifestDoxTO.loginOfficeType=='BO'}">
							
							 <c:if test="${outManifestDoxTO.manifestOpenType eq 'O'}">
								<td width="7%">Direct Manifest</td>
							</c:if>
							<c:if test="${outManifestDoxTO.manifestOpenType eq 'P'}">
								<td width="7%">Open manifest</td>
							</c:if> 
						
						</c:when>
						<c:otherwise>
						<c:choose>
							
							<c:when test="${outManifestDoxTO.loginOfficeType=='HO'}">
							
							
							 <c:if test="${outManifestDoxTO.ogmManifestType eq 'TRANS'}">
								<td width="7%">TRANSHIPMENT</td>
							</c:if>
							<c:if test="${outManifestDoxTO.ogmManifestType eq 'PURE'}">
								<td width="7%">PURE</td>
							</c:if> 
						</c:when>
						</c:choose>
						
						</c:otherwise>
					</c:choose>
						
					</tr>
				</table>
				<!-- ################## Left Div Starts ###########-->
				<div style="float: left; width: 49.5%;">
					<c:if test="${not empty page.leftOGMList}">

						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.manifestGrid.serialNo" /></td>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.print.outmanifestDox.consg" /></td>
								<td width="205px;" class="tdBorder"><bean:message
										key="label.print.InBpl.destinations" /></td>
								<td width="100px;" class="tdBorder"><bean:message
										key="label.print.weight" /></td>
								<td width="85px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.outmanifestDox.amt" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>

							<c:forEach var="f_col" items="${page.leftOGMList}"
								varStatus="f_status">
								<c:if test="${not empty f_col.consgNo}">
									<tr>
										<td width="70px;" class="tdBorder">${f_col.srNo}</td>
										<td width="200px;" class="tdBorder">${f_col.consgNo}</td>
										<td width="140px;" class="tdBorder">${f_col.destCity}</td>
										<td width="120px;" class="tdBorder">${f_col.bkgWeight}</td>
										<td width="100px;" class="tdBorder tdBorderRight">${f_col.amount}</td>
									</tr>
								</c:if>
								<c:if test="${not empty f_col.comailNo}">
									<tr>
										<td width="70px;" class="tdBorder">${f_col.srNo}</td>
										<td width="200px;" class="tdBorder">${f_col.comailNo}</td>
										<td width="140px;" class="tdBorder"></td>
										<td width="120px;" class="tdBorder"></td>
										<td width="100px;" class="tdBorder tdBorderRight"></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr>
								<td colspan="5">
									<div class="line"></div>
								</td>
							</tr>

						</table>
					</c:if>
				</div>
				<!-- ################## Left Div End ###########-->
				<!-- ################## right Div Starts ###########-->
				<div style="float: right; width: 49.5%;">
					<c:if test="${not empty page.rightOGMList}">
						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.manifestGrid.serialNo" /></td>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.print.outmanifestDox.consg" /></td>
								<td width="205px;" class="tdBorder"><bean:message
										key="label.print.InBpl.destinations" /></td>
								<td width="100px;" class="tdBorder"><bean:message
										key="label.print.weight" /></td>
								<td width="85px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.outmanifestDox.amt" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>

							<c:forEach var="s_col" items="${page.rightOGMList}"
								varStatus="s_status">
								<c:if test="${not empty s_col.consgNo}">
									<tr>
										<td width="70px;" class="tdBorder">${s_col.srNo}</td>
										<td width="200px;" class="tdBorder">${s_col.consgNo}</td>
										<td width="140px;" class="tdBorder">${s_col.destCity}</td>
										<td width="120px;" class="tdBorder">${s_col.bkgWeight}</td>
										<td width="100px;" class="tdBorder tdBorderRight">${s_col.amount}</td>
									</tr>
								</c:if>
								<c:if test="${not empty s_col.comailNo}">
									<tr>
										<td width="70px;" class="tdBorder">${s_col.srNo}</td>
										<td width="200px;" class="tdBorder">${s_col.comailNo}</td>
										<td width="140px;" class="tdBorder"></td>
										<td width="120px;" class="tdBorder"></td>
										<td width="100px;" class="tdBorder tdBorderRight"></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr>
								<td colspan="5">
									<div class="line"></div>
								</td>
							</tr>

						</table>
					</c:if>
				</div>
				<!-- ################## right Div End ###########-->
				<c:if test="${mainList.size() ne page_status.count}">
					<div class="pageBreak"></div>
				</c:if>
			</c:forEach>
			<!-- ################## for loop Ends ###########-->

			<div style="float: left; width: 100%;">
				<table>
					<tr>
						<td><bean:message key="label.print.TotalNoConsig" />
							${outManifestDoxTO.totalConsg}</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><bean:message key="label.totalWeight" /> :
							${outManifestDoxTO.consigTotalWt}</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><bean:message key="label.print.totalLcAmt" />
							${outManifestDoxTO.totalLcAmount}</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- ################## Main Div End ###########-->
	</html:form>
</body>

</html>
