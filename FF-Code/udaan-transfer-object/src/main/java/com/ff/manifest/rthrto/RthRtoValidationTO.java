/**
 * 
 */
package com.ff.manifest.rthrto;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class RthRtoManifestTO.
 *
 * @author narmdr
 */
public class RthRtoValidationTO extends CGBaseTO {

	private static final long serialVersionUID = 858566491317972748L;

	private Integer consignmentReturnId;
	private Integer consignmentId;
	private String consignmentNumber;
	private String isEmailValidated = CommonConstants.NO; //Y-Yes, N-No
	private String contactNumber;
	private String returnType; // R-RTO, H-RTH
	private String drsDateTimeStr;
	private Double actualWeight;
	private Integer maxReasonsForRto;
	private Integer maxReasonsForRth;
	private String reasonTypeCode;//reason type master code
	private String processNumber;
	private ConsignmentTypeTO consignmentTypeTO;
	private OfficeTO OfficeTO;
	private ProcessTO processTO;
	private OfficeTO loggedInOfficeTO;
	private ConsignmentTO consignmentTO;//for RTO

	private List<RthRtoValidationDetailsTO> rthRtoValidationDetailsTOs;
	private List<RthRtoValidationDetailsTO> rthValidationDetailsTOs;
	//private List<RthRtoValidationDetailsTO> rtoValidationDetailsTOs;
	
	private String isExcessConsg = CommonConstants.NO;
	
	private int rowCount;
	private String[] dateStr = new String[rowCount];
	private String[] time = new String[rowCount];
	private String[] remarks = new String[rowCount];
	private Integer[] reasonId = new Integer[rowCount];
	private Integer[] consignmentReturnReasonId = new Integer[rowCount];


