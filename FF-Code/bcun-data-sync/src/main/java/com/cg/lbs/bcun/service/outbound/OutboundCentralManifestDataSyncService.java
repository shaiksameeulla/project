/**
 * 
 */
package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;

import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;

/**
 * The Interface OutboundCentralManifestDataSyncService.
 *
 * @author narmdr
 */
public interface OutboundCentralManifestDataSyncService {

	/**
	 * Proceed outbound process.
	 *
	 * @param configOffice the config office
	 * @param configTo the config to
	 */
	void proceedOutboundProcess(OutboundDatasyncConfigOfficeDO configOffice,
			OutboundConfigPropertyTO configTo) throws IOException ;

}
