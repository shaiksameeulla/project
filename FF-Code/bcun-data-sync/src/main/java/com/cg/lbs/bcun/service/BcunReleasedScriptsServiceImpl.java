package com.cg.lbs.bcun.service;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.dao.BcunDatasyncDAO;
import com.ff.domain.releasedscripts.ReleasedScriptsDO;

/**
 * The Main objective of this service class is executing the SQL scripts on branch DB,
 * which we are transfer from central to branch.
 * @author bmodala
 */
public class BcunReleasedScriptsServiceImpl implements BcunReleasedScriptsService{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BcunReleasedScriptsServiceImpl.class);

	private BcunDatasyncDAO datasyncDao;

	public void setDatasyncDao(BcunDatasyncDAO datasyncDao) {
		this.datasyncDao = datasyncDao;
	}

	@SuppressWarnings("unchecked")
	public void processReleasedScriptsOnBranch(){
		LOGGER.debug("BcunReleasedScriptsServiceImpl: processReleasedScriptsOnBranch(): START");
		try{
			List<ReleasedScriptsDO> releaseScriptsDos =(List<ReleasedScriptsDO>) datasyncDao.getDataByNamedQuery("getAllNonExcecutedScripts");
			LOGGER.debug("-----Count of released scripts----"+releaseScriptsDos.size());
			if(!StringUtil.isEmptyList(releaseScriptsDos)){
				for (ReleasedScriptsDO releasedScriptsDO : releaseScriptsDos) {
					try{
						boolean flag=datasyncDao.executeReleasedSQLScripts(releasedScriptsDO.getScriptContent());
						if(flag){
							releasedScriptsDO.setIsExecuted("Yes");
							releasedScriptsDO.setExecutedDate(new Date());
							releasedScriptsDO.setException(null);
							datasyncDao.updateEntityStatus(releasedScriptsDO);
						}
					}catch(Exception e){
						LOGGER.error("BcunReleasedScriptsServiceImpl: processReleasedScriptsOnBranch():Exception ::::::"+e);
						String exceptionMessage = "";
						exceptionMessage = ExceptionUtil.getMessageFromException(e);
						releasedScriptsDO.setExecutedDate(new Date());
						releasedScriptsDO.setException(exceptionMessage.substring(0,(exceptionMessage.length() >= 499) ? 499 : exceptionMessage.length()));
						datasyncDao.updateEntityStatus(releasedScriptsDO);
					}
				}
			}
		}catch(Exception ex){
			LOGGER.error("BcunReleasedScriptsServiceImpl: processReleasedScriptsOnBranch():Exception ::::::"+ex);
		}
		LOGGER.debug("BcunReleasedScriptsServiceImpl: processReleasedScriptsOnBranch(): END");
	}
}
