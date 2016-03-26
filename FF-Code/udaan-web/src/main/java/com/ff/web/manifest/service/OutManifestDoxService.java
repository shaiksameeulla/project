package com.ff.web.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestDoxDetailsTO;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.manifest.OutManifestValidate;

// TODO: Auto-generated Javadoc
/**
 * The Interface OutManifestDoxService.
 */
public interface OutManifestDoxService {

	/**
	 * Gets the consignment dtls.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @param cnValidateTO
	 *            the consignment validation TO
	 * @return the consignment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestDoxDetailsTO getConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO,
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Search manifest dtls.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the out manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestDoxTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update out manifest dox.
	 * 
	 * @param allConsignments
	 * 
	 * @param outmanifestDoxTO
	 *            the outmanifest dox to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String saveOrUpdateOutManifestDox(OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the in manifestd consignment dtls.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the in manifestd consignment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException;

	List<BookingConsignmentDO> readAllConsignments(
			OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException;


}
