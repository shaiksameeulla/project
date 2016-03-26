
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
<title></title>
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
	src="js/manifest/bplBranchOutManifestPrint.js"></script>
</head>

<html:form action="/bplBranchOutManifest.do" method="post"
	styleId="bplBranchOutManifestForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b>   ${bplbranchOutManifestTO.loginOfficeAddress1},${bplbranchOutManifestTO.loginOfficeAddress2},${bplbranchOutManifestTO.loginOfficeAddress3}-${bplbranchOutManifestTO.loginOfficePincode}</b><br/></center> --%>
					<%-- <center>${bplbranchOutManifestTO.loginOfficeName}-${bplbranchOutManifestTO.loginOfficeAddress3}</center><br/> --%>
					<center>
						<bean:message key="label.print.bplBranchHeader" />
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
								<td width="45%" align="left">${bplbranchOutManifestTO.loginOfficeAddress3}-${bplbranchOutManifestTO.loginOfficeName}</td>
								<td width="5%" align="left"><bean:message
										key="label.print.bplDestination" /></td>
								<td width="45%" align="left">${bplbranchOutManifestTO.destinationCityTO.cityName}-${bplbranchOutManifestTO.destinationOfficeName}</td>

							</tr>
							<tr>
								<td width="8%" align="left"><bean:message
										key="label.print.bplNo" /></td>
								<td width="42%" align="left">${bplbranchOutManifestTO.manifestNo}</td>
								<%-- <td width="10%" align="right" ><bean:message key="label.print.bagNo" /></td>
                  	<td width="8%" align="left" >${bplbranchOutManifestTO.manifestNo}</td>  --%>
								<td width="8%" align="right"><bean:message
										key="label.print.dateTime" /></td>
								<td width="42%" align="left">${bplbranchOutManifestTO.manifestDate}</td>
							</tr>
							<tr>
								<td width="15%" align="left"><bean:message
										key="label.print.loadNo" /></td>
								<td width="85%" align="left" colspan="5">${bplbranchOutManifestTO.loadNo}</td>
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
											key="label.print.brManifestNo" /></td>
									<td width="10%" align="center" valign="top"><bean:message
											key="label.print.NoConsig" /></td>
									<td width="28%" align="center" valign="top"><bean:message
											key="label.manifestGrid.weight" /></td>
								</tr>
							</thead>
							<tbody>

								<!-- Use C:FOREACH loop to display consignment numbers -->
								<c:forEach var="dtlsTOs"
									items="${bplbranchOutManifestTO.bplBranchOutManifestDetailsTOsList}"
									varStatus="status">
									<tr>
										<td align="center"><c:out value="${status.count }" /></td>
										<td align="center"><c:out value="${dtlsTOs.manifestNo}" /></td>
										<td align="center"><c:out
												value="${dtlsTOs.noOfConsignment }" /></td>
										<td align="center"><c:out value="${dtlsTOs.weight }" /></td>

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
									<table cellpadding="10" cellspacing="2" width="100%" border="0">
										<tr class="showTopLine">
											<td width="24%" colspan="2"><bean:message
													key="label.print.TotalNoPackets" />
												${bplbranchOutManifestTO.rowcount}</td>
											<td width="33%"><bean:message
													key="label.print.TotalNoConsig" />
												${bplbranchOutManifestTO.consigTotal}</td>
											<td width="28%"><bean:message
													key="label.print.TotalWeight" />
												${bplbranchOutManifestTO.consigTotalWt}</td>

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
			</tbody>
		</table>
</html:form>
</body>
</html>
