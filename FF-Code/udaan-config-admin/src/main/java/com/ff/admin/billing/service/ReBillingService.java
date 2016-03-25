/**
 * 
 */
package com.ff.admin.billing.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.ReBillGDRAliasTO;
import com.ff.to.billing.ReBillingTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.ratemanagement.masters.RateContractTO;

/**
 * @author abarudwa
 *
 */
public interface ReBillingService {
	
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
	
	public List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, String startDateStr, String endDateStr)
			throws CGBusinessException, CGSystemException;
	
	public List<RateContractTO> getRateContractsByCustomerIds(List<Integer> customerIds) 
			throws CGBusinessException,CGSystemException;
	
	public List<RateCalculationOutputTO> prepareRateInputs(List<ConsignmentTO>  consignmentList)
			throws CGBusinessException,CGSystemException;
	
	public ReBillingTO saveOrUpdateReBilling(ReBillingTO rebillingTO)throws CGBusinessException,
	CGSystemException;
	
	 public void getRebillingDetails()throws CGBusinessException, CGSystemException, HttpException, ClassNotFoundException, IOException;

	void checkOldContractAvailability(Integer bookingOfficeId,
			Integer customerId, Date endDate) throws CGBusinessException, CGSystemException;
}
