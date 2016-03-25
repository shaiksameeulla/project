package com.ff.admin.billing.form;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.billing.CustModificationTO;

public class CustModificationForm extends CGBaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustModificationForm() {
		RegionTO bkgRegionTO = new RegionTO();
		CustomerTO newCustTO = new CustomerTO();
		CustomerTO bkgCustTO = new CustomerTO();
		ConsignmentTypeTO cnTypeTO = new ConsignmentTypeTO();
		List<CityTO> cityList = new ArrayList<CityTO>();
		
		CustModificationTO custModificationTO = new CustModificationTO();
		custModificationTO.setBkgRegionTO(bkgRegionTO);
		custModificationTO.setNewCustTO(newCustTO);
		custModificationTO.setCityList(cityList);
		custModificationTO.setBkgCustTO(bkgCustTO);
		custModificationTO.setCnTypeTO(cnTypeTO);
		
		setTo(custModificationTO);
	}

}
