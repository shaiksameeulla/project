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
		font:normal 15px "Verdana", Monaco, monospace;
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
<script language="JavaScript" src="js/jquery/jquery-1.4.4.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/codreceipt/codReceiptPrint.js"></script>
</head>

<html:form  method="post" styleId="codReceiptForm">
<body>

<table cellpadding="2" cellspacing="0" width="100%" border="0">
<tr>
        <td width="740" colspan="2"><table cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
             <!--  <td width="23%" align="left"><img src="images/ff_logo_cod.jpg" /></td> -->
            <center><b><bean:message key="label.print.header" /> </b></center>
            <%--  <center> <b><td width="77%" align="left"><span style="margin-left:115px; font:bold 13px Verdana, Geneva, sans-serif"> CASH / CHEQUE RECEIPT</span></td></b></center> --%>
            <center><b>CASH / CHEQUE RECEIPT </b></center><br/>
            </tr>
          </table></td>
      </tr>
    <tr>
    	<td align="left" valign="top">
        	<table cellpadding="2" cellspacing="2" width="100%" border="0">
            <tr>
              <th ><bean:message key="label.codreceipt.region"/></th>
              <td align="left" >${codReceiptDetails.regionName}</td>
              <th width="15%"><bean:message key="label.codreceipt.codReceiptNo"/></th>
              <td  align="left" >${codReceiptDetails.codReceiptNo}</td>
            </tr>
            <tr>
              <th  ><bean:message key="label.codreceipt.branch"/></th>
              <td  align="left" >${codReceiptDetails.branchName}</td>
              <th  ><bean:message key="label.codreceipt.date"/></th>
              <td  align="left" >${codReceiptDetails.currDate}</td>
            </tr>
            <tr>
              <td width="23%">Received  with thanks from</td>
              <td colspan="3"><p style="border-bottom: 1px solid black;"></p></td>
            </tr>
            <tr>
              <td>Rupees in words</td>
              <td colspan="3"><p style="border-bottom: 1px solid black;"></p></td>
            </tr>
            <tr>
              <td>By Cash / Cheque Number</td>
              <td width="37%"><p style="border-bottom: 1px solid black;"></p></td>
              <td width="6%">Dated</td>
              <td width="34%"><p style="border-bottom: 1px solid black;"></p></td>
            </tr>
            <tr>
              <td>Drawn on bank</td>
              <td colspan="3"><p style="border-bottom: 1px solid black;"></p></td>
            </tr>
            <tr>
              <td>Towards Bill Number</td>
              <td><p style="border-bottom: 1px solid black;"></p></td>
              <td>Month</td>
              <td><p style="border-bottom: 1px solid black;"></p></td>
            </tr>
            <tr>
              <th ><bean:message key="label.codreceipt.consgNo"/></th>
              <td  align="left" >${codReceiptDetails.consgNo}</td>
              <th  ><bean:message key="label.codreceipt.bookingdate"/></th>
              <td  align="left" >${codReceiptDetails.bookDate}</td>
            </tr>
            </table>
			<!-- -------------------------------------------------------------------------------- -->
       	</td>
	</tr>

    <tr>
    	<td align="left" valign="top">
        	<table cellpadding="3" cellspacing="2" width="100%" border="0" class="showLine">
              	 <tr class="showTopBtmLine">
                      <th width="13%" align="center" ><bean:message key="label.common.serialNo"/></th>
                      <th width="35%" align="center" ><bean:message key="label.codreceipt.expenseType"/></th>
                      <th width="15%" align="center"><bean:message key="label.codreceipt.amount"/></th>
                    </tr>
              	<!-- Use C:FOREACH loop to display consignment numbers -->
              	<c:forEach var="dtlsTOs" items="${codReceiptDetails.codReceiptDetailsTOs}" varStatus="status">
				    <tr>
					    <td align="center"><c:out value="${status.count }"/></td>
					    <td align="center" ><c:out value="${dtlsTOs.expenseDescription}"/></td>
					    <td align="center" ><c:out value="${dtlsTOs.expenseTotalAmount}"/></td>
				    </tr>    
				    </c:forEach>
				   <tr class="showBtmLine">
				        <td align="center"><c:out value="${codReceiptDetails.codReceiptDetailsTOs.size() + 1}"/></td>
					    <td align="center" ><c:out value="Grand Total"/></td>
					    <td align="center" ><c:out value="${codReceiptDetails.grandTotal}"/></td>
				   </tr>
              	
        	</table>
        	
        </td>
   	</tr>

</table>

</body>
</html:form>

</html>




