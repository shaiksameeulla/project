/*
 * @author soagarwa */
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

import src.com.dtdc.mdbDao.booking.CashBookingMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.cashbooking.CashBookingDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.ConsignerAddressDO;
import com.dtdc.domain.master.customer.ConsignerInfoDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.to.booking.BookingTO;
import com.dtdc.to.booking.cashbooking.CashBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CashBookingServiceImpl.
 */
public class CashBookingMDBServiceImpl implements CashBookingMDBService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(CashBookingMDBServiceImpl.class);

	/** The cash booking dao. */
	private CashBookingMDBDAO cashBookingMDBDAO;

	/** The parent child consignment do list. */
	private List<CashBookingDO> parentChildConsignmentDOList = new ArrayList<CashBookingDO>();

	/* (non-Javadoc)
	 * @see com.dtdc.ng.bs.booking.cashbooking.CashBookingService#saveCashBookingDetails(com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	public boolean saveCashBookingDetails(CGBaseTO bookingTO)throws CGBusinessException {
		LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on CGBaseTO start========>");
		CashBookingTO cashBookingTO = (CashBookingTO) bookingTO.getBaseList()
		.get(0);
		LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on CGBaseTO:cashBookingTO========>" + cashBookingTO);
		return saveCashBookingDetails(cashBookingTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dtdc.ng.bs.franchisebooking.FranchiseeBookingService#saveCNBookingDetails
	 * (java.util.List)
	 */

	@Override
	public boolean saveCashBookingDetails(CashBookingTO cashBookingTO)throws CGBusinessException {
		LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on CashBookingTO========>" + cashBookingTO);
		List<CashBookingDO> cashBookingDOList = null;
		parentChildConsignmentDOList.clear();
		boolean bookingStatus = false;
		new CashBookingDO();
		//		String channelType = cashBookingTO.getChannelType();
		String channelType = cashBookingTO.getCashBookingType();
		LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on channelType========>" + channelType);
		/*String[] tokens = channelType
				.split(ApplicationConstants.CHARACTER_HASH);*/
		LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on channelType tokens========>" + channelType);
		if (channelType!=null && !channelType.isEmpty()) {
			//			String tokenType = tokens[1];
			LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on channelType tokens:tokenType:========>" + channelType);
			if(channelType.equals(BookingConstants.CASH_BOOKING_CC_TYPE)|| channelType.equals(BookingConstants.CASH_BOOKING_PB_TYPE) ||channelType.equals(BookingConstants.CASH_BOOKING_WK_TYPE)) {
				cashBookingDOList = new ArrayList<CashBookingDO>();
				cashBookingDOList = convertTotoDO(cashBookingTO);
			} else {
				LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::token is not a cash booking type========>");
			}
		} else {
			LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on CashBookingTO::tokens is null or empty========>");
		}
		if (cashBookingDOList != null && !cashBookingDOList.isEmpty()
				&& cashBookingDOList.size() != 0) {
			bookingStatus = cashBookingMDBDAO
			.saveAndDeleteCashBookingDetails(cashBookingDOList);
		} else {
			LOGGER.info("CashBookingMDBServiceImpl::saveCashBookingDetails::on CashBookingTO cashBookingDOList is null or empty========>");
		}
		return bookingStatus;
	}

	/**
	 * Convert toto do.
	 *
	 * @param cashTO the cash to
	 * @return the list
	 */
	public List<CashBookingDO> convertTotoDO(CashBookingTO cashTO) {

		List<CashBookingDO> bookingDOList = new ArrayList<CashBookingDO>();
		List cashBookingDOList = null;
		Iterator itr = null;

		if (cashTO != null) {			
			String[] cnNo = cashTO.getCnNumberArray();
			LOGGER.debug("CashBookingMDBServiceImpl::convertTotoDO::cnNo::" + cnNo);
			if (cnNo == null || cnNo.length == 0) {
				LOGGER.debug("CashBookingMDBServiceImpl::convertTotoDO::cnNo length is::" + cnNo.length);
				if(cashTO.getConsignmentNumber() != null && !cashTO.getConsignmentNumber().trim().equals("")){

					//if(cashTO.getConsignmentNumber() != null && !cashTO.getConsignmentNumber().trim().equals("")){
					try {
						cashBookingDOList=cashBookingMDBDAO.getCashBookingEnquiryDetails(cashTO.getConsignmentNumber());
					} catch (CGBusinessException e) {
						LOGGER.error("CashBookingMDBServiceImpl::convertTotoDO::CGBusinessException::" + e.getMessage());
					}
					//}
					CashBookingDO cashBookingDO=null;
					if(cashBookingDOList!=null && cashBookingDOList.size()>0){
						cashBookingDO=(CashBookingDO)cashBookingDOList.get(0);

						LOGGER.debug("For CNNO:"+cashTO.getConsignmentNumber()+"Office Id From TO"+cashTO.getOfficeID().intValue()+"Office Id From DO"+ cashBookingDO.getOfficeID().getOfficeId().intValue());
						//bookingTO.getBookingDate() == bookingDO.getBookingDate()
						if(cashTO.getOfficeID().intValue() == cashBookingDO.getOfficeID().getOfficeId().intValue()){
							//Record found central with same branch and other details so it's genuine update
							LOGGER.debug("Record found central with same branch and other details so it's genuine update");	
						}else{
							LOGGER.debug("Record found central with Different branch and other details so it's Duplicate");
							// bookingDO=new BookingDuplicateDO();	

						}

					}else{
						cashBookingDO = new CashBookingDO();
						LOGGER.debug("New Record Insertion");
					}



					// save booking branch off id
					//save common fields  for grid and single cn 
					if(cashTO.getUserId() != null){
						cashBookingDO.setUserId(cashTO.getUserId());
					}
					cashBookingDO.setSecurityPouchNo(cashTO.getSecurityPouchNo());
					if (cashTO.getBookingBranchofficeID() != null && cashTO.getBookingBranchofficeID()!= 0) {
						OfficeDO bookingBranchOffice = new OfficeDO();
						bookingBranchOffice.setOfficeId(cashTO.getBookingBranchofficeID());
						cashBookingDO.setOfficeID(bookingBranchOffice);
					}
					/*if(cashTO.getBookingId()!=null && cashTO.getBookingId()!=0){
					//cashBookingDO.setBookingId(cashTO.getBookingId());
				}
				else */if(cashTO.getBookingId() == 0 || cashTO.getBookingId() == null){
					cashBookingDO.setServerDate(cashTO.getBookingDate());
				}
				if(cashTO.getCccId()!=null && cashTO.getCccId()!=0){
					OfficeDO cashCollectionCenter=new OfficeDO();
					cashCollectionCenter.setOfficeId(cashTO.getCccId());
					cashBookingDO.setCashCollectionCenter(cashCollectionCenter);
				}

				EmployeeDO employeeDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					employeeDO=((CashBookingDO)cashBookingDOList.get(0)).getEmployeeId();
				}else{
					employeeDO = new EmployeeDO();
				}

				if (cashTO.getEmployeeId() != null && cashTO.getEmployeeId() != 0) {
					employeeDO.setEmployeeId(cashTO.getEmployeeId());
					cashBookingDO.setEmployeeId(employeeDO);
				}

				EmployeeDO empDO = new EmployeeDO();

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					empDO=((CashBookingDO)cashBookingDOList.get(0)).getEmployeeIdForPickupBoyId();
				}else{
					empDO = new EmployeeDO();
				}

				if (cashTO.getPickupBoyEmployeeIdforCash() != null && cashTO.getPickupBoyEmployeeIdforCash() != 0) {
					empDO.setEmployeeId(cashTO.getPickupBoyEmployeeIdforCash());
					cashBookingDO.setEmployeeIdForPickupBoyId(empDO);
				}


				// populating foreign key objects
				if (!StringUtil.isEmpty(cashTO.getBookingZone())) {
					cashBookingDO.setBookingDivisionID(cashTO.getBookingZone());
				}

				ChannelDO channelDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					channelDO=((CashBookingDO)cashBookingDOList.get(0)).getChannelTypeID();
				}else{
					channelDO = new ChannelDO();
				}				

				if (!StringUtil.isEmpty(cashTO.getChannelType())) {
					String channelType = cashTO.getChannelType();
					/*String[] tokens = channelType
							.split(ApplicationConstants.CHARACTER_HASH);*/
					if ( !(StringUtil.isEmpty(channelType.trim()))) {
						channelDO.setChannelTypeId(Integer.parseInt(channelType));
						cashBookingDO.setChannelTypeID(channelDO);
					}
				}
				// TODO need to be revisited
				CustomerDO customerDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					customerDO=((CashBookingDO)cashBookingDOList.get(0)).getCustomerId();
				}else{
					customerDO = new CustomerDO();
				}

				if (cashTO.getCustomerId() != null && cashTO.getCustomerId() != 0) {
					customerDO.setCustomerId(cashTO.getCustomerId());
					cashBookingDO.setCustomerId(customerDO);
				}

				/*Set consignerAddress = new HashSet();

				if (cashTO.getAreaIdForConsigner() != null
						&& cashTO.getAreaIdForConsigner() != 0) {
					CustAddressDO customerAddressDO = new CustAddressDO();
					AreaDO areaDO = new AreaDO();
					areaDO.setAreaId(cashTO
							.getAreaIdForConsigner());
					customerAddressDO.setArea(areaDO);
					customerAddressDO.setStreet1(cashTO.getConsignorAddress1());
					customerAddressDO.setStreet2(cashTO.getConsignorAddress2());
					customerAddressDO.setStreet3(cashTO.getConsignorAddress3());

					consignerAddress.add(customerAddressDO);
				}

				// TODO need to be revisited
				CustomerDO customerDO = new CustomerDO();
				if (cashTO.getCustomerId() != null && cashTO.getCustomerId() != 0) {
//					customerDO.setCustomerId(cashTO.getCustomerId());
					customerDO.setFirstName(cashTO.getConsignorName());
					customerDO.setAddresses(consignerAddress);
					customerDO.setPhone(cashTO.getConsignorPhone().toString());
					customerDO.setEmail(cashTO.getConsignorEmail());
					cashBookingDO.setCustomerId(customerDO);
				}*/



				PincodeDO pincodeDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					pincodeDO=((CashBookingDO)cashBookingDOList.get(0)).getPincodeDO();
				}else{
					pincodeDO = new PincodeDO();
				}

				if (cashTO.getPincodeId() != null && cashTO.getPincodeId() != 0) {
					pincodeDO.setPincodeId(cashTO.getPincodeId());
					cashBookingDO.setPincodeDO(pincodeDO);

				}

				CityDO cityDO= null;
				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					cityDO=((CashBookingDO)cashBookingDOList.get(0)).getCityDO();
				}else{
					cityDO = new CityDO();
				}

				if (cashTO.getCityId() != null && cashTO.getCityId() != 0) {
					cityDO.setCityId(cashTO.getCityId());
					cashBookingDO.setCityDO(cityDO);
				}

				ServiceDO serviceDO = null;
				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					serviceDO=((CashBookingDO)cashBookingDOList.get(0)).getServiceID();
				}else{
					serviceDO = new ServiceDO();
				}

				if (!StringUtil.isEmpty(cashTO.getServiceType())) {
					String serviceType = cashTO.getServiceType();
					String[] serviceId = serviceType
					.split(ApplicationConstants.CHARACTER_HASH);
					if (!StringUtil.isEmpty(serviceId[0])) {
						serviceDO.setServiceId(Integer
								.parseInt(serviceId[0]));
						cashBookingDO.setServiceID(serviceDO);
					}					
				}

				CommodityDO commodityDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					commodityDO=((CashBookingDO)cashBookingDOList.get(0)).getDomesticCommodityId();
				}else{
					commodityDO = new CommodityDO();
				}

				if (!StringUtil.isEmpty(cashTO.getContentsIdAndCode())) {
					String commodity = cashTO.getContentsIdAndCode();
					String[] commodityId = commodity
					.split(ApplicationConstants.CHARACTER_HASH);
					if (!StringUtil.isEmpty(commodityId[0])) {
						commodityDO.setCommodityId(Integer
								.parseInt(commodityId[0]));
						cashBookingDO.setDomesticCommodityId(commodityDO);
					}					
				}


				DocumentDO documentDo = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					documentDo=((CashBookingDO)cashBookingDOList.get(0)).getDocumentID();
				}else{
					documentDo = new DocumentDO();
				}

				if (!StringUtil.isEmpty(cashTO.getDoxTypeForCash())) {
					String doxType = cashTO.getDoxTypeForCash();
					String[] documentId = doxType
					.split(ApplicationConstants.CHARACTER_HASH);
					documentDo.setDocumentId(Integer.parseInt(documentId[0]));
					cashBookingDO.setDocumentID(documentDo);
				}

				String prodIdAndCode=cashTO.getProductTypeForCash();
				if (prodIdAndCode!=null && !StringUtil.isEmpty(prodIdAndCode) && !prodIdAndCode.equals(BookingConstants.ALL)) {
					ProductDO productDo = new ProductDO();
					String[] productId = prodIdAndCode
					.split(ApplicationConstants.CHARACTER_HASH);
					if (productId[0] != null
							&& !StringUtils.isEmpty(productId[0])) {
						productDo.setProductId(Integer.parseInt(productId[0]));
						cashBookingDO.setProductID(productDo);
					}
				}

				String vasProdCode=cashTO.getProductCodeForCash();
				if(vasProdCode!=null && !StringUtil.isEmpty(vasProdCode)){
					vasProdCode=vasProdCode.substring(vasProdCode.indexOf(BookingConstants.SLASH)+1);
					cashBookingDO.setVasProductCode(vasProdCode);
				}

				// populating Header information

				cashBookingDO.setSelectScheme(cashTO.getSelectScheme());
				cashBookingDO.setNoOfPieces(cashTO.getNoOfPieces());
				cashBookingDO.setCreateChildCn(cashTO.getCreateChildCn());
				cashBookingDO.setInvoiceValue(cashTO.getValue());
				cashBookingDO.setConsignmentNumber(cashTO.getConsignmentNumber());
				cashBookingDO.setExpectedDlvDate(cashTO.getEdd());
				cashBookingDO.setAmount(cashTO.getAmount());
				cashBookingDO.setDbServer(cashTO.getDbServer());
				cashBookingDO.setBookingDate(cashTO.getBookingDate());
				/*
				 * cashBookingDO.setCashServiceTax(cashTO.getCashServiceTax());
				 * cashBookingDO.setCashCustomerType(cashTO.getCashCustomerType());
				 */
				cashBookingDO.setPaid(cashTO.getCashPaid());	
				cashBookingDO.setPaymentMode(cashTO.getPaymentMode());
				//				cashBookingDO.setCustomerCode(cashTO.getCashCode());
				cashBookingDO.setRemarks(cashTO.getCashRemarks());
				cashBookingDO.setWeighingType(cashTO.getCaptureWeightType());
				cashBookingDO.setLength(cashTO.getLengthInCms());
				cashBookingDO.setBreadth(cashTO.getBreadthInCms());
				cashBookingDO.setHeight(cashTO.getHeightInCms());
				cashBookingDO.setActualWeight(cashTO.getActualWeight());
				cashBookingDO.setVolumetricWght(cashTO.getVolumtericWeight());
				cashBookingDO.setFinalWeight(cashTO.getFinalWeight());
				cashBookingDO.setIsInsured(cashTO.getIsInsured());
				cashBookingDO.setInsuredBy(cashTO.getInsuredBy());
				// for setting values in ConsigneeAddressDO
				/*ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();
				consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1());
				consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2());
				consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3());
				GeographyDO geographyConsigneeDO = new GeographyDO();
				if (cashTO.getGeographyIdForConsignee() != null
						&& cashTO.getGeographyIdForConsignee() != 0) {
					geographyConsigneeDO.setGeographyId(cashTO
							.getGeographyIdForConsignee());
					consigneeAddressDO.setGeography(geographyConsigneeDO);
				}*/

				//--Set address = new HashSet();

				Set address = null;
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;

				if (cashTO.getAreaIdForConsignee() != null
						&& cashTO.getAreaIdForConsignee() != 0) {




					/*--ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();
					AreaDO areaDO = new AreaDO();
					areaDO.setAreaId(cashTO.getAreaIdForConsignee());
					//areaDO.setAreaId(cashTO.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
					consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1());
					consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2());
					consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3());
					if(cashTO.getConsigneeAddressId() != null && cashTO.getConsigneeAddressId() != 0){
						consigneeAddressDO.setConsgAddrId(cashTO.getConsigneeAddressId());
					}
					try{
					address.add(consigneeAddressDO);
					}catch(Exception e){
						//e#prntstcktrce
					}
					 */


					if(cashBookingDOList!=null && cashBookingDOList.size()>0){
						address = new HashSet();
						Iterator iterator=(((CashBookingDO)cashBookingDOList.get(0)).getConsigneeID().getAddresses()).iterator();
						consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
						areaDO=consigneeAddressDO.getAreaDO();
						areaDO.setAreaId(cashTO.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3());
						address.add(consigneeAddressDO);


					}else{
						consigneeAddressDO=new ConsigneeAddressDO();
						areaDO= new AreaDO();
						address = new HashSet();
						areaDO.setAreaId(cashTO.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3());
						if(cashTO.getConsigneeAddressId() != null && cashTO.getConsigneeAddressId() != 0){
							consigneeAddressDO.setConsgAddrId(cashTO.getConsigneeAddressId());
						}
						address.add(consigneeAddressDO);
					}


				}

				// for setting values in ConsigneeDO
				ConsigneeDO consigneeDO = null;			

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					consigneeDO=((CashBookingDO)cashBookingDOList.get(0)).getConsigneeID();
				}else{
					consigneeDO = new ConsigneeDO();
				}

				/*if(cashTO.getConsigneeInfoId() != null && cashTO.getConsigneeInfoId() != 0){
					consigneeDO.setConsigneeId(cashTO.getConsigneeInfoId());
				}*/
				consigneeDO.setFirstName(cashTO.getConsigneeName());
				consigneeDO.setEmail(cashTO.getConsigneeEmail());
				if (cashTO.getConsigneePhone() != null) {
					consigneeDO.setPhone(cashTO.getConsigneePhone().toString());
				}

				consigneeDO.setAddresses(address);
				cashBookingDO.setConsigneeID(consigneeDO);

				/* Set the consignor Info */

				Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
				// create set of consignee address and set that to the consigneeDO
				// adresses.

				ConsignerAddressDO consignorAddressDO =null;
				PincodeDO consignorPincodeDO=null;
				CityDO consignorCityDO =null;

				if (cashTO.getConsignorPincodeId() != null
						&& cashTO.getConsignorPincodeId() > 0) {


					if(cashBookingDOList!=null && cashBookingDOList.size()>0){
						Iterator iterator=((CashBookingDO)cashBookingDOList.get(0)).getConsignorID().getConsignorAddresses().iterator();
						consignorAddressDO=(ConsignerAddressDO)iterator.next();

						consignorPincodeDO = consignorAddressDO.getPincodeId();
						consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorCityDO = consignorAddressDO.getCity();
						consignorCityDO.setCityId(cashTO.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);

						consignorAddressDO.setStreet1(cashTO.getConsignorAddress1());
						consignorAddressDO.setStreet2(cashTO.getConsignorAddress2());
						consignorAddressDO.setStreet3(cashTO.getConsignorAddress3());

					}else{
						consignorAddressDO= new ConsignerAddressDO();
						consignorPincodeDO = new PincodeDO();
						consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorAddressDO.setStreet1(cashTO.getConsignorAddress1());
						consignorAddressDO.setStreet2(cashTO.getConsignorAddress2());
						consignorAddressDO.setStreet3(cashTO.getConsignorAddress3());

						if (cashTO.getConsignerCityId() != null
								&& cashTO.getConsignerCityId() > 0) {
							consignorCityDO = new CityDO();
							consignorCityDO.setCityId(cashTO.getConsignerCityId());
							consignorAddressDO.setCity(consignorCityDO);
						}

					}



					/*--ConsignerAddressDO consignorAddressDO = new ConsignerAddressDO();
					PincodeDO consignorPincodeDO = new PincodeDO();
					consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);
					if(cashTO.getConsignorCity() != null){
						CityDO cityDo = new CityDO();
						cityDo.setCityId(cashTO.getConsignerCityId());
						consignorAddressDO.setCity(cityDo);
					}
					if (cashTO.getAreaIdForConsigner() != null
							&& cashTO.getAreaIdForConsigner() != 0) {
						AreaDO areaDO = new AreaDO();
						areaDO.setAreaId(cashTO.getAreaIdForConsigner());
						consignorAddressDO.setAreaDO(areaDO);
					}
					consignorAddressDO.setStreet1(cashTO.getConsignorAddress1());
					consignorAddressDO.setStreet2(cashTO.getConsignorAddress2());
					consignorAddressDO.setStreet3(cashTO.getConsignorAddress3());
					if (cashTO.getConsigrAddrId() != null && cashTO.getConsigrAddrId() != 0) {
						consignorAddressDO.setConsigrAddrId(cashTO.getConsigrAddrId());
					}*/
					consignorAddress.add(consignorAddressDO);
				}

				ConsignerInfoDO consignorDO = null;

				if(cashBookingDOList!=null && cashBookingDOList.size()>0){
					consignorDO=((CashBookingDO)cashBookingDOList.get(0)).getConsignorID();
				}else{
					consignorDO = new ConsignerInfoDO();
				}


				/*--if(cashTO.getConsignerInfoId() != null && cashTO.getConsignerInfoId() != 0){
					consignorDO.setConsignerId(cashTO.getConsignerInfoId());
				}*/
				consignorDO.setFirstName(cashTO.getConsignorName());
				consignorDO.setEmail(cashTO.getConsignorEmail());
				if (cashTO.getConsignorPhone() != null) {
					consignorDO.setPhone(cashTO.getConsignorPhone().toString());
				}
				consignorDO.setConsignorAddresses(consignorAddress);
				cashBookingDO.setConsignorID(consignorDO);


				/*
				TAXABLE_AMOUNT ----Sub total
				TAX_PAYABLE ---service tax
				DISCOUNT  ---Discount Amount
				DISCOUNT_IN_PERC ---Discount Percent*/

				cashBookingDO.setTaxableAmount(cashTO.getSubTotalFreight());
				cashBookingDO.setTaxPayable(cashTO.getServiceTaxAmount());
				cashBookingDO.setDiscount(cashTO.getDiscountAmount());
				cashBookingDO.setFrieght(cashTO.getFreight());
				cashBookingDO.setRiskSurchgAmt(cashTO.getRiskSurchargeAmount());
				cashBookingDO.setDiscountInPerc(cashTO.getDisPlayDiscount());
				cashBookingDO.setServiceTax(cashTO.getDisPlayServiceTax());
				cashBookingDO.setRiskSurchgAmt(cashTO.getRiskSurchargeAmount());
				cashBookingDO.setServChrgAmt(cashTO.getServiceChargeAmount());
				cashBookingDO.setAmount(cashTO.getGrandTotal());
				//Rate calculation components
				cashBookingDO.setTsAmount(cashTO.getFreight());
				cashBookingDO.setMiscCharge(cashTO.getTotalMiscExpense());
				cashBookingDO.setDiplomaticOrRemoteAreaCharge(cashTO.getDiplomaticOrRemoteAreaCharge());

				//cashBookingDO.setRemarks(cashTO.getCashBookingRemarks());
				// for setting remote delivery popup data
				cashBookingDO.setAreaNameLtDox(cashTO.getAreaName());
				cashBookingDO.setDoxAmount(new Double(cashTO
						.getRemoteDeliveryDoxAmount()));
				cashBookingDO.setNonDoxAmount(new Double(cashTO
						.getRemoteDeliveryNonDoxAmount()));
				cashBookingDO.setRemoteDeliveryRemarks(cashTO
						.getRemoteDeliveryRemarks());
				cashBookingDO.setExtrDlvCharges(cashTO.getExtraDeliveryCharges());
				cashBookingDO.setExtDlvRemarks(cashTO.getRemoteDeliveryRemark1());
				// for setting passport popup data
				cashBookingDO.setPassportDetails(cashTO.getPassportDetails());
				cashBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

				SimpleDateFormat sdfDate = new SimpleDateFormat(
						BookingConstants.TIME_FORMAT_HHMMSS);
				Date now = new Date();
				String strDate = sdfDate.format(now);

				cashBookingDO.setBookingTime(strDate);
				//store staff id as employee in booking and set is_staff field to value 'Y' is staff is entered
				//on screen
				if(cashTO.getStaffId()!=null && cashTO.getStaffId()!=0){
					EmployeeDO staff=new EmployeeDO();
					staff.setEmployeeId(cashTO.getStaffId());
					cashBookingDO.setEmployeeId(staff);
					cashBookingDO.setIsStaff(BookingConstants.CONSTANT_Y);
				}else{
					cashBookingDO.setIsStaff(BookingConstants.CONSTANT_N);
				}
				//save new fields added in cas
				cashBookingDO.setPrioritySticker(cashTO.getPrioritySticker());
				cashBookingDO.setPickUpTime(cashTO.getPickupTime());
				cashBookingDO.setPaymentMode(cashTO.getPaymentMode());
				cashBookingDO.setCustomerIndustry(cashTO.getCustomerIndustry());
				cashBookingDO.setCustRefNo(cashTO.getReferenceNumber());


				//store customer id for DP Code if customer id is not already populated
				//customer id can be populated for DP Code or Loyal customer but only one of these two wud be entered
				//so customerId can either DP Code or Loyal Customer

				if(cashTO.getDpCode()!=null && !StringUtil.isEmpty(cashTO.getDpCode()) && cashBookingDO.getCustomerId()==null){
					CustomerDO dpCustomer = null;

					if(cashBookingDOList!=null && cashBookingDOList.size()>0){
						dpCustomer=((CashBookingDO)cashBookingDOList.get(0)).getCustomerId();
					}else{
						dpCustomer = new CustomerDO();
					}

					dpCustomer.setCustomerId(cashTO.getCustomerId());
					cashBookingDO.setCustomerId(dpCustomer);
				}
				cashBookingDO.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				cashBookingDO.setNodeId(cashTO.getNodeId());
				cashBookingDO.setDiFlag(BookingConstants.DIFLAG);
				cashBookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
				LOGGER.debug("CashBooking.DIFLAG"+cashBookingDO.getDiFlag());
				LOGGER.debug("CashBooking.NodeID"+cashBookingDO.getNodeId());

				bookingDOList.add(cashBookingDO);

				// Create new consignment for Nondoxpaperwrk items and store the
				// items in BookingItemList against the booking id
				CashBookingDO paperWrkItemDO = null;
				if (cashTO != null
						&& !StringUtil.isEmpty(cashTO.getNoOfPaperWrkItems())) {
					paperWrkItemDO = getPaperWorkBookingDO(cashTO);
					paperWrkItemDO.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
					paperWrkItemDO.setServerDate(cashTO.getBookingDate());
					paperWrkItemDO.setNodeId(cashTO.getNodeId());
					paperWrkItemDO.setDiFlag(BookingConstants.DIFLAG);
					paperWrkItemDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
					LOGGER.debug("paperWrkItemDO CashBooking.DIFLAG"+paperWrkItemDO.getDiFlag());
					LOGGER.debug("paperWrkItemDO CashBooking.NodeID"+paperWrkItemDO.getNodeId());
					bookingDOList.add(paperWrkItemDO);
				}
				// for setting Child CN data popup data
				if (cashTO != null
						&& cashTO.getChildCNDetailsHidden() != null
						&& !StringUtil.isEmpty(cashTO.getChildCNDetailsHidden())) {
					getSetOfChildConsignments(cashTO.getChildCNDetailsHidden(),
							cashTO.getConsignmentNumber());
					itr = parentChildConsignmentDOList.iterator();
					while (itr.hasNext()) {
						CashBookingDO cashBookingDO1 = (CashBookingDO) itr.next();
						if (!bookingDOList.isEmpty() && bookingDOList.size() != 0) {
							cashBookingDO1.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
							cashBookingDO1.setServerDate(cashTO.getBookingDate());
							cashBookingDO1.setNodeId(cashTO.getNodeId());
							cashBookingDO1.setDiFlag(BookingConstants.DIFLAG);
							cashBookingDO1.setReadByLocal(BookingConstants.READ_BY_LOCAL);
							LOGGER.debug("ChilDCN CashBooking.DIFLAG"+cashBookingDO1.getDiFlag());
							LOGGER.debug("CHILDCN CashBooking.NodeID"+cashBookingDO1.getNodeId());
							bookingDOList.add(cashBookingDO1);
						}

					}

				}
				}

			}else{
				//				noofRows = cashTO.getNoOfRows();
				for(int cnt=0;cnt<cnNo.length;cnt++){
					if(cashTO.getCnNumberArray()[cnt] != null && !cashTO.getCnNumberArray()[cnt].trim().equals("")){

						try {
							cashBookingDOList=cashBookingMDBDAO.getCashBookingEnquiryDetails(cashTO.getCnNumberArray()[cnt]);
						} catch (CGBusinessException e) {
							LOGGER.error("CashBookingMDBServiceImpl::convertTotoDO::Exception occured:"
									+e.getMessage());

						}

						CashBookingDO cashBookingDO=null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							cashBookingDO=(CashBookingDO)cashBookingDOList.get(cnt);
						}else{
							cashBookingDO = new CashBookingDO();
						}

						// save booking branch off id
						//save common fields  for grid and single cn 
						if (cashTO.getBookingBranchofficeID() != null && cashTO.getBookingBranchofficeID()!= 0) {
							OfficeDO bookingBranchOffice = new OfficeDO();
							bookingBranchOffice.setOfficeId(cashTO.getBookingBranchofficeID());
							cashBookingDO.setOfficeID(bookingBranchOffice);
						}
						if(cashTO.getUserId() != null){
							cashBookingDO.setUserId(cashTO.getUserId());
						}
						/*if(cashTO.getBookingId()!=null && cashTO.getBookingId()!=0){
							//cashBookingDO.setBookingId(cashTO.getBookingId());
						}
						else */if(cashTO.getBookingId() == 0 || cashTO.getBookingId() == null){
							cashBookingDO.setServerDate(cashTO.getBookingDate());
						}
						if(cashTO.getCccId()!=null && cashTO.getCccId()!=0){
							OfficeDO cashCollectionCenter=null;
							if(cashBookingDOList!=null && cashBookingDOList.size()>0){
								cashCollectionCenter=((CashBookingDO)cashBookingDOList.get(cnt)).getCashCollectionCenter();
							}else{
								cashCollectionCenter = new OfficeDO();
							}

							cashCollectionCenter.setOfficeId(cashTO.getCccId());
							cashBookingDO.setCashCollectionCenter(cashCollectionCenter);
						}

						EmployeeDO employeeDO = null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0 && ((CashBookingDO)cashBookingDOList.get(cnt)).getEmployeeId() != null){
							employeeDO=((CashBookingDO)cashBookingDOList.get(cnt)).getEmployeeId();
						}else{
							employeeDO = new EmployeeDO();
						}

						if (cashTO.getEmployeeId() != null && cashTO.getEmployeeId() != 0) {
							employeeDO.setEmployeeId(cashTO.getEmployeeId());
							cashBookingDO.setEmployeeId(employeeDO);
						}

						EmployeeDO empDO = null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0 && ((CashBookingDO)cashBookingDOList.get(cnt)).getEmployeeIdForPickupBoyId() != null){
							empDO=((CashBookingDO)cashBookingDOList.get(cnt)).getEmployeeIdForPickupBoyId();
						}else{
							empDO = new EmployeeDO();
						}

						if (cashTO.getPickupBoyEmployeeIdforCash() != null && cashTO.getPickupBoyEmployeeIdforCash() != 0) {
							empDO.setEmployeeId(cashTO.getPickupBoyEmployeeIdforCash());
							cashBookingDO.setEmployeeIdForPickupBoyId(empDO);
						}
						// populating foreign key objects
						if (!StringUtil.isEmpty(cashTO.getBookingZone())) {
							cashBookingDO.setBookingDivisionID(cashTO.getBookingZone());
						}

						ChannelDO channelDO = null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							channelDO=((CashBookingDO)cashBookingDOList.get(cnt)).getChannelTypeID();
						}else{
							channelDO = new ChannelDO();
						}
						if (!StringUtil.isEmpty(cashTO.getChannelType())) {
							String channelType = cashTO.getChannelType();
							String[] tokens = channelType
							.split(ApplicationConstants.CHARACTER_HASH);
							if (tokens[0] != null
									&&  !(StringUtil.isEmpty(tokens[0].trim()))) {
								channelDO.setChannelTypeId(Integer.parseInt(tokens[0]));
								cashBookingDO.setChannelTypeID(channelDO);
							}
						}
						//save customer id for the loyal customer code entered in the cash booking grid
						if(cashTO.getCustomerIdArray() != null && cashTO.getCustomerIdArray().length > cnt
								&& cashTO.getCustomerIdArray()[cnt] != null && cashTO.getCustomerIdArray()[cnt] != 0){
							CustomerDO customerDO=new CustomerDO();
							customerDO.setCustomerId(cashTO.getCustomerIdArray()[cnt]);
							cashBookingDO.setCustomerId(customerDO);
						}
						if(cashTO.getActualWeightArray() != null && cashTO.getActualWeightArray().length > cnt
								&& cashTO.getActualWeightArray()[cnt] != null && cashTO.getActualWeightArray()[cnt] != 0){
							cashBookingDO.setActualWeight(cashTO.getActualWeightArray()[cnt]);
						}

						CityDO cityDO= null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							cityDO=((CashBookingDO)cashBookingDOList.get(cnt)).getCityDO();
						}else{
							cityDO = new CityDO();
						}
						if (cashTO.getCityIdArray() != null && cashTO.getCityIdArray().length > cnt) {
							cityDO.setCityId(cashTO.getCityIdArray()[cnt]);
							cashBookingDO.setCityDO(cityDO);
						}

						String prodIdAndCode=cashTO.getProductTypeForCash();
						if (prodIdAndCode!=null && !StringUtil.isEmpty(prodIdAndCode) && !prodIdAndCode.equals(BookingConstants.ALL)) {
							ProductDO productDo = new ProductDO();
							String[] productId = prodIdAndCode
							.split(ApplicationConstants.CHARACTER_HASH);
							if (productId[0] != null
									&& !StringUtils.isEmpty(productId[0])) {
								productDo.setProductId(Integer.parseInt(productId[0]));
								cashBookingDO.setProductID(productDo);
							}
						}

						cashBookingDO.setCashBookingManifestNo(cashTO.getCashBookingManifestNo());
						// populating Header information

						cashBookingDO.setDbServer(cashTO.getDbServer());
						cashBookingDO.setBookingDate(cashTO.getBookingDate());
						if(cashTO.getCnNumberArray() != null && cashTO.getCnNumberArray().length > cnt ){
							cashBookingDO.setConsignmentNumber(cashTO.getCnNumberArray()[cnt]);
						}

						if(cashTO.getRefNumberArray() != null && cashTO.getRefNumberArray().length > cnt
								&& cashTO.getRefNumberArray()[cnt] != null && cashTO.getRefNumberArray()[cnt] != 0){
							cashBookingDO.setReferenceNumber(cashTO.getRefNumberArray()[cnt]);
						}

						PincodeDO pincodeDO = null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							pincodeDO=((CashBookingDO)cashBookingDOList.get(cnt)).getPincodeDO();
						}else{
							pincodeDO = new PincodeDO();
						}
						if (cashTO.getZipCodeIdArray() != null && cashTO.getZipCodeIdArray().length >cnt 
								&& cashTO.getZipCodeIdArray()[cnt] != null && cashTO.getZipCodeIdArray()[cnt] != 0) {
							pincodeDO.setPincodeId(cashTO.getZipCodeIdArray()[cnt]);
							cashBookingDO.setPincodeDO(pincodeDO);
						}
						if(cashTO.getNoofPiecesArray() != null && cashTO.getNoofPiecesArray().length >cnt){
							cashBookingDO.setNoOfPieces(cashTO.getNoofPiecesArray()[cnt]);
						}

						DocumentDO documentDo = null;
						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							documentDo=((CashBookingDO)cashBookingDOList.get(cnt)).getDocumentID();
						}else{
							documentDo = new DocumentDO();
						}
						if (!StringUtil.isEmpty(cashTO.getDoxTypeArray()[cnt])) {
							String doxType = cashTO.getDoxTypeArray()[cnt];
							String[] documentId = doxType
							.split(ApplicationConstants.CHARACTER_HASH);
							documentDo.setDocumentId(Integer.parseInt(documentId[0]));
							cashBookingDO.setDocumentID(documentDo);
						}

						ServiceDO serviceDO = null;
						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							serviceDO=((CashBookingDO)cashBookingDOList.get(cnt)).getServiceID();
						}else{
							serviceDO = new ServiceDO();
						}
						if (cashTO.getServiceTypeArray()!= null && cashTO.getServiceTypeArray().length > cnt 
								&& !StringUtil.isEmpty(cashTO.getServiceTypeArray()[cnt])) {
							String serviceType = cashTO.getServiceTypeArray()[cnt];
							String[] serviceId = serviceType
							.split(ApplicationConstants.CHARACTER_HASH);
							if (!StringUtil.isEmpty(serviceId[0])) {
								serviceDO.setServiceId(Integer
										.parseInt(serviceId[0]));
								cashBookingDO.setServiceID(serviceDO);
							}					
						}
						if (cashTO.getProductCodeArray()!= null && cashTO.getProductCodeArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getProductCodeArray()[cnt])) {
							String vasProdCode=cashTO.getProductCodeArray()[cnt];
							if(vasProdCode!=null && !StringUtil.isEmpty(vasProdCode)){
								vasProdCode=vasProdCode.substring(vasProdCode.indexOf(BookingConstants.SLASH)+1);
								cashBookingDO.setVasProductCode(vasProdCode);
							}
						}

						//ItemDO itemDo = new ItemDO();
						if(cashTO.getActualContent()!= null && cashTO.getActualContent().length > cnt
								&& cashTO.getActualContent()[cnt] != null  ){


							String contentType = cashTO.getActualContent()[cnt];
							if( contentType!=null && (contentType
									.indexOf(ApplicationConstants.CHARACTER_HASH)!=-1)){
								contentType = contentType.substring(0, contentType
										.indexOf(ApplicationConstants.CHARACTER_HASH));
								Integer commodityId= Integer.parseInt(contentType);
								CommodityDO commodityDO = new CommodityDO();
								commodityDO.setCommodityId(commodityId);
								cashBookingDO.setDomesticCommodityId(commodityDO);

							}

						}

						cashBookingDO.setSelectScheme(cashTO.getSelectScheme());
						cashBookingDO.setCreateChildCn(cashTO.getCreateChildCn());
						if (cashTO.getInvoiceValueArray()!= null && cashTO.getInvoiceValueArray().length > cnt) {
							cashBookingDO.setInvoiceValue(cashTO.getInvoiceValueArray()[cnt]);
						}

						//TODO rework for grid 
						if (cashTO.getEddStringArray() != null && cashTO.getEddStringArray().length > cnt 
								&& !StringUtil.isEmpty(cashTO.getEddStringArray()[cnt])) {
							//						String dateString = DateFormatUtils.format(Long.parseLong(cashTO.getEddString()[cnt]), "dd/MM/yyyy");
							//						Date newDate = DateFormatterUtil
							//								.slashDelimitedstringToDDMMYYYYFormat(dateString);
							//cashTO.setEddStr(cashTO.getEddStringArray()[cnt]);
							if (!StringUtil.isEmpty(cashTO.getEddStr())) {
								cashBookingDO.setExpectedDlvDate(DateFormatterUtil
										.slashDelimitedstringToDDMMYYYYFormat(cashTO.getEddStr()));
							}
						}
						//					cashBookingDO.setExpectedDlvDate(cashTO.getEddString()[cnt]);
						// cashBookingDO.setAmount(cashTO.getAmount());
						// cashBookingDO.setPaid(cashTO.getAmountPaid().toString());
						/*
						 * cashBookingDO.setCashServiceTax(cashTO.getCashServiceTax());
						 * cashBookingDO.setCashCustomerType(cashTO.getCashCustomerType());
						 */

						cashBookingDO.setWeighingType(cashTO.getCaptureWeightType());
						//TODO volumetric weight calculation For VolumetricPopup and ParentChildCn Popup
						if(cashTO.getVolWeightArray() != null && cashTO.getVolWeightArray().length > cnt 
								&& !StringUtil.isEmpty(cashTO.getVolWeightArray()[cnt]) ){
							String volWeight = cashTO.getVolWeightArray()[cnt];
							if (!StringUtil.isEmpty(volWeight)) {
								String[] tokens = volWeight.split("~");
								// checking if volumetric weight is empty we are not
								// capturing volumetric weight details
								if (tokens.length > 0 && !StringUtil.isEmpty(tokens[4])) {
									cashBookingDO
									.setNoOfPieces(!StringUtil.isEmpty(tokens[0]) ? Integer
											.parseInt(tokens[0]) : null);
									cashBookingDO.setLength(!StringUtil.isEmpty(tokens[1]) ? Float
											.parseFloat(tokens[1]) : null);
									cashBookingDO.setBreadth(!StringUtil
											.isEmpty(tokens[2]) ? Float
													.parseFloat(tokens[2]) : null);
									cashBookingDO.setHeight(!StringUtil.isEmpty(tokens[3]) ? Float
											.parseFloat(tokens[3]) : null);
									cashBookingDO.setVolumetricWght(!StringUtil
											.isEmpty(tokens[4]) ? Double
													.parseDouble(tokens[4]) : null);
									cashBookingDO.setActualWeight(!StringUtil
											.isEmpty(tokens[5]) ? Double
													.parseDouble(tokens[5]) : null);
									cashBookingDO.setFinalWeight(!StringUtil
											.isEmpty(tokens[6]) ? Double
													.parseDouble(tokens[6]) : null);
								}
							}
						}

						Set address = null;
						ConsigneeAddressDO consigneeAddressDO = null;
						AreaDO areaDO =null;


						if (cashTO.getAreaIdForConsigneeArray() != null && cashTO.getAreaIdForConsigneeArray().length > cnt 
								&& cashTO.getAreaIdForConsigneeArray()[cnt] != null && cashTO.getAreaIdForConsigneeArray()[cnt] != 0) {
							if(cashBookingDOList!=null && cashBookingDOList.size()>0){

								address = new HashSet();
								Iterator iterator=(((CashBookingDO)cashBookingDOList.get(cnt)).getConsigneeID().getAddresses()).iterator();
								consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
								areaDO=consigneeAddressDO.getAreaDO();
								areaDO.setAreaId(cashTO.getAreaIdForConsigneeArray()[cnt]);
								consigneeAddressDO.setAreaDO(areaDO);
								consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1Array()[cnt]);
								consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2Array()[cnt]);
								consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3Array()[cnt]);
								address.add(consigneeAddressDO);


							}else{
								consigneeAddressDO=new ConsigneeAddressDO();
								areaDO= new AreaDO();
								address = new HashSet();
								areaDO.setAreaId(cashTO.getAreaIdForConsigneeArray()[cnt]);
								consigneeAddressDO.setAreaDO(areaDO);
								consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1Array()[cnt]);
								consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2Array()[cnt]);
								consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3Array()[cnt]);
								if(cashTO.getConsigneeAddressIdArray()!=null && cashTO.getConsigneeAddressIdArray().length>0 && !StringUtil.isEmpty(cashTO.getConsigneeAddressIdArray()[cnt])){
									consigneeAddressDO.setConsgAddrId(
											Integer.valueOf(cashTO.getConsigneeAddressIdArray()[cnt]));
								}
								address.add(consigneeAddressDO);
							}


						}

						// for setting values in ConsigneeDO
						ConsigneeDO consigneeDO = null;			

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							consigneeDO=((CashBookingDO)cashBookingDOList.get(cnt)).getConsigneeID();
						}else{
							consigneeDO = new ConsigneeDO();
						}

						if (cashTO.getConsigneeNameArray() != null && cashTO.getConsigneeNameArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsigneeNameArray()[cnt])) {
							consigneeDO.setFirstName(cashTO.getConsigneeNameArray()[cnt]);
						}
						if (cashTO.getConsigneeEmailArray() != null && cashTO.getConsigneeEmailArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsigneeEmailArray()[cnt])) {
							consigneeDO.setEmail(cashTO.getConsigneeEmailArray()[cnt]);
						}

						if (cashTO.getConsigneePhoneArray() != null && cashTO.getConsigneePhoneArray().length > cnt
								&& cashTO.getConsigneePhoneArray()[cnt] != null) {
							consigneeDO.setPhone(cashTO.getConsigneePhoneArray()[cnt].toString());
						}

						consigneeDO.setAddresses(address);
						cashBookingDO.setConsigneeID(consigneeDO);				


						/*--if (cashTO.getAreaIdForConsigneeArray() != null && cashTO.getAreaIdForConsigneeArray().length > cnt 
							&& cashTO.getAreaIdForConsigneeArray()[cnt] != null && cashTO.getAreaIdForConsigneeArray()[cnt] != 0) {
						ConsigneeAddressDO consigneeAddressDO = new ConsigneeAddressDO();
						AreaDO areaDO = new AreaDO();
						areaDO.setAreaId(cashTO.getAreaIdForConsigneeArray()[cnt]);
						consigneeAddressDO.setAreaDO(areaDO);
						if (cashTO.getConsigneeAddress1Array() != null && cashTO.getConsigneeAddress1Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsigneeAddress1Array()[cnt])){
							consigneeAddressDO.setStreet1(cashTO.getConsigneeAddress1Array()[cnt]);
						}
						if (cashTO.getConsigneeAddress2Array() != null && cashTO.getConsigneeAddress2Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsigneeAddress2Array()[cnt])){
							consigneeAddressDO.setStreet2(cashTO.getConsigneeAddress2Array()[cnt]);
						}
						if (cashTO.getConsigneeAddress3Array() != null && cashTO.getConsigneeAddress3Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsigneeAddress3Array()[cnt])){
							consigneeAddressDO.setStreet3(cashTO.getConsigneeAddress3Array()[cnt]);
						}

						if(cashTO.getConsigneeAddressIdArray()!=null && cashTO.getConsigneeAddressIdArray().length>0 && !StringUtil.isEmpty(cashTO.getConsigneeAddressIdArray()[cnt])){
							consigneeAddressDO.setConsgAddrId(
									new Integer(cashTO.getConsigneeAddressIdArray()[cnt]));
						}

						address.add(consigneeAddressDO);
					}

					// for setting values in ConsigneeDO
					ConsigneeDO consigneeDO = new ConsigneeDO();
					if (cashTO.getConsigneeNameArray() != null && cashTO.getConsigneeNameArray().length > cnt
							&& !StringUtil.isEmpty(cashTO.getConsigneeNameArray()[cnt])) {
						consigneeDO.setFirstName(cashTO.getConsigneeNameArray()[cnt]);
					}
					if (cashTO.getConsigneeEmailArray() != null && cashTO.getConsigneeEmailArray().length > cnt
							&& !StringUtil.isEmpty(cashTO.getConsigneeEmailArray()[cnt])) {
						consigneeDO.setEmail(cashTO.getConsigneeEmailArray()[cnt]);
					}

					if (cashTO.getConsigneePhoneArray() != null && cashTO.getConsigneePhoneArray().length > cnt
							&& cashTO.getConsigneePhoneArray()[cnt] != null) {
						consigneeDO.setPhone(cashTO.getConsigneePhoneArray()[cnt].toString());
					}
					if(address != null){
						consigneeDO.setAddresses(address);
					}

					if(cashTO.getConsigneeInfoIdArray()!=null && cashTO.getConsigneeInfoIdArray().length>0 && !StringUtil.isEmpty(cashTO.getConsigneeInfoIdArray()[cnt])){
						consigneeDO.setConsigneeId(
								new Integer(cashTO.getConsigneeInfoIdArray()[cnt]));
					}
					cashBookingDO.setConsigneeID(consigneeDO);*/




						Set<ConsignerAddressDO> consignorAddress = new HashSet<ConsignerAddressDO>();
						// create set of consignee address and set that to the consigneeDO
						// adresses.

						ConsignerAddressDO consignorAddressDO =null;
						PincodeDO consignorPincodeDO=null;
						CityDO consignorCityDO =null;

						if (cashTO.getConsignorPincodeIdArray() != null && cashTO.getConsignorPincodeIdArray().length > cnt 
								&& cashTO.getConsignorPincodeIdArray()[cnt] != null && cashTO.getConsignorPincodeIdArray()[cnt] != 0) {


							if(cashBookingDOList!=null && cashBookingDOList.size()>0){
								Iterator iterator=((CashBookingDO)cashBookingDOList.get(cnt)).getConsignorID().getConsignorAddresses().iterator();
								consignorAddressDO=(ConsignerAddressDO)iterator.next();

								consignorPincodeDO = consignorAddressDO.getPincodeId();
								consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeIdArray()[cnt]);
								consignorAddressDO.setPincodeId(consignorPincodeDO);

								consignorCityDO = consignorAddressDO.getCity();
								consignorCityDO.setCityId(cashTO.getConsignorCityIdArray()[cnt]);
								consignorAddressDO.setCity(consignorCityDO);

								consignorAddressDO.setStreet1(cashTO.getConsignorAddress1Array()[cnt]);
								consignorAddressDO.setStreet2(cashTO.getConsignorAddress2Array()[cnt]);
								consignorAddressDO.setStreet3(cashTO.getConsignorAddress3Array()[cnt]);

							}else{
								consignorAddressDO= new ConsignerAddressDO();
								consignorPincodeDO = new PincodeDO();
								consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeIdArray()[cnt]);
								consignorAddressDO.setPincodeId(consignorPincodeDO);

								consignorAddressDO.setStreet1(cashTO.getConsignorAddress1Array()[cnt]);
								consignorAddressDO.setStreet2(cashTO.getConsignorAddress2Array()[cnt]);
								consignorAddressDO.setStreet3(cashTO.getConsignorAddress3Array()[cnt]);

								if (cashTO.getConsignorCityIdArray() != null && cashTO.getConsignorCityIdArray().length > cnt 
										&& cashTO.getConsignorCityIdArray()[cnt] != null && cashTO.getConsignorCityIdArray()[cnt] != 0) {

									consignorCityDO = new CityDO();
									consignorCityDO.setCityId(cashTO.getConsignorCityIdArray()[cnt]);
									consignorAddressDO.setCity(consignorCityDO);
								}

							}
							consignorAddress.add(consignorAddressDO);
						}

						ConsignerInfoDO consignorDO = null;

						if(cashBookingDOList!=null && cashBookingDOList.size()>0){
							consignorDO=((CashBookingDO)cashBookingDOList.get(cnt)).getConsignorID();
						}else{
							consignorDO = new ConsignerInfoDO();
						}

						if (cashTO.getConsignorNameArray() != null && cashTO.getConsignorNameArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorNameArray()[cnt])) {
							consignorDO.setFirstName(cashTO.getConsignorNameArray()[cnt]);
						}
						if (cashTO.getConsignorEmailArray() != null && cashTO.getConsignorEmailArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorEmailArray()[cnt])) {
							consignorDO.setEmail(cashTO.getConsignorEmailArray()[cnt]);
						}

						if (cashTO.getConsignorPhoneArray() != null && cashTO.getConsignorPhoneArray().length > cnt
								&& cashTO.getConsignorPhoneArray()[cnt] != null) {
							consignorDO.setPhone(cashTO.getConsignorPhoneArray()[cnt].toString());
						}
						consignorDO.setConsignorAddresses(consignorAddress);
						cashBookingDO.setConsignorID(consignorDO);



						/*--
					Set consignorAddress = new HashSet();
					// create set of consignee address and set that to the consigneeDO
					// adresses.
					ConsignerAddressDO consignorAddressDO = new ConsignerAddressDO();
					if (cashTO.getConsignorPincodeIdArray() != null && cashTO.getConsignorPincodeIdArray().length > cnt 
								&& cashTO.getConsignorPincodeIdArray()[cnt] != null && cashTO.getConsignorPincodeIdArray()[cnt] != 0) {

						PincodeDO consignorPincodeDO = new PincodeDO();
						consignorPincodeDO.setPincodeId(cashTO.getConsignorPincodeIdArray()[cnt]);
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						if (cashTO.getConsignorAddress1Array() != null && cashTO.getConsignorAddress1Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorAddress1Array()[cnt])){
							consignorAddressDO.setStreet1(cashTO.getConsignorAddress1Array()[cnt]);
						}
						if (cashTO.getConsignorAddress2Array() != null && cashTO.getConsignorAddress2Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorAddress2Array()[cnt])){
							consignorAddressDO.setStreet2(cashTO.getConsignorAddress2Array()[cnt]);
						}
						if (cashTO.getConsignorAddress3Array() != null && cashTO.getConsignorAddress3Array().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorAddress3Array()[cnt])){
							consignorAddressDO.setStreet3(cashTO.getConsignorAddress3Array()[cnt]);
						}


						if (cashTO.getConsignorCityIdArray() != null && cashTO.getConsignorCityIdArray().length > cnt 
									&& cashTO.getConsignorCityIdArray()[cnt] != null && cashTO.getConsignorCityIdArray()[cnt] != 0) {
							CityDO consignorCityDO = new CityDO();
							consignorCityDO.setCityId(cashTO.getConsignorCityIdArray()[cnt]);
							consignorAddressDO.setCity(consignorCityDO);
						}

						if(cashTO.getConsigrAddrIdArray()!=null && cashTO.getConsigrAddrIdArray().length>0 && !StringUtil.isEmpty(cashTO.getConsigrAddrIdArray()[cnt])){
							consignorAddressDO.setConsigrAddrId(
									new Integer(cashTO.getConsigrAddrIdArray()[cnt]));
						}


						consignorAddress.add(consignorAddressDO);

						ConsignerInfoDO consignorDO = new ConsignerInfoDO();

						if(cashTO.getConsignerInfoIdArray()!=null && cashTO.getConsignerInfoIdArray().length>0 && !StringUtil.isEmpty(cashTO.getConsignerInfoIdArray()[cnt])){
							consignorDO.setConsignerId(
									new Integer(cashTO.getConsignerInfoIdArray()[cnt]));
						}

						if (cashTO.getConsignorNameArray() != null && cashTO.getConsignorNameArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorNameArray()[cnt])) {
							consignorDO.setFirstName(cashTO.getConsignorNameArray()[cnt]);
						}
						if (cashTO.getConsignorEmailArray() != null && cashTO.getConsignorEmailArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getConsignorEmailArray()[cnt])) {
							consignorDO.setEmail(cashTO.getConsignorEmailArray()[cnt]);
						}

						if (cashTO.getConsignorPhoneArray() != null && cashTO.getConsignorPhoneArray().length > cnt
								&& cashTO.getConsignorPhoneArray()[cnt] != null) {
							consignorDO.setPhone(cashTO.getConsignorPhoneArray()[cnt].toString());
						}
						consignorDO.setConsignorAddresses(consignorAddress);
						cashBookingDO.setConsignorID(consignorDO);
					}
						 */


						cashBookingDO.setIsInsured(cashTO.getIsInsured());
						cashBookingDO.setInsuredBy(cashTO.getInsuredBy());
						if (cashTO.getCashPaidArray() != null && cashTO.getCashPaidArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getCashPaidArray()[cnt])) {
							cashBookingDO.setPaid(cashTO.getCashPaidArray()[cnt]);
						}
						if (cashTO.getPaymentModeArray() != null && cashTO.getPaymentModeArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getPaymentModeArray()[cnt])) {
							cashBookingDO.setModeOfCollection(cashTO.getPaymentModeArray()[cnt]);
						}
						if (cashTO.getCashRemarksArray() != null && cashTO.getCashRemarksArray().length > cnt
								&& !StringUtil.isEmpty(cashTO.getCashRemarksArray()[cnt])) {
							cashBookingDO.setRemarks(cashTO.getCashRemarksArray()[cnt]);
						}
						if (cashTO.getFrieghtArray() != null && cashTO.getFrieghtArray().length > cnt){
							cashBookingDO.setFrieght(cashTO.getFrieghtArray()[cnt]);
						}
						if (cashTO.getRiskSurchargeAmtArray() != null && cashTO.getRiskSurchargeAmtArray().length > cnt){
							cashBookingDO.setRiskSurchgAmt(cashTO.getRiskSurchargeAmtArray()[cnt]);
						}
						if (cashTO.getDiscountArray() != null && cashTO.getDiscountArray().length > cnt){
							cashBookingDO.setDiscountInPerc(cashTO.getDiscountArray()[cnt]);
						}					

						if (cashTO.getServiceTaxArray() != null && cashTO.getServiceTaxArray().length > cnt){
							cashBookingDO.setServiceTax(cashTO.getServiceTaxArray()[cnt]);
						}

						if (cashTO.getServiceChargeAmtArray() != null && cashTO.getServiceChargeAmtArray().length > cnt){
							cashBookingDO.setServChrgAmt(cashTO.getServiceChargeAmtArray()[cnt]);
						}

						if (cashTO.getGrandTotalArray() != null && cashTO.getGrandTotalArray().length > cnt){
							cashBookingDO.setAmount(cashTO.getGrandTotalArray()[cnt]);
						}

						// for setting remote delivery popup data
						if(cashTO.getAreaNameArray() != null && cashTO.getAreaNameArray().length > cnt ){
							cashBookingDO.setAreaNameLtDox(cashTO.getAreaNameArray()[cnt]);
						}
						if(cashTO.getDocxAmountArray() != null && cashTO.getDocxAmountArray().length > cnt ){
							cashBookingDO.setDoxAmount(new Double(cashTO
									.getDocxAmountArray()[cnt]));
						}
						if(cashTO.getNonDocxAmountArray() != null && cashTO.getNonDocxAmountArray().length > cnt ){
							cashBookingDO.setNonDoxAmount(new Double(cashTO
									.getNonDocxAmountArray()[cnt]));
						}
						if(cashTO.getRemoteDeliveryRemarkArray() != null && cashTO.getRemoteDeliveryRemarkArray().length > cnt ){
							cashBookingDO.setRemoteDeliveryRemarks(cashTO
									.getRemoteDeliveryRemarkArray()[cnt]);
						}
						if(cashTO.getExtraDeliveryChargesArray() != null && cashTO.getExtraDeliveryChargesArray().length > cnt ){
							cashBookingDO.setExtrDlvCharges(String.valueOf(cashTO.getExtraDeliveryChargesArray()[cnt]));
						}
						if(cashTO.getRemoteDeliveryRemark1Array() != null && cashTO.getRemoteDeliveryRemark1Array().length > cnt ){
							cashBookingDO.setExtDlvRemarks(cashTO.getRemoteDeliveryRemark1Array()[cnt]);
						}
						if(cashTO.getSecurityPouchNumArray() != null && cashTO.getSecurityPouchNumArray().length > cnt ){
							cashBookingDO.setSecurityPouchNo((cashTO.getSecurityPouchNumArray()[cnt]).toString());
						}

						if(cashTO.getPaymentModeArray() != null && cashTO.getPaymentModeArray().length > cnt ){
							cashBookingDO.setPaymentMode(cashTO.getPaymentModeArray()[cnt]);
						}
						if(cashTO.getCustomerIndustryArray() != null && cashTO.getCustomerIndustryArray().length > cnt ){
							cashBookingDO.setCustomerIndustry(cashTO.getCustomerIndustryArray()[cnt]);
						}

						// for setting passport popup data
						//cashBookingDO.setPassportDetails(cashTO.getPassportDetailsArray()[cnt]);
						cashBookingDO.setConsgmntStatus(BookingConstants.BOOKED);

						SimpleDateFormat sdfDate = new SimpleDateFormat(
								BookingConstants.TIME_FORMAT_HHMMSS);
						Date now = new Date();
						String strDate = sdfDate.format(now);

						cashBookingDO.setBookingTime(strDate);

						if(cashTO.getCccId()!=null && cashTO.getCccId()!=0){
							OfficeDO cashCollectionCenter=new OfficeDO();
							cashCollectionCenter.setOfficeId(cashTO.getCccId());
							cashBookingDO.setCashCollectionCenter(cashCollectionCenter);
						}
						if(cashTO.getPriorityStickerNoArray()!=null && cashTO.getPriorityStickerNoArray().length>cnt){
							cashBookingDO.setPrioritySticker(cashTO.getPriorityStickerNoArray()[cnt]);
						}
						cashBookingDO.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
						//cashBookingDO.setServerDate(cashTO.getBookingDate());
						cashBookingDO.setNodeId(cashTO.getNodeId());
						cashBookingDO.setDiFlag(BookingConstants.DIFLAG);
						cashBookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

						bookingDOList.add(cashBookingDO);

						// Create new consignment for Nondoxpaperwrk items and store the
						// items in BookingItemList against the booking id
						CashBookingDO paperWrkItemDO = null;
						if (cashTO != null
								&& !StringUtil.isEmpty(cashTO.getNoOfPaperWrkItems())) {
							paperWrkItemDO = getPaperWorkBookingDO(cashTO);
							paperWrkItemDO.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
							paperWrkItemDO.setServerDate(cashTO.getBookingDate());
							paperWrkItemDO.setNodeId(cashTO.getNodeId());
							paperWrkItemDO.setDiFlag(BookingConstants.DIFLAG);
							paperWrkItemDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
							bookingDOList.add(paperWrkItemDO);
						}

						// for setting Child CN data popup data 
						//&& !StringUtil.isEmpty(cashTO.getChildCNDetailsArray()[cnt])
						if (cashTO != null
								&& cashTO.getChildCNDetailsArray() != null && cashTO.getChildCNDetailsArray().length > 0
								&& !StringUtil.isEmpty(cashTO.getChildCNDetailsArray()[cnt])) {
							getSetOfChildConsignments(cashTO.getChildCNDetailsArray()[cnt],
									cashTO.getCnNumberArray()[cnt]);
							itr = parentChildConsignmentDOList.iterator();
							while (itr.hasNext()) {
								CashBookingDO cashBookingDO1 = (CashBookingDO) itr.next();
								if (!bookingDOList.isEmpty() && bookingDOList.size() != 0) {
									cashBookingDO1.setLastTransModifiedDate(DateFormatterUtil.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
									cashBookingDO1.setServerDate(cashTO.getBookingDate());
									cashBookingDO1.setNodeId(cashTO.getNodeId());
									cashBookingDO1.setDiFlag(BookingConstants.DIFLAG);
									cashBookingDO1.setReadByLocal(BookingConstants.READ_BY_LOCAL);
									bookingDOList.add(cashBookingDO1);
								}

							}

						}
					}
					//				bookingDOList.addAll(bookingDO);

				}
			}

		}		

		return bookingDOList;

	}

	/**
	 * Adds a new consignment for paper wrk items.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the list the of child consignments
	 */
	private CashBookingDO getPaperWorkBookingDO(BookingTO bookingTO) {

		CashBookingDO bookingDO = new CashBookingDO();
		if(bookingTO.getPaperWrkBookingId() != null &&
				bookingTO.getPaperWrkBookingId() != 0){
			bookingDO.setBookingId(bookingTO.getPaperWrkBookingId());
		}
		bookingDO.setConsignmentNumber(bookingTO.getPaperworkCN());
		bookingDO.setParentCnNumber(bookingTO.getConsignmentNumber());
		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = cashBookingMDBDAO.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = cashBookingMDBDAO.getIdForDoxType();
			documentDo.setDocumentId(doxTypeId);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setDocumentID(documentDo);
			bookingDO.setAmount(new Double(0));
			bookingDO.setFinalWeight(new Double(bookingTO.getnDoxWeight()));
			if (!StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
				bookingDO.setNoOfPieces(Integer.parseInt(bookingTO
						.getNoOfPaperWrkItems()));
			}
			bookingDO.setBookingDate(bookingTO.getBookingDate());

			List<BookingItemListDO> bookingItemsList = cashBookingMDBDAO
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
			//set the consignment type as NonDox PaperWork
			bookingDO.setConsgmntType(BookingConstants.NONDOX_PAPERWORK);

		} catch (Exception e) {
			LOGGER.error("CashBookingMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;
	}

	/**
	 * Gets the sets the of child consignments.
	 * 
	 * @param childCNData
	 *            the child cn data
	 * @param parentConsignment
	 *            the parent consignment
	 * @return the sets the of child consignments
	 */
	public List<CashBookingDO> getSetOfChildConsignments(String childCNData,
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
			CashBookingDO bookingDO = new CashBookingDO();
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


	/**
	 * Gets the cash booking mdbdao.
	 *
	 * @return the cashBookingMDBDAO
	 */
	public CashBookingMDBDAO getCashBookingMDBDAO() {
		return cashBookingMDBDAO;
	}

	/**
	 * Sets the cash booking mdbdao.
	 *
	 * @param cashBookingMDBDAO the cashBookingMDBDAO to set
	 */
	public void setCashBookingMDBDAO(CashBookingMDBDAO cashBookingMDBDAO) {
		this.cashBookingMDBDAO = cashBookingMDBDAO;
	}

}
