package com.ff.rate.calculation.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.CustomerCustomerRateTypeDO;
import com.ff.domain.ratemanagement.masters.EBRatePreferenceDO;
import com.ff.domain.ratemanagement.masters.RateComponentCalculatedDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeInsuredByDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateCalculationCODChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateProductDO;
import com.ff.domain.ratemanagement.operations.ba.BaSlabRateDO;
import com.ff.domain.ratemanagement.operations.ba.BaSpecialDestinationRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashCODChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateHeaderProductDO;
import com.ff.domain.ratemanagement.operations.cash.CashSlabRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashSpecialDestinationDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.CashRateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationSpecialDestDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSlabRateDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;

public interface RateCalculationDAO {

	/**
	 * @param input
	 * @return
	 */
	public SectorDO getSectorByPincode(Integer orgCityId, String destPincode)
			throws CGSystemException;

	/**
	 * @param pincode
	 * @return
	 */
	public SectorDO getSectorByPincode(String pincode) throws CGSystemException;

	public List<RateQuotationSlabRateDO> getCourierQuotaionSlabsByCustomer(
			String customerCode, Integer sectorId) throws CGSystemException;

	public List<RateQuotationSlabRateDO> getRateSlabsByCustomerProductNSector(
			String customerCode, String productCode, Integer orgSector,
			Integer destSector) throws CGSystemException;

	/**
	 * @param customerCode
	 * @return
	 */
	public RateQuotationDO getQuotationByCustomerCode(String customerCode)
			throws CGSystemException;

	/**
	 * @param customerCode
	 * @return
	 */
	public RateQuotationProductCategoryHeaderDO getQuotationDetailsByCustomerCode(
			String customerCode) throws CGSystemException;

	/**
	 * @param rateTO
	 * @return
	 */
	/*public List<RateQuotationSpecialDestinationDO> getSpecialDestByQuotationNPincode(
			RateCalculationInputTO rateTO) throws CGSystemException;*/

	/**
	 * @param hql
	 * @param params
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getDataByNamedQueryAndNamedParam(String hql, String[] params,
			Object[] values) throws CGSystemException;

	/**
	 * @param orgSector
	 * @param destSector
	 * @param rateTO
	 * @return
	 */
	public RateQuotationSlabRateDO getRateQuotationSlabRateForOtherThanSpecialDest(
			Integer orgSector, Integer destSector, RateCalculationInputTO rateTO)
			throws CGSystemException;

	public RateQuotationCODChargeDO calculateCODCharge(Double appliedOn,
			Integer rateQuotation) throws CGSystemException;

	public List<RateComponentDO> getAllRateComponaent()
			throws CGSystemException;

	/**
	 * @param pincode
	 * @return
	 */
	public CityDO getCity(String pincode) throws CGSystemException;

	/**
	 * @return
	 */
	public List<RateComponentCalculatedDO> getDependentRateComponentForCalculation()
			throws CGSystemException;

	/**
	 * @param input
	 * @param rateQuotationType
	 * @return
	 */
	public RateCalculationProductCategoryHeaderDO getQuotationForCustomerAndProduct(
			RateCalculationInputTO input, String rateQuotationType)
			throws CGSystemException;

	/**
	 * @param rateQuotationProductCategoryHeaderId
	 * @param originSector
	 * @param destinationSector
	 * @param rateCalculatedFor 
	 * @param string
	 * @return
	 */
	public List<RateCalculationQuotationSlabRateDO> getNormalRateSlabs(
			Integer rateQuotationProductCategoryHeaderId, Integer originSector,
			Integer destinationSector, String isOriginConsidered, String rateCalculatedFor)
			throws CGSystemException;

	/**
	 * @param cityId
	 * @param rateQuotationProductCategoryHeaderId
	 * @param stateId 
	 * @param isOriginConsidered
	 * @param rateCalculatedFor 
	 * @param originConsidered
	 * @return
	 */
	public List<RateCalculationSpecialDestDO> getSpecialDestinationSlabs(
			Integer cityId, Integer rateQuotationProductCategoryHeaderId,
			Integer stateId, String isOriginConsidered, Integer originSector, String rateCalculatedFor)
			throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @param rateTO
	 * @return
	 */
	public List<RateComponentDO> getRateComponentList(
			RateCalculationInputTO rateTO, Integer rateQuotationId)
			throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @return
	 */
	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForQuotation(
			Integer rateQuotationId) throws CGSystemException;

