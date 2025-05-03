package basic.concurrentthread.adv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import basic.concurrentthread.basic.RunnableThread;

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Thread t= new Thread(new RunnableThread());
		t.start();*/
		/*Executor ex= Executors.newSingleThreadExecutor();
		ex.execute(new RunnableThread());
		ex.execute(new ThreadThread("Thread"));*/
		
		ExecutorService ex= Executors.newCachedThreadPool();
		Future<Integer> result = ex.submit(new CallableThread<Integer>());
		Integer retunValue=-1;
		Future<Integer> result2 = ex.submit(new RunnableThread(),retunValue);
		System.out.println(retunValue);
		ex.shutdown();

	}

}
