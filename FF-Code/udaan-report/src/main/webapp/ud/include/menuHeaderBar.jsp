<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/ud/include/taglib_includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <link href="ud/css/global.css" rel="stylesheet" type="text/css" />
	<!-- DataGrids -->
	<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
	<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
	<link href="ud/css/global.css" rel="stylesheet" type="text/css" />
	<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
	<title>First Flight Couriers</title>
</head>
<script type="text/javascript">

function logoutUser()
{
	var url = "./logout.do?submitName=logoutUser";
	window.location = url;
}

function returnLoginPage()
{
	var url = "./home.do";
	window.location = url;
}
</script>
<body>
 	<div id="wraper">
 	<div id="main-header-int">
			    	<div class="fflogo" title="First Flight Couriers Ltd." onclick="returnLoginPage();"></div>
			    		<div class="welcomeMsg"> Welcome to UDAAN, 
			    			<strong>	
			        				<c:if test="${user ne null}">
									${welcomeUserName}&nbsp;|
									 <a  href="#" title="Logout" style="color:red;"  onclick="logoutUser();"><strong>Logout</strong></a> <br/>
			        				</c:if>
			        		</strong>Logged In Office: 
			        		<strong>${user.officeTo.officeTypeTO.offcTypeDesc}-${user.officeTo.officeName}</strong>&nbsp;|
			        				<jsp:useBean id="now" class="java.util.Date" />
			        				<fmt:formatDate value="${now}" pattern="EEE, d MMM yyyy hh:mm a" /><br/>
			    					<a href="changePassword.do?submitName=showChangePassword" style="color:white;"> Change Password</a>
			     		</div>
			  	</div>
		  	
		    	<div id="topPanel">
		   			 <div id="top_nav">
		      			<ul id="top_nav_menu">
		      			
						      	<!-- ############# Header Menu Start ############### -->
						      	<c:forEach var="item" items="${udaanTreeMenus}">
						      		<li><a  title="${item.key}"><strong><c:out value="${item.key}"/></strong></a>
							      		<ul>	      		
						      			<!-- ############# Sub Menu1 Start ############### -->
							      		<c:forEach var="menuNode" items="${item.value.menuNodeDOs}">
							      		<li>
										<c:if test="${empty menuNode.applScreenDO}">			
										    <!-- var1 is empty or null. -->
										    <a  title="${menuNode.menuLabel}"><strong><c:out value="${menuNode.menuLabel}"/></strong></a>
										    
										    	<ul>
						      					<!-- ############# Sub Menu2 Start ############### -->
												<c:forEach var="subMenuNode" items="${menuNode.menuNodeDOs}">
										      		<li>
														<c:if test="${empty subMenuNode.applScreenDO}">			
														    <!-- var1 is empty or null. -->
														    <a title="${subMenuNode.menuLabel}"><strong><c:out value="${subMenuNode.menuLabel}"/></strong></a>
														    
														    <ul>
								      						<!-- ############# Sub Menu3 Start ############### -->
															<c:forEach var="subMenuNode2" items="${subMenuNode.menuNodeDOs}">
													      		<li>
																	<c:if test="${not empty subMenuNode2.applScreenDO}">
																		<c:choose>
																			 <c:when test="${subMenuNode2.applScreenDO.appName eq 'centralized'}">
																				<a href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${subMenuNode2.applScreenDO.screenCode}'/>&moduleName=<c:out value='${subMenuNode2.applScreenDO.moduleName}'/>&appName=udaan-config-admin" target="_blank"><c:out value='${subMenuNode2.applScreenDO.screenName}'/></a>
																			</c:when> 
																			<c:otherwise>
																			  <a href="<c:out value='${subMenuNode2.applScreenDO.urlName}'/>"><c:out value='${subMenuNode2.applScreenDO.screenName}'/></a>
																			</c:otherwise>
																		</c:choose>
																	</c:if>	      		
													      		</li>	      			
												      		</c:forEach>				      		
								      						<!-- ############# Sub Menu3 End ############### -->
														    </ul>
														</c:if>
														
														<c:if test="${not empty subMenuNode.applScreenDO}">
															<c:choose>
																<c:when test="${subMenuNode.applScreenDO.appName eq 'centralized'}">
																	<a href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${subMenuNode.applScreenDO.screenCode}'/>&moduleName=<c:out value='${subMenuNode.applScreenDO.moduleName}'/>&appName=udaan-config-admin" target="_blank"><c:out value='${subMenuNode.applScreenDO.screenName}'/></a>
																</c:when>
																<c:otherwise>
																  <a href="<c:out value='${subMenuNode.applScreenDO.urlName}'/>"><c:out value='${subMenuNode.applScreenDO.screenName}'/></a>
																</c:otherwise>
															</c:choose>
														</c:if>	      		
										      		</li>	      			
									      		</c:forEach>
						      					<!-- ############# Sub Menu2 End ############### -->
						      					</ul>
											</c:if>
											
											<c:if test="${not empty menuNode.applScreenDO}">
												<c:choose>
													<c:when test="${menuNode.applScreenDO.appName eq 'centralized'}">
														<a href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${menuNode.applScreenDO.screenCode}'/>&moduleName=<c:out value='${menuNode.applScreenDO.moduleName}'/>&appName=udaan-config-admin" target="_blank"><c:out value='${menuNode.applScreenDO.screenName}'/></a>
													</c:when>
													<c:otherwise>
													  <a href="<c:out value='${menuNode.applScreenDO.urlName}'/>"><c:out value='${menuNode.applScreenDO.screenName}'/></a>
													</c:otherwise>
												</c:choose>
											</c:if>	      		
							      		</li>	      			
							      		</c:forEach>	      		
						      				<!-- ############# Sub Menu1 End ############### -->
							      	</ul>
						        	</li>
						      	</c:forEach>
						     	 <!-- ############# Header Menu End ############### -->
		      			</ul>
		    			<script type="text/javascript">var topLevelUL = "#top_nav_menu";</script> 
		   				<script type="text/javascript" src="js/top-menu.js"></script> 
		    		</div>
		 		 </div>
	 </div>

</body>
</html>