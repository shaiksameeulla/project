<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateConfigurationCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/baRateConfigurationForCourier.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/baRateConfigurationForPriority.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/baRateConfigurationForAirCargo.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/baRateConfigurationForTrain.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>

<!-- DataGrids /-->

<!-- Tabs-->
<!--<script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
<!--<link rel="stylesheet" href="/resources/demos/style.css" />  -->
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>
<script>
	$(function() {
		$("#tabsnested").tabs();
	});
</script>
<script>
	$(function() {
		$("#tabsnested1").tabs();
	});
	
	$(document).ready( function () {
	     $("#tabs").tabs({disabled: [1]});// 0th - Non-Priorty
	     $("#tabsnested").tabs({disabled: [1,2,3,4]});// 0th - Courier
	     $("#tabsnested1").tabs({disabled: [0,1,2]});
	    } );
	function enableTabs(id,index){
		var i=0;
		while(i<index){
		$("#"+id).tabs( "enable" , i );
		i++;
		}
	}
	
	statesList = '${statesList}';
	statesList = jsonJqueryParser(statesList);	
</script>
<!-- Tabs ends /-->
<!-- <script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var oTable1 = $('#CODChargesTbl').dataTable( {
		"sScrollY": "100",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable1, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0,
	} );
} );

</script> -->

<script type="text/javascript" charset="utf-8">
        
		$(document).ready( function () {
			loadDefaultCODCharges();
		} );
</script>
	
<script type="text/javascript" charset="utf-8">
	var TODAY_DATE='${TODAY_DATE}';
	var TRAIN_PROD_CAT_CODE = "TR";
	var AIR_CARGO_PROD_CAT_CODE = "AR";
	var COURIER_PROD_CAT_CODE = "CO";
	var PRIORITY_PROD_CAT_CODE = "PR";
	
	var nextColumnMinChrg = '${nextColumnMinChrgJSON}';
	nextColumnMinChrg = jsonJqueryParser(nextColumnMinChrg);
	
	// weight slab for train
	var weightSlabListForTrain = '${weightSlabListForTrainJSON}';
	weightSlabListForTrain = jsonJqueryParser(weightSlabListForTrain);
	
	// Rate sector for train
	var rateSectorsForTrain = '${rateSectorsForTrainJSON}';
	rateSectorsForTrain = jsonJqueryParser(rateSectorsForTrain);
	
	// weight slab for air cargo
	var weightSlabListForAirCargo = '${weightSlabListForAirCargoJSON}';
	weightSlabListForAirCargo = jsonJqueryParser(weightSlabListForAirCargo);
	
	// Rate sector for air cargo
	var rateSectorsForAirCargo = '${rateSectorsForAirCargoJSON}';
	rateSectorsForAirCargo = jsonJqueryParser(rateSectorsForAirCargo);
	
	// weight slab for courier
	var weightSlabListForCourier = '${weightSlabListForCourierJSON}';
	weightSlabListForCourier = jsonJqueryParser(weightSlabListForCourier);
	
	// Rate sector for courier
	var rateSectorsForCourier = '${rateSectorsForCourierJSON}';
	rateSectorsForCourier = jsonJqueryParser(rateSectorsForCourier);
	
	// weight slab for priority
	var weightSlabListForPriority = '${weightSlabListForPriorityJSON}';
	weightSlabListForPriority = jsonJqueryParser(weightSlabListForPriority);
	
	// Rate sector for priority
	var rateSectorsForPriority = '${rateSectorsForPriorityJSON}';
	rateSectorsForPriority = jsonJqueryParser(rateSectorsForPriority);
	
</script>

