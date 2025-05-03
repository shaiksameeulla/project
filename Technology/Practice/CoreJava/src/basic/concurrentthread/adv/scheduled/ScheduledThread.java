package basic.concurrentthread.adv.scheduled;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThread {

	private final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(1);

		   public void beepForAnHourAtFixedRate() {
		     final Runnable beeper = new Runnable() {
		       public void run() { System.out.println("beep "+ new Date());  /*try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/}
		     };
		     final ScheduledFuture<?> beeperHandle =
		       scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
		     scheduler.schedule(new Runnable() {
		       public void run() { beeperHandle.cancel(true); System.out.println("cancelling " +new Date());}
		     }, 1, TimeUnit.MINUTES);
		   }
		   public void beepForAnHourWithFixedDelay() {
			     final Runnable beeper = new Runnable() {
			       public void run() { System.out.println("beep "+ new Date());  /*try {
					Thread.sleep(12000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/}
			     };
			     final ScheduledFuture<?> beeperHandle =
			       scheduler.scheduleWithFixedDelay(beeper, 10, 10, TimeUnit.SECONDS);
			     scheduler.schedule(new Runnable() {
			       public void run() { beeperHandle.cancel(true); System.out.println("cancelling " +new Date());}
			     }, 1, TimeUnit.MINUTES);
			   }

}
