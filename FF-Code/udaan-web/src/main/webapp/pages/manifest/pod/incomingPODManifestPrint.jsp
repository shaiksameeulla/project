<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ff.manifest.pod.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UDAAN</title>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/manifest/pod/podManifestPrint.js"></script>
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
	size: auto;
	margin-top: 0mm
}
</style>
</head>
<body>
	<html:form action="/incomingPODManifest.do"
		styleId="incomingPODManifestForm">
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center>
					<center>
						<bean:message key="label.print.pod.in.mainTitle" />
					</center>
				</td>
			</tr>
		</table>

		<div style="width: 100%;">
			<c:forEach var="f_page" items="${mainList}" varStatus="page_status">
				<table>
					<tr>
						<td colspan="4">
							<div class="line"></div>
						</td>
					</tr>
					<tr>
						<td width="5%"><bean:message key="label.print.pod.origin" />
							:</td>
						<td width="15%">${podManifestDtls.dispachOfficeTO.officeName}-${podManifestDtls.dispachOfficeTO.address3}</td>
						<td width="5%"><bean:message
								key="label.print.pod.destination" /> :</td>
						<td width="7%">${podManifestDtls.destOffice}-${podManifestDtls.destCity}</td>
					</tr>
					<tr>
						<td width="6%"><bean:message key="label.print.pod.manifestNo" />
							:</td>
						<td width="15%">${podManifestDtls.manifestNo}</td>
						<td width="5%"><bean:message key="label.print.pod.datetime" />
							:</td>
						<td width="7%">${podManifestDtls.manifestDate}</td>
					</tr>
				</table>
				<div style="float: left; width: 49.5%;">
					<c:if test="${not empty f_page.firstCol}">
						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
								<td width="120px;" class="tdBorder"><bean:message
										key="label.pickup.SrNo" /></td>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.print.outmanifestDox.consg" /></td>
								<td width="160px;" class="tdBorder"><bean:message
										key="label.print.pod.recdDate" /></td>
								<td width="160px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.pod.deldDate" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>

							<c:forEach var="f_col" items="${f_page.firstCol}"
								varStatus="f_status">
								<tr>
									<td width="70px;" class="tdBorder">${f_col.srNo}</td>
									<td width="200px;" class="tdBorder">${f_col.consgNumber}</td>
									<td width="140px;" class="tdBorder">${f_col.receivedDate}</td>
									<td width="140px;" class="tdBorder tdBorderRight">${f_col.dlvDate}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="4">
									<div class="line"></div>
								</td>
							</tr>

						</table>
					</c:if>
				</div>
				<div style="float: right; width: 49.5%;">
					<c:if test="${not empty f_page.secondCol}">
						<table>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<tr>
								<td width="120px;" class="tdBorder"><bean:message
										key="label.pickup.SrNo" /></td>
								<td width="110px;" class="tdBorder"><bean:message
										key="label.print.outmanifestDox.consg" /></td>
								<td width="160px;" class="tdBorder"><bean:message
										key="label.print.pod.recdDate" /></td>
								<td width="160px;" class="tdBorder tdBorderRight"><bean:message
										key="label.print.pod.deldDate" /></td>
							</tr>
							<tr>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
								<td class="line"></td>
							</tr>
							<c:forEach var="s_col" items="${f_page.secondCol}"
								varStatus="s_status">
								<tr>
									<td width="70px;" class="tdBorder">${s_col.srNo}</td>
									<td width="200px;" class="tdBorder">${s_col.consgNumber}</td>
									<td width="140px;" class="tdBorder">${s_col.receivedDate}</td>
									<td width="140px;" class="tdBorder tdBorderRight">${s_col.dlvDate}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="4">
									<div class="line"></div>
								</td>
							</tr>
						</table>
					</c:if>
				</div>
				<c:if test="${mainList.size() ne page_status.count}">
					<div class="pageBreak"></div>
				</c:if>
			</c:forEach>
		</div>
		<div style="width: 100%; float: left;">
			<bean:message key="label.print.TotalNoConsg" />
			${totSize}
		</div>
	</html:form>
</body>
</html>