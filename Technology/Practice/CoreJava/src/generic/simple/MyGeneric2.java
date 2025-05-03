package generic.simple;

public class MyGeneric2<K,V> {
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	K key;
	V value;
	public MyGeneric2(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public MyGeneric2() {
	}
	
	
}
