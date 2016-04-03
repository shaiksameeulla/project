package com.ff.ud.utils;
import java.util.HashMap;
import java.util.Map;

import com.ff.ud.constants.XMLConstant;

public class OpsmanSQLUtils {

	public static void main(String[] args) {
		
		Map<String,String> map=getDRSSelectQuey();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			//System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
			System.out.println(entry.getKey()+" - "+entry.getValue());
		}
	}
	
	
	public static  HashMap<String, String> getDRSSelectQuey(){
		HashMap<String, String> drsQueryMap=new HashMap<String,String>();
		
		//Hyderabad Region 
		//String selectClause =" SELECT bd.CON_NO AS consignmentNumber,bd.[LOAD] AS loadNumber,bd.DRS_NO AS drsNumber,bd.DRS_DATE AS drsDate,bd.U_EMP_NO AS employeeId,bd.CON_TYPE AS consignmentType,bd.Duplicate AS ypDrs,bd.[Status]AS drsStatus,bd.ORIGIN AS cityId,bd.ATTEMPT_NO AS attemptNumber,bp.CON_NO AS consignmentNumber,bp.stcode AS stCode,bp.receiver AS receiverName,bp.tel_no AS contactNumber,ba.CON_NO AS consignmentNumber,ba.BA_CODE AS baCode,bf.M_CON_NO AS mConsignmentNumber,bf.PIECES AS noOfPieces,dn.DRS_NO AS drsNumber,dn.TIME AS time";
		String selectClause=" SELECT  bd.CON_NO As consignmentNo,bd.[LOAD] As loadValue,bd.DRS_NO As drsNo,bd.DRS_DATE  As  drsDate,bd.U_EMP_NO As empNo,bd.CON_TYPE As consType,bd.Duplicate As duplicate,dn.[Status] As status,bd.ORIGIN As origin,bd.ATTEMPT_NO As attemptNO,bp.CON_NO As consignmentNo,bp.stcode As stCode,bp.receiver As receiver,bp.tel_no As telNo,bp.DEL_Date As delTime,ba.CON_NO  As consignmentNo,ba.BA_CODE As baCode,bf.M_CON_NO As consignmentNo,bf.PIECES As noOfPices,dn.DRS_NO As drsno,dn.TIME As time , dn.INSERTTIME As insertTime,dn.branch As branchCode";
		
		drsQueryMap.put(XMLConstant.REGION_A_HYDERABAD,             selectClause +" FROM opsman.dbo.BIADRS bd INNER JOIN opsman.dbo.BIApod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOAFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIADRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_B_MUMBAI,                selectClause +" FROM opsman.dbo.BIBDRS bd INNER JOIN opsman.dbo.BIBpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOBFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIBDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_C_CHENNAI,               selectClause +" FROM opsman.dbo.BICDRS bd INNER JOIN opsman.dbo.BICpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOCFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BICDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_D_DELHI,                 selectClause +" FROM opsman.dbo.BIDDRS bd INNER JOIN opsman.dbo.BIDpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BODFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIDDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_E_KOLKATA,               selectClause +" FROM opsman.dbo.BIEDRS bd INNER JOIN opsman.dbo.BIEpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOEFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIEDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_F_BANGALORE,             selectClause +" FROM opsman.dbo.BIFDRS bd INNER JOIN opsman.dbo.BIFpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOFFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIFDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_G_AHMEDABAD,             selectClause +" FROM opsman.dbo.BIGDRS bd INNER JOIN opsman.dbo.BIGpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOGFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIGDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K,    selectClause +" FROM opsman.dbo.BIIDRS bd INNER JOIN opsman.dbo.BIIpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOIFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIIDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_J_LUCKNOW,               selectClause +" FROM opsman.dbo.BIJDRS bd INNER JOIN opsman.dbo.BIJpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOJFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIJDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_M_PUNE,                  selectClause +" FROM opsman.dbo.BIMDRS bd INNER JOIN opsman.dbo.BIMpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOMFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIMDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_O_HARYANA,               selectClause +" FROM opsman.dbo.BIODRS bd INNER JOIN opsman.dbo.BIOpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOOFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIODRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_P_MADHYA_PRADESH,        selectClause +" FROM opsman.dbo.BIPDRS bd INNER JOIN opsman.dbo.BIPpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOPFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIPDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_R_JAIPUR,                selectClause +" FROM opsman.dbo.BIRDRS bd INNER JOIN opsman.dbo.BIRpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BORFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIRDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_S_SALEM,                 selectClause +" FROM opsman.dbo.BISDRS bd INNER JOIN opsman.dbo.BISpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOSFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BISDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_T_COIMBATORE,            selectClause +" FROM opsman.dbo.BITDRS bd INNER JOIN opsman.dbo.BITpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOTFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BITDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_U_JAMSHEDPUR,            selectClause +" FROM opsman.dbo.BIUDRS bd INNER JOIN opsman.dbo.BIUpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOUFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIUDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_W_UTTAR_PRADESH_WEST,    selectClause +" FROM opsman.dbo.BIWDRS bd INNER JOIN opsman.dbo.BIWpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOWFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIWDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		 
		return drsQueryMap;
		
	}
	
	public static  HashMap<String, String> getBookingSelectQuey(){
		HashMap<String, String> bookingQueryMap=new HashMap<String,String>();
		
		StringBuilder bookingSelectClause=new StringBuilder();
		
		StringBuilder whereClause=new StringBuilder();
		String mumbaiBranchCodeClause=" fm.M_BR_CD in (select o_office_code_1 from FFCLMaster.dbo.ou_office where u_office_code in ('B991','B012','B013','B014','B015','B016','B017','B018','B019','B020','B021','B022','B023','B024','B025','B026','B027','B028','B029','B030','B031','B032','B033','B034','B035','B036','B037','B038','B039','B040','B041','B042','B043','B044','B045','B046','B047','B048','B049','B050','B051','B052','B053','B054','B056','B057','B058','B059','B060','B061','B062','B063','B064','B067','B068','B069','B070'))";
		whereClause.append("  WHERE fm.M_BR_CD IS NOT NULL AND len(fm.M_CON_NO) = 12 and fm.DT_UDAAN IS NULL ");
		//whereClause.append("  and fm.PINCODE in (400064,400703,560085,700001) ");
		whereClause.append("  and "+mumbaiBranchCodeClause );
		
		String whereCustomerCodeClause=" and fm.M_CON_CD IN (SELECT o_customer_code FROM FFCLMaster.dbo.ou_customer)  AND fm.PINCODE !='0'";
		
		bookingSelectClause.append(" SELECT TOP 3 ");
		bookingSelectClause.append(" fm.M_CON_NO As consignmentNo,");
		bookingSelectClause.append(" fm.WEIGHT As weight,");
		bookingSelectClause.append(" fm.BKING_DATE As bookingDate,");
		bookingSelectClause.append(" fm.m_pay_mode As paymentMode,");
		bookingSelectClause.append(" fm.CONTENTS As contents,");
		bookingSelectClause.append(" fm.M_CON_CD As customerCode, ");
		bookingSelectClause.append(" fm.[DECLARE] As declareValue,");
		bookingSelectClause.append(" fm.INSURED As insured,");
		bookingSelectClause.append(" fm.pieces As pieces,");
		bookingSelectClause.append(" fm.M_BR_CD As branchCode,");
		bookingSelectClause.append(" fm.PINCODE As pincode,");
		bookingSelectClause.append(" fm.CONS_TYPE As consType,");
		bookingSelectClause.append(" fm.POLICY_NO As policyNo,");
		bookingSelectClause.append(" fm.REF_NO As refNo,");
		bookingSelectClause.append(" bobo.BREADTH As breadth ,");
		bookingSelectClause.append(" bobo.HEIGHT As height,");
		bookingSelectClause.append(" bobo.LENGTH As length,");
		bookingSelectClause.append(" bobo.VENDORNAME As vendorName ,");
		bookingSelectClause.append(" bobo.COD_AMT As codAmt,");
		bookingSelectClause.append(" bobo.LCT_AMT As lctAmt,  ");
		bookingSelectClause.append(" fm.inserttime As createdDate,     ");
		bookingSelectClause.append(" bobo.origin As origin,    ");
		bookingSelectClause.append(" (fm.SERVICETAX+ fm.INS_CHRG+fm.TOCHRG+fm.FUEL_CHRG+fm.ED_CESS) As price    ");
		
		bookingQueryMap.put(XMLConstant.REGION_B_MUMBAI,                    bookingSelectClause.toString() +" FROM opsman.dbo.BOBFFMAST    fm INNER JOIN opsman.dbo.BOBOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause);//for cash no customer details
		bookingQueryMap.put(XMLConstant.REGION_B_MUMBAI_FOC,                bookingSelectClause.toString() +" FROM opsman.dbo.BOBFFMAST    fm INNER JOIN opsman.dbo.BOBOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+" AND fm.M_CON_CD LIKE '%FOC%' ");//for cash no customer details
		bookingQueryMap.put(XMLConstant.REGION_B_MUMBAI_CASH,               bookingSelectClause.toString() +" FROM opsman.dbo.BOCashFFMAST fm INNER JOIN opsman.dbo.BOCashOUTGO bobo ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause);
		
		
		bookingQueryMap.put(XMLConstant.REGION_A_HYDERABAD,             bookingSelectClause.toString() +" FROM opsman.dbo.BOAFFMAST    fm INNER JOIN opsman.dbo.BOAOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_A_HYDERABAD_FOC,             bookingSelectClause.toString() +" FROM opsman.dbo.BOAFFMAST    fm INNER JOIN opsman.dbo.BOAOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause);
		bookingQueryMap.put(XMLConstant.REGION_B_MUMBAI,                bookingSelectClause.toString() +" FROM opsman.dbo.BOBFFMAST    fm INNER JOIN opsman.dbo.BOBOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_C_CHENNAI,               bookingSelectClause.toString() +" FROM opsman.dbo.BOCFFMAST    fm INNER JOIN opsman.dbo.BOCOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_D_DELHI,                 bookingSelectClause.toString() +" FROM opsman.dbo.BODFFMAST    fm INNER JOIN opsman.dbo.BODOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_E_KOLKATA,               bookingSelectClause.toString() +" FROM opsman.dbo.BOEFFMAST    fm INNER JOIN opsman.dbo.BOEOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_F_BANGALORE,             bookingSelectClause.toString() +" FROM opsman.dbo.BOFFFMAST    fm INNER JOIN opsman.dbo.BOFOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_G_AHMEDABAD,             bookingSelectClause.toString() +" FROM opsman.dbo.BOGFFMAST    fm INNER JOIN opsman.dbo.BOGOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K,    bookingSelectClause.toString() +" FROM opsman.dbo.BOIFFMAST    fm INNER JOIN opsman.dbo.BOIOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_J_LUCKNOW,               bookingSelectClause.toString() +" FROM opsman.dbo.BOJFFMAST    fm INNER JOIN opsman.dbo.BOJOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_M_PUNE,                  bookingSelectClause.toString() +" FROM opsman.dbo.BOMFFMAST    fm INNER JOIN opsman.dbo.BOMOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_O_HARYANA,               bookingSelectClause.toString() +" FROM opsman.dbo.BOOFFMAST    fm INNER JOIN opsman.dbo.BOOOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_P_MADHYA_PRADESH,        bookingSelectClause.toString() +" FROM opsman.dbo.BOPFFMAST    fm INNER JOIN opsman.dbo.BOPOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_R_JAIPUR,                bookingSelectClause.toString() +" FROM opsman.dbo.BORFFMAST    fm INNER JOIN opsman.dbo.BOROUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_S_SALEM,                 bookingSelectClause.toString() +" FROM opsman.dbo.BOSFFMAST    fm INNER JOIN opsman.dbo.BOSOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_T_COIMBATORE,            bookingSelectClause.toString() +" FROM opsman.dbo.BOTFFMAST    fm INNER JOIN opsman.dbo.BOTOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_U_JAMSHEDPUR,            bookingSelectClause.toString() +" FROM opsman.dbo.BOUFFMAST    fm INNER JOIN opsman.dbo.BOUOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause); 
		bookingQueryMap.put(XMLConstant.REGION_W_UTTAR_PRADESH_WEST,    bookingSelectClause.toString() +" FROM opsman.dbo.BOWFFMAST    fm INNER JOIN opsman.dbo.BOWOUTGO bobo    ON (fm.M_CON_NO=bobo.CON_NO) "+whereClause+whereCustomerCodeClause);
		
		
		 
		return bookingQueryMap;
		
	} 
	

	

	public static  HashMap<String, String> getDRSnUMBERSelectQuey(){
		HashMap<String, String> drsQueryMap=new HashMap<String,String>();
		
		//Hyderabad Region 
		String selectClause =" SELECT top 20 bd.DRS_NO ";
		
		drsQueryMap.put(XMLConstant.REGION_A_HYDERABAD,             selectClause +" FROM opsman.dbo.BIADRS bd INNER JOIN opsman.dbo.BIApod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOAFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIADRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_B_MUMBAI,                selectClause +" FROM opsman.dbo.BIBDRS bd INNER JOIN opsman.dbo.BIBpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOBFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIBDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_C_CHENNAI,               selectClause +" FROM opsman.dbo.BICDRS bd INNER JOIN opsman.dbo.BICpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOCFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BICDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_D_DELHI,                 selectClause +" FROM opsman.dbo.BIDDRS bd INNER JOIN opsman.dbo.BIDpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BODFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIDDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_E_KOLKATA,               selectClause +" FROM opsman.dbo.BIEDRS bd INNER JOIN opsman.dbo.BIEpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOEFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIEDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_F_BANGALORE,             selectClause +" FROM opsman.dbo.BIFDRS bd INNER JOIN opsman.dbo.BIFpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOFFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIFDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_G_AHMEDABAD,             selectClause +" FROM opsman.dbo.BIGDRS bd INNER JOIN opsman.dbo.BIGpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOGFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIGDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K,    selectClause +" FROM opsman.dbo.BIIDRS bd INNER JOIN opsman.dbo.BIIpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOIFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIIDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_J_LUCKNOW,               selectClause +" FROM opsman.dbo.BIJDRS bd INNER JOIN opsman.dbo.BIJpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOJFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIJDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_M_PUNE,                  selectClause +" FROM opsman.dbo.BIMDRS bd INNER JOIN opsman.dbo.BIMpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOMFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIMDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_O_HARYANA,               selectClause +" FROM opsman.dbo.BIODRS bd INNER JOIN opsman.dbo.BIOpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOOFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIODRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_P_MADHYA_PRADESH,        selectClause +" FROM opsman.dbo.BIPDRS bd INNER JOIN opsman.dbo.BIPpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOPFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIPDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_R_JAIPUR,                selectClause +" FROM opsman.dbo.BIRDRS bd INNER JOIN opsman.dbo.BIRpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BORFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIRDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_S_SALEM,                 selectClause +" FROM opsman.dbo.BISDRS bd INNER JOIN opsman.dbo.BISpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOSFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BISDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_T_COIMBATORE,            selectClause +" FROM opsman.dbo.BITDRS bd INNER JOIN opsman.dbo.BITpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOTFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BITDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_U_JAMSHEDPUR,            selectClause +" FROM opsman.dbo.BIUDRS bd INNER JOIN opsman.dbo.BIUpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOUFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIUDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		drsQueryMap.put(XMLConstant.REGION_W_UTTAR_PRADESH_WEST,    selectClause +" FROM opsman.dbo.BIWDRS bd INNER JOIN opsman.dbo.BIWpod bp ON (bd.CON_NO=bp.CON_NO) INNER JOIN opsman.dbo.BIBA_DRS ba ON (bp.CON_NO=ba.CON_NO) INNER JOIN opsman.dbo.BOWFFMAST bf ON (ba.CON_NO=bf.M_CON_NO) INNER JOIN opsman.dbo.BIWDRSNO dn ON (bd.DRS_NO=dn.DRS_NO)");
		
		
		
		 
		return drsQueryMap;
		
	}
	
	public static String getDRSNoTableName(String region) {
		
		String tableName="";
		
		if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIADRSNO";
		}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIBDRSNO";
		}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BICDRSNO";
		}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIDDRSNO";
		}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIEDRSNO";
		}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIFDRSNO";
		}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIGDRSNO";
		}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIIDRSNO";
		}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIJDRSNO";
		}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIMDRSNO";
		}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIODRSNO";
		}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIPDRSNO";
		}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIRDRSNO";
		}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BISDRSNO";
		}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BITDRSNO";
		}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIUDRSNO";
		}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIWDRSNO";
		}
		
		return tableName;
	}

	public static String getOutgoTableName(String region,String officeType) {
		
		String tableName="";
		
		if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"AOUTGO";
		}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"BOUTGO";
		}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"COUTGO";
		}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"DOUTGO";
		}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"EOUTGO";
		}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"FOUTGO";
		}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"GOUTGO";
		}
		else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"HOUTGO";
		}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"IOUTGO";
		}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"JOUTGO";
		}
		else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"KOUTGO";
		}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"MOUTGO";
		}
		else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"NOUTGO";
		}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"OOUTGO";
		}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"POUTGO";
		}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"ROUTGO";
		}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"SOUTGO";
		}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"TOUTGO";
		}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"UOUTGO";
		}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"WOUTGO";
		}
		
		return tableName;
	}
