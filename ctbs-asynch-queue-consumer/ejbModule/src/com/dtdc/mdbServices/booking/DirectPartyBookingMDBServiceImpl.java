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

import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.booking.DirectPartyBookingMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.dpbooking.DirectPartyBookingDO;
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
import com.dtdc.to.booking.dpbooking.DirectPartyBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectPartyBookingServiceImpl.
 */
@SuppressWarnings("rawtypes")
public class DirectPartyBookingMDBServiceImpl implements
DirectPartyBookingMDBService {

	/** The logger. */
	private Logger logger = LoggerFactory
	.getLogger(DirectPartyBookingMDBServiceImpl.class);

	/** The parent child consignment to list. */
	private List<DirectPartyBookingTO> parentChildConsignmentTOList = new ArrayList<DirectPartyBookingTO>();

	/** The Dp booking dao. */
	private DirectPartyBookingMDBDAO dpBookingDAO;

	/** The serializer. */
	public transient JSONSerializer serializer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.bs.booking.dpbooking.DirectPartyBookingService#
	 * saveDPBookingDetails
	 * (com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO)
	 */
	public boolean saveDPBookingDetails(CGBaseTO bookingTO)
	throws CGBusinessException {

		List<DirectPartyBookingTO> bookingTOList = (List<DirectPartyBookingTO>) bookingTO
		.getBaseList();
		return saveDPBookingDetails(bookingTOList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtdc.ng.bs.booking.dpbooking.DirectPartyBookingService#
	 * saveDPBookingDetails(java.util.List)
	 */
	@Override
	public boolean saveDPBookingDetails(List<DirectPartyBookingTO> bookingTOList)
	throws CGBusinessException {
		List<DirectPartyBookingDO> bookingList;
		Iterator<DirectPartyBookingTO> itr = null;
		boolean bookingStatus = false;
		parentChildConsignmentTOList.clear();
		/*
		 * Iterating the Franchisee List to get the Child consignment Details
		 * which is a hidden field in form of string
		 */
		itr = bookingTOList.iterator();
		for (int i = 0; i < bookingTOList.size(); i++) {
			try{
				DirectPartyBookingTO bookingTo = (DirectPartyBookingTO) bookingTOList
				.get(i);
				if (bookingTo != null && bookingTo.getChildCNDetails() != null
						&& !StringUtil.isEmpty(bookingTo.getChildCNDetails()[0])) {
					getSetOfChildConsignments(bookingTo,bookingTo.getChildCNDetails()[i],
							bookingTo.getConsignmentNumber());
				}
			}catch(Exception e){
				logger.error("DirectPartyBookingMDBServiceImpl::saveDPBookingDetails::Exception occured:"
						+e.getMessage());
			}
		}
		/*
		 * Iterating the parentChildConsignmentTOList to get the no of child
		 * consignment and store them as a seprate object in main list
		 */
		itr = parentChildConsignmentTOList.iterator();
		while (itr.hasNext()) {
			try{
				DirectPartyBookingTO bookingTo = (DirectPartyBookingTO) itr.next();
				bookingTOList.add(bookingTo);
			}catch(Exception e){
				logger.error("DirectPartyBookingMDBServiceImpl::saveDPBookingDetails::Exception occured:"
						+e.getMessage());
			}
		}
		// Convert Direct BokkingTO to DirectDO
		bookingList = populateDOListFromTO(bookingTOList);
		bookingStatus = dpBookingDAO
		.saveOrUpdateDPBookings(bookingList);

		return bookingStatus;
	}

	/**
	 * Populate do list from to.
	 *
	 * @param bookingTOList the booking to list
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<DirectPartyBookingDO> populateDOListFromTO(
			List<DirectPartyBookingTO> bookingTOList)throws CGBusinessException{List<DirectPartyBookingDO> bookingDOList = new ArrayList<DirectPartyBookingDO>();
			String dbServerStatus = null;
			/*
			 * Get Db Server status whether request have been written to central Db
			 * or not
			 */
			List<DirectPartyBookingDO> dpBookingDOList=null;
			if (bookingTOList != null && bookingTOList.size() != 0) {
				dbServerStatus = bookingTOList.get(0).getDbServer();


				int count=0;
				Iterator<DirectPartyBookingTO> itr = bookingTOList.iterator();
				while (itr.hasNext()) {

					try{
						DirectPartyBookingTO bookingTO = itr.next();
						DirectPartyBookingDO bookingDO = null;
						// populating foreign key objects
						// GeographyDO geographyDO = new GeographyDO();
						// geographyDO.setGeographyId(bookingTO.getGeographyID());
						try{
							dpBookingDOList = dpBookingDAO.getDPBookingDetails(bookingTO.getConsignmentNumber());
						}catch (Exception e) {
							logger.error("DirectPartyBookingMDBServiceImpl::populateDOListFromTO::Exception occured:"
									+e.getMessage());
						}
						boolean duplicateFlag=false;
						if(dpBookingDOList!=null && dpBookingDOList.size()>0){
							bookingDO=dpBookingDOList.get(count);
							logger.debug("For CNNO:"+bookingTO.getConsgNumber()+"Office Id From TO"+bookingTO.getOfficeID().intValue()+"Office Id From DO"+ bookingDO.getOfficeID().getOfficeId().intValue());
							//bookingTO.getBookingDate() == bookingDO.getBookingDate()
							if(bookingTO.getOfficeID().intValue() == bookingDO.getOfficeID().getOfficeId().intValue()){
								//Record found central with same branch and other details so it's genuine update
								logger.debug("Record found central with same branch and other details so it's genuine update");
								duplicateFlag=false;	
							}else{
								// Consignmet used Twice
								duplicateFlag=true;
								logger.debug("Record found central with Different branch and other details so it's Duplicate");
								// bookingDO=new BookingDuplicateDO();	

							}


						}else{
							logger.debug("New Record Insertion");
							bookingDO = new DirectPartyBookingDO();
						}

						/*if(bookingTO.getBookingId() != null && bookingTO.getBookingId() != 0){
					//bookingDO.setBookingId(bookingTO.getBookingId());
				}else */if(bookingTO.getBookingId() == 0 || bookingTO.getBookingId() == null){
					bookingDO.setServerDate(bookingTO.getBookingDate());
				}

				if(bookingTO.getUserId()!= null && bookingTO.getUserId() != 0){
					bookingDO.setUserId(bookingTO.getUserId());
				}

				PincodeDO pincodeDO = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					pincodeDO=dpBookingDOList.get(count).getPincodeDO();
				}else{
					pincodeDO = new PincodeDO();
				}
				pincodeDO.setPincodeId(bookingTO.getPincodeId());

				CityDO cityDO = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					cityDO=dpBookingDOList.get(count).getCityDO();
				}else{
					cityDO = new CityDO();
				}
				cityDO.setCityId(bookingTO.getCityId());
				if (bookingTO.getPincodeId() != null
						&& bookingTO.getPincodeId() != 0) {
					bookingDO.setPincodeDO(pincodeDO);

				}
				if (bookingTO.getCityId() != null && bookingTO.getCityId() != 0) {
					bookingDO.setCityDO(cityDO);
				}

				// populating COD FOD pop up fields in consignee,consignee address
				// and booking DOs
				Set address = null;
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;
				// create set of consignee address and set that to the consigneeDO
				// adresses.

				if (bookingTO.getAreaIdForConsignee() != null
						&& bookingTO.getAreaIdForConsignee() != 0) {
					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						address = new HashSet();
						Iterator iterator=(dpBookingDOList.get(count).getConsigneeID().getAddresses()).iterator();
						consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
						areaDO=consigneeAddressDO.getAreaDO();
						areaDO.setAreaId(bookingTO.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(bookingTO.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(bookingTO.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(bookingTO.getConsigneeAddress3());
						address.add(consigneeAddressDO);
					}else{
						consigneeAddressDO=new ConsigneeAddressDO();
						areaDO= new AreaDO();
						address = new HashSet();
						areaDO.setAreaId(bookingTO.getAreaIdForConsignee());
						consigneeAddressDO.setAreaDO(areaDO);
						consigneeAddressDO.setStreet1(bookingTO.getConsigneeAddress1());
						consigneeAddressDO.setStreet2(bookingTO.getConsigneeAddress2());
						consigneeAddressDO.setStreet3(bookingTO.getConsigneeAddress3());
						if(bookingTO.getConsigneeAddressId() != null && bookingTO.getConsigneeAddressId() != 0){
							consigneeAddressDO.setConsgAddrId(bookingTO.getConsigneeAddressId());
						}
						address.add(consigneeAddressDO);
					}

					ConsigneeDO consigneeDO = null;
					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						consigneeDO=dpBookingDOList.get(count).getConsigneeID();
					}else{
						consigneeDO = new ConsigneeDO();
					}

					consigneeDO.setFirstName(bookingTO.getConsigneeName());
					consigneeDO.setEmail(bookingTO.getConsigneeEmail());
					if (bookingTO.getConsigneePhone() != null) {
						consigneeDO.setPhone(bookingTO.getConsigneePhone().toString());
					}

					/*if(bookingTO.getConsigneeInfoId() != null && bookingTO.getConsigneeInfoId() != 0){
						consigneeDO.setConsigneeId(bookingTO.getConsigneeInfoId());
					}*/
					consigneeDO.setAddresses(address);		
					bookingDO.setConsigneeID(consigneeDO);
				}



				/* Set the consignor Info */

				Set consignorAddress = new HashSet();
				// create set of consignee address and set that to the consigneeDO
				// adresses.
				ConsignerAddressDO consignorAddressDO = null;
				PincodeDO consignorPincodeDO=null;
				CityDO consignorCityDO =null;

				if (bookingTO.getConsignorPincodeId() != null
						&& bookingTO.getConsignorPincodeId() > 0) {
					/*PincodeDO consignorPincodeDO = new PincodeDO();
					consignorPincodeDO.setPincodeId(bookingTO.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);

					consignorAddressDO.setStreet1(bookingTO.getConsignorAddress1());
					consignorAddressDO.setStreet2(bookingTO.getConsignorAddress2());
					consignorAddressDO.setStreet3(bookingTO.getConsignorAddress3());
					if(bookingTO.getConsigrAddrId() != null && bookingTO.getConsigrAddrId() != 0){
						consignorAddressDO.setConsigrAddrId(bookingTO.getConsigrAddrId());
					}
					if (bookingTO.getConsignerCityId() != null
							&& bookingTO.getConsignerCityId() > 0) {
						CityDO consignorCityDO = new CityDO();
						consignorCityDO.setCityId(bookingTO.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);
					}*/

					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						Iterator iterator=(dpBookingDOList.get(count).getConsignorID().getConsignorAddresses()).iterator();
						consignorAddressDO=(ConsignerAddressDO)iterator.next();

						consignorPincodeDO = consignorAddressDO.getPincodeId();
						consignorPincodeDO.setPincodeId(bookingTO.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorCityDO = consignorAddressDO.getCity();
						consignorCityDO.setCityId(bookingTO.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);

						consignorAddressDO.setStreet1(bookingTO.getConsignorAddress1());
						consignorAddressDO.setStreet2(bookingTO.getConsignorAddress2());
						consignorAddressDO.setStreet3(bookingTO.getConsignorAddress3());

					}else{
						consignorAddressDO= new ConsignerAddressDO();
						consignorPincodeDO = new PincodeDO();
						consignorPincodeDO.setPincodeId(bookingTO.getConsignorPincodeId());
						consignorAddressDO.setPincodeId(consignorPincodeDO);

						consignorAddressDO.setStreet1(bookingTO.getConsignorAddress1());
						consignorAddressDO.setStreet2(bookingTO.getConsignorAddress2());
						consignorAddressDO.setStreet3(bookingTO.getConsignorAddress3());

						if (bookingTO.getConsignerCityId() != null
								&& bookingTO.getConsignerCityId() > 0) {
							consignorCityDO = new CityDO();
							consignorCityDO.setCityId(bookingTO.getConsignerCityId());
							consignorAddressDO.setCity(consignorCityDO);
						}

					}

					consignorAddress.add(consignorAddressDO);

					ConsignerInfoDO consignorDO = null;

					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						consignorDO=dpBookingDOList.get(count).getConsignorID();
					}else{
						consignorDO= new ConsignerInfoDO();
					}

					/*if(bookingTO.getConsignerInfoId() != null && bookingTO.getConsignerInfoId() != 0){
						consignorDO.setConsignerId(bookingTO.getConsignerInfoId());
					}*/
					if(!StringUtil.isEmpty(bookingTO.getConsignorName())){
						consignorDO.setFirstName(bookingTO.getConsignorName());
					}
					if (!StringUtil.isEmpty(bookingTO.getConsignorPhone())) {
						consignorDO.setPhone(bookingTO.getConsignorPhone());
					}
					if(!StringUtil.isEmpty(bookingTO.getConsignorEmail())){
						consignorDO.setEmail(bookingTO.getConsignorEmail());
					}
					consignorDO.setConsignorAddresses(consignorAddress);
					bookingDO.setConsignorID(consignorDO);
				}


				String customerCode = bookingTO.getCustomerCode();
				if(customerCode != null && !customerCode.trim().equals("")){
					dpBookingDAO.getCustomerDetails(customerCode);
				}

				//consigneeDO.setBookingType(bookingTO.getBookingType());
				// set consigneeDO in bookingDO
				//				bookingDO.setConsigneeID(consigneeDO);

				ServiceDO serviceDO = null;

				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					serviceDO=dpBookingDOList.get(count).getServiceID();
				}else{
					serviceDO =  new ServiceDO();
				}

				if (!StringUtil.isEmpty(bookingTO.getServiceType())) {
					serviceDO.setServiceId(Integer.parseInt(bookingTO
							.getServiceType()));
				}

				DocumentDO documentDo = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					documentDo=dpBookingDOList.get(count).getDocumentID();
				}else{
					documentDo =  new DocumentDO();
				}
				documentDo.setDocumentId(bookingTO.getDoxType());
				//ItemDO itemDo = new ItemDO();
				//	itemDo.setItemId(bookingTO.getContents());
				ProductDO productDo = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					productDo=dpBookingDOList.get(count).getProductID();
				}else{
					productDo =  new ProductDO();
				}

				String prodId = bookingTO.getMainProductType();
				if (prodId != null && !StringUtil.isEmpty(prodId)) {
					productDo.setProductId(Integer.parseInt(prodId));
					bookingDO.setProductID(productDo);
				}
				// COMMENTED AS IT IS NOT REQUIRED
				/*FranchiseeDO franchiseeDo = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					franchiseeDo=dpBookingDOList.get(count).getFranchiseeId();
				}else{
					franchiseeDo =  new FranchiseeDO();
				}
				if(bookingTO.getFranchiseeID() != null)
					franchiseeDo.setFranchiseeId(bookingTO.getFranchiseeID());*/
				/*
				 * OfficeDO officeDO = new OfficeDO();
				 * officeDO.setOfficeId(bookingTO.getBookingZone());
				 */
				bookingDO.setWeighingType(bookingTO.getCaptureWeightType());

				CustomerDO customerDO = null;
				if (bookingTO.getCustomerId() != null
						&& bookingTO.getCustomerId() != 0) {
					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						customerDO=dpBookingDOList.get(count).getCustomerId();
					}else{
						customerDO =  new CustomerDO();
					}
					customerDO.setCustomerId(bookingTO.getCustomerId());
					bookingDO.setCustomerId(customerDO);
				}
				bookingDO.setCustRefNo(bookingTO.getReferenceNumber());

				ChannelDO channelDO = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					channelDO=dpBookingDOList.get(count).getChannelTypeID();
				}else{
					channelDO =  new ChannelDO();
				}
				if (bookingTO.getChannelType() != null
						&& !bookingTO.getChannelType().equalsIgnoreCase("")) {
					channelDO.setChannelTypeId(Integer.parseInt(bookingTO
							.getChannelType()));
				}

				EmployeeDO employeeDoForPickUpBoy = null;
				// employeeDoForPickUpBoy.setEmployeeId(bookingTO.getPickupBoyDetails());
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					employeeDoForPickUpBoy=dpBookingDOList.get(count).getEmployeeIdForPickupBoyId();
				}else{
					employeeDoForPickUpBoy =  new EmployeeDO();
				}
				if (bookingTO.getPickupBoyEmployeeId() != null
						&& !bookingTO.getPickupBoyEmployeeId().equalsIgnoreCase("")) {
					employeeDoForPickUpBoy.setEmployeeId(Integer.parseInt(bookingTO
							.getPickupBoyEmployeeId()));
				}
				// Populating DO for grid fields
				bookingDO.setConsignmentNumber(bookingTO.getConsignmentNumber());
				/*
				 * if (bookingTO.getGeographyID() != null) {
				 * bookingDO.setGeographyIDForPincode(geographyDO);
				 * bookingDO.setGeographyIdForCity(geographyDO); }
				 */
				bookingDO.setFinalWeight(bookingTO.getFinalWeight());
				bookingDO.setVolumetricWght(bookingTO.getVolumtericWeight());
				bookingDO.setNoOfPieces(bookingTO.getNoOfPieces());
				bookingDO.setInvoiceValue(bookingTO.getInvoiceValue());
				bookingDO.setOpenMnfstNo(bookingTO.getOpenManifestNo());
				bookingDO.setSecurityPouchNo(bookingTO.getSecurityPouchNo());
				bookingDO.setAmount(bookingTO.getAmount());
				bookingDO.setDbServer(dbServerStatus);
				bookingDO.setCodAmount(bookingTO.getCodAmount());
				bookingDO.setInFavourOf(bookingTO.getInFavourOf());
				bookingDO.setFodAmount(bookingTO.getFodAmount());
				bookingDO.setCodRemarks(bookingTO.getCodFodRemarks());
				bookingDO.setModeOfCollection(bookingTO.getModeOfCollection());

				/* For operation freedom */
				bookingDO.setOperationFreedom(bookingTO.getOperationsFreedom());
				// bookingDO.setExpectedDlvDate(bookingTO.getEdd());
				if (!StringUtil.isEmpty(bookingTO.getServiceType())) {
					bookingDO.setServiceID(serviceDO);
				}
				if (bookingTO.getDoxType() != null && bookingTO.getDoxType() != 0) {
					bookingDO.setDocumentID(documentDo);
				}

				CommodityDO commodityDO = null;
				if(dpBookingDOList!=null && dpBookingDOList.size()>0){
					commodityDO=dpBookingDOList.get(count).getDomesticCommodityId();
				}else{
					commodityDO =  new CommodityDO();
				}
				if (bookingTO.getContents() != null && bookingTO.getContents() != 0) {
					commodityDO.setCommodityId(bookingTO.getContents());
					bookingDO.setDomesticCommodityId(commodityDO);
				}
				// populating Header information
				bookingDO.setBookingDate(bookingTO.getBookingDate());
				bookingDO.setExpectedDlvDate(bookingTO.getEdd());
				// Save Remote Delivery Pop up data
				bookingDO.setExtDlvRemarks(bookingTO.getRemoteExtraDlvRemarks());
				bookingDO.setRemoteDeliveryRemarks(bookingTO
						.getRemoteDeliveryRemarks());
				bookingDO.setExtrDlvCharges(String.valueOf(bookingTO
						.getRemoteDeliveryExtraDeliveryCharges()));
				bookingDO.setAreaNameLtDox(bookingTO.getAreaName());
				bookingDO.setDoxAmount(bookingTO.getRemoteDeliveryDoxAmount());
				bookingDO
				.setNonDoxAmount(bookingTO.getRemoteDeliveryNonDoxAmount());

				//Rate Calculation component
				bookingDO.setTsAmount(bookingTO.getTsAmount());
				bookingDO.setServChrgAmt(bookingTO.getServiceChargeAmount());
				bookingDO.setRiskSurchgAmt(bookingTO.getRiskSurchargeAmount());
				bookingDO.setMiscCharge(bookingTO.getTotalMiscExpense());
				bookingDO.setDiplomaticOrRemoteAreaCharge(bookingTO.getDiplomaticOrRemoteAreaCharge());
				bookingDO.setFrieght(bookingTO.getTsAmount());

				/*
				 * if (bookingDO.getBookingDate() != null) { String dateString =
				 * DateFormatUtils.format( bookingDO.getBookingDate(),
				 * BookingConstants.DDMMYYYY_SLASH);
				 * bookingTO.setBookingDateStr(dateString); }
				 * 
				 * if (bookingDO.getExpectedDlvDate() != null) { String dateString =
				 * DateFormatUtils.format( bookingDO.getExpectedDlvDate(),
				 * BookingConstants.DDMMYYYY_SLASH);
				 * bookingTO.setEddStr(dateString); }
				 */
				if (!StringUtil.isEmpty(bookingTO.getBookingZone())) {
					bookingDO.setBookingDivisionID(bookingTO.getBookingZone());
				}
				if (!StringUtil.isEmpty(bookingTO.getChannelType())) {
					bookingDO.setChannelTypeID(channelDO);
				}
				if (bookingTO.getPickupBoyDetails() != null
						&& bookingTO.getPickupBoyDetails() != 0) {
					bookingDO.setEmployeeIdForPickupBoyId(employeeDoForPickUpBoy);

				}

				// for populating child consignments
				bookingDO.setParentCnNumber(bookingTO.getParentCnNumber());
				// only for child CN
				if (bookingTO.getChargedWeight() != null && bookingTO.getChargedWeight() !=0) {
					bookingDO.setFinalWeight(bookingTO.getChargedWeight());
				}else{
					bookingDO.setFinalWeight(bookingTO.getActualWeight());
				}
				if (bookingTO.getLengthInCms() != null) {
					bookingDO.setLength(bookingTO.getLengthInCms().floatValue());
				}
				if (bookingTO.getBreadthInCms() != null) {
					bookingDO.setBreadth(bookingTO.getBreadthInCms().floatValue());
				}
				if (bookingTO.getHeightInCms() != null) {
					bookingDO.setHeight(bookingTO.getHeightInCms().floatValue());
				}
				if (bookingTO.getActualWeight() != null) {
					bookingDO.setActualWeight(bookingTO.getActualWeight());
				} else {
					bookingDO.setActualWeight(bookingTO.getFinalWeight());
				}
				bookingDO.setDescription(bookingTO.getChildCnDesc());
				SimpleDateFormat sdfDate = new SimpleDateFormat(
						BookingConstants.TIME_FORMAT_HHMMSS);// dd/MM/yyyy
				Date now = new Date();
				String strDate = sdfDate.format(now);

				bookingDO.setBookingTime(strDate);
				bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
				bookingDO.setFranchMinfNumber(bookingTO.getFranchiseeManifestNo());
				bookingDO.setPassportDetails(bookingTO.getPassportDetails());

				// Create new consignment for Nondoxpaperwrk items and store the
				// items in BookingItemList against the booking id
				DirectPartyBookingDO paperWrkItemDO = null;
				if (bookingTO != null
						&& !StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
					paperWrkItemDO = getPaperWorkBookingDO(bookingTO);
					bookingDOList.add(paperWrkItemDO);
				}
				// set vas product code selected in grid
				bookingDO.setVasProductCode(bookingTO.getProductCode());

				// save booking branch off id

				if (bookingTO.getOfficeID() != null && bookingTO.getOfficeID() != 0) {
					OfficeDO bookingBranchOffice = null;
					if(dpBookingDOList!=null && dpBookingDOList.size()>0){
						bookingBranchOffice=dpBookingDOList.get(count).getOfficeID();
					}else{
						bookingBranchOffice =  new OfficeDO();
					}
					bookingBranchOffice.setOfficeId(bookingTO.getOfficeID());
					bookingDO.setOfficeID(bookingBranchOffice);
				}
				// save customer ref no
				// bookingDO.setCustRefNo(bookingTO.getReferenceNumber());
				// Adding the Do in the List

				// save insurance charge
				bookingDO.setIsInsured(bookingTO.getIsInsured());
				bookingDO.setInsuredBy(bookingTO.getInsuredBy());
				bookingDO.setInsuranceAmount(bookingTO.getInsuranceCharge());

				bookingDO.setLastTransModifiedDate(DateFormatterUtil
						.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				bookingDO.setNodeId(bookingTO.getNodeId());
				bookingDO.setDiFlag(BookingConstants.DIFLAG);
				bookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
				bookingDO.setNodeId(bookingTO.getNodeId());
				bookingDO.setDiFlag(BookingConstants.DIFLAG);
				bookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);
				/*try {
					bookingDO.setNodeId(ApplicatonUtils.getNodeId());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					//e#prntstcktrce
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					//e#prntstcktrce
				}*/

				if(!duplicateFlag){
					logger.debug("Genuine insert / Update");
					bookingDOList.add(bookingDO); //genuine insert / Update
				}else{

					//update in Duplicate Table
					logger.debug("Duplicate Table insert");
					BookingDuplicateDO bookingDuplicateDO=new BookingDuplicateDO();

					try {
						PropertyUtils.copyProperties(bookingDuplicateDO, bookingDO);
						dpBookingDAO.insertInBookingDuplicateTable(bookingDuplicateDO);
					} catch (Exception e) {
						logger.error("DirectPartyBookingMDBServiceImpl::populateDOListFromTO::Exception occured:"
								+e.getMessage());
					}

				}
				//count++;

					}catch(Exception e){
						logger.error("DirectPartyBookingMDBServiceImpl::populateDOListFromTO::Exception occured:"
								+e.getMessage());
					}
				}
			}
			return bookingDOList;
	}

	/**
	 * Gets the sets the of child consignments.
	 *
	 * @param dpBookingTo the dp booking to
	 * @param childCNData the child cn data
	 * @param parentConsignment the parent consignment
	 * @return the sets the of child consignments
	 */
	public List<DirectPartyBookingTO> getSetOfChildConsignments(DirectPartyBookingTO dpBookingTo,
			String childCNData, String parentConsignment) {
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

		if (!StringUtil.isEmpty(childCNData)) {
			StringTokenizer st = null;
			String[] tokens = childCNData
			.split(ApplicationConstants.CHARACTER_TILDE);
			if(!dpBookingTo.getRequestType().equals("save")){
				st = new StringTokenizer(tokens[0],
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
			}else{
				st = new StringTokenizer(tokens[0],
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
			}


			for (int i = 0; i < slNoList.size(); i++) {
				DirectPartyBookingTO bookingTO = new DirectPartyBookingTO();
				bookingTO.setParentCnNumber(parentConsignment);
				bookingTO.setConsignmentNumber(childCNNumberList.get(i));

				if (!StringUtil.isEmpty(actualWeightList.get(i))) {
					bookingTO.setActualWeight(Double.parseDouble(actualWeightList
							.get(i)));
				}
				if (!StringUtil.isEmpty(lengthList.get(i))) {
					bookingTO.setLengthInCms(Float.parseFloat(lengthList.get(i)));
				}
				if (!StringUtil.isEmpty(widthList.get(i))) {
					bookingTO.setBreadthInCms(Float.parseFloat(widthList.get(i)));
				}
				if (!StringUtil.isEmpty(heightList.get(i))) {
					bookingTO.setHeightInCms(Float.parseFloat(heightList.get(i)));
				}
				if (!StringUtil.isEmpty(volWeightList.get(i))) {
					bookingTO.setVolumtericWeight(Double.parseDouble(volWeightList
							.get(i)));
				}
				if (!StringUtil.isEmpty(finalWeightList.get(i))) {
					bookingTO.setChargedWeight(Double.parseDouble(finalWeightList
							.get(i)));
				}
				if (descList != null && descList.size() > i) {
					bookingTO.setChildCnDesc(descList.get(i));
				}
				if(dpBookingTo.getRequestType().equals(BookingConstants.UPDATE_AFTER_ENQUIRY)){
					if (childBookingIdList  != null && childBookingIdList.size() > 0
							&& !StringUtil.isEmpty(childBookingIdList.get(i)) && !childBookingIdList.get(i).equals("NA")) {
						bookingTO.setBookingId(Integer.valueOf(childBookingIdList.get(i)));
					}
				}

				// TO-DO Need to remove these fields later

				bookingTO.setEdd(new Date());
				bookingTO.setBookingDate(new Date());
				bookingTO.setInvoiceDate(new Date());

				// Added by Hari as Manifest people Requested

				bookingTO.setOfficeID(dpBookingTo.getOfficeID());
				bookingTO.setCityId(dpBookingTo.getCityId());
				bookingTO.setPincodeId(dpBookingTo.getPincodeId());
				bookingTO.setServiceType(dpBookingTo.getServiceType());
				bookingTO.setDoxType(dpBookingTo.getDoxType());
				parentChildConsignmentTOList.add(bookingTO);

			}
		}
		return parentChildConsignmentTOList;

	}

	/**
	 * Adds a new consignment for paper wrk items.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the list the of child consignments
	 */
	private DirectPartyBookingDO getPaperWorkBookingDO(BookingTO bookingTO) {

		DirectPartyBookingDO bookingDO = new DirectPartyBookingDO();

		bookingDO.setConsignmentNumber(bookingTO.getPaperWrkCn());
		bookingDO.setParentCnNumber(bookingTO.getConsignmentNumber());

		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = dpBookingDAO.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = dpBookingDAO.getIdForDoxType();
			documentDo.setDocumentId(doxTypeId);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setDocumentID(documentDo);
			bookingDO.setAmount(new Double(0));
			bookingDO.setFinalWeight(bookingTO.getPaperWrkWeight());

			if (!StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
				bookingDO.setNoOfPieces(Integer.parseInt(bookingTO
						.getNoOfPaperWrkItems()));
			}
			bookingDO.setBookingDate(bookingTO.getBookingDate());
			List<BookingItemListDO> bookingItemsList = dpBookingDAO
			.getBookingItemList(bookingTO.getPaperWrkCn());
			Set<BookingItemListDO> bookingItemsSet = new HashSet<BookingItemListDO>(
					bookingItemsList);
			bookingDO.setNonDoxPaperWrkItems(bookingItemsSet);
			SimpleDateFormat sdfDate = new SimpleDateFormat(
					BookingConstants.TIME_FORMAT_HHMMSS);
			Date now = new Date();
			String strDate = sdfDate.format(now);
			bookingDO.setBookingTime(strDate);
			bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
		} catch (Exception e) {
			logger.error("DirectPartyBookingMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;

	}

	/**
	 * Gets the dp booking dao.
	 *
	 * @return the dpBookingDAO
	 */
	public DirectPartyBookingMDBDAO getDpBookingDAO() {
		return dpBookingDAO;
	}

	/**
	 * Sets the dp booking dao.
	 *
	 * @param dpBookingDAO the dpBookingDAO to set
	 */
	public void setDpBookingDAO(DirectPartyBookingMDBDAO dpBookingDAO) {
		this.dpBookingDAO = dpBookingDAO;
	}


}
