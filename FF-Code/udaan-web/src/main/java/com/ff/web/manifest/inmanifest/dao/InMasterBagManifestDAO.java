package com.ff.web.manifest.inmanifest.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InMasterBagManifestDAO.
 *
 * @author nkattung
 */
public interface InMasterBagManifestDAO {

	/**
	 * gets the mbpl and bpl manifest details for given mbpl number.
	 *
	 * @param manifestInputTO the manifest input to
	 * @return ManifestDO
	 * @throws CGSystemException if any database error occurs
	 */
	ManifestDO searchManifestDtls(InMasterBagManifestTO manifestInputTO) throws CGSystemException;

	/**
	 * gets embedded manifest details.
	 *
	 * @param manifestInputTO the manifest input to
	 * @return list of ManifestDO
	 * @throws CGSystemException if any database error occurs
	 */
	List<ManifestDO> getEmbeddedManifestDtls(
			InMasterBagManifestTO manifestInputTO) throws CGSystemException;

}
