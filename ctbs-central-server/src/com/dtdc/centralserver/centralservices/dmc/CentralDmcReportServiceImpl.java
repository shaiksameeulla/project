/**
 * 
 */
package com.dtdc.centralserver.centralservices.dmc;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.capgemini.lbs.framework.constants.BookingConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.centralserver.centraldao.dmc.CentralDelvryMangmntCellDAO;
import com.dtdc.to.dmc.DMCFranchiseeDirectEmpTO;
import com.dtdc.to.dmc.DmcCentralTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralDmcReportServiceImpl.
 *
 * @author mohammes
 */
public class CentralDmcReportServiceImpl implements CentralDmcReportService {
	
	/** The central delvry mangmnt cell dao. */
	private CentralDelvryMangmntCellDAO centralDelvryMangmntCellDAO;
	
	/**
	 * Gets the central delvry mangmnt cell dao.
	 *
	 * @return the centralDelvryMangmntCellDAO
	 */
	public CentralDelvryMangmntCellDAO getCentralDelvryMangmntCellDAO() {
		return centralDelvryMangmntCellDAO;
	}
	
	/**
	 * Sets the central delvry mangmnt cell dao.
	 *
	 * @param centralDelvryMangmntCellDAO the centralDelvryMangmntCellDAO to set
	 */
	public void setCentralDelvryMangmntCellDAO(
			CentralDelvryMangmntCellDAO centralDelvryMangmntCellDAO) {
		this.centralDelvryMangmntCellDAO = centralDelvryMangmntCellDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.dtdc.centralserver.centralservices.dmc.CentralDmcReportService#findFdmDetails(CGBaseTO)
	 */
	@Override
	public CGBaseTO findFdmDetails(CGBaseTO baseTO) throws CGBusinessException,CGSystemException {
		baseTO.getBaseList();
		List dmcFrEmpList = baseTO.getJsonChildObject();
		DmcCentralTO dmcCentralTo = new DmcCentralTO();
		if (dmcFrEmpList != null) {
			DMCFranchiseeDirectEmpTO dmcFranchiseeTo = (DMCFranchiseeDirectEmpTO) dmcFrEmpList.get(0);
			dmcCentralTo = displayFirstGridInformationForFranchisee(dmcFranchiseeTo);
		}
		CGBaseTO returnTo=new CGBaseTO();
		List<DmcCentralTO> resultToList= new ArrayList<DmcCentralTO>();
		resultToList.add(dmcCentralTo);
		returnTo.setBaseList(resultToList);
		return returnTo;
	}
	
	/**
	 * Display first grid information for franchisee.
	 *
	 * @param dmcFranchiseeDirectEmpTO the dmc franchisee direct emp to
	 * @return the dmc central to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public DmcCentralTO displayFirstGridInformationForFranchisee(DMCFranchiseeDirectEmpTO dmcFranchiseeDirectEmpTO)throws CGBusinessException,CGSystemException
	{
		DmcCentralTO dmcCentralTo=new DmcCentralTO();
		//to display firstGrid information for Franchisee


		DMCFranchiseeDirectEmpTO  franchisee=new DMCFranchiseeDirectEmpTO();
		float delPerc=0.0f;
		if(dmcFranchiseeDirectEmpTO!=null){
			franchisee.setDmcDate(DateFormatterUtil.getDateFromStringDDMMYYY(dmcFranchiseeDirectEmpTO.getDmcDateStr()));
			franchisee.setFranchiseeID(Integer.valueOf(dmcFranchiseeDirectEmpTO.getFranchiseeIdStr()));
			franchisee.setFranchiseeCode(dmcFranchiseeDirectEmpTO.getFranchiseeCode());
			franchisee.setDmcFromTime(dmcFranchiseeDirectEmpTO.getDmcFromTime());
			franchisee.setDmcToTime(dmcFranchiseeDirectEmpTO.getDmcToTime());
			franchisee.setFranchemp(dmcFranchiseeDirectEmpTO.getFranchemp());
			franchisee.setPreparationDate(DateFormatterUtil.getPreviousDateFromGivenDate(franchisee.getDmcDate()));
			franchisee.setFromDate(combineDateTime(dmcFranchiseeDirectEmpTO.getDmcDateStr(),dmcFranchiseeDirectEmpTO.getStartTimeHr(),dmcFranchiseeDirectEmpTO.getStartTimeMin()));
			franchisee.setToDate(combineDateTime(dmcFranchiseeDirectEmpTO.getDmcDateStr(),dmcFranchiseeDirectEmpTO.getEndTimeHr(),dmcFranchiseeDirectEmpTO.getEndTimeMin()));
		}

		List deliveryInformation=centralDelvryMangmntCellDAO.findFdmDetails(franchisee);
		List delivered=centralDelvryMangmntCellDAO.getDeliveredInfo(franchisee);
		if(delivered !=null &&!delivered.isEmpty()){
			List prepared=centralDelvryMangmntCellDAO.getPreparedInfo(franchisee);
			if(prepared !=null && !prepared.isEmpty()){
				int preparedValue=Integer.parseInt(prepared.get(0).toString());
				int deliveredValue=Integer.parseInt(delivered.get(0).toString());
				if(preparedValue!=0){
					delPerc = deliveredValue/preparedValue;
				}
				else{
					delPerc=0.0f;
				}
			}
			dmcCentralTo.setShortApprovalPre(delPerc);
		}
		String fdmDetails=getFdmFromFranchEmp(deliveryInformation).toString();
		dmcCentralTo.setFdmDetails(fdmDetails);
		return dmcCentralTo;

	}//end of fdm calculation

	/**
	 * This is used to calculate fdm total for lite ,pep,vas.
	 *
	 * @param deliveryInformation the delivery information
	 * @return the fdm from franch emp
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws NullPointerException the null pointer exception
	 */
	private StringBuilder getFdmFromFranchEmp(List deliveryInformation)throws CGBusinessException,CGSystemException,NullPointerException{
		StringBuilder fdm=new StringBuilder();
		Integer fdmtotallite=0;
		Integer fdmtotalPep=0;
		Integer fdmtotalVas=0;

		if(deliveryInformation!=null && !deliveryInformation.isEmpty()){ 

			for(int siz=0;siz<deliveryInformation.size();siz++){

				Object[] obj = (Object[])deliveryInformation.get(siz);

				if((StringUtil.checkForNull(obj[0])?Integer.valueOf(obj[0].toString()):0)==1)
				{
					fdmtotallite=StringUtil.checkForNull(obj[1])?Integer.valueOf(obj[1].toString()):0;

					//lite-end		
				} else if ((StringUtil.checkForNull(obj[0])?Integer.valueOf(obj[0].toString()):0)==2){
					fdmtotalPep=StringUtil.checkForNull(obj[1])?Integer.valueOf(obj[1].toString()):0;

				}else if ((StringUtil.checkForNull(obj[0])?Integer.valueOf(obj[0].toString()):0)==3){
					fdmtotalVas=StringUtil.checkForNull(obj[1])?Integer.valueOf(obj[1].toString()):0;

				}//end calculation for a  product


			}//end of for loop--end of calculations for all products

			fdm.append(fdmtotallite.toString());
			fdm.append(BookingConstants.DELIM_CHAR);
			fdm.append(fdmtotalPep.toString());
			fdm.append(BookingConstants.DELIM_CHAR);
			fdm.append(fdmtotalVas.toString());
		}
		else{
			fdm.append("0");
			fdm.append(BookingConstants.DELIM_CHAR);
			fdm.append("0");
			fdm.append(BookingConstants.DELIM_CHAR);
			fdm.append("0");
		}

		return fdm;
	}
	
	/**
	 * Combine date time.
	 *
	 * @param date the date
	 * @param hh the hh
	 * @param min the min
	 * @return the date
	 */
	private Date combineDateTime(String date, String hh,String min){
		Date dateObj=	DateFormatterUtil
			.getDateFromStringDDMMYYY(date);
		int t = decodeTime(hh+":"+min);	 
			return  new Date(dateObj.getTime()+t);
		}
		// Decodes a time value in "hh:mm:ss" format and returns it as milliseconds since midnight.
		/**
		 * Decode time.
		 *
		 * @param s the s
		 * @return the int
		 */
		private  int decodeTime (String s)  {
		   SimpleDateFormat f = new SimpleDateFormat("HH:mm");
		   TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		   f.setTimeZone (utcTimeZone);
		   f.setLenient (false);
		   ParsePosition p = new ParsePosition(0);
		   Date d = f.parse(s,p);
		   if (d == null || !isRestOfStringBlank(s,p.getIndex())){
			   return 0;
		   }
		   return (int)d.getTime();
		   }
		// Returns true if string s is blank from position p to the end.
		/**
		 * Checks if is rest of string blank.
		 *
		 * @param s the s
		 * @param p the p
		 * @return true, if is rest of string blank
		 */
		private static boolean isRestOfStringBlank (String s, int p) {
		   while (p < s.length() && Character.isWhitespace(s.charAt(p))){
			   p++;
		   }
		   return p >= s.length(); 
		   }
		

}
