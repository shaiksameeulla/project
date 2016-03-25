package com.report;

import java.util.ArrayList;
import java.util.List;

import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;

public class CommonReportWrapperTO {
	List<RegionTO> regionTO = new ArrayList<RegionTO>();
	List<CityTO>   cityTO = new ArrayList<CityTO>();
	List<OfficeTO> officeTO = new ArrayList<OfficeTO>();
	
	
	public List<RegionTO> getRegionTO() {
		return regionTO;
	}
	public List<CityTO> getCityTO() {
		return cityTO;
	}
	public List<OfficeTO> getOfficeTO() {
		return officeTO;
	}
}
