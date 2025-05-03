package basic.nestedclass.innerclass;

public class Outer_Class_Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Outer_Class outerClass= new Outer_Class();
		Outer_Class.Inner_Class innerclass=outerClass.new Inner_Class();
		outerClass.display();
		innerclass.display();

	}

}
