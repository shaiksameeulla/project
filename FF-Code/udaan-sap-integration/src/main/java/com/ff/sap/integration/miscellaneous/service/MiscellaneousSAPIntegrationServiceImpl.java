/**
 * 
 */
package com.ff.sap.integration.miscellaneous.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingInterfaceWrapperDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.SAPLiabilityPaymentDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.mec.collection.SAPCollectionDO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.ExpenseEntriesDO;
import com.ff.domain.mec.expense.SAPExpenseDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPCollectionTO;
import com.ff.sap.integration.to.SAPExpenseTO;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;
import com.ff.sap.integration.to.SAPLiabilityPaymentTO;
import com.ff.sap.integration.to.SAPOutstandingPaymentTO;
import com.ff.universe.booking.dao.BookingUniversalDAO;
import com.ff.universe.business.dao.BusinessCommonDAO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.mec.dao.MECUniversalDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;
import com.firstflight.fi.csdtosap.codlcconsignmentNew.DTCSDCODLCConsignment;
import com.firstflight.fi.csdtosap.codlcliabilityRegion.DTCSDCODLCLiability;
import com.firstflight.fi.csdtosap.codlcliabilityRegion.DTCSDCODLCLiability.CODLCLiability;
import com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries;
import com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries;
import com.firstflight.fi.csdtosap.expentries.DTCSDExpenseEntries;
import com.firstflight.fi.csdtosap.expentries.DTCSDExpenseEntries.ExpenseEntries;

/**
 * @author cbhure
 *
 */
public class MiscellaneousSAPIntegrationServiceImpl implements MiscellaneousSAPIntegrationService {

	public SAPIntegrationDAO integrationDAO;
	private BusinessCommonDAO businessCommonDAO;
	private OrganizationCommonDAO organizationCommonDAO;
	private GeographyServiceDAO geographyServiceDAO;
	private MECUniversalDAO mecUniversalDAO; 
	private BookingUniversalDAO bookingUniversalDAO;
	private SequenceGeneratorService sequenceGeneratorService;
	
	
	Logger logger = Logger.getLogger(MiscellaneousSAPIntegrationServiceImpl.class); 
	
	
	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}
	
	
	
	/**
	 * @param sequenceGeneratorService the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}



	@Override
	public List<SAPExpenseDO> findExpenseDtlsForSAPIntegration(
			SAPExpenseTO expenseTo) throws CGSystemException, CGBusinessException {
		logger.debug("EXPENSE :: MiscellaneousSAPIntegrationServiceImpl :: findExpenseDtlsForSAPIntegration :: Start");
		List<ExpenseDO> expenseDOList = null;
		List<ExpenseDO> expDOList = null;
		List<SAPExpenseDO> sapExpList = null;
		List<SAPExpenseDO> sapExpDOs = null;
		ExpenseDO expenseDO = null;
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(expenseTo.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getExpenseCount(expenseTo);
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop -
					for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
						expenseDOList = new ArrayList<ExpenseDO>();
						//1. Fetching Dtls from Transaction Table
						expenseDOList = integrationDAO.findExpenseDtlsForSAPIntegration(expenseTo,maxDataCountLimit);
						
						//2 Step - Save CSD Table Data to Interface Staging Table
						if(!StringUtil.isEmptyColletion(expenseDOList)){ 
							sapExpList = convertExpenseCSDDOToStagingDO(expenseDOList);
							integrationDAO.saveExpenseStagingData(sapExpList);
							
							//3 Step - Fecthing data from Staging Table
							sapExpDOs = new ArrayList<>();
							sapExpDOs = integrationDAO.findExpenseDetailsFromStaging(expenseTo,maxDataCountLimit);
						}
					}		
			}else{
				//3 Step - Fecthing data from Staging Table
				sapExpDOs = new ArrayList<>();
				sapExpDOs = integrationDAO.findExpenseDetailsFromStaging(expenseTo,maxDataCountLimit);
			}			
		}catch(Exception e){
			logger.error("EXPENSE :: Exception IN :: MiscellaneousSAPIntegrationServiceImpl :: findExpenseDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findExpenseDtlsForSAPIntegration :: End");
		return sapExpDOs;
	}
	
	private List<SAPExpenseDO> convertExpenseCSDDOToStagingDO(List<ExpenseDO> expenseDOList) throws CGBusinessException,CGSystemException{
		logger.debug("EXPENSE :: MiscellaneousSAPIntegrationServiceImpl :: convertExpenseCSDDOToStagingDO :: Start");
		List<SAPExpenseDO> expStagingDOList = new ArrayList<SAPExpenseDO>();;
		SAPExpenseDO expStagingDO = null;
		for(ExpenseDO expenseCSDDO : expenseDOList){
			if((!StringUtil.isNull(expenseCSDDO) 
					&& !StringUtil.isEmptyColletion(expenseCSDDO.getExpenseEntries())
					&& ((!StringUtil.isNull(expenseCSDDO.getTypeOfExpense())
							&& !StringUtil.isStringEmpty(expenseCSDDO.getTypeOfExpense().getIsEmpGL())
							&& expenseCSDDO.getTypeOfExpense().getIsEmpGL().equals("Y"))
					|| (!StringUtil.isNull(expenseCSDDO.getTypeOfExpense())
							&& !StringUtil.isStringEmpty(expenseCSDDO.getTypeOfExpense().getIsEmpGL())
							&& expenseCSDDO.getTypeOfExpense().getIsOctroiGL().equals("Y"))))){
			//Child Items
			Set<ExpenseEntriesDO> expenseEntriedDO = expenseCSDDO.getExpenseEntries();
				if(!StringUtil.isEmptyColletion(expenseEntriedDO)){
				for(ExpenseEntriesDO expEntriesDO : expenseEntriedDO){
					expStagingDO = new SAPExpenseDO();
					if(!StringUtil.isNull(expEntriesDO) /*|| 
								expenseCSDDO.getExpenseFor().equals('O')*/){
						
						
						//expStagingDO = new SAPExpenseDO();
							//Emp Code
							if(!StringUtil.isNull(expenseCSDDO)
									&& !StringUtil.isStringEmpty(expenseCSDDO.getExpenseFor())
									&& expenseCSDDO.getExpenseFor().equalsIgnoreCase("E")
									&& !StringUtil.isNull(expenseCSDDO.getTypeOfExpense())
									&& !StringUtil.isStringEmpty(expenseCSDDO.getTypeOfExpense().getIsEmpGL())
									&& expenseCSDDO.getTypeOfExpense().getIsEmpGL().equals("Y")
									&& !StringUtil.isNull(expEntriesDO.getEmployeeDO())
									&& !StringUtil.isStringEmpty(expEntriesDO.getEmployeeDO().getEmpCode())){
								expStagingDO.setEmpCode(expEntriesDO.getEmployeeDO().getEmpCode());
							}else{
								expStagingDO.setEmpCode(null);
							}
							logger.debug("Emp Code ----------->"+ expStagingDO.getEmpCode());
							/*if(!StringUtil.isNull(expEntriesDO.getEmployeeDO())
									&& (!StringUtil.isStringEmpty(expEntriesDO.getEmployeeDO().getEmpCode()))){
							}*/
							
							if(!StringUtil.isNull(expEntriesDO.getRemarks())){
								expStagingDO.setRemark(expEntriesDO.getRemarks());
							}
							
							//Consg No && Dest RHO
							if(!StringUtil.isNull(expEntriesDO.getConsignmentDO())
									&& (!StringUtil.isStringEmpty(expEntriesDO.getConsignmentDO().getConsgNo()))){
								expStagingDO.setConsgNo(expEntriesDO.getConsignmentDO().getConsgNo());
								
								//Destination RHO 
								
								String  consgNo = expEntriesDO.getConsignmentDO().getConsgNo();
								List<BookingDO> bookingDO = new ArrayList<BookingDO>();
								String queryName = SAPIntegrationConstants.QRY_PARAM_PINCODE_AGAINST_CONSG_NO;
								String paramNames[]= {SAPIntegrationConstants.CONSG_NO};
								Object paramValues[]= {consgNo};
								bookingDO = integrationDAO.findPincodeAgainstConsgNo(queryName, paramNames, paramValues);
								BookingDO bookingNewDO = null;
								bookingNewDO = bookingDO.get(0);
								if(!StringUtil.isNull(bookingNewDO)
										&& (!StringUtil.isNull(bookingNewDO.getPincodeId())
												&& (!StringUtil.isStringEmpty(bookingNewDO.getPincodeId().getPincode())))){
									
									String pincode = bookingNewDO.getPincodeId().getPincode();
									CityDO cityDO = null;
									cityDO = geographyServiceDAO.getCity(pincode);
									if(!StringUtil.isNull(cityDO)
											&& (!StringUtil.isEmptyInteger(cityDO.getCityId()))){
										
										Integer cityId = cityDO.getCityId();
										//Integer regionId = cityDO.getRegion();
										List<OfficeDO> officeDO = null;
										officeDO = organizationCommonDAO.getAllOfficesByCity(cityId);
										OfficeDO ofcDO = null;
										ofcDO = officeDO.get(0);
										if(!StringUtil.isNull(ofcDO)
												&& (!StringUtil.isNull(ofcDO.getReportingRHO()))){
											Integer reportingRHOId = ofcDO.getReportingRHO();
											String offCode = null;
											OfficeDO reportingOfcCode = null;
											reportingOfcCode = organizationCommonDAO.getOfficeByIdOrCode(reportingRHOId, offCode);
											if(!StringUtil.isNull(reportingOfcCode)
													&& (!StringUtil.isStringEmpty(reportingOfcCode.getOfficeCode()))){
												expStagingDO.setDestinationRHO(reportingOfcCode.getOfficeCode());
												
												logger.debug("Destination RHO Code ----------->"+ expStagingDO.getDestinationRHO());
											}
										}
									}
								}
							}
							logger.debug("Consg NO ----------->"+ expStagingDO.getConsgNo());
							
							//service Charge basic
							
							if(!StringUtil.isEmptyDouble(expEntriesDO.getAmount())){
								expStagingDO.setTotalExpense(expEntriesDO.getAmount());
							}
							logger.debug("Amount---------------> "+expStagingDO.getTotalExpense());
							
							if(!StringUtil.isEmptyDouble(expEntriesDO.getServiceCharge())){
								expStagingDO.setServiceChanrge(expEntriesDO.getServiceCharge());
							}
							logger.debug("Service Charge ------------->"+expStagingDO.getServiceChanrge());
							
							if(!StringUtil.isEmptyDouble(expEntriesDO.getServiceTax())){
								expStagingDO.setServiceTaxBasic(expEntriesDO.getServiceTax());
							}
							logger.debug("Service Tax Basic ------------->"+expStagingDO.getServiceTaxBasic());
							
							if(!StringUtil.isEmptyDouble(expEntriesDO.getEducationCess())){
								expStagingDO.setEdOnServiceTax(expEntriesDO.getEducationCess());
							}
							logger.debug("ED on Service Tax ------------->"+expStagingDO.getEdOnServiceTax());
							
							if(!StringUtil.isEmptyDouble(expEntriesDO.getHigherEducationCess())){
								expStagingDO.setHedOnServiceTax(expEntriesDO.getHigherEducationCess());
							}
							logger.debug("HED on Service Tax ------------->"+expStagingDO.getHedOnServiceTax());
							
							
							//Header
							if(!StringUtil.isNull(expenseCSDDO.getExpenseOfficeId())
									&& (!StringUtil.isStringEmpty(expenseCSDDO.getExpenseOfficeId().getOfficeCode()))){
								expStagingDO.setOfficeCode(expenseCSDDO.getExpenseOfficeId().getOfficeCode());
							}
							logger.debug("Brach Code / Plant Code /Ofc Code--------------->"+expStagingDO.getOfficeCode());
							
							if(!StringUtil.isStringEmpty(expenseCSDDO.getTxNumber())){
								expStagingDO.setTxNumber(expenseCSDDO.getTxNumber());
							}
							logger.debug("Transaction No--------------->"+expStagingDO.getTxNumber());
							
							if(!StringUtil.isNull(expenseCSDDO.getTypeOfExpense())
									&&(!StringUtil.isStringEmpty(expenseCSDDO.getTypeOfExpense().getGlCode()))){
								expStagingDO.setExpenseGLCode(expenseCSDDO.getTypeOfExpense().getGlCode());
							}
							logger.debug("Expense GL Code--------------->"+expStagingDO.getExpenseGLCode());
							
							if(!StringUtil.isNull(expenseCSDDO.getModeOfExpense())
									&& (!StringUtil.isStringEmpty(expenseCSDDO.getModeOfExpense().getPaymentType()))){
								expStagingDO.setPaymentCode(expenseCSDDO.getModeOfExpense().getPaymentType());
							}
							logger.debug("Mode Of Payment--------------->"+expStagingDO.getPaymentCode());
							
							if(!StringUtil.isNull(expenseCSDDO.getModeOfExpense())
									&&(!StringUtil.isStringContainsInteger(expenseCSDDO.getModeOfExpense().getPaymentType()))
									&& (expenseCSDDO.getModeOfExpense().getPaymentType().equalsIgnoreCase("cheque"))){
								
								if(!StringUtil.isNull(expenseCSDDO.getBankDO())
										&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlCode())){
									expStagingDO.setBankGLCode(expenseCSDDO.getBankDO().getGlCode());
								}
							}
							logger.debug("Bank GL Code--------------->"+expStagingDO.getBankGLCode());
							
							if(!StringUtil.isNull(expenseCSDDO.getBankDO())
									&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlCode())){
								expStagingDO.setBankGLCode(expenseCSDDO.getBankDO().getGlCode());
								//Added glIndicator
								expStagingDO.setGlIndicator(expenseCSDDO.getTypeOfExpense().getNature());
							}
							logger.debug("Bank GL Code--------------->"+expStagingDO.getBankGLCode());
							
							if(!StringUtil.isNull(expenseCSDDO.getChequeDate())){
								expStagingDO.setChequeDate(expenseCSDDO.getChequeDate());
							}
							logger.debug("Cheque Date --------------->"+expStagingDO.getChequeDate());
							
							if(!StringUtil.isStringEmpty(expenseCSDDO.getChequeNo())){
								expStagingDO.setChequeNo(expenseCSDDO.getChequeNo());
							}
							logger.debug("Cheque No--------------->"+expStagingDO.getChequeNo());
				
							if(!StringUtil.isNull(expenseCSDDO.getBankDO())
									&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlDesc())){
								expStagingDO.setBankName(expenseCSDDO.getBankDO().getGlDesc());
							}
							logger.debug("Cheque Bank Name --------------->"+expStagingDO.getBankName());
							
							if(!StringUtil.isNull(expenseCSDDO.getPostingDate())){
								expStagingDO.setPostingDate(expenseCSDDO.getPostingDate());
							}
							logger.debug("Creation Date--------------->"+expStagingDO.getPostingDate());
							
							if(!StringUtil.isNull(expenseCSDDO.getExpenseOfficeRho())){
								OfficeDO officeRHODO = new OfficeDO();
								Integer officeId = expenseCSDDO.getExpenseOfficeRho();
								String offCode = null;
								officeRHODO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
								if(!StringUtil.isNull(officeRHODO)
										&&(!StringUtil.isStringEmpty(officeRHODO.getOfficeCode()))){
									expStagingDO.setReportingRhoCode(officeRHODO.getOfficeCode());
								}
							}
							logger.debug("Reporting RHO Code--------------->"+expStagingDO.getReportingRhoCode());
					
							if(!StringUtil.isEmptyInteger(expenseCSDDO.getCreatedBy())){
								expStagingDO.setCreatedBy(expenseCSDDO.getCreatedBy());
							}
							
							if(!StringUtil.isEmptyInteger(expenseCSDDO.getUpdatedBy())){
								expStagingDO.setUpdatedBy(expenseCSDDO.getUpdatedBy());
							}
							
							if(!StringUtil.isNull(expenseCSDDO.getCreatedDate())){
								expStagingDO.setCreatedDate(expenseCSDDO.getCreatedDate());
							}
							
							if(!StringUtil.isNull(expenseCSDDO.getUpdatedDate())){
								expStagingDO.setUpdatedDate(expenseCSDDO.getUpdatedDate());
							}
							
							expStagingDOList.add(expStagingDO);
						}//end of null chk child items
					}//end of for child items iteration
				}
			}else{
				//Header
			expStagingDO = new SAPExpenseDO();
				if(!StringUtil.isNull(expenseCSDDO.getExpenseOfficeId())
						&& (!StringUtil.isStringEmpty(expenseCSDDO.getExpenseOfficeId().getOfficeCode()))){
					expStagingDO.setOfficeCode(expenseCSDDO.getExpenseOfficeId().getOfficeCode());
				}
				logger.debug("Brach Code / Plant Code /Ofc Code--------------->"+expStagingDO.getOfficeCode());
				
				if(!StringUtil.isStringEmpty(expenseCSDDO.getTxNumber())){
					expStagingDO.setTxNumber(expenseCSDDO.getTxNumber());
				}
				logger.debug("Transaction No--------------->"+expStagingDO.getTxNumber());
				
				if(!StringUtil.isNull(expenseCSDDO.getTypeOfExpense())
						&&(!StringUtil.isStringEmpty(expenseCSDDO.getTypeOfExpense().getGlCode()))){
					expStagingDO.setExpenseGLCode(expenseCSDDO.getTypeOfExpense().getGlCode());
					//Added glIndicator
					expStagingDO.setGlIndicator(expenseCSDDO.getTypeOfExpense().getNature());
				}
				
				logger.debug("Expense GL Code--------------->"+expStagingDO.getExpenseGLCode());
				
				if(!StringUtil.isNull(expenseCSDDO.getModeOfExpense())
						&& (!StringUtil.isStringContainsInteger(expenseCSDDO.getModeOfExpense().getPaymentType()))){
					expStagingDO.setPaymentCode(expenseCSDDO.getModeOfExpense().getPaymentType());
				}
				logger.debug("Mode Of Payment--------------->"+expStagingDO.getPaymentCode());
				
				if(!StringUtil.isNull(expenseCSDDO.getModeOfExpense())
						&&(!StringUtil.isStringContainsInteger(expenseCSDDO.getModeOfExpense().getPaymentType()))
						&& (expenseCSDDO.getModeOfExpense().getPaymentType().equalsIgnoreCase("cheque"))){
					
					if(!StringUtil.isNull(expenseCSDDO.getBankDO())
							&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlCode())){
						expStagingDO.setBankGLCode(expenseCSDDO.getBankDO().getGlCode());
					}
				}
				logger.debug("Bank GL Code--------------->"+expStagingDO.getBankGLCode());
				
				if(!StringUtil.isNull(expenseCSDDO.getBankDO())
						&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlCode())){
					expStagingDO.setBankGLCode(expenseCSDDO.getBankDO().getGlCode());
				}
				logger.debug("Bank GL Code--------------->"+expStagingDO.getBankGLCode());
				
				if(!StringUtil.isEmptyDouble(expenseCSDDO.getTotalExpense())){
					expStagingDO.setTotalExpense(expenseCSDDO.getTotalExpense());
				}
				logger.debug("Amount--------------->"+expStagingDO.getTotalExpense());
	
				if(!StringUtil.isNull(expenseCSDDO.getChequeDate())){
					expStagingDO.setChequeDate(expenseCSDDO.getChequeDate());
				}
				logger.debug("Cheque Date --------------->"+expStagingDO.getChequeDate());
				
				if(!StringUtil.isStringEmpty(expenseCSDDO.getChequeNo())){
					expStagingDO.setChequeNo(expenseCSDDO.getChequeNo());
				}
				logger.debug("Cheque No--------------->"+expStagingDO.getChequeNo());
	
				if(!StringUtil.isNull(expenseCSDDO.getBankDO())
						&& !StringUtil.isStringEmpty(expenseCSDDO.getBankDO().getGlDesc())){
					expStagingDO.setBankName(expenseCSDDO.getBankDO().getGlDesc());
				}
				logger.debug("Cheque Bank Name --------------->"+expStagingDO.getBankName());
				
				if(!StringUtil.isNull(expenseCSDDO.getPostingDate())){
					expStagingDO.setPostingDate(expenseCSDDO.getPostingDate());
				}
				logger.debug("Creation Date--------------->"+expStagingDO.getPostingDate());
				
				if(!StringUtil.isNull(expenseCSDDO.getExpenseOfficeRho())){
					OfficeDO officeRHODO = new OfficeDO();
					Integer officeId = expenseCSDDO.getExpenseOfficeRho();
					String offCode = null;
					officeRHODO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
					if(!StringUtil.isNull(officeRHODO)
							&&(!StringUtil.isStringEmpty(officeRHODO.getOfficeCode()))){
						expStagingDO.setReportingRhoCode(officeRHODO.getOfficeCode());
					}
				}
				logger.debug("Reporting RHO Code--------------->"+expStagingDO.getReportingRhoCode());
		
				if(!StringUtil.isEmptyInteger(expenseCSDDO.getCreatedBy())){
					expStagingDO.setCreatedBy(expenseCSDDO.getCreatedBy());
				}
				
				if(!StringUtil.isEmptyInteger(expenseCSDDO.getUpdatedBy())){
					expStagingDO.setUpdatedBy(expenseCSDDO.getUpdatedBy());
				}
				
				if(!StringUtil.isNull(expenseCSDDO.getCreatedDate())){
					expStagingDO.setCreatedDate(expenseCSDDO.getCreatedDate());
				}
				
				if(!StringUtil.isNull(expenseCSDDO.getUpdatedDate())){
					expStagingDO.setUpdatedDate(expenseCSDDO.getUpdatedDate());
				}
				if(!StringUtil.isNull(expenseCSDDO.getRemarks())){
					expStagingDO.setRemark(expenseCSDDO.getRemarks());
				}
				expStagingDOList.add(expStagingDO);
			}
		}//end of for loop header iteration
		logger.debug("EXPENSE :: MiscellaneousSAPIntegrationServiceImpl :: convertExpenseCSDDOToStagingDO :: End");
		return expStagingDOList;
	}
	
