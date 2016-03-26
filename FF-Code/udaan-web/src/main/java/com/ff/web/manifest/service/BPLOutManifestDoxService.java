package com.ff.web.manifest.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.routeserviced.TransshipmentRouteTO;

/**
 * The Interface BPLOutManifestDoxService.
 */
public interface BPLOutManifestDoxService {

	
	/**
	 * Save or update bpl out manifest dox.
	 * 
	 * @param bplOutManifestDoxTO
	 *            the bpl out manifest dox to
	 * @return the bPL out manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean saveOrUpdateOutManifestBPL(
			final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest dtls.
	 * 
	 * @param manifestInputsTO
	 *            the manifest inputs to
	 * @return the manifest dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	BPLOutManifestDoxTO getManifestDtls(final ManifestInputs manifestInputsTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Search manifest dtls for bpl.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the bPL out manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	BPLOutManifestDoxTO searchManifestDtlsForBPL(final ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the route servicibility.
	 * 
	 * @param transshipmentRouteTO
	 *            the transshipment route to
	 * @return the route servicibility
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean getRouteServicibility(
			final TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param originCityId
	 * @param destCityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Integer getRouteIdByOriginCityIdAndDestCityId(final Integer originCityId,
			final Integer destCityId) throws CGBusinessException,
			CGSystemException;

}
