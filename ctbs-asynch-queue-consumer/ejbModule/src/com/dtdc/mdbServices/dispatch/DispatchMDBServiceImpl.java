package src.com.dtdc.mdbServices.dispatch;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import src.com.dtdc.mdbDao.dispatch.DispatchMDBDAO;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.dispatch.DispatchBagManifestDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.dispatch.DispatchReceiveBagManifestDO;
import com.dtdc.domain.dispatch.DispatchReceiverDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.OTCMasterDO;
import com.dtdc.domain.master.Billing.BillingDO;
import com.dtdc.domain.master.airline.AirportDO;
import com.dtdc.domain.master.coloader.CoLoaderDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.train.TrainDO;
import com.dtdc.domain.master.vehicle.VehicleDO;
import com.dtdc.domain.master.vendor.VendorDO;
import com.dtdc.domain.opmaster.shipment.ModeDO;
import com.dtdc.to.dispatch.DispatchTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DispatchMDBServiceImpl.
 */
public class DispatchMDBServiceImpl implements DispatchMDBService {

	/** The Constant OBC. */
	private static final String OBC = "OBC";

	/** The Constant NO. */
	private static final String NO = "NO";

	/** The Constant YES. */
	private static final String YES = "YES";

	/** The Constant DD_MM_YYYY. */
	private static final String DD_MM_YYYY = "dd/MM/yyyy";

	/** The Constant TRAIN. */
	private static final String TRAIN = "TRAIN";

	/** The Constant AIR. */
	private static final String AIR = "AIR";

	/** The Constant ROAD. */
	private static final String ROAD = "Road";

	/** The Constant SURFACE. */
	private static final String SURFACE = "Surface";

