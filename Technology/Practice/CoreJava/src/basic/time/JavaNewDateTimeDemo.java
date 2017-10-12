package basic.time;

import java.time.Clock;
import java.time.Instant;

public class JavaNewDateTimeDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instant ins= Instant.now();
		System.out.println(ins);
		Clock clock = Clock.systemUTC();
		System.out.println("clock"+clock.instant());

	}

}