/*	@Override
	public void findExpenseDtlsForSAPIntegrationFlagUpdate(
			SAPExpenseTO expenseTo) throws CGSystemException {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findExpenseDtlsForSAPIntegrationFlagUpdate=====> Start");
		List<ExpenseDO> expenseDO = new ArrayList<>(); 
		String queryName = SAPIntegrationConstants.QRY_PARAM_EXPENSE_DETAILS_FOR_SAP;
		String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND,SAPIntegrationConstants.EXPENSE_STATUS};
		Object paramValues[]= {expenseTo.getSapStatus(),expenseTo.getStatus()};
		expenseDO = integrationDAO.findExpenseDtlsForSAPIntegration(queryName, paramNames, paramValues);
		List<ExpenseDO> expDOList = new ArrayList<>();
		if(!StringUtil.isNull(expenseDO)){
			ExpenseDO expNewDO = null;
			for(ExpenseDO expDO : expenseDO){
				expNewDO = new ExpenseDO();
				expNewDO.setExpenseId(expDO.getExpenseId());
				logger.debug("Expense Id ------>"+expNewDO.getExpenseId());
				Date dateTime = Calendar.getInstance().getTime();
				expNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+expNewDO.getSapTimestamp());
				expNewDO.setSapStatus("C");
				logger.debug("SAP Status ------>"+expNewDO.getSapStatus());
				expDOList.add(expNewDO);
			}
		integrationDAO.updateDateTimeAndStatusFlagOfExpense(expDOList);
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findExpenseDtlsForSAPIntegrationFlagUpdate=====> End");
		}
	}*/

	@Override
	public List<SAPCollectionDO> findCollectionDtlsForSAPIntegration(SAPCollectionTO collectionTO) throws CGSystemException,
			CGBusinessException {
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: findCollectionDtlsForSAPIntegration :: Start");
		List<CollectionDO> collectionDOs = null;
		List<CollectionDO> collectionDOList = null;
		List<SAPCollectionDO> sapCollectionStagingDOList = null;
		List<SAPCollectionDO> sapCollectionStagingDOs = null;
		CollectionDO collDO = null;
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(collectionTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getCollectionDetailsCount(collectionTO.getStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop -
					for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
						collectionDOs = new ArrayList<CollectionDO>();
						//1. Fetching Dtls from Transaction Table
						collectionDOs = integrationDAO.findCollectionDtlsForSAPIntegration(collectionTO,maxDataCountLimit);
						
						//2 Step - Save CSD Table Data to Interface Staging Table
						if(!StringUtil.isEmptyColletion(collectionDOs)){ 
							sapCollectionStagingDOList = convertCollectionCSDDOToStagingDO(collectionDOs);
							integrationDAO.saveCollectionStagingData(sapCollectionStagingDOList);
							
							//3 Step - Fecthing data from Staging Table
							sapCollectionStagingDOs = new ArrayList<SAPCollectionDO>();
							sapCollectionStagingDOs = integrationDAO.findCollectionDetailsFromStaging(collectionTO,maxDataCountLimit);
						}
					}
				}else{
					//3 Step - Fecthing data from Staging Table
					sapCollectionStagingDOs = new ArrayList<SAPCollectionDO>();
					sapCollectionStagingDOs = integrationDAO.findCollectionDetailsFromStaging(collectionTO,maxDataCountLimit);
				}
		}catch(Exception e){
			logger.error("COLLECTION :: Exception IN :: MiscellaneousSAPIntegrationServiceImpl :: findCollectionDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: findCollectionDtlsForSAPIntegration :: End");
		return sapCollectionStagingDOs;
	}
	
	private List<SAPCollectionDO> convertCollectionCSDDOToStagingDO(List<CollectionDO> collectionDOs)throws CGBusinessException, CGSystemException{
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: convertCollectionCSDDOToStagingDO :: Starts");
		List<SAPCollectionDO> sapCollectionDOlist = new ArrayList<SAPCollectionDO>();
		SAPCollectionDO sapCollectionDO = null;
		List<GLMasterDO> glMasterList = null; 
		for(CollectionDO collnDO : collectionDOs){
			// 2) - Line Items
			if(!StringUtil.isEmptyColletion(collnDO.getCollectionDtls())){
				Set<CollectionDtlsDO> collectionDtlsSet = collnDO.getCollectionDtls();
					for(CollectionDtlsDO collectionDtlsDO : collectionDtlsSet){
						sapCollectionDO = new SAPCollectionDO();
						if(!StringUtil.isStringEmpty(collnDO.getSapStatus())
										&& collnDO.getSapStatus().equals("I")){
							
							// get collection numbaer without _1 and get collection date of it and send it
							
							//Tx No 
							if(!StringUtil.isStringEmpty(collnDO.getTxnNo())){
								sapCollectionDO.setTxNumber(collnDO.getTxnNo());
							}
							logger.debug("COLLECTION :: TX Number--------------->"+sapCollectionDO.getTxNumber());
							
							String transactionNumber = collnDO.getTxnNo().substring(0,12);
							 
							CollectionDO collectionNewDO = integrationDAO.getCollectionDtlsByTransactionNumber(transactionNumber);
							
							//Created Date
							if(!StringUtil.isNull(collectionNewDO)
									&& !StringUtil.isNull(collectionNewDO.getCollectionDate())){
								sapCollectionDO.setCollectionDate(collectionNewDO.getCollectionDate());
								logger.debug("COLLECTION :: Created Date GregorianCalendar Format ----------->"+sapCollectionDO.getCollectionDate());
								
							}
							
							// 1) - Header Items
							//Cust Code -
							List<CustomerDO> customerDO = null;
							if(!StringUtil.isNull(collnDO.getCustomerDO())){
								customerDO = new ArrayList<>();
								//Integer customerId = null;
								if(!StringUtil.isEmptyInteger(collnDO.getCustomerDO().getCustomerId())){
								Integer	customerId = collnDO.getCustomerDO().getCustomerId();
								customerDO = businessCommonDAO.getCustomer(customerId);
								for(CustomerDO customerNewDO : customerDO){
									sapCollectionDO.setCustCode(customerNewDO.getCustomerCode());
								}
								logger.debug("COLLECTION :: Cust Code ----------------->"+sapCollectionDO.getCustCode());
								}
							}
								
							//Mode Of Payment chq then set cheDate,ChkNo,GLCode
							if(!StringUtil.isNull(collnDO.getPaymentModeDO())
									&& (!StringUtil.isStringEmpty(collnDO.getPaymentModeDO().getPaymentType()))
									&&(collnDO.getPaymentModeDO().getPaymentType().equalsIgnoreCase("CHEQUE"))){
								
								sapCollectionDO.setModeOfPayment(collnDO.getPaymentModeDO().getPaymentType());
								
								logger.debug("COLLECTION :: Mode Of Payment ----------->"+sapCollectionDO.getModeOfPayment());
								
								if(!StringUtil.isNull(collnDO.getBankGLDO())
										&& (!StringUtil.isStringEmpty(collnDO.getBankGLDO().getGlCode()))){
									sapCollectionDO.setBankGLCode(collnDO.getBankGLDO().getGlCode());
									logger.debug("COLLECTION :: Bank GL Code ----------->"+sapCollectionDO.getBankGLCode());
								}
								
								//Cheque Date
								if(!StringUtil.isNull(collnDO.getChqDate())){
									sapCollectionDO.setChequeDate(collnDO.getChqDate());
									logger.debug("COLLECTION :: Cheque Date  ----------->"+sapCollectionDO.getChequeDate());
								}
								
								//Chq NO
								if(!StringUtil.isStringEmpty(collnDO.getChqNo())){
									sapCollectionDO.setChequeNo(collnDO.getChqNo());
								}
								logger.debug("COLLECTION :: Cheque No ----------->"+sapCollectionDO.getChequeNo());
							}else if(!StringUtil.isNull(collnDO.getPaymentModeDO())
									&& (!StringUtil.isStringEmpty(collnDO.getPaymentModeDO().getPaymentType()))){//set Payment Mode
								sapCollectionDO.setModeOfPayment(collnDO.getPaymentModeDO().getPaymentType());
								logger.debug("COLLECTION :: Mode Of Payment ----------->"+sapCollectionDO.getModeOfPayment());
							}
								
							if(!StringUtil.isNull(collnDO.getCollectionOfficeDO())
									&&(!StringUtil.isStringEmpty(collnDO.getCollectionOfficeDO().getOfficeCode()))){
								sapCollectionDO.setOfficeCode(collnDO.getCollectionOfficeDO().getOfficeCode());
								logger.debug("COLLECTION :: Branch ----------->"+collnDO.getCollectionOfficeDO().getOfficeCode());
							}
							
							//Consignment No
							if(!StringUtil.isNull(collnDO.getCollectionId())
												&& (collnDO.getCollectionId().equals(collectionDtlsDO.getCollectionDO().getCollectionId()))){
										
										if(!StringUtil.isNull(collectionDtlsDO.getConsgDO()) &&
												!StringUtil.isStringEmpty(collectionDtlsDO.getConsgDO().getConsgNo())){
											sapCollectionDO.setConsgNo(collectionDtlsDO.getConsgDO().getConsgNo());
										}
										logger.debug("COLLECTION :: Consg No ----------->"+sapCollectionDO.getConsgNo());
										
										//collection Against
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getCollectionAgainst())){
											sapCollectionDO.setCollectionAgainst(collectionDtlsDO.getCollectionAgainst());
										}
										logger.debug("COLLECTION :: collection Against ----------->"+sapCollectionDO.getCollectionAgainst());
										
										//Bill No 
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getBillNo())){
											sapCollectionDO.setBillNo(collectionDtlsDO.getBillNo());
										}
										logger.debug("COLLECTION :: Bill No----------->"+sapCollectionDO.getBillNo());
										
										//Received Amt
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getRecvAmount())){
											sapCollectionDO.setReceivedAmt(collectionDtlsDO.getRecvAmount());
										}
										logger.debug("COLLECTION :: Received Amt ----------->"+sapCollectionDO.getReceivedAmt());
										
										//TDS Amt
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getTdsAmount())){
											sapCollectionDO.setTdsAmt(collectionDtlsDO.getTdsAmount());
										}
										logger.debug("COLLECTION :: TDS Amt ----------->"+sapCollectionDO.getTdsAmt());
										
										//Billed Amt
										if(StringUtil.isNull(collectionDtlsDO.getBillAmount())){
											sapCollectionDO.setBilledAmt(0.00);
										}else{
											sapCollectionDO.setBilledAmt(collectionDtlsDO.getBillAmount());
										}
										logger.debug("COLLECTION :: Billed Amt ----------->"+sapCollectionDO.getBilledAmt());
										
										//Remarks
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getRemarks())){
											sapCollectionDO.setRemarks(collectionDtlsDO.getRemarks());
										}
										logger.debug("COLLECTION :: Remarks ----------->"+sapCollectionDO.getRemarks());
										
										//Remarks
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getDeduction())){
											sapCollectionDO.setDeduction(collectionDtlsDO.getDeduction());
										}
										logger.debug("COLLECTION :: Deduction ----------->"+sapCollectionDO.getDeduction());
										
										//Remarks
										if(!StringUtil.isNull(collectionDtlsDO.getReasonDO())
												&& !StringUtil.isStringEmpty(collectionDtlsDO.getReasonDO().getReasonCode())){
											sapCollectionDO.setReasonCode(collectionDtlsDO.getReasonDO().getReasonCode());
										}
										logger.debug("COLLECTION :: Reason Code ----------->"+sapCollectionDO.getReasonCode());
										
										if(!StringUtil.isEmptyInteger(collectionDtlsDO.getCreatedBy())){
											sapCollectionDO.setCreatedBy(collectionDtlsDO.getCreatedBy());
										}
										
										if(!StringUtil.isEmptyInteger(collectionDtlsDO.getUpdatedBy())){
											sapCollectionDO.setUpdatedBy(collectionDtlsDO.getUpdatedBy());
										}
										
										if(!StringUtil.isNull(collectionDtlsDO.getCreatedDate())){
											sapCollectionDO.setCreatedDate(collectionDtlsDO.getCreatedDate());
										}
										
										if(!StringUtil.isNull(collectionDtlsDO.getUpdatedDate())){
											sapCollectionDO.setUpdatedDate(collectionDtlsDO.getUpdatedDate());
										}
									}
								}else{
									// 1) - Header Items
									//Cust Code -
								List<CustomerDO> customerDO = null;
								if(!StringUtil.isNull(collnDO.getCustomerDO())){
									customerDO = new ArrayList<>();
									//Integer customerId = null;
									if(!StringUtil.isEmptyInteger(collnDO.getCustomerDO().getCustomerId())){
									Integer	customerId = collnDO.getCustomerDO().getCustomerId();
									customerDO = businessCommonDAO.getCustomer(customerId);
									for(CustomerDO customerNewDO : customerDO){
										sapCollectionDO.setCustCode(customerNewDO.getCustomerCode());
									}
									logger.debug("COLLECTION :: Cust Code ----------------->"+sapCollectionDO.getCustCode());
									}
								}
								//Tx No 
								if(!StringUtil.isStringEmpty(collnDO.getTxnNo())){
									sapCollectionDO.setTxNumber(collnDO.getTxnNo());
								}
								logger.debug("COLLECTION :: TX Number--------------->"+sapCollectionDO.getTxNumber());
								
								//Created Date
								if(!StringUtil.isNull(collnDO.getCollectionDate())){
									sapCollectionDO.setCollectionDate(collnDO.getCollectionDate());
								}
								logger.debug("COLLECTION :: Created Date GregorianCalendar Format ----------->"+sapCollectionDO.getCollectionDate());
								
								//Mode Of Payment chq then set cheDate,ChkNo,GLCode
								if(!StringUtil.isNull(collnDO.getPaymentModeDO())
										&& (!StringUtil.isStringEmpty(collnDO.getPaymentModeDO().getPaymentType()))
										&&(collnDO.getPaymentModeDO().getPaymentType().equalsIgnoreCase("CHEQUE"))){
									
									sapCollectionDO.setModeOfPayment(collnDO.getPaymentModeDO().getPaymentType());
									
									logger.debug("COLLECTION :: Mode Of Payment ----------->"+sapCollectionDO.getModeOfPayment());
									
									if(!StringUtil.isNull(collnDO.getBankGLDO())
											&& (!StringUtil.isStringEmpty(collnDO.getBankGLDO().getGlCode()))){
										sapCollectionDO.setBankGLCode(collnDO.getBankGLDO().getGlCode());
										logger.debug("COLLECTION :: Bank GL Code ----------->"+sapCollectionDO.getBankGLCode());
									}
									
									//Cheque Date
									if(!StringUtil.isNull(collnDO.getChqDate())){
										sapCollectionDO.setChequeDate(collnDO.getChqDate());
										logger.debug("COLLECTION :: Cheque Date  ----------->"+sapCollectionDO.getChequeDate());
									}
									
									//Chq NO
									if(!StringUtil.isStringEmpty(collnDO.getChqNo())){
										sapCollectionDO.setChequeNo(collnDO.getChqNo());
									}
									logger.debug("COLLECTION :: Cheque No ----------->"+sapCollectionDO.getChequeNo());
								}else if(!StringUtil.isNull(collnDO.getPaymentModeDO())
										&& (!StringUtil.isStringEmpty(collnDO.getPaymentModeDO().getPaymentType()))){//set Payment Mode
									sapCollectionDO.setModeOfPayment(collnDO.getPaymentModeDO().getPaymentType());
									logger.debug("COLLECTION :: Mode Of Payment ----------->"+sapCollectionDO.getModeOfPayment());
								}
								
								if(!StringUtil.isNull(collnDO.getCollectionOfficeDO())
										&&(!StringUtil.isStringEmpty(collnDO.getCollectionOfficeDO().getOfficeCode()))){
									sapCollectionDO.setOfficeCode(collnDO.getCollectionOfficeDO().getOfficeCode());
									logger.debug("COLLECTION :: Branch ----------->"+collnDO.getCollectionOfficeDO().getOfficeCode());
								}
								
								//Consignment No
								if(!StringUtil.isNull(collnDO.getCollectionId())
												&& (collnDO.getCollectionId().equals(collectionDtlsDO.getCollectionDO().getCollectionId()))){
										
										if(!StringUtil.isNull(collectionDtlsDO.getConsgDO()) &&
												!StringUtil.isStringEmpty(collectionDtlsDO.getConsgDO().getConsgNo())){
											sapCollectionDO.setConsgNo(collectionDtlsDO.getConsgDO().getConsgNo());
										}
										logger.debug("COLLECTION :: Consg No ----------->"+sapCollectionDO.getConsgNo());
										
										//collection Against
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getCollectionAgainst())){
											sapCollectionDO.setCollectionAgainst(collectionDtlsDO.getCollectionAgainst());
										}
										logger.debug("COLLECTION :: collection Against ----------->"+sapCollectionDO.getCollectionAgainst());
										
										//Bill No 
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getBillNo())){
											sapCollectionDO.setBillNo(collectionDtlsDO.getBillNo());
										}
										logger.debug("COLLECTION :: Bill No----------->"+sapCollectionDO.getBillNo());
										
										//Received Amt
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getRecvAmount())){
											sapCollectionDO.setReceivedAmt(collectionDtlsDO.getRecvAmount());
										}
										logger.debug("COLLECTION :: Received Amt ----------->"+sapCollectionDO.getReceivedAmt());
										
										//TDS Amt
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getTdsAmount())){
											sapCollectionDO.setTdsAmt(collectionDtlsDO.getTdsAmount());
										}
										logger.debug("COLLECTION :: TDS Amt ----------->"+sapCollectionDO.getTdsAmt());
										
										//Billed Amt
										if(StringUtil.isNull(collectionDtlsDO.getBillAmount())){
											sapCollectionDO.setBilledAmt(0.00);
										}else{
											sapCollectionDO.setBilledAmt(collectionDtlsDO.getBillAmount());
										}
										logger.debug("COLLECTION :: Billed Amt ----------->"+sapCollectionDO.getBilledAmt());
										
										//Remarks
										if(!StringUtil.isStringEmpty(collectionDtlsDO.getRemarks())){
											sapCollectionDO.setRemarks(collectionDtlsDO.getRemarks());
										}
										logger.debug("COLLECTION :: Remarks ----------->"+sapCollectionDO.getRemarks());
										
										//Remarks
										if(!StringUtil.isEmptyDouble(collectionDtlsDO.getDeduction())){
											sapCollectionDO.setDeduction(collectionDtlsDO.getDeduction());
										}
										logger.debug("COLLECTION :: Deduction ----------->"+sapCollectionDO.getDeduction());
										
										//Remarks
										if(!StringUtil.isNull(collectionDtlsDO.getReasonDO())
												&& !StringUtil.isStringEmpty(collectionDtlsDO.getReasonDO().getReasonCode())){
											sapCollectionDO.setReasonCode(collectionDtlsDO.getReasonDO().getReasonCode());
										}
										logger.debug("COLLECTION :: Reason Code ----------->"+sapCollectionDO.getReasonCode());
										
										if(!StringUtil.isEmptyInteger(collectionDtlsDO.getCreatedBy())){
											sapCollectionDO.setCreatedBy(collectionDtlsDO.getCreatedBy());
										}
										
										if(!StringUtil.isEmptyInteger(collectionDtlsDO.getUpdatedBy())){
											sapCollectionDO.setUpdatedBy(collectionDtlsDO.getUpdatedBy());
										}
										
										if(!StringUtil.isNull(collectionDtlsDO.getCreatedDate())){
											sapCollectionDO.setCreatedDate(collectionDtlsDO.getCreatedDate());
										}
										
										if(!StringUtil.isNull(collectionDtlsDO.getUpdatedDate())){
											sapCollectionDO.setUpdatedDate(collectionDtlsDO.getUpdatedDate());
										}
									}
								}
					sapCollectionDOlist.add(sapCollectionDO);	
				}
			}
		}
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: convertCollectionCSDDOToStagingDO :: Ends");
		return sapCollectionDOlist;
	}

