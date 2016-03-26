/**
 * 
 */
package com.ff.web.drs.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.delivery.DeliveryNavigatorDO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.util.DrsUtil;

/**
 * @author mohammes
 *
 */
public class DeliveryCommonDAOImpl extends CGBaseDAO implements
		DeliveryCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DeliveryCommonDAOImpl.class);

	
	/**
	 * getMaxNumberFromProcess : to generate DRS/YPDRS number for DRS preparation Transaction
	 * @param seqConfigTo
	 * @param queryName
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public String getMaxNumberFromProcess(final SequenceGeneratorConfigTO seqConfigTo, final String queryName) throws CGSystemException {
		List<String> numberList;
		String params[]={DrsCommonConstants.QRY_PARAM_TRANSACTION_STATUS,DrsCommonConstants.QRY_PARAM_OFFICEID,DrsCommonConstants.QRY_PARAM_PREFIX ,DrsCommonConstants.QRY_PARAM_NUMBER_LENGTH};
		Object value[] = {StockUniveralConstants.TRANSACTION_STATUS,seqConfigTo.getRequestingBranchId(),seqConfigTo.getProcessRequesting()+seqConfigTo.getRequestingBranchCode()+FrameworkConstants.CHARACTER_PERCENTILE,seqConfigTo.getLengthOfNumber()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,params, value);
		return !StringUtil.isEmptyList(numberList)?numberList.get(0):null;
	}
	
	/**
	 * discardGeneratedDrs
	 * @param deliveryDO
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public Boolean discardGeneratedDrs(DeliveryDO deliveryDO) throws CGSystemException {
		Boolean result=Boolean.FALSE;
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx =session.beginTransaction();
			discardDeliveryHeader(deliveryDO,session);
			discardDeliveryDetails(deliveryDO,session);
			tx.commit();
			result=Boolean.TRUE;
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("DeliveryCommonDAOImpl::discardGeneratedDrs ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return result;
	}
	
	/**
	 * Modify drs. : user can only Modify DRS-FOR and EMployee/DA Code (and  Opened DRS only)
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean modifyDrs(DeliveryDO deliveryDO) throws CGSystemException {
		Boolean result=Boolean.FALSE;
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx =session.beginTransaction();
			String hqlQry = prepreDeliveryHeaderQuery(deliveryDO);
			Query qry = session.createQuery(hqlQry);
			if(qry.executeUpdate()>0){
				result=Boolean.TRUE;
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("DeliveryCommonDAOImpl::modifyDrs ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return result;
	}

	/**
	 * @param deliveryDO
	 */
	private String prepreDeliveryHeaderQuery(final DeliveryDO deliveryDO) {
		 final StringBuilder qry= new StringBuilder();
		qry.append(" UPDATE com.ff.domain.delivery.DeliveryDO drs SET ");
		switch(deliveryDO.getDrsFor()){
		case DrsConstants.DRS_FOR_BA :
			qry.append(" drs.baDO.vendorId ="+deliveryDO.getBaDO().getVendorId());
			break;
		case DrsConstants.DRS_FOR_CO_COURIER :
			qry.append(" drs.coCourierDO.vendorId ="+deliveryDO.getCoCourierDO().getVendorId());
			break;
		case DrsConstants.DRS_FOR_FIELD_STAFF :
			qry.append(" drs.fieldStaffDO.employeeId ="+deliveryDO.getFieldStaffDO().getEmployeeId());
			break;
		case DrsConstants.DRS_FOR_FR :
			qry.append(" drs.franchiseDO.vendorId ="+deliveryDO.getFranchiseDO().getVendorId());
			break;
		}
		qry.append(FrameworkConstants.CHARACTER_COMMA);
		qry.append(" drs.updatedBy ="+deliveryDO.getUpdatedBy());
		qry.append(FrameworkConstants.CHARACTER_COMMA);
		qry.append(" drs.drsFor = '"+deliveryDO.getDrsFor()+FrameworkConstants.SINGLE_QUOTE);
		qry.append(FrameworkConstants.CHARACTER_COMMA);
		qry.append(" drs.dtToCentral = '"+deliveryDO.getDtToCentral()+FrameworkConstants.SINGLE_QUOTE);
		qry.append(" WHERE drs.deliveryId ="+deliveryDO.getDeliveryId());
		return qry.toString();
	}
	
	/**
	 * Discard delivery header.
	 *
	 * @param deliveryDO the delivery do
	 * @param session the session
	 * @return the int
	 */
	private int discardDeliveryHeader(final DeliveryDO deliveryDO,final Session session){
		DrsUtil.preProcessForTwoWayWrite(deliveryDO);
		Query qry = session.getNamedQuery(DrsCommonConstants.QRY_DISARD_DELIVERY_HEADER);
		qry.setLong(DrsCommonConstants.QRY_PARAM_DELIVERY_ID, deliveryDO.getDeliveryId());
		qry.setString(DrsCommonConstants.QRY_PARAM_DISCARD,UniversalDeliveryContants.DRS_DISCARDED_YES );
		qry.setString(DrsCommonConstants.QRY_PARAM_DT_TO_CENTRAL,deliveryDO.getDtToCentral());
		return qry.executeUpdate();
	}
	
