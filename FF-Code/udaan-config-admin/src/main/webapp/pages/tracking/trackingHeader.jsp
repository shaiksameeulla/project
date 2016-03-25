<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>    
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>    

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<script type="text/javascript" charset="utf-8" src="js/tracking/consignmentTracking.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script type="text/javascript" charset="utf-8"></script>
<!-- DataGrids /-->

<script>  
$(function() {    $( "#tabs" ).tabs();  });  
      </script>
<!-- Tabs ends /-->
</head>
 <body>
<!--wraper-->
<div id="wraper">
<html:form action="/consignmentTrackingHeader.do" method="post"  styleId="consignmentTrackingHeaderForm"> 
<!-- main content -->
	<div id="maincontent">
    	<div class="formbox">
        	<h1><bean:message key="label.tracking.consignmenttracking.header"/></h1>
        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      	</div>
        <div class="formTable">
        	<table border="0" cellpadding="0" cellspacing="5" width="100%">
            	<tr>
		            <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
		            <td width="26%" >
		            	
					
					<html:select property="to.type"  styleId="typeName"  styleClass="selectBox width140">
                          <html:option value="" >--Select--</html:option>
                   		<c:forEach var="type" items="${typeNameTo}"  varStatus="loop">
		                  <html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>
		                </c:forEach>
                </html:select>
		            </td>
		            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.consignmentorrefno"/></td>
		            <td width="18%"><html:text property="to.consgNumber" styleId="consgNumber" styleClass="txtbox width145"  />
		            	&nbsp;<html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackconsignment();">
								<bean:message key="button.label.track"/></html:button>
					</td>
          		</tr>
			</table>
      	</div>
	</div>
<!-- main content ends -->
</html:form>
</div>
<!--wraper ends-->
</body>
</html>