package com.ff.ud.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.AllFfMastDO;
import com.ff.ud.domain.CityDO;
import com.ff.ud.domain.CustomerDO;
import com.ff.ud.domain.FinancialProductDO;
import com.ff.ud.domain.OfficeDO;
import com.ff.ud.domain.ProductDO;
import com.ff.ud.domain.RegionDO;
import com.ff.ud.dto.AssignOfficeUserDTO;
import com.ff.ud.dto.ChequeDetailsDTO;
import com.ff.ud.dto.CityDTO;
import com.ff.ud.dto.CustomerDTO;
import com.ff.ud.dto.MonthAndDateDTO;
import com.ff.ud.dto.MonthWiseDateDTO;
import com.ff.ud.dto.OfficeCodeManagerDTO;
import com.ff.ud.dto.OfficeDTO;
import com.ff.ud.dto.OfficeRightsDTO;
import com.ff.ud.dto.PinckUpDeliveryDTO;
import com.ff.ud.dto.ProductDTO;
import com.ff.ud.dto.RegionDTO;
import com.ff.ud.dto.VendorCodeDTO;
import com.ff.ud.dto.VendorDetailsDTO;
import com.ff.ud.dto.vendorDTO;
import com.ff.ud.utils.StringUtils;

@Component
@Service
public   class DispatchAndReceiveReportServiceImpl implements DispatchAndReceiveReportService{

	static Logger logger = Logger.getLogger(DispatchAndReceiveReportServiceImpl.class.getName());
	
	@Autowired
	private MasterDAO masterDAO;
	
	
	@Override
	public List<RegionDO> getRegion() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getRegion :: -------->Start");
		List<RegionDTO> regionlist = masterDAO.getRegionAlphabet();
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (RegionDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setRegionName(regionDto.getRegionName());
			regionDolist.add(regionDo);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getRegion :: -------->End");
		return regionDolist;
	}


