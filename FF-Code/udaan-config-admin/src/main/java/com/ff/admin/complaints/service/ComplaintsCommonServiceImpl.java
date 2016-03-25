package com.ff.admin.complaints.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.constants.ComplaintsServiceRequestConstants;
import com.ff.admin.complaints.converter.ComplaintsConverter;
import com.ff.admin.complaints.dao.ComplaintsCommonDAO;
import com.ff.admin.complaints.utils.ComplaintsUtils;
import com.ff.business.CustomerTypeTO;
import com.ff.complaints.ServiceRequestComplaintTypeTO;
import com.ff.complaints.ServiceRequestCustTypeTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.complaints.ServiceTransferTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.complaints.ServiceRequestComplaintTypeDO;
import com.ff.domain.complaints.ServiceRequestCustTypeDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestQueryTypeDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.domain.complaints.ServiceRequestTransfertoDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.util.UniversalConverterUtil;

public class ComplaintsCommonServiceImpl implements ComplaintsCommonService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComplaintsCommonServiceImpl.class);

	/** The globalUniversalService. */
	private GlobalUniversalService globalUniversalService;

	/** The complaintsCommonDAO. */
	private ComplaintsCommonDAO complaintsCommonDAO;

	/** The geographyCommonService. */
	private GeographyCommonService geographyCommonService;

	/** The serviceOfferingCommonService. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The ConsignmentCommonService. */
	private ConsignmentCommonService consignmentCommonService;

	/** The organizationCommonService. */
	private OrganizationCommonService organizationCommonService;

	private RateCalculationUniversalService rateCalculationUniversalService;

	private EmailSenderUtil emailSenderUtil;



	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	public void setComplaintsCommonDAO(ComplaintsCommonDAO complaintsCommonDAO) {
		this.complaintsCommonDAO = complaintsCommonDAO;
	}

	/**
	 * @return the emailSenderUtil
	 */
	public EmailSenderUtil getEmailSenderUtil() {
		return emailSenderUtil;
	}

	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @return the rateCalculationUniversalService
	 */
	public RateCalculationUniversalService getRateCalculationUniversalService() {
		return rateCalculationUniversalService;
	}

	/**
	 * @param rateCalculationUniversalService the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	@Override
	public List<StockStandardTypeTO> getStandardTypesByTypeName(String typeName)
			throws CGBusinessException, CGSystemException {
		return globalUniversalService.getStandardTypesByTypeName(typeName);
	}

	@Override
	public List<CustomerTypeTO> getCustomerTypeList()
			throws CGBusinessException, CGSystemException {
		List<CustomerTypeTO> customerList = null;
		List<CustomerTypeDO> customerDOList = complaintsCommonDAO
				.getCustomerTypeList();
		if (!CGCollectionUtils.isEmpty(customerDOList)) {
			customerList = new ArrayList<>(customerDOList.size());
			for (CustomerTypeDO customerTypeDO : customerDOList) {
				CustomerTypeTO customerTypeTO = new CustomerTypeTO();
				customerTypeTO.setCustomerTypeId(customerTypeDO
						.getCustomerTypeId());
				customerTypeTO.setCustomerTypeCode(customerTypeDO
						.getCustomerTypeCode());
				customerTypeTO.setCustomerTypeDesc(customerTypeDO
						.getCustomerTypeDesc());
				customerList.add(customerTypeTO);
			}

		}
		return customerList;
	}

	@Override
	public List<ServiceRequestCustTypeTO> getCustomerTypeListForComplaints()
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestCustTypeTO> customerTypeTOList = null;
		List<ServiceRequestCustTypeDO> customerDOList = complaintsCommonDAO
				.getCustomerTypeListForComplaints();
		if (!CGCollectionUtils.isEmpty(customerDOList)) {
			customerTypeTOList = new ArrayList<>(customerDOList.size());
			for (ServiceRequestCustTypeDO customerTypeDO : customerDOList) {
				ServiceRequestCustTypeTO customerTypeTO = new ServiceRequestCustTypeTO();
				customerTypeTO.setServiceRequestCustomerTypeId(customerTypeDO
						.getServiceRequestCustomerTypeId());
				customerTypeTO.setCustomerTypeCode(customerTypeDO
						.getCustomerTypeCode());
				customerTypeTO.setCustomerTypeDescription(customerTypeDO
						.getCustomerTypeDescription());
				customerTypeTO.setCustomerTypeName(customerTypeDO
						.getCustomerTypeName());
				customerTypeTOList.add(customerTypeTO);
			}

		}
		return customerTypeTOList;
	}

	@Override
	public List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeDetails(ServiceRequestQueryTypeTO serviceQueryTypeTO)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestQueryTypeTO> serviceReqQueryTypeTOList = null;
		ServiceRequestQueryTypeDO inputTypeDO=null;
		if(!StringUtil.isNull(serviceQueryTypeTO)){
			inputTypeDO= new ServiceRequestQueryTypeDO();
			CGObjectConverter.createDomainFromTo(serviceQueryTypeTO, inputTypeDO);
		}
		List<ServiceRequestQueryTypeDO> serviceReqQueryTypeDOList = complaintsCommonDAO
				.getServiceRequestQueryTypeDetails(inputTypeDO);
		if (!CGCollectionUtils.isEmpty(serviceReqQueryTypeDOList)) {
			serviceReqQueryTypeTOList = new ArrayList<>(
					serviceReqQueryTypeDOList.size());
			for (ServiceRequestQueryTypeDO customerTypeDO : serviceReqQueryTypeDOList) {
				ServiceRequestQueryTypeTO customerTypeTO = new ServiceRequestQueryTypeTO();
				customerTypeTO.setServiceRequestQueryTypeId(customerTypeDO
						.getServiceRequestQueryTypeId());
				customerTypeTO.setQueryTypeCode(customerTypeDO
						.getQueryTypeCode());
				customerTypeTO.setQueryTypeName(customerTypeDO
						.getQueryTypeName());
				customerTypeTO.setQueryType(customerTypeDO
						.getQueryType());
				customerTypeTO.setQueryTypeDescription(customerTypeDO
						.getQueryTypeDescription());
				serviceReqQueryTypeTOList.add(customerTypeTO);
			}

		}
		return serviceReqQueryTypeTOList;
	}

	@Override
	public List<ServiceRequestComplaintTypeTO> getComplaintTypeDetails()
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestComplaintTypeTO> serviceReqComplaintTOList = null;
		List<ServiceRequestComplaintTypeDO> serviceReqComplaintDOList = complaintsCommonDAO
				.getComplaintTypeDetails();
		if (!CGCollectionUtils.isEmpty(serviceReqComplaintDOList)) {
			serviceReqComplaintTOList = new ArrayList<>(
					serviceReqComplaintDOList.size());
			for (ServiceRequestComplaintTypeDO complaintTypeDo : serviceReqComplaintDOList) {
				ServiceRequestComplaintTypeTO complaintTypeTo = new ServiceRequestComplaintTypeTO();
				complaintTypeTo
				.setServiceRequestComplaintTypeId(complaintTypeDo
						.getServiceRequestComplaintTypeId());
				complaintTypeTo.setComplaintTypeCode(complaintTypeDo
						.getComplaintTypeCode());
				complaintTypeTo.setComplaintTypeName(complaintTypeDo
						.getComplaintTypeName());
				complaintTypeTo.setComplaintTypeDescription(complaintTypeDo
						.getComplaintTypeDescription());
				serviceReqComplaintTOList.add(complaintTypeTo);
			}

		}
		return serviceReqComplaintTOList;
	}

	@Override
	public CityTO getCity(final CityTO cityTO) throws CGBusinessException,
	CGSystemException {

		return geographyCommonService.getCity(cityTO);
	}

	@Override
	public List<CityTO> getCitiesByCity(final CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	@Override
	public Map<Integer, String> getAllCitiesByCity(final CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		Map<Integer, String> cityMap = null;
		List<CityTO> cityList = geographyCommonService.getCitiesByCity(cityTO);
		if (!CGCollectionUtils.isEmpty(cityList)) {
			cityMap = new HashMap<>(cityList.size());
			for (CityTO cityTo : cityList) {
				cityMap.put(
						cityTo.getCityId(),
						cityTo.getCityCode()
						+ FrameworkConstants.CHARACTER_HYPHEN
						+ cityTo.getCityName());
			}
		}
		return cityMap;
	}

	@Override
	public List<PincodeTO> getPincodeDetailsByPincode(final PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getPincodeDetailsByPincode(pincode);
	}

	@Override
	public Map<Integer, String> getPincodeListByPinCode(final PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		Map<Integer, String> pincodeList = null;
		List<PincodeTO> cityList = geographyCommonService
				.getPincodeDetailsByPincode(pincode);
		if (!CGCollectionUtils.isEmpty(cityList)) {
			pincodeList = new HashMap<>(cityList.size());
			for (PincodeTO pincodeTo : cityList) {
				pincodeList.put(pincodeTo.getPincodeId(),
						pincodeTo.getPincode());
			}
		}
		return pincodeList;
	}
	@Override
	public Map<Integer, String> getAllPincodeAsMap()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getAllPincodesAsMap();
	}
	@Override
	public Map<Integer, String> getAllBranchesAsMap()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getAllBranchesAsMap();
	}

	@Override
	public Map<Integer, String> getAllRHOOfficesAsMap()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getAllRHOOfficesAsMap();
	}

	@Override
	public Map<Integer, String> getAllRHOAndCOOfficesAsMap()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getAllRHOAndCOOfficesAsMap();
	}

	@Override
	public Map<Integer, String> getAllCitiesByRhoOfficeAsMap(Integer rhoOfficeid)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService
				.getAllCitiesByRhoOfficeAsMap(rhoOfficeid);
	}

	@Override
	public Map<Integer, String> getAllRHOAndCOOfficesAsMap(boolean isCORequired)
			throws CGBusinessException, CGSystemException {
		Map<Integer, String> allRHOMap=null;
		if(isCORequired){
			allRHOMap=getAllRHOAndCOOfficesAsMap();
		}else{
			allRHOMap=getAllRHOOfficesAsMap();
		}
		return allRHOMap;
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeTO> getEmployeeDetailsByDesignationType(
			final String designationType) throws CGSystemException,
			CGBusinessException {
		List<EmployeeDO> employeeDOs = complaintsCommonDAO
				.getEmployeeDetailsByDesignationType(designationType);
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(
			final String designationType, final Integer officeId)
					throws CGSystemException, CGBusinessException {
		List<EmployeeDO> employeeDOs = complaintsCommonDAO
				.getEmployeeDetailsByUserRoleAndOffice(designationType,
						officeId);
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}
	@Override
	public List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(
			final String designationType, final List<Integer> officeIdList)
					throws CGSystemException, CGBusinessException {
		List<EmployeeDO> employeeDOs = complaintsCommonDAO
				.getEmployeeDetailsByUserRoleAndOffice(designationType,
						officeIdList);
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@Override
	public List<EmployeeTO> getEmployeeDetailsByUserRoleAndCity(
			final String designationType, final Integer cityId)
					throws CGSystemException, CGBusinessException {
		List<EmployeeDO> employeeDOs = null;
		final List<Integer> officeIdList=complaintsCommonDAO.getAllOfficeIdByCityId(cityId);
		if(!CGCollectionUtils.isEmpty(officeIdList)){
			employeeDOs = complaintsCommonDAO
					.getEmployeeDetailsByUserRoleAndOffice(designationType,
							officeIdList);
		}
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@Override
	public EmployeeTO getEmployeeDetailsById(Integer empId)
			throws CGBusinessException, CGSystemException{
		EmployeeTO empTo=new EmployeeTO();
		empTo.setEmployeeId(empId);
		List<EmployeeTO> employeeTOs = organizationCommonService.getEmployeeDetails(empTo);
		if(!StringUtil.isEmptyColletion(employeeTOs)){
			empTo = employeeTOs.get(0);
		}
		return empTo;

	}

	@Override
	public String generateReferenceNumber(String loginOfficeCode)
			throws CGSystemException, CGBusinessException {
		return ComplaintsUtils.generateRefNumber(
				ComplaintsCommonConstants.COMPLAINTS_NUMBER_START_CODE,
				loginOfficeCode, ComplaintsCommonConstants.COMPLAINTS_NUMBER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductTO> getProductList() throws CGSystemException,
	CGBusinessException {
		List<ProductDO> productDOs = complaintsCommonDAO.getProductList();
		List<ProductTO> productTOs = null;
		if (!CGCollectionUtils.isEmpty(productDOs)) {
			productTOs = (List<ProductTO>) CGObjectConverter
					.createTOListFromDomainList(productDOs, ProductTO.class);
		}
		return productTOs;
	}
	@Override
	public List<ProductTO> getProductList(String consignmentSeries) throws CGSystemException,
	CGBusinessException {
		List<ProductDO> productDOs = complaintsCommonDAO.getProductList(consignmentSeries);
		List<ProductTO> productTOs = null;
		if (!CGCollectionUtils.isEmpty(productDOs)) {
			productTOs = (List<ProductTO>) CGObjectConverter
					.createTOListFromDomainList(productDOs, ProductTO.class);
		}
		return productTOs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PincodeTO> getPincode(String pincode) throws CGSystemException,
	CGBusinessException {
		List<PincodeDO> pincodeDOs = complaintsCommonDAO.getPincode(pincode);
		List<PincodeTO> pincodeTOs = null;
		if (!CGCollectionUtils.isEmpty(pincodeDOs)) {
			pincodeTOs = new ArrayList(pincodeDOs.size());
			for (PincodeDO pincodeDO : pincodeDOs) {
				PincodeTO pincodeTO = new PincodeTO();
				pincodeTO.setPincodeId(pincodeDO.getPincodeId());
				pincodeTO.setPincode(pincodeDO.getPincode());
				pincodeTOs.add(pincodeTO);
			}

		}
		return pincodeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestTO> getServiceRequestDetails(
			ServiceRequestFilters requestFilters) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ComplaintsCommonServiceImpl::getServiceRequestDetails::START------------>:::::::");
		List<ServiceRequestTO> serviceRequestTOs = null;
		if (!StringUtil.isNull(requestFilters)) {
			List<ServiceRequestDO> serviceRequestDOs = complaintsCommonDAO
					.getServiceRequestDetails(requestFilters);
			if (!StringUtil.isNull(serviceRequestDOs)) {
				serviceRequestTOs = (List<ServiceRequestTO>) CGObjectConverter
						.createTOListFromDomainList(serviceRequestDOs,
								ServiceRequestTO.class);
			}
		}

		LOGGER.debug("ComplaintsCommonServiceImpl::getServiceRequestDetails::END------------>:::::::");
		return serviceRequestTOs;
	}

	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService
				.getPaperWorks(paperWorkValidationTO);
	}
	@Override
	public String getPaperWorksDetailsForComplaints(Integer destPincode, String consgType) throws CGSystemException,
	CGBusinessException {
		StringBuilder paperworks=new StringBuilder();
		ConsignmentTypeConfigTO configType=new ConsignmentTypeConfigTO();

		configType.setConsignmentType(consgType);
		configType.setDocType(consgType);
		List<ConsignmentTypeConfigTO> consgTypeConfig = serviceOfferingCommonService
				.getConsgTypeConfigDtlsByConsgCongType(configType);
		if(!CGCollectionUtils.isEmpty(consgTypeConfig)){
			for(ConsignmentTypeConfigTO configToType: consgTypeConfig){
				if(!StringUtil.isStringEmpty(configToType.getIsPaperworkMandatory()) && configToType.getIsPaperworkMandatory().equalsIgnoreCase(CommonConstants.YES)){
					paperworks.append(" \n Supporting paper(s) is(are) mandatory for ConsignmentType :");
					paperworks.append(configToType.getDocType());
					paperworks.append(" \t for Declared value: ");
					paperworks.append(configToType.getDeclaredValue());

					PincodeTO pincodeTO = getPincodeByIdOrCode(destPincode,null);
					CNPaperWorksTO cnpaperworkTO= new CNPaperWorksTO();
					cnpaperworkTO.setPincode(pincodeTO.getPincode());

					List<CNPaperWorksTO> cnpaperworksList=serviceOfferingCommonService.getPaperWorks(cnpaperworkTO);
					if(!CGCollectionUtils.isEmpty(cnpaperworksList)){
						paperworks.append("\n Required supporting documents are : \t");
						int counter=1;
						for(CNPaperWorksTO paperworkTO:cnpaperworksList){
							paperworks.append("\n");
							paperworks.append(counter);
							paperworks.append(".");
							paperworks.append(paperworkTO.getCnPaperWorkDesc());
							++counter;
						}
					}else{
						//FIXME throw exception 
						paperworks= new StringBuilder();
						paperworks.append("Supporting papers are not required for this pincode");
						break;
					}
				}else{
					paperworks.append("\n Paperwork is not mandatory for ConsignmentType :");
					paperworks.append(configToType.getDocType());
					paperworks.append(" for Decalared value: ");
					paperworks.append(configToType.getDeclaredValue());
				}
			}
		}
		return paperworks.toString();

	}
	@Override
	public PincodeTO getPincodeByIdOrCode(Integer destPincodeId,String pincodeCode)
			throws CGBusinessException, CGSystemException {
		PincodeTO pincodeTO= new PincodeTO();
		pincodeTO.setPincodeId(destPincodeId);
		pincodeTO.setPincode(pincodeCode);
		pincodeTO=geographyCommonService.validatePincode(pincodeTO);
		return pincodeTO;
	}
	@Override
	public List<ProductTO> getProductByProductTO(
			ProductTO product) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getProductDetailsByProduct(product);
	}

	@Override
	public List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("ComplaintsCommonServiceImpl :: getBacklineSummaryStatus() :: Start --------> ::::::");
		List<ServiceRequestStatusTO> statusTOs = null;
		List<ServiceRequestStatusDO> statusDOs = complaintsCommonDAO
				.getServiceRequestStatus();
		if (StringUtil.isEmptyColletion(statusDOs)) {
			ExceptionUtil
			.prepareBusinessException(ComplaintsCommonConstants.ERROR_IN_GETTING_BACKLINE_SUMMARY_STATUS);
		}
		statusTOs = new ArrayList<ServiceRequestStatusTO>(statusDOs.size());
		for (ServiceRequestStatusDO statusDO : statusDOs) {
			ServiceRequestStatusTO statusTO = new ServiceRequestStatusTO();
			CGObjectConverter.createToFromDomain(statusDO, statusTO);
			statusTOs.add(statusTO);
		}
		LOGGER.trace("ComplaintsCommonServiceImpl :: getBacklineSummaryStatus() :: End --------> ::::::");

		return statusTOs;
	}

	@Override
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}

	@Override
	public ConsignmentDO getConsgDtlsByConsgNo(String consgNo)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getConsgDtlsByConsgNo(consgNo);
	}

	@Override
	public ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService
				.getConsgDtlsByBookingRefNo(bookingRefNo);
	}

	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(officeId);
	}

	@Override
	public CustomerDO getCustomerDtlsByCustId(Integer customerId)
			throws CGSystemException {
		return complaintsCommonDAO.getCustomerDtlsByCustId(customerId);
	}
	@Override
	public List<ServiceRequestTO> searchServiceRequestDetails(ServiceRequestFilters requestFilters)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestDO> serviceReqDtls=null;
		List<ServiceRequestTO> serviceRequestTOList=null;
		serviceReqDtls= complaintsCommonDAO.searchServiceRequestDtls(requestFilters);
		if(!CGCollectionUtils.isEmpty(serviceReqDtls)){
			serviceRequestTOList= new ArrayList<>(serviceReqDtls.size());
			for(ServiceRequestDO requestDO :serviceReqDtls){
				ServiceRequestTO serviceTo= new ServiceRequestTO();
				CGObjectConverter.createToFromDomain(requestDO, serviceTo);
				serviceTo.setCreatedDateStr(DateUtil.getDDMMYYYYDateString(requestDO.getCreatedDate()));
				serviceTo.setUpdateDateStr(DateUtil.getDDMMYYYYDateString(requestDO.getUpdateDate()));
				serviceTo.setServiceRequestNo(requestDO.getServiceRequestNo());
				serviceTo.setServiceRequestId(requestDO.getServiceRequestId());
				serviceTo.setLinkedServiceReqNo(requestDO.getLinkedServiceReqNo());
				if(!StringUtil.isStringEmpty(requestDO.getLinkedServiceReqNo())){
					serviceTo.setIsLinkedWith(FrameworkConstants.ENUM_YES);
				}
				serviceTo.setServiceResult(requestDO.getServiceResult());
				serviceTo.setSourceOfQuery(requestDO.getSourceOfQuery());
				serviceTo.setRemark(requestDO.getRemark());
				if(!StringUtil.isNull(requestDO.getWeight())){
					String weight=requestDO.getWeight()+"";
					String weightWithPrecision[]=weight.split("\\"+FrameworkConstants.CHARACTER_DOT);
					serviceTo.setWeightKgs(String.format("%07d", Integer.parseInt(weightWithPrecision[0])));

					serviceTo.setWeightGrm(weightWithPrecision[1].length()==3?weightWithPrecision[1]:String.format("%03d", Integer.parseInt(weightWithPrecision[1])));
				}

				serviceTo.setSmsToConsignee(requestDO.getSmsToConsignee());
				serviceTo.setSmsToConsignor(requestDO.getSmsToConsignor());
				serviceTo.setEmailToCaller(requestDO.getEmailToCaller());

				populateCallerDtlsFromDO2TO(requestDO, serviceTo);

				populateServiceTypeDtlsFromDO2TO(requestDO, serviceTo);
				populateEmpDtlsFromDO2TO(requestDO, serviceTo);
				if(!StringUtil.isStringEmpty(requestDO.getConsignmentType())){
					serviceTo.setConsignmentType(requestDO.getConsignmentType());
				}
				if(requestDO.getOriginCity()!=null){
					serviceTo.setOriginCityId(requestDO.getOriginCity().getCityId());
					serviceTo.setOriginLabel(requestDO.getOriginCity()
							.getCityCode()
							+ CommonConstants.HYPHEN
							+ requestDO.getOriginCity().getCityName());
				}else if (!StringUtil.isEmptyInteger(requestDO.getOriginBranchId())){
					serviceTo.setOriginCityId(requestDO.getOriginBranchId());
					serviceTo.setOriginBranchRequireToLoad(true);
				}

				if(requestDO.getDestPincode()!=null){
					serviceTo.setPincodeId(requestDO.getDestPincode().getPincodeId());
					Map<Integer,String> pincodeMap=new HashMap<>(1);
					pincodeMap.put(requestDO.getDestPincode().getPincodeId(),requestDO.getDestPincode().getPincode());
					serviceTo.setPincodeMap(pincodeMap);
				}
				if(requestDO.getProductType()!=null){
					serviceTo.setProductId(requestDO.getProductType().getProductId());
					// Product description
					serviceTo.setProductLabel(requestDO.getProductType()
							.getProductDesc());
				}
				if(requestDO.getServiceRequestCustTypeDO()!=null){
					serviceTo.setCustomerType(requestDO.getServiceRequestCustTypeDO().getServiceRequestCustomerTypeId()+FrameworkConstants.CHARACTER_TILDE+requestDO.getServiceRequestCustTypeDO().getCustomerTypeCode());
				}

				populateQueryTypeDtlsFromDO2TO(requestDO, serviceTo);
				populateComplaintTypeDtlsFromDO2TO(requestDO, serviceTo);

				if(requestDO.getServiceRequestStatusDO()!=null){
					serviceTo.setStatus(requestDO.getServiceRequestStatusDO().getServiceRequestStatusId()+FrameworkConstants.CHARACTER_TILDE+requestDO.getServiceRequestStatusDO().getStatusCode());
					ServiceRequestStatusTO serviceRequestStatusTO = null;
					if(!StringUtil.isNull(requestDO.getServiceRequestStatusDO())){
						serviceRequestStatusTO = new ServiceRequestStatusTO();
						CGObjectConverter.createToFromDomain(requestDO.getServiceRequestStatusDO(),serviceRequestStatusTO);
						serviceTo.setServiceRequestStatusTO(serviceRequestStatusTO);
					}
				}

				serviceRequestTOList.add(serviceTo);
			}
		}
		return serviceRequestTOList;
	}

	private void populateQueryTypeDtlsFromDO2TO(ServiceRequestDO requestDO,
			ServiceRequestTO serviceTo) {
		if(!StringUtil.isNull(requestDO.getServiceRequestQueryTypeDO())){
			serviceTo.setServiceRelated(requestDO.getServiceRequestQueryTypeDO().getServiceRequestQueryTypeId()+FrameworkConstants.CHARACTER_TILDE+requestDO.getServiceRequestQueryTypeDO().getQueryTypeCode());
			ServiceRequestQueryTypeTO queryType= new ServiceRequestQueryTypeTO();
			try {
				PropertyUtils.copyProperties(queryType,requestDO.getServiceRequestQueryTypeDO());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("ComplaintsCommonServiceImpl :: populateQueryTypeDtlsFromDO2TO() :: Exception --------> ::::::",e);

			}
			serviceTo.setServiceRequestQueryTypeTO(queryType);
		}
	}

	private void populateComplaintTypeDtlsFromDO2TO(ServiceRequestDO requestDO,
			ServiceRequestTO serviceTo) {
		if(!StringUtil.isNull(requestDO.getServiceRequestComplaintTypeDO())){
			serviceTo.setComplaintCategory(requestDO.getServiceRequestComplaintTypeDO().getServiceRequestComplaintTypeId()+FrameworkConstants.CHARACTER_TILDE+requestDO.getServiceRequestComplaintTypeDO().getComplaintTypeCode());
			ServiceRequestComplaintTypeTO complaintType= new ServiceRequestComplaintTypeTO();
			try {
				PropertyUtils.copyProperties(complaintType,requestDO.getServiceRequestComplaintTypeDO());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("ComplaintsCommonServiceImpl :: populateComplaintTypeDtlsFromDO2TO() :: Exception --------> ::::::",e);

			}
			serviceTo.setServiceRequestComplaintTypeTO(complaintType);
			String queryTypeCode = requestDO.getServiceRequestQueryTypeDO().getQueryTypeCode();
			if (queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT)
					|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT)
					|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT)
					|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT)) {
				serviceTo.setIsLinkEnabled("Y");
			}
		}
	}

	private void populateServiceTypeDtlsFromDO2TO(ServiceRequestDO requestDO,
			ServiceRequestTO serviceTo) throws CGSystemException, CGBusinessException {
		String role=null;
		if(!StringUtil.isStringEmpty(requestDO.getBookingNoType())){
			serviceTo.setBookingNo(requestDO.getBookingNo());
			switch(requestDO.getBookingNoType()){
			case ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_REF:
				serviceTo.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF);
				role=globalUniversalService.getConfigParamValueByName(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE);
				break;
			case ComplaintsCommonConstants.COMPLAINT_BOOKING_NO_TYPE_CN:
				serviceTo.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG);
				role=globalUniversalService.getConfigParamValueByName(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE);
				break;
			default:
				serviceTo.setBookingNo(requestDO.getServiceRequestNo());
				serviceTo.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);
				role=globalUniversalService.getConfigParamValueByName(CommonConstants.CONFIG_PARAM_ROLE_SALES_COORDINATOR);
			}
		}else{
			serviceTo.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);
			role=globalUniversalService.getConfigParamValueByName(CommonConstants.CONFIG_PARAM_ROLE_SALES_COORDINATOR);
		}
		serviceTo.setTransactionRoleType(role);
	}

	private void populateEmpDtlsFromDO2TO(ServiceRequestDO requestDO,
			ServiceRequestTO serviceTo) throws CGSystemException, CGBusinessException {
		Map<Integer,String> employeeMapDtls= null;
		Map<Integer,String> assignedEmployeeMapDtls= null;
		if(!StringUtil.isNull(requestDO.getAssignedTo())){
			assignedEmployeeMapDtls=new HashMap<>(1);
			serviceTo.setEmployeeId(requestDO.getAssignedTo().getEmployeeId());
			serviceTo.setEmpEmailId(requestDO.getAssignedTo().getEmailId());
			serviceTo.setEmpPhone(requestDO.getAssignedTo().getEmpPhone());
			if(requestDO.getServiceRequestStatusDO() !=null && (requestDO.getServiceRequestStatusDO().getStatusCode().equalsIgnoreCase(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_BACKLINE)||requestDO.getServiceRequestStatusDO().getStatusCode().equalsIgnoreCase(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_FOLLOWUP))){
				ServiceTransferTO transferTo=new ServiceTransferTO();
				transferTo.setServiceReqNo(requestDO.getServiceRequestNo());
				transferTo.setEmpName(requestDO.getAssignedTo().getEmpCode()+FrameworkConstants.CHARACTER_HYPHEN+requestDO.getAssignedTo().getFirstName()+requestDO.getAssignedTo().getLastName());
				transferTo.setEmpEmail(requestDO.getAssignedTo().getEmailId());
				transferTo.setEmpPhoneNumber(requestDO.getAssignedTo().getEmpPhone());
				transferTo.setServiceRequestId(requestDO.getServiceRequestId());
				serviceTo.setTransferTO(transferTo);
			}else{
				serviceTo.setTransferTO(null);
			}
			EmployeeTO empto= new EmployeeTO();
			CGObjectConverter.createToFromDomain(requestDO.getAssignedTo(),empto);
			serviceTo.setAssignedToEmpTO(empto);
			assignedEmployeeMapDtls.put(requestDO.getAssignedTo().getEmployeeId(), requestDO.getAssignedTo().getEmpCode()+FrameworkConstants.CHARACTER_HYPHEN+requestDO.getAssignedTo().getFirstName()+requestDO.getAssignedTo().getLastName());

		}

		employeeMapDtls = getEmployeeListByRole(requestDO, serviceTo);
		if(CGCollectionUtils.isEmpty(employeeMapDtls) && !CGCollectionUtils.isEmpty(assignedEmployeeMapDtls)){
			employeeMapDtls= assignedEmployeeMapDtls; 
		}else if (!CGCollectionUtils.isEmpty(employeeMapDtls)&& !CGCollectionUtils.isEmpty(assignedEmployeeMapDtls)){
			employeeMapDtls.putAll(assignedEmployeeMapDtls);
		}
		serviceTo.setEmplyoeeMap(employeeMapDtls);
	}

	private Map<Integer, String> getEmployeeListByRole(
			ServiceRequestDO requestDO, ServiceRequestTO serviceTo
			) throws CGBusinessException,
			CGSystemException {
		Map<Integer, String> employeeMapDtls=null;
		OfficeTO officeTO=getOfficeDetails(requestDO.getTransactionOfficeId());
		if(officeTO!=null){
			Integer officeId=officeTO.getOfficeId();
			String officeType=officeTO.getOfficeTypeTO().getOffcTypeCode();
			if(officeTO.getOfficeTypeTO()!=null && !StringUtil.isStringEmpty(officeType)){
				if(!officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					officeId=officeTO.getReportingRHO();
				}
			}

			List<EmployeeTO> empListTo =getEmployeeDetailsByUserRoleAndOffice(serviceTo.getTransactionRoleType(), officeId);
			if(!CGCollectionUtils.isEmpty(empListTo)){
				employeeMapDtls= new HashMap<>(empListTo.size());
				employeeMapDtls=UniversalConverterUtil.getEmployeeMapFromList(empListTo);

			}
		}
		return employeeMapDtls;
	}

	private void populateCallerDtlsFromDO2TO(ServiceRequestDO requestDO,
			ServiceRequestTO serviceTo) {
		serviceTo.setCallerName(requestDO.getCallerName());
		serviceTo.setCallerPhone(requestDO.getCallerPhone());
		serviceTo.setCallerEmail(requestDO.getCallerEmail());
	}

	@Override
	public String complaintProcessNumberGenerator(SequenceGeneratorConfigTO to)
			throws CGSystemException, CGBusinessException {
		String generatedNumber=null;
		String qryName=null;

		if(isValidInput(to)){
			/*switch (to.getProcessRequesting()) {
			case ComplaintsCommonConstants.COMPLAINTS_NUMBER_START_CODE:
				qryName=ComplaintsCommonConstants.QRY_GET_SERVICE_REQ_MAX_NUMBER;
				break;

			}*/
			qryName=ComplaintsCommonConstants.QRY_GET_SERVICE_REQ_MAX_NUMBER;
			if(StringUtil.isStringEmpty(qryName)){
				LOGGER.error("StockCommonServiceImpl::stockProcessNumberGenerator---process Query not defined for the given process");
				throw new CGBusinessException("process Query not defined for the given process");
			}
			generatedNumber = getGeneratedNumber(to,qryName);

		}else{
			LOGGER.error("StockCommonServiceImpl::stockProcessNumberGenerator---invalid input(s)");
			throw new CGBusinessException("invalid input(s)");
		}



		return generatedNumber;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestTransfertoTO> getTransfettoDetails()
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestTransfertoTO> trasnfettoTOs = null;
		List<ServiceRequestTransfertoDO> trasnfettoDOs = complaintsCommonDAO
				.getTransfettoDetails();
		if (!StringUtil.isEmptyColletion(trasnfettoDOs)) {
			trasnfettoTOs = new ArrayList<ServiceRequestTransfertoTO>(trasnfettoDOs.size());			
			trasnfettoTOs = (List<ServiceRequestTransfertoTO>)CGObjectConverter.createTOListFromDomainList(trasnfettoDOs, ServiceRequestTransfertoTO.class);			
		}

		return 	trasnfettoTOs;
	}

	/**
	 * Gets the generated number.
	 *
	 * @param to the to
	 * @param qryName the qry name
	 * @return the generated number
	 * @throws CGSystemException the cG system exception
	 */
	private String getGeneratedNumber(SequenceGeneratorConfigTO to,String qryName) throws CGSystemException {
		String generatedNumber=null;
		String number=null;
		Long result=1l;
		Integer length =to.getProcessRequesting().length()+to.getRequestingBranchCode().trim().length();
		length = length+to.getSequenceRunningLength();

		number = complaintsCommonDAO.getMaxNumberFromProcess(to, qryName);
		if(!StringUtil.isStringEmpty(number)){
			String  res=number.substring(8);

			result=StringUtil.parseLong(res);
			if(StringUtil.isNull(result) ){
				result=1l;
			}else if(result >= 0l){
				result +=1;
			}

		}else{
			LOGGER.info("StockCommonServiceImpl::getGeneratedNumber---Max number does not exist for Request"+to.getProcessRequesting());
		}
		generatedNumber=to.getProcessRequesting().toUpperCase()+to.getRequestingBranchCode().toUpperCase().trim()+sequencePadding(result,to.getSequenceRunningLength());
		return generatedNumber;
	}

	/**
	 * Checks if is valid input.
	 *
	 * @param to the to
	 * @return the boolean
	 */
	private Boolean isValidInput(SequenceGeneratorConfigTO to) {
		if(StringUtil.isStringEmpty(to.getProcessRequesting())){
			return Boolean.FALSE;
		}else if(StringUtil.isStringEmpty(to.getRequestingBranchCode())){
			return Boolean.FALSE;
		}else if(StringUtil.isEmptyInteger(to.getRequestingBranchId())){
			return Boolean.FALSE;
		}else if(StringUtil.isEmptyInteger(to.getSequenceRunningLength())){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * Sequence padding.
	 *
	 * @param number the number
	 * @param length the length
	 * @return the string
	 */
	private String sequencePadding(Long number,Integer length){
		String format=null;
		format="%0"+length+"d";
		return String.format(format, number);  
	}

	@Override
	public RateCalculationOutputTO calculateRate(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException {
		return rateCalculationUniversalService.calculateRate(inputTO);
	}

	@Override
	public Double getFinalRateGrandTotal(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException {
		Double grandTotalIncludingTax=null;
		RateCalculationOutputTO outputTo=calculateRate(inputTO);
		grandTotalIncludingTax = ComplaintsConverter.getFinalRateComponent(outputTo);
		return grandTotalIncludingTax;
	}

	
	@Override
	public Boolean isPincodeServiceableByProductSeries(Integer pincodeId,Integer productId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.isPincodeServiceableByProductSeries(pincodeId, productId);
	}
	@Override
	public List<OfficeTO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getBranchDtlsForPincodeServiceByPincode(pincodeId);
	}

	@Override
	public void sendEmail(MailSenderTO emailTO) throws CGBusinessException,CGSystemException {
		emailSenderUtil.sendEmail(emailTO);
	}

	@Override
	public List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeByServiceType(String serviceType)
			throws CGBusinessException, CGSystemException {
		ServiceRequestQueryTypeTO serviceTypeTO=prepareServiceRequestQueryTypeByServiceType(serviceType);
		return getServiceRequestQueryTypeDetails(serviceTypeTO);
	}
	private ServiceRequestQueryTypeTO prepareServiceRequestQueryTypeByServiceType(String serviceType)
			throws CGBusinessException, CGSystemException {
		ServiceRequestQueryTypeTO serviceTypeTO=null;
		if(!StringUtil.isStringEmpty(serviceType)){
			serviceTypeTO= new ServiceRequestQueryTypeTO();
			switch(serviceType){
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_SR);

				break;
			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_CN);
				break;

			case ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG:
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_CN);
				break;
			}
			if(!StringUtil.isStringEmpty(serviceTypeTO.getQueryType())){
				serviceTypeTO.setQueryType(serviceTypeTO.getQueryType()+FrameworkConstants.CHARACTER_COMMA+ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_BOTH_CN_AND_SR);
			}else{
				serviceTypeTO.setQueryType(ComplaintsCommonConstants.SERVICE_REQUEST_QUERY_TYPE_BOTH_CN_AND_SR);
			}
		}
		return serviceTypeTO;
	}

	@Override
	public String getConsignorMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getConsignorMobileNumberByConsgNo(consgNo, bookingRefNumber);
	}
	@Override
	public String getConsigneeMobileNumberByConsgNo(String consgNo,String bookingRefNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getConsigneeMobileNumberByConsgNo(consgNo, bookingRefNumber);
	}
	@Override
	public Date getConsignmentDeliveryDate(String consigNo)
			throws CGSystemException, CGBusinessException {
		return consignmentCommonService.getConsignmentDeliveryDate(consigNo);
	}

}


