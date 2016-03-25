package com.ff.admin.coloading.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ff.coloading.CdTO;

public class TestRateUpload {
	
/*	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}*/


	public static void main(String[] args) {
		
		String vendorName = "1001200002-Express Industry Council of India=338";
		
//		vendorName.
		
		String vendorArray[] = vendorName.split("==");
		for(int i=0; i< vendorArray.length; i++) {
			System.out.println(vendorArray[i]);
		}
		
		
/*		String str = "su";
		System.out.println("isNumeric: " + isNumeric(str) );*/
		
/*		String errorMessage = "Go Air , AIRASIA200 , GCR, 600, 49, 29, 19, 19, 19, 19, C, 0, 0, 100, 0.9, 0, 0, 0, 1.35, 216, 0.86, 40, 0.8, 0, 2, 600, 0.1, 4.4, Invalid Content for FuelSurcharge,";
		
		
		if(errorMessage.contains("Invalid")) {
			System.out.println("contains");
		} else {
			System.out.println("do not contain");
		}*/
		
		
		
		/*
		String rowArray[] = new String[10];
		
		String StrArray[] = {"Go Air", "G8537", "17", "13", "40", "1.5", "0", "6.7", "150", "18", "11", "12", "13",};
		System.out.println("Array Length: " + StrArray.length);
		
		
		
		for(int i=0;i<10;i++) {	
			rowArray[i] = StrArray[i];		
		}
		
		for(int i=0;i<rowArray.length;i++) {
			System.out.println(">>>>> " + StrArray[i]);
		}
		
		
//		int invalidCount = 0;
//		int listSize = 8;
//		List<String> errList = new ArrayList<String>();
		
		List<String> secondRow = new ArrayList<String>();
		
		List<String> newRow = new ArrayList<String>();
		
		String str = "Go Air, G8537, 17, 13, 40, 1.5, 0, 6.7, 150, 18, ";
		System.out.println(str);
		str = str.substring(0, 10);
		System.out.println(str);
		
//		int j =1;
		
		secondRow.add("Go Air");
		secondRow.add("G8537");
		secondRow.add("17");
		secondRow.add("13");
		secondRow.add("40");
		secondRow.add("1.5");
		secondRow.add("0");
		secondRow.add("6.7");
		secondRow.add("150");
		secondRow.add("18");
		secondRow.add("sudesh");
		secondRow.add("umesh");
		secondRow.add("nilesh");
		
		for(int i=0;i<10;i++) {
			newRow.add(secondRow.get(i));
		}
		
		for(String row : newRow) {
			System.out.println(row);
		}
		
		Iterator<String> itr = secondRow.iterator();
		int j=0;
		while (itr.hasNext()) {
			String strValue =  itr.next();

			if(j > 9) {
				secondRow.remove(j);
			}
			j++;
		}
		
		System.out.println(secondRow);
		
		for(String row : secondRow) {
			System.out.println(row);
		}
		
		for(int i=0;i<secondRow.size();i++) {
			if(i >= 10) {
				secondRow.remove(secondRow.get(i));
				//System.out.println(secondRow.get(i));
			}
		}
		
		Object[] values = secondRow.toArray();
		
		Array row[] = new Array[10];
		
		for(int i=0;i<10;i++) {
			System.out.println(values[i]);
			
		}
		Iterator<String> itr = secondRow.iterator();
		while (itr.hasNext()) {
			String  cd = itr.next();
			if(!secondRow.contains(cd.getFlightNo().trim())) {
				itr.remove();
			}									
		}
		
		String rowContent = secondRow.toString();
		
		System.out.println(rowContent);
		
//		secondRow.remove(10);
		

		
		
		
		Object[] values = secondRow.toArray();
		System.out.println(values);
		System.out.println(values.length);
		

		
		if(values.length  > 10) {
			
		}*
		
		
		
		String ff = "G8537 ";
		System.out.println("ff: " + ff.trim());
		
		errList.add("Air Line, Flight No, Billing Rate, FSC, Per Bag Octroi , Per KG Octroi, Surcharge , Other charges, CD Charge, SSP Rate, Error Description");
		errList.add("INDIGO, 9W2734, 29, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Flight Number,");
		errList.add("INDIGO, 9W2752, 30, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Flight Number,");
		errList.add("INDIGO, 9W2736, 31, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Air line for Flight Number,");
		errList.add("INDIGO, 9W2737, 32, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Air line for Flight Number,");
		errList.add("INDIGO, 9W2738, 33, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Flight Number,");
		errList.add("INDIGO, 9W2735, 34, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Air line for Flight Number,");
		errList.add("INDIGO, 9W2740, 35, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Flight Number,");
		errList.add("INDIGO, 9W2741, 36, 0, 40, 1.5, 0, 3.8, 150, 29, Invalid Air line for Flight Number,");
		
		if(errList.size() > 1) {
			for(int i=0;i<errList.size();i++) {
				if(errList.get(i).contains("Invalid Flight Number") 	
						|| errList.get(i).contains("Invalid Air line for Flight Number") 
						|| errList.get(i).contains("Invalid Air line/Flight Number")) {
					
					invalidCount++;					
				}
			}
			
			System.out.println("Counted: " + invalidCount);
			
			if(invalidCount == listSize) {
				System.out.println("All uploaded flight details are invalid.");
			} else if(invalidCount > 0 && invalidCount <= listSize) {
				System.out.println("Some of the Data are saved successfully and Some of the Data are not saved due to invalid data.");
			}
			
		} else {
			System.out.println("Details saved successfully.");
		}
		
	*/}
}
