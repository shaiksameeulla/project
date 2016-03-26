/**
 * 
 */
package com.ff.web.drs.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryNavigatorTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.web.drs.preparation.action.DrsCommonPage;
import com.ff.web.drs.preparation.action.Page;

/**
 * The Interface DeliveryCommonService.
 *
 * @author mohammes
 */
public interface DeliveryCommonService {

	/**
	 * Gets the city by id.
	 *
	 * @param cityId the city id
	 * @return the city by id
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	CityTO getCityById(Integer cityId) throws CGSystemException,
			CGBusinessException;
	
	/**
	 * Gets the standard types as map.
	 *
	 * @param typeName the type name
	 * @return the standard types as map
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the drs for std types.
	 *
	 * @return the drs for std types
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<String, String> getDrsForStdTypes() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the yp drs std types.
	 *
	 * @return the yp drs std types
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<String, String> getYpDrsStdTypes() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the delivery type std types.
	 *
	 * @return the delivery type std types
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<String, String> getDeliveryTypeStdTypes() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the seal and sign std types.
	 *
	 * @return the seal and sign std types
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Map<String, String> getSealAndSignStdTypes() throws CGSystemException,
			CGBusinessException;
/**
 * getLoadLotForDRS Here Key &values are same ie Load No
 * @return Map<Integer, Integer>
 * @throws CGSystemException
 * @throws CGBusinessException
 */
	Map<Integer, Integer> getLoadLotForDRS() throws CGSystemException,
			CGBusinessException;

/**
 * Gets the party type details for drs.
 *
 * @param drsForType the drs for type
 * @param officeTO the office id
 * @return the party type details for drs
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<Integer, String> getPartyTypeDetailsForDRS(String drsForType,
		OfficeTO officeTO) throws CGSystemException, CGBusinessException;

/**
 * Drs process number generator.
 *
 * @param to the to
 * @return the string
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
String drsProcessNumberGenerator(SequenceGeneratorConfigTO to)
		throws CGSystemException, CGBusinessException;

/**
 * Discard generated drs.
 *
 * @param deliveryTO the delivery to
 * @return the boolean
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Boolean discardGeneratedDrs(AbstractDeliveryTO deliveryTO)
		throws CGBusinessException, CGSystemException;

/**
 * Validate consignment details.
 *
 * @param deliveryTO the delivery to
 * @return the delivery consignment to
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryConsignmentTO validateConsignmentDetails(AbstractDeliveryTO deliveryTO)
		throws CGBusinessException, CGSystemException;



/**
 * Gets the drs navigation details.
 *
 * @param drsNumber the drs number
 * @param drsCode the drs code
 * @return the drs navigation details
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
List<DeliveryNavigatorTO> getDrsNavigationDetails(List<String> drsNumber,
		String drsCode) throws CGBusinessException, CGSystemException;

/**
 * Gets the drs navigation details.
 *
 * @param drsNumber the drs number
 * @param drsCode the drs code
 * @return the drs navigation details
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryNavigatorTO getDrsNavigationDetails(String drsNumber, String drsCode)
		throws CGBusinessException, CGSystemException;

/**
 * Modify generated drs.Modifies only DRS-FOR & its code if and only if it's in open status
 *
 * @param deliveryTO the delivery to
 * @return the boolean
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Boolean modifyGeneratedDrs(AbstractDeliveryTO deliveryTO)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the non delivery reasons.
 *
 * @return the non delivery reasons
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Map<Integer, String> getNonDeliveryReasons() throws CGBusinessException,
		CGSystemException;

/**
 * Gets the drs details by drs number for update.
 *
 * @param inputTo the input to
 * @return the drs details by drs number for update
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryTO getDrsDetailsForUpdate(AbstractDeliveryTO inputTo)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the drs details by drs number for preparation.
 *
 * @param inputTo the input to
 * @return the drs details by drs number for preparation
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryTO getDrsDetailsForPreparation(AbstractDeliveryTO inputTo)
		throws CGBusinessException, CGSystemException;

/**
 * Validate consignment by drs number.
 *
 * @param inputTo the input to
 * @return the delivery details to
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryDetailsTO validateConsignmentByDrsNumber(AbstractDeliveryTO inputTo)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the all id proofs for delivery.
 *
 * @return the all id proofs for delivery
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<Integer, String> getAllIdProofsForDelivery() throws CGSystemException,
		CGBusinessException;

/**
 * Gets the all relations for delivery.
 *
 * @return the all relations for delivery
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<Integer, String> getAllRelationsForDelivery() throws CGSystemException,
		CGBusinessException;

/**
 * Gets the all co courier vendor list.
 * @param officeId TODO
 *
 * @return the all co courier vendor list
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<Integer, String> getAllCoCourierVendorList(OfficeTO officeTO) throws CGSystemException,
		CGBusinessException;

/**
 * Gets the all vendor list.
 *
 * @param vendorTO the vendor to
 * @return the all vendor list
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
List<LoadMovementVendorTO> getAllVendorList(LoadMovementVendorTO vendorTO)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the mode of payment details.
 *
 * @return the mode of payment details
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<String, String> getModeOfPaymentDetails() throws CGSystemException, CGBusinessException;

/**
 * Gets the manifested date by consg number.
 * @param isParent the is parent
 * @param dlvInputTO TODO
 *
 * @return the manifested date by consg number
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Date getManifestedDateByConsgNumber(AbstractDeliveryTO dlvInputTO,boolean isParent) throws CGSystemException, CGBusinessException;

/**
 * Gets the manifested date by comail number.
 *
 * @param comailNumber the comail number
 * @return the manifested date by comail number
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Date getManifestedDateByComailNumber(String comailNumber)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the consignment status from delivery.
 *
 * @param consignment the consignment
 * @return the consignment status from delivery
 * @throws CGSystemException the cG system exception
 */
String getConsignmentStatusFromDelivery(String consignment)
		throws CGSystemException;

/**
 * Checks if is consg having child cns.
 *
 * @param consignment the consignment
 * @return the boolean
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Boolean isConsgHavingChildCns(String consignment) throws CGBusinessException,
		CGSystemException;

/**
 * Checks if is child cn.
 *
 * @param consignment the consignment
 * @return the boolean
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Boolean isChildCn(String consignment) throws CGBusinessException,
		CGSystemException;

/**
 * Gets the attempt count for consignment.
 *
 * @param consignment the consignment
 * @return the attempt count for consignment
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Integer getAttemptCountForConsignment(String consignment)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the child consg details for drs.
 *
 * @param consignment the consignment
 * @return the child consg details for drs
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryConsignmentTO getChildConsgDetailsForDRS(String consignment)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the cities by offices.
 *
 * @param officeId the office id
 * @return the cities by offices
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
CityTO getCitiesByOffices(Integer officeId) throws CGSystemException,
		CGBusinessException;


/**
 * Gets the details for dox print.
 *
 * @param drsNumber the drs number
 * @return the details for dox print
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryTO getDetailsForDoxPrint(String drsNumber) throws CGSystemException,
		CGBusinessException;

/**
 * Gets the details for ppx print.
 *
 * @param drsNumber the drs number
 * @return the details for ppx print
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryTO getDetailsForPpxPrint(String drsNumber) throws CGSystemException,
		CGBusinessException; 

/*for print module*/
List<Page> preparePrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException;

List<DrsCommonPage> prepareCreditPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException;

List<DrsCommonPage> prepareCodLcDoxPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException;

List<DrsCommonPage> prepareNormalPpxPrint(DeliveryTO  drsTo )throws CGSystemException, CGBusinessException;

/**
 * Gets the drs payment type dtls.
 *
 * @return the drs payment type dtls
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<String, String> getDrsPaymentTypeDtls() throws CGSystemException,
		CGBusinessException;

/**
 * Gets the consignment types.
 *
 * @param consgTypeTO the consg type to
 * @return the consignment types
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
List<ConsignmentTypeTO> getConsignmentTypes(ConsignmentTypeTO consgTypeTO)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the consignment type for delivery.
 *
 * @return the consignment type for delivery
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Map<String, String> getConsignmentTypeForDelivery() throws CGBusinessException,
		CGSystemException;

/**
 * Gets the drs status by drs number.
 *
 * @param drsHeaderTO the drs header to
 * @return the drs status by drs number
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
String getDrsStatusByDrsNumber(AbstractDeliveryTO drsHeaderTO)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the party type details for drs.
 *
 * @param inputTo the input to
 * @param drsFor the drs for
 * @return the party type details for drs
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Map<Integer, String> getPartyTypeDetailsForDRS(AbstractDeliveryTO inputTo,
		String drsFor) throws CGSystemException, CGBusinessException;

/**
 * Checks if is comail valid.
 *
 * @param comailNumber the comail number
 * @return true, if is comail valid
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
boolean isComailValid(String comailNumber) throws CGSystemException,
		CGBusinessException;

/**
 * Gets the in manifested comail dtls.
 *
 * @param inputTo the input to
 * @return the in manifested comail dtls
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryConsignmentTO getInManifestedComailDtls(AbstractDeliveryTO inputTo)
		throws CGSystemException, CGBusinessException;

/**
 * Checks if is consignment valid.
 *
 * @param consgNumber the consg number
 * @return true, if is consignment valid
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
boolean isConsignmentValid(String consgNumber) throws CGSystemException,
		CGBusinessException;

String validateInManifestedAndLoggedInOfficeForDOX(AbstractDeliveryTO dlvInputTO)
		throws CGSystemException, CGBusinessException;

/**
 * Validate in manifested and logged in office for parent cnppx.
 *
 * @param dlvInputTO the dlv input to
 * @return the string
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
String validateInManifestedAndLoggedInOfficeForParentCNPPX(
		AbstractDeliveryTO dlvInputTO) throws CGSystemException,
		CGBusinessException;

String validateInManifestedAndLoggedInOfficeForChildCNPPX(
		AbstractDeliveryTO dlvInputTO) throws CGSystemException,
		CGBusinessException;

/**
 * Gets the parent consg details for drs.
 *
 * @param consignment the consignment
 * @return the parent consg details for drs
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
ConsignmentTO getParentConsgDetailsForDRS(String consignment)
		throws CGBusinessException, CGSystemException;

DeliveryConsignmentTO getParentConsgDetailsFromConsignment(String consignment)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the dox consg dtls from booking.
 *
 * @param deliveryTO the delivery to
 * @return the dox consg dtls from booking
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryConsignmentTO getDoxConsgDtlsFromBooking(AbstractDeliveryTO deliveryTO)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the in manifested consgn dtls.
 *
 * @param inputTo the input to
 * @return the in manifested consgn dtls
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryConsignmentTO getInManifestedConsgnDtls(AbstractDeliveryTO inputTo)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the ppx consg dtls from booking.
 *
 * @param inputTo the input to
 * @return the ppx consg dtls from booking
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryConsignmentTO getPpxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the consignment type by consg number.
 *
 * @param consignment the consignment
 * @return the consignment type by consg number
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
String getConsignmentTypeByConsgNumber(String consignment)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the drs details for manual drs.
 *
 * @param inputTo the input to
 * @return the drs details for manual drs
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
DeliveryTO getDrsDetailsForManualDrs(AbstractDeliveryTO inputTo)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the consignment status from consg.
 *
 * @param consignment the consignment
 * @return the consignment status from consg
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
String getConsignmentStatusFromConsg(String consignment)
		throws CGBusinessException, CGSystemException;

/**
 * Gets the manifested type by consignment and logged inoffice.
 * @param dlvInputTO TODO
 *
 * @return the manifested type by consignment and logged inoffice
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
void getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException;

/**
 * Gets the manifested type by consignment and logged inoffice for third party manifest.
 * @param dlvInputTO TODO
 *
 * @return the manifested type by consignment and logged inoffice for third party manifest
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
void getManifestedTypeByConsignmentAndLoggedInofficeForThirdPartyManifest(
		AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
		CGSystemException;

/**
 * Gets the manifested date by consg number for manifest drs.
 *
 * @param dlvInputTO the dlv input to
 * @param isParent the is parent
 * @return the manifested date by consg number for manifest drs
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
Date getManifestedDateByConsgNumberForThirdPartyManifestDrs(
		AbstractDeliveryTO dlvInputTO, boolean isParent)
		throws CGSystemException, CGBusinessException;

/**
 * Gets the third party manifested consgn dtls for parent cn.
 *
 * @param inputTo the input to
 * @return the third party manifested consgn dtls for parent cn
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForParentCn(
		AbstractDeliveryTO inputTo) throws CGSystemException,
		CGBusinessException;

/**
 * Gets the third party manifested consgn dtls for child cn.
 *
 * @param inputTo the input to
 * @return the third party manifested consgn dtls for child cn
 * @throws CGSystemException the cG system exception
 * @throws CGBusinessException the cG business exception
 */
DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForChildCn(
		AbstractDeliveryTO inputTo) throws CGSystemException,
		CGBusinessException;

/**
 * Validate stop delivery.
 *
 * @param consgNumber the consg number
 * @return the string
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
String validateStopDelivery(String consgNumber) throws CGBusinessException,
		CGSystemException;

/**
 * Gets the octroi amount for drs.
 *
 * @param consignment the consignment
 * @return the octroi amount for drs
 * @throws CGBusinessException the cG business exception
 * @throws CGSystemException the cG system exception
 */
Double getOctroiAmountForDrs(String consignment) throws CGBusinessException,
		CGSystemException;

/**
 * Checks if is payment captured for cn.
 *
 * @param consgId the consg id
 * @return the boolean
 * @throws CGSystemException the cG system exception
 */
Boolean isPaymentCapturedForCn(Integer consgId) throws CGBusinessException,CGSystemException;

String getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForDOXManualDrs(
		AbstractDeliveryTO deliveryTO) throws CGBusinessException,
		CGSystemException;

String getOutManifestedNumberByLoggedOfficeForTPManifestParentConsgForPpxManualDrs(
		AbstractDeliveryTO deliveryTO) throws CGBusinessException,
		CGSystemException;

String getOutManifestedNumberByLoggedOfficeForTPManifestChildConsgForPpxManualDrs(
		AbstractDeliveryTO deliveryTO) throws CGBusinessException,
		CGSystemException;

DeliveryConsignmentTO getOutManifestedConsgnDtls(AbstractDeliveryTO inputTo)
		throws CGSystemException, CGBusinessException;

String checkIfConsIsDelivered(String consNO, String delivryStatus);

/**
 * Two way write for drs.
 *
 * @param deliveryTo the delivery to
 * @throws CGBusinessException the cG business exception
 */
void twoWayWriteForDRS(AbstractDeliveryTO deliveryTo)
		throws CGBusinessException;

