/**
 * 
 */
package com.ff.web.drs.manualdrs.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;

/**
 * @author mohammes
 *
 */
public class ManualDrsDAOImpl extends CGBaseDAO implements ManualDrsDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManualDrsDAOImpl.class);
	
	
	
	@Override
	public ManifestDO getManifestDetailsByManifestNumber(AbstractDeliveryTO drsTO) throws CGSystemException {
		
		//return getManifestDetailsForDrs(drsTO);
		return getManifestDetailsByManifestNumberUsingCriteria(drsTO);
	}
	private ManifestDO getManifestDetailsByManifestNumberUsingCriteria(
			AbstractDeliveryTO drsTO) throws CGSystemException {
		List<ManifestDO> manifestDtls=null;
		Session session=createSession();
		Criteria manifestCriteria=null;
		try {
			 manifestCriteria= session.createCriteria(ManifestDO.class,"manifest");
			 manifestCriteria.setFetchMode("manifest.consignments", FetchMode.JOIN);
			 manifestCriteria.setFetchMode("manifest.doxConsignments", FetchMode.JOIN);
			 manifestCriteria.add(Restrictions.eq("manifest.manifestNo", drsTO.getDrsNumber()));
			 
			 DetachedCriteria updatingProcess=null;

				//for Sub Query ****START****
			 updatingProcess = DetachedCriteria.forClass(ProcessDO.class, "processDo");
			 updatingProcess.add( Restrictions.in("processDo.processCode",new Object[]{CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX,CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL}));  
			 updatingProcess.setProjection(Projections.property("processDo.processId"));
				//for Sub Query ****END****
				//for Main Query Join Condition
			 manifestCriteria.add(Property.forName("manifest.updatingProcess.processId").in(updatingProcess));
			 manifestCriteria.add(Restrictions.eq("manifest.originOffice.officeId", drsTO.getLoginOfficeId()));
			
			manifestDtls = manifestCriteria.list();
			if(!StringUtil.isEmptyList(manifestDtls)){
			 	Hibernate.initialize(manifestDtls.get(0).getConsignments());
			}
		} catch (Exception e) {
			LOGGER.error("ManualDrsDAOImpl::getManifestDetailsByManifestNumber :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		
		return !CGCollectionUtils.isEmpty(manifestDtls)?manifestDtls.get(0):null;
	}
	public ManifestDO getManifestDetailsForDrs(AbstractDeliveryTO drsTO) throws CGSystemException {
		List<ManifestDO> manifestDtls=null;
		Session session=createSession();
		try {
			Query qry = session.getNamedQuery(DrsCommonConstants.QRY_GET_THIRD_PARTY_MNFST_DTS_FOR_DRS);
			qry.setString(DrsCommonConstants.QRY_PARAM_MANIFESTNO, drsTO.getDrsNumber());
			qry.setParameterList(UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE, new String[]{CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX,CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL});
			qry.setInteger(DrsCommonConstants.QRY_PARAM_OFFICEID, drsTO.getLoginOfficeId());
			qry.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			manifestDtls = qry.list();
		} catch (Exception e) {
			LOGGER.error("ManualDrsDAOImpl::getManifestDetailsForDrs :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		
		return !CGCollectionUtils.isEmpty(manifestDtls)?manifestDtls.get(0):null;
	}
	@Override
	public Integer getManifestConsignemtnCountForDrs(AbstractDeliveryTO drsTO) throws CGSystemException {
		List<ConsignmentDO> manifestedCnCount=null;
		Integer cnCount=0;
		Session session=createSession();
		try {
			Query qry = session.getNamedQuery(DrsCommonConstants.QRY_GET_CONSIGNMENT_COUNT_OF_MANIFEST_BY_MNFST_NO);
			qry.setString(DrsCommonConstants.QRY_PARAM_MANIFESTNO, drsTO.getDrsNumber());
			qry.setParameterList(UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE, new String[]{CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX,CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL});
			qry.setInteger(DrsCommonConstants.QRY_PARAM_OFFICEID, drsTO.getLoginOfficeId());
			manifestedCnCount = qry.list();
		} catch (Exception e) {
			LOGGER.error("ManualDrsDAOImpl::getManifestDetailsForDrs :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		if(!CGCollectionUtils.isEmpty(manifestedCnCount)){
			cnCount=manifestedCnCount.size();
			for(ConsignmentDO consgDO:manifestedCnCount){
				if(!CGCollectionUtils.isEmpty(consgDO.getChildCNs())){
					cnCount=cnCount-1+consgDO.getChildCNs().size();
				}
			}
		}
		return cnCount;
	}
	
	

	@Override
	public Boolean saveManualDrs(DeliveryDO deliveryDO)
			throws CGSystemException {
		DeliveryDO persistedDO=null;
		try {
					
			String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER,DrsCommonConstants.QRY_PARAM_OFFICEID};
			Object value[] = {deliveryDO.getDrsNumber(),deliveryDO.getCreatedOfficeDO().getOfficeId()};
			List <DeliveryDO>deliveryDOList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_DRS_DETAILS_BY_DRS_NUMBER,params, value);
			if(!CGCollectionUtils.isEmpty(deliveryDOList)){
				persistedDO= deliveryDOList.get(0);
				//DrsUtil.preProcessForTwoWayWrite(deliveryDO);
				persistedDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				persistedDO.setTransactionModifiedDate(DateUtil.getCurrentDate());
				persistedDO.setDtToCentral("N");
				if(!CGCollectionUtils.isEmpty(deliveryDO.getDeliveryDtlsDO())){
					persistedDO.getDeliveryDtlsDO().addAll(deliveryDO.getDeliveryDtlsDO());
				}
				deliveryDO= getHibernateTemplate().merge(persistedDO);
			}
			
		} catch (Exception e) {
			LOGGER.error("DeliveryCommonDAOImpl::savePrepareDrs :: Exception",e);
			throw new CGSystemException(e);
		}
		return Boolean.TRUE;
	}
	
}
