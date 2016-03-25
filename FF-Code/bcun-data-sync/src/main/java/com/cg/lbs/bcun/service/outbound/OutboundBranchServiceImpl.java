package com.cg.lbs.bcun.service.outbound;

import java.io.File;
import java.util.List;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.AbstractBcunDatasyncServiceImpl;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;

/**
 * @author mohammal
 * Jan 15, 2013
 * Provides BCUN implementation for out bound branch specific
 */
public class OutboundBranchServiceImpl extends AbstractBcunDatasyncServiceImpl {

	
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getBcunConfigProps()
	 */
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps() {
		//Getting configured BCUN properties
		return OutboundPropertyReader.getInboundConfigProperty();
	}
	
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps(String processName) {
		//Getting configured BCUN properties
		return OutboundPropertyReader.getOutBoundPropertyListByProcess(processName);
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getModeOfOpration()
	 */
	@Override
	public String getModeOfOpration() {
		//Return the configured mode of operation
		return bcunProperties.getProperty("bcun.operation.mode");
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.BcunDatasyncService#getProcessFileLocation()
	 */
	@Override
	public String getBaseFileLocation() {
		//Getting file location based on OS
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.branch.outbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.branch.outbound.file.location");
		}
		FileUtils.createDirectory(fileLocation);
		return fileLocation;
	}

	/*@Override
	public String getErrorFileLocation() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			saveOrUpdateTransferedEntity(baseDO);
		}
	}
	public String[] getAllFilesNames(String location) {
		return com.capgemini.lbs.framework.utils.FileUtils.getAllFilesNames(location,FrameworkConstants.BCUN_FILE_IDENTIFIER_OUTBOUND);
	}
	
}
