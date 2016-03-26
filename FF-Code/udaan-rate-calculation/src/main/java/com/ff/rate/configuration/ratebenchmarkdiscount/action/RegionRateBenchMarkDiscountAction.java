package com.ff.rate.configuration.ratebenchmarkdiscount.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.ratebenchmark.constants.RateBenchMarkConstants;
import com.ff.rate.configuration.ratebenchmarkdiscount.constants.RateBenchMarkDiscountConstants;
import com.ff.rate.configuration.ratebenchmarkdiscount.form.RegionRateBenchMarkDiscountForm;
import com.ff.rate.configuration.ratebenchmarkdiscount.service.RegionRateBenchMarkDiscountService;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;

/**
 * @author preegupt
 *
 */
public class RegionRateBenchMarkDiscountAction extends AbstractRateAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RegionRateBenchMarkDiscountAction.class);

	private RegionRateBenchMarkDiscountService regionRateBenchMarkDiscountService;

	/**@Desc : For Preparing the page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 */
	public ActionForward viewBenchMarkDiscount(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CGBusinessException {
		LOGGER.trace("RegionRateBenchMarkDiscountAction::viewBenchMarkDiscount::START------------>:::::::");

		RegionRateBenchMarkDiscountTO regionRateBenchMarkDiscountTO = new RegionRateBenchMarkDiscountTO();
		try {
			getDefultUIValues(request, regionRateBenchMarkDiscountTO);
			((RegionRateBenchMarkDiscountForm) form)
					.setTo(regionRateBenchMarkDiscountTO);

		} catch (Exception e) {
			LOGGER.error("Exception happened in viewBenchMarkDiscount of RegionRateBenchMarkDiscountAction..."
					+ e.getMessage());
		}
		LOGGER.trace("RegionRateBenchMarkDiscountAction::viewBenchMarkDiscount::END------------>:::::::");

		return mapping.findForward(RateBenchMarkDiscountConstants.success);
	}

	/**@Desc : For getting all the default values on laod of page
	 * @param request
	 * @param regionRateBenchMarkDiscountTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getDefultUIValues(HttpServletRequest request,
			RegionRateBenchMarkDiscountTO regionRateBenchMarkDiscountTO)
					throws CGBusinessException, CGSystemException{
		
		List<LabelValueBean> indCatList = null;
		HttpSession session=request.getSession(false);	
		List<RateIndustryCategoryTO> industryCategoryToList = null;
		try{
			LOGGER.trace("RegionRateBenchMarkDiscountAction::getDefultUIValues::START------------>:::::::");
		regionRateBenchMarkDiscountService = getRateBenchMarkDiscountService();
		
		industryCategoryToList = getRateIndustryCategoryList(request,RateCommonConstants.RATE_BENCH_MARK);
		if(!CGCollectionUtils.isEmpty(industryCategoryToList)){
	
			indCatList = new ArrayList<LabelValueBean>();
			for(RateIndustryCategoryTO rateIndustryCategoryTO: industryCategoryToList){
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(rateIndustryCategoryTO.getRateIndustryCategoryName());
				lvb.setValue(rateIndustryCategoryTO.getRateIndustryCategoryId().toString());
				indCatList.add(lvb);
			}
				
			session.setAttribute(RateBenchMarkConstants.RATE_INDUSTRY_CAT_ID, indCatList);
		}
		
		
		if(!CGCollectionUtils.isEmpty(indCatList)){
			request.setAttribute(RateBenchMarkConstants.RATE_INDUSTRY_CAT_ID, indCatList);
			regionRateBenchMarkDiscountTO.setRateIndCatList(indCatList);
		}else{
			LOGGER.error("Exception happened in getDefultUIValues of RateBenchMarkAction...");
			throw new CGBusinessException();
		}
		
		List<RegionTO> regionTOs = regionRateBenchMarkDiscountService.getAllRegions();
		request.setAttribute("regionTOs", regionTOs);
		LOGGER.trace("RegionRateBenchMarkDiscountAction::getDefultUIValues::END------------>:::::::");
	}catch(Exception e){
		LOGGER.error("Exception happened in getDefultUIValues of RegionRateBenchMarkDiscountAction...",e);
	}
		
	}	
	
	
	
	
	
	/**@Desc : For getting the  details of the discount
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getDiscountDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String jsonResult = "";
		try {
			LOGGER.trace("RegionRateBenchMarkDiscountAction::getDiscountDetails::START------------>:::::::");
			RegionRateBenchMarkDiscountTO rbmdTO = new RegionRateBenchMarkDiscountTO();
			out = response.getWriter();
			Integer industryCategoryId =Integer.parseInt(request.getParameter("industryCategoryId"));
			rbmdTO.setIndustryCategory(industryCategoryId);
				
			regionRateBenchMarkDiscountService = getRateBenchMarkDiscountService();
			List<RegionRateBenchMarkDiscountTO> rbmdTOs= regionRateBenchMarkDiscountService
						.getDiscountDetails(rbmdTO);
			if (!StringUtil.isEmptyList(rbmdTOs)){
			jsonResult = JSONSerializer.toJSON(rbmdTOs).toString();
			}
			}
		 catch (Exception e) {
			LOGGER.error("Error occured in RegionRateBenchMarkDiscountAction :: getDiscountDetails() ::"
					+ e.getMessage());
		} finally {
		
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RegionRateBenchMarkDiscountAction::getDiscountDetails::END------------>:::::::");
	}
	
	
	/**@Desc : For saving and submitting the discount details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void saveOrUpdateDiscount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RegionRateBenchMarkDiscountAction::saveOrUpdateDiscount::START------------>:::::::");
		RegionRateBenchMarkDiscountForm regionRateBenchMarkDiscountForm = (RegionRateBenchMarkDiscountForm) form;
		RegionRateBenchMarkDiscountTO rbmdTO = (RegionRateBenchMarkDiscountTO) regionRateBenchMarkDiscountForm
				.getTo();
		regionRateBenchMarkDiscountService = getRateBenchMarkDiscountService();
		regionRateBenchMarkDiscountService
				.saveOrUpdateRateBenchMarkDiscount(rbmdTO);
		LOGGER.trace("RegionRateBenchMarkDiscountAction::saveOrUpdateDiscount::END------------>:::::::");
	}

	public RegionRateBenchMarkDiscountService getRateBenchMarkDiscountService() {
		if (StringUtil.isNull(regionRateBenchMarkDiscountService)) {
			regionRateBenchMarkDiscountService = (RegionRateBenchMarkDiscountService) getBean(RateBenchMarkDiscountConstants.REGION_RATE_BENCHMARK_DISCOUNT);
		}
		return regionRateBenchMarkDiscountService;
	}
}
