package com.ff.admin.leads.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.domain.leads.CompetitorDO;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.leads.PlanFeedbackDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.umc.constants.UmcConstants;



/**
 * @author sdalli
 *
 */
public class LeadCommonDAOImpl extends CGBaseDAO implements LeadCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadCommonDAOImpl.class);
	

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getSalesPersonsTitlesList(
			final String DepartmentType) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<EmployeeDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE,
							new String[] { LeadCommonConstants.DEPARTMENT_NAME },
							new Object[] { DepartmentType });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getSalesPersonsTitlesList() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompetitorDO> getCompetitorList() throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getCompetitorList() :: Start --------> ::::::");
		List<CompetitorDO> competitorDOList = null;
		Session session = null;
		session = createSession();
		try{
			competitorDOList = (List<CompetitorDO>) session.createCriteria(
					CompetitorDO.class).list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getCompetitorList() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getCompetitorList() :: End --------> ::::::");
		return competitorDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId, final String designation)
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getRegionalSalesPersonsList() :: Start --------> ::::::");
		/*List<EmployeeUserDO> employeeUserDOList = null;*/
		List<EmployeeUserJoinBean> employeeUserDOList = null;
		try{
			employeeUserDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_EMP_DESIGN,
							new String[] { LeadCommonConstants.DEPARTMENT_NAME,LeadCommonConstants.DESIGNATION,LeadCommonConstants.OFFICEID  },
							new Object[] { LeadCommonConstants.DEPARTMENT_NAME_TYPE,designation,officeId });
			
			}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getRegionalSalesPersonsList() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getRegionalSalesPersonsList() :: End --------> ::::::");
		return employeeUserDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getRegionalBranchesList(final Integer regionId,
			final Integer officeTypeId) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getRegionalBranchesList() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		try{
			officeDOs=getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					LeadCommonConstants.QRY_GET_REGIONAL_BRANCHES_BY_REGIONID_AND_OFFICE_TYPE_ID,
					new String[] { LeadCommonConstants.OFFICE_TYPE_ID,LeadCommonConstants.REGION_ID },
					new Object[] { officeTypeId,regionId });
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getRegionalSalesPersonsList() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getRegionalSalesPersonsList() :: End --------> ::::::");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LeadDO getLeadDetails(final String leadNumber) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getLeadDetails() :: Start --------> ::::::");
		LeadDO leadDO =null;
		Session session = null;
		session = createSession();
		try{
		List<LeadDO>	leadDOs = (List<LeadDO>) session.createCriteria(
					LeadDO.class).add(Restrictions.eq("leadNumber", leadNumber)).list();
		if(!StringUtil.isEmptyColletion(leadDOs)){
			leadDO=leadDOs.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getLeadDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getLeadDetails() :: End --------> ::::::");
		
		return leadDO;
	}

	@Override
	public void savePlan(final PlanFeedbackDO planFeedbackDO) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: savePlan() :: Start --------> ::::::");
		try{
			getHibernateTemplate().saveOrUpdate(planFeedbackDO);
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::savePlan() :: " + e);
			throw new CGSystemException(e);
		}		
		LOGGER.trace("LeadCommonDAOImpl :: savePlan() :: End --------> ::::::");
		
		
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlanFeedbackDO getPlanFeedbackDetails(final  String leadNumber)
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getLeadDetails() :: Start --------> ::::::");
		Session session = null;
		session = createSession();
		PlanFeedbackDO feedbackDO = null;
		try{
			List<PlanFeedbackDO> feedbackDOs = (List<PlanFeedbackDO>) session.createCriteria(
					PlanFeedbackDO.class).add(Restrictions.eq("leadDO.leadNumber", leadNumber)).list();
			if(!StringUtil.isEmptyColletion(feedbackDOs)){
				feedbackDO=feedbackDOs.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getPlanFeedbackDetails() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getPlanFeedbackDetails() :: End --------> ::::::");
		return feedbackDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeadDO> getLeadUpdateDate() throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getLeadUpdateDate() :: Start --------> ::::::");
		Session session = null;
		session = createSession();
		List<LeadDO> leadDOs = null;
		try{
			 leadDOs =(List<LeadDO>)session.createCriteria(LeadDO.class).add(Restrictions.eq(LeadCommonConstants.LEAD_STATUS_CODE,LeadCommonConstants.LEAD_STATUS )).list();
		
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getLeadUpdateDate() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getLeadUpdateDate() :: End --------> ::::::");
		return leadDOs;
	}

	@Override
	public Boolean savePlan(List<PlanFeedbackDO> planFeedbackDOList)
			throws CGSystemException {
		
		LOGGER.trace("LeadCommonDAOImpl :: savePlan() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try{
			for (PlanFeedbackDO planFeedbackDO : planFeedbackDOList) {
			getHibernateTemplate().saveOrUpdate(planFeedbackDO);
			isSaved = Boolean.TRUE;
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::savePlan() :: " + e);
			throw new CGSystemException(e);
		}		
		LOGGER.trace("LeadCommonDAOImpl :: savePlan() :: End --------> ::::::");
		return isSaved;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanFeedbackDO> getLeadsPlanningDtlsOrdeByTimeDesc(
			String leadNumber) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getLeadsPlanningDtlsOrdeByTimeDesc() :: Start --------> ::::::");
		List<PlanFeedbackDO> planFeedbackDOs = null;
		try {
			planFeedbackDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_LEADS_PLANNING_FEEDBACK_DTLS_BY_LEADNUMBER_IN_TIME_DESC_ORDER,
							LeadCommonConstants.LEADNUMBER,
							leadNumber);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getLeadsPlanningDtlsOrdeByTimeDesc() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getLeadsPlanningDtlsOrdeByTimeDesc() :: End --------> ::::::");
		return planFeedbackDOs;
	}

	@Override
	public void updateLeadStatus(Integer leadId, String updatingStatus)
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: updateLeadStatus() :: Start --------> ::::::");
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session
					.getNamedQuery(LeadCommonConstants.QRY_UPDATE_LEADS_STATUS_BY_LEAD_ID);
			query.setString(LeadCommonConstants.LEAD_STATUS_CODE,
					updatingStatus);
			query.setInteger(LeadCommonConstants.LEAD_ID,
					leadId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::updateLeadStatus() :: "+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: updateLeadStatus() :: End --------> ::::::");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getBranchesUnderReportingRHO(Integer officeId)
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderReportingRHO() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		try{
			officeDOs=getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					LeadCommonConstants.QRY_GET_ALL_BRANCHES_UNDER_REPORTING_RHO,
					new String[] { LeadCommonConstants.OFFICE_ID},
					new Object[] { officeId });
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getBranchesUnderReportingRHO() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderReportingRHO() :: End --------> ::::::");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		try{
			officeDOs=getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					LeadCommonConstants.QRY_GET_ALL_BRANCHES_UNDER_REPORTING_HUB,
					new String[] { LeadCommonConstants.OFFICE_ID},
					new Object[] { officeId });
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getBranchesUnderReportingHub() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getBranchesUnderCorporateOffice()
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderCorporateOffice() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		Session session = null;
		session = createSession();
		try{
			
			officeDOs =(List<OfficeDO>)session.createCriteria(OfficeDO.class).list();
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getBranchesUnderCorporateOffice() :: "
					+ e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getBranchesUnderCorporateOffice() :: End --------> ::::::");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRightsDO> getUserRoleById(Integer userId)
			throws CGSystemException {
		List<UserRightsDO> userRoleList = null;
		try {
			userRoleList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
						LeadCommonConstants.QRY_GET_USER_RIGHT_BY_USER_ID,
						new String[] {UmcConstants.USER_ID,UmcConstants.STATUS},
						new Object[] { userId,"A"});
			
			
		} catch (Exception e) {
			LOGGER.error("Error occured in LeadCommonDAOImpl :: getUserRoleById()..:"
					+ e);
			throw new CGSystemException(e);
		}
		return userRoleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesMappedToUser(Integer userId) throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getOfficesMappedToUser() :: Start --------> ::::::");
		List<OfficeDO> officeDOs= null;
		try{
			officeDOs=getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					LeadCommonConstants.QRY_GET_OFFICES_MAPPED_TO_USER,
					new String[] {UmcConstants.USER_ID},
					new Object[] { userId });
	
		}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getOfficesMappedToUser() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getOfficesMappedToUser() :: End --------> ::::::");
		return officeDOs;
	}



}
