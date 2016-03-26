package com.ff.web.manifest.service;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.web.manifest.action.BranchManifestPage;


/**
 * @author nihsingh
 *
 */
public interface BranchOutManifestDoxService {

	/**@Desc Gets Consignment Details
	 * @param manifestFactoryTO
	 * @return BranchOutManifestDoxDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BranchOutManifestDoxDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO)throws CGBusinessException, CGSystemException;
	
	/**@Desc Gets Booking details by consignmnt no
	 * @param manifestFactoryTO
	 * @return BranchOutManifestDoxDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BranchOutManifestDoxDetailsTO getBookingDtlsByConsignmentNo(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException;
	
	/**@Desc saves Or Updates Manifest
	 * @param branchOutmanifestDoxTO
	 * @return String value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *//*
	public String saveOrUpdateBranchOutManifestDox(BranchOutManifestDoxTO branchOutmanifestDoxTO)
			throws CGBusinessException, CGSystemException;*/
	
	/**Search manifest details
	 * @param manifestTO
	 * @return BranchOutManifestDoxTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BranchOutManifestDoxTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException ;
	
	/**
	 * Gets the in manifestd consignment dtls.
	 *
	 * @param manifestFactoryTO the manifest factory to
	 * @return the in manifestd consignment dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public BranchOutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException, CGSystemException;
				
	/*public void deleteFromComailManifest(Integer[] comailmanifstIdArray);*/
	
	public String saveOrUpdateBranchOutManifestDox(ManifestDO manifest,
			BranchOutManifestDoxTO branchOutManifestDoxTO, List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException;
	
	public List<BranchManifestPage> preparePrint(BranchOutManifestDoxTO branchOutManifestDoxTO) throws CGBusinessException;
}
