package com.ff.web.manifest.service;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.manifest.BranchOutManifestParcelDetailsTO;
import com.ff.manifest.BranchOutManifestParcelTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;

public interface BranchOutManifestParcelService {
	
	/**
	 * @Desc search manifest details by manifest number
	 * @param manifestTO
	 * @return BranchOutManifestParcelTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	BranchOutManifestParcelTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @Desc get consignment details by consignment number
	 * @param manifestFactoryTO
	 * @return BranchOutManifestParcelDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	BranchOutManifestParcelDetailsTO getConsignmentDtls(OutManifestValidate cnValidateTO)
			throws CGBusinessException, CGSystemException;
	
	
	
	/**
	 * Gets the in manifestd consignment dtls.
	 *
	 * @param manifestFactoryTO the manifest factory to
	 * @return the in manifestd consignment dtls
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public BranchOutManifestParcelDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException, CGSystemException;
	
	public String saveOrUpdateBranchOutManifestParcel(ManifestDO manifest,
			BranchOutManifestParcelTO branchOutManifestParcelTO, List<BookingDO> allBooking,
			List<ConsignmentDO> pickupConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException;
			
}
