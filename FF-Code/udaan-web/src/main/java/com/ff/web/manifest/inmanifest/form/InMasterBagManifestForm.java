package com.ff.web.manifest.inmanifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.organization.OfficeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InMasterBagManifestForm.
 *
 * @author nkattung
 */
public class InMasterBagManifestForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5221671562082437483L;
	
	/** The in master bag manifest to. */
	InMasterBagManifestTO inMasterBagManifestTO = new InMasterBagManifestTO();
	
	/** The origin region. */
	RegionTO originRegion = new RegionTO();
	
	/** The origin city to. */
	CityTO originCityTO = new CityTO();
	
	/** The origin office to. */
	OfficeTO originOfficeTO = new OfficeTO();
	
	/** The destination office to. */
	OfficeTO destinationOfficeTO = new OfficeTO();
	

	/**
	 * Instantiates a new in master bag manifest form.
	 */
	public InMasterBagManifestForm() {
		inMasterBagManifestTO.setOriginRegionTO(originRegion);
		inMasterBagManifestTO.setOriginCityTO(originCityTO);
		inMasterBagManifestTO.setDestinationOfficeTO(destinationOfficeTO);
		inMasterBagManifestTO.setOriginOfficeTO(originOfficeTO);
		setTo(inMasterBagManifestTO);
	}

	
}
