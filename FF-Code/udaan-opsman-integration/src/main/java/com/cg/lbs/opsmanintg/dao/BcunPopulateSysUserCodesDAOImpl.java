package com.cg.lbs.opsmanintg.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ff.domain.bcun.SystemAuthDO;

// TODO: Auto-generated Javadoc
/**
 * The Class BcunPopulateSysUserCodesDAOImpl.
 */
@SuppressWarnings("unchecked")
public class BcunPopulateSysUserCodesDAOImpl extends HibernateDaoSupport implements BcunPopulateSysUserCodesDAO {

	
	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunPopulateSysUserCodesDAO#retrieveAllSysUserCodes()
	 */
	@Override
	public List<SystemAuthDO> retrieveAllSysUserCodes() {
		String queryName = "getSystemAuthDetailsForAllUsers";
		HibernateTemplate template = getHibernateTemplate();
		return  template.findByNamedQuery(queryName);
	}
}