	/**
	 * Outputs
	 */
	private String errorMsg;
	private Boolean isConsignment;
	private Boolean isRth;
	private Boolean isRto;
	private Boolean isRtohManifest;
	//private ConsignmentTO consignmentTO;//for populate
	

	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	/**
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		return OfficeTO;
	}

	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		OfficeTO = officeTO;
	}

	/**
	 * @return the processTO
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * @param processTO the processTO to set
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the time
	 */
	public String[] getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String[] time) {
		this.time = time;
	}

	/**
	 * @return the reasonId
	 */
	public Integer[] getReasonId() {
		return reasonId;
	}

	/**
	 * @param reasonId the reasonId to set
	 */
	public void setReasonId(Integer[] reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return the consignmentId
	 */
	public Integer getConsignmentId() {
		return consignmentId;
	}

	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}

	/**
	 * @return the maxReasonsForRto
	 */
	public Integer getMaxReasonsForRto() {
		return maxReasonsForRto;
	}

	/**
	 * @param maxReasonsForRto the maxReasonsForRto to set
	 */
	public void setMaxReasonsForRto(Integer maxReasonsForRto) {
		this.maxReasonsForRto = maxReasonsForRto;
	}

	/**
	 * @return the maxReasonsForRth
	 */
	public Integer getMaxReasonsForRth() {
		return maxReasonsForRth;
	}

	/**
	 * @param maxReasonsForRth the maxReasonsForRth to set
	 */
	public void setMaxReasonsForRth(Integer maxReasonsForRth) {
		this.maxReasonsForRth = maxReasonsForRth;
	}

	/**
	 * @return the reasonTypeCode
	 */
	public String getReasonTypeCode() {
		return reasonTypeCode;
	}

	/**
	 * @param reasonTypeCode the reasonTypeCode to set
	 */
	public void setReasonTypeCode(String reasonTypeCode) {
		this.reasonTypeCode = reasonTypeCode;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the consignmentReturnId
	 */
	public Integer getConsignmentReturnId() {
		return consignmentReturnId;
	}

	/**
	 * @param consignmentReturnId the consignmentReturnId to set
	 */
	public void setConsignmentReturnId(Integer consignmentReturnId) {
		this.consignmentReturnId = consignmentReturnId;
	}

	/**
	 * @return the isEmailValidated
	 */
	public String getIsEmailValidated() {
		return isEmailValidated;
	}

	/**
	 * @param isEmailValidated the isEmailValidated to set
	 */
	public void setIsEmailValidated(String isEmailValidated) {
		this.isEmailValidated = isEmailValidated;
	}

	/**
	 * @return the consignmentReturnReasonId
	 */
	public Integer[] getConsignmentReturnReasonId() {
		return consignmentReturnReasonId;
	}

	/**
	 * @param consignmentReturnReasonId the consignmentReturnReasonId to set
	 */
	public void setConsignmentReturnReasonId(Integer[] consignmentReturnReasonId) {
		this.consignmentReturnReasonId = consignmentReturnReasonId;
	}

	/**
	 * @return the rthValidationDetailsTOs
	 */
	public List<RthRtoValidationDetailsTO> getRthValidationDetailsTOs() {
		return rthValidationDetailsTOs;
	}

	/**
	 * @param rthValidationDetailsTOs the rthValidationDetailsTOs to set
	 */
	public void setRthValidationDetailsTOs(List<RthRtoValidationDetailsTO> rthValidationDetailsTOs) {
		this.rthValidationDetailsTOs = rthValidationDetailsTOs;
	}

	/**
	 * @return the dateStr
	 */
	public String[] getDateStr() {
		return dateStr;
	}

	/**
	 * @param dateStr the dateStr to set
	 */
	public void setDateStr(String[] dateStr) {
		this.dateStr = dateStr;
	}

	/**
	 * @return the loggedInOfficeTO
	 */
	public OfficeTO getLoggedInOfficeTO() {
		return loggedInOfficeTO;
	}

	/**
	 * @param loggedInOfficeTO the loggedInOfficeTO to set
	 */
	public void setLoggedInOfficeTO(OfficeTO loggedInOfficeTO) {
		this.loggedInOfficeTO = loggedInOfficeTO;
	}

	/**
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}

	/**
	 * @param processNumber the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the isConsignment
	 */
	public Boolean getIsConsignment() {
		return isConsignment;
	}

	/**
	 * @param isConsignment the isConsignment to set
	 */
	public void setIsConsignment(Boolean isConsignment) {
		this.isConsignment = isConsignment;
	}

	/**
	 * @return the isRth
	 */
	public Boolean getIsRth() {
		return isRth;
	}

	/**
	 * @param isRth the isRth to set
	 */
	public void setIsRth(Boolean isRth) {
		this.isRth = isRth;
	}

	/**
	 * @return the isRto
	 */
	public Boolean getIsRto() {
		return isRto;
	}

	/**
	 * @param isRto the isRto to set
	 */
	public void setIsRto(Boolean isRto) {
		this.isRto = isRto;
	}

	/**
	 * @return the rthRtoValidationDetailsTOs
	 */
	public List<RthRtoValidationDetailsTO> getRthRtoValidationDetailsTOs() {
		return rthRtoValidationDetailsTOs;
	}

	/**
	 * @param rthRtoValidationDetailsTOs the rthRtoValidationDetailsTOs to set
	 */
	public void setRthRtoValidationDetailsTOs(
			List<RthRtoValidationDetailsTO> rthRtoValidationDetailsTOs) {
		this.rthRtoValidationDetailsTOs = rthRtoValidationDetailsTOs;
	}

	/**
	 * @return the drsDateTimeStr
	 */
	public String getDrsDateTimeStr() {
		return drsDateTimeStr;
	}

	/**
	 * @param drsDateTimeStr the drsDateTimeStr to set
	 */
	public void setDrsDateTimeStr(String drsDateTimeStr) {
		this.drsDateTimeStr = drsDateTimeStr;
	}

	/**
	 * @return the consignmentTO
	 */
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}

	/**
	 * @param consignmentTO the consignmentTO to set
	 */
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}

	/**
	 * @return the isRtohManifest
	 */
	public Boolean getIsRtohManifest() {
		return isRtohManifest;
	}

	/**
	 * @param isRtohManifest the isRtohManifest to set
	 */
	public void setIsRtohManifest(Boolean isRtohManifest) {
		this.isRtohManifest = isRtohManifest;
	}

	/**
	 * @return the isExcessConsg
	 */
	public String getIsExcessConsg() {
		return isExcessConsg;
	}

	/**
	 * @param isExcessConsg the isExcessConsg to set
	 */
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}
	
}
