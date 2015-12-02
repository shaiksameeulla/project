/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.common.OpsOfficeTO;
import com.dtdc.to.manifest.AgentManifestIntlTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentManifestIntlMDBService.
 *
 * @author rchalich
 */
public interface AgentManifestIntlMDBService {

	/**
	 * Gets the gateway offices by country.
	 *
	 * @param originOfficeId the origin office id
	 * @param opsOffType the ops off type
	 * @return the gateway offices by country
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<OpsOfficeTO> getGatewayOfficesByCountry(Integer originOfficeId,
			String opsOffType) throws CGBusinessException, CGSystemException;

	// For Saving to local Server
	/**
	 * Save agent manifest.
	 *
	 * @param agentManifestTO the agent manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String saveAgentManifest(AgentManifestIntlTO agentManifestTO)
			throws CGSystemException, CGBusinessException;

	// For Booking details
	/**
	 * Gets the booking details by cn num.
	 *
	 * @param consgNumber the consg number
	 * @param modeId the mode id
	 * @param manifestType the manifest type
	 * @return the booking details by cn num
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String getBookingDetailsByCnNum(String consgNumber, Integer modeId,
			String manifestType) throws CGSystemException, CGBusinessException;

	// For Printing Manifest
	/**
	 * Find by manifest number.
	 *
	 * @param manifestNum the manifest num
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<AgentManifestIntlTO> findByManifestNumber(String manifestNum)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save or update agent mnfst intl db sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateAgentMnfstIntlDBSync(
			List<AgentManifestIntlTO> manifestTOs) throws CGSystemException;

	/**
	 * Save or update agent mnfst intl db sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateAgentMnfstIntlDBSync(CGBaseTO manifestTOs)
			throws CGSystemException;

}
