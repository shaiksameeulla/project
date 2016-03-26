package com.ff.web.manifest.inmanifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InManifestCommonDAO.
 * 
 * @author narmdr
 */
public interface InManifestCommonDAO {

	/**
	 * Gets the manifest dtls.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest dtls
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestDtls(ManifestBaseTO manifestBaseTO) throws CGSystemException;

	/**
	 * Save or update manifest.
	 *
	 * @param manifestDO the manifest do
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * Save or update manifest list.
	 *
	 * @param manifestDOs the manifest d os
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<ManifestDO> saveOrUpdateManifestList(List<ManifestDO> manifestDOs)
			throws CGSystemException;

	/**
	 * Save or update manifest process.
	 *
	 * @param manifestProcessDOs the manifest process d os
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
/*	boolean saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException;*/

	/**
	 * Gets the manifest by no process consg type.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest by no process consg type
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestByNoProcessConsgType(InBagManifestTO manifestBaseTO)
			throws CGSystemException;

	/**
	 * Gets the embedded manifest dtls by embedded id.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the embedded manifest dtls by embedded id
	 * @throws CGSystemException the cG system exception
	 */
	List<ManifestDO> getEmbeddedManifestDtlsByEmbeddedId(
			ManifestBaseTO manifestBaseTO) throws CGSystemException;

	/**
	 * Report less baggage.
	 *
	 * @param inMasterBagManifestTO the in master bag manifest to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<String>  reportLessBaggage(InMasterBagManifestTO inMasterBagManifestTO) throws CGSystemException;

	/**
	 * Gets the manifest id.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest id
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestId(InManifestTO inManifestTO) throws CGSystemException;

	/**
	 * Gets the less manifest numbers.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the less manifest numbers
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getLessManifestNumbers(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the manifest id by embedded id and mf no.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest id by embedded id and mf no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdByEmbeddedIdAndMfNo(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the consg details.
	 *
	 * @param consgNum the consg num
	 * @param processCode the process code
	 * @param manifestNum the manifest num
	 * @return the consg details
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentManifestDO getConsgDetails(String consgNum, String processCode, String manifestNum) throws CGSystemException;
	
	/**
	 * Gets the manifest consignment dtls by manifest id.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest consignment dtls by manifest id
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentManifestDO> getManifestConsignmentDtlsByManifestId(
			ManifestBaseTO manifestBaseTO) throws CGSystemException;

	/**
	 * Save or update consignment list.
	 *
	 * @param consignmentDOs the consignment d os
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentDO> saveOrUpdateConsignmentList(
			List<ConsignmentDO> consignmentDOs) throws CGSystemException;

	/**
	 * Save or update consignment manifest list.
	 *
	 * @param consignmentManifestDOs the consignment manifest d os
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentManifestDO> saveOrUpdateConsignmentManifestList(
			List<ConsignmentManifestDO> consignmentManifestDOs)
			throws CGSystemException;

	/**
	 * Save or update consignment.
	 *
	 * @param consignmentDO the consignment do
	 * @return the consignment do
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException;

	/**
	 * Gets the cN pricing details by consg id.
	 *
	 * @param consgId the consg id
	 * @return the cN pricing details by consg id
	 * @throws CGSystemException the cG system exception
	 *//*
	CNPricingDetailsDO getCNPricingDetailsByConsgId(Integer consgId)
			throws CGSystemException;*/

	/**
	 * Gets the less consg numbers.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the less consg numbers
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getLessConsgNumbers(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the consg manifest id by manifest id and consg no.
	 *
	 * @param inManifestTO4Excess the in manifest t o4 excess
	 * @return the consg manifest id by manifest id and consg no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getConsgManifestIdByManifestIdAndConsgNo(
			InManifestTO inManifestTO4Excess) throws CGSystemException;

	/**
	 * Save or update cn pricing details.
	 *
	 * @param cnPricingDetailsTO the cn pricing details to
	 * @return the cN pricing details do
	 * @throws CGSystemException the cG system exception
	 */
/*	CNPricingDetailsDO saveOrUpdateCnPricingDetails(
			CNPricingDetailsDO cnPricingDetailsDO) throws CGSystemException;*/

	/**
	 * Update cn pricing details.
	 *
	 * @param cnPricingDetailsTO the cn pricing details to
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	boolean updateCnPricingDetails(CNPricingDetailsTO cnPricingDetailsTO)
			throws CGSystemException;

	
	/**
	 * Gets the consignment details.
	 *
	 * @param consignmentTO the consignment to
	 * @return the consignment details
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO getConsignmentDetails(ConsignmentTO consignmentTO)
			throws CGSystemException;

	/**
	 * Gets the consignment id by consg no.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the consignment id by consg no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getConsignmentIdByConsgNo(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the manifest header details.
	 *
	 * @param inManifestValidationTO the in manifest validation to
	 * @return the manifest header details
	 * @throws CGSystemException the cG system exception
	 */
	List<ManifestDO> getManifestHeaderDetails(InManifestValidationTO inManifestValidationTO)
			throws CGSystemException;

