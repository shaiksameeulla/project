package com.ff.admin.mec.collection.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;

public interface BillCollectionDAO {
	
	public CollectionDO searchBillCollectionDtls(String transactionNo, String collectionType)
			throws CGSystemException ;
	
	public List<CollectionDtlsDO> getCollectionDetailsByBillNumber(List<String> billNo)
	throws CGSystemException;

}
