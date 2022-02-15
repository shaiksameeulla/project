package basic.concurrentthread.basic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadSandbox2 implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*Thread t= new Thread(new RunnableThread());
		t.start();*/
		/*Executor ex= Executors.newSingleThreadExecutor();
		ex.execute(new RunnableThread());
		ex.execute(new ThreadThread("Thread"));*/
		
		/*Executor ex= Executors.newScheduledThreadPool(2);
		ex.execute(new RunnableThread());
		ex.execute(new ThreadThread("Thread"));*/
Thread t = new Thread( new ThreadSandbox2());
t.start();
System.out.println("begin");
t.join();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("run");
		
	}

}
