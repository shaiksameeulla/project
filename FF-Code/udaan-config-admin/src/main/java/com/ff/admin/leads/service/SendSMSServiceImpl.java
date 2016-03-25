/**
 * 
 */
package com.ff.admin.leads.service;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.sms.SmsSenderService;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.leads.LeadTO;


/**
 * @author abarudwa
 *
 */
public class SendSMSServiceImpl implements SendSMSService{

	private LeadsCommonService leadsCommonService;
	/** The SMS sender service. */
	private static SmsSenderService smsSenderServic;
	
	
	public static void setSmsSenderServic(SmsSenderService smsSenderServic) {
		SendSMSServiceImpl.smsSenderServic = smsSenderServic;
	}

	/**
	 * @param leadsCommonService the leadsCommonService to set
	 */
	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}



	@Override
	public LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,
			CGBusinessException {
		LeadTO leadTO =	leadsCommonService.getLeadDetails(leadNumber);
		if(StringUtil.isNull(leadTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		return leadTO;
	}

	@Override
	public String sendSMS(final String num,final String msg,final Integer userId) throws CGSystemException, CGBusinessException {
		String isSent= "N";
		SmsSenderTO smsSenderTO = new SmsSenderTO();
		smsSenderTO.setContactNumber(num);
		smsSenderTO.setMessage(msg);
		smsSenderTO.setModuleName("LEADS");
		smsSenderTO.setUserId(userId);
		
		smsSenderServic.prepareAndSendSms(smsSenderTO);
		if (StringUtils.equals(smsSenderTO.getIsSmsSent(), CommonConstants.YES)) {
			// sent
			isSent = LeadCommonConstants.SUCCESS;
		}

		return isSent;
	}

}
