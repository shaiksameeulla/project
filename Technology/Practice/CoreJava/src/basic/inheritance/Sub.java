package basic.inheritance;

public class Sub  extends Base implements I{
	
	Sub(){
		System.out.println("Sub");
	}
	
	public static void display(){
		System.out.println("Sub display");
	}
	
}
