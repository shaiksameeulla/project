package com.ff.report.billing.action;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractBillingAction.
 *
 * @author narmdr
 */
public abstract class AbstractBillingAction extends CGBaseAction {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractBillingAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
}
