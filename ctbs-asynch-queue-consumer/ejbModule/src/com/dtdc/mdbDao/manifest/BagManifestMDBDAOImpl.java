/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import src.com.dtdc.constants.ManifestConstant;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.ManifestDO;

// TODO: Auto-generated Javadoc
/**
 * The Class BagManifestMDBDAOImpl.
 *
 * @author nisahoo
 */
public class BagManifestMDBDAOImpl extends CGBaseDAO implements BagManifestMDBDAO {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BagManifestMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.BagManifestMDBDAO#getBagManifestDetailsByCompositeID(String, String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getBagManifestDetailsByCompositeID(String manifestNo,
			String cnNo, String loginOfficeCode) throws CGSystemException {
		LOGGER.debug("BagManifestMDBDAOImpl: getBagManifestDetailsByCompositeID(): START");
		ManifestDO manifestDO = null;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try{
			String query = "getBagManifestDetailsByCompositeId";
			String[] params = {
					"manifestNo",
					"cnNo",
					"manifestType",
					"loginofficeCode"
					};
			Object[] values = {
					manifestNo,
					cnNo,
					ManifestConstant.MANIFEST_TYPE_AGAINST_INCOMING,
					loginOfficeCode
					};
				
			List<ManifestDO> manifestList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(query, params, values);
			
			if (manifestList != null && !manifestList.isEmpty()) {
				manifestDO = manifestList.get(0);
			}
			
		}catch (Exception ex) {
			logger.error("BagManifestMDBDAOImpl::getBagManifestDetailsByCompositeID::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException(ex);
			
		}finally{
			hibernateTemplate.flush();
			hibernateTemplate.clear();
		}
		LOGGER.debug("BagManifestDAOImpl: getBagManifestDetailsByCompositeID(): END");
		return manifestDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.BagManifestMDBDAO#saveBagManifestData(List)
	 */
	@Override
	public void saveBagManifestData(List<ManifestDO> manifestDOList){
		LOGGER.debug("BagManifestMDBDAOImpl: saveBagManifestData():START::manifestDOList " + manifestDOList);
		int serialNo = 1;
		for (ManifestDO manifestDO : manifestDOList) {
			manifestDO.setLineItemSequenceNo(serialNo);
			manifestDO.setDiFlag("N");
			getHibernateTemplate().saveOrUpdate(manifestDO);
			serialNo++;
		}
		LOGGER.debug("BagManifestMDBDAOImpl: saveBagManifestData():END ");
	}


	
}
