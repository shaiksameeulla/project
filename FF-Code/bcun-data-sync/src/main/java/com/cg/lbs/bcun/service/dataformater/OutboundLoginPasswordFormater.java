package com.cg.lbs.bcun.service.dataformater;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.umc.BcunPasswordDO;

public class OutboundLoginPasswordFormater extends AbstractDataFormater {

	Logger logger = Logger.getLogger(OutboundLoginPasswordFormater.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {

		logger.debug("OutboundLoginPasswordFormater::formatInsertData::START::"
				+ System.currentTimeMillis());

		BcunPasswordDO passwordDO = (BcunPasswordDO) baseDO;
		// passwordDO.setPasswordId(null);
		BcunPasswordDO password = null;
		/*
		 * String[] paramNames = {"encryptedPassword","userId"}; Object[] values
		 * = {passwordDO.getPassword(), passwordDO.getUserId()};
		 */
		List<BcunPasswordDO> passwordDOs = null;
		passwordDOs = (List<BcunPasswordDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getLatestPasswordRecord",
						"userId", passwordDO.getUserId());
		// passwordDOs =
		// (List<BcunPasswordDO>)bcunService.getDataByNamedQueryAndNamedParam("getLatestPasswordRecord",
		// paramNames, values);

		// try{

		if (!StringUtil.isEmptyList(passwordDOs)
				&& !StringUtil.isNull(passwordDOs)) {
			password = passwordDOs.get(0);
			int value = passwordDO.getLastModifiedDate().compareTo(
					password.getLastModifiedDate());
			/*
			 * if(value==1){ passwordDO.setDtToCentral("Y");
			 * if(!StringUtil.isStringEmpty
			 * (passwordDO.getIsActivePassword())&&passwordDO
			 * .getIsActivePassword().equalsIgnoreCase("Y")){
			 * if(password.getIsActivePassword().equalsIgnoreCase("Y")){
			 * password.setIsActivePassword("N");
			 * password.setChangeRequired("N");
			 * password.setDtUpdateToCentral("Y"); password.setDtToCentral("N");
			 * bcunService.updatePasswordInBranch(password); } } return
			 * passwordDO; }else if(value==-1){
			 * PropertyUtils.copyProperties(passwordDO, password); return
			 * passwordDO; }
			 */

			
			// Added to fix the issue of multiple active password
			if (value == -1) {
				throw new CGBusinessException();
			} else {
				passwordDO.setDtToCentral("Y");
				if (!StringUtil.isStringEmpty(passwordDO.getIsActivePassword())
						&& passwordDO.getIsActivePassword().equalsIgnoreCase(
								"Y")) {
					if (password.getIsActivePassword().equalsIgnoreCase("Y")) {
						for (BcunPasswordDO pass : passwordDOs) {
							pass.setIsActivePassword("N");
							pass.setChangeRequired("N");
							pass.setDtUpdateToCentral("Y");
							pass.setDtToBranch("N");
							bcunService.updatePasswordInBranch(pass);
						}
					}
					return passwordDO;
				}
			}
		}
		/*
		 * }catch (IllegalAccessException e) { logger.error(
		 * "Exception in ::OutboundLoginPasswordFormater :: formatInsertData :"
		 * ,e); } catch (InvocationTargetException e) { logger.error(
		 * "Exception in ::OutboundLoginPasswordFormater :: InvocationTargetException :"
		 * ,e); } catch (NoSuchMethodException e) { logger.error(
		 * "Exception in ::OutboundLoginPasswordFormater :: NoSuchMethodException :"
		 * ,e); }
		 */

		logger.debug("OutboundLoginPasswordFormater::formatInsertData::END::"
				+ System.currentTimeMillis());
		return passwordDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {

		logger.debug("OutboundLoginPasswordFormater::formatUpdateData::START::"
				+ System.currentTimeMillis());

		BcunPasswordDO passwordDO = (BcunPasswordDO) baseDO;
		BcunPasswordDO password = null;
		Integer userId = passwordDO.getUserId();
		String[] paramNames = { "encryptedPassword", "userId",
				"lastModifiedDate" };
		Object[] values = { passwordDO.getPassword(), userId,
				passwordDO.getLastModifiedDate() };
		List<BcunPasswordDO> passwordDOs = null;
		// passwordDOs = (List<BcunPasswordDO>)
		// bcunService.getDataByNamedQueryAndNamedParam("getOutboundUpdateLoginPassword",
		// "userId", userId);
		passwordDOs = (List<BcunPasswordDO>) bcunService
				.getDataByNamedQueryAndNamedParam(
						"getOutboundUpdateLoginPassword", paramNames, values);
		try {
			if (!StringUtil.isEmptyList(passwordDOs)
					&& !StringUtil.isNull(passwordDOs)) {
				password = passwordDOs.get(0);

				// int
				// value=passwordDO.getLastModifiedDate().compareTo(password.getLastModifiedDate());
				// if(value==1){

				Integer branchPasswordID = password.getPasswordId();
				PropertyUtils.copyProperties(password, passwordDO);
				passwordDO.setPasswordId(branchPasswordID);
				passwordDO.setDtToCentral("Y");
				passwordDO.setDtUpdateToCentral("N");
				return passwordDO;
				// }else if(value==-1){
				// PropertyUtils.copyProperties(passwordDO, password);
				// return passwordDO;
				// }

			}
		} catch (IllegalAccessException e) {
			logger.error(
					"Exception in ::OutboundLoginPasswordFormater :: formatUpdateData :",
					e);
		} catch (InvocationTargetException e) {
			logger.error(
					"Exception in ::OutboundLoginPasswordFormater :: formatUpdateData :",
					e);
		} catch (NoSuchMethodException e) {
			logger.error(
					"Exception in ::OutboundLoginPasswordFormater :: formatUpdateData :",
					e);
		}

		logger.debug("OutboundLoginPasswordFormater::formatUpdateData::END::"
				+ System.currentTimeMillis());

		return passwordDO;
	}

}
