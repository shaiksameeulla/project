package com.capgemini.lbs.centralserver.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.centralserver.messageproducer.QueueProducer;
import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Class QueueTestServlet.
 */
public class QueueTestServlet extends HttpServlet {
	
	private final static Logger LOGGER = LoggerFactory
	.getLogger(QueueTestServlet.class);
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("#########################Inside QueueTestServlet########################");
		CGBaseTO baseTO = new CGBaseTO();
		PickUpTO pickupTo = new PickUpTO();
		
		pickupTo.setCustomerType("Customer");
		pickupTo.setDomesticInt("I");
		pickupTo.setPickupSource("Source");
		
		List<PickUpTO> drsList=new ArrayList<PickUpTO>();
		drsList.add(pickupTo);
		baseTO.setBaseList(drsList);
		baseTO.setObjectType(PickUpTO.class);
		baseTO.setRequestType(ApplicationConstants.WRITE_REQUEST);
		baseTO.setBeanId("pickupDomesticService");
		baseTO.setClassType("PickUpDomesticService");
		baseTO.setMethodName("savePickUpGeneration");
		try {
			QueueProducer.sendMessage(baseTO);
		} catch (CGSystemException e) {
			LOGGER.error("QueueTestServlet::doGet::Exception occured:"
					+e.getMessage());
		}
	}
	
}
