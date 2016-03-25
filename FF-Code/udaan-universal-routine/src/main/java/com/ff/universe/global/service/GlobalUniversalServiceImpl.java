package com.ff.universe.global.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.global.dao.GlobalUniversalDAO;

public class GlobalUniversalServiceImpl implements GlobalUniversalService {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GlobalUniversalServiceImpl.class);
	private GlobalUniversalDAO globalUniversalDAO;

	public GlobalUniversalDAO getGlobalUniversalDAO() {
		return globalUniversalDAO;
	}

	public void setGlobalUniversalDAO(GlobalUniversalDAO globalUniversalDAO) {
		this.globalUniversalDAO = globalUniversalDAO;
	}

	@Override
	public Map<String, String> getConfigParams() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("GlobalUniversalServiceImpl::getConfigParams::STARTS----->");
		Map<String, String> configuraleParams = null;
		try {
			List<ConfigurableParamsDO> configParmsDOList = globalUniversalDAO
					.getConfigurabParams();
			if (!CGCollectionUtils.isEmpty(configParmsDOList)) {
				configuraleParams = new HashMap<String, String>(
						configParmsDOList.size());
				for (ConfigurableParamsDO paramDO : configParmsDOList) {
					if (StringUtil.isStringEmpty(paramDO.getParamName())) {
						continue;
					}
					configuraleParams.put(paramDO.getParamName(),
							paramDO.getParamValue());
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error occured while retrieving cConfigurabParams list", e);
			// TODO Exception handling
		}
		
		LOGGER.debug("GlobalUniversalServiceImpl::getConfigParams::ENDS----->");
		return configuraleParams;
	}

	@Override
	public String getConfigParamValueByName(String paramName)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("GlobalUniversalServiceImpl::getConfigParams:: ParamName :["
				+ paramName + "]");
		String paramValue = null;
		List<ConfigurableParamsDO> configParmsDOList = globalUniversalDAO
				.getConfigParamValueByName(paramName);
		if (!CGCollectionUtils.isEmpty(configParmsDOList)) {
			paramValue = configParmsDOList.get(0).getParamValue();
		}
		LOGGER.debug("GlobalUniversalServiceImpl::getConfigParams:: ParamName :["
				+ paramName + "] Param Value:[" + paramValue + "]");
		return paramValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.global.service.GlobalUniversalService#
	 * getStandardTypesByTypeName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> stockStandardTypeTOs = null;
		List<StockStandardTypeDO> StockStandardTypeDOList = globalUniversalDAO
				.getStandardTypesByTypeName(typeName);
		if (!StringUtil.isEmptyColletion(StockStandardTypeDOList)) {
			stockStandardTypeTOs = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(StockStandardTypeDOList,
							StockStandardTypeTO.class);
		}
		return stockStandardTypeTOs;
	}

	@Override
	public String getNumberInWords(Long enteredNo) throws CGBusinessException {
		LOGGER.trace("GlobalUniversalServiceImpl :: getNumberInWords() :: START");
		String enteredNoInWord = CommonConstants.EMPTY_STRING;
		String[] ones = { " one", " two", " three", " four", " five", " six",
				" seven", " eight", " nine", " ten", " eleven", " twelve",
				" thirteen", " fourteen", " fifteen", " sixteen", " seventeen",
				" eighteen", " nineteen" };
		String[] tens = { " twenty", " thirty", " forty", " fifty", " sixty",
				" seventy", " eighty", " ninety" };
		// So, quintillions is as big as it gets. The program would
		// automatically handle larger numbers if this array were extended.
		String[] groups = { "", " thousand", " million", " billion",
				" trillion", " quadrillion", " quintillion" };
		// Go through the number one group at a time.
		for (int i = groups.length - 1; i >= 0; i--) {
			// Is the number as big as this group?
			long cutoff = (long) Math.pow((double) 10, (double) (i * 3));
			if (enteredNo >= cutoff) {
				int thisPart = (int) (enteredNo / cutoff);
				// Use the ones[] array for both the hundreds and the ones
				// digit. Note that tens[] starts at "twenty".
				if (thisPart >= 100) {
					enteredNoInWord += ones[(thisPart / 100) - 1] + " hundred";
					thisPart = thisPart % 100;
				}
				if (thisPart >= 20) {
					enteredNoInWord += tens[(thisPart / 10) - 2];
					thisPart = thisPart % 10;
				}
				if (thisPart >= 1) {
					enteredNoInWord += ones[thisPart - 1];
				}
				enteredNoInWord += groups[i];
				// To check for big numbers which are greater than or equal to 1
				// million
				enteredNo = enteredNo % cutoff;
			}
		}
		if (enteredNoInWord.length() == 0) {
			enteredNoInWord = "zero";
		} else {
			// remove initial space
			enteredNoInWord = enteredNoInWord.substring(1);
		}
		LOGGER.trace("GlobalUniversalServiceImpl :: getNumberInWords() :: END");
		return enteredNoInWord;
	}

}
