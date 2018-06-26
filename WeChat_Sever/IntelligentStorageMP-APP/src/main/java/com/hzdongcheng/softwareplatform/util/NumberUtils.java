package com.hzdongcheng.softwareplatform.util;

import java.util.Arrays;

public class NumberUtils {
	public static String toStringHex(String s) {  
		   byte[] baKeyword = new byte[s.length() / 2];  
		   for (int i = 0; i < baKeyword.length; i++) {  
		    try {  
		     baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(  
		       i * 2, i * 2 + 2), 16));  
		    } catch (Exception e) {  
		     e.printStackTrace();  
		    }  
		   }  
		   try {  
		    s = new String(baKeyword, "utf-8");// UTF-16le:Not  
		   } catch (Exception e1) {  
		    e1.printStackTrace();  
		   }  
		   return s;  
		}
	
	public static String cutString(String str) {  
		int m=str.length()/2;
		if(m*2<str.length()){
			m++;
		}
		String a ="";
		String[] strs=new String[m];
		int j=0;
		for(int i=0;i<str.length();i++){
		if(i%2==0){//每隔两个
		strs[j]=""+str.charAt(i);
		}else{
			strs[j]=strs[j]+" "+str.charAt(i);//将字符加上两个空格
			j++;
		}           
			a = Arrays.toString(strs);
		}
		return a;
	}
	public static void main(String[] args) {
		
	}
}
