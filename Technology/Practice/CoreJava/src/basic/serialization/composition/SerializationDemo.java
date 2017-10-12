package basic.serialization.composition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SerializationDemo {
	
	public static void main(String s[]) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		Employee emp = Employee.class.newInstance();
		System.out.println(emp);
		Constructor<Employee> constructor = Employee.class.getConstructor();
		Employee emp3 = constructor.newInstance();
		
		System.out.println("emp3"+emp3);
		Employee e= new Employee();
		/*String s1="sami";
		String s22=s1.intern();
		System.out.println(s1==s22);*/
		e.setEmployeeName("sami");
		Address ad= new Address();
		ad.setLocation("Bangalore");
		e.setAddress(ad);
		ad.setCountry("india");
		serialize(e, "sami.txt");
		System.out.println("--------Serialization completed---------- ");
		Employee e2= (Employee)deserialize("sami.txt");
		System.out.println(e2.toString());
		
				
	}
	
	// deserialize to Object from given file
		public static Object deserialize(String fileName) throws IOException,
				ClassNotFoundException {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			ois.close();
			return obj;
		}

		// serialize the given object and save it to file
		public static void serialize(Object obj, String fileName)
				throws IOException {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);

			fos.close();
		}

}
