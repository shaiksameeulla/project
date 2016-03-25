<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/errorhandling/errorHandlingScreen.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>			
</head>

<body>
<html:form styleId="errorHandingForm">
<%-- <html:form action="/leadsView.do" method="post" styleId="leadsViewForm">  --%> 
<%-- <html:hidden styleId="rowCount" property="rowCount"/>   --%>
 <div id="wraper">  
	 <div id="maincontent">
	    <div class="mainbody">
	      <div class="formbox">
	        <h1>Error Handling</h1>
	       <%--  <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mandatory" /></div> --%>
	      </div>
	      <div class="formTable">
	        <table border="0" cellpadding="0" cellspacing="7" width="100%">               
	          
	         <tr>
	            <td class="lable"><bean:message key="label.effectiveFrom" /></td>
	            <td>
		     		<html:text styleId="effectiveFromStr" property="to.effectiveFromStr" styleClass="txtbox width140" size="30" readonly="true" disabled="true" value="${effectiveFromDate}"/>
	            </td>
	            
	            <td class="lable"><bean:message key="label.effectiveTo" /></td>
	            <td>
		     		<html:text styleId="effectiveToStr" property="to.effectiveToStr"  styleClass="txtbox width140" size="30" readonly="true" disabled="true" value="${effectiveToDate}"/>
				</td>
									        
	          </tr>
	
		   </table>
	     </div>
		
         <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="transactionDetails" >
            <thead>
             	<tr>
                    <th width="1%"><bean:message key="label.error.handling.srNo"/></th>  
                    <th width="2%" align="center"><bean:message key="label.error.handling.businessTransaction"/></th>
                     <th width="7%"><bean:message key="label.error.handling.errorDescription"/></th>         
                </tr>            
          </thead>
          <tbody>
     			<c:set var="rowID" value="1" />
				<c:forEach var="itemDtls" items="${errorHandlingToList}" varStatus="stat">
					<tr class="${rowID % 2 == 0 ? 'even' : 'odd'}">
					<td>${rowID}</td> 
					<td>${itemDtls.customerNo}</td>
					<td>${itemDtls.exception}</td>											
					</tr>
				<c:set var="rowID" value="${rowID + 1}" />
				</c:forEach>
          </tbody>
       </table>
          
      </div>  		
      		<div id="allButtons" style="float: right;">		
				 <input name="Re-Process" type="button" value="Re-Process" class="btnintform" title="Clear" />
			</div>
	</div>
            
</div>

</div>

</html:form>
</body>
</html>
