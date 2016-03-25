/**
 * 
 */
package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;

import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;
import com.ff.domain.bcun.OutboundDatasyncConfigOfficeDO;

/**
 * The Interface OutboundCentralConsignmentDataSyncService.
 *
 * @author narmdr
 */
public interface OutboundCentralConsignmentDataSyncService {

	/**
	 * Proceed outbound process.
	 *
	 * @param configOffice the config office
	 * @param configTo the config to
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void proceedOutboundProcess(OutboundDatasyncConfigOfficeDO configOffice,
			OutboundConfigPropertyTO configTo) throws IOException ;

}
