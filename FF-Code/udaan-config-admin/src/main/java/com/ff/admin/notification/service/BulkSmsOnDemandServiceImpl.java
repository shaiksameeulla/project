package com.ff.admin.notification.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.notification.constants.BulkSmsOnDemandConstants;
import com.ff.admin.notification.dao.BulkSmsOnDemandDAO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.notification.BulkSmsConsignmentDtlsTO;
import com.ff.notification.BulkSmsOnDemandTO;
import com.ff.notification.service.TrackingNotificationService;
import com.ff.organization.OfficeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;

public class BulkSmsOnDemandServiceImpl implements BulkSmsOnDemandService {

	private GeographyCommonService geographyCommonService;
	private OrganizationCommonService organizationCommonService;
	private BulkSmsOnDemandDAO bulkSmsOnDemandDAO;
	private TrackingNotificationService trackingNotificationService;
		
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public void setBulkSmsOnDemandDAO(BulkSmsOnDemandDAO bulkSmsOnDemandDAO) {
		this.bulkSmsOnDemandDAO = bulkSmsOnDemandDAO;
	}

	public void setTrackingNotificationService(
			TrackingNotificationService trackingNotificationService) {
		this.trackingNotificationService = trackingNotificationService;
	}

	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	@Override
	public List<CityTO> getCitiesByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByRegion(regionId);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCity(cityId);
	}

	@Override
	public void getConsignmentDetailsByStatus(BulkSmsOnDemandTO smsOnDemandTO)
			throws CGBusinessException, CGSystemException {
		List<BulkSmsConsignmentDtlsTO> consignmentDtlsTOs = null;
		List<Object[]> consignmentDtls = bulkSmsOnDemandDAO.getConsignmentDetailsByStatus(smsOnDemandTO);
		if(!StringUtil.isEmptyColletion(consignmentDtls)){
			consignmentDtlsTOs = new ArrayList<BulkSmsConsignmentDtlsTO>();
			for (Object[] consignmentData : consignmentDtls) {
				BulkSmsConsignmentDtlsTO smsConsignmentDtlsTO = new BulkSmsConsignmentDtlsTO();
				smsConsignmentDtlsTO.setConsgId((Integer) consignmentData[0]);
				smsConsignmentDtlsTO.setConsgNo((String) consignmentData[1]);
				smsConsignmentDtlsTO.setBookingDate(DateUtil.getDDMMYYYYDateToString((Date) consignmentData[2]));
				ReasonTO reasonTO = null;
				if(!StringUtil.isNull(consignmentData[5])){
					reasonTO = new ReasonTO();
					reasonTO.setReasonName((String) consignmentData[5]);
				}
				smsConsignmentDtlsTO.setReasonTO(reasonTO);
				if(!StringUtil.isNull(consignmentData[4])){
					smsConsignmentDtlsTO.setMobileNo((String) consignmentData[4]);
				}else if(!StringUtil.isNull(consignmentData[3])){
					smsConsignmentDtlsTO.setMobileNo((String) consignmentData[3]);
				}
				
				consignmentDtlsTOs.add(smsConsignmentDtlsTO);
			}
		}else{
			throw new CGBusinessException(BulkSmsOnDemandConstants.CONSIGNMENT_DETAILS_NOT_FOUND);
		}
		smsOnDemandTO.setConsignmentDtlTOs(consignmentDtlsTOs);
	}
	
	@Override
	public String sendBulkSMS(BulkSmsOnDemandTO bulkSmsOnDemandTO) throws CGBusinessException,CGSystemException{
		String statusMessage= null;
		String consignmentNos[] = bulkSmsOnDemandTO.getConsignmentNos();
		String mobileNos[] = bulkSmsOnDemandTO.getMobileNos();
		Integer successRate = 0;
		for (int cnt=0 ; cnt < consignmentNos.length; cnt++) {
			String cnNumber = consignmentNos[cnt];
			String mobileNo = mobileNos[cnt];			
			if(StringUtils.isNotEmpty(mobileNo)){
				SmsSenderTO smsSenderTO = prepareSmsSenderTO(cnNumber, mobileNo);
				smsSenderTO.setUserId(bulkSmsOnDemandTO.getLoginUserId());
				trackingNotificationService.sendSMSNotification(smsSenderTO);
				successRate++;
			}
		}
		if(successRate == 0){
			statusMessage = CommonConstants.ERROR_MESSAGE;
		}else if(successRate == consignmentNos.length){
			statusMessage = CommonConstants.SUCCESS;
		}
		
		return statusMessage;
	}

	private SmsSenderTO prepareSmsSenderTO(String cnNumber, String mobileNo) {
		SmsSenderTO smsSenderTO = new SmsSenderTO();	
		smsSenderTO.setModuleName(BulkSmsOnDemandConstants.BULK_SMS_ON_DEMAND_MODULE_NAME);
		smsSenderTO.setContactNumber(mobileNo);
		smsSenderTO.setCnNumber(cnNumber);
		return smsSenderTO;
	}
}
