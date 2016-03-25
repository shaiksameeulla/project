<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadValidate.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsView.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		
		<script type="text/javascript" charset="utf-8">
		var userRole = '${userRole}';
		var userSaleRole = '${leadsViewForm.to.userRoles}';
		var salesExecutiveRole = '${leadsViewForm.to.salesExecutiveRole}';
		var controlTeamRole = '${leadsViewForm.to.controlTeamRole}';
		</script>
		
</head>

<body  onload = "loadGlobalValues()">
<html:form action="/leadsView.do" method="post" styleId="leadsViewForm"> 
<%-- <html:hidden styleId="rowCount" property="rowCount"/>   --%>
 <div id="wraper">  
	 <div id="maincontent">
	    <div class="mainbody">
	      <div class="formbox">
	        <h1>Leads View</h1>
	        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message
												key="label.mandatory" /></div>
	      </div>
	      <div class="formTable">
	        <table border="0" cellpadding="0" cellspacing="7" width="100%">  
  
  <tr>
<%-- <td width="12%" class="lable"><html:radio  property="status" value="status" styleId="statusId" /><bean:message key="label.leads.Status"/></td> --%>
<td class="lable"><sup class="star">*</sup><bean:message key="label.leads.Status" /></td>
					<td width="18%"><html:select  property="to.status.statusDescription" styleId="status" styleClass="selectBox width130">
                            <html:option value="ALL"><bean:message key="label.common.all"/></html:option>
-                              <c:forEach var="leadStatuses" items="${leadStatusList}" varStatus="loop">
		              			 <option value="${leadStatuses.description}" ><c:out value="${leadStatuses.description}"/></option>
		            		</c:forEach>  
                    </html:select> </td>  
  </tr>              
	          
	         <tr>
	            <td class="lable"><sup class="star">*</sup><bean:message key="label.effectiveFrom" /></td>
	            <td>
		     		<html:text styleId="effectiveFromStr" property="to.effectiveFromStr" styleClass="txtbox width140" size="30" readonly="true" value=""/>
		     		
	                          <!-- <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" id = "img1" onmouseover = 'changeCursor(this.id);' onclick='checkDate("effectiveFromStr",this.value);'/> -->
	                         <a href="#" onclick="javascript:show_calendar('effectiveFromStr', this.value)"
											title="Select Date" id="effectiveFromDt"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a>                                     
	            
		     		
	            </td>
	            
	            <td class="lable"><sup class="star">*</sup><bean:message key="label.effectiveTo" /></td>
	            <td>
		     		<html:text styleId="effectiveToStr" property="to.effectiveToStr"  styleClass="txtbox width140" size="30" readonly="true" value=""/>
		     		
		     		<a href="#" onclick="javascript:show_calendar('effectiveToStr', this.value)"
											title="Select Date" id="effectiveToDt"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a>										
									</td>
									
									
									
	                      
	          </tr>
	
							</table>
	      </div>
	
		<!-- Button -->
	    <div class="button_containerform">    
		<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="loadDefaultObjects();" title="Search">
	        <bean:message key="button.label.Search"/>
	    </html:button>	
		</div>
		
		
		
         <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="leadDetails" width="100%">
            <thead id="leadDetailsThead">
           		<tr>
                      <th width="6%" ><bean:message key="label.leads.srNo"/></th>
                      <th width="10%" align="center"><bean:message key="label.leads.viewLeadNumber"/></th>
                      <th width="11%" align="center"><bean:message key="label.leads.viewCustomer"/></th>
                      <th width="13%"><bean:message key="label.leads.viewContact"/></th>                      
                      <th width="13%"><bean:message key="label.leads.viewContactNumber"/></th>
                      <th width="13%"><bean:message key="label.leads.viewMobileNumber"/></th>
                      <th width="13%"><bean:message key="label.leads.viewEmail"/></th>
                      <th width="13%"><bean:message key="label.leads.Status"/></th>
                      <th width="15%"><bean:message key="label.leads.viewSalesExecutive"/></th>
                </tr>
          </thead>
          </table>
                    <html:hidden property="loginOfficeCode" styleId="loginOfficeCode" value="${loginOfficeCode}"/>
					<html:hidden property="loginOfficeId" styleId="loginOfficeId" value="${loginOfficeId}"/>
					<html:hidden property="date" styleId="date" value="${date}"/>
					<html:hidden property="userId" styleId="userId" value="${userId}"/>
					<html:hidden property="userRoles" styleId="userRoles" value="${userRoles}"/>
					<html:hidden property="regionId" styleId="regionId" value="${regionId}"/>
      </div>  		
      
      			<div id="allButtons" style="float: right;">		
				 <input name="Clear"
					onclick="clearLeadsViewScreen();" type="button" value="Clear"
					class="btnintform" title="Clear" />
			</div>
	      
	       
	</div>
            
</div>

</div>

</html:form>
</body>
</html>
