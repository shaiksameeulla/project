<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>

</head>
<body>
<!--wraper-->
<div id="wraper"> 
  <!--header-->
  
   <div id="topPanel">
    <div id="top_nav">
      <ul id="top_nav_menu">
<%--               <c:forEach var="menuOrder" items="${MENU_ORDER}" varStatus="loop">
      	<c:forEach var="item" items="${menuLink}">
      	 <c:if test="${item.key eq menuOrder.moduleName}">
      		<li><a href="#2" title="${item.key}"><strong><c:out value="${item.key}"/></strong></a>
	      		<ul>
	      		<c:forEach var="links" items="${item.value}">
	      		<li><a href="<c:out value='${links.urlName}'/>"><c:out value='${links.screenName}'/></a></li>
	      		<c:choose>
	      		<c:when test="${links.parentName eq null || links.parentName eq '' || links.parentName == ''}">
	      		<li><a href="<c:out value='${links.urlName}'/>"><c:out value='${links.screenName}'/></a></li>
	      		</c:when>
	      		<c:otherwise><li><a href="<c:out value='#2'/>"><c:out value='${links.parentName}'/></a></li></c:otherwise>
	      		</c:choose>
	      		
	      			
	      		</c:forEach>
	      		</ul>
        	</li>
        	</c:if>
      	</c:forEach>
      	</c:forEach> --%>
      	
      	<!-- ############# Header Menu Start ############### -->
      	<c:forEach var="item" items="${udaanTreeMenus}">
      		<li><a href="#2" title="${item.key}"><strong><c:out value="${item.key}"/></strong></a>
	      		<ul>	      		
	      		
      			<!-- ############# Sub Menu1 Start ############### -->
	      		<c:forEach var="menuNode" items="${item.value.menuNodeDOs}">
	      		<li>
				<c:if test="${empty menuNode.applScreenDO}">			
				    <!-- var1 is empty or null. -->
				    <a href="#2" title="${menuNode.menuLabel}"><strong><c:out value="${menuNode.menuLabel}"/></strong></a>
				    
				    	<ul>
      					<!-- ############# Sub Menu2 Start ############### -->
						<c:forEach var="subMenuNode" items="${menuNode.menuNodeDOs}">
			      		<li>
						<c:if test="${empty subMenuNode.applScreenDO}">			
						    <!-- var1 is empty or null. -->
						    <a href="#2" title="${subMenuNode.menuLabel}"><strong><c:out value="${subMenuNode.menuLabel}"/></strong></a>
						    
						    <ul>
      						<!-- ############# Sub Menu3 Start ############### -->
							<c:forEach var="subMenuNode2" items="${subMenuNode.menuNodeDOs}">
				      		<li>
							
							<c:if test="${not empty subMenuNode2.applScreenDO}">
							<%-- <c:choose>
							<c:when test="${subMenuNode2.applScreenDO.appName eq 'centralized'}">
							 <a href="<c:out value='${subMenuNode2.applScreenDO.urlName}'/>" target="_blank"><c:out value='${subMenuNode2.applScreenDO.screenName}'/></a>
							</c:when>
							<c:otherwise> --%>
							  <a href="<c:out value='${subMenuNode2.applScreenDO.urlName}'/>"><c:out value='${subMenuNode2.applScreenDO.screenName}'/></a>
							<%-- </c:otherwise>
							</c:choose> --%>
							  
							</c:if>	      		
				      		</li>	      			
				      		</c:forEach>				      		
      						<!-- ############# Sub Menu3 End ############### -->
						    </ul>
						    
						</c:if>
						<c:if test="${not empty subMenuNode.applScreenDO}">
						<%-- <c:choose>
							<c:when test="${subMenuNode.applScreenDO.appName eq 'centralized'}">
							 <a href="<c:out value='${subMenuNode.applScreenDO.urlName}'/>" target="_blank"><c:out value='${subMenuNode.applScreenDO.screenName}'/></a>
							</c:when>
							<c:otherwise> --%>
							  <a href="<c:out value='${subMenuNode.applScreenDO.urlName}'/>"><c:out value='${subMenuNode.applScreenDO.screenName}'/></a>
							<%-- </c:otherwise>
							</c:choose> --%>
						</c:if>	      		
			      		</li>	      			
			      		</c:forEach>
      					<!-- ############# Sub Menu2 End ############### -->
      					</ul>
				    
				</c:if>
				<c:if test="${not empty menuNode.applScreenDO}">
				<%-- <c:choose>
							<c:when test="${subMenuNode.applScreenDO.appName eq 'centralized'}">
							 <a href="<c:out value='${menuNode.applScreenDO.urlName}'/>" target="_blank"><c:out value='${menuNode.applScreenDO.screenName}'/></a>
							</c:when>
							<c:otherwise> --%>
							  <a href="<c:out value='${menuNode.applScreenDO.urlName}'/>"><c:out value='${menuNode.applScreenDO.screenName}'/></a>
							<%-- </c:otherwise>
							</c:choose> --%>
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
