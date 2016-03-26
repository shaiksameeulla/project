/**
 * 
 */
package com.ff.web.drs.blkpending.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.web.drs.common.constants.DrsCommonConstants;

/**
 * @author mohammes
 *
 */
public class BulkCnPendingDrsDAOImpl extends CGBaseDAO implements BulkCnPendingDrsDAO{
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkCnPendingDrsDAOImpl.class);
	
	
	@Override
	public ManifestDO getManifestDetailsByManifestNumber(AbstractDeliveryTO drsTO) throws CGSystemException{
		ManifestDO manifestDO=null;
		
		
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
				 updatingProcess.add( Restrictions.in("processDo.processCode",new Object[]{CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX,CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL}));  
				 updatingProcess.setProjection(Projections.property("processDo.processId"));
					//for Sub Query ****END****
					//for Main Query Join Condition
				 manifestCriteria.add(Property.forName("manifest.updatingProcess.processId").in(updatingProcess));
				 manifestCriteria.add(Restrictions.eq("manifest.operatingOffice", drsTO.getLoginOfficeId()));
				
				manifestDtls = manifestCriteria.list();
				if(!StringUtil.isEmptyList(manifestDtls)){
				 	Hibernate.initialize(manifestDtls.get(0).getConsignments());
				}
			} catch (Exception e) {
				LOGGER.error("ManualDrsDAOImpl::getManifestDetailsByManifestNumber :: Exception",e);
			throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}
			
			manifestDO= !CGCollectionUtils.isEmpty(manifestDtls)?manifestDtls.get(0):null;
		
		
		
		return manifestDO;
	}
	
	@Override
	public Date getManifestDateByManifestNumber(String manifestNumber) throws CGSystemException {
		 List<Date> manifestDateList;
		String params[]={DrsCommonConstants.QRY_PARAM_MANIFESTNO};
		Object value[] = {manifestNumber};
		manifestDateList=getHibernateTemplate().findByNamedQueryAndNamedParam("getManifetedDateForBulkDrs",params, value);
		return !StringUtil.isEmptyList(manifestDateList)?manifestDateList.get(0):null;
	}

}
