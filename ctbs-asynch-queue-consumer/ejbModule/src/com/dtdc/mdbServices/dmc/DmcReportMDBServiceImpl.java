/**
 * 
 */
package src.com.dtdc.mdbServices.dmc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.com.dtdc.constants.DMCConstants;
import src.com.dtdc.mdbDao.dmc.DelvryMangmntCellMDBDAO;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.domain.booking.ProductDO;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.transaction.delivery.DeliveryManagementDO;
import com.dtdc.to.dmc.DMCFranchiseeDirectEmpTO;
import com.dtdc.to.dmc.DeliveryManagementTO;
import com.dtdc.to.dmc.DmcGridTO;

// TODO: Auto-generated Javadoc
/**
 * The Class DmcReportMDBServiceImpl.
 *
 * @author mohammes
 */
public class DmcReportMDBServiceImpl implements DmcReportMDBService {


	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
	.getLogger(DmcReportMDBServiceImpl.class);

	/** The delvry mangmnt cell dao. */
	DelvryMangmntCellMDBDAO delvryMangmntCellDAO;

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dmc.DmcReportMDBService#saveDeliverymanagementInfo(CGBaseTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveDeliverymanagementInfo(CGBaseTO dmcTo)
	throws CGBusinessException, CGSystemException {
		List<DMCFranchiseeDirectEmpTO> dmcToList = (List<DMCFranchiseeDirectEmpTO>) dmcTo
		.getBaseList();	
		List<DmcGridTO>	dmcGridTo = (List<DmcGridTO>)dmcTo.getJsonChildObject();
		dmcToList.get(0).setDmcGridTO(dmcGridTo);
		return saveDeliverymanagementInfo(dmcToList);

	}

