/**
 * 
 */
package src.com.dtdc.mdbDao.manifest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseDAO.CGBaseDAO;
import com.dtdc.domain.manifest.ManifestDO;
import com.dtdc.domain.master.office.OpsOfficeDO;

import src.com.dtdc.constants.ManifestConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentManifestIntlMDBDAOImpl.
 *
 * @author rchalich
 */
public class AgentManifestIntlMDBDAOImpl extends CGBaseDAO implements AgentManifestIntlMDBDAO  {
	
	/** logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AgentManifestIntlMDBDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.AgentManifestIntlMDBDAO#insertOrUpdateAgentManifest(List)
	 */
	@Override
	public String insertOrUpdateAgentManifest(List<ManifestDO> manifestDOList)
	throws CGSystemException {
		String manifestNum = "";
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		StringBuilder manifestDetails = new StringBuilder();
		try {
			for (ManifestDO manifestDO : manifestDOList) {
				hibernateTemplate.saveOrUpdate(manifestDO);
				hibernateTemplate.flush();
				if (StringUtils.isEmpty(manifestNum)) {
					manifestNum = manifestDO.getManifestNumber();
				}
			}
			manifestDetails.append(ManifestConstant.SUCCESS_MSG);
			manifestDetails.append(",");
			manifestDetails.append(manifestNum);
		}catch (Exception ex) {
			logger.error("AgentManifestIntlMDBDAOImpl::insertOrUpdateAgentManifest::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  "+ex.getMessage(),ex);
		}	

		return manifestDetails.toString();
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.AgentManifestIntlMDBDAO#findByManifestNumber(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ManifestDO> findByManifestNumber(String manifestNumber)
			throws CGSystemException {
		List<ManifestDO> agentManifestIntlList = null;
		Session session = null;
		try {
			if ((StringUtils.isNotEmpty(manifestNumber))) {
				
				agentManifestIntlList = new ArrayList<ManifestDO>();
				session = getSession();
				agentManifestIntlList = (List<ManifestDO>) session
						.createCriteria(ManifestDO.class)						
						.add(Restrictions.eq("manifestNumber", manifestNumber))
						.add(Restrictions.eq("manifestType",ManifestConstant.MANIFEST_TYPE_AGAINST_OUTGOING))
						.list();
			}
		}catch (Exception ex) {
			logger.error("AgentManifestIntlMDBDAOImpl::findByManifestNumber::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException("  "+ex.getMessage(),ex);
		} finally {
			session.flush();
			session.close();
		}
		return agentManifestIntlList;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbDao.manifest.AgentManifestIntlMDBDAO#getGatewayOfficesByCountry(Integer, String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OpsOfficeDO> getGatewayOfficesByCountry(Integer originOfficeId,
			String opsOffType) throws CGSystemException {
		ArrayList<OpsOfficeDO> gatewayOffices = null;
		Session session = null;
		try {
			session = getSession();
			gatewayOffices = (ArrayList<OpsOfficeDO>) session
					.createCriteria(OpsOfficeDO.class)
					.add(Restrictions.eq("officeId.officeId", originOfficeId))
					.add(Restrictions.eq("opsOfficeType", opsOffType)).list();
		} catch (Exception e) {
			logger.error("AgentManifestIntlMDBDAOImpl::getGatewayOfficesByCountry::Exception occured:"
					+e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return gatewayOffices;

	}
}
