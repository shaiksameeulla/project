package src.com.dtdc.mdbServices;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.configurableparam.ConfigurableParamsDO;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.StandardTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.agent.AgentAddressDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.airline.AirportDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.CountryDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.office.OpsOfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.ServiceMappingDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.shortapproval.ShortApprovalDO;

// TODO: Auto-generated Javadoc
/**
 * The Class CTBSApplicationDAO.
 *
 * @author Narasimha Rao Kattunga
 */
public interface CTBSApplicationMDBDAO {

	/**
	 * Gets the standard types by parent.
	 *
	 * @param typeName the type name
	 * @param parentType the parent type
	 * @return the standard types by parent
	 * @throws CGSystemException the cG system exception
	 */
	public List<StandardTypeDO> getStandardTypesByParent(String typeName, String parentType) throws CGSystemException;
	
	/**
	 * Gets the standard codes.
	 *
	 * @param typeName the type name
	 * @return the standard codes
	 * @throws CGSystemException the cG system exception
	 */
	public List<StandardTypeDO> getStandardCodes(String typeName) throws CGSystemException;

	/**
	 * Gets the product by id or code.
	 *
	 * @param productId the product id
	 * @param productCode the product code
	 * @return the product by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public ProductDO getProductByIdOrCode(Integer productId, String productCode)
			throws CGSystemException;

	/**
	 * Gets the mode by id.
	 *
	 * @param modeId the mode id
	 * @return the mode by id
	 * @throws CGSystemException the cG system exception
	 */
	public ModeDO getModeById(Integer modeId) throws CGSystemException;

	/**
	 * Gets the modes.
	 *
	 * @return the modes
	 * @throws CGSystemException the cG system exception
	 */
	public List<ModeDO> getModes() throws CGSystemException;
	
	/**
	 * Gets the pin code by id or code.
	 *
	 * @param pinCodeId the pin code id
	 * @param pinCode the pin code
	 * @return the pin code by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public PincodeDO getPinCodeByIdOrCode(Integer pinCodeId,String pinCode) throws CGSystemException;
	
	/**
	 * Gets the city by id or code.
	 *
	 * @param cityId the city id
	 * @param cityCode the city code
	 * @return the city by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public CityDO getCityByIdOrCode(Integer cityId,String cityCode) throws CGSystemException;

	/**
	 * Gets the std handling insts.
	 *
	 * @return the std handling insts
	 * @throws CGSystemException the cG system exception
	 */
	public List<StdHandlingInstDO> getStdHandlingInsts() throws CGSystemException;
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 * @throws CGSystemException the cG system exception
	 */
	public List<ProductDO> getProducts() throws CGSystemException;
	
	/**
	 * Gets the reasons.
	 *
	 * @return the reasons
	 * @throws CGSystemException the cG system exception
	 */
	public List<ReasonDO> getReasons() throws CGSystemException;
	
	/**
	 * Gets the offices.
	 *
	 * @return the offices
	 * @throws CGSystemException the cG system exception
	 */
	public List<OfficeDO> getOffices() throws CGSystemException;
	
	/**
	 * Gets the officesby office id.
	 *
	 * @param officeId the office id
	 * @return the officesby office id
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getOfficesbyOfficeId(Integer officeId) throws CGSystemException;
	
	/**
	 * Gets the officeby office code.
	 *
	 * @param officeCode the office code
	 * @return the officeby office code
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getOfficebyOfficeCode(String officeCode) throws CGSystemException;
	
	/**
	 * Gets the branch by code or id.
	 *
	 * @param branchId the branch id
	 * @param branchCode the branch code
	 * @return the branch by code or id
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getBranchByCodeOrID(Integer branchId, String branchCode) throws CGSystemException;	
	
	/**
	 * Gets the rO by branch office.
	 *
	 * @param branchId the branch id
	 * @return the rO by branch office
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getROByBranchOffice(Integer branchId) throws CGSystemException;
	
	/**
	 * method to load the employee object by id or code.
	 *
	 * @param employeeId the employee id
	 * @param employeeCode the employee code
	 * @return EmployeeDO
	 * @throws CGSystemException the cG system exception
	 */
	public EmployeeDO getEmployeeByCodeOrID(Integer employeeId, String employeeCode)
	throws CGSystemException;
	
