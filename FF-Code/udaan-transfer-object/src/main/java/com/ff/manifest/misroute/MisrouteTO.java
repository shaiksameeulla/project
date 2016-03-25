package com.ff.manifest.misroute;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestProcessTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

public class MisrouteTO extends CGBaseTO {

	private static final long serialVersionUID = 4073789612476388034L;
	private String misrouteDate;
	private String misrouteNo;
	private Integer misrouteId;
	private String misrouteType;// possible values may be Enum (C,P,B,M)
	private String manifestType;
	private Integer officeCode;
	private String officeType; // possible values may be Enum (BO,HO)
	private String officeName;
	private String misrouteManifestStatus;// possible values may be Enum (O,S)
	private String consgType;// possible values may be Enum (D,P)
	private List<RegionTO> destinationRegionList;
	private Integer destinationRegionId;
	private String destinationRegionName;// possible values may be Enum (D,P)
	private List<OfficeTO> destinationStationList;// getting the destination
													// offices
	private Integer destinationStationId;
	private String destinationStationName;
	private String destinationOfficeName;
	private String destinationOfficeCode;
	private Integer destinationCityId;
	private String destinationCityName;
	private List<CityTO> destinationCityList;
	private String manifestDirection;
	private Integer loginOfficeId;
	protected int rowCount;
	private String loginCityCode;
	private String officeCodeProcess;
	private List<MisrouteDetailsTO> misrouteDetailsTO;
	/** The process id. */
	private String processCode;
	/** The process id. */
	private Integer processId;
	/** The process No. */
	private String processNo;
	private Integer loginRegionId;
	private Integer[] positions = new Integer[rowCount];
	private ConsignmentTypeTO consignmentTypeTO;
	private PincodeTO pincodeTO;
	private ManifestProcessTO manifestProcessTo = new ManifestProcessTO();

	// Internal use for transferring data from UI to Action

	/** The scanned grid item no. */
	private String[] scannedItemNos = new String[rowCount];
	private Integer[] scannedItemIds = new Integer[rowCount];
	private String[] origins = new String[rowCount];
	private Integer[] Pieces = new Integer[rowCount];
	private String[] pincodes = new String[rowCount];
	private Double[] actualWeights = new Double[rowCount];
	private Integer[] pincodeIds = new Integer[rowCount];
	private String[] cnContents = new String[rowCount];
	private Integer[] cnContentIds = new Integer[rowCount];
	private Boolean[] ischeckeds = new Boolean[rowCount];
	private String[] paperWorks = new String[rowCount];
	private Integer[] paperWorkIds = new Integer[rowCount];
	private String[] insuredBys = new String[rowCount];
	private String[] insurancePolicyNos = new String[rowCount];
	private String[] insurances = new String[rowCount];
	private String[] remarksGrid = new String[rowCount];
	private Integer[] manifestEmbeddeIns = new Integer[rowCount];
	private Integer[] manifestMappedEmbeddeId = new Integer[rowCount];
	private Integer[] consgManifestedIds = new Integer[rowCount];

	private Integer operatingLevel;
	private int rowcount;
	private int consigTotal;
	private double consigTotalWt;
	private int totalComail;

	private Integer createdBy;
	private Integer updatedBy;

	private String bagLockNo;
	private String loginRegionCode;
	private String loginOfficeName;
	private String loginofficeCity;

	private Integer loginofficeCityId;

	public Integer getLoginofficeCityId() {
		return loginofficeCityId;
	}

	public void setLoginofficeCityId(Integer loginofficeCityId) {
		this.loginofficeCityId = loginofficeCityId;
	}

	/**
	 * @return the loginOfficeName
	 */
	public String getLoginOfficeName() {
		return loginOfficeName;
	}

	/**
	 * @param loginOfficeName
	 *            the loginOfficeName to set
	 */
	public void setLoginOfficeName(String loginOfficeName) {
		this.loginOfficeName = loginOfficeName;
	}

	/**
	 * @return the loginofficeCity
	 */
	public String getLoginofficeCity() {
		return loginofficeCity;
	}

	/**
	 * @param loginofficeCity
	 *            the loginofficeCity to set
	 */
	public void setLoginofficeCity(String loginofficeCity) {
		this.loginofficeCity = loginofficeCity;
	}

	/**
	 * @return the loginRegionCode
	 */
	public String getLoginRegionCode() {
		return loginRegionCode;
	}

	/**
	 * @param loginRegionCode
	 *            the loginRegionCode to set
	 */
	public void setLoginRegionCode(String loginRegionCode) {
		this.loginRegionCode = loginRegionCode;
	}

	/**
	 * @return the bagLockNo
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * @param bagLockNo
	 *            the bagLockNo to set
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	/**
	 * @return the loginRegionId
	 */
	public Integer getLoginRegionId() {
		return loginRegionId;
	}

