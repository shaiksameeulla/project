/**
 * 
 */
package designpattern;

import java.io.Serializable;

/**
 * @author mohammes
 *
 */
public class SingletonDoubleChecking implements Serializable{
	private transient static SingletonDoubleChecking singleton =null;
	private SingletonDoubleChecking(){
		throw new RuntimeException();
	}
	
	 public static SingletonDoubleChecking getInstance(){
		
		if(singleton == null){
			synchronized(SingletonDoubleChecking.class){
				if(singleton == null){
					singleton= new SingletonDoubleChecking();
				}
			}
			
		}
		
		return singleton;
	}
	 
	 protected Object readResolve() {
		    return getInstance();
		}

}
