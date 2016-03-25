package com.ff.admin.master.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.business.VendorOfficeMapDO;
import com.ff.domain.organization.OfficeDO;

public class VendorMappingDAOImpl extends CGBaseDAO implements VendorMappingDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VendorMappingDAOImpl.class);

	public LoadMovementVendorDO getVendorDetails(String vendorCode)
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: getVendorDetails() :: start --------> ::::::");
		Session session = null;
		LoadMovementVendorDO vendorDO = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			List<LoadMovementVendorDO> vendorDOList = (List<LoadMovementVendorDO>) session
					.createCriteria(LoadMovementVendorDO.class)
					.add(Restrictions.eq("vendorCode", vendorCode)).list();

			if (vendorDOList != null && vendorDOList.size() > 0) {
				vendorDO = vendorDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::getVendorDetails() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("VendorMappingDAOImpl :: getVendorDetails() :: End --------> ::::::");
		return vendorDO;
	}

	public boolean saveOrUpdateVendor(LoadMovementVendorDO vendorDO)
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: saveOrUpdateVendor() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try {

			getHibernateTemplate().saveOrUpdate(vendorDO);
			isSaved = Boolean.TRUE;
		}

		catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::saveOrUpdateVendor() :: "
					+ e);
			throw new CGSystemException(e);
		}

		LOGGER.debug("VendorMappingDAOImpl :: saveOrUpdateVendor() :: End --------> ::::::");

		return isSaved;
	}

	public boolean saveOrUpdateVendorOffice(
			List<VendorOfficeMapDO> vendorOfficeDOList)
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: saveOrUpdateVendorOffice() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		try {

			getHibernateTemplate().saveOrUpdateAll(vendorOfficeDOList);
			isSaved = Boolean.TRUE;
		}

		catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::saveOrUpdateVendorOffice() :: "
					+ e);
			throw new CGSystemException(e);
		}

		LOGGER.debug("VendorMappingDAOImpl :: saveOrUpdateVendorOffice() :: End --------> ::::::");

		return isSaved;
	}

	public List<VendorOfficeMapDO> alreadtExistVedorOffice(
			List<VendorOfficeMapDO> vendorOfficeDOList)
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: alreadtExistVedorOffice() :: Start --------> ::::::");
		Session session = null;
		List<VendorOfficeMapDO> vendorOfficeDOMapList = new ArrayList<VendorOfficeMapDO>();
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			for (VendorOfficeMapDO vendorOfficeMapDO : vendorOfficeDOList) {
				@SuppressWarnings("unchecked")
				List<VendorOfficeMapDO> vendorDOList = (List<VendorOfficeMapDO>) session
						.createCriteria(VendorOfficeMapDO.class)
						.add(Restrictions.eq("vendorOfficeRegionId",
								vendorOfficeMapDO.getVendorOfficeRegionId()))
						.add(Restrictions.eq("officeDO.officeId",
								vendorOfficeMapDO.getOfficeDO().getOfficeId()))
						.list();

				if (vendorDOList != null && vendorDOList.size() > 0) {
					/*
					 * vendorOfficeDO = vendorDOList.get(0);
					 * vendorOfficeDOMapList.add(vendorOfficeDO);
					 */
				} else {
					vendorOfficeDOMapList.add(vendorOfficeMapDO);
				}

			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::alreadtExistVedorOffice() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("VendorMappingDAOImpl :: alreadtExistVedorOffice() :: End --------> ::::::");
		return vendorOfficeDOMapList;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllVendorsList()
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl::getAllVendorsList()::start --------> ::::::");
		List<String> vendorDOList = null;
		try {
			
			vendorDOList = getHibernateTemplate().findByNamedQuery("getAllVendorsForOfficeMapping");

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::getAllVendorsList() :: "
					+ e);
			throw new CGSystemException(e);
		} 
		LOGGER.debug("VendorMappingDAOImpl::getAllVendorsList()::End --------> ::::::");
		return vendorDOList;
	}
	
	/*public List<LoadMovementVendorDO> getAllVendorsList1()
			throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: getVendorDetails() :: start --------> ::::::");
		Session session = null;
		List<LoadMovementVendorDO> vendorDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			Criteria cr = session.createCriteria(LoadMovementVendorDO.class,
					"collectionDetails");
			vendorDOList = cr.list();

			
			 * Criteria criteria = session.createCriteria(
			 * LoadMovementVendorDO.class) .setProjection( Projections
			 * .projectionList() .add(Projections.property("vendorCode"),
			 * "vendorCode") .add(Projections.property("firstname"),
			 * "firstname") .add(Projections.property("lastName"), "lastName")
			 * .add(Projections.property("businessName"), "businessName"));
			 * vendorDOList = criteria.list();
			 

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::getVendorDetails() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("VendorMappingDAOImpl :: getVendorDetails() :: End --------> ::::::");
		return vendorDOList;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getSelectedOffices(Integer regionId, Integer cityId,
			Integer vendorId) throws CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: getSelectedOffices() :: start --------> ::::::");
		List<OfficeDO> selectedOfficeList = null;
		String[] paramNames = { "vendorId", "regionId", "cityId" };
		Object[] values = { vendorId, regionId, cityId };
		String queryName = "getSelectedOfficesForVendor";
		selectedOfficeList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(queryName, paramNames, values);
		LOGGER.debug("VendorMappingDAOImpl :: getSelectedOffices() :: End --------> ::::::");
		return selectedOfficeList;
	}

	@Override
	public void deleteVendorOfficeMap(Integer regionMappedId,
			List<Integer> branchIds) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("VendorMappingDAOImpl :: deleteVendorOfficeMap() :: start --------> ::::::");

		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			if (!branchIds.isEmpty()) {
				Query q = session
						.createQuery("DELETE FROM com.ff.domain.business.VendorOfficeMapDO ven WHERE ven.vendorOfficeRegionId=:regId AND ven.officeDO.officeId IN :idList");
				q.setInteger("regId", regionMappedId);
				q.setParameterList("idList", branchIds);
				int status = q.executeUpdate();
				LOGGER.debug("Status===" + status);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::VendorMappingDAOImpl::deleteVendorOfficeMap() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("VendorMappingDAOImpl :: deleteVendorOfficeMap() :: End --------> ::::::");
	}

}
