package com.ff.web.manifest.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;

/**
 * The Class OutManifestDoxDAOImpl.
 */
public class OutManifestDoxDAOImpl extends CGBaseDAO implements
		OutManifestDoxDAO {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestDoxDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.OutManifestDoxDAO#saveOrUpdateManifest(java.util.List)
	 */
	public List<ManifestDO> saveOrUpdateManifest(List<ManifestDO> manifestDtls)
			throws CGSystemException{
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifest() :: START-------->:::::::");
		List<ManifestDO> manifestDOList=null;
		try {
			manifestDOList=new ArrayList<>();
			for (ManifestDO manifestDO : manifestDtls) {				
				getHibernateTemplate().saveOrUpdate(manifestDO);
				manifestDOList.add(manifestDO);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestDoxDAOImpl :: saveOrUpdateManifest()..:"
					+ e.getMessage());
			throw new CGSystemException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED,e);
		}
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifest() :: END-------->:::::::");
		return manifestDOList;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.OutManifestDoxDAO#saveOrUpdateManifest(com.ff.domain.manifest.ManifestDO)
	 */
	public ManifestDO saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifest() :: START-------->:::::::");
		try {
			getHibernateTemplate().saveOrUpdate(manifestDO);			
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestDoxDAOImpl :: saveOrUpdateManifest()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifest() :: END-------->:::::::");
		return manifestDO;
	}
	
	/*
	 * @see com.ff.web.manifest.dao.OutManifestDoxDAO#saveOrUpdateManifest(com.ff.domain.manifest.ManifestDO)
	 */
	public ManifestDO saveOrUpdateOutManifestDox(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateOutManifestDox() :: START-------->:::::::");
		try {
			getHibernateTemplate().merge(manifestDO);			
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestDoxDAOImpl :: saveOrUpdateOutManifestDox()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateOutManifestDox() :: END-------->:::::::");
		return manifestDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.OutManifestDoxDAO#saveOrUpdateManifestProcess(java.util.List)
	 */
	/*public List<ManifestProcessDO> saveOrUpdateManifestProcess(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException {
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifestProcess() :: START-------->:::::::");
		//boolean transStatus = Boolean.FALSE;
		try {
			for (ManifestProcessDO manifestProcessDO : manifestProcessDOs) {
				getHibernateTemplate().saveOrUpdate(manifestProcessDO);
			}
			//transStatus = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestDoxDAOImpl :: saveOrUpdateManifestProcess()..:"
					+ e.getMessage());
			throw new CGSystemException(ManifestErrorCodesConstants.MANIFEST_NOT_SAVED,e);
		}
		LOGGER.trace("OutManifestDoxDAOImpl :: saveOrUpdateManifestProcess() :: END-------->:::::::");
		return manifestProcessDOs;
	}*/
	
}
