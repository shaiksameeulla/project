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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.mdbDao.booking.DirectPartyBookingIntlMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.expense.ExpenditureTypeDO;
import com.dtdc.domain.expense.MiscExpenseDO;
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
import com.dtdc.to.expense.CnMiscExpenseTO;
import com.dtdc.to.internationalbooking.BookingInternationalTO;
import com.dtdc.to.internationalbooking.directpartybooking.DirectPartyBookingIntlTO;


// TODO: Auto-generated Javadoc
/**
 * The Class DirectPartyBookingIntlMDBServiceImpl.
 */
public class DirectPartyBookingIntlMDBServiceImpl implements DirectPartyBookingIntlMDBService{

	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DirectPartyBookingIntlMDBServiceImpl.class);
	
	/** The dp booking intl dao. */
	private DirectPartyBookingIntlMDBDAO dpBookingIntlDAO=null;

	/**
	 * Sets the dp booking intl dao.
	 *
	 * @param dpBookingIntlDAO the new dp booking intl dao
	 */
	public void setDpBookingIntlDAO(DirectPartyBookingIntlMDBDAO dpBookingIntlDAO){

		this.dpBookingIntlDAO=dpBookingIntlDAO;
	}


	/** The parent child consignment do list. */
	private List<DirectPartyBookingDO> parentChildConsignmentDOList = new ArrayList<DirectPartyBookingDO>();

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.DirectPartyBookingIntlMDBService#saveDPBookingDetails(DirectPartyBookingIntlTO)
	 */
	@Override
	public boolean saveDPBookingDetails(DirectPartyBookingIntlTO directPartyTO)
	throws CGSystemException, CGBusinessException {
		List<DirectPartyBookingDO> bookingList = new ArrayList<DirectPartyBookingDO>();
		Iterator itr = null;
		boolean bookingStatus = false;
		parentChildConsignmentDOList.clear();

		// Convert Franchisee BokkingTO to FranchiseeDO
		bookingList = populateDOListFromTO(directPartyTO);

		/*
		 * Iterating the parentChildConsignmentTOList to get the no of child
		 * consignment and store them as a seprate object in main list
		 */

		itr = parentChildConsignmentDOList.iterator();
		while (itr.hasNext()) {
			DirectPartyBookingDO bookingDo = (DirectPartyBookingDO) itr.next();
			bookingList.add(bookingDo);
		}

		bookingStatus = dpBookingIntlDAO
		.insertDirectPartyBookingDetails(bookingList);

		/*
		 * } catch (CGBusinessException e) {
		 * logger.info("Error while inserting Booking data" ,e.getMessage()); }
		 */

		return bookingStatus;
	}

	/**
	 * Populate do list from to.
	 *
	 * @param bookingTO the booking to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private List<DirectPartyBookingDO> populateDOListFromTO(
			DirectPartyBookingIntlTO bookingTO) throws CGSystemException,
			CGBusinessException {
		List<DirectPartyBookingDO> bookingDOList = new ArrayList<DirectPartyBookingDO>();
		DirectPartyBookingDO bookingDO = null;
		if (bookingTO != null) {
			bookingTO.getDbServer();
		}
		if (bookingTO != null && bookingTO.getNoofRows() != null
				&& bookingTO.getNoofRows() > 0) {
			for (int cnt = 0; cnt < bookingTO.getNoofRows() - 1; cnt++) {
				if (bookingTO.getCnNo() != null
						&& bookingTO.getCnNo().length > 0
						&& !StringUtil.isEmpty(bookingTO.getCnNo()[cnt])) {
					List<MiscExpenseDO> miscExpenseDOList = new ArrayList<MiscExpenseDO>(
							0);
					if (bookingTO.getMiscExpenseTOList() != null
							&& !bookingTO.getMiscExpenseTOList().isEmpty()) {
						List<CnMiscExpenseTO> miscExpenseToList = bookingTO
						.getMiscExpenseTOList();
						if (miscExpenseToList != null
								&& !miscExpenseToList.isEmpty()) {
							for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
								MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
								// miscExpenseDo.setProcessName("BDM");
								miscExpenseDOList.add(miscExpenseDo);
							}
						}
						if (miscExpenseDOList != null
								&& !miscExpenseDOList.isEmpty()) {
							dpBookingIntlDAO
							.saveMiscExp(miscExpenseDOList);
						}
					}

					bookingDO = new DirectPartyBookingDO();
					bookingDO.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
					// populating foreign key objects
					bookingDO.setCodAmount(bookingTO.getCodAmtArray()[cnt]);
					if (!StringUtil.isEmpty(bookingTO.getBookingZone())) {
						bookingDO.setBookingDivisionID(bookingTO
								.getBookingZone());
					}
					ChannelDO channelDO = new ChannelDO();
					if (!StringUtil.isEmpty(bookingTO.getChannelType())) {
						if (bookingTO.getChannelType().equals("SDP")
								|| bookingTO.getChannelType().equals("MDP")) {
							channelDO.setChannelTypeId(1);
						}
						bookingDO.setChannelTypeID(channelDO);
					}
					String[] doxTypeTokens = bookingTO.getDoxType()[cnt]
					                                                .split("#");
					DocumentDO documentDo = new DocumentDO();
					if (bookingTO.getDoxType() != null
							&& bookingTO.getDoxType().length > 0
							&& !StringUtil.isEmpty(bookingTO.getDoxType()[cnt])) {
						String doxType = bookingTO.getDoxType()[cnt];
						if (doxType
								.indexOf(ApplicationConstants.CHARACTER_HASH) != -1) {
							doxType = doxType
							.substring(
									0,
									doxType.indexOf(ApplicationConstants.CHARACTER_HASH));
						}
						if (doxType != null && !doxType.equals("")) {
							documentDo.setDocumentId(Integer.valueOf(doxType));
							bookingDO.setDocumentID(documentDo);
							bookingDO.setMainDoxType(doxTypeTokens[1]);
						}
					}
					ProductDO productDo = new ProductDO();
					if (bookingTO.getProductType() != null
							&& !bookingTO.getProductType().equals("")) {
						String mainProductIdAndCode = bookingTO
						.getProductType();
						String mainProductId = null;
						if (mainProductIdAndCode != null
								&& !StringUtil.isEmpty(mainProductIdAndCode)
								&& !mainProductIdAndCode
								.equals(BookingConstants.ALL)
								&& !mainProductIdAndCode
								.equals(BookingConstants.MNP)) {
							if (mainProductIdAndCode
									.indexOf(BookingConstants.SLASH) != -1) {
								mainProductId = mainProductIdAndCode
								.substring(
										0,
										mainProductIdAndCode
										.indexOf(BookingConstants.SLASH));
								productDo.setProductId(Integer
										.valueOf(mainProductId));
								bookingDO.setProductID(productDo);
								bookingDO.setMainProductType(mainProductId);
							}
						}
					}

					EmployeeDO employeeDO = new EmployeeDO();
					if (!StringUtil.isEmpty(bookingTO.getPickupBoyEmployeeId())) {
						employeeDO.setEmployeeId(Integer.valueOf(bookingTO
								.getPickupBoyEmployeeId()));
						bookingDO.setEmployeeIdForPickupBoyId(employeeDO);
					}
					// Destination pincode
					PincodeDO pincodeDO = new PincodeDO();
					if (bookingTO.getZipcodeID() != null
							&& bookingTO.getZipcodeID().length > 0
							&& bookingTO.getZipcodeID()[cnt] != 0) {
						pincodeDO.setPincodeId(Integer.valueOf(bookingTO
								.getZipcodeID()[cnt]));
						bookingDO.setPincodeDO(pincodeDO);
					}
					// Destination city
					CityDO cityDO = new CityDO();
					if (bookingTO.getCityId() != null
							&& bookingTO.getCityId().length > 0
							&& bookingTO.getCityId()[cnt] != 0) {
						cityDO.setCityId(bookingTO.getCityId()[cnt]);
						bookingDO.setCityDO(cityDO);
					}
					// if
					// (StringUtils.isEmpty(bookingTO.getUpdateAfterEnquiry()))
					// {
					if (bookingTO.getBookingDate() != null) {
						bookingDO.setBookingDate(bookingTO.getBookingDate());
						bookingDO.setServerDate(bookingTO.getBookingDate());
					}
					// }
					bookingDO.setFranchMinfNumber(bookingTO
							.getFranchiseeManifestNo());
					if (bookingTO.getSelectScheme() != null) {
						bookingDO.setSelectScheme(bookingTO.getSelectScheme()
								.toString());
					}
					OfficeDO officeDO = new OfficeDO();
					if (bookingTO.getBookingBranchofficeID() != null
							&& !StringUtil.isEmpty(bookingTO
									.getBookingBranchofficeID())) {
						officeDO.setOfficeId(Integer.valueOf(bookingTO
								.getBookingBranchofficeID()));
						bookingDO.setOfficeID(officeDO);
					}

					// Populating DO for grid fields
					if (bookingTO.getCnNo() != null
							&& bookingTO.getCnNo().length > 0
							&& !StringUtil.isEmpty(bookingTO.getCnNo()[cnt])) {
						bookingDO
						.setConsignmentNumber(bookingTO.getCnNo()[cnt]);
					}
					// Setting TS amount
					if (bookingTO.getConsgTsAmount().length > 0) {
						bookingDO
						.setTsAmount(bookingTO.getConsgTsAmount()[cnt]);
					}
					if (bookingTO.getBookingIdList().length > 0) {
						bookingDO
						.setBookingId(bookingTO.getBookingIdList()[cnt]);
					}

					CustomerDO customerDO = new CustomerDO();
					// for Single DP
					if (bookingTO.getChannelType().equals("SDP")) {
						if (bookingTO.getCustomerId() != null
								&& bookingTO.getCustomerId() != 0) {
							customerDO.setCustomerId(bookingTO.getCustomerId());
							bookingDO.setCustomerId(customerDO);
							if (StringUtils.isNotEmpty(bookingTO
									.getCustomerRefCode())) {
								bookingDO.setCustRefNo(bookingTO
										.getCustomerRefCode());
							}
						}
					}

					// for Multiple DP
					if (bookingTO.getChannelType().equals("MDP")) {
						Integer mdpCustIds = bookingTO.getCpDpCustId().length;
						if (mdpCustIds > 0 && bookingTO.getCpDpCustId() != null) {
							Integer mbpCustId = bookingTO.getCpDpCustId()[cnt];
							if (mbpCustId != null && mbpCustId > 0) {
								customerDO.setCustomerId(mbpCustId);
								bookingDO.setCustomerId(customerDO);
								Integer mdpRefNumbers = bookingTO
								.getBookingRefNoCPDP().length;
								if (mdpRefNumbers > 0
										&& bookingTO.getBookingRefNoCPDP() != null
										&& bookingTO.getBookingRefNoCPDP()[cnt] != null) {
									String refNo = bookingTO
									.getBookingRefNoCPDP()[cnt];
									bookingDO.setCustRefNo(refNo);
								}
							}
						}
					}

					if (bookingTO.getShipmentCategoryArray() != null
							&& bookingTO.getShipmentCategoryArray().length > 0
							&& !StringUtil.isEmpty(bookingTO
									.getShipmentCategoryArray()[cnt])) {
						String[] shipmentTokens = bookingTO
						.getShipmentCategoryArray()[cnt]
						                            .split(BookingConstants.SLASH);
						bookingDO.setShipmentCategory(shipmentTokens[1]);
					}

					AgentDO agentDO = new AgentDO();
					if (bookingTO.getAgent() != null
							&& bookingTO.getAgent().length > 0
							&& bookingTO.getAgent()[cnt] != null) {
						String[] agentTokens = bookingTO.getAgent()[cnt]
						                                            .split(BookingConstants.SLASH);
						agentDO.setAgentId(Integer.parseInt(agentTokens[0]));
						bookingDO.setAgentID(agentDO);
					}
					ServiceDO serviceDO = new ServiceDO();
					if (!StringUtil.isEmpty(bookingTO.getServiceType()[cnt])) {
						String[] serviceTokens = bookingTO.getServiceType()[cnt]
						                                                    .split(BookingConstants.SLASH);
						serviceDO.setServiceId(Integer
								.valueOf(serviceTokens[0]));
						bookingDO.setServiceID(serviceDO);
					}

					ModeDO modeDO = new ModeDO();
					if (bookingTO.getMode() != null
							&& bookingTO.getMode().length > 0
							&& bookingTO.getMode()[cnt] != null) {
						String[] modeTokens = bookingTO.getMode()[cnt]
						                                          .split(BookingConstants.SLASH);
						modeDO.setModeId(Integer.valueOf(modeTokens[0]));
						bookingDO.setMode(modeDO);
					}

					if (bookingTO.getNoOfPieces() != null
							&& bookingTO.getNoOfPieces().length > 0
							&& bookingTO.getNoOfPieces()[cnt] != 0) {
						bookingDO.setNoOfPieces(bookingTO.getNoOfPieces()[cnt]);
					}

					if (bookingTO.getChargedWeight() != null
							&& bookingTO.getChargedWeight().length > 0
							&& bookingTO.getChargedWeight()[cnt] != 0) {
						bookingDO
						.setFinalWeight(bookingTO.getChargedWeight()[cnt]);
					}

					if (bookingTO.getDoxType() != null
							&& bookingTO.getDoxType().length > 0
							&& !StringUtil.isEmpty(bookingTO.getDoxType()[cnt])) {
						bookingDO.setDoxType(doxTypeTokens[1]);
					}

					InternationalCommodityDO commodityDo = new InternationalCommodityDO();
					if (bookingTO.getCommodityCategory() != null
							&& bookingTO.getCommodityCategory().length > 0
							&& !StringUtil.isEmpty(bookingTO
									.getCommodityCategory()[cnt])) {
						String[] commTokens = bookingTO.getCommodityCategory()[cnt]
						                                                       .split(BookingConstants.SLASH);
						commodityDo.setIntlCommodityId(Integer
								.parseInt(commTokens[0]));
						bookingDO.setCommodityId(commodityDo);
					}

					// Save Remote Delivery Pop up data
					bookingDO.setExtDlvRemarks(bookingTO
							.getRemoteDeliveryRemark1()[cnt]);
					bookingDO.setRemoteDeliveryRemarks(bookingTO
							.getRemoteDeliveryRemark()[cnt]);
					bookingDO.setExtrDlvCharges(String.valueOf(bookingTO
							.getExtraDeliveryCharges()[cnt]));
					bookingDO.setAreaNameLtDox(bookingTO.getAreaName()[cnt]);
					bookingDO.setDoxAmount(bookingTO.getDocxAmount()[cnt]);
					bookingDO
					.setNonDoxAmount(bookingTO.getNonDocxAmount()[cnt]);

					bookingDO
					.setWeighingType(bookingTO.getCaptureWeightType()[cnt]);
					// TODO volumetric weight calculation For VolumetricPopup
					// and ParentChildCn Popup
					if (bookingTO.getNoOfPieces() != null
							&& bookingTO.getNoOfPieces().length > 0
							&& bookingTO.getNoOfPieces()[cnt] != null
							&& bookingTO.getNoOfPieces()[cnt] == 1) {
						if (bookingTO.getVolWeight() != null
								&& bookingTO.getVolWeight().length > 0
								&& !StringUtil
								.isEmpty(bookingTO.getVolWeight()[cnt])) {
							String volWeight = bookingTO.getVolWeight()[cnt];
							if (!StringUtil.isEmpty(volWeight)) {
								String[] tokens = volWeight.split("~");
								if (tokens.length > 0
										&& !StringUtil.isEmpty(tokens[4])) {
									bookingDO.setNoOfPieces(!StringUtil
											.isEmpty(tokens[0]) ? Integer
													.parseInt(tokens[0]) : null);
									bookingDO.setLength(!StringUtil
											.isEmpty(tokens[1]) ? Float
													.parseFloat(tokens[1]) : null);
									bookingDO.setBreadth(!StringUtil
											.isEmpty(tokens[2]) ? Float
													.parseFloat(tokens[2]) : null);
									bookingDO.setHeight(!StringUtil
											.isEmpty(tokens[3]) ? Float
													.parseFloat(tokens[3]) : null);
									bookingDO.setVolumetricWght(!StringUtil
											.isEmpty(tokens[4]) ? Double
													.parseDouble(tokens[4]) : null);
									bookingDO.setActualWeight(!StringUtil
											.isEmpty(tokens[5]) ? Double
													.parseDouble(tokens[5]) : null);
									bookingDO.setFinalWeight(!StringUtil
											.isEmpty(tokens[6]) ? Double
													.parseDouble(tokens[6]) : null);
								}
							}
						}
					}

					/* Set the consignee Info */
					Set<ConsigneeAddressDO> address = new HashSet<ConsigneeAddressDO>();
					Integer cneCityId = 0;
					Integer cneCountryId = 0;
					Integer cneAreaId = 0;
					if (bookingTO.getConsigneeCountyStateCityAreaIds() != null
							&& bookingTO.getConsigneeCountyStateCityAreaIds().length > 0) {
						String cneIds = bookingTO
						.getConsigneeCountyStateCityAreaIds()[cnt];
						List<Integer> cneIdsList = null;
						try {
							cneIdsList = StringUtil.parseIntegerList(cneIds,
									"~");
							if (cneIdsList != null && cneIdsList.size() > 0) {
								cneCountryId = cneIdsList.get(0);
								cneIdsList.get(1);
								cneCityId = cneIdsList.get(2);
								cneAreaId = cneIdsList.get(3);
							}
						} catch (Exception e) {
							LOGGER.error("DirectPartyBookingIntlMDBServiceImpl::populateDOListFromTO::Exception occured:"
									+e.getMessage());
						}

					}

					if (cneCityId != null && cneCityId > 0) {
						ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();
						if (bookingTO.getAreaIdForCneForMainCN() != null
								&& bookingTO.getAreaIdForCneForMainCN().length > 0
								&& bookingTO.getAreaIdForCneForMainCN()[cnt] != 0) {
							AreaDO areaDO = new AreaDO();
							areaDO.setAreaId(bookingTO
									.getAreaIdForCneForMainCN()[cnt]);
							consigneeAddressDO.setAreaDO(areaDO);
						}
						consigneeAddressDO.setStreet1(bookingTO
								.getConsigneeDetailsAdd1()[cnt]);
						consigneeAddressDO.setStreet2(bookingTO
								.getConsigneeDetailsAdd2()[cnt]);
						consigneeAddressDO.setStreet3(bookingTO
								.getConsigneeDetailsAdd3()[cnt]);
						// Setting city
						CityDO city = new CityDO();
						city.setCityId(cneCityId);
						consigneeAddressDO.setCity(city);
						// Setting optinal pincode
						if (bookingTO.getCneOptionalPincode() != null
								&& bookingTO.getCneOptionalPincode().length > 0
								&& bookingTO.getCneOptionalPincode()[cnt] != null) {
							consigneeAddressDO.setCneOptionalPinCode(bookingTO
									.getCneOptionalPincode()[cnt]);

						}
						// for enquiry
						int cneIdsLength = bookingTO.getConsigneeAddressIDs().length;
						if (cneIdsLength > 0
								&& bookingTO.getConsigneeAddressIDs()[cnt] != null
								&& bookingTO.getConsigneeAddressIDs()[cnt] != 0) {
							consigneeAddressDO.setConsgAddrId(bookingTO
									.getConsigneeAddressIDs()[cnt]);
						}
						// setting ids
						if (cneCountryId != null && cneCountryId > 0) {
							CountryDO country = new CountryDO();
							country.setCountryId(cneCountryId);
							consigneeAddressDO.setCountry(country);
						}
						/*
						 * if (stateId != null && stateId > 0) { StateDO state =
						 * new StateDO(); state.setStateId(stateId);
						 * consigneeAddressDO.setState(state); }
						 */
						if (cneCityId != null && cneCityId > 0) {
							CityDO cneCity = new CityDO();
							cneCity.setCityId(cneCityId);
							consigneeAddressDO.setCity(cneCity);
						}
						if (cneAreaId != null && cneAreaId > 0) {
							AreaDO cneArea = new AreaDO();
							cneArea.setAreaId(cneAreaId);
							consigneeAddressDO.setAreaDO(cneArea);
						}
						address.add(consigneeAddressDO);
					}
					if (bookingTO.getConsigneeDetailsName() != null
							&& bookingTO.getConsigneeDetailsName().length > 0
							&& bookingTO.getConsigneeDetailsName()[cnt] != null) {
						ConsigneeDO consigneeDO = new ConsigneeDO();
						// For enquiry
						int cneIds = bookingTO.getConsigneeIDs().length;
						if (cneIds > 0
								&& bookingTO.getConsigneeIDs()[cnt] != null
								&& bookingTO.getConsigneeIDs()[cnt] != 0) {
							consigneeDO.setConsigneeId(bookingTO
									.getConsigneeIDs()[cnt]);
						}
						consigneeDO.setFirstName(bookingTO
								.getConsigneeDetailsName()[cnt]);
						consigneeDO.setEmail(bookingTO
								.getConsigneeDetailsEmail()[cnt]);
						boolean flag = false;
						if (bookingTO.getConsigneeDetailsPhone() != null
								&& bookingTO.getConsigneeDetailsPhone().length > 0
								&& bookingTO.getConsigneeDetailsPhone()[cnt] != null) {
							consigneeDO
							.setPhone(bookingTO
									.getConsigneeDetailsPhone()[cnt]
									                            .toString());
							flag = true;
						}
						if (bookingTO.getConsigneeDetailsOptPhone() != null
								&& bookingTO.getConsigneeDetailsOptPhone().length > 0
								&& bookingTO.getConsigneeDetailsOptPhone()[cnt] != null) {
							consigneeDO.setMobile(bookingTO
									.getConsigneeDetailsOptPhone()[cnt]
									                               .toString());
						} else {
							if (flag) {
								consigneeDO.setDirectPhone(bookingTO
										.getConsigneeDetailsPhone()[cnt]
										                            .toString());
							}
						}
						consigneeDO.setAddresses(address);
						// added by narasimha for enqury
						bookingDO.setConsigneeID(consigneeDO);
					}

					/* Set the consignor Info */
					Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
					if (bookingTO.getCnrCityId() != null
							&& bookingTO.getCnrCityId().length > 0
							&& bookingTO.getCnrCityId()[cnt] != 0) {
						ConsignerAddressDO consignorAddressDO = new ConsignerAddressDO();
						if (bookingTO.getZipcodeIDforConsignor() != null
								&& bookingTO.getZipcodeIDforConsignor().length > 0
								&& bookingTO.getZipcodeIDforConsignor()[cnt] != null
								&& bookingTO.getZipcodeIDforConsignor()[cnt] != 0) {
							PincodeDO consignorPincodeDO = new PincodeDO();
							consignorPincodeDO.setPincodeId(bookingTO
									.getZipcodeIDforConsignor()[cnt]);
							consignorAddressDO.setPincodeId(consignorPincodeDO);
						}
						consignorAddressDO.setStreet1(bookingTO
								.getConsignerDetailsAdd1()[cnt]);
						consignorAddressDO.setStreet2(bookingTO
								.getConsignerDetailsAdd2()[cnt]);
						consignorAddressDO.setStreet3(bookingTO
								.getConsignerDetailsAdd3()[cnt]);
						CityDO cnrCity = new CityDO();
						cnrCity.setCityId(bookingTO.getCnrCityId()[cnt]);
						consignorAddressDO.setCity(cnrCity);
						// Setting optinal pincode
						if (bookingTO.getCnrOptionalPincode() != null
								&& bookingTO.getCnrOptionalPincode().length > 0
								&& bookingTO.getCnrOptionalPincode()[cnt] != null) {
							consignorAddressDO.setCnrOptionalPinCode(bookingTO
									.getCnrOptionalPincode()[cnt]);

						}
						// for enquiry
						int cnrIdsLength = bookingTO.getConsignerAddressIDs().length;
						if (cnrIdsLength > 0
								&& bookingTO.getConsignerAddressIDs()[cnt] != null
								&& bookingTO.getConsignerAddressIDs()[cnt] != 0) {
							consignorAddressDO.setConsigrAddrId(bookingTO
									.getConsignerAddressIDs()[cnt]);
						}
						consignorAddress.add(consignorAddressDO);
						if (bookingTO.getConsignerCounttyStateCityAreaIds() != null
								&& bookingTO
								.getConsignerCounttyStateCityAreaIds().length > 0) {
							String cnrIds = bookingTO
							.getConsignerCounttyStateCityAreaIds()[cnt];
							try {
								List<Integer> cnrIdsList = StringUtil
								.parseIntegerList(cnrIds, "~");
								if (cnrIdsList != null && cnrIdsList.size() > 0) {
									Integer countryId = cnrIdsList.get(0);
									Integer stateId = cnrIdsList.get(1);
									Integer cityId = cnrIdsList.get(2);
									Integer areaId = cnrIdsList.get(3);
									if (countryId != null && countryId > 0) {
										CountryDO country = new CountryDO();
										country.setCountryId(countryId);
										consignorAddressDO.setCountry(country);
									}
									if (stateId != null && stateId > 0) {
										StateDO state = new StateDO();
										state.setStateId(stateId);
										consignorAddressDO.setState(state);
									}
									if (cityId != null && cityId > 0) {
										CityDO city = new CityDO();
										city.setCityId(cityId);
										consignorAddressDO.setCity(city);
									}
									if (areaId != null && areaId > 0) {
										AreaDO area = new AreaDO();
										area.setAreaId(areaId);
										consignorAddressDO.setAreaDO(area);
									}
								}

							} catch (Exception e) {
								LOGGER.error("DirectPartyBookingIntlMDBServiceImpl::populateDOListFromTO::Exception occured:"
										+e.getMessage());
							}

							consignorAddress.add(consignorAddressDO);
						}
					}

					if (bookingTO.getConsignerDetailsName() != null
							&& bookingTO.getConsignerDetailsName().length > 0
							&& !StringUtil.isEmpty(bookingTO
									.getConsignerDetailsName()[cnt])) {
						ConsignerInfoDO consignorDO = new ConsignerInfoDO();
						// For enquiry
						int cnrIds = bookingTO.getConsignerIDs().length;
						if (cnrIds > 0
								&& bookingTO.getConsignerIDs()[cnt] != null
								&& bookingTO.getConsignerIDs()[cnt] != 0) {
							consignorDO.setConsignerId(bookingTO
									.getConsignerIDs()[cnt]);
						}
						consignorDO.setFirstName(bookingTO
								.getConsignerDetailsName()[cnt]);
						consignorDO.setEmail(bookingTO
								.getConsignerDetailsEmail()[cnt]);
						if (bookingTO.getConsignerDetailsPhone() != null
								&& bookingTO.getConsignerDetailsPhone().length > 0
								&& bookingTO.getConsignerDetailsPhone()[cnt] != null) {
							consignorDO.setPhone(bookingTO
									.getConsignerDetailsPhone()[cnt]);
						}
						// for enquiry
						/*
						 * int cnrIdCount = bookingTO.getConsignerIDs().length;
						 * if (cnrIdCount > 0) {
						 * consignorDO.setConsignerId(bookingTO
						 * .getConsignerIDs()[cnt]); }
						 */
						consignorDO.setConsignorAddresses(consignorAddress);
						bookingDO.setConsignorID(consignorDO);
					}

					// populate insurance details popup
					bookingDO.setRemarks(bookingTO.getActualContent()[cnt]);
					bookingDO.setInvoiceValue(bookingTO.getInvoiceValue()[cnt]);
					if (bookingTO.getValueCurrencyIds() != null
							&& bookingTO.getValueCurrencyIds().length > 0) {
						CurrencyDO valueCurr = new CurrencyDO();
						valueCurr
						.setCurrencyId(bookingTO.getValueCurrencyIds()[cnt]);
						bookingDO.setValueCurrency(valueCurr);

					}
					bookingDO.setRiskSurchgAmt(bookingTO
							.getRiskSurchargeAmount()[cnt]);
					bookingDO.setServChrgAmt(bookingTO
							.getServiceSurchargeAmount()[cnt]);
					bookingDO.setIsInsured(bookingTO.getIsInsured()[cnt]);
					bookingDO.setInsuredBy(bookingTO.getInsuredBy()[cnt]);
					if (bookingTO.getDutyPaidBy() != null
							&& bookingTO.getDutyPaidBy().length > 0
							&& !StringUtil
							.isEmpty(bookingTO.getDutyPaidBy()[cnt])) {
						bookingDO.setDutyPaidBy(bookingTO.getDutyPaidBy()[cnt]);
					}
					if (bookingTO.getDutyCollectedAmount() != null
							&& bookingTO.getDutyCollectedAmount().length > 0
							&& bookingTO.getDutyCollectedAmount()[cnt] != null) {
						bookingDO.setDutyCollectedAmount(bookingTO
								.getDutyCollectedAmount()[cnt]);
					}
					if (bookingTO.getInsuranceRemarks() != null
							&& bookingTO.getInsuranceRemarks().length > 0
							&& !StringUtil.isEmpty(bookingTO
									.getInsuranceRemarks()[cnt])) {
						bookingDO.setInsuranceRemarks(bookingTO
								.getInsuranceRemarks()[cnt]);
					}
					// For vas produccts selections
					if (!StringUtil.isEmpty(bookingTO.getVasIntlProdId()[cnt])
							&& !StringUtil.isEmpty(bookingTO
									.getVasProdCharges()[cnt])) {
						List<VasProductChargesDO> vasProdChargesDOList = getVasChargesDetails(
								bookingTO, cnt);
						Set<VasProductChargesDO> vasChargesSet = new HashSet<VasProductChargesDO>(
								vasProdChargesDOList);
						bookingDO.setVasCharges(vasChargesSet);
					}
					SimpleDateFormat sdfDate = new SimpleDateFormat(
							BookingConstants.TIME_FORMAT_HHMMSS);
					Date now = new Date();
					String strDate = sdfDate.format(now);
					bookingDO.setBookingTime(strDate);
					bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
					// Adding the Do in the Lkist
					bookingDOList.add(bookingDO);

					// for Nondoxpaperwrk items
					DirectPartyBookingDO paperWrkItemDO = null;
					if (bookingTO.getPaperworkCN().length > 0
							&& StringUtils.isNotEmpty(bookingTO
									.getPaperworkCN()[cnt])) {
						paperWrkItemDO = getPaperWorkBookingDO(bookingTO, cnt);
						bookingDOList.add(paperWrkItemDO);
					}

					if (bookingTO.getChildCNDetails() != null
							&& bookingTO.getChildCNDetails().length > 0
							&& !StringUtil.isEmpty(bookingTO
									.getChildCNDetails()[cnt])) {
						getSetOfChildConsignments(bookingTO,
								bookingTO.getChildCNDetails()[cnt],
								bookingTO.getCnNo()[cnt],
								bookingTO.getFranchiseeMF());
					}

				}
			}
		}
		return bookingDOList;
	}


	/**
	 * Adds a new consignment for paper wrk items.
	 *
	 * @param bookingTO the booking to
	 * @param cnt the cnt
	 * @return the list the of child consignments
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private DirectPartyBookingDO getPaperWorkBookingDO(
			BookingInternationalTO bookingTO, int cnt)
	throws CGSystemException, CGBusinessException {

		DirectPartyBookingDO bookingDO = new DirectPartyBookingDO();
		bookingDO.setConsignmentNumber(bookingTO.getPaperworkCN()[cnt]);
		bookingDO.setParentCnNumber(bookingTO.getCnNo()[cnt]);
		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = dpBookingIntlDAO.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = dpBookingIntlDAO.getIdForDoxType();
			documentDo.setDocumentId(doxTypeId);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setDocumentID(documentDo);
			bookingDO.setAmount(new Double(0));
			bookingDO.setFinalWeight(bookingTO.getnDoxWeight()[cnt]);
			if (!StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems()[cnt])) {
				bookingDO.setNoOfPieces(Integer.parseInt(bookingTO
						.getNoOfPaperWrkItems()[cnt]));
			}
			bookingDO.setBookingDate(bookingTO.getBookingDate());
			List<BookingItemListDO> bookingItemsList = dpBookingIntlDAO
			.getBookingItemList(bookingTO.getPaperworkCN()[cnt]);
			Set<BookingItemListDO> bookingItemsSet = new HashSet<BookingItemListDO>(
					bookingItemsList);
			bookingDO.setNonDoxPaperWrkItems(bookingItemsSet);
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					BookingConstants.TIME_FORMAT_HHMMSS);
			Date now = new Date();
			String strDate = sdfDate.format(now);
			bookingDO.setBookingTime(strDate);
			bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
			// set the consignment type as NonDox PaperWork
			bookingDO.setConsgmntType(BookingConstants.NONDOX_PAPERWORK);

		} catch (Exception e) {
			LOGGER.error("DirectPartyBookingIntlMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;
	}

	/**
	 * Gets the vas charges details.
	 *
	 * @param directPartyTO the direct party to
	 * @param cnt the cnt
	 * @return the vas charges details
	 */
	public List<VasProductChargesDO> getVasChargesDetails(
			DirectPartyBookingIntlTO directPartyTO, int cnt) {
		VasProductChargesDO vasProductChargesDO = null;
		List<String> vasIdList = new ArrayList<String>();
		List<String> vasChargesList = new ArrayList<String>();

		List<VasProductChargesDO> vasProductChargesDOList = new ArrayList<VasProductChargesDO>();
		// TODO VAS PRODUCT CODE COMES BASED ON THE SERVICETYPE
		// set vas product code selected in grid
		String vasProdId = directPartyTO.getVasIntlProdId()[cnt];
		String vasProdChar = directPartyTO.getVasProdCharges()[cnt];
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

		if (directPartyTO.getNoOfVasProducts() != null
				&& directPartyTO.getNoOfVasProducts().length > 0) {
			Integer count = directPartyTO.getNoOfVasProducts()[cnt];
			for (int i = 0; i < count; i++) {
				vasProductChargesDO = new VasProductChargesDO();
				if (!StringUtil.isEmpty(vasIdList.get(i))) {
					VasInternationalDO vasInternationalTO = new VasInternationalDO();
					vasInternationalTO.setVasInternationalId(Integer
							.valueOf(vasIdList.get(i)));
					vasProductChargesDO
					.setVasInternationalId(vasInternationalTO);
				}
				if (!StringUtil.isEmpty(vasChargesList.get(i))) {
					vasProductChargesDO.setVasCharge(Double
							.valueOf(vasChargesList.get(i)));
				}
				vasProductChargesDOList.add(vasProductChargesDO);
			}
		}

		/*
		 * SimpleDateFormat sdfDate = new SimpleDateFormat(
		 * BookingConstants.TIME_FORMAT_HHMMSS); Date now = new Date(); String
		 * strDate = sdfDate.format(now);
		 * directPartyBookingDO.setBookingTime(strDate);
		 * directPartyBookingDO.setConsgmntStatus(BookingConstants.BOOKED);
		 */
		return vasProductChargesDOList;
	}

	/**
	 * Gets the misc expense do from to.
	 *
	 * @param miscExpenseTO the misc expense to
	 * @return the misc expense do from to
	 */
	private MiscExpenseDO getMiscExpenseDoFromTo(CnMiscExpenseTO miscExpenseTO) {
		MiscExpenseDO miscExpenseDO = new MiscExpenseDO();
		miscExpenseDO.setConsignmentNumber("");
		miscExpenseDO.setExpenditureId(miscExpenseTO.getExpndTypeId());
		ExpenditureTypeDO expType = new ExpenditureTypeDO();
		expType.setExpndTypeId(miscExpenseTO.getExpenditureType());
		miscExpenseDO.setExpenditureType(expType);

		miscExpenseDO
		.setExpenditureAmount(miscExpenseTO.getExpenditureAmount());
		EmployeeDO auth = new EmployeeDO();
		auth.setEmployeeId(miscExpenseTO.getEmpId());
		miscExpenseDO.setAuthorizer(auth);
		miscExpenseDO.setRemark(miscExpenseTO.getRemark());
		miscExpenseDO.setExpenditureDate(DateFormatterUtil
				.getDateFromStringDDMMYYY(miscExpenseTO.getExpenditureDate()));
		miscExpenseDO.setVoucherNumber(miscExpenseTO.getVoucherNumber());
		miscExpenseDO.setVoucherDate(DateFormatterUtil
				.getDateFromStringDDMMYYY(miscExpenseTO.getVoucherDate()));
		miscExpenseDO.setConsignmentNumber(miscExpenseTO.getCnNumber());
		miscExpenseDO.setApproved("Y");
		EmployeeDO enteredBy = new EmployeeDO();
		enteredBy.setEmployeeId(Integer.parseInt(miscExpenseTO.getEnteredBy()));
		miscExpenseDO.setEnterebBy(enteredBy);
		return miscExpenseDO;
	}

	/**
	 * Gets the sets the of child consignments.
	 *
	 * @param bookingTO the booking to
	 * @param childCNData the child cn data
	 * @param parentConsignment the parent consignment
	 * @param manifestNo the manifest no
	 * @return the sets the of child consignments
	 */
	public List<DirectPartyBookingDO> getSetOfChildConsignments(
			DirectPartyBookingIntlTO bookingTO, String childCNData,
			String parentConsignment, String manifestNo) {
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
			DirectPartyBookingDO bookingDO = new DirectPartyBookingDO();
			bookingDO.setFranchMinfNumber(manifestNo);
			bookingDO.setParentCnNumber(parentConsignment);
			if (childCNNumberList != null && !childCNNumberList.isEmpty()
					&& !StringUtil.isEmpty(childCNNumberList.get(i))) {
				bookingDO.setConsignmentNumber(childCNNumberList.get(i));
			}

			if (actualWeightList != null && !actualWeightList.isEmpty()
					&& !StringUtil.isEmpty(actualWeightList.get(i))) {
				bookingDO.setActualWeight(Double.parseDouble(actualWeightList
						.get(i)));
			}
			if (!StringUtil.isEmpty(lengthList.get(i))) {
				bookingDO.setLength(Float.parseFloat(lengthList.get(i)));
			}
			if (!StringUtil.isEmpty(widthList.get(i))) {
				bookingDO.setBreadth(Float.parseFloat(widthList.get(i)));
			}
			if (!StringUtil.isEmpty(heightList.get(i))) {
				bookingDO.setHeight(Float.parseFloat(heightList.get(i)));
			}
			if (volWeightList != null && !volWeightList.isEmpty()
					&& !StringUtil.isEmpty(volWeightList.get(i))) {
				bookingDO.setVolumetricWght(Double.parseDouble(volWeightList
						.get(i)));
			}
			if (finalWeightList != null && !finalWeightList.isEmpty()
					&& !StringUtil.isEmpty(finalWeightList.get(i))) {
				bookingDO.setFinalWeight(Double.parseDouble(finalWeightList
						.get(i)));
			}
			if (descList != null && !descList.isEmpty()
					&& !StringUtil.isEmpty(descList.get(i))) {
				bookingDO.setDescription(descList.get(i));
			}
			if (StringUtils.equals(bookingTO.getRequestType(),
					BookingConstants.UPDATE_AFTER_ENQUIRY)) {
				if (childBookingIdList != null && childBookingIdList.size() > 0
						&& !StringUtil.isEmpty(childBookingIdList.get(i))
						&& !childBookingIdList.get(i).equals("NA")) {
					bookingDO.setBookingId(Integer.valueOf(childBookingIdList
							.get(i)));
				}
			}

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

}
