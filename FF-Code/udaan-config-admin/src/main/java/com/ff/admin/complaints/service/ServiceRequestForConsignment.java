package com.ff.admin.complaints.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.ServiceRequestForConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingConsignmentTO;
import com.ff.umc.EmployeeUserTO;

/**
 * @author sdalli
 *
 */
public interface ServiceRequestForConsignment {
		 
		 List<StockStandardTypeTO> getSearchCategoryList()
				throws CGSystemException, CGBusinessException;
		 
		 List<StockStandardTypeTO> getStatusbyType() throws CGSystemException, CGBusinessException;

		 String generateReferenceNumber(final String loginOfficeCode)throws CGBusinessException, CGSystemException;

		void saveOrUpdateServiceConsigDtls(final ServiceRequestForConsignmentTO serviceTO) throws CGBusinessException, CGSystemException;
	
		List<EmployeeUserTO> getBackLineEmpList(final Integer officeId,final String designation )throws CGBusinessException, CGSystemException;
		TrackingConsignmentTO viewTrackInformation(String consgNum , String refNum,String loginUserType)
				throws CGSystemException, CGBusinessException;
			
	
		public Boolean sendEmailByPlainText(final String to)
				 throws CGBusinessException, CGSystemException;
		 
		 public void sendSMS(String num, HttpServletResponse response)
				 throws CGBusinessException, CGSystemException;
		 
		 public ConsignmentTO getConsignmentDtls(String consgNo)
					throws CGBusinessException, CGSystemException ;
	
}
