package com.ff.web.manifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;

/**
 * The Interface BPLOutManifestDoxDAO.
 */
public interface BPLOutManifestDoxDAO {
	
	/**
	 * Gets the bPL for dox details.
	 *
	 * @param manifestNumber the manifest number
	 * @return the bPL for dox details
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> getBPLForDoxDetails(String manifestNumber)
			throws CGSystemException;
	
	/**
	 * Gets the embeded manifest dtl.
	 *
	 * @param bplNo the bpl no
	 * @return the embeded manifest dtl
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> getEmbededManifestDtl(String bplNo)
			throws CGSystemException;
	
	/**
	 * Save or update bpl out manifest dox.
	 *
	 * @param manifestDO the manifest do
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO>  saveOrUpdateBPLOutManifestDox(List<ManifestDO> manifestDO)
			throws CGSystemException;
	
	/**
	 * Save or update manifest process dtls.
	 *
	 * @param manifestProcessDOs the manifest process d os
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	/*public boolean saveOrUpdateManifestProcessDtls(List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException;*/
	
	/**
	 * Update manifest dtls.
	 *
	 * @param manifestDO the manifest do
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean updateManifestDtls(ManifestDO manifestDO) throws CGSystemException ;
	
	/**
	 * Search manifest dtls.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO searchManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException;
	
	/**
	 * Search manifest dtlsby offic login.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest do
	 * @throws CGSystemException the cG system exception
	 */
	public ManifestDO searchManifestDtlsbyOfficLogin(ManifestInputs manifestTO)
			throws CGSystemException;
	
	public ManifestDO searchManifestDtlsFromInManifestDox(ManifestInputs manifestTO)
			throws CGSystemException;
	
	/**
	 * Gets the embedded manifest dtls by embedded id.
	 *
	 * @param manifestTO the manifest to
	 * @return the embedded manifest dtls by embedded id
	 * @throws CGSystemException the cG system exception
	 */
	public List<ManifestDO> getEmbeddedManifestDtlsByEmbeddedId(
			ManifestInputs manifestTO) throws CGSystemException;
	
	/**
	 * Gets the manifest process dtls.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest process dtls
	 * @throws CGSystemException the cG system exception
	 */
	/*public List<ManifestProcessDO> getManifestProcessDtls(
			ManifestInputs manifestTO) throws CGSystemException;
	
	public ManifestProcessDO isManifestComailed(ManifestInputs manifestTO) throws CGSystemException;*/
	
	public Boolean saveOrUpdateBPLOutManifestDox4InManifestBagDtls(ManifestDO manifestDO)
			throws CGSystemException;

	List<ManifestDO> getManifestDtlsByManifestNoAndStatus(
			ManifestInputs manifestInputsTO)throws CGSystemException;

	Object[] isManifestNumInManifested(ManifestInputs manifestInputs)throws CGSystemException;

	 Integer getOutManifestDestnOfficeId(Integer officeId,Integer manifestId)throws CGSystemException;
	 
	  ManifestDO getDirectManifestPktByManifestNo(
				ManifestInputs manifestInputsTO) throws CGSystemException ;

	  ManifestMappedEmbeddedDO saveOrUpdateEmbeddedManifest(ManifestMappedEmbeddedDO manifestDtls)
				throws CGSystemException ;

	 List<ManifestDO> getDirectPacketManifests(
			List<String> manifestNOList)throws CGSystemException ;

	 ManifestDO getManifestByManifestID(final Integer manifestId)throws CGSystemException ;

	ManifestDO getManifestDetailsWithFetchProfile(ManifestBaseTO manifestBaseTO)
			throws CGSystemException;

}
