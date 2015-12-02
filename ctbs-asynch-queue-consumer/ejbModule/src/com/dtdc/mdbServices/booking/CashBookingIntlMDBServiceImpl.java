package src.com.dtdc.mdbServices.booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.mdbDao.booking.CashBookingIntlMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.internationalbooking.cashbooking.CashBookingInternationalDO;
import com.dtdc.domain.master.CurrencyDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.ConsignerAddressDO;
import com.dtdc.domain.master.customer.ConsignerInfoDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.CountryDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.InternationalCommodityDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.VasInternationalDO;
import com.dtdc.domain.master.product.VasProductChargesDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.internationalbooking.cashbooking.CashBookingInternationalTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CashBookingIntlMDBServiceImpl.
 */
public class CashBookingIntlMDBServiceImpl implements CashBookingIntlMDBService {

	/** The cash booking international dao. */
	private CashBookingIntlMDBDAO cashBookingInternationalDAO;

	/** The parent child consignment do list. */
	private List<CashBookingDO> parentChildConsignmentDOList = new ArrayList<CashBookingDO>();

	/**
	 * Sets the cash booking international dao.
	 *
	 * @param cashBookingInternationalDAO the new cash booking international dao
	 */
	public void setCashBookingInternationalDAO(
			CashBookingIntlMDBDAO cashBookingInternationalDAO) {

		this.cashBookingInternationalDAO = cashBookingInternationalDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.CashBookingIntlMDBService#saveCashBookingDetails(CashBookingInternationalTO)
	 */
	@Override
	public boolean saveCashBookingDetails(
			CashBookingInternationalTO cashBookingIntlTO)
	throws CGBusinessException {
		List<CashBookingDO> cashBookingDOList = null;
		parentChildConsignmentDOList.clear();
		boolean bookingStatus = false;
		new CashBookingInternationalDO();
		String channelType = cashBookingIntlTO.getChannelType();
		channelType.split(BookingConstants.SLASH);

		// if(tokens[1].equals(BookingConstants.CASH_BOOKING_CODE_CSB)){
		cashBookingDOList = new ArrayList<CashBookingDO>();
		cashBookingDOList = convertTotoDO(cashBookingIntlTO);
		// }
		if (cashBookingDOList != null && !cashBookingDOList.isEmpty()
				&& cashBookingDOList.size() != 0) {
			bookingStatus = cashBookingInternationalDAO
			.saveCashBookingDetails(cashBookingDOList);
		}
		return bookingStatus;
	}

	/**
	 * Convert toto do.
	 *
	 * @param cashTO the cash to
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	public List<CashBookingDO> convertTotoDO(CashBookingInternationalTO cashTO)
	throws CGBusinessException {
		CashBookingDO cashBookingIntlDO = new CashBookingDO();
		cashBookingIntlDO.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
		List<CashBookingDO> bookingDOList = new ArrayList<CashBookingDO>();
		Iterator itr = null;
		if (cashTO != null) {
			// populating foreign key objects
			if (cashTO.getBookingId() != 0) {
				cashBookingIntlDO.setBookingId(cashTO.getBookingId());
			}
			if (!StringUtil.isEmpty(cashTO.getBookingZone())) {
				cashBookingIntlDO.setBookingDivisionID(cashTO.getBookingZone());
			}
			ChannelDO channelDO = new ChannelDO();
			if (!StringUtil.isEmpty(cashTO.getChannelType())) {
				String channelType = cashTO.getChannelType();
				String[] tokens = channelType
				.split(ApplicationConstants.CHARACTER_HASH);
				if (tokens[0] != null
						&& !(StringUtil.isEmpty(tokens[0].trim()))) {
					channelDO.setChannelTypeId(Integer.parseInt(tokens[0]));
					cashBookingIntlDO.setChannelTypeID(channelDO);
				}
			}
			String[] doxTypeTokens = cashTO.getDoxtypeForCash().split("#");
			DocumentDO documentDo = new DocumentDO();
			if (cashTO.getDoxType() != null && cashTO.getDoxType().length > 0
					&& !StringUtil.isEmpty(cashTO.getDoxtypeForCash())) {
				String doxType = cashTO.getDoxtypeForCash();
				if (doxType.indexOf(ApplicationConstants.CHARACTER_HASH) != -1) {
					doxType = doxType.substring(0, doxType
							.indexOf(ApplicationConstants.CHARACTER_HASH));
				}
				if (doxType != null && !doxType.equals("")) {
					documentDo.setDocumentId(Integer.valueOf(doxType));
					cashBookingIntlDO.setDocumentID(documentDo);

					cashBookingIntlDO.setMainDoxType(doxTypeTokens[1]);
				}
			}
			ProductDO productDo = new ProductDO();
			if (cashTO.getServicesForCash() != null
					&& !cashTO.getServicesForCash().equals("")) {
				String mainProductIdAndCode = cashTO.getServicesForCash();
				String mainProductId = null;
				if (mainProductIdAndCode != null
						&& !StringUtil.isEmpty(mainProductIdAndCode)
						&& !mainProductIdAndCode.equals(BookingConstants.ALL)
						&& !mainProductIdAndCode.equals(BookingConstants.MNP)) {
					if (mainProductIdAndCode.indexOf(BookingConstants.SLASH) != -1) {
						String[] serProdId = mainProductIdAndCode
						.split(BookingConstants.SLASH);
						mainProductId = serProdId[2];
						productDo.setProductId(Integer.valueOf(mainProductId));
						cashBookingIntlDO.setProductID(productDo);
						cashBookingIntlDO.setMainProductType(mainProductId);
					}
				}
			}

			OfficeDO officeDO = new OfficeDO();
			if (StringUtils.isNotEmpty((cashTO.getBookingBranchofficeID()))) {
				officeDO.setOfficeId(Integer.valueOf(cashTO
						.getBookingBranchofficeID()));
				cashBookingIntlDO.setOfficeID(officeDO);
			}
			ServiceDO serviceDO = new ServiceDO();
			if (!(StringUtil.isEmpty(cashTO.getServicesForCash()))) {
				String[] servArray = null;
				if (cashTO.getServicesForCash() != null) {
					servArray = cashTO.getServicesForCash().split("#");
					serviceDO.setServiceId(Integer.valueOf(servArray[0]));
					cashBookingIntlDO.setServiceID(serviceDO);
				}
			}

			ModeDO modeDO = new ModeDO();
			if (!(StringUtil.isEmpty(cashTO.getModeForCash()))) {
				String[] modeArray = null;
				if (cashTO.getModeForCash() != null) {
					modeArray = cashTO.getModeForCash().split("#");
					modeDO.setModeId(Integer.valueOf(modeArray[0]));
					cashBookingIntlDO.setMode(modeDO);
				}
			}

			/*
			 * ProductDO productDO = new ProductDO();
			 * if(!StringUtil.isEmpty(cashTO.getProductTypeForCash())){ String
			 * prodCode = cashTO.getProductTypeForCash(); String[] productId =
			 * prodCode.split(BookingConstants.SLASH);
			 * productDO.setProductId(Integer.parseInt(productId[0]));
			 * cashBookingIntlDO.setProductID(productDO); }
			 */

			cashBookingIntlDO.setBookingDate(cashTO.getBookingDate());
			// store staff id as employee in booking and set is_staff field to
			// value 'Y' is staff is entered
			// on screen
			if (cashTO.getEmployeeId() != null && cashTO.getEmployeeId() != 0) {
				EmployeeDO staff = new EmployeeDO();
				staff.setEmployeeId(cashTO.getEmployeeId());
				cashBookingIntlDO.setEmployeeId(staff);
				cashBookingIntlDO.setIsStaff(BookingConstants.CONSTANT_Y);
			} else {
				cashBookingIntlDO.setIsStaff(BookingConstants.CONSTANT_N);
			}
			/*
			 * EmployeeDO employeeDO = new EmployeeDO(); if
			 * (cashTO.getEmployeeId() != null && cashTO.getEmployeeId() != 0) {
			 * employeeDO.setEmployeeId(cashTO.getEmployeeId());
			 * cashBookingIntlDO.setEmployeeIdForPickupBoyId(employeeDO); }
			 */
			cashBookingIntlDO.setPrioritySticker(cashTO.getPrioritySticker());

			// store customer id for DP Code if customer id is not already
			// populated
			// customer id can be populated for DP Code or Loyal customer but
			// only one of these two wud be entered
			// so customerId can either DP Code or Loyal Customer

			if (cashTO.getDpCode() != null
					&& !StringUtil.isEmpty(cashTO.getDpCode())
					&& cashBookingIntlDO.getCustomerId() == null) {
				CustomerDO dpCustomer = new CustomerDO();
				dpCustomer.setCustomerId(cashTO.getCustomerId());
				cashBookingIntlDO.setCustomerId(dpCustomer);
			}

			if (cashTO.getLoyalCustomerCode() != null
					&& !StringUtil.isEmpty(cashTO.getLoyalCustomerCode())
					&& cashBookingIntlDO.getCustomerId() == null) {
				CustomerDO loyalCustomerDO = new CustomerDO();
				loyalCustomerDO.setCustomerId(cashTO.getLoyalCustomerId());
				cashBookingIntlDO.setCustomerId(loyalCustomerDO);
			}

			cashBookingIntlDO.setConsignmentNumber(cashTO
					.getConsignmentNumber());
			cashBookingIntlDO.setReferenceNumber(cashTO.getReferenceNumber());
			if (cashTO.getSelectScheme() != null
					&& !cashTO.getSelectScheme().toString().equals("")) {
				cashBookingIntlDO.setSelectScheme(cashTO.getSelectScheme()
						.toString());
			}

			/* Set the consignor Info */
			Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
			if (StringUtils.isNotEmpty(cashTO.getConsignorName())) {
				// Setting CNR details
				ConsignerInfoDO consignorDO = new ConsignerInfoDO();
				consignorDO.setFirstName(cashTO.getConsignorName());
				consignorDO.setEmail(cashTO.getConsignorEmail());
				if (!(StringUtil.isEmpty(cashTO.getConsignorPhone()))) {
					consignorDO.setPhone(cashTO.getConsignorPhone());
				}
				consignorDO.setCompanyType(cashTO.getConsignorCompanyIntCash());
				// Setting CNE Address
				ConsignerAddressDO consignorAddressDO = new ConsignerAddressDO();
				// For enqury
				if (cashTO.getConsignerAddressIDField() != null
						&& cashTO.getConsignerAddressIDField() > 0) {
					consignorAddressDO.setConsigrAddrId(cashTO
							.getConsignerAddressIDField());
				}
				consignorAddressDO.setStreet1(cashTO.getConsignorAddress1());
				consignorAddressDO.setStreet2(cashTO.getConsignorAddress2());
				consignorAddressDO.setStreet3(cashTO.getConsignorAddress3());
				if (cashTO.getCnrCountryId() != null
						&& cashTO.getCnrCountryId() > 0) {
					CountryDO cnrCounty = new CountryDO();
					cnrCounty.setCountryId(cashTO.getCnrCountryId());
					consignorAddressDO.setCountry(cnrCounty);
				}
				if (cashTO.getCnrStateId() != null
						&& cashTO.getCnrStateId() > 0) {
					StateDO cnrState = new StateDO();
					cnrState.setStateId(cashTO.getCnrStateId());
					consignorAddressDO.setState(cnrState);
				}
				if (cashTO.getCnrCityIdField() != null
						&& cashTO.getCnrCityIdField() > 0) {
					CityDO cnrCity = new CityDO();
					cnrCity.setCityId(cashTO.getCnrCityIdField());
					consignorAddressDO.setCity(cnrCity);
				}
				if (cashTO.getConsignorPincodeId() != null
						&& cashTO.getConsignorPincodeId() > 0) {
					PincodeDO cnrPinCode = new PincodeDO();
					cnrPinCode.setPincodeId(cashTO.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(cnrPinCode);
				} else if (StringUtils.isNotEmpty(cashTO.getConsignorZipcode())) {
					consignorAddressDO.setCnrOptionalPinCode(cashTO
							.getConsignorZipcode());
				}
				if (cashTO.getAreaIdForConsigner() != null
						&& cashTO.getAreaIdForConsigner() != 0) {
					AreaDO areaDO = new AreaDO();
					areaDO.setAreaId(cashTO.getAreaIdForConsigner());
					consignorAddressDO.setAreaDO(areaDO);
				}
				consignorAddress.add(consignorAddressDO);
				consignorDO.setConsignorAddresses(consignorAddress);
				cashBookingIntlDO.setConsignorID(consignorDO);
			}

			/* Set the consignee Info */
			Set<ConsigneeAddressDO> address = new HashSet<ConsigneeAddressDO>();
			if (StringUtils.isNotEmpty(cashTO.getConsignorName())) {
				// Setting CNE details
				ConsigneeDO consigneeDO = new ConsigneeDO();
				consigneeDO.setFirstName(cashTO.getConsigneeName());
				consigneeDO.setEmail(cashTO.getConsigneeEmail());
				if (!(StringUtil.isEmpty(cashTO.getConsigneePhone()))) {
					consigneeDO.setPhone(cashTO.getConsigneePhone());
				}
				consigneeDO.setCompanyType(cashTO.getConsigneeCompanyIntCash());
				// Setting CNE Address
				ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();
				// For enqury
				if (cashTO.getConsigneeAddressIDField() != null
						&& cashTO.getConsigneeAddressIDField() > 0) {
					consigneeAddressDO.setConsgAddrId(cashTO
							.getConsigneeAddressIDField());
				}
				consigneeAddressDO.setStreet1(cashTO.getConsignorAddress1());
				consigneeAddressDO.setStreet2(cashTO.getConsignorAddress2());
				consigneeAddressDO.setStreet3(cashTO.getConsignorAddress3());
				if (cashTO.getCneCountryId() != null
						&& cashTO.getCneCountryId() > 0) {
					CountryDO cneCounty = new CountryDO();
					cneCounty.setCountryId(cashTO.getCneCountryId());
					consigneeAddressDO.setCountry(cneCounty);
				}
				/*
				 * if (cashTO.getCneStateId() != null && cashTO.getCneStateId()
				 * > 0) { StateDO cneState = new StateDO();
				 * cneState.setStateId(cashTO.getCneStateId());
				 * consigneeAddressDO.setState(cneState); }
				 */
				if (cashTO.getCneCityId() != null && cashTO.getCneCityId() > 0) {
					CityDO cneCity = new CityDO();
					cneCity.setCityId(cashTO.getCneCityId());
					consigneeAddressDO.setCity(cneCity);
				}
				if (cashTO.getConsigneeZipcodeId() != null
						&& cashTO.getConsigneeZipcodeId() > 0) {
					PincodeDO cnePinCode = new PincodeDO();
					cnePinCode.setPincodeId(cashTO.getConsigneeZipcodeId());
					cashBookingIntlDO.setPincodeDO(cnePinCode);

				} else if (StringUtils.isNotEmpty(cashTO.getConsigneeZipcode())) {
					consigneeAddressDO.setCneOptionalPinCode(cashTO
							.getConsigneeZipcode());
				}
				if (cashTO.getAreaIdForConsignee() != null
						&& cashTO.getAreaIdForConsignee() != 0) {
					AreaDO areaDO = new AreaDO();
					areaDO.setAreaId(cashTO.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
				}
				address.add(consigneeAddressDO);
				consigneeDO.setAddresses(address);
				cashBookingIntlDO.setConsigneeID(consigneeDO);
			}

			if (StringUtils.isNotEmpty(cashTO.getShipmentCategory())) {
				String[] shipCate = cashTO.getShipmentCategory().split(
						BookingConstants.SLASH);
				cashBookingIntlDO.setShipmentCategory(shipCate[1]);
			}

			cashBookingIntlDO.setNoOfPieces(cashTO.getNoOfPiecesIntCash());
			cashBookingIntlDO.setCodAmount(cashTO.getCodAmount());
			cashBookingIntlDO.setFinalWeight(cashTO.getChargedWeightForCash());
			AgentDO agentDO = new AgentDO();
			if (cashTO.getAgentDetails() != null) {
				String[] agentTokens = cashTO.getAgentDetails().split(
						BookingConstants.SLASH);

				if (agentTokens != null) {
					if(!agentTokens[0].equals("")) {
						agentDO.setAgentId(Integer.valueOf(agentTokens[0]));
						cashBookingIntlDO.setAgentID(agentDO);
					}
				}

			}
			DocumentDO documentDO = new DocumentDO();
			if (!StringUtil.isEmpty(cashTO.getDoxtypeForCash())) {
				String doxType = cashTO.getDoxtypeForCash();
				String[] documentId = doxType.split(BookingConstants.SLASH);
				documentDO.setDocumentId(Integer.parseInt(documentId[0]));
				cashBookingIntlDO.setDocumentID(documentDO);
			}

			InternationalCommodityDO commodityDo = new InternationalCommodityDO();
			if (cashTO.getCommodityCatForCash() != null) {
				String[] commTokens = cashTO.getCommodityCatForCash().split(
						BookingConstants.SLASH);
				commodityDo.setIntlCommodityId(Integer.valueOf(commTokens[0]));
				cashBookingIntlDO.setCommodityId(commodityDo);
			}
			cashBookingIntlDO.setContents(cashTO.getContent());
			cashBookingIntlDO.setInvoiceValue(cashTO.getValue());
			Integer valueCurrId = 0;
			Integer currId = 0;
			valueCurrId = cashTO.getValCurrencyIdForCash();
			currId = cashTO.getCurrencyIdForCash();
			if (valueCurrId > 0) {
				CurrencyDO valCurr = new CurrencyDO();
				valCurr.setCurrencyId(valueCurrId);
				cashBookingIntlDO.setValueCurrency(valCurr);
			}
			if (currId > 0) {
				CurrencyDO curr = new CurrencyDO();
				curr.setCurrencyId(currId);
				cashBookingIntlDO.setCurrency(curr);
			}
			cashBookingIntlDO.setIsInsured(cashTO.getIsInsuredIntCash());
			cashBookingIntlDO.setInsuredBy(cashTO.getInsuredByIntCash());
			cashBookingIntlDO.setInFavourOf(cashTO.getInfavourOf());
			cashBookingIntlDO.setDutyPaidBy(cashTO.getDutyPaidByIntCash());
			cashBookingIntlDO.setDutyCollectedAmount(cashTO
					.getDutyCollectedAmt());
			cashBookingIntlDO.setRiskSurchgAmt(cashTO
					.getRiskSurchargeAmountIntCash());
			cashBookingIntlDO.setServChrgAmt(cashTO.getServiceChargeAmount());
			cashBookingIntlDO.setSecurityPouchNo(cashTO.getSecurityPouchNo());
			cashBookingIntlDO.setExpectedDlvDate(DateFormatterUtil
					.getDateFromStringDDMMYYY(cashTO.getEddStr()));

			cashBookingIntlDO.setModeOfCollection(cashTO.getPaymentMode());
			// TODO FIELDS NOT AVAILABLE IN BOOKING TABLE
			// cashBookingIntlDO.setcardNo(cashTO.getCardNo());
			// cashBookingIntlDO.setcardHolderName(cashTO.getCardholderName());
			cashBookingIntlDO.setCustomerIndustry(cashTO.getCustomerIndustry());
			cashBookingIntlDO.setRemarks(cashTO.getRemarks());

			/*
			 * cashBookingIntlDO.setCashServiceTax(cashTO.getCashServiceTax());
			 * cashBookingIntlDO
			 * .setCashCustomerType(cashTO.getCashCustomerType());
			 * cashBookingIntlDO.setPaid(cashTO.getCashPaid());
			 * cashBookingIntlDO.setCustomerCode(cashTO.getCashCode());
			 * cashBookingIntlDO.setCashRemarks(cashTO.getCashRemarks());
			 */

			cashBookingIntlDO.setLength(cashTO.getLengthInCms());
			cashBookingIntlDO.setBreadth(cashTO.getBreadthInCms());
			cashBookingIntlDO.setHeight(cashTO.getHeightInCms());
			cashBookingIntlDO.setActualWeight(cashTO.getChargedWeightForCash());
			cashBookingIntlDO.setVolumetricWght(cashTO.getValue());
			cashBookingIntlDO.setFinalWeight(cashTO.getChargedWeightForCash());

			// cashBookingIntlDO.setInvoiceValue(cashTO.getInvoiceValue());
			/*
			 * cashBookingIntlDO.setFrieght(cashTO.getFreight());
			 * cashBookingIntlDO
			 * .setRiskSurchgAmt(cashTO.getRiskSurchargeAmount());
			 * cashBookingIntlDO.setDiscountInPerc(cashTO.getDisPlayDiscount());
			 * cashBookingIntlDO.setServiceTax(cashTO.getDisPlayServiceTax());
			 */
			cashBookingIntlDO.setDiscount(cashTO.getDiscountAmount());
			cashBookingIntlDO.setSplecialCharge(Float.valueOf(cashTO
					.getSpecialCharge().toString()));
			cashBookingIntlDO.setAdditionalCharge(Float.valueOf(cashTO
					.getAdditionalCahrge().toString()));
			cashBookingIntlDO.setFuelSurcharge(cashTO.getFuelSurcharge());
			cashBookingIntlDO.setTaxableAmount(cashTO.getTaxableAmount());
			cashBookingIntlDO.setCommercialTax(cashTO.getCommercialTax());
			cashBookingIntlDO.setServiceTax(cashTO.getServiceTaxAmount());
			cashBookingIntlDO.setCessTax(cashTO.getCessTax());
			cashBookingIntlDO.setEducationalTax(cashTO.getEducationalTax());
			cashBookingIntlDO.setTaxPayable(cashTO.getTaxPayable());
			cashBookingIntlDO.setAmount(cashTO.getTotalAmount());

			// for setting remote delivery popup data
			cashBookingIntlDO.setAreaNameLtDox(cashTO.getAreaNameIntCash());
			cashBookingIntlDO.setDoxAmount(new Double(cashTO
					.getRemoteDeliveryDoxAmount()));
			cashBookingIntlDO.setNonDoxAmount(new Double(cashTO
					.getRemoteDeliveryNonDoxAmount()));
			cashBookingIntlDO.setRemoteDeliveryRemarks(cashTO
					.getRemoteDeliveryRemarksIntCash());
			cashBookingIntlDO.setExtrDlvCharges(cashTO
					.getExtraDeliveryChargesIntCash().toString());
			cashBookingIntlDO.setExtDlvRemarks(cashTO
					.getRemoteDeliveryRemarks1IntCash());

			// cashBookingIntlDO.setWeighingType(cashTO.getCaptureWeightType()[0]);

			// TODO volumetric weight calculation For VolumetricPopup and
			// ParentChildCn Popup
			if (cashTO.getNoOfPiecesIntCash() != null
					&& cashTO.getNoOfPiecesIntCash() == 1) {
				if (cashTO.getVolWeight() != null
						&& cashTO.getVolWeight().length > 0
						&& !StringUtil.isEmpty(cashTO.getVolWeight()[0])) {
					String volWeight = cashTO.getVolWeight()[0];
					if (!StringUtil.isEmpty(volWeight)) {
						String[] tokens = volWeight.split("~");
						// checking if volumetric weight is empty we are not
						// capturing volumetric weight details
						if (tokens.length > 0 && !StringUtil.isEmpty(tokens[4])) {
							/*
							 * bookingDO
							 * .setNoOfPieces(!StringUtil.isEmpty(tokens[0]) ?
							 * Integer .parseInt(tokens[0]) : null);
							 */
							cashBookingIntlDO.setLength(!StringUtil
									.isEmpty(tokens[1]) ? Float
											.parseFloat(tokens[1]) : null);
							cashBookingIntlDO.setBreadth(!StringUtil
									.isEmpty(tokens[2]) ? Float
											.parseFloat(tokens[2]) : null);
							cashBookingIntlDO.setHeight(!StringUtil
									.isEmpty(tokens[3]) ? Float
											.parseFloat(tokens[3]) : null);
							cashBookingIntlDO.setVolumetricWght(!StringUtil
									.isEmpty(tokens[4]) ? Double
											.parseDouble(tokens[4]) : null);
							cashBookingIntlDO.setActualWeight(!StringUtil
									.isEmpty(tokens[5]) ? Double
											.parseDouble(tokens[5]) : null);
							cashBookingIntlDO.setFinalWeight(!StringUtil
									.isEmpty(tokens[6]) ? Double
											.parseDouble(tokens[6]) : null);
						}
					}
				}
			}

			// for setting passport popup data
			cashBookingIntlDO.setPassportDetails(cashTO.getPassportDetails());
			cashBookingIntlDO.setConsgmntStatus(BookingConstants.BOOKED);

			SimpleDateFormat sdfDate = new SimpleDateFormat(
					BookingConstants.TIME_FORMAT_HHMMSS);
			Date now = new Date();
			String strDate = sdfDate.format(now);
			cashBookingIntlDO.setBookingDate(new Date());
			cashBookingIntlDO.setBookingTime(strDate);
			// Code added By Narasimha to insert the VAS products
			Integer vasProdLength = cashTO.getVasIntlProdId().length;
			List<VasProductChargesDO> vasProdChargesDOList = null;
			if (vasProdLength > 0) {
				for (int i = 0; i < vasProdLength; i++) {
					vasProdChargesDOList = getVasChargesDetails(cashTO, i);
				}
				Set<VasProductChargesDO> vasChargesSet = new HashSet<VasProductChargesDO>(
						vasProdChargesDOList);
				cashBookingIntlDO.setVasCharges(vasChargesSet);
			}
			bookingDOList.add(cashBookingIntlDO);
			// for setting Child CN data popup data
			if (cashTO != null
					&& cashTO.getChildCNDetailsHidden() != null
					&& cashTO.getChildCNDetailsHidden() != BookingConstants.EMPTY) {
				getSetOfChildConsignments(cashTO,
						cashTO.getChildCNDetailsHidden(),
						cashTO.getConsignmentNumber());
				itr = parentChildConsignmentDOList.iterator();
				while (itr.hasNext()) {
					CashBookingDO cashBookingDO = (CashBookingDO) itr.next();
					if (!bookingDOList.isEmpty() && bookingDOList.size() != 0) {
						bookingDOList.add(cashBookingDO);
					}

				}

			}
		}
		return bookingDOList;

	}

	/**
	 * Gets the sets the of child consignments.
	 *
	 * @param bookingTO the booking to
	 * @param childCNData the child cn data
	 * @param parentConsignment the parent consignment
	 * @return the sets the of child consignments
	 */
	public List<CashBookingDO> getSetOfChildConsignments(
			CashBookingInternationalTO bookingTO, String childCNData,
			String parentConsignment) {
		List<String> slNoList = new ArrayList<String>();
		List<String> childCNNumberList = new ArrayList<String>();
		List<String> actualWeightList = new ArrayList<String>();
		List<String> lengthList = new ArrayList<String>();
		List<String> widthList = new ArrayList<String>();
		List<String> heightList = new ArrayList<String>();
		List<String> volWeightList = new ArrayList<String>();
		List<String> finalWeightList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();
		List<String> childBookingIdList = new ArrayList<String>();

		String[] tokens = childCNData
		.split(ApplicationConstants.CHARACTER_TILDE);
		StringTokenizer st = new StringTokenizer(tokens[0],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			slNoList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[1],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			childCNNumberList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[2],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			actualWeightList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[3],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			lengthList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[4],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			widthList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[5],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			heightList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[6],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			volWeightList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[7],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			finalWeightList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[8],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			descList.add(st.nextToken());
		}
		st = new StringTokenizer(tokens[9],
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			childBookingIdList.add(st.nextToken());
		}

		for (int i = 0; i < slNoList.size(); i++) {
			CashBookingDO bookingDO = new CashBookingDO();
			// bookingDO.setFranchiseeManifestNo(manifestNo);
			bookingDO.setParentCnNumber(parentConsignment);
			if (!StringUtil.isEmpty(childCNNumberList.get(i))) {
				bookingDO.setConsignmentNumber(childCNNumberList.get(i));
			}
			if (actualWeightList != null && actualWeightList.size() > 0) {
				if (!StringUtil.isEmpty(actualWeightList.get(i))) {
					bookingDO.setActualWeight(Double
							.parseDouble(actualWeightList.get(i)));
				}
			}
			if (lengthList != null && lengthList.size() > 0) {
				if (!StringUtil.isEmpty(lengthList.get(i))) {
					bookingDO.setLength(Float.parseFloat(lengthList.get(i)));
				}
			}
			if (widthList != null && widthList.size() > 0) {
				if (!StringUtil.isEmpty(widthList.get(i))) {
					bookingDO.setBreadth(Float.parseFloat(widthList.get(i)));
				}
			}
			if (heightList != null && heightList.size() > 0) {
				if (!StringUtil.isEmpty(heightList.get(i))) {
					bookingDO.setHeight(Float.parseFloat(heightList.get(i)));
				}
			}
			if (volWeightList != null && volWeightList.size() > 0) {
				if (!StringUtil.isEmpty(volWeightList.get(i))) {
					bookingDO.setVolumetricWght(Double
							.parseDouble(volWeightList.get(i)));
				}
			}
			if (finalWeightList != null && finalWeightList.size() > 0) {
				if (!StringUtil.isEmpty(finalWeightList.get(i))) {
					bookingDO.setFinalWeight(Double.parseDouble(finalWeightList
							.get(i)));
				}
			}
			if (descList != null && descList.size() > 0) {
				bookingDO.setDescription(descList.get(i));
			}
			// if (StringUtils.equals(bookingTO.getUpdateAfterEnquiry(),
			// BookingConstants.UPDATE_AFTER_ENQUIRY)) {
			if (childBookingIdList != null && childBookingIdList.size() > 0
					&& !StringUtil.isEmpty(childBookingIdList.get(i))
					&& !childBookingIdList.get(i).equals("NA")) {
				bookingDO.setBookingId(Integer.valueOf(childBookingIdList
						.get(i)));
			}
			// }
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					BookingConstants.TIME_FORMAT_HHMMSS);
			Date now = new Date();
			String strDate = sdfDate.format(now);
			bookingDO.setBookingDate(new Date());
			bookingDO.setBookingTime(strDate);
			bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
			parentChildConsignmentDOList.add(bookingDO);
		}
		return parentChildConsignmentDOList;

	}

