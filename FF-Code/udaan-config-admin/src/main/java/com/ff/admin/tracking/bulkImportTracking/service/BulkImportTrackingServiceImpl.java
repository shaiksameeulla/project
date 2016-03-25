/**
 * 
 */
package com.ff.admin.tracking.bulkImportTracking.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.common.service.TrackingCommonService;
import com.ff.tracking.TrackingBulkImportTO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;

/**
 * @author uchauhan
 * 
 */
public class BulkImportTrackingServiceImpl implements BulkImportTrackingService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkImportTrackingServiceImpl.class);

	private TrackingCommonService trackingCommonService;
	
	/**
	 * @param trackingCommonService
	 *            the trackingCommonService to set
	 */
	public void setTrackingCommonService(
			TrackingCommonService trackingCommonService) {
		this.trackingCommonService = trackingCommonService;
	}

	/**
	 * @see com.ff.admin.tracking.bulkImportTracking.service.BulkImportTrackingService#getBulkConsgDetails(java.lang.String,
	 *      com.ff.tracking.TrackingBulkImportTO,
	 *      org.apache.struts.upload.FormFile) May 13, 2013
	 * @param fileUrl
	 * @param bulkTO
	 * @param myFile
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 *             getBulkConsgDetails BulkImportTrackingService uchauhan
	 */

	@Override
	public List<TrackingBulkImportTO> getBulkConsgDetails(String fileUrl,
			FormFile myFile, TrackingBulkImportTO bulkTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BulkImportTrackingServiceImpl::getBulkConsgDetails()::START");
		List<TrackingBulkImportTO> trackingBulkImportTOs = new ArrayList<>();
		List<String> allConsgNos = new ArrayList<>();
		List<List> numbers = CGExcelUploadUtil
				.getAllRowsValues(fileUrl, myFile);
		if(!StringUtil.isEmptyList(numbers)){
			List<String> header = numbers.get(0);
			// call to validate the header list
			boolean reslt = isValidHeader(header.get(0), bulkTO);
			if (!reslt) {
				throw new CGBusinessException(
						AdminSpringConstants.INVALID_NUMBER_TYPE);
			}
			for (List<String> cons : numbers) {
				allConsgNos.add(cons.get(0));
			}
			if (allConsgNos.size() > 1001) {
				throw new CGBusinessException(
						AdminErrorConstants.CONSIGNMENT_EXCEED_LIMIT);
			}
			bulkTO = trackingCommonService.isValidConsgNum(allConsgNos, bulkTO);
			if (!StringUtil.isEmptyColletion(bulkTO.getValidConsg())) {
				trackingBulkImportTOs = trackingCommonService
						.getBulkTrackDetails(bulkTO);
			} else {
				throw new CGBusinessException(
						AdminSpringConstants.INVALID_NUMBER_TYPE);
			}
		}else{
			throw new CGBusinessException(
					AdminErrorConstants.PROVIDE_NUMBERS_TO_TRACK);
		}
		LOGGER.trace("BulkImportTrackingServiceImpl::getBulkConsgDetails()::END");
		return trackingBulkImportTOs;
	}

	public boolean isValidHeader(String header, TrackingBulkImportTO bulkTO) {
		LOGGER.trace("BulkImportTrackingServiceImpl::isValidHeader()::START");
		Boolean flag = Boolean.FALSE;
		if (bulkTO.getType().equalsIgnoreCase(
				UniversalTrackingConstants.CONSG_NUMBER)
				&& header.equalsIgnoreCase(AdminSpringConstants.CONSG_NUM)) {
			flag = Boolean.TRUE;
		} else if (bulkTO.getType().equalsIgnoreCase(
				UniversalTrackingConstants.REF_NUMBER)
				&& header.equalsIgnoreCase(AdminSpringConstants.REF_NUM)) {
			flag = Boolean.TRUE;
		}
		LOGGER.trace("BulkImportTrackingServiceImpl::isValidHeader()::END");
		return flag;
	}

	@Override
	public XSSFWorkbook reportBulkUpload(List<List> uploadList) {
		LOGGER.trace("BulkImportTrackingServiceImpl::reportBulkUpload()::START");
		XSSFWorkbook xssfWorkbook = null;
		try {
			// creates a new excel file
			xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(uploadList);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception occurs in BulkImportTrackingServiceImpl::reportBulkUpload()::" + e);
		}
		LOGGER.trace("BulkImportTrackingServiceImpl::reportBulkUpload()::END");
		return xssfWorkbook;
	}

}
