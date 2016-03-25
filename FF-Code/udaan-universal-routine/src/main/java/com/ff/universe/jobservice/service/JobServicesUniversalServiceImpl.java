package com.ff.universe.jobservice.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.jobservice.JobServicesDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.jobservices.JobServicesTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.jobservice.dao.JobServicesUniversalDAO;

public class JobServicesUniversalServiceImpl implements
		JobServicesUniversalService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(JobServicesUniversalServiceImpl.class);

	private JobServicesUniversalDAO jobServicesUniversalDAO;

	private SequenceGeneratorService sequenceGeneratorService;

	/**
	 * @return the jobServicesDAO
	 */
	public JobServicesUniversalDAO getJobServicesUniversalDAO() {
		return jobServicesUniversalDAO;
	}

	/**
	 * @param jobServicesDAO
	 *            the jobServicesDAO to set
	 */
	public void setJobServicesUniversalDAO(
			JobServicesUniversalDAO jobServicesUniversalDAO) {
		this.jobServicesUniversalDAO = jobServicesUniversalDAO;
	}

	/**
	 * @return the sequenceGeneratorService
	 */
	public SequenceGeneratorService getSequenceGeneratorService() {
		return sequenceGeneratorService;
	}

	/**
	 * @param sequenceGeneratorService
	 *            the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	@Override
	public JobServicesTO saveOrUpdateJobService(JobServicesTO jobServicesTO)
			throws CGBusinessException, CGSystemException {

		boolean isJobCreated = Boolean.FALSE;

		JobServicesDO jobServicesDO = new JobServicesDO();

		if (!StringUtil.isNull(jobServicesTO)) {

			if (!StringUtil.isStringEmpty(jobServicesTO.getJobNumber())) {
				jobServicesDO = jobServicesUniversalDAO
						.getJobServiceDetails(jobServicesTO.getJobNumber());
			}

			convertJobServiceTOtoJObServicedO(jobServicesTO, jobServicesDO);

			isJobCreated = jobServicesUniversalDAO
					.saveOrUpdateJobService(jobServicesDO);

			if (isJobCreated) {
				jobServicesTO.setJobNumber(jobServicesDO.getJobNumber());
			}
		}
		return jobServicesTO;
	}

	/*
	 * @Override public void updateJobService(JobServicesTO jobServicesTO)
	 * throws CGBusinessException, CGSystemException {
	 * 
	 * boolean isJobUpdated = Boolean.FALSE;
	 * 
	 * //String jobNumber = null;
	 * 
	 * JobServicesDO jobServicesDO = new JobServicesDO();
	 * 
	 * convertJobServiceTOtoJObServicedO(jobServicesTO, jobServicesDO);
	 * 
	 * isJobUpdated =
	 * jobServicesUniversalDAO.saveOrUpdateJobService(jobServicesDO);
	 * 
	 * 
	 * 
	 * //return jobNumber; }
	 */

	private JobServicesDO convertJobServiceTOtoJObServicedO(
			JobServicesTO jobServicesTO, JobServicesDO jobServicesDO)
			throws CGBusinessException, CGSystemException {

		// jobServicesDO =
		// (JobServicesDO)CGObjectConverter.createDomainFromTo(jobServicesTO,
		// jobServicesDO);
		if (StringUtil.isStringEmpty(jobServicesTO.getJobNumber())) {
			String jobNumber = getJobServiceNumber(jobServicesTO
					.getProcessCode());
			if (!StringUtil.isNull(jobNumber)) {
				jobServicesDO.setJobNumber(jobNumber);
			} else {
				throw new CGBusinessException();
			}
		}

		/*
		 * if(!StringUtil.isEmptyInteger(jobServicesTO.getJobId())){
		 * jobServicesDO.setJobId(jobServicesTO.getJobId()); }
		 */

		// if(!StringUtil.isStringEmpty(jobServicesTO.getJobNumber())){

		// jobServicesDO.setJobNumber(jobServicesTO.getJobNumber()) ;
		// }

		if (!StringUtil.isStringEmpty(jobServicesTO.getProcessCode())) {

			jobServicesDO.setProcessCode(jobServicesTO.getProcessCode());
		}

		if (!StringUtil.isEmptyInteger(jobServicesTO.getOfficeId())) {

			jobServicesDO.setOfficeId(jobServicesTO.getOfficeId());
		}
		
		if (!StringUtil.isStringEmpty(jobServicesTO.getFileNameFailure())) {

			jobServicesDO.setFileNameFailure(jobServicesTO.getFileNameFailure());
		}
		
		if (!StringUtil.isStringEmpty(jobServicesTO.getFileNameSuccess())) {

			jobServicesDO.setFileNameSuccess(jobServicesTO.getFileNameSuccess());
		}
		
		if (!StringUtil.isNull(jobServicesTO.getFileSubmissionDate())) {

			jobServicesDO.setFileSubmissionDate(jobServicesTO
					.getFileSubmissionDate());
		}

		if (!StringUtil.isEmptyInteger(jobServicesTO.getPriority())) {

			jobServicesDO.setPriority(jobServicesTO.getPriority());
		}

		if (!StringUtil.isStringEmpty(jobServicesTO.getJobStatus())) {

			jobServicesDO.setJobStatus(jobServicesTO.getJobStatus());

		}

		if (!StringUtil.isStringEmpty(jobServicesTO.getRemarks())) {

			jobServicesDO.setRemarks(jobServicesTO.getRemarks());

		}

		if (!StringUtil.isEmptyInteger(jobServicesTO.getCreatdBy())) {

			jobServicesDO.setCreatdBy(jobServicesTO.getCreatdBy());

		}

		if (!StringUtil.isNull(jobServicesTO.getCreatedDate())) {

			jobServicesDO.setCreatedDate(jobServicesTO.getCreatedDate());

		}

		if (!StringUtil.isEmptyInteger(jobServicesTO.getUpdateBy())) {

			jobServicesDO.setUpdateBy(jobServicesTO.getUpdateBy());

		}

		if (!StringUtil.isNull(jobServicesTO.getUpdateDate())) {

			jobServicesDO.setUpdateDate(jobServicesTO.getUpdateDate());

		}

		if (!StringUtil.isEmptyInteger(jobServicesTO.getPercentageCompleted())) {

			jobServicesDO.setPercentageCompleted(jobServicesTO
					.getPercentageCompleted());

		}

		if (!StringUtil.isNull(jobServicesTO.getFailureFile())) {

			jobServicesDO.setFailureFile(jobServicesTO.getFailureFile());

		}
		if (!StringUtil.isNull(jobServicesTO.getSuccessFile())) {

			jobServicesDO.setSuccessFile(jobServicesTO.getSuccessFile());

		}

		return jobServicesDO;
	}

	private String getJobServiceNumber(String processCode)
			throws CGBusinessException, CGSystemException {
		List<String> seqNos = generateSequenceNo(1, "JOB_SERVICE_NO");
		String jobNumber = null;
		if (!CGCollectionUtils.isEmpty(seqNos)) {
			jobNumber = processCode + seqNos.get(0);
		}

		return jobNumber;
	}

	private List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("JobServicesUniversalServiceImpl::generateSequenceNo::START------------>:::::::");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(process);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSeq);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("JobServicesUniversalServiceImpl::generateSequenceNo::END------------>:::::::");
		return sequenceNumber;
	}

	private SequenceGeneratorConfigTO getGeneratedSequence(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException {
		return sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobServicesTO> searchJobService(String processCode,
			String jobNumber, String fromDate, String endDate)
			throws CGBusinessException, CGSystemException {

		List<JobServicesTO> jobServicesTOList = null;

		List<JobServicesDO> jobServicesDOList = null;

		Date frmDate = null;
		Date toDate = null;

		if (!StringUtil.isStringEmpty(fromDate)) {
			frmDate = DateUtil.combineDateWithTimeHHMMSS(fromDate, "");
		}

		if (!StringUtil.isStringEmpty(fromDate)) {
			toDate = DateUtil.combineDateWithTimeHHMMSS(endDate, "23:59:59");
		}

		jobServicesDOList = jobServicesUniversalDAO.searchJobService(
				processCode, jobNumber, frmDate, toDate);

		if (!CGCollectionUtils.isEmpty(jobServicesDOList)) {
			jobServicesTOList = (List<JobServicesTO>) CGObjectConverter
					.createTOListFromDomainList(jobServicesDOList,
							JobServicesTO.class);
		}

		return jobServicesTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getJobProcessList(String stdTypeName)
			throws CGBusinessException, CGSystemException {
		List<StockStandardTypeTO> jobProcessTOList = null;

		List<StockStandardTypeDO> jobProcessDOList = null;

		jobProcessDOList = jobServicesUniversalDAO
				.getJobProcessList(stdTypeName);

		if (!CGCollectionUtils.isEmpty(jobProcessDOList)) {
			jobProcessTOList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(jobProcessDOList,
							StockStandardTypeTO.class);
		}

		return jobProcessTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JobServicesTO getJobResponseFile(String jobNumber)
			throws CGBusinessException, CGSystemException {

		List<JobServicesTO> jobServicesTOList = null;

		List<JobServicesDO> jobServicesDOList = null;
		JobServicesTO jobServiceTO = null;

		jobServicesDOList = jobServicesUniversalDAO.searchJobService(null,
				jobNumber, null, null);

		if (!CGCollectionUtils.isEmpty(jobServicesDOList)) {
			jobServicesTOList = (List<JobServicesTO>) CGObjectConverter
					.createTOListFromDomainList(jobServicesDOList,
							JobServicesTO.class);
		}

		if (!CGCollectionUtils.isEmpty(jobServicesDOList)) {
			jobServiceTO = jobServicesTOList.get(0);
		}

		return jobServiceTO;
	}

}
