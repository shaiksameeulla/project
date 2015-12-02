package src.com.dtdc.mdbServices.booking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.mdbDao.booking.BookingMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.booking.BookingDBSyncDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.master.CurrencyDO;
import com.dtdc.domain.master.agent.AgencyDO;
import com.dtdc.domain.master.agent.AgentDO;
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
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.paperwork.PaperWorkItemDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.domain.master.product.InternationalCommodityDO;
import com.dtdc.domain.master.product.InternationalPaperworkItemsDO;
import com.dtdc.domain.master.product.ItemDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.VasInternationalDO;
import com.dtdc.domain.master.product.VasProductChargesDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.to.booking.BookingDBSyncTO;
import com.dtdc.to.common.ConsigneeAddressTO;
import com.dtdc.to.common.ConsignerAddressTO;
import com.dtdc.to.internationalbooking.BookingItemListTO;
import com.dtdc.to.internationalbooking.VasProductChargesTO;

// TODO: Auto-generated Javadoc
/**
 * The Class BookingMDBServiceImpl.
 */
public class BookingMDBServiceImpl implements BookingMDBService {

	/** The booking mdbdao. */
	private BookingMDBDAO bookingMDBDAO;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(BookingMDBServiceImpl.class);

	/**
	 * Gets the booking mdbdao.
	 *
	 * @return the booking mdbdao
	 */
	public BookingMDBDAO getBookingMDBDAO() {
		return bookingMDBDAO;
	}

