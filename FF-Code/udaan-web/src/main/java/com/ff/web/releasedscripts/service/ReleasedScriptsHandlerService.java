package com.ff.web.releasedscripts.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.service.BcunReleasedScriptsService;
/**
 * @author bmodala
 * 25Aug,2015
 * This service will call while server startUp and execute the released scripts on branch
 */
public class ReleasedScriptsHandlerService {

	/**
	 * Logger used to log the messages.
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(ReleasedScriptsHandlerService.class);
	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	private BcunReleasedScriptsService bcunReleasedScriptsService;

	public void setBcunReleasedScriptsService(
			BcunReleasedScriptsService bcunReleasedScriptsService) {
		this.bcunReleasedScriptsService = bcunReleasedScriptsService;
	}

	@PostConstruct
	protected void handleReleasedScripts(){	
		logger.info("ReleasedScriptsHandlerService::handleReleasedScripts::start===>");
		try{
			bcunReleasedScriptsService.processReleasedScriptsOnBranch();
		}catch(Exception ee){
			logger.error("ReleasedScriptsHandlerService::handleReleasedScripts Exception msg:::::>"+ee);
		}
		logger.info("ReleasedScriptsHandlerService::handleReleasedScripts::ends===>");

	}

}
