package basic.concurrentthread.adv.scheduled;

import java.util.Date;

public class ScheduledDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("beepForAnHourAtFixedRate");
		ScheduledThread sd= new ScheduledThread();
		System.out.println(" task started "+ new Date());
		sd.beepForAnHourAtFixedRate();
		System.out.println(" Main task END "+ new Date());
	}

}
