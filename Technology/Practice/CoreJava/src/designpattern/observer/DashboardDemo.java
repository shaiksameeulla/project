package designpattern.observer;

public class DashboardDemo {

	public static void main(String[] args) {
		Observer obs= new ObserverImpl1("topic1");
		Observer obs1= new ObserverImpl1("topic2");
		Subject tc= new Topic();
		tc.addObserver(obs);
		tc.addObserver(obs1);
		tc.update("sami");

	}

}
