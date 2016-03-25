/**
 * 
 */
package com.ff.to.ratemanagement.operations.ratequotation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.StateTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixTO;


/**
 * @author rmaladi
 *
 */
public class RateQuotationProposedRatesTO extends CGBaseTO{

			private static final long serialVersionUID = 1L;
			
			
			private EmployeeTO approver;
			private Set<RateQuotationProductCategoryHeaderTO> rateQuotationProductCategoryHeaderTO;
			private String isApproved;
			
			private RateQuotationProductCategoryHeaderTO productCatHeaderTO;
			
			
			private List<RateBenchMarkMatrixTO> rateBMMList;
			
			
			private List<RateProductCategoryTO> rateProdCatList;
			private List<RateVobSlabsTO> rateVobSlabsList;
			private List<RateWeightSlabsTO> rateWtSlabsList;
			private List<RateSectorsTO> rateSectorsList;
			private List<RateMinChargeableWeightTO> rateMinChargWtList;
			
			private Integer rateVobSlabsId;
		
			private Integer rateQuotationId;
			private Integer rateProdCatId;
			private Integer rateOriginSectorId;
			private Integer rateQuotationProdCatHeaderId;
			private Integer rateMinChargWtId;
			private Map<String, List<RateQuotationSlabRateTO>> rateMatrixMap ;
			
			
			private int rowCount;
			private int colCount;
			private Double[] rate = 	new Double[rowCount];
			private Double[] specialDestRate = 	new Double[rowCount];
			private Integer[] pincode = 	new Integer[rowCount];
			private Integer[] cityId = 	new Integer[rowCount];
			//private Integer[] rateCSecId = 	new Integer[rowCount];
			private Integer[] rateASecId = 	new Integer[rowCount];
			private Integer[] rateTSecId = 	new Integer[rowCount];
			private Integer[] rateBenchMarkMatrixId = 	new Integer[rowCount];
			private Integer[] rateQuotSlabRateId = 	new Integer[rowCount];
			private Integer[] rateWtSlabId = new Integer[rowCount];
			private Integer rateAddWtSlabId;
			
			private Integer rateCSecId;
			//private Integer rateCWtId;
			
			//private Integer[] rateCWtId = 	new Integer[rowCount];
			private Integer[] rateAWtId = 	new Integer[rowCount];
			private Integer[] rateTWtId = 	new Integer[rowCount];
			
