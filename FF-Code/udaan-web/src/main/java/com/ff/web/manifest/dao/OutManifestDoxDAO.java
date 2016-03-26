package com.ff.web.manifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;


// TODO: Auto-generated Javadoc
/**
 * The Interface OutManifestDoxDAO.
 */
public interface OutManifestDoxDAO {

	/**
	 * Save or update manifest.
	 *
	 * @param manifestDtls the manifest dtls
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> saveOrUpdateManifest(List<ManifestDO> manifestDtls)
			throws CGSystemException;
	
	/**
	 * Save or update manifest process.
	 *
	 * @param manifestProcessDOs the manifest process d os
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException;
	
	public ManifestDO saveOrUpdateOutManifestDox(ManifestDO manifestDO)
			throws CGSystemException;
	
	/*public List<ManifestProcessDO> saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException;*/
}
