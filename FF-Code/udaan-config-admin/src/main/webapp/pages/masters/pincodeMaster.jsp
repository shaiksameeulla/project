<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>


<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/masters/pincodeMaster.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var oTable = $('#example').dataTable({
			"sScrollY" : "115",
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"bScrollCollapse" : false,
			"bSort" : false,
			"bInfo" : false,
			"bPaginate" : false,
			"sPaginationType" : "full_numbers"
		});
		new FixedColumns(oTable, {
			"sLeftWidth" : 'relative',
			"iLeftColumns" : 0,
			"iRightColumns" : 0,
			"iLeftWidth" : 0,
			"iRightWidth" : 0
		});
	});
	
	
	function showAlertForPincode() {
		var msg = "${pincodeSaved}";
		var failureMsg = "${pincodeNotSaved}";
		
		
			if (!isNull(msg)) {
				alert(msg);
			} else if (!isNull(failureMsg)) {
				alert(failureMsg);
			}
			
		
			
	}
	
</script>
<!-- DataGrids /-->
</head>

<body onload="showAlertForPincode();">
	<html:form method="post" styleId="pincodemasterForm"
		action="/pincodeMaster.do?submitName=preparePage">
		<div id="wraper">
			<!--wraper-->
			<div id="wraper">
				<!-- main content -->
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<strong><bean:message
									key="label.masterpincode.printHeade" /></strong>
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*&nbsp</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.masterpincode.Pincode" /></td>
								<td width="14%"><html:text property="to.pincodeNo"
										styleClass="txtbox width130" styleId="pincodeNo" maxlength="6"
										readonly="false"
										onkeypress="enterKeyNavigationForPincode(event);"
										onchange="validatePincodeField();"></html:text> <html:hidden
										property="to.pincodeId" styleId="pincodeId"></html:hidden>
									<td width="14%"><html:button property="Search"
											styleId="Find" styleClass="btnintgrid"
											onclick="searchPincode();">
										Search
									</html:button></td>
									<td></td>
									<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.masterpincode.region" /></td>
									<td width="20%"><html:select property="to.regionId"
											styleId="regionList" styleClass="selectBox width130"
											onchange="getStateList();">

											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>

											<c:forEach var="region" items="${regionTo}" varStatus="loop">
												<option value="${region.regionId}">
													<c:out value="${region.regionName}" />
												</option>
											</c:forEach>
										</html:select></td>
									<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.masterpincode.state" /></td>
									<td width="18%"><html:select property="to.stateId"
											styleId="stateList" styleClass="selectBox width130"
											onchange="getCityList();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
										</html:select>
							</tr>
							<!-- <tr>
								<td colspan="7"><br /></td>
							</tr> -->
							<tr>
								<td class="lable" valign="top"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.masterpincode.city" /></td>

								<td valign="top"><span> <html:select
											property="to.cityId" styleId="cityList"
											styleClass="selectBox width170"
											onchange="confirmTheCitySelected();">

											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>

										</html:select>
								</span></td>

								<td class="lable" valign="top"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.masterpincode.location" /></td>
								<td valign="top"><html:text property="to.location"
										styleClass="txtbox width130" styleId="location"
										maxlength="100" readonly="false"></html:text></td>
								<td class="lable" valign="top"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.masterpincode.ServiceableBranch" /></td>

								<td valign="top"><html:select
										property="to.servicablebranch" styleId="branchlist"
										multiple="multiple" styleClass="selectBox width130">
										<%-- <html:option value=""><bean:message key="label.common.select" /></html:option> --%>
									</html:select></td>

								<td class="lable" valign="top">&nbsp;<bean:message
										key="label.masterpincode.Paperworks" /></td>

								<td valign="top"><html:select property="to.paperWorkIds"
										styleClass="selectBox width130" multiple="multiple"
										styleId="paperworklist">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<c:forEach var="paperworkTo" items="${paperWork}"
											varStatus="loop">
											<option value="${paperworkTo.cnPaperWorkId}">
												<c:out value="${paperworkTo.cnPaperWorkName}" />
											</option>
										</c:forEach>

									</html:select></td>

							</tr>

							<tr>
								<td colspan="8">
									<div class="columnuni">
										<div class="columnleftcaller">
											<form method="post" action="index.html">
												<fieldset>
													<legend>
														<sup class="star">*</sup>
														<bean:message key="label.masterpincode.productsServiced" />
													</legend>

													<table border="0" align="left" cellpadding="0"
														cellspacing="2" width="100%">
														<tbody>

															<logic:present name="gropupName" scope="request">
																<c:forEach var="gropupNameList" items="${gropupName}"
																	varStatus="loop">
																	<tr>
																		<td width="2%" align="center"><input
																			type="checkbox" class="checkbox" name="to.groupIds"
																			id="group${gropupNameList.value}"
																			value="${gropupNameList.value}"
																			onclick="disableEnable(this);" /></td>

																		<td class="lable1" colspan="2"><span
																			id="groupId${loop.count}">${gropupNameList.label}</span>

																		</td>

																	</tr>

																	<c:if test="${gropupNameList.value == '3'}">
																		<tr>
																			<td>&nbsp;City:</td>
																			<td class="lable1" colspan="2"><span> <html:select
																						property="to.group3cityList"
																						styleId="group3cityList" multiple="multiple"
																						styleClass="selectBox width170">
																						<html:option value="">
																							<bean:message key="label.common.select" />
																						</html:option>
																						<c:forEach var="city" items="${cityTO}"
																							varStatus="loop">
																							<option value="${city.cityId}">
																								<c:out value="${city.cityName}" />
																							</option>
																						</c:forEach>
																					</html:select>
																			</span></td>
																		</tr>
																		<tr>
																			<td>&nbsp;</td>
																			<td align="right" class="lable1" width="2%"><input
																				type="checkbox" class="checkbox" name="to.group3Ids"
																				id="group3A" value="B" disabled="disabled" /></td>
																			<td class="lable1"><span id="groupId3A">Before
																					14:00</span></td>
																		</tr>
																		<tr>
																			<td>&nbsp;</td>
																			<td align="right" class="lable1"><input
																				type="checkbox" class="checkbox" name="to.group3Ids"
																				id="group3B" value="A" disabled="disabled" /></td>
																			<td class="lable1"><span id="groupId3B">After
																					14:00</span></td>
																		</tr>
																		<tr>
																			<td>&nbsp;</td>
																			<td align="right" class="lable1"><input
																				type="checkbox" class="checkbox" name="to.group3Ids"
																				id="group3C" value="S" disabled="disabled" /></td>
																			<td class="lable1"><span id="groupId3C">Till 48 Hrs</span>
																			</td>
																		</tr>
																	</c:if>

																	<c:if test="${gropupNameList.value == '5'}">

																		<tr>
																			<td>&nbsp;City:</td>
																			<td class="lable1" colspan="2"><span> <html:select
																						property="to.group5cityList"
																						styleId="group5cityList" multiple="multiple"
																						styleClass="selectBox width170">
																						<html:option value="">
																							<bean:message key="label.common.select" />
																						</html:option>
																						<c:forEach var="city" items="${cityTO}"
																							varStatus="loop">
																							<option value="${city.cityId}">
																								<c:out value="${city.cityName}" />
																							</option>
																						</c:forEach>
																					</html:select>
																			</span></td>


																		</tr>


																	</c:if>
														</tbody>

														</c:forEach>
														</logic:present>
													</table>
												</fieldset>
											</form>
										</div>

										<!-- Button ends -->


									</div>
								</td>
							</tr>
						</table>
					</div>

				</div>
			</div>

			<!-- Button -->
			<div class="button_containerform">

				<html:button property="saveBtn" styleClass="btnintform"
					styleId="saveBtn" onclick="savePincodeMaster();">
					<bean:message key="button.save" />
				</html:button>
				<html:button property="newBtn" styleClass="btnintform"
					styleId="activateBtn" onclick="activateDeactivatePincode('A');">
					<bean:message key="button.activate" />
				</html:button>
				<html:button property="newBtn" styleClass="btnintform"
					styleId="deactivateBtn" onclick="activateDeactivatePincode('I');">
					<bean:message key="button.deactivate" />
				</html:button>
				<html:button property="cancelBtn" styleClass="btnintform"
					styleId="cancelBtn" onclick="cancel();">
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