/**
 * @param businessCommonDAO the businessCommonDAO to set
 */
public void setBusinessCommonDAO(BusinessCommonDAO businessCommonDAO) {
	this.businessCommonDAO = businessCommonDAO;
}

//@Override
/*public void findCollectionDtlsForSAPIntegrationFlagUpdate(
		SAPCollectionTO collectionTO) throws CGSystemException {
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findCollectionDtlsForSAPIntegrationFlagUpdate=====> Start");
	List<CollectionDO> collectionDO = new ArrayList<>(); 
	String queryName = SAPIntegrationConstants.QRY_PARAM_COLLECTION_DETAILS_FOR_SAP;
	String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND,SAPIntegrationConstants.COLLECTION_STATUS};
	Object paramValues[]= {collectionTO.getSapStatus(),collectionTO.getStatus()};
	collectionDO = integrationDAO.findCollectionDtlsForSAPIntegration(queryName, paramNames, paramValues);
	List<CollectionDO> collnDOList = new ArrayList<>();
	if(!StringUtil.isNull(collectionDO)){
		CollectionDO collnNewDO = null;
		for(CollectionDO collnDO : collnDOList){
			collnNewDO = new CollectionDO();
			collnNewDO.setCollectionId(collnDO.getCollectionId());
			logger.debug("Collection Id ------>"+collnNewDO.getCollectionId());
			Date dateTime = Calendar.getInstance().getTime();
			collnNewDO.setSapTimestamp(dateTime);
			logger.debug("Date Stamp ------>"+collnNewDO.getSapTimestamp());
			collnNewDO.setSapStatus("C");
			logger.debug("SAP Status ------>"+collnNewDO.getSapStatus());
			collnDOList.add(collnNewDO);
		}
	integrationDAO.updateDateTimeAndStatusFlagOfCollection(collnDOList);
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findCollectionDtlsForSAPIntegrationFlagUpdate=====> End");
	}
	
}*/

