
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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

table tr .showLine td {
	border-left: 2px dashed black;
}

table tr .showLine td:last-child {
	border-left: 2px dashed black;
	border-right: 2px dashed black;
}

.header {
	display: table-header-group;
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
</style>
<style type="text/css" media="print">
@page {
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}
</style>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/rthrto/rthRtoManifestDoxPrint.js"></script>
</head>

<html:form action="/rthRtoManifestDox.do" method="post"
	styleId="rthRtoManifestDoxForm">
	<body>

		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b> ${rthRtoManifestTO.originOfficeTO.address1},${rthRtoManifestTO.originOfficeTO.address2},${rthRtoManifestTO.originOfficeTO.address3}-${rthRtoManifestTO.pincode} </b><br/></center> --%>
					<%-- <center><b><bean:message key="label.print.rtoManifestDox" /> </b></center><br/> --%>
					<%-- <center>${rthRtoManifestTO.loggedInOfficeName}-${rthRtoManifestTO.loggedInOfficeCity}</center><br/> --%>
					<c:choose>
						<c:when test="${rthRtoManifestTO.manifestType== 'R'}">
							<center>
								<bean:message key="label.print.rtoManifestPpx" />
							</center>
						</c:when>
						<c:otherwise>
							<center>
								<bean:message key="label.print.rthManifestPpx" />
							</center>
						</c:otherwise>
					</c:choose> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="2" width="100%" border="0">
						<tr class="showTopLine">
							<td width="20%" align="left"><bean:message
									key="label.print.headerOrigin" /></td>
							<td width="30%" align="left">${rthRtoManifestTO.loggedInOfficeCity}-${rthRtoManifestTO.loggedInOfficeName}</td>
							<td width="20%" align="left"><bean:message
									key="label.print.bplDestination" /></td>
							<%-- <td width="98%" align="left"  colspan="5">${outManifestParcelTO.destinationCityTO.cityName}-${outManifestParcelTO.destinationOfficeName}</td> --%>
							<td width="30%" align="left" colspan="5">${rthRtoManifestTO.destCityTO.cityName}-${rthRtoManifestTO.destOfficeTO.officeName}</td>

						</tr>
						<tr>
							<c:choose>
								<c:when test="${rthRtoManifestTO.manifestType== 'R'}">
									<td width="25%" align="left"><bean:message
											key="label.print.rtoManifestNumber" /></td>
								</c:when>
								<c:otherwise>
									<td width="25%" align="left"><bean:message
											key="label.print.rthManifestNumber" /></td>
								</c:otherwise>
							</c:choose>
							<td width="30%" align="left">${rthRtoManifestTO.manifestNumber}</td>
							<td width="5%" align="left"><bean:message
									key="label.print.dateTime" /></td>
							<td width="30%" align="left">${rthRtoManifestTO.manifestDate}</td>
						</tr>
					</table> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>

			<tr>
				<td align="left" valign="top">
					<table cellpadding="3" cellspacing="2" width="100%" border="0"
						class="showLine">
						<thead>

							<tr class="showTopBtmLine">
								<td width="2%" align="center" valign="top"><bean:message
										key="label.manifestGrid.serialNo" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.outmanifestDox.consg" /></td>
								<td width="5%" align="center" valign="top"><bean:message
										key="label.print.rthRtopincode" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rtoRthReceivedDate" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rthRtoPieces" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rtoRthWeight" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rthRtoContents" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rthRtoValue" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rtoRthToPayAmt" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rtoRthCodAmt" /></td>
								<td width="3%" align="center" valign="top"><bean:message
										key="label.print.rthRtoReasonAndCode" /></td>
							</tr>
						</thead>
						<!-- Use C:FOREACH loop to display consignment numbers -->
						<tbody>
							<c:forEach var="dtlsTOs"
								items="${rthRtoManifestTO.rthRtoDetailsTOs}" varStatus="status">
								<tr>
									<td align="center"><c:out value="${status.count}" /></td>
									<td align="center"><c:out value="${dtlsTOs.consgNumber}" /></td>
									<td align="center"><c:out value="${dtlsTOs.pincode}" /></td>
									<td align="center"><c:out value="${dtlsTOs.receivedDate}" /></td>
									<td align="center"><c:out value="${dtlsTOs.numOfPc}" /></td>
									<td align="center"><c:out value="${dtlsTOs.actualWeight}" /></td>
									<td align="center"><c:choose>
											<c:when test="${empty dtlsTOs.otherContent}">
												<c:out value="${dtlsTOs.cnContentName}" />
											</c:when>
											<c:otherwise>
												<c:out value="${dtlsTOs.otherContent}" />
											</c:otherwise>
										</c:choose></td>
									<%--  <td align="center"><c:if test="${dtlsTOs.cnContentName eq 'Others'}"><c:out value="${dtlsTOs.otherContent}"/></c:if> <c:if test="${dtlsTOs.cnContentName ne 'Others'}"><c:out value="${dtlsTOs.cnContentName}"/></c:if></td> --%>
									<%--  <td align="center"><c:out value="${dtlsTOs.cnContent}"/></td> --%>
									<td align="center"><c:out value="${dtlsTOs.declaredValue}" /></td>
									<td align="center"><c:out value="${dtlsTOs.toPayAmt}" /></td>
									<td align="center"><c:out value="${dtlsTOs.codAmt}" /></td>
									<td align="center"><c:out
											value="${dtlsTOs.reasonName}-${dtlsTOs.reasonCode}" /></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>

				</td>
			</tr>


			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="2" width="100%" border="0">
						<!-- <tr>
	         	<td colspan="10" align="left" valign="top">
	         	--------------------------------------------------------------------------------
	         	</td>
	         </tr> -->
						<tr>
							<td width="18" align="left" valign="top">
								<table cellpadding="2" cellspacing="2" width="100%" border="0">
									<tr class="showTopLine">
										<td width="24%"></td>
										<th width="40%" align="left" valign="top">&nbsp;</th>
										<th width="40%" align="left" valign="top">&nbsp;</th>
									</tr>
									<tr>
										<td width="24%" colspan="2"><bean:message
												key="label.print.TotalNoConsig" />
											${rthRtoManifestTO.rthRtoDetailsTOs[0].consgCount}</td>
										<th width="40%" align="left" valign="top">&nbsp;</th>
										<th width="40%" align="left" valign="top">&nbsp;</th>
									</tr>
									<tr>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
									</tr>
									<tr>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
										<th align="left">&nbsp;</th>
									</tr>
									<!-- <tr class="showBtmLine">               	
                  	<th width="25%" align="left" valign="top" colspan="2">MANIFEST PREPARED BY:</th>
                    <th align="left" valign="top">&nbsp;</th>
                    <th align="left" valign="top">Signature: </th>
              	</tr>
                 <tr>
                    	<th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                    </tr>
                    <tr>
                    <td align="left" valign="top" colspan="4">
                        <table cellpadding="2" cellspacing="4" width="100%" border="0">
                            <tr>
                                <th width="19%" align="left" valign="top">Load Received On:</th>
                                <th width="9%" align="left" valign="top"></th>
                                <th width="4%" align="right" valign="top">At:</th>
                                <th width="15%" align="left" valign="top"></th>
                                <th width="5%" align="right" valign="top">Hrs:</th>                         
                                <th width="15%" align="left" valign="top"></th>
                            </tr>
                            <tr>
                            </tr>
                            <tr>
                                <th width="19%" align="left" valign="top">Load Received By:</th>
                                <th width="9%"  align="left" valign="top" colspan="2">&nbsp;</th>
                                <th align="right" valign="top" colspan="2">Signature / Seal:</th>                                
                                <th width="18%" rowspan="2" align="left" valign="top">&nbsp;</th>
                            </tr>
                           
                            <tr>
                               <th width="19%" align="left" valign="top">Date:</th>
                                <th width="13%" align="left" valign="top">&nbsp;</th>
                                <th width="4%" align="right" valign="top">Time:</th>
                                <th  align="left" valign="top">&nbsp;</th>    
                                <th rowspan="2" align="left" valign="top">&nbsp;</th>                                
                            </tr>
                        </table>
                    </td>
                </tr> -->
								</table>
							</td>
						</tr>


					</table>
				</td>
			</tr>
		</table>
</html:form>
</body>
</html>
