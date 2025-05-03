package basic.overridesequal;

public class Employee {
	{System.out.println("instance block");}
	
	static {System.out.println("static block");}
	
	Employee(){
		System.out.println("constructor");
	}

	public int hashCode(){
		return 1;
	}
	
	public boolean equals(Employee e){
		return true;
	}
}
