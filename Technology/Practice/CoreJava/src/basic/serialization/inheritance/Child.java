package basic.serialization.inheritance;

import java.io.Serializable;

public class Child  extends Base implements Serializable{
	
	Child(){
		System.out.println("Child constructor");
	}
	
	private String childObj;
	private String childObj2;
	public String getChildObj() {
		return childObj;
	}
	public void setChildObj(String childObj) {
		this.childObj = childObj;
	}
	public String getChildObj2() {
		return childObj2;
	}
	public void setChildObj2(String childObj2) {
		this.childObj2 = childObj2;
	}
	@Override
	public String toString() {
		return "Child [childObj=" + childObj + ", childObj2=" + childObj2 + "]";
	}

}
