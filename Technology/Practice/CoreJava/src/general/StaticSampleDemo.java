package general;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaticSampleDemo {

	public static void main(String[] args) {
		StaticSample demp1 = new StaticSample();
		StaticSample demp2 = new StaticSample();
		
		System.out.println(demp1.equals(demp2));
		System.out.println(demp1 == demp2);
		System.out.println( 10 & 40);
		List<String> al= new ArrayList<>(12);
		for(int i=0;i<12;i++){
			al.add("number"+i);
		}
		
	Iterator<String> itr=al.iterator();
	int i=0;
	System.out.println(al);
	while(itr.hasNext()){
		itr.next();
		++i;
		if(i>8 && i<10){
			itr.remove();
		}

	}
	System.out.println(al);
	
	}
}
