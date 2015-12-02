/**
 * 
 */
package src.com.dtdc.mdbDao.dmc;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import src.com.dtdc.constants.DMCConstants;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.transaction.delivery.DeliveryManagementDO;
import com.dtdc.to.dmc.DmcGridTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DelvryMangmntCellMDBDAOImpl.
 *
 * @author mohammes
 */
public class DelvryMangmntCellMDBDAOImpl extends HibernateDaoSupport implements
DelvryMangmntCellMDBDAO {

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dmc.DelvryMangmntCellMDBDAO#saveDeliverymanagementInfo(List, List)
	 */
	@Override
	public boolean saveDeliverymanagementInfo(
			List<DeliveryManagementDO> deliveryManagementDoList,
			List<DmcGridTO> dmcGridTo) throws CGSystemException {
		boolean result=false;
		if(deliveryManagementDoList!=null&& deliveryManagementDoList.size()>0){
			for (int i=0;i<deliveryManagementDoList.size();i++){
				DeliveryManagementDO deliveryDo=deliveryManagementDoList.get(i);
				DmcGridTO dmcGrid=dmcGridTo.get(i);
				Integer rowid=	checkForDrs(deliveryDo.getRunsheetNumber());
				if(rowid!=null){
					deliveryDo.setDeliveryManagementId(rowid);
					getHibernateTemplate().update(deliveryDo);
				} else {
					getHibernateTemplate().save(deliveryDo);
				}
				updateDeliveryInfo(dmcGrid);
			}

			getHibernateTemplate().flush();
			result=true;
			//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDeliverymanagementInfo::  Information  is saved");
		} else {
			//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDeliverymanagementInfo:: There is no Information  to save");
			return result;
		}

		return false;
	}

	/**
	 * Check for drs.
	 *
	 * @param drsNo the drs no
	 * @return the integer
	 */
	private Integer checkForDrs(String drsNo){
		Integer rowid=null;
		List rowvalues=null;
		rowvalues= getHibernateTemplate()
		.findByNamedQueryAndNamedParam(DMCConstants.CHECK_FOR_DRS_HQL, DMCConstants.DRS_NO,
				drsNo);
		if(rowvalues.size()>0){
			rowid=(Integer)rowvalues.get(0);
		}
		return rowid;
	}

	/**
	 * Update delivery info.
	 *
	 * @param dmcGridInfo the dmc grid info
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean updateDeliveryInfo(
			DmcGridTO dmcGridInfo)
	throws CGSystemException {
		Boolean result=false;
		if( dmcGridInfo!=null) {
			Session session = null;
			//Transaction tx=null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			//	tx=session.beginTransaction();
			try {
				if (dmcGridInfo.getServiceId() == DMCConstants.FOUR_SIX_CONSTANT) {// cod information

					String conNum=dmcGridInfo.getConsignmentNo();
					Double codAmount = dmcGridInfo.getCodAmnt();
					Integer chequeNo = dmcGridInfo.getChequeNo();

					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---start======COD");

					String hql = "UPDATE com.dtdc.domain.transaction.delivery.DeliveryDO delivery "
						+ " SET delivery.codAmt=:codAmount,"
						+ " delivery.chequeNo=:chequeNo"
						+ " WHERE delivery.conNum=:conNum";


					Query query = session.createQuery(hql);

					query.setString("conNum", conNum);
					query.setDouble("codAmount", codAmount);
					query.setInteger("chequeNo", chequeNo);
					query.executeUpdate();
					result=true;



					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---end======>cod-updated");

				} else if (dmcGridInfo.getServiceId() == DMCConstants.FOUR_SEVEN_CONSTANT) {// fod information
					String conNum=dmcGridInfo.getConsignmentNo();
					Double fodAmount = dmcGridInfo.getFodAmnt();
					String recept = dmcGridInfo.getReceiptNo();

					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---start======>");
					String hql = "UPDATE com.dtdc.domain.transaction.delivery.DeliveryDO delivery "
						+ " SET delivery.fodAmt= :fodAmount,"
						+ " delivery.receiptNo=:recept"
						+ " WHERE delivery.conNum= :conNum ";


					Query query = session.createQuery(hql);

					query.setString("conNum", conNum);
					query.setDouble("fodAmount", fodAmount);
					query.setString("recept", recept);
					query.executeUpdate();

					result=true;

					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---======>FOD--updated");
				} else if (dmcGridInfo.getServiceId() == DMCConstants.FOUR_FIVE_CONSTANT) { // cfd information

					String conNum=dmcGridInfo.getConsignmentNo();
					Double fodAmount = dmcGridInfo.getFodAmnt();
					String recept = dmcGridInfo.getReceiptNo();
					Double codAmount = dmcGridInfo.getCodAmnt();
					Integer chequeNo = dmcGridInfo.getChequeNo();

					String hql = "UPDATE com.dtdc.domain.transaction.delivery.DeliveryDO delivery "
						+ " SET  delivery.codAmt=:codAmount ,"
						+ " delivery.chequeNo=:chequeNo ,"
						+ " delivery.receiptNo=:recept ,"
						+ " delivery.fodAmt= :fodAmount"
						+ " WHERE delivery.conNum=:conNum";
					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---start=CFD=====>");


					Query query = session.createQuery(hql);

					query.setString("conNum", conNum);
					query.setDouble("fodAmount", fodAmount);
					query.setString("recept", recept);
					query.setDouble("codAmount", codAmount);
					query.setInteger("chequeNo", chequeNo);
					query.executeUpdate();


					//LOGGER.debug("DelvryMangmntCellDAOImpl::saveDrsEntryDetails::info---=====>CFD-updated");
					result=true;

				}// end of if
				//	tx.commit();
			} catch (Exception e) {
				logger.error("DelvryMangmntCellMDBDAOImpl::updateDeliveryInfo occured:"
						+e.getMessage());
				//tx.rollback();
				throw new CGSystemException(e);
			} finally{
				session.close();
			}
		}
		return result;


	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.dmc.DelvryMangmntCellMDBDAO#saveDMC(List)
	 */
	@Override
	public boolean saveDMC(
			List<DeliveryManagementDO> deliveryManagementDoList) throws CGSystemException {

		getHibernateTemplate().saveOrUpdateAll(deliveryManagementDoList);

		return true;
	}

}