/*public static String getBoOutgoTableName(String region,String officeType) {
		
		String tableName="";
		
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"AOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"BOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"COUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"DOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"EOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"FOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"GOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"HOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"IOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"JOUTGO";
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"KOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"MOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"NOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"OOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"POUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"ROUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"SOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"TOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"UOUTGO";
		
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo."+officeType+"WOUTGO";
		
		
		return tableName;
	}
*/
	
public static String getDRSTableName(String region) {
		
		String tableName="";
		if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIADRS";
		}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIBDRS";
		}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BICDRS";
		}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIDDRS";
		}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIEDRS";
		}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIFDRS";
		}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIGDRS";
		}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIIDRS";
		}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIJDRS";
		}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIMDRS";
		}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIODRS";
		}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIPDRS";
		}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIRDRS";
		}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BISDRS";
		}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BITDRS";
		}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIUDRS";
		}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIWDRS";
		}if(XMLConstant.REGION_BA.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIBA_DRS";
		}
		return tableName;
	}
  
public static String getPODTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIAPOD";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIBPOD";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BICPOD";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIDPOD";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIEPOD";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIFPOD";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIGPOD";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIIPOD";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIJPOD";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIMPOD";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIOPOD";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIPPOD";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIRPOD";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BISPOD";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BITPOD";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIUPOD";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BIWPOD";
	}
	return tableName;
}