</head>
<body onload="loadDefaultValues();">
<!--wraper-->
<div id="wraper">
<div class="clear"></div>
	<!-- main content -->
	<html:form action="/baRateConfiguration.do" method="post" styleId="baRateConfigurationForm">
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<h1><strong><bean:message key="label.ba.rate.configuration.header" /></strong></h1>
					<div class="mandatoryMsgf"> <span class="mandatoryf">*</span><bean:message key="label.Rate.fieldMandatory" /></div>
				</div>
				<div class="formTable">
					<table border="0" cellpadding="0" cellspacing="2" width="100%">
						<tr>
							<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.configuration.frmDate" />:</td>
							<td width="15%">
								<html:text property="to.frmDate" styleId="frmDate" styleClass="txtbox width90" readonly="true"/>
								&nbsp;<a href="javascript:show_calendar('frmDate', this.value)" title="Select Date" id="frmDt"> <img id="calendarImg" src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" /></a></td>
							<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.configuration.toDate" />:</td>
							<td width="15%">
								<html:text property="to.toDate" styleId="toDate" styleClass="txtbox width90" readonly="true"/>
								&nbsp;<a href="javascript:show_calendar('toDate', this.value)" title="Select Date" id="toDt"> <img id="calendarImg" src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" /></a></td>
							<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.configuration.region" />:</td>
							<td width="17%">
								<html:select property="to.regionId" styleId="regionId" styleClass="selectBox width130" onchange="getAllCities();">
									<html:option value=""><bean:message key="label.common.select" locale="display" /></html:option>
									<c:forEach var="regionTO" items="${regionTOs}" varStatus="loop">
										<option value="${regionTO.regionId}"><c:out value="${regionTO.regionName}" /></option>
									</c:forEach>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.configuration.city" />:</td>
							<td width="15%">
								<html:select property="to.cityId" styleClass="selectBox width140" styleId="cityId" onchange="clearBaType();">
									<html:option value=""><bean:message key="label.common.select" locale="display" /></html:option>
								</html:select>
							</td>
							<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ba.rate.configuration.baType" />:</td>
							<td width="15%">
								<html:select property="to.baType" styleId="baType" styleClass="selectBox width155" onchange="searchBARatesDtls('CO');">
									<html:option value=""><bean:message key="label.common.select" locale="display" /></html:option>
									<c:forEach var="baType" items="${BATypeList}" varStatus="loop">
										<option value="${baType.customerTypeId}"><c:out value="${baType.customerTypeDesc}" /></option>
									</c:forEach>
								</html:select>
							</td>
							<td class="lable">&nbsp;</td>
							<td width="17%">
								<!-- <input name="Search2" type="button" value="Search" class="button" title="Search" onclick="searchBARateConfiguration();" /> -->
								<%-- <html:button property="search" styleClass="button" styleId="btnSearch" onclick="searchBARateConfiguration();" title="search"> <bean:message key="button.search" /></html:button> --%> 
								  &nbsp; 
								  <html:button property="renew" styleClass="button_disable" styleId="btnRenew" onclick="renewBAConfiguration();" title="renew" disabled = "true">
											<bean:message key="button.renew" /></html:button>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabs">
					<ul>
						<li><a href="#tabs-2" onclick="searchBARatesDtls('CO');scrollDataTableWidth();"><bean:message key="label.Rate.nonPriority"/></a></li>
						<li><a href="#tabs-3" onclick="prepareSplDestTable('PR');searchBARatesDtls('PR');"><bean:message key="label.Rate.priority"/></a></li>
					</ul>
					<div id="tabs-2">
						<div id="tabsnested">
							<ul>
								<li><a href="#tabs-4" onclick="searchBARatesDtls('CO');"><bean:message key="label.Rate.courier"/></a></li>
								<li><a href="#tabs-5" onclick="prepareSplDestTable('AR');searchBARatesDtls('AR');"><bean:message key="label.Rate.airCg"/></a></li>
								<li><a href="#tabs-6" onclick="prepareSplDestTable('TR');searchBARatesDtls('TR');"><bean:message key="label.Rate.train"/></a></li>
								<li><a href="#tabs-11" onclick="searchFixedChargesForCourier();prioritySplDestTable();loadDefaultCODCharges();"><bean:message key="label.Rate.fixedChrg"/></a></li>
								<li><a href="#tabs-12" onclick="searchRTOChargesForCourier();"><bean:message key="label.Rate.RTOChrg"/></a></li>
							</ul>
								
						<!-- tabs-4 begins -->
						<!-- COURIER START -->
						<div id="tabs-4">
							<table cellpadding="0" cellspacing="0" border="0" class="display" id="courierNormalSlabs" width="100%">
								<thead>
									<tr>
										<th width="25%" align="center"><bean:message key="label.ba.rate.configuration.region.weightSlab" /></th>
										<logic:present name="weightSlabListForCourier" scope="request">
											<c:forEach var="rateWtSlabs" items="${weightSlabListForCourier}" varStatus="loop">
												<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'CO'}">
													<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
														<th width="10%" align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}-${rateWtSlabs.weightSlabTO.endWeightLabel}</th>
													</c:if>
													<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
														<th width="10%" align="center">ADD ${rateWtSlabs.weightSlabTO.startWeight}</th>
													</c:if>
													<c:set var="courierProductCategoryId" value="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request" />
												</c:if>
											</c:forEach>
										</logic:present>
									</tr>
								</thead>
								<tbody>
									<logic:present name="rateSectorsForCourier" scope="request">
										<c:forEach var="rateSector" items="${rateSectorsForCourier}" varStatus="loopS">
											<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'CO'}">
												<c:if test="${rateSector.sectorType == 'D'}">
													<tr class="gradeA">
														<td><c:out value='${rateSector.sectorTO.sectorName}' /></td>
														<c:forEach var="rateWtSlabs" items="${weightSlabListForCourier}" varStatus="loopWt">
															<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'CO'}">
																<td>
																	<input type="text" id="rate${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" name="to.baCourierSlabRateTO.rate" class="txtbox width90" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" />
																	<input type="hidden" id="baDestSector${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" name="to.baCourierSlabRateTO.destinationSectorId" value="<c:out value='${rateSector.sectorTO.sectorId}'/>" />
																	<input type="hidden" id="slabRateid${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" name="to.baCourierSlabRateTO.baSlabRateid" />
																	<html:hidden styleId="baWeightSlabId${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baCourierSlabRateTO.baWeightSlabId" value="" />
																	<html:hidden styleId="startWeightSlabId${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baCourierSlabRateTO.startWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																	<html:hidden styleId="endWeightSlabId${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baCourierSlabRateTO.endWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" /> 
																	<html:hidden styleId="position${courierProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baCourierSlabRateTO.position" value="${loopWt.count+1}" />
																</td>
															</c:if>
														</c:forEach>
													</tr>
												</c:if>
											</c:if>
										</c:forEach>
									</logic:present>
								</tbody>
							</table>
							<br />
							<div class="formbox">
								<h1><strong><bean:message key="label.rate.configuration.special.destination" /></strong></h1>
							</div>
							<div>
								<table cellpadding="0" cellspacing="0" border="0" class="display" id="courierSpecialDestination" width="100%">
									<thead>
										<tr>
											<th width="19%" align="center"><bean:message key="label.rate.state" /></th>
											<th width="18%" align="center"><bean:message key="label.rate.city" /></th>
											<c:set var="wtCount" scope="request" />
											<logic:present name="weightSlabListForCourier" scope="request">
												<c:forEach var="rateWtSlabs" items="${weightSlabListForCourier}" varStatus="loop">
													<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'CO'}">
														<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
															<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}-${rateWtSlabs.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
															<th align="center">ADD ${rateWtSlabs.weightSlabTO.startWeight}</th>
														</c:if>
														<c:set var="courierSplDestcoloumCount" value="${loop.count}" scope="request" />
													</c:if>
												</c:forEach>
											</logic:present>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
										
								<!-- Button -->
								<div class="button_containerform">
									<html:button property="Save" styleClass="btnintformbigdis" disabled="true" styleId="btnCOSlabsSave" onclick="saveOrUpdateBARatesDtls('CO');" title="Save">
												<bean:message key="button.label.save" /></html:button>
									<html:button property="Cancel" styleClass="btnintformbigdis" disabled="true" styleId="btnCOSlabsCancel" onclick="searchBARatesDtls('CO');" title="Cancel">
												<bean:message key="button.clear" /></html:button>
								</div>
								<!-- Button ends -->
							</div>
						</div>
						<!-- COURIER END -->
						<!-- tabs-4 ends -->
						
						<!-- tabs-5 begins -->
						<!-- AIR-CARGO START -->
						<div id="tabs-5" style="width: 99%">
							<table cellpadding="0" cellspacing="0" border="0" class="display" id="airCargoWtSlabTable" width="100%">
								<thead>
									<tr>
										<th width="25%" align="center"><bean:message key="label.ba.rate.configuration.region.weightSlab" /></th>
										<logic:present name="weightSlabMinChrg" scope="request">
											<th align="center"><bean:message key="label.rate.minPayLoadChrgFor" />  
												<html:select property="to.wtSlabMinChrgAR" styleId="wtSlabMinChrgAR" styleClass="selectBox width90" onchange="validateForMC(this,'AR');" >
													<html:option value=""><bean:message key="label.common.select" /></html:option>
													<c:forEach var="wtSlabMinChrg" items="${weightSlabMinChrg}" varStatus="loopMC">
														<html:option value="${wtSlabMinChrg.weightSlabId}">${wtSlabMinChrg.endWeight}</html:option>
													</c:forEach>
												</html:select>
											</th>
										</logic:present>
										<logic:present name="weightSlabListForAirCargo" scope="request">
											<c:forEach var="rateWtSlabs" items="${weightSlabListForAirCargo}" varStatus="loop">
												<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'AR'}">
													<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
														<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}<c:if test="${not empty rateWtSlabs.weightSlabTO.endWeightLabel}"> - ${rateWtSlabs.weightSlabTO.endWeightLabel}</c:if></th>
													</c:if>
													<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
														<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}</th>
													</c:if>
													<c:set var="airCargoProductCategoryId" value="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request" />
												</c:if>
											</c:forEach>
										</logic:present>
									</tr>
								</thead>
								<tbody>
									<logic:present name="rateSectorsForAirCargo" scope="request">
										<c:forEach var="rateSector" items="${rateSectorsForAirCargo}" varStatus="loopS">
											<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'AR'}">
												<c:if test="${rateSector.sectorType == 'D'}">
													<tr class="gradeA">
														<td width="25%" align="center"><c:out value='${rateSector.sectorTO.sectorName}' /></td>
														<logic:present name="weightSlabMinChrg" scope="request">
															<td align="center">
																<html:text styleId="rate${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.rate" styleClass="txtbox width90" value="" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" /> 
																<html:hidden styleId="baWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.baWeightSlabId" value="" />
																<html:hidden styleId="startWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.startWeightSlabId" value="${zeroWeightSlabId}" />
																<html:hidden styleId="endWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.endWeightSlabId" value="${zeroWeightSlabId}" /> 
																<html:hidden styleId="baDestSector${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.destinationSectorId" value="${rateSector.sectorTO.sectorId}" />
																<html:hidden styleId="slabRateid${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.baSlabRateid" /> 
																<html:hidden styleId="position${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baAirCargoSlabRateTO.position" value="1" />
															</td>
														</logic:present>
														<c:forEach var="rateWtSlabs" items="${weightSlabListForAirCargo}" varStatus="loopWt">
															<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'AR'}">
																<td align="center">
																	<html:text styleId="rate${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.rate" styleClass="txtbox width90" value="" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" /> 
																	<html:hidden styleId="baWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.baWeightSlabId" value="" />
																	<html:hidden styleId="startWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.startWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																	<html:hidden styleId="endWeightSlabId${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.endWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" /> 
																	<html:hidden styleId="baDestSector${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.destinationSectorId" value="${rateSector.sectorTO.sectorId}" />
																	<html:hidden styleId="slabRateid${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.baSlabRateid" value="" />
																	<html:hidden styleId="position${airCargoProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baAirCargoSlabRateTO.position" value="${loopWt.count+1}" />
																</td>
															</c:if>
														</c:forEach>
													</tr>
												</c:if>
											</c:if>
										</c:forEach>
									</logic:present>
								</tbody>
							</table>
							<br />
							<div class="formbox">
								<h1><strong><bean:message key="label.rate.configuration.special.destination" /></strong></h1>
							</div>
							<div>
								<table cellpadding="0" cellspacing="0" border="0" class="display" id="airCargoSplDestTable" width="100%">
									<thead>
										<tr>
											<th width="19%" align="center"><bean:message key="label.rate.state" /></th>
											<th width="18%" align="center"><bean:message key="label.rate.city" /></th>
											<c:set var="wtCount" scope="request" />
											<logic:present name="weightSlabMinChrg" scope="request">
												<th width="13%" align="center"><bean:message key="label.rate.minPayLoadChrgFor" /> <span id="splDestMC_AR"></span></th>
											</logic:present>
											<logic:present name="weightSlabListForAirCargo" scope="request">
												<c:forEach var="rateWtSlabs" items="${weightSlabListForAirCargo}" varStatus="loop">
													<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'AR'}">
														<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
															<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}<c:if test="${not empty rateWtSlabs.weightSlabTO.endWeightLabel}"> - ${rateWtSlabs.weightSlabTO.endWeightLabel}</c:if></th>
														</c:if>
														<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
															<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="airCargoSplDestcoloumCount" value="${loop.count}" scope="request" />
													</c:if>
												</c:forEach>
											</logic:present>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								
								<!-- Button -->
								<div class="button_containerform">
									<html:button property="Save" styleClass="btnintformbigdis" disabled="true" styleId="btnARSlabsSave" onclick="saveOrUpdateBARatesDtls('AR');" title="Save">
										<bean:message key="button.label.save" /></html:button>
									<html:button property="Cancel" styleClass="btnintformbigdis" disabled="true" styleId="btnARSlabsCancel" onclick="searchBARatesDtls('AR');" title="Cancel">
										<bean:message key="button.clear" /></html:button>
								</div>
								<!-- Button ends -->
							</div>
						</div>
						<!-- AIR-CARGO END -->
						<!-- tabs-5 ends -->
			            
						<!-- tabs-6 begins -->
						<!-- TRAIN START -->
						<div id="tabs-6" style="width: 99%">
							<table cellpadding="0" cellspacing="0" border="0" class="display" id="trainWtSlabTable" width="100%">
								<thead>
									<tr>
										<th width="25%" align="center"><bean:message key="label.ba.rate.configuration.region.weightSlab" /></th>
										<logic:present name="weightSlabMinChrg" scope="request">
											<th align="center"><bean:message key="label.rate.minPayLoadChrgFor" />  
												<html:select property="to.wtSlabMinChrgTR" styleId="wtSlabMinChrgTR" styleClass="selectBox width90" onchange="validateForMC(this,'TR');" >
													<html:option value=""><bean:message key="label.common.select" /></html:option>
													<c:forEach var="wtSlabMinChrg" items="${weightSlabMinChrg}" varStatus="loopMC">
														<html:option value="${wtSlabMinChrg.weightSlabId}">${wtSlabMinChrg.endWeight}</html:option>
													</c:forEach>
												</html:select>
											</th>
										</logic:present>
										<logic:present name="weightSlabListForTrain" scope="request">
											<c:forEach var="rateWtSlabs" items="${weightSlabListForTrain}" varStatus="loop">
												<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'TR'}">
													<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
														<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}<c:if test="${not empty rateWtSlabs.weightSlabTO.endWeightLabel}"> - ${rateWtSlabs.weightSlabTO.endWeightLabel}</c:if></th>
													</c:if>
													<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
														<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}</th>
													</c:if>
													<c:set var="trainProductCategoryId" value="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request" />
												</c:if>
											</c:forEach>
										</logic:present>
									</tr>
								</thead>
								<tbody>
									<logic:present name="rateSectorsForTrain" scope="request">
										<c:forEach var="rateSector" items="${rateSectorsForTrain}" varStatus="loopS">
											<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'TR'}">
												<c:if test="${rateSector.sectorType == 'D'}">
													<tr class="gradeA">
														<td width="25%" align="center"><c:out value='${rateSector.sectorTO.sectorName}' /></td>
														<logic:present name="weightSlabMinChrg" scope="request">
															<td align="center">
																<html:text styleId="rate${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.rate" styleClass="txtbox width90" value="" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" /> 
																<html:hidden styleId="baWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.baWeightSlabId" value="" />
																<html:hidden styleId="startWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.startWeightSlabId" value="${zeroWeightSlabId}" />
																<html:hidden styleId="endWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.endWeightSlabId" value="${zeroWeightSlabId}" /> 
																<html:hidden styleId="baDestSector${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.destinationSectorId" value="${rateSector.sectorTO.sectorId}" />
																<html:hidden styleId="slabRateid${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.baSlabRateid" /> 
																<html:hidden styleId="position${trainProductCategoryId}${rateSector.sectorTO.sectorId}${zeroWeightSlabId}" property="to.baTrainSlabRateTO.position" value="1" />
															</td>
														</logic:present>
														<c:forEach var="rateWtSlabs" items="${weightSlabListForTrain}" varStatus="loopWt">
															<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'TR'}">
																<td align="center">
																	<html:text styleId="rate${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.rate" styleClass="txtbox width90" value="" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" /> 
																	<html:hidden styleId="baWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.baWeightSlabId" value="" />
																	<html:hidden styleId="startWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.startWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																	<html:hidden styleId="endWeightSlabId${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.endWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" /> 
																	<html:hidden styleId="baDestSector${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.destinationSectorId" value="${rateSector.sectorTO.sectorId}" />
																	<html:hidden styleId="slabRateid${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.baSlabRateid" value="" />
																	<html:hidden styleId="position${trainProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baTrainSlabRateTO.position" value="${loopWt.count+1}" />
																</td>
															</c:if>
														</c:forEach>
													</tr>
												</c:if>
											</c:if>
										</c:forEach>
									</logic:present>
								</tbody>
							</table>
							<br />
							<div class="formbox">
								<h1><strong><bean:message key="label.rate.configuration.special.destination" /></strong></h1>
							</div>
							<div>
								<table cellpadding="0" cellspacing="0" border="0" class="display" id="trainSplDestTable" width="100%">
									<thead>
										<tr>
											<th width="19%" align="center"><bean:message key="label.rate.state" /></th>
											<th width="18%" align="center"><bean:message key="label.rate.city" /></th>
											<c:set var="wtCount" scope="request" />
											<logic:present name="weightSlabMinChrg" scope="request">
												<th width="13%" align="center"><bean:message key="label.rate.minPayLoadChrgFor" /> <span id="splDestMC_TR"></span></th>
											</logic:present>
											<logic:present name="weightSlabListForTrain" scope="request">
												<c:forEach var="rateWtSlabs" items="${weightSlabListForTrain}" varStatus="loop">
													<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'TR'}">
														<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
															<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}<c:if test="${not empty rateWtSlabs.weightSlabTO.endWeightLabel}"> - ${rateWtSlabs.weightSlabTO.endWeightLabel}</c:if></th>
														</c:if>
														<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
															<th align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="trainSplDestcoloumCount" value="${loop.count}" scope="request" />
													</c:if>
												</c:forEach>
											</logic:present>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>

								<!-- Button -->
								<div class="button_containerform">
									<html:button property="Save" styleClass="btnintformbigdis" disabled="true" styleId="btnTRSlabsSave" onclick="saveOrUpdateBARatesDtls('TR');" title="Save">
										<bean:message key="button.label.save" /></html:button>
									<html:button property="Cancel" styleClass="btnintformbigdis" disabled="true" styleId="btnTRSlabsCancel" onclick="searchBARatesDtls('TR');" title="Cancel">
										<bean:message key="button.label.Cancel" /></html:button>
								</div>
								<!-- Button ends -->
							</div>
						</div>
						<!-- TRAIN END -->
			            <!-- tabs-6 ends -->
						
								<div id="tabs-11">
									<table border="0" cellpadding="0" cellspacing="2" width="100%">
										<tr>
											<td width="18%" height="22" class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.fuelSurchargeChk"
													styleId="FuelSurchargesChk" onchange="clearcheckBoxValue('FuelSurchargesChk','FuelSurcharges');"/> <bean:message
													key="label.Rate.FuelSurcharges" /></td>
											<td width="17%" class="lable1"><html:text
													property="to.baCourierFixedChargesTO.fuelSurcharge"
													styleId="FuelSurcharges" value=""
													styleClass="txtbox width30"></html:text> <bean:message
													key="label.Rate.Percent" /></td>
											<td width="18%" class="lable1"><bean:message
													key="label.Rate.ifInsuredBy" /></td>
											<td class="lable1"><bean:message key="label.Rate.FF" />&nbsp;

												<c:forEach var="insuredBy" items="${insuredByList}"
													varStatus="loopS">
													<c:if test="${insuredBy.insuredByCode == 'F'}">
														<html:text
															property="to.baCourierFixedChargesTO.ifInsuredByFF"
															styleId="ifInsuredByFF" value="${insuredBy.percentile}"
															styleClass="txtbox width30" readonly="true">
														</html:text>
													</c:if>
													<c:if test="${insuredBy.insuredByCode == 'C'}">
														<bean:message key="label.Rate.customer" />&nbsp; <html:text
															property="to.baCourierFixedChargesTO.ifInsuredByCustomer"
															styleId="ifInsuredByCustomer"
															value="${insuredBy.percentile}"
															styleClass="txtbox width30" readonly="true">
														</html:text>
													</c:if>
												</c:forEach></td>
											<td width="25%" rowspan="8" valign="top">
												<table width="100%" border="0" cellpadding="3"
													cellspacing="3">
													<tr>
														<td width="219" class="lable1"><html:checkbox
																property="to.baCourierFixedChargesTO.serviceTaxChk"
																styleId="serviceTaxChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.ServiceTax" /></td>
														<td width="72" class="lable1"><html:text
																property="to.baCourierFixedChargesTO.serviceTax"
																styleId="serviceTax" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baCourierFixedChargesTO.eduChargesChk"
																styleId="eduChargesChk" styleClass="checkbox" />
															<bean:message key="label.Rate.EducationCess" /></td>
														<td class="lable1"><html:text
																property="to.baCourierFixedChargesTO.eduCharges"
																styleId="eduCharges" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1" width="219"><html:checkbox
																property="to.baCourierFixedChargesTO.higherEduChargesChk"
																styleId="higherEduChargesChk" styleClass="checkbox" />
															<bean:message key="label.Rate.HigherEducationCess" /></td>
														<td class="lable1"><html:text
																property="to.baCourierFixedChargesTO.higherEduCharges"
																styleId="higherEduCharges" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baCourierFixedChargesTO.stateTaxChk"
																styleId="stateTaxChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.StateTax" /></td>
														<td class="lable1"><html:text
																property="to.baCourierFixedChargesTO.stateTax"
																styleId="stateTax" value="" styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baCourierFixedChargesTO.surchargeOnSTChk"
																styleId="surchargeOnSTChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.SurchargeOnST" /></td>
														<td class="lable1"><html:text
																property="to.baCourierFixedChargesTO.surchargeOnST"
																styleId="surchargeOnST" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.otherChargesChk"
													styleId="otherChargesChk" styleClass="checkbox" onchange="clearcheckBoxValue('otherChargesChk','otherCharges');"/> <bean:message
													key="label.Rate.OtherCharges" /></td>
											<td class="lable1"><html:text
													property="to.baCourierFixedChargesTO.otherCharges"
													styleId="otherCharges" value="" styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.parcelChargesChk"
													styleId="parcelChargesChk" onchange="clearcheckBoxValue('parcelChargesChk','parcelCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.ParcelHandlingCharges" /></td>
											<td width="22%" class="lable1"><html:text
													property="to.baCourierFixedChargesTO.parcelCharges"
													styleId="parcelCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.octroiBourneByChk"
													styleId="octroiBourneByChk" onchange="clearcheckBoxValue('octroiBourneByChk','octroiServiceCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.OctroiBornBy" /></td>
											<td class="lable1"><select name="to.baCourierFixedChargesTO.octroiBourneBy"
												id="octroiBourneBy" class="selectBox width90 ">
													<option value="CO">Consignor</option>
													<option value="CE">Consignee</option>
											</select></td>
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.octroiServiceChargesChk"
													styleId="octroiServiceChargesChk" onchange="clearcheckBoxValue('octroiServiceChargesChk','octroiServiceCharges');" styleClass="checkbox" />
												<bean:message key="label.Rate.OctroiServiceCharges" /></td>
											<td class="lable1"><html:text
													property="to.baCourierFixedChargesTO.octroiServiceCharges"
													styleId="octroiServiceCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Percent" /></td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.airportChargesChk"
													styleId="airportChargesChk" onchange="clearcheckBoxValue('airportChargesChk','airportCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.AirportHandlingCharges" /></td>
											<td class="lable1"><html:text
													property="to.baCourierFixedChargesTO.airportCharges"
													styleId="airportCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Percent" /></td>
											<!-- <td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td> -->
											<td class="lable1"><html:checkbox
													property="to.baCourierFixedChargesTO.toPayChargesChk"
													styleId="toPayChargesChk" onchange="clearcheckBoxValue('toPayChargesChk','toPayCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.ToPayCharge" /></td>
											<td width="22%" class="lable1"><html:text
													property="to.baCourierFixedChargesTO.toPayCharges"
													styleId="toPayCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>
										</tr>
										<tr>
											<td height="-1" class="lable">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
										</tr>
										<tr>
										<td  class="lable"><html:checkbox
												property="to.baCourierFixedChargesTO.codChargesChk"
												disabled="true" styleId="codChargesChk" /> <bean:message
												key="label.Rate.codCharges" /></td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										</tr>
										<!-- COD Charges start -->
										<tr style="height:110px;">
										<td colspan ="6" valign = "top">
										<table cellpadding="0" cellspacing="0" border="0" class="display" id="CODChargesTbl" width="100%">
											<thead>
												<tr>
													<th align="center" width = "6%"><bean:message key="label.Rate.DeclaredValue" /></th>
													<th align="center"  width = "6%"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"  width = "6%"><bean:message key="label.Rate.COD%" /></th>
													<th align="center"  width = "6%"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"  width = "6%"><bean:message key="label.Rate.FCorCODGreater" /></th>
												</tr>
											</thead>
											<tbody>
											<c:set var="codCntValue" value="1"/>
												<logic:present name="codChargeTOs" scope="request">												 
													<c:forEach var="codChargeList" items="${codChargeTOs}"
														varStatus="loop">
														<tr class="gradeA">
															<td align="center">
															<span id="decalredVal${loop.count}"> ${codChargeList.minimumDeclaredValLabel}
															<c:if test="${codChargeList.maximumDeclaredValLabel != null}">
																		 - ${codChargeList.maximumDeclaredValLabel}
																		 </c:if>
																		</span></td>
															<td class="center"><input type="text"
																class="txtbox width90" id="fixedCharges${loop.count}"
																name="to.baCourierFixedChargesTO.fixedChargesEco" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'');"/></td>
															<td class="center"><input type="text"
																class="txtbox width90" size="11"
																id="codPercent${loop.count}"
																name="to.baCourierFixedChargesTO.codPercent" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P');"/></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="FixedChargesRadio${loop.count}" value="f" /></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="fcOrCODRadio${loop.count}" value="c" />
																<input type="hidden" id="codChargeId${loop.count}"
																value="${codChargeList.codChargeId }"
																name="to.baCourierFixedChargesTO.codChargeId" /></td>
															 <c:set var="codCntValue" value="${loop.count}"/>
														</tr>
														
													</c:forEach>													
												</logic:present>
											</tbody>
										</table>
										
										
										
										
										</td>
										</tr>
										<tr>
										<td colspan ="6"></td></tr>
									</table>
									<div>
										
										<br /> <input type="hidden" id="codChargeTOs"
											value="${codChargeTOs}" />
											<input type="hidden" id="codRowsCnt"
																value="${codCntValue}"
																name="codRowsCnt" />
										 <%--  <input type="hidden"  id="codChargeLength" value="${codChargeTO}" /> --%>
									</div>
									<!-- COD Charges ends -->
									
									<!-- Button -->
									<div class="button_containerform">
										<html:button property="Save" styleClass="btnintform"
											styleId="btnSave"
											onclick="saveOrUpdateFixedChargesForCourier();" title="Save">
											<bean:message key="button.label.save" />
										</html:button>
										<input name="Cancel" type="button" value="Cancel"
											class="btnintform" title="Cancel" onclick="searchFixedChargesForCourier();"/>
									</div>
									<!-- Button ends -->
								</div>
								<!-- tab 11 ends -->

								<div id="tabs-12">
									<table border="0" cellpadding="0" cellspacing="3" width="100%">
										<tr>
											<td width="18%" class="lable"><html:checkbox
													property="to.baCourierRTOChargesTO.rtoChargesApplicablechk"
													onclick="enableFields(this);"
													styleId="RTOChargesApplicableChk" /> <bean:message
													key="label.rate.configuration.rto.charge.applicable" /></td>
											
											<td width="15%" class="lable"><html:checkbox
													property="to.baCourierRTOChargesTO.sameAsSlabRate"
													onclick="enableDiscountFields(this);"
													styleId="sameAsSlabRateChk" disabled="true" /> <bean:message
													key="label.Rate.configuration.ifYes" /></td>
											<td width="17%" class="lable1"><select name="select13"
												class="selectBox" id ="sameAsSlabRate">
													<option value="0">Same as Tariff No.</option>
											</select></td>
											<td width="15%" class="lable"><bean:message
													key="label.Rate.configuration.discount" /></td>
											<td width="21%" class="lable1"><html:text
													property="to.baCourierRTOChargesTO.discountOnSlab"
													styleId="Discount" value="" styleClass="txtbox width70"
													disabled="true"></html:text> <bean:message
													key="label.Rate.Percent" /></td>
										</tr>

									</table>
									<div class="clear"></div>
									<!-- Button -->
									<div class="button_containerform">
									<html:button property="Save" styleClass="btnintform"
											styleId="btnCourierRTOSave"
											onclick="saveOrUpdateRTOChargesForCourier();" title="Save">
											<bean:message key="button.label.save" />
										</html:button>
										<input name="Clear" type="button" value="Clear"
											class="btnintform" title="Clear" onclick="searchRTOChargesForCourier();"/>

									</div>
									<!-- Button ends -->
								</div>
								<!-- tab 12 ends -->

							</div>
							<!-- tab nested ends -->
						</div>
						<!-- tab 2nd ends-->

						<div id="tabs-3">
							<div id="tabsnested1">
								<ul>
									<li><a href="#tabs-13"  onclick="prepareSplDestTable('PR');searchBARatesDtls('PR');">Rates</a></li>
									<li><a href="#tabs-14"  onclick="searchFixedChargesForPriority();">Fixed Charges</a></li>
									<li><a href="#tabs-15"  onclick="searchRTOChargesForPriority();">RTO Charges</a></li>
								</ul>
								
						<!-- tabs-13 begins -->
						<!-- PRIORITY START -->
						<div id="tabs-13">
							<div class="formTable">
								<table border="0" cellpadding="0" cellspacing="2" width="100%">
									<tr>
										<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;Serviced At:</td>
										<td width="23%" class="lable1">
											<html:select property="to.baPrioritySlabRateTO.servicedOn" styleId="servicedOn" styleClass="selectBox width155" onchange="searchBARatesDtls('PR');">
		          							<logic:present name="servicedOnList" scope="request">
												<c:forEach var="servicedOn" items="${servicedOnList}" varStatus="loop">
													<html:option value="${servicedOn.stdTypeCode}">${servicedOn.description}</html:option>
												</c:forEach>
											</logic:present>
											</html:select>
										</td>
										<td width="14%" class="lable">&nbsp;</td>
										<td width="21%">&nbsp;</td>
										<td width="15%" class="lable">&nbsp;</td>
										<td width="17%">&nbsp;</td>
									</tr>
								</table>
							</div>
							<div>
								<table cellpadding="0" cellspacing="0" border="0" class="display" id="prioritySlabRates" width="90%">
									<thead>
										<tr>
											<th id = 'prcol' width="25%" align="center"><bean:message key="label.ba.rate.configuration.region.weightSlab" /></th>
											<logic:present name="weightSlabListForCourier" scope="request">
												<c:forEach var="rateWtSlabs" items="${weightSlabListForPriority}" varStatus="loop">
													<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'PR'}">
														<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
															<th id = 'prcol${loop.index}' width="10%" align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}-${rateWtSlabs.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
															<th id = 'prcol${loop.index}' width="10%" align="center">ADD ${rateWtSlabs.weightSlabTO.startWeight}</th>
														</c:if>
														<c:set var="priorityProductCategoryId" value="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request" />
													</c:if>
												</c:forEach>
											</logic:present>
										</tr>
									</thead>
									<tbody>
										<logic:present name="rateSectorsForPriority" scope="request">
											<c:forEach var="rateSector" items="${rateSectorsForPriority}" varStatus="loopS">
												<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'PR'}">
													<c:if test="${rateSector.sectorType == 'D'}">
														<tr class="gradeA">
															<td><c:out value='${rateSector.sectorTO.sectorName}' /></td>
															<c:forEach var="rateWtSlabs" items="${weightSlabListForPriority}" varStatus="loopWt">
																<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'PR'}">
																	<td>
																		<input type="text" id="rate${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" name="to.baPrioritySlabRateTO.rate" class="txtbox width90" onkeypress="return onlyDecimal(event);" onblur="validateFormat(this);" />
																		<input type="hidden" id="baDestSector${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" name="to.baPrioritySlabRateTO.destinationSectorId" value="<c:out value='${rateSector.sectorTO.sectorId}'/>" />
																		<html:hidden styleId="slabRateid${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baPrioritySlabRateTO.baSlabRateid" value ="" />
																		<html:hidden styleId="baWeightSlabId${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baPrioritySlabRateTO.baWeightSlabId" value="" />
																		<html:hidden styleId="startWeightSlabId${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baPrioritySlabRateTO.startWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																		<html:hidden styleId="endWeightSlabId${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baPrioritySlabRateTO.endWeightSlabId" value="${rateWtSlabs.weightSlabTO.weightSlabId}" /> 
																		<html:hidden styleId="position${priorityProductCategoryId}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}" property="to.baPrioritySlabRateTO.position" value="${loopWt.count+1}" />
																	</td>
																</c:if>
															</c:forEach>
														</tr>
													</c:if>
												</c:if>
											</c:forEach>
										</logic:present>
									</tbody>
								</table>
								<br />
								<div class="formbox">
									<h1><strong><bean:message key="label.rate.configuration.special.destination" /></strong></h1>
								</div>
								<div>
									<table cellpadding="0" cellspacing="0" border="0" class="display" id="prioritySpecialDestination" width="100%">
										<thead>
											<tr>
												<th width="19%" align="center"><bean:message key="label.rate.state" /></th>
												<th width="18%" align="center"><bean:message key="label.rate.city" /></th>
												<logic:present name="weightSlabListForCourier" scope="request">
												<c:forEach var="rateWtSlabs" items="${weightSlabListForPriority}" varStatus="loop">
	 												<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == 'PR'}">
														<c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
															<th id = 'prsdcol${loop.index}'  align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}-${rateWtSlabs.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
															<th  id = 'prsdcol${loop.index}' align="center">ADD ${rateWtSlabs.weightSlabTO.startWeight}</th>
														</c:if>
														<c:set var="prioritySplDestcoloumCount" value="${loop.count}" scope="request" />
													</c:if>
												</c:forEach>
												</logic:present>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							
								<!-- Button -->
								<br></br>
								<div class="button_containerform">
									<html:button property="Save" styleClass="btnintform" styleId="btnPrioritySlabsSave" onclick="saveOrUpdateBARatesDtls('PR');" title="Save">
	   				 					<bean:message key="button.label.save" /></html:button>
									<html:button property="Cancel" styleClass="btnintform" styleId="btnPrioritySlabsCancel" onclick="searchBARatesDtls('PR');" title="Save">
			   			 					<bean:message key="button.label.Cancel" /></html:button>
								</div>
								<!-- Button ends -->
							</div>
						</div>
						<!-- PRIORITY END -->
						<!-- tabs-13 ends -->
							
								
								<div id="tabs-14">
									<table border="0" cellpadding="0" cellspacing="2" width="100%">
										<tr>
											<td width="18%" height="22" class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.fuelSurchargeChk"
													styleId="pr_FuelSurchargesChk" onchange="clearcheckBoxValue('pr_FuelSurchargesChk','pr_FuelSurcharges');"/> <bean:message
													key="label.Rate.FuelSurcharges" /></td>
											<td width="17%" class="lable1"><html:text
													property="to.baPriorityFixedChargesTO.fuelSurcharge"
													styleId="pr_FuelSurcharges" value=""
													styleClass="txtbox width30"></html:text> <bean:message
													key="label.Rate.Percent" /></td>
											<td width="18%" class="lable1"><bean:message
													key="label.Rate.ifInsuredBy" /></td>
											<td class="lable1"><bean:message key="label.Rate.FF" />&nbsp;

												<c:forEach var="insuredBy" items="${insuredByList}"
													varStatus="loopS">
													<c:if test="${insuredBy.insuredByCode == 'F'}">
														<html:text
															property="to.baPriorityFixedChargesTO.ifInsuredByFF"
															styleId="pr_ifInsuredByFF" value="${insuredBy.percentile}"
															styleClass="txtbox width30" readonly="true">
														</html:text>
													</c:if>
													<c:if test="${insuredBy.insuredByCode == 'C'}">
														<bean:message key="label.Rate.customer" />&nbsp; <html:text
															property="to.baPriorityFixedChargesTO.ifInsuredByCustomer"
															styleId="pr_ifInsuredByCustomer"
															value="${insuredBy.percentile}"
															styleClass="txtbox width30" readonly="true">
														</html:text>
													</c:if>
												</c:forEach></td>
											<td width="25%" rowspan="8" valign="top">
												<table width="100%" border="0" cellpadding="3"
													cellspacing="3">
													<tr>
														<td width="219" class="lable1"><html:checkbox
																property="to.baPriorityFixedChargesTO.serviceTaxChk"
																styleId="pr_serviceTaxChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.ServiceTax" /></td>
														<td width="72" class="lable1"><html:text
																property="to.baPriorityFixedChargesTO.serviceTax"
																styleId="pr_serviceTax" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baPriorityFixedChargesTO.eduChargesChk"
																styleId="pr_eduChargesChk" styleClass="checkbox" />
															<bean:message key="label.Rate.EducationCess" /></td>
														<td class="lable1"><html:text
																property="to.baPriorityFixedChargesTO.eduCharges"
																styleId="pr_eduCharges" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1" width="219"><html:checkbox
																property="to.baPriorityFixedChargesTO.higherEduChargesChk"
																styleId="pr_higherEduChargesChk" styleClass="checkbox" />
															<bean:message key="label.Rate.HigherEducationCess" /></td>
														<td class="lable1"><html:text
																property="to.baPriorityFixedChargesTO.higherEduCharges"
																styleId="pr_higherEduCharges" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baPriorityFixedChargesTO.stateTaxChk"
																styleId="pr_stateTaxChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.StateTax" /></td>
														<td class="lable1"><html:text
																property="to.baPriorityFixedChargesTO.stateTax"
																styleId="pr_stateTax" value="" styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
													<tr>
														<td class="lable1"><html:checkbox
																property="to.baPriorityFixedChargesTO.surchargeOnSTChk"
																styleId="pr_surchargeOnSTChk" styleClass="checkbox" /> <bean:message
																key="label.Rate.SurchargeOnST" /></td>
														<td class="lable1"><html:text
																property="to.baPriorityFixedChargesTO.surchargeOnST"
																styleId="pr_surchargeOnST" value=""
																styleClass="txtbox width30" readonly="true">
															</html:text> <bean:message key="label.Rate.Percent" /></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.otherChargesChk"
													styleId="pr_otherChargesChk" onchange="clearcheckBoxValue('pr_otherChargesChk','pr_otherCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.OtherCharges" /></td>
											<td class="lable1"><html:text
													property="to.baPriorityFixedChargesTO.otherCharges"
													styleId="pr_otherCharges" value="" styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>
											<td class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.parcelChargesChk"
													styleId="pr_parcelChargesChk" onchange="clearcheckBoxValue('pr_parcelChargesChk','pr_parcelCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.ParcelHandlingCharges" /></td>
											<td width="22%" class="lable1"><html:text
													property="to.baPriorityFixedChargesTO.parcelCharges"
													styleId="pr_parcelCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.octroiBourneByChk"
													styleId="pr_octroiBourneByChk" onchange="clearcheckBoxValue('pr_octroiBourneByChk','pr_octroiServiceCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.OctroiBornBy" /></td>
											<td class="lable1"><select name="to.baPriorityFixedChargesTO.octroiBourneBy"
												id="pr_octroiBourneBy" class="selectBox width90 ">
													<option value="CO">Consignor</option>
													<option value="CE">Consignee</option>
											</select></td>
											<td class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.octroiServiceChargesChk"
													styleId="pr_octroiServiceChargesChk" onchange="clearcheckBoxValue('pr_octroiServiceChargesChk','pr_octroiServiceCharges');" styleClass="checkbox" />
												<bean:message key="label.Rate.OctroiServiceCharges" /></td>
											<td class="lable1"><html:text
													property="to.baPriorityFixedChargesTO.octroiServiceCharges"
													styleId="pr_octroiServiceCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Percent" /></td>
										</tr>
										<tr>
											<td class="lable1"><html:checkbox
													property="to.baPriorityFixedChargesTO.airportChargesChk"
													styleId="pr_airportChargesChk" onchange="clearcheckBoxValue('pr_airportChargesChk','pr_airportCharges');" styleClass="checkbox" /> <bean:message
													key="label.Rate.AirportHandlingCharges" /></td>
											<td class="lable1"><html:text
													property="to.baPriorityFixedChargesTO.airportCharges"
													styleId="pr_airportCharges" value=""
													styleClass="txtbox width30">
												</html:text> <bean:message key="label.Rate.Percent" /></td>
											<td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
										</tr>
										<tr>
											<td height="-1" class="lable">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
											<td class="lable1">&nbsp;</td>
										</tr>
									</table>
									<!-- Button -->
									<div class="button_containerform">
										<html:button property="Save" styleClass="btnintform"
											styleId="btnPriorityFixedChargeSave"
											onclick="saveOrUpdateFixedChargesForPriority();" title="Save">
									    <bean:message key="button.label.save" />
									    </html:button>
										
										<html:button property="Cancel" styleClass="btnintform"
											styleId="btnPriorityFixedChargeCancel"
											onclick="searchFixedChargesForPriority();" title="Save">
									    <bean:message key="button.label.Cancel" />
									    </html:button>
										
									</div>
									<!-- Button ends -->
								</div>
								<!-- tab 14 ends -->

								<div id="tabs-15">
									 <table border="0" cellpadding="0" cellspacing="3" width="100%">
										<tr>
											<td width="18%" class="lable"><html:checkbox
													property="to.baPriorityRTOChargesTO.rtoChargesApplicablechk"
													onclick="enableFields_PR(this);"
													styleId="prRTOChargesApplicableChk" /> <bean:message
													key="label.rate.configuration.rto.charge.applicable" /></td>
											
											<td width="15%" class="lable"><html:checkbox
													property="to.baPriorityRTOChargesTO.sameAsSlabRate"
													onclick="enableDiscountFields_PR(this);"
													styleId="prsameAsSlabRateChk" disabled="true" /> <bean:message
													key="label.Rate.configuration.ifYes" /></td>
											<td width="17%" class="lable1"><select name="select13"
												class="selectBox " id ="prsameAsSlabRate">
													<option value="0">Same as Tariff No.</option>
											</select></td>
											<td width="15%" class="lable"><bean:message
													key="label.Rate.configuration.discount" /></td>
											<td width="21%" class="lable1"><html:text
													property="to.baPriorityRTOChargesTO.discountOnSlab"
													styleId="prDiscount" value="" styleClass="txtbox width70"></html:text> <bean:message
													key="label.Rate.Percent" /></td>
										</tr>

									</table>
									<div class="clear"></div>
									<!-- Button -->
									<div class="button_containerform">
									<html:button property="Save" styleClass="btnintform"
											styleId="btnPriorityRTOSave"
											onclick="saveOrUpdateRTOChargesForPriority();" title="Save">
											<bean:message key="button.label.save" />
										</html:button>	
										<input name="Clear" type="button" value="Clear"
											class="btnintform" title="Clear" onclick="searchRTOChargesForPriority();"/>
									</div>
									<!-- Button ends -->
								</div>
								<!-- tab 15 ends -->

							</div>
							<!-- tab nested ends -->
						</div>
						<!-- tab 3nd ends-->

					</div>

					<!-- Grid /-->
				</div>
				<!-- Button -->
				<div class="button_containerform">
				
				<html:button property="Submit" styleClass="btnintform" disabled="false" styleId="btnSubmit"
					onclick="submitBaRateConfiguration();" title="Submit"> <bean:message key="button.submit" />
				</html:button>
				<html:button property="Cancel" styleClass="btnintform" disabled="false"	
					styleId="cancelBaRateConfiguration" onclick="cancelConfiguration();"
					title="Cancel">
					<bean:message key="button.cancel" />
				</html:button>	
				</div><!-- Button ends -->
				
				<!-- Hidden Fields START -->
				<html:hidden styleId="headerId" property="to.headerId" />
				<input type="hidden" id="priorityIndicator" name="to.priorityIndicator" value=""> </input>
				<input type="hidden" id="isPriorityProduct" name="to.isPriorityProduct" value=""> </input>
				
				<!-- Courier hidden fields  -->
			    <input type="hidden" id="courierSplDestcoloumCount" name="to.baCourierSlabRateTO.courierSplDestcoloumCount" value="${courierSplDestcoloumCount}" /> 
				<input type="hidden" id="courierProductCategoryId" name="to.baCourierSlabRateTO.courierProductCategoryId" value="${courierProductCategoryId}" />
				<input type="hidden" id="productCategoryIdForCourier" name="to.productCategoryIdForCourier" value="${courierProductCategoryId}" />
				<input type="hidden" id="courierProductHeaderId" name="to.courierProductHeaderId" /> 
				
				<!-- Train hidden fields  -->
				<input type="hidden" id="trainSplDestcoloumCount" name="to.baTrainSlabRateTO.courierSplDestcoloumCount" value="${trainSplDestcoloumCount}" />
				<input type="hidden" id="trainProductCategoryId" name="to.baTrainSlabRateTO.courierProductCategoryId" value="${trainProductCategoryId}" />
				<input type="hidden" id="productCategoryIdForTrain" name="to.productCategoryIdForTrain" value="${trainProductCategoryId}" /> 
				<input type="hidden" id="trainProductHeaderId" name="to.trainProductHeaderId" />
				
				<!-- Air-Cargo hidden fields  -->
				<input type="hidden" id="airCargoSplDestcoloumCount" name="to.baAirCargoSlabRateTO.courierSplDestcoloumCount" value="${airCargoSplDestcoloumCount}" />				
				<input type="hidden" id="airCargoProductCategoryId" name="to.baAirCargoSlabRateTO.courierProductCategoryId" value="${airCargoProductCategoryId}" />
				<input type="hidden" id="productCategoryIdForAirCargo" name="to.productCategoryIdForAirCargo" value="${airCargoProductCategoryId}" /> 
				<input type="hidden" id="airCargoProductHeaderId" name="to.airCargoProductHeaderId" />
				
				<!-- Priority hidden fields  -->
				<input type="hidden" id="prioritySplDestcoloumCount" name="to.baPrioritySlabRateTO.prioritySplDestcoloumCount" value="${prioritySplDestcoloumCount}" />
				<input type="hidden" id="productCategoryIdForPriority" name="to.productCategoryIdForPriority" value="${priorityProductCategoryId}" />
				<input type="hidden" id="priorityProductHeaderId" name="to.priorityProductHeaderId" />
				
				<input type="hidden" id="headerStatus" name="to.headerStatus" />
				<input type="hidden" id="isCourierProductHeaderSaved" name="to.isCourierProductHeaderSaved" />
				<input type="hidden" id="isPriorityProductHeaderSaved" name="to.isPriorityProductHeaderSaved" />
				<input type="hidden" id="isBAConfigRenew" name="to.isBAConfigRenew" value="" />
				
				<input type="hidden" id="oldHeaderId" name="to.oldHeaderId" value="${oldHeaderId}" />
				<input type="hidden" id="oldfromDate" name="to.oldfromDate" value="${oldfromDate}" />
				<input type="hidden" id="oldtoDate" name="to.oldtoDate" value="${oldtoDate}" />
				<input type="hidden" id="oldCity" name="to.oldCity" value="${oldCity}" />
				<input type="hidden" id="oldbaType" name="to.oldbaType" value="${oldbaType}" />
				<input type="hidden" id="isRenewWindow" name="to.isRenewWindow" value="${isRenewWindow}" />
				<input type="hidden" id="oldRegion" name="to.oldRegion" value="${oldRegion}" />
				
				<!-- Non-priority rates check flags -->
				<html:hidden property="to.courierRatesCheck" styleId="courierRatesCheck" />
				<html:hidden property="to.airCargoRatesCheck" styleId="airCargoRatesCheck" />
				<html:hidden property="to.trainRatesCheck" styleId="trainRatesCheck" />
				<html:hidden property="to.courierChargesCheck" styleId="courierChargesCheck" />
				<html:hidden property="to.courierRTOCheck" styleId="courierRTOCheck" />
				<!-- Priority rates check flags -->
	    		<html:hidden property="to.priorityARatesCheck" styleId="priorityARatesCheck" />
	    		<html:hidden property="to.priorityBRatesCheck" styleId="priorityBRatesCheck" />
	    		<html:hidden property="to.prioritySRatesCheck" styleId="prioritySRatesCheck" />
	    		<html:hidden property="to.priorityChargesCheck" styleId="priorityChargesCheck" />
	    		<html:hidden property="to.priorityRTOCheck" styleId="priorityRTOCheck" />
	    		
	    		<html:hidden property="to.sectorCode" styleId="sectorCode" />
	    		<html:hidden property="to.zeroWeightSlabId" styleId="zeroWeightSlabId" value="${zeroWeightSlabId}" />
				
				<!-- Common Hidden Fields -->
				<html:hidden property="to.commonProdCatCode" styleId="commonProdCatCode" />
				<html:hidden property="to.servicedOnCmn" styleId="servicedOnCmn" />
				
				<!-- Hidden Fields END -->
			
			</div>
		</html:form>
		<!-- main content ends -->

	</div>
	<!--wraper ends-->
</body>
</html>