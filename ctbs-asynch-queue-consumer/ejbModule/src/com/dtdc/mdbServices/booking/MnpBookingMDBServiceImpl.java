/*
 * @author soagarwa
 */
package src.com.dtdc.mdbServices.booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.booking.MnpBookingMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
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
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.to.booking.mnpbooking.MnpBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Class MnpBookingServiceImpl.
 */
@SuppressWarnings("rawtypes")
public class MnpBookingMDBServiceImpl implements MnpBookingMDBService {


	/** logger. */
	private final static Logger logger = LoggerFactory
	.getLogger(MnpBookingMDBServiceImpl.class);

	/** The mnp booking dao. */
	private MnpBookingMDBDAO mnpBookingDAO;

	/** The parent child consignment do list. */
	private List<BookingDO> parentChildConsignmentDOList = new ArrayList<BookingDO>();

	/**
	 * Save mnp bookin details.
	 *
	 * @param bookingTO the booking to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public boolean saveMnpBookinDetails(CGBaseTO bookingTO)
	throws CGBusinessException {
		MnpBookingTO mnpBookingTO = (MnpBookingTO) bookingTO.getBaseList().get(
				0);

		return saveMnpBookinDetails(mnpBookingTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.bs.booking.mnpbooking.MnpBookingService#book(com.dtdc.to.
	 * booking.mnpbooking.MnpBookingTO)
	 */
	@Override
	public boolean saveMnpBookinDetails(MnpBookingTO to)
	throws CGBusinessException {
		boolean bookingStatus = false;
		BookingDO dTDCBookingDO = null;
		parentChildConsignmentDOList.clear();
		List<BookingDO> dTDCBookingDOList = new ArrayList<BookingDO>();
		String channelType = to.getChannelType();
		String[] tokens = channelType
		.split(ApplicationConstants.CHARACTER_HASH);
		if (tokens != null && tokens.length > 1) {
			if (tokens[1].equals(BookingConstants.FR_BOOKING_TYPE)) {
				dTDCBookingDO = new FranchiseeBookingDO();
				dTDCBookingDOList = convertTotoDO(to, dTDCBookingDO);
			}
			if (tokens[1].equals(BookingConstants.CASH_BOOKING_CC_TYPE) || tokens[1].equals(BookingConstants.CASH_BOOKING_WK_TYPE)
					|| tokens[1].equals(BookingConstants.CASH_BOOKING_PB_TYPE) ) {

				dTDCBookingDO = new CashBookingDO();
				dTDCBookingDOList = convertTotoDO(to, dTDCBookingDO);
			}
			if (tokens[1].equals(BookingConstants.DP_BOOKING_TYPE)) {
				dTDCBookingDO = new DirectPartyBookingDO();
				dTDCBookingDOList = convertTotoDO(to, dTDCBookingDO);
			}
		}
		bookingStatus = mnpBookingDAO.saveAndDeleteMnpBookinDetails(dTDCBookingDOList);
		return bookingStatus;
	}

	/**
	 * Convert toto do.
	 * 
	 * @param to
	 *            the to
	 * @param dTDCBookingDO
	 *            the d tdc booking do
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<BookingDO> convertTotoDO(MnpBookingTO to,
			BookingDO dTDCBookingDO) {

		List<BookingDO> bookingDoList = new ArrayList<BookingDO>();
		Iterator itr = null;
		List mnpBookingDOList =null;

		if(to.getConsignmentNumber() !=null && ! to.getConsignmentNumber().trim().equalsIgnoreCase("")){
			try {
				mnpBookingDOList=mnpBookingDAO.getMnpBookingEnquiryDetails(to.getConsignmentNumber());
			} catch (Exception e) {
				logger.error("MnpBookingMDBServiceImpl::convertTotoDO::Exception occured:"
						+e.getMessage());
			}
		}


		if (dTDCBookingDO instanceof FranchiseeBookingDO) {
			FranchiseeBookingDO franchiseeBookingDO = null;

			if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
				franchiseeBookingDO=(FranchiseeBookingDO)mnpBookingDOList.get(0);
			}else{
				franchiseeBookingDO = (FranchiseeBookingDO) dTDCBookingDO;
			}


			try {
				// populating foreign key objects

				// populating Header information
				if (!StringUtil.isEmpty(to.getChannelType())) {
					ChannelDO channelDO = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						channelDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getChannelTypeID();
					}else{
						channelDO = new ChannelDO();
					}

					String channelType = to.getChannelType();
					String[] tokens = channelType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (tokens[0] != null
							&& tokens[0].trim() != ApplicationConstants.EMPTY_STRING) {
						channelDO.setChannelTypeId(Integer.parseInt(tokens[0]));
						franchiseeBookingDO.setChannelTypeID(channelDO);
					}

				}

				/*if (to.getEmployeeId() != null && to.getEmployeeId() != 0) {
					EmployeeDO employeeDO = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						employeeDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getEmployeeId();
					}else{
						employeeDO = new EmployeeDO();
					}


					employeeDO.setEmployeeId(to.getEmployeeId());
					franchiseeBookingDO.setEmployeeId(employeeDO);

				}*/

				if (to.getPickupBoyEmployeeId()!= null && to.getPickupBoyEmployeeId() != 0) {

					EmployeeDO employeeDO = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						employeeDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getEmployeeId();
					}else{
						employeeDO = new EmployeeDO();
					}

