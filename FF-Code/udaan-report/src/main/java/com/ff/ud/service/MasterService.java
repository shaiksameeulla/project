package com.ff.ud.service;

import java.util.List;

import com.ff.ud.domain.OfficeDO;



public interface MasterService {

	
	public List<OfficeDO>  getHUBOffices(List<Integer>  cityId);

	public List<Integer> getCityesIdByRegionCode(String regCode);
}
