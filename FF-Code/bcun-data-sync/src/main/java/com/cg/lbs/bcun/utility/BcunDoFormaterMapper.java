package com.cg.lbs.bcun.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.to.InboundConfigPropertyTO;
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;

public class BcunDoFormaterMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BcunDoFormaterMapper.class);
	private static List<InboundConfigPropertyTO>  inboundList;
	private static List<OutboundConfigPropertyTO>  outboundList;
	
	private static Map<String, String> inboundMap;
	
	public static Map<String, String> getInboundFormaters() {
		LOGGER.debug("BcunDoFormaterMapper::getInboundFormaters :: START with inboundMap:["+(CGCollectionUtils.isEmpty(inboundMap)?"Empty/null":inboundMap.size())+"]");
		if(CGCollectionUtils.isEmpty(inboundMap)){
			inboundMap= new HashMap<String, String>(); 
		}
		if(CGCollectionUtils.isEmpty(inboundList)) {
			inboundList = InboundPropertyReader.getInboundConfigProperty();
		}
		
		if(!CGCollectionUtils.isEmpty(inboundList)){
			LOGGER.debug("BcunDoFormaterMapper::getInboundFormaters :: Preparing InboundConfigPropertyTO");
			for(InboundConfigPropertyTO inTo : inboundList) {
				String formater = inTo.getDataFormater();
				String doName = inTo.getDoName();
				if(!StringUtil.isStringEmpty(doName) && !StringUtil.isStringEmpty(formater)){
					inboundMap.put(doName.trim(), formater.trim());
				}
			}
		}
		LOGGER.debug("BcunDoFormaterMapper::getInboundFormaters :: ENDs with inboundMap:["+(CGCollectionUtils.isEmpty(inboundMap)?"Empty/null":inboundMap)+"]");
		
		//FIXME need to remove
		if(inboundMap!=null){
			for(Map.Entry<String, String> entry:inboundMap.entrySet()){
				LOGGER.trace("Key :["+entry.getKey()+"] value :["+entry.getValue()+"]");
			}
		}
		return inboundMap;
	}
}
