/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PacketManifestMDBDAO.
 *
 * @author nisahoo
 */
public interface PacketManifestMDBDAO {

	/**
	 * Save incoming pkt manifest.
	 *
	 * @param manifestDOList the manifest do list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveIncomingPktManifest(List<ManifestDO> manifestDOList)
			throws CGSystemException;

	/**
	 * Gets the packet manifest by composite id.
	 *
	 * @param manifestNumber the manifest number
	 * @param cnNo the cn no
	 * @param manifestType the manifest type
	 * @param loginOfficeCode the login office code
	 * @return the packet manifest by composite id
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO getPacketManifestByCompositeID(
			String manifestNumber, String cnNo, String manifestType, String loginOfficeCode)
			throws CGSystemException;

	/**
	 * Save misc exp.
	 *
	 * @param miscExpenseDOList the misc expense do list
	 * @throws CGSystemException the cG system exception
	 */
	public void saveMiscExp(List<MiscExpenseDO> miscExpenseDOList) throws CGSystemException;

}