public static String getFFMastTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOAFFMast";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBFFMast";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBFFMast";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOCFFMast";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BODFFMast";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOEFFMast";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOFFFMast";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOGFFMast";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOIFFMast";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOJFFMast";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOMFFMast";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOOFFMast";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOPFFMast";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BORFFMast";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOSFFMast";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOTFFMast";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOUFFMast";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOWFFMast";
	}
	return tableName;
}

public static  HashMap<String, String> getDSSelectQuey(){
	HashMap<String, String> dsQueryMap=new HashMap<String,String>();

StringBuilder dispatchReceiveSelectClause=new StringBuilder();

dispatchReceiveSelectClause.append(" SELECT TOP 100 ");
dispatchReceiveSelectClause.append(" BP.MBL_NO AS mblNumber, ");
dispatchReceiveSelectClause.append(" BP.BAG_LOCK AS lockNumber, ");
dispatchReceiveSelectClause.append(" BP.WEIGHT AS connectWeight, ");
dispatchReceiveSelectClause.append(" BP.HUB_WEIGHT AS dispatchWeight, ");
dispatchReceiveSelectClause.append(" BP.WEIGHT AS manifestWeight, ");
dispatchReceiveSelectClause.append(" BP.DESP_DATE AS manifestDate, ");
dispatchReceiveSelectClause.append(" BP.Origin AS originOffice, ");
dispatchReceiveSelectClause.append(" BM.DESP_TYPE AS recvTransportMode, ");
dispatchReceiveSelectClause.append(" BM.MBL_NO AS recvGatepassNumber, ");
dispatchReceiveSelectClause.append(" BM.DESP_DATE AS loadingDate, ");
dispatchReceiveSelectClause.append(" BP.INSERTTIME AS createdDate, ");
dispatchReceiveSelectClause.append(" BP.PKT_NO As manifestNo,");
dispatchReceiveSelectClause.append(" BM.DEST_CODE As destinationCity, ");
dispatchReceiveSelectClause.append(" BM.DESP_TYPE As transportModeId ");

dsQueryMap.put(XMLConstant.REGION_A_HYDERABAD,             dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOApacket BP INNER JOIN opsman.dbo.BOAMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_B_MUMBAI,                dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOBpacket BP INNER JOIN opsman.dbo.BOBMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_C_CHENNAI,               dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOCpacket BP INNER JOIN opsman.dbo.BOCMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_D_DELHI,                 dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BODpacket BP INNER JOIN opsman.dbo.BODMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_E_KOLKATA,               dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOEpacket BP INNER JOIN opsman.dbo.BOEMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_F_BANGALORE,             dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOFpacket BP INNER JOIN opsman.dbo.BOFMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_G_AHMEDABAD,             dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOGpacket BP INNER JOIN opsman.dbo.BOGMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K,    dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOIpacket BP INNER JOIN opsman.dbo.BOIMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_J_LUCKNOW,               dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOJpacket BP INNER JOIN opsman.dbo.BOJMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_M_PUNE,                  dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOMpacket BP INNER JOIN opsman.dbo.BOMMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_O_HARYANA,               dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOOpacket BP INNER JOIN opsman.dbo.BOOMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_P_MADHYA_PRADESH,        dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOPpacket BP INNER JOIN opsman.dbo.BOPMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_R_JAIPUR,                dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BORpacket BP INNER JOIN opsman.dbo.BORMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_S_SALEM,                 dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOSpacket BP INNER JOIN opsman.dbo.BOSMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_T_COIMBATORE,            dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOTpacket BP INNER JOIN opsman.dbo.BOTMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_U_JAMSHEDPUR,            dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOUpacket BP INNER JOIN opsman.dbo.BOUMBL BM ON (BP.MBL_NO = BM.MBL_NO)");
dsQueryMap.put(XMLConstant.REGION_W_UTTAR_PRADESH_WEST,    dispatchReceiveSelectClause.toString() +" FROM opsman.dbo.BOWpacket BP INNER JOIN opsman.dbo.BOWMBL BM ON (BP.MBL_NO = BM.MBL_NO)");

 
return dsQueryMap;
}

public static String getPacketTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName= XMLConstant.DATABASE_REF_NAME+".dbo.HOAPacket";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBpacket";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBPacket";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOCPacket";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HODPacket";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOEPacket";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOFPacket";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOGPacket";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOIPacket";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOJPacket";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOMPacket";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOOPacket";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOPPacket";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HORpacket";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOSPacket";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOTPacket";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOUPacket";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOWPacket";
	}
	else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOHPacket";
	}else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOKPacket";
	}else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HONPacket";
	}
	return tableName;
}
public static String getMblTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOAMbl";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBMbl";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBMbl";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOCMbl";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HODMbl";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOEMbl";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOFmbl";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOGMbl";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOIMbl";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOJMbl";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOMMbl";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOOMbl";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOPMbl";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HORMbl";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOSMBl";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOTMbl";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOUMbl";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOWMbl";
	}
	else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOHMbl";
	}else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOKMbl";
	}else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HONMbl";
	}
	return tableName;
}

