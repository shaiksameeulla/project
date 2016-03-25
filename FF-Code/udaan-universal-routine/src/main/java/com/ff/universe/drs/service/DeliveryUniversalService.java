/**
 * 
 */
package com.ff.universe.drs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DrsCollectionIntegrationWrapperDO;
import com.ff.geography.CityTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;

/**
 * @author mohammes
 * 
 */
public interface DeliveryUniversalService {

	/**
	 * Gets the city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the city
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CityTO getCity(CityTO cityTO) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the standard types as map.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the standard types as map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the load no.
	 * 
	 * @return the load no
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<LoadLotTO> getLoadNo() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the employee details.
	 * 
	 * @param empTo
	 *            the emp to
	 * @return the employee details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<EmployeeTO> getEmployeeDetails(EmployeeTO empTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the franchisee details.
	 * 
	 * @param frTo
	 *            the fr to
	 * @return the franchisee details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CustomerTO> getFranchiseeDetails(CustomerTO frTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the business associates details.
	 * 
	 * @param baTO
	 *            the ba to
	 * @return the business associates details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CustomerTO> getBusinessAssociatesDetails(
			CustomerTO baTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the consignment status from delivery.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the consignment status from delivery
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	String getConsignmentStatusFromDelivery(String consignment)
			throws CGSystemException;

	/**
	 * Checks if is consignment valid.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	Boolean isConsignmentValid(String consignment) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is consignment exist in delivery.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isConsignmentExistInDelivery(String consignment)
			throws CGSystemException;

	/**
	 * Checks if is consignment delivered.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isConsignmentDelivered(String consignment) throws CGSystemException;

	/**
	 * Gets the dox consg dtls from booking.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the dox consg dtls from booking
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	DeliveryConsignmentTO getDoxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the in manifested consgn dtls.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the in manifested consgn dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	DeliveryConsignmentTO getInManifestedConsgnDtls(AbstractDeliveryTO inputTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the cities by offices.
	 * 
	 * @param officeTo
	 *            the office to
	 * @return the cities by offices
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CityTO getCitiesByOffices(OfficeTO officeTo) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the cities by offices.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the cities by offices
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	CityTO getCitiesByOffices(Integer officeId) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is consignment undelivered.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean isConsignmentUndelivered(String consignment)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the latest date for consignment.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the latest date for consignment
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Date getLatestDateForConsignment(String consignment)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the delivered consignments details.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return DeliveryDetailsTO
	 * @throws CGBusinessException
	 *             ,CGSystemException the cG system exception
	 */
	DeliveryDetailsTO getDeliverdConsgDtls(String consignment,
			Integer originOffId) throws CGBusinessException, CGSystemException;

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
	 * Gets the reasons by reason type.
	 *
	 * @param reasonType the reason type
	 * @return the reasons by reason type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ReasonTO> getReasonsByReasonType(String reasonType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the non delivery reasons.
	 *
	 * @return the non delivery reasons
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ReasonTO> getNonDeliveryReasons() throws CGBusinessException,
			CGSystemException;

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
	 * Checks if is comail valid.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean isComailValid(String consignment) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the all id proofs for delivery.
	 *
	 * @param idProofTo the id proof to
	 * @return the all id proofs for delivery
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<IdentityProofTypeTO> getAllIdProofsForDelivery(
			IdentityProofTypeTO idProofTo) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all relationships for delivery.
	 *
	 * @param relationTo the relation to
	 * @return the all relationships for delivery
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<RelationTO> getAllRelationsForDelivery(RelationTO relationTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the vendors list.
	 *
	 * @param loadMovementVendorTO the load movement vendor to
	 * @return the vendors list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LoadMovementVendorTO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO)
			throws CGBusinessException, CGSystemException;

	

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
	 * Checks if is child consg in manifested for ppx.
	 *
	 * @param inputTo the input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean isChildConsgInManifestedForPpx(AbstractDeliveryTO inputTo)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * @return List<PaymentModeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<PaymentModeTO> getModeOfPaymentDetails()
			throws CGBusinessException, CGSystemException;
		

	/**
	 * Checks if is consg having child cns.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isConsgHavingChildCns(String consignment)
			throws CGBusinessException, CGSystemException;

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
	 * Gets the manifested date by consg number for parent consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for parent consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForParentConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number for child consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for child consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForChildConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number for dox.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for dox
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForDox(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number parent for ppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number parent for ppx
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberParentForPpx(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number child for ppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number child for ppx
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberChildForPpx(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is consignment open.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isConsignmentOpen(String consignment) throws CGSystemException;
	
	/**
	 * Gets the manifested date by comail number.
	 *
	 * @param consignment the consignment
	 * @return the manifested date by comail number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByComailNumber(String consignment)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the drs dtls by consg no.
	 *
	 * @param consignmentNumber the consignment number
	 * @return the drs dtls by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryDetailsTO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate in manifested and logged in office for parent cnppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateInManifestedAndLoggedInOfficeForParentCNPPX(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate in manifested and logged in office for child cnppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateInManifestedAndLoggedInOfficeForChildCNPPX(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate in manifested and logged in office for dox.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateInManifestedAndLoggedInOfficeForDOX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException;

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
	 * Gets the payment mode dtls.
	 *
	 * @param processCode the process code
	 * @return the payment mode dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the payment mode type for collection.
	 *
	 * @return the payment mode type for collection
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<String, Integer> getPaymentModeTypeForCollection()
			throws CGBusinessException, CGSystemException;

	/**
	 * Process delivery details for collection.
	 *
	 * @param drsDtls the drs dtls
	 * @param collectionMap the collection map
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean processDeliveryDetailsForCollection(DeliveryDetailsDO drsDtls,
			Map<String, Integer> collectionMap) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the delivery details for collection.
	 * @param qryName TODO
	 *
	 * @return the delivery details for collection
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DrsCollectionIntegrationWrapperDO> getDeliveryDetailsForCollection(String qryName)
			throws CGSystemException, CGBusinessException;

	String getConsignmentStatusFromCNForParentConsg(String consignment)
			throws CGBusinessException, CGSystemException;

	String getConsignmentStatusFromCNForChildConsg(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the parent consg details from cn.
	 *
	 * @param consignment the consignment
	 * @return the parent consg details from cn
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryConsignmentTO getParentConsgDetailsFromConsignment(String consignment)
			throws CGBusinessException, CGSystemException;

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
	List<?> getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException;
	
	
	/**
	 * Gets the vendor dtls for drs.
	 *
	 * @param partyType the party type
	 * @param officeId the office id
	 * @return the vendor dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadMovementVendorTO> getVendorDtlsForDrs(String partyType,Integer officeId) throws CGSystemException;

	/**
	 * Gets the manifested date by consg number for third party dox.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for third party dox
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForThirdPartyDox(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number for third party p px.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for third party p px
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForThirdPartyParentPPx(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifested date by consg number for third party child p px.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number for third party child p px
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberForThirdPartyChildPPx(AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate in manifested and logged in office for manifest drs dox.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateOutManifestedAndLoggedInOfficeForThirdPartyDOX(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate in manifested and logged in office for parent cn for third party ppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateOutManifestedAndLoggedInOfficeForParentCNForThirdPartyPPX(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate in manifested and logged in office for child cn third party ppx.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String validateOutManifestedAndLoggedInOfficeForChildCNThirdPartyPPX(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the manifested date by manifest and consg number for parent consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by manifest and consg number for parent consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByManifestAndConsgNumberForParentConsg(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the manifested date by manifest and consg number for child consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by manifest and consg number for child consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByManifestAndConsgNumberForChildConsg(
			AbstractDeliveryTO dlvInputTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the manifested type by consignment and logged inoffice and manifest number.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested type by consignment and logged inoffice and manifest number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(
			AbstractDeliveryTO dlvInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the octroi amount for drs.
	 *
	 * @param consignment the consignment
	 * @return the octroi amount for drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Double getOctroiAmountForDrs(String consignment)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the child consg details for third party drs.
	 *
	 * @param inputTo the input to
	 * @return the child consg details for third party drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryConsignmentTO getThirdpartyManifestedConsgDetailsForDRSChildCn(
			AbstractDeliveryTO inputTo) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the in manifested consgn dtls for third party drs for parent cn.
	 *
	 * @param inputTo the input to
	 * @return the in manifested consgn dtls for third party drs for parent cn
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForDrsParentCn(
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
	 * Process delivery details for collection integration.
	 *
	 * @param drsDtls the drs dtls
	 * @param paymentTypeMap the payment type map
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean processDeliveryDetailsForCollectionIntegration(
			DrsCollectionIntegrationWrapperDO drsDtls, Map<String, Integer> paymentTypeMap)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets the vendor dtls for drs by city id.
	 *
	 * @param partyType the party type
	 * @param cityId the city id
	 * @return the vendor dtls for drs by city id
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadMovementVendorTO> getVendorDtlsForDrsByCityId(String partyType,Integer cityId) throws CGSystemException;

	DeliveryConsignmentTO getOutManifestedConsgnDtlsForThirdParty(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is consignment exist in drs.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isConsignmentExistInDRS(String consignment)
			throws CGSystemException;

	

}
