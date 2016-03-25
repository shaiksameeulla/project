package com.ff.universe.manifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.misroute.MisrouteDetailsTO;

/**
 * The Interface ManifestUniversalDAO.
 */
public interface ManifestUniversalDAO {

	/**
	 * Checks if is consignment closed.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param manifestDirection
	 *            the manifest direction
	 * @param manifestStatus
	 *            the manifest status
	 * @return true, if is consignment closed
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGSystemException;

	/**
	 * Save or update manifest4 load mgmt.
	 * 
	 * @param manifestDOList
	 *            the manifest do list
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestDO> saveOrUpdateManifest4LoadMgmt(
			List<ManifestDO> manifestDOList) throws CGSystemException;

	/**
	 * Checks if is consignments manifested.
	 * 
	 * @param consgnmentList
	 *            the consgnment list
	 * @param manifestType
	 *            the manifest type
	 * @return true, if is consignments manifested
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsignmentsManifested(List<String> consgnmentList,
			String manifestType) throws CGSystemException;

	/**
	 * Validate comail.
	 * 
	 * @param comailNo
	 *            the comail no
	 * @param manifestId 
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isComailNumberUsed(String comailNo, Integer manifestId) throws CGSystemException;

	/**
	 * Validate Manifest Number.
	 * 
	 * @param manifestNo
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGSystemException;

	public ConsignmentManifestDO getConsgManifest(
			MisrouteDetailsTO misrouteDetailsTO) throws CGSystemException;

	public List<Object[]> getConsignmentManifestedDate(String manifestType,
			Integer consignmentId) throws CGSystemException;

	public List<ManifestDO> getMisrouteManifestDtls(ManifestInputs manifestInputTO)
			throws CGSystemException;

	Long getComailCountByManifestNo(String manifestNo) throws CGSystemException;

	public Long getComailCountByManifestId(Integer manifestIds)
			throws CGSystemException;

	public Long getConsgCountByManifestId(Integer manifestIds)
			throws CGSystemException;

	public ManifestDO getBoutGridManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException;

	public boolean isExistInComailTable(String comailNO);

	public Integer getComailIdByComailNo(String comailNO);

	boolean isValiedBagLockNo(String bagLockNo) throws CGSystemException;

	/*ManifestProcessDO getManifestProcess(ManifestInputs manifestTO)
			throws CGSystemException;*/

	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGSystemException ;
	
	/*public List<ManifestProcessDO> getManifestProcessDtls(
			ManifestInputs manifestTO) throws CGSystemException;*/
}
