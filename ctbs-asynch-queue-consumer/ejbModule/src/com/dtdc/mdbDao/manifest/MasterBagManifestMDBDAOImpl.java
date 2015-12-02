/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterBagManifestMDBDAOImpl.
 *
 * @author vsulibha
 */
public class MasterBagManifestMDBDAOImpl extends CGBaseDAO implements
		MasterBagManifestMDBDAO {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(MasterBagManifestMDBDAOImpl.class);
	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.MasterBagManifestMDBDAO#insertMasterBagManifestData(List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void insertMasterBagManifestData(List<ManifestDO> manifestDOList) {
		
		
		LOGGER.info("MasterBagManifestMDBDAOImpl: insertBagManifestData():START ");

		for (ManifestDO manifestDO : manifestDOList) {
			manifestDO.setDiFlag("N");
			getHibernateTemplate().saveOrUpdate(manifestDO);
		}
		LOGGER.info("MasterBagManifestMDBDAOImpl: insertMasterBagManifestData():END ");

	}
	
	
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.MasterBagManifestMDBDAO#getManifestType(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ManifestTypeDO getManifestType(String manfstCode) throws CGBusinessException {
		
		List<ManifestTypeDO>   manifestTypeList = null;
		try 
		{
			getHibernateTemplate();
			
			manifestTypeList= getHibernateTemplate().findByNamedQueryAndNamedParam("getManifestTypes", "mnfstCode", manfstCode);
		} catch (Exception ex) {
			logger.error("MasterBagManifestMDBDAOImpl::getManifestType::Exception occured:"
					+ex.getMessage());
		}
		
		return manifestTypeList.get(0);
	}		
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.MasterBagManifestMDBDAO#checkWhetherRecordExists(String, String, String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ManifestDO> checkWhetherRecordExists(String mnfstNo, String consgNo, String officeCode) throws CGBusinessException {
		
		List<ManifestDO>   manifestTypeList = null;
		try 
		{
			getHibernateTemplate();
			
			String[] params = { "manifestNumber", "consgNumber", "officeCode"};
			
			Object[] values = {  mnfstNo, consgNo, officeCode};
			
			manifestTypeList= getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getIncomingMasterBagDataForMnfstNoAndCode", params, values);
		} catch (Exception ex) {
			logger.error("MasterBagManifestMDBDAOImpl::checkWhetherRecordExists::Exception occured:"
					+ex.getMessage());
		}
		
		return manifestTypeList;
	}	

}
