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

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.booking.FranchiseeBookingMDBDAO;

import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingDuplicateDO;
import com.dtdc.domain.booking.BookingItemListDO;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.frbooking.FranchiseeBookingDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.ConsignerAddressDO;
import com.dtdc.domain.master.customer.ConsignerInfoDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.CommodityDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.to.booking.BookingTO;
import com.dtdc.to.booking.franchisebooking.FranchiseeBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeBookingServiceImpl.
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FranchiseeBookingMDBServiceImpl implements
FranchiseeBookingMDBService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(FranchiseeBookingMDBServiceImpl.class);

	/** The franchisee booking dao. */
	private FranchiseeBookingMDBDAO franchiseeBookingDAO;

	/** The parent child consignment to list. */
	private List<FranchiseeBookingTO> parentChildConsignmentTOList = new ArrayList<FranchiseeBookingTO>();

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.FranchiseeBookingMDBService#saveFRBookingDetails(CGBaseTO)
	 */
	public boolean saveFRBookingDetails(CGBaseTO franchiseeBookingTO) throws CGSystemException ,CGBusinessException{

		List<FranchiseeBookingTO> bookingTOList = (List<FranchiseeBookingTO>) franchiseeBookingTO
		.getBaseList();
		return saveFRBookingDetails(bookingTOList);
	}

	/* (non-Javadoc)
	 * @see com.dtdc.ng.bs.booking.franchisebooking.FranchiseeBookingService#saveFRBookingDetails(java.util.List)
	 */
	@Override
	public boolean saveFRBookingDetails(List<FranchiseeBookingTO> bookingTOList) throws CGSystemException ,CGBusinessException{
		List<FranchiseeBookingDO> bookingList;
		Iterator itr = null;
		boolean bookingStatus = false;
		parentChildConsignmentTOList.clear();

		LOGGER.debug("Service saveFRBookingDetails bookingTOList SIZE"+bookingTOList.size());

		// try {
		/*
		 * Iterating the Franchisee List to get the Child consignment Details
		 * which is a hidden field in form of string
		 */
		itr = bookingTOList.iterator();
		// while (itr.hasNext()) {
		for (int i = 0; i < bookingTOList.size(); i++) {
			FranchiseeBookingTO bookingTo = (FranchiseeBookingTO) bookingTOList
			.get(i);
			// parentChildConsignmentTOList.clear();
			/*commented out by sanjukta for two way write */
			// Parent Child Functionality is required as per the Use case, hence uncommented by Rajashekar Sabbani 
			if (bookingTo != null && bookingTo.getNoOfPieces() > 1	
					&& bookingTo.getChildCNDetails()!=null && bookingTo.getChildCNDetails()[0]!=null && !bookingTo.getChildCNDetails()[0].equals("") && !StringUtil.isEmpty(bookingTo.getChildCNDetails()[0])) {
				getSetOfChildConsignments(bookingTo,bookingTo.getChildCNDetails()[i],
						bookingTo.getConsignmentNumber());
			}

		}
		/*
		 * Iterating the parentChildConsignmentTOList to get the no of child
		 * consignment and store them as a seprate object in main list
		 */

		itr = parentChildConsignmentTOList.iterator();
		while (itr.hasNext()) {
			FranchiseeBookingTO bookingTo = (FranchiseeBookingTO) itr.next();
			bookingTOList.add(bookingTo);
		}
		// Convert Franchisee BokkingTO to FranchiseeDO
		bookingList = populateDOListFromTO(bookingTOList);
		bookingStatus = franchiseeBookingDAO
		.insertAndDeleteFranchiseeBookingDetails(bookingList);
		return bookingStatus;
	}


	/**
	 * Populate do list from to.
	 *
	 * @param bookingTOList the booking to list
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<FranchiseeBookingDO> populateDOListFromTO (
			List<FranchiseeBookingTO> bookingTOList) throws CGBusinessException {List<FranchiseeBookingDO> bookingDOList = new ArrayList<FranchiseeBookingDO>();

			/*
			 * Get Db Server status whether request have been written to central Db
			 * or not
			 */

			LOGGER.debug(":::::::::::populateDOListFromTO STARTS:::::::::::::::");

			if (bookingTOList != null && bookingTOList.size() != 0) {
				Iterator<FranchiseeBookingTO> itr = bookingTOList.iterator();
				String dbServerStatus = null;
				dbServerStatus = bookingTOList.get(0).getDbServer();
				LOGGER.debug(":::::::::::populateDOListFromTO bookingTOList Size::::::::::::::"+bookingTOList.size());	

				int count=0;
				while (itr.hasNext()) {
					FranchiseeBookingTO bookingTO = itr.next();
					//FranchiseeBookingDO bookingDO = new FranchiseeBookingDO();
					List<FranchiseeBookingDO> franchiseeBookingDOList=null;
					LOGGER.debug(":::::::::::::::::CNNO:::::::::::::::"+bookingTO.getConsignmentNumber());
					try {
						franchiseeBookingDOList =franchiseeBookingDAO.getFranchiseeBookingDetails(bookingTO.getConsignmentNumber().trim()
						);
					} catch (CGSystemException e) {
						LOGGER.error("FranchiseeBookingMDBServiceImpl::populateDOListFromTO::Exception::" + e.getMessage());
					}

					boolean duplicateFlag=false;

					FranchiseeBookingDO bookingDO=null;
					LOGGER.debug("CNNO:"+bookingTO.getConsignmentNumber());
					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						bookingDO=franchiseeBookingDOList.get(count);
						LOGGER.debug("For CNNO:"+bookingTO.getConsgNumber()+"Office Id From TO"+bookingTO.getOfficeID().intValue()+"Office Id From DO"+ bookingDO.getOfficeID().getOfficeId().intValue());
						//bookingTO.getBookingDate() == bookingDO.getBookingDate()
						if(bookingTO.getOfficeID().intValue() == bookingDO.getOfficeID().getOfficeId().intValue()){
							//Record found central with same branch and other details so it's genuine update
							LOGGER.debug("Record found central with same branch and other details so it's genuine update");
							duplicateFlag=false;	
						}else{
							// Consignmet used Twice
							duplicateFlag=true;
							LOGGER.debug("Record found central with Different branch and other details so it's Duplicate");
							// bookingDO=new BookingDuplicateDO();	

						}
					}else{

						bookingDO = new FranchiseeBookingDO();
					}



					// populating foreign key objects

					if(bookingTO.getUserId()!= null && bookingTO.getUserId() != 0){
						bookingDO.setUserId(bookingTO.getUserId());
					}

					/*if(bookingTO.getBookingId() != null && bookingTO.getBookingId() != 0){
					//bookingDO.setBookingId(bookingTO.getBookingId());
				}else */if(bookingTO.getBookingId() == 0 || bookingTO.getBookingId() == null){
					bookingDO.setServerDate(bookingTO.getBookingDate());
				}

				// GeographyDO geographyDO = new GeographyDO();
				// geographyDO.setGeographyId(bookingTO.getGeographyID());

				PincodeDO pincodeDO = null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					pincodeDO=franchiseeBookingDOList.get(count).getPincodeDO();
				}else{
					pincodeDO = new PincodeDO();
				}
				pincodeDO.setPincodeId(bookingTO.getPincodeId());

				CityDO cityDO = null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					cityDO=franchiseeBookingDOList.get(count).getCityDO();
				}else{
					cityDO = new CityDO();
				}
				cityDO.setCityId(bookingTO.getCityId());

				ServiceDO serviceDO =null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					serviceDO=franchiseeBookingDOList.get(count).getServiceID();
				}else{
					serviceDO =  new ServiceDO();
				}
				if (!StringUtil.isEmpty(bookingTO.getServiceType())) {
					serviceDO.setServiceId(Integer.parseInt(bookingTO
							.getServiceType()));
				}

				DocumentDO documentDo = null;
				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					documentDo=franchiseeBookingDOList.get(count).getDocumentID();
				}else{
					documentDo = new DocumentDO();
				}
				documentDo.setDocumentId(bookingTO.getDoxType());


				//	ItemDO itemDo = new ItemDO();
				//itemDo.setItemId(bookingTO.getContents());
				ProductDO productDo = null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					productDo=franchiseeBookingDOList.get(count).getProductID();
				}else{
					productDo = new ProductDO();
				}

				String prodId = bookingTO.getMainProductType();
				if (prodId != null && !StringUtil.isEmpty(prodId)) {
					productDo.setProductId(Integer.parseInt(prodId));
					bookingDO.setProductID(productDo);
				}

				FranchiseeDO franchiseeDo = null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					franchiseeDo=franchiseeBookingDOList.get(count).getFranchiseeId();
				}else{
					franchiseeDo = new FranchiseeDO();
				}
				franchiseeDo.setFranchiseeId(bookingTO.getFranchiseeID());

				/*
				 * OfficeDO officeDO = new OfficeDO();
				 * officeDO.setOfficeId(bookingTO.getBookingZone());
				 */
				ChannelDO channelDO = null;

				if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
					channelDO=franchiseeBookingDOList.get(count).getChannelTypeID();
				}else{
					channelDO = new ChannelDO();
				}
				if (bookingTO.getChannelType() != null
						&& bookingTO.getChannelType().trim() != ApplicationConstants.EMPTY_STRING) {
					channelDO.setChannelTypeId(Integer.parseInt(bookingTO
							.getChannelType()));
				}

				// Populating DO for grid fields
				bookingDO.setConsignmentNumber(bookingTO.getConsignmentNumber());

				LOGGER.debug("###############CONSIGNMENT NUMBER#########===="+bookingTO.getConsignmentNumber());
				if (bookingTO.getPincodeId() != null
						&& bookingTO.getPincodeId() != 0) {
					bookingDO.setPincodeDO(pincodeDO);

				}
				if (bookingTO.getCityId() != null && bookingTO.getCityId() != 0) {
					bookingDO.setCityDO(cityDO);
				}

				bookingDO.setAreaNameLtDox(bookingTO.getAreaName());
				bookingDO.setFinalWeight(bookingTO.getFinalWeight());
				bookingDO.setVolumetricWght(bookingTO.getVolumtericWeight());
				bookingDO.setNoOfPieces(bookingTO.getNoOfPieces());
				bookingDO.setInvoiceValue(bookingTO.getInvoiceValue());
				bookingDO.setOpenMnfstNo(bookingTO.getOpenManifestNo());
				bookingDO.setSecurityPouchNo(bookingTO.getSecurityPouchNo());
				bookingDO.setAmount(bookingTO.getAmount());
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

				bookingDO.setDbServer(dbServerStatus);
				/* For operation Freedom */

				bookingDO.setOperationFreedom(bookingTO.getOperationsFreedom());


				if (!StringUtil.isEmpty(bookingTO.getServiceType())) {
					bookingDO.setServiceID(serviceDO);
				}
				if (bookingTO.getDoxType() != null && bookingTO.getDoxType() != 0) {
					bookingDO.setDocumentID(documentDo);
				}
				if (bookingTO.getContents() != null && bookingTO.getContents() != 0) {
					//bookingDO.setItemID(itemDo);
					CommodityDO commodityDO = new CommodityDO();
					commodityDO.setCommodityId(bookingTO.getContents());
					bookingDO.setDomesticCommodityId(commodityDO);
				}
				// populating Header information
				bookingDO.setBookingDate(bookingTO.getBookingDate());
				if (bookingTO.getFranchiseeID() != null
						&& bookingTO.getFranchiseeID() != 0) {
					bookingDO.setFranchiseeId(franchiseeDo);
				}
				bookingDO.setFranchiseeManifestNo(bookingTO
						.getFranchiseeManifestNo());
				if (!StringUtil.isEmpty(bookingTO.getBookingZone())) {
					bookingDO.setBookingDivisionID(bookingTO.getBookingZone());
				}
				if (!StringUtil.isEmpty(bookingTO.getChannelType())) {
					bookingDO.setChannelTypeID(channelDO);
				}
				// for populating child consignments
				bookingDO.setParentCnNumber(bookingTO.getParentCnNumber());
				// only for child CN

				if (bookingTO.getChargedWeight() != null && bookingTO.getChargedWeight()!=0) {
					bookingDO.setFinalWeight(bookingTO.getChargedWeight());
				}else{
					bookingDO.setFinalWeight(bookingTO.getActualWeight());
				}

				//System.out.println("After========="+bookingDO.getFinalWeight());

				if (bookingTO.getLengthInCms() != null) {
					bookingDO.setLength(bookingTO.getLengthInCms().floatValue());
				}
				if (bookingTO.getBreadthInCms() != null) {
					bookingDO.setBreadth(bookingTO.getBreadthInCms().floatValue());
				}
				if (bookingTO.getHeightInCms() != null) {
					bookingDO.setHeight(bookingTO.getHeightInCms().floatValue());
				}
				bookingDO.setActualWeight(bookingTO.getActualWeight());
				bookingDO.setDescription(bookingTO.getChildCnDesc());
				// populating COD FOD pop up fields in consignee,consignee address
				// and booking DOs
				Set address = null;
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;

				// create set of consignee address and set that to the consigneeDO
				// adresses.

				if (bookingTO.getAreaIdForConsignee() != null
						&& bookingTO.getAreaIdForConsignee() > 0) {

					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						address = new HashSet();
						if(franchiseeBookingDOList.get(count).getConsigneeID()!=null && franchiseeBookingDOList.get(count).getConsigneeID().getAddresses()!=null){
							Iterator iterator=(franchiseeBookingDOList.get(count).getConsigneeID().getAddresses()).iterator();
							consigneeAddressDO=(ConsigneeAddressDO)iterator.next();
							areaDO=consigneeAddressDO.getAreaDO();
							areaDO.setAreaId(bookingTO.getAreaIdForConsignee());
							consigneeAddressDO.setAreaDO(areaDO);
							consigneeAddressDO.setStreet1(bookingTO.getConsigneeAddress1());
							consigneeAddressDO.setStreet2(bookingTO.getConsigneeAddress2());
							consigneeAddressDO.setStreet3(bookingTO.getConsigneeAddress3());
							address.add(consigneeAddressDO);
						}

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

					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){

						consigneeDO=franchiseeBookingDOList.get(count).getConsigneeID();
					}else{
						consigneeDO = new ConsigneeDO();

					}


					/*if(bookingTO.getConsigneeInfoId() != null && bookingTO.getConsigneeInfoId() != 0){
						consigneeDO.setConsigneeId(bookingTO.getConsigneeInfoId());
					}*/
					consigneeDO.setFirstName(bookingTO.getConsigneeName());
					consigneeDO.setEmail(bookingTO.getConsigneeEmail());
					if (bookingTO.getConsigneePhone() != null) {
						consigneeDO.setPhone(bookingTO.getConsigneePhone());
					}
					consigneeDO.setAddresses(address);
					bookingDO.setConsigneeID(consigneeDO);


				}

				//consigneeDO.setBookingType(bookingTO.getBookingType());

				/* Set the consignor Info */

				Set consignorAddress = new HashSet();
				// create set of consignee address and set that to the consigneeDO
				// adresses.
				ConsignerAddressDO consignorAddressDO =null;
				PincodeDO consignorPincodeDO=null;
				CityDO consignorCityDO =null;
				if (bookingTO.getConsignorPincodeId() != null
						&& bookingTO.getConsignorPincodeId() > 0) {

					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						if(franchiseeBookingDOList.get(count).getConsignorID()!=null && franchiseeBookingDOList.get(count).getConsignorID().getConsignorAddresses()!=null){
							Iterator iterator=(franchiseeBookingDOList.get(count).getConsignorID().getConsignorAddresses()).iterator();
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
						}
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

					/*if(bookingTO.getConsigrAddrId() != null && bookingTO.getConsigrAddrId() != 0){
						consignorAddressDO.setConsigrAddrId(bookingTO.getConsigrAddrId());
					}*/
					consignorAddress.add(consignorAddressDO);

					ConsignerInfoDO consignorDO =null;

					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						consignorDO=franchiseeBookingDOList.get(count).getConsignorID();
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

				bookingDO.setFodAmount(bookingTO.getFodAmount());
				bookingDO.setCodRemarks(bookingTO.getCodFodRemarks());
				bookingDO.setModeOfCollection(bookingTO.getModeOfCollection());
				bookingDO.setInFavourOf(bookingTO.getInFavourOf());
				bookingDO.setInvoiceValue(bookingTO.getInvoiceValue());
				bookingDO.setCodAmount(bookingTO.getCodAmount());
				// set weighing type
				bookingDO.setWeighingType(bookingTO.getCaptureWeightType());
				// set consigneeDO in bookingDO

				// set vas product code selected in grid
				bookingDO.setVasProductCode(bookingTO.getProductCode());
				/* Either the user will capture CPDP or DP */

				// For CPDP
				if (bookingTO.getCustomerId() != null
						&& bookingTO.getCustomerId() != 0) {
					CustomerDO customerDO = null;
					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						customerDO=franchiseeBookingDOList.get(count).getCustomerId();
					}else{
						customerDO= new CustomerDO();

					}
					customerDO.setCustomerId(bookingTO.getCustomerId());
					bookingDO.setCustomerId(customerDO);
				}

				// for DP pop Up
				if (bookingTO.getDpId()!=null
						&& bookingTO.getDpId() >  0) {

					CustomerDO customerDO = null;
					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						customerDO=franchiseeBookingDOList.get(count).getCustomerId();
					}else{
						customerDO= new CustomerDO();
					}
					customerDO.setCustomerId(bookingTO.getDpId());
					bookingDO.setCallNumber(bookingTO.getCallNumber());
					bookingDO.setCustomerId(customerDO);
				}
				// bookingDO.setDirectPartyDetails(bookingTO.getDirectPartyDetails());
				if (bookingTO.getAgentId()!=null 
						&& bookingTO.getAgentId() != 0) {
					AgentDO agentDO = null;
					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						agentDO=franchiseeBookingDOList.get(count).getAgentID();
					}else{
						agentDO= new AgentDO();
					}
					agentDO.setAgentId(bookingTO.getAgentId());
					bookingDO.setAgentID(agentDO);
				}
				SimpleDateFormat sdfDate = new SimpleDateFormat(
						BookingConstants.TIME_FORMAT_HHMMSS);
				Date now = new Date();
				String strDate = sdfDate.format(now);

				bookingDO.setBookingTime(strDate);

				bookingDO.setConsgmntStatus(BookingConstants.BOOKED);
				bookingDO.setPassportDetails(bookingTO.getPassportDetails());
				bookingDO.setConsgmntType(null);
				// Create new consignment for Nondoxpaperwrk items and store the
				// items in BookingItemList against the booking id
				FranchiseeBookingDO paperWrkItemDO = null;
				if (bookingTO != null
						&& !StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
					paperWrkItemDO = getPaperWorkBookingDO(bookingTO);
					bookingDOList.add(paperWrkItemDO);
				}

				// save booking branch off id
				if (bookingTO.getOfficeID() != null && bookingTO.getOfficeID() != 0) {
					OfficeDO bookingBranchOffice= null;

					if(franchiseeBookingDOList!=null && franchiseeBookingDOList.size()>0){
						bookingBranchOffice=franchiseeBookingDOList.get(count).getOfficeID();
					}else{
						bookingBranchOffice = new OfficeDO();

					}
					bookingBranchOffice.setOfficeId(bookingTO.getOfficeID());
					bookingDO.setOfficeID(bookingBranchOffice);
				}
				// save customer ref no
				bookingDO.setCustRefNo(bookingTO.getReferenceNumber());

				//save insurance charge
				bookingDO.setIsInsured(bookingTO.getIsInsured());
				bookingDO.setInsuredBy(bookingTO.getInsuredBy());
				bookingDO.setInsuranceAmount(bookingTO.getInsuranceCharge());

				bookingDO.setLastTransModifiedDate(DateFormatterUtil
						.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				bookingDO.setNodeId(bookingTO.getNodeId());
				bookingDO.setDiFlag(BookingConstants.DIFLAG);
				bookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

				bookingDO.setReadByLocal(BookingConstants.READ_BY_LOCAL);

				//Rate Calculation component
				bookingDO.setTsAmount(bookingTO.getTsAmount());
				bookingDO.setServChrgAmt(bookingTO.getServiceChargeAmount());
				bookingDO.setRiskSurchgAmt(bookingTO.getRiskSurchargeAmount());
				bookingDO.setMiscCharge(bookingTO.getTotalMiscExpense());
				bookingDO.setDiplomaticOrRemoteAreaCharge(bookingTO.getDiplomaticOrRemoteAreaCharge());
				bookingDO.setFrieght(bookingTO.getTsAmount());


				/*try {
					bookingDO.setNodeId(ApplicatonUtils.getNodeId());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					//e#prntstcktrce
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					//e#prntstcktrce
				}*/
				// Adding the Do in the List
				// added by Hari to eliminate the Duplicates and Writing dtdc_f_booking_duplicate table  
				if(!duplicateFlag){
					LOGGER.debug("Genuine insert / Update");
					bookingDOList.add(bookingDO); //genuine insert / Update
				}else{

					//update in Duplicate Table
					LOGGER.debug("Duplicate Table insert");
					BookingDuplicateDO bookingDuplicateDO=new BookingDuplicateDO();

					try {
						PropertyUtils.copyProperties(bookingDuplicateDO, bookingDO);
						franchiseeBookingDAO.insertInBookingDuplicateTable(bookingDuplicateDO);
					} catch (Exception e) {
						LOGGER.error("FranchiseeBookingMDBServiceImpl::populateDOListFromTO::Exception occured:"
								+e.getMessage());
					}

				}



				//	count++;
				}
			}
			LOGGER.debug(":::::::::::populateDOListFromTO Ends:::::::::::::::");
			return bookingDOList;

	}

	/**
	 * Gets the sets the of child consignments.
	 *
	 * @param frBookingTo the fr booking to
	 * @param childCNData the child cn data
	 * @param parentConsignment the parent consignment
	 * @return the sets the of child consignments
	 */
	public List<FranchiseeBookingTO> getSetOfChildConsignments(FranchiseeBookingTO frBookingTo,
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
			if(!frBookingTo.getRequestType().equals("save")){
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
				FranchiseeBookingTO bookingTO = new FranchiseeBookingTO();
				bookingTO.setParentCnNumber(parentConsignment);
				bookingTO.setConsignmentNumber(childCNNumberList.get(i));
				if (actualWeightList != null && actualWeightList.size() > 0
						&& !StringUtil.isEmpty(actualWeightList.get(i))) {
					bookingTO.setActualWeight(Double
							.parseDouble(actualWeightList.get(i)));
				}
				if (lengthList != null && lengthList.size() > 0
						&& !StringUtil.isEmpty(lengthList.get(i))) {
					bookingTO
					.setLengthInCms(Float.parseFloat(lengthList.get(i)));
				}
				if (widthList != null && widthList.size() > 0
						&& !StringUtil.isEmpty(widthList.get(i))) {
					bookingTO
					.setBreadthInCms(Float.parseFloat(widthList.get(i)));
				}
				if (heightList != null && heightList.size() > 0
						&& !StringUtil.isEmpty(heightList.get(i))) {
					bookingTO
					.setHeightInCms(Float.parseFloat(heightList.get(i)));
				}
				if (volWeightList != null && volWeightList.size() > 0
						&& !StringUtil.isEmpty(volWeightList.get(i))) {
					bookingTO.setVolumtericWeight(Double
							.parseDouble(volWeightList.get(i)));
				}
				if (finalWeightList != null && finalWeightList.size() > 0
						&& !StringUtil.isEmpty(finalWeightList.get(i))) {
					bookingTO.setChargedWeight(Double
							.parseDouble(finalWeightList.get(i)));
				}
				if (descList != null && descList.size() > i) {
					bookingTO.setChildCnDesc(descList.get(i));
				}
				if(frBookingTo.getRequestType().equals(BookingConstants.UPDATE_AFTER_ENQUIRY)){
					if (childBookingIdList  != null && childBookingIdList.size() > 0
							&& !StringUtil.isEmpty(childBookingIdList.get(i)) && !childBookingIdList.get(i).equals("NA")) {
						bookingTO.setBookingId(Integer.valueOf(childBookingIdList.get(i)));
					}
				}

				// TO-DO Need to remove these fields later

				bookingTO.setEdd(new Date());
				bookingTO.setBookingDate(new Date());
				bookingTO.setInvoiceDate(new Date());

				// added by Hari as Manifest people requested

				bookingTO.setOfficeID(frBookingTo.getOfficeID());
				bookingTO.setCityId(frBookingTo.getCityId());
				bookingTO.setPincodeId(frBookingTo.getPincodeId());
				bookingTO.setServiceType(frBookingTo.getServiceType());
				bookingTO.setDoxType(frBookingTo.getDoxType());
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
	private FranchiseeBookingDO getPaperWorkBookingDO(BookingTO bookingTO) {

		FranchiseeBookingDO bookingDO = new FranchiseeBookingDO();

		bookingDO.setConsignmentNumber(bookingTO.getPaperWrkCn());
		bookingDO.setParentCnNumber(bookingTO.getConsignmentNumber());
		ServiceDO serviceDO = new ServiceDO();
		DocumentDO documentDo = new DocumentDO();
		try {
			Integer serviceId = franchiseeBookingDAO.getIdForExpressService();
			serviceDO.setServiceId(serviceId);
			Integer doxTypeId = franchiseeBookingDAO.getIdForDoxType();
			//query to get pincodeId and cityId of destination branch
			List<Object []> pincodeAndCity = franchiseeBookingDAO.getDestBranchPincodeAndCity(bookingTO.getPinCode());
			if(pincodeAndCity!=null && !pincodeAndCity.isEmpty()){
				Object[] result = pincodeAndCity.get(0);
				CityDO city = null;
				PincodeDO pincode= null;
				if(result!=null && result.length > 1){

					Integer pincodeId = (Integer )result[0];
					Integer cityId = (Integer )result[1];
					pincode =new PincodeDO();
					pincode.setPincodeId(pincodeId);
					city = new CityDO();
					city.setCityId(cityId);
				}
				bookingDO.setCityDO(city);			
				bookingDO.setPincodeDO(pincode);
			}
			documentDo.setDocumentId(doxTypeId);
			bookingDO.setServiceID(serviceDO);
			bookingDO.setDocumentID(documentDo);
			bookingDO.setAmount(new Double(0));
			bookingDO.setFinalWeight(bookingTO.getPaperWrkWeight());
			if (!StringUtil.isEmpty(bookingTO.getNoOfPaperWrkItems())) {
				bookingDO.setNoOfPieces(Integer.parseInt(bookingTO
						.getNoOfPaperWrkItems()));
			}
			if(bookingTO.getPaperWrkBookingId() != null &&
					bookingTO.getPaperWrkBookingId() != 0){
				bookingDO.setBookingId(bookingTO.getPaperWrkBookingId());
			}
			bookingDO.setBookingDate(bookingTO.getBookingDate());

			List<BookingItemListDO> bookingItemsList = franchiseeBookingDAO
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
			//set the consignment type as NonDox PaperWork
			bookingDO.setConsgmntType(BookingConstants.NONDOX_PAPERWORK);
			OfficeDO officeDo = new OfficeDO();
			officeDo.setOfficeId(bookingTO.getOfficeID());
			bookingDO.setOfficeID(officeDo);

		} catch (Exception e) {
			LOGGER.error("FranchiseeBookingMDBServiceImpl::getPaperWorkBookingDO::Exception occured:"
					+e.getMessage());
		}
		return bookingDO;
	}

	/**
	 * Gets the franchisee booking dao.
	 * 
	 * @return the franchiseeBookingDAO
	 */
	public FranchiseeBookingMDBDAO getFranchiseeBookingDAO() {
		return franchiseeBookingDAO;
	}

	/**
	 * Sets the franchisee booking dao.
	 * 
	 * @param franchiseeBookingDAO
	 *            the franchiseeBookingDAO to set
	 */
	public void setFranchiseeBookingDAO(
			FranchiseeBookingMDBDAO franchiseeBookingDAO) {
		this.franchiseeBookingDAO = franchiseeBookingDAO;
	}
}
