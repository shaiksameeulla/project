/**
 * 
 */
package com.ff.universe.drs.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DrsCollectionIntegrationWrapperDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.to.drs.AbstractDeliveryTO;

/**
 * @author mohammes
 * 
 */
public interface DeliveryUniversalDAO {

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
	 * Gets the dox consg dtls from booking.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the dox consg dtls from booking
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	ConsignmentDO getDoxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
			throws CGSystemException;

	/**
	 * Gets the manifested consgn dtls.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param manifestStatus
	 *            the manifest status
	 * @return the manifested consgn dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	ConsignmentDO getManifestedConsgnDtls(String consgNumber,
			String manifestStatus) throws CGSystemException;

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
			throws CGSystemException;

	/**
	 * Gets the Delivery Details By Consgnumber.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return DeliveryDetailsDO
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	DeliveryDetailsDO getDeliverdConsgDtls(String consignment,
			Integer orgOfficeId) throws CGSystemException;

	/**
	 * Gets the attempt count for consignment.
	 *
	 * @param consgNumber the consg number
	 * @return the attempt count for consignment
	 * @throws CGSystemException the cG system exception
	 */
	Integer getAttemptCountForConsignment(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the manifested comail dtls.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @return the manifested comail dtls
	 * @throws CGSystemException the cG system exception
	 */
	ComailDO getManifestedComailDtls(String consgNumber, String manifestStatus)
			throws CGSystemException;

	/**
	 * Checks if is valid comail number.
	 *
	 * @param consgNumber the consg number
	 * @return true, if is valid comail number
	 * @throws CGSystemException the cG system exception
	 */
	boolean isValidComailNumber(String consgNumber) throws CGSystemException;

	

	/**
	 * Gets the child consg dtls for drs.
	 *
	 * @param consgNumber the consg number
	 * @return the child consg dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	ChildConsignmentDO getChildConsgDtlsForDrs(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the parent consg dtls for drs.
	 *
	 * @param consgNumber the consg number
	 * @return the parent consg dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO getParentConsgDtlsForDrs(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the mnfst child consgn dtls for drs for ppx.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @return the mnfst child consgn dtls for drs for ppx
	 * @throws CGSystemException the cG system exception
	 */
	String getMnfstChildConsgnDtlsForDrsForPPX(String consgNumber,
			String manifestStatus) throws CGSystemException;

	/**
	 * Checks if is consg having child cns.
	 *
	 * @param consgNumber the consg number
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	String isConsgHavingChildCns(String consgNumber) throws CGSystemException;

	/**
	 * Checks if is child cns.
	 *
	 * @param consgNumber the consg number
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	String isChildCns(String consgNumber) throws CGSystemException;

	/**
	 * Gets the ppx consg dtls from booking.
	 *
	 * @param inputTo the input to
	 * @return the ppx consg dtls from booking
	 * @throws CGSystemException the cG system exception
	 */
	ChildConsignmentDO getPpxConsgDtlsFromBooking(AbstractDeliveryTO inputTo)
			throws CGSystemException;

	/**
	 * Gets the manifested date by consg number.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumber(AbstractDeliveryTO dlvInputTO) throws CGSystemException;
	
	/**
	 * Gets the manifested date by comail number.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param processCode the process code
	 * @return the manifested date by comail number
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByComailNumber(String consgNumber,
			String manifestStatus, String processCode) throws CGSystemException;

	

	/**
	 * Gets the drs dtls by consg no.
	 *
	 * @param consignmentNumber the consignment number
	 * @return the drs dtls by consg no
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryDetailsDO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGSystemException;

	/**
	 * Validate manifested office by logged in office.
	 * @param dlvInputTO TODO
	 *
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	String getInManifestedConsignmentNumberByOffice(AbstractDeliveryTO dlvInputTO) throws CGSystemException;

	/**
	 * Gets the delivery details for collection.
	 * @param qryName TODO
	 *
	 * @return the delivery details for collection
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<DrsCollectionIntegrationWrapperDO> getDeliveryDetailsForCollection(String qryName)
			throws CGSystemException;

	/**
	 * Save collection dtls by drs.
	 *
	 * @param collectionDo the collection do
	 * @return the boolean
	 */
	Boolean saveCollectionDtlsByDrs(CollectionDO collectionDo)throws CGSystemException;

	/**
	 * Update drs for collection integration.
	 *
	 * @param drsDtsDO the drs dts do
	 * @return the boolean
	 */
	Boolean updateDrsForCollectionIntegration(DrsCollectionIntegrationWrapperDO drsDtsDO)throws CGSystemException;

	String getConsignmentStatusFromConsgForChildCn(String consgNumber)
			throws CGSystemException;

	String getConsignmentStatusFromConsgForParentCn(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the consignment type.
	 *
	 * @param consgNumber the consg number
	 * @return the consignment type
	 * @throws CGSystemException the cG system exception
	 */
	String getConsignmentTypeByConsgNumber(String consgNumber) throws CGSystemException;

	/**
	 * Gets the consignment status from consg.
	 *
	 * @param consgNumber the consg number
	 * @return the consignment status from consg
	 * @throws CGSystemException the cG system exception
	 */
	String getConsignmentStatusFromConsg(String consgNumber)
			throws CGSystemException;

	/**
	 * Gets the manifested type by consignment and logged inoffice.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested type by consignment and logged inoffice
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGSystemException;

	/**
	 * Gets the manifested date by consg number and manifest number.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested date by consg number and manifest number
	 * @throws CGSystemException the cG system exception
	 */
	Date getManifestedDateByConsgNumberAndManifestNumber(AbstractDeliveryTO dlvInputTO) throws CGSystemException;

	/**
	 * Gets the in manifested consignment number by office for third party manifest.
	 * @param dlvInputTO TODO
	 *
	 * @return the in manifested consignment number by office for third party manifest
	 * @throws CGSystemException the cG system exception
	 */
	String getOutManifestedConsignmentNumberByOfficeForThirdPartyManifest(
			AbstractDeliveryTO dlvInputTO) throws CGSystemException;

	/**
	 * Gets the manifested type by consignment and logged inoffice and manifest number.
	 * @param dlvInputTO TODO
	 *
	 * @return the manifested type by consignment and logged inoffice and manifest number
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(
			AbstractDeliveryTO dlvInputTO)
			throws CGSystemException;

	/**
	 * Gets the octroi amount for drs.
	 *
	 * @param consgNumber the consg number
	 * @return the octroi amount for drs
	 * @throws CGSystemException the cG system exception
	 */
	Double getOctroiAmountForDrs(String consgNumber) throws CGSystemException;

	/**
	 * Gets the manifested consgn dtls for third party drs parent cn.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param manifestNumber the manifest number
	 * @return the manifested consgn dtls for third party drs parent cn
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO getManifestedConsgnDtlsForThirdPartyDrsParentCn(
			String consgNumber, String manifestStatus, String manifestNumber)
			throws CGSystemException;

	/**
	 * Gets the manifested consgn dtls for third party drs child cn.
	 *
	 * @param consgNumber the consg number
	 * @param manifestStatus the manifest status
	 * @param manifestNumber the manifest number
	 * @return the manifested consgn dtls for third party drs child cn
	 * @throws CGSystemException the cG system exception
	 */
	String getManifestedConsgnDtlsForThirdPartyDrsChildCn(
			String consgNumber,String manifestStatus,String manifestNumber)
			throws CGSystemException;

	/**
	 * Update all child consignments for integration.
	 *
	 * @param drsDtsDO the drs dts do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateCollectionStatusForAllDRSChildConsignments(DrsCollectionIntegrationWrapperDO drsDtsDO)
			throws CGSystemException;

	/**
	 * Checks if is collection entry already exist for drs.
	 *
	 * @param consgId the consg id
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isCollectionEntryAlreadyExistForDRS(Integer consgId)
			throws CGSystemException;

	/**
	 * Save collection dtls by drs.
	 *
	 * @param collectionDo the collection do
	 * @return the boolean
	 */
	Boolean saveCollectionDtlsByDrs(List<CollectionDO> collectionDo);

	/**
	 * Checks if is consignment exist in delivery by parent consg.
	 *
	 * @param consignment the consignment
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	String isConsignmentExistInDeliveryByParentConsg(String consignment)
			throws CGSystemException;

	Integer getOfficeIdByOfficeCode(String officeCode) throws CGSystemException;

	Integer getDeliveredOfficeByDeliveryDtl(Long deliveryDtlsId)
			throws CGSystemException;

	String getOfficeCodeByOfficeId(Integer officeId) throws CGSystemException;

	

}
