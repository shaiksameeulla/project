package com.ff.universe.manifest.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.manifest.ManifestIssueValidationTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;

/**
 * The Interface ManifestUniversalService.
 */
public interface ManifestUniversalService {

	/**
	 * Checks if is consignment closed.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param manifestDirection
	 *            the manifest direction
	 * @param manifestStatus
	 *            the manifest status
	 * @return true, if is consignment closed
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is comail number used.
	 * 
	 * @param comailNo
	 *            the comail no
	 * @return true, if is comail number used
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isComailNumberUsed(String comailNo, Integer manifestId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is manifest number used.
	 * 
	 * @param Manifest
	 *            Number
	 * @return true, Manifest Number used
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * */
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGBusinessException,
			CGSystemException;

	/**
	 * Checks if is isManifesIssued issues to region or not.
	 * 
	 * @param String
	 *            manifestNo,String seriesType
	 * @return true, Manifest Number issued
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * */
	public boolean isManifesIssued(
			ManifestIssueValidationTO stockIssueValidationTO)
			throws CGBusinessException, CGSystemException;

	boolean isValiedBagLockNo(String bagLockNo) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * @param validationTo
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean isStockCancel(String stockItemNumber)
			throws CGBusinessException, CGSystemException;

}
