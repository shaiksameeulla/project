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
	font:normal 16px "Lucida Console", Monaco, monospace;
	/* font:normal 16px Arial; */
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
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/drs/preparation/drsNormPriorityDoxPrint.js"></script> -->
</head>
  
  
  
  <tr>
    <td align="left" valign="top"><table cellpadding="2" cellspacing="0" width="100%" border="0">
        <tr >
          <td width="18" align="left" valign="top"><table cellpadding="5" cellspacing="4" width="100%" border="0">
              <tr class="showTopLine">
                <td width="48%" align="left" valign="top">Total <c:out value="${param.totalCN}"/> deliveries taken by <c:out value="${param.fsName}"/><c:out value="${param.fsLastName}"/></td>
                <td width="52%" align="left" valign="top">Total No. of C/ments delivered in STP</td>
              </tr>
              <tr >
                <th align="left">&nbsp;</th>
                <th align="left">&nbsp;</th>
              </tr>
              <tr class="showBtmLine">
                <th colspan="2" align="left">All the deliveries checked &amp; tallied with address &amp; stamp on Pod by: <br />
                  <br />
                  <br />
                </th>
              </tr>
              <tr>
                <td align="left" valign="top" colspan="4"></td>
              </tr>
            </table></td>
        </tr>
        <!-- tr>
	         	<td colspan="10" align="left" valign="top">
	         	--------------------------------------------------------------------------------
	         	</td>
	         </tr> -->
        <tr>
          <td align="right" valign="top"> Ver : UDAAN 1.0 Released on 25/03/2013 </td>
        </tr>
      </table>
      </td>
  </tr>
  </html>