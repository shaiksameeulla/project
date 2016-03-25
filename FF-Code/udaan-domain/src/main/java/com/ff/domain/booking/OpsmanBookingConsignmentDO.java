package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.OpsmanConsignmentDO;

public class OpsmanBookingConsignmentDO  extends CGFactDO
{

	private static final long serialVersionUID = 3952111320426580299L;
	
	private BcunBookingDO bcunBookingDO;
	private OpsmanConsignmentDO opsmanConsignmentDO;
	
	public BcunBookingDO getBcunBookingDO() {
		return bcunBookingDO;
	}
	public void setBcunBookingDO(BcunBookingDO bcunBookingDO) {
		this.bcunBookingDO = bcunBookingDO;
	}
	public OpsmanConsignmentDO getOpsmanConsignmentDO() {
		return opsmanConsignmentDO;
	}
	public void setOpsmanConsignmentDO(OpsmanConsignmentDO opsmanConsignmentDO) {
		this.opsmanConsignmentDO = opsmanConsignmentDO;
	}
	
}
