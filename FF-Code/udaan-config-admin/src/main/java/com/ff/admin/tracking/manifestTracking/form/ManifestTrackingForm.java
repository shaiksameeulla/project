package com.ff.admin.tracking.manifestTracking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ManifestTrackingTO;
import com.ff.tracking.TrackingManifestTO;

public class ManifestTrackingForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ManifestTrackingTO manifestTrackingTO = new ManifestTrackingTO();
	private TrackingManifestTO outManifestTO = new TrackingManifestTO();
	private TrackingManifestTO inManifestTO = new TrackingManifestTO();
	private OfficeTO officeTO = new OfficeTO();

	public ManifestTrackingForm() {
		manifestTrackingTO.setOutManifestTO(outManifestTO);
		manifestTrackingTO.setInManifestTO(inManifestTO);
		manifestTrackingTO.setOfficeTO(officeTO);
		setTo(manifestTrackingTO);

	}

}
