package com.ff.tracking;

import java.util.List;

import com.ff.consignment.ConsignmentTO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;

public class TrackingManifestTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ManifestBaseTO manifestBaseTO;
	private List<ConsignmentTO> consignmentTO;
	private List<ComailTO> comailTO;	
	private List<ManifestBaseTO> manifestTOs;
	private String receiveStatus;
	private String OperatingOff;
	
	public ManifestBaseTO getManifestBaseTO() {
		return manifestBaseTO;
	}
	public void setManifestBaseTO(ManifestBaseTO manifestBaseTO) {
		this.manifestBaseTO = manifestBaseTO;
	}
	public List<ConsignmentTO> getConsignmentTO() {
		return consignmentTO;
	}	
	public void setConsignmentTO(List<ConsignmentTO> consignmentTO) {
		this.consignmentTO = consignmentTO;
	}
	public List<ComailTO> getComailTO() {
		return comailTO;
	}
	public void setComailTO(List<ComailTO> comailTO) {
		this.comailTO = comailTO;
	}
	public List<ManifestBaseTO> getManifestTOs() {
		return manifestTOs;
	}
	public void setManifestTOs(List<ManifestBaseTO> manifestTOs) {
		this.manifestTOs = manifestTOs;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getOperatingOff() {
		return OperatingOff;
	}
	public void setOperatingOff(String operatingOff) {
		OperatingOff = operatingOff;
	}
}