/**
 * Discard delivery details.
 *
 * @param deliveryDO the delivery do
 * @param session the session
 * @return the int
 */
private int discardDeliveryDetails(DeliveryDO deliveryDO,Session session){
	Query qry = session.getNamedQuery(DrsCommonConstants.QRY_DISARD_DELIVERY_DETAILS);
	qry.setLong(DrsCommonConstants.QRY_PARAM_DELIVERY_ID, deliveryDO.getDeliveryId());
	qry.setString(UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS,UniversalDeliveryContants.RECORD_STATUS_IN_ACTIVE);
	return qry.executeUpdate();
	}

/**
 * Gets the drs details by drs number.
 *
 * @param inputTo the input to
 * @return the drs details by drs number
 * @throws CGSystemException the cG system exception
 */
@Override
public DeliveryDO getDrsDetailsByDrsNumber(AbstractDeliveryTO inputTo) throws CGSystemException {
	 List<DeliveryDO> deliveryDOList;
	String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER,DrsCommonConstants.QRY_PARAM_OFFICEID};
	Object value[] = {inputTo.getDrsNumber(),inputTo.getLoginOfficeId()};
	deliveryDOList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_DRS_DETAILS_BY_DRS_NUMBER,params, value);
	return !StringUtil.isEmptyList(deliveryDOList)?deliveryDOList.get(0):null;
}

/**
 * Gets the drs details by consg and drs number.
 *
 * @param inputTo the input to
 * @return the drs details by consg and drs number
 * @throws CGSystemException the cG system exception
 */
@Override
public DeliveryDetailsDO getDrsDetailsByConsgAndDrsNumber(AbstractDeliveryTO inputTo) throws CGSystemException {
	 List<DeliveryDetailsDO> deliveryDOList;
	String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER,UniversalDeliveryContants.QRY_PARAM_CONSG,
			UniversalDeliveryContants.QRY_PARAM_RECORD_STATUS};
	Object value[] = {inputTo.getDrsNumber(),inputTo.getConsignmentNumber(),UniversalDeliveryContants.RECORD_STATUS_ACTIVE};
	deliveryDOList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_DRS_DTLS_BY_DRS_CONSG_NUMBER,params, value);
	return !StringUtil.isEmptyList(deliveryDOList)?deliveryDOList.get(0):null;
}




/**
 * Gets the drs navigation details.
 *
 * @param drsNumber the drs number
 * @param drsCode the drs code
 * @return the drs navigation details
 * @throws CGSystemException the cG system exception
 */
@Override
public DeliveryNavigatorDO getDrsNavigationDetails(String drsNumber,String drsCode) throws CGSystemException {
	List<DeliveryNavigatorDO> navigatorDOList;
	String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER,DrsCommonConstants.QRY_PARAM_FROM_SCREEN_CODE};
	Object value[] = {drsNumber,drsCode};
	navigatorDOList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_NAVIGATION_DTLS_BY_DRS_NUMBER,params, value);
	return !StringUtil.isEmptyList(navigatorDOList)?navigatorDOList.get(0):null;
}



/**
 * Gets the drs navigation details.
 *
 * @param drsNumber the drs number
 * @param drsCode the drs code
 * @return the drs navigation details
 * @throws CGSystemException the cG system exception
 */
