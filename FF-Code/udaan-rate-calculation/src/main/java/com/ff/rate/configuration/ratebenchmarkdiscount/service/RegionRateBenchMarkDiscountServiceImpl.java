package com.ff.rate.configuration.ratebenchmarkdiscount.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.rate.configuration.ratebenchmarkdiscount.dao.RegionRateBenchMarkDiscountDAO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;

/**
 * @author preegupt
 *
 */
public class RegionRateBenchMarkDiscountServiceImpl implements
		RegionRateBenchMarkDiscountService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RegionRateBenchMarkDiscountServiceImpl.class);

	private GeographyCommonService geographyCommonService;
	private RegionRateBenchMarkDiscountDAO regionRateBenchMarkDiscountDAO;
	private OrganizationCommonService organizationCommonService;

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param regionRateBenchMarkDiscountDAO
	 *            the regionRateBenchMarkDiscountDAO to set
	 */
	public void setRegionRateBenchMarkDiscountDAO(
			RegionRateBenchMarkDiscountDAO regionRateBenchMarkDiscountDAO) {
		this.regionRateBenchMarkDiscountDAO = regionRateBenchMarkDiscountDAO;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	@Override
	public String saveOrUpdateRateBenchMarkDiscount(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: saveOrUpdateRateBenchMarkDiscount() :: START --------> ::::::");
		boolean rbmStatus = Boolean.FALSE;
		String status = CommonConstants.FAILURE;
		try{
		if (!StringUtil.isNull(rbmdTO)) {

			List<RegionRateBenchMarkDiscountDO> discountDO = DomainConverteDiscountTO2DiscountDO(rbmdTO);
			if (!StringUtil.isEmptyList(discountDO)) {
				rbmStatus = regionRateBenchMarkDiscountDAO
						.saveOrUpdateRateBenchMark(discountDO);
			}
			if (rbmStatus) {
				status = CommonConstants.SUCCESS;
			}

		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RegionRateBenchMarkDiscountServiceImpl.saveOrUpdateRateBenchMarkDiscount", e);
			throw new CGSystemException(e);
		}
		
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: saveOrUpdateRateBenchMarkDiscount() :: END --------> ::::::");
		return status;
	}

	private List<RegionRateBenchMarkDiscountDO> DomainConverteDiscountTO2DiscountDO(
			RegionRateBenchMarkDiscountTO rbmdTO) {
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: DomainConverteDiscountTO2DiscountDO() :: START --------> ::::::");
		List<RegionRateBenchMarkDiscountDO> rbmdtDOs = new ArrayList<>();
		RegionRateBenchMarkDiscountDO rbmdtDO = null;
		int regLen = rbmdTO.getRegionId().length;
		for (int i = 0; i < regLen; i++) {
			rbmdtDO = new RegionRateBenchMarkDiscountDO();
			rbmdtDO.setDiscountPercentage(rbmdTO.getDiscountPercentage()[i]);

			rbmdtDO.setRegion(rbmdTO.getRegionId()[i]);

			EmployeeDO employeeDO = new EmployeeDO();
			employeeDO.setEmployeeId(rbmdTO.getEmployeeId()[i]);
			rbmdtDO.setEmployeeDO(employeeDO);

			rbmdtDO.setIndustryCategory(rbmdTO.getIndustryCategory());

			rbmdtDO.setRegionRateBenchMarkDiscountId(rbmdTO
					.getRegionRateBenchMarkDiscountArr()[i]);

			rbmdtDO.setDiscountApproved(rbmdTO.getDiscountApproved());

			rbmdtDOs.add(rbmdtDO);
		}
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: DomainConverteDiscountTO2DiscountDO() :: END --------> ::::::");
		return rbmdtDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryCategoryTO> getRateIndustryCategoryList()
			throws CGSystemException, CGBusinessException {

		List<RateIndustryCategoryTO> rateIndCatTOList = null;
		try {
			LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getRateIndustryCategoryList() :: START --------> ::::::");
			List<RateIndustryCategoryDO> rateIndCatDOList = regionRateBenchMarkDiscountDAO
					.getRateIndustryCategoryList();

			if (rateIndCatDOList != null && rateIndCatDOList.size() > 0) {
				rateIndCatTOList = (List<RateIndustryCategoryTO>) CGObjectConverter
						.createTOListFromDomainList(rateIndCatDOList,
								RateIndustryCategoryTO.class);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : RegionRateBenchMarkDiscountServiceImpl.getRateIndustryCategoryList", e);
			throw new CGSystemException(e);
		}
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getRateIndustryCategoryList() :: END --------> ::::::");
		return rateIndCatTOList;
	}

	@Override
	public EmployeeTO getEmployeeDetails(String empCode)
			throws CGSystemException, CGBusinessException {
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getEmployeeDetails() :: START --------> ::::::");
		EmployeeTO empTO = null;
		if (!StringUtil.isNull(empCode)) {
			empTO = new EmployeeTO();
			empTO.setEmpCode(empCode);
			List<EmployeeTO> empTOList = organizationCommonService
					.getEmployeeDetails(empTO);
			if (!CGCollectionUtils.isEmpty(empTOList))
				empTO = empTOList.get(0);
			else
				empTO = null;
		}
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getEmployeeDetails() :: END --------> ::::::");
		return empTO;
	}

	@Override
	public List<RegionRateBenchMarkDiscountTO> getDiscountDetails(
			RegionRateBenchMarkDiscountTO rbmdTO) throws CGBusinessException,
			CGSystemException {

		List<RegionRateBenchMarkDiscountDO> discountDO = null;
		List<RegionRateBenchMarkDiscountTO> rbmdTOs = null;
		try{
			LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getDiscountDetails() :: START --------> ::::::");
		discountDO = regionRateBenchMarkDiscountDAO.getDiscountDetails(rbmdTO);
		if (!StringUtil.isNull(discountDO)) {
			rbmdTOs = createDiscountTOListFromDomainList(discountDO);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RegionRateBenchMarkDiscountServiceImpl.getDiscountDetails", e);
			throw new CGSystemException(e);
		}
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getDiscountDetails() :: END --------> ::::::");
		return rbmdTOs;
	}

	private List<RegionRateBenchMarkDiscountTO> createDiscountTOListFromDomainList(
			List<RegionRateBenchMarkDiscountDO> discountDOList) {
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: createDiscountTOListFromDomainList() :: START --------> ::::::");
		List<RegionRateBenchMarkDiscountTO> rbmdtTOs = new ArrayList<>();
		RegionRateBenchMarkDiscountTO rbmdtTO = null;

		for (RegionRateBenchMarkDiscountDO discountDO : discountDOList) {
			rbmdtTO = new RegionRateBenchMarkDiscountTO();

			rbmdtTO.setDiscountPercent(discountDO.getDiscountPercentage());

			rbmdtTO.setRegion(discountDO.getRegion());

			EmployeeTO employee = new EmployeeTO();

			EmployeeDO employeeDO = discountDO.getEmployeeDO();
			try {
				employee = (EmployeeTO) CGObjectConverter.createToFromDomain(
						employeeDO, employee);
			} catch (CGBusinessException e) {
				LOGGER.error("RegionRateBenchMarkDiscountServiceImpl :: createDiscountTOListFromDomainList() :: error::",e);
			}

			employee.setEmployeeId(discountDO.getEmployeeDO().getEmployeeId());
			rbmdtTO.setEmployeeTO(employee);

			rbmdtTO.setIndustryCategory(discountDO.getIndustryCategory());

			rbmdtTO.setDiscountApproved(discountDO.getDiscountApproved());

			rbmdtTO.setRegionRateBenchMarkDiscount(discountDO
					.getRegionRateBenchMarkDiscountId());

			rbmdtTOs.add(rbmdtTO);
		}
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: createDiscountTOListFromDomainList() :: END --------> ::::::");
		return rbmdtTOs;
	}

	@Override
	public RegionRateBenchMarkDiscountDO getRateBenchMarkDiscountServiceByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getRateBenchMarkDiscountServiceByRegion() :: START --------> ::::::");
		List<RegionRateBenchMarkDiscountDO> discountDO = null;
		
		discountDO = regionRateBenchMarkDiscountDAO.getDiscountDetailsbyRegionId(regionId);
		LOGGER.info("RegionRateBenchMarkDiscountServiceImpl :: getRateBenchMarkDiscountServiceByRegion() :: END --------> ::::::");
		if (CGCollectionUtils.isEmpty(discountDO)) {
			return null;
		}
		return discountDO.get(0);
		
	}

	@Override
	public List<RegionRateBenchMarkDiscountDO> checkEmpIdRegionalApprover(
			Integer empId) throws CGBusinessException, CGSystemException {
		
		return regionRateBenchMarkDiscountDAO.checkEmpIdRegionalApprover(empId);
	}

}
