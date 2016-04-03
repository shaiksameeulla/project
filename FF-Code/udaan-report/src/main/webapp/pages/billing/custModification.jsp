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
			<script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript" ></script>
			<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
			<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
			<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
	        <script type="text/javascript" charset="utf-8" src="js/billing/custModification.js"></script>
			<script type="text/javascript" charset="utf-8">
			  $(document).ready( function () {
		           custModificationStartUp();
	          }); 
			 var custIDArr = new Array();
			 var custCodeArr = new Array();
			 var data = new Array();
			 var custCodeName = new Array();
			 var custShipCodeArr = new Array();
		
			 function loadCustomerName(){
				var errormsg = document.getElementById("errormessage").value;
					if (errormsg != "") {
						errormsg = wrapText(errormsg, 75);
						alert(errormsg);
					}
					document.getElementById("submitBtn").disabled = true;
					buttonDisabled("submitBtn", "btnintform");
					jQuery("#" + "submitBtn").addClass("btnintformbigdis");
					jQuery('input#newCustName').flushCache();
					<c:forEach var="custTO" items="${customerList}" varStatus="rstatus">
					data.push("${custTO.businessName}");
					custIDArr.push("${custTO.customerId}");
					custCodeArr.push("${custTO.customerCode}");
					custShipCodeArr.push("${custTO.shippedToCode}");
					custCodeName.push("${custTO.businessName}".trim() + " - "
							+ "${custTO.shippedToCode}".trim());
					</c:forEach>
					jQuery("#newCustName").autocomplete(custCodeName);
				}
			</script>
			<!-- DataGrids /-->
			</head>
			<body onload="loadCustomerName()">
	<!--wraper-->
	<div id="wraper"> 
	<html:form method="post" styleId="custModificationForm">
	          <!--header-->
	          <!--top navigation ends--> 
	          <!--header ends-->
	          <div class="clear"></div>
	          <!-- main content -->
	          <div id="maincontent">
	    <div class="mainbody">
	              <div class="formbox">
	        <h1>Consignment Modification</h1>
	        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.common.FieldsareMandatory" /></div>
	      </div>
	           <div class="formTable">
	             <table border="0" cellpadding="0" align="left" cellspacing="14">
	                  <tr>
	                    <td class="lable" ><sup class="star">*</sup>&nbsp;<bean:message key="label.stopdelivery.consignmentNo" /></td>
	                    <td><html:text property="to.congNo" styleId="congNo" styleClass="txtbox width145" maxlength="12" onkeypress="return callEnterKey(event,document.getElementById('searchBtn'));"/>
	                    <input name="Browse" type="button" id="searchBtn" value="Search" class="button"  title="Search"   onclick="getConsgBookDeatils();"/>&nbsp;</td>
	                </tr>
	             </table>
	             <table border="0" cellpadding="0" cellspacing="14" width="100%">   
                	<tr>
                	   <!-- booking information -->
                  	<td class="lable" style="font-size: 12" width="22%"><b><bean:message key="label.custModification.bookInfo" /></b></td>
                    </tr>
                    <tr>
                    <td class="label" style="font-size: 12;" align="right"><sup class="star">*</sup><bean:message key="label.custModification.ExistingCust" /></td>
                    <td><html:text property="to.bookCustName" styleId="bookCustName" readonly="true" styleClass="txtbox width340"/></td>
                	<td class="label" style="font-size: 12;" align="center" colspan="2"><sup class="star">*</sup><bean:message key="label.custModification.finalWeight" /> 
           					<html:hidden  property="to.totalConsignmentWeight" styleId="totalWeight"  value=""/>
	            			<html:text property="to.wtKg" styleClass="txtbox width60" styleId="wtKg" readonly="true"  onkeypress="return onlyNos(event,this);" tabindex="-1" maxlength="4" size="4"/>.
	            			<html:text property="to.wtGm"  styleClass="txtbox width60" styleId="wtGm" readonly="true" onkeypress="return onlyNos(event,this);" tabindex="-1"  maxlength="3" size="3" />Kgs
           			</td>
           			  
                    </tr>
                    <!-- added declared values in the screen  -->
                    <tr>
                    <td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.custModification.declaredValue" /></td>
								<td width="15%"><html:text
										property="to.declaredValue"
										styleClass="txtbox width130" styleId="declaredValue" readonly="true"
										maxlength="10" size="11"/>
								</td>
                    </tr>
                    
                    <tr>
                    <!-- for excess consignment -->
                    <td class="lable" style="font-size: 12 " align="right" ><b><bean:message key="label.custModification.excessConsignment" /></b></td>
                    </tr>
                    <tr style="margin-left: 14pt" >
                   <td class="label" style="font-size: 12 " align="center"><bean:message key="label.custModification.region" /></td>
                   <td> <html:text property="to.regionName" styleId="regionName" disabled="true" styleClass="txtbox width145" /></td>
                   <html:hidden property="to.regionId" styleId="regionId" styleClass="selectBox width130"/>
                   
                    <td class="label" style="font-size: 12" align="left" width="26%"><sup class="star">*</sup><bean:message key="label.custModification.station" />
                    &nbsp;&nbsp;<html:select
										property="to.stationId" styleId="stationList" disabled="true" onchange="getBranchesByCity();"
										styleClass="selectBox width130" onkeypress="return callEnterKey(event, document.getElementById('officeList'));">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<c:forEach var="city" items="${cityTO}" varStatus="loop">
											<option value="${city.cityId}">
												<c:out value="${city.cityName}" /> 
											</option>
										</c:forEach>
									</html:select>
									</td>
                    <td class="label" style="font-size: 12" align="left" width="26%"><sup class="star">*</sup><bean:message key="label.custModification.office" />
                    &nbsp;&nbsp;<html:select
										property="to.office" styleId="officeList" disabled="true" onchange="getNewCustomerList();"
										styleClass="selectBox width130" onkeypress="return callEnterKey(event, document.getElementById('bookingDate'));">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<c:forEach var="branch" items="${branchOffices}" varStatus="loop">
											<option value="${branch.officeId}">
												<c:out value="${branch.officeName}" />
											</option>
										</c:forEach>
									</html:select>
									</td>
                    </tr>
                    <tr>
                    <td class="label" style="font-size: 12" align="center"><sup class="star">*</sup><bean:message key="label.custModification.bookingDate" /></td>
                    <td width="24%">
              		<html:text property="to.bookingDate" styleClass="txtbox width140"  styleId="bookingDate" disabled="true" onblur="checkFutureDate(this);checkBackDate(this);" size="30" readonly="true" value="" onkeypress = "return callEnterKey(event, document.getElementById('newCustName'));"/>
            		 <a href='javascript:show_calendar("bookingDate", this.value)'/>
                          <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" />                                    
            		</td>
                    </tr>
                    <tr>
                    <!-- new information -->
                    <td class="lable" style="font-size: 12">&nbsp;<b><bean:message key="label.custModification.newInfo" /></b></td>
                    </tr>
                    <tr>
                   <!--  new customer name and code -->
                      <td width="15%" class="lable" style="font-size: 12"><sup class="star">*</sup>&nbsp;<bean:message key="label.custModification.newCust"/></td>
                    <td width="12%"> <html:text property="to.newCustName" styleId="newCustName" styleClass="txtbox width340" readonly="true" maxlength="100" onkeypress="return callEnterKey(event, document.getElementById('finalwtKg'));"/></td>
                    
                     <td class="label" style="font-size: 12" align="center" colspan="2"><sup class="star">*</sup><bean:message key="label.custModification.billWeight" />
           				<html:hidden  property="to.billingConsignmentWeight" styleId="billingWeight"  value=""/>
	            			<html:text property="to.finalwtKg" styleClass="txtbox width60" styleId="finalwtKg" disabled="true" onkeypress="return onlyNos(event,'finalwtKg');" onchange="setDefaultValOnBillingWeight();"  maxlength="4" size="4"/>.
	            			<html:text property="to.finalwtGm"  styleClass="txtbox width60" styleId="finalwtGm" disabled="true" onkeypress="return onlyNos(event,'finalwtGm');" onchange="setDefaultValOnBillingWeight();" maxlength="3" size="3" />Kgs
           				</td> 
                    </tr>
                     <!-- added declared values in the screen  -->
                    <tr>
                    <td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.custModification.declaredValue" /></td>
								<td width="15%"><html:text
										property="to.declaredValue"
										styleClass="txtbox width130" styleId="declareValue" readonly="true"
										maxlength="10" size="11" onkeypress="return numbersOnlyAllow(this, event)" onchange="isValidDecValueCNModi(this);"/>
								</td>
                    </tr>
                   </table> 
	                <table border="0" cellpadding="0" cellspacing="0" width="100%">
	  					<!-- list of hidden fields -->
	                </table>
	                <input type="hidden" name = "newCustId" id="newCustId" class="txtbox width115"/>
	                <html:hidden property="to.consgId" styleId="consgId" styleClass="txtbox width115"/>
	                <html:hidden property="to.isInvoice" styleId="isInvoice" styleClass="txtbox width115"/>
	                <html:hidden property="to.cityId" styleId="cityId" styleClass="txtbox width115"/>
	                <!-- <input type="hidden" name = "bookCustId" id="bookCustId" class="txtbox width115"/>
	                <input type="hidden" name = "newCustId" id="newCustId" class="txtbox width115"/>
	                <input type="hidden" name = "bookCustTypeCode" id="bookCustTypeCode" class="txtbox width115"/>
	                <input type="hidden" name = "newCustTypeCode" id="newCustTypeCode" class="txtbox width115"/>
	                <input type="hidden" name = "consgId" id="consgId" class="txtbox width115"/>  -->
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
				 <html:button property="Submit"  styleClass="btnintform" styleId="submitBtn" onclick="mandatoryFieldsCheck();" >
	    		<bean:message key="button.label.Submit" locale="display" />
	  			  </html:button>
				 <html:button property="modify" styleClass="btnintform"
						onclick="updateCustConsigment()" styleId="modify">
						<bean:message key="button.label.modify" locale="display" />
					</html:button>
				 <html:button property="clear"  styleClass="btnintform" styleId="clear" onclick="clearDetails()">
	    		<bean:message key="button.clear" locale="display" />
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
	<input id="errormessage" name="errormessage"  type="hidden"  value="${ERROR}"/>
	</body>
	</html>
