<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Grid START FROM HERE -->
<thead>
							<tr>
								<th width="2%" align="center"><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('chkbx',this.checked);" tabindex="-1"/></th>
								<th width="3%" align="center"><bean:message key="label.common.serialNo" /></th>
								<th width="7%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.consgNo" /></th>
								<th width="7%"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.prep.Origin" /> </th>
								<th width="10%"><sup class="star">*</sup>&nbsp;
								
								<bean:message key="label.drs.update.reason" />
								</th>
								<th width="7%"><bean:message key="label.drs.update.missedcard" /></th>
								<th width="10%"><bean:message key="label.drs.update.remarks" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="drsDtls" items="${pendingDrsForm.to.drsDetailsTo}" varStatus="loop">
                           <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
                               <td><input type="checkbox" name="to.checkbox" value='${loop.count-1}' id="checkbox${loop.count}"  tabindex="-1"/></td>
                               <td><label><c:out value="${drsDtls.rowNumber}"/> </label></td>
                              <td><html:text styleId="rowConsignmentNumber${loop.count}" property="to.rowConsignmentNumber" styleClass="txtbox width145" value='${drsDtls.consignmentNumber}' readonly="true" tabindex="-1" /></td>
                               <td align="center" >
			                    <html:text styleId="rowOriginCityName${loop.count}" property="to.rowOriginCityName" value="${drsDtls.originCityName}" styleClass="txtbox width140" readonly="true" />
			                     <html:hidden styleId="rowOriginCityId${loop.count}" property="to.rowOriginCityId" value="${drsDtls.originCityId}"/>
			                <html:hidden styleId="rowOriginCityCode${loop.count}" property="to.rowOriginCityCode" value="${drsDtls.originCityCode}"/>
			                <html:hidden styleId="rowConsignmentId${loop.count}" property="to.rowConsignmentId" value="${drsDtls.consgnmentId}"/>
			                </td>
                               <td>
                               
                               	<html:select property="to.rowPendingReasonId" styleId="rowPendingReasonId${loop.count}"
									styleClass="selectBox" disabled="true" style="width: 100px" tabindex="-1">
									<option value="">SELECT</option>
									<logic:present name="drsDtls" property="nonDlvReason">
										<logic:iterate id="reason"  name="drsDtls" property="nonDlvReason" >
											<c:choose>
												<c:when	test="${reason.key==drsDtls.reasonId}">
													<option value="${reason.key}" selected="selected">
														<c:out value='${reason.value}' />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${reason.key}">
														<c:out value='${reason.value}' />
													</option>
												</c:otherwise>
											</c:choose>
										</logic:iterate>
									</logic:present>
								</html:select>
                               
                               </td>
                                
                                <td><html:text styleId="rowMissedCardNumber${loop.count}" property="to.rowMissedCardNumber" styleClass="txtbox width100" value='${drsDtls.missedCardNumber}' readonly="true" tabindex="-1" maxlength="25"/><!-- styleClass="inputFieldGrey" --></td>
                                <td><html:text styleId="rowRemarks${loop.count}" property="to.rowRemarks" styleClass="txtbox width100" value='${drsDtls.remarks}' readonly="true" tabindex="-1" maxlength="30"/>
                                <html:hidden styleId="rowDeliveryDetailId${loop.count}" property="to.rowDeliveryDetailId" value='${drsDtls.deliveryDetailId}'/>
                                </td>
                               </tr>
                               </c:forEach>
						</tbody>
						<!-- Grid END FROM HERE -->