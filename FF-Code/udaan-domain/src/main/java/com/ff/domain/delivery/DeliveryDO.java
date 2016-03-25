/**
 * 
 */
package com.ff.domain.delivery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryDO.
 *
 * @author mohammes
 */
public class DeliveryDO extends CGFactDO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2174009227188841679L;

	/** The delivery id. */
	private Long deliveryId;
	
	/** The drs number. */
	private String drsNumber;
	
	/** The load number. */
	private Integer loadNumber;
	
	/** The drs for. */
	private String drsFor;
	
	/** The is yp drs. */
	private String ypDrs="N";
	
	/** The consignment type.  EX: DOX ,PPX*/
	private String consignmentType;
	
	/** The drs date. */
	private Date drsDate;
	
	/** The fs out time. */
	private Date fsOutTime;
	
	/** The fs in time. */
	private Date fsInTime;
	
	/** The drs screen code.
	 * Name of the DRS type(ex:NP Drs,CC&Q etc) at which drs created
	 */
	private String drsScreenCode;
	
	/** The drs status.
	 * DRS Status Open(DRS prepared)
	 * /updated(updated all un delivered cns)
	 * /closed (updated all delivered cns 
	 * &process compltd) */
	private String drsStatus;//
	
	/** The field staff do. */
	private EmployeeDO fieldStaffDO;
	
	/** The franchise do. */
	private LoadMovementVendorDO franchiseDO;
	
	/** The co courier do. */
	private LoadMovementVendorDO coCourierDO;
	
	/** The ba do. */
	private LoadMovementVendorDO baDO;
	
	/** The created office do. */
	private OfficeDO createdOfficeDO;
	
	
	
	/** The is drs dicarded. */
	private String isDrsDicarded= "N";
	
	/** (one-to-many relation ship in hbm). */
	 @JsonManagedReference 
	 Set<DeliveryDetailsDO> deliveryDtlsDO;
	
   
	
	/** Non-persistent Properties. */
	List<DeliveryNavigatorDO> navigatorList;
	
	private String manifestDrsType;
	
	private Date transactionCreateDate = Calendar.getInstance().getTime();
	private Date transactionModifiedDate = Calendar.getInstance().getTime();
	/**
	 * Gets the delivery id.
	 *
	 * @return the deliveryId
	 */
	public Long getDeliveryId() {
		return deliveryId;
	}

	/**
	 * Gets the drs number.
	 *
	 * @return the drsNumber
	 */
	public String getDrsNumber() {
		return drsNumber;
	}

	/**
	 * Gets the load number.
	 *
	 * @return the loadNumber
	 */
	public Integer getLoadNumber() {
		return loadNumber;
	}

	/**
	 * Gets the drs for.
	 *
	 * @return the drsFor
	 */
	public String getDrsFor() {
		return drsFor;
	}

	

	/**
	 * Gets the consignment type.
	 *
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}

	/**
	 * Gets the drs date.
	 *
	 * @return the drsDate
	 */
	public Date getDrsDate() {
		return drsDate;
	}

	/**
	 * Gets the fs out time.
	 *
	 * @return the fsOutTime
	 */
	public Date getFsOutTime() {
		return fsOutTime;
	}

	/**
	 * Gets the fs in time.
	 *
	 * @return the fsInTime
	 */
	public Date getFsInTime() {
		return fsInTime;
	}

	/**
	 * Gets the drs screen code.
	 *
	 * @return the drsScreenCode
	 */
	public String getDrsScreenCode() {
		return drsScreenCode;
	}

	/**
	 * Gets the drs status.
	 *
	 * @return the drsStatus
	 */
	public String getDrsStatus() {
		return drsStatus;
	}

	/**
	 * Gets the field staff do.
	 *
	 * @return the fieldStaffDO
	 */
	public EmployeeDO getFieldStaffDO() {
		return fieldStaffDO;
	}

	

	/**
	 * Gets the co courier do.
	 *
	 * @return the coCourierDO
	 */
	public LoadMovementVendorDO getCoCourierDO() {
		return coCourierDO;
	}

	
	/**
	 * Gets the created office do.
	 *
	 * @return the createdOfficeDO
	 */
	public OfficeDO getCreatedOfficeDO() {
		return createdOfficeDO;
	}

	/**
	 * Gets the checks if is drs dicarded.
	 *
	 * @return the isDrsDicarded
	 */
	public String getIsDrsDicarded() {
		return isDrsDicarded;
	}

	/**
	 * Sets the delivery id.
	 *
	 * @param deliveryId the deliveryId to set
	 */
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	/**
	 * Sets the drs number.
	 *
	 * @param drsNumber the drsNumber to set
	 */
	public void setDrsNumber(String drsNumber) {
		this.drsNumber = drsNumber;
		if(!StringUtil.isStringEmpty(this.drsNumber)){
			this.drsNumber=this.drsNumber.toUpperCase();
		}
	}

	/**
	 * Sets the load number.
	 *
	 * @param loadNumber the loadNumber to set
	 */
	public void setLoadNumber(Integer loadNumber) {
		this.loadNumber = loadNumber;
	}

	/**
	 * Sets the drs for.
	 *
	 * @param drsFor the drsFor to set
	 */
	public void setDrsFor(String drsFor) {
		this.drsFor = drsFor;
	}

	

	/**
	 * Sets the consignment type.
	 *
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	/**
	 * Sets the drs date.
	 *
	 * @param drsDate the drsDate to set
	 */
	public void setDrsDate(Date drsDate) {
		this.drsDate = drsDate;
	}

	/**
	 * Sets the fs out time.
	 *
	 * @param fsOutTime the fsOutTime to set
	 */
	public void setFsOutTime(Date fsOutTime) {
		this.fsOutTime = fsOutTime;
	}

	/**
	 * Sets the fs in time.
	 *
	 * @param fsInTime the fsInTime to set
	 */
	public void setFsInTime(Date fsInTime) {
		this.fsInTime = fsInTime;
	}

	/**
	 * Sets the drs screen code.
	 *
	 * @param drsScreenCode the drsScreenCode to set
	 */
	public void setDrsScreenCode(String drsScreenCode) {
		this.drsScreenCode = drsScreenCode;
	}

	/**
	 * Sets the drs status.
	 *
	 * @param drsStatus the drsStatus to set
	 */
	public void setDrsStatus(String drsStatus) {
		this.drsStatus = drsStatus;
	}

	/**
	 * Sets the field staff do.
	 *
	 * @param fieldStaffDO the fieldStaffDO to set
	 */
	public void setFieldStaffDO(EmployeeDO fieldStaffDO) {
		this.fieldStaffDO = fieldStaffDO;
	}

	

	/**
	 * Sets the co courier do.
	 *
	 * @param coCourierDO the coCourierDO to set
	 */
	public void setCoCourierDO(LoadMovementVendorDO coCourierDO) {
		this.coCourierDO = coCourierDO;
	}

	

	/**
	 * Sets the created office do.
	 *
	 * @param createdOfficeDO the createdOfficeDO to set
	 */
	public void setCreatedOfficeDO(OfficeDO createdOfficeDO) {
		this.createdOfficeDO = createdOfficeDO;
	}

	/**
	 * Sets the checks if is drs dicarded.
	 *
	 * @param isDrsDicarded the isDrsDicarded to set
	 */
	public void setIsDrsDicarded(String isDrsDicarded) {
		this.isDrsDicarded = isDrsDicarded;
	}

	/**
	 * Gets the delivery dtls do.
	 *
	 * @return the deliveryDtlsDO
	 */
	public Set<DeliveryDetailsDO> getDeliveryDtlsDO() {
		return deliveryDtlsDO;
	}

	/**
	 * @return the manifestDrsType
	 */
	public String getManifestDrsType() {
		return manifestDrsType;
	}

	/**
	 * @param manifestDrsType the manifestDrsType to set
	 */
	public void setManifestDrsType(String manifestDrsType) {
		this.manifestDrsType = manifestDrsType;
	}

	/**
	 * Sets the delivery dtls do.
	 *
	 * @param deliveryDtlsDO the deliveryDtlsDO to set
	 */
	public void setDeliveryDtlsDO(Set<DeliveryDetailsDO> deliveryDtlsDO) {
		this.deliveryDtlsDO = deliveryDtlsDO;
	}

	/**
	 * Gets the navigator list.
	 *
	 * @return the navigatorList
	 */
	public List<DeliveryNavigatorDO> getNavigatorList() {
		return navigatorList;
	}

	/**
	 * Sets the navigator list.
	 *
	 * @param navigatorList the navigatorList to set
	 */
	public void setNavigatorList(List<DeliveryNavigatorDO> navigatorList) {
		this.navigatorList = navigatorList;
	}

	/**
	 * Gets the yp drs.
	 *
	 * @return the ypDrs
	 */
	public String getYpDrs() {
		return ypDrs;
	}

	/**
	 * Sets the yp drs.
	 *
	 * @param ypDrs the ypDrs to set
	 */
	public void setYpDrs(String ypDrs) {
		this.ypDrs = ypDrs;
	}

	/**
	 * @return the franchiseDO
	 */
	public LoadMovementVendorDO getFranchiseDO() {
		return franchiseDO;
	}

	/**
	 * @param franchiseDO the franchiseDO to set
	 */
	public void setFranchiseDO(LoadMovementVendorDO franchiseDO) {
		this.franchiseDO = franchiseDO;
	}

	/**
	 * @return the baDO
	 */
	public LoadMovementVendorDO getBaDO() {
		return baDO;
	}

	/**
	 * @param baDO the baDO to set
	 */
	public void setBaDO(LoadMovementVendorDO baDO) {
		this.baDO = baDO;
	}

	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}

	/**
	 * @return the transactionModifiedDate
	 */
	public Date getTransactionModifiedDate() {
		return transactionModifiedDate;
	}

	/**
	 * @param transactionCreateDate the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

	/**
	 * @param transactionModifiedDate the transactionModifiedDate to set
	 */
	public void setTransactionModifiedDate(Date transactionModifiedDate) {
		this.transactionModifiedDate = transactionModifiedDate;
	}

	

	
	
	

}
