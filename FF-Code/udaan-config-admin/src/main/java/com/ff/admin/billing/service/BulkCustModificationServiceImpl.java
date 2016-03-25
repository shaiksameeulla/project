package com.ff.admin.billing.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.dao.BulkCustModificationDAO;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.domain.geography.CityDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.universe.geography.service.GeographyCommonService;

public class BulkCustModificationServiceImpl implements BulkCustModificationService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkCustModificationServiceImpl.class);
	private BulkCustModificationDAO bulkCustModificationDAO;

	/** The GeographyCommonService */
	private GeographyCommonService geographyCommonService;

	public void setBulkCustModificationDAO(
			BulkCustModificationDAO bulkCustModificationDAO) {
		this.bulkCustModificationDAO = bulkCustModificationDAO; 
	}

	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public List<RegionTO> getRegions() throws CGSystemException,
	CGBusinessException {
		LOGGER.debug("BulkCustModificationServiceImpl::getRegions::START----->");
		try {
			return geographyCommonService.getAllRegions();
		} catch (Exception ex) {
			LOGGER.error("ERROR : BulkCustModificationServiceImpl::getRegions", ex);
			throw new CGBusinessException(AdminErrorConstants.NO_REGION_FOUND,
					ex);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCitysByStateId(Integer stateId)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BulkCustModificationServiceImpl::getCitysByStateId::START----->");
		List<CityTO> cityTO = null;
		try {
			List<CityDO> cityDO = bulkCustModificationDAO.getCitysByStateId(stateId);

			if (!CGCollectionUtils.isEmpty(cityDO)) {

				cityTO = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDO, CityTO.class);
			} else {
				ExceptionUtil
				.prepareBusinessException(BillingConstants.CITIES_NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : BulkCustModificationServiceImpl::getCitysByStateId",
					ex);
			throw ex;
		}
		LOGGER.debug("BulkCustModificationServiceImpl::getCitysByStateId::END----->");
		return cityTO;

	}

}