	/** The dispatch mdb dao. */
	private DispatchMDBDAO dispatchMDBDao;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DispatchMDBServiceImpl.class);

	/**
	 * Sets the dispatch mdb dao.
	 *
	 * @param dispatchMDBDao the dispatchMDBDao to set
	 */
	public void setDispatchMDBDao(DispatchMDBDAO dispatchMDBDao) {
		this.dispatchMDBDao = dispatchMDBDao;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#modifyDispatch(CGBaseTO)
	 */
	@Override
	public DispatchTO modifyDispatch(CGBaseTO cgDispatchTO) throws CGSystemException {
		DispatchTO dispatchTO=(DispatchTO)cgDispatchTO.getBaseList().get(0);
		return modifyDispatch(dispatchTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#modifyDispatch(DispatchTO)
	 */
	@Override
	public DispatchTO modifyDispatch(DispatchTO to) throws CGSystemException{
		logger.debug("DispatchMDBServiceImpl::modifyDispatch::START======>");
		DispatchTO newTo=new DispatchTO();
		newTo.setDispatchNumber(to.getDispatchNumber());
		DispatchDO dispDO=dispatchMDBDao.getDispatchByNumber(to.getDispatchNumber());
		if(to.getCdLtRrNum()!=null){
			dispDO.setCdLtRrNum(to.getCdLtRrNum());
			dispDO.setCdDate(DateFormatterUtil.getDateFromStringDDMMYYY(to.getCdCreatedDate()));
			dispDO.setCdTime(to.getCdCreatedTime());
			dispDO.setRemarks(to.getRemarks());
			if(to.getModeType()!=null){
				ModeDO mode=dispatchMDBDao.getMode(to.getModeCode());
				dispDO.setModeDO(mode);
			}
			VehicleDO vehicle=dispatchMDBDao.getVehicle(to.getVehicleRegNo());
			if(vehicle!=null){
				dispDO.setVehicalDO(vehicle);
			}
			//SAVE DO
			dispatchMDBDao.saveDispatch(dispDO);
		}
		copyDOtoTO(dispDO,newTo);
		logger.debug("DispatchMDBServiceImpl::modifyDispatch::END======>");
		return newTo;
	}

	/**
	 * Copy d oto to.
	 *
	 * @param dispDO the disp do
	 * @param to the to
	 * @throws CGSystemException the cG system exception
	 */
	private void copyDOtoTO(DispatchDO dispDO,DispatchTO to) throws CGSystemException{
		logger.debug("DispatchMDBServiceImpl::copyDOtoTO::START======>");
		to.setOrgOfficeType(dispDO.getOrgOfficeType());
		OfficeDO orgOfc=dispDO.getOriginOfficeDO();
		to.setOrgOfficeCode(orgOfc.getOfficeCode());
		to.setOrgOfficeName(orgOfc.getOfficeName());
		to.setDestOfficeType(dispDO.getDestOfficeType());
		OfficeDO destOfc=dispDO.getDestOfficeDO();
		to.setDestOfficeCode(destOfc.getOfficeCode());
		to.setDestOfficeName(destOfc.getOfficeName());
		ModeDO mode=dispDO.getModeDO();
		to.setModeType(mode.getModeId()+"");
		to.setServiceType(dispDO.getServiceType());
		if(dispDO.getServiceType().equals("C")){
			////get CO-LOADER DO
			CoLoaderDO loader=dispDO.getColoaderDO();
			if(loader!=null){
				to.setServiceCode(loader.getLoaderCode());
				to.setServiceName(loader.getLoaderName());
			}
		}else if(dispDO.getServiceType().equals("O")){
			VendorDO vendor=dispDO.getVendorDO();
			if(vendor!=null){
				to.setServiceCode(vendor.getVendorCode());
				to.setServiceName(vendor.getFirstName());
			}
		}else if(dispDO.getServiceType().equals("D")){
			EmployeeDO emp=dispDO.getDirectEmpDO();
			if(emp!=null){
				to.setServiceCode(emp.getEmpCode());
				to.setServiceName(emp.getFirstName());
			}
		}

		VehicleDO vehicle=dispDO.getVehicalDO();
		if(vehicle!=null){
			to.setVehicleRegNo(vehicle.getRegNumber());
			to.setVehicleName(vehicle.getVehicleName());
			to.setExpDepDateTime(vehicle.getVehDeptTime());
			to.setExpArrivalDateTime(vehicle.getVehArrivalTime());
		}
		else{
			//GET MODE=SURFACE/ROAD
			if(mode.getModeName().equalsIgnoreCase(SURFACE) || mode.getModeName().equalsIgnoreCase(ROAD)){
				if(dispDO.getTempVehicalCode()!=null){
					to.setVehicleRegNo(dispDO.getTempVehicalCode());
					to.setVehicleName(dispDO.getTempVehicalName());
					to.setExpDepDateTime(dispDO.getTempExpArrivalDateTime());
					to.setExpArrivalDateTime(dispDO.getTempExpArrivalDateTime());
				}
			}
		}

		to.setRemarks(dispDO.getRemarks());
		to.setCdLtRrNum(dispDO.getCdLtRrNum());
		EmployeeDO emp=dispDO.getLoadSentEmpDO();
		if(emp!=null){
			to.setEmpCode(emp.getEmpCode());
			to.setEmpName(emp.getFirstName());
		}
		to.setLoadSentDate(DateFormatterUtil.getDDMMYYYYDateToString(new Date()));
		to.setLoadSentTime(currentTime()+"");
		to.setCdCreatedDate(DateFormatterUtil.getDDMMYYYYDateToString(dispDO.getCdDate()));
		to.setCdCreatedTime(dispDO.getCdTime());
		to.setTotalWt(dispDO.getTotalWt());
		to.setChargedWt(dispDO.getChargedWt());

		BillingDO billingDO=dispDO.getBillingDO();
		if(billingDO!=null){
			to.setBillingMode(billingDO.getBillingName());
		}

		//ALSO set CHILD DATA
		List<DispatchBagManifestDO> dispatchDoList=dispatchMDBDao.getDispatchManfstChild(dispDO.getDispatchId());
		if(dispatchDoList!=null && dispatchDoList.size()>0){
			String[] manifestNum=new String[dispatchDoList.size()];
			double[] weight=new double[dispatchDoList.size()];
			String[] stickerNum=new String[dispatchDoList.size()];
			String[] remark=new String[dispatchDoList.size()];

			for(int i=0;i<dispatchDoList.size();i++){
				DispatchBagManifestDO childDO=dispatchDoList.get(i);
				manifestNum[i]=childDO.getBagManifestNum();
				weight[i]=childDO.getPhysicalWt();
				stickerNum[i]=childDO.getStickerNum();
				remark[i]=childDO.getRemark();
			}
			to.setManifestNum(manifestNum);
			to.setWeight(weight);
			to.setStickerNum(stickerNum);
			to.setRemark(remark);
		}
		logger.debug("DispatchMDBServiceImpl::copyDOtoTO::END======>");
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#saveReceiver(CGBaseTO)
	 */
	@Override
	public void saveReceiver(CGBaseTO cgDispatchTO) throws CGSystemException {
		List<DispatchTO> dispatchTOList =(List<DispatchTO>) cgDispatchTO.getBaseList();
		for(DispatchTO dispatchTO :dispatchTOList){
			saveReceiver(dispatchTO);
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#saveReceiver(DispatchTO)
	 */
	@Override
	public void saveReceiver(DispatchTO dispatchTO) throws CGSystemException{
		logger.debug("DispatchMDBServiceImpl::saveReceiver::START======>");
		try {				
			DispatchReceiverDO recvDO=null;
			try {
				if(dispatchTO.getDispatchNumber() !=null && !dispatchTO.getDispatchNumber().trim().equals("")){
					//disp=dispatchMDBDao.getDispatchByNumber(dispatchTO.getDispatchNumber());
					recvDO=dispatchMDBDao.getDispatchRecieveByNumber(dispatchTO.getDispatchNumber());
					logger.trace("DispatchMDBServiceImpl::saveReceiver::dispatchTO.getDispatchNumber()======>"+dispatchTO.getDispatchNumber());
					if(recvDO ==null){
						logger.trace("DispatchMDBServiceImpl::recvDO::value Null");
						recvDO =new DispatchReceiverDO();
						recvDO.setRecvDispatchId(null);
					}
					//				DispatchDO dispatch =new DispatchDO();				
					//				dispatch.setDispatchId(null);
					//				recvDO.setDispatchDO(dispatch);
				}else{
					logger.trace("DispatchMDBServiceImpl::recvDO::value Null");
					recvDO =new DispatchReceiverDO();
					recvDO.setRecvDispatchId(null);
				}
			} catch (Exception e1) {
				logger.error("DispatchMDBServiceImpl::saveReceiver::Error setting dispatchId to Null" + e1.getMessage());
			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>1");

			if(dispatchTO.getDispatchNumber()!=null){
				recvDO.setDispatchNumber(dispatchTO.getDispatchNumber());			
			}
			recvDO.setOrgOfficeType(dispatchTO.getOrgOfficeType());
			try{
				OfficeDO orgOffc=dispatchMDBDao.getOfficeByCode(dispatchTO.getOrgOfficeCode());

				recvDO.setOriginOfficeDO(orgOffc);
			}catch(Exception e){
				logger.error("DispatchMDBServiceImpl::saveReceiver::OfficeDO ===> Error loading officeDO");
			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>2");
			recvDO.setDestOfficeType(dispatchTO.getDestOfficeType());

			OfficeDO destOffc;
			try {
				destOffc = dispatchMDBDao.getOfficeByCode(dispatchTO.getDestOfficeCode());
				recvDO.setDestOfficeDO(destOffc);
			} catch (Exception e) {
				logger.error("DispatchMDBServiceImpl::saveReceiver::destOffc ===> Error loading destOffc");
			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>3");
			ModeDO mode =null;
			try {
				mode = dispatchMDBDao.getMode(dispatchTO.getModeCode());
				recvDO.setModeDO(mode);
			} catch (Exception e) {
				logger.error("DispatchMDBServiceImpl::saveReceiver::modeDO ===> Error loading mode");
			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>4");

			if(dispatchTO.getBillingMode()!=null && !dispatchTO.getBillingMode().trim().equals("")){
				BillingDO billingDO=dispatchMDBDao.getBillingMode(dispatchTO.getBillingMode());
				logger.trace("DispatchMDBServiceImpl::saveReceiver::billingDO.getBillingId() : "+billingDO.getBillingId());
				recvDO.setBillingDO(billingDO);
			}

			recvDO.setServiceType(dispatchTO.getServiceType());
			if(dispatchTO.getServiceType().equals("C")){
				////get CO-LOADER DO
				CoLoaderDO coloader=dispatchMDBDao.getColoaderByCode(dispatchTO.getServiceCode());
				if(coloader!=null){
					recvDO.setColoaderDO(coloader);	
				}
				logger.trace("DispatchMDBServiceImpl::saveReceiver::CoLoaderDO : "+coloader);
				logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>5");
			}else if(dispatchTO.getServiceType().equals("D")){
				//get VENDOR DO
				VendorDO vendor=dispatchMDBDao.getVendorByCode(dispatchTO.getServiceCode());
				if(vendor!=null){
					recvDO.setVendorDO(vendor);	
				}

				logger.trace("DispatchMDBServiceImpl::saveReceiver::VendorDO : "+vendor);
			}else if(dispatchTO.getServiceType().equals("O")){
				//get EMP DO
				EmployeeDO emp=dispatchMDBDao.getEmployeeByCode(dispatchTO.getServiceCode());
				if(emp!=null){
					recvDO.setDirectEmpDO(emp);	
				}

				logger.trace("DispatchMDBServiceImpl::saveReceiver::EmployeeDO : "+emp);
			}
			if(dispatchTO.getUserCode()!=null && !dispatchTO.getUserCode().equals("")){
				UserDO userDO=dispatchMDBDao.getUserByUserCode(dispatchTO.getUserCode());
				if(userDO!=null) {
					recvDO.setUserDo(userDO);
				}
				logger.trace("DispatchMDBServiceImpl::saveReceiver::userDO : "+userDO);

			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::START======>6");
			if(mode !=null && mode.getModeName().equalsIgnoreCase(SURFACE) || mode.getModeName().equalsIgnoreCase(ROAD)){			
				VehicleDO vehicle=dispatchMDBDao.getVehicle(dispatchTO.getVehicleRegNo());		
				if(vehicle!=null){
					recvDO.setVehicleDO(vehicle);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::VehicleDO : "+vehicle);
				}else{
					//INSERT IN TEMP COLUMNS 
					recvDO.setTempVehicalCode(dispatchTO.getVehicleRegNo());
				}
			}else if(mode !=null &&mode.getModeName().equalsIgnoreCase(AIR)){
				AirportDO airportDO=dispatchMDBDao.getAirportByAirlineNumber(dispatchTO.getVehicleRegNo());
				if(airportDO!=null){
					recvDO.setAirportDO(airportDO);			
					logger.trace("DispatchMDBServiceImpl::saveReceiver::airportDO : "+airportDO);
				}			
			}else if(mode !=null &&mode.getModeName().equalsIgnoreCase(TRAIN)){
				TrainDO trainDO=dispatchMDBDao.getTrainByTrainNumber(dispatchTO.getVehicleRegNo());
				if(trainDO!=null){
					recvDO.setTrainDO(trainDO);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::trainDO : "+trainDO);
				}
			}		

			////////////
			if(dispatchTO.getArrivalTime()!=null){
				recvDO.setArrivalTime(dispatchTO.getArrivalTime());			
			}

			try {
				EmployeeDO emp=dispatchMDBDao.getEmployeeByCode(dispatchTO.getEmpCode());
				recvDO.setReceiverEmpDO(emp);
			} catch (Exception e) {
				logger.error("DispatchMDBServiceImpl::saveReceiver::EmployeeDO ===> Error loading emp");
			}


			recvDO.setLoadRecvDate(new Date());
			recvDO.setLoadRecvTime(dispatchTO.getLoadRecvTime());
			recvDO.setCdLtRrNum(dispatchTO.getCdLtRrNum());
			Double totalWtt=dispatchTO.getTotalWt();
			if(totalWtt!=null) {
				recvDO.setTotalWt(new Float(totalWtt));
			}

			recvDO.setRecvRemark(dispatchTO.getRecvRemarks());

			if(dispatchTO.getArrivalDate()!=null) {
				recvDO.setArrivalDate(DateFormatterUtil.getDateFromString(dispatchTO.getArrivalDate(),DD_MM_YYYY));
			}
			logger.trace("DispatchMDBServiceImpl::saveReceiver::ArrivalDate : "+dispatchTO.getArrivalDate());
			if(dispatchTO.isDummyCD()){
				recvDO.setDummyCd(YES);			
			}else{
				recvDO.setDummyCd(NO);
			}
			recvDO.setDiFlag("N");
			recvDO.setDbServer("N");
			logger.trace("DispatchMDBServiceImpl::saveReceiver::mode.getModeId() : "+mode.getModeId());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getArrivalTime());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getAirportDO : "+recvDO.getAirportDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getArrivalDate());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getBillingDO() : "+recvDO.getBillingDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getCdLtRrNum() : "+recvDO.getCdLtRrNum());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getDbServer() : "+recvDO.getDbServer());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getDestOfficeType() : "+recvDO.getDestOfficeType());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getDispatchNumber() : "+recvDO.getDispatchNumber());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getDummyCd() : "+recvDO.getDummyCd());

			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getLoadRecvTime() : "+recvDO.getLoadRecvTime());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getLogTime() : "+recvDO.getLogTime());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getNodeId() : "+recvDO.getNodeId());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getOrgOfficeType() : "+recvDO.getOrgOfficeType());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getReadByLocal());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getRecvRemark());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getServiceType());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getArrivalTime() : "+recvDO.getTempVehicalCode());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::recvDO.getLoadRecvTime() : "+recvDO.getLoadRecvTime());

			logger.trace("DispatchMDBServiceImpl::saveReceiver::getLogTime() : "+recvDO.getLogTime());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getChildEntityId() : "+recvDO.getChildEntityId());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getRecvDispatchId() : "+recvDO.getRecvDispatchId());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getTotalWt() : "+recvDO.getTotalWt());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getColoaderDO() : "+recvDO.getColoaderDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getDestOfficeDO() : "+recvDO.getDestOfficeDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getDirectEmpDO() : "+recvDO.getDirectEmpDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getDispatchDO() : "+recvDO.getDispatchDO());


			logger.trace("DispatchMDBServiceImpl::saveReceiver::getLastTransModifiedDate() : "+recvDO.getLastTransModifiedDate());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getLoadRecvDate() : "+recvDO.getLoadRecvDate());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getLogDate() : "+recvDO.getLogDate());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getModeDO() : "+recvDO.getModeDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getOriginOfficeDO() : "+recvDO.getOriginOfficeDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getReceiverEmpDO() : "+recvDO.getReceiverEmpDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getTrainDO() : "+recvDO.getTrainDO());

			logger.trace("DispatchMDBServiceImpl::saveReceiver::getTransCreateDate() : "+recvDO.getTransCreateDate());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getUserDo() : "+recvDO.getUserDo());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getVehicleDO() : "+recvDO.getVehicleDO());
			logger.trace("DispatchMDBServiceImpl::saveReceiver::getVendorDO() : "+recvDO.getVendorDO());

			DispatchReceiverDO savedDO=dispatchMDBDao.saveReceiver(recvDO);

			//SAVE THE GRID-CHILD DATA
			String[] manifestNum=dispatchTO.getManifestNum();
			double[] weight=dispatchTO.getWeight();
			String[] stickerNum=dispatchTO.getStickerNum();
			String[] depsCat=dispatchTO.getDepsCat();
			String[] recvRemark=dispatchTO.getRemark();

			List<DispatchReceiveBagManifestDO> childList=new ArrayList<DispatchReceiveBagManifestDO>();
			for(int i=0;i<manifestNum.length;i++){
				logger.trace("DispatchMDBServiceImpl::saveReceiver::manifestNum.length : "+manifestNum.length);
				DispatchReceiveBagManifestDO dispMnDO=new DispatchReceiveBagManifestDO();
				//call ManifestDO from manifestNumber to validate it
				//ManifestDO manifest=dispatchMDBDao.getManifestByNum(manifestNum[i]);
				if(manifestNum[i]!=null && !manifestNum[i].trim().equals("")){
					logger.trace("DispatchMDBServiceImpl::saveReceiver::manifestNum : "+manifestNum[i]);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::weight : "+weight[i]);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::stickerNum : "+stickerNum[i]);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::depsCat : "+depsCat[i]);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::recvRemark : "+recvRemark[i]);

					dispMnDO.setBagManifestNum(manifestNum[i]);
					dispMnDO.setPhysicalWt(weight[i]);
					dispMnDO.setStickerNum(stickerNum[i]);
					dispMnDO.setDepsCategory(depsCat[i]);
					dispMnDO.setRemark(recvRemark[i]);
					dispMnDO.setDispatchRecvDO(savedDO);
					dispMnDO.setDbServer("N");
					dispMnDO.setDispatchRecvBagMnfstId(null);
					childList.add(dispMnDO);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::dispMnDO : "+dispMnDO);
					logger.trace("DispatchMDBServiceImpl::saveReceiver::childList : "+childList);
				}else{
					logger.trace("DispatchMDBServiceImpl::saveReceiver::No manifest child details : ");
				}
			}

			//first delete then save
			//dispatchMDBDao.deleteDispRecvManifestByParentId(savedDO.getRecvDispatchId());
			logger.trace("DispatchMDBServiceImpl::saveUpdateDispRecvManifestList::START======>");
			dispatchMDBDao.saveUpdateDispRecvManifestList(childList);
			logger.trace("DispatchMDBServiceImpl::saveUpdateDispRecvManifestList::END======>");
		} catch (Exception e) {
			logger.error("DispatchMDBServiceImpl::saveReceiver::" + e.getMessage());
			logger.error("DispatchMDBServiceImpl::saveReceiver::" + e.getStackTrace());
		}
		logger.debug("DispatchMDBServiceImpl::saveReceiver::END======>");
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#saveDispatch(CGBaseTO)
	 */
	@Override
	public void saveDispatch(CGBaseTO cgDispatchTO) throws CGSystemException {
		List<DispatchTO> dispatchTOList =(List<DispatchTO>) cgDispatchTO.getBaseList();
		for(DispatchTO dispatchTO :dispatchTOList){
			saveDispatch(dispatchTO);
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dispatch.DispatchMDBService#saveDispatch(DispatchTO)
	 */
	@Override
	public void saveDispatch(DispatchTO dispatchTO) throws CGSystemException{
		logger.debug("DispatchMDBServiceImpl::saveDispatch::START======>");
		try{

			DispatchDO dispatchDO=new DispatchDO();
			dispatchDO.setDispatchId(null);
			dispatchDO.setOrgOfficeType(dispatchTO.getOrgOfficeType());
			OfficeDO orgOffc=dispatchMDBDao.getOfficeByCode(dispatchTO.getOrgOfficeCode());
			dispatchDO.setOriginOfficeDO(orgOffc);
			logger.trace("DispatchMDBServiceImpl::saveDispatch::orgOffc.getOfficeId() : "+orgOffc.getOfficeId());
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>1");
			dispatchDO.setDestOfficeType(dispatchTO.getDestOfficeType());
			OfficeDO destOffc=dispatchMDBDao.getOfficeByCode(dispatchTO.getDestOfficeCode());
			dispatchDO.setDestOfficeDO(destOffc);
			ModeDO mode=dispatchMDBDao.getMode(dispatchTO.getModeCode());
			dispatchDO.setModeDO(mode);
			BillingDO billingDO=null;
			if(dispatchTO.getBillingMode()!=null && !dispatchTO.getBillingMode().trim().equals("")){
				billingDO=dispatchMDBDao.getBillingMode(dispatchTO.getBillingMode());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::billingDO====>"+billingDO);
				dispatchDO.setBillingDO(billingDO);
				if(billingDO!=null &&billingDO.getBillingCode().equalsIgnoreCase(OBC)){
					dispatchDO.setObcCode(dispatchTO.getObcCode());
					dispatchDO.setPnrNumber(dispatchTO.getPnrNumber());
					dispatchDO.setTcktCost(dispatchTO.getTicketCost());
				}
			}
			dispatchDO.setServiceType(dispatchTO.getServiceType());
			CoLoaderDO coloader=null;
			VendorDO vendor=null;
			EmployeeDO emp=null;
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>2");
			if(dispatchTO.getServiceType().equals("C")){
				////get CO-LOADER DO
				coloader=dispatchMDBDao.getColoaderByCode(dispatchTO.getServiceCode());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::coloader====>"+coloader);
				dispatchDO.setColoaderDO(coloader);
			}else if(dispatchTO.getServiceType().equals("D")){
				//get VENDOR DO
				vendor=dispatchMDBDao.getVendorByCode(dispatchTO.getServiceCode());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::vendor====>"+vendor);
				dispatchDO.setVendorDO(vendor);
			}else if(dispatchTO.getServiceType().equals("O")){
				//get OTCMaster DO
				OTCMasterDO otcMasterDO=dispatchMDBDao.getOTCMasterByOtcCode(dispatchTO.getServiceCode());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::otcMasterDO====>"+otcMasterDO);
				dispatchDO.setOtcDO(otcMasterDO);
				if(otcMasterDO.getEmployeeDO()!=null){
					//set EMP DO
					emp=otcMasterDO.getEmployeeDO();
					logger.trace("DispatchMDBServiceImpl::saveDispatch::emp====>"+emp);

					dispatchDO.setDirectEmpDO(emp);
				}			
			}
			if(dispatchTO.getUserCode()!=null && !dispatchTO.getUserCode().equals("")){
				UserDO userDO=dispatchMDBDao.getUserByUserCode(dispatchTO.getUserCode());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::userDO====>"+userDO);
				if(userDO!=null) {
					dispatchDO.setUserDo(userDO);
				}
			}

			VehicleDO vehicle=null;
			AirportDO airport=null;
			TrainDO train=null;
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>3");
			if(mode!=null){
				if(mode.getModeName().equalsIgnoreCase(SURFACE) || mode.getModeName().equalsIgnoreCase(ROAD)){			
					vehicle=dispatchMDBDao.getVehicle(dispatchTO.getVehicleRegNo());
					logger.trace("DispatchMDBServiceImpl::saveDispatch::vehicle====>"+vehicle);
					logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>1");
					if(vehicle!=null){
						dispatchDO.setVehicalDO(vehicle);
					}else{
						//INSERT IN TEMP COLUMNS 
						dispatchDO.setTempVehicalCode(dispatchTO.getVehicleRegNo());
						dispatchDO.setTempVehicalName(dispatchTO.getVehicleName());
						dispatchDO.setTempExpArrivalDateTime(dispatchTO.getExpArrivalDateTime());
						dispatchDO.setTempExpDepDateTime(dispatchTO.getExpDepDateTime());
						logger.trace("DispatchMDBServiceImpl::saveDispatch::vehicle else part====>");
					}
				}else if(mode.getModeName().equalsIgnoreCase(AIR)){
					airport=dispatchMDBDao.getAirportByAirlineNumber(dispatchTO.getVehicleRegNo());
					logger.trace("DispatchMDBServiceImpl::saveDispatch::airport"+airport);

					if(airport!=null){
						dispatchDO.setAirportDO(airport);

					}			
				}else if(mode.getModeName().equalsIgnoreCase(TRAIN)){
					train=dispatchMDBDao.getTrainByTrainNumber(dispatchTO.getVehicleRegNo());
					logger.trace("DispatchMDBServiceImpl::saveDispatch::train"+train);
					if(train!=null){
						dispatchDO.setTrainDO(train);					
					}
				}		
			}
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>4");
			if(dispatchTO.getCdLtRrNum()!=null){
				dispatchDO.setCdLtRrNum(dispatchTO.getCdLtRrNum());
				dispatchDO.setCdDate(DateFormatterUtil.getDateFromStringDDMMYYY(dispatchTO.getCdCreatedDate()));
				dispatchDO.setCdTime(dispatchTO.getCdCreatedTime());
				logger.trace("DispatchMDBServiceImpl::saveDispatch::dispatchTO.getCdLtRrNum()"+dispatchTO.getCdLtRrNum());

			}
			EmployeeDO emp1=dispatchMDBDao.getEmployeeByCode(dispatchTO.getEmpCode());
			logger.trace("DispatchMDBServiceImpl::saveDispatch::emp1()"+emp1);

			dispatchDO.setLoadSentEmpDO(emp1);
			dispatchDO.setLoadSentDate(new Date());
			dispatchDO.setLoadSentTime(dispatchTO.getLoadSentTime());
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>5");

			dispatchDO.setChargedWt(dispatchTO.getChargedWt());
			dispatchDO.setTotalWt(dispatchTO.getTotalWt());		
			dispatchDO.setDispatchNumber(dispatchTO.getDispatchNumber());

			if(dispatchTO.getRemarks()!=null) {
				dispatchDO.setRemarks(dispatchTO.getRemarks());
			}

			//SAVE THE DATA
			dispatchDO.setDbServer("N");
			dispatchDO.setReadByLocal("N");
			dispatchDO.setDiFlag("N");
			DispatchDO savedDO=dispatchMDBDao.saveDispatch(dispatchDO);
			logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>6");
			//SAVE THE GRID-CHILD DATA
			String[] manifestNum=dispatchTO.getManifestNum();
			double[] weight=dispatchTO.getWeight();
			String[] stickerNum=dispatchTO.getStickerNum();
			String[] remarks=dispatchTO.getRemark();
			List<DispatchBagManifestDO> childList=new ArrayList<DispatchBagManifestDO>();
			for(int i=0;i<manifestNum.length;i++){
				//call ManifestDO from manifestNumber to validate it
				if(manifestNum[i]!=null && !manifestNum[i].trim().equals("")){
					DispatchBagManifestDO dispMnDO=new DispatchBagManifestDO();
					ManifestDO manifest=dispatchMDBDao.getManifestByNum(manifestNum[i].toUpperCase());
					if(manifest!=null){
						logger.trace("DispatchMDBServiceImpl::saveDispatch::manifest not  null"+manifest);

						dispMnDO.setBagManifestNum(manifestNum[i].toUpperCase());
						logger.trace("DispatchMDBServiceImpl::saveDispatch::manifestNum[i]"+manifestNum[i]);
						dispMnDO.setDispatchDO(savedDO);
						logger.trace("DispatchMDBServiceImpl::saveDispatch::savedDO"+savedDO);
						dispMnDO.setPhysicalWt(weight[i]);
						logger.trace("DispatchMDBServiceImpl::saveDispatch::weight[i]"+weight[i]);
						dispMnDO.setStickerNum(stickerNum[i]);
						logger.trace("DispatchMDBServiceImpl::saveDispatch::stickerNum[i]"+stickerNum[i]);
						dispMnDO.setRemark(remarks[i]);
						logger.trace("DispatchMDBServiceImpl::saveDispatch:remarks[i]"+remarks[i]);
						dispMnDO.setCreatedDate(new Date());
						logger.trace("DispatchMDBServiceImpl::saveDispatch::manifest not  null"+manifest);
						//also add header detail @ child level
						dispMnDO.setModeDO(mode);
						logger.trace("DispatchMDBServiceImpl::saveDispatch::mode"+mode);
						//SURFACE 
						if(mode.getModeName().equalsIgnoreCase(SURFACE) || mode.getModeName().equalsIgnoreCase(ROAD)){
							if(vehicle!=null){
								logger.trace("DispatchMDBServiceImpl::saveDispatch::vehicles "+vehicle);

								dispMnDO.setVehicalDO(vehicle);
							}else{//INSERT IN TEMP COLUMNS
								dispMnDO.setTempVehicalCode(dispatchTO.getVehicleRegNo());
								dispMnDO.setTempVehicalName(dispatchTO.getVehicleName());
								dispMnDO.setTempExpArrivalDateTime(dispatchTO.getExpArrivalDateTime());
								dispMnDO.setTempExpDepDateTime(dispatchTO.getExpDepDateTime());
							}
						}else if(mode.getModeName().equalsIgnoreCase(AIR)){
							if(airport!=null){
								dispMnDO.setAirportDO(airport);
							}
						}else if(mode.getModeName().equalsIgnoreCase(TRAIN)){
							if(train!=null){
								dispMnDO.setTrainDO(train);
							}
						}

						if(dispatchTO.getCdLtRrNum()!=null){
							logger.trace("DispatchMDBServiceImpl::saveDispatch::dispatchTO.getCdLtRrNum() "+dispatchTO.getCdLtRrNum());

							dispMnDO.setCdLtRrNum(dispatchTO.getCdLtRrNum());
							dispMnDO.setCdDate(DateFormatterUtil.getDateFromStringDDMMYYY(dispatchTO.getCdCreatedDate()));
							dispMnDO.setCdTime(dispatchTO.getCdCreatedTime());
						}
						dispMnDO.setServiceType(dispatchTO.getServiceType());

						if(dispatchTO.getServiceType().equals("C")){
							////get CO-LOADER DO
							dispMnDO.setColoaderDO(coloader);
						}else if(dispatchTO.getServiceType().equals("O")){
							//get EMP DO
							dispMnDO.setDirectEmpDO(emp);
						}else if(dispatchTO.getServiceType().equals("D")){
							//get VENDOR DO
							dispMnDO.setVendorDO(vendor);
						}

						if(billingDO!=null) {
							dispMnDO.setBillingDO(billingDO);
						}

						dispMnDO.setDbServer("N");
						logger.trace("DispatchMDBServiceImpl::saveDispatch::=====>7");

						childList.add(dispMnDO);
					}
				}
			}

			//SAVE CHILD DATA IN DISP_MANIFEST_DETAIL
			dispatchMDBDao.saveUpdateDispManifestList(childList);



		} catch (Exception e) {
			logger.error("DispatchMDBServiceImpl::saveDispatch::" + e.getMessage());
			logger.error("DispatchMDBServiceImpl::saveDispatch::" + e.getCause().getMessage());
		}
		logger.debug("DispatchMDBServiceImpl::saveDispatch::END======>");

	}

	/**
	 * Current time.
	 *
	 * @return the time
	 */
	private Time currentTime() {
		Calendar calendar = new GregorianCalendar();
		long time=calendar.getTimeInMillis();
		return new Time(time);
	}

}
