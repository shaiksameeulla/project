package com.ff.complaints;


import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.umc.UserTO;


public class ComplaintsBacklineSummaryTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7815591481866719567L;
	private UserTO userTO;
	private String compStatus;
	//Consignment no
	private String compDesc;
	
	private List<ServiceRequestTO> serviceRequestTOs;
	
	public UserTO getUserTO() {
		return userTO;
	}
	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}	
	public String getCompStatus() {
		return compStatus;
	}
	public void setCompStatus(String compStatus) {
		this.compStatus = compStatus;
	}
	public String getCompDesc() {
		return compDesc;
	}
	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}
	public List<ServiceRequestTO> getServiceRequestTOs() {
		return serviceRequestTOs;
	}
	public void setServiceRequestTOs(List<ServiceRequestTO> serviceRequestTOs) {
		this.serviceRequestTOs = serviceRequestTOs;
	}
}
