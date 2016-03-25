package com.ff.admin.tracking.manifestTracking.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;

public class ManifestTrackingDAOImpl extends CGBaseDAO implements
		ManifestTrackingDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestTrackingDAOImpl.class);

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public ManifestDO getManifestInformation1(String manifestNumber,
			String codeIn, String codeOut) throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getManifestInformation()::START");
		Session session = null;
		Criteria criteriaIn = null;
		Criteria criteriaOut = null;
		ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOs = new ArrayList<>();
		Object valuesIn[] = codeIn.split(",");
		Object valuesOut[] = codeOut.split(",");
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteriaIn = session.createCriteria(ManifestDO.class);
			if (manifestNumber != null) {
				criteriaIn.add(Restrictions.eq(UniversalTrackingConstants.MANIFEST_NUMBER, manifestNumber))
						.add(Restrictions.in("manifestProcessCode", valuesIn));
				manifestDOs = (List<ManifestDO>) criteriaIn.list();
			}

			if (manifestDOs == null || manifestDOs.isEmpty()) {
				criteriaOut = session.createCriteria(ManifestDO.class);
				criteriaOut.add(Restrictions.eq("manifestNo", manifestNumber))
						.add(Restrictions.in("manifestProcessCode", valuesOut));
				manifestDOs = (List<ManifestDO>) criteriaOut.list();
			}
			manifestDO = manifestDOs == null || manifestDOs.isEmpty() ? null
					: manifestDOs.get(0);
		}

		catch (Exception e) {
			LOGGER.error("ManifestTrackingDAOImpl::getManifestInformation()::ERROR :: "
					,e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("ManifestTrackingDAOImpl::getManifestInformation()::END");
		return manifestDO;
	}

	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public ManifestDO getDetailsFromOriginManifest(String manifestNumber) throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getDetailsFromOriginManifest()::START");
		ManifestDO manifestDO = null;
		Session session = null;
		try {
			session = createSession();
			session.enableFetchProfile("manifest-dox");
			Criteria criteria = session.createCriteria(ManifestDO.class)
					.add(Restrictions.eq("manifestNo", manifestNumber))
					.add(Restrictions.isNotNull("noOfElements"))
					.addOrder(Order.asc("manifestDate"))
					.setMaxResults(1);
			List<ManifestDO> manifestDOList = criteria.list();
			if(!StringUtil.isEmptyList(manifestDOList)){
				manifestDO = manifestDOList.get(0);
			 	Hibernate.initialize(manifestDO.getConsignments());
				Hibernate.initialize(manifestDO.getComails());
			}
		}catch (Exception e) {
			LOGGER.error("ManifestTrackingDAOImpl::getDetailsFromOriginManifest()::ERROR :: "
					,e);
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("ManifestTrackingDAOImpl::getDetailsFromOriginManifest()::END");
		return manifestDO;
	}

	@Override
	public List<ManifestDO> getEmbeddedInManifestInfo(Integer manifestID,
			String fetchProfile) throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getEmbeddedInManifestInfo()::START");
		List<ManifestDO> embeddedManifestDOs = null;
		Session session = null;
		try {
//			DetachedCriteria manifestID=null;
			session = createSession();
			session.enableFetchProfile(fetchProfile);
			
//			manifestID = DetachedCriteria.forClass(ManifestDO.class, "manifestDO");
//			manifestID.add(Restrictions.eq("manifestDO.manifestNo",manifestNo));
//			manifestID.setProjection(Projections.property("manifestDO.manifestId"));
			Criteria criteria = session.createCriteria(ManifestDO.class)
					.add(Restrictions.eq("manifestEmbeddedIn",manifestID));
			
			embeddedManifestDOs = criteria.list();
			if(!StringUtil.isEmptyList(embeddedManifestDOs)){
				for (ManifestDO manifestDO : embeddedManifestDOs) {
					Hibernate.initialize(manifestDO.getConsignments());
					Hibernate.initialize(manifestDO.getComails());
					Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestTrackingDAOImpl :: getEmbeddedInManifestInfo() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ManifestTrackingDAOImpl::getEmbeddedInManifestInfo()::END");
		return embeddedManifestDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessMapDO> getDetailedTrackingInformation(String manifestNum)
			throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getDetailedTrackingInformation()::START");
		List<ProcessMapDO> processMapDOs = null;
		processMapDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				AdminSpringConstants.GET_MANIFEST_PROCESS_DETAILS,
				AdminSpringConstants.MANIFEST_NO, manifestNum);
		LOGGER.trace("ManifestTrackingDAOImpl::getDetailedTrackingInformation()::END");
		return processMapDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessDO> getProcessDetails() throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getProcessDetails()::START");
		List<ProcessDO> processDOs = null;
		processDOs = getHibernateTemplate().findByNamedQuery(
				AdminSpringConstants.GET_PROCESS_DETAILS);
		LOGGER.trace("ManifestTrackingDAOImpl::getProcessDetails()::END");
		return processDOs;
	}

	@SuppressWarnings("unchecked")
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException {
		LOGGER.trace("ManifestTrackingDAOImpl::getTypeName()::START");
		List<StockStandardTypeDO> stockTypeDOList = null;
		try {
			stockTypeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							AdminSpringConstants.QRY_GET_MANIFEST_Type, "Type",
							AdminSpringConstants.Type);
		} catch (Exception e) {
			LOGGER.error("ManifestTrackingDAOImpl :: getTypeName():: ERROR ::::"
					,e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ManifestTrackingDAOImpl::getTypeName()::END");
		return stockTypeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestInformation(String manifestNumber,
			 String manifestDirection, String fetchProfile) throws CGSystemException{
		LOGGER.trace("ManifestTrackingDAOImpl::getManifestInformation()::START");
		ManifestDO manifestDO = null;
		Session session = null;
		try {
			session = createSession();
			session.enableFetchProfile(fetchProfile);			
			
			Query query =session.getNamedQuery("trackManifestDetails");
			query.setString("manifestNo", manifestNumber);
			query.setString("manifestDirection", manifestDirection);
			List<ManifestDO> manifestDOList = query.list();
			if(!StringUtil.isEmptyList(manifestDOList)){
				if(StringUtils.equalsIgnoreCase(manifestDirection, CommonConstants.DIRECTION_OUT)){
					//origin Manifest
					manifestDO = manifestDOList.get(0);
				}else{
					//Latest Manifest
					manifestDO = manifestDOList.get(manifestDOList.size()-1);
				}
				Hibernate.initialize(manifestDO.getConsignments());
				Hibernate.initialize(manifestDO.getComails());
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ManifestTrackingDAOImpl :: getManifestInformation() ::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ManifestTrackingDAOImpl::getManifestInformation()::END");
		return manifestDO;
	}
}
