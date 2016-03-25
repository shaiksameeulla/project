package com.ff.admin.complaints.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.domain.complaints.ServiceRequestPapersDO;

public class PaperworkDAOImpl extends CGBaseDAO implements PaperworkDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaperworkDAOImpl.class);

	@Override
	public boolean saveOrUpdatePaperwork(ServiceRequestPapersDO paperworkDO)
			throws CGSystemException {
		LOGGER.trace("PaperworkDAOImpl :: saveOrUpdatePaperwork() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(paperworkDO);
			LOGGER.trace("PaperworkDAOImpl :: saveOrUpdatePaperwork() ");
			tx.commit();
			isSaved=Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			isSaved = Boolean.FALSE;
			LOGGER.error(
					"Error occured in PaperworkDAOImpl :: saveOrUpdatePaperwork()..:",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("PaperworkDAOImpl :: saveOrUpdatePaperwork() :: END --------> ::::::");
		return isSaved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceRequestPapersDO> getPaperworkdetails(
			Integer serviceRequestId) throws CGSystemException {
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: Start --------> ::::::");
		List<ServiceRequestPapersDO> paperDOsList = null;
		// ServiceRequestPapersDO paperDO = null;
		try {
			String[] paramNames = { "serviceRequestId" };
			Object[] values = { serviceRequestId };
			paperDOsList = (List<ServiceRequestPapersDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getPaperworkByServiceRequest", paramNames, values);
			/*
			 * if(!CGCollectionUtils.isEmpty(paperDOsList)){ paperDO =
			 * paperDOsList.get(0); }
			 */
		} catch (Exception e) {
			LOGGER.error("ERROR : PaperworkDAOImpl.getPaperworkdetails", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: END --------> ::::::");
		return paperDOsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceRequestPapersDO getPaperworkFile(Integer paperWorkId)
			throws CGSystemException {
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: START");
		ServiceRequestPapersDO paperDO = null;
		try {
			String[] params = { ComplaintsCommonConstants.PARAM_PAPER_WORK_ID };
			Object[] values = { paperWorkId };
			List<ServiceRequestPapersDO> paperDOsList = (List<ServiceRequestPapersDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ComplaintsCommonConstants.QRY_GET_PAPER_WORK_BY_PAPER_WORK_ID,
							params, values);
			if (!CGCollectionUtils.isEmpty(paperDOsList)) {
				paperDO = paperDOsList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : PaperworkDAOImpl.getPaperworkdetails", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PaperworkDAOImpl :: getPaperworkdetails() :: END");
		return paperDO;
	}

}
