<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN > Change Password Screen</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />

<script language="JavaScript">

function enterKeyNavigation(e) {
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("sbmit").click();
} 



function changePassword() {
	 
	if (document.getElementById("loginid").value == ""
			|| document.getElementById("oldpassword").value == ""
				|| document.getElementById("newpassword").value == ""
					|| document.getElementById("confirmpassword").value == ""){
		alert('Password fields cannot be blank');
        return false;
	}
	
	if(document.getElementById("oldpassword").value == document.getElementById("newpassword").value){
        alert('Old password and new password cannot be same');
        document.getElementById("loginid").focus();
        return false;
    }
	
	if(document.getElementById("newpassword").value != document.getElementById("confirmpassword").value){
        alert('New password and comfirm password are not same');
        document.getElementById("confirmpassword").focus();
        return false;
	}
	
	if ( document.getElementById("newpassword").value.length<5
			||document.getElementById("newpassword").value.length>8){
	alert('Password length should be 5 to 8 characters long');
    return false;
   }
	
	var newPwd=document.getElementById("newpassword").value;
	var pattern = /^[A-Za-z]{3,20}$/;
	var numpattern = /^[0-9]{3,20}$/;

	if (pattern.test(newPwd)|| numpattern.test(newPwd)) { 
	alert(" New Password is not alphanumeric Please input alphanumeric value!");
		return false;      
		} 
      
	
	var url="./changePassword.do?submitName=showLogin";
    document.changePasswordForm.action=url;
    document.changePasswordForm.submit();
    
}



</script>
</head>
<body >

<!--wraper-->
<div id="wraper">
<div id="main-header">
  	<div class="login-fflogo" title="First Flight Couriers Ltd."></div>
    <div class="login-apptext" title="UDAAN - Unified Dynamic Access to Agile Network"></div>
  </div>
<!--header-->    
  
  <!-- main content -->    
  <div id="mainbody"><!-- login--> 
  <div class="errorcontainer">
     <center>
       <div class="msgcontainer">
        <div class="errormsg"> <html:messages name="error" id="message" bundle="loginError"> <img src="images/error.png" /> <bean:write name="message"/> </html:messages> </div>
      </div>
      <div class="msgcontainer">
        <div class="warningmsg"> <html:messages name="warning" id="message" bundle="loginError"> <img src="images/warning.png" /> <bean:write name="message"/> </html:messages> </div>
      </div>
      <div class="msgcontainer">
        <div class="infomsg"><html:messages name="info" id="message" bundle="loginError"> <img src="images/info.png" /> <bean:write name="message"/> </html:messages> </div>
      </div>	
	 </center>
   	 </div>
	  <div class="logincontainer">
    <div class="loginbox">
    	<div class="loginimagec"></div>
        <div class="loginform">
        	<h2>Change Password</h2><!-- Change Password form-->   
              <html:form action="/changePassword" method="post" styleId="changePasswordForm">
                <table cellpadding="1" cellspacing="0" border="0">
                  <tr>
                    <td colspan="2"></td>
                  </tr>
                  <tr>
                    <td width="160" class="loginuserpwdtxt"><bean:message key="label.changePassword.loginID" bundle="loginResource" /></td>
                    <td width="140"><html:text styleId="loginid"   property="to.username" maxlength="255" styleClass="logintxtbox width120" disabled="true" /></td>
                  </tr>
                  <tr>
                    <td class="loginuserpwdtxt"><bean:message key="label.changePassword.oldpassword" bundle="loginResource" /></td>
                    <td ><html:password styleId="oldpassword" property="to.oldPassword"  maxlength="255" styleClass="logintxtbox width120"/></td>
                  </tr> 
                  <tr>
                    <td class="loginuserpwdtxt"><bean:message key="label.changePassword.newpassword" bundle="loginResource" /></td>
                    <td ><html:password styleId="newpassword" property="to.newPassword"  maxlength="255" styleClass="logintxtbox width120"/></td>
                  </tr> 
                  <tr>
                    <td class="loginuserpwdtxt"><bean:message key="label.changePassword.confirmpassword" bundle="loginResource" /></td>
                    <td ><html:password styleId="confirmpassword" property="to.confirmPassword"  maxlength="255" styleClass="logintxtbox width120" onkeypress="enterKeyNavigation(event)"/></td>
                  </tr>           
                  <tr>
                    <td>&nbsp;</td>
                    <td valign="middle"><html:button styleClass="btn" property="Submit" styleId="sbmit" onclick="changePassword()">
		            <bean:message key="button.label.submit" bundle="loginResource"/></html:button></td>
                  </tr>
                </table>
              </html:form><!-- Change Password form ends-->   
       </div>
       </div>
    </div><!-- login ends-->  
  </div><!-- main content ends -->
  <!--footer-->
   <div id="main-footer">
    <div class="footer">&copy; 2012 Copyright First Flight Couriers Ltd. All Rights Reserved.This site is best viewed with a resolution of 1024x768.</div>
  </div>
  <!--footer ends-->
</div>
<!--wraper ends-->
</body>
</html>
