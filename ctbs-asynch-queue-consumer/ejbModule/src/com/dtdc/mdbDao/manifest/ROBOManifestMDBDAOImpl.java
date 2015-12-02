/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import src.com.dtdc.constants.ManifestConstant;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.manifest.ManifestTypeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ROBOManifestMDBDAOImpl.
 *
 * @author narmdr
 */
public class ROBOManifestMDBDAOImpl  extends CGBaseDAO implements ROBOManifestMDBDAO {


	/** logger. */
	private final static Logger logger = LoggerFactory.getLogger(PacketManifestMDBDAOImpl.class);

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.ROBOManifestMDBDAO#getManifestType(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ManifestTypeDO getManifestType(String manifestType)
	throws CGSystemException {
		List<ManifestTypeDO> manifestTypeDOList = null;
		String paramNames = "mnfstCode";
		Object values = manifestType;
		ManifestTypeDO manifestTypeObject = null;
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			manifestTypeDOList = new ArrayList<ManifestTypeDO>();
			manifestTypeDOList = hibernateTemplate
			.findByNamedQueryAndNamedParam(
					ManifestConstant.GET_MANIFEST_TYPE_QUERY,
					paramNames, values);
			for (ManifestTypeDO manifestTypeDO : manifestTypeDOList) {
				manifestTypeObject = manifestTypeDO;
			}
			hibernateTemplate.flush();
		} catch (Exception ex) {
			logger.error("ROBOManifestMDBDAOImpl::getManifestType::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException(" " + ex.getMessage(), ex);
		}
		return manifestTypeObject;
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.ROBOManifestMDBDAO#saveOrUpdateROBOCheckList(List)
	 */
	@Override
	public void saveOrUpdateROBOCheckList(List<ManifestDO> manifestDOList)
	throws CGSystemException {
		try{
			for(ManifestDO manifestDO :manifestDOList ){
				getHibernateTemplate().saveOrUpdate(manifestDO);
			}	
		}catch(Exception e){
			logger.error("ROBOManifestMDBDAOImpl::saveOrUpdateROBOCheckList()" + e.getMessage());
			throw new CGSystemException(e);
		}

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.ROBOManifestMDBDAO#getManifestIdByMfstNoMfstTypeIdMfstTypeConsgNo(String, Integer, String, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getManifestIdByMfstNoMfstTypeIdMfstTypeConsgNo(
			String manifestNo, Integer manifestTypeId, String manifestType,
			String consgNo) throws CGSystemException {
		List<ManifestDO> manifestDOList = new ArrayList<ManifestDO>();
		Integer manifestId = null;
		Session session = null;
		Criteria criteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(ManifestDO.class);
			criteria.add(Restrictions.eq("manifestNumber", manifestNo));
			criteria.add(Restrictions.eq("mnsftTypes.mnfstTypeId", manifestTypeId));
			criteria.add(Restrictions.eq("manifestType", manifestType));
			criteria.add(Restrictions.eq("consgNumber", consgNo));
			manifestDOList = criteria.list();
			if(manifestDOList!=null && manifestDOList.size()>0) {
				manifestId = manifestDOList.get(0).getManifestId();
			}
		}
		catch(Exception e){
			logger.error("ROBOManifestMDBDAOImpl::getManifestIdByMfstNoMfstTypeIdMfstTypeConsgNo()" + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			if(session!=null){
				session.flush();
				session.close();				
			}
		}
		return manifestId;
	}

}
