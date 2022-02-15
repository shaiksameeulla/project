package generic.simple;

public class MyGeneric<T> {
	T variable;
	
	T getVariable(){
		return variable;
	}

	void setVariable(T variable){
		this.variable=variable;
	}
	MyGeneric(){
		
	}
	MyGeneric(int i){
		
	}
	
	public  boolean compare1(MyGeneric<T> p1) {
        return p1.variable == p1.variable;
    }
}
