package com.ff.web.manifest.rthrto.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.rthrto.RthRtoManifestDoxTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestDoxForm.
 *
 * @author hkansagr
 */

public class RthRtoManifestDoxForm extends CGBaseForm{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new rth rto manifest dox form.
	 */
	public RthRtoManifestDoxForm(){
		RthRtoManifestDoxTO rthRtoManifestDoxTO = new RthRtoManifestDoxTO();
		rthRtoManifestDoxTO.setConsignmentTypeTO(new ConsignmentTypeTO());
		rthRtoManifestDoxTO.setOriginOfficeTO(new OfficeTO());
		rthRtoManifestDoxTO.setDestOfficeTO(new OfficeTO());
		rthRtoManifestDoxTO.setDestRegionTO(new RegionTO());
		rthRtoManifestDoxTO.setDestCityTO(new CityTO());
		setTo(rthRtoManifestDoxTO);
	}

}
