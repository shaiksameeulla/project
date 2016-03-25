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
<script type="text/javascript" src="js/stockmanagement/stockIssue.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript"
	src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		var oTable = $('#issueGrid').dataTable( {
			"sScrollY": "120",
			"sScrollX": "100%",
			"sScrollXInner":"175%",
			"bScrollCollapse": false,
			"bSort": false,
			"bInfo": false,
			"bPaginate": false,
			"sPaginationType": "full_numbers"
		} );
		new FixedColumns( oTable, {
			"sLeftWidth": 'relative',
			"iLeftColumns": 0,
			"iRightColumns": 0,
			"iLeftWidth": 0,
			"iRightWidth": 0
		} );
		issueStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'stockIssueForm');
		}
	} );
</script>
<!-- DataGrids /-->
<!-- Add Row To Table -->

<!-- Add Row To Table /-->
</head>
<body>
	<!--wraper-->
	<html:form method="post" styleId="stockIssueForm">
		<div id="wraper">
			<!-- main content -->
			<div class="clear"></div>
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="stock.label.issue.title" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="stock.label.issue.mandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="4" width="100%">
							<tr>
								<td width="12%" class="lable"><bean:message
										key="stock.label.issue.issueddate" /> <strong>/</strong> <bean:message
										key="stock.label.issue.time" />:</td>
								<td width="11%"><html:text property="to.issueDateStr"
										styleId="issueDateStr" styleClass="txtbox width145" size="18"
										readonly="true" tabindex="-1" /></td>
								<td width="11%" class="lable"><bean:message
										key="stock.label.issue.requisitionNum" />:</td>
								<td width="20%"><html:text property="to.requisitionNumber"
										styleId="requisitionNumber" styleClass="txtbox width140"
										onkeydown="return findOnEnterKey('req',event.keyCode);"
										size="14" maxlength="12" /> <!-- <a href="#" id="requisitionSearch" onclick="find('req');"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
									<%-- <html:button styleClass="searchButton" property="FIND" alt="Search" onclick="find('req');" styleId="requisitionSearch"/> --%>
									<html:button styleClass="btnintgrid" property="FIND"
										alt="Search" onclick="find('req');"
										styleId="requisitionSearch">
										<bean:message key="button.search" />
									</html:button></td>
								<td width="8%" class="lable"><bean:message
										key="stock.label.issue.num" />:</td>
								<td width="20%"><html:text property="to.stockIssueNumber"
										styleId="stockIssueNumber" styleClass="txtbox width140"
										maxlength="12" size="14"
										onkeydown="return findOnEnterKey('issue',event.keyCode);" />
									<!-- <a href="#" id="issueSearch" onclick="find('issue');"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
									<%-- <html:button styleClass="searchButton" property="FIND" alt="Search" onclick="find('issue');" styleId="issueSearch"/> --%>
									<html:button styleClass="btnintgrid" property="FIND"
										alt="Search" onclick="find('issue');" styleId="issueSearch">
										<bean:message key="button.search" />
									</html:button></td>
							</tr>
							<tr>
								<td width="12%" class="lable"><sup class="star">*</sup>
								<bean:message key="stock.label.issue.to" />:</td>
								<td width="10%"><html:select property="to.issuedToType"
										styleId="issuedToType" styleClass="selectBox width150"
										onchange="validateIssuedTO(this)"
										onkeydown="return enterKeyNav('recipientId' ,event.keyCode)">
										<html:option value="">
											<bean:message key="stock.label.select" />
										</html:option>
										<logic:present name="issuedToMap" scope="request">
											<html:optionsCollection name="issuedToMap" label="value"
												value="key" />
										</logic:present>
									</html:select></td>
								<td width="11%" class="lable"><sup class="star">*</sup>
								<bean:message key="stock.label.issue.code" /> &amp; <bean:message
										key="stock.label.issue.name" />:</td>
								<td width="17%" colspan="3"><html:select
										property="to.recipientId" styleClass="selectBox width170"
										styleId="recipientId"
										onkeydown="return focusOnFirstRow(event.keyCode,'to.rowItemTypeId','rowItemTypeId');"
										onchange="setShippedToCodeForBaFr(this)">
										<logic:notPresent name="issuedToTypeDtls" scope="request">
											<html:option value="">
												<bean:message key="stock.label.select" />
											</html:option>
										</logic:notPresent>
										<logic:present name="issuedToTypeDtls" scope="request">
											<html:optionsCollection name="issuedToTypeDtls" label="value"
												value="key" />
										</logic:present>
									</html:select></td>

							</tr>
						</table>
					</div>

					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="stock.label.issue.stockDetails" />
							</div>
						</div>

						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="issueGrid" width="100%">
							<thead>
								<tr>
									<th width="1%" align="center"><input type="checkbox"
										name="chkAll" id="chkAll"
										onclick="return checkAllBoxes('to.checkbox',this.checked);"
										tabindex="-1" /></th>
									<th width="1%" align="center"><bean:message
											key="stock.label.issue.SrNo" /></th>
									<th width="5%" align="center"><sup class="star">*</sup>
									<bean:message key="stock.label.issue.materialType" /></th>
									<th width="6%" align="center"><sup class="star">*</sup>
									<bean:message key="stock.label.issue.materialCode" /></th>
									<th width="5%" align="center"><bean:message
											key="stock.label.issue.description" /></th>
									<th width="3%"><bean:message key="stock.label.issue.uom" /></th>
									<th width="3%"><bean:message
											key="stock.label.issue.requestedQty" /></th>
									<th width="3%"><bean:message
											key="stock.label.issue.approvedQty" /></th>
									<th width="3%"><sup class="star">*</sup>
									<bean:message key="stock.label.issue.issuedQty" /></th>
									<th width="5%"><bean:message
											key="stock.label.issue.startNum" /></th>
									<th width="5%"><bean:message
											key="stock.label.issue.endNum" /></th>
									<th width="5%"><bean:message
											key="stock.label.issue.unitrate" /></th>
									<th width="5%"><bean:message
											key="stock.label.issue.amount" /></th>
									<th width="5%"><bean:message
											key="stock.label.issue.remarks" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="itemDtls"
									items="${stockIssueForm.to.issueItemDetls}" varStatus="loop">
									<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}">
										<!-- class="gradeA" -->
										<td><html:checkbox property="to.checkbox"
												value="${loop.count-1}" styleId="checkbox${loop.count}" /></td>
										<td><label><c:out value="${loop.count}" /></label></td>
										<td class="center"><html:select
												property="to.rowItemTypeId"
												styleId="rowItemTypeId${loop.count}"
												styleClass="selectBox width170"
												onchange="getItemList(this);clearSeriesForIssue(${loop.count})"
												tabindex="-1">
												<html:option value="">
													<bean:message key="stock.label.select" />
												</html:option>

												<logic:present name="itemDtls" property="itemType">
													<logic:iterate id="itype" name="itemDtls"
														property="itemType">
														<c:choose>
															<c:when test="${itype.key==itemDtls.itemTypeId}">
																<option value="${itype.key}" selected="selected">
																	<c:out value='${itype.value}' />
																</option>
															</c:when>
															<c:otherwise>
																<option value="${itype.key}">
																	<c:out value='${itype.value}' />
																</option>
															</c:otherwise>
														</c:choose>
													</logic:iterate>
												</logic:present>

											</html:select></td>
										<td class="center"><html:select property="to.rowItemId"
												styleId="rowItemId${loop.count}"
												styleClass="selectBox width170"
												onchange="clearSeriesForIssue(${loop.count})" tabindex="-1">
												<html:option value="">
													<bean:message key="stock.label.select" />
												</html:option>

												<logic:present name="itemDtls" property="item">
													<logic:iterate id="item1" name="itemDtls" property="item">
														<c:choose>
															<c:when test="${item1.key==itemDtls.itemId}">
																<option value="${item1.key}" selected="selected">
																	<c:out value='${item1.value}' />
																</option>
															</c:when>
															<c:otherwise>
																<option value="${item1.key}">
																	<c:out value='${item1.value}' />
																</option>
															</c:otherwise>
														</c:choose>
													</logic:iterate>
												</logic:present>

											</html:select></td>
										<td><html:text styleId="rowDescription${loop.count}"
												property="to.rowDescription" value="${itemDtls.description}"
												readonly="true" styleClass="txtbox width145"
												style="width: 80px" size="11" tabindex="-1" /></td>
										<td><html:text styleId="rowUom${loop.count}"
												property="to.rowUom" value="${itemDtls.uom}" readonly="true"
												styleClass="txtbox width100" size="11" tabindex="-1" /></td>

										<td><html:text
												styleId="rowRequestedQuantity${loop.count}"
												property="to.rowRequestedQuantity"
												value="${itemDtls.requestedQuantity}"
												styleClass="txtbox width30" size="11" readonly="true"
												maxlength="6" onkeypress="return onlyNumeric(event)"
												style="width: 80px" tabindex="-1" /></td>
										<td><html:text styleId="rowApprovedQuantity${loop.count}"
												property="to.rowApprovedQuantity"
												value="${itemDtls.approvedQuantity}"
												styleClass="txtbox width30" size="11" readonly="true"
												maxlength="6" onkeypress="return onlyNumeric(event)"
												style="width: 80px" tabindex="-1" /></td>
										<td><html:text styleId="rowIssuedQuantity${loop.count}"
												property="to.rowIssuedQuantity"
												value="${itemDtls.issuedQuantity}"
												styleClass="txtbox width30" size="11" maxlength="6"
												onkeypress="return onlyNumeric(event)"
												onchange="checkStockQnatity(this);seriesValidationWrapper('${loop.count}')"
												onkeydown="return enterKeyNav('rowStartSerialNumber${loop.count}' ,event.keyCode);"
												style="width: 80px" /></td>

										<td><html:text
												styleId="rowStartSerialNumber${loop.count}"
												property="to.rowStartSerialNumber"
												value="${itemDtls.startSerialNumber}"
												styleClass="txtbox width110" onchange="validateSeries(this)"
												maxlength="12"
												onkeydown="return enterKeyNav('rowRemarks${loop.count}' ,event.keyCode);" /></td>
										<td><html:text styleId="rowEndSerialNumber${loop.count}"
												property="to.rowEndSerialNumber"
												value="${itemDtls.endSerialNumber}"
												styleClass="txtbox width110" readonly="true" tabindex="-1"
												maxlength="12" /></td>

										<td><html:text
												styleId="rowRatePerUnitQuantity${loop.count}"
												property="to.rowRatePerUnitQuantity"
												value="${itemDtls.ratePerUnitQuantity}"
												styleClass="txtbox width110" readonly="true" tabindex="-1"
												maxlength="12" /></td>
										<td><html:text styleId="rowTotalPrice${loop.count}"
												property="to.rowTotalPrice" value="${itemDtls.itemPrice}"
												styleClass="txtbox width110" readonly="true" tabindex="-1"
												maxlength="12" /></td>
										<td><html:text styleId="rowRemarks${loop.count}"
												property="to.rowRemarks" value="${itemDtls.remarks}"
												styleClass="txtbox width145" maxlength="20"
												style="width: 80px"
												onkeydown="focusOnNextRowOnEnter(event.keyCode,this,'issueGrid','rowRemarks','rowIssuedQuantity')" />
											<html:hidden property="to.rowStockIssueItemDtlsId"
												styleId="rowStockIssueItemDtlsId${loop.count}"
												value='${itemDtls.stockIssueItemDtlsId}' /> <html:hidden
												property="to.rowBalanceQuantity"
												styleId="rowBalanceIssueQuantity${loop.count}"
												value='${itemDtls.balanceQuantity}' /> <html:hidden
												property="to.rowSeries" styleId="rowSeries${loop.count}"
												value='${itemDtls.series}' /> <html:hidden
												property="to.rowSeriesLength"
												styleId="rowSeriesLength${loop.count}"
												value='${itemDtls.seriesLength}' /> <html:hidden
												property="to.rowIsItemHasSeries"
												styleId="rowIsItemHasSeries${loop.count}"
												value='${itemDtls.isItemHasSeries}' /> <html:hidden
												property="to.rowStockItemDtlsId"
												styleId="rowStockItemDtlsId${loop.count}"
												value='${itemDtls.stockItemDtlsId}' /> <html:hidden
												property="to.rowCurrentStockQuantity"
												styleId="rowCurrentStockQuantity${loop.count}"
												value='${itemDtls.currentStockQuantity}' /> <html:hidden
												property="to.rowSeriesType"
												styleId="rowSeriesType${loop.count}"
												value='${itemDtls.seriesType}' /> <html:hidden
												property="to.rowOfficeProduct"
												styleId="rowOfficeProduct${loop.count}"
												value='${itemDtls.officeProductCodeInSeries}' /> <html:hidden
												property="to.rowStartLeaf"
												styleId="rowStartLeaf${loop.count}"
												value='${itemDtls.startLeaf}' /> <html:hidden
												property="to.rowEndLeaf" styleId="rowEndLeaf${loop.count}"
												value='${itemDtls.endLeaf}' /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<br />
					<!-- Grid /-->
					<div class="formbox" id="paymntTitle">
						<h1>
							<bean:message key="stock.label.payment.title" />
						</h1>
						<div>
							<center></center>
						</div>
					</div>
					<div class="formTable" id="paymntDtls">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="11%" class="lable"><bean:message
										key="stock.label.payment.topayamount" /></td>
								<td width="16%"><html:text
										property="to.paymentTO.totalToPayAmount"
										styleId="totalToPayAmount" styleClass="txtbox width145"
										size="11" maxlength="20" readonly="true" tabindex="-1" /></td>
								<td width="11%" class="lable"><bean:message
										key="stock.label.issue.paymentmode" /></td>
								<td width="16%"><html:select
										property="to.paymentTO.paymentMode" styleId="paymentMode"
										styleClass="selectBox width150" onchange="validatePayment()">
										<html:option value="">
											<bean:message key="stock.label.select" />
										</html:option>
										<logic:present name="issuePaymntMap" scope="request">
											<html:optionsCollection name="issuePaymntMap" label="value"
												value="key" />
										</logic:present>
									</html:select></td>
							</tr>
							<tr>
								<td width="11%" class="lable"><sup class="star">*</sup> <bean:message
										key="stock.label.payment.chequeDate" />:</td>
								<td width="16%"><html:text
										property="to.paymentTO.paymentDateStr" styleId="chequeDate"
										styleClass="txtbox width85" size="13" readonly="true"
										onblur="isFutureDateForIssue(this,'chequeNum')"
										onkeypress="enterKeyNav('chequeNum',event.keyCode);"
										value="${stockIssueForm.to.paymentTO.paymentDateStr}" /> <a
									href='javascript:show_calendar("chequeDate", this.value)'
									id="calImg"> <img src="images/calender.gif"
										alt="Select Date" width="16" height="16" border="0" /></a></td>
								<td width="14%" class="lable"><sup class="star">*</sup>
								<bean:message key="stock.label.payment.chequeNum" />:</td>
								<td width="17%"><html:text
										property="to.paymentTO.chequeNumber" styleId="chequeNum"
										styleClass="txtbox width145" size="11" maxlength="6"
										onkeypress="enterKeyNav('bankName',event.keyCode);return onlyNumeric(event);" /></td>
								<td width="10%" class="lable"><sup class="star">*</sup><span
									class="lable"><bean:message
											key="stock.label.payment.bank" />:</span></td>
								<td><html:text property="to.paymentTO.bankName"
										styleId="bankName" styleClass="txtbox width145" size="11"
										maxlength="20"
										onkeypress="enterKeyNav('branch',event.keyCode);" /></td>
							</tr>
							<tr>
								<td width="11%" class="lable">&nbsp;</td>
								<td width="10%">&nbsp;</td>
								<td width="10%" class="lable"><sup class="star">*</sup>
								<bean:message key="stock.label.payment.branch" />:</td>
								<td><html:text property="to.paymentTO.branch"
										styleId="branch" styleClass="txtbox width145" size="11"
										maxlength="20"
										onkeypress="enterKeyNav('amountReceived',event.keyCode);" /></td>
								<td width="14%" class="lable"><sup class="star">*</sup>
								<bean:message key="stock.label.payment.amount" />:</td>
								<td width="17%"><html:text
										property="to.paymentTO.amountReceived"
										styleId="amountReceived" styleClass="txtbox width75" size="11"
										maxlength="8"
										onkeypress="enterKeyNav('Save',event.keyCode);return onlyDecimal(event)"
										onchange="validateAmount()" /></td>


							</tr>
						</table>
					</div>
				</div>

				<!--hidden fields start from here -->
				<html:hidden property="to.stockIssueId" styleId="stockIssueId" />
				<html:hidden property="to.paymentTO.stockPaymentId"
					styleId="stockPaymentId" />
				<html:hidden property="to.loggedInUserId" styleId="loggedInUserId" />
				<html:hidden property="to.createdByUserId" styleId="createdByUserId" />
				<html:hidden property="to.updatedByUserId" styleId="updatedByUserId" />
				<html:hidden property="to.loggedInOfficeId"
					styleId="loggedInOfficeId" />
				<html:hidden property="to.issueOfficeId" styleId="issueOfficeId" />
				<%--  <html:hidden property="to.recipientId" styleId="recipientId"/> --%>
				<html:hidden property="to.recipientCode" styleId="recipientCode" />
				<html:hidden property="to.recipientName" styleId="recipientName" />

				<html:hidden property="to.transactionFromType"
					styleId="transactionFromType" />

				<html:hidden property="to.loggedInOfficeCode"
					styleId="loggedInOfficeCode" />
				<html:hidden property="to.noSeries" styleId="noSeries" />
				<html:hidden property="to.normalCnoteIdentifier"
					styleId="normalCnoteIdentifier" />

				<html:hidden property="to.issuedToBranch" styleId="issuedToBranch" />
				<html:hidden property="to.issuedToBa" styleId="issuedToBa" />
				<html:hidden property="to.issuedToFr" styleId="issuedToFr" />
				<html:hidden property="to.issuedToEmp" styleId="issuedToEmp" />
				<html:hidden property="to.issuedToCustomer"
					styleId="issuedToCustomer" />

				<html:hidden property="to.transactionIssueType"
					styleId="transactionIssueType" />
				<html:hidden property="to.transactionPRType"
					styleId="transactionPRType" />


				<html:hidden property="to.baAllowedSeries" styleId="baAllowedSeries" />
				<html:hidden property="to.empAllowedSeries"
					styleId="empAllowedSeries" />
				<html:hidden property="to.creditCustAllowedSeries"
					styleId="creditCustAllowedSeries" />
				<html:hidden property="to.accCustAllowedSeries"
					styleId="accCustAllowedSeries" />
				<html:hidden property="to.franchiseeAllowedSeries"
					styleId="franchiseeAllowedSeries" />

				<html:hidden property="to.regionCode" styleId="regionCode" />

				<html:hidden property="to.consignmentType" styleId="consignmentType" />
				<html:hidden property="to.ogmStickers" styleId="ogmStickers" />
				<html:hidden property="to.bplStickers" styleId="bplStickers" />
				<html:hidden property="to.comailNumber" styleId="comailNumber" />
				<html:hidden property="to.bagLocNumber" styleId="bagLocNumber" />
				<html:hidden property="to.mbplStickers" styleId="mbplStickers" />
				<html:hidden property="to.cityCode" styleId="cityCode" />


				<html:hidden property="to.canUpdate" styleId="canUpdate" />
				<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode" />
				<html:hidden property="to.todayDate" styleId="todayDate" />

				<html:hidden property="to.shippedToCode" styleId="shippedToCode" />

				<!--  For Tax Components START -->
				<html:hidden property="to.paymentTO.isForPanIndia"
					styleId="isForPanIndia" />
				<html:hidden property="to.paymentTO.serviceTax" styleId="serviceTax" />
				<html:hidden property="to.paymentTO.serviceTaxAmount"
					styleId="serviceTaxAmount" />
				<html:hidden property="to.paymentTO.eduCessTax" styleId="eduCessTax" />
				<html:hidden property="to.paymentTO.eduCessTaxAmount"
					styleId="eduCessTaxAmount" />
				<html:hidden property="to.paymentTO.heduCessTax"
					styleId="heduCessTax" />
				<html:hidden property="to.paymentTO.heduCessTaxAmount"
					styleId="heduCessTaxAmount" />
				<html:hidden property="to.paymentTO.stateTax" styleId="stateTax" />
				<html:hidden property="to.paymentTO.stateTaxAmount"
					styleId="stateTaxAmount" />
				<html:hidden property="to.paymentTO.surChrgeStateTax"
					styleId="surChrgeStateTax" />
				<html:hidden property="to.paymentTO.surChrgeStateTaxAmount"
					styleId="surChrgeStateTaxAmount" />
				<html:hidden property="to.paymentTO.totalTaxPerQuantityPerRupe"
					styleId="totalTaxPerQuantityPerRupe" />

				<!--  For Tax Components END -->
				<html:hidden property="to.paymentCash" styleId="paymentCash" />
				<html:hidden property="to.paymentDd" styleId="paymentDd" />
				<html:hidden property="to.paymentChq" styleId="paymentChque" />
				<!--hidden fields ENDs  here -->

			</div>
			<!-- Button -->
			<div class="button_containerform">


				<c:choose>
					<c:when
						test="${not empty stockIssueForm.to.stockIssueId && stockIssueForm.to.stockIssueId>0}">
						<%-- <html:button property="Modify" styleClass="btnintform" onclick="save('Update');">
   			<bean:message key="button.modify" /></html:button> --%>
					</c:when>
					<c:otherwise>
						<html:button property="Save" styleId="Save"
							styleClass="btnintform" onclick="save('Save');">
							<bean:message key="button.save" />
						</html:button>
						<%-- <html:button property="Add" styleClass="btnintform" onclick="isValidForAddNewRow();" >
			<bean:message key="button.add" /></html:button> --%>
					</c:otherwise>
				</c:choose>

				<html:button property="Print" styleClass="btnintformbigdis"
					onclick="printStock()" disabled="true" styleId="Print">
					<bean:message key="button.print" />
				</html:button>
				<html:button property="Cancel" styleId="Cancel"
					styleClass="btnintform" onclick="clearScreen('stockIssue')">
					<bean:message key="button.cancel" />
				</html:button>


			</div>
			<!-- Button ends -->
			<!-- main content ends -->

		</div>
		<!--wraper ends-->
	</html:form>
</body>
</html>
