/**
 * 
 */
package basic.enumex;

/**
 * @author mohammes
 *
 */
public enum Month {
	January,Feburary;
	;
	Month(){
		System.out.println(" constructor");
	}
	
	static void method1(){
		System.out.println("Method1");
			}
	static {System.out.println("static block");}
	{System.out.println("instance block");}
	
	
	
}
