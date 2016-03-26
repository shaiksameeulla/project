package com.ff.web.manifest.rthrto.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.rthrto.RthRtoManifestDoxTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoManifestDoxService.
 */
public interface RthRtoManifestDoxService {

	/**
	 * To save or update RTH/RTO manifest Dox details.
	 *
	 * @param rthRtoManifestDoxTO the rth rto manifest dox to
	 * @return rthRtoManifestDoxTO
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	RthRtoManifestDoxTO saveOrUpdateRthRtoManifestDox(RthRtoManifestDoxTO 
			rthRtoManifestDoxTO) throws CGBusinessException, CGSystemException;
	
}
