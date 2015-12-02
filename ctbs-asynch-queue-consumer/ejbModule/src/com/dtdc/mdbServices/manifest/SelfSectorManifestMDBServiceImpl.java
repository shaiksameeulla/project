/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.ManifestConstant;
import src.com.dtdc.mdbDao.manifest.SelfSectorManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.document.DocumentDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.manifest.SelfSectorManifestTO;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfSectorManifestMDBServiceImpl.
 *
 * @author nisahoo
 */
public class SelfSectorManifestMDBServiceImpl implements
SelfSectorManifestMDBService {

	/** logger. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(SelfSectorManifestMDBServiceImpl.class);

	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;

	/** The self sector manifest mdbdao. */
	private SelfSectorManifestMDBDAO selfSectorManifestMDBDAO = null;

	/**
	 * Gets the ctbs application mdbdao.
	 *
	 * @return the ctbsApplicationMDBDAO
	 */
	public CTBSApplicationMDBDAO getCtbsApplicationMDBDAO() {
		return ctbsApplicationMDBDAO;
	}

	/**
	 * Sets the ctbs application mdbdao.
	 *
	 * @param ctbsApplicationMDBDAO the ctbsApplicationMDBDAO to set
	 */
	public void setCtbsApplicationMDBDAO(
			CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/**
	 * Gets the self sector manifest mdbdao.
	 *
	 * @return the selfSectorManifestMDBDAO
	 */
	public SelfSectorManifestMDBDAO getSelfSectorManifestMDBDAO() {
		return selfSectorManifestMDBDAO;
	}

	/**
	 * Sets the self sector manifest mdbdao.
	 *
	 * @param selfSectorManifestMDBDAO the selfSectorManifestMDBDAO to set
	 */
	public void setSelfSectorManifestMDBDAO(
			SelfSectorManifestMDBDAO selfSectorManifestMDBDAO) {
		this.selfSectorManifestMDBDAO = selfSectorManifestMDBDAO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.SelfSectorManifestMDBService#saveOrUpdateSelfSectorManifest(CGBaseTO)
	 */
	@Override
	public SelfSectorManifestTO saveOrUpdateSelfSectorManifest(CGBaseTO cgBaseTO)
	throws CGSystemException {

		SelfSectorManifestTO selfSectorTO = (SelfSectorManifestTO) cgBaseTO
		.getBaseList().get(0);
		return saveOrUpdateSelfSectorManifest(selfSectorTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.SelfSectorManifestMDBService#saveOrUpdateSelfSectorManifest(SelfSectorManifestTO)
	 */
	@Override
	public SelfSectorManifestTO saveOrUpdateSelfSectorManifest(
			SelfSectorManifestTO selfSectorTO) throws CGSystemException {
		LOGGER.info("SelfSectorManifestMDBServiceImpl: saveOrUpdateSelfSectorManifest(): START");

		// Validate Mode selected with Modes available between Offices
		List<ModeDO> modeList = ctbsApplicationMDBDAO.getModesBetweenOffices(
				selfSectorTO.getOriginOfficeId(),
				selfSectorTO.getDestGatewayOfficeId());
		boolean modeChkFlg = false;
		if (modeList != null && modeList.size() > 0) {
			for (ModeDO modeDO : modeList) {
				if (modeDO.getModeId() == Integer.parseInt(selfSectorTO
						.getMode())) {
					modeChkFlg = true;
				}
			}
		}
		if (modeChkFlg) {
			List<ManifestDO> manifestDOList = createManifestDOfromTO(selfSectorTO);
			for (ManifestDO manifestDO : manifestDOList) {
				selfSectorManifestMDBDAO
				.saveOrUpdateSelfSectorManifest(manifestDO);
			}
		}
		LOGGER.info("SelfSectorManifestMDBServiceImpl: saveOrUpdateSelfSectorManifest(): END");
		return selfSectorTO;
	}

	/**
	 * Creates the manifest d ofrom to.
	 *
	 * @param selfSectorTO the self sector to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> createManifestDOfromTO(
			SelfSectorManifestTO selfSectorTO) throws CGSystemException {
		LOGGER.info("SelfSectorManifestMDBServiceImpl: createManifestDOfromTO(): START");
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();

		int noOfConsignments = selfSectorTO.getConsignmentNo().length;

		for (int i = 0; i < noOfConsignments; i++) {

			// Fetch the Manifest Details By Composite key
			ManifestDO manifestDO = selfSectorManifestMDBDAO
			.getManifestDetailsByCompositeID(
					selfSectorTO.getManifestNumber(),
					selfSectorTO.getConsignmentNo()[i],
					ManifestConstant.SELF_SECTOR_MANIFEST_CODE);

			// Decide Save/Update to Self Sector manifest
			if (manifestDO == null) {
				manifestDO = new ManifestDO();
			}

			/* Set Manifest Related Details */
			manifestDO.setManifestNumber(selfSectorTO.getManifestNumber());
			manifestDO.setManifestDate(DateFormatterUtil.getDateFromString(
					selfSectorTO.getDate(), DateFormatterUtil.DD_MM_YYYY));
			manifestDO.setManifestTime(selfSectorTO.getTime());
			manifestDO.setTotWeightKgs(selfSectorTO.getTotalWt());
			manifestDO.setTotConsgNum(noOfConsignments);
			manifestDO.setNoOfBagMade(selfSectorTO.getNoOfBags());
			manifestDO
			.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);

			if (selfSectorTO.getDocType()[i] != null
					&& !selfSectorTO.getDocType()[i].equals("")) {
				DocumentDO documentDO = ctbsApplicationMDBDAO
				.getDocumentByIdOrType(null,
						selfSectorTO.getDocType()[i]);
				manifestDO.setDocument(documentDO);
			} else {
				manifestDO.setDocument(null);
			}

			ManifestTypeDO manifestTypeDO = selfSectorManifestMDBDAO
			.getManifestTypeByCode(ManifestConstant.SELF_SECTOR_MANIFEST_CODE);
			manifestDO.setMnsftTypes(manifestTypeDO);

			EmployeeDO employeeDO = ctbsApplicationMDBDAO
			.getEmployeeByCodeOrID(selfSectorTO.getPreparedById(), "");
			manifestDO.setEmployee(employeeDO);

			if (selfSectorTO.getMode() != null
					&& Integer.parseInt(selfSectorTO.getMode()) != 0) {
				int modeId = Integer.parseInt(selfSectorTO.getMode());
				ModeDO mode = ctbsApplicationMDBDAO.getModeById(modeId);
				manifestDO.setMode(mode);
			}

			/* Consignment Related Details */
			manifestDO.setConsgNumber(selfSectorTO.getConsignmentNo()[i]);
			manifestDO.setIndvWeightKgs(selfSectorTO.getWeight()[i]);
			// manifestDO.setServiceType(selfSectorTO.getServiceType()[i]);
			manifestDO.setIndvWeightKgs(selfSectorTO.getWeight()[i]);
			manifestDO.setWeighingType(selfSectorTO.getWeighingType()[i]);

			ProductDO productDO = ctbsApplicationMDBDAO.getProductByIdOrCode(
					selfSectorTO.getProductId()[i], "");
			manifestDO.setProduct(productDO);

			if (selfSectorTO.getHandlingInstructions() != null
					&& !selfSectorTO.getHandlingInstructions()
					.equalsIgnoreCase("Select")
					&& Integer.parseInt(selfSectorTO.getHandlingInstructions()) != 0) {
				StdHandlingInstDO stdHandleInstDO = new StdHandlingInstDO();
				stdHandleInstDO.setHandleInstId(Integer.parseInt(selfSectorTO
						.getHandlingInstructions()));
				manifestDO.setStdHandleInst(stdHandleInstDO);
			} else {
				manifestDO.setStdHandleInst(null);
			}

			CityDO cityDO = ctbsApplicationMDBDAO.getCityByIdOrCode(
					selfSectorTO.getDestCityId()[i], "");
			manifestDO.setDestCity(cityDO);

			PincodeDO pincodeDO = ctbsApplicationMDBDAO.getPinCodeByIdOrCode(
					selfSectorTO.getPinCodeId()[i], "");
			manifestDO.setDestPinCode(pincodeDO);

			OfficeDO destBranch = ctbsApplicationMDBDAO.getBranchByCodeOrID(
					selfSectorTO.getDestGatewayOfficeId(), "");
			manifestDO.setDestBranch(destBranch);

			manifestDO.setDbServer(selfSectorTO.getDbServer());

			manifestDOList.add(manifestDO);
		}
		LOGGER.info("SelfSectorManifestMDBServiceImpl: createManifestDOfromTO(): END");
		return manifestDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.SelfSectorManifestMDBService#saveOrUpdateSelfSectorMnfstIntlDBSync(CGBaseTO)
	 */
	@Override
	public String saveOrUpdateSelfSectorMnfstIntlDBSync(CGBaseTO manifestTOs)
	throws CGSystemException {
		List<SelfSectorManifestTO> selfSectorManifestTOList = (List<SelfSectorManifestTO>) manifestTOs
		.getBaseList();
		if (selfSectorManifestTOList != null
				&& selfSectorManifestTOList.size() > 0) {
			return saveOrUpdateSelfSectorMnfstIntlDBSync(selfSectorManifestTOList);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.SelfSectorManifestMDBService#saveOrUpdateSelfSectorMnfstIntlDBSync(List)
	 */
	@Override
	public String saveOrUpdateSelfSectorMnfstIntlDBSync(
			List<SelfSectorManifestTO> manifestTOsList)
	throws CGSystemException {
		LOGGER.debug("SelfSectorManifestMDBServiceImpl: saveOrUpdateSelfSectorMnfstIntlDBSync():START");
		String saveStatus = "FALSE";
		List<ManifestDO> manifestDOList = convertTOtoManifestDODBSync(manifestTOsList);

		for (ManifestDO manifestDO : manifestDOList) {
			selfSectorManifestMDBDAO.saveOrUpdateSelfSectorManifest(manifestDO);
			saveStatus = "TRUE";
		}

		LOGGER.debug("SelfSectorManifestMDBServiceImpl: saveOrUpdateSelfSectorMnfstIntlDBSync():END");
		return saveStatus;
	}

	/**
	 * Convert t oto manifest dodb sync.
	 *
	 * @param selfSectorTOList the self sector to list
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> convertTOtoManifestDODBSync(
			List<SelfSectorManifestTO> selfSectorTOList)
			throws CGSystemException {
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();

		for (SelfSectorManifestTO selfSectorTO : selfSectorTOList) {
			ManifestDO manifestDO = new ManifestDO();

			manifestDO.setManifestNumber(selfSectorTO.getManifestNumber());
			if(selfSectorTO.getDestGatewayOfficeId()>0){
				OfficeDO officeDO = new OfficeDO();
				officeDO.setOfficeId(selfSectorTO.getDestGatewayOfficeId());
				manifestDO.setDestBranch(officeDO);
			}
			if(selfSectorTO.getOriginOfficeId()>0){
				OfficeDO officeDO = new OfficeDO();
				officeDO.setOfficeId(selfSectorTO.getOriginOfficeId());
				manifestDO.setOriginBranch(officeDO);
			}
			if(selfSectorTO.getDocIdField()>0){
				DocumentDO documentDO = new DocumentDO();
				documentDO.setDocumentId(selfSectorTO.getDocIdField());
				manifestDO.setDocument(documentDO);
			}
			manifestDO.setNoOfBagMade(selfSectorTO.getNoOfBags());
			if(selfSectorTO.getHandlingInstIdField()>0){
				StdHandlingInstDO stdHandlingInstDO  = new StdHandlingInstDO();
				stdHandlingInstDO.setHandleInstId(selfSectorTO.getHandlingInstIdField());
				manifestDO.setStdHandleInst(stdHandlingInstDO);
			}
			if(selfSectorTO.getColoaderIdField()>0){
				CoLoaderDO coloaderDO = new CoLoaderDO();
				coloaderDO.setLoaderId(selfSectorTO.getColoaderIdField());
				manifestDO.setCoLoader(coloaderDO);
			}
			if(selfSectorTO.getOpsPersonId()>0){
				EmployeeDO employeeDO = new EmployeeDO();
				employeeDO.setEmployeeId(selfSectorTO.getOpsPersonId());
				manifestDO.setEmployee(employeeDO);
			}
			manifestDO.setVechileOrFlightNo(selfSectorTO.getFlightDetails());
			manifestDO.setManifestId(selfSectorTO.getManifestIdField());
			manifestDO.setConsgNumber(selfSectorTO.getConsignmentNoField());
			manifestDO.setIndvWeightKgs(selfSectorTO.getWeightField());
			manifestDO.setWeighingType(selfSectorTO.getWeighingTypeField());
			if(selfSectorTO.getProductField()>0){
				ProductDO productDO = new ProductDO();
				productDO.setProductId(selfSectorTO.getProductField());
				manifestDO.setProduct(productDO);
			}
			if(selfSectorTO.getModeIdField()>0){
				ModeDO modeDO = new ModeDO();
				modeDO.setModeId(selfSectorTO.getModeIdField());
				manifestDO.setMode(modeDO);
			}
			manifestDO.setManifestDate(DateFormatterUtil.getDateFromString(
					selfSectorTO.getDate(), DateFormatterUtil.DDMMYYYY_FORMAT));
			manifestDO.setManifestTime(selfSectorTO.getTime());
			manifestDO.setTotWeightKgs(selfSectorTO.getTotalWt());
			manifestDO.setNoOfBagMade(selfSectorTO.getNoOfBags());
			manifestDO.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
			manifestDO.setStatus(ManifestConstant.MANIFEST_STATUS);
			if(selfSectorTO.getModeIdField()>0){
				ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
				manifestTypeDO.setMnfstTypeId(selfSectorTO.getManifestTypeIdField());
				manifestDO.setMnsftTypes(manifestTypeDO);
			}			
			manifestDO.setTotConsgNum(Integer.parseInt(selfSectorTO.getTotConsgNum()));
			if(selfSectorTO.getDestCityIdField()>0){
				CityDO destCityDO = new CityDO();
				destCityDO.setCityId(selfSectorTO.getDestCityIdField());
				manifestDO.setDestCity(destCityDO);
			}			
			if(selfSectorTO.getDestPincodeId()>0){
				PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(selfSectorTO.getDestPincodeId());
				manifestDO.setDestPinCode(pincodeDO);
			}
			if(selfSectorTO.getServiceId()>0){
				ServiceDO serviceDO = new ServiceDO();
				serviceDO.setServiceId(selfSectorTO.getServiceId());
				manifestDO.setService(serviceDO);
			}

			manifestDO.setDbServer(selfSectorTO.getDbServer());
			manifestDO.setDiFlag("N");
			manifestDO.setReadByLocal("Y");
			manifestDOList.add(manifestDO);
		}

		return manifestDOList;
	}	

}
