/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.pickup.ReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.ConfirmPickupOrderDAO;
import com.ff.web.pickup.utils.PickupUtils;

/**
 * @author uchauhan
 * 
 */
public class ConfirmPickupOrderServiceImpl implements ConfirmPickupOrderService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConfirmPickupOrderServiceImpl.class);
	
	private ConfirmPickupOrderDAO confirmPickupOrderDAO;

	private PickupManagementCommonService pickupManagementCommonService;

	private GeographyCommonService geographyCommonService;

	private ServiceOfferingCommonService serviceOfferingCommonService;
	
	
	
	

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param pickupManagementCommonService
	 *            the pickupManagementCommonService to set
	 */
	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	/**
	 * @param confirmPickupOrderDAO
	 *            the confirmPickupOrderDAO to set
	 */
	public void setConfirmPickupOrderDAO(
			ConfirmPickupOrderDAO confirmPickupOrderDAO) {
		this.confirmPickupOrderDAO = confirmPickupOrderDAO;
	}

	@Override
	public PickupOrderTO updatePickupOrderDetails(PickupOrderTO pickupOrderTO , String status) throws CGSystemException {
		LOGGER.trace("ConfirmPickupOrderServiceImpl :: updatePickupOrderDetails() :: Start --------> ::::::");
		boolean isUpdated = Boolean.FALSE;
		Integer cnt = pickupOrderTO.getCheckbox().length ;
		for(int i = 0 ; i <cnt ;i++ ){
			
		int rowNum = pickupOrderTO.getCheckbox()[i];
			Integer officeId = pickupOrderTO.getAssignedOfficeId()[rowNum];
		    Integer detailId = pickupOrderTO.getOrderBranchId()[rowNum];
		    Integer updatedBy = pickupOrderTO.getLoggedInUserId();
		    isUpdated = confirmPickupOrderDAO.updateBranchOrderDetails(status,officeId,detailId,updatedBy);
			if(status.equalsIgnoreCase(PickupManagementConstants.ACCEPTED))
			{
				isUpdated = confirmPickupOrderDAO.updatePickupOrderDetails(PickupManagementConstants.CLOSED,officeId,detailId,updatedBy);
				pickupOrderTO.setUpdated(isUpdated);
			}
			//Two-way write
			List<ReversePickupOrderDetailDO> pickupOrderDetailDOs = confirmPickupOrderDAO.getPickupOrderBranchMappingDtls(detailId);
			Set<ReversePickupOrderDetailDO> detailDOs = new HashSet<>(pickupOrderDetailDOs);
			PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPickupOrderIds4TwoWayWrite(detailDOs);
			pickupOrderTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
		}
		LOGGER.trace("ConfirmPickupOrderServiceImpl :: updatePickupOrderDetails() :: End --------> ::::::");
		return pickupOrderTO;
		
	}

	@Override
	public List<PickupOrderDetailsTO> getPickupRequestList(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConfirmPickupOrderServiceImpl :: getPickupRequestList() :: Start --------> ::::::");
		List<PickupOrderDetailsTO> detailsTOList = null;
		OfficeTO offcTO = null;

		try {
			List<ReversePickupOrderDetailDO> pickupDOs = confirmPickupOrderDAO
					.getPickupOrderRequestList(officeTO);
			if (!StringUtil.isEmptyList(pickupDOs)) {
				detailsTOList = new ArrayList<>(pickupDOs.size());
             /*for every ReversePickupOrderDetailDO create a list of 
              * corresponding TOs and set in the form*/
				for (ReversePickupOrderDetailDO detailDO : pickupDOs) {

					PickupOrderDetailsTO to = new PickupOrderDetailsTO();
					ReversePickupOrderHeaderDO header = detailDO
							.getPickupOrderHeader();
					PincodeTO pincodeTO = new PincodeTO();
					pincodeTO.setPincodeId(detailDO.getPincode());
					// validate pincode
					pincodeTO = geographyCommonService.validatePincode(pincodeTO);
					to.setPincodeName(pincodeTO.getPincode());
					// get city
					CityTO cityTO = geographyCommonService.getCity(pincodeTO
							.getPincode());
					to.setCityName(cityTO.getCityName());
					List<ConsignmentTypeTO> consignmentTypeTOs = serviceOfferingCommonService
							.getConsignmentType();
					Map<Integer, String> m = new HashMap<>();
					for (ConsignmentTypeTO consignment : consignmentTypeTOs) {
						m.put(consignment.getConsignmentId(),
								consignment.getConsignmentName());
					}
					to.setConsignmentName(m.get(detailDO.getConsignmentType()));
					// convert the detailDO to TO
					CGObjectConverter.createToFromDomain(detailDO, to);

					for(ReversePickupOrderBranchMappingDO orderBranch : detailDO.getBranchesAssignedDO()) {
						to.setRevOrderBranchId(orderBranch.getPickupOrderDetail().getDetailId());
						to.setAssignedOfficeId(orderBranch.getOrderAssignedBranch());
					}

					if (header != null) {
						if(header.getOriginatingOffice()!=null){
						offcTO = pickupManagementCommonService
								.getOfficeDetails(header.getOriginatingOffice());
						PickupOrderTO headerTO = new PickupOrderTO();
						// decides the logged in office Type and populates the details 
						pickupManagementCommonService.getReportingOffice(offcTO, headerTO);
						if (headerTO.getRegion()!=null){
							to.setOriginatingRegionName(headerTO.getRegion());
						}
						if (headerTO.getBranch()!=null){
							to.setOriginatingBranchName(headerTO.getBranch());
						}
						if (headerTO.getHub()!=null){
							to.setOriginatingHubName(headerTO.getHub());
						}
						}
						to.setRequestDate(header.getRequestDate());
					}/*else {
						throw new CGBusinessException();
					}*/
					detailsTOList.add(to);
				}
			}else{
				//As discussed and confirmed with Saumya : This exception message is not required.
				//throw new CGBusinessException(PickupManagementConstants.NO_PENDING_REQUESTS);
			}

		} catch (CGSystemException | CGBusinessException e) {
			
			throw e;
		}
		LOGGER.trace("ConfirmPickupOrderServiceImpl :: getPickupRequestList() :: End --------> ::::::");
		return detailsTOList;
	}

	


}
