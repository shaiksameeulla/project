package com.ff.report.common.service;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignmentCustomerTO;
import com.ff.business.CustomerTO;
import com.ff.domain.business.RateRevisionDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.LcCodReportAliasTO;
import com.ff.report.PriorityTypeTO;
import com.ff.report.ReportTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.mec.LiabilityTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;


/**
 * @author khassan
 * 
 */
public interface CommonReportService {

	/**
	 * @param cityId
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getOfficesByCityIdForReport(Integer cityId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the regions.
	 * 
	 * @return the regions
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the products.
	 * 
	 * @return the products
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ProductTO> getProducts() throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the products.
	 * 
	 * @return the products
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<RateProductCategoryTO> getRateProdcuts() throws CGBusinessException, CGSystemException;

	// getStations
	/**
	 * Gets the cities by region id.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the cities by region id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllHubsByCityID(Integer cityId)
			throws CGBusinessException, CGSystemException;
	
	List<ItemTypeTO> getItemTypeList() throws CGSystemException,CGBusinessException;
	List<CustomerTO> getCustomersByOfficeId(final Integer officeId)
			throws CGBusinessException, CGSystemException;
	public List<EmployeeTO> getEmployeesByOfficeIdForReport(Integer cityId) throws CGSystemException, CGBusinessException;
	public List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType) throws CGSystemException;
	public List<RateIndustryTypeTO> getNatureOfBusinesses() throws CGSystemException;
	
	List<VobSlabTO> getSlabList(Integer productId) throws CGBusinessException, CGSystemException;
	public Long getworkingDaysFromHoliday(Integer regionId,String fromDate)throws CGBusinessException,CGSystemException;
	
	/**
	 * @param cityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CityTO> getCityByCityID(Integer cityId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param cityId
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getOfficecByCityIDAndUserIDForReport(Integer userId, Integer cityId ) throws CGBusinessException, CGSystemException;
	
	public List<SectorTO> getOriginSectors() throws CGSystemException;	
	public List<RateProductCategoryTO> getProductsByCustomer(final Integer customerId)
			throws CGBusinessException, CGSystemException;
	public List<CityTO> getCitiesByRegionIds(String regionIds)
			throws CGSystemException, CGBusinessException;
	public List<OfficeTO> getOfficesByCityIds(String cityIds)
			throws CGSystemException, CGBusinessException;
	/**
	 * Get the months .
	 * 
	 * * @return the month-year format
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List getMonthList()	throws CGSystemException,CGBusinessException;	
	
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<OfficeTO> getOfficeForBOUser(Integer cityId, Integer userID) throws CGSystemException,CGBusinessException;	
	
	/**
	 * @param userId
	 * @param officeId 
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<RegionTO> getAllRegionForBoUser(Integer userId, Integer officeId) throws CGSystemException,CGBusinessException;
	
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCityByRegionForBranchUser(Integer userId, Integer regionId) throws CGSystemException,CGBusinessException;
	
	/**
	 * @param userId
	 * @param regionId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<RegionTO> getAllRegionByUserType(Integer userId, Integer officeId, String officeType) throws CGSystemException,CGBusinessException;
	
	
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCitiesByRegionForHubUser(Integer userId, Integer officeId, Integer regionID) throws CGSystemException,CGBusinessException;
	
	/**
	 * @param cityId
	 * @param userID
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<OfficeTO> getOfficeByUserType(String officeType, Integer userId, Integer officeId, Integer cityId) throws CGSystemException,CGBusinessException;
	
	/**
	 * @param userId
	 * @param officeId
	 * @param regionID
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCitiesByRegionForRhoUser(Integer userId, Integer officeId, Integer regionID) throws CGSystemException,CGBusinessException;
	
	public List<StockStandardTypeTO> getStandardTypeForLcCod(String typeName) throws CGBusinessException,
	CGSystemException;
	
	public List<LcCodReportAliasTO> getCustomerByRegionAndProduct(List<Integer> regionId,List<String> prodSeries)throws CGBusinessException,
	CGSystemException;
	
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<OfficeTO> getOfficeForMultipleCitiesForBOUser(Integer[] cityId, Integer userID) throws CGSystemException,CGBusinessException;	
	/**
	 * @param cityId
	 * @param userID
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<OfficeTO> getOfficeForMultipleCitiesUserType(String officeType, Integer userId, Integer officeId, Integer[] cityId) throws CGSystemException,CGBusinessException;
	
	/**
	 * @param cityId
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getOfficecByMultipleCityIDAndUserIDForReport(Integer[] cityId ) throws CGBusinessException, CGSystemException;
	
	
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCityByMultipleRegionForBranchUser(Integer userId, Integer[] regionId) throws CGSystemException,CGBusinessException;
	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCitiesByMultipleRegionForHubUser(Integer userId, Integer officeId, Integer[] regionID) throws CGSystemException,CGBusinessException;
	/**
	 * @param userId
	 * @param officeId
	 * @param regionID
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CityTO> getCitiesByMultipleRegionForRhoUser(Integer userId, Integer officeId, Integer[] regionID) throws CGSystemException,CGBusinessException;
	/**
	 * Gets the cities by region id.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the cities by region id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByMultipleRegionId(final Integer[] regionId)
			throws CGBusinessException, CGSystemException;
	/**
	 * Gets the consignment type.
	 *
	 * @return the consignment type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentTypeTO> getConsignmentType() throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<CategoryReportAliasTO> getCategory() throws CGBusinessException,CGSystemException;
	
	/**
	 * Gets the load.
	 *
	 * @return the load
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadLotTO> getLoad() throws CGBusinessException,CGSystemException;

	/**
	 * @param string
	 * @param userId
	 * @param officeId
	 * @param cityID
	 * @param officeId 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getReportingOfficesForRhoUser(Integer userId,
			Integer cityID, Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * @param officeIds
	 * @param rateProduct
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CustomerTO> getCustomerByBranchAndRateProductCategory(
			Integer[] officeIds, Integer rateProduct)throws CGBusinessException, CGSystemException;
	
	/**
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CustomerTO> getCustomersByOfficeIds(final Integer[] officeId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * To get customer ids by office ids for report filters
	 * 
	 * @param officeId
	 * @param regionId
	 * @return customerTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CustomerTO> getCustomersByOffIds(final Integer[] officeId,
			Integer regionId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @param productIds
	 * @param station
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CustomerTO> getCustomerByStationAndProduct(
			Integer[] productIds, Integer station)throws CGBusinessException, CGSystemException;

	/**
	 * Gets the standard types by type name.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the standard types by type name
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<StockStandardTypeTO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * 
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<SectorTO> getSectors() throws CGSystemException, CGBusinessException;

	List<CustomerTO> getCustomerByStation(Integer stationId)
			throws CGBusinessException, CGSystemException;

	List<ProductTO> getProductByCustomers(Integer[] customerIds)
			throws CGBusinessException, CGSystemException;
	
	List<CustomerTO> getCustomerByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException;
	
	List<String> getchequeNumbers(Integer customerId,Date fromDate,Date toDate)
			throws CGBusinessException, CGSystemException;

	List<CustomerTO> getCustomersByContractBranches(Integer[] officeIds, Integer cityId)
			throws CGBusinessException, CGSystemException;

	List<LabelValueBean> getAllTransportModeList() throws CGBusinessException,
			CGSystemException;

	List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException,
			CGSystemException;
	
	public List<CustomerTO> getAllCustomerForStockReport(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException;
	
	public List<CustomerTO> getCustomersByOfficeIdForStockReport(Integer officeId,String offcType)
			throws CGSystemException, CGBusinessException;
	
	 public OfficeTO getOfficeDetailsByOfficeId(Integer officeId)throws CGBusinessException, CGSystemException;
	 
	 List<CityTO> getAllCities()
				throws CGBusinessException, CGSystemException;
	 
	 public List<ConsignmentCustomerTO> getCustomersByContractBranchesForConsignmentDetails(Integer[] officeIds,
				Integer[] cityId) throws CGSystemException;
	 
	 List<CustomerTO> getCustomerByStation1(List<Integer> stationNum)
				throws CGBusinessException, CGSystemException;
	 
	 List<RateRevisionDO> getCustomerByStation2(List<Integer> stationNum)
				throws CGBusinessException, CGSystemException;

	public List<CustomerTO> getAllCustomerByLoggedInOffice(Integer officeId,
			String loginOfficeType) throws CGSystemException, CGBusinessException;
	
	
	public List<CGBaseTO> getSalesPersonsForClientGained(Integer officeId,
			Integer cityId, Integer regionId) throws CGBusinessException,
			CGSystemException;

	public List<CGBaseTO> getSalesPersonsForProspects(Integer officeId,
			Integer cityId, Integer regionId) throws CGBusinessException,
			CGSystemException ;
	
	public List<ReportTypeTO> getReportType() throws CGBusinessException, CGSystemException;

	public List<ItemTypeTO> getItemTypeListForUsed()throws CGBusinessException, CGSystemException;
	
	public List<PriorityTypeTO> getPriorityType() throws CGBusinessException, CGSystemException;

	List<String> getchequeNumbersByRegion(Integer regionId, Date fromDate,
			Date toDate) throws CGBusinessException, CGSystemException;
		
	

}