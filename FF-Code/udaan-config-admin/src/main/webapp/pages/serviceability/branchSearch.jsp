<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>

<!-- DataGrids -->
		 <script type="text/javascript" language="JavaScript" src="js/serviceability/branchSearch.js"></script> 
<!-- DataGrids /-->
</head>
<body>
<html:form action="/searchByBranch.do" styleId="searchByBranchForm">
<!--wraper-->
<div id="wraper"> 
<!-- main content -->
	<div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox"><h1>Branch Pincode Serviceability</h1>
        		<%-- <div class="mandatoryMsgf"><sup class="star"><bean:message key="symbol.common.star"/></sup><bean:message key="label.common.FieldsareMandatory"/></div> --%>
        	</div>
        <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
        	<tr>
        		<%-- <td width="11%" class="lable">Region</td>
                    <td width="26%" ><html:select  property="to.status" styleId="region" styleClass="selectBox width130" onchange="getStationsList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    <c:forEach var="regionsList" items="${regionsList}" varStatus="loop">
		              		<option value="${regionsList.regionId}" ><c:out value="${regionsList.regionName}"/></option>
		            		</c:forEach> 
                    </html:select>
                    </td> --%>
                    <td width="11%" class="lable">City</td>
                    <td width="23%"><html:select  property="to.status" styleId="station" styleClass="selectBox width130" onchange="getBranchesList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    <c:forEach var="cityTOsList" items="${cityTOsList}" varStatus="loop">
		              		<option value="${cityTOsList.cityId}" ><c:out value="${cityTOsList.cityName}"/></option>
		            		</c:forEach> 
                    </html:select>
                    </td>
                    <td width="12%" class="lable">Office</td>
                    <td width="17%"><html:select  property="to.status" styleId="branch" styleClass="selectBox width130" onchange="getPincodeList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
                    </td>
          </tr>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" class="display" width="100%" id="pincodeBranch">
	            	<thead>
	            		<tr>
	                    	<!-- <th width="3%" align="center" ><input type="checkbox" class="checkbox" name="type"/></th> -->
	                      	<th width="3%" align="left" >Serviceable Pincodes</th>  	
	                    </tr>            
	          		</thead>
          			<tbody>
          			
          			</tbody>
				</table>
						
						
						
      </div>
<!-- Grid /--> 
		</div>
	</div>
 <!-- Hidden Fields Start -->
 <%-- <html:hidden property="to.userTO.userId" styleId="userId"/> --%>
 <html:hidden property="employeeId" styleId="employeeId" value="${employeeId}"/>
 <!-- Hidden Fields End -->	
<!-- Button -->
<div class="button_containerform">
 	<%-- <html:button property="Print" styleClass="btnintform" styleId="btnPrint" onclick=""><bean:message key="button.print"/></html:button> --%>
	<%-- <html:button property="Solve" styleClass="btnintform" styleId="btnSolve" onclick=""><bean:message key="button.solve"/></html:button>
    <html:button property="Follow Up" styleClass="btnintform" styleId="btnFollowUp" onclick=""><bean:message key="button.followUp"/></html:button>
    <html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelDetails();"><bean:message key="button.clear"/></html:button> --%>
</div>
<!-- Button ends --> 
<!-- main content ends --> 
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
