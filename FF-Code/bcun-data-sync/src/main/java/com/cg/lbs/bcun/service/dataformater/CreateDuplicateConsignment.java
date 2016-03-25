package com.cg.lbs.bcun.service.dataformater;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.DuplicateChildConsignmentDO;
import com.ff.domain.consignment.DuplicateConsignmentDO;

/**
 * The Class CreateDuplicateConsignment.
 *
 * @author narmdr
 */
public class CreateDuplicateConsignment {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreateDuplicateConsignment.class);

	public static void createDuplicateConsignment(
			BcunDatasyncService bcunService, ConsignmentDO consignmentDO) {
		LOGGER.info("CreateDuplicateConsignment::createDuplicateConsignment::START------------>:::::::");
		try {
			LOGGER.trace("CreateDuplicateConsignment::createDuplicateConsignment::start saving of Duplicate Consignment.------------>:::::::");
			DuplicateConsignmentDO duplicateConsignmentDO = convertAndGetDuplicateCN(consignmentDO);
			bcunService.saveOrUpdateTransferedEntity(duplicateConsignmentDO);
			LOGGER.trace("CreateDuplicateConsignment::createDuplicateConsignment::Duplicate Consignment saved.------------>:::::::");
		} catch (Exception e) {
			LOGGER.error("CreateDuplicateConsignment::createDuplicateConsignment::Error in saving of Duplicate Consignment No. :: "
					+ consignmentDO.getConsgNo() + " ------------>:::::::");
			LOGGER.error("Exception happened in createDuplicateConsignment of CreateDuplicateConsignment..."
					, e);
			
		}
		LOGGER.info("CreateDuplicateConsignment::createDuplicateConsignment::END------------>:::::::");
	}

	private static DuplicateConsignmentDO convertAndGetDuplicateCN(
			ConsignmentDO consignmentDO) throws CGBusinessException {
		DuplicateConsignmentDO duplicateConsignmentDO = new DuplicateConsignmentDO();
		CGObjectConverter.copyDO2DO(consignmentDO, duplicateConsignmentDO);

		setChildCNs(consignmentDO, duplicateConsignmentDO);
		
		duplicateConsignmentDO
				.setConsigneeDetails(getConsigneeConsignorFormat(consignmentDO
						.getConsignee()));
		duplicateConsignmentDO
				.setConsignorDetails(getConsigneeConsignorFormat(consignmentDO
						.getConsignor()));

		if (consignmentDO.getDestPincodeId() != null
				&& !StringUtil.isEmptyInteger(consignmentDO.getDestPincodeId()
						.getPincodeId())) {
			duplicateConsignmentDO.setDestinationPincodeId(consignmentDO.getDestPincodeId().getPincodeId());
			//PincodeDO destPincodeDO = new PincodeDO();
			// destPincodeDO.setPincodeId(consignmentDO.getDestPincodeId().getPincodeId());
			//duplicateConsignmentDO.setDestPincodeDO(destPincodeDO);
		}

		if (consignmentDO.getConsgType() != null
				&& !StringUtil.isEmptyInteger(consignmentDO.getConsgType()
						.getConsignmentId())) {
			duplicateConsignmentDO.setConsignmentTypeId(consignmentDO.getConsgType().getConsignmentId());
			//ConsignmentTypeDO consignmentTypeDO = new ConsignmentTypeDO();
			//consignmentTypeDO.setConsignmentId(consignmentDO.getConsgType().getConsignmentId());
			//duplicateConsignmentDO.setConsignmentTypeDO(consignmentTypeDO);
		}

		if (consignmentDO.getCnContentId() != null
				&& !StringUtil.isEmptyInteger(consignmentDO.getCnContentId()
						.getCnContentId())) {
			duplicateConsignmentDO.setCnContentsId(consignmentDO.getCnContentId().getCnContentId());
			//CNContentDO cnContentDO = new CNContentDO();
			//cnContentDO.setCnContentId(consignmentDO.getCnContentId().getCnContentId());
			//duplicateConsignmentDO.setCnContentDO(cnContentDO);

		}

		if (consignmentDO.getCnPaperWorkId() != null
				&& !StringUtil.isEmptyInteger(consignmentDO.getCnPaperWorkId()
						.getCnPaperWorkId())) {
			duplicateConsignmentDO.setCnPaperWorksId(consignmentDO.getCnPaperWorkId().getCnPaperWorkId());
			//CNPaperWorksDO cnPaperWorksDO = new CNPaperWorksDO();
			//cnPaperWorksDO.setCnPaperWorkId(consignmentDO.getCnPaperWorkId().getCnPaperWorkId());
			//duplicateConsignmentDO.setCnPaperWorksDO(cnPaperWorksDO);
		}

		if (consignmentDO.getInsuredBy() != null
				&& !StringUtil.isEmptyInteger(consignmentDO.getInsuredBy()
						.getInsuredById())) {
			duplicateConsignmentDO.setInsuredById(consignmentDO.getInsuredBy().getInsuredById());
			//InsuredByDO insuredByDO = new InsuredByDO();
			//insuredByDO.setInsuredById(consignmentDO.getInsuredBy().getInsuredById());
			//duplicateConsignmentDO.setInsuredByDO(insuredByDO);
		}

		return duplicateConsignmentDO;
	}

	private static void setChildCNs(ConsignmentDO consignmentDO,
			DuplicateConsignmentDO duplicateConsignmentDO)
			throws CGBusinessException {
		if (!StringUtil.isEmptyColletion(consignmentDO.getChildCNs())) {
			Set<DuplicateChildConsignmentDO> duplicateChildConsignmentDOs = new HashSet<>();
			for (ChildConsignmentDO childConsignmentDO : consignmentDO
					.getChildCNs()) {
				DuplicateChildConsignmentDO duplicateChildConsignmentDO = new DuplicateChildConsignmentDO();
				CGObjectConverter.copyDO2DO(childConsignmentDO,
						duplicateChildConsignmentDO);
				duplicateChildConsignmentDOs.add(duplicateChildConsignmentDO);
			}
			duplicateConsignmentDO
					.setDuplicateChildCNs(duplicateChildConsignmentDOs);
		}

	}

	private static String getConsigneeConsignorFormat(
			ConsigneeConsignorDO consigneeConsignorDO) {
		if (consigneeConsignorDO == null) {
			return null;
		}
		
//		Format:-
//		Name : firstName+lastName
//		BusinessName : BusinessName
//		Address : address
//		Phone : phone 
//		Mobile : mobile
//		Email : email
		
		StringBuilder consigneeConsignorBuilder = new StringBuilder(1500);
		
		if (StringUtils.isNotBlank(consigneeConsignorDO.getFirstName())
				&& StringUtils.isNotBlank(consigneeConsignorDO.getLastName())) {
			consigneeConsignorBuilder.append("Name : ")
					.append(consigneeConsignorDO.getFirstName()).append(" ")
					.append(consigneeConsignorDO.getLastName());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);

		} else if (StringUtils.isNotBlank(consigneeConsignorDO.getFirstName())) {
			consigneeConsignorBuilder.append("Name : ").append(
					consigneeConsignorDO.getFirstName());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);

		} else if (StringUtils.isNotBlank(consigneeConsignorDO.getLastName())) {
			consigneeConsignorBuilder.append("Name : ").append(
					consigneeConsignorDO.getLastName());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);
		}

		if (StringUtils.isNotBlank(consigneeConsignorDO.getBusinessName())) {
			consigneeConsignorBuilder.append("Business Name : ").append(
					consigneeConsignorDO.getBusinessName());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);
		}
		if (StringUtils.isNotBlank(consigneeConsignorDO.getAddress())) {
			consigneeConsignorBuilder.append("Address : ").append(
					consigneeConsignorDO.getAddress());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);
		}
		if (StringUtils.isNotBlank(consigneeConsignorDO.getPhone())) {
			consigneeConsignorBuilder.append("Phone : ").append(
					consigneeConsignorDO.getPhone());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);
		}
		if (StringUtils.isNotBlank(consigneeConsignorDO.getMobile())) {
			consigneeConsignorBuilder.append("Mobile : ").append(
					consigneeConsignorDO.getMobile());
			consigneeConsignorBuilder
					.append(CommonConstants.CHARACTER_NEW_LINE);
		}
		if (StringUtils.isNotBlank(consigneeConsignorDO.getEmail())) {
			consigneeConsignorBuilder.append("Email : ").append(
					consigneeConsignorDO.getEmail());
		}
		return consigneeConsignorBuilder.toString();
	}
	
}
