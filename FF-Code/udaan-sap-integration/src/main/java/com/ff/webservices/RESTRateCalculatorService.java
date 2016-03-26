/**
 * 
 */
package com.ff.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.rate.RateCalculationInputTO;

/**
 * @author prmeher
 *
 */
@Produces("application/json")
@Consumes("application/json")
public interface RESTRateCalculatorService {

	@POST
	@Path("/getCalculatedRates")
	public ConsignmentRateCalculationOutputTO calculateRate(RateCalculationInputTO rateCalculationInputTO) throws CGBusinessException, CGSystemException;
}
