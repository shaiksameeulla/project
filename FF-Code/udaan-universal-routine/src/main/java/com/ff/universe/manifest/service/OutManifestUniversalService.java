package com.ff.universe.manifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.PincodeTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OutManifestUniversalService.
 */
public interface OutManifestUniversalService {

	/**
	 * Gets the booking dtls.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the booking dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentModificationTO> getBookingDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException;

	/*
	 * public List<? extends OutManifestBaseTO> getManifestDtlsByManifestNo(
	 * ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
	 * CGSystemException;
	 */

	/**
	 * Update manifest weight.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest dtls for dispatch.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the manifest dtls for dispatch
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestTO> getManifestDtlsForDispatch(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Transfer converter4 dispatch.
	 * 
	 * @param manifestDOList
	 *            the manifest do list
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ManifestTO> transferConverter4Dispatch(List<ManifestDO> manifestDOList)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the process.
	 * 
	 * @param process
	 *            the process
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the consingment dtls.
	 * 
	 * @param consgNumner
	 *            the consg numner
	 * @return the consingment dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the pincode dtl.
	 * 
	 * @param pincodeTO
	 *            the pincode to
	 * @return the pincode dtl
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public PincodeTO getPincodeDtl(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the consignment types.
	 * 
	 * @param consgTypeTO
	 *            the consg type to
	 * @return the consignment types
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the bag rf no by rf id.
	 * 
	 * @param rfId
	 *            the rf id
	 * @return the bag rf no by rf id
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public String getBagRfNoByRfId(Integer rfId) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the bookings.
	 * 
	 * @param consgNumbers
	 *            the consg numbers
	 * @param consgType
	 *            the consg type
	 * @return the bookings
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<BookingDO> getBookings(List<String> consgNumbers,
			String consgType) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the consignment id by consg no.
	 * 
	 * @param consignmentTO
	 *            the consignment to
	 * @return the consignment id by consg no
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consg princing dtls.
	 * 
	 * @param consgNumner
	 *            the consg numner
	 * @return the consg princing dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*
	 * public CNPricingDetailsTO getConsgPrincingDtls(String consgNumner) throws
	 * CGBusinessException, CGSystemException;
	 */

	/**
	 * Update consg weight.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	boolean updateConsgWeight(OutManifestDetailBaseTO to)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the manifest dtls for dispatch by manifest no origin off id.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the manifest dtls for dispatch by manifest no origin off id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ManifestTO> getManifestDtlsForDispatchByManifestNoOriginOffId(
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To check if Consignment is returned due to party shifted reason
	 * 
	 * @param consgId
	 * @param processId
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	public boolean isPartyShiftedConsg(Integer consgId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consg dtls.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the consg dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTO> getConsgDtls(ManifestFactoryTO manifestFactoryTO)
			throws CGBusinessException, CGSystemException;

	public Integer calcOperatingLevel(OutManifestBaseTO manifestTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	public Integer getManifestOperatingLevel(OutManifestBaseTO manifestTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get process details
	 * 
	 * @param processCode
	 * @return ProcessDO
	 * @throws CGSystemException
	 */
	public ProcessDO getProcess(String processCode) throws CGSystemException;

	/**
	 * To save or update consignment billing rates
	 * 
	 * @param consignmentBillingRateDOs
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param consgNumber
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * To checks if is consignment exist in DRS.
	 * 
	 * @param consignment
	 *            the consignment
	 * @return the boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author hkansagr
	 */
	Boolean isConsignmentExistInDRS(String consignment)
			throws CGBusinessException, CGSystemException;

	public ConsignmentTypeDO getConsignmentTypeByCode(
			String consignmentTypeCode)throws CGBusinessException, CGSystemException;

}
