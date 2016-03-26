/**
 * 
 */
package com.ff.rate.configuration.ratebenchmark.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkProductDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.ratebenchmark.constants.RateBenchMarkConstants;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;

/**
 * @author rmaladi
 *
 */
public class RateBenchMarkDAOImpl extends CGBaseDAO implements RateBenchMarkDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(RateBenchMarkDAOImpl.class);
	
	
	@Override
	public boolean saveOrUpdateRateBenchMark(RateBenchMarkHeaderDO rbmhDO)
			throws CGSystemException {

		LOGGER.trace("RateBenchMarkDAOImpl :: saveOrUpdateRateBenchMark() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isRBMCreated = Boolean.FALSE;

		try{
			
			session = createSession();
			tx = session.beginTransaction();
			if(!StringUtil.isNull(rbmhDO)){
			
				session.merge(rbmhDO);
					tx.commit();
					
					isRBMCreated = Boolean.TRUE;
			
		
			}
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RateBenchMarkDAOImpl::saveOrUpdateRateBenchMark :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RateBenchMarkDAOImpl :: saveOrUpdateRateBenchMark() :: End --------> ::::::");
		return isRBMCreated;
	}

	@Override
	public void updateRateBenchMarkHeaderEffectiveToDate(Integer headerId,Date date) 
			throws CGSystemException {
		Session session = null;
		try{
			LOGGER.trace("RateBenchMarkDAOImpl :: updateRateBenchMarkHeaderEffectiveToDate() :: Start --------> ::::::");
		if(!StringUtil.isNull(headerId) && headerId != 0)
		{
			session = createSession();
			Query qry=null;
			qry= session.getNamedQuery(RateBenchMarkConstants.QRY_UPDATE_RATE_BENCHMARK_TO_DATE);
			qry.setDate(RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_EFFECTIVE_TO, date);
			qry.setInteger(RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID, headerId);
			
			qry.executeUpdate();
		}
		}
		catch(Exception e){			
			LOGGER.error("Exception Occured in::RateBenchMarkDAOImpl::updateRateBenchMarkHeaderEffectiveToDate :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}	
		LOGGER.trace("RateBenchMarkDAOImpl :: updateRateBenchMarkHeaderEffectiveToDate() :: END --------> ::::::");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RateBenchMarkMatrixDO> getRateBenchMarkMatrix(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException {
		
		LOGGER.trace("RateBenchMarkDAOImpl :: getRateBenchMarkMatrix() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateBenchMarkMatrixDO> rateMatrixDOList = null;
		 Integer headerId = null;
		 RateBenchMarkHeaderDO rbmhDO = null;
		
			try{
				if(!StringUtil.isEmptyInteger(rbmhTO.getRateBenchMarkHeaderId()))
					headerId = rbmhTO.getRateBenchMarkHeaderId();
				
				else{
					rbmhDO = getLatestRateBencMark(rbmhTO);
					if(!StringUtil.isNull(rbmhDO) && !StringUtil.isEmptyInteger(rbmhDO.getRateBenchMarkHeaderId()))
					headerId = rbmhDO.getRateBenchMarkHeaderId();
				}
				if(!StringUtil.isNull(headerId) && headerId != 0 && !StringUtil.isNull(rbmhTO.getRateProdCatId()) && !StringUtil.isNull(rbmhTO.getRateOriginSectorId()) && rbmhTO.getRateOriginSectorId() != 0){
				
					String query = RateBenchMarkConstants.GET_RATE_MATRIX_DETAILS_BY_IND_PROD_VOB_AND_ORG_SEC_ID;
					String params[] = {RateBenchMarkConstants.PARAM_RATE_INDUSTRY_CAT_ID,RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID, RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_CUST_CAT_ID, RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_PROD_CAT_ID,
							RateBenchMarkConstants.PARAM_RATE_VOB_SLAB_ID, RateBenchMarkConstants.PARAM_RATE_ORG_SECTOR_ID};
					
					
					Object values[] = {rbmhTO.getRateIndustryCategoryId(),headerId, rbmhTO.getRateCustCatId(),rbmhTO.getRateProdCatId(),rbmhTO.getRateVobSlabsId(),rbmhTO.getRateOriginSectorId()};
					
					rateMatrixDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}else if(!StringUtil.isEmptyInteger(headerId) && !StringUtil.isNull(rbmhTO.getRateProdCatId())){
				
					String query = RateBenchMarkConstants.GET_RATE_MATRIX_DETAILS_BY_IND_PROD_AND_VOB_ID;
					String params[] = {RateBenchMarkConstants.PARAM_RATE_INDUSTRY_CAT_ID,RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID, RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_CUST_CAT_ID, RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_PROD_CAT_ID,
							RateBenchMarkConstants.PARAM_RATE_VOB_SLAB_ID};
					
					
					Object values[] = {rbmhTO.getRateIndustryCategoryId(),headerId, rbmhTO.getRateCustCatId(),rbmhTO.getRateProdCatId(),rbmhTO.getRateVobSlabsId()};
					
					rateMatrixDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}
				else{
				if(!StringUtil.isEmptyInteger(headerId) ){	
				/*String query = RateBenchMarkConstants.GET_RATE_BENCH_MARK_DETAILS_BY_INDUSTRY_CATEGORY_ID;
				
				String params[] = {RateBenchMarkConstants.PARAM_RATE_INDUSTRY_CAT_ID,RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID, RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_CUST_CAT_ID};
				
				Object values[] = {rbmhTO.getRateIndustryCategoryId(),headerId, rbmhTO.getRateCustCatId()};
				rateMatrixDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);*/
					rateMatrixDOList = new ArrayList<RateBenchMarkMatrixDO>();
					Set<RateBenchMarkProductDO> rbmpDOSet =  rbmhDO.getRateBenchMarkProductDO();
					Set<RateBenchMarkMatrixHeaderDO> rbmmhDOSet =  new HashSet<RateBenchMarkMatrixHeaderDO>();
					Set<RateBenchMarkMatrixDO> rbmmDOSet =  new HashSet<RateBenchMarkMatrixDO>();
					Set<RateBenchMarkMatrixDO> matrixDOSet =  new HashSet<RateBenchMarkMatrixDO>();
					if(!CGCollectionUtils.isEmpty(rbmpDOSet)){
					for(RateBenchMarkProductDO rbmpDO : rbmpDOSet){
						
						rbmmhDOSet = rbmpDO.getRateBenchMarkMatrixHeaderDO();
						if(!CGCollectionUtils.isEmpty(rbmmhDOSet)){
						for(RateBenchMarkMatrixHeaderDO rbmmhDO : rbmmhDOSet){
							rbmmDOSet = rbmmhDO.getRateBenchMarkMatrixDO();
							if(!CGCollectionUtils.isEmpty(rbmmDOSet)){
								matrixDOSet.addAll(rbmmDOSet);
							}
						}
						}
					}
					}
					if(!CGCollectionUtils.isEmpty(matrixDOSet)){
						rateMatrixDOList = new ArrayList<RateBenchMarkMatrixDO>(matrixDOSet);
					}
				}
				
			}
			}catch (Exception e) {
				LOGGER.error("ERROR : RateBenchMarkDAOImpl.getRateBenchMarkMatrix", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateBenchMarkDAOImpl :: getRateBenchMarkMatrix() :: End --------> ::::::");
			return rateMatrixDOList;
	}


	private RateBenchMarkHeaderDO getLatestRateBencMark(RateBenchMarkHeaderTO rbmhTO) throws CGSystemException{

		
		List<RateBenchMarkHeaderDO> rateBenchMarkHeaderDOList = getRatebenchMarkHeaderList(rbmhTO);
		
		try{
			LOGGER.trace("RateBenchMarkDAOImpl :: getLatestRateBencMark() :: Start --------> ::::::");
		if(!CGCollectionUtils.isEmpty(rateBenchMarkHeaderDOList)){
			
			if(!StringUtil.isNull(rbmhTO.getRateBenchMarkType()) && rbmhTO.getRateBenchMarkType().equals("R")){
				if(rateBenchMarkHeaderDOList.size() == 1){
					return null;
				}else if(StringUtil.isNull(rateBenchMarkHeaderDOList.get(0).getRateBenchMarkEffectiveTo())
						&& !StringUtil.isNull(rateBenchMarkHeaderDOList.get(1).getRateBenchMarkEffectiveTo())
						&& (rateBenchMarkHeaderDOList.get(1).getRateBenchMarkEffectiveTo().compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInYYYYMMDDHHMM())))<0	
						){
					return null;
				}else{
					return rateBenchMarkHeaderDOList.get(0);
				}
			}
			
			else{
			if(rateBenchMarkHeaderDOList.size() == 1){
				return rateBenchMarkHeaderDOList.get(0);
			}
			else if(!StringUtil.isNull(rateBenchMarkHeaderDOList.get(1).getRateBenchMarkEffectiveTo())
					&& (rateBenchMarkHeaderDOList.get(1).getRateBenchMarkEffectiveTo().compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInYYYYMMDDHHMM())))<0	
					&& rateBenchMarkHeaderDOList.get(0).getIsApproved().equals("Y")){
				return rateBenchMarkHeaderDOList.get(0);
			}else{
				return rateBenchMarkHeaderDOList.get(1);
			}
			}
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RateBenchMarkDAOImpl.getLatestRateBencMark", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RateBenchMarkDAOImpl :: getLatestRateBencMark() :: END --------> ::::::");
		return null;
	} 
	
	
	
	@SuppressWarnings("unchecked")
	private List<RateBenchMarkHeaderDO> getRatebenchMarkHeaderList(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException {
		LOGGER.trace("RateBenchMarkDAOImpl :: getRatebenchMarkHeaderList() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<RateBenchMarkHeaderDO> rateBenchMarkHeaderDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(RateBenchMarkHeaderDO.class,"rateBenchMarkHeaderDO");
				if(!StringUtil.isNull(rbmhTO.getRateIndustryCategoryId()))
					criteria.add(Restrictions.eq("rateBenchMarkHeaderDO.rateIndustryCategoryDO.rateIndustryCategoryId",rbmhTO.getRateIndustryCategoryId()));
				if(!StringUtil.isNull(rbmhTO.getRateIndustryCategoryCode())){
					criteria.createAlias("rateBenchMarkHeaderDO.rateIndustryCategoryDO", "industryCategory");
					criteria.add(Restrictions.eq("industryCategory.rateIndustryCategoryCode",rbmhTO.getRateIndustryCategoryCode()));
				}
				if(!StringUtil.isNull(rbmhTO.getEmpId()))
					criteria.add(Restrictions.eq("rateBenchMarkHeaderDO.approver.employeeId",rbmhTO.getEmpId()));

				criteria.addOrder(Order.desc("rateBenchMarkHeaderDO.rateBenchMarkHeaderId"));
				criteria.setMaxResults(2);
				rateBenchMarkHeaderDOList = criteria.list(); 
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateBenchMarkDAOImpl.getRatebenchMarkHeaderList", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RateBenchMarkDAOImpl :: getRatebenchMarkHeaderList() :: End --------> ::::::");
			return rateBenchMarkHeaderDOList;
	}

	@Override
	public boolean updateApproverDetails(Integer empId, Integer headerId)
			throws CGSystemException {
		Session session = null;
		try{
			LOGGER.trace("RateBenchMarkDAOImpl :: updateApproverDetails() :: Start --------> ::::::");
		if(!StringUtil.isNull(headerId) && headerId != 0 && !StringUtil.isNull(empId) && empId != 0)
		{
			session = createSession();
			Query qry=null;
			qry= session.getNamedQuery(RateBenchMarkConstants.QRY_UPDATE_RATE_BENCHMARK_APPROVER);
			qry.setInteger(RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_APPROVER,empId);
			qry.setInteger(RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID, headerId);
			
			qry.executeUpdate();
		}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RateBenchMarkDAOImpl::updateApproverDetails :: " + e);
			return Boolean.FALSE;
		}
		finally{
			closeSession(session);
		}
		LOGGER.trace("RateBenchMarkDAOImpl :: updateApproverDetails() :: END --------> ::::::");
		return Boolean.TRUE;
	}

	@Override
	public RateBenchMarkHeaderDO getRateBenchMarkHeader(
			RateBenchMarkHeaderTO rbmhTO) throws CGSystemException {
		/* Session session = null;*/
		 RateBenchMarkHeaderDO rateBenchMarkHeaderDO = null;
		 /*Criteria criteria=null;*/
			try{
				LOGGER.trace("RateBenchMarkDAOImpl :: getRateBenchMarkHeader() :: Start --------> ::::::");
				/*session = createSession();
				criteria = session.createCriteria(RateBenchMarkHeaderDO.class,"rateBenchMarkHeaderDO");
				if(!StringUtil.isNull(rbmhTO.getRateIndustryCategoryId()))
					criteria.add(Restrictions.eq("rateBenchMarkHeaderDO.rateIndustryCategoryDO.rateIndustryCategoryId",rbmhTO.getRateIndustryCategoryId()));
					criteria.add(Restrictions.le("rateBenchMarkHeaderDO.rateBenchMarkEffectiveFrom", DateUtil.getCurrentDate()));
					criteria.add(Restrictions.eq("rateBenchMarkHeaderDO.isApproved", RateBenchMarkConstants.YES));
					Criterion ctn1 = Restrictions.isNull("rateBenchMarkHeaderDO.rateBenchMarkEffectiveTo");
					Criterion ctn2 = Restrictions.ge("rateBenchMarkHeaderDO.rateBenchMarkEffectiveTo",DateUtil.getCurrentDate());
					criteria.add(Restrictions.or(ctn1,ctn2));
					
				
				criteria.addOrder(Order.desc("rateBenchMarkHeaderDO.rateBenchMarkHeaderId"));
				rateBenchMarkHeaderDO = (RateBenchMarkHeaderDO) criteria.uniqueResult(); */
				rateBenchMarkHeaderDO = getLatestRateBencMark(rbmhTO);
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RateBenchMarkDAOImpl.getRateBenchMarkHeader", e);
				throw new CGSystemException(e);
			}					
			LOGGER.trace("RateBenchMarkDAOImpl :: getRateBenchMarkHeader() :: End --------> ::::::");
			return rateBenchMarkHeaderDO;
	}

	@Override
	public List<RateBenchMarkHeaderDO> checkEmpIdCorpApprover(Integer empId)
			throws CGSystemException {
		LOGGER.trace("RateBenchMarkDAOImpl :: checkEmpIdCorpApprover() :: Start --------> ::::::");
		List<RateBenchMarkHeaderDO> rbmhDOList = new ArrayList<RateBenchMarkHeaderDO>();
		
		RateBenchMarkHeaderDO rbmhDOG = null;
		RateBenchMarkHeaderDO rbmhDOB = null;
		RateBenchMarkHeaderTO rbmhTO = new RateBenchMarkHeaderTO();
		
		rbmhTO.setEmpId(empId);
		
		rbmhTO.setRateIndustryCategoryCode(RateCommonConstants.IND_CODE_GENEREL);
		
		
		rbmhDOG = getLatestRateBencMark(rbmhTO);
		
		if(!StringUtil.isNull(rbmhDOG)){
		
			rbmhDOList.add(rbmhDOG);
		}
		
		rbmhTO.setRateIndustryCategoryCode(RateCommonConstants.IND_CODE_BFSI);
		
		rbmhDOB = getLatestRateBencMark(rbmhTO);
		
		if(!StringUtil.isNull(rbmhDOB)){
		
			rbmhDOList.add(rbmhDOB);
		}
		
		if(rbmhDOList.size()<=0)
			return null;
		LOGGER.trace("RateBenchMarkDAOImpl :: checkEmpIdCorpApprover() :: END --------> ::::::");
		return rbmhDOList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateBenchMarkHeaderDO getRateBenchMarkByHeaderId(Integer headerId)
			throws CGSystemException {
		//String query = RateBenchMarkConstants.GET_RATE_BENCH_MARK_DETAILS_BY_INDUSTRY_CATEGORY_ID;
		List<RateBenchMarkHeaderDO> rbmhList = null;
		RateBenchMarkHeaderDO rbmhDO = null;
		String query = "getBenchMarkDetailsByHeaderId";
		String params[] = {RateBenchMarkConstants.PARAM_RATE_BENCH_MARK_HEADER_ID};
		
		Object values[] = {headerId};
		rbmhList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		
		if(!CGCollectionUtils.isEmpty(rbmhList)){
			rbmhDO = rbmhList.get(0);
		}
		
		return rbmhDO;
	}
	
	

}
