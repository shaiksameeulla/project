package basic.overridesequal;

public class ExceptionDemo {
	public static void main(String[] args) {
		System.out.println(f1());
	}
	
	public static int f1(){
		
		try{
			//System.exit(1);
			throw new Error();
			
		}catch(Error sn){
			System.out.println("SuperException");
			return 1;
		}catch(SuperException3 sn){
			System.out.println("SuperException2");
		}finally{
			return 3;
		}
		//System.out.println("sami");
	}
}
