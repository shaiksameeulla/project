<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/complaints/searchServiceRequest.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/complaints/serviceRequestCommon.js"></script>

        <script language="javascript" type="text/javascript">
       

		</script>

        </head>
        <body>
<!--wraper-->
<html:form styleId="searchServiceRequestForm" >
<div id="wraper"> 
         
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
          <h1><bean:message key="label.complaints.service.search.header"/></h1>
          <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.common.FieldsareMandatory"/></div>
        </div>
 <div class="formTable">
           <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
              <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;Service Type :</td>
              <td width="17%" >
				<html:select styleId="searchType" property="to.searchType" styleClass="selectBox width130" onkeydown="return enterKeyNav('searchNumber',event.keyCode);">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="searchCategoryList" scope="request">
                          	<html:optionsCollection name="searchCategoryList" label="value" value="key"/>
                          	</logic:present>
				</html:select>
      		
      		</td>
              <td width="17%" class="lable"><sup class="star">*</sup><bean:message key="label.complaints.service.number"/> </td>
             <td><html:text property="to.searchNumber" styleClass="txtbox width130" styleId="searchNumber"    maxlength="12" onkeydown="return enterKeyForSearch(event.keyCode)" onkeypress="return OnlyAlphabetsAndNos(event);"/></td>
              <td width="42%"><input name="Search2" type="button" value="Search" class="button"  title="Search"  onclick="searchServiceRequestDtls()"/></td>
            </tr>
                      
                    </table>
        </div>            
                   
                <div class="column">
    <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="listGrid" width="100%">
                  <thead>
            <tr>
                      <th width="6%" align="center" ><bean:message key="label.complaints.sino" />
                      </th>
                      <th width="18%" align="center"><bean:message key="label.complaints.serviceRequestNo" /></th>
                       <th width="18%" align="center"><bean:message key="label.complaints.linked.serviceReqNo" /></th>
                        <th width="18%" align="center"><bean:message key="label.complaints.cnno" /></th>
                         <th width="18%" align="center"><bean:message key="label.complaints.linked.bref" /></th>
                          <th width="18%" align="center"><bean:message key="label.complaints.phone.no" /></th>
                    </tr>
                    
          </thead>
                  <tbody>
            <c:forEach var="serviceRequestDtls" items="${searchServiceRequestForm.to.gridDtls}" varStatus="loop">
            		<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}"> 
            		<td align="center"><c:out value='${loop.count}'/></td>
                <td align="center" >
                    <a href="#" onclick="navigateToComplaintScreen('${serviceRequestDtls.serviceRequestNo}')">
                      <c:out value='${serviceRequestDtls.serviceRequestNo}'/>
                   </a> 
                </td>
                <td>
                <c:out value='${serviceRequestDtls.linkedServiceReqNo}'/></td>
                  <td><c:out value='${serviceRequestDtls.consignmentNumber}'/></td>
                   <td><c:out value='${serviceRequestDtls.bookingReferenceNo}'/></td>
                    <td><c:out value='${serviceRequestDtls.callerPhone}'/></td>
            		</tr>
            		</c:forEach>
            
          </tbody>
                </table>
      </div>
              <!-- Grid /--> 
     
        
            </div>
   </div>
 		 </div> 
 		 </div>
  </html:form>
        
</body>
</html>
