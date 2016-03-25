package com.ff.admin.master.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeMasterDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;

public class PincodeMasterDaoImpl extends CGBaseDAO implements PincodeMasterDao {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PincodeMasterDaoImpl.class);

	public List<StateDO> getStatesByRegionId(Integer regionId)
			throws CGSystemException {
		LOGGER.debug("PincodeMasterDaoImpl :: getStatesByRegionId :: Start --------> ::::::");

		List<StateDO> statedo;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getStateByRegionId");
			query.setInteger("regionId", regionId);
			statedo = query.list();

		} catch (Exception e) {
			LOGGER.error("Error occured in PincodeMasterDaoImpl :: getStatesByRegionId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return statedo;
	}

	@Override
	public List<CityDO> getCitysByStateId(Integer stateId)
			throws CGSystemException {
		LOGGER.debug("PincodeMasterDaoImpl :: getCitysByStateId :: Start --------> ::::::");

		List<CityDO> cityDO;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCitiesByState");
			query.setInteger("stateId", stateId);
			cityDO = query.list();

		} catch (Exception e) {
			LOGGER.error("Error occured in PincodeMasterDaoImpl :: getCitysByStateId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return cityDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDO> getAllProducts() throws CGSystemException {
		List<ProductDO> products = null;
		Session session = null;
		try {
			session = createSession();
			products = (List<ProductDO>) session
					.createCriteria(ProductDO.class).list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PincodeMasterDaoImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}
	
	@Override
	public List<CNPaperWorksDO> getAllPaperWorks() throws CGSystemException {
		List<CNPaperWorksDO> products = null;
		Session session = null;
		try {
			session = createSession();
			products = (List<CNPaperWorksDO>) session
					.createCriteria(CNPaperWorksDO.class).list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PincodeMasterDaoImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return products;
	}
	
	
	public ProductGroupServiceabilityDO getProductGroupServiceByGrupId(Integer productGrupId) throws CGSystemException {
		
		ProductGroupServiceabilityDO productGrupServcbleDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			
			criteria = session.createCriteria(ProductGroupServiceabilityDO.class, "productGrupServicbltyDO");
			criteria.add(Restrictions.eq("productGrupServicbltyDO.prodGroupId",productGrupId ));
			productGrupServcbleDO = (ProductGroupServiceabilityDO) criteria.uniqueResult();
			
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PincodeMasterDaoImpl.getProductGroupServiceByGrupId", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return productGrupServcbleDO;
	}
	
	public CNPaperWorksDO getCnPaperWorksByPaperWorkId(Integer paperWorkId) throws CGSystemException {
		CNPaperWorksDO cnPaperWorkDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			
			criteria = session.createCriteria(CNPaperWorksDO.class, "cnPaperWorkDO");
			criteria.add(Restrictions.eq("cnPaperWorkDO.cnPaperWorkId",paperWorkId ));
			cnPaperWorkDO = (CNPaperWorksDO) criteria.uniqueResult();
			
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PincodeMasterDaoImpl.getAllProducts", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return cnPaperWorkDO;
	}
	
	public boolean saveOrUpdatePincode(PincodeMasterDO pincodeDO){
		LOGGER.debug("PincodeMasterDaoImpl :: saveOrUpdatePincode :: Start --------> ::::::");

		boolean flag = Boolean.FALSE;
		try{
			
			getHibernateTemplate().merge(pincodeDO);
			flag = Boolean.TRUE;
		}
		catch(Exception e){
			LOGGER.error("PincodeMasterDaoImpl :: saveOrUpdatePincode :: ERROR --------> ::::::", e);
		}
		LOGGER.debug("PincodeMasterDaoImpl :: saveOrUpdatePincode :: END --------> ::::::");

		return flag;
	}
	
	public PincodeMasterDO searchPincodeDetls(String pincodeNO){
		
		PincodeMasterDO pincodeDO = null;
		List<PincodeMasterDO> pincodeList = null;
		try{
//			session = createSession();
//			
//			criteria = session.createCriteria(PincodeMasterDO.class, "pincodeDO");
//			criteria.add(Restrictions.eq("pincodeDO.pincode",pincodeNO ));
//			pincodeDO = (PincodeMasterDO) criteria.uniqueResult();
			
			pincodeList = getHibernateTemplate().findByNamedQueryAndNamedParam("getPincodeByPincodeNo", "pincodeNo", pincodeNO);
			
			if(!StringUtil.isNull(pincodeList)){
				return pincodeList.get(0);
			}
		
			
			
			
		}catch(Exception e){
			LOGGER.error("PincodeMasterDaoImpl :: searchPincodeDetls :: ERROR --------> ::::::", e);
		}
		
		return pincodeDO;
	}

	@Override
	public List<CityDO> getCitysByStateIdAndRegionID(Integer stateId,
			Integer regionId) throws CGSystemException {

		LOGGER.debug("PincodeMasterDaoImpl :: getCitysByStateIdAndRegionID :: Start --------> ::::::");

		List<CityDO> cityDO;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCitiesByStateAndRegion");
			query.setInteger("stateId", stateId);
			query.setInteger("regionId", regionId);
			cityDO = query.list();

		} catch (Exception e) {
			LOGGER.error("Error occured in PincodeMasterDaoImpl :: getCitysByStateIdAndRegionID()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.debug("PincodeMasterDaoImpl :: getCitysByStateIdAndRegionID :: End --------> ::::::");
		return cityDO;
	
	}

}