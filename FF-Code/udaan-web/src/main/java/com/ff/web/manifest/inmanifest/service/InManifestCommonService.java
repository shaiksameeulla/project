package com.ff.web.manifest.inmanifest.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.tracking.ProcessTO;

/**
 * The Interface InManifestCommonService.
 *
 * @author narmdr
 */
public interface InManifestCommonService {

	/**
	 * Gets the manifest dtls.
	 *
	 * @param mbplTO the mbpl to
	 * @return the manifest dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestBaseTO getManifestDtls(ManifestBaseTO mbplTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the office details.
	 *
	 * @param regionId the region id
	 * @return the office details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	OfficeTO getOfficeDetails(Integer regionId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all regions.
	 *
	 * @return the all regions
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the cities by region.
	 *
	 * @param regionTO the region to
	 * @return the cities by region
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city.
	 *
	 * @param cityId the city id
	 * @return the all offices by city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city and office type.
	 *
	 * @param cityId the city id
	 * @param officeTypeId the office type id
	 * @return the all offices by city and office type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest grid dtls.
	 *
	 * @param baseTO the base to
	 * @return the manifest grid dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InMasterBagManifestDetailsTO getManifestGridDtls(ManifestBaseTO baseTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the process.
	 *
	 * @param processTO the process to
	 * @return the process
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ProcessTO getProcess(ProcessTO processTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the city.
	 *
	 * @param cityTO the city to
	 * @return the city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	CityTO getCity(CityTO cityTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the less excess manifest.
	 *
	 * @param inBagManifestTO the in bag manifest to
	 * @param inManifestValidateTO the in manifest validate to
	 * @return the less excess manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void getLessExcessManifest(InManifestTO inBagManifestTO,
			InManifestTO inManifestValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate pincode.
	 *
	 * @param pincodeTO the pincode to
	 * @return the in manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestValidationTO validatePincode(PincodeTO pincodeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update consignment.
	 *
	 * @param consignmentDO the consignment do
	 * @return the consignment do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the cN pricing details by consg id.
	 *
	 * @param bookingValidationTO the booking validation to
	 * @return the cN pricing details by consg id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
/*	CNPricingDetailsDO getCNPricingDetailsByConsgId(Integer consgId)
			throws CGBusinessException, CGSystemException;
*/
	/**
	 * Validate declared value.
	 *
	 * @param bookingValidationTO the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the paper works.
	 *
	 * @param paperWorkValidationTO the paper work validation to
	 * @return the paper works
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CNPaperWorksTO> getPaperWorks(CNPaperWorksTO paperWorkValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the less excess consg.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the less excess consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void getLessExcessConsg(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booking type by consg number.
	 *
	 * @param consignmentTO the consignment to
	 * @return the booking type by consg number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update cn pricing details.
	 *
	 * @param cnPricingDetailsTO the cn pricing details to
	 * @return the cN pricing details do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	/*CNPricingDetailsDO saveOrUpdateCnPricingDetails(
			CNPricingDetailsDO cnPricingDetailsDO) throws CGBusinessException,
			CGSystemException;*/

	/**
	 * Update cn pricing details.
	 *
	 * @param cnPricingDetailsTO the cn pricing details to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void updateCnPricingDetails(CNPricingDetailsTO cnPricingDetailsTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Generate less excess report.
	 *
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws HttpException 
	 */
	void generateLessExcessReport() throws CGBusinessException,
			CGSystemException, HttpException, ClassNotFoundException, IOException;

	/**
	 * Gets the consignment id by consg no.
	 *
	 * @param consignmentTO the consignment to
	 * @return the consignment id by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update receive dtls.
	 *
	 * @param manifestTO the manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateReceiveDtls(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is bpl exists.
	 *
	 * @param baseTO the base to
	 * @return the manifest base to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestBaseTO isBPLExists(ManifestBaseTO baseTO) throws CGBusinessException, CGSystemException;
	
	
	/**
	 * Gets the error messages.
	 *
	 * @param errorCode the error code
	 * @return the error messages
	 */
	public String getErrorMessages(String errorCode);

	/**
	 * Gets the office by id or code.
	 *
	 * @param officeId the office id
	 * @param officeCode the office code
	 * @return the office by id or code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	OfficeTO getOfficeByIdOrCode(Integer officeId, String officeCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is manifest num in manifested.
	 *
	 * @param inManifestTO the in manifest to
	 * @return true, if is manifest num in manifested
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestNumInManifested(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update manifest.
	 *
	 * @param manifestDO the manifest do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest dtls by manifest no.
	 *
	 * @param manifestNumber the manifest number
	 * @return the manifest dtls by manifest no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestBaseTO getManifestDtlsByManifestNo(String manifestNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Update manifest remarks.
	 *
	 * @param manifestDO the manifest do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void updateManifestRemarks(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Calc and get operating level.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the integer
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer calcAndGetOperatingLevel(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest operating level.
	 *
	 * @param originOfficeId the origin office id
	 * @param destOfficeId the dest office id
	 * @param destCityId the dest city id
	 * @return the manifest operating level
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestOperatingLevel(Integer originOfficeId,
			Integer destOfficeId, Integer destCityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Prepare in manifest t o4 less excess.
	 *
	 * @param inBagManifestTO the in bag manifest to
	 * @param headerProcessCode the header process code
	 * @param headerUpdateProcessCode the header update process code
	 * @return the in manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestTO prepareInManifestTO4LessExcess(InManifestTO inBagManifestTO,
			String headerProcessCode, String headerUpdateProcessCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is manifest header in manifested.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return true, if is manifest header in manifested
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestHeaderInManifested(ManifestBaseTO manifestBaseTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Sets the billing flags in consignment.
	 *
	 * @param consignmentDO the new billing flags in consignment
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void setBillingFlagsInConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest details with fetch profile.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest details with fetch profile
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestDetailsWithFetchProfile(ManifestBaseTO manifestBaseTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest id from cn manifest.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest id from cn manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Sets the product in consg.
	 *
	 * @param manifestDO the new product in consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void setProductInConsg(ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate and set excess consg flag.
	 *
	 * @param manifestDO the manifest do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void validateAndSetExcessConsgFlag(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Two way write.
	 *
	 * @param inManifestTO the in manifest to
	 */
	void twoWayWrite(InManifestTO inManifestTO);

	/**
	 * Validate consg no in manifested.
	 *
	 * @param inManifestTO the in manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void validateConsgNoInManifested(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest dtls by consg no operating office.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest dtls by consg no operating office
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestTO getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the booking office id by consg no.
	 *
	 * @param consgNo the consg no
	 * @return the booking office id by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getBookingOfficeIdByConsgNo(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the and set booking office.
	 *
	 * @param consignmentDO the consignment do
	 * @return the and set booking office
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void getAndSetBookingOffice(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate is manifest out manifested.
	 *
	 * @param manifestDO the manifest do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void validateIsManifestOutManifested(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	
	/**
	 * Gets the mnfst open type and bpl mnfst type.
	 *
	 * @param inManifestDO the in manifest do
	 * @return the mnfst open type and bpl mnfst type
	 * @throws CGSystemException the CG system exception
	 */
	void getMnfstOpenTypeAndBplMnfstType(ManifestDO inManifestDO)
			throws CGSystemException;

}
