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
<style type="text/css" >
body { 
	/* font: normal 16px "Lucida Console", Monaco, monospace; */
	/* font: normal 15px "Verdana", Monaco, monospace; */
	/* font:normal 16px Arial; */
	font-size: 12px;
	font-family: "Times New Roman", Times,serif;
}

table tr .showLine td {
	border-left: 1px dashed black;
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

.breakPage {
	page-break-after: always;
	/* border-left: 2px dashed black; */
}
</style>
<style type="text/css" media="print">
@page {
	size: auto; /* auto is the initial value */
	margin-top: 0mm;
}
</style>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/drs/preparation/drsNormPriorityDoxPrint.js"></script>
</head>
<%-- <div id="errorDiv" style="color: red; float: right;">
	<html:messages name="error" id="message" bundle="errorBundle">
		<script type="text/javascript">
			$(document).ready(function() {
				_$tag__________________________
				alert('_$tag_______________________');
				_$tag__
			});
		</script>
	</html:messages>
</div> --%>
<body>
<html:form action="/deliveryPrintDrs.do" method="post" styleId="deliveryDrsForm">
	<c:forEach var="dtlsTOs" items="${page}" varStatus="vstatus">
		<jsp:include page="/pages/drs/preparation/drsPrintHeader.jsp" />
		<tr>
			<td align="left" valign="top">
				<table cellpadding="2" cellspacing="0" width="100%" border="0" class="showLine">
					<tr class="showTopBtmLine">
						<td align="left" valign="top">
							<table cellpadding="5" cellspacing="0" width="100%" border="0" class="showLine"	style="border-right: 1px dashed black; height: 650%;">
								<tr class="showTopBtmLine">
									<td width="6%" align="left" valign="top"><bean:message key="label.manifestGrid.serialNo" /></td>
									<td width="6%" align="left" valign="top"><bean:message key="label.print.drs.ConsgManifestNo" /></td>
									<td width="9%" align="left" valign="top"><bean:message key="label.print.drs.receiversSign" /></td>
									<td width="5%" align="left" valign="top"><bean:message key="label.print.drs.time" /></td>
									<c:if test="${not empty dtlsTOs.firstCol}">
										<c:forEach var="dtlsTO" items="${dtlsTOs.firstCol}" varStatus="status">
											<tr class="showBtmLine" style="height: 8.1em">
												<td align="left" valign="top"><c:out value="${dtlsTO.srNo }" /></td>
												<td align="left" valign="top">
													<c:out value="${dtlsTO.consigment}" /><br/>
													<c:out value="${dtlsTO.cityCode}" />
												</td>
												<td align="left" valign="top">&nbsp;</td>
												<td align="left" valign="top"><c:out value="${dtlsTO.time}" /></td>
											</tr>
										</c:forEach>
										<%-- <c:if test="${fn:length(dtlsTOs.firstCol) lt  drsTo.maxAllowedPrintRows/2}">
											 <c:forEach begin="${fn:length(dtlsTOs.firstCol) }" end="${(drsTo.maxAllowedPrintRows/2)-1}">
											 <tr class="showBtmLine" style="height: 60px">
											 <td height="30" align="left" valign="top"></td>
											 <td align="left" valign="top"></td>
											 <td align="left" valign="top">&nbsp;</td>
                                             <td align="left" valign="top">&nbsp;</td>
                                             </tr>
											 </c:forEach>
											 </c:if>
										--%>
									</c:if>
								</tr>
							</table>
						</td>
						<c:if test="${not empty dtlsTOs.secondCol}">
							<td align="left" valign="top">
								<!-- second table begins here -->
								<table cellpadding="5" cellspacing="0" width="100%" border="0" class="showLine" style="border-right: 1px dashed black; height: 650%;">
									<tr class="showTopBtmLine">
										<td width="6%" align="left" valign="top"><bean:message key="label.manifestGrid.serialNo" /></td>
										<td width="6%" align="left" valign="top"><bean:message key="label.print.drs.ConsgManifestNo" /></td>
 										<td width="9%" align="left" valign="top"><bean:message key="label.print.drs.receiversSign" /></td>
										<td width="5%" align="left" valign="top"><bean:message key="label.print.drs.time" /></td>
										<c:if test="${not empty dtlsTOs.secondCol}">
											<c:forEach var="dtlsTO" items="${dtlsTOs.secondCol}" varStatus="status">
												<tr class="showBtmLine" style="height: 8.1em">
													<td align="left" valign="top"><c:out value="${dtlsTO.srNo }" /></td>
													<td align="left" valign="top">
														<c:out value="${dtlsTO.consigment}" /><br/>
														<c:out value="${dtlsTO.cityCode}" />
													</td>
													<td align="left" valign="top">&nbsp;</td>
													<td align="left" valign="top"><c:out value="${dtlsTO.time}" /></td>
												</tr>
											</c:forEach>
											<%-- <c:if test="${fn:length(dtlsTOs.secondCol) lt  drsTo.maxAllowedPrintRows/2}">
												 <c:forEach begin="${fn:length(dtlsTOs.secondCol) }" end="${(drsTo.maxAllowedPrintRows/2)-1}">
												 <tr class="showBtmLine" style="height: 60px">
												 <td height="30" align="left" valign="top"></td>
												 <td align="left" valign="top"></td>
												 <td align="left" valign="top">&nbsp;</td>
                                                 <td align="left" valign="top">&nbsp;</td>
                                                 </tr>
												 </c:forEach>
												 </c:if>
											--%>
										</c:if>
									</tr>
								</table>
							</td>
						</c:if>
					</tr>
				</table>
			</td>
		</tr>
		<%-- 	<jsp:include page="/pages/drs/preparation/drsPrintFooter.jsp" >
			 	<jsp:param value="${dtlsTOs.totalConsg}" name="totalCN" />
				<c:if test="${not empty drsTo.fieldStaffTO}"> 
				<jsp:param value="${drsTo.fieldStaffTO.firstName}" name="fsName" />
				<jsp:param value="${drsTo.fieldStaffTO.lastName}" name="fsLastName" />
				</c:if>
				<c:if test="${not empty drsTo.franchiseTO }"> 
				<jsp:param value="${drsTo.franchiseTO.businessName}" name="fsName" />
				</c:if>
				<c:if test="${not empty drsTo.coCourierTO}"> 
				<jsp:param value="${drsTo.coCourierTO.firstname}" name="fsName" />
				<jsp:param value="${drsTo.coCourierTO.lastName}" name="fsLastName" />
				</c:if>
				<c:if test="${not empty drsTo.baTO}"> 
				<jsp:param value="${drsTo.baTO.businessName}" name="fsName" />
				</c:if>
				</jsp:include> 
			--%>
		<br />
		<c:if test="${page.size() eq vstatus.count}">
		<tr>
			<td align="left" valign="top">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tfoot>
						<tr>
							<td width="18" align="left" valign="top">
								<table cellpadding="2" cellspacing="2" width="100%" border="0">
									<tr class="showTopLine">
										<c:if test="${not empty drsTo.fieldStaffTO}">
											<td width="48%" align="left" valign="top">
												Total <c:out value="${dtlsTOs.totalConsg}" /> deliveries taken by 
												<c:out value="${drsTo.fieldStaffTO.firstName}" /> 
												<c:out value="${drsTo.fieldStaffTO.lastName}" />
											</td>
										</c:if>
										<c:if test="${not empty  drsTo.franchiseTO }">
											<td width="48%" align="left" valign="top">
												Total <c:out value="${dtlsTOs.totalConsg}" /> deliveries taken by 
												<c:out value="${drsTo.franchiseTO.businessName}" />
											</td>
										</c:if>
										<c:if test="${not empty drsTo.coCourierTO}">
											<td width="48%" align="left" valign="top">
												Total <c:out value="${dtlsTOs.totalConsg}" /> deliveries taken by 
												<c:out value="${drsTo.coCourierTO.firstname}" /> 
											 	<c:out value="${drsTo.coCourierTO.lastName}" />
											</td>
										</c:if>
										<c:if test="${not empty drsTo.baTO}">
											<td width="48%" align="left" valign="top">
												Total <c:out value="${dtlsTOs.totalConsg}" /> deliveries taken by 
												<c:out value="${drsTo.baTO.businessName}" />
											</td>
										</c:if>
										<td width="52%" align="left" valign="top">Total...... C/ments delivered in STP</td>
									</tr>
									<!-- <tr> -->
									<!-- <th align="left">&nbsp;</th> -->
									<!-- <th align="left">&nbsp;</th> -->
									<!-- </tr> -->
									<!-- <br/> -->
									<tr class="showBtmLine">
										<td colspan="2" align="left">All the deliveries checked &amp; tallied with address &amp; stamp on Pod by:</td>
									</tr>
									<!-- <tr>
										 <td align="left" valign="top" colspan="4"></td>
										 </tr> 
									-->
								</table>
							</td>
						</tr>
					</tfoot>
					<!-- <tr>
	         			 <td colspan="10" align="left" valign="top">
	         			 --------------------------------------------------------------------------------
	         			 </td>
	         			 </tr> 
	         		-->
					<!-- <tr>
          				 <td align="right" valign="top"> Ver : UDAAN 1.0 Released on 25/03/2013 </td>
        				 </tr> 
        			-->
				</table>
			</td>
		</tr>
		</c:if>
	
		<c:if test="${page.size() ne vstatus.count}">
			<tr>
				<td align="left" valign="top">
				<div class="breakPage">&nbsp;</div> 
				<!-- 	<table cellpadding="2" cellspacing="0" width="100%" border="0" class="breakPage">
        				<tr>
          				<td width="18" align="left" valign="top"><table cellpadding="5" cellspacing="4" width="100%" border="0">
	              		<tr>
    	            	<td width="48%" align="left" valign="top"> </td>
        	        	<td width="48%" align="left" valign="top"> </td>
            	    	<td width="48%" align="left" valign="top"> </td>
                		<td width="48%" align="left" valign="top"> </td>
                		<td width="52%" align="left" valign="top"></td>
	                	<th><br></br></th>
    	          		</tr>
        	      		<tr>
            	    	<th align="left">&nbsp;</th>
                		<th align="left">&nbsp;</th>
              			</tr>
	              		<tr>
    	            	<th colspan="2" align="left">  <br />
        	          	<br/><br/><br></br><br></br>
            	    	</th>
                		<th align="left">&nbsp;</th>
                		<th align="left">&nbsp;</th>
	              		</tr>
    	          		<tr>
        	        	<td align="left" valign="top" colspan="4"></td>
            	  		</tr>
            			</table></td>
        				</tr>
	        			<tr>
		         		<td colspan="10" align="left" valign="top">
	    	     		--------------------------------------------------------------------------------
	        	 		</td>
	         			</tr>
        				<tr>
	          			<td align="right" valign="top"> Ver : UDAAN 1.0 Released on 25/03/2013 </td>
    	    			</tr>
      					</table> 
      			-->	
      			</td>
			</tr>
		</c:if>
	</c:forEach>
</html:form>
</body>
</html>
