package com.ff.umc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.ApproverUserOfficeDO;
import com.ff.domain.umc.AssignApproverDO;
import com.ff.umc.AssignApproverTO;
import com.ff.universe.constant.UdaanCommonConstants;

public class AssignApproverDAOImpl extends CGBaseDAO implements AssignApproverDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AssignApproverDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplScreenDO> getAllAssignApplScreens()
			throws CGSystemException {
		
		LOGGER.trace("AssignApproverDAOImpl :: getAllAssignApplScreens() :: Start --------> ::::::");
		 
		List<ApplScreenDO> applScreenDOList = null;
		try {
			applScreenDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_ALL_ASSIGN_APPL_SCREENS,
							new String[] {
									UdaanCommonConstants.QRY_PARAM_SCR_ASSIGN},
							new Object[] {UdaanCommonConstants.QRY_PARAM_VALUE_SCR_ASSIGN});

		} catch (Exception e) {
			LOGGER.error("ERROR : AssignApproverDAOImpl.getAllAssignApplScreens", e);
			throw new CGSystemException(e);
		}
		
		LOGGER.trace("AssignApproverDAOImpl :: getAllAssignApplScreens() :: End --------> ::::::");
		
		return applScreenDOList;

	}

	@Override
	public boolean saveOrUpdateAssignApprover(
			List<AssignApproverDO> assignApproverDOList,
			List<ApproverUserOfficeDO> approverUserOfficeDOList)
			throws CGSystemException {
		
		LOGGER.trace("AssignApproverDAOImpl :: saveOrUpdateAssignApprover() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isAssigned = Boolean.FALSE;
		List<AssignApproverDO> assignAppDOList = new ArrayList<AssignApproverDO>();
		List<ApproverUserOfficeDO> approverUserOffDOList = new ArrayList<ApproverUserOfficeDO>();
		AssignApproverTO assignApproverTO = null;
		List<Integer> newScreenList =null;
		List<Integer> newOfficList =null;
		List<Integer> newCityList =null;
		List<Integer> newRegionalOffcIds = null;
		List<Integer> oldScreenIds =null;
		List<Integer> oldOfficIds = null;
		List<Integer> oldCityIds = null;
		List<Integer> oldRegionalOffcIds = null;
		List<String> oldMappedTOs = null;
		Integer newOffcId=0;
		Integer newCityId=0;
		Integer newRegionalOffId=0;
		String newMappedTO ="";
		try{
			session = createSession();
			tx = session.beginTransaction();	
			if(!CGCollectionUtils.isEmpty(assignApproverDOList)){
				
				assignApproverTO = new AssignApproverTO();
				assignApproverTO.setUserId(assignApproverDOList.get(0).getUser());
				//assignAppDOList = getAssignApproverDetails(assignApproverTO);
				assignAppDOList = getOldAssignApproverDetails(assignApproverTO);
				
				newScreenList = new ArrayList<Integer>();
				for(int i=0;i<assignApproverDOList.size();i++){
					Integer newScreenId = assignApproverDOList.get(i).getScreen();
					newScreenList.add(newScreenId);
					
				}
				
				
				oldScreenIds = new ArrayList<Integer>();
				for(int i=0;i<assignAppDOList.size();i++){
					Integer oldScreenId = assignAppDOList.get(i).getScreen();
					oldScreenIds.add(oldScreenId);
					if(!newScreenList.contains(oldScreenId)&&assignAppDOList.get(i).getActive().equalsIgnoreCase("Y")){
						updateAssignApproverDtls(assignAppDOList.get(i));
					}else if(newScreenList.contains(oldScreenId)&&assignAppDOList.get(i).getActive().equalsIgnoreCase("N")){
						updateExistingAssignApproverDtlsToActive(assignAppDOList.get(i));
					}
					
				}
				
				for (AssignApproverDO assignApproverDO : assignApproverDOList) {
					Integer screenId = assignApproverDO.getScreen();
					if(oldScreenIds.contains(screenId)){
						assignApproverDO.setActive("Y");
						assignApproverDO.setDtToBranch("N");
						//session.saveOrUpdate(assignApproverDO);
						//userRoleObj.getApplRights().add(applRight);
					}else if(!oldScreenIds.contains(screenId)){
						assignApproverDO.setActive("Y");
						assignApproverDO.setDtToBranch("N");
						session.save(assignApproverDO);
					}
				}
				
				
			/*for(AssignApproverDO  assignApproverDO :assignApproverDOList){
				session.merge(assignApproverDO);
				}*/
			}
			
			
			if(!CGCollectionUtils.isEmpty(approverUserOfficeDOList)){
				//approverUserOffDOList = getApproverUserOfficeDetails(assignApproverTO);
				approverUserOffDOList= getOldApproverUserOfficeDetails(assignApproverTO);
				
			
				
				
				
				newRegionalOffcIds = new ArrayList<Integer>();
				newCityList = new ArrayList<Integer>();
				newOfficList = new ArrayList<Integer>();
				for(int i=0;i<approverUserOfficeDOList.size();i++){
					 newOffcId = approverUserOfficeDOList.get(i).getOffice();
					 newMappedTO = approverUserOfficeDOList.get(i).getMappedTo();
					 newCityId = approverUserOfficeDOList.get(i).getCity();
					 newRegionalOffId = approverUserOfficeDOList.get(i).getRegionalOffice();
					 newOfficList.add(newOffcId);
					 newCityList.add(newCityId);
					 newRegionalOffcIds.add(newRegionalOffId);
					 
					 
				}
				
				oldMappedTOs=new ArrayList<String>();
				oldOfficIds=new ArrayList<Integer>();
				oldCityIds=new ArrayList<Integer>();
				oldRegionalOffcIds = new ArrayList<Integer>();
				for(int i=0;i<approverUserOffDOList.size();i++){
					Integer oldOfficId = approverUserOffDOList.get(i).getOffice();
					Integer oldCityId = approverUserOffDOList.get(i).getCity();
					Integer oldRegionalOffId = approverUserOffDOList.get(i).getRegionalOffice();
					 String oldmappedTO = approverUserOffDOList.get(i).getMappedTo();
					 oldOfficIds.add(oldOfficId);
					 oldCityIds.add(oldCityId);
					 oldRegionalOffcIds.add(oldRegionalOffId);
					 oldMappedTOs.add(oldmappedTO);
					//case 1
					if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("O")&&!StringUtil.isNull(newMappedTO)&&newMappedTO.equalsIgnoreCase("O") ){
						//compare the cities
						if(!newCityList.contains(oldCityId)&& approverUserOffDOList.get(i).getActive().equalsIgnoreCase("Y")){
							updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
						}
					}
					//case 2
					else if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("S")&&!StringUtil.isNull(newMappedTO)&&newMappedTO.equalsIgnoreCase("S") ){
						//compare the RHOS
						if(!newRegionalOffcIds.contains(oldRegionalOffId)&& approverUserOffDOList.get(i).getActive().equalsIgnoreCase("Y")){
							updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
						}
					}
					//case 3
					else if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("C")&&!StringUtil.isNull(newMappedTO)&&newMappedTO.equalsIgnoreCase("C") ){
						//no need to save dat do
					}
					else if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("O")
							&&!StringUtil.isNull(newMappedTO)&& (newMappedTO.equalsIgnoreCase("S")
									|| newMappedTO.equalsIgnoreCase("C"))){
							//den make all cities I in old DB
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}
					else if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("S")
							&&!StringUtil.isNull(newMappedTO)&& (newMappedTO.equalsIgnoreCase("O")
									|| newMappedTO.equalsIgnoreCase("C"))){
							//den make all RHOS I in old DB
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}
					else if(!StringUtil.isNull(oldmappedTO)&&oldmappedTO.equalsIgnoreCase("C")
							&&!StringUtil.isNull(newMappedTO)&& (newMappedTO.equalsIgnoreCase("S")
									|| newMappedTO.equalsIgnoreCase("O"))){
							//den make all RHOS I in old DB
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
						
						
					}else if(oldmappedTO==null && !StringUtil.isNull(newMappedTO)){
						//den make old offices null
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}else if(!StringUtil.isNull(oldmappedTO) && newMappedTO==null){
						//den make old records I
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}
					else if(oldmappedTO==null && newMappedTO==null){
						//den compare offices
						
						if(!newOfficList.contains(oldOfficId)&& approverUserOffDOList.get(i).getActive().equalsIgnoreCase("Y")){
							updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
						}else if(newOfficList.contains(oldOfficId)&&approverUserOffDOList.get(i).getActive().equalsIgnoreCase("N")){
							updateExistingAssignApproverOfficDtls(approverUserOffDOList.get(i));
						}
						
					}
						
					
					
					/*if(!newOfficList.contains(oldOfficId)&& approverUserOffDOList.get(i).getActive().equalsIgnoreCase("Y")){
						updateAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}else if(newOfficList.contains(oldOfficId)&&approverUserOffDOList.get(i).getActive().equalsIgnoreCase("N")){
						updateExistingAssignApproverOfficDtls(approverUserOffDOList.get(i));
					}*/
					
				}
				
				//compare the new and old objects with office null
				
				/*for(int i=0;i<newObjectswithOfficNull.size();i++){
					for(int j=0;j<oldObjectswithOfficNull.size();i++){
						
						if(newObjectswithOfficNull.get(i)==oldObjectswithOfficNull.get(j)){
						}else{
							//if some attributes diffrent den check in db it exists by passing the paarmters og old one
							boolean isExists = checkIfTheRecordExists(newObjectswithOfficNull.get(i));
							
							//if exists den only update
							//if not den save
							if(isExists){
								//then dont do anythin provided the active column is Y
							}else{
								//call update
							}
							
							
						}
						
						
						
					}
				}*/
				
				//to save the new mapped to
				for (ApproverUserOfficeDO approverUserOfficeDO : approverUserOfficeDOList) {
					String mappedTO = approverUserOfficeDO.getMappedTo();
					
					
					if(oldMappedTOs.contains(mappedTO)){
						
						if(!StringUtil.isNull(mappedTO)&&mappedTO.equalsIgnoreCase("O")){
							if(oldCityIds.contains(approverUserOfficeDO.getCity())){
								approverUserOfficeDO.setActive("Y");
								approverUserOfficeDO.setDtToBranch("N");
							}
						}else if(!StringUtil.isNull(mappedTO)&&mappedTO.equalsIgnoreCase("S")){
							if(oldRegionalOffcIds.contains(approverUserOfficeDO.getRegionalOffice())){
								approverUserOfficeDO.setActive("Y");
								approverUserOfficeDO.setDtToBranch("N");
							}
						}else if(!StringUtil.isNull(mappedTO)&&mappedTO.equalsIgnoreCase("C")){
							
						}else if(StringUtil.isNull(mappedTO)){
							if(oldOfficIds.contains(approverUserOfficeDO.getOffice())){
								approverUserOfficeDO.setActive("Y");
								approverUserOfficeDO.setDtToBranch("N");
								//session.saveOrUpdate(approverUserOfficeDO);
								//userRoleObj.getApplRights().add(applRight);
							}else if(!oldOfficIds.contains(approverUserOfficeDO.getOffice())){
								approverUserOfficeDO.setActive("Y");
								approverUserOfficeDO.setDtToBranch("N");
								session.save(approverUserOfficeDO);
							}
						}
						
						
					}else if(!oldMappedTOs.contains(mappedTO)){
						approverUserOfficeDO.setActive("Y");
						approverUserOfficeDO.setDtToBranch("N");
						session.save(approverUserOfficeDO);
					}
				}
				
				/*for (ApproverUserOfficeDO approverUserOfficeDO : approverUserOfficeDOList) {
					Integer officeid = approverUserOfficeDO.getOffice();
					if(oldOfficIds.contains(officeid)){
						approverUserOfficeDO.setActive("Y");
						approverUserOfficeDO.setDtToBranch("N");
						//session.saveOrUpdate(approverUserOfficeDO);
						//userRoleObj.getApplRights().add(applRight);
					}else if(!oldOfficIds.contains(officeid)){
						approverUserOfficeDO.setActive("Y");
						approverUserOfficeDO.setDtToBranch("N");
						session.save(approverUserOfficeDO);
					}
				}*/
			/*for(ApproverUserOfficeDO  approverUserOfficeDO :approverUserOfficeDOList){
				session.merge(approverUserOfficeDO);	
				}*/
			}
			tx.commit();
			
				isAssigned = Boolean.TRUE;		
		}
		catch(Exception e){	
			LOGGER.trace("Exception Occured in::AssignApproverDAOImpl::saveOrUpdateAssignApprover :: " + e);
			if(!StringUtil.isNull(tx))
			tx.rollback();
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("AssignApproverDAOImpl :: saveOrUpdateAssignApprover() :: End --------> ::::::");
		return isAssigned;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignApproverDO> getAssignApproverDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: getAssignApproverDetails() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Criteria criteria=null;
		 Session session = null;
			try{
				session = createSession();
				criteria = session.createCriteria(AssignApproverDO.class,"assignApprover");
				
				if(!StringUtil.isEmptyInteger(assignApproverTO.getUserId())){
					criteria.add(Restrictions.eq("assignApprover.user", assignApproverTO.getUserId()));
					criteria.add(Restrictions.eq("assignApprover.active", "Y"));
					}
				
				
				assignApproverDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.getAssignApproverDetails", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: getAssignApproverDetails() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AssignApproverDO> getOldAssignApproverDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: getAssignApproverDetails() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Criteria criteria=null;
		 Session session = null;
			try{
				session = createSession();
				criteria = session.createCriteria(AssignApproverDO.class,"assignApprover");
				
				if(!StringUtil.isEmptyInteger(assignApproverTO.getUserId())){
					criteria.add(Restrictions.eq("assignApprover.user", assignApproverTO.getUserId()));
					//criteria.add(Restrictions.eq("assignApprover.active", "Y"));
					}
				
				
				assignApproverDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.getAssignApproverDetails", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: getAssignApproverDetails() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}
	
	
	public List<AssignApproverDO> updateAssignApproverDtls(AssignApproverDO assignApproverDO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: updateAssignApproverDtls() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Session session = null;
			try{
				session = createSession();
				Query q = session.getNamedQuery("updateAssignApprover");
				q.setString("active", "N");
				q.setInteger("userId", assignApproverDO.getUser());
				q.setInteger("screenId", assignApproverDO.getScreen());
				q.executeUpdate();
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.updateAssignApproverDtls", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: updateAssignApproverDtls() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}
	
	public List<AssignApproverDO> updateExistingAssignApproverDtlsToActive(AssignApproverDO assignApproverDO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: updateExistingAssignApproverDtlsToActive() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Session session = null;
			try{
				session = createSession();
				Query q = session.getNamedQuery("updateAssignApprover");
				q.setString("active", "Y");
				q.setInteger("userId", assignApproverDO.getUser());
				q.setInteger("screenId", assignApproverDO.getScreen());
				q.executeUpdate();
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.updateExistingAssignApproverDtlsToActive", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: updateExistingAssignApproverDtlsToActive() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}
	
	public List<AssignApproverDO> updateExistingAssignApproverOfficDtls(ApproverUserOfficeDO assignApproverOffcDO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: updateExistingAssignApproverOfficDtls() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Session session = null;
			try{
				session = createSession();
				Query q = session.getNamedQuery("updateAssignApproverOffc");
				q.setString("active", "Y");
				q.setInteger("userId", assignApproverOffcDO.getUser());
				q.setInteger("officeId", assignApproverOffcDO.getOffice());
				q.executeUpdate();
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.updateExistingAssignApproverOfficDtls", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: updateExistingAssignApproverOfficDtls() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}
	
	public List<AssignApproverDO> updateAssignApproverOfficDtls(ApproverUserOfficeDO assignApproverOffcDO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: updateAssignApproverOfficDtls() :: START --------> ::::::");
		 List<AssignApproverDO> assignApproverDOList = null;
		 Session session = null;
		 Query q =null;
			try{
				session = createSession();
				if(assignApproverOffcDO.getOffice()!=null){
					 q = session.getNamedQuery("updateAssignApproverOffc");
				}else if(assignApproverOffcDO.getOffice()==null){
					 q = session.getNamedQuery("updateAssignApproverOffcforNoOffic");
				}
				q.setString("active", "N");
				q.setInteger("userId", assignApproverOffcDO.getUser());
				if(assignApproverOffcDO.getMappedTo()==null){
					q.setInteger("officeId", assignApproverOffcDO.getOffice());
				}else if(assignApproverOffcDO.getMappedTo().equalsIgnoreCase("O")){
					q.setString("mappedTO","O");
				}
				else if(assignApproverOffcDO.getMappedTo().equalsIgnoreCase("S")){
					q.setString("mappedTO","S");
				}else if(assignApproverOffcDO.getMappedTo().equalsIgnoreCase("C")){
					q.setString("mappedTO","C");
				}
				q.executeUpdate();
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.updateAssignApproverOfficDtls", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("AssignApproverDAOImpl :: updateAssignApproverOfficDtls() :: End --------> ::::::");
			return assignApproverDOList;	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApproverUserOfficeDO> getApproverUserOfficeDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: getApproverUserOfficeDetails() :: START --------> ::::::");
		 List<ApproverUserOfficeDO> approverUserOfficeDOList = null;
		 Criteria criteria=null;
		 Session session = null;
			try{
				session = createSession();
				criteria = session.createCriteria(ApproverUserOfficeDO.class,"approverUserOffice");
				
				if(!StringUtil.isEmptyInteger(assignApproverTO.getUserId())){
					criteria.add(Restrictions.eq("approverUserOffice.user", assignApproverTO.getUserId()));
					criteria.add(Restrictions.eq("approverUserOffice.active", "Y"));
					}
				
				
				approverUserOfficeDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.getApproverUserOfficeDetails", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("AssignApproverDAOImpl :: getApproverUserOfficeDetails() :: End --------> ::::::");
			return approverUserOfficeDOList;
		
		
	}

	@SuppressWarnings("unchecked")
	public List<ApproverUserOfficeDO> getOldApproverUserOfficeDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException {
		LOGGER.trace("AssignApproverDAOImpl :: getApproverUserOfficeDetails() :: START --------> ::::::");
		 List<ApproverUserOfficeDO> approverUserOfficeDOList = null;
		 Criteria criteria=null;
		 Session session = null;
			try{
				session = createSession();
				criteria = session.createCriteria(ApproverUserOfficeDO.class,"approverUserOffice");
				
				if(!StringUtil.isEmptyInteger(assignApproverTO.getUserId())){
					criteria.add(Restrictions.eq("approverUserOffice.user", assignApproverTO.getUserId()));
					
					}
				
				
				approverUserOfficeDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.getApproverUserOfficeDetails", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("AssignApproverDAOImpl :: getApproverUserOfficeDetails() :: End --------> ::::::");
			return approverUserOfficeDOList;
		
		
	}
	
	/*public boolean checkIfTheRecordExists(ApproverUserOfficeDO approverAssignDO)
			throws CGSystemException{
		
		LOGGER.trace("AssignApproverDAOImpl :: checkIfTheRecordExists() :: START --------> ::::::");
		 List<ApproverUserOfficeDO> approverUserOfficeDOList = null;
		 Criteria criteria=null;
		 Session session = null;
			try{
				session = createSession();
				criteria = session.createCriteria(ApproverUserOfficeDO.class,"approverUserOffice");
				
				if(!StringUtil.isEmptyInteger(approverAssignDO.getUserId())){
					criteria.add(Restrictions.eq("approverUserOffice.user", approverAssignDO.getUserId()));
					}
				
				criteria.add(Restrictions.eq("approverUserOffice.city", approverAssignDO.getCity()));
				criteria.add(Restrictions.eq("approverUserOffice.regionalOffice", approverAssignDO.getRegionalOffice()));
				criteria.add(Restrictions.eq("approverUserOffice.mappedTo", approverAssignDO.getMappedTo()));
				criteria.add(Restrictions.eq("approverUserOffice.active", approverAssignDO.getActive()));
				criteria.add(Restrictions.eq("approverUserOffice.dtToBranch", approverAssignDO.getDtToBranch()));
				
				
				approverUserOfficeDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : AssignApproverDAOImpl.checkIfTheRecordExists", e.getMessage());
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("AssignApproverDAOImpl :: checkIfTheRecordExists() :: End --------> ::::::");
			return approverUserOfficeDOList;
	}*/
	

}
