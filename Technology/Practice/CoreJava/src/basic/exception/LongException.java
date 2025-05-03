package basic.exception;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongException {
	LongException(){
		LongException.start();
	}
	
	public static void start(){
		throw new IllegalStateException("sami");
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			try{
				try{
					new LongException();
				}catch(Throwable t){
					throw t;
				}
			}catch(Throwable t){
				if(t instanceof IllegalStateException ){
					throw (RuntimeException)t;
				}else{
					throw (IllegalStateException)t;
				}
			}
		}catch(IllegalStateException t){
			System.out.println("IllegalStateException");
		}catch(RuntimeException t){
			System.out.println("RuntimeException");
		}catch(Exception t){
			System.out.println("Exception");
		}catch(Throwable t){
			System.out.println("Throwable");
		}
		Date d = new Date(100);
		System.out.println(d);
		System.out.println(new File("sami.js").isFile());
		
		StringBuffer sb= new StringBuffer("prometric");
		sb.append(new StringBuffer("test"));
		sb.delete(4, 8).insert(2,"se");
		System.out.println(sb);
		
		System.out.printf("12%1$s","13%1$s","14%1$s");
		String s="sadf";
		
		switch(s){
		case "A":
		case "a":
		}
		
		Pattern p= Pattern.compile("samiulla shaik");
		Matcher mp=p.matcher("sami");
		mp.find();
		System.out.println(mp.group());
	}
	
	
	

}
