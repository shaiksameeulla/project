package src.com.dtdc.mdbServices.booking;

/*
 * @author rajmanda
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.booking.SplCustomerBookingDAO;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ChannelDO;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.booking.specialcustomer.SplCustomerBookingDO;
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
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.to.booking.specialcustomer.SplCustomerBookingTO;

// TODO: Auto-generated Javadoc
/**
 * The Class SplCustomerBookingServiceImpl.
 */
public class SplCustomerBookingServiceImpl implements SplCustomerBookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(SplCustomerBookingServiceImpl.class);


	/** The spl customer booking dao. */
	private SplCustomerBookingDAO splCustomerBookingDAO;

	/**
	 * Gets the spl customer booking dao.
	 * 
	 * @return the spl customer booking dao
	 */
	public SplCustomerBookingDAO getSplCustomerBookingDAO() {
		return splCustomerBookingDAO;
	}

	/**
	 * Sets the spl customer booking dao.
	 * 
	 * @param splCustomerBookingDAO
	 *            the new spl customer booking dao
	 */
	public void setSplCustomerBookingDAO(
			SplCustomerBookingDAO splCustomerBookingDAO) {
		this.splCustomerBookingDAO = splCustomerBookingDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.SplCustomerBookingService#saveSplCustBookingDetails(CGBaseTO)
	 */
	@Override
	public boolean saveSplCustBookingDetails(CGBaseTO bookingTO)
	throws CGSystemException,CGBusinessException {
		return saveSplCustBookingDetails((List<SplCustomerBookingTO>)bookingTO.getBaseList());
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.booking.SplCustomerBookingService#saveSplCustBookingDetails(List)
	 */
	@Override
	public boolean saveSplCustBookingDetails(List<SplCustomerBookingTO> splCustomerBookingTOList)
	throws CGSystemException,CGBusinessException {
		List<SplCustomerBookingDO> splCustomerBookingDOList = null;
		boolean bookingStatus = false;
		if (splCustomerBookingTOList != null && !splCustomerBookingTOList.isEmpty()
				&& splCustomerBookingTOList.size() != 0) {
			splCustomerBookingDOList = convertTOtoDO(splCustomerBookingTOList);
		}
		if (splCustomerBookingDOList != null && !splCustomerBookingDOList.isEmpty()
				&& splCustomerBookingDOList.size() != 0) {
			bookingStatus = splCustomerBookingDAO.saveSplCustBookingDetails(splCustomerBookingDOList);
		}
		return bookingStatus;
	}

	/**
	 * Convert t oto do.
	 *
	 * @param splCustomerBookingTOList the spl customer booking to list
	 * @return the list
	 */
	public List<SplCustomerBookingDO> convertTOtoDO(List<SplCustomerBookingTO> splCustomerBookingTOList){
		List<SplCustomerBookingDO> splCustomerBookingDOList =null;
		for(SplCustomerBookingTO splCustomerBookingTO : splCustomerBookingTOList){
			SplCustomerBookingDO splCustomerBookingDO = new SplCustomerBookingDO();
			if (splCustomerBookingTO != null) {
				//OfficeDO office = splCustomerBookingTO.getBookingDivisionID();
				if (!StringUtil.isEmpty(splCustomerBookingTO.getBookingZone())) {
					splCustomerBookingDO.setBookingDivisionID(splCustomerBookingTO.getBookingZone());
				}
				splCustomerBookingDO.setBookingId(splCustomerBookingTO.getBookingId());

				// for DBsync
				//					splCustomerBookingTO.setCaptureWeightType(splCustomerBookingTO.getWeighingType());
				splCustomerBookingDO.setDbServer(splCustomerBookingTO.getDbServer());
				splCustomerBookingDO.setUserId(splCustomerBookingTO.getUserId());
				splCustomerBookingDO.setNodeId(splCustomerBookingTO.getNodeId());
				splCustomerBookingDO.setBookingDate(splCustomerBookingTO.getBookingDate());
				if (splCustomerBookingTO.getDocumentId() != null
						&& splCustomerBookingTO.getDocumentId() > 0) {
					DocumentDO documentDo = new DocumentDO();
					documentDo.setDocumentId(splCustomerBookingTO.getDocumentId());
					splCustomerBookingDO.setDocumentID(documentDo);
				}
				//					splCustomerBookingTO.setServiceTaxAmount(splCustomerBookingTO.getServiceTax());

				// splCustomerBookingTO.getBookingDivisionID().getOfficeId();
				if (splCustomerBookingTO.getChannelTypeId() != null
						&& splCustomerBookingTO.getChannelTypeId() > 0) {
					ChannelDO channelDo = new ChannelDO();
					channelDo.setChannelTypeId(splCustomerBookingTO.getChannelTypeId());
					splCustomerBookingDO
					.setChannelTypeID(channelDo);
				}

				if (splCustomerBookingTO.getProductId() != null
						&& splCustomerBookingTO.getProductId() > 0) {
					ProductDO productDo = new ProductDO();
					productDo.setProductId(splCustomerBookingTO.getProductId());
					splCustomerBookingDO
					.setProductID(productDo);
				}
				//splCustomerBookingTO.setBranchCode(splCustomerBookingTO.getOfficeID().getOfficeCode());
				//splCustomerBookingTO.setCashBookingBranchDetails(splCustomerBookingTO.getOfficeID().getOfficeName());
				splCustomerBookingDO.setSecurityPouchNo(splCustomerBookingTO.getSecurityPouchNo());
				splCustomerBookingDO.setSelectScheme(splCustomerBookingTO.getSelectScheme());
				splCustomerBookingDO.setNoOfPieces(splCustomerBookingTO.getNoOfPieces());
				splCustomerBookingDO.setCreateChildCn(splCustomerBookingTO.getCreateChildCn());
				splCustomerBookingDO.setInvoiceValue(splCustomerBookingTO.getInvoiceValue());
				if(splCustomerBookingTO.getCustomerId() != null
						&& splCustomerBookingTO.getCustomerId() > 0){
					CustomerDO customer = new CustomerDO();
					customer.setCustomerId(splCustomerBookingTO.getCustomerId());
					splCustomerBookingDO.setCustomerId(customer);
				}

				splCustomerBookingDO.setConsignmentNumber(splCustomerBookingTO
						.getConsignmentNumber());

				if (splCustomerBookingTO.getExpectedDlvDate() != null) {
					splCustomerBookingDO.setExpectedDlvDate(splCustomerBookingTO.getExpectedDlvDate());
				}
				if(splCustomerBookingTO.getPincodeId() != null 
						&& splCustomerBookingTO.getPincodeId() > 0){
					PincodeDO pincodeDO = new PincodeDO();
					pincodeDO.setPincodeId(splCustomerBookingTO.getPincodeId());
					splCustomerBookingDO.setPincodeDO(pincodeDO) ;
				}

				if(splCustomerBookingTO.getCityId() != null 
						&& splCustomerBookingTO.getCityId() > 0){
					CityDO cityDO = new CityDO();
					cityDO.setCityId(splCustomerBookingTO.getCityId());
					splCustomerBookingDO.setCityDO(cityDO);
				}


				splCustomerBookingDO.setAmount(splCustomerBookingTO.getAmount());

				splCustomerBookingDO.setDiscountInPerc(splCustomerBookingTO.getDiscountInPerc());
				splCustomerBookingDO.setAmount(splCustomerBookingTO.getAmount());

				if(splCustomerBookingTO.getServiceId() !=null
						&& splCustomerBookingTO.getServiceId() > 0){
					ServiceDO serviceDo = new ServiceDO();
					serviceDo.setServiceId(splCustomerBookingTO.getServiceId());
					splCustomerBookingDO.setServiceID(serviceDo);
				}
				// splCustomerBookingTO.setCashBookingBranchDetails(splCustomerBookingTO.getCashBookingBranchDetails());
				if (splCustomerBookingTO.getEmployeeIdForPickupBoyId() != null
						&& splCustomerBookingTO.getEmployeeIdForPickupBoyId() > 0) {
					EmployeeDO employeeDo = new EmployeeDO();
					employeeDo.setEmployeeId(splCustomerBookingTO.getEmployeeIdForPickupBoyId());
					splCustomerBookingDO.setEmployeeIdForPickupBoyId(employeeDo);
				}
				if (splCustomerBookingTO.getServiceTax() != null) {
					splCustomerBookingDO.setServiceTax(splCustomerBookingTO.getServiceTax());
				}
				if (splCustomerBookingTO.getCustomerId() != null
						&& splCustomerBookingTO.getCustomerId() > 0) {
					CustomerDO customerDo = new CustomerDO();
					customerDo.setCustomerId(splCustomerBookingTO.getCustomerId());
					splCustomerBookingDO.setCustomerId(customerDo);
				}

				splCustomerBookingDO.setTaxableAmount(splCustomerBookingTO.getTaxableAmount());
				splCustomerBookingDO.setTaxPayable(splCustomerBookingTO.getTaxPayable());
				splCustomerBookingDO.setDiscount(splCustomerBookingTO.getDiscount());
				splCustomerBookingDO.setPaid(splCustomerBookingTO.getPaid());
				splCustomerBookingDO.setLength(splCustomerBookingTO.getLength());
				splCustomerBookingDO.setActualWeight(splCustomerBookingTO.getActualWeight());
				splCustomerBookingDO.setBreadth(splCustomerBookingTO.getBreadth());
				if(splCustomerBookingTO.getCashCollectionCenterOfcId() != null
						&& splCustomerBookingTO.getCashCollectionCenterOfcId() > 0){
					OfficeDO officeDo = new OfficeDO();
					officeDo.setOfficeId(splCustomerBookingTO.getCashCollectionCenterOfcId());
					splCustomerBookingDO.setCashCollectionCenter(officeDo);
				}
				splCustomerBookingDO.setVolumetricWght(splCustomerBookingTO.getVolumetricWght());
				splCustomerBookingDO.setHeight(splCustomerBookingTO.getHeight());
				splCustomerBookingDO.setFinalWeight(splCustomerBookingTO.getFinalWeight());
				splCustomerBookingDO.setIsInsured(splCustomerBookingTO.getIsInsured());
				splCustomerBookingDO.setFodAmount(splCustomerBookingTO.getFodAmount());
				splCustomerBookingDO.setInsuredBy(splCustomerBookingTO.getInsuredBy());
				splCustomerBookingDO.setPaymentMode(splCustomerBookingTO.getPaymentMode());
				splCustomerBookingDO.setInvoiceValue(splCustomerBookingTO.getInvoiceValue());
				splCustomerBookingDO.setInFavourOf(splCustomerBookingTO.getInFavourOf());

				Set address = null;
				ConsigneeAddressDO consigneeAddressDO = null;
				AreaDO areaDO =null;

				// create set of consignee address and set that to the consigneeDO
				// adresses.

				if (splCustomerBookingTO.getAreaIdForConsignee() != null
						&& splCustomerBookingTO.getAreaIdForConsignee() > 0) {
					consigneeAddressDO=new ConsigneeAddressDO();
					areaDO= new AreaDO();
					address = new HashSet();
					areaDO.setAreaId(splCustomerBookingTO.getAreaIdForConsignee());
					consigneeAddressDO.setAreaDO(areaDO);
					consigneeAddressDO.setStreet1(splCustomerBookingTO.getConsigneeAddress1());
					consigneeAddressDO.setStreet2(splCustomerBookingTO.getConsigneeAddress2());
					consigneeAddressDO.setStreet3(splCustomerBookingTO.getConsigneeAddress3());
					if(splCustomerBookingTO.getConsigneeAddressId() != null 
							&& splCustomerBookingTO.getConsigneeAddressId() != 0){
						consigneeAddressDO.setConsgAddrId(splCustomerBookingTO.getConsigneeAddressId());
					}
					address.add(consigneeAddressDO);

					ConsigneeDO consigneeDO = null;

					if(splCustomerBookingTO.getConsigneeId() !=null 
							&& splCustomerBookingTO.getConsigneeId()>0){
						consigneeDO.setConsigneeId(splCustomerBookingTO.getConsigneeId());
					}else{
						consigneeDO = new ConsigneeDO();
					}

					/*if(bookingTO.getConsigneeInfoId() != null && bookingTO.getConsigneeInfoId() != 0){
							consigneeDO.setConsigneeId(bookingTO.getConsigneeInfoId());
						}*/
					consigneeDO.setFirstName(splCustomerBookingTO.getConsigneeName());
					consigneeDO.setEmail(splCustomerBookingTO.getConsigneeEmail());
					if (splCustomerBookingTO.getConsigneePhone() != null) {
						consigneeDO.setPhone(splCustomerBookingTO.getConsigneePhone().toString());
					}
					consigneeDO.setAddresses(address);
					splCustomerBookingDO.setConsigneeID(consigneeDO);

				}

				//consigneeDO.setBookingType(bookingTO.getBookingType());

				/* Set the consignor Info */

				Set consignorAddress = new HashSet();
				// create set of consignee address and set that to the consigneeDO
				// adresses.
				ConsignerAddressDO consignorAddressDO =null;
				PincodeDO consignorPincodeDO=null;
				CityDO consignorCityDO =null;
				if (splCustomerBookingTO.getConsignorPincodeId() != null
						&& splCustomerBookingTO.getConsignorPincodeId() > 0) {

					consignorAddressDO= new ConsignerAddressDO();
					consignorPincodeDO = new PincodeDO();
					consignorPincodeDO.setPincodeId(splCustomerBookingTO.getConsignorPincodeId());
					consignorAddressDO.setPincodeId(consignorPincodeDO);

					consignorAddressDO.setStreet1(splCustomerBookingTO.getConsignorAddress1());
					consignorAddressDO.setStreet2(splCustomerBookingTO.getConsignorAddress2());
					consignorAddressDO.setStreet3(splCustomerBookingTO.getConsignorAddress3());

					if (splCustomerBookingTO.getConsignerCityId() != null
							&& splCustomerBookingTO.getConsignerCityId() > 0) {
						consignorCityDO = new CityDO();
						consignorCityDO.setCityId(splCustomerBookingTO.getConsignerCityId());
						consignorAddressDO.setCity(consignorCityDO);
					}


					/*if(bookingTO.getConsigrAddrId() != null && bookingTO.getConsigrAddrId() != 0){
							consignorAddressDO.setConsigrAddrId(bookingTO.getConsigrAddrId());
						}*/
					consignorAddress.add(consignorAddressDO);

					ConsignerInfoDO consignorDO =null;

					if(splCustomerBookingTO.getConsignerInfoId()!=null 
							&& splCustomerBookingTO.getConsignerInfoId() >0){
						consignorDO = new ConsignerInfoDO();
						consignorDO.setConsignerId(splCustomerBookingTO.getConsignerInfoId());
					}else{
						consignorDO= new ConsignerInfoDO();
					}

					/*if(bookingTO.getConsignerInfoId() != null && bookingTO.getConsignerInfoId() != 0){
							consignorDO.setConsignerId(bookingTO.getConsignerInfoId());
						}*/
					if(!StringUtil.isEmpty(splCustomerBookingTO.getConsignorName())){
						consignorDO.setFirstName(splCustomerBookingTO.getConsignorName());
					}
					if (!StringUtil.isEmpty(splCustomerBookingTO.getConsignorPhone())) {
						consignorDO.setPhone(splCustomerBookingTO.getConsignorPhone());
					}
					if(!StringUtil.isEmpty(splCustomerBookingTO.getConsignorEmail())){
						consignorDO.setEmail(splCustomerBookingTO.getConsignorEmail());
					}
					consignorDO.setConsignorAddresses(consignorAddress);
					splCustomerBookingDO.setConsignorID(consignorDO);
				}




				/*splCustomerBookingTO.setFreight(splCustomerBookingTO.getFrieght());
					splCustomerBookingTO.setRiskSurchargeAmount(splCustomerBookingTO
							.getRiskSurchgAmt());
					splCustomerBookingTO
							.setServiceChargeAmount(splCustomerBookingTO.getServChrgAmt());
					splCustomerBookingTO.setCashRemarks(splCustomerBookingTO.getRemarks());*/

				// Remote Delivery Pop up data -- begin
				/*splCustomerBookingTO.setRemoteExtraDlvRemarks(splCustomerBookingTO.getExtDlvRemarks());
					splCustomerBookingTO.setRemoteDeliveryRemarks(splCustomerBookingTO.getRemoteDeliveryRemarks());
					if(!StringUtil.isEmpty(splCustomerBookingTO.getExtrDlvCharges()) && !splCustomerBookingTO.getExtrDlvCharges().trim().equalsIgnoreCase("null") ){
						splCustomerBookingTO.setRemoteDeliveryExtraDeliveryCharges(new Double(splCustomerBookingTO.getExtrDlvCharges()));
					}
					splCustomerBookingTO.setAreaName(splCustomerBookingTO.getAreaNameLtDox());
					splCustomerBookingTO.setRemoteDeliveryDoxAmount(splCustomerBookingTO.getDoxAmount());
					splCustomerBookingTO.setRemoteDeliveryNonDoxAmount(splCustomerBookingTO.getNonDoxAmount());*/

				// Remote Delivery Pop up data -- end


				splCustomerBookingDO
				.setPassportDetails(splCustomerBookingTO.getPassportDetails());
				splCustomerBookingDO.setProductCode(splCustomerBookingTO.getVasProductCode());

				if(splCustomerBookingTO.getOfficeId()!=null 
						&& splCustomerBookingTO.getOfficeId() >0){
					OfficeDO bookingBranch= new OfficeDO();
					bookingBranch.setOfficeId(splCustomerBookingTO.getOfficeId());
					splCustomerBookingDO.setOfficeID(bookingBranch);
				}

				splCustomerBookingDO.setPrioritySticker(splCustomerBookingTO.getPrioritySticker());
				splCustomerBookingDO.setPickUpTime(splCustomerBookingTO.getPickUpTime());
				splCustomerBookingDO.setPaymentMode(splCustomerBookingTO.getPaymentMode());
				splCustomerBookingDO.setCustomerIndustry(splCustomerBookingTO.getCustomerIndustry());
				splCustomerBookingDO.setCustRefNo(splCustomerBookingTO.getReferenceNumber());

				splCustomerBookingDO.setLastTransModifiedDate(DateFormatterUtil
						.getDateFromStringDDMMYYY(DateFormatterUtil.todayDate()));
				splCustomerBookingDO.setNodeId(splCustomerBookingTO.getNodeId());
				splCustomerBookingDO.setDiFlag(BookingConstants.DIFLAG);
			}

			splCustomerBookingDOList.add(splCustomerBookingDO);				

			//}
			//else{
			//	return null;
			//	}
		}
		if(splCustomerBookingDOList.size() == 0) {
			return null;
		} else {
			return splCustomerBookingDOList;
		}
	}

}
