package basic.serialization.composition;

import java.io.IOException;

public class EmptyClassSerializationDemo {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		EmptyClass ec= new EmptyClass();
		SerializationDemo.serialize(ec, "empty.ser");
		EmptyClass ecn= (EmptyClass)SerializationDemo.deserialize("empty.ser");
		System.out.println(ecn);
	}

}
