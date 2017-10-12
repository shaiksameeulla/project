package basic;

class Vehicle {
    public void printSound() {
        System.out.print("vehicle");
    }
}

class Car extends Vehicle {
    public void printSound() {
        System.out.print("car");
    }
}

class Bike extends Vehicle {
    public void printSound() {
        System.out.print("bike");
    }
}
public class Sandbox3 {
	
	
	 public static void waitForSomething() throws InterruptedException {
		 Bike o = new Bike();
	        synchronized (o) {
	           
	            o.notify(); 
	            o.wait();
	            System.out.println("sss");
	        }
	    }

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		waitForSomething();
		System.out.println(System.getProperties());
		System.out.println( System.getProperty("user.name"));
		Vehicle v = new Car();
		
        Car c = (Car) v;
        
        
        
        v.printSound();
        c.printSound();

	}

}
