package com.ff.admin.heldup.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.heldup.constants.HeldUpConstants;
import com.ff.domain.heldup.HeldUpDO;
import com.ff.to.heldup.HeldUpTO;

/**
 * The Class HeldUpDAOImpl.
 * 
 * @author narmdr
 */
public class HeldUpDAOImpl extends CGBaseDAO implements HeldUpDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(HeldUpDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.dao.HeldUpDAO#saveOrUpdateHeldUp(com.ff.domain.heldup.HeldUpDO)
	 */
	@Override
	public void saveOrUpdateHeldUp(final HeldUpDO heldUpDO) throws CGSystemException {
		LOGGER.debug("HeldUpDAOImpl :: saveOrUpdateHeldUp() :: Start --------> ::::::");
		try{
			getHibernateTemplate().saveOrUpdate(heldUpDO);
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::HeldUpDAOImpl::saveOrUpdateHeldUp() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("HeldUpDAOImpl :: saveOrUpdateHeldUp() :: End --------> ::::::");		
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.dao.HeldUpDAO#findHeldUpNumber(com.ff.to.heldup.HeldUpTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HeldUpDO findHeldUpNumber(final HeldUpTO heldUpTO)
			throws CGSystemException {
		LOGGER.debug("HeldUpDAOImpl :: findHeldUpNumber() :: Start --------> ::::::");
		HeldUpDO heldUpDO = null;
		try {
			List<HeldUpDO> heldUpDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							HeldUpConstants.QRY_GET_HELD_UP_BY_HELD_UP_NO_AND_OFFICE_ID,
							new String[] { HeldUpConstants.PARAM_OFFICE_ID,
									HeldUpConstants.PARAM_HELD_UP_NUMBER },
							new Object[] {
									heldUpTO.getOfficeTO().getOfficeId(),
									heldUpTO.getHeldUpNumber() });
			heldUpDO = !StringUtil.isEmptyList(heldUpDOs) ? heldUpDOs.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::HeldUpDAOImpl::findHeldUpNumber() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("HeldUpDAOImpl :: findHeldUpNumber() :: End --------> ::::::");
		return heldUpDO;
	}

}
