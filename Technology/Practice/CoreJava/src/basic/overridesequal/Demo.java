package basic.overridesequal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Demo {

	public static void main(String[] args) {
		
		List<String> x= Arrays.asList("US");
		List<String> y= Arrays.asList("US1");
		
		List<String> xy= new ArrayList<String>();
		xy.addAll(x);
		xy.addAll(y);
		System.out.println(xy);
		// TODO Auto-generated method stub
		Employee e1= new Employee();
		Employee e2= new Employee();
		if(e1 == e2){
			System.out.println("e1==e2");
		}
		if(e1.equals(e2)){
			System.out.println("e1.equal(e2)");
		}
		Object b= e1.equals(e2);
		System.out.println("Object :"+b);
		
		System.out.println("End");
		StringBuffer sb= new StringBuffer("shaiksameeulla");
		System.out.println(sb.substring(1, 3));
		System.out.println(sb.length());
		System.out.println(sb.charAt(3));
				

	}

}
