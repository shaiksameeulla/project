package basic.serialization.inheritance;

import java.io.Serializable;

public class SuperBase {
	SuperBase(){
		System.out.println("SuperBase constructor");
	}
	
	private String sbaseObj;
	private String sbaseObj2;
	public String getSbaseObj() {
		return sbaseObj;
	}
	public void setSbaseObj(String sbaseObj) {
		this.sbaseObj = sbaseObj;
	}
	public String getSbaseObj2() {
		return sbaseObj2;
	}
	public void setSbaseObj2(String sbaseObj2) {
		this.sbaseObj2 = sbaseObj2;
	}
	
	

}
