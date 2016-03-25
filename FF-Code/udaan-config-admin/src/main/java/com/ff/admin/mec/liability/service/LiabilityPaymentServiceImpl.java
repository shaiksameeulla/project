package com.ff.admin.mec.liability.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.expense.service.ExpenseEntryServiceImpl;
import com.ff.admin.mec.liability.dao.LiabilityDAO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.LiabilityCollectionWrapperDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.LiabilityDetailsDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.to.mec.LiabilityDetailsTO;
import com.ff.to.mec.LiabilityPageTO;
import com.ff.to.mec.LiabilityTO;

/**
 * @author amimehta
 */

public class LiabilityPaymentServiceImpl implements LiabilityPaymentService {

	/** The LOGGER> */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpenseEntryServiceImpl.class);

	/** The Expense Entry DAO */
	private LiabilityDAO liabilityDAO;

	/** The MEC common service */
	private MECCommonService mecCommonService;

	/**
	 * @param liabilityDAO
	 *            the liability payment DAO Impl
	 */
	public void setLiabilityDAO(LiabilityDAO liabilityDAO) {
		this.liabilityDAO = liabilityDAO;
	}

	/**
	 * @param mecCommonService
	 *            the mecCommonService to set
	 */
	public void setMecCommonService(MECCommonService mecCommonService) {
		this.mecCommonService = mecCommonService;
	}
	@Deprecated
	@Override
	public LiabilityTO saveOrUpdateLiability(LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl :: saveOrUpdateLiability() :: START");
		LiabilityDO domain = null;
		if (!StringUtil.isNull(to)) {
			if (StringUtil.isStringEmpty(to.getTxNumber())) {
				List<String> seqNOs = mecCommonService.generateSequenceNo(
						Integer.parseInt(MECCommonConstants.ONE),
						CommonConstants.GEN_LIABILITY_TXN_NO);
				String txNumber = getTxNoForLiability(to, seqNOs);
				to.setTxNumber(txNumber);
			}
			to.setLiabilityStatus(MECCommonConstants.SUBMITTED_STATUS);

			domain = createLiabilityDOFromTO(to);
			liabilityDAO.saveOrUpdateLiabilityDtls(domain);
		} else {
			throw new CGBusinessException(
					AdminErrorConstants.DETAILS_CAN_NOT_SAVED);
		}
		LOGGER.trace("LiabilityPaymentServiceImpl :: saveOrUpdateLiability() :: END");
		return to;
	}
	@Override
	public LiabilityTO saveLiability(LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		long initilaTime = System.currentTimeMillis();
		LOGGER.debug("LiabilityPaymentServiceImpl :: saveLiability() :: START");
		LiabilityDO domain = null;
		if (!StringUtil.isNull(to)) {
		//	if (StringUtil.isStringEmpty(to.getTxNumber())) {
				List<String> seqNOs = mecCommonService.generateSequenceNo(
						Integer.parseInt(MECCommonConstants.ONE),
						CommonConstants.GEN_LIABILITY_TXN_NO);
				if(CGCollectionUtils.isEmpty(seqNOs)){
					throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
				}
				String txNumber = getTxNoForLiability(to, seqNOs);
				
				to.setTxNumber(txNumber);
			//}
			to.setLiabilityStatus(MECCommonConstants.SUBMITTED_STATUS);

			domain = prepareLiabilityDOFromTO(to);
			if(CGCollectionUtils.isEmpty(domain.getLiabilityDetailsList())){
				throw new CGBusinessException(
						AdminErrorConstants.DETAILS_CAN_NOT_SAVED);
			}
			long initialDBTime = System.currentTimeMillis();
			LOGGER.debug("LiabilityPaymentServiceImpl :: DAO before call() :: START" +initialDBTime);
			liabilityDAO.saveLiability(domain);
			long finalDBTime = System.currentTimeMillis();
			long diff=finalDBTime-initilaTime;
			String timePhase=" finalTime:["+finalDBTime+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff);
			LOGGER.debug("LiabilityPaymentServiceImpl :: After DAO call() :: timePhase"+timePhase);
			
		} else {
			throw new CGBusinessException(
					AdminErrorConstants.DETAILS_CAN_NOT_SAVED);
		}
		long finalTime = System.currentTimeMillis();
		long diff=finalTime-initilaTime;
		String timePhase=" finalTime:["+finalTime+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff);
		LOGGER.debug("LiabilityPaymentServiceImpl :: saveLiability() :: END "+timePhase);
		return to;
	}

	@Deprecated
	@Override
	public LiabilityTO saveOrUpdateLiabilityDtlsForNext(LiabilityTO liabilityTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::saveOrUpdateLiabilityDtlsForNext::START");
		LiabilityDO liabilityDO = null;
		LiabilityDO liabilityDO2 = new LiabilityDO();
		LiabilityTO liabilityTO2 = new LiabilityTO();
		LiabilityDetailsTO liabilityDtlsTo = null;
		List<LiabilityDetailsTO> liabilityDtlsTos = new ArrayList<>();
		liabilityDO = createLiabilityDOFromTO(liabilityTO);
		try {
			liabilityDO2 = liabilityDAO
					.saveOrUpdateLiabilityDtlsForNext(liabilityDO);
			if (!StringUtil.isNull(liabilityDO2)) {
				liabilityTO2 = convertToFromDO(liabilityTO2, liabilityDO2);

				Set<LiabilityDetailsDO> liabilityDtlsDoSet = liabilityDO2
						.getLiabilityDetailsList();
				for (LiabilityDetailsDO liabilityDtlsDo : liabilityDtlsDoSet) {
					liabilityDtlsTo = new LiabilityDetailsTO();
					liabilityDtlsTo.setConsgId(liabilityDtlsDo.getConsgId()
							.getConsgId());
					liabilityDtlsTo.setConsgNo(liabilityDtlsDo.getConsgId()
							.getConsgNo());
					liabilityDtlsTo.setCodLcAmt(liabilityDtlsDo.getCodLcAmt());
					liabilityDtlsTo.setCollectedAmt(liabilityDtlsDo
							.getCollectedAmt());
					liabilityDtlsTo.setPaidAmt(liabilityDtlsDo.getPaidAmt());
					liabilityDtlsTos.add(liabilityDtlsTo);

				}
				liabilityTO2.setLiabilityDetailsTOList(liabilityDtlsTos);
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception in :: LiabilityPaymentServiceImpl :: saveOrUpdateLiabilityDtlsForNext ",
					e);
			throw new CGBusinessException(
					AdminErrorConstants.DETAILS_CAN_NOT_SAVED);
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::saveOrUpdateLiabilityDtlsForNext::END");
		return liabilityTO2;

	}

	/**
	 * To get Tx No for Expense
	 * 
	 * @param to
	 * @param seqNOs
	 * @return txNo
	 */
	private String getTxNoForLiability(LiabilityTO to, List<String> seqNOs) {
		String txNo = to.getLoginOfficeCode() + MECCommonConstants.TX_CODE_LP
				+ seqNOs.get(0);
		return txNo;
	}

	/**
	 * To create liability DO from TO
	 * 
	 * @param to
	 * @return liabilityDo
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Deprecated
	private LiabilityDO createLiabilityDOFromTO(LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::createLiabilityDOFromTO::START");
		LiabilityDO liabilityDo = new LiabilityDO();

		RegionDO regionDo = new RegionDO();
		regionDo.setRegionId(to.getRegionId());
		liabilityDo.setRegionId(regionDo);

		CustomerDO custDo = fillCustDO(to);
		liabilityDo.setCustId(custDo);

		GLMasterDO bankDo = convertBankDO(to);
		liabilityDo.setBankId(bankDo);

		OfficeDO officeDo = convertOfficeDO(to);
		liabilityDo.setLiabilityOffice(officeDo);
		// fill common header fields in to
		if (!StringUtil.isEmptyInteger(to.getLiabilityId()))
			liabilityDo.setLiabilityId(to.getLiabilityId());

		if (!StringUtil.isNull(to.getLiabilityDate()))
			liabilityDo
					.setLiabilityDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(to
									.getLiabilityDate()));
		if (!StringUtil.isStringEmpty(to.getPaymentMode()))
			liabilityDo.setPaymentMode(to.getPaymentMode());
		if (!StringUtil.isNull(to.getChqDate()))
			liabilityDo.setChqDate(DateUtil.stringToDDMMYYYYFormat(to
					.getChqDate()));
		if (!StringUtil.isNull(to.getChqNo()))
			liabilityDo.setChqNo(to.getChqNo());
		if (!StringUtil.isNull(to.getLiabilityAmt()))
			liabilityDo.setLiabiltyAmt(to.getLiabilityAmt());
		if (!StringUtil.isNull(to.getTxNumber()))
			liabilityDo.setTxNumber(to.getTxNumber());
		if (!StringUtil.isNull(to.getLiabilityStatus()))
			liabilityDo.setStatus(to.getLiabilityStatus());
		liabilityDo.setCreatedBy(to.getCreatedBy());
		liabilityDo.setUpdatedBy(to.getUpdatedBy());

		// Setting Created Date & Updated Date
		liabilityDo.setCreatedDate(Calendar.getInstance().getTime());
		liabilityDo.setUpdatedDate(Calendar.getInstance().getTime());

		// fill details to list
		Set<LiabilityDetailsDO> libilityDetailsSet = null;
		int consgNosLength = to.getConsgNo().length;
		if (to.getConsgNo() != null && consgNosLength > 0) {
			libilityDetailsSet = convertDetailsDoFromTO(to, liabilityDo);
		}
		liabilityDo.setLiabilityDetailsList(libilityDetailsSet);
		LOGGER.trace("LiabilityPaymentServiceImpl::createLiabilityDOFromTO::END");
		return liabilityDo;
	}

	private LiabilityDO prepareLiabilityDOFromTO(LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::createLiabilityDOFromTO::START");
		LiabilityDO liabilityDo = new LiabilityDO();

		RegionDO regionDo = new RegionDO();
		regionDo.setRegionId(to.getRegionId());
		liabilityDo.setRegionId(regionDo);

		CustomerDO custDo = fillCustDO(to);
		liabilityDo.setCustId(custDo);

		GLMasterDO bankDo = convertBankDO(to);
		liabilityDo.setBankId(bankDo);

		OfficeDO officeDo = convertOfficeDO(to);
		liabilityDo.setLiabilityOffice(officeDo);
		// fill common header fields in to
		

		if (!StringUtil.isNull(to.getLiabilityDate()))
			liabilityDo
					.setLiabilityDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(to
									.getLiabilityDate()));
		if (!StringUtil.isStringEmpty(to.getPaymentMode()))
			liabilityDo.setPaymentMode(to.getPaymentMode());
		if (!StringUtil.isNull(to.getChqDate()))
			liabilityDo.setChqDate(DateUtil.stringToDDMMYYYYFormat(to
					.getChqDate()));
		if (!StringUtil.isNull(to.getChqNo()))
			liabilityDo.setChqNo(to.getChqNo());
		if (!StringUtil.isNull(to.getLiabilityAmt()))
			liabilityDo.setLiabiltyAmt(to.getLiabilityAmt());
		if (!StringUtil.isNull(to.getTxNumber()))
			liabilityDo.setTxNumber(to.getTxNumber());
		if (!StringUtil.isNull(to.getLiabilityStatus()))
			liabilityDo.setStatus(to.getLiabilityStatus());
		liabilityDo.setCreatedBy(to.getCreatedBy());
		liabilityDo.setUpdatedBy(to.getUpdatedBy());

		// Setting Created Date & Updated Date
		liabilityDo.setCreatedDate(Calendar.getInstance().getTime());
		liabilityDo.setUpdatedDate(Calendar.getInstance().getTime());

		// fill details to list
		Set<LiabilityDetailsDO> libilityDetailsSet = null;
			libilityDetailsSet = convertCollectionDetailsTO2DO(to, liabilityDo);
		liabilityDo.setLiabilityDetailsList(libilityDetailsSet);
		LOGGER.trace("LiabilityPaymentServiceImpl::createLiabilityDOFromTO::END");
		return liabilityDo;
	}


	/**
	 * To fill customer DO
	 * 
	 * @param to
	 * @return custDo
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private CustomerDO fillCustDO(LiabilityTO to) throws CGBusinessException,
			CGSystemException {
		CustomerDO custDo = new CustomerDO();
		custDo.setCustomerId(to.getCustId());
		return custDo;
	}

	/**
	 * To convert bank do
	 * 
	 * @param to
	 * @return bankDo
	 */
	private GLMasterDO convertBankDO(LiabilityTO to) {
		GLMasterDO bankDo = null;
		if (!StringUtil.isEmptyInteger(to.getBankId())) {
			bankDo = new GLMasterDO();
			bankDo.setGlId(to.getBankId());
			bankDo.setGlDesc(to.getBankName());
		}
		return bankDo;
	}

	/**
	 * To convert office do
	 * 
	 * @param to
	 * @return officeDo
	 */
	private OfficeDO convertOfficeDO(LiabilityTO to) {
		LOGGER.trace("LiabilityPaymentServiceImpl::convertOfficeDO::START");
		OfficeDO officeDo = new OfficeDO();
		officeDo.setOfficeId(to.getLoginOfficeId());
		officeDo.setOfficeCode(to.getLoginOfficeCode());
		LOGGER.trace("LiabilityPaymentServiceImpl::convertOfficeDO::END");
		return officeDo;

	}

	/**
	 * To fill in liability do - convert details DO from TO
	 * 
	 * @param to
	 * @param domain
	 * @return liabilityDetailSet
	 */
	@Deprecated
	public static Set<LiabilityDetailsDO> convertDetailsDoFromTO(
			LiabilityTO to, LiabilityDO domain) {
		LOGGER.trace("LiabilityPaymentServiceImpl::convertDetailsDoFromTO::START");
		Set<LiabilityDetailsDO> liabilityDetailSet = null;
		int consgNosLength = to.getConsgNo().length;
		if (to.getConsgNo() != null && consgNosLength > 0) {
			liabilityDetailSet = new HashSet<>(to.getLiabilityDetailsTOList()
					.size());
			for (LiabilityDetailsTO liabilityDetailsTO : to
					.getLiabilityDetailsTOList()) {
				if (!StringUtil.isEmptyInteger(liabilityDetailsTO.getConsgId())) {
					LiabilityDetailsDO liabilityDetaildo = new LiabilityDetailsDO();
					if (!StringUtil.isEmptyInteger(liabilityDetailsTO
							.getLibilityDetailsId()))
						liabilityDetaildo
								.setLiabilityDetailId(liabilityDetailsTO
										.getLibilityDetailsId());
					ConsignmentDO consgDO = new ConsignmentDO();
					consgDO.setConsgId(liabilityDetailsTO.getConsgId());
					consgDO.setConsgNo(liabilityDetailsTO.getConsgNo());
					liabilityDetaildo.setConsgId(consgDO);
					liabilityDetaildo
							.setBookingDate(DateUtil
									.slashDelimitedstringToDDMMYYYYFormat(liabilityDetailsTO
											.getBookingDate()));
					liabilityDetaildo.setCodLcAmt(liabilityDetailsTO
							.getCodLcAmt());
					liabilityDetaildo.setCollectedAmt(liabilityDetailsTO
							.getCollectedAmt());
					liabilityDetaildo.setPaidAmt(liabilityDetailsTO
							.getPaidAmt());
					liabilityDetaildo.setLiabilityDetailId(liabilityDetailsTO
							.getLibilityDetailsId());
					liabilityDetaildo.setCreatedBy(to.getCreatedBy());
					liabilityDetaildo.setUpdatedBy(to.getUpdatedBy());
					liabilityDetaildo.setLiabilityId(domain);

					// Setting Created Date & Updated Date
					liabilityDetaildo.setCreatedDate(Calendar.getInstance()
							.getTime());
					liabilityDetaildo.setUpdatedDate(Calendar.getInstance()
							.getTime());

					liabilityDetailSet.add(liabilityDetaildo);
				}
			}
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::convertDetailsDoFromTO::END");
		return liabilityDetailSet;
	}

	
	public static Set<LiabilityDetailsDO> convertCollectionDetailsTO2DO(
			LiabilityTO to, LiabilityDO liabilityHeaderDO) {
		LOGGER.trace("LiabilityPaymentServiceImpl::convertDetailsDoFromTO::START");
		Set<LiabilityDetailsDO> liabilityDetailSet = null;
		Date currentDat=Calendar.getInstance()
				.getTime();
		liabilityDetailSet = new HashSet<>(to.getPageList().size()*to.getTotalNoPages());
		for (LiabilityPageTO pageTO : to.getPageList()) {
			for(LiabilityDetailsTO liabilityDetailsTO:pageTO.getPageContentList()){
				if(!StringUtil.isStringEmpty(liabilityDetailsTO.getIsSelect()) && liabilityDetailsTO.getIsSelect().equalsIgnoreCase(CommonConstants.YES)){
					LiabilityDetailsDO liabilityDetaildo = new LiabilityDetailsDO();
				
					ConsignmentDO consgDO = new ConsignmentDO();
					consgDO.setConsgId(liabilityDetailsTO.getConsgId());
					consgDO.setConsgNo(liabilityDetailsTO.getConsgNo());
					liabilityDetaildo.setConsgId(consgDO);
					liabilityDetaildo
					.setBookingDate(DateUtil
							.slashDelimitedstringToDDMMYYYYFormat(liabilityDetailsTO
									.getBookingDate()));
					liabilityDetaildo.setCodLcAmt(liabilityDetailsTO
							.getCodLcAmt());
					liabilityDetaildo.setCollectedAmt(liabilityDetailsTO
							.getCollectedAmt());
					liabilityDetaildo.setPaidAmt(liabilityDetailsTO
							.getPaidAmt());
					liabilityDetaildo.setCollectionEntriesId(liabilityDetailsTO.getCollectionEntriesId());
					liabilityDetaildo.setTransactionBalanceAmount(liabilityDetailsTO.getBalanceAmount());
					double balance=liabilityDetailsTO
							.getCollectedAmt()-liabilityDetailsTO
							.getPaidAmt(); 
					if(balance >0.0d){
						liabilityDetaildo.setPartialPayment(true);
					}else if(balance<0){
						//TODO throw Exception
					}
					
					liabilityDetaildo.setCreatedBy(to.getCreatedBy());
					liabilityDetaildo.setUpdatedBy(to.getUpdatedBy());
					liabilityDetaildo.setLiabilityId(liabilityHeaderDO);
					liabilityDetaildo.setCollectionEntriesId(liabilityDetailsTO.getCollectionEntriesId());

					// Setting Created Date & Updated Date
					liabilityDetaildo.setCreatedDate(currentDat);
					liabilityDetaildo.setUpdatedDate(currentDat);

					liabilityDetailSet.add(liabilityDetaildo);
				}
			}
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::convertDetailsDoFromTO::END");
		return liabilityDetailSet;
	}


	@Override
	@Deprecated
	public LiabilityTO getLiabilityDetails(Integer regionId, Integer custId,
			String custCode) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilityDetails::START");
		LiabilityTO liabilityTO = new LiabilityTO();
			List<LiabilityDetailsTO> liabilityDtlsTos =null;
			List<CollectionDtlsDO> collectionDtlsDOs = liabilityDAO
					.getLiabilityDetails(regionId, custId);
			if(!CGCollectionUtils.isEmpty(collectionDtlsDOs)){
				liabilityDtlsTos = new ArrayList<LiabilityDetailsTO>(collectionDtlsDOs.size());
				for(CollectionDtlsDO collectionDtlsDO :collectionDtlsDOs){
					LiabilityDetailsTO liabilityDtlsTo = new LiabilityDetailsTO();
					liabilityDtlsTo.setConsgId(collectionDtlsDO.getConsgDO()
							.getConsgId());
					liabilityDtlsTo.setConsgNo(collectionDtlsDO.getConsgDO()
							.getConsgNo());
					liabilityDtlsTo.setCodLcAmt(collectionDtlsDO.getBillAmount());
					liabilityDtlsTo.setCollectedAmt(collectionDtlsDO
							.getTotalBillAmount());

					// Setting booking date
					Date bookindDate = mecCommonService
							.getBookingDateByConsgNo(collectionDtlsDO.getConsgDO()
									.getConsgNo());
					if (!StringUtil.isNull(bookindDate)) {
						if(bookindDate.before(DateUtil.stringToDDMMYYYYFormat("01/04/2015"))){
							continue;
						}
						liabilityDtlsTo.setBookingUtilDate(bookindDate);
						liabilityDtlsTo
						.setBookingDate(DateUtil
								.getDDMMYYYYDateToString(bookindDate));
					}
					liabilityDtlsTos.add(liabilityDtlsTo);
				}

			}
			if(!CGCollectionUtils.isEmpty(liabilityDtlsTos)){
				Collections.sort(liabilityDtlsTos);
			}

			liabilityTO.setLiabilityDetailsTOList(liabilityDtlsTos);
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilityDetails::END");
		return liabilityTO;
	}
	@Override
	public LiabilityTO getLiabilityDetailsFromCollection(Integer regionId, Integer custId,
			String custCode) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilityDetails::START");
		LiabilityTO liabilityTO = new LiabilityTO();
		List<Integer> paidConsg=null;
			List<LiabilityDetailsTO> liabilityDtlsTos =null;
			List<LiabilityCollectionWrapperDO> collectionDtlsDOs = liabilityDAO
					.getLiabilityDetailsFromCollection(regionId, custId);
			if(!CGCollectionUtils.isEmpty(collectionDtlsDOs)){
				liabilityDtlsTos = new ArrayList<LiabilityDetailsTO>(collectionDtlsDOs.size());
				paidConsg= new ArrayList<Integer>(collectionDtlsDOs.size());
				for(LiabilityCollectionWrapperDO collectionDtlsDO :collectionDtlsDOs){
					LiabilityDetailsTO liabilityDtlsTo = new LiabilityDetailsTO();
					liabilityDtlsTo.setConsgId(collectionDtlsDO.getConsgId());
					liabilityDtlsTo.setConsgNo(collectionDtlsDO.getConsgNo());
					liabilityDtlsTo.setCodLcAmt(collectionDtlsDO.getBillAmount());
					liabilityDtlsTo.setCollectedAmt(collectionDtlsDO
							.getTotalBillAmount());
					liabilityDtlsTo.setBalanceAmount(collectionDtlsDO.getBalanceAmount());
					liabilityDtlsTo.setCollectionEntriesId(collectionDtlsDO.getCollectionEntryId());
					// Setting booking date
					Date bookindDate = mecCommonService
							.getBookingDateByConsgNo(collectionDtlsDO.getConsgNo());
					if (!StringUtil.isNull(bookindDate)) {
						if(bookindDate.before(DateUtil.stringToDDMMYYYYFormat("01/04/2015"))){
							continue;
						}

					}else{
						bookindDate=collectionDtlsDO.getBookingDate();
					}
					Double totalPaidAmount=	liabilityDAO.getTotalPaidLiabilityByConsg(collectionDtlsDO.getConsgId());
					Double balancePaidAmount=collectionDtlsDO.getBalanceAmount();
					if(!StringUtil.isEmptyDouble(totalPaidAmount)){
						Double balanceAmt=collectionDtlsDO.getTotalBillAmount()-totalPaidAmount;
						if(StringUtil.isEmptyDouble(balanceAmt) || balanceAmt<0){
							//total Amount Paid nothing to pay for this Consignment
							paidConsg.add(collectionDtlsDO.getConsgId());
							continue;
						}else{
							balancePaidAmount=balanceAmt;
						}
					}
					if(StringUtil.isEmptyDouble(balancePaidAmount)){
						balancePaidAmount=collectionDtlsDO.getTotalBillAmount();
					}
					liabilityDtlsTo.setBalanceAmount(balancePaidAmount);
					liabilityDtlsTo.setBookingUtilDate(bookindDate);
					liabilityDtlsTo
					.setBookingDate(DateUtil
							.getDDMMYYYYDateToString(bookindDate));
					liabilityDtlsTos.add(liabilityDtlsTo);
				}

			}
			if(!CGCollectionUtils.isEmpty(paidConsg)){
				processPaidLiabilityConsingments(paidConsg);
			}
			if(!CGCollectionUtils.isEmpty(liabilityDtlsTos)){
				Collections.sort(liabilityDtlsTos);
			}else {
				//thow Exception 
				throw new CGBusinessException(MECCommonConstants.LIBILITY_ERROR_DATA_NOT_FOUND);
			}

			liabilityTO.setLiabilityDetailsTOList(liabilityDtlsTos);
			
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilityDetails::END");
		return liabilityTO;
	}

	@Override
	public LiabilityTO searchLiabilityDetails(String txnNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::searchLiabilityDetails::START");
		LiabilityTO liabilityTO = new LiabilityTO();
		LiabilityDO liabilityDO = new LiabilityDO();
		LiabilityDetailsTO liabilityDtlsTo;
		List<LiabilityDetailsTO> liabilityDtlsTos = new ArrayList<>();
		liabilityDO = liabilityDAO.searchLiabilityDetails(txnNo);
		if (liabilityDO != null) {
			liabilityTO = convertToFromDO(liabilityTO, liabilityDO);
			Set<LiabilityDetailsDO> liabilityDtlsDoSet = liabilityDO
					.getLiabilityDetailsList();
			for (LiabilityDetailsDO liabilityDtlsDo : liabilityDtlsDoSet) {
				liabilityDtlsTo = new LiabilityDetailsTO();
				liabilityDtlsTo.setConsgId(liabilityDtlsDo.getConsgId()
						.getConsgId());
				liabilityDtlsTo.setConsgNo(liabilityDtlsDo.getConsgId()
						.getConsgNo());
				// Booking date
				if (!StringUtil.isNull(liabilityDtlsDo.getBookingDate())) {
					liabilityDtlsTo.setBookingDate(DateUtil
							.getDDMMYYYYDateToString(liabilityDtlsDo
									.getBookingDate()));
					liabilityDtlsTo.setBookingUtilDate(liabilityDtlsDo.getBookingDate());
				}
				liabilityDtlsTo.setCodLcAmt(liabilityDtlsDo.getCodLcAmt());
				liabilityDtlsTo.setCollectedAmt(liabilityDtlsDo
						.getCollectedAmt());
				liabilityDtlsTo.setPaidAmt(liabilityDtlsDo.getPaidAmt());
				liabilityDtlsTo.setBalanceAmount(liabilityDtlsDo.getTransactionBalanceAmount());
				liabilityDtlsTos.add(liabilityDtlsTo);
			}
			if(!CGCollectionUtils.isEmpty(liabilityDtlsTos)){
				Collections.sort(liabilityDtlsTos);
			}else{
				throw new CGBusinessException(
						MECCommonConstants.DTLS_NOT_FOUND_FOR_GIVEN_TXN);
			}
			liabilityTO.setLiabilityDetailsTOList(liabilityDtlsTos);
		} else {
			throw new CGBusinessException(
					MECCommonConstants.DTLS_NOT_FOUND_FOR_GIVEN_TXN);
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilityDetails::END");
		return liabilityTO;
	}

	/**
	 * To convert to from do
	 * 
	 * @param liabilityTO
	 * @param liabilityDO
	 * @return liabilityTO
	 */
	private LiabilityTO convertToFromDO(LiabilityTO liabilityTO,
			LiabilityDO liabilityDO) {
		LOGGER.trace("LiabilityPaymentServiceImpl::convertToFromDO::START");
		if (!StringUtil.isNull(liabilityDO.getLiabilityId()))
			liabilityTO.setLiabilityId(liabilityDO.getLiabilityId());
		if (!StringUtil.isNull(liabilityDO.getTxNumber()))
			liabilityTO.setTxNumber(liabilityDO.getTxNumber());
		if (!StringUtil.isNull(liabilityDO.getRegionId().getRegionId()))
			liabilityTO.setRegionId(liabilityDO.getRegionId().getRegionId());
		if (!StringUtil.isNull(liabilityDO.getCustId().getCustomerCode())) {
			liabilityTO.setCustId(liabilityDO.getCustId().getCustomerId());
			liabilityTO.setCustCode(liabilityDO.getCustId().getCustomerCode());
			liabilityTO.setCustName(liabilityDO.getCustId().getBusinessName());
		}
		if (!StringUtil.isNull(liabilityDO.getLiabilityDate()))
			liabilityTO.setLiabilityDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(liabilityDO
							.getLiabilityDate()));
		if (!StringUtil.isNull(liabilityDO.getPaymentMode())) {
			liabilityTO.setPaymentMode(liabilityDO.getPaymentMode());
			if (liabilityDO.getPaymentMode().equalsIgnoreCase("C")) {

				// Bank Name
				liabilityTO.setBankId(liabilityDO.getBankId().getGlId());
				liabilityTO.setBankName(liabilityDO.getBankId().getGlDesc());

				liabilityTO.setBankName(liabilityDO.getBankId().getGlDesc());
				liabilityTO.setChqNo(liabilityDO.getChqNo());
				liabilityTO.setChqDate(DateUtil
						.getDDMMYYYYDateToString(liabilityDO.getChqDate()));
			}
		}
		if (!StringUtil.isNull(liabilityDO.getLiabilityId()))
			liabilityTO.setLiabilityAmt(liabilityDO.getLiabiltyAmt());
		if (!StringUtil.isNull(liabilityDO.getStatus()))
			liabilityTO.setLiabilityStatus(liabilityDO.getStatus());
		LOGGER.trace("LiabilityPaymentServiceImpl::convertToFromDO::END");
		return liabilityTO;
	}
	
	@Override
	public void processPaidLiabilityConsingments(List<Integer> consgList)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl::processPaidLiabilityConsingments::START");
		liabilityDAO.processPaidLiabilityConsingments(consgList);
		LOGGER.trace("LiabilityPaymentServiceImpl::processPaidLiabilityConsingments::END");
	}

}
