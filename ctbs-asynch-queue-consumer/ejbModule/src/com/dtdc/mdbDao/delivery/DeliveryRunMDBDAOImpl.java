package src.com.dtdc.mdbDao.delivery;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;

/**
 * Class for uc 71 & 71.
 *
 * @author vishnp
 */
public class DeliveryRunMDBDAOImpl extends CGBaseDAO implements
		DeliveryRunMDBDAO {
	
	/** logger. */
	private Logger LOGGER = LoggerFactory.getLogger(DeliveryRunMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#getDeliveryDOs(String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryDOs(String...consgNo)
			throws CGSystemException, CGBusinessException {
		List<DeliveryDO> dos = new ArrayList<DeliveryDO>();
		String hql ="from com.dtdc.domain.transaction.delivery.DeliveryDO delivery WHERE delivery.conNum IN (:consgNo) and delivery.consgStatus=:status";
		Session session = null;	
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setParameterList("consgNo", consgNo);
			String[] status = {"C"};
			query.setParameterList("status", status);
			dos = query.list();
		} catch (HibernateException e) {
			logger.error("DeliveryRunMDBDAOImpl::getDeliveryDOs occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}finally{
			session.flush();
			session.close();
		}
		
		return dos;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#updateAllDeliveryDOs(List, List)
	 */
	@Override
	public void updateAllDeliveryDOs(
			List<DeliveryDO> deliveryDOs, List<MiscExpenseDO> miscExpenseDos) {
		try {
			if(miscExpenseDos != null && !miscExpenseDos.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(miscExpenseDos);
			}
			if(deliveryDOs != null && !deliveryDOs.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(deliveryDOs);
			}
		} catch (Exception e) {
			logger.error("DeliveryRunMDBDAOImpl::updateAllDeliveryDOs occured:"
					+e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#getReasonDOById(Integer)
	 */
	@Override
	public ReasonDO getReasonDOById(Integer id) throws CGSystemException,
			CGBusinessException {
		return getHibernateTemplate().get(ReasonDO.class, id);
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#saveOrUpdateAllDeliveryDOs(List, List, List)
	 */
	@Override
	public void saveOrUpdateAllDeliveryDOs(List<? extends DeliveryDO> deliveryDOs,
			List<MiscExpenseDO> miscExpenseDos, List<? extends DeliveryDO> bdmFdmDOs) throws CGBusinessException {
		try {
			if(miscExpenseDos != null && !miscExpenseDos.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(miscExpenseDos);
			}
			if(deliveryDOs != null && !deliveryDOs.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(deliveryDOs);
			}
			if(bdmFdmDOs != null && !bdmFdmDOs.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(bdmFdmDOs);
			}
		} catch (Exception e) {
			logger.error("DeliveryRunMDBDAOImpl::saveOrUpdateAllDeliveryDOs occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#getFDMOrBDMDtls(Boolean, List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getFDMOrBDMDtls(Boolean isBdm,List<String>consgNo)
			throws CGSystemException, CGBusinessException {
		List<DeliveryDO> dos =null;
		String hql=null;
		if(!isBdm){
		 hql ="from com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO delivery WHERE delivery.conNum IN (:consgNo) and delivery.consgStatus=:status";
		}else {
			hql ="from com.dtdc.domain.transaction.delivery.DeliveryDO delivery WHERE delivery.conNum IN (:consgNo) and delivery.consgStatus=:status";
		}
		 Session session = null;	
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setParameterList("consgNo", consgNo);
			String[] status = {"O"};
			query.setParameterList("status", status);
			dos = query.list();
		} catch (HibernateException e) {
			logger.error("DeliveryRunMDBDAOImpl::getFDMOrBDMDtls occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}finally{
			session.close();
		}
		
		return dos;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.DeliveryRunMDBDAO#getDeliveryDtls(String, String, String)
	 */
	@Override
	public List<? extends DeliveryDO> getDeliveryDtls(String fdmNumber,
			String runsheetNum, String cnNumber) throws CGSystemException {
		Session session = null;	
		List<DeliveryDO>  aDeliveryDOs = null;
		try {			
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria  criteria = session.createCriteria(DeliveryDO.class);
			if(!StringUtil.isEmpty(fdmNumber)&& !StringUtil.isEmpty(runsheetNum)){
				criteria.add(Restrictions.eq("runSheetNum",runsheetNum))
				.add(Restrictions.eq("fdmNumber", fdmNumber));
			}else if(!StringUtil.isEmpty(fdmNumber)){
				criteria.add(Restrictions.eq("fdmNumber", fdmNumber));
			}else if(!StringUtil.isEmpty(fdmNumber)){
				criteria.add(Restrictions.eq("runSheetNum", runsheetNum));
			}
			criteria.add(Restrictions.eq("consgStatus", "A"));
			aDeliveryDOs = criteria.add(Restrictions.eq("conNum", cnNumber)).list();							
							
			
		} catch (HibernateException e) {
			logger.error("DeliveryRunMDBDAOImpl::getDeliveryDtls occured:"
					+e.getMessage());
			//throw new CGBusinessException(e);
		}finally{
			session.close();
		}
		return aDeliveryDOs;
	}
	
}