public static String getCustomerTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOACUSTOMER";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBCUSTOMER";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBCUSTOMER";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOCCUSTOMER";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BODCUSTOMER";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOECUSTOMER";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOFCUSTOMER";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOGCUSTOMER";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOICUSTOMER";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOJCUSTOMER";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOMCUSTOMER";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOOCUSTOMER";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOPCUSTOMER";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BORCUSTOMER";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOSCUSTOMER";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOTCUSTOMER";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOUCUSTOMER";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOWCUSTOMER";
	}
	return tableName;
}
public static String getBobPacketTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOAPacket";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBpacket";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBPacket";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOCPacket";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BODPacket";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOEPacket";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOFPacket";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOGPacket";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOIPacket";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOJPacket";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOMPacket";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOOPacket";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOPPacket";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BORpacket";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOSPacket";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOTPacket";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOUPacket";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOWPacket";
	}
	return tableName;
}

public static String getBranchMblTableName(String region) {
	
	String tableName="";
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOAMbl";
	}if(XMLConstant.REGION_B_MUMBAI_FOC.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBMbl";
	}if(XMLConstant.REGION_B_MUMBAI_CASH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBcashMast";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOBMbl";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOCMbl";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BODMbl";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOEMbl";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOFmbl";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOGMbl";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOIMbl";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOJMbl";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOMMbl";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOOMbl";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOPMbl";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BORMbl";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOSMBl";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOTMbl";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOUMbl";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.BOWMbl";
	}
	return tableName;
}
/**
 * HubToBranch HIPacket table name
 * @param region
 * @return String TableName
 */
