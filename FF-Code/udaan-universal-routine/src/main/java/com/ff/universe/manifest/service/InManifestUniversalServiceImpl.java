/**
 * 
 */
package com.ff.universe.manifest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.global.RemarksTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

/**
 * @author uchauhan
 *
 */
public class InManifestUniversalServiceImpl implements InManifestUniversalService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InManifestUniversalServiceImpl.class);
	
	private OrganizationCommonService organizationCommonService;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}
	
	/**
	 * @param serviceOfferingCommonService the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(regionId);
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();

	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestUniversalServiceImpl::getAllOfficesByCity::START------------>:::::::");
		List<OfficeTO> officeTOList = null;
		List<OfficeTO> officeTOList1 = organizationCommonService
				.getAllOfficesByCity(cityId);
		if (!StringUtil.isEmptyList(officeTOList1)) {
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(0);
			officeTO.setOfficeName("ALL");
			officeTOList = new ArrayList<>();
			officeTOList.add(officeTO);
			officeTOList.addAll(officeTOList1);
		}
		LOGGER.trace("InManifestUniversalServiceImpl::getAllOfficesByCity::END------------>:::::::");
		return officeTOList;
	}
	
	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCityAndOfficeType(
				cityId, officeTypeId);
	}

	@Override
	public List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
			CGSystemException {
		List<LabelValueBean> officeTypeList = organizationCommonService
				.getOfficeTypeList();

		return officeTypeList;
	}

	@Override
	public ConsignmentTypeTO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return serviceOfferingCommonService.getConsgType(consignmentTypeTO);
	}

	@Override
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getContentValues();
	}

	@Override
	public List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getInsuarnceBy();
	}

	@Override
	public List<RemarksTO> getInManifestRemarks(String remarkType)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getRemarksByType(remarkType);
	}

	@Override
	public String isValidBranchCode(String branchCode)
			throws CGSystemException, CGBusinessException {
		String validBranch = CommonConstants.NO;
		OfficeTO officeTO = organizationCommonService.getOfficeByIdOrCode(null, branchCode);
		if(!StringUtil.isNull(officeTO))
			validBranch = CommonConstants.YES;
		return validBranch;
	}
}
