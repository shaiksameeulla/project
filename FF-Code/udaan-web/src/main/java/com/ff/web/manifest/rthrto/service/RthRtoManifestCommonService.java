/*
 * 
 */
package com.ff.web.manifest.rthrto.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;

/**
 * The Interface RthRtoManifestCommonService.
 * 
 * @author narmdr
 */
public interface RthRtoManifestCommonService {
	
	/**
	 * Gets the offices by office.
	 *
	 * @param officeTO the office to
	 * @return the offices by office
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignment types.
	 *
	 * @param consgTypeTO the consg type to
	 * @return the consignment types
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the consignment details.
	 *
	 * @param consigValidationTO the consig validation to
	 * @return the consignment details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public RthRtoDetailsTO getConsignmentDetails(
			ConsignmentValidationTO consigValidationTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the reasons by reason type.
	 *
	 * @param reasonTO the reason to
	 * @return the reasons by reason type
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the process.
	 *
	 * @param processTO the process to
	 * @return the process
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public ProcessTO getProcess(ProcessTO processTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Creates the process number.
	 *
	 * @param processTO the process to
	 * @param officeTO the office to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the consingment dtls.
	 *
	 * @param consignmentNumber the consignment number
	 * @return the consingment dtls
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public ConsignmentTO getConsingmentDtls(String consignmentNumber)
			throws CGSystemException, CGBusinessException;

	/**
	 * Search rtoh manifest details.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return the rth rto manifest to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public RthRtoManifestTO searchRTOHManifestDetails(
			RthRtoManifestTO rthRtoManifestTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the cities by city.
	 *
	 * @param cityTO the city to
	 * @return the cities by city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is rtoh no manifested.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @return true, if is rtoh no manifested
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isRtohNoManifested(RthRtoManifestTO rthRtoManifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all regions.
	 *
	 * @return the all regions
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the hub and brnch offices4 city.
	 *
	 * @param cityId the city id
	 * @return the hub and brnch offices4 city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<OfficeTO> getHubAndBrnchOffices4City(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the config params.
	 *
	 * @return the config params
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public Map<String, String> getConfigParams() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the drs dtls by consg no.
	 *
	 * @param consignmentNumber the consignment number
	 * @return the drs dtls by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public DeliveryDetailsTO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the in manifest date by consg id.
	 *
	 * @param consgId the consg id
	 * @return the in manifest date by consg id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String getInManifestDateByConsgId(Integer consgId)
			throws CGBusinessException, CGSystemException;

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
	 * Update consignment status.
	 *
	 * @param consignmentStatus the consignment status
	 * @param processCode the process code
	 * @param consgNumbers the consg numbers
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumbers)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the and set operating level and office to manifest.
	 *
	 * @param manifestDO the manifest do
	 * @return the and set operating level and office to manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void getAndSetOperatingLevelAndOfficeToManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the offices by city and office types.
	 *
	 * @param cityId the city id
	 * @param officeTypeCode the office type code
	 * @return the offices by city and office types
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<OfficeTO> getOfficesByCityAndOfficeTypes(Integer cityId,
			String officeTypeCode) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get city by office id
	 * 
	 * @param officeId
	 * @return cityTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CityTO getCityByOfficeId(Integer officeId) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * To check whether consignment is validated or not for RTH/RTO process
	 * 
	 * @param consgValidationTO
	 * @return Boolean true if RTH/RTO validation done else false
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Integer isConsgNoValidated(ConsignmentValidationTO consgValidationTO) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Sets the rto billing flags in consignment.
	 *
	 * @param consignmentDO the new rto billing flags in consignment
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void setRtoBillingFlagsInConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest id from cn manifest.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest id from cn manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;
	
	public ReasonDO validateConsNoForBranchOut(String returnType, Integer loginOfficeId, String consNo)
			throws CGBusinessException, CGSystemException;
	
	//public ConsignmentBillingRateDO calculateAndSetRtoRate(ConsignmentDO consignmentDO)throws CGBusinessException, CGSystemException;
	public ConsignmentTO getConsgDetailsByNo(String consgNo)throws CGBusinessException, CGSystemException;
	public ConsignmentRateCalculationOutputTO calculateRtoRateForConsignment(ConsignmentTO consignmentTO)throws CGBusinessException, CGSystemException;

	/**
	 * Two way write.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 */
	public void twoWayWrite(RthRtoManifestTO rthRtoManifestTO);

	/**
	 * Gets the manifest dtls by consg no operating office.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest dtls by consg no operating office
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InManifestTO getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGBusinessException,
			CGSystemException;

	public List<CityTO> getCitiesByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException;
}
