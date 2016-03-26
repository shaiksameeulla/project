package com.ff.web.manifest.service;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyOutManifestDoxDetailsTO;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;

/**
 * @author cbhure
 *
 */

public interface ThirdPartyManifestDoxService {
 
	/**
	 * 
	 *  To fetch the details of consignment at grid
	 *  
	 * @param manifestFactoryTO will be passed with the following details filled in it
	 * <ul>
	 * 	<li>	login Office Id 
	 * 	<li>	consignment no
	 * 	<li> 	manifest type 
	 * 	<li>	consignment type 
	 * </ul>
	 * 
	 * @return thirdPartyOutManifestDoxTO
	 * @throws CGBusinessException - if any violation of business rule as consg is manifested already,
	 * 									pincode is not serviced by destination,consg not booked 
	 * 
	 * @throws CGSystemException - if no connection found with server
	 */
	
	public ThirdPartyOutManifestDoxDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * 
	 * To save or Update Manifest
	 * 
	 * @param thirdPartyOutManifestDoxTO will be passed with the following details filled in it
	 * 
	 *<ul>
	 * 	<li>	consignmentTypeTO 
	 * 	<li>	manifest type 
	 * 	<li>	ManifestNo 
	 * 	<li> 	Weights 
	 * 	<li>	Pincodes 
	 *  <li>	PincodeIds 
	 *  <li>	login Office Id 
	 *  <li>	login office name
	 *  <li>	Current date 
	 *  <li>	Third Party Type
	 *  <li>	Third Party Name 
	 *  <li>	Load No 
	 * </ul>
	 * 
	 * @return trasnStatus
	 * @throws CGSystemException - if no connection found with server
	 */
	
	public String saveOrUpdateOutManifestTPDX(ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO)
			throws CGSystemException;

	/**
	 * 
	 * To find the details by Manifest No
	 * 
	 * @param manifestTO will be passed with the following details filled in it
	 * <ul>
	 * 	<li>	Login Office Id
	 * 	<li>	Manifest Number
	 * 	<li>	Manifest Process Code
	 * 	<li>	Doc Type
	 * 	<li> 	Manifest Direction 
	 * 	<li> 	manifest type 
	 * </ul>
	 * @return thirdPartyOutManifestDoxTO
	 * @throws CGBusinessException - if any violation of business rule while converting Manifestdo,Manifest ProcessDO to thirdPartyOutManifestDoxTo using common converters 
	 * @throws CGSystemException - if no connection found with server
	 */
	
	
	public ThirdPartyOutManifestDoxTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the in manifestd consignment dtls.
	 *
	 * @param manifestFactoryTO the manifest factory to
	 * @return the in manifestd consignment dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public ThirdPartyOutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException, CGSystemException;
	
	public String saveOrUpdateThirdPartyOutManifestDox(ManifestDO manifest,
			ThirdPartyOutManifestDoxTO thirdPartyOutManifestDoxTO , List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException; 
	
	public ThirdPartyOutManifestDoxTO searchThirdPartyManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;
	
	 
}
