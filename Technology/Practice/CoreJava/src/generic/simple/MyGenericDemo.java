package generic.simple;

import java.util.ArrayList;
import java.util.List;

public class MyGenericDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyGeneric<String> sami= new MyGeneric<>();
		MyGeneric<String> sami1= new MyGeneric<String>(10);
		MyGeneric2<String,String> sam3= new MyGeneric2<>();
		sami.setVariable("sami");
		System.out.println(sami.getVariable());
		
		List<? super Integer> superEx= new ArrayList<Number>();
		
	}

}
