package com.dsa.hashmap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomHashMap {
	
	public static class HashMap<K,V>{
		
		private int bucketSize;
		private LinkedList<Node>[] bucket;
		private int nodesCount;
		
		public HashMap(int bucketSize){
			this.bucketSize =bucketSize;
			bucket = new LinkedList[bucketSize];
			for(int i=0;i<bucketSize;i++) {
				bucket[i]= new LinkedList<>();
			}
		}
		public HashMap(){
			bucketSize=10;
			bucket = new LinkedList[bucketSize];
			for(int i=0;i<bucketSize;i++) {
				bucket[i]= new LinkedList<>();
			}
		}
		
		
		private class Node{
			K key;
			V value;
			
			Node(K key,V value){
				this.key=key;
				this.value = value;
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
		private int searchKey(K key,int bucketIndex) {
			LinkedList<Node> ll =bucket[bucketIndex];
			for(int i=0;i<ll.size();i++) {
				if(ll.get(i).key == key) {return i;}
			}
			return -1;
		}
		public void put(K key,V value) {
			int bIndex =getBucketIndex(key);
			int nodeIndex = searchKey(key, bIndex);
			if(nodeIndex == -1) {
				bucket[bIndex].add(new Node(key,value));
				nodesCount++;
				resize();
			}else {
				bucket[bIndex].get(nodeIndex).value=value;
			}
			
		}
		public void resize() {
			double resize=(double)(nodesCount/bucketSize);
			//System.out.println("nodesCount"+nodesCount);
			//System.out.println("resize"+resize);
			if(resize >=1.0f) {
				rehash();
			}
		}
		public void rehash() {
			//System.out.println("rehash");
			LinkedList<Node>[] oldbucket=bucket;
			int oldbucketSize=bucketSize;
			bucketSize = bucketSize*2;
			nodesCount=0;
			bucket= new LinkedList[bucketSize];
			for(int i=0;i<bucketSize;i++) {
				bucket[i]= new LinkedList<>();
			}
			for(int i=0; i<oldbucket.length; i++) {
				LinkedList<Node> ll = oldbucket[i];
				for(int j=0; j<ll.size(); j++) {
				Node node = ll.get(j);
				put(node.key, node.value);
				}
				}
			//System.out.println("rehash--DONE");
		}
		public Node remove(K key) {
			int bIndex =getBucketIndex(key);
			int nodeIndex = searchKey(key, bIndex);
			if(nodeIndex == -1) {
				return null;
			}else {
				return bucket[bIndex].remove(nodeIndex);
			}
			
		}
		public V get(K key) {
			int bIndex =getBucketIndex(key);
			int nodeIndex = searchKey(key, bIndex);
			if(nodeIndex == -1) {
				return null;
			}else {
				return bucket[bIndex].get(nodeIndex).value;
			}
			
		}
		
		public List<K> getKeys(){
			List<K> list= new ArrayList<>();
			for(int i=0;i<bucketSize;i++) {
				list.addAll(bucket[i].stream().map(node->node.key).toList());
			}
			return list;
		}
		
		public String toString() {
			StringBuilder sb= new StringBuilder("[");
			getKeys().forEach( key->				sb.append("["+key+","+get(key)).append("]")			);
			return sb.append("]").toString();
		}
	}

	public static void main(String[] args) {
		HashMap<String, Integer> map= new HashMap<>(10);
		
		map.put("Ranm", 100);
		map.put("Kiran", 2110);
		map.put("Raj", 3000);
		map.put("Sure", 400);
		map.put("123123", 400);
		map.put("323123", 400);
		map.put("423123", 400);
		map.put("523123", 400);
		map.put("623123", 400);
		System.out.println(map);
		map.put("2345123", 400);
		map.put("233123", 400);
		map.put("323123", 400);
		
		
		System.out.println(map.get("Ranm"));
		map.put("Ranm", 1232334);
		System.out.println(map);
		String key="Ranm";
		System.out.println("Removing key :"+key);
		com.dsa.hashmap.CustomHashMap.HashMap.Node removed = map.remove(key);
		System.out.print("Remvoed :");
		System.out.println(removed);

	}

}
