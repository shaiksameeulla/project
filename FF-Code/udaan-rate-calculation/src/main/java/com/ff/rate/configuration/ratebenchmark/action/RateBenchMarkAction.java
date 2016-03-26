package com.ff.rate.configuration.ratebenchmark.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.ratebenchmark.constants.RateBenchMarkConstants;
import com.ff.rate.configuration.ratebenchmark.form.RateBenchMarkForm;
import com.ff.rate.configuration.ratebenchmark.service.RateBenchMarkService;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixTO;

/**
 * @author rmaladi
 *
 */
public class RateBenchMarkAction  extends AbstractRateAction {
	private RateBenchMarkService rateBenchMarkService;

	private final static Logger LOGGER = LoggerFactory.getLogger(RateBenchMarkAction.class);

	/** 
     * View Form Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */
	
	public ActionForward viewRateBenchMark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RateBenchMarkAction::viewRateBenchMark::START------------>:::::::");
		RateBenchMarkHeaderTO rateBenchMarkHeaderTO = new RateBenchMarkHeaderTO();
		getDefultUIValues(request, rateBenchMarkHeaderTO);
		((RateBenchMarkForm) form).setTo(rateBenchMarkHeaderTO);
			
		LOGGER.trace("RateBenchMarkAction::viewRateBenchMark::END------------>:::::::");	
	
