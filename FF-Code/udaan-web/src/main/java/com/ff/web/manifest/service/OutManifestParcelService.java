package com.ff.web.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestParcelDetailsTO;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;

/**
 * The Interface OutManifestParcelService.
 */
public interface OutManifestParcelService {

	/**
	 * Gets the consignment dtls.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the consignment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestParcelDetailsTO getConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO,OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the paper works.
	 * 
	 * @param paperWorkValidationTO
	 *            the paper work validation to
	 * @return the paper works
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update out manifest parcel.
	 * 
	 * @param outmanifestParcelTO
	 *            the outmanifest parcel to
	 * @return the string
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public String saveOrUpdateOutManifestParcel(
			OutManifestParcelTO outmanifestParcelTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Search manifest dtls.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the out manifest parcel to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestParcelTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;

	public List<InsuredByTO> getInsuredByDtls() throws CGBusinessException,
			CGSystemException;
	
	public OutManifestParcelDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException, CGSystemException;

}
