package com.ff.admin.complaints.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.SearchServiceRequestHeaderTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestValidationTO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author sdalli
 *
 */
public interface ServiceRequestForServiceReqService {
		 
		 List<StockStandardTypeTO> getSearchCategoryList()
				throws CGSystemException, CGBusinessException;
		 
		 List<StockStandardTypeTO> getQueryType()
				throws CGSystemException, CGBusinessException;
		 
		 List<StockStandardTypeTO> getServiceRelatedbyType()
				throws CGSystemException, CGBusinessException;
		 
		 List<StockStandardTypeTO> getStatusbyType()
					throws CGSystemException, CGBusinessException;
		 
		 
		 
		 CityTO getCity(final CityTO cityTO)throws CGBusinessException, CGSystemException;
		 
		 List<EmployeeTO> getEmployeeDetailsByDesignationType(
				 final String employeeDesignationType) throws CGBusinessException, CGSystemException;
		 
		 void saveOrUpdateServiceReqDtls(final ServiceRequestTO serviceRequestTO)throws CGBusinessException, CGSystemException;

		String generateReferenceNumber(final String loginOfficeCode) throws CGBusinessException, CGSystemException;

		List<ProductTO> getProductList()throws CGBusinessException, CGSystemException;

		ServiceRequestTO searchServiceReq(ServiceRequestFilters serviceRequestFilters)throws CGBusinessException, CGSystemException;

		List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(String roleName,
				Integer officeId) throws CGSystemException, CGBusinessException;

		/**
		 * Search service request dtls.
		 *
		 * @param serviceRequestTO the service request to
		 * @return the list
		 * @throws CGBusinessException the cG business exception
		 * @throws CGSystemException the cG system exception
		 */
		List<ServiceRequestTO> searchServiceRequestDtls(
				ServiceRequestTO serviceRequestTO)
				throws CGBusinessException, CGSystemException;

		/**
		 * Service equiry validation.
		 *
		 * @param validationTO the validation to
		 * @return the service request validation to
		 * @throws CGBusinessException the cG business exception
		 * @throws CGSystemException the cG system exception
		 */
		ServiceRequestValidationTO serviceEquiryValidation(
				ServiceRequestValidationTO validationTO)
				throws CGBusinessException, CGSystemException;

		/**
		 * Search service request dtls.
		 *
		 * @param serviceRequestTO the service request to
		 * @return the search service request header to
		 * @throws CGBusinessException the cG business exception
		 * @throws CGSystemException the cG system exception
		 */
		SearchServiceRequestHeaderTO searchServiceRequestDtls(
				SearchServiceRequestHeaderTO serviceRequestTO)
				throws CGBusinessException, CGSystemException;

		/**
		 * Gets the service request query type by service type.
		 *
		 * @param serviceType the service type
		 * @return the service request query type by service type
		 * @throws CGBusinessException the cG business exception
		 * @throws CGSystemException the cG system exception
		 */
		List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeByServiceType(
				String serviceType) throws CGBusinessException,
				CGSystemException;

		/**
		 * Update service transfer details.
		 *
		 * @param serviceRequestTO the service request to
		 * @return the string
		 * @throws CGBusinessException the cG business exception
		 * @throws CGSystemException the cG system exception
		 */
		Boolean updateServiceTransferDetails(ServiceRequestTO serviceRequestTO)
				throws CGBusinessException, CGSystemException;

}
