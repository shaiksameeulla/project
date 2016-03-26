/**
 * 
 */
package com.ff.web.manifest.inmanifest.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.manifest.inmanifest.InBagManifestDoxTO;
import com.ff.manifest.inmanifest.InBagManifestParcelTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.serviceOfferring.CNPaperWorksTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface InBagManifestService.
 *
 * @author narmdr
 */
public interface InBagManifestService {

	/**
	 * Find bpl number dox.
	 *
	 * @param inBagManifestDoxTO the in bag manifest dox to
	 * @return the in manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestValidationTO findBplNumberDox(InBagManifestDoxTO inBagManifestDoxTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update in bag manifest dox.
	 *
	 * @param inBagManifestDoxTO the in bag manifest dox to
	 * @return the in bag manifest dox to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InBagManifestDoxTO saveOrUpdateInBagManifestDox(
			InBagManifestDoxTO inBagManifestDoxTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Find bpl number parcel.
	 *
	 * @param inBagManifestParcelTO the in bag manifest parcel to
	 * @return the in manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestValidationTO findBplNumberParcel(
			InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update in bag manifest parcel.
	 *
	 * @param inBagManifestParcelTO the in bag manifest parcel to
	 * @return the in bag manifest parcel to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InBagManifestParcelTO saveOrUpdateInBagManifestParcel(
			InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate consg number.
	 *
	 * @param inBagManifestParcelTO the in bag manifest parcel to
	 * @return the in manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	InManifestValidationTO validateConsgNumber(InBagManifestParcelTO inBagManifestParcelTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate declared value.
	 *
	 * @param bookingValidationTO the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingValidationTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the paper works.
	 *
	 * @param paperWorkValidationTO the paper work validation to
	 * @return the paper works
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<CNPaperWorksTO> getPaperWorks(CNPaperWorksTO paperWorkValidationTO)
			throws CGBusinessException, CGSystemException;

	InBagManifestDoxTO findBplNumberDox4Print( InBagManifestDoxTO inBagManifestDoxTO) throws CGBusinessException, CGSystemException;
}
