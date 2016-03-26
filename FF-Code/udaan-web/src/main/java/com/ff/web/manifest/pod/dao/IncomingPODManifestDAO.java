/**
 * 
 */
package com.ff.web.manifest.pod.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ConsignmentManifestDO;

/**
 * @author nkattung
 * 
 */
public interface IncomingPODManifestDAO {

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return ConsignmentManifestDO
	 * @throws CGSystemException
	 */
	public ConsignmentManifestDO getOutgoingPODConsgDtls(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode, Integer destOffid)
			throws CGSystemException;

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return ConsignmentManifestDO
	 * @throws CGSystemException
	 */
	public boolean isConsgnmentBelongsToManifest(String consgNumber,
			String manifestNo) throws CGSystemException;

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestType
	 * @param manifestPorcessCode
	 * @return ConsignmentManifestDO
	 * @throws CGSystemException
	 */
	public List<String> getConsignmentsOfManifest(String manifestNo)
			throws CGSystemException;

}
