package com.ff.to.ratemanagement.operations.ratebenchmark;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateCustomerProductCatMapTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;

public class RateBenchMarkHeaderTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateBenchMarkHeaderId;
	private Date rateBenchMarkEffectiveFrom;
	private Date rateBenchMarkEffectiveTo;
	
	private RateIndustryCategoryTO rateIndustryCategoryTO;
	private RateCustomerCategoryTO rateCustomerCategoryTO;
	private EmployeeTO approver;
	private Set<RateBenchMarkProductTO> rateBenchMarkProductTO;
	private String isApproved;
	
	private List<RateBenchMarkMatrixTO> rateBMMList;
	
	private List<LabelValueBean> rateIndCatList;
	private List<RateProductCategoryTO> rateProdCatList;
	private List<RateVobSlabsTO> rateVobSlabsList;
	private List<RateWeightSlabsTO> rateWtSlabsList;
	private List<RateSectorsTO> rateSectorsList;
	private List<RateCustomerProductCatMapTO> rateCustProdCatMapList;
	private List<RateCustomerCategoryTO> rateCustCatList;
	private List<RateMinChargeableWeightTO> rateMinChargWtList;
	private Integer rateVobSlabsId;
	private String rateBenchMarkDateStr;
	private String empCode;
	private String empName;
	private Integer empId;
	private Integer rateIndustryCategoryId;
	private Integer rateProdCatId;
	private Integer rateCustCatId;
	private Integer rateOriginSectorId;
	private Integer rateBenchMarkProductId;
	private Integer rateBenchMarkMatrixHeaderId;
	private Integer rateMinChargWtId;
	private Map<String, List<RateBenchMarkMatrixTO>> rateMatrixMap ;
	
	private Integer rateCurrentHeaderId;
	
	private int rowCount;
	private int colCount;
	private Double[] rate = 	new Double[rowCount];
	//private Integer[] rateCSecId = 	new Integer[rowCount];
	private Integer[] rateASecId = 	new Integer[rowCount];
	private Integer[] rateTSecId = 	new Integer[rowCount];
	private Integer[] rateBenchMarkMatrixId = 	new Integer[rowCount];
	private Integer rateCSecId;
	private Integer rateCWtId;
	
	//private Integer[] rateCWtId = 	new Integer[rowCount];
	private Integer[] rateAWtId = 	new Integer[rowCount];
	private Integer[] rateTWtId = 	new Integer[rowCount];
	
	private String secArrStr;
	private String wtArrStr;
	private Integer[] rateOrgSec = new Integer[rowCount];
	private Integer[] rateVob = new Integer[rowCount];
	private Integer[] rateProdId = 	new Integer[rowCount];
	private Integer[] rateMinChargWt = 	new Integer[rowCount];
	private String rateBenchMarkType;
	private String rateIndustryCategoryCode;
	private String transMsg;
	private boolean isSaved = Boolean.FALSE;
	
	/**
	 * @return the rateIndustryCategoryCode
	 */
	public String getRateIndustryCategoryCode() {
		return rateIndustryCategoryCode;
	}
	/**
	 * @param rateIndustryCategoryCode the rateIndustryCategoryCode to set
	 */
	public void setRateIndustryCategoryCode(String rateIndustryCategoryCode) {
		this.rateIndustryCategoryCode = rateIndustryCategoryCode;
	}
	public Integer getRateCurrentHeaderId() {
		return rateCurrentHeaderId;
	}
	public void setRateCurrentHeaderId(Integer rateCurrentHeaderId) {
		this.rateCurrentHeaderId = rateCurrentHeaderId;
	}
	public String getRateBenchMarkType() {
		return rateBenchMarkType;
	}
	public void setRateBenchMarkType(String rateBenchMarkType) {
		this.rateBenchMarkType = rateBenchMarkType;
	}
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public Map<String, List<RateBenchMarkMatrixTO>> getRateMatrixMap() {
		return rateMatrixMap;
	}
	public void setRateMatrixMap(
			Map<String, List<RateBenchMarkMatrixTO>> rateMatrixMap) {
		this.rateMatrixMap = rateMatrixMap;
	}
	public Integer[] getRateMinChargWt() {
		return rateMinChargWt;
	}
	public void setRateMinChargWt(Integer[] rateMinChargWt) {
		this.rateMinChargWt = rateMinChargWt;
	}
	public Integer[] getRateVob() {
		return rateVob;
	}
	public void setRateVob(Integer[] rateVob) {
		this.rateVob = rateVob;
	}
	public Integer[] getRateOrgSec() {
		return rateOrgSec;
	}
	public void setRateOrgSec(Integer[] rateOrgSec) {
		this.rateOrgSec = rateOrgSec;
	}
	public List<RateCustomerCategoryTO> getRateCustCatList() {
		return rateCustCatList;
	}
	public void setRateCustCatList(List<RateCustomerCategoryTO> rateCustCatList) {
		this.rateCustCatList = rateCustCatList;
	}
	public Integer[] getRateProdId() {
		return rateProdId;
	}
	public void setRateProdId(Integer[] rateProdId) {
		this.rateProdId = rateProdId;
	}
	public List<RateMinChargeableWeightTO> getRateMinChargWtList() {
		return rateMinChargWtList;
	}
	public void setRateMinChargWtList(
			List<RateMinChargeableWeightTO> rateMinChargWtList) {
		this.rateMinChargWtList = rateMinChargWtList;
	}
	public Integer getRateMinChargWtId() {
		return rateMinChargWtId;
	}
	public void setRateMinChargWtId(Integer rateMinChargWtId) {
		this.rateMinChargWtId = rateMinChargWtId;
	}
	public Integer getRateBenchMarkProductId() {
		return rateBenchMarkProductId;
	}
	public void setRateBenchMarkProductId(Integer rateBenchMarkProductId) {
		this.rateBenchMarkProductId = rateBenchMarkProductId;
	}
	public Integer getRateBenchMarkMatrixHeaderId() {
		return rateBenchMarkMatrixHeaderId;
	}
	public void setRateBenchMarkMatrixHeaderId(Integer rateBenchMarkMatrixHeaderId) {
		this.rateBenchMarkMatrixHeaderId = rateBenchMarkMatrixHeaderId;
	}
	public Integer[] getRateBenchMarkMatrixId() {
		return rateBenchMarkMatrixId;
	}
	public void setRateBenchMarkMatrixId(Integer[] rateBenchMarkMatrixId) {
		this.rateBenchMarkMatrixId = rateBenchMarkMatrixId;
	}
	public List<RateCustomerProductCatMapTO> getRateCustProdCatMapList() {
		return rateCustProdCatMapList;
	}
	public void setRateCustProdCatMapList(
			List<RateCustomerProductCatMapTO> rateCustProdCatMapList) {
		this.rateCustProdCatMapList = rateCustProdCatMapList;
	}
	public String getSecArrStr() {
		return secArrStr;
	}
	public void setSecArrStr(String secArrStr) {
		this.secArrStr = secArrStr;
	}
	public String getWtArrStr() {
		return wtArrStr;
	}
	public void setWtArrStr(String wtArrStr) {
		this.wtArrStr = wtArrStr;
	}
	public Integer getRateCSecId() {
		return rateCSecId;
	}
	public void setRateCSecId(Integer rateCSecId) {
		this.rateCSecId = rateCSecId;
	}
	public Integer getRateCWtId() {
		return rateCWtId;
	}
	public void setRateCWtId(Integer rateCWtId) {
		this.rateCWtId = rateCWtId;
	}
	public Integer getRateOriginSectorId() {
		return rateOriginSectorId;
	}
	public void setRateOriginSectorId(Integer rateOriginSectorId) {
		this.rateOriginSectorId = rateOriginSectorId;
	}
	public List<RateBenchMarkMatrixTO> getRateBMMList() {
		return rateBMMList;
	}
	public void setRateBMMList(List<RateBenchMarkMatrixTO> rateBMMList) {
		this.rateBMMList = rateBMMList;
	}
	public Integer getRateProdCatId() {
		return rateProdCatId;
	}
	public void setRateProdCatId(Integer rateProdCatId) {
		this.rateProdCatId = rateProdCatId;
	}
	public Integer getRateCustCatId() {
		return rateCustCatId;
	}
	public void setRateCustCatId(Integer rateCustCatId) {
		this.rateCustCatId = rateCustCatId;
	}
	public Integer getRateIndustryCategoryId() {
		return rateIndustryCategoryId;
	}
	public void setRateIndustryCategoryId(Integer rateIndustryCategoryId) {
		this.rateIndustryCategoryId = rateIndustryCategoryId;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	/*public Integer[] getRateCSecId() {
		return rateCSecId;
	}
	public void setRateCSecId(Integer[] rateCSecId) {
		this.rateCSecId = rateCSecId;
	}*/
	public Integer[] getRateASecId() {
		return rateASecId;
	}
	public void setRateASecId(Integer[] rateASecId) {
		this.rateASecId = rateASecId;
	}
	public Integer[] getRateTSecId() {
		return rateTSecId;
	}
	public void setRateTSecId(Integer[] rateTSecId) {
		this.rateTSecId = rateTSecId;
	}
	/*public Integer[] getRateCWtId() {
		return rateCWtId;
	}
	public void setRateCWtId(Integer[] rateCWtId) {
		this.rateCWtId = rateCWtId;
	}*/
	public Integer[] getRateAWtId() {
		return rateAWtId;
	}
	public void setRateAWtId(Integer[] rateAWtId) {
		this.rateAWtId = rateAWtId;
	}
	public Integer[] getRateTWtId() {
		return rateTWtId;
	}
	public void setRateTWtId(Integer[] rateTWtId) {
		this.rateTWtId = rateTWtId;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRateBenchMarkDateStr() {
		return rateBenchMarkDateStr;
	}
	public void setRateBenchMarkDateStr(String rateBenchMarkDateStr) {
		this.rateBenchMarkDateStr = rateBenchMarkDateStr;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getColCount() {
		return colCount;
	}
	public void setColCount(int colCount) {
		this.colCount = colCount;
	}
	public Double[] getRate() {
		return rate;
	}
	public void setRate(Double[] rate) {
		this.rate = rate;
	}
	public RateCustomerCategoryTO getRateCustomerCategoryTO() {
		return rateCustomerCategoryTO;
	}
	public void setRateCustomerCategoryTO(
			RateCustomerCategoryTO rateCustomerCategoryTO) {
		this.rateCustomerCategoryTO = rateCustomerCategoryTO;
	}
	public List<RateSectorsTO> getRateSectorsList() {
		return rateSectorsList;
	}
	public void setRateSectorsList(List<RateSectorsTO> rateSectorsList) {
		this.rateSectorsList = rateSectorsList;
	}
	public Integer getRateVobSlabsId() {
		return rateVobSlabsId;
	}
	public void setRateVobSlabsId(Integer rateVobSlabsId) {
		this.rateVobSlabsId = rateVobSlabsId;
	}
	public List<RateProductCategoryTO> getRateProdCatList() {
		return rateProdCatList;
	}
	public void setRateProdCatList(List<RateProductCategoryTO> rateProdCatList) {
		this.rateProdCatList = rateProdCatList;
	}
	public List<RateVobSlabsTO> getRateVobSlabsList() {
		return rateVobSlabsList;
	}
	public void setRateVobSlabsList(List<RateVobSlabsTO> rateVobSlabsList) {
		this.rateVobSlabsList = rateVobSlabsList;
	}
	public List<RateWeightSlabsTO> getRateWtSlabsList() {
		return rateWtSlabsList;
	}
	public void setRateWtSlabsList(List<RateWeightSlabsTO> rateWtSlabsList) {
		this.rateWtSlabsList = rateWtSlabsList;
	}
	public List<LabelValueBean> getRateIndCatList() {
		return rateIndCatList;
	}
	public void setRateIndCatList(List<LabelValueBean> rateIndCatList) {
		this.rateIndCatList = rateIndCatList;
	}
	public Integer getRateBenchMarkHeaderId() {
		return rateBenchMarkHeaderId;
	}
	public void setRateBenchMarkHeaderId(Integer rateBenchMarkHeaderId) {
		this.rateBenchMarkHeaderId = rateBenchMarkHeaderId;
	}
	
	public Date getRateBenchMarkEffectiveFrom() {
		return rateBenchMarkEffectiveFrom;
	}
	public void setRateBenchMarkEffectiveFrom(Date rateBenchMarkEffectiveFrom) {
		this.rateBenchMarkEffectiveFrom = rateBenchMarkEffectiveFrom;
	}
	public Date getRateBenchMarkEffectiveTo() {
		return rateBenchMarkEffectiveTo;
	}
	public void setRateBenchMarkEffectiveTo(Date rateBenchMarkEffectiveTo) {
		this.rateBenchMarkEffectiveTo = rateBenchMarkEffectiveTo;
	}
	public RateIndustryCategoryTO getRateIndustryCategoryTO() {
		return rateIndustryCategoryTO;
	}
	public void setRateIndustryCategoryTO(
			RateIndustryCategoryTO rateIndustryCategoryTO) {
		this.rateIndustryCategoryTO = rateIndustryCategoryTO;
	}
	public EmployeeTO getApprover() {
		return approver;
	}
	public void setApprover(EmployeeTO approver) {
		this.approver = approver;
	}
	public Set<RateBenchMarkProductTO> getRateBenchMarkProductTO() {
		return rateBenchMarkProductTO;
	}
	public void setRateBenchMarkProductTO(
			Set<RateBenchMarkProductTO> rateBenchMarkProductTO) {
		this.rateBenchMarkProductTO = rateBenchMarkProductTO;
	}
	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}
	/**
	 * @param transMsg the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}
	/**
	 * @return the isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved the isSaved to set
	 */
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	
	
}

