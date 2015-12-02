/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;

// TODO: Auto-generated Javadoc
/**
 * The Class PacketManifestMDBDAOImpl.
 *
 * @author nisahoo
 */
public class PacketManifestMDBDAOImpl extends CGBaseDAO implements
		PacketManifestMDBDAO {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory.getLogger(PacketManifestMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PacketManifestMDBDAO#getPacketManifestByCompositeID(String, String, String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getPacketManifestByCompositeID(
			String manifestNumber, String cnNo, String manifestType, String loginOfficeCode)
			throws CGSystemException {
		LOGGER.debug("PacketManifestMDBDAOImpl : getIncomingPktManifestByCompositeID() : START");
		
		ManifestDO manifestDO = null;
		try {
			String query = "getPacketManifestDetailsByCompositeId";
			String[] params = { 
					"manifestNo", 
					"cnNo",
					"manifestType",
					"loginofficeCode",
					};
			
			Object[] values = { 
					manifestNumber, 
					cnNo,
					manifestType,
					loginOfficeCode,
					};
			
			List<ManifestDO> pktManfisetList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);

			if (pktManfisetList != null && !pktManfisetList.isEmpty()) {
				manifestDO = pktManfisetList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("PacketManifestMDBDAOImpl : getIncomingPktManifestByCompositeID():"+e.getStackTrace());
			throw new CGSystemException(e);
		}
		LOGGER.debug("PacketManifestMDBDAOImpl : getIncomingPktManifestByCompositeID() : END");
		return manifestDO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PacketManifestMDBDAO#saveIncomingPktManifest(List)
	 */
	@Override
	public boolean saveIncomingPktManifest(List<ManifestDO> manifestDOList)throws CGSystemException {
		LOGGER.debug("PacketManifestMDBDAOImpl: saveIncomingPktManifest(): START");
		boolean saveSuccess = true;
		
		int serialNo = 1;
		for (ManifestDO manifestDo : manifestDOList) {
			manifestDo.setLineItemSequenceNo(serialNo);
			try {
				manifestDo.setDiFlag("N");
				getHibernateTemplate().saveOrUpdate(manifestDo);
				getHibernateTemplate().flush();
				serialNo++;				
			} catch (Exception e) {
				logger.error("PacketManifestMDBDAOImpl::saveIncomingPktManifest::Exception occured:"
						+e.getMessage());
			saveSuccess = false;
			//throw new CGSystemException(e);
		}
	}	
		LOGGER.debug("PacketManifestMDBDAOImpl: saveIncomingPktManifest(): END");
		return saveSuccess;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.PacketManifestMDBDAO#saveMiscExp(List)
	 */
	@Override
	public void saveMiscExp(List<MiscExpenseDO> miscExpenseDOList)throws CGSystemException {
		LOGGER.debug("PacketManifestMDBDAOImpl: saveMiscExp(): START");
		
		getHibernateTemplate().saveOrUpdateAll(miscExpenseDOList);
		
		LOGGER.debug("PacketManifestMDBDAOImpl: saveMiscExp(): END");
	}

}
