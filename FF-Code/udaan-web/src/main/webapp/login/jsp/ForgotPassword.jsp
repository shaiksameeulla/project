<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN > Forgot Password Screen</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />

<script language="JavaScript">
/* function enterKeyNavigation(e) {
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("submit").click();
} */

function forgotPassword() {
	
	if (document.getElementById("loginid").value == "")
		{
		alert('Please enter the Login ID');
        return false;
	}
	
 	var url="./forgotPassword.do?submitName=showLogin";
     document.forgotPasswordForm.action=url;
     document.forgotPasswordForm.submit();
   
}


</script>
</head>
<body>
<!--wraper-->
<div id="wraper">
<!--header-->    
  <div id="main-header">
  	<div class="login-fflogo" title="First Flight Couriers Ltd."></div>
    <div class="login-apptext" title="UDAAN - Unified Dynamic Access to Agile Network"></div>
  </div><!--header ends-->
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
  
    <div class="loginbox">
    	<div class="loginimagef"></div>
        <div class="loginform">
        	<h2>Forgot Password ?</h2><!-- Forgot Password form-->   
              <html:form action="/forgotPassword" styleId="forgotPasswordForm">
                <table cellpadding="1" cellspacing="3" border="0">
                  <tr>
                    <td colspan="2"></td>
                  </tr>
                    <tr>
                    <td colspan="2">&nbsp;</td>
                  </tr>    
                  <tr>
                    <td width="105" class="loginuserpwdtxt"><bean:message key="label.forgotPassword.userid" bundle="loginResource" /></td>
                    <td width="172"><html:text styleId="loginid" property="to.username" styleClass="logintxtbox width145"  onkeypress="enterKeyNavigation(event)" title="Enter your login id"/></td>
                  </tr>                   
                  <tr>
                    <td>&nbsp;</td>
                    <td valign="middle"><html:button styleClass="btn" property="Submit" onclick="forgotPassword()">
		            <bean:message key="button.label.submit" bundle="loginResource"/></html:button>
		            </td>
                  </tr>
                </table>
              </html:form><!-- forgot password form ends-->   
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
