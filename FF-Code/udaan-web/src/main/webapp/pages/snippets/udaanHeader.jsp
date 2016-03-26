<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isELIgnored="false"%>
<jsp:include page="commonTagLibrary.jsp"/>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<script type="text/javascript">
function logoutUser()
{
	var url = "./logout.do?submitName=logoutUser";
	window.location = url;
}

function returnLoginPage(){
	var url = "./home.do";
	window.location = url;
	
}
</script>

</head>
<body>
<!--wraper-->
<div id="wraper"> 
  <!--header-->
  <div id="main-header-int">
    <div class="fflogo" title="First Flight Couriers Ltd." onclick="returnLoginPage();"></div>
    <div class="welcomeMsg"> 
    
   Welcome to UDAAN, <strong>
        				<c:if test="${user ne null}">
						${welcomeUserName} <c:if test="${user.userto ne null}">(${user.userto.userCode}) </c:if>&nbsp;|
						 <a href="#" title="Logout" style="color:red;"  onclick="logoutUser();"><strong>Logout</strong></a> <br/>
        				</c:if>
        				</strong>
        				Logged In Office: <strong>${user.officeTo.officeTypeTO.offcTypeDesc}-${user.officeTo.officeName}(${user.officeTo.officeCode})</strong>&nbsp;|
        				<jsp:useBean id="now" class="java.util.Date" />
        				<fmt:formatDate value="${now}" pattern="EEE, d MMM yyyy hh:mm a" /><br/>
    					<a href="changePassword.do?submitName=showChangePassword" style="color:white;"> Change Password</a>
      
      </div>
  </div>
  
 
  <!--top navigation ends--> 
  <!--header ends-->
  <div class="clear"></div>
  <!-- main content --> 
  
  <!-- main content ends --> 
  <!-- footer--> 
  
  <!-- footer ends --> 
</div>
<!-- wrapper ends --> 
</body>
</html>
