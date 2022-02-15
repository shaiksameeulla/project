package designpattern;

import java.io.Serializable;

public class Singleton implements Serializable{
	private Singleton(){
		
	}
	
	private static class SingletonHolder{
		private static final Singleton singleton  = new Singleton();
	}

	
	public static Singleton getInstance(){
		return SingletonHolder.singleton;
	}
}
