<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> --%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
    <head>
    <link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">

	<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script>
 <script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script> 
	<script language="JavaScript" src="js/jquery/jquery-ui.min.js" type="text/javascript" ></script>
	<script language="JavaScript" src="js/jquery/ui/jquery.ui.core.js" type="text/javascript" ></script>
	<script language="JavaScript" src="js/jquery/ui/jquery.ui.widget.js" type="text/javascript" ></script>
	<script language="JavaScript" src="js/jquery/jquery.dimensions.js" type="text/javascript" ></script>
	<script language="JavaScript" src="js/jquery/jquery.bgiframe-2.1.2.js" type="text/javascript" ></script>
	
	<script language="JavaScript" type="text/javascript" src="js/jquery/jquery.idletimeout.js"></script>
	<script language="JavaScript" type="text/javascript" src="js/jquery/jquery.idletimer.js"></script>
<!-- 	<script language="JavaScript" type="text/javascript" src="js/firstLevelNav.js"></script> -->	
	<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
	<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
	<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
	<script type="text/javascript" src="js/calendar/ts_picker.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Welcome to UDAAN</title>
            <script>
	            jQuery(document).ready((function () {
		          	var screenWidth = screen.width;
	            	var screenHeight = screen.height;

	            	//ContentWrapper
	            	var contentHeight = "";	            	
	    			var sectionHeight = 300;
					var contentHeight = screenHeight - sectionHeight; 
					document.getElementById("contentwrapper").style.height = contentHeight+"px";	

	    			//LeftNavigation
		    		/* var sectionHeight = 50;
		    		var leftHeight = contentHeight - sectionHeight;
		    		document.getElementById("leftNav").style.background = "#ccdbee";  
		    		document.getElementById("leftNav").style.height = leftHeight;		
		    		document.getElementById("leftNav").style.overflow = "auto";   */

	    			//ContentContainer
		    		if(document.getElementById("contentContainer") != null){
		    			document.getElementById("contentContainer").style.background = "#efeef4";
			    		document.getElementById("contentContainer").style.height = leftHeight+30;		
			    		document.getElementById("contentContainer").style.overflow = "auto";  
			    		var sectionWidth = 200;
			    		document.getElementById("contentContainer").style.width = (screenWidth - sectionWidth)+"px";
				    }	
				    						    
	            }));	           
            </script>
    </head>
    <body>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
	<META HTTP-EQUIV="Expires" CONTENT="-1">    
	 <div id="errorDiv" style="color:red;float: right;">
		<html:messages name="error" id="message" bundle="errorBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="error" id="message" bundle="rateResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="error" id="message" bundle="frameworkResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	</div>
	
	<div id="warningDiv" style="color:orange" >
		<html:messages name="warning" id="message" bundle="errorBundle">
	     
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${warning ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="warning" id="message" bundle="rateResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${warning ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="warning" id="message" bundle="frameworkResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${warning ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	</div>
	
	<div id="infoDiv" style="color:blue" >
		<html:messages name="info" id="message" bundle="errorBundle">
	     
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${info ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="info" id="message" bundle="rateResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${info ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    
	    <html:messages name="info" id="message" bundle="frameworkResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${info ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	    <html:messages name="error" id="message" bundle="universalResourceBundle">
	      <script type="text/javascript">
	      $(document).ready(function(){ 
				<c:if test="${error ne null && not fn:containsIgnoreCase(message,'bundle')}" >
				alert('<bean:write name="message"/>');
				</c:if>
			});
		</script>
	    </html:messages>
	</div>
	
    	<div id="maincontainer">
	     <tiles:insert attribute="header" />
	     <tiles:insert attribute="topMenu" />
		     <div id="contentwrapper" class="contentwrapper">
		     <%
				response.setHeader("Cache-Control","no-cache"); 
				response.setHeader("Pragma","no-cache"); 
				response.setDateHeader ("Expires", -1); 
			%>
			    <table align="left" cellspacing="0" cellpadding="0" width="100%"
			    	style="min-height: 100%;height: auto !important;" border="0">
				    <tr>
				  		<td valign="top" >
				  		<%-- <tiles:insert attribute="leftMenu" /> --%></td>			  		
				  		<td valign="top" align="center"><tiles:insert attribute="body"  />
				  		</td>
				  	</tr>				  	
		   		</table>		   		
	   		</div>   			
   		</div>
   		<div>
	   	  	<tiles:insert attribute="footer"/>	   	 
	   	</div>	
   		
    </body>
</html>
