<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/manifest/mbplOutManifest.js"></script>
<script type="text/javascript" charset="utf-8">
		var maxManifestAllowed = null;
		var maxWeightAllowed = null;
		var maxTolerenceAllowed = null;
		var MBPL_SERIES = null;
		var BAGLOCK_SERIES='${SERIES_TYPE_BAG_LOCK_NO}';
		var OGM_SERIES=null;
		var BPL_SERIES=null;
		var MANIFEST_STATUS_CLOSE = '${MANIFEST_STATUS_CLOSE}';
		var MANIFEST_STATUS_OPEN = '${MANIFEST_STATUS_OPEN}';
		var OFF_TYPE_REGION_HEAD_OFFICE='${OFF_TYPE_REGION_HEAD_OFFICE}';
		var OFF_TYPE_CODE_HUB ='${OFF_TYPE_CODE_HUB}';
		var OFF_TYPE_CODE_BRANCH ='${OFF_TYPE_CODE_BRANCH}';
		var MANIFEST_TYPE_PURE='${PURE}';
		var MANIFEST_TYPE_TRANSHIPMENT='${TRANS}';
		var wmActualWeight=0.000;
		var wmCapturedWeight = 0.000;
		var newWMWt=0.000;
		var bookingDetail=null;
		var manifestNumber=null;
		var ERROR_FLAG = null;
		var SUCCESS_FLAG = null;
		



		
			$(document).ready( function () {
				var oTable = $('#outManifestTable').dataTable( {
					"sScrollY": "230",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
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
				maxManifestAllowed = document.getElementById("maxManifestAllowed").value;
				maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
				maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;
			} );
			
			function loadDefaultObjects(){
			addMBplOutManifestRows();
			//getWeightFromWeighingMachine();
			MBPL_SERIES='${SERIES_TYPE_MBPL_NO}';
			BPL_SERIES='${SERIES_TYPE_BPL_NO}';
			ERROR_FLAG = '${ERROR_FLAG}';
			SUCCESS_FLAG = '${SUCCESS_FLAG}';

			setDefaultValue();
			
			}
</script>
<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects();">
	<!--wraper-->
	<div id="wraper">
		<html:form action="/mbplOutManifest.do" method="post"
			styleId="mbplOutManifestForm">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>MBPL</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.manifest.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">

						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.dateTime" /></td>
								<td width="19%"><html:text property="to.manifestDate"
										styleClass="txtbox width130" styleId="dateTime" tabindex="-1"
										readonly="true"></html:text></td>

								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.mbplNo" /></td>
								<td width="25%"><html:text property="to.manifestNo"
										styleClass="txtbox width130" styleId="manifestNo"
										maxlength="10"
										onkeypress="return callEnterKey(event, document.getElementById('manifestType'));"
										onblur="convertDOMObjValueToUpperCase(this);isValidManifestNoForHeader(this);"></html:text> <html:button
										property="Search" styleId="Find" styleClass="btnintgrid"
										onclick="searchManifest()">
										<bean:message key="button.label.search" />
									</html:button></td>
								<!--    &nbsp;<a href="#" title="Search"><img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch" onclick="searchManifest();"/></a></td> -->
								<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.originOffice" /></td>
								<td width="15%"><html:text property="to.loginOfficeName"
										styleId="originOffice" styleClass="txtbox width130"
										tabindex="-1" size="25" readonly="true"></html:text></td>
							</tr>
							<tr>
								<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.manifestType" /></td>
								<td width="18%"><html:select property="to.bplManifestType"
										styleClass="selectBox width130" styleId="manifestType"
										onchange="checkGridEmpty(this,manifestType);clearDropDownValues4Header();getDestinationDtlsByBplManifestType();"
										onkeypress="return enterKeyForManifestType(event,this);">
										<option value="">----Select----</option>
										<c:forEach var="manifestTypes"
											items="${stockStandardTypeTOList}" varStatus="loop">

											<option value="${manifestTypes.stdTypeCode}">
												<c:out value="${manifestTypes.description}" />
											</option>

										</c:forEach>
									</html:select></td>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.destinationRegion" /></td>
								<td><html:select property="to.destinationRegionId"
										styleClass="selectBox  width130" styleId="destRegionId"
										onkeypress="return callEnterKey(event, document.getElementById('destCity'));"
										onblur="valiedManifestType();getAllCitiesByRegion();"
										onchange="checkGridEmpty(this,destRegion);clearDropDownValOnDestnRegn4Header();">
										<option value="">----Select----</option>
										<c:forEach var="consgTypes" items="${regionTOs}"
											varStatus="loop">

											<option value="${consgTypes.regionId}">
												<c:out value="${consgTypes.regionName}" />
											</option>

										</c:forEach>
									</html:select></td>
								<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.destinationCity" /></td>
								<td width="15%"><html:select
										property="to.destinationCityId"
										styleClass="selectBox width130" styleId="destCity"
										onkeypress="return callEnterManifest(event);"
										onchange="checkGridEmpty(this,destCity);getAllOffc();clearDropDownValForDeatCity();"
										onblur="getReportingRHOByoffice(this.value);">
										<option value="">----Select----</option>
									</html:select></td>
							</tr>
							<tr>
								<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.officeType" /></td>
								<td width="18%"><html:select property="to.destOfficeType"
										styleClass="selectBox width130" styleId="destOfficeType"
										onkeypress="return callEnterKey(event, document.getElementById('destOffice'));"
										onchange="checkGridEmpty(this,officeType);getAllOffc();">
										<option value="">----Select----</option>
										<c:forEach var="officeType" items="${officeTypeList}"
											varStatus="loop">

											<option
												value="${officeType.offcTypeId}~${officeType.offcTypeCode}">
												<c:out value="${officeType.offcTypeDesc}" />
											</option>

										</c:forEach>
									</html:select></td>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.destinationOffice" /></td>
								<td><html:select property="to.destinationOffcId"
										styleClass="selectBox width130" styleId="destOffice"
										onkeypress="return callEnterKey(event, document.getElementById('bagLockNo'));"
										onchange="getAllOfficeIds(this);validateBranchHubOffice();checkGridEmpty(this,destOffice);">
										<option value="">----Select----</option>
									</html:select></td>
								<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.bagLockNo" /></td>
								<td width="15%"><html:text property="to.bagLockNo"
										styleId="bagLockNo" maxlength="8" styleClass="txtbox width130"
										onkeypress="return callFocusEnterKey(event);"
										onblur="convertDOMObjValueToUpperCase(this);validateBagLockNo(this,'H');"></html:text></td>
							</tr>
							<tr>
								<td width="11%" class="lable"><bean:message
										key="label.manifest.rfid" /></td>
								<td width="14%"><html:text property="to.rfidNo"
										styleId="rfidNo" styleClass="txtbox width130"
										onblur="getRfIdByRfNo(this);" maxlength="25" /></td>
								<td width="20%" class="lable"><bean:message
										key="label.manifest.weight" /></td>
								<td width="21%"><html:text property=""
										styleClass="txtbox width30" readonly="true" tabindex="-1"
										styleId="finalKgs" maxlength="3" value="" />. <html:text
										property="" styleClass="txtbox width30" tabindex="-1"
										readonly="true" styleId="finalGms" maxlength="3" value="" /><span
									class="lable">kgs</span> <html:hidden property="to.finalWeight"
										styleId="finalWeight" value="" /></td>
								<td width="15%" class="lable">&nbsp;</td>
								<td width="15%">&nbsp;</td>
							</tr>
						</table>
					</div>

					<div id="demo">
						<div class="title">
							<div class="title2">Details</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="outManifestTable" width="100%">
							<thead>
								<tr>
									<th width="5%" align="center"><input type="checkbox"
										class="checkbox" id="checkAll" name="type"
										onclick="selectAllCheckBox(maxManifestAllowed.value);"
										tabindex="-1" /></th>
									<th width="17%"><bean:message
											key="label.manifestGrid.serialNo" /></th>
									<th width="6%" align="center"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.manifestGrid.bplNo" /></th>
									<th width="25%"><bean:message
											key="label.manifestGrid.bagType" /></th>
									<th width="30%" align="center"><bean:message
											key="label.manifestGrid.bagLockNo" /></th>
									<th width="22%"><bean:message
											key="label.manifestGrid.weight" /></th>
									<th width="45%"><bean:message
											key="label.manifestGrid.destination" /></th>
									
								</tr>
							</thead>


						</table>
					</div>
					<!-- Grid /-->

					<!-- Hidden Fields Start -->
					<html:hidden property="to.action" styleId="action" />
					<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
					<html:hidden property="to.destinationOfficeId" value="" />
					<html:hidden property="to.regionCode" styleId="regionCode" />
					<html:hidden property="to.manifestIdListAtGrid" value=""
						styleId="manifestIdListAtGrid" />
					<html:hidden property="to.regionId" value="${regionId}"
						styleId="regionId" />
					<html:hidden property="to.manifestStatus" styleId="manifestStatus" />
					<html:hidden styleId="manifestId" property="to.manifestId" value="" />
					<html:hidden property="to.seriesType" value="${seriesType}"
						styleId="seriesType" />
					<html:hidden property="to.maxManifestAllowed"
						styleId="maxManifestAllowed" />
					<html:hidden property="to.maxWeightAllowed"
						styleId="maxWeightAllowed" />
					<html:hidden property="to.maxTolerenceAllowed"
						styleId="maxTolerenceAllowed" />
					<html:hidden property="to.isMulDestination" styleId="isMulDest" />
					<html:hidden property="to.multiDestinations" styleId="multiDest" />
					<html:hidden property="to.bagRFID" styleId="bagRFID" />
					<html:hidden property="to.loginCityCode" styleId="loginCityCode" />
					<html:hidden property="to.loginCityId" styleId="loginCityId" />
					<html:hidden property="to.manifestDirection"
						styleId="manifestDirection" />
					<html:hidden property="to.processCode" value="${processCode}"
						styleId="processCode" />
					<html:hidden property="to.officeCode" styleId="officeCode" />
					<html:hidden property="to.repHubOfficeId" styleId="repHubOfficeId" />
					<input type="hidden" value="${originOfficeTypeCode}"
						id="originOfficeTypeCode" /> <input type="hidden"
						value="${hubOfficeType}" id="hubOfficeType" /> <input
						type="hidden" value="${BranchOfficeType}" id="branchOfficeType" />
					<input type="hidden" value="${originCityId}" id="originCityId" />
					<html:hidden property="to.manifestProcessId"
						styleId="manifestProcessId" />
					<html:hidden property="to.processNo" styleId="manifestProcessNo" />
					<html:hidden property="to.outMnfstDestIds"
						styleId="outMnfstDestIds" />
					<html:hidden property="to.loginOfficeType"
						styleId="loginOfficeType" />
					<html:hidden property="to.loginRepHub" styleId="loginRepHub" />
					<html:hidden styleId="createdBy" property="to.createdBy" />
					<html:hidden property="to.updatedBy" styleId="updatedBy" />
					<!-- Hidden Fields Stop -->

				</div>

			</div>
			<!-- Button -->
			<div class="button_containerform">

				<html:button property="saveBtn" styleClass="btnintform"
					styleId="saveBtn" onclick="saveOrCloseOutManifestMBPL('save');">
					<bean:message key="button.label.save" />
				</html:button>
				<html:button property="deleteBtn" styleClass="btnintform"
					styleId="deleteBtn" onclick="deleteConsgDtlsClientSide();">
					<bean:message key="button.label.delete" />
				</html:button>
				<html:button property="printBtn" styleClass="btnintform"
					styleId="printBtn" onclick="printManifest();">
					<bean:message key="button.label.Print" />
				</html:button>
				<html:button property="closeBtn" styleClass="btnintform"
					styleId="closeBtn" onclick="saveOrCloseOutManifestMBPL('close');">
					<bean:message key="button.label.close" />
				</html:button>
				<html:button property="Cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearPage();">
					<bean:message key="button.label.cancel" />
				</html:button>

			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!--wraper ends-->
	<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000"> </iframe> -->
</body>
</html>