	/**
	 * Gets the manifest numbers by embedded id.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest numbers by embedded id
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getManifestNumbersByEmbeddedId(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the consg numbers by manifest id.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the consg numbers by manifest id
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getConsgNumbersByManifestId(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the manifest id by manifest no operating office.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest id by manifest no operating office
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdByManifestNoOperatingOffice(ManifestTO manifestTO)
			throws CGSystemException;

	/**
	 * Gets the in manifest dtls.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the in manifest dtls
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentManifestDO getInManifestDtls(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;
	
	/**
	 * Gets the in comail details.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the in comail details
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getInComailDetails(ManifestBaseTO manifestBaseTO) throws CGSystemException;

	/**
	 * Checks if is consg no in manifested.
	 *
	 * @param inBagManifestTO the in bag manifest to
	 * @return true, if is consg no in manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isConsgNoInManifested(InBagManifestTO inBagManifestTO)
			throws CGSystemException;

	/**
	 * Checks if is manifest num in manifested.
	 *
	 * @param inManifestTO the in manifest to
	 * @return true, if is manifest num in manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestNumInManifested(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Save or update manifest mapped embedded dos.
	 *
	 * @param manifestMappedEmbeddedDOs the manifest mapped embedded d os
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateManifestMappedEmbeddedDOs(
			List<ManifestMappedEmbeddedDO> manifestMappedEmbeddedDOs)
			throws CGSystemException;

	/**
	 * Update manifest remarks.
	 *
	 * @param manifestDO the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	void updateManifestRemarks(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * Gets the manifest by no process type.
	 *
	 * @param baseTO the base to
	 * @return the manifest by no process type
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestByNoProcessType(ManifestBaseTO baseTO)
			throws CGSystemException;

	/**
	 * Checks if is co mail num in manifested.
	 *
	 * @param inManifestTO the in manifest to
	 * @return true, if is co mail num in manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isCoMailNumInManifested(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Gets the co mail id by no.
	 *
	 * @param coMailNo the co mail no
	 * @return the co mail id by no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getCoMailIdByNo(String coMailNo)
			throws CGSystemException;

	/**
	 * Checks if is manifest header in manifested.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return true, if is manifest header in manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestHeaderInManifested(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;
	

	/**
	 * Gets the manifest details.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest details
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestDetailsWithFetchProfile(ManifestBaseTO manifestBaseTO) throws CGSystemException;
	
	/**
	 * Gets the manifest id by manifest no and type and origin.
	 *
	 * @param baseTO the base to
	 * @return the manifest id by manifest no and type and origin
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdByManifestNoAndTypeAndOrigin(ManifestBaseTO baseTO)throws CGSystemException;

	/**
	 * Checks if is bpl dox parcel.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return true, if is bpl dox parcel
	 * @throws CGSystemException the cG system exception
	 */
	boolean isBplDoxParcel(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;

	/**
	 * Gets the manifest id by manifest.
	 *
	 * @param manifestBaseTO the manifest base to
	 * @return the manifest id by manifest
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdByManifest(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;

	/**
	 * Gets the manifest id from cn manifest.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest id from cn manifest
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGSystemException;

	/**
	 * Save or merge manifest.
	 *
	 * @param manifestDO the manifest do
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO saveOrMergeManifest(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * Gets the manifest dtls by consg no operating office.
	 *
	 * @param inManifestTO the in manifest to
	 * @return the manifest dtls by consg no operating office
	 * @throws CGSystemException the cG system exception
	 */
	Object[] getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGSystemException;

	/**
	 * Gets the booking office id by consg no.
	 *
	 * @param consgNo the consg no
	 * @return the booking office id by consg no
	 * @throws CGSystemException the cG system exception
	 */
	Integer getBookingOfficeIdByConsgNo(String consgNo) throws CGSystemException;

	/**
	 * Checks if is manifest out manifested.
	 *
	 * @param manifestDO the manifest do
	 * @return true, if is manifest out manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestOutManifested(ManifestDO manifestDO) throws CGSystemException;

	/**
	 * Gets the mnfst open type and bpl mnfst type.
	 *
	 * @param inManifestDO the in manifest do
	 * @return the mnfst open type and bpl mnfst type
	 * @throws CGSystemException the CG system exception
	 */
	List<?> getMnfstOpenTypeAndBplMnfstType(ManifestDO inManifestDO)
			throws CGSystemException;
}
