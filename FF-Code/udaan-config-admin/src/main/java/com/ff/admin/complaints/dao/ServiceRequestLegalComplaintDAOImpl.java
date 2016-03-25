package com.ff.admin.complaints.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.complaints.LegalComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestLegalComplaintDO;
import com.ff.domain.complaints.ServiceRequestPapersDO;




public class ServiceRequestLegalComplaintDAOImpl extends CGBaseDAO implements ServiceRequestLegalComplaintDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestLegalComplaintDAOImpl.class);
	
	@Override
	public boolean saveOrUpdateLegalComplaint(
			ServiceRequestLegalComplaintDO servicereqstLegalcomplaintDO,ServiceRequestPapersDO serviceRequestPaperDO) throws CGSystemException {
		//private final static Logger LOGGER = LoggerFactory.getLogger(ServiceRequestLegalComplaintDAOImpl.class);
		LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		Criteria criteria = null;
		ServiceRequestPapersDO serviceRequstPaperDO = null;
		ServiceRequestComplaintDO serviceRequestComplainDO = null;
		try {
			
			//List<ServiceRequestPapersDO> serviceRequstPaperList = getHibernateTemplate().findByNamedParam("getPaperworkByServiceRequest", "serviceRequestId", serviceRequestPaperDO.getServiceRequestId());
			
			session = createSession();
		
			tx = session.beginTransaction();
			
			
			criteria = session.createCriteria(ServiceRequestPapersDO.class, "serviceRequestPaperDO");
			criteria.add(Restrictions.eq("serviceRequestPaperDO.serviceRequestId", serviceRequestPaperDO.getServiceRequestId()));
			criteria.add(Restrictions.eq("serviceRequestPaperDO.fileName", serviceRequestPaperDO.getFileName()));
			serviceRequstPaperDO = (ServiceRequestPapersDO) criteria.uniqueResult();
			
			
			
			if(!StringUtil.isNull(serviceRequstPaperDO)){
				//serviceRequstPaperList.get(0).setMailCopyFileName(serviceRequestPaperDO.getMailCopyFileName());
				// serviceRequstPaperDO.setAdvaocateNoticefileName(serviceRequestPaperDO.getAdvaocateNoticefileName());// FIXME
				serviceRequstPaperDO.setServiceRequestPaperId(serviceRequstPaperDO.getServiceRequestPaperId());
				serviceRequstPaperDO.setFileName(serviceRequestPaperDO.getFileName());
				serviceRequstPaperDO.setFile(serviceRequestPaperDO.getFile());
				serviceRequstPaperDO.setCreatedBy(serviceRequestPaperDO.getCreatedBy());
				serviceRequstPaperDO.setUpdatedBy(serviceRequestPaperDO.getUpdatedBy());
				serviceRequstPaperDO.setCreatedDate(serviceRequestPaperDO.getCreatedDate());
				serviceRequstPaperDO.setUpdatedDate(serviceRequestPaperDO.getUpdatedDate());
				
				
				session.merge(serviceRequstPaperDO);
			}else{
				//serviceRequstPaperDO=new  ServiceRequestPapersDO();
				//serviceRequstPaperDO.setServiceRequestId(serviceRequestPaperDO.getServiceRequestId());
				// serviceRequstPaperDO.setAdvaocateNoticefileName(serviceRequestPaperDO.getAdvaocateNoticefileName()); // FIXME
				session.merge(serviceRequestPaperDO);
			}
			
			
			
			//chk if foreign key service_request_id exists in ff_f_service_request_complaint
			criteria = session.createCriteria(ServiceRequestComplaintDO.class, "serviceRequestComplainDO");
			criteria.createAlias("serviceRequestComplainDO.serviceRequestDO", "serviceRequestDO");
			criteria.add(Restrictions.eq("serviceRequestDO.serviceRequestId", serviceRequestPaperDO.getServiceRequestId()));
			serviceRequestComplainDO = (ServiceRequestComplaintDO) criteria.uniqueResult();
			
			
			//chk if foreign key ADVOCATE_NOTICE_PAPER exists in ff_f_service_request_papers
			criteria = session.createCriteria(ServiceRequestPapersDO.class, "serviceRequestPaperDO");
			criteria.add(Restrictions.eq("serviceRequestPaperDO.fileName",serviceRequestPaperDO.getFileName() ));
			criteria.add(Restrictions.eq("serviceRequestPaperDO.serviceRequestId",serviceRequestPaperDO.getServiceRequestId()));
			ServiceRequestPapersDO paperDO = (ServiceRequestPapersDO) criteria.uniqueResult();
			
			
			if(!StringUtil.isNull(serviceRequestComplainDO)){
				servicereqstLegalcomplaintDO.setServiceRequestComplaintId(serviceRequestComplainDO.getServiceRequestComplaintId());
				
				criteria = session.createCriteria(ServiceRequestLegalComplaintDO.class, "serviceRequestLegalComplainDO");
				
				criteria.add(Restrictions.eq("serviceRequestLegalComplainDO.serviceRequestComplaintId", serviceRequestComplainDO.getServiceRequestComplaintId()));
				ServiceRequestLegalComplaintDO serviceRequestLegalComplainDO = (ServiceRequestLegalComplaintDO) criteria.uniqueResult();
				
				servicereqstLegalcomplaintDO.setAdvocateNoticePaperDO(paperDO);
				
				if(!StringUtil.isNull(serviceRequestLegalComplainDO)){
					servicereqstLegalcomplaintDO.setServiceRequestComplaintLegal(serviceRequestLegalComplainDO.getServiceRequestComplaintLegal());
					
				}
				
				
				session.merge(servicereqstLegalcomplaintDO);
			}else if(StringUtil.isNull(serviceRequestComplainDO)){
				throw new CGSystemException("Critical Complaint needs to be created before creating Legal complaints", new Exception("Critical Complaint needs to be created before creating Legal complaints"));
			}
			
			
			
			
		
			isSaved = Boolean.TRUE;
			//LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint() ");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("Error occured in ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint()..:"
					, e);			
			throw new CGSystemException(e);
		} finally {
			
			session.close();
		}
		LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint() :: END --------> ::::::");
		
		return isSaved;
	}

	@Override
	public ServiceRequestLegalComplaintDO searchLegalComplaint(
			LegalComplaintTO to) throws CGSystemException {
		LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: searchLegalComplaint() :: START");
		ServiceRequestLegalComplaintDO serviceRequestLegalComplaintDO = null;
		Session session = null;
		Criteria criteria = null;
		ServiceRequestComplaintDO serviceRequestComplainDO = null;
		List<ServiceRequestLegalComplaintDO> serviceRequestLegalComplaintDOs = null;
		try {
			session = createSession();
			
			criteria = session.createCriteria(ServiceRequestComplaintDO.class, "serviceRequestComplainDO");
			criteria.createAlias("serviceRequestComplainDO.serviceRequestDO", "serviceRequestDO");
			criteria.add(Restrictions.eq("serviceRequestDO.serviceRequestId", to.getServiceRequestComplaintId()));
			serviceRequestComplainDO = (ServiceRequestComplaintDO) criteria.uniqueResult();
			
			if(!StringUtil.isNull(serviceRequestComplainDO)){
				
				criteria = session.createCriteria(ServiceRequestLegalComplaintDO.class,
						"legalcomplaintDO");
				criteria.add(Restrictions.eq("legalcomplaintDO.serviceRequestComplaintId",
						serviceRequestComplainDO.getServiceRequestComplaintId()));
				 serviceRequestLegalComplaintDOs = criteria.list();
				 
				 if (!CGCollectionUtils.isEmpty(serviceRequestLegalComplaintDOs)) {
						serviceRequestLegalComplaintDO = serviceRequestLegalComplaintDOs.get(0);
				}
				 
		}
			
	} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ServiceRequestLegalComplaintDAOImpl :: searchLegalComplaint() ::",
					e);
			throw new CGSystemException(e);
		}finally {
			
			session.close();
		}
		LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: searchLegalComplaint() :: END");
		return serviceRequestLegalComplaintDO;
	}
	
	
	public ServiceRequestPapersDO getPaperWorkDO(LegalComplaintTO legalComplntTO) throws CGSystemException{
	
	Session session = null;
	Criteria criteria = null;
	ServiceRequestPapersDO serviceRequstPaperDO = null;
	try {
		
		//List<ServiceRequestPapersDO> serviceRequstPaperList = getHibernateTemplate().findByNamedParam("getPaperworkByServiceRequest", "serviceRequestId", serviceRequestPaperDO.getServiceRequestId());
		
		session = createSession();
		
		//tx = session.beginTransaction();
	
		criteria = session.createCriteria(ServiceRequestPapersDO.class, "serviceRequestPaperDO");
		criteria.add(Restrictions.eq("serviceRequestPaperDO.serviceRequestId", legalComplntTO.getServiceRequestComplaintId()));
		serviceRequstPaperDO = (ServiceRequestPapersDO) criteria.uniqueResult();
		
		
		
		if(!StringUtil.isNull(serviceRequstPaperDO)){
			//serviceRequstPaperList.get(0).setMailCopyFileName(serviceRequestPaperDO.getMailCopyFileName());
			return serviceRequstPaperDO;
		}
	}
		catch (Exception e) {
			
			LOGGER.error("Error occured in ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint()..:"
					, e);			
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("ServiceRequestLegalComplaintDAOImpl :: saveOrUpdateLegalComplaint() :: END --------> ::::::");
		
		return serviceRequstPaperDO;
	}
	
}
