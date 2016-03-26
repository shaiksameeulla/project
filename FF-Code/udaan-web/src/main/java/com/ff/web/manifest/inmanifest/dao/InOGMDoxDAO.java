/**
 * 
 */
package com.ff.web.manifest.inmanifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InOGMDoxDAO.
 *
 * @author uchauhan
 */
public interface InOGMDoxDAO {
	
	/**
	 * gets the list of consignments for given manifest number.
	 *
	 * @param baseTO set with manifest number
	 * @return list of consignments
	 * @throws CGSystemException the cG system exception
	 */
	public List<ConsignmentDO> getConsgManifestedDetails(ManifestBaseTO baseTO) throws CGSystemException;

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
	 * Save or update comail.
	 *
	 * @param comailDO the comail do
	 * @return the comail do
	 * @throws CGSystemException the cG system exception
	 */
	public ComailDO saveOrUpdateComail(ComailDO comailDO) throws CGSystemException;

	/**
	 * validates if consignment is already inmanifested
	 * @param ogmTO
	 * @return
	 * @throws CGSystemException
	 */
	public ConsignmentManifestDO isConsgManifested(InManifestOGMTO ogmTO) throws CGSystemException;

}
