package com.ff.admin.master.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.master.constants.HolidayConstants;
import com.ff.admin.master.form.HolidayForm;
import com.ff.admin.master.service.HolidayService;
import com.ff.admin.master.service.PincodeMasterService;
import com.ff.domain.masters.HolidayDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.master.HolidayNameTO;
import com.ff.master.HolidayTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.organization.service.OrganizationCommonService;

public class HolidayAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(HolidayAction.class);

	private HolidayService holidayService;

	private PincodeMasterService pincodeMasterService;

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
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("HolidayAction::preparePage::START");
		try {
			HolidayForm holidayForm = (HolidayForm) form;
			HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
			holidayTO = new HolidayTO();
			loadCommonData(request, holidayForm, holidayTO);
		} catch (CGBusinessException e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		}
		LOGGER.debug("HolidayAction:preparePage:End");

		return mapping.findForward(HolidayConstants.SUCCESS);
	}
	
	public ActionForward prepareCreateHolidayPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("HolidayAction::preparePage::START");
		try {
			HolidayForm holidayForm = (HolidayForm) form;
			HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
			holidayTO = new HolidayTO();
			loadCommonData(request, holidayForm, holidayTO);
		} catch (CGBusinessException e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("HolidayAction::preparePageForTrain ..Exception :" + e);
			getGenericException(request, e);
		}
		LOGGER.debug("HolidayAction:preparePage:End");

		return mapping.findForward(HolidayConstants.SUCCESS_CREATE);
	}
	
	public ActionForward prepareEditHolidayPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("HolidayAction::prepareEditHolidayPage::START");
		HolidayForm holidayForm = (HolidayForm) form;
		HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
		try {
			holidayTO.setDate(request.getParameter("date"));
			holidayTO.setRegionId(Integer.parseInt(request.getParameter("regionId")));
			holidayTO.setStateId(Integer.parseInt(request.getParameter("stateId")));
			holidayTO.setCityId(Integer.parseInt(request.getParameter("stationId")));
			holidayTO.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			holidayTO.setHolidayNameId(Integer.parseInt(request.getParameter("reasonId")));
			holidayTO.setOthers(request.getParameter("others"));
			
			holidayTO.setRegionName(request.getParameter("regionName"));
			holidayTO.setStateName(request.getParameter("stateName"));
			holidayTO.setCityName(request.getParameter("stationName"));
			holidayTO.setBranchName(request.getParameter("branchName"));
			
			holidayService = (HolidayService) getBean(AdminSpringConstants.HOLIDAY_SERVICE);
			if (holidayService != null) {
				List<HolidayNameTO> holidayNameTOs = holidayService
						.getHolidayNameList();
				if (!StringUtil.isEmptyColletion(holidayNameTOs)) {
					request.setAttribute(HolidayConstants.HOLIDAY_NAME_LIST,
							holidayNameTOs);
				}
			}
			
			holidayForm.setTo(holidayTO);
			
		} catch (CGBusinessException | CGSystemException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		LOGGER.debug("HolidayAction::prepareEditHolidayPage::END");
		return mapping.findForward(HolidayConstants.SUCCESS_EDIT);
	}

	private void loadCommonData(HttpServletRequest request,
			HolidayForm holidayForm, HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException {
		pincodeMasterService = (PincodeMasterService) getBean(AdminSpringConstants.PINCODE_MASTER_SERVICE);
		if (pincodeMasterService != null) {
			List<RegionTO> regionTo = pincodeMasterService.getRegions();
			if (!StringUtil.isEmptyColletion(regionTo)) {
				request.setAttribute(HolidayConstants.REGION_LIST, regionTo);
			}
		}
		
		
		
		holidayService = (HolidayService) getBean(AdminSpringConstants.HOLIDAY_SERVICE);
		if (holidayService != null) {
			List<HolidayNameTO> holidayNameTOs = holidayService
					.getHolidayNameList();
			if (!StringUtil.isEmptyColletion(holidayNameTOs)) {
				request.setAttribute(HolidayConstants.HOLIDAY_NAME_LIST,
						holidayNameTOs);
			}
		}
		request.setAttribute("to", holidayTO);
		holidayForm.setTo(holidayTO);
		request.setAttribute("holidayForm", holidayForm);
	}

	public ActionForward saveHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:saveAirData:Start");
			HolidayForm holidayForm = (HolidayForm) form;
			HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
			holidayService = (HolidayService) getBean(AdminSpringConstants.HOLIDAY_SERVICE);
			if (holidayService != null) {
				
				List<HolidayDO> holidayDOList = holidayService
						.getHoliday(holidayTO);
				
				if (CGCollectionUtils.isEmpty(holidayDOList)) {
					
					actionMessage = saveHoliday(actionMessage, holidayTO);
					
					holidayTO = new HolidayTO();
				} else {
					boolean saveFlag = true;
					for (HolidayDO holidayDO : holidayDOList) {
						if (holidayDO.getRegionId() == null) {
							actionMessage = new ActionMessage(
									HolidayConstants.HD0002);
							saveFlag = false;
						} else if (holidayDO.getStateId() == null
								&& holidayDO.getRegionId().equals(
										holidayTO.getRegionId())) {
							actionMessage = new ActionMessage(
									HolidayConstants.HD0002);
							saveFlag = false;
						} else if (holidayDO.getCityId() == null
								&& (holidayDO.getStateId() != null && holidayDO
										.getStateId().equals(
												holidayTO.getStateId()))) {
							actionMessage = new ActionMessage(
									HolidayConstants.HD0002);
							saveFlag = false;
						} else if (holidayDO.getBranchId() == null
								&& (holidayDO.getCityId() != null && holidayDO
										.getCityId().equals(
												holidayTO.getCityId()))) {
							actionMessage = new ActionMessage(
									HolidayConstants.HD0002);
							saveFlag = false;
						} else if ((holidayTO.getHolidayNameId() !=null && holidayDO.getHolidayNameId() != null) && holidayTO.getHolidayNameId().equals(holidayDO.getHolidayNameId())) {
							actionMessage = new ActionMessage(
									HolidayConstants.HD0002);
							saveFlag = false;
						}
					}
					if (saveFlag) {
						for (HolidayDO holidayDO : holidayDOList) {
							if (DateUtil.slashDelimitedstringToDDMMYYYYFormat(
									holidayTO.getDate()).equals(
									holidayDO.getDate())
									&& holidayTO.getRegionId().equals(
											holidayDO.getRegionId())
									&& holidayTO.getStateId().equals(
											holidayDO.getStateId())
									&& holidayTO.getCityId().equals(
											holidayDO.getCityId())
									&& holidayTO.getBranchId().equals(
											holidayDO.getBranchId())) {
								actionMessage = new ActionMessage(
										HolidayConstants.HD0002);
								saveFlag = false;
								break;
							}
						}
						if (saveFlag) {
							if (holidayTO.getRegionId() == 0) {
								for (HolidayDO holidayDO : holidayDOList) {
									holidayDO
											.setStatus(HolidayConstants.INACTIVE);
									holidayService.updateHoliday(holidayDO);
								}
							} else if (holidayTO.getStateId() == null
									|| holidayTO.getStateId() == 0) {
								holidayService.updateHoliday(holidayTO
										.getRegionId());
							} else if (holidayTO.getCityId() == null
									|| holidayTO.getCityId() == 0) {
								holidayService.updateHoliday(
										holidayTO.getRegionId(),
										holidayTO.getStateId());
							} else if (holidayTO.getBranchId() == null
									|| holidayTO.getBranchId() == 0) {
								holidayService.updateHoliday(
										holidayTO.getRegionId(),
										holidayTO.getStateId(),
										holidayTO.getCityId());
							}
							actionMessage = saveHoliday(actionMessage,
									holidayTO);
							holidayTO = new HolidayTO();
						}else{
							holidayTO = new HolidayTO();
						}
					}else{
						holidayTO = new HolidayTO();
					}
				}

			}
			loadCommonData(request, holidayForm, holidayTO);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::saveTrainData ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::saveTrainData ..CGSystemException :"
					+ e); // actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::saveTrainData ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:saveTrainData:End");
		
		if (HolidayConstants.SENDER_PAGE_EDIT_POPUP.equals(request.getParameter(HolidayConstants.SENDER_PAGE))) {
			return mapping.findForward(HolidayConstants.SUCCESS_EDIT);
		} else {
			return mapping.findForward(HolidayConstants.SUCCESS);
		}
	}

	private ActionMessage saveHoliday(ActionMessage actionMessage,
			HolidayTO holidayTO) throws CGBusinessException, CGSystemException {
		HolidayDO holidayDO;
		holidayDO = holidayService.saveHoliday(holidayTO);
		if (holidayDO != null) {
			actionMessage = new ActionMessage(HolidayConstants.HD0001);
			holidayTO.setRegionId(holidayDO.getRegionId());
			holidayTO.setStateId(holidayDO.getStateId());
			holidayTO.setCityId(holidayDO.getCityId());
			holidayTO.setBranchId(holidayDO.getBranchId());
			holidayTO.setHolidayNameId(holidayDO.getHolidayNameId());
			holidayTO.setOthers(holidayDO.getOthers());
		}
		return actionMessage;
	}

	/**
	 * Gets the states.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 */
	public void getStatesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("HolidayAction::getStatesByRegion::START");
		
		PrintWriter out = null;
		String region = request.getParameter("regionId");
		Integer regionId = Integer.parseInt(region);
		String stateJson = FrameworkConstants.EMPTY_STRING;
		

		try {
			out = response.getWriter();
			List<StateTO> stateTO;
			stateTO = pincodeMasterService.getStatesByRegionId(regionId);
			
			if (!CGCollectionUtils.isEmpty(stateTO))
				stateJson = JSONSerializer.toJSON(stateTO).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("HolidayAction::getStatesByRegion::Exception :"
					, e);
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("HolidayAction::getStatesByRegion::Exception :"
					+ e.getMessage());
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("HolidayAction::getStatesByRegion::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(stateJson);
			out.flush();
			out.close();
		}

		LOGGER.debug("HolidayAction::getStatesByRegion::END");
	}

	/**
	 * Gets the citys.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 */
	public void getCitysByState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("HolidayAction::getCitysByState::START");

		PrintWriter out = null;
		String state = request.getParameter("stateId");
		Integer stateId = Integer.parseInt(state);
		String region = request.getParameter("regionId");
		Integer regionId = Integer.parseInt(region);
		String cityJson = FrameworkConstants.EMPTY_STRING;
		
		try {
			out = response.getWriter();
			List<CityTO> cityTO;
			cityTO = pincodeMasterService.getCitysByStateIdAndRegionID(stateId,regionId);
			request.setAttribute("cityTO", cityTO);
			if (cityTO != null)
				cityJson = JSONSerializer.toJSON(cityTO).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("HolidayAction::getCitysByState::Exception :"
					, e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("HolidayAction::getCitysByState::Exception :"
					+ e.getMessage());
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("HolidayAction::getCitysByState::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			if (cityJson != null) {
				out.print(cityJson);
			}
			out.flush();
			out.close();
		}
		LOGGER.debug("HolidayAction::getCitysByState::END");
	}

	/**
	 * Gets the branches.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void getBranchesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("HolidayAction::getBranchesByCity::START");

		PrintWriter out = null;
		String city = request.getParameter("cityId");
		Integer cityId = Integer.parseInt(city);
		String branchJson = FrameworkConstants.EMPTY_STRING;
		
		try {
			OrganizationCommonService commonService = (OrganizationCommonService) getBean(AdminSpringConstants.ORGANIZATION_COMMON_SERVICE);
			if (commonService != null) {
				out = response.getWriter();
				List<OfficeTO> officeTO = commonService
						.getAllOfficesByCity(cityId);
				if (!CGCollectionUtils.isEmpty(officeTO)) {
					request.setAttribute("branchOffices", officeTO);
					branchJson = JSONSerializer.toJSON(officeTO).toString();
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("HolidayAction::getBranchesByCity::Exception :"
					, e);
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("HolidayAction::getBranchesByCity::Exception :"
					+ e.getMessage());
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("HolidayAction::getBranchesByCity::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(branchJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("HolidayAction::getBranchesByCity::END");
	}

	public HolidayService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}
	
	public ActionForward searchHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.trace("HolidayAction::searchHoliday::START::" + System.currentTimeMillis());
		String jsonResult = "";
		PrintWriter out=null;
		
		try {
			out=response.getWriter();
			response.setContentType("text/javascript");
			
			HolidayForm holidayForm = (HolidayForm) form;
			HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
			holidayService = (HolidayService) getBean(AdminSpringConstants.HOLIDAY_SERVICE);
			
			List<HolidayTO> holidayTOList = holidayService.searchHoliday(holidayTO);
			
			if (!CollectionUtils.isEmpty(holidayTOList)) {
				jsonResult = JSONSerializer.toJSON(holidayTOList).toString();
			}
		} catch (CGBusinessException  e) {
			LOGGER.error("Error occured in HolidayAction :: searchHoliday() :"
					+ e.getMessage());
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			
		}catch (CGSystemException  e) {
			LOGGER.error("Error occured in HolidayAction :: searchHoliday() :"
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			
		}  
		catch (Exception e) {
			LOGGER.error("Error occured in HolidayAction :: searchHoliday() :"
					+ e.getMessage());
			
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		
		} finally{
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("HolidayAction::searchHoliday::END");
		return null;
	}
	
	public ActionForward editExistingHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//String jsonResult = null;
		Boolean flag = Boolean.FALSE;
		try {
			LOGGER.debug("ColoadingAction:saveAirData:Start");
			HolidayForm holidayForm = (HolidayForm) form;
			HolidayTO holidayTO = (HolidayTO) holidayForm.getTo();
			holidayService = (HolidayService) getBean(AdminSpringConstants.HOLIDAY_SERVICE);
			if (holidayService != null) {
				flag = holidayService.editExistingHoliday(holidayTO);
			}
		} catch (CGSystemException e) {
			e.printStackTrace();
		}finally{
			
		}
		return  mapping.findForward(HolidayConstants.SUCCESS);
	}

}