			private String secArrStr;
			private String wtArrStr;
			private Integer[] rateOrgSec = new Integer[rowCount];
			private Integer[] rateVob = new Integer[rowCount];
			private Integer[] rateProdId = 	new Integer[rowCount];
			private Integer[] rateMinChargWt = 	new Integer[rowCount];
			
			
			private Integer[] rateQuotStartWeight = new Integer[rowCount];
			private Integer[] rateQuotCOStartWeight = new Integer[rowCount];
			private Integer[] rateQuotEndWeight = new Integer[rowCount];
			private Integer rateQuotAddWeight;
			private Integer[] rateQuotCOAddWeight =  new Integer[rowCount];
			private Integer[] rateCOAddWtSlab =  new Integer[rowCount];
			private Integer[] rateCOAddWtSlabId =  new Integer[rowCount];
			
			
			private Integer[] rateWtId = new Integer[rowCount];
			private String rateProdCatCode;
			private Boolean wtSlabChange = Boolean.FALSE;
			private String[] isROI = new String[rowCount];
			private String regionCode;
			private String custCatCode;
			private Integer custCatId;
			private Map<String, RateQuotationProductCategoryHeaderTO> productHeaderMap;
			private Integer[] destProdCat = new Integer[rowCount];
			private Integer[] pinProdCat = new Integer[rowCount];
			private Integer rowNo;
			private String moduleType;
			private String slabRateStatus;
			private String indCatCode;
			private Integer regionId;
			private String transMsg;
			private boolean isSaved = Boolean.FALSE;
			private Integer[] stateId = new Integer[rowCount];
			private List<StateTO> statesList;
			private List<CityTO>[] cityList = new ArrayList[rowCount];
			private String rateConfiguredType;
			private String flatRate;
			
			
			
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
			 * @return the regionId
			 */
			public Integer getRegionId() {
				return regionId;
			}
			/**
			 * @param regionId the regionId to set
			 */
			public void setRegionId(Integer regionId) {
				this.regionId = regionId;
			}
			/**
			 * @return the indCat
			 */
			public String getIndCatCode() {
				return indCatCode;
			}
			/**
			 * @param indCat the indCat to set
			 */
			public void setIndCatCode(String indCatCode) {
				this.indCatCode = indCatCode;
			}
			/**
			 * @return the slabRateStatus
			 */
			public String getSlabRateStatus() {
				return slabRateStatus;
			}
			/**
			 * @param slabRateStatus the slabRateStatus to set
			 */
			public void setSlabRateStatus(String slabRateStatus) {
				this.slabRateStatus = slabRateStatus;
			}
			/**
			 * @return the moduleType
			 */
			public String getModuleType() {
				return moduleType;
			}
			/**
			 * @param moduleType the moduleType to set
			 */
			public void setModuleType(String moduleType) {
				this.moduleType = moduleType;
			}
			/**
			 * @return the destProdCat
			 */
			public Integer[] getDestProdCat() {
				return destProdCat;
			}
			/**
			 * @return the rowNo
			 */
			public Integer getRowNo() {
				return rowNo;
			}
			/**
			 * @param rowNo the rowNo to set
			 */
			public void setRowNo(Integer rowNo) {
				this.rowNo = rowNo;
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
			 * @return the pinProdCat
			 */
			public Integer[] getPinProdCat() {
				return pinProdCat;
			}
			/**
			 * @param pinProdCat the pinProdCat to set
			 */
			public void setPinProdCat(Integer[] pinProdCat) {
				this.pinProdCat = pinProdCat;
			}
			/**
			 * @param destProdCat the destProdCat to set
			 */
			public void setDestProdCat(Integer[] destProdCat) {
				this.destProdCat = destProdCat;
			}
			/**
			 * @return the productHeaderMap
			 */
			public Map<String, RateQuotationProductCategoryHeaderTO> getProductHeaderMap() {
				return productHeaderMap;
			}
			/**
			 * @param productHeaderMap the productHeaderMap to set
			 */
			public void setProductHeaderMap(
					Map<String, RateQuotationProductCategoryHeaderTO> productHeaderMap) {
				this.productHeaderMap = productHeaderMap;
			}
			/**
			 * @return the custCatId
			 */
			public Integer getCustCatId() {
				return custCatId;
			}
			/**
			 * @param custCatId the custCatId to set
			 */
			public void setCustCatId(Integer custCatId) {
				this.custCatId = custCatId;
			}
			/**
			 * @return the custCatCode
			 */
			public String getCustCatCode() {
				return custCatCode;
			}
			/**
			 * @param custCatCode the custCatCode to set
			 */
			public void setCustCatCode(String custCatCode) {
				this.custCatCode = custCatCode;
			}
			/**
			 * @return the regionCode
			 */
			public String getRegionCode() {
				return regionCode;
			}
			/**
			 * @param regionCode the regionCode to set
			 */
			public void setRegionCode(String regionCode) {
				this.regionCode = regionCode;
			}
			/**
			 * @return the rateProdCatCode
			 */
			public String getRateProdCatCode() {
				return rateProdCatCode;
			}
			/**
			 * @return the isROI
			 */
			public String[] getIsROI() {
				return isROI;
			}
			/**
			 * @param isROI the isROI to set
			 */
			public void setIsROI(String[] isROI) {
				this.isROI = isROI;
			}
			/**
			 * @return the wtSlabChange
			 */
			public Boolean getWtSlabChange() {
				return wtSlabChange;
			}
			/**
			 * @param wtSlabChange the wtSlabChange to set
			 */
			public void setWtSlabChange(Boolean wtSlabChange) {
				this.wtSlabChange = wtSlabChange;
			}
			/**
			 * @return the rateWtSlabId
			 */
			public Integer[] getRateWtSlabId() {
				return rateWtSlabId;
			}
			/**
			 * @param rateWtSlabId the rateWtSlabId to set
			 */
			public void setRateWtSlabId(Integer[] rateWtSlabId) {
				this.rateWtSlabId = rateWtSlabId;
			}
			/**
			 * @return the rateAddWtSlabId
			 */
			public Integer getRateAddWtSlabId() {
				return rateAddWtSlabId;
			}
			/**
			 * @param rateAddWtSlabId the rateAddWtSlabId to set
			 */
			public void setRateAddWtSlabId(Integer rateAddWtSlabId) {
				this.rateAddWtSlabId = rateAddWtSlabId;
			}
			/**
			 * @param rateProdCatCode the rateProdCatCode to set
			 */
			public void setRateProdCatCode(String rateProdCatCode) {
				this.rateProdCatCode = rateProdCatCode;
			}
			/**
			 * @return the productCatHeaderTO
			 */
			public RateQuotationProductCategoryHeaderTO getProductCatHeaderTO() {
				return productCatHeaderTO;
			}
			/**
			 * @param productCatHeaderTO the productCatHeaderTO to set
			 */
			public void setProductCatHeaderTO(
					RateQuotationProductCategoryHeaderTO productCatHeaderTO) {
				this.productCatHeaderTO = productCatHeaderTO;
			}
			/**
			 * @return the rateWtId
			 */
			public Integer[] getRateWtId() {
				return rateWtId;
			}
			/**
			 * @param rateWtId the rateWtId to set
			 */
			public void setRateWtId(Integer[] rateWtId) {
				this.rateWtId = rateWtId;
			}
			/**
			 * @return the specialDestRate
			 */
			public Double[] getSpecialDestRate() {
				return specialDestRate;
			}
			/**
			 * @param specialDestRate the specialDestRate to set
			 */
			public void setSpecialDestRate(Double[] specialDestRate) {
				this.specialDestRate = specialDestRate;
			}
			/**
			 * @return the pincode
			 */
			public Integer[] getPincode() {
				return pincode;
			}
			/**
			 * @param pincode the pincode to set
			 */
			public void setPincode(Integer[] pincode) {
				this.pincode = pincode;
			}
			/**
			 * @return the cityId
			 */
			public Integer[] getCityId() {
				return cityId;
			}
			/**
			 * @param cityId the cityId to set
			 */
			public void setCityId(Integer[] cityId) {
				this.cityId = cityId;
			}
			/**
			 * @return the rateQuotEndWeight
			 */
			public Integer[] getRateQuotEndWeight() {
				return rateQuotEndWeight;
			}
			/**
			 * @return the rateQuotStartWeight
			 */
			public Integer[] getRateQuotStartWeight() {
				return rateQuotStartWeight;
			}
			/**
			 * @param rateQuotStartWeight the rateQuotStartWeight to set
			 */
			public void setRateQuotStartWeight(Integer[] rateQuotStartWeight) {
				this.rateQuotStartWeight = rateQuotStartWeight;
			}
			/**
			 * @param rateQuotEndWeight the rateQuotEndWeight to set
			 */
			public void setRateQuotEndWeight(Integer[] rateQuotEndWeight) {
				this.rateQuotEndWeight = rateQuotEndWeight;
			}
			/**
			 * @return the rateQuotAddWeight
			 */
			public Integer getRateQuotAddWeight() {
				return rateQuotAddWeight;
			}
			/**
			 * @param rateQuotAddWeight the rateQuotAddWeight to set
			 */
			public void setRateQuotAddWeight(Integer rateQuotAddWeight) {
				this.rateQuotAddWeight = rateQuotAddWeight;
			}
			/**
			 * @return the approver
			 */
			public EmployeeTO getApprover() {
				return approver;
			}
			/**
			 * @param approver the approver to set
			 */
			public void setApprover(EmployeeTO approver) {
				this.approver = approver;
			}
			/**
			 * @return the rateQuotationProductCategoryHeaderTO
			 */
			public Set<RateQuotationProductCategoryHeaderTO> getRateQuotationProductCategoryHeaderTO() {
				return rateQuotationProductCategoryHeaderTO;
			}
			/**
			 * @param rateQuotationProductCategoryHeaderTO the rateQuotationProductCategoryHeaderTO to set
			 */
			public void setRateQuotationProductCategoryHeaderTO(
					Set<RateQuotationProductCategoryHeaderTO> rateQuotationProductCategoryHeaderTO) {
				this.rateQuotationProductCategoryHeaderTO = rateQuotationProductCategoryHeaderTO;
			}
			/**
			 * @return the isApproved
			 */
			public String getIsApproved() {
				return isApproved;
			}
			/**
			 * @param isApproved the isApproved to set
			 */
			public void setIsApproved(String isApproved) {
				this.isApproved = isApproved;
			}
			/**
			 * @return the rateBMMList
			 */
			public List<RateBenchMarkMatrixTO> getRateBMMList() {
				return rateBMMList;
			}
			/**
			 * @param rateBMMList the rateBMMList to set
			 */
			public void setRateBMMList(List<RateBenchMarkMatrixTO> rateBMMList) {
				this.rateBMMList = rateBMMList;
			}
			/**
			 * @return the rateProdCatList
			 */
			public List<RateProductCategoryTO> getRateProdCatList() {
				return rateProdCatList;
			}
			/**
			 * @param rateProdCatList the rateProdCatList to set
			 */
			public void setRateProdCatList(List<RateProductCategoryTO> rateProdCatList) {
				this.rateProdCatList = rateProdCatList;
			}
			/**
			 * @return the rateVobSlabsList
			 */
			public List<RateVobSlabsTO> getRateVobSlabsList() {
				return rateVobSlabsList;
			}
			/**
			 * @param rateVobSlabsList the rateVobSlabsList to set
			 */
			public void setRateVobSlabsList(List<RateVobSlabsTO> rateVobSlabsList) {
				this.rateVobSlabsList = rateVobSlabsList;
			}
			/**
			 * @return the rateWtSlabsList
			 */
			public List<RateWeightSlabsTO> getRateWtSlabsList() {
				return rateWtSlabsList;
			}
			/**
			 * @param rateWtSlabsList the rateWtSlabsList to set
			 */
			public void setRateWtSlabsList(List<RateWeightSlabsTO> rateWtSlabsList) {
				this.rateWtSlabsList = rateWtSlabsList;
			}
			/**
			 * @return the rateSectorsList
			 */
			public List<RateSectorsTO> getRateSectorsList() {
				return rateSectorsList;
			}
			/**
			 * @param rateSectorsList the rateSectorsList to set
			 */
			public void setRateSectorsList(List<RateSectorsTO> rateSectorsList) {
				this.rateSectorsList = rateSectorsList;
			}
			/**
			 * @return the rateMinChargWtList
			 */
			public List<RateMinChargeableWeightTO> getRateMinChargWtList() {
				return rateMinChargWtList;
			}
			/**
			 * @param rateMinChargWtList the rateMinChargWtList to set
			 */
			public void setRateMinChargWtList(
					List<RateMinChargeableWeightTO> rateMinChargWtList) {
				this.rateMinChargWtList = rateMinChargWtList;
			}
			/**
			 * @return the rateVobSlabsId
			 */
			public Integer getRateVobSlabsId() {
				return rateVobSlabsId;
			}
			/**
			 * @param rateVobSlabsId the rateVobSlabsId to set
			 */
			public void setRateVobSlabsId(Integer rateVobSlabsId) {
				this.rateVobSlabsId = rateVobSlabsId;
			}
			/**
			 * @return the rateQuotationId
			 */
			public Integer getRateQuotationId() {
				return rateQuotationId;
			}
			/**
			 * @param rateQuotationId the rateQuotationId to set
			 */
			public void setRateQuotationId(Integer rateQuotationId) {
				this.rateQuotationId = rateQuotationId;
			}
			/**
			 * @return the rateProdCatId
			 */
			public Integer getRateProdCatId() {
				return rateProdCatId;
			}
			/**
			 * @param rateProdCatId the rateProdCatId to set
			 */
			public void setRateProdCatId(Integer rateProdCatId) {
				this.rateProdCatId = rateProdCatId;
			}
			/**
			 * @return the rateOriginSectorId
			 */
			public Integer getRateOriginSectorId() {
				return rateOriginSectorId;
			}
			/**
			 * @param rateOriginSectorId the rateOriginSectorId to set
			 */
			public void setRateOriginSectorId(Integer rateOriginSectorId) {
				this.rateOriginSectorId = rateOriginSectorId;
			}
			/**
			 * @return the rateQuotationProdCatHeaderId
			 */
			public Integer getRateQuotationProdCatHeaderId() {
				return rateQuotationProdCatHeaderId;
			}
			/**
			 * @param rateQuotationProdCatHeaderId the rateQuotationProdCatHeaderId to set
			 */
			public void setRateQuotationProdCatHeaderId(Integer rateQuotationProdCatHeaderId) {
				this.rateQuotationProdCatHeaderId = rateQuotationProdCatHeaderId;
			}
			/**
			 * @return the rateMinChargWtId
			 */
			public Integer getRateMinChargWtId() {
				return rateMinChargWtId;
			}
			/**
			 * @param rateMinChargWtId the rateMinChargWtId to set
			 */
			public void setRateMinChargWtId(Integer rateMinChargWtId) {
				this.rateMinChargWtId = rateMinChargWtId;
			}
			/**
			 * @return the rateMatrixMap
			 */
			public Map<String, List<RateQuotationSlabRateTO>> getRateMatrixMap() {
				return rateMatrixMap;
			}
			/**
			 * @param rateMatrixMap the rateMatrixMap to set
			 */
			public void setRateMatrixMap(
					Map<String, List<RateQuotationSlabRateTO>> rateMatrixMap) {
				this.rateMatrixMap = rateMatrixMap;
			}

			/**
			 * @return the colCount
			 */
			public int getColCount() {
				return colCount;
			}
			/**
			 * @param colCount the colCount to set
			 */
			public void setColCount(int colCount) {
				this.colCount = colCount;
			}
			/**
			 * @return the rate
			 */
			public Double[] getRate() {
				return rate;
			}
			/**
			 * @param rate the rate to set
			 */
			public void setRate(Double[] rate) {
				this.rate = rate;
			}
			/**
			 * @return the rateASecId
			 */
			public Integer[] getRateASecId() {
				return rateASecId;
			}
			/**
			 * @param rateASecId the rateASecId to set
			 */
			public void setRateASecId(Integer[] rateASecId) {
				this.rateASecId = rateASecId;
			}
			/**
			 * @return the rateTSecId
			 */
			public Integer[] getRateTSecId() {
				return rateTSecId;
			}
			/**
			 * @param rateTSecId the rateTSecId to set
			 */
			public void setRateTSecId(Integer[] rateTSecId) {
				this.rateTSecId = rateTSecId;
			}
			/**
			 * @return the rateBenchMarkMatrixId
			 */
			public Integer[] getRateBenchMarkMatrixId() {
				return rateBenchMarkMatrixId;
			}
			/**
			 * @param rateBenchMarkMatrixId the rateBenchMarkMatrixId to set
			 */
			public void setRateBenchMarkMatrixId(Integer[] rateBenchMarkMatrixId) {
				this.rateBenchMarkMatrixId = rateBenchMarkMatrixId;
			}
			/**
			 * @return the rateQuotSlabRateId
			 */
			public Integer[] getRateQuotSlabRateId() {
				return rateQuotSlabRateId;
			}
			/**
			 * @param rateQuotSlabRateId the rateQuotSlabRateId to set
			 */
			public void setRateQuotSlabRateId(Integer[] rateQuotSlabRateId) {
				this.rateQuotSlabRateId = rateQuotSlabRateId;
			}
			/**
			 * @return the rateCSecId
			 */
			public Integer getRateCSecId() {
				return rateCSecId;
			}
			/**
			 * @param rateCSecId the rateCSecId to set
			 */
			public void setRateCSecId(Integer rateCSecId) {
				this.rateCSecId = rateCSecId;
			}
			/**
			 * @return the rateCWtId
			 */
			/*public Integer getRateCWtId() {
				return rateCWtId;
			}
			*//**
			 * @param rateCWtId the rateCWtId to set
			 *//*
			public void setRateCWtId(Integer rateCWtId) {
				this.rateCWtId = rateCWtId;
			}*/
			/**
			 * @return the rateAWtId
			 */
			public Integer[] getRateAWtId() {
				return rateAWtId;
			}
			/**
			 * @param rateAWtId the rateAWtId to set
			 */
			public void setRateAWtId(Integer[] rateAWtId) {
				this.rateAWtId = rateAWtId;
			}
			/**
			 * @return the rateTWtId
			 */
			public Integer[] getRateTWtId() {
				return rateTWtId;
			}
			/**
			 * @param rateTWtId the rateTWtId to set
			 */
			public void setRateTWtId(Integer[] rateTWtId) {
				this.rateTWtId = rateTWtId;
			}
			/**
			 * @return the secArrStr
			 */
			public String getSecArrStr() {
				return secArrStr;
			}
			/**
			 * @param secArrStr the secArrStr to set
			 */
			public void setSecArrStr(String secArrStr) {
				this.secArrStr = secArrStr;
			}
			/**
			 * @return the wtArrStr
			 */
			public String getWtArrStr() {
				return wtArrStr;
			}
			/**
			 * @param wtArrStr the wtArrStr to set
			 */
			public void setWtArrStr(String wtArrStr) {
				this.wtArrStr = wtArrStr;
			}
			/**
			 * @return the rateOrgSec
			 */
			public Integer[] getRateOrgSec() {
				return rateOrgSec;
			}
			/**
			 * @param rateOrgSec the rateOrgSec to set
			 */
			public void setRateOrgSec(Integer[] rateOrgSec) {
				this.rateOrgSec = rateOrgSec;
			}
			/**
			 * @return the rateVob
			 */
			public Integer[] getRateVob() {
				return rateVob;
			}
			/**
			 * @param rateVob the rateVob to set
			 */
			public void setRateVob(Integer[] rateVob) {
				this.rateVob = rateVob;
			}
			/**
			 * @return the rateProdId
			 */
			public Integer[] getRateProdId() {
				return rateProdId;
			}
			/**
			 * @param rateProdId the rateProdId to set
			 */
			public void setRateProdId(Integer[] rateProdId) {
				this.rateProdId = rateProdId;
			}
			/**
			 * @return the rateMinChargWt
			 */
			public Integer[] getRateMinChargWt() {
				return rateMinChargWt;
			}
			/**
			 * @param rateMinChargWt the rateMinChargWt to set
			 */
			public void setRateMinChargWt(Integer[] rateMinChargWt) {
				this.rateMinChargWt = rateMinChargWt;
			}
			
			public Integer[] getStateId() {
				return stateId;
			}
			public void setStateId(Integer[] stateId) {
				this.stateId = stateId;
			}
			public List<StateTO> getStatesList() {
				return statesList;
			}
			public void setStatesList(List<StateTO> statesList) {
				this.statesList = statesList;
			}
			public List<CityTO>[] getCityList() {
				return cityList;
			}
			public void setCityList(List<CityTO>[] cityList) {
				this.cityList = cityList;
			}
			public Integer[] getRateQuotCOAddWeight() {
				return rateQuotCOAddWeight;
			}
			public void setRateQuotCOAddWeight(Integer[] rateQuotCOAddWeight) {
				this.rateQuotCOAddWeight = rateQuotCOAddWeight;
			}
			public Integer[] getRateQuotCOStartWeight() {
				return rateQuotCOStartWeight;
			}
			public void setRateQuotCOStartWeight(Integer[] rateQuotCOStartWeight) {
				this.rateQuotCOStartWeight = rateQuotCOStartWeight;
			}
			public String getRateConfiguredType() {
				return rateConfiguredType;
			}
			public void setRateConfiguredType(String rateConfiguredType) {
				this.rateConfiguredType = rateConfiguredType;
			}
			public Integer[] getRateCOAddWtSlab() {
				return rateCOAddWtSlab;
			}
			public void setRateCOAddWtSlab(Integer[] rateCOAddWtSlab) {
				this.rateCOAddWtSlab = rateCOAddWtSlab;
			}
			public Integer[] getRateCOAddWtSlabId() {
				return rateCOAddWtSlabId;
			}
			public void setRateCOAddWtSlabId(Integer[] rateCOAddWtSlabId) {
				this.rateCOAddWtSlabId = rateCOAddWtSlabId;
			}
			public String getFlatRate() {
				return flatRate;
			}
			public void setFlatRate(String flatRate) {
				this.flatRate = flatRate;
			}
			
}
