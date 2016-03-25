package com.ff.universe.organization.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.organization.dao.OrganizationCommonDAO;
import com.ff.universe.organization.dao.OrganizationServiceDAO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

public class OrganizationCommonServiceImpl implements OrganizationCommonService {

	private OrganizationServiceDAO organizationServiceDAO;
	private OrganizationCommonDAO organizationCommonDAO;

	public void setOrganizationServiceDAO(
			OrganizationServiceDAO organizationServiceDAO) {
		this.organizationServiceDAO = organizationServiceDAO;
	}

	public void setOrganizationCommonDAO(
			OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	private final static Logger LOGGER = LoggerFactory
			.getLogger(OrganizationCommonServiceImpl.class);

	@Override
	public List<LabelValueBean> getDeliveryBranchesOfCustomer(
			CustomerTO customerTO) throws CGBusinessException,
			CGSystemException {

		List<OfficeDO> listOfOfficeDO = null;
		List<LabelValueBean> boList = new ArrayList<LabelValueBean>();
		try {
			listOfOfficeDO = organizationServiceDAO
					.getDeliveryBranchesOfCustomer(customerTO);
			for (OfficeDO officeDO : listOfOfficeDO) {
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(officeDO.getOfficeCode() + "-"
						+ officeDO.getOfficeName());
				lvb.setValue(officeDO.getOfficeId() + "");
				boList.add(lvb);

			}
		} catch (Exception obj) {
			LOGGER.error(
					"ERROR : OrganizationCommonServiceImpl.getDeliveryBranchesOfCustomer",
					obj);

		}

		return boList;
	}

	@Override
	public List<Object[]> getReverseLogisticsCustomerList(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {

		OfficeDO officeDO = new OfficeDO();
		officeDO.setOfficeId(officeTO.getOfficeId());
		officeDO.setOfficeName(officeTO.getOfficeName());
		officeDO.setOfficeCode(officeTO.getOfficeCode());
		List<Object[]> customerObjs = organizationServiceDAO
				.getReverseLogisticsCustomerList(officeDO);
		return customerObjs;
	}

	@Override
	public List<OfficeTO> getBranchesServicing(String Pincode)
			throws CGBusinessException, CGSystemException {

		List<OfficeTO> officeTOs = new ArrayList<>();
		List<OfficeDO> officeDOs = new ArrayList<>();
		OfficeTO officeTO;
		try {
			officeDOs = organizationServiceDAO.getBranchesServicing(Pincode);
			for (OfficeDO officeDO : officeDOs) {
				officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOs.add(officeTO);
			}

		} catch (Exception exception) {
			LOGGER.error(
					"ERROR : OrganizationCommonServiceImpl.getBranchesServicing",
					exception);
		}

		// TODO Auto-generated method stub
		return officeTOs;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OrganizationCommonServiceImpl : getOfficeDetails"+System.currentTimeMillis());
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		OfficeTypeTO offTypeTo = null;
		RegionTO regionTO = null;
		try {
			List<OfficeDO> officeDOs = organizationServiceDAO
					.getOfficeDetails(officeId);
			if (officeDOs != null && !officeDOs.isEmpty()) {
				officeTO = new OfficeTO();
				officeDO = officeDOs.get(0);
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				if (officeDO.getOfficeTypeDO() != null) {
					offTypeTo = new OfficeTypeTO();
					CGObjectConverter.createToFromDomain(
							officeDO.getOfficeTypeDO(), offTypeTo);
				}
				if (officeDO.getMappedRegionDO() != null) {
					regionTO = new RegionTO();
					CGObjectConverter.createToFromDomain(
							officeDO.getMappedRegionDO(), regionTO);
				}
				officeTO.setOfficeTypeTO(offTypeTo);
				officeTO.setRegionTO(regionTO);
			}
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error(
					"ERROR : OrganizationCommonServiceImpl.getOfficeDetails",
					e);
		}
		LOGGER.debug("OrganizationCommonServiceImpl : getOfficeDetails"+System.currentTimeMillis());
		return officeTO;
	}

	@Override
	public Integer validateHubByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		Integer officeTypeId = organizationServiceDAO
				.validateHubByOfficeId(officeId);
		return officeTypeId;
	}

	@Override
	public List<LabelValueBean> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> boList = new ArrayList<LabelValueBean>();
		List<OfficeDO> officeDOList = organizationServiceDAO
				.getBOsByOfficeTypeId(officeTypeId);

		for (OfficeDO officeDO : officeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(officeDO.getOfficeCode() + "-"
					+ officeDO.getOfficeName());
			lvb.setValue(officeDO.getOfficeId() + "");
			boList.add(lvb);
		}
		return boList;
	}

