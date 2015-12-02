/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.manifest.PacketManifestDetailTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PacketManifestMDBService.
 *
 * @author nisahoo
 */
public interface PacketManifestMDBService {

	/**
	 * Save incoming pkt manifest.
	 *
	 * @param cgBaseTO the cg base to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveIncomingPktManifest(CGBaseTO cgBaseTO)
	throws CGBusinessException;
	
	/**
	 * Save incoming pkt manifest.
	 *
	 * @param pktmanifestDtlTO the pktmanifest dtl to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveIncomingPktManifest(
			PacketManifestDetailTO pktmanifestDtlTO) throws CGBusinessException;
}