	/**
	 * @param loginRegionId
	 *            the loginRegionId to set
	 */
	public void setLoginRegionId(Integer loginRegionId) {
		this.loginRegionId = loginRegionId;
	}

	/**
	 * @return the consgManifestedIds
	 */
	public Integer[] getConsgManifestedIds() {
		return consgManifestedIds;
	}

	/**
	 * @param consgManifestedIds
	 *            the consgManifestedIds to set
	 */
	public void setConsgManifestedIds(Integer[] consgManifestedIds) {
		this.consgManifestedIds = consgManifestedIds;
	}

	/**
	 * @return the manifestMappedEmbeddeId
	 */
	public Integer[] getManifestMappedEmbeddeId() {
		return manifestMappedEmbeddeId;
	}

	/**
	 * @param manifestMappedEmbeddeId
	 *            the manifestMappedEmbeddeId to set
	 */
	public void setManifestMappedEmbeddeId(Integer[] manifestMappedEmbeddeId) {
		this.manifestMappedEmbeddeId = manifestMappedEmbeddeId;
	}

	/**
	 * @return the operatingLevel
	 */
	public Integer getOperatingLevel() {
		return operatingLevel;
	}

	/**
	 * @param operatingLevel
	 *            the operatingLevel to set
	 */
	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}

	/**
	 * @return the misrouteDate
	 */
	public String getMisrouteDate() {
		return misrouteDate;
	}

	/**
	 * @param misrouteDate
	 *            the misrouteDate to set
	 */
	public void setMisrouteDate(String misrouteDate) {
		this.misrouteDate = misrouteDate;
	}

	/**
	 * @return the misrouteNo
	 */
	public String getMisrouteNo() {
		return misrouteNo;
	}

	/**
	 * @param misrouteNo
	 *            the misrouteNo to set
	 */
	public void setMisrouteNo(String misrouteNo) {
		this.misrouteNo = misrouteNo;
	}

	/**
	 * @return the misrouteType
	 */
	public String getMisrouteType() {
		return misrouteType;
	}

	/**
	 * @param misrouteType
	 *            the misrouteType to set
	 */
	public void setMisrouteType(String misrouteType) {
		this.misrouteType = misrouteType;
	}

	/**
	 * @return the officeCode
	 */
	public Integer getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode
	 *            the officeCode to set
	 */
	public void setOfficeCode(Integer officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}

	/**
	 * @param officeType
	 *            the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName
	 *            the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the misrouteManifestStatus
	 */
	public String getMisrouteManifestStatus() {
		return misrouteManifestStatus;
	}

	/**
	 * @param misrouteManifestStatus
	 *            the misrouteManifestStatus to set
	 */
	public void setMisrouteManifestStatus(String misrouteManifestStatus) {
		this.misrouteManifestStatus = misrouteManifestStatus;
	}

	/**
	 * @return the consgType
	 */
	public String getConsgType() {
		return consgType;
	}

	/**
	 * @param consgType
	 *            the consgType to set
	 */
	public void setConsgType(String consgType) {
		this.consgType = consgType;
	}

	/**
	 * @return the destinationRegionId
	 */
	public Integer getDestinationRegionId() {
		return destinationRegionId;
	}

	/**
	 * @param destinationRegionId
	 *            the destinationRegionId to set
	 */
	public void setDestinationRegionId(Integer destinationRegionId) {
		this.destinationRegionId = destinationRegionId;
	}

	/**
	 * @return the destinationRegionList
	 */
	public List<RegionTO> getDestinationRegionList() {
		return destinationRegionList;
	}

	/**
	 * @param destinationRegionList
	 *            the destinationRegionList to set
	 */
	public void setDestinationRegionList(List<RegionTO> destinationRegionList) {
		this.destinationRegionList = destinationRegionList;
	}

	/**
	 * @return the destinationStationId
	 */
	public Integer getDestinationStationId() {
		return destinationStationId;
	}

	/**
	 * @param destinationStationId
	 *            the destinationStationId to set
	 */
	public void setDestinationStationId(Integer destinationStationId) {
		this.destinationStationId = destinationStationId;
	}

	/**
	 * @return the manifestDirection
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * @param manifestDirection
	 *            the manifestDirection to set
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
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
	 * @return the destinationStationList
	 */
	public List<OfficeTO> getDestinationStationList() {
		return destinationStationList;
	}

	/**
	 * @param destinationStationList
	 *            the destinationStationList to set
	 */
	public void setDestinationStationList(List<OfficeTO> destinationStationList) {
		this.destinationStationList = destinationStationList;
	}

	/**
	 * @return the misrouteDetailsTO
	 */
	public List<MisrouteDetailsTO> getMisrouteDetailsTO() {
		return misrouteDetailsTO;
	}

	/**
	 * @param misrouteDetailsTO
	 *            the misrouteDetailsTO to set
	 */
	public void setMisrouteDetailsTO(List<MisrouteDetailsTO> misrouteDetailsTO) {
		this.misrouteDetailsTO = misrouteDetailsTO;
	}

	/**
	 * @return the scannedItemNos
	 */
	public String[] getScannedItemNos() {
		return scannedItemNos;
	}

	/**
	 * @param scannedItemNos
	 *            the scannedItemNos to set
	 */
	public void setScannedItemNos(String[] scannedItemNos) {
		this.scannedItemNos = scannedItemNos;
	}

	/**
	 * @return the scannedItemIds
	 */
	public Integer[] getScannedItemIds() {
		return scannedItemIds;
	}

	/**
	 * @param scannedItemIds
	 *            the scannedItemIds to set
	 */
	public void setScannedItemIds(Integer[] scannedItemIds) {
		this.scannedItemIds = scannedItemIds;
	}

	/**
	 * @return the origins
	 */
	public String[] getOrigins() {
		return origins;
	}

	/**
	 * @param origins
	 *            the origins to set
	 */
	public void setOrigins(String[] origins) {
		this.origins = origins;
	}

	/**
	 * @return the pieces
	 */
	public Integer[] getPieces() {
		return Pieces;
	}

	/**
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(Integer[] pieces) {
		Pieces = pieces;
	}

	/**
	 * @return the pincodes
	 */
	public String[] getPincodes() {
		return pincodes;
	}

	/**
	 * @param pincodes
	 *            the pincodes to set
	 */
	public void setPincodes(String[] pincodes) {
		this.pincodes = pincodes;
	}

	/**
	 * @return the cnContents
	 */
	public String[] getCnContents() {
		return cnContents;
	}

	/**
	 * @param cnContents
	 *            the cnContents to set
	 */
	public void setCnContents(String[] cnContents) {
		this.cnContents = cnContents;
	}

	/**
	 * @return the cnContentIds
	 */
	public Integer[] getCnContentIds() {
		return cnContentIds;
	}

	/**
	 * @param cnContentIds
	 *            the cnContentIds to set
	 */
	public void setCnContentIds(Integer[] cnContentIds) {
		this.cnContentIds = cnContentIds;
	}

	/**
	 * @return the paperWorks
	 */
	public String[] getPaperWorks() {
		return paperWorks;
	}

	/**
	 * @param paperWorks
	 *            the paperWorks to set
	 */
	public void setPaperWorks(String[] paperWorks) {
		this.paperWorks = paperWorks;
	}

	/**
	 * @return the paperWorkIds
	 */
	public Integer[] getPaperWorkIds() {
		return paperWorkIds;
	}

	/**
	 * @param paperWorkIds
	 *            the paperWorkIds to set
	 */
	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	/**
	 * @return the insuredBys
	 */
	public String[] getInsuredBys() {
		return insuredBys;
	}

	/**
	 * @param insuredBys
	 *            the insuredBys to set
	 */
	public void setInsuredBys(String[] insuredBys) {
		this.insuredBys = insuredBys;
	}

	/**
	 * @return the insurancePolicyNos
	 */
	public String[] getInsurancePolicyNos() {
		return insurancePolicyNos;
	}

	/**
	 * @param insurancePolicyNos
	 *            the insurancePolicyNos to set
	 */
	public void setInsurancePolicyNos(String[] insurancePolicyNos) {
		this.insurancePolicyNos = insurancePolicyNos;
	}

	/**
	 * @param actualWeights
	 *            the actualWeights to set
	 */
	public void setActualWeights(Double[] actualWeights) {
		this.actualWeights = actualWeights;
	}

	/**
	 * @return the actualWeights
	 */
	public Double[] getActualWeights() {
		return actualWeights;
	}

	/**
	 * @return the pincodeIds
	 */
	public Integer[] getPincodeIds() {
		return pincodeIds;
	}

	/**
	 * @param pincodeIds
	 *            the pincodeIds to set
	 */
	public void setPincodeIds(Integer[] pincodeIds) {
		this.pincodeIds = pincodeIds;
	}

	/**
	 * @return the remarksGrid
	 */
	public String[] getRemarksGrid() {
		return remarksGrid;
	}

	/**
	 * @param remarksGrid
	 *            the remarksGrid to set
	 */
	public void setRemarksGrid(String[] remarksGrid) {
		this.remarksGrid = remarksGrid;
	}

	/**
	 * @return the processCode
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * @param processCode
	 *            the processCode to set
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * @return the misrouteId
	 */
	public Integer getMisrouteId() {
		return misrouteId;
	}

	/**
	 * @param misrouteId
	 *            the misrouteId to set
	 */
	public void setMisrouteId(Integer misrouteId) {
		this.misrouteId = misrouteId;
	}

	/**
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO
	 *            the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	/**
	 * @return the processNo
	 */
	public String getProcessNo() {
		return processNo;
	}

	/**
	 * @param processNo
	 *            the processNo to set
	 */
	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	/**
	 * @return the insurances
	 */
	public String[] getInsurances() {
		return insurances;
	}

	/**
	 * @param insurances
	 *            the insurances to set
	 */
	public void setInsurances(String[] insurances) {
		this.insurances = insurances;
	}

	/**
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}

	/**
	 * @param pincodeTO
	 *            the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}

	/**
	 * @return the manifestEmbeddeIns
	 */
	public Integer[] getManifestEmbeddeIns() {
		return manifestEmbeddeIns;
	}

	/**
	 * @param manifestEmbeddeIns
	 *            the manifestEmbeddeIns to set
	 */
	public void setManifestEmbeddeIns(Integer[] manifestEmbeddeIns) {
		this.manifestEmbeddeIns = manifestEmbeddeIns;
	}

	/**
	 * @param ischeckeds
	 *            the ischeckeds to set
	 */
	public void setIscheckeds(Boolean[] ischeckeds) {
		this.ischeckeds = ischeckeds;
	}

	/**
	 * @return the ischeckeds
	 */
	public Boolean[] getIscheckeds() {
		return ischeckeds;
	}

	/**
	 * @return the destinationStationName
	 */
	public String getDestinationStationName() {
		return destinationStationName;
	}

	/**
	 * @param destinationStationName
	 *            the destinationStationName to set
	 */
	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}

	/**
	 * @return the destinationRegionName
	 */
	public String getDestinationRegionName() {
		return destinationRegionName;
	}

	/**
	 * @param destinationRegionName
	 *            the destinationRegionName to set
	 */
	public void setDestinationRegionName(String destinationRegionName) {
		this.destinationRegionName = destinationRegionName;
	}

	/**
	 * @return the manifestProcessTo
	 */
	public ManifestProcessTO getManifestProcessTo() {
		return manifestProcessTo;
	}

	/**
	 * @param manifestProcessTo
	 *            the manifestProcessTo to set
	 */
	public void setManifestProcessTo(ManifestProcessTO manifestProcessTo) {
		this.manifestProcessTo = manifestProcessTo;
	}

	/**
	 * @return the manifestType
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * @param manifestType
	 *            the manifestType to set
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}

	/**
	 * @return the destinationOfficeName
	 */
	public String getDestinationOfficeName() {
		return destinationOfficeName;
	}

	/**
	 * @param destinationOfficeName
	 *            the destinationOfficeName to set
	 */
	public void setDestinationOfficeName(String destinationOfficeName) {
		this.destinationOfficeName = destinationOfficeName;
	}

	/**
	 * @return the loginCityCode
	 */
	public String getLoginCityCode() {
		return loginCityCode;
	}

	/**
	 * @param loginCityCode
	 *            the loginCityCode to set
	 */
	public void setLoginCityCode(String loginCityCode) {
		this.loginCityCode = loginCityCode;
	}

	public String getOfficeCodeProcess() {
		return officeCodeProcess;
	}

	public void setOfficeCodeProcess(String officeCodeProcess) {
		this.officeCodeProcess = officeCodeProcess;
	}

	public Integer getDestinationCityId() {
		return destinationCityId;
	}

	public void setDestinationCityId(Integer destinationCityId) {
		this.destinationCityId = destinationCityId;
	}

	public String getDestinationCityName() {
		return destinationCityName;
	}

	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}

	public List<CityTO> getDestinationCityList() {
		return destinationCityList;
	}

	public void setDestinationCityList(List<CityTO> destinationCityList) {
		this.destinationCityList = destinationCityList;
	}

	public Integer[] getPositions() {
		return positions;
	}

	public void setPositions(Integer[] positions) {
		this.positions = positions;
	}

	public String getDestinationOfficeCode() {
		return destinationOfficeCode;
	}

	public void setDestinationOfficeCode(String destinationOfficeCode) {
		this.destinationOfficeCode = destinationOfficeCode;
	}

	public int getRowcount() {
		return rowcount;
	}

	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	public int getConsigTotal() {
		return consigTotal;
	}

	public void setConsigTotal(int consigTotal) {
		this.consigTotal = consigTotal;
	}

	public double getConsigTotalWt() {
		return consigTotalWt;
	}

	public void setConsigTotalWt(double consigTotalWt) {
		this.consigTotalWt = consigTotalWt;
	}

	public int getTotalComail() {
		return totalComail;
	}

	public void setTotalComail(int totalComail) {
		this.totalComail = totalComail;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
