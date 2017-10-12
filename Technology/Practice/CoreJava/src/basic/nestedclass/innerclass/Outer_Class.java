package basic.nestedclass.innerclass;

public class Outer_Class {
	private String shadow="outershadow";
	
	public  class Inner_Class{
		private String innerObj="innerrobj";
		private final static String staticInnerObj="staticInnerObj";
		private String shadow="innerrshadow";
		
		public void display(){
			System.out.println("shadow "+shadow);
			System.out.println("shadow(outer) "+Outer_Class.this.shadow);
			System.out.println("staticInnerObj "+staticInnerObj);
		}

	}
	
	public  void display(){
		System.out.println("shadow "+shadow);
		Inner_Class innerObj= new Inner_Class();
		innerObj.display();
	}

}
