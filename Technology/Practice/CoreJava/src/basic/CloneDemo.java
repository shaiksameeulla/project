package basic;

import basic.serialization.composition.Address;
import basic.serialization.composition.Employee;

public class CloneDemo {
public static void main (String s[]) throws CloneNotSupportedException{
	Employee emp = new Employee();
	
	Address add = new Address();
	add.setCountry("India");
	add.setLocation("Bangalore");
	emp.setAddress(add);
	emp.setEmployeeName("Capgemini");
	Employee cloneedEmp = emp.cloneObject();
	System.out.println("------EMP---------------------------");
	if(emp == cloneedEmp){
		System.out.println("Emp References are same");
		System.out.println(emp);
			}else{
				System.out.println("Emp Clonned objects");
				System.out.println(cloneedEmp);
			}
	if (emp.getAddress() == cloneedEmp.getAddress()){
		System.out.println("Address ref are equal");
	}else{
		System.out.println("Address clonned object");
	}
	
	System.out.println("---------------------------------");
}
}
