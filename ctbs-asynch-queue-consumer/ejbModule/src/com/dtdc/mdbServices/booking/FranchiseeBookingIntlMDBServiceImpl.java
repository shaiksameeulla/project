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
import src.com.dtdc.mdbDao.booking.FranchiseeBookingIntlMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
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
import com.dtdc.domain.master.franchisee.FranchiseeDO;
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
import com.dtdc.to.internationalbooking.franchiseebooking.FranchiseeBookingIntlTO;

// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeBookingIntlMDBServiceImpl.
 */
public class FranchiseeBookingIntlMDBServiceImpl implements
FranchiseeBookingIntlMDBService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FranchiseeBookingIntlMDBServiceImpl.class);

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO;

	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbs application mdbdao
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the new ctbs application mdbdao
	 */
	public void setCtbsApplicationMDBDAO(
			CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/**
	 * Gets the franchisee booking intl dao.
	 *
	 * @return the franchisee booking intl dao
	 */
	public FranchiseeBookingIntlMDBDAO getFranchiseeBookingIntlDAO() {
		return franchiseeBookingIntlDAO;
	}

	/**
	 * Sets the franchisee booking intl dao.
	 *
	 * @param franchiseeBookingIntlDAO the new franchisee booking intl dao
	 */
	public void setFranchiseeBookingIntlDAO(
			FranchiseeBookingIntlMDBDAO franchiseeBookingIntlDAO) {
		this.franchiseeBookingIntlDAO = franchiseeBookingIntlDAO;
	}

	/** The parent child consignment do list. */
	private List<FranchiseeBookingDO> parentChildConsignmentDOList = new ArrayList<FranchiseeBookingDO>();

	/** The franchisee booking intl dao. */
	private FranchiseeBookingIntlMDBDAO franchiseeBookingIntlDAO = null;

	/**
	 * Sets the franchisee booking intl mdbdao.
	 *
	 * @param franchiseeBookingIntlDAO the new franchisee booking intl mdbdao
	 */
	public void setFranchiseeBookingIntlMDBDAO(
			FranchiseeBookingIntlMDBDAO franchiseeBookingIntlDAO) {

		this.franchiseeBookingIntlDAO = franchiseeBookingIntlDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.FranchiseeBookingIntlMDBService#saveFRBookingDetails(FranchiseeBookingIntlTO)
	 */
	@Override
	public boolean saveFRBookingDetails(
			FranchiseeBookingIntlTO franchiseeBookingIntlTO)
	throws CGSystemException, CGBusinessException {
		List<FranchiseeBookingDO> bookingList = new ArrayList<FranchiseeBookingDO>();
		Iterator itr = null;
		boolean bookingStatus = false;
		parentChildConsignmentDOList.clear();
		bookingList = populateDOListFromTO(franchiseeBookingIntlTO);
		itr = parentChildConsignmentDOList.iterator();
		while (itr.hasNext()) {
			FranchiseeBookingDO bookingDo = (FranchiseeBookingDO) itr.next();
			bookingList.add(bookingDo);
		}
		bookingStatus = franchiseeBookingIntlDAO
		.insertFranchiseeBookingDetails(bookingList);
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
	private List<FranchiseeBookingDO> populateDOListFromTO(
			FranchiseeBookingIntlTO bookingTO) throws CGSystemException,
			CGBusinessException {
		List<FranchiseeBookingDO> bookingDOList = new ArrayList<FranchiseeBookingDO>();
		FranchiseeBookingDO bookingDO = null;
		/*
		 * Get Db Server status whether request have been written to central Db
		 * or not
		 */
		if (bookingTO != null) {
			bookingTO.getDbServer();
		}
		if (bookingTO != null) {
			List<MiscExpenseDO> miscExpenseDOList = new ArrayList<MiscExpenseDO>(
					0);
			if (bookingTO.getMiscExpenseTOList() != null
					&& !bookingTO.getMiscExpenseTOList().isEmpty()) {
				List<CnMiscExpenseTO> miscExpenseToList = bookingTO
				.getMiscExpenseTOList();
				if (miscExpenseToList != null && !miscExpenseToList.isEmpty()) {
					for (CnMiscExpenseTO miscExpenseTo : miscExpenseToList) {
						MiscExpenseDO miscExpenseDo = getMiscExpenseDoFromTo(miscExpenseTo);
						miscExpenseDo.setProcessName("BOOKING");
						miscExpenseDOList.add(miscExpenseDo);
					}
				}
				if (miscExpenseDOList != null && !miscExpenseDOList.isEmpty()) {
					franchiseeBookingIntlDAO
					.saveMiscExp(miscExpenseDOList);
				}
			}
			for (int cnt = 0; cnt < bookingTO.getCnNo().length; cnt++) {
				if (StringUtils.isNotEmpty(bookingTO.getCnNo()[cnt])) {
					bookingDO = new FranchiseeBookingDO();
					bookingDO.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
					if (!StringUtil.isEmpty(bookingTO.getBookingZone())) {
						bookingDO.setBookingDivisionID(bookingTO
								.getBookingZone());
					}
					// Inserting booking office
					OfficeDO bookingOffice = new OfficeDO();
					bookingOffice.setOfficeId(Integer.parseInt(bookingTO
							.getBookingBranchofficeID()));
					bookingDO.setOfficeID(bookingOffice);
					bookingDO.setUserId(bookingTO.getUserId());
					ChannelDO channelDO = new ChannelDO();
					if (!StringUtil.isEmpty(bookingTO.getChannelType())) {
						String channelType = bookingTO.getChannelType();
						String[] tokens = channelType
						.split(ApplicationConstants.CHARACTER_HASH);
						if (tokens[0] != null
								&& !(StringUtil.isEmpty(tokens[0].trim()))) {
							channelDO.setChannelTypeId(Integer
									.parseInt(tokens[0]));
							bookingDO.setChannelTypeID(channelDO);
						}
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
					bookingDO.setFranchiseeManifestNo(bookingTO
							.getFranchiseeManifestNo());

					PincodeDO pincodeDO = new PincodeDO();
					if (bookingTO.getZipcodeID() != null
							&& bookingTO.getZipcodeID().length > 0
							&& bookingTO.getZipcodeID()[cnt] != 0) {
						pincodeDO.setPincodeId(Integer.valueOf(bookingTO
								.getZipcodeID()[cnt]));
						bookingDO.setPincodeDO(pincodeDO);
					}

					CityDO cityDO = new CityDO();
					if (bookingTO.getCityId() != null
							&& bookingTO.getCityId().length > 0
							&& bookingTO.getCityId()[cnt] != 0) {
						cityDO.setCityId(bookingTO.getCityId()[cnt]);
						bookingDO.setCityDO(cityDO);
					}

					/*
					 * ServiceDO serviceDO = new ServiceDO();
					 * if(!StringUtil.isEmpty(bookingTO.getServiceType())){
					 * serviceDO
					 * .setServiceId(Integer.valueOf(bookingTO.getServiceType
					 * ())); }
					 */
					/*
					 * if
					 * (StringUtils.isEmpty(bookingTO.getUpdateAfterEnquiry()))
					 * {
					 */
					if (bookingTO.getBookingDate() != null) {
						bookingDO.setBookingDate(bookingTO.getBookingDate());
						bookingDO.setServerDate(bookingTO.getBookingDate());
					}
					// }

					FranchiseeDO franchiseeDo = new FranchiseeDO();
					if (bookingTO.getFranchiseeID() != null
							&& bookingTO.getFranchiseeID() != 0) {
						franchiseeDo.setFranchiseeId(bookingTO
								.getFranchiseeID());
						bookingDO.setFranchiseeId(franchiseeDo);
					}
					if (!StringUtil.isEmpty(bookingTO.getFranchiseeCode())) {
						bookingDO.setFranchiseeDetails(bookingTO
								.getFranchiseeCode());
					}
					bookingDO.setFranchiseeManifestNo(bookingTO
							.getFranchiseeManifestNo());
					if (bookingTO.getSelectScheme() != null) {
						bookingDO.setSelectScheme(bookingTO.getSelectScheme()
								.toString());
					}

					OfficeDO officeDO = new OfficeDO();
					if (bookingTO.getOfficeID() != null
							&& bookingTO.getOfficeID() != 0) {
						officeDO.setOfficeId(bookingTO.getOfficeID());
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
					// for cpdp
					int cpdpCustomerIds = bookingTO.getCpDpCustId().length;
					if (cpdpCustomerIds > 0
							&& bookingTO.getCpDpCustId()[cnt] > 0) {
						CustomerDO customer = new CustomerDO();
						customer.setCustomerId(bookingTO.getCpDpCustId()[cnt]);
						bookingDO.setCustomerId(customer);
						int codpCustRef = bookingTO.getBookingRefNoCPDP().length;
						if (codpCustRef > 0) {
							bookingDO.setCustRefNo(bookingTO
									.getBookingRefNoCPDP()[cnt]);
						}
					}
					if (bookingTO.getDestZipCode() != null
							&& bookingTO.getDestZipCode().length > 0
							&& !StringUtil
							.isEmpty(bookingTO.getDestZipCode()[cnt])) {
						bookingDO.setPinCode(Integer.valueOf(bookingTO
								.getDestZipCode()[cnt]));
					}

					if (bookingTO.getDestCity() != null
							&& bookingTO.getDestCity().length > 0
							&& !StringUtil
							.isEmpty(bookingTO.getDestCity()[cnt])) {
						bookingDO
						.setDestinationCity(bookingTO.getDestCity()[cnt]);
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
							&& !StringUtil.isEmpty(bookingTO.getAgent()[cnt])) {
						String[] agentTokens = bookingTO.getAgent()[cnt]
						                                            .split(BookingConstants.SLASH);

						agentDO.setAgentId(Integer.parseInt(agentTokens[0]));
						bookingDO.setAgentID(agentDO);
					}

					ServiceDO serviceDO = new ServiceDO();
					if (!StringUtil.isEmpty(bookingTO.getServiceType()[cnt])) {
						String[] serviceTokens = bookingTO.getServiceType()[cnt]
						                                                    .split(BookingConstants.SLASH);
						Integer serviceId = Integer.valueOf(serviceTokens[0]);
						if (serviceId > 0) {
							serviceDO.setServiceId(Integer
									.valueOf(serviceTokens[0]));
							bookingDO.setServiceID(serviceDO);
							ProductDO product = null;
							serviceDO = ctbsApplicationMDBDAO
							.getServiceByIdOrCode(serviceId,"");
							product = serviceDO.getProductDO();
							bookingDO.setProductID(product);
						}
					}

					ModeDO modeDO = new ModeDO();
					if (bookingTO.getMode() != null
							&& bookingTO.getMode().length > 0
							&& !StringUtil.isEmpty(bookingTO.getMode()[cnt])) {
						String[] modeTokens = bookingTO.getMode()[cnt]
						                                          .split(BookingConstants.SLASH);
						modeDO.setModeId(Integer.valueOf(modeTokens[0]));
						bookingDO.setMode(modeDO);
					}
					// set agentDO in bookingDo
					/*
					 * if (agentDO != null) {
					 * 
					 * }
					 */
					/*
					 * if (bookingTO.getServiceType() != null) {
					 * bookingDO.setServiceID(serviceDO); }
					 */

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
								.valueOf(commTokens[0]));
						bookingDO.setCommodityId(commodityDo);
					}
					bookingDO.setAmount(bookingTO.getAmount());

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
								// checking if volumetric weight is empty we are
								// not capturing volumetric weight details
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
							LOGGER.error("FranchiseeBookingIntlMDBServiceImpl::populateDOListFromTO::Exception occured:"
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
						// for enquiry
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
								LOGGER.error("FranchiseeBookingIntlMDBServiceImpl::populateDOListFromTO::Exception occured:"
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

					bookingDO.setCodAmount(bookingTO.getCodAmtArray()[cnt]);

					// populate insurance details popup
					bookingDO.setContents(bookingTO.getActualContent()[cnt]);
					bookingDO.setInvoiceValue(bookingTO.getInvoiceValue()[cnt]);
					bookingDO.setRemarks(bookingTO.getActualContent()[cnt]);
					if (bookingTO.getValueCurrencyIds() != null
							&& bookingTO.getValueCurrencyIds().length > 0) {
						CurrencyDO valueCurr = new CurrencyDO();
						if (bookingTO.getValueCurrencyIds()[cnt] > 0) {
							valueCurr.setCurrencyId(bookingTO
									.getValueCurrencyIds()[cnt]);
							bookingDO.setValueCurrency(valueCurr);
						}
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

					/*
					 * CustomerDO customerDO = new CustomerDO();
					 * customerDO.setCustomerCode(bookingTO.getdi
					 * .getDirectPartyDetails());
					 * bookingDO.setCallNumber(bookingTO.getCallNumber());
					 * bookingDO.setCustomerId(customerDO);
					 */

					SimpleDateFormat sdfDate = new SimpleDateFormat(
							BookingConstants.TIME_FORMAT_HHMMSS);
					Date now = new Date();
					String strDate = sdfDate.format(now);
					bookingDO.setBookingTime(strDate);
					bookingDO.setConsgmntStatus(BookingConstants.BOOKED);

					// Create new consignment for Nondoxpaperwrk items and store
					// the items in BookingItemList against the booking
					FranchiseeBookingDO paperWrkItemDO = null;
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

					// Code added Narasimha to store the VAS products
					Integer vasProdSize = bookingTO.getVasIntlProdId().length;
					if (vasProdSize > 0) {
						List<VasProductChargesDO> vasProdChargesDOList = getVasChargesDetails(
								bookingTO, cnt);
						Set<VasProductChargesDO> vasChargesSet = new HashSet<VasProductChargesDO>(
								vasProdChargesDOList);
						bookingDO.setVasCharges(vasChargesSet);
					}

					bookingDOList.add(bookingDO);
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
	 * @param manifestNo the manifest no
	 * @return the sets the of child consignments
	 */
	public List<FranchiseeBookingDO> getSetOfChildConsignments(
			FranchiseeBookingIntlTO bookingTO, String childCNData,
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
			FranchiseeBookingDO bookingDO = new FranchiseeBookingDO();
			bookingDO.setFranchiseeManifestNo(manifestNo);
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
	 * Adds a new consignment for paper wrk items.
	 *
	 * @param bookingTO the booking to
	 * @param cnt the cnt
	 * @return the list the of child consignments
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private FranchiseeBookingDO getPaperWorkBookingDO(
			BookingInternationalTO bookingTO, int cnt)
	throws CGSystemException, CGBusinessException {
		FranchiseeBookingDO bookingDO = new FranchiseeBookingDO();
		bookingDO.setConsignmentNumber(bookingTO.getPaperworkCN()[cnt]);
		bookingDO.setParentCnNumber(bookingTO.getCnNo()[cnt]);
		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = franchiseeBookingIntlDAO
			.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = franchiseeBookingIntlDAO.getIdForDoxType();
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
			List<BookingItemListDO> bookingItemsList = franchiseeBookingIntlDAO
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
			// for enquiry
			int bkIds = bookingTO.getPaperWrkBookingIds().length;
			if (bkIds > 0) {
				bookingDO.setBookingId(bookingTO.getPaperWrkBookingIds()[cnt]);
			}

		} catch (Exception e) {
			LOGGER.error("FranchiseeBookingIntlMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;
	}

	/**
	 * Gets the vas charges details.
	 *
	 * @param frBookingTO the fr booking to
	 * @param cnt the cnt
	 * @return the vas charges details
	 */
	public List<VasProductChargesDO> getVasChargesDetails(
			FranchiseeBookingIntlTO frBookingTO, int cnt) {
		VasProductChargesDO vasProductChargesDO = null;
		List<String> vasIdList = new ArrayList<String>();
		List<String> bkVasChargesIds = new ArrayList<String>();
		List<String> vasChargesList = new ArrayList<String>();
		List<VasProductChargesDO> vasProductChargesDOList = new ArrayList<VasProductChargesDO>();
		String vasProdId = frBookingTO.getVasIntlProdId()[cnt];
		String vasProdChar = frBookingTO.getVasProdCharges()[cnt];
		String bkVasProdids = "";
		// For enruiqry
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
		// for enquiry
		if (frBookingTO.getBkVasChgIds() != null) {
			int bkIdsCount = frBookingTO.getBkVasChgIds().length;
			if (bkIdsCount > 0) {
				bkVasProdids = frBookingTO.getBkVasChgIds()[cnt];
				StringTokenizer bkVasIds = new StringTokenizer(bkVasProdids,
						ApplicationConstants.CHARACTER_COMMA);
				while (bkVasIds.hasMoreTokens()) {
					bkVasChargesIds.add(bkVasIds.nextToken());
				}
			}
		}
		if (frBookingTO.getNoOfVasProducts() != null
				&& frBookingTO.getNoOfVasProducts().length > 0) {
			Integer count = frBookingTO.getNoOfVasProducts()[cnt];
			for (int i = 0; i < count; i++) {
				vasProductChargesDO = new VasProductChargesDO();
				if (!StringUtil.isEmpty(vasIdList.get(i))) {
					VasInternationalDO vasInternationalTO = new VasInternationalDO();
					// for Enuiry
					if (bkVasChargesIds != null && bkVasChargesIds.size() > 0) {
						String bkVasId = bkVasChargesIds.get(i);
						if (StringUtils.isNotEmpty(bkVasId)) {
							Integer bookVasId = Integer.parseInt(bkVasId);
							vasProductChargesDO
							.setBookingVasChargeId(bookVasId);
						}
					}
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

}
