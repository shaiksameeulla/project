package general;

public class StaticSample {
	public static String sameeulla;	
	static{
		System.out.println("static block");
	}
	
	{
		System.out.println("instance block");
		
	}
	
	public static void m1(String sameeullas){
		sameeulla = sameeullas;
		System.out.println("m1 with args");
	}
	public static void m1(){
		System.out.println("M1");
	}

}
