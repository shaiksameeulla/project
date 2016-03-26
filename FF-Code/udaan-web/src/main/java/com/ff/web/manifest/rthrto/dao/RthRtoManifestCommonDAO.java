package com.ff.web.manifest.rthrto.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.rthrto.ConsignmentValidationTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoManifestCommonDAO.
 */
public interface RthRtoManifestCommonDAO {
	
	/**
	 * Save or update manifest.
	 *
	 * @param manifestDO the manifest do
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * Save or update manifest process.
	 *
	 * @param manifestProcessDO the manifest process do
	 * @return the manifest process do
	 * @throws CGSystemException the cG system exception
	 */
//	public ManifestProcessDO saveOrUpdateManifestProcess(
//			ManifestProcessDO manifestProcessDO) throws CGSystemException;

	/**
	 * Search rtoh manifest details.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO searchRTOHManifestDetails(ManifestInputs manifestTO)
			throws CGSystemException;

	/**
	 * Gets the consg return by consg no office id return type.
	 *
	 * @param consignmentNo the consignment no
	 * @param originOffice the origin office
	 * @param returnType the return type
	 * @return the consg return by consg no office id return type
	 * @throws CGSystemException the cG system exception
	 */
	public ConsignmentReturnDO getConsgReturnByConsgNoOfficeIdReturnType(
			String consignmentNo, Integer originOffice, String returnType)
			throws CGSystemException;

	/**
	 * To check whether consignment is validated or not for 
	 * RTH/RTO process
	 * 
	 * @param consgValidationTO
	 * @return boolean
	 * @throws CGSystemException
	 */
	public Integer isConsgNoValidated(ConsignmentValidationTO 
			consgValidationTO) throws CGSystemException;

	/**
	 * To check whether is RTH/RTO manifest No. manifested or not
	 * 
	 * @param manifestTO
	 * @return manifestDOs
	 * @throws CGSystemException
	 */
	List<ManifestDO> isRtohNoManifested(ManifestInputs manifestTO)
			throws CGSystemException;
	
	public ReasonDO validateConsForBranchOut(String returnType,Integer loginOffcId,String  consNo)
			throws CGSystemException;
	
	public ProductDO getProductDetails(Integer productId)throws CGSystemException;
	
	public ConsignmentBillingRateDO getAlreadyExistConsgRtoRate(ConsignmentDO consingnment,String rateFor)throws CGSystemException;
	
	public Boolean saveOrUpdateConsgRtoRate(List<ConsignmentBillingRateDO> consignmentBillingRate)throws CGSystemException;

	public boolean isCNStopDelivered(ConsignmentValidationTO consigValidationTO) throws CGSystemException;

	public Object[] getManifestDtlsByConsgNoOperatingOffice(
			ConsignmentValidationTO consignmentValidationTO) throws CGSystemException;
}
