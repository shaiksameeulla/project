package general;

public class CloseTOZero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(test());
		System.out.println(0x7FFFFFFF);
	}
	
	public static int test(){
		 
        int[] a = {8,-2,-4,5,19,2,-1,1,0};
 
 
        int closeTzero=a[0];//considering array has atleast one element
 
        for (int i = 0; i < a.length-1; i++) {
        	if(Math.abs(a[i])<= Math.abs(a[i+1])){            
        		closeTzero = a[i];      
        	}else{
        		closeTzero =a[i+1];
        	}

        }
 
        return closeTzero;
 
    }

}
