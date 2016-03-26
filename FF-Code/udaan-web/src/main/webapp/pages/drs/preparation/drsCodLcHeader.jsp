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

<table cellpadding="2" cellspacing="0" width="100%" border="0">
<tr>
    	<td align="left" valign="top">
    		<center><b><bean:message key="label.print.header" /> </b></center><br/>
    		<center><b><bean:message key="label.print.drs.lcCoddoxHeader" /></b></center>
       	</td>
    </tr>
      
       <tr>
    	<td align="left" valign="top">
        	<table cellpadding="2" cellspacing="2" width="100%" border="0">
            	<tr class="showTopLine">
                	<th width="19%" align="left" ><bean:message key="label.print.drs.branch" /></th>
                	<td width="10%" align="left"  colspan="5">${drsTo.createdOfficeTO.officeName}</td> 
                  	
              	</tr>
               	<tr >
                	<th width="2%" align="left" ><bean:message key="label.print.drs.drsNo" /></th>
                	<td width="10%" align="left" colspan="5" >${drsTo.drsNumber}</td> 
                  	<th width="10%" align="left" ><bean:message key="label.print.dateTime" /></th>
                  	<td width="20%" align="left"  >${drsTo.drsDate}</td> 
           		</tr>
              	<tr >
              	     <th width="2%" align="left" ><bean:message key="label.print.drs.type" /></th>
                	<td width="10%" align="left" colspan="5">${bplbranchOutManifestTO.manifestProcessTo.loadNo}</td>
                	<th width="10%" align="left" ><bean:message key="label.print.loadNo" /></th>
                	<td width="20%" align="left" colspan="5">${drsTo.loadNumber}</td>
                </tr>
                
                <tr >
              	     <th width="2%" align="left" ><bean:message key="label.print.drs.timeOut" /></th>
                	<td width="10%" align="left" colspan="5">${drsTo.fsOutTime}</td>
                	<th width="10%" align="left" ><bean:message key="label.print.drs.timeIn"/></th>
                	<td width="20%" align="left" colspan="5">${drsTo.fsInTime}</td>
                </tr>
            </table>
			<!-- -------------------------------------------------------------------------------- -->
       	</td>
	</tr>
	</table>
</html>