package basic.concurrentthread.adv.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ForkJoinPool pool = new ForkJoinPool();
		long l[]= new long[]{2,100,-40,98,-4,0,1,7,3,30,11,19,45,24,55,66,44,33,22,11,0,-3,-5,-6};
		pool.invoke(new SortTask(l));

	}
	
	static class SortTask extends RecursiveAction {
		   final long[] array; final int lo, hi;
		   SortTask(long[] array, int lo, int hi) {
		     this.array = array; this.lo = lo; this.hi = hi;
		     System.out.println("constructor");
		   }
		   SortTask(long[] array) { this(array, 0, array.length); }
		   protected void compute() {
		     if (hi - lo < THRESHOLD)
		       sortSequentially(lo, hi);
		     else {
		       int mid = (lo + hi) >>> 1;
		       invokeAll(new SortTask(array, lo, mid),
		                 new SortTask(array, mid, hi));
		       merge(lo, mid, hi);
		       for(Long l:array)
		       System.out.println(l);
		     }
		   }
		   // implementation details follow:
		   static final int THRESHOLD = 5;
		   void sortSequentially(int lo, int hi) {
		     Arrays.sort(array, lo, hi);
		   }
		   void merge(int lo, int mid, int hi) {
		     long[] buf = Arrays.copyOfRange(array, lo, mid);
		     for (int i = 0, j = lo, k = mid; i < buf.length; j++)
		       array[j] = (k == hi || buf[i] < array[k]) ?
		         buf[i++] : array[k++];
		   }
		 }

}
