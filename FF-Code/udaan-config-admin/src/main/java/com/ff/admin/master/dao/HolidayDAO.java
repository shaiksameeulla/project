/**
 * 
 */
package com.ff.admin.master.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.geography.StateDO;
import com.ff.domain.masters.HolidayDO;
import com.ff.domain.masters.HolidayNameDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.master.HolidayTO;


/**
 * The Interface HolidayDAO.
 *
 * @author isawarka
 */
public interface HolidayDAO {

	List<HolidayNameDO> getHolidayNameList()
			throws CGBusinessException, CGSystemException;
	
	List<HolidayDO> getHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException;
	HolidayDO saveHoliday(HolidayDO holidayDO)
			throws CGBusinessException, CGSystemException;
	void updateHoliday(HolidayDO holidayDO)
			throws CGBusinessException, CGSystemException;
	
	void updateHoliday(Integer regionId)
			throws CGBusinessException, CGSystemException;
	void updateHoliday(Integer regionId,Integer stateId)
			throws CGBusinessException, CGSystemException;
	void updateHoliday(Integer regionId,Integer stateId,Integer cityId)
			throws CGBusinessException, CGSystemException;

	List<HolidayDO> searchHoliday(HolidayTO holidayTO) throws CGBusinessException,
			CGSystemException;

	StateDO getStateById(Integer stateId) throws CGSystemException;

	OfficeDO getOfficeByOfficeId(Integer branchId) throws CGSystemException;

	HolidayNameDO getHolidayNameByHolidayId(Integer holidayNameId) throws CGSystemException;

	Boolean editExistingHoliday(HolidayTO holidayTO) throws CGSystemException;
}
