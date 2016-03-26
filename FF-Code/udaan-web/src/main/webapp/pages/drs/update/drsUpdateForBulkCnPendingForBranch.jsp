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
<script type="text/javascript" src="js/drs/update/drsUpdateForBulkCnPendingForBranch.js"></script>

<!-- DataGrids /-->
</head>
<body>
<html:form method="post" styleId="bulkCnPendingDrsForm">

	<!--wraper-->
	<div id="wraper">

		<!-- main content -->
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<h1>
						Bulk DRS update for Pending consignments for Branch
					</h1>
					<div class="mandatoryMsgf">
						<span class="mandatoryf">*</span> <bean:message key="label.cashbooking.mandatoryField" />
					</div>
				</div>
				<!--  Header START-->
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup> <bean:message
										key="label.drs.drs" /> <bean:message key="label.common.date" />
									<strong><bean:message key="label.common.slash" /></strong> <bean:message
										key="label.common.time" />
									<bean:message key="label.common.colon" /></td>
								<td width="19%"><html:text styleId="drsDateTimeStr"
										property="to.drsDateTimeStr" styleClass="txtbox width145"
										readonly="true" tabindex="-1" /></td>
								<td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.drs.drs" />#
									<bean:message key="label.common.colon" /></td>
								<td width="25%"><html:text styleId="drsNumber"
										property="to.drsNumber" styleClass="txtbox width120" size="16"
										maxlength="14"
										onkeydown="return findOnEnterKey(event.keyCode);" /> <html:button
										styleClass="btnintgrid" styleId="Find" property="FIND"
										alt="DRS number Search" onclick="find()">
										<bean:message key="button.label.search" />
									</html:button></td>
								<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.common.office" /> <bean:message
										key="label.common.code" />
									<bean:message key="label.common.colon" /></td>
								<td width="15%"><html:text styleId="drsOfficeName"
										property="to.drsOfficeName" styleClass="txtbox width145"
										readonly="true" tabindex="-1" /></td>
							</tr>
							<tr>
								
								
								<td class="lable" ><sup class="star">*</sup>&nbsp;DRS Type <bean:message
												key="label.common.colon" /></td>
										<td width="21%"><html:select
												property="to.ypDrs" styleId="ypDrs"
												styleClass="selectBox width100"
												onkeydown="return enterKeyNav('pendingReasonForBulkCn',event.keyCode);"
												onchange="clearGridForYpDrs()">
												<logic:present name="drsLoadTypeMap" scope="request">
													<html:optionsCollection name="drsLoadTypeMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
								
								<td class="lable"><sup class="star">*</sup>&nbsp;
								
								<bean:message key="label.drs.update.reason" /></td>
								<td>
								
								<html:select property="to.pendingReasonForBulkCn"
										styleId="pendingReasonForBulkCn" onkeydown="return focusOnFirstRowConsignment(event.keyCode);"
										styleClass="selectBox width140">
										<option value="">SELECT</option>
										<logic:present name="nonDlvReasonMapBlk" scope="request">
											<html:optionsCollection name="nonDlvReasonMapBlk"
												label="value" value="key" />
										</logic:present>
									</html:select>



								</td>
								<td></td>
							</tr>
							<tr>
							
							
								<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.drs.fs.out" />&nbsp;<bean:message
										key="label.drs.fs.date" />/<bean:message
										key="label.common.time" /></td>
								<td><html:text styleId="fsOutTimeDateStr"
										property="to.fsOutTimeDateStr" styleClass="txtbox width110"
										readonly="true" tabindex="-1" /> </td>

								
										<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.drs.fs.in" />&nbsp;<bean:message
												key="label.drs.fs.date" />/<bean:message
												key="label.common.time" /></td>
										<td><html:text styleId="fsInTimeDateStr"
												property="to.fsInTimeDateStr" styleClass="txtbox width110"
												readonly="true" />  </td>
<td/><td/>
									
								
							</tr>
						</table>
					</div>

					<!--  Header END-->

				<div id="demo">
					<div class="title">
						<div class="title2"><bean:message key="label.drs.prep.npdox.details" /></div>
					</div>
					
					<table cellpadding="0" cellspacing="0" border="0" class="display"
						id="drsUpdate" width="100%">
						<thead>
							<tr>
								<th width="2%" align="center"><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('chkbx',this.checked);" tabindex="-1"/></th>
								<th width="3%" align="center"><bean:message key="label.common.serialNo" /></th>
								<th width="7%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.consgNo" /></th>
								<th width="7%"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.prep.Origin" /> </th>
							
								<th width="10%"><bean:message key="label.drs.update.remarks" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="drsDtls" items="${bulkCnPendingDrsForm.to.drsDetailsTo}" varStatus="loop">
									<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
										<td><input type="checkbox" name="chkbx"
											value='${loop.count+1}' id="checkbox${loop.count}"
											tabindex="-1" disabled="disabled" /></td>
										<td><label><c:out value="${drsDtls.rowNumber}" />
										</label></td>
										<td><html:text
												styleId="rowConsignmentNumber${loop.count}"
												property="to.rowConsignmentNumber"
												styleClass="txtbox width140"
												value='${drsDtls.consignmentNumber}' readonly="true"
												tabindex="-1" maxlength="12" /></td>
										<td align="center"><html:text
												styleId="rowOriginCityName${loop.count}"
												property="to.rowOriginCityName"
												value="${drsDtls.originCityName}"
												styleClass="txtbox width140" readonly="true" /> <html:hidden
												styleId="rowOriginCityId${loop.count}"
												property="to.rowOriginCityId"
												value="${drsDtls.originCityId}" /> <html:hidden
												styleId="rowOriginCityCode${loop.count}"
												property="to.rowOriginCityCode"
												value="${drsDtls.originCityCode}" /> <html:hidden
												styleId="rowConsignmentId${loop.count}"
												property="to.rowConsignmentId"
												value="${drsDtls.consgnmentId}" /></td>


										<td><html:text styleId="rowRemarks${loop.count}"
												property="to.rowRemarks" styleClass="txtbox width100"
												value='${drsDtls.remarks}' readonly="true" tabindex="-1"
												maxlength="30" /> <html:hidden
												styleId="rowDeliveryDetailId${loop.count}"
												property="to.rowDeliveryDetailId"
												value='${drsDtls.deliveryDetailId}' /> <html:hidden
												styleId="rowAlreadyAddedRow${loop.count}"
												property="to.rowAlreadyAddedRow"
												value='${drsDtls.alreadyAddedRow}' /></td>
									</tr>
								</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- Grid /-->
			</div>
			 <!--hidden fields start from here --> 
           <jsp:include page="/pages/drs/drsCommon.jsp" />
           <html:hidden property="to.consignmentType" />
           
            <html:hidden property="to.drsPartyId" />
            <html:hidden property="to.drsFor" />
            <!--hidden fields ENDs  here --> 
		</div>
		<!-- Button -->
		<div class="button_containerform">
			<!-- Button -->
			<html:button property="Save" styleClass="btnintform" styleId="Save" title="Save">
   			<bean:message key="button.label.save" /></html:button>
   			<html:button property="Cancel" styleClass="btnintform" styleId="Cancel" title="Clear Screen">
   			<bean:message key="button.label.Cancel" /></html:button>
			<!-- Button ends -->
			<html:button property="Delete" styleClass="btnintform"
				styleId="Delete" title="Delete">
				<bean:message key="button.label.delete" />
			</html:button>
		</div>
		<!-- Button ends -->
		<!-- main content ends -->
	</div>
	<!--wraper ends-->
	</html:form>
</body>
</html>