	@Override
	public EmployeeTO getEmployeeDetails(Integer employeeId)
			throws CGBusinessException, CGSystemException {
		EmployeeTO employeeTO = null;
		try {
			Object[] employeeDetails = organizationServiceDAO
					.getEmployeeDetails(employeeId);
			if (employeeDetails != null && employeeDetails.length > 0) {
				employeeTO = new EmployeeTO();
				employeeTO.setEmployeeId((Integer) employeeDetails[0]);
				if(!StringUtil.isNull(employeeDetails[1])){
					employeeTO.setFirstName(employeeDetails[1].toString());
				}
				if(!StringUtil.isNull(employeeDetails[2])){
					employeeTO.setLastName(employeeDetails[2].toString());
				}		
				if(!StringUtil.isNull(employeeDetails[3])){
					employeeTO.setEmpCode(employeeDetails[3].toString());
				}	
				if(!StringUtil.isNull(employeeDetails[4])){
					employeeTO.setEmailId(employeeDetails[4].toString());
				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonServiceImpl.getEmployeeDetails",
					e);
			throw new CGSystemException(e);
		}

		return employeeTO;
	}

	/**
	 * @see com.ff.web.organization.service.OrganizationCommonService#getBranchEmployees(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getBranchEmployees OrganizationCommonService kgajare
	 */
	@Override
	public List<EmployeeTO> getBranchEmployees(Integer branchId)
			throws CGBusinessException, CGSystemException {
		List<EmployeeDO> employeeDOs = organizationCommonDAO
				.getBranchEmployees(branchId);
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	/**
	 * @see com.ff.web.organization.service.OrganizationCommonService#getMasterCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getMasterCustomerList OrganizationCommonService kgajare
	 */
	@Override
	public List<CustomerTO> getMasterCustomerList(Integer branchId)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = organizationCommonDAO
				.getMasterCustomerList(branchId);
		List<CustomerTO> customerTOs = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOs, CustomerTO.class);
		return customerTOs;
	}

