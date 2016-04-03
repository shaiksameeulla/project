package com.ff.ud.service;

import java.util.List;

import com.ff.ud.domain.AllFfMastDO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.ChequeDetailsDTO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.MonthWiseDateDTO;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.dto.VendorCodeDTO;

public interface DispatchAndReceiveReportService {
	
	
	/**
	 * This method returns the list of the region
	 * @return list of region objects
	 */
		public List<RegionDO> getRegion();
		
		/**
		 * This methods returns the city
		 * @return list of cities
		 */
		public List<CityDO> getCity();

		/**
		 * This methods returns the Product series
		 * @return list of Product series
		 */
		public List<ProductDTO> getProduct();
		
		public List<RegionDO> getOfficeType(String officeType);
		
		public List<RegionDO> getCityNames(String cityName);
		
		public List<RegionDO> getRegionid(String region);
		public List<CityDO> getCityIdForRHOList(String cityCode);
		public List<CityDO>getCityNameForBranch(String cityId);

		public List<RegionDO> getBranchNameWithBranchCode(String cityName);

		public List<CityDTO> getCityCodeFromAllRegion();

		public List<AllFfMastDO> getOfficesByAll(Integer regCode);
		public List<AllFfMastDO> getOfficesByAllHUb(Integer regCode);
		
		public List<OfficeDO> getUserAssignedOffices(Integer userId);

		public List<CityDTO> getCityCodeByStation(String regCode,String cityName);
		public List<CustomerDTO> getCustomerNameFromOFficeCode(String officeCode);

		public List<AllFfMastDO> getOfficeCodeForAllOriginOffice(Integer regionId);
		
		public Integer getRegionIdForRegionCode(String cityName);

		public  List<OfficeDO> getOfficesByCityIdForHubNames(Integer cityName);
		public List<OfficeDO> getOfficesByAllHUbByCityCode(Integer cityId);
		/*
		 * this method for only hub
		 */
		public List<OfficeDO> getOfficesByAllHubs(Integer regCode);
		/*
		 * Reverse Logistic Client Request Report
		 */
		public List<CustomerDTO> getClientList();

		public List<OfficeDO> getOfficeNamesByCustomerId(String customerId);

		public List<CustomerDTO> getCustomerCodeByNames();

		public List<CityDTO> getBranchAllocationForManager(Integer userId);

		public List<OfficeDO> getOfficeNamesForManager(Integer userId);
		
		/*
		 * Reverse Logistic Summary Report
		 
		public List<CustomerDTO> getClientSummaryList();*/
		
		/*
		 * This is use for BA customer
		 */
		
		public List<CustomerDTO> getBACustomerNameFromOFficeCode(String officeCode);

		public List<CustomerDTO> getCustomerNameFromOFficeCodeForAll(String allValue);

		public List<VendorCodeDTO> getVendorCustomerType();

		public List<VendorCodeDTO> getVendorCodeDetailsByVendorId(String vendorId,String officeCode);
		/*
		 * this is for get customer from productId and offciecode
		 */
		public List<CustomerDTO> getCustomerDetailsByProductId(String productId,String officeCode);
		/*
		 * This method returs financial product
		 */
		public List<ProductDTO> getFinacialProduct();

		public List<VendorCodeDTO> getEmployeeNameByFiledStaff(String vendorId,
				String officeCode);

		public List<VendorCodeDTO> getAllBranchForVendor(String vendorId,
				String officeCode);

		public List<CustomerDTO> getCustomerNameByRegion(
				String allValesForRegion);

		public List<AllFfMastDO> getAllRegionAllOffice();

		public List<CustomerDTO> getForAllLCCODCustomer();

		public List<CustomerDTO> getLCCODForBranch(String officeCode);

		public List<CustomerDTO> getCustomerNameForAllOffice(String officeCode);

		public List<MonthWiseDateDTO> getMonthWiseDateFormate(String values);

		public List<MonthWiseDateDTO> getMonthWiseDateEndDay(String values);

		public List<CustomerDTO> getCustomerNamesForLCCODToPayFromRegion(String region);
		
		public List<CustomerDTO> getCustomerListByRegionId(String regionId);
		
		public List<ChequeDetailsDTO> getChequeNumbersByCustomerId(String customerId,String fromDate,String toDate);
}
