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
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript" src="js/drs/list/listDrs.js"></script>

<!-- DataGrids /-->
</head>
<body>
<html:form method="post" styleId="listDrsForm">
<!--wraper-->
<div id="wraper">
          
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>List DRS</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are mandatory</div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
            <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.common.date" /> <strong><bean:message key="label.common.slash" /></strong> <bean:message key="label.common.time" /><bean:message key="label.common.colon" /></td>
            <td width="19%" ><html:text styleId="drsDateTimeStr" property="to.drsDateTimeStr" styleClass="txtbox width145" readonly="true" tabindex="-1"/></td>
            <td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.common.office" /> &nbsp;<bean:message key="label.common.code" /></td>
            <td width="21%"> <html:text styleId="drsOfficeName" property="to.drsOfficeName" styleClass="txtbox width145" readonly="true" tabindex="-1"/></td>
            <td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.manifest.loadNo" /></td>
            <td width="17%"><html:select property="to.loadNumber" styleId="loadNumber" styleClass="selectBox width140" onchange="clearTableGrid()">
                          	<logic:present name="loadNumberMap" scope="request">
                          	<html:optionsCollection name="loadNumberMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select></td>
          </tr>
                  <tr>
            <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.list.staff" /></td>
            <td width="19%"><html:select property="to.drsFor" styleId="drsPartyId" styleClass="selectBox width140" onchange="clearTableGrid()" onkeydown="return enterKeyNav('Find',event.keyCode);">
                			<html:option value=""><bean:message key="label.common.select" /></html:option>
                          	<logic:present name="dlvDrsEmpMap" scope="request">
                          	<html:optionsCollection name="dlvDrsEmpMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select></td>
            <td class="lable">&nbsp;</td>
            <td width="21%">&nbsp;</td>
            <td width="15%" class="lable">&nbsp;</td>
            <td width="17%">&nbsp;</td>
          </tr>
                </table>
</div>

      <!-- Button -->
        <div class="button_containerform">
                <html:button property="Find" styleClass="btnintform" styleId="Find" title="List DRS" onclick="search()">
   			 <bean:message key="label.drs.list.button" /> </html:button>
                </div>
        <!-- Button ends --> 
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="listGrid" width="100%">
                  <thead>
            <tr>
                      <th width="6%" align="center" ><bean:message key="label.serialNo" />
                      </th>
                      <th width="94%" align="center"><bean:message key="label.drs.list" /></th>
                    </tr>
          </thead>
                  <tbody>
            <c:forEach var="drsDtls" items="${listDrsForm.to.listDrsDetails}" varStatus="loop">
            		<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}"> 
            		<td align="center"><c:out value='${loop.count}'/></td>
                <td align="center" >
                    <a href="#" onclick="navigateToDrsPage('${drsDtls.url}')">
                      <c:out value='${drsDtls.drsNumber}'/>
                   </a> 
                </td>
            		</tr>
            		</c:forEach>
            
          </tbody>
                </table>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
         
          <!-- Button ends --> 
           <!--hidden fields start from here --> 
           <jsp:include page="/pages/drs/drsCommon.jsp" />
            <!--hidden fields ENDs  here --> 
          <!-- main content ends --> 
          
        </div>
<!--wraper ends-->

</html:form>
</body>
</html>
