<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Rate Benchmark</title>        
				
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
     
         <!-- tab css<link rel="stylesheet" href="css/jquery-tab-ui.css" /> <!--tab css ends-->
        
		<script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/ratebenchmark.js"></script>
       
		<script type="text/javascript" charset="utf-8">
		var courierCode="";
		var originCode = "";
		var destCode = "";
		var renewCode = "";
		var approveCode = "";
		var editCode = "";
		
			$(document).ready( function () {
				var oTable = $('#example1').dataTable( {
					"sScrollY": "185",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": true,
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
			
			loadValues();	
			
			} );
			
			
			
			
			function loadGlobalValues(){
				courierCode = "<bean:message key="label.rate.CourierCode"/>";
				originCode = "<bean:message key="label.rate.originCode"/>";
				destCode = "<bean:message key="label.rate.destinationCode"/>";
				renewCode = "<bean:message key="label.rate.renewCode"/>";
				approveCode = "<bean:message key="label.rate.approved"/>";
				editCode = "<bean:message key="label.rate.editCode"/>";
			}
		</script>
		<script type="text/javascript" charset="utf-8">
		$(document).ready( function () {
			var oTable2 = $('#example2').dataTable( {
				"sScrollY": "125",
				"sScrollX": "100%",
				"sScrollXInner":"100%",
				"bScrollCollapse": true,
				"bSort": false,
				"bInfo": false,
				"bPaginate": false,
				"sPaginationType": "full_numbers"
			} );
			new FixedColumns( oTable2, {
				"sLeftWidth": 'relative',
				"iLeftColumns": 0,
				"iRightColumns": 0,
				"iLeftWidth": 0,
				"iRightWidth": 0
			} );
		
		} );
		</script>
		<script type="text/javascript" charset="utf-8">
		$(document).ready( function () {
			var oTable3 = $('#example3').dataTable( {
				"sScrollY": "125",
				"sScrollX": "100%",
				"sScrollXInner":"100%",
				"bScrollCollapse": true,
				"bSort": false,
				"bInfo": false,
				"bPaginate": false,
				"sPaginationType": "full_numbers"
			} );
			new FixedColumns( oTable3, {
				"sLeftWidth": 'relative',
				"iLeftColumns": 0,
				"iRightColumns": 0,
				"iLeftWidth": 0,
				"iRightWidth": 0
			} );
		
		} );
		</script>
		<!-- DataGrids /-->
         
		
		<!-- Tabs-->
        <!--<script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
        <!--<link rel="stylesheet" href="/resources/demos/style.css" />  -->
		<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
		<script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
        <!-- Tabs ends /-->
		</head>
<body  onload = "loadGlobalValues()">
<html:form action="/rateBenchMark.do?submitName=viewRateBenchMark" styleId="rateBenchMarkForm">  
<!--wraper-->
<div id="wraper"> 
    
        
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><strong>Rate Benchmark</strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mandatory"/></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.effectiveFrom"/></td>
                    <td width="31%" >
                    
             
	     		<html:text styleId="rateBenchMarkDateStr" property="to.rateBenchMarkDateStr" styleClass="txtbox width130" size="30" readonly="true" onblur="checkDate(this);"/>
	     		
                  &nbsp;<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" class="imgsearch" id = "img1" onmouseover = 'changeCursor(this.id);' onclick='selectDate("rateBenchMarkDateStr",this.value);'/>                                    
            </td>
                    
                    
                    
                    
                  
                    <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.industryCategory"/></td>
                    <td width="37%">
                    
                    <html:select styleId="rateIndustryCategoryId" property="to.rateIndustryCategoryId" styleClass="selectBox width130" onchange="loadValuesByIndustry();">
            	 
            	 	<logic:present name="rateBenchMarkIndustryCategoryList" scope="request">
					 	<html:optionsCollection property="to.rateIndCatList" label="label" value="value" />
					</logic:present>
				</html:select>
                 </td>
                  </tr>
                  <tr>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.empCode"/></td>
                    <td ><html:text styleId="empCode" property="to.empCode" styleClass="txtbox width130" onblur = "getEmployeeDetails();"/></td>
                    <td class="lable"><sup class="star">*</sup>&nbsp; <bean:message key="label.rate.empName"/></td>
                    <td><html:text styleId="empName" property="to.empName" styleClass="txtbox width130" readonly="true"/></td>
                  </tr>
                </table>
</div>
                
<div id="tabs">

 
  <ul>
  <logic:present name="rateBenchMarkProductCategoryList" scope="request">
  <c:forEach var="rateProdCat" items="${rateBenchMarkProductCategoryList}" varStatus="loop">
    <li><a href="#tabs-${rateProdCat.value}" onclick = "loadAllGridValues(${rateProdCat.value});"><c:out value='${rateProdCat.label}'/></a></li>
  </c:forEach>
  </logic:present>
  </ul>
  
   <c:forEach var="rateProdCat" items="${rateBenchMarkProductCategoryList}" varStatus="prodLoop">
   <div id="tabs-${rateProdCat.value}">
   <table border="0" cellpadding="0" cellspacing="5" width="100%">
               
    		 <!-- <tr class="gradeA" align="left">-->
    		 <tr>
		                 <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.volumeOfBusiness"/></td>
                    <td width="20%" >
                   
                      <html:select styleId="rateVob${rateProdCat.value}" property="to.rateVob" styleClass="selectBox width145" onchange="assignRateValues();">
                      <logic:present name="rateBenchMarkVobSlabList" scope="request">
    	 	 <c:forEach var="rateVobSlabs" items="${rateBenchMarkVobSlabList}" varStatus="loop">
    	 	 <c:if test="${rateVobSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}"> 
                      <html:option value='${rateVobSlabs.vobSlabTO.vobSlabId}'> <fmt:parseNumber integerOnly="true"  value="${rateVobSlabs.vobSlabTO.lowerLimit}"  type="number"/> - <fmt:parseNumber integerOnly="true"  value="${rateVobSlabs.vobSlabTO.upperLimit}" type="number"/></html:option>
                    </c:if>
                      </c:forEach>
		    </logic:present>
		      </html:select>
		    
                     </td>                     
                     
                    
                    
                    <c:set var="origin" value="false"/>
 				<c:forEach var="rateSector" items="${rateBenchMarkSectorList}" varStatus="loop">
   					<c:if test="${rateSector.sectorType == 'O'}">
    	 	 <c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
    	 	 <c:if test="${not origin}"> 
  						
		                 <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.origin"/></td>
                    <td width="40%" >
                   
                      <html:select styleId="rateOrgSec${rateProdCat.value}" property="to.rateOrgSec" styleClass="selectBox width145" onchange="assignRateValues();">
                      <logic:present name="rateBenchMarkSectorList" scope="request">
    	 	 <c:forEach var="rateSector" items="${rateBenchMarkSectorList}" varStatus="loop">
    	 	  <c:if test="${rateSector.sectorType == 'O'}">
    	 	 <c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}"> 
                      <html:option value='${rateSector.sectorTO.sectorId}'> ${rateSector.sectorTO.sectorName}</html:option>
                    </c:if>
                    </c:if>
                      </c:forEach>
		    </logic:present>
		      </html:select>
		      			</td>
		      			 <c:set var="origin" value="true"/>
		      			 </c:if>
 					 </c:if>
  					 </c:if>
				</c:forEach>
		     
                   
                         </tr>
		                 
		                 
		                 
   
   
  
		                 
		                 
		                 
		               
		                  
		     </table>
          <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <div style="width:975px;">
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example${prodLoop.count}" width="100%">
                  <thead>
            <tr>
                      <th width="10%" align="center" ><bean:message key="label.rate.sectorWeightSlab"/></th>
                      
                       <logic:present name="rateBenchMarkWtSlabList" scope="request">
    	 	 <c:forEach var="rateWtSlabs" items="${rateBenchMarkWtSlabList}" varStatus="loop">
    	 	 <c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
    	 	 
    	 	 <c:if test="${rateWtSlabs.weightSlabTO.additional != 'Y'}">
                      
                    <th width="10%" align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}-${rateWtSlabs.weightSlabTO.endWeightLabel}</th>
                    </c:if>
                     <c:if test="${rateWtSlabs.weightSlabTO.additional == 'Y'}">
                      
                    <th width="10%" align="center">${rateWtSlabs.weightSlabTO.startWeightLabel}</th>
                    </c:if>
             </c:if>      
                      </c:forEach>
		    </logic:present>
                      
               
                    </tr>
          </thead>
                  <tbody>
                     <logic:present name="rateBenchMarkSectorList" scope="request">
                   <c:forEach var="rateSector" items="${rateBenchMarkSectorList}" varStatus="loopS">
                   <c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
                   <c:if test="${rateSector.sectorType == 'D'}">
            		<tr class="gradeA">
                      <td><c:out value='${rateSector.sectorTO.sectorName}' /></td>
                      <c:forEach var="rateWtSlabs" items="${rateBenchMarkWtSlabList}" varStatus="loopWt">
                      <c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
    	 	 
                      <td> <input type="text" id="rate${rateProdCat.value}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}"
										 name="to.rate" size="11" class="txtbox width90" onkeypress="return onlyDecimal(event);"  onblur="validateFormat(this);" />
							<input type="hidden" id="rateBenchMarkMatrixId${rateProdCat.value}${rateSector.sectorTO.sectorId}${rateWtSlabs.weightSlabTO.weightSlabId}"
										 name="to.rateBenchMarkMatrixId" size="11" class="txtbox width90"  value="" />	
							<input type="hidden" id="rateProdId"
										 name="to.rateProdId" size="11" class="txtbox width90"  value="<c:out value='${rateProdCat.value}'/>" />	
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
            </div>
            <%-- <logic:present name="rateSectorsList" scope="request">
            <input type="hidden" id="rateCSec" value = '<c:out value="${fn:length(rateSectorsList)}"/>'/>
                  <c:forEach var="rateSector" items="${rateSectorsList}" varStatus="loop">
                  <c:if test="${rateSector.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
                   <c:if test="${rateSector.sectorType == 'D'}">
                  <input type="hidden" id="rateCSecId<c:out value='${loop.count}'/>"
										 name="to.rateCSecId" size="11" class="txtbox width140"  value="<c:out value='${rateSector.sectorTO.sectorId}'/>" /> 
					</c:if>
					</c:if>
			</c:forEach>
            </logic:present>
            <logic:present name="rateWtSlabsList" scope="request">
            <input type="hidden" id="rateCWt" value = '<c:out value="${fn:length(rateWtSlabsList)}"/>'/>
                  <c:forEach var="rateWtSlabs" items="${rateWtSlabsList}" varStatus="loop">
                  <c:if test="${rateWtSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
                  <input type="hidden" id="rateCWtId<c:out value='${loop.count}'/>"
										 name="to.rateCWtId" size="11" class="txtbox width140"  value="<c:out value='${rateWtSlabs.weightSlabTO.weightSlabId}'/>" /> 
			</c:if>
			</c:forEach>
            </logic:present> --%>
      </div>
       <!-- Button -->
         <c:set var="minWt" value="false"/>
 				<c:forEach var="rateMinChargWt" items="${rateBenchMarkMinChrgWtList}" varStatus="loop">
   			 <c:if test="${rateMinChargWt.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
    	 	 <c:if test="${not minWt}"> 
    	 	   <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                
  						<tr>
		                 <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.rate.minChrgWt"/></td>
                    <td width="31%" >
                    
                 <html:select styleId="rateMinChargWt${rateProdCat.value}" property="to.rateMinChargWt" styleClass="selectBox width145">
            	  <html:option value=''></html:option>
            	 	<logic:present name="rateBenchMarkMinChrgWtList" scope="request">
            	 	 <c:forEach var="rateMinChargWt" items="${rateBenchMarkMinChrgWtList}" varStatus="loop">
					  <c:if test="${rateMinChargWt.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}"> 
                      <html:option value='${rateMinChargWt.rateMinChargeableWeightId}'><fmt:parseNumber integerOnly="true"  value="${rateMinChargWt.minChargeableWeight}"  type="number"/></html:option>
					</c:if>
					</c:forEach>
					</logic:present>
					</html:select>
                     </td>     
		     
                  
                  
		                 </tr>  
		                   </table>
	</div>
	 <c:set var="minWt" value="true"/>
   
   
   </c:if>
  </c:if>  
