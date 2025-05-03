/**
 * 
 */
package designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mohammes
 *
 */
public class Topic implements Subject {
	
	private volatile List<Observer> list=null;
	private volatile boolean statusChanged=false;
	
	private Object mutex= new Object();
	public Topic(){
		list = new ArrayList<>();
	}
	/* (non-Javadoc)
	 * @see designpattern.observer.Subject#addObserver(designpattern.observer.Observer)
	 */
	@Override
	public synchronized boolean addObserver(Observer obs) {
		boolean added= false;
		if(!list.contains(obs)){
			added = list.add(obs);
		}
		return added;
	}

	/* (non-Javadoc)
	 * @see designpattern.observer.Subject#removeObserver(designpattern.observer.Observer)
	 */
	@Override
	public synchronized boolean removeObserver(Observer obs) {
		// TODO Auto-generated method stub
		return list.remove(obs);
	}

	/* (non-Javadoc)
	 * @see designpattern.observer.Subject#notifyObserver(java.lang.String)
	 */
	@Override
	public void notifyObserver(String msg) {
		List<Observer> list=null;
		synchronized (mutex){
			if(!this.statusChanged){
				return;
			}
			list = new ArrayList(this.list);
		}
		for(Observer obs:list){
			obs.update(msg);
		}
		clearStatus();

	}
	public boolean isStatusChanged() {
		return statusChanged;
	}
	public synchronized  void setStatusChanged() {
		this.statusChanged = true;
	}
	public synchronized  void clearStatus() {
		this.statusChanged = false;
	}
	@Override
	public void update(String msg) {
		
		setStatusChanged();
		notifyObserver(msg);
		
	}

}
