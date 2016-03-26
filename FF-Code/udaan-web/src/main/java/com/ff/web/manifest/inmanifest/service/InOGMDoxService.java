/**
 * 
 */
package com.ff.web.manifest.inmanifest.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InOGMDoxService.
 *
 * @author uchauhan
 */
public interface InOGMDoxService {
	
	/**
	 * gets the details for given packet number and its corresponding consignments.
	 *
	 * @param baseTO with ogm number
	 * @return InManifestOGMTO
	 * @throws CGSystemException if any database failure occurs
	 * @throws CGBusinessException if any business rule fails
	 */
	public InManifestOGMTO getConsgManifestedDetails(ManifestBaseTO baseTO) throws CGSystemException, CGBusinessException ;
	
	/**
	 * save the InOGMDox details.
	 *
	 * @param ogmDoxTO the ogm dox to
	 * @return String with success or failure message
	 * @throws CGBusinessException if any business rule fails
	 * @throws CGSystemException if any database failure occurs
	 */
	public InManifestOGMTO saveOrUpdateInOGMDox(InManifestOGMTO ogmDoxTO) throws CGBusinessException, CGSystemException ;

	/**
	 * Save or update consignment.
	 *
	 * @param consignmentDO the consignment do
	 * @return the consignment do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update comail.
	 *
	 * @param comailDO the comail do
	 * @return the comail do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public ComailDO saveOrUpdateComail(ComailDO comailDO) throws CGBusinessException, CGSystemException ;

	/**
	 * validates if the consignment Number is already manifested
	 * @param ogmTO populated with consgNumber
	 * @return InManifestOGMTO
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public InManifestOGMTO isConsgManifested(InManifestOGMTO ogmTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consg details.
	 *
	 * @param inBagManifestTO the in bag manifest to
	 * @return the consg details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public ConsignmentTO getConsgDetails(InBagManifestTO inBagManifestTO)
			throws CGBusinessException, CGSystemException;

	public InManifestValidationTO validateCoMailNumber(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException;
}
