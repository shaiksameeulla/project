package basic.concurrentthread.adv.scheduled;

import java.util.Date;

public class ScheduledDemoFixedDelay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("beepForAnHourWithFixedDelay");
		ScheduledThread sd= new ScheduledThread();
		System.out.println(" task started "+ new Date());
		sd.beepForAnHourWithFixedDelay();
		System.out.println(" Main task END "+ new Date());
	}

}
