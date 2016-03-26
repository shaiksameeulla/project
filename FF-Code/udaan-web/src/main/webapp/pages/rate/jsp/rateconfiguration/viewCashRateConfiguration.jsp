<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<!-- tab css--><!--<link rel="stylesheet" href="jquery-tab-ui.css" />--><!--tab css ends-->
<script type="text/javascript" charset="utf-8" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/cashRateConfigHeader.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/courierCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/airCargoCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/trainCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/priorityCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/fixedChargesCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/rtoChargesCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/priorityFixedChargesCashRateConfig.js"></script>
<script type="text/javascript" charset="utf-8" src="pages/rate/js/rateconfiguration/priorityRtoChargesCashRateConfig.js"></script>

<script type="text/javascript" charset="utf-8">
var CASH_RATE_FORM="cashRateConfigurationForm";//Form Id
var TODAY_DATE='${TODAY_DATE}';//Current Date
var YES='${YES}';//Y-YES
var NO='${NO}';//N-NO
var DEST='${DEST}';//D-Destination
var ORIGIN='${ORIGIN}';//D-Origin
var ACTIVE="${ACTIVE}";//A
var INACTIVE="${INACTIVE}";//I
//Product Category Code
var PRO_CODE_COURIER="${PRO_CODE_COURIER}";//CO
var PRO_CODE_AIR_CARGO="${PRO_CODE_AIR_CARGO}";//AR
var PRO_CODE_TRAIN="${PRO_CODE_TRAIN}";//TR
var PRO_CODE_PRIORITY="${PRO_CODE_PRIORITY}";//PR
//Product Category Type
var PRO_CAT_TYPE_N="${PRO_CAT_TYPE_N}";//N-Non-Priority
var PRO_CAT_TYPE_P="${PRO_CAT_TYPE_P}";//P-Priority

//Error Message
var DATA_SAVED = "<bean:message key='CRC003' />";
var DATA_NOT_SAVED = "<bean:message key='CRC002' />";

$(document).ready( function () {
	addDefaultRows();
	_btnDisable("renewBtn1");
	validateRenew();
});

