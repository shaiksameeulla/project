/**
 * 
 */
package com.ff.web.manifest.inmanifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.organization.OfficeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InOGMDoxManifestForm.
 *
 * @author uchauhan
 */
public class InOGMDoxManifestForm extends CGBaseForm{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7188193249866101332L;

	/** The in ogm dox to. */
	InManifestOGMTO inOgmDoxTO = new InManifestOGMTO();
	
	/** The origin region to. */
	RegionTO originRegionTO = new RegionTO();
	
	/** The origin city to. */
	CityTO originCityTO = new CityTO();
	
	/** The origin office to. */
	OfficeTO originOfficeTO = new OfficeTO();
	
	/** The destination office to. */
	OfficeTO destinationOfficeTO = new OfficeTO();
	
	/**
	 * Instantiates a new in ogm dox manifest form.
	 */
	public InOGMDoxManifestForm() {
		inOgmDoxTO.setOriginRegionTO(originRegionTO);
		inOgmDoxTO.setOriginCityTO(originCityTO);
		inOgmDoxTO.setDestinationOfficeTO(destinationOfficeTO);
		inOgmDoxTO.setOriginOfficeTO(originOfficeTO);
		setTo(inOgmDoxTO);
	}
	

}
