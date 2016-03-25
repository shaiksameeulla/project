package com.cg.lbs.bcun.service.dataformater;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.umc.PasswordDO;

public class InboundLoginPasswordFormater extends AbstractDataFormater {

	Logger logger = Logger.getLogger(InboundLoginPasswordFormater.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException {
		PasswordDO passwordDO = (PasswordDO) baseDO;
		// passwordDO.setPasswordId(null);
		// passwordDO.setDtToBranch("Y");
		// return passwordDO;

		PasswordDO password = null;
		/*
		 * String[] paramNames = {"encryptedPasword","userId"}; Object[] values
		 * = {passwordDO.getPassword(), passwordDO.getUserId()};
		 */
		List<PasswordDO> passwordDOs = null;

		passwordDOs = (List<PasswordDO>) bcunService
				.getDataByNamedQueryAndNamedParam(
						"getLatestPasswordRecordForInbound", "userId",
						passwordDO.getUserId());

		// passwordDOs =
		// (List<PasswordDO>)bcunService.getDataByNamedQueryAndNamedParam("getLatestPasswordRecordForInbound",
		// paramNames, values);

		//try { 
			if (!StringUtil.isEmptyList(passwordDOs)
					&& !StringUtil.isNull(passwordDOs)) {
				password = passwordDOs.get(0);// get the latest record
				int value = passwordDO.getLastModifiedDate().compareTo(
						password.getLastModifiedDate());
				if (value == -1) {
					throw new CGBusinessException();
				} else {
					passwordDO.setDtToBranch("N");
					if (!StringUtil.isStringEmpty(passwordDO
							.getIsActivePassword())
							&& passwordDO.getIsActivePassword()
									.equalsIgnoreCase("Y")) { 
						if (password.getIsActivePassword()
								.equalsIgnoreCase("Y")) { 

							/*password.setIsActivePassword("N");
							password.setChangeRequired("N");
							password.setDtUpdateToCentral("Y");
							password.setDtToBranch("N");
							bcunService.updatePasswordInCentral(password);
							*/
							// Set IsActivePassword = N for all saved passwords
							
							/*Iterator<PasswordDO> iterator = passwordDOs.iterator();
							while(iterator.hasNext()) {
								passwordDOs.set(index, passwordDO)
							}*/
							for(PasswordDO pass : passwordDOs) {
								pass.setIsActivePassword("N");
								pass.setChangeRequired("N");
								pass.setDtUpdateToCentral("Y");
								pass.setDtToBranch("N");
								bcunService.updatePasswordInCentral(pass);
							}
							//bcunService.updateCentralDB(passwordDOs);
						}
						passwordDO.setPasswordId(null);

						return passwordDO;
					}
				}
			}
		/*} catch (IllegalAccessException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatInsertData :",
					e);
		} catch (InvocationTargetException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatInsertData :",
					e);
		} catch (NoSuchMethodException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatInsertData :",
					e);
		}
*/
		return passwordDO;
	}

	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) {
		PasswordDO passwordDO = (PasswordDO) baseDO;
		PasswordDO password = null;
		Integer userId = passwordDO.getUserId();
		String[] paramNames = { "encryptedPassword", "userId",
				"lastModifiedDate" };
		Object[] values = { passwordDO.getPassword(), userId,
				passwordDO.getLastModifiedDate() };
		List<PasswordDO> passwordDOs = null;
		// passwordDOs = (List<BcunPasswordDO>)
		// bcunService.getDataByNamedQueryAndNamedParam("getUpdateLoginPassword",
		// "userId", userId);
		passwordDOs = (List<PasswordDO>) bcunService
				.getDataByNamedQueryAndNamedParam("getUpdateLoginPassword",
						paramNames, values);
		try {
			if (!StringUtil.isEmptyList(passwordDOs)
					&& !StringUtil.isNull(passwordDOs)) {
				password = passwordDOs.get(0);

				// int
				// value=passwordDO.getLastModifiedDate().compareTo(password.getLastModifiedDate());
				// if(value==1){
				Integer centralPasswordID = password.getPasswordId();
				PropertyUtils.copyProperties(password, passwordDO);
				passwordDO.setPasswordId(centralPasswordID);
				passwordDO.setDtToBranch("N");
				passwordDO.setDtUpdateToCentral("N");
				return passwordDO;
				// }else if(value==-1){
				// PropertyUtils.copyProperties(passwordDO, password);
				// return passwordDO;
				// }
			}

		} catch (IllegalAccessException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatUpdateData :",
					e);
		} catch (InvocationTargetException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatUpdateData :",
					e);
		} catch (NoSuchMethodException e) {
			logger.error(
					"Exception in ::InboundLoginPasswordFormater :: formatUpdateData :",
					e);
		}
		return passwordDO;

	}

}