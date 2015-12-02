/**
 * 
 */
package com.dtdc.bodbadmin.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.UtilConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGStackTraceUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.constant.BranchDBAdminConstant;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.BookingMnpItems;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.BookingNonDoxPaperWrkItems;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.BookingVasChrgesItems;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.ConsigneeDtls.ConsigneeAddressList;
import com.dtdc.bodbadmin.schema.booking.BookingDetailsData.BookingDetails.ConsignerDetails.ConsignorAddressDtls;
import com.dtdc.bodbadmin.schema.delivery.DeliveyManifestData;
import com.dtdc.bodbadmin.schema.dispatch.DispatchDetailsData;
import com.dtdc.bodbadmin.schema.heldupRelease.HeldupReleaseData;
import com.dtdc.bodbadmin.schema.manifest.ManifestData;
import com.dtdc.bodbadmin.schema.purchase.goodsCancellation.GoodsCancellationData;
import com.dtdc.bodbadmin.schema.purchase.goodsIssue.GoodsIssueData;
import com.dtdc.bodbadmin.schema.purchase.goodsRenewal.GoodsRenewalData;
import com.dtdc.bodbadmin.schema.rtoManifest.RtoData;
import com.dtdc.bodbadmin.xmlutil.ParserMappingUtil;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.dispatch.DispatchBagManifestDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.manifest.RtnToOrgDO;
import com.dtdc.domain.master.CurrencyDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.Billing.BillingDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.airline.AirportDO;
import com.dtdc.domain.master.bank.BankDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.ConsignerAddressDO;
import com.dtdc.domain.master.customer.ConsignerInfoDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.CountryDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.identityproof.IdentityProofDocDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.paperwork.PaperWorkItemDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.domain.master.product.InternationalCommodityDO;
import com.dtdc.domain.master.product.ItemDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.VasProductChargesDO;
import com.dtdc.domain.master.train.TrainDO;
import com.dtdc.domain.master.vehicle.VehicleDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.master.warehouse.WarehouseDO;
import com.dtdc.domain.opmaster.PartyTypeDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
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
import com.dtdc.domain.transaction.waybill.WayBillDetailsDO;

// TODO: Auto-generated Javadoc
/**
 * The Class TransferObjectToDomainObjectConverter.
 *
 * @author nisahoo
 */
