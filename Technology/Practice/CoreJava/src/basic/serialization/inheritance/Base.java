package basic.serialization.inheritance;

import java.io.Serializable;

public class Base extends SuperBase implements Serializable {
	Base(){
		System.out.println("Base Constructor");
	}
	
	private String baseObj;
	private String baseObj2;
	
	public String getBaseObj() {
		return baseObj;
	}
	public void setBaseObj(String baseObj) {
		this.baseObj = baseObj;
	}
	public String getBaseObj2() {
		return baseObj2;
	}
	public void setBaseObj2(String baseObj2) {
		this.baseObj2 = baseObj2;
	}
	@Override
	public String toString() {
		return "Base [baseObj=" + baseObj + ", baseObj2=" + baseObj2 + "]";
	}

}
