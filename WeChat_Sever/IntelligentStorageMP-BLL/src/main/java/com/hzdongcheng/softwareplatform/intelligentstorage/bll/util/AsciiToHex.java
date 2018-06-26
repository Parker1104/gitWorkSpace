package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

import java.io.UnsupportedEncodingException;

public class AsciiToHex {

	public static  String convertStringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	public static  String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	// 504F533838383834 POS88884
	public static void main(String[] args) throws UnsupportedEncodingException {
 /*         //1   ==================
			System.out.println("\n*****ASCII码转换为16进制*****");
			String str = "POS88884";
			String hex = StringToHex.convertStringToHex(str);
			System.out.println(str+"转换为16进制 : " + hex);

	       //2   ==================
			System.out.println("\n***** 16进制转换为ASCII *****");
			hex="312D2E";
			System.out.println("Hex : " + hex+"     ASCII : " + StringToHex.convertHexToString(hex));*/
			
			
	       //3    10进制去16机制
/*			byte testes []="1....1".getBytes( );
		    for(int i=0;i<testes.length;i++){
		    	 System.out.println("----- ="+testes [i]);
		    } 
		    String  ttttt= new String(testes, "UTF-8");
		    System.out.println("ttttt========="+ttttt);
		    
		    
    	    String openCode = "";
    	    byte  codeLength[]= new byte[testes.length];
			for (short i = 0; i < codeLength.length; i++){
				  codeLength[i]= testes [i];
				  openCode += String.format("%02x", codeLength[i]);
			}
		    System.out.println("openCode========="+openCode);
		    
		    System.out.println(" ASCII : " + StringToHex.convertHexToString(openCode));*/
		    
		 
	}
}










