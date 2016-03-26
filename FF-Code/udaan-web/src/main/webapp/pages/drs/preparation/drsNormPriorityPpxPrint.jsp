
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>UDAAN</title>
<style type="text/css">
body { /* font: normal 16px "Lucida Console", Monaco, monospace; */
	/* font: normal 12px "Verdana", Monaco, monospace; */
	/* font:normal 16px Arial; */
	font-size: 12px;
	font-family: "Times New Roman", Times,serif;
}

table tr .showLine td {
	border-left: 1px dashed black;
}

table tr .showLine td:last-child {
	border-left: 1px dashed black;
	border-right: 1px dashed black;
}

table tr .showBtmLine td,table tr .showBtmLine th {
	border-bottom: 1px dashed black;
}

table tr .showTopLine td,table tr .showTopLine th {
	border-top: 1px dashed black;
}

table tr .showTopBtmLine td,table tr .showTopBtmLine th {
	border-bottom: 1px dashed black;
	border-top: 1px dashed black;
}

.header {
	display: table-header-group;
}
</style>
<style type="text/css" media="print">
.PageBreak {
	page-break-after: always;
}

@page {
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}
</style>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/drs/preparation/drsNormPriorityPpxPrint.js"></script> -->
</head>

<div id="errorDiv" style="color: red; float: right;">
	<html:messages name="error" id="message" bundle="errorBundle">
		<script type="text/javascript">
			$(document).ready(function() {
				<c:if test="${error ne null}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	</html:messages>
</div>
<html:form action="/deliveryPrintDrs.do" method="post"
	styleId="deliveryDrsForm">
	<body>
		<table>
			<c:forEach var="dtlsTOs" items="${page}" varStatus="vstatus">
				<tr>
					<jsp:include
						page="/pages/drs/preparation/drsNormPpxHeaderPrint.jsp" />
				</tr>
				<tr>
					<td align="left" valign="top">
						<table cellpadding="7" cellspacing="0" width="100%" border="0"
							class="showLine">

							<tr class="showTopBtmLine">
								<td align="left" valign="top">
									<table cellpadding="7" cellspacing="0" width="100%" border="0"
										class="showLine">
										<thead>
											<tr class="showTopBtmLine">
												<td width="6%" align="left" valign="top"><bean:message
														key="label.manifestGrid.serialNo" /></td>
												<td width="8%" align="left" valign="top"><bean:message
														key="label.print.drs.consgmanifestorigin" /></td>
												<%-- <td width="5%" align="left" valign="top"><bean:message key="label.print.drs.NoOfPieces"/></td> --%>
												<td width="5%" align="left" valign="top"><bean:message
														key="label.drs.weight" /></td>
												<td width="9%" align="left" valign="top"><bean:message
														key="label.print.contents" /></td>
												<td width="17%" align="left" valign="top"><bean:message
														key="label.print.drs.receiversSign" /></td>
												<td width="5%" align="left" valign="top"><bean:message
														key="label.print.drs.time" /></td>
											</tr>
										</thead>
										<!-- Use C:FOREACH loop to display consignment numbers -->
										<c:if test="${not empty dtlsTOs.firstCol}">
											<c:forEach var="dtlsTO" items="${dtlsTOs.firstCol}"
												varStatus="status">
												<tbody>
													<tr class="showBtmLine" style="height: 8.1em">
														<td height="55" align="left" valign="top"><c:out
																value="${dtlsTO.srNo }" /></td>
														<td align="left" valign="top"><c:out
																value="${dtlsTO.consigment}" /><br><c:out
																	value="${dtlsTO.cityCode}" /></td>
														<%-- <td align="left" valign="top"><c:out value="${dtlsTO.noOfPeices}"/></td> --%>
														<td align="left" valign="top"><c:out
																value="${dtlsTO.weight}" /></td>
														<td align="left" valign="top"><c:out
																value="${dtlsTO.content}" /></td>
														<td align="left" valign="top">&nbsp;</td>
														<td align="left" valign="top"></td>
														<%--  <td align="left" valign="top"><c:out value="${dtlsTO.time}"/></td> --%>
													</tr>
												</tbody>

											</c:forEach>
										</c:if>

									</table>

								</td>
							</tr>

						</table>
					</td>
				</tr>

				<%-- <jsp:include page="/pages/drs/preparation/drsPrintFooter.jsp">
		<jsp:param value="${dtlsTOs.totalConsg}" name="totalCN" />
		</jsp:include> --%>

				<%-- <c:if test="${dtlsTOs.size() eq 0}"> --%>
				<c:if test="${page.size() eq vstatus.count}">
					<tr>
						<td align="left" valign="top"><table cellpadding="1"
								cellspacing="0" width="100%" border="0">
								<tfoot>
									<tr>
										<td width="18" align="left" valign="top"><table
												cellpadding="2" cellspacing="2" width="100%" border="0">
												<tr class="showTopLine">
													<c:if test="${not empty drsTo.fieldStaffTO}">
														<td width="48%" align="left" valign="top">Total <c:out
																value="${dtlsTOs.totalConsg}" /> deliveries taken by <c:out
																value="${drsTo.fieldStaffTO.firstName}" /> <c:out
																value="${drsTo.fieldStaffTO.lastName}" /></td>
													</c:if>
													<c:if test="${not empty  drsTo.franchiseTO }">
														<td width="48%" align="left" valign="top">Total <c:out
																value="${dtlsTOs.totalConsg}" /> deliveries taken by <c:out
																value="${drsTo.franchiseTO.businessName}" /></td>
													</c:if>
													<c:if test="${not empty drsTo.coCourierTO}">
														<td width="48%" align="left" valign="top">Total <c:out
																value="${dtlsTOs.totalConsg}" /> deliveries taken by <c:out
																value="${drsTo.coCourierTO.firstname}" /> <c:out
																value="${drsTo.coCourierTO.lastName}" /></td>
													</c:if>
													<c:if test="${not empty drsTo.baTO}">
														<td width="48%" align="left" valign="top">Total <c:out
																value="${dtlsTOs.totalConsg}" /> deliveries taken by <c:out
																value="${drsTo.baTO.businessName}" /></td>
													</c:if>
													<td width="52%" align="left" valign="top">Total......
														C/ments delivered in STP</td>
												</tr>
												<!-- 											<tr> -->
												<!-- 												<th align="left">&nbsp;</th> -->
												<!-- 												<th align="left">&nbsp;</th> -->
												<!-- 											</tr> -->
												<!-- 											<br /> -->
												<tr class="showBtmLine">
													<td colspan="2" align="left">All the deliveries
														checked &amp; tallied with address &amp; stamp on Pod by:</td>
												</tr>
												<!-- <tr>
												<td align="left" valign="top" colspan="4"></td>
											</tr> -->
											</table></td>
									</tr>
								</tfoot>
								<!-- tr>
	         	<td colspan="10" align="left" valign="top">
	         	--------------------------------------------------------------------------------
	         	</td>
	         </tr> -->
								<!--  <tr>
          <td align="right" valign="top"> Ver : UDAAN 1.0 Released on 25/03/2013 </td>
        </tr> -->
							</table></td>
					</tr>
				</c:if>

<%-- 				<c:if test="${page.size() ne vstatus.count}"> --%>
<!-- 					<tr> -->
<!-- 						<td align="left" valign="top"><table cellpadding="2" -->
<!-- 								cellspacing="0" width="100%" border="0"> -->
<!-- 								<tr> -->
<!-- 									<td width="18" align="left" valign="top"><table -->
<!-- 											cellpadding="5" cellspacing="4" width="100%" border="0"> -->
<!-- 											<tr> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="52%" align="left" valign="top"></td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="52%" align="left" valign="top"></td> -->
<!-- 												<td width="48%" align="left" valign="top"></td> -->
<!-- 												<td width="52%" align="left" valign="top"></td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<th colspan="2" align="left"><br /> <br /> <br /> <br /> -->
<!-- 													<br /> <br></br> <br></br></th> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<td align="left" valign="top" colspan="4"></td> -->
<!-- 											</tr> -->
<!-- 										</table></td> -->
<!-- 								</tr> -->
<!-- 								tr>
<!-- 	         	<td colspan="10" align="left" valign="top"> -->
<!-- 	         	-------------------------------------------------------------------------------- -->
<!-- 	         	</td> -->
<!-- 	         </tr> -->
<!-- 								 <tr>
<!--           <td align="right" valign="top"> Ver : UDAAN 1.0 Released on 25/03/2013 </td> -->
<!--         </tr> --> 
<!-- 							</table></td> -->
<!-- 					</tr> -->
<%-- 				</c:if> --%>

				<div class="PageBreak"></div>
			</c:forEach>
		</table>
	</body>
</html:form>

</html>
