package com.ff.ud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ff.ud.dao.MasterDAO;
import com.ff.ud.domain.OfficeDO;



@Component
@Service
public class MasterServiceImpl implements MasterService{

	@Autowired private MasterDAO masterDAO;
	
	@Override
	public List<OfficeDO> getHUBOffices(List<Integer>  cityId) {
		
		return masterDAO.getHUBOffices( cityId);
	}

	@Override
	public List<Integer> getCityesIdByRegionCode(String regCode) {
		// SELECT  city_id FROM ff_d_city WHERE region =(SELECT REGION_ID FROM ff_d_region WHERE REGION_CODE='B')
		
		return masterDAO.getCityesIdByRegionCode(regCode);
	}

}
