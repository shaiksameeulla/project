package com.ff.notification.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.sms.SmsSenderDAO;
import com.capgemini.lbs.framework.dao.sms.SmsSenderDAOImpl;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.SmsDO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.queueUtils.NotificationQueueContentTO;
import com.capgemini.lbs.framework.sms.SmsSenderService;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.masters.ConsignmentStatusSmsDO;
import com.ff.domain.masters.NotificationConfigDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.notification.NotificationConstant;
import com.ff.notification.dao.ConsignmentNotificationDAO;
import com.ff.notification.to.ConsignmentNotificationTO;
//import com.ff.notification.to.NotificationQueueContentTO;

public class ConsignmentNotificationServiceImpl implements
		ConsignmentNotificationService {

	/*
	 * private static final Logger loggger = Logger
	 * .getLogger(ConsignmentNotificationServiceImpl.class);
	 */
	private MultiKeyMap configuredValues = MultiKeyMap.decorate(new LRUMap(50));
	private MultiKeyMap customersList = MultiKeyMap.decorate(new LRUMap(50));

	private final static Logger loggger = LoggerFactory
			.getLogger(ConsignmentNotificationServiceImpl.class);
	//private static final String SmsDO = null;

	/** Dependency: Data access object **/
	private ConsignmentNotificationDAO notificationDao;

	/** Dependency: The velocity engine. */
	private VelocityEngine velocityEngine;
	private static Map<String, String> configurableParams;
	// smsSenderServic
	SmsSenderService smsSenderServic;
	EmailSenderUtil mailSender;
	SmsSenderDAOImpl smsSenderDAOImpl;
	//SmsSenderServiceImpl smsSenderServiceImpl;
	SmsSenderDAO smsSenderDAO;
	
	

	@Override
	public void proceedNotification(CGBaseDO baseDO) throws CGBusinessException {
		loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::starts====>");
		if (baseDO instanceof DeliveryDO) {
			DeliveryDO headerDO = (DeliveryDO) baseDO;
			prepareDeliveryStatusNotification(headerDO);
		} else if (baseDO instanceof ConsignmentDO) {
			loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::ConsignmentDO====>");
		}

		loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::end====>");
	}

	public void prepareDeliveryStatusNotification(DeliveryDO headerDO) {
		loggger.debug("ConsignmentNotificationServiceImpl::prepareDeliveryStatusNotification::starts====>");
		NotificationQueueContentTO to = new NotificationQueueContentTO();
		for (DeliveryDetailsDO deliveryDetailDO : headerDO.getDeliveryDtlsDO()) {
			to.setConsgNo(deliveryDetailDO.getConsignmentNumber());
			to.setCurrentStatus(deliveryDetailDO.getDeliveryStatus());
			try {
				proceedIndividual(to);
			} catch (Exception e) {
				loggger.error(
						"ConsignmentNotificationServiceImpl::prepareDeliveryStatusNotification::Exception====>",
						e);
				e.printStackTrace();
			}
		}
		loggger.debug("ConsignmentNotificationServiceImpl::prepareDeliveryStatusNotification::ends====>");
	}

	public void proceedIndividual(NotificationQueueContentTO notifyTO)
			throws CGBusinessException {
		loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::starts====>");
		// loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::allowedSeries====>"
		// + allowedSeries);
		// loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::allowedStatus====>"
		// + allowedStatus);
		// loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::allowedCustomers====>"
		// + allowedCustomers);
		if (notifyTO == null || notifyTO.getConsgNo() == null
				|| notifyTO.getConsgNo().isEmpty()
				|| notifyTO.getCurrentStatus() == null
				|| notifyTO.getCurrentStatus().isEmpty())
			throw new CGBusinessException(
					"consignment number or current/previous status is null/empty");

		loggger.debug("ConsignmentNotificationServiceImpl::proceedNotification::to object is not null====>");
		// String series = getProduct(notifyTO.getConsgNo());

		// Getting notification type
		int notificationType = getNotificationType(notifyTO);

		switch (notificationType) {
		case NotificationConstant.NOTIFICATION_TYPE_NON:
			break;
		case NotificationConstant.NOTIFICATION_TYPE_SMS:
			proceedSMSNotification(notifyTO);
			break;
		case NotificationConstant.NOTIFICATION_TYPE_EMAIL:

			break;
		}

	}

	public int getNotificationType(NotificationQueueContentTO notifyTO) {

		// Get configured notification type from DB for consignment type, status
		// and customer.

		// Calculate applied notification type

		// FIXME remove fixed notification once configuration done
		return NotificationConstant.NOTIFICATION_TYPE_SMS;
	}

	@SuppressWarnings("unused")
	@Override
	public void proceedSMSNotification(NotificationQueueContentTO notifyTO) {
		loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::starts====>");
		try {
			String product = getProduct(notifyTO.getConsgNo());
			/*ConsignmentNotificationTO cnNotifyTo = getConfiguredCustomers(product, notifyTO.getCurrentStatus());
			if (cnNotifyTo == null) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::notification is not configured for product["
						+ product
						+ "] and status["
						+ notifyTO.getCurrentStatus()
						+ "] for CN: "
						+ notifyTO.getConsgNo());
				return;
			}

			List<Integer> customers = cnNotifyTo.getCustomersId();
			if (customers == null) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::notification is not configured for product["
						+ product
						+ "] and status["
						+ notifyTO.getCurrentStatus()
						+ "] for CN: "
						+ notifyTO.getConsgNo());
				return;
			}*/
			// Getting consignment details
			ConsignmentDO cnDetails = notificationDao
					.getCnDetailsByCnNo(notifyTO.getConsgNo());
			if (cnDetails == null) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::consignment details are null so cannot send SMS");
				return;
			}
			
			Integer cnCustomer = cnDetails.getCustomer();
			List<NotificationConfigDO> notiConfigs = notificationDao.getNotificationDetailsByProductNStatus(product, notifyTO.getCurrentStatus());
			if(notiConfigs == null || notiConfigs.isEmpty()) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::notification is not configured for product["
						+ product
						+ "] and status["
						+ notifyTO.getCurrentStatus()
						+ "] for CN: "
						+ notifyTO.getConsgNo());
				return;
			}
			
			NotificationConfigDO cnNotifyDo = getConfiguredNotificationDetails(notiConfigs, cnCustomer);
			if(cnNotifyDo == null || cnNotifyDo.getNotifyTO() == null) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::getConfiguredNotificationDetails::notification or notify to is not configured for product["
						+ product
						+ "] and status["
						+ notifyTO.getCurrentStatus()
						+ "] for CN: "
						+ notifyTO.getConsgNo());
				return;
			}
			
			DeliveryDetailsDO drsNumber = notificationDao.
			        getdrsNumberByConsgNo(cnDetails.getConsgNo());
			
			if(drsNumber ==null){
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::Drs number is empty");
			  return;
			}
			// Getting and validating consigner/consignee
			ConsigneeConsignorDO consiner = cnDetails.getConsignor();
			ConsigneeConsignorDO consinee = cnDetails.getConsignee();
			//String consinee1 = "9167217359";
			String mobileNumber=(String) consinee.getMobile().subSequence(0, 1);
			int consineeNumber=consinee.getMobile().length();
			
			if(!(mobileNumber.equalsIgnoreCase(NotificationConstant.MOBILE_NUMBER_START_DIGIT_NINE)
                    || mobileNumber.equalsIgnoreCase(NotificationConstant.MOBILE_NUMBER_START_DIGIT_EIGHT)
                    || mobileNumber.equalsIgnoreCase(NotificationConstant.MOBILE_NUMBER_START_DIGIT_SEVEN))){
                    return;
             }
			
			if (consinee == null && consiner == null) {
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::consiner and consinee both are null so cannot send SMS");
				return;
			}
		
			ConsignmentStatusSmsDO consigStatus = notificationDao.
					        getConsignmentStatusSmsDeatails(consinee.getMobile(),cnDetails.getConsgNo(),cnDetails.getConsgStatus(),drsNumber.getDeliveryDO().getDrsNumber());
			
			if(consigStatus !=null){
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::Already we are send message for This customer" +
						" "+consinee.getMobile()+"======>"+cnDetails.getConsgNo()+"==========>"+cnDetails.getConsgStatus());
			return;
	           }
			try {
				// Get delivery details of CN
				String smsText = getSMSText(notifyTO.getConsgNo(), cnNotifyDo);
				loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::smsText::====>" + smsText);
				if (smsText == null)
					return;
				String cneMobile = null;
				String cnrMobile = null;
				if (consinee != null && consinee.getMobile() != null
						&& !consinee.getMobile().isEmpty()) {
					cneMobile = consinee.getMobile();//"9167217359";//
				} else {
					loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::Consignee mobile is null or empty for CN====>"
							+ notifyTO.getConsgNo());
				}

				if (consiner != null && consiner.getMobile() != null
						&& !consiner.getMobile().isEmpty()) {
					cnrMobile =  consiner.getMobile();//"9167217359";
				} else {
					loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::Consigner mobile is null or empty for CN====>"
							+ notifyTO.getConsgNo());
				}

				sendSMS(cnrMobile, cneMobile, cnNotifyDo, smsText,cnDetails,drsNumber);
					
			} catch (Exception ex) {
				loggger.error(
						"ConsignmentNotificationServiceImpl::proceedSMSNotification::Exception====>",
						ex);
			}
			

		} catch (Exception ex) {
			loggger.error(
					"ConsignmentNotificationServiceImpl::proceedSMSNotification::Exception====>"
							+ notifyTO.getConsgNo(), ex);
		}

		loggger.debug("ConsignmentNotificationServiceImpl::proceedSMSNotification::ends====>");
	}

	

	private void sendSMS(String cnrMobile, String cneMobile, NotificationConfigDO cnNotifyDo, 
			String smsText,ConsignmentDO cnDetails,DeliveryDetailsDO drsNumber) throws CGBusinessException, CGSystemException {
		
		/*switch(cnNotifyDo.getNotifyTO()) {
		case NotificationConstant.NOTIFY_TO_CNR_CNE:
			if (cnrMobile != null)
				smsSenderServic.prepareAndSendSms(getSenderTO(smsText, cnrMobile));
			if (cneMobile != null)
				smsSenderServic.prepareAndSendSms(getSenderTO(smsText, cneMobile));
			break;
		case NotificationConstant.NOTIFY_TO_CNR:
			if(cnrMobile != null)
				smsSenderServic.prepareAndSendSms(getSenderTO(smsText,cnrMobile));
			break;
		case NotificationConstant.NOTIFY_TO_CNE :
			if(cneMobile != null)
				smsSenderServic.prepareAndSendSms(getSenderTO(smsText,cneMobile));
			
		}*/
		SmsSenderTO	smsSenderTo=null;
		try{
		smsSenderTo=getSenderTO(smsText,cneMobile);
		if (smsSenderTo.isImmediateSend()) {
	        sendSms(smsSenderTo);
		}
		}catch(Exception e){
			loggger.error("Exception Occured while sending SMS::SmsSenderServiceImpl::sendSMS() :: ", e);
		}
		SmsDO smsDO = new SmsDO();
		ConsignmentStatusSmsDO consStatus=new ConsignmentStatusSmsDO();
	    CGObjectConverter.createDomainFromTo(smsSenderTo, smsDO);
	    smsDO.setCreatedDate(DateUtil.getCurrentDate());
	    smsDO.setUpdatedDate(DateUtil.getCurrentDate());
	    setDefaultValueToSmsDO(smsDO);
	    smsDO= smsSenderDAO.saveSmsDetails(smsDO);
	    
	    if(smsDO ==null){
	    	
	    	loggger.error("ConsignmentNotificationServiceImpl::sendSMS:: Sms id is not avaiable");
		return;
	    	
	    	
	    }
	    
	    Integer smsId =smsDO.getSmsId();
    	consStatus.setContactNumber(cneMobile);
    	consStatus.setConsgNumber(cnDetails.getConsgNo());
    	consStatus.setDrsNumber(drsNumber.getDeliveryDO().getDrsNumber());
    	consStatus.setStatus(cnDetails.getConsgStatus());
    	consStatus.setSmsId(String.valueOf(smsId));
    	
    	   notificationDao.getSaveConsgStatusOfSmsDetails(consStatus);
    	 loggger.trace("SmsSenderServiceImpl::Save Successfully::------------>:::::::");
	    
	    
	    loggger.trace("SmsSenderServiceImpl::sendSMS::END------------>:::::::");
		
		
	}
	

	private void setDefaultValueToSmsDO(SmsDO smsDO)
	  {
	    if (StringUtils.isBlank(smsDO.getStatusCode())) {
	      smsDO.setStatusCode("000");
	    }
	    if (StringUtils.isBlank(smsDO.getIsSmsSent())) {
	      smsDO.setIsSmsSent("N");
	    }
	  }
	private void loadConfigParams()
		    throws CGSystemException
		  {
		    if (configurableParams != null) {
		      return;
		    }
		    List<String> paramNames = new ArrayList();
		    paramNames.add("SMS_HOST");
		    paramNames.add("SMS_SENDER_ID");
		    paramNames.add("SMS_USER_ID");
		    paramNames.add("SMS_PASSWORD");
		    paramNames.add("SMS_CHARSET");
		    
		    List<?> configParams = this.smsSenderDAO.getConfigurableParamMapByNames(paramNames);
		    
		    configurableParams = prepareMapFromConfigParams(configParams);
		  }
	
	private static Map<String, String> prepareMapFromConfigParams(List<?> configParams)
	  {
	    Map<String, String> configMap = null;
	    if (!StringUtil.isEmptyList(configParams).booleanValue())
	    {
	      configMap = new HashMap(configParams.size());
	      for (Object configP : configParams)
	      {
	        Map map = (Map)configP;
	        configMap.put((String)map.get("paramName"), (String)map.get("paramValue"));
	      }
	    }
	    return configMap;
	  }
	 public void sendSms(SmsSenderTO smsSenderTO)
			    throws CGSystemException, CGBusinessException
			  {
			    loggger.trace("SmsSenderServiceImpl::sendSms::START------------>:::::::");
			    String isSmsSent = "N";
			    try
			    {
			      if (configurableParams == null)
			      {
			        loadConfigParams();
			        if (configurableParams == null) {
			          throw new CGBusinessException("Unable to load config params.");
			        }
			      }
			      String queryString = "senderID=%s&msisdn=%s&msg=%s&userid=%s&pwd=%s";
			      
			      String query = String.format(queryString, new Object[] { URLEncoder.encode((String)configurableParams.get("SMS_SENDER_ID"), (String)configurableParams.get("SMS_CHARSET")), URLEncoder.encode(smsSenderTO.getContactNumber(), (String)configurableParams.get("SMS_CHARSET")), URLEncoder.encode(smsSenderTO.getMessage(), (String)configurableParams.get("SMS_CHARSET")), URLEncoder.encode((String)configurableParams.get("SMS_USER_ID"), (String)configurableParams.get("SMS_CHARSET")), URLEncoder.encode((String)configurableParams.get("SMS_PASSWORD"), (String)configurableParams.get("SMS_CHARSET")) });
			      
			      HttpURLConnection urlCon = (HttpURLConnection)new URL((String)configurableParams.get("SMS_HOST")).openConnection();
			      
			      urlCon.setDoOutput(true);
			      urlCon.setRequestMethod("POST");
			      urlCon.setRequestProperty("Accept-Charset", (String)configurableParams.get("SMS_CHARSET"));
			      
			      urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + (String)configurableParams.get("SMS_CHARSET"));
			      
			      DataOutputStream wr = new DataOutputStream(urlCon.getOutputStream());
			      wr.writeBytes(query);
			      wr.flush();
			      wr.close();
			      
			      int status = urlCon.getResponseCode();
			      if (status == 200) {
			        isSmsSent = "Y";
			      }
			      smsSenderTO.setStatusCode(Integer.toString(status));
			      smsSenderTO.setIsSmsSent(isSmsSent);
			    }
			    catch (IOException e)
			    {
			      loggger.error("Exception happened in sendSms of SmsSenderServiceImpl...", e);
			      
			      throw new CGBusinessException(e);
			    }
			    loggger.trace("SmsSenderServiceImpl::sendSms::END------------>:::::::");
			  }
	
	
	
	@Override
	public String getSMSText(String consgNo, NotificationConfigDO cnNotifyDo) {
		
		String smsText = getSMSText(consgNo, cnNotifyDo.getTemplate());
		return smsText;
	}

	private NotificationConfigDO getConfiguredNotificationDetails(List<NotificationConfigDO> notiConfigs, Integer cnCustomer) throws IOException {
		//ConsignmentNotificationTO to = new ConsignmentNotificationTO();
		NotificationConfigDO customerNotificationDO = null;
		NotificationConfigDO appToAllNotificationDO = null;
		for(NotificationConfigDO deo: notiConfigs) {
			String appliToAll = deo.getApplicableToAll();
			Integer customerId = deo.getCustomerId(); 
			if(appliToAll != null && appliToAll.equalsIgnoreCase("Y")) {
				appToAllNotificationDO = deo;
			} else if(customerId.intValue() == cnCustomer.intValue()){
				customerNotificationDO = deo;
				break;
			}
		}
		
		return customerNotificationDO == null? appToAllNotificationDO : customerNotificationDO;
	}
	private SmsSenderTO getSenderTO(String smsText, String mobile) {
		SmsSenderTO to = new SmsSenderTO();
		to.setMessage(smsText);
		to.setContactNumber(mobile);
		to.setImmediateSend(true);
		return to;
	}

	@Override
	public String getProduct(String consgNo) {
		// String consgNo = notifyTO.getConsgNo();
		char series = consgNo.charAt(4);// Getting CN seriese B991P2000003
		boolean isDigit = Character.isDigit(series);
		if (isDigit)
			series = 'N';
		if (series == 'P' || series == 'N') {

		}
		return series + "";
	}

	@Override
	public String getSMSText(String cnNo, String template) {
		loggger.debug("ConsignmentNotificationServiceImpl::getSMSText::starts====>");
		String smsText = null;
		DeliveryDetailsDO delDetails = getDeliveryDetailsByCn(cnNo);
		if (delDetails != null) {
			smsText = getDeliveryStatusSMSText(delDetails, cnNo, template);
		} else {
			loggger.debug("ConsignmentNotificationServiceImpl::getSMSText::delDetails not found in DB====>");
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getSMSText::ends====>"
				+ smsText);
		return smsText;
	}

	private String getDeliveryStatusSMSText( DeliveryDetailsDO deliveryDtl, String cnNo, String template) {
		
		String status = deliveryDtl.getDeliveryStatus();
		String smsText = null;
		try {

			if (status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_DELIVERY)) {
				String receiverName = deliveryDtl.getReceiverName();
				Date delDate = deliveryDtl.getDeliveryDate();
				String dateStr = DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(delDate);
				smsText = template == null ? getDeliveredSMSTextByTemplate(receiverName,
						dateStr, cnNo):getDeliveredSMSTextByPlaceholder(receiverName,
								dateStr, cnNo, template);
				loggger.debug("ConsignmentNotificationServiceImpl::getSMSText::deliText====>"
						+ smsText);
			} else if (status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_PENDING)) {
				String reason = deliveryDtl.getReasonDO() == null ? ""
						: deliveryDtl.getReasonDO().getReasonName();
				OfficeDO branchOffice = deliveryDtl.getDeliveryDO()
						.getCreatedOfficeDO();
				// FIXME: replace empty string in below line with corporate
				// office contact number
				String branchContact = branchOffice == null ? ""
						: branchOffice.getPhone() == null ? ""
								: branchOffice.getPhone();
				smsText = template == null ? getPendingSMSTextByTemplate(cnNo, reason,
						branchContact): getPendingSMSTextByPlaceholder(cnNo, reason,branchContact, template);

			}
		} catch (Exception e) {
			loggger.error(
					"ConsignmentNotificationServiceImpl::getSMSText::ERROR====>",
					e);
			e.printStackTrace();
		}
		return smsText;
	}
	private String getPendingSMSTextByPlaceholder(String consgNo, String pendigReason,
			String delBrContactNo, String template) throws IOException {
		VelocityContext context = new VelocityContext();
		context.put("consgNo", consgNo);
		context.put("pendigReason", pendigReason);
		context.put("delBrContactNo", delBrContactNo);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context,writer, "Customer template for ["+ consgNo + "] is empty" , template);
		return writer.toString();
	}

	private String getDeliveredSMSTextByPlaceholder(String receiverName,
			String dateStr, String cnNo, String template) throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("consg", cnNo);
		context.put("receiverName", receiverName);
		context.put("deliveryDate", dateStr);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context,writer, "Customer template for ["+ cnNo + "] is empty" , template);
		return writer.toString();
	}

	@Override
	public String getPendingSMSTextByTemplate(String consgNo,
			String pendigReason, String delBrContactNo) throws Exception {
		Template delTemp = velocityEngine.getTemplate("PendingSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", consgNo);
		context.put("pendigReason", pendigReason);
		context.put("delBrContactNo", delBrContactNo);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	@Override
	public String getDeliveredSMSTextByTemplate(String receiverName,
			String deliveryDate, String consg) throws Exception {
		Template delTemp = velocityEngine.getTemplate("DeliveredSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consg", consg);
		context.put("receiverName", receiverName);
		context.put("deliveryDate", deliveryDate);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}
//TransitStatuMessage.vm
	@Override
	public String getTransitStatusTextByTemplate() throws Exception {
		Template delTemp = velocityEngine.getTemplate("TransitStatuMessage.vm");
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	@Override
	public String testStringTemplate(String consignment, String mobile, String status)  {
		NotificationQueueContentTO notifyTO = new NotificationQueueContentTO();
		notifyTO.setConsgNo(consignment);
		notifyTO.setCurrentStatus(status);
		proceedSMSNotification(notifyTO);
		//Template delTemp = velocityEngine.
		/*VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		context.put("deliveryDate", new Date());
		//context.put("consg", consg);
		context.put("consg", consignment);
		velocityEngine.evaluate(context, writer, "Hello", "Dear Customer,CN:$consg is delivered to $receiverName on $deliveryDate Thank you for making First Flight your first choice");
		return writer.toString();*/
		return null;
	}

	public ConsignmentNotificationTO getConfiguredCustomers(String product,
			String cnStatus) {
		loggger.debug("ConsignmentNotificationServiceImpl::getConfiguredCustomers::starts====>");
		if (configuredValues.isEmpty()) {
			prepareConfigurationData();
		}
		ConsignmentNotificationTO notifiDetails = (ConsignmentNotificationTO) configuredValues
				.get(product, cnStatus);
		loggger.debug("ConsignmentNotificationServiceImpl::getConfiguredCustomers::notification details for product["
				+ product
				+ "] and cnStatus["
				+ cnStatus
				+ "] is"
				+ notifiDetails);
		return notifiDetails;
	}

	/*private void prepareCOnfigurationData() {
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::starts====>");
		List<NotificationConfigDO> notiDeos = notificationDao
				.getNotificationConfig();
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::customers====>"
				+ notiDeos);
		List<Integer> customersId = null;
		ConsignmentNotificationTO notifyTO = null;
		if (notiDeos != null && !notiDeos.isEmpty()) {
			for (NotificationConfigDO deo : notiDeos) {
				ProductDO productDO = deo.getProduct();
				
				String product = productDO.getConsgSeries();
				String status = deo.getConsgStatus();

				customersId = (List<Integer>) customersList.get(product, status);
				notifyTO = (ConsignmentNotificationTO) configuredValues.get(product, status);

				if (customersId == null) {
					customersId = new ArrayList();
				}

				if (notifyTO == null) {
					notifyTO = new ConsignmentNotificationTO();
					notifyTO.setNotifyType(deo.getNotifyTO());
					notifyTO.setCustomersId(customersId);
					configuredValues.put(product, status, notifyTO);
				}

				if (!deo.getApplicableToAll().equalsIgnoreCase("Y")) {
					customersId.add(deo.getCustomerId());
				}
				customersList.put(product, status, customersId);
				loggger.debug("ConsignmentNotificationServiceImpl::getConfiguredCustomers::adding in map product["+product+"] and cnStatus["+status+"] notify object" + notifyTO);
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::customersId====>"
						+ customersId);
			}

			MapIterator it = configuredValues.mapIterator();
			while (it.hasNext()) {
				it.next();
				MultiKey mk = (MultiKey) it.getKey();
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::key[product] " + mk.getKey(0));
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::key[status] " + mk.getKey(1));
				ConsignmentNotificationTO notiTO = (ConsignmentNotificationTO) it.getValue();
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::getCustomersId: "+ notiTO.getCustomersId() + "and notifyTO: " +  notiTO.getNotifyType());
			}
		} else {
			loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::notification is not configured");
		}
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::ends====>");
	}*/
	private void prepareConfigurationData() {
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::starts====>");
		List<NotificationConfigDO> notiDeos = notificationDao
				.getNotificationConfig();
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::customers====>"
				+ notiDeos);
		List<Integer> customersId = null;
		ConsignmentNotificationTO notifyTO = null;
		if (notiDeos != null && !notiDeos.isEmpty()) {
			for (NotificationConfigDO deo : notiDeos) {
				ProductDO productDO = deo.getProduct();
				
				String product = productDO.getConsgSeries();
				String status = deo.getConsgStatus();

				customersId = (List<Integer>) customersList.get(product, status);
				notifyTO = (ConsignmentNotificationTO) configuredValues.get(product, status);

				if (customersId == null) {
					customersId = new ArrayList();
				}

				if (notifyTO == null) {
					notifyTO = new ConsignmentNotificationTO();
					notifyTO.setNotifyType(deo.getNotifyTO());
					notifyTO.setCustomersId(customersId);
					configuredValues.put(product, status, notifyTO);
				}

				if (!deo.getApplicableToAll().equalsIgnoreCase("Y")) {
					customersId.add(deo.getCustomerId());
				}
				customersList.put(product, status, customersId);
				loggger.debug("ConsignmentNotificationServiceImpl::getConfiguredCustomers::adding in map product["+product+"] and cnStatus["+status+"] notify object" + notifyTO);
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::customersId====>"
						+ customersId);
			}

			MapIterator it = configuredValues.mapIterator();
			while (it.hasNext()) {
				it.next();
				MultiKey mk = (MultiKey) it.getKey();
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::key[product] " + mk.getKey(0));
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::key[status] " + mk.getKey(1));
				ConsignmentNotificationTO notiTO = (ConsignmentNotificationTO) it.getValue();
				loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::getCustomersId: "+ notiTO.getCustomersId() + "and notifyTO: " +  notiTO.getNotifyType());
			}
		} else {
			loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::notification is not configured");
		}
		loggger.debug("ConsignmentNotificationServiceImpl::prepareCOnfigurationData::ends====>");
	}
	@Override
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cn) {
		return notificationDao.getDeliveryDetailsByCn(cn);
	}
	
	@Override
	public void postNotification(NotificationQueueContentTO notificationTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendSMSNotification(String cnNumber, String mobile) throws CGBusinessException, CGSystemException {
		if(cnNumber == null || cnNumber.isEmpty() || mobile == null || mobile.isEmpty())
			throw new CGBusinessException("consignment and mobile number cannot be null");
		
		String smsText = null;
		try {
			smsText = getNotificationText(cnNumber);
			smsSenderServic.prepareAndSendSms(getSenderTO(smsText, mobile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String getNotificationText(String cnNumber) throws Exception {
		String smsText = null;
		ConsignmentDO consignment = notificationDao.getCnDetailsByCnNo(cnNumber);
		if(consignment == null) {
			BookingDO booking = notificationDao.getBookingDetailsByCnNo(cnNumber);
			if(booking == null)
				throw new CGBusinessException("consignment details not found");
			
			 smsText = getTransitStatusTextByTemplate();
		} else {
			 DeliveryDetailsDO deliveryDtl = getDeliveryDetailsByCn(cnNumber);
			 if(deliveryDtl == null) {
				 smsText = getTransitStatusTextByTemplate();
			 } else {
				 smsText = getDeliveryStatusSMSText(deliveryDtl, cnNumber, null);
			 }
		}
		
		
		return smsText;
	}
	@Override
	public void sendEmailNotification(String cnNumber, String email) throws CGBusinessException {
		
		if(cnNumber == null || cnNumber.isEmpty() || email == null || email.isEmpty())
			throw new CGBusinessException("consignment and mobile number cannot be null");
		
		String smsText = null;
		 DeliveryDetailsDO deliveryDtl = getDeliveryDetailsByCn(cnNumber);
		 if(deliveryDtl == null) {
			 smsText = "   Dear Customer,\nYour consignment(s) is in transit please try again later for additional details";
		 } else {
			 smsText = getDeliveryStatusSMSText(deliveryDtl, cnNumber, null);
		 }
	}
	
	
	public void setMailSender(EmailSenderUtil mailSender) {
		this.mailSender = mailSender;
	}

	public ConsignmentNotificationDAO getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(ConsignmentNotificationDAO notificationDao) {
		this.notificationDao = notificationDao;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setSmsSenderServic(SmsSenderService smsSenderServic) {
		this.smsSenderServic = smsSenderServic;
	}
	
	

	public SmsSenderDAO getSmsSenderDAO() {
		return smsSenderDAO;
	}

	public void setSmsSenderDAO(SmsSenderDAO smsSenderDAO) {
		this.smsSenderDAO = smsSenderDAO;
	}


	
}
