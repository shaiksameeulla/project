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
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="login/js/userManagement/roleAssignment.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.dimensions.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/uddanAutocomplete.js" type="text/javascript" ></script>
<script language="javascript"> 


</script>

</head>
<body>
<!--wraper-->
	<div id="wraper">
<html:form action="/roleAssignment" method="post" styleId="userRoleAssignmentForm">
  
  <!--top navigation ends-->
  <div class="clear"></div>
  <!-- main content -->
  <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.login.AssignRole" bundle="loginResource" /></h1>
       <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.login.FieldsAreMandatory" bundle="loginResource" /></div>
      </div>
      <!-- <div class="formContainer">  -->
        <div class="formTable">
          <table border="0" cellpadding="0" cellspacing="5" width="100%">
            <tr>
               <input type="hidden" name="to.roleType" id="roleType" value=""></input>
               <td class="lable"><input type="radio"  class="checkbox" name="roleType" id="ffcl"  onclick="getUserRoles('E');enableDisableAreaOfficeMapping('E');getUsers('E');"/><bean:message key="label.login.FFCL" bundle="loginResource" /></td>
                <td class="lable" ><input  type="radio" class="checkbox" name="roleType" id="cust" onclick="getUserRoles('C');enableDisableAreaOfficeMapping('C');getUsers('C');"/><bean:message key="label.login.Customer" bundle="loginResource" /></td>
                 <td class="lable"><br></br></td>
             
            </tr>            
            <tr>
            
              <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.login.LoginID" bundle="loginResource" /></td>
              <td width="16%">
               <html:hidden property="to.userId"  styleId="userId" value=""/>
               <html:hidden property="to.userCityId"  styleId="userCityId" value=""/>
              <html:text property="to.userCode" size="11" styleClass="txtbox width140" styleId="userCode" value="" />
              </td>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.login.Name" bundle="loginResource" /></td>
              <td width="18%">
               <html:text property="to.userName" size="11" styleClass="txtbox width140" styleId="userName" value="" readonly="true"/>
              </td>
               <td>  
               <html:button  property="search" styleClass="btnintgrid" title="Search" styleId="search" onclick="getUserDetails();">
               <bean:message key="button.label.Search" bundle="loginResource" /></html:button></td>
              <td width="9%" class="lable">&nbsp;</td>
              <td colspan="3">&nbsp;</td>
              <td width="7%" class="lable">&nbsp;</td>
              <td width="15%" colspan="3">&nbsp;</td>              
            </tr>
          </table>
        </div>
        <!-- Button -->   
        
        <!-- Button ends -->
           
        <div class="formTable"><form name="combo">
          <table border="0" cellpadding="2" cellspacing="0" width="50%">
            <tr>
              <td class="lable1" align="left"><table border="0" cellpadding="0" cellspacing="0" width="50%">
                  <tr>
                    <td width="5%" class="lable1">&nbsp;</td>
            
		    <td width="21%" class="lable1" style="background-color:#ebf1fe"><strong><bean:message key="label.login.Roles" bundle="loginResource" /></strong> <br />
                    <html:select property="to.roleIds" styleClass="selectBox width450" multiple="multiple" styleId="roleId" disabled="true">
                    </html:select>
                    
                    </td>
                    <td width="5%" class="lable1">
                    <input name="Add" type="button" value="" id="btnfrom" class="btnintmultiselectr"  title="Add" onclick="move(this.form.roleId,this.form.assignRoleIds)"/><br>
                    <input name="Add" type="button" value="" id="btnto" class="btnintmultiselectl"  title="Add" onclick="move(this.form.assignRoleIds,this.form.roleId)"/>
                    
                    </td>
                       
                    <td width="21%" class="lable1" style="background-color:#ebf1fe"><strong><bean:message key="label.login.AssignedRoles" bundle="loginResource" /></strong> <br />
                    <html:hidden property="to.assignedUserRoleIds" styleId="assignedUserRoleIds" value=""/>
                     <html:hidden property="to.assignedOfficeIds" styleId="assignedOfficeIds" value=""/>
                    <html:select   property="to.assignRoleIds" styleClass="selectBox width450" multiple="multiple" styleId="assignRoleIds" disabled="true">
                    </html:select>                
                    </td>
                  </tr>
                </table></td>
            </tr>
          </table>
          </form>
        </div>
         
     <div class="formTable">
          <table border="0" cellpadding="0" cellspacing="5" width="100%">
            <tr>
              <td width="10%" class="lable">
             <html:hidden property="to.mappingTO" styleId="mappingTO" value=""/>
              <input type="radio" id="area" class="checkbox" name="type" onclick="getOfficeDetails('A');"/><bean:message key="label.login.Area" bundle="loginResource" /></td>
              <td width="5%"></td>
              <td width="9%" class="lable">
              <input type="radio" id="rho" class="checkbox" name="type" onclick="getOfficeDetails('R');"/><bean:message key="label.login.RHO" bundle="loginResource" /></td>
              <td ></td>
            </tr>
          </table>
        </div>
        <div class="formTable"><form name="combo2">
         
	  <table border="0" cellpadding="0" cellspacing="0" width="50%">
            <tr>
              <td class="" align="left"><table border="0" cellpadding="0" cellspacing="0" width="50%">
                  <tr>
                    <td width="5%" class="lable1">&nbsp;</td>
                    <td width="21%" class="lable1" style="background-color:#ebf1fe"><strong><bean:message key="label.login.Branches" bundle="loginResource" /></strong> <br />
                       <html:select property="to.officeIds" styleClass="selectBox width450" multiple="multiple" styleId="officeIds" disabled="true" >
                    
                    </html:select>
                    </td>
                    <td width="5%" class="lable1">
                      <input name="Add" type="button" value="" class="btnintmultiselectr"  title="Add" id="btnfrom1" onclick="move(this.form.officeIds,this.form.mappedOfficeIds);"/><br>
                    <input name="Add" type="button" value="" class="btnintmultiselectl"  title="Add" id="btnto1"onclick="move(this.form.mappedOfficeIds,this.form.officeIds);"/>
                    </td>
                    <td width="21%" class="lable1" style="background-color:#ebf1fe"><strong><bean:message key="label.login.selectedBranches" bundle="loginResource" /></strong>
                      <html:select property="to.mappedOfficeIds" styleClass="selectBox width450" multiple="multiple" styleId="mappedOfficeIds" disabled="true">
                    </html:select></td>
                  </tr>
                </table></td>
            </tr>
          </table>
          </form>
        </div>
      </div>
      
      <!-- Grid /--> 
    </div>
  
  <!-- main content ends --> 
  <!-- Button -->
  <div class="button_containerform">
  <html:button property="Save" styleClass="btnintform" styleId="btnSave"  title="Save" onclick="saveRoleAssignments();">
  <bean:message key="button.label.Save" bundle="loginResource"/></html:button>
   <html:button property="Modify" styleClass="btnintform" styleId="btnModify" title="Modify" onclick="enableFields();">
   <bean:message key="button.label.Modify" bundle="loginResource"/></html:button>
  <html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" title="Cancel" onclick="cancel();">
   <bean:message key="button.label.Cancel" bundle="loginResource"/></html:button>
  </div>
  <!-- Button ends -->
 </html:form>
 </div>
</body>

