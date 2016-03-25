/**
 * 
 */
package com.cg.lbs.bcun.utility;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.cg.lbs.bcun.to.TwoWayWriteDataContentTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ff.domain.booking.BcunBookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryNavigatorDO;
import com.ff.domain.loadmanagement.BcunLoadMovementDO;
import com.ff.domain.manifest.BcunConsignmentReturnDO;
import com.ff.domain.manifest.BcunManifestDO;
//import com.ff.domain.manifest.ManifestProcessDO;
import com.ff.domain.pickup.BcunPickupRunsheetHeaderDO;
import com.ff.domain.pickup.BcunReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.BcunRunsheetAssignmentHeaderDO;
import com.ff.domain.umc.LogInOutDetlDO;
import com.ff.domain.umc.PasswordDO;

/**
 * The Class TwoWayWriteUtil.
 *
 * @author narmdr
 */
public class TwoWayWriteUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(TwoWayWriteUtil.class);

	/**
	 * Gets the two way write process class.
	 *
	 * @param processName the process name
	 * @return the two way write process class
	 */
	public static Class getTwoWayWriteProcessClass(String processName) {
		Class processClass = null;
		switch (processName) {
		case CommonConstants.TWO_WAY_WRITE_PROCESS_REVERSE_PICKUP_REQ:
			processClass = BcunReversePickupOrderBranchMappingDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_RUNSHEET_ASSIGNMENT:
			processClass = BcunRunsheetAssignmentHeaderDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_GENERATE_RUNSHEET:
			processClass = BcunPickupRunsheetHeaderDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_BOOKING:
			processClass = BcunBookingDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT:
			processClass = ConsignmentDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST:
			processClass = BcunManifestDO.class;
			break;
		/*case CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS:
			processClass = ManifestProcessDO.class;
			break;*/
		case CommonConstants.TWO_WAY_WRITE_PROCESS_DISPATCH_RECEIVE:
			processClass = BcunLoadMovementDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_CONSIGNMENT_RETURN:
			processClass = BcunConsignmentReturnDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_DRS:
			processClass = DeliveryDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_DRS_NAVIGATOR:
			processClass = DeliveryNavigatorDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_LOGIN_PASSWORD:
			processClass = PasswordDO.class;
			break;
		case CommonConstants.TWO_WAY_WRITE_PROCESS_LOGIN_HISTORY:
			processClass = LogInOutDetlDO.class;
			break;
		}
		return processClass;
	}

	/**
	 * Gets the json array from cg base d os.
	 *
	 * @param cGBaseDOs the c g base d os
	 * @return the json array from cg base d os
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static String[] getJsonArrayFromCGBaseDOs(List<CGBaseDO> cGBaseDOs) throws IOException, ClassNotFoundException {
		if (cGBaseDOs == null)
			return null;
		LOGGER.trace("TwoWayWriteService::getJsonArrayFromCGBaseDOs::start====>");
		
		String[] jsonObjectArrayStr=new String[cGBaseDOs.size()];
		ObjectMapper mapper = new ObjectMapper();
		
		for (int i=0;i<cGBaseDOs.size();i++) {
			//Creating writer
			StringWriter writer = new StringWriter();
			//Writing content to the writer with the help of mapper
			mapper.writeValue(writer, cGBaseDOs.get(i));
			String jsonString = writer.toString();
			jsonObjectArrayStr[i]=jsonString;
			
			LOGGER.trace("TwoWayWriteService::getJsonArrayFromCGBaseDOs::jsonString : ====>"+jsonString);
		}
		LOGGER.trace("TwoWayWriteService::getJsonArrayFromCGBaseDOs::jsonObjectArrayStr : ====>"+jsonObjectArrayStr);
		LOGGER.trace("TwoWayWriteService::getJsonArrayFromCGBaseDOs::end====>");
		return jsonObjectArrayStr;
	}

	/**
	 * Gets the json str from two way write data content to.
	 *
	 * @param twoWayWriteDataContentTO the two way write data content to
	 * @return the json str from two way write data content to
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String getJsonStrFromTwoWayWriteDataContentTO(
			TwoWayWriteDataContentTO twoWayWriteDataContentTO)
			throws JsonGenerationException, JsonMappingException, IOException {

		// Creating object mapper
		// ObjectMapper mapper = new
		// ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
		// mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		// mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
		// mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);

		// ObjectMapper mapper = new ObjectMapper();
		// Creating writer
		StringWriter writer = new StringWriter();
		// Writing content to the writer with the help of mapper
		mapper.writeValue(writer, twoWayWriteDataContentTO);
		// String jsonString = writer.toString();

		return writer.toString();
	}
	
	/**
	 * Convert two way write data content to to json str.
	 *
	 * @param twoWayWriteDataContentTO the two way write data content to
	 * @param cgBaseDOs the cg base d os
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String convertTwoWayWriteDataContentTOToJsonStr(
			TwoWayWriteDataContentTO twoWayWriteDataContentTO,
			List<CGBaseDO> cgBaseDOs) throws ClassNotFoundException,
			IOException {
		String[] jsonTextArray = getJsonArrayFromCGBaseDOs(cgBaseDOs);
		twoWayWriteDataContentTO.setJsonObjectArrayStr(jsonTextArray);
		String twoWayWriteDataContentTOJsonStr = getJsonStrFromTwoWayWriteDataContentTO(twoWayWriteDataContentTO);
		return twoWayWriteDataContentTOJsonStr;
	}

	
	/**
	 * Convert json str to two way write data content to.
	 *
	 * @param twoWayWriteDataContentTOJSONStr the two way write data content tojson str
	 * @return the two way write data content to
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static TwoWayWriteDataContentTO convertJsonStrToTwoWayWriteDataContentTO(
			String twoWayWriteDataContentTOJSONStr) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
		TwoWayWriteDataContentTO twoWayWriteDataContentTO = (TwoWayWriteDataContentTO)mapper.readValue(twoWayWriteDataContentTOJSONStr, new TypeReference<TwoWayWriteDataContentTO>() {});
		return twoWayWriteDataContentTO;
	}
	
	/**
	 * Gets the process do from json.
	 *
	 * @param jsonString the json string
	 * @param processDOName the process do name
	 * @return the process do from json
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static CGBaseDO getProcessDOFromJson(String jsonString, String processDOName)
			throws JsonParseException, JsonMappingException, IOException {
		if (jsonString == null)
			return null;
		// Mapper class to map string into java object
		ObjectMapper mapper = new ObjectMapper();
		// Creating java type of JSON object
		JavaType type = mapper.getTypeFactory().constructFromCanonical(
				processDOName);
		CGBaseDO cgBaseDO = (CGBaseDO) mapper.readValue(jsonString, type);
		return cgBaseDO;
	}

}
