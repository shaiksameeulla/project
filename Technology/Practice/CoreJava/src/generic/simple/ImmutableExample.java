/**
 * 
 */
package generic.simple;

import java.util.HashMap;

/**
 * @author mohammes
 *
 */
public class ImmutableExample {
	
	public static void main(String[] args) {
		System.out.println("smi");
		Employee e= new Employee();
		e.setEmpName("sami");
		e.setEmpNo("31913");
		HashMap<Employee, Integer> hasmap= new HashMap<>(1);
		hasmap.put(e, 10);
		e.setEmpName("samieeuasdfasd");
		System.out.println(hasmap.get(e));
		
	}
	
	

}
