<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Welcome to UDAAN</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />	
		
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>			
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script language="JavaScript"  type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsFeedback.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsCommon.js"></script>
		
		</head>
		<body>
		 <html:form action="/leadPlanning.do" method="post" styleId="viewUpdateFeedbackForm" >
<!--wraper-->
<div id="wraper"> 
          
          <div class="clear"></div>
          
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.leadFeedback.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      </div>
              <div class="formTable">
              <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
             <html:hidden property="to.leadId" styleId="leadId" value=""/> 
             <html:hidden property="to.leadFeedCodeSave" styleId="leadFeedCodeSave" value="FeedBackSave"/> 
             <%--  <html:hidden property="to.bookingType" styleId="bookingType" value="${bookingType}"/> --%>
              <td width="11%" class="lable"><bean:message key="label.leadPlaning.leadNumber"/></td>
             <td width="24%">
            <html:text property="to.leadNumber" styleClass="txtbox width140" styleId="leadNumber" readonly="true" value=""  onchange="getLeadDetailsByLeadNumber();"/>
            </td>
            <td width="11%" class="lable"><bean:message key="label.leads.CustomerName"/></td>
             <td width="24%">
            <html:text property="to.customerName" styleClass="txtbox width140" maxlength="50" styleId="custName" readonly="true" value="" onkeypress="return onlyAlphabet4Lead(event);" />
            </td>
             
             </tr>
                
                </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"> <bean:message key="label.leadPlaning.details"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="leadsPlan" width="100%">
                  <thead>
             <tr>
             	<input type="hidden" name="chk0" id="chk0" />
             	<!--  <th width="3%" align="center"><input type="checkbox" class="checkbox" name="chk0" id="chk0" /></th> -->
              <%-- 	 <th width="5%" align="center"  ><bean:message key="label.serialNo"/> </th> --%>
	             <th width="5%" align="center"><bean:message key="label.leadPlaning.visitNo"/></th>
	          	 <th width="20%" align="center"><sup class="star">*</sup><bean:message key="label.common.date"/></th>
	           	 <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.leadPlaning.purposeOfVisit"/></th>
	             <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.leadPlaning.contactPerson"/></th>
	             <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.common.time"/></th>
	             <th width="15%" align="center"><bean:message key="button.label.Feedback"/></th>
	             <th width="15%" align="center"><bean:message key="label.common.remarks"/></th>
            </tr>
            		
          </thead>
                
                </table>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
          
          <!-- Button -->
          <div class="button_containerform">
          
   					 <html:button property="save" styleClass="btnintform" onclick="saveOrUpdateLeadsFeedbackDtls();" styleId="save" >
					<bean:message key="button.label.save" locale="display" />
					</html:button>
				
					<html:button styleClass="btnintform" property="Cancel" 	onclick="cancelPage();" styleId="cancel">
					<bean:message key="button.label.Cancel" locale="display" />
					</html:button>
					
					<%-- <html:button styleClass="btnintform" property="Print" styleId="Print">
					<bean:message key="label.baBookingDox.Print" locale="display" />
					</html:button>  --%>
				
				<%-- <html:button property="delete" styleId="delete" styleClass="btnintform"  onclick="deleteLeadsDataRow();">
				<bean:message key="button.label.delete" locale="display" />
				</html:button> --%>
  		</div>
         
        </div>
<!-- wrapper ends -->
</html:form>
</body>
</html>
