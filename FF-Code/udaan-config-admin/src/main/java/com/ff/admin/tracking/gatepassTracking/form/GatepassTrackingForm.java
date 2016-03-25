package com.ff.admin.tracking.gatepassTracking.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.tracking.TrackingGatepassTO;

public class GatepassTrackingForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrackingGatepassTO trackingGatepassTO=new TrackingGatepassTO();
	private LoadMovementTO loadMovementTO=new LoadMovementTO();
	public GatepassTrackingForm() {
		trackingGatepassTO.setLoadMovementTO(loadMovementTO);
		setTo(trackingGatepassTO);
		
	}

}
