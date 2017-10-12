package designpattern.observer;

public class ObserverImpl1 implements Observer {
	private String name;
	
	public ObserverImpl1(){
		name ="default";
	}
	public ObserverImpl1(String name){
		this.name =name;
	}

	@Override
	public void update(String msg) {
		System.out.println("msg for ["+name+"] :"+msg);

	}

}
