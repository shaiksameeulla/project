package src.com.dtdc.mdbDao.delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import src.com.dtdc.constants.DRSConstants;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.master.ReasonDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.domain.transaction.delivery.NonDeliveryRunDO;
import com.dtdc.to.delivery.DeliveryRunTO;
import com.dtdc.to.delivery.NonDeliveryRunDisplyTO;
import com.dtdc.to.delivery.NonDeliveryRunTO;
import com.dtdc.to.franchisee.FranchiseeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class NonDeliveryRunMDBDAOImpl.
 */
public class NonDeliveryRunMDBDAOImpl extends CGBaseDAO implements
NonDeliveryRunMDBDAO{
	/** logger. */
	private Logger LOGGER = LoggerFactory.getLogger(NonDeliveryRunMDBDAOImpl.class);

	/**
	 * utility method to copy values from DO to DeliveryRunDisplayTO.
	 *
	 * @param doList the do list
	 * @param forPrint the for print
	 * @return the list
	 */
	private List<NonDeliveryRunDisplyTO> copyDO2TO(List<DeliveryDO>  doList, boolean forPrint){
		List<NonDeliveryRunDisplyTO> tos = new ArrayList<NonDeliveryRunDisplyTO>();
		if(doList != null && doList.size() > 0){

			for (int i = 0; i < doList.size(); i++) {
				DeliveryDO deliveryDO = doList.get(i);
				NonDeliveryRunDisplyTO to = new NonDeliveryRunDisplyTO();
				if(deliveryDO != null){
					to.setDeliveryId(deliveryDO.getDeliveryId());
					to.setConsgNum(deliveryDO.getConNum());
					to.setDelivAttemptNo(deliveryDO.getAttemptNum()+"");
					to.setDeliveryStatus(deliveryDO.getDeliveryStatus());
					to.setAttemptTime(deliveryDO.getAttemptTime());
					to.setPodReceived(deliveryDO.getPod());
					to.setRelationCode(deliveryDO.getRelationShip());
					to.setPhoneNo(deliveryDO.getPhNum());

					if(forPrint){
						//to.setWeight(String.valueOf(deliveryDO.getConsgWeight()));
						to.setNoOfPieces(deliveryDO.getTotalNumOFPieces());
						to.setDeliveryTime(deliveryDO.getDeliveryTime());
					}
				}
				tos.add(to);
			}


		} 
		return tos;      
	}	

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getBranchName(String)
	 */
	@Override
	public String getBranchName(String brCode) throws CGSystemException,
	CGBusinessException {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		String name = "";
		try {
			String hql = "select office.officeName from com.dtdc.domain.master.office.OfficeDO office where  office.officeCode=:brCode";
			Query query = session.createQuery(hql);
			query.setString("brCode", brCode);
			name = (String)query.uniqueResult();
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getBranchName occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return name;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public DeliveryDO getDeliveryConsignmentDetails(CGBaseTO to, String consignmentNo,String runSheetNumber)
			throws CGSystemException, CGBusinessException {		
		DeliveryRunTO drto = null;
		NonDeliveryRunTO ndrto = null;
		StringBuilder query = new StringBuilder("from com.dtdc.domain.transaction.delivery.DeliveryDO do ");
		DeliveryDO do1 = null;
		//this variable is a flag to discriminate the branch or franchisee. 
		String headerFileName = "";
		List<DeliveryDO> consignDetls = new ArrayList<DeliveryDO>();

		if(to instanceof DeliveryRunTO){
			drto = (DeliveryRunTO)to;
			headerFileName = drto.getHeaderFileName();
		} else if(to instanceof NonDeliveryRunTO){
			ndrto = (NonDeliveryRunTO)to;
			headerFileName = ndrto.getHeaderFileName();
		}

		if(headerFileName != null && headerFileName.equalsIgnoreCase("branch")){
			Object[] values = new Object[3];
			String[] params = {"conNum","runSheetNum","branchId"};
			if(to instanceof DeliveryRunTO){
				values[0] = consignmentNo;
				values[1] = runSheetNumber;
				values[2] =Integer.valueOf(drto.getBranchId());

			} else if(to instanceof  NonDeliveryRunTO) {
				values[0] = consignmentNo;
				values[1] = runSheetNumber;
				values[2] =Integer.valueOf(ndrto.getBranchId());
			}
			query.append(" where consgStatus in ('A') and conNum =:conNum and runSheetNum=:runSheetNum and branch.officeId=:branchId");
			consignDetls  = getHibernateTemplate().findByNamedParam( query.toString(),params, values);

		} else if(headerFileName != null && headerFileName.equalsIgnoreCase("franchisee")) {
			String[] params = {"conNum","branchId", "franchiseeId"};
			Object[] values = new Object[3];
			if(to instanceof DeliveryRunTO){
				//values[0] = drto.getFdmNo(); 
				values[0] =	consignmentNo;
				values[1] = Integer.valueOf(drto.getBranchId());
				values[2] =Integer.valueOf(drto.getFranchiseeId());

			} else if(to instanceof  NonDeliveryRunTO) {
				//values[0] = ndrto.getFdmNo(); 
				values[0] =	consignmentNo;
				values[1] = Integer.valueOf(ndrto.getBranchId());
				values[2] =Integer.valueOf(ndrto.getFranchiseeId());
			}
			query.append(" where consgStatus in ('A') and conNum =:conNum and branch.officeId=:branchId and franchisee.franchiseeId=:franchiseeId");
			consignDetls  = getHibernateTemplate().findByNamedParam( query.toString(),params, values);
		} else {
			throw new CGBusinessException(ErrorCodeConstants.INVALID_REQUEST);
		}

		if(consignDetls.size() > 0)	{
			do1 = consignDetls.get(0);
		}
		return do1;
	}*/

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getDeliveryConsignmentDetailsForBranch(CGBaseTO, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryConsignmentDetailsForBranch(CGBaseTO to,
			String consignmentNo) throws CGSystemException, CGBusinessException {

		List<DeliveryDO> dos = null;
		DeliveryRunTO drto = null;
		NonDeliveryRunTO ndrto = null;
		//String runSheetNum="";
		String branchId="";
		Session session = null;

		if(to instanceof DeliveryRunTO){
			drto = (DeliveryRunTO)to;
			//runSheetNum=runSheetNumber;
			branchId=drto.getBranchId();
		} else if(to instanceof NonDeliveryRunTO){
			ndrto = (NonDeliveryRunTO)to;
			//runSheetNum=runSheetNumber;
			branchId=ndrto.getBranchId();
		}

		StringBuilder hql = new StringBuilder("from com.dtdc.domain.transaction.delivery.DeliveryDO do ");
		hql.append("where conNum =:conNum and branch.officeId=:branchId and consgStatus !='I'"); //and runSheetNum=:runSheetNum

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql.toString());
			query.setString("conNum", consignmentNo);
			/*query.setString("runSheetNum", runSheetNum);*/
			query.setString("branchId", branchId);		
			dos   = query.list();			
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getDeliveryConsignmentDetailsForBranch occured:"
					+e.getMessage());
		} finally{			
			session.flush();
			session.close();
		}
		return dos;
	}



	/*@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryDO(String aConsgNumber, String branchId, 
			String drsNo, String headerFile, String fdmNo)
			throws CGSystemException, CGBusinessException {

		List<DeliveryDO> deliveryDOs = null;
		Session session = null;	

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			 Criteria criteria = session.createCriteria(DeliveryDO.class);
			 criteria.add(Restrictions.eq("conNum", aConsgNumber));
			 if(StringUtils.hasText(headerFile) && headerFile.equalsIgnoreCase(DRSConstants.BRANCH)){
				 criteria.add(Restrictions.eq("runSheetNum", drsNo));
				 criteria.add(Restrictions.eq("branch.officeId", branchId));
				 //criteria.add(Restrictions.eq("deliveryCode", deliver));
			 } else {
				 criteria.add(Restrictions.eq("fdmNumber", fdmNo));
				 criteria.add(Restrictions.eq("franchisee.franchiseeId", branchId));
			 }
			 criteria.add(Restrictions.not(Restrictions.in("consgStatus", new String[] {"I"})));
			 deliveryDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.info("Error occured in getDeliveryDO()..:" + e.getMessage());
		} finally{			
			session.flush();
			session.close();
		}		
		return deliveryDOs;
	}*/


	//Currently code not in use
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#inquireDRS(NonDeliveryRunTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<NonDeliveryRunDisplyTO> inquireDRS(NonDeliveryRunTO searchTo) throws CGBusinessException{		
		List<NonDeliveryRunDisplyTO> nondeliveryRunDisplyTOs = new ArrayList<NonDeliveryRunDisplyTO>();

		StringBuffer sb = new StringBuffer("from com.dtdc.domain.transaction.delivery.DeliveryDO do"); 
		sb.append(" where do.deliType=:deliType");

		if(searchTo != null){
			if(searchTo.getFdmNo() != null && 
					StringUtils.hasText(searchTo.getFdmNo())){
				sb.append(" and do.fdmNumber=:fdmNumber");
			}

			/*if(searchTo.getDeliveryDate() != null && 
					StringUtils.hasText(searchTo.getDeliveryDate())){
				sb.append(" and do.deliveryDate=:deliveryDate");
			}*/

			if(searchTo.getBranchCode() != null && 
					StringUtils.hasText(searchTo.getBranchId())){
				sb.append(" and do.branchID=:branchID"); //for search branchID reqd
			}

			if(searchTo.getFranchiseCode() != null && 
					StringUtils.hasText(searchTo.getFranchiseCode())){
				sb.append(" and do.franchiseeID=:franchiseeID");
			}
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(sb.toString());

			query.setString("deliType", "NDRS"); 

			if(searchTo.getFdmNo() != null && 
					StringUtils.hasText(searchTo.getFdmNo())){
				query.setString("fdmNumber", searchTo.getFdmNo());
			}

			if(searchTo != null){
				/*if(searchTo.getDeliveryDate() != null && 
						StringUtils.hasText(searchTo.getDeliveryDate()[rowNum])){
					query.setDate("deliveryDate", DateFormatterUtil
													.getDateFromString(searchTo.getDeliveryDate()[rowNum],DateFormatterUtil.DD_MM_YYYY));
				}*/

				if(searchTo.getBranchCode() != null && 
						StringUtils.hasText(searchTo.getBranchId())){
					query.setString("branchID", searchTo.getBranchId());
				}

				if(searchTo.getFranchiseCode() != null && 
						StringUtils.hasText(searchTo.getFranchiseCode())){
					query.setString("franchiseeID", searchTo.getFranchiseCode());
				}

				List<DeliveryDO> doList = (List<DeliveryDO>)query.list();
				nondeliveryRunDisplyTOs = copyDO2TO(doList, false);
				session.flush();
				session.close();
			}
		}

		return nondeliveryRunDisplyTOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getDeliveryDOByDrsNoForBranch(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryDOByDrsNoForBranch(String aDRSNumber, Integer branchId)
	throws CGSystemException, CGBusinessException {

		Session session = null;	
		List<DeliveryDO>  aDeliveryDOs = new ArrayList<DeliveryDO>();
		try {			
			session = getHibernateTemplate().getSessionFactory().openSession();
			aDeliveryDOs = session.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq("runSheetNum", aDRSNumber))
			.add(Restrictions.eq("branch.officeId", branchId))							
			.list();
		} catch (HibernateException e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getDeliveryDOByDrsNoForBranch occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}finally{
			session.flush();
			session.close();
		}
		return aDeliveryDOs;
	}



	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getDeliveryDOByDRSNoForFranchisee(String, Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryDOByDRSNoForFranchisee(String aDRSNumber, Integer branchId,
			Integer franchiseeId) throws CGSystemException, CGBusinessException {
		Session session = null;	
		List<DeliveryDO>  aDeliveryDOs = new ArrayList<DeliveryDO>();
		try {			
			session = getHibernateTemplate().getSessionFactory().openSession();
			aDeliveryDOs = session.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq("runSheetNum", aDRSNumber))
			.add(Restrictions.eq("branch.officeId", branchId))
			.add(Restrictions.eq("franchisee.franchiseeId", franchiseeId))							
			.list();
		} catch (HibernateException e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getDeliveryDOByDRSNoForFranchisee occured:"
					+e.getMessage());
		}finally{
			session.flush();
			session.close();
		}
		return aDeliveryDOs;
	}


	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getDeliveryDOs(String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getDeliveryDOs(String...consgNo)
	throws CGSystemException, CGBusinessException {
		List<DeliveryDO> dos = new ArrayList<DeliveryDO>();
		String hql ="from com.dtdc.domain.transaction.delivery.DeliveryDO delivery WHERE delivery.conNum IN (:consgNo) and delivery.consgStatus=:status";
		Session session = null;	
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setParameterList("consgNo", consgNo);
			String[] status = {"A"};
			query.setParameterList("status", status);
			dos = query.list();
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getDeliveryDOs occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally{
			session.flush();
			session.close();
		}
		return dos;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getReasonDOById(Integer)
	 */
	@Override
	public ReasonDO getReasonDOById(Integer id) throws CGBusinessException {
		ReasonDO reasonDO = null;
		try {
			reasonDO =  getHibernateTemplate().get(ReasonDO.class, id);
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getReasonDOById occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}
		return reasonDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#updateAllDeliveryDOs(List, List, List)
	 */
	@Override
	public void updateAllDeliveryDOs(List<? extends DeliveryDO> deliveryDOs, List<MiscExpenseDO> miscExpenseDos, List<? extends DeliveryDO> bdmFdmDos) 
	throws CGSystemException, CGBusinessException {
		try {
			if(miscExpenseDos != null && !miscExpenseDos.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(miscExpenseDos);
			}
			getHibernateTemplate().saveOrUpdateAll(deliveryDOs);
			getHibernateTemplate().saveOrUpdateAll(bdmFdmDos);
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::updateAllDeliveryDOs occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<FranchiseeDO> getAllFranchiseeByOfficeId(
			Integer officeId) throws CGSystemException {
		List<FranchiseeDO> franchiseeDOs = null;
		Session session = null;
		Set<Integer> masteridSet = new HashSet<Integer>();
		List<Integer> offIdList = new ArrayList<Integer>();
		offIdList.add(officeId);
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			getAllChildOffices(offIdList, masteridSet);
			Criteria criteria = session.createCriteria(FranchiseeDO.class);
			ProjectionList pl = Projections.projectionList();
			pl.add(Projections.property("franchiseeId"));
			pl.add(Projections.property("franchiseeCode"));
			pl.add(Projections.property("firstName"));
			pl.add(Projections.property("lastName"));
			criteria.setProjection(pl);
			criteria.add(Restrictions.in("officeId.officeId", masteridSet));
			criteria.add(Restrictions.eq("status", DRSConstants.ACTIVE_STATUS));
			franchiseeDOs = criteria.list();

		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			SessionFactoryUtils.closeSession(session);
		}

		return franchiseeDOs;
	}*/

	/*
	public List<OfficeDO> getAllChildOfficeByOfficeId(Integer parentOfficeId){
	List<OfficeDO> childOffices = null; 
	Set<Integer> masteridSet = new HashSet<Integer>();
	List<Integer> offIdList = new ArrayList<Integer>();
	offIdList.add(parentOfficeId);

	Session session = null;
	try {
		getAllChildOffices(offIdList, masteridSet);
		session = getHibernateTemplate().getSessionFactory().openSession();


	} catch (Exception e) {
		//e#prntstcktrce
		throw new CGSystemException("Exception occurred in getAllChildOffices...",e);
	} finally {
		if(session != null){
			session.flush();
			session.close();
		}
	}

}*/

	/**
	 * method will return all child officeIds.
	 *
	 * @param parentOfficeIds the parent office ids
	 * @param masterSet the master set
	 * @return the all child offices
	 * @throws CGSystemException the cG system exception
	 */
	@SuppressWarnings("unchecked")
	private void getAllChildOffices(Collection<Integer> parentOfficeIds, Set<Integer> masterSet) throws CGSystemException{
		List<Integer> idList = new ArrayList<Integer>();
		Set<Integer> childList = new HashSet<Integer>();
		Session session =getHibernateTemplate().getSessionFactory().openSession();
		try {
			masterSet.addAll(parentOfficeIds);
			ProjectionList plList=Projections.projectionList();
			plList.add(Projections.property("officeId"));
			//plList.add(Projections.property("reginolOfficeId"));
			Criteria ct = session.createCriteria(OfficeDO.class);
			ct.setProjection(plList);
			ct.add(Restrictions.in("reginolOfficeId", parentOfficeIds));
			idList = ct.list();

			//to avoid cyclic dependency.
			//the following condition is very important to avoid infinite loop.
			for (Integer childId : idList) {
				if(!masterSet.contains(childId)){
					childList.add(childId);
				}
			}
			masterSet.addAll(idList);
			if(childList != null && !childList.isEmpty()){
				getAllChildOffices(childList, masterSet);
			}


		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getAllChildOffices occured:"
					+e.getMessage());
			throw new CGSystemException("Exception occurred in getAllChildOffices...",e);
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getFranchiseeCodeValuesByApexBranch(Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public List<FranchiseeTO> getFranchiseeCodeValuesByApexBranch(
			Integer branchId) throws CGSystemException {
		List<FranchiseeTO> franchiseeTOs = null;

		String paramName = "branchId";
		Object value = branchId;
		try {
			franchiseeTOs = getHibernateTemplate().findByNamedQueryAndNamedParam("getFranchiseeCodeValuesByApexBranch", paramName, value);
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getFranchiseeCodeValuesByApexBranch occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		}

		return franchiseeTOs;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#isDuplicateDrsNo(String, String, String, String)
	 */
	@Override
	public boolean isDuplicateDrsNo(String aDRSNumber, String branchId, String franchiseeCode, String headerFileName)
	throws CGBusinessException {
		Session session =getHibernateTemplate().getSessionFactory().openSession();
		try {
			/*Set<String> status = new HashSet<String>();

			status.add(DRSConstants.STATUS_D);
			status.add(DRSConstants.STATUS_N);
			status.add(DRSConstants.STATUS_H);*/

			StringBuffer queryStr = new StringBuffer(" select d.runSheetNum ");
			queryStr.append(" from com.dtdc.domain.transaction.delivery.DeliveryDO d ");
			queryStr.append(" where d.consgStatus != 'I' and d.runSheetNum =:runSheetNum and ");
			queryStr.append("((deliveryStatus = 'D' and deliveryDate < :today ) or (deliveryStatus = 'N' and attemptDate < :today))");
			//queryStr.append(" and deliveryStatus in (:status) ");

			/*
			 * 
		if(headerFileName.equalsIgnoreCase(DRSConstants.BRANCH)){
			queryStr.append(" d.branch.officeId=:branchId ");
		}
		if(headerFileName.equalsIgnoreCase(DRSConstants.FRANCHISEE)){
			queryStr.append(" d.franchisee.franchiseeId=:franchiseeId");
		}
			 */

			Query query = session.createQuery(queryStr.toString());
			query.setParameter("runSheetNum", aDRSNumber);
			query.setDate("today", new Date());
			//query.setParameterList("status", status.toArray());

			@SuppressWarnings("unchecked")
			List<String> drsNos = (List<String>)query.list();

			if(drsNos != null && drsNos.size() > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::isDuplicateDrsNo occured:"
					+e.getMessage());
			throw new CGBusinessException();
		} finally {
			if(session != null){
				session.flush();
				session.close();
			}
		}

		return false;
	}



	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getNonDeliveryReasons()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonDO> getNonDeliveryReasons() throws CGSystemException {
		List<ReasonDO> reasonsList = new ArrayList<ReasonDO>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria c = session.createCriteria(ReasonDO.class);
			c.add(Restrictions.eq("nonDelvReason", "Y"));
			reasonsList = (List<ReasonDO>) c.list();
		} catch (Exception obj) {
			logger.error("NonDeliveryRunMDBDAOImpl::getNonDeliveryReasons occured:"
					+obj.getMessage());
			throw new CGSystemException(obj);
		} finally {
			session.flush();
			session.close();
		}
		return reasonsList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#isDrsNoAugogeneratedForBranch(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isDrsNoAugogeneratedForBranch(String aDRSNumber,
			Integer branchId) throws CGSystemException {
		Session session = null;	
		List<DeliveryDO>  aDeliveryDOs = new ArrayList<DeliveryDO>();
		try {			
			session = getHibernateTemplate().getSessionFactory().openSession();
			aDeliveryDOs = session.createCriteria(DeliveryDO.class)
			.add(Restrictions.eq("runSheetNum", aDRSNumber))
			.add(Restrictions.eq("branch.officeId", branchId))
			.add(Restrictions.eq("consgStatus", "A"))
			.add(Restrictions.eq("drsAutoGenerated", "Y"))
			//.add(Restrictions.eq("franchisee.franchiseeId", franchiseeId))							
			.list();
			if(aDeliveryDOs != null && aDeliveryDOs.size() > 0){
				return true;
			}
		} catch (HibernateException e) {
			logger.error("NonDeliveryRunMDBDAOImpl::isDrsNoAugogeneratedForBranch occured:"
					+e.getMessage());
		}finally{
			session.flush();
			session.close();
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#isDrsNoAugogeneratedForFranchisee(String, Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isDrsNoAugogeneratedForFranchisee(String aDRSNumber,
			Integer franchiseeId) throws CGSystemException {
		Session session = null;	
		List<DeliveryDO>  aDeliveryDOs = new ArrayList<DeliveryDO>();
		try {			
			session = getHibernateTemplate().getSessionFactory().openSession();
			aDeliveryDOs = session.createCriteria(DeliveryDO.class)
			//.add(Restrictions.eq("runSheetNum", aDRSNumber)) //for franchisee the autogenerated fdm no. will be runsheet no. also
			.add(Restrictions.eq("fdmNumber", aDRSNumber))     //TODO needs to re-verify this validation.
			//.add(Restrictions.eq("branch.officeId", branchId))
			.add(Restrictions.eq("consgStatus", "A"))
			.add(Restrictions.eq("drsAutoGenerated", "Y"))
			.add(Restrictions.eq("franchisee.franchiseeId", franchiseeId))							
			.list();
			if(aDeliveryDOs != null && aDeliveryDOs.size() > 0){
				return true;
			}
		} catch (HibernateException e) {
			logger.error("NonDeliveryRunMDBDAOImpl::isDrsNoAugogeneratedForFranchisee occured:"
					+e.getMessage());
		}finally{
			session.flush();
			session.close();
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getNDRS(NonDeliveryRunTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NonDeliveryRunDO> getNDRS(NonDeliveryRunTO to)
	throws CGBusinessException, CGSystemException {
		List<NonDeliveryRunDO> ndoList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(NonDeliveryRunDO.class);
			criteria.add(Restrictions.eq("runSheetNum", to.getDrsNumSearch()));
			criteria.add(Restrictions.eq("consgStatus", DRSConstants.ACTIVE_STATUS));// only active records to be fetched
			if(to.getHeaderFileName().equalsIgnoreCase(DRSConstants.BRANCH)){
				if(to.getBranchCode() != null && 
						StringUtils.hasText(to.getBranchCode())){
					criteria.add(Restrictions.eq("branch.officeId", Integer.valueOf(to.getBranchId())));
				}
			}
			if(to.getHeaderFileName().equalsIgnoreCase(DRSConstants.FRANCHISEE)){
				if(to.getFranchiseCode() != null && 
						StringUtils.hasText(to.getFranchiseCode())){
					criteria.add(Restrictions.eq("franchisee.franchiseeId", Integer.valueOf(to.getFranchiseeId())));
				} 
			}
			ndoList = criteria.list();

		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getNDRS occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return ndoList;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getPreviousReason(CGBaseTO, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ReasonDO getPreviousReason(CGBaseTO to, String aConsgNumber)
	throws CGBusinessException, CGSystemException {

		DeliveryRunTO drto = null;
		NonDeliveryRunTO ndrto = null;
		StringBuilder query = new StringBuilder("from com.dtdc.domain.transaction.delivery.NonDeliveryRunDO ndo ");
		ReasonDO rdo = null;
		//this variable is a flag to discriminate the branch or franchisee. 
		String headerFileName = "";
		List<NonDeliveryRunDO> consignDetls = new ArrayList<NonDeliveryRunDO>();

		if(to instanceof DeliveryRunTO){
			drto = (DeliveryRunTO)to;
			headerFileName = drto.getHeaderFileName();
		} else if(to instanceof NonDeliveryRunTO){
			ndrto = (NonDeliveryRunTO)to;
			headerFileName = ndrto.getHeaderFileName();
		}

		if(headerFileName != null && headerFileName.equalsIgnoreCase("branch")){
			Object[] values = new Object[2];
			String[] params = {"conNum","branchId"};
			if(to instanceof DeliveryRunTO){
				values[0] = aConsgNumber;
				values[1] =Integer.valueOf(drto.getBranchId());

			} else if(to instanceof  NonDeliveryRunTO) {
				values[0] = aConsgNumber;
				values[1] =Integer.valueOf(ndrto.getBranchId());
			}
			query.append(" where conNum =:conNum and branch.officeId=:branchId");
			consignDetls  = getHibernateTemplate().findByNamedParam( query.toString(),params, values);

		} else if(headerFileName != null && headerFileName.equalsIgnoreCase("franchisee")) {
			String[] params = {"conNum","branchId", "franchiseeId"};
			Object[] values = new Object[3];
			if(to instanceof DeliveryRunTO){
				//values[0] = drto.getFdmNo(); 
				values[0] =	aConsgNumber;
				values[1] = Integer.valueOf(drto.getBranchId());
				values[2] =Integer.valueOf(drto.getFranchiseeId());

			} else if(to instanceof  NonDeliveryRunTO) {
				//values[0] = ndrto.getFdmNo(); 
				values[0] =	aConsgNumber;
				values[1] = Integer.valueOf(ndrto.getBranchId());
				values[2] =Integer.valueOf(ndrto.getFranchiseeId());
			}
			query.append(" where conNum =:conNum and branch.officeId=:branchId and franchisee.franchiseeId=:franchiseeId");
			consignDetls  = getHibernateTemplate().findByNamedParam( query.toString(),params, values);
		}

		if(consignDetls.size() > 0)	{
			NonDeliveryRunDO ndrdo = consignDetls.get(0);
			rdo=ndrdo.getReason();
		}
		return rdo;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getBdmlessThanDeliveryDate(String[], String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDO> getBdmlessThanDeliveryDate(String[] deliveryDate,
			String[] consgNo) throws CGBusinessException, CGSystemException {

		List<DeliveryDO> dos = new ArrayList<DeliveryDO>();
		StringBuffer hql = new StringBuffer(" from com.dtdc.domain.transaction.delivery.DeliveryDO delivery ");
		hql.append(" WHERE delivery.conNum =:consgNo and delivery.consgStatus=:status and delivery.preparationDate ");
		hql.append(" < :bdmDate and delivery.deliveryStatus in ('O') ");
		Session session = null;	
		Query query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			for (int i = 0; i < consgNo.length; i++) {
				query = session.createQuery(hql.toString());
				query.setString("consgNo", consgNo[i]);
				String[] status = {"A"};
				query.setParameterList("status", status);
				query.setDate("bdmDate", DateFormatterUtil.getDateFromStringYYYYMMDD(deliveryDate[i]));
				dos.addAll(query.list());
			}

		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getBdmlessThanDeliveryDate occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		} finally{
			session.flush();
			session.close();
		}
		return dos;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#getReasonDOByCode(String)
	 */
	@Override
	public ReasonDO getReasonDOByCode(String reasonCode)
	throws CGBusinessException, CGSystemException {

		ReasonDO reasonDO = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			reasonDO = (ReasonDO) session.createCriteria(ReasonDO.class)
			.add(Restrictions.eq("reasonCode", reasonCode))
			.uniqueResult();
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::getReasonDOByCode occured:"
					+e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		return reasonDO;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO#saveOrUpdateAllDeliveryDOs(List)
	 */
	@Override
	public void saveOrUpdateAllDeliveryDOs(
			List<? extends DeliveryDO> deliveryDOs) throws CGBusinessException {
		try {
			if(deliveryDOs != null && !deliveryDOs.isEmpty()){
				getHibernateTemplate().saveOrUpdateAll(deliveryDOs);
			}
		} catch (Exception e) {
			logger.error("NonDeliveryRunMDBDAOImpl::saveOrUpdateAllDeliveryDOs occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

	}
}
