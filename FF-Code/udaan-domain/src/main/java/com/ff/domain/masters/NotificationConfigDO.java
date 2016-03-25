package com.ff.domain.masters;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.ff.domain.serviceOffering.ProductDO;

public class NotificationConfigDO extends CGBaseDO {
	private Integer notifyConfigId;
	private ProductDO product;
	private String consgStatus;
	private String applicableToAll;
	private String recordStatus;
	private Integer customerId;
	private String notifyTO;
	private String template;
	public Integer getNotifyConfigId() {
		return notifyConfigId;
	}
	public void setNotifyConfigId(Integer notifyConfigId) {
		this.notifyConfigId = notifyConfigId;
	}
	public ProductDO getProduct() {
		return product;
	}
	public void setProduct(ProductDO product) {
		this.product = product;
	}
	public String getConsgStatus() {
		return consgStatus;
	}
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
	public String getApplicableToAll() {
		return applicableToAll;
	}
	public void setApplicableToAll(String applicableToAll) {
		this.applicableToAll = applicableToAll;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getNotifyTO() {
		return notifyTO;
	}
	public void setNotifyTO(String notifyTO) {
		this.notifyTO = notifyTO;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
