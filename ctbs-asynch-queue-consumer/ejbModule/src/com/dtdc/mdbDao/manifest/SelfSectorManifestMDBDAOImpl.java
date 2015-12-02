/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfSectorManifestMDBDAOImpl.
 *
 * @author nisahoo
 */
public class SelfSectorManifestMDBDAOImpl extends CGBaseDAO implements SelfSectorManifestMDBDAO {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SelfSectorManifestMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.SelfSectorManifestMDBDAO#saveOrUpdateSelfSectorManifest(ManifestDO)
	 */
	@Override
	public void saveOrUpdateSelfSectorManifest(ManifestDO manifestDO) {
		LOGGER.info("SelfSectorManifestMDBDAOImpl: saveSelfSectorManifest(): START");

		getHibernateTemplate().saveOrUpdate(manifestDO);

		LOGGER.info("SelfSectorManifestMDBDAOImpl: saveSelfSectorManifest(): END");
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.SelfSectorManifestMDBDAO#getManifestTypeByCode(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestTypeDO getManifestTypeByCode(String manifestCode) {
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestTypeByCode(): START");
		ManifestTypeDO manifestTypeDo = null;
		String query = "getManifestType";
		String param = "mnfstCode";
		Object value = manifestCode;
		
		List<ManifestTypeDO> manifestTypeList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(query, param, value);
		
		if (manifestTypeList != null && manifestTypeList.size() > 0) {
			manifestTypeDo = manifestTypeList.get(0);
		}
		
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestTypeByCode(): END");
		return manifestTypeDo;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.SelfSectorManifestMDBDAO#getManifestDetailsByCompositeID(String, String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsByCompositeID(String manifestNo, String cnNo, String manifestType) {
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestDetailsByCompositeID(): START");
		ManifestDO manifestDO = null;
		
		String query = "getManifestDetailsByCompositeId";
		String[] params = {"manifestNo","cnNo","manifestTypeCode"};
		Object[] values = {manifestNo,cnNo,manifestType};
			
		List<ManifestDO> manifestList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);
		
		if (manifestList != null && manifestList.size() > 0) {
			manifestDO = manifestList.get(0);
		}
		
		LOGGER.info("SelfSectorManifestMDBDAOImpl: getManifestDetailsByCompositeID(): END");
		return manifestDO;
	}

}
