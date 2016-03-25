package com.ff.routeserviced;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.organization.EmployeeTO;

/**
 * @author narmdr
 *
 */
public class ServicedByTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer servicedById;
	private String servicedByType;
	private LoadMovementVendorTO loadMovementVendorTO;
	private EmployeeTO employeeTO;
	private ServiceByTypeTO serviceByTypeTO;
	
	public Integer getServicedById() {
		return servicedById;
	}
	public void setServicedById(Integer servicedById) {
		this.servicedById = servicedById;
	}
	public String getServicedByType() {
		return servicedByType;
	}
	public void setServicedByType(String servicedByType) {
		this.servicedByType = servicedByType;
	}
	public LoadMovementVendorTO getLoadMovementVendorTO() {
		return loadMovementVendorTO;
	}
	public void setLoadMovementVendorTO(LoadMovementVendorTO loadMovementVendorTO) {
		this.loadMovementVendorTO = loadMovementVendorTO;
	}
	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}
	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}
	public ServiceByTypeTO getServiceByTypeTO() {
		return serviceByTypeTO;
	}
	public void setServiceByTypeTO(ServiceByTypeTO serviceByTypeTO) {
		this.serviceByTypeTO = serviceByTypeTO;
	}
	
}
