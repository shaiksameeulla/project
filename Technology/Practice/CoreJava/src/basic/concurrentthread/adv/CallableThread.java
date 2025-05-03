/**
 * 
 */
package basic.concurrentthread.adv;

import java.util.concurrent.Callable;

/**
 * @author mohammes
 * @param <V>
 *
 */
public class CallableThread<V> implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		for(Integer i=0;i<1_00;i++){
			System.out.println(i);
		}
		return 10;
	}

}
