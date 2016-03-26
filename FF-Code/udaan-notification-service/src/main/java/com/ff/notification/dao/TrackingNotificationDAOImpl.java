package com.ff.notification.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;

public class TrackingNotificationDAOImpl extends CGBaseDAO implements TrackingNotificationDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(TrackingNotificationDAOImpl.class);
	
	
	
	@Override
	public BookingDO getBookingDetailsByCnNumber(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getBookingDetailsByCnNumber::start===>");
		String queryName = "getBookingConsignment";
		List<BookingDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNos", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getBookingDetailsByCnNumber::end===>");
			return cnList.get(0);
		
	}
	
	
	@Override
	public ConsignmentDO getConsgDetailsByConNumber(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getConsgDetailsByConNumber::start===>");
		String queryName = "getConsgDetailsByCnNumber";
		List<ConsignmentDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNumber", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getConsgDetailsByConNumber::end===>");
			return cnList.get(0);
	}

	

	@Override
	public ConsignmentManifestDO getManifestDetailsByCon(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getManifestDetailsByCon::start===>");
		String queryName = "getmanifestDetailsByCon";
		List<ConsignmentManifestDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNO", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getManifestDetailsByCon::end===>");
			return cnList.get(0);
	}

	@Override
	public ManifestDO getOriginHubInDetailsByCon(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getBookingOutDetailsByCnNo::start===>");
		String queryName = "getmanifestDetailsByCon";
		List<ManifestDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNO", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getBookingOutDetailsByCnNo::end===>");
			return cnList.get(0);
	}
	@Override
	public OfficeDO getReportingOfficeFindByofficeId(int bookingOffice) {
		logger.debug("TrackingNotificationDAOImpl::getReportingOfficeFindByofficeId::start===>");
		String queryName = "getOfficeDetails";
		List<OfficeDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "officeId", bookingOffice);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getReportingOfficeFindByofficeId::start===>");
			return cnList.get(0);
		
	}
	
	@Override
	public DeliveryDetailsDO getDeliveryDetailsByCon(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getDeliveryDetailsByCon::start===>");
		String queryName = "delivaryDetailsByConsgNo";
		List<DeliveryDetailsDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNumber", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getDeliveryDetailsByCon::start===>");
			return cnList.get(0);
	}
	
	@Override
	public DeliveryDetailsDO getPendingDetailsByCon(String cnNumber) {
		logger.debug("TrackingNotificationDAOImpl::getPendingDetailsByCon::start===>");
		String queryName = "PendingDetailsByConsgNo";
		List<DeliveryDetailsDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNumber", cnNumber);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getPendingDetailsByCon::start===>");
			return cnList.get(0);
		
	}

	
	@Override
	public OfficeDO getDestinationOfficeByPincodeId(Integer pincodeId) {
		logger.debug("TrackingNotificationDAOImpl::getDestinationOfficeByPincodeId::start===>");
		String queryName = "getBranchDtlsForPincodeServiceByPincode";
		List<OfficeDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "pincodeId", pincodeId);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getDestinationOfficeByPincodeId::start===>");
			return cnList.get(0);
		
	}

	
	
	
	@Override
	public ConsignmentManifestDO getConsgManifestDetailsByConsgId(String consgId) {
		logger.debug("TrackingNotificationDAOImpl::getConsgManifestDetailsByConsgId::start===>");
		String queryName = "getManifestIdFromConsgId";
		List<ConsignmentManifestDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgId", consgId);
		if(cnList == null || cnList.isEmpty())
			return null;
		else
			logger.debug("TrackingNotificationDAOImpl::getConsgManifestDetailsByConsgId::start===>");
			return cnList.get(0);
	}



	@Override
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cnNo)  {
		logger.debug("TrackingNotificationDAOImpl::getDeliveryDetailsByCn::start===>");
//		/getDrsDtlsByConsgNo,consgNo
		String queryName = "getDrsDtlsByConsgNo";
		List<DeliveryDetailsDO> cnList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, "consgNo", cnNo);
		if(cnList == null || cnList.isEmpty())
			return null;
		/*int count = 0;
		for(DeliveryDetailsDO delDetails : cnList) {
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::getDeliveryDetailId"+delDetails.getDeliveryDetailId());
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::"+delDetails.getReasonDO().getReasonName());
			logger.debug("ConsignmentNotificationDAOImpl::getDeliveryDetailsByCn::"+count);
			count++;
		}*/
		logger.debug("TrackingNotificationDAOImpl::getDeliveryDetailsByCn::start===>");
		return cnList.get(0);
	}


	

}
