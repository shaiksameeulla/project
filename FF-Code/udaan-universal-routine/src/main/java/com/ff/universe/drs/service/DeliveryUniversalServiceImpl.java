/**
 * 
 */
package com.ff.universe.drs.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DrsCollectionIntegrationWrapperDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.PaymentModeDO;
import com.ff.geography.CityTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.drs.dao.DeliveryUniversalDAO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.stockmanagement.util.StockUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryUniversalServiceImpl.
 * 
 * @author mohammes
 */
public class DeliveryUniversalServiceImpl implements DeliveryUniversalService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DeliveryUniversalServiceImpl.class);
	/** The delivery universal dao. */
	private DeliveryUniversalDAO deliveryUniversalDAO;

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The business common service. */
	private BusinessCommonService businessCommonService;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The sequence generator service. */
	private SequenceGeneratorService sequenceGeneratorService;

	/**
	 * Gets the sequence generator service.
	 *
	 * @return the sequenceGeneratorService
	 */
	public SequenceGeneratorService getSequenceGeneratorService() {
		return sequenceGeneratorService;
	}

	/**
	 * Sets the sequence generator service.
	 *
	 * @param sequenceGeneratorService the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * Gets the consignment common service.
	 *
	 * @return the consignmentCommonService
	 */
	public ConsignmentCommonService getConsignmentCommonService() {
		return consignmentCommonService;
	}

	/**
	 * Sets the consignment common service.
	 *
	 * @param consignmentCommonService the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * Gets the delivery universal dao.
	 * 
	 * @return the deliveryUniversalDAO
	 */
	public DeliveryUniversalDAO getDeliveryUniversalDAO() {
		return deliveryUniversalDAO;
	}

	/**
	 * Sets the delivery universal dao.
	 * 
	 * @param deliveryUniversalDAO
	 *            the deliveryUniversalDAO to set
	 */
	public void setDeliveryUniversalDAO(
			DeliveryUniversalDAO deliveryUniversalDAO) {
		this.deliveryUniversalDAO = deliveryUniversalDAO;
	}

	/**
	 * Gets the organization common service.
	 * 
	 * @return the organizationCommonService
	 */
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	/**
	 * Gets the geography common service.
	 * 
	 * @return the geographyCommonService
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * Gets the service offering common service.
	 * 
	 * @return the serviceOfferingCommonService
	 */
	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}

	/**
	 * Gets the business common service.
	 * 
	 * @return the businessCommonService
	 */
	public BusinessCommonService getBusinessCommonService() {
		return businessCommonService;
	}

	/**
	 * Sets the organization common service.
	 * 
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public final void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the service offering common service.
	 * 
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Sets the business common service.
	 * 
	 * @param businessCommonService
	 *            the businessCommonService to set
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getCity(com.ff.geography.CityTO)
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
	CGBusinessException {
		return geographyCommonService.getCity(cityTO);
	}

	/**
	 * Gets the cities by offices.
	 * 
	 * @param officeTo
	 *            the office to
	 * @return the cities by offices
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public CityTO getCitiesByOffices(OfficeTO officeTo)
			throws CGSystemException, CGBusinessException {
		List<OfficeTO> officeTO = new ArrayList<>(1);
		List<CityTO> cityList = null;
		CityTO cityTo = null;
		officeTO.add(officeTo);
		cityList = geographyCommonService.getCitiesByOffices(officeTO);
		if (!CGCollectionUtils.isEmpty(cityList)) {
			cityTo = cityList.get(0);
		}
		return cityTo;
	}

	/**
	 * Gets the cities by offices.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the cities by offices
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public CityTO getCitiesByOffices(Integer officeId)
			throws CGSystemException, CGBusinessException {
		OfficeTO officeTo = new OfficeTO();
		officeTo.setOfficeId(officeId);
		return getCitiesByOffices(officeTo);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getStandardTypesAsMap(java.lang.String)
	 */
	@Override
	public Map<String, String> getStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getStandardTypesAsMap(typeName);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getLoadNo()
	 */
	@Override
	public List<LoadLotTO> getLoadNo() throws CGBusinessException,
	CGSystemException {
		return serviceOfferingCommonService.getLoadNo();
	}

	/**
	 * getAllEmployeesByOfficeId : load All employees of logged in Office.
	 *
	 * @param empTo the emp to
	 * @return List<EmployeeTO>
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<EmployeeTO> getEmployeeDetails(EmployeeTO empTo)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllEmployeesUnderRegion(empTo);
	}

	/**
	 * getFranchiseeDetails : get All franchisees.
	 *
	 * @param frTo the fr to
	 * @return the franchisee details
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<CustomerTO> getFranchiseeDetails(CustomerTO frTo)
			throws CGBusinessException, CGSystemException {
		return businessCommonService.getAllCustomersUnderRegion(frTo);
	}

	/**
	 * getBusinessAssociatesDetails.
	 *
	 * @param baTO the ba to
	 * @return List<BusinessAssociateTO>
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<CustomerTO> getBusinessAssociatesDetails(
			CustomerTO baTO) throws CGBusinessException,
			CGSystemException {

		return businessCommonService.getAllCustomersUnderRegion(baTO);
	}

	/**
	 * getConsignmentStatusFromDelivery : returns status of consg.
	 *
	 * @param consignment the consignment
	 * @return String
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String getConsignmentStatusFromDelivery(String consignment)
			throws CGSystemException {
		return deliveryUniversalDAO
				.getConsignmentStatusFromDelivery(consignment);
	}

	@Override
	public Boolean isConsignmentExistInDRS(final String consignment)
			throws CGSystemException {
		String status=deliveryUniversalDAO
				.isConsignmentExistInDeliveryByParentConsg(consignment);
		return StringUtil.isStringEmpty(status)?false:true;
	}

	/**
	 * isConsignmentDelivered.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isConsignmentDelivered(String consignment)
			throws CGSystemException {
		boolean result = Boolean.FALSE;
		String status = getConsignmentStatusFromDelivery(consignment);
		if (!StringUtil.isStringEmpty(status)) {
			if (status
					.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
				result = Boolean.TRUE;
			}
		}
		return result;
	}

	/**
	 * Checks if is consignment undelivered.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public Boolean isConsignmentUndelivered(String consignment)
			throws CGSystemException {
		boolean result = Boolean.FALSE;
		String status = getConsignmentStatusFromDelivery(consignment);
		if (!StringUtil.isStringEmpty(status)) {
			if (status
					.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_PENDING)) {
				result = Boolean.TRUE;
			}
		}
		return result;
	}

	/**
	 * Checks if is consignment open.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isConsignmentOpen(String consignment)
			throws CGSystemException {
		boolean result = Boolean.FALSE;
		String status = getConsignmentStatusFromDelivery(consignment);
		if (!StringUtil.isStringEmpty(status)) {
			if (status
					.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)) {
				result = Boolean.TRUE;
			}
		}
		return result;
	}

	/**
	 * isConsignmentExistInDelivery.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isConsignmentExistInDelivery(String consignment)
			throws CGSystemException {
		boolean result = Boolean.FALSE;
		String status = getConsignmentStatusFromDelivery(consignment);

		if (!StringUtil.isStringEmpty(status)) {
			result = Boolean.TRUE;
			LOGGER.debug("DeliveryUniversalServiceImpl ::isConsignmentExistInDelivery :: STATUS"
					+ status);
		} else {
			LOGGER.debug("DeliveryUniversalServiceImpl ::isConsignmentExistInDelivery :: Does not Exist in the delivery table");
		}
		return result;
	}

	/**
	 * Checks if is consignment valid.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Boolean isConsignmentValid(String consignment)
			throws CGSystemException, CGBusinessException {
		boolean result = Boolean.FALSE;
		ConsignmentTO to = new ConsignmentTO();
		to.setConsgNo(consignment);
		Integer consgId = consignmentCommonService
				.getConsignmentIdByConsgNo(to);

		if (!StringUtil.isEmptyInteger(consgId)) {
			result = Boolean.TRUE;
			LOGGER.debug("DeliveryUniversalServiceImpl ::isConsignmentValid :: consgId"
					+ consgId);
		} else {
			LOGGER.debug("DeliveryUniversalServiceImpl ::isConsignmentValid :: Does not Exist in the delivery table");
		}
		return result;
	}

	/**
	 * Checks if is comail valid.
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Boolean isComailValid(String consignment)
			throws CGSystemException, CGBusinessException {
		return	deliveryUniversalDAO.isValidComailNumber(consignment);
	}

	/**
	 * Gets the dox consg dtls from booking.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the dox consg dtls from booking
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public DeliveryConsignmentTO getDoxConsgDtlsFromBooking(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		DeliveryConsignmentTO consgTO = null;
		ConsignmentDO consgDO = deliveryUniversalDAO
				.getDoxConsgDtlsFromBooking(inputTo);
		consgTO = convertConsignmentDO2TO(consgDO);
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getPpxConsgDtlsFromBooking(com.ff.to.drs.AbstractDeliveryTO)
	 */
	@Override
	public DeliveryConsignmentTO getPpxConsgDtlsFromBooking(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		DeliveryConsignmentTO consgTO = null;
		ChildConsignmentDO childConsgDO = deliveryUniversalDAO
				.getPpxConsgDtlsFromBooking(inputTo);
		consgTO = prepareDlvCnTOForChildCn(consgTO, childConsgDO);
		return consgTO;
	}

	/**
	 * Prepare dlv cn to for child cn.
	 *
	 * @param consgTO the consg to
	 * @param childConsgDO the child consg do
	 * @return the delivery consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private DeliveryConsignmentTO prepareDlvCnTOForChildCn(
			DeliveryConsignmentTO consgTO, ChildConsignmentDO childConsgDO)
					throws CGBusinessException, CGSystemException {
		if(!StringUtil.isNull(childConsgDO)){
			ConsignmentDO consgDO= childConsgDO.getConsignment();
			consgTO = convertConsignmentDO2TO(consgDO);
			consgTO.setConsgNo(childConsgDO.getChildConsgNumber());
			consgTO.setParentChildCnType(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE);
			consgTO.setParentCnNumber(consgDO.getConsgNo());
			/**  artf3113656 : Delivery runsheet! start*/
			consgTO.setActualWeight(childConsgDO.getChildConsgWeight());
			consgTO.setFinalWeight(childConsgDO.getChildConsgWeight());
		}
		/**  artf3113656 : Delivery runsheet! END*/
		return consgTO;
	}

	/**
	 * Gets the in manifested consgn dtls.
	 * 
	 * @param inputTo
	 *            the input to
	 * @return the in manifested consgn dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@Override
	public DeliveryConsignmentTO getInManifestedConsgnDtls(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug(
				"DeliveryUniversalServiceImpl::getInManifestedConsgnDtls::start------------>:::::::startMilliseconds"+startMilliseconds+"For Consignment["+inputTo.getConsignmentNumber()+"]");
	
		DeliveryConsignmentTO consgTO = null;
		ConsignmentDO consgDO = deliveryUniversalDAO.getManifestedConsgnDtls(
				inputTo.getConsignmentNumber(),
				CommonConstants.MANIFEST_TYPE_IN);
		consgTO = convertConsignmentDO2TO(consgDO);
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug(
				"DeliveryUniversalServiceImpl::getInManifestedConsgnDtls::END------------>:::::::endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff)+"For Consignment["+inputTo.getConsignmentNumber()+"]");
	
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getThirdPartyManifestedConsgnDtlsForDrsParentCn(com.ff.to.drs.AbstractDeliveryTO)
	 */
	@Override
	public DeliveryConsignmentTO getThirdPartyManifestedConsgnDtlsForDrsParentCn(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		DeliveryConsignmentTO consgTO = null;
		ConsignmentDO consgDO = deliveryUniversalDAO.getManifestedConsgnDtlsForThirdPartyDrsParentCn(
				inputTo.getConsignmentNumber(),
				CommonConstants.MANIFEST_TYPE_OUT,inputTo.getDrsNumber());
		consgTO = convertConsignmentDO2TO(consgDO);
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getThirdpartyManifestedConsgDetailsForDRSChildCn(com.ff.to.drs.AbstractDeliveryTO)
	 */
	@Override
	public DeliveryConsignmentTO getThirdpartyManifestedConsgDetailsForDRSChildCn(AbstractDeliveryTO inputTo) throws CGBusinessException, CGSystemException{
		String consgNumber = deliveryUniversalDAO.getManifestedConsgnDtlsForThirdPartyDrsChildCn(inputTo.getConsignmentNumber(),CommonConstants.MANIFEST_TYPE_OUT,inputTo.getDrsNumber());
		DeliveryConsignmentTO consgTO=null;
		if(!StringUtil.isNull(consgNumber)){
			consgTO=getChildConsgDetailsForDRS(inputTo.getConsignmentNumber());
		}
		return consgTO;
	}



	/**
	 * Checks if is child consg in manifested for ppx.
	 *
	 * @param inputTo the input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Boolean isChildConsgInManifestedForPpx(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		String consg = null;
		Boolean result= false;
		consg = deliveryUniversalDAO.getMnfstChildConsgnDtlsForDrsForPPX(
				inputTo.getConsignmentNumber(),
				CommonConstants.MANIFEST_TYPE_IN);
		if(!StringUtil.isStringEmpty(consg)){
			result=true;
		}
		return result;
	}

	/**
	 * Gets the in manifested comail dtls.
	 *
	 * @param inputTo the input to
	 * @return the in manifested comail dtls
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryConsignmentTO getInManifestedComailDtls(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		DeliveryConsignmentTO consgTO = null;
		ComailDO consgDO = deliveryUniversalDAO.getManifestedComailDtls(inputTo.getConsignmentNumber(),
				CommonConstants.MANIFEST_TYPE_IN);
		consgTO = convertComailDO2TO(consgDO);
		return consgTO;
	}

	/**
	 * Convert consignment d o2 to.
	 *
	 * @param consgDO the consg do
	 * @return the delivery consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private DeliveryConsignmentTO convertConsignmentDO2TO(ConsignmentDO consgDO)
			throws CGBusinessException, CGSystemException {
		DeliveryConsignmentTO consgTO=null;
		if (consgDO != null) {
			consgTO = new DeliveryConsignmentTO();

			try {
				PropertyUtils.copyProperties(consgTO, consgDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("DeliveryUniversalServiceImpl ::getDoxConsgDtlsFromBooking :: consgId");
				throw new CGBusinessException(e);
			}
			consgTO.setNoOfPcs(1);//FIXME
			if (!StringUtil.isNull(consgDO.getConsgType())) {
				consgTO.setConsgTypeId(consgDO.getConsgType()
						.getConsignmentId());
				consgTO.setConsignmentTypeCode(consgDO.getConsgType()
						.getConsignmentCode());
				consgTO.setConsignmentTypeName(consgDO.getConsgType()
						.getConsignmentName());
			}
			if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
				CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
				CGObjectConverter.createToFromDomain(
						consgDO.getCnPaperWorkId(), cnPaperworkTO);
				cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
				consgTO.setCnPaperWorks(cnPaperworkTO);
			}
			if (!StringUtil.isNull(consgDO.getCnContentId())) {
				CNContentTO cnContentTO = new CNContentTO();
				CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
						cnContentTO);
				cnContentTO.setOtherContent(consgDO.getOtherCNContent());
				cnContentTO.setCnContentName(consgDO.getCnContentId().getCnContentName()+(!StringUtil.isStringEmpty(consgDO.getOtherCNContent())?FrameworkConstants.CHARACTER_HYPHEN+consgDO.getOtherCNContent():FrameworkConstants.EMPTY_STRING));
				consgTO.setCnContents(cnContentTO);
			}
			if (!StringUtil.isNull(consgDO.getInsuredBy())) {
				InsuredByTO insuredBy = new InsuredByTO();
				CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
						insuredBy);
				consgTO.setInsuredByTO(insuredBy);
			}
			if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
				VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
				volWeightDtls.setVolWeight(consgDO.getVolWeight());
				volWeightDtls.setHeight(consgDO.getHeight());
				volWeightDtls.setLength(consgDO.getLength());
				volWeightDtls.setBreadth(consgDO.getBreath());
				consgTO.setVolWightDtls(volWeightDtls);
			}
			if (!StringUtil.isEmptyInteger(consgDO.getOrgOffId())) {
				CityTO city = getCitiesByOffices(consgDO.getOrgOffId());
				if (city != null) {
					consgTO.setCityId(city.getCityId());
					consgTO.setCityCode(city.getCityCode());
					consgTO.setCityName(city.getCityName());
				}

			}else{
				ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_ORIGIN_OFFICE_NOT_EXIST,new String[]{consgDO.getConsgNo()});
			}
			if (!StringUtil.isNull(consgDO.getConsignee())) {
				ConsigneeConsignorDO consignee=consgDO.getConsignee();
				ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignee,
						consigneeTO);
				consgTO.setConsigneeTO(consigneeTO);
			}
			if (!StringUtil.isNull(consgDO.getConsignor())) {
				ConsigneeConsignorDO consignor=consgDO.getConsignor();
				ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignor, consignorTO);
				consgTO.setConsignorTO(consignorTO);
			}

			prepareConsgPrice(consgDO, consgTO);

		}
		return consgTO;
	}

	/**
	 * Prepare consg price.
	 *
	 * @param consgDO the consg do
	 * @param consgTO the consg to
	 * @throws CGBusinessException 
	 */
	public void prepareConsgPrice(ConsignmentDO consgDO,
			DeliveryConsignmentTO consgTO) throws CGBusinessException {
		if(!StringUtil.isEmptyDouble(consgDO.getBaAmt())){
			consgTO.setBaAmount(consgDO.getBaAmt());
		}else{
			
			consgTO.setCodAmount(consgDO.getCodAmt());
			consgTO.setLcAmount(consgDO.getLcAmount());
			consgTO.setToPayAmount(consgDO.getTopayAmt());
			String product=StockSeriesGenerator.getProductDtls(consgDO.getConsgNo());
			if(!StringUtil.isStringEmpty(product)){
				product=product.trim().toUpperCase();
				switch(product){
				case CommonConstants.PRODUCT_SERIES_CASH_COD:
					if(StringUtil.isEmptyDouble(consgDO.getCodAmt())){
						ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_COD_LC_TO_PAY_MANDATORY,new String[]{"COD",product,consgDO.getConsgNo()});
					}
					break;
				case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
					if(StringUtil.isEmptyDouble(consgDO.getLcAmount())){
						ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_COD_LC_TO_PAY_MANDATORY,new String[]{"LC",product,consgDO.getConsgNo()});
					}
					break;
				case CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD:
					if(StringUtil.isEmptyDouble(consgDO.getTopayAmt())){
						ExceptionUtil.prepareBusinessException(UniversalErrorConstants.CONSG_COD_LC_TO_PAY_MANDATORY,new String[]{"To-Pay",product,consgDO.getConsgNo()});
					}
					break;

				}

			}
		}
		consgTO.setPrice(consgDO.getPrice());
		//consgTO.setAdditionalCharges(additionalCharges) FIXME
		StringBuffer pricingLogs=getPricingFromConsg(consgDO);
		LOGGER.info("DeliveryUniversalServiceImpl ::prepareConsgPrice:: pricingLogs"+pricingLogs);

	}

	/**
	 * Convert comail d o2 to.
	 *
	 * @param comailDO the comail do
	 * @return the delivery consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private DeliveryConsignmentTO convertComailDO2TO(ComailDO comailDO)
			throws CGBusinessException, CGSystemException {
		DeliveryConsignmentTO consgTO=null;
		if (comailDO != null) {
			consgTO = new DeliveryConsignmentTO();

			try {
				PropertyUtils.copyProperties(consgTO, comailDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("DeliveryUniversalServiceImpl ::getDoxConsgDtlsFromBooking :: consgId");
				throw new CGBusinessException(e);
			}
			consgTO.setConsgNo(comailDO.getCoMailNo());
			consgTO.setNoOfPcs(1);//FIXME
			if (!StringUtil.isEmptyInteger(comailDO.getOriginOffice())) {
				CityTO city = getCitiesByOffices(comailDO.getOriginOffice());
				if (city != null) {
					consgTO.setCityId(city.getCityId());
					consgTO.setCityCode(city.getCityCode());
					consgTO.setCityName(city.getCityName());
				}

			}


		}
		return consgTO;
	}

	/**
	 * Gets the latest date for consignment it's for only active consignments.
	 *
	 * @param consignment the consignment
	 * @return the latest date for consignment
	 * @throws CGBusinessException ,CGSystemException the cG system exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getLatestDateForConsignment(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalDAO.getLatestDateForConsignment(consignment);
	}

	/**
	 * Gets the delivered consignments details.
	 *
	 * @param consignment the consignment
	 * @param originOffId the origin off id
	 * @return DeliveryDetailsTO
	 * @throws CGBusinessException ,CGSystemException the cG system exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryDetailsTO getDeliverdConsgDtls(String consignment,
			Integer originOffId) throws CGBusinessException, CGSystemException {
		DeliveryDetailsTO dlvDtlsTO = null;
		DeliveryDetailsDO dlvDtlsDO = deliveryUniversalDAO
				.getDeliverdConsgDtls(consignment, originOffId);
		if (!StringUtil.isNull(dlvDtlsDO)) {
			dlvDtlsTO = new DeliveryDetailsTO();
			dlvDtlsTO.setDeliveryDate(dlvDtlsDO.getDeliveryDate());
			dlvDtlsTO.setConsignmentNumber(dlvDtlsDO.getConsignmentNumber());
			ConsignmentTO consingment = consignmentCommonService
					.getConsingmentDtls(dlvDtlsDO.getConsignmentNumber());
			dlvDtlsTO.setConsignmentTO(consingment);
			dlvDtlsTO.setReceiverName(dlvDtlsDO.getReceiverName());
			dlvDtlsTO.setCompanySealSign(dlvDtlsDO.getCompanySealSign());
			dlvDtlsTO.setDeliveryStatus(dlvDtlsDO.getDeliveryStatus());
			DeliveryTO deliveryTO = new DeliveryTO();
			deliveryTO.setFsOutTime(dlvDtlsDO.getDeliveryDO().getFsOutTime());
			dlvDtlsTO.setDeliveryTO(deliveryTO);
		}
		return dlvDtlsTO;
	}

	/**
	 * Gets the attempt count for consignment.
	 *
	 * @param consignment the consignment
	 * @return the attempt count for consignment
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Integer getAttemptCountForConsignment(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalDAO.getAttemptCountForConsignment(consignment);
	}

	/**
	 * getNonDeliveryReasons.
	 *
	 * @return the non delivery reasons
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<ReasonTO> getNonDeliveryReasons()
			throws CGBusinessException, CGSystemException {
		return getReasonsByReasonType(UdaanCommonConstants.REASON_TYPE_FOR_NON_DELIVERY);
	}

	/**
	 * Gets the reasons by reason type.
	 *
	 * @param reasonType the reason type
	 * @return the reasons by reason type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<ReasonTO> getReasonsByReasonType(String reasonType)
			throws CGBusinessException, CGSystemException {
		ReasonTO inTo= new  ReasonTO();
		inTo.setReasonType(reasonType);
		return serviceOfferingCommonService.getReasonsByReasonType(inTo);
	}

	/**
	 * Gets the all drs relationships.
	 *
	 * @param relationTo the relation to
	 * @return the all drs relationships
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<RelationTO> getAllRelationsForDelivery(RelationTO relationTo)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getAllRelations(relationTo);
	}

	/**
	 * Gets the all id proofs for delivery.
	 *
	 * @param idProofTo the id proof to
	 * @return the all id proofs for delivery
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<IdentityProofTypeTO> getAllIdProofsForDelivery(IdentityProofTypeTO idProofTo)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getAllIdentityProofs(idProofTo);
	}


	/**
	 * Gets the vendors list.
	 *
	 * @param loadMovementVendorTO the load movement vendor to
	 * @return the vendors list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Deprecated
	@Override
	public List<LoadMovementVendorTO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO) throws CGBusinessException,
			CGSystemException {
		return businessCommonService.getVendorsList(loadMovementVendorTO);
	}





	/**
	 * Gets the parent consg details for drs.
	 *
	 * @param consignment the consignment
	 * @return the parent consg details for drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public ConsignmentTO getParentConsgDetailsForDRS(String consignment) throws CGBusinessException, CGSystemException{
		ConsignmentDO consgDO = deliveryUniversalDAO.getParentConsgDtlsForDrs(consignment);
		ConsignmentTO consgTO=null;
		if(!StringUtil.isNull(consgDO)){
			consgTO =convertConsignmentDO2ConsgTO(consgDO);
		}
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getConsignmentTypeByConsgNumber(java.lang.String)
	 */
	@Override
	public String getConsignmentTypeByConsgNumber(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getConsignmentTypeByConsgNumber(consignment);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getConsignmentStatusFromConsg(java.lang.String)
	 */
	@Override
	public String getConsignmentStatusFromConsg(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getConsignmentStatusFromConsg(consignment);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getParentConsgDetailsFromConsignment(java.lang.String)
	 */
	@Override
	public DeliveryConsignmentTO getParentConsgDetailsFromConsignment(String consignment) throws CGBusinessException, CGSystemException{
		ConsignmentDO consgDO = deliveryUniversalDAO.getParentConsgDtlsForDrs(consignment);
		DeliveryConsignmentTO consgTO=null;
		if(!StringUtil.isNull(consgDO)){
			consgTO =convertConsignmentDO2TO(consgDO);
		}
		return consgTO;
	}

	;
	/**
	 * Gets the child consg details for drs.
	 *
	 * @param consignment the consignment
	 * @return the child consg details for drs
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryConsignmentTO getChildConsgDetailsForDRS(String consignment) throws CGBusinessException, CGSystemException{
		ChildConsignmentDO childConsgDO = deliveryUniversalDAO.getChildConsgDtlsForDrs(consignment);
		DeliveryConsignmentTO consgTO=null;
		consgTO = prepareDlvCnTOForChildCn(consgTO, childConsgDO);
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getOctroiAmountForDrs(java.lang.String)
	 */
	@Override
	public Double getOctroiAmountForDrs(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getOctroiAmountForDrs(consignment);
	}

	/**
	 * Convert consignment d o2 consg to.
	 *
	 * @param consgDO the consg do
	 * @return the consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private ConsignmentTO convertConsignmentDO2ConsgTO(ConsignmentDO consgDO)
			throws CGBusinessException, CGSystemException {
		ConsignmentTO consgTO=null;
		if (consgDO != null) {
			consgTO = new ConsignmentTO();

			try {
				PropertyUtils.copyProperties(consgTO, consgDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("DeliveryUniversalServiceImpl ::getDoxConsgDtlsFromBooking :: consgId");
				throw new CGBusinessException(e);
			}
			if (!StringUtil.isNull(consgDO.getConsgType())) {
				ConsignmentTypeTO typeTO= new ConsignmentTypeTO();
				CGObjectConverter.createToFromDomain(
						consgDO.getConsgType(), typeTO);
				consgTO.setTypeTO(typeTO);
			}
			if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
				CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
				CGObjectConverter.createToFromDomain(
						consgDO.getCnPaperWorkId(), cnPaperworkTO);
				cnPaperworkTO.setPaperWorkRefNum(consgDO.getPaperWorkRefNo());
				consgTO.setCnPaperWorks(cnPaperworkTO);
			}
			if (!StringUtil.isNull(consgDO.getCnContentId())) {
				CNContentTO cnContentTO = new CNContentTO();
				CGObjectConverter.createToFromDomain(consgDO.getCnContentId(),
						cnContentTO);
				cnContentTO.setOtherContent(consgDO.getOtherCNContent());
				consgTO.setCnContents(cnContentTO);
			}
			if (!StringUtil.isNull(consgDO.getInsuredBy())) {
				InsuredByTO insuredBy = new InsuredByTO();
				CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
						insuredBy);
				consgTO.setInsuredByTO(insuredBy);
			}
			if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
				VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
				volWeightDtls.setVolWeight(consgDO.getVolWeight());
				volWeightDtls.setHeight(consgDO.getHeight());
				volWeightDtls.setLength(consgDO.getLength());
				volWeightDtls.setBreadth(consgDO.getBreath());
				consgTO.setVolWightDtls(volWeightDtls);
			}

			if (!StringUtil.isNull(consgDO.getConsignee())) {
				ConsigneeConsignorDO consignee=consgDO.getConsignee();
				ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignee,
						consigneeTO);
				consgTO.setConsigneeTO(consigneeTO);
			}
			if (!StringUtil.isNull(consgDO.getConsignor())) {
				ConsigneeConsignorDO consignor=consgDO.getConsignor();
				ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
				CGObjectConverter.createToFromDomain(consignor, consignorTO);
				consgTO.setConsignorTO(consignorTO);
			}
			if(!CGCollectionUtils.isEmpty(consgDO.getChildCNs())){
				Set<ChildConsignmentTO> childTOSet=(Set<ChildConsignmentTO>)CGObjectConverter.createTOSetFromDomainSet(consgDO.getChildCNs(),ChildConsignmentTO.class );
				consgTO.setChildTOSet(childTOSet);
			}

		}
		return consgTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getModeOfPaymentDetails()
	 */
	@Override
	public List<PaymentModeTO> getModeOfPaymentDetails()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getPaymentDetails();
	}


	/**
	 * Checks if is consg having child cns.
	 *
	 * @param consignment the consignment0
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isConsgHavingChildCns(String consignment) throws CGBusinessException, CGSystemException{
		Boolean result=false;
		String childConsgDO = deliveryUniversalDAO.isConsgHavingChildCns(consignment);
		if(!StringUtil.isStringEmpty(childConsgDO)){
			result=true;
		}
		return result;
	}

	/**
	 * Checks if is child cn. just verifies is it available in child cn table
	 *
	 * @param consignment the consignment
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean isChildCn(String consignment) throws CGBusinessException, CGSystemException{
		Boolean result=false;
		String childConsg = deliveryUniversalDAO.isChildCns(consignment);
		if(!StringUtil.isStringEmpty(childConsg)){
			result=true;
		}
		return result;
	}


	/**
	 * Gets the manifested date by consg number for parent consg.
	 * *	In Mainfest
	 * For Dox  In OGM
	 * For PPX  In BPL
	 *
	 * @return the manifested date by consg number for parent consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByConsgNumberForParentConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_FOR_MANFST_DATE_FOR_PARENT_CN);
		mnfstDate= deliveryUniversalDAO.getManifestedDateByConsgNumber(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedDateByManifestAndConsgNumberForParentConsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Date getManifestedDateByManifestAndConsgNumberForParentConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_FOR_MANFST_DATE_FOR_PARENT_CN_TP_MANIFEST);
		mnfstDate= deliveryUniversalDAO.getManifestedDateByConsgNumberAndManifestNumber(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedDateByManifestAndConsgNumberForChildConsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Date getManifestedDateByManifestAndConsgNumberForChildConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_FOR_MANFST_DATE_FOR_CHILD_CN_TP_MANIFEST);
		mnfstDate= deliveryUniversalDAO.getManifestedDateByConsgNumberAndManifestNumber(dlvInputTO);
		return mnfstDate;
	}

	/**
	 * Gets the manifested date by comail number.
	 *
	 * @param consignment the consignment
	 * @return the manifested date by comail number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByComailNumber(String consignment) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		String processCode=CommonConstants.PROCESS_IN_MANIFEST_DOX;
		String manifestStatus=CommonConstants.MANIFEST_TYPE_IN;
		mnfstDate= deliveryUniversalDAO.getManifestedDateByComailNumber(consignment, manifestStatus, processCode);
		return mnfstDate;
	}

	/**
	 * Gets the manifested date by consg number for child consg.
	 * *	In Mainfest
	 * For Dox  In OGM
	 * For PPX  In BPL
	 *
	 * @return the manifested date by consg number for child consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByConsgNumberForChildConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_FOR_MANFST_DATE_FOR_CHILD_CN);
		mnfstDate= deliveryUniversalDAO.getManifestedDateByConsgNumber(dlvInputTO);
		return mnfstDate;
	}

	/**
	 * Gets the manifested date by consg number for dox.
	 **	In Mainfest
	 *			For Dox  In OGM  
	 *			For PPX  In BPL
	 * @return the manifested date by consg number for dox
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByConsgNumberForDox(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_IN);
		mnfstDate= getManifestedDateByConsgNumberForParentConsg(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedDateByConsgNumberForThirdPartyDox(java.lang.String, java.lang.String)
	 */
	@Override
	public Date getManifestedDateByConsgNumberForThirdPartyDox(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		mnfstDate= getManifestedDateByManifestAndConsgNumberForParentConsg(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedDateByConsgNumberForThirdPartyParentPPx(java.lang.String, java.lang.String)
	 */
	@Override
	public Date getManifestedDateByConsgNumberForThirdPartyParentPPx(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		mnfstDate= getManifestedDateByManifestAndConsgNumberForParentConsg(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedDateByConsgNumberForThirdPartyChildPPx(java.lang.String, java.lang.String)
	 */
	@Override
	public Date getManifestedDateByConsgNumberForThirdPartyChildPPx(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		mnfstDate= getManifestedDateByManifestAndConsgNumberForChildConsg(dlvInputTO);
		return mnfstDate;
	}

	/**
	 * Gets the manifested date by consg number parent for ppx.
	 **	In Mainfest
	 *			For Dox  In OGM  
	 *			For PPX  In BPL
	 * @return the manifested date by consg number parent for ppx
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByConsgNumberParentForPpx(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_IN);
		mnfstDate= getManifestedDateByConsgNumberForParentConsg(dlvInputTO);
		return mnfstDate;
	}

	/**
	 * Gets the manifested date by consg number child for ppx.
	 **	In Mainfest
	 *			For Dox  In OGM  
	 *			For PPX  In BPL
	 * @return the manifested date by consg number child for ppx
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getManifestedDateByConsgNumberChildForPpx(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		Date mnfstDate=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_IN);
		mnfstDate= getManifestedDateByConsgNumberForChildConsg(dlvInputTO);
		return mnfstDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getDrsDtlsByConsgNo(java.lang.String)
	 */
	@Override
	public DeliveryDetailsTO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGBusinessException, CGSystemException {
		DeliveryDetailsTO deliveryDetailsTO = null;
		DeliveryDetailsDO deliveryDetailsDO = deliveryUniversalDAO
				.getDrsDtlsByConsgNo(consignmentNumber);

		if(deliveryDetailsDO!=null){
			deliveryDetailsTO = new DeliveryDetailsTO();
			CGObjectConverter.createToFromDomain(deliveryDetailsDO, deliveryDetailsTO);
			ConsignmentTO consignmentTO = convertConsignmentDO2ConsgTO(deliveryDetailsDO.getConsignmentDO());
			deliveryDetailsTO.setConsignmentTO(consignmentTO);
			if(deliveryDetailsDO.getDeliveryDO()!=null){
				DeliveryTO deliveryTO = new DeliveryTO();
				CGObjectConverter.createToFromDomain(deliveryDetailsDO.getDeliveryDO(), deliveryTO);
				deliveryDetailsTO.setDeliveryTO(deliveryTO);
			}
		}		

		return deliveryDetailsTO;
	}

	/**
	 * validateInManifestedAndLoggedInOfficeForDOX.
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String validateInManifestedAndLoggedInOfficeForDOX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		mnfstNumber= getLoggedInManifestedNumberForParentConsg(dlvInputTO);
		return mnfstNumber;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#validateOutManifestedAndLoggedInOfficeForThirdPartyDOX(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public String validateOutManifestedAndLoggedInOfficeForThirdPartyDOX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX);
		mnfstNumber= getOutManifestedNumberByLoggedOfficeForThirdPartyManifestParentConsg(dlvInputTO);
		return mnfstNumber;
	}

	/**
	 * Validate in manifested and logged in office for child cnppx.
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String validateInManifestedAndLoggedInOfficeForChildCNPPX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstnumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		mnfstnumber= getLoggedInManifestedNumberForChildConsg(dlvInputTO);
		return mnfstnumber;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#validateOutManifestedAndLoggedInOfficeForChildCNThirdPartyPPX(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public String validateOutManifestedAndLoggedInOfficeForChildCNThirdPartyPPX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstnumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		mnfstnumber= getLoggedOutManifestedNumberForChildConsgForThirdPartyManifest(dlvInputTO);
		return mnfstnumber;
	}

	/**
	 * Validate in manifested and logged in office for parent cnppx.
	 *
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String validateInManifestedAndLoggedInOfficeForParentCNPPX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		mnfstNumber= getLoggedInManifestedNumberForParentConsg(dlvInputTO);
		return mnfstNumber;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#validateOutManifestedAndLoggedInOfficeForParentCNForThirdPartyPPX(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public String validateOutManifestedAndLoggedInOfficeForParentCNForThirdPartyPPX(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL);
		mnfstNumber= getOutManifestedNumberByLoggedOfficeForThirdPartyManifestParentConsg(dlvInputTO);
		return mnfstNumber;
	}

	/**
	 * Gets the logged in manifested number for child consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the logged in manifested number for child consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private String getLoggedInManifestedNumberForChildConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_GET_LOGGED_IN_MANIFESTED_NUMBER_FOR_CHILD_CN);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_IN);
		mnfstNumber= deliveryUniversalDAO.getInManifestedConsignmentNumberByOffice(dlvInputTO);
		return mnfstNumber;
	}

	/**
	 * Gets the logged out manifested number for child consg for third party manifest.
	 * @param dlvInputTO TODO
	 *
	 * @return the logged out manifested number for child consg for third party manifest
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private String getLoggedOutManifestedNumberForChildConsgForThirdPartyManifest(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_GET_OUT_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_CHILD_CN_TP_MANIFEST);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		mnfstNumber= deliveryUniversalDAO.getOutManifestedConsignmentNumberByOfficeForThirdPartyManifest(dlvInputTO);
		return mnfstNumber;
	}

	/**
	 * Gets the logged in manifested number for parent consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the logged in manifested number for parent consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */

	private String getLoggedInManifestedNumberForParentConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_GET_LOGGED_IN_MANIFESTED_NUMBER_FOR_PARENT_CONSG);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_IN);
		mnfstNumber= deliveryUniversalDAO.getInManifestedConsignmentNumberByOffice(dlvInputTO);
		return mnfstNumber;
	}

	/**
	 * Gets the out manifested number by logged office for third party manifest parent consg.
	 * @param dlvInputTO TODO
	 *
	 * @return the out manifested number by logged office for third party manifest parent consg
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private String getOutManifestedNumberByLoggedOfficeForThirdPartyManifestParentConsg(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		String mnfstNumber=null;
		dlvInputTO.setQueryName(UniversalDeliveryContants.QRY_GET_MANIFESTED_NUMBER_BY_LOGGED_IN_OFFICE_FOR_PARENT_CONSG_TP_MANIFEST);
		dlvInputTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		mnfstNumber= deliveryUniversalDAO.getOutManifestedConsignmentNumberByOfficeForThirdPartyManifest(dlvInputTO);
		return mnfstNumber;
	}



	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getConsignmentStatusFromCNForParentConsg(java.lang.String)
	 */
	@Override
	public String getConsignmentStatusFromCNForParentConsg(String consignment) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getConsignmentStatusFromConsgForParentCn(consignment);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getConsignmentStatusFromCNForChildConsg(java.lang.String)
	 */
	@Override
	public String getConsignmentStatusFromCNForChildConsg(String consgNumber) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getConsignmentStatusFromConsgForChildCn(consgNumber);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#validateStopDelivery(java.lang.String)
	 * 
	 * Usage : this method is used to get consignment status from the consignment table.
	 * 
	 */
	@Override
	public String validateStopDelivery(String consgNumber)throws CGBusinessException, CGSystemException{
		String status=null;
		status=getConsignmentStatusFromCNForParentConsg(consgNumber);
		if(StringUtil.isStringEmpty(status)){
			status=getConsignmentStatusFromCNForChildConsg(consgNumber);
		}
		return status;
	}

	/**
	 * getConsignmentTypes.
	 *
	 * @param consgTypeTO the consg type to
	 * @return the consignment types
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(ConsignmentTypeTO consgTypeTO)throws CGBusinessException, CGSystemException{
		return serviceOfferingCommonService.getConsignmentTypes(consgTypeTO);
	}

	/**
	 * getPaymentModeDtls : get the payment details based on the Process.
	 *
	 * @param processCode the process code
	 * @return the payment mode dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException{
		return serviceOfferingCommonService.getPaymentModeDtls(processCode);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getPaymentModeTypeForCollection()
	 */
	@Override
	public  Map<String,Integer> getPaymentModeTypeForCollection()
			throws CGBusinessException, CGSystemException {
		Map<String,Integer> paymentTypeMap=null;
		List<PaymentModeTO> paymentTypeList=getPaymentModeDtls(CommonConstants.PROCESS_MEC);
		if(!CGCollectionUtils.isEmpty(paymentTypeList)){
			paymentTypeMap = new HashMap<>(paymentTypeList.size());
			for(PaymentModeTO paymentTypeTO:paymentTypeList){
				paymentTypeMap.put(paymentTypeTO.getPaymentCode(),paymentTypeTO.getPaymentId());
			}
		}
		return paymentTypeMap;
	}

	/**
	 * getDeliveryDetailsForCollection.
	 *
	 * @param qryName the qry name
	 * @return the delivery details for collection
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public List<DrsCollectionIntegrationWrapperDO> getDeliveryDetailsForCollection(String qryName)throws CGSystemException,CGBusinessException{
		return deliveryUniversalDAO.getDeliveryDetailsForCollection(qryName);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#processDeliveryDetailsForCollection(com.ff.domain.delivery.DeliveryDetailsDO, java.util.Map)
	 */
	@Override
	@Deprecated
	public Boolean processDeliveryDetailsForCollection(DeliveryDetailsDO drsDtls,Map<String,Integer> paymentTypeMap)throws CGSystemException,CGBusinessException{
		boolean processed=false;
		/*drsDtls.setCollectionStatus(UniversalDeliveryContants.COLLECTION_STATUS_FLAG_YES);//Marking Delivery record as 'Y'
		Boolean isCollectionExist=false;
		isCollectionExist=deliveryUniversalDAO.isCollectionEntryAlreadyExistForDRS(drsDtls.getConsignmentDO().getConsgId());
		if(!isCollectionExist){
			CollectionDO collectionDo=null;
			collectionDo= prepareCollectionHeaderDO(drsDtls, paymentTypeMap);

			processed= deliveryUniversalDAO.saveCollectionDtlsByDrs(collectionDo);
			LOGGER.debug("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection :: Collection save  status:"
					+ processed);
			processed= deliveryUniversalDAO.updateDrsForCollectionIntegration(drsDtls);
			LOGGER.debug("DeliveryUniversalServiceImpl ::updateDrsForCollectionIntegration :: DRS update for Collection   status:"
					+ processed);
		}
		processed=deliveryUniversalDAO.updateCollectionStatusForAllDRSChildConsignments(drsDtls);
		LOGGER.debug("DeliveryUniversalServiceImpl ::updateAllChildConsignmentsForIntegration :: DRS update for child CN   status:"
				+ processed);*/

		return processed;

	}

	/**
	 * Prepare collection child dtls.
	 *
	 * @param dlvDtlsDO the dlv dtls do
	 * @param collectionDO the collection do
	 */
	@Deprecated
	private void prepareCollectionChildDtls(
			DrsCollectionIntegrationWrapperDO dlvDtlsDO, CollectionDO collectionDO) {
		Set<CollectionDtlsDO> collectionDOSet= new HashSet<>(3);

			if(!StringUtil.isEmptyDouble(dlvDtlsDO.getTopayAmt())){
				CollectionDtlsDO collectionDtls = prepareCollectionDetailsDO(dlvDtlsDO,
						collectionDO);
				collectionDtls.setCollectionType(MECUniversalConstants.COLLECTION_TYPE_TOPAY);
				collectionDOSet.add(collectionDtls);
			}
			if(!StringUtil.isEmptyDouble(dlvDtlsDO.getLcAmount())){
				CollectionDtlsDO collectionDtls = prepareCollectionDetailsDO(dlvDtlsDO,
						collectionDO);
				collectionDtls.setCollectionType(MECUniversalConstants.COLLECTION_TYPE_LC);
				collectionDOSet.add(collectionDtls);
			}
			if(!StringUtil.isEmptyDouble(dlvDtlsDO.getCodAmt())){
				CollectionDtlsDO collectionDtls = prepareCollectionDetailsDO(dlvDtlsDO,
						collectionDO);
				collectionDtls.setCollectionType(MECUniversalConstants.COLLECTION_TYPE_COD);
				collectionDOSet.add(collectionDtls);
			}
			if(CGCollectionUtils.isEmpty(collectionDOSet)){//If COD,LC,TO-Pay Amount doesnot Exist
				CollectionDtlsDO collectionDtls = prepareCollectionDetailsDO(dlvDtlsDO,
						collectionDO);
				collectionDOSet.add(collectionDtls);
			}

		collectionDO.setCollectionDtls(collectionDOSet);

	}

	/**
	 * Prepare collection details do.
	 *
	 * @param dlvDtlsDO the dlv dtls do
	 * @param collectionDO the collection do
	 * @return the collection dtls do
	 */
	private CollectionDtlsDO prepareCollectionDetailsDO(
			DrsCollectionIntegrationWrapperDO dlvDtlsDO, CollectionDO collectionDO) {
		CollectionDtlsDO collectionDtls= new CollectionDtlsDO();
		collectionDtls.setCollectionDO(collectionDO);
		collectionDtls.setBillAmount(collectionDO.getTotalAmount());
		collectionDtls.setCollectionFor(MECUniversalConstants.COLLECTION_FOR_FFCL);
		collectionDtls.setRecvAmount(collectionDO.getTotalAmount());
		collectionDtls.setBillAmount(collectionDO.getTotalAmount());//FIXME
		collectionDtls.setTotalBillAmount(collectionDO.getTotalAmount());
		ConsignmentDO consg=new ConsignmentDO();
		consg.setConsgId(dlvDtlsDO.getConsgId());
		collectionDtls.setConsgDO(consg);
		collectionDtls.setConsgDeliveryDate(dlvDtlsDO.getDeliveryDate());
		collectionDtls.setCreatedDate(DateUtil.getCurrentDate());
		collectionDtls.setUpdatedDate(DateUtil.getCurrentDate());
		return collectionDtls;
	}


	

	

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#processDeliveryDetailsForCollectionIntegration(com.ff.domain.delivery.DeliveryDetailsDO, java.util.Map)
	 */
	@Override
	public Boolean processDeliveryDetailsForCollectionIntegration(DrsCollectionIntegrationWrapperDO drsDtls,Map<String,Integer> paymentTypeMap)throws CGSystemException,CGBusinessException{
		boolean processed=false;
		long startMilliseconds=System.currentTimeMillis();
		drsDtls.setCollectionStatus(UniversalDeliveryContants.COLLECTION_STATUS_FLAG_YES);//Marking Delivery record as 'Y'
		Boolean isCollectionExist=false;
		isCollectionExist=deliveryUniversalDAO.isCollectionEntryAlreadyExistForDRS(drsDtls.getConsgId());
		String product=StockSeriesGenerator.getProductDtls(drsDtls.getDrsConsgNumber());
		if(!isCollectionExist){
			drsDtls.setCreatedOfficeId(deliveryUniversalDAO.getOfficeIdByOfficeCode(drsDtls.getCreatedOfficeCode()));
			if(StringUtil.isEmptyInteger(drsDtls.getCreatedOfficeId())){
				Integer officeId=deliveryUniversalDAO.getDeliveredOfficeByDeliveryDtl(drsDtls.getDeliveryDtlsId());
				if(!StringUtil.isEmptyInteger(officeId)){
					drsDtls.setCreatedOfficeId(officeId);
					drsDtls.setCreatedOfficeCode(deliveryUniversalDAO.getOfficeCodeByOfficeId(officeId));
				}else{
					LOGGER.warn("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection :: office detail does not exist for the delivery _dtl_id "+drsDtls.getDeliveryDtlsId());
					return false;
				}
			}
			List<CollectionDO> collectionDoList=null;
			int numberOfTxNumbers=0;
			//Double codAmount=StringUtil.isEmptyDouble(drsDtls.getCodAmt())?drsDtls.getDrsCodAmount():drsDtls.getCodAmt();
			Double codAmount=drsDtls.getDrsCodAmount();
			//Double lcAmount=StringUtil.isEmptyDouble(drsDtls.getLcAmount())?drsDtls.getDrsLcAmount() :drsDtls.getLcAmount();
			Double lcAmount=drsDtls.getDrsLcAmount() ;
			Double toPayAmount=drsDtls.getDrsToPayAmount();
			//Double toPayAmount=StringUtil.isEmptyDouble(drsDtls.getTopayAmt())?drsDtls.getDrsToPayAmount():drsDtls.getTopayAmt();
			Double baAmount=drsDtls.getDrsBaAmount();
			//Double baAmount=StringUtil.isEmptyDouble(drsDtls.getBaAmt())? drsDtls.getDrsBaAmount():drsDtls.getBaAmt();
			Double additinalChrges=drsDtls.getDrsAdditionalCharges();
			Double otherAmount=drsDtls.getDrsOtherAmount();

			if(StringUtil.isEmptyDouble(baAmount)){
				/**
				 * If BA Amount is not exist then consider  COD/LC/TO-Pay amount for collection
				 * 
				 * 
				 * 1.	L- series
				 * a.	Need to display only  COD Amount and same to be stamped in Collection
				 *2.	D- series
				 *a.	Need to Display only  LC Amount and same to be sampled  in Collection
				 *3.	T- series
				 *a.	If it has BA amount, then only BA amount to be shown in DRS and in collection 
				 *b.	If it has COD amount, then need to display only COD amount in DRS. 
				 *c.	If it has COD amount and To-pay amount, same as (3.b)  
				 *d.	If it has  only To-pay amount then  to-pay amount will be shown in DRS and in collection module  
				 *4.	If L,T,D series have Other Charges/Additional charges  then this will be populated in DRS/Collection in addition to the above BR.
				 *(in otherwords, if any of the series has other charges/Addition charges/other Amount then this will be shown in DRS/Collection in any case)
				 * */
				if(!StringUtil.isStringEmpty(product)){

					if(product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_CASH_COD)){
						/** L Series*/
						lcAmount=null;
						toPayAmount=null;
						if(!StringUtil.isEmptyDouble(codAmount)){
							++numberOfTxNumbers;
						}
					}else  if(product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT)){
						/** D Series*/
						codAmount=null;
						toPayAmount=null;
						if(!StringUtil.isEmptyDouble(lcAmount)){
							++numberOfTxNumbers;
						} 
					}else  if(product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD)){
						
						if(StringUtil.isEmptyDouble(codAmount) && !StringUtil.isEmptyDouble(toPayAmount)){
							++numberOfTxNumbers;
						} else if(!StringUtil.isEmptyDouble(codAmount)){
							//for a consignment with T series and has Cod amount then it will not be considered for Collection
							drsDtls.setCollectionStatus("R");
							processed= deliveryUniversalDAO.updateDrsForCollectionIntegration(drsDtls);
							LOGGER.warn("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection :: consignment with T series and has Cod amount then it's will not be considred for Collection hence updating status as R in collection"
									+ processed);
							return true;
						}
						/** T Series*/
						codAmount=null;
						lcAmount=null;
						
					}

				}//end of product



			}else{//end of BA Amount
				++numberOfTxNumbers;
			}
			if(!StringUtil.isEmptyDouble(additinalChrges)){
				++numberOfTxNumbers;
			} if(!StringUtil.isEmptyDouble(otherAmount)){
				++numberOfTxNumbers;
			}
			if(!StringUtil.isEmptyInteger(numberOfTxNumbers)){
				collectionDoList= new ArrayList<>(numberOfTxNumbers);
				if(StringUtil.isEmptyDouble(baAmount)){
					/**
					 * If BA Amount is not exist then consider  COD/LC/TO-Pay amount for collection
					 * */
					if(!StringUtil.isEmptyDouble(codAmount)){
						String collectionType=MECUniversalConstants.COLLECTION_TYPE_COD;
						CollectionDO collectionDo=null;
						collectionDo = populateCollectionFromDrs(drsDtls,
								paymentTypeMap, codAmount, collectionType,drsDtls.getModeOfPayment());
						populatePaymentDetails(drsDtls, collectionDo);
						collectionDoList.add(collectionDo);
					} if(!StringUtil.isEmptyDouble(lcAmount)){
						String collectionType=MECUniversalConstants.COLLECTION_TYPE_LC;
						CollectionDO collectionDo=null;
						collectionDo = populateCollectionFromDrs(drsDtls,
								paymentTypeMap, lcAmount, collectionType,drsDtls.getModeOfPayment());
						populatePaymentDetails(drsDtls, collectionDo);
						collectionDoList.add(collectionDo);
					}  if(!StringUtil.isEmptyDouble(toPayAmount)){
						String collectionType=MECUniversalConstants.COLLECTION_TYPE_TOPAY;
						CollectionDO collectionDo=null;
						collectionDo = populateCollectionFromDrs(drsDtls,
								paymentTypeMap, toPayAmount, collectionType,drsDtls.getModeOfPayment());
						populatePaymentDetails(drsDtls, collectionDo);
						collectionDoList.add(collectionDo);
					}
				}else{
					/**
					 * If BA Amount is exist then consider only BA amount and exclude COD/LC/TO-Pay amount
					 * */
					String collectionType="BA Amount";
					CollectionDO collectionDo=null;
					collectionDo = populateCollectionFromDrs(drsDtls,
							paymentTypeMap, baAmount, collectionType,drsDtls.getModeOfPayment());
					populatePaymentDetails(drsDtls, collectionDo);
					collectionDoList.add(collectionDo);
				}
				if(!StringUtil.isEmptyDouble(additinalChrges)){
					String collectionType="additional charges";
					CollectionDO collectionDo=null;
					collectionDo = populateCollectionFromDrs(drsDtls,
							paymentTypeMap, additinalChrges, collectionType,drsDtls.getModeOfPayment());
					if(!StringUtil.isStringEmpty(drsDtls.getModeOfPayment())){
						populatePaymentDetails(drsDtls, collectionDo);
					}
					collectionDoList.add(collectionDo);
				} if(!StringUtil.isEmptyDouble(otherAmount)){
					String collectionType="other Amount";
					CollectionDO collectionDo=null;
					collectionDo = populateCollectionFromDrs(drsDtls,
							paymentTypeMap, otherAmount, collectionType,drsDtls.getModeOfPayment());
					if(!StringUtil.isStringEmpty(drsDtls.getModeOfPayment())){
						populatePaymentDetails(drsDtls, collectionDo);
					}
					collectionDoList.add(collectionDo);
				}
				List<String> txNumberList=prepareTransactionNumberListForCollection(drsDtls, numberOfTxNumbers);
				int counter=0;
				for(String txNumber:txNumberList){
					CollectionDO drsCollectionDO =collectionDoList.get(counter);
					drsCollectionDO.setTxnNo(txNumber);
					counter++;
				}


				if(!CGCollectionUtils.isEmpty(collectionDoList)){
					processed= deliveryUniversalDAO.saveCollectionDtlsByDrs(collectionDoList);
					LOGGER.debug("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection :: Collection save  status:"
							+ processed);
					}else{
						LOGGER.warn("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection :: No Collection details are available for save for the consg :"+drsDtls.getDrsConsgNumber());
					}
				
			}
			processed= deliveryUniversalDAO.updateDrsForCollectionIntegration(drsDtls);
			LOGGER.debug("DeliveryUniversalServiceImpl ::updateDrsForCollectionIntegration :: DRS update for Collection   status:"
					+ processed);
		}
		processed=deliveryUniversalDAO.updateCollectionStatusForAllDRSChildConsignments(drsDtls);
		LOGGER.debug("DeliveryUniversalServiceImpl ::updateAllChildConsignmentsForIntegration :: DRS update for child CN   status:"
				+ processed);
		long endMilliSeconds=System.currentTimeMillis();
		long diff=endMilliSeconds-startMilliseconds;
		String time=DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff);
		String loggs=
				"DeliveryUniversalServiceImpl::processDeliveryDetailsForCollectionIntegration::END------------>:::::::startMilliseconds ["+startMilliseconds+"] endMilliSeconds:["+endMilliSeconds+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+time;
		LOGGER.debug(loggs);
	
		return processed;

	}



	/**
	 * Populate collection from drs.
	 *
	 * @param drsDtls the drs dtls
	 * @param paymentTypeMap the payment type map
	 * @param amount the amount
	 * @param collectionType the collection type
	 * @param paymentMode the payment mode
	 * @return the collection do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private CollectionDO populateCollectionFromDrs(DrsCollectionIntegrationWrapperDO drsDtls,
			Map<String, Integer> paymentTypeMap,
			Double amount, String collectionType,String paymentMode) throws CGBusinessException,
			CGSystemException {
		CollectionDO collectionDo;
		collectionDo= prepareCollectionHeaderDOFromDRS(drsDtls,amount,collectionType);
		PaymentModeDO paymentDO=StockUtility.preparePaymentModeForCollection(paymentTypeMap,paymentMode);
		collectionDo.setPaymentModeDO(paymentDO);
		return collectionDo;
	}

	/**
	 * Populate payment details.
	 *
	 * @param drsDtls the drs dtls
	 * @param collectionDo the collection do
	 */
	private void populatePaymentDetails(DrsCollectionIntegrationWrapperDO drsDtls,
			CollectionDO collectionDo) {
		collectionDo.setChqDate(drsDtls.getChequeDDDate());
		collectionDo.setChqNo(drsDtls.getChequeDDNumber());
		collectionDo.setBankName(drsDtls.getBankNameBranch());
	}

	/**
	 * Prepare collection header do from drs.
	 *
	 * @param deliveryDtlsDO the delivery dtls do
	 * @param amount the amount
	 * @param collectionType the collection type
	 * @return the collection do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private CollectionDO prepareCollectionHeaderDOFromDRS(DrsCollectionIntegrationWrapperDO deliveryDtlsDO,Double amount,String collectionType)throws CGBusinessException, CGSystemException{
		CollectionDO collectionDO=null;
		collectionDO=  new CollectionDO();
		collectionDO.setTotalAmount(amount);
		OfficeDO createdOfficeDO=new OfficeDO();
		createdOfficeDO.setOfficeId(deliveryDtlsDO.getCreatedOfficeId());
		collectionDO.setCollectionOfficeDO(createdOfficeDO);
		if(StringUtil.isStringEmpty(deliveryDtlsDO.getDrsConsgNumber())){
			LOGGER.error("DeliveryUniversalServiceImpl ::prepareCollectionHeaderDOFromDRS::prepareCollectionHeaderDO ::Consignment Id does not exist in the delivey table for the DLV Number :"+deliveryDtlsDO.getDrsConsgNumber());
			throw new CGBusinessException("Consignment ID does not Exist for the Given Cnote: "+deliveryDtlsDO.getDrsConsgNumber());
		}
		if(!StringUtil.isEmptyInteger(deliveryDtlsDO.getCustomerId())){
			CustomerDO customerDO= new CustomerDO();
			customerDO.setCustomerId(deliveryDtlsDO.getCustomerId());
			collectionDO.setCustomerDO(customerDO);
		}else{
			LOGGER.info("DeliveryUniversalServiceImpl ::prepareCollectionHeaderDOFromDRS::prepareCollectionHeaderDO ::Customer ID does not Exist IN CN table for the CN:"+deliveryDtlsDO.getConsgNo());

		}
		collectionDO.setCollectionCategory(MECUniversalConstants.CN_COLLECTION_TYPE);
		collectionDO.setStatus(MECUniversalConstants.STATUS_OPENED);

		collectionDO.setCollectionDate(deliveryDtlsDO.getDeliveryDate());


		collectionDO.setCreatedDate(DateUtil.getCurrentDate());
		collectionDO.setUpdatedDate(DateUtil.getCurrentDate());
		prepareCollectionChildDtlsFromDrs(deliveryDtlsDO, collectionDO,collectionType);//Populate Collection child details
		return collectionDO;
	}

	/**
	 * Prepare collection child dtls from drs.
	 *
	 * @param dlvDtlsDO the dlv dtls do
	 * @param collectionDO the collection do
	 * @param collectionType the collection type
	 */
	private void prepareCollectionChildDtlsFromDrs(
			DrsCollectionIntegrationWrapperDO dlvDtlsDO, CollectionDO collectionDO,String collectionType) {
		Set<CollectionDtlsDO> collectionDOSet= new HashSet<>(1);
		if(!StringUtil.isStringEmpty(dlvDtlsDO.getDrsConsgNumber())){
			CollectionDtlsDO collectionDtls = prepareCollectionDetailsDO(dlvDtlsDO,
					collectionDO);
			collectionDtls.setCollectionType(collectionType);
			collectionDtls.setCollectionDO(collectionDO);
			collectionDOSet.add(collectionDtls);

		}
		collectionDO.setCollectionDtls(collectionDOSet);

	}

	/**
	 * Prepare transaction number list for collection.
	 *
	 * @param deliveryDtlsDO the delivery dtls do
	 * @param noOfSequence the no of sequence
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<String> prepareTransactionNumberListForCollection(
			DrsCollectionIntegrationWrapperDO deliveryDtlsDO,int noOfSequence)
					throws CGBusinessException {
		List<String> generatedTxNumber=null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO
		.setProcessRequesting(MECUniversalConstants.TX_CODE_CC);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSequence);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO.setPrefixCode(deliveryDtlsDO.getCreatedOfficeCode()+MECUniversalConstants.TX_CODE_CC);
		sequenceGeneratorConfigTO.setLengthOfNumber(CommonConstants.COLLECTION_TRANSACTION_NUMBER_LENGTH);
		sequenceGeneratorConfigTO.setRequestingBranchId(deliveryDtlsDO.getCreatedOfficeId());
		sequenceGeneratorConfigTO.setSequenceRunningLength(CommonConstants.COLLECTION_RUNNING_NUMBER_LENGTH);
		try {
			sequenceGeneratorConfigTO = sequenceGeneratorService
					.getCollectionSequence(sequenceGeneratorConfigTO);
			generatedTxNumber=sequenceGeneratorConfigTO.getGeneratedSequences();

		} catch (Exception e) {
			LOGGER.error("DeliveryUniversalServiceImpl ::processDeliveryDetailsForCollection:: NUmber Generation logic Exception", e);
			throw new CGBusinessException("Problem in MEC Number Generation for DRS Scheduler");
		}
		return generatedTxNumber;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedTypeByConsignmentAndLoggedInoffice(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<?> getManifestedTypeByConsignmentAndLoggedInoffice(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getManifestedTypeByConsignmentAndLoggedInoffice(dlvInputTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.drs.service.DeliveryUniversalService#getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<?> getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(AbstractDeliveryTO dlvInputTO) throws CGBusinessException, CGSystemException{
		return deliveryUniversalDAO.getManifestedTypeByConsignmentAndLoggedInofficeAndManifestNumber(dlvInputTO);
	}

	/**
	 * Gets the vendor dtls for drs.
	 *
	 * @param partyType the party type
	 * @param officeId the office id
	 * @return the vendor dtls for drs
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadMovementVendorTO> getVendorDtlsForDrs(String partyType,Integer officeId) throws CGSystemException{
		List<LoadMovementVendorTO> vendorPartyTOList = businessCommonService.getPartyNames(partyType, officeId);
		return vendorPartyTOList;
	}

	/**
	 * Gets the vendor dtls for drs by city id.
	 *
	 * @param partyType the party type
	 * @param cityId the city id
	 * @return the vendor dtls for drs by city id
	 * @throws CGSystemException the cG system exception
	 */
	public List<LoadMovementVendorTO> getVendorDtlsForDrsByCityId(String partyType,Integer cityId) throws CGSystemException{
		List<LoadMovementVendorTO> vendorPartyTOList = businessCommonService.getVendorDtlsForDrsByLoggdCity(partyType, cityId);
		return vendorPartyTOList;
	}


	/**
	 * Gets the out manifested consgn dtls for third party.
	 *
	 * @param inputTo the input to
	 * @return the out manifested consgn dtls for third party
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public DeliveryConsignmentTO getOutManifestedConsgnDtlsForThirdParty(
			AbstractDeliveryTO inputTo) throws CGSystemException,
			CGBusinessException {
		DeliveryConsignmentTO consgTO = null;
		ConsignmentDO consgDO = deliveryUniversalDAO.getManifestedConsgnDtls(
				inputTo.getConsignmentNumber(),
				CommonConstants.MANIFEST_TYPE_OUT);
		consgTO = convertConsignmentDO2TO(consgDO);
		return consgTO;
	}
	public static StringBuffer getPricingFromConsg(ConsignmentDO consgDO) {
		StringBuffer rateDtls= new StringBuffer();
		rateDtls.append("CN Details :[");
		rateDtls.append(" CN Number :");
		rateDtls.append(consgDO.getConsgNo());
		rateDtls.append("\t COD Amount :");
		rateDtls.append(consgDO.getCodAmt());
		rateDtls.append("\t LC Amount :");
		rateDtls.append(consgDO.getLcAmount());
		rateDtls.append("\t TOPay Amount :");
		rateDtls.append(consgDO.getTopayAmt());
		rateDtls.append("\t Pricing Amount :");
		rateDtls.append(consgDO.getPrice());
		
		rateDtls.append("\t BA  Amount :");
		rateDtls.append(consgDO.getBaAmt());
		rateDtls.append(" \t ]");
		return rateDtls;
	}

}
