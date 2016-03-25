/**
 * 
 */
package com.cg.lbs.bcun.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author mohammes
 *
 */
public final class InboundBranchIntializer  implements InitializingBean{
	
	/**
	 *  Log the message of the process.
	 */
	private static Logger logger = LoggerFactory.getLogger(InboundBranchIntializer.class);

	InboundBranchIntializerHelper inboundBranchIntializerHelper;

	/**
	 * @return the inboundBranchIntializerHelper
	 */
	public InboundBranchIntializerHelper getInboundBranchIntializerHelper() {
		return inboundBranchIntializerHelper;
	}

	/**
	 * @param inboundBranchIntializerHelper the inboundBranchIntializerHelper to set
	 */
	public void setInboundBranchIntializerHelper(
			InboundBranchIntializerHelper inboundBranchIntializerHelper) {
		this.inboundBranchIntializerHelper = inboundBranchIntializerHelper;
		
	}
	
	
	public void inboundBranchSetUp(){
		logger.info("InboundBranchIntializer :: InboundBranchSetUp :: START");
		try {
			inboundBranchIntializerHelper.branchSetUp();
		} catch (Exception e) {
			logger.error("InboundBranchIntializer :: InboundBranchSetUp :: ERROR",e);
		}
		logger.info("InboundBranchIntializer :: InboundBranchSetUp :: END");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("InboundBranchIntializer :: afterPropertiesSet :: START");
		inboundBranchSetUp();
		logger.debug("InboundBranchIntializer :: afterPropertiesSet :: END");
	}
	

}
