/**
 * 
 */
package com.ff.web.drs.manualdrs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.geography.CityTO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.CodLcDrsDetailsTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryNavigatorTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.ManualDrsTO;
import com.ff.to.drs.pending.PendingDrsDetailsTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.manualdrs.dao.ManualDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class ManualDrsServiceImpl implements ManualDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManualDrsServiceImpl.class);
	
	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;
	
	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;
	
	private ManualDrsDAO manualDrsDAO;

	/**
	 * @return the deliveryCommonService
	 */
	public DeliveryCommonService getDeliveryCommonService() {
		return deliveryCommonService;
	}

	/**
	 * @return the deliveryCommonDAO
	 */
	public DeliveryCommonDAO getDeliveryCommonDAO() {
		return deliveryCommonDAO;
	}

	/**
	 * @return the manualDrsDAO
	 */
	public ManualDrsDAO getManualDrsDAO() {
		return manualDrsDAO;
	}

	/**
	 * @param manualDrsDAO the manualDrsDAO to set
	 */
	public void setManualDrsDAO(ManualDrsDAO manualDrsDAO) {
		this.manualDrsDAO = manualDrsDAO;
	}

	/**
	 * @param deliveryCommonService the deliveryCommonService to set
	 */
	public void setDeliveryCommonService(DeliveryCommonService deliveryCommonService) {
		this.deliveryCommonService = deliveryCommonService;
	}

	/**
	 * @param deliveryCommonDAO the deliveryCommonDAO to set
	 */
	public void setDeliveryCommonDAO(DeliveryCommonDAO deliveryCommonDAO) {
		this.deliveryCommonDAO = deliveryCommonDAO;
	}
	@Override
	public PendingDrsHeaderTO searchPendingConsignments(PendingDrsHeaderTO inputTo)throws CGSystemException,CGBusinessException{
		LOGGER.debug("ManualDrsServiceImpl::searchPendingConsignments::START");
		Integer maxallowedCn=null;
		if(StringUtil.isStringEmpty(inputTo.getDrsNumber())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
		}
		String drsScreenCode=inputTo.getDrsScreenCode();
		String status=deliveryCommonService.getDrsStatusByDrsNumber(inputTo);
		if(!StringUtil.isStringEmpty(inputTo.getManifestDrsType()) && inputTo.getManifestDrsType().equalsIgnoreCase(DrsConstants.MANIFEST_DRS_TYPE_MANIFEST)){
			maxallowedCn=manualDrsDAO.getManifestConsignemtnCountForDrs(inputTo);
		}
		if(!StringUtil.isStringEmpty(status)){
			getDetailsFromDrsForPending(inputTo, drsScreenCode);
			if(inputTo!=null){
				inputTo.setCanCreateRows(DrsConstants.FLAG_NO);
				if(!status.equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED)){
					int createdChild=inputTo.getDrsDetailsTo().size();
					if(!StringUtil.isEmptyInteger(maxallowedCn)){
						if(maxallowedCn-createdChild>0){
							inputTo.setCanCreateRows(DrsConstants.FLAG_YES);
							inputTo.setMaxAllowedRows(maxallowedCn);
							inputTo.setAddedRowCount(createdChild);
						}
					}
				}
			}
		}else{
			if(!StringUtil.isStringEmpty(inputTo.getManifestDrsType())&& inputTo.getManifestDrsType().equalsIgnoreCase(DrsConstants.MANIFEST_DRS_TYPE_MANIFEST)){
				ManifestDO manifestDo=manualDrsDAO.getManifestDetailsByManifestNumber((AbstractDeliveryTO)inputTo);
				if(manifestDo!=null){
					
					prepareThirdPartyManifestForDRSHeader((AbstractDeliveryTO)inputTo,
							manifestDo);
					inputTo.setCanCreateRows(DrsConstants.FLAG_YES);
					
				}else{
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_TMF_NOT_EXIST);
				}
			}else{
				/** If DRS Does not Exist in the DRS table then this block will executes*/
				inputTo.setCanCreateRows(DrsConstants.FLAG_YES);
				if(StringUtil.isEmptyLong(inputTo.getDeliveryId())){
					inputTo.setDrsFor("");
				}
			}
		}
		if(!StringUtil.isEmptyInteger(maxallowedCn)){
			inputTo.setMaxAllowedRows(maxallowedCn);
		}
		LOGGER.debug("ManualDrsServiceImpl::searchPendingConsignments::END");
		
		return inputTo;
	}

	/**
	 * @param inputTo
	 * @param drsScreenCode
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getDetailsFromDrsForPending(PendingDrsHeaderTO inputTo,
			String drsScreenCode) throws CGBusinessException, CGSystemException {
		DeliveryTO outTo;
		outTo= deliveryCommonService.getDrsDetailsForManualDrs(inputTo);
		Integer noOfRows=null;
		if(outTo!=null){
			
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(inputTo,outTo);
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| (outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				/** check whether DRS details allowed to update*/
				LOGGER.error("ManualDrsServiceImpl ::searchPendingConsignments ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
			}
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				noOfRows=outTo.getDtlsTOList().size();
				inputTo.setMaxAllowedRows(noOfRows);
				List<PendingDrsDetailsTO> childList=new ArrayList<>(noOfRows);
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					if(!StringUtil.isNull(dtlsTo.getReasonTO())){
						PendingDrsDetailsTO  detailsTo= new PendingDrsDetailsTO();
						try {
							PropertyUtils.copyProperties(detailsTo, dtlsTo);
						} catch (Exception e) {
							LOGGER.error("ManualDrsServiceImpl::searchPendingConsignments::Exception",e);
							throw new CGSystemException(e);
						}
						/** set City Details*/
						DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
						childList.add(detailsTo);
					}
				}
				inputTo.setDrsDetailsTo(childList);
				//DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_PENDING_DONE_TMF);
			}
			inputTo.setDrsScreenCode(drsScreenCode);
			DeliveryNavigatorTO navigationTO=deliveryCommonService.getDrsNavigationDetails(inputTo.getDrsNumber(), inputTo.getDrsScreenCode());
			if(!StringUtil.isNull(navigationTO)){
				String url=navigationTO.getNavigateToUrl();
				if(!StringUtil.isStringEmpty(url)){
					url=url+inputTo.getDrsNumber();
					inputTo.setUpdateDeliveredUrl(url);
				}
			}
		}else{
			DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_NUMBER_ALREADY_IN_USE);
		}
	}
	
	private void getDetailsFromDrsForUpdate(ManualDrsTO inputTo) throws CGBusinessException, CGSystemException {
		DeliveryTO outTo;
		outTo= deliveryCommonService.getDrsDetailsForManualDrs(inputTo);
		Integer noOfRows=null;
		List<String> cnManifestedList=null;
		if(outTo!=null){
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(inputTo,outTo);
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| (outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				/** check whether DRS details allowed to update*/
				LOGGER.error("ManualDrsServiceImpl ::searchPendingConsignments ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
				inputTo.setCanCreateRows(DrsConstants.FLAG_NO);
			}else{
				inputTo.setCanCreateRows(DrsConstants.FLAG_YES);
			}
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				noOfRows=outTo.getDtlsTOList().size();
				cnManifestedList= new ArrayList<>(noOfRows);
				inputTo.setMaxAllowedRows(noOfRows+1);
				List<CodLcDrsDetailsTO> childList=new ArrayList<>(noOfRows);
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					CodLcDrsDetailsTO  detailsTo= new CodLcDrsDetailsTO();
					CGObjectConverter.copyTO2TO(dtlsTo, detailsTo);
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						detailsTo.setConsgnmentId(dtlsTo.getConsignmentTO().getConsgId());
					}
					cnManifestedList.add(dtlsTo.getConsignmentNumber());
					
					if(!StringUtil.isNull(dtlsTo.getDeliveryDate())){
						//detailsTo.setDeliveryTimeStr(DateUtil.getTimeFromDate(dtlsTo.getDeliveryDate()));
						detailsTo.setDeliveryTimeInHHStr(DateUtil.extractHourInHHFormatFromDate(dtlsTo.getDeliveryDate()));
						detailsTo.setDeliveryTimeInMMStr(DateUtil.extractMinutesInMMFormatFromDate(dtlsTo.getDeliveryDate()));
					}
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					DrsConverterUtil.prepareGridToFromDeliveryTO4PaymentDtls(dtlsTo, detailsTo);
					DrsConverterUtil.prepareGridToFromDeliveryTO4Charges(dtlsTo, detailsTo);
					DrsConverterUtil.prepareTOWithRelationAndIdDtls(dtlsTo, detailsTo);
					if(dtlsTo.getReasonTO()!=null){
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
					}
					childList.add(detailsTo);
				}
				inputTo.setCodLcDrsDetailsToList(childList);
			}
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())||(!StringUtil.isStringEmpty(outTo.getDrsStatus()) && !outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				prepareThirdpartyManifestGridDetails(inputTo, outTo,cnManifestedList, null);
			}
			if(!CGCollectionUtils.isEmpty(inputTo.getCodLcDrsDetailsToList())){
				Collections.sort(inputTo.getCodLcDrsDetailsToList());//Sorting records by RowNumber(Sr No: in the screen) 
				inputTo.setCodLcDrsDetailsToList(inputTo.getCodLcDrsDetailsToList());
			}
		}
	}

	private void prepareThirdpartyManifestGridDetails(ManualDrsTO inputTo,
			DeliveryTO outTo, List<String> cnManifestedList, ManifestDO manifestDo)
			throws CGSystemException, CGBusinessException {
		List<CodLcDrsDetailsTO> newCnList=null;
		int maxAllowedRows=0;
		int pendingCnSize=0;
		int deliveredCnSize=0;
		if( (outTo ==null )|| (outTo!=null && !outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED) && !StringUtil.isStringEmpty(inputTo.getManifestDrsType())&& inputTo.getManifestDrsType().equalsIgnoreCase(DrsConstants.MANIFEST_DRS_TYPE_MANIFEST))){
			if(manifestDo==null){
				manifestDo=manualDrsDAO.getManifestDetailsByManifestNumber((AbstractDeliveryTO)inputTo);
			}
			if(manifestDo!=null){
				inputTo.setCanCreateRows(DrsConstants.FLAG_NO);
				boolean isCNPending=false;
				int rowNumber=1;
				if(!CGCollectionUtils.isEmpty(cnManifestedList)){
					isCNPending=true;
					rowNumber=cnManifestedList.size()+1;
					pendingCnSize=cnManifestedList.size();
				}
				
				if(!CGCollectionUtils.isEmpty(manifestDo.getConsignments())){
					 maxAllowedRows=manifestDo.getConsignments().size();
					newCnList=new ArrayList<>(maxAllowedRows);
					
					for(ConsignmentDO consg:manifestDo.getConsignments()){
						
						if(!CGCollectionUtils.isEmpty(consg.getChildCNs())){
							maxAllowedRows=maxAllowedRows+consg.getChildCNs().size()-1;
							//process Child Consignemtns
							for(ChildConsignmentDO childCN:consg.getChildCNs()){
								String childCn=childCN.getChildConsgNumber();
								if(isCNPending && cnManifestedList.contains(childCn)){
									continue;
								}
								
								String status=deliveryCommonService.getConsignmentStatusFromDelivery(childCn);
								LOGGER.trace("ManualDrsServiceImpl ::validateConsignmentDetails :: getConsignmentStatusFromDelivery :For the Consg ["+consg.getConsgNo()+"]Status["+status+"]");
								if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
									++deliveredCnSize;
									continue;
								}
								CodLcDrsDetailsTO  detailsTo= new CodLcDrsDetailsTO();
								detailsTo.setConsignmentNumber(childCn);
								detailsTo.setConsgnmentId(consg.getConsgId());
								detailsTo.setParentChildCnType(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE);
								detailsTo.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_OFFICE);
								detailsTo.setCompanySealSign(UniversalDeliveryContants.COMPANY_SEAL_AND_SIGN);
								DrsConverterUtil.prepareGridToFromConsgDO4Charges(consg,detailsTo);
								CityTO cityTo=deliveryCommonService.getCitiesByOffices(consg.getOrgOffId());
								if(!StringUtil.isNull(cityTo)){
									detailsTo.setOriginCityId(cityTo.getCityId());
									detailsTo.setOriginCityCode(cityTo.getCityCode());
									detailsTo.setOriginCityName(cityTo.getCityName());
								}else{
									ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_ORIGIN_OFFICE_NOT_EXIST,new String[]{consg.getConsgNo()});
								}
								
								detailsTo.setRowNumber(rowNumber);
								rowNumber++;
								newCnList.add(detailsTo);
							}
						}else{
							String consgNumber=consg.getConsgNo();
							if(isCNPending && cnManifestedList.contains(consgNumber)){
								continue;
							}
							String status=deliveryCommonService.getConsignmentStatusFromDelivery(consg.getConsgNo());
							if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
								++deliveredCnSize;
								continue;
							}

						

							CodLcDrsDetailsTO  detailsTo= new CodLcDrsDetailsTO();
							detailsTo.setConsignmentNumber(consgNumber);
							detailsTo.setConsgnmentId(consg.getConsgId());

							detailsTo.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
							detailsTo.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_OFFICE);
							detailsTo.setCompanySealSign(UniversalDeliveryContants.COMPANY_SEAL_AND_SIGN);
							DrsConverterUtil.prepareGridToFromConsgDO4Charges(consg,detailsTo);
							CityTO cityTo=deliveryCommonService.getCitiesByOffices(consg.getOrgOffId());
							if(!StringUtil.isNull(cityTo)){
								detailsTo.setOriginCityId(cityTo.getCityId());
								detailsTo.setOriginCityCode(cityTo.getCityCode());
								detailsTo.setOriginCityName(cityTo.getCityName());
							}else{
								ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_ORIGIN_OFFICE_NOT_EXIST,new String[]{consgNumber});
							}

							detailsTo.setRowNumber(rowNumber);
							rowNumber++;
							newCnList.add(detailsTo);
						}
					}
					inputTo.setMaxAllowedRows(maxAllowedRows);
				}else{
					//throw CGBusiness Exception
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR, new String[]{DrsCommonConstants.CONSIGNMENT," For given Manifest No:"+inputTo.getDrsNumber()});
				}
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_TMF_NOT_EXIST);
			}
		}
		if(!CGCollectionUtils.isEmpty(newCnList)){
			if(!CGCollectionUtils.isEmpty(inputTo.getCodLcDrsDetailsToList())){
				inputTo.getCodLcDrsDetailsToList().addAll(newCnList);
			}else{
				inputTo.setCodLcDrsDetailsToList(newCnList);
			}
		}
		if(!StringUtil.isEmptyInteger(maxAllowedRows) && maxAllowedRows==deliveredCnSize+pendingCnSize){
			LOGGER.error("UpdateRtoDrsServiceImpl ::findDrsByDrsNumber ::Business Exception (DRS already been Closed)");
			DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{DrsConstants.DRS_STATUS_CLOSED});
		}
		
	}
	
	/**
	 * 
	 * @param drsInputTo
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Boolean saveUndeliveredDrsConsg(PendingDrsHeaderTO drsInputTo)throws CGSystemException,CGBusinessException{
		boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("ManualDrsServiceImpl :: saveUndeliveredDrsConsg ::START");
		
		if(drsInputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
				/** Header DO Preparation*/
				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);
				
				if(!StringUtil.isStringEmpty(drsInputTo.getFsOutTimeDateStr())){
					deliveryDO.setFsOutTime(DateUtil.combineDateWithTimeHHMM(drsInputTo.getFsOutTimeDateStr(), drsInputTo.getFsOutTimeHHStr(), drsInputTo.getFsOutTimeMMStr()));
				}
				if(!StringUtil.isStringEmpty(drsInputTo.getFsInTimeDateStr())){
					deliveryDO.setFsInTime(DateUtil.combineDateWithTimeHHMM(drsInputTo.getFsInTimeDateStr(), drsInputTo.getFsInTimeHHStr(),drsInputTo.getFsInTimeMMStr()));
				}
				if(!StringUtil.isStringEmpty(drsInputTo.getDrsNumber())){
				deliveryDO.setDrsNumber(drsInputTo.getDrsNumber().toUpperCase());
				drsInputTo.setDrsNumber(drsInputTo.getDrsNumber().toUpperCase());
				}else{
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
				}
				//############################### Grid Preparation START##############
				/** Child DO Preparation*/
				int gridSize=0;
				gridSize=DrsConverterUtil.getGridSize(drsInputTo);
				int alreadyAddedRowCount=0;
				
				Set<DeliveryDetailsDO> deliveryDetails= new HashSet<>(gridSize);
				for(int counter=0;counter<gridSize;counter++){
					if(StringUtil.isEmpty(drsInputTo.getRowAlreadyAddedRow()) || StringUtil.isStringEmpty(drsInputTo.getRowAlreadyAddedRow()[counter]) || !drsInputTo.getRowAlreadyAddedRow()[counter].equalsIgnoreCase(FrameworkConstants.ENUM_YES)){
					if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
						DeliveryDetailsDO detailDO =  new DeliveryDetailsDO();
						detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_PENDING);
						detailDO.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_NO_DELIVERY);
						/** Inspect & proceed Consignment Number */
						DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
								detailDO);
						/** Inspect & set Consignment id */
						DrsConverterUtil.setConsgDO2DeliveryDO((AbstractDeliveryTO)drsInputTo, counter, detailDO);
						DrsConverterUtil.setOriginCityDetails(drsInputTo, counter, detailDO);
						DrsConverterUtil.setConsgParentChildType(drsInputTo, counter, detailDO);
						deliveryCommonService.validateConsignmentFromDeliveryForSave(detailDO.getConsignmentNumber(), UniversalDeliveryContants.DRS_CONSIGMENT);

						detailDO.setRowNumber(counter+1);
						if(!StringUtil.isStringEmpty(drsInputTo.getRowMissedCardNumber()[counter])){
							detailDO.setMissedCardNumber(drsInputTo.getRowMissedCardNumber()[counter]);
						}
						if(!StringUtil.isStringEmpty(drsInputTo.getRowRemarks()[counter])){
							detailDO.setRemarks(drsInputTo.getRowRemarks()[counter]);
						}
						if(!StringUtil.isEmptyInteger(drsInputTo.getRowPendingReasonId()[counter])){
							ReasonDO reasonDO= new ReasonDO();
							reasonDO.setReasonId(drsInputTo.getRowPendingReasonId()[counter]);
							detailDO.setReasonDO(reasonDO);
						}

						detailDO.setDeliveryDO(deliveryDO);
						deliveryDetails.add(detailDO);
					}
					}else if(!StringUtil.isEmpty(drsInputTo.getRowAlreadyAddedRow()) && !StringUtil.isStringEmpty(drsInputTo.getRowAlreadyAddedRow()[counter]) && drsInputTo.getRowAlreadyAddedRow()[counter].equalsIgnoreCase(FrameworkConstants.ENUM_YES)){
						++alreadyAddedRowCount;
					}
				}
				deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				
				int userEnterdRows=deliveryDetails.size()+alreadyAddedRowCount;
				
				int maxAllowedRows=drsInputTo.getMaxAllowedRows();
				if(!StringUtil.isEmptyInteger(userEnterdRows) && !StringUtil.isEmptyInteger(maxAllowedRows) && maxAllowedRows == userEnterdRows){
					deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				}else{
					deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_UPDATED);
				}
				
				deliveryDO.setDrsScreenCode(DrsConstants.MANUAL_TMF_DRS_SCREEN_CODE);
				drsInputTo.setUpdateDeliveredUrl(DrsUtil.prepareDrsNavigatorForManualDrs(deliveryDO));
				LOGGER.trace("ManualDrsServiceImpl :: savePrepareDrs ::URL"+drsInputTo.getUpdateDeliveredUrl());
				//############################### Grid Preparation END##############
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
			}
			if(StringUtil.isEmptyLong(deliveryDO.getDeliveryId())){
			result=deliveryCommonDAO.savePrepareDrs(deliveryDO);
			}else{
				result=deliveryCommonDAO.updateManualDrs(deliveryDO);
			}
		}
		drsInputTo.setDrsStatus(deliveryDO.getDrsStatus());
		LOGGER.debug("ManualDrsServiceImpl :: savePrepareDrs ::END:::Status"+result);
		return result;
	}
	/**
	 * saveDeliveredDrsConsg
	 * @param drsInputTo
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Boolean saveDeliveredDrsConsg(ManualDrsTO drsInputTo)throws CGSystemException,CGBusinessException{
		boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("ManualDrsServiceImpl :: saveDeliveredDrsConsg ::START");
		
		if(drsInputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
				/** Header DO Preparation*/
				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);
				
				if(StringUtil.isStringEmpty(drsInputTo.getDrsNumber())){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CAN_NOT_EMPTY,new String[]{DrsCommonConstants.DRS});
				}
				deliveryDO.setDrsNumber(drsInputTo.getDrsNumber());
				deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				drsInputTo.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				//############################### Grid Preparation START##############
				/** Child DO Preparation*/
				int gridSize=0;
				gridSize=DrsConverterUtil.getGridSize(drsInputTo);
				
				Set<DeliveryDetailsDO> deliveryDetails= new HashSet<>(gridSize);
				for(int counter=0;counter<gridSize;counter++){
					if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
						
						if(!StringUtil.isEmptyInteger(drsInputTo.getRowPendingReasonId()[counter])){
							/** since it's undelivered consg ,hence ignore that*/
							continue;
						}
							DeliveryDetailsDO detailDO =  new DeliveryDetailsDO();
							if(!StringUtil.isEmptyLong(drsInputTo.getRowDeliveryDetailId()[counter])){
								detailDO.setDeliveryDetailId(drsInputTo.getRowDeliveryDetailId()[counter]);
							}
							DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
									detailDO);
							/** Inspect & set Consignment id */
							DrsConverterUtil.setConsgDO2DeliveryDO((AbstractDeliveryTO)drsInputTo, counter, detailDO);
							deliveryCommonService.validateConsignmentFromDeliveryForSave(detailDO.getConsignmentNumber(), UniversalDeliveryContants.DRS_CONSIGMENT);
							DrsConverterUtil.prepareDeliveryDtlsWithDlvTime(drsInputTo, counter,
									detailDO);
							
							DrsConverterUtil.prepareDeliveryDtlsWithContactDtls(drsInputTo,
									counter, detailDO);
							DrsConverterUtil.prepareDeliveryDtlsWithCompanySealSign(drsInputTo,
									counter, detailDO);
							detailDO.setRowNumber(counter+1);
							detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
							detailDO.setDeliveryType(drsInputTo.getRowDeliveryType()[counter]);
							DrsConverterUtil.setAttemptNumberDetailsToDeliveryDomain(drsInputTo,
									counter, detailDO);
							/** Inspect & proceed Consignment Number */
							/** set  City  details */
							DrsConverterUtil.setOriginCityDetails((AbstractDeliveryTO)drsInputTo, counter, detailDO);
							/** set  whether  parent/child cn*/
							DrsConverterUtil.setConsgParentChildType((AbstractDeliveryTO)drsInputTo, counter, detailDO);

							DrsConverterUtil.setCodLcPayCharges(drsInputTo, counter, detailDO);
							DrsConverterUtil.prepareDeliveryDtlsWithPaymentDts(drsInputTo,
									counter, detailDO);
							DrsConverterUtil.prepareDeliveryDtlsWithRelationAndIdDtls(
									drsInputTo, counter, detailDO);
							
							detailDO.setDeliveryDO(deliveryDO);
							deliveryDetails.add(detailDO);
					}
				}
				deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				
				
				drsInputTo.setDrsStatus(deliveryDO.getDrsStatus());
				deliveryDO.setDrsScreenCode(deliveryDO.getDrsScreenCode());
				DrsUtil.prepareDrsNavigatorForManualDrs(deliveryDO);
				//############################### Grid Preparation END##############
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
			}
			if(StringUtil.isEmptyLong(deliveryDO.getDeliveryId())){
				result=deliveryCommonDAO.savePrepareDrs(deliveryDO);
			}else{
				result=manualDrsDAO.saveManualDrs(deliveryDO);
			}
		}
		
		LOGGER.debug("ManualDrsServiceImpl :: savePrepareDrs ::END:::Status"+result);
		return result;
	}

	

	

	@Override
	public DeliveryConsignmentTO validateConsignmentDetails(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		String consgNumber=deliveryTO.getConsignmentNumber();
		deliveryCommonService.validateStopDelivery(consgNumber);
		String consgType= deliveryCommonService.getConsignmentTypeByConsgNumber(consgNumber);
		DrsUtil.validateConsignmentType(deliveryTO, consgNumber, consgType);
		/** BR:validate Whether it's delivered/Prepared */
		String status=deliveryCommonService.getConsignmentStatusFromDelivery(consgNumber);
		LOGGER.trace("ManualDrsServiceImpl ::validateConsignmentDetails :: getConsignmentStatusFromDelivery :For the Consg ["+consgNumber+"]Status["+status+"]");
		if(!StringUtil.isStringEmpty(status)){
			if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
				/** Consignemtn already delivered ,throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_DELIVERED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}else if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)){
				/** Consignemtn already prepared , throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PREPARED_ALREADY_FOR_CONSG,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
		}
		
		//String consgStatus= deliveryCommonService.getConsignmentStatusFromConsg(consgNumber);
		//Commented above method but using more advanced method to get the status of the consignment.
		/**
		 * New Change Added to restrict RTOed consignments not to allow in general DRS.
		 * */
		String consgStatus=deliveryCommonService.validateForRTOed(consgNumber);
		if(!StringUtil.isStringEmpty(consgStatus) && consgStatus.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS)){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_NOT_RTOED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
		}
		if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_STOPDELV)){
			ExceptionUtil.prepareBusinessException(UdaanWebErrorConstants.CN_STOP_DELIVERY, new String[]{consgNumber});
		}
		
		if(!StringUtil.isStringEmpty(deliveryTO.getManifestDrsType())){
			switch(deliveryTO.getManifestDrsType()){
			case DrsConstants.MANIFEST_DRS_TYPE_DRS:
				/** BR:validate against CN/manifest*/
				dlvConsgTo=validateConsignmentForManualDrs(deliveryTO);
				break;
			case DrsConstants.MANIFEST_DRS_TYPE_MANIFEST:
				/** BR:validate against Third party manifest table */
				dlvConsgTo=validateConsignmentForThirdPartyManifestDrs(deliveryTO);
				break;
				
			}
			if(StringUtil.isNull(dlvConsgTo)){
				/** Consgnment not yet inmanifested ,throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}else{
				dlvConsgTo.setNoOfPcs(1);
				/** get attempt number for the consignment */
				Integer attempNum=deliveryCommonService.getAttemptCountForConsignment(deliveryTO.getConsignmentNumber());
				if(StringUtil.isEmptyInteger(attempNum)){
					attempNum=1;
				}
				dlvConsgTo.setAttemptNumber(attempNum);
			}
			
			if(StringUtil.isStringEmpty(dlvConsgTo.getConsignmentTypeCode()) || !dlvConsgTo.getConsignmentTypeCode().equalsIgnoreCase(deliveryTO.getConsignmentType())){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_INVALID_CONSG_TYPE,new String[]{consgNumber});
			}
			
		}else{
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_MANIFEST_DRS_TYPE_INVALID);
		}
		if(!StringUtil.isNull(dlvConsgTo)){
			dlvConsgTo.setOtherAmount(deliveryCommonService.getOctroiAmountForDrs(consgNumber));
			/*boolean isPaymentCaptured=deliveryCommonService.isPaymentCapturedForCn(dlvConsgTo.getConsgId());
			if(isPaymentCaptured){
				dlvConsgTo.setIsPaymentAlreadyCaptured(DrsConstants.FLAG_YES);
			}*/
		}
		
		return dlvConsgTo;
	}
	
	public DeliveryConsignmentTO validateConsignmentForManualDrs(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		String inConsgType=deliveryTO.getConsignmentType();
		switch(inConsgType){
		case CommonConstants.CONSIGNMENT_TYPE_DOCUMENT:
			dlvConsgTo	=validateConsignmentForDOXForManualDrs(deliveryTO);
			break;
		case CommonConstants.CONSIGNMENT_TYPE_PARCEL:
			dlvConsgTo	=validateConsignmentForPPXManualDRS(deliveryTO);
			break;

		}
		
		return dlvConsgTo;
		
	}
	
	
	
	
	public DeliveryConsignmentTO validateConsignmentForDOXForManualDrs(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO consgTO=null;
		Boolean statuFlag=Boolean.FALSE;
		boolean isComail=false;

		String consgNumber = deliveryTO.getConsignmentNumber();
		isComail = DrsUtil.isComailNumber(consgNumber);

		if(isComail){
			consgTO = validateForComailForManualDrs(deliveryTO);
		}else{
			/** it checks whether given consignment exist in the Consignment table or not*/
			statuFlag= deliveryCommonService.isConsignmentValid(consgNumber);

			if(!statuFlag){
				/** throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
			try{
				Date manifestDate = deliveryCommonService.getManifestedDateByConsgNumber(deliveryTO, true);

				if(StringUtil.isNull(manifestDate)){
					//Check in Booking table whether consignment is booked with logged in office
					consgTO = deliveryCommonService.getDoxConsgDtlsFromBooking(deliveryTO);
				}else{
					consgTO=deliveryCommonService.getInManifestedConsgnDtls(deliveryTO);

				}
			}catch(CGBusinessException exception){
				String manifestNumber=deliveryCommonService.getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForDOXManualDrs(deliveryTO);

				if(StringUtil.isStringEmpty(manifestNumber)){
					throw exception;
				}else{
					//get consg dtls
					consgTO = deliveryCommonService.getOutManifestedConsgnDtls(deliveryTO);
				}
			}
		}
		if(!StringUtil.isNull(consgTO)){
			consgTO.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
		}

		return consgTO;
	}
	public DeliveryConsignmentTO validateConsignmentForPPXManualDRS(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		boolean isParent=true;
		String consgNumber = deliveryTO.getConsignmentNumber();
		Boolean isConghasChlds=deliveryCommonService.isConsgHavingChildCns(consgNumber);

		if(isConghasChlds){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PARENT_CONSG_NUMBER,new String[]{consgNumber});
		}
		Boolean isChildcn=deliveryCommonService.isChildCn(consgNumber);

		if(isChildcn){
			isParent=false;
		}
	try{
		Date manifestDate = deliveryCommonService.getManifestedDateByConsgNumber(deliveryTO, isParent);

		if(StringUtil.isNull(manifestDate)){
			/** get information from Booking table*/
			if(isParent){
				dlvConsgTo = deliveryCommonService.getDoxConsgDtlsFromBooking(deliveryTO);
			}else{
				dlvConsgTo = deliveryCommonService.getPpxConsgDtlsFromBooking(deliveryTO);
			}

		}else{
			/** get information from manifest table*/
			if(isParent){
				dlvConsgTo= deliveryCommonService.getInManifestedConsgnDtls(deliveryTO);
			}else{
				dlvConsgTo= deliveryCommonService.getChildConsgDetailsForDRS(consgNumber);
			}

		}
	}catch(CGBusinessException exception){
			String mnfstNumber=null;
			if(isParent){
				//1. verify whether it's TPMF for PPX
				mnfstNumber=deliveryCommonService.getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForPpxManualDrs(deliveryTO);
				//2. if yes then get the Cn details
				if(!StringUtil.isStringEmpty(mnfstNumber)){
					dlvConsgTo = deliveryCommonService.getOutManifestedConsgnDtls(deliveryTO);
				}else{
					throw exception;
				}
				
			}else{
				
				//1. verify whether it's TPMF for PPX for child
				mnfstNumber=deliveryCommonService.getOutManifestedNumberByLoggedOfficeForTPManifestChildConsgForPpxManualDrs(deliveryTO);
				//2. if yes then get the Cn details
				if(!StringUtil.isStringEmpty(mnfstNumber)){
					dlvConsgTo= deliveryCommonService.getChildConsgDetailsForDRS(consgNumber);
				}else{
					throw exception;
				}
			}
			
			
			
		}
		if(!StringUtil.isNull(dlvConsgTo)){
			if(isParent){
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
			}else{
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE);
			}
		}
		return dlvConsgTo;
	}

	public DeliveryConsignmentTO validateConsignmentForThirdPartyManifestDrs(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		String inConsgType=deliveryTO.getConsignmentType();
		switch(inConsgType){
		case CommonConstants.CONSIGNMENT_TYPE_DOCUMENT:
			dlvConsgTo	=validateConsignmentForThirdPartyManifestDoxDRS(deliveryTO);
			break;
		case CommonConstants.CONSIGNMENT_TYPE_PARCEL:
			dlvConsgTo	=validateConsignmentForThirdPartyManifestPpxDRS(deliveryTO);
			break;
		}
		
		if(StringUtil.isNull(dlvConsgTo)){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.CONSG_NO_NOT_UNDER_MANIFEST,new String[]{deliveryTO.getConsignmentNumber(),deliveryTO.getDrsNumber()});
		}
		return dlvConsgTo;

	}
	

	public DeliveryConsignmentTO validateConsignmentForThirdPartyManifestDoxDRS(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO consgTO=null;
		Boolean statuFlag=Boolean.FALSE;
		String consgNumber = deliveryTO.getConsignmentNumber();
		
			/** it checks whether given consignment exist in the Consignment table or not*/
			statuFlag= deliveryCommonService.isConsignmentValid(consgNumber);

			if(!statuFlag){
				/** throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
			Date manifestDate = deliveryCommonService.getManifestedDateByConsgNumberForThirdPartyManifestDrs(deliveryTO, true);

			if(!StringUtil.isNull(manifestDate)){
				consgTO=deliveryCommonService.getThirdPartyManifestedConsgnDtlsForParentCn(deliveryTO);
			}
			
		if(!StringUtil.isNull(consgTO)){
			consgTO.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
		}
		return consgTO;
	}


	public DeliveryConsignmentTO validateConsignmentForThirdPartyManifestPpxDRS(AbstractDeliveryTO deliveryTO)throws CGBusinessException, CGSystemException{
		DeliveryConsignmentTO dlvConsgTo=null;
		boolean isParent=true;
		String consgNumber = deliveryTO.getConsignmentNumber();
		Boolean isConghasChlds=deliveryCommonService.isConsgHavingChildCns(consgNumber);

		if(isConghasChlds){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PARENT_CONSG_NUMBER,new String[]{consgNumber});
		}
		Boolean isChildcn=deliveryCommonService.isChildCn(consgNumber);

		if(isChildcn){
			isParent=false;
		}
		Date manifestDate = deliveryCommonService.getManifestedDateByConsgNumberForThirdPartyManifestDrs(deliveryTO, isParent);

		if(!StringUtil.isNull(manifestDate)){
			/** get information from manifest table*/
			if(isParent){
				dlvConsgTo=deliveryCommonService.getThirdPartyManifestedConsgnDtlsForParentCn(deliveryTO);
			}else{
				dlvConsgTo=deliveryCommonService.getThirdPartyManifestedConsgnDtlsForChildCn(deliveryTO);
			}

		}
		if(!StringUtil.isNull(dlvConsgTo)){
			if(isParent){
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE);
			}else{
				dlvConsgTo.setParentChildCnType(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE);
			}
		}
		return dlvConsgTo;
	}


	private DeliveryConsignmentTO validateForComailForManualDrs(AbstractDeliveryTO deliveryTO) throws CGSystemException, CGBusinessException {
		Boolean statuFlag;
		DeliveryConsignmentTO consgTO=null;
		/** Note : since comail consgment's do not have Booking Information */
		String consgNumber = deliveryTO.getConsignmentNumber();
		statuFlag=deliveryCommonService.isComailValid(consgNumber);
		if(!statuFlag){
			/** throw Business Exception*/
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_COMAIL,consgNumber});
		}
		/** it checks whether given consignment is IN-Manifested or not*/
		consgTO= deliveryCommonService.getInManifestedComailDtls(deliveryTO);

		/** it checks whether given consignment is valid Consignment Type or not*/
		if(consgTO==null){
			/** Consgnment not yet inmanifested ,throw Business Exception*/
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED,new String[]{UniversalDeliveryContants.DRS_COMAIL,consgNumber});
		}
		return consgTO;
	}
	@Override
	public ManualDrsTO findDrsByDrsNumberForUpdate(ManualDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException{

		if(StringUtil.isStringEmpty(drsInputTo.getDrsNumber())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
		}
		
		String status=deliveryCommonService.getDrsStatusByDrsNumber(drsInputTo);
		if(!StringUtil.isStringEmpty(status)){
			getDetailsFromDrsForUpdate(drsInputTo);
		}else{
			if(StringUtil.isStringEmpty(drsInputTo.getManifestDrsType())){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_MANIFEST_DRS_TYPE_INVALID);
			}
			if(!StringUtil.isStringEmpty(drsInputTo.getManifestDrsType())&& drsInputTo.getManifestDrsType().equalsIgnoreCase(DrsConstants.MANIFEST_DRS_TYPE_MANIFEST)){
				ManifestDO manifestDo=manualDrsDAO.getManifestDetailsByManifestNumber((AbstractDeliveryTO)drsInputTo);
				if(manifestDo!=null){
					prepareThirdPartyManifestForDRSHeader((AbstractDeliveryTO)drsInputTo,
							manifestDo);
					prepareThirdpartyManifestGridDetails(drsInputTo, null, null, manifestDo);
					drsInputTo.setCanCreateRows(DrsConstants.FLAG_NO);
				}else{
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_TMF_NOT_EXIST);
				}
			}else{
				/** If DRS Does not Exist in the DRS table then this block will executes*/
				drsInputTo.setCanCreateRows(DrsConstants.FLAG_YES);
			}
		}
		return drsInputTo;
	}

	private void prepareThirdPartyManifestForDRSHeader(AbstractDeliveryTO drsInputTo,
			ManifestDO manifestDo) throws CGBusinessException,
			CGSystemException {
		if(CGCollectionUtils.isEmpty(manifestDo.getConsignments())){
			//throw CGBusiness Exception
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR, new String[]{DrsCommonConstants.CONSIGNMENT," For given Manifest No:"+drsInputTo.getDrsNumber()});
		}else{
			drsInputTo.setMaxAllowedRows(manifestDo.getConsignments().size());
		}
		if(StringUtil.isStringEmpty(manifestDo.getManifestStatus()) || !manifestDo.getManifestStatus().equalsIgnoreCase(CommonConstants.MANIFEST_STATUS_CLOSED)){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_MANIFEST_NOT_CLOSED,new String[]{drsInputTo.getDrsNumber()});
		}
		if(manifestDo.getUpdatingProcess().getProcessCode().equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX)){
			drsInputTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		}else if(manifestDo.getUpdatingProcess().getProcessCode().equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL)){
			drsInputTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		}

		drsInputTo.setFsOutTimeDateStr(DateUtil.getDDMMYYYYDateToString(manifestDo.getManifestDate()));
		//drsInputTo.setFsOutTimeMinStr(DateUtil.getTimeFromDate(manifestDo.getManifestDate()));
		
		drsInputTo.setFsOutTimeMMStr(DateUtil.extractMinutesInMMFormatFromDate(manifestDo.getManifestDate()));
		drsInputTo.setFsOutTimeHHStr(DateUtil.extractHourInHHFormatFromDate(manifestDo.getManifestDate()));
		drsInputTo.setLoadNumber(manifestDo.getLoadLotId());
		if(!StringUtil.isStringEmpty(manifestDo.getThirdPartyType())){
			boolean isValidVendor=true;
			switch(manifestDo.getThirdPartyType()){
			case UdaanCommonConstants.VENDOR_TYPE_BA:
				drsInputTo.setDrsFor(DrsConstants.DRS_FOR_BA);
				break;
			case UdaanCommonConstants.VENDOR_TYPE_FRANCHISE:
				drsInputTo.setDrsFor(DrsConstants.DRS_FOR_FR);
				break;
			case UdaanCommonConstants.VENDOR_TYPE_CO_COURIER:
				drsInputTo.setDrsFor(DrsConstants.DRS_FOR_CO_COURIER);
				break;
				default:
					isValidVendor=false;
			}
			if(!isValidVendor){
				//throw CGBusiness Exception
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR, new String[]{UniversalDeliveryContants.DRS_MANIFEST_TYPE,UniversalDeliveryContants.DRS_FOR_MANIFEST_NO});
			}
		}else{
			//throw CGBusiness Exception
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR, new String[]{UniversalDeliveryContants.DRS_MANIFEST_TYPE,UniversalDeliveryContants.DRS_FOR_MANIFEST_NO});
		}
		if(StringUtil.isEmptyInteger(manifestDo.getVendorId())){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST_FOR, new String[]{UniversalDeliveryContants.DRS_MANIFEST_TYPE,UniversalDeliveryContants.DRS_FOR_MANIFEST_NO});
		}
		drsInputTo.setDrsPartyId(manifestDo.getVendorId());
		drsInputTo.setPartyTypeMap(deliveryCommonService.getPartyTypeDetailsForDRS(drsInputTo,drsInputTo.getDrsFor()));
	}
	
	

}
