package src.com.dtdc.mdbServices.delivery;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.DeliveryManifestConstants;
import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;
import src.com.dtdc.mdbServices.booking.MnpBookingMDBServiceImpl;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.master.product.ServiceMappingDO;
import com.dtdc.domain.transaction.delivery.DeliveryManifestBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryManifestMDBValidator.
 */
public class DeliveryManifestMDBValidator {
	
	/** logger. */
	private final static Logger logger = LoggerFactory
	.getLogger(MnpBookingMDBServiceImpl.class);
	/**
	 * Validate consignment.
	 *
	 * @param bookingDO the booking do
	 * @param consigneeAddress the consignee address
	 * @param consgNum the consg num
	 * @param duplicateCongntSameDay the duplicate congnt same day
	 * @param ctbsApplicationMDBDAO the ctbs application mdbdao
	 * @return the string builder
	 * @throws CGSystemException the cG system exception
	 */
	public static StringBuilder validateConsignment(
			DeliveryManifestBookingDO bookingDO,
			ConsigneeAddressDO consigneeAddress, String consgNum,
			String duplicateCongntSameDay, CTBSApplicationMDBDAO ctbsApplicationMDBDAO)
			throws CGSystemException {
		StringBuilder bookingDetails = new StringBuilder();
		String buildingName = "";
		String buildingBlock = "";
		String street1 = "";
		String street2 = "";
		String street3 = "";
		String areaName = "";
		String citiName = "";
		String stateName = "";
		String pinCode = "";
		String selectedProductCode = "";
		Integer selectedProductID = 0;
		String firstName = "";
		String lastName = "";
		String surName = "";
		ProductDO product = null;
		ConsigneeDO consignee = null;
		DocumentDO document = null;
		ServiceDO service = null;
		int documentID = 0;
		int serviceID = 0;
		PincodeDO destPincode = null;
		OfficeDO destOffice = null;
		int destOffId = 0;
		AreaDO area = null;
		String areaType = "";
		String serviceType = "";
		ServiceMappingDO serviceMapping = null;
		String consgDeliveryOnHoliday = "";
		String isConsgDevOnHoliday = "";
		try {
			ResourceBundle rb = ResourceBundle
					.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			document = bookingDO.getDocument();
			service = bookingDO.getService();
			destPincode = bookingDO.getDestPinCode();
			if (document != null) {
				documentID = document.getDocumentId();
			}

			if (consigneeAddress != null) {
				buildingName = consigneeAddress.getBuildingName();
				buildingBlock = consigneeAddress.getBuildingBlock();
				street1 = consigneeAddress.getStreet1();
				street2 = consigneeAddress.getStreet2();
				street3 = consigneeAddress.getStreet3();
				area = consigneeAddress.getAreaDO();
				if (area != null) {
					CityDO city = area.getCity();
					StateDO state = city.getDistrict().getState();
					areaName = area.getAreaName();
					citiName = city.getCityName();
					stateName = state.getStateName();
				}
				consignee = consigneeAddress.getConsignee();
			}

			if (consignee != null) {
				firstName = consignee.getFirstName();
				lastName = consignee.getLastName();
				surName = consignee.getSurName();
			}
			// For Service
			if (service != null) {
				serviceType = service.getServiceType();
				serviceID = service.getServiceId();
				product = service.getProductDO();
				serviceMapping = ctbsApplicationMDBDAO
						.getServiceMappings(serviceID);
				if (serviceMapping != null) {
					consgDeliveryOnHoliday = serviceMapping
							.getSundayServicable();
					if (StringUtils.isNotEmpty(consgDeliveryOnHoliday)) {					
						Calendar todayCal = Calendar.getInstance();					
						int iDay = todayCal.get(Calendar.DAY_OF_WEEK);
						if (iDay == 7) {
							isConsgDevOnHoliday = "CONSGDLVONHOLYDAY";
						}
					}
				}
				if (product != null) {
					selectedProductCode = product.getProductCode();
					selectedProductID = product.getProductId();
				} else {
					bookingDetails
							.append(rb
									.getString(ManifestConstant.INVALID_PRODUCT_SERVICE));
				}
			}
			// TODO commented below code because without booking also allowing to prepare FDM			
			/*else {
				bookingDetails.append(rb
						.getString(ManifestConstant.INVALID_PRODUCT_SERVICE));
				return bookingDetails;
			}*/
			
			if (destPincode != null) {
				pinCode = destPincode.getPincode();
				destOffice = destPincode.getOffice();
				if (destOffice != null) {
					area = destOffice.getArea();
					areaType = area.getAreaType();
				}
				if (destOffice != null) {
					destOffId = destOffice.getOfficeId();
				}
			}

			if (!StringUtil.isEmpty(consgNum)) {
				/*
				 * if (count > 0) { bookingDetails .append(rb
				 * .getString(DeliveryManifestConstants.DUPLICATE_CONSIGNMENT));
				 * return bookingDetails; }
				 */
				bookingDetails.append(buildingName);
				bookingDetails.append(",");
				bookingDetails.append(buildingBlock);
				bookingDetails.append(",");
				bookingDetails.append(street1);
				bookingDetails.append(",");
				bookingDetails.append(street2);
				bookingDetails.append(",");
				bookingDetails.append(street3);
				bookingDetails.append(",");
				bookingDetails.append(areaName);
				bookingDetails.append(",");
				bookingDetails.append(citiName);
				bookingDetails.append(",");
				bookingDetails.append(stateName);
				bookingDetails.append(",");
				bookingDetails.append(pinCode);
				bookingDetails.append(",");
				bookingDetails.append(selectedProductCode);
				bookingDetails.append(",");
				bookingDetails.append(selectedProductID);
				bookingDetails.append(",");
				bookingDetails.append(firstName);
				bookingDetails.append(" ");
				bookingDetails.append(lastName);
				bookingDetails.append(" ");
				bookingDetails.append(surName);
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getBookedWeight());
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getNuOfPieces());
				bookingDetails.append(",");
				bookingDetails.append(documentID);
				bookingDetails.append(",");
				bookingDetails.append(serviceID);
				bookingDetails.append(",");
				bookingDetails.append("SUCCESS");
				bookingDetails.append(",");
				bookingDetails.append(duplicateCongntSameDay);
				bookingDetails.append(",");
				bookingDetails.append(destOffId);
				bookingDetails.append(",");
				bookingDetails.append(areaType);
				bookingDetails.append(",");
				bookingDetails.append(serviceType);
				bookingDetails.append(",");
				bookingDetails.append(isConsgDevOnHoliday);
				// set volumetric weight
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getLengthInCms());
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getBreadthInCms());
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getHeightInCms());
				bookingDetails.append(",");
				bookingDetails.append(bookingDO.getVolumtericWeight());				
				return bookingDetails;
			}

		} /*
		 * else if (StringUtil.equals(DeliveryManifestConstants.WEIGHT,
		 * validationType)) { if (!StringUtil.isEmpty(valueForValidation)) {
		 * double weightOnDelivery = Double .parseDouble(valueForValidation);
		 * double weightOnBooking = bookingDO.getBookedWeight(); double
		 * weightOnTolerance = weightOnBooking -
		 * DeliveryManifestConstants.WEIGHT_TOLERANCE; if (weightOnDelivery >=
		 * weightOnTolerance && weightOnDelivery <= weightOnTolerance) {
		 * bookingDetails .append(DeliveryManifestConstants.INVALID_WEIGHT);
		 * return bookingDetails; }
		 * 
		 * } } else if (StringUtil.equals( DeliveryManifestConstants.NOOFPIECES,
		 * validationType)) { if (!StringUtil.isEmpty(valueForValidation)) { int
		 * noOfPieces = Integer.parseInt(valueForValidation); if (noOfPieces !=
		 * bookingDO.getNuOfPieces()) { bookingDetails
		 * .append(DeliveryManifestConstants.INVALID_NOOFPIECES); return
		 * bookingDetails; } } }
		 */

		catch (Exception e) {
			logger.error("DeliveryManifestMDBValidator::validateConsignment::Exception occured:"
					+e.getMessage());
		}
		return null;
	}

	

	/**
	 * Validate manifest number.
	 *
	 * @param manifestNum the manifest num
	 * @param fdmHandOver the fdm hand over
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unused")
	public String validateManifestNumber(String manifestNum, String fdmHandOver)
			throws CGSystemException {
		StringBuilder errorMessagesFomAjax = new StringBuilder();
		try {
			ResourceBundle rb = ResourceBundle
					.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			if (!StringUtil.isEmpty(fdmHandOver)) {
				errorMessagesFomAjax.append(fdmHandOver);
				return errorMessagesFomAjax.toString();
			} else {
				errorMessagesFomAjax
						.append(rb
								.getString(DeliveryManifestConstants.INVALID_FDMNUMBER));

				return errorMessagesFomAjax.toString();
			}
		} catch (Exception e) {
			logger.error("DeliveryManifestMDBValidator::validateManifestNumber::Exception occured:"
					+e.getMessage());
		}
		return null;

	}
}
