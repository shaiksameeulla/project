<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.capgemini.lbs.framework.utils.DateUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/global.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	function printpage() {
		window.print();
		self.close();
	}
</script>
</head>
<body onload="printpage()">
	<html:form action="/cnPrinting.do" styleId="cnPrintingForm">
		<!--- CONTENT SECTION -->
		<div id="contentwrapper">
			<div class="contentContainer" id="contentContainer">
				<c:forEach var="cnSeriesTO" items="${stickerToArray}"
					varStatus="status">
					<table border="0" width="340px;" heigh="45">
						<tr>
							<td colspan="2">
								<table border="0" border="0">
									<tr>
										<td width="150px;">&nbsp;</td>
										<td>${cnSeriesTO.originCityCode}</td>
										<td style="font-size: 9"><span> ${cnSeriesTO.time}
										</span> <br> <span style="font-size: 10">${cnSeriesTO.date}
										</span></td>
									</tr>
									<tr>
										<td colspan="2" style="font-size: 12" width="150px">Sold:
											004</td>
										<td style="font-size: 12">Qty# &nbsp;&nbsp;
											&nbsp;${cnSeriesTO.quantity}</td>

									</tr>
									<tr style="font-size: 11">
										<td colspan="3">To, ${cnSeriesTO.consigneeName} <br />
											${cnSeriesTO.city} <br> ${cnSeriesTO.address}
										</td>
									</tr>
									<tr valign="bottom">
										<td colspan="2" style="font-size: 11" valign="bottom">${cnSeriesTO.city}</td>
										<td align="center" style="font-size: 11" valign="bottom">${cnSeriesTO.pinCode}</td>
									</tr>
								</table>
							</td>
							<td align="right">
								<table border="0">
									<tr>
										<td align="right"><img width="98" height="30"
											src="/udaan-web/images/cnPrinting/<c:out value="${cnSeriesTO.consgNo}"/>_TOP.png" /></td>
									</tr>
									<tr>
										<td align="right"><img width="98" height="30"
											src="/udaan-web/images/cnPrinting/<c:out value="${cnSeriesTO.consgNo}"/>_TOP.png" /></td>
									</tr>
									<tr>
										<td align="right" rowspan="2"><img width="98" height="30"
											src="/udaan-web/images/cnPrinting/<c:out value="${cnSeriesTO.consgNo}"/>_TOP.png" />
											<br />
											<img width="98" height="30"
											src="/udaan-web/images/cnPrinting/<c:out value="${cnSeriesTO.consgNo}"/>_BOTTOM.png" /></td>
									</tr>

								</table>
							</td>
						</tr>

						<tr>
							<td colspan="3">
								<table width="100%" border="0">
									<tr>
										<td style="font-size: 11" width="10%"><b> 504U3 </b></td>
										<td align="center" style="font-size: 11;" width="75%">UTI
											PIN - RED TO BASE BRANCH</td>
										<td style="font-size: 13;" width="15%"><b> AHD </b></td>

									</tr>
								</table>

							</td>

						</tr>
					</table>
					<br />
				</c:forEach>
			</div>
		</div>
	</html:form>
</body>
</html>
