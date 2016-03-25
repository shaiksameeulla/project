package com.ff;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;


// TODO: Auto-generated Javadoc
/**
 * The Class TestTwoWayWriteMainClass.
 */
public class TestTwoWayWriteMainClass
{


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		
		//TwoWayWriteProceesCall twoWayWriteProceesCall= new TwoWayWriteProceesCall();
		
		BookingDO bookingDO=new BookingDO();
		
		ConsigneeConsignorDO consigneeConsignorDO=new ConsigneeConsignorDO();
		
		List<CGBaseDO> listCGBaseDO=new ArrayList<CGBaseDO>();
		String[] doNames={"BookingDO","ConsigneeConsignorDO"};
		
		listCGBaseDO.add(bookingDO);
		listCGBaseDO.add(consigneeConsignorDO);
		
		//twoWayWriteProceesCall.twoWriteProcess(listCGBaseDO, doNames);
		
	}
}