					employeeDO.setEmployeeId(to.getPickupBoyEmployeeId());
					franchiseeBookingDO.setEmployeeId(employeeDO);
					//franchiseeBookingDO.setEmployeeId(employeeDO);

				}

				/*if(to.getBookingId()!=null && to.getBookingId()!=0){
					//franchiseeBookingDO.setBookingId(to.getBookingId());

				}else */if(to.getBookingId() != 0 || to.getBookingId() != null){
					franchiseeBookingDO.setServerDate(to.getBookingDate());
				}

				franchiseeBookingDO.setFinalWeight(to.getFinalWeight());
				franchiseeBookingDO.setServiceTax(to.getServiceTaxAmount());
				franchiseeBookingDO.setServChrgAmt(to.getServiceCharge());
				franchiseeBookingDO.setActualWeight(to.getActualWeight());
				//franchiseeBookingDO.setFinalWeight(to.getChargedWeight());
				franchiseeBookingDO.setVolumetricWght(to.getVolumetricWeight());

				franchiseeBookingDO.setSelectScheme(to.getSelectScheme());
				franchiseeBookingDO.setNoOfPieces(to.getNoOfPieces());
				franchiseeBookingDO.setCreateChildCn(to.getCreateChildCn());


				//need to check

				if (to.getCustomerCode() != null && !to.getCustomerCode().trim().equals("")) {
					CustomerDO customerDO = mnpBookingDAO.getCustomerDetails(to.getCustomerCode());
					if(customerDO != null) {
						franchiseeBookingDO.setCustomerId(customerDO);
					}
				}
				to.getProductTypeForMNP();

				String vasProdCode = to.getProductIdAndCode();
				if (vasProdCode != null && !StringUtil.isEmpty(vasProdCode)) {
					vasProdCode = vasProdCode.substring(vasProdCode
							.indexOf(BookingConstants.SLASH) + 1);
					franchiseeBookingDO.setVasProductCode(vasProdCode);
				}

				franchiseeBookingDO.setConsignmentNumber(to
						.getConsignmentNumber());
				franchiseeBookingDO.setExpectedDlvDate(to.getEdd());

				if (to.getPincodeId() != null && to.getPincodeId() != 0) {
					PincodeDO pincodeDO = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						pincodeDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getPincodeDO();
					}else{
						pincodeDO = new PincodeDO();
					}

					pincodeDO.setPincodeId(to.getPincodeId());
					franchiseeBookingDO.setPincodeDO(pincodeDO);
				}

				if (to.getCityId() != null && to.getCityId() != 0) {
					CityDO cityDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						cityDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getCityDO();
					}else{
						cityDO = new CityDO();
					}
					cityDO.setCityId(to.getCityId());
					franchiseeBookingDO.setCityDO(cityDO);
				}

				if (!StringUtil.isEmpty(to.getDoxTypeIdAndCode())) {
					DocumentDO documentDo = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						documentDo=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getDocumentID();
					}else{
						documentDo = new DocumentDO();
					}

					String doxType = to.getDoxTypeIdAndCode();
					String[] documentId = doxType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (documentId[0] != null
							&& documentId[0].trim() != ApplicationConstants.EMPTY_STRING) {
						documentDo.setDocumentId(Integer
								.parseInt(documentId[0]));
						franchiseeBookingDO.setDocumentID(documentDo);
					}
				}

				if (!StringUtil.isEmpty(to.getServiceType())) {
					ServiceDO serviceDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						serviceDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getServiceID();
					}else{
						serviceDO = new ServiceDO();
					}
					String serviceType = to.getServiceType();
					String[] serviceId = serviceType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (!StringUtil.isEmpty(serviceId[0])) {
						serviceDO.setServiceId(Integer.parseInt(serviceId[0]));
						//serviceDO.setServiceType(serviceType);
						franchiseeBookingDO.setServiceID(serviceDO);
					}
				}

				if (!StringUtil.isEmpty(to.getBookingZone())) {
					// OfficeDO officeDO = new OfficeDO();
					// officeDO.setOfficeId(to.getBookingZone());
					franchiseeBookingDO.setBookingDivisionID(to
							.getBookingZone());
				}

				if (to.getFranchiseeID() != null && to.getFranchiseeID() != 0) {
					FranchiseeDO franchiseeDo = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						franchiseeDo=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getFranchiseeId();
					}else{
						franchiseeDo = new FranchiseeDO();
					}
					franchiseeDo.setFranchiseeId(to.getFranchiseeID());
					franchiseeBookingDO.setFranchiseeId(franchiseeDo);
				}
				// franchiseeBookingDO.setEmployeeIdForPickupBoyId(employeeDO);
				franchiseeBookingDO.setBookingDate(to.getBookingDate());
				SimpleDateFormat sdfDate = new SimpleDateFormat(
						BookingConstants.TIME_FORMAT_HHMMSS);
				Date now = new Date();
				String strDate = sdfDate.format(now);

				franchiseeBookingDO.setBookingTime(strDate);
				franchiseeBookingDO.setSecurityPouchNo(to.getSecurityPouchNo());
				franchiseeBookingDO.setPaymentMode(to.getPaymentMode());
				franchiseeBookingDO.setPaid(to.getCashPaid()); 
				/*
				 * franchiseeBookingDO.setCashServiceTax(to.getCashServiceTax());
				 * franchiseeBookingDO
				 * .setCashCustomerType(to.getCashCustomerType());
				 */

				/* for cash

				 * franchiseeBookingDO.setCustomerCode(to.getCashCode());
				 * franchiseeBookingDO.setCashRemarks(to.getCashRemarks());
				 */
				if (to.getNoOfPieces() != 0 && to.getNoOfPieces() > 1) {
					if (to.getLength() != null && to.getLength().length != 0) {
						Float length = to.getLength()[0];
						Float breath = to.getBreadth()[0];
						Float height = to.getHeight()[0];
						Double actualWeigtht = to.getActualWeightGrid()[0];
						Double volweight = to.getVolumetricWeightGrid()[0];
						Double finalWeight = to.getFinalWeightGrid()[0];
						Integer noOfPiece = to.getNoOfPieces();
						franchiseeBookingDO.setLength(length * noOfPiece);
						franchiseeBookingDO.setBreadth(breath * noOfPiece);
						franchiseeBookingDO.setHeight(height * noOfPiece);
						franchiseeBookingDO.setActualWeight(actualWeigtht
								* noOfPiece);
						franchiseeBookingDO.setVolumetricWght(volweight
								* noOfPiece);
						franchiseeBookingDO.setFinalWeight(finalWeight
								* noOfPiece);
					}
				} else {
					franchiseeBookingDO.setLength(to.getLengthInCms());
					franchiseeBookingDO.setBreadth(to.getBreadthInCms());
					franchiseeBookingDO.setHeight(to.getHeightInCms());
					franchiseeBookingDO.setActualWeight(to.getActualWeight());
					//franchiseeBookingDO.setVolumetricWght(to.getVolumtericWeight());
					franchiseeBookingDO.setFinalWeight(to.getFinalWeight());
				}

				franchiseeBookingDO.setIsInsured(to.getIsInsured());
				franchiseeBookingDO.setInsuredBy(to.getInsuredBy());
				franchiseeBookingDO.setInvoiceValue(to.getInvoiceValue());
				franchiseeBookingDO.setDbServer(to.getDbServer());
				franchiseeBookingDO.setPrioritySticker(to.getPrioritySticker());
				// for setting values in ConsigneeAddressDO

				Set address = new HashSet();
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;

				if (to.getAreaIdForConsignee() != null
						&& to.getAreaIdForConsignee() != 0) {
					//ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						address = new HashSet();
						Iterator iterator=((FranchiseeBookingDO)(mnpBookingDOList.get(0))).getConsigneeID().getAddresses().iterator();
						consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
						areaDO=consigneeAddressDO.getAreaDO();
						areaDO.setAreaId(to.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
						address.add(consigneeAddressDO);


					}else{
						consigneeAddressDO=new ConsigneeAddressDO();
						areaDO= new AreaDO();
						address = new HashSet();
						areaDO.setAreaId(to.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
						/*if(to.getConsigneeAddressId() != null && to.getConsigneeAddressId() != 0){
								consigneeAddressDO.setConsgAddrId(to.getConsigneeAddressId());
							}*/
						address.add(consigneeAddressDO);
					}

					/*areaDO = new AreaDO();
					areaDO.setAreaId(to.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
					consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
					consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
					consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
					if(to.getConsigneeAddressId() != null && to.getConsigneeAddressId() != 0){
						consigneeAddressDO.setConsgAddrId(to.getConsigneeAddressId());
					}else{
						consigneeAddressDO.setConsgAddrId(null);
					}
					address.add(consigneeAddressDO);*/
				}

				/* Set the consignor Info */

				Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
				// create set of consignee address and set that to the consigneeDO
				// adresses.
				ConsignerAddressDO consignorAddressDO = null;
				PincodeDO consignorPincodeDO = null;
				CityDO consignorCityDO =null;
				if (to.getConsignorPincodeId() != null
						&& to.getConsignorPincodeId() > 0) {

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						Iterator iterator=((FranchiseeBookingDO)(mnpBookingDOList.get(0))).getConsignorID().getConsignorAddresses().iterator();
						consignorAddressDO=(ConsignerAddressDO)iterator.next();

						consignorPincodeDO = consignorAddressDO.getPincodeId();
						consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorCityDO = consignorAddressDO.getCity();
						consignorCityDO.setCityId(to.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);

						consignorAddressDO.setStreet1(to.getConsignorAddress1());
						consignorAddressDO.setStreet2(to.getConsignorAddress2());
						consignorAddressDO.setStreet3(to.getConsignorAddress3());

					}else{
						consignorAddressDO= new ConsignerAddressDO();
						consignorPincodeDO = new PincodeDO();
						consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorAddressDO.setStreet1(to.getConsignorAddress1());
						consignorAddressDO.setStreet2(to.getConsignorAddress2());
						consignorAddressDO.setStreet3(to.getConsignorAddress3());

						if (to.getConsignerCityId() != null
								&& to.getConsignerCityId() > 0) {
							consignorCityDO = new CityDO();
							consignorCityDO.setCityId(to.getConsignerCityId());
							consignorAddressDO.setCity(consignorCityDO);
						}

					}

					consignorAddress.add(consignorAddressDO);
				}

				ConsignerInfoDO consignorDO = null;

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					consignorDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getConsignorID();
				}else{
					consignorDO= new ConsignerInfoDO();
				}

				/*if(to.getConsignerInfoId() != null && to.getConsignerInfoId() != 0){
					consignorDO.setConsignerId(to.getConsignerInfoId());
				}else{
					consignorDO.setConsignerId(null);
				}*/

				consignorDO.setFirstName(to.getConsignorName());
				consignorDO.setEmail(to.getConsignorEmail());
				if (to.getConsignorPhone() != null) {
					consignorDO.setPhone(to.getConsignorPhone().toString());
				}
				consignorDO.setConsignorAddresses(consignorAddress);
				franchiseeBookingDO.setConsignorID(consignorDO);

				/*
				 * GeographyDO geographyConsigneeDO = new GeographyDO();
				 * geographyConsigneeDO.setGeographyId(to
				 * .getGeographyIdForConsignee());
				 * consigneeAddressDO.setGeography(geographyConsigneeDO);
				 */

				// for setting values in ConsigneeDO
				ConsigneeDO consigneeDO = null;

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					consigneeDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getConsigneeID();
				}else{
					consigneeDO= new ConsigneeDO();
				}

				/*if(to.getConsigneeInfoId() != null && to.getConsigneeInfoId() != 0){
					consigneeDO.setConsigneeId(to.getConsigneeInfoId());
				}else{
					consigneeDO.setConsigneeId(null);
				}*/
				consigneeDO.setFirstName(to.getConsigneeName());
				consigneeDO.setEmail(to.getConsigneeEmail());
				if (to.getConsigneePhone() != null) {
					consigneeDO.setPhone(to.getConsigneePhone().toString());
				}

				consigneeDO.setAddresses(address);
				franchiseeBookingDO.setConsigneeID(consigneeDO);

				// franchiseeBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
				// franchiseeBookingDO.setServChrgAmt(to.getServiceChargeAmount());
				franchiseeBookingDO.setAmount(to.getGrandTotal());
				// franchiseeBookingDO.setRemarks(to.getCashBookingRemarks());
				// for setting remote delivery popup data
				franchiseeBookingDO.setAreaNameLtDox(to.getAreaName());
				if (to.getRemoteDeliveryDoxAmount() != null) {
					franchiseeBookingDO.setDoxAmount(new Double(to
							.getRemoteDeliveryDoxAmount()));
				}
				if (to.getRemoteDeliveryNonDoxAmount() != null) {
					franchiseeBookingDO.setNonDoxAmount(new Double(to
							.getRemoteDeliveryNonDoxAmount()));
				}
				franchiseeBookingDO.setRemoteDeliveryRemarks(to
						.getRemoteDeliveryRemarks());
				franchiseeBookingDO.setExtrDlvCharges(to
						.getExtraDeliveryCharges());
				franchiseeBookingDO.setExtDlvRemarks(to.getExtDlvRemarks());
				// for setting passport popup data
				franchiseeBookingDO.setPassportDetails(to.getPassportDetails());

				/* Fopr Mnp items 
				Set mnpItem = new HashSet();

				 For storing sub item Info 
				if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

					int itemSize = to.getSubItemId().length;
					for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
						if (to.getSubItemId()[itemCnt] != null
								&& to.getSubItemId()[itemCnt] != 0) {
							BookingItemListDO bookingItemDo = null;
							CommodityDO commodityDO = null;


							if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
							 Iterator iterator=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
								bookingItemDo=(BookingItemListDO)iterator.next();
								commodityDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getDomesticCommodityId();
							}else{
								bookingItemDo=new BookingItemListDO();
								commodityDO= new CommodityDO();
							}
							if(commodityDO.getParentCommCode() !=null){
							commodityDO
									.setCommodityId(to.getSubItemId()[itemCnt]);
							bookingItemDo.setCommodity(commodityDO);
							bookingItemDo
									.setComments(to.getSubItemComments()[itemCnt]);
							bookingItemDo.setNumberOfPieces(to
									.getSubItemNoOfPeice()[itemCnt]);
							bookingItemDo
									.setSerialNumber(to.getSubItemSlNo()[itemCnt]);
							bookingItemDo.setCommodity(commodityDO);
							franchiseeBookingDO.setDomesticCommodityId(commodityDO);

							if(to.getBookingItemlistIds()!=null && to.getBookingItemlistIds().length>0 && to.getBookingItemlistIds()[0] != 0){

								if(itemCnt<=to.getBookingItemlistIds().length && to.getBookingItemlistIds()[itemCnt] != 0){
									bookingItemDo.setBookingItemlistId(to.getBookingItemlistIds()[itemCnt]);
								}
							}else if(to.getBookingItemlistIds()==null && to.getBookingItemlistIds()[0] == 0){
								bookingItemDo.setBookingItemlistId(null);
							}

							mnpItem.add(bookingItemDo);
						}}
					}

				}
				 For storing main item Info 
				if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
					String[] mnpCommodityId = to.getMnpCodeAndId().split(
							ApplicationConstants.CHARACTER_HASH);
					if (mnpCommodityId != null
							&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
						BookingItemListDO bookingMainItemDo = null;
						CommodityDO commodityMainItemDO = null;

						if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
							 Iterator iterator=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
							 bookingMainItemDo=(BookingItemListDO)iterator.next();
							 commodityMainItemDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getDomesticCommodityId();
							}else{
								bookingMainItemDo=new BookingItemListDO();
								commodityMainItemDO= new CommodityDO();
							}
						if(commodityMainItemDO.getParentCommCode() == null){

						commodityMainItemDO.setCommodityId(Integer
								.valueOf(mnpCommodityId[0]));
						bookingMainItemDo.setCommodity(commodityMainItemDO);
						mnpItem.add(bookingMainItemDo);
					}}
				}*/

				/* Fopr Mnp items */
				Set mnpItem = new HashSet();

				/* For storing sub item Info */
				if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

					int itemSize = to.getSubItemId().length;

					for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
						if (to.getSubItemId()[itemCnt] != null
								&& to.getSubItemId()[itemCnt] != 0) {
							BookingItemListDO bookingItemDo = new BookingItemListDO();
							CommodityDO commodityDO = new CommodityDO();
							commodityDO.setCommodityId(to.getSubItemId()[itemCnt]);
							bookingItemDo.setCommodity(commodityDO);
							bookingItemDo
							.setComments(to.getSubItemComments()[itemCnt]);
							bookingItemDo.setNumberOfPieces(to
									.getSubItemNoOfPeice()[itemCnt]);
							bookingItemDo
							.setSerialNumber(to.getSubItemSlNo()[itemCnt]);
							mnpItem.add(bookingItemDo);
						}
					}

				}
				/* For storing main item Info */
				if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
					String[] mnpCommodityId = to.getMnpCodeAndId().split(
							ApplicationConstants.CHARACTER_HASH);
					if (mnpCommodityId != null
							&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
						BookingItemListDO bookingMainItemDo = new BookingItemListDO();
						CommodityDO commodityMainItemDO = new CommodityDO();
						commodityMainItemDO.setCommodityId(Integer
								.valueOf(mnpCommodityId[0]));
						bookingMainItemDo.setCommodity(commodityMainItemDO);
						mnpItem.add(bookingMainItemDo);
					}
				}
				franchiseeBookingDO.setMnpItems(mnpItem);

				franchiseeBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

				// save booking branch off id
				if (to.getBookingBranchofficeID() != null
						&& to.getBookingBranchofficeID() != 0) {
					OfficeDO bookingBranchOffice = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						bookingBranchOffice=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getOfficeID();
					}else{
						bookingBranchOffice=new OfficeDO();
					}
					bookingBranchOffice.setOfficeId(to
							.getBookingBranchofficeID());
					franchiseeBookingDO.setBookingBranchDetails(to.getBookingBranchDetails());
					franchiseeBookingDO.setFranchiseeBranchDetails(to.getFrBranchDetails());
					franchiseeBookingDO.setOfficeID(bookingBranchOffice);
				}

				/* Fopr Mnp items */
				// save weight capture type
				franchiseeBookingDO.setWeighingType(to.getCaptureWeightType());
				franchiseeBookingDO.setActualWeight(to.getActualWeight());

				// Create new consignment for Nondoxpaperwrk items and store the
				// items in BookingItemList against the booking id


				FranchiseeBookingDO paperWrkItemDO = null;
				if (to != null
						&& !StringUtil.isEmpty(to.getNoOfPaperWrkItems())) {
					paperWrkItemDO = (FranchiseeBookingDO) getPaperWorkBookingDO(
							to, BookingConstants.FRANCHISEE_BOOKING_TYPE);
					paperWrkItemDO.setDiFlag(BookingConstants.DIFLAG);
					paperWrkItemDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
					bookingDoList.add(paperWrkItemDO);
				}



				franchiseeBookingDO.setUserId(to.getUserId());
				franchiseeBookingDO.setFrieght(to.getFreight());
				franchiseeBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
				franchiseeBookingDO.setServChrgAmt(to.getServiceChargeAmount());
				franchiseeBookingDO.setTaxableAmount(to.getSubTotalFreight());
				franchiseeBookingDO.setTaxPayable(to.getServiceTaxAmount());
				franchiseeBookingDO.setDiscount(to.getDiscountAmount());
				franchiseeBookingDO.setDiscountInPerc(to.getDisPlayDiscount());
				franchiseeBookingDO.setAmount(to.getGrandTotal());
				franchiseeBookingDO.setRemarks(to.getRemarks());
				franchiseeBookingDO.setCustomerIndustry(to.getCustomerIndustry());
				franchiseeBookingDO.setServiceTax(to.getServiceTaxAmount());

				franchiseeBookingDO.setTsAmount(to.getFreight());
				franchiseeBookingDO.setDiplomaticOrRemoteAreaCharge(to.getDiplomaticOrRemoteAreaCharge());
				franchiseeBookingDO.setMiscCharge(to.getTotalMiscExpense());

				franchiseeBookingDO.setDiFlag(BookingConstants.DIFLAG);
				franchiseeBookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

				bookingDoList.add(franchiseeBookingDO);


				// for setting Child CN data popup data
				if (to != null
						&& to.getChildCNDetailsHidden() != null
						&& !StringUtil.isEmpty(to.getChildCNDetailsHidden())) {
					getSetOfChildConsignments(to.getChildCNDetailsHidden(),
							to.getConsignmentNumber());
					itr = parentChildConsignmentDOList.iterator();
					while (itr.hasNext()) {
						BookingDO bookingDO1 = (BookingDO) itr.next();
						if (!bookingDoList.isEmpty() && bookingDoList.size() != 0) {
							bookingDoList.add(bookingDO1);
						}

					}

				}

			} catch (Exception e) {
				logger.error("MnpBookingMDBServiceImpl::convertTotoDO::Exception occured:"
						+e.getMessage());
			}

		}
		if (dTDCBookingDO instanceof CashBookingDO) {
			CashBookingDO cashBookingDO = null;

			if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
				cashBookingDO=(CashBookingDO)mnpBookingDOList.get(0);
			}else{
				cashBookingDO = (CashBookingDO) dTDCBookingDO;
			}

			if (to != null) {
				// populating foreign key objects
				if (!StringUtil.isEmpty(to.getChannelType())) {
					ChannelDO channelDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						channelDO=((CashBookingDO)mnpBookingDOList.get(0)).getChannelTypeID();
					}else{
						channelDO = new ChannelDO();
					}

					String channelType = to.getChannelType();
					String[] tokens = channelType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (tokens[0] != null
							&& tokens[0].trim() != ApplicationConstants.EMPTY_STRING) {
						channelDO.setChannelTypeId(Integer.parseInt(tokens[0]));
						cashBookingDO.setChannelTypeID(channelDO);
					}
				}
				/*if(to.getBookingId()!=null && to.getBookingId()!=0){
					//	cashBookingDO.setBookingId(to.getBookingId());

				}else */if(to.getBookingId() != 0 || to.getBookingId() != null ){
					cashBookingDO.setServerDate(to.getBookingDate());
				}
				cashBookingDO.setServiceTax(to.getServiceTaxAmount());
				cashBookingDO.setServChrgAmt(to.getServiceChargeAmount());

				cashBookingDO.setActualWeight(to.getActualWeight());
				cashBookingDO.setFinalWeight(to.getFinalWeight());
				cashBookingDO.setVolumetricWght(to.getVolumetricWeight());

				/*if (to.getEmployeeId() != null && to.getEmployeeId() != 0) {
					EmployeeDO employeeDO = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						employeeDO=((CashBookingDO)mnpBookingDOList.get(0)).getEmployeeId();
					}else{
						employeeDO = new EmployeeDO();
					}
					employeeDO.setEmployeeId(to.getEmployeeId());
					cashBookingDO.setEmployeeIdForPickupBoyId(employeeDO);
				}*/

				/*if (to.getPickupBoyEmployeeIdforCash() != null && to.getPickupBoyEmployeeIdforCash() != 0) {
					EmployeeDO employeeDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						employeeDO=((CashBookingDO)mnpBookingDOList.get(0)).getEmployeeId();
					}else{
						employeeDO = new EmployeeDO();
					}
					employeeDO.setEmployeeId(to.getPickupBoyEmployeeIdforCash());
					cashBookingDO.setEmployeeIdForPickupBoyId(employeeDO);
				}*/

				if (to.getPickupBoyEmployeeId() != null && to.getPickupBoyEmployeeId() != 0) {
					EmployeeDO employeeDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						employeeDO=((CashBookingDO)mnpBookingDOList.get(0)).getEmployeeIdForPickupBoyId();
					}else{
						employeeDO = new EmployeeDO();
					}
					employeeDO.setEmployeeId(to.getPickupBoyEmployeeId());
					cashBookingDO.setEmployeeIdForPickupBoyId(employeeDO);
				}


				// commented as MNP will have only MNP as prod and prod id will
				// be stored as null
				/*
				 * String prodIdAndCode=to.getProductTypeForMNP(); if
				 * (prodIdAndCode!=null && !StringUtil.isEmpty(prodIdAndCode)) {
				 * ProductDO productDo = new ProductDO(); String[] productId =
				 * prodIdAndCode .split(ApplicationConstants.CHARACTER_HASH); if
				 * (productId[0] != null && !StringUtils.isEmpty(productId[0]))
				 * { productDo.setProductId(Integer.parseInt(productId[0]));
				 * cashBookingDO.setProductID(productDo); } }
				 */

				String vasProdCode = to.getProductIdAndCode();
				if (vasProdCode != null && !StringUtil.isEmpty(vasProdCode)) {
					vasProdCode = vasProdCode.substring(vasProdCode
							.indexOf(BookingConstants.SLASH) + 1);
					cashBookingDO.setVasProductCode(vasProdCode);
				}
				/*
				 * if (to.getGeographyID() != null && to.getGeographyID() != 0)
				 * { GeographyDO geographyDO = new GeographyDO();
				 * geographyDO.setGeographyId(to.getGeographyID());
				 * cashBookingDO.setGeographyIDForPincode(geographyDO); //
				 * franchiseeBookingDO.setGeographyIdForCity(geographyDO); }
				 */

				if (to.getPincodeId() != null && to.getPincodeId() != 0) {
					PincodeDO pincodeDO = new PincodeDO();
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						pincodeDO=((CashBookingDO)mnpBookingDOList.get(0)).getPincodeDO();
					}else{
						pincodeDO = new PincodeDO();
					}
					pincodeDO.setPincodeId(to.getPincodeId());
					cashBookingDO.setPincodeDO(pincodeDO);
				}

				if (to.getCityId() != null && to.getCityId() != 0) {
					CityDO cityDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						cityDO=((CashBookingDO)mnpBookingDOList.get(0)).getCityDO();
					}else{
						cityDO = new CityDO();
					}
					cityDO.setCityId(to.getCityId());
					cashBookingDO.setCityDO(cityDO);
				}

				if (!StringUtil.isEmpty(to.getDoxTypeIdAndCode())) {
					DocumentDO documentDo = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						documentDo=((CashBookingDO)mnpBookingDOList.get(0)).getDocumentID();
					}else{
						documentDo = new DocumentDO();
					}
					String doxType = to.getDoxTypeIdAndCode();
					String[] documentId = doxType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (documentId[0] != null
							&& documentId[0].trim() != ApplicationConstants.EMPTY_STRING) {
						documentDo.setDocumentId(Integer
								.parseInt(documentId[0]));
						cashBookingDO.setDocumentID(documentDo);
					}
				}  
				if (!StringUtil.isEmpty(to.getServiceType())) {
					ServiceDO serviceDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						serviceDO=((CashBookingDO)mnpBookingDOList.get(0)).getServiceID();
					}else{
						serviceDO = new ServiceDO();
					}
					String serviceType = to.getServiceType();
					String[] serviceId = serviceType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (!StringUtil.isEmpty(serviceId[0])) {
						serviceDO.setServiceId(Integer.parseInt(serviceId[0]));
						//serviceDO.setServiceType(serviceType);
						cashBookingDO.setServiceID(serviceDO);
					}
				}

				if (!StringUtil.isEmpty(to.getBookingZone())) {
					/*
					 * OfficeDO officeDO = new OfficeDO();
					 * officeDO.setOfficeId(to.getBookingZone());
					 */
					cashBookingDO.setBookingDivisionID(to.getBookingZone());
				}

				if (to.getCustomerId() != null && to.getCustomerId() != 0) {
					CustomerDO customerDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						customerDO=((CashBookingDO)mnpBookingDOList.get(0)).getCustomerId();
					}else{
						customerDO = new CustomerDO();
					}
					customerDO.setCustomerId(to.getCustomerId());
					cashBookingDO.setCustomerId(customerDO);
				}

				// populating Header information
				cashBookingDO.setSelectScheme(to.getSelectScheme());
				cashBookingDO.setNoOfPieces(to.getNoOfPieces());
				cashBookingDO.setCreateChildCn(to.getCreateChildCn());
				cashBookingDO.setConsignmentNumber(to.getConsignmentNumber());
				cashBookingDO.setExpectedDlvDate(to.getEdd());
				cashBookingDO.setBookingDate(to.getBookingDate());
				SimpleDateFormat sdfDate = new SimpleDateFormat(
						BookingConstants.TIME_FORMAT_HHMMSS);
				Date now = new Date();
				String strDate = sdfDate.format(now);

				cashBookingDO.setBookingTime(strDate);
				cashBookingDO.setPaid(to.getCashPaid());
				cashBookingDO.setRemarks(to.getRemarks());
				cashBookingDO.setDbServer(to.getDbServer());

				if (to.getNoOfPieces() != 0 && to.getNoOfPieces() > 1) {
					if (to.getLength() != null && to.getLength().length != 0) {
						Float length = to.getLength()[0];
						Float breath = to.getBreadth()[0];
						Float height = to.getHeight()[0];
						Double actualWeigtht = to.getActualWeightGrid()[0];
						Double volweight = to.getVolumetricWeightGrid()[0];
						Double finalWeight = to.getFinalWeightGrid()[0];
						Integer noOfPiece = to.getNoOfPieces();
						cashBookingDO.setLength(length * noOfPiece);
						cashBookingDO.setBreadth(breath * noOfPiece);
						cashBookingDO.setHeight(height * noOfPiece);
						cashBookingDO
						.setActualWeight(actualWeigtht * noOfPiece);
						cashBookingDO.setVolumetricWght(volweight * noOfPiece);
						cashBookingDO.setFinalWeight(finalWeight * noOfPiece);
					}
				} else {
					cashBookingDO.setLength(to.getLengthInCms());
					cashBookingDO.setBreadth(to.getBreadthInCms());
					cashBookingDO.setHeight(to.getHeightInCms());
					cashBookingDO.setActualWeight(to.getActualWeight());
					//cashBookingDO.setVolumetricWght(to.getVolumtericWeight());
					cashBookingDO.setFinalWeight(to.getFinalWeight());
				}

				cashBookingDO.setIsInsured(to.getIsInsured());
				cashBookingDO.setInsuredBy(to.getInsuredBy());
				cashBookingDO.setInvoiceValue(to.getInvoiceValue());

				/*
				 * GeographyDO geographyConsigneeDO = new GeographyDO();
				 * geographyConsigneeDO.setGeographyId(to
				 * .getGeographyIdForConsignee());
				 * consigneeAddressDO.setGeography(geographyConsigneeDO);
				 */

				Set address = new HashSet();
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;
				if (to.getAreaIdForConsignee() != null
						&& to.getAreaIdForConsignee() != 0) {

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						address = new HashSet();
						Iterator iterator=((CashBookingDO)(mnpBookingDOList.get(0))).getConsigneeID().getAddresses().iterator();
						consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
						areaDO=consigneeAddressDO.getAreaDO();
						areaDO.setAreaId(to.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
						address.add(consigneeAddressDO);


					}else{
						consigneeAddressDO=new ConsigneeAddressDO();
						areaDO= new AreaDO();
						address = new HashSet();
						areaDO.setAreaId(to.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
						/*if(to.getConsigneeAddressId() != null && to.getConsigneeAddressId() != 0){
								consigneeAddressDO.setConsgAddrId(to.getConsigneeAddressId());
							}*/
						address.add(consigneeAddressDO);
					}
					address.add(consigneeAddressDO);
				}


				// for setting values in ConsigneeDO
				ConsigneeDO consigneeDO = null;

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					consigneeDO=((CashBookingDO)mnpBookingDOList.get(0)).getConsigneeID();
				}else{
					consigneeDO= new ConsigneeDO();
				}
				consigneeDO.setFirstName(to.getConsigneeName());
				consigneeDO.setEmail(to.getConsigneeEmail());
				if (to.getConsigneePhone() != null) {
					consigneeDO.setPhone(to.getConsigneePhone().toString());
				}

				consigneeDO.setAddresses(address);
				cashBookingDO.setConsigneeID(consigneeDO);

				/* Set the consignor Info */

				Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
				// create set of consignee address and set that to the consigneeDO
				// adresses.
				ConsignerAddressDO consignorAddressDO = null;
				PincodeDO consignorPincodeDO = null;
				CityDO consignorCityDO =null;

				if (to.getConsignorPincodeId() != null
						&& to.getConsignorPincodeId() > 0) {

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						Iterator iterator=((CashBookingDO)(mnpBookingDOList.get(0))).getConsignorID().getConsignorAddresses().iterator();
						consignorAddressDO=(ConsignerAddressDO)iterator.next();

						consignorPincodeDO = consignorAddressDO.getPincodeId();
						consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorCityDO = consignorAddressDO.getCity();
						consignorCityDO.setCityId(to.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);

						consignorAddressDO.setStreet1(to.getConsignorAddress1());
						consignorAddressDO.setStreet2(to.getConsignorAddress2());
						consignorAddressDO.setStreet3(to.getConsignorAddress3());

					}else{
						consignorAddressDO= new ConsignerAddressDO();
						consignorPincodeDO = new PincodeDO();
						consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorAddressDO.setStreet1(to.getConsignorAddress1());
						consignorAddressDO.setStreet2(to.getConsignorAddress2());
						consignorAddressDO.setStreet3(to.getConsignorAddress3());

						if (to.getConsignerCityId() != null
								&& to.getConsignerCityId() > 0) {
							consignorCityDO = new CityDO();
							consignorCityDO.setCityId(to.getConsignerCityId());
							consignorAddressDO.setCity(consignorCityDO);
						}

					}

					/*if(bookingTO.getConsigrAddrId() != null && bookingTO.getConsigrAddrId() != 0){
						consignorAddressDO.setConsigrAddrId(bookingTO.getConsigrAddrId());
					}*/
					consignorAddress.add(consignorAddressDO);


					/*ConsignerAddressDO consignorAddressDO = new ConsignerAddressDO();
					PincodeDO consignorPincodeDO = new PincodeDO();
					consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);
					consignorAddressDO.setStreet1(to.getConsignorAddress1());
					consignorAddressDO.setStreet2(to.getConsignorAddress2());
					consignorAddressDO.setStreet3(to.getConsignorAddress3());
					if(to.getAreaIdForConsigner() != null 
							&& to.getAreaIdForConsigner() > 0){
						AreaDO areaDO= new AreaDO();
						areaDO.setAreaId(to.getAreaIdForConsigner());
						consignorAddressDO.setAreaDO(areaDO);
					}
					if (to.getConsigrAddrId() != null && to.getConsigrAddrId() != 0) {
						consignorAddressDO.setConsigrAddrId(to.getConsigrAddrId());
					}else{
						consignorAddressDO.setConsigrAddrId(null);
					}
					if(to.getConsignorCity() != null){
						CityDO cityDo = null;

						if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
							cityDo=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getCityDO();
						}else{
							cityDo = new CityDO();
						}

						cityDo.setCityId(to.getConsignerCityId());
						consignorAddressDO.setCity(cityDo);
					}
					consignorAddress.add(consignorAddressDO);*/
				}

				ConsignerInfoDO consignorDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					consignorDO=((CashBookingDO)mnpBookingDOList.get(0)).getConsignorID();
				}else{
					consignorDO= new ConsignerInfoDO();
				}
				consignorDO.setFirstName(to.getConsignorName());
				consignorDO.setEmail(to.getConsignorEmail());
				if (to.getConsignorPhone() != null) {
					consignorDO.setPhone(to.getConsignorPhone().toString());
				}
				consignorDO.setConsignorAddresses(consignorAddress);
				cashBookingDO.setConsignorID(consignorDO);


				cashBookingDO.setTaxableAmount(to.getSubTotalFreight());
				cashBookingDO.setTaxPayable(to.getServiceTaxAmount());
				cashBookingDO.setDiscount(to.getDiscountAmount());
				cashBookingDO.setFrieght(to.getFreight());
				cashBookingDO.setDiscountInPerc(to.getDisPlayDiscount());
				cashBookingDO.setServiceTax(to.getServiceTaxAmount());
				cashBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
				cashBookingDO.setServChrgAmt(to.getServiceChargeAmount());
				cashBookingDO.setAmount(to.getGrandTotal());

				cashBookingDO.setTsAmount(to.getFreight());
				cashBookingDO.setDiplomaticOrRemoteAreaCharge(to.getDiplomaticOrRemoteAreaCharge());
				cashBookingDO.setMiscCharge(to.getTotalMiscExpense());

				/*cashBookingDO.setFrieght(to.getFreight());
				cashBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
				cashBookingDO.setServChrgAmt(to.getServiceChargeAmount());
				cashBookingDO.setAmount(to.getGrandTotal());
				cashBookingDO.setRemarks(to.getRemarks());
				cashBookingDO.setDiscountInPerc(to.getDisPlayDiscount());
				cashBookingDO.setServiceTax(to.getDisPlayServiceTax());*/
				// for setting remote delivery popup data
				cashBookingDO.setAreaNameLtDox(to.getAreaName());
				cashBookingDO.setDoxAmount(new Double(to
						.getRemoteDeliveryDoxAmount()));
				cashBookingDO.setNonDoxAmount(new Double(to
						.getRemoteDeliveryNonDoxAmount()));
				cashBookingDO.setRemoteDeliveryRemarks(to
						.getRemoteDeliveryRemarks());
				cashBookingDO.setExtrDlvCharges(to.getExtraDeliveryCharges());
				cashBookingDO.setExtDlvRemarks(to.getExtDlvRemarks());
				// for setting passport popup data
				cashBookingDO.setPassportDetails(to.getPassportDetails());

				/* Fopr Mnp items */

				/* For storing sub item Info 
				if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

					int itemSize = to.getSubItemId().length;
					for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
						if (to.getSubItemId()[itemCnt] != null
								&& to.getSubItemId()[itemCnt] != 0) {
							BookingItemListDO bookingItemDo = null;
							CommodityDO commodityDO = null;


							if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
							 Iterator iterator=((CashBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
								bookingItemDo=(BookingItemListDO)iterator.next();
								commodityDO=((CashBookingDO)mnpBookingDOList.get(0)).getDomesticCommodityId();
							}else{
								bookingItemDo=new BookingItemListDO();
								commodityDO= new CommodityDO();
							}

							commodityDO
									.setCommodityId(to.getSubItemId()[itemCnt]);
							bookingItemDo.setCommodity(commodityDO);
							bookingItemDo
									.setComments(to.getSubItemComments()[itemCnt]);
							bookingItemDo.setNumberOfPieces(to
									.getSubItemNoOfPeice()[itemCnt]);
							bookingItemDo
									.setSerialNumber(to.getSubItemSlNo()[itemCnt]);

							bookingItemDo.setCommodity(commodityDO);
							cashBookingDO.setDomesticCommodityId(commodityDO);
							if(to.getBookingItemlistIds()!=null && to.getBookingItemlistIds().length>0 && to.getBookingItemlistIds()[0] != 0){

								if(itemCnt<=to.getBookingItemlistIds().length && to.getBookingItemlistIds()[itemCnt] != 0){
									bookingItemDo.setBookingItemlistId(to.getBookingItemlistIds()[itemCnt]);
								}
							}else if(to.getBookingItemlistIds()==null && to.getBookingItemlistIds()[0] == 0){
								bookingItemDo.setBookingItemlistId(null);
							}

							mnpItem.add(bookingItemDo);
						}
					}

				}
				 For storing main item Info 
				if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
					String[] mnpCommodityId = to.getMnpCodeAndId().split(
							ApplicationConstants.CHARACTER_HASH);
					if (mnpCommodityId != null
							&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
						BookingItemListDO bookingMainItemDo = null;
						CommodityDO commodityMainItemDO = null;

						if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
							 Iterator iterator=((CashBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
							 bookingMainItemDo=(BookingItemListDO)iterator.next();
							 commodityMainItemDO=((FranchiseeBookingDO)mnpBookingDOList.get(0)).getDomesticCommodityId();
							}else{
								bookingMainItemDo=new BookingItemListDO();
								commodityMainItemDO= new CommodityDO();
							}


						commodityMainItemDO.setCommodityId(Integer
								.valueOf(mnpCommodityId[0]));
						bookingMainItemDo.setCommodity(commodityMainItemDO);
						mnpItem.add(bookingMainItemDo);
					}
				}*/

				/* Fopr Mnp items */
				Set mnpItem = new HashSet();

				/* For storing sub item Info */
				if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

					int itemSize = to.getSubItemId().length;

					for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
						if (to.getSubItemId()[itemCnt] != null
								&& to.getSubItemId()[itemCnt] != 0) {
							BookingItemListDO bookingItemDo = new BookingItemListDO();
							CommodityDO commodityDO = new CommodityDO();
							commodityDO.setCommodityId(to.getSubItemId()[itemCnt]);
							bookingItemDo.setCommodity(commodityDO);
							bookingItemDo
							.setComments(to.getSubItemComments()[itemCnt]);
							bookingItemDo.setNumberOfPieces(to
									.getSubItemNoOfPeice()[itemCnt]);
							bookingItemDo
							.setSerialNumber(to.getSubItemSlNo()[itemCnt]);
							mnpItem.add(bookingItemDo);
						}
					}

				}
				/* For storing main item Info */
				if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
					String[] mnpCommodityId = to.getMnpCodeAndId().split(
							ApplicationConstants.CHARACTER_HASH);
					if (mnpCommodityId != null
							&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
						BookingItemListDO bookingMainItemDo = new BookingItemListDO();
						CommodityDO commodityMainItemDO = new CommodityDO();
						commodityMainItemDO.setCommodityId(Integer
								.valueOf(mnpCommodityId[0]));
						bookingMainItemDo.setCommodity(commodityMainItemDO);
						mnpItem.add(bookingMainItemDo);
					}
				}
				cashBookingDO.setMnpItems(mnpItem);
				cashBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

				// save booking branch off id
				if (to.getBookingBranchofficeID() != null
						&& to.getBookingBranchofficeID() != 0) {
					OfficeDO bookingBranchOffice = null;

					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						bookingBranchOffice=((CashBookingDO)mnpBookingDOList.get(0)).getOfficeID();
					}else{
						bookingBranchOffice=new OfficeDO();
					}
					bookingBranchOffice.setOfficeId(to
							.getBookingBranchofficeID());
					cashBookingDO.setBookingBranchDetails(to.getBookingBranchDetails());
					cashBookingDO.setOfficeID(bookingBranchOffice);
				}

				if (to.getCccId()!= null
						&& to.getCccId() != 0) {
					OfficeDO bookingBranchOffice = new OfficeDO();
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						bookingBranchOffice=((CashBookingDO)mnpBookingDOList.get(0)).getOfficeID();
					}else{
						bookingBranchOffice=new OfficeDO();
					}
					bookingBranchOffice.setOfficeId(to
							.getCccId());
					cashBookingDO.setCashCollectionCenter(bookingBranchOffice);
				}
				cashBookingDO.setDiFlag(BookingConstants.DIFLAG);
				cashBookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

				//cashBookingDO.setInvoiceValue(to.getValue());
				cashBookingDO.setSecurityPouchNo(to.getSecurityPouchNo());
				cashBookingDO.setPrioritySticker(to.getPrioritySticker());
				cashBookingDO.setPaymentMode(to.getPaymentMode());
				cashBookingDO.setUserId(to.getUserId());
				/* Fopr Mnp items Ends */
				cashBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

				cashBookingDO.setWeighingType(to.getCaptureWeightType());
				cashBookingDO.setActualWeight(to.getWeight());

				// Create new consignment for Nondoxpaperwrk items and store the
				// items in BookingItemList against the booking id
				CashBookingDO paperWrkItemDO = null;
				if (to != null
						&& !StringUtil.isEmpty(to.getNoOfPaperWrkItems())) {
					paperWrkItemDO = (CashBookingDO) getPaperWorkBookingDO(to,
							BookingConstants.CASH_BOOKING_TYPE);
					paperWrkItemDO.setDiFlag(BookingConstants.DIFLAG);
					paperWrkItemDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
					bookingDoList.add(paperWrkItemDO);
				}

			}
			try{
				// for setting Child CN data popup data
				if (to != null
						&& to.getChildCNDetailsHidden() != null
						&& !StringUtil.isEmpty(to.getChildCNDetailsHidden())) {
					getSetOfChildConsignments(to.getChildCNDetailsHidden(),
							to.getConsignmentNumber());
					itr = parentChildConsignmentDOList.iterator();
					while (itr.hasNext()) {
						BookingDO bookingDO1 = (BookingDO) itr.next();
						bookingDO1.setDiFlag(BookingConstants.DIFLAG);
						bookingDO1.setReadByLocal(BookingConstants.READ_BY_LOCAL);
						if (!bookingDoList.isEmpty() && bookingDoList.size() != 0) {
							bookingDoList.add(bookingDO1);
						}

					}

				}}catch(Exception e){
					logger.error("MnpBookingMDBServiceImpl::convertTotoDO::Exception occured:"
							+e.getMessage());
				}

				bookingDoList.add(cashBookingDO);
		}
		if (dTDCBookingDO instanceof DirectPartyBookingDO) {
			DirectPartyBookingDO directPartyBookingDO = null;

			if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
				directPartyBookingDO=(DirectPartyBookingDO)mnpBookingDOList.get(0);
			}else{
				directPartyBookingDO = (DirectPartyBookingDO) dTDCBookingDO;
			}

			/*if(to.getBookingId()!=null && to.getBookingId()!=0){
				//directPartyBookingDO.setBookingId(to.getBookingId());
			}else */if(to.getBookingId() != 0 || to.getBookingId() != null){
				directPartyBookingDO.setServerDate(to.getBookingDate());
			}
			directPartyBookingDO.setInvoiceValue(to.getInvoiceValue());
			directPartyBookingDO.setSecurityPouchNo(to.getSecurityPouchNo());
			directPartyBookingDO.setPrioritySticker(to.getPrioritySticker());
			directPartyBookingDO.setPaymentMode(to.getPaymentMode());
			directPartyBookingDO.setUserId(to.getUserId());
			directPartyBookingDO.setActualWeight(to.getActualWeight());
			directPartyBookingDO.setFinalWeight(to.getFinalWeight());
			directPartyBookingDO.setVolumetricWght(to.getVolumetricWeight());

			directPartyBookingDO.setServiceTax(to.getServiceTaxAmount());
			directPartyBookingDO.setServChrgAmt(to.getServiceTaxAmount());

			directPartyBookingDO.setDiFlag(BookingConstants.DIFLAG);
			directPartyBookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

			if (!StringUtil.isEmpty(to.getChannelType())) {
				ChannelDO channelDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					channelDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getChannelTypeID();
				}else{
					channelDO = new ChannelDO();
				}
				String channelType = to.getChannelType();
				String[] tokens = channelType
				.split(ApplicationConstants.CHARACTER_HASH);
				if (tokens[0] != null
						&& tokens[0].trim() != ApplicationConstants.EMPTY_STRING) {
					channelDO.setChannelTypeId(Integer.parseInt(tokens[0]));
					directPartyBookingDO.setChannelTypeID(channelDO);
				}
			}

			/*if (to.getEmployeeId() != null && to.getEmployeeId() != 0) {
				EmployeeDO employeeDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					employeeDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getEmployeeId();
				}else{
					employeeDO = new EmployeeDO();
				}
				employeeDO.setEmployeeId(to.getEmployeeId());
				directPartyBookingDO.setEmployeeIdForPickupBoyId(employeeDO);
			}*/

			if (to.getPickupBoyEmployeeId() != null && to.getPickupBoyEmployeeId() != 0) {
				EmployeeDO employeeDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					employeeDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getEmployeeIdForPickupBoyId();
				}else{
					employeeDO = new EmployeeDO();
				}
				employeeDO.setEmployeeId(to.getPickupBoyEmployeeId());
				directPartyBookingDO.setEmployeeIdForPickupBoyId(employeeDO);

			}

			/*
			 * String prodIdAndCode=to.getProductTypeForMNP(); if
			 * (prodIdAndCode!=null && !StringUtil.isEmpty(prodIdAndCode)) {
			 * ProductDO productDo = new ProductDO(); String[] productId =
			 * prodIdAndCode .split(ApplicationConstants.CHARACTER_HASH); if
			 * (productId[0] != null && !StringUtils.isEmpty(productId[0])) {
			 * productDo.setProductId(Integer.parseInt(productId[0]));
			 * directPartyBookingDO.setProductID(productDo); } }
			 */

			String vasProdCode = to.getProductIdAndCode();
			if (vasProdCode != null && !StringUtil.isEmpty(vasProdCode)) {
				vasProdCode = vasProdCode.substring(vasProdCode
						.indexOf(BookingConstants.SLASH) + 1);
				directPartyBookingDO.setVasProductCode(vasProdCode);
			}

			/*
			 * if (to.getGeographyID() != null && to.getGeographyID() != 0) {
			 * GeographyDO geographyDO = new GeographyDO();
			 * geographyDO.setGeographyId(to.getGeographyID());
			 * directPartyBookingDO.setGeographyIDForPincode(geographyDO); }
			 */

			if (to.getPincodeId() != null && to.getPincodeId() != 0) {
				PincodeDO pincodeDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					pincodeDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getPincodeDO();
				}else{
					pincodeDO = new PincodeDO();
				}
				pincodeDO.setPincodeId(to.getPincodeId());
				directPartyBookingDO.setPincodeDO(pincodeDO);
			}

			if (to.getCityId() != null && to.getCityId() != 0) {

				CityDO cityDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					cityDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getCityDO();
				}else{
					cityDO = new CityDO();
				}
				cityDO.setCityId(to.getCityId());
				directPartyBookingDO.setCityDO(cityDO);
			}

			if (!StringUtil.isEmpty(to.getDoxTypeIdAndCode())) {
				DocumentDO documentDo = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					documentDo=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getDocumentID();
				}else{
					documentDo = new DocumentDO();
				}
				String doxType = to.getDoxTypeIdAndCode();
				String[] documentId = doxType
				.split(ApplicationConstants.CHARACTER_HASH);
				if (documentId[0] != null
						&& documentId[0].trim() != ApplicationConstants.EMPTY_STRING) {
					documentDo.setDocumentId(Integer.parseInt(documentId[0]));
					directPartyBookingDO.setDocumentID(documentDo);
				}
			}
			if (!StringUtil.isEmpty(to.getServiceType())) {
				ServiceDO serviceDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					serviceDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getServiceID();
				}else{
					serviceDO = new ServiceDO();
				}
				String serviceType = to.getServiceType();
				String[] serviceId = serviceType
				.split(ApplicationConstants.CHARACTER_HASH);
				if (!StringUtil.isEmpty(serviceId[0])) {
					serviceDO.setServiceId(Integer.parseInt(serviceId[0]));
					//serviceDO.setServiceType(serviceType);
					directPartyBookingDO.setServiceID(serviceDO);
				}
			}

			if (!StringUtil.isEmpty(to.getBookingZone())) {
				/*
				 * OfficeDO officeDO = new OfficeDO();
				 * officeDO.setOfficeId(to.getBookingZone());
				 */
				directPartyBookingDO.setBookingDivisionID(to.getBookingZone());
			}

			if (to.getCustomerId() != null && to.getCustomerId() != 0) {
				CustomerDO customerDO = null;
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					customerDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getCustomerId();
				}else{
					customerDO = new CustomerDO();
				}
				customerDO.setCustomerId(to.getCustomerId());
				directPartyBookingDO.setCustomerId(customerDO);
			}

			// populating Header information

			directPartyBookingDO.setSelectScheme(to.getSelectScheme());
			directPartyBookingDO.setNoOfPieces(to.getNoOfPieces());
			directPartyBookingDO.setCreateChildCn(to.getCreateChildCn());

			directPartyBookingDO
			.setConsignmentNumber(to.getConsignmentNumber());
			directPartyBookingDO.setExpectedDlvDate(to.getEdd());

			directPartyBookingDO.setBookingDate(to.getBookingDate());

			SimpleDateFormat sdfDate = new SimpleDateFormat(
					BookingConstants.TIME_FORMAT_HHMMSS);
			Date now = new Date();
			String strDate = sdfDate.format(now);

			directPartyBookingDO.setBookingTime(strDate);

			if (to.getNoOfPieces() != 0 && to.getNoOfPieces() > 1) {
				if (to.getLength() != null && to.getLength().length != 0) {
					Float length = to.getLength()[0];
					Float breath = to.getBreadth()[0];
					Float height = to.getHeight()[0];
					Double actualWeigtht = to.getActualWeightGrid()[0];
					Double volweight = to.getVolumetricWeightGrid()[0];
					Double finalWeight = to.getFinalWeightGrid()[0];
					Integer noOfPiece = to.getNoOfPieces();
					directPartyBookingDO.setLength(length * noOfPiece);
					directPartyBookingDO.setBreadth(breath * noOfPiece);
					directPartyBookingDO.setHeight(height * noOfPiece);
					directPartyBookingDO.setActualWeight(actualWeigtht
							* noOfPiece);
					directPartyBookingDO.setVolumetricWght(volweight
							* noOfPiece);
					directPartyBookingDO
					.setFinalWeight(finalWeight * noOfPiece);
				}
			} else {
				directPartyBookingDO.setLength(to.getLengthInCms());
				directPartyBookingDO.setBreadth(to.getBreadthInCms());
				directPartyBookingDO.setHeight(to.getHeightInCms());
				directPartyBookingDO.setActualWeight(to.getActualWeight());
				//directPartyBookingDO.setVolumetricWght(to.getVolumtericWeight());
				directPartyBookingDO.setFinalWeight(to.getFinalWeight());
			}

			directPartyBookingDO.setTaxableAmount(to.getSubTotalFreight());
			directPartyBookingDO.setTaxPayable(to.getServiceTaxAmount());
			directPartyBookingDO.setDiscount(to.getDiscountAmount());
			directPartyBookingDO.setFrieght(to.getFreight());
			directPartyBookingDO.setDiscountInPerc(to.getDisPlayDiscount());
			directPartyBookingDO.setServiceTax(to.getServiceTaxAmount());
			directPartyBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
			directPartyBookingDO.setServChrgAmt(to.getServiceChargeAmount());
			directPartyBookingDO.setAmount(to.getGrandTotal());

			directPartyBookingDO.setTsAmount(to.getFreight());
			directPartyBookingDO.setDiplomaticOrRemoteAreaCharge(to.getDiplomaticOrRemoteAreaCharge());
			directPartyBookingDO.setMiscCharge(to.getTotalMiscExpense());

			directPartyBookingDO.setIsInsured(to.getIsInsured());
			directPartyBookingDO.setInsuredBy(to.getInsuredBy());
			directPartyBookingDO.setInvoiceValue(to.getInvoiceValue());
			directPartyBookingDO.setDbServer(to.getDbServer());
			// for setting values in ConsigneeAddressDO

			Set address = new HashSet();
			ConsigneeAddressDO consigneeAddressDO = null;
			AreaDO areaDO =null;
			if (to.getAreaIdForConsignee() != null
					&& to.getAreaIdForConsignee() != 0) {

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					address = new HashSet();
					Iterator iterator=((DirectPartyBookingDO)(mnpBookingDOList.get(0))).getConsigneeID().getAddresses().iterator();
					consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
					areaDO=consigneeAddressDO.getAreaDO();
					areaDO.setAreaId(to.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
					consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
					consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
					consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
					address.add(consigneeAddressDO);


				}else{
					consigneeAddressDO=new ConsigneeAddressDO();
					areaDO= new AreaDO();
					address = new HashSet();
					areaDO.setAreaId(to.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
					consigneeAddressDO.setStreet1(to.getConsigneeAddress1());
					consigneeAddressDO.setStreet2(to.getConsigneeAddress2());
					consigneeAddressDO.setStreet3(to.getConsigneeAddress3());
					/*if(to.getConsigneeAddressId() != null && to.getConsigneeAddressId() != 0){
							consigneeAddressDO.setConsgAddrId(to.getConsigneeAddressId());
						}*/
					address.add(consigneeAddressDO);
				}
				address.add(consigneeAddressDO);
			}

			/*
			 * GeographyDO geographyConsigneeDO = new GeographyDO();
			 * geographyConsigneeDO
			 * .setGeographyId(to.getGeographyIdForConsignee());
			 */
			// for setting values in ConsigneeDO
			ConsigneeDO consigneeDO = null;

			if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
				consigneeDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getConsigneeID();
			}else{
				consigneeDO= new ConsigneeDO();
			}
			consigneeDO.setFirstName(to.getConsigneeName());
			consigneeDO.setEmail(to.getConsigneeEmail());
			if (to.getConsigneePhone() != null) {
				consigneeDO.setPhone(to.getConsigneePhone().toString());
			}
			/*
			 * Set address = new HashSet(); address.add(consigneeAddressDO);
			 */
			consigneeDO.setAddresses(address);
			directPartyBookingDO.setConsigneeID(consigneeDO);

			/* Set the consignor Info */

			Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
			// create set of consignee address and set that to the consigneeDO
			// adresses.
			ConsignerAddressDO consignorAddressDO = null;
			PincodeDO consignorPincodeDO = null;
			CityDO consignorCityDO =null;
			if (to.getConsignorPincodeId() != null
					&& to.getConsignorPincodeId() > 0) {
				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					Iterator iterator=((DirectPartyBookingDO)(mnpBookingDOList.get(0))).getConsignorID().getConsignorAddresses().iterator();
					consignorAddressDO=(ConsignerAddressDO)iterator.next();

					consignorPincodeDO = consignorAddressDO.getPincodeId();
					consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);

					consignorCityDO = consignorAddressDO.getCity();
					consignorCityDO.setCityId(to.getConsignerCityId());
					consignorAddressDO.setCity(consignorCityDO);

					consignorAddressDO.setStreet1(to.getConsignorAddress1());
					consignorAddressDO.setStreet2(to.getConsignorAddress2());
					consignorAddressDO.setStreet3(to.getConsignorAddress3());

				}else{
					consignorAddressDO= new ConsignerAddressDO();
					consignorPincodeDO = new PincodeDO();
					consignorPincodeDO.setPincodeId(to.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);

					consignorAddressDO.setStreet1(to.getConsignorAddress1());
					consignorAddressDO.setStreet2(to.getConsignorAddress2());
					consignorAddressDO.setStreet3(to.getConsignorAddress3());

					if (to.getConsignerCityId() != null
							&& to.getConsignerCityId() > 0) {
						consignorCityDO = new CityDO();
						consignorCityDO.setCityId(to.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);
					}

				}
				consignorAddress.add(consignorAddressDO);
			}

			ConsignerInfoDO consignorDO = null;

			if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
				consignorDO=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getConsignorID();
			}else{
				consignorDO= new ConsignerInfoDO();
			}
			consignorDO.setFirstName(to.getConsignorName());
			consignorDO.setEmail(to.getConsignorEmail());
			if (to.getConsignorPhone() != null) {
				consignorDO.setPhone(to.getConsignorPhone().toString());
			}
			consignorDO.setConsignorAddresses(consignorAddress);
			directPartyBookingDO.setConsignorID(consignorDO);


			directPartyBookingDO.setRiskSurchgAmt(to.getRiskSurchargeAmount());
			directPartyBookingDO.setServChrgAmt(to.getServiceChargeAmount());
			directPartyBookingDO.setAmount(to.getGrandTotal());
			// directPartyBookingDO.setRemarks(to.getCashBookingRemarks());
			// for setting remote delivery popup data
			directPartyBookingDO.setAreaNameLtDox(to.getAreaName());
			directPartyBookingDO.setDoxAmount(new Double(to
					.getRemoteDeliveryDoxAmount()));
			directPartyBookingDO.setNonDoxAmount(new Double(to
					.getRemoteDeliveryNonDoxAmount()));
			directPartyBookingDO.setRemoteDeliveryRemarks(to
					.getRemoteDeliveryRemarks());
			directPartyBookingDO
			.setExtrDlvCharges(to.getExtraDeliveryCharges());
			directPartyBookingDO.setExtDlvRemarks(to.getExtDlvRemarks());
			// for setting passport popup data
			directPartyBookingDO.setPassportDetails(to.getPassportDetails());

			/* Fopr Mnp items 
			Set mnpItem = new HashSet();
			Iterator iterator=null;
			Iterator iterator1=null;
			 For storing sub item Info 
			if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

				int itemSize = to.getSubItemId().length;

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					 iterator=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
				}

				for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
					if (to.getSubItemId()[itemCnt] != null
							&& to.getSubItemId()[itemCnt] != 0) {
						BookingItemListDO bookingItemDo = null;
						CommodityDO commodityDO = null;


						if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						 //Iterator iterator=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
							bookingItemDo=(BookingItemListDO)iterator.next();
							commodityDO=bookingItemDo.getCommodity();
						}else{
							bookingItemDo=new BookingItemListDO();
							commodityDO= new CommodityDO();
						}
						if(commodityDO !=null){
						commodityDO
								.setCommodityId(to.getSubItemId()[itemCnt]);
						bookingItemDo.setCommodity(commodityDO);
						bookingItemDo
								.setComments(to.getSubItemComments()[itemCnt]);
						bookingItemDo.setNumberOfPieces(to
								.getSubItemNoOfPeice()[itemCnt]);
						bookingItemDo
								.setSerialNumber(to.getSubItemSlNo()[itemCnt]);
						bookingItemDo.setCommodity(commodityDO);

						//directPartyBookingDO.setDomesticCommodityId(commodityDO);

						if(to.getBookingItemlistIds()!=null && to.getBookingItemlistIds().length>0 && to.getBookingItemlistIds()[0] != 0){

							if(itemCnt<=to.getBookingItemlistIds().length && to.getBookingItemlistIds()[itemCnt] != 0){
								bookingItemDo.setBookingItemlistId(to.getBookingItemlistIds()[itemCnt]);
							}
						}else if(to.getBookingItemlistIds()==null && to.getBookingItemlistIds()[0] == 0){
							bookingItemDo.setBookingItemlistId(null);
						}


					}
						mnpItem.add(bookingItemDo);
					}
				}

			}
			 For storing main item Info 
			if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
				String[] mnpCommodityId = to.getMnpCodeAndId().split(
						ApplicationConstants.CHARACTER_HASH);
				if (mnpCommodityId != null
						&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
					BookingItemListDO bookingMainItemDo = null;
					CommodityDO commodityMainItemDO = null;
					if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
						iterator1=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getMnpItems().iterator();
						 //while(iterator1.hasNext()){

								if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){

									 bookingMainItemDo=(BookingItemListDO)iterator1.next();
									 commodityMainItemDO=bookingMainItemDo.getCommodity();
								}
								else{
										bookingMainItemDo=new BookingItemListDO();
										commodityMainItemDO= new CommodityDO();
										commodityMainItemDO.setCommodityId(Integer
												.valueOf(mnpCommodityId[0]));
										bookingMainItemDo.setCommodity(commodityMainItemDO);
									}



								//mnpItem.add(bookingMainItemDo);
						 }
				//	}
				else{
						bookingMainItemDo=new BookingItemListDO();
						commodityMainItemDO= new CommodityDO();
						commodityMainItemDO.setCommodityId(Integer
								.valueOf(mnpCommodityId[0]));
						bookingMainItemDo.setCommodity(commodityMainItemDO);
					}
					if(commodityMainItemDO.getParentCommCode() !=null){
						bookingMainItemDo.setCommodity(commodityMainItemDO);
					}
					mnpItem.add(bookingMainItemDo);
				}
			}*/



			/* Fopr Mnp items */
			Set mnpItem = new HashSet();

			/* For storing sub item Info */
			if (to.getSubItemId() != null && to.getSubItemId().length != 0) {

				int itemSize = to.getSubItemId().length;

				for (int itemCnt = 0; itemCnt < itemSize; itemCnt++) {
					if (to.getSubItemId()[itemCnt] != null
							&& to.getSubItemId()[itemCnt] != 0) {
						BookingItemListDO bookingItemDo = new BookingItemListDO();
						CommodityDO commodityDO = new CommodityDO();
						commodityDO.setCommodityId(to.getSubItemId()[itemCnt]);
						bookingItemDo.setCommodity(commodityDO);
						bookingItemDo
						.setComments(to.getSubItemComments()[itemCnt]);
						bookingItemDo.setNumberOfPieces(to
								.getSubItemNoOfPeice()[itemCnt]);
						bookingItemDo
						.setSerialNumber(to.getSubItemSlNo()[itemCnt]);
						mnpItem.add(bookingItemDo);
					}
				}

			}
			/* For storing main item Info */
			if (!StringUtil.isEmpty(to.getMnpCodeAndId())) {
				String[] mnpCommodityId = to.getMnpCodeAndId().split(
						ApplicationConstants.CHARACTER_HASH);
				if (mnpCommodityId != null
						&& mnpCommodityId[0] != ApplicationConstants.EMPTY_STRING) {
					BookingItemListDO bookingMainItemDo = new BookingItemListDO();
					CommodityDO commodityMainItemDO = new CommodityDO();
					commodityMainItemDO.setCommodityId(Integer
							.valueOf(mnpCommodityId[0]));
					bookingMainItemDo.setCommodity(commodityMainItemDO);
					mnpItem.add(bookingMainItemDo);
				}
			}
			directPartyBookingDO.setMnpItems(mnpItem);
			directPartyBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

			// save booking branch off id

			if (to.getBookingBranchofficeID() != null
					&& to.getBookingBranchofficeID() != 0) {
				OfficeDO bookingBranchOffice = null;

				if(mnpBookingDOList!=null && mnpBookingDOList.size()>0){
					bookingBranchOffice=((DirectPartyBookingDO)mnpBookingDOList.get(0)).getOfficeID();
				}else{
					bookingBranchOffice=new OfficeDO();
				}
				bookingBranchOffice.setOfficeId(to
						.getBookingBranchofficeID());
				directPartyBookingDO.setBookingBranchDetails(to.getBookingBranchDetails());
				directPartyBookingDO.setOfficeID(bookingBranchOffice);
			}


			/* Fopr Mnp items Ends */
			directPartyBookingDO.setConsgmntStatus(BookingConstants.BOOKED);
			directPartyBookingDO.setWeighingType(to.getCaptureWeightType());
			directPartyBookingDO.setActualWeight(to.getActualWeight());
			// Create new consignment for Nondoxpaperwrk items and store the
			// items in BookingItemList against the booking id
			DirectPartyBookingDO paperWrkItemDO = null;
			if (to != null && !StringUtil.isEmpty(to.getNoOfPaperWrkItems())) {
				paperWrkItemDO = (DirectPartyBookingDO) getPaperWorkBookingDO(
						to, BookingConstants.DIRECT_PARTY_BOOKING_TYPE);
				paperWrkItemDO.setDiFlag(BookingConstants.DIFLAG);
				paperWrkItemDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
				bookingDoList.add(paperWrkItemDO);
			}

			try{
				// for setting Child CN data popup data
				if (to != null
						&& to.getChildCNDetailsHidden() != null
						&& !StringUtil.isEmpty(to.getChildCNDetailsHidden())) {
					getSetOfChildConsignments(to.getChildCNDetailsHidden(),
							to.getConsignmentNumber());
					itr = parentChildConsignmentDOList.iterator();
					while (itr.hasNext()) {
						BookingDO bookingDO1 = (BookingDO) itr.next();
						bookingDO1.setDiFlag(BookingConstants.DIFLAG);
						bookingDO1.setReadByLocal(BookingConstants.READ_BY_LOCAL);
						if (!bookingDoList.isEmpty() && bookingDoList.size() != 0) {
							bookingDoList.add(bookingDO1);
						}

					}

				}}catch(Exception e){
					logger.error("MnpBookingMDBServiceImpl::convertTotoDO::Exception occured:"
							+e.getMessage());
				}



				bookingDoList.add(directPartyBookingDO);



		}

		return bookingDoList;

	}

	/**
	 * Adds a new consignment for paper wrk items.
	 *
	 * @param bookingTO the booking to
	 * @param type the type
	 * @return the list the of child consignments
	 */
	private BookingDO getPaperWorkBookingDO(MnpBookingTO bookingTO, String type) {

		BookingDO bookingDO = null;

		if (type.equals(BookingConstants.FRANCHISEE_BOOKING_TYPE)) {
			bookingDO = new FranchiseeBookingDO();
		} else if (type.equals(BookingConstants.DIRECT_PARTY_BOOKING_TYPE)) {
			bookingDO = new DirectPartyBookingDO();
		} else if (type.equals(BookingConstants.CASH_BOOKING_TYPE)) {
			bookingDO = new CashBookingDO();
		}
		if(bookingTO.getPaperWrkBookingId() != null &&
				bookingTO.getPaperWrkBookingId() != 0){
			bookingDO.setBookingId(bookingTO.getPaperWrkBookingId());
		}
		bookingDO.setConsignmentNumber(bookingTO.getPaperworkCN());
		bookingDO.setParentCnNumber(bookingTO.getConsignmentNumber());
		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = mnpBookingDAO.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = mnpBookingDAO.getIdForDoxType();
			documentDo.setDocumentId(doxTypeId);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setDocumentID(documentDo);
			bookingDO.setAmount(new Double(0));
			bookingDO.setFinalWeight(Double.valueOf(bookingTO.getnDoxWeight()));
			if (!StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
				bookingDO.setNoOfPieces(Integer.parseInt(bookingTO
						.getNoOfPaperWrkItems()));
			}
			bookingDO.setBookingDate(bookingTO.getBookingDate());

			List<BookingItemListDO> bookingItemsList = mnpBookingDAO
			.getBookingItemList(bookingTO.getPaperworkCN());
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
			logger.error("MnpBookingMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;
	}

	/**
	 * Gets the mnp booking dao.
	 * 
	 * @return the mnpBookingDAO
	 */
	public MnpBookingMDBDAO getMnpBookingDAO() {
		return mnpBookingDAO;
	}

	/**
	 * Sets the mnp booking dao.
	 * 
	 * @param mnpBookingDAO
	 *            the mnpBookingDAO to set
	 */
	public void setMnpBookingDAO(MnpBookingMDBDAO mnpBookingDAO) {
		this.mnpBookingDAO = mnpBookingDAO;
	}

	/**
	 * Gets the sets the of child consignments.
	 *
	 * @param childCNData the child cn data
	 * @param parentConsignment the parent consignment
	 * @return the sets the of child consignments
	 * @throws CGBusinessException the cG business exception
	 */
	public List<BookingDO> getSetOfChildConsignments(String childCNData,
			String parentConsignment)  throws CGBusinessException{
		List<String> slNoList = new ArrayList<String>();
		List<String> childCNNumberList = new ArrayList<String>();
		List<String> actualWeightList = new ArrayList<String>();
		List<String> lengthList = new ArrayList<String>();
		List<String> widthList = new ArrayList<String>();
		List<String> heightList = new ArrayList<String>();
		List<String> volWeightList = new ArrayList<String>();
		List<String> finalWeightList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();

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
		while (!StringUtil.isEmpty(st.toString()) && st.hasMoreTokens()) {
			descList.add(st.nextToken());
		}

		for (int i = 0; i < slNoList.size(); i++) {
			BookingDO bookingDO = new BookingDO();
			bookingDO.setParentCnNumber(parentConsignment);
			bookingDO.setConsignmentNumber(childCNNumberList.get(i));

			if (actualWeightList.size() > 0 && !StringUtil.isEmpty(actualWeightList.get(i))){
				bookingDO.setActualWeight(Double.parseDouble(actualWeightList
						.get(i)));
			}else{
				bookingDO.setActualWeight(new Double(0.0));
			}
			if (!StringUtil.isEmpty(lengthList.get(i))){
				bookingDO.setLength(Float.parseFloat(lengthList.get(i)));
			}
			if (!StringUtil.isEmpty(widthList.get(i))){
				bookingDO.setBreadth(Float.parseFloat(widthList.get(i)));
			}
			if (!StringUtil.isEmpty(heightList.get(i))){
				bookingDO.setHeight(Float.parseFloat(heightList.get(i)));
			}
			if (!StringUtil.isEmpty(volWeightList.get(i))){
				bookingDO
				.setVolumetricWght(Double.parseDouble(volWeightList.get(i)));
			}
			if (!StringUtil.isEmpty(finalWeightList.get(i))){
				bookingDO
				.setFinalWeight(Double.parseDouble(finalWeightList.get(i)));
			}
			if (!descList.isEmpty() && descList.size() > 0 && 
					i < descList.size() && !StringUtil.isEmpty(descList.get(i))){
				if(descList.get(i).equalsIgnoreCase(BookingConstants.NOT_ATTEMPTED)){
					bookingDO.setDescription(BookingConstants.EMPTY );
				}else{
					bookingDO.setDescription(descList.get(i));	
				}
			}
			parentChildConsignmentDOList.add(bookingDO);

		}
		return parentChildConsignmentDOList;

	}


}
