
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
<script type="text/javascript" charset="utf-8"
	src="js/manifest/thirdPartyBPLOutManifestPrint.js"></script>

</head>

<html:form action="/thirdPartyBPLOutManifest.do" method="post"
	styleId="thirdPartyBPLOutManifestForm">
	<body>
		<div id="printPopup">
			<table cellpadding="2" cellspacing="0" width="100%" border="0">
				<tr>
					<td align="left" valign="top">
						<center>
							<bean:message key="label.print.header" />
						</center> <%-- <center><b>   ${thirdPartyBPLTO.loginOfficeAddress1},${thirdPartyBPLTO.loginOfficeAddress2},${thirdPartyBPLTO.loginOfficeAddress3}-${thirdPartyBPLTO.loginOfficePincode}</b><br/></center> --%>
						<%-- <center>${thirdPartyBPLTO.loginOfficeName}-${thirdPartyBPLTO.loginOfficeAddress3}</center><br/> --%>
						<center>
							<bean:message key="label.print.thirdPartyHeader" />
						</center> <!-- -------------------------------------------------------------------------------- -->
					</td>
				</tr>
			</table>
			<table cellpadding="2" cellspacing="0" width="100%" border="0">
				<thead>
					<tr>
						<td align="left" valign="top">
							<table cellpadding="2" cellspacing="2" width="100%" border="0">
								<tr class="showTopLine">
									<td width="2%" align="left"><bean:message
											key="label.print.branch" /></td>
									<td width="18%" align="left">${thirdPartyBPLTO.loginOfficeName}</td>
									<td width="5%" align="left"><bean:message
											key="label.print.loadNo" /></td>
									<td width="7%" align="left" colspan="1">${thirdPartyBPLTO.loadNoId}</td>

								</tr>
								<tr>
									<td width="7%" align="left"><bean:message
											key="label.print.bplNo" /></td>
									<td width="40%" align="left">${thirdPartyBPLTO.manifestNo}</td>
									<td width="2%" align="left"><bean:message
											key="label.print.dateTime" /></td>
									<td width="40%" align="left">${thirdPartyBPLTO.manifestDate}</td>
								</tr>

								<tr>

									<td width="2%" align="left" colspan="2"><bean:message
											key="label.print.thirdPartyName" /></td>
									<td align="left" width="15%" colspan="2"><c:if
											test="${not empty thirdPartyBPLTO.thirdPartyName }">${thirdPartyBPLTO.thirdPartyName}</c:if>
										- <c:if test="${not empty thirdPartyBPLTO.vendorCode }">${thirdPartyBPLTO.vendorCode}</c:if></td>
									<%-- <td width="98%" align="left" >${manifestTO.manifestProcessTo.baId}</td> --%>
									<%-- <td width="98%" align="left" colspan="5">${thirdPartyBPLTO.thirdPartyType}</td> --%>
								</tr>
							</table> <!-- -------------------------------------------------------------------------------- -->
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
										<td width="5%" align="center" valign="top"><bean:message
												key="label.manifestGrid.serialNo" /></td>
										<td width="20%" align="center" valign="top"><bean:message
												key="label.print.consgAndmanifest" /></td>
										<td width="15%" align="center" valign="top"><bean:message
												key="label.print.origin" /></td>
										<td width="15%" align="center" valign="top"><bean:message
												key="label.print.atm" /></td>
										<td width="10%" align="center" valign="top"><bean:message
												key="label.print.weight" /></td>
										<td width="17%" align="center" valign="top"><bean:message
												key="label.print.contents" /></td>
										<td width="11%" align="center" valign="top"><bean:message
												key="label.print.consigValue" /></td>
										<td width="12%" align="center" valign="top"><bean:message
												key="label.print.toPayCod" /></td>
									</tr>
								</thead>
								<tbody>

									<!-- Use C:FOREACH loop to display consignment numbers -->
									<c:forEach var="dtlsTOs"
										items="${thirdPartyBPLTO.thirdPartyBPLDetailsListTO}"
										varStatus="status">
										<tr>

											<c:if test="${dtlsTOs.embeddedType=='C'}">
												<td align="center"><c:out value="${status.count }" /></td>
												<td align="center"><c:out value="${dtlsTOs.consgNo}" /></td>
												<td align="center"><c:out
														value="${dtlsTOs.bookingOffName}" /></td>
												<td align="center"><c:out
														value="${dtlsTOs.deliveryAttempt}" /></td>
												<td align="center"><c:out value="${dtlsTOs.weight }" /></td>
												<td align="center"><c:choose>
														<c:when test="${empty dtlsTOs.otherContent}">
															<c:out value="${dtlsTOs.cnContent}" />
														</c:when>
														<c:otherwise>
															<c:out value="${dtlsTOs.otherContent}" />
														</c:otherwise>
													</c:choose></td>
												<%-- <td align="center"><c:if test="${dtlsTOs.cnContent eq 'Others'}"><c:out value="${dtlsTOs.otherContent}"/></c:if> <c:if test="${dtlsTOs.cnContent ne 'Others'}"><c:out value="${dtlsTOs.cnContent}"/></c:if></td> --%>
												<%--  <td align="center"><c:out value="${dtlsTOs.cnContent }"/></td> --%>
												<td align="center"><c:out
														value="${dtlsTOs.declaredValues}" /></td>
												<td align="center"><c:if
														test="${not empty dtlsTOs.toPayAmts}">
														<c:out value="${dtlsTOs.toPayAmts}" />
													</c:if> <c:if test="${not empty dtlsTOs.codAmts}">/<c:out
															value="${dtlsTOs.codAmts}" /></</c:if></td>
												<%-- <td align="center"><c:out value="${dtlsTOs.codAmts}"/></td> --%>
											</c:if>

											<c:if test="${dtlsTOs.embeddedType=='M'}">
												<td align="center"><c:out value="${status.count }" /></td>
												<td align="center"><c:out value="${dtlsTOs.manifestNo}" /></td>
												<td align="center"><c:out
														value="${dtlsTOs.bookingOffName}"></c:out></td>
												<td align="center"></td>
												<td align="center"><c:out value="${dtlsTOs.weight }" /></td>
												<td align="center"></td>
												<td align="center"></td>
												<td align="center"></td>
											</c:if>

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
												<td width="24%" colspan="1"></td>
												<td width="15%" colspan="2"></td>
												<td width="15%" colspan="2"></td>
											</tr>
											<tr>
												<td width="15%" colspan="1"><bean:message
														key="label.print.TotalNoPackets" />
													${thirdPartyBPLTO.totalPacket}</td>
												<td width="25%" colspan="2"><bean:message
														key="label.print.TotalNoConsig" />
													${thirdPartyBPLTO.totalConsg}</td>
												<td width="20%" colspan="2"><bean:message
														key="label.print.TotalWeight" />
													${thirdPartyBPLTO.finalWeight}</td>
												<%-- <th width="28%"><bean:message key="label.print.TotalWeight"/>  ${bplbranchOutManifestTO.consigTotalWt}</th> --%>
											</tr>
											<tr>
												<td width="15%" colspan="2"><bean:message
														key="label.print.TotalNoComails" />
													${thirdPartyBPLTO.totalComail}</td>
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
				</tbody>
			</table>
		</div>
	</body>

</html:form>

</html>
