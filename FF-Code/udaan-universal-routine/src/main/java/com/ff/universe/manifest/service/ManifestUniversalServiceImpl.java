package com.ff.universe.manifest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.manifest.ManifestIssueValidationTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.stockmanagement.service.StockUniversalService;

/**
 * The Class ManifestUniversalServiceImpl.
 */
public class ManifestUniversalServiceImpl implements ManifestUniversalService {

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;
	/** The stock universal service. */
	private StockUniversalService stockUniversalService;

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestUniversalServiceImpl.class);

	/**
	 * Sets the manifest universal dao.
	 * 
	 * @param manifestUniversalDAO
	 *            the new manifest universal dao
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/**
	 * @param stockUniversalService
	 *            the stockUniversalService to set
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.ManifestUniversalService#isConsignmentClosed
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGBusinessException, CGSystemException {
		return manifestUniversalDAO.isConsignmentClosed(consgNumber,
				manifestDirection, manifestStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.ManifestUniversalService#isComailNumberUsed
	 * (java.lang.String)
	 */
	@Override
	public boolean isComailNumberUsed(String comailNo, Integer manifestId)
			throws CGBusinessException, CGSystemException {
		return manifestUniversalDAO.isComailNumberUsed(comailNo, manifestId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.ManifestUniversalService#isComailNumberUsed
	 * (java.lang.String)
	 */
	@Override
	public boolean isManifestExists(String manifestNo,
			String manifestDirection, String manifestType,
			String manifestPorcessCode) throws CGBusinessException,
			CGSystemException {
		return manifestUniversalDAO.isManifestExists(manifestNo,
				manifestDirection, manifestType, manifestPorcessCode);
	}

	@Override
	public boolean isManifesIssued(
			ManifestIssueValidationTO manifetsIssueValidationTO)
			throws CGBusinessException, CGSystemException {

		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("ManifestUniversalServiceImpl :: isManifesIssued :: Start ::");
		logger.append(" StartTime :: " + startTimeInMilis);
		LOGGER.debug(logger.toString());

		boolean isIssued = Boolean.FALSE;
		if (!StringUtil.isNull(manifetsIssueValidationTO)) {
			StockIssueValidationTO stockIssueValidationTO = new StockIssueValidationTO();
			stockIssueValidationTO.setStockItemNumber(manifetsIssueValidationTO
					.getStockItemNumber());
			stockIssueValidationTO.setSeriesType(manifetsIssueValidationTO
					.getSeriesType());
			stockIssueValidationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockIssueValidationTO.setIssuedTOPartyId(manifetsIssueValidationTO
					.getIssuedTOPartyId());
			stockIssueValidationTO.setRegionCode(manifetsIssueValidationTO
					.getRegionCode());
			if (!StringUtil.isStringEmpty(manifetsIssueValidationTO
					.getCityCode())) {
				stockIssueValidationTO.setCityCode(manifetsIssueValidationTO
						.getCityCode());
			}
			stockIssueValidationTO = stockUniversalService
					.validateStock(stockIssueValidationTO);
			if (!stockIssueValidationTO.getIsIssuedTOParty()) {
				throw new CGBusinessException(
						UniversalErrorConstants.MANIFEST_NOT_ISSUED_REGION);
			} else
				isIssued = Boolean.TRUE;
		}
		long endTimeInMilis = System.currentTimeMillis();

		LOGGER.debug("ManifestUniversalServiceImpl :: isManifesIssued :: End"
				+ " End Time ::"
				+ endTimeInMilis
				+ " Time Difference in miliseconds:: "+(endTimeInMilis-startTimeInMilis)
				+" Time Difference in HH:MM:SS :: "
				+ DateUtil
						.convertMilliSecondsTOHHMMSSStringFormat(endTimeInMilis
								- startTimeInMilis));
		return isIssued;
	}

	@Override
	public boolean isValiedBagLockNo(String bagLockNo)
			throws CGBusinessException, CGSystemException {
		return manifestUniversalDAO.isValiedBagLockNo(bagLockNo);
	}

	@Override
	public boolean isStockCancel(String stockItemNumber)
			throws CGBusinessException, CGSystemException {
		StockIssueValidationTO validationTo = new StockIssueValidationTO();
		validationTo.setStockItemNumber(stockItemNumber);
		boolean isStockCancel = stockUniversalService
				.isSeriesCancelledForIntegration(validationTo);
		if (isStockCancel) {
			ExceptionUtil.prepareBusinessException(
					UniversalErrorConstants.STOCK_CANCELED,
					new String[] { validationTo.getStockItemNumber() });
		}
		return isStockCancel;
	}

}
