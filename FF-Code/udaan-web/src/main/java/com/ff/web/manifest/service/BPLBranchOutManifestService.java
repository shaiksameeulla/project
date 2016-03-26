package com.ff.web.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.manifest.BPLBranchOutManifestDetailsTO;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.BplBranchOutManifestTO;
import com.ff.manifest.ManifestInputs;

/**
 * @author nihsingh
 *
 */
public interface BPLBranchOutManifestService  {

	/**
	 * @Desc gets the manifest details
	 * @param manifestInputsTO
	 * @return BplBranchOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BplBranchOutManifestTO getManifestDtls(ManifestInputs manifestInputsTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc saves or updates the manifest
	 * @param bplBranchOutManifestTO
	 * @return BplBranchOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveOrUpdateBplBranchOutManifest(
			BplBranchOutManifestTO bplBranchOutManifestTO) throws CGBusinessException,
			CGSystemException;

	
	/**
	 * @Desc search manifest details
	 * @param manifestTO
	 * @return BplBranchOutManifestTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BplBranchOutManifestTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @param bplBranchOutManifestTOs
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BplBranchOutManifestTO getTotalConsignmentCount(
			List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestTOs)
			throws CGBusinessException, CGSystemException;
}
