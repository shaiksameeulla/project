package basic.inheritance;

public class Base {
	
	Base(){
		System.out.println("Base");
	}
	
	public static void display(){
		System.out.println("Base display");
	}
	
	/*public void f(int i){
		System.out.println("f Integer"+i);
	}*/
	/*public void f(Integer i){
		System.out.println("f Integer"+i);
	}*/
	
	/*public void f(char i){
		System.out.println("f char"+i);
	}*/
	public void f(Character i[]){
		System.out.println("f Character[]"+i);
	}
	public void f(char i[]){
		System.out.println("f char[]"+i);
	}
	/*public void f(Boolean i){
		System.out.println("f Boolean"+i);
	}*/
	
public void base1(){
	System.out.println("base1");
}
}
