package com.ff.universe.serviceOffering.dao;

/**
 * 
 */

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.locationSearch.LocationSearchDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.serviceOffering.BookingTypeProductMapDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeConfigDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.IdentityProofTypeDO;
import com.ff.domain.serviceOffering.InsuranceConfigDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.domain.serviceOffering.PrivilegeCardDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.serviceOffering.RelationDO;
import com.ff.domain.serviceOffering.RemarksDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityCityNameDO;
import com.ff.domain.serviceability.PincodeBranchServiceabilityDO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.serviceofferings.ReasonTO;

/**
 * The Interface ServiceOfferingServiceDAO.
 * 
 * @author uchauhan
 */
public interface ServiceOfferingServiceDAO {

	/**
	 * Gets the consignment type.
	 * 
	 * @return the consignment type
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTypeDO> getConsignmentType()
			throws CGSystemException;

	/**
	 * Gets the payment mode.
	 * 
	 * @return the payment mode
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<PaymentModeDO> getPaymentMode() throws CGSystemException;

	/**
	 * Gets the content values.
	 * 
	 * @return the content values
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CNContentDO> getContentValues() throws CGSystemException;

	/**
	 * Gets the paper works.
	 * 
	 * @param paperWorkValidationTO
	 *            the paper work validation to
	 * @return the paper works
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CNPaperWorksDO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException;

	/**
	 * Gets the product by consg series.
	 * 
	 * @param consgSeries
	 *            the consg series
	 * @return the product by consg series
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ProductDO getProductByConsgSeries(String consgSeries)
			throws CGSystemException;

	/**
	 * Gets the booking product mapping.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking product mapping
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingTypeProductMapDO getBookingProductMapping(String bookingType)
			throws CGSystemException;

	/**
	 * Gets the privilege card dtls.
	 * 
	 * @param privilegeCardNo
	 *            the privilege card no
	 * @return the privilege card dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public PrivilegeCardDO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException;

	/**
	 * Gets the privilege card trans dtls.
	 * 
	 * @param privilegeCardNo
	 *            the privilege card no
	 * @return the privilege card trans dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<PrivilegeCardTransactionDO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException;

	/**
	 * Gets the insured by.
	 * 
	 * @return the insured by
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<InsuredByDO> getInsuredBy() throws CGSystemException;

	/**
	 * Gets the insuarnce config dtls.
	 * 
	 * @param declarebValue
	 *            the declareb value
	 * @param bookingType
	 *            the booking type
	 * @return the insuarnce config dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<InsuranceConfigDO> getInsuarnceConfigDtls(Double declarebValue,
			String bookingType) throws CGSystemException;

	/**
	 * Gets the consg type config dtls.
	 * 
	 * @param consgTypeConfigTO
	 *            the consg type config to
	 * @return the consg type config dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ConsignmentTypeConfigDO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO) throws CGSystemException;

	/**
	 * Checks if is product serviced by booking.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @param congSeries
	 *            the cong series
	 * @return true, if is product serviced by booking
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGSystemException;

	/**
	 * Gets the all products.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the all products
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<BookingTypeProductMapDO> getAllBookingProductMapping(
			String bookingType) throws CGSystemException;

	/**
	 * Gets the payment mode.
	 * 
	 * @param paymentId
	 *            the payment id
	 * @return the payment mode
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public PaymentModeDO getPaymentMode(Integer paymentId)
			throws CGSystemException;

	/**
	 * Gets the consignment types.
	 * 
	 * @param consgTypeDO
	 *            the consg type do
	 * @return the consignment types
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTypeDO> getConsignmentTypes(
			ConsignmentTypeDO consgTypeDO) throws CGSystemException;

	/**
	 * Gets the paper work by pincode.
	 * 
	 * @param pincode
	 *            the pincode
	 * @param paperWorkName
	 *            the paper work name
	 * @return the paper work by pincode
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public CNPaperWorksDO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException;

	/**
	 * Gets the cN content by name.
	 * 
	 * @param cnContentName
	 *            the cn content name
	 * @return the cN content by name
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public CNContentDO getCNContentByName(String cnContentName)
			throws CGSystemException;

	/**
	 * Gets the insured by name or code.
	 * 
	 * @param insuredByName
	 *            the insured by name
	 * @param insuredByCode
	 *            the insured by code
	 * @return the insured by name or code
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public InsuredByDO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById) throws CGSystemException;

	/**
	 * Checks if is normal product serviced by booking.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @param prodCode
	 *            the prod code
	 * @return true, if is normal product serviced by booking
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException;

	/**
	 * Gets the consg type.
	 * 
	 * @param consignmentTypeTO
	 *            the consignment type to
	 * @return the consg type
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ConsignmentTypeDO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGSystemException;

	/**
	 * Gets the standard types as map.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the standard types as map
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<?> getStandardTypesAsMap(String typeName)
			throws CGSystemException;

	/**
	 * Gets the load no.
	 * 
	 * @return the load no
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<LoadLotDO> getLoadNo() throws CGSystemException;

	/**
	 * Gets the reasons by reason type.
	 * 
	 * @param reasonTO
	 *            the reason to
	 * @return the reasons by reason type
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ReasonDO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException;

	/**
	 * Gets the all identity proofs.
	 * 
	 * @param idProofDO
	 *            the id proof do
	 * @return the all identity proofs
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<IdentityProofTypeDO> getAllIdentityProofs(IdentityProofTypeDO idProofDO)
			throws CGSystemException;

	/**
	 * Gets the all relations.
	 * 
	 * @param relationDO
	 *            the relation do
	 * @return the all relations
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<RelationDO> getAllRelations(RelationDO relationDO)
			throws CGSystemException;

	/**
	 * To get payment mode details by processCode
	 * 
	 * @param processCode
	 * @return paymentModeDOs
	 * @throws CGSystemException
	 */
	public List<PaymentModeDO> getPaymentModeDtls(String processCode)
			throws CGSystemException;