	/* (non-Javadoc)
	 * @see ejbModule.src.com.dtdc.mdbServices.dmc.DmcReportMDBService#saveDeliverymanagementInfo(List)
	 */
	@Override
	public boolean saveDeliverymanagementInfo(
			List<DMCFranchiseeDirectEmpTO> dMCToList)
	throws CGBusinessException, CGSystemException {
		boolean resultStatus=false;
		if(dMCToList != null && !dMCToList.isEmpty()){
			DMCFranchiseeDirectEmpTO dmcFranchEmpTo=dMCToList.get(0);
			//LOGGER.debug("DmcReportServiceImpl::saveDeliverymanagementInfo::savind Information=======>start" );
			// List<DeliveryManagementDO> deliveryManagementList=new ArrayList<DeliveryManagementDO>();
			List<DmcGridTO>	dmcGridTo =dmcFranchEmpTo.getDmcGridTO();
			DeliveryManagementDO domainobj=null;
			List<DeliveryManagementDO> deliveryManagementDoList= new ArrayList<DeliveryManagementDO>();
			if(dmcFranchEmpTo!=null&&dmcGridTo.size()>0){


				for(int i=0;i<dmcGridTo.size();i++){	
					FranchiseeDO fdo = new FranchiseeDO();
					EmployeeDO edo = new EmployeeDO();
					ProductDO po = new ProductDO();
					ServiceDO so=new ServiceDO();
					if (dmcFranchEmpTo.getFranchemp().equalsIgnoreCase(
							BookingConstants.FRANCHISEE)) {
						dmcGridTo.get(i).setFrId(Integer
								.valueOf(dmcFranchEmpTo.getFranchiseeIdStr()));


					} else if (dmcFranchEmpTo.getFranchemp().equalsIgnoreCase(
							BookingConstants.DIRECT_EMPLOYEE)) {
						dmcGridTo.get(i).setEmpid(Integer.valueOf(dmcFranchEmpTo
								.getCustomerIdStr()));

					}
					dmcGridTo.get(i).setHandoverDate(DateFormatterUtil
							.getDateFromStringDDMMYYY(dmcFranchEmpTo
									.getDmcDateStr()));
					dmcGridTo.get(i).setFromTime(dmcFranchEmpTo
							.getDmcFromTime());
					dmcGridTo.get(i).setToTime(dmcFranchEmpTo.getDmcToTime());
					dmcGridTo.get(i).setTokenNumber(dmcFranchEmpTo
							.getAssignTokenNo());
					dmcGridTo.get(i).setPodReceived(dmcFranchEmpTo
							.getPodReceived());
					domainobj = new DeliveryManagementDO();
					// Covert RowTO To DO
					CGObjectConverter.createDomainFromTo(dmcGridTo.get(i),
							domainobj);
					domainobj.setPodReceived(dmcGridTo.get(i).getPodReceived()!=null?dmcGridTo.get(i).getPodReceived():0);
					//  Here setting the values for foreign keys
					if (dmcFranchEmpTo.getFranchemp().equalsIgnoreCase(
							BookingConstants.FRANCHISEE)) {
						fdo.setFranchiseeId(dmcGridTo.get(i).getFrId());
						edo = null;
						domainobj.setFranchiseeDO(fdo);
						domainobj.setEmployeeDO(edo);
						domainobj.setFdmdrsCount(dmcGridTo.get(i).getFdmdrsCount());

					}else if (dmcFranchEmpTo.getFranchemp().equalsIgnoreCase(
							BookingConstants.DIRECT_EMPLOYEE)) {
						edo.setEmployeeId(dmcGridTo.get(i).getEmpid());
						fdo = null;
						domainobj.setEmployeeDO(edo);
						domainobj.setFranchiseeDO(fdo);
						domainobj.setTotalDrsCount(dmcGridTo.get(i).getFdmdrsCount());
					}
					if (dmcGridTo.get(i).getProductId()==DMCConstants.ONE_CONSTANT) {

						po.setProductId(1);
						domainobj.setProductTypeDO(po);
						so=null;
						domainobj.setServiceDO(so);
					}else if (dmcGridTo.get(i).getProductId()==DMCConstants.TWO_CONSTANT) {

						po.setProductId(2);
						domainobj.setProductTypeDO(po);
						so=null;
						domainobj.setServiceDO(so);
					}else if (dmcGridTo.get(i).getProductId()==DMCConstants.THREE_CONSTANT) {

						po.setProductId(3);
						domainobj.setProductTypeDO(po);
						//FIXME 
						domainobj.setConsignmentNo(dmcGridTo.get(i).getConsignmentNo());
						if(dmcGridTo.get(i).getServiceId()==DMCConstants.FOUR_FIVE_CONSTANT){
							so.setServiceId(dmcGridTo.get(i).getServiceId());
							domainobj.setServiceDO(so);
							domainobj.setCodAmount(dmcGridTo.get(i).getCodAmnt());
							domainobj.setChequeNumber(dmcGridTo.get(i).getChequeNo().toString());
							domainobj.setFodAmount(dmcGridTo.get(i).getFodAmnt());
							domainobj.setReceiptNumber(dmcGridTo.get(i).getReceiptNo());
						}else if(dmcGridTo.get(i).getServiceId()==DMCConstants.FOUR_SEVEN_CONSTANT){
							so.setServiceId(dmcGridTo.get(i).getServiceId());
							domainobj.setServiceDO(so);
							domainobj.setFodAmount(dmcGridTo.get(i).getFodAmnt());
							domainobj.setReceiptNumber(dmcGridTo.get(i).getReceiptNo());

						}else if(dmcGridTo.get(i).getServiceId()==DMCConstants.FOUR_SIX_CONSTANT){
							so.setServiceId(dmcGridTo.get(i).getServiceId());
							domainobj.setServiceDO(so);
							domainobj.setCodAmount(dmcGridTo.get(i).getCodAmnt());
							domainobj.setChequeNumber(dmcGridTo.get(i).getChequeNo().toString());

						}

					}

					deliveryManagementDoList.add(domainobj);

				}//end for loop
				if(deliveryManagementDoList.size()>0 &&dmcGridTo.size()>0)
				{
					//calling Dao--start
					resultStatus=delvryMangmntCellDAO.saveDeliverymanagementInfo(deliveryManagementDoList,dmcGridTo);
					//calling Dao--end
				}

			}
			else{
				//LOGGER.debug("DmcReportServiceImpl::saveDeliverymanagementInfo::---------no data save" );
				resultStatus=false;

			}

		}
		return resultStatus;
	}

