package basic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo3 {

	public static void main(String[] args) {
		String email_patten="^\\S{3,10}@\\S{3,6}.[a-z]{2,3}$";
		 email_patten="^([a-bA-Z0-9]{3,10})@([a-bA-Z]{3,6})\\.([a-z]{2,3})$";
		 //email_patten="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		Pattern p = Pattern.compile(email_patten);
		Matcher m= p.matcher("sasmi@gmai.com");
		
		if(m.matches()){
			System.out.println("matches");
		}else{
			System.out.println("Invalid Email");
		}

	}

}
