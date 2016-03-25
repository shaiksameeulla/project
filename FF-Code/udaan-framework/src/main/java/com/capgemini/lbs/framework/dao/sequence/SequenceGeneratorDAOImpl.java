/**
 * 
 */
package com.capgemini.lbs.framework.dao.sequence;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.SequenceGeneratorConfigDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *
 */
public class SequenceGeneratorDAOImpl extends CGBaseDAO implements
		SequenceGeneratorDAO {
	private Logger logger = Logger.getLogger(SequenceGeneratorDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.capgemini.lbs.framework.dao.sequence.SequenceGeneratorDAO#getSequenceDO(java.lang.String)
	 */
	@Override
	public SequenceGeneratorConfigDO getSequenceDO(String processType)
			throws CGSystemException {
		List<SequenceGeneratorConfigDO> resultDo=null;
		resultDo = getHibernateTemplate().findByNamedQueryAndNamedParam(CommonConstants.QRY_GET_SEQUENCE_BY_PROCESS, CommonConstants.QRY_PARAM_PROCESS_TYPE, processType);
		return StringUtil.isEmptyList(resultDo)?null :resultDo.get(0);
	}

	@Override
	public Boolean updateGeneratedSequenceById(Integer sequenceGeneratorId,Long lastSeqNum)
			throws CGSystemException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
	Boolean result=Boolean.FALSE;
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Transaction tx=null;
	try {
		 tx=session.beginTransaction();
	
		Query qry = session.getNamedQuery(CommonConstants.QRY_UPDATE_SEQ_BY_GENERATOR_ID);	
		qry.setLong(CommonConstants.QRY_PARAM_LAST_GENERATED_NUMBER, lastSeqNum);
		qry.setDate(CommonConstants.QRY_PARAM_LAST_GENERATED_DATE, new Date());
		qry.setInteger(CommonConstants.QRY_PARAM_SEQUENCE_GENERATOR_ID, sequenceGeneratorId);
		int rowCount = qry.executeUpdate();
		if(rowCount>0){
			result=Boolean.TRUE;
		}
		logger.info("tx.isActive()"+tx.isActive());
		logger.info("tx.wasCommitted()"+tx.wasCommitted());
		 tx.commit();
	} catch (Exception e) {
		logger.error("SequenceGeneratorDAOImpl::updateGeneratedSequenceById",e);
		 tx.rollback();
		 
		 throw new CGSystemException(e);
	}finally{
		closeSession(session);
	}
	return result;
	}
	
	@Override
	public String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqConfigTo, String queryName) throws CGSystemException {
		List<String> numberList=null;
		String params[]={CommonConstants.QRY_PARAM_PREFIX ,CommonConstants.LOGGED_IN_OFFICE_ID,CommonConstants.QRY_PARAM_NUMBER_LENGTH};
		Object value[] = {seqConfigTo.getPrefixCode()+FrameworkConstants.CHARACTER_PERCENTILE,seqConfigTo.getRequestingBranchId(),seqConfigTo.getLengthOfNumber()};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,params, value);
		return !StringUtil.isEmptyList(numberList)?numberList.get(0):null;
	}

}
