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
<title>UDAAN</title>
<style type="text/css">
body{
	/* font:normal 16px "Lucida Console", Monaco, monospace; */
	/* font:normal 16px Arial; */
	font:normal 12px "Verdana", Monaco, monospace;

}
table tr .showLine td{
	border-left: 2px dashed black;
	
}
table tr .showBtmLine td, table tr .showBtmLine th{
	border-bottom: 2px dashed black;
}
table tr .showTopLine td, table tr .showTopLine th{
	border-top: 2px dashed black;
}
table tr .showTopBtmLine td, table tr .showTopBtmLine th{
	border-bottom: 2px dashed black;
	border-top: 2px dashed black;
}
</style>
<style type="text/css" media="print">
@page 
    {
        size: auto;   /* auto is the initial value */
        margin-top: 0mm;
    }
</style>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/drs/preparation/drsNormPriorityDoxPrint.js"></script>
</head>


<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr >
    	<td align="left" valign="top">
    		<center><bean:message key="label.print.header" /></center>
    		<%-- <center><b>   ${drsTo.createdOfficeTO.officeName}</b><br/></center>  --%>
    		<center><bean:message key="label.print.drs.lcCoddoxHeader" /></center>
       	</td>
    </tr>
      
     <tr>
	    <td align="left" valign="top">
			<table cellpadding="2" cellspacing="5" width="100%" border="0">
        		<tr class="showTopLine">
                	<td width="2%" align="left" ><bean:message key="label.print.branch" /></td>
                	<td width="10%" align="left"  >${drsTo.createdOfficeTO.officeName}-${drsTo.createdOfficeTO.officeCode}</td> 
                  	<td  align="left" ><bean:message key="label.print.drs.drsNo" /></td>
                  	<td width="8%" align="left" >${drsTo.drsNumber}</td> 
              	</tr>
              	<tr>
              	     
              	   <%--  <th width="10%" align="left" ><bean:message key="label.print.dateTime" /></th>
                	<td width="18%" align="left" >${drsTo.drsDate}</td>  --%>
                	<td width="10%" align="left" ><bean:message key="label.print.drs.type" /></td>
                	<td width="8%" align="left" >${drsTo.drsFor} <c:if test="${not empty drsTo.franchiseTO } "> <c:out value="${drsTo.franchiseTO.customerCode}"/></c:if><c:if test="${not empty drsTo.fieldStaffTO } "> <c:out value="${drsTo.fieldStaffTO.empCode}"/></c:if><c:if test="${not empty drsTo.coCourierTO } "> <c:out value="${drsTo.coCourierTO.vendorCode}"/></c:if> <c:if test="${not empty drsTo.baTO } "> <c:out value="${drsTo.baTO.customerCode}"/></c:if></td>
                	<td width="10%" align="left" ><bean:message key="label.pickup.customer.name" /></td>
                	<c:if test="${not empty drsTo.fieldStaffTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.fieldStaffTO.firstName}"/>   <c:out value="${drsTo.fieldStaffTO.lastName}"/></td>
                   </c:if>
                    <c:if test="${not empty drsTo.franchiseTO }"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.franchiseTO.businessName}"/>  </td>
                   </c:if>
                    <c:if test="${not empty drsTo.coCourierTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.coCourierTO.firstname}"/>   <c:out value="${drsTo.coCourierTO.lastName}"/></td>
                   </c:if>
                   
                   <c:if test="${not empty drsTo.baTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.baTO.businessName}"/> </td>
                   </c:if> 
              	</tr>
               <%-- 	<tr >
               	
                	<th width="10%" align="left" ><bean:message key="label.print.drs.type" /></th>
                	<td width="8%" align="left" >${drsTo.drsFor} <c:if test="${not empty drsTo.franchiseTO } "> <c:out value="${drsTo.franchiseTO.customerCode}"/></c:if><c:if test="${not empty drsTo.fieldStaffTO } "> <c:out value="${drsTo.fieldStaffTO.empCode}"/></c:if><c:if test="${not empty drsTo.coCourierTO } "> <c:out value="${drsTo.coCourierTO.vendorCode}"/></c:if> <c:if test="${not empty drsTo.baTO } "> <c:out value="${drsTo.baTO.customerCode}"/></c:if></td>
                	<c:if test="${not empty drsTo.fieldStaffTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.fieldStaffTO.firstName}"/>   <c:out value="${drsTo.fieldStaffTO.lastName}"/></td>
                   </c:if>
                    <c:if test="${not empty drsTo.franchiseTO }"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.franchiseTO.businessName}"/>  </td>
                   </c:if>
                    <c:if test="${not empty drsTo.coCourierTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.coCourierTO.firstname}"/>   <c:out value="${drsTo.coCourierTO.lastName}"/></td>
                   </c:if>
                   
                   <c:if test="${not empty drsTo.baTO}"> 
                    <td width="8%" align="left" valign="top">  <c:out value="${drsTo.baTO.businessName}"/> </td>
                   </c:if> 
           		</tr> --%>
              	<tr >
                	<%-- <th width="2%" align="left" ><bean:message key="label.print.drs.fsName" /></th>
                	<td width="16%" align="left" >${drsTo.fieldStaffTO.firstName} ${drsTo.fieldStaffTO.lastName}</td> --%>
                	<td width="10%" align="left" ><bean:message key="label.print.drs.timeOut" /></td>
                	<td width="12%" align="left">${drsTo.fsOutAlias}</td>
                	<td width="2%" align="left" ><bean:message key="label.print.loadNo" /></td>
                	<td width="10%" align="left" >${drsTo.loadNumber}</td>
                	<%-- <th width="10%" align="left" ><bean:message key="label.print.drs.timeIn" /></th>
                	<td width="10%" align="left">${drsTo.fsInAlias}</td> --%>
                </tr>
           </table>
       	</td>
	</tr>
	</table>
</html>