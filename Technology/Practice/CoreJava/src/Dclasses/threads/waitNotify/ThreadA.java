package Dclasses.threads.waitNotify;

public class ThreadA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadB b= new ThreadB();
		b.start();
		synchronized(b){
		try {
			b.wait();
			System.out.println("ThreadA waiting");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("total :"+b.total);
	}

}
