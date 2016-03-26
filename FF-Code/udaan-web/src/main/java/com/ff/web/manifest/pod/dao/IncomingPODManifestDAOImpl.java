/**
 * 
 */
package com.ff.web.manifest.pod.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;

/**
 * @author nkattung
 * 
 */
public class IncomingPODManifestDAOImpl extends CGBaseDAO implements
		IncomingPODManifestDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(IncomingPODManifestDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.dao.IncomingPODManifestDAOImpl#
	 * getManifestedConsingmentDtls( String consgNumber, String
	 * manifestDirection, String manifestType, String manifestPorcessCode,
	 * Integer destOffId)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ConsignmentManifestDO getOutgoingPODConsgDtls(String consgNumber,
			String manifestDirection, String manifestType,
			String manifestPorcessCode, Integer destOffId)
			throws CGSystemException {
		LOGGER.trace("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls():::: START");
		ConsignmentManifestDO manifestConsgDtls = null;
		List<ConsignmentManifestDO> manifestCosgDtlsDOs = null;
		try {
			String queryName = PODManifestConstants.QRY_GET_OUTGOING_POD_CONSG_DETAILS;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					PODManifestConstants.PARAM_DEST_OFF_ID };
			Object[] values = { consgNumber, manifestDirection, manifestType,
					manifestPorcessCode, destOffId };
			manifestCosgDtlsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
			if (!StringUtil.isEmptyList(manifestCosgDtlsDOs))
				manifestConsgDtls = manifestCosgDtlsDOs.get(0);
		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("PODManifestCommonDAOImpl :: getOutgoingPODConsgDtls():::: END");
		return manifestConsgDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isConsgnmentBelongsToManifest
	 * (com .ff.manifest.OutManifestValidate)
	 */
	@Override
	public boolean isConsgnmentBelongsToManifest(String consgNumber,
			String manifestNo) throws CGSystemException {
		LOGGER.trace("PODManifestCommonDAOImpl :: isConsgnmentBelongsToManifest():::: START");
		boolean isCNManifested = Boolean.FALSE;
		try {
			String queryName = PODManifestConstants.QRY_IS_CONSG_IS_BELONGS_TO_MANIFEST;
			String[] params = { OutManifestConstants.CONSIGNMENT_NO,
					OutManifestConstants.MANIFEST_DIRECTION,
					ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_NO };

			Object[] values = { consgNumber,
					PODManifestConstants.POD_MANIFEST_OUT,
					PODManifestConstants.POD_MANIFEST,
					CommonConstants.PROCESS_POD, manifestNo };
			long count = 0;
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values)
					.get(0);
			if (count > 0)
				isCNManifested = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: isConsgnmentBelongsToManifest()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("PODManifestCommonDAOImpl :: isConsgnmentBelongsToManifest():::: END");
		return isCNManifested;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.dao.OutManifestCommonDAO#isConsgnmentBelongsToManifest
	 * (com .ff.manifest.OutManifestValidate)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getConsignmentsOfManifest(String manifestNo)
			throws CGSystemException {
		LOGGER.trace("PODManifestCommonDAOImpl :: getConsignmentsOfManifest():::: START");
		List<String> consingmets = null;
		try {
			String queryName = PODManifestConstants.QRY_CONSINGMENTS_OF_MANIFEST;
			String[] params = { OutManifestConstants.MANIFEST_DIRECTION,
					ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_NO };

			Object[] values = { PODManifestConstants.POD_MANIFEST_OUT,
					PODManifestConstants.POD_MANIFEST,
					CommonConstants.PROCESS_POD, manifestNo };
			consingmets = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("PODManifestCommonDAOImpl :: getConsignmentsOfManifest()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("PODManifestCommonDAOImpl :: getConsignmentsOfManifest():::: END");
		return consingmets;
	}

}