public static String getHubToBranchHIinpacketTableName(String region) {
	
	String tableName="";
	
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIAINPACKET";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIBINPACKET";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HICINPACKET";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIDINPACKET";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIEINPACKET";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIFINPACKET";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIGINPACKET";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIIINPACKET";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIJINPACKET";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIMINPACKET";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIOINPACKET";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIPINPACKET";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIRINPACKET";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HISINPACKET";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HITINPACKET";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIUINPACKET";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIWINPACKET";
	}else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIHINPACKET";
	}else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIKINPACKET";
	}else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
			tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HININPACKET";
	}
	
	return tableName;
}
/**
 * HubToBranch HIMBL table name
 * @param region
 * @return String TableName
 */
public static String getHubToBranchHIMBLTableName(String region) {
	
	String tableName="";
	
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIAMBL";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIBMBL";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HICMBL";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIDMBL";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIEMBL";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIFMBL";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIGMBL";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIIMBL";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIJMBL";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIMMBL";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIOMBL";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIPMBL";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIRMBL";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HISMBL";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HITMBL";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIUMBL";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIWMBL";
	}else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIHMBL";
	}else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIKMBL";
	}else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HINMBL";
	}
	
	return tableName;
}
/**
 * HubToBranch HI INMAST table name
 * @param region
 * @return String TableName
 */
