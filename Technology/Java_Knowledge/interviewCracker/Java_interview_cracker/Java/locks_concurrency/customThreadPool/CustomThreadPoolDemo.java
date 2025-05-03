package com.example.threads.customThreads;

public class CustomThreadPoolDemo {

	public static void main(String[] args) {
		CustomThreadPool pool=new CustomThreadPool(20);
		for(int i=0;i<3;i++) {
		pool.submit(()->{System.out.println("Work["+Thread.currentThread().getName()+"]");});
		}

	}

}
