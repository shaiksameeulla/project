<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<tr>
	<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;Origin
		RHO:</td>
	<td width="18%" class="lable1"><html:select  disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
			 property="to.origionRegionID"
			styleId="origionRegionList" styleClass="selectBox width130" onchange="getVendorList('origionRegionList','vendorList');">  <!-- onchange="getVendorList('origionRegionList','vendorList');" -->
			<html:option value="-1">
				<bean:message key="label.common.select" />
			</html:option>
			<logic:present name="regionList" scope="request">
				<logic:iterate id="origionRegion" name="regionList">
					<c:choose>
						<c:when test="${origionRegion.regionId==to.origionRegionID}">
							<option value="${origionRegion.regionId}" selected="selected">
								<c:out value="${origionRegion.regionName}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${origionRegion.regionId}">
								<c:out value="${origionRegion.regionName}" />
							</option>
						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</logic:present>
		</html:select><html:hidden property="to.origionRegionID" /></td>
	<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;Origin
		Station:</td>
	<td width="25%" class="lable1"><html:select disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
			 property="to.origionCityID" onclick="eraseEffectivefromDate();"
			styleId="origionStationList" styleClass="selectBox width300">
			<logic:present name="origionCityList" scope="request">
				<logic:iterate id="city" name="origionCityList">
					<c:choose>
						<c:when test="${city.cityId==to.origionCityID}">
							<option value="${city.cityId}" selected="selected">
								<c:out value="${city.cityName}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${city.cityId}">
								<c:out value="${city.cityName}" />
							</option>
						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</logic:present>
		</html:select><html:hidden property="to.origionCityID" /></td>
	<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;Destination
		RHO:</td>
	<td width="15%" class="lable1"><html:select disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
			 property="to.destinationRegionID"
			styleId="destinationRegionList" styleClass="selectBox width130"
			onchange="getStationsList('destinationRegionList','destinationStationList');">
			<html:option value="-1">
				<bean:message key="label.common.select" />
			</html:option>
			<logic:present name="regionList" scope="request">
				<logic:iterate id="destinationRegion" name="regionList">
					<c:choose>
						<c:when
							test="${destinationRegion.regionId==to.destinationRegionID}">
							<option value="${destinationRegion.regionId}" selected="selected">
								<c:out value="${destinationRegion.regionName}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${destinationRegion.regionId}">
								<c:out value="${destinationRegion.regionName}" />
							</option>
						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</logic:present>
		</html:select><html:hidden property="to.destinationRegionID" /></td>
</tr>
<tr>
	<td class="lable"><sup class="star">*</sup>&nbsp;Destination
		Station:</td>
	<td class="lable1"><html:select property="to.destinationCityID"  onchange="checkStation();"
			 styleId="destinationStationList" disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
			styleClass="selectBox width140">
			<logic:present name="destinationCityList" scope="request">
				<logic:iterate id="city" name="destinationCityList">
					<c:choose>
						<c:when test="${city.cityId==to.destinationCityID}">
							<option value="${city.cityId}" selected="selected">
								<c:out value="${city.cityName}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${city.cityId}">
								<c:out value="${city.cityName}" />
							</option>
						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</logic:present>
		</html:select><html:hidden property="to.destinationCityID" /></td>
		
  	<td class="lable"><sup class="star">*</sup>&nbsp;Effective From:</td>
	<td class="lable1">
		<table>
			<tr>
				<td>
				<html:text property="to.effectiveFrom" onblur="checkEffectiveFromDate('effectiveFrom', '${to.renewFlag}'), displayData('vendorList', '${to.renewFlag}', '${to.coloaderType}')" 
						 style="height: 20px" readonly="true" 
						styleId="effectiveFrom" value=" "
						styleClass="txtbox width100" /></td>
				<td><c:if test="${!to.storeStatus == 'P' || !to.renewFlag eq 'R' || to.isRenewalAllow}">
						<a href="#" id="calIcon"
							onclick="javascript:show_calendar('effectiveFrom', this.value)"
							title="Select Date"><img src="images/icon_calendar.gif"
							alt="Effective from date" width="16" height="16" border="0"
							class="imgsearch" /></a>
					</c:if>
					<input name="R"	type="hidden" id="renewFlag"
												value="${to.renewFlag}" />
				</td>
			</tr>
		</table>
	</td>		 	
		
	<td class="lable"><sup class="star">*</sup>&nbsp;Vendor:</td>
	<td class="lable1"><html:select property="to.vendorId" onclick="checkEffectivefrom();"
			 styleId="vendorList" disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
			styleClass="selectBox width300">
			<logic:present name="vendorList" scope="request">
				<logic:iterate id="vendor" name="vendorList">				
					<c:choose>
						<c:when test="${vendor.vendorId==to.vendorId}">
							<option value="${vendor.vendorId}" selected="selected">
								<c:out value="${vendor.vendorCode } - ${vendor.businessName}" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="${vendor.vendorId}">
								<c:out value="${vendor.vendorCode } - ${vendor.businessName}" />
							</option>
						</c:otherwise>
					</c:choose>
				</logic:iterate>
			</logic:present>
		</html:select><html:hidden property="to.vendorId" />
		</td>
	
</tr>