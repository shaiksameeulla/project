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

table tr .showToptopLine td,table tr .showTopBtmLine th {
	border-bottom: 2px dashed black;
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
	src="js/manifest/inmanifest/inBagManifestDoxPrint.js"></script>
</head>
<body>
	<html:form action="/inBagManifest.do" styleId="inBagManifestForm">

		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr class="showTopBtmLine">
				<td align="left" valign="top">

					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b>  ${inBagManifestDoxTO.originOfficeTO.address1},${inBagManifestDoxTO.originOfficeTO.address2},${inBagManifestDoxTO.originOfficeTO.address3}</b></center><br/> --%>
					<%-- <center>${inBagManifestDoxTO.loggedInOfficeName}-${inBagManifestDoxTO.loggedInOfficeCity}</center><br/> --%>
					<center>
						<bean:message key="label.print.inBplHeader" />
					</center>

				</td>
			</tr>
		</table>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<thead>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="3" cellspacing="0" width="100%" border="0">
							<!-- Header Row 1 -->
							<tr class="showTopLine">
								<td width="12%" align="left" valign="top"><bean:message
										key="label.baBookingParcel.dateTime" /></td>
								<td>${inBagManifestDoxTO.manifestDateTime}</td>
								<td><bean:message key="label.print.bplNo" /></td>
								<td>${inBagManifestDoxTO.manifestNumber}</td>
							</tr>

							<tr>
								<td width="10%" align="left" valign="top"><bean:message
										key="label.print.bplDestination" /></td>
								<td>${inBagManifestDoxTO.destCityName}-${inBagManifestDoxTO.destOfficeName}</td>
								<td><bean:message key="label.print.origin" /></td>
								<td>${inBagManifestDoxTO.originOfficeTO.address3}-${inBagManifestDoxTO.originOfficeTO.officeName}</td>


							</tr>

						</table>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0"
							id="bplOutManifestDoxPrint" class="showLine">
							<thead>
								<tr class="showTopBtmLine">
									<td width="10%" align="left" valign="top"><bean:message
											key="label.baBookingParcel.serialNo" /></td>
									<td width="30%" align="left" valign="top"><bean:message
											key="label.manifestGrid.manifestNo" /></td>
									<td width="30%" align="left" valign="top"><bean:message
											key="label.manifestGrid.destination" /></td>
									<td width="30%" align="left" valign="top"><bean:message
											key="label.manifestGrid.noOfPieces" /></td>


								</tr>
							</thead>
							<tbody>

								<c:forEach var="dtlsTOs"
									items="${inBagManifestDoxTO.inBagManifestDetailsDoxTOs}"
									varStatus="status">
									<tr>
										<td><c:out value="${status.count }" /></td>
										<td><c:out value="${dtlsTOs.manifestNumber}" /></td>
										<td><c:out value="${dtlsTOs.destCity.cityName }" /></td>
										<td><c:out value="${dtlsTOs.noOfPcs }" /></td>
									</tr>
								</c:forEach>
							</tbody>
							<%--  <tr class="showTopLine">
				            
				            <th>&nbsp;</th>
			                <th><bean:message key="label.print.TotalNoPackets"/>${bplOutManifestDoxTO.rowcount}</th>
				            <th><bean:message key="label.print.TotalNoConsig"/>${bplOutManifestDoxTO.consigTotal}</th>
				            <th>&nbsp;</th>
              		</tr>
              		 <tr class="showTopLine">
              		    <th></th>
			                <th>&nbsp;</th>
				            <th>&nbsp;</th>
				            <th>&nbsp;</th>
				            <th>&nbsp;</th>
              		 </tr> --%>

							<!--  <tr>
                    	<th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                        <th align="left">&nbsp;</th>
                    </tr>
                   <tr class="showBtmLine">               	
                  	<th width="25%" align="left" valign="top" colspan="4">MANIFEST PREPARED BY:</th>
                    <th align="left" valign="top">&nbsp;</th>
                    <th widhth="90"align="left" valign="top" colspan="2">Signature: </th>
                    <th widhth="150" align="left" colspan="5">&nbsp;</th>
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
                                <th width="25%" align="left" valign="top"colspan="4">Load Received On:</th>
                                <th width="9%" align="left" valign="top"></th>
                                <th width="40%" align="right" valign="top"  >At:</th>
                                <th width="15%" align="left" valign="top" colspan="3"></th>
                                <th width="10%" align="right" valign="top">Hrs:</th>                         
                                <th width="15%" align="left" valign="top"></th>
                            </tr>
                            <tr>
                            </tr>
                            <tr>
                                <th width="25%" align="left" valign="top" colspan="5">Load Received By:</th>
                                <th width="9%"  align="left" valign="top" colspan="3">&nbsp;</th>
                                <th width="10%" align="right" valign="top" colspan="2">Signature / Seal:</th>                                
                                <th width="18%" rowspan="2" align="left" valign="top">&nbsp;</th>
                            </tr>
                           
                            <tr>
                               <th width="25%" align="left" valign="top">Date:</th>
                                <th width="13%" align="left" valign="top">&nbsp;</th>
                                <th width="85%" align="left" valign="top">Time:</th>
                                <th  align="left" valign="top">&nbsp;</th>    
                                <th rowspan="2" align="left" valign="top">&nbsp;</th>                                
                            </tr>
                        </table>
                    </td>
                </tr> -->
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
										<%-- <tr class="showTopLine">						
						<th width="24%" colspan="2"><bean:message key="label.print.TotalNoPackets"/>  ${bplbranchOutManifestTO.rowcount}</th>
						<th width="33%"><bean:message key="label.print.TotalNoConsig"/>  ${bplbranchOutManifestTO.consigTotal}</th>
						<th width="28%"><bean:message key="label.print.TotalWeight"/>  ${bplbranchOutManifestTO.consigTotalWt}</th>
                        
					</tr> --%>
										<tr class="showTopLine">
											<td width="20%" align="left" valign="top"></td>
											<td width="38%" align="right"></td>
											<th align="left">&nbsp;</th>
											<th align="left">&nbsp;</th>
										</tr>
										<tr>

											<!--  <th>&nbsp;</th> -->
											<td width="20%" align="left" valign="top"><bean:message
													key="label.print.TotalNoPackets" />${inBagManifestDoxTO.rowCount}</td>
											<td width="38%" align="right"><bean:message
													key="label.print.TotalNoConsig" />${inBagManifestDoxTO.totalConsg}</td>
											<!--  <th width="98" align="right" valign="top">&nbsp;</th> -->
											<th align="left">&nbsp;</th>
											<th align="left">&nbsp;</th>
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
                </tr>
	       		</table>
	       	   </td>
	         </tr>
			 -->
										</tbody>
									</table>
<!-- 								</td> -->
<!-- 							</tr> -->
<!-- 						</table> -->
	</html:form>
</body>
</html>