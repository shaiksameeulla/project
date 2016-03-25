package com.ff.admin.heldup.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.ff.admin.heldup.constants.HeldUpConstants;
import com.ff.admin.heldup.converter.HeldUpConverter;
import com.ff.admin.heldup.dao.HeldUpDAO;
import com.ff.domain.heldup.HeldUpDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.heldup.HeldUpTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;

/**
 * The Class HeldUpServiceImpl.
 *
 * @author narmdr
 */
public class HeldUpServiceImpl implements HeldUpService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(HeldUpServiceImpl.class);
	
	/** The held up dao. */
	private transient HeldUpDAO heldUpDAO;
	
	/** The service offering common service. */
	private transient ServiceOfferingCommonService serviceOfferingCommonService;
	
	/** The organization common service. */
	private transient OrganizationCommonService organizationCommonService;
	
	/** The global universal service. */
	private transient GlobalUniversalService globalUniversalService;
	
	/** The tracking universal service. */
	private transient TrackingUniversalService trackingUniversalService;

	/**
	 * Sets the held up dao.
	 *
	 * @param heldUpDAO the heldUpDAO to set
	 */
	public void setHeldUpDAO(HeldUpDAO heldUpDAO) {
		this.heldUpDAO = heldUpDAO;
	}
	
	/**
	 * Sets the service offering common service.
	 *
	 * @param serviceOfferingCommonService the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Sets the organization common service.
	 *
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the global universal service.
	 *
	 * @param globalUniversalService the globalUniversalService to set
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	/**
	 * Sets the tracking universal service.
	 *
	 * @param trackingUniversalService the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#getEmployeesOfOffice(com.ff.organization.OfficeTO)
	 */
	@Override
	public List<EmployeeTO> getEmployeesOfOffice(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getEmployeesOfOffice(officeTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#getReasonsByReasonType(com.ff.to.serviceofferings.ReasonTO)
	 */
	@Override
	public List<ReasonTO> getReasonsByReasonType(final ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getReasonsByReasonType(reasonTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#getStdTypesByTypeName(java.lang.String)
	 */
	@Override
	public List<StockStandardTypeTO> getStdTypesByTypeName(final String typeName)
			throws CGBusinessException, CGSystemException {
		return globalUniversalService.getStandardTypesByTypeName(typeName);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#saveOrUpdateHeldUp(com.ff.to.heldup.HeldUpTO)
	 */
	@Override
	public void saveOrUpdateHeldUp(final HeldUpTO heldUpTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("HeldUpServiceImpl::saveOrUpdateHeldUp::START------------>:::::::");
		try{
			HeldUpDO heldUpDO = HeldUpConverter.heldUpDomainConverter(heldUpTO);
			heldUpDAO.saveOrUpdateHeldUp(heldUpDO);
		}catch(Exception e){
			LOGGER.error("HeldUpServiceImpl::saveOrUpdateHeldUp::Exception happened in saveOrUpdateHeldUp of HeldUpServiceImpl...", e);
			ExceptionUtil.prepareBusinessException(HeldUpConstants.ERROR_IN_SAVING_HELD_UP);
		}
		
		//Commented Because ProcessMap is no where using
		/*try {
			ProcessMapTO processMapTO = prepareProcessmapTO(heldUpTO);
			trackingUniversalService.saveProcessMap(processMapTO,
					heldUpTO.getOfficeTO(), null, null);
		} catch (Exception e) {
		}*/
		LOGGER.trace("HeldUpServiceImpl::saveOrUpdateHeldUp::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#getProcess(com.ff.tracking.ProcessTO)
	 */
	@Override
	public ProcessTO getProcess(final ProcessTO processTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService.getProcess(processTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.heldup.service.HeldUpService#findHeldUpNumber(com.ff.to.heldup.HeldUpTO)
	 */
	@Override
	public HeldUpTO findHeldUpNumber(final HeldUpTO heldUpTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("HeldUpServiceImpl::findHeldUpNumber::START------------>:::::::");
		HeldUpDO heldUpDO = heldUpDAO.findHeldUpNumber(heldUpTO);
		HeldUpTO heldUpTO1 = null;
		if (heldUpDO != null) {
			heldUpTO1 = HeldUpConverter.heldUpTransferConverter(heldUpDO);
			heldUpTO1.setIsFind(Boolean.TRUE);
		} else {			
			// throw new CGBusinessException(	HeldUpConstants.INVALID_HELD_UP_NUMBER);
			ExceptionUtil.prepareBusinessException(HeldUpConstants.INVALID_HELD_UP_NUMBER, new String[]{heldUpTO.getHeldUpNumber()});
		}
		LOGGER.trace("HeldUpServiceImpl::findHeldUpNumber::END------------>:::::::");
		return heldUpTO1;
	}

	@Override
	public String createProcessNumber(final ProcessTO processTO, final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService.createProcessNumber(processTO,officeTO);
	}
	
}
