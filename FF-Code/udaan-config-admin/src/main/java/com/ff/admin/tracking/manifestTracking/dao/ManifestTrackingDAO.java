package com.ff.admin.tracking.manifestTracking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;

public interface ManifestTrackingDAO {
	
	public ManifestDO getManifestInformation1(String manifestNumber, String codeIn, String codeOut) throws CGSystemException;
	public List<ProcessMapDO> getDetailedTrackingInformation(String manifestNum) throws CGSystemException;
	public List<ProcessDO> getProcessDetails() throws CGSystemException;
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException;
	public List<ManifestDO> getEmbeddedInManifestInfo(Integer manifestID, String fetchProfile) throws CGSystemException;
	public ManifestDO getDetailsFromOriginManifest(String manifestNumber) throws CGSystemException;
	public ManifestDO getManifestInformation(String manifestNumber, String manifestDirection, String fetchProfile) throws CGSystemException;
}
