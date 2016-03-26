package com.ff.web.manifest.inmanifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InMasterBagManifestService.
 *
 * @author nkattung
 */
public interface InMasterBagManifestService {

	/**
	 * save the MBPL details.
	 *
	 * @param inMasterBagManifestTO the in master bag manifest to
	 * @return the in master bag manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InMasterBagManifestTO saveOrUpdateInMBPL(InMasterBagManifestTO inMasterBagManifestTO) throws CGBusinessException,CGSystemException;

	/**
	 * search the details for given MBPL number.
	 *
	 * @param manifestTO the manifest to
	 * @return the in master bag manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InMasterBagManifestTO searchMBPLManifest(InMasterBagManifestTO manifestTO) throws CGBusinessException,CGSystemException;

	InMasterBagManifestTO getInfoForPrint(List<InMasterBagManifestDetailsTO> mbplManifestDetailsTOs )throws CGBusinessException,
	CGSystemException ;
}