public class TransferObjectToDomainObjectConverter {
	/** The Constant LOGGER. */
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TransferObjectToDomainObjectConverter.class);
	/**
	 * Creates the booking do from booking to.
	 *
	 * @param bookingDO the booking do
	 * @param bookingRow the booking row
	 * @return the booking do
	 * @throws CGBusinessException the cG business exception
	 */
	public BookingDO createBookingDOFromBookingTO(BookingDO bookingDO,
			BookingDetailsData.BookingDetails bookingRow)
			throws CGBusinessException{

		ItemDO itemDO = null;
		ChannelDO channelDO = null;
		CityDO cityDO = null;
		PincodeDO pincodeDO = null;
		ConsigneeDO consigneeDO = null;
		CustomerDO customerDO = null;
		EmployeeDO pickupBoyDO = null;
		ModeDO modeDO = null;
		ProductDO productDO = null;
		DocumentDO documentDO = null;
		ServiceDO serviceDO = null;
		OfficeDO officeDO = null;
		AgentDO agentDO = null;
		EmployeeDO employeeDO = null;
		ConsignerInfoDO consignerInfoDO = null;
		OfficeDO cashCollectionCenterDO = null;
		InternationalCommodityDO intCommodityDO = null;
		HeldUpReleaseDO heldUpDO = null;

		try {


			bookingDO.setBookingId(null);

			// Copy other properties
			if (bookingRow.getBookingDates() != null
					&& !bookingRow.getBookingDates().equals("")) {
				bookingDO.setBookingDate(DateFormatterUtil
						.convertXMLGregorianCalendarToDate(bookingRow
								.getBookingDates()));
			}


			if (bookingRow.getRateLastCalculateDates() != null
					&& !bookingRow.getRateLastCalculateDates().equals("")) {
				bookingDO.setRateLastCalculateDate(DateFormatterUtil
						.convertXMLGregorianCalendarToDate(bookingRow
								.getRateLastCalculateDates()));
			}

			// Set Current System Date as Created Date
			bookingDO.setTransCreatedDate(DateFormatterUtil.getCurrentDate());
			bookingDO.setTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
			// Set Read by Local Status
			bookingDO
					.setReadByLocal(BranchDBAdminConstant.READ_BY_LOCAL_SUCCESS);

			if (bookingRow.getItemIdValue() != null) {
				itemDO = new ItemDO();
				itemDO.setItemId(bookingRow.getItemIdValue());
			}

			if (bookingRow.getChannelTypeIdValue() != null) {
				channelDO = new ChannelDO();
				channelDO.setChannelTypeId(bookingRow.getChannelTypeIdValue());
			}

			if (bookingRow.getCityIdValue() != null) {
				cityDO = new CityDO();
				cityDO.setCityId(bookingRow.getCityIdValue());
			}

			if (bookingRow.getCustomerIdValue() != null) {
				customerDO = new CustomerDO();
				customerDO.setCustomerId(bookingRow.getCustomerIdValue());
			}

			if (bookingRow.getEmployeeIdForPickupBoyIdValue() != null) {
				pickupBoyDO = new EmployeeDO();
				pickupBoyDO.setEmployeeId(bookingRow
						.getEmployeeIdForPickupBoyIdValue());
			}

			if (bookingRow.getModeIdValue() != null) {
				modeDO = new ModeDO();
				modeDO.setModeId(bookingRow.getModeIdValue());
			}

			if (bookingRow.getProductIdValue() != null) {
				productDO = new ProductDO();
				productDO.setProductId(bookingRow.getProductIdValue());
			}


			if (bookingRow.getAgentIdValue() != null) {
				agentDO = new AgentDO();
				agentDO.setAgentId(bookingRow.getAgentIdValue());
			}

			if (bookingRow.getEmployeeIdValue() != null) {
				employeeDO = new EmployeeDO();
				employeeDO.setEmployeeId(bookingRow.getEmployeeIdValue());
			}


			if (bookingRow.getCashCollectionCenterIdValue() != null) {
				cashCollectionCenterDO = new OfficeDO();
				cashCollectionCenterDO.setOfficeId(bookingRow
						.getCashCollectionCenterIdValue());
			}

			if (bookingRow.getHeldupIdValue() != null) {
				heldUpDO = new HeldUpReleaseDO();
				heldUpDO.setHeldupReleaseId(bookingRow.getHeldupIdValue());

			}

			bookingDO.setItemID(itemDO);
			bookingDO.setChannelTypeID(channelDO);
			bookingDO.setCityDO(cityDO);
			bookingDO.setPincodeDO(pincodeDO);
			bookingDO.setConsigneeID(consigneeDO);
			bookingDO.setCustomerId(customerDO);
			bookingDO.setEmployeeIdForPickupBoyId(pickupBoyDO);
			bookingDO.setMode(modeDO);
			bookingDO.setProductID(productDO);
			bookingDO.setDocumentID(documentDO);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setOfficeID(officeDO);
			bookingDO.setAgentID(agentDO);
			bookingDO.setEmployeeId(employeeDO);
			bookingDO.setConsignorID(consignerInfoDO);
			bookingDO.setCashCollectionCenter(cashCollectionCenterDO);
			bookingDO.setCommodityId(intCommodityDO);
			bookingDO.setHeldupDO(heldUpDO);
			bookingDO.setDiFlag(BranchDBAdminConstant.DI_FLAG);
		} catch (Exception ex) {
			LOGGER.error("TransferObjectToDomainObjectConverter::createBookingDOFromBookingTO::Exception occured:"
					+ex.getMessage());
			throw new CGBusinessException(ex);
		}
		return bookingDO;
	}


	/**
	 * Creates the rtn to org do from rto data.
	 *
	 * @param rtoData the rto data
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<RtnToOrgDO> createRtnToOrgDOFromRtoData(
			RtoData rtoData) throws CGBusinessException {
		List<RtnToOrgDO> rtnToOrgDOList = new ArrayList<RtnToOrgDO>();
		
		try {
			if(rtoData!=null && rtoData.getRto()!=null){
				for (RtoData.Rto rtoRow : rtoData.getRto()) {
					
					OfficeDO originOffice = null;
					OfficeDO dispatchingOff = null;
					OfficeDO destOff = null;
					ModeDO mode = null; 
					UserDO user = null;
					AgentDO agent = null;

					RtnToOrgDO rtnToOrgDO = new RtnToOrgDO();
					PropertyUtils.copyProperties(rtnToOrgDO, rtoRow);
					
					rtnToOrgDO.setRtnOrgId(null);

					// Set Other Properties
					if(rtoRow.getManifestDates()!=null){
						rtnToOrgDO.setManifestDate(DateFormatterUtil.convertXMLGregorianCalendarToDate(rtoRow.getManifestDates()));
					}
					if(rtoRow.getConsgBookDates()!=null){
						rtnToOrgDO.setConsgBookDate(DateFormatterUtil.convertXMLGregorianCalendarToDate(rtoRow.getConsgBookDates()));
					}
					if(rtoRow.getRtoDates()!=null){
						rtnToOrgDO.setRtoDate(DateFormatterUtil.convertXMLGregorianCalendarToDate(rtoRow.getRtoDates()));
					}
					if(rtoRow.getRtoStatusDates()!=null){
						rtnToOrgDO.setRtoStatusDate(DateFormatterUtil.convertXMLGregorianCalendarToDate(rtoRow.getRtoStatusDates()));
					}
					
					if(rtoRow.getOriginOfficeId()!=null){
						originOffice = new OfficeDO();
						originOffice.setOfficeId(rtoRow.getOriginOfficeId());
						originOffice.setOfficeCode(rtoRow.getOriginOfficeCode());
					}
					if(rtoRow.getDispatchingOffId()!=null){
						dispatchingOff = new OfficeDO();
						dispatchingOff.setOfficeId(rtoRow.getDispatchingOffId());
						dispatchingOff.setOfficeCode(rtoRow.getDispatchingOffCode());
					}
					if(rtoRow.getDestOffId()!=null){
						destOff = new OfficeDO();
						destOff.setOfficeId(rtoRow.getDestOffId());
						destOff.setOfficeCode(rtoRow.getDestOffCode());
					}
					if(rtoRow.getModeId()!=null){
						mode = new ModeDO();
						mode.setModeId(rtoRow.getModeId());
						mode.setModeCode(rtoRow.getModeCode());
					}
					if(rtoRow.getUserId()!=null){
						user = new UserDO();
						user.setUserId(rtoRow.getUserId());
						user.setUserCode(rtoRow.getUserCode());
					}
					if(rtoRow.getAgentId()!=null){
						agent = new AgentDO();
						agent.setAgentId(rtoRow.getAgentId());
						agent.setAgentCode(rtoRow.getAgentCode());
					}

					rtnToOrgDO.setOriginOffice(originOffice);
					rtnToOrgDO.setDispatchingOff(dispatchingOff);
					rtnToOrgDO.setDestOff(destOff);
					rtnToOrgDO.setMode(mode);
					rtnToOrgDO.setUser(user);
					
					rtnToOrgDOList.add(rtnToOrgDO);
				}
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createRtnToOrgDOFromRtoData", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createRtnToOrgDOFromRtoData::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return rtnToOrgDOList;
	}
	
	/**
	 * Creates the manifest do from manifest to.
	 *
	 * @param manifestData the manifest data
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<ManifestDO> createManifestDOFromManifestTO(
			ManifestData manifestData) throws CGBusinessException {

		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>(0);

		OfficeDO originBranch = null;
		OfficeDO reportingBranch = null;
		OfficeDO destBranch = null;
		OfficeDO office = null;
		CityDO destCity = null;
		ModeDO mode = null;
		PincodeDO destZip = null;
		StdHandlingInstDO handleInst = null;
		ProductDO product = null;
		ServiceDO service = null;
		DocumentDO document = null;
		EmployeeDO employee = null;
		ManifestTypeDO manifestType = null;
		OfficeDO recvOffice = null;
		OfficeDO despRegOffice = null;
		OfficeDO despBranch = null;
		FranchiseeDO franchisee = null;
		CoLoaderDO coloader = null;
		CustomerDO customer = null;
		AgentDO agent = null;
		AirportDO airport = null;
		WayBillDetailsDO wayBillDetail = null;
		HeldUpReleaseDO heldUpRelease = null;

		try {
			if (manifestData != null
					&& manifestData.getManifest() != null) {

				

				for (ManifestData.Manifest manifestRow : manifestData.getManifest()) {

					ManifestDO manifestDO = new ManifestDO();
					PropertyUtils.copyProperties(manifestDO, manifestRow);
					if(StringUtil.isEmpty(manifestRow.getDiFlag())){
						manifestDO.setDiFlag("N");
					}
					manifestDO.setManifestId(null);

					// Set Other Properties
					if (manifestRow.getMnfstDate() != null
							&& !manifestRow.getMnfstDate().equals("")) {
						manifestDO
								.setManifestDate(DateFormatterUtil
										.slashDelimitedstringToDDMMYYYYFormat(manifestRow
												.getMnfstDate()));
					}

					if (manifestRow.getHandoverDateStr() != null
							&& !manifestRow.getHandoverDateStr().equals("")) {
						manifestDO
								.setHandoverDate(DateFormatterUtil
										.slashDelimitedstringToDDMMYYYYFormat(manifestRow
												.getHandoverDateStr()));
					}

					// Set Current System Date as Created Date
					manifestDO.setTransCreateDate(DateFormatterUtil
							.getCurrentDate());
					manifestDO.setTransLastModifiedDate(DateFormatterUtil
							.getDateFromStringDDMMYYY(DateFormatterUtil
									.todayDate()));

					// Set Child Objects
					if (manifestRow.getOriginBranchId() != null) {
						originBranch = new OfficeDO();
						originBranch.setOfficeId(manifestRow
								.getOriginBranchId());
						originBranch.setOfficeCode(manifestRow
								.getOriginBranchCode());
					}

					if (manifestRow.getRepBranchId() != null) {
						reportingBranch = new OfficeDO();
						reportingBranch.setOfficeId(manifestRow
								.getRepBranchId());
						reportingBranch.setOfficeCode(manifestRow
								.getRepBranchCode());
					}

					if (manifestRow.getDestBranchId() != null) {
						destBranch = new OfficeDO();
						destBranch.setOfficeId(manifestRow.getDestBranchId());
						destBranch.setOfficeCode(manifestRow
								.getDestBranchCode());
					}


					if (manifestRow.getDestCityId() != null) {
						destCity = new CityDO();
						destCity.setCityId(manifestRow.getDestCityId());
						destCity.setCityCode(manifestRow.getDestCityCode());
					}

					if (manifestRow.getModeId() != null) {
						mode = new ModeDO();
						mode.setModeId(manifestRow.getModeId());
						mode.setModeCode(manifestRow.getModeCode());
					}

					if (manifestRow.getDestZipId() != null) {
						destZip = new PincodeDO();
						destZip.setPincodeId(manifestRow.getDestZipId());
						destZip.setPincode(manifestRow.getDestZipCode());
					}

					if (manifestRow.getHandleInstId() != null) {
						handleInst = new StdHandlingInstDO();
						handleInst.setHandleInstId(manifestRow
								.getHandleInstId());
						handleInst.setHandleInstCode(manifestRow
								.getHandleInstCode());
					}

					if (manifestRow.getProductId() != null) {
						product = new ProductDO();
						product.setProductId(manifestRow.getProductId());
						product.setProductCode(manifestRow.getProductCode());
					}

					if (manifestRow.getDocumentId() != null) {
						document = new DocumentDO();
						document.setDocumentId(manifestRow.getDocumentId());
						document.setDocumentCode(manifestRow.getDocumentCode());
					}

					if (manifestRow.getServiceId() != null) {
						service = new ServiceDO();
						service.setServiceId(manifestRow.getServiceId());
						service.setServiceCode(manifestRow.getServiceCode());
					}

					if (manifestRow.getEmpId() != null) {
						employee = new EmployeeDO();
						employee.setEmployeeId(manifestRow.getEmpId());
						employee.setEmpCode(manifestRow.getEmployeeCode());
					}

					if (manifestRow.getManifestTypeId() != null) {
						manifestType = new ManifestTypeDO();
						manifestType.setMnfstTypeId(manifestRow
								.getManifestTypeId());
						manifestType.setMnfstCode(manifestRow
								.getManifestTypeCode());
					}

					if (manifestRow.getRecvOfficeId() != null) {
						recvOffice = new OfficeDO();
						recvOffice.setOfficeId(manifestRow.getRecvOfficeId());
						recvOffice.setOfficeCode(manifestRow
								.getRecvOfficeCode());
					}

					if (manifestRow.getDespBranchId() != null) {
						despRegOffice = new OfficeDO();
						despRegOffice
								.setOfficeId(manifestRow.getDespBranchId());
						despRegOffice.setOfficeCode(manifestRow
								.getDespRegOfficeCode());
					}

					if (manifestRow.getDespBranchId() != null) {
						despBranch = new OfficeDO();
						despBranch.setOfficeId(manifestRow.getDespBranchId());
						despBranch.setOfficeCode(manifestRow
								.getDespBranchCode());
					}

					if (manifestRow.getFranchiseeId() != null) {
						franchisee = new FranchiseeDO();
						franchisee.setFranchiseeId(manifestRow
								.getFranchiseeId());
						franchisee.setFranchiseeCode(manifestRow
								.getFranchiseeCode());
					}

					if (manifestRow.getCoLoaderId() != null) {
						coloader = new CoLoaderDO();
						coloader.setLoaderId(manifestRow.getCoLoaderId());
						coloader.setLoaderCode(manifestRow.getCoLoaderCode());
					}


					if (manifestRow.getAgentId() != null) {
						agent = new AgentDO();
						agent.setAgentId(manifestRow.getAgentId());
						agent.setAgentCode(manifestRow.getAgentCode());
					}

					if (manifestRow.getWayBillDetailsId() != null) {
						wayBillDetail = new WayBillDetailsDO();
						wayBillDetail.setWayBillId(manifestRow
								.getWayBillDetailsId());
						wayBillDetail.setWayBillNumber(manifestRow
								.getWayBillDetailsCode());
					}

					if (manifestRow.getAirportId() != null) {
						airport = new AirportDO();
						airport.setAirportId(manifestRow.getAirportId());
						airport.setAirportCode(manifestRow.getAirportCode());
					}

					if (manifestRow.getHeldupReleaseId() != null) {
						heldUpRelease = new HeldUpReleaseDO();
						heldUpRelease.setHeldupReleaseId(manifestRow
								.getHeldupReleaseId());

					}

					manifestDO.setOriginBranch(originBranch);
					manifestDO.setReportingBranch(reportingBranch);
					manifestDO.setDestBranch(destBranch);
					manifestDO.setOffice(office);
					manifestDO.setDestCity(destCity);
					manifestDO.setMode(mode);
					manifestDO.setDestPinCode(destZip);
					manifestDO.setStdHandleInst(handleInst);
					manifestDO.setProduct(product);
					manifestDO.setService(service);
					manifestDO.setDocument(document);
					manifestDO.setEmployee(employee);
					manifestDO.setMnsftTypes(manifestType);
					manifestDO.setRecvBranch(recvOffice);
					manifestDO.setDespRegBranch(despRegOffice);
					manifestDO.setDespBranch(despBranch);
					manifestDO.setFranchisee(franchisee);
					manifestDO.setCoLoader(coloader);
					manifestDO.setCustomer(customer);
					manifestDO.setAgent(agent);
					manifestDO.setWayBillDetails(wayBillDetail);
					manifestDO.setAirport(airport);
					manifestDO.setHeldupDO(heldUpRelease);

					manifestDOList.add(manifestDO);
				}
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createManifestDOFromManifestTO", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createManifestDOFromManifestTO::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return manifestDOList;
	}

	/**
	 * Creates the heldup release do from data object.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<HeldUpReleaseDO> createHeldupReleaseDoFromDataObject(
			HeldupReleaseData fromObject) throws CGBusinessException{
		List<HeldUpReleaseDO> parentDoList = new ArrayList<HeldUpReleaseDO>(0);

		try {
			for (HeldupReleaseData.HeldupRelease parentData : fromObject
					.getHeldupRelease()) {
				// parent details
				HeldUpReleaseDO parentDo = new HeldUpReleaseDO();
				PropertyUtils.copyProperties(parentDo, parentData);
				parentDo.setCurDateTime(DateFormatterUtil
						.convertXMLGregorianCalendarToGregorianCalendar(parentData
								.getCurDateTimes()) != null ? DateFormatterUtil
						.convertXMLGregorianCalendarToGregorianCalendar(
								parentData.getCurDateTimes()).getTime() : null);
				EmployeeDO empDo = null;
				if (!StringUtil.isEmptyInteger(parentData.getEmpId())) {
					empDo = new EmployeeDO();
					empDo.setEmployeeId(parentData.getEmpId());
				}
				parentDo.setEmployeeId(empDo);
				OfficeDO entryOff = null;
				if (!StringUtil.isEmptyInteger(parentData.getEntryOffId())) {
					entryOff = new OfficeDO();
					entryOff.setOfficeId(parentData.getEntryOffId());
				}
				parentDo.setEntryOfficeId(entryOff);

				OfficeDO recvngOff = null;
				if (!StringUtil.isEmptyInteger(parentData.getReceiveOffId())) {
					recvngOff = new OfficeDO();
					recvngOff.setOfficeId(parentData.getReceiveOffId());
				}
				parentDo.setReceiveOfficeId(recvngOff);
				parentDo.setUserId(parentData.getUserId());
				parentDo.setHeldupReleaseId(null);
				// child details
				Set<HeldUpReleaseItemsDtlsDO> childDoList = new HashSet<HeldUpReleaseItemsDtlsDO>(
						0);
				for (HeldupReleaseData.HeldupRelease.HeldupReleaseDtls childData : parentData
						.getHeldupReleaseDtls()) {
					HeldUpReleaseItemsDtlsDO childDo = new HeldUpReleaseItemsDtlsDO();
					PropertyUtils.copyProperties(childDo, childData);
					childDo.setHeldupDate(DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(childData
									.getHeldupDates()) != null ? DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(
									childData.getHeldupDates()).getTime()
							: null);
					childDo.setReleaseDate(DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(childData
									.getReleaseDates()) != null ? DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(
									childData.getReleaseDates()).getTime()
							: null);
					childDo.setTransCreateDate(DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(childData
									.getTransCreateDates()) != null ? DateFormatterUtil
							.convertXMLGregorianCalendarToGregorianCalendar(
									childData.getTransCreateDates()).getTime()
							: null);
					childDo.setTransLastModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
					childDo.setHeldupDO(parentDo);
					ReasonDO heldUpreason = null;
					if (!StringUtil.isEmptyInteger(childData.getHeldupRsnId())) {
						heldUpreason = new ReasonDO();
						heldUpreason.setReasonId(childData.getHeldupRsnId());
					}
					childDo.setHeldupReasonId(heldUpreason);
					ManifestTypeDO mnfstTypedo = null;
					if (!StringUtil.isEmptyInteger(childData.getMnfstId())) {
						mnfstTypedo = new ManifestTypeDO();
						mnfstTypedo.setMnfstTypeId(childData.getMnfstId());
					}
					childDo.setMnfstTypeId(mnfstTypedo);
					WarehouseDO storeLoc = null;
					if (!StringUtil.isEmptyInteger(childData
							.getStorageLocationId())) {
						storeLoc = new WarehouseDO();
						storeLoc.setWarehouseId(childData
								.getStorageLocationId());
					}
					childDo.setStorageLocId(storeLoc);
					childDo.setHeldupReleaseItemsDtlsId(null);
					childDoList.add(childDo);
				}
				parentDo.setItemDetailsSet(childDoList);
				parentDoList.add(parentDo);
			}

		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createHeldupReleaseDoFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createHeldupReleaseDoFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}

		return parentDoList;
	}

	/**
	 * Creates the goods renewal do from data object.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<GoodsRenewalDO> createGoodsRenewalDoFromDataObject(
			GoodsRenewalData fromObject) throws CGBusinessException{
		LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsRenewalDoFromDataObject():START");
		List<GoodsRenewalDO> parentDoList = new ArrayList<GoodsRenewalDO>(0);

		CustomerDO custDO = null;
		OfficeDO renewingOffDO = null;
		OfficeDO cccOffDO = null;
		FranchiseeDO frDO = null;
		PartyTypeDO partyDO = null;
		EmployeeDO empDO = null;
		UserDO userDO = null;

		ItemDO itemId = null;

		try {
			for (GoodsRenewalData.GoodsRenewal parentData : fromObject
					.getGoodsRenewal()) {
				GoodsRenewalDO goodsRenewalDO = new GoodsRenewalDO();

				Set<GoodsRenewalItemDtlsDO> itemSet = new HashSet<GoodsRenewalItemDtlsDO>();

				// PARENT GOODS CANCL DETAILS
				LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsRenewalDoFromDataObject(): COPYING HEADER DETAILS DATA");
				PropertyUtils.copyProperties(goodsRenewalDO, parentData);

				// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
				goodsRenewalDO.setGoodsRenewalId(null);
				goodsRenewalDO.setGoodsRenewalDate(DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(parentData
								.getGoodsRenewalDateStr()));

				if (!StringUtil.isEmptyInteger(parentData.getCustId())) {
					custDO = new CustomerDO();
					custDO.setCustomerId(parentData.getCustId());

					goodsRenewalDO.setCustomerId(custDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getRenewingOffId())) {
					cccOffDO = new OfficeDO();
					cccOffDO.setOfficeId(parentData.getRenewingOffId());

					goodsRenewalDO.setCccOfficeId(cccOffDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getCccOffId())) {
					renewingOffDO = new OfficeDO();
					renewingOffDO.setOfficeId(parentData.getCccOffId());

					goodsRenewalDO.setRenewingOfficeId(renewingOffDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getFrId())) {
					frDO = new FranchiseeDO();
					frDO.setFranchiseeId(parentData.getFrId());

					goodsRenewalDO.setFranchiseId(frDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getPartyId())) {
					partyDO = new PartyTypeDO();
					partyDO.setPartyId(parentData.getPartyId());

					goodsRenewalDO.setPartyDo(partyDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getEmpId())) {
					empDO = new EmployeeDO();
					empDO.setEmployeeId(parentData.getEmpId());

					goodsRenewalDO.setEmployeeId(empDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getUserID())) {
					userDO = new UserDO();
					userDO.setUserId(parentData.getUserID());

					goodsRenewalDO.setUserId(userDO);
				}
				
				// CHILD GOODS CANCL ITEM DETAILS
				for (GoodsRenewalData.GoodsRenewal.GoodsRenewalitemDtlsList childData : parentData
						.getGoodsRenewalitemDtlsList()) {
					GoodsRenewalItemDtlsDO itemDO = new GoodsRenewalItemDtlsDO();
					LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsRenewalDoFromDataObject(): COPYING CHILD - ITEM DETAILS DATA");
					PropertyUtils.copyProperties(itemDO, childData);
					// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
					itemDO.setIssueDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(childData
									.getIssueDateStr()));
					itemDO.setGoodsRenewalItemDtlsId(null);
					itemDO.setTransCreateDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
					itemDO.setTransLastModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
					if (!StringUtil.isEmptyInteger(childData.getItemDOId())) {
						itemId = new ItemDO();
						itemId.setItemId(childData.getItemDOId());
						itemDO.setItemDO(itemId);
					}
					
					itemSet.add(itemDO);
				}

				goodsRenewalDO.setGoodsRenewalItmDtls(itemSet);
				parentDoList.add(goodsRenewalDO);
				LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsRenewalDoFromDataObject():END");
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createGoodsRenewalDoFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createGoodsRenewalDoFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return parentDoList;

	}

	/**
	 * Creates the goods cancl do from data object.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<GoodscCancellationDO> createGoodsCanclDoFromDataObject(
			GoodsCancellationData fromObject) throws CGBusinessException {
		LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsCanclDoFromDataObject():START");

		List<GoodscCancellationDO> parentDoList = new ArrayList<GoodscCancellationDO>(
				0);

		try {
			for (GoodsCancellationData.GoodsCancellation parentData : fromObject
					.getGoodsCancellation()) {
				GoodscCancellationDO goodsCanclDO = new GoodscCancellationDO();

				Set<GoodsCanclItemDtlsDO> itemSet = new HashSet<GoodsCanclItemDtlsDO>();

				// PARENT GOODS CANCL DETAILS
				LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsCanclDoFromDataObject(): copying header data");
				PropertyUtils.copyProperties(goodsCanclDO, parentData);
				// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
				goodsCanclDO.setGoodsCanclId(null);
				goodsCanclDO.setCanclDate(DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(parentData
								.getCanclDateStr()));

				if (!StringUtil.isEmptyInteger(parentData.getCanclApprEmpId())) {
					EmployeeDO approverEmpDO = new EmployeeDO();
					approverEmpDO.setEmployeeId(parentData.getCanclApprEmpId());
					goodsCanclDO.setCanclApproverEmpId(approverEmpDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getCanclEmployeeId())) {
					EmployeeDO canclEmpDO = new EmployeeDO();
					canclEmpDO.setEmployeeId(parentData.getCanclEmployeeId());
					goodsCanclDO.setCanclEmpId(canclEmpDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getCanclOffId())) {
					OfficeDO canclOffDO = new OfficeDO();
					canclOffDO.setOfficeId(parentData.getCanclOffId());
					goodsCanclDO.setCanclOfficeId(canclOffDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getReturnPartyTyId())) {
					PartyTypeDO partyDO = new PartyTypeDO();
					partyDO.setPartyId(parentData.getReturnPartyTyId());
					goodsCanclDO.setReturnPartyTypeId(partyDO);
				}
				if (!StringUtil
						.isEmptyInteger(parentData.getReturnCustomerId())) {
					CustomerDO custDO = new CustomerDO();
					custDO.setCustomerId(parentData.getReturnCustomerId());
					goodsCanclDO.setReturnCustId(custDO);
				}
				if (!StringUtil.isEmptyInteger(parentData
						.getReturnCCCOfficeId())) {
					OfficeDO cccOffDO = new OfficeDO();
					cccOffDO.setOfficeId(parentData.getReturnCCCOfficeId());
					goodsCanclDO.setReturnCCCOffId(cccOffDO);
				}
				if (!StringUtil.isEmptyInteger(parentData
						.getReturnFranchiseeId())) {
					FranchiseeDO frDO = new FranchiseeDO();
					frDO.setFranchiseeId(parentData.getReturnFranchiseeId());
					goodsCanclDO.setReturnFRId(frDO);

				}
				if (!StringUtil
						.isEmptyInteger(parentData.getReturnEmployeeId())) {
					EmployeeDO empDO = new EmployeeDO();
					empDO.setEmployeeId(parentData.getReturnEmployeeId());
					goodsCanclDO.setReturnEmpId(empDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getUserID())) {
					UserDO userDO = new UserDO();
					userDO.setUserId(parentData.getUserID());
					goodsCanclDO.setUserId(userDO);
				}
				goodsCanclDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
				// CHILD GOODS CANCL ITEM DETAILS
				for (GoodsCancellationData.GoodsCancellation.GoodsCanclitemDtlsList childData : parentData
						.getGoodsCanclitemDtlsList()) {
					GoodsCanclItemDtlsDO itemDO = new GoodsCanclItemDtlsDO();
					LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsCanclDoFromDataObject(): COPYING CHILD - ITEM DETAILS DATA");
					PropertyUtils.copyProperties(itemDO, childData);

					// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
					itemDO.setGoodsCanclItemDtlsId(null);
					itemDO.setIssueDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(childData
									.getIssueDateStr()));

					if (!StringUtil.isEmptyInteger(childData.getItemID())) {
						ItemDO itemId = new ItemDO();
						itemId.setItemId(childData.getItemID());
						itemDO.setItemId(itemId);
					}
					if (!StringUtil.isStringEmpty(childData.getIssueDateStr())) {
						itemDO.setIssueDate(DateFormatterUtil
								.getDateFromStringDDMMYYY(childData
										.getIssueDateStr()));
					}
					if (!StringUtil.isEmptyInteger(childData.getIssuedOffId())) {
						OfficeDO issueOff = new OfficeDO();
						issueOff.setOfficeId(childData.getIssuedOffId());
						itemDO.setIssuedOfficeId(issueOff);
					}
					if (!StringUtil
							.isEmptyInteger(childData.getCancelReaonId())) {
						ReasonDO reasonCanclDO = new ReasonDO();
						reasonCanclDO.setReasonId(childData.getCancelReaonId());
						itemDO.setCanclReaonId(reasonCanclDO);
					}
					itemDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
					itemSet.add(itemDO);
				}
				goodsCanclDO.setGoodsCanclItemDtls(itemSet);
				parentDoList.add(goodsCanclDO);
				LOGGER.info("TransferObjectToDomainObjectConverter : createGoodsCanclDoFromDataObject():END");
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createGoodsCanclDoFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createGoodsCanclDoFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return parentDoList;
	}

	/**
	 * Creates the dispatch do from data object.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DispatchDO> createDispatchDoFromDataObject(
			DispatchDetailsData fromObject)throws CGBusinessException {
		LOGGER.info("TransferObjectToDomainObjectConverter : createDispatchDoFromDataObject():START");

		List<DispatchDO> parentDoList = new ArrayList<DispatchDO>(0);

		CustomerDO custDO = null;
		OfficeDO originOfficeDO = null;
		OfficeDO destOfficeDO = null;
		FranchiseeDO frDO = null;
		PartyTypeDO partyDO = null;
		EmployeeDO loadSentEmpDO = null;
		UserDO userDO = null;
		VendorDO vendorDO = null;

		EmployeeDO directEmpDO = null;
		AirportDO airportDO = null;
		BillingDO billingDO = null;
		CoLoaderDO coloaderDO = null;
		ModeDO modeDO = null;
		VehicleDO vehicalDO = null;
		TrainDO trainDO = null;
		EmployeeDO directEmp = null;
		DispatchDO dispatch = null;
		ManifestDO manifest = null;
		ModeDO mode = null;
		VehicleDO vehical = null;
		TrainDO train = null;
		AirportDO airport = null;
		BillingDO billing = null;
		VendorDO vendor = null;
		CoLoaderDO coloader = null;
		try {
			for (DispatchDetailsData.DispatchDetails parentData : fromObject
					.getDispatchDetails()) {
				DispatchDO dispatchDO = new DispatchDO();

				Set<DispatchBagManifestDO> itemSet = new HashSet<DispatchBagManifestDO>();

				// PARENT GOODS CANCL DETAILS
				PropertyUtils.copyProperties(dispatchDO, parentData);
				// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
				dispatchDO.setDispatchId(null);
				dispatchDO
						.setLoadSentDate(DateFormatterUtil
								.slashDelimitedstringToDDMMYYYYFormat(parentData
										.getLoadSentDateStr()));
				dispatchDO.setCdDate(DateFormatterUtil
						.slashDelimitedstringToDDMMYYYYFormat(parentData.getCdDateStr()));

				if (!StringUtil.isEmptyInteger(parentData.getLoadSentEmpId())) {
					loadSentEmpDO = new EmployeeDO();
					loadSentEmpDO.setEmployeeId(parentData.getLoadSentEmpId());
					dispatchDO.setLoadSentEmpDO(loadSentEmpDO);
				}
				if(StringUtil.isEmpty(parentData.getDiflag())){
					dispatchDO.setDiFlag("N");
				}
				if (!StringUtil.isEmptyInteger(parentData.getDirectEmpId())) {
					loadSentEmpDO = new EmployeeDO();
					loadSentEmpDO.setEmployeeId(parentData.getDirectEmpId());
					dispatchDO.setDirectEmpDO(loadSentEmpDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getAirportId())) {
					airportDO = new AirportDO();
					airportDO.setAirportId(parentData.getAirportId());
					dispatchDO.setAirportDO(airportDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getBillingId())) {
					billingDO = new BillingDO();
					billingDO.setBillingId(parentData.getBillingId());
					dispatchDO.setBillingDO(billingDO);

				}
				if (!StringUtil.isEmptyInteger(parentData.getColoaderId())) {
					coloaderDO = new CoLoaderDO();
					coloaderDO.setLoaderId(parentData.getColoaderId());

					dispatchDO.setColoaderDO(coloaderDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getOriginOfficeId())) {
					originOfficeDO = new OfficeDO();
					originOfficeDO.setOfficeId(parentData.getOriginOfficeId());
					dispatchDO.setOriginOfficeDO(originOfficeDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getDestOfficeId())) {
					destOfficeDO = new OfficeDO();
					destOfficeDO.setOfficeId(parentData.getDestOfficeId());
					dispatchDO.setDestOfficeDO(destOfficeDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getModeId())) {
					modeDO = new ModeDO();
					modeDO.setModeId(parentData.getModeId());
					dispatchDO.setModeDO(modeDO);

				}
				if (!StringUtil.isEmptyInteger(parentData.getVehicalId())) {
					vehicalDO = new VehicleDO();
					vehicalDO.setVehicleId(parentData.getVehicalId());
					dispatchDO.setVehicalDO(vehicalDO);

				}
				if (!StringUtil.isEmptyInteger(parentData.getTrainId())) {
					trainDO = new TrainDO();
					trainDO.setTrainId(parentData.getTrainId());
					dispatchDO.setTrainDO(trainDO);

				}
				if (!StringUtil.isEmptyInteger(parentData.getVendorId())) {
					vendorDO = new VendorDO();
					vendorDO.setVendorId(parentData.getVendorId());
					dispatchDO.setVendorDO(vendorDO);
				}
				if (!StringUtil.isEmptyInteger(parentData.getUserId())) {
					userDO = new UserDO();
					userDO.setUserId(parentData.getUserId());
					dispatchDO.setUserDo(userDO);
				}
				dispatchDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
				// CHILD GOODS CANCL ITEM DETAILS
				for (DispatchDetailsData.DispatchDetails.DispatchBagManifestDetailsList childData : parentData
						.getDispatchBagManifestDetailsList()) {
					DispatchBagManifestDO itemDO = new DispatchBagManifestDO();
					PropertyUtils.copyProperties(itemDO, childData);
					// SET NULL PRIMARY KEY BEFORE INSERT THE DATA IN LOCAL DB
					itemDO.setDispatchBagMnfstId(null);

					if (!StringUtil.isEmptyInteger(childData.getDirectEmpId())) {
						directEmp = new EmployeeDO();
						directEmp.setEmployeeId(childData.getDirectEmpId());
						itemDO.setDirectEmpDO(directEmp);
					}
					if (!StringUtil.isEmptyInteger(childData.getModeId())) {
						mode = new ModeDO();
						mode.setModeId(childData.getModeId());
						itemDO.setModeDO(mode);

					}
					if (!StringUtil.isEmptyInteger(childData.getVehicalId())) {
						vehical = new VehicleDO();
						vehical.setVehicleId(childData.getVehicalId());
						itemDO.setVehicalDO(vehical);

					}
					if (!StringUtil.isEmptyInteger(childData.getTrainId())) {
						train = new TrainDO();
						train.setTrainId(childData.getTrainId());
						itemDO.setTrainDO(train);

					}
					if (!StringUtil.isEmptyInteger(childData.getAirportId())) {
						airport = new AirportDO();
						airport.setAirportId(childData.getAirportId());
						itemDO.setAirportDO(airport);
					}
					if (!StringUtil.isEmptyInteger(childData.getBillingId())) {
						billing = new BillingDO();
						billing.setBillingId(childData.getBillingId());
						itemDO.setBillingDO(billing);

					}
					if (!StringUtil.isEmptyInteger(childData.getColoaderId())) {
						coloader = new CoLoaderDO();
						coloader.setLoaderId(childData.getColoaderId());

						itemDO.setColoaderDO(coloader);
					}
					if (!StringUtil.isEmptyInteger(childData.getVendorId())) {
						vendor = new VendorDO();
						vendor.setVendorId(childData.getVendorId());
						itemDO.setVendorDO(vendor);
					}
					itemDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
					itemSet.add(itemDO);
				}
				dispatchDO.setDispatchBagManifestDOList(itemSet);
				parentDoList.add(dispatchDO);
				LOGGER.info("TransferObjectToDomainObjectConverter : createDispatchDoFromDataObject():END");
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createDispatchDoFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createDispatchDoFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());
		}
		return parentDoList;
	}

	/**
	 * Creates the goods issue do from data object.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<GoodsIssueDO> createGoodsIssueDoFromDataObject(
			GoodsIssueData fromObject) throws  CGBusinessException{
		List<GoodsIssueDO> parentDoList = new ArrayList<GoodsIssueDO>(0);
		try {
			for (GoodsIssueData.GoodsIssue issueData : fromObject
					.getGoodsIssue()) {
				GoodsIssueDO parentDo = new GoodsIssueDO();
				PropertyUtils.copyProperties(parentDo, issueData);

				parentDo.setDateOfPayment(StringUtil.isNull(issueData
						.getDateOfPayments()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getDateOfPayments()));
				parentDo.setGoodsIssueId(null);
				parentDo.setIssueDocumentDate(StringUtil.isNull(issueData
						.getIssueDocumentDates()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getIssueDocumentDates()));
				parentDo.setReceiptDocumentDate(StringUtil.isNull(issueData
						.getReceiptDocumentDates()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getReceiptDocumentDates()));
				parentDo.setReqSlipDate(StringUtil.isNull(issueData
						.getReqSlipDates()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getReqSlipDates()));
				parentDo.setStartDocumentDate(StringUtil.isNull(issueData
						.getStartDocumentDates()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getStartDocumentDates()));
				parentDo.setTransactionCrDate(StringUtil.isNull(issueData
						.getTransactionCrDates()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getTransactionCrDates()));
				parentDo.setTransactionLastMdfDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				EmployeeDO appver = null;
				if (!StringUtil.isEmptyInteger(issueData.getApprvrId())) {
					appver = new EmployeeDO();
					appver.setEmployeeId(issueData.getApprvrId());
					appver.setEmpCode(issueData.getApprvrCode());
					parentDo.setApproverEmpId(appver);
				}
				EmployeeDO issPerson = null;
				if (!StringUtil.isEmptyInteger(issueData.getIssPersonId())) {
					issPerson = new EmployeeDO();
					issPerson.setEmployeeId(issueData.getIssPersonId());
					issPerson.setEmpCode(issueData.getIssPersonCode());
					parentDo.setIssuingPerson(issPerson);
				}
				EmployeeDO recpnt = null;
				if (!StringUtil.isEmptyInteger(issueData.getRecpntId())) {
					recpnt = new EmployeeDO();
					recpnt.setEmployeeId(issueData.getRecpntId());
					recpnt.setEmpCode(issueData.getRecpntCode());
					parentDo.setReceipientId(recpnt);
				}
				EmployeeDO recPesron = null;
				if (!StringUtil.isEmptyInteger(issueData.getRecvngPersonId())) {
					recPesron = new EmployeeDO();
					recPesron.setEmployeeId(issueData.getRecvngPersonId());
					recPesron.setEmpCode(issueData.getRecvngPersonCode());
					parentDo.setReceivingPersonId(recPesron);
				}
				UserDO user = null;
				if (!StringUtil.isEmptyInteger(issueData.getUsrId())) {
					user = new UserDO();
					user.setUserId(issueData.getUsrId());
					user.setUserCode(issueData.getUsrCode());
					parentDo.setUserDo(user);
				}
				OfficeDO issuingOff = null;
				if (!StringUtil.isEmptyInteger(issueData.getIssOffId())) {
					issuingOff = new OfficeDO();
					issuingOff.setOfficeId(issueData.getIssOffId());
					issuingOff.setOfficeCode(issueData.getIssOffCode());
					parentDo.setIssuingPlant(issuingOff);
				}
				OfficeDO revngOff = null;
				if (!StringUtil.isEmptyInteger(issueData.getRecvngOffId())) {
					revngOff = new OfficeDO();
					revngOff.setOfficeId(issueData.getRecvngOffId());
					revngOff.setOfficeCode(issueData.getRecvngOffCode());
					parentDo.setReceivingPlantId(revngOff);
				}
				FranchiseeDO franchisee = null;
				if (!StringUtil.isEmptyInteger(issueData.getFrId())) {
					franchisee = new FranchiseeDO();
					franchisee.setFranchiseeId(issueData.getFrId());
					franchisee.setFranchiseeCode(issueData.getFrCode());
					parentDo.setFranchisee(franchisee);
				}
				CustomerDO recCust = null;
				if (!StringUtil.isEmptyInteger(issueData.getRecvngCustId())) {
					recCust = new CustomerDO();
					recCust.setCustomerId(issueData.getRecvngCustId());
					recCust.setCustomerCode(issueData.getRecvngCustCode());
					parentDo.setReceivingCustomerId(recCust);
				}
				
				
				if(!StringUtil.isEmptyInteger(issueData.getWarehouseId())){
					WarehouseDO warehouseDo= new WarehouseDO();
					warehouseDo.setWarehouseId(issueData.getWarehouseId());
					parentDo.setWarehouseDo(warehouseDo);
				}
				Set<GoodsIssueItemDtlsDO> issueItmDtlsSet = new HashSet<GoodsIssueItemDtlsDO>(
						0);
				for (GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls issueItmDtls : issueData
						.getGoodsIssueItmsDtls()) {
					GoodsIssueItemDtlsDO childDo = new GoodsIssueItemDtlsDO();
					PropertyUtils.copyProperties(childDo, issueItmDtls);
					childDo.setIssueDo(parentDo);
					childDo.setStartSINo(StringUtil.isEmpty(issueItmDtls.getStartSINo())? null :issueItmDtls.getStartSINo());
					childDo.setEndSINo(StringUtil.isEmpty(issueItmDtls.getEndSINo())? null :issueItmDtls.getEndSINo());
					if(StringUtil.isEmpty(childDo.getDiFlag())){
						childDo.setDiFlag("N");
					}
					childDo.setItemExpiryDate(StringUtil.isNull(issueItmDtls
							.getItemExpiryDates()) ? null : DateFormatterUtil
							.convertXMLGregorianCalendarToDate(issueItmDtls
									.getItemExpiryDates()));
					childDo.setTransCreateDate(StringUtil.isNull(issueItmDtls
							.getTransCreateDates()) ? null
							: DateFormatterUtil
									.convertXMLGregorianCalendarToTimeStamp(issueItmDtls
											.getTransCreateDates()));
					childDo.setTransLastModifiedDate(StringUtil
							.isNull(issueItmDtls.getTransLastModifiedDates()) ? null
							: DateFormatterUtil
									.convertXMLGregorianCalendarToTimeStamp(issueItmDtls
											.getTransLastModifiedDates()));
					UserDO userDo = null;
					if (!StringUtil.isEmptyInteger(issueItmDtls.getUsrId())) {
						userDo = new UserDO();
						userDo.setUserId(issueItmDtls.getUsrId());
						userDo.setUserCode(issueItmDtls.getUsrCode());
						childDo.setUserId(userDo);
					}

					OfficeDO revngOffIce = null;
					if (!StringUtil.isEmptyInteger(issueItmDtls
							.getRecvngOffId())) {
						revngOffIce = new OfficeDO();
						revngOffIce.setOfficeId(issueItmDtls.getRecvngOffId());
						revngOffIce.setOfficeCode(issueItmDtls
								.getRecvngOffCode());
						childDo.setReceivingPlantId(revngOffIce);
					}
					childDo.setIssueDo(parentDo);
					// preparing charges DO list
					Set<GoodsIssueChargesDO> issueChrgDtlsSet = new HashSet<GoodsIssueChargesDO>(
							0);
					for (GoodsIssueData.GoodsIssue.GoodsIssueItmsDtls.GoodsIssueChargesDtls issueChrgDtls : issueItmDtls
							.getGoodsIssueChargesDtls()) {
						GoodsIssueChargesDO chargsDo = new GoodsIssueChargesDO();
						PropertyUtils.copyProperties(chargsDo, issueChrgDtls);
						chargsDo.setGoodsIssueItemDtls(childDo);
						childDo.setStartSINo(StringUtil.isEmpty(issueItmDtls.getStartSINo())? null :issueItmDtls.getStartSINo());
						childDo.setEndSINo(StringUtil.isEmpty(issueItmDtls.getEndSINo())? null :issueItmDtls.getEndSINo());
						chargsDo.setTransCreateDate(StringUtil
								.isNull(issueChrgDtls.getTransCreateDates()) ? null
								: DateFormatterUtil
										.convertXMLGregorianCalendarToTimeStamp(issueChrgDtls
												.getTransCreateDates()));
						chargsDo.setTransLastModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
						UserDO chargsUserDo = null;
						if (!StringUtil
								.isEmptyInteger(issueChrgDtls.getUsrId())) {
							chargsUserDo = new UserDO();
							chargsUserDo.setUserId(issueChrgDtls.getUsrId());
							chargsUserDo
									.setUserCode(issueChrgDtls.getUsrCode());
							chargsDo.setUserdo(chargsUserDo);
						}
						chargsDo.setGoodsIssueItemDtls(childDo);
						issueChrgDtlsSet.add(chargsDo);
					}
					childDo.setChargesDo(issueChrgDtlsSet);

					issueItmDtlsSet.add(childDo);
				}
				Set<PurchasePaymentDtlsDO> issuePymntDtlsSet = new HashSet<PurchasePaymentDtlsDO>(
						0);
				for (GoodsIssueData.GoodsIssue.GoodsIssuePaymntDtls issuePaymntDtls : issueData
						.getGoodsIssuePaymntDtls()) {
					PurchasePaymentDtlsDO paymntDo = new PurchasePaymentDtlsDO();
					PropertyUtils.copyProperties(paymntDo, issuePaymntDtls);
					paymntDo.setGoodsIssueId(parentDo);
					paymntDo.setDateOfPayment(StringUtil.isNull(issuePaymntDtls
							.getDateOfPayments()) ? null : DateFormatterUtil
							.convertXMLGregorianCalendarToDate(issuePaymntDtls
									.getDateOfPayments()));
					paymntDo.setTransactionCrDate(StringUtil
							.isNull(issuePaymntDtls.getTransCreateDates()) ? null
							: DateFormatterUtil
									.convertXMLGregorianCalendarToDate(issuePaymntDtls
											.getTransCreateDates()));
					paymntDo.setTransactionLastMdfDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
					UserDO usr = null;
					if (!StringUtil.isEmptyInteger(issuePaymntDtls.getUsrId())) {
						usr = new UserDO();
						usr.setUserId(issuePaymntDtls.getUsrId());
						usr.setUserCode(issuePaymntDtls.getUsrCode());
						paymntDo.setUserDo(usr);
					}
					if (!StringUtil.isEmptyInteger(issuePaymntDtls.getBankId())) {
						BankDO bankDO =  new BankDO();
						bankDO.setBankId(issuePaymntDtls.getBankId());
						paymntDo.setBankDO(bankDO);
						
					}
					paymntDo.setGoodsIssueId(parentDo);
					issuePymntDtlsSet.add(paymntDo);
				}
				parentDo.setPaymentDetls(issuePymntDtlsSet);
				parentDo.setGoodsIssueItems(issueItmDtlsSet);
				parentDo.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
				parentDoList.add(parentDo);
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createGoodsIssueDoFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createGoodsIssueDoFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());

		}

		return parentDoList;
	}

	/**
	 * Prepare data for booking do.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public static List<BookingDO> prepareDataForBookingDO(
			BookingDetailsData fromObject) throws CGBusinessException {

		LOGGER.debug("ParserMappingUtil : copyBookingProperties() : START");
		
		List<BookingDO> bookingDolist=null;
		if(fromObject.getBookingDetails()!=null && !fromObject.getBookingDetails().isEmpty()){
			bookingDolist = new ArrayList<BookingDO>(fromObject.getBookingDetails().size());
		}
		try {
		for ( BookingDetails  xmlParent : fromObject.getBookingDetails()){
				BookingDO bookingDo = null;

				if (xmlParent.getBookingType().equals("FRBOOKING")) {
					bookingDo = new FranchiseeBookingDO();
					bookingDo.setBookingType("FRBOOKING");
				} else if (xmlParent.getBookingType().equals("CASHBOOKING")) {
					bookingDo = new CashBookingDO();
					bookingDo.setBookingType("CASHBOOKING");
				} else if (xmlParent.getBookingType().equals("DPBOOKING")) {
					bookingDo = new DirectPartyBookingDO();
					bookingDo.setBookingType("DPBOOKING");
				} else {
					bookingDo = new BookingDO();
				}
				
				//
				 PropertyUtils.copyProperties(bookingDo, xmlParent);
				 bookingDo.setBookingId(null);
				 bookingDo.setBookingDate(DateFormatterUtil
							.convertXMLGregorianCalendarToDate(xmlParent.getBookingDates()));
					 bookingDo.setRateLastCalculateDate(DateFormatterUtil
								.convertXMLGregorianCalendarToDate(xmlParent.getRateLastCalculateDates()));
					 
					 if(!StringUtil.isEmptyInteger(xmlParent.getCustomerIdValue())){
						 CustomerDO customerDo = new CustomerDO();
						 customerDo.setCustomerId(xmlParent.getCustomerIdValue());
						 bookingDo.setCustomerId(customerDo); 
					 }
					 
					 if(!StringUtil.isEmptyInteger(xmlParent.getDocumentIdValue())){
						 DocumentDO documentDO = new DocumentDO();
							documentDO.setDocumentId(xmlParent.getDocumentIdValue());
							 bookingDo.setDocumentID(documentDO); 
					 }
					 if(!StringUtil.isEmptyInteger(xmlParent.getPincodeIdValue())){
						 PincodeDO pincodeDo = new PincodeDO();
							pincodeDo.setPincodeId(xmlParent.getPincodeIdValue());
							bookingDo.setPincodeDO(pincodeDo); 
					 }
					
					 if(!StringUtil.isEmptyInteger(xmlParent.getItemIdValue())){
						 ItemDO itemDO =new ItemDO();
							itemDO.setItemId(xmlParent.getItemIdValue());
							bookingDo.setItemID(itemDO); 
					 }
					
					 if(!StringUtil.isEmptyInteger(xmlParent.getAgentIdValue())){
						 AgentDO agentDO =new AgentDO();
							agentDO.setAgentId(xmlParent.getAgentIdValue());
							bookingDo.setAgentID(agentDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getEmployeeIdValue())){
						EmployeeDO employeeDO = new EmployeeDO();
						employeeDO.setEmployeeId(xmlParent.getEmployeeIdValue());
						bookingDo.setEmployeeId(employeeDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getProductIdValue())){
						ProductDO productDO = new ProductDO();
						productDO.setProductId(xmlParent.getProductIdValue());
						bookingDo.setProductID(productDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getOfficeIdValue())){
						OfficeDO officeDO = new OfficeDO();
						officeDO.setOfficeId(xmlParent.getOfficeIdValue());
						bookingDo.setOfficeID(officeDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getServiceIdValue())){
						ServiceDO serviceDO = new ServiceDO();
						serviceDO.setServiceId(xmlParent.getServiceIdValue());
						bookingDo.setServiceID(serviceDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getModeIdValue())){
						ModeDO modeDO = new ModeDO();
						modeDO.setModeId(xmlParent.getModeIdValue());
						bookingDo.setMode(modeDO);
					 }
					bookingDo.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
					if(xmlParent.getConsigneeDtls()!=null){
						ConsigneeDO consigneeDO = new ConsigneeDO();
						PropertyUtils.copyProperties(consigneeDO,xmlParent.getConsigneeDtls());
						consigneeDO.setConsigneeId(null);
						Set<ConsigneeAddressDO> consigneeAddressDOSet = null;
							if(xmlParent.getConsigneeDtls().getConsigneeAddressList() != null ){
								consigneeAddressDOSet = new HashSet<ConsigneeAddressDO>();
								for (ConsigneeAddressList consigneeAddress : xmlParent.getConsigneeDtls().getConsigneeAddressList()) {
									ConsigneeAddressDO	consigneeAddressDO = new ConsigneeAddressDO();
									PropertyUtils.copyProperties(consigneeAddressDO,consigneeAddress);
									consigneeAddressDO.setConsgAddrId(null);
									if(!StringUtil.isEmptyInteger(consigneeAddress.getAreaIdValue())){
										AreaDO areaDO = new AreaDO();
										areaDO.setAreaId(consigneeAddress.getAreaIdValue());
										consigneeAddressDO.setAreaDO(areaDO);
									}
									if(!StringUtil.isEmptyInteger(consigneeAddress.getCountryIdValue())){
										CountryDO countryDO = new CountryDO();
										countryDO.setCountryId(consigneeAddress.getCountryIdValue());
										consigneeAddressDO.setCountry(countryDO);
									}
									if(!StringUtil.isEmptyInteger(consigneeAddress.getAreaIdValue())){
										CityDO cityDO = new CityDO();
										cityDO.setCityId(consigneeAddress.getCityIdValue());
										consigneeAddressDO.setCity(cityDO);
									}
									if(!StringUtil.isEmptyInteger(consigneeAddress.getAreaIdValue())){
										StateDO stateDO = new StateDO();
										stateDO.setStateId(consigneeAddress.getStateIdValue());
										consigneeAddressDO.setState(stateDO);
									}
									consigneeAddressDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
									consigneeAddressDOSet.add(consigneeAddressDO);
								}
								consigneeDO.setAddresses(consigneeAddressDOSet);

							}
							bookingDo.setConsigneeID(consigneeDO);
						
					}
					
					if(xmlParent.getConsignerDetails() != null){
						
						ConsignerInfoDO consignerInfoDO = new ConsignerInfoDO();
						
						PropertyUtils.copyProperties(consignerInfoDO,xmlParent.getConsignerDetails());
						consignerInfoDO.setConsignerId(null);
						Set<ConsignerAddressDO> consignerAddressDOSet = null;
						ConsignerAddressDO consignerAddressDO = null;
						if(xmlParent.getConsignerDetails().getConsignorAddressDtls() != null && !xmlParent.getConsignerDetails().getConsignorAddressDtls().isEmpty()  ){
							consignerAddressDOSet = new HashSet<ConsignerAddressDO>(xmlParent.getConsignerDetails().getConsignorAddressDtls().size());
							for (ConsignorAddressDtls consignerAddress : xmlParent.getConsignerDetails().getConsignorAddressDtls()) {
								consignerAddressDO = new ConsignerAddressDO();
								PropertyUtils.copyProperties(consignerAddressDO,consignerAddress);
								consignerAddressDO.setConsignerInfoId(null);
								if(!StringUtil.isEmptyInteger(consignerAddress.getConsignerAreaId())){
									AreaDO areaDO = new AreaDO();
									areaDO.setAreaId(consignerAddress.getConsignerAreaId());
									consignerAddressDO.setAreaDO(areaDO);
								}
								if(!StringUtil.isEmptyInteger(consignerAddress.getConsignerCountryId())){
									CountryDO countryDO = new CountryDO();
									countryDO.setCountryId(consignerAddress.getConsignerCountryId());
									consignerAddressDO.setCountry(countryDO);
								}
								if(!StringUtil.isEmptyInteger(consignerAddress.getConsignerPincodeId())){
									PincodeDO pincodeDO = new PincodeDO();
									pincodeDO.setPincodeId(consignerAddress.getConsignerPincodeId());
									consignerAddressDO.setPincodeId(pincodeDO);
								}
								if(!StringUtil.isEmptyInteger(consignerAddress.getConsignerStateId())){
									StateDO stateDO = new StateDO();
									stateDO.setStateId(consignerAddress.getConsignerStateId());
									consignerAddressDO.setState(stateDO);
								}
								consignerAddressDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
								consignerAddressDOSet.add(consignerAddressDO);
							}
							consignerInfoDO.setConsignorAddresses(consignerAddressDOSet);
							
						}
						bookingDo.setConsignorID(consignerInfoDO);
					}
					
					if(!StringUtil.isEmptyInteger(xmlParent.getCityIdValue())){
						CityDO cityDO = new CityDO();
						cityDO.setCityId(xmlParent.getCityIdValue());
						bookingDo.setCityDO(cityDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getChannelTypeIdValue())){
						ChannelDO channelDO = new ChannelDO();
						channelDO.setChannelTypeId(xmlParent.getChannelTypeIdValue());
						bookingDo.setChannelTypeID(channelDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getHeldupIdValue())){
						HeldUpReleaseDO heldUpReleaseDO = new HeldUpReleaseDO();
						heldUpReleaseDO.setHeldupReleaseId(xmlParent.getHeldupIdValue());
						bookingDo.setHeldupDO(heldUpReleaseDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getCurrencyIdValue())){
						CurrencyDO currencyDO = new CurrencyDO();
						currencyDO.setCurrencyId(xmlParent.getCurrencyIdValue());
						bookingDo.setCurrency(currencyDO);
					 }
				
					if(!StringUtil.isEmptyInteger(xmlParent.getValueCurrencyIdValue())){
						CurrencyDO valueCurrency = new CurrencyDO();
						valueCurrency.setCurrencyId(xmlParent.getValueCurrencyIdValue());
						bookingDo.setValueCurrency(valueCurrency);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getEmployeeIdForPickupBoyIdValue())){
						EmployeeDO pickupBoyDO = new EmployeeDO();
						pickupBoyDO.setEmployeeId(xmlParent.getEmployeeIdForPickupBoyIdValue());
						bookingDo.setEmployeeIdForPickupBoyId(pickupBoyDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getCashCollectionCenterIdValue())){
						OfficeDO cccOfficeDO = new OfficeDO();
						cccOfficeDO.setOfficeId(xmlParent.getCashCollectionCenterIdValue());
						bookingDo.setCashCollectionCenter(cccOfficeDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getCommodityIdValue())){
						InternationalCommodityDO internationalCommodityDO = new InternationalCommodityDO();
						internationalCommodityDO.setIntlCommodityId(xmlParent.getCommodityIdValue());
						bookingDo.setCommodityId(internationalCommodityDO);
					 }
					
					if(!StringUtil.isEmptyInteger(xmlParent.getDomesticCommodityIdValue())){
						CommodityDO commodityDO = new CommodityDO();
						commodityDO.setCommodityId(xmlParent.getDomesticCommodityIdValue());
						bookingDo.setDomesticCommodityId(commodityDO);
					 }
					
					if(xmlParent.getBookingMnpItems()!=null && !xmlParent.getBookingMnpItems().isEmpty()){
						Set<BookingItemListDO> mnpSet = new HashSet<BookingItemListDO>(xmlParent.getBookingMnpItems().size());
						for (BookingMnpItems  mnpItms :xmlParent.getBookingMnpItems()){
							BookingItemListDO mnpItmDo = new BookingItemListDO();
							PropertyUtils.copyProperties(mnpItmDo,mnpItms);
							mnpItmDo.setBookingItemlistId(null);
							if(!StringUtil.isEmptyInteger(mnpItms.getCommodityIdValue())){
								CommodityDO commodityDO = new CommodityDO();
								commodityDO.setCommodityId(mnpItms.getCommodityIdValue());
								mnpItmDo.setCommodity(commodityDO);
							}
							mnpSet.add(mnpItmDo);
						}
						bookingDo.setMnpItems(mnpSet);
					}
					
					
					if(xmlParent.getBookingNonDoxPaperWrkItems()!=null && !xmlParent.getBookingNonDoxPaperWrkItems().isEmpty()){
						Set<BookingItemListDO> nonDoxSet = new HashSet<BookingItemListDO>(xmlParent.getBookingNonDoxPaperWrkItems().size());
						for (BookingNonDoxPaperWrkItems  nonDox :xmlParent.getBookingNonDoxPaperWrkItems()){
							BookingItemListDO nonDoxItmDo = new BookingItemListDO();

							PropertyUtils.copyProperties(nonDoxItmDo,nonDox);
							nonDoxItmDo.setBookingItemlistId(null);
							if(!StringUtil.isEmptyInteger(nonDox.getPaperWorkItemIdValue())){
								PaperWorkItemDO paperWorkItemDO = new PaperWorkItemDO();
								paperWorkItemDO.setPaperWorkItemId(nonDox.getPaperWorkItemIdValue());
								nonDoxItmDo.setPaperWorkItemDO(paperWorkItemDO);
							}
							nonDoxSet.add(nonDoxItmDo);
						}
						bookingDo.setNonDoxPaperWrkItems(nonDoxSet);
					}
					
					
					if(xmlParent.getBookingVasChrgesItems()!=null && !xmlParent.getBookingVasChrgesItems().isEmpty()){
						Set<VasProductChargesDO> vasChrgSet = new HashSet<VasProductChargesDO>(xmlParent.getBookingVasChrgesItems().size());
						for (BookingVasChrgesItems  vasChrg :xmlParent.getBookingVasChrgesItems()){
							VasProductChargesDO vasChrgDo = new VasProductChargesDO();
							PropertyUtils.copyProperties(vasChrgDo,vasChrg);
							vasChrgDo.setBookingVasChargeId(null);
							
							
							vasChrgSet.add(vasChrgDo);
						}
						bookingDo.setVasCharges(vasChrgSet);
					}
					bookingDo.setDiFlag(BranchDBAdminConstant.DI_FLAG);
				 bookingDolist.add(bookingDo);
			
		}
		
		
		} catch (Exception obj) {
			CGStackTraceUtil.getStackTraceException(obj,  LOGGER, "prepareDataForBookingDO", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::prepareDataForBookingDO::Exception occured:"
					+obj.getMessage());
			throw new CGBusinessException(obj.getMessage());
		}
		LOGGER.debug("ParserMappingUtil : copyBookingProperties() : END");
		return bookingDolist;

	}

	/**
	 * Added by Narasimha Rao Kattunga Creating delivery manifest Object from
	 * Data ibject.
	 *
	 * @param fromObject the from object
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<DeliveryDO> createDlvMnfstDOFromDataObject(
			DeliveyManifestData fromObject) throws CGBusinessException{
		List<DeliveryDO> dlvMnfstList = new ArrayList<DeliveryDO>(0);
		try {
			for (DeliveyManifestData.DeliveryManifest issueData : fromObject
					.getDeliveryManifest()) {
				DeliveryDO dlvMnfstDO = null;				
				String dlvCode = issueData.getDeliveryCode();
				if (StringUtils.equalsIgnoreCase(dlvCode, "FDM")) {
					dlvMnfstDO = new FranchiseDeliveryManifestDO();
				} else if (StringUtils.equalsIgnoreCase(dlvCode, "FHR")) {
					dlvMnfstDO = new FranchiseDeliveryManifestHandOverDO();
				} else if (StringUtils.equalsIgnoreCase(dlvCode, "DRS")) {
					dlvMnfstDO = new DeliveryRunDO();
				} else if (StringUtils.equalsIgnoreCase(dlvCode, "NDRS")) {
					dlvMnfstDO = new NonDeliveryRunDO();
				} else if (StringUtils.equalsIgnoreCase(dlvCode, "POD")) {
					dlvMnfstDO = new PODDeliveryDO();
				} else {
					dlvMnfstDO = new DeliveryDO();
				}
				PropertyUtils.copyProperties(dlvMnfstDO, issueData);
				dlvMnfstDO.setDeliveryId(null);
				dlvMnfstDO.setTransactionCrDate(StringUtil.isNull(issueData
						.getTransCreateDate()) ? null : DateFormatterUtil
						.convertXMLGregorianCalendarToDate(issueData
								.getTransCreateDate()));
				dlvMnfstDO.setTransactionLastMdfDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				if (!StringUtil.isEmptyInteger(issueData.getEmployeeId())) {
					EmployeeDO loginEmp = new EmployeeDO();
					loginEmp.setEmployeeId(issueData.getEmployeeId());
					dlvMnfstDO.setEmployee(loginEmp);
				}
				if (!StringUtil.isEmptyInteger(issueData.getOfficeId())) {
					OfficeDO office = new OfficeDO();
					office.setOfficeId(issueData.getOfficeId());
					dlvMnfstDO.setBranch(office);
				}
				if (!StringUtil.isEmptyInteger(issueData.getReportOffId())) {
					OfficeDO repOffice = new OfficeDO();
					repOffice.setOfficeId(issueData.getReportOffId());
					dlvMnfstDO.setReportingBranch(repOffice);
				}
				if (!StringUtil.isEmptyInteger(issueData.getFranchiseeId())) {
					FranchiseeDO franchisee = new FranchiseeDO();
					franchisee.setFranchiseeId((issueData.getFranchiseeId()));
					dlvMnfstDO.setFranchisee(franchisee);
				}
				if (!StringUtil.isEmptyInteger(issueData.getDocId())) {
					DocumentDO document = new DocumentDO();
					document.setDocumentId(issueData.getDocId());
					dlvMnfstDO.setDocument(document);
				}
				if (!StringUtil.isEmptyInteger(issueData.getReasonId())) {
					ReasonDO reason = new ReasonDO();
					reason.setReasonId(issueData.getReasonId());
					dlvMnfstDO.setReason(reason);
				}
				if (!StringUtil.isEmptyInteger(issueData.getProductId())) {
					ProductDO product = new ProductDO();
					product.setProductId(issueData.getProductId());
					dlvMnfstDO.setProduct(product);
				}
				if (!StringUtil.isEmptyInteger(issueData.getServiceId())) {
					ServiceDO service = new ServiceDO();
					service.setServiceId(issueData.getServiceId());
					dlvMnfstDO.setService(service);
				}
				if (!StringUtil.isEmptyInteger(issueData.getHeldUpReleaseId())) {
					HeldUpReleaseDO helupRel = new HeldUpReleaseDO();
					helupRel.setHeldupReleaseId(issueData.getHeldUpReleaseId());
					dlvMnfstDO.setHeldupDO(helupRel);
				}
				if (!StringUtil.isEmptyInteger(issueData
						.getIdentityProofDocId())) {
					IdentityProofDocDO identyPfDoc = new IdentityProofDocDO();
					identyPfDoc.setIdentityProofDocId(issueData
							.getHeldUpReleaseId());
					dlvMnfstDO.setIdentityProofDO(identyPfDoc);
				}
				if (!StringUtil.isEmptyInteger(issueData.getConsigneeId())) {
					ConsigneeDO cne = new ConsigneeDO();
					cne.setConsigneeId(issueData.getConsigneeId());
					dlvMnfstDO.setConsignee(cne);
				}
				if (!StringUtil.isEmptyInteger(issueData.getDestPinCodeId())) {
					PincodeDO destPin = new PincodeDO();
					destPin.setPincodeId(issueData.getDestPinCodeId());
					dlvMnfstDO.setDestPinCode(destPin);
				}
				dlvMnfstDO.setLastTransModifiedDate(DateFormatterUtil.getTimeStampFromDateSlashFormatString(DateFormatterUtil.todayDate()));
				dlvMnfstList.add(dlvMnfstDO);
			}
		} catch (Exception e) {
			CGStackTraceUtil.getStackTraceException(e,  LOGGER, "createDlvMnfstDOFromDataObject", UtilConstants.LOGGER_LEVEL_ERROR);
			LOGGER.error("TransferObjectToDomainObjectConverter::createDlvMnfstDOFromDataObject::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e.getMessage());

		}
		return dlvMnfstList;
	}

}
