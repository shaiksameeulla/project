/**
 * 
 */
package com.ff.report.billing.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.dao.ReBillingDAO;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.business.CustomerTO;
import com.ff.domain.billing.ReBillingHeaderDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.ReBillingGDRTO;
import com.ff.to.billing.ReBillingHeaderTO;
import com.ff.universe.billing.service.BillingUniversalService;

/**
 * @author abarudwa
 *
 */
public class ReBillingGDRServiceImpl implements ReBillingGDRService{
private final static Logger LOGGER = LoggerFactory.getLogger(ReBillingGDRServiceImpl.class);
	
	private BillingCommonService billingCommonService;
	
	private BillingUniversalService billingUniversalService;
	
	private BillPrintingService billPrintingService;
	
	private ReBillingDAO reBillingDAO;

	/**
	 * @param billingUniversalService the billingUniversalService to set
	 */
	public void setBillingUniversalService(
			BillingUniversalService billingUniversalService) {
		this.billingUniversalService = billingUniversalService;
	}

	/**
	 * @param billingCommonService the billingCommonService to set
	 */
	public void setBillingCommonService(BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	public void setBillPrintingService(BillPrintingService billPrintingService) {
		this.billPrintingService = billPrintingService;
	}

	public void setReBillingDAO(ReBillingDAO reBillingDAO) {
		this.reBillingDAO = reBillingDAO;
	}

	@Override
	public List<RegionTO> getRegions() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingGDRServiceImpl::getRegions::START----->");
		List<RegionTO> regionTOs = billingCommonService.getRegions();
		if (CGCollectionUtils.isEmpty(regionTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_REGION_FOUND);
		}
		LOGGER.debug("ReBillingGDRServiceImpl::getRegions::END----->");
		return regionTOs;
	}

	@Override
	public List<CityTO> getCitiesByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingGDRServiceImpl::getCitiesByRegionId::START----->");
		List<CityTO> cityTOs = billingCommonService.getCitiesByRegionId(regionId);
		if (CGCollectionUtils.isEmpty(cityTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_STATION_FOUND);
		}
		LOGGER.debug("ReBillingGDRServiceImpl::getCitiesByRegionId::END----->");
		return cityTOs;
	}

	@Override
	public List<OfficeTO> getOfficesByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingGDRServiceImpl::getOfficesByCityId::START----->");
		List<OfficeTO> officeTOs = billingCommonService.getOfficesByCityId(cityId);
		if (CGCollectionUtils.isEmpty(officeTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_BRANCHES_FOUND);
		}
		LOGGER.debug("InvoiceRunSheetUpdateServiceImpl::getOfficesByCityId::END----->");
		return officeTOs;
	}

	@Override
	public List<CustomerTO> getCustomersByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingGDRServiceImpl::getCustomersByOfficeId::START----->");
		//List<CustomerTO> customerTOs = billingCommonService.getCustomersByOfficeId(officeId);
		List<Integer> branchId=new ArrayList<Integer>();
		branchId.add(officeId);
		List<CustomerTO> customerTOs=billPrintingService.getCustomersByBillingBranch(branchId);
		if (CGCollectionUtils.isEmpty(customerTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
		}
		LOGGER.debug("ReBillingGDRServiceImpl::getCustomersByOfficeId::END----->");
		return customerTOs;
	}
	
	
	public ReBillingGDRTO getRebillDetails(ReBillingGDRTO rebillingGDRTO)throws CGBusinessException, CGSystemException {
		LOGGER.debug("ReBillingGDRServiceImpl::getRebillDetails::START----->");
		ReBillingGDRTO reBillingGDRTO=null;
		List<ReBillingHeaderTO> reBillTOs=null;
		List<ReBillingHeaderTO> reBillTosList=new ArrayList<ReBillingHeaderTO>();
		List<ReBillingHeaderDO> reBillDOs=null;
		Long totalCns,totalOldContract,totalNewContr;
		if(!StringUtil.isNull(rebillingGDRTO)){
			reBillDOs= reBillingDAO.getRebillDetails(rebillingGDRTO);
			if(!StringUtil.isEmptyColletion(reBillDOs)){
				reBillTOs = new ArrayList(reBillDOs.size());
				reBillTOs = (List<ReBillingHeaderTO>) CGObjectConverter
							.createTOListFromDomainList(reBillDOs, ReBillingHeaderTO.class);
				
				if(!StringUtil.isEmptyColletion(reBillTOs)){
					for(ReBillingHeaderTO rebillHeader:reBillTOs){
						totalCns= reBillingDAO.getTotalRebillCnCount(rebillHeader.getReBillingId());
						totalOldContract=reBillingDAO.getOldContractForCount(rebillHeader.getReBillingId());
						totalNewContr=reBillingDAO.getNewContractForCount(rebillHeader.getReBillingId());
						rebillHeader.setTotalCns(totalCns);
						rebillHeader.setNewContrFor(totalNewContr);
						rebillHeader.setOldContrFor(totalOldContract);
						reBillTosList.add(rebillHeader);
					}
					
					
					
					reBillingGDRTO=new ReBillingGDRTO();
					reBillingGDRTO.setReBillList(reBillTosList);
				}
					
			}else{
				throw new CGBusinessException(
						AdminErrorConstants.NO_REBILL_DATA);
			}
		}
		LOGGER.debug("ReBillingGDRServiceImpl::getRebillDetails::END----->");
		return reBillingGDRTO;
	}

	
	
}
