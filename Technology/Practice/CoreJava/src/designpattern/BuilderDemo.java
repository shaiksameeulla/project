package designpattern;

public class BuilderDemo {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Builder.BuilderHelper helper= new Builder.BuilderHelper();
		helper.setVar(2);
		
		
		System.out.println(3+2+"434");
me();
	}
	
	public static int me(){
		try{
			System.out.println("me");
			throw new Exception();
			}catch(Exception e){
				System.out.println("exception");
				return 2;
			}finally{
				System.out.println("exit");
				 System.exit(0);
			}
	}
}
