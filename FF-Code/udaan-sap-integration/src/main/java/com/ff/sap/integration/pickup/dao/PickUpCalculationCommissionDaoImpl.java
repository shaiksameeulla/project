package com.ff.sap.integration.pickup.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickUpCommissionCalculationDO;
import com.ff.domain.pickup.SAPPickUpComissionCalculationStagingDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.universe.constant.UdaanCommonConstants;

public class PickUpCalculationCommissionDaoImpl extends CGBaseDAO implements PickUpCalculationCommissionDao {
	
	

	Logger logger = Logger.getLogger(PickUpCalculationCommissionDaoImpl.class);
	
	private EmailSenderUtil emailSenderUtil;

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}
	
	@Override
	public ConfigurableParamsDO getMaxDataCount(String paramName)
			throws CGSystemException {
		logger.debug("PickUpCalculationCommissionDaoImpl :: getMaxDataCount() :: Start");
		List<ConfigurableParamsDO> configParamDOs = null;
		ConfigurableParamsDO configParamDO = null;
		try {
			String queryName = "getConfigurableValueForSAPIntegration";
			configParamDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "paramName",
							paramName);
			if (!StringUtil.isEmptyColletion(configParamDOs)) {
				configParamDO = configParamDOs.get(0);
			}
		} catch (Exception e) {
			logger.error("ERROR : PickUpCalculationCommissionDaoImpl.getMaxDataCount", e);
			throw new CGSystemException(e);
		}
		logger.debug("PickUpCalculationCommissionDaoImpl :: getMaxDataCount() :: End");
		return configParamDO;
	}

	@Override
	public Long  getTotalRecordCount(String sapStatus) throws CGSystemException {
		logger.debug("PickUpCalculationCommissionDaoImpl :: getTotalRecordCount :: start======>");
		Long count;
		try {
			String queryName = "getTotalRecordsInPickUpTransactionTable";
			count =  (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, "sapStatus", sapStatus).get(0);
			
		} catch (Exception e) {
			logger.error("ERROR :: PickUpCalculationCommissionDaoImpl :: getTotalRecordCount()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		logger.debug("PickUpCalculationCommissionDaoImpl :: getTotalRecordCount :: End======>");
		return count;
	}
	
	
	@Override
	public List<PickUpCommissionCalculationDO> getPickUpDtlsFromTransactionTable(String sapStatus , Long maxDataCountLimit) throws CGSystemException {
		List<PickUpCommissionCalculationDO> pickUpCalcltnDOList = null; 
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_REQ_DETAILS_FOR_PICKUP_COMMISSION);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,sapStatus);
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			pickUpCalcltnDOList = query.list();
		}catch(Exception e){
			logger.error("Exception In :: PickUpCalculationCommissionDaoImpl :: getPickUpDtlsFromTransactionTable ::",e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		return pickUpCalcltnDOList;
	}
	
	@Override
	public boolean savePickUpCommissionStagingData(List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalculatnStagingDOList) throws CGSystemException {
		logger.debug("PickUpCalculationCommissionDaoImpl :: savePickUpCommissionStagingData :: start======>");
		boolean isSaved = false;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		String emailID = null;
			for(SAPPickUpComissionCalculationStagingDO pickCommisionCalculationDO : sapPickUpCommissionCalculatnStagingDOList){
				try{
					getHibernateTemplate().save(pickCommisionCalculationDO);
					
					//2 A
					//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
					//if flag = true status = C and Time stamp = current time
					//if flag = false status = N and time Stamp = current Time
					
					Date dateTime = Calendar.getInstance().getTime();
					pickCommisionCalculationDO.setSapTimeStamp(dateTime);
					logger.debug("Date Stamp ------>"+pickCommisionCalculationDO.getSapTimeStamp());
					pickCommisionCalculationDO.setDtSapOutbound("C");
					logger.debug("SAP Status ------>"+pickCommisionCalculationDO.getDtSapOutbound());
					updateDateTimeAndStatusFlag(pickCommisionCalculationDO);
				} catch(Exception ex){
					logger.error("STOCKCANCELLATION :: Exception In :: SAPIntegrationDAOImpl :: Error",ex);
					
					//2 A
					//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
					//if flag = true status = C and Time stamp = current time
					//if flag = false status = N and time Stamp = current Time
					
					SAPErrorTO errorTO = new SAPErrorTO();
					if(!StringUtil.isStringEmpty(ex.getCause().getCause().getMessage())){
						errorTO.setErrorMessage(ex.getCause().getCause().getMessage());
					}
					if(!StringUtil.isStringEmpty(pickCommisionCalculationDO.getEmpCode())){
						errorTO.setTransactionNo(pickCommisionCalculationDO.getEmpCode());
					}
					sapErroTOlist.add(errorTO);
					Date dateTime = Calendar.getInstance().getTime();
					pickCommisionCalculationDO.setSapTimeStamp(dateTime);
					logger.debug("Date Stamp ------>"+pickCommisionCalculationDO.getSapTimeStamp());
					pickCommisionCalculationDO.setDtSapOutbound("N");
					logger.debug("SAP Status ------>"+pickCommisionCalculationDO.getDtSapOutbound());
					updateDateTimeAndStatusFlag(pickCommisionCalculationDO);
				}
			}
		sendSapIntgErrorMail(sapErroTOlist,
				SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
				"PickUpCommision Calculation Error Records");
		logger.debug("PickUpCalculationCommissionDaoImpl :: savePickUpCommissionStagingData :: end======>");
		return isSaved;
	}
	
	
	public List<SAPPickUpComissionCalculationStagingDO> findPickUpCommissionDtlsFromStaging(String sapStatus, Long maxDataCountLimit) throws CGSystemException {
		logger.debug("PickUpCalculationCommissionDaoImpl :: findPickUpCommissionDtlsFromStaging :: start");
		List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissioncalcultnDOList = null; 
		Session session = null;
		try {
			int maxDataCount = maxDataCountLimit.intValue();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(SAPIntegrationConstants.QRY_PARAM_PICKUP_DTLS_FROM_STAGING);
			query.setParameter(SAPIntegrationConstants.DT_SAP_OUTBOUND,sapStatus);
			query.setMaxResults(maxDataCount);
			query.setCacheable(false);
			sapPickUpCommissioncalcultnDOList = query.list();
		}catch(Exception e){
			logger.error("Exception In :: SAPIntegrationDAOImpl :: getDtls ::",e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		logger.debug("PickUpCalculationCommissionDaoImpl::findPickUpCommissionDtlsFromStaging:: end");
		return sapPickUpCommissioncalcultnDOList;
	}
	
	public void updateDateTimeAndStatusFlag(
			SAPPickUpComissionCalculationStagingDO pickUpCommissionCalculatnDoList) throws CGSystemException {
		logger.debug("PickUpCalculationCommissionDaoImpl :: updateDateTimeAndStatusFlag :: Start");
		boolean isUpdated = false;
		Session session =  null;
		try {
			session = createSession();
			EmployeeDO empDo=getEmpIdByEmpCode(pickUpCommissionCalculatnDoList.getEmpCode());
			if(!StringUtil.isNull(empDo)){
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_PICKUP_COMMISON_DTLS;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = Calendar.getInstance().getTime();        
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString("dtSapOutbound",pickUpCommissionCalculatnDoList.getDtSapOutbound());
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setLong("employee_id",empDo.getEmployeeId());
				qry.executeUpdate();
				isUpdated = true;
		  }		
		} catch (Exception e) {
			isUpdated = false;
			logger.error("PickUpCalculationCommissionDaoImpl :: updateDateTimeAndStatusFlag:: Exception  ",e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		logger.debug("PickUpCalculationCommissionDaoImpl :: updateDateTimeAndStatusFlag :: END");
	} 
	
	private EmployeeDO getEmpIdByEmpCode(String empCode) {
		logger.debug("PickUpCalculationCommissionDaoImpl :: getEmpIdByEmpCode :: END");
       List<EmployeeDO> result = null;
		
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UdaanCommonConstants.QRY_GET_EMP_BY_CODE, UdaanCommonConstants.PARAM_EMP_CODE, empCode);
		
		logger.debug("PickUpCalculationCommissionDaoImpl :: getEmpIdByEmpCode :: END");
		if (StringUtil.isEmptyList(result))
			return null;
		else
			return result.get(0);
		
	}

	public EmployeeDO getEmployeeCode(Integer employeeId){
		
		List<EmployeeDO> empDOList = null;
		try{
			empDOList = getHibernateTemplate().findByNamedQueryAndNamedParam("getEmpCodeByEmpId", "employeeId",employeeId);
		}catch(Exception e){
			logger.error("PickUpCalculationCommissionDaoImpl :: getEmployeeCode:: Exception  ",e);
		}
		if(!StringUtil.isEmptyList(empDOList)){
			return empDOList.get(0);
		}else{
			return null;
		}
		
		
	}
	
	public void updatePickUpStagingFlag(String status, List<SAPPickUpComissionCalculationStagingDO> sapPickUpCommissionCalculationStagingDOList,String exception) throws CGSystemException{
		logger.debug("PickUpCalculationCommissionDaoImpl :: updatePickUpStagingFlag :: Start");
		boolean isUpdated = false;
		Session session =  null;
		try {
			session = createSession();
			String queryName = SAPIntegrationConstants.QRY_PARAM_UPDATE_PICKUP_COMMISON_DTLS_FOR_STAGING;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(SAPPickUpComissionCalculationStagingDO pickUpCalculationCommisionStagingDO : sapPickUpCommissionCalculationStagingDOList){
				
				Date today = Calendar.getInstance().getTime();        
				String dateStamp = df.format(today);
				Query qry = session.getNamedQuery(queryName);
				qry.setString("dtSapOutbound",status);
				qry.setString(SAPIntegrationConstants.SAP_TIMESTAMP, dateStamp);
				qry.setString("employee_code",pickUpCalculationCommisionStagingDO.getEmpCode());
				qry.setString(SAPIntegrationConstants.EXCEPTION,exception);
				qry.executeUpdate();
				isUpdated = true;
			}
		} catch (Exception e) {
			isUpdated = false;
			logger.error("PickUpCalculationCommissionDaoImpl :: updatePickUpStagingFlag:: Exception  ",e);
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		logger.debug("PickUpCalculationCommissionDaoImpl :: updatePickUpStagingFlag :: END");
		
		
		
	}
	
	public void sendSapIntgErrorMail(List<SAPErrorTO> sapErroTOlist,
			String templateMane, String subName) {
		if(!StringUtil.isEmptyList(sapErroTOlist)){
			MailSenderTO mailTO = new MailSenderTO();
			String[] to = {getEmailIdForErrorRecord()};
			mailTO.setTo(to);
			mailTO.setMailSubject(subName);
			Map<Object, Object> templateVariables = new HashMap<Object, Object>();
			templateVariables.put("sapErroTOlist", sapErroTOlist);
			mailTO.setTemplateName(templateMane);
			mailTO.setTemplateVariables(templateVariables);
			emailSenderUtil.sendEmail(mailTO);
		}
	}
	
	
	private String getEmailIdForErrorRecord()  {
		logger.debug("SAPIntegrationDAOImpl :: getEmailIdForErrorRecord :: Start --------> ::::::");
		String paramName = "SAP_INTEGRATION_EMAIL_ID";
		String email = null;
		try {
			email = (String) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							SAPIntegrationConstants.QRY_GET_LIMIT_FOR_BILLING_BATCH,
							SAPIntegrationConstants.PARAM_NAME, paramName)
					.get(0);

		} catch (Exception e) {
			logger.error("SAPIntegrationDAOImpl :: getEmailIdForErrorRecord()..:",e);
		}
		logger.debug("SAPIntegrationDAOImpl :: getEmailIdForErrorRecord :: End --------> ::::::");
		return email;
	}
}
