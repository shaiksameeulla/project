/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class CGObjectConverter.
 */
@SuppressWarnings("rawtypes")
public abstract class CGObjectConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CGObjectConverter.class);
	
	/**
	 * Creates the to from domain.
	 * 
	 * @param entity
	 *            the entity
	 * @param to
	 *            the to
	 * @return the CG base to
	 * @throws CGBusinessException
	 *             the CG business exception
	 */
	public static CGBaseTO createToFromDomain(CGBaseDO entity,
			CGBaseTO to) throws CGBusinessException {
		try {
			PropertyUtils.copyProperties(to, entity);
		} catch (Exception obj) {
			LOGGER.error("CGObjectConverter::createToFromDomain::error::", obj);
			throw new CGBusinessException(obj.getMessage());

		}

		return to;
	}
	
	/**
	 * Creates the to from domain.
	 *
	 * @param entity the entity
	 * @param to the to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 */
	public static CGBaseTO copyTO2TO(CGBaseTO source,
			CGBaseTO dest) throws CGBusinessException {
		try {
			PropertyUtils.copyProperties(dest, source);
		} catch (Exception obj) {
			LOGGER.error("CGObjectConverter::copyTO2TO::error::", obj);
			throw new CGBusinessException(obj.getMessage());
		}
		return dest;
	}
	public static CGBaseDO copyDO2DO(CGBaseDO source,
			CGBaseDO dest) throws CGBusinessException {
		try {
			PropertyUtils.copyProperties(dest, source);
		} catch (Exception obj) {
			LOGGER.error("CGObjectConverter::copyDO2DO::error::", obj);
			throw new CGBusinessException(obj.getMessage());
		}
		return dest;
	}

	/**
	 * Creates the domain from to.
	 * 
	 * @param to
	 *            the to
	 * @param entity
	 *            the entity
	 * @return the CG base entity
	 * @throws CGBusinessException
	 *             the CG business exception
	 */
	public static CGBaseDO createDomainFromTo(CGBaseTO to,
			CGBaseDO entity) throws CGBusinessException {
		try {
			PropertyUtils.copyProperties(entity, to);
		} catch (Exception obj) {
			LOGGER.error("CGObjectConverter::createDomainFromTo::error::", obj);
			throw new CGBusinessException(obj.getMessage());
		}
		return entity;
	}

	/**
	 * Creates the domain list from to list.
	 *
	 * @param CGBaseTOList the d tdc base to list
	 * @param CGBaseDO the d tdc base entity
	 * @return the list<? extends CG base entity>
	 * @throws CGBusinessException the CG business exception
	 */
	public static List<? extends CGBaseDO> createDomainListFromToList(
			List<? extends CGBaseTO> CGBaseTOList,
			Class CGBaseDO) throws CGBusinessException {
		List baseEntityList = new ArrayList(CGBaseTOList.size());
		Iterator itr = CGBaseTOList.iterator();
		while (itr.hasNext()) {
			CGBaseTO baseTo = (CGBaseTO) itr.next();
			try {
				CGBaseDO baseEntity = (CGBaseDO) CGBaseDO.newInstance();
				PropertyUtils.copyProperties(baseEntity, baseTo);
				baseEntityList.add(baseEntity);

			} catch (Exception obj) {
				LOGGER.error("CGObjectConverter::createDomainListFromToList::error::", obj);
				throw new CGBusinessException(obj.getMessage());
			}
		}
		return baseEntityList;
	}
	
	/**
	 * Creates the domain set from to set.
	 *
	 */
	public static Set<? extends CGBaseDO> createDomainSetFromToSet(
			Set<? extends CGBaseTO> cgBaseTOSet, Class cgBaseDO) throws CGBusinessException {
		
		Set baseEntitySet = new HashSet(cgBaseTOSet.size());
		Iterator itr = cgBaseTOSet.iterator();
		while (itr.hasNext()) {
			CGBaseTO baseTo = (CGBaseTO) itr.next();
			try {
				CGBaseDO baseEntity = (CGBaseDO) cgBaseDO.newInstance();
				PropertyUtils.copyProperties(baseEntity, baseTo);
				baseEntitySet.add(baseEntity);

			} catch (Exception obj) {
				LOGGER.error("CGObjectConverter::createDomainSetFromToSet::error::", obj);
				throw new CGBusinessException(obj.getMessage());
			}
		}
		return baseEntitySet;
		
	}
	
	/**
	 * @param CGBaseTOList
	 * @param CGBaseDO
	 * @return
	 * @throws CGBusinessException
	 */
	public static Set<? extends CGBaseDO> createDomainSetFromToList(
			List<? extends CGBaseTO> CGBaseTOList,
			Class CGBaseDO) throws CGBusinessException {
		Set baseEntityList = new HashSet(CGBaseTOList.size());
		Iterator itr = CGBaseTOList.iterator();
		while (itr.hasNext()) {
			CGBaseTO baseTo = (CGBaseTO) itr.next();
			try {
				CGBaseDO baseEntity = (CGBaseDO) CGBaseDO.newInstance();
				PropertyUtils.copyProperties(baseEntity, baseTo);
				baseEntityList.add(baseEntity);

			} catch (Exception obj) {
				LOGGER.error("CGObjectConverter::createDomainSetFromToList::error::", obj);
				throw new CGBusinessException(obj.getMessage());
			}
		}
		return baseEntityList;
	}

	/**
	 * Creates the to list from domain list.
	 *
	 * @param CGBaseDOList the d tdc base entity list
	 * @param CGBaseTO the d tdc base to
	 * @return the list<? extends CG base t o>
	 * @throws CGBusinessException the CG business exception
	 */
	public static List<? extends CGBaseTO> createTOListFromDomainList(
			List<? extends CGBaseDO> CGBaseDOList,
			Class CGBaseTO) throws CGBusinessException {
		List baseTOList = new ArrayList(CGBaseDOList.size());
		Iterator itr = CGBaseDOList.iterator();
		while (itr.hasNext()) {
			CGBaseDO baseEntity = (CGBaseDO) itr.next();
			try {
				CGBaseTO BaseTO = (CGBaseTO) CGBaseTO.newInstance();
				PropertyUtils.copyProperties(BaseTO, baseEntity);
				baseTOList.add(BaseTO);

			} catch (Exception obj) {
				LOGGER.error("CGObjectConverter::createTOListFromDomainList::error::", obj);
				throw new CGBusinessException(obj.getMessage());
			}
			
		}
		return baseTOList;
	}
	
	
	/**
	 * Creates the to Set from domain Set.
	 */
	
	public static Set<? extends CGBaseTO> createTOSetFromDomainSet(
			Set<? extends CGBaseDO> CGBaseDOSet,
			Class CGBaseTO) throws CGBusinessException {
		Set baseTOSet = new HashSet();
		Iterator itr = CGBaseDOSet.iterator();
		while (itr.hasNext()) {
			CGBaseDO baseEntity = (CGBaseDO) itr.next();
			try {
				CGBaseTO BaseTO = (CGBaseTO) CGBaseTO.newInstance();
				PropertyUtils.copyProperties(BaseTO, baseEntity);
				baseTOSet.add(BaseTO);

			} catch (Exception obj) {
				LOGGER.error("CGObjectConverter::createTOSetFromDomainSet::error::", obj);
				throw new CGBusinessException(obj.getMessage());
			}
			
		}
		return baseTOSet;
	}
	
}
