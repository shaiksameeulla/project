/**
 * 
 */
package com.dtdc.bodbadmin.utility;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.dao.CentralDataExtractorDAO;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.*;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.ConsigneeDtls.*;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.*;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.ConsignerDetails.*;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.dispatch.DispatchBagManifestDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.ConsignerAddressDO;
import com.dtdc.domain.master.customer.ConsignerInfoDO;
import com.dtdc.domain.master.product.VasProductChargesDO;
import com.dtdc.domain.purchase.GoodsCanclItemDtlsDO;
import com.dtdc.domain.purchase.GoodsIssueChargesDO;
import com.dtdc.domain.purchase.GoodsIssueDO;
import com.dtdc.domain.purchase.GoodsIssueItemDtlsDO;
import com.dtdc.domain.purchase.GoodsRenewalDO;
import com.dtdc.domain.purchase.GoodsRenewalItemDtlsDO;
import com.dtdc.domain.purchase.GoodscCancellationDO;
import com.dtdc.domain.purchase.PurchasePaymentDtlsDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.DeliveryRunDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO;
import com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestHandOverDO;
import com.dtdc.domain.transaction.delivery.NonDeliveryRunDO;
import com.dtdc.domain.transaction.delivery.PODDeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO;

// TODO: Auto-generated Javadoc
/**
 * The Class DomainToTransferObjectConverter.
 *
 * @author nisahoo
 */
