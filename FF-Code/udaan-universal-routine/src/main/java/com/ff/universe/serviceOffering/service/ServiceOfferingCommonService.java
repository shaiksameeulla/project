package com.ff.universe.serviceOffering.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.global.RemarksTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.BookingTypeProductMapTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.PrivilegeCardTO;
import com.ff.serviceOfferring.PrivilegeCardTransactionTO;
import com.ff.serviceOfferring.ProductGroupServiceabilityTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceability.PincodeBranchServiceabilityCityNameTO;
import com.ff.serviceability.PincodeBranchServiceabilityTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;
import com.ff.to.utilities.LocationSearchTO;

public interface ServiceOfferingCommonService {

	/**
	 * To get Consignment Type
	 * 
	 * @return consignmentTypeTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGSystemException, CGBusinessException;

	/**
	 * To get Payment Details List
	 * 
	 * @return paymentModeTOs
	 */
	public List<PaymentModeTO> getPaymentDetails();

	public PaymentModeTO getPaymentMode(String processCode, String payModeCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Payment Details
	 * 
	 * @param paymentId
	 * @return paymentModeTOs
	 */
	public PaymentModeTO getPaymentDetails(Integer paymentId);

	/**
	 * To get Content Values
	 * 
	 * @return cnContentTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException;

	/**
	 * To get PaperWorks
	 * 
	 * @param paperWorkValidationTO
	 * @return cnPaperWorksTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get Product By Consg. Series
	 * 
	 * @param consgSeries
	 * @return productTO
	 * @throws CGSystemException
	 */
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException;

	/**
	 * To get Booking Type ProductMap
	 * 
	 * @param bookingType
	 * @return bookingProductMapTO
	 * @throws CGSystemException
	 */
	public BookingTypeProductMapTO getBookingTypeProductMap(String bookingType)
			throws CGSystemException;

	/**
	 * To get PrivilegeCard Details
	 * 
	 * @param privilegeCardNo
	 * @return privgCardTO
	 * @throws CGSystemException
	 */
	public PrivilegeCardTO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException;

	/**
	 * To get PrivilegeCard Transaction Details
	 * 
	 * @param privilegeCardNo
	 * @return privgCardTransDtlsTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<PrivilegeCardTransactionTO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get InsuarnceBy
	 * 
	 * @return insuredByTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
			CGBusinessException;

	/**
	 * To get Insurance Config Details
	 * 
	 * @param declarebValue
	 * @param bookingType
	 * @return insuredByTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<InsuranceConfigTO> getInsuarnceConfigDtls(Double declarebValue,
			String bookingType) throws CGSystemException, CGBusinessException;

	/**
	 * To get Consignment type Config details
	 * 
	 * @param consgTypeConfigTO
	 * @return configTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To check whether product is Serviced By Booking
	 * 
	 * @param bookingType
	 * @param congSeries
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGBusinessException, CGSystemException;

	/**
	 * To get All Products
	 * 
	 * @param bookingType
	 * @return productTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<ProductTO> getAllBookingProductMapping(String bookingType)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get ConsignmentTypes
	 * 
	 * @param consgTypeTO
	 * @return consignmentTypeTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get PaperWork By Pincode
	 * 
	 * @param pincode
	 * @param paperWorkName
	 * @return cnPaperWorksTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CNPaperWorksTO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException, CGBusinessException;

	/**
	 * To get Consginment Content By Name
	 * 
	 * @param cnContentName
	 * @return cnContentTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CNContentTO getCNContentByName(String cnContentName)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get insuredBy Name Or Code
	 * 
	 * @param insuredByName
	 * @param insuredByCode
	 * @return insuredByTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public InsuredByTO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById)
			throws CGSystemException, CGBusinessException;

	/**
	 * To check whether normal product is serviced by booking
	 * 
	 * @param bookingType
	 * @param prodCode
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException;

	/**
	 * To get consignment type
	 * 
	 * @param consignmentTypeTO
	 * @return consgTypeTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public ConsignmentTypeTO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get standard types as map
	 * 
	 * @param typeName
	 * @return stockStdTypeMap
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get Load No
	 * 
	 * @return loadLotTOList
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<LoadLotTO> getLoadNo() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the reasons by reason type.
	 * 
	 * @param reasonTO
	 *            the reason to
	 * @return the reasons by reason type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all identity proofs.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the all identity proofs
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<IdentityProofTypeTO> getAllIdentityProofs(IdentityProofTypeTO inputTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all relations.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the all relations
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RelationTO> getAllRelations(RelationTO inputTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get payment mode details by processCode
	 * 
	 * @param processCode
	 * @return paymentModeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException;

	public CNContentTO getCNContentByCodeOrId(Integer contentId,
			String contentCode) throws CGSystemException, CGBusinessException;

	public CNPaperWorksTO getPaperWorkByCodeOrId(Integer paperWorkId,
			String paperWorkCode) throws CGSystemException, CGBusinessException;

	public InsuranceConfigTO validateInsuarnceConfigDtls(Double declarebValue,
			String bookingType, Integer insuredById) throws CGSystemException,
			CGBusinessException;

	public List<ProductTO> getAllProducts() throws CGBusinessException,
			CGSystemException;

	public List<ProductGroupServiceabilityTO> getAllProductGroup()
			throws CGBusinessException, CGSystemException;

	public List<RemarksTO> getRemarksByType(String remarkType)
			throws CGBusinessException, CGSystemException;

	public PrivilegeCardTransactionTO getprivilegeCardDtls(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the product details by product.
	 * 
	 * @param productTO
	 *            the product to
	 * @return the product details by product
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ProductTO> getProductDetailsByProduct(ProductTO productTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consg type config dtls by consg cong type.
	 * 
	 * @param consgTypeConfigTO
	 *            the consg type config to
	 * @return the consg type config dtls by consg cong type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ConsignmentTypeConfigTO> getConsgTypeConfigDtlsByConsgCongType(
			ConsignmentTypeConfigTO consgTypeConfigTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param productId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public ProductDO getProductByProductId(Integer productId)
			throws CGSystemException, CGBusinessException;

	public List<PincodeBranchServiceabilityTO> getAllServicingOfficebyPincode(
			String pincode) throws CGBusinessException, CGSystemException;

	public List<PincodeBranchServiceabilityTO> getAllServicingOfficebyOfficeId(
			String officeCode) throws CGBusinessException, CGSystemException;

	public List<RegionTO> getAllRegionsList() throws CGBusinessException,
			CGSystemException;

	public List<CityTO> getAllCites() throws CGBusinessException,
			CGSystemException;

	public List<OfficeTO> getAllOfficesByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllBranchOfficesByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException;

	public List<PincodeBranchServiceabilityTO> getAllPincodesByOfficeId(
			Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<String> getAllServicingSearchLocation()
			throws CGBusinessException, CGSystemException;

	/**
	 * 
	 * @param locAddress
	 * @param loggedInOffice
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<LocationSearchTO> getlocationDetails(String locAddress, String cityName,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	public List<PincodeBranchServiceabilityCityNameTO> getAllServicingOfficebyPincodeCity(
			String pincode)throws CGBusinessException,
			CGSystemException;

	List<OfficeTO> getAllBranchAndStandaloneOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	public ConsignmentTypeDO getConsignmentTypeByCode(String consignmentTypeCode)throws CGBusinessException, CGSystemException;



	

}
