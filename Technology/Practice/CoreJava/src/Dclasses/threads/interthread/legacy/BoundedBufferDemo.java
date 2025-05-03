package Dclasses.threads.interthread.legacy;

import java.util.Date;

public class BoundedBufferDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoundedBuffer bb= new BoundedBuffer();
		
		Thread producer= new Thread( new Runnable(){

			@Override
			public void run() {
				produce(bb);
				
			}
			
		}
		,"Producer");
		
		Thread consumer= new Thread( new Runnable(){

			@Override
			public void run() {
				consume(bb);
				
			}
			
		}
		,"consumer");
		producer.start();
		consumer.start();
		
		
		
	}
	
	static void  produce(BoundedBuffer bb){
		try {
			for(int i=0;;i++){
				String str=" put ["+i+"] at time["+new Date()+"]";
			bb.put(str);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void consume(BoundedBuffer bb){
		try {
			for(;;){
			String s= (String)bb.take();
			System.out.println("Consumeed :["+s+"]");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