public class DomainToTransferObjectConverter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(DomainToTransferObjectConverter.class);

	/** The central data extractor dao. */
	private CentralDataExtractorDAO centralDataExtractorDAO;

	/**
	 * Gets the central data extractor dao.
	 *
	 * @return the central data extractor dao
	 */
	public CentralDataExtractorDAO getCentralDataExtractorDAO() {
		return centralDataExtractorDAO;
	}

	/**
	 * Sets the central data extractor dao.
	 *
	 * @param centralDataExtractorDAO the new central data extractor dao
	 */
	public void setCentralDataExtractorDAO(
			CentralDataExtractorDAO centralDataExtractorDAO) {
		this.centralDataExtractorDAO = centralDataExtractorDAO;
	}

	/**
	 * Created manifest to.
	 *
	 * @param manifestDOList the manifest do list
	 * @return the manifest data
	 * @throws CGBusinessException the cG business exception
	 */
	public ManifestData createdManifestTO(List<ManifestDO> manifestDOList)
			throws CGBusinessException {

		ManifestData manifestDataXMLTO = null;
		try {
			manifestDataXMLTO = new ManifestData();
			List<ManifestData.Manifest> manifestList = manifestDataXMLTO
					.getManifest();

			for (ManifestDO manifestDO : manifestDOList) {
				ManifestData.Manifest manifestRow = new ManifestData.Manifest();

				PropertyUtils.copyProperties(manifestRow, manifestDO);

				// Set Other Properties
				manifestRow.setMnfstDate(DateFormatterUtil
						.getDDMMYYYYDateToString(manifestDO.getManifestDate()));
				manifestRow.setHandoverDateStr(DateFormatterUtil
						.getDDMMYYYYDateToString(manifestDO.getHandoverDate()));

				if (manifestDO.getOriginBranch() != null) {
					manifestRow.setOriginBranchId(manifestDO.getOriginBranch()
							.getOfficeId());
					manifestRow.setOriginBranchCode(manifestDO
							.getOriginBranch().getOfficeCode());
				}

				if (manifestDO.getReportingBranch() != null) {
					manifestRow.setRepBranchId(manifestDO.getReportingBranch()
							.getOfficeId());
					manifestRow.setRepBranchCode(manifestDO
							.getReportingBranch().getOfficeCode());
				}

				if (manifestDO.getDestBranch() != null) {
					manifestRow.setDestBranchId(manifestDO.getDestBranch()
							.getOfficeId());
					manifestRow.setDestBranchCode(manifestDO.getDestBranch()
							.getOfficeCode());
				}

				if (manifestDO.getDestCity() != null) {
					manifestRow.setDestCityId(manifestDO.getDestCity()
							.getCityId());
					manifestRow.setDestCityCode(manifestDO.getDestCity()
							.getCityCode());
				}

				if (manifestDO.getMode() != null) {
					manifestRow.setModeId(manifestDO.getMode().getModeId());
					manifestRow.setModeCode(manifestDO.getMode().getModeCode());
				}

				if (manifestDO.getDestPinCode() != null) {
					manifestRow.setDestZipId(manifestDO.getDestPinCode()
							.getPincodeId());
					manifestRow.setDestZipCode(manifestDO.getDestPinCode()
							.getPincode());
				}

				if (manifestDO.getStdHandleInst() != null) {
					manifestRow.setHandleInstId(manifestDO.getStdHandleInst()
							.getHandleInstId());
					manifestRow.setHandleInstCode(manifestDO.getStdHandleInst()
							.getHandleInstCode());
				}

				if (manifestDO.getService() != null) {
					manifestRow.setServiceId(manifestDO.getService()
							.getServiceId());
					manifestRow.setServiceCode(manifestDO.getService()
							.getServiceCode());
				}

				if (manifestDO.getProduct() != null) {
					manifestRow.setProductId(manifestDO.getProduct()
							.getProductId());
					manifestRow.setProductCode(manifestDO.getProduct()
							.getProductCode());
				}

				if (manifestDO.getDocument() != null) {
					manifestRow.setDocumentId(manifestDO.getDocument()
							.getDocumentId());
					manifestRow.setDocumentCode(manifestDO.getDocument()
							.getDocumentCode());
				}

				if (manifestDO.getEmployee() != null) {
					manifestRow.setEmpId(manifestDO.getEmployee()
							.getEmployeeId());
					manifestRow.setEmployeeCode(manifestDO.getEmployee()
							.getEmpCode());
				}

				if (manifestDO.getMnsftTypes() != null) {
					manifestRow.setManifestTypeId(manifestDO.getMnsftTypes()
							.getMnfstTypeId());
					manifestRow.setManifestTypeCode(manifestDO.getMnsftTypes()
							.getMnfstCode());
				}

				if (manifestDO.getRecvBranch() != null) {
					manifestRow.setRecvOfficeId(manifestDO.getRecvBranch()
							.getOfficeId());
					manifestRow.setRecvOfficeCode(manifestDO.getRecvBranch()
							.getOfficeCode());
				}

				if (manifestDO.getDespRegBranch() != null) {
					manifestRow.setDespRegOfficeId(manifestDO
							.getDespRegBranch().getOfficeId());
					manifestRow.setDespRegOfficeCode(manifestDO
							.getDespRegBranch().getOfficeCode());
				}

				if (manifestDO.getDespBranch() != null) {
					manifestRow.setDespBranchId(manifestDO.getDespBranch()
							.getOfficeId());
					manifestRow.setDespBranchCode(manifestDO.getDespBranch()
							.getOfficeCode());
				}

				if (manifestDO.getFranchisee() != null) {
					manifestRow.setFranchiseeId(manifestDO.getFranchisee()
							.getFranchiseeId());
					manifestRow.setFranchiseeCode(manifestDO.getFranchisee()
							.getFranchiseeCode());
				}

				if (manifestDO.getCoLoader() != null) {
					manifestRow.setCoLoaderId(manifestDO.getCoLoader()
							.getLoaderId());
					manifestRow.setCoLoaderCode(manifestDO.getCoLoader()
							.getLoaderCode());
				}

				if (manifestDO.getAgent() != null) {
					manifestRow.setAgentId(manifestDO.getAgent().getAgentId());
					manifestRow.setAgentCode(manifestDO.getAgent()
							.getAgentCode());
				}

				if (manifestDO.getWayBillDetails() != null) {
					manifestRow.setWayBillDetailsId(manifestDO
							.getWayBillDetails().getWayBillId());
					manifestRow.setWayBillDetailsCode(manifestDO
							.getWayBillDetails().getWayBillNumber());
				}

				if (manifestDO.getUser() != null) {
					manifestRow.setUserId(manifestDO.getUser().getUserId());
					manifestRow.setUserCode(manifestDO.getUser().getUserCode());
				}

				if (manifestDO.getAirport() != null) {
					manifestRow.setAirportId(manifestDO.getAirport()
							.getAirportId());
					manifestRow.setAirportCode(manifestDO.getAirport()
							.getAirportCode());
				}

				if (manifestDO.getHeldupDO() != null) {
					manifestRow.setHeldupReleaseId(manifestDO.getHeldupDO()
							.getHeldupReleaseId());
				}

				manifestList.add(manifestRow);
			}
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter::createdManifestTO::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return manifestDataXMLTO;
	}

	/**
	 * Created held up release jax obj.
	 *
	 * @param heldUpReleaseList the held up release list
	 * @return the heldup release data
	 * @throws CGBusinessException the cG business exception
	 */
	public HeldupReleaseData createdHeldUpReleaseJaxObj(
			List<HeldUpReleaseDO> heldUpReleaseList) throws CGBusinessException {

		HeldupReleaseData heldupReleaseData = null;
		try {
			heldupReleaseData = new HeldupReleaseData();
			List<HeldupReleaseData.HeldupRelease> heldupReleaseDtlsList = heldupReleaseData
					.getHeldupRelease();

			for (HeldUpReleaseDO heldUpReleaseDo : heldUpReleaseList) {
				HeldupReleaseData.HeldupRelease parentTo = new HeldupReleaseData.HeldupRelease();
				PropertyUtils.copyProperties(parentTo, heldUpReleaseDo);
				parentTo.setCurDateTimes(DateFormatterUtil
						.convertDateToXMLGregorianCalendar(heldUpReleaseDo
								.getCurDateTime()));
				parentTo.setCurDateTimeStr(DateFormatterUtil
						.getDDMMYYYYDateToString(heldUpReleaseDo
								.getCurDateTime()));
				parentTo.setEmpId(heldUpReleaseDo.getEmployeeId() != null ? heldUpReleaseDo
						.getEmployeeId().getEmployeeId() : null);
				parentTo.setEmpCode(heldUpReleaseDo.getEmployeeId() != null ? heldUpReleaseDo
						.getEmployeeId().getEmpCode() : null);
				parentTo.setEntryOffId(heldUpReleaseDo.getEntryOfficeId() != null ? heldUpReleaseDo
						.getEntryOfficeId().getOfficeId() : null);
				parentTo.setEntryOffCode(heldUpReleaseDo.getEntryOfficeId() != null ? heldUpReleaseDo
						.getEntryOfficeId().getOfficeCode() : null);
				parentTo.setReceiveOffId(heldUpReleaseDo.getReceiveOfficeId() != null ? heldUpReleaseDo
						.getReceiveOfficeId().getOfficeId() : null);
				parentTo.setReceiveOffCode(heldUpReleaseDo.getReceiveOfficeId() != null ? heldUpReleaseDo
						.getReceiveOfficeId().getOfficeCode() : null);
				parentTo.setUserId(heldUpReleaseDo.getUserId());

				for (HeldUpReleaseItemsDtlsDO childDtls : heldUpReleaseDo
						.getItemDetailsSet()) {
					HeldupReleaseData.HeldupRelease.HeldupReleaseDtls childTo = new HeldupReleaseData.HeldupRelease.HeldupReleaseDtls();
					PropertyUtils.copyProperties(childTo, childDtls);
					childTo.setHeldupDates(DateFormatterUtil
							.convertDateToXMLGregorianCalendar(childDtls
									.getHeldupDate()));
					childTo.setHeldupDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(childDtls.getHeldupDate()));
					childTo.setHeldupId(childDtls.getHeldupDO()
							.getHeldupReleaseId());
					childTo.setHeldupNum(childDtls.getHeldupDO()
							.getHeldupNumber());
					childTo.setMnfstId(childDtls.getMnfstTypeId()!=null ? childDtls.getMnfstTypeId()
							.getMnfstTypeId():null);
					childTo.setMnfstCode(childDtls.getMnfstTypeId()!=null ? childDtls.getMnfstTypeId()
							.getMnfstCode():null);
					childTo.setReleaseDates(DateFormatterUtil
							.convertDateToXMLGregorianCalendar(childDtls
									.getReleaseDate()));
					childTo.setReleaseDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(childDtls.getReleaseDate()));
					childTo.setHeldupRsnId(childDtls.getHeldupReasonId()!=null ?childDtls.getHeldupReasonId()
							.getReasonId():null);
					childTo.setHeldupRsnCode(childDtls.getHeldupReasonId()!=null ?childDtls.getHeldupReasonId()
							.getReasonCode():null);
					childTo.setTransCreateDates(DateFormatterUtil
							.convertDateToXMLGregorianCalendar(childDtls
									.getTransCreateDate()));
					childTo.setTransCreateDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(childDtls
									.getTransCreateDate()));
					childTo.setTransLastModifiedDates(DateFormatterUtil
							.convertDateToXMLGregorianCalendar(childDtls
									.getTransLastModifiedDate()));
					childTo.setTransLastModifiedDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(childDtls
									.getTransLastModifiedDate()));
					childTo.setStorageLocationId(childDtls.getStorageLocId() != null ? childDtls
							.getStorageLocId().getWarehouseId() : null);
					childTo.setStorageLocationCode(childDtls.getStorageLocId() != null ? childDtls
							.getStorageLocId().getWarehouseCode() : null);
					childTo.setTransLocId(childDtls.getTransLocationId() != null ? childDtls
							.getTransLocationId().getOfficeId() : null);
					childTo.setTransLocCode(childDtls.getTransLocationId() != null ? childDtls
							.getTransLocationId().getOfficeCode() : null);
					parentTo.getHeldupReleaseDtls().add(childTo);
				}
				heldupReleaseDtlsList.add(parentTo);
			}
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter::createdHeldUpReleaseJaxObj::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return heldupReleaseData;
	}

	/**
	 * Created dispatch jax obj.
	 *
	 * @param dispatchDOList the dispatch do list
	 * @return the dispatch details data
	 * @throws CGBusinessException the cG business exception
	 */
	public DispatchDetailsData createdDispatchJaxObj(
			List<DispatchDO> dispatchDOList) throws CGBusinessException {
		LOGGER.info("DomainToTransferObjectConverter : createdDispatchJaxObj() : START...");

		DispatchDetailsData jaxbObj = new DispatchDetailsData();
		try {
			if (dispatchDOList != null && !dispatchDOList.isEmpty()) {
				for (DispatchDO dispatchDO : dispatchDOList) {

					DispatchDetailsData.DispatchDetails dispatchDetailsData = new DispatchDetailsData.DispatchDetails();
					// PARENT Dispatch DETAILS
					PropertyUtils.copyProperties(dispatchDetailsData,
							dispatchDO);

					dispatchDetailsData
							.setAirportId(dispatchDO.getAirportDO() != null ? dispatchDO
									.getAirportDO().getAirportId() : null);
					dispatchDetailsData
							.setBillingId(dispatchDO.getBillingDO() != null ? dispatchDO
									.getBillingDO().getBillingId() : null);
					dispatchDetailsData.setColoaderId(dispatchDO
							.getColoaderDO() != null ? dispatchDO
							.getColoaderDO().getLoaderId() : null);
					dispatchDetailsData.setOriginOfficeId(dispatchDO
							.getOriginOfficeDO() != null ? dispatchDO
							.getOriginOfficeDO().getOfficeId() : null);
					dispatchDetailsData.setDestOfficeId(dispatchDO
							.getDestOfficeDO() != null ? dispatchDO
							.getDestOfficeDO().getOfficeId() : null);
					dispatchDetailsData
							.setModeId(dispatchDO.getModeDO() != null ? dispatchDO
									.getModeDO().getModeId() : null);
					dispatchDetailsData
							.setVehicalId(dispatchDO.getVehicalDO() != null ? dispatchDO
									.getVehicalDO().getVehicleId() : null);
					dispatchDetailsData
							.setTrainId(dispatchDO.getTrainDO() != null ? dispatchDO
									.getTrainDO().getTrainId() : null);
					dispatchDetailsData.setLoadSentEmpId(dispatchDO
							.getLoadSentEmpDO() != null ? dispatchDO
							.getLoadSentEmpDO().getEmployeeId() : null);
					dispatchDetailsData.setDirectEmpId(dispatchDO
							.getDirectEmpDO() != null ? dispatchDO
							.getDirectEmpDO().getEmployeeId() : null);
					dispatchDetailsData
							.setVendorId(dispatchDO.getVendorDO() != null ? dispatchDO
									.getVendorDO().getVendorId() : null);
					dispatchDetailsData
							.setUserId(dispatchDO.getUserDo() != null ? dispatchDO
									.getUserDo().getUserId() : null);
					dispatchDetailsData.setLoadSentDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(dispatchDO
									.getLoadSentDate()));
					dispatchDetailsData.setCdDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(dispatchDO.getCdDate()));

					// CHILD Dispatch Bag Manifest ITEM DETAILS
					for (DispatchBagManifestDO dispatchBagManifestDO : dispatchDO
							.getDispatchBagManifestDOList()) {
						DispatchDetailsData.DispatchDetails.DispatchBagManifestDetailsList dispatchBagManifestDetailsData = new DispatchDetailsData.DispatchDetails.DispatchBagManifestDetailsList();

						PropertyUtils.copyProperties(
								dispatchBagManifestDetailsData,
								dispatchBagManifestDO);

						dispatchBagManifestDetailsData
								.setAirportId(dispatchBagManifestDO
										.getAirportDO() != null ? dispatchBagManifestDO
										.getAirportDO().getAirportId() : null);
						dispatchBagManifestDetailsData
								.setDispatchId(dispatchBagManifestDO
										.getDispatchDO() != null ? dispatchBagManifestDO
										.getDispatchDO().getDispatchId() : null);
						dispatchBagManifestDetailsData
								.setManifestId(dispatchBagManifestDO
										.getManifestDO() != null ? dispatchBagManifestDO
										.getManifestDO().getManifestId() : null);

						dispatchBagManifestDetailsData
								.setBillingId(dispatchBagManifestDO
										.getBillingDO() != null ? dispatchBagManifestDO
										.getBillingDO().getBillingId() : null);
						dispatchBagManifestDetailsData
								.setColoaderId(dispatchBagManifestDO
										.getColoaderDO() != null ? dispatchBagManifestDO
										.getColoaderDO().getLoaderId() : null);

						dispatchBagManifestDetailsData
								.setModeId(dispatchBagManifestDO.getModeDO() != null ? dispatchBagManifestDO
										.getModeDO().getModeId() : null);
						dispatchBagManifestDetailsData
								.setVehicalId(dispatchBagManifestDO
										.getVehicalDO() != null ? dispatchBagManifestDO
										.getVehicalDO().getVehicleId() : null);
						dispatchBagManifestDetailsData
								.setTrainId(dispatchBagManifestDO.getTrainDO() != null ? dispatchBagManifestDO
										.getTrainDO().getTrainId() : null);

						dispatchBagManifestDetailsData
								.setDirectEmpId(dispatchBagManifestDO
										.getDirectEmpDO() != null ? dispatchBagManifestDO
										.getDirectEmpDO().getEmployeeId()
										: null);
						dispatchBagManifestDetailsData
								.setVendorId(dispatchBagManifestDO
										.getVendorDO() != null ? dispatchBagManifestDO
										.getVendorDO().getVendorId() : null);
						dispatchBagManifestDetailsData
								.setCreatedDateStr(DateFormatterUtil
										.getDDMMYYYYDateToString(dispatchBagManifestDO
												.getCreatedDate()));
						dispatchBagManifestDetailsData
								.setCdDateStr(DateFormatterUtil
										.getDDMMYYYYDateToString(dispatchBagManifestDO
												.getCdDate()));

						dispatchDetailsData.getDispatchBagManifestDetailsList()
								.add(dispatchBagManifestDetailsData);
					}
					// ADD in Parent List
					jaxbObj.getDispatchDetails().add(dispatchDetailsData);
				}
			}
			LOGGER.info("DomainToTransferObjectConverter : createdGoodsCanclJaxObj() : END...");
		} catch (Exception ex) {
			LOGGER.error("DomainToTransferObjectConverter::createdDispatchJaxObj::Exception occured:"
					+ex.getMessage());
		}

		return jaxbObj;
	}

	/**
	 * Created goods cancl jax obj.
	 * 
	 * @param goodsCanclDOList
	 *            the goods cancl do list
	 * @return the goods cancellation data
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public GoodsCancellationData createdGoodsCanclJaxObj(
			List<GoodscCancellationDO> goodsCanclDOList)
			throws CGBusinessException {
		LOGGER.info("DomainToTransferObjectConverter : createdGoodsCanclJaxObj() : START...");

		GoodsCancellationData jaxbObj = new GoodsCancellationData();
		try {
			if (goodsCanclDOList != null && !goodsCanclDOList.isEmpty()) {
				for (GoodscCancellationDO goodsCanclDO : goodsCanclDOList) {
					GoodsCancellationData.GoodsCancellation goodsCanclData = new GoodsCancellationData.GoodsCancellation();
					// PARENT GOODS CANCL DETAILS
					PropertyUtils.copyProperties(goodsCanclData, goodsCanclDO);

					goodsCanclData.setCanclApprEmpId(goodsCanclDO
							.getCanclApproverEmpId() != null ? goodsCanclDO
							.getCanclApproverEmpId().getEmployeeId() : null);
					goodsCanclData.setCanclEmployeeId(goodsCanclDO
							.getCanclEmpId() != null ? goodsCanclDO
							.getCanclEmpId().getEmployeeId() : null);
					goodsCanclData.setCanclDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(goodsCanclDO
									.getCanclDate()));
					goodsCanclData.setCanclOffId(goodsCanclDO
							.getCanclOfficeId() != null ? goodsCanclDO
							.getCanclOfficeId().getOfficeId() : null);

					goodsCanclData.setReturnPartyTyId(goodsCanclDO
							.getReturnPartyTypeId() != null ? goodsCanclDO
							.getReturnPartyTypeId().getPartyId() : null);
					goodsCanclData.setReturnFranchiseeId(goodsCanclDO
							.getReturnFRId() != null ? goodsCanclDO
							.getReturnFRId().getFranchiseeId() : null);
					goodsCanclData.setReturnCustomerId(goodsCanclDO
							.getReturnCustId() != null ? goodsCanclDO
							.getReturnCustId().getCustomerId() : null);
					goodsCanclData.setReturnCCCOfficeId(goodsCanclDO
							.getReturnCCCOffId() != null ? goodsCanclDO
							.getReturnCCCOffId().getOfficeId() : null);
					goodsCanclData.setReturnEmployeeId(goodsCanclDO
							.getReturnEmpId() != null ? goodsCanclDO
							.getReturnEmpId().getEmployeeId() : null);
					goodsCanclData
							.setUserID(goodsCanclDO.getUserId() != null ? goodsCanclDO
									.getUserId().getUserId() : null);

					// CHILD GOODS CANCL ITEM DETAILS
					for (GoodsCanclItemDtlsDO goodsCanclItemDO : goodsCanclDO
							.getGoodsCanclItemDtls()) {
						GoodsCancellationData.GoodsCancellation.GoodsCanclitemDtlsList goodsCanclItemData = new GoodsCancellationData.GoodsCancellation.GoodsCanclitemDtlsList();

						PropertyUtils.copyProperties(goodsCanclItemData,
								goodsCanclItemDO);

						goodsCanclItemData.setIssuedOffId(goodsCanclItemDO
								.getIssuedOfficeId() != null ? goodsCanclItemDO
								.getIssuedOfficeId().getOfficeId() : null);
						goodsCanclItemData.setIssuedOffCode(goodsCanclItemDO
								.getIssuedOfficeId() != null ? goodsCanclItemDO
								.getIssuedOfficeId().getOfficeCode() : null);
						goodsCanclItemData.setCancelReaonId(goodsCanclItemDO
								.getCanclReaonId() != null ? goodsCanclItemDO
								.getCanclReaonId().getReasonId() : null);

						goodsCanclItemData.setItemID(goodsCanclItemDO
								.getItemId() != null ? goodsCanclItemDO
								.getItemId().getItemId() : null);
						goodsCanclItemData.setIssueDateStr(DateFormatterUtil
								.getDDMMYYYYDateToString(goodsCanclItemDO
										.getIssueDate()));

						goodsCanclData.getGoodsCanclitemDtlsList().add(
								goodsCanclItemData);
					}
					// ADD in Parent List
					jaxbObj.getGoodsCancellation().add(goodsCanclData);
				}
			}
			LOGGER.info("DomainToTransferObjectConverter : createdGoodsCanclJaxObj() : END...");
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter::createdGoodsCanclJaxObj::Exception occured:"
					+e.getMessage());
		}

		return jaxbObj;
	}

	/**
	 * Created goods renewal jax obj.
	 * 
	 * @param goodsRenewalDOList
	 *            the goods renewal do list
	 * @return the goods renewal data
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public GoodsRenewalData createdGoodsRenewalJaxObj(
			List<GoodsRenewalDO> goodsRenewalDOList) throws CGBusinessException {
		LOGGER.debug("DomainToTransferObjectConverter : createdGoodsRenewalJaxObj() : START...");

		GoodsRenewalData jaxbObj = new GoodsRenewalData();
		try {
			if (goodsRenewalDOList != null && !goodsRenewalDOList.isEmpty()) {
				for (GoodsRenewalDO goodsRenewalDO : goodsRenewalDOList) {

					GoodsRenewalData.GoodsRenewal goodsRenewalData = new GoodsRenewalData.GoodsRenewal();
					// PARENT GOODS CANCL DETAILS
					PropertyUtils.copyProperties(goodsRenewalData,
							goodsRenewalDO);

					goodsRenewalData.setGoodsRenewalDateStr(DateFormatterUtil
							.getDDMMYYYYDateToString(goodsRenewalDO
									.getGoodsRenewalDate()));

					goodsRenewalData
							.setCustId(goodsRenewalDO.getCustomerId() != null ? goodsRenewalDO
									.getCustomerId().getCustomerId() : null);
					goodsRenewalData.setCustomerCode(goodsRenewalDO
							.getCustomerId() != null ? goodsRenewalDO
							.getCustomerId().getCustomerCode() : null);

					goodsRenewalData.setRenewingOffId(goodsRenewalDO
							.getRenewingOfficeId() != null ? goodsRenewalDO
							.getRenewingOfficeId().getOfficeId() : null);
					goodsRenewalData.setRenewingOfficeCode(goodsRenewalDO
							.getRenewingOfficeId() != null ? goodsRenewalDO
							.getRenewingOfficeId().getOfficeCode() : null);

					goodsRenewalData.setCccOffId(goodsRenewalDO
							.getCccOfficeId() != null ? goodsRenewalDO
							.getCccOfficeId().getOfficeId() : null);
					goodsRenewalData.setCccOfficeCode(goodsRenewalDO
							.getCccOfficeId() != null ? goodsRenewalDO
							.getCccOfficeId().getOfficeCode() : null);

					goodsRenewalData
							.setFrId(goodsRenewalDO.getFranchiseId() != null ? goodsRenewalDO
									.getFranchiseId().getFranchiseeId() : null);
					goodsRenewalData.setFranchiseCode(goodsRenewalDO
							.getFranchiseId() != null ? goodsRenewalDO
							.getFranchiseId().getFranchiseeCode() : null);

					goodsRenewalData
							.setPartyId(goodsRenewalDO.getPartyDo() != null ? goodsRenewalDO
									.getPartyDo().getPartyId() : null);
					goodsRenewalData
							.setPartyCode(goodsRenewalDO.getPartyDo() != null ? goodsRenewalDO
									.getPartyDo().getPartyCode() : null);

					goodsRenewalData
							.setEmpId(goodsRenewalDO.getEmployeeId() != null ? goodsRenewalDO
									.getEmployeeId().getEmployeeId() : null);
					goodsRenewalData.setEmployeeCode(goodsRenewalDO
							.getEmployeeId() != null ? goodsRenewalDO
							.getEmployeeId().getEmpCode() : null);

					goodsRenewalData
							.setUserID(goodsRenewalDO.getUserId() != null ? goodsRenewalDO
									.getUserId().getUserId() : null);

					// CHILD GOODS CANCL ITEM DETAILS
					for (GoodsRenewalItemDtlsDO goodsRenewalItemDO : goodsRenewalDO
							.getGoodsRenewalItmDtls()) {
						GoodsRenewalData.GoodsRenewal.GoodsRenewalitemDtlsList goodsRenewalItemData = new GoodsRenewalData.GoodsRenewal.GoodsRenewalitemDtlsList();

						PropertyUtils.copyProperties(goodsRenewalItemData,
								goodsRenewalItemDO);

						goodsRenewalItemData.setItemDOId(goodsRenewalItemDO
								.getItemDO() != null ? goodsRenewalItemDO
								.getItemDO().getItemId() : null);
						goodsRenewalItemData.setIssueDateStr(DateFormatterUtil
								.getDDMMYYYYDateToString(goodsRenewalItemDO
										.getIssueDate()));

						goodsRenewalData.getGoodsRenewalitemDtlsList().add(
								goodsRenewalItemData);
					}
					// ADD in Parent List
					jaxbObj.getGoodsRenewal().add(goodsRenewalData);
				}
			}
			LOGGER.info("DomainToTransferObjectConverter : createdGoodsRenewalJaxObj() : END...");
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter::createdGoodsRenewalJaxObj::Exception occured:"
					+e.getMessage());
		}

		return jaxbObj;
	}

	/**
	 * Created goods issue jax obj.
	 *
	 * @param goodsIssueDoList the goods issue do list
	 * @return the goods issue data
	 * @throws CGBusinessException the cG business exception
	 */
	public GoodsIssueData createdGoodsIssueJaxObj(
			List<GoodsIssueDO> goodsIssueDoList) throws CGBusinessException {
		LOGGER.debug("DomainToTransferObjectConverter : createdGoodsIssueJaxObj() : START...");
		com.dtdc.bodbadmin.schema.purchase.goodsIssue.ObjectFactory goodsIssue = new com.dtdc.bodbadmin.schema.purchase.goodsIssue.ObjectFactory();
		GoodsIssueData goodsIssueData = goodsIssue.createGoodsIssueData();
		try {
			for (GoodsIssueDO issueDo : goodsIssueDoList) {
				GoodsIssueData.GoodsIssue parentTo = new GoodsIssueData.GoodsIssue();
				PropertyUtils.copyProperties(parentTo, issueDo);
				parentTo.setDateOfPayments(StringUtil.isNull(issueDo
						.getDateOfPayment()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getDateOfPayment()));
				parentTo.setGoodsIssueId(null);
				parentTo.setIssueDocumentDates(StringUtil.isNull(issueDo
						.getIssueDocumentDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getIssueDocumentDate()));
				parentTo.setReceiptDocumentDates(StringUtil.isNull(issueDo
						.getReceiptDocumentDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getReceiptDocumentDate()));
				parentTo.setReqSlipDates(StringUtil.isNull(issueDo
						.getReqSlipDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getReqSlipDate()));
				parentTo.setStartDocumentDates(StringUtil.isNull(issueDo
						.getStartDocumentDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getStartDocumentDate()));
				parentTo.setTransactionCrDates(StringUtil.isNull(issueDo
						.getTransactionCrDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(issueDo
								.getTransactionCrDate()));
				parentTo.setTransactionLastMdfDates(StringUtil.isNull(issueDo
						.getTransactionLastMdfDate()) ? null
						: DateFormatterUtil
								.convertDateToXMLGregorianCalendar(issueDo
										.getTransactionLastMdfDate()));
				parentTo.setApprvrId(StringUtil.isNull(issueDo
						.getApproverEmpId()) ? null : issueDo
						.getApproverEmpId().getEmployeeId());
				parentTo.setApprvrCode(StringUtil.isNull(issueDo
						.getApproverEmpId()) ? null : issueDo
						.getApproverEmpId().getEmpCode());
				parentTo.setFrId(StringUtil.isNull(issueDo.getFranchisee()) ? null
						: issueDo.getFranchisee().getFranchiseeId());
				parentTo.setFrCode(StringUtil.isNull(issueDo.getFranchisee()) ? null
						: issueDo.getFranchisee().getFranchiseeCode());
				parentTo.setIssOffId(StringUtil.isNull(issueDo
						.getIssuingPlant()) ? null : issueDo.getIssuingPlant()
						.getOfficeId());
				parentTo.setIssOffCode(StringUtil.isNull(issueDo
						.getIssuingPlant()) ? null : issueDo.getIssuingPlant()
						.getOfficeCode());
				parentTo.setIssPersonId(StringUtil.isNull(issueDo
						.getIssuingPerson()) ? null : issueDo
						.getIssuingPerson().getEmployeeId());
				parentTo.setIssPersonCode(StringUtil.isNull(issueDo
						.getIssuingPerson()) ? null : issueDo
						.getIssuingPerson().getEmpCode());
				parentTo.setRecpntId(StringUtil.isNull(issueDo
						.getReceipientId()) ? null : issueDo.getReceipientId()
						.getEmployeeId());
				parentTo.setRecpntCode(StringUtil.isNull(issueDo
						.getReceipientId()) ? null : issueDo.getReceipientId()
						.getEmpCode());
				parentTo.setRecvngCustId(StringUtil.isNull(issueDo
						.getReceivingCustomerId()) ? null : issueDo
						.getReceivingCustomerId().getCustomerId());
				parentTo.setRecvngCustCode(StringUtil.isNull(issueDo
						.getReceivingCustomerId()) ? null : issueDo
						.getReceivingCustomerId().getCustomerCode());
				parentTo.setRecvngOffId(StringUtil.isNull(issueDo
						.getReceivingPlantId()) ? null : issueDo
						.getReceivingPlantId().getOfficeId());
				parentTo.setRecvngOffCode(StringUtil.isNull(issueDo
						.getReceivingPlantId()) ? null : issueDo
						.getReceivingPlantId().getOfficeCode());
				parentTo.setRecvngPersonId(StringUtil.isNull(issueDo
						.getReceivingPersonId()) ? null : issueDo
						.getReceivingPersonId().getEmployeeId());
				parentTo.setRecvngPersonCode(StringUtil.isNull(issueDo
						.getReceivingPersonId()) ? null : issueDo
						.getReceivingPersonId().getEmpCode());
				parentTo.setUsrId(StringUtil.isNull(issueDo.getUserDo()) ? null
						: issueDo.getUserDo().getUserId());
				parentTo.setUsrCode(StringUtil.isNull(issueDo.getUserDo()) ? null
						: issueDo.getUserDo().getUserCode());
				
				if(!StringUtil.isNull(issueDo.getWarehouseDo())){
				parentTo.setWarehouseId(issueDo.getWarehouseDo().getWarehouseId());
				parentTo.setWarehouseCode(issueDo.getWarehouseDo().getWarehouseCode());
				}
				
				for (GoodsIssueItemDtlsDO issueDtlsDo : issueDo
						.getGoodsIssueItems()) {
					GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls childTo = new GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls();
					PropertyUtils.copyProperties(childTo, issueDtlsDo);
					childTo.setGoodsIssueItemDtlsId(null);
					childTo.setItemExpiryDates(StringUtil.isNull(issueDtlsDo
							.getItemExpiryDate()) ? null : DateFormatterUtil
							.convertDateToXMLGregorianCalendar(issueDtlsDo
									.getItemExpiryDate()));
					childTo.setTransCreateDates(StringUtil.isNull(issueDtlsDo
							.getTransCreateDate()) ? null : DateFormatterUtil
							.convertDateToXMLGregorianCalendar(issueDtlsDo
									.getTransCreateDate()));
					childTo.setTransLastModifiedDates(StringUtil
							.isNull(issueDtlsDo.getTransLastModifiedDate()) ? null
							: DateFormatterUtil
									.convertDateToXMLGregorianCalendar(issueDtlsDo
											.getTransLastModifiedDate()));
					childTo.setUsrId(StringUtil.isNull(issueDtlsDo.getUserId()) ? null
							: issueDtlsDo.getUserId().getUserId());
					childTo.setUsrCode(StringUtil.isNull(issueDtlsDo
							.getUserId()) ? null : issueDtlsDo.getUserId()
							.getUserCode());
					childTo.setRecvngOffId(StringUtil.isNull(issueDtlsDo
							.getReceivingPlantId()) ? null : issueDtlsDo
							.getReceivingPlantId().getOfficeId());
					childTo.setRecvngOffCode(StringUtil.isNull(issueDtlsDo
							.getReceivingPlantId()) ? null : issueDtlsDo
							.getReceivingPlantId().getOfficeCode());
					for (GoodsIssueChargesDO issueChargesDo : issueDtlsDo
							.getChargesDo()) {
						GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls.GoodsIssueChargesDtls grandChilTo = new GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls.GoodsIssueChargesDtls();
						PropertyUtils.copyProperties(grandChilTo,
								issueChargesDo);
						grandChilTo.setGoodsIssueChargesId(null);
						grandChilTo.setGoodsIssueItemDtlsId(null);
						grandChilTo
								.setTransCreateDates(StringUtil
										.isNull(issueChargesDo
												.getTransCreateDate()) ? null
										: DateFormatterUtil
												.convertDateToXMLGregorianCalendar(issueChargesDo
														.getTransCreateDate()));
						grandChilTo
								.setTransLastModifiedDates(StringUtil
										.isNull(issueChargesDo
												.getTransLastModifiedDate()) ? null
										: DateFormatterUtil
												.convertDateToXMLGregorianCalendar(issueChargesDo
														.getTransLastModifiedDate()));
						grandChilTo.setUsrId(StringUtil.isNull(issueChargesDo
								.getUserdo()) ? null : issueChargesDo
								.getUserdo().getUserId());
						grandChilTo.setUsrCode(StringUtil.isNull(issueChargesDo
								.getUserdo()) ? null : issueChargesDo
								.getUserdo().getUserCode());
						childTo.getGoodsIssueChargesDtls().add(grandChilTo);
					}

					parentTo.getGoodsIssueItmsDtls().add(childTo);
				}

				for (PurchasePaymentDtlsDO paymntDo : issueDo.getPaymentDetls()) {
					GoodsIssueData.GoodsIssue.GoodsIssuePaymntDtls child2To = new GoodsIssueData.GoodsIssue.GoodsIssuePaymntDtls();
					PropertyUtils.copyProperties(child2To, paymntDo);
					child2To.setPaymentDtlsId(null);
					child2To.setGoodsIssId(null);
					child2To.setDateOfPayments(StringUtil.isNull(paymntDo
							.getDateOfPayment()) ? null : DateFormatterUtil
							.convertDateToXMLGregorianCalendar(paymntDo
									.getDateOfPayment()));
					child2To.setTransCreateDates(StringUtil.isNull(paymntDo
							.getTransactionCrDate()) ? null : DateFormatterUtil
							.convertDateToXMLGregorianCalendar(paymntDo
									.getTransactionCrDate()));
					child2To.setTransLastModifiedDates(StringUtil
							.isNull(paymntDo.getTransactionLastMdfDate()) ? null
							: DateFormatterUtil
									.convertDateToXMLGregorianCalendar(paymntDo
											.getTransactionLastMdfDate()));
					child2To.setUsrId(StringUtil.isNull(paymntDo.getUserDo()) ? null
							: paymntDo.getUserDo().getUserId());
					child2To.setUsrCode(StringUtil.isNull(paymntDo.getUserDo()) ? null
							: paymntDo.getUserDo().getUserCode());
					if(!StringUtil.isNull(paymntDo.getBankDO())){
						child2To.setBankId(paymntDo.getBankDO().getBankId());
						child2To.setBankCode(paymntDo.getBankDO().getBankCode());
						child2To.setBankName(paymntDo.getBankDO().getBankName());
						
					}
					parentTo.getGoodsIssuePaymntDtls().add(child2To);
				}
				goodsIssueData.getGoodsIssue().add(parentTo);
			}
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter : createdGoodsIssueJaxObj() :Exception Happened:"
					+ e.getStackTrace());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("DomainToTransferObjectConverter : createdGoodsIssueJaxObj() : END...");
		return goodsIssueData;
	}

	/**
	 * Created booking jax obj.
	 *
	 * @param bookingDOList the booking do list
	 * @return the booking details data
	 * @throws CGBusinessException the cG business exception
	 */
	public BookingDetailsData createdBookingJaxObj(List<BookingDO> bookingDOList)
	throws CGBusinessException {
		LOGGER.debug("DomainToJaxBConverter : createdBookingJaxObj() : START...");
		LOGGER.debug("DomainToJaxBConverter : createdBookingJaxObj() : input booking Size..."+bookingDOList!=null && !bookingDOList.isEmpty()? bookingDOList.size():null);
		BookingDetailsData bookingDetailsData = null;
		com.dtdc.bodbadmin.schema.booking.ObjectFactory bookingObjFactory = new  com.dtdc.bodbadmin.schema.booking.ObjectFactory ();
		try {
			bookingDetailsData =bookingObjFactory.createBookingDetailsData();
			for (BookingDO bookingDO : bookingDOList) {
				BookingDetails parentTo = new BookingDetails();

				getBookingHeaderDtls(bookingDO, parentTo);

				if (bookingDO instanceof FranchiseeBookingDO) {
					parentTo.setBookingType(CentralDataExtractorConstant.BOOKING_TYPE_FRANCHISEE_BOOKING);
					FranchiseeBookingDO frBookingDO = (FranchiseeBookingDO) bookingDO;
						parentTo.setFranchiseeIdValue(frBookingDO.getFranchiseeId() != null ?frBookingDO.getFranchiseeId()
								.getFranchiseeId():null);
						parentTo.setFranchMinfNumber(frBookingDO.getFranchiseeManifestNo() != null ? frBookingDO
								.getFranchiseeManifestNo():null);
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is of FRBooking");

				} else if (bookingDO instanceof CashBookingDO) {
					parentTo.setBookingType(CentralDataExtractorConstant.BOOKING_TYPE_CASH_BOOKING);

					CashBookingDO cashBookingDO = (CashBookingDO) bookingDO;
					parentTo.setDiscount(cashBookingDO.getDiscount());

					parentTo.setAdditionalCharge(cashBookingDO
							.getAdditionalCharge());
					parentTo.setFuelSurcharge(cashBookingDO.getFuelSurcharge());
					parentTo.setTaxableAmount(cashBookingDO.getTaxableAmount());
					parentTo.setCommercialTax(cashBookingDO.getCommercialTax());
					parentTo.setCessTax(cashBookingDO.getCessTax());
					parentTo.setEducationalTax(cashBookingDO
							.getEducationalTax());
					parentTo.setTaxPayable(cashBookingDO.getTaxPayable());
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is of CASH Booking");
				} else if (bookingDO instanceof DirectPartyBookingDO) {
					parentTo.setBookingType(CentralDataExtractorConstant.BOOKING_TYPE_DIRECT_PARTY_BOOKING);
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is of DP Booking");
				}
				ConsigneeDO childDtls = bookingDO.getConsigneeID();
				if (childDtls != null) {
					ConsigneeDtls consigneeTo = new ConsigneeDtls();
					PropertyUtils.copyProperties(consigneeTo, childDtls);
					if(childDtls.getAddresses()!=null){
						Iterator addItr = childDtls.getAddresses().iterator();
						while (addItr.hasNext()) {
							ConsigneeAddressList  consineeAddress = new ConsigneeAddressList();
							ConsigneeAddressDO consigneeAddDo = (ConsigneeAddressDO) addItr
							.next();

							PropertyUtils.copyProperties(consineeAddress, consigneeAddDo);
							consineeAddress.setAreaIdValue(consigneeAddDo.getAreaDO() != null ? consigneeAddDo.getAreaDO().getAreaId() : null);
							consineeAddress.setCountryIdValue(consigneeAddDo.getCountry() != null ? consigneeAddDo.getCountry().getCountryId() : null);
							consineeAddress.setCityIdValue(consigneeAddDo.getCity() != null ? consigneeAddDo.getCity().getCityId() : null);
							consineeAddress.setStateIdValue(consigneeAddDo.getState() != null ? consigneeAddDo.getState().getStateId() : null);
							
							consigneeTo.getConsigneeAddressList().add(consineeAddress);
						}
						parentTo.setConsigneeDtls(consigneeTo);
					}
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is having ConsigneeDO childDtls");


				}else{
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is not having ConsigneeDO childDtls");
				}

				ConsignerInfoDO conschildDtls = bookingDO.getConsignorID();
				if (conschildDtls != null) {
					ConsignerDetails consignorTo = new ConsignerDetails();

					PropertyUtils.copyProperties(consignorTo, childDtls);

					if(conschildDtls.getConsignorAddresses()!=null){
						Iterator addItr1 = conschildDtls.getConsignorAddresses()
						.iterator();

						while (addItr1.hasNext()) {
							ConsignorAddressDtls consignerAddress = new ConsignorAddressDtls();
							ConsignerAddressDO consignerAddDo = (ConsignerAddressDO) addItr1
							.next();
							PropertyUtils.copyProperties(consignerAddress, consignerAddDo);
							consignerAddress.setConsignerPincodeId(consignerAddDo.getPincodeId() != null ?consignerAddDo.getPincodeId().getPincodeId() : null);
							consignerAddress.setConsignerAreaId(consignerAddDo.getAreaDO() != null ?consignerAddDo.getAreaDO().getAreaId() : null);
							consignerAddress.setConsignerCountryId(consignerAddDo.getCountry() != null ?consignerAddDo.getCountry().getCountryId() :null);
							consignerAddress.setConsignerStateId(consignerAddDo.getState() != null ?consignerAddDo.getState().getStateId() :null);
							consignerAddress.setConsigrCityIdValue( consignerAddDo.getCity()!= null ?consignerAddDo.getCity().getCityId() :null);
							consignorTo.getConsignorAddressDtls().add(consignerAddress);
						}

					}
					parentTo.setConsignerDetails(consignorTo);
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is having conschildDtls");
				}else {
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is not having conschildDtls");
				}
				
				if(bookingDO.getMnpItems()!=null && !bookingDO.getMnpItems().isEmpty()){
					Iterator bookingMnps = bookingDO.getMnpItems().iterator();

					while (bookingMnps.hasNext()) {
						BookingItemListDO bookingItemListDo = (BookingItemListDO)bookingMnps.next();
						BookingMnpItems manpItems =new BookingMnpItems();
						PropertyUtils.copyProperties(manpItems,
								bookingItemListDo);
						manpItems.setBookingItemlistId(null);
						manpItems.setCommodityIdValue(bookingItemListDo.getCommodity() != null ? bookingItemListDo.getCommodity().getCommodityId() : null);
						parentTo.getBookingMnpItems().add(manpItems);
					}

					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is having mnpItems");
				}//end of getMnpItems
				else {
					LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO is not having mnpItems");
				}
				// VAS needs to be implemented
				
				
				if(bookingDO.getNonDoxPaperWrkItems()!=null && !bookingDO.getNonDoxPaperWrkItems().isEmpty()){
					Iterator itr =  bookingDO.getNonDoxPaperWrkItems().iterator();
					while (itr.hasNext()) {
						BookingItemListDO bookingItemListDo = (BookingItemListDO)itr.next();
						BookingNonDoxPaperWrkItems paperWorkItemsForXML = new BookingNonDoxPaperWrkItems();
						PropertyUtils.copyProperties(paperWorkItemsForXML,
								bookingItemListDo);
						bookingItemListDo.setBookingItemlistId(null);
						paperWorkItemsForXML.setPaperWorkItemIdValue(bookingItemListDo.getPaperWorkItemDO() != null ? bookingItemListDo.getPaperWorkItemDO().getPaperWorkItemId() : null);
						parentTo.getBookingNonDoxPaperWrkItems().add(paperWorkItemsForXML);
					}



				}//end of getNonDoxPaperWrkItems
				
				if(bookingDO.getVasCharges()!=null && !bookingDO.getVasCharges().isEmpty()){
				for (VasProductChargesDO chrgsDo : bookingDO.getVasCharges()){
						BookingVasChrgesItems vaschrges = new BookingVasChrgesItems();
						PropertyUtils.copyProperties(vaschrges,
								chrgsDo);
						vaschrges.setBookingIdValue(null);
						vaschrges.setBookingVasChargeId(null);
						parentTo.getBookingVasChrgesItems().add(vaschrges);
					}
				}//end of VasProductChargesDO
				bookingDetailsData.getBookingDetails().add(parentTo);
				LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : bookingDO After getBookingDetails Add Process");
			}

		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter::createdBookingJaxObj::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		LOGGER.debug("DomainToJaxBConverter : createdBookingJaxObj() : END...");
		return bookingDetailsData;
	}

	/**
	 * Gets the booking header dtls.
	 *
	 * @param bookingDO the booking do
	 * @param parentTo the parent to
	 * @return the booking header dtls
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	private void getBookingHeaderDtls(BookingDO bookingDO,
			BookingDetails parentTo) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		PropertyUtils.copyProperties(parentTo, bookingDO);
		parentTo.setBookingId(null);
		// Set Other Properties
		parentTo.setBookingDates(DateFormatterUtil
				.convertDateToXMLGregorianCalendar(bookingDO.getBookingDate()));
		parentTo.setRateLastCalculateDates(DateFormatterUtil
				.convertDateToXMLGregorianCalendar(bookingDO
						.getRateLastCalculateDate()));
		parentTo.setCustomerIdValue(bookingDO.getCustomerId() != null ? bookingDO.getCustomerId().getCustomerId():null);
		parentTo.setDocumentIdValue(bookingDO.getDocumentID() != null ? bookingDO.getDocumentID().getDocumentId():null);
		parentTo.setPincodeIdValue(bookingDO.getPincodeDO() != null ? bookingDO.getPincodeDO().getPincodeId():null);
		parentTo.setItemIdValue(bookingDO.getItemID() != null ? bookingDO.getItemID().getItemId():null);
		parentTo.setAgentIdValue(bookingDO.getAgentID() != null ? bookingDO.getAgentID().getAgentId():null);
		parentTo.setEmployeeIdValue(bookingDO.getEmployeeId() != null ? bookingDO.getEmployeeId().getEmployeeId():null);
		parentTo.setProductIdValue(bookingDO.getProductID() != null ? bookingDO.getProductID().getProductId() :null);
		parentTo.setOfficeIdValue(bookingDO.getOfficeID() != null ? bookingDO.getOfficeID().getOfficeId() :null);
		parentTo.setServiceIdValue(bookingDO.getServiceID() != null ? bookingDO.getServiceID().getServiceId() :null);
		parentTo.setModeIdValue(bookingDO.getMode() != null ? bookingDO.getMode().getModeId():null);
		parentTo.setConsigneeIdValue(bookingDO.getConsigneeID() != null ? bookingDO.getConsigneeID().getConsigneeId() :null);
		parentTo.setConsignorIdValue(bookingDO.getConsignorID() != null ? bookingDO.getConsignorID().getConsignerId() :null);
		parentTo.setCityIdValue(bookingDO.getCityDO() != null ? bookingDO.getCityDO().getCityId():null);
		parentTo.setChannelTypeIdValue(bookingDO.getChannelTypeID() != null ? bookingDO.getChannelTypeID().getChannelTypeId():null);
		parentTo.setHeldupIdValue(bookingDO.getHeldupDO() != null ? bookingDO.getHeldupDO().getHeldupReleaseId() :null);
		parentTo.setCurrencyIdValue(bookingDO.getCurrency() != null ? bookingDO.getCurrency().getCurrencyId() :null);
		parentTo.setValueCurrencyIdValue(bookingDO.getValueCurrency() != null ? bookingDO.getValueCurrency().getCurrencyId() :null);
		parentTo.setEmployeeIdForPickupBoyIdValue(bookingDO.getEmployeeIdForPickupBoyId() != null ?
																		bookingDO.getEmployeeIdForPickupBoyId().getEmployeeId():null);
		parentTo.setCashCollectionCenterIdValue(bookingDO.getCashCollectionCenter() != null ? bookingDO
																						.getCashCollectionCenter().getOfficeId():null);
		parentTo.setCommodityIdValue(bookingDO.getCommodityId() != null ? bookingDO.getCommodityId().getIntlCommodityId() :null);
		parentTo.setDomesticCommodityIdValue(bookingDO.getDomesticCommodityId() != null ? bookingDO.getDomesticCommodityId().getCommodityId() :null);
		
		LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : Phase 1...");
		
		LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : Phase 2...");
		LOGGER.trace("DomainToJaxBConverter : createdBookingJaxObj() : Phase 3...");
	}

	/**
	 * Created return to origin jax obj.
	 *
	 * @param rtnOrgDOList the rtn org do list
	 * @return the rto data
	 * @throws CGBusinessException the cG business exception
	 */
	public RtoData createdReturnToOriginJaxObj(List<RtnToOrgDO> rtnOrgDOList)
			throws CGBusinessException {
		LOGGER.info("DomainToTransferObjectConverter : createdReturnToOriginJaxObj() : START...");
		RtoData rtnToOrgData = new RtoData();
		try {
			for (RtnToOrgDO rtnToOrgDO : rtnOrgDOList) {
				RtoData.Rto parentTo = new RtoData.Rto();
				PropertyUtils.copyProperties(parentTo, rtnToOrgDO);
				parentTo.setRtnOrgId(null);
				parentTo.setRtoDates(StringUtil.isNull(rtnToOrgDO.getRtoDate()) ? null
						: DateFormatterUtil
								.convertDateToXMLGregorianCalendar(rtnToOrgDO
										.getRtoDate()));
				parentTo.setConsgBookDates(StringUtil.isNull(rtnToOrgDO
						.getConsgBookDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(rtnToOrgDO
								.getConsgBookDate()));
				parentTo.setManifestDates(StringUtil.isNull(rtnToOrgDO
						.getManifestDate()) ? null : DateFormatterUtil
						.convertDateToXMLGregorianCalendar(rtnToOrgDO
								.getManifestDate()));
				// parentTo.setRecvngOffId(StringUtil.isNull(issueDo.getReceivingPlantId())?null

				parentTo.setOriginOfficeId(StringUtil.isNull(rtnToOrgDO
						.getOriginOffice()) ? null : rtnToOrgDO
						.getOriginOffice().getOfficeId());
				parentTo.setDispatchingOffId(StringUtil.isNull(rtnToOrgDO
						.getDispatchingOff()) ? null : rtnToOrgDO
						.getDispatchingOff().getOfficeId());
				parentTo.setDestOffId(StringUtil.isNull(rtnToOrgDO.getDestOff()) ? null
						: rtnToOrgDO.getDestOff().getOfficeId());
				
				parentTo.setModeId(StringUtil.isNull(rtnToOrgDO.getMode()) ? null
						: rtnToOrgDO.getMode().getModeId());
				parentTo.setUserId(StringUtil.isNull(rtnToOrgDO.getUser()) ? null
						: rtnToOrgDO.getUser().getUserId());
				parentTo.setAgentId(StringUtil.isNull(rtnToOrgDO.getAgent()) ? null
						: rtnToOrgDO.getAgent().getAgentId());
				rtnToOrgData.getRto().add(parentTo);
			}
		} catch (Exception e) {
			LOGGER.error("DomainToTransferObjectConverter : createdReturnToOriginJaxObj() :Exception Happened:"
					+ e.getStackTrace());
			throw new CGBusinessException(e);
		}
		return rtnToOrgData;
	}

	/**
	 * Added by Narasimha Rao Kattunga Creating delivery manifest Jax Object.
	 *
	 * @param dlvMnfstList the dlv mnfst list
	 * @return the delivey manifest data
	 * @throws CGBusinessException the cG business exception
	 */
	public DeliveyManifestData createdDlvMnfstJaxObj(
			List<DeliveryDO> dlvMnfstList) throws CGBusinessException {
		LOGGER.info("DomainToTransferObjectConverter : createdDeliveryJaxObj() : START...");
		DeliveyManifestData jaxbObj = new DeliveyManifestData();
		try {
			if (dlvMnfstList != null && dlvMnfstList.size() > 0) {
				for (DeliveryDO delivery : dlvMnfstList) {
					DeliveyManifestData.DeliveryManifest dlvMnfstDtlsData = new DeliveyManifestData.DeliveryManifest();
					// Setting foreign keys objects
					PropertyUtils.copyProperties(dlvMnfstDtlsData, delivery);
					dlvMnfstDtlsData
							.setEmployeeId(delivery.getEmployee() != null ? delivery
									.getEmployee().getEmployeeId() : null);
					dlvMnfstDtlsData
							.setOfficeId(delivery.getBranch() != null ? delivery
									.getBranch().getOfficeId() : null);
					dlvMnfstDtlsData.setReportOffId(delivery
							.getReportingBranch() != null ? delivery
							.getReportingBranch().getOfficeId() : null);
					dlvMnfstDtlsData
							.setFranchiseeId(delivery.getFranchisee() != null ? delivery
									.getFranchisee().getFranchiseeId() : null);
					dlvMnfstDtlsData
							.setDocId(delivery.getDocument() != null ? delivery
									.getDocument().getDocumentId() : null);
					dlvMnfstDtlsData
							.setReasonId(delivery.getReason() != null ? delivery
									.getReason().getReasonId() : null);
					dlvMnfstDtlsData
							.setProductId(delivery.getProduct() != null ? delivery
									.getProduct().getProductId() : null);
					dlvMnfstDtlsData
							.setServiceId(delivery.getService() != null ? delivery
									.getService().getServiceId() : null);
					// dlvMnfstDtlsData.setUserId(delivery.getUserId()!=null ?
					dlvMnfstDtlsData
							.setHeldUpReleaseId(delivery.getHeldupDO() != null ? delivery
									.getHeldupDO().getHeldupReleaseId() : null);
					dlvMnfstDtlsData.setIdentityProofDocId(delivery
							.getIdentityProofDO() != null ? delivery
							.getIdentityProofDO().getIdentityProofDocId()
							: null);
					dlvMnfstDtlsData
							.setConsigneeId(delivery.getConsignee() != null ? delivery
									.getConsignee().getConsigneeId() : null);
					dlvMnfstDtlsData
							.setDestPinCodeId(delivery.getDestPinCode() != null ? delivery
									.getDestPinCode().getPincodeId() : null);
					if (delivery instanceof FranchiseDeliveryManifestDO) {
						dlvMnfstDtlsData.setDeliveryCode("FDM");
					} else if (delivery instanceof FranchiseDeliveryManifestHandOverDO) {
						dlvMnfstDtlsData.setDeliveryCode("FHR");
					} else if (delivery instanceof DeliveryRunDO) {
						dlvMnfstDtlsData.setDeliveryCode("DRS");
					} else if (delivery instanceof NonDeliveryRunDO) {
						dlvMnfstDtlsData.setDeliveryCode("NDRS");
					} else if (delivery instanceof PODDeliveryDO) {
						dlvMnfstDtlsData.setDeliveryCode("POD");
					}

					// ADD in Parent List
					jaxbObj.getDeliveryManifest().add(dlvMnfstDtlsData);
				}
			}
			LOGGER.info("DomainToTransferObjectConverter : createdDeliveryJaxObj() : END...");
		} catch (Exception ex) {
			LOGGER.error("DomainToTransferObjectConverter : createdDeliveryJaxObj() :Exception Happened:"
					+ ex.getStackTrace());
		}
		return jaxbObj;
	}
}