	/**
	 * Gets the delvry mangmnt cell dao.
	 *
	 * @return the delvryMangmntCellDAO
	 */
	public DelvryMangmntCellMDBDAO getDelvryMangmntCellDAO() {
		return delvryMangmntCellDAO;
	}

	/**
	 * Sets the delvry mangmnt cell dao.
	 *
	 * @param delvryMangmntCellDAO the delvryMangmntCellDAO to set
	 */
	public void setDelvryMangmntCellDAO(DelvryMangmntCellMDBDAO delvryMangmntCellDAO) {
		this.delvryMangmntCellDAO = delvryMangmntCellDAO;
	}

	/**
	 * Save dmc.
	 *
	 * @param dmcTo the dmc to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveDmc(CGBaseTO dmcTo)
	throws CGBusinessException, CGSystemException {
		List<DeliveryManagementTO> dmcToList = (List<DeliveryManagementTO>) dmcTo
		.getBaseList();	
		if(StringUtil.isEmptyList(dmcToList)){
			return false;
		}

		return saveDmc(dmcToList);

	}

	/**
	 * Save dmc.
	 *
	 * @param dmcList the dmc list
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public boolean saveDmc(List<DeliveryManagementTO>  dmcList)
	throws CGBusinessException, CGSystemException {
		LOGGER.debug("DmcReportMDBServiceImpl:: saveDmc----START");
		List<DeliveryManagementDO> dmcDoList=null;
		if(!StringUtil.isEmptyList(dmcList)){
			dmcDoList= new ArrayList<DeliveryManagementDO>(dmcList.size());
		}else{
			LOGGER.debug("DmcReportMDBServiceImpl:: due to empty list from queue data saving is not happend");
			throw new CGBusinessException("DmcReportMDBServiceImpl:: due to empty list from queue data saving is not happend");
		}
		try {	
			for(DeliveryManagementTO dmcTo : dmcList){

				DeliveryManagementDO dmcDo = new DeliveryManagementDO();

				PropertyUtils.copyProperties(dmcDo, dmcTo);

				if(!StringUtil.isEmptyInteger(dmcTo.getFranchiseeId())){
					FranchiseeDO franchiseeDO = new FranchiseeDO();
					franchiseeDO.setFranchiseeId(dmcTo.getFranchiseeId());
					dmcDo.setFranchiseeDO(franchiseeDO);
				}

				if(!StringUtil.isEmptyInteger(dmcTo.getEmployeeId())){
					EmployeeDO employeeDO = new EmployeeDO();
					employeeDO.setEmployeeId(dmcTo.getEmployeeId());
					dmcDo.setEmployeeDO(employeeDO);
				}

				if(!StringUtil.isEmptyInteger(dmcTo.getProductId())){
					ProductDO productTypeDO = new ProductDO();
					productTypeDO.setProductId(dmcTo.getProductId());
					dmcDo.setProductTypeDO(productTypeDO);
				}
				if(!StringUtil.isEmptyInteger(dmcTo.getServiceId())){
					ServiceDO serviceDO = new ServiceDO();
					serviceDO.setServiceId(dmcTo.getProductId());
					dmcDo.setServiceDO(serviceDO);
				}

				if(!StringUtil.isEmptyInteger(dmcTo.getUserId())){
					UserDO userDo = new UserDO();
					userDo.setUserId(dmcTo.getUserId());
					dmcDo.setUserDo(userDo);
				}

				dmcDoList.add(dmcDo);

			}

			delvryMangmntCellDAO.saveDMC(dmcDoList);
		} catch (Exception e) {
			LOGGER.error("DmcReportMDBServiceImpl::saveDmc::Exception occured:"
					+e.getMessage());
			throw new CGBusinessException(e);
		}

		LOGGER.debug("DmcReportMDBServiceImpl:: saveDmc----END");
		return true;

	}

}
