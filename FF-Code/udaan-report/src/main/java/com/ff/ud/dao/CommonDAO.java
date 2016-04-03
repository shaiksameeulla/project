package com.ff.ud.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ff.ud.dto.OfficeCodeManagerDTO;


@Repository
@Transactional
public class CommonDAO {

	@Autowired
	@Qualifier(value="dbServer")
	private SessionFactory sessionFactory;
	

	
	public String getCurrentDateFromSQL() {
		String sql = "select getDate()";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
	    return String.valueOf(query.uniqueResult());
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getUniversities() {
		String sql = "SELECT  university_name FROM University;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
	    return query.list();
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Jobs> getJobs() {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Jobs.class);
		criteria.add(Restrictions.eq("display", Boolean.TRUE));
		return criteria.list();
	}*/
	
	@SuppressWarnings("unchecked")
	public List<OfficeCodeManagerDTO> getDataFromOpsmanCentralToDestHub(){
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(OfficeCodeManagerDTO.class);
		criteria.setMaxResults(10);
		return criteria.list();
	}
	
	
	
	
	
}
