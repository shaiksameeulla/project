package com.cg.lbs.bcun.service.dataformater;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.consignment.BcunConsignmentDO;
import com.ff.domain.mec.expense.BcunExpenseDO;
import com.ff.domain.mec.expense.BcunExpenseEntriesDO;

public class OutBoundExpenseFormatter extends AbstractDataFormater {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutBoundExpenseFormatter.class);

	@Override
	public CGBaseDO formatInsertData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutBoundExpenseFormatter :: formatInsertData() :: forwarded To formatUpdateData()");
		return formatUpdateData(baseDO, bcunService);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CGBaseDO formatUpdateData(CGBaseDO baseDO,
			BcunDatasyncService bcunService) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutBoundExpenseFormatter :: formatUpdateData() :: START");
		BcunExpenseDO expenseDO = (BcunExpenseDO) baseDO;
		try {
			// To check whether TXN exists already in the branch or not
			List<CGBaseDO> expenseDOs = null;
			String[] params = { "transactionNo" };
			Object[] values = { expenseDO.getTxNumber() };
			expenseDOs = (List<CGBaseDO>) bcunService
					.getDataByNamedQueryAndNamedParam(
							"getBcunExpenseDtlsByTxNo", params, values);

			if (!CGCollectionUtils.isEmpty(expenseDOs)) {
				// If already exists then update existing record
				expenseDO.setExpenseId(((BcunExpenseDO) expenseDOs.get(0))
						.getExpenseId());
			} else {
				// If does not exists then create new record
				expenseDO.setExpenseId(null);
			}

			/*
			 * To validate existence of consingment in branch: IF consignment
			 * exist in branch, then update consignment id in expense entries
			 * details ELSE create new consignment record in branch
			 */
			if (expenseDO.getExpenseFor().equalsIgnoreCase("C")
					&& (!expenseDO.getStatus().equalsIgnoreCase("S") || expenseDO
							.getStatus().equalsIgnoreCase("V"))) {// IF-1

				Set<BcunExpenseEntriesDO> expensEntryDOs = expenseDO
						.getExpenseEntries();
				List<BcunConsignmentDO> consgDOs = null;

				if (!CGCollectionUtils.isEmpty(expensEntryDOs)) {// IF-2

					for (BcunExpenseEntriesDO expensEntryDO : expensEntryDOs) {// FOR-LOOP
						/*
						 * As per business requirement, it should consider only
						 * those records/details whose octroi-bourne-by is
						 * CE-consignee.
						 */
						if (!StringUtil.isNull(expensEntryDO
								.getOctroiBourneBy())
								&& expensEntryDO.getOctroiBourneBy()
										.equalsIgnoreCase("CE")) {// IF-3

							// To check consignment existence in branch
							consgDOs = (List<BcunConsignmentDO>) bcunService
									.getDataByNamedQueryAndNamedParam(
											"getConsgByConsgNo", "consgNo",
											expensEntryDO.getConsignmentDO()
													.getConsgNo());

							if (!CGCollectionUtils.isEmpty(consgDOs)) {// IF-4
								BcunConsignmentDO consgDO = new BcunConsignmentDO();
								consgDO.setConsgId(consgDOs.get(0).getConsgId());
								expensEntryDO.setConsignmentDO(consgDO);
							} else {
								expensEntryDO.getConsignmentDO().setConsgId(
										null);
							}// End of IF-4
						} else {
							// If octroi bourne by is CO - Consignor then,
							// ignore
							// CONSIGNMENT details
							expensEntryDO.setConsignmentDO(null);
						} // End of IF-3

						// If new record then make PK as null
						if (StringUtil.isEmptyLong(expenseDO.getExpenseId())) {// IF-5
							expensEntryDO.setExpenseEntryId(null);
						}// End of IF-5
						expensEntryDO.setExpenseDO(expenseDO);
					}// End of FOR-LOOP
				}// End of IF-2
			}// End of IF-1

			/*
			 * NOTE: Here, IF-1 is not mandatory check, it can be remove later
			 * if it will not require
			 */
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in OutBoundExpenseFormatter :: formatUpdateData() :: ",
					e);
			throw e;
		}
		LOGGER.debug("OutBoundExpenseFormatter :: formatUpdateData() :: END");
		return expenseDO;
	}

}
