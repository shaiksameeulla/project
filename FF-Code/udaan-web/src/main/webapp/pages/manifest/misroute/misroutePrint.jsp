
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
.header{
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
	src="js/manifest/misroute/misroutePrint.js"></script>
</head>

<html:form action="/misroute.do" method="post" styleId="misrouteForm">
	<body>

		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center>${misrouteTO.loginOfficeName}-${misrouteTO.loginofficeCity}</center><br/> --%>
					<center>
						<bean:message key="label.print.misrouteHeader" />
					</center>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="2" width="100%" border="0">
						<tr class="showTopLine">
							<td width="50%" align="left"><bean:message
									key="label.print.headerOrigin" /> <!-- </td>
                	<td width="23%" align="left"> -->${misrouteTO.loginofficeCity}-${misrouteTO.loginOfficeName}</td>
							<td width="50%" align="left"><bean:message
									key="label.print.bplDestination" /> <!-- </td>
                	<td width="37%" align="left"> -->
								${misrouteTO.officeName}</td>

						</tr>
						<tr>
							<td><bean:message key="label.print.MisrouteManifestNo" /> <!-- </td>
                	<td> --> ${misrouteTO.misrouteNo}</td>
							<td align="left"><bean:message key="label.print.dateTime" />
								<!-- </td>
                	<td align="left"> --> ${misrouteTO.misrouteDate}</td>

						</tr>
						<tr>
							<td><bean:message key="label.print.misroute.consignmentType" />
								<!-- </td>
                	<td> -->
								${misrouteTO.consignmentTypeTO.consignmentName}</td>
							<td><bean:message key="label.print.misrouteType" /> <!-- </td>
                  	<td> --> ${misrouteTO.misrouteType}</td>

							<td></td>
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
								<td width="16%" align="center" valign="top"><bean:message
										key="label.print.consigNo" /></td>
								<td width="23%" align="center" valign="top"><bean:message
										key="label.print.origin" /></td>
								<td width="11%" align="center" valign="top"><bean:message
										key="label.print.noOfPieces" /></td>

								<td width="15%" align="center" valign="top"><bean:message
										key="label.print.pincode" /></td>
								<td width="12%" align="center" valign="top"><bean:message
										key="label.manifestGrid.weight" /></td>
								<td width="18%" align="center" valign="top"><bean:message
										key="label.print.misroute.contents" /></td>
								<td width="15%" align="center" valign="top"><bean:message
										key="label.print.decValue" /></td>
								<td width="20%" align="center" valign="top"><bean:message
										key="label.print.toPay" /></td>
								<td width="20%" align="center" valign="top"><bean:message
										key="label.print.codAmt" /></td>
							</tr>
						</thead>
						<tbody>

							<!-- Use C:FOREACH loop to display consignment numbers -->
							<c:forEach var="misrouteTOs"
								items="${misrouteTO.misrouteDetailsTO}" varStatus="status">
								<tr>
									<td align="center"><c:out value="${status.count }" /></td>
									<td align="center"><c:out
											value="${misrouteTOs.scannedItemNo}" /></td>
									<td align="center"><c:out value="${misrouteTOs.origin}" /></td>
									<td align="center"><c:out
											value="${misrouteTOs.noOfPieces}" /></td>
									<td align="center"><c:out value="${misrouteTOs.pincode }" /></td>
									<td align="center"><c:out
											value="${misrouteTOs.actualWeight }" /></td>
									<td align="center"><c:choose>
											<c:when test="${empty misrouteTOs.otherContent}">
												<c:out value="${misrouteTOs.cnContent}" />
											</c:when>
											<c:otherwise>
												<c:out value="${misrouteTOs.otherContent}" />
											</c:otherwise>
										</c:choose></td>
									<%-- <td align="center"><c:if test="${misrouteTOs.cnContent eq 'Others'}"><c:out value="${misrouteTOs.otherContent}"/></c:if> <c:if test="${misrouteTOs.cnContent ne 'Others'}"><c:out value="${misrouteTOs.cnContent}"/></c:if></td> --%>
									<%--  <td align="center"><c:out value="${misrouteTOs.cnContent }"/></td> --%>
									<td align="center"><c:out
											value="${misrouteTOs.declaredValue }" /></td>
									<td align="center"><c:out value="${misrouteTOs.topayAmt }" /></td>
									<td align="center"><c:out value="${misrouteTOs.codAmt }" /></td>
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
										<td width="33%"></td>
										<th width="28%">&nbsp;</th>
										<td width="28%"></td>
										<th width="28%">&nbsp;</th>
										<th width="28%">&nbsp;</th>
									</tr>
									<tr>

										<td width="33%"><bean:message
												key="label.print.TotalNoConsig" />
											${misrouteTO.consigTotal}</td>
										<th width="28%">&nbsp;</th>
										<td width="28%"><bean:message
												key="label.print.TotalNoComail" />
											${misrouteTO.totalComail}</td>
										<th width="28%">&nbsp;</th>
										<th width="28%">&nbsp;</th>

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
									<!--  <tr class="showBtmLine">               	
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
