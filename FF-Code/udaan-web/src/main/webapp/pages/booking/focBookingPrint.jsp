<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<title>First Flight</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	/*background:url(POD.jpg) no-repeat;
	background:url('POD.jpg');
	background-repeat: no-repeat;*/
	margin: 0px;
	padding: 0px;
	border: 0px solid #000;
	width: 100%;
}
</style>
<style type="text/css" media="print">
@page {
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}
</style>
<script type="text/javascript">
	function printpage() {
		window.print();
		self.close();
	}
</script>
</head>
<body onload="printpage();">
	<table width="100%" cellpadding="0" cellspacing="0"
		style="border: 0px dashed #000; width: 100%; margin: 0; float: left;">
		<tr>
			<td width="967" align="left" valign="top"><table width="100%"
					cellpadding="0" cellspacing="0" border="0">
					<!-- <tr>
              <td rowspan="2" width="227"><h1>Logo</h1></td>
              <td width="238" align="center">P.O.D Copy</td>
              <td rowspan="2" width="432" valign="top" align="left"><img src="Trans.gif" /></td>
            </tr>-->
					<tr>
						<td height="29" align="center" valign="top"><c:if
								test="${consignmentModificationTO.customer ne null}">
								<!-- Customer Code -->
								<div style="padding-left: 32%; text-align: left;">
									${consignmentModificationTO.customer.customerCode}</div>
							</c:if></td>
					</tr>
				</table></td>
			<td width="15" rowspan="2" align="left" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td height="294" align="left" valign="top"><table width="100%"
					style="border: 0px dashed #000;" cellpadding="0" cellspacing="0">
					<tr>
						<td height="16" colspan="2">&nbsp;</td>
						<td width="175" valign="top" colspan="2"><table width="98%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="0">
								<tr>
									<c:choose>
										<c:when
											test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
											<td width="15%" align="center"
												style="padding-top: 11px; padding-left: 65px;"><img
												src="images/correct.gif" width="17" height="15" /></td>
											<td width="32%">&nbsp;</td>
											<td width="7%" align="center"></td>
											<td width="46%">&nbsp;</td>
										</c:when>

										<c:otherwise>
											<td width="15%" align="center"></td>
											<td width="32%">&nbsp;</td>
											<td width="7%" align="center"
												style="padding-top: 8px; padding-left: 110px;"><img
												src="images/correct.gif" width="17" height="15" /></td>
											<td width="46%">&nbsp;</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</table></td>

						<c:choose>
							<c:when
								test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
								<td colspan="2" valign="top"></td>
							</c:when>
							<c:otherwise>
								<td colspan="2" valign="bottom" align="center">&nbsp;&nbsp;&nbsp;&nbsp;${consignmentModificationTO.cnContents.cnContentName}</td>
							</c:otherwise>
						</c:choose>
						<!-- For paecel -->
						<td width="64" valign="top">&nbsp;</td>
						<td colspan="2" rowspan="3" valign="top"><table width="100%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="0">
								<!-- <tr>
                    <td colspan="3" align="center">&nbsp;</td>
                  </tr>-->
								<tr>
									<td width="18%" valign="top">&nbsp;</td>
									<c:choose>
										<c:when
											test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
											<td width="17%"></td>
											<!-- For paecel -->
											<td width="54%" rowspan="4" valign="top"></td>
											<!-- For paecel -->
										</c:when>
										<c:otherwise>
											<td width="17%" rowspan="4" colspan="2" valign="bottom">${consignmentModificationTO.cnPaperWorks.cnPaperWorkName}
												<br /> ${consignmentModificationTO.paperWorkRefNo}
											</td>
											<!-- For paecel -->
											<%-- 											<td width="54%" rowspan="4" valign="bottom">${consignmentModificationTO.paperWorkRefNo}</td> --%>
											<!-- For paecel -->
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
									<td valign="top">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td valign="top">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td height="14" valign="top">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table></td>
						<td width="125" rowspan="3" valign="top"><table width="100%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="1">
								<tr>
									<td colspan="3" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td width="66%" align="center">&nbsp;</td>
									<td width="34%" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td valign="middle" align="center"
										style="padding-right: 55px; padding-top: 13px;">${dateWeight[2]}</td>
									<td valign="middle" align="center"
										style="padding-right: 47px; padding-top: 13px;">${dateWeight[3]}</td>
								</tr>
								<tr>
									<td valign="middle" align="center">&nbsp;</td>
									<td valign="middle" align="center">&nbsp;</td>
								</tr>
							</table></td>
					</tr>
					<tr>


						<c:choose>
							<c:when
								test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
								<td width="64" height="22">&nbsp;</td>
								<td width="163" valign="middle">&nbsp;${dateWeight[0]}</td>
								<td width="32">&nbsp;</td>
								<td width="175" valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dateWeight[1]}</td>
								<td colspan="2"></td>
							</c:when>
							<c:otherwise>
								<td width="64" height="22">&nbsp;</td>
								<td width="163" valign="bottom">&nbsp;${dateWeight[0]}</td>
								<td width="32">&nbsp;</td>
								<td width="175" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dateWeight[1]}</td>
								<td colspan="2" align="center" valign="bottom">${consignmentModificationTO.noOfPieces}</td>
							</c:otherwise>
						</c:choose>
						<!-- for parcel -->
						<td width="64">&nbsp;</td>
					</tr>
					<tr>
						<td width="64" height="32" valign="top">&nbsp;</td>
						<td width="163" valign="top" style="padding-top: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;${bookingCity}</td>
						<td width="32" valign="top">&nbsp;</td>
						<td width="175" valign="top"
							style="padding-top: 10px; padding-left: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${destinationCity}</td>

						<c:choose>
							<c:when
								test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
								<td colspan="2" valign="top"></td>
							</c:when>
							<c:otherwise>
								<td colspan="2" align="center" valign="middle">${decl_value}</td>
							</c:otherwise>
						</c:choose>
						<!-- for parcel -->
						<td width="64" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="92" colspan="4" valign="top"><table width="100%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="0">
								<tr>
									<td width="24%" height="14">&nbsp;</td>
									<td width="76%" height="55" valign="top">
										<div style="height: 100%; width: 75%; padding-top: 2px;">
											${consignorTO.firstName}&nbsp;${consignorTO.lastName}<br />
											${consignorTO.address}
										</div>
									</td>
								</tr>
								<tr>
									<td height="5">&nbsp;</td>
									<td>&nbsp;<br />
									</td>
								</tr>
								<tr>
									<td height="15">&nbsp;</td>
									<td valign="bottom"><br />&nbsp;&nbsp;${consignorTO.mobile}/${consignorTO.phone}</td>
								</tr>
							</table></td>
						<td colspan="5" valign="top"><table width="100%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="0">
								<tr>
									<td width="17%" height="14">&nbsp;</td>
									<td width="83%" height="53" valign="top">
										<div style="height: 100%; width: 78%; padding-top: 4px;">
											${consigneeTO.firstName}&nbsp;${consigneeTO.lastName}<br />
											${consigneeTO.address}
										</div>
									</td>
								</tr>
								<tr>
									<td height="5">&nbsp;</td>
									<td>${consignmentModificationTO.pincode}</td>
								</tr>
								<tr>
									<td height="5">&nbsp;</td>
									<td></td>
								</tr>
								<tr>
									<td height="15">&nbsp;</td>
									<td valign="bottom">
										&nbsp;&nbsp;${consigneeTO.mobile}/${consigneeTO.phone}</td>
								</tr>
							</table></td>
						<td rowspan="5" align="left" valign="top"><table width="100%"
								style="border: 0px dashed #000;" cellpadding="0" cellspacing="1">
								<tr>
									<td colspan="3" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td width="65%" align="center">&nbsp;</td>
									<td width="35%" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td valign="bottom" align="center"
										style="padding-right: 55px; padding-top: 5px;">${dateWeight[4]}</td>
									<td valign="bottom" align="center"
										style="padding-right: 47px; padding-top: 5px;">${dateWeight[5]}</td>
								</tr>
								<tr>
									<td width="65%" align="center">&nbsp;</td>
									<td width="35%" align="center">&nbsp;</td>
								</tr>
							</table>
							<table width="100%" border="0" style="border: 0px #000 solid"
								cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="4" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td width="49%" align="center">&nbsp;</td>
									<td width="18%" align="center">&nbsp;</td>
									<td width="33%" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4" align="center">&nbsp;</td>
								</tr>
								<tr>
									<td width="49%" align="center">&nbsp;</td>
									<td width="18%" align="center">&nbsp;</td>
									<c:choose>
										<c:when
											test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
											<td width="33%" align="center"></td>
										</c:when>
										<c:otherwise>
											<td width="33%" align="top" style="padding-right: 44px;">${consignmentModificationTO.length}</td>
										</c:otherwise>
									</c:choose>
									<!-- For parcel -->
								</tr>
								<tr>
									<td height="28" align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<td height="25">&nbsp;</td>
								</tr>
								<tr>
									<td align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<c:choose>
										<c:when
											test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
											<td width="33%" align="center"></td>
										</c:when>
										<c:otherwise>
											<td width="33%" align="top" style="padding-right: 44px;">${consignmentModificationTO.breath}</td>
										</c:otherwise>
									</c:choose>
									<!-- For paecel -->
								</tr>
								<tr>
									<td align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td height="47" align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<c:choose>
										<c:when
											test="${consignmentModificationTO.consgTypeCode eq 'DOX'}">
											<td width="33%" align="center"></td>
										</c:when>
										<c:otherwise>
											<td width="33%" align="top" style="padding-right: 44px;">${consignmentModificationTO.height}</td>
										</c:otherwise>
									</c:choose>
									<!-- For paecel -->
								</tr>
							</table></td>
					</tr>

					<tr>
						<td colspan="4" rowspan="2" valign="top">&nbsp;&nbsp;</td>
						<td valign="top" height="21">&nbsp;</td>
						<td colspan="2" valign="bottom">
							<!-- sign here -->
						</td>
						<td colspan="2" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="21" valign="top">
							<!--Receiver's Full Name:-->
						</td>
						<td colspan="2" valign="bottom">
							<!-- text -->
						</td>
						<td colspan="2" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td height="31" colspan="4" valign="top">&nbsp;</td>
						<td valign="top">&nbsp;</td>
						<td width="89" valign="top">
							<!-- 12/10/2013 -->
						</td>
						<td width="27" valign="top">&nbsp;</td>
						<td width="104" valign="top">
							<!-- 14:25 -->
						</td>
						<td width="135" valign="top" align="left">
							<!-- Company Seal -->
						</td>
					</tr>
					<tr>
						<td width="64" height="16" valign="top">
							<!--Employee Sign-->
						</td>
						<td width="163" valign="top"></td>
						<td valign="top">
							<!--Consignor's Sign-->
						</td>
						<td valign="top"></td>
						<td colspan="5" valign="top">&nbsp;</td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
