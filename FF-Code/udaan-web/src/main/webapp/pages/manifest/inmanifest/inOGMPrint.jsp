
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
	src="js/manifest/inmanifest/inOGMDoxPrint.js"></script>
<script type="text/javascript" charset="utf-8">
	var count = '${ogmTO.rowCount}';
</script>
</head>

<html:form action="/inOGMDoxManifest.do" method="post"
	styleId="inOGMDoxManifestForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b> ${ogmTO.originOfficeTO.address1},${ogmTO.originOfficeTO.address2},${ogmTO.originOfficeTO.address3}</b></center><br/>  --%>
					<%-- <center>${ogmTO.loggedInOfficeName}-${ogmTO.loggedInOfficeCity}</center><br/> --%>
					<center>
						<bean:message key="label.print.inOgmDoxHeader" />
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
								<td width="5%" align="left"><bean:message
										key="label.print.headerOrigin" /></td>
								<td width="25%" align="left">${ogmTO.originCityTO.cityName}-${ogmTO.originOfficeTO.officeName}</td>
								<td width="5%" align="left"><bean:message
										key="label.print.bplDestination" /></td>
								<td width="48%" align="left" colspan="5">${ogmTO.destinationOfficeTO.address3}-${ogmTO.destinationOfficeTO.officeName}</td>

							</tr>
							<tr>
								<td width="15%" align="left" colspan="1"><bean:message
										key="label.print.ogmNo" /></td>
								<td width="25%">${ogmTO.manifestNumber}</td>
								<td width="5%" align="right"><bean:message
										key="label.print.dateTime" /></td>
								<td width="48%">${ogmTO.manifestDate}</td>
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
									<td width="9%" align="center" valign="top"><bean:message
											key="label.manifestGrid.serialNo" /></td>
									<td width="16%" align="center" valign="top"><bean:message
											key="label.print.consgNo" /></td>
									<td width="16%" align="center" valign="top"><bean:message
											key="label.print.pincode" /></td>
									<td width="20%" align="center" valign="top"><bean:message
											key="label.print.mbpl.destination" /></td>
									<td width="20%" align="center" valign="top"><bean:message
											key="label.print.weight" /></td>
								</tr>
							</thead>
							<tbody>

								<!-- Use C:FOREACH loop to display consignment numbers -->
								<c:forEach var="dtlsTOs" items="${ogmTO.inManifestOGMDetailTOs}"
									varStatus="status">
									<c:if test="${not empty ogmTO.inManifestOGMDetailTOs}">
										<tr>
											<td align="center"><c:out value="${status.count }" /></td>
											<td align="center"><c:out
													value="${dtlsTOs.consignmentNumber}" /></td>
											<td align="center"><c:out
													value="${dtlsTOs.destPincode.pincode }" /></td>
											<td align="center"><c:out
													value="${dtlsTOs.destCity.cityName }" /></td>
											<td align="center"><c:out
													value="${dtlsTOs.manifestWeight }" /></td>
										</tr>
									</c:if>
									<c:set value="${status.count}" var="counter" scope="page" />
								</c:forEach>
								<c:if test="${not empty ogmTO.inCoMailTOs}">
									<c:forEach var="dtlsTOs" items="${ogmTO.inCoMailTOs}"
										varStatus="status">
										<tr>
											<td align="center"><c:out
													value="${status.count + counter}" /></td>
											<td align="center"><c:out value="${dtlsTOs.comailNo}" /></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</c:forEach>
								</c:if>
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
											<td width="24%" colspan="3"></td>
											<td width="40%" align="right"></td>
											<th width="8%" align="right">&nbsp;</th>
										</tr>
										<tr>
											<td width="24%"><bean:message
													key="label.print.TotalNoConsg" /> ${ogmTO.rowCount}</td>
											<td width="40%" align="right" colspan="2"><bean:message
													key="label.print.weightPcs" />${ogmTO.totalWt}</td>
											<td width="36%" align="right">&nbsp;</td>
										</tr>
										<tr>
											<th width="15%" align="left">&nbsp;</th>
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
                    <th  width="25%" align="right" valign="top" colspan="2">Signature: </th>
                    <th width="8%" align="right">&nbsp;</th>
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