	public PaymentModeDO getPaymentMode(String processCode, String payModeCode)
			throws CGSystemException;

	public CNContentDO getCNContentByCodeOrId(Integer contentId,
			String contentCode) throws CGSystemException;

	/**
	 * Gets the paper work by pincode.
	 * 
	 * @param pincode
	 *            the pincode
	 * @param paperWorkName
	 *            the paper work name
	 * @return the paper work by pincode
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public CNPaperWorksDO getPaperWorkByCodeOrId(Integer paperWorkId,
			String paperWorkCode) throws CGSystemException;

	public InsuranceConfigDO validateInsuarnceConfigDtls(Double declaredValue,
			String bookingType, Integer insuredBy) throws CGSystemException;

	public List<ProductDO> getAllProducts() throws CGSystemException;

	public List<ProductGroupServiceabilityDO> getAllProductGroup()
			throws CGSystemException;

	public List<RemarksDO> getRemarksByType(String remarkType)
			throws CGSystemException;

	public PrivilegeCardTransactionDO getprivilegeCardDtls(String consgNo)
			throws CGSystemException;

	/**
	 * Gets the product by product.
	 *
	 * @param productDO the product do
	 * @return the product by product
	 * @throws CGSystemException the cG system exception
	 */
	List<ProductDO> getProductByProduct(ProductDO productDO)
			throws CGSystemException;

	/**
	 * Gets the consg type config dtls by consg config type.
	 *
	 * @param consgTypeConfigTO the consg type config to
	 * @return the consg type config dtls by consg config type
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentTypeConfigDO> getConsgTypeConfigDtlsByConsgConfigType(
			ConsignmentTypeConfigTO consgTypeConfigTO) throws CGSystemException;

	/**
	 * @param productId
	 * @return
	 * @throws CGSystemException
	 */
	public ProductDO getProductByProductId(Integer productId) throws CGSystemException;
	
	public List<PincodeBranchServiceabilityDO> getAllServicingOfficebyPincode(String pincode) throws CGSystemException;

	public List<PincodeBranchServiceabilityDO> getAllServicingOfficebyBranch(String officeCode) throws CGSystemException;
	
	public List<PincodeBranchServiceabilityDO> getAllPincodesByOfficeId(Integer officeId) throws CGSystemException;
	
	/**
	 * Returns list of pincode
	 * 
	 * @param pincode
	 * @return
	 * @throws CGSystemException
	 */
	public List<PincodeBranchServiceabilityCityNameDO> getAllServicingOfficebyPincodeCity(String pincode) throws CGSystemException;

	/**
	 * Returns the the list of locations from ff_d_pincode table for Location Search Screen
	 * 
	 * @return
	 * @throws CGSystemException
	 */
	public List<String> getServicingLocationSearch()throws CGSystemException;

	
	/**
	 * Returns list containing Pincode, Branch Mapped and Product Mapped for Location Search Screen.
	 * 
	 * @param locAddress
	 * @param loggedInOffice
	 * @return
	 * @throws CGSystemException
	 */
	public List<LocationSearchDO> getServicingLocationDetails(String locAddress, String cityName, OfficeTO loggedInOffice) throws CGSystemException;

	/**
	 * @param consignmentTypeCode
	 * @return
	 * @throws CGSystemException
	 */
	public ConsignmentTypeDO getConsignmentTypeByCode(String consignmentTypeCode)throws CGSystemException;

}
