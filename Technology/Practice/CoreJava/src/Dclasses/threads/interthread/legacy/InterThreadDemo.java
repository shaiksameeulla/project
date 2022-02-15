package Dclasses.threads.interthread.legacy;

import java.util.ArrayList;
import java.util.List;

public class InterThreadDemo {

	public static void main(String[] args) {
		
		ThreadGroup tg=	Thread.currentThread().getThreadGroup();
		System.out.println("Group Name:"+tg.getName() +" parent"+tg.getParent().getName());
		System.out.println("Main Thread Name :"+Thread.currentThread().getName());
		System.out.println("TG Priority"+tg.getMaxPriority());
		List l = new ArrayList<>();
		
		Producer p= new Producer(l);
		System.out.println(p.getPriority());
		Consumer c= new Consumer(l);
				
		

	}

}
