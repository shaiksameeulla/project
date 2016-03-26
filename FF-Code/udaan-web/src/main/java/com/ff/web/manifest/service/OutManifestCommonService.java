package com.ff.web.manifest.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.LoadLotTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProductMapTO;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * The Interface OutManifestCommonService.
 */
public interface OutManifestCommonService {

	/**
	 * Gets the office details.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the office details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all regions.
	 * 
	 * @return the all regions
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the cities by region.
	 * 
	 * @param manifestRegionTO
	 *            the manifest region to
	 * @return the cities by region
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CityTO> getCitiesByRegion(ManifestRegionTO manifestRegionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city.
	 * 
	 * @param cityId
	 *            the city id
	 * @return the all offices by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city and office type.
	 * 
	 * @param cityId
	 *            the city id
	 * @param officeTypeId
	 *            the office type id
	 * @return the all offices by city and office type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;

	/**
	 * To populate list of load no.
	 * 
	 * @return loadLotTOList
	 * @throws CGSystemException
	 *             - when no connection found with server
	 * @throws CGBusinessException
	 *             - Any violation of business rules
	 */
	public List<LoadLotTO> getLoadNo() throws CGSystemException,
			CGBusinessException;

	/**
	 * Save or update dispatch dtls.
	 * 
	 * @param manifestTOList
	 *            the manifest to list
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestTO> saveOrUpdateDispatchDtls(
			List<ManifestTO> manifestTOList) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate consignment.
	 * 
	 * @param cnValidateTO
	 *            the cn validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestValidate validateConsignment(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Checks if is consgn no manifested.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is consgn no manifested
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgnNoManifested(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest no.
	 * 
	 * @param stockIssueValidationTO
	 *            the stock issue validation to
	 * @param loginOfficeId
	 *            the login office id
	 * @return the manifest stock issue inputs
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ManifestStockIssueInputs validateManifestNo(
			ManifestStockIssueInputs stockIssueValidationTO,
			String loginOfficeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the office type list.
	 * 
	 * @return the office type list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
			CGSystemException;

	/**
	 * Checks if is valid pincode by branch.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is valid pincode by branch
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isValidPincodeByBranch(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is manifest embedded in.
	 * 
	 * @param ManifestInputs
	 *            the manifest inputs
	 * @return true, if is manifest embedded in
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isManifestEmbeddedIn(ManifestInputs ManifestInputs)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all stock standard type.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the all stock standard type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<StockStandardTypeTO> getAllStockStandardType(String typeName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is valid pincode by city.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is valid pincode by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isValidPincodeByCity(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the branches by hub.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the branches by hub
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTO> getBranchesByHub(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the party names for ba.
	 * 
	 * @return List<BusinessAssociateTO> -> list of all business associates
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<LoadMovementVendorTO> getPartyNames(String partyType,
			Integer regionId) throws CGSystemException;

	/**
	 * Gets the rf id by rf no.
	 * 
	 * @param rfNo
	 *            the rf no
	 * @return the rf id by rf no
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public Integer getRfIdByRfNo(String rfNo) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the pincode dtl.
	 * 
	 * @param pincodeTO
	 *            the pincode to
	 * @return the pincode dtl
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public PincodeTO getPincodeDtl(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the manifest product map dtls.
	 * 
	 * @param mnfstProductMapTO
	 *            the mnfst product map to
	 * @return the manifest product map dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<Object[]> getManifestProductMapDtls(
			ManifestProductMapTO mnfstProductMapTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the dtls for tpba.
	 * 
	 * @param baID
	 *            the ba id
	 * @return the dtls for tpba
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CustomerTO> getDtlsForTPBA(int baID)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the dtls for tpfr.
	 * 
	 * @param frID
	 *            the fr id
	 * @return the dtls for tpfr
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CustomerTO> getDtlsForTPFR(int frID)
			throws CGBusinessException, CGSystemException;;

	/**
	 * Gets the dtls for tpcc.
	 * 
	 * @param ccID
	 *            the cc id
	 * @return the dtls for tpcc
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<LoadMovementVendorTO> getDtlsForTPCC(int ccID)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the cities by city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the cities by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate declared value.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the city
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException, CGSystemException;

	/**
	 * Gets the city.
	 * 
	 * @param Pincode
	 *            the pincode
	 * @return the city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public CityTO getCity(String Pincode) throws CGBusinessException,
			CGSystemException, CGSystemException;

	/**
	 * Gets the consignee consignor dtls.
	 * 
	 * @param cnNumber
	 *            the cn number
	 * @param partyType
	 *            the party type
	 * @return the consignee consignor dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber,
			String partyType) throws CGSystemException, CGSystemException;

	/**
	 * Gets the consingment dtls.
	 * 
	 * @param consgNumner
	 *            the consg numner
	 * @return the consingment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consg princing dtls.
	 * 
	 * @param consgNumner
	 *            the consg numner
	 * @return the consg princing dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*
	 * public CNPricingDetailsTO getConsgPrincingDtls(String consgNumner) throws
	 * CGBusinessException, CGSystemException;
	 */

