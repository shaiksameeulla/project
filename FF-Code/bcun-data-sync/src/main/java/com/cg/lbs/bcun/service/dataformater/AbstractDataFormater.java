package com.cg.lbs.bcun.service.dataformater;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.service.BcunDatasyncService;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public abstract class AbstractDataFormater {
	/**
	 * @param baseDO
	 * @param bcunService TODO
	 * @return
	 */
	public abstract CGBaseDO formatInsertData(CGBaseDO baseDO, BcunDatasyncService bcunService) throws CGBusinessException,CGSystemException;
	/**
	 * @param baseDO
	 * @return
	 */
	public abstract CGBaseDO formatUpdateData(CGBaseDO baseDO, BcunDatasyncService bcunService)throws CGBusinessException,CGSystemException;
}