	/**
	 * Sets the booking mdbdao.
	 *
	 * @param bookingMDBDAO the new booking mdbdao
	 */
	public void setBookingMDBDAO(BookingMDBDAO bookingMDBDAO) {
		this.bookingMDBDAO = bookingMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.BookingMDBService#saveOrUpdateBookings(CGBaseTO)
	 */
	@Override
	public boolean saveOrUpdateBookings(CGBaseTO bookingTO)
	throws CGBusinessException {
		@SuppressWarnings("unchecked")
		List<BookingDBSyncTO> bookingTOList = (List<BookingDBSyncTO>) bookingTO
		.getBaseList();
		boolean isSaved = Boolean.TRUE;
		try {
			LOGGER.debug("BookingMDBServiceImpl : saveOrUpdateBookings0 : Start..:"
					+ bookingTOList.size());
			isSaved = saveOrUpdateBookings(bookingTOList);
		} catch (CGBusinessException e) {
				LOGGER.error(
					"Error occured while in  saveOrUpdateBookingst : Cause:", e
					.getCause().getMessage());
		}
		return isSaved;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.BookingMDBService#saveOrUpdateBookings(List)
	 */
	@Override
	public boolean saveOrUpdateBookings(List<BookingDBSyncTO> bookingTOs)
	throws CGBusinessException {
		List<BookingDBSyncDO> bookings = null;
		String bokingStatus = "";
		boolean isSaved = Boolean.FALSE;
		try {
			LOGGER.debug("BookingMDBServiceImpl : saveOrUpdateBookings : Start..");
			if (bookingTOs != null && bookingTOs.size() > 0) {
				bookings = new ArrayList<BookingDBSyncDO>();
				bookings = bookingConverter(bookingTOs);
				if (bookings != null && bookings.size() > 0) {
					// Save Or Updatye Bookings
					bokingStatus = bookingMDBDAO.saveOrUpdateBookings(bookings);
					if (StringUtils.isNotEmpty(bokingStatus)) {
						isSaved = Boolean.TRUE;
						// Sending email for failure consignments

					}
				}
			}
			LOGGER.debug("BookingMDBServiceImpl : saveOrUpdateBookings : End..");
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error occured while in  saveOrUpdateBookingst : Cause:", e
					.getCause().getMessage());
		}
		return isSaved;
	}

	/**
	 * Booking converter.
	 *
	 * @param bookingTOs the booking t os
	 * @return the list
	 */
	private List<BookingDBSyncDO> bookingConverter(
			List<BookingDBSyncTO> bookingTOs) {
		List<BookingDBSyncDO> bookings = new ArrayList<BookingDBSyncDO>();
		try {
			for (BookingDBSyncTO bookingTO : bookingTOs) {
				BookingDBSyncDO booking = new BookingDBSyncDO();
				booking.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
				booking.setDiFlag("N");
				// Setting bookingId - Update mode
				Integer bookingId = 0;
				Integer consigneeId = 0;
				Integer consignorId = 0;
				List<Object[]> results = bookingMDBDAO.getBookingId(bookingTO
						.getConsignmentNumber());
				for (Object[] objArray : results) {
					if (objArray[0] != null) {
						bookingId = ((Integer) objArray[0]);
					}
					if (objArray[1] != null) {
						consigneeId = ((Integer) objArray[1]);
					}	
					if (objArray[2] != null) {
						consignorId = ((Integer) objArray[2]);
					}	
				}

				if (bookingId > 0) {
					booking.setBookingId(bookingId);
				}
				booking.setBookingType(bookingTO.getBookingType());
				if (bookingTO.getCustomerId() > 0) {
					CustomerDO cust = new CustomerDO();
					cust.setCustomerId(bookingTO.getCustomerId());
					booking.setCustomerId(cust);
				}
				if (bookingTO.getFranchiseeId() > 0) {
					FranchiseeDO frnch = new FranchiseeDO();
					frnch.setFranchiseeId(bookingTO.getFranchiseeId());
					booking.setFranchiseeId(frnch);
				}
				if (bookingTO.getDocumentID() > 0) {
					DocumentDO doc = new DocumentDO();
					doc.setDocumentId(bookingTO.getDocumentID());
					booking.setDocumentID(doc);
				}
				if (bookingTO.getPincodeId() > 0) {
					PincodeDO pin = new PincodeDO();
					pin.setPincodeId(bookingTO.getPincodeId());
					booking.setPincodeDO(pin);
				}
				if (bookingTO.getItemID() > 0) {
					ItemDO item = new ItemDO();
					item.setItemId(bookingTO.getItemID());
					booking.setItemID(item);
				}
				if (bookingTO.getAgentID() > 0) {
					AgentDO agent = new AgentDO();
					agent.setAgentId(bookingTO.getAgentID());
					booking.setAgentID(agent);
				}
				if (bookingTO.getEmployeeId() > 0) {
					EmployeeDO emp = new EmployeeDO();
					emp.setEmployeeId(bookingTO.getEmployeeId());
					booking.setEmployeeId(emp);
				}
				if (bookingTO.getProductID() > 0) {
					ProductDO prod = new ProductDO();
					prod.setProductId(bookingTO.getProductID());
					booking.setProductID(prod);
				}
				if (bookingTO.getOfficeID() > 0) {
					OfficeDO off = new OfficeDO();
					off.setOfficeId(bookingTO.getOfficeID());
					booking.setOfficeID(off);
				}
				if (bookingTO.getServiceID() > 0) {
					ServiceDO service = new ServiceDO();
					service.setServiceId(bookingTO.getServiceID());
					booking.setServiceID(service);
				}
				if (bookingTO.getModeId() > 0) {
					ModeDO mode = new ModeDO();
					mode.setModeId(bookingTO.getModeId());
					booking.setMode(mode);
				}
				if (bookingTO.getDestCityId() > 0) {
					CityDO city = new CityDO();
					city.setCityId(bookingTO.getDestCityId());
					booking.setCityDO(city);
				}
				if (bookingTO.getChannelTypeID() > 0) {
					ChannelDO channel = new ChannelDO();
					channel.setChannelTypeId(bookingTO.getChannelTypeID());
					booking.setChannelTypeID(channel);

				}
				if (bookingTO.getHeldUpId() > 0) {
					HeldUpReleaseDO heldUp = new HeldUpReleaseDO();
					heldUp.setHeldupReleaseId(bookingTO.getHeldUpId());
					booking.setHeldupDO(heldUp);
				}
				if (bookingTO.getCurrencyId() > 0) {
					CurrencyDO curr = new CurrencyDO();
					curr.setCurrencyId(bookingTO.getCurrencyId());
					booking.setCurrency(curr);
				}
				if (bookingTO.getValueCurrencyId() > 0) {
					CurrencyDO valCurr = new CurrencyDO();
					valCurr.setCurrencyId(bookingTO.getValueCurrencyId());
					booking.setValueCurrency(valCurr);
				}
				if (bookingTO.getValueCurrencyId() > 0) {
					CurrencyDO valCurr = new CurrencyDO();
					valCurr.setCurrencyId(bookingTO.getValueCurrencyId());
					booking.setValueCurrency(valCurr);
				}
				if (bookingTO.getVolumetricWght() > 0) {
					booking.setVolumetricWght(bookingTO.getVolumetricWght());
				}
				booking.setConsgmntType(bookingTO.getConsgmntType());
				if (bookingTO.getLength() > 0){
					booking.setLength(bookingTO.getLength());
				}
				if (bookingTO.getBreadth() > 0) {
					booking.setBreadth(bookingTO.getBreadth());
				}	
				if (bookingTO.getHeight() > 0) {
					booking.setHeight(bookingTO.getHeight());
				}	
				booking.setDescription(bookingTO.getDescription());
				booking.setInFavourOf(bookingTO.getInFavourOf());
				if (bookingTO.getFinalWeight() > 0) {
					booking.setFinalWeight(bookingTO.getFinalWeight());
				}	
				booking.setConsgmntStatus(bookingTO.getConsgmntStatus());
				booking.setWeighingType(bookingTO.getWeighingType());
				booking.setManifestNumber(bookingTO.getManifestNumber());
				if (StringUtils.isNotEmpty(bookingTO.getBookingDate())) {
					booking.setBookingDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bookingTO
									.getBookingDate()));
				}	
				booking.setBookingTime(bookingTO.getBookingTime());
				booking.setToPay(bookingTO.getToPay());
				if (bookingTO.getCodAmount() > 0) {
					booking.setCodAmount(bookingTO.getCodAmount());
				}	
				if (bookingTO.getFodAmount() > 0) {
					booking.setFodAmount(bookingTO.getFodAmount());
				}	
				booking.setCustRefNo(bookingTO.getCustRefNo());
				booking.setSerialNo(bookingTO.getSerialNo());
				if (bookingTO.getDoxAmount() > 0) {
					booking.setDoxAmount(bookingTO.getDoxAmount());
				}	
				if (bookingTO.getNonDoxAmount() > 0) {
					booking.setNonDoxAmount(bookingTO.getNonDoxAmount());
				}	
				booking.setRemoteDeliveryRemarks(bookingTO
						.getRemoteDeliveryRemarks());
				booking.setRemarks(bookingTO.getRemarks());
				booking.setAreaNameLtDox(bookingTO.getAreaNameLtDox());
				booking.setPassportDetails(bookingTO.getPassportDetails());
				booking.setExtrDlvCharges(bookingTO.getExtrDlvCharges());
				booking.setExtDlvRemarks(bookingTO.getExtDlvRemarks());
				if (bookingTO.getAmount() > 0) {
					booking.setAmount(bookingTO.getAmount());
				}	
				if (bookingTO.getActualWeight() > 0) {
					booking.setActualWeight(bookingTO.getActualWeight());
				}	
				booking.setParentCnNumber(bookingTO.getParentCnNumber());
				booking.setPaid(bookingTO.getPaid());
				if (StringUtils.isNotEmpty(bookingTO.getExpectedDlvDate())) {
					booking.setExpectedDlvDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bookingTO
									.getExpectedDlvDate()));
				}	
				booking.setInsuredBy(bookingTO.getInsuredBy());
				if (bookingTO.getInsuranceAmount() > 0) {
					booking.setInsuranceAmount(bookingTO.getInsuranceAmount());
				}	
				booking.setCodRemarks(bookingTO.getCodRemarks());
				booking.setFodRemarks(bookingTO.getFodRemarks());
				if (bookingTO.getInvoiceValue() > 0) {
					booking.setInvoiceValue(bookingTO.getInvoiceValue());
				}	
				if (bookingTO.getExtraBookingCharge() > 0) {
					booking.setExtraBookingCharge(bookingTO
							.getExtraBookingCharge());
				}	
				booking.setConsignmentNumber(bookingTO.getConsignmentNumber());
				booking.setCallNumber(bookingTO.getCallNumber());
				booking.setSecurityPouchNo(bookingTO.getSecurityPouchNo());
				booking.setNoOfPieces(bookingTO.getNoOfPieces());
				booking.setPrioritySticker(bookingTO.getPrioritySticker());
				booking.setCreateChildCn(bookingTO.getCreateChildCn());
				booking.setModeOfCollection(bookingTO.getModeOfCollection());
				booking.setOpenMnfstNo(bookingTO.getOpenMnfstNo());
				booking.setOperationFreedom(bookingTO.getOperationFreedom());
				booking.setCaptureVolumetric(bookingTO.getCaptureVolumetric());
				booking.setGlobalDp(bookingTO.getGlobalDp());
				booking.setCpdp(bookingTO.getCpdp());
				booking.setCaptureRefNo(bookingTO.getCaptureRefNo());
				booking.setIsInsured(bookingTO.getIsInsured());
				booking.setDbServer(bookingTO.getDbServer());
				booking.setValidation(bookingTO.getValidation());
				booking.setUpdatedFromProcess(bookingTO.getUpdatedFromProcess());
				//booking.setDiFlag(bookingTO.getDiFlag());
				booking.setBillingStatus(bookingTO.getBillingStatus());
				booking.setChangedAfterBilling(bookingTO
						.getChangedAfterBilling());
				if (bookingTO.getFrieght() > 0) {
					booking.setFrieght(bookingTO.getFrieght());
				}	
				if (bookingTO.getDiscountInPerc() > 0) {
					booking.setDiscountInPerc(bookingTO.getDiscountInPerc());
				}	
				if (bookingTO.getServiceTax() > 0) {
					booking.setServiceTax(bookingTO.getServiceTax());
				}	
				if (bookingTO.getEmployeeIdForPickupBoyId() > 0) {
					EmployeeDO pkEmp = new EmployeeDO();
					pkEmp.setEmployeeId(bookingTO.getEmployeeIdForPickupBoyId());
					booking.setEmployeeIdForPickupBoyId(pkEmp);
				}
				if (bookingTO.getRiskSurchgAmt() > 0) {
					booking.setRiskSurchgAmt(bookingTO.getRiskSurchgAmt());
				}	
				if (bookingTO.getDiplomaticOrRemoteAreaCharge() > 0) {
					booking.setDiplomaticOrRemoteAreaCharge(bookingTO
							.getDiplomaticOrRemoteAreaCharge());
				}	
				if (bookingTO.getServChrgAmt() > 0) {
					booking.setServChrgAmt(bookingTO.getServChrgAmt());
				}	
				booking.setVasProductCode(bookingTO.getVasProductCode());
				booking.setBookingDivisionID(bookingTO.getBookingDivisionID());
				booking.setPickUpTime(bookingTO.getPickUpTime());
				if (bookingTO.getCashCollectionCenterId() > 0) {
					OfficeDO cashOff = new OfficeDO();
					cashOff.setOfficeId(bookingTO.getCashCollectionCenterId());
					booking.setCashCollectionCenter(cashOff);
				}
				booking.setPaymentMode(bookingTO.getPaymentMode());
				booking.setCustomerIndustry(bookingTO.getCustomerIndustry());
				booking.setIsStaff(bookingTO.getIsStaff());

