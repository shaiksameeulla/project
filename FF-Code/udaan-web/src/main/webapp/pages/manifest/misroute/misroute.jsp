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
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/manifest/misroute/misroute.js"></script>
<script type="text/javascript" charset="utf-8">
	var CONSIGNMENT = null;
	var PACKET = null;
	var BAG = null;
	var MASTER_BAG = null;
	var officeType = null;
	var BranchMisroute = null;
	var OriginMisroute = null;
	var BranchCode = null;
	var HubCode = null;
	var RegionId = null;
	var RegionName = null;
	var cityId = null;
	var loggedincity = null;
	var loggedInReportingHUB = null;
	var ERROR_FLAG = null;
	var SUCCESS_FLAG = null;
	var OGM_SERIES = null;
	var BPL_SERIES = null;
	var BAGLOCK_SERIES = null;
	
	
	$(document).ready(function() {
		var oTable = $('#misrouteGrid').dataTable({
			"sScrollY" : "240",
			"sScrollX" : "100%",
			"sScrollXInner" : "140%",
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

	function loadDefaultObjects() {
		fnClickAddRow();
		CONSIGNMENT = '${CONSIGNMENT}';
		PACKET = '${PACKET}';
		BAG = '${BAG}';
		MASTER_BAG = '${MASTER_BAG}';
		officeType = '${office}';
		BranchMisroute = '${BranchMisroute}';
		OriginMisroute = '${OriginMisroute}';
		BranchCode = '${BranchCode}';
		HubCode = '${HubCode}';
		RegionId = '${regionId}';
		RegionName = '${regionName}';
		loggedincity = '${loggedincity}';
		loggedInReportingHUB = '${loggedInReportingHUB}';
		DOCUMENT = '${DOCUMENT}';
		PARCEL = '${PARCEL}';
		ERROR_FLAG = '${ERROR_FLAG}';
		SUCCESS_FLAG = '${SUCCESS_FLAG}';
		OGM_SERIES='${SERIES_TYPE_OGM_STICKERS}';
		BPL_SERIES='${SERIES_TYPE_BPL_NO}';
		BAGLOCK_SERIES='${BAGLOCK_SERIES}';

		document.getElementById("misrouteNumber").focus();
		document.getElementById("scannedItem").innerHTML = "CN/Packet/Bag/MasterBag Number";
		if (officeType == "B") {
			document.getElementById("misrouteHeader").innerHTML = BranchMisroute;
			document.getElementById("misrouteCode").innerHTML = BranchCode;
			$("#regionId").val(RegionId);
			document.getElementById("regionId").disabled = true;
			
		} else if (officeType == "O") {
			document.getElementById("misrouteHeader").innerHTML = OriginMisroute;
			document.getElementById("misrouteCode").innerHTML = HubCode;
		}

	}
</script>
<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects();">

	<div id="wraper">
		<html:form action="/misroute.do" method="post" styleId="misrouteForm">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<span id="misrouteHeader"></span>
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.manifest.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>

								<td width="9%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.dateTime" /></td>
								<td width="9%"><html:text property="to.misrouteDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true"></html:text></td>

								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.MisrouteManifestNumber" /></td>
								<td width="30%" colspan="2"><html:text
										property="to.misrouteNo" maxlength="10" styleClass="txtbox width130"
										styleId="misrouteNumber"
										onblur="convertDOMObjValueToUpperCase(this);validateMisrouteNumber(this);"
										onchange="fnValidateMisrouteByMnfstNo();"
										onkeypress="return OnlyAlphabetsAndNosAndEnter(event);"></html:text>
									<html:button property="Search" styleId="Find"
										styleClass="btnintgrid" onclick="searchMisroute()">
										<bean:message key="button.label.search" />
									</html:button></td>

								<td width="9%" class="lable"><span id="misrouteCode"></span></td>
								<td width="9%"><html:text property="to.officeName"
										styleClass="txtbox width130" styleId="branchCode" size="25"
										readonly="true"></html:text></td>

							</tr>
							<tr>

								<td width="9%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.manifest.destinationRegion"/></td>
								<td width="19%"><html:select
										property="to.destinationRegionId"
										styleClass="selectBox width140" styleId="regionId" onblur=""
										onchange="checkGridEmpty(this);clearDropDownValueCity();getAllMisrouteCities();"
										onkeypress="return callEnterKey(event, document.getElementById('cityId'));">
										<option selected="selected" value="0">----Select----</option>
										<c:forEach var="region" items="${regionTOs}" varStatus="loop">
											<option value="${region.regionId}">
												<c:out value="${region.regionName}" />
											</option>
										</c:forEach>
									</html:select></td>
								<%-- <html:text property="to.destinationRegionName" styleClass="txtbox width130" styleId="regionName" size="25"  readonly="true" onkeypress = "return callEnterKey(event, document.getElementById('stationId'));"></html:text> &nbsp;</td> --%>
								<td width="5%" class="lable"><bean:message
										key="label.manifest.Station" /></td>
								<td width="5%" colspan="2"><html:select
										property="to.destinationCityId"
										styleClass="selectBox width130" styleId="cityId"
										onchange="checkGridEmpty(this);clearDropDownValueOffice();getAllOffices(this);"
										onkeypress="return callEnterKey(event, document.getElementById('stationId'));">
										<option selected="selected" value="0">----Select----</option>
										<c:forEach var="city" items="${cityTOs}" varStatus="loop">
											<option value="${city.cityId}">
												<c:out value="${city.cityName}" />
											</option>
										</c:forEach>
									</html:select></td>


								<td width="5%" class="lable"><bean:message
										key="label.manifest.destinationOffice" /></td>
								<td width="9%"><html:select
										property="to.destinationStationId"
										styleClass="selectBox width130" styleId="stationId"
										onchange="checkGridEmpty(this);"
										onkeypress="return callEnterKey(event, document.getElementById('misrouteType'));">
										<option selected="selected" value="0">----Select----</option>
										<c:forEach var="station" items="${stationTOs}"
											varStatus="loop">
											<option value="${station.officeId}">
												<c:out value="${station.officeName}" />
											</option>
										</c:forEach>
									</html:select></td>


							</tr>
							<tr>
								<td width="9%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.manifest.MisrouteType" /></td>
								<td width="9%"><html:select property="to.misrouteType"
										styleClass="selectBox width130" styleId="misrouteType"
										onchange="labelChange(this);checkGridEmpty(this);"
										onkeypress="return enterKeyForMisrouteType(event,this);">
										<option selected="selected" value="0">---Select---</option>
										<c:forEach var="manifestTypes"
											items="${stockStandardTypeTOList}" varStatus="loop">
											<option value="${manifestTypes.stdTypeCode}">
												<c:out value="${manifestTypes.description}" />
											</option>
										</c:forEach>
									</html:select></td>

								<td width="12%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.manifest.ConsignmentType" /></td>
								<td colspan="2"><html:select property="to.consgType"
										styleClass="selectBox width130" styleId="consgTypeName"
										onchange="checkGridEmpty(this);"
										onkeypress="return enterKeyForConsgType(event,this);">
										<option selected="selected" value="0">----Select----</option>
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<c:choose>
												<c:when test="${consgTypes.consignmentName == docType}">
													<option
														value="${consgTypes.consignmentId}#${consgTypes.consignmentName}"
														selected="selected">
														<c:out value="${consgTypes.consignmentName}" />
													</option>
												</c:when>
												<c:otherwise>
													<option
														value="${consgTypes.consignmentId}#${consgTypes.consignmentName}">
														<c:out value="${consgTypes.consignmentName}" />
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</html:select></td>

								<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.manifest.bagLockNo" /></td>
								<td width="15%"><html:text property="to.bagLockNo"
										styleId="bagLockNo" maxlength="8" styleClass="txtbox width130"
										onkeypress="return callFocusEnterKey(event);"
										onblur="convertDOMObjValueToUpperCase(this);validateBagLockNo(this,'H');"></html:text></td>
							</tr>

						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.misrouteGrid.ValidationDetails" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="misrouteGrid" width="100%">
							<thead>
								<tr>
									<th width="3%"><input type="checkbox" id="checked"
										name="type" onclick="selectAllCheckBox();" /></th>
									<th width="5%"><bean:message
											key="label.misrouteGrid.serialNo" /></th>
									<th width="15%"><sup class="star">*</sup>&nbsp;<span
										id="scannedItem"></span> <%-- <bean:message key="label.misrouteGrid.CN/Paclet/Bag/MasterBagNumber"/> --%></th>
									<th width="8%"><bean:message
											key="label.misrouteGrid.Origin" /></th>
									<th width="5%"><bean:message
											key="label.misrouteGrid.NoofPieces" /></th>
									<th width="8%"><bean:message
											key="label.misrouteGrid.Pincode" /></th>
									<th width="15%"><bean:message
											key="label.misrouteGrid.ActualWeight" /></th>
									<th width="8%"><bean:message
											key="label.misrouteGrid.Contents" /></th>
									<th width="15%"><bean:message
											key="label.misrouteGrid.Paperwork" /></th>
									<th width="11%"><bean:message
											key="label.misrouteGrid.Insurance" /></th>
									<th width="10%"><sup class="mandatoryf">*</sup> <bean:message
											key="label.misrouteGrid.Remarks" /></th>
								</tr>
							</thead>

						</table>
					</div>
					<!-- Grid /-->
					<!-- Hidden Fields Start -->
					<html:hidden property="to.loginOfficeId" styleId="loginOfficeId"
						value="${loginOfficeId}" />
					<html:hidden property="to.loginCityCode" styleId="loginCityCode"
						value="${loginCityCode}" />
					<html:hidden property="to.seriesType" value="${seriesType}"
						styleId="seriesType" />
					<html:hidden property="to.officeType" styleId="officeType" />
					<html:hidden property="to.misrouteManifestStatus"
						styleId="misrouteManifestStatus" />
					<html:hidden property="to.misrouteId" styleId="misrouteId" />
					<html:hidden property="to.manifestType" styleId="manifestType"
						value="${manifestType}" />
					<html:hidden property="to.processCode" styleId="processCode"
						value="${processCode}" />
					<html:hidden property="to.manifestDirection"
						styleId="ManifestDirection" value="${ManifestDirection}" />
					<html:hidden property="to.manifestProcessTo.manifestProcessId"
						styleId="manifestProcessId" />
					<html:hidden property="to.officeCodeProcess"
						styleId="officeCodeProcess" value="${officeCodeProcess}" />
					<html:hidden property="to.destinationRegionId" styleId="regionId" />
					<html:hidden property="to.loginRegionId" styleId="loginRegionId" />
					<html:hidden property="to.loginRegionCode"
						styleId="loginRegionCode" value="${loginRegionCode}" />
					<html:hidden styleId="createdBy" property="to.createdBy" />
					<html:hidden property="to.updatedBy" styleId="updatedBy" />



				</div>
			</div>

			<!-- Button -->
			<div class="button_containerform">
				<html:button property="Save" styleClass="btnintform" styleId="Save"
					onclick="saveMisroute();">
					<bean:message key="button.label.save" />
				</html:button>
				<html:button property="Delete" styleClass="btnintform"
					styleId="Delete" onclick="deleteTableRow();">
					<bean:message key="button.label.delete" />
				</html:button>
				<html:button property="Print" styleClass="btnintform"
					styleId="Print" onclick="printMisroute();">
					<bean:message key="button.label.Print" />
				</html:button>
				<html:button property="Cancel" styleId="Cancel"
					styleClass="btnintform" onclick="cancelMisrouteDetails();">
					<bean:message key="label.bulkBooking.Cancel" />
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
