package basic.enumex;

public enum Colour {
	RED,BLUE,GREEN{
		
		public void info(){
			System.out.println("Green universal Colour");
		}
	};
	public void info(){
		System.out.println("universal colour");
	}
	
	

}
