<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN &gt; Login Screen</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="wraper">
		<!--header-->
		<div id="main-header">
			<div class="login-fflogo" title="First Flight Couriers Ltd."></div>
			<div class="login-apptext"
				title="UDAAN - Unified Dynamic Access to Agile Network"></div>
		</div>
		<div class="errorcontainer">
			<div class="msgcontainer">
				<div class="noSrvcs" id="noRecordsFound">
					<div class="sadFaceImg"></div>
					<div class="errormsg">
						<html:messages id="message" bundle="frameworkResourceBundle">
							<img src="images/error.png" />
							<bean:write name="message" />
						</html:messages>
					</div>
				</div>

			</div>
		</div>
		<div id="login-footer">
			<div class="footer">&copy; 2013 Copyright First Flight Couriers
				Ltd. All Rights Reserved.This site is best viewed with a resolution
				of 1024x768.</div>
		</div>
		<!--footer ends-->
	</div>
</body>
</html>