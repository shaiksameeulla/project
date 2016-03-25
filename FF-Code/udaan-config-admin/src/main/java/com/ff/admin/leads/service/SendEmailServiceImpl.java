/**
 * 
 */
package com.ff.admin.leads.service;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.leads.LeadTO;
//import com.ff.universe.util.EmailSenderService;


/**
 * @author abarudwa
 *
 */
public class SendEmailServiceImpl implements SendEmailService{

	
	private LeadsCommonService leadsCommonService;
	private EmailSenderUtil emailSenderUtil;
	//private EmailSenderService emailSenderService;
	
	/**
	 * @param emailSenderService the emailSenderService to set
	 */
	/*public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}*/

	/**
	 * @param leadsCommonService the leadsCommonService to set
	 */
	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}


	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}



	@Override
	public LeadTO getLeadDetails(String leadNumber) throws CGSystemException,
			CGBusinessException {
		LeadTO leadTO =	leadsCommonService.getLeadDetails(leadNumber);
		if(StringUtil.isNull(leadTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		return leadTO;
	}

	@Override
	public String sendEmail(MailSenderTO mailSenderTO) {
		emailSenderUtil.sendEmail(mailSenderTO);
		return LeadCommonConstants.SUCCESS;
	}

	
	

}
