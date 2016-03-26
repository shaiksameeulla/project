package com.ff.web.manifest.rthrto.form;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestForm.
 */
public class RthRtoManifestForm extends CGBaseForm {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5790019902731971539L;

	/**
	 * Instantiates a new rth rto manifest form.
	 */
	public RthRtoManifestForm() {
		ConsignmentTypeTO consignmentTypeTO=new ConsignmentTypeTO();
		RegionTO regionTO=new RegionTO();
		CityTO cityTO=new CityTO();
		OfficeTO originOfficeTO=new OfficeTO();
		OfficeTypeTO originOfficeTypeTO=new OfficeTypeTO();
		originOfficeTO.setOfficeTypeTO(originOfficeTypeTO);
		OfficeTO destOfficeTO=new OfficeTO();
		ProcessTO processTO=new ProcessTO();
		ProcessTO updateProcessTO=new ProcessTO();
		List<RthRtoDetailsTO> rthRtoDetailsTOs=new ArrayList<>();
		
		RthRtoManifestTO rthRtoManifestTO=new RthRtoManifestTO();
		rthRtoManifestTO.setConsignmentTypeTO(consignmentTypeTO);
		rthRtoManifestTO.setDestRegionTO(regionTO);
		rthRtoManifestTO.setDestCityTO(cityTO);		
		rthRtoManifestTO.setOriginOfficeTO(originOfficeTO);
		rthRtoManifestTO.setDestOfficeTO(destOfficeTO);
		rthRtoManifestTO.setProcessTO(processTO);
		rthRtoManifestTO.setUpdateProcessTO(updateProcessTO);
		rthRtoManifestTO.setRthRtoDetailsTOs(rthRtoDetailsTOs);
		setTo(rthRtoManifestTO);
	}
}
