package com.ff.web.manifest.rthrto.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.rthrto.RthRtoManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoManifestParcelService.
 */
public interface RthRtoManifestParcelService {
	
	/**
	 * Save or update rth rto manifest parcel.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the rth rto manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public RthRtoManifestTO saveOrUpdateRthRtoManifestParcel(
			RthRtoManifestTO rthRtoManifestTO) throws CGBusinessException,
			CGSystemException;
}
