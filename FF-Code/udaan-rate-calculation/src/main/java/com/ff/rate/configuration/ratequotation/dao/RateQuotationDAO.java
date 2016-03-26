/**
 * 
 */
package com.ff.rate.configuration.ratequotation.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.OctroiChargeDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.to.ratemanagement.operations.ratequotation.OctroiChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;

/**
 * @author rmaladi
 *
 */
public interface RateQuotationDAO {

	/**
	 * @param rqpchDO
	 * @param rqDO
	 * @param originSector
	 * @param weightSlab
	 * @return
	 * @throws CGSystemException
	 */
	public RateQuotationProductCategoryHeaderDO saveOrUpdateRateQuotProposedRates(
			RateQuotationProductCategoryHeaderDO rqpchDO, RateQuotationDO rqDO, Integer originSector, Boolean weightSlab)
			throws CGSystemException ;
	
	/**
	 * @param quotationId
	 * @param productCatId
	 * @return
	 * @throws CGSystemException
	 */
	public List<RateQuotationWeightSlabDO> getRateQuotationWeightSlabs(Integer quotationId, Integer productCatId)
			throws CGSystemException;
	
	/**
	 * @param quotationId
	 * @param productCatId
	 * @return
	 * @throws CGSystemException
	 */
	public List<RateQuotationSlabRateDO> getRateQuotationSlabRates(Integer quotationId, Integer productCatId)
			throws CGSystemException;

	/**
	 * @param quotationId
	 * @param productCatId
	 * @return
	 * @throws CGSystemException
	 */
	public List<RateQuotationProductCategoryHeaderDO> getRateQuotationProductCatHeader(Integer quotationId, Integer productCatId)
			 throws CGSystemException;
		  
	/**
	 * @param prodCatHeaderId
	 * @return
	 * @throws CGSystemException
	 */
	public List<RateQuotationProductCategoryHeaderDO> getRateQuotProposedRateDetailsByProdHeader(
			Integer prodCatHeaderId) throws CGSystemException;
	

	
	/**
	 * @return
	 * @throws CGSystemException
	 */
	List<RateIndustryTypeDO> getRateIndustryType() throws CGSystemException;

	/**
	 * @return
	 * @throws CGSystemException
	 */
	List<CustomerGroupDO> getcustomerGroup() throws CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGSystemException
	 */
	EmployeeUserDO getEmployeeUser(Integer userId) throws CGSystemException;

	/**
	 * @param quotationDO
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationDO saveOrUpdateBasicInfo(RateQuotationDO quotationDO)
			throws CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationDO> searchQuotationDetails(RateQuotationTO rateQuotationTO)
			throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationFixedChargesDO> loadDefaultFixedChargesValue(
			Integer rateQuotationId) throws CGSystemException;

	/**
	 * @param octroiChargeTO
	 * @return
	 * @throws CGSystemException
	 */
	OctroiChargeDO getOctroiChargeValue(OctroiChargeTO octroiChargeTO)
			throws CGSystemException;

	/**
	 * @param quotationDO
	 * @param configDO
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationDO saveOrUpdateFixedCharges(RateQuotationDO quotationDO,
			Set<RateQuotationFixedChargesConfigDO> configDO)
			throws CGSystemException;

	/**
	 * @return
	 * @throws CGSystemException
	 */
	List<RateComponentDO> loadDefaultRateComponentValue()
			throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationFixedChargesConfigDO loadDefaultFixedChargesConfigValue(
			Integer rateQuotationId) throws CGSystemException;

	/**
	 * @param quotationDO
	 * @param rtoChargesDO
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationDO saveOrUpdateRTOCharges(RateQuotationDO quotationDO,
			RateQuotationRTOChargesDO rtoChargesDO) throws CGSystemException;
	

	/**
	 * @param stateId
	 * @return
	 */
	List<RateTaxComponentDO> loadDefaultRateTaxComponentValue(Integer stateId);

	/**
	 * @param quotationId
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationRTOChargesDO loadRTOChargesDefault(String quotationId)

			throws CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @param quotationNo
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationDO copyQuotation(RateQuotationTO rateQuotationTO,
			String quotationNo, RateQuotationDO copyQuotationDO) throws CGSystemException;

	/**
	 * @return
	 * @throws CGSystemException
	 */
	List<CodChargeDO> getDeclaredValueCodCharge() throws CGSystemException;

	/**
	 * @param quotationDO
	 * @param quotationCOD
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationDO saveOrUpdateEcomerceFixedCharges(
			RateQuotationDO quotationDO,
			Set<RateQuotationCODChargeDO> quotationCOD)
			throws CGSystemException;

	/**
	 * @param rateQuotationId
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationCODChargeDO> loadQuotationCodCharge(
			Integer rateQuotationId) throws CGSystemException;


	/**
	 * @param loggedInId
	 * @return
	 * @throws CGSystemException
	 */
	List<OfficeDO> getAllOfficesUnderLoggedInRHO(Integer loggedInId)
			throws CGSystemException;
	

