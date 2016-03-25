package com.ff.domain.routeserviced;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author narmdr
 *
 */
public class BcunServicedByDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	private Integer servicedById;
	private String servicedByType;
	private LoadMovementVendorDO loadMovementVendorDO;
	private EmployeeDO employeeDO;
	private ServiceByTypeDO serviceByTypeDO;
	
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
	public EmployeeDO getEmployeeDO() {
		return employeeDO;
	}
	public void setEmployeeDO(EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}
	public ServiceByTypeDO getServiceByTypeDO() {
		return serviceByTypeDO;
	}
	public void setServiceByTypeDO(ServiceByTypeDO serviceByTypeDO) {
		this.serviceByTypeDO = serviceByTypeDO;
	}
	public LoadMovementVendorDO getLoadMovementVendorDO() {
		return loadMovementVendorDO;
	}
	public void setLoadMovementVendorDO(LoadMovementVendorDO loadMovementVendorDO) {
		this.loadMovementVendorDO = loadMovementVendorDO;
	}	
}
