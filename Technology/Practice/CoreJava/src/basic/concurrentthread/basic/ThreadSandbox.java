package basic.concurrentthread.basic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadSandbox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Thread t= new Thread(new RunnableThread());
		t.start();*/
		/*Executor ex= Executors.newSingleThreadExecutor();
		ex.execute(new RunnableThread());
		ex.execute(new ThreadThread("Thread"));*/
		
		Executor ex= Executors.newScheduledThreadPool(2);
		ex.execute(new RunnableThread());
		ex.execute(new ThreadThread("Thread"));
	}

}
