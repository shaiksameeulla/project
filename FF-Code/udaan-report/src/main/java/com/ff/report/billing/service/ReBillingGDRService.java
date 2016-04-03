/**
 * 
 */
package com.ff.report.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.ReBillingGDRTO;

/**
 * @author abarudwa
 *
 */
public interface ReBillingGDRService {
List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;
	
	// getStations
	List<CityTO> getCitiesByRegionId(final Integer regionId)
				throws CGBusinessException, CGSystemException;
	
	// getBranches
	List<OfficeTO> getOfficesByCityId(final Integer cityId)
				throws CGBusinessException, CGSystemException;
		
	// getCustomers
	List<CustomerTO> getCustomersByOfficeId(final Integer officeId)
				throws CGBusinessException, CGSystemException;
	
	public ReBillingGDRTO getRebillDetails(ReBillingGDRTO rebillingGDRTO)throws CGBusinessException, CGSystemException;
}
