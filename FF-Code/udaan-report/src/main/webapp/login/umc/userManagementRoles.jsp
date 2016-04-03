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
<title>Welcome to User Management - Adding User Roles</title>
 <link href="css/global.css" rel="stylesheet" type="text/css" /> 
<!-- DataGrids -->
<script language="JavaScript" src="login/js/userManagement/userManagementRoles.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8">


$(document).ready( function () {
	var oTable = $('#baBooking').dataTable( {
		"sScrollY": "250px",
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
} );


function setDefaultValues(){
	document.getElementById("roleType").value = '<c:out value='${roleType}'/>';
		
	}


function setRoleType(roleType){
	if(roleType  =='E'){
		document.getElementById('ffcl').checked="checked";
	}
	else if(roleType =='C'){
		document.getElementById('cust').checked="checked";
	}
}

function displayTrasnMsg(roleType,trasnMode,isUpdationMode,userRoleId){
	if(trasnMode !=null && trasnMode!="") {
		if(roleType  =='C'){
			if(isUpdationMode =="Y")
				alert("Customer role updated successfully.");
			else 
				alert("Customer role created successfully.");
		}
		else if(roleType =='E'){
			if(isUpdationMode =="Y")
				alert("FFCL role updated successfully.");
			else 
				alert("FFCL role created successfully.");
		}
		if(userRoleId !=null && userRoleId !="" && userRoleId > 0){
			document.getElementById("roleId").value=userRoleId;
			document.getElementById("roleId").selected='selected';
			getUserRights(userRoleId);
		}
	}
	
}
		
</script>
<!-- DataGrids /-->
</head>
<body onload="setDefaultValues();setRoleType('<c:out value='${roleType}'/>');displayTrasnMsg('<c:out value='${roleType}'/>','<c:out value='${trasnMode}'/>','<c:out value='${isUpdationMode}'/>','<c:out value='${userRoleId}'/>')">
	<!--wraper-->
	<div id="wraper">
		  
		<!--top navigation ends-->
		<html:form action="/userManagementRoles" method="post" styleId="userManagementRolesForm">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1> <bean:message key="label.login.ManageRole" bundle="loginResource" /></h1>
					</div>
					 <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.login.FieldsAreMandatory" bundle="loginResource" /></div>
      </div>
					<div class="formContainer">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
							
							
						
    		 <input type="hidden" name="to.roleType" id="roleType" value=""></input>
    		  <input type="hidden" name="to.status" id="status" value=""></input>
    		   <input type="hidden" name="to.isUpdationMode" id="isUpdationMode" value="N"></input>
               <tr>
            
               
                <td class="lable"><input type="radio"  class="checkbox" name="ffcl" id="ffcl"  onclick="getUserRolesAndRights('E');"/>FFCL</td>
                <td class="lable" ><input  type="radio" class="checkbox" name="cust" id="cust" onclick="getUserRolesAndRights('C');"/>Customer</td>
              </tr> 	
              <tr>
                <td width="7%" class="lable"><sup class="star">*</sup><bean:message key="label.login.Role" bundle="loginResource" /></td>
                <td width="16%">
                
                <html:select styleId="roleId" property="to.roleId" value="" onclick="getUserRights(this.value);">
                <html:option value="N" ><bean:message key="label.login.New" bundle="loginResource" /></html:option>
                <c:forEach var="item" items="${userRoles}" varStatus="loop">
		                  <html:option value="${item.roleId}"><c:out value="${item.roleName}"/></html:option>
		               </c:forEach>
                  </html:select>  
                  </td>

                <td class="lable"><sup class="star">*</sup><bean:message key="label.login.RoleName" bundle="loginResource" /></td>
                <td width="17%" class="center">
                 <html:text styleId="roleName" property="to.roleName" size="15"  value="" maxlength="15" onblur="isUserRoleExists(this.value);"/></td>
                <td width="13%" class="lable"><sup class="star">*</sup><bean:message key="label.login.RoleDescription" bundle="loginResource" /></td>
                <td width="28%" valign="bottom"><html:textarea   styleId="roleDesc" property="to.roleDesc" style="resize:none"   value=""  onkeypress="return imposeMaxLength(this,event,200);"/></td>
              </tr>
							
				
				
       	</table>
						</div>
					</div>
 					<div id="demo">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="baBooking" width="100%" align="center">
							<thead>
								<tr>
								
							<th ><bean:message key="label.login.AllRightsInUdaan" bundle="loginResource" /> </th>
							<th ><bean:message key="label.login.Select" bundle="loginResource" /> </th>
									</tr>
								
							</thead>
							<tbody>
    		
    		
    		
    		 <c:forEach var="applScreen" items="${applScreens}" varStatus="loop">
    		 <tr class="gradeA" >
		                 <td class="center"><c:out value="${applScreen.screenName}"/></td>
		                 <td class="center">
		                 <input type="checkbox"  name="rightIdChkBox" id="rightIdChkBox<c:out value="${loop.count}"/>" value="<c:out value="${applScreen.screenId}"/>" onclick ="isChecked(this,<c:out value="${loop.count}"/>);"/>
		            	</td>
		                 <html:hidden property="to.applScreenId" styleId="applScreenId${loop.count}" value="${applScreen.screenId}"/>
		                  <html:hidden property="to.isRightAssigned" styleId="isRightAssigned${loop.count}" value="N"/>
		                   <html:hidden property="to.userRightIds" styleId="userRightIds${loop.count}" value="0"/>
		                    </tr> 
		               </c:forEach>
                 
              
                  
    		
              
  			</tbody>

						</table>
					</div>
	
						
					
				<!-- Grid /--> 
	</div>     
        <div class="button_containerform">
      
       
    <html:button property="Save"  styleId="saveButton" styleClass="btnintform" onclick="saveOrUpdateUserRoles();"> <bean:message key="button.label.Save" bundle="loginResource"/></html:button>
      <html:button property="Modify"  styleId="modifyButton" styleClass="btnintform" onclick="rightsChkEnable4Edit();"> <bean:message key="button.label.Modify" bundle="loginResource"/></html:button>
       <html:button property="Activate"  styleId="activateButton" styleClass="btnintform" onclick="activateDeactivateUserRole('A');"><bean:message key="button.label.Activate" bundle="loginResource"/></html:button> 
       <html:button property="Deactivate"  styleId="deactivateButton" styleClass="btnintform" onclick="activateDeactivateUserRole('I');"> <bean:message key="button.label.Deactivate" bundle="loginResource"/></html:button>
      
       <!--  <input name="Save" type="button" class="btnintform" value='Save' title="Save" onclick="saveOrUpdateUserRoles();"/> -->
  <!-- <input name="Modify" type="button" class="btnintform" value='Modify' title="Modify" onclick="rightsChkEnable4Edit();"/> -->
  <!-- <input name="Activate" type="button" class="btnintform" value='Activate' title="Activate" onclick="activateDeactivateUserRole('A');"/>
  <input name="Deactivate" type="button" value="Deactivate" class="btnintformbig"  title="Deactivate" onclick="activateDeactivateUserRole('I');"/> -->
  </div><!-- Button ends -->
				
			</div>
			 <!-- Button -->

      
</div>
			
</html:form>
</body>

</html>