	@Override
	public List<CityDO> getCity() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCity :: -------->Start");
			List<CityDO> cityList = new ArrayList<CityDO>();
			CityDO cityDo = null;
			List<CityDTO> cityToList = masterDAO.getCityByRegion();
			for(CityDTO cityTO  : cityToList) {
				cityDo = new CityDO();
				cityDo.setCityCode(cityTO.getCityCode());
				cityDo.setCityName(cityTO.getCityName());
				cityDo.setRegCode(cityTO.getRegCode());
				cityDo.setCityId(cityTO.getCityId());
				
				cityList.add(cityDo);

			}
			logger.info("DispatchAndReceiveReportServiceImpl :: getCity :: -------->End");
			return cityList;
	}


	@Override
	public List<ProductDTO> getProduct() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getProduct :: -------->Start");
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		ProductDTO productDTO = null;
		List<ProductDO> productToList = masterDAO.getProductSeries();
		logger.info("DispatchAndReceiveReportServiceImpl :: getProduct :: -------->"+productToList);
		for(ProductDO product1DO  : productToList) {
			productDTO = new ProductDTO();
			productDTO.setProductSerices(product1DO.getConsgSeries());
			productDTO.setProductName(product1DO.getProductName());
			productDTO.setProductId(product1DO.getProductId());
			productList.add(productDTO);

		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCity :: -------->End");
		return productList;
}

	/*
	 * (non-Javadoc) New method Added Reverse Logistic Client Request Report
	 * @see com.ff.ud.service.DispatchAndReceiveReportService#getClient()
	 * Reverse Logistic Client Request Report
	 */
	@Override
	public List<CustomerDTO> getClientList() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getBussinessName :: -------->Start");
		List<CustomerDTO> clientList = new ArrayList<CustomerDTO>();
		CustomerDTO customerDTO = null;
		List<CustomerDO> clientToList = masterDAO.getBussinessName();
		logger.info("DispatchAndReceiveReportServiceImpl :: getBussinessName :: -------->"+clientToList);
		for(CustomerDO customerDOs  : clientToList) {
			customerDTO = new CustomerDTO();
			customerDTO.setBussinessName(customerDOs.getCustomerName());
			customerDTO.setCustomerId(String.valueOf(customerDOs.getCustomerId()));
			clientList.add(customerDTO);
			System.out.println("client List customer Id "+clientList);

		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerName(Client Name) :: -------->End");
		return clientList;
}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ff.ud.service.DispatchAndReceiveReportService#getOfficeType(java.lang.String)
	 * Reverse Logistic Summary Report
	 
	@Override
	public List<CustomerDTO> getClientSummaryList() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getClientSummaryList :: -------->Start");
		List<CustomerDTO> clientSummaryList = new ArrayList<CustomerDTO>();
		CustomerDTO customerDTO = null;
		List<CustomerDO> clientSummaryToList = masterDAO.getClientSummaryList();
		logger.info("DispatchAndReceiveReportServiceImpl :: getClientSummaryList :: -------->"+clientSummaryToList);
		for(CustomerDO customerDOs  : clientSummaryToList) {
			customerDTO = new CustomerDTO();
			customerDTO.setBussinessName(customerDOs.getCustomerName());
			customerDTO.setCustomerId(String.valueOf(customerDOs.getCustomerId()));
			clientSummaryList.add(customerDTO);
			System.out.println("client List customer Id "+clientSummaryList);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerName(Client Name) :: -------->End");
		return clientSummaryList;
	}*/
	
	
	
	@Override
	public List<RegionDO> getOfficeType(String officeType) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getOfficeType :: -------->Start");
		List<OfficeDO> regionlist = masterDAO.getOfficeType(officeType);;
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDO regionDto : regionlist) {
			RegionDO cityDO = new RegionDO();
			cityDO.setRegionCode(String.valueOf(regionDto.getOfficeType()));
			regionDolist.add(cityDO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getOfficeType :: -------->End");
		return regionDolist;
	}


	@Override
	public List<RegionDO> getCityNames(String cityName) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityNames :: -------->Start");	
		List<OfficeDTO> regionlist = masterDAO.getRegionNames(cityName);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setCityId(String.valueOf(regionDto.getCityId()));
			regionDolist.add(regionDo);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityNames :: -------->End");
		return regionDolist;
	}


	@Override
	public List<RegionDO> getRegionid(String region) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getRegionid :: -------->Start");
		List<RegionDTO> regionlist = masterDAO.getRegionAlphabetid(region);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (RegionDTO regionDto : regionlist) {
			RegionDO regionDo = new RegionDO();
			regionDo.setRegionCode(regionDto.getRegionCode());
			regionDo.setRegionName(regionDto.getRegionName());
			regionDolist.add(regionDo);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getRegionid :: -------->End");
		return regionDolist;
	}


	@Override
	public List<CityDO> getCityIdForRHOList(String cityCode) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityIdForRHOList :: -------->Start");
		List<CityDO> regionlist = masterDAO.getCityIdForRHOList(cityCode);
		Set<CityDO> regionDolist=new HashSet<CityDO>();
		for (CityDO regionDto : regionlist) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(regionDto.getCityId());
			cityDO.setCityName(regionDto.getCityName());
			regionDolist.add(cityDO);
		}
		List<CityDO> regionDolist1 = new ArrayList<CityDO>(regionDolist);
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityIdForRHOList :: -------->End");
		return regionDolist1;
	}


	@Override
	public List<CityDO> getCityNameForBranch(String cityId) {
		List<CityDO> regionlist = masterDAO.getCityNameForBranch(cityId);
		List<CityDO> regionDolist = new ArrayList<CityDO>();
		for (CityDO regionDto : regionlist) {
			CityDO cityDO = new CityDO();
			cityDO.setCityName(regionDto.getCityName());
			cityDO.setCityId(regionDto.getCityId());
			System.out.println("office name :"+cityDO.getCityName());
			regionDolist.add(cityDO);
		}

		return regionDolist;
	}


	@Override
	public List<RegionDO> getBranchNameWithBranchCode(String cityName) {
		List<OfficeDO> regionlist = masterDAO.getBranchCode(cityName);
		List<RegionDO> regionDolist = new ArrayList<RegionDO>();
		for (OfficeDO regionDto : regionlist) {
			RegionDO regionDO = new RegionDO();
			regionDO.setRegionCode(regionDto.getOfficeCode());
			regionDO.setRegionName(regionDto.getOfficeName());
			regionDolist.add(regionDO);
		}

		return regionDolist;
	}


	@Override
	public List<CityDTO> getCityCodeFromAllRegion() {
		return masterDAO.getCityCodeFromAllRegion() ;
	}


	@Override
	public List<AllFfMastDO> getOfficesByAll(Integer regCode) {
		return masterDAO.getOfficesByALL(regCode);
	}
	/*
	 * this method only for hub
	 * 
	 */
	public List<OfficeDO> getOfficesByAllHubs(Integer regCode) {
		return masterDAO.getOfficesByCityIdForHub(regCode);
	}

	@Override
	public List<CityDTO> getCityCodeByStation(String regCode,String cityName) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityCodeByStation :: -------->Start");
		List<OfficeDO> regionlist1 = masterDAO.getCityCodeByStation(regCode,cityName);
		Integer cityCode=null;
		for(OfficeDO cityId :regionlist1){
			cityCode=cityId.getCityId();
		}
		List<CityDO> regionlist = masterDAO.getCityIdByRegionStation(cityCode);
		List<CityDTO> regionDolist = new ArrayList<CityDTO>();
		for (CityDO regionDto : regionlist) {
			CityDTO cityDTO = new CityDTO();
			cityDTO.setCityName(regionDto.getCityName());
			cityDTO.setCityId(regionDto.getCityId());
			System.out.println("office name :"+cityDTO.getCityName());
			regionDolist.add(cityDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCityCodeByStation :: -------->Start");

		return regionDolist;
	}
		
	
	@SuppressWarnings("unused")
	@Override
	public List<CustomerDTO> getCustomerNameFromOFficeCode(String officeCode) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCode :: -------->Start");
		List<OfficeDO>officeId=null;
		Integer offId=null;
		List<String> officeIdForHub=null;
		List<PinckUpDeliveryDTO> customerIdList=null;
		
		List<PinckUpDeliveryDTO>hubId=masterDAO.getHubIdbyOfficeCode(officeCode);
		List<PinckUpDeliveryDTO>rhoId=masterDAO.getRHOIdbyOfficeCode(officeCode);
		if(StringUtils.isEmptyColletion(rhoId)){
			for(PinckUpDeliveryDTO officeId1 :hubId){
				offId=officeId1.getOfficeId();
			}
       	    customerIdList=masterDAO.getHubIdFormCustomerNames(offId,officeCode);
		}else if(StringUtils.isEmptyColletion(customerIdList)){
			for(PinckUpDeliveryDTO officeId2 :rhoId){
				offId=officeId2.getOfficeId();
			}
			 customerIdList=masterDAO.getRHODetailsByCustomerNames(offId);
			
		}
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCode :: -------->End");
		return customerListDTO;
		
	}


	@Override
	public List<OfficeDO> getUserAssignedOffices(Integer userId) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getUserAssignedOffices :: -------->Start");
		List<AssignOfficeUserDTO> officeList =  masterDAO.getUserAssignedOffices(userId);
		
		
		List<OfficeDO> officeListDTO=new ArrayList<OfficeDO>();
		
		for(AssignOfficeUserDTO assigOfficeList :officeList){
			
			OfficeDO officeDTO=new OfficeDO();
			officeDTO.setOfficeId(Integer.valueOf(assigOfficeList.getOfficeId()));
			officeDTO.setOfficeCode(assigOfficeList.getOfficeCode());
			officeDTO.setOfficeName(assigOfficeList.getOfficeName());
			officeListDTO.add(officeDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getUserAssignedOffices :: -------->End");
		return officeListDTO;
	}


	@Override
	public List<AllFfMastDO> getOfficeCodeForAllOriginOffice(Integer regionId) {
               return masterDAO.getOfficeCodeForAllOriginOffice(regionId);
	}


	@Override
	public Integer getRegionIdForRegionCode(String cityName) {
		
		List<AllFfMastDO> regionList =masterDAO.getRegionIdForRegionCode(cityName);
		
		Integer regionCodeInteger=null;
		for(AllFfMastDO regionId:regionList){
			regionCodeInteger=regionId.getMappedToRegion();
		}
		
		/*List<RegionDO> regionDTO=new ArrayList<RegionDO>();
		
        for(AllFfMastDO regionCodeList :regionList){
			
        	RegionDO regionCodeNamesDTO=new RegionDO();
        	regionCodeNamesDTO.setRegionCode(String.valueOf(regionCodeList.getMappedToRegion()));
        	
        	regionDTO.add(regionCodeNamesDTO);
		}*/
		
		return  regionCodeInteger;
	}


	@Override
	public List<AllFfMastDO> getOfficesByAllHUb(Integer regCode) {
		return masterDAO.getOfficesByAllHUb(regCode);
	}
	
	@Override
	public List<OfficeDO> getOfficesByCityIdForHubNames(Integer regCode) {
		return masterDAO.getOfficesByCityIdForHubNames(regCode);
	}


	@Override
	public List<OfficeDO> getOfficesByAllHUbByCityCode(Integer cityId) {
	
		return masterDAO.getOfficesByCityIdForHubNames(cityId);
	}




	@Override
	public List<OfficeDO> getOfficeNamesByCustomerId(String customerId) {
	/*	//List<UpsOfficeCodeDO>OfficeCodeList =masterDAO.getOfficeNamesByCustomerId(customerId);
		
		List<AllFfMastDO> officeDTO=new ArrayList<AllFfMastDO>();
		for(UpsOfficeCodeDO officeCode:OfficeCodeList){
		       AllFfMastDO allOfficode =new AllFfMastDO();
		       allOfficode.setOfficeCode(officeCode.getOfficeId());
		       officeDTO.add(allOfficode);
		}
		
		
		List<OfficeDO> OfficeList=new ArrayList<OfficeDO>();
		for(AllFfMastDO cityCodeFind :officeDTO){
			String officeCode=cityCodeFind.getOfficeCode();
			//List<OfficeDO> CityNameList =masterDAO.getCityNamesByOfficeId(officeCode);
		}
		*/
		
		return null;
	}


	@Override
	public List<CityDTO> getBranchAllocationForManager(Integer userId) {
		logger.info("getBranchAllocationForManager :: stated");
		List<OfficeRightsDTO> rightList=null;
		rightList =masterDAO.getBranchAllocationForManager(userId);
		List<CityDTO> rightOfofficeDTO=new ArrayList<CityDTO>();
		
		for(OfficeRightsDTO rightForUser :rightList){
			CityDTO userCitys=new CityDTO();
			userCitys.setCityId(String.valueOf(rightForUser.getCityId()));
			userCitys.setCityName(rightForUser.getCityName());
			rightOfofficeDTO.add(userCitys);
			
		}
		logger.info("getBranchAllocationForManager :: end");
		return rightOfofficeDTO;
	}

	@Override
	public List<OfficeDO> getOfficeNamesForManager(Integer userId) {
		logger.info("getBranchAllocationForManager :: stated");
		List<OfficeCodeManagerDTO>
		officeList =masterDAO.getOfficeNamesForManager(userId);
		List<OfficeDO> rightOfofficeDTO=new ArrayList<OfficeDO>();
		
		for(OfficeCodeManagerDTO officeNames :officeList){
			OfficeDO officeNamesList=new OfficeDO();
			officeNamesList.setOfficeCode(officeNames.getOfficeCode());
			officeNamesList.setOfficeName(officeNames.getOfficeName());
			rightOfofficeDTO.add(officeNamesList);
			
		}
		
		logger.info("getBranchAllocationForManager :: end");
		return rightOfofficeDTO;
	}
	@Override
	public List<CustomerDTO> getCustomerCodeByNames() {
		List<CustomerDO>custList=masterDAO.getCustomerCodeByNames();
		
		List<CustomerDTO> custNames=new ArrayList<CustomerDTO>();
		
		for(CustomerDO custmerList:custList){
			CustomerDTO custNameList=new CustomerDTO();
			custNameList.setCustomerId(String.valueOf(custmerList.getCustomerId()));
			custNameList.setBussinessName(custmerList.getCustomerName());
			custNames.add(custNameList);
		}
		return custNames;
	}
/*
 *This is use for BA Customer
 */
	
	@Override
	public List<CustomerDTO> getBACustomerNameFromOFficeCode(String officeCode) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCode :: -------->Start");
		List<OfficeDO>officeId= masterDAO.getOfficeIdFromOfficeCode(officeCode);
		Integer offId=null;
		for(OfficeDO officeId1 :officeId){
			offId=officeId1.getOfficeId();
		}
		List<CustomerDO> customerList=masterDAO.getBACustomerName(offId);
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(CustomerDO customerdo:customerList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getCustomerName());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCode :: -------->End");
		return customerListDTO;
	}


	@Override
	public List<CustomerDTO> getCustomerNameFromOFficeCodeForAll(String allValue) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCodeForAll :: -------->Start");
		List<PinckUpDeliveryDTO> customerIdList=masterDAO.getCustomerIdFromPickUpLocationForAll(allValue);
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCodeForAll :: -------->End");
		return customerListDTO;
	}
	
	@Override
	public List<CustomerDTO> getCustomerNameByRegion(String allValesForRegion) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameByRegion :: -------->Start");
		List<PinckUpDeliveryDTO> customerIdList=masterDAO.getCustomerNameByRegion(allValesForRegion);
		
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameByRegion :: -------->End");
		return customerListDTO;
	}


	@Override
	public List<VendorCodeDTO> getVendorCustomerType() {
		List<VendorDetailsDTO> vendorDetails=masterDAO.getVendorCustomerType();
		List<VendorCodeDTO> vendorList=new ArrayList<VendorCodeDTO>();
		
		for(VendorDetailsDTO vedor:vendorDetails){
			VendorCodeDTO vendorCode=new VendorCodeDTO();
			vendorCode.setVendorId(String.valueOf(vedor.getVendorTypeId()));
			vendorCode.setVendorName(vedor.getVendorTypeName());
			vendorList.add(vendorCode);
			
		}
		
		return vendorList;
	}


	@Override
	public List<VendorCodeDTO> getVendorCodeDetailsByVendorId(String vendorId,String officeCode) {
		
		logger.info("DispatchAndReceiveReportServiceImpl :: getVendorCodeDetailsByVendorId :: -------->Start");
		List<vendorDTO> customerIdList=masterDAO.getVendorDetailsByVendorId(vendorId,officeCode);
		//List<CustomerDO> customerList=masterDAO.getCustomerName(offId);
		List<VendorCodeDTO> customerListDTO=new ArrayList<VendorCodeDTO>();
		
		for(vendorDTO customerdo:customerIdList){
			VendorCodeDTO customerDTO=new VendorCodeDTO();
			customerDTO.setVendorCode(customerdo.getVendorCode());
			customerDTO.setVendorId(String.valueOf(customerdo.getVendorId()));
			customerDTO.setVendorName(customerdo.getVendorName()+"-"+customerdo.getVendorCode());
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getVendorCodeDetailsByVendorId :: -------->End");
		return customerListDTO;
	}
	@Override
	public List<CustomerDTO> getCustomerDetailsByProductId(String productId,String officeCode){
		
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerDetailsByProductId :: -------->Start");
		
		List<PinckUpDeliveryDTO> customeridDTO=masterDAO.getCutomerDetailsproductId(productId, officeCode);
        List<CustomerDTO> customerList=new ArrayList<CustomerDTO>();
        
        for(PinckUpDeliveryDTO customerDo1:customeridDTO)
        {
        	CustomerDTO customerdto=new CustomerDTO();
        	customerdto.setCustomerId(String.valueOf(customerDo1.getCustomerId()));
        	customerdto.setBussinessName(customerDo1.getBusinessName()+"-"+customerDo1.getCustomerCode());
        	
        	
        	
        	customerList.add(customerdto);
        }
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerDetailsByProductId :: -------->Start");
		
		return customerList;
		
	}
	
	
	@Override
	public List<ProductDTO> getFinacialProduct() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getProduct :: -------->Start");
		List<ProductDTO> finacialProductList = new ArrayList<ProductDTO>();
		ProductDTO productDTO = null;
		List<FinancialProductDO> productToList = masterDAO.getFinacialProductName();
		logger.info("DispatchAndReceiveReportServiceImpl :: getProduct :: -------->"+productToList);
		for(FinancialProductDO product1DO  : productToList) {
			productDTO = new ProductDTO();
			productDTO.setProductName(product1DO.getProductName());
			productDTO.setProductId(product1DO.getProductId());
			finacialProductList.add(productDTO);

		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCity :: -------->End");
		return finacialProductList;
}


	@Override
	public List<VendorCodeDTO> getEmployeeNameByFiledStaff(String vendorId,String officeCode) {
    logger.info("DispatchAndReceiveReportServiceImpl :: getEmployeeNameByFiledStaff :: -------->Start");
		
		List<PinckUpDeliveryDTO> empList=masterDAO.getEmployeeNameByFiledStaff(vendorId, officeCode);
        List<VendorCodeDTO> emplList=new ArrayList<VendorCodeDTO>();
        
        for(PinckUpDeliveryDTO customerDo1:empList)
        {
        	VendorCodeDTO customerdto=new VendorCodeDTO();
        	customerdto.setVendorId(String.valueOf(customerDo1.getOfficeId()));
        	customerdto.setVendorName(customerDo1.getCustomer()+" "+customerDo1.getBusinessName());
        	
        	emplList.add(customerdto);
        }
		logger.info("DispatchAndReceiveReportServiceImpl :: getEmployeeNameByFiledStaff :: -------->End");
		
		return emplList;
	}


	@Override
	public List<VendorCodeDTO> getAllBranchForVendor(String vendorId,String officeCode) {
		 logger.info("DispatchAndReceiveReportServiceImpl :: getAllBranchForVendor :: -------->Start");
			
			List<vendorDTO> empList=masterDAO.getAllBranchForVendor(vendorId, officeCode);
	        List<VendorCodeDTO> emplList=new ArrayList<VendorCodeDTO>();
	        
	        for(vendorDTO customerDo1:empList)
	        {
	        	VendorCodeDTO customerDTO=new VendorCodeDTO();
				customerDTO.setVendorCode(customerDo1.getVendorCode());
				customerDTO.setVendorId(String.valueOf(customerDo1.getVendorId()));
				customerDTO.setVendorName(customerDo1.getVendorName()+"-"+customerDo1.getVendorCode());
				emplList.add(customerDTO);
	        }
			logger.info("DispatchAndReceiveReportServiceImpl :: getAllBranchForVendor :: -------->End");
			
			return emplList;
	}


	@Override
	public List<AllFfMastDO> getAllRegionAllOffice() {
		return masterDAO.getAllRegionAllOffice();
	}


	@Override
	public List<CustomerDTO> getForAllLCCODCustomer() {
		logger.info("DispatchAndReceiveReportServiceImpl :: getForAllLCCODCustomer :: -------->Start");
		List<PinckUpDeliveryDTO> customerIdList=masterDAO.getForAllLCCODCustomer();
		
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getForAllLCCODCustomer :: -------->End");
		return customerListDTO;
	}


	@Override
	public List<CustomerDTO> getLCCODForBranch(String officeCode) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getLCCODForBranch :: -------->Start");
		List<OfficeDO>officeId=null;
		Integer offId=null;
		List<String> officeIdForHub=null;
		List<PinckUpDeliveryDTO> customerIdList=null;
		
		List<PinckUpDeliveryDTO>hubId=masterDAO.getHubIdbyOfficeCode(officeCode);
		List<PinckUpDeliveryDTO>rhoId=masterDAO.getRHOIdbyOfficeCode(officeCode);
		if(StringUtils.isEmptyColletion(rhoId)){
			for(PinckUpDeliveryDTO officeId1 :hubId){
				offId=officeId1.getOfficeId();
			}
       	    customerIdList=masterDAO.getLcCodHUbDetails(offId,officeCode);
		}else if(StringUtils.isEmptyColletion(customerIdList)){
			for(PinckUpDeliveryDTO officeId2 :rhoId){
				offId=officeId2.getOfficeId();
			}
			 customerIdList=masterDAO.getLCCODRHOByCustomerNames(offId);
			
		}
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getLCCODForBranch :: -------->End");
		return customerListDTO;
	}


	@Override
	public List<CustomerDTO> getCustomerNameForAllOffice(String officeCode) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameForAllOffice :: -------->Start");
		List<PinckUpDeliveryDTO> customerIdList=masterDAO.getCustomerNamesForAllLCCOD(officeCode);
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNameFromOFficeCodeForAll :: -------->End");
		return customerListDTO;
	}


	@Override
	public List<MonthWiseDateDTO> getMonthWiseDateFormate(String values) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getMonthWiseDateFormate :: -------->Start");
		
		List<MonthAndDateDTO> dateList=masterDAO.getMonthWiseDateFormate(values);
        List<MonthWiseDateDTO> list=new ArrayList<MonthWiseDateDTO>();
        
        for(MonthAndDateDTO srtDate:dateList)
        {
        	MonthWiseDateDTO dto =new MonthWiseDateDTO();
        	dto.setStartdate(srtDate.getStartdate());
        	
        	list.add(dto);
        }
		logger.info("DispatchAndReceiveReportServiceImpl :: getMonthWiseDateFormate :: -------->End");
		return list;
	}


	@Override
	public List<MonthWiseDateDTO> getMonthWiseDateEndDay(String values) {
    logger.info("DispatchAndReceiveReportServiceImpl :: getMonthWiseDateEndDay :: -------->Start");
		
       String startValues=values.substring(5, 7);
       //String dateValues=values.substring(8, 10);
    
    
		List<MonthAndDateDTO> dateList=masterDAO.getMonthWiseDateEndDay(startValues);
        List<MonthWiseDateDTO> list=new ArrayList<MonthWiseDateDTO>();
        
        for(MonthAndDateDTO endDate:dateList)
        {
        	MonthWiseDateDTO dto =new MonthWiseDateDTO();
        	//dto.setStartdate(srtDate.getStartdate());
        	dto.setEnddate(endDate.getEnddate());
        	
        	list.add(dto);
        }
		logger.info("DispatchAndReceiveReportServiceImpl :: getMonthWiseDateEndDay :: -------->End");
		return list;
	}


	@Override
	public List<CustomerDTO> getCustomerNamesForLCCODToPayFromRegion(String region) {
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNamesForLCCODToPayFromRegion :: -------->Start");
		List<PinckUpDeliveryDTO> customerIdList=masterDAO.getCustomerNamesForLCCODToPayFromRegion(region);
		List<CustomerDTO> customerListDTO=new ArrayList<CustomerDTO>();
		
		for(PinckUpDeliveryDTO customerdo:customerIdList){
			CustomerDTO customerDTO=new CustomerDTO();
			customerDTO.setBussinessName(customerdo.getBusinessName()+"-"+customerdo.getCustomerCode());
			customerDTO.setCustomerId(String.valueOf(customerdo.getCustomerId()));
			customerListDTO.add(customerDTO);
		}
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerNamesForLCCODToPayFromRegion :: -------->End");
		return customerListDTO;
	}
	
	public List<CustomerDTO> getCustomerListByRegionId(String regionId){
		List<CustomerDTO> customerList= new ArrayList<CustomerDTO>();
logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerListByRegionId :: -------->Start");
		List<PinckUpDeliveryDTO> customeridDTO=masterDAO.getCustomerListByRegionId(regionId);
        
        if(null != customeridDTO)
        for(PinckUpDeliveryDTO customerDo1:customeridDTO)
        {
        	CustomerDTO customerdto=new CustomerDTO();
        	customerdto.setCustomerId(String.valueOf(customerDo1.getCustomerId()));
        	customerdto.setBussinessName(customerDo1.getBusinessName()+"-"+customerDo1.getCustomerCode());
        	customerList.add(customerdto);
        }
		logger.info("DispatchAndReceiveReportServiceImpl :: getCustomerListByRegionId :: -------->End");
		
		return customerList;
	}
	public List<ChequeDetailsDTO> getChequeNumbersByCustomerId(String customerId,String fromDate,String toDate){
		
		List<ChequeDetailsDTO> ChequeDetailsDTOList=null;
		ChequeDetailsDTOList = masterDAO.getChequeNumbersByCustomerId(customerId,fromDate,toDate);
		
		
		return ChequeDetailsDTOList;
	}

}
