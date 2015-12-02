package src.com.dtdc.mdbDao.manifest;

/**
 * 
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;

// TODO: Auto-generated Javadoc
/**
 * The Class PODMDBDAOImpl.
 *
 * @author mohammal
 */
public class PODMDBDAOImpl extends HibernateDaoSupport implements PODMDBDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(PODMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#savePodManifest(ManifestDO)
	 */
	@Override
	public void savePodManifest(ManifestDO manifestDo) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			//session.save(manifestDo);
			session.saveOrUpdate(manifestDo);
			tx.commit();
		} catch(Exception ex) {
			logger.error("PODMDBDAOImpl::savePodManifest::Exception occured:"
					+ex.getMessage());
			tx.commit();
		} finally {
			session.flush();
			session.close();
		}
		//getHibernateTemplate().save(manifestDo);
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#updatePODDetails(DeliveryDO)
	 */
	@Override
	public void updatePODDetails(DeliveryDO podDetails) {
		LOGGER.debug("PODDAOImpl::savePodDetails::start:=======>");
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.update(podDetails);
		tx.commit();
		session.flush();
		session.close();
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getPodManifestType()
	 */
	public ManifestTypeDO getPodManifestType() {
		ManifestTypeDO mft = null;
		List<ManifestTypeDO> mfType = getHibernateTemplate().findByNamedQuery(ManifestConstant.GET_POD_MF_TYPE_QUERY);
		if (mfType != null && !mfType.isEmpty()) {
			mft = mfType.get(0);
		}
		return mft;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getDeliveryByCnNumberAndProductId(String, int)
	 */
	@Override
	public DeliveryDO getDeliveryByCnNumberAndProductId(String consignNumber, int productId) {
		DeliveryDO delivery = null;
		String[] params = ManifestConstant.GET_POD_STATUS_BY_CN_AND_PRODUCT_PARAM.split(",");
		Object[] values = 
		{
				consignNumber,
				productId
		};
		List<DeliveryDO> deliveryList = getHibernateTemplate().findByNamedQueryAndNamedParam(ManifestConstant.GET_DELIVERY_BY_CN_QUERY, params, values);
		if(deliveryList != null && !deliveryList.isEmpty()) {
			delivery = deliveryList.get(0);
		}
		return delivery;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getIncomingPODManifestByMfNumber(String)
	 */
	public List<ManifestDO> getIncomingPODManifestByMfNumber(String mfNumber) {
		List<ManifestDO> mfDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(ManifestConstant.GET_INCOMING_PODS_BY_MF_NUMBER_QUERY, 
				ManifestConstant.GET_MANIFEST_BY_MF_NUMBER_PARAM, mfNumber);
		
		return mfDetails;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getOutgoingPODManifestByMfNumber(String)
	 */
	public List<ManifestDO> getOutgoingPODManifestByMfNumber(String mfNumber) {
		List<ManifestDO> mfDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(ManifestConstant.GET_OUT_GOING_PODS_BY_MF_NUMBER_QUERY, 
				ManifestConstant.GET_MANIFEST_BY_MF_NUMBER_PARAM, mfNumber);
		
		return mfDetails;
	}
	
	//** Add By Jay 03-Oct-2011 --**/
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getIncomingPODManifestByConsigNumber(String, String)
	 */
	@Override
	public List<ManifestDO> getIncomingPODManifestByConsigNumber(String consigNumber,String podMfNumber) {
		
		String[] params = { ManifestConstant.CONSG_NUMBER,
				ManifestConstant.GET_MANIFEST_BY_MF_NUMBER_PARAM };
		Object[] values = { consigNumber, podMfNumber };
		List<ManifestDO> mfDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(ManifestConstant.GET_INCOMING_PODS_BY_CONSIG_NUMBER_QUERY, 
				params, values);
		
		return mfDetails;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#saveIncomingPodManifest(ManifestDO)
	 */
	@Override
	public void saveIncomingPodManifest(ManifestDO manifestDo) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(manifestDo);
			//session.saveOrUpdate(manifestDo);
			tx.commit();
		} catch(Exception e) {
			logger.error("PODMDBDAOImpl::saveIncomingPodManifest::Exception occured:"
					+e.getMessage());
			tx.commit();
		} finally {
			session.flush();
			session.close();
		}
		//getHibernateTemplate().save(manifestDo);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#saveMiscExp(List)
	 */
	@Override
	public void saveMiscExp(List<MiscExpenseDO> miscExpenseDOList) throws CGSystemException {
			getHibernateTemplate().saveOrUpdateAll(miscExpenseDOList);	
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#getOutgoingPODManifestByConsigNumber(String, String)
	 */
	@Override
	public List<ManifestDO> getOutgoingPODManifestByConsigNumber(String consigNumber,String podMfNumber) {
		
		String[] params = { ManifestConstant.CONSG_NUMBER,
				ManifestConstant.GET_MANIFEST_BY_MF_NUMBER_PARAM };
		Object[] values = { consigNumber, podMfNumber };
		List<ManifestDO> mfDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(ManifestConstant.GET_OUT_GOING_PODS_BY_CONSIG_NUMBER_QUERY, 
				params, values);
		
		return mfDetails;
	}
	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PODMDBDAO#savePenaltychargeForUnstampedPOD(List)
	 */
	@Override
	public void savePenaltychargeForUnstampedPOD(
			List<MiscExpenseDO> miscExpenseDOList) throws CGSystemException {
		
		try {
			getHibernateTemplate().saveOrUpdateAll(miscExpenseDOList);
		} catch (Exception e) {
			logger.error("PODMDBDAOImpl::savePenaltychargeForUnstampedPOD::Exception occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}
		
	}
	
	
}
