package com.ff.web.manifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProductMapTO;
import com.ff.manifest.OutManifestValidate;

// TODO: Auto-generated Javadoc
/**
 * The Interface OutManifestCommonDAO.
 */
public interface OutManifestCommonDAO {

	/**
	 * To populate list of Load No.
	 * 
	 * @return loadLotDOList
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<LoadLotDO> getLoadNo() throws CGSystemException;

	/**
	 * Checks if is consgn no manifested.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is consgn no manifested
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgnNoManifested(OutManifestValidate manifestValidateTO)
			throws CGSystemException;

	/**
	 * Checks if is manifest embedded in.
	 * 
	 * @param ManifestInputs
	 *            the manifest inputs
	 * @return true, if is manifest embedded in
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isManifestEmbeddedIn(ManifestInputs ManifestInputs)
			throws CGSystemException;

	/**
	 * Gets the manifest product map dtls.
	 * 
	 * @param MnfstProductMapTO
	 *            the mnfst product map to
	 * @return the manifest product map dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<Object[]> getManifestProductMapDtls(
			ManifestProductMapTO MnfstProductMapTO) throws CGSystemException;

	/**
	 * Checks if is consgn no in manifested.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return true, if is consgn no in manifested
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgnNoInManifested(OutManifestValidate manifestValidateTO)
			throws CGSystemException;

	/**
	 * Checks if is embedded type is of in manifest.
	 * 
	 * @param manifestEmbededId
	 *            the manifest embeded id
	 * @return true, if is embedded type is of in manifest
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isEmbeddedTypeIsOfInManifest(Integer manifestEmbededId)
			throws CGSystemException;

	public ManifestDO getManifestById(Integer manifestId)
			throws CGSystemException;

	public RateComponentDO getRateComponentByCode(String rateCompCode)
			throws CGSystemException;

	public ComailDO getComailsByComailNo(String comailNo)
			throws CGSystemException;

	/**
	 * To get Unique Manifest
	 * 
	 * @param manifestDO
	 * @return manifestDO
	 * @throws CGSystemException
	 */
	public ManifestDO getUniqueManifest(ManifestDO manifestDO)
			throws CGSystemException;
	
	/**
	 * Checks if is consgn no manifested for branch manifest.
	 *
	 * @param manifestValidateTO the manifest validate to
	 * @return true, if is consgn no manifested for branch manifest
	 * @throws CGSystemException the cG system exception
	 */
	public boolean isConsgnNoManifestedForBranchManifest(OutManifestValidate manifestValidateTO)
			throws CGSystemException;

	/**
	 * Checks if is consg. no. in misrouted in logged in office or not.
	 * 
	 * @param manifestValidateTO
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean isConsgnNoMisroute(OutManifestValidate manifestValidateTO) 
			throws CGSystemException;
	
	/**
	 * Checks if is consgn no manifested for third party.
	 *
	 * @param manifestValidateTO the manifest validate to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<ConsignmentManifestDO> isConsgnNoManifestedForThirdParty(OutManifestValidate manifestValidateTO)
			throws CGSystemException;

	public Integer getNoOfElementsFromIn(String manifestNo)
			throws CGSystemException;

	public Integer getManifestIdByNo(String manifestNo)
			throws CGSystemException;

	/**
	 * Gets the manifest dtls by consg no login office.
	 *
	 * @param manifestValidateTO the manifest validate to
	 * @return the manifest dtls by consg no login office
	 * @throws CGSystemException the cG system exception
	 */
	public Object[] getManifestDtlsByConsgNoLoginOffice(
			OutManifestValidate manifestValidateTO) throws CGSystemException;
}