@Override
	public void saveExpenseStagingData(List<ExpenseEntries> elements)
			throws CGSystemException {
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveExpenseStagingData =====> Start");
	List<SAPExpenseDO> sapExpDOList = new ArrayList<>();
	SAPExpenseDO sapExpenseDO = null;
	DTCSDExpenseEntries expenseEntries = new DTCSDExpenseEntries();
	for(DTCSDExpenseEntries.ExpenseEntries expense : elements){
		sapExpenseDO = new SAPExpenseDO();
		elements =  expenseEntries.getExpenseEntries();
		sapExpenseDO.setOfficeCode(expense.getBranchCode());
		sapExpenseDO.setTxNumber(expense.getTransactionID());
		sapExpenseDO.setExpenseGLCode(expense.getExpenseGLCode());
		sapExpenseDO.setPaymentCode(expense.getModeOfPayment());
		sapExpenseDO.setBankGLCode(expense.getBankGLCode());
		//if mode of payment is cheque then Bank GL Code is mandatory
		sapExpenseDO.setTotalExpense(Double.valueOf(expense.getAmount()));
		Date chequeDate =  new Date();  
        if(expense.getChequeDate()!=null){  
            XMLGregorianCalendar calendar = expense.getChequeDate();  
            chequeDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
        }  
        sapExpenseDO.setChequeDate(chequeDate);
		sapExpenseDO.setChequeNo(expense.getChequeNo());
		sapExpenseDO.setBankName(expense.getChequeBank());
		
		Date postingDate =  new Date();  
        if(expense.getCreationDate()!=null){  
            XMLGregorianCalendar calendar = expense.getCreationDate();  
            postingDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
        }
		sapExpenseDO.setPostingDate(postingDate);
		sapExpenseDO.setSapStatus(expense.getSAPStatus());
		Date dateTime = Calendar.getInstance().getTime();
		sapExpenseDO.setSapTimestamp(dateTime);
		
		//Time Stamp need to be added in WSDL 
		sapExpDOList.add(sapExpenseDO);
	}
	integrationDAO.saveExpenseStagingData(sapExpDOList);
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveExpenseStagingData =====> End");
	}

	@Override
	public void saveCollectionStagingData(List<CollectionEntries> elementsNew)
			throws CGSystemException {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveCollectionStagingData =====> Start");
		List<SAPCollectionDO> sapColnDOList = new ArrayList<>();
		SAPCollectionDO sapcollnDO = null;
		DTCSDCollectionEntries collectionEntries = new DTCSDCollectionEntries();
		for(DTCSDCollectionEntries.CollectionEntries collection : elementsNew){
			sapcollnDO = new SAPCollectionDO();
			elementsNew =  collectionEntries.getCollectionEntries();
			sapcollnDO.setBankGLCode(collection.getBankGLCode());
			sapcollnDO.setBankName(collection.getChequeBank());
			Date chequeDate =  new Date();  
			if(collection.getChequeDate()!=null){  
	            XMLGregorianCalendar calendar = collection.getChequeDate();  
	            chequeDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
	            sapcollnDO.setChequeDate(chequeDate);
	        }  
			sapcollnDO.setChequeNo(collection.getChequeNo());
			sapcollnDO.setCustCode(collection.getCustomerCode());
			sapcollnDO.setModeOfPayment(collection.getModeOfPayment());
			sapcollnDO.setSapStatus(collection.getSAPStatus());
			sapcollnDO.setTxNumber(collection.getTransactionNo());
			
			Date createdDate =  new Date();  
			if(collection.getCreatedDate()!=null){  
	            XMLGregorianCalendar calendar = collection.getCreatedDate();  
	            createdDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
	            sapcollnDO.setCollectionDate(createdDate);
	        }
			for(DTCSDCollectionEntries.CollectionEntries.ItemDetails collectionDtls : collection.itemDetails){
				sapcollnDO.setConsgNo(collectionDtls.getConsignmentNo());
				if(!StringUtil.isStringEmpty(collectionDtls.getCollectionAgainst())
						&& collectionDtls.getCollectionAgainst().equalsIgnoreCase("BILL")){
					sapcollnDO.setCollectionAgainst("B");
				}else if(!StringUtil.isStringEmpty(collectionDtls.getCollectionAgainst())
						&& collectionDtls.getCollectionAgainst().equalsIgnoreCase("CREDIT")){
					sapcollnDO.setCollectionAgainst("C");
				}else if(!StringUtil.isStringEmpty(collectionDtls.getCollectionAgainst())
						&& collectionDtls.getCollectionAgainst().equalsIgnoreCase("DEBIT")){
					sapcollnDO.setCollectionAgainst("D");
				}else if(!StringUtil.isStringEmpty(collectionDtls.getCollectionAgainst())
						&& collectionDtls.getCollectionAgainst().equalsIgnoreCase("ON ACCOUNT")){
					sapcollnDO.setCollectionAgainst("OA");
				}
				sapcollnDO.setBillNo(collectionDtls.getBillNo());
				sapcollnDO.setReceivedAmt(Double.valueOf(collectionDtls.getReceivedAmount()));
				sapcollnDO.setTdsAmt(Double.valueOf(collectionDtls.getTDSAmount()));
				sapcollnDO.setBilledAmt(Double.valueOf(collectionDtls.getBilledAmount()));
				sapcollnDO.setRemarks(collectionDtls.getRemarks());
			}
			
			Date dateTime = Calendar.getInstance().getTime();
			sapcollnDO.setSapTimestamp(dateTime);
			sapColnDOList.add(sapcollnDO);
		}
		integrationDAO.saveCollectionStagingData(sapColnDOList);
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveCollectionStagingData =====> End");
		
	}

	/*@Override
	public List<SAPLiabilityPaymentDO> findLiabilityPaymentDtlsForSAPIntegration(
			SAPLiabilityPaymentTO liabilityPaytTO) throws CGSystemException, CGBusinessException{
		
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findLiabilityPaymentDtlsForSAPIntegration :: Start");
		List<LiabilityDO> liabilityPaymentDOList = new ArrayList<LiabilityDO>();
		
		//1 Step - Fecthing data from CSD Table
		String queryName = SAPIntegrationConstants.QRY_PARAM_LIABILITY_PAYMENT_DETAILS_FOR_SAP;
		String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
		Object paramValues[]= {liabilityPaytTO.getSapStatus()};
		liabilityPaymentDOList = integrationDAO.findLiabilityPaymentDtlsForSAPIntegration(queryName, paramNames, paramValues);
		
		//2 Step - Save CSD Table Data to Interface Staging Table
		List<SAPLiabilityPaymentDO> sapLiabilityPaymentStagingDOList = new ArrayList<SAPLiabilityPaymentDO>();
		boolean isSaved = false;
		if(!StringUtil.isNull(liabilityPaymentDOList)){ 
			sapLiabilityPaymentStagingDOList = convertLiabilityPaymentCSDDOToStagingDO(liabilityPaymentDOList);
			isSaved = integrationDAO.saveLiabilityStagingData(sapLiabilityPaymentStagingDOList);
		}
		//2 A
		//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
		//if flag = true status = C and Time stamp = current time
		//if flag = false status = N and time Stamp = current Time
		boolean isUpdated = false;
		if(isSaved){
			List<LiabilityDO> liabilityDOsList = new ArrayList<>();
			LiabilityDO liabilityNewDO = null;
			for(LiabilityDO liabilDO : liabilityPaymentDOList){
				liabilityNewDO = new LiabilityDO();
				liabilityNewDO.setLiabilityId(liabilDO.getLiabilityId());
				logger.debug("Expense Id ------>"+liabilityNewDO.getLiabilityId());
				Date dateTime = Calendar.getInstance().getTime();
				liabilityNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+liabilityNewDO.getSapTimestamp());
				liabilityNewDO.setSapStatus("C");
				logger.debug("SAP Status ------>"+liabilityNewDO.getSapStatus());
				liabilityDOsList.add(liabilityNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfLiability(liabilityDOsList);
		}else{
			List<LiabilityDO> liabilityDOsList = new ArrayList<>();
			LiabilityDO liabilityNewDO = null;
			for(LiabilityDO liabilDO : liabilityPaymentDOList){
				liabilityNewDO = new LiabilityDO();
				liabilityNewDO.setLiabilityId(liabilDO.getLiabilityId());
				logger.debug("Expense Id ------>"+liabilityNewDO.getLiabilityId());
				Date dateTime = Calendar.getInstance().getTime();
				liabilityNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+liabilityNewDO.getSapTimestamp());
				liabilityNewDO.setSapStatus("N");
				logger.debug("SAP Status ------>"+liabilityNewDO.getSapStatus());
				liabilityDOsList.add(liabilityNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfLiability(liabilityDOsList);
		}
		
		//3 Step - Fecthing data from Staging Table
		List<SAPLiabilityPaymentDO> sapLiabiliPaymentDOList = new ArrayList<SAPLiabilityPaymentDO>();
		if(isUpdated){
				String stagingQueryName = SAPIntegrationConstants.QRY_PARAM_GET_LIABILITY_DETAILS_FROM_STAGING;
				String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
				Object stagingParamValues[]= {liabilityPaytTO.getSapStatus()};
				sapLiabiliPaymentDOList = integrationDAO.findLiabilityPaymentDtlsFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findLiabilityPaymentDtlsForSAPIntegration :: End");
		return sapLiabiliPaymentDOList;
	}*/
	
	
	@Override
	public List<SAPLiabilityPaymentDO> findLiabilityPaymentDtlsForSAPIntegration(
			SAPLiabilityPaymentTO liabilityPaytTO) throws CGSystemException,
			CGBusinessException {
		logger.debug("LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityPaymentDtlsForSAPIntegration :: Start");
		List<LiabilityDO> liabilityPaymentDOList = null;
		List<SAPLiabilityPaymentDO> sapLiabiliPaymentDOList = null;
		List<LiabilityDO> liabilityDOsList = null;
		List<SAPLiabilityPaymentDO> sapLiabiliPaymentDOs = null;
		LiabilityDO liabilityNewDO = null; 
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try
		{	
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(liabilityPaytTO
					.getMaxCheck());
			if (!StringUtil.isNull(configParamDO)) {
				String maxDataCount = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			// Find out total record count from Transaction table = 150
			totalRecords = integrationDAO.getLiabilityPaymentCount(liabilityPaytTO.getSapStatus());
			
			if (!StringUtil.isEmptyLong(totalRecords)) {
				// Add for loop -
				for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount
						+ maxDataCountLimit) {
				// 1 Step - Fecthing data from CSD Table
				//String queryName = SAPIntegrationConstants.;
				//String paramNames[] = { SAPIntegrationConstants.DT_SAP_OUTBOUND };
				//Object paramValues[] = { liabilityPaytTO.getSapStatus() };
				liabilityPaymentDOList = integrationDAO.findLiabilityPaymentDtlsForSAPIntegration(liabilityPaytTO, maxDataCountLimit);

				// 2 Step - Save CSD Table Data to Interface Staging Table
				List<SAPLiabilityPaymentDO> sapLiabilityPaymentStagingDOList = new ArrayList<SAPLiabilityPaymentDO>();
					if (!StringUtil.isNull(liabilityPaymentDOList)) {
						sapLiabilityPaymentStagingDOList = convertLiabilityPaymentCSDDOToStagingDO(liabilityPaymentDOList);
						integrationDAO.saveLiabilityStagingData(sapLiabilityPaymentStagingDOList);
	
						// 3 Step - Fecthing data from Staging Table
						//if (isUpdated) {
							sapLiabiliPaymentDOList = new ArrayList<>();
							sapLiabiliPaymentDOList = integrationDAO.findLiabilityPaymentDtlsFromStaging(liabilityPaytTO, maxDataCountLimit);
						//}
					}
				}
			}else{
				sapLiabiliPaymentDOList = new ArrayList<>();
				sapLiabiliPaymentDOList = integrationDAO.findLiabilityPaymentDtlsFromStaging(liabilityPaytTO, maxDataCountLimit);
			}
		}catch (Exception e) {
			logger.error("LIABILITY PAYMENT :: Exception IN :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityPaymentDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityPaymentDtlsForSAPIntegration :: End");
		return sapLiabiliPaymentDOList;
	}



	private List<SAPLiabilityPaymentDO> convertLiabilityPaymentCSDDOToStagingDO(List<LiabilityDO> liabilityPaymentDOList) { 
		
		logger.debug("LIABILITY PAYMENT :: LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: convertLiabilityPaymentCSDDOToStagingDO :: Start");
		
		List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOs = new ArrayList<SAPLiabilityPaymentDO>();
		SAPLiabilityPaymentDO sapLiabilityPaymentDO = null;
		
		for(LiabilityDO liabilityPaymentDO : liabilityPaymentDOList){
			
			if(!StringUtil.isNull(liabilityPaymentDO)){
				sapLiabilityPaymentDO = new SAPLiabilityPaymentDO();
			
				if(!StringUtil.isNull(liabilityPaymentDO.getCustId())
						&& (!StringUtil.isStringEmpty(liabilityPaymentDO.getCustId().getCustomerCode()))){ 
					sapLiabilityPaymentDO.setCustCode(liabilityPaymentDO.getCustId().getCustomerCode());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Customer Code ---------->"+sapLiabilityPaymentDO.getCustCode());
				
				if(!StringUtil.isStringEmpty(liabilityPaymentDO.getTxNumber())){ 
					sapLiabilityPaymentDO.setTxNumber(liabilityPaymentDO.getTxNumber());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface TX Number ---------->"+sapLiabilityPaymentDO.getTxNumber());
				
				if(!StringUtil.isNull(liabilityPaymentDO.getLiabilityDate())){ 
					sapLiabilityPaymentDO.setCreationDate(liabilityPaymentDO.getLiabilityDate());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Created Date ---------->"+sapLiabilityPaymentDO.getCreatedDate());
				
				if(!StringUtil.isNull(liabilityPaymentDO.getChqDate())){
					sapLiabilityPaymentDO.setChequeDate(liabilityPaymentDO.getChqDate());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Chqque Date ---------->"+sapLiabilityPaymentDO.getChequeDate());
				
				if(!StringUtil.isStringEmpty(liabilityPaymentDO.getChqNo())){
					sapLiabilityPaymentDO.setChequeNo(liabilityPaymentDO.getChqNo());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Cheque No ---------->"+sapLiabilityPaymentDO.getChequeNo());
				
				if(!StringUtil.isNull(liabilityPaymentDO.getBankId())
						&& (!StringUtil.isStringEmpty(liabilityPaymentDO.getBankId().getGlDesc()))){
					sapLiabilityPaymentDO.setChequeBankName(liabilityPaymentDO.getBankId().getGlDesc());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface CHQ Bank Name---------->"+sapLiabilityPaymentDO.getChequeBankName());
				
				if(!StringUtil.isNull(liabilityPaymentDO.getBankId())
						&& (!StringUtil.isStringEmpty(liabilityPaymentDO.getBankId().getGlCode()))){
					sapLiabilityPaymentDO.setBankGLCode(liabilityPaymentDO.getBankId().getGlCode());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Bank GL Code Code ---------->"+sapLiabilityPaymentDO.getBankGLCode());
				
				if(!StringUtil.isEmptyDouble(liabilityPaymentDO.getLiabiltyAmt())){ 
					sapLiabilityPaymentDO.setAmount(liabilityPaymentDO.getLiabiltyAmt());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Amount---------->"+sapLiabilityPaymentDO.getAmount());
				
				if(!StringUtil.isNull(liabilityPaymentDO.getRegionId())
						&& (!StringUtil.isStringEmpty(liabilityPaymentDO.getRegionId().getRegionCode()))){ 
					sapLiabilityPaymentDO.setRegionCode(liabilityPaymentDO.getRegionId().getRegionCode());
				}
				logger.debug("LIABILITY PAYMENT :: Liability Interface Region Code---------->"+sapLiabilityPaymentDO.getRegionCode());
				
				if(!StringUtil.isEmptyInteger(liabilityPaymentDO.getCreatedBy())){
					sapLiabilityPaymentDO.setCreatedBy(liabilityPaymentDO.getCreatedBy());
				}
				
				if(!StringUtil.isEmptyInteger(liabilityPaymentDO.getUpdatedBy())){
					sapLiabilityPaymentDO.setUpdatedBy(liabilityPaymentDO.getUpdatedBy());
				}
				
				/*if(!StringUtil.isNull(liabilityPaymentDO.getCreatedDate())){
					sapLiabilityPaymentDO.setCreatedBy(liabilityPaymentDO.getCreatedDate());
				}*/
				
				if(!StringUtil.isNull(liabilityPaymentDO.getUpdatedDate())){
					sapLiabilityPaymentDO.setUpdatedDate(liabilityPaymentDO.getUpdatedDate());
				}
			}
			
			sapLiabilityPaymentDOs.add(sapLiabilityPaymentDO);
		}
		logger.debug("LIABILITY PAYMENT :: LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: convertLiabilityPaymentCSDDOToStagingDO :: Start");
		return sapLiabilityPaymentDOs;
	}

	private List<DTCSDCODLCLiability.CODLCLiability> prepareLiabilityTOFromDO(
			List<LiabilityDO> liabilityDO) {
		
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: Start");
		
		DTCSDCODLCLiability liabilityEntries = new DTCSDCODLCLiability();
		List<DTCSDCODLCLiability.CODLCLiability> liabilityList = null;
		DTCSDCODLCLiability.CODLCLiability liability = null;
		
		liabilityList = liabilityEntries.getCODLCLiability();
		liabilityList = new ArrayList<DTCSDCODLCLiability.CODLCLiability>();
		
		for(LiabilityDO liabilityDo : liabilityDO){
			liability = new DTCSDCODLCLiability.CODLCLiability();
			if(!StringUtil.isNull(liabilityDo)){
				
				if(!StringUtil.isNull(liabilityDo.getCustId())
						&& (!StringUtil.isStringEmpty(liabilityDo.getCustId().getCustomerCode()))){ 
					liability.setCustomerCode(liabilityDo.getCustId().getCustomerCode());
				}
				logger.debug("Liability Interface Customer Code ---------->"+liability.getCustomerCode());
				
				if(!StringUtil.isStringEmpty(liabilityDo.getTxNumber())){ 
					liability.setTransactionNo(liabilityDo.getTxNumber());
				}
				logger.debug("Liability Interface TX Number ---------->"+liability.getTransactionNo());
				
				if(!StringUtil.isNull(liabilityDo.getCreatedDate())){ 
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(liabilityDo.getCreatedDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						liability.setCreatedDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: error",e);
					}
				}
				logger.debug("Liability Interface Created Date ---------->"+liability.getCreatedDate());
				
				if(!StringUtil.isNull(liabilityDo.getChqDate())){
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(liabilityDo.getChqDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						liability.setChequeDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: error",e);;
					}
				}
				logger.debug("Liability Interface Chqque Date ---------->"+liability.getChequeDate());
				
				if(!StringUtil.isStringEmpty(liabilityDo.getChqNo())){
					liability.setChequeNo(liabilityDo.getChqNo());
				}
				logger.debug("Liability Interface Cheque No ---------->"+liability.getChequeNo());
				
				if(!StringUtil.isNull(liabilityDo.getBankId())
						&& (!StringUtil.isStringEmpty(liabilityDo.getBankId().getGlDesc()))){
					liability.setChequeBankName(liabilityDo.getBankId().getGlDesc());
				}
				logger.debug("Liability Interface CHQ Bank Name---------->"+liability.getChequeBankName());
				
				if(!StringUtil.isNull(liabilityDo.getBankId())
						&& (!StringUtil.isStringEmpty(liabilityDo.getBankId().getGlCode()))){
					liability.setBankGLCode(liabilityDo.getBankId().getGlCode());
				}
				logger.debug("Liability Interface Bank GL Code Code ---------->"+liability.getBankGLCode());
				
				if(!StringUtil.isEmptyDouble(liabilityDo.getLiabiltyAmt())){ 
					liability.setAmount(String.valueOf(liabilityDo.getLiabiltyAmt()));
				}
				logger.debug("Liability Interface Amount---------->"+liability.getAmount());
				
				if(!StringUtil.isNull(liabilityDo.getRegionId())
						&& (!StringUtil.isStringEmpty(liabilityDo.getRegionId().getRegionCode()))){ 
					liability.setRegion(liabilityDo.getRegionId().getRegionCode());
				}
				logger.debug("Liability Interface Region ---------->"+liability.getRegion());
				
				liabilityList.add(liability);
			}
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: End");
		logger.info("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: End");
		return liabilityList;
	}

	@Override
	public void saveLiabilityStagingData(List<CODLCLiability> elementsNew) throws CGSystemException{
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveLiabilityStagingData =====> Start");
		List<SAPLiabilityPaymentDO> sapLiabilityDOList = new ArrayList<SAPLiabilityPaymentDO>();
		SAPLiabilityPaymentDO sapLiabilityPaymentDO = null;
		DTCSDCODLCLiability dtcsdcodlcLiability = new DTCSDCODLCLiability();
		for(DTCSDCODLCLiability.CODLCLiability liability : elementsNew){
			
			sapLiabilityPaymentDO = new SAPLiabilityPaymentDO();
			elementsNew =  dtcsdcodlcLiability.getCODLCLiability();
			
			sapLiabilityPaymentDO.setAmount(Double.valueOf(liability.getAmount()));
			sapLiabilityPaymentDO.setBankGLCode(liability.getBankGLCode());
			sapLiabilityPaymentDO.setChequeBankName(liability.getChequeBankName());
			sapLiabilityPaymentDO.setChequeNo(liability.getChequeNo());
			sapLiabilityPaymentDO.setCustCode(liability.getCustomerCode());
			Date chequeDate =  new Date();  
	        if(liability.getChequeDate()!=null){  
	            XMLGregorianCalendar calendar = liability.getChequeDate();  
	            chequeDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
	        }  
	        sapLiabilityPaymentDO.setChequeDate(chequeDate);
			
			Date creationDate =  new Date();  
	        if(liability.getCreatedDate()!=null){  
	            XMLGregorianCalendar calendar = liability.getCreatedDate();  
	            creationDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
	        }
	        sapLiabilityPaymentDO.setCreationDate(creationDate);
	        sapLiabilityPaymentDO.setSapStatus(liability.getSAPStatus());
	        sapLiabilityPaymentDO.setTxNumber(liability.getTransactionNo());
			Date dateTime = Calendar.getInstance().getTime();
			sapLiabilityPaymentDO.setSapTimestamp(dateTime);
			sapLiabilityDOList.add(sapLiabilityPaymentDO);
		}
		integrationDAO.saveLiabilityStagingData(sapLiabilityDOList);
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveLiabilityStagingData =====> End");
	}

	/*@Override
	public void findLiabilityEntriesDtlsForSAPIntegration(SAPLiabilityEntriesTO sapLiEntriesTO) throws CGSystemException,CGBusinessException {
		
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsForSAPIntegration :: Start");
		
		List<ConsignmentDO> consignmentDOList = new ArrayList<ConsignmentDO>();
		//1 Fetch List of Product IDs of COD and LC
		List<ProductDO> productDOList = new ArrayList<>();
		String qryName = SAPIntegrationConstants.GET_PRODUCT_BY_CONSG_SERIES;
		productDOList = integrationDAO.getProductByCode(qryName);
		List<Integer> productIDList = null;
		if(!StringUtil.isEmptyColletion(productDOList)){
			productIDList = new ArrayList<Integer>();
			for(ProductDO productDO : productDOList){
				productIDList.add(productDO.getProductId());
			}
		}
		
		//1 A Step - Fecthing data from CSD Table
		// From Consignment Table where Cons is of product COD/LC 
		String queryName = SAPIntegrationConstants.GET_BOOKED_COD_LC_CONSG;
		String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND,SAPIntegrationConstants.PRODUCT_ID_LIST};
		Object paramValues[]= {sapLiEntriesTO.getSapStatus(),productIDList};
		consignmentDOList = integrationDAO.findLiabilityEntriesDtlsForSAPIntegration(queryName, paramNames, paramValues);
		
		//2 Step - Save CSD Table Data to Interface Staging Table
		List<SAPLiabilityEntriesDO> lEntriesStagingDOList = new ArrayList<SAPLiabilityEntriesDO>();
		boolean isSaved = false;
		if(!StringUtil.isNull(consignmentDOList)){ 
			lEntriesStagingDOList = convertLEntriesCSDDOToStagingDO(consignmentDOList);
			isSaved = integrationDAO.saveLiabilityEntriesStagingData(lEntriesStagingDOList);
		}
		//2 A
		//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
		//if flag = true status = C and Time stamp = current time
		//if flag = false status = N and time Stamp = current Time
		boolean isUpdated = false;
		if(isSaved){
			List<ConsignmentDO> consgDOList = new ArrayList<>();
			ConsignmentDO consgNewDO = null;
			for(ConsignmentDO consgDO : consignmentDOList){
				consgNewDO = new ConsignmentDO();
				consgNewDO.setConsgId(consgDO.getConsgId());
				logger.debug("Consg Id ------>"+consgNewDO.getConsgId());
				Date dateTime = Calendar.getInstance().getTime();
				consgNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+consgNewDO.getSapTimestamp());
				consgNewDO.setSapStatus("C");
				logger.debug("SAP Status ------>"+consgNewDO.getSapStatus());
				consgDOList.add(consgNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfLiabilityEntries(consgDOList);
		}else{
			List<ConsignmentDO> consgDOList = new ArrayList<>();
			ConsignmentDO consgNewDO = null;
			for(ConsignmentDO consgDO : consignmentDOList){
				consgNewDO = new ConsignmentDO();
				consgNewDO.setConsgId(consgDO.getConsgId());
				logger.debug("Consg Id ------>"+consgNewDO.getConsgId());
				Date dateTime = Calendar.getInstance().getTime();
				consgNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+consgNewDO.getSapTimestamp());
				consgNewDO.setSapStatus("N");
				logger.debug("SAP Status ------>"+consgNewDO.getSapStatus());
				consgDOList.add(consgNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfLiabilityEntries(consgDOList);
		}
	}*/

	@Override
	public void findLiabilityEntriesDtlsForSAPIntegration(
			SAPLiabilityEntriesTO sapLiEntriesTO) throws CGSystemException,
			CGBusinessException {

		logger.debug("CODLC BOOKING :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsForSAPIntegration() :: Start");

		List<ConsignmentDO> consignmentDOList = new ArrayList<ConsignmentDO>();
		List<ProductDO> productDOList = new ArrayList<>();
		ArrayList<Integer> productIDList = null;
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		try {
			String qryName = SAPIntegrationConstants.GET_PRODUCT_BY_CONSG_SERIES;
			productDOList = integrationDAO.getProductByCode(qryName);
			
			if(!StringUtil.isEmptyColletion(productDOList)){
				productIDList = new ArrayList<Integer>();
				for(ProductDO productDO : productDOList){
					productIDList.add(productDO.getProductId());
				}
			}
			// get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapLiEntriesTO.getMaxCheck());
			if (!StringUtil.isNull(configParamDO)) {
				String maxDataCount = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}

			 totalRecords = integrationDAO.getLiabilityEntriesCount(sapLiEntriesTO.getSapStatus(),productIDList);
			 
			// Find out total record count from Transaction table = 150
			if (!StringUtil.isEmptyLong(totalRecords)) {
				// Add for loop -
				for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount + maxDataCountLimit) {

					consignmentDOList = integrationDAO.findLiabilityEntriesDtlsForSAPIntegration(sapLiEntriesTO, maxDataCountLimit,productIDList);

					// 2 Step - Save CSD Table Data to Interface Staging Table
					List<SAPLiabilityEntriesDO> lEntriesStagingDOList = new ArrayList<SAPLiabilityEntriesDO>();

					if (!StringUtil.isEmptyColletion(consignmentDOList)) {
						lEntriesStagingDOList = convertLEntriesCSDDOToStagingDO(consignmentDOList);
						if(!StringUtil.isEmptyColletion(lEntriesStagingDOList)){
							integrationDAO.saveLiabilityEntriesStagingData(lEntriesStagingDOList);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("CODLC BOOKING :: Exception IN :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsForSAPIntegration",e);
			throw new CGSystemException(e);
		}
		logger.debug("CODLC BOOKING :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsForSAPIntegration :: End");
	}

	
	
	private List<SAPLiabilityEntriesDO> convertLEntriesCSDDOToStagingDO(List<ConsignmentDO> consignmentDOList) throws CGSystemException, CGBusinessException {
		logger.debug("CODLC BOOKING :: MiscellaneousSAPIntegrationServiceImpl :: convertLEntriesCSDDOToStagingDO :: Start");
		List<SAPLiabilityEntriesDO> sapLEntriesList = new ArrayList<SAPLiabilityEntriesDO>();
		SAPLiabilityEntriesDO sapLEntriesDO = null;
		for (ConsignmentDO consgDO : consignmentDOList) {
			/** Search Consignment Entries in Staging Table */
			sapLEntriesDO = mecUniversalDAO.getLiabilityEntryByConsigNoForSAPLiabilityScheduler(consgDO.getConsgNo());
			if (StringUtil.isNull(sapLEntriesDO)) {
				sapLEntriesDO = new SAPLiabilityEntriesDO();
				sapLEntriesDO.setStatusFlag(SAPIntegrationConstants.SAP_STATUS_B);
				BookingDO bookingDO = null;
				if (!StringUtil.isStringEmpty(consgDO.getConsgNo())) {
					sapLEntriesDO.setConsgId(consgDO.getConsgId());
					String consgNumber = consgDO.getConsgNo();
					bookingDO = bookingUniversalDAO.getBookingDtlsByConsgNo(consgNumber);
					if (!StringUtil.isNull(bookingDO)) {
						// BOOKING_DATE
						if (!StringUtil.isNull(bookingDO.getBookingDate())) {
							sapLEntriesDO.setBookingDate(bookingDO.getBookingDate());
						}
						
						// CONSIGNMENT_NO
						if (!StringUtil.isStringEmpty(bookingDO.getConsgNumber())) {
							sapLEntriesDO.setConsgNo(bookingDO.getConsgNumber());
						}
						
						// BOOKING_OFFICE_RHO_CODE
						if (!StringUtil.isEmptyInteger(bookingDO.getBookingOfficeId())) {
							Integer ofcId = bookingDO.getBookingOfficeId();
							String ofcCode = null;
							OfficeDO offcDO = organizationCommonDAO.getOfficeByIdOrCode(ofcId, ofcCode);
							if (!StringUtil.isNull(offcDO)) {
								Integer offcId = offcDO.getReportingRHO();
								String offcCode = null;
								OfficeDO offccDO = organizationCommonDAO.getOfficeByIdOrCode(offcId, offcCode);
								if (!StringUtil.isNull(offccDO)) {
									sapLEntriesDO.setBookingOfcRHOCode(offccDO.getOfficeCode());
								}
							}
						}
						
						// CUSTOMER_CODE
						if (!StringUtil.isNull(bookingDO.getCustomerId())
								&& (!StringUtil.isStringEmpty(bookingDO.getCustomerId().getCustomerCode()))) {
							sapLEntriesDO.setCustNo(bookingDO.getCustomerId().getCustomerCode());
						}

						// DESTINATION_RHO
						if (!StringUtil.isNull(bookingDO.getPincodeId())) {
							if (!StringUtil.isEmptyInteger(bookingDO.getPincodeId().getCityId())) {
								CityTO cityTO = new CityTO();
								cityTO.setCityId(bookingDO.getPincodeId().getCityId());
								CityDO cityDO = geographyServiceDAO.getCity(cityTO);
								if (!StringUtil.isNull(cityDO) && !StringUtil.isEmptyInteger(cityDO.getRegion())) {
									Integer regionId = cityDO.getRegion();
									OfficeDO ofcDO = organizationCommonDAO.getOfficesByRegionAndOfficeType(regionId);
									if (!StringUtil.isNull(ofcDO) && !StringUtil.isStringEmpty(ofcDO.getOfficeCode())) {
										sapLEntriesDO.setDestRHO(ofcDO.getOfficeCode());
									}
								}
							}
						}

						// COD_VALUE
						if (!StringUtil.isEmptyDouble(consgDO.getCodAmt())) {
							sapLEntriesDO.setCodValue(consgDO.getCodAmt());
						}

						// LC_VALUE
						if (!StringUtil.isEmptyDouble(consgDO.getLcAmount())) {
							sapLEntriesDO.setLcValue(consgDO.getLcAmount());
						}

						// BA_AMOUNT
						if (!StringUtil.isEmptyDouble(consgDO.getBaAmt())) {
							sapLEntriesDO.setBaAmount(consgDO.getBaAmt());
						}

						// CREATED_BY
						if (!StringUtil.isEmptyInteger(consgDO.getCreatedBy())) {
							sapLEntriesDO.setCreatedBy(consgDO.getCreatedBy());
						}

						// UPDATE_BY
						if (!StringUtil.isEmptyInteger(consgDO.getUpdatedBy())) {
							sapLEntriesDO.setUpdatedBy(consgDO.getUpdatedBy());
						}

						// CREATED_DATE
						if (!StringUtil.isNull(consgDO.getCreatedDate())) {
							sapLEntriesDO.setCreatedDate(consgDO.getCreatedDate());
						}

						// UPDATE_DATE
						if (!StringUtil.isNull(consgDO.getUpdatedDate())) {
							sapLEntriesDO.setUpdatedDate(consgDO.getUpdatedDate());
						}
						sapLEntriesList.add(sapLEntriesDO);
					}
					else{
						/*If the record does not exist in booking table, then update DT_SAP_OUTBOUND flag of consignment table to 'I'
						 * Once this is done, that consignment will not be picked up next time by the scheduler
						 * New Logic added by Tejas*/
						sapLEntriesDO.setConsgId(consgDO.getConsgId());
						sapLEntriesDO.setSapStatus(SAPIntegrationConstants.SAP_STATUS_I);
						integrationDAO.updateDateTimeAndStatusFlagOfLiabilityEntries(sapLEntriesDO);
					}
				}

			} else {
				Boolean isCnValueDiffer = Boolean.FALSE;
				/** Check COD Value */
				if (!StringUtil.isEmptyDouble(consgDO.getCodAmt())) {
					if(!StringUtil.isEmptyDouble(sapLEntriesDO.getCodValue())){
						if(!consgDO.getCodAmt().equals(sapLEntriesDO.getCodValue())){
							sapLEntriesDO.setCodValue(consgDO.getCodAmt());
							isCnValueDiffer = Boolean.TRUE;
						}
					}
					else{
						sapLEntriesDO.setCodValue(consgDO.getCodAmt());
						isCnValueDiffer = Boolean.TRUE;
					}
				}
				/** Check LC Value */
				if (!StringUtil.isEmptyDouble(consgDO.getLcAmount())) {
					if(!StringUtil.isEmptyDouble(sapLEntriesDO.getLcValue())){
						if(!consgDO.getLcAmount().equals(sapLEntriesDO.getLcValue())){
							sapLEntriesDO.setLcValue(consgDO.getLcAmount());
							isCnValueDiffer = Boolean.TRUE;
						}
					}
					else{
						sapLEntriesDO.setLcValue(consgDO.getLcAmount());
						isCnValueDiffer = Boolean.TRUE;
					}
				}
				/** Check BA Amount */
				if (!StringUtil.isEmptyDouble(consgDO.getBaAmt())) {
					if(!StringUtil.isEmptyDouble(sapLEntriesDO.getBaAmount())){
						if(!consgDO.getBaAmt().equals(sapLEntriesDO.getBaAmount())){
							sapLEntriesDO.setBaAmount(consgDO.getBaAmt());
							isCnValueDiffer = Boolean.TRUE;
						}
					}
					else{
						sapLEntriesDO.setBaAmount(consgDO.getBaAmt());
						isCnValueDiffer = Boolean.TRUE;
					}
				}
				
				if (isCnValueDiffer){
					// STATUS_FLAG
					sapLEntriesDO.setStatusFlag(SAPIntegrationConstants.SAP_STATUS_B);
					
					// DT_SAP_OUTBOUND
					sapLEntriesDO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
					sapLEntriesList.add(sapLEntriesDO);
				} else {
					SAPLiabilityEntriesDO consgDOList = new SAPLiabilityEntriesDO();
					consgDOList.setSapStatus(SAPIntegrationConstants.SAP_STATUS_C);
					consgDOList.setConsgId(consgDO.getConsgId());
					integrationDAO.updateDateTimeAndStatusFlagOfLiabilityEntries(consgDOList);
				}
			}
		}
		logger.debug("CODLC BOOKING :: CODLC BOOKING :: MiscellaneousSAPIntegrationServiceImpl :: convertLEntriesCSDDOToStagingDO :: End");
		return sapLEntriesList;
	}

	private List<DTCSDCODLCConsignment.CODLCConsignment> prepareLiabilityDtlsTOFromDO(
			List<Object> liabilityDetailsDOs) { 
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityDtlsTOFromDO :: Start");
		
		//DTCSDCODLCConsignment liabilityEntriesDtls = new DTCSDCODLCConsignment();
		List<DTCSDCODLCConsignment.CODLCConsignment> liabilityDtlsList = null;
		DTCSDCODLCConsignment.CODLCConsignment liabilityDtls = null;
		
		//liabilityDtlsList = liabilityEntriesDtls.getCODLCConsignment();
		liabilityDtlsList = new ArrayList<DTCSDCODLCConsignment.CODLCConsignment>();
		
		//Object objArr = liabilityDetailsDOs;
		@SuppressWarnings("rawtypes")
		Iterator it = liabilityDetailsDOs.iterator();
		while(it.hasNext()){
			liabilityDtls = new DTCSDCODLCConsignment.CODLCConsignment();
	          Object[] row = (Object[])it.next();
	          if(!StringUtil.isStringEmpty(row[0].toString())){
	        	  liabilityDtls.setConsignmentNo(row[0].toString());
	          }
	          logger.debug("Liability Consg Interface Consg No ---------->"+liabilityDtls.getConsignmentNo());
	          
	          if(!StringUtil.isStringEmpty(row[1].toString())){
	        	  liabilityDtls.setCODValue(row[1].toString());
	          }
	          logger.debug("Liability Consg Interface Cod Value ---------->"+liabilityDtls.getCODValue());
	          if(!StringUtil.isNull(row[2].toString())){ 
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(row[2].toString()));
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						liabilityDtls.setBookingDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
						logger.error("MiscellaneousSAPIntegrationServiceImpl :: prepareLiabilityTOFromDO :: error",e);;
					}
				}
	          logger.debug("Liability Consg Interface Booking Date ---------->"+liabilityDtls.getBookingDate());
	          if(!StringUtil.isStringEmpty(row[3].toString())){
	        	  liabilityDtls.setBookingOfficeRHOCode(row[3].toString());
	          }
	          logger.debug("Liability Consg Interface Office ID ---------->"+liabilityDtls.getBookingOfficeRHOCode());
	          liabilityDtlsList.add(liabilityDtls);
	       }
		
	/*	for(LiabilityDetailsDO liabilityDtlsDo : liabilityDetailsDOs){
			liabilityDtls = new DTCSDCODLCConsignment.CODLCConsignment();
			if(!StringUtil.isNull(liabilityDtlsDo)){
				
				//Booking Ofc RHO CODE - not available in DB
				
				//Booking Date - not available in DB- created Date
				
				if(!StringUtil.isNull(liabilityDtlsDo.getCreatedDate())){ 
					GregorianCalendar gregCalenderDdate = new GregorianCalendar();
					gregCalenderDdate.setTime(liabilityDtlsDo.getCreatedDate());
					try {
						XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
						liabilityDtls.setBookingDate(xmlGregCalDate);
					} catch (DatatypeConfigurationException e) {
					}
				}
				logger.debug("Liability Consg Interface Booking Date ---------->"+liabilityDtls.getBookingDate());
				//Consg No
				
				if(!StringUtil.isNull(liabilityDtlsDo.getConsgId())
						&& (!StringUtil.isStringEmpty(liabilityDtlsDo.getConsgId().getConsgNo()))){
					liabilityDtls.setConsignmentNo(liabilityDtlsDo.getConsgId().getConsgNo());
				}
				logger.debug("Consg No ---------->"+liabilityDtls.getConsignmentNo());
				
				//cod Value
				if(!StringUtil.isEmptyDouble(liabilityDtlsDo.getCodLcAmt())){
					liabilityDtls.setCODValue(String.valueOf(liabilityDtlsDo.getCodLcAmt()));
				}
				logger.debug("COD Value---------------->"+liabilityDtls.getCODValue());
				
				//LC Value
				if(!StringUtil.isEmptyDouble(liabilityDtlsDo.getCollectedAmt())){
					liabilityDtls.setLCValue(String.valueOf(liabilityDtlsDo.getCollectedAmt()));
				}
				logger.debug("LC Value---------------->"+liabilityDtls.getLCValue());
				
				//Dest RHO
				
				//Status flag
				
				//RTODRSUpdateDate
				
				//RTODate
				liabilityDtlsList.add(liabilityDtls);
			}
		}*/
		return liabilityDtlsList; 
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public void saveLcLiabilityConsgStagingData(List<CODLCConsignment> elementsNew) throws CGSystemException{
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveLcLiabilityConsgStagingData =====> Start");
		List<SAPLiabilityEntriesDO> sapLiabilityEntriesDOList = new ArrayList<SAPLiabilityEntriesDO>();
		SAPLiabilityEntriesDO sapLiabilityEntriesDO = null;
		DTCSDCODLCConsignment dtcsdcodlcLiability = new DTCSDCODLCConsignment();
		for(DTCSDCODLCConsignment.CODLCConsignment liability : elementsNew){
			
			sapLiabilityEntriesDO = new SAPLiabilityEntriesDO();
			elementsNew =  (List<CODLCConsignment>) dtcsdcodlcLiability.getCODLCConsignment();
			
			Date bookingDate =  new Date();  
			if(liability.getBookingDate()!=null){  
				XMLGregorianCalendar calendar = liability.getBookingDate();  
				bookingDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
			}
			sapLiabilityEntriesDO.setBookingDate(bookingDate);
			
			sapLiabilityEntriesDO.setBookingOfcRHOCode(liability.getBookingOfficeRHOCode());
			sapLiabilityEntriesDO.setCodValue(Double.valueOf(liability.getCODValue()));
			sapLiabilityEntriesDO.setConsgNo(liability.getConsignmentNo());
			//sapLiabilityEntriesDO.setLcValue(Double.valueOf(liability.getLCValue()));
			sapLiabilityEntriesDO.setSapStatus(liability.getSAPStatus());
			Date dateTime = Calendar.getInstance().getTime();
			sapLiabilityEntriesDO.setSapTimestamp(dateTime);
			sapLiabilityEntriesDOList.add(sapLiabilityEntriesDO);
		}
		integrationDAO.saveLcLiabilityConsgStagingData(sapLiabilityEntriesDOList);
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: saveLcLiabilityConsgStagingData =====> End");
	}*/

	@Override
	public void updateExpenseStagingStatusFlag(String sapStatus,List<SAPExpenseDO> sapExpenseDOList,String exception)
			throws CGSystemException {
		List<SAPExpenseDO> sapExpDOList = new ArrayList<>();
		SAPExpenseDO sapExpNewDO = null;
		for(SAPExpenseDO sapExpDO : sapExpenseDOList){
			sapExpNewDO = new SAPExpenseDO();
			sapExpNewDO.setId(sapExpDO.getId());
			logger.debug("Expense Id ------>"+sapExpNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapExpNewDO.setSapTimestamp(dateTime);
			logger.debug("Date Stamp ------>"+sapExpNewDO.getSapTimestamp());
			sapExpNewDO.setSapStatus(sapStatus);
			logger.debug("SAP Status ------>"+sapExpNewDO.getSapStatus());
			sapExpNewDO.setException(exception);
			sapExpDOList.add(sapExpNewDO);
		}
		integrationDAO.updateExpenseStagingStatusFlag(sapExpDOList);
	}

	@Override
	public List<ExpenseDO> getAllExpenseOfficeRHO() throws CGSystemException,
			CGBusinessException {
		List<ExpenseDO> expenseRHOList = new ArrayList<ExpenseDO>();
		String queryName = SAPIntegrationConstants.QRY_PARAM_EXPENSE_RHO_OFC;
		expenseRHOList = integrationDAO.getAllExpenseOfficeRHO(queryName);
		return expenseRHOList;
	}

	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	@Override
	public void updateCollnStagingStatusFlag(List<SAPCollectionDO> sapCollectionDOList, String sapStatus,String exception)throws CGSystemException {
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: UpdateCollnStagingStatusFlag :: Start");
			List<SAPCollectionDO> sapCollnDOList = new ArrayList<>();
			SAPCollectionDO sapCollnNewDO = null;
			for(SAPCollectionDO sapExpDO : sapCollectionDOList){
				sapCollnNewDO = new SAPCollectionDO();
				sapCollnNewDO.setId(sapExpDO.getId());
				logger.debug("Expense Id ------>"+sapCollnNewDO.getId());
				Date dateTime = Calendar.getInstance().getTime();
				sapCollnNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+sapCollnNewDO.getSapTimestamp());
				sapCollnNewDO.setSapStatus(sapStatus);
				logger.debug("SAP Status ------>"+sapCollnNewDO.getSapStatus());
				sapCollnNewDO.setException(sapExpDO.getException());
				logger.debug("Collection Interface Exception------>"+sapCollnNewDO.getException());
				sapCollnDOList.add(sapCollnNewDO);
			}
			integrationDAO.updateCollnStagingStatusFlag(sapCollnDOList);
		logger.debug("COLLECTION :: MiscellaneousSAPIntegrationServiceImpl :: UpdateCollnStagingStatusFlag :: End");	
		}

	/**
	 * @param geographyServiceDAO the geographyServiceDAO to set
	 */
	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}

/*	@Override
	public void updateLiabilityPaymentStagingStatusFlag(String sapStatus,List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList) throws CGSystemException{
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateLiabilityPaymentStagingStatusFlag :: Start");
		List<SAPLiabilityPaymentDO> sapLiabilityPayDOList = new ArrayList<>();
		SAPLiabilityPaymentDO sapLiabilityPayDO = null;
		for(SAPLiabilityPaymentDO sapLiabPaymentDO : sapLiabilityPaymentDOList){
			sapLiabilityPayDO = new SAPLiabilityPaymentDO();
			sapLiabilityPayDO.setId(sapLiabPaymentDO.getId());
			logger.debug("Expense Id ------>"+sapLiabPaymentDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapLiabilityPayDO.setSapTimestamp(dateTime);
			logger.debug("Date Stamp ------>"+sapLiabPaymentDO.getSapTimestamp());
			sapLiabilityPayDO.setSapStatus(sapStatus);
			logger.debug("SAP Status ------>"+sapLiabPaymentDO.getSapStatus());
			sapLiabilityPayDOList.add(sapLiabilityPayDO);
		}
		integrationDAO.updateLiabilityPaymentStagingStatusFlag(sapLiabilityPayDOList);
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateLiabilityPaymentStagingStatusFlag :: End");
	}*/
	
	@Override
	public void updateLiabilityPaymentStagingStatusFlag(String sapStatus,
			List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList,String exception)
			throws CGSystemException {
		logger.debug("LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: updateLiabilityPaymentStagingStatusFlag :: Start");
		List<SAPLiabilityPaymentDO> sapLiabilityPayDOList = new ArrayList<>();
		SAPLiabilityPaymentDO sapLiabilityPayDO = null;
		for (SAPLiabilityPaymentDO sapLiabPaymentDO : sapLiabilityPaymentDOList) {
			sapLiabilityPayDO = new SAPLiabilityPaymentDO();
			sapLiabilityPayDO.setId(sapLiabPaymentDO.getId());
			logger.debug("Expense Id ------>" + sapLiabPaymentDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapLiabilityPayDO.setSapTimestamp(dateTime);
			logger.debug("Date Stamp ------>"
					+ sapLiabPaymentDO.getSapTimestamp());
			sapLiabilityPayDO.setSapStatus(sapStatus);
			logger.debug("SAP Status ------>" + sapLiabPaymentDO.getSapStatus());
			sapLiabilityPayDO.setException(exception);
			sapLiabilityPayDOList.add(sapLiabilityPayDO);
		}
		integrationDAO
				.updateLiabilityPaymentStagingStatusFlag(sapLiabilityPayDOList);
		logger.debug("LIABILITY PAYMENT :: MiscellaneousSAPIntegrationServiceImpl :: updateLiabilityPaymentStagingStatusFlag :: End");
	}


	/**
	 * @param mecUniversalDAO the mecUniversalDAO to set
	 */
	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}

	/**
	 * @param bookingUniversalDAO the bookingUniversalDAO to set
	 */
	public void setBookingUniversalDAO(BookingUniversalDAO bookingUniversalDAO) {
		this.bookingUniversalDAO = bookingUniversalDAO;
	}

	/*@Override
	public void findConsgForDeliveredFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForDeliveredFromStaging :: Start");
		try{
			List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
			String stagingQueryName = SAPIntegrationConstants.GET_COD_LC_CONSG_STATUS_FROM_STAGING;
			String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
			Object stagingParamValues[]= {sapCODLCTO.getSapStatus()};
			sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
			
			List<ConsignmentDO> consgDeliveredDOList = null;
			if(!StringUtil.isEmptyColletion(sapCODLCDOList)){
				consgDeliveredDOList = new ArrayList<ConsignmentDO>(); 
				for(SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList){
					if(!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())){
						ConsignmentDO consgDO = new ConsignmentDO();
						String consgNo = sapCODLCDO.getConsgNo();
						consgDO = integrationDAO.getConsgStatusDelivered(consgNo);
						if(!StringUtil.isNull(consgDO)){
							consgDeliveredDOList.add(consgDO);
						}
					}
				}
				List<SAPLiabilityEntriesDO> sapCODLCDIList = new ArrayList<>(); 
				SAPLiabilityEntriesDO sapCODLCDO = null;
				for(ConsignmentDO consgDeliveredDO : consgDeliveredDOList){
					sapCODLCDO = new SAPLiabilityEntriesDO();
					if(!StringUtil.isStringEmpty(consgDeliveredDO.getConsgStatus())){
						sapCODLCDO.setConsgDelivered(consgDeliveredDO.getConsgStatus());
					}
					logger.debug("Staging Consg Delivered Status ----->"+sapCODLCDO.getConsgDelivered());
					sapCODLCDO.setConsgNo(consgDeliveredDO.getConsgNo());
					logger.debug("Staging Consg No ----->"+sapCODLCDO.getConsgNo());
					Date dateTime = Calendar.getInstance().getTime();
					sapCODLCDO.setSapTimestamp(dateTime);
					logger.debug("Staging Consg Delivered Date Stamp----->"+sapCODLCDO.getSapTimestamp());
					sapCODLCDIList.add(sapCODLCDO);
				}
				integrationDAO.updateConsgDeliveredStatusInStaging(sapCODLCDIList);
			}
		}catch(Exception e){
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForDeliveredFromStaging :: End");
	}
*/
	
	@Override
	public void findConsgForDeliveredFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("CODLC DELIVERED :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForDeliveredFromStaging :: Start");
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		List<SAPLiabilityEntriesDO> sapCODLCDOList = null;
		Long totalRecords;
		Long initialCount;
		try {
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapCODLCTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getCODLCStagingCount(sapCODLCTO.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
					//1 Step - Fetching data from CSD Table
					
					sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(sapCODLCTO, maxDataCountLimit);
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					
					if (!StringUtil.isEmptyColletion(sapCODLCDOList)) {
						//consgDeliveredDOList = new ArrayList<ConsignmentDO>();
						//Find Consg Delivery ofc from ff_f_del_dtls table and compare with staging dest RTHO if equals then update and no then insert new entry
						for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) {
							if (!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())) {
								ConsignmentDO consgDO = null;
								String destOfcCode = null;
								String consgNo = sapCODLCDO.getConsgNo();
								consgDO = integrationDAO.getConsgStatusDelivered(consgNo);
								if (!StringUtil.isNull(consgDO)) {
								destOfcCode = integrationDAO.getDeliveryDetails(consgNo);
								if(!StringUtil.isStringEmpty(destOfcCode)){
									Integer officeId = null;
									OfficeDO ofcDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, destOfcCode);
										if(!StringUtil.isNull(ofcDO)
												&& !StringUtil.isEmptyInteger(ofcDO.getReportingRHO())){
											officeId = ofcDO.getReportingRHO();
											destOfcCode = null;
											ofcDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, destOfcCode);
											if(!StringUtil.isNull(ofcDO)){
												destOfcCode = ofcDO.getOfficeCode();
											}
										}
										if(!StringUtil.isStringEmpty(destOfcCode)
												&& destOfcCode.equalsIgnoreCase(sapCODLCDO.getDestRHO())){
												if (!StringUtil.isStringEmpty(consgDO.getConsgStatus())) {
													sapCODLCDO.setConsgDelivered(consgDO.getConsgStatus());
												}
												sapCODLCDO.setConsgNo(consgDO.getConsgNo());
												integrationDAO.updateConsgDeliveredStatusInStaging(sapCODLCDO);
											}
										else{
											sapCODLCDO.setStatusFlag(SAPIntegrationConstants.STATUS_FLAG_M);
											sapCODLCDO.setConsgDelivered(CommonConstants.CONSIGNMENT_STATUS_DELV);
											sapCODLCDO.setDestRHO(destOfcCode);
											sapCODLCDO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
											integrationDAO.saveMisrouteEntry(sapCODLCDO);
										}
									}
								}
							}
						}
					}
				} 
			}
		}catch (Exception e) {
			logger.error("CODLC DELIVERED :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForDeliveredFromStaging :: Exception  "+ e.getLocalizedMessage());
		}
		logger.debug("CODLC DELIVERED :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForDeliveredFromStaging :: End");
	}
	
	/*@Override
	public void findConsgForRTOFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTOFromStaging :: Start");
		try{
			List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
			String stagingQueryName = SAPIntegrationConstants.GET_COD_LC_CONSG_STATUS_FROM_STAGING;
			String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
			Object stagingParamValues[]= {sapCODLCTO.getSapStatus()};
			sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
			
			if(!StringUtil.isEmptyColletion(sapCODLCDOList)){
				List<SAPLiabilityEntriesDO> sapCODRTOList = new ArrayList<SAPLiabilityEntriesDO>();
				for(SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList){
					if(!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())){
						ManifestDO manifestDO = new ManifestDO();
						String consgNo = sapCODLCDO.getConsgNo();
						Integer consgId = sapCODLCDO.getConsgId(); 
						manifestDO = integrationDAO.getConsgStatusRTO(consgId);
						if(!StringUtil.isNull(manifestDO)){
							if(!StringUtil.isNull(manifestDO.getConsignments())){
								Set<ConsignmentDO> consgManiSet = manifestDO.getConsignments();
								for(ConsignmentDO cmDO : consgManiSet){
									logger.debug("Cons No From Staging---->"+consgNo);
									SAPLiabilityEntriesDO sapRTODO  = null;
									if((!StringUtil.isStringEmpty(cmDO.getConsgNo())
													&& (cmDO.getConsgNo().equals(consgNo)))){
										logger.debug("Consg No from Manifest Table ---->"+cmDO.getConsgNo());
										sapRTODO = new SAPLiabilityEntriesDO();
										sapRTODO.setConsgNo(cmDO.getConsgNo());
										sapRTODO.setRtoDate(manifestDO.getManifestDate());
										sapRTODO.setSapStatus("N");
										sapCODRTOList.add(sapRTODO);
									}
								}
							}
						}
					}
				}
				integrationDAO.updateConsgRTOStatusInStaging(sapCODRTOList);
			}
		}catch(Exception e){
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTOFromStaging :: End");
		
	}
*/
	
	@Override
	public void findConsgForRTOFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("CODLC RTO :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTOFromStaging :: Start");
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		try {
			//get max data constant from table - ff_d_config_param = 10
			//having doubt
			configParamDO = mecUniversalDAO.getMaxDataCount(sapCODLCTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getCODLCStagingCount(sapCODLCTO.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
			
			List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
			sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(sapCODLCTO, maxDataCountLimit);
			//changed in dec and impl
			//2 Step - Save CSD Table Data to Interface Staging Table
			//no need to save data
			if (!StringUtil.isEmptyColletion(sapCODLCDOList)) {
				List<SAPLiabilityEntriesDO> sapCODRTOList = new ArrayList<SAPLiabilityEntriesDO>();
				for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) {
					if (!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())) {
						ManifestDO manifestDO = new ManifestDO();
						String consgNo = sapCODLCDO.getConsgNo();
						Integer consgId = sapCODLCDO.getConsgId();
						manifestDO = integrationDAO.getConsgStatusRTO(consgId);
						if (!StringUtil.isNull(manifestDO)) {
							if (!StringUtil.isNull(manifestDO.getConsignments())) {
								Set<ConsignmentDO> consgManiSet = manifestDO.getConsignments();
								for (ConsignmentDO cmDO : consgManiSet) {
									SAPLiabilityEntriesDO sapRTODO = null;
									if ((!StringUtil.isStringEmpty(cmDO.getConsgNo()) && (cmDO.getConsgNo().equals(consgNo)))) {
										sapRTODO = new SAPLiabilityEntriesDO();
										sapRTODO.setConsgNo(cmDO.getConsgNo());
										sapRTODO.setRtoDate(manifestDO.getManifestDate());
										sapRTODO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
										sapRTODO.setUpdatedBy(SAPIntegrationConstants.SAP_USER_ID);
										sapCODRTOList.add(sapRTODO);
									}
								}
							}
						}
					}
				} // end of loop
				if(!StringUtil.isEmptyColletion(sapCODRTOList)){
					integrationDAO.updateConsgRTOStatusInStaging(sapCODRTOList);
				}
			}
		}
			}
		}catch (Exception e) {
			logger.error("CODLC RTO :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTOFromStaging :: End",e);
		}
		logger.debug("CODLC RTO :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTOFromStaging :: End");
	}
	
	
	
/*	@Override
	public void findConsgForRTODRSFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: Start");
		try{
			List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
			String stagingQueryName = SAPIntegrationConstants.GET_COD_LC_STAGING_CONSG_FOR_RTODRS;
			String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
			Object stagingParamValues[]= {sapCODLCTO.getSapStatus()};
			sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
			
			List<ConsignmentDO> rtoDRSConsgList = null;
			if(!StringUtil.isEmptyColletion(sapCODLCDOList)){
				rtoDRSConsgList = new ArrayList<ConsignmentDO>(); 
				for(SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList){
					if(!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())){
						ConsignmentDO consgDO = new ConsignmentDO();
						String consgNo = sapCODLCDO.getConsgNo();
						consgDO = integrationDAO.getConsgStatusRTODRS(consgNo);
						if(!StringUtil.isNull(consgDO)){
							rtoDRSConsgList.add(consgDO);
						}
					}
				}
				List<SAPLiabilityEntriesDO> sapCODLCDIList = new ArrayList<>(); 
				SAPLiabilityEntriesDO sapCODLCDO = null;
				for(ConsignmentDO rtoDRSConsgDO : rtoDRSConsgList){
					sapCODLCDO = new SAPLiabilityEntriesDO();
					if(!StringUtil.isNull(rtoDRSConsgDO.getDeliveredDate())){
						sapCODLCDO.setRtoDrsDate(rtoDRSConsgDO.getDeliveredDate());
					}
					logger.debug("Staging RO DRS DATE ----->"+sapCODLCDO.getRtoDrsDate());
					sapCODLCDO.setConsgNo(rtoDRSConsgDO.getConsgNo());
					logger.debug("Staging Consg No ----->"+sapCODLCDO.getConsgNo());
					Date dateTime = Calendar.getInstance().getTime();
					sapCODLCDO.setSapTimestamp(dateTime);
					logger.debug("Staging Consg Delivered Date Stamp----->"+sapCODLCDO.getSapTimestamp());
					sapCODLCDO.setSapStatus("N");
					sapCODLCDIList.add(sapCODLCDO);
				}
				integrationDAO.updateConsgRTODRSStatusInStaging(sapCODLCDIList);
			}
		}catch(Exception e){
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: End");
	}*/
	
	
	

	@Override
	public void findConsgForRTODRSFromStaging(SAPLiabilityEntriesTO sapCODLCTO) {
		logger.debug("CODLC RTODRS :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: Start");
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		try {
			//get max data constant from table - ff_d_config_param = 10
			//having doubt
			configParamDO = mecUniversalDAO.getMaxDataCount(sapCODLCTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getCODLCStagingCount(sapCODLCTO.getSapStatus());
			
				if(!StringUtil.isEmptyLong(totalRecords)){
					//Add for loop - 
						for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
							
					List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<SAPLiabilityEntriesDO>();
					sapCODLCDOList = integrationDAO.findConsgStatusFromStaging(sapCODLCTO, maxDataCountLimit);
					//changed in dec and impl
					//2 Step - Save CSD Table Data to Interface Staging Table
					//no need to save data
					List<ConsignmentDO> rtoDRSConsgList = null;
					if (!StringUtil.isEmptyColletion(sapCODLCDOList)) {
						rtoDRSConsgList = new ArrayList<ConsignmentDO>();
						for (SAPLiabilityEntriesDO sapCODLCDO : sapCODLCDOList) {
							if (!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())) {
								ConsignmentDO consgDO = new ConsignmentDO();
								String consgNo = sapCODLCDO.getConsgNo();
								consgDO = integrationDAO.getConsgStatusRTODRS(consgNo);
								if (!StringUtil.isNull(consgDO)) {
									rtoDRSConsgList.add(consgDO);
								}
							}
						}
						List<SAPLiabilityEntriesDO> sapCODLCDIList = new ArrayList<>();
						SAPLiabilityEntriesDO sapCODLCDO = null;
						for (ConsignmentDO rtoDRSConsgDO : rtoDRSConsgList) {
							sapCODLCDO = new SAPLiabilityEntriesDO();
							if (!StringUtil.isNull(rtoDRSConsgDO.getDeliveredDate())) {
								sapCODLCDO.setRtoDrsDate(rtoDRSConsgDO.getDeliveredDate());
								sapCODLCDO.setConsgNo(rtoDRSConsgDO.getConsgNo());
								sapCODLCDO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
								sapCODLCDIList.add(sapCODLCDO);
							}
						}
						if(!StringUtil.isEmptyColletion(sapCODLCDIList)){
							integrationDAO.updateConsgRTODRSStatusInStaging(sapCODLCDIList);
						}
					}
				} 
			}
		}catch (Exception e) {
			logger.error("Exception in :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: ",e);
		}
		logger.debug("CODLC RTODRS :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: End");
	}


	/*@Override
	public void findConsgForConsigneeFromCollection() {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: Start");
		boolean isUpdated = false;
		List<CollectionDtlsDO> collnDtlsList = null;
		SAPLiabilityEntriesDO sapCODLCDO = null;
		List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<>();
		collnDtlsList = integrationDAO.getConsgStatusConsignee();
		if(!StringUtil.isEmptyColletion(collnDtlsList)){
			for(CollectionDtlsDO collnDO : collnDtlsList){
				if(!StringUtil.isNull(collnDO.getConsgDO())
						&& (!StringUtil.isStringEmpty(collnDO.getConsgDO().getConsgNo()))){
					String consgNo = collnDO.getConsgDO().getConsgNo();
					sapCODLCDO = integrationDAO.getCODLCDtlsByConsgNo(consgNo);
					if(!StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())){
						if(!StringUtil.isNull(collnDO.getCreatedDate())){
							sapCODLCDO.setConsigneeDate(collnDO.getCreatedDate());
						}
						logger.debug("Consignee Date ----> "+sapCODLCDO.getConsigneeDate());
						sapCODLCDO.setSapStatus("N");
						sapCODLCDO.setConsgNo(consgNo);
						sapCODLCDOList.add(sapCODLCDO);
					}
				}
			}
			isUpdated = integrationDAO.updateConsigneeDateInStaging(sapCODLCDOList);
			//update DT SAP OUTBOUND Status as C so nect time consignne scheduler will not pick up that record.
			integrationDAO.updateConsgCollStatus(collnDtlsList,isUpdated);
				
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findConsgForRTODRSFromStaging :: End");
	}*/


	@Override
	public void findConsgForConsigneeFromCollection() {
		logger.debug("CODLC CONSIGNEE :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForConsigneeFromCollection :: Start");
		List<CollectionDtlsDO> collnDtlsList = null;
		SAPLiabilityEntriesDO sapCODLCDO = null;
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		String consgNo = null;
		try
		{
			//get max data constant from table - ff_d_config_param = 10
			//having doubt
			configParamDO = mecUniversalDAO.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getCountOfConsgStatusConsignee();
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
					for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
						collnDtlsList = integrationDAO.getConsgStatusConsignee(maxDataCountLimit);
							if (!StringUtil.isEmptyColletion(collnDtlsList)) {
								for (CollectionDtlsDO collnDO : collnDtlsList) 
								{
									if (!StringUtil.isNull(collnDO.getConsgDO())
											&& (!StringUtil.isStringEmpty(collnDO.getConsgDO().getConsgNo()))) {
										consgNo = collnDO.getConsgDO().getConsgNo();
										sapCODLCDO = integrationDAO.getCODLCDtlsByConsgNo(consgNo);
										if (!StringUtil.isNull(sapCODLCDO) && !StringUtil.isStringEmpty(sapCODLCDO.getConsgNo())) {
											if (!StringUtil.isNull(collnDO.getCreatedDate())) {
												sapCODLCDO.setConsigneeDate(collnDO.getCreatedDate());
												sapCODLCDO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
												sapCODLCDO.setConsgNo(consgNo);
												
												// Update the data in ff_f_collection_entries and ff_f_sap_liability_entries tables accordingly
												integrationDAO.updateSapStatusAndConsigneeDateForConsgInterface(collnDO, sapCODLCDO);
											}
										}
									}
								} // end of for loop
				            }
			             }
		     }
		}catch (Exception e) {
			logger.error("CODLC CONSIGNEE :: Error in :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForConsigneeFromCollection ::",e);
		}
		logger.debug("CODLC CONSIGNEE :: CODLC CONSIGNEE :: MiscellaneousSAPIntegrationServiceImpl :: findConsgForConsigneeFromCollection :: End");
	}
	
	
	
	/*@Override
	public List<SAPLiabilityEntriesDO> findLiabilityEntriesDtlsFromStaging(SAPLiabilityEntriesTO sapLiEntriesTO) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsFromStaging :: Start");
		List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<>();
		try{
			String stagingQueryName = SAPIntegrationConstants.QRY_PARAM_GET_COD_LC_FROM_STAGING;
			String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
			Object stagingParamValues[]= {sapLiEntriesTO.getSapStatus()};
			sapCODLCDOList = integrationDAO.findCODLCFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
		}catch(Exception e){
		}
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsFromStaging :: End");
		return sapCODLCDOList;
	}
*/
	

	@Override
	public List<SAPLiabilityEntriesDO> findLiabilityEntriesDtlsFromStaging(
			SAPLiabilityEntriesTO sapLiEntriesTO) {
		logger.debug("CODLC STAGING :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsFromStaging :: Start");
		List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<>();
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		try {
				//get max data constant from table - ff_d_config_param = 10
				//having doubt
				configParamDO = mecUniversalDAO.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
				if(!StringUtil.isNull(configParamDO)){
					String maxDataCount  = configParamDO.getParamValue();
					maxDataCountLimit = Long.valueOf(maxDataCount);
				}
				
				totalRecords = integrationDAO
						.getLiabilityEntriesDtlsCount(sapLiEntriesTO.getSapStatus());
				
				if (!StringUtil.isEmptyLong(totalRecords)) {
					// Add for loop -
					for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount
							+ maxDataCountLimit) {
				
						// 1 A Step - Fecthing data from CSD Table
						// From Consignment Table where Cons is of product COD/LC
			
						sapCODLCDOList = integrationDAO.findCODLCFromStaging( sapLiEntriesTO, maxDataCountLimit );
					}
				}
		}catch (Exception e) {
			logger.error("CODLC STAGING :: Exception IN :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsFromStaging :: ",e);
		}
		logger.debug("CODLC STAGING :: MiscellaneousSAPIntegrationServiceImpl :: findLiabilityEntriesDtlsFromStaging :: End");
		return sapCODLCDOList;
	}



	
	/*@Override
	public void updateCODLCStagingStatusFlag(String sapStatus,List<SAPLiabilityEntriesDO> sapLiabilityEntriesList) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingStatusFlag :: Start");
		List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<>();
		SAPLiabilityEntriesDO sapCODLCDO = null;
		for(SAPLiabilityEntriesDO sapLiabilityEntDo : sapLiabilityEntriesList){
			sapCODLCDO = new SAPLiabilityEntriesDO();
			sapCODLCDO.setId(sapLiabilityEntDo.getId());
			logger.debug("COD LC Id ------>"+sapLiabilityEntDo.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapCODLCDO.setSapTimestamp(dateTime);
			logger.debug("Date Stamp ------>"+sapLiabilityEntDo.getSapTimestamp());
			sapCODLCDO.setSapStatus(sapStatus);
			logger.debug("SAP Status ------>"+sapLiabilityEntDo.getSapStatus());
			sapCODLCDOList.add(sapCODLCDO);
		}
		integrationDAO.updateCODLCStagingStatusFlag(sapCODLCDOList);
	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingStatusFlag :: End");
	}*/
	