	/**
	 * method to load the customer object by id or code.
	 *
	 * @param custId the cust id
	 * @param custCode the cust code
	 * @return CustomerDO
	 * @throws CGSystemException the cG system exception
	 */
	public CustomerDO getCustomerByIdOrCode(Integer custId,	String custCode) throws CGSystemException;
	
	/**
	 * method to retrieve the reporting branch office by franchisee code.
	 *
	 * @param frCode the fr code
	 * @return OfficeDO
	 * @throws CGSystemException the cG system exception
	 */
	public FranchiseeDO getFranchiseeByFrCode(String frCode) throws CGSystemException;
	
	/**
	 * Gets the consignee address.
	 *
	 * @param consigneeID the consignee id
	 * @return the consignee address
	 * @throws CGSystemException the cG system exception
	 */
	public ConsigneeAddressDO getConsigneeAddress(Integer consigneeID)throws CGSystemException;
	
	/**
	 * Gets the booking details by cn.
	 *
	 * @param cnNumber the cn number
	 * @return the booking details by cn
	 */
	public BookingDO getBookingDetailsByCn(String cnNumber) ;
	
	/**
	 * Gets the approvers by approval name and office.
	 *
	 * @param purchaseCode the purchase code
	 * @param officeid the officeid
	 * @return the approvers by approval name and office
	 */
	public List<ShortApprovalDO> getApproversByApprovalNameAndOffice(String purchaseCode, Integer officeid);
	
	/**
	 * Gets the agents by office id.
	 *
	 * @param officeid the officeid
	 * @return the agents by office id
	 * @throws CGSystemException the cG system exception
	 */
	public List<AgentDO> getAgentsByOfficeId(Integer officeid) throws CGSystemException;
	
	/**
	 * Gets the agents by code.
	 *
	 * @param agentCode the agent code
	 * @return the agents by code
	 * @throws CGSystemException the cG system exception
	 */
	public AgentDO getAgentsByCode(String agentCode) throws CGSystemException;
	
	/**
	 * method to retrieve the country list.
	 *
	 * @return List<CountryDO>
	 */
	public List<CountryDO> getAllCountries();
	
	/**
	 * method to retrieve the country list.
	 *
	 * @return List<DocumentDO>
	 */
	public List<DocumentDO> getAllDocuments();
	
	/**
	 * Gets the co loaders by office id.
	 *
	 * @param officeid the officeid
	 * @return the co loaders by office id
	 * @throws CGSystemException the cG system exception
	 */
	public List<CoLoaderDO> getCoLoadersByOfficeId(Integer officeid) throws CGSystemException;
	
	/**
	 * Gets the co loader by code.
	 *
	 * @param coLoaderCode the co loader code
	 * @return the co loader by code
	 * @throws CGSystemException the cG system exception
	 */
	public CoLoaderDO getCoLoaderByCode(String coLoaderCode) throws CGSystemException;
	
	/**
	 * method to retrieve the Office for Employee Id.
	 *
	 * @param employeeId the employee id
	 * @return OfficeDO
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeDO getOfficeByEmployeeId(Integer employeeId)throws CGSystemException;
	
	/**
	 * Gets the employee by office id.
	 *
	 * @param officeId the office id
	 * @return the employee by office id
	 * @throws CGSystemException the cG system exception
	 */
	public List<EmployeeDO> getEmployeeByOfficeId(Integer officeId)	throws CGSystemException;
	
	/**
	 * Gets the franchisees.
	 *
	 * @return the franchisees
	 * @throws CGSystemException the cG system exception
	 */
	public List<FranchiseeDO> getFranchisees()	throws CGSystemException;
	
	/**
	 * Gets the franchisees by code.
	 *
	 * @param frCode the fr code
	 * @return the franchisees by code
	 * @throws CGSystemException the cG system exception
	 */
	public FranchiseeDO getFranchiseesByCode(String frCode)	throws CGSystemException;
	
	/**
	 * method to retrieve the ManifestType Detail using manifest type code.
	 *
	 * @param manifestTypeCode the manifest type code
	 * @return ManifestTypeDO
	 */
	public ManifestTypeDO getManifestTypeByCode(String manifestTypeCode);
	
	/**
	 * Gets the manifest types.
	 *
	 * @return the manifest types
	 */
	public List<ManifestTypeDO> getManifestTypes();
	
