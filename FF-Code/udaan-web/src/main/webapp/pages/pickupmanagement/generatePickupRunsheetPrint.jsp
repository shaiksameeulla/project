<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
body {
	font: normal 12px "Verdana", Monaco, monospace;
	/* font: normal 14px Arial, "Times New Roman", Times, serif; */
	/* font:normal 16px Arial; 
	font: normal 16px Arial, "Times New Roman", Times, serif;
	font: 12pt "Lucida Console", Monaco, monospace;
	border:1px solid #ff0000; */
	/* margin: 0px;
	background: none;
	width: 100%;
	font:normal 5px "Verdana", Monaco, monospace;
	font-size:5px; */
}

.breakPage {
	page-break-after: always;
}

@page Section1 { /* size:8.27in 11.69in;  */
	/* margin: .1in .1in .1in .1in; */
	/* mso-header-margin: .1in;
	mso-footer-margin: .1in;
	mso-paper-source: 0; */
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}

div.Section1 {
	page: Section1;
}

/*table tr .showLine td {
	border-left: 2px dashed black;
}

table tr .showLine1 td {
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
}*/
.showBtmLine {
	border-bottom: 2px dashed black;
}

.contl {
	overflow: hidden;
	width: 406px; /* or other */
	height: 95px; /* or other */
	-o-text-overflow: ellipsis; /* Opera */
	text-overflow: ellipsis;
	/* white-space: nowrap; */
}

