package src.com.dtdc.mdbServices.manifest;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.transaction.manifest.ManifestBookingDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ManifestMDBValidator.
 */
public class ManifestMDBValidator {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(ManifestMDBValidator.class);

	/**
	 * Consignment validations.
	 *
	 * @param booking the booking
	 * @param productCode the product code
	 * @param selectedMode the selected mode
	 * @param productCodeOnConsignment the product code on consignment
	 * @return the string builder
	 * @throws Exception the exception
	 */
	public static StringBuilder consignmentValidations(
			ManifestBookingDO booking, String productCode, ModeDO selectedMode,
			String productCodeOnConsignment) throws Exception {
		StringBuilder bookingDetails = null;
		ProductDO bookedProduct = null;
		ServiceDO service = null;
		ModeDO mode = null;
		Set<ModeDO> modes = null;
		try {
			ResourceBundle rb = ResourceBundle
			.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			bookingDetails = new StringBuilder();
			if (booking == null) {
				bookingDetails.append(ManifestConstant.ERROR_FLAG);
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(rb
						.getString(ManifestConstant.INVALID_CONSIGNMENT));
				return bookingDetails;
			} else {
				bookedProduct = booking.getProduct();
				// manifestValidations(selectedProduct.getProductCode(),selectedMode.getModeCode(),
				// bookedProduct, mode);
				// Validation for products PEP / PTP / Normal
				if (StringUtils.equals(productCode,
						ManifestConstant.PRODUCT_PEP_TYPE)
						|| StringUtils.equals(productCode,
								ManifestConstant.PRODUCT_PTP_TYPE)) {
					if (!(StringUtils.equals(productCode,
							productCodeOnConsignment))) {
						bookingDetails.append(ManifestConstant.ERROR_FLAG);
						bookingDetails.append(ManifestConstant.COMMA);
						bookingDetails
						.append(rb
								.getString(ManifestConstant.PRODUCT_VALIDATION));
						return bookingDetails;
					}
				} else {
					String consgNumberSeries = booking.getConsignmentNumber()
					.substring(0, 1);
					if (StringUtils.equals(consgNumberSeries,
							ManifestConstant.CONSG_NUMBER_E_SERIES)
							|| StringUtils.equals(consgNumberSeries,
									ManifestConstant.CONSG_NUMBER_V_SERIES)) {
						bookingDetails.append(ManifestConstant.ERROR_FLAG);
						bookingDetails.append(ManifestConstant.COMMA);
						bookingDetails
						.append(rb
								.getString(ManifestConstant.PRODUCT_VALIDATION));
						return bookingDetails;
					}
				}
				// calling Mode Validation
				if (StringUtils.equals(productCode,
						ManifestConstant.PRODUCT_NORMAL_TYPE)) {
					service = booking.getService();
					if (service != null) {
						modes = service.getModes();
						if (modes != null) {
							if (modes.iterator().hasNext()) {
								mode = modes.iterator().next();
							}
						}

						if (mode != null) {
							boolean isValidMode = manifestModeValidations(
									selectedMode.getModeCode(), mode);
							if (isValidMode) {
								bookingDetails
								.append(ManifestConstant.ERROR_FLAG);
								bookingDetails.append(ManifestConstant.COMMA);
								bookingDetails
								.append(rb
										.getString(ManifestConstant.MODE_VALIDATION));
								return bookingDetails;
							}
						} else {
							bookingDetails.append(ManifestConstant.ERROR_FLAG);
							bookingDetails.append(ManifestConstant.COMMA);
							bookingDetails.append(rb
									.getString(ManifestConstant.INVALID_MODE));
							return bookingDetails;
						}
					}
				}

				bookingDetails.append(booking.getConsignmentNumber());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(booking.getBookedWeight());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(booking.getNuOfPieces());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(booking.getDestCity().getCityName());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(booking.getDestPinCode().getPincode());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(booking.getDocument().getDocumentId());
				bookingDetails.append(ManifestConstant.COMMA);
				bookingDetails.append(bookedProduct.getProductId());
			}

		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::consignmentValidations::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return bookingDetails;
	}

	/**
	 * Manifest mode validations.
	 *
	 * @param modeCode the mode code
	 * @param mode the mode
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean manifestModeValidations(String modeCode, ModeDO mode)
	throws Exception {
		boolean isvalidMode = Boolean.FALSE;
		String modeCodeFromConsignment = "";
		try {
			// Mode Validations
			modeCodeFromConsignment = mode.getModeCode();
			/*
			 * if (StringUtils.equals(ManifestConstant.MODE_AIR,
			 * modeCodeFromConsignment)) { if
			 * (StringUtils.equals(ManifestConstant.MODE_AIR, modeCode)) {
			 * isvalidMode = Boolean.TRUE;
			 * 
			 * } } else
			 */
			if (StringUtils.equals(ManifestConstant.MODE_AIR_CARGO,
					modeCodeFromConsignment)) {
				if (StringUtils.equals(ManifestConstant.MODE_AIR, modeCode)) {
					isvalidMode = Boolean.TRUE;

				}
			} else if (StringUtils.equals(ManifestConstant.MODE_SURFACE,
					modeCodeFromConsignment)) {
				if (StringUtils.equals(ManifestConstant.MODE_AIR, modeCode)
						|| StringUtils.equals(ManifestConstant.MODE_AIR_CARGO,
								modeCode)) {
					isvalidMode = Boolean.TRUE;

				}
			} else if (StringUtils.equals(
					ManifestConstant.MODE_GROUND_EXPRESS_CARGO,
					modeCodeFromConsignment)) {
				if (StringUtils.equals(ManifestConstant.MODE_AIR, modeCode)
						|| StringUtils.equals(ManifestConstant.MODE_AIR_CARGO,
								modeCode)
								|| StringUtils.equals(ManifestConstant.MODE_SURFACE,
										modeCode)) {
					isvalidMode = Boolean.TRUE;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::manifestModeValidations::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return isvalidMode;
	}

	/**
	 * Manifest number validations.
	 *
	 * @param modeCode the mode code
	 * @param mode the mode
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String manifestNumberValidations(String modeCode, ModeDO mode)
	throws Exception {
		StringBuilder manifestDetails = null;
		try {
			manifestDetails = new StringBuilder();

			// Mode Validations
			/*
			 * if (StringUtils.equals(ManifestConstant.MODE_AIR_CARGO,
			 * modeCode)) { if (StringUtils.equals(ManifestConstant.MODE_AIR,
			 * product.getProductCode())) {
			 * manifestDetails.append(ManifestConstant.COMMA);
			 * manifestDetails.append(ManifestConstant.MODE_VALIDATION);
			 * manifestDetails.append(ManifestConstant.COMMA);
			 * 
			 * } } else if (StringUtils.equals(ManifestConstant.MODE_SURFACE,
			 * modeCode)) { if (StringUtils.equals(ManifestConstant.MODE_AIR,
			 * product.getProductCode()) ||
			 * StringUtils.equals(ManifestConstant.MODE_AIR_CARGO, modeCode)) {
			 * manifestDetails.append(ManifestConstant.COMMA);
			 * manifestDetails.append(ManifestConstant.MODE_VALIDATION);
			 * manifestDetails.append(ManifestConstant.COMMA);
			 * 
			 * } } else if (StringUtils.equals(
			 * ManifestConstant.MODE_GROUND_EXPRESS_CARGO, modeCode)) { if
			 * (StringUtils.equals(ManifestConstant.MODE_AIR,
			 * product.getProductCode()) ||
			 * StringUtils.equals(ManifestConstant.MODE_AIR_CARGO, modeCode) ||
			 * StringUtils.equals(ManifestConstant.MODE_SURFACE, modeCode)) {
			 * manifestDetails.append(ManifestConstant.COMMA);
			 * manifestDetails.append(ManifestConstant.MODE_VALIDATION); } }
			 */
		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::manifestNumberValidations::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return manifestDetails.toString();
	}

	/**
	 * Consignment number validations.
	 *
	 * @param roboManifest the robo manifest
	 * @return the string builder
	 */
	public static StringBuilder consignmentNumberValidations(
			ManifestDO roboManifest) {
		StringBuilder consignmentDetails = null;
		Double consignmentWeight = 0.0;
		String consignmentNumber = "";
		String pincode = "";
		Integer noOfPieces = 0;
		try {
			ResourceBundle rb = ResourceBundle
			.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			consignmentDetails = new StringBuilder();
			if (roboManifest != null) {

				consignmentNumber = roboManifest.getConsgNumber();
				consignmentWeight = roboManifest.getIndvWeightKgs();
				noOfPieces = roboManifest.getNoOfPieces();
				pincode = roboManifest.getDestPinCode().getPincode();
				consignmentDetails = new StringBuilder();
				consignmentDetails.append(consignmentNumber);
				consignmentDetails.append(ManifestConstant.COMMA);
				consignmentDetails.append(consignmentWeight);
				consignmentDetails.append(ManifestConstant.COMMA);
				consignmentDetails.append(noOfPieces);
				consignmentDetails.append(ManifestConstant.COMMA);
				consignmentDetails.append(pincode);
			} else {

				consignmentDetails.append(ManifestConstant.ERROR_FLAG);
				consignmentDetails.append(",");
				consignmentDetails
				.append(rb
						.getString(ManifestConstant.INVALID_CONSIGNMENT_NUMBER));

				return consignmentDetails;
			}

		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::consignmentNumberValidations::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}

		return consignmentDetails;
	}

	/**
	 * Manifest validations.
	 *
	 * @param manifestList the manifest list
	 * @return the string builder
	 */
	public static StringBuilder manifestValidations(List manifestList) {
		StringBuilder manifestDetails = null;
		Object[] manifest_Obj = null;
		Double manifestWeight = 0.0;
		String manifestNumber = "";
		Integer noOfPieces = 0;
		try {
			ResourceBundle rb = ResourceBundle
			.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			manifestDetails = new StringBuilder();
			if (manifestList == null) {
				manifestDetails.append(ManifestConstant.ERROR_FLAG);
				manifestDetails.append(",");
				manifestDetails.append(rb
						.getString(ManifestConstant.INVALID_MANIfEST_NUMBER));
				return manifestDetails;
			} else {
				if (manifestList != null && manifestList.size() > 0) {
					for (int i = 0; i < manifestList.size(); i++) {
						manifest_Obj = (Object[]) manifestList.get(i);
						manifestNumber = manifest_Obj[0].toString();
						manifestWeight = Double.parseDouble(manifest_Obj[1]
						                                                 .toString());
						noOfPieces = Integer.parseInt(manifest_Obj[2]
						                                           .toString());

					}
					manifestDetails.append(manifestNumber);
					manifestDetails.append(ManifestConstant.COMMA);
					manifestDetails.append(manifestWeight);
					manifestDetails.append(ManifestConstant.COMMA);
					manifestDetails.append(noOfPieces);
				}
			}

		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::manifestValidations::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}

		return manifestDetails;
	}

	/**
	 * Consignment validations for robo check list.
	 *
	 * @param manifestDO the manifest do
	 * @return the string builder
	 */
	public static StringBuilder consignmentValidationsForROBOCheckList(
			ManifestDO manifestDO) {
		StringBuilder roboDetails = null;
		try {
			ResourceBundle rb = ResourceBundle
			.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			roboDetails = new StringBuilder();
			if (manifestDO == null) {
				roboDetails.append(ManifestConstant.ERROR_FLAG);
				roboDetails.append(",");
				roboDetails.append(rb
						.getString(ManifestConstant.INVALID_CONSIGNMENT_NO));
				return roboDetails;
			} else {

				roboDetails.append(manifestDO.getConsgNumber());
				roboDetails.append(ManifestConstant.COMMA);
				roboDetails.append(manifestDO.getIndvWeightKgs());
				roboDetails.append(ManifestConstant.COMMA);
				roboDetails.append(manifestDO.getNoOfPieces());
				roboDetails.append(ManifestConstant.COMMA);
				roboDetails.append(manifestDO.getManifestId());
				roboDetails.append(ManifestConstant.COMMA);
				roboDetails.append(manifestDO.getDocument().getDocumentId());
			}

		} catch (Exception ex) {
			LOGGER.error("ManifestMDBValidator::consignmentValidationsForROBOCheckList::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}

		return roboDetails;
	}

	/**
	 * This method is used for validation of manifest number for the regional
	 * office.
	 *
	 * @param roOfficeCode regional office code
	 * @param manifestNumber to be validated
	 * @return true if valid otherwise false
	 */
	public static boolean isValidManifest(String roOfficeCode,
			String manifestNumber) {
		char mfChar = manifestNumber.charAt(0);
		char offChar = roOfficeCode.charAt(0);
		return mfChar == offChar
		&& validateNumericString(manifestNumber.substring(1));
	}

	/**
	 * This method check the string contents for digits only.
	 *
	 * @param testString to be tested
	 * @return true if string contains only digits otherwise false
	 */
	public static boolean validateNumericString(String testString) {
		// It can't contain only numbers if it's null or empty...
		if (testString == null || testString.length() != 7) {
			return false;
		}

		for (int i = 0; i < testString.length(); i++) {

			// If we find a non-digit character we return false.
			if (!Character.isDigit(testString.charAt(i))) {
				return false;
			}
		}

		return true;

	}
}
