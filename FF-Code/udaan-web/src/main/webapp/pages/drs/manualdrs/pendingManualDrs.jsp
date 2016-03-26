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
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script> DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript"
	src="js/drs/manualdrs/pendingManualDrs.js"></script>

<!-- DataGrids /-->
</head>
<body>
	<html:form method="post" styleId="pendingDrsForm">
		<div id="wraper">


			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.drs.manual.pending" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.cashbooking.mandatoryField" />
						</div>
					</div>
					<!--  Header START-->
					<jsp:include page="/pages/drs/manualdrs/manualDrsHeader.jsp" />

					<!--  Header END-->

					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.drs.prep.npdox.details" />
							</div>
						</div>
						<!-- Button -->
						<div class="button_containerform">
							<br /> <a href="#" title="Update Delivered Consignments"
								onclick="navigateToDrsPage('${pendingDrsForm.to.updateDeliveredUrl}')"
								tabindex="-1" id="navigatorlink"><u><strong> <bean:message
											key="label.drs.pending.link" />
								</strong></u></a><br />
						</div>
						<!-- Button ends -->
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="drsUpdate" width="100%">
							<thead>
							<tr>
								<th width="2%" align="center"><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('chkbx',this.checked);" tabindex="-1"/></th>
								<th width="3%" align="center"><bean:message key="label.common.serialNo" /></th>
								<th width="7%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.consgNo" /></th>
								<th width="7%"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.prep.Origin" /> </th>
								<th width="10%"><sup class="star">*</sup>&nbsp;
								
								<bean:message key="label.drs.update.reason" />
								</th>
								<th width="7%"><bean:message key="label.drs.update.missedcard" /></th>
								<th width="10%"><bean:message key="label.drs.update.remarks" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="drsDtls" items="${pendingDrsForm.to.drsDetailsTo}" varStatus="loop">
                           <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
                               <td><input type="checkbox" name="to.checkbox" value='${loop.count-1}' id="checkbox${loop.count}"  tabindex="-1" disabled="disabled"/></td>
                               <td><label><c:out value="${drsDtls.rowNumber}"/> </label></td>
                              <td><html:text styleId="rowConsignmentNumber${loop.count}" property="to.rowConsignmentNumber" styleClass="txtbox width145" value='${drsDtls.consignmentNumber}' readonly="true" tabindex="-1" /></td>
                               <td align="center" >
			                    <html:text styleId="rowOriginCityName${loop.count}" property="to.rowOriginCityName" value="${drsDtls.originCityName}" styleClass="txtbox width140" readonly="true" />
			                     <html:hidden styleId="rowOriginCityId${loop.count}" property="to.rowOriginCityId" value="${drsDtls.originCityId}"/>
			                <html:hidden styleId="rowOriginCityCode${loop.count}" property="to.rowOriginCityCode" value="${drsDtls.originCityCode}"/>
			                <html:hidden styleId="rowConsignmentId${loop.count}" property="to.rowConsignmentId" value="${drsDtls.consgnmentId}"/>
			                </td>
                               <td>
                               
                               	<html:select property="to.rowPendingReasonId" styleId="rowPendingReasonId${loop.count}"
									styleClass="selectBox" disabled="true" style="width: 100px" tabindex="-1">
									<option value="">SELECT</option>
									<logic:present name="drsDtls" property="nonDlvReason">
										<logic:iterate id="reason"  name="drsDtls" property="nonDlvReason" >
											<c:choose>
												<c:when	test="${reason.key==drsDtls.reasonId}">
													<option value="${reason.key}" selected="selected">
														<c:out value='${reason.value}' />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${reason.key}">
														<c:out value='${reason.value}' />
													</option>
												</c:otherwise>
											</c:choose>
										</logic:iterate>
									</logic:present>
								</html:select>
                               
                               </td>
                                
                                <td><html:text styleId="rowMissedCardNumber${loop.count}" property="to.rowMissedCardNumber" styleClass="txtbox width100" value='${drsDtls.missedCardNumber}' readonly="true" tabindex="-1" maxlength="25"/><!-- styleClass="inputFieldGrey" --></td>
                                <td><html:text styleId="rowRemarks${loop.count}" property="to.rowRemarks" styleClass="txtbox width100" value='${drsDtls.remarks}' readonly="true" tabindex="-1" maxlength="30"/>
                                <html:hidden styleId="rowDeliveryDetailId${loop.count}" property="to.rowDeliveryDetailId" value='${drsDtls.deliveryDetailId}'/>
                                 <html:hidden styleId="rowAlreadyAddedRow${loop.count}" property="to.rowAlreadyAddedRow" value='${drsDtls.alreadyAddedRow}'/>
                                 <input type="hidden" id="rowParentChildCnType${loop.count}" name="to.rowParentChildCnType" />
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
				<input type="hidden" id="manualpending"/>
				 <input  type="hidden" id="addedRowCount" value="${pendingDrsForm.to.addedRowCount}"/>
				<!--hidden fields ENDs  here -->
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<!-- Button -->
				<html:button property="Save" styleClass="btnintform" styleId="Save"
					title="Save">
					<bean:message key="button.label.save" />
				</html:button>
				<html:button property="Print" styleClass="btnintform"
					styleId="Print" title="Print" style="display:none">
					<bean:message key="button.label.Print" />
				</html:button>
				<html:button property="Cancel" styleClass="btnintform"
					styleId="Cancel" title="Clear Screen">
					<bean:message key="button.label.Cancel" />
				</html:button>
				<!-- Button ends -->
				<html:button property="Delete" styleClass="btnintform"
					styleId="Delete" title="Delete">
					<bean:message key="button.label.delete" />
				</html:button>
				
				<%-- <c:if test="${isDRSCloseRequired == pendingDrsForm.to.flagYes}">
				<html:button property="Close" styleClass="btnintform"
					styleId="Close" title="Close Drs">
					<bean:message key="button.label.drs.close" />
				</html:button>
				</c:if> --%>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->

		</div>
		<!--wraper ends-->

	</html:form>
</body>
</html>
