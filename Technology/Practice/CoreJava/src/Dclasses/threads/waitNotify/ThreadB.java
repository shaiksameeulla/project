package Dclasses.threads.waitNotify;

public class ThreadB extends Thread {
	int total=0;
	synchronized public void run(){
		System.out.println("ThreadB started");
		for(int i=1;i<=100;i++){
			total= total+i;
		}
		System.out.println("ThreadB total completed");
		this.notify();
		System.out.println("ThreadB notified");
	}

}
