package Dclasses.threads.interthread.legacy;

import java.util.List;

import javax.xml.ws.Response;

public class Consumer extends Thread{
	
	List l;
	
	Consumer(List l){
		this.l = l;
		this.start();
	}
	public void run(){
		consume();
	}
	public synchronized void consume() {
		while(true){
			
			if(l.isEmpty()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				System.out.println("Consumed :"+l.remove(0));
				
				notify();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				
		}
	}

}
