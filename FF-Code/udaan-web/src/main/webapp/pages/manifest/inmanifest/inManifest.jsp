<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" charset="utf-8">
	function getRemarks() {
		var text = "<option value=''>---SELECT---</option>";
		<c:forEach var="remark" items="${inManifestRemarkList}" varStatus="status">
		text = text
				+ "<option value=${remark.description}>${remark.description}</option>";
		</c:forEach>
		return text;
	}
	var REMARKS_OPTION = getRemarks();
</script>