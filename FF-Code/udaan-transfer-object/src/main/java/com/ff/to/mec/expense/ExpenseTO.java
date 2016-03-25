package com.ff.to.mec.expense;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class ExpenseTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Long expenseId;/* hidden */

	/** The tx No. */
	private String txNumber;

	/** The posting date (current date) i.e. DD/MM/YYYY HH:MM */
	private String postingDate;

	/** The Document date i.e. DD/MM/YYYY */
	private String documentDate;

	/** The expense for i.e O=Office E=Emp. C=Consig. */
	private String expenseFor;

	/** The expense for List */
	private List<LabelValueBean> expenseForList;

	/** The expense Type Id */
	private Integer expenseType;

	/** The expense Type List */
	private List<LabelValueBean> expenseTypeList;

	/** The expense mode */
	private Integer expenseMode;

	/** The expense mode List */
	private List<LabelValueBean> expenseModeList;

	/** The cheque number */
	private String chequeNumber;

	/** The cheque date */
	private String chequeDate;

	/** The bank Id */
	private Integer bank;

	/** The bank list */
	private List<LabelValueBean> bankList;

	/** The branch name of bank */
	private String bankBranchName;

	/** The final amount in header */
	private String finalAmount;

	/** The expense status i.e. OPENED, SUBMITTED, VALIDATE (default O=OPEN) */
	private String expenseStatus = "O";/* hidden */

	/** The transaction remarks for expense type office. */
	private String txRemarks;

	/** The loggedIn Office id */
	private Integer loginOfficeId;/* hidden */

	/** The loggedIn Office Code */
	private String loginOfficeCode;/* hidden */

	/** The expense Office RHO */
	private Integer expenseOfficeRho;/* hidden */

	/** The loggedIn Office region id */
	private Integer regionId;/* hidden */

	/** The expense created by user id */
	private Integer createdBy;/* hidden */

	/** The expense updated by user id */
	private Integer updatedBy;/* hidden */

	/** The Grid details for Employee expense */
	private List<EmployeeExpenseDetailTO> empDtlsTO;

	/** The Grid details for Consignment expense */
	private List<ConsignmentExpenseDetailTO> consgDtlsTO;

	/* Common details */

	/** The expense entries id */
	protected Long expenseEntriesId;

	/** The position for grid */
	protected Integer position;

	/** The amount */
	private Double amount;

	/** The remark */
	private String remark;

	/** The Octroi charge. */
	private Double octroiCharge;

	/* Common grid details */
	public int rowCount;

	/** The remarks in grid */
	private String[] remarks = new String[rowCount];

	/** The amounts in grid */
	private Double[] amounts = new Double[rowCount];

	/** The rowNumbers in grid */
	private Integer[] positions = new Integer[rowCount];

	/** The expense entries ids */
	private Long[] expenseEntriesIds = new Long[rowCount];

	/* Employee grid details */

	/** The employee Ids. */
	private Integer[] employeeIds = new Integer[rowCount];

	/* Consignment grid details */

	/** The consignment ids. */
	private Integer[] consgIds = new Integer[rowCount];

	/** The consignment numbers. */
	private String[] consgNos = new String[rowCount];

	/** The service charges. */
	private Double[] serviceCharges = new Double[rowCount];

	/** The total Tax. i.e. totalTax = serviceTax + eduCess + higherEduCess */
	private Double[] totalTaxs = new Double[rowCount];

	/** The service Tax. */
	private Double[] serviceTaxs = new Double[rowCount];

	/** The education Cess. */
	private Double[] eduCesss = new Double[rowCount];

	/** The higher education Cess. */
	private Double[] higherEduCesss = new Double[rowCount];

	/** The other charges. */
	private Double[] otherCharges = new Double[rowCount];

	/** The totals. */
	private Double[] totals = new Double[rowCount];

	/** The octroi charges. */
	private Double[] octroiCharges = new Double[rowCount];

	/** The octroiBourneBys. */
	private String[] octroiBourneBys = new String[rowCount]; /* hidden */

	/** The stateTaxs. */
	private Double[] stateTaxs = new Double[rowCount];

	/** The surchargeOnStateTaxs. */
	private Double[] surchargeOnStateTaxs = new Double[rowCount];

	/** The billing flags. */
	private String[] billingFlags = new String[rowCount];

	/** The employee list for search. */
	private List<LabelValueBean> empList;

	/** To check whether is validate screen or not */
	private String isValidateScreen;

	/** To check whether is credit note or not */
	private String isCrNote;

	/** The old expenseId to update old expense status. */
	private Long oldExpId;

	/** The GL payment Type. */
	private Integer glPaymentType;

	/** The isNegativeGLNature. */
	private String isNegativeGLNature;/* Y or N */

	/** The isEmpGL. */
	private String isEmpGL;/* Y or N */

	/** The isOctroiGL. */
	private String isOctroiGL;/* Y or N */

	
	
	/**
	 * @return the txRemarks
	 */
	public String getTxRemarks() {
		return txRemarks;
	}

	/**
	 * @param txRemarks
	 *            the txRemarks to set
	 */
	public void setTxRemarks(String txRemarks) {
		this.txRemarks = txRemarks;
	}

	/**
	 * @return the billingFlags
	 */
	public String[] getBillingFlags() {
		return billingFlags;
	}

	/**
	 * @param billingFlags
	 *            the billingFlags to set
	 */
	public void setBillingFlags(String[] billingFlags) {
		this.billingFlags = billingFlags;
	}

	/**
	 * @return the octroiBourneBys
	 */
	public String[] getOctroiBourneBys() {
		return octroiBourneBys;
	}

	/**
	 * @param octroiBourneBys
	 *            the octroiBourneBys to set
	 */
	public void setOctroiBourneBys(String[] octroiBourneBys) {
		this.octroiBourneBys = octroiBourneBys;
	}

	/**
	 * @return the stateTaxs
	 */
	public Double[] getStateTaxs() {
		return stateTaxs;
	}

	/**
	 * @param stateTaxs
	 *            the stateTaxs to set
	 */
	public void setStateTaxs(Double[] stateTaxs) {
		this.stateTaxs = stateTaxs;
	}

	/**
	 * @return the surchargeOnStateTaxs
	 */
	public Double[] getSurchargeOnStateTaxs() {
		return surchargeOnStateTaxs;
	}

	/**
	 * @param surchargeOnStateTaxs
	 *            the surchargeOnStateTaxs to set
	 */
	public void setSurchargeOnStateTaxs(Double[] surchargeOnStateTaxs) {
		this.surchargeOnStateTaxs = surchargeOnStateTaxs;
	}

	/**
	 * @return the isEmpGL
	 */
	public String getIsEmpGL() {
		return isEmpGL;
	}

	/**
	 * @param isEmpGL
	 *            the isEmpGL to set
	 */
	public void setIsEmpGL(String isEmpGL) {
		this.isEmpGL = isEmpGL;
	}

	/**
	 * @return the isOctroiGL
	 */
	public String getIsOctroiGL() {
		return isOctroiGL;
	}

	/**
	 * @param isOctroiGL
	 *            the isOctroiGL to set
	 */
	public void setIsOctroiGL(String isOctroiGL) {
		this.isOctroiGL = isOctroiGL;
	}

	/**
	 * @return the isNegativeGLNature
	 */
	public String getIsNegativeGLNature() {
		return isNegativeGLNature;
	}

	/**
	 * @param isNegativeGLNature
	 *            the isNegativeGLNature to set
	 */
	public void setIsNegativeGLNature(String isNegativeGLNature) {
		this.isNegativeGLNature = isNegativeGLNature;
	}

	/**
	 * @return the glPaymentType
	 */
	public Integer getGlPaymentType() {
		return glPaymentType;
	}

	/**
	 * @param glPaymentType
	 *            the glPaymentType to set
	 */
	public void setGlPaymentType(Integer glPaymentType) {
		this.glPaymentType = glPaymentType;
	}

	/**
	 * @return the finalAmount
	 */
	public String getFinalAmount() {
		return finalAmount;
	}

	/**
	 * @param finalAmount
	 *            the finalAmount to set
	 */
	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}

	/**
	 * @return the eduCesss
	 */
	public Double[] getEduCesss() {
		return eduCesss;
	}

	/**
	 * @param eduCesss
	 *            the eduCesss to set
	 */
	public void setEduCesss(Double[] eduCesss) {
		this.eduCesss = eduCesss;
	}

	/**
	 * @return the higherEduCesss
	 */
	public Double[] getHigherEduCesss() {
		return higherEduCesss;
	}

	/**
	 * @param higherEduCesss
	 *            the higherEduCesss to set
	 */
	public void setHigherEduCesss(Double[] higherEduCesss) {
		this.higherEduCesss = higherEduCesss;
	}

	/**
	 * @return the totalTaxs
	 */
	public Double[] getTotalTaxs() {
		return totalTaxs;
	}

	/**
	 * @param totalTaxs
	 *            the totalTaxs to set
	 */
	public void setTotalTaxs(Double[] totalTaxs) {
		this.totalTaxs = totalTaxs;
	}

	/**
	 * @return the serviceTaxs
	 */
	public Double[] getServiceTaxs() {
		return serviceTaxs;
	}

	/**
	 * @param serviceTaxs
	 *            the serviceTaxs to set
	 */
	public void setServiceTaxs(Double[] serviceTaxs) {
		this.serviceTaxs = serviceTaxs;
	}

	/**
	 * @return the expenseOfficeRho
	 */
	public Integer getExpenseOfficeRho() {
		return expenseOfficeRho;
	}

	/**
	 * @param expenseOfficeRho
	 *            the expenseOfficeRho to set
	 */
	public void setExpenseOfficeRho(Integer expenseOfficeRho) {
		this.expenseOfficeRho = expenseOfficeRho;
	}

	/**
	 * @return the oldExpId
	 */
	public Long getOldExpId() {
		return oldExpId;
	}

	/**
	 * @param oldExpId
	 *            the oldExpId to set
	 */
	public void setOldExpId(Long oldExpId) {
		this.oldExpId = oldExpId;
	}

	/**
	 * @return the isValidateScreen
	 */
	public String getIsValidateScreen() {
		return isValidateScreen;
	}

	/**
	 * @param isValidateScreen
	 *            the isValidateScreen to set
	 */
	public void setIsValidateScreen(String isValidateScreen) {
		this.isValidateScreen = isValidateScreen;
	}

	/**
	 * @return the octroiCharge
	 */
	public Double getOctroiCharge() {
		return octroiCharge;
	}

	/**
	 * @param octroiCharge
	 *            the octroiCharge to set
	 */
	public void setOctroiCharge(Double octroiCharge) {
		this.octroiCharge = octroiCharge;
	}

	/**
	 * @return the octroiCharges
	 */
	public Double[] getOctroiCharges() {
		return octroiCharges;
	}

	/**
	 * @param octroiCharges
	 *            the octroiCharges to set
	 */
	public void setOctroiCharges(Double[] octroiCharges) {
		this.octroiCharges = octroiCharges;
	}

	/**
	 * @return the empList
	 */
	public List<LabelValueBean> getEmpList() {
		return empList;
	}

	/**
	 * @param empList
	 *            the empList to set
	 */
	public void setEmpList(List<LabelValueBean> empList) {
		this.empList = empList;
	}

	/**
	 * @return the expenseEntriesId
	 */
	public Long getExpenseEntriesId() {
		return expenseEntriesId;
	}

	/**
	 * @param expenseEntriesId
	 *            the expenseEntriesId to set
	 */
	public void setExpenseEntriesId(Long expenseEntriesId) {
		this.expenseEntriesId = expenseEntriesId;
	}

	/**
	 * @return the expenseEntriesIds
	 */
	public Long[] getExpenseEntriesIds() {
		return expenseEntriesIds;
	}

	/**
	 * @param expenseEntriesIds
	 *            the expenseEntriesIds to set
	 */
	public void setExpenseEntriesIds(Long[] expenseEntriesIds) {
		this.expenseEntriesIds = expenseEntriesIds;
	}

	/**
	 * @return the expenseId
	 */
	public Long getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId
	 *            the expenseId to set
	 */
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	/**
	 * @return the txNumber
	 */
	public String getTxNumber() {
		return txNumber;
	}

	/**
	 * @param txNumber
	 *            the txNumber to set
	 */
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	/**
	 * @return the postingDate
	 */
	public String getPostingDate() {
		return postingDate;
	}

	/**
	 * @param postingDate
	 *            the postingDate to set
	 */
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	/**
	 * @return the documentDate
	 */
	public String getDocumentDate() {
		return documentDate;
	}

	/**
	 * @param documentDate
	 *            the documentDate to set
	 */
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	/**
	 * @return the expenseFor
	 */
	public String getExpenseFor() {
		return expenseFor;
	}

	/**
	 * @param expenseFor
	 *            the expenseFor to set
	 */
	public void setExpenseFor(String expenseFor) {
		this.expenseFor = expenseFor;
	}

	/**
	 * @return the expenseForList
	 */
	public List<LabelValueBean> getExpenseForList() {
		return expenseForList;
	}

	/**
	 * @param expenseForList
	 *            the expenseForList to set
	 */
	public void setExpenseForList(List<LabelValueBean> expenseForList) {
		this.expenseForList = expenseForList;
	}

	/**
	 * @return the expenseType
	 */
	public Integer getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType
	 *            the expenseType to set
	 */
	public void setExpenseType(Integer expenseType) {
		this.expenseType = expenseType;
	}

	/**
	 * @return the expenseTypeList
	 */
	public List<LabelValueBean> getExpenseTypeList() {
		return expenseTypeList;
	}

	/**
	 * @param expenseTypeList
	 *            the expenseTypeList to set
	 */
	public void setExpenseTypeList(List<LabelValueBean> expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}

	/**
	 * @return the expenseMode
	 */
	public Integer getExpenseMode() {
		return expenseMode;
	}

	/**
	 * @param expenseMode
	 *            the expenseMode to set
	 */
	public void setExpenseMode(Integer expenseMode) {
		this.expenseMode = expenseMode;
	}

	/**
	 * @return the expenseModeList
	 */
	public List<LabelValueBean> getExpenseModeList() {
		return expenseModeList;
	}

	/**
	 * @param expenseModeList
	 *            the expenseModeList to set
	 */
	public void setExpenseModeList(List<LabelValueBean> expenseModeList) {
		this.expenseModeList = expenseModeList;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the chequeDate
	 */
	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate
	 *            the chequeDate to set
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the bank
	 */
	public Integer getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */
	public void setBank(Integer bank) {
		this.bank = bank;
	}

	/**
	 * @return the bankList
	 */
	public List<LabelValueBean> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList
	 *            the bankList to set
	 */
	public void setBankList(List<LabelValueBean> bankList) {
		this.bankList = bankList;
	}

	/**
	 * @return the bankBranchName
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @param bankBranchName
	 *            the bankBranchName to set
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @return the expenseStatus
	 */
	public String getExpenseStatus() {
		return expenseStatus;
	}

	/**
	 * @param expenseStatus
	 *            the expenseStatus to set
	 */
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	/**
	 * @return the loginOfficeId
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * @param loginOfficeId
	 *            the loginOfficeId to set
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * @return the loginOfficeCode
	 */
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}

	/**
	 * @param loginOfficeCode
	 *            the loginOfficeCode to set
	 */
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the empDtlsTO
	 */
	public List<EmployeeExpenseDetailTO> getEmpDtlsTO() {
		return empDtlsTO;
	}

	/**
	 * @param empDtlsTO
	 *            the empDtlsTO to set
	 */
	public void setEmpDtlsTO(List<EmployeeExpenseDetailTO> empDtlsTO) {
		this.empDtlsTO = empDtlsTO;
	}

	/**
	 * @return the consgDtlsTO
	 */
	public List<ConsignmentExpenseDetailTO> getConsgDtlsTO() {
		return consgDtlsTO;
	}

	/**
	 * @param consgDtlsTO
	 *            the consgDtlsTO to set
	 */
	public void setConsgDtlsTO(List<ConsignmentExpenseDetailTO> consgDtlsTO) {
		this.consgDtlsTO = consgDtlsTO;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the amounts
	 */
	public Double[] getAmounts() {
		return amounts;
	}

	/**
	 * @param amounts
	 *            the amounts to set
	 */
	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}

	/**
	 * @return the positions
	 */
	public Integer[] getPositions() {
		return positions;
	}

	/**
	 * @param positions
	 *            the positions to set
	 */
	public void setPositions(Integer[] positions) {
		this.positions = positions;
	}

	/**
	 * @return the employeeIds
	 */
	public Integer[] getEmployeeIds() {
		return employeeIds;
	}

	/**
	 * @param employeeIds
	 *            the employeeIds to set
	 */
	public void setEmployeeIds(Integer[] employeeIds) {
		this.employeeIds = employeeIds;
	}

	/**
	 * @return the consgIds
	 */
	public Integer[] getConsgIds() {
		return consgIds;
	}

	/**
	 * @param consgIds
	 *            the consgIds to set
	 */
	public void setConsgIds(Integer[] consgIds) {
		this.consgIds = consgIds;
	}

	/**
	 * @return the consgNos
	 */
	public String[] getConsgNos() {
		return consgNos;
	}

	/**
	 * @param consgNos
	 *            the consgNos to set
	 */
	public void setConsgNos(String[] consgNos) {
		this.consgNos = consgNos;
	}

	/**
	 * @return the serviceCharges
	 */
	public Double[] getServiceCharges() {
		return serviceCharges;
	}

	/**
	 * @param serviceCharges
	 *            the serviceCharges to set
	 */
	public void setServiceCharges(Double[] serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	/**
	 * @return the otherCharges
	 */
	public Double[] getOtherCharges() {
		return otherCharges;
	}

	/**
	 * @param otherCharges
	 *            the otherCharges to set
	 */
	public void setOtherCharges(Double[] otherCharges) {
		this.otherCharges = otherCharges;
	}

	/**
	 * @return the totals
	 */
	public Double[] getTotals() {
		return totals;
	}

	/**
	 * @param totals
	 *            the totals to set
	 */
	public void setTotals(Double[] totals) {
		this.totals = totals;
	}

	/**
	 * @return the isCrNote
	 */
	public String getIsCrNote() {
		return isCrNote;
	}

	/**
	 * @param isCrNote
	 *            the isCrNote to set
	 */
	public void setIsCrNote(String isCrNote) {
		this.isCrNote = isCrNote;
	}

}
