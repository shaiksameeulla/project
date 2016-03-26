package com.ff.web.manifest.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;

/**
 * The Class BPLOutManifestDoxDAOImpl.
 */
public class BPLOutManifestDoxDAOImpl extends CGBaseDAO implements
		BPLOutManifestDoxDAO {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestDoxDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#getBPLForDoxDetails(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getBPLForDoxDetails(String manifestNumber)throws CGSystemException {

		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getBPLForDoxDetails() :: Start --------> ::::::");
		String paramNames[] = { OutManifestConstants.MANIFEST_NO,
				OutManifestConstants.PROCESS_ID };
		Integer processId = 4;
		Object values[] = { manifestNumber, processId };
		List<ManifestDO> outManifestDOList = null;
		try{
			 outManifestDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							OutManifestConstants.QRY_GET_BPL_DTLS_BY_BPL_NO,
							paramNames, values);
		}catch(Exception e){
			LOGGER.error("BPLOutManifestDoxDAOImpl :: getEmbededManifestDtl() :: Exception"
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getBPLForDoxDetails() :: End --------> ::::::");
		return outManifestDOList;

	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#getEmbededManifestDtl(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getEmbededManifestDtl(String bplNo)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getEmbededManifestDtl() :: Start --------> ::::::");
		String paramNames[] = { OutManifestConstants.MANIFEST_NO,
				OutManifestConstants.PROCESS_ID };
		Integer processId = 3;
		Object values[] = { bplNo, processId };

		List<ManifestDO> outManifestDOList= null;
		try{
		outManifestDOList= getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						OutManifestConstants.QRY_GET_EMBEDED_MANIFEST_DTLS,
						paramNames, values);
		}catch(Exception e){
			LOGGER.error("BPLOutManifestDoxDAOImpl :: getEmbededManifestDtl() Exception "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getEmbededManifestDtl() :: End --------> ::::::");
		return outManifestDOList;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#saveOrUpdateBPLOutManifestDox(java.util.List)
	 */
	public List<ManifestDO> saveOrUpdateBPLOutManifestDox(List<ManifestDO> manifestDO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox() :: Start --------> ::::::");
		try {
			for (ManifestDO manifest : manifestDO) {
				
				getHibernateTemplate().saveOrUpdate(manifest);
			}
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox()..:"
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox() :: End --------> ::::::");
		return manifestDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#saveOrUpdateManifestProcessDtls(java.util.List)
	 */
	/*public boolean saveOrUpdateManifestProcessDtls(
			List<ManifestProcessDO> manifestProcessDOs)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateManifestProcessDtls() :: Start --------> ::::::");
		boolean isBPLBookingAdded = Boolean.FALSE;
		try {
			for (ManifestProcessDO manifestProcess : manifestProcessDOs) {
				getHibernateTemplate().saveOrUpdate(manifestProcess);
			}
			isBPLBookingAdded = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: saveOrUpdateManifestProcessDtls()..:: Exception"
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateManifestProcessDtls() :: End --------> ::::::");
		return isBPLBookingAdded;
	}*/


	
	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#updateManifestDtls(com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public boolean updateManifestDtls(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: updateManifestDtls() :: Start --------> ::::::");
		boolean isUpdated = Boolean.FALSE ;
		Session session=null;
		try {
			 session = getHibernateTemplate().getSessionFactory()
					.openSession();
			Query query = session
					.getNamedQuery(ManifestUniversalConstants.QRY_UPDATE_MANIFEST_DETAILS);
			query.setDouble(ManifestUniversalConstants.MANIFEST_WEIGHT,
					manifestDO.getManifestWeight());
			query.setInteger(ManifestUniversalConstants.MANIFEST_EMBEDDED_IN, manifestDO.getManifestEmbeddedIn());
			
			query.setInteger(ManifestUniversalConstants.MANIFEST_ID,
					manifestDO.getManifestId());
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: updateManifestWeight()..:: Exception"
					+ e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: updateManifestDtls() :: End --------> ::::::");
		return isUpdated;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#searchManifestDtls(com.ff.manifest.ManifestInputs)
	 */
	@SuppressWarnings("unchecked")
	public ManifestDO searchManifestDtls(ManifestInputs manifestTO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: searchManifestDtls() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.createAlias("originOffice", "officeDO");
			criteria.add(Restrictions.eq("officeDO.officeId",
					manifestTO.getLoginOfficeId()));
			criteria.add(Restrictions.in("manifestNo",
					new Object[]{manifestTO.getManifestNumber()}));
			criteria.add(Restrictions.eq("manifestProcessCode",
					manifestTO.getManifestProcessCode()));
			criteria.add(Restrictions.eq("manifestType",
					manifestTO.getManifestType()));
			if (StringUtils.isNotEmpty(manifestTO.getDocType())) {
				criteria.createAlias("manifestLoadContent", "loadContent");
				criteria.add(Restrictions.eq("loadContent.consignmentCode",
						manifestTO.getDocType()));
			}
			List<ManifestDO> manifestDOs = (List<ManifestDO>) criteria.list();
			if(!StringUtil.isEmptyColletion(manifestDOs)  ){
				manifestDO=manifestDOs.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: searchManifestDtls()..Exception "
					+ e);
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: searchManifestDtls() :: End --------> ::::::");
		return manifestDO;
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO searchManifestDtlsbyOfficLogin(ManifestInputs manifestInputsTO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: searchManifestDtlsbyOfficLogin() :: Start --------> ::::::");
		ManifestDO manifestDo = null;
		String paramNames[] = { ManifestConstants.MANIFEST_NO,
				ManifestConstants.PROCESS_CODE,ManifestConstants.MANIFEST_STATUS };
		
		Object values[] = { manifestInputsTO.getManifestNumber(),manifestInputsTO.getManifestProcessCode(),ManifestConstants.MANIFEST_STATUS_CODE};
		try {
		List<ManifestDO> outManifestDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ManifestConstants.QRY_GET_MANIFESTED_DTLS_BY_MANIFESTNO_STATUS_PROCESSCODE,
						paramNames, values);
		if(outManifestDOList!=null && outManifestDOList.size()>0){
			manifestDo=outManifestDOList.get(0);
		}
	} catch (Exception e) {
		LOGGER.error("BPLOutManifestDoxDAOImpl :: searchManifestDtlsbyOfficLogin()..Exception "
				+ e);
		throw new CGSystemException(e);
	}
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: searchManifestDtlsbyOfficLogin() :: End --------> ::::::");
		return manifestDo;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#getEmbeddedManifestDtlsByEmbeddedId(com.ff.manifest.ManifestInputs)
	 */
	@SuppressWarnings("unchecked")
	public List<ManifestDO> getEmbeddedManifestDtlsByEmbeddedId(
			ManifestInputs manifestTO) throws CGSystemException {
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getEmbeddedManifestDtlsByEmbeddedId() :: Start --------> ::::::");
		List<ManifestDO> manifestDOs = null;
		try {
			manifestDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_GET_EMBEDDED_MANIFEST_DTLS_BY_EMBEDDED_ID,
							"manifestEmbeddedId",
							manifestTO.getManifestId());

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BPLOutManifestDoxDAOImpl::getEmbeddedManifestDtlsByEmbeddedId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getEmbeddedManifestDtlsByEmbeddedId() :: End --------> ::::::");
		return manifestDOs;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.manifest.dao.BPLOutManifestDoxDAO#getManifestProcessDtls(com.ff.manifest.ManifestInputs)
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public List<ManifestProcessDO> getManifestProcessDtls(
			ManifestInputs manifestTO) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getManifestProcessDtls() :: Start --------> ::::::");
		List<ManifestProcessDO> manifestProcessDOs = null;
		try {
			String[] paramNames = { ManifestUniversalConstants.MANIFEST_ID,
					ManifestUniversalConstants.ORIGIN_OFFICE_ID,
					ManifestUniversalConstants.MANIFEST_TYPE,
					ManifestUniversalConstants.MANIFEST_PROCESS_CODE,
					ManifestUniversalConstants.MANIFEST_DIRECTION };
			Object[] values = { manifestTO.getManifestId(),
					manifestTO.getLoginOfficeId(),
					manifestTO.getManifestType(),
					manifestTO.getManifestProcessCode(),
					manifestTO.getManifestDirection() };
			manifestProcessDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestUniversalConstants.QRY_GET_BPL_MANIFEST_PROCESS_DTLS,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: getManifestProcessDtls()..Exception "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getManifestProcessDtls() :: End --------> ::::::");
		return manifestProcessDOs;
	}*/

	/*@SuppressWarnings("unchecked")
	@Override
	public ManifestProcessDO isManifestComailed(ManifestInputs manifestInputsTO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: isManifestComailed() :: Start --------> ::::::");
		ManifestProcessDO manifestProcessDO = null;
		String paramNames[] = { ManifestConstants.MANIFEST_NO,
				ManifestConstants.LOGIN_OFFICE_ID,ManifestConstants.MANIFEST_STATUS,ManifestConstants.PROCESS_CODE };
		
		Object values[] = { manifestInputsTO.getManifestNumber(), manifestInputsTO.getLoginOfficeId(),ManifestConstants.MANIFEST_STATUS_CODE,manifestInputsTO.getManifestProcessCode(),};
		try {
		List<ManifestProcessDO> outManifestDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						OutManifestConstants.QRY_GET_MANIFESTPROCESS_DTLS_FOR_COMAIL,
						paramNames, values);
		if(outManifestDOList!=null && outManifestDOList.size()>0){
			manifestProcessDO=outManifestDOList.get(0);
		}
	} catch (Exception e) {
		LOGGER.error("BPLOutManifestDoxDAOImpl :: isManifestComailed()..:: Exception "
				+ e);
		throw new CGSystemException(e);
	}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: isManifestComailed() :: End --------> ::::::");
		return manifestProcessDO;
	}
*/
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO searchManifestDtlsFromInManifestDox(
			ManifestInputs manifestTO) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: searchManifestDtlsFromInManifestDox() :: Start --------> ::::::");
		ManifestDO manifestDo = null;
		String paramNames[] = { ManifestConstants.MANIFEST_NO,ManifestConstants.LOGIN_OFFICE_ID, 
				ManifestConstants.PROCESS_CODE,ManifestConstants.MANIFEST_TYPE };
		
		Object values[] = { manifestTO.getManifestNumber(),manifestTO.getLoginOfficeId(),manifestTO.getManifestProcessCode(),ManifestConstants.MANIFEST_TYPE_IN};
		try {
		List<ManifestDO> outManifestDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ManifestConstants.QRY_GET_IN_MANIFESTED_DTLS_4_BPL_OUT_MANIFESTNO_LOGIN_OFFICEID,
						paramNames, values);
		if(outManifestDOList!=null && outManifestDOList.size()>0){
			manifestDo=outManifestDOList.get(0);
		}
	} catch (Exception e) {
		LOGGER.error("BPLOutManifestDoxDAOImpl :: searchManifestDtlsbyOfficLogin()..:: Exception "
				+ e);
		throw new CGSystemException(e);
	}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: searchManifestDtlsFromInManifestDox() :: End --------> ::::::");
		return manifestDo;
	}
	
	public Boolean saveOrUpdateBPLOutManifestDox4InManifestBagDtls(ManifestDO manifestDO)
			throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox4InManifestBagDtls() :: Start --------> ::::::");
		Boolean flag=Boolean.FALSE;
		try {
			
			getHibernateTemplate().saveOrUpdate(manifestDO);
			flag=Boolean.TRUE;
			
		} catch (Exception e) {
			 flag=Boolean.FALSE;
			LOGGER.error("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox4InManifestBagDtls()..:: Exception "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: saveOrUpdateBPLOutManifestDox4InManifestBagDtls() :: End --------> ::::::");
		return flag;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ManifestDO> getManifestDtlsByManifestNoAndStatus(
			ManifestInputs manifestInputsTO) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: getManifestDtlsByManifestNoAndStatus() :: Start --------> ::::::");
		List<ManifestDO> manifestDo = null;
		String paramNames[] = { ManifestConstants.MANIFEST_NO,ManifestConstants.MANIFEST_TYPE, ManifestConstants.LOGIN_OFFICE_ID };
				
		Object values[] = { manifestInputsTO.getManifestNumber(),manifestInputsTO.getManifestTypeList(),manifestInputsTO.getLoginOfficeId()};
		try {
		
			manifestDo = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ManifestConstants.QRY_GET_MANIFEST_DTLS_BY_MANIFEST_NO_AND_STATUS,
						paramNames, values);
		
	} catch (Exception e) {
		LOGGER.error("BPLOutManifestDoxDAOImpl :: getManifestDtlsByManifestNoAndStatus()..:: Exception "
				+ e);
		throw new CGSystemException(e);
	}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getManifestDtlsByManifestNoAndStatus() :: End --------> ::::::");
		return manifestDo;
	}

	@Override
	public Object[] isManifestNumInManifested(ManifestInputs manifestInputs)
			throws CGSystemException {
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: isManifestNumInManifested() :: Start --------> ::::::");

		Object[] manifestDtlsByPktNo = null;
		try {
			List<Object[]> manifestDtlsByPktNoList = (List<Object[]>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getBagManifestDetailsByPacketNo",
							new String[] { "manifestNumber", "operatingOffice"},
							new Object[] {
									manifestInputs.getManifestNumber(),
									manifestInputs.getLoginOfficeId()});
			if (!StringUtil.isEmptyColletion(manifestDtlsByPktNoList)) {
				manifestDtlsByPktNo = manifestDtlsByPktNoList.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BPLOutManifestDoxDAOImpl::isManifestNumInManifested() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: isManifestNumInManifested() :: End --------> ::::::");
		return manifestDtlsByPktNo;
	
	}

	@Override
	public Integer getOutManifestDestnOfficeId(Integer officeId,Integer manifestId)
			throws CGSystemException {
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getOutManifestDestnOfficeId() :: Start --------> ::::::");
		
		Integer offcId = null;
		try {
			@SuppressWarnings("unchecked")
			List<Integer> destinIds =  getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							ManifestConstants.QRY_IS_OUT_MANIFEST_DESTN_ID,
							new String[] {"OfficeId", "manifestId",  },
							new Object[] {officeId,manifestId});

			if(destinIds!=null && destinIds.size()>0){
				offcId=destinIds.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BPLOutManifestDoxDAOImpl::getOutManifestDestnOfficeId() ::Exception "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getOutManifestDestnOfficeId() :: End --------> ::::::");
		return offcId;
	
	}

	@Override
	@SuppressWarnings("unchecked")
	public ManifestDO getDirectManifestPktByManifestNo(
			ManifestInputs manifestInputsTO) throws CGSystemException {
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getDirectManifestPktByManifestNo() :: Start --------> ::::::");
		ManifestDO manifestDo = null;
		String paramNames[] = { ManifestConstants.MANIFEST_NO,ManifestConstants.MANIFEST_TYPE ,OutManifestConstants.MANIFEST_OPEN_TYPE};
		Object values[] = { manifestInputsTO.getManifestNumber(),manifestInputsTO.getManifestTypeList(),ManifestConstants.OPEN_MANIFEST };
		try {
		List<ManifestDO> manifestDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						ManifestConstants.QRY_GET_DIRECT_MANIFEST_DTLS_BY_MANIFEST_NO_AND_STATUS,
						paramNames, values);
		if(manifestDOList!=null && manifestDOList.size()>0){
			manifestDo=manifestDOList.get(0);
		}
	} catch (Exception e) {
		LOGGER.error("BPLOutManifestDoxDAOImpl :: getDirectManifestPktByManifestNo()..:: Exception "
				+ e);
		throw new CGSystemException(e);
	}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: getDirectManifestPktByManifestNo() :: End --------> ::::::");
		return manifestDo;
	}

	@Override
	public ManifestMappedEmbeddedDO saveOrUpdateEmbeddedManifest(
			ManifestMappedEmbeddedDO manifestDtls) throws CGSystemException {
		LOGGER.trace("BPLOutManifestDoxDAOImpl :: saveOrUpdateEmbeddedManifest() :: Start --------> ::::::");
		try {
				
			getHibernateTemplate().saveOrUpdate(manifestDtls);
			
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxDAOImpl :: saveOrUpdateEmbeddedManifest()..:: Exception "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BPLOutManifestDoxDAOImpl :: saveOrUpdateEmbeddedManifest() :: End --------> ::::::");
		return manifestDtls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> getDirectPacketManifests(
			List<String> manifestNOList)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifests() :: START");
		List<ManifestDO> manifestDOs = null;
		try {
			String paramNames[] = { ManifestConstants.MANIFEST_NO,ManifestConstants.MANIFEST_TYPE };
			List<String> manifestTypeList= new ArrayList<>();
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_OUT);
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_IN );
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_BRANCH_MISROUTE ); // Branch Misroute
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_RTH ); // RTH MANIFEST

			Object values[] = { manifestNOList,manifestTypeList };
			manifestDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					ManifestConstants.QRY_GET_DIRECT_MANIFESTS_BY_MANIFESTNOS,
					paramNames, values);
		} catch (Exception e) {
			LOGGER.error("ERROR :: ManifestCommonDAOImpl :: getManifests() ::: Exception ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifests() :: END");
		return manifestDOs;
	}

	@Override
	public ManifestDO getManifestByManifestID(Integer manifestId)
			throws CGSystemException {
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestByManifestID() :: START");
		Session session = null;
		ManifestDO manifestDO = null;
		try {
			session = createSession();
			manifestDO = (ManifestDO) session.createCriteria(ManifestDO.class).add(Restrictions.eq("manifestId", manifestId)).uniqueResult();
		
		}catch (Exception e) {
			LOGGER.error("ERROR :: ManifestCommonDAOImpl :: getManifestByManifestID() ::",
					e);
			throw new CGSystemException(e);
		} finally{
			session.close();
		}
		LOGGER.debug("ManifestCommonDAOImpl :: getManifestByManifestID() :: END");
		return manifestDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGSystemException {
		LOGGER.trace("InManifestCommonDAOImpl::getManifestDetailsWithFetchProfile::START------------>:::::::");
		Session session = null;
		ManifestDO manifestDO = null;
		Criteria criteria = null;
		try {
			session = createSession();
			enableFetchProfileInSession(manifestBaseTO, session);

			criteria = session.createCriteria(ManifestDO.class);

			if (!StringUtil.isEmptyInteger(manifestBaseTO.getManifestId())) {
				criteria.add(Restrictions.eq("manifestId",
						manifestBaseTO.getManifestId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestNumber())) {
				criteria.add(Restrictions.eq("manifestNo",
						manifestBaseTO.getManifestNumber()));
			}
			if (!StringUtil.isEmptyInteger(manifestBaseTO
					.getLoggedInOfficeId())) {
				criteria.add(Restrictions.eq("operatingOffice",
						manifestBaseTO.getLoggedInOfficeId()));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getProcessCode())) {
				criteria.add(Restrictions.in(
						"manifestProcessCode",
						manifestBaseTO.getProcessCode().split(
								CommonConstants.COMMA)));
			}
			if (StringUtils.isNotBlank(manifestBaseTO.getManifestType())) {
				if (manifestBaseTO.getIsExcludeManifestType()) {
					criteria.add(Restrictions.ne("manifestType",
							manifestBaseTO.getManifestType()));
				} else {
					criteria.add(Restrictions.eq("manifestType",
							manifestBaseTO.getManifestType()));
				}
			}
			criteria.addOrder(Order.desc("manifestDate"));
			criteria.setMaxResults(1);
			List<ManifestDO> manifestDOs = criteria.list();

			manifestDO = !StringUtil.isEmptyList(manifestDOs) ? manifestDOs
					.get(0) : null;
			initializeSetByHib(manifestBaseTO, manifestDO);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::InManifestCommonDAOImpl::getManifestDetailsWithFetchProfile() :: "
					+ e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		LOGGER.debug("InManifestCommonDAOImpl :: getManifestDetailsWithFetchProfile() :: End --------> ::::::");
		return manifestDO;
	}

	private void initializeSetByHib(ManifestBaseTO manifestBaseTO,
			ManifestDO manifestDO) {
		if (manifestDO != null) {
			if (manifestBaseTO.getIsFetchProfileManifestEmbedded()) {
				Hibernate.initialize(manifestDO.getEmbeddedManifestDOs());
			}
			if (manifestBaseTO.getIsFetchProfileManifestParcel()) {
				Hibernate.initialize(manifestDO.getConsignments());
			}
			if (manifestBaseTO.getIsFetchProfileManifestDox()) {
				Hibernate.initialize(manifestDO.getConsignments());
				Hibernate.initialize(manifestDO.getComails());
			}
		}
	}
	
	private void enableFetchProfileInSession(ManifestBaseTO manifestBaseTO,
			Session session) {
		if (manifestBaseTO.getIsFetchProfileManifestEmbedded()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Embedded);
		}
		if (manifestBaseTO.getIsFetchProfileManifestParcel()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Parcel);
		}
		if (manifestBaseTO.getIsFetchProfileManifestDox()) {
			session.enableFetchProfile(InManifestConstants.Fetch_Profile_Manifest_Dox);
		}
	}
}