package com.ff.admin.coloading.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.coloading.ColoadingAirAwbDO;
import com.ff.domain.coloading.ColoadingAirCdDO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingTrainContractRateDetailsDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;

public class ColoadingRateCalculationDAOImpl extends CGBaseDAO implements
ColoadingRateCalculationDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingRateCalculationDAOImpl.class);

	@Override
	public void calculateAndSaveRateForAir() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForAir() :: Start");
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			if (session != null) {
				// Getting Dispatch Date for TranportModeId= 1,1 is for Air
				List<LoadMovementDO> list = getLoadMovment(session, 1);
				if (!CGCollectionUtils.isEmpty(list)) {
					// Fetching Service Tax Rate Component
					//Double serviceTax = getServiceTax(session);
					//Double educationCess = getEducationCessTax(session);
					//Double higherEducationCess = getHigherEducationCessTax(session);

					Set<String> tokenSet = new HashSet<String>(); 

					for (LoadMovementDO loadMovementDO : list) {
						List<ColoaderRatesDO> coloaderRatesDOs = getColoderRatesDOList(
								loadMovementDO, ColoadingConstants.AIR);

						for (ColoaderRatesDO coloaderRatesDO : coloaderRatesDOs) {

							if(! tokenSet.contains(coloaderRatesDO.getAwb_cd_rr_number())) {

								// Fetching Configured rate for Dispatch start
								criteria = session
										.createCriteria(ColoadingAirDO.class);
								criteria.add(Restrictions.eq("vendorId",
										coloaderRatesDO.getVendorId()));
								criteria.add(Restrictions.eq("originCityId",
										coloaderRatesDO.getOrigin_id()));
								criteria.add(Restrictions.eq("destinationCityId",
										coloaderRatesDO.getDestination_id()));
								criteria.add(Restrictions.le(
										"effectiveFrom",
										DateUtil.slashDelimitedstringToDDMMYYYYFormat(DateUtil
												.getCurrentDateInDDMMYYYY())));
								criteria.add(Restrictions.eq("cdType",
										coloaderRatesDO.getSubRateType()));
								criteria.add(Restrictions.eq("storeStatus",
										ColoadingConstants.SUBMIT_CHAR));

								if (ColoadingConstants.AWB
										.equalsIgnoreCase(coloaderRatesDO
												.getSubRateType())) {
									criteria.createAlias("coloadingAirAwbs",
											"coloadingAirAwbs");
									criteria.add(Restrictions.eq(
											"coloadingAirAwbs.flightNo",
											coloaderRatesDO.getTransportRefNo()));
								} else {
									criteria.createAlias("coloadingAirCds",
											"coloadingAirCds");
									criteria.add(Restrictions.eq(
											"coloadingAirCds.flightNo",
											coloaderRatesDO.getTransportRefNo()));
								}
								ColoadingAirDO airDO = (ColoadingAirDO) criteria
										.uniqueResult();
								// Fetching Configured rate for Dispatch end	
								if (airDO == null) {
									airDO = getCurrentRates(session,
											coloaderRatesDO, airDO);
								}
								if (airDO != null) {
									coloaderRatesDO
									.setServiceType(ColoadingConstants.AIR);

									// If Air type is CD
									if (ColoadingConstants.CD
											.equalsIgnoreCase(coloaderRatesDO
													.getSubRateType())) {
										Set<ColoadingAirCdDO> coloadingAirCds = airDO
												.getColoadingAirCds();
										if (!CGCollectionUtils
												.isEmpty(coloadingAirCds)) {
											for (ColoadingAirCdDO airCdDO : coloadingAirCds) {
												if (airCdDO.getFlightNo() != null
														&& airCdDO
														.getFlightNo()
														.equalsIgnoreCase(
																coloaderRatesDO
																.getTransportRefNo())) {
													double weight = coloaderRatesDO
															.getWeight();

													if(weight <= 0){
														continue;
													}
													// Calculation logic Started

													// Setting Billing Rate per KG
													/*double billingRate = weight
													 * airCdDO
																	.getBillingRate();*/
													double billingRate =airCdDO.getBillingRate();

													// Setting FSC In per KG
													coloaderRatesDO.setFsc(weight * airCdDO
															.getFuelSurcharge());

													// Setting Octroi Per Bag
													coloaderRatesDO
													.setOctroi(coloaderRatesDO
															.getQuantity()
															* airCdDO
															.getOctroiPerBag());
													// Setting Octroi Per KG if it
													// is > Octroi Per Bag
													if (weight
															* airCdDO
															.getOctroiPerKg() > coloaderRatesDO
															.getOctroi()) {
														coloaderRatesDO
														.setOctroi(weight
																* airCdDO
																.getOctroiPerKg());
													}

													// Setting Surcharge Per KG
													coloaderRatesDO
													.setSurcharge(weight
															* airCdDO
															.getSurcharge());

													// Setting Other Charges Per KG
													coloaderRatesDO
													.setOtherCharges(weight
															* airCdDO
															.getOtherCharges());

													// Setting Basic = (Billing
													// Rate+FSC+Octroi+SC+OC)
													coloaderRatesDO  
													.setBasic(weight * billingRate
															+ coloaderRatesDO
															.getFsc()
															+ coloaderRatesDO
															.getOctroi()
															+ coloaderRatesDO
															.getSurcharge()
															+ coloaderRatesDO
															.getOtherCharges());

													// Setting CD Bill charges
													coloaderRatesDO
													.setAwbCdCharges(airCdDO
															.getCdValue());

													// Setting Total = (Billing
													// Rate+CD Charges)
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getBasic()
															+ coloaderRatesDO
															.getAwbCdCharges());

													/*calculateAllTaxes(
															coloaderRatesDO,
															serviceTax,
															educationCess,
															higherEducationCess);*/
													calculateAllTaxes(session,coloaderRatesDO);

													// SettingGross Total
													// charges(Gross Total =
													// Total+Service Tax +
													// Educational Cess + Higher
													// Educational Cess);
													coloaderRatesDO
													.setGrossTotal(coloaderRatesDO
															.getTotal()
															+ coloaderRatesDO
															.getServiceTax()
															+ coloaderRatesDO
															.getEducationalCess()
															+ coloaderRatesDO
															.getHigherEducationalCess());

													// Setting SSP rate
													if (airCdDO.getSspRate() != 0) {
														coloaderRatesDO
														.setSsp(airCdDO
																.getSspRate());
														// Setting Discount =
														// (Billing Rate - SSP)*
														// weight
														coloaderRatesDO
														.setDiscount((billingRate - coloaderRatesDO
																.getSsp())
																* weight);
														coloaderRatesDO
														.setNetEffectiveAmts(coloaderRatesDO
																.getGrossTotal()
																- coloaderRatesDO
																.getDiscount());
													} else {
														coloaderRatesDO
														.setNetEffectiveAmts(coloaderRatesDO
																.getGrossTotal());
													}
													// Calculation logic End
													saveRates(session,
															coloaderRatesDO);
													// Updating Flag
													updateIsRateCalculatedFlag(
															coloaderRatesDOs,
															session);
												}
											}
										}
									} else {// For AWB
										Set<ColoadingAirAwbDO> airAwbDOs = airDO
												.getColoadingAirAwbs();
										if (!CGCollectionUtils.isEmpty(airAwbDOs)) {
											for (ColoadingAirAwbDO airAwbDO : airAwbDOs) {
												if (airAwbDO.getFlightNo() != null
														&& airAwbDO
														.getFlightNo()
														.equalsIgnoreCase(
																coloaderRatesDO
																.getTransportRefNo())) {
													// Calculation Started
													// Setting Basic Price accordig
													// to the slap Weight
													double weight = coloaderRatesDO
															.getWeight();
													if(weight <= 0){
														continue;
													}
													double slapWeight;
													double slabrate = 0;
													if (weight > 0 && weight <= 44) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW1());
														slabrate=airAwbDO.getW1();
													} else if (weight > 44
															&& weight <= 99) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW2());
														slabrate=airAwbDO.getW2();
													} else if (weight > 99
															&& weight <= 249) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW3());
														slabrate=airAwbDO.getW3();
													} else if (weight > 249
															&& weight <= 499) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW4());
														slabrate=airAwbDO.getW4();
													} else if (weight > 499
															&& weight <= 999) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW5());
														slabrate=airAwbDO.getW5();
													} else if (weight > 999) {
														coloaderRatesDO
														.setBasic(weight
																* airAwbDO
																.getW6());
														slabrate=airAwbDO.getW6();
													}
													//For any single bag weighing greater than or equal to 100kg; W1 rate should be applied for AWB.
													if(weight>=100 && coloaderRatesDO.getQuantity()==1){
														coloaderRatesDO.setBasic(weight* airAwbDO.getW1());	
														slabrate=airAwbDO.getW1();
													}

													// Setting if minimum is > rate
													// per KG
													if (airAwbDO.getMinTariff() > coloaderRatesDO
															.getBasic()) {
														coloaderRatesDO
														.setBasic(airAwbDO
																.getMinTariff());
													}
													slapWeight = coloaderRatesDO
															.getBasic();

													// Setting FuelSurcharge per KG
													coloaderRatesDO
													.setFsc(weight
															* airAwbDO
															.getFuelSurcharge());

													// Setting Octroi Per Bag
													coloaderRatesDO
													.setOctroi(coloaderRatesDO
															.getQuantity()
															* airAwbDO
															.getOctroiPerBag());
													// Setting Octroi Per KG if it
													// is > Octroi Per Bag
													if (weight
															* airAwbDO
															.getOctroiPerKg() > coloaderRatesDO
															.getOctroi()) {
														coloaderRatesDO
														.setOctroi(weight
																* airAwbDO
																.getOctroiPerKg());
													}

													// Setting Origin TSP
													coloaderRatesDO
													.setOriginTsp(airAwbDO
															.getOriginTspFlatRate());
													// Setting Origin TSP Per KG if
													// it is > Origin TSP Flat
													if (weight
															* airAwbDO
															.getOriginTspPerKgRate() > coloaderRatesDO
															.getOriginTsp()) {
														coloaderRatesDO
														.setOriginTsp(weight
																* airAwbDO
																.getOriginTspPerKgRate());
													}
													//if the dispatch type is FF then destination Tsp will not consider.

													// Setting Destination TSP
													if(!ColoadingConstants.FIRST_FLIGHT_TO_FIRST_FLIGHT
															.equals(coloaderRatesDO.getConnectedAs())){
														coloaderRatesDO
														.setDestinationTsp(airAwbDO
																.getDestTspFlatRate());
														// Setting Destination TSP Per
														// KG if it is > Destination TSP
														// Flat
														if (weight
																* airAwbDO
																.getDestTspPerKgRate() > coloaderRatesDO
																.getDestinationTsp()) {
															coloaderRatesDO
															.setDestinationTsp(weight
																	* airAwbDO
																	.getDestTspPerKgRate());
														}}
													// Setting Airport Handling
													// Charge Per KG
													coloaderRatesDO
													.setAirportHandlingCharges(weight
															* airAwbDO
															.getAirportHandlingCharge());

													// Setting X-ray Charge Per KG
													coloaderRatesDO
													.setXrayCharges(weight
															* airAwbDO
															.getXrayCharge());

													// Setting Origin Utilization
													// Charge
													coloaderRatesDO
													.setOriginUti(airAwbDO
															.getOriginMinUtilCharge());
													// Setting Origin Utilization
													// Charge Per KG if it is >
													// Origin Utilization Min Charge
													if (weight
															* airAwbDO
															.getOriginUtilChargePerKg() > coloaderRatesDO
															.getOriginUti()) {
														coloaderRatesDO
														.setOriginUti(weight
																* airAwbDO
																.getOriginUtilChargePerKg());
													}

													// Setting Destination
													// Utilization Charge
													coloaderRatesDO
													.setDestinationUti(airAwbDO
															.getDestMinUtilCharge());
													// Setting Destination
													// Utilization Charge Per KG if
													// it is > Destination
													// Utilization Min Charge
													if (weight
															* airAwbDO
															.getDestUtilChargePerKg() > coloaderRatesDO
															.getDestinationUti()) {
														coloaderRatesDO
														.setDestinationUti(weight
																* airAwbDO
																.getDestUtilChargePerKg());
													}

													// Setting Service Charge Of
													// Airline Per KG
													coloaderRatesDO
													.setServiceChargeOfAirline(weight
															* airAwbDO
															.getAirlineServiceCharge());

													// Setting Surcharge Per KG
													coloaderRatesDO
													.setSurcharge(weight
															* airAwbDO
															.getSurcharge());

													// Adding All the charges(Basic
													// = Frieght+FSC+Octori (higher)
													// + TSP (higher)+
													// AHC+X-ray+UTI+SC+Surcharge);
													coloaderRatesDO
													.setBasic(coloaderRatesDO
															.getBasic()
															+ coloaderRatesDO
															.getFsc()
															+ coloaderRatesDO
															.getOctroi()
															+ coloaderRatesDO
															.getOriginTsp()
															+ (StringUtil.isEmptyDouble(coloaderRatesDO.getDestinationTsp()) ? 0 : coloaderRatesDO.getDestinationTsp())
															+ coloaderRatesDO
															.getAirportHandlingCharges()
															+ coloaderRatesDO
															.getXrayCharges()
															+ coloaderRatesDO
															.getOriginUti()
															+ coloaderRatesDO
															.getDestinationUti()
															+ coloaderRatesDO
															.getServiceChargeOfAirline()
															+ coloaderRatesDO
															.getSurcharge());

													// Setting Airways Bill charges
													coloaderRatesDO
													.setAwbCdCharges(airAwbDO
															.getAirwayBill());

													// Setting Total charges(Basic =
													// Basic+AWB);
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getBasic()
															+ coloaderRatesDO
															.getAwbCdCharges());

													/*calculateAllTaxes(
															coloaderRatesDO,
															serviceTax,
															educationCess,
															higherEducationCess);*/
													calculateAllTaxes(session,coloaderRatesDO);

													// Gross Total charges(Gross
													// Total+Service Tax +
													// Educational Cess + Higher
													// Educational Cess);
													coloaderRatesDO
													.setGrossTotal(coloaderRatesDO
															.getTotal()
															+ coloaderRatesDO
															.getServiceTax()
															+ coloaderRatesDO
															.getEducationalCess()
															+ coloaderRatesDO
															.getHigherEducationalCess());

													// Setting SSP rate
													coloaderRatesDO.setSsp(airAwbDO
															.getSspRate());

													// Setting Discount when
													// disocunt cosidered = (Frieght
													// *Discount)/100
													if (airDO.getSspRateAboveKg() != null
															&& airDO.getSspRateAboveKg() != 0) {
														if (weight < airDO
																.getSspRateAboveKg()) {
															//if (airAwbDO
															//	.getDiscountedPercentage() != 0.0) {
															//if min tariff value is greater than basic(weight * slabrate) then discount should not be consider.
															if(airAwbDO.getDiscountedPercentage() != 0.0 
																	&& (airAwbDO.getMinTariff() < weight * slabrate)){
																coloaderRatesDO
																.setDiscountWhenDiscountConsidered((slapWeight * airAwbDO
																		.getDiscountedPercentage()) / 100);
															}
															if(coloaderRatesDO.getDiscountWhenDiscountConsidered() != null){
																coloaderRatesDO
																.setNetEffectiveAmts(coloaderRatesDO
																		.getGrossTotal()
																		- coloaderRatesDO
																		.getDiscountWhenDiscountConsidered());
															}else{
																coloaderRatesDO.setNetEffectiveAmts(coloaderRatesDO.getGrossTotal());
															}

														} else {
															//if min tariff value is greater than basic(weight * slabrate) then discount should not be consider.
															if(airAwbDO.getMinTariff() < weight * slabrate){
																coloaderRatesDO
																.setDiscount(weight
																		* (slabrate-airAwbDO
																				.getSspRate()));
															}
															if(coloaderRatesDO.getDiscount() !=null){
																coloaderRatesDO
																.setNetEffectiveAmts(coloaderRatesDO
																		.getGrossTotal()
																		- coloaderRatesDO
																		.getDiscount());
															}else{
																coloaderRatesDO.setNetEffectiveAmts(coloaderRatesDO.getGrossTotal());
															}
														}
													} else {
														//if min tariff value is greater than basic(weight * slabrate) then discount should not be consider.
														if(airAwbDO.getDiscountedPercentage() != 0.0 
																&& (airAwbDO.getMinTariff() < weight * slabrate)){
															coloaderRatesDO
															.setDiscountWhenDiscountConsidered((slapWeight * airAwbDO
																	.getDiscountedPercentage()) / 100);
														}
														if(coloaderRatesDO.getDiscountWhenDiscountConsidered() != null){
															coloaderRatesDO
															.setNetEffectiveAmts(coloaderRatesDO
																	.getGrossTotal()
																	- coloaderRatesDO
																	.getDiscountWhenDiscountConsidered());
														}else{
															coloaderRatesDO.setNetEffectiveAmts(coloaderRatesDO.getGrossTotal());
														}
													}
													// Calculation End

													saveRates(session,
															coloaderRatesDO);
													// Updating Flag
													updateIsRateCalculatedFlag(
															coloaderRatesDOs,
															session);
												}
											}
										}
									}
								}

								tokenSet.add(coloaderRatesDO.getAwb_cd_rr_number());
							}
						}

					}
				}
				list = null;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingRateCalculationDAOImpl::calculateAndSaveRateForAir()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForAir() :: END");

	}
	@Override
	public void calculateAndSaveRateForTrain() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForTrain() :: Start");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				// Getting Dispatch Date for TranportModeId= 2,2 is for Train
				List<LoadMovementDO> list = getLoadMovment(session, 2);
				// Fetching Service Tax Rate Component
				//Double serviceTax = getServiceTax(session);
				//Double educationCess = getEducationCessTax(session);
				//Double higherEducationCess = getHigherEducationCessTax(session);
				if (!CGCollectionUtils.isEmpty(list)) {
					for (LoadMovementDO loadMovementDO : list) {
						List<ColoaderRatesDO> coloaderRatesDOs = getColoderRatesDOList(
								loadMovementDO, ColoadingConstants.TRAIN);
						for (ColoaderRatesDO coloaderRatesDO : coloaderRatesDOs) {
							// Fetching Configured rate for Dispatch
							Criteria criteria = session
									.createCriteria(ColoadingTrainContractDO.class);
							criteria.createAlias(
									"coloadingTrainContractRateDtls",
									"coloadingTrainContractRateDtls");
							criteria.add(Restrictions.eq("vendorId",
									coloaderRatesDO.getVendorId()));

							criteria.add(Restrictions.eq("originCityId",
									coloaderRatesDO.getOrigin_id()));
							criteria.add(Restrictions.eq("destCityId",
									coloaderRatesDO.getDestination_id()));

							criteria.add(Restrictions.le(
									"effectiveFrom",
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(DateUtil
											.getCurrentDateInDDMMYYYY())));
							criteria.add(Restrictions.eq("storeStatus",
									ColoadingConstants.SUBMIT_CHAR));
							criteria.add(Restrictions.eq(
									"coloadingTrainContractRateDtls.trainNo",
									coloaderRatesDO.getTransportRefNo()));
							ColoadingTrainContractDO coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
									.uniqueResult();
							if (coloadingTrainContractDO == null) {
								coloadingTrainContractDO = getCurrentRates(
										session, coloaderRatesDO,
										coloadingTrainContractDO);
							}
							if (coloadingTrainContractDO != null) {
								Set<ColoadingTrainContractRateDetailsDO> coloadingTrainContractRateDtls = coloadingTrainContractDO
										.getColoadingTrainContractRateDtls();
								if (!CGCollectionUtils
										.isEmpty(coloadingTrainContractRateDtls)) {
									for (ColoadingTrainContractRateDetailsDO rateDetailsDO : coloadingTrainContractRateDtls) {
										if (rateDetailsDO.getTrainNo() != null
												&& rateDetailsDO
												.getTrainNo()
												.equalsIgnoreCase(
														coloaderRatesDO
														.getTransportRefNo())) {

											// Calculation Started
											double weight = coloaderRatesDO
													.getWeight();
											// Calculate rate if Weght is > 0
											if (weight > 0) {
												// Setting Rate per KK Charges
												coloaderRatesDO
												.setBasic(weight
														* rateDetailsDO
														.getRatePerKg());

												// Setting Minimum Charges
												if (rateDetailsDO
														.getMinChargeableRate() != null) {
													// Setting Minimum charges
													// if it is > Rate Per KG
													if (rateDetailsDO
															.getMinChargeableRate() > coloaderRatesDO
															.getBasic()) {
														coloaderRatesDO
														.setBasic(rateDetailsDO
																.getMinChargeableRate());
													}
												}

												// Setting Other Charges
												if (rateDetailsDO
														.getOtherChargesPerKg() != null) {
													coloaderRatesDO
													.setOtherCharges(weight
															* rateDetailsDO
															.getOtherChargesPerKg());
												}

												// Setting Total
												if (coloaderRatesDO
														.getOtherCharges() != null) {
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getBasic()
															+ coloaderRatesDO
															.getOtherCharges());
												} else {
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getBasic());
												}

												/*calculateAllTaxes(
														coloaderRatesDO,
														serviceTax,
														educationCess,
														higherEducationCess);*/
												calculateAllTaxes(session,coloaderRatesDO);

												// Gross Total = Total+Service
												// Tax + Educational Cess +
												// Higher Educational Cess);
												coloaderRatesDO
												.setGrossTotal(coloaderRatesDO
														.getTotal()
														+ coloaderRatesDO
														.getServiceTax()
														+ coloaderRatesDO
														.getEducationalCess()
														+ coloaderRatesDO
														.getHigherEducationalCess());
												// Setting finalPayble
												coloaderRatesDO
												.setNetEffectiveAmts(coloaderRatesDO
														.getGrossTotal());
												// Calculation End
												coloaderRatesDO
												.setServiceType(ColoadingConstants.TRAIN);
												saveRates(session,
														coloaderRatesDO);
												// Updating Flag
												updateIsRateCalculatedFlag(
														coloaderRatesDOs,
														session);
											}
										}
									}

								}
							}
						}
					}
					list = null;
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingRateCalculationDAOImpl::calculateAndSaveRateForTrain()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForTrain() :: END");
	}

	@Override
	public void calculateAndSaveRateForSurface() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForSurface() :: Start");
		Session session = null;
		try {
			session = createSession();
			if (session != null) {
				// Getting Dispatch Date for TranportModeId= 3,3 is for Surface
				List<LoadMovementDO> list = getLoadMovment(session, 3);
				if (!CGCollectionUtils.isEmpty(list)) {
					// Fetching Service Tax Rate Component
					//Double serviceTax = getServiceTax(session);
					//Double educationCess = getEducationCessTax(session);
					//Double higherEducationCess = getHigherEducationCessTax(session);

					for (LoadMovementDO loadMovementDO : list) {
						List<ColoaderRatesDO> coloaderRatesDOs = getColoderRatesDOList(
								loadMovementDO, ColoadingConstants.ROAD);
						for (ColoaderRatesDO coloaderRatesDO : coloaderRatesDOs) {
							coloaderRatesDO
							.setServiceType(ColoadingConstants.ROAD);
							coloaderRatesDO
							.setSubRateType(ColoadingConstants.SURFACE);
							// Fetching Configured rate for Dispatch
							Criteria criteria = session
									.createCriteria(ColoadingSurfaceRateEntryDO.class);
							criteria.add(Restrictions.eq("vendorId",
									coloaderRatesDO.getVendorId()));
							criteria.add(Restrictions.le(
									"fromDate",
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(DateUtil
											.getCurrentDateInDDMMYYYY())));
							ColoadingSurfaceRateEntryDO coloadingSurfaceRateEntryDO = (ColoadingSurfaceRateEntryDO) criteria
									.uniqueResult();
							if (coloadingSurfaceRateEntryDO != null) {
								// Calculation Started
								double weight = coloaderRatesDO.getWeight();
								double weightSlab = coloadingSurfaceRateEntryDO
										.getToWeight();
								// Calculate rate if Weght is > 0
								if (weight > 0) {
									if (weight > weightSlab) {
										double diff = weight - weightSlab;
										coloaderRatesDO
										.setBasic((weight - diff)
												* coloadingSurfaceRateEntryDO
												.getRate());
										coloaderRatesDO
										.setBasic(coloaderRatesDO
												.getBasic()
												+ (diff * coloadingSurfaceRateEntryDO
														.getAdditionalPerKg()));
									} else {
										coloaderRatesDO.setBasic(weight
												* coloadingSurfaceRateEntryDO
												.getRate());
									}

									// Setting Total
									coloaderRatesDO.setTotal(coloaderRatesDO
											.getBasic());
									/*calculateAllTaxes(coloaderRatesDO,
											serviceTax, educationCess,
											higherEducationCess);*/
									calculateAllTaxes(session,coloaderRatesDO);

									// Gross Total = Total+Service Tax +
									// Educational Cess + Higher Educational
									// Cess);
									coloaderRatesDO
									.setGrossTotal(coloaderRatesDO
											.getTotal()
											+ coloaderRatesDO
											.getServiceTax()
											+ coloaderRatesDO
											.getEducationalCess()
											+ coloaderRatesDO
											.getHigherEducationalCess());

									// Setting finalPayble
									coloaderRatesDO
									.setNetEffectiveAmts(coloaderRatesDO
											.getGrossTotal());
									// Calculation End
									saveRates(session, coloaderRatesDO);
									// Updating Flag
									updateIsRateCalculatedFlag(
											coloaderRatesDOs, session);
								}

							}
						}
					}
					list = null;
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingRateCalculationDAOImpl::calculateAndSaveRateForSurface()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForSurface() :: END");
	}

	@Override
	public void calculateAndSaveRateForVehicle() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForVehicle() :: Start");
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			if (session != null) {
				// Getting ColoadingVehicleServiceEntry list
				criteria = session
						.createCriteria(ColoadingVehicleServiceEntryDO.class);
				criteria.add(Restrictions.eq("isRateCalculated",
						CommonConstants.NO));
				List<ColoadingVehicleServiceEntryDO> coloadingVehicleServiceEntryDOs = criteria
						.list();
				List<ColoadingVehicleServiceEntryDO> sameEntry;
				if (!CGCollectionUtils.isEmpty(coloadingVehicleServiceEntryDOs)) {
					Date date = null;
					String vehicleNumber = null;
					ColoaderRatesDO coloaderRatesDO = null;
					// Fetching Service Tax Rate Component
					//Double serviceTax = getServiceTax(session);
					//Double educationCess = getEducationCessTax(session);
					//Double higherEducationCess = getHigherEducationCessTax(session);
					for (ColoadingVehicleServiceEntryDO serviceEntryDO : coloadingVehicleServiceEntryDOs) {
						if (date != null
								&& vehicleNumber != null
								&& date.equals(serviceEntryDO.getDate())
								&& vehicleNumber.equals(serviceEntryDO
										.getVehNumber())) {
							serviceEntryDO
							.setIsRateCalculated(CommonConstants.YES);
							session.save(serviceEntryDO);
							session.flush();
							continue;
						}
						date = serviceEntryDO.getDate();
						vehicleNumber = serviceEntryDO.getVehNumber();
						sameEntry = new ArrayList<>();
						for (ColoadingVehicleServiceEntryDO entryDO : coloadingVehicleServiceEntryDOs) {
							if (entryDO.getIsRateCalculated().equals(
									CommonConstants.NO)) {
								if (date.equals(entryDO.getDate())
										&& vehicleNumber.equals(entryDO
												.getVehNumber())) {
									sameEntry.add(entryDO);
								}
							}
						}
						double totalDistance = 0;
						double ot = 0;
						for (ColoadingVehicleServiceEntryDO entryDO : sameEntry) {
							if (totalDistance == 0 && ot == 0) {
								totalDistance = serviceEntryDO.getClosingKm()
										- serviceEntryDO.getOpeningKm();
								ot = serviceEntryDO.getOt();
							} else {
								totalDistance += entryDO.getClosingKm()
										- entryDO.getOpeningKm();
								ot += entryDO.getOt();
							}
						}
						double actualDistance = totalDistance;
						coloaderRatesDO = new ColoaderRatesDO();
						coloaderRatesDO.setDispatchDate(serviceEntryDO
								.getCreatedDate());
						coloaderRatesDO.setTransportRefNo(vehicleNumber);
						if (coloaderRatesDO.getTransportRefNo() != null) {
							criteria = session
									.createCriteria(ColoadingVehicleContractDO.class);
							criteria.add(Restrictions.eq("vehicleNo",
									coloaderRatesDO.getTransportRefNo()));
							criteria.add(Restrictions.le(
									"effectiveFrom",
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(DateUtil
											.getCurrentDateInDDMMYYYY())));
							criteria.add(Restrictions.eq("storeStatus",
									ColoadingConstants.SUBMIT_CHAR));
							ColoadingVehicleContractDO coloadingVehicleContractDO = (ColoadingVehicleContractDO) criteria
									.uniqueResult();
							if (coloadingVehicleContractDO != null) {
								coloaderRatesDO
								.setServiceType(ColoadingConstants.ROAD);
								// Setting Rent
								if (coloadingVehicleContractDO.getRent() != null) {
									coloaderRatesDO
									.setTotal(coloadingVehicleContractDO
											.getRent());
								}
								// Setting Other Charges IF applicable
								if (coloadingVehicleContractDO.getOthers() != null) {
									if (coloaderRatesDO.getTotal() != null) {
										coloaderRatesDO
										.setTotal(coloaderRatesDO
												.getTotal()
												+ coloadingVehicleContractDO
												.getOthers());
									} else {
										coloaderRatesDO
										.setTotal(coloadingVehicleContractDO
												.getOthers()
												.doubleValue());
									}
								}
								if (ColoadingConstants.RATE_TYPE_FIXED
										.equals(coloadingVehicleContractDO
												.getRateType())) {
									if (coloadingVehicleContractDO.getFreeKm() != null) {
										// Setting Free KM
										coloaderRatesDO.setFreeKm(new Double(
												coloadingVehicleContractDO
												.getFreeKm()));
									}
									coloaderRatesDO
									.setSubRateType(ColoadingConstants.RATE_TYPE_FIXED);
								} else if (ColoadingConstants.RATE_TYPE_FIXED_DAY
										.equals(coloadingVehicleContractDO
												.getRateType())) {
									if (coloadingVehicleContractDO.getFreeKm() != null
											&& coloadingVehicleContractDO
											.getFreeKm() != 0) {
										// Setting Free KM
										coloaderRatesDO.setFreeKm(new Double(
												coloadingVehicleContractDO
												.getFreeKm()));
										totalDistance -= coloaderRatesDO
												.getFreeKm();
									}

									setOTandPerHourRate(coloaderRatesDO,
											totalDistance, ot,
											coloadingVehicleContractDO);

									coloaderRatesDO
									.setSubRateType(ColoadingConstants.RATE_TYPE_FIXED_DAY);

								} else if (ColoadingConstants.RATE_TYPE_PER_DAY_FUEL
										.equals(coloadingVehicleContractDO
												.getRateType())) {
									if (coloadingVehicleContractDO.getFreeKm() != null
											&& coloadingVehicleContractDO
											.getFreeKm() != 0) {
										// Setting Free KM
										coloaderRatesDO.setFreeKm(new Double(
												coloadingVehicleContractDO
												.getFreeKm()));
										totalDistance -= coloaderRatesDO
												.getFreeKm();
									}

									setOTandPerHourRate(coloaderRatesDO,
											totalDistance, ot,
											coloadingVehicleContractDO);
									if (coloadingVehicleContractDO
											.getVendorId() != null) {
										double fuelRate = getFuelRateForVendorCity(
												session,
												coloadingVehicleContractDO
												.getVendorId(),
												coloadingVehicleContractDO
												.getFuelType());
										if (fuelRate != 0) {
											// setting Fuel Rate
											if (coloadingVehicleContractDO
													.getAverage() != null
													&& coloadingVehicleContractDO
													.getAverage() != 0) {
												coloaderRatesDO
												.setFuelRate((totalDistance / coloadingVehicleContractDO
														.getAverage())
														* fuelRate);
											}
											if (coloaderRatesDO.getFuelRate() != null
													&& coloaderRatesDO
													.getFuelRate() != 0) {
												if (coloaderRatesDO.getTotal() != null) {
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getTotal()
															+ coloaderRatesDO
															.getFuelRate());
												} else {
													coloaderRatesDO
													.setTotal(coloaderRatesDO
															.getFuelRate());
												}

											}
										}
									}

									coloaderRatesDO
									.setSubRateType(ColoadingConstants.RATE_TYPE_PER_DAY_FUEL);
								}
								/*calculateAllTaxes(coloaderRatesDO, serviceTax,
										educationCess, higherEducationCess);*/
								calculateAllTaxes(session,coloaderRatesDO);
								// Setting Gross Total charges(Gross Total =
								// Total+Service Tax);
								if (coloaderRatesDO.getGrossTotal() != null) {
									// Gross Total = Total+Service Tax +
									// Educational Cess + Higher Educational
									// Cess);
									coloaderRatesDO
									.setGrossTotal(coloaderRatesDO
											.getGrossTotal()
											+ coloaderRatesDO
											.getTotal()
											+ coloaderRatesDO
											.getServiceTax()
											+ coloaderRatesDO
											.getEducationalCess()
											+ coloaderRatesDO
											.getHigherEducationalCess());

								} else {
									coloaderRatesDO
									.setGrossTotal(coloaderRatesDO
											.getTotal()
											+ coloaderRatesDO
											.getServiceTax()
											+ coloaderRatesDO
											.getEducationalCess()
											+ coloaderRatesDO
											.getHigherEducationalCess());
								}
								// Setting set Net Effective Amounts
								coloaderRatesDO
								.setNetEffectiveAmts(coloaderRatesDO
										.getGrossTotal());
								// Setting Quantity
								coloaderRatesDO.setQuantity(actualDistance);

								// Setting UOM
								coloaderRatesDO.setUom(ColoadingConstants.KM);
								if (coloadingVehicleContractDO.getVendorId() != null
										&& coloadingVehicleContractDO
										.getVendorId() != 0) {
									coloaderRatesDO
									.setVendorId(coloadingVehicleContractDO
											.getVendorId());
								}
								//
								coloaderRatesDO
								.setTripSheetNumber(serviceEntryDO
										.getId());
								// Setting Office for Vendor
								coloaderRatesDO.setOfficeId(serviceEntryDO
										.getOfficeId());

								saveRates(session, coloaderRatesDO);
								serviceEntryDO
								.setIsRateCalculated(CommonConstants.YES);
								session.save(serviceEntryDO);
								session.flush();
							} else {
								date = null;
								vehicleNumber = null;
							}
						}

					}
				}
				sameEntry = null;
				coloadingVehicleServiceEntryDOs = null;
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingRateCalculationDAOImpl::calculateAndSaveRateForVehicle()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForVehicle() :: END");

	}

	@Override
	public void calculateAndSaveRateForVehicleMonthly()
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForVehicleMonthly() :: Start");
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			if (session != null) {
				// Getting ColoadingVehicleServiceEntry list
				criteria = session
						.createCriteria(ColoadingVehicleServiceEntryDO.class);
				criteria.add(Restrictions.eq("isRateCalculated",
						CommonConstants.NO));
				List<ColoadingVehicleServiceEntryDO> coloadingVehicleServiceEntryDOs = criteria
						.list();
				if (!CGCollectionUtils.isEmpty(coloadingVehicleServiceEntryDOs)) {
					Date date = null;
					String vehicleNumber = null;
					ColoaderRatesDO coloaderRatesDO = null;
					// Fetching Service Tax Rate Component
					//Double serviceTax = getServiceTax(session);
					//Double educationCess = getEducationCessTax(session);
					//Double higherEducationCess = getHigherEducationCessTax(session);
					for (ColoadingVehicleServiceEntryDO serviceEntryDO : coloadingVehicleServiceEntryDOs) {
						if (date != null
								&& vehicleNumber != null
								&& date.equals(serviceEntryDO.getDate())
								&& vehicleNumber.equals(serviceEntryDO
										.getVehNumber())) {
							serviceEntryDO
							.setIsRateCalculated(CommonConstants.YES);
							session.save(serviceEntryDO);
							session.flush();
							continue;
						}
						double totalDistance = 0;
						double ot = 0;
						for (ColoadingVehicleServiceEntryDO entryDO : coloadingVehicleServiceEntryDOs) {
							if (entryDO.getIsRateCalculated().equals(
									CommonConstants.NO)) {
								if (date == null && vehicleNumber == null) {
									date = entryDO.getDate();
									vehicleNumber = entryDO.getVehNumber();
									totalDistance = entryDO.getClosingKm()
											- entryDO.getOpeningKm();
									ot = entryDO.getOt();
								} else {
									if (date.equals(entryDO.getDate())
											&& vehicleNumber.equals(entryDO
													.getVehNumber())) {
										totalDistance += entryDO.getClosingKm()
												- entryDO.getOpeningKm();
										ot += entryDO.getOt();
									}
								}
							} else {
								date = null;
								vehicleNumber = null;
								totalDistance = 0;
								ot = 0;
							}
						}
						double actualDistance = totalDistance;
						coloaderRatesDO = new ColoaderRatesDO();
						coloaderRatesDO.setDispatchDate(serviceEntryDO
								.getCreatedDate());
						coloaderRatesDO.setTransportRefNo(vehicleNumber);
						if (coloaderRatesDO.getTransportRefNo() != null) {
							criteria = session
									.createCriteria(ColoadingVehicleContractDO.class);
							criteria.add(Restrictions.eq("vehicleNo",
									coloaderRatesDO.getTransportRefNo()));
							criteria.add(Restrictions.le(
									"effectiveFrom",
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(DateUtil
											.getCurrentDateInDDMMYYYY())));
							criteria.add(Restrictions.eq("storeStatus",
									ColoadingConstants.SUBMIT_CHAR));
							ColoadingVehicleContractDO coloadingVehicleContractDO = (ColoadingVehicleContractDO) criteria
									.uniqueResult();
							if (coloadingVehicleContractDO != null) {
								coloaderRatesDO
								.setServiceType(ColoadingConstants.ROAD);
								// Setting Rent
								if (coloadingVehicleContractDO.getRent() != null) {
									coloaderRatesDO
									.setTotal(coloadingVehicleContractDO
											.getRent());
								}
								// Setting Other Charges IF applicable
								if (coloadingVehicleContractDO.getOthers() != null) {
									if (coloaderRatesDO.getTotal() != null) {
										coloaderRatesDO
										.setTotal(coloaderRatesDO
												.getTotal()
												+ coloadingVehicleContractDO
												.getOthers());
									} else {
										coloaderRatesDO
										.setTotal(coloadingVehicleContractDO
												.getOthers()
												.doubleValue());
									}
								}
								if (ColoadingConstants.RATE_TYPE_FIXED_MONTH
										.equals(coloadingVehicleContractDO
												.getRateType())) {
									if (coloadingVehicleContractDO.getFreeKm() != null
											&& coloadingVehicleContractDO
											.getFreeKm() != 0) {
										// Setting Free KM
										coloaderRatesDO.setFreeKm(new Double(
												coloadingVehicleContractDO
												.getFreeKm()));
										totalDistance -= coloaderRatesDO
												.getFreeKm();
									}

									setOTandPerHourRate(coloaderRatesDO,
											totalDistance, ot,
											coloadingVehicleContractDO);
									coloaderRatesDO
									.setSubRateType(ColoadingConstants.RATE_TYPE_FIXED_MONTH);

								} else if (ColoadingConstants.RATE_TYPE_PER_MONTH_FUEL
										.equals(coloadingVehicleContractDO
												.getRateType())) {
									if (coloadingVehicleContractDO.getFreeKm() != null
											&& coloadingVehicleContractDO
											.getFreeKm() != 0) {
										// Setting Free KM
										coloaderRatesDO.setFreeKm(new Double(
												coloadingVehicleContractDO
												.getFreeKm()));
										totalDistance -= coloaderRatesDO
												.getFreeKm();
									}

									setOTandPerHourRate(coloaderRatesDO,
											totalDistance, ot,
											coloadingVehicleContractDO);

									if (totalDistance > 0 && coloadingVehicleContractDO
											.getVendorId() != null) {
										double fuelRate = getFuelRateForVendorCity(
												session,
												coloadingVehicleContractDO
												.getVendorId(),
												coloadingVehicleContractDO
												.getFuelType());
										if (fuelRate != 0) {
											// setting Fuel Rate
											if (coloadingVehicleContractDO
													.getAverage() != null
													&& coloadingVehicleContractDO
													.getAverage() != 0) {
												coloaderRatesDO
												.setFuelRate((totalDistance / coloadingVehicleContractDO
														.getAverage())
														* fuelRate);
											}
											if (coloaderRatesDO.getFuelRate() != null
													&& coloaderRatesDO
													.getFuelRate() != 0) {
												coloaderRatesDO
												.setGrossTotal(coloaderRatesDO
														.getFuelRate());
											}
										}
									}
									coloaderRatesDO
									.setSubRateType(ColoadingConstants.RATE_TYPE_PER_MONTH_FUEL);
								}
								/*calculateAllTaxes(coloaderRatesDO, serviceTax,
										educationCess, higherEducationCess);*/
								calculateAllTaxes(session,coloaderRatesDO);
								// Setting Gross Total charges(Gross Total =
								// Total+Service Tax);
								if (coloaderRatesDO.getGrossTotal() != null) {
									coloaderRatesDO
									.setGrossTotal(coloaderRatesDO
											.getGrossTotal()
											+ coloaderRatesDO
											.getTotal()
											+ coloaderRatesDO
											.getServiceTax()
											+ coloaderRatesDO
											.getEducationalCess()
											+ coloaderRatesDO
											.getHigherEducationalCess());
								} else {
									coloaderRatesDO
									.setGrossTotal(coloaderRatesDO
											.getTotal()
											+ coloaderRatesDO
											.getServiceTax()
											+ coloaderRatesDO
											.getEducationalCess()
											+ coloaderRatesDO
											.getHigherEducationalCess());
								}
								// Setting set Net Effective Amounts
								coloaderRatesDO
								.setNetEffectiveAmts(coloaderRatesDO
										.getGrossTotal());
								// Setting Quantity
								coloaderRatesDO.setQuantity(actualDistance);

								// Setting UOM
								coloaderRatesDO.setUom(ColoadingConstants.KM);
								if (coloadingVehicleContractDO.getVendorId() != null
										&& coloadingVehicleContractDO
										.getVendorId() != 0) {
									coloaderRatesDO
									.setVendorId(coloadingVehicleContractDO
											.getVendorId());

								}
								//
								coloaderRatesDO
								.setTripSheetNumber(serviceEntryDO
										.getId());
								// Setting OfficeId
								coloaderRatesDO.setOfficeId(serviceEntryDO
										.getOfficeId());

								saveRates(session, coloaderRatesDO);
								serviceEntryDO
								.setIsRateCalculated(CommonConstants.YES);
								session.save(serviceEntryDO);
								session.flush();
							} else {
								date = null;
								vehicleNumber = null;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ColoadingRateCalculationDAOImpl::calculateAndSaveRateForVehicleMonthly()",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAndSaveRateForVehicleMonthly() :: END");

	}

	private void setOTandPerHourRate(ColoaderRatesDO coloaderRatesDO,
			double totalDistance, double ot,
			ColoadingVehicleContractDO coloadingVehicleContractDO) {
		if (totalDistance > 0) {
			if (coloadingVehicleContractDO.getPerKmRate() != null) {
				// Setting Per KM rate
				coloaderRatesDO.setPerKmRate(new Double(
						coloadingVehicleContractDO.getPerKmRate()
						* totalDistance));
				if (coloaderRatesDO.getTotal() != null) {
					coloaderRatesDO.setTotal(coloaderRatesDO.getTotal()
							+ coloaderRatesDO.getPerKmRate());
				} else {
					coloaderRatesDO.setTotal(coloaderRatesDO.getPerKmRate());
				}
			}
		}

		if (coloadingVehicleContractDO.getPerHourRate() != null && ot > 0) {
			// Setting Per Hour rate
			coloaderRatesDO.setPerHrOt(new Double(coloadingVehicleContractDO
					.getPerHourRate() * ot));
			if (coloaderRatesDO.getTotal() != null) {
				coloaderRatesDO.setTotal(coloaderRatesDO.getTotal()
						+ coloaderRatesDO.getPerHrOt());
			} else {
				coloaderRatesDO.setTotal(coloaderRatesDO.getPerHrOt());
			}
		}
	}

	private double getFuelRateForVendorCity(Session session, Integer vendorId,
			String fuelType) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getFuelRateForVendorCity() :: START");
		double fuelRate = 0;
		Criteria criteria = session.createCriteria(LoadMovementVendorDO.class)
				.setProjection(Projections.property("pincode"));
		criteria.add(Restrictions.eq("vendorId", vendorId));
		String pincode = (String) criteria.uniqueResult();
		if (pincode != null) {
			criteria = session.createCriteria(PincodeDO.class).setProjection(
					Projections.property("cityId"));
			criteria.add(Restrictions.eq("pincode", pincode));
			Integer cityId = (Integer) criteria.uniqueResult();
			if (cityId != null) {
				criteria = session
						.createCriteria(ColoadingFuelRateEntryDO.class);
				criteria.add(Restrictions.eq("cityId", cityId));
				criteria.add(Restrictions.le("effectiveFrom", DateUtil
						.slashDelimitedstringToDDMMYYYYFormat(DateUtil
								.getCurrentDateInDDMMYYYY())));
				criteria.add(Restrictions.eq("storeStatus",
						ColoadingConstants.SUBMIT_CHAR));
				ColoadingFuelRateEntryDO coloadingFuelRateEntryDO = (ColoadingFuelRateEntryDO) criteria
						.uniqueResult();
				if (coloadingFuelRateEntryDO != null) {
					switch (fuelType) {
					case ColoadingConstants.PETROL:
						fuelRate = coloadingFuelRateEntryDO.getPetrol();
						break;
					case ColoadingConstants.DIESEL:
						fuelRate = coloadingFuelRateEntryDO.getDiesel();
						break;
					case ColoadingConstants.CNG:
						fuelRate = coloadingFuelRateEntryDO.getCng();
						break;
					case ColoadingConstants.LPG:
						fuelRate = coloadingFuelRateEntryDO.getLpg();
						break;
					}
				}
			}
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getFuelRateForVendorCity() :: END");
		return fuelRate;
	}

	/*
	 * private Integer getOfficeForVendor(Session session, ColoaderRatesDO
	 * coloaderRatesDO) { LOGGER.debug(
	 * "ColoadingRateCalculationDAOImpl :: getOfficeForVendor() :: START");
	 * Integer officeId = null; Criteria criteria =
	 * session.createCriteria(LoadMovementVendorDO.class)
	 * .setProjection(Projections.property("officeDO.officeId"));
	 * criteria.add(Restrictions.eq("vendorId", coloaderRatesDO.getVendorId()));
	 * LoadMovementVendorDO vendorDO = (LoadMovementVendorDO) criteria
	 * .uniqueResult(); if (vendorDO != null) { officeId =
	 * vendorDO.getOfficeDO().getOfficeId(); }
	 * LOGGER.debug("ColoadingRateCalculationDAOImpl :: getOfficeForVendor() :: END"
	 * ); return officeId; }
	 */

	private void saveRates(Session session, ColoaderRatesDO coloaderRatesDO) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: saveRates() :: START");
		// Setting Common Properties
		coloaderRatesDO.setDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat()));
		// Saving Calculated Rate
		session.save(coloaderRatesDO);
		coloaderRatesDO = null;
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: saveRates() :: END");
	}

	/*private void calculateAllTaxes(ColoaderRatesDO coloaderRatesDO,
			Double serviceTax, Double educationCess, Double higherEducationCess) {*/
	private void calculateAllTaxes(Session session,ColoaderRatesDO coloaderRatesDO) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAllTaxes() :: START");
		// Setting Service Tax
		String dispatchDateStr= DateUtil.getDDMMYYYYDateToString(coloaderRatesDO.getDispatchDate());
		Date dispatchDate= DateUtil.slashDelimitedstringToDDMMYYYYFormat(dispatchDateStr);
		
		Double serviceTax = getServiceTax(session,dispatchDate);
		Double educationCess = getEducationCessTax(session,dispatchDate);
		Double higherEducationCess = getHigherEducationCessTax(session,dispatchDate);

		if (serviceTax != null) {
			coloaderRatesDO
			.setServiceTax((coloaderRatesDO.getTotal() * serviceTax) / 100);
			coloaderRatesDO
			.setEducationalCess((coloaderRatesDO.getServiceTax() * educationCess) / 100);
			coloaderRatesDO.setHigherEducationalCess((coloaderRatesDO
					.getServiceTax() * higherEducationCess) / 100);
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: calculateAllTaxes() :: END");
	}

	private List<LoadMovementDO> getLoadMovment(Session session,
			int transportModeId) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getLoadMovment() :: START");
		Criteria criteria = session.createCriteria(LoadMovementDO.class);
		criteria.createAlias("loadConnectedDOs", "loadConnectedDOs");
		// To check CD weight is Null
		criteria.add(Restrictions.isNotNull("loadConnectedDOs.connectWeight"));
		criteria.add(Restrictions.isNotNull("loadConnectedDOs.tokenNumber"));
		criteria.add(Restrictions.ne("loadConnectedDOs.tokenNumber", ""));
		if (transportModeId == 1) {
			criteria.add(Restrictions.or(Restrictions.eq(
					"loadConnectedDOs.coloadingDispatchedUsing",
					ColoadingConstants.CD), Restrictions.eq(
							"loadConnectedDOs.coloadingDispatchedUsing",
							ColoadingConstants.AWB)));
		} else if (transportModeId == 2) {
			criteria.add(Restrictions.eq(
					"loadConnectedDOs.coloadingDispatchedUsing",
					ColoadingConstants.RR));
		}
		criteria.add(Restrictions.eq(
				"loadConnectedDOs.coloadingRateCalculated", CommonConstants.NO));
		criteria.createAlias("transportModeDO", "transportModeDO");
		criteria.createAlias("tripServicedByDO", "tripServicedByDO")
		.createAlias("tripServicedByDO.servicedByDO", "servicedByDO")
		.createAlias("servicedByDO.serviceByTypeDO", "serviceByTypeDO");
		criteria.add(Restrictions.eq("movementDirection",
				ColoadingConstants.MOVEMENT_DIRECTION));
		criteria.add(Restrictions.eq("transportModeDO.transportModeId",
				transportModeId));
		criteria.add(Restrictions.eq("serviceByTypeDO.serviceByTypeCode",
				ColoadingConstants.COLOADER));
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getLoadMovment() :: END");
		return criteria.list();
	}

	private Double getServiceTax(Session session,Date dispatchDate) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getServiceTax() :: START");
		Criteria criteria=null;
		// Fetching Service Tax Rate Component
		criteria = session.createCriteria(RateTaxComponentDO.class)
				.setProjection(
						Projections.projectionList().add(
								Projections.property("taxPercentile"),
								"taxPercentile"));
		criteria.add(Restrictions.eq("activeStatus", "A"));
		criteria.add(Restrictions.eq("taxGroup", "SEC"));
		criteria.add(Restrictions.eq("rateComponentCode", "SRVTX"));
		criteria.add(Restrictions.le("effectiveFrom", dispatchDate));
		criteria.add(Restrictions.ge("effectiveTo", dispatchDate));
		Double st = (Double) criteria.uniqueResult();
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: ServiceTax::" + st);
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getServiceTax() :: END");
		return st;
	}

	private Double getEducationCessTax(Session session,Date dispatchDate) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getEducationCessTax() :: START");
		// Fetching Education Cess Tax
		Criteria criteria=null;
		criteria = session.createCriteria(RateTaxComponentDO.class)
				.setProjection(
						Projections.projectionList().add(
								Projections.property("taxPercentile"),
								"taxPercentile"));
		criteria.add(Restrictions.eq("activeStatus", "A"));
		criteria.add(Restrictions.eq("taxGroup", "SEC"));
		criteria.add(Restrictions.eq("rateComponentCode", "EDUCS"));
		criteria.add(Restrictions.le("effectiveFrom", dispatchDate));
		criteria.add(Restrictions.ge("effectiveTo", dispatchDate));
		Double ect = (Double) criteria.uniqueResult();
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: Education Cess Tax::"
				+ ect);
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getEducationCessTax() :: END");
		return ect;
	}

	private Double getHigherEducationCessTax(Session session,Date dispatchDate) {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getHigherEducationCessTax() :: START");
		// Fetching Higher Education Cess Tax
		Criteria criteria=null;
		criteria = session.createCriteria(RateTaxComponentDO.class)
				.setProjection(
						Projections.projectionList().add(
								Projections.property("taxPercentile"),
								"taxPercentile"));
		criteria.add(Restrictions.eq("activeStatus", "A"));
		criteria.add(Restrictions.eq("taxGroup", "SEC"));
		criteria.add(Restrictions.eq("rateComponentCode", "HEDCS"));
		criteria.add(Restrictions.le("effectiveFrom", dispatchDate));
		criteria.add(Restrictions.ge("effectiveTo", dispatchDate));
		Double hect = (Double) criteria.uniqueResult();
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: Higher Education Cess Tax::"
				+ hect);
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: getHigherEducationCessTax() :: END");
		return hect;
	}

	private ColoaderRatesDO createRateCalculationDO(
			LoadMovementDO loadMovementDO) {
		ColoaderRatesDO coloaderRatesDO = new ColoaderRatesDO();
		if (ColoadingConstants.M.equalsIgnoreCase(loadMovementDO
				.getRouteServicedTransportType())) {
			if (loadMovementDO.getTripServicedByDO().getTripDO()
					.getTransportDO().getFlightDO() != null) {
				// Setting Flight Number
				coloaderRatesDO.setTransportRefNo(loadMovementDO
						.getTripServicedByDO().getTripDO().getTransportDO()
						.getFlightDO().getFlightNumber());
			}
		} else {
			// Setting Flight Number
			coloaderRatesDO.setTransportRefNo(loadMovementDO
					.getRouteServicedTransportNumber());
		}
		if (loadMovementDO.getTripServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO()
				.getLoadMovementVendorDO() != null) {
			// Setting vendor id
			coloaderRatesDO.setVendorId(loadMovementDO.getTripServicedByDO()
					.getServicedByDO().getLoadMovementVendorDO().getVendorId());
		}
		// Setting Unit Of Measurement
		coloaderRatesDO.setUom(ColoadingConstants.Each_BAG);
		return coloaderRatesDO;
	}

	private ColoaderRatesDO createRateCalculationTrainDO(
			LoadMovementDO loadMovementDO) {
		ColoaderRatesDO coloaderRatesDO = new ColoaderRatesDO();
		if (ColoadingConstants.M.equalsIgnoreCase(loadMovementDO
				.getRouteServicedTransportType())) {
			if (loadMovementDO.getTripServicedByDO().getTripDO()
					.getTransportDO().getTrainDO() != null) {
				// Setting Train Number
				coloaderRatesDO.setTransportRefNo(loadMovementDO
						.getTripServicedByDO().getTripDO().getTransportDO()
						.getTrainDO().getTrainNumber());
			}
		} else {
			// Setting Train Number
			coloaderRatesDO.setTransportRefNo(loadMovementDO
					.getRouteServicedTransportNumber());
		}

		if (loadMovementDO.getTripServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO()
				.getLoadMovementVendorDO() != null) {
			// Setting vendor id
			coloaderRatesDO.setVendorId(loadMovementDO.getTripServicedByDO()
					.getServicedByDO().getLoadMovementVendorDO().getVendorId());
		}
		// Setting Unit Of Measurement
		coloaderRatesDO.setUom(ColoadingConstants.Each_BAG);
		return coloaderRatesDO;
	}

	private ColoaderRatesDO createRateCalculationRoadDO(
			LoadMovementDO loadMovementDO) {
		ColoaderRatesDO coloaderRatesDO = new ColoaderRatesDO();

		if (loadMovementDO.getVehicleType() != null) {
			if (ColoadingConstants.M.equalsIgnoreCase(loadMovementDO
					.getVehicleType())) {
				if (loadMovementDO.getVehicleDO().getRegNumber() != null) {
					// Setting Vehicle Number
					coloaderRatesDO.setTransportRefNo(loadMovementDO
							.getTripServicedByDO().getTripDO().getTransportDO()
							.getVehicleDO().getRegNumber());
				}
			} else {
				// Setting Vehicle Number
				coloaderRatesDO.setTransportRefNo(loadMovementDO
						.getVehicleRegNumber());
			}
		} else {

			if (ColoadingConstants.M.equalsIgnoreCase(loadMovementDO
					.getRouteServicedTransportType())) {
				if (loadMovementDO.getTripServicedByDO().getTripDO()
						.getTransportDO().getVehicleDO() != null) {
					// Setting Vehicle Number
					coloaderRatesDO.setTransportRefNo(loadMovementDO
							.getTripServicedByDO().getTripDO().getTransportDO()
							.getVehicleDO().getRegNumber());
				}
			} else {
				// Setting Vehicle Number
				coloaderRatesDO.setTransportRefNo(loadMovementDO
						.getRouteServicedTransportNumber());
			}
		}

		if (loadMovementDO.getTripServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO() != null
				&& loadMovementDO.getTripServicedByDO().getServicedByDO()
				.getLoadMovementVendorDO() != null) {
			// Setting vendor id
			coloaderRatesDO.setVendorId(loadMovementDO.getTripServicedByDO()
					.getServicedByDO().getLoadMovementVendorDO().getVendorId());
		}
		// Setting Unit Of Measurement
		return coloaderRatesDO;
	}

	private List<ColoaderRatesDO> getColoderRatesDOList(
			LoadMovementDO loadMovementDO, String type) {
		List<ColoaderRatesDO> ratesDOs = null;

		Set<LoadConnectedDO> connectedDOs = loadMovementDO
				.getLoadConnectedDOs();

		if (!CGCollectionUtils.isEmpty(connectedDOs)) {
			ColoaderRatesDO coloaderRatesDO = null;
			if (ColoadingConstants.AIR.equals(type)) {
				coloaderRatesDO = createRateCalculationDO(loadMovementDO);
			} else if (ColoadingConstants.TRAIN.equals(type)) {
				coloaderRatesDO = createRateCalculationTrainDO(loadMovementDO);
			} else if (ColoadingConstants.ROAD.equals(type)) {
				coloaderRatesDO = createRateCalculationRoadDO(loadMovementDO);
			}

			// Setting Origin City
			if (loadMovementDO.getOriginOfficeDO() != null) {
				coloaderRatesDO.setOrigin_id(loadMovementDO.getOriginOfficeDO()
						.getCityId());
			}
			// Setting Destination City
			if (loadMovementDO.getDestOfficeDO() != null) {
				coloaderRatesDO.setDestination_id(loadMovementDO
						.getDestOfficeDO().getCityId());
			}
			coloaderRatesDO.setOfficeId(loadMovementDO.getOperatingOffice());
			coloaderRatesDO.setDispatchNumber(loadMovementDO
					.getGatePassNumber());

			List<ColoaderRatesDO> ratesTempDOs = null;
			for (LoadConnectedDO connectedDO : connectedDOs) {
				ratesTempDOs = new ArrayList<>(connectedDOs.size());
				if (ratesDOs != null) {
					for (ColoaderRatesDO ratesDO : ratesDOs) {
						if (ratesDO.getAwb_cd_rr_number().equals(connectedDO.getTokenNumber())) {
							coloaderRatesDO.setWeight(coloaderRatesDO.getWeight() + connectedDO.getConnectWeight());
							coloaderRatesDO.setQuantity(coloaderRatesDO.getQuantity() + 1d);
						} else {
							// Setting AWB_CD_RR number
							coloaderRatesDO.setAwb_cd_rr_number(connectedDO
									.getTokenNumber());
							// Setting Weight
							coloaderRatesDO.setWeight(connectedDO
									.getConnectWeight());
							// Setting DispatchDate
							coloaderRatesDO.setDispatchDate(loadMovementDO
									.getLoadingDate());
							coloaderRatesDO.setQuantity(1d);
							coloaderRatesDO.setSubRateType(connectedDO
									.getColoadingDispatchedUsing());
							coloaderRatesDO.setConnectedAs(connectedDO
									.getColoadingDispatchedType());
							ratesTempDOs.add(coloaderRatesDO);
						}
					}
				} else {
					ratesDOs = new ArrayList<>();
					// Setting AWB_CD_RR number
					coloaderRatesDO.setAwb_cd_rr_number(connectedDO
							.getTokenNumber());
					// Setting Weight
					coloaderRatesDO.setWeight(connectedDO.getConnectWeight());
					// Setting DispatchDate
					coloaderRatesDO.setDispatchDate(loadMovementDO
							.getLoadingDate());
					coloaderRatesDO.setQuantity(1d);
					coloaderRatesDO.setSubRateType(connectedDO
							.getColoadingDispatchedUsing());
					coloaderRatesDO.setConnectedAs(connectedDO
							.getColoadingDispatchedType());
					ratesTempDOs.add(coloaderRatesDO);
				}
				//				ratesDOs.clear();
				ratesDOs.addAll(ratesTempDOs);
			}
			//			ratesTempDOs = null;
			//			ratesTempDOs.clear();
		}
		return ratesDOs;
	}

	private void updateIsRateCalculatedFlag(
			List<ColoaderRatesDO> coloaderRatesDOs, Session session)
					throws CGSystemException {
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: updateIsRateCalculatedFlag() :: Start --------> ::::::");
		Set<String> tokenNumbers = new HashSet<>(coloaderRatesDOs.size());
		for (ColoaderRatesDO ratesDO : coloaderRatesDOs) {
			tokenNumbers.add(ratesDO.getAwb_cd_rr_number());
		}
		if (!CGCollectionUtils.isEmpty(tokenNumbers)) {
			Query query = session
					.getNamedQuery(ColoadingConstants.QRY_UPDATE_RATE_CALCULATED);
			query.setParameterList("tokenNumbers", tokenNumbers);
			query.executeUpdate();
		}
		LOGGER.debug("ColoadingRateCalculationDAOImpl :: updateIsRateCalculatedFlag() :: End --------> ::::::");
	}

	private ColoadingAirDO getCurrentRates(Session session,
			ColoaderRatesDO coloaderRatesDO, ColoadingAirDO airDO) {
		Criteria criteria;
		//Date currentDate = DateUtil.getCurrentDate();
		//if (airDO.getEffectiveFrom().after(currentDate)) {
		/*Date previusDate = DateUtil.getPreviousDateFromGivenDate(airDO
					.getEffectiveFrom());*/
		String dispatchDateStr= DateUtil.getDDMMYYYYDateToString(coloaderRatesDO.getDispatchDate());
		Date dispatchDate= DateUtil.slashDelimitedstringToDDMMYYYYFormat(dispatchDateStr);
		criteria = session.createCriteria(ColoadingAirDO.class);
		criteria.add(Restrictions.eq("vendorId",
				coloaderRatesDO.getVendorId()));
		criteria.add(Restrictions.eq("originCityId",
				coloaderRatesDO.getOrigin_id()));
		criteria.add(Restrictions.eq("destinationCityId",
				coloaderRatesDO.getDestination_id()));
		criteria.add(Restrictions.eq("storeStatus",
				ColoadingConstants.RENEW));
		criteria.add(Restrictions.eq("cdType",
				coloaderRatesDO.getSubRateType()));
		//criteria.add(Restrictions.eq("effectiveTill",previusDate));
		criteria.add(Restrictions.le("effectiveFrom",dispatchDate));
		criteria.add(Restrictions.ge("effectiveTill",dispatchDate));

		if (ColoadingConstants.AWB.equalsIgnoreCase(coloaderRatesDO
				.getSubRateType())) {
			criteria.createAlias("coloadingAirAwbs", "coloadingAirAwbs");
			criteria.add(Restrictions.eq("coloadingAirAwbs.flightNo",
					coloaderRatesDO.getTransportRefNo()));
		} else {
			criteria.createAlias("coloadingAirCds", "coloadingAirCds");
			criteria.add(Restrictions.eq("coloadingAirCds.flightNo",
					coloaderRatesDO.getTransportRefNo()));
		}
		ColoadingAirDO currentAirDo = (ColoadingAirDO) criteria
				.uniqueResult();
		if (currentAirDo != null) {
			airDO = currentAirDo;
		}
		//}
		return airDO;
	}

	private ColoadingTrainContractDO getCurrentRates(Session session,
			ColoaderRatesDO coloaderRatesDO, ColoadingTrainContractDO contractDO) {
		Criteria criteria;
		/*Date currentDate = DateUtil.getCurrentDate();
		if (contractDO.getEffectiveFrom().after(currentDate)) {*/	
		String dispatchDateStr= DateUtil.getDDMMYYYYDateToString(coloaderRatesDO.getDispatchDate());
		Date dispatchDate= DateUtil.slashDelimitedstringToDDMMYYYYFormat(dispatchDateStr);
		
			criteria = session.createCriteria(ColoadingTrainContractDO.class);
			criteria.createAlias("coloadingTrainContractRateDtls",
					"coloadingTrainContractRateDtls");
			criteria.add(Restrictions.eq("vendorId",
					coloaderRatesDO.getVendorId()));

			criteria.add(Restrictions.eq("originCityId",
					coloaderRatesDO.getOrigin_id()));
			criteria.add(Restrictions.eq("destCityId",
					coloaderRatesDO.getDestination_id()));
			criteria.add(Restrictions.eq("storeStatus",
					ColoadingConstants.RENEW));
			criteria.add(Restrictions.le("effectiveFrom",dispatchDate));
			criteria.add(Restrictions.ge("effectiveTill",dispatchDate));
			criteria.add(Restrictions.eq(
					"coloadingTrainContractRateDtls.trainNo",
					coloaderRatesDO.getTransportRefNo()));

			ColoadingTrainContractDO coloadingTrainContractDO = (ColoadingTrainContractDO) criteria
					.uniqueResult();
			if (coloadingTrainContractDO != null) {
				contractDO = coloadingTrainContractDO;
			}
		return contractDO;
	}

}
