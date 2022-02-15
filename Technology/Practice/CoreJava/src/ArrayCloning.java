
public class ArrayCloning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a[][]={{1020},
				    {30,40,50}};
		int copy[][]=a.clone();
		copy[0][0]=99;
		System.out.println(copy[0][0]);
		
		copy[1]= new int[]{300,400,500};
		System.out.println(copy[1][1]);

	}

}
