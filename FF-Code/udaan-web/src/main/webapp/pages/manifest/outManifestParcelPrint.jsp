
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
	src="js/manifest/outManifestParcelPrint.js"></script>
</head>

<html:form action="/outManifestParcel.do" method="post"
	styleId="outManifestParcelForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b> ${outManifestParcelTO.loginOfficeAddress1},${outManifestParcelTO.loginOfficeAddress2},${outManifestParcelTO.loginOfficeAddress3}-${outManifestParcelTO.loginOfficePincode} </b><br/></center> --%>
					<%-- <center>${outManifestParcelTO.loginOfficeName}-${outManifestParcelTO.loginOfficeAddress3}</center><br/> --%>
					<center>
						<bean:message key="label.print.outmanifestParcel" />
					</center> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
		</table>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<thead>

				<tr>
					<td align="left" valign="top">
						<table cellpadding="1" cellspacing="2" width="100%" border="0">
							<tr class="showTopLine">
								<td width="5%" align="left"><bean:message
										key="label.print.headerOrigin" /></td>
								<td width="20%" align="left">${outManifestParcelTO.loginOfficeAddress3}-${outManifestParcelTO.originOfficeName}</td>
								<td width="8%" align="left"><bean:message
										key="label.print.bplDestination" /></td>
								<td width="20%" align="left">${outManifestParcelTO.destinationCityTO.cityName}-${outManifestParcelTO.destinationOfficeName}</td>
								<td width="14%" align="left"><bean:message
										key="label.print.bagLocNo" /></td>
								<td width="5%" align="left">${outManifestParcelTO.bagLockNo}</td>
							</tr>
							<tr>
								<td width="9%" align="left"><bean:message
										key="label.print.bplNo" /></td>
								<td width="28%" align="left">${outManifestParcelTO.manifestNo}</td>
								<td width="5%" align="right"><bean:message
										key="label.print.dateTime" /></td>
								<td width="30%" align="left">${outManifestParcelTO.manifestDate}</td>
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
									<td width="2%" align="center" valign="top"><bean:message
											key="label.manifestGrid.serialNo" /></td>
									<td width="4%" align="center" valign="top"><bean:message
											key="label.print.outmanifestDox.consg" /></td>
									<%-- <td width="5%" align="center" valign="top"><bean:message key="label.print.pincode"/></td> --%>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.InBpl.destinations" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.inBpl.weight" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.inBpl.pcs" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.contents" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.inBpl.declvalue" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.inBpl.toPay" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.inBpl.codamtLcAmt" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.PaperWorks" /></td>
								</tr>
							</thead>
							<tbody>

								<!-- Use C:FOREACH loop to display consignment numbers -->
								<c:forEach var="dtlsTOs"
									items="${outManifestParcelTO.outManifestParcelDetailsList}"
									varStatus="status">
									<tr>
										<td align="center"><c:out value="${status.count }" /></td>
										<td align="center"><c:out value="${dtlsTOs.consgNo}" /></td>
										<%-- <td align="center" ><c:out value="${dtlsTOs.pincode}"/></td> --%>
										<td align="center"><c:out value="${dtlsTOs.destCity}" /></td>
										<td align="center"><c:out value="${dtlsTOs.actWeight}" /></td>
										<td align="center"><c:out value="${dtlsTOs.noOfPcs}" /></td>
										<td align="center"><c:choose>
												<c:when test="${empty dtlsTOs.otherCNContent}">
													<c:out value="${dtlsTOs.cnContent}" />
												</c:when>
												<c:otherwise>
													<c:out value="${dtlsTOs.otherCNContent}" />
												</c:otherwise>
											</c:choose></td>
										<%-- <td align="center"><c:if test="${dtlsTOs.cnContent eq 'Others'}"><c:out value="${dtlsTOs.otherCNContent}"/></c:if> <c:if test="${dtlsTOs.cnContent ne 'Others'}"><c:out value="${dtlsTOs.cnContent}"/></c:if></td> --%>
										<td align="center"><c:out
												value="${dtlsTOs.declaredValue}" /></td>
										<td align="center"><c:out value="${dtlsTOs.toPayAmt}" /></td>
										<%-- <td align="center"><c:out value="${dtlsTOs.codAmt}" /></td> --%>
										<%-- <td align="center"><c:out value="${dtlsTOs.paperWork}" /></td> --%>
										<td align="center"><c:if test="${not empty dtlsTOs.codAmt}"><c:out value="${dtlsTOs.codAmt}"/></c:if>  <c:if test="${not empty dtlsTOs.lcAmt}"><c:out value="${dtlsTOs.lcAmt}"/></c:if></td>
										 <td align="center"><c:out value="${dtlsTOs.paperWork}" /></td> 
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
											<td width="30%"></td>
											<td width="70%"></td>
											<td width="20%" alighn="center"></td>
										</tr>

										<tr>
											<td width="20%"><bean:message
													key="label.print.TotalNoConsig" />
												${outManifestParcelTO.totalConsg}</td>
											<td width="10%" align="left"><bean:message
													key="label.print.weightPcs" />
												${outManifestParcelTO.totalWt} &nbsp; &nbsp;<bean:message
													key="label.print.Pcs" /> ${outManifestParcelTO.totalNoPcs}</td>
											<th align="left" valign="top">&nbsp;</th>
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
										<!--         <tr class="showBtmLine">               	
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
</html:form>
</body>
</html>
