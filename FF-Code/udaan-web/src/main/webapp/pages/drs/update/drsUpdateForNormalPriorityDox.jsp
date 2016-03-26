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
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript"
	src="js/drs/update/drsUpdateForNormalPriorityDox.js"></script>
</head>
<body>
	<html:form method="post" styleId="npDrsForm">
		<!--wraper-->
		<div id="wraper">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.drs.update.npdox" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.cashbooking.mandatoryField" />
						</div>
					</div>
					<!--  Header START-->
					<jsp:include page="/pages/drs/drsHeader.jsp" />
					<!--  Header END-->

					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.drs.prep.npdox.details" />
							</div>
						</div>

						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="drsUpdateNpDox" width="100%">
							<thead>
								<tr>
									<th width="3%" align="center"><bean:message
											key="label.common.serialNo" /></th>
									<th width="8%" align="center"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.consgNo" /></th>
									<th width="10%"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.update.delivery.type" /></th>
									<th width="10%"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.update.delivery.time" /></th>
									<th width="10%"><sup class="star">*</sup><bean:message
											key="label.drs.update.receiver.name" /></th>
									<th width="8%"><sup class="star">*</sup><bean:message
											key="label.drs.update.contact.number" /></th>
									<th width="15%"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.update.sealsign" /></th>
									<th width="10%"><bean:message
											key="label.drs.update.reason" /></th>
									<th width="20%"><bean:message
											key="label.drs.update.missedcard" /></th>
									<th width="20%"><bean:message
											key="label.drs.update.remarks" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="drsDtls" items="${npDrsForm.to.detailsToList}"
									varStatus="loop">
									<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
										<td><label><c:out value="${drsDtls.rowNumber}" />
										</label></td>
										<td><html:text
												styleId="rowConsignmentNumber${loop.count}"
												property="to.rowConsignmentNumber"
												styleClass="txtbox width145"
												value='${drsDtls.consignmentNumber}' readonly="true"
												tabindex="-1" /></td>
										<td><html:select property="to.rowDeliveryType"
												styleId="rowDeliveryType${loop.count}"
												styleClass="selectBox width140"
												value='${drsDtls.deliveryType}' onchange="enableForDlvType('${loop.count}')" onkeypress="return enterKeyNav('rowDeliveryTimeInHH${loop.count}',event.keyCode);">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="dlvTypeMap" scope="request">
													<html:optionsCollection name="dlvTypeMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
										<td><html:text styleId="rowDeliveryTimeInHH${loop.count}"
												property="to.rowDeliveryTimeInHH" styleClass="txtbox width30"
												value='${drsDtls.deliveryTimeInHHStr}' maxlength="2" onchange="clearDeliveryTimeMinutes('${loop.count}')" onkeypress="return enterKeyNav('rowDeliveryTimeInMM${loop.count}',event.keyCode)"/> <bean:message key="label.common.colon" /><html:text styleId="rowDeliveryTimeInMM${loop.count}"
												property="to.rowDeliveryTimeInMM" styleClass="txtbox width30"
												value='${drsDtls.deliveryTimeInMMStr}' maxlength="2" onchange="validateDeliveryTime('${loop.count}')" onkeypress="return enterKeynavForDlvTime('${loop.count}',event.keyCode)"/>
										</td>
										<td><html:text styleId="rowReceiverName${loop.count}"
												property="to.rowReceiverName" styleClass="txtbox width100"
												value='${drsDtls.receiverName}' maxlength="20"  onkeypress="return enterKeyNav('rowContactNumber${loop.count}',event.keyCode);"/></td>
										<td><html:text styleId="rowContactNumber${loop.count}"
												property="to.rowContactNumber" styleClass="txtbox width100"
												value='${drsDtls.contactNumber}' maxlength="11"  onchange="validateContactNumber(this)" onkeypress="return onlyNumeric(event)" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,'${loop.count}','rowContactNumber')"/></td>
										<td><html:select property="to.rowCompanySealSign"
												styleId="rowCompanySealSign${loop.count}"
												styleClass="selectBox width140"
												value='${drsDtls.companySealSign}' disabled="true" onkeypress="return enterKeyNavforDrsUpdateContactNum(event.keyCode,'${loop.count}','rowCompanySealSign')">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="drsSealMap" scope="request">
													<html:optionsCollection name="drsSealMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
										<td><html:select property="to.rowPendingReasonId"
												styleId="rowPendingReasonId${loop.count}"
												styleClass="selectBox width140" disabled="true" tabindex="-1">
												<option value="">SELECT</option>
												<logic:present name="drsDtls" property="nonDlvReason">
													<logic:iterate id="reason" name="drsDtls"
														property="nonDlvReason">
														<c:choose>
															<c:when test="${reason.key==drsDtls.reasonId}">
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
											</html:select></td>
										<td><html:text styleId="rowMissedCardNumber${loop.count}"
												property="to.rowMissedCardNumber"
												styleClass="txtbox width100"
												value='${drsDtls.missedCardNumber}' readonly="true"
												tabindex="-1" maxlength="25" /></td>
										<td><html:text styleId="rowRemarks${loop.count}"
												property="to.rowRemarks" styleClass="txtbox width100"
												value='${drsDtls.remarks}' readonly="true" tabindex="-1"
												maxlength="30" /> <html:hidden
												styleId="rowDeliveryDetailId${loop.count}"
												property="to.rowDeliveryDetailId"
												value='${drsDtls.deliveryDetailId}' />
												<input  type="hidden" name="to.rowDeliveryStatus" id="rowDeliveryStatus${loop.count}" value="${drsDtls.deliveryStatus}"/>
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
			<!-- Button -->
			<jsp:include page="/pages/drs/update/drsUpdateButtonContainer.jsp" />
			<!-- Button ends -->
			<!-- main content ends -->
		</div>
		<!--wraper ends-->
	</html:form>
</body>
</html>
