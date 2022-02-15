package basic.serialization.composition;

import java.io.Serializable;

public class Employee implements Serializable,Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1229370239535053332L;

	static{System.out.println("static");}
	
	{System.out.println("instance");}
	
	public Employee(){
		System.out.println("employee constructor");
	}
	 String a=toString();
	
	@Override
	public String toString() {
		System.out.println("TO string");
		return "Employee [employeeName=" + employeeName + ", address=" + address + "]";
	}
	private String employeeName;
	transient private Address address;
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Employee cloneObject() throws CloneNotSupportedException{
		Employee clonEmp = (Employee)this.clone();
		clonEmp.setAddress(this.getAddress().cloneObject());
		return clonEmp;
	}
	

}
