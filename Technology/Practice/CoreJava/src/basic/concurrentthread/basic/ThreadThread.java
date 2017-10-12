/**
 * 
 */
package basic.concurrentthread.basic;

/**
 * @author mohammes
 *
 */
public class ThreadThread extends Thread {
	public ThreadThread(String name){
		super(name);
				
	}
	public void run() {
        System.out.println(this.getClass().getName());
    }

}
