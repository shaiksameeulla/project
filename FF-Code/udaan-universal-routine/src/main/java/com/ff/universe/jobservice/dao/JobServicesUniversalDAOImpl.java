package com.ff.universe.jobservice.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ff.domain.jobservice.JobServicesDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

public class JobServicesUniversalDAOImpl extends CGBaseDAO implements JobServicesUniversalDAO{
	private final static Logger LOGGER = LoggerFactory.getLogger(JobServicesUniversalDAOImpl.class);
	@Override
	public boolean saveOrUpdateJobService(JobServicesDO jobServicesDO)
			throws CGSystemException {
		LOGGER.trace("JobServicesDAOImpl :: saveJobService() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean jobCreated = Boolean.FALSE;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			tx = session.beginTransaction();
			session.merge(jobServicesDO);
			LOGGER.trace("JobServicesDAOImpl :: saveOrUpdateJobService() ");
			tx.commit();
			jobCreated = Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.trace("Error occured in JobServicesDAOImpl :: saveOrUpdateJobService()..:"
					, e);			
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("JobServicesDAOImpl :: saveOrUpdateJobService() :: END --------> ::::::");
		return jobCreated;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JobServicesDO> searchJobService(String processCode,
			String jobNumber, Date fromDate, Date toDate)
			throws CGSystemException {
		LOGGER.trace("JobServicesDAOImpl :: searchJobService() :: Start --------> ::::::");
		List<JobServicesDO> jobServicesDOList = null;
		Session session = null;
		Criteria criteria = null;
		try{
			session = createSession();
			criteria = session.createCriteria(JobServicesDO.class,"jobService");
			if(!StringUtil.isStringEmpty(jobNumber)){
				criteria.add(Restrictions.eq("jobService.jobNumber", jobNumber));
			}
			else{
			if(!StringUtil.isStringEmpty(processCode)){
				criteria.add(Restrictions.eq("jobService.processCode", processCode));	
			}
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//Date startDate = format.parse("2014-01-13 00:00:00");
			//Date endDate = format.parse("2014-01-15 00:00:00");
			//criteria.add(Restrictions.between("jobService.fileSubmissionDate", startDate, endDate));
			//criteria.add(Restrictions.sqlRestriction("JOB_SUBMISSION_DATE", "2014-01-14", org.hibernate.type.StandardBasicTypes.DATE));
			//criteria.add(Restrictions.ge("jobService.fileSubmissionDate", startDate));
			if(!StringUtil.isNull(fromDate)){
				criteria.add(Restrictions.ge("jobService.fileSubmissionDate", fromDate));
			}
			
			if(!StringUtil.isNull(toDate)){
				criteria.add(Restrictions.lt("jobService.fileSubmissionDate", toDate));
			}
			}
			/*if(!StringUtil.isNull(toDate)){
				criteria.add(Expression.between("jobService.fileSubmissionDate", fromDate, toDate));
			}*/
			
			jobServicesDOList = criteria.list();
			
		}catch(Exception e){
			throw new CGSystemException(e);
		}finally{
			session.close();
		}
		LOGGER.trace("JobServicesDAOImpl :: searchJobService() :: END --------> ::::::");
		return jobServicesDOList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getJobProcessList(String stdTypeName)
			throws CGSystemException {
		LOGGER.trace("JobServicesDAOImpl :: getJobProcessList() :: START --------> ::::::");
		List<StockStandardTypeDO> stockStandardTypeDO = null;
		try {
			
			String queryName = "getStockStdTypeByTypeName";
			
			stockStandardTypeDO = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "typeName", stdTypeName);

			
			
		} catch (Exception e) {
			LOGGER.error("ERROR : JobServicesDAOImpl.getJobProcessList", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace(" JobServicesDAOImpl:: getJobProcessList() :: END --------> ::::::");
		return stockStandardTypeDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public JobServicesDO getJobServiceDetails(String jobNumber)
			throws CGSystemException {
		LOGGER.trace("JobServicesDAOImpl :: getJobServiceDetails() :: START --------> ::::::");
		List<JobServicesDO> JobServicesDOList = null;
		JobServicesDO jobServicesDO = null;
		try {
			
			String queryName = "getJobserviceByJobNumber";
			
			JobServicesDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "jobNumber", jobNumber);

			if(!CGCollectionUtils.isEmpty(JobServicesDOList)){
				jobServicesDO = JobServicesDOList.get(0);
			}
			
			
		} catch (Exception e) {
			LOGGER.error("ERROR : JobServicesDAOImpl.getJobServiceDetails", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("JobServicesDAOImpl :: getJobServiceDetails() :: END --------> ::::::");
		return jobServicesDO;
	}

}
