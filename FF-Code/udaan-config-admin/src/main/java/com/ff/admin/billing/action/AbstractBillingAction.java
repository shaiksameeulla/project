package com.ff.admin.billing.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.organization.service.OrganizationCommonService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractBillingAction.
 *
 * @author narmdr
 */
public abstract class AbstractBillingAction extends CGBaseAction {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractBillingAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	private OrganizationCommonService organizationCommonService = null;
	 
	public OrganizationCommonService getOrganizationCommonService(){
		if(StringUtil.isNull(organizationCommonService))
			organizationCommonService = (OrganizationCommonService) getBean("organizationCommonService");
		return organizationCommonService;
	}
	public void getBranchesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractBillingAction :: getBranchesByCity :: START----->");
		String city = request.getParameter("cityId");
		Integer cityId = Integer.parseInt(city);
		String branchJson = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<OfficeTO> officeTOList = getOrganizationCommonService().getAllOfficesByCity(cityId);

			if (!CGCollectionUtils.isEmpty(officeTOList)){
				branchJson = JSONSerializer.toJSON(officeTOList).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractBillingAction :: getBranchesByCity :: --->"+ e.getLocalizedMessage());
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("AbstractBillingAction :: getBranchesByCity :: --->"+ e.getMessage());
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("AbstractBillingAction :: getBranchesByCity :: --->"+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(branchJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractBillingAction :: getBranchesByCity::END----->");
	}
	
	public String getLoggedInOfficeType(HttpServletRequest request){
		LOGGER.debug("AbstractBillingAction::getLoggedInOfficeType()::START");
		String loggedInOfficeType = null;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (!StringUtil.isNull(loggedInOfficeTO) && !StringUtil.isNull(loggedInOfficeTO.getOfficeTypeTO())) {
			loggedInOfficeType = loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode();
		}
		LOGGER.debug("AbstractBillingAction::getLoggedInOfficeType()::END");
		return loggedInOfficeType;
	}
}
