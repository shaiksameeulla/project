
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
body { /* font-family: arial;
	font-size: 13px; */
	/* font:normal 16px "Lucida Console", Monaco, monospace; */
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
	src="js/manifest/inmanifest/inMasterBagManifestPrint.js"></script>
</head>

<html:form action="/inMasterBagManifest.do" method="post"
	styleId="inMasterBagManifestForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr>
				<td align="left" valign="top">
					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b>  ${inMasterBagManifestTO.originOfficeTO.address1},${inMasterBagManifestTO.originOfficeTO.address2},${inMasterBagManifestTO.originOfficeTO.address3}</b></center><br/> --%>
					<%-- <center>${inMasterBagManifestTO.loggedInOfficeName}-${inMasterBagManifestTO.loggedInOfficeCity}</center><br/> --%>
					<center>
						<bean:message key="label.print.inMbplHeader" />
					</center> <!-- -------------------------------------------------------------------------------- -->
				</td>
			</tr>
		</table>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<thead>

				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<tr class="showTopLine">
								<td width="11%" align="left"><bean:message
										key="label.print.headerOrigin" /></td>
								<td>${inMasterBagManifestTO.originCityTO.cityName}-${inMasterBagManifestTO.originOfficeTO.officeName}</td>
								<td width="11%" align="left"><bean:message
										key="label.print.bplDestination" /></td>
								<td>${inMasterBagManifestTO.destinationOfficeTO.address3}-${inMasterBagManifestTO.destinationOfficeTO.officeName}</td>

							</tr>
							<tr>
								<td width="11%" align="left"><bean:message
										key="label.print.mbplNo" /></td>
								<td>${inMasterBagManifestTO.manifestNumber}</td>
								<%-- <td width="11%" align="left" ><bean:message key="label.print.bagNo" /></td>
                  	<td >${inMasterBagManifestTO.manifestNumber}</td>  --%>
								<td width="6%" align="left"><bean:message
										key="label.print.dateTime" /></td>
								<td>${inMasterBagManifestTO.manifestDate}</td>
							</tr>
							<tr>
								<td width="20%" align="left"><bean:message
										key="label.print.bagLocNo" /></td>
								<td>${inMasterBagManifestTO.lockNum}</td>
							</tr>
						</table> <!-- -------------------------------------------------------------------------------- -->
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="5" cellspacing="0" width="100%" border="0"
							class="showLine">
							<thead>

								<tr class="showTopBtmLine">
									<td width="2%" align="left" valign="top"><bean:message
											key="label.manifestGrid.serialNo" /></td>
									<td width="4%" align="left" valign="top"><bean:message
											key="label.print.mbpl.bplNo" /></td>
									<td width="6%" align="left" valign="top"><bean:message
											key="label.print.mbpl.destination" /></td>
									<td width="4%" align="left" valign="top"><bean:message
											key="label.manifestGrid.weight" /></td>
									<td width="6%" align="left" valign="top"><bean:message
											key="label.print.mbpl.backlock" /></td>
								</tr>
							</thead>
							<tbody>

								<!-- Use C:FOREACH loop to display consignment numbers -->
								<c:forEach var="dtlsTOs"
									items="${inMasterBagManifestTO.inMasterBagManifestDtlsTOs}"
									varStatus="status">
									<tr>
										<td><c:out value="${status.count }" /></td>
										<td><c:out value="${dtlsTOs.bplNumber}" /></td>
										<td><c:out value="${dtlsTOs.destCity.cityName}" /></td>
										<td><c:out value="${dtlsTOs.manifestWeight }" /></td>
										<td><c:out value="${dtlsTOs.bagLackNo }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</td>
				</tr>


				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<!-- <tr>
	         	<td colspan="10" align="left" valign="top">
	         	--------------------------------------------------------------------------------
	         	</td>
	         </tr> -->
							<tr>
								<td width="18" align="left" valign="top">
									<table cellpadding="5" cellspacing="0" width="100%" border="0">
										<tr class="showTopLine">
											<td width="35%"></td>
											<td width="35%"></td>
											<td width="25%"></td>
											<th align="right">&nbsp;&nbsp;</th>
											<th align="right">&nbsp;&nbsp;</th>
										</tr>
										<tr>
											<%-- <td width="6%" align="left" valign="top">&nbsp;</td>
						<th width="35%" align="left" valign="top"><bean:message key="label.print.TotalNoPackets"/>${mbplOutManifestTO.rowCount}</th>
						<th width="31%" align="left" valign="top"><bean:message key="label.print.TotalNoConsig"/>${mbplOutManifestTO.consigTotal}</th>
						<th width="28%" align="left" valign="top"><bean:message key="label.print.TotalWeight"/>${mbplOutManifestTO.consigTotalWt}</th>
                         --%>

											<td width="35%" valign="top"><bean:message
													key="label.print.TotalNoBag" />${inMasterBagManifestTO.rowCount}</td>
											<td width="35%" valign="top"><bean:message
													key="label.print.TotalNoConsig" />
												${inMasterBagManifestTO.totalConsg}</td>
											<td width="25%" valign="top"><bean:message
													key="label.print.TotalNoComails" />
												${inMasterBagManifestTO.totalComail}</td>
											<th align="right">&nbsp;&nbsp;</th>
											<th align="right">&nbsp;&nbsp;</th>
										</tr>
										<tr>
											<td width="35%"><bean:message
													key="label.print.TotalWeight" />${inMasterBagManifestTO.totalBplWt}</td>
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
                	<th width="6%" align="left" valign="top">&nbsp;</th>
                  	<th align="left" >MANIFEST PREPARED BY:</th>
                    <th align="left" valign="top">&nbsp;&nbsp;</th>
                    <th  align="right" valign="top">Signature: </th>
                    <th width="25%" align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;</th>
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
