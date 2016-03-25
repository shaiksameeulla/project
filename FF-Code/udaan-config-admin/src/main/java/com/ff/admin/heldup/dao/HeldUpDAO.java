package com.ff.admin.heldup.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.heldup.HeldUpDO;
import com.ff.to.heldup.HeldUpTO;

/**
 * The Interface HeldUpDAO.
 * 
 * @author narmdr
 */
public interface HeldUpDAO {

	/**
	 * Save or update held up.
	 *
	 * @param heldUpDO the held up do
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateHeldUp(final HeldUpDO heldUpDO) throws CGSystemException;

	/**
	 * Find held up number.
	 *
	 * @param heldUpTO the held up to
	 * @return the held up do
	 * @throws CGSystemException the cG system exception
	 */
	HeldUpDO findHeldUpNumber(final HeldUpTO heldUpTO) throws CGSystemException;

}
