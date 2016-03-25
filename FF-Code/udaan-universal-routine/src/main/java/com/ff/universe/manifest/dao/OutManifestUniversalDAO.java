package com.ff.universe.manifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestUpdateInput;
import com.ff.manifest.OutManifestDetailBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OutManifestUniversalDAO.
 */
public interface OutManifestUniversalDAO {

	/**
	 * Gets the manifest dtls by manifest no.
	 * 
	 * @param manifestNumber
	 *            the manifest number
	 * @return the manifest dtls by manifest no
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestDO> getManifestDtlsByManifestNo(String manifestNumber)
			throws CGSystemException;

	/**
	 * Update manifest weight.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGSystemException;

	/**
	 * Gets the bag rf id by rf id no.
	 * 
	 * @param rfIdNo
	 *            the rf id no
	 * @return the bag rf id by rf id no
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Integer getBagRfIdByRfIdNO(String rfIdNo) throws CGSystemException;

	// Getting BagRFNo by sending BagRfId...by Reddy
	/**
	 * Gets the bag rf no by rf id.
	 * 
	 * @param rfIdNo
	 *            the rf id no
	 * @return the bag rf no by rf id
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String getBagRfNoByRfId(Integer rfIdNo) throws CGSystemException;

	public boolean updateRemarksOfMisroute(int manifestId, String remarks,
			String manifestType, int updateProcessId) throws CGSystemException;

	/**
	 * Updating the position by manifestId
	 * 
	 * @param manifestInput
	 * @return
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	public boolean updatePositionByManifestId(ManifestUpdateInput manifestInput)
			throws CGSystemException;

	/**
	 * Update consg weight.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	boolean updateConsgWeight(OutManifestDetailBaseTO to, Integer operatingLevel)
			throws CGSystemException;

	/**
	 * Gets the manifest dtls by manifest no origin off id.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the manifest dtls by manifest no origin off id
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestDO> getManifestDtlsByManifestNoOriginOffId(
			ManifestTO manifestTO) throws CGSystemException;

	/**
	 * To check if Consignment is retured due to party shifted reason
	 * 
	 * @param consgId
	 * @param processId
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	public boolean isPartyShiftedConsg(Integer consgId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the cons dtls.
	 * 
	 * @param consgNumbers
	 *            the consg numbers
	 * @param consgType
	 *            the consg type
	 * @return the cons dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<ConsignmentDO> getConsDtls(List<String> consgNumbers,
			String consgType) throws CGSystemException;

	/**
	 * update ProcessId Id Details.
	 * 
	 * @param manifestInput
	 *            the manifest input
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 */

	public boolean updateProcessIdDetails(
			ManifestUpdateInput manifestUpdateInput) throws CGSystemException;

	/**
	 * To get process details
	 * 
	 * @param processCode
	 * @return ProcessDO
	 * @throws CGSystemException
	 */
	public ProcessDO getProcess(String processCode) throws CGSystemException;

	/**
	 * To save or update consignment billing rates
	 * 
	 * @param consignmentBillingRateDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGSystemException;

}
