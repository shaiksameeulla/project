package com.example.threads.customThreads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {

	
	private BlockingQueue<Runnable> taskList;
	private WorkerThread[] workerThreads;
	
	public CustomThreadPool(int threads) {
		taskList= new LinkedBlockingQueue<>();
		workerThreads= new WorkerThread[threads];
		for(int i=0;i<threads;i++) {
			workerThreads[i]=new WorkerThread();
			workerThreads[i].start();
		}
		
	}
	public void submit(Runnable r) {
		taskList.add(r);
	}
	public void shutdownImmediately() {
	    for (int i = 0; i < workerThreads.length; i++) {
	    	workerThreads[i].shutdownSignal = true;
	    	workerThreads[i].interrupt(); // this is crucial for this case
	    	workerThreads[i] = null;
	    }
	}
	
	private class WorkerThread extends Thread{
		boolean shutdownSignal = false;

		@Override
		public void run() {
			while(true && !shutdownSignal) {
				
				try {
					Runnable r=taskList.take();
					if(r !=null) {
					r.run();
					}
					} catch (InterruptedException e) {
					   break; // this thread is interrupted. Time to leave this loop
					}
			}

		}


	}
	

}
