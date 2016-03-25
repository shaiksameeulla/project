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
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		
		
        <script type="text/javascript" charset="utf-8" src="js/stopdelivery/stopDelivery.js"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "105",
					"sScrollX": "100%",
					"sScrollXInner":"145%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
			} );
			
			 $(document).ready( function () {
		           stopDeliveryStartUp();
	            });
		</script>
		<!-- DataGrids /-->
		</head>
		<body>
<!--wraper-->
<div id="wraper"> 
<html:form method="post" styleId="stopDeliveryForm">
          <!--header-->
          <!--top navigation ends--> 
          <!--header ends-->
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>Stop Delivery</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.common.FieldsareMandatory" /></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                    <td width="14%" class="lable"><sup class="star">*</sup> <bean:message key="label.stopdelivery.date"  /></td>
                    <td width="17%" ><html:text property="to.date" styleId="date" styleClass="txtbox width145" value="${currDate}" readonly="true"/></td>
                    <td width="19%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.consignmentNo" /></td>
                    <td width="24%"><html:text property="to.consgNo" styleId="consgNo" styleClass="txtbox width145" maxlength="12" onkeypress="enterKeyNavigation(event,'seachBtn');"/> <html:button property="search" styleClass="btnintgrid" styleId="seachBtn" onclick="getConsgDeatils();"><bean:message key="button.search" /></html:button></td>
                    <td width="13%" class="lable"><span class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.bookingdate" /></span></td>
                    <td width="13%"><html:text property="to.bookingDate" styleId="bookingDate" styleClass="txtbox width145" readonly="true"/></td>
                  </tr>
                  <tr>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.pincode" /></td>
                    <td ><html:text property="to.pincode" styleId="pincode" styleClass="txtbox width145" readonly="true"/></td>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.weight" /></td>
                    <td><html:text property="to.weight" styleId="weight" styleClass="txtbox width145" readonly="true"/>
                      &nbsp;</td>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.customer" /></td>
                    <td><html:text property="to.customer" styleId="customer" styleClass="txtbox width145" readonly="true"/>
                      </td>
                  </tr>
                  <tr>
                    <td width="14%" class="lable"><sup class="star"></sup>&nbsp;<bean:message key="label.stopdelivery.codlctopay" /></td>
                    <td width="17%" ><html:text property="to.codLcTopay" styleId="codLcTopay" styleClass="txtbox width145" readonly="true"/></td>
                    <td width="19%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.sdReason" /></td>
                    <td width="24%"><html:select property="to.stopDeliveryReason"
										styleId="reasonList" styleClass="selectBox width140"  >
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										 <%-- <c:forEach var="type" items="${reasonList}" varStatus="loop">
											<html:option value="${type.reasonId}">
												<c:out value="${type.reasonName}" />
											</html:option>
										</c:forEach>  --%>
									</html:select>                  &nbsp;</td>
                    <td class="lable"><sup class="star"></sup>&nbsp;<bean:message key="label.stopdelivery.remarks" /></td>
                   <td width="13%"><html:text property="to.remarks" styleId="remarks" styleClass="txtbox width145" maxlength="50"/></td>
                  </tr>
                 
                </table>
</div>

              <!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
          <div class="button_containerform">
   			 <html:button property="Submit" styleClass="btnintform"
					styleId="submitBtn" onclick="submitStopDelivery();">
					<bean:message key="button.submit" />
			 </html:button>
			 <html:button property="Cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="canceledStopDelivery();">
					<bean:message key="button.cancel" />
			 </html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div>
          <!-- footer ends --> 
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>
