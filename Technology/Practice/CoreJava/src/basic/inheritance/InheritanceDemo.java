package basic.inheritance;

public class InheritanceDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//Base b= new SubSub();
//b.f(null);
// boolean bool= new Base() instanceof I;
 //System.out.println(bool);
/*b.display();
int i=1;
i *=10+10;
System.out.println(i);*/
 
 Base base= new Sub();
 base.display();
 System.out.println("-------");
 Sub sub= new Sub();
 sub.display();
 System.out.println("-------");
  base=sub;
  base.display();
  
  Sub sub1 =(Sub)base;
  sub1.display();

}

}
