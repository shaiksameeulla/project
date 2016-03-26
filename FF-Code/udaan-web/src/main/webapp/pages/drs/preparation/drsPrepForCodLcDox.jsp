<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script> DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript" src="js/drs/preparation/drsPrepForCodLcDox.js"></script>
<script type="text/javascript" src="js/drs/drsPrint.js"></script>
<!-- DataGrids /-->
</head>
<body>
<html:form method="post" styleId="codLcDoxDrsForm">
<!--wraper-->
<div id="wraper"> 
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.drs.prep.codLcDox"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.cashbooking.mandatoryField" /></div>
      </div> 
      <!--  Header START-->
               <jsp:include page="/pages/drs/drsHeader.jsp" />
              
      <!--  Header END-->
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.drs.prep.npdox.details" /></div>
                </div>
                 <table cellpadding="0" cellspacing="0" border="0" class="display" id="drsPrepCodLcDox" width="100%">
 			<thead>
            	<tr>
                      <th width="3%" align="center" ><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('chkbx',this.checked);" tabindex="-1"/></th>
                      <th width="4%" align="center" ><bean:message key="label.creditCustBookingDox.serialNo" /></th>
                      <th width="10%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.consignmentNo" /></th>
                      <th width="10%"><sup class="star">*</sup>&nbsp;<bean:message key="label.misrouteGrid.Origin" /></th>
                      <th width="10%"><bean:message key="label.drs.prep.codLc.codAmount"/></th>
                      <th width="10%"><bean:message key="label.drs.prep.codLc.LCAmount"/></th>
                      <th width="10%"><bean:message key="label.drs.prep.codLc.toPayAmount"/></th>
                      <th width="10%"><bean:message key="label.drs.prep.codLc.otherCharges"/></th>
                      <th width="10%"><bean:message key="label.drs.prep.codLc.ba.amount"/></th>
                </tr>
          	</thead>
            <tbody>
            <c:forEach var="drsCodLCSeriesDtls" items="${codLcDoxDrsForm.to.codLcDrsDetailsToList}" varStatus="loop">
            		<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}"> 
            			<td align="center"></td>
            			<td align="center"><c:out value='${loop.count}'/></td>
                		<td align="center" ><html:text styleId="rowConsignmentNumber${loop.count}" property="to.rowConsignmentNumber" value="${drsCodLCSeriesDtls.consignmentNumber}" styleClass="txtbox width140" readonly="true" /></td>
                		<td align="center" ><html:text styleId="rowOriginCityName${loop.count}" property="to.rowOriginCityName" value="${drsCodLCSeriesDtls.originCityName}" styleClass="txtbox width110" readonly="true" />
                     		<html:hidden styleId="rowConsignmentId${loop.count}" property="to.rowConsignmentId" value="${drsCodLCSeriesDtls.consgnmentId}"/>
                     		<html:hidden styleId="rowOriginCityId${loop.count}" property="to.rowOriginCityId" value="${drsCodLCSeriesDtls.originCityId}"/>
                			<html:hidden styleId="rowOriginCityCode${loop.count}" property="to.rowOriginCityCode" value="${drsCodLCSeriesDtls.originCityCode}"/>
                		</td>
                		<td align="center" ><html:text styleId="rowCodAmount${loop.count}" property="to.rowCodAmount" value="${drsCodLCSeriesDtls.codAmount}" styleClass="txtbox width110" readonly="true" /></td>
                		<td align="center" ><html:text styleId="rowLCAmount${loop.count}" property="to.rowLCAmount" value="${drsCodLCSeriesDtls.lcAmount}" styleClass="txtbox width110" readonly="true" /></td>
               			<td align="center" ><html:text styleId="rowToPayAmount${loop.count}" property="to.rowToPayAmount" value="${drsCodLCSeriesDtls.toPayAmount}" styleClass="txtbox width110" readonly="true" /></td>
                		<td align="center" ><html:text styleId="rowOtherCharges${loop.count}" property="to.rowOtherCharges" value="${drsCodLCSeriesDtls.otherCharges}" styleClass="txtbox width110" readonly="true" /></td>
                      	<td align="center" ><html:text styleId="rowBaAmount${loop.count}" property="to.rowBaAmount" value="${drsCodLCSeriesDtls.baAmount}" styleClass="txtbox width110" readonly="true" /></td>
            		</tr>
            		</c:forEach> 
             </tbody>
                </table>
      </div>
              
              <!-- Grid /--> 
            </div>
             <!--hidden fields start from here --> 
           <jsp:include page="/pages/drs/drsCommon.jsp" />
            <!--hidden fields ENDs  here --> 
    
  </div>
           <!-- Button -->
<jsp:include page="/pages/drs/preparation/drsPrepButtonsContainer.jsp" />
          <!-- Button ends --> 
          <!-- main content ends --> 
          
          
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
            