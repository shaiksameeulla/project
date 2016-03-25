/**
 * 
 */
package com.ff.admin.stockmanagement.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockreceipt.constants.StockReceiptConstants;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockCommonDAOImpl.
 *
 * @author mohammes
 */
@SuppressWarnings("unchecked")
public class StockCommonDAOImpl extends CGBaseDAO implements StockCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockCommonDAOImpl.class);


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemTypeList()
	 */
	@Override
	public List<ItemTypeDO> getItemTypeList() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_ACTIVE_ITEM_TYPES, StockCommonConstants.QRY_PARAM_IS_ACTIVE, StockCommonConstants.ACTIVE_STATUS);
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemListByItemType(java.lang.Integer)
	 */
	@Override
	public List<ItemDO> getItemListByItemType(Integer itemTypeId) throws CGSystemException {
		String params[]={StockCommonConstants.QRY_PARAM_IS_ACTIVE,StockCommonConstants.QRY_PARAM_ITEM_TYPE_ID};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,itemTypeId};
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_MATERIAL_BY_TYPE,params, value);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemTypeAsMap()
	 */
	@Override
	public List<?> getItemTypeAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_ITEM_TYP_AS_MAP, StockCommonConstants.QRY_PARAM_IS_ACTIVE, StockCommonConstants.ACTIVE_STATUS);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemByTypeAsMap(java.lang.Integer)
	 */
	@Override
	public List<?> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException {
		String params[]={StockCommonConstants.QRY_PARAM_IS_ACTIVE,StockCommonConstants.QRY_PARAM_ITEM_TYPE_ID};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,itemTypeId};
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_MATERIAL_BY_TYPE_AS_MAP,params, value);

	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemByItemTypeAndItemId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ItemDO getItemByItemTypeAndItemId(Integer itemTypeId,Integer itemId) throws CGSystemException {
		List<ItemDO> itemList=null;
		String params[]={StockCommonConstants.QRY_PARAM_IS_ACTIVE,StockCommonConstants.QRY_PARAM_ITEM_TYPE_ID,StockCommonConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,itemTypeId,itemId};
		itemList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_MATERIAL_BY_TYPE_AND_ITEM_ID,params, value);
		return !StringUtil.isEmptyList(itemList)?itemList.get(0):null;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemByItemId(java.lang.Integer)
	 */
	@Override
	public ItemDO getItemByItemId(Integer itemId) throws CGSystemException {
		List<ItemDO> itemList=null;
		String params[]={StockCommonConstants.QRY_PARAM_IS_ACTIVE,StockCommonConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,itemId};
		itemList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_MATERIAL_ITEM_ID,params, value);
		return !StringUtil.isEmptyList(itemList)?itemList.get(0):null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getItemDetails()
	 */
	@Override
	public List<?> getItemDetails() throws CGSystemException {
		String params[]={StockCommonConstants.QRY_PARAM_IS_ACTIVE};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS};
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_MATERIAL_DTLS_AS_MAP,params, value);

	}


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getMaxNumberFromProcess(com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO, java.lang.String)
	 */
	@Override
	public String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqConfigTo, String queryName) throws CGSystemException {
		List<String> numberList=null;
		String params[]={StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_PREFIX ,StockCommonConstants.QRY_PARAM_NUMBER_LENGTH};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,seqConfigTo.getRequestingBranchId(),seqConfigTo.getProcessRequesting()+seqConfigTo.getRequestingBranchCode()+FrameworkConstants.CHARACTER_PERCENTILE,seqConfigTo.getLengthOfNumber()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,params, value);
		return !StringUtil.isEmptyList(numberList)?numberList.get(0):null;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getStockStandardTypes(java.lang.String)
	 */
	@Override
	public List<StockStandardTypeDO> getStockStandardTypes(String typeName) throws CGSystemException {
		List<StockStandardTypeDO> numberList=null;
		Object[] values=null;
		if(!StringUtil.isStringEmpty(typeName)&& typeName.contains(FrameworkConstants.CHARACTER_COMMA)){
			values=typeName.split(FrameworkConstants.CHARACTER_COMMA);
		}else{
			values= new Object[]{typeName};
		}
		String params[]={StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.GET_STOCK_STD_TYPE_BY_TYPE_NAME,params, values);
		return numberList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getStockStandardTypesAsMap(java.lang.String)
	 */
	@Override
	public List<?> getStockStandardTypesAsMap(String typeName) throws CGSystemException {
		List<?> numberList=null;
		Object[] values=null;
		if(!StringUtil.isStringEmpty(typeName)&& typeName.contains(FrameworkConstants.CHARACTER_COMMA)){
			values=typeName.split(FrameworkConstants.CHARACTER_COMMA);
		}else{
			values= new Object[]{typeName};
		}

		Session session = null;
		try{
			session =createSession();
			Query query = session.getNamedQuery(StockCommonConstants.GET_STOCK_STD_TYPE_BY_TYPE_NAME_MAP);
			query.setParameterList(StockCommonConstants.QRY_PARAM_TYPE_NAME, values);
			numberList = query.list();
		}catch (Exception e) {
			LOGGER.error("StockCommonDAOImpl::getStockStandardTypesAsMap :: Exception",e);
			throw new CGSystemException(e.getMessage(),e);
		}finally{
			closeSession(session);
		}


		return numberList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#getIssueType(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public String getIssueType(StockValidationTO to) throws CGSystemException {
		List<String> numberList=null;
		String params[]={StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_END_LEAF,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {StockCommonConstants.ACTIVE_STATUS,to.getOfficeProduct(),to.getStartLeaf(),to.getEndLeaf(),to.getItemId()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_STOCK_ISSUE_TYPE,params, value);
		return !StringUtil.isEmptyList(numberList)?numberList.get(0):null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#isSeriesTransferTOPartyType(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesTransferTOPartyType(String Qryname,StockValidationTO to) throws CGSystemException {
		List<Long> numberList=null;
		List<Long> invalidSeries=new ArrayList<Long>(0);
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {to.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,to.getOfficeProduct(),1l,to.getItemId()};
		for(Long leaf:to.getLeafList()){
			value[3]=leaf;
			numberList=ht.findByNamedQueryAndNamedParam(Qryname,params, value);
			if(numberList.isEmpty()){
				invalidSeries.add(leaf);
			}

		}
		return invalidSeries;
	}

	@Override
	public Boolean isSeriesTransferTOPartyTypeByLeaf(String Qryname,StockValidationTO to) throws CGSystemException {
		List<Long> numberList=null;
		//List<Long> invalidSeries=new ArrayList<Long>(0);
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {to.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,to.getOfficeProduct(),to.getLeaf(),to.getItemId()};
		numberList=ht.findByNamedQueryAndNamedParam(Qryname,params, value);
		return !CGCollectionUtils.isEmpty(numberList) && !StringUtil.isEmptyLong(numberList.get(0))?true:false;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#isSeriesReturnedTOPlant(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesReturnedTOPlant(StockValidationTO validationTo) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_SERIES_RETURNED_TO_PLANT;
		List<Long> numberList=null;
		List<Long> invalidSeries=new ArrayList<Long>();
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTo.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),1l,validationTo.getItemId()};
		for(Long leaf:validationTo.getLeafList()){
			value[3]=leaf;
			numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
			if(numberList.isEmpty()){
				invalidSeries.add(leaf);
			}

		}
		return invalidSeries;
	}
	/**
	 * isSeriesReturnedTOPlantByLeaf :: it takes each leaf check whether series is returned or not :if it's returned to plant then method returns value otherwise null
	 * @param validationTo
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public Boolean isSeriesReturnedTOPlantByLeaf(StockValidationTO validationTo) throws CGSystemException {
		String qryName= StockUniveralConstants.QRY_SERIES_RETURNED_TO_PLANT;
		List<Long> numberList=null;
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTo.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),validationTo.getLeaf(),validationTo.getItemId()};
		numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
		return ((!CGCollectionUtils.isEmpty(numberList) && !StringUtil.isEmptyLong(numberList.get(0)))?true:false);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#isSeriesReturnedFromPlant(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesReturnedFromPlant(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_SERIES_RETURNED_FROM_PLANT;
		List<Long> numberList=null;
		List<Long> alreadyReturned=new ArrayList<Long>(validationTO.getLeafList().size());
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTO.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),1l,validationTO.getItemId()};
		for(Long leaf:validationTO.getLeafList()){
			value[3]=leaf;
			numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
			if(!numberList.isEmpty()){
				alreadyReturned.add(leaf);
			}

		}
		return alreadyReturned;
	}

	@Override
	public Boolean isSeriesReturnedFromPlantByLeaf(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_SERIES_RETURNED_FROM_PLANT;
		List<Long> numberList=null;
		HibernateTemplate ht=getHibernateTemplate();
		String params[]={ StockUniveralConstants.QRY_PARAM_TYPE_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTO.getPartyTypeId(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getLeaf(),validationTO.getItemId()};
		numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
		return !CGCollectionUtils.isEmpty(numberList)&& !StringUtil.isEmptyLong(numberList.get(0))?true:false;
	}

	/**
	 * Checks if is series issued with issue number.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException Purpose : for Stock Receipt validations against issue number
	 */
	@Override
	public List<Long> isSeriesIssuedWithIssueNumber(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_SERIES_ISSUED_WITH_ISSUE_NUMBER;
		List<Long> numberList=null;
		String params[]={StockCommonConstants.TRANSACTION_NUMBER ,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getItemId(),validationTO.getStartLeaf(),validationTO.getEndLeaf()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return numberList;
	}
	/**
	 * isSeriesReceivedWithReceiptNumber :: for Stock Return against Acknowledgementnumber
	 * @param validationTO
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<Long> isSeriesReceivedWithReceiptNumber(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_IS_SERIES_RECEIVED_WITH_RECEIPT_NUMBER_BY_RANGE;
		List<Long> numberList=null;
		String params[]={StockCommonConstants.TRANSACTION_NUMBER ,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getItemId(),validationTO.getStartLeaf(),validationTO.getEndLeaf()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return numberList;
	}
	@Override
	public List<Long> isSeriesReceivedWithLatestReceiptNumber(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_IS_SERIES_RECEIVED_WITH_LATEST_RECEIPT_NUMBER_BY_RANGE;
		List<Long> numberList=null;
		String params[]={StockCommonConstants.TRANSACTION_NUMBER ,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getItemId(),validationTO.getStartLeaf(),validationTO.getEndLeaf()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return numberList;
	}
	/**
	 * isSeriesReceivedWithReceiptNumberWithDtlsId:  for Stock Return against Acknowledgementnumber
	 * @param validationTO
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<Long> isSeriesReceivedWithReceiptNumberWithDtlsId(StockValidationTO validationTO) throws CGSystemException {
		String qryName= StockUniveralConstants.QRY_IS_SERIES_RECEIVED_WITH_RECEIPT_NUMBER_WITH_DTLS_ID_BY_RANGE;
		List<Long> numberList=null;
		String params[]={StockCommonConstants.TRANSACTION_NUMBER ,StockReceiptConstants.QRY_PARAM_RECEIPT_DTLS_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getTransactionNumber(),validationTO.getStockReceiptItemDetailsId(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getItemId(),validationTO.getStartLeaf(),validationTO.getEndLeaf()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return numberList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#isSeriesIssuedWithIssueNumberWithDtlsId(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesIssuedWithIssueNumberWithDtlsId(StockValidationTO validationTO) throws CGSystemException {

		String qryName= StockUniveralConstants.QRY_SERIES_ISSUED_WITH_ISSUE_NUMBER_DETLS_ID;
		List<Long> numberList=null;
		String params[]={StockCommonConstants.TRANSACTION_NUMBER ,StockUniveralConstants.QRY_PARAM_ITEM_DETAILS_ID,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getTransactionNumber(),validationTO.getItemDetailsId(),StockCommonConstants.ACTIVE_STATUS,validationTO.getOfficeProduct(),validationTO.getItemId(),validationTO.getStartLeaf(),validationTO.getEndLeaf()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);
		return numberList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.common.dao.StockCommonDAO#isSeriesReceivedWithIssueNumber(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesReceivedWithIssueNumber(
			StockValidationTO validationTo) throws CGSystemException {

		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER;


		List<Long> numberList=null;
		List<Long> notReceived=new ArrayList<Long>(validationTo.getLeafList().size());
		HibernateTemplate ht=getHibernateTemplate();

		String params[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTo.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),1l,validationTo.getItemId()};

		for(Long leaf:validationTo.getLeafList()){
			value[3]=leaf;
			numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
			if(numberList.isEmpty()){
				notReceived.add(leaf);
				break;
			}

		}


		return CGCollectionUtils.isEmpty(notReceived)?true:false;
	}

	/**
	 * Checks if is series issued from branch for transfer.
	 *
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<Long> isSeriesIssuedFromBranchForTransfer(StockValidationTO validationTO)
			throws CGSystemException {
		List<Long> result=null;
		List<Long> issuedList=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String Qry=StockUniveralConstants.QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getLoggedInOfficeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};


		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			result= ht.findByNamedQueryAndNamedParam(Qry, paramNames, values);
			if(result.isEmpty()){
				issuedList.add(leaf);
				break;
			}
		}
		return issuedList;
	}


	@Override
	public String isIssueNumberExistForOffice(String issueNumber,Integer officeId)
			throws CGSystemException {
		List<String> issueDtls = null;
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockCommonDAOImpl::isIssueNumberExistForOffice ..Start Time:[ "+starttime+"]");
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {issueNumber,StockCommonConstants.ACTIVE_STATUS,officeId};
		issueDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_IS_ISSUE_NUMBER_EXIST_FOR_OFFICE, params, values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockCommonDAOImpl::isIssueNumberExistForOffice ..End Time:[ "+endtime+"] Difference :"+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(issueDtls)? issueDtls.get(0) :null;
	} 

	@Override
	public String getMaxTxNoForStockConsolidation(String numberPrefix, Integer runningNumberLength, Integer officeId) throws CGSystemException {
		LOGGER.trace("StockCommonDAOImpl :: getMaxTxNoForStockConsolidation() :: START");
		String params[]={StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_PREFIX ,StockCommonConstants.QRY_PARAM_NUMBER_LENGTH};
		Object value[] = {officeId,numberPrefix+FrameworkConstants.CHARACTER_PERCENTILE,runningNumberLength+numberPrefix.length()};
		List<String> numberList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				StockCommonConstants.QRY_GET_MAX_TXN_NO, params,value);
	
		LOGGER.trace("StockCommonDAOImpl :: getMaxTxNoForStockConsolidation() :: END");
		return !StringUtil.isEmptyList(numberList) ? numberList.get(0) : null;
	}
	
	@Override
	public StockHolderWrapperDO getLatestStockDetailsByLeafForIssue(StockValidationTO validationTO)
			throws CGSystemException {
		List<StockHolderWrapperDO> result=null;
		Session session=null;
		String Qry="getLatestStockByLeafForIssue";
		try {
			session=createSession();
			Query query=session.getNamedQuery(Qry);
			query.setParameter(StockUniveralConstants.QRY_PARAM_ITEM_ID,validationTO.getItemId());
			query.setParameter(StockUniveralConstants.QRY_PARAM_OFFICE_CODE,validationTO.getOfficeProduct());
			query.setParameter(StockUniveralConstants.QRY_PARAM_LEAF,validationTO.getLeaf());
			query.setResultTransformer(Transformers.aliasToBean(StockHolderWrapperDO.class));
			result=query.list();
			logger .debug("---Count of rows for reconcillation------"+result.size());
		}catch(Exception ex){
			logger.error("StockCommonDAOImpl :: getLatestStockDetailsByLeafForIssue()::::::", ex);
			throw new CGSystemException(ex);
		}finally{
			closeSession(session);
		}
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}
	@Override
	public String getLatestStockDetailsByLeafForIssue1(StockValidationTO validationTO)
			throws CGSystemException {
		List<String> result=null;
		Session session=null;
		String Qry="getLatestStockByLeafForIssue1";
		try {
			session=createSession();
			Query query=session.getNamedQuery(Qry);
			query.setParameter(StockUniveralConstants.QRY_PARAM_ITEM_ID,validationTO.getItemId());
			query.setParameter(StockUniveralConstants.QRY_PARAM_OFFICE_CODE,validationTO.getOfficeProduct());
			query.setParameter(StockUniveralConstants.QRY_PARAM_START_LEAF,validationTO.getStartLeaf());
			query.setParameter(StockUniveralConstants.QRY_PARAM_END_LEAF,validationTO.getEndLeaf());
			query.setParameter(StockUniveralConstants.QRY_PARAM_TYPE_ID,validationTO.getPartyTypeId());
			result=query.list();
			logger .debug("---Count of rows for reconcillation------"+result.size());
		}catch(Exception ex){
			logger.error("StockCommonDAOImpl :: getLatestStockDetailsByLeafForIssue()::::::", ex);
			throw new CGSystemException(ex);
		}finally{
			closeSession(session);
		}
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}

	
	
}
