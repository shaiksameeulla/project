package com.ff.web.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyBPLDetailsTO;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;

public interface ThirdPartyBPLService {

	/**
	 * This service is used to get the consignment details
	 * 
	 * @param cnValidateTO
	 * @return ThirdPartyBPLDetailsTO(all parcel related columns of a
	 *         consignment)
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ThirdPartyBPLDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * This service is used to get the manifest details via manifest common dao
	 * 
	 * @param manifestNo
	 * @return ThirdPartyBPLDetailsTO(manifest id,weight,pincode,embeddedin
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ThirdPartyBPLDetailsTO getThirdPartyManifestDtls(
			ManifestInputs manifestInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * This service is used when user wants to save a manifest or close a
	 * manifest
	 * 
	 * @param logged
	 *            in office details,manifest header details, list of embedded
	 *            consignments and manifest with their
	 *            details{List<thirdpartydetailsTO>}
	 * @return ThirdPartyBPLDetailsTO(manifest id,weight,pincode,embeddedin)
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * 
	 */
	public String saveOrUpdateOutManifestTPBP(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * This service is used to get the manifest details of an already created
	 * manifest
	 * 
	 * @param manifestNo
	 *            ,logged in office details,manifestType(O),
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * 
	 */
	public ThirdPartyBPLOutManifestTO searchManifestDtls(
			ManifestInputs manifestTO) throws CGBusinessException,
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
	public ThirdPartyBPLDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException;

	public ThirdPartyBPLOutManifestTO getTotalConsignmentCount(
			List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * To checks if is consignment exist in DRS.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author hkansagr
	 */
	Boolean isConsignmentExistInDRS(String consignment)
			throws CGBusinessException, CGSystemException;

}
