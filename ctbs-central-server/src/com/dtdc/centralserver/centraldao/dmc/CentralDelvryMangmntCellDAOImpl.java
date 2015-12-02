/**
 * 
 */
package com.dtdc.centralserver.centraldao.dmc;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.to.dmc.DMCFranchiseeDirectEmpTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralDelvryMangmntCellDAOImpl.
 *
 * @author mohammes
 */
public class CentralDelvryMangmntCellDAOImpl extends HibernateDaoSupport implements
		CentralDelvryMangmntCellDAO {

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.dmc.CentralDelvryMangmntCellDAO#findFdmDetails(DMCFranchiseeDirectEmpTO)
	 */
	@Override
	public List findFdmDetails(
			DMCFranchiseeDirectEmpTO dMCFranchiseeDirectEmpTO)
	throws CGSystemException {
			Integer franchEmpId = dMCFranchiseeDirectEmpTO.getFranchiseeID();
			Date fromDate=dMCFranchiseeDirectEmpTO.getFromDate();
			Date toDate=dMCFranchiseeDirectEmpTO.getToDate();
			List deliveryFDMInformation = null;
			

				String[] params = {"franchEmpId",
						"deliverydateFrom","deliverydateTo" };

				Object[] values = { franchEmpId, fromDate,toDate };



				deliveryFDMInformation = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						"getDeliveryFranchiseeDispInfo", params, values);

		

			return deliveryFDMInformation;
		}

	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.dmc.CentralDelvryMangmntCellDAO#getDeliveredInfo(DMCFranchiseeDirectEmpTO)
	 */
	@Override
	public List getDeliveredInfo(DMCFranchiseeDirectEmpTO dmcTo)
			throws CGSystemException {
		Integer franchEmpId = dmcTo.getFranchiseeID();
		
		Date deliveryDate=dmcTo.getToDate();
		dmcTo.getToDate();
				List delivered = null;
			String[] params = {"franchEmpId",
					"deliveryDate" };

			Object[] values = { franchEmpId,deliveryDate };
			delivered = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					"getDeliveredDataByfr", params, values);
		return delivered;
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centraldao.dmc.CentralDelvryMangmntCellDAO#getPreparedInfo(DMCFranchiseeDirectEmpTO)
	 */
	@Override
	public List getPreparedInfo(DMCFranchiseeDirectEmpTO dmcTo)
			throws CGSystemException {
		Integer franchEmpId = dmcTo.getFranchiseeID();
		dmcTo.getFromDate();
		dmcTo.getToDate();
		Date preparationDate=dmcTo.getToDate();
		
		List prepared = null;
		String[] params = {"franchEmpId",
				"preparationDate" };
			Object[] values = { franchEmpId, preparationDate };
			prepared = getHibernateTemplate()
			.findByNamedQueryAndNamedParam(
					"getPreparedDataByfr", params, values);
		return prepared;
	}
	}


