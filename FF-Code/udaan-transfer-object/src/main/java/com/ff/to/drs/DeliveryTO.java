/**
 * 
 */
package com.ff.to.drs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;

/**
 * @author mohammes
 * 
 */
public class DeliveryTO extends CGBaseTO {

	/**
	 * 
	 */
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
	private String ypDrs;

	/** The consignment type. EX: DOX ,PPX */
	private String consignmentType;

	/** The drs date. */
	private Date drsDate;

	/** The fs out time. */
	private Date fsOutTime;

	/** The fs in time. */
	private Date fsInTime;

	/**
	 * The drs screen code. Name of the DRS type(ex:NP Drs,CC&Q etc) at which
	 * drs created
	 */
	private String drsScreenCode;

	/**
	 * The drs status. DRS Status Open(DRS prepared) /updated(updated all un
	 * delivered cns) /closed (updated all delivered cns &process compltd)
	 */
	private String drsStatus;//

	/** The field staff do. */
	private EmployeeTO fieldStaffTO;

	/** The franchise do. */
	private LoadMovementVendorTO franchiseTO;

	/** The co courier do. */
	private LoadMovementVendorTO coCourierTO;

	/** The ba do. */
	private LoadMovementVendorTO baTO;

	/** The created office do. */
	private OfficeTO createdOfficeTO;

	/** The is drs dicarded. */
	private String isDrsDicarded = "N";

	/** The created by. */
	private Integer createdBy;

	/** The updated by. */
	private Integer updatedBy;
	/** The can update. */
	public String canUpdate;

	/** The party type map. For Employee/DA code in the Screen */
	private Map<Integer, String> partyTypeMap;

	private List<DeliveryDetailsTO> dtlsTOList;
	
	private DeliveryUserTO dlvUserTO;//for Print functionality
	
	private Integer maxAllowedPrintRows;
	private String manifestDrsType;
	private String fsInAlias;
	private String fsOutAlias;
	/**
	 * @return the deliveryId
	 */

	// Added By Narasimha
	private ConsignmentTO consignmentTO;

	public Long getDeliveryId() {
		return deliveryId;
	}

	/**
	 * @return the drsNumber
	 */
	public String getDrsNumber() {
		return drsNumber;
	}

	/**
	 * @return the loadNumber
	 */
	public Integer getLoadNumber() {
		return loadNumber;
	}

	/**
	 * @return the drsFor
	 */
	public String getDrsFor() {
		return drsFor;
	}

	/**
	 * @return the consignmentType
	 */
	public String getConsignmentType() {
		return consignmentType;
	}

	/**
	 * @return the drsDate
	 */
	public Date getDrsDate() {
		return drsDate;
	}

	/**
	 * @return the fsOutTime
	 */
	public Date getFsOutTime() {
		return fsOutTime;
	}

	/**
	 * @return the fsInTime
	 */
	public Date getFsInTime() {
		return fsInTime;
	}

	/**
	 * @return the drsScreenCode
	 */
	public String getDrsScreenCode() {
		return drsScreenCode;
	}

	/**
	 * @return the drsStatus
	 */
	public String getDrsStatus() {
		return drsStatus;
	}

	/**
	 * @return the fieldStaffTO
	 */
	public EmployeeTO getFieldStaffTO() {
		return fieldStaffTO;
	}

	

	/**
	 * @return the coCourierTO
	 */
	public LoadMovementVendorTO getCoCourierTO() {
		return coCourierTO;
	}

	

	/**
	 * @return the createdOfficeTO
	 */
	public OfficeTO getCreatedOfficeTO() {
		return createdOfficeTO;
	}

	/**
	 * @return the isDrsDicarded
	 */
	public String getIsDrsDicarded() {
		return isDrsDicarded;
	}

	/**
	 * @param deliveryId
	 *            the deliveryId to set
	 */
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	/**
	 * @param drsNumber
	 *            the drsNumber to set
	 */
	public void setDrsNumber(String drsNumber) {
		this.drsNumber = drsNumber;
	}

	/**
	 * @param loadNumber
	 *            the loadNumber to set
	 */
	public void setLoadNumber(Integer loadNumber) {
		this.loadNumber = loadNumber;
	}

	/**
	 * @param drsFor
	 *            the drsFor to set
	 */
	public void setDrsFor(String drsFor) {
		this.drsFor = drsFor;
	}

	/**
	 * @param consignmentType
	 *            the consignmentType to set
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	/**
	 * @param drsDate
	 *            the drsDate to set
	 */
	public void setDrsDate(Date drsDate) {
		this.drsDate = drsDate;
	}

	/**
	 * @param fsOutTime
	 *            the fsOutTime to set
	 */
	public void setFsOutTime(Date fsOutTime) {
		this.fsOutTime = fsOutTime;
	}