	/**
	 * Gets the details for RTO COD print.
	 * 
	 * @param drsNumber
	 *            the drs number
	 * @return the details for ppx print
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	DeliveryTO getDetailsForRtoCodPrint(String drsNumber)
			throws CGSystemException, CGBusinessException;

	String validateForRTOed(String consgNumber) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the non delivery reasons by type.
	 *
	 * @param reasonType the reason type
	 * @return the non delivery reasons by type
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	Map<Integer, String> getNonDeliveryReasonsByType(String reasonType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the common sequence number.
	 *
	 * @param sequenceTO the sequence to
	 * @return the common sequence number
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	SequenceGeneratorConfigTO getCommonSequenceNumber(
			SequenceGeneratorConfigTO sequenceTO) throws CGBusinessException,
			CGSystemException;

	void validateConsignmentFromDelivery(String consgNumber, String seriesType)
			throws CGSystemException, CGBusinessException;

	/**
	 * Validate consignment from delivery for save.
	 *
	 * @param consgNumber the consg number
	 * @param seriesType the series type
	 * @throws CGSystemException the CG system exception
	 * @throws CGBusinessException the CG business exception
	 */
	void validateConsignmentFromDeliveryForSave(String consgNumber,
			String seriesType) throws CGSystemException, CGBusinessException;

}
