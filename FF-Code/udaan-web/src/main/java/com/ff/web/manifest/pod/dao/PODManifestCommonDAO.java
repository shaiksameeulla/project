/**
 * 
 */
package com.ff.web.manifest.pod.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;

/**
 * @author nkattung
 * 
 */
public interface PODManifestCommonDAO {
	/**
	 * To save or update POD Manifest Data
	 * 
	 * @param manifestDO
	 * @return
	 * @throws CGSystemException
	 */
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException;

	/**
	 * @param manifestProcessDOs
	 * @return
	 * @throws CGSystemException
	 */
/*	public boolean saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException;*/

	/**
	 * Seach manifest details
	 * 
	 * @param manifestNo
	 * @param manifestType
	 * @param processCode
	 * @return
	 * @throws CGSystemException
	 */
	public ManifestDO searchManifestDtls(String manifestNo,
			String manifestType, String processCode, Integer orginOfficeId)
			throws CGSystemException;

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return
	 * @throws CGSystemException
	 */
	public boolean isConsgnNoManifested(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException;

	/**
	 * @param manifestNo
	 * @param manifestType
	 * @param processCode
	 * @param orginOfficeId
	 * @return
	 * @throws CGSystemException
	 */
	public ManifestDO searchOutgoingPODManifetsDtls(String manifestNo,
			String manifestType, String processCode, Integer orginOfficeId)throws CGSystemException;
	
	public DeliveryDetailsDO getDeliverdConsgDtlsForDestBranchToDestHub(String consignment,
			Integer orgOfficeId) throws CGSystemException;
	
	public List<ConsignmentManifestDO> isConsgnNoManifestedToDestHub(String consignment,
			String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException;
	
	
	public List<ConsignmentManifestDO> isConsgnNoInManifestedAtLoggdInOffc(String consignment,
			String podManifestType, String manifestProcessCode,Integer officeId) throws CGSystemException;

	public DeliveryDetailsDO getConsignmentDetailsForParentCN(
			String consignment, Integer officeId)throws CGSystemException;

}
