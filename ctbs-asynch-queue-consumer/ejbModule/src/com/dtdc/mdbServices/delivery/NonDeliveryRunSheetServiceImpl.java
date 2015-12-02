package src.com.dtdc.mdbServices.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import src.com.dtdc.constants.DRSConstants;
import src.com.dtdc.mdbDao.delivery.NonDeliveryRunMDBDAO;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.domain.expense.MiscExpenseDO;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.to.delivery.NonDeliveryRunTO;
import com.dtdc.to.expense.CnMiscExpenseTO;

// TODO: Auto-generated Javadoc
/**
 * The Class NonDeliveryRunSheetServiceImpl.
 */
public class NonDeliveryRunSheetServiceImpl implements NonDeliveryRunSheetService {

	/** logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NonDeliveryRunSheetServiceImpl.class);
	
	
	
	/** The non delivery run mdbdao. */
	private NonDeliveryRunMDBDAO nonDeliveryRunMDBDAO;

	
	/**
	 * Sets the non delivery run mdbdao.
	 *
	 * @param nonDeliveryRunMDBDAO the new non delivery run mdbdao
	 */
	public void setNonDeliveryRunMDBDAO(
			NonDeliveryRunMDBDAO nonDeliveryRunMDBDAO) {
		this.nonDeliveryRunMDBDAO = nonDeliveryRunMDBDAO;
	}
	
	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.NonDeliveryRunSheetService#updateNDRS(CGBaseTO)
	 */
	@Override
	public void updateNDRS(CGBaseTO cgBaseTO) throws CGSystemException,
			CGBusinessException {
		final NonDeliveryRunTO nonDeliveryRunTO = (NonDeliveryRunTO)cgBaseTO.getBaseList().get(0);
		updateNDRS(nonDeliveryRunTO);
	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.delivery.NonDeliveryRunSheetService#updateNDRS(NonDeliveryRunTO)
	 */
	@Override
	public void updateNDRS(NonDeliveryRunTO nonDeliveryRunTO)
			throws CGSystemException, CGBusinessException {
		List<DeliveryDO> deliveryDOs = null;
		List<MiscExpenseDO> miscExpenseDos = null;
		LOGGER.debug("Entered in updateNDRS");
		if(nonDeliveryRunTO != null){
			
			deliveryDOs = nonDeliveryRunMDBDAO.getDeliveryDOs(nonDeliveryRunTO.getConsgNum());
			Map<String, List<? extends DeliveryDO>> drsMap = DrsNdrsConverter.getNDRSEntity(nonDeliveryRunTO, deliveryDOs, nonDeliveryRunMDBDAO);
			List<? extends DeliveryDO> nonDeliveryRunDOs = drsMap.get(DRSConstants.NDRS);
			List<? extends DeliveryDO> bdmFdms = drsMap.get(DRSConstants.BDM_FDM);
			
			if(nonDeliveryRunTO.getMiscExpnseList() != null && !nonDeliveryRunTO.getMiscExpnseList().isEmpty()){
				miscExpenseDos = new ArrayList<MiscExpenseDO>();
				for (CnMiscExpenseTO cnMiscExpenseTO : nonDeliveryRunTO.getMiscExpnseList()) {
					miscExpenseDos.add(DrsNdrsConverter.getMiscExpenseDoFromTo(cnMiscExpenseTO,DRSConstants.DRS));
				}
			}
			
			if(!CollectionUtils.isEmpty(nonDeliveryRunDOs)){
				nonDeliveryRunMDBDAO.updateAllDeliveryDOs(nonDeliveryRunDOs,miscExpenseDos, bdmFdms);
			}
		}
		
	}

	
}
