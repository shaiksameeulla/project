package basic;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

import generic.simple.Employee;

public class Sandbox {

	private static void m1(){
		System.out.println("m1");
	}
	private static void doStuff(String str) {
        int var = 4;
        if (var == str.length()) {
            System.out.print(str.charAt(--var) + " ");
        }
        else {
            System.out.print(str.charAt(0) + " ");
        }
    }
	public static void main(String[] args) throws InterruptedException {
		doStuff("abcd");
        doStuff("efg");
        doStuff("hi");
		/*Thread.sleep(1);
		m1();
		LinkedList l= new LinkedList();
		l.add(new Employee());
		l.add("ram");
		Collections.sort(l);
		System.out.println(l);
		*/// TODO Auto-generated method stub
		/*Set<String> setlIst= new HashSet<>(2);
		setlIst.add("sss");
		setlIst.add("ss1s");
		for(String s:setlIst){
			System.out.println(s);
		}*/
		//Employee emp = new Employee();
		//Employee emp2 = new Employee();
		
		/*LinkedList<String> list = new LinkedList<String>();
        list.add("BbB1");
        list.add("bBb2");
        list.add("bbB3");
        list.add("BBb4");
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        for (String str : list) {
            System.out.print(str + ":");
        }*/
		
		/*List list = new LinkedList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        
        Collections.reverse(list);
        Iterator iter = list.iterator();
        
        for (Object o : iter) {
            System.out.print(o + " ");
        }
	*/
		
		int i = 10;
		      while (i++ <= 10) {
		          i++;
		      }
		      System.out.print(i);
		      System.out.println(" \nsecond");
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2009);
		c.set(Calendar.MONTH, 6);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String formattedDate = df.format(c.getTime());
		System.out.println(formattedDate);	
	
	}

}
