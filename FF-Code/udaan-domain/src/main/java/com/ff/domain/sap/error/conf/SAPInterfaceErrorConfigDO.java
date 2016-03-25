package com.ff.domain.sap.error.conf;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.DateUtil;

public class SAPInterfaceErrorConfigDO extends CGBaseDO {

	private static final long serialVersionUID = -8308775327771455576L;
	
	private Long Id;
	private String interfaceName;
	private String interfaceType;
	private String tableName;	
	private String statusColumnName;
	private Date createdDate = DateUtil.getCurrentDate();
	private Date updatedDate = DateUtil.getCurrentDate();
	
	private String successStatus;	
	private String failureStatus;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getStatusColumnName() {
		return statusColumnName;
	}
	public void setStatusColumnName(String statusColumnName) {
		this.statusColumnName = statusColumnName;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public String getSuccessStatus() {
		return successStatus;
	}
	public void setSuccessStatus(String successStatus) {
		this.successStatus = successStatus;
	}
	
	public String getFailureStatus() {
		return failureStatus;
	}
	public void setFailureStatus(String failureStatus) {
		this.failureStatus = failureStatus;
	}	

}
