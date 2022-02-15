package general.comparator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Employee> emplist = new ArrayList<>(10);
		Calendar currentDate=Calendar.getInstance();
		
		currentDate.set(2001, 1, 29);
		emplist.add(new Employee(201,currentDate.getTime(),"stev"));
		currentDate.set(2000, 1, 29);
		
		emplist.add(new Employee(100,currentDate.getTime() ,"stev"));
		currentDate.set(2000, 1, 29);
		
		emplist.add(new Employee(220,currentDate.getTime(),"stev"));
		currentDate.set(2021, 1, 29);
		
		emplist.add(new Employee(21,currentDate.getTime(),"stev"));
		currentDate.set(2010, 1, 29);
		
		emplist.add(new Employee(10,currentDate.getTime(),"stev"));
		System.out.println(emplist);
		Collections.sort(emplist, new Comparator<Employee>() {

			@Override
			public int compare(Employee o1, Employee o2) {
				int result=o1.getHireDate().compareTo(o2.getHireDate());
				System.out.println("o1: ["+o1 +"] o2"+o2);
						if(result !=0){
							return result;
						}
				return o1.getEmpId().compareTo(o2.getEmpId());
			}
		});
		
		System.out.println(emplist);

	}

	
}