	/**
	 * @see com.ff.web.organization.service.OrganizationCommonService#getReverseCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getReverseCustomerList OrganizationCommonService kgajare
	 */
	@Override
	public List<CustomerTO> getReverseCustomerList(Integer branchId)
			throws CGBusinessException, CGSystemException {
		List<CustomerDO> customerDOs = organizationCommonDAO
				.getReverseCustomerList(branchId);
		List<CustomerTO> customerTOs = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOs, CustomerTO.class);
		return customerTOs;
	}

	/**
	 * @see com.ff.web.organization.service.OrganizationCommonService#getBranchesUnderHUB(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getBranchesUnderHUB OrganizationCommonService kgajare
	 */
	@Override
	public List<OfficeTO> getBranchesUnderHUB(Integer officeId)
			throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getBranchesUnderHUB(officeId);
		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}

	@Override
	public List<EmployeeTO> getAllEmployees() throws CGBusinessException,
	CGSystemException {
		List<EmployeeDO> employeeDOs = organizationCommonDAO.getAllEmployees();
		List<EmployeeTO> employeeTOs = (List<EmployeeTO>) CGObjectConverter
				.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		return employeeTOs;
	}

	@Override
	public List<LabelValueBean> getOfficeTypeListForDispatch()
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> officeTypeList = new ArrayList<LabelValueBean>();
		List<OfficeTypeDO> officeTypeDOList = organizationServiceDAO
				.getOfficeTypeListForDispatch();
		for (OfficeTypeDO officeTypeDO : officeTypeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(officeTypeDO.getOffcTypeDesc());
			lvb.setValue(officeTypeDO.getOffcTypeId() + CommonConstants.TILD
					+ officeTypeDO.getOffcTypeCode());
			officeTypeList.add(lvb);
		}
		return officeTypeList;
	}

	@Override
	public List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
	CGSystemException {
		List<LabelValueBean> officeTypeList = new ArrayList<LabelValueBean>();
		List<OfficeTypeDO> officeTypeDOList = organizationServiceDAO
				.getOfficeTypeListForDispatch();
		for (OfficeTypeDO officeTypeDO : officeTypeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(officeTypeDO.getOffcTypeDesc());
			lvb.setValue(officeTypeDO.getOffcTypeId().toString());
			officeTypeList.add(lvb);
		}
		return officeTypeList;
	}

	/**
	 * @see com.ff.web.organization.service.OrganizationCommonService#getApprovers
	 * 
	 * @param officeTO
	 * @return List<EmployeeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getApprovers OrganizationCommonService uchauhan
	 */
	@Override
	public List<EmployeeTO> getApproversUnderRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		List<EmployeeTO> employeeTOs = null;
		List<EmployeeDO> employeeDOs = organizationCommonDAO
				.getApproversUnderRegion(regionId);
		if (employeeDOs != null && !employeeDOs.isEmpty()) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@Override
	public List<EmployeeTO> geScreentApproversByOffice(Integer screenId, Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("  OrganizationCommonServiceImpl.geScreentApproversByOffice");
		List<EmployeeTO> employeeTOs = null;
		List<EmployeeDO> employeeDOs = organizationCommonDAO
				.geScreentApproversByOffice(screenId, officeId);
		if (employeeDOs != null && !employeeDOs.isEmpty()) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@Override
	public List<LabelValueBean> getOfficesByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> officeList = new ArrayList<LabelValueBean>();
		List<OfficeDO> officeDOList = organizationServiceDAO
				.getBOsByOfficeTypeId(officeTypeId);

		for (OfficeDO officeDO : officeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(officeDO.getOfficeCode() + CommonConstants.HYPHEN
					+ officeDO.getOfficeName());
			lvb.setValue(officeDO.getOfficeId() + CommonConstants.TILD
					+ officeDO.getCityId());
			officeList.add(lvb);
		}
		return officeList;
	}

	@Override
	public List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		List<OfficeDO> officeDOList = organizationServiceDAO
				.getOfficesByOffice(officeTO);
		if(!CGCollectionUtils.isEmpty(officeDOList)){
			officeTOList = new ArrayList<OfficeTO>(officeDOList.size());
		}
		for (OfficeDO officeDO : officeDOList) {
			OfficeTO officeTO2 = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, officeTO2);
			if(officeDO.getOfficeTypeDO()!=null){
				OfficeTypeTO typeTo= new OfficeTypeTO();
				CGObjectConverter.createToFromDomain(officeDO.getOfficeTypeDO(),typeTo);
				officeTO2.setOfficeTypeTO(typeTo);
			}
			if(officeDO.getMappedRegionDO()!=null){
				RegionTO regionTO=new RegionTO();
				CGObjectConverter.createToFromDomain(officeDO.getMappedRegionDO(),regionTO);
				officeTO2.setRegionTO(regionTO);
			}
			officeTOList.add(officeTO2);
		}
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs=null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByCity(cityId);
		if(StringUtil.isEmptyList(officeDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_OFFICE_DETAILS_FOUND);
		}
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}		
		return officeTOs;
	}
	
	
	@Override
	public List<OfficeTO> getAllBranchOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs=null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllBranchOfficesByCity(cityId);
		if(StringUtil.isEmptyList(officeDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_OFFICE_DETAILS_FOUND);
		}
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}		
		return officeTOs;
	}
	
	@Override
	public List<OfficeTO> getAllBranchAndStandaloneOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs=null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllBranchAndStandaloneOfficesByCity(cityId);
		if(StringUtil.isEmptyList(officeDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_OFFICE_DETAILS_FOUND);
		}
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}		
		return officeTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);
		/*List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);*/
		List<OfficeTO> officeTOs=null;
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}
		return officeTOs;
	}

	@Override
	public List<EmployeeTO> getAllEmployeesUnderRegion(EmployeeTO customerTo)
			throws CGBusinessException, CGSystemException {
		EmployeeDO employeeDO = null;
		List<EmployeeDO> empDoList = null;
		List<EmployeeTO> empTOList = null;

		employeeDO = employeeTO2DOConverter(customerTo);
		empDoList = organizationCommonDAO
				.getAllEmployeesUnderRegion(employeeDO);
		empTOList = employeeDO2TOConverter(empDoList);
		return empTOList;
	}

	private EmployeeDO employeeTO2DOConverter(EmployeeTO empTO) {
		EmployeeDO empDO = null;
		if (empTO != null) {
			try {
				empDO = new EmployeeDO();
				PropertyUtils.copyProperties(empDO, empTO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("Error occured in OrganizationCommonServiceImpl :: employeeTO2DOConverter()..:"
						,e);
			}

		}
		return empDO;
	}

	private List<EmployeeTO> employeeDO2TOConverter(List<EmployeeDO> empDOList) {
		List<EmployeeTO> empTOList = null;
		if (!StringUtil.isEmptyList(empDOList)) {
			empTOList = new ArrayList<>(empDOList.size());
			try {
				for (EmployeeDO frDO : empDOList) {
					EmployeeTO to = new EmployeeTO();
					PropertyUtils.copyProperties(to, frDO);
					empTOList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("Error occured in OrganizationCommonServiceImpl :: employeeDO2TOConverter()..:"
						,e);
			}
		}
		return empTOList;
	}

	@Override
	public List<OfficeTO> getOfficesByOfficeType(OfficeTypeTO officeTypeTo)
			throws CGBusinessException, CGSystemException {

		OfficeTypeDO typeDo = new OfficeTypeDO();
		try {
			PropertyUtils.copyProperties(typeDo, officeTypeTo);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e1) {
			LOGGER.error("Error occured in OrganizationCommonServiceImpl :: getOfficesByOfficeType()..:"
					,e1);	
			throw new CGBusinessException(e1);
		}
		List<OfficeTO> officeTOList = null;
		List<OfficeDO> officeDOList = organizationCommonDAO
				.getAllOfficeByOfficeType(typeDo);

		if (!StringUtil.isEmptyList(officeDOList)) {
			officeTOList = new ArrayList<>(officeDOList.size());
			try {
				for (OfficeDO frDO : officeDOList) {
					OfficeTO to = new OfficeTO();
					PropertyUtils.copyProperties(to, frDO);
					officeTOList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("Error occured in OrganizationCommonServiceImpl :: getOfficesByOfficeType()..:"
						,e);			
				}
		}
		return officeTOList;
	}

	@Override
	public boolean validateBranchPincodeServiceability(
			PincodeServicabilityTO pincodeServicabilityTO)
					throws CGBusinessException, CGSystemException {
		return organizationCommonDAO
				.validateBranchPincodeServiceability(pincodeServicabilityTO);
	}

	@Override
	public List<OfficeTO> getAllOffices(Integer regionId)
			throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByRegion(regionId);
		@SuppressWarnings("unchecked")
		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
		.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}

	/**
	 * Get Offices By Regional Office Exclude Office(by officeId).
	 * <p>
	 * <ul>
	 * <li>If there exists Offices by reportingRHO and Exclude officeId, the
	 * method will return a valid List<OfficeTO> with all the details filled
	 * else return null.
	 * </ul>
	 * <p>
	 * 
	 * @param OfficeTO
	 *            :: The input OfficeTO will be passed with the following
	 *            details filled in -
	 *            <ul>
	 *            <li>officeId <li>reportingRHO
	 *            </ul>
	 * @return List<OfficeTO> :: List<OfficeTO> will get filled with all the
	 *         offices details.
	 * 
	 * @author R Narmdeshwar
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesByRegionalOfficeExcludeOffice(
			OfficeTO officeTO) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs = null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getOfficesByRegionalOfficeExcludeOffice(officeTO);
		if (officeDOs != null && officeDOs.size() > 0) {
			officeTOs = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOs, OfficeTO.class);
		}
		return officeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByCityList(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByCityList(cityTOList);

		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllRegionalOffices() throws CGBusinessException,
	CGSystemException {
		List<OfficeTO> officeTOs = null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllRegionalOffices();
		if (officeDOs != null && officeDOs.size() > 0) {
			officeTOs = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOs, OfficeTO.class);
		}
		return officeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs = null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getOfficesByOffices(officeTOList);
		if (officeDOs != null && officeDOs.size() > 0) {
			officeTOs = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOs, OfficeTO.class);
		}
		return officeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs = null;
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getOfficesByCityAndRHO(cityTOList);
		if (officeDOs != null && officeDOs.size() > 0) {
			officeTOs = (List<OfficeTO>) CGObjectConverter
					.createTOListFromDomainList(officeDOs, OfficeTO.class);
		}
		return officeTOs;
	}

	@Override
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OrganizationCommonServiceImpl::getOfficeByIdOrCode::START------------>:::::::");
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		officeDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
		if (!StringUtil.isNull(officeDO)) {
			officeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, officeTO);
			OfficeTypeTO offType = new OfficeTypeTO();
			CGObjectConverter.createToFromDomain(officeDO.getOfficeTypeDO(), offType);
			officeTO.setOfficeTypeTO(offType);
			RegionTO regTO=new RegionTO();
			if(!StringUtil.isNull(officeDO.getMappedRegionDO())){
			CGObjectConverter.createToFromDomain(officeDO.getMappedRegionDO(), regTO);
			officeTO.setRegionTO(regTO);
			}
			
		}
		LOGGER.debug("OrganizationCommonServiceImpl::getOfficeByIdOrCode::ENd------------>:::::::");
		return officeTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.organization.service.OrganizationCommonService#getEmployeesOfOffice(com.ff.organization.OfficeTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {		
		List<EmployeeDO> employeeDOs = organizationCommonDAO.getEmployeesOfOffice(officeTO);
		List<EmployeeTO> employeeTOs = null;
		if(!StringUtil.isEmptyColletion(employeeDOs)){
			employeeTOs = (List<EmployeeTO>) CGObjectConverter.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeTypeTO getOfficeTypeIdByOfficeTypeCode(String officeType)
			throws CGSystemException, CGBusinessException {
		OfficeTypeDO officeTypeDO = organizationCommonDAO.getOfficeTypeIdByOfficeTypeCode(officeType);
		OfficeTypeTO officeTypeTo = null;
		if(!StringUtil.isNull(officeTypeDO)){
			officeTypeTo = new OfficeTypeTO();
			officeTypeTo = (OfficeTypeTO) CGObjectConverter.createToFromDomain(officeTypeDO, officeTypeTo);
		}
		return officeTypeTo;
	}
	@Override
	public OfficeTypeTO getOfficeTypeDOByOfficeTypeIdOrCode(OfficeTypeTO officeTypeTO)
			throws CGSystemException, CGBusinessException {
		OfficeTypeDO officeTypeDO = organizationCommonDAO.getOfficeTypeDOByOfficeTypeIdOrCode(officeTypeTO);
		OfficeTypeTO officeTypeTo = new OfficeTypeTO();
		if(!StringUtil.isNull(officeTypeDO)){
			officeTypeTo = (OfficeTypeTO)CGObjectConverter.createToFromDomain(officeTypeDO, officeTypeTo);
		}
		return officeTypeTo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficeIdByReportingRHOCode(String reportingRHOCode)
			throws CGSystemException, CGBusinessException {
		List<OfficeDO> officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
		List<OfficeTO> officeTO = null;
		if(!StringUtil.isEmptyColletion(officeDO)){
			officeTO = (List<OfficeTO>) CGObjectConverter.createTOListFromDomainList(officeDO, OfficeTO.class);
		}
		return officeTO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getEmployeeDetails(EmployeeTO employeeTO)
			throws CGBusinessException, CGSystemException{
		List<EmployeeDO> employeeDOs = organizationCommonDAO.getEmployeeDetails(employeeTO);
		List<EmployeeTO> employeeTOs = null;
		if(!StringUtil.isEmptyColletion(employeeDOs)){
			employeeTOs = (List<EmployeeTO>) CGObjectConverter.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		return employeeTOs;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTypeTO> getOfficeTypes() throws CGBusinessException,
	CGSystemException {
		List<OfficeTypeTO> officeTypeList = null;
		List<OfficeTypeDO> officeTypeDOList = organizationServiceDAO.getOfficeTypeListForDispatch();
		if(!StringUtil.isEmptyColletion(officeTypeDOList)){
			officeTypeList = (List<OfficeTypeTO>) CGObjectConverter.createTOListFromDomainList(officeTypeDOList, OfficeTypeTO.class);
		}
		return officeTypeList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesByCityAndOfficeTypes(
			CityTO cityTO,List<String> officeTypes) throws CGBusinessException, CGSystemException{
		List<OfficeTO> officeList = null;
		List<OfficeDO> officeDOList = organizationCommonDAO.getOfficesByCityAndOfficeTypes(cityTO,officeTypes);
		if(!StringUtil.isEmptyColletion(officeDOList)){
			officeList = (List<OfficeTO>) CGObjectConverter.createTOListFromDomainList(officeDOList, OfficeTO.class);
		}
		return officeList;
	}

	@Override
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
	CGSystemException {
		OfficeTO officeTO = null;
		OfficeDO officeDO= organizationCommonDAO.getOfficeByEmpId(empId);
		if (officeDO != null) {
			officeTO = new OfficeTO();// creating office Object
			CGObjectConverter.createToFromDomain(officeDO, officeTO);
		}
		return officeTO;
	}

	@Override
	public List<OfficeTO> getRHOOfficesByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {

		List<OfficeTO> officeList = null;
		List<OfficeDO> officeDOList = organizationCommonDAO.getRHOOfficesByUserId(userId);
		if(!StringUtil.isEmptyColletion(officeDOList)){
			officeList = (List<OfficeTO>) CGObjectConverter.createTOListFromDomainList(officeDOList, OfficeTO.class);
		}
		return officeList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByCityListExceptCOOfc(
			List<CityTO> cityTOList) throws CGBusinessException,
			CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByCityListExceptCOOfc(cityTOList);

		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		return officeTOs;
	}
	@Override
	public Map<Integer, String> getOfficeUnderOfficeIdAsMap(Integer officeId) throws CGBusinessException,
	CGSystemException {
		List<?> officeMapDtls= organizationServiceDAO.getOfficeUnderOfficeIdAsMap(officeId);
		return prepareMapFromList(officeMapDtls);
	}

	/**
	 * Prepare map from list.
	 *
	 * @param officeMapList the item type list
	 * @return the map
	 */
	private Map<Integer, String> prepareMapFromList(List<?> officeMapList) {
		Map<Integer, String> itemTypeMap=null;
		if(!StringUtil.isEmptyList(officeMapList)){
			itemTypeMap = new HashMap<Integer, String>(officeMapList.size());
			for(Object itemType :officeMapList){
				Map map= (Map)itemType;
				String name=(String)map.get(StockUniveralConstants.TYPE_NAME);
				itemTypeMap.put((Integer)map.get(StockUniveralConstants.TYPE_ID),name.replaceAll(",", ""));
			}
		}
		return itemTypeMap;
	}
	@Override
	public List<EmployeeTO> getEmployeesByCity(Integer cityId) throws CGBusinessException,
	CGSystemException {
		List<EmployeeDO> employeeDOs = organizationCommonDAO.getEmployeesByCity(cityId);
		if(StringUtil.isEmptyList(employeeDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_EMPLOYESS_FOUND);
		}
		List<EmployeeTO> employeeTOs = (List<EmployeeTO>) CGObjectConverter
				.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		return employeeTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTO> getCustomersByOffice(List<Integer> officeList) throws CGBusinessException,
	CGSystemException {
		List<CustomerDO> customerDOs = organizationCommonDAO.getCustomersByOffice(officeList);
		if(StringUtil.isEmptyList(customerDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_CUSTOMERS_FOUND);
		}
		List<CustomerTO> customerTOs = (List<CustomerTO>) CGObjectConverter
				.createTOListFromDomainList(customerDOs, CustomerTO.class);
		return customerTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesAndHubOfficesServicedByPincode(
			String pincode) throws CGBusinessException, CGSystemException {

		List<OfficeTO> officeList = null;
		if(!StringUtil.isStringEmpty(pincode)){
		List<OfficeDO> officeDOList = organizationCommonDAO.getOfficesAndHubOfficesServicedByPincode(pincode);
		if(!StringUtil.isEmptyColletion(officeDOList)){
			officeList = (List<OfficeTO>) CGObjectConverter.createTOListFromDomainList(officeDOList, OfficeTO.class);
		}
		}
		return officeList;
	}
	
	
	@Override
	public List<OfficeTO> getAllHubOfficesByCity(Integer cityId,
			String officeTypeCode) throws CGBusinessException, CGSystemException {
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllHubOfficesByCity(cityId, officeTypeCode);
		/*List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);*/
		List<OfficeTO> officeTOs=null;
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}
		return officeTOs;
	}

	@Override
	public OfficeTO getOfficeByUserId(Integer userId)
			throws CGBusinessException, CGSystemException {
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		officeDO = organizationCommonDAO.getOfficeByUserId(userId);
		if (!StringUtil.isNull(officeDO)) {
			officeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, officeTO);
			OfficeTypeTO offType = new OfficeTypeTO();
			CGObjectConverter.createToFromDomain(officeDO.getOfficeTypeDO(), offType);
			officeTO.setOfficeTypeTO(offType);
			RegionTO regTO=new RegionTO();
			if(!StringUtil.isNull(officeDO.getMappedRegionDO())){
			CGObjectConverter.createToFromDomain(officeDO.getMappedRegionDO(), regTO);
			officeTO.setRegionTO(regTO);
			}
			
		}
		LOGGER.debug("OrganizationCommonServiceImpl::getOfficeByIdOrCode::ENd------------>:::::::");
		return officeTO;
	}
	
	@Override
	public OfficeTO getOfficeByOfcCode(String ofcCode)
			throws CGBusinessException, CGSystemException {
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		officeDO = organizationCommonDAO.getOfficeByOfcCode(ofcCode);
		if (!StringUtil.isNull(officeDO)) {
			officeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, officeTO);
			OfficeTypeTO offType = new OfficeTypeTO();
			CGObjectConverter.createToFromDomain(officeDO.getOfficeTypeDO(), offType);
			officeTO.setOfficeTypeTO(offType);
			RegionTO regTO=new RegionTO();
			if(!StringUtil.isNull(officeDO.getMappedRegionDO())){
			CGObjectConverter.createToFromDomain(officeDO.getMappedRegionDO(), regTO);
			officeTO.setRegionTO(regTO);
			}
			
		}
		LOGGER.debug("OrganizationCommonServiceImpl::getOfficeByIdOrCode::ENd------------>:::::::");
		return officeTO;
	}
	
	@Override
	public EmployeeTO getEmployeeByEmpCode(String empCode) throws CGBusinessException, CGSystemException{
		EmployeeDO employeeDO = null;
		EmployeeTO employeeTO = null;
		
		employeeDO = organizationCommonDAO
				.getEmployeeByEmpCode(empCode);
		
		if(!StringUtil.isNull(employeeDO)){
			employeeTO = new EmployeeTO();
			employeeTO = (EmployeeTO)CGObjectConverter.createToFromDomain(employeeDO, employeeTO);
		}
				
		return employeeTO;
		
	}

	@Override
	public DepartmentTO getDepartmentByCode(String deptCode)
			throws CGBusinessException, CGSystemException {
		DepartmentDO deptDO = null;
		DepartmentTO deptTO = null;
		
		deptDO = organizationCommonDAO
				.getDepartmentByCode(deptCode);
		
		if(!StringUtil.isNull(deptDO)){
			deptTO = new DepartmentTO();
			deptTO = (DepartmentTO)CGObjectConverter.createToFromDomain(deptDO, deptTO);
		}
				
		return deptTO;
	}

	@Override
	public OfficeTO getRHOOfcIdByRegion(Integer originRegionId)
			throws CGBusinessException, CGSystemException {
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		officeDO = organizationCommonDAO.getRHOOfcIdByRegion(originRegionId);
		if (!StringUtil.isNull(officeDO)) {
			officeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, officeTO);
			OfficeTypeTO offType = new OfficeTypeTO();
			CGObjectConverter.createToFromDomain(officeDO.getOfficeTypeDO(), offType);
			officeTO.setOfficeTypeTO(offType);
			RegionTO regTO=new RegionTO();
			if(!StringUtil.isNull(officeDO.getMappedRegionDO())){
			CGObjectConverter.createToFromDomain(officeDO.getMappedRegionDO(), regTO);
			officeTO.setRegionTO(regTO);
			}
			
		}
		LOGGER.debug("OrganizationCommonServiceImpl::getOfficeByIdOrCode::ENd------------>:::::::");
		return officeTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getOfficesAndAllHubOfficesofCityServicedByPincode(
			String pincode) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeList = null;
		if(!StringUtil.isStringEmpty(pincode)){
		List<OfficeDO> officeDOList = organizationCommonDAO.getOfficesAndAllHubOfficesofCityServicedByPincode(pincode);
		if(!StringUtil.isEmptyColletion(officeDOList)){
			officeList = (List<OfficeTO>) CGObjectConverter.createTOListFromDomainList(officeDOList, OfficeTO.class);
		}
		}
		return officeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OrganizationCommonServiceImpl :: getAllOfficesByType() :: START ");
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByType(offType);
		List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);
		LOGGER.trace("OrganizationCommonServiceImpl :: getAllOfficesByType() :: START ");
		return officeTOs;
	}
	
	@Override
	public boolean validateBranchPincodeServiceabilityForHubOffice(
			PincodeServicabilityTO pincodeServicabilityTO)
					throws CGBusinessException, CGSystemException {
		return organizationCommonDAO
				.validateBranchPincodeServiceabilityForHubOffice(pincodeServicabilityTO);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			String ofcTypeCode) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOs=null;
		List<OfficeDO> officeDOs = null;
		if(ofcTypeCode.equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
			officeDOs = organizationCommonDAO
					.getAllOfficesReportedToCity(cityId);
		}else if(ofcTypeCode.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
			officeDOs = organizationCommonDAO
					.getAllOfficesAndRHOOfcByCity(cityId);
		}else{
			officeDOs = organizationCommonDAO
				.getAllOfficesByCity(cityId);
		}
		if(StringUtil.isEmptyList(officeDOs)){
			throw new CGBusinessException(UniversalErrorConstants.NO_OFFICE_DETAILS_FOUND);
		}
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}		
		return officeTOs;
	}

	@Override
	public List<OfficeTO> getAllOfficesByRegionAndOfficeType(Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		List<OfficeDO> officeDOs = organizationCommonDAO
				.getAllOfficesByRegionAndOfficeType(regionId, officeTypeId);
		/*List<OfficeTO> officeTOs = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOs, OfficeTO.class);*/
		List<OfficeTO> officeTOs=null;
		if(!StringUtil.isEmptyList(officeDOs)){
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO officeTO=new OfficeTO();
				officeTO=(OfficeTO) CGObjectConverter
						.createToFromDomain(officeDO, officeTO);

				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO=(OfficeTypeTO) CGObjectConverter
						.createToFromDomain(officeDO.getOfficeTypeDO(), officeTypeTO);
				officeTO.setOfficeTypeTO(officeTypeTO);

				officeTOs.add(officeTO);
			}
		}
		return officeTOs;
	}
	
}