		return mapping.findForward(RateBenchMarkConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * View BenchMark Form Renew  Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */
	
	public ActionForward viewReNewRateBenchMark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RateBenchMarkAction::viewReNewRateBenchMark::START------------>:::::::");
		
		RateBenchMarkForm rateBenchMarkForm = (RateBenchMarkForm) form;
		RateBenchMarkHeaderTO rateBenchMarkHeaderTO = (RateBenchMarkHeaderTO) rateBenchMarkForm.getTo();
		
			Integer indCatId = rateBenchMarkHeaderTO.getRateIndustryCategoryId();
			String type = rateBenchMarkHeaderTO.getRateBenchMarkType();
			Integer headerId = rateBenchMarkHeaderTO.getRateCurrentHeaderId();
			rateBenchMarkHeaderTO = new RateBenchMarkHeaderTO();
			getDefultUIValues(request, rateBenchMarkHeaderTO);
			rateBenchMarkHeaderTO.setRateIndustryCategoryId(indCatId);
			rateBenchMarkHeaderTO.setRateCurrentHeaderId(headerId);
			rateBenchMarkHeaderTO.setRateBenchMarkType(type);
			((RateBenchMarkForm) form).setTo(rateBenchMarkHeaderTO);
			
	
		return mapping.findForward(RateBenchMarkConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * View BenchMark Form with Industry Category Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */
	
	public ActionForward viewRateBenchMarkByIndustry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RateBenchMarkAction::viewRateBenchMarkByIndustry::START------------>:::::::");
		
		RateBenchMarkForm rateBenchMarkForm = (RateBenchMarkForm) form;
		RateBenchMarkHeaderTO rateBenchMarkHeaderTO = (RateBenchMarkHeaderTO) rateBenchMarkForm.getTo();
			Integer indCatId = rateBenchMarkHeaderTO.getRateIndustryCategoryId();
			rateBenchMarkHeaderTO = new RateBenchMarkHeaderTO();
			getDefultUIValues(request, rateBenchMarkHeaderTO);
			rateBenchMarkHeaderTO.setRateIndustryCategoryId(indCatId);
			((RateBenchMarkForm) form).setTo(rateBenchMarkHeaderTO);
			
		LOGGER.trace("RateBenchMarkAction::viewRateBenchMarkByIndustry::END------------>:::::::");	
	
		return mapping.findForward(RateBenchMarkConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Load the default values into TO
	 * @param request
	 * @param rateBenchMarkHeaderTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	
	private void getDefultUIValues(HttpServletRequest request, RateBenchMarkHeaderTO rateBenchMarkHeaderTO){
		List<LabelValueBean> indCatList = null;
		List<LabelValueBean> prodCatList = null;
		List<RateCustomerCategoryTO> custCatList = null;
		//List<RateCustomerProductCatMapTO> custProdList = null;
		List<RateMinChargeableWeightTO> minChrgWtList = null;
		List<RateVobSlabsTO> vobSlabsList = null;
		List<RateWeightSlabsTO> wtSlabList = null;
		List<RateSectorsTO> sectorsList = null;
		List<RateIndustryCategoryTO> industryCategoryToList = null;
		//List<RateBenchMarkMatrixTO> matrixList = null;
		ActionMessage actionMessage = null;	
		try{
			LOGGER.trace("RateBenchMarkAction::getDefultUIValues::START------------>:::::::");
			industryCategoryToList = getRateIndustryCategoryList(request,RateCommonConstants.RATE_BENCH_MARK);
			if(!CGCollectionUtils.isEmpty(industryCategoryToList)){
				
				indCatList = new ArrayList<LabelValueBean>();
				for(RateIndustryCategoryTO rateIndustryCategoryTO: industryCategoryToList){
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(rateIndustryCategoryTO.getRateIndustryCategoryName());
					lvb.setValue(rateIndustryCategoryTO.getRateIndustryCategoryId().toString());
					indCatList.add(lvb);
					if(rateIndustryCategoryTO.getRateIndustryCategoryCode().equals(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_CODE))
						rateBenchMarkHeaderTO.setRateIndustryCategoryId(rateIndustryCategoryTO.getRateIndustryCategoryId());
				}
				
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_IND_CAT_LIST, indCatList);
				rateBenchMarkHeaderTO.setRateIndCatList(indCatList);
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.RATE_INDUSTRY_CATEGORY_DTLS_NOT_EXIST);
			}
			
			
			
			List<RateProductCategoryTO> prodCatToList = getRateProductCategoryList(request,RateCommonConstants.RATE_BENCH_MARK, RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N);
			if(!CGCollectionUtils.isEmpty(prodCatToList)){
				prodCatList = new ArrayList<LabelValueBean>();
				for(RateProductCategoryTO rateProductCategoryTO: prodCatToList){
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(rateProductCategoryTO.getRateProductCategoryName());
					lvb.setValue(rateProductCategoryTO.getRateProductCategoryId().toString());
					prodCatList.add(lvb);
				}
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_LIST, prodCatList);
				rateBenchMarkHeaderTO.setRateProdCatId(Integer.parseInt(prodCatList.get(0).getValue()));	
				
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.PRODUCT_CATEGORY_DTLS_NOT_EXIST);
			}
			
			custCatList = getRateCustomerCategoryList(request);
			if(!CGCollectionUtils.isEmpty(custCatList)){
				request.setAttribute(RateCommonConstants.RATE_CUSTOMER_CATEGORY_LIST, custCatList);
				for(RateCustomerCategoryTO custCat : custCatList){
				if(custCat.getRateCustomerCategoryCode().equals(RateCommonConstants.RATE_BENCH_MARK_CUST_CAT_CODE))	
				rateBenchMarkHeaderTO.setRateCustCatId(custCat.getRateCustomerCategoryId());
				}
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.CUSTOMER_CATEGORY_DTLS_NOT_EXIST);
			}
		
