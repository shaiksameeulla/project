/**
 * 
 */
package com.ff.admin.complaints.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.ServiceRequestFollowupTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;

/**
 * @author prmeher
 *
 */
public interface ServiceRequestFollowupService {

	/**
	 * @param serviceRequestFollowupTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Boolean saveOrUpdateFollowup(
			ServiceRequestFollowupTO serviceRequestFollowupTO) throws CGBusinessException, CGSystemException;

	/**
	 * @param complaintId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ServiceRequestFollowupTO> getComplaintFollowupDetails(
			Integer complaintId)throws CGBusinessException, CGSystemException;

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RegionTO> getAllRegions()throws CGBusinessException, CGSystemException;

	/**
	 * @param regionId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CityTO> getCitiesByRegion(Integer regionId)throws CGBusinessException, CGSystemException;

	/**
	 * @param cityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<OfficeTO> getAllOfficesByCity(Integer cityId)throws CGBusinessException, CGSystemException;

	/**
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<EmployeeTO> getAllEmployeeByOfficeAndRole(String designationType, Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * @param complaintId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ServiceRequestTO getComplaintDtlsByComplaintId(Integer complaintId)
			throws CGBusinessException, CGSystemException;
}
