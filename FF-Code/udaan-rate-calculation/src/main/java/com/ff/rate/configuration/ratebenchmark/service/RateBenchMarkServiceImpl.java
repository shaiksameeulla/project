package com.ff.rate.configuration.ratebenchmark.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkProductDO;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ratebenchmark.constants.RateBenchMarkConstants;
import com.ff.rate.configuration.ratebenchmark.dao.RateBenchMarkDAO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixTO;

public class RateBenchMarkServiceImpl implements RateBenchMarkService {
	private final static Logger LOGGER = LoggerFactory.getLogger(RateBenchMarkServiceImpl.class);
	private RateBenchMarkDAO rateBenchMarkDAO;
	private RateCommonService rateCommonService;

	public RateCommonService getRateCommonService() {
		return rateCommonService;
	}

	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}

	public RateBenchMarkDAO getRateBenchMarkDAO() {
		return rateBenchMarkDAO;
	}

	public void setRateBenchMarkDAO(RateBenchMarkDAO rateBenchMarkDAO) {
		this.rateBenchMarkDAO = rateBenchMarkDAO;
	}

	@Override
	public String saveOrUpdateRateBenchMark(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateBenchMarkServiceImpl::saveOrUpdateRateBenchMark::START------------>:::::::");
		boolean rbmStatus = Boolean.FALSE;
		String status = CommonConstants.FAILURE;

		if (!StringUtil.isNull(rbmhTO)) {
			RateBenchMarkHeaderDO rbhmDO = new RateBenchMarkHeaderDO();
			DomainConverterRateBenchMarkHeaderTO2RateBenchMarkHeaderDO(rbmhTO,
					rbhmDO);
			if (!StringUtil.isNull(rbhmDO)) {
				rbmStatus = rateBenchMarkDAO.saveOrUpdateRateBenchMark(rbhmDO);

				if (!StringUtil.isNull(rbmhTO.getRateCurrentHeaderId())
						&& (rbmhTO.getRateBenchMarkType().equals("R"))
						&& (rbmhTO.getIsApproved().equals("Y"))) {
					try {
						String dateStr = rbmhTO.getRateBenchMarkDateStr();
						SimpleDateFormat sdf = new SimpleDateFormat(
								FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
						Calendar c = Calendar.getInstance();
						c.setTime(sdf.parse(dateStr));
						c.add(Calendar.DATE, -1); // number of days to minus
						dateStr = sdf.format(c.getTime());

						Date date = DateUtil.getDateFromString(dateStr,
								FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
						rateBenchMarkDAO
								.updateRateBenchMarkHeaderEffectiveToDate(
										rbmhTO.getRateCurrentHeaderId(), date);
					} catch (Exception e) {
						LOGGER.error("Exception happened in saveOrUpdateRateBenchMark of RateBenchMarkServiceImpl..."+e.getMessage());
					}
				}

			}
			if (rbmStatus) {
				status = CommonConstants.SUCCESS;
			}
		}
		LOGGER.trace("RateBenchMarkServiceImpl::saveOrUpdateRateBenchMark::END------------>:::::::");
		return status;

	}

	private void DomainConverterRateBenchMarkHeaderTO2RateBenchMarkHeaderDO(
			RateBenchMarkHeaderTO rbmhTO, RateBenchMarkHeaderDO rbmhDO) {
		LOGGER.trace("RateBenchMarkServiceImpl::DomainConverterRateBenchMarkHeaderTO2RateBenchMarkHeaderDO::START------------>:::::::");
		if (!StringUtil.isNull(rbmhTO.getRateBenchMarkHeaderId())
				&& rbmhTO.getRateBenchMarkHeaderId() != 0) {
			rbmhDO.setRateBenchMarkHeaderId(rbmhTO.getRateBenchMarkHeaderId());
		}
		if (!StringUtil.isNull(rbmhTO.getEmpId())) {
			EmployeeDO empDO = new EmployeeDO();
			empDO.setEmployeeId(rbmhTO.getEmpId());
			empDO.setEmpCode(rbmhTO.getEmpCode());
			rbmhDO.setApprover(empDO);
		}

		rbmhDO.setRateBenchMarkEffectiveFrom(DateUtil
				.stringToDDMMYYYYFormat(rbmhTO.getRateBenchMarkDateStr()));
		RateIndustryCategoryDO ricDO = new RateIndustryCategoryDO();
		ricDO.setRateIndustryCategoryId(rbmhTO.getRateIndustryCategoryId());

		rbmhDO.setRateIndustryCategoryDO(ricDO);

		RateCustomerCategoryDO rccDO = new RateCustomerCategoryDO();
		rccDO.setRateCustomerCategoryId(rbmhTO.getRateCustCatId());

		rbmhDO.setRateCustomerCategoryDO(rccDO);
		if (rbmhTO.getIsApproved().equals("Y"))
			rbmhDO.setIsApproved(rbmhTO.getIsApproved());
		else
			rbmhDO.setIsApproved("N");

		RateProductCategoryDO rpcDO = new RateProductCategoryDO();
		rpcDO.setRateProductCategoryId(rbmhTO.getRateProdCatId());

		RateBenchMarkProductDO rbmpDO = new RateBenchMarkProductDO();
		if (!StringUtil.isNull(rbmhTO.getRateBenchMarkProductId())) {
			rbmpDO.setRateBenchMarkProductId(rbmhTO.getRateBenchMarkProductId());
		}

		rbmpDO.setRateProductCategoryDO(rpcDO);

		Set<RateBenchMarkMatrixHeaderDO> rbmmhDOSet = new HashSet<RateBenchMarkMatrixHeaderDO>();

		RateBenchMarkMatrixHeaderDO rbmmhDO = new RateBenchMarkMatrixHeaderDO();

		if (!StringUtil.isNull(rbmhTO.getRateBenchMarkMatrixHeaderId())) {
			rbmmhDO.setRateBenchMarkMatrixHeaderId(rbmhTO
					.getRateBenchMarkMatrixHeaderId());
		}
		if (!StringUtil.isNull(rbmhTO.getRateMinChargWtId())
				&& rbmhTO.getRateMinChargWtId() != 0) {
			rbmmhDO.setRateMinChargeableWeight(rbmhTO.getRateMinChargWtId());
		} else {
			rbmmhDO.setRateMinChargeableWeight(null);
		}
		rbmmhDO.setVobSlab(rbmhTO.getRateVobSlabsId());

		Set<RateBenchMarkMatrixDO> rbmmDOSet = new HashSet<RateBenchMarkMatrixDO>();
		int k = 0;
		String[] sector = rbmhTO.getSecArrStr().split(",");
		String[] wt = rbmhTO.getWtArrStr().split(",");
		int pLen = rbmhTO.getRateProdId().length;
		Integer prodId = rbmhTO.getRateProdCatId();
		for (int l = 0; l < pLen; l++) {
			if (rbmhTO.getRateProdId()[l].equals(prodId))
				break;
			else
				k++;
		}

		Integer originSector = null;
		if (!StringUtil.isNull(rbmhTO.getRateOriginSectorId())
				&& rbmhTO.getRateOriginSectorId() != 0) {
			originSector = rbmhTO.getRateOriginSectorId();
		}
		int secLength = sector.length;
		int wtLength = wt.length;
		for (int i = 0; i < secLength; i++) {
			for (int j = 0; j < wtLength; j++) {
				RateBenchMarkMatrixDO rbmmDO = new RateBenchMarkMatrixDO();
				if ((rbmhTO.getRateBenchMarkMatrixId().length > 0)
						&& !StringUtil
								.isNull(rbmhTO.getRateBenchMarkMatrixId()[k])) {
					rbmmDO.setRateBenchMarkMatrixId(rbmhTO
							.getRateBenchMarkMatrixId()[k]);
				}
				if (!StringUtil.isNull(originSector))
					rbmmDO.setRateOriginSector(originSector);
				rbmmDO.setRateDestinationSector(Integer.parseInt(sector[i]));
				rbmmDO.setWeightSlab(Integer.parseInt(wt[j]));
				rbmmDO.setRate(rbmhTO.getRate()[k]);
				rbmmDO.setRateBenchMarkMatrixHeaderDO(rbmmhDO);
				rbmmDOSet.add(rbmmDO);
				k++;
			}
		}

		rbmmhDO.setRateBenchMarkMatrixDO(rbmmDOSet);
		rbmmhDO.setRateBenchMarkProductDO(rbmpDO);

		rbmmhDOSet.add(rbmmhDO);

		rbmpDO.setRateBenchMarkMatrixHeaderDO(rbmmhDOSet);

		rbmpDO.setRateBenchMarkHeaderDO(rbmhDO);

		Set<RateBenchMarkProductDO> rbmpDOSet = new HashSet<RateBenchMarkProductDO>();
		rbmpDOSet.add(rbmpDO);

		rbmhDO.setRateBenchMarkProductDO(rbmpDOSet);
		LOGGER.trace("RateBenchMarkServiceImpl::DomainConverterRateBenchMarkHeaderTO2RateBenchMarkHeaderDO::END------------>:::::::");
	}

	@Override
	public RateBenchMarkHeaderDO getRateBenchMarkHeaderDetails(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException, CGBusinessException {
		return rateBenchMarkDAO
				.getRateBenchMarkHeader(rbmhTO);
	}

	
	@Override
	public void getRateBenchMarkDetails(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateBenchMarkServiceImpl::getRateBenchMarkDetails::START------------>:::::::");
		List<RateBenchMarkMatrixDO> rbmmDOList = null ;
		RateBenchMarkHeaderDO rbmhDO = null;
		boolean status = Boolean.FALSE;
		rbmmDOList = rateBenchMarkDAO
				.getRateBenchMarkMatrix(rbmhTO);
		
		if((RateBenchMarkConstants.PARAM_RENEW).equals(rbmhTO.getRateBenchMarkType())){
			if(CGCollectionUtils.isEmpty(rbmmDOList) && !StringUtil.isEmptyInteger(rbmhTO.getRateCurrentHeaderId())){
				rbmhDO = rateBenchMarkDAO.getRateBenchMarkByHeaderId(rbmhTO.getRateCurrentHeaderId());
				if(!StringUtil.isNull(rbmhDO)){
					convertRateBenchMarkDO(rbmhDO);
					status = rateBenchMarkDAO.saveOrUpdateRateBenchMark(rbmhDO);
					if(status){
						rbmmDOList = rateBenchMarkDAO
								.getRateBenchMarkMatrix(rbmhTO);
					}
				}
				
			}
		}
		
		
		
		
		if (!CGCollectionUtils.isEmpty(rbmmDOList)) {
			TOConverterRateBenchMarkMatrixDOList2RateBenchMarkHeaderTO(
					rbmmDOList, rbmhTO);
		}
		LOGGER.trace("RateBenchMarkServiceImpl::getRateBenchMarkDetails::END------------>:::::::");
		// return rbmhTO;
	}

	private RateBenchMarkHeaderDO convertRateBenchMarkDO(
			RateBenchMarkHeaderDO rbmhDO) {
		
			Set<RateBenchMarkProductDO> rateBenchMarkProductDOSet = null;
			Set<RateBenchMarkProductDO> rbmpDOSet = null;
			Set<RateBenchMarkMatrixHeaderDO> rateBenchMatrixHeaderDOSet = null;
			Set<RateBenchMarkMatrixHeaderDO> rbmmhDOSet = null;
			Set<RateBenchMarkMatrixDO> rateBenchMatrixDOSet = null;
			Set<RateBenchMarkMatrixDO> rbmmDOSet = null;
			
			rbmhDO.setRateBenchMarkHeaderId(null);
			rbmhDO.setRateBenchMarkEffectiveFrom(DateUtil.getFutureDate(1));
			rbmhDO.setRateBenchMarkEffectiveTo(null);
			rbmhDO.setIsApproved("N");
			
			rateBenchMarkProductDOSet = rbmhDO.getRateBenchMarkProductDO();
			rbmpDOSet = new HashSet<RateBenchMarkProductDO>();
			rbmhDO.setRateBenchMarkProductDO(rbmpDOSet);
			if(!CGCollectionUtils.isEmpty(rateBenchMarkProductDOSet)){
				for(RateBenchMarkProductDO rbmpDO : rateBenchMarkProductDOSet){
					rbmpDO.setRateBenchMarkProductId(null);
					rbmpDO.setRateBenchMarkHeaderDO(rbmhDO);
					rbmpDOSet.add(rbmpDO);
					
					rateBenchMatrixHeaderDOSet = rbmpDO.getRateBenchMarkMatrixHeaderDO();
					rbmmhDOSet = new HashSet<RateBenchMarkMatrixHeaderDO>();
					rbmpDO.setRateBenchMarkMatrixHeaderDO(rbmmhDOSet);
					if(!CGCollectionUtils.isEmpty(rateBenchMatrixHeaderDOSet)){
						for(RateBenchMarkMatrixHeaderDO rbmmhDO : rateBenchMatrixHeaderDOSet){
							rbmmhDO.setRateBenchMarkMatrixHeaderId(null);
							rbmmhDO.setRateBenchMarkProductDO(rbmpDO);
							rbmmhDOSet.add(rbmmhDO);
							
							rateBenchMatrixDOSet = rbmmhDO.getRateBenchMarkMatrixDO();
							rbmmDOSet = new HashSet<RateBenchMarkMatrixDO>();
							rbmmhDO.setRateBenchMarkMatrixDO(rbmmDOSet);
							if(!CGCollectionUtils.isEmpty(rateBenchMatrixDOSet)){
								for(RateBenchMarkMatrixDO rbmmDO : rateBenchMatrixDOSet){
									rbmmDO.setRateBenchMarkMatrixId(null);
									rbmmDO.setRateBenchMarkMatrixHeaderDO(rbmmhDO);
									rbmmDOSet.add(rbmmDO);
								}
							}
						}
					}
				}				
			}
		
		return null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void TOConverterRateBenchMarkMatrixDOList2RateBenchMarkHeaderTO(
			List<RateBenchMarkMatrixDO> rbmmDOList, RateBenchMarkHeaderTO rbmhTO)
			throws CGBusinessException {
		LOGGER.trace("RateBenchMarkServiceImpl::TOConverterRateBenchMarkMatrixDOList2RateBenchMarkHeaderTO::START------------>:::::::");
		List<RateBenchMarkMatrixTO> rbmmTOList = new ArrayList<RateBenchMarkMatrixTO>();

		int aryLen = rbmhTO.getRateProdCatList().size();
		List<RateBenchMarkMatrixTO>[] rbmmTOAry = new ArrayList[aryLen];

		Map<String, List<RateBenchMarkMatrixTO>> rmmhMap = new HashMap<String, List<RateBenchMarkMatrixTO>>();

		Integer[] prodCat = new Integer[aryLen];

		int m = 0;

		if (!CGCollectionUtils.isEmpty(rbmmDOList)) {

			for (RateBenchMarkMatrixDO rbmmDO : rbmmDOList) {
				if (!StringUtil.isNull(rbmmDO)) {
					RateBenchMarkMatrixTO rbmmTO = new RateBenchMarkMatrixTO();
					rbmmTO = (RateBenchMarkMatrixTO) CGObjectConverter
							.createToFromDomain(rbmmDO, rbmmTO);
					if (!StringUtil.isNull(rbmmDO.getRateDestinationSector())) {
						rbmmTO.setRateDestinationSector(rbmmDO
								.getRateDestinationSector());
					}
					if (!StringUtil.isNull(rbmmDO.getRateOriginSector())) {
						rbmmTO.setRateOriginSector(rbmmDO.getRateOriginSector());
					}
					if (!StringUtil.isNull(rbmmDO.getWeightSlab())) {
						rbmmTO.setWeightSlab(rbmmDO.getWeightSlab());
					}
					if (!StringUtil.isNull(rbmmDO
							.getRateBenchMarkMatrixHeaderDO())) {
						
						rbmmTO.setRateBenchMarkMatrixHeaderId(rbmmDO
							.getRateBenchMarkMatrixHeaderDO().getRateBenchMarkMatrixHeaderId());
						if (!StringUtil.isNull(rbmmDO
								.getRateBenchMarkMatrixHeaderDO().getVobSlab())) {
							rbmmTO.setVobId(rbmmDO
									.getRateBenchMarkMatrixHeaderDO()
									.getVobSlab());
						}
						if (!StringUtil.isNull(rbmmDO
								.getRateBenchMarkMatrixHeaderDO()
								.getRateMinChargeableWeight())) {
							rbmmTO.setMinChrgWtId(rbmmDO
									.getRateBenchMarkMatrixHeaderDO()
									.getRateMinChargeableWeight());
						}
						if (!StringUtil.isNull(rbmmDO
								.getRateBenchMarkMatrixHeaderDO()
								.getRateBenchMarkProductDO())) {
							if (!StringUtil.isNull(rbmmDO
									.getRateBenchMarkMatrixHeaderDO()
									.getRateBenchMarkProductDO())) {
								rbmmTO.setProductId(rbmmDO
										.getRateBenchMarkMatrixHeaderDO()
										.getRateBenchMarkProductDO()
										.getRateBenchMarkProductId());

								if (!StringUtil.isNull(rbmmDO
										.getRateBenchMarkMatrixHeaderDO()
										.getRateBenchMarkProductDO()
										.getRateProductCategoryDO())) {
									rbmmTO.setProdCatId(rbmmDO
											.getRateBenchMarkMatrixHeaderDO()
											.getRateBenchMarkProductDO()
											.getRateProductCategoryDO()
											.getRateProductCategoryId());
								}
							}
						}
					}
					Integer pCat = rbmmTO.getProdCatId();
					boolean exist = false;
					for (int j = 0; j < m; j++) {
						if (!StringUtil.isNull(prodCat[j])
								&& prodCat[j] == pCat) {
							rbmmTOAry[j].add(rbmmTO);
							exist = true;
							break;
						}
					}
					if (!exist) {
						prodCat[m] = pCat;
						rbmmTOAry[m] = new ArrayList<RateBenchMarkMatrixTO>();
						rbmmTOAry[m].add(rbmmTO);
						m++;
					}
				}
			}

			int k = 0;
			int l = 0;
			if (!StringUtil.isNull(rbmhTO.getRateMatrixMap())) {
				int prodSize = rbmhTO.getRateProdCatList().size();
				for (int j = 0; j < prodSize; j++) {
					rmmhMap.put(
							rbmhTO.getRateProdCatList().get(j)
									.getRateProductCategoryId().toString(),
							rbmhTO.getRateMatrixMap().get(
									rbmhTO.getRateProdCatList().get(j)
											.getRateProductCategoryId()
											.toString()));
				}
			}
			
			boolean exist = false;
			int cnt = 0;
			int rbmLength = rbmmTOAry.length;
			for (int i = 0; i < rbmLength; i++) {
				if (!StringUtil.isNull(prodCat[i]) && prodCat[i] != 0){
					
					if(!StringUtil.isNull(rmmhMap.get(prodCat[i].toString()))){
						List<RateBenchMarkMatrixTO> toList = (List<RateBenchMarkMatrixTO>) rmmhMap.get(prodCat[i].toString());
						int rbmLn = rbmmTOAry[i].size();
						int toLn = toList.size();
						cnt  = 0;
						for(int p =0 ;p<rbmLn;p++){
							RateBenchMarkMatrixTO mTO = rbmmTOAry[i].get(p);
						for(int n=0;n<toLn;n++){
							if(toList.get(n).getVobId().equals(mTO.getVobId()) 
									&&( StringUtil.isNull(toList.get(n).getRateOriginSector()) || 
											(!StringUtil.isNull(toList.get(n).getRateOriginSector()) 
													&& toList.get(n).getRateOriginSector().equals(mTO.getRateOriginSector())))){	
							if(toList.get(n).getRateBenchMarkMatrixId().equals(rbmmTOAry[i].get(p).getRateBenchMarkMatrixId())){
								toList.remove(n);
								toList.add(n,mTO);
								cnt++;
								exist = true;
								break;
							}
							}
						}
							if(cnt == rbmLn)
							break;
							if(!exist)
								toList.add(mTO);
							}
						}
						
						else{
							rmmhMap.put(prodCat[i].toString(), rbmmTOAry[i]);
						}
						
					}
					
				}
			
			rbmhTO.setRateBenchMarkDateStr(DateUtil
					.getDDMMYYYYDateToString(rbmmDOList.get(0)
							.getRateBenchMarkMatrixHeaderDO()
							.getRateBenchMarkProductDO()
							.getRateBenchMarkHeaderDO()
							.getRateBenchMarkEffectiveFrom()));
			rbmhTO.setRateIndustryCategoryId(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getRateIndustryCategoryDO().getRateIndustryCategoryId());
			rbmhTO.setEmpCode(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getApprover().getEmpCode());
			rbmhTO.setEmpName(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getApprover().getFirstName()
					+ " "
					+ rbmmDOList.get(0).getRateBenchMarkMatrixHeaderDO()
							.getRateBenchMarkProductDO()
							.getRateBenchMarkHeaderDO().getApprover()
							.getLastName());
			rbmhTO.setEmpId(rbmmDOList.get(0).getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getApprover().getEmployeeId());
			rbmhTO.setRateCustCatId(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getRateCustomerCategoryDO().getRateCustomerCategoryId());
			rbmhTO.setRateMatrixMap(rmmhMap);
			rbmhTO.setRateBenchMarkHeaderId(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getRateBenchMarkHeaderId());
			rbmhTO.setIsApproved(rbmmDOList.get(0)
					.getRateBenchMarkMatrixHeaderDO()
					.getRateBenchMarkProductDO().getRateBenchMarkHeaderDO()
					.getIsApproved());

		}
		LOGGER.trace("RateBenchMarkServiceImpl::TOConverterRateBenchMarkMatrixDOList2RateBenchMarkHeaderTO::END------------>:::::::");
	}

	public String updateApproverDetails(Integer empId, Integer headerId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateBenchMarkServiceImpl::updateApproverDetails::START------------>:::::::");
		boolean status = Boolean.FALSE;
		status = rateBenchMarkDAO.updateApproverDetails(empId, headerId);
		LOGGER.trace("RateBenchMarkServiceImpl::updateApproverDetails::END------------>:::::::");
		if (!status)
			return CommonConstants.FAILURE;

		return CommonConstants.SUCCESS;

	}

	@Override
	public List<RateBenchMarkHeaderDO> checkEmpIdCorpApprover(Integer empId)
			throws CGBusinessException, CGSystemException {
		return rateBenchMarkDAO.checkEmpIdCorpApprover(empId);
	}
}
