package com.ff.report.common.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.ConsignmentCustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.RateRevisionDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.report.wrapper.ClientGainedWrapperDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.manifest.LoadLotTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.LcCodReportAliasTO;
import com.ff.report.PriorityTypeTO;
import com.ff.report.ReportTypeTO;
import com.ff.to.mec.LiabilityTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;


/**
 * @author khassan
 *
 */
public interface CommonReportDAO {
	
	/**
	 * @param cityId
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getOfficesByCityIdForReport(Integer cityId) throws CGSystemException;
	public List<ItemTypeDO> getItemTypeList() throws CGSystemException;
	public List<CustomerDO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException;
	public List<EmployeeDO> getEmployeesByOfficeIdForReport(Integer cityId)throws CGSystemException;
	public List<OfficeDO> getAllHubsByCityID(Integer cityId) throws CGSystemException;
	public List<EmployeeDO> getSalesPersonsTitlesList(final String DepartmentType) throws CGSystemException;
	public List<RateIndustryTypeDO> getNatureOfBusinesses() throws CGSystemException;
	
	public List<RateProductCategoryTO> getRateProdcuts() throws CGBusinessException, CGSystemException;
	List<VobSlabTO> getSlabList(Integer productId) throws CGBusinessException, CGSystemException;
	
	public List<CityDO> getCityByCityID(Integer cityId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param userId
	 * @param cityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getOfficecByCityIDAndUserIDForReport(Integer userId, Integer cityId ) throws CGBusinessException, CGSystemException;
	
	
	public Long getworkingDaysFromHoliday(Integer regionId,String fromDate)throws CGSystemException;
	
	public List<RateProductCategoryDO> getProductsByCustomer(Integer customerId) throws CGSystemException;
	public List<SectorDO> getOriginSectors() throws CGSystemException;
	public List<CityDO> getCitiesByRegionIds(String regionIds)
			throws CGSystemException;
	public List<OfficeDO> getOfficesByCityIds(String cityIds)
			throws CGSystemException;
	public List getMonthList()	throws CGSystemException;
	
	public List<RegionDO> getAllRegionForBoUser(Integer userId, Integer officeId) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCityByRegionForBranchUser(Integer userId, Integer regionId) throws CGSystemException,CGBusinessException;
	
	public List<OfficeDO> getOfficeForBOUser(Integer cityId, Integer userID) throws CGSystemException,CGBusinessException;
	
	public List<RegionDO> getAllRegionByUserType(Integer userId, Integer officeId, String officeType) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCitiesByRegionForHubUser(Integer userId, Integer officeId, Integer regionID) throws CGSystemException,CGBusinessException;
	
	public List<OfficeDO> getOfficeByUserType(String officeType, Integer userId, Integer officeId, Integer cityId) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCitiesByRegionForRhoUser(Integer userId, Integer officeId, Integer regionID) throws CGSystemException,CGBusinessException;
	
	
   public List<StockStandardTypeDO> getStandardTypeForLcCod(String typeName) throws CGSystemException;

	
	public List<LcCodReportAliasTO> getCustomerByRegionAndProduct(List<Integer> regionId,List<String> prodSeries) throws CGSystemException;
	
public List<OfficeDO> getOfficeForMultipleCitiesForBOUser(Integer[] cityId, Integer userID) throws CGSystemException,CGBusinessException;
	
	public List<OfficeDO> getOfficeForMultipleCitiesUserType(String officeType, Integer userId, Integer officeId, Integer[] cityId) throws CGSystemException,CGBusinessException;
	
	/**
	 * @param userId
	 * @param cityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getOfficesByMultipleCityIdForReport(Integer[] cityId ) throws CGBusinessException, CGSystemException;
	
	public List<CityDO> getCityByMultipleRegionForBranchUser(Integer userId, Integer[] regionId) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCitiesByMultipleRegionForHubUser(Integer userId, Integer officeId, Integer[] regionID) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCitiesByMultipleRegionForRhoUser(Integer userId, Integer officeId, Integer[] regionID) throws CGSystemException,CGBusinessException;
	
	public List<CityDO> getCitiesByMultipleRegionIds(Integer[] regionID) throws CGSystemException,CGBusinessException;
	
	public List<CategoryReportAliasTO> getCategory() throws CGSystemException,CGBusinessException;
	
	public List<LoadLotTO> getLoad() throws CGSystemException,CGBusinessException;
	/**
	 * @param userId
	 * @param officeId
	 * @param officeId2 
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getReportingOfficesForRhoUser(Integer userId, Integer cityId, Integer officeId)throws CGSystemException;
	/**
	 * @param officeIds
	 * @param rateProduct
	 * @return
	 */
	public List<CustomerDO> getCustomerByBranchAndRateProductCategory(
			Integer[] officeIds, Integer rateProduct)throws CGSystemException;
	
	/**
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 */
	public List<CustomerDO> getCustomersByOfficeIds(Integer[] officeId)
			throws CGSystemException;
	
	/**
	 * To get customer by office ids for report filters
	 * 
	 * @param officeIds
	 * @param regionId
	 * @return customerDOs
	 * @throws CGSystemException
	 */
	public List<CustomerDO> getCustomersByOffIds(Integer[] officeIds,
			Integer regionId) throws CGSystemException;
	
	/**
	 * @param productIds
	 * @param stationId
	 * @return
	 * @throws CGSystemException
	 */
	public List<CustomerDO> getCustomerByStationAndProduct(
			Integer[] productIds, Integer stationId)throws CGSystemException;
	List<CustomerDO> getCustomerByStation(Integer stationId)
			throws CGSystemException;
	List<ProductDO> getProductByCustomers(Integer[] customerIds)
			throws CGSystemException;
	
	public List<LiabilityDO> getchequeNumbers(Integer customerId,Date fromDate,Date toDate)
			throws CGBusinessException, CGSystemException;

	public List<CustomerDO> getCustomersByContractBranches(Integer[] officeIds, Integer cityId)
			throws CGSystemException;
	
	List<TransportModeDO> getAllTransportModeList() throws CGSystemException;
	
	List<ServiceByTypeDO> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGSystemException;
	public List<CityDO> getAllCities() throws CGSystemException,CGBusinessException;
	
	public List<ConsignmentCustomerTO> getCustomersByContractBranchesForConsignmentDetails(Integer[] officeIds,
			Integer[] cityId) throws CGSystemException;
	List<CustomerDO> getCustomerByStation(Integer[] stationId)
			throws CGSystemException;
	
	List<RateRevisionDO> getCustomerByStation1(List<Integer> stationId)
			throws CGSystemException;
	
	public List<ClientGainedWrapperDO> getSalesPersonsForClientGained(Integer officeId,
			Integer cityId, Integer regionId) throws CGSystemException;
	public List<ClientGainedWrapperDO> getSalesPersonsForProspects(
			Integer officeId, Integer cityId, Integer regionId)
			throws CGSystemException;
	
	
	public List<ReportTypeTO> getReportType() throws CGSystemException,CGBusinessException;
	public List<ItemTypeDO> getItemTypeListForUsed() throws CGSystemException,CGBusinessException;
	public List<PriorityTypeTO> getPriorityType()throws CGSystemException,CGBusinessException;
	List<LiabilityTO> getchequeNumbersByRegion(Integer regionId, Date fromDate,
			Date toDate) throws CGBusinessException, CGSystemException;
}
