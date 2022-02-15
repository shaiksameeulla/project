package basic.nestedclass.localclass;

public class OuterClass_local {
	private int a=10;
	private int b=10;
	private int outerObj=30;
	static int stticvar=10;
	
	public void dispaly(final int a, final int b){
		final int dispObj=30;
		 int dispObj1=30;
		
		class localClass{
			 int innerObje=30;
			static final int innerObje1=30;
			public localClass(){
				System.out.println("localClass constructor"+a);
				System.out.println("outerObj"+outerObj);
				System.out.println("dispObj"+dispObj);
				System.out.println("dispObj1"+dispObj1);//not valid in Jdk7
			}
			int i=10;
			void display(){
				System.out.println("i from localClass"+i);
				System.out.println("a param :"+a);
				System.out.println("b param :"+b);
				System.out.println("Outer a : "+OuterClass_local.this.a);
				System.out.println("outerObj"+outerObj);
			}
			
			void display(int x){
				System.out.println(i);
				System.out.println(a);
				System.out.println(x);
			}
		}
		localClass loc= new localClass();
		loc.display();
	}
	
	

}
