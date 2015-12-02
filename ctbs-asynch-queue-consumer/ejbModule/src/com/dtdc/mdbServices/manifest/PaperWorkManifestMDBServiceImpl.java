/**
 * 
 */
package src.com.dtdc.mdbServices.manifest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.mdbDao.manifest.PaperWorkManifestMDBDAO;
import src.com.dtdc.mdbServices.CTBSApplicationMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.master.StdHandlingInstDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import src.com.dtdc.constants.ManifestConstant;

import com.dtdc.to.manifest.PaperWorkManifestNonDoxTO;

// TODO: Auto-generated Javadoc
/**
 * The Class PaperWorkManifestMDBServiceImpl.
 *
 * @author nisahoo
 */
public class PaperWorkManifestMDBServiceImpl implements
		PaperWorkManifestMDBService {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaperWorkManifestMDBServiceImpl.class);
	
	/** The ctbs application mdbdao. */
	private CTBSApplicationMDBDAO ctbsApplicationMDBDAO = null;
	
	/** The paper work manifest mdbdao. */
	private PaperWorkManifestMDBDAO paperWorkManifestMDBDAO = null;
	
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
	public void setCtbsApplicationMDBDAO(CTBSApplicationMDBDAO ctbsApplicationMDBDAO) {
		this.ctbsApplicationMDBDAO = ctbsApplicationMDBDAO;
	}

	/**
	 * Gets the paper work manifest mdbdao.
	 *
	 * @return the paperWorkManifestMDBDAO
	 */
	public PaperWorkManifestMDBDAO getPaperWorkManifestMDBDAO() {
		return paperWorkManifestMDBDAO;
	}

	/**
	 * Sets the paper work manifest mdbdao.
	 *
	 * @param paperWorkManifestMDBDAO the paperWorkManifestMDBDAO to set
	 */
	public void setPaperWorkManifestMDBDAO(
			PaperWorkManifestMDBDAO paperWorkManifestMDBDAO) {
		this.paperWorkManifestMDBDAO = paperWorkManifestMDBDAO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PaperWorkManifestMDBService#saveOrUpdatePaperWorkManifest(CGBaseTO)
	 */
	@Override
	public PaperWorkManifestNonDoxTO saveOrUpdatePaperWorkManifest(
			CGBaseTO cgBaseTO) throws CGSystemException,CGBusinessException {
		
		PaperWorkManifestNonDoxTO pwmanifestTO = (PaperWorkManifestNonDoxTO) cgBaseTO.getBaseList().get(0);		
		
		return saveOrUpdatePaperWorkManifest(pwmanifestTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.manifest.PaperWorkManifestMDBService#saveOrUpdatePaperWorkManifest(PaperWorkManifestNonDoxTO)
	 */
	@Override
	public PaperWorkManifestNonDoxTO saveOrUpdatePaperWorkManifest(PaperWorkManifestNonDoxTO pwmanifestTO) throws CGSystemException, CGBusinessException{
		
		/* Check if the manifest is already put into bag manifest or not */
		boolean manifestInBagCheck = isManifestInBag(pwmanifestTO.getManifestNumber());
		LOGGER .info("Manifest Update Possible="+manifestInBagCheck);
		
		//boolean cnCheck = isPaperWorkConsignment(pwmanifestTO);
		
		/* Validate PinCode serviceability */
		// Code to go here
		
		List<ManifestDO> manifestDOList = convertPaperWorkManifestTOtoManifestDO(pwmanifestTO);
		for (ManifestDO manifestDO: manifestDOList){
			paperWorkManifestMDBDAO.savePaperWorkManifest(manifestDO);
		}
		/* After save or Update to manifest, update the weight change to booking */
		List<BookingDO> bookingDOList = createBookingDOfromPaperWorkmanifestTO(pwmanifestTO);
		if(bookingDOList != null){
			for(BookingDO bookingDO: bookingDOList){
				paperWorkManifestMDBDAO.updateBookingWeight(bookingDO);
			}
		}
		
		return pwmanifestTO;
	}
	
	/**
	 * Checks if is manifest in bag.
	 *
	 * @param manifestNo the manifest no
	 * @return true, if is manifest in bag
	 */
	private boolean isManifestInBag(String manifestNo){
		
		return paperWorkManifestMDBDAO.isManifestInBag(manifestNo);
	}
	
	/**
	 * Convert paper work manifest t oto manifest do.
	 *
	 * @param pwmanifestTO the pwmanifest to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	private List<ManifestDO> convertPaperWorkManifestTOtoManifestDO(PaperWorkManifestNonDoxTO pwmanifestTO) throws CGSystemException
	{
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		int totalCns = pwmanifestTO.getConsignmentNo().length;
		
		for (int i = 0; i < totalCns; i++) {
			
			/*Save or Update */
			ManifestDO manifestDO = paperWorkManifestMDBDAO
					.getManifestDetailsByCompositeID(
							pwmanifestTO.getManifestNumber(),
							pwmanifestTO.getConsignmentNo()[i],
							ManifestConstant.PAPER_WORK_MANIFEST);
			if(manifestDO == null){
				manifestDO = new ManifestDO();
			}
			
			/* Set Manifest Related Details */
			manifestDO.setManifestNumber(pwmanifestTO.getManifestNumber());
			manifestDO.setManifestDate(DateFormatterUtil.getDateFromString(pwmanifestTO.getStrDate(), DateFormatterUtil.DD_MM_YYYY));
			manifestDO.setManifestTime(pwmanifestTO.getStrTime());
			manifestDO.setTotWeightKgs(Double.parseDouble(pwmanifestTO.getManifestWeight()));
			manifestDO.setManifestType(ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING);
			manifestDO.setStatus(ManifestConstant.MANIFEST_STATUS);
			manifestDO.setTotConsgNum(totalCns);
			
			ManifestTypeDO manifestTypeDO = ctbsApplicationMDBDAO.getManifestTypeByCode(ManifestConstant.PAPER_WORK_MANIFEST);
			manifestDO.setMnsftTypes(manifestTypeDO);
			
			EmployeeDO employeeDO = ctbsApplicationMDBDAO.getEmployeeByCodeOrID(pwmanifestTO.getLoggedInUserId(), "");
			manifestDO.setEmployee(employeeDO);
			
			if(pwmanifestTO.getMode()!= null && pwmanifestTO.getMode()!= 0){
				ModeDO modeDO = ctbsApplicationMDBDAO.getModeById(pwmanifestTO.getMode());
				manifestDO.setMode(modeDO);
			}
			
			if(pwmanifestTO.getHandlingInstruction()!= null && pwmanifestTO.getHandlingInstruction()!= 0){
				StdHandlingInstDO stdHandleInst = ctbsApplicationMDBDAO.getStdHandlingByIdOrCode(pwmanifestTO.getHandlingInstruction(), "");
				manifestDO.setStdHandleInst(stdHandleInst);
			}
			
			/* Set Consignment Related Details */
			if(pwmanifestTO.getConsignmentNo()[i] != null && 
					!pwmanifestTO.getConsignmentNo()[i].equalsIgnoreCase("")){
				manifestDO.setConsgNumber(pwmanifestTO.getConsignmentNo()[i]);
				BookingDO bookingDO = paperWorkManifestMDBDAO.getBookedConsignmentByCnNumber(pwmanifestTO.getConsignmentNo()[i]);
				if(bookingDO != null){
					manifestDO.setService(bookingDO.getServiceID());
					manifestDO.setServiceType(bookingDO.getServiceID().getServiceType());
					manifestDO.setProduct(bookingDO.getServiceID().getProductDO());
					manifestDO.setDocument(bookingDO.getDocumentID());
					
					BookingDO parentBookingDO = paperWorkManifestMDBDAO.getBookingDetailsByParentCnNo(bookingDO.getParentCnNumber());
					manifestDO.setOffice(parentBookingDO.getOfficeID());
					Double volWt = parentBookingDO.getVolumetricWght();
					if(volWt != null){
						manifestDO.setVolumetricWeight(volWt);
						manifestDO.setLength(Double.parseDouble(String.valueOf(parentBookingDO.getLength())));
						manifestDO.setBreadth(Double.parseDouble(String.valueOf(parentBookingDO.getBreadth())));
						manifestDO.setHeight(Double.parseDouble(String.valueOf(parentBookingDO.getHeight())));
					}
				}
			}
			manifestDO.setRemarks(pwmanifestTO.getRemarks()[i]);
			manifestDO.setIndvWeightKgs(pwmanifestTO.getWeightMFDetails()[i]);
			manifestDO.setNoOfPieces(pwmanifestTO.getNoOfPieces()[i]);
			manifestDO.setWeighingType(pwmanifestTO.getWeighingType()[i]);
			
			/* Set City and PinCode Details */
			CityDO destCity = paperWorkManifestMDBDAO.getDestCityByCityName(pwmanifestTO.getDestCity()[i]);
			manifestDO.setDestCity(destCity);
			PincodeDO destPinCode = ctbsApplicationMDBDAO.getPinCodeByIdOrCode(null, pwmanifestTO.getDestPincode()[i]);
			manifestDO.setDestPinCode(destPinCode);
			 
			/* Set Office Related Details */
			if(pwmanifestTO.getEmployeeOfficeId()!= null &&
					pwmanifestTO.getEmployeeOfficeId()!= 0){
				OfficeDO originOffice = ctbsApplicationMDBDAO.getBranchByCodeOrID(pwmanifestTO.getEmployeeOfficeId(),"");
				manifestDO.setOriginBranch(originOffice);

			}
			if(pwmanifestTO.getRepOfficeId()!= null &&
					pwmanifestTO.getRepOfficeId()!= 0){
				OfficeDO repOffice = ctbsApplicationMDBDAO.getBranchByCodeOrID(pwmanifestTO.getRepOfficeId(), "");
				manifestDO.setReportingBranch(repOffice);
			}
			
			if(pwmanifestTO.getDestBranchId()!= null &&
					pwmanifestTO.getDestBranchId()!= 0){
				OfficeDO destOffice = ctbsApplicationMDBDAO.getBranchByCodeOrID(pwmanifestTO.getDestBranchId(), "");
				manifestDO.setDestBranch(destOffice);
			}
			
			manifestDO.setDbServer(pwmanifestTO.getDbServer());
			
		manifestDOList.add(manifestDO);
		}
		return manifestDOList;
	}
	
	/**
	 * Creates the booking d ofrom paper workmanifest to.
	 *
	 * @param pwmanifestTO the pwmanifest to
	 * @return the list
	 */
	private List<BookingDO> createBookingDOfromPaperWorkmanifestTO(PaperWorkManifestNonDoxTO pwmanifestTO) {
		List<BookingDO> bookingDOList = new ArrayList<BookingDO>();
		for (int i = 0; i < pwmanifestTO.getConsignmentNo().length; i++) {
			if (pwmanifestTO.getBookingWtModify()[i] != null
					&& pwmanifestTO.getBookingWtModify()[i].equalsIgnoreCase("Y")) {
				BookingDO bookingDO = paperWorkManifestMDBDAO.getBookedConsignmentByCnNumber(pwmanifestTO.getConsignmentNo()[i]);
				bookingDO.setFinalWeight(pwmanifestTO.getWeightMFDetails()[i]);
				bookingDO.setUpdatedFromProcess(ManifestConstant.BOOKING_UPDATE_PROCESS_PAPERWORK);
				bookingDOList.add(bookingDO);
			}
		}
		return bookingDOList;
	}

}
