/**
 * 
 */
package src.com.dtdc.mdbDao.heldup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import src.com.dtdc.constants.HelpdUpReleaseConstants;
import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.BookingDO;
import com.dtdc.domain.dispatch.DispatchDO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;
import com.dtdc.domain.manifest.VehicleMnfstDO;
import com.dtdc.domain.manifest.VehicleMnfstLoadDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseDO;
import com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO;



// TODO: Auto-generated Javadoc
/**
 * The Class HeldUpReleaseMDBDAOImpl.
 *
 * @author mohammes
 */
public class HeldUpReleaseMDBDAOImpl extends HibernateDaoSupport implements
HeldUpReleaseMDBDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(HeldUpReleaseMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#saveHeldUp(HeldUpReleaseDO)
	 */
	@Override
	public Boolean saveHeldUp(HeldUpReleaseDO heldUpReleaseDO) throws CGBusinessException,CGSystemException {
		Boolean result=false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.saveOrUpdate(heldUpReleaseDO);
		result=true;
		hibernateTemplate.flush();
		return result;
	}


	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#delete(List)
	 */
	@Override
	public void delete(List<HeldUpReleaseDO> releaseDoList) throws CGBusinessException {
		for(HeldUpReleaseDO helupDo :releaseDoList){
			deleteDetails(helupDo.getItemDetailsSet());
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#find(String)
	 */
	@Override
	public HeldUpReleaseDO find(String heldupNo) throws CGBusinessException {
		List<HeldUpReleaseDO> result=null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam
		("getHeldUpReleaseDetailsByHeldUpNo","heldupNo", heldupNo.trim());
		if (result.size() > 0){
			result.size();
			return result.get(0);
		} else {
			return null;
		}			

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getReleaseReasons(String)
	 */
	@Override
	public List<ReasonDO> getReleaseReasons(String reasonType) throws CGBusinessException {
		List<ReasonDO> list = getHibernateTemplate().findByNamedQueryAndNamedParam
		("getReleaseReasons","reasonType", reasonType);
		return list;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#saveReleaseDetails(HeldUpReleaseDO, List)
	 */
	@Override
	public Boolean saveReleaseDetails(HeldUpReleaseDO heldUpReleaseDo, List<MiscExpenseDO> miscListDoList) throws CGBusinessException,CGSystemException {
		Boolean result=false;
		Session session=null;
		Transaction tx=null;
		try {
			Set<HeldUpReleaseItemsDtlsDO> itemDetailslist = heldUpReleaseDo.getItemDetailsSet();
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			for(HeldUpReleaseItemsDtlsDO heldUpReleaseItemsDtlsDo:itemDetailslist){
				String qry = prepareUpdateQuery(heldUpReleaseItemsDtlsDo);
				Query query = session.createQuery(qry);
				query.setDate("releaseDate", heldUpReleaseItemsDtlsDo.getReleaseDate());
				query.setDate("transLastModifiedDate",Calendar.getInstance().getTime());
				query.executeUpdate();
				//*********************************************Start

				String manifestCode = heldUpReleaseItemsDtlsDo.getMnfstTypeId().getMnfstCode();
				if(manifestCode != null && !manifestCode.equals("")){
					if((manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_NONDOX))||
							(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_DOX))||
							(manifestCode.equals(HelpdUpReleaseConstants.MASTER_BAG_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.PACKET_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.ROBO_CHECKLIST))||
							(manifestCode.equals(HelpdUpReleaseConstants.POD))||
							(manifestCode.equals(HelpdUpReleaseConstants.MNP_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.RE_ROUT_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.PAPER_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.SELF_SECTOR_MANIFEST))||
							(manifestCode.equals(HelpdUpReleaseConstants.AGENT_MANIFEST_INTL))){

						List<ManifestDO> manifestsDOList =getMasterbagChildList(heldUpReleaseItemsDtlsDo.getManifestNumber());
						if (!manifestsDOList.isEmpty()) {
							updateManifestDOListForRelease(manifestsDOList, heldUpReleaseDo);//Update the Manifest Status and HeldUpReleaseID 
							updateManifest(manifestsDOList);//Update the Manifest table
						}
					}else if(manifestCode.equals(HelpdUpReleaseConstants.VEHICLE_MANIFEST)){
						List<String> manifestNoList = updateVehicleManifest(Integer.valueOf(heldUpReleaseItemsDtlsDo.getManifestNumber()),heldUpReleaseDo);
						int counter = 0; 
						while(manifestNoList.size() > counter){
							List<ManifestDO> manifestsDOList = getMasterbagChildList(manifestNoList.get(counter));
							if (!manifestsDOList.isEmpty()) {
								updateManifestDOListForRelease(manifestsDOList, heldUpReleaseDo);//Update the Manifest Status and HeldUpReleaseID 
								updateManifest(manifestsDOList);//Update the Manifest table
							}
							counter++;
						}
					}else if(manifestCode.equals(HelpdUpReleaseConstants.FRANCHISEE_DELIVERY_MANIFEST)){
						updateFRDeliveryManifest(heldUpReleaseItemsDtlsDo.getManifestNumber(),heldUpReleaseDo, HelpdUpReleaseConstants.RELEASE_STATUS_FOR_FDM);//Update the delivery table
					}else if(manifestCode.equals(HelpdUpReleaseConstants.BRANCH_DELIVERY_MANIFEST)){
						updateBRDeliveryManifest(heldUpReleaseItemsDtlsDo.getManifestNumber(),heldUpReleaseDo, HelpdUpReleaseConstants.RELEASE_STATUS_FOR_FDM);//Update the delivery table
					}else if((manifestCode.equals(HelpdUpReleaseConstants.CONSIGNMENT))){
						updateBookingConsignment(heldUpReleaseItemsDtlsDo.getManifestNumber(),heldUpReleaseDo, HelpdUpReleaseConstants.RELEASE_STATUS_FOR_CONSIGNMENT);//Update the booking table
					}else if(manifestCode.equals(HelpdUpReleaseConstants.COLOADER_MANIFEST)){
						updateCDDispatch(heldUpReleaseItemsDtlsDo.getManifestNumber(),heldUpReleaseDo, HelpdUpReleaseConstants.RELEASE_STATUS_FOR_FDM);
					}
					/*else if(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_DOX)||(manifestCode.equals(HelpdUpReleaseConstants.BAG_MANIFEST_NONDOX))){
						List<ManifestDO> manifestsDOList =getMasterbagChildListForDoxNonDox(heldUpReleaseItemsDtlsDo.getManifestNumber(),manifestCode);
						if (!manifestsDOList.isEmpty()) {
							updateManifestDOListForRelease(manifestsDOList, heldUpReleaseDo);//Update the Manifest Status and HeldUpReleaseID 
							updateManifest(manifestsDOList);//Update the Manifest table
						}
					}*/	


				}
				//**********************************************End



			}
			if(miscListDoList != null && !miscListDoList.isEmpty()) {
				saveMiscExpForRelease(miscListDoList);
			}

			result=true;

		} finally{
			tx.commit();
			session.close();
		}
		return result;
	}

	/**
	 * Update manifest do list for release.
	 *
	 * @param manifestDOList the manifest do list
	 * @param heldupDO the heldup do
	 */
	private void updateManifestDOListForRelease(List<ManifestDO> manifestDOList, HeldUpReleaseDO heldupDO){
		int counter = 0;
		while(manifestDOList.size() > counter){
			manifestDOList.get(counter).setStatus(HelpdUpReleaseConstants.MANIFEST_STATUS);
			manifestDOList.get(counter).setHeldupDO(heldupDO);
			counter++;
		}
	}


	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getManifestTypeDO()
	 */
	@Override
	public List<ManifestTypeDO> getManifestTypeDO() {


		getHibernateTemplate();

		List<ManifestTypeDO> manifestTypeDo = getHibernateTemplate().findByNamedQuery("getManifestTypeNames");

		return manifestTypeDo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getReasonName(Integer)
	 */
	@Override
	public String getReasonName(Integer reasonId) {

		String reasonName = "";


		/*HibernateTemplate hibernateTemplate = getHibernateTemplate();*/
		Session session = null;
		/*List<ReasonDO> reasonDoList = getHibernateTemplate().findByNamedQueryAndNamedParam("getReasonName", "reasonCode", reasonCd);
		reasonName = reasonDoList.get(0).getReasonName();
		 */
		try {
			ReasonDO reasonDO=null;
			if(reasonId!=null){
				session =getHibernateTemplate().getSessionFactory().openSession();
				Criteria criteria = session.createCriteria(ReasonDO.class);
				criteria.add(Restrictions.eq("reasonId", reasonId));
				reasonDO=(ReasonDO)criteria.uniqueResult();
			}
			if(reasonDO!=null) {
				reasonName=reasonDO.getReasonName();
			}
		} finally{
			session.close();
		}

		return reasonName;

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getManifestTypeDO(String)
	 */
	@Override
	public String getManifestTypeDO(String mnfstTypeId) {

		String mnfstTypeName = "";


		getHibernateTemplate();

		List<ManifestTypeDO> manfstTypeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam("getManifestTypeName", "manifestTypeId", mnfstTypeId);
		mnfstTypeName = manfstTypeDOList.get(0).getMnfstName();

		return mnfstTypeName;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#edit(List)
	 */
	@Override
	public Boolean edit(List<HeldUpReleaseItemsDtlsDO> itemDetailslist) throws CGBusinessException {
		Boolean result=false;
		getHibernateTemplate();
		Session session=null;
		Transaction tx=null;
		try{
			session =  getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			for (HeldUpReleaseItemsDtlsDO itemDo:itemDetailslist)
			{
				/*hibernateTemplate.update(itemDo);
			result=true;*/
				Query query = session.getNamedQuery("updateHeldupReleaseDtls");
				query.setInteger("reasonId",itemDo.getHeldupReasonId().getReasonId());
				query.setString("rack",itemDo.getRack());
				query.setString("position",itemDo.getPosition());
				query.setString("heldUpRemarks",itemDo.getHeldUpRemarks());
				query.setInteger("heldDtlsId",itemDo.getHeldupReleaseItemsDtlsId());

				if(query.executeUpdate() > 0 ){
					result=true;
				}
				tx.commit();
			}	
		}catch (Exception e) {
			logger.error("HeldUpReleaseMDBDAOImpl::edit::Exception occured:"
					+e.getMessage());
			tx.rollback();
			throw new CGBusinessException(e);
		} 
		finally{

			session.close();
		}
		//session.close();
		return result;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getReasonId(String)
	 */
	@Override
	public int getReasonId(String reasonCd) {

		getHibernateTemplate();

		List<ReasonDO> reasonDoList = getHibernateTemplate().findByNamedQueryAndNamedParam("getReasonName", "reasonCode", reasonCd);
		int reasonId  = reasonDoList.get(0).getReasonId();

		return reasonId;

	}



	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#validateManifestDetails(String, Integer)
	 */
	@Override
	public boolean validateManifestDetails(String mnfstNumber,
			Integer mnfstTypeId) throws CGBusinessException {

		List<ManifestDO> manfstDOList = null;

		boolean isValid = false;

		getHibernateTemplate();


		String[] params={"mnfstTypeId", "manifestNumber"};
		Object[] values = {mnfstTypeId, mnfstNumber};
		manfstDOList = getHibernateTemplate().findByNamedQueryAndNamedParam("getManifestByNoAndType", params, values);
		if (manfstDOList.size() > 0)
		{
			isValid = true;
		}

		LOGGER.info("validateManifestDetails ...... ");

		return isValid;
	}	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#validateManifestNoDetails(String, Integer)
	 */
	@Override
	public boolean validateManifestNoDetails(String mnfstNumber,
			Integer mnfstTypeId) throws CGBusinessException,CGSystemException {
		mnfstNumber = mnfstNumber.trim();
		List<ManifestDO> manfstDOList = null;

		List<VehicleMnfstLoadDO> vehicleMnfstLoadDOList = null;
		boolean isValid = false;
		List<DeliveryDO> deliveryDOList = null;
		getHibernateTemplate();
		List<DispatchDO> dispatchDOList = null;
		DispatchDO dispatchDO_Obj = null;
		ManifestTypeDO manifestTypeDO= getManifestTypeObject( mnfstTypeId);
		if((manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.BAG_MANIFEST_NONDOX))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.BAG_MANIFEST_DOX))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.MASTER_BAG_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.PACKET_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.ROBO_CHECKLIST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.POD))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.MNP_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.RE_ROUT_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.PAPER_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.SELF_SECTOR_MANIFEST))||
				(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.AGENT_MANIFEST_INTL))){

			String[] params={"manifestNumber","status","mnfstTypeId"};
			Object[] values = {mnfstNumber,HelpdUpReleaseConstants.MANIFEST_STATUS ,mnfstTypeId };
			manfstDOList = getHibernateTemplate().findByNamedQueryAndNamedParam("getManifestByNoAndType", params, values);
			if (!StringUtil.isEmptyList(manfstDOList))
			{
				isValid = true;
			}
		} else if(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.CONSIGNMENT)) {
			String queryForConsignmentBooked = "checkIfConsignmentIsBooked";
			List<Integer> result = null;
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryForConsignmentBooked, BookingConstants.CONSG_NUM,
					mnfstNumber);
			isValid = StringUtil.isEmptyList(result) ? false : true;
			if(isValid){
				queryForConsignmentBooked = "isConsignmentDeliveredForHeldUp";
				result = getHibernateTemplate().findByNamedQueryAndNamedParam(
						queryForConsignmentBooked, BookingConstants.CONSG_NUM,
						mnfstNumber);
				isValid = !StringUtil.isEmptyList(result)? false : true;
			}
		} else if(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.VEHICLE_MANIFEST)) {
			String params = "mnfstTransNo" ;
			Object values = Integer.parseInt(mnfstNumber);
			vehicleMnfstLoadDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getVehicleManifestByloadIdNumber",	params, values);

			if (!StringUtil.isEmptyList(vehicleMnfstLoadDOList))
			{
				isValid = true;
			}
		}else if(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.FRANCHISEE_DELIVERY_MANIFEST)) {
			String params = "fdmNumber" ;
			Object values = mnfstNumber;
			deliveryDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					HelpdUpReleaseConstants.GET_DELIVERY_MANIFEST_BY_FDM,	params, values);

			if (deliveryDOList.size() > 0)
			{
				isValid = true;
			}
		}else if(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.BRANCH_DELIVERY_MANIFEST)) {
			LOGGER.info("validateManifestDetails ...... Branch Delivery Manifest");
			String params = "runSheetNum" ;
			Object values = mnfstNumber;
			deliveryDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					HelpdUpReleaseConstants.GET_DELIVERY_MANIFEST_BY_BDM,	params, values);

			if (deliveryDOList.size() > 0)
			{
				isValid = true;
			}

		}else if(manifestTypeDO.getMnfstCode().equals(HelpdUpReleaseConstants.COLOADER_MANIFEST)) {
			dispatchDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
			(HelpdUpReleaseConstants.GET_DISPATCH_COLOADER_OBJECTS,
					HelpdUpReleaseConstants.DISPATCH_NUMBER, mnfstNumber);

			for(DispatchDO dispatchDO :dispatchDOList){

				dispatchDO_Obj = dispatchDO;
			}
			/*dispatchBagManifestDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
			(HelpdUpReleaseConstants.GET_DISPATCH_COLOADER_BAG_MANIFEST_OBJECTS,
					HelpdUpReleaseConstants.DISPATCH_ID, dispatchDO_Obj.getDispatchId());*/
			if(dispatchDO_Obj != null){
				List bagManifestId = getHibernateTemplate().findByNamedQueryAndNamedParam
				("validatetDispatchColoaderBagManifestObject",HelpdUpReleaseConstants.DISPATCH_ID, dispatchDO_Obj.getDispatchId());

				/*for(DispatchBagManifestDO dispatchBagManifestDO :dispatchBagManifestDOList){

				dispatchBagManifestDO_Obj = dispatchBagManifestDO;

			}			
			if (dispatchBagManifestDOList.size() > 0)
			{
				isValid = true;
			}*/
				if ( bagManifestId !=null && bagManifestId.size() > 0)
				{
					isValid = true;
				}
			}
		}

		return isValid;
	}	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getManifestTypeObject(Integer)
	 */
	@Override
	public ManifestTypeDO getManifestTypeObject(Integer mnfstTypeId)throws CGSystemException{

		ManifestTypeDO manifestTypeDO = new ManifestTypeDO();
		List<ManifestTypeDO> manifestTypeDOList= null;

		manifestTypeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		("getManifestTypeDetailsByTypeId","mnfstTypeId", mnfstTypeId);

		for(ManifestTypeDO manifestTypeDO_Obj :manifestTypeDOList ){

			manifestTypeDO =manifestTypeDO_Obj;
			LOGGER.info("getManifestTypeObject ...... getMnfstCode Type"+manifestTypeDO.getMnfstCode());
		}

		return manifestTypeDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getOfficeDetailByOfficeCode(String, String)
	 */
	@Override
	public OfficeDO getOfficeDetailByOfficeCode(String officeCode, String loggedInOfficeCode)
	throws CGSystemException {
		OfficeDO officeDO = null;

		List<OfficeDO> originOfficeList = null;
		String paramNames []= {HelpdUpReleaseConstants.OFFICE_CODE,HelpdUpReleaseConstants.LOGGED_IN_BRANCH_CODE};
		Object values []= {officeCode,loggedInOfficeCode};
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			originOfficeList = hibernateTemplate
			.findByNamedQueryAndNamedParam(
					HelpdUpReleaseConstants.GET_RO_AND_BO_OFFICES_BY_OFFICE_CODE,
					paramNames, values);
			if (originOfficeList != null && !originOfficeList.isEmpty()) {
				officeDO = originOfficeList.get(0);
			}
			hibernateTemplate.flush();
		} catch (Exception ex) {
			logger.error("HeldUpReleaseMDBDAOImpl::getOfficeDetailByOfficeCode::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  " + ex.getMessage(), ex);

		}
		return officeDO;
	}

	/** The consgn no list. */
	List<ManifestDO> consgnNoList = new ArrayList<ManifestDO>();



	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateManifest(List)
	 */
	@Override
	public void updateManifest(List<ManifestDO> manifestList)
	throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.saveOrUpdateAll(manifestList);

		hibernateTemplate.flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateVehicleManifest(Integer, HeldUpReleaseDO)
	 */
	@Override
	public List<String> updateVehicleManifest(Integer vehicleMFNo, HeldUpReleaseDO heldupdo)
	throws CGSystemException {
		List<VehicleMnfstDO> vehicleMnfstDOList = null;
		List<String> manifestNoList = new ArrayList<String>();
		vehicleMnfstDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		(ManifestConstant.VECHILE_MANIFEST_QUERY,
				ManifestConstant.TRANCATION_NUMBER, vehicleMFNo);

		//Update the vehicle manifest table by setting the heldup id
		VehicleMnfstDO vehicleMnfstDO = vehicleMnfstDOList.get(0);
		vehicleMnfstDO.setHeldUpReleaseDO(heldupdo);//setting the heldup id foreign key
		getHibernateTemplate().saveOrUpdate(vehicleMnfstDO);
		getHibernateTemplate().flush();

		//get the manifest no.s. This list will be returned
		//to the service layer. The service layer will update all the manifest no.s
		if (vehicleMnfstDO.getDtdcLVehicleMnfsts() != null){
			Set<VehicleMnfstLoadDO> dtdcLVehicleMnfsts = vehicleMnfstDO.getDtdcLVehicleMnfsts();
			for(VehicleMnfstLoadDO domain:dtdcLVehicleMnfsts){
				if(domain.getLoadIdNumber() != null) {
					manifestNoList.add(domain.getLoadIdNumber());
				}
			}
		}
		return manifestNoList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateFRDeliveryManifest(String, HeldUpReleaseDO, String)
	 */
	@Override
	public void updateFRDeliveryManifest(String deliveryManifestNo,
			HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException {
		List<com.dtdc.domain.transaction.delivery.FranchiseDeliveryManifestDO> deliveryDOList = null;

		deliveryDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		(HelpdUpReleaseConstants.GET_DELIVERY_MANIFEST_BY_FDM,
				HelpdUpReleaseConstants.FRANCHISEE_DELIVERY_MANIFEST_NUMBER, deliveryManifestNo);

		int counter = 0;
		if(deliveryDOList !=null){
			while(deliveryDOList.size() > counter){
				//setting the heldup id foreign key
				deliveryDOList.get(counter).setHeldupDO(heldupdo);
				deliveryDOList.get(counter).setDeliveryStatus(heldUpReleaseStatus);
				counter++;
			}
		}
		//Update the delivery table by setting the heldup id
		getHibernateTemplate().saveOrUpdateAll(deliveryDOList);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateBRDeliveryManifest(String, HeldUpReleaseDO, String)
	 */
	@Override
	public void updateBRDeliveryManifest(String deliveryManifestNo,
			HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException {
		List<DeliveryDO> deliveryDOList = null;

		deliveryDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		(HelpdUpReleaseConstants.GET_DELIVERY_MANIFEST_BY_BDM,
				HelpdUpReleaseConstants.BD_MANIFEST_RUN_SHEET_NUMBER, deliveryManifestNo);

		int counter = 0;
		while(deliveryDOList.size() > counter){
			//setting the heldup id foreign key
			deliveryDOList.get(counter).setHeldupDO(heldupdo);
			deliveryDOList.get(counter).setDeliveryStatus(heldUpReleaseStatus);
			counter++;
		}
		//Update the delivery table by setting the heldup id
		getHibernateTemplate().saveOrUpdateAll(deliveryDOList);
		getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateBookingConsignment(String, HeldUpReleaseDO, String)
	 */
	@Override
	public void updateBookingConsignment(String deliveryManifestNo,
			HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException {
		List<BookingDO> bookingDOList = null;

		bookingDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		(HelpdUpReleaseConstants.GET_BOOKING_DETAILS_BY_CN_QUERY,
				HelpdUpReleaseConstants.GET_BOOKING_DETAILS_BY_CN_PARAM, deliveryManifestNo);

		int counter = 0;
		while(bookingDOList.size() > counter){
			//setting the heldup id foreign key
			bookingDOList.get(counter).setHeldupDO(heldupdo);
			bookingDOList.get(counter).setConsgmntStatus(heldUpReleaseStatus);
			counter++;
		}
		//Update the booking table by setting the heldup id
		getHibernateTemplate().saveOrUpdateAll(bookingDOList);
		getHibernateTemplate().flush();

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#updateCDDispatch(String, HeldUpReleaseDO, String)
	 */
	@Override
	public void updateCDDispatch(String dispatchNo,
			HeldUpReleaseDO heldupdo, String heldUpReleaseStatus) throws CGSystemException, CGBusinessException {
		List <ManifestDO> manifestDOlist  = new ArrayList<ManifestDO>();
		List<DispatchDO> dispatchDOList = null;
		DispatchDO dispatchDO_Obj = null;
		dispatchDOList = getHibernateTemplate().findByNamedQueryAndNamedParam
		(HelpdUpReleaseConstants.GET_DISPATCH_COLOADER_OBJECTS,
				HelpdUpReleaseConstants.DISPATCH_NUMBER, dispatchNo);

		dispatchDO_Obj = dispatchDOList.get(0);
		List<String> dispatchBagManifest=getHibernateTemplate().findByNamedQueryAndNamedParam
		(HelpdUpReleaseConstants.GET_DISPATCH_COLOADER_BAG_MANIFEST_OBJECTS,
				HelpdUpReleaseConstants.DISPATCH_ID, dispatchDO_Obj.getDispatchId());
		for(String dispatchBagManifestDO :dispatchBagManifest){
			manifestDOlist.addAll(getMasterbagChildList(dispatchBagManifestDO));
		}
		//Update Manifest table for the manifest numbers
		int counter = 0;
		while(manifestDOlist.size() > counter){
			//setting the heldup id foreign key
			manifestDOlist.get(counter).setHeldupDO(heldupdo);
			manifestDOlist.get(counter).setStatus(heldUpReleaseStatus);

			//Update the vehicle manifest table by setting the heldup id and status
			getHibernateTemplate().saveOrUpdate(manifestDOlist.get(counter));
			counter++;
		}
		getHibernateTemplate().flush();		
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#deleteDetails(Set)
	 */
	@Override
	public Boolean deleteDetails(Set<HeldUpReleaseItemsDtlsDO> itemDetailslist)
	throws CGBusinessException {
		Boolean result=false;
		Query query=null;
		Session session=null;
		try {
			session=getHibernateTemplate().getSessionFactory().openSession();
			for(HeldUpReleaseItemsDtlsDO detailsToDelete:itemDetailslist){
				query = session.getNamedQuery("updateStatusForDeletedRecords");
				query.setInteger("heldChildId", detailsToDelete.getHeldupReleaseItemsDtlsId());
				query.executeUpdate();
				result=true;
			}
		} finally{
			session.close();

		}

		return result;
	}

	/**
	 * Prepare update query.
	 *
	 * @param heldUpitemDtlsDo the held upitem dtls do
	 * @return the string
	 */
	private String prepareUpdateQuery(HeldUpReleaseItemsDtlsDO heldUpitemDtlsDo) {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(" update com.dtdc.domain.transaction.heldup.HeldUpReleaseItemsDtlsDO h set");
		queryBuffer.append(" h.releaseDate= :releaseDate ");
		queryBuffer.append(" , h.heldUpStatus= 'N' ");
		queryBuffer.append(" , h.diFlag= 'N' ");
		queryBuffer.append(" , h.transLastModifiedDate = :transLastModifiedDate ");
		queryBuffer.append(" , h.releaseAction= '" +heldUpitemDtlsDo.getReleaseAction()+"'");
		if(heldUpitemDtlsDo.getReleaseAction().equalsIgnoreCase("DSTR")) {
			queryBuffer.append(" , h.customerBilledAmount=" +heldUpitemDtlsDo.getCustomerBilledAmount());
		}
		if(heldUpitemDtlsDo.getHeldUpCause().equalsIgnoreCase("AGT")) {
			queryBuffer.append(" , h.agentDestructionAmount=" +heldUpitemDtlsDo.getAgentDestructionAmount());
		}
		queryBuffer.append(" , h.releaseRemarks='" +heldUpitemDtlsDo.getReleaseRemarks()+"'");
		queryBuffer.append("  where h.heldupReleaseItemsDtlsId =" +heldUpitemDtlsDo.getHeldupReleaseItemsDtlsId());
		return queryBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#saveMiscExpForRelease(List)
	 */
	@Override
	public Boolean saveMiscExpForRelease(List<MiscExpenseDO> miscExpenseDoList)
	throws CGBusinessException {
		Boolean result=false;
		if(miscExpenseDoList !=null && !miscExpenseDoList.isEmpty()){
			getHibernateTemplate().saveOrUpdateAll(miscExpenseDoList);
			result=true;
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getMasterbagChildList(String)
	 */
	@Override
	public List<ManifestDO> getMasterbagChildList(String manifestNumber)
	throws CGBusinessException {
		List <ManifestDO> manifestDOlist  = new ArrayList<ManifestDO>();
		String mnftType="";
		for(int i=0;i<2;i++){
			if(i==0) {
				mnftType="I";
			} else {
				mnftType="O";
			}
			List<ManifestDO> manifestChildList = getChildManifest(manifestNumber,mnftType);//gives Master bag records
			manifestDOlist.addAll(manifestChildList);
			for(ManifestDO manifestDo:manifestChildList){
				List<ManifestDO> manifestChild1 = getChildManifest(manifestDo.getConsgNumber(),mnftType);//gives Bag records
				manifestDOlist.addAll(manifestChild1);
				for(ManifestDO manifestDo1:manifestChild1){
					List<ManifestDO> manifestChild2 = getChildManifest(manifestDo1.getConsgNumber(),mnftType);//gives packet records
					manifestDOlist.addAll(manifestChild2);
					for(ManifestDO manifestDo2:manifestChild2){
						List<ManifestDO> manifestChild3 = getChildManifest(manifestDo2.getConsgNumber(),mnftType);//gives consignment records
						manifestDOlist.addAll(manifestChild3);
					}//end of inner for loop
				}//end of mid for loop
			}//end of main for loop
		}
		return manifestDOlist;
	}

	/**
	 * Gets the child manifest.
	 *
	 * @param manifestNumber the manifest number
	 * @param mnfstType the mnfst type
	 * @return the child manifest
	 */
	private List<ManifestDO> getChildManifest(String manifestNumber ,String mnfstType) {
		String[] params = {"manifestNumber","mnftType"};
		//		Object[] values = { manifestDO.getManifestNumber(),
		//				manifestDO.getMnsftTypes().getMnfstCode() };
		Object[] values = {manifestNumber,mnfstType};
		List<ManifestDO> manifestsDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				"getEmbeddedHeldupManifestsForOutGoing", params,
				values);
		return manifestsDOList;
	}

	/**
	 * This method is used to update the booking table and prior to this manifest table has to be updated
	 * This method is not using now.
	 *
	 * @param manifestDo the manifest do
	 * @param HeldUpReleaseId the held up release id
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Boolean updateBookingByConsignmentNum(ManifestDO manifestDo, Integer HeldUpReleaseId)
	throws CGBusinessException {
		Boolean updated = false;
		Session session=null;
		Transaction tx=null;
		try {
			session =getSession(true);
			tx = session.beginTransaction();
			Query query=session.getNamedQuery("updateConsignmentByheldUpId");
			query.setInteger("heldUpId",HeldUpReleaseId );
			query.setString("consgNum", manifestDo.getConsgNumber());
			query.executeUpdate();
			updated=true;
		} finally{
			tx.commit();
			session.close();
		}

		return updated;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#findReleaseDetailsForPopUp(String, String)
	 */
	@Override
	public HeldUpReleaseItemsDtlsDO findReleaseDetailsForPopUp(
			String manifestNum, String manifestCode) throws CGBusinessException {
		List<HeldUpReleaseItemsDtlsDO> releaseDtlsList=null;
		HeldUpReleaseItemsDtlsDO releaseDtls=null;
		String[] params = {"manifestNumber","manifestCode"};
		Object[] values = { manifestNum,manifestCode };
		releaseDtlsList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				"getHeldUpDetailsByConsgNoAndMnfstId", params,
				values);
		if(!releaseDtlsList.isEmpty()){
			releaseDtls = releaseDtlsList.get(0);
		}

		return releaseDtls;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#getMasterbagChildListForDoxNonDox(String, String)
	 */
	@Override
	public List<ManifestDO> getMasterbagChildListForDoxNonDox(String manifestNumber,String mnfstDoxNonDoxCode)
	throws CGBusinessException {
		List <ManifestDO> manifestDOlist  = new ArrayList<ManifestDO>();
		String mnftType="";
		for(int i=0;i<2;i++){
			if(i==0) {
				mnftType="I";
			} else {
				mnftType="O";
			}
			List<ManifestDO> manifestChildList = getChildManifestForDoxNonDox(manifestNumber,mnftType,mnfstDoxNonDoxCode);//gives Master bag records
			manifestDOlist.addAll(manifestChildList);
			for(ManifestDO manifestDo:manifestChildList){
				List<ManifestDO> manifestChild1 = getChildManifestForDoxNonDox(manifestDo.getConsgNumber(),mnftType,mnfstDoxNonDoxCode);//gives Bag records
				manifestDOlist.addAll(manifestChild1);
				for(ManifestDO manifestDo1:manifestChild1){
					List<ManifestDO> manifestChild2 = getChildManifestForDoxNonDox(manifestDo1.getConsgNumber(),mnftType,mnfstDoxNonDoxCode);//gives packet records
					manifestDOlist.addAll(manifestChild2);
					for(ManifestDO manifestDo2:manifestChild2){
						List<ManifestDO> manifestChild3 = getChildManifestForDoxNonDox(manifestDo2.getConsgNumber(),mnftType,mnfstDoxNonDoxCode);//gives consignment records
						manifestDOlist.addAll(manifestChild3);
					}//end of inner for loop
				}//end of mid for loop
			}//end of main for loop
		}
		return manifestDOlist;
	}

	/**
	 * Gets the child manifest for dox non dox.
	 *
	 * @param manifestNumber the manifest number
	 * @param mnfstType the mnfst type
	 * @param mnfstDoxNonDoxCode the mnfst dox non dox code
	 * @return the child manifest for dox non dox
	 */
	private List<ManifestDO> getChildManifestForDoxNonDox(String manifestNumber ,String mnfstType,String mnfstDoxNonDoxCode) {
		String[] params = {"manifestNumber","mnftType","mnfstCode"};
		//		Object[] values = { manifestDO.getManifestNumber(),
		//				manifestDO.getMnsftTypes().getMnfstCode() };
		Object[] values = {manifestNumber,mnfstType,mnfstDoxNonDoxCode};
		List<ManifestDO> manifestsDOList = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(
				"getEmbeddedHeldupManifestsForDOXAndNonDox", params,
				values);
		return manifestsDOList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#findHeldupDtls(List)
	 */
	@Override
	public List<HeldUpReleaseDO> findHeldupDtls(List<String> heldupNums) throws CGBusinessException {
		List<HeldUpReleaseDO> result=null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam
		("getHeldUpDtlsByHeldUpNums","heldupNo", heldupNums);
		return result;	
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.heldup.HeldUpReleaseMDBDAO#saveHeldUpReleaseList(List)
	 */
	@Override
	public Boolean saveHeldUpReleaseList(List<HeldUpReleaseDO> heldUpReleaseDoList) throws CGBusinessException,CGSystemException {
		Boolean result=false;
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.saveOrUpdateAll(heldUpReleaseDoList);
		result=true;
		return result;
	}
}
