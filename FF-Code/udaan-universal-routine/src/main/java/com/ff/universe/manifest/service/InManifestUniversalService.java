/**
 * 
 */
package com.ff.universe.manifest.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.RegionTO;
import com.ff.global.RemarksTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;

/**
 * @author uchauhan
 * 
 */
public interface InManifestUniversalService {

	OfficeTO getOfficeDetails(Integer regionId) throws CGBusinessException,
			CGSystemException;

	List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;

	List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
			CGSystemException;

	ConsignmentTypeTO getConsgType(ConsignmentTypeTO consignmentTypeTO)
			throws CGBusinessException, CGSystemException;

	List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException;

	List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
			CGBusinessException;

	List<RemarksTO> getInManifestRemarks(String paramManifestRemarkType)
			throws CGSystemException, CGBusinessException;

	String isValidBranchCode(String branchCode) throws CGSystemException, CGBusinessException;

}