	/**
	 * Gets the cities by district id.
	 *
	 * @param districtId the district id
	 * @return the cities by district id
	 * @throws CGSystemException the cG system exception
	 */
	public List<CityDO> getCitiesByDistrictId(Integer districtId)	throws CGSystemException;
	
	/**
	 * Gets the dP codes for franchisee.
	 *
	 * @param franchiseeId the franchisee id
	 * @return the dP codes for franchisee
	 * @throws CGSystemException the cG system exception
	 */
	public List<String> getDPCodesForFranchisee(String franchiseeId) throws CGSystemException;
	
	/**
	 * Gets the vendor name.
	 *
	 * @param vendCode the vend code
	 * @return the vendor name
	 * @throws CGSystemException the cG system exception
	 */
	public String getVendorName(String vendCode) throws CGSystemException ;
	
	/**
	 * Gets the vendor id.
	 *
	 * @param vendCode the vend code
	 * @return the vendor id
	 * @throws CGSystemException the cG system exception
	 */
	public Integer getVendorId(String vendCode) throws CGSystemException;
	
	/**
	 * Gets the vendor.
	 *
	 * @param vendId the vend id
	 * @return the vendor
	 * @throws CGSystemException the cG system exception
	 */
	public VendorDO getVendor(Integer vendId) throws CGSystemException;
	
	/**
	 * Gets the ops office by code and type.
	 *
	 * @param offCode the off code
	 * @param opsType the ops type
	 * @return the ops office by code and type
	 * @throws CGSystemException the cG system exception
	 */
	public OpsOfficeDO getOpsOfficeByCodeAndType(String offCode, String opsType) throws CGSystemException;
	
	/**
	 * Gets the franchisee code values for branch.
	 *
	 * @param branchId the branch id
	 * @return the franchisee code values for branch
	 * @throws CGSystemException the cG system exception
	 */
	public  List<FranchiseeDO>  getFranchiseeCodeValuesForBranch(Integer branchId) throws CGSystemException;
	
	/**
	 * Gets the dP code values for branch.
	 *
	 * @param branchId the branch id
	 * @return the dP code values for branch
	 * @throws CGSystemException the cG system exception
	 */
	public  List<CustomerDO> getDPCodeValuesForBranch(Integer branchId) throws CGSystemException; 
	
	/**
	 * Gets the airport details.
	 *
	 * @return the airport details
	 * @throws CGSystemException the cG system exception
	 */
	public List<AirportDO> getAirportDetails() throws CGSystemException;
	
	/**
	 * Gets the ops office by id.
	 *
	 * @param offId the off id
	 * @param opsType the ops type
	 * @return the ops office by id
	 * @throws CGSystemException the cG system exception
	 */
	public OpsOfficeDO getOpsOfficeById(Integer offId, String opsType) throws CGSystemException;
	
	/**
	 * Gets the offices by office type.
	 *
	 * @param officeType the office type
	 * @return the offices by office type
	 */
	public List<OfficeDO> getOfficesByOfficeType(String officeType);
	
	/**
	 * Gets the rO details by office code.
	 *
	 * @param officeCode the office code
	 * @return the rO details by office code
	 */
	public OfficeDO getRODetailsByOfficeCode(String officeCode);
	
	/**
	 * Gets the all b os under ro.
	 *
	 * @param roId the ro id
	 * @return the all b os under ro
	 */
	public List<OfficeDO> getAllBOsUnderRO(Integer roId);
	
	/**
	 * Gets the agent address by agent id.
	 *
	 * @param agentId the agent id
	 * @return the agent address by agent id
	 */
	public AgentAddressDO getAgentAddressByAgentId(Integer agentId);
	
	/**
	 * Retrieve service type.
	 *
	 * @param productId the product id
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	List<ServiceDO> retrieveServiceType(String productId) throws CGBusinessException;
	
	/**
	 * Retrieve product type.
	 * 
	 * @return the list
	 * @throws CGBusinessException
	 *             the dTDC business exception
	 */
	List<ProductDO> retrieveProductType() throws CGBusinessException;
	
	/**
	 * Gets the franchisees.
	 *
	 * @param value the value
	 * @return the franchisees
	 * @throws CGSystemException the cG system exception
	 */
	public List<FranchiseeDO> getFranchisees(String value) throws CGSystemException;
	
