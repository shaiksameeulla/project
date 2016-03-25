package com.ff.admin.master.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.master.form.VendorMappingForm;
import com.ff.admin.master.service.VendorMappingService;
import com.ff.business.LoadMovementVendorTO;
import com.ff.geography.CityTO;
import com.ff.master.VendorMappingTO;
import com.ff.organization.OfficeTO;

public class VendorMappingAction extends CGBaseAction {

	private VendorMappingService vendorMappingService;

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VendorMappingAction.class);

	/**
	 * Prepare page.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("VendorMappingAction::preparePage::START----->");
		ActionMessage actionMessage = null;
		List<String> vendorList = null;
		HttpSession session = (HttpSession) request.getSession(false);
		// HttpSession session = request.getSession();
		try {
			vendorMappingService = (VendorMappingService) getBean(AdminSpringConstants.VENDOR_MAPPING_SERVICE);
			vendorList = (List<String>) session
					.getAttribute("vendorList");
			if (CGCollectionUtils.isEmpty(vendorList)) {
				vendorList = vendorMappingService.getAllVendorsList();
			}
			session.setAttribute("vendorList", vendorList);
		} catch (CGBusinessException e) {
			LOGGER.error("VendorMappingAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("VendorMappingAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("VendorMappingAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("VendorMappingAction::preparePage::END----->");
		return mapping.findForward(BillingConstants.SUCCESS);
	}

	public void getVendorMappingInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("VendorMappingAction::getVendorMappingInfo::START----->");
		ActionMessage actionMessage = null;
		String vendorCode = request.getParameter("vendorCode");
		vendorMappingService = (VendorMappingService) getBean(AdminSpringConstants.VENDOR_MAPPING_SERVICE);
		LoadMovementVendorTO vendorTO = null;
		// HttpSession session = request.getSession();
		String jsonResult = null;
		PrintWriter out = null;
		JSONSerializer serializer = null;
		HttpSession session = (HttpSession) request.getSession(false);
		try {
			out = response.getWriter();
			if (!StringUtils.isEmpty(vendorCode)) {
				vendorTO = vendorMappingService.getVendorDetails(vendorCode);
			}

			if (!StringUtil.isNull(vendorTO)) {
				jsonResult = serializer.toJSON(vendorTO).toString();
			}
			session.setAttribute("vendorDetail", vendorTO);
		} catch (CGBusinessException e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("VendorMappingAction::getVendorMappingInfo::END----->");
	}

	@SuppressWarnings("static-access")
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("VendorMappingAction::getStations::START----->");
		String jsonResult = null;
		PrintWriter out = null;
		JSONSerializer serializer = null;
		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			Integer regionId = Integer.parseInt(region);
			List<CityTO> stationList = vendorMappingService
					.getCitiesByRegionId(regionId);

			if (!CGCollectionUtils.isEmpty(stationList)) {
				jsonResult = serializer.toJSON(stationList).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("VendorMappingAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("VendorMappingAction::getStations::END----->");
	}

	public void getBranches(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("VendorMappingAction::getBranches::START----->");
		String jsonResult = null;
		PrintWriter out = null;
		JSONSerializer serializer = null;
		try {
			out = response.getWriter();
			HttpSession session = request.getSession();
			String station = request.getParameter("station");
			String reg = request.getParameter("region");
			Integer rgn=0;
			if(!reg.isEmpty()|| reg !="")
			{
				rgn = Integer.parseInt(reg);
			}
			LoadMovementVendorTO vendorTO = (LoadMovementVendorTO) session
					.getAttribute("vendorDetail");

			Integer stationId = Integer.parseInt(station);
			List<OfficeTO> branchList = vendorMappingService
					.getOfficesByCityId(stationId);

			List<OfficeTO> selBranchList = vendorMappingService
					.getSelectedOffices(rgn, stationId, vendorTO.getVendorId());
			LOGGER.debug("selBranchList----->"+selBranchList);
			if(selBranchList !=null && branchList !=null)
			{
				for (int j = 0; j < selBranchList.size(); j++) {
					for (int i = branchList.size() - 1; i >= 0; i--) {
						if (branchList.get(i).getOfficeId()
								.equals(selBranchList.get(j).getOfficeId())) {
							branchList.remove(i);
							break;
						}
					}
				}
				
				session.setAttribute("previousSelectedList", selBranchList);
			}
			
			List listOfBranches = new ArrayList();

			if (!CGCollectionUtils.isEmpty(branchList)) { 
				listOfBranches.add(branchList);
			} else {
				listOfBranches.add(new ArrayList());
			}
				
			if (!CGCollectionUtils.isEmpty(selBranchList)) {
				listOfBranches.add(selBranchList);
			} else {
				listOfBranches.add(new ArrayList());
			}
				
			if (!CGCollectionUtils.isEmpty(listOfBranches)) {
				jsonResult = serializer.toJSON(listOfBranches).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("VendorMappingAction :: getBranches() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("VendorMappingAction :: getBranches() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("VendorMappingAction :: getBranches() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("VendorMappingAction::getBranches::END----->");
	}

	public void saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("VendorMappingAction::saveOrUpdate::START----->");
		VendorMappingForm vendorMappingForm = null;
		VendorMappingTO vendorMappingTO = null;
		vendorMappingForm = (VendorMappingForm) form;
		vendorMappingTO = (VendorMappingTO) vendorMappingForm.getTo();
		String errorMessage = null;
		PrintWriter out = null;
		int count = 0;
		String successMessage = null;
		JSONSerializer serializer = null;
		try {
			out = response.getWriter();
			HttpSession session = (HttpSession) request.getSession(false);
			LoadMovementVendorTO vendorTO = (LoadMovementVendorTO) session
					.getAttribute("vendorDetail");
			String officeList = request.getParameter("officeLists");
			String[] lists = officeList.split(",");
			List<String> officeNums = Arrays.asList(lists);
			List<Integer> actualOfficeId = new ArrayList<Integer>();
			for (String officeId : officeNums) {
				Integer office = Integer.parseInt(officeId);
				actualOfficeId.add(office);
			}
			
			@SuppressWarnings("unchecked")
			List<OfficeTO> selBranchList=(List<OfficeTO>)session.getAttribute("previousSelectedList");
			List<OfficeTO> officeTobeRemoved= new ArrayList<>();;
			if(selBranchList !=null)
			{
				if(selBranchList.size()>actualOfficeId.size())
				{
					for (OfficeTO officeTO : selBranchList) {
						Integer id=officeTO.getOfficeId();
						if(!actualOfficeId.contains(id))
						{
							officeTobeRemoved.add(officeTO);
						}
					}
				}
			}
			String regionIds = request.getParameter("regionId");
			String address = request.getParameter("address");
			Integer regionId = Integer.parseInt(regionIds);
			if (!StringUtil.isNull(vendorMappingTO)) {
				String[] busiName = vendorMappingTO.getVendorName().split("~");
				String businessName = busiName[0];
				vendorMappingTO.setVendorName(businessName.trim());
				if (!vendorMappingTO.getVendorName().equals(
						vendorTO.getBusinessName())) {
					vendorTO.setBusinessName(vendorMappingTO.getVendorName());
					count += count + 1;
				}
				if (!vendorMappingTO.getVendorCode().equals(
						vendorTO.getVendorCode())) {
					vendorTO.setVendorCode(vendorMappingTO.getVendorCode());
					count += count + 1;
				}

				if (!address.trim().equals(vendorTO.getAddress().trim())) {
					vendorTO.setAddress(vendorMappingTO.getAddress());
					count += count + 1;
				}

				if (count != 0) {
					vendorMappingService.saveOrUpdateVendor(vendorTO);
				}

				vendorMappingService.saveOrUpdateVendorOffice(vendorTO, regionId, actualOfficeId, officeTobeRemoved);
				/*vendorMappingService.saveOrUpdateVendorOffice(vendorTO,
						regionId, actualOfficeId);*/;
				successMessage = "Vendor Office Map Details Saved Successfully";

			}
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			// message = prepareErrorMessageSystemException(request, e,
			// LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdate of VendorMappingAction.."
					+ e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdate of VendorMappingAction.."
					+ e);
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			// message = prepareErrorMessageSystemException(request, e,
			// LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdate of VendorMappingAction.."
					+ e);
		} finally {
			// prepareActionMessage(request, actionMessage);
			vendorMappingTO.setErrorMessage(errorMessage);
			vendorMappingTO.setSuccessMessage(successMessage);
			String vendorOfficeMap = serializer.toJSON(vendorMappingTO)
					.toString();
			// out.write(loadMovementTOJSON);

			out.print(vendorOfficeMap);
			out.flush();
			out.close();
		}
		LOGGER.debug("VendorMappingAction::saveOrUpdate::END----->");
	}
}
