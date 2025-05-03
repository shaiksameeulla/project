package com.dsa.hashmap;

public class CustomHashMap2 {
	
	public static class HashMap<K,V>{
		
		private Entry<K, V>[] table; 
		private int bucketSize;
		 
		private int nodesCount;
		
		public HashMap(int bucketSize){
			this.bucketSize =bucketSize;
			table = new Entry[bucketSize];
			
		}
		public HashMap(){
			bucketSize=10;
			table = new Entry[bucketSize];
			
		}
		
		
		private class Entry<K,V>{
			K key;
			V value;
			Entry<K,V> next;
			Entry(K key,V value){
				this(key,value,null);
			}
			Entry(K key,V value,Entry next){
				this.key=key;
				this.value = value;
				this.next =next;
			}
			
			
			public String toString() {
				return "key: "+key+" Value : "+value;
			}
		}
		
		private int getHashCode(K key) {
			return Math.abs(key.hashCode());
		}
		private int getBucketIndex(K key) {
			int hashCode = getHashCode(key);
			return hashCode % bucketSize ;
		}
	
		public void put(K key,V value) {
			Entry eput= new Entry<K, V>(key, value);
			int bindex = getBucketIndex(key);
			
			if(table[bindex] == null) {
				table[bindex] =eput;
				nodesCount++;
			}else {
				Entry indexNode=table[bindex];
				Entry prev=null;
				Entry current=indexNode;
				while(current !=null) {
					if(current.key == key) {
						current.value=value;
						break;
					}else {
						prev =current;
						current =current.next;
					}
				}
				if(current ==null) {
					prev.next=eput;
					nodesCount++;
				}
			}
		}
		public Entry get(K key) {
			Entry eput= null;
			int bindex = getBucketIndex(key);
			
			if(table[bindex] == null) {
				return null;
			}else {
				Entry indexNode=table[bindex];
				Entry prev=null;
				Entry current=indexNode;
				while(current !=null) {
					if(current.key == key) {
						eput= new Entry(key, current.value);
						break;
					}else {
						prev=current;
						current =current.next;
					}
				}
				
			}
			return eput;
		}
		public Entry delete(K key) {
			Entry deleted=null;
			int bindex = getBucketIndex(key);
			
			if(table[bindex] == null) {
				return null;
			}else {
				Entry indexNode=table[bindex];
				Entry prev=null;
				Entry current=indexNode;
				while(current !=null) {
					if(current.key == key) {
						if(prev ==null) {
							table[bindex] =current.next;
							deleted=current;
							deleted.next=null;
							nodesCount--;
							break;
						}else {
							prev.next=current.next;
							deleted=current;
							deleted.next=null;
							nodesCount--;
							break;
						}
						
					}else {
						prev=current;
						current =current.next;
					}
				}
				
			}
			return deleted;
		}
		
		public String toString() {
			StringBuilder sb= new StringBuilder();
			for(int i=0;i<table.length;i++) {
				Entry<K, V> entry=table[i];
				while(entry !=null) {
					sb.append("["+entry.key+","+entry.value+"]");
					entry =entry.next;
				}
				
			}
		return sb.toString();
		}
		
	}

	public static void main(String[] args) {
		HashMap<String, Integer> map= new HashMap<>(10);
		
		map.put("Ranm", 100);
		map.put("Kiran", 2110);
		map.put("Raj", 3000);
		map.put("Sure", 400);
		System.out.println(map);
		map.put("Sure", 403450);
		System.out.println(map);
		System.out.println(map.get("Raj"));
		
		  map.put("123123", 1400); 
		  map.put("323123", 2400); 
		  map.put("423123", 3400);
		  map.put("523123", 4400); 
		  map.put("623123", 5400); 
		  System.out.println(map);
		  map.put("2345123", 6400); 
		  map.put("233123", 7400);
		  map.put("323123", 8400);
		  System.out.println(map);
		  
		  System.out.println("Delete: "+map.delete("Raj"));
		  System.out.println(map);
		  map.put("Raj", 3000);
		  System.out.println(map);
		
		
		
	}

}