	/**
	 * method to retrieve the reporting branch office by franchisee code.
	 *
	 * @param frCode the fr code
	 * @param franchiseeId the franchisee id
	 * @return OfficeDO
	 * @throws CGSystemException the cG system exception
	 */
	public FranchiseeDO getFranchiseeByFrCodeOrId(String frCode, Integer franchiseeId) throws CGSystemException;
	
	/**
	 * Gets the modes between offices.
	 *
	 * @param orgOfficeId the org office id
	 * @param destOfficeId the dest office id
	 * @return the modes between offices
	 */
	public List<ModeDO> getModesBetweenOffices(Integer orgOfficeId,Integer destOfficeId);
	
	/**
	 * Gets the document by id or type.
	 *
	 * @param documentId the document id
	 * @param documentType the document type
	 * @return the document by id or type
	 * @throws CGSystemException the cG system exception
	 */
	public DocumentDO getDocumentByIdOrType(Integer documentId,String documentType) throws CGSystemException;
	
	/**
	 * Gets the std handling by id or code.
	 *
	 * @param stdHandlingId the std handling id
	 * @param stdHandlingCode the std handling code
	 * @return the std handling by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public StdHandlingInstDO getStdHandlingByIdOrCode(Integer stdHandlingId,String stdHandlingCode) throws CGSystemException; 
	
	/**
	 * Gets the id proof docs by id or code.
	 *
	 * @param idProofDocId the id proof doc id
	 * @param idProofDocCode the id proof doc code
	 * @return the id proof docs by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public IdentityProofDocDO getIdProofDocsByIdOrCode(Integer idProofDocId,
			String idProofDocCode) throws CGSystemException;
	
	/**
	 * Gets the configurab param by param name.
	 *
	 * @param paramName the param name
	 * @return the configurab param by param name
	 */
	public ConfigurableParamsDO getConfigurabParamByParamName(String paramName);
	
	/**
	 * Gets the expenditure type by type.
	 *
	 * @param expendType the expend type
	 * @return the expenditure type by type
	 * @throws CGSystemException the cG system exception
	 */
	public ExpenditureTypeDO getExpenditureTypeByType(String expendType)
	throws CGSystemException;
	
	/**
	 * Gets the manifest type ids.
	 *
	 * @param manifestCodes the manifest codes
	 * @return the manifest type ids
	 * @throws CGSystemException the cG system exception
	 */
	public List<Integer> getManifestTypeIds(List<String> manifestCodes)
	throws CGSystemException;
	
	/**
	 * Gets the service mappings.
	 *
	 * @param serviceId the service id
	 * @return the service mappings
	 * @throws CGBusinessException the cG business exception
	 */
	public ServiceMappingDO getServiceMappings(int serviceId)
	throws CGBusinessException;
	
	/**
	 * Gets the id proof docs by code.
	 *
	 * @param idProofCode the id proof code
	 * @return the id proof docs by code
	 * @throws CGSystemException the cG system exception
	 */
	public List<IdentityProofDocDO> getIdProofDocsByCode(String... idProofCode)
			throws CGSystemException;
	
	/**
	 * Gets the service by id or code.
	 *
	 * @param serviceId the service id
	 * @param serviceCode the service code
	 * @return the service by id or code
	 * @throws CGSystemException the cG system exception
	 */
	public ServiceDO getServiceByIdOrCode(Integer serviceId, String serviceCode) throws CGSystemException;

	/**
	 * Gets the mode by code.
	 *
	 * @param modeCode the mode code
	 * @return the mode by code
	 * @throws CGSystemException the cG system exception
	 */
	ModeDO getModeByCode(String modeCode) throws CGSystemException; 

	/**
	 * Gets the user by user code.
	 *
	 * @param userCode the user code
	 * @return the user by user code
	 * @throws CGSystemException the cG system exception
	 */
	UserDO getUserByUserCode(String userCode) throws CGSystemException ;
	
	/**
	 * Gets the document by document code.
	 *
	 * @param documentCode the document code
	 * @return the document by document code
	 * @throws CGSystemException the cG system exception
	 */
	DocumentDO getDocumentByDocumentCode(String documentCode) throws CGSystemException ;
}

