<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.d
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
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
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/manifest/bplOutManifestDox.js"></script>
<script type="text/javascript" charset="utf-8">
		var maxManifestAllowed = null;
		var maxWeightAllowed = null;
		var maxTolerenceAllowed = null;
		var OGM_SERIES=null;
		var BPL_SERIES=null;
		var MBPL_SERIES = null;
		var BAGLOCK_SERIES= null;
		var MANIFEST_STATUS_CLOSE = null;
		var MANIFEST_STATUS_OPEN = null;
		//Weight related Attributes
		var isWeighingMachineConnected = false;
		var wmActualWeight=0.000;
		var wmCapturedWeight = 0.000;
		var newWMWt=0.000;
		var bookingDetail=null;
		var manifestNumber=null;
		var MANIFEST_TYPE_TRANSHIPMENT= null;
		var OFF_TYPE_CODE_HUB = null;
		var OFF_TYPE_CODE_BRANCH = null;
		var MANIFEST_TYPE_PURE = null;
		var OFF_TYPE_REGION_HEAD_OFFICE=null;
		var ERROR_FLAG = null;
		var SUCCESS_FLAG = null;
		
					$(document).ready( function () {
				var oTable = $('#bplOutManifestDoxTab').dataTable( {
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
				
				MANIFEST_STATUS_CLOSE = '${MANIFEST_STATUS_CLOSE}';
				MANIFEST_STATUS_OPEN = '${MANIFEST_STATUS_OPEN}';
				MANIFEST_TYPE_TRANSHIPMENT='${TRANS}';
				OFF_TYPE_CODE_HUB ='${OFF_TYPE_CODE_HUB}';
				OFF_TYPE_CODE_BRANCH ='${OFF_TYPE_CODE_BRANCH}';
				MANIFEST_TYPE_PURE='${PURE}';
				addBplOutManifestRows();
				BPL_SERIES='${SERIES_TYPE_BPL_NO}';
				OGM_SERIES='${SERIES_TYPE_OGM_STICKERS}';
				BAGLOCK_SERIES= '${SERIES_TYPE_BAG_LOCK_NO}';
				OFF_TYPE_REGION_HEAD_OFFICE='${OFF_TYPE_REGION_HEAD_OFFICE}';
				ERROR_FLAG = '${ERROR_FLAG}';
				SUCCESS_FLAG = '${SUCCESS_FLAG}';
				setDefaultValue();
				//getWeightFromWeighingMachine();
			} );
					
		</script>
<!-- DataGrids /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/bplOutManifestDox.do" method="post"
			styleId="bplOutManifestDoxForm">
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>BPL for Document</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<%--   <td width="20%" class="lable"><bean:message key="label.manifest.bplNo"/></td>
                  <td width="21%"><html:text property="" styleClass="txtbox width130" size="11" styleId="manifestNoSearch" value="" onblur="searchManifestForBPL();"/> 
                     &nbsp;<a href="#" title="Search"><img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch" onclick="searchManifestForBPL();" /></a></td> --%>
							<tr>
								<td width="11%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.dateTime" /></td>
								<td width="18%"><html:text property="to.manifestDate"
										styleClass="txtbox width155" styleId="dateTime"
										readonly="true" /></td>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.bplNo" /></td>
								<td width="25%"><html:text property="to.manifestNo"
										styleClass="txtbox width115" maxlength="10"
										styleId="manifestNo" value=""
										onkeypress="return callEnterKey(event, document.getElementById('manifestType'));"
										onblur="convertDOMObjValueToUpperCase(this);isValidManifestNoForHeader(this);" /> <!--    onchange="isValidManifestNoForHeader(this);"  -->
									&nbsp;<!-- <a href="#" title="Search"><img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a> -->
									<html:button styleClass="btnintgrid" styleId="btnSearch"
										property="Search" alt="Search"
										onclick="searchManifestForBPL();" title="Search">
										<bean:message key="button.label.search" />
									</html:button></td>



								<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.originOffice" /></td>
								<td width="15%"><html:text property="to.loginOfficeName"
										styleClass="txtbox width155" styleId="loginOfficeName"
										readonly="true" size="11" /></td>
							</tr>
							<tr>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.manifestType" /></td>
								<td width="18%"><html:select property="to.bplManifestType"
										styleClass="selectBox width155" styleId="manifestType"
										onkeypress="return enterKeyForManifestType(event,this);"
										onchange="checkGridEmpty(this,manifestType);clearDropDownValues4Header();valiedManifestType();getDestinationDtlsByBplManifestType();">
										<!-- clearDropDownValues4Header(); -->
										<option value="">----Select----</option>
										<c:forEach var="manifestTypes"
											items="${stockStandardTypeTOList}" varStatus="loop">

											<option value="${manifestTypes.stdTypeCode}">
												<c:out value="${manifestTypes.description}" />
											</option>

										</c:forEach>
									</html:select></td>


								<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.destinationRegion" /></td>
								<td><html:select property="to.destinationRegionId"
										styleClass="selectBox width155" styleId="destRegionId"
										onkeypress="return callEnterKey(event, document.getElementById('destCity'));"
										onblur="getAllCitiesByRegion();valiedManifestType();"
										onchange="checkGridEmpty(this,destRegionId);clearDropDownValOnDestnRegn4Header();">
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
										styleClass="selectBox width155" styleId="destCity"
										onkeypress="return callEnterManifest(event);"
										onchange="checkGridEmpty(this,destCity);getAllOffc();getReportingRHOByoffice(this.value);clearDropDownValForDeatCity();">
										<option value="">----Select----</option>
									</html:select></td>
							</tr>
							<tr>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.officeType" /></td>
								<td width="18%"><html:select property="to.destOfficeType"
										styleClass="selectBox width155" styleId="destOfficeType"
										onkeypress="return callEnterKey(event, document.getElementById('destOffice'));"
										onchange="checkGridEmpty(this,officeType);getAllOffc();">
										<!-- getOfficesByCityAndofficeTypeBPL(); -->
										<option value="">----Select----</option>
										<c:forEach var="officeType" items="${officeTypeList}"
											varStatus="loop">

											<option
												value="${officeType.offcTypeId}~${officeType.offcTypeCode}">
												<c:out value="${officeType.offcTypeDesc}" />
											</option>

										</c:forEach>
									</html:select></td>
								<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.destinationOffice" /></td>
								<td><html:select property="to.destinationOffcId"
										styleClass="selectBox width155" styleId="destOffice"
										onkeypress="return callEnterKey(event, document.getElementById('bagLockNo'));"
										onchange="getAllOfficeIdsBPL(this);validateBranchHubOffice();checkGridEmpty(this,destOffice);">
										<option value="">----Select----</option>
										<option value="0">All</option>
									</html:select></td>
								<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.bagLockNo" /></td>
								<td width="15%"><html:text property="to.bagLockNo"
										styleClass="txtbox width130" styleId="bagLockNo" value=""
										maxlength="8" onkeypress="return callEnterKeyOnBagLockNo(event,this);"
										onblur="convertDOMObjValueToUpperCase(this);validateBagLockNo(this,'H');"/></td>
								<!-- onchange="validateBagLockNo(this,'H');"  -->
							</tr>
							<tr>
								<td width="11%" class="lable"><bean:message
										key="label.manifest.rfid" /></td>
								<td width="18%"><html:text property="to.rfidNo"
										styleClass="txtbox width130"  maxlength="25" styleId="rfidNo" value=""
										size="11" onkeypress="return callFocusEnterKey(event);"
										onchange="getRfIdByRfNo(this);" /></td>
								<td width="20%" class="lable"><bean:message
										key="label.manifest.weight" /></td>
								<td width="21%"><html:text property=""
										styleClass="txtbox width50" size="11" styleId="finalKgs"
										maxlength="5" value="" readonly="true"
										onkeypress="return onlyNumeric(event);" /><span
									class="lable">.</span> <html:text property=""
										styleClass="txtbox width30" size="11" styleId="finalGms"
										maxlength="3" value="" readonly="true"
										onkeypress="return onlyNumeric(event);"
										onchange="mergeWeighInGmKg(this);" /><span class="lable">kgs</span></td>
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
							id="bplOutManifestDoxTab" width="100%">
							<!-- bplOutManifestDoxTab -->
							<thead>
								<tr>
									<th width="5%" align="center"><input type="checkbox"
										class="checkbox" id="checkAll"
										onclick="selectAllCheckBox(maxManifestAllowed.value);" /></th>
									<th width="6%" align="center"><bean:message
											key="label.manifestGrid.serialNo" /></th>
									<th width="21%" align="center"><bean:message
											key="label.manifestGrid.manifestNo" /></th>
									<th width="15%"><bean:message
											key="label.manifestGrid.weight" /></th>
									<th width="15%"><bean:message
											key="label.manifestGrid.totalCN" /></th>
									<th width="15%"><bean:message
											key="label.manifestGrid.manifestType" /></th>
									<th width="18%"><bean:message
											key="label.manifestGrid.destination" /></th>
								</tr>
							</thead>

						</table>
					</div>

					<!-- Hidden Fields Start -->
					<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
					<%--  <html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId" /> --%>
					<html:hidden property="to.seriesType" styleId="seriesType" />
					<html:hidden property="to.bagRFID" styleId="bagRFID" value="" />
					<html:hidden property="to.finalWeight" styleId="finalWeight"
						value="" />
					<html:hidden property="to.regionCode" styleId="regionCode" />
					<html:hidden property="to.regionId" value="${regionId}"
						styleId="regionId" />
					<html:hidden property="to.manifestIdListAtGrid" value=""
						styleId="manifestIdListAtGrid" />
					<html:hidden property="to.manifestStatus" styleId="manifestStatus" />
					<html:hidden property="to.maxCNsAllowed"
						styleId="maxManifestAllowed" />
					<html:hidden property="to.maxWeightAllowed"
						styleId="maxWeightAllowed" />
					<html:hidden property="to.maxTolerenceAllowed"
						styleId="maxTolerenceAllowed" />
					<html:hidden styleId="manifestId" property="to.manifestId" value="" />
					<html:hidden property="to.loginCityCode" styleId="loginCityCode" />

					<html:hidden property="to.loginCityId" styleId="loginCityId" />
					<html:hidden property="to.isMulDestination" styleId="isMulDest" />
					<html:hidden property="to.multiDestinations" styleId="multiDest" />
					<html:hidden property="to.officeCode" styleId="officeCode" />
					<html:hidden property="to.manifestDirection"
						styleId="manifestDirection" />
					<html:hidden property="to.processCode" value="${processCode}"
						styleId="processCode" />
					<input type="hidden" id="deletedIds" />
					<html:hidden property="to.repHubOfficeId" styleId="repHubOfficeId" />
					<input type="hidden" value="${originOfficeTypeCode}"
						id="originOfficeTypeCode" /> <input type="hidden"
						value="${hubOfficeType}" id="hubOfficeType" /> <input
						type="hidden" value="${BranchOfficeType}" id="branchOfficeType" />
					<input type="hidden" value="${originCityId}" id="originCityId" />
					<html:hidden property="to.outMnfstDestIds"
						styleId="outMnfstDestIds" />
					<html:hidden property="to.manifestProcessId"
						styleId="manifestProcessId" />
					<html:hidden property="to.processNo" styleId="manifestProcessNo" />
					<html:hidden property="to.loginOfficeType"
						styleId="loginOfficeType" />
					<html:hidden property="to.loginRepHub" styleId="loginRepHub" />
					<html:hidden property="to.action" styleId="action" />
					<html:hidden styleId="createdBy" property="to.createdBy" />
					<html:hidden property="to.updatedBy" styleId="updatedBy" />
					<!-- Hidden Fields Stop -->
				</div>
			</div>

			<div class="button_containerform">
				<html:button property="Save" styleClass="btnintform" styleId="save"
					onclick="saveOrCloseOutManifestMBPL('save');">
					<bean:message key="button.label.save" />
				</html:button>
				<html:button property="Delete" styleClass="btnintform"
					styleId="deletedId" onclick="deleteConsgDtlsClientSide();">
					<bean:message key="button.label.delete" />
				</html:button>
				<html:button property="Print" styleClass="btnintform"
					styleId="print" onclick="printBPL();">
					<bean:message key="button.label.Print" />
				</html:button>
				<html:button property="Close" styleClass="btnintform"
					styleId="close" onclick="saveOrCloseOutManifestMBPL('close');">
					<bean:message key="button.label.close" />
				</html:button>
				<html:button property="Cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearPage();">
					<bean:message key="button.label.cancel" />
				</html:button>
			</div>
		</html:form>
	</div>
	<!--wraper ends-->

	<!-- <iframe name="iFrame" id="iFrame" width="700" height="1000"> </iframe> -->
</body>
</html>