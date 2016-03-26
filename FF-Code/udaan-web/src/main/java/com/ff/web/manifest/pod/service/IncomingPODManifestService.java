/**
 * 
 */
package com.ff.web.manifest.pod.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.pod.PODConsignmentDtlsTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;

/**
 * @author nkattung
 * 
 */
public interface IncomingPODManifestService {
	/**
	 * @param consignment
	 * @param officeId
	 * @param ManifestType
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PODManifestDtlsTO getOutgoingPODConsgDtls(String consignment,
			Integer officeId, String ManifestType, String manifestNo)
			throws CGBusinessException, CGSystemException;

	public String saveOrUpdateIncomingPODMnfst(PODManifestTO podManifestTO)
			throws CGBusinessException, CGSystemException;

	public String isConsgnmentBelongsToManifest(String consgNumber,
			String manifestNo) throws CGSystemException;

	public PODConsignmentDtlsTO podConsignmentDtls(String consgNumbers,
			String manifestNo) throws CGSystemException, CGBusinessException;
}
