/**
 * 
 */
package com.ff.admin.master.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.masters.HolidayDO;
import com.ff.master.HolidayNameTO;
import com.ff.master.HolidayTO;


/**
 * @author isawarka
 * 
 */
public interface HolidayService {
	
	List<HolidayNameTO> getHolidayNameList()
			throws CGBusinessException, CGSystemException;
	List<HolidayDO> getHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException;
	
	HolidayDO saveHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException;
	
	void updateHoliday(HolidayDO holidayDO)
			throws CGBusinessException, CGSystemException;
	
	void updateHoliday(Integer regionId)
			throws CGBusinessException, CGSystemException;
	void updateHoliday(Integer regionId,Integer stateId)
			throws CGBusinessException, CGSystemException;
	void updateHoliday(Integer regionId,Integer stateId,Integer cityId)
			throws CGBusinessException, CGSystemException;
	List<HolidayTO> searchHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException;
	Boolean editExistingHoliday(HolidayTO holidayTO) throws CGSystemException;
}