</c:forEach>
          <div class="button_containerform">
       <html:button property="Save${rateProdCat.value}" styleClass="btnintform" styleId="btnSave${rateProdCat.value}" onclick="saveRateBenchMark('save');" title="Save">
			<bean:message key="button.label.save"/>
	</html:button>    
  </div><!-- Button ends -->  
  </div>
   </c:forEach>
  <!-- <div id="tabs-3">
    <p>Train Content</p>
  </div> -->
  
</div>
</div>
</div>
<div class="button_containerform"> 
 	<html:button property="reNew" styleClass="btnintform" styleId="btnReNew" onclick="renewBenchMark();" title="ReNew">
			<bean:message key="button.label.Renew"/>
	</html:button>
    <html:button property="Edit" styleClass="btnintform" styleId="btnEdit" onclick="editBenchMark();" title="Edit">
			<bean:message key="button.label.Edit"/>
	</html:button>
    <html:button property="Submit" styleClass="btnintform" styleId="btnSubmit" onclick="saveRateBenchMark('submit');" title="Submit">
			<bean:message key="button.label.Submit"/>
	</html:button>	
    
    </div>   
     </div>          
    <html:hidden styleId="empId" property = "to.empId"/>
    <html:hidden styleId="rateProdCatId" property = "to.rateProdCatId"/>
    <html:hidden styleId="rateCustCatId" property = "to.rateCustCatId"/>
    <html:hidden styleId="rateCWtId" property = "to.rateCWtId"/>
    <html:hidden styleId="rateCSecId" property = "to.rateCSecId"/>
    <html:hidden styleId="secArrStr" property = "to.secArrStr"/>
    <html:hidden styleId="wtArrStr" property = "to.wtArrStr"/>
    <html:hidden styleId="rateBenchMarkHeaderId" property = "to.rateBenchMarkHeaderId"/>
    <html:hidden styleId="rateBenchMarkMatrixHeaderId" property = "to.rateBenchMarkMatrixHeaderId"/>
    <html:hidden styleId="rateBenchMarkProductId" property = "to.rateBenchMarkProductId"/>   
    <html:hidden styleId="rateOriginSectorId" property = "to.rateOriginSectorId"/>
    <html:hidden styleId="rateVobSlabsId" property = "to.rateVobSlabsId"/>
    <html:hidden styleId="rateMinChargWtId" property = "to.rateMinChargWtId"/>
    <html:hidden styleId="isApproved" property = "to.isApproved"/>
    <html:hidden styleId="rateBenchMarkType" property = "to.rateBenchMarkType"/>
    <html:hidden styleId="rateCurrentHeaderId" property = "to.rateCurrentHeaderId"/>
    <input type="hidden" id="rateEdit"/>
   
<!-- Grid /--> 
   
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 

<!--wraper ends-->
</html:form>
</body>

