package basic.overridesequal;

public class Sandbox2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
int b=11;
switch(b){
case 4:
	b +=2;
	System.out.println("--4");
case 2:
	b +=2;
	System.out.println("--2");
default :
	b +=2;
	System.out.println("--default");
case 10:
	b +=2;
	System.out.println("--10");
}

System.out.println(b);
		
	}

}
