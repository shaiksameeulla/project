/**
 * 
 */
package com.dtdc.bodbadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDO.CGBaseEntity;
import com.dtdc.bodbadmin.constant.BranchDBAdminConstant;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalDBDataPersistDAOImpl.
 *
 * @author nisahoo
 */
public class LocalDBDataPersistDAOImpl extends HibernateDaoSupport implements
		LocalDBDataPersistDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger
			.getLogger(LocalDBDataPersistDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveBookingDataToLocalBranch(List)
	 */
	@Override
	public void saveBookingDataToLocalBranch(List<BookingDO> bookingDOList)
			throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveBookingDataToLocalBranch() : SATRT");
		try {

			getHibernateTemplate().saveOrUpdateAll(bookingDOList);

			LOGGER.debug("LocalDBDataPersistDAOImpl : saveBookingDataToLocalBranch() : END");
		} catch (Exception e) {
			LOGGER.error("LocalDBDataPersistDAOImpl : saveBookingDataToLocalBranch() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveRtoDataToLocalBranch(List)
	 */
	@Override
	public void saveRtoDataToLocalBranch(List<RtnToOrgDO> rtnToOrgDOList) {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveRtoDataToLocalBranch() : SATRT");
		getHibernateTemplate().saveOrUpdateAll(rtnToOrgDOList);	
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveRtoDataToLocalBranch() : END");
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveManifestDataToLocalBranch(List)
	 */
	@Override
	public void saveManifestDataToLocalBranch(List<ManifestDO> manifestDOList) {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveManifestDataToLocalBranch() : SATRT");
		getHibernateTemplate().saveOrUpdateAll(manifestDOList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveManifestDataToLocalBranch() : END");

	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveHeldUpReleaseToLocalBranch(List)
	 */
	@Override
	public Boolean saveHeldUpReleaseToLocalBranch(
			List<HeldUpReleaseDO> heldUpReleaseDoList) throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveHeldUpReleaseToLocalBranch() : START");
		getHibernateTemplate().saveOrUpdateAll(heldUpReleaseDoList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveHeldUpReleaseToLocalBranch() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveGoodsRenewalToLocalBranch(List)
	 */
	@Override
	public Boolean saveGoodsRenewalToLocalBranch(
			List<GoodsRenewalDO> goodsRenewalDoList) throws CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsRenewalToLocalBranch() : START");
		getHibernateTemplate().saveOrUpdateAll(goodsRenewalDoList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsRenewalToLocalBranch() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveGoodsCanclToLocalBranch(List)
	 */
	@Override
	public Boolean saveGoodsCanclToLocalBranch(
			List<GoodscCancellationDO> goodsCanclDoList)
			throws CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsCanclToLocalBranch() : START");
		getHibernateTemplate().saveOrUpdateAll(goodsCanclDoList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsCanclToLocalBranch() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveDispatchDetailsToLocalBranch(List)
	 */
	@Override
	public Boolean saveDispatchDetailsToLocalBranch(
			List<DispatchDO> dispatchDOList) throws CGSystemException {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdateAll(dispatchDOList);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveGoodsIsueDetailsToLocalBranch(List)
	 */
	@Override
	public Boolean saveGoodsIsueDetailsToLocalBranch(
			List<GoodsIssueDO> goodsIssueDoList) throws CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsIsueDetailsToLocalBranch() : START");
		getHibernateTemplate().saveOrUpdateAll(goodsIssueDoList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveGoodsIsueDetailsToLocalBranch() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveDlvMnfstDetailsToLocalBranch(List)
	 */
	@Override
	public Boolean saveDlvMnfstDetailsToLocalBranch(
			List<DeliveryDO> dlvMnfstDoList) throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveDlvMnfstDetailsToLocalBranch() : START");
		getHibernateTemplate().saveOrUpdateAll(dlvMnfstDoList);
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveDlvMnfstDetailsToLocalBranch() : END");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#getGoodsIssueDtlsByBusinessKey(GoodsIssueDO)
	 */
	@Override
	public List<GoodsIssueDO> getGoodsIssueDtlsByBusinessKey(GoodsIssueDO extractedDo)
			throws CGSystemException {
		List<GoodsIssueDO> issueDoList = null;
		GoodsIssueDO issueDo = null;
		LOGGER.debug("LocalDBDataPersistDAOImpl : getGoodsIssueDtlsByBusinessKey() : START");
		String query = BranchDBAdminConstant.GET_GOODS_ISSUE_DTLS_BY_BUSINESS_KEY;
		String params[] = { BranchDBAdminConstant.ISSUE_NUMBER,
				BranchDBAdminConstant.REQ_SLIP_NUMBER };
		Object values[] = { extractedDo.getGoodsIssueNumber(),
				extractedDo.getReqSlipNumber() };
		LOGGER.debug("LocalDBDataPersistDAOImpl : getGoodsIssueDtlsByBusinessKey() : Business Keys===[ Goods issue Number : "
				+ extractedDo.getGoodsIssueNumber()
				+ "\t;Requistition Slip Number"
				+ extractedDo.getReqSlipNumber() + "]");
		issueDoList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				query, params, values);
		LOGGER.debug("LocalDBDataPersistDAOImpl : getGoodsIssueDtlsByBusinessKey() : END");
		return issueDoList;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#deleteGoodsIssueDtlsByIssueId(GoodsIssueDO)
	 */
	@Override
	public Boolean deleteGoodsIssueDtlsByIssueId(GoodsIssueDO issueDo)
			throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : deleteGoodsIssueDtlsByIssueId() :#####START##### Deleting the record with the primary key :["
				+ issueDo.getGoodsIssueId() + "]");
		getHibernateTemplate().delete(issueDo);
		LOGGER.debug("LocalDBDataPersistDAOImpl : deleteGoodsIssueDtlsByIssueId() :#####END##### Deleted the record with the primary key :["
				+ issueDo.getGoodsIssueId() + "]");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#deleteGoodsIssueDtlsListByIssueId(List)
	 */
	@Override
	public Boolean deleteGoodsIssueDtlsListByIssueId(
			List<GoodsIssueDO> existedDolist) throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : deleteGoodsIssueDtlsListByIssueId() :#####START##### Deleting the records  ");
		getHibernateTemplate().deleteAll(existedDolist);
		LOGGER.debug("LocalDBDataPersistDAOImpl : deleteGoodsIssueDtlsListByIssueId() :#####END##### Deleted the records  ");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#getManifestId(String, String, String, Integer)
	 */
	@SuppressWarnings("unchecked")
	public Integer getManifestId(String mnfstNumber, String consgNum,
			String mnfstType, Integer mnfstTypeId) throws CGSystemException {
		Integer manifestId = 0;
		List<Integer> mnfstTypeIds = null;
		LOGGER.debug("LocalDBDataPersistDAOImpl : getManifestId() : START");
		String query = BranchDBAdminConstant.GET_MANIFEST_ID;
		String params[] = { BranchDBAdminConstant.MANIFEST_NUMBER,
				BranchDBAdminConstant.CONSG_NUMBER,
				BranchDBAdminConstant.MANIFEST_TYPE_ID,
				BranchDBAdminConstant.MANIFEST_TYPE };
		Object values[] = { mnfstNumber, consgNum, mnfstTypeId, mnfstType };
		mnfstTypeIds = (List<Integer>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(query, params, values);
		if(mnfstTypeIds !=null && mnfstTypeIds.size() > 0){
			manifestId = mnfstTypeIds.get(0);
		}
		return manifestId;
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.bodbadmin.dao.LocalDBDataPersistDAO#saveBookingDataToLocalBranch(BookingDO)
	 */
	@Override
	public Boolean saveBookingDataToLocalBranch(BookingDO bookingDo)
			throws CGSystemException {
			LOGGER.debug("LocalDBDataPersistDAOImpl : saveBookingDataToLocalBranch():(single BookingDo object as param) : SATRT");
			getHibernateTemplate().save(bookingDo);
			LOGGER.debug("LocalDBDataPersistDAOImpl : saveBookingDataToLocalBranch()(single BookingDo object as param) : END");
			return Boolean.TRUE;
	}
	
	public Boolean saveBaseEntityDataToLocalBranch(
			List<CGBaseEntity> baseEntityList) throws CGSystemException {
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveBaseEntityDataToLocalBranch() : START");
		try{
			getHibernateTemplate().flush();
			getHibernateTemplate().saveOrUpdateAll(baseEntityList);	
			getHibernateTemplate().flush();
			
		}catch(Exception e){
			LOGGER.error("LocalDBDataPersistDAOImpl : saveBaseEntityDataToLocalBranch() : "+e.getMessage());
			e.printStackTrace();
		}
		
		LOGGER.debug("LocalDBDataPersistDAOImpl : saveBaseEntityDataToLocalBranch() : END");
		return true;
	}
}
