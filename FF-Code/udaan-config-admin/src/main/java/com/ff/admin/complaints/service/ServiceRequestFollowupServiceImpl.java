/**
 * 
 */
package com.ff.admin.complaints.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.converter.ServiceRequestFollowupConverter;
import com.ff.admin.complaints.dao.ServiceRequestFollowupDAO;
import com.ff.complaints.ServiceRequestFollowupTO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestFollowupDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;

/**
 * @author prmeher
 *
 */
public class ServiceRequestFollowupServiceImpl implements
		ServiceRequestFollowupService {
	
	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestFollowupServiceImpl.class);
	/** The service request followup DAO. */
	private ServiceRequestFollowupDAO serviceRequestFollowupDAO;
	
	private ComplaintsCommonService complaintsCommonService ;
	
	/** The geography common service. */
	private GeographyCommonService geographyCommonService;
	
	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;
	
	/**
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param geographyCommonService the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param complaintsCommonService the complaintsCommonService to set
	 */
	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}

	@Override
	public Boolean saveOrUpdateFollowup(
			ServiceRequestFollowupTO serviceRequestFollowupTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ServiceRequestFollowupServiceImpl :: saveOrUpdateFollowup :: START");
		Boolean isSave = false;
		ServiceRequestFollowupDO serviceRequestFollowupDO = null;
		List<ServiceRequestStatusTO> statusTOs =null;
		ServiceRequestDO complaint = null;
		serviceRequestFollowupDO = ServiceRequestFollowupConverter.followupDomainConverter(serviceRequestFollowupTO);
		isSave = serviceRequestFollowupDAO.saveOrUpdateFollowup(serviceRequestFollowupDO);
		
		if(isSave){
			if (!StringUtil.isEmptyInteger(serviceRequestFollowupTO
					.getComplaintId())) {
				complaint = serviceRequestFollowupDAO
						.getComplaintDtlsByComplaintId(serviceRequestFollowupTO
								.getComplaintId());
				if (!StringUtil.isNull(complaint)) {
					// Set Employee details
					if (!StringUtil.isNull(serviceRequestFollowupDO
							.getEmployeeDO())) {
						complaint.setAssignedTo(serviceRequestFollowupDO
								.getEmployeeDO());
					}
					if (!complaint
							.getServiceRequestStatusDO()
							.getStatusCode()
							.equalsIgnoreCase(
									ComplaintsCommonConstants.STATUS_CODE_FOLLOWUP)) {
						ServiceRequestStatusDO serviceRequestStatusDO = new ServiceRequestStatusDO();
						statusTOs = complaintsCommonService
								.getServiceRequestStatus();
						for (ServiceRequestStatusTO serviceRequestStatusTO : statusTOs) {
							if (serviceRequestStatusTO
									.getStatusCode()
									.equalsIgnoreCase(
											ComplaintsCommonConstants.STATUS_CODE_FOLLOWUP)) {
								serviceRequestStatusDO
										.setServiceRequestStatusId(serviceRequestStatusTO
												.getServiceRequestStatusId());
							}
						}
						complaint
								.setServiceRequestStatusDO(serviceRequestStatusDO);
					}
					complaint.setUpdateDate(Calendar.getInstance().getTime());
					isSave = serviceRequestFollowupDAO
							.updateServiceRequest(complaint);
				}
			}
		 /** As per customer request we are Disable email functionlaity*/
		   /*serviceRequestFollowupDO.setServiceRequestDO(complaint);
		   sendComplaintMail(serviceRequestFollowupDO);*/
		}
		
		LOGGER.trace("ServiceRequestFollowupServiceImpl :: saveOrUpdateFollowup :: END");
		return isSave;
	}
	
	
	private void sendComplaintMail(
			ServiceRequestFollowupDO serviceRequestFollowupDO)
			throws CGBusinessException, CGSystemException {
		try {
			List<MailSenderTO> mailerList = new ArrayList<>(2);
			String subject = "Your complaint with reference number "
					+ serviceRequestFollowupDO.getServiceRequestDO()
							.getServiceRequestNo() + " has been followup";
			subject = subject
					+ " for consignment/Booking ref no: "
					+ serviceRequestFollowupDO.getServiceRequestDO()
							.getBookingNo();
			StringBuilder plainMailBody = getMailBody(subject);
			prepareCallerMailAddress(serviceRequestFollowupDO, mailerList,
					subject, plainMailBody);
			prepareExecutiveMail(serviceRequestFollowupDO, mailerList);
			for (MailSenderTO senderTO : mailerList) {
				complaintsCommonService.sendEmail(senderTO);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",
					e);
		}
	}
	
	private void prepareExecutiveMail(
			ServiceRequestFollowupDO serviceRequestFollowupDO,
			List<MailSenderTO> mailerList) {
		if (StringUtil.isStringEmpty(serviceRequestFollowupDO.getEmail())) {
			if (!StringUtil.isNull(serviceRequestFollowupDO
					.getServiceRequestDO())
					&& !StringUtil.isNull(serviceRequestFollowupDO
							.getServiceRequestDO().getAssignedTo())
					&& !StringUtil
							.isNull(serviceRequestFollowupDO
									.getServiceRequestDO().getAssignedTo()
									.getEmailId())) {
				MailSenderTO exucutive = new MailSenderTO();
				exucutive.setTo(new String[] { serviceRequestFollowupDO
						.getServiceRequestDO().getAssignedTo().getEmailId() });
				exucutive.setMailSubject("Complaint No:"
						+ serviceRequestFollowupDO.getServiceRequestDO()
								.getServiceRequestNo() + " assigned to you");
				exucutive.setPlainMailBody(getMailBody(
						exucutive.getMailSubject()).toString());
				mailerList.add(exucutive);

			}

		}
	}

	
	private void prepareCallerMailAddress(ServiceRequestFollowupDO serviceRequestFollowupDO,
			List<MailSenderTO> mailerList, String subject,
			StringBuilder plainMailBody) {
		MailSenderTO callerSenderTO=new MailSenderTO();
		callerSenderTO.setTo(new String[]{serviceRequestFollowupDO.getEmail()});
		callerSenderTO.setMailSubject(subject);
		callerSenderTO.setPlainMailBody(plainMailBody.toString());
		mailerList.add(callerSenderTO);
	}

	private StringBuilder getMailBody(String subject) {
		StringBuilder plainMailBody=new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append(subject);
		plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
		return plainMailBody;
	}

	
	/**
	 * @param serviceRequestFollowupDAO the serviceRequestFollowupDAO to set
	 */
	public void setServiceRequestFollowupDAO(
			ServiceRequestFollowupDAO serviceRequestFollowupDAO) {
		this.serviceRequestFollowupDAO = serviceRequestFollowupDAO;
	}

	@Override
	public List<ServiceRequestFollowupTO> getComplaintFollowupDetails(
			Integer complaintId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("ServiceRequestFollowupServiceImpl :: getComplaintFollowupDetails :: START");
		List<ServiceRequestFollowupDO> followupDOs = null;
		List<ServiceRequestFollowupTO> followupTOs = null;
		/**Get Followup details */
		followupDOs = serviceRequestFollowupDAO.getComplaintFollowupDetails(complaintId);
	
		if(!CGCollectionUtils.isEmpty(followupDOs)){
			followupTOs = new ArrayList <ServiceRequestFollowupTO>();
		}
		/** Prepair TO list from followup Do list */
		for (ServiceRequestFollowupDO followupDO : followupDOs) {
			ServiceRequestFollowupTO followupTO = new ServiceRequestFollowupTO();
			followupTO = ServiceRequestFollowupConverter.followupTransferObjectConverter(followupDO);
			followupTOs.add(followupTO);
		}
		
		LOGGER.trace("ServiceRequestFollowupServiceImpl :: getComplaintFollowupDetails :: END");
		return followupTOs;
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
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllOfficesByCity(cityId);
		return officeTOList;
	}

	@Override
	public List<EmployeeTO> getAllEmployeeByOfficeAndRole(String designationType, Integer officeId)
			throws CGBusinessException, CGSystemException {
		return complaintsCommonService.getEmployeeDetailsByUserRoleAndOffice(designationType, officeId);
	}
	
	@Override
	public ServiceRequestTO getComplaintDtlsByComplaintId(Integer complaintId)
			throws CGBusinessException, CGSystemException {
		ServiceRequestTO serviceRequestTO = null;
		ServiceRequestDO serviceRequestDO = serviceRequestFollowupDAO.getComplaintDtlsByComplaintId(complaintId);
		if(!StringUtil.isNull(serviceRequestDO)){
			serviceRequestTO = new ServiceRequestTO();
			serviceRequestTO.setCallerName(serviceRequestDO.getCallerName());
			serviceRequestTO.setCallerEmail(serviceRequestDO.getCallerEmail());
		}
		return serviceRequestTO;
	}

}
