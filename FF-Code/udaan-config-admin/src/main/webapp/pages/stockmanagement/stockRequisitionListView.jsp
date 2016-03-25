<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript"  src="js/jquery.dataTables.js"></script>
<script type="text/javascript"  src="js/FixedColumns.js"></script> 
<script type="text/javascript"  src="js/common.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockRequisitionListView.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" src="js/calendar/ts_picker.js"></script>
<!-- <script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script> -->
		<!-- DataGrids /-->
		</head>
<body>
<html:form  method="post" styleId="listStockRequisitionForm">
<!--wraper-->
<div id="wraper"> 
         
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>
        <bean:message key="stock.label.req.list.view"/>
        </h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="stock.label.issue.mandatory"/></div>
      </div>
              <div class="formTable">
              <table border="0" cellpadding="0" cellspacing="5" width="100%">
                <tr>
                  <td class="lable"><bean:message key="stock.label.req.office"/>:</td>
                  <td>
                  <html:select property="to.requisitionOfficeId" styleId="requisitionOfficeId" styleClass="selectBox width150"  onkeydown="return enterKeyNav('status' ,event.keyCode)">
                			<html:option value=""><bean:message key="stock.label.req.office.list"/></html:option>
                          	<logic:present name="reqOfficeMap" scope="request">
                          	<html:optionsCollection name="reqOfficeMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select>
                   </td>
                  <td class="lable"><sup class="star">*</sup><bean:message key="stock.label.req.status"/>:</td>
                  <td>
                  <html:select property="to.status" styleId="status" styleClass="selectBox width150"  onkeydown="return enterKeyNav('calImgFrom' ,event.keyCode)">
                          	<logic:present name="reqStatusMap" scope="request">
                          	<html:optionsCollection name="reqStatusMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select>
                   </td>
                </tr>
                  <tr>
            <td width="9%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.req.from.date"/> :</td>
            <td width="12%">
            
            <html:text property="to.fromDateStr" styleId="fromDateStr" styleClass="txtbox width110" size="13" readonly="true"  onchange="isFutureDateForIssue(this,'calImgTo')" onkeypress="enterKeyNav('calImgTo',event.keyCode);" value="${listStockRequisitionForm.to.fromDateStr}"/>
           						 <a href='javascript:show_calendar("fromDateStr", this.value)' id="calImgFrom" title="Select From Date"> 
         				 		<img src="images/calender.gif" alt="Select From Date" width="16" height="16" border="0" class="imgsearch" id="fromCal"/></a> 
           </td>
           
            <td width="18%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.req.to.date"/>:</td>
            <td width="12%">
           <html:text property="to.toDateStr" styleId="toDateStr" styleClass="txtbox width110" size="13" readonly="true" onchange="checkDateDifferenceForTodate(this,'Search')"   onkeypress="enterKeyNav('Search',event.keyCode);" value="${listStockRequisitionForm.to.toDateStr}"/>
           						 <a href='javascript:show_calendar("toDateStr", this.value)' id="calImgTo" title="Select From Date"> 
         				 		<img src="images/calender.gif" alt="Select To Date" width="16" height="16" border="0" class="imgsearch" id="toCal"/></a> 
              &nbsp;</td>
            <td width="39%">
            <html:button property="Search" styleId="Search" styleClass="btnintform" onclick="search();" title="Search">
   			<bean:message key="button.search" /></html:button>
   			
   			 <html:button property="Cancel" styleId="Cancel" styleClass="btnintform" onclick="clearScreen()">
   			<bean:message key="button.cancel" /></html:button>
            
            </td>
                </tr>
                 
                </table>
        
</div>
              
      <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="stock.label.dtls"/></div>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="stockListView" width="40%">
                  <thead>
            <tr>
                      <th width="7%" align="center" ><bean:message key="stock.label.issue.SrNo" /></th>
                      <th width="34%" align="center" ><bean:message key="stock.label.req.date"/></th>
                      <th width="59%" align="center" ><bean:message key="stock.label.issue.requisitionNum"/></th>
                    </tr>
          </thead>
                  <tbody>
                  <c:forEach var="itemDtls" items="${listStockRequisitionForm.to.lineItems}" varStatus="loop">
            		<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}"> 
            		 
                	  <td><label><c:out value="${loop.count}"/></label></td>
                	  <td>${itemDtls.requisitionDate}</td>
                      <td>
                      <%-- <a href="#" onclick="navigateToApproveReq('${itemDtls.approveRequisitionUrl}')">${itemDtls.requisitionNumber} --%>
                      <a href="${itemDtls.approveRequisitionUrl}"  target="_blank">${itemDtls.requisitionNumber}
                      
                      </a></td>
                	  </tr>
                	  </c:forEach>
			</tbody>
        </table>
      </div>
              <!-- Grid /--> 
            </div>
   
  </div>
        </div>
        <html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
        <html:hidden property="to.todayDate" styleId="todayDate"/>
<!--wraper ends-->
</html:form>
</body>
</html>
