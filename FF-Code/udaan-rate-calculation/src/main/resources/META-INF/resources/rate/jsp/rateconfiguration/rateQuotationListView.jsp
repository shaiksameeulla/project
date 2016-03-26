<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Rate Quotation</title>

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/ratequotationlistview.js"></script>


<script type="text/javascript" charset="utf-8">

			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "100",
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
					"iRightWidth": 0,
				} );
			} );

		</script>

</head>
<body onload="getQuotations();">
<html:form action="/rateQuotation.do?submitName=listViewRateQuotation"
		styleId="rateQuotationForm">
		<div id="wraper"> 
         <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><strong><bean:message key="label.Rate.QuotaionListView" /></strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message
											key="label.Rate.fieldMandatory" /></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                     <td width="13%" class="lable"><bean:message
											key="label.Rate.fromDt" /></td>
                    <td width="18%" >
                    <html:text styleId="fromDate" property="rateQuotationListViewTO.fromDate" styleClass="txtbox width130" readonly="true" tabindex="-1" onfocus="validateFromDate(this);"/>
                      &nbsp;<a href="#" onclick="javascript:show_calendar('fromDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" /></a></td>
                    <td width="20%" class="lable"><bean:message
											key="label.Rate.toDt" /></td>
                    <td width="18%">
                     <html:text styleId="toDate" property="rateQuotationListViewTO.toDate" styleClass="txtbox width130" readonly="true" tabindex="-1" onfocus="validateToDate(this);"/>
                      &nbsp;<a href="#" onclick="javascript:show_calendar('toDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a></td>
                    <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Region" /></td> 
                    <logic:present name="regionalAllList" scope="request">
                    <td>
					<html:select styleId="regionDropId" property="rateQuotationListViewTO.regionDropId" styleClass="selectBox width130" onchange="getStationsByRegion(this.value);">
                    	<html:option value="A">ALL</html:option>
                    	<c:forEach var="reg" items="${regionalAllList}" varStatus="loop">
                    	<html:option value="${reg.officeId}">${reg.officeName}</html:option>
                    	</c:forEach>
                    </html:select>
                    </td>
                    </logic:present>
                    
                    <logic:notPresent name="regionalAllList" scope="request">
                    
					<td width="17%">
					<html:text styleId="regionalName" property="rateQuotationListViewTO.regionalName" disabled = "true" styleClass="txtbox width130"/>
					<html:hidden styleId="regionOfcId" property="rateQuotationListViewTO.regionOfcId" styleClass="txtbox width130"/>
					</td>
					
                    </logic:notPresent>
                    
                  </tr>
                  <tr>
                  
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Station" /></td>
                    <logic:present name="stationRegList" scope="request">
                    <logic:present name="regionalAllList" scope="request">
                      <td width="15%">
                    <html:select styleId="cityRegDropId" property="rateQuotationListViewTO.cityRegDropId" styleClass="selectBox width130">
                    <html:option value="A">ALL</html:option>
                    </html:select>
                    </td>
                    </logic:present>
                     </logic:present>
                     
                    <logic:present name="stationRegList" scope="request">
                    <logic:notPresent name="regionalAllList" scope="request">
                      <td width="15%">
                    <html:select styleId="cityRegDropId" property="rateQuotationListViewTO.cityRegDropId" styleClass="selectBox width130">
                    <html:option value="A">ALL</html:option>
                    <c:forEach var="city" items="${stationRegList}" varStatus="loop">
                    	<html:option value="${city.cityId}">${city.cityName}</html:option>
                    	</c:forEach>
                    </html:select>
                    </td>
                    </logic:notPresent>
                    </logic:present> 
                     
                    <logic:notPresent name="stationRegList" scope="request">
                    <td width="15%">
                    <html:text styleId="cityName" property="rateQuotationListViewTO.cityName" disabled = "true" styleClass="txtbox width130"/>
                    <html:hidden styleId="cityId" property="rateQuotationListViewTO.cityId" styleClass="txtbox width130"/>
					</td>
					</logic:notPresent>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.paramStatus" /></td>
                    <td>
                    <logic:present name="quotStatusList" scope="request">
                    <html:select styleId="status" property="rateQuotationListViewTO.status" styleClass="selectBox width130">
                      <c:forEach var="status"	items="${quotStatusList}" varStatus="loop">
                    	<html:option value="${status.stdTypeCode}">${status.description}</html:option>
                      </c:forEach>
                    </html:select>
                    </logic:present>
                    </td>
                    <td class="lable"><bean:message
											key="label.Rate.quotation" /></td>
                    <td width="17%"><html:text styleId="rateQuotationNo" property="rateQuotationListViewTO.rateQuotationNo" styleClass="txtbox width130"/></td>
                  </tr>
                  <tr>
                    <td align="right" valign="top" colspan="6"><input name="Search" type="button" value="Search" class="button" onclick="getQuotations();" title="Search"/></td>
                  </tr>
                </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"> <bean:message key="label.Rate.details" /></div>
        </div>
        <div style="width:985px">
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                  <thead>
            <tr>
                      
                      <th width="5%"><bean:message
											key="label.Rate.SrNo" /></th>
                      <th width="13%"><bean:message
											key="label.ListView.CreatedDate" /></th>
                      <th width="13%"><bean:message
											key="label.ListView.QuotationNo" /></th>
                      <th width="13%"><bean:message
											key="label.Rate.RegionName" /></th>
                      <th width="13%"><bean:message
											key="label.ListView.Station" /></th>
                      <th width="13%"><bean:message
											key="label.Rate.CustName" /></th>
                      <th width="13%"><bean:message
											key="label.Rate.SalesOfficeName" /></th>
                      <th width="13%"><bean:message
											key="label.ListView.SalesPerson" /></th>
                      <th width="13%"><bean:message
											key="label.Rate.Status" /></th>
                    </tr>
          </thead>
                
                </table>
                </div>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
          <html:hidden property="rateQuotationListViewTO.userId" styleId="userId"/>
           <input type="hidden" name="type" id="type" value="${empType}"/>
           <html:hidden styleId="officeType" property="rateQuotationListViewTO.officeType"/>
           
           
          <!-- Button -->
          <!--<div class="button_containerform">
    <input name="Approve" type="button" class="btnintform" value='Approve' title="Approve"/>
    <input name="Reject" type="button" class="btnintform" value='Reject' title="Reject"/>
  </div>-->
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div>
          <!-- footer ends --> 
        </div>
<!-- wrapper ends -->
</html:form>
</body>

