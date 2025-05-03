package designpattern.observer;

public interface Subject {
	
	boolean addObserver(Observer obs);
	boolean removeObserver(Observer obs);
	void notifyObserver(String msg);
	void update(String msg);

}
