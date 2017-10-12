package basic.enumex;

public class ColourDemo {

	public static void main(String[] args) {

		Colour c[]= Colour.values();
		for(Colour c1:c){
			c1.info();
			System.out.println(c1.ordinal());
			System.out.println("Name:"+c1.name());
			System.out.println("Tostring:"+c1.toString());
			System.out.println("name == tostring :"+c1.name().equals(c1.toString()));
		}
		
	}

}
