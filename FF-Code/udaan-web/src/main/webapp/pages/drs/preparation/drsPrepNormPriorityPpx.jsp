<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript"	src="js/drs/preparation/drsPrepNormPriorityPpx.js"></script>
<script type="text/javascript" src="js/drs/drsPrint.js"></script>
<!-- DataGrids /-->
</head>
<body>
<html:form method="post" styleId="npDrsForm">
	<!--wraper-->
<div id="wraper"> 
 <div id="topPanel">
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1> <bean:message key="label.drs.prep.npppx" /></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span>  <bean:message key="label.cashbooking.mandatoryField" /></div>
      </div>
       <!--  Header START-->
               <jsp:include page="/pages/drs/drsHeader.jsp" />
              
      <!--  Header END-->
					<div id="demo">
						<div class="title">
							<div class="title2"><bean:message key="label.drs.prep.npdox.details" /></div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="drsPrepNpPpx" width="100%">
							<thead>
								<tr>
								<c:if test="${(empty npDrsForm.to.deliveryId) or (npDrsForm.to.deliveryId==0) or (npDrsForm.to.deliveryId == null) }">
									<th width="1%" align="center" ><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('chkbx',this.checked);" tabindex="-1"/></th>
									</c:if>
									<th width="2%" align="center"><bean:message key="label.common.serialNo" /></th>
									<th width="8%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.consgNo" /></th>
									<th width="6%"><bean:message key="label.drs.prep.Origin" /></th>
									
									<th width="2%"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.ppx.noofpieces" /></th>
									<th width="4%"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.ppx.actualwght" /></th>
									<th width="3%"><bean:message key="label.drs.ppx.chrblewght" /></th>
									<th width="3%"><bean:message key="label.drs.ppx.cnts" /></th>
									<th width="4%"><bean:message key="label.drs.ppx.pprwrk" /></th>
									<th width="5%"><bean:message key="label.drs.ppx.amnt" /></th>
									<th width="6%"><bean:message key="label.drs.prep.ccQ.consgneeName" /></th>
								</tr>
							</thead>
								<tbody>
									<c:forEach var="drsDtls" items="${npDrsForm.to.detailsToList}"
										varStatus="loop">
										<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
											
											<td align="center"><c:out value='${loop.count}' /></td>
											
											<td align="center"><html:text
													styleId="rowConsignmentNumber${loop.count}"
													property="to.rowConsignmentNumber"
													value="${drsDtls.consignmentNumber}"
													styleClass="txtbox width110" readonly="true" tabindex="-1"/>
													
													<html:hidden
													styleId="rowConsignmentId${loop.count}"
													property="to.rowConsignmentId"
													value="${drsDtls.consgnmentId}"	/>
													</td>
											<td align="center"><html:text
													styleId="rowOriginCityName${loop.count}"
													property="to.rowOriginCityName"
													value="${drsDtls.originCityName}"
													styleClass="txtbox width140" readonly="true" tabindex="-1"/> <html:hidden
													styleId="rowOriginCityId${loop.count}"
													property="to.rowOriginCityId"
													value="${drsDtls.originCityId}" /> <html:hidden
													styleId="rowOriginCityCode${loop.count}"
													property="to.rowOriginCityCode"
													value="${drsDtls.originCityCode}" /></td>
													
													<td align="center">
													<html:text
													styleId="rowNoOfPieces${loop.count}"
													property="to.rowNoOfPieces"
													value="${drsDtls.noOfPieces}"
													styleClass="txtbox width30" readonly="true" tabindex="-1"/>
													</td>
													
													<td align="center">
													<input type="text" name="to.rowActualWeight" id="rowActualWeight${loop.count}" value="${drsDtls.actualWeight}" class="txtbox width50" readonly="readonly" tabindex="-1"/>
													</td>
													<td align="center">
													<input type="text" name="to.rowChargeableWeight" id="rowChargeableWeight${loop.count}" value="${drsDtls.chargeableWeight}" class="txtbox width50" readonly="readonly" tabindex="-1"/>
													</td>
													<td align="center">
													<input type="text" name="to.rowContentName" id="rowContentName${loop.count}" value="${drsDtls.contentName}" class="txtbox width110" readonly="readonly" tabindex="-1"/>
													<input type="hidden" name="to.rowContentId" id="rowContentId${loop.count}" value="${drsDtls.contentId}"/>
													</td>
													<td align="center">
													<input type="text" name="to.rowPaperworkName" id="rowPaperworkName${loop.count}" value="${drsDtls.paperWorkName}" class="txtbox width110" readonly="readonly" tabindex="-1"/>
													<input type="hidden" name="to.rowPaperworkId" id="rowPaperworkId${loop.count}" value="${drsDtls.paperWorkId}" />
													</td>
													<td align="center">
													<input type="text" name="to.rowAmount" id="rowAmount${loop.count}" value="${drsDtls.amount}" class="txtbox width80" readonly="readonly" tabindex="-1"/>
													</td>
													
													<td align="center">
													<input type="text" name="to.rowConsineeName" id="rowConsineeName${loop.count}" value="${drsDtls.consigneeName}" class="txtbox width110" readonly="readonly" tabindex="-1"/>
													</td>
													
													
										</tr>
									</c:forEach>

								</tbody>
							</table>
					</div>
					<!-- Grid /-->
				</div>
				<!--hidden fields start from here -->
				<jsp:include page="/pages/drs/drsCommon.jsp" />
				<!--hidden fields ENDs  here -->

			</div>
			</div>
			<!-- Button -->
			<jsp:include
				page="/pages/drs/preparation/drsPrepButtonsContainer.jsp" />
			<!-- Button ends -->
			<!-- main content ends -->

		</div>
		<!--wraper ends-->
	</html:form>
</body>
</html>