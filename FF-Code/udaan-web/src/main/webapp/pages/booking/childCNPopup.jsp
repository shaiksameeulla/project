<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/childConsignment.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<script>
		var cndetails ;
		var details="" ;
		var rowCount = '<%= request.getParameter("rowCount") %>';
		var process = '<%= request.getParameter("processCode") %>';
		if(rowCount==""){
			rowCount = 0;
		}
		rowCount = parseInt(rowCount);
		</script>
</head>
<body onload="setDefaultVal('<%= request.getParameter("rowCount") %>')"
	onkeypress="ESCclose(event);">

	<!--wraper-->
	<div id="wraper">
		<%
		String rows = request.getParameter("pieces");
         String parentCNs = request.getParameter("parentCNs");
       
	%>

		<div class="clear"></div>
		<!-- main content -->
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<h1>Child CN</h1>
					<div class="mandatoryMsgf">
						<span class="mandatoryf">*</span> Fields are Mandatory
					</div>
				</div>

				<div id="demo">


					<table width='100%' border='0' class='display' cellpadding='0'
						cellspacing='0' id="childCNTable">
						<thead>
							<tr>
								<th width="7%"><sup class="mandatoryf"
									style="color: white;">*</sup>CN Number</th>
								<th width="8%"><sup class="mandatoryf"
									style="color: white;">*</sup>Actual Weight</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="i" begin="0"
								end="<%= Integer.parseInt(request.getParameter(\"pieces\"))-1 %>"
								step="1">
								<tr>
									<td align="center"><input type='text' maxlength="12"
										name='consgNo${i}' id='consgNo${i}' value=""
										class="txtbox width110" size='10'
										onchange="convertDOMObjValueToUpperCase(this);validateConsignment(this,'<%= request.getParameter("processCode") %>','<%= request.getParameter("rowCount") %>');"
										onkeypress="return enterKeyValidationOnConsignment(event, document.getElementById('weight${i}'), this);" />
										<input type="hidden" name="rowcount" id="rowcount" value="" />
									</td>
									<td align="center"><input type='hidden'
										name='actWeight${i}' id='actWeight${i}' value="" /> <span
										class="lable">Kgs</span> <input type='text' name='weight${i}'
										id='weight${i}' maxlength="4" value="" class="txtbox width30"
										size='11'
										onkeypress="return onlyNumberAndEnterKeyNav(event,this,'weightGm${i}');" />
										<span class="lable">.</span> <input type='text'
										name='weightgm${i}' id='weightGm${i}' maxlength="3" value=""
										class="txtbox width30" size='11'
										onkeypress="return onlyNumberAndEnterKeyNav1(event,'<%=rows%>','${i}');"
										onblur="weightFormatForGm(this);" /> <span class="lable">Gms</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>



				</div>
				<!-- Grid /-->
			</div>
		</div>

		<!-- Button -->
		<div class="button_containerform">
			<input name="Submit" id="submit" type="button" class="btnintform"
				value='Submit' title="Submit" onclick='submitVal("<%=rows%>")' /> <input
				name="Cancel" type="button" class="btnintform" value='Cancel'
				title="Cancel" onclick='cancelVal("<%=rows%>")' />


		</div>
		<!-- Button ends -->
		<!-- main content ends -->

	</div>
	<!-- wrapper ends -->
	</form>
</body>
</html>
