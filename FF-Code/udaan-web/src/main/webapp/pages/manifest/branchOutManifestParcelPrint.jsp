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
/* body{
     font:normal 16px "Lucida Console", Monaco, monospace;
	
}
table tr .showLine td{
	border-left: 2px dashed black;
}
table tr .showBtmLine td, table tr .showBtmLine th{
	border-bottom: 2px dashed black;
}
table tr .showTopLine td, table tr .showTopLine th{
	border-top: 2px dashed black;
}
table tr .showTopBtmLine td, table tr .showTopBtmLine th{
	border-bottom: 2px dashed black;
	border-top: 2px dashed black;
}
table tr .showToptopLine td, table tr .showTopBtmLine th{
	border-bottom: 2px dashed black;
	
} */
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

table tr .noshowLine td {
	border-left: 0px dashed black;
	border-top: 2px dashed black;
}

table tr .noshowLine td:last-child {
	border-left: 0px dashed black;
	border-right: 0px dashed black;
}

table tr .noshowLine1 td {
	border-left: 0px dashed black;
	border-top: 0px dashed black;
}

table tr .noshowLine1 td:last-child {
	border-left: 0px dashed black;
	border-right: 0px dashed black;
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
	src="js/manifest/branchOutManifestParcelPrint.js"></script>
</head>
<html:form action="/branchOutManifestParcel.do"
	styleId="branchOutManifestParcelForm">
	<body>
		<table cellpadding="2" cellspacing="0" width="100%" border="0">
			<tr class="showTopBtmLine">
				<td align="left" valign="top">

					<center>
						<bean:message key="label.print.header" />
					</center> <%-- <center><b>   ${branchOutManifestParcelTO.loginOfficeAddress1},${branchOutManifestParcelTO.loginOfficeAddress2},${branchOutManifestParcelTO.loginOfficeAddress3}-${branchOutManifestParcelTO.loginOfficePincode}</b><br/></center> --%>
					<%-- <center>${branchOutManifestParcelTO.loginOfficeName}-${branchOutManifestParcelTO.loginOfficeAddress3}</center><br/> --%>
					<center>
						<bean:message key="label.print.branchOutGoingHeader" />
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
								<td width="17%" align="left" valign="top"><bean:message
										key="label.print.headerOrigin" /></td>
								<td width="27%">${branchOutManifestParcelTO.loginOfficeAddress3}-${branchOutManifestParcelTO.loginOfficeName}</td>
								<td width="20%" align="right" valign="top"><bean:message
										key="label.print.bplDestination" /></td>
								<td>${branchOutManifestParcelTO.destinationCityTO.cityName}-${branchOutManifestParcelTO.destinationOfficeName}</td>
							</tr>

						</table>
					</td>
				</tr>

				<td align="left" valign="top">
					<table cellpadding="2" cellspacing="0" width="100%" border="0">
						<!-- Header Row 1 -->
						<tr>
							<td width="17%" align="left" valign="top"><bean:message
									key="label.print.packetNo" /></td>
							<td width="30%" align="left">${branchOutManifestParcelTO.manifestNo}</td>
							<td width="20%" align="right"><bean:message
									key="label.print.branchLoadNo" /></td>
							<td>${branchOutManifestParcelTO.loadNoId}</td>
							<!-- <th widhth="100%" align="left" colspan="4">&nbsp;</th> -->
						</tr>

					</table>
				</td>
				</tr>

				<tr>
					<td align="left" valign="top">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<!-- Header Row 1 -->
							<tr>
								<td width="17%" align="left" valign="top"><bean:message
										key="label.print.dateTime" /></td>
								<td width="28%" align="left">${branchOutManifestParcelTO.manifestDate}</td>
								<td width="20%" align="right" valign="top"><bean:message
										key="label.print.branchDispatchDate" /></td>
								<td>${branchOutManifestParcelTO.manifestDate}</td>

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
									<td width="5%" align="center" valign="top"><bean:message
											key="label.manifestGrid.serialNo" /></td>
									<td width="7%" align="center" valign="top"><bean:message
											key="label.manifestGrid.consgNo" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.branchToPay" /></td>
									<td width="5%" align="center" valign="top"><bean:message
											key="label.print.branchCOD" /></td>
									<%--   <th width="15%" align="left" valign="top" ><bean:message key="label.print.branchInvoice"/></th> --%>
									<td width="7%" align="center" valign="top"><bean:message
											key="label.manifestGrid.decValue" /></td>
									<td width="8%" align="center" valign="top"><bean:message
											key="label.manifestGrid.weight" /></td>
									<td width="8%" align="center" valign="top"><bean:message
											key="label.manifestGrid.noOfPieces" /></td>
									<td width="12%" align="center" valign="top"><bean:message
											key="label.manifestGrid.contentDesc" /></td>
									<%--  <th width="9%"  align="left" valign="top"><bean:message key="label.manifestGrid.contentDesc"/></th> --%>


								</tr>
							</thead>
							<c:forEach var="dtlsTOs"
								items="${branchOutManifestParcelTO.branchOutManifestParcelDetailsList}"
								varStatus="status">
								<tbody>
									<tr>
										<td align="center"><c:out value="${status.count }" /></td>
										<td align="center"><c:out value="${dtlsTOs.consgNo}" /></td>
										<td align="center"><c:out value="${dtlsTOs.toPayAmt}" /></td>
										<td align="center"><c:out value="${dtlsTOs.codAmt}" /></td>
										<td align="center"><c:out
												value="${dtlsTOs.declaredValue }" /></td>
										<td align="center"><c:out value="${dtlsTOs.weight }" /></td>
										<td align="center"><c:out value="${dtlsTOs.noOfPcs }" /></td>
										<td align="center"><c:choose>
												<c:when test="${empty dtlsTOs.otherCNContent}">
													<c:out value="${dtlsTOs.cnContent}" />
												</c:when>
												<c:otherwise>
													<c:out value="${dtlsTOs.otherCNContent}" />
												</c:otherwise>
											</c:choose></td>
										<%-- <td align="center"><c:if test="${dtlsTOs.cnContent eq 'Others'}"><c:out value="${dtlsTOs.otherCNContent}"/></c:if> <c:if test="${dtlsTOs.cnContent ne 'Others'}"><c:out value="${dtlsTOs.cnContent}"/></c:if></td> --%>
										<%--  <td ><c:out value="${dtlsTOs.cnContent }"/></td> --%>

									</tr>
								</tbody>
							</c:forEach>
							<tr class="noshowLine">
								<!-- <th>&nbsp;</th> -->
								<td colspan="3"></td>
								<!-- <td>&nbsp;</td> -->
								<!-- <td>&nbsp;</td> -->
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>&nbsp;</td>
								<!-- 				             <td>&nbsp;</td> -->
							</tr>
							<tr class="noshowLine1">

								<td colspan="3" align="left"><bean:message
										key="label.print.TotalNoConsig" />${branchOutManifestParcelTO.consignCount}</td>
								<!-- <td>&nbsp;</td> -->
								<!-- 								<td>&nbsp;</td> -->
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td align="center"><bean:message
										key="label.print.weightPcs" />${branchOutManifestParcelTO.printTotalWeight}</td>
								<td align="center"><bean:message key="label.print.Pcs" />${branchOutManifestParcelTO.printTotalNoOfPcs}</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<th></th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
							</tr>

							<tr>
								<th align="left">&nbsp;</th>
								<th align="left">&nbsp;</th>
								<th align="left">&nbsp;</th>
								<th align="left">&nbsp;</th>
							</tr>
							<!--  <tr class="showBtmLine">               	
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
                    <th align="left" valign="top" colspan="4">
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
                            </tr> -->
						</table>
					</th>
				</tr>
			</tbody>
		</table>
		</td>
		</tr>

		</table>
</html:form>
</body>
</html>