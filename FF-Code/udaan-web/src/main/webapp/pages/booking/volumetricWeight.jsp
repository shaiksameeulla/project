<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/cashBookingParcel.js"></script>
<script language="JavaScript"  type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/volumetricWeight.js"></script>
<script type="text/javascript" charset="utf-8">
var rowCount = '<%= request.getParameter("rowCount") %>';
var processCode = '<%= request.getParameter("processCode") %>';
if(rowCount==""){
	rowCount = 0;
}
rowCount = parseInt(rowCount);
</script>
</head>
<body onload="setDefaultVal('<%= request.getParameter("rowCount") %>')">

<!--wraper-->
<div id="wraper"> 
  <!-- header -->
  <div id="popupheader">
    <div class="ltxt">Volumetric Weight (All in cm)</div>
  
  </div>
  <!-- header ends-->
  <div id="popupheader_orange"></div>
  <!-- maincontent -->

  <div id="popupmain">
   <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
     <tr>
        <th width="7%"><bean:message key="label.cashbooking.Length"/></th>
        <td width="18%"><html:text property="to.lengths"  styleClass="txtbox width115" styleId="length" size="12" maxlength="5" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'breath\');" value=""/></td>
     </tr>
    
     
     
     <tr>
       <th width="8%"><bean:message key="label.cashbooking.Breadth"/></th>
        <td width="18%"><html:text property="to.breaths"  styleClass="txtbox width115" styleId="breath" size="12" maxlength="5" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'height\');" value=""/></td>
</tr>
<tr>
        <th width="8%"><bean:message key="label.cashbooking.Height"/></th>
        <td width="18%"><html:text property="to.heights"  styleClass="txtbox width115" styleId="height" size="11" maxlength="5" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'submit\');" value=""/></td>
      </tr>
    </table>
  </div>
  <!-- maincontent ends--> 
  <!-- footer -->
  <div id="popupfooter">
    <html:button property="Submit" styleId="submit" styleClass="btnintform" value='Submit' onclick="submitVal()"  title="Submit"/>
    <html:button property="Cancel"  styleClass="btnintform" value='Cancel' onclick="cancelVal();" title="Cancel"/>
  </div>
  <!-- footer ends--> 

</div>

<!-- wraper ends -->
</body>
</html>