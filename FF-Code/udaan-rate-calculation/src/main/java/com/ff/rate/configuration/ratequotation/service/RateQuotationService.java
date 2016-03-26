/**
 * 
 */
package com.ff.rate.configuration.ratequotation.service;


import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.OctroiChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationCODChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

/**
 * @author rmaladi
 *
 */
public interface RateQuotationService {

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateIndustryTypeTO> getRateIndustryType() throws CGBusinessException,
	CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CustomerGroupTO> getcustomerGroup() throws CGBusinessException,
	CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	EmployeeUserTO getEmployeeUser(Integer userId) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotationTO searchQuotationDetails(RateQuotationTO rateQuotationTO)
	throws CGBusinessException, CGSystemException;

	/**
	 * @param loggedInRHO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	OfficeTO getOfficeDetails(Integer loggedInRHO) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param rhoCityTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCitiesByCity(CityTO rhoCityTO) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param cityTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CityTO getCity(CityTO cityTO) throws CGBusinessException, CGSystemException;

	/**
	 * @param string
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<StockStandardTypeTO> getStandardType(String string)
	throws CGBusinessException, CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<InsuredByTO> getRiskSurchargeInsuredBy() throws CGBusinessException,
	CGSystemException;

	/**
	 * @param quotationId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotaionFixedChargesTO> loadDefaultFixedChargesValue(
	String quotationId) throws CGBusinessException, CGSystemException;

	/**
	 * @param octroiChargeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	OctroiChargeTO getOctroiChargeValue(OctroiChargeTO octroiChargeTO)
	throws CGBusinessException, CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotationTO saveOrUpdateBasicInfo(RateQuotationTO rateQuotationTO)
	throws CGBusinessException, CGSystemException;

	/**
	 * @param fixedChargesTO
	 * @param codList 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotaionFixedChargesTO saveOrUpdateFixedCharges(
	RateQuotaionFixedChargesTO fixedChargesTO, List<RateQuotationCODChargeTO> codList)
	throws CGBusinessException, CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateComponentTO> loadDefaultRateComponentValue()
	throws CGBusinessException, CGSystemException;

	/**
	 * @param rtoChargesTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotaionRTOChargesTO saveOrUpdateRTOCharges(
	RateQuotaionRTOChargesTO rtoChargesTO) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param stateId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateTaxComponentTO> loadDefaultRateTaxComponentValue(Integer stateId)
	throws CGBusinessException, CGSystemException;

	/**
	 * @param quotationId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotaionRTOChargesTO loadRTOChargesDefault(String quotationId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotationTO copyQuotation(RateQuotationTO rateQuotationTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @param loginOfficId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getAllOfficesUnderLoggedInRHO(Integer loginOfficId)throws CGBusinessException,
	CGSystemException;
	
	/**
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<UserDO> getAllUsersOfOffice(Integer officeId)throws CGBusinessException,
	CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CodChargeTO> getDeclaredValueCodCharge() throws CGBusinessException,
			CGSystemException;

	/**
	 * @param loginCityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param officeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param fixedChargesTO
	 * @param codList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotaionFixedChargesTO saveOrUpdateEcomerceFixedCharges(
			RateQuotaionFixedChargesTO fixedChargesTO,
			List<RateQuotationCODChargeTO> codList) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param quotationId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationCODChargeTO> loadQuotationCodCharge(String quotationId)
			throws CGBusinessException, CGSystemException;


	/**
	 * @param rbmhTOList
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param quotationNo
	 * @param status 
	 * @param userRegionId 
	 * @param isEQApprover 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> searchQuotationForRegional(List<RegionRateBenchMarkDiscountTO> rbmhTOList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover, Integer userRegionId) throws CGBusinessException,
	CGSystemException;
	
	/**
	 * @param userDOList
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> searchQuotationByUserIdDateAndStatus(List<UserDO> userDOList,Date fromDate, Date toDate, String status) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param rateQuotationTO
	 * @param rateContractTO 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateContractTO createContract(RateQuotationTO rateQuotationTO, RateContractTO rateContractTO)
			throws CGBusinessException, CGSystemException;
	
	 /**
	 * @param userDOList
	 * @param quotatnNo
	 * @param status
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> searchQuotationByUserIdStatusAndQuotatnNo(List<UserDO> userDOList,String quotatnNo, String status) throws CGBusinessException,
	CGSystemException;


	/**
	 * @param rateQuotationProposedRatesTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void getRateQuotationProposedRateDetails(RateQuotationProposedRatesTO rateQuotationProposedRatesTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param rateQuotationProposedRatesTO
	 * @param rqTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotationProposedRatesTO  saveOrUpdateSlabRateDeatails(RateQuotationProposedRatesTO rateQuotationProposedRatesTO, RateQuotationTO rqTO) throws CGBusinessException, CGSystemException;
	
	
	/**
	 * @param rtoChargesTO
	 * @param custCode
	 * @param regionId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateQuotaionRTOChargesTO submitRateQuotation(
			RateQuotaionRTOChargesTO rtoChargesTO, String custCode,  Integer regionId) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * @param userId
	 * @param regionIds
	 * @param cityIds
	 * @param fromDate
	 * @param toDate
	 * @param quotationNo
	 * @param status
	 * @param type
	 * @param officeType 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> rateQuotationListViewDetails(Integer userId, Integer[] regionIds, Integer[] cityIds, String fromDate, String toDate, String quotationNo, String status, String type, String officeType) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param selectdNosArrayOfTypeRO
	 * @param selectdNosArrayOfTypeRC
	 * @param opName
	 * @param approver
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean approveRejectDomesticQuotation(String[] selectdNosArrayOfTypeRO, String[] selectdNosArrayOfTypeRC,String opName, String approver)throws CGBusinessException, CGSystemException;

	/**
	 * @param empId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RegionRateBenchMarkDiscountTO>  checkEmpIdRegionalApprovr(Integer empId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param empId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<Integer> checkEmpIdCorpApprovr(Integer empId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param rateIndustryCategryIdList
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param quotationNo
	 * @param isEQApprover 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateQuotationListViewTO> searchQuotationForCorp(List<Integer> rateIndustryCategryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover)
			throws CGBusinessException,	CGSystemException;

	List<RateQuotationListViewTO> rateQuotationListViewSEDetails(
			Integer userId, String fromDate, String toDate, String quotationNo, String status) throws CGBusinessException,	CGSystemException;

	RateQuotationDO getContractQuotation(RateQuotationTO rateQuotationTO) throws CGBusinessException,	CGSystemException;

	void proceedUploadRateQuotation(RateQuotationTO rateQuotationTO,
			OfficeTO loggedInOffice, String filePath, String jobNumber) throws CGBusinessException,	CGSystemException;

	boolean isEcommerceQuotationApprover(Integer i, String string) throws CGBusinessException,	CGSystemException;
}
