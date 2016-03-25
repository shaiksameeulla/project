package com.ff.admin.tracking.common.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.common.dao.TrackingCommonDAO;
import com.ff.domain.tracking.BulkCnTrackDO;
import com.ff.tracking.TrackingBulkImportTO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;

public class TrackingCommonServiceImpl implements TrackingCommonService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TrackingCommonServiceImpl.class);

	private TrackingCommonDAO trackingCommonDAO;

	/**
	 * @param trackingCommonDAO
	 *            the trackingCommonDAO to set
	 */
	public void setTrackingCommonDAO(TrackingCommonDAO trackingCommonDAO) {
		this.trackingCommonDAO = trackingCommonDAO;
	}

	public List<TrackingBulkImportTO> getBulkTrackDetails(TrackingBulkImportTO bulkTO) throws CGSystemException,
			NumberFormatException, CGBusinessException {
		LOGGER.trace("TrackingCommonServiceImpl::getBulkTrackDetails()::START");
		List<TrackingBulkImportTO> bulkTOs = null;
		if (!StringUtil.isEmptyColletion(bulkTO.getValidConsg())) {
			// Prepare the CN string with valid consignments list.
			String cnToTrack = StringUtil.collectionToDelimitedString(bulkTO.getValidConsg(),CommonConstants.COMMA,"'","'");
			cnToTrack = CommonConstants.SPACE + cnToTrack + CommonConstants.SPACE;
			List<BulkCnTrackDO> processMapDOs = trackingCommonDAO.getDetailedCommonTracking(cnToTrack, bulkTO.getType());
			if (StringUtil.isEmptyList(processMapDOs)) {
				throw new CGBusinessException(AdminErrorConstants.TRACKING_DETAILS_NOT_FOUND);
			}
			bulkTOs = createToListFromDomainList(processMapDOs, bulkTO);
		}
		LOGGER.trace("TrackingCommonServiceImpl::getBulkTrackDetails()::END");
		return bulkTOs;
	}

	private List<TrackingBulkImportTO> createToListFromDomainList(List<BulkCnTrackDO> processMapDOs, TrackingBulkImportTO bulkTO1) {
		TrackingBulkImportTO bulkTO = null;
		NumberFormat formatter = new DecimalFormat("#0.000");
		List<TrackingBulkImportTO> bulkTOs = new ArrayList<>(processMapDOs.size());
		List<String> trackedConsgNos = new ArrayList<>(processMapDOs.size());
		
		for (BulkCnTrackDO bulkCnTrackDO : processMapDOs) {
			bulkTO = new TrackingBulkImportTO();
			bulkTO.setConsgNum(bulkCnTrackDO.getConsgNo());
			bulkTO.setRefNum(bulkCnTrackDO.getRefNo());
			bulkTO.setBookingDate(bulkCnTrackDO.getBookingDate());
			bulkTO.setOrigin(bulkCnTrackDO.getOriginCity());
			bulkTO.setDestination(bulkCnTrackDO.getDestCity());
			bulkTO.setStatus(bulkCnTrackDO.getCnStatus());
			bulkTO.setPendingReason(bulkCnTrackDO.getPendingReason());
			bulkTO.setDelvDate(bulkCnTrackDO.getDeliveryDate());
			bulkTO.setRcvrName(bulkCnTrackDO.getReceiverName());
			if(!StringUtil.isNull(bulkCnTrackDO.getCnWeight())){
				Double cnWeight = Double.parseDouble(bulkCnTrackDO.getCnWeight());
				bulkTO.setWeight(formatter.format(cnWeight));
			}
			bulkTO.setOgmNum(bulkCnTrackDO.getOgmNo());
			bulkTO.setOgmDate(bulkCnTrackDO.getOgmDate());
			bulkTO.setBplNum(bulkCnTrackDO.getBplNo());
			bulkTO.setBplDate(bulkCnTrackDO.getBplDate());
			bulkTO.setCdNum(bulkCnTrackDO.getCdNo());
			bulkTO.setCdDate(bulkCnTrackDO.getCdDate());
			bulkTO.setFlightNum(bulkCnTrackDO.getFlightNo());
			bulkTO.setFlightDept(bulkCnTrackDO.getFlightDep());
			bulkTO.setFlightArrvl(bulkCnTrackDO.getFlightArr());
			bulkTO.setRcvDate(bulkCnTrackDO.getRcvDate());
			bulkTO.setManifestDate(bulkCnTrackDO.getInMnfstDate());
			
			//Enhancement added new columns DRS No,FS/Third Party Name ,delivery branch name
			bulkTO.setDrsNo(bulkCnTrackDO.getDrsNo());
			bulkTO.setThirdPartyName(bulkCnTrackDO.getThirdPartyName());
			bulkTO.setDlvBranchName(bulkCnTrackDO.getDlvBranchName());
			
			bulkTO.setInValidConsg(bulkTO1.getInValidConsg());
			bulkTO.setInValidRef(bulkTO1.getInValidRef());
			bulkTOs.add(bulkTO);
			
			if (StringUtils.equalsIgnoreCase(bulkTO1.getType(), "CN"))
				trackedConsgNos.add(bulkCnTrackDO.getConsgNo().replace("*", ""));
			else
				trackedConsgNos.add(bulkCnTrackDO.getRefNo().replace("*", ""));
		}
		/*artf3783378 : NO OGM C/MENTS ARE NOT REFLECTING IN BULK/MULTIPLE TRACKING*/
		/* No.of Trackable consignments are more than the tracked consignments */
		List<String> trackableConsgNos = bulkTO1.getValidConsg();
		/*Remove duplicates elements*/
		trackableConsgNos = new ArrayList<String>(new LinkedHashSet<String>(trackableConsgNos));
		//For Bulk or Multiple tracking
		trackableConsgNos.remove("Consignment No");
		trackableConsgNos.remove("Reference No");
		if (trackableConsgNos.size() > processMapDOs.size()) {
			for (String consgNo : trackableConsgNos) {
				/* Trackable consignment not in tracked consignments list */
				if (Collections.frequency(trackedConsgNos, consgNo) == 0) {
					bulkTO = new TrackingBulkImportTO();
					if (StringUtils.equalsIgnoreCase(bulkTO1.getType(), "CN"))
						bulkTO.setConsgNum(consgNo);
					else
						bulkTO.setRefNum(consgNo);
					bulkTO.setInValidConsg(bulkTO1.getInValidConsg());
					bulkTO.setInValidRef(bulkTO1.getInValidRef());

					bulkTOs.add(bulkTO);
				}
			}
		}
		
		return bulkTOs;
	}
	
	@Override
	public TrackingBulkImportTO isValidConsgNum(List<String> consgNum,
			TrackingBulkImportTO bulkTO) {
		LOGGER.trace("TrackingCommonServiceImpl::isValidConsgNum()::START");
		List<String> validConsg = new ArrayList<>();
		List<String> inValidConsg = new ArrayList<>();
		List<String> inValidRef = new ArrayList<>();
		
		String numpattern = ".*[0-9].*";
		String alphaNumeric = "[a-zA-Z0-9]*$";
		
		if (bulkTO.getType().equalsIgnoreCase(
				UniversalTrackingConstants.CONSG_NUMBER)) {
			for (String num : consgNum) {
				if(StringUtils.isEmpty(num)){
					continue;
				}
				num = StringUtil.trimWhitespace(num);
				int consgSize = num.length();
				Pattern pattern1 = Pattern.compile(alphaNumeric);
				Pattern pattern2 = Pattern.compile(numpattern);
				Matcher matcher1 = null;
				Matcher matcher2 = null;
				if(consgSize >= 5){
					matcher1 = pattern1.matcher(num.substring(0, 5));
					matcher2 = pattern2.matcher(num.substring(5));
				}
				
				if (consgSize == 12 && matcher1.matches() && matcher2.matches()) {
					validConsg.add(num);
				} else {
					if(!StringUtils.equalsIgnoreCase(AdminSpringConstants.CONSG_NUM, num)){
						inValidConsg.add(num);
					}
				}
			}
		}else if (bulkTO.getType().equalsIgnoreCase(
				UniversalTrackingConstants.REF_NUMBER)){
			for (String num : consgNum) {
				if(StringUtils.isEmpty(num)){
					continue;
				}
				num = StringUtil.trimWhitespace(num);
				int consgSize = num.length();
				Pattern pattern1 = Pattern.compile(alphaNumeric);
				Pattern pattern2 = Pattern.compile(numpattern);
				Matcher matcher1 = null;
				Matcher matcher2 = null;
				if(consgSize >= 5){
					matcher1 = pattern1.matcher(num.substring(0, 5));
					matcher2 = pattern2.matcher(num.substring(5));
				}
				if (consgSize == 12 && matcher1.matches() && matcher2.matches()) {
					if(!StringUtils.equalsIgnoreCase(AdminSpringConstants.REF_NUM, num)){
						inValidRef.add(num);
					}
				} else {
					validConsg.add(num);
				}
			}
		}
		
		bulkTO.setValidConsg(validConsg);
		bulkTO.setInValidConsg(inValidConsg);
		bulkTO.setInValidRef(inValidRef);
		LOGGER.trace("TrackingCommonServiceImpl::isValidConsgNum()::END");
		return bulkTO;
	}
}
