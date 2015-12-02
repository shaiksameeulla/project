/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.CommonConstants;
import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.manifest.AgentManifestIntlMDBDAO;
import src.com.dtdc.mdbDao.manifest.OutgoingManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.ConsigneeAddressDO;
import com.dtdc.domain.master.customer.ConsigneeDO;
import com.dtdc.domain.master.customer.CustAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.CountryDO;
import com.dtdc.domain.master.geography.DistrictDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.geography.StateDO;
import com.dtdc.domain.master.geography.ZoneDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.office.OpsOfficeDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.domain.transaction.manifest.ManifestBookingDO;
import com.dtdc.to.common.OpsOfficeTO;
import com.dtdc.to.manifest.AgentManifestIntlTO;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentManifestIntlMDBServiceImpl.
 *
 * @author Narasimha Rao Kattunga
 */
public class AgentManifestIntlMDBServiceImpl implements
AgentManifestIntlMDBService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(AgentManifestIntlMDBServiceImpl.class);

	/** The agent manifest mdbdao. */
	private AgentManifestIntlMDBDAO agentManifestMDBDAO = null;

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;

	/** The outgoing manifest mdbdao. */
	private OutgoingManifestMDBDAO outgoingManifestMDBDAO = null;

	/**
	 * Gets the agent manifest mdbdao.
	 *
	 * @return the agent manifest mdbdao
	 */
	public AgentManifestIntlMDBDAO getAgentManifestMDBDAO() {
		return agentManifestMDBDAO;
	}

	/**
	 * Sets the agent manifest mdbdao.
	 *
	 * @param agentManifestMDBDAO the new agent manifest mdbdao
	 */
	public void setAgentManifestMDBDAO(
			AgentManifestIntlMDBDAO agentManifestMDBDAO) {
		this.agentManifestMDBDAO = agentManifestMDBDAO;
	}

	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbs application mdbdao
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the new ctbs application mdbdao
	 */
	public void setCtbsApplicationMDBDAO(
			CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/**
	 * Gets the outgoing manifest mdbdao.
	 *
	 * @return the outgoing manifest mdbdao
	 */
	public OutgoingManifestMDBDAO getOutgoingManifestMDBDAO() {
		return outgoingManifestMDBDAO;
	}

	/**
	 * Sets the outgoing manifest mdbdao.
	 *
	 * @param outgoingManifestMDBDAO the new outgoing manifest mdbdao
	 */
	public void setOutgoingManifestMDBDAO(
			OutgoingManifestMDBDAO outgoingManifestMDBDAO) {
		this.outgoingManifestMDBDAO = outgoingManifestMDBDAO;
	}

	/**
	 * Save agent manifest.
	 *
	 * @param agentManifestTO the agent manifest to
	 * @return String Manifest Number
	 * @throws CGSystemException and CGBusinessException
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public String saveAgentManifest(AgentManifestIntlTO agentManifestTO)
	throws CGSystemException, CGBusinessException {
		List<ManifestDO> manifestDOList = null;
		String agentDetails = "";
		try {
			manifestDOList = convertAgentManifestTOtoManifestDO(agentManifestTO);
			agentDetails = agentManifestMDBDAO
			.insertOrUpdateAgentManifest(manifestDOList);

		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::saveAgentManifest::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return agentDetails;
	}

	/**
	 * Insert Or Update Packet, Re-Route, MNP and Bag-NonDox for DB Sync.
	 *
	 * @param manifestTOs the manifest t os
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public String saveOrUpdateAgentMnfstIntlDBSync(
			List<AgentManifestIntlTO> manifestTOs) throws CGSystemException {
		LOGGER.debug("*********AgentManifestIntlMDBServiceImpl::saveOrUpdateAgentMnfstIntlDBSync*******: Start");
		String manifetsDetails = "";
		List<ManifestDO> manifestDOList = null;
		StringBuffer manifestDetailsBuff = new StringBuffer();
		try {
			manifestDOList = convertAgentManifestTOtoManifestDODBSync(manifestTOs);
			manifetsDetails = agentManifestMDBDAO
			.insertOrUpdateAgentManifest(manifestDOList);
		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::saveOrUpdateAgentMnfstIntlDBSync::Exception occured:"
					+ex.getMessage());
			manifestDetailsBuff.append(ManifestConstant.FAILURE_MSG);
			return manifestDetailsBuff.toString();
		}
		LOGGER.debug("*********AgentManifestIntlMDBServiceImpl::saveOrUpdateAgentMnfstIntlDBSync*******: End");
		return manifetsDetails;
	}

	/**
	 * insert Or Update Packet, Re-Route, MNP and Bag-NonDox for DB Sync.
	 *
	 * @param agentManifestTO the agent manifest to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveOrUpdateAgentMnfstIntlDBSync(CGBaseTO agentManifestTO)
	throws CGSystemException {
		String message = "";
		LOGGER.debug("*********saveOrUpdateAgentMnfstIntlDBSync*******: Strat");
		LOGGER.debug("*********saveOrUpdateAgentMnfstIntlDBSync*******: CGBaseTO:"
				+ agentManifestTO.getBaseList());
		List<AgentManifestIntlTO> manifestTOs = (List<AgentManifestIntlTO>) agentManifestTO
		.getBaseList();
		if (manifestTOs != null && !manifestTOs.isEmpty()) {
			message = saveOrUpdateAgentMnfstIntlDBSync(manifestTOs);
			LOGGER.debug("*********saveOrUpdateAgentMnfstIntlDBSync*******:message:"
					+ message);
		} else {
			LOGGER.error("*********saveOrUpdateAgentMnfstIntlDBSync*******: No records found");
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.AgentManifestIntlMDBService#getGatewayOfficesByCountry(Integer, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OpsOfficeTO> getGatewayOfficesByCountry(Integer originOfficeId,
			String opsOffType) throws CGSystemException {
		List<OpsOfficeTO> gatewayOfficesTOs = null;
		List<OpsOfficeDO> gatewayOfficesDOs = null;
		try {
			gatewayOfficesDOs = new ArrayList<OpsOfficeDO>();
			gatewayOfficesDOs = agentManifestMDBDAO.getGatewayOfficesByCountry(
					originOfficeId, opsOffType);
			gatewayOfficesTOs = (List<OpsOfficeTO>) CGObjectConverter
			.createTOListFromDomainList(gatewayOfficesDOs,
					OpsOfficeTO.class);
		} catch (CGBusinessException e) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::getGatewayOfficesByCountry::Exception occured:"
					+e.getMessage());
		}
		return gatewayOfficesTOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.AgentManifestIntlMDBService#getBookingDetailsByCnNum(String, Integer, String)
	 */
	@Override
	public String getBookingDetailsByCnNum(String consgNumber, Integer modeId,
			String manifestType) throws CGSystemException, CGBusinessException {
		ManifestBookingDO booking = null;
		StringBuilder agentManifestBooking = null;
		int count = 0;
		boolean isValid = Boolean.TRUE;
		ModeDO selectedMode = null;
		ModeDO modeOnConsignment = null;
		DocumentDO selectedDocument = null;
		DocumentDO documentOnConsignment = null;
		String docType = "";
		String docValidation = "";
		ManifestTypeDO mnfst = null;
		int mnfstTypeId = 0;
		try {
			agentManifestBooking = new StringBuilder();
			ResourceBundle messageREsources = ResourceBundle
			.getBundle(ManifestConstant.MESSAGE_RESOURCES);
			isValid = AgentManifestIntlMDBValidator
			.isValidConsignment(consgNumber);
			if (!isValid) {
				agentManifestBooking.append(ManifestConstant.ERROR_FLAG);
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(messageREsources
						.getString(ManifestConstant.INVALID_CONSIGNMENT));
				return agentManifestBooking.toString();

			}
			mnfst = outgoingManifestMDBDAO.getManifestType(manifestType);
			if (mnfst != null) {
				mnfstTypeId = mnfst.getMnfstTypeId();
			}
			count = outgoingManifestMDBDAO.getDuplicateConsignment(consgNumber,
					ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING,
					mnfstTypeId);
			if (count > 0) {
				agentManifestBooking.append(ManifestConstant.ERROR_FLAG);
				agentManifestBooking.append(",");
				agentManifestBooking.append(messageREsources
						.getString(ManifestConstant.DUPLICATE_CONSIGNMENT));
				return agentManifestBooking.toString();
			}
			booking = outgoingManifestMDBDAO
			.getBookingDetailsByConNum(consgNumber);
			// Mode Validation
			if (booking != null) {
				selectedMode = ctbsApplicationMDBDAO.getModeById(modeId);
				modeOnConsignment = booking.getMode();
				if (modeOnConsignment != null) {
					isValid = ManifestMDBValidator.manifestModeValidations(
							selectedMode.getModeCode(), modeOnConsignment);
					if (isValid) {
						agentManifestBooking
						.append(ManifestConstant.ERROR_FLAG);
						agentManifestBooking.append(ManifestConstant.COMMA);
						agentManifestBooking.append(messageREsources
								.getString(ManifestConstant.MODE_VALIDATION));
						return agentManifestBooking.toString();
					}
				} else {
					agentManifestBooking.append(ManifestConstant.ERROR_FLAG);
					agentManifestBooking.append(ManifestConstant.COMMA);
					agentManifestBooking.append(messageREsources
							.getString(ManifestConstant.INVALID_MODE));
					return agentManifestBooking.toString();
				}
				// Document Validation
				if (StringUtils.equals(manifestType,
						ManifestConstant.CONSIGNMENT_TYPE_DOX)) {
					docType = ManifestConstant.CONSIGNMENT_TYPE_DOX;
					docValidation = ManifestConstant.VALIDATE_DOX_CONSIGNMENT;
				}
				if (StringUtils.equals(manifestType,
						ManifestConstant.CONSIGNMENT_TYPE_NONDOX)) {
					docType = ManifestConstant.CONSIGNMENT_TYPE_NONDOX;
					docValidation = ManifestConstant.VALIDATE_NON_DOX_CONSIGNMENT;
				}
				if (!(StringUtils.isEmpty(docType))) {
					selectedDocument = outgoingManifestMDBDAO
					.getDocumentID(docType);
					documentOnConsignment = booking.getDocument();
					if (documentOnConsignment != null) {
						if (!(StringUtils.equals(
								selectedDocument.getDocumentCode(),
								documentOnConsignment.getDocumentCode()))) {
							agentManifestBooking
							.append(ManifestConstant.ERROR_FLAG);
							agentManifestBooking.append(ManifestConstant.COMMA);
							agentManifestBooking.append(messageREsources
									.getString(docValidation));
							return agentManifestBooking.toString();
						}
					} else {
						agentManifestBooking
						.append(ManifestConstant.ERROR_FLAG);
						agentManifestBooking.append(ManifestConstant.COMMA);
						agentManifestBooking.append(messageREsources
								.getString(ManifestConstant.INVALID_DOCUMENT));
						return agentManifestBooking.toString();
					}
				}
				agentManifestBooking.append("SUCCESS");
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(booking.getConsignmentNumber());
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(booking.getBookedWeight());
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(booking.getNuOfPieces());
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(booking.getDocument()
						.getDocumentId());
				agentManifestBooking.append(CommonConstants.COMMA);
				agentManifestBooking.append(booking.getMode().getModeId());
			}

		} catch (Exception e) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::getBookingDetailsByCnNum::Exception occured:"
					+e.getMessage());
		}
		return agentManifestBooking.toString();
	}

	/**
	 * Convert agent manifest t oto manifest do.
	 *
	 * @param agentManifestTO the agent manifest to
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertAgentManifestTOtoManifestDO(
			AgentManifestIntlTO agentManifestTO) throws CGBusinessException {

		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		String agentCode = "";
		int manifestIdCount = 0;
		CityDO cityFromTO = null;
		String destCodeName = null;
		String destCode = null;
		AgentDO agentFromTO = null;
		DocumentDO document = null;
		try {
			new ArrayList<Integer>();
			if (agentManifestTO.getManifestIDs().length > 0) {
				Arrays
				.asList(agentManifestTO.getManifestIDs());
				manifestIdCount = agentManifestTO.getManifestIDs().length;
			}

			for (int i = 0; i < agentManifestTO.getSlNo().length; i++) {
				ManifestDO agentManifest = new ManifestDO();
				agentManifest.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
				agentManifest.setDiFlag("N");
				// Setting Grid Data
				agentManifest
				.setConsgNumber(agentManifestTO.getConsignmentNo()[i]);
				agentManifest.setIndvWeightKgs(agentManifestTO.getWeight()[i]);
				agentManifest.setNoOfPieces(agentManifestTO.getNoOfPieces()[i]);
				agentManifest.setRefNumber(agentManifestTO.getRefNumber()[i]);
				agentManifest.setRemarks(agentManifestTO.getRemarks()[i]);
				// Setting dest city
				destCodeName = agentManifestTO.getDestCityName();
				String[] cityCodes = destCodeName.split("-");
				if (cityCodes.length > 0) {
					destCode = cityCodes[0];
					cityFromTO = ctbsApplicationMDBDAO.getCityByIdOrCode(-1,
							destCode);
					CityDO city = new CityDO();
					city.setCityId(cityFromTO.getCityId());
					agentManifest.setDestCity(city);
				}

				// Header Data
				// agentCode = agentManifestTO.getAgentCode();
				if (!(StringUtils.isEmpty(agentCode))) {
					String[] agList = agentCode.split("-");
					if (agList.length > 0) {
						agentCode = agList[1].trim();
					}
					agentFromTO = ctbsApplicationMDBDAO
					.getAgentsByCode(agentCode);
					AgentDO agent = new AgentDO();
					agent.setAgentId(agentFromTO.getAgentId());
					agentManifest.setAgent(agent);
				}
				// CoLoader
				/*
				 * String[] coLoaderCodes = agentManifestTO.getCoLoaderCode()
				 * .split("-");
				 */
				/*
				 * if (coLoaderCodes.length > 0) { coLoaderCode =
				 * coLoaderCodes[0]; coLOaderFromTO = ctbsApplicationMDBDAO
				 * .getCoLoaderByCode(coLoaderCode); CoLoaderDO coLoader = new
				 * CoLoaderDO();
				 * coLoader.setLoaderId(coLOaderFromTO.getLoaderId());
				 * agentManifest.setCoLoader(coLoader); }
				 */
				// Prepared By
				EmployeeDO employee = new EmployeeDO();
				employee.setEmployeeId(agentManifestTO.getPreparedById());
				agentManifest.setEmployee(employee);
				// Mode
				ModeDO mode = new ModeDO();
				mode.setModeId(agentManifestTO.getModeIds()[i]);
				agentManifest.setMode(mode);
				// GateWayHub - todo
				/*
				 * String[] gateWayOffice =
				 * agentManifestTO.getGateWayHub().split( "-");
				 */
				/*
				 * if (gateWayOffice.length > 0) { String opsOffCode =
				 * gateWayOffice[0]; OpsOfficeDO gatewayOffices =
				 * ctbsApplicationMDBDAO .getOpsOfficeByCodeAndType(opsOffCode,
				 * ManifestConstant.GATEWAY_CODE); OfficeDO office =
				 * gatewayOffices.getOfficeId();
				 * agentManifest.setOffice(office); }
				 */
				// Document
				document = new DocumentDO();
				document.setDocumentId(agentManifestTO.getDocumentIds()[i]);
				agentManifest.setDocument(document);
				/*
				 * agentManifest.setDocuments(agentManifestTO.getDocuments());
				 * agentManifest.setSamples(agentManifestTO.getSamples());
				 * agentManifest.setShipments(agentManifestTO.getShipments());
				 * agentManifest
				 * .setCbvNumber(agentManifestTO.getCsnOrCbnNumber());
				 * agentManifest
				 * .setCsnNumber(agentManifestTO.getCsnOrCbnNumber());
				 */
				/*
				 * agentManifest.setSendCondition(agentManifestTO
				 * .getSendCondition());
				 */
				agentManifest.setManifestNumber(agentManifestTO
						.getManifestNumber());
				agentManifest.setManifestDate(DateFormatterUtil
						.stringToDDMMYYYYFormat(agentManifestTO
								.getManifestDate()));
				agentManifest.setTransCreateDate(DateFormatterUtil
						.stringToDDMMYYYYFormat(agentManifestTO
								.getManifestDate()));
				agentManifest.setTransLastModifiedDate(DateFormatterUtil
						.stringToDDMMYYYYFormat(agentManifestTO
								.getManifestDate()));
				agentManifest
				.setManifestTime(agentManifestTO.getManifestTime());
				agentManifest
				.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
				agentManifest.setStatus(ManifestConstant.MANIFEST_STATUS);
				ManifestTypeDO manifestTypeDO = outgoingManifestMDBDAO
				.getManifestType(ManifestConstant.MANIFEST_TYPE_AGENT_MANIFEST_INTL);
				agentManifest.setMnsftTypes(manifestTypeDO);
				// for Flight details
				/*
				 * AirportDO airport = new AirportDO();
				 * airport.setAirportId(agentManifestTO.getFlightId());
				 * agentManifest.setAirport(airport);
				 */
				/*
				 * agentManifest.setWeighingType(agentManifestTO
				 * .getWeighingTypes()[i]);
				 */
				agentManifest.setDbServer(agentManifestTO.getDbServer());
				// For Edit Flow
				if (manifestIdCount > i) {
					agentManifest.setManifestId(agentManifestTO
							.getManifestIDs()[i]);
				}
				manifestDOList.add(agentManifest);
			}

		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::convertAgentManifestTOtoManifestDO::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return manifestDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.AgentManifestIntlMDBService#findByManifestNumber(String)
	 */
	@Override
	public List<AgentManifestIntlTO> findByManifestNumber(String manifestNum)
	throws CGSystemException, CGBusinessException {

		List<AgentManifestIntlTO> agentManifestTOList = new ArrayList<AgentManifestIntlTO>();
		List<ManifestDO> agentManifestList = new ArrayList<ManifestDO>();

		agentManifestList = agentManifestMDBDAO
		.findByManifestNumber(manifestNum);
		if (agentManifestList != null && agentManifestList.size() > 0) {
			// Converting DO to To
			agentManifestTOList = agentManifestIntlConvertorDOToTO(agentManifestList);
		}

		// Setting addredd
		/*
		 * for(AgentManifestIntlTO agentManifest:agentManifestTOList){
		 * ManifestBookingDO booking = new ManifestBookingDO(); booking =
		 * outgoingManifestDAO.getBookingDetailsByConNum(agentManifest.getcon);
		 * }
		 */

		return agentManifestTOList;
	}

	/**
	 * Agent manifest intl convertor do to to.
	 *
	 * @param agentManifestIntlList the agent manifest intl list
	 * @return the list
	 */
	private List<AgentManifestIntlTO> agentManifestIntlConvertorDOToTO(
			List<ManifestDO> agentManifestIntlList) {

		List<AgentManifestIntlTO> agentManifestIntlToList = new ArrayList<AgentManifestIntlTO>();
		Integer officeId = null;
		ManifestBookingDO booking = null;
		ConsigneeDO consignee = null;
		CustomerDO consigner = null;
		Set<ConsigneeAddressDO> consigneeAddresses = null;
		ConsigneeAddressDO consigneeAddress = null;
		StringBuilder consigneeAdd = null;
		CustAddressDO consignerAddress = null;
		StringBuilder consignerAdd = null;
		Set<CustAddressDO> consignerAddresses = null;
		AreaDO area = null;
		CityDO city = null;
		DistrictDO district = null;
		StateDO state = null;
		ZoneDO zone = null;
		CountryDO country = null;
		String countryName = "";
		String pinCode = "";
		PincodeDO pinCodeDO = null;
		try {
			for (ManifestDO agentManifestIntl : agentManifestIntlList) {
				AgentManifestIntlTO agentManifestIntlTO = new AgentManifestIntlTO();
				agentManifestIntlTO.setManifestId(agentManifestIntl
						.getManifestId());
				agentManifestIntlTO.setManifestNumber(agentManifestIntl
						.getManifestNumber());
				String manifestDate = DateFormatterUtil
				.getDDMMYYYYDateString(agentManifestIntl
						.getManifestDate());
				agentManifestIntlTO.setManifestDate(manifestDate);
				agentManifestIntlTO.setManifestTime(agentManifestIntl
						.getManifestTime());

				agentManifestIntlTO.setManifestType(agentManifestIntl
						.getManifestType());
				// Doc Type
				agentManifestIntlTO.setDocumentId(agentManifestIntl
						.getDocument().getDocumentId());
				agentManifestIntlTO.setDocumentName(agentManifestIntl
						.getDocument().getDocumentType());
				// Mode
				agentManifestIntlTO.setModeID(agentManifestIntl.getMode()
						.getModeId());
				agentManifestIntlTO.setModeName(agentManifestIntl.getMode()
						.getModeName());
				// CoLoader
				/*
				 * agentManifestIntlTO.setCoLoaderName(agentManifestIntl
				 * .getCoLoader().getLoaderName());
				 * agentManifestIntlTO.setCoLoaderCode(agentManifestIntl
				 * .getCoLoader().getLoaderCode());
				 * agentManifestIntlTO.setCoLoaderID(agentManifestIntl
				 * .getCoLoader().getLoaderId());
				 */
				// Gateway hub
				officeId = agentManifestIntl.getOffice().getOfficeId();

				OpsOfficeDO gatewayOffices = ctbsApplicationMDBDAO
				.getOpsOfficeById(officeId,
						ManifestConstant.GATEWAY_CODE);
				gatewayOffices.getOpsInchargeName();
				gatewayOffices.getOpsOfficeCode();

				// Destination City
				agentManifestIntlTO.setDestCityId(agentManifestIntl
						.getDestCity().getCityId());
				/*
				 * agentManifestIntlTO.setDestCityCode(agentManifestIntl
				 * .getDestCity().getCityCode());
				 */
				agentManifestIntlTO.setDestCityName(agentManifestIntl
						.getDestCity().getCityName());
				// Agent Code
				/*
				 * agentManifestIntlTO.setAgentCode(agentManifestIntl.getAgent()
				 * .getAgentCode());
				 */
				agentManifestIntlTO.setAgentId(agentManifestIntl.getAgent()
						.getAgentId());
				agentManifestIntlTO.setAgentName(agentManifestIntl.getAgent()
						.getBusinessName());
				// Airport Details
				/*
				 * agentManifestIntlTO.setFlightId(agentManifestIntl.getAirport()
				 * .getAirportId());
				 * agentManifestIntlTO.setFlightNumber(agentManifestIntl
				 * .getAirport().getAirlineNumber());
				 * agentManifestIntlTO.setFlightDate
				 * (agentManifestIntl.getAirport
				 * ().getDepartureDate().toString().replace("-", "/"));
				 */
				agentManifestIntlTO.setWeighingType(agentManifestIntl
						.getWeighingType());
				agentManifestIntlTO.setPreparedById(agentManifestIntl
						.getEmployee().getEmployeeId());
				agentManifestIntlTO.setPreparedBy(agentManifestIntl
						.getEmployee().getFirstName());
				/*
				 * agentManifestIntlTO.setCsnOrCbnNumber(agentManifestIntl
				 * .getCsnNumber());
				 * agentManifestIntlTO.setSamples(agentManifestIntl
				 * .getSamples());
				 * agentManifestIntlTO.setShipments(agentManifestIntl
				 * .getShipments());
				 * agentManifestIntlTO.setSendCondition(agentManifestIntl
				 * .getSendCondition());
				 * agentManifestIntlTO.setDocuments(agentManifestIntl
				 * .getDocuments());
				 */
				// Grid Data
				agentManifestIntlTO.setConsgRemarks(agentManifestIntl
						.getRemarks());
				agentManifestIntlTO.setConsgNoOfPieces(agentManifestIntl
						.getNoOfPieces());
				agentManifestIntlTO.setConsgWeight(agentManifestIntl
						.getIndvWeightKgs());
				agentManifestIntlTO.setWayBillNumber(agentManifestIntl
						.getConsgNumber());
				agentManifestIntlTO.setWayBillRefNumber(agentManifestIntl
						.getRefNumber());
				// For consinee and consigner
				booking = new ManifestBookingDO();
				booking = outgoingManifestMDBDAO
				.getBookingDetailsByConNum(agentManifestIntlTO
						.getWayBillNumber());
				if (booking != null) {
					consignee = booking.getConsignee();
					consigner = booking.getCustomer();
					if (consignee != null) {
						consigneeAddresses = new HashSet<ConsigneeAddressDO>();
						consigneeAddresses = consignee.getAddresses();
						if (consigneeAddresses != null) {
							if (consigneeAddresses.size() > 0) {
								Iterator<ConsigneeAddressDO> it = consigneeAddresses
								.iterator();
								if (it.hasNext()) {
									consigneeAddress = it.next();
								}
								if (consigneeAddress != null) {
									consigneeAdd = new StringBuilder();
									consigneeAdd.append(consignee
											.getFirstName());
									consigneeAdd.append(" ");
									consigneeAdd
									.append(consignee.getLastName());
									consigneeAdd.append(" ");
									consigneeAdd.append(consignee.getSurName());
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(consigneeAddress
											.getBuildingName());
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(consigneeAddress
											.getBuildingBlock());
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(consigneeAddress
											.getStreet1());
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(consigneeAddress
											.getStreet2());
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(consigneeAddress
											.getStreet3());
									consigneeAdd.append(CommonConstants.COMMA);
									area = consigneeAddress.getAreaDO();
									if (area != null) {
										city = area.getCity();
										pinCodeDO = area.getPincode();
										if (pinCodeDO != null) {
											pinCode = pinCodeDO.getPincode();
										}
										if (city != null) {
											district = city.getDistrict();
											if (district != null) {
												state = district.getState();
												if (state != null) {
													zone = state.getZone();
													if (zone != null) {
														country = zone
														.getCountry();
														countryName = country
														.getCountryName();
													}

												}
											}
										}
									}
									consigneeAdd.append(countryName);
									consigneeAdd.append(CommonConstants.COMMA);
									consigneeAdd.append(pinCode);
								}
								if (consigneeAdd != null) {
									agentManifestIntlTO
									.setConsigneeAddress(consigneeAdd
											.toString());
									agentManifestIntlTO
									.setConsigneeName(consignee
											.getFirstName());
								}
							}
							// consigner
							if (consigner != null) {
								consignerAddresses = consigner.getAddresses();
								Iterator<CustAddressDO> it = consignerAddresses
								.iterator();
								if (it.hasNext()) {
									consignerAddress = it.next();
								}
								if (consignerAddress != null) {
									consignerAdd = new StringBuilder();
									consignerAdd.append(consigner
											.getFirstName());
									consignerAdd.append(" ");
									consignerAdd
									.append(consigner.getLastName());
									consignerAdd.append(" ");
									consignerAdd.append(consigner.getSurName());
									consignerAdd.append(" ");
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getBuildingName());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getBuildingBlock());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getStreet1());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getStreet2());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getStreet3());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getArea().getCity().getCityName());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getArea().getCity().getDistrict()
											.getDistrictName());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getArea().getCity().getDistrict()
											.getState().getStateName());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getArea().getCity().getDistrict()
											.getState().getZone().getCountry()
											.getCountryName());
									consignerAdd.append(CommonConstants.COMMA);
									consignerAdd.append(consignerAddress
											.getArea().getPincode()
											.getPincode());
								}
								if (consignerAdd != null) {
									agentManifestIntlTO
									.setConsignorAddress1(consignerAdd
											.toString());
									agentManifestIntlTO
									.setConsignorName(consigner
											.getFirstName());
								}
							}
						}
					}
				}

				agentManifestIntlToList.add(agentManifestIntlTO);

			}

		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::agentManifestIntlConvertorDOToTO::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		return agentManifestIntlToList;
	}

	/**
	 * Convert agent manifest t oto manifest dodb sync.
	 *
	 * @param agentManifestTOs the agent manifest t os
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 */
	private List<ManifestDO> convertAgentManifestTOtoManifestDODBSync(
			List<AgentManifestIntlTO> agentManifestTOs)
			throws CGBusinessException {
		LOGGER.debug("AgentManifestIntlMDBServiceImpl : convertAgentManifestTOtoManifestDODBSync():::Start");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		Integer manifestId = 0;
		OfficeDO orgOff = null;
		ModeDO mode = null;
		StdHandlingInstDO stdHandling = null;
		DocumentDO doc = null;
		AgentDO agent = null;
		EmployeeDO pickBoy = null;
		try {
			if (agentManifestTOs != null && agentManifestTOs.size() > 0) {
				AgentManifestIntlTO mnfstTO = agentManifestTOs.get(0);
				if (mnfstTO.getOriginBranchID() != null
						&& mnfstTO.getOriginBranchID() > 0) {
					orgOff = new OfficeDO();
					orgOff.setOfficeId(mnfstTO.getOriginBranchID());
				}
				if (mnfstTO.getModeID() != null && mnfstTO.getModeID() > 0) {
					mode = new ModeDO();
					mode.setModeId(mnfstTO.getModeID());
				}
				if (mnfstTO.getHandlingInstID() != null
						&& mnfstTO.getHandlingInstID() > 0) {
					stdHandling = new StdHandlingInstDO();
					stdHandling.setHandleInstId(mnfstTO.getHandlingInstID());

				}
				if (mnfstTO.getDocumentId() != null
						&& mnfstTO.getDocumentId() > 0) {
					doc = new DocumentDO();
					doc.setDocumentId(mnfstTO.getDocumentId());

				}
				if (mnfstTO.getAgentId() != null && mnfstTO.getAgentId() > 0) {
					agent = new AgentDO();
					agent.setAgentId(mnfstTO.getAgentId());
				}
				if (mnfstTO.getPreparedById() != null
						&& mnfstTO.getPreparedById() > 0) {
					pickBoy = new EmployeeDO();
					pickBoy.setEmployeeId(mnfstTO.getPreparedById());
				}
				for (AgentManifestIntlTO agentManifestTO : agentManifestTOs) {
					LOGGER.debug("AgentManifestIntlMDBServiceImpl : convertAgentManifestTOtoManifestDODBSync():::CN No:"
							+ agentManifestTO.getWayBillNumber());
					ManifestDO agentManifest = new ManifestDO();
					// getting Unique manifest id for update if already exists
					Integer manifestTypeId = agentManifestTO
					.getManifestTypeId();
					String manifestType = "O";
					manifestId = outgoingManifestMDBDAO.getManifestId(
							agentManifestTO.getManifestNumber(),
							agentManifestTO.getWayBillNumber(), manifestType,
							manifestTypeId);
					if (manifestId > 0) {
						agentManifestTO.setManifestId(manifestId);
					}
					agentManifest.setManifestNumber(agentManifestTO
							.getManifestNumber());
					Date manifestDate = DateFormatterUtil
					.slashDelimitedstringToDDMMYYYYFormat(agentManifestTO
							.getManifestDate());
					agentManifest.setManifestDate(manifestDate);
					agentManifest.setManifestTime(agentManifestTO
							.getManifestTime());
					ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
					manifestTypeDO.setMnfstTypeId(manifestTypeId);
					agentManifest.setMnsftTypes(manifestTypeDO);
					agentManifest.setTotWeightKgs(agentManifestTO
							.getTotalManifestWeight());
					if (agentManifestTO.getDestPincodeId() != null
							&& mnfstTO.getDestPincodeId() > 0) {
						PincodeDO destPincode = new PincodeDO();
						destPincode.setPincodeId(mnfstTO.getDestPincodeId());
						agentManifest.setDestPinCode(destPincode);
					}
					if (doc != null) {
						agentManifest.setDocument(doc);
					}
					agentManifest.setManifestType(agentManifestTO
							.getManifestType());
					if (mode != null) {
						agentManifest.setMode(mode);
					}
					if (agentManifestTO.getDestCityId() != null
							&& mnfstTO.getDestCityId() > 0) {
						CityDO destCity = new CityDO();
						destCity.setCityId(mnfstTO.getDestCityId());
						agentManifest.setDestCity(destCity);
					}
					if (agent != null) {
						agentManifest.setAgent(agent);
					}
					agentManifest.setWeighingType(agentManifestTO
							.getWeighingType());
					if (pickBoy != null) {
						agentManifest.setEmployee(pickBoy);
					}
					agentManifest.setRemarks(agentManifestTO.getConsgRemarks());
					agentManifest.setNoOfPieces(agentManifestTO
							.getConsgNoOfPieces());
					agentManifest.setIndvWeightKgs(agentManifestTO
							.getConsgWeight());
					agentManifest.setConsgNumber(agentManifestTO
							.getWayBillNumber());
					agentManifest.setRefNumber(agentManifestTO
							.getWayBillRefNumber());
					if (orgOff != null) {
						agentManifest.setOriginBranch(orgOff);
					}
					if (stdHandling != null) {
						agentManifest.setStdHandleInst(stdHandling);
					}
					agentManifest
					.setStatus(agentManifestTO.getManifestStatus());
					agentManifest.setVechileOrFlightNo(agentManifestTO
							.getVehicleOrFlightNo());
					if (StringUtils.isNotEmpty(agentManifestTO
							.getVehicleOrFlightDate())
							&& StringUtils.isNotEmpty(agentManifestTO
									.getVehicleOrFlightTime())) {
						agentManifest
						.setVechileOrFlightDateTime(DateFormatterUtil.combineDateWithTimeHHMM(
								agentManifestTO
								.getVehicleOrFlightDate(),
								agentManifestTO
								.getVehicleOrFlightTime()));
					}
					if (agentManifestTO.getLengthInCms() > 0) {
						agentManifest.setLength(agentManifestTO
								.getLengthInCms());
					}
					if (agentManifestTO.getHeightInCms() > 0) {
						agentManifest.setHeight(agentManifestTO
								.getHeightInCms());
					}
					if (agentManifestTO.getBreadthInCms() > 0) {
						agentManifest.setBreadth(agentManifestTO
								.getBreadthInCms());
					}
					if (StringUtils.isNotEmpty(agentManifestTO
							.getVolumetricWeight())) {
						agentManifest.setVolumetricWeight(Double
								.parseDouble(agentManifestTO
										.getVolumetricWeight()));
					}
					agentManifest
					.setReadByLocal(CommonConstants.READ_BY_LOCAL_FLAG_Y);
					agentManifest.setDbServer(agentManifestTO.getDbServer());
					manifestDOList.add(agentManifest);
				}
			}

		} catch (Exception ex) {
			LOGGER.error("AgentManifestIntlMDBServiceImpl::convertAgentManifestTOtoManifestDODBSync::Exception occured:"
					+ex.getMessage());
			new CGBaseException();
		}
		LOGGER.debug("AgentManifestIntlMDBServiceImpl : convertAgentManifestTOtoManifestDODBSync():::End");
		return manifestDOList;
	}

}
