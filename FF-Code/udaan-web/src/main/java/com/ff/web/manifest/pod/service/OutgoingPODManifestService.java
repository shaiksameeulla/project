/**
 * 
 */
package com.ff.web.manifest.pod.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.pod.PODManifestTO;

/**
 * @author nkattung
 * 
 */
public interface OutgoingPODManifestService {
	/**
	 * To Save or update POD manifest
	 * @param podManifestTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public String saveOrUpdatePODManifest(PODManifestTO podManifestTO)
			throws CGBusinessException, CGSystemException;
}
