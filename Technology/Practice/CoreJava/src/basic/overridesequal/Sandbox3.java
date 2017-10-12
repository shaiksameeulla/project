package basic.overridesequal;

import java.util.HashSet;
import java.util.Set;

public class Sandbox3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

Set s= new HashSet<>();
s.add("1");
s.add("2");
for(String s1:(Set<String>)s){
	System.out.println(s1);
}

System.out.println(f1());
		
	}

	public static int f1(){
		try{
			return 1;
		}catch(Exception e){
			return 2;
		}finally{
			return 3;
		}
		
	}
}
