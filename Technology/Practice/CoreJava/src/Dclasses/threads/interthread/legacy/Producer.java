package Dclasses.threads.interthread.legacy;

import java.util.List;

public class Producer extends Thread {
	
	int counter=0;
	List l;
	Producer(List l){
		this.l =l;
		this.start();
	}
	
	public void run(){
		produce();
	}

	public synchronized void produce(){
		while(true){
			
			if(!l.isEmpty()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Producing :"+counter);
			
			l.add(counter++);
			System.out.println("notifying");
			notify();
			;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
	}
}
