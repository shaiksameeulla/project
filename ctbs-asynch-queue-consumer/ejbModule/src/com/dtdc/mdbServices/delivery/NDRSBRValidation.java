package src.com.dtdc.mdbServices.delivery;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.MessageDetail;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.exception.ParamInfo;
import com.dtdc.domain.transaction.delivery.DeliveryDO;
import com.dtdc.to.delivery.DeliveryRunDisplyTO;

// TODO: Auto-generated Javadoc
/**
 * The Class NDRSBRValidation.
 */
public class NDRSBRValidation {
	
	
	/**
	 * Checks if is valid params.
	 *
	 * @param displyTO the disply to
	 * @param consignmentNo the consignment no
	 * @param runSheetNum the run sheet num
	 * @param headerFileName the header file name
	 */
	public static void isValidParams(DeliveryRunDisplyTO displyTO, String consignmentNo,String runSheetNum,String headerFileName) {
		if(!StringUtils.hasText(consignmentNo) || !StringUtils.hasText(runSheetNum) || !StringUtils.hasText(headerFileName)){
			displyTO.setBusinessException(getCgBusinessExcpetion("E0011",null));
		}
	}
	
	/**
	 * Checks if is valid for drs.
	 *
	 * @param displyTO the disply to
	 * @param deliveryDO the delivery do
	 */
	public static void isValidForDRS(DeliveryRunDisplyTO displyTO, DeliveryDO deliveryDO){
		MessageWrapper mw = new MessageWrapper();
		
		if(deliveryDO == null){
			mw.addMessageDetail(createErrorMessage("E0012",null));
		}
		if(!deliveryDO.getConsgStatus().equalsIgnoreCase("O")){
			mw.addMessageDetail(createErrorMessage("E0013",null));
		}
		CGBusinessException cgb = new CGBusinessException(mw);
		displyTO.setBusinessException(cgb);
	}
	
	/**
	 * Gets the cg business excpetion.
	 *
	 * @param msgKey the msg key
	 * @param params the params
	 * @return the cg business excpetion
	 */
	public static CGBusinessException getCgBusinessExcpetion(String msgKey,ArrayList<ParamInfo> params) {
		MessageDetail msgDetail = new MessageDetail();
		msgDetail.setMessageKey(msgKey);
		msgDetail.setParams(params);
		msgDetail.setMessageType(MessageType.Error);
		MessageWrapper mw = new MessageWrapper();
		mw.addMessageDetail(msgDetail);
		CGBusinessException cgb = new CGBusinessException(mw);
		return cgb;
	}
	
	/**
	 * Creates the error message.
	 *
	 * @param msgKey the msg key
	 * @param params the params
	 * @return the message detail
	 */
	public static MessageDetail createErrorMessage(String msgKey,ArrayList<ParamInfo> params){
		MessageDetail msgDetail = new MessageDetail();
		msgDetail.setMessageKey(msgKey);
		msgDetail.setParams(params);
		msgDetail.setMessageType(MessageType.Error);
		return msgDetail;
	}

}
