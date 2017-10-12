package basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

public class Sandbox4 {
	
	

	public static void main(String[] args) throws InterruptedException {
		String s1=10+20+"sami"+30+40;
		String s2="sami"+30+40;
		
		System.out.println(s1);
		System.out.println(s2);
		
		List l= new ArrayList<>();
		l.iterator();
		Set s= new HashSet<>();
		s.iterator();
		Map m= new HashMap<>();
	List lv= new Vector<>();
	Vector v= new Vector();
	v.elements();
	
	Queue q= new PriorityQueue();
	q.add("string");
	q.add("zstring");
	q.add("wstring");
	q.add("astring");
	q.add("astring");
	
	System.out.println(q);
	for(Iterator irt=q.iterator();irt.hasNext();){
		System.out.println(irt.next());
	}
	System.out.println("Peek top element"+q.peek());
	
	System.out.println("GCD :"+gcd(2,10));
		
	}
	//Euclid’s algorithm
	public static int gcd(int p, int q)
	{
	if (q == 0) return p;
	int r = p % q;
	System.out.println("P ["+p+"] Q ["+q+"] remainder [ "+r+"]");
	return gcd(q, r);
	}

}
