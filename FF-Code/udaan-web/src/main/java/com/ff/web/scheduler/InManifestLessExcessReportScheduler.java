package com.ff.web.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.web.manifest.inmanifest.service.InManifestCommonService;

public class InManifestLessExcessReportScheduler extends QuartzJobBean {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InManifestLessExcessReportScheduler.class);
	
	private InManifestCommonService inManifestCommonService;

	/**
	 * @param inManifestCommonService the inManifestCommonService to set
	 */
	public void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			inManifestCommonService.generateLessExcessReport();
		} catch (HttpException e) {
			LOGGER.error("InManifestLessExcessReportScheduler::executeInternal::HttpException::",e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("InManifestLessExcessReportScheduler::executeInternal::ClassNotFoundException::" ,e);
		} catch (IOException e) {
			LOGGER.error("InManifestLessExcessReportScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("InManifestLessExcessReportScheduler::executeInternal::Exception::" , e);
		}
	}

}