@Override
public List<DeliveryNavigatorDO> getDrsNavigationDetails(List<String> drsNumber,String drsCode) throws CGSystemException {
	List<DeliveryNavigatorDO> navigatorDOList=null;
	Session session=createSession();
	try {
		Query qry = session.getNamedQuery(DrsCommonConstants.QRY_NAVIGATION_DTLS_BY_DRS_NUMBER);
		qry.setString(DrsCommonConstants.QRY_PARAM_FROM_SCREEN_CODE, drsCode);
		qry.setParameterList(DrsCommonConstants.QRY_PARAM_DRS_NUMBER, drsNumber);
		navigatorDOList = qry.list();
	} catch (Exception e) {
		LOGGER.error("DeliveryCommonDAOImpl::getDrsNavigationDetails :: Exception"+e.getLocalizedMessage());
	throw new CGSystemException(e);
	}
	finally{
		closeSession(session);
	}
	
	
	return navigatorDOList;
}

/**
 * Save prepare drs.
 *
 * @param deliveryDO the delivery do
 * @return the boolean
 * @throws CGSystemException the cG system exception
 */
@Override
public Boolean savePrepareDrs(DeliveryDO deliveryDO)
		throws CGSystemException {
	DrsUtil.preProcessForTwoWayWrite(deliveryDO);
	boolean isNew=false;
	try {
		if(StringUtil.isEmptyLong(deliveryDO.getDeliveryId())){
			isNew=true;
		}
		getHibernateTemplate().saveOrUpdate(deliveryDO);
		if(isNew && !CGCollectionUtils.isEmpty(deliveryDO.getNavigatorList())){
			getHibernateTemplate().saveOrUpdateAll(deliveryDO.getNavigatorList());
		}
	} catch (Exception e) {
		LOGGER.error("DeliveryCommonDAOImpl::savePrepareDrs :: Exception",e);
		throw new CGSystemException(e);
	}
	return Boolean.TRUE;
}

@Override
public Boolean updateManualDrs(DeliveryDO deliveryDO)
		throws CGSystemException {
	DrsUtil.preProcessForTwoWayWrite(deliveryDO);
	try {
		if(StringUtil.isEmptyLong(deliveryDO.getDeliveryId())){
			getHibernateTemplate().saveOrUpdate(deliveryDO);
			getHibernateTemplate().saveOrUpdateAll(deliveryDO.getNavigatorList());
		}else{
			DeliveryDO loadedDlv=getHibernateTemplate().get(DeliveryDO.class, deliveryDO.getDeliveryId());
			loadedDlv.setDtToCentral(CommonConstants.NO);
			loadedDlv.setDrsStatus(deliveryDO.getDrsStatus());
			loadedDlv.setTransactionModifiedDate(deliveryDO.getTransactionModifiedDate());
			loadedDlv.getDeliveryDtlsDO().addAll(deliveryDO.getDeliveryDtlsDO());
			getHibernateTemplate().saveOrUpdate(loadedDlv);
		}
	} catch (Exception e) {
		LOGGER.error("DeliveryCommonDAOImpl::savePrepareDrs :: Exception",e);
		throw new CGSystemException(e);
	}
	return Boolean.TRUE;
}

/**
 * getDrsNumberStatus
 * @param drsHeaderTO
 * @return
 * @throws CGSystemException
 * @throws CGBusinessException
 */
@Override
public String getDrsStatusByDrsNumber(AbstractDeliveryTO drsHeaderTO)throws CGSystemException{
	List<String> drsStatus;
	String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER};
	Object value[] = {drsHeaderTO.getDrsNumber()};
	drsStatus=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_GET_DRS_STATUS_BY_DRS_NUMBER,params, value);
	return !StringUtil.isEmptyList(drsStatus)?drsStatus.get(0):null;
}
/**
 * Gets the drs details by drs number For Print Functionality.
 *
 * @param drsNumber the drs number
 * @return the drs details by drs number
 * @throws CGSystemException the cG system exception
 */
