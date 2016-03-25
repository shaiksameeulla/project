 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size:11px;
	margin:0px;
	padding:0px;
	border: 0px solid #000;
}
div, p, h1, h2, h3, ul, img {
	padding:0;
	margin:0;
}
ul {
	list-style-type:none;
}
.clear {
	clear: both;
}
#wraperform {
	width:1003;
	margin-top:0px;
	margin-right:15px;
	margin-bottom:0px;
	margin-left:15px;
	border:1px solid #000;
" 	/*background:url(../images/bg_img.gif) repeat-y;*/
 border: 0px solid #000;
}
.applFormArea {
	font:normal 12px Arial, Helvetica, sans-serif;
	color:#000;
	line-height:16px;
	text-align:left;
	border:0px;
}
.formContainer .formContainerBlue {
	margin:0px;
}
.formContainerBlue {
	border:1px #eb7619 solid;
	background:#f9f9f9;
}
.formContainerBlue table {
	border-left:1px #f1f1f1 solid;
	border-top:1px #f1f1f1 solid;
}
.formContainerBlue table td {
	border-bottom:1px #fff solid;
	border-right:1px #fff solid;
	padding:5px;
	font:normal 11px Verdana, Geneva, sans-serif;
	color: #000;
}
.formContainerBlue table th {
	border-bottom:1px #fff solid;
	border-right:1px #fff solid;
	font-size:11px;
	background:#213b78;
	padding:6px;
	font:bold 11px Verdana, Geneva, sans-serif;
	color: #fff;
}
.formboxadd {
	margin:1px;
	padding:6px;
	border: 0px #9a9a9a solid;
}
.formboxadd h1 {
	border-style:none;
	font:normal 12px Verdana, Geneva, sans-serif;
	color:#000;
	margin:4px;
	padding:6px;
}
.txt {
	border-style:none;
	font:normal 11px Verdana, Geneva, sans-serif;
	color:#000;
	padding:2px;
	margin:0;
}

.txt1 {
	border-style:none;
	font:normal 12px Verdana, Geneva, sans-serif;
	color:#000;
}
.txt2 {
	border-style:none;
	font:bold 13px Verdana, Geneva, sans-serif;
	color:#000;
	text-align:center;
	text-decoration: underline;
}
.txt3 {
	border-style:none;
	font:normal 11px Verdana, Geneva, sans-serif;
	color:#000;
	padding:2px;
	margin:0;
	text-decoration:underline;
}
.txtheader {
	font:bold 21px Verdana, Geneva, sans-serif;
	color:#051a49;
	position:relative;
	padding:5px;
}
.bulletContainer{
	list-style:none;
	font:normal 10px Verdana, Geneva, sans-serif;
	
}

/* ################# Footer ################# */

