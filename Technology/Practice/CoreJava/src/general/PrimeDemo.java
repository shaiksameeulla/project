package general;

public class PrimeDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=29;
System.out.println("is ["+n+"] :"+isPrime(n));
	}

	public static boolean isPrime(int n){
		if(n<=0){
			return false;
		}else if(n==1 || n==2){
			return true;
		}else {
			for(int i=2;i<=n/2;i++){
				if( n%i == 0){
					return false;
				}
			}
		}
		
		return true;
	}
}
