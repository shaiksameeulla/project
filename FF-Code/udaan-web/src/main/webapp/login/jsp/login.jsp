<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN &gt; Login Screen</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="login/js/login.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" language="JavaScript">
$(document).ready( function () {
	
	$('#userName').keypress(function(e) { 
	    var s = String.fromCharCode( e.which );
	    var loginLength=jQuery('#userName').val().length;
	    if ( s.toUpperCase() === s && s.toLowerCase() !== s && !e.shiftKey  && loginLength==0) {
	        alert('Capslock is on');
	    }
	});

});
</script>
</head>
<body onload="load()">
<div id="wraper"><!--header-->    
  <div id="main-header">
  	<div class="login-fflogo" title="First Flight Couriers Ltd."></div>
    <div class="login-apptext" title="UDAAN - Unified Dynamic Access to Agile Network"></div>
  </div><!--header ends-->
  <!-- main content -->    
  <div id="mainbody"><!-- login-->  
     <html:form  action="/login.do"  styleId="loginPage" method="post" >
     <div class="errorcontainer">
     <center>
       <div class="msgcontainer" id="msgcontainer">
        <div class="errormsg"> <html:messages name="error" id="message" bundle="loginError">
        <c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
         <img src="images/error.png" /> <bean:write name="message"/> 
        </c:if>
        </html:messages> 
        <html:messages name="error" id="message" bundle="frameworkResourceBundle">
				<c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				<img src="images/error.png" />
				<bean:write name="message"/>
				</c:if>
		
	    </html:messages>
        </div>
      
        <div class="warningmsg"> <html:messages name="warning" id="message" bundle="loginError"> 
        <c:if test="${warning ne null && not fn:containsIgnoreCase(message,'bundle')}" >
        <img src="images/warning.png" /> <bean:write name="message"/>
        
        </c:if>
         </html:messages> </div>
        <div class="infomsg"><html:messages name="info" id="message" bundle="loginError">
        <c:if test="${info ne null && not fn:containsIgnoreCase(message,'bundle')}" >
         <img src="images/info.png" /> <bean:write name="message"/>
         
         </c:if>
          </html:messages> </div>
      </div>	
	 </center>
   	 </div>
	    <div class="logincontainer">
    <div class="loginbox">
    	<div class="loginimage"></div>
    	 
        <div class="loginform">
        
        	<h2><bean:message key="label.login" bundle="loginResource" /></h2><!-- login form-->   
              
              
              
                <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <td colspan="2">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="85" class="loginuserpwdtxt">
                   <bean:message key="label.login.username" bundle="loginResource" />
                    </td>
                    <td width="189">
                    <%-- <html:text	property="to.userName" styleId="userName" maxlength="255" styleClass="logintxtbox width145" onkeypress="enterKeyNavigation(event)"/> --%>
                    <html:text	property="to.userName" styleId="userName" maxlength="255" styleClass="logintxtbox width145" onkeypress="return callEnterKey(event, document.getElementById('password'));"/>
                    </td>
                  </tr>
                  <tr>
                    <td class="loginuserpwdtxt">
                    <bean:message key="label.login.password" bundle="loginResource" />
                    </td>
                    <td>
                    <html:password property="pwdto.password" styleId="password" maxlength="255" styleClass="logintxtbox width145" onkeypress="enterKeyNavigation(event)"/>
                    </td>
                  </tr>           
                  <tr>
                    <td>&nbsp;</td>
                    <td valign="middle">
                    <html:button styleId="loginButton" styleClass="btn" property="Login" onclick="submitLogin()">
		            <bean:message key="button.label.login" bundle="loginResource"/></html:button>
                    </td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td><a href="#" onclick="forgotpassword()"  class="txtSmall" title="Forgot Password">
                    <bean:message key="label.login.forgotPassword" bundle="loginResource" />
                    </a></td>
                  </tr>
                </table>
       </div>
       </div>
    </div><!-- login ends-->  
   
  <b><center><span>${UDAAN_BUILD}</span></center></b>
    </html:form><!-- login form ends-->   
    
  </div><!-- main content ends -->
  <!--footer-->
  <div id="login-footer">
    <div class="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved.This site is best viewed with a resolution of 1024x768.</div>
  </div><!--footer ends-->
</div>
</body>
</html>