.contr {
	/* overflow: hidden; */
	width: 406px; /* or other */
	height: 95px; /* or other */
	-o-text-overflow: ellipsis; /* Opera */
	text-overflow: ellipsis;
	word-wrap: break-word;
	/* white-space: nowrap; */
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
	src="js/pickupmanagement/generatePickupRunsheetPrint.js"></script>
</head>

<body>
	<div class="section1">
		<html:form method="post" styleId="pickupRunsheetForm">
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<c:if test="${not empty page}">
					<c:forEach var="dtlsTOs" items="${page}" varStatus="vstatus">
						<table cellpadding="2" cellspacing="0" width="100%" border="0">
							<tr>
								<td>&nbsp;<br></br></td>
							</tr>
							<tr>
								<td align="left" valign="top">
									<center>
										<bean:message key="label.print.header" />
									</center>
									<center>
										<bean:message key="label.print.pickupRunsheet.header" />
									</center>
								</td>
							</tr>
							<tr>
								<td>
									<div class="showBtmLine"></div>
								</td>

								
							</tr>
							<tr>
								<td align="left" valign="top" width="50%">
									<table cellpadding="2" cellspacing="5" width="100%" border="0">
										<tr>
											<td width="2%" align="left"><bean:message
													key="label.print.branch" /></td>
											<td width="15%" align="left">${dtlsTOs.firstCol[0].branchNameCode}</td>
											<td width="9%" align="left"><bean:message
													key="label.print.pickupRunsheet.staff" /></td>
											<td width="9%" align="left" colspan="3">${dtlsTOs.firstCol[0].empName}-${dtlsTOs.firstCol[0].empCode}</td>
										</tr>
										<tr>

											<td width="2%" align="left"><bean:message
													key="label.print.pickupRunsheet.Date" /></td>
											<td width="5%" align="left">${dtlsTOs.firstCol[0].runsheetDate}</td>
											<td width="8%" align="left"><bean:message
													key="label.print.pickupRunsheet.runsheetNO" /></td>
											<td width="5%" align="left">${dtlsTOs.firstCol[0].runsheetNo}</td>
											<td width="5%" align="left"><bean:message
													key="label.print.pickupRunsheet.runsheetType" /></td>
											<td width="5%" align="left">${dtlsTOs.firstCol[0].runsheetType}</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-- <tr>
				<td align="left" valign="top"> -->
						<table cellpadding="1" cellspacing="0" width="100%" border="0">
							<tr>
								<td align="left" valign="top" width="50%">
									<table cellpadding="3" cellspacing="0" width="100%" border="0"
										style="border-right: 0px dashed black; height: 650%;">
										<tr class="showBtmLine">
											<td width="6%" align="left" valign="top"
												style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-left: 2px dashed black; border-right: 2px dashed black;">
												<bean:message key="label.print.pickupRunsheet.custName" />
											</td>
											<td width="3%" align="left" valign="top"
												style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-right: 2px dashed black;">
												Consignment</td>
											<td width="3%" align="left" valign="top"
												style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-right: 2px dashed black;">
												<bean:message key="label.print.pickupRunsheet.total" />
											</td>
										</tr>

										<%-- <logic:present name="${pickupRunsheetForm.to.addressFlag}" scope="request">
										
									</logic:present> --%>
										<c:if test="${not empty dtlsTOs.firstCol}">
											<c:choose>
												<c:when test="${pkupRunsheetTO.addressFlag=='Y'}">

													<c:forEach var="dtlsTO" items="${dtlsTOs.firstCol}"
														varStatus="status">
														<tr style="height: 0px">

															<%-- 	<td style="overflow:hidden; width:60px; max-width:150px;" align="left" valign="top"><c:out
															value="${dtlsTO.custCode}" /> <br> <c:out value="${dtlsTO.custName}" /> <br><c:out value="${dtlsTO.custAddress}"/></td>
													 --%>
															<td
																style=" width: 60px; max-width: 150px; border-left: 2px dashed black; border-right: 2px dashed black;"
																align="left" valign="top" rowspan="4">
																<span style="word-wrap: break-word; width: 406px; height: 95px;">
																   <c:out value="${dtlsTO.custCode}" />
																	/
																	<c:out value="${dtlsTO.custName}" />
																	<br><c:out value="${dtlsTO.custAddress}" />
																</span>
																<%-- <div class="contr">
																	<c:out value="${dtlsTO.custCode}" />
																	/
																	<c:out value="${dtlsTO.custName}" />
																	<br><c:out value="${dtlsTO.custAddress}" />
																</div> --%>
															</td>
															<%-- <td align="left" valign="top">
															<div class="contl">
																<c:out value="${dtlsTO.custCode}" />
																/ <b><c:out value="${dtlsTO.custName}" /></b> <br><c:out
																		value="${dtlsTO.custAddress}" />
															</div>
														</td> --%>

															<!-- <td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td>
														<td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td>
														<td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td> -->

															<td align="left" valign="top"
																style="border-right: 2px dashed black;">Opening</td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td align="left" valign="top"
																style="border-top: 2px dashed black; border-right: 2px dashed black;">Closing</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>

														<tr class="showBtmLine">
															<td align="left" valign="bottom"
																style="border-left: 2px dashed black; border-right: 2px dashed black; border-bottom: 2px dashed black;">Pickup<br>Time:
																	${dtlsTO.runsheetTime}</td>
															<td align="left" valign="top" colspan="3" height="55"
																style="border-right: 2px dashed black; border-bottom: 2px dashed black; border-top: 2px dashed black;">Cust.
																Seal/Sign</td>
														</tr>
													</c:forEach>

													<!--  <table cellpadding="3" cellspacing="0" width="100%" > -->
													<c:if test="${fn:length(dtlsTOs.firstCol) lt  5}">

														<c:forEach begin="${fn:length(dtlsTOs.firstCol) }" end="4">
															<tr style="height: 0px">

																<td style="width: 60px; max-width: 150px;" align="left"
																	valign="top" rowspan="4"><div class="contr">
																		<c:out value="" />
																		<c:out value="" />
																		<br><c:out value="" />
																	</div></td>


																<td align="left" valign="top"></td>
																<td align="left" valign="top">&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td align="left" valign="top"></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>

															<tr>
																<td align="left" valign="bottom"><br></td>
																<td align="left" valign="top" colspan="3" height="55">
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<!-- </table>  -->
												</c:when>

												<c:otherwise>

													<c:forEach var="dtlsTO" items="${dtlsTOs.firstCol}"
														varStatus="status">
														<tr style="height: 0px">

															<td
																style="overflow: hidden; width: 60px; max-width: 150px; border-left: 2px dashed black; border-right: 2px dashed black;"
																align="left" valign="top" rowspan="4"><c:out
																	value="${dtlsTO.custCode}" /> <br><c:out
																		value="${dtlsTO.custName}" /></td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;">Opening</td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;">&nbsp;</td>


														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td align="left" valign="top"
																style="border-top: 2px dashed black; border-right: 2px dashed black;">Closing</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>

														<tr>
															<td align="left" valign="bottom"
																style="border-left: 2px dashed black; border-right: 2px dashed black; border-bottom: 2px dashed black;">
																Pickup<br>Time:${dtlsTO.runsheetTime} 
															</td>
															<td align="left" valign="top" colspan="2" height="55"
																style="border-right: 2px dashed black; border-bottom: 2px dashed black; border-top: 2px dashed black;">
																Cust. Seal/Sign</td>
														</tr>

													</c:forEach>
													<%-- <table cellpadding="3" cellspacing="0" width="100%" >--%>
													<c:if test="${fn:length(dtlsTOs.firstCol) lt  5}">

														<c:forEach begin="${fn:length(dtlsTOs.firstCol) }" end="4">
															<tr style="height: 0px">

																<td style="width: 60px; max-width: 150px;" align="left"
																	valign="top" rowspan="4"><div class="contr">
																		<c:out value="" />
																		<c:out value="" />
																		<br><c:out value="" />
																	</div></td>


																<td align="left" valign="top"></td>
																<td align="left" valign="top">&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td align="left" valign="top"></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>

															<tr>
																<td align="left" valign="bottom"><br></td>
																<td align="left" valign="top" colspan="3" height="55">
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<!-- </table>  -->

												</c:otherwise>
											</c:choose>

										</c:if>


										<%-- <c:if test="${empty dtlsTOs.firstCol}">
										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>

										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>
									</c:if> --%>




									</table>


								</td>
								<c:if test="${empty dtlsTOs.secondCol}">
									<td align="left" valign="top" width="50%">
										<table cellpadding="3" cellspacing="0" width="100%" border="0">
											<tr style="height: 0px">

												<td style="width: 0px; max-width: 150px;" align="left"
													valign="top" rowspan="2"><div class="contr">
														<c:out value="" />
														<c:out value="" />
														<br><c:out value="" />
													</div></td>


												<td align="left" valign="top"></td>
												<td align="left" valign="top">&nbsp;</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td align="left" valign="top"></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>

											<tr>
												<td align="left" valign="bottom"><br></td>
												<td align="left" valign="top" colspan="3" height="55">
												</td>
											</tr>
										</table>
									</td>
								</c:if>
								<c:if test="${not empty dtlsTOs.secondCol}">
									<td align="left" valign="top" width="50%">
										<table cellpadding="3" cellspacing="0" width="100%" border="0"
											style="border-right: 0px dashed black; height: 650%;">
											<tr class="showBtmLine">
												<td width="6%" align="left" valign="top"
													style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-left: 2px dashed black; border-right: 2px dashed black;"><bean:message
														key="label.print.pickupRunsheet.custName" /></td>
												<td width="3%" align="left" valign="top"
													style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-right: 2px dashed black;">Consignment</td>
												<%-- <td width="3%" align="left" valign="top"
											style="border-top: 2px dashed black;"><bean:message
												key="label.print.pickupRunsheet.closing" /></td> --%>
												<td width="3%" align="left" valign="top"
													style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-right: 2px dashed black;"><bean:message
														key="label.print.pickupRunsheet.total" /></td>
											</tr>

											<%-- <logic:present name="${pickupRunsheetForm.to.addressFlag}" scope="request">
										
									</logic:present> --%>
											<%-- <c:if test="${not empty dtlsTOs.secondCol}"> --%>
											<c:choose>
												<c:when test="${pkupRunsheetTO.addressFlag=='Y'}">

													<c:forEach var="dtlsTO" items="${dtlsTOs.secondCol}"
														varStatus="status">
														<tr style="height: 0px">

															<td
																style=" width: 60px; max-width: 150px; border-left: 2px dashed black; border-right: 2px dashed black;"
																align="left" valign="top" rowspan="4">
																<span style="word-wrap: break-word; width: 406px; height: 95px;">
																   <c:out value="${dtlsTO.custCode}" />
																	/
																	<c:out value="${dtlsTO.custName}" />
																	<br><c:out value="${dtlsTO.custAddress}" />
																</span>
																<%-- <div class="contr">
																	<c:out value="${dtlsTO.custCode}" />
																	/
																	<c:out value="${dtlsTO.custName}" />
																	<br><c:out value="${dtlsTO.custAddress}" />
																</div> --%>
															</td>
															<%-- <td align="left" valign="top">
															<div class="contl">
																<c:out value="${dtlsTO.custCode}" />
																/ <b><c:out value="${dtlsTO.custName}" /></b> <br><c:out
																		value="${dtlsTO.custAddress}" />
															</div>
														</td> --%>

															<!-- <td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td>
														<td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td>
														<td align="left" valign="top"
															style="border-bottom: 2px dashed black;">&nbsp;</td> -->

															<td align="left" valign="top"
																style="border-right: 2px dashed black;">Opening</td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td align="left" valign="top"
																style="border-top: 2px dashed black; border-right: 2px dashed black;">Closing</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>

														<tr class="showBtmLine">
															<td align="left" valign="bottom"
																style="border-left: 2px dashed black; border-right: 2px dashed black; border-bottom: 2px dashed black;">Pickup<br>Time:
																	${dtlsTO.runsheetTime}</td>
															<td align="left" valign="top" colspan="3" height="55"
																style="border-right: 2px dashed black; border-bottom: 2px dashed black; border-top: 2px dashed black;">Cust.
																Seal/Sign</td>
														</tr>
													</c:forEach>
													<%-- <table cellpadding="3" cellspacing="0" width="100%" >--%>
													<c:if test="${fn:length(dtlsTOs.firstCol) lt  5}">

														<c:forEach begin="${fn:length(dtlsTOs.firstCol) }" end="4">
															<tr style="height: 0px">

																<td style="width: 60px; max-width: 150px;" align="left"
																	valign="top" rowspan="4"><div class="contr">
																		<c:out value="" />
																		<c:out value="" />
																		<br><c:out value="" />
																	</div></td>


																<td align="left" valign="top"></td>
																<td align="left" valign="top">&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td align="left" valign="top"></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>

															<tr>
																<td align="left" valign="bottom"><br></td>
																<td align="left" valign="top" colspan="3" height="55">
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<!-- </table>  -->
												</c:when>

												<c:otherwise>
													<c:forEach var="dtlsTO" items="${dtlsTOs.secondCol}"
														varStatus="status">
														<tr style="height: 0px">

															<td
																style="overflow: hidden; width: 60px; max-width: 150px; border-left: 2px dashed black; border-right: 2px dashed black;"
																align="left" valign="top" rowspan="4"><c:out
																	value="${dtlsTO.custCode}" /> <br><c:out
																		value="${dtlsTO.custName}" /></td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;"">Opening</td>
															<td align="left" valign="top"
																style="border-right: 2px dashed black;">&nbsp;</td>


														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td align="left" valign="top"
																style="border-top: 2px dashed black; border-right: 2px dashed black;">Closing</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>
														<tr>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
															<td style="border-right: 2px dashed black;">&nbsp;</td>
														</tr>

														<tr class="showBtmLine">
															<td align="left" valign="bottom"
																style="border-bottom: 2px dashed black; border-left: 2px dashed black; border-right: 2px dashed black;">
																Pickup<br>Time:${dtlsTO.runsheetTime} 
															</td>
															<td align="left" valign="top" colspan="2" height="55"
																style="border-top: 2px dashed black; border-bottom: 2px dashed black; border-right: 2px dashed black;">
																Cust. Seal/Sign</td>
														</tr>

													</c:forEach>

													<%-- <table cellpadding="3" cellspacing="0" width="100%" >--%>
													<c:if test="${fn:length(dtlsTOs.firstCol) lt  5}">

														<c:forEach begin="${fn:length(dtlsTOs.firstCol) }" end="4">
															<tr style="height: 0px;">

																<td style="width: 60px; max-width: 150px;" align="left"
																	valign="top" rowspan="4"><div class="contr">
																		<c:out value="" />
																		<c:out value="" />
																		<br><c:out value="" />
																	</div></td>


																<td align="left" valign="top"></td>
																<td align="left" valign="top">&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td align="left" valign="top"></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td>&nbsp;</td>
																<td>&nbsp;</td>
															</tr>

															<tr>
																<td align="left" valign="bottom"><br></td>
																<td align="left" valign="top" colspan="3" height="55">
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<!-- </table>  -->

												</c:otherwise>
											</c:choose>

											<%-- 	</c:if> --%>

											<%-- 	<c:if test="${ empty dtlsTOs.secondCol}">

										<tr style="height: 106px">
											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>

										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

										<tr style="height: 106px">

											<td align="left" valign="top">&nbsp;<br>&nbsp;<br></td>

											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
											<td align="left" valign="top"
												style="border-bottom: 2px dashed black;">&nbsp;</td>
										</tr>
										<tr class="showBtmLine">
											<td align="left" valign="bottom">Pickup<br>Time: </td>
											<td align="left" valign="top" colspan="3" height="55">Cust.
												Seal/Sign</td>
										</tr>

									</c:if> --%>

										</table>
									</td>
								</c:if>
						</table>
						<!-- <br></br> -->
						<c:choose>
							<c:when test="${page.size() eq vstatus.count}">
								<table cellpadding="0" cellspacing="0" width="100%" border="0">
									<tr>
										<td width="40%" align="left">Br. Sales Exec Signs:</td>
										<td width="10%" align="left">...............</td>

										<td width="40%" align="left">Br. Ops Exec Sign:</td>
										<td width="10%" align="left">...............</td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>
								<div class="breakPage"></div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>

			</table>
		</html:form>
	</div>
</body>
</html>

