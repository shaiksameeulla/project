package com.cg.lbs.bcun.service.outbound;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;
import com.capgemini.lbs.framework.utils.FileUtils;
import com.cg.lbs.bcun.service.AbstractBcunDatasyncServiceImpl;
import com.cg.lbs.bcun.to.BcunConfigPropertyTO;
import com.cg.lbs.bcun.utility.OutboundPropertyReader;

public class OutboundCentralServiceImpl extends AbstractBcunDatasyncServiceImpl {

	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps() {
		return OutboundPropertyReader.getInboundConfigProperty();
	}
	@Override
	public List<? extends BcunConfigPropertyTO> getBcunConfigProps(String processName) {
		return OutboundPropertyReader.getOutBoundPropertyListByProcess(processName);
	}

	@Override
	public String getModeOfOpration() {
		return bcunProperties.getProperty("bcun.operation.mode");
	}

	@Override
	public String getBaseFileLocation() {
		String fileLocation = null;
		if(ApplicatonUtils.isWindowsOS()) {
			fileLocation = bcunProperties.getProperty("window.central.outbound.file.location");
		} else {
			fileLocation = bcunProperties.getProperty("linux.central.outbound.file.location");
		}
		
		FileUtils.createDirectory(fileLocation);
		return fileLocation;
	}

	@Override
	public void updateTransferedStatus(List<CGBaseDO> baseList) {
		if(baseList == null || baseList.isEmpty())
			return;
		for(CGBaseDO baseDO : baseList) {
			baseDO.setDtToBranch("Y");
			saveOrUpdateTransferedEntity(baseDO);
		}
	}
}