	/**
	 * @param declaredValue
	 * @param customerCategory
	 * @return
	 */
	public RiskSurchargeDO getRiskSurcharge(Double declaredValue,
			Integer customerCategory) throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @return
	 */
	public RateQuotationRTOChargesDO getRTOChargesForRateQuotation(
			Integer rateQuotationId) throws CGSystemException;

	/**
	 * @param stateId
	 * @param currentDate
	 * @return
	 */
	public List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate, String taxGroup) throws CGSystemException;

	public RiskSurchargeInsuredByDO getInsuredBy(Integer insuredBy)
			throws CGSystemException;

	public BaRateProductDO getBaRateProductHeader(RateCalculationInputTO rateTO)
			throws CGSystemException;

	public List<BaSlabRateDO> getBANormalRateSlabs(
			Integer productCategoryHeaderId, Integer originSector,
			Integer destinationSector, String servicedOn)
			throws CGSystemException;

	public List<BaSpecialDestinationRateDO> getBASpecialDestinationSlabs(
			Integer cityId,Integer stateId, Integer rateProductCategoryHeaderId,
			 String servicedOn) throws CGSystemException;

	public CityDO getCityByCityCode(String originCityCode)
			throws CGSystemException;

	public List<RateComponentDO> getRateComponentListForBA(
			RateCalculationInputTO rateTO, Integer headerId, String priorityInd)
			throws CGSystemException;

	public BaRateCalculationCODChargesDO calculateBACODCharge(double declaredValue,
			int headerId, String priorityInd) throws CGSystemException;

	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForBA(
			int baProductHeaderId, String priorityInd) throws CGSystemException;

	public List<RateComponentDO> getRateComponentListForCash(
			RateCalculationInputTO rateTO, Integer headerId, String priorityInd)
			throws CGSystemException;

	public CashRateHeaderProductDO getCashProductHeaderMap(
			RateCalculationInputTO rateTO) throws CGSystemException;

	public List<CashSlabRateDO> getCashNormalRateSlabs(
			Integer productCategoryHeaderId, Integer originSector,
			Integer destinationSector, String servicedOn,
			String originConsidered) throws CGSystemException;

	public List<CashSpecialDestinationDO> getCashSpecialDestinationSlabs(
			Integer cityId, Integer stateId, Integer rateProductCategoryHeaderId,
			 String servicedOn, String originConsidered, Integer originSector)
			throws CGSystemException;

	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForCash(
			Integer headerProductMapId, String priorityInd) throws CGSystemException;

	public CashCODChargesDO calculateCashCODCharge(double declaredValue,
			Integer headerProductMapId) throws CGSystemException;

	public BARateRTOChargesDO getRTOChargesForBA(int baProductHeaderId, String priorityInd) throws CGSystemException;

	public CashRateRTOChargesDO getRTOChargesForCash(Integer headerProductMapId, String priorityInd)
			throws CGSystemException;

	public EBRatePreferenceDO getEBRatePreferenceDetails(
			RateCalculationInputTO input) throws CGSystemException;

	public List<RateComponentDO> getRateComponentListForCash(
			RateCalculationInputTO input) throws CGSystemException;

	public Boolean isProductValidForCreditCustomerContract(
			ProductToBeValidatedInputTO input) throws CGSystemException;

	public Boolean isProductValidForCashContract(
			ProductToBeValidatedInputTO input) throws CGSystemException;

	public Boolean isProductValidForBACustomerContract(
			ProductToBeValidatedInputTO input) throws CGSystemException;

	public Boolean isProductValidForEBCashContract(
			ProductToBeValidatedInputTO input) throws CGSystemException;

	public SectorDO getDestinationSector(Integer cityId,
			String destinationPincode) throws CGSystemException;

	public CustomerCustomerRateTypeDO getCustomerCodeAndRateCustomerCategoryByCustomerId(
			Integer customer) throws CGSystemException;

	List<BookingPreferenceDetailsDO> getBookingPrefDetails()
			throws CGSystemException;

	public List<ConsignmentBilling> getConsignmentForRate(int i, String rateType)throws CGSystemException;

	public BookingDO getConsgBookingDetails(String consgNo)throws CGSystemException;

	public ProductDO getProduct(Integer productId)throws CGSystemException;

	public RiskSurchargeInsuredByDO getInsuredByInsuredByCode(String insuredBy)throws CGSystemException;

}