	/**
	 * Gets the vas charges details.
	 *
	 * @param cashBookingIntlTO the cash booking intl to
	 * @param cnt the cnt
	 * @return the vas charges details
	 */
	public List<VasProductChargesDO> getVasChargesDetails(
			CashBookingInternationalTO cashBookingIntlTO, int cnt) {
		VasProductChargesDO vasProductChargesDO = null;
		List<String> vasIdList = new ArrayList<String>();
		List<String> vasChargesList = new ArrayList<String>();
		List<VasProductChargesDO> vasProductChargesDOList = new ArrayList<VasProductChargesDO>();
		String vasProdId = cashBookingIntlTO.getVasIntlProdId()[cnt];
		String vasProdChar = cashBookingIntlTO.getVasProdCharges()[cnt];
		StringTokenizer st = new StringTokenizer(vasProdId,
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			vasIdList.add(st.nextToken());
		}
		st = new StringTokenizer(vasProdChar,
				ApplicationConstants.CHARACTER_COMMA);
		while (st.hasMoreTokens()) {
			vasChargesList.add(st.nextToken());
		}
		if (cashBookingIntlTO.getNoOfVasProducts() != null
				&& cashBookingIntlTO.getNoOfVasProducts().length > 0) {
			Integer count = cashBookingIntlTO.getNoOfVasProducts()[cnt];
			for (int i = 0; i < count; i++) {
				vasProductChargesDO = new VasProductChargesDO();
				if (!StringUtil.isEmpty(vasIdList.get(i))) {
					VasInternationalDO vasInternationalDO = new VasInternationalDO();
					vasInternationalDO.setVasInternationalId(Integer
							.valueOf(vasIdList.get(i)));
					vasProductChargesDO
					.setVasInternationalId(vasInternationalDO);
				}
				if (!StringUtil.isEmpty(vasChargesList.get(i))) {
					vasProductChargesDO.setVasCharge(Double
							.valueOf(vasChargesList.get(i)));
				}
				vasProductChargesDOList.add(vasProductChargesDO);
			}
		}
		return vasProductChargesDOList;
	}

}
