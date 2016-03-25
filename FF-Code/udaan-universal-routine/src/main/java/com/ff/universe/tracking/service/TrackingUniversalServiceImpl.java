package com.ff.universe.tracking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;
import com.ff.universe.tracking.dao.TrackingUniversalDAO;


/**
 * @author nkattung
 * 
 */
public class TrackingUniversalServiceImpl implements TrackingUniversalService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(TrackingUniversalServiceImpl.class);
	TrackingUniversalDAO trackingUniversalDAO;

	private OrganizationCommonService organizationCommonService;

	private BusinessCommonService businessCommonService;

	private GeographyCommonService geographyCommonService;

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param businessCommonService
	 *            the businessCommonService to set
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	public void setTrackingUniversalDAO(
			TrackingUniversalDAO trackingUniversalDAO) {
		this.trackingUniversalDAO = trackingUniversalDAO;
	}

	private SequenceGeneratorService sequenceGeneratorService;

	/**
	 * Sets the sequence generator service.
	 * 
	 * @param sequenceGeneratorService
	 *            the new sequence generator service
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	@Override
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("TrackingUniversalServiceImpl::getProcess()::START");
		ProcessDO processDO = null;
		ProcessTO processTO = null;
		try {
			processDO = trackingUniversalDAO.getProcess(process);
			if (!StringUtil.isNull(processDO)) {
				processTO = new ProcessTO();
				// converts DO to TO
				processTO = (ProcessTO) CGObjectConverter.createToFromDomain(
						processDO, processTO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getProcess() :" + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("TrackingUniversalServiceImpl::getProcess()::END");
		return processTO;
	}

	@Override
	public OfficeTO getLoggedInOffice(OfficeTO office)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("TrackingUniversalServiceImpl::getLoggedInOffice()::START");
		OfficeDO officeDO = null;
		OfficeTO officeTO = null;
		try {
			officeDO = trackingUniversalDAO.getLoggedInOffice(office);
			if (!StringUtil.isNull(officeDO)) {
				officeTO = new OfficeTO();
				// converts DO to TO
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getProcess() :" + e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("TrackingUniversalServiceImpl::getLoggedInOffice()::END");
		return officeTO;
	}

	public String formatArtifactPath(String template, ProcessMapDO processMapDO)
			throws NumberFormatException, CGBusinessException,
			CGSystemException {
		LOGGER.trace("TrackingUniversalServiceImpl::formatArtifactPath()::START");
		Map<String, String> map = new HashMap<String, String>();
		map.put(processMapDO.getKey1(), processMapDO.getValue1());
		map.put(processMapDO.getKey2(), processMapDO.getValue2());
		map.put(processMapDO.getKey3(), processMapDO.getValue3());
		map.put(processMapDO.getKey4(), processMapDO.getValue4());
		map.put(processMapDO.getKey5(), processMapDO.getValue5());
		map.put(processMapDO.getKey6(), processMapDO.getValue6());
		map.put(processMapDO.getKey7(), processMapDO.getValue7());
		map.put(processMapDO.getKey8(), processMapDO.getValue8());
		map.put(processMapDO.getKey9(), processMapDO.getValue9());
		map.put(processMapDO.getKey10(), processMapDO.getValue10());
		map.put(processMapDO.getKey11(), processMapDO.getValue11());
		map.put(processMapDO.getKey12(), processMapDO.getValue12());
		map.put(processMapDO.getKey13(), processMapDO.getValue13());
		map.put(processMapDO.getKey14(), processMapDO.getValue14());

		String newValue = null;
		String mnfstDestCity=null;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String processKey = entry.getKey();
			String processValue = entry.getValue();
			//use switch-case
			if(StringUtils.isNotEmpty(processKey)){
				processKey = processKey.trim();
				switch (processKey) {
				case "originOff":
				case "destOff":
				case "hubOff":
					if (processKey.equalsIgnoreCase("destOff") && StringUtils.isEmpty(processValue)){
						map.put(processKey, mnfstDestCity);
					}else if(StringUtils.isNotEmpty(processValue)){
						OfficeTO officeTO = getOfficeByOfficeId (Integer.parseInt(processValue));
						String offcName = officeTO.getOfficeName() + "-" + officeTO.getOfficeTypeTO().getOffcTypeDesc();
						newValue = "<a class='lable1' onclick='showOffice("+ processValue + ")' href=\"#\">" + offcName + "</a>";
						map.put(processKey, newValue);
					}
					break;
				case "receiveType":
					if (processValue.equalsIgnoreCase("O")){
						//In case of Out-station RCVE Origin office is 'null'
						//as discussed with punith, in case of Out station receive template should be like below. since we never get org off in this case.
						template = "Received (Manifest No : {manifestNo}) at {destOff} having Weight {weight} kg.";
					}
					break;
				case "custName":
					if (StringUtils.isNotEmpty(processValue)) {
						if(StringUtils.equalsIgnoreCase(processMapDO.getArtifactType(), "M")){
							map.put(processKey, processValue);
						}else{
							CustomerTO custTO = businessCommonService.getCustomer(Integer.parseInt(processValue));
							newValue = custTO.getBusinessName();
							map.put(processKey, newValue);
						}
					}
					break;
				case "orgCity":
					if (StringUtils.isNotEmpty(processValue)) {
						CityTO cityTO = new CityTO();
						cityTO.setCityId(Integer.parseInt(processValue));
						cityTO = geographyCommonService.getCity(cityTO);
						newValue = cityTO.getCityName();
						map.put(processKey, newValue);
					}
					break;
				case "destCity":
					if (StringUtils.isNotEmpty(processValue)) {
						CityTO cityTO = new CityTO();
						cityTO.setCityId(Integer.parseInt(processValue));
						cityTO = geographyCommonService.getCity(cityTO);
						mnfstDestCity = cityTO.getCityName();
						map.put(processKey, mnfstDestCity);
					}
					break;
				case "noOfPieces":
					if (StringUtils.isEmpty(processValue)) {
						newValue = CommonConstants.DEFAULT_NO_PIECES;
						map.put(processKey, newValue);
					}
					break;
				case "stopDlvReqOff":
					if(StringUtils.isNotEmpty(processValue)){
						OfficeTO officeTO = getOfficeByOfficeId(Integer.parseInt(processValue));
						if(!StringUtil.isNull(officeTO)){
							map.put(processKey, officeTO.getOfficeName());
						}
					}
					break;
				case "transportModeAndNo":
					if(StringUtils.isNotEmpty(processValue) && StringUtils.isEmpty(map.get("flightNo"))
							&& StringUtils.isEmpty(map.get("trainNo")) && StringUtils.isEmpty(map.get("vehicleNo"))){
						String values[] = processValue.split("#");
						StringBuilder txt = new StringBuilder();
						txt.append(values[0]);
						txt.append(" Connect by ");
						txt.append(values[1]);
						map.put(processKey, txt.toString());
					}
					break;
				case "flightNo":
					if (StringUtils.isNotEmpty(processValue) && StringUtils.isEmpty(map.get("transportModeAndNo"))
							&& !StringUtils.equalsIgnoreCase(processValue, CommonConstants.HYPHEN)) {
						StringBuilder txt = new StringBuilder();
						txt.append("Air Connect by ");
						txt.append(processValue);
						map.put("transportModeAndNo", txt.toString());
					}
					break;
				case "trainNo":
					if (StringUtils.isNotEmpty(processValue) && StringUtils.isEmpty(map.get("transportModeAndNo"))
							&& !StringUtils.equalsIgnoreCase(processValue, CommonConstants.HYPHEN)) {
						StringBuilder txt = new StringBuilder();
						txt.append("Train Connect by ");
						txt.append(processValue);
						map.put("transportModeAndNo", txt.toString());
					}
					break;
				case "vehicleNo":
					if (StringUtils.isNotEmpty(processValue) && StringUtils.isEmpty(map.get("transportModeAndNo"))) {
						StringBuilder txt = new StringBuilder();
						txt.append("Road Connect by ");
						txt.append(processValue);
						map.put("transportModeAndNo", txt.toString());
					}
					break;
				default:
					break;
				}
			}
		}
		StrSubstitutor sub = new StrSubstitutor(map, "{", "}");
		String result = sub.replace(template);
		LOGGER.trace("TrackingUniversalServiceImpl::formatArtifactPath()::END");
		return result;
	}

	public String formatArtifactPath(String template,
			ProcessMapDO processMapDO, String flag)
			throws NumberFormatException, CGBusinessException,
			CGSystemException {
		LOGGER.trace("TrackingUniversalServiceImpl::formatArtifactPath()::START");
		Map<String, String> map = new HashMap<String, String>();
		String key1 = UniversalTrackingConstants.KEY_1;
		String key2 = UniversalTrackingConstants.KEY_2;
		String key3 = UniversalTrackingConstants.KEY_3;
		//Added for weight in delivery template
		//String key4 = UniversalTrackingConstants.KEY_4;
		String key5 = UniversalTrackingConstants.KEY_5;
		map.put(processMapDO.getKey3(), processMapDO.getValue3());
		map.put(key1, null);
		map.put(key2, null);
		map.put(key3, null);
		//map.put(key4, processMapDO.getValue4());
		map.put(key5, processMapDO.getValue5());
		map.put("DeliveryDate", DateUtil.getDDMMYYYYDateString(processMapDO.getProcessDate()));
		map.put("DeliveryTime", DateUtil.extractTimeFromDate(processMapDO.getProcessDate()));
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey() != null) {
				if (entry.getKey().equalsIgnoreCase("other")) {
					String value = entry.getValue();
					if (value != null) {
						String values[] = value.split("#");
						int i = 0;
						for (String val : values) {
							switch (i) {
							case 0:
								map.put(key1, val);
								break;
							case 1:
								if (StringUtils.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED, val)){
									val = UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_DELIVERED;
									template = UniversalTrackingConstants.DELIVERED_CN_PATH;
								} else if (StringUtils.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_PENDING, val)) {
									val = UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_PENDING;
								}
								map.put(key2, val);
								break;
							case 2:
								map.put(key3, val);
								break;
							}
							i++;
						}
					}
				}
			}
		}
		map.remove(processMapDO.getKey3());
		StrSubstitutor sub = new StrSubstitutor(map, "{", "}");
		String result = sub.replace(template);
		LOGGER.trace("TrackingUniversalServiceImpl::formatArtifactPath()::END");
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.tracking.service.TrackingUniversalService#createProcessNumber
	 * (com.ff.tracking.ProcessTO, com.ff.organization.OfficeTO)
	 */
	@Override
	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("TrackingUniversalServiceImpl::createProcessNumber()::START");
		String processNo = "";
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			if (processTO != null) {
				if (processTO.getProcessCode() == null) {
					processTO = getProcess(processTO);
				}
				sb.append(processTO.getProcessCode());
			}
			if (officeTO != null) {
				if (officeTO.getOfficeCode() == null) {
					officeTO = getLoggedInOffice(officeTO);
				}
				sb.append(officeTO.getOfficeCode());
			}
			String seqNum = generateSeqNum();
			sb.append(seqNum);
			processNo = sb.toString();
		}  catch (CGBusinessException e) {
			LOGGER.error("Exception in createProcessNumber() :"
					+ e.getMessage());
			throw e;
		}catch (Exception e) {
			LOGGER.error("Exception in createProcessNumber() :"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("TrackingUniversalServiceImpl::createProcessNumber()::END");
		return processNo;
	}

	/**
	 * Generates 7 digit Running Number
	 * 
	 * @param noOfOrders
	 *            the no of orders
	 * @return the sequence Number
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private String generateSeqNum() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("TrackingUniversalServiceImpl::generateSeqNum()::START");
		List<String> sequenceNumber = null;
		String seqNum = "";
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO
				.setProcessRequesting(UniversalTrackingConstants.TRACKING_RUNNING_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(new Integer(1));
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		if (!sequenceNumber.isEmpty() && sequenceNumber != null) {
			seqNum = sequenceNumber.get(0);
		}
		LOGGER.trace("TrackingUniversalServiceImpl::generateSeqNum()::END");
		return seqNum;
	}

	/**
	 * gets the reporting Office of the given officeId
	 * 
	 * @param officeId
	 * @return officeDO
	 * @throws CGBusinessException
	 */
	public OfficeTO getReportingOffice(Integer officeId)
			throws CGBusinessException {
		LOGGER.trace("TrackingUniversalServiceImpl::getReportingOffice()::START");
		OfficeDO officeDO = null;
		OfficeTO officeTO = new OfficeTO();
		OfficeTypeTO typeTO = new OfficeTypeTO();
		OfficeTypeTO typeTO1 = new OfficeTypeTO();
		try {

			officeDO = trackingUniversalDAO.getOfficeByOfficeId(officeId);
			if (!StringUtil.isNull(officeDO)) {
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				if (!StringUtil.isNull(officeDO.getOfficeTypeDO())) {
					typeTO1 = (OfficeTypeTO) CGObjectConverter.createToFromDomain(
							officeDO.getOfficeTypeDO(), typeTO);
				}
			}
						
			officeTO.setOfficeTypeTO(typeTO1);

		} catch (CGSystemException e) {
			LOGGER.debug("Exception in getReportingOffice() :" + e.getMessage());

		}
		LOGGER.trace("TrackingUniversalServiceImpl::getReportingOffice()::END");
		return officeTO;
	}

	public List<Integer> getOffice(Integer officeId, Integer cityId)
			throws CGBusinessException {
		LOGGER.trace("TrackingUniversalServiceImpl::getOffice()::START");
		List<OfficeDO> officeDOs = null;
		List<Integer> officeIds = new ArrayList<>();
		try {
			officeDOs = trackingUniversalDAO.getOfficeByDestCityId(officeId,
					cityId);
			for (OfficeDO officeDO : officeDOs) {
				officeIds.add(officeDO.getOfficeId());
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception occurs in TrackingUniversalServiceImpl::getOffice()::" 
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("TrackingUniversalServiceImpl::getOffice()::END");
		return officeIds;

	}
	@Override
	public OfficeTO getOfficeByOfficeId(Integer officeId) throws CGBusinessException, CGSystemException{
		return organizationCommonService.getOfficeDetails(officeId);
	}
}
