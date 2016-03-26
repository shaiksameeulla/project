package com.ff.web.manifest.misroute.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;

/**
 * @author preegupt
 * 
 */
public class MisrouteDAOImpl extends CGBaseDAO implements MisrouteDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MisrouteDAOImpl.class);


	@Override
	public String getRemarks(Integer manifestId, Integer consgId)
			throws CGBusinessException, CGSystemException {
		String remark = "";
		Session session= null;
		try {
			session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_GET_REMARKS);
			query.setInteger(ManifestUniversalConstants.CONSIGNMENT_ID, consgId);
			query.setInteger(ManifestUniversalConstants.MANIFEST_ID, manifestId);
			@SuppressWarnings("unchecked")
			List<String> remarks = query.list();
			remark = remarks.get(0);

		} catch (Exception e) {
			LOGGER.error("Error occured in MisrouteDAOImpl :: deleteMisrouteManifest()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally {
			session.flush();
			session.close();
		}
		return remark;
	}

}