@Override
public DeliveryDO getDrsDetailsByDrsNumber(String drsNumber) throws CGSystemException {
	 List<DeliveryDO> deliveryDOList;
	String params[]={DrsCommonConstants.QRY_PARAM_DRS_NUMBER};
	Object value[] = {drsNumber};
	try{
		deliveryDOList=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_DRS_DETAILS_BY_DRS_NUMBER_PRINT,params, value);

	}catch(Exception e){
		throw new CGSystemException(e);
	}
	return !StringUtil.isEmptyList(deliveryDOList)?deliveryDOList.get(0):null;
}

@Override
public Boolean isPaymentCapturedForCn(Integer consgId)throws CGSystemException{
	Boolean isPaymentCaptured=false;
	List<Long> drsDtls;
	String params[]={UniversalDeliveryContants.QRY_PARAM_CONSG_ID,UniversalDeliveryContants.QRY_PARAM_DRS_STATUS};
	Object value[] = {consgId,UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED};
	drsDtls=getHibernateTemplate().findByNamedQueryAndNamedParam(DrsCommonConstants.QRY_IS_PAYMENT_DETAILS_CAPTURED_BY_CN,params, value);
	if(!CGCollectionUtils.isEmpty(drsDtls) && !StringUtil.isEmptyLong(drsDtls.get(0))){
		isPaymentCaptured=true;
	}
	return isPaymentCaptured;
}


/**
 * Usage:To check whether the  Consignment  included in Third Party manifest
 *  purpose: to allow CN in Manual Drs ( inspite of CN manifested in Third pary manifest)
 * @return
 * @throws CGSystemException
 */
@Override
public String getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForParentCn(AbstractDeliveryTO deliveryTO) throws CGSystemException {
	List<String> manifestedNumber;
	String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
			ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID,ManifestUniversalConstants.MANIFEST_STATUS};
	Object[] values = { deliveryTO.getConsignmentNumber(), CommonConstants.MANIFEST_TYPE_OUT,deliveryTO.getProcessCode() ,deliveryTO.getLoginOfficeId(),ManifestUniversalConstants.MANIFEST_STATUS_CLOSED};
	manifestedNumber = getHibernateTemplate().findByNamedQueryAndNamedParam(
			deliveryTO.getQueryName(),
			params, values);
	return !CGCollectionUtils.isEmpty(manifestedNumber) ? manifestedNumber
			.get(0) : null;
}

/**
 *  Usage:To check whether the  Consignment  included in Third Party manifest
 *  purpose: to allow CN in Manual Drs ( inspite of CN manifested in Third pary manifest)
 *
 * @return the out manifested consignment number by office for tp manifest for manual drs for child cn
 * @throws CGSystemException the cG system exception
 */
@Override
public String getOutManifestedConsignmentNumberByOfficeForTPManifestForManualDrsForChildCn(AbstractDeliveryTO deliveryTO) throws CGSystemException {
	List<String> manifestedNumber;
	String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG,
			ManifestUniversalConstants.MANIFEST_TYPE, UniversalDeliveryContants.QRY_PARAM_PROCESS_CODE,UniversalDeliveryContants.QRY_PARAM_LOGGED_IN_OFFICE_ID,ManifestUniversalConstants.MANIFEST_STATUS};
	Object[] values = { deliveryTO.getConsignmentNumber(), CommonConstants.MANIFEST_TYPE_OUT,deliveryTO.getProcessCode() ,deliveryTO.getLoginOfficeId(),ManifestUniversalConstants.MANIFEST_STATUS_CLOSED};
	manifestedNumber = getHibernateTemplate().findByNamedQueryAndNamedParam(
			deliveryTO.getQueryName(),
			params, values);
	return !CGCollectionUtils.isEmpty(manifestedNumber) ? manifestedNumber
			.get(0) : null;
}


/**
 * Checkif cons is delivered.
 *
 * @param query the query
 * @param consNO the cons no
 * @param deliverystatus the deliverystatus
 * @return the string
 */
@Override
public String checkifConsIsDelivered(String query,String consNO,String deliverystatus){
	List<String> status;
	String[] params = { UniversalDeliveryContants.QRY_PARAM_CONSG};
	Object[] values = { consNO};
	status = getHibernateTemplate().findByNamedQueryAndNamedParam(
			query,
			params, values);
	return !CGCollectionUtils.isEmpty(status) ? status
			.get(0) : null;
	
}

}