$(document).ready( function () {
    $("#tabs").tabs({disabled: [0,1]});
    $("#tabsnested").tabs({disabled: [0,1,2,3,4]});
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
<!-- DataGrids /-->
<!-- Tabs -->
<!-- <script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
<!-- <link rel="stylesheet" href="/resources/demos/style.css" />  -->
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script type="text/javascript" charset="utf-8">
$(function() {    $( "#tabs" ).tabs();  });  

$(function() {    $( "#tabsnested" ).tabs();  });  

$(function() {    $( "#tabsnested1" ).tabs();  });  
</script>
<!-- Tabs ends /-->
</head>
<body onload="searchCashRateDtls();">
<!--wraper-->
<html:form method="post" styleId="cashRateConfigurationForm">
<div id="wraper">
<div class="clear"></div>
	<!-- main content -->
	<div id="maincontent">
		<div class="mainbody">
			<div class="formbox">
          		<h1><strong><bean:message key="label.Rate.rateCashConfig.title" /></strong></h1>
          		<div class="mandatoryMsgf"><span class="mandatoryf"><bean:message key="symbol.common.star" /></span> <bean:message key="label.Rate.fieldMandatory" /></div>
       		</div>
       		<!-- Rate CASH/ACC Cofiguration Header START -->
        	<div class="formTable">
				<table border="0" cellpadding="0" cellspacing="2" width="100%">
	    			<tr>
	              		<%-- <td width="13%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.fromDt" /></td> --%>
	              			<html:hidden property="to.fromDateStr" styleId="fromDateStr" styleClass="txtbox width100" onfocus="validateFromDate(this);" />
	              		<%-- <td width="17%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.toDt" /></td> --%>
	              			<html:hidden property="to.toDateStr" styleId="toDateStr" styleClass="txtbox width100" onfocus="validateToDate(this);" />
	             		<td width="13%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.Region" /></td>
	              		<td width="24%">
	              			<c:if test="${cashRateConfigurationForm.to.isRenew==YES}">
	              				<c:set var="isDisabledForRenew" scope="request" value="true" />
	              			</c:if>
	              			<html:select property="to.regionId" styleId="regionId" styleClass="selectBox width170" onchange="getOriginSector(this);searchCashRateDtls();">
	                  			<html:option value=""><bean:message key="label.option.select" /></html:option>
	                  			<logic:present name="regions" scope="request">
									<c:forEach var="regionList" items="${regions}">
										<html:option value="${regionList.regionId}">
											<c:out value="${regionList.regionName}"/>
										</html:option>
									</c:forEach>
								</logic:present>
	                		</html:select>
	                	</td>
	            	</tr>
	    			<tr>
	              		<td height="37" class="lable">&nbsp;</td>
	              		<td width="15%">&nbsp;</td>
	              		<td class="lable">&nbsp;</td>
	      		        <td width="15%">&nbsp;</td>
	       		       	<td class="lable">&nbsp;</td>
	       		      	<td width="22%">
	       		      		<c:if test="${cashRateConfigurationForm.to.isRenew!=YES}">
		       		      			<%-- <html:button property="Search" styleId="searchBtn1" styleClass="button" onclick="searchCashRateDtls();">
		       		      			<bean:message key="button.search"/></html:button>&nbsp; --%>
		       		      		<%-- <html:button property="Renew" styleId="renewBtn1" styleClass="button" onclick="renewCashRateConfig();">
		       		      			<bean:message key="button.renew"/></html:button> --%>
		       		      	</c:if>
	       				</td>
	            	</tr>
	  			</table>
       		</div>
       		<!-- Rate CASH/ACC Cofiguration Header END -->
       		
       		<!-- tabs start -->
			<div id="tabs">
				<ul>
         			<li><a href="#tabs-1"><bean:message key="label.Rate.nonPriority"/></a></li>
          			<li><a href="#tabs-2" onclick="priorityStartup();"><bean:message key="label.Rate.priority"/></a></li>
        		</ul>

	        	<div id="tabs-1">
	            	<div id="tabsnested">
	           			<ul>
	                      	<li><a href="#tabs-3"><bean:message key="label.Rate.courier"/></a></li>
	                      	<li><a href="#tabs-4" onclick="airCargoStartup();"><bean:message key="label.Rate.airCg"/></a></li>
	                      	<li><a href="#tabs-5" onclick="trainStartup();"><bean:message key="label.Rate.train"/></a></li>
	                      	<li><a href="#tabs-6" onclick="fixedChrgsStartup();"><bean:message key="label.Rate.fixedChrg"/></a></li>
	                      	<li><a href="#tabs-7" onclick="rtoChrgsStartup();"><bean:message key="label.Rate.RTOChrg"/></a></li>
	                 	</ul>
	                    
	                    <!-- tab 3 begins -->
	                    <div id="tabs-3"><!-- COURIER -->
          				<div class="clear"></div>
         					<div style="width: 97%">
	    						<table cellpadding="0" cellspacing="0" border="0" class="display" id="courierWtSlabTable" width="90%">
	              					<thead>
	        							<tr>
	                              			<th width="25%" align="center"><bean:message key="label.Rate.regWtSlab"/></th>
	                              			<logic:present name="cashWeightSlabListForCourier" scope="request">
												<c:forEach var="rateWtSlab" items="${cashWeightSlabListForCourier}" varStatus="loop">
													<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_COURIER}">
														<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="coProdCatId" value="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request"/>
													</c:if>
												</c:forEach>
											</logic:present>
	                            		</tr>
	      							</thead>
	              					<tbody>
	              						<logic:present name="cashRateSectorsForCourier" scope="request">
											<c:forEach var="rateSector" items="${cashRateSectorsForCourier}" varStatus="loopSec">
												<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_COURIER}">
													<c:if test="${rateSector.sectorType == DEST}">
														<tr class="gradeA">
															<td>
																<c:out value='${rateSector.sectorTO.sectorName}' />
																<html:hidden styleId="sectorNames${coProdCatId}${loopSec.count}" property="to.courierSlabRateTO.sectorNames" 
																	value="${rateSector.sectorTO.sectorName}" />
															</td>
															<logic:present name="cashWeightSlabListForCourier" scope="request">
																<c:forEach var="rateWtSlabs" items="${cashWeightSlabListForCourier}" varStatus="loopWt">
																	<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_COURIER}">
																		<td>
																			<html:text 
																				styleId="slabRates${coProdCatId}${loopSec.count}${loopWt.count}" property="to.courierSlabRateTO.slabRates" value=""
																				styleClass="txtbox width100" maxlength="5" onkeypress="return onlyDecimal(event);" />
																			<html:hidden 
																				styleId="weightSlabIds${coProdCatId}${loopSec.count}${loopWt.count}" property="to.courierSlabRateTO.weightSlabIds" 
																				value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																			<html:hidden 
																				styleId="destSectorIds${coProdCatId}${loopSec.count}${loopWt.count}" property="to.courierSlabRateTO.destSectorIds" 
																				value="${rateSector.sectorTO.sectorId}" />
																			<html:hidden 
																				styleId="slabRateIds${coProdCatId}${loopSec.count}${loopWt.count}" property="to.courierSlabRateTO.slabRateIds" value=""/>
																		</td>
																	</c:if>
																</c:forEach>
															</logic:present>
														</tr>
														<c:set var="coSectorRowCount" value="${loopSec.count}" scope="request" />
													</c:if>
												</c:if>
											</c:forEach>
										</logic:present>
	                        		</tbody>
            					</table>
	    						<br />
	    						<div class="formbox"><!-- COURIER -->
	                          		<h1><strong><bean:message key="label.Rate.SpecialDestination"/></strong></h1>
	            				</div>
	                        	<div>
	                        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="courierSplDestTable" width="100%">
	                    				<thead>
	                              			<tr>
	                                			<th align="center"><bean:message key="label.rate.state"/></th>
	                                			<th align="center"><bean:message key="label.rate.city"/></th>
	                                			<logic:present name="cashWeightSlabListForCourier" scope="request">
													<c:forEach var="rateWtSlab" items="${cashWeightSlabListForCourier}" varStatus="loop">
														<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_COURIER}">
															<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
															</c:if>
															<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
															</c:if>
															
															<c:set var="coSplDestColCount" value="${loop.count}" scope="request" />
														</c:if>
													</c:forEach>
												</logic:present>
	                                   		</tr>
	                            		</thead>
	                    				<tbody>
	                    				
	                            		</tbody>
	                  				</table>
	                  				<!-- Hidden Fields START -->
	                  				<html:hidden styleId="coSplDestColCount" property="to.courierSplDestTO.splDestColCount" value="${coSplDestColCount}" />
	                  				<html:hidden styleId="coSectorRowCount" property="to.courierSlabRateTO.sectorRowCount" value="${coSectorRowCount}" />
	                  				<html:hidden styleId="coProdCatId" property="to.courierProductTO.productId" value="${coProdCatId}"/>
	                  				<html:hidden styleId="coHeaderProMapId" property="to.courierProductTO.headerProductMapId" value=""/>
	                  				<!-- Hidden Fields END -->
		                        	<!-- Button -->
									<div class="button_containerform">
										<%-- <html:button property="Save" styleId="saveBtn1" styleClass="btnintformbigdis" disabled="true" onclick="saveOrUpdateCourierCashRateDtls();">
	       		      						<bean:message key="button.save"/></html:button>
		              					<html:button property="Cancel" styleId="cancelBtn1" styleClass="btnintformbigdis" disabled="true" onclick="clearProductDtls(PRO_CODE_COURIER);">
	       		      						<bean:message key="button.label.clear"/></html:button> --%>
		            				</div>
		    						<!-- Button ends -->
		                   	 	</div>
							</div>
		       			</div>
						<!-- tab 3 ends -->
						
	                	<!-- tabs-4 begins -->
		                <div id="tabs-4" style="width: 97%"><!-- AIR-CARGO -->
			           	 	<div class="formTable">
			               		<table border="0" cellpadding="0" cellspacing="5" width="100%">
			            			<tr>
			                        	<td width="10%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.origin"/></td>
			                            <td width="23%" class="lable1">
											<html:select property="to.airCargoSlabRateTO.originSectorId" styleId="airCargoOriginSectorId" styleClass="selectBox width130" disabled="true">
												<%-- <html:option value=""><bean:message key="label.option.select"/></html:option> --%>
												<logic:present name="originSectorList" scope="request">
													<c:forEach var="rateSector" items="${originSectorList}" varStatus="loop">
														<c:if test="${rateSector.sectorType == ORIGIN}">
															<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}"> 
																<html:option value='${rateSector.sectorTO.sectorId}'>${rateSector.sectorTO.sectorName}</html:option>
															</c:if>
														</c:if>
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
			                	<table cellpadding="0" cellspacing="0" border="0" class="display" id="airCargoWtSlabTable" width="90%">
			                    	<thead>
	        							<tr>
	                              			<th width="25%" align="center"><bean:message key="label.Rate.regWtSlab"/></th>
	                              			<logic:present name="cashWeightSlabListForAir" scope="request">
												<c:forEach var="rateWtSlab" items="${cashWeightSlabListForAir}" varStatus="loop">
													<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}">
														<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="arProdCatId" value="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request"/>
													</c:if>
												</c:forEach>
											</logic:present>
	                            		</tr>
	      							</thead>
	              					<tbody>
	              						<logic:present name="cashRateSectorsForAir" scope="request">
											<c:forEach var="rateSector" items="${cashRateSectorsForAir}" varStatus="loopSec">
												<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}">
													<c:if test="${rateSector.sectorType == DEST}">
														<tr class="gradeA">
															<td>
																<c:out value='${rateSector.sectorTO.sectorName}' />
																<html:hidden styleId="sectorNames${arProdCatId}${loopSec.count}" property="to.airCargoSlabRateTO.sectorNames" 
																	value="${rateSector.sectorTO.sectorName}" />
															</td>
															<logic:present name="cashWeightSlabListForAir" scope="request">
																<c:forEach var="rateWtSlabs" items="${cashWeightSlabListForAir}" varStatus="loopWt">
																	<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}">
																		<td>
																			<html:text 
																				styleId="slabRates${arProdCatId}${loopSec.count}${loopWt.count}" property="to.airCargoSlabRateTO.slabRates" value=""
																				styleClass="txtbox width100" maxlength="5" onkeypress="return onlyDecimal(event);" />
																			<html:hidden 
																				styleId="weightSlabIds${arProdCatId}${loopSec.count}${loopWt.count}" property="to.airCargoSlabRateTO.weightSlabIds" 
																				value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																			<html:hidden 
																				styleId="destSectorIds${arProdCatId}${loopSec.count}${loopWt.count}" property="to.airCargoSlabRateTO.destSectorIds" 
																				value="${rateSector.sectorTO.sectorId}" />
																			<html:hidden 
																				styleId="slabRateIds${arProdCatId}${loopSec.count}${loopWt.count}" property="to.airCargoSlabRateTO.slabRateIds" value=""/>
																		</td>
																	</c:if>
																</c:forEach>
															</logic:present>
														</tr>
														<c:set var="arSectorRowCount" value="${loopSec.count}" scope="request" />
													</c:if>
												</c:if>
											</c:forEach>
										</logic:present>
	                        		</tbody>
								</table>
			                    <br/>
		                    	<div class="formbox"><!-- AIR-CARGO -->
		                        	<h1><strong><bean:message key="label.Rate.SpecialDestination"/></strong></h1>
		            			</div>
		                        <div>
		                        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="airCargoSplDestTable" width="100%">
			                    		<thead>
	                              			<tr>
	                                			<th align="center"><bean:message key="label.rate.state"/></th>
	                                			<th align="center"><bean:message key="label.rate.city"/></th>
	                                			<logic:present name="cashWeightSlabListForAir" scope="request">
													<c:forEach var="rateWtSlab" items="${cashWeightSlabListForAir}" varStatus="loop">
														<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}">
															<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
															</c:if>
															<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
															</c:if>
															<c:set var="arSplDestColCount" value="${loop.count}" scope="request" />
														</c:if>
													</c:forEach>
												</logic:present>
	                                   		</tr>
	                            		</thead>
	                    				<tbody>
	                    				
	                            		</tbody>
			               			</table>
			               			
			               			<!-- Minimum Chargable Weight for AIR-CARGO -->
			               			<div class="formTable">
					               		<table border="0" cellpadding="0" cellspacing="5" width="100%">
					            			<tr>
					                        	<td width="40%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.minChrgWt" /></td>
					                            <td width="60%" class="lable1">
					                                 <html:select styleId="airCargoMinChargeableWeight" property="to.airCargoProductTO.minChargeableWeight" styleClass="selectBox width145">
														<html:option value=""><bean:message key="label.option.select" /></html:option>
														<logic:present name="cashMinChargeableWeightList" scope="request">
															<c:forEach var="rateMinChargWt"	items="${cashMinChargeableWeightList}" varStatus="loop6">
																<c:if test="${rateMinChargWt.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_AIR_CARGO}">
																	<html:option value='${rateMinChargWt.rateMinChargeableWeightId}'>
																		  <c:out value='${rateMinChargWt.minChargeableWeight}' />
																	</html:option>
																</c:if>
															</c:forEach>
														</logic:present>
													</html:select>
												</td>
					                    	</tr>
					               		</table>
				                	</div>
				                	
			               			<!-- Hidden Fields START -->
	                  				<html:hidden styleId="arSplDestColCount" property="to.airCargoSplDestTO.splDestColCount" value="${arSplDestColCount}" />
	                  				<html:hidden styleId="arSectorRowCount" property="to.airCargoSlabRateTO.sectorRowCount" value="${arSectorRowCount}" />
	                  				<html:hidden styleId="arProdCatId" property="to.airCargoProductTO.productId" value="${arProdCatId}"/>
	                  				<html:hidden styleId="arHeaderProMapId" property="to.airCargoProductTO.headerProductMapId" value=""/>
	                  				<!-- Hidden Fields END -->
			                        <!-- Button -->
									<div class="button_containerform">             
			              				<%-- <html:button property="Save" styleId="saveBtn2" styleClass="btnintform" onclick="saveOrUpdateAirCargoCashRateDtls();">
	       		      						<bean:message key="button.save"/></html:button>
		              					<html:button property="Cancel" styleId="cancelBtn2" styleClass="btnintform" onclick="clearProductDtls(PRO_CODE_AIR_CARGO);">
	       		      						<bean:message key="button.cancel"/></html:button> --%>
			            			</div>
			    					<!-- Button ends -->
								</div>
							</div>
						</div>
			            <!-- tabs-4 ends -->
	                    
			            <!-- tabs-5 begins -->
			            <div id="tabs-5" style="width: 97%"><!-- TRAIN -->
			            	<div class="formTable">
			                	<table border="0" cellpadding="0" cellspacing="5" width="100%">
			                  		<tr>
			                        	<td width="10%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.origin"/></td>
			                            <td width="23%" class="lable1" >
			                            	<html:select property="to.trainSlabRateTO.originSectorId" styleId="trainOriginSectorId" styleClass="selectBox width130" disabled="true">
			                            		<%-- <html:option value=""><bean:message key="label.option.select"/></html:option> --%>
												<logic:present name="originSectorList" scope="request">
													<c:forEach var="rateSector" items="${originSectorList}" varStatus="loop">
														<c:if test="${rateSector.sectorType == ORIGIN}">
															<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}"> 
																<html:option value='${rateSector.sectorTO.sectorId}'>${rateSector.sectorTO.sectorName}</html:option>
															</c:if>
														</c:if>
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
			                	<table cellpadding="0" cellspacing="0" border="0" class="display" id="trainWtSlabTable" width="90%">
			                    	<thead>
	        							<tr>
	                              			<th width="25%" align="center"><bean:message key="label.Rate.regWtSlab"/></th>
	                              			<logic:present name="cashWeightSlabListForTrain" scope="request">
												<c:forEach var="rateWtSlab" items="${cashWeightSlabListForTrain}" varStatus="loop">
													<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}">
														<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="trProdCatId" value="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request"/>
													</c:if>
												</c:forEach>
											</logic:present>
	                            		</tr>
	      							</thead>
	              					<tbody>
	              						<logic:present name="cashRateSectorsForTrain" scope="request">
											<c:forEach var="rateSector" items="${cashRateSectorsForTrain}" varStatus="loopSec">
												<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}">
													<c:if test="${rateSector.sectorType == DEST}">
														<tr class="gradeA">
															<td>
																<c:out value='${rateSector.sectorTO.sectorName}' />
																<html:hidden styleId="sectorNames${trProdCatId}${loopSec.count}" property="to.trainSlabRateTO.sectorNames" 
																	value="${rateSector.sectorTO.sectorName}" />
															</td>
															<logic:present name="cashWeightSlabListForTrain" scope="request">
																<c:forEach var="rateWtSlabs" items="${cashWeightSlabListForTrain}" varStatus="loopWt">
																	<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}">
																		<td>
																			<html:text 
																				styleId="slabRates${trProdCatId}${loopSec.count}${loopWt.count}" property="to.trainSlabRateTO.slabRates" value=""
																				styleClass="txtbox width100" maxlength="5" onkeypress="return onlyDecimal(event);" />
																			<html:hidden 
																				styleId="weightSlabIds${trProdCatId}${loopSec.count}${loopWt.count}" property="to.trainSlabRateTO.weightSlabIds" 
																				value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																			<html:hidden 
																				styleId="destSectorIds${trProdCatId}${loopSec.count}${loopWt.count}" property="to.trainSlabRateTO.destSectorIds" 
																				value="${rateSector.sectorTO.sectorId}" />
																			<html:hidden 
																				styleId="slabRateIds${trProdCatId}${loopSec.count}${loopWt.count}" property="to.trainSlabRateTO.slabRateIds" value=""/>
																		</td>
																	</c:if>
																</c:forEach>
															</logic:present>
														</tr>
														<c:set var="trSectorRowCount" value="${loopSec.count}" scope="request" />
													</c:if>
												</c:if>
											</c:forEach>
										</logic:present>
	                        		</tbody>
								</table>
			                    <br />
			                    <div class="formbox"><!-- TRAIN -->
			                    	<h1><strong><bean:message key="label.Rate.SpecialDestination"/></strong></h1>
			            		</div>
			                    <div>
			                    	<table cellpadding="0" cellspacing="0" border="0" class="display" id="trainSplDestTable" width="100%">
			                    		<thead>
	                              			<tr>
	                                			<th align="center"><bean:message key="label.rate.state"/></th>
	                                			<th align="center"><bean:message key="label.rate.city"/></th>
	                                			<logic:present name="cashWeightSlabListForTrain" scope="request">
													<c:forEach var="rateWtSlab" items="${cashWeightSlabListForTrain}" varStatus="loop">
														<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}">
															<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
															</c:if>
															<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
															</c:if>
															<c:set var="trSplDestColCount" value="${loop.count}" scope="request" />
														</c:if>
													</c:forEach>
												</logic:present>
	                                   		</tr>
	                            		</thead>
	                    				<tbody>
	                    				
	                            		</tbody>
			               			</table>
				               		
				               		<!-- Minimum Chargable Weight for TRAIN -->
				               		<div class="formTable">
					               		<table border="0" cellpadding="0" cellspacing="0" width="100%">
					            			<tr>
					                        	<td width="40%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.minChrgWt" /></td>
					                            <td width="60%" class="lable1">
					                                 <html:select styleId="trainMinChargeableWeight" property="to.trainProductTO.minChargeableWeight" styleClass="selectBox width145">
														<html:option value=""><bean:message key="label.option.select" /></html:option>
														<logic:present name="cashMinChargeableWeightList" scope="request">
															<c:forEach var="rateMinChargWt"	items="${cashMinChargeableWeightList}" varStatus="loop6">
																<c:if test="${rateMinChargWt.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_TRAIN}">
																	<html:option value='${rateMinChargWt.rateMinChargeableWeightId}'>
																		  <c:out value='${rateMinChargWt.minChargeableWeight}' />
																	</html:option>
																</c:if>
															</c:forEach>
														</logic:present>
													</html:select>
												</td>
					                          
					                    	</tr>
					               		</table>
					               	</div>
				                	
			               			<!-- Hidden Fields START -->
	                  				<html:hidden styleId="trSplDestColCount" property="to.trainSplDestTO.splDestColCount" value="${trSplDestColCount}" />
	                  				<html:hidden styleId="trSectorRowCount" property="to.trainSlabRateTO.sectorRowCount" value="${trSectorRowCount}" />
	                  				<html:hidden styleId="trProdCatId" property="to.trainProductTO.productId" value="${trProdCatId}"/>
	                  				<html:hidden styleId="trHeaderProMapId" property="to.trainProductTO.headerProductMapId" value=""/>
			                        <!-- Button -->
									<div class="button_containerform">             
			              				<%-- <html:button property="Save" styleId="saveBtn3" styleClass="btnintform" onclick="saveOrUpdateTrainCashRateDtls();">
	       		      						<bean:message key="button.save"/></html:button>
		              					<html:button property="Cancel" styleId="cancelBtn3" styleClass="btnintform" onclick="clearProductDtls(PRO_CODE_TRAIN);">
	       		      						<bean:message key="button.cancel"/></html:button> --%>
			            			</div>
			    					<!-- Button ends -->
								</div>
							</div>
						</div>
			            <!-- tabs-5 ends -->
	            
			            <!--tabs-6 begins-->
			            <div id="tabs-6"><!-- Non-Priority Fixed Charges -->                 
			            	<div class="formTable">
			                	<table border="0" cellpadding="0" cellspacing="2" width="100%">
			                    	<tr>
			                			<td width="20%" class="lable1">
			                				<html:checkbox property="to.fixedChargesTO.fuelSurchargeChk" styleId="fuelSurchargeChk" styleClass="checkbox" tabindex="-1" />
			                				<bean:message key="label.Rate.FuelSurcharges"/>
			                			</td>
			               				<td width="17%" class="lable1">
			               					<html:text property="to.fixedChargesTO.fuelSurcharge" styleId="fuelSurcharge" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			               					<%-- <html:hidden property="to.fixedChargesTO.fuelSurchargeId" styleId="fuelSurchargeId" value="" /> --%>
			               					&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			               				</td>
			                			<td width="17%" class="lable1">
			                				<html:checkbox property="to.fixedChargesTO.otherChargesChk" styleId="otherChargesChk" styleClass="checkbox" tabindex="-1" />
			                				<bean:message key="label.Rate.OtherCharges"/>
			                			</td>
			                			<td width="14%" class="lable1">
			                				<html:text property="to.fixedChargesTO.otherCharges" styleId="otherCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                				<%-- <html:hidden property="to.fixedChargesTO.otherChargesId" styleId="otherChargesId" value="" /> --%>
			                				&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
			                			</td>
			                			<td width="32%" rowspan="6" valign="top">
			                            	<table width="100%" border="0" cellpadding="3" cellspacing="3">
			                                	<tr>
			                                    	<td width="60%" class="lable1">
			                                    		<html:checkbox property="to.fixedChargesTO.serviceTaxChk" styleId="serviceTaxChk" onchange="checkTax('serviceTaxChk');" styleClass="checkbox" tabindex="-1" />
			                                    		<bean:message key="label.Rate.ServiceTax"/>
			                                    	</td>
			                                      	<td width="40%" class="lable1">
			                                      		<html:text property="to.fixedChargesTO.serviceTax" styleId="serviceTax" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
			                                      		<%-- <html:hidden property="to.fixedChargesTO.serviceTaxId" styleId="serviceTaxId" value="" /> --%>
			                                      		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                                      	</td>
			                                  	</tr>
			                                  	<tr>
			                                      	<td class="lable1">
			                                      		<html:checkbox property="to.fixedChargesTO.eduCessChk" styleId="eduCessChk" styleClass="checkbox" onchange="checkTax('eduCessChk');" tabindex="-1" />
			                                      		<bean:message key="label.Rate.EducationCess"/>
			                                      	</td>
			                                      	<td class="lable1">
			                                      		<html:text property="to.fixedChargesTO.eduCess" styleId="eduCess" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
			                                      		<%-- <html:hidden property="to.fixedChargesTO.eduCessId" styleId="eduCessId" value="" /> --%>
			                                      		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                                      	</td>
			                                  	</tr>
			                                  	<tr>
			                                      	<td class="lable1">
			                                      		<html:checkbox property="to.fixedChargesTO.higherEduCessChk" styleId="higherEduCessChk" styleClass="checkbox" onchange="checkTax('higherEduCessChk');" tabindex="-1" />
			                                      		<bean:message key="label.Rate.HigherEducationCess"/>
			                                      	</td>
			                                      	<td class="lable1">
			                                      		<html:text property="to.fixedChargesTO.higherEduCess" styleId="higherEduCess" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
			                                      		<%-- <html:hidden property="to.fixedChargesTO.higherEduCessId" styleId="higherEduCessId" value="" /> --%>
			                                      		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                                      	</td>
			                                  	</tr>
			                                  	<tr>
			                                    	<td class="lable1">
			                                    		<html:checkbox property="to.fixedChargesTO.stateTaxChk" styleId="stateTaxChk" styleClass="checkbox" onchange="checkSTTax('stateTaxChk');" tabindex="-1" />
			                                    		<bean:message key="label.Rate.StateTax"/>
			                                    	</td>
			                                      	<td class="lable1">
			                                      		<html:text property="to.fixedChargesTO.stateTax" styleId="stateTax" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
			                                      		<%-- <html:hidden property="to.fixedChargesTO.stateTaxId" styleId="stateTaxId" value="" /> --%>
			                                      		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                                      	</td>
			                                  	</tr>
			                                   	<tr>
			                                      	<td class="lable1">
			                                      		<html:checkbox property="to.fixedChargesTO.surchargeOnSTChk" styleId="surchargeOnSTChk" styleClass="checkbox" onchange="checkSTTax('surchargeOnSTChk');" tabindex="-1" />
			                                      		<bean:message key="label.Rate.SurchargeOnS"/>
			                                      	</td>
			                                      	<td class="lable1">
			                                      		<html:text property="to.fixedChargesTO.surchargeOnST" styleId="surchargeOnST" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
			                                      		<%-- <html:hidden property="to.fixedChargesTO.surchargeOnSTId" styleId="surchargeOnSTId" value="" /> --%>
			                                      		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                                      	</td>
			                                  	</tr>
			               			 		</table>
			                       		</td>
			           				</tr>
			                   		<tr>
			                        	<td class="lable1">
			                        		<bean:message key="label.Rate.ifInsrdBy"/>
		                        		</td>
			                            <td class="lable1">
			                            	<span class="lable1"><bean:message key="label.Rate.FFCL"/></span>&nbsp;
			                            	<html:text property="to.fixedChargesTO.ifInsuredByFFCL" styleId="ifInsuredByFFCL" styleClass="txtbox width30" value="${FFCL_PERCENTILE}" readonly="true" tabindex="-1" />
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/>
		                            	</span>
			                            </td>
		                            	<td class="lable1">
		                            		<html:checkbox property="to.fixedChargesTO.octroiBourneByChk" styleId="octroiBourneByChk" styleClass="checkbox" tabindex="-1" />
		                            		<bean:message key="label.Rate.OctroiBornBy"/>
	                            		</td>
			                            <td class="lable1">
			                            	<html:select property="to.fixedChargesTO.octroiBourneBy" styleId="octroiBourneBy" styleClass="selectBox width130">
												<logic:present name="octroiBornBy" scope="request">
													<c:forEach var="list" items="${octroiBornBy}">
														<html:option value="${list.stdTypeCode}">
															<c:out value="${list.description}" />
														</html:option>
													</c:forEach>
												</logic:present>
											</html:select>
		                            	</td>
									</tr>
			                        <tr>
			                        	<td class="lable1">&nbsp;</td>
			                            <td class="lable1">
			                            	<span class="lable1"><bean:message key="label.Rate.customer"/></span>&nbsp;
			                            	<html:text property="to.fixedChargesTO.ifInsuredByCustomer" styleId="ifInsuredByCustomer" styleClass="txtbox width30" value="${CUST_PERCENTILE}" readonly="true" tabindex="-1" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			                            </td>
			                            <td class="lable1">
			                            	<html:checkbox property="to.fixedChargesTO.octroiServiceChargesChk" styleId="octroiServiceChargesChk" styleClass="checkbox" tabindex="-1"/>
			                            	<bean:message key="label.Rate.OctroiServiceCharges"/>
		                            	</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.octroiServiceCharges" styleId="octroiServiceCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.octroiServiceChargesId" styleId="octroiServiceChargesId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
		                            	</td>
			                 		</tr>
			                        <tr>
			                        	<td class="lable1">
			                        		<html:checkbox property="to.fixedChargesTO.parcelChargesChk" styleId="parcelChargesChk" styleClass="checkbox" tabindex="-1"/>
			                        		<bean:message key="label.Rate.ParcelHandlingCharges"/>
		                        		</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.parcelCharges" styleId="parcelCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.parcelChargesId" styleId="parcelChargesId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
		                            	</td>
			                            <td class="lable1">
			                            	<html:checkbox property="to.fixedChargesTO.toPayChk" styleId="toPayChk" styleClass="checkbox" tabindex="-1"/>
			                            	<bean:message key="label.Rate.toPayChrgs"/>
		                            	</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.toPay" styleId="toPay" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.toPayId" styleId="toPayId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
		                            	</td>
			                      	</tr>
			                        <tr>
			                        	<td class="lable1">
			                        		<html:checkbox property="to.fixedChargesTO.docChargesChk" styleId="docChargesChk" styleClass="checkbox" tabindex="-1"/>
			                        		<bean:message key="label.Rate.DocumentHandlingCharges"/>
		                        		</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.docCharges" styleId="docCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.docChargesId" styleId="docChargesId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
		                            	</td>
			                            <td class="lable1">
			                            	<html:checkbox property="to.fixedChargesTO.lcChargesChk" styleId="lcChargesChk" styleClass="checkbox" tabindex="-1"/>
			                            	<bean:message key="label.Rate.LCCharges"/>
		                            	</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.lcCharges" styleId="lcCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.lcChargesId" styleId="lcChargesId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
		                            	</td>
			                     	</tr>
			                       	<tr>
			                        	<td height="28" class="lable1">
			                        		<html:checkbox property="to.fixedChargesTO.airportChargesChk" styleId="airportChargesChk" styleClass="checkbox" tabindex="-1"/>
			                        		<bean:message key="label.Rate.AirportHandlingCharges"/>
		                        		</td>
			                            <td class="lable1">
			                            	<html:text property="to.fixedChargesTO.airportCharges" styleId="airportCharges" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			                            	<%-- <html:hidden property="to.fixedChargesTO.airportChargesId" styleId="airportChargesId" value="" /> --%>
			                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
		                            	</td>
			                            <td class="lable1" valign="top">&nbsp;</td>
			                            <td>&nbsp;</td>               
			                      	</tr>
			                   	</table>
			                   	<!-- Hidden Fields START -->
			                   	<html:hidden property="to.fixedChargesTO.priorityInd" styleId="priorityIndFixedChrg" value="${NO}" />
			                   	<!-- Hidden Fields END -->
			            		<!-- Button -->
			            		<div class="button_containerform">
	       		      				<%-- <html:button property="Save" styleId="saveBtn4" styleClass="btnintform" onclick="saveOrUpdateFixedChrgsDtls();">
	       		      					<bean:message key="button.save"/></html:button>
	       		      				<html:button property="Cancel" styleId="cancelBtn4" styleClass="btnintform" onclick="fixedChrgsStartup();">
	       		      					<bean:message key="button.cancel"/></html:button> --%>
			               		</div>
			            		<!-- Button ends -->                     
							</div>
						</div>
	            		<!--tabs-6 ends-->
	            		
			            <!--tabs-7 begins-->
			            <div id="tabs-7"><!-- Non-Priority RTO Charges -->
			            	<table border="0" cellpadding="0" cellspacing="5" width="100%">
			            		<tr>
			                    	<td width="18%" class="lable">
			                    		<html:checkbox property="to.rtoChargesTO.rtoChargeApplicableChk" styleId="rtoChargeApplicableChk" styleClass="checkbox" tabindex="-1" onclick="validateRTOChrgs(PRO_CAT_TYPE_N);" />
			                    		<bean:message key="label.Rate.RTOChargesApplicable"/>
			                    	</td>
			                      	<%-- <td width="15%">
			                      		<html:select property="to.rtoChargesTO.rtoChargeApplicable" styleId="rtoChargeApplicable" styleClass="selectBox">
			                        		<html:option value="Y">Yes</html:option>
			                          		<html:option value="N">No</html:option>
			                        	</html:select>
			                        </td> --%>
			                      	<td width="15%" class="lable">
			                      		<html:checkbox property="to.rtoChargesTO.sameAsSlabRateChk" styleId="sameAsSlabRateChk" styleClass="checkbox" disabled="true" tabindex="-1" onclick="validateRTOChrgs(PRO_CAT_TYPE_N);" />
			                      		<bean:message key="label.Rate.IfYes"/>
			                      	</td>
			                      	<td width="17%" class="lable1">
			                      		<select name="select13" class="selectBox" tabindex="-1" disabled="disabled">
			                        		<option value="0">Same As Tariff No.</option>
			                        	</select>
			                        </td>
			                    	<td width="15%" class="lable">
			                    		<bean:message key="label.Rate.Discount"/>
			                    	</td>
			            			<td width="21%" class="lable1">
			            				<html:text property="to.rtoChargesTO.discountOnSlab" styleId="discountOnSlab" styleClass="txtbox width70" disabled="true" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
			            				&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
			            			</td>
			                    </tr>
			          		</table>
			                <div class="clear"></div>
			                <!-- Hidden Fields START -->
			                <html:hidden property="to.rtoChargesTO.coRTOChargesId" styleId="coRTOChargesId" value="" />
			                <html:hidden property="to.rtoChargesTO.arRTOChargesId" styleId="arRTOChargesId" value="" />
			                <html:hidden property="to.rtoChargesTO.trRTOChargesId" styleId="trRTOChargesId" value="" />
			                <html:hidden property="to.rtoChargesTO.priorityInd" styleId="priorityIndRto" value="${NO}" />
			                <!-- Hidden Fields END -->
			                <!-- Button -->
			                <div class="button_containerform">
	       		      			<%-- <html:button property="Save" styleId="saveBtn5" styleClass="btnintform" onclick="saveOrUpdateRTOChrgsDtls();">
	       		      				<bean:message key="button.save"/></html:button>
	       		      			<html:button property="Cancel" styleId="cancelBtn5" styleClass="btnintform" onclick="rtoChrgsStartup();">
	       		      				<bean:message key="button.cancel"/></html:button> --%>
			                </div>
			                <!-- Button ends --> 
			       		</div>
			            <!--tabs-7 ends-->                  
	                    
					</div><!-- tab nested ends -->
				</div><!-- tabs-1 ends-->
    
			    <!-- tabs-2 begins-->
				<div id="tabs-2">
			    	<div id="tabsnested1">
			        	<ul>
			            	<li><a href="#tabs-8"><bean:message key="label.Rate.rates"/></a></li>
			                <li><a href="#tabs-9" onclick="priorityFixedChrgsStartup();"><bean:message key="label.Rate.fixedChrg"/></a></li>
			                <li><a href="#tabs-10" onclick="priorityRtoChrgsStartup();"><bean:message key="label.Rate.RTOChrg"/></a></li>
						</ul>
						<!-- tabs-8 begins -->
	            		<div id="tabs-8" style="width: 97%"><!-- PRIORITY -->
			            	<div class="formTable">
			                	<table border="0" cellpadding="0" cellspacing="2" width="100%">
			                    	<tr>
			                    		<td width="10%" class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup>&nbsp;<bean:message key="label.Rate.servicedAt"/></td>
			                    		<td width="23%" class="lable1">
				                    		<html:select property="to.servicedOn" styleId="priorityservicedOn" styleClass="selectBox width130" onchange="getPrioroityRates();">
												<%-- <html:option value=""><bean:message key="label.option.select"/></html:option> --%>
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
								<table cellpadding="0" cellspacing="0" border="0" class="display" id="priorityWtSlabTable" width="90%">
									<thead>
	        							<tr>
	                              			<th width="25%" align="center"><bean:message key="label.Rate.regWtSlab"/></th>
	                              			<logic:present name="cashWeightSlabListForPriority" scope="request">
												<c:forEach var="rateWtSlab" items="${cashWeightSlabListForPriority}" varStatus="loop">
													<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_PRIORITY}">
														<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
														</c:if>
														<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
															<th width="10%" align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
														</c:if>
														<c:set var="prProdCatId" value="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId}" scope="request"/>
													</c:if>
												</c:forEach>
											</logic:present>
	                            		</tr>
	      							</thead>
	              					<tbody>
	              						<logic:present name="cashRateSectorsForPriority" scope="request">
											<c:forEach var="rateSector" items="${cashRateSectorsForPriority}" varStatus="loopSec">
												<c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_PRIORITY}">
													<c:if test="${rateSector.sectorType == DEST}">
														<tr class="gradeA">
															<td>
																<c:out value='${rateSector.sectorTO.sectorName}' />
																<html:hidden styleId="sectorNames${prProdCatId}${loopSec.count}" property="to.prioritySlabRateTO.sectorNames" 
																	value="${rateSector.sectorTO.sectorName}" />
															</td>
															<logic:present name="cashWeightSlabListForPriority" scope="request">
																<c:forEach var="rateWtSlabs" items="${cashWeightSlabListForPriority}" varStatus="loopWt">
																	<c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_PRIORITY}">
																		<td>
																			<html:text 
																				styleId="slabRates${prProdCatId}${loopSec.count}${loopWt.count}" property="to.prioritySlabRateTO.slabRates" value=""
																				styleClass="txtbox width100" maxlength="5" onkeypress="return onlyDecimal(event);" />
																			<html:hidden 
																				styleId="weightSlabIds${prProdCatId}${loopSec.count}${loopWt.count}" property="to.prioritySlabRateTO.weightSlabIds" 
																				value="${rateWtSlabs.weightSlabTO.weightSlabId}" />
																			<html:hidden 
																				styleId="destSectorIds${prProdCatId}${loopSec.count}${loopWt.count}" property="to.prioritySlabRateTO.destSectorIds" 
																				value="${rateSector.sectorTO.sectorId}" />
																			<html:hidden 
																				styleId="slabRateIds${prProdCatId}${loopSec.count}${loopWt.count}" property="to.prioritySlabRateTO.slabRateIds" value=""/>
																		</td>
																	</c:if>
																</c:forEach>
															</logic:present>
														</tr>
														<c:set var="prSectorRowCount" value="${loopSec.count}" scope="request" />
													</c:if>
												</c:if>
											</c:forEach>
										</logic:present>
	                        		</tbody>
								</table>
			          			<br />
					          	<div class="formbox"><!-- PRIORITY -->
					            	<h1><strong><bean:message key="label.Rate.SpecialDestination"/></strong></h1>
					           	</div>
					            <div>
					            	<table cellpadding="0" cellspacing="0" border="0" class="display" id="prioritySplDestTable" width="100%">
					                	<thead>
	                              			<tr>
	                                			<th align="center"><bean:message key="label.rate.state"/></th>
	                                			<th align="center"><bean:message key="label.rate.city"/></th>
	                                			<logic:present name="cashWeightSlabListForPriority" scope="request">
													<c:forEach var="rateWtSlab" items="${cashWeightSlabListForPriority}" varStatus="loop">
														<c:if test="${rateWtSlab.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == PRO_CODE_PRIORITY}">
															<c:if test="${rateWtSlab.weightSlabTO.additional != YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel} - ${rateWtSlab.weightSlabTO.endWeightLabel}</th>
															</c:if>
															<c:if test="${rateWtSlab.weightSlabTO.additional == YES}">
																<th align="center">${rateWtSlab.weightSlabTO.startWeightLabel}</th>
															</c:if>
															<c:set var="prSplDestColCount" value="${loop.count}" scope="request" />
														</c:if>
													</c:forEach>
												</logic:present>
	                                   		</tr>
	                            		</thead>
	                    				<tbody>
	                    				
	                            		</tbody>
					          		</table>
					                <!-- Hidden Fields START -->
	                  				<html:hidden styleId="prSplDestColCount" property="to.prioritySplDestTO.splDestColCount" value="${prSplDestColCount}" />
	                  				<html:hidden styleId="prSectorRowCount" property="to.prioritySlabRateTO.sectorRowCount" value="${prSectorRowCount}" />
	                  				<html:hidden styleId="prProdCatId" property="to.priorityProductTO.productId" value="${prProdCatId}"/>
	                  				<html:hidden styleId="prHeaderProMapId" property="to.priorityProductTO.headerProductMapId" value=""/>          
					               	<!-- Button -->
					                <div class="button_containerform">
					                	<%-- <html:button property="Save" styleId="saveBtn6" styleClass="btnintform" onclick="saveOrUpdatePriorityCashRateDtls();">
	       		      						<bean:message key="button.save"/></html:button>
	       		      					<html:button property="Cancel" styleId="cancelBtn6" styleClass="btnintform" onclick="clearProductDtls(PRO_CODE_PRIORITY);">
	       		      						<bean:message key="button.cancel"/></html:button> --%>
					              	</div>
					                <!-- Button ends --> 
					          	</div>
							</div>
						</div>
						<!-- tabs-8 ends -->
						
						<!-- tabs-9 begins -->
						<div id="tabs-9"><!-- Priority Fixed Charges -->
							<table border="0" cellpadding="0" cellspacing="2" width="100%">
					        	<tr>
					            	<td width="18%" height="22" class="lable1">
					            		<html:checkbox property="to.priorityFixedChargesTO.fuelSurchargeChk" styleId="fuelSurchargeChkPR" styleClass="checkbox" tabindex="-1" />
					            		<bean:message key="label.Rate.FuelSurcharges"/>
					            	</td>
					                <td width="17%" class="lable1">
					                	<html:text property="to.priorityFixedChargesTO.fuelSurcharge" styleId="fuelSurchargePR" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					                	<%-- <html:hidden property="to.fixedChargesTO.fuelSurcharge" styleId="fuelSurchargeIdPR" value="" /> --%>
					                	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                </td>
					                <td width="18%" class="lable1">
					                	<bean:message key="label.Rate.ifInsrdBy"/>
					                </td>
					                <td class="lable1">
					                	<span class="lable1"><bean:message key="label.Rate.FFCL"/></span>&nbsp;
					                	<html:text property="to.priorityFixedChargesTO.ifInsuredByFFCL" styleId="ifInsuredByFFCLPR" styleClass="txtbox width30" value="${FFCL_PERCENTILE}" readonly="true" tabindex="-1"/>
					                	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                </td>
					                <td width="25%" rowspan="8" valign="top">
					                	<table width="100%" border="0" cellpadding="3" cellspacing="3">
					       					<tr>
					                        	<td width="219" class="lable1">
					                        		<html:checkbox property="to.priorityFixedChargesTO.serviceTaxChk" styleId="serviceTaxChkPR" onchange="checkTaxPR('serviceTaxChkPR');" styleClass="checkbox" tabindex="-1" />
					                        		<bean:message key="label.Rate.ServiceTax"/>
					                        	</td>
					                            <td width="72" class="lable1">
					                            	<html:text property="to.priorityFixedChargesTO.serviceTax" styleId="serviceTaxPR" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
					                            	<%-- <html:hidden property="to.fixedChargesTO.serviceTax" styleId="serviceTaxIdPR" value="" /> --%>
					                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                            </td>
					                      	</tr>
					                        <tr>
					                        	<td class="lable1">
					                        		<html:checkbox property="to.priorityFixedChargesTO.eduCessChk" styleId="eduCessChkPR" styleClass="checkbox" onchange="checkTaxPR('eduCessChkPR');" tabindex="-1"/>
					                        		<bean:message key="label.Rate.EducationCess"/>
					                        	</td>
					                            <td class="lable1">
					                            	<html:text property="to.priorityFixedChargesTO.eduCess" styleId="eduCessPR" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
					                            	<%-- <html:hidden property="to.fixedChargesTO.eduCess" styleId="eduCessIdPR" value="" /> --%>
					                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                            </td>
					                     	</tr>
					                        <tr>
					                        	<td class="lable1" width="219">
					                        		<html:checkbox property="to.priorityFixedChargesTO.higherEduCessChk" styleId="higherEduCessChkPR" onchange="checkTaxPR('higherEduCessChkPR');" styleClass="checkbox" tabindex="-1"/>
					                        		<bean:message key="label.Rate.HigherEducationCess"/>
					                        	</td>
					                           	<td class="lable1">
					                           		<html:text property="to.priorityFixedChargesTO.higherEduCess" styleId="higherEduCessPR" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
					                           		<%-- <html:hidden property="to.fixedChargesTO.higherEduCess" styleId="higherEduCessIdPR" value="" /> --%>
					                           		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                           	</td>
					                    	</tr>
					                        <tr>
					                        	<td class="lable1">
					                        		<html:checkbox property="to.priorityFixedChargesTO.stateTaxChk" styleId="stateTaxChkPR" styleClass="checkbox" onchange="checkSTTaxPR('stateTaxChkPR');" tabindex="-1"/>
					                        		<bean:message key="label.Rate.StateTax"/>
					                        	</td>
					                            <td class="lable1">
					                            	<html:text property="to.priorityFixedChargesTO.stateTax" styleId="stateTaxPR" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
					                            	<%-- <html:hidden property="to.fixedChargesTO.stateTax" styleId="stateTaxIdPR" value="" /> --%>
					                            	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                            </td>
					                        </tr>
					                        <tr>
					                        	<td class="lable1">
					                        		<html:checkbox property="to.priorityFixedChargesTO.surchargeOnSTChk" styleId="surchargeOnSTChkPR" styleClass="checkbox" onchange="checkSTTaxPR('surchargeOnSTChkPR');" tabindex="-1"/>
					                        		<bean:message key="label.Rate.SurchargeOnS"/>
					                        	</td>
					                           	<td class="lable1">
					                           		<html:text property="to.priorityFixedChargesTO.surchargeOnST" styleId="surchargeOnSTPR" styleClass="txtbox width30" maxlength="5" readonly="true" tabindex="-1" onkeypress="return onlyDecimal(event);"/>
					                           		<%-- <html:hidden property="to.fixedChargesTO.surchargeOnST" styleId="surchargeOnSTIdPR" value="" /> --%>
					                           		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                           	</td>
					                      	</tr>
					               		</table>
					               	</td>
					    		</tr>
					    		<tr>
					    			<td colspan="3">&nbsp;</td>
					    			<td class="lable1">
					    				<span class="lable1"><bean:message key="label.Rate.customer"/></span>&nbsp;
					    				<html:text property="to.priorityFixedChargesTO.ifInsuredByCustomer" styleId="ifInsuredByCustomerPR" styleClass="txtbox width30" value="${CUST_PERCENTILE}" readonly="true" tabindex="-1" />
					    				&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					    			</td>
					    			<td>&nbsp;</td>
					    		</tr>
					            <tr>
					          		<td class="lable1">
					          			<html:checkbox property="to.priorityFixedChargesTO.otherChargesChk" styleId="otherChargesChkPR" styleClass="checkbox" tabindex="-1"/>
					          			<bean:message key="label.Rate.OtherCharges"/>
					          		</td>
					                <td class="lable1">
					                	<html:text property="to.priorityFixedChargesTO.otherCharges" styleId="otherChargesPR" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					                	<%-- <html:hidden property="to.fixedChargesTO.otherCharges" styleId="otherChargesIdPR" value="" /> --%>
					                	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
					                </td>
					                <td class="lable1">
					                	<html:checkbox property="to.priorityFixedChargesTO.parcelChargesChk" styleId="parcelChargesChkPR" styleClass="checkbox" tabindex="-1"/>
					                	<bean:message key="label.Rate.ParcelHandlingCharges"/>
					                </td>
					                <td width="22%" class="lable1">
					                	<html:text property="to.priorityFixedChargesTO.parcelCharges" styleId="parcelChargesPR" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					                	<%-- <html:hidden property="to.fixedChargesTO.parcelCharges" styleId="parcelChargesIdPR" value="" /> --%>
					                	&nbsp;<span class="lable1"><bean:message key="label.Rate.Rupees"/></span>
					                </td>
					          	</tr>
					            <tr>
					               	<td class="lable1">
					               		<html:checkbox property="to.priorityFixedChargesTO.octroiBourneByChk" styleId="octroiBourneByChkPR" styleClass="checkbox" tabindex="-1"/>
					               		<bean:message key="label.Rate.OctroiBornBy"/>
				               		</td>
					               	<td class="lable1">
					               		<html:select property="to.priorityFixedChargesTO.octroiBourneBy" styleId="octroiBourneByPR" styleClass="selectBox width130">
											<logic:present name="octroiBornBy" scope="request">
												<c:forEach var="list" items="${octroiBornBy}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select>
					              	</td>
					               	<td class="lable1">
					               		<html:checkbox property="to.priorityFixedChargesTO.octroiServiceChargesChk" styleId="octroiServiceChargesChkPR" styleClass="checkbox" tabindex="-1"/>
					               		<bean:message key="label.Rate.OctroiServiceCharges"/>
				               		</td>
					                <td class="lable1">
					                	<html:text property="to.priorityFixedChargesTO.octroiServiceCharges" styleId="octroiServiceChargesPR" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					                	<%-- <html:hidden property="to.fixedChargesTO.octroiServiceCharges" styleId="octroiServiceChargesIdPR" value="" /> --%>
					                	&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					                </td>
					        	</tr>
					          	<tr>
					            	<td class="lable1">
					            		<html:checkbox property="to.priorityFixedChargesTO.airportChargesChk" styleId="airportChargesChkPR" styleClass="checkbox" tabindex="-1"/>
					            		<bean:message key="label.Rate.AirportHandlingCharges"/>
				            		</td>
					              	<td class="lable1">
					              		<html:text property="to.priorityFixedChargesTO.airportCharges" styleId="airportChargesPR" styleClass="txtbox width30" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					              		<%-- <html:hidden property="to.fixedChargesTO.airportCharges" styleId="airportChargesIdPR" value="" /> --%>
					              		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
					              	</td>
					                <td class="lable1">&nbsp;</td>
					                <td class="lable1">&nbsp;</td>
					          	</tr>
					            <tr>
						            <td class="lable">&nbsp;</td>
						            <td class="lable1">&nbsp;</td>
						            <td class="lable1">&nbsp;</td>
						            <td class="lable1">&nbsp;</td>
					           	</tr>
					       	</table>
					       	<!-- Hidden Fields START -->
		                   	<html:hidden property="to.priorityFixedChargesTO.priorityInd" styleId="priorityIndFixedChrgPR" value="${YES}" />
		                   	<!-- Hidden Fields END -->
					        <!-- Button -->
					        <div class="button_containerform">
					        	<%-- <html:button property="Save" styleId="saveBtn7" styleClass="btnintform" onclick="saveOrUpdatePriorityFixedChrgsDtls();">
	       		      				<bean:message key="button.save"/></html:button>
	       		      			<html:button property="Cancel" styleId="cancelBtn7" styleClass="btnintform" onclick="priorityFixedChrgsStartup();">
	       		      				<bean:message key="button.cancel"/></html:button> --%>
					       	</div>
					      	<!-- Button ends --> 
						</div>
					   	<!-- tab 9 ends -->
            	
					  	<!-- tabs-10 begins -->
						<div id="tabs-10"><!-- Priority RTO Charges -->
					    	<table border="0" cellpadding="0" cellspacing="5" width="100%">
					        	<tr>
					         		<td width="18%" class="lable">
					         			<html:checkbox property="to.priorityRtoChargesTO.rtoChargeApplicableChk" styleId="rtoChargeApplicableChkPR" styleClass="checkbox" tabindex="-1" onclick="validateRTOChrgs(PRO_CAT_TYPE_P);" />
					         			<bean:message key="label.Rate.RTOChargesApplicable"/>
				         			</td>
					            	<%-- <td width="15%">
					            		<html:select property="to.priorityRtoChargesTO.rtoChargeApplicable" styleId="rtoChargeApplicablePR" styleClass="selectBox">
					                   		<html:option value="Y">Yes</html:option>
					                   		<html:option value="N">No</html:option>
					                	</html:select>
					                </td> --%>
					             	<td width="15%" class="lable">
					             		<html:checkbox property="to.priorityRtoChargesTO.sameAsSlabRateChk" styleId="sameAsSlabRateChkPR" styleClass="checkbox" tabindex="-1" onclick="validateRTOChrgs(PRO_CAT_TYPE_P);" />
					             		<bean:message key="label.Rate.IfYes"/>
				             		</td>
					            	<td width="17%" class="lable1">
					            		<select name="select13" class="selectBox" tabindex="-1" disabled="disabled">
					                		<option value="0">Same As Tariff No.</option>
					               	 	</select>
				               	 	</td>
					            	<td width="15%" class="lable">
					            		<bean:message key="label.Rate.Discount"/>
				            		</td>
					            	<td width="21%" class="lable1">
					            		<html:text property="to.priorityRtoChargesTO.discountOnSlab" styleId="discountOnSlabPR" styleClass="txtbox width70" disabled="true" value="" maxlength="5" onkeypress="return onlyDecimal(event);"/>
					            		&nbsp;<span class="lable1"><bean:message key="label.Rate.Percent"/></span>
				            		</td>
					   			</tr>
					   		</table>
					        <div class="clear"></div>
					        <!-- Hidden Fields START -->
					        <html:hidden property="to.priorityRtoChargesTO.prRTOChargesId" styleId="prRTOChargesId" value="" />
					        <html:hidden property="to.priorityRtoChargesTO.priorityInd" styleId="priorityIndRtoPR" value="${YES}" />
					        <!-- Hidden Fields END -->
						    <!-- Button -->
						    <div class="button_containerform">
	       		      			<%-- <html:button property="Save" styleId="saveBtn8" styleClass="btnintform" onclick="saveOrUpdatePriorityRTOChrgsDtls();">
	       		      				<bean:message key="button.save"/></html:button>
	       		      			<html:button property="Cancel" styleId="cancelBtn8" styleClass="btnintform" onclick="priorityRtoChrgsStartup();">
	       		      				<bean:message key="button.cancel"/></html:button> --%>
						    </div>
						    <!-- Button ends --> 
						</div>
						<!-- tabs-10 ends -->
					</div>
	  				<!-- tab nested ends --> 
				</div>
    			<!-- tabs-2 ends -->
    			
    		</div>
    		<!-- tabs ends -->
    		
    		<!-- Hidden Fields START -->
    		<html:hidden property="to.cashRateHeaderId" styleId="cashRateHeaderId" />
    		<html:hidden property="to.headerStatus" styleId="headerStatus" />
    		<html:hidden property="to.productCode" styleId="productCode" />
    		<html:hidden property="to.productCatType" styleId="productCatType" />
    		<html:hidden property="to.prevCashRateHeaderId" styleId="prevCashRateHeaderId" />
    		<html:hidden property="to.isRenew" styleId="isRenew" />
    		
    		<html:hidden property="to.nonPriorityRatesCheck" styleId="nonPriorityRatesCheck" />
    		<html:hidden property="to.priorityRatesCheck" styleId="priorityRatesCheck" />
    		<html:hidden property="to.priorityBRatesCheck" styleId="priorityBRatesCheck" />
    		<html:hidden property="to.prioritySRatesCheck" styleId="prioritySRatesCheck" />
    		<html:hidden property="to.priorityARatesCheck" styleId="priorityARatesCheck" />
    		<html:hidden property="to.nonPriorityChargesCheck" styleId="nonPriorityChargesCheck" />
    		<html:hidden property="to.priorityChargesCheck" styleId="priorityChargesCheck" />
    		<html:hidden property="to.sectorId" styleId="orgSectorId" />
      		<!-- Hidden Fields END -->
    		
		</div>
	    <!-- Button -->
		<div class="button_containerform">
			<%-- <html:button property="Submit" styleId="submitBtn1" styleClass="btnintform" onclick="submitCashRateDtls();">
      					<bean:message key="button.submit"/></html:button>
			<html:button property="Cancel" styleId="cancelBtn9" styleClass="btnintform" onclick="cancelAllCash();">
      					<bean:message key="button.cancel"/></html:button> --%>
		</div>
		<!-- Button ends -->
<!------------------------------------ main ---------------- body ends ------------------------------->
	</div><!-- main content ends --> 
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
