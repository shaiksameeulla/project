package com.ff.notification.dao;

import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;

public interface TrackingNotificationDAO {
	
	
	public BookingDO getBookingDetailsByCnNumber(String cnNumber);
	public ConsignmentDO getConsgDetailsByConNumber(String cnNumber);
	public ConsignmentManifestDO getConsgManifestDetailsByConsgId(String consgId);
	public ConsignmentManifestDO getManifestDetailsByCon(String cnNumber);
	public ManifestDO getOriginHubInDetailsByCon(String cnNumber);
	public OfficeDO getReportingOfficeFindByofficeId(int bookingOffice);
	public DeliveryDetailsDO getDeliveryDetailsByCon(String cnNumber);
	public OfficeDO getDestinationOfficeByPincodeId(Integer pincodeId);
	public DeliveryDetailsDO getPendingDetailsByCon(String cnNumber);  
	/**
	 * @param cnNo
	 * @return
	 */
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cnNo);

}