	/**
	 * Validate comail.
	 * 
	 * @param comailNo
	 *            the comail no
	 * @param manifestId 
	 * @param manifestId 
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String validateComail(String comailNo, Integer manifestId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the content values.
	 * 
	 * @return the content values
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the consg type config dtls.
	 * 
	 * @param consgTypeConfigTO
	 *            the consg type config to
	 * @return the consg type config dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO)
			throws CGSystemException, CGBusinessException;

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
	 * Save or update credit cust booking parcel.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String saveOrUpdateCreditCustBookingParcel(
			List<CreditCustomerBookingParcelTO> bookingTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the insuarnce by.
	 * 
	 * @return the insuarnce by
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the office types.
	 * 
	 * @return the office types
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTypeTO> getOfficeTypes() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the product by consg series.
	 * 
	 * @param consgSeries
	 *            the consg series
	 * @return the product by consg series
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException;

	/**
	 * Save or update credit cust booking dox.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<BookingDO> saveOrUpdateCreditCustBookingDox(
			List<CreditCustomerBookingDoxTO> bookingTOs)
			throws CGBusinessException, CGSystemException;

	String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	public OfficeTypeTO getOfficeTypeDOByOfficeTypeIdOrCode(
			OfficeTypeTO officeTypeTO) throws CGSystemException,
			CGBusinessException;

	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException, CGSystemException;

	/**
	 * To update Consignment weight if new weight is greater then booking weight
	 * 
	 * @param to
	 * @param processId
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean updateConsgWeight(OutManifestDetailBaseTO to, Integer processId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is consgn no in manifested.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is consgn no in manifested
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgnNoInManifested(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is embedded type is of in manifest.
	 * 
	 * @param manifestEmbeddId
	 *            the manifest embedd id
	 * @return true, if is embedded type is of in manifest
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isEmbeddedTypeIsOfInManifest(Integer manifestEmbeddId)
			throws CGBusinessException, CGSystemException;

	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	public List<OfficeTO> getAllOfficesByCityAndOfficeTypeCode(Integer cityId,
			String officeTypecode) throws CGBusinessException,
			CGSystemException;

	public List<Integer> saveOrUpdateConsignments(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException;

	public ProcessTO getProcess(ProcessTO processTO)
			throws CGBusinessException, CGSystemException;

	public ManifestDO getManifestById(Integer manifestId)
			throws CGBusinessException, CGSystemException;

	public Integer getRateComponentIdByCode(String rateCompCode)
			throws CGBusinessException, CGSystemException;

	public boolean isExistInComailTable(String comailNO)
			throws CGBusinessException, CGSystemException;

	public Integer getComailIdByComailNo(String comailNO)
			throws CGBusinessException, CGSystemException;

	boolean isValiedBagLockNo(String bagLockNo) throws CGBusinessException,
			CGSystemException;

	public List<ConsignmentDO> createConsignments(
			List<CreditCustomerBookingDoxTO> creditCustomerBookingDoxTOList,
			List<BookingDO> bookingDOList) throws CGBusinessException,
			CGSystemException;

	String updateConsignmentForOutDoxMF(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Unique Manifest
	 * 
	 * @param outmanifestDoxTO
	 * @param searchedManifest
	 * @return ManifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ManifestDO getUniqueManifest(OutManifestBaseTO outmanifestBaseTO,
			Boolean searchedManifest) throws CGBusinessException,
			CGSystemException;

	/**
	 * to get process
	 * 
	 * @param processCode
	 * @return processDO
	 * @throws CGSystemException
	 */
	public ProcessDO getProcess(String processCode) throws CGSystemException;

	public ManifestDO prepareManifestDO(ManifestInputs manifestTO);

	boolean saveOrUpdateBooking(List<BookingDO> bookingDOs,
			List<ConsignmentDO> pickupConsignment) throws CGBusinessException,
			CGSystemException;

	public List<BookingConsignmentDO> readAllConsignments(
			OutManifestBaseTO outmanifestTO) throws CGBusinessException,
			CGSystemException;

	public OutManifestValidate validateConsignmentForMisroute(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get consignment types
	 * 
	 * @param consgTypeTO
	 * @return ConsignmentTypeTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<ConsignmentTypeTO> getConsignmentTypes(ConsignmentTypeTO consgTypeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To calculate rate for consignment
	 * 
	 * @param consignmentTO
	 * @return ConsignmentRateCalculationOutputTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	ConsignmentRateCalculationOutputTO calculateRateForConsignment(
			ConsignmentTO consignmentTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * To save or update consignment billing rates
	 * 
	 * @param consignmentBillingRateDOs
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGBusinessException, CGSystemException;
	
	
	/**
	 * Validate consignment for branch manifest.
	 *
	 * @param manifestValidateTO the manifest validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public OutManifestValidate validateConsignmentForBranchManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException;
	
	public boolean isConsgnNoManifestedForBranchManifest(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To set booking details i.e. booking Date, customer id, rate customer
	 * category code.
	 * 
	 * @param consignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void setBookingDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is consg. no. in misrouted in logged in office or not.
	 * 
	 * @param manifestValidateTO
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean isConsgnNoMisroute(OutManifestValidate manifestValidateTO) 
			throws CGBusinessException,CGSystemException;
	
	public OutManifestValidate validateConsignmentForThirdPartyManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException;
	
	public List<ConsignmentManifestDO> isConsgnNoManifestedForThirdParty(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Two way write.
	 *
	 * @param outManifestBaseTO the out manifest base to
	 */
	public void twoWayWrite(OutManifestBaseTO outManifestBaseTO);

	/**
	 * @param customerId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ConsigneeConsignorDO setConsignorDetailsFromCustomer(
			CustomerDO customerId)throws CGBusinessException, CGSystemException;

	public Integer getNoOfElementsFromIn(String manifestNo)
			throws CGBusinessException, CGSystemException;

	public Integer getManifestIdByNo(String manifestNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the pincode details by pincode.
	 * 
	 * @param pincode
	 *            the pincode
	 * @return the pincode details by pincode
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<PincodeTO> getPincodeDetailsByPincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate consgn no out manifested.
	 *
	 * @param manifestValidateTO the manifest validate to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void validateConsgnNoOutManifested(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException;
	
}