#main-footer {
	width:100%;
	float:left;
	font-family: Verdana, Geneva, sans-serif;
	font-kerning: auto;
	font-size: 11px;
	font-size-adjust: none;
	font-stretch: normal;
	font-style: normal;
	font-variant: normal;
	font-weight: normal;
	color:#fff;
	border-top:#fff 0px solid;
	margin:0 auto;
	height: 29px;
	background-color:#1d407a;
	bottom: 0;
}
#footer {
	height:auto;
	margin:7px auto;
	text-align:center;
}
</style>
<script type="text/javascript" src="js/stockmanagement/stockIssue.js"></script>
</head>
<body onload="printpage();">
<!-- <body> -->
<!--wraper-->
<html:form method="post" styleId="stockIssueForm">
<div id="wraperform" > 
  <!--header-->
  <div>
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
      <tr>
        <td width="36%"><img src="images/FirstFlight.jpg" width="164" height="71" border="0" /></td>
        <td width="64%" class="txtheader"><bean:message key="stock.label.issue.print.header"/></td>
      </tr>
    </table>
  </div>
  <!--top navigation--> 
  
  <!--top navigation ends--> 
  <!--header ends-->
  <div class="clear"></div>
  <!-- main content -->
  <div id="maincontent">
    <div class="mainbody">
      <div class="formboxadd">
        <h1><strong><bean:message key="stock.label.issue.print.rhoAddress"/></strong><span class="txt">${stockIssueForm.to.RHOAddress1},${stockIssueForm.to.RHOAddress2},${stockIssueForm.to.RHOAddress3},${stockIssueForm.to.RHOPincode}</span> <br />
          <strong><bean:message key="stock.label.issue.print.corpAddress"/></strong> <span class="txt">${stockIssueForm.to.CORPAddress1},${stockIssueForm.to.CORPAddress2},${stockIssueForm.to.CORPAddress3},${stockIssueForm.to.CORPPincode}</span> <br />
          <strong><bean:message key="stock.label.issue.print.billNo"/></strong> <span class="txt1">${stockIssueForm.to.stockIssueNumber}</span> <br />
          <strong><bean:message key="stock.label.issue.print.billDate"/></strong> <span class="txt1">${stockIssueForm.to.issueDateStr}</span></h1>
      </div>
      <div class="txt2"><bean:message key="stock.label.issue.print.BAHeader"/></div>
      <div class="formboxadd">
        <table cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
            <td width="4%" valign="top"><span class="txt1" style="margin:9px;"><strong><bean:message key="stock.label.issue.print.to"/></strong></span></td>
            <td width="96%"><span class="txt1">${stockIssueForm.to.recipientName}<br />
              ${stockIssueForm.to.address1},<br />
              ${stockIssueForm.to.address2},<br />
             ${stockIssueForm.to.address3} -  ${stockIssueForm.to.pincode}<br />
			</span></td>
          </tr>
        </table>
      </div>
      <div class="applFormArea" style="margin:3px">
        <div class="formContainerBlue"  margin-top:"2px;">
          <table border="0" class="formTable" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <th width="33%" align="center"><bean:message key="stock.label.issue.print.nameOfItems"/></th>
              <th width="13%" align="center"><bean:message key="stock.label.issue.print.sNoFrom"/></th>
              <th width="13%" align="center"><bean:message key="stock.label.issue.print.sNoTo"/></th>
              <th width="14%" align="center"><bean:message key="stock.label.issue.print.quantity"/></th>
              <th width="13%" align="center"><bean:message key="stock.label.issue.print.rate"/></th>
              <th width="14%" align="left"><bean:message key="stock.label.issue.print.amount"/></th>
            </tr>
             <c:forEach var="itemDtls" items="${stockIssueForm.to.issueItemDetls}" varStatus="loop">
            <tr>
              <td align="left">${itemDtls.description}</td>
              <td align="center">${itemDtls.startSerialNumber}</td>
              <td align="center">${itemDtls.endSerialNumber}</td>
              <td align="right">${itemDtls.issuedQuantity}</td>
              <td align="right">${itemDtls.ratePerUnitQuantity}</td>
              <td align="right">${itemDtls.itemPrice}</td>
            </tr>
              </c:forEach>
            <tr>
              <td align="left"><bean:message key="stock.label.issue.print.subTotal"/></td>
              <td align="left">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right"><strong>${stockIssueForm.to.paymentTO.totalAmountBeforeTax}</strong></td>
            </tr>
            <tr>
              <td align="left"><bean:message key="stock.label.issue.print.addServiceTax"/></td>
              <td align="left">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right">${stockIssueForm.to.paymentTO.totalTaxApplied} %</td>
              <td align="right"><strong>${stockIssueForm.to.paymentTO.totalTaxAmountApplied}</strong></td>
            </tr>
            <tr>
              <td align="left"><bean:message key="stock.label.issue.print.swachhBharatCess"/></td>
              <td align="left">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right">${stockIssueForm.to.paymentTO.eduCessTax} %</td>
              <td align="right"><strong>${stockIssueForm.to.paymentTO.eduCessTaxAmount}</strong></td>
            </tr>
            <tr style="background:#ccc">
              <td align="left"><strong><bean:message key="stock.label.issue.print.total"/></strong></td>
              <td align="left">&nbsp;</td>
              <td align="center">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td align="right"><strong>${stockIssueForm.to.paymentTO.totalToPayAmount}</strong></td>
            </tr>
            <tr>
              <td colspan="6"><span class="txt"><bean:message key="stock.label.issue.print.inWords"/></span> <span class="txt1"><strong>RUPEES ${stockIssueForm.to.paymentTO.totalAmountInWords} Only</strong></span></td>
            </tr>
          </table>
        </div>
        <br />
        <div>
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="31%" align="left" class="txt"><bean:message key="stock.label.issue.print.authorizedSign"/></td>
              <td width="16%" align="left">&nbsp;</td>
              <td width="11%" align="center">&nbsp;</td>
              <td width="10%" align="right">&nbsp;</td>
              <td width="5%" align="right">&nbsp;</td>
              <td width="27%" align="right">&nbsp;</td>
            </tr>
            <tr>
              <td align="left" class="txt3"><bean:message key="stock.label.issue.print.stationeryIsuuedFrom"/></td>
              <td align="left">&nbsp;</td>
              <td align="right" class="txt"><strong><bean:message key="stock.label.issue.date"/></strong></td>
              <td align="left">&nbsp;${stockIssueForm.to.issueDateStr}</td>
              <td align="right">&nbsp;</td>
              <td align="right">&nbsp;</td>
            </tr>
            <tr>
              <td align="left" class="txt3"><bean:message key="stock.label.issue.print.amountReceived"/></td>
              <td align="left">&nbsp;</td>
              <td align="right" class="txt"><strong><bean:message key="stock.label.issue.print.receiptNo"/></strong></td>
              <td align="left">&nbsp;${stockIssueForm.to.stockIssueNumber}</td>
              <td align="right">&nbsp;</td>
              <td align="right">&nbsp;</td>
            </tr>
            <tr>
              <td align="left">&nbsp;</td>
              <td align="left">&nbsp;</td>
              <td align="right" class="txt"><strong><bean:message key="stock.label.issue.print.amoutRs"/></strong></td>
              <td align="left">&nbsp;${stockIssueForm.to.paymentTO.amountReceived}</td>
              <td align="right" class="txt"><strong><bean:message key="stock.label.issue.date"/></strong></td>
              <td align="left">&nbsp;${stockIssueForm.to.issueDateStr}</td>
            </tr>
          </table>
        </div>
        <br />
        <div class="bulletContainer">
          <ul>
            <strong><bean:message key="stock.label.issue.print.terms"/></strong> <br />
            <li>1. Interest will be charged @ 24% p.a on overdue unpaid bills<br />
              2. Any discrepancy whatsoever out of bill will lapse unless raised in writing in 7 days from the receipt of this bill.<br />
              3. Payment are to be made by A/c payee cheque in favour of &quot;First Flight Couriers Limited&quot;<br />
              4. SERVICE TAX NO.: AAACF0841RST181</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- Button --> 
  <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
</div>
<!-- main content ends -->

</div>
<!--wraper ends-->
</html:form>
</body>
</html>