public static String getHubToBranchHIINMASTTableName(String region) {
	
	String tableName="";
	
	if(XMLConstant.REGION_A_HYDERABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIAINMAST";
	}else if(XMLConstant.REGION_B_CORPORATE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIBINMAST";
	}else if(XMLConstant.REGION_C_CHENNAI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HICINMAST";
	}else if(XMLConstant.REGION_D_DELHI.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIDMBL";
	}else if(XMLConstant.REGION_E_KOLKATA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIEINMAST";
	}else if(XMLConstant.REGION_F_BANGALORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIFINMAST";
	}else if(XMLConstant.REGION_G_AHMEDABAD.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIGINMAST";
	}else if(XMLConstant.REGION_I_PUNJAB_AND_J_AND_K.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIIINMAST";
	}else if(XMLConstant.REGION_J_LUCKNOW.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIJINMAST";
	}else if(XMLConstant.REGION_M_PUNE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIMINMAST";
	}else if(XMLConstant.REGION_O_HARYANA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIOINMAST";
	}else if(XMLConstant.REGION_P_MADHYA_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIPINMAST";
	}else if(XMLConstant.REGION_R_JAIPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIRINMAST";
	}else if(XMLConstant.REGION_S_SALEM.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HISINMAST";
	}else if(XMLConstant.REGION_T_COIMBATORE.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HITINMAST";
	}else if(XMLConstant.REGION_U_JAMSHEDPUR.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIUINMAST";
	}else if(XMLConstant.REGION_W_UTTAR_PRADESH_WEST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIWINMAST";
	}else if(XMLConstant.REGION_H_KERALA.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIHINMAST";
	}else if(XMLConstant.REGION_K_HIMACHAL_PRADESH.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HIKINMAST";
	}else if(XMLConstant.REGION_N_NORTH_EAST.equalsIgnoreCase(region)){
		tableName=XMLConstant.DATABASE_REF_NAME+".dbo.HININMAST";
	}
	
	return tableName;
}

	public static String checkSQLInString(String inData){
		if(StringUtils.isNullOrEmpty(inData)){
			return "' '";
		}else {
			return inData;
		}
		
	}

}
