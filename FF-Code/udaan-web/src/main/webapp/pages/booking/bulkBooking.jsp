<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/bulkBooking.js"></script>
<script type="text/javascript">
	function setDefaultvalues() {
		var flag = '${isError}';
		var trasnStatus = '${trasnStatus}';
		var fileName = '${fileName}';
		var jobNumber = '${jobNumber}';
		if (!isNull(jobNumber)) {
			//alert("Job has initiated. please note the job number "+jobNumber);
			window.location = "./jobService.do?submitName=jobServiceView&jobNumber="
					+ jobNumber;

		}
		if (!isNull(trasnStatus)) {
			alert(trasnStatus);
		}
		if (flag == 'Y') {
			getErrorList(fileName);
		}
		jQuery.unblockUI();
	}
</script>
<!-- DataGrids /-->
</head>
<body onload="setDefaultvalues();">
	<!--wraper-->
	<div id="wraper">
		<html:form action="/bulkBooking.do" method="post"
			styleId="creditCustomerBookingParcelForm"
			enctype="multipart/form-data">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.bulkBooking.bulkBooking" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<html:hidden property="to.cnCount" styleId="cnCount" />
							<html:hidden property="to.bookingOfficeId"
								styleId="bookingOfficeId" value="${originOfficeId}" />
							<html:hidden property="to.bookingOffCode"
								styleId="bookingOfficeCode" value="${originOfficeCode}" />
							<html:hidden property="to.customerCodeSingle"
								styleId="customerCode" />
							<html:hidden property="to.createdBy" styleId="createdBy"
								value="${createdBy}" />
							<html:hidden property="to.updatedBy" styleId="updatedBy"
								value="${updatedBy}" />
							<tr>
								<td width="17%" class="lable"><bean:message
										key="label.bulkBooking.dateTime" /></td>
								<td width="30%"><html:text property="to.bookingDate"
										styleClass="txtbox width175" styleId="dlvDateTime"
										readonly="true" value="${todaysDate}" /></td>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.docType" /></td>
								<td><select name="to.consgTypeName" id="docType"
									onkeypress="return callEnterKey(event, document.getElementById('custCode'));">
										<option value="">---Select---</option>
										<c:forEach var="consg" items="${consgTypes}" varStatus="loop">
											<option
												value="${consg.consignmentId}#${consg.consignmentCode}">
												<c:out value="${consg.consignmentName}" />
											</option>
										</c:forEach>
								</select></td>

								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.customerCode" /></td>
								<td width="30%"><html:select property="to.customerId"
										styleId="custCode" onchange="validateConsgAndCust(this)"
										styleClass="selectBox width145"
										onkeypress="return callEnterKey(event, document.getElementById('stdType'));">
										<html:option value="">
											<c:out value="---Select---" />
										</html:option>
										<logic:present name="customerMap" scope="request">
											<html:optionsCollection name="customerMap" label="value"
												value="key" />
										</logic:present>
									</html:select></td>

							</tr>
							<tr>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.consigType" /></td>
								<td width="35%"><html:select property="to.consgStickerType"
										styleId="stdType" onchange="checkConsgType()"
										onkeypress="return enterKeyForProductCode(event);">
										<html:option value="">---Select---</html:option>
										<c:forEach var="stdTypes" items="${standardTypes}"
											varStatus="loop">
											<html:option value="${stdTypes.stdTypeCode}">
												<c:out value="${stdTypes.description}" />
											</html:option>
										</c:forEach>

									</html:select></td>

								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.product" /></td>
								<td width="35%"><html:select property="to.productCode"
										styleId="productCode"
										onkeypress="return callEnterKey(event, document.getElementById('startConsgNo'));">
										<html:option value="">
											<c:out value="---Select---" />
										</html:option>
										<c:forEach var="product" items="${products}" varStatus="loop">
											<html:option
												value="${product.productId}#${product.consgSeries}">
												<c:out
													value="${product.consgSeries} - (${product.productName})" />
											</html:option>
										</c:forEach>
									</html:select></td>
							</tr>
							<tr>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.cnStartNo" /></td>
								<td width="30%"><html:text property="to.startConsgNo"
										styleId="startConsgNo" styleClass="txtbox width145"
										maxlength="12" size="11" value=""
										onblur="convertDOMObjValueToUpperCase(this);isValidConsFormat(this,'start')"
										onkeypress="return callEnterKey(event, document.getElementById('endConsgNo'));" /></td>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.cnEndNo" /></td>
								<td width="30%"><html:text property="to.endConsgNo"
										styleId="endConsgNo" styleClass="txtbox width145"
										maxlength="12" size="11" value=""
										onblur="convertDOMObjValueToUpperCase(this);isValidConsFormat(this,'end');"
										onkeypress="return callEnterKey(event, document.getElementById('fileUpload'));" /></td>

							</tr>


							<tr>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.bulkBooking.ExcelUpload" /></td>
								<td width="35%" colspan="5"><html:file
										property="to.bulkBookingExcel" styleId="fileUpload"
										styleClass="txtbox"
										onkeypress="return callEnterKey(event, document.getElementById('Save'));" /></td>

							</tr>
						</table>
					</div>
					<!-- Grid /-->
				</div>


			</div>

			<!-- Button -->
			<div class="button_containerform">

				<html:button styleId="Save" styleClass="btnintform"
					onclick="upload()" property="">
					<bean:message key="label.bulkBooking.Submit" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBulkBooking();" styleId="cancel">
					<bean:message key="label.bulkBooking.Cancel" locale="display" />
				</html:button>


				<%-- 				<html:button property="Print" styleClass="btnintform" onclick="" --%>
				<%-- 					styleId="print"> --%>
				<%-- 					<bean:message key="label.bulkBooking.Print" locale="display" /> --%>
				<%-- 				</html:button> --%>
			</div>
			<br />
			<br />
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>

	<!--wraper ends-->
</body>
</html>