package com.ff.admin.billing.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.service.BillPrintingService;
import com.ff.admin.billing.service.BillingCommonService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.FinancialProductTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class BillPrintingAction.
 *
 * @author narmdr
 */
public class BillPrintingAction extends AbstractBillingAction  {
	
	/** The billing common service. */
	private BillingCommonService billingCommonService;
	
	/** The bill printing service. */
	private BillPrintingService billPrintingService;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BillPrintingAction.class);
	
	/**
	 * Prepare page.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("BillPrintingAction::preparePage::START----->");
		ActionMessage actionMessage = null;
		try	{
			billingCommonService = (BillingCommonService)getBean(AdminSpringConstants.BILLING_COMMON_SERVICE);
			billPrintingService=(BillPrintingService)getBean(AdminSpringConstants.BILLING_PRINT_SERVICE);
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			request.setAttribute("reportUrl", composeReportUrl(userInfoTO.getConfigurableParams()));

			if(loggedInOfficeTO != null){
				if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE) || 
									 loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
											.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE) || loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
											.equals(CommonConstants.OFF_TYPE_HUB_OFFICE) ){
			List<RegionTO> regionTo = billingCommonService.getRegions();
		
			if (loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO() != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				Integer regionId=loggedInOfficeTO.getRegionTO().getRegionId();
				request.setAttribute("branchUserRegion", regionId);
				request.setAttribute("OFFICE_TYPE", loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode());
				
				Integer cityId=loggedInOfficeTO.getCityId();
				CityTO cityTo=billPrintingService.getStation(cityId);
				request.setAttribute("cityTo", cityTo);
				List<OfficeTO> branchList=billingCommonService.getOfficesByCityId(cityId);
				request.setAttribute("branchList", branchList);
				List<Integer> branches = new ArrayList<Integer>();
				branches.add(loggedInOfficeTO.getOfficeId());
				//List<CustomerTO> customerList=billPrintingService.getCustsByBillingBranchAndFinancialProduct(branches);
				//request.setAttribute("customerList", customerList);
			}else if(loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO() != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				Integer regionId=loggedInOfficeTO.getRegionTO().getRegionId();
				request.setAttribute("rhoRegion", regionId);
				request.setAttribute("OFFICE_TYPE", loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode());
				List<CityTO> stationList=billingCommonService.getCitiesByRegionId(regionId);
				request.setAttribute("cityTo", stationList);
				request.setAttribute("OFFICE_TYPE", loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode());
			}
			else if(loggedInOfficeTO != null
					&& loggedInOfficeTO.getOfficeTypeTO() != null
					&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
							.equals(CommonConstants.OFF_TYPE_HUB_OFFICE)){
				
				Integer regionId=loggedInOfficeTO.getRegionTO().getRegionId();
				request.setAttribute("HubUserRegion", regionId);
				request.setAttribute("OFFICE_TYPE", loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode());
				
				Integer cityId=loggedInOfficeTO.getCityId();
				CityTO cityTo=billPrintingService.getStation(cityId);
				request.setAttribute("cityTo", cityTo);
				List<OfficeTO> branchList=billPrintingService.getBranchesUnderReportingHub(loggedInOfficeTO.getOfficeId());
				OfficeTO hubOffTO=new OfficeTO();
				hubOffTO.setOfficeId(loggedInOfficeTO.getOfficeId());
				hubOffTO.setOfficeName(loggedInOfficeTO.getOfficeName());
				/**
				 * Change START
				 * Changed By - kgajare
				 * Changed Date - 19 May 2014
				 * Changed Reason - An issue was reported for HUB login in BIll Printing where 
				 * user of Standalone HUB cannot print bills for its own customers. The reason was below business exception.
				 * As confirmed with BA Team we have to remove the business exception even if HUB did not have any branches 
				 * under it and let the user use Bill Printing with only HUB itself in Branch List.
				 */
				if( branchList == null ){
					branchList = new ArrayList<OfficeTO>();
				}
				/**
				 * Change END
				 */
				branchList.add(hubOffTO);
				request.setAttribute("branchList", branchList);
				
			}
			List<FinancialProductTO> productTo= billingCommonService.getProducts();//get all products
			//List<StockStandardTypeTO> stationeryTypes=billPrintingService.getStationaryTypes(BillingConstants.STATIONARY_TYPE);//get stationery
			request.setAttribute("loggedInOfficeTO", loggedInOfficeTO);
			request.setAttribute(BillingConstants.REGION_TO, regionTo);
			request.setAttribute(BillingConstants.PRODUCT_TO, productTo);
			request.setAttribute("BR_OFFICE", CommonConstants.OFF_TYPE_BRANCH_OFFICE);
			request.setAttribute("RHO_OFFICE", CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);
			request.setAttribute("HUB_OFFICE", CommonConstants.OFF_TYPE_HUB_OFFICE);
			
			//request.setAttribute(BillingConstants.STATIONERY_TYPES, stationeryTypes);
			}
			 else{
					/*ActionMessages msgs = new ActionMessages();
					ActionMessage errMsg = new ActionMessage("W0010");
					msgs.add(BillingConstants.WARNING_MESSAGE, errMsg);*/
					//request.setAttribute(BillingConstants.WARNING_MESSAGE, "Not Autorized User");
					actionMessage =  new ActionMessage(AdminErrorConstants.NOT_AUTHIRIZED_USER);
				}
			}
			
			
		} catch (CGBusinessException e) {
			LOGGER.error("BillPrintingAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("BillPrintingAction::preparePage ..CGSystemException :"+e);
			//actionMessage =  new ActionMessage(e.getMessage());
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("BillPrintingAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
			//prepareCommonException(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BillPrintingAction::preparePage::END----->");
		return mapping.findForward(BillingConstants.SUCCESS);
	}
	
	//to populate Stations dropdown
	/**
	 * Gets the stations.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the stations
	 */
	@SuppressWarnings("static-access")
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	    
		LOGGER.debug("BillPrintingAction::getStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		try {
			    out = response.getWriter();
				String region =	request.getParameter("region");	
				Integer regionId = Integer.parseInt(region);
				List<CityTO> stationList=billingCommonService.getCitiesByRegionId(regionId);
				
				if(!CGCollectionUtils.isEmpty(stationList)){
					jsonResult = serializer.toJSON(stationList).toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BillPrintingAction::getStations::END----->");
	}
	
	
	//to populate Branches dropdown
		/**
	 * Gets the branches.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the branches
	 */
	@SuppressWarnings("static-access")
		public void getBranches (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			
			LOGGER.debug("BillPrintingAction::getBranches::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			
			try {
				    out = response.getWriter();		
					String station =	request.getParameter("station");	
					Integer stationId = Integer.parseInt(station);
					List<OfficeTO> branchList=billingCommonService.getOfficesByCityId(stationId);
					
					if(!CGCollectionUtils.isEmpty(branchList)){
						jsonResult = serializer.toJSON(branchList).toString();
					}
					
			} catch (CGBusinessException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BillPrintingAction :: getBranches() ::"+e);
				jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			}catch (CGSystemException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BillPrintingAction :: getBranches() ::"+e);
				String exception=getSystemExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} catch(Exception e){
				LOGGER.error("BillPrintingAction :: getBranches() ::"+e);
				String exception=getGenericExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} finally {
				out.print(jsonResult);
				out.flush();
				out.close();
			}
			LOGGER.debug("BillPrintingAction::getBranches::END----->");
		}
		
		//to populate Customers dropdown
		/**
		 * Gets the customers by branch.
		 *
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 * @return the customers by branch
		 */
		public void getCustomersByBranch (ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			
			LOGGER.debug("BillPrintingAction::getCustomersByBranch::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			List<CustomerTO> CustomerList=new  ArrayList<CustomerTO>() ;
			billPrintingService=(BillPrintingService)getBean(AdminSpringConstants.BILLING_PRINT_SERVICE);
			
			try {
				out = response.getWriter();
				String branchIds =	request.getParameter("BranchIds");	
				String branches[]=branchIds.split(",");
				List<Integer> ids=new ArrayList<Integer>();
				for(int i=0;i<branches.length;i++){
					ids.add(Integer.parseInt(branches[i].trim()));
				}
				String productId=request.getParameter("FinancialPId");
				Integer financialProdId=Integer.parseInt(productId);
			
				CustomerList=billPrintingService.getCustsByBillingBranchAndFinancialProduct(ids,financialProdId);
				if(!CGCollectionUtils.isEmpty(CustomerList)){
					jsonResult = serializer.toJSON(CustomerList).toString();
				}
			

			} catch (CGBusinessException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BillPrintingAction :: getCustomersByBranch() ::"+e);
				jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			}catch (CGSystemException e) {
				// TODO Auto-generated catch block
				LOGGER.error("BillPrintingAction :: getCustomersByBranch() ::"+e);
				String exception=getSystemExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} catch(Exception e){
				LOGGER.error("BillPrintingAction :: getCustomersByBranch() ::"+e);
				String exception=getGenericExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} finally {
				out.print(jsonResult);
				out.flush();
				out.close();
			}
			LOGGER.debug("BillPrintingAction::getCustomersByBranch::END----->");
		}
		
		
		/**
		 * Gets the bills.
		 *
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 * @return the bills
		 */
		@SuppressWarnings("static-access")
		public void getBills(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			
			LOGGER.debug("BillPrintingAction::getBills::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			billPrintingService=(BillPrintingService)getBean(AdminSpringConstants.BILLING_PRINT_SERVICE);
			try {
				out = response.getWriter();
				String branchIds =	request.getParameter("CustomerId");	
				String startDate=request.getParameter("StartDate");	
				String endDate =request.getParameter("EndDate");
				String product=request.getParameter("ProductID");
				Integer productId=Integer.parseInt(product);
				/*String branches[]=branchIds.split(" ");*/
				List<Integer> ids=new ArrayList<Integer>();
				
					ids.add(Integer.parseInt(branchIds));
				
					
					String billingOffs=request.getParameter("BillingOffs");	
					String branches[]=billingOffs.split(",");
					List<Integer> billingBrachs=new ArrayList<Integer>();
					for(int i=0;i<branches.length;i++){
						billingBrachs.add(Integer.parseInt(branches[i].trim()));
					}
				List<BillTO> billList=billPrintingService.getBills(ids, startDate, endDate,productId,billingBrachs);
				
				if(!CGCollectionUtils.isEmpty(billList)){
					jsonResult = serializer.toJSON(billList).toString();
				}
			}catch (CGBusinessException e)  {
				LOGGER.error("Error occured in BillPrintingAction :: getBills() :"	+ e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			}catch(CGSystemException e){
				LOGGER.error("Error occured in BillPrintingAction :: getBills() :"	+ e);
				String exception=getSystemExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			}catch(Exception e){
				LOGGER.error("Error occured in BillPrintingAction :: getBills() :"	+ e);
				String exception=getGenericExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} finally {
				out.print(jsonResult);
				out.flush();
				out.close();
			}
			LOGGER.debug("BillPrintingAction::getBills::END----->");
		}
		
	
}