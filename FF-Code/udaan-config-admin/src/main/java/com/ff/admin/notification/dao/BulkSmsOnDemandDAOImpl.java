package com.ff.admin.notification.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.notification.BulkSmsOnDemandTO;

public class BulkSmsOnDemandDAOImpl extends CGBaseDAO implements BulkSmsOnDemandDAO {
	private final static Logger LOGGER = LoggerFactory.getLogger(BulkSmsOnDemandDAOImpl.class);
	@Override
	public List<Object[]> getConsignmentDetailsByStatus(
			BulkSmsOnDemandTO smsOnDemandTO) throws CGSystemException {
		LOGGER.debug("BulkSmsOnDemandDAOImpl :: getConsignmentDetailsByStatus :: Start ----------->");
		List<Object[]> consignmentDtls = null;
		try {
			String qryName = "";
			String[] params = {"officeId","fromDate","toDate"};
			Object[] values = {smsOnDemandTO.getOfficeId(),DateUtil.stringToDDMMYYYYFormat(smsOnDemandTO.getFromDate()),DateUtil.stringToDDMMYYYYFormat(smsOnDemandTO.getToDate()) };
			if(StringUtils.equalsIgnoreCase(smsOnDemandTO.getCnStatus(), CommonConstants.CONSIGNMENT_STATUS_RTH)) {
				qryName = "getRTHedConsignmentsForBulkSMS";
			} else if(StringUtils.equalsIgnoreCase(smsOnDemandTO.getCnStatus(), CommonConstants.CONSIGNMENT_STATUS_PENDING)){
				qryName = "getPendingConsignmentsForBulkSMS";
			}
			consignmentDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(qryName, params, values);
		} catch (Exception e) {
			LOGGER.error("BulkSmsOnDemandDAOImpl :: getConsignmentDetailsByStatus :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BulkSmsOnDemandDAOImpl :: getConsignmentDetailsByStatus :: Error ----------->");
		return consignmentDtls;
	}

}
