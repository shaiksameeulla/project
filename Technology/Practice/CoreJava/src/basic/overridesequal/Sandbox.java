package basic.overridesequal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sandbox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> l1= new ArrayList<>();
		l1.add("A");
		l1.add("B");l1.add("c");l1.add("d");

		List<String> l2= new ArrayList<>();
		l2.add("A");
		l2.add("M");
		l2.add("P");
		l2.add("d");

		System.out.println(l1);
		System.out.println(l2);
		Iterator itr= l1.iterator();
		while(itr.hasNext()){
			itr.next();
			itr.remove();
			break;
		}
		System.out.println(l1);

	}

}
