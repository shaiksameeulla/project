<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to User Management - Assign Approver</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<script language="JavaScript" src="login/js/assignApprover/assignApprover.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script> 
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" charset="utf-8">

var ALL = "";
var officeCode = "";
var stationCode = "";
var regionalOfficeCode = "";



function loadGlobalValues(){
ALL = "<bean:message key="label.umc.ALL" bundle="loginResource" />";
officeCode = "<bean:message key="label.umc.OfficeCode" bundle="loginResource" />";
stationCode = "<bean:message key="label.umc.StationCode" bundle="loginResource" />";
regionalOfficeCode = "<bean:message key="label.umc.RegionalOfficeCode" bundle="loginResource" />";

disableFields();
getUsers('E');
}

</script>
<!-- DataGrids /-->
</head>

<body onload="loadGlobalValues();">
<html:form action="/assignApprover" method="post" styleId="assignApproverForm">
	<!--wraper-->
	<div id="wraper">
		  
		<!--top navigation ends-->
		
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1> <bean:message key="label.umc.AssignApprover" bundle="loginResource" /></h1>
					</div>
					 <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.login.FieldsAreMandatory" bundle="loginResource" /></div>
      </div>
					<div class="formContainer">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
							
							
						   <tr>
            
              <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.umc.UserID" bundle="loginResource" /></td>
              <td width="25%">
               <html:hidden property="to.userId"  styleId="userId" value=""/>
              <html:text property="to.userName" size="11" styleClass="txtbox width140" styleId="userName" value="" />
              <html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="getUserDetails();" title="Search">
        <bean:message key="button.label.Search"/>
    </html:button>
              </td>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.umc.empName" bundle="loginResource" /></td>
              <td width="15%">
               <html:text property="to.empName" size="11" styleClass="txtbox width140" styleId="empName" value="" readonly="true"/>
              </td>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.umc.empCode" bundle="loginResource" /></td>
              <td width="18%">
               <html:text property="to.empCode" size="11" styleClass="txtbox width140" styleId="empCode" value="" readonly="true"/>
              </td>                             
            </tr>
          </table>
        </div>
        <!-- Button -->   
        
        <!-- Button ends -->
           
        <div class="formTable">
          <table border="0" cellpadding="2" cellspacing="0" width="100%">
            <tr>
              <td><table border="0" cellpadding="0" cellspacing="0" width="100%">
                  <tr>
                    <td class="lable1">&nbsp;&nbsp;&nbsp;<sup class="star">*</sup><bean:message key="label.umc.RegionalOffice" bundle="loginResource" /></td>
            
		    		<td  class="lable1">
                    <html:select property="to.regionalOfficeIds" styleClass="selectBox width200" multiple="multiple" styleId="regionalOfficeIds" disabled="true" onchange="getStationsByRegionalOffices(this.form.regionalOfficeIds);">
                    <logic:present name="regOfficeList" scope="request">
                    <html:optionsCollection property="to.regionalOfficeList" label="label" value="value" />
                    </logic:present>
                    </html:select>
                    
                    </td>
                       
                     <td  class="lable1">&nbsp;&nbsp;&nbsp;<sup class="star">*</sup><bean:message key="label.umc.Station" bundle="loginResource" /></td>  
                    <td  class="lable1" >
                    <html:select property="to.cityIds" styleClass="selectBox width200" multiple="multiple" styleId="cityIds" disabled="true" onchange="getOfficesByCity(this.form.cityIds);">
                    </html:select>
                    
                    </td>
                     <td  class="lable1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sup class="star">*</sup><bean:message key="label.umc.Office" bundle="loginResource" /></td> 
                    <td  class="lable1">
                    <html:select property="to.officeIds" styleClass="selectBox width200" multiple="multiple" styleId="officeIds" disabled="true">
                    </html:select>
                    
                    </td>
                  </tr>
                </table></td>
            </tr>	
				
				
       	</table>
						</div>
					</div>
					<br></br>
					<br></br>
					
					<br></br>
 					<div id="demo" style=width:1000;height:250;overflow:auto >
					<table cellpadding="0" cellspacing="0" border="1" class="display" id="assignApprover" width="100%" align="center">
							<thead>
								<tr>
								
							<th align="center" ><bean:message key="label.umc.ScreenName" bundle="loginResource" /> </th>
							<th ><bean:message key="label.umc.Assign" bundle="loginResource" /> </th>
									</tr>
								
							</thead>
							<tbody>
    		
    		
    		<logic:present name="applScreensList" scope="request">
    	 	 <c:forEach var="applScreen" items="${applScreensList}" varStatus="loop">
    		 <tr class="gradeA" align="left">
		                 <td ><c:out value="${applScreen.screenName}"/></td>
		                 <td >
		                 <input type="checkbox" disabled name="chkBox" id="chkBox<c:out value="${loop.count}"/>" value="<c:out value="${applScreen.screenId}"/>" />
		                 </td>
		                 </tr> 
		     </c:forEach>
		    </logic:present>
  			</tbody>

						</table>
					</div>
	
	</div>     
        
				
			</div>
      <div class="button_containerform">
    <html:button property="Save"  styleId="saveButton" styleClass="btnintform" onclick="saveOrUpdateAssignApprover();"> <bean:message key="button.label.Save" bundle="loginResource"/></html:button>
    <html:button property="Modify"  styleId="modifyButton" styleClass="btnintform" onclick="modifyAssignApprover();"> <bean:message key="button.label.Modify" bundle="loginResource"/></html:button>
</div>
<html:hidden property="to.cityIdsStr"  styleId="cityIdsStr" value=""/>
<html:hidden property="to.regionalOfficeIdsStr"  styleId="regionalOfficeIdsStr" value=""/>
<html:hidden property="to.officeIdsStr"  styleId="officeIdsStr" value=""/>
<html:hidden property="to.screenIdsStr"  styleId="screenIdsStr" value=""/>
	                 			
</html:form>
</body>
