package com.ff.tracking;

import java.util.List;

import com.ff.consignment.ConsignmentTO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;

public class ManifestTrackingTO extends TrackingBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Out Manifest
	private TrackingManifestTO outManifestTO;
	//In Manifest
	private TrackingManifestTO inManifestTO;
	private OfficeTO officeTO;
	
	public TrackingManifestTO getOutManifestTO() {
		return outManifestTO;
	}
	public void setOutManifestTO(TrackingManifestTO outManifestTO) {
		this.outManifestTO = outManifestTO;
	}
	public TrackingManifestTO getInManifestTO() {
		return inManifestTO;
	}
	public void setInManifestTO(TrackingManifestTO inManifestTO) {
		this.inManifestTO = inManifestTO;
	}
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
}