	/**
	 * @param fsInTime
	 *            the fsInTime to set
	 */
	public void setFsInTime(Date fsInTime) {
		this.fsInTime = fsInTime;
	}

	/**
	 * @param drsScreenCode
	 *            the drsScreenCode to set
	 */
	public void setDrsScreenCode(String drsScreenCode) {
		this.drsScreenCode = drsScreenCode;
	}

	/**
	 * @param drsStatus
	 *            the drsStatus to set
	 */
	public void setDrsStatus(String drsStatus) {
		this.drsStatus = drsStatus;
	}

	/**
	 * @param fieldStaffTO
	 *            the fieldStaffTO to set
	 */
	public void setFieldStaffTO(EmployeeTO fieldStaffTO) {
		this.fieldStaffTO = fieldStaffTO;
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
	 * @param coCourierTO
	 *            the coCourierTO to set
	 */
	public void setCoCourierTO(LoadMovementVendorTO coCourierTO) {
		this.coCourierTO = coCourierTO;
	}

	

	/**
	 * @param createdOfficeTO
	 *            the createdOfficeTO to set
	 */
	public void setCreatedOfficeTO(OfficeTO createdOfficeTO) {
		this.createdOfficeTO = createdOfficeTO;
	}

	/**
	 * @param isDrsDicarded
	 *            the isDrsDicarded to set
	 */
	public void setIsDrsDicarded(String isDrsDicarded) {
		this.isDrsDicarded = isDrsDicarded;
	}

	/**
	 * @return the dtlsTOList
	 */
	public List<DeliveryDetailsTO> getDtlsTOList() {
		return dtlsTOList;
	}

	/**
	 * @param dtlsTOList
	 *            the dtlsTOList to set
	 */
	public void setDtlsTOList(List<DeliveryDetailsTO> dtlsTOList) {
		this.dtlsTOList = dtlsTOList;
	}

	/**
	 * @return the canUpdate
	 */
	public String getCanUpdate() {
		return canUpdate;
	}

	/**
	 * @param canUpdate
	 *            the canUpdate to set
	 */
	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}

	/**
	 * @return the ypDrs
	 */
	public String getYpDrs() {
		return ypDrs;
	}

	/**
	 * @param ypDrs
	 *            the ypDrs to set
	 */
	public void setYpDrs(String ypDrs) {
		this.ypDrs = ypDrs;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the partyTypeMap
	 */
	public Map<Integer, String> getPartyTypeMap() {
		return partyTypeMap;
	}

	/**
	 * @param partyTypeMap
	 *            the partyTypeMap to set
	 */
	public void setPartyTypeMap(Map<Integer, String> partyTypeMap) {
		this.partyTypeMap = partyTypeMap;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the consignmentTO
	 */
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}

	/**
	 * @param consignmentTO
	 *            the consignmentTO to set
	 */
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}

	/**
	 * @return the dlvUserTO
	 */
	public DeliveryUserTO getDlvUserTO() {
		return dlvUserTO;
	}

	/**
	 * @param dlvUserTO the dlvUserTO to set
	 */
	public void setDlvUserTO(DeliveryUserTO dlvUserTO) {
		this.dlvUserTO = dlvUserTO;
	}

	/**
	 * @return the maxAllowedPrintRows
	 */
	public Integer getMaxAllowedPrintRows() {
		return maxAllowedPrintRows;
	}

	/**
	 * @param maxAllowedPrintRows the maxAllowedPrintRows to set
	 */
	public void setMaxAllowedPrintRows(Integer maxAllowedPrintRows) {
		this.maxAllowedPrintRows = maxAllowedPrintRows;
	}

	/**
	 * @return the franchiseTO
	 */
	public LoadMovementVendorTO getFranchiseTO() {
		return franchiseTO;
	}

	/**
	 * @param franchiseTO the franchiseTO to set
	 */
	public void setFranchiseTO(LoadMovementVendorTO franchiseTO) {
		this.franchiseTO = franchiseTO;
	}

	/**
	 * @return the baTO
	 */
	public LoadMovementVendorTO getBaTO() {
		return baTO;
	}

	/**
	 * @param baTO the baTO to set
	 */
	public void setBaTO(LoadMovementVendorTO baTO) {
		this.baTO = baTO;
	}

	public String getFsInAlias() {
		return fsInAlias;
	}

	public void setFsInAlias(String fsInAlias) {
		this.fsInAlias = fsInAlias;
	}

	public String getFsOutAlias() {
		return fsOutAlias;
	}

	public void setFsOutAlias(String fsOutAlias) {
		this.fsOutAlias = fsOutAlias;
	}
    
	
	
}
