/**
 * 
 */
package basic.threads;

/**
 * @author mohammes
 *
 */
public class MyThread implements Runnable {
String s;
	MyThread(String s){
		this.s = s;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("run"+s);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
new MyThread("sami").start();
	}
	public void start(){
		new Thread(this).start();
	}

}