			minChrgWtList = getRateMinChargeWtList(request, RateCommonConstants.RATE_BENCH_MARK, RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N, RateCommonConstants.RATE_CUST_CRDT);
			if(!CGCollectionUtils.isEmpty(minChrgWtList)){
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_MIN_CHAG_WT_LIST, minChrgWtList);
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.MINIMUM_CHARGE_WEIGHT_DTLS_NOT_EXIST);
			}
			
			vobSlabsList = getRateVobSlabsList(request, RateCommonConstants.RATE_BENCH_MARK, RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N, RateCommonConstants.RATE_CUST_CRDT);
			if(!CGCollectionUtils.isEmpty(vobSlabsList)){
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_VOB_SLAB_LIST, vobSlabsList);
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.VOB_DTLS_NOT_EXIST);
			}
			
			wtSlabList = getRateWeightSlabsList(request, RateCommonConstants.RATE_BENCH_MARK, RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N, RateCommonConstants.RATE_CUST_CRDT);
			if(!CGCollectionUtils.isEmpty(wtSlabList)){
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_WT_SLAB_LIST, wtSlabList);
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.WEIGHT_SLAB_DTLS_NOT_EXIST);
			}
			
			
			
			sectorsList = getRateSectorsList(request, RateCommonConstants.RATE_BENCH_MARK, RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N, RateCommonConstants.RATE_CUST_CRDT);
			if(!CGCollectionUtils.isEmpty(sectorsList)){
				request.setAttribute(RateCommonConstants.RATE_BENCH_MARK_SECTOR_LIST, sectorsList);
			}else{
				LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
				actionMessage = new ActionMessage(
						RateErrorConstants.SECTOR_DTLS_NOT_EXIST);
			}
			
			//request.setAttribute(RateBenchMarkConstants.RATE_BENCH_MARK_TO, rateBenchMarkHeaderTO);
			
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefaultUIValues of RateBenchMarkAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefaultUIValues of RateBenchMarkAction..."+e.getMessage());
			getBusinessError(request, e);
		}catch(Exception e){
			LOGGER.error("Exception happened in getCodChagreValue of RateBenchMarkAction..."+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("RateBenchMarkAction::getDefultUIValues::END------------>:::::::");
	}
	

	/**  
	 * Save RateBenchMark details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRateBenchMark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException, CGBusinessException{
		String status = null;
		PrintWriter out = null;
		String data = "";
		try{
			LOGGER.trace("RateBenchMarkAction::saveOrUpdateRateBenchMark::START------------>:::::::");
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
		RateBenchMarkForm rateBenchMarkForm = (RateBenchMarkForm) form;
		RateBenchMarkHeaderTO rbmhTO = (RateBenchMarkHeaderTO) rateBenchMarkForm.getTo();
		rateBenchMarkService = getRateBenchMarkService();
		status = rateBenchMarkService.saveOrUpdateRateBenchMark(rbmhTO);
		/*if(status.equals(CommonConstants.SUCCESS)){
		rbmhTO = loadAllGridValues(request,rbmhTO);
		data = serializer.toJSON(rbmhTO).toString();
		}else{
			data = status;
		}*/
		
		if (status.equals(CommonConstants.SUCCESS)) {
			rbmhTO = loadAllGridValues(request,rbmhTO);
			if(rbmhTO.getIsApproved().equals("Y")){
				rbmhTO.setTransMsg(getMessageFromErrorBundle(
					request,
					RateErrorConstants.RATES_SUBMITTED_SUCCESSFULLY, null));
			}else{
				rbmhTO.setTransMsg(getMessageFromErrorBundle(
						request,
						RateErrorConstants.RATES_SAVED_SUCCESSFULLY, null));
			}
			data = serializer.toJSON(rbmhTO).toString();			
		} else {
			if(rbmhTO.getIsApproved().equals("Y")){
				data = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getMessageFromErrorBundle(request,
							RateErrorConstants.RATES_NOT_SUBMITTED_SUCCESSFULLY, null));
			}else{
				data = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.RATES_NOT_SAVED_SUCCESSFULLY, null));
			}
		}
		}catch (CGBusinessException e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
				out.print(data);
				out.flush();
				out.close();
			}
		LOGGER.trace("RateBenchMarkAction::saveOrUpdateRateBenchMark::END------------>:::::::");
	}
	
	/**
	 * Load the RateBenchMArk details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@SuppressWarnings({ "static-access", "unchecked"})
	public void getValues(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		PrintWriter out = null;
		String data = "";
		HttpSession session=request.getSession(false);
		List<RateProductCategoryTO> prodCatList = null;
		try{
			LOGGER.trace("RateBenchMarkAction::getValues::START------------>:::::::");
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		RateBenchMarkHeaderTO rateBenchMarkHeaderTO = new RateBenchMarkHeaderTO();
		
		prodCatList = (List<RateProductCategoryTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_LIST);
		if(!StringUtil.isNull(rateBenchMarkHeaderTO)){
		for(RateProductCategoryTO rpcTO : prodCatList){
			List<RateBenchMarkMatrixTO> matrixCList = (List<RateBenchMarkMatrixTO>)session.getAttribute(rpcTO.getRateProductCategoryId().toString());
		
			if(!CGCollectionUtils.isEmpty(matrixCList))
			session.removeAttribute(rpcTO.getRateProductCategoryId().toString());
		}
		}
		setUpValues(request,rateBenchMarkHeaderTO);
		if(!StringUtil.isNull(rateBenchMarkHeaderTO.getRateMatrixMap())){
			for(RateProductCategoryTO rpcTO : prodCatList){
			session.setAttribute(rpcTO.getRateProductCategoryId().toString(),rateBenchMarkHeaderTO.getRateMatrixMap().get(rpcTO.getRateProductCategoryId().toString()));
		}
		}
		//if(!CGCollectionUtils.isEmpty(rateBenchMarkHeaderTO.getRateMatrixMap())){
				data = serializer.toJSON(rateBenchMarkHeaderTO).toString();
		
		}catch (CGBusinessException e) {
			LOGGER.error("RateBenchMarkAction::getValues()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateBenchMarkAction::getValues()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateBenchMarkAction::getValues()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateBenchMarkAction::getValues::END------------>:::::::");
	}
	
	/**
	 * get RateBenchMarkService Object
	 * @return
	 */
	public RateBenchMarkService getRateBenchMarkService()
	{
		if(StringUtil.isNull(rateBenchMarkService)) {
			rateBenchMarkService = (RateBenchMarkService)getBean(RateSpringConstants.RATE_BENCH_MARK_SERVICE);
		}
		return rateBenchMarkService;
	}
	
	
	/**
	 * Get the Grid values of RateBenchMark
	 * @param request
	 * @param rbmhTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@SuppressWarnings("unchecked")
	public RateBenchMarkHeaderTO loadAllGridValues(HttpServletRequest request, RateBenchMarkHeaderTO rbmhTO)
			throws CGBusinessException, CGSystemException{
		
		LOGGER.trace("RateBenchMarkAction::loadAllGridValues::START------------>:::::::");
		HttpSession session=request.getSession(false);
		List<RateProductCategoryTO> prodCatList = null;
		RateBenchMarkHeaderTO rbmmTO = new RateBenchMarkHeaderTO();

		rbmmTO.setRateBenchMarkHeaderId(rbmhTO.getRateBenchMarkHeaderId());	
		rbmmTO.setRateIndustryCategoryId(rbmhTO.getRateIndustryCategoryId());
		rbmmTO.setRateCustCatId(rbmhTO.getRateCustCatId());
		rbmmTO.setRateProdCatId(rbmhTO.getRateProdCatId());
		rbmmTO.setRateVobSlabsId(rbmhTO.getRateVobSlabsId());
		if(rbmhTO.getRateOriginSectorId() != 0)
		rbmmTO.setRateOriginSectorId(rbmhTO.getRateOriginSectorId());
		if(!StringUtil.isNull(rbmhTO.getRateBenchMarkType()))
			rbmmTO.setRateBenchMarkType(rbmhTO.getRateBenchMarkType());

		
		prodCatList = (List<RateProductCategoryTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_LIST);
		
		Map<String, List<RateBenchMarkMatrixTO>> rmmMap = new HashMap<String, List<RateBenchMarkMatrixTO>>();
		for(RateProductCategoryTO rpcTO : prodCatList){
			
			List<RateBenchMarkMatrixTO> matrixCList = (List<RateBenchMarkMatrixTO>) session.getAttribute(rpcTO.getRateProductCategoryId().toString());
		
			rmmMap.put(rpcTO.getRateProductCategoryId().toString(), matrixCList);
		}
		if(!CGCollectionUtils.isEmpty(rmmMap)){
			rbmmTO.setRateMatrixMap(rmmMap);
		}
		
		setUpValues(request,rbmmTO);
		
		for(RateProductCategoryTO rpcTO : prodCatList){
			List<RateBenchMarkMatrixTO> matrixCList = (List<RateBenchMarkMatrixTO>)session.getAttribute(rpcTO.getRateProductCategoryId().toString());
		
			if(!CGCollectionUtils.isEmpty(matrixCList))
			session.removeAttribute(rpcTO.getRateProductCategoryId().toString());
		/*}
		for(RateProductCategoryTO rpcTO : prodCatList){*/
			session.setAttribute(rpcTO.getRateProductCategoryId().toString(),rbmmTO.getRateMatrixMap().get(rpcTO.getRateProductCategoryId().toString()));
		}
		
		//data = serializer.toJSON(rbmmTO).toString();
	
		
		LOGGER.trace("RateBenchMarkAction::loadAllGridValues::END------------>:::::::");
		return rbmmTO;
	}
	
	/**
	 * Setup Grid values of RateBenchMark
	 * @param request
	 * @param rateBenchMarkHeaderTO
	 */
	@SuppressWarnings({ "unchecked" })
	public void setUpValues(HttpServletRequest request,RateBenchMarkHeaderTO rateBenchMarkHeaderTO)
		throws CGBusinessException, CGSystemException{
		
		HttpSession session=request.getSession(false);
		String type = null;
		String industryCat = null;
		String productCat = null;
		String customerCat = null;
		String vobSlab = null;
		String originSecSlab = null;
		String rateType = null;
		String oldHeaderId = null;
		
		LOGGER.trace("RateBenchMarkAction::setUpValues::START------------>:::::::");
		rateBenchMarkService = getRateBenchMarkService();
		
		industryCat = request.getParameter(RateBenchMarkConstants.PARAM_IND_CAT_ID);
		productCat = request.getParameter(RateBenchMarkConstants.PARAM_PROD_CAT_ID);
		customerCat = request.getParameter(RateBenchMarkConstants.PARAM_CUST_CAT_ID);
		vobSlab = request.getParameter(RateBenchMarkConstants.PARAM_VOB_SLAB_ID);
		originSecSlab = request.getParameter(RateBenchMarkConstants.PARAM_ORIGIN_SEC_ID);
		rateType = request.getParameter(RateBenchMarkConstants.PARAM_TYPE);
		oldHeaderId = request.getParameter("oldHeaderId");
		if(!StringUtil.isStringEmpty(industryCat)){
			rateBenchMarkHeaderTO.setRateIndustryCategoryId(Integer.parseInt(industryCat));
		}
		if(!StringUtil.isStringEmpty(productCat)){
			rateBenchMarkHeaderTO.setRateProdCatId(Integer.parseInt(productCat));	
		}
		if(!StringUtil.isStringEmpty(customerCat)){
			rateBenchMarkHeaderTO.setRateCustCatId(Integer.parseInt(customerCat));	
		}
		if(!StringUtil.isStringEmpty(vobSlab)){
			rateBenchMarkHeaderTO.setRateVobSlabsId(Integer.parseInt(vobSlab));
		}
		if(!StringUtil.isStringEmpty(originSecSlab)){
			rateBenchMarkHeaderTO.setRateOriginSectorId(Integer.parseInt(originSecSlab));
		}
		if(!StringUtil.isStringEmpty(oldHeaderId)){
			rateBenchMarkHeaderTO.setRateCurrentHeaderId(Integer.parseInt(oldHeaderId));
		}
		
		if(!StringUtil.isStringEmpty(rateType)){
			type = rateType;
			rateBenchMarkHeaderTO.setRateBenchMarkType(type);
		}
		if(!StringUtil.isStringEmpty(rateBenchMarkHeaderTO.getRateBenchMarkType())){
			type = rateBenchMarkHeaderTO.getRateBenchMarkType();
		}
		if(!StringUtil.isStringEmpty(type) && type.equals(RateBenchMarkConstants.PARAM_RENEW))
		{
			try{
			String newDate = DateUtil.getCurrentDateInDDMMYYYY();
			SimpleDateFormat sdf = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(newDate));
			c.add(Calendar.DATE, 1);  // number of days to add
			newDate = sdf.format(c.getTime()); 
			rateBenchMarkHeaderTO.setRateBenchMarkDateStr(newDate);
			}catch(Exception e){
				ExceptionUtil.prepareBusinessException(RateErrorConstants.DATE_PARSE_ERROR);
			}
		}
		
		else{
		rateBenchMarkHeaderTO.setRateBenchMarkDateStr(DateUtil.getCurrentDateInDDMMYYYY());
		}
		
		List<RateProductCategoryTO> prodCatList = null;
		
		prodCatList = (List<RateProductCategoryTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_LIST);
		
		rateBenchMarkHeaderTO.setRateProdCatList(prodCatList);
		
		rateBenchMarkService.getRateBenchMarkDetails(rateBenchMarkHeaderTO);
		
		
		List<LabelValueBean> indCatList = null;
		
		List<RateVobSlabsTO> vobSlabsList = null;
		List<RateWeightSlabsTO> wtSlabList = null;
		List<RateSectorsTO> sectorsList = null;
		List<RateMinChargeableWeightTO> minChrgWtList = null;
		
		indCatList = (List<LabelValueBean>)session.getAttribute(RateCommonConstants.RATE_INDUSTRY_CATEGORY_LIST);
		vobSlabsList = (List<RateVobSlabsTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_VOB_SLAB_LIST);
		wtSlabList = (List<RateWeightSlabsTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_WT_SLAB_LIST);
		sectorsList = (List<RateSectorsTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_SECTOR_LIST);
		//custProdList = (List<RateCustomerProductCatMapTO>)session.getAttribute(RateBenchMarkConstants.RATE_CUST_PROD_CAT_MAP_LIST);
		minChrgWtList = (List<RateMinChargeableWeightTO>)session.getAttribute(RateCommonConstants.RATE_BENCH_MARK_MIN_CHAG_WT_LIST);
		rateBenchMarkHeaderTO.setRateIndCatList(indCatList);
		rateBenchMarkHeaderTO.setRateProdCatList(prodCatList);
		rateBenchMarkHeaderTO.setRateVobSlabsList(vobSlabsList);
		rateBenchMarkHeaderTO.setRateWtSlabsList(wtSlabList);
		rateBenchMarkHeaderTO.setRateSectorsList(sectorsList);
		//rateBenchMarkHeaderTO.setRateCustProdCatMapList(custProdList);
		rateBenchMarkHeaderTO.setRateMinChargWtList(minChrgWtList);
		LOGGER.trace("RateBenchMarkAction::setUpValues::END------------>:::::::");
		
	}
	
	
	
	/**
	 * Update the Approver details of RateBenchMark
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void updateApproverDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.trace("RateBenchMarkAction::updateApproverDetails::START------------>:::::::");
		Integer empId = null;
		Integer headerId = null;
		PrintWriter out = null;
		String data = "";
		String status = CommonConstants.FAILURE;
		try{
		serializer = CGJasonConverter.getJsonObject();
		out = response.getWriter();
		empId = Integer.parseInt(request.getParameter(RateBenchMarkConstants.PARAM_EMP_ID));
		headerId = Integer.parseInt(request.getParameter(RateBenchMarkConstants.PARAM_HEADER_ID));
		rateBenchMarkService = getRateBenchMarkService();		
		status = rateBenchMarkService.updateApproverDetails(empId,headerId);
		if (status.equals(CommonConstants.SUCCESS)) {
			
			data = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,getMessageFromErrorBundle(request,RateErrorConstants.APPROVER_SAVED_SUCCESSFULLY,null));
						
		} else {
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,getMessageFromErrorBundle(request,RateErrorConstants.APPROVER_NOT_SAVED_SUCCESSFULLY,null));
		}
		}catch (CGBusinessException e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateBenchMarkAction::saveOrUpdateRateBenchMark()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateBenchMarkAction::updateApproverDetails::END------------>:::::::");
	}
	
}