@Override
	public void updateCODLCStagingStatusFlag(String sapStatus,
			List<SAPLiabilityEntriesDO> sapLiabilityEntriesList,String exception) {
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingStatusFlag :: Start");
		List<SAPLiabilityEntriesDO> sapCODLCDOList = new ArrayList<>();
		SAPLiabilityEntriesDO sapCODLCDO = null;
		for (SAPLiabilityEntriesDO sapLiabilityEntDo : sapLiabilityEntriesList) {
			sapCODLCDO = new SAPLiabilityEntriesDO();
			sapCODLCDO.setId(sapLiabilityEntDo.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapCODLCDO.setSapTimestamp(dateTime);
			sapCODLCDO.setSapStatus(sapStatus);
			sapCODLCDO.setException(exception);
			sapCODLCDOList.add(sapCODLCDO);
		}
		integrationDAO.updateCODLCStagingStatusFlag(sapCODLCDOList);
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingStatusFlag :: End");
	}

/*	@Override
	public List<SAPOutstandingPaymentDO> findOutstandingPaymentDtls(SAPOutstandingPaymentTO outPaymentTO) throws CGSystemException, CGBusinessException{
		
		logger.debug("MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Start");
		
		//1 Step - Fecthing data from Staging Table
		List<SAPOutstandingPaymentDO> sapOutstandingReportDOs = new ArrayList<SAPOutstandingPaymentDO>();
			try{
				String stagingQueryName = SAPIntegrationConstants.QRY_PARAM_GET_OUT_STANDING_PAYMENT_DTLS;
				String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
				Object stagingParamValues[]= {outPaymentTO.getSapStatus()};
				sapOutstandingReportDOs = integrationDAO.findOutstandingPaymentDtls(stagingQueryName, stagingParamNames, stagingParamValues);
			}catch(Exception e){
				logger.error("MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Exception  ", e);
			}
				
		logger.debug(" MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: End");
		return sapOutstandingReportDOs;
	}
*/

@Override
public List<SAPOutstandingPaymentDO> findOutstandingPaymentDtls(
		SAPOutstandingPaymentTO outPaymentTO) throws CGSystemException,
		CGBusinessException {

	logger.debug("OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Start");
	List<SAPOutstandingPaymentDO> sapOutstandingReportDOs = new ArrayList<SAPOutstandingPaymentDO>();
	ConfigurableParamsDO configParamDO = null;
	Long maxDataCountLimit = null;
	Long totalRecords;
	Long initialCount;
	try {
			//get max data constant from table - ff_d_config_param = 10
			//having doubt
			configParamDO = mecUniversalDAO.getMaxDataCount(SAPIntegrationConstants.MAX_CHECK);
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			//Find out total record count from Transaction table = 150
			totalRecords = integrationDAO
					.getOutstandingPaymentDtls(outPaymentTO.getSapStatus());
			if (!StringUtil.isEmptyLong(totalRecords)) {
				// Add for loop -
				for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount
						+ maxDataCountLimit) {
					// 1 Step - Fecthing data from Staging Table
					
					sapOutstandingReportDOs = integrationDAO
							.findOutstandingPaymentDtls(outPaymentTO,maxDataCountLimit);
				}
	}
	}catch (Exception e) {
		logger.error(
				"OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Exception  ",
				e);
	}

	logger.debug("OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: End");
	return sapOutstandingReportDOs;
}


	@Override
	public void updateOutStandingPaymentStagingStatusFlag(String sapStatus,List<SAPOutstandingPaymentDO> sapOutPaymentDOs,String exception) {
		logger.debug("OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: updateOutStandingPaymentStagingStatusFlag :: Start");
		List<SAPOutstandingPaymentDO> sapOutStandingPaymentList = new ArrayList<>();
		SAPOutstandingPaymentDO sapOutStandingPaymentDO = null;
		try{
			for(SAPOutstandingPaymentDO sapOutstandingReportDO : sapOutPaymentDOs){
				sapOutStandingPaymentDO = new SAPOutstandingPaymentDO();
				sapOutStandingPaymentDO.setId(sapOutstandingReportDO.getId());
				logger.debug("COP Id ------>"+sapOutStandingPaymentDO.getId());
				Date dateTime = Calendar.getInstance().getTime();
				sapOutStandingPaymentDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+sapOutStandingPaymentDO.getSapTimestamp());
				sapOutStandingPaymentDO.setSapStatus(sapStatus);
				logger.debug("SAP Status ------>"+sapOutStandingPaymentDO.getSapStatus());
				sapOutStandingPaymentDO.setException(exception);
				sapOutStandingPaymentList.add(sapOutStandingPaymentDO);
			}
			integrationDAO.updateOutStandingPaymentStagingStatusFlag(sapOutStandingPaymentList);
		}catch(Exception e){
			logger.error("OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: findOutstandingPaymentDtls :: Exception  ", e);
		}
	logger.debug("OUTSTANDING REPORT :: MiscellaneousSAPIntegrationServiceImpl :: updateOutStandingPaymentStagingStatusFlag :: End");
	}

	@Override
	public boolean findConsignmentCollection(SAPCollectionTO sapcollnTO) {
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollection :: Start");
		List<BookingInterfaceWrapperDO> bookingWrapperDOs =  null;
		CollectionDO collectionDO = null;
		boolean isSaved = false;
		Long maxDataCountLimit = null; 
		try {
			List<Integer> officeIds = null;
			String queryName = SAPIntegrationConstants.QRY_GET_ALL_BOOKING_OFFICES_OF_CURRENT_DAY;
			officeIds = integrationDAO.getBookingOfcsOfCurrDate(queryName);

			sapcollnTO.setSapStatus("N");
			sapcollnTO.setBookingType(SAPIntegrationConstants.BOOKING_TYPE_VALUE);
			sapcollnTO.setPaymentCode(SAPIntegrationConstants.PAYMENT_CODE_VALUE);
			
			if (!StringUtil.isEmptyColletion(officeIds)) {
				try {
					bookingWrapperDOs = new ArrayList<>();
					bookingWrapperDOs = integrationDAO.getGrandTotalSumForInterface(sapcollnTO, maxDataCountLimit, officeIds);
					if (!StringUtil.isEmptyColletion(bookingWrapperDOs)) {
						for (BookingInterfaceWrapperDO bookingWarpperDO : bookingWrapperDOs) {
							collectionDO = convertConsignmentCollnHeaderDO(bookingWarpperDO);
							Set<CollectionDtlsDO> collndtls = null;
							collndtls = convertChildEntryDO(bookingWarpperDO, sapcollnTO, collectionDO);
							collectionDO.setCollectionDtls(collndtls);
							sapcollnTO.setSapStatus("C");
							isSaved = integrationDAO.saveConsignmentCollection(collectionDO, sapcollnTO, bookingWarpperDO);
						}
					}
				} catch (Exception e) {
					logger.error(
							"COLLECTIONCR :: Exception occurred in MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollection() :: ",
							e);
				}
			}
		}catch(Exception e){
			logger.error("COLLECTIONCR :: Error IN :: MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollection :: " ,e);
		}
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollection :: End");
		return isSaved;
	}


	public String decreaseDateByOne(String fromDt) throws Exception {
		logger.trace("MiscellaneousSAPIntegrationServiceImpl :: decreaseDateByOne() :: START");
		String toDtStr = CommonConstants.EMPTY_STRING;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			// Number of days to reduce
			c.add(Calendar.DATE, -1);
			// toDtStr is now the new date, 1 day before
			toDtStr = sdf.format(c.getTime());
		} catch (ParseException e) {
			logger.error(
					"Exception occurs in MiscellaneousSAPIntegrationServiceImpl :: decreaseDateByOne() :: ",
					e);
			throw new CGBusinessException(e);
		}
		logger.trace("MiscellaneousSAPIntegrationServiceImpl :: decreaseDateByOne() :: END");
		return toDtStr;
	}
	
	private Set<CollectionDtlsDO> convertChildEntryDO(BookingInterfaceWrapperDO bookingWarpperDO, SAPCollectionTO sapcollnTO,CollectionDO collectionDO) {
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: convertChildEntryDO :: Start");
		
		Set<CollectionDtlsDO> collndtls = new HashSet<>();
		CollectionDtlsDO collDtlsDO = new CollectionDtlsDO();
		if(!StringUtil.isEmptyDouble(bookingWarpperDO.getGrandTotalIncludingTax())){
			collDtlsDO.setRecvAmount(bookingWarpperDO.getGrandTotalIncludingTax());
		}
		logger.debug("COLLECTIONCR :: Received Amt ----->"+collDtlsDO.getRecvAmount());
		
		if(!StringUtil.isEmptyDouble(bookingWarpperDO.getGrandTotalIncludingTax())){
			collDtlsDO.setBillAmount(bookingWarpperDO.getGrandTotalIncludingTax());
		}
		logger.debug("COLLECTIONCR :: Billed Amt ----->"+collDtlsDO.getBillAmount());
		
		collDtlsDO.setCollectionAgainst("W");
		
		collDtlsDO.setCollectionDO(collectionDO);
		collndtls.add(collDtlsDO);
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: convertChildEntryDO :: End");
		
		return collndtls;
	}

	@SuppressWarnings("null")
	private CollectionDO convertConsignmentCollnHeaderDO(BookingInterfaceWrapperDO bookingWarpperDO) throws CGBusinessException, CGSystemException { 
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollectionHeaderDO :: Start");
		
		OfficeDO offcCodeDO = null;
		String offCode = null;
		CollectionDO collnDO = new CollectionDO();
		//SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		
		if(!StringUtil.isEmptyInteger(bookingWarpperDO.getBookingOfficeId())){
			Integer ofcId = bookingWarpperDO.getBookingOfficeId();
			offcCodeDO = organizationCommonDAO.getOfficeByIdOrCode(ofcId, offCode);
		}
		
		/*sequenceGeneratorConfigTO.setProcessRequesting(CommonConstants.GEN_MISC_CONSG_COLL_TXN_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		
		List<String> seqNOs = sequenceGeneratorConfigTO.getGeneratedSequences();
		String txNumber = SAPIntegrationConstants.TX_CODE_CC + offcCodeDO.getOfficeCode() + seqNOs.get(0);
		
		if(!StringUtil.isStringEmpty(txNumber)){
			collnDO.setTxnNo(txNumber);
		}*/
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO= new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setPrefixCode(offcCodeDO.getOfficeCode()+ CommonConstants.TX_CODE_CC);
		sequenceGeneratorConfigTO.setProcessRequesting(CommonConstants.TX_CODE_CC);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO.setRequestingBranchCode(offcCodeDO.getOfficeCode());
		sequenceGeneratorConfigTO.setRequestingBranchId(offcCodeDO.getOfficeId());
		sequenceGeneratorConfigTO.setSequenceRunningLength(CommonConstants.COLLECTION_RUNNING_NUMBER_LENGTH);
		
		 sequenceGeneratorService.getCollectionSequence(sequenceGeneratorConfigTO);
		 List<String> seqNOs =sequenceGeneratorConfigTO.getGeneratedSequences();
		if(CGCollectionUtils.isEmpty(seqNOs)){
			throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
		}
		String txNumber = seqNOs.get(0);
		logger.debug("COLLECTIONCR ::  Tx No -----> "+txNumber);
		collnDO.setTxnNo(txNumber);
		
		if(!StringUtil.isNull(bookingWarpperDO.getBookingDate())){
			collnDO.setCollectionDate(bookingWarpperDO.getBookingDate());
		}
		logger.debug("COLLECTIONCR :: Collection Date -----> "+collnDO.getCollectionDate());
		collnDO.setCollectionCategory(MECUniversalConstants.CN_COLLECTION_TYPE);
		collnDO.setCollectionOfficeDO(offcCodeDO);
		collnDO.setSapTimestamp(new Date());
		collnDO.setCreatedDate(new Date());
		//collnDO.setCollectionDate(new Date());
		logger.debug("COLLECTIONCR :: Ofc ID "+collnDO.getCollectionOfficeDO().getOfficeId());
		
		collnDO.setStatus("V");
		collnDO.setTotalAmount(bookingWarpperDO.getGrandTotalIncludingTax());
		
		logger.debug("COLLECTIONCR :: MiscellaneousSAPIntegrationServiceImpl :: findConsignmentCollectionHeaderDO :: End");
		return collnDO;
		
	}

	@Override
	public void updateCODLCStagingConsignmentStaus() throws Exception {
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingConsignmentStaus :: Start");
		boolean isUpdate = false;
		//if present date is 15 then find config param and update consg status from 1 to 7
		try{
			Date dateTime = Calendar.getInstance().getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");  
			String presentDate = sdf1.format(dateTime);
			String day = presentDate.substring(8, 10);
			String lastDate = getLastDay(new Date());
			String lastDateDay = lastDate.substring(8,10);
			logger.debug("Present Date : "+ sdf1.format(dateTime));
			logger.debug("Last Date: " + lastDate); 
			if(!StringUtil.isStringEmpty(day)
				&& (day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FIRST) || day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_SEC)
				|| day.equals(lastDateDay) || day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FOURTH))){
				
					if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FIRST)){
						
						isUpdate = updateConsignmentStaus(presentDate,day,lastDateDay);
						
					}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_SEC)){
						
						isUpdate = updateConsignmentStaus(presentDate,day,lastDateDay);
						
					}else if(day.equals(lastDateDay)){
						
						isUpdate = updateConsignmentStaus(presentDate,day,lastDateDay);
						
					}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FOURTH)){
						
						isUpdate = updateConsignmentStaus(presentDate,day,lastDateDay);
					}
			}
		}catch(Exception e){
			logger.error("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingConsignmentStaus",e);
			throw new CGSystemException(e);
		}
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateCODLCStagingConsignmentStaus :: End");
	}
	
	private boolean updateConsignmentStaus(String presentDate,String day,String lastDateDay) {
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateConsignmentStaus :: Start");
		boolean isUpdate = false;
		try{
			String bookingStartDate = getBookingStartDate(presentDate,day,lastDateDay);
			String bookingEndDate = getBookingEndDate(presentDate,day,lastDateDay);		
			isUpdate = integrationDAO.updateCODLCStagingConsignmentStaus(bookingStartDate,bookingEndDate);
		}catch(Exception e){
			logger.error("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateConsignmentStaus",e);
		}
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: updateConsignmentStaus :: End");
		return isUpdate;
	}



	private String getBookingStartDate(String presentDate,String day,String lastDateDay) {
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: getBookingStartDate :: Start");
		String bookingStartDate = null;
		try{
			String month = presentDate.substring(5, 7);
			String year = presentDate.substring(0, 4);
			if(!StringUtil.isStringEmpty(day)){
				if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FIRST)){
					bookingStartDate = year+"/"+month+"/"+SAPIntegrationConstants.FIRST_INTERVAL_BOOKIND_START_DATE;
					logger.debug("Booking Start Date : "+bookingStartDate);
				}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_SEC)){
					bookingStartDate = year+"/"+month+"/"+SAPIntegrationConstants.SEC_INTERVAL_BOOKIND_START_DATE;
					logger.debug("Booking Start Date : "+bookingStartDate);
				}else if(day.equals(lastDateDay)){
					bookingStartDate = year+"/"+month+"/"+SAPIntegrationConstants.THIRD_INTERVAL_BOOKIND_START_DATE;
				}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FOURTH)){
					Integer preMonth = Integer.valueOf(month)-1;
					if(!preMonth.equals(0)){
						bookingStartDate = year+"/"+preMonth+"/"+SAPIntegrationConstants.FOURTH_INTERVAL_BOOKIND_START_DATE;
						logger.debug("Booking Start Date : "+bookingStartDate);
					}else{
						bookingStartDate = year+"/"+12+"/"+SAPIntegrationConstants.FOURTH_INTERVAL_BOOKIND_START_DATE;
						logger.debug("Booking Start Date : "+bookingStartDate);
					}
				}
			}
		}catch(Exception e){
			logger.error("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: getBookingStartDate",e);
		}
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: getBookingStartDate :: End");
		return bookingStartDate;
	}

	private String getBookingEndDate(String presentDate,String day,String lastDateDay) {
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: getBookingEndDate :: Start");
		String month = presentDate.substring(5, 7);
		String year = presentDate.substring(0, 4);
		String bookingEndDate = null;
		if(!StringUtil.isStringEmpty(day)){
			if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FIRST)){
				bookingEndDate = year+"/"+month+"/"+SAPIntegrationConstants.FIRST_INTERVAL_BOOKIND_END_DATE;
				logger.debug("Booking End Date : "+bookingEndDate);
			}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_SEC)){
				bookingEndDate = year+"/"+month+"/"+SAPIntegrationConstants.SEC_INTERVAL_BOOKIND_END_DATE;
				logger.debug("Booking End Date : "+bookingEndDate);
			}else if(day.equals(lastDateDay)){
				bookingEndDate = year+"/"+month+"/"+SAPIntegrationConstants.THIRD_INTERVAL_BOOKIND_END_DATE;
				logger.debug("Booking End Date : "+bookingEndDate);
			}else if(day.equals(SAPIntegrationConstants.COD_LC_CONSG_POSTING_INTERVAL_FOURTH)){
				bookingEndDate = getLastDateOfPreviousMonth();
				logger.debug("Booking End Date : "+bookingEndDate);
			}
		}
		logger.debug("CONSG POSTING :: MiscellaneousSAPIntegrationServiceImpl :: getBookingEndDate :: End");
		return bookingEndDate;
	}

	public String getLastDateOfPreviousMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, max);
		Date lastMonthDate = calendar.getTime();
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");  
        return sdf1.format(lastMonthDate);
	}
	
	public String getFirstDay(Date d) throws Exception  
    {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        Date dddd = calendar.getTime();  
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");  
        return sdf1.format(dddd);  
    }  
  
    public String getLastDay(Date d) throws Exception  
    {  
    	logger.debug("MiscellaneousSAPIntegrationServiceImpl :: getLastDay :: Start");
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);  
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        Date dddd = calendar.getTime();  
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd"); 
        logger.debug("MiscellaneousSAPIntegrationServiceImpl :: getLastDay :: END");
        return sdf1.format(dddd);  
    }  
  
    public String getDate(Date d)  
    {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");  
        return sdf.format(d);  
    }  
  
    public int getLastDayOfMonth(Date date)  
    {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }
}
