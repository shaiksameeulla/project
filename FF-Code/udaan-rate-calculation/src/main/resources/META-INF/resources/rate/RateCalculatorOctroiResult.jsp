<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"
	xmlns:mso="urn:schemas-microsoft-com:office:office"
	xmlns:msdt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">
<head>
<%
	int count = 1;
%>
<script type="text/javascript" charset="utf-8">
	function displayMsg() {
		var msg = '${ErrorMsg}';
		if (msg != undefined && msg != null && msg != "" && msg != "null"
				&& msg != " ") {
			alert(msg);
			var url = "./calculateRate.do?submitName=preparePage";
			window.location.replace(url);
		}
	}
</script>
</head>
<body onload="displayMsg();">

	<table border="1" align="center" width="800">
		<c:forEach items="${ResponseTime}" var="item" varStatus="Loop">
			<tr>
				<td align="center"><c:out value="${item.eventName}" /></td>
				<td align="center"><c:out value="${item.timeTaken}" /></td>
				<td align="center">milli Second</td>
				<td align="center"></td>
			</tr>
		</c:forEach>
		<tr bgcolor="grey">
			<td align="center">Sr. No.</td>
			<td align="center">Component Name</td>
			<td align="center">Calculated Value</td>
			<td align="center">Actual Value</td>
		</tr>
		<c:forEach items="${components}" var="item" varStatus="Loop">
			<c:if
				test="${item.rateComponentCode == 'OCSTG' || item.rateComponentCode == 'EDOSG' || item.rateComponentCode == 'HEDOG' || item.rateComponentCode == 'OCSRT' || item.rateComponentCode == 'OCSST' || item.rateComponentCode == 'OCSCG' || item.rateComponentCode == 'OCTRI'}">
				<tr>
					<td align="center"><c:out value="<%=count++%>" /></td>
					<td align="center"><c:out value="${item.rateComponentDesc}" /></td>
					<td align="center"><c:out value="${item.calculatedValue}" /></td>
					<td align="center"><c:out value="${item.actualValue}" /></td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>