/**
 * 
 */
package com.ff.admin.tracking.bulkImportTracking.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.tracking.TrackingBulkImportTO;

/**
 * @author uchauhan
 *
 */
public interface BulkImportTrackingService {

    /**
     * May 13, 2013
     * @param fileUrl
     * @param bulkTO
     * @param myFile
     * @return
     * getBulkConsgDetails
     * BulkImportTrackingService
     * TrackingBulkImportTO
     * uchauhan
     */
	public List<TrackingBulkImportTO>  getBulkConsgDetails(String fileUrl,FormFile myFile ,TrackingBulkImportTO bulkTO) throws CGSystemException,CGBusinessException;
	public XSSFWorkbook reportBulkUpload(List<List> uploadList) throws CGBusinessException, CGSystemException;
    

}