				if (bookingTO.getTsAmount() > 0) {
					booking.setTsAmount(bookingTO.getTsAmount());
				}	
				if (bookingTO.getRiskSurCharge() > 0) {
					booking.setRiskSurCharge(bookingTO.getRiskSurCharge());
				}	
				if (bookingTO.getPenaltyCharge() > 0) {
					booking.setPenaltyCharge(bookingTO.getPenaltyCharge());
				}	
				if (bookingTO.getMiscCharge() > 0) {
					booking.setMiscCharge(bookingTO.getMiscCharge());
				}	
				booking.setInsuranceOption(bookingTO.getInsuranceOption());
				if (bookingTO.getUserId() > 0) {
					booking.setUserId(bookingTO.getUserId());
				}	
				booking.setTransactionId(bookingTO.getTransactionId());
				if (StringUtils
						.isNotEmpty(bookingTO.getRateLastCalculateDate())) {
					booking.setRateLastCalculateDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bookingTO
									.getRateLastCalculateDate()));
				}
				booking.setDutyPaidBy(bookingTO.getDutyPaidBy());
				if (bookingTO.getDutyCollectedAmount() > 0) {
					booking.setDutyCollectedAmount(bookingTO
							.getDutyCollectedAmount());
				}	
				booking.setInsuranceRemarks(bookingTO.getInsuranceRemarks());
				booking.setShipmentCategory(bookingTO.getShipmentCategory());
				if (bookingTO.getIntlCommodityId() > 0) {
					InternationalCommodityDO intlComm = new InternationalCommodityDO();
					intlComm.setIntlCommodityId(bookingTO.getIntlCommodityId());
					booking.setCommodityId(intlComm);

				}
				if (bookingTO.getDomesticCommodityId() != null) {
					CommodityDO domComm = new CommodityDO();
					domComm.setCommodityId(bookingTO.getDomesticCommodityId());
					booking.setDomesticCommodityId(domComm);
				}
				if (bookingTO.getAgencyId() > 0) {
					AgencyDO agency = new AgencyDO();
					agency.setAgencyId(bookingTO.getAgencyId());
					booking.setAgency(agency);
				}
				if (StringUtils.isNotEmpty(bookingTO.getServerDate())) {
					booking.setServerDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bookingTO
									.getServerDate()));
				}	
				booking.setNodeId(bookingTO.getNodeId());
				if (StringUtils
						.isNotEmpty(bookingTO.getLastTransModifiedDate())) {
					booking.setLastTransModifiedDate(DateFormatterUtil
							.slashDelimitedstringToDDMMYYYYFormat(bookingTO
									.getLastTransModifiedDate()));
				}
				if (bookingTO.getTaxableAmount() > 0) {
					booking.setTaxableAmount(bookingTO.getTaxableAmount());
				}	
				if (bookingTO.getTaxPayable() > 0) {
					booking.setTaxPayable(bookingTO.getTaxPayable());
				}    
				if (bookingTO.getDiscount() > 0) {
					booking.setDiscount(bookingTO.getDiscount());
				}	
				booking.setAgentRefNo(bookingTO.getAgentRefNo());
				booking.setPurNo(bookingTO.getPurNo());
				// Setting Consignee
				booking = setConsigneeDetails(booking, bookingTO, consigneeId);
				// Setting Consigner
				booking = setConsignorDetails(booking, bookingTO, consignorId);
				// Setting Vas products
				booking = setVasProdDetails(booking, bookingTO);
				// Setting paperwork items
				booking = setNonDoxPaperWorkDetails(booking, bookingTO);
				bookings.add(booking);
			}
		} catch (Exception e) {
			LOGGER.error("BookingMDBServiceImpl::bookingConverter::Exception occured:"
					+e.getMessage());
		}

		return bookings;
	}

	/**
	 * Sets the consignee details.
	 *
	 * @param bookingDO the booking do
	 * @param bookingTO the booking to
	 * @param consigneeId the consignee id
	 * @return the booking db sync do
	 * @throws CGBusinessException the cG business exception
	 */
	private BookingDBSyncDO setConsigneeDetails(BookingDBSyncDO bookingDO,
			BookingDBSyncTO bookingTO, Integer consigneeId)
	throws CGBusinessException {
		Integer cneAddId = 0;
		if (StringUtils.isNotEmpty(bookingTO.getCneFirstName())) {
			ConsigneeDO consinee = new ConsigneeDO();
			// Need to set The id for Update
			if (consigneeId > 0) {
				consinee.setConsigneeId(consigneeId);
				cneAddId = bookingMDBDAO.getCneAddressId(consigneeId);

			}
			consinee.setFirstName(bookingTO.getCneFirstName());
			consinee.setLastName(bookingTO.getCneLastName());
			consinee.setSurName(bookingTO.getCneSurName());
			consinee.setEmail(bookingTO.getCneEmail());
			consinee.setConsigneeCode(bookingTO.getConsignerCode());
			consinee.setFax(bookingTO.getCneFax());
			consinee.setBusinessName(bookingTO.getCneBusinessName());
			Set<ConsigneeAddressDO> addresses = new HashSet<ConsigneeAddressDO>();
			Set<ConsigneeAddressTO> addressesTOs = null;
			addressesTOs = bookingTO.getConsigneeAddress();
			for (ConsigneeAddressTO cneAddress : addressesTOs) {
				ConsigneeAddressDO cneAdd = new ConsigneeAddressDO();
				// Set the Id for Update
				if (cneAddId > 0) {
					cneAdd.setConsgAddrId(cneAddId);
				}	
				cneAdd.setStreet1(cneAddress.getStreet1());
				cneAdd.setStreet2(cneAddress.getStreet2());
				cneAdd.setStreet3(cneAddress.getStreet3());
				cneAdd.setBuildingName(cneAddress.getBuildingName());
				cneAdd.setBuildingBlock(cneAddress.getBuildingBlock());
				if (cneAddress.getCityId() > 0) {
					CityDO city = new CityDO();
					city.setCityId(cneAddress.getCityId());
					cneAdd.setCity(city);
				}
				if (cneAddress.getStateId() > 0) {
					StateDO state = new StateDO();
					state.setStateId(cneAddress.getStateId());
					cneAdd.setState(state);
				}
				if (cneAddress.getAreaId() > 0) {
					AreaDO area = new AreaDO();
					area.setAreaId(cneAddress.getAreaId());
					cneAdd.setAreaDO(area);
				}
				if (cneAddress.getCountryId() > 0) {
					CountryDO country = new CountryDO();
					country.setCountryId(cneAddress.getCountryId());
					cneAdd.setCountry(country);
				}
				if (cneAddress.getPincodeId() > 0) {
					PincodeDO pinCode = new PincodeDO();
					pinCode.setPincodeId(cneAddress.getPincodeId());
					cneAdd.setPincode(pinCode);
				}
				cneAdd.setCneOptionalPinCode(cneAddress.getCneOptionalPinCode());
				cneAdd.setCneOptionalState(cneAddress.getCneOptionalState());
				addresses.add(cneAdd);
			}
			if (addresses != null && addresses.size() > 0) {
				consinee.setAddresses(addresses);
				bookingDO.setConsigneeID(consinee);
			}
		}
		return bookingDO;
	}

	/**
	 * Sets the consignor details.
	 *
	 * @param bookingDO the booking do
	 * @param bookingTO the booking to
	 * @param consignorId the consignor id
	 * @return the booking db sync do
	 * @throws CGBusinessException the cG business exception
	 */
	private BookingDBSyncDO setConsignorDetails(BookingDBSyncDO bookingDO,
			BookingDBSyncTO bookingTO, Integer consignorId)
	throws CGBusinessException {
		// Need to set The id for Update
		Integer cnrAddressId = 0;
		if (StringUtils.isNotEmpty(bookingTO.getCnrFirstName())) {
			ConsignerInfoDO consinor = new ConsignerInfoDO();
			if (consignorId > 0) {
				consinor.setConsignerId(consignorId);
				cnrAddressId = bookingMDBDAO.getCnrAddressId(consignorId);
			}
			consinor.setFirstName(bookingTO.getCnrFirstName());
			consinor.setLastName(bookingTO.getCnrLastName());
			consinor.setSurName(bookingTO.getCnrSurName());
			consinor.setEmail(bookingTO.getCnrEmail());
			consinor.setConsignerCode(bookingTO.getConsignerCode());
			consinor.setFax(bookingTO.getCnrFax());
			consinor.setBusinessName(bookingTO.getCnrBusinessName());
			Set<ConsignerAddressDO> addresses = new HashSet<ConsignerAddressDO>();
			Set<ConsignerAddressTO> addressesTOs = null;
			addressesTOs = bookingTO.getConsignorAddresses();
			for (ConsignerAddressTO cnrAddress : addressesTOs) {
				ConsignerAddressDO cnrAdd = new ConsignerAddressDO();
				// Set the Id for Update
				if (cnrAddressId > 0) {
					cnrAdd.setConsigrAddrId(cnrAddressId);
				}	
				cnrAdd.setStreet1(cnrAddress.getStreet1());
				cnrAdd.setStreet2(cnrAddress.getStreet2());
				cnrAdd.setStreet3(cnrAddress.getStreet3());
				cnrAdd.setBuildingName(cnrAddress.getBuildingName());
				cnrAdd.setBuildingBlock(cnrAddress.getBuildingBlock());
				if (cnrAddress.getCityId() > 0) {
					CityDO city = new CityDO();
					city.setCityId(cnrAddress.getCityId());
					cnrAdd.setCity(city);
				}
				if (cnrAddress.getStateId() > 0) {
					StateDO state = new StateDO();
					state.setStateId(cnrAddress.getStateId());
					cnrAdd.setState(state);
				}
				if (cnrAddress.getAreaId() > 0) {
					AreaDO area = new AreaDO();
					area.setAreaId(cnrAddress.getAreaId());
					cnrAdd.setAreaDO(area);
				}
				if (cnrAddress.getCountryId() > 0) {
					CountryDO country = new CountryDO();
					country.setCountryId(cnrAddress.getCountryId());
					cnrAdd.setCountry(country);
				}
				if (cnrAddress.getPincodeId() > 0) {
					PincodeDO pinCode = new PincodeDO();
					pinCode.setPincodeId(cnrAddress.getPincodeId());
					cnrAdd.setPincodeId(pinCode);
				}
				cnrAdd.setCnrOptionalPinCode(cnrAddress.getCnrOptionalPinCode());
				cnrAdd.setCnrOptionalState(cnrAddress.getCnrOptionalState());
				addresses.add(cnrAdd);
			}
			if (addresses != null && addresses.size() > 0) {
				consinor.setConsignorAddresses(addresses);
				bookingDO.setConsignorID(consinor);
			}
		}
		return bookingDO;
	}

	/**
	 * Sets the vas prod details.
	 *
	 * @param bookingDO the booking do
	 * @param bookingTO the booking to
	 * @return the booking db sync do
	 */
	private BookingDBSyncDO setVasProdDetails(BookingDBSyncDO bookingDO,
			BookingDBSyncTO bookingTO) {
		Set<VasProductChargesDO> vasProds = null;
		Set<VasProductChargesTO> vasProdsTOs = null;
		vasProdsTOs = bookingTO.getVasCharges();
		if (vasProdsTOs != null && vasProdsTOs.size() > 0) {
			vasProds = new HashSet<VasProductChargesDO>();
			Iterator<VasProductChargesTO> ite = vasProdsTOs.iterator();
			while (ite.hasNext()) {				
				VasProductChargesTO vasProd = ite.next();
				if (vasProd != null) {
					VasProductChargesDO varcharges = new VasProductChargesDO();
					// Need to set the ID for update
					//TODO
					varcharges.setVasCharge(vasProd.getVasCharge());
					varcharges.setCustImpExpCode(vasProd.getCustImpExpCode());
					varcharges.setCustPassportNo(vasProd.getCustPassportNo());
					if (vasProd.getVasIntlId() > 0) {
						VasInternationalDO vasIntl = new VasInternationalDO();
						vasIntl.setVasInternationalId(vasProd.getVasIntlId());
						varcharges.setVasInternationalId(vasIntl);
					}
					vasProds.add(varcharges);
				}
			}
			bookingDO.setVasCharges(vasProds);
		}
		return bookingDO;
	}

	/**
	 * Sets the non dox paper work details.
	 *
	 * @param bookingDO the booking do
	 * @param bookingTO the booking to
	 * @return the booking db sync do
	 */
	private BookingDBSyncDO setNonDoxPaperWorkDetails(
			BookingDBSyncDO bookingDO, BookingDBSyncTO bookingTO) {
		Set<BookingItemListTO> bookingItemsSet = bookingTO
		.getNonDoxPaperWrkItems();
		Set<BookingItemListDO> bookingItems = null;
		if (bookingItemsSet != null && bookingItemsSet.size() > 0) {
			bookingItems = new HashSet<BookingItemListDO>();
			for (BookingItemListTO bookingItem : bookingItemsSet) {
				// Need to set the Id for update
				//TODO
				BookingItemListDO bookingItemDO = new BookingItemListDO();
				bookingItemDO
				.setNumberOfPieces(bookingItem.getNumberOfPieces());
				bookingItemDO.setSerialNumber(bookingItem.getSerialNumber());
				bookingItemDO.setComments(bookingItem.getComments());
				bookingItemDO.setPaperWorkCN(bookingItem.getPaperWorkCN());
				bookingItemDO.setSetOfDocument(bookingItem.getSetOfDocument());
				if (bookingItem.getItemId() > 0) {
					ItemDO item = new ItemDO();
					item.setItemId(bookingItem.getItemId());
					bookingItemDO.setItem(item);
				}
				if (bookingItem.getCommodityId() > 0) {
					CommodityDO domComm = new CommodityDO();
					domComm.setCommodityId(bookingItem.getCommodityId());
					bookingItemDO.setCommodity(domComm);
				}
				if (bookingItem.getIntlCommodityId() > 0) {
					InternationalCommodityDO intlComm = new InternationalCommodityDO();
					intlComm.setIntlCommodityId(bookingItem
							.getIntlCommodityId());
					bookingItemDO.setIntlCommodityDO(intlComm);
				}
				if (bookingItem.getPaperWorkItemId() > 0) {
					PaperWorkItemDO domPaperWrk = new PaperWorkItemDO();
					domPaperWrk.setPaperWorkItemId(bookingItem
							.getPaperWorkItemId());
					bookingItemDO.setPaperWorkItemDO(domPaperWrk);
				}
				if (bookingItem.getIntlPaperWorkItemId() > 0) {
					InternationalPaperworkItemsDO intlPaperWrk = new InternationalPaperworkItemsDO();
					intlPaperWrk.setPaperworkItemId(bookingItem
							.getIntlPaperWorkItemId());
					bookingItemDO.setIntlPaperWorkItemDO(intlPaperWrk);
				}
				bookingItemDO.setActualPieces(bookingItem.getActualPieces());
				bookingItemDO
				.setIsDocSubmitted(bookingItem.getIsDocSubmitted());
				bookingItemDO.setFileExtnName(bookingItem.getFileExtnName());
				bookingItemDO.setFileName(bookingItem.getFileName());
				bookingItems.add(bookingItemDO);
			}
			bookingDO.setNonDoxPaperWrkItems(bookingItems);
		}
		return bookingDO;
	}

}