	/**
	 * @param rateQuotationTO
	 * @param contractNo
	 * @param quotationNo
	 * @return
	 * @throws CGSystemException
	 */
	RateContractDO createContract(RateQuotationTO rateQuotationTO,
			String contractNo,String quotationNo, RateContractDO contractDO) throws CGSystemException;



	/**
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 */
	List<UserDO> getAllUsersOfOffice(Integer officeId)throws CGSystemException;

	/**
	 * @param regionId
	 * @param rateIndustryCategryIdList
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param quotationNo
	 * @param userRegionId 
	 * @param isEQApprover 
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> searchQuotationForRegional(List<Integer> regionId, Integer rateIndustryCategryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover, Integer userRegionId)throws CGSystemException;

	/**
	 * @param userId
	 * @return
	 */
	String getRHONameByCreatedBy(Integer userId);
	
	/**
	 * @param userId
	 * @return
	 * @throws CGSystemException 
	 */
	String getStationNameByCreatedBy(Integer userId) throws CGSystemException;
	
	/**
	 * @param custId
	 * @return
	 * @throws CGSystemException
	 */
	String getSalesOffcNameByCustomr(Integer custId) throws CGSystemException;
	 
	/**
	 * @param custId
	 * @return
	 * @throws CGSystemException
	 */
	String getSalesPersonNameByCustomr(Integer custId) throws CGSystemException;

	/**
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> getQuotationDtlsByUserIdDateAndStatus(Integer userId,Date fromDate,Date toDate,String status) throws CGSystemException;
	
	/**
	 * @param userId
	 * @param quotatnNo
	 * @param status
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> getQuotationDtlsByUserIdStatusAndQuotatnNo(Integer userId,String quotatnNo,String status) throws CGSystemException;
	

	/**
	 * @param quotationDO
	 * @param rtoChargesDO
	 * @return
	 * @throws CGSystemException
	 */
	RateQuotationRTOChargesDO saveOrUpdateQuotationRTOCharges(RateQuotationDO quotationDO,
			RateQuotationRTOChargesDO rtoChargesDO) throws CGSystemException;

	/**
	 * @param quotationDO
	 * @throws CGSystemException
	 */
	void submitQuotation(RateQuotationDO quotationDO)throws CGSystemException;

	/**
	 * @param userId
	 * @param region
	 * @param city
	 * @param fromDate
	 * @param toDate
	 * @param quotationNo
	 * @param status
	 * @param type
	 * @param officeType 
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> rateQuotationListViewDetails(Integer userId, Integer[] region, Integer[] city, String fromDate, String toDate, String quotationNo, String status, String type, String officeType) throws CGSystemException;

	/**
	 * @param quotationNoOfTypeRO
	 * @param quotationNoArrayOfTypeRC
	 * @param opName
	 * @param approver
	 * @return
	 * @throws CGSystemException
	 */
	boolean approveRejectDomesticQuotation(String[] quotationNoOfTypeRO,String[] quotationNoArrayOfTypeRC,String opName, String approver) throws CGSystemException;

	/**
	 * @param empId
	 * @return
	 * @throws CGSystemException
	 */
	List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprovr(Integer empId) throws CGSystemException;
	
	/**
	 * @param empId
	 * @return
	 * @throws CGSystemException
	 */
	List<RateBenchMarkHeaderDO> checkEmpIdCorpApprovr(Integer empId) throws CGSystemException;
	
	/**
	 * @param rateIndustryCategryIdList
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param quotationNo
	 * @param userRegionId 
	 * @param isEQApprover 
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> searchQuotationDtlsForCorp(List<Integer> rateIndustryCategryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover) throws CGSystemException;

	List<RateQuotationWrapperDO> rateQuotationListViewSEDetails(
			Integer userId, String fromDate, String toDate, String quotationNo, String status )  throws CGSystemException;
	
	RateQuotationDO getRateQuotation(String quotationNo, String quotationUsedFor, Integer quotationId) throws CGSystemException;

	void saveBulkRateQuotaions(List<RateQuotationDO> dataList) throws CGSystemException;

	RateQuotationDO rateQuotationByQuotationId(Integer quotationId)	throws CGSystemException;

	boolean isRateQuotationExist(String quotationNo, String quotationUsedFor) throws CGSystemException;

	Integer getUserIdByUserName(String userName) throws CGSystemException;
	Integer getOfcIdByOfcCode(String ofcCode) throws CGSystemException;

	boolean isEcommerceQuotationApprover(Integer userId, String screenCode) throws CGSystemException;
	
	public RateProductCategoryDO getRateProductDetailsFromRateProductId(Integer rateProductCategoryId) throws CGSystemException;
}
