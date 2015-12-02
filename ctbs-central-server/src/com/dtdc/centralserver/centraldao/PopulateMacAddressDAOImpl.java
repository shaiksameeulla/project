/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centraldao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dtdc.domain.master.authorization.SystemAuthenticationDO;

// TODO: Auto-generated Javadoc
/**
 * The Class PopulateMacAddressDAOImpl.
 */
@SuppressWarnings("unchecked")
public class PopulateMacAddressDAOImpl extends HibernateDaoSupport implements
		PopulateMacAddressDAO {

	/* (non-Java doc)
	 * @see com.dtdc.centralserver.centraldao.PopulateMacAddressDAO#retrieveAllMacAddress()
	 */
	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.PopulateMacAddressDAO#retrieveAllMacAddress()
	 */
	@Override
	public List<SystemAuthenticationDO> retrieveAllMacAddress() {
		String queryName = "getDetailsForLogin";
		HibernateTemplate template = getHibernateTemplate();
		return  template.findByNamedQuery(queryName);
	}

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.PopulateMacAddressDAO#getSysAuthenticationDOByMac(String, String)
	 */
	@Override
	public SystemAuthenticationDO getSysAuthenticationDOByMac(String userInfo, String mac) {
		String queryName = "getSysAuthDOByMac";
		String[] params = {"userInfo","macAddress"};
		String[] values = {userInfo,mac};
		SystemAuthenticationDO systemAuthenticationDO = null;
		HibernateTemplate template = getHibernateTemplate();
		List<SystemAuthenticationDO> systemAuthnList = template.findByNamedQueryAndNamedParam(queryName, params, values);
		
		if(systemAuthnList != null && !systemAuthnList.isEmpty()){
			systemAuthenticationDO = (SystemAuthenticationDO)systemAuthnList.get(0);
		}
		
		return systemAuthenticationDO;
		
	}

}
