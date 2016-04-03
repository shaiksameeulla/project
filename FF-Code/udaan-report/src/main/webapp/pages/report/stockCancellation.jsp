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
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/report/stockCancellationReport.js"></script>
<script type="text/javascript" charset="utf-8"
 src="js/calendar/ts_picker.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		transferStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'consignmentReportForm');
		}
	});
</script>
</head>
<body>
<!--wraper-->

		
			<form method="post" id="consignmentReportForm" name="consignmentReportForm"
		action="/udaan-report/stockCancellationReport.do?submitName=getStockCancellationReport">
<div id="wraper">
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
           	<div class="formbox">
       			<h1><bean:message key="label.stockreport.stockCancellationReport" /></h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory" /></div>
    		</div>
           	<div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="12" width="100%">
                	
                  	<tr>
            			<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="label.stockreport.region" /></td>
            			
            			<td><select name="to.originRegion" id="originRegion" class="selectBox width130" onchange="getOrgStationsList();">            			
                				<option value=""><bean:message key="stock.label.select" /></option>                          		
                          			<logic:present name="regionTo" scope="request">
											<logic:iterate id="regions" name="regionTo">
												<c:choose>
													<c:when test="${ regions.regionId==branchUserRegion}">
														<option value="${regions.regionId}" selected="selected" >
															<c:out value="${regions.regionName}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${regions.regionId}">
															<c:out value="${regions.regionName}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>                    				
              				</select></td>
              			
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="label.stockreport.station" /></td>
            			<td>
            			
            			
            			<select name="to.originStation" id="originStation" class="selectBox width130" onchange="getBranchList('originStation')">
									<option value=""><bean:message key="label.common.select"/></option>
						</select>
            				
            				
								
								
              			</td>
            			
            			
          			</tr>
                  	<tr>
            			<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="label.stockreport.branch" /></td>
            			<td width="18%">
            				<select name="to.branch" id="branchList" class="selectBox width130" onchange="getCustomerList('branchList')">
									<option value=""><bean:message key="label.common.select"/></option>
								</select>
              			</td>
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="label.stockreport.periodFrom" /></td>
            			<td width="25%">          				
            				
            				
							
							<input type="text" name="to.dispatchDate" style="height: 20px" value="" 
													id="startDate" class="txtbox width130" readonly="readonly"> <a href="#"
														onclick="javascript:show_calendar('startDate', this.value)"
														title="Select Date"><img src="images/icon_calendar.gif"
															alt="Search" width="16" height="16" border="0"
															class="imgsearch" /></a>	
							
									
              			</td>
          			</tr>
          			
          			<tr>
          			<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="label.stockreport.periodTo" /></td>
            			<td width="18%">
            				
			            			
			            			<input type="text" name="to.dispatchDate1" style="height: 20px" value="" 
													id="endDate" class="txtbox width130" readonly="readonly"> <a href="#"
														onclick="javascript:show_calendar('endDate', this.value)"
														title="Select Date"><img src="images/icon_calendar.gif"
															alt="Search" width="16" height="16" border="0"
															class="imgsearch" /></a>
			            			            			
			            			
              			</td>
            			<td width="37%" class="lable" colspan="2"><bean:message key="label.stockreport.product" /></td>
            			<td width="25%">
            				<select name="to.productTo" id="productTo" class="selectBox width130" >
									<option value="" ><bean:message key="label.common.select"/></option><c:forEach var="product" items="${productTo}" varStatus="loop"><option value="${product.itemTypeId}"><c:out value="${product.itemTypeName}"/></option></c:forEach></select>

              			</td>
            			
          			</tr>
          			    	<tr>
            			<td width="16%" class="lable"><bean:message key="label.stockreport.client" /></td>
            			<td width="18%">
            				<select name="to.customerList" id="customerList" class="selectBox width130" >
									<option value=""><bean:message key="label.common.select"/></option>
								</select>
              			</td>
            			
          			</tr>
          			
          				
          			
          			
          	
          			
          			
          			
          			
                </table>
      		</div>
         
		</div>
    </div>
	<!-- Button -->
	<div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="printReport()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
</div>
</form>
<!--wraper ends-->
</body>
</html